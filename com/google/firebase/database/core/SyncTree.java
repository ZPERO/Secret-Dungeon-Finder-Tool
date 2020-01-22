package com.google.firebase.database.core;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.collection.ImmutableSortedMap;
import com.google.firebase.database.collection.LLRBNode.NodeVisitor;
import com.google.firebase.database.connection.ListenHashProvider;
import com.google.firebase.database.core.operation.AckUserWrite;
import com.google.firebase.database.core.operation.ListenComplete;
import com.google.firebase.database.core.operation.Merge;
import com.google.firebase.database.core.operation.Operation;
import com.google.firebase.database.core.operation.OperationSource;
import com.google.firebase.database.core.operation.Overwrite;
import com.google.firebase.database.core.persistence.PersistenceManager;
import com.google.firebase.database.core.utilities.Clock;
import com.google.firebase.database.core.utilities.ImmutableTree;
import com.google.firebase.database.core.utilities.ImmutableTree.TreeVisitor;
import com.google.firebase.database.core.utilities.NodeSizeEstimator;
import com.google.firebase.database.core.utilities.Pair;
import com.google.firebase.database.core.utilities.Utilities;
import com.google.firebase.database.core.view.Change;
import com.google.firebase.database.core.view.DataEvent;
import com.google.firebase.database.core.view.Event;
import com.google.firebase.database.core.view.Event.EventType;
import com.google.firebase.database.core.view.QuerySpec;
import com.google.firebase.database.core.view.View;
import com.google.firebase.database.logging.LogWrapper;
import com.google.firebase.database.snapshot.ChildKey;
import com.google.firebase.database.snapshot.Node;
import com.google.firebase.database.snapshot.RangeMerge;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Callable;

public class SyncTree
{
  private static final long SIZE_THRESHOLD_FOR_COMPOUND_HASH = 1024L;
  private final Set<QuerySpec> keepSyncedQueries = new HashSet();
  private final ListenProvider listenProvider;
  private final LogWrapper logger;
  private long nextQueryTag = 1L;
  private final WriteTree pendingWriteTree = new WriteTree();
  private final PersistenceManager persistenceManager;
  private final Map<QuerySpec, Tag> queryToTagMap = new HashMap();
  private ImmutableTree<SyncPoint> syncPointTree = ImmutableTree.emptyInstance();
  private final Map<Tag, QuerySpec> tagToQueryMap = new HashMap();
  
  public SyncTree(Context paramContext, PersistenceManager paramPersistenceManager, ListenProvider paramListenProvider)
  {
    listenProvider = paramListenProvider;
    persistenceManager = paramPersistenceManager;
    logger = paramContext.getLogger("SyncTree");
  }
  
  private List applyOperationDescendantsHelper(final Operation paramOperation, ImmutableTree paramImmutableTree, final Node paramNode, final WriteTreeRef paramWriteTreeRef)
  {
    SyncPoint localSyncPoint = (SyncPoint)paramImmutableTree.getValue();
    final Node localNode = paramNode;
    if (paramNode == null)
    {
      localNode = paramNode;
      if (localSyncPoint != null) {
        localNode = localSyncPoint.getCompleteServerCache(Path.getEmptyPath());
      }
    }
    paramNode = new ArrayList();
    paramImmutableTree.getChildren().inOrderTraversal(new LLRBNode.NodeVisitor()
    {
      public void visitEntry(ChildKey paramAnonymousChildKey, ImmutableTree paramAnonymousImmutableTree)
      {
        Node localNode = localNode;
        if (localNode != null) {
          localNode = localNode.getImmediateChild(paramAnonymousChildKey);
        } else {
          localNode = null;
        }
        WriteTreeRef localWriteTreeRef = paramWriteTreeRef.child(paramAnonymousChildKey);
        paramAnonymousChildKey = paramOperation.operationForChild(paramAnonymousChildKey);
        if (paramAnonymousChildKey != null) {
          paramNode.addAll(SyncTree.this.applyOperationDescendantsHelper(paramAnonymousChildKey, paramAnonymousImmutableTree, localNode, localWriteTreeRef));
        }
      }
    });
    if (localSyncPoint != null) {
      paramNode.addAll(localSyncPoint.applyOperation(paramOperation, paramWriteTreeRef, localNode));
    }
    return paramNode;
  }
  
