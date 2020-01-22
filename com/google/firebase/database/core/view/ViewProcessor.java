package com.google.firebase.database.core.view;

import com.google.firebase.database.core.CompoundWrite;
import com.google.firebase.database.core.Path;
import com.google.firebase.database.core.WriteTreeRef;
import com.google.firebase.database.core.operation.AckUserWrite;
import com.google.firebase.database.core.operation.Merge;
import com.google.firebase.database.core.operation.Operation;
import com.google.firebase.database.core.operation.OperationSource;
import com.google.firebase.database.core.operation.Overwrite;
import com.google.firebase.database.core.utilities.ImmutableTree;
import com.google.firebase.database.core.view.filter.ChildChangeAccumulator;
import com.google.firebase.database.core.view.filter.NodeFilter;
import com.google.firebase.database.core.view.filter.NodeFilter.CompleteChildSource;
import com.google.firebase.database.snapshot.ChildKey;
import com.google.firebase.database.snapshot.ChildrenNode;
import com.google.firebase.database.snapshot.EmptyNode;
import com.google.firebase.database.snapshot.Index;
import com.google.firebase.database.snapshot.IndexedNode;
import com.google.firebase.database.snapshot.KeyIndex;
import com.google.firebase.database.snapshot.NamedNode;
import com.google.firebase.database.snapshot.Node;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class ViewProcessor
{
  private static NodeFilter.CompleteChildSource NO_COMPLETE_SOURCE = new NodeFilter.CompleteChildSource()
  {
    public NamedNode getChildAfterChild(Index paramAnonymousIndex, NamedNode paramAnonymousNamedNode, boolean paramAnonymousBoolean)
    {
      return null;
    }
    
    public Node getCompleteChild(ChildKey paramAnonymousChildKey)
    {
      return null;
    }
  };
  private final NodeFilter filter;
  
  public ViewProcessor(NodeFilter paramNodeFilter)
  {
    filter = paramNodeFilter;
  }
  
  private ViewCache ackUserWrite(ViewCache paramViewCache, Path paramPath, ImmutableTree paramImmutableTree, WriteTreeRef paramWriteTreeRef, Node paramNode, ChildChangeAccumulator paramChildChangeAccumulator)
  {
    if (paramWriteTreeRef.shadowingWrite(paramPath) != null) {
      return paramViewCache;
    }
    boolean bool = paramViewCache.getServerCache().isFiltered();
    Object localObject2 = paramViewCache.getServerCache();
    Object localObject1;
    if (paramImmutableTree.getValue() != null)
    {
      if (((paramPath.isEmpty()) && (((CacheNode)localObject2).isFullyInitialized())) || (((CacheNode)localObject2).isCompleteForPath(paramPath))) {
        return applyServerOverwrite(paramViewCache, paramPath, ((CacheNode)localObject2).getNode().getChild(paramPath), paramWriteTreeRef, paramNode, bool, paramChildChangeAccumulator);
      }
      paramImmutableTree = paramViewCache;
      if (paramPath.isEmpty())
      {
        paramImmutableTree = CompoundWrite.emptyWrite();
        localObject1 = ((CacheNode)localObject2).getNode().iterator();
        while (((Iterator)localObject1).hasNext())
        {
          localObject2 = (NamedNode)((Iterator)localObject1).next();
          paramImmutableTree = paramImmutableTree.addWrite(((NamedNode)localObject2).getName(), ((NamedNode)localObject2).getNode());
        }
        return applyServerMerge(paramViewCache, paramPath, paramImmutableTree, paramWriteTreeRef, paramNode, bool, paramChildChangeAccumulator);
      }
    }
    else
    {
      localObject1 = CompoundWrite.emptyWrite();
      Iterator localIterator = paramImmutableTree.iterator();
      paramImmutableTree = (ImmutableTree)localObject1;
      while (localIterator.hasNext())
      {
        localObject1 = (Path)((Map.Entry)localIterator.next()).getKey();
        Path localPath = paramPath.child((Path)localObject1);
        if (((CacheNode)localObject2).isCompleteForPath(localPath)) {
          paramImmutableTree = paramImmutableTree.addWrite((Path)localObject1, ((CacheNode)localObject2).getNode().getChild(localPath));
        }
      }
      paramImmutableTree = applyServerMerge(paramViewCache, paramPath, paramImmutableTree, paramWriteTreeRef, paramNode, bool, paramChildChangeAccumulator);
    }
    return paramImmutableTree;
  }
  
  private ViewCache applyServerMerge(ViewCache paramViewCache, Path paramPath, CompoundWrite paramCompoundWrite, WriteTreeRef paramWriteTreeRef, Node paramNode, boolean paramBoolean, ChildChangeAccumulator paramChildChangeAccumulator)
  {
    if ((paramViewCache.getServerCache().getNode().isEmpty()) && (!paramViewCache.getServerCache().isFullyInitialized())) {
      return paramViewCache;
    }
    if (!paramPath.isEmpty()) {
      paramCompoundWrite = CompoundWrite.emptyWrite().addWrites(paramPath, paramCompoundWrite);
    }
    Node localNode1 = paramViewCache.getServerCache().getNode();
    paramCompoundWrite = paramCompoundWrite.childCompoundWrites();
    Object localObject1 = paramCompoundWrite.entrySet().iterator();
    paramPath = paramViewCache;
    Object localObject3;
    Object localObject2;
    while (((Iterator)localObject1).hasNext())
    {
      localObject3 = (Map.Entry)((Iterator)localObject1).next();
      localObject2 = (ChildKey)((Map.Entry)localObject3).getKey();
      if (localNode1.hasChild((ChildKey)localObject2))
      {
        Node localNode2 = localNode1.getImmediateChild((ChildKey)localObject2);
        localObject3 = ((CompoundWrite)((Map.Entry)localObject3).getValue()).apply(localNode2);
        paramPath = applyServerOverwrite(paramPath, new Path(new ChildKey[] { localObject2 }), (Node)localObject3, paramWriteTreeRef, paramNode, paramBoolean, paramChildChangeAccumulator);
      }
    }
    paramCompoundWrite = paramCompoundWrite.entrySet().iterator();
    while (paramCompoundWrite.hasNext())
    {
      localObject2 = (Map.Entry)paramCompoundWrite.next();
      localObject1 = (ChildKey)((Map.Entry)localObject2).getKey();
      localObject3 = (CompoundWrite)((Map.Entry)localObject2).getValue();
      int i;
      if ((!paramViewCache.getServerCache().isCompleteForChild((ChildKey)localObject1)) && (((CompoundWrite)localObject3).rootWrite() == null)) {
        i = 1;
      } else {
        i = 0;
      }
      if ((!localNode1.hasChild((ChildKey)localObject1)) && (i == 0))
      {
        localObject3 = localNode1.getImmediateChild((ChildKey)localObject1);
        localObject2 = ((CompoundWrite)((Map.Entry)localObject2).getValue()).apply((Node)localObject3);
        paramPath = applyServerOverwrite(paramPath, new Path(new ChildKey[] { localObject1 }), (Node)localObject2, paramWriteTreeRef, paramNode, paramBoolean, paramChildChangeAccumulator);
      }
    }
    return paramPath;
  }
  
  private ViewCache applyServerOverwrite(ViewCache paramViewCache, Path paramPath, Node paramNode1, WriteTreeRef paramWriteTreeRef, Node paramNode2, boolean paramBoolean, ChildChangeAccumulator paramChildChangeAccumulator)
  {
    CacheNode localCacheNode = paramViewCache.getServerCache();
    NodeFilter localNodeFilter = filter;
    if (!paramBoolean) {
      localNodeFilter = localNodeFilter.getIndexedFilter();
    }
    paramBoolean = paramPath.isEmpty();
    boolean bool = true;
    if (paramBoolean) {
      paramNode1 = localNodeFilter.updateFullNode(localCacheNode.getIndexedNode(), IndexedNode.from(paramNode1, localNodeFilter.getIndex()), null);
    }
    for (;;)
    {
      break;
      ChildKey localChildKey;
      Path localPath;
      if ((localNodeFilter.filtersNodes()) && (!localCacheNode.isFiltered()))
      {
        localChildKey = paramPath.getFront();
        localPath = paramPath.popFront();
        paramNode1 = localCacheNode.getNode().getImmediateChild(localChildKey).updateChild(localPath, paramNode1);
        paramNode1 = localCacheNode.getIndexedNode().updateChild(localChildKey, paramNode1);
        paramNode1 = localNodeFilter.updateFullNode(localCacheNode.getIndexedNode(), paramNode1, null);
      }
      else
      {
        localChildKey = paramPath.getFront();
        if ((!localCacheNode.isCompleteForPath(paramPath)) && (paramPath.size() > 1)) {
          return paramViewCache;
        }
        localPath = paramPath.popFront();
        paramNode1 = localCacheNode.getNode().getImmediateChild(localChildKey).updateChild(localPath, paramNode1);
        if (localChildKey.isPriorityChildName()) {
          paramNode1 = localNodeFilter.updatePriority(localCacheNode.getIndexedNode(), paramNode1);
        } else {
          paramNode1 = localNodeFilter.updateChild(localCacheNode.getIndexedNode(), localChildKey, paramNode1, localPath, NO_COMPLETE_SOURCE, null);
        }
      }
    }
    paramBoolean = bool;
    if (!localCacheNode.isFullyInitialized()) {
      if (paramPath.isEmpty()) {
        paramBoolean = bool;
      } else {
        paramBoolean = false;
      }
    }
    paramViewCache = paramViewCache.updateServerSnap(paramNode1, paramBoolean, localNodeFilter.filtersNodes());
    return generateEventCacheAfterServerEvent(paramViewCache, paramPath, paramWriteTreeRef, new WriteTreeCompleteChildSource(paramWriteTreeRef, paramViewCache, paramNode2), paramChildChangeAccumulator);
  }
  
  private ViewCache applyUserMerge(ViewCache paramViewCache, Path paramPath, CompoundWrite paramCompoundWrite, WriteTreeRef paramWriteTreeRef, Node paramNode, ChildChangeAccumulator paramChildChangeAccumulator)
  {
    Object localObject1 = paramCompoundWrite.iterator();
    ViewCache localViewCache = paramViewCache;
    Object localObject2;
    while (((Iterator)localObject1).hasNext())
    {
      localObject2 = (Map.Entry)((Iterator)localObject1).next();
      Path localPath = paramPath.child((Path)((Map.Entry)localObject2).getKey());
      if (cacheHasChild(paramViewCache, localPath.getFront())) {
        localViewCache = applyUserOverwrite(localViewCache, localPath, (Node)((Map.Entry)localObject2).getValue(), paramWriteTreeRef, paramNode, paramChildChangeAccumulator);
      }
    }
    paramCompoundWrite = paramCompoundWrite.iterator();
    while (paramCompoundWrite.hasNext())
    {
      localObject1 = (Map.Entry)paramCompoundWrite.next();
      localObject2 = paramPath.child((Path)((Map.Entry)localObject1).getKey());
      if (!cacheHasChild(paramViewCache, ((Path)localObject2).getFront())) {
        localViewCache = applyUserOverwrite(localViewCache, (Path)localObject2, (Node)((Map.Entry)localObject1).getValue(), paramWriteTreeRef, paramNode, paramChildChangeAccumulator);
      }
    }
    return localViewCache;
  }
  
  private ViewCache applyUserOverwrite(ViewCache paramViewCache, Path paramPath, Node paramNode1, WriteTreeRef paramWriteTreeRef, Node paramNode2, ChildChangeAccumulator paramChildChangeAccumulator)
  {
    CacheNode localCacheNode = paramViewCache.getEventCache();
    paramWriteTreeRef = new WriteTreeCompleteChildSource(paramWriteTreeRef, paramViewCache, paramNode2);
    if (paramPath.isEmpty())
    {
      paramPath = filter.getIndex();
      paramPath = IndexedNode.from((Node)paramNode1, paramPath);
      return paramViewCache.updateEventSnap(filter.updateFullNode(paramViewCache.getEventCache().getIndexedNode(), paramPath, paramChildChangeAccumulator), true, filter.filtersNodes());
    }
    paramNode2 = paramPath.getFront();
    if (paramNode2.isPriorityChildName()) {
      return paramViewCache.updateEventSnap(filter.updatePriority(paramViewCache.getEventCache().getIndexedNode(), (Node)paramNode1), localCacheNode.isFullyInitialized(), localCacheNode.isFiltered());
    }
    Path localPath = paramPath.popFront();
    Node localNode = localCacheNode.getNode().getImmediateChild(paramNode2);
    if (!localPath.isEmpty()) {
      for (;;)
      {
        paramPath = paramWriteTreeRef.getCompleteChild(paramNode2);
        if (paramPath != null)
        {
          if ((localPath.getBack().isPriorityChildName()) && (paramPath.getChild(localPath.getParent()).isEmpty())) {
            paramNode1 = paramPath;
          } else {
            paramNode1 = paramPath.updateChild(localPath, (Node)paramNode1);
          }
        }
        else {
          paramNode1 = EmptyNode.Empty();
        }
      }
    }
    paramPath = paramViewCache;
    if (!localNode.equals(paramNode1)) {
      paramPath = paramViewCache.updateEventSnap(filter.updateChild(localCacheNode.getIndexedNode(), paramNode2, (Node)paramNode1, localPath, paramWriteTreeRef, paramChildChangeAccumulator), localCacheNode.isFullyInitialized(), filter.filtersNodes());
    }
    return paramPath;
  }
  
  private static boolean cacheHasChild(ViewCache paramViewCache, ChildKey paramChildKey)
  {
    return paramViewCache.getEventCache().isCompleteForChild(paramChildKey);
  }
  
  private ViewCache generateEventCacheAfterServerEvent(ViewCache paramViewCache, Path paramPath, WriteTreeRef paramWriteTreeRef, NodeFilter.CompleteChildSource paramCompleteChildSource, ChildChangeAccumulator paramChildChangeAccumulator)
  {
    CacheNode localCacheNode = paramViewCache.getEventCache();
    if (paramWriteTreeRef.shadowingWrite(paramPath) != null) {
      return paramViewCache;
    }
    Object localObject;
    if (paramPath.isEmpty())
    {
      if (paramViewCache.getServerCache().isFiltered())
      {
        localObject = paramViewCache.getCompleteServerSnap();
        paramCompleteChildSource = (NodeFilter.CompleteChildSource)localObject;
        if (!(localObject instanceof ChildrenNode)) {
          paramCompleteChildSource = EmptyNode.Empty();
        }
        paramWriteTreeRef = paramWriteTreeRef.calcCompleteEventChildren((Node)paramCompleteChildSource);
      }
      else
      {
        paramWriteTreeRef = paramWriteTreeRef.calcCompleteEventCache(paramViewCache.getCompleteServerSnap());
      }
      paramWriteTreeRef = IndexedNode.from(paramWriteTreeRef, filter.getIndex());
      paramWriteTreeRef = filter.updateFullNode(paramViewCache.getEventCache().getIndexedNode(), paramWriteTreeRef, paramChildChangeAccumulator);
    }
    else
    {
      localObject = paramPath.getFront();
      if (((ChildKey)localObject).isPriorityChildName())
      {
        paramWriteTreeRef = paramWriteTreeRef.calcEventCacheAfterServerOverwrite(paramPath, localCacheNode.getNode(), paramViewCache.getServerCache().getNode());
        if (paramWriteTreeRef != null) {
          paramWriteTreeRef = filter.updatePriority(localCacheNode.getIndexedNode(), paramWriteTreeRef);
        } else {
          paramWriteTreeRef = localCacheNode.getIndexedNode();
        }
      }
      else
      {
        Path localPath = paramPath.popFront();
        if (localCacheNode.isCompleteForChild((ChildKey)localObject))
        {
          Node localNode = paramViewCache.getServerCache().getNode();
          paramWriteTreeRef = paramWriteTreeRef.calcEventCacheAfterServerOverwrite(paramPath, localCacheNode.getNode(), localNode);
          if (paramWriteTreeRef != null) {
            paramWriteTreeRef = localCacheNode.getNode().getImmediateChild((ChildKey)localObject).updateChild(localPath, paramWriteTreeRef);
          } else {
            paramWriteTreeRef = localCacheNode.getNode().getImmediateChild((ChildKey)localObject);
          }
        }
        else
        {
          paramWriteTreeRef = paramWriteTreeRef.calcCompleteChild((ChildKey)localObject, paramViewCache.getServerCache());
        }
        if (paramWriteTreeRef != null) {
          paramWriteTreeRef = filter.updateChild(localCacheNode.getIndexedNode(), (ChildKey)localObject, paramWriteTreeRef, localPath, paramCompleteChildSource, paramChildChangeAccumulator);
        } else {
          paramWriteTreeRef = localCacheNode.getIndexedNode();
        }
      }
    }
    boolean bool;
    if ((!localCacheNode.isFullyInitialized()) && (!paramPath.isEmpty())) {
      bool = false;
    } else {
      bool = true;
    }
    return paramViewCache.updateEventSnap(paramWriteTreeRef, bool, filter.filtersNodes());
  }
  
  private ViewCache listenComplete(ViewCache paramViewCache, Path paramPath, WriteTreeRef paramWriteTreeRef, Node paramNode, ChildChangeAccumulator paramChildChangeAccumulator)
  {
    paramNode = paramViewCache.getServerCache();
    IndexedNode localIndexedNode = paramNode.getIndexedNode();
    boolean bool;
    if ((!paramNode.isFullyInitialized()) && (!paramPath.isEmpty())) {
      bool = false;
    } else {
      bool = true;
    }
    return generateEventCacheAfterServerEvent(paramViewCache.updateServerSnap(localIndexedNode, bool, paramNode.isFiltered()), paramPath, paramWriteTreeRef, NO_COMPLETE_SOURCE, paramChildChangeAccumulator);
  }
  
  private void maybeAddValueEvent(ViewCache paramViewCache1, ViewCache paramViewCache2, List paramList)
  {
    paramViewCache2 = paramViewCache2.getEventCache();
    if (paramViewCache2.isFullyInitialized())
    {
      int i;
      if ((!paramViewCache2.getNode().isLeafNode()) && (!paramViewCache2.getNode().isEmpty())) {
        i = 0;
      } else {
        i = 1;
      }
      if ((!paramList.isEmpty()) || (!paramViewCache1.getEventCache().isFullyInitialized()) || ((i != 0) && (!paramViewCache2.getNode().equals(paramViewCache1.getCompleteEventSnap()))) || (!paramViewCache2.getNode().getPriority().equals(paramViewCache1.getCompleteEventSnap().getPriority()))) {
        paramList.add(Change.valueChange(paramViewCache2.getIndexedNode()));
      }
    }
  }
  
  public ProcessorResult applyOperation(ViewCache paramViewCache, Operation paramOperation, WriteTreeRef paramWriteTreeRef, Node paramNode)
  {
    ChildChangeAccumulator localChildChangeAccumulator = new ChildChangeAccumulator();
    int i = 2.$SwitchMap$com$google$firebase$database$core$operation$Operation$OperationType[paramOperation.getType().ordinal()];
    boolean bool;
    if (i != 1)
    {
      if (i != 2)
      {
        if (i != 3)
        {
          if (i == 4)
          {
            paramOperation = listenComplete(paramViewCache, paramOperation.getPath(), paramWriteTreeRef, paramNode, localChildChangeAccumulator);
          }
          else
          {
            paramViewCache = new StringBuilder();
            paramViewCache.append("Unknown operation: ");
            paramViewCache.append(paramOperation.getType());
            throw new AssertionError(paramViewCache.toString());
          }
        }
        else
        {
          paramOperation = (AckUserWrite)paramOperation;
          if (!paramOperation.isRevert()) {
            paramOperation = ackUserWrite(paramViewCache, paramOperation.getPath(), paramOperation.getAffectedTree(), paramWriteTreeRef, paramNode, localChildChangeAccumulator);
          } else {
            paramOperation = revertUserWrite(paramViewCache, paramOperation.getPath(), paramWriteTreeRef, paramNode, localChildChangeAccumulator);
          }
        }
      }
      else
      {
        paramOperation = (Merge)paramOperation;
        if (paramOperation.getSource().isFromUser())
        {
          paramOperation = applyUserMerge(paramViewCache, paramOperation.getPath(), paramOperation.getChildren(), paramWriteTreeRef, paramNode, localChildChangeAccumulator);
        }
        else
        {
          if ((!paramOperation.getSource().isTagged()) && (!paramViewCache.getServerCache().isFiltered())) {
            bool = false;
          } else {
            bool = true;
          }
          paramOperation = applyServerMerge(paramViewCache, paramOperation.getPath(), paramOperation.getChildren(), paramWriteTreeRef, paramNode, bool, localChildChangeAccumulator);
        }
      }
    }
    else
    {
      paramOperation = (Overwrite)paramOperation;
      if (paramOperation.getSource().isFromUser())
      {
        paramOperation = applyUserOverwrite(paramViewCache, paramOperation.getPath(), paramOperation.getSnapshot(), paramWriteTreeRef, paramNode, localChildChangeAccumulator);
      }
      else
      {
        if ((!paramOperation.getSource().isTagged()) && ((!paramViewCache.getServerCache().isFiltered()) || (paramOperation.getPath().isEmpty()))) {
          bool = false;
        } else {
          bool = true;
        }
        paramOperation = applyServerOverwrite(paramViewCache, paramOperation.getPath(), paramOperation.getSnapshot(), paramWriteTreeRef, paramNode, bool, localChildChangeAccumulator);
      }
    }
    paramWriteTreeRef = new ArrayList(localChildChangeAccumulator.getChanges());
    maybeAddValueEvent(paramViewCache, paramOperation, paramWriteTreeRef);
    return new ProcessorResult(paramOperation, paramWriteTreeRef);
  }
  
  public ViewCache revertUserWrite(ViewCache paramViewCache, Path paramPath, WriteTreeRef paramWriteTreeRef, Node paramNode, ChildChangeAccumulator paramChildChangeAccumulator)
  {
    if (paramWriteTreeRef.shadowingWrite(paramPath) != null) {
      return paramViewCache;
    }
    WriteTreeCompleteChildSource localWriteTreeCompleteChildSource = new WriteTreeCompleteChildSource(paramWriteTreeRef, paramViewCache, paramNode);
    IndexedNode localIndexedNode = paramViewCache.getEventCache().getIndexedNode();
    Object localObject = localIndexedNode;
    if ((!paramPath.isEmpty()) && (!paramPath.getFront().isPriorityChildName()))
    {
      ChildKey localChildKey = paramPath.getFront();
      Node localNode2 = paramWriteTreeRef.calcCompleteChild(localChildKey, paramViewCache.getServerCache());
      paramNode = localNode2;
      Node localNode1 = paramNode;
      if (localNode2 == null)
      {
        localNode1 = paramNode;
        if (paramViewCache.getServerCache().isCompleteForChild(localChildKey)) {
          localNode1 = localIndexedNode.getNode().getImmediateChild(localChildKey);
        }
      }
      if (localNode1 != null)
      {
        paramNode = filter.updateChild(localIndexedNode, localChildKey, localNode1, paramPath.popFront(), localWriteTreeCompleteChildSource, paramChildChangeAccumulator);
      }
      else
      {
        paramNode = (Node)localObject;
        if (localNode1 == null)
        {
          paramNode = (Node)localObject;
          if (paramViewCache.getEventCache().getNode().hasChild(localChildKey)) {
            paramNode = filter.updateChild(localIndexedNode, localChildKey, EmptyNode.Empty(), paramPath.popFront(), localWriteTreeCompleteChildSource, paramChildChangeAccumulator);
          }
        }
      }
      paramPath = paramNode;
      if (paramNode.getNode().isEmpty())
      {
        paramPath = paramNode;
        if (paramViewCache.getServerCache().isFullyInitialized())
        {
          localObject = paramWriteTreeRef.calcCompleteEventCache(paramViewCache.getCompleteServerSnap());
          paramPath = paramNode;
          if (((Node)localObject).isLeafNode())
          {
            paramPath = IndexedNode.from((Node)localObject, filter.getIndex());
            paramPath = filter.updateFullNode(paramNode, paramPath, paramChildChangeAccumulator);
          }
        }
      }
    }
    else
    {
      if (paramViewCache.getServerCache().isFullyInitialized()) {
        paramPath = paramWriteTreeRef.calcCompleteEventCache(paramViewCache.getCompleteServerSnap());
      } else {
        paramPath = paramWriteTreeRef.calcCompleteEventChildren(paramViewCache.getServerCache().getNode());
      }
      paramPath = IndexedNode.from(paramPath, filter.getIndex());
      paramPath = filter.updateFullNode(localIndexedNode, paramPath, paramChildChangeAccumulator);
    }
    boolean bool;
    if ((!paramViewCache.getServerCache().isFullyInitialized()) && (paramWriteTreeRef.shadowingWrite(Path.getEmptyPath()) == null)) {
      bool = false;
    } else {
      bool = true;
    }
    return paramViewCache.updateEventSnap(paramPath, bool, filter.filtersNodes());
  }
  
  public static class ProcessorResult
  {
    public final List<Change> changes;
    public final ViewCache viewCache;
    
    public ProcessorResult(ViewCache paramViewCache, List paramList)
    {
      viewCache = paramViewCache;
      changes = paramList;
    }
  }
  
  private static class WriteTreeCompleteChildSource
    implements NodeFilter.CompleteChildSource
  {
    private final Node optCompleteServerCache;
    private final ViewCache viewCache;
    private final WriteTreeRef writes;
    
    public WriteTreeCompleteChildSource(WriteTreeRef paramWriteTreeRef, ViewCache paramViewCache, Node paramNode)
    {
      writes = paramWriteTreeRef;
      viewCache = paramViewCache;
      optCompleteServerCache = paramNode;
    }
    
    public NamedNode getChildAfterChild(Index paramIndex, NamedNode paramNamedNode, boolean paramBoolean)
    {
      Node localNode = optCompleteServerCache;
      if (localNode == null) {
        localNode = viewCache.getCompleteServerSnap();
      }
      return writes.calcNextNodeAfterPost(localNode, paramNamedNode, paramBoolean, paramIndex);
    }
    
    public Node getCompleteChild(ChildKey paramChildKey)
    {
      Object localObject = viewCache.getEventCache();
      if (((CacheNode)localObject).isCompleteForChild(paramChildKey)) {
        return ((CacheNode)localObject).getNode().getImmediateChild(paramChildKey);
      }
      localObject = optCompleteServerCache;
      if (localObject != null) {
        localObject = new CacheNode(IndexedNode.from((Node)localObject, KeyIndex.getInstance()), true, false);
      } else {
        localObject = viewCache.getServerCache();
      }
      return writes.calcCompleteChild(paramChildKey, (CacheNode)localObject);
    }
  }
}
