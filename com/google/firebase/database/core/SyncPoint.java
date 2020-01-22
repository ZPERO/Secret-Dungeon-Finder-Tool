package com.google.firebase.database.core;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.core.operation.Operation;
import com.google.firebase.database.core.operation.OperationSource;
import com.google.firebase.database.core.persistence.PersistenceManager;
import com.google.firebase.database.core.utilities.Pair;
import com.google.firebase.database.core.view.CacheNode;
import com.google.firebase.database.core.view.Change;
import com.google.firebase.database.core.view.Event.EventType;
import com.google.firebase.database.core.view.QueryParams;
import com.google.firebase.database.core.view.QuerySpec;
import com.google.firebase.database.core.view.View;
import com.google.firebase.database.core.view.View.OperationResult;
import com.google.firebase.database.core.view.ViewCache;
import com.google.firebase.database.snapshot.IndexedNode;
import com.google.firebase.database.snapshot.NamedNode;
import com.google.firebase.database.snapshot.Node;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class SyncPoint
{
  private final PersistenceManager persistenceManager;
  private final Map<QueryParams, View> views = new HashMap();
  
  public SyncPoint(PersistenceManager paramPersistenceManager)
  {
    persistenceManager = paramPersistenceManager;
  }
  
  private List applyOperationToView(View paramView, Operation paramOperation, WriteTreeRef paramWriteTreeRef, Node paramNode)
  {
    paramOperation = paramView.applyOperation(paramOperation, paramWriteTreeRef, paramNode);
    if (!paramView.getQuery().loadsAllData())
    {
      paramWriteTreeRef = new HashSet();
      paramNode = new HashSet();
      Iterator localIterator = changes.iterator();
      while (localIterator.hasNext())
      {
        Change localChange = (Change)localIterator.next();
        Event.EventType localEventType = localChange.getEventType();
        if (localEventType == Event.EventType.CHILD_ADDED) {
          paramNode.add(localChange.getChildKey());
        } else if (localEventType == Event.EventType.CHILD_REMOVED) {
          paramWriteTreeRef.add(localChange.getChildKey());
        }
      }
      if ((!paramNode.isEmpty()) || (!paramWriteTreeRef.isEmpty())) {
        persistenceManager.updateTrackedQueryKeys(paramView.getQuery(), paramNode, paramWriteTreeRef);
      }
    }
    return events;
  }
  
  public List addEventRegistration(EventRegistration paramEventRegistration, WriteTreeRef paramWriteTreeRef, CacheNode paramCacheNode)
  {
    QuerySpec localQuerySpec = paramEventRegistration.getQuerySpec();
    Object localObject2 = (View)views.get(localQuerySpec.getParams());
    Object localObject1 = localObject2;
    if (localObject2 == null)
    {
      if (paramCacheNode.isFullyInitialized()) {
        localObject1 = paramCacheNode.getNode();
      } else {
        localObject1 = null;
      }
      localObject2 = paramWriteTreeRef.calcCompleteEventCache((Node)localObject1);
      localObject1 = localObject2;
      boolean bool;
      if (localObject2 != null)
      {
        bool = true;
      }
      else
      {
        localObject1 = paramWriteTreeRef.calcCompleteEventChildren(paramCacheNode.getNode());
        bool = false;
      }
      localObject1 = new View(localQuerySpec, new ViewCache(new CacheNode(IndexedNode.from((Node)localObject1, localQuerySpec.getIndex()), bool, false), paramCacheNode));
      if (!localQuerySpec.loadsAllData())
      {
        paramWriteTreeRef = new HashSet();
        paramCacheNode = ((View)localObject1).getEventCache().iterator();
        while (paramCacheNode.hasNext()) {
          paramWriteTreeRef.add(((NamedNode)paramCacheNode.next()).getName());
        }
        persistenceManager.setTrackedQueryKeys(localQuerySpec, paramWriteTreeRef);
      }
      views.put(localQuerySpec.getParams(), localObject1);
    }
    ((View)localObject1).addEventRegistration(paramEventRegistration);
    return ((View)localObject1).getInitialEvents(paramEventRegistration);
  }
  
  public List applyOperation(Operation paramOperation, WriteTreeRef paramWriteTreeRef, Node paramNode)
  {
    Object localObject = paramOperation.getSource().getQueryParams();
    if (localObject != null) {
      return applyOperationToView((View)views.get(localObject), paramOperation, paramWriteTreeRef, paramNode);
    }
    localObject = new ArrayList();
    Iterator localIterator = views.entrySet().iterator();
    while (localIterator.hasNext()) {
      ((List)localObject).addAll(applyOperationToView((View)((Map.Entry)localIterator.next()).getValue(), paramOperation, paramWriteTreeRef, paramNode));
    }
    return localObject;
  }
  
  public Node getCompleteServerCache(Path paramPath)
  {
    Iterator localIterator = views.values().iterator();
    while (localIterator.hasNext())
    {
      View localView = (View)localIterator.next();
      if (localView.getCompleteServerCache(paramPath) != null) {
        return localView.getCompleteServerCache(paramPath);
      }
    }
    return null;
  }
  
  public View getCompleteView()
  {
    Iterator localIterator = views.entrySet().iterator();
    while (localIterator.hasNext())
    {
      View localView = (View)((Map.Entry)localIterator.next()).getValue();
      if (localView.getQuery().loadsAllData()) {
        return localView;
      }
    }
    return null;
  }
  
  public List getQueryViews()
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = views.entrySet().iterator();
    while (localIterator.hasNext())
    {
      View localView = (View)((Map.Entry)localIterator.next()).getValue();
      if (!localView.getQuery().loadsAllData()) {
        localArrayList.add(localView);
      }
    }
    return localArrayList;
  }
  
  Map getViews()
  {
    return views;
  }
  
  public boolean hasCompleteView()
  {
    return getCompleteView() != null;
  }
  
  public boolean isEmpty()
  {
    return views.isEmpty();
  }
  
  public Pair removeEventRegistration(QuerySpec paramQuerySpec, EventRegistration paramEventRegistration, DatabaseError paramDatabaseError)
  {
    ArrayList localArrayList1 = new ArrayList();
    ArrayList localArrayList2 = new ArrayList();
    boolean bool = hasCompleteView();
    if (paramQuerySpec.isDefault())
    {
      localObject = views.entrySet().iterator();
      while (((Iterator)localObject).hasNext())
      {
        View localView = (View)((Map.Entry)((Iterator)localObject).next()).getValue();
        localArrayList2.addAll(localView.removeEventRegistration(paramEventRegistration, paramDatabaseError));
        if (localView.isEmpty())
        {
          ((Iterator)localObject).remove();
          if (!localView.getQuery().loadsAllData()) {
            localArrayList1.add(localView.getQuery());
          }
        }
      }
    }
    Object localObject = (View)views.get(paramQuerySpec.getParams());
    if (localObject != null)
    {
      localArrayList2.addAll(((View)localObject).removeEventRegistration(paramEventRegistration, paramDatabaseError));
      if (((View)localObject).isEmpty())
      {
        views.remove(paramQuerySpec.getParams());
        if (!((View)localObject).getQuery().loadsAllData()) {
          localArrayList1.add(((View)localObject).getQuery());
        }
      }
    }
    if ((bool) && (!hasCompleteView())) {
      localArrayList1.add(QuerySpec.defaultQueryAtPath(paramQuerySpec.getPath()));
    }
    return new Pair(localArrayList1, localArrayList2);
  }
  
  public boolean viewExistsForQuery(QuerySpec paramQuerySpec)
  {
    return viewForQuery(paramQuerySpec) != null;
  }
  
  public View viewForQuery(QuerySpec paramQuerySpec)
  {
    if (paramQuerySpec.loadsAllData()) {
      return getCompleteView();
    }
    return (View)views.get(paramQuerySpec.getParams());
  }
}