  private List applyOperationHelper(Operation paramOperation, ImmutableTree paramImmutableTree, Node paramNode, WriteTreeRef paramWriteTreeRef)
  {
    if (paramOperation.getPath().isEmpty()) {
      return applyOperationDescendantsHelper(paramOperation, paramImmutableTree, paramNode, paramWriteTreeRef);
    }
    SyncPoint localSyncPoint = (SyncPoint)paramImmutableTree.getValue();
    Node localNode = paramNode;
    if (paramNode == null)
    {
      localNode = paramNode;
      if (localSyncPoint != null) {
        localNode = localSyncPoint.getCompleteServerCache(Path.getEmptyPath());
      }
    }
    paramNode = new ArrayList();
    ChildKey localChildKey = paramOperation.getPath().getFront();
    Operation localOperation = paramOperation.operationForChild(localChildKey);
    ImmutableTree localImmutableTree = (ImmutableTree)paramImmutableTree.getChildren().get(localChildKey);
    if ((localImmutableTree != null) && (localOperation != null))
    {
      if (localNode != null) {
        paramImmutableTree = localNode.getImmediateChild(localChildKey);
      } else {
        paramImmutableTree = null;
      }
      paramNode.addAll(applyOperationHelper(localOperation, localImmutableTree, paramImmutableTree, paramWriteTreeRef.child(localChildKey)));
    }
    if (localSyncPoint != null) {
      paramNode.addAll(localSyncPoint.applyOperation(paramOperation, paramWriteTreeRef, localNode));
    }
    return paramNode;
  }
  
  private List applyOperationToSyncPoints(Operation paramOperation)
  {
    return applyOperationHelper(paramOperation, syncPointTree, null, pendingWriteTree.childWrites(Path.getEmptyPath()));
  }
  
  private List applyTaggedOperation(QuerySpec paramQuerySpec, Operation paramOperation)
  {
    paramQuerySpec = paramQuerySpec.getPath();
    return ((SyncPoint)syncPointTree.readLong(paramQuerySpec)).applyOperation(paramOperation, pendingWriteTree.childWrites(paramQuerySpec), null);
  }
  
  private List collectDistinctViewsForSubTree(ImmutableTree paramImmutableTree)
  {
    ArrayList localArrayList = new ArrayList();
    collectDistinctViewsForSubTree(paramImmutableTree, localArrayList);
    return localArrayList;
  }
  
  private void collectDistinctViewsForSubTree(ImmutableTree paramImmutableTree, List paramList)
  {
    SyncPoint localSyncPoint = (SyncPoint)paramImmutableTree.getValue();
    if ((localSyncPoint != null) && (localSyncPoint.hasCompleteView()))
    {
      paramList.add(localSyncPoint.getCompleteView());
      return;
    }
    if (localSyncPoint != null) {
      paramList.addAll(localSyncPoint.getQueryViews());
    }
    paramImmutableTree = paramImmutableTree.getChildren().iterator();
    while (paramImmutableTree.hasNext()) {
      collectDistinctViewsForSubTree((ImmutableTree)((Map.Entry)paramImmutableTree.next()).getValue(), paramList);
    }
  }
  
  private Permutation getNextQueryTag()
  {
    long l = nextQueryTag;
    nextQueryTag = (1L + l);
    return new Permutation(l);
  }
  
  private QuerySpec queryForListening(QuerySpec paramQuerySpec)
  {
    QuerySpec localQuerySpec = paramQuerySpec;
    if (paramQuerySpec.loadsAllData())
    {
      localQuerySpec = paramQuerySpec;
      if (!paramQuerySpec.isDefault()) {
        localQuerySpec = QuerySpec.defaultQueryAtPath(paramQuerySpec.getPath());
      }
    }
    return localQuerySpec;
  }
  
  private QuerySpec queryForTag(Permutation paramPermutation)
  {
    return (QuerySpec)tagToQueryMap.get(paramPermutation);
  }
  
  private List removeEventRegistration(final QuerySpec paramQuerySpec, final EventRegistration paramEventRegistration, final DatabaseError paramDatabaseError)
  {
    (List)persistenceManager.runInTransaction(new Callable()
    {
      public List call()
      {
        Object localObject3 = paramQuerySpec.getPath();
        Object localObject2 = (SyncPoint)syncPointTree.readLong((Path)localObject3);
        Object localObject1 = new ArrayList();
        if ((localObject2 != null) && ((paramQuerySpec.isDefault()) || (((SyncPoint)localObject2).viewExistsForQuery(paramQuerySpec))))
        {
          localObject1 = ((SyncPoint)localObject2).removeEventRegistration(paramQuerySpec, paramEventRegistration, paramDatabaseError);
          if (((SyncPoint)localObject2).isEmpty())
          {
            localObject2 = SyncTree.this;
            SyncTree.access$702((SyncTree)localObject2, syncPointTree.remove((Path)localObject3));
          }
          List localList1 = (List)((Pair)localObject1).getFirst();
          List localList2 = (List)((Pair)localObject1).getSecond();
          localObject1 = localList1.iterator();
          for (int k = 0;; k = 1)
          {
            if (!((Iterator)localObject1).hasNext()) {
              break label197;
            }
            localObject2 = (QuerySpec)((Iterator)localObject1).next();
            persistenceManager.setQueryInactive(paramQuerySpec);
            if ((k == 0) && (!((QuerySpec)localObject2).loadsAllData())) {
              break;
            }
          }
          label197:
          localObject2 = syncPointTree;
          localObject1 = localObject2;
          int j;
          if ((((ImmutableTree)localObject2).getValue() != null) && (((SyncPoint)((ImmutableTree)localObject2).getValue()).hasCompleteView())) {
            j = 1;
          } else {
            j = 0;
          }
          Iterator localIterator = ((Path)localObject3).iterator();
          int i;
          do
          {
            m = j;
            if (!localIterator.hasNext()) {
              break;
            }
            localObject2 = ((ImmutableTree)localObject1).getChild((ChildKey)localIterator.next());
            localObject1 = localObject2;
            if ((j == 0) && ((((ImmutableTree)localObject2).getValue() == null) || (!((SyncPoint)((ImmutableTree)localObject2).getValue()).hasCompleteView()))) {
              i = 0;
            } else {
              i = 1;
            }
            m = i;
            if (i != 0) {
              break;
            }
            j = i;
          } while (!((ImmutableTree)localObject2).isEmpty());
          int m = i;
          if ((k != 0) && (m == 0))
          {
            localObject1 = syncPointTree.subtree((Path)localObject3);
            if (!((ImmutableTree)localObject1).isEmpty())
            {
              localObject1 = SyncTree.this.collectDistinctViewsForSubTree((ImmutableTree)localObject1).iterator();
              while (((Iterator)localObject1).hasNext())
              {
                localObject3 = (View)((Iterator)localObject1).next();
                localObject2 = new SyncTree.ListenContainer(SyncTree.this, (View)localObject3);
                localObject3 = ((View)localObject3).getQuery();
                listenProvider.startListening(SyncTree.this.queryForListening((QuerySpec)localObject3), SyncTree.ListenContainer.access$1400((SyncTree.ListenContainer)localObject2), (ListenHashProvider)localObject2, (SyncTree.CompletionListener)localObject2);
              }
            }
          }
          if ((m == 0) && (!localList1.isEmpty()) && (paramDatabaseError == null)) {
            if (k != 0)
            {
              listenProvider.stopListening(SyncTree.this.queryForListening(paramQuerySpec), null);
            }
            else
            {
              localObject1 = localList1.iterator();
              while (((Iterator)localObject1).hasNext())
              {
                localObject2 = (QuerySpec)((Iterator)localObject1).next();
                localObject3 = SyncTree.this.tagForQuery((QuerySpec)localObject2);
                listenProvider.stopListening(SyncTree.this.queryForListening((QuerySpec)localObject2), (Permutation)localObject3);
              }
            }
          }
          SyncTree.this.removeTags(localList1);
          return localList2;
        }
        return localObject1;
      }
    });
  }
  
  private void removeTags(List paramList)
  {
    paramList = paramList.iterator();
    while (paramList.hasNext())
    {
      QuerySpec localQuerySpec = (QuerySpec)paramList.next();
      if (!localQuerySpec.loadsAllData())
      {
        Permutation localPermutation = tagForQuery(localQuerySpec);
        queryToTagMap.remove(localQuerySpec);
        tagToQueryMap.remove(localPermutation);
      }
    }
  }
  
  private void setupListener(QuerySpec paramQuerySpec, View paramView)
  {
    Path localPath = paramQuerySpec.getPath();
    Permutation localPermutation = tagForQuery(paramQuerySpec);
    paramView = new ListenContainer(paramView);
    listenProvider.startListening(queryForListening(paramQuerySpec), localPermutation, paramView, paramView);
    paramQuerySpec = syncPointTree.subtree(localPath);
    if (localPermutation != null) {
      return;
    }
    paramQuerySpec.foreach(new ImmutableTree.TreeVisitor()
    {
      public Void onNodeValue(Path paramAnonymousPath, SyncPoint paramAnonymousSyncPoint, Void paramAnonymousVoid)
      {
        if ((!paramAnonymousPath.isEmpty()) && (paramAnonymousSyncPoint.hasCompleteView()))
        {
          paramAnonymousPath = paramAnonymousSyncPoint.getCompleteView().getQuery();
          listenProvider.stopListening(SyncTree.this.queryForListening(paramAnonymousPath), SyncTree.this.tagForQuery(paramAnonymousPath));
        }
        else
        {
          paramAnonymousPath = paramAnonymousSyncPoint.getQueryViews().iterator();
          while (paramAnonymousPath.hasNext())
          {
            paramAnonymousSyncPoint = ((View)paramAnonymousPath.next()).getQuery();
            listenProvider.stopListening(SyncTree.this.queryForListening(paramAnonymousSyncPoint), SyncTree.this.tagForQuery(paramAnonymousSyncPoint));
          }
        }
        return null;
      }
    });
  }
  
  private Permutation tagForQuery(QuerySpec paramQuerySpec)
  {
    return (Permutation)queryToTagMap.get(paramQuerySpec);
  }
  
  public List ackUserWrite(final long paramLong, boolean paramBoolean1, final boolean paramBoolean2, final Clock paramClock)
  {
    (List)persistenceManager.runInTransaction(new Callable()
    {
      public List call()
      {
        if (paramBoolean2) {
          persistenceManager.removeUserWrite(paramLong);
        }
        UserWriteRecord localUserWriteRecord = pendingWriteTree.getWrite(paramLong);
        boolean bool = pendingWriteTree.removeWrite(paramLong);
        if ((localUserWriteRecord.isVisible()) && (!paramClock))
        {
          localObject1 = ServerValues.generateServerValues(val$serverClock);
          if (localUserWriteRecord.isOverwrite())
          {
            localObject1 = ServerValues.resolveDeferredValueSnapshot(localUserWriteRecord.getOverwrite(), (Map)localObject1);
            persistenceManager.applyUserWriteToServerCache(localUserWriteRecord.getPath(), (Node)localObject1);
          }
          else
          {
            localObject1 = ServerValues.resolveDeferredValueMerge(localUserWriteRecord.getMerge(), (Map)localObject1);
            persistenceManager.applyUserWriteToServerCache(localUserWriteRecord.getPath(), (CompoundWrite)localObject1);
          }
        }
        if (!bool) {
          return Collections.emptyList();
        }
        Object localObject2 = ImmutableTree.emptyInstance();
        Object localObject1 = localObject2;
        if (localUserWriteRecord.isOverwrite())
        {
          localObject2 = ((ImmutableTree)localObject2).getKey(Path.getEmptyPath(), Boolean.valueOf(true));
        }
        else
        {
          Iterator localIterator = localUserWriteRecord.getMerge().iterator();
          for (;;)
          {
            localObject2 = localObject1;
            if (!localIterator.hasNext()) {
              break;
            }
            localObject1 = ((ImmutableTree)localObject1).getKey((Path)((Map.Entry)localIterator.next()).getKey(), Boolean.valueOf(true));
          }
        }
        return SyncTree.this.applyOperationToSyncPoints(new AckUserWrite(localUserWriteRecord.getPath(), (ImmutableTree)localObject2, paramClock));
      }
    });
  }
  
  public List addEventRegistration(final EventRegistration paramEventRegistration)
  {
    (List)persistenceManager.runInTransaction(new Callable()
    {
      public List call()
      {
        throw new Runtime("d2j fail translate: java.lang.RuntimeException: fail exe a28 = a27\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.exec(BaseAnalyze.java:92)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.exec(BaseAnalyze.java:1)\n\tat com.googlecode.dex2jar.ir.ts.Cfg.dfs(Cfg.java:255)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.analyze0(BaseAnalyze.java:75)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.analyze(BaseAnalyze.java:69)\n\tat com.googlecode.dex2jar.ir.ts.UnSSATransformer.transform(UnSSATransformer.java:274)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:163)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\nCaused by: java.lang.NullPointerException\n");
      }
    });
  }
  
  public List applyListenComplete(final Path paramPath)
  {
    (List)persistenceManager.runInTransaction(new Callable()
    {
      public List call()
      {
        persistenceManager.setQueryComplete(QuerySpec.defaultQueryAtPath(paramPath));
        return SyncTree.this.applyOperationToSyncPoints(new ListenComplete(OperationSource.SERVER, paramPath));
      }
    });
  }
  
  public List applyServerMerge(final Path paramPath, final Map paramMap)
  {
    (List)persistenceManager.runInTransaction(new Callable()
    {
      public List call()
      {
        CompoundWrite localCompoundWrite = CompoundWrite.fromPathMerge(paramMap);
        persistenceManager.updateServerCache(paramPath, localCompoundWrite);
        return SyncTree.this.applyOperationToSyncPoints(new Merge(OperationSource.SERVER, paramPath, localCompoundWrite));
      }
    });
  }
  
  public List applyServerOverwrite(final Path paramPath, final Node paramNode)
  {
    (List)persistenceManager.runInTransaction(new Callable()
    {
      public List call()
      {
        persistenceManager.updateServerCache(QuerySpec.defaultQueryAtPath(paramPath), paramNode);
        return SyncTree.this.applyOperationToSyncPoints(new Overwrite(OperationSource.SERVER, paramPath, paramNode));
      }
    });
  }
  
  public List applyServerRangeMerges(Path paramPath, List paramList)
  {
    Object localObject = (SyncPoint)syncPointTree.readLong(paramPath);
    if (localObject == null) {
      return Collections.emptyList();
    }
    localObject = ((SyncPoint)localObject).getCompleteView();
    if (localObject != null)
    {
      localObject = ((View)localObject).getServerCache();
      Iterator localIterator = paramList.iterator();
      for (paramList = (List)localObject; localIterator.hasNext(); paramList = ((RangeMerge)localIterator.next()).applyTo(paramList)) {}
      return applyServerOverwrite(paramPath, paramList);
    }
    return Collections.emptyList();
  }
  
  public List applyTaggedListenComplete(final Permutation paramPermutation)
  {
    (List)persistenceManager.runInTransaction(new Callable()
    {
      public List call()
      {
        QuerySpec localQuerySpec = SyncTree.this.queryForTag(paramPermutation);
        if (localQuerySpec != null)
        {
          persistenceManager.setQueryComplete(localQuerySpec);
          ListenComplete localListenComplete = new ListenComplete(OperationSource.forServerTaggedQuery(localQuerySpec.getParams()), Path.getEmptyPath());
          return SyncTree.this.applyTaggedOperation(localQuerySpec, localListenComplete);
        }
        return Collections.emptyList();
      }
    });
  }
  
  public List applyTaggedQueryMerge(final Path paramPath, final Map paramMap, final Permutation paramPermutation)
  {
    (List)persistenceManager.runInTransaction(new Callable()
    {
      public List call()
      {
        QuerySpec localQuerySpec = SyncTree.this.queryForTag(paramPermutation);
        if (localQuerySpec != null)
        {
          Object localObject = Path.getRelative(localQuerySpec.getPath(), paramPath);
          CompoundWrite localCompoundWrite = CompoundWrite.fromPathMerge(paramMap);
          persistenceManager.updateServerCache(paramPath, localCompoundWrite);
          localObject = new Merge(OperationSource.forServerTaggedQuery(localQuerySpec.getParams()), (Path)localObject, localCompoundWrite);
          return SyncTree.this.applyTaggedOperation(localQuerySpec, (Operation)localObject);
        }
        return Collections.emptyList();
      }
    });
  }
  
  public List applyTaggedQueryOverwrite(final Path paramPath, final Node paramNode, final Permutation paramPermutation)
  {
    (List)persistenceManager.runInTransaction(new Callable()
    {
      public List call()
      {
        QuerySpec localQuerySpec = SyncTree.this.queryForTag(paramPermutation);
        if (localQuerySpec != null)
        {
          Path localPath = Path.getRelative(localQuerySpec.getPath(), paramPath);
          if (localPath.isEmpty()) {
            localObject = localQuerySpec;
          } else {
            localObject = QuerySpec.defaultQueryAtPath(paramPath);
          }
          persistenceManager.updateServerCache((QuerySpec)localObject, paramNode);
          Object localObject = new Overwrite(OperationSource.forServerTaggedQuery(localQuerySpec.getParams()), localPath, paramNode);
          return SyncTree.this.applyTaggedOperation(localQuerySpec, (Operation)localObject);
        }
        return Collections.emptyList();
      }
    });
  }
  
  public List applyTaggedRangeMerges(Path paramPath, List paramList, Permutation paramPermutation)
  {
    Object localObject = queryForTag(paramPermutation);
    if (localObject != null)
    {
      localObject = ((SyncPoint)syncPointTree.readLong(((QuerySpec)localObject).getPath())).viewForQuery((QuerySpec)localObject).getServerCache();
      Iterator localIterator = paramList.iterator();
      for (paramList = (List)localObject; localIterator.hasNext(); paramList = ((RangeMerge)localIterator.next()).applyTo(paramList)) {}
      return applyTaggedQueryOverwrite(paramPath, paramList, paramPermutation);
    }
    return Collections.emptyList();
  }
  
  public List applyUserMerge(final Path paramPath, final CompoundWrite paramCompoundWrite1, CompoundWrite paramCompoundWrite2, final long paramLong, final boolean paramBoolean)
  {
    (List)persistenceManager.runInTransaction(new Callable()
    {
      public List call()
        throws Exception
      {
        if (paramBoolean) {
          persistenceManager.saveUserMerge(paramPath, paramCompoundWrite1, paramLong);
        }
        pendingWriteTree.addMerge(paramPath, val$children, Long.valueOf(paramLong));
        return SyncTree.this.applyOperationToSyncPoints(new Merge(OperationSource.USER, paramPath, val$children));
      }
    });
  }
  
  public List applyUserOverwrite(final Path paramPath, final Node paramNode1, Node paramNode2, final long paramLong, final boolean paramBoolean1, final boolean paramBoolean2)
  {
    boolean bool;
    if ((!paramBoolean1) && (paramBoolean2)) {
      bool = false;
    } else {
      bool = true;
    }
    Utilities.hardAssert(bool, "We shouldn't be persisting non-visible writes.");
    (List)persistenceManager.runInTransaction(new Callable()
    {
      public List call()
      {
        if (paramBoolean2) {
          persistenceManager.saveUserOverwrite(paramPath, paramNode1, paramLong);
        }
        pendingWriteTree.addOverwrite(paramPath, paramBoolean1, Long.valueOf(paramLong), val$visible);
        if (!val$visible) {
          return Collections.emptyList();
        }
        return SyncTree.this.applyOperationToSyncPoints(new Overwrite(OperationSource.USER, paramPath, paramBoolean1));
      }
    });
  }
  
  public Node calcCompleteEventCache(Path paramPath, List paramList)
  {
    ImmutableTree localImmutableTree = syncPointTree;
    Object localObject1 = (SyncPoint)localImmutableTree.getValue();
    Object localObject3 = Path.getEmptyPath();
    localObject1 = null;
    Object localObject2 = paramPath;
    Object localObject4;
    do
    {
      Object localObject5 = ((Path)localObject2).getFront();
      Path localPath1 = ((Path)localObject2).popFront();
      localObject2 = localPath1;
      localObject4 = ((Path)localObject3).child((ChildKey)localObject5);
      localObject3 = localObject4;
      Path localPath2 = Path.getRelative((Path)localObject4, paramPath);
      if (localObject5 != null) {
        localImmutableTree = localImmutableTree.getChild((ChildKey)localObject5);
      } else {
        localImmutableTree = ImmutableTree.emptyInstance();
      }
      localObject5 = (SyncPoint)localImmutableTree.getValue();
      localObject4 = localObject1;
      if (localObject5 != null) {
        localObject4 = ((SyncPoint)localObject5).getCompleteServerCache(localPath2);
      }
      if (localPath1.isEmpty()) {
        break;
      }
      localObject1 = localObject4;
    } while (localObject4 == null);
    return pendingWriteTree.calcCompleteEventCache(paramPath, (Node)localObject4, paramList, true);
  }
  
  ImmutableTree getSyncPointTree()
  {
    return syncPointTree;
  }
  
  public boolean isEmpty()
  {
    return syncPointTree.isEmpty();
  }
  
  public void keepSynced(QuerySpec paramQuerySpec, boolean paramBoolean)
  {
    if ((paramBoolean) && (!keepSyncedQueries.contains(paramQuerySpec)))
    {
      addEventRegistration(new KeepSyncedEventRegistration(paramQuerySpec));
      keepSyncedQueries.add(paramQuerySpec);
      return;
    }
    if ((!paramBoolean) && (keepSyncedQueries.contains(paramQuerySpec)))
    {
      removeEventRegistration(new KeepSyncedEventRegistration(paramQuerySpec));
      keepSyncedQueries.remove(paramQuerySpec);
    }
  }
  
  public List removeAllEventRegistrations(QuerySpec paramQuerySpec, DatabaseError paramDatabaseError)
  {
    return removeEventRegistration(paramQuerySpec, null, paramDatabaseError);
  }
  
  public List removeAllWrites()
  {
    (List)persistenceManager.runInTransaction(new Callable()
    {
      public List call()
        throws Exception
      {
        persistenceManager.removeAllUserWrites();
        if (pendingWriteTree.purgeAllWrites().isEmpty()) {
          return Collections.emptyList();
        }
        ImmutableTree localImmutableTree = new ImmutableTree(Boolean.valueOf(true));
        return SyncTree.this.applyOperationToSyncPoints(new AckUserWrite(Path.getEmptyPath(), localImmutableTree, true));
      }
    });
  }
  
  public List removeEventRegistration(EventRegistration paramEventRegistration)
  {
    return removeEventRegistration(paramEventRegistration.getQuerySpec(), paramEventRegistration, null);
  }
  
  public static abstract interface CompletionListener
  {
    public abstract List onListenComplete(DatabaseError paramDatabaseError);
  }
  
  private static class KeepSyncedEventRegistration
    extends EventRegistration
  {
    private QuerySpec spec;
    
    public KeepSyncedEventRegistration(QuerySpec paramQuerySpec)
    {
      spec = paramQuerySpec;
    }
    
    public EventRegistration clone(QuerySpec paramQuerySpec)
    {
      return new KeepSyncedEventRegistration(paramQuerySpec);
    }
    
    public DataEvent createEvent(Change paramChange, QuerySpec paramQuerySpec)
    {
      return null;
    }
    
    public boolean equals(Object paramObject)
    {
      return ((paramObject instanceof KeepSyncedEventRegistration)) && (spec.equals(spec));
    }
    
    public void fireCancelEvent(DatabaseError paramDatabaseError) {}
    
    public void fireEvent(DataEvent paramDataEvent) {}
    
    public QuerySpec getQuerySpec()
    {
      return spec;
    }
    
    public int hashCode()
    {
      return spec.hashCode();
    }
    
    public boolean isSameListener(EventRegistration paramEventRegistration)
    {
      return paramEventRegistration instanceof KeepSyncedEventRegistration;
    }
    
    public boolean respondsTo(Event.EventType paramEventType)
    {
      return false;
    }
  }
  
  private class ListenContainer
    implements ListenHashProvider, SyncTree.CompletionListener
  {
    private final Permutation act;
    private final View view;
    
    public ListenContainer(View paramView)
    {
      view = paramView;
      act = SyncTree.this.tagForQuery(paramView.getQuery());
    }
    
    public com.google.firebase.database.connection.CompoundHash getCompoundHash()
    {
      com.google.firebase.database.snapshot.CompoundHash localCompoundHash = com.google.firebase.database.snapshot.CompoundHash.fromNode(view.getServerCache());
      Object localObject = localCompoundHash.getPosts();
      ArrayList localArrayList = new ArrayList(((List)localObject).size());
      localObject = ((List)localObject).iterator();
      while (((Iterator)localObject).hasNext()) {
        localArrayList.add(((Path)((Iterator)localObject).next()).asList());
      }
      return new com.google.firebase.database.connection.CompoundHash(localArrayList, localCompoundHash.getHashes());
    }
    
    public String getSimpleHash()
    {
      return view.getServerCache().getHash();
    }
    
    public List onListenComplete(DatabaseError paramDatabaseError)
    {
      if (paramDatabaseError == null)
      {
        paramDatabaseError = view.getQuery();
        localObject = act;
        if (localObject != null) {
          return applyTaggedListenComplete((Permutation)localObject);
        }
        return applyListenComplete(paramDatabaseError.getPath());
      }
      Object localObject = logger;
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Listen at ");
      localStringBuilder.append(view.getQuery().getPath());
      localStringBuilder.append(" failed: ");
      localStringBuilder.append(paramDatabaseError.toString());
      ((LogWrapper)localObject).warn(localStringBuilder.toString());
      return removeAllEventRegistrations(view.getQuery(), paramDatabaseError);
    }
    
    public boolean shouldIncludeCompoundHash()
    {
      return NodeSizeEstimator.estimateSerializedNodeSize(view.getServerCache()) > 1024L;
    }
  }
  
  public static abstract interface ListenProvider
  {
    public abstract void startListening(QuerySpec paramQuerySpec, Permutation paramPermutation, ListenHashProvider paramListenHashProvider, SyncTree.CompletionListener paramCompletionListener);
    
    public abstract void stopListening(QuerySpec paramQuerySpec, Permutation paramPermutation);
  }
}
