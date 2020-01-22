package com.google.firebase.database.core.view;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.core.EventRegistration;
import com.google.firebase.database.core.Path;
import com.google.firebase.database.core.WriteTreeRef;
import com.google.firebase.database.core.operation.Operation;
import com.google.firebase.database.core.operation.Operation.OperationType;
import com.google.firebase.database.core.operation.OperationSource;
import com.google.firebase.database.core.view.filter.IndexedFilter;
import com.google.firebase.database.core.view.filter.NodeFilter;
import com.google.firebase.database.snapshot.EmptyNode;
import com.google.firebase.database.snapshot.IndexedNode;
import com.google.firebase.database.snapshot.NamedNode;
import com.google.firebase.database.snapshot.Node;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class View
{
  private final EventGenerator eventGenerator;
  private final List<EventRegistration> eventRegistrations;
  private final ViewProcessor processor;
  private final QuerySpec query;
  private ViewCache viewCache;
  
  public View(QuerySpec paramQuerySpec, ViewCache paramViewCache)
  {
    query = paramQuerySpec;
    Object localObject = new IndexedFilter(paramQuerySpec.getIndex());
    NodeFilter localNodeFilter = paramQuerySpec.getParams().getNodeFilter();
    processor = new ViewProcessor(localNodeFilter);
    CacheNode localCacheNode = paramViewCache.getServerCache();
    paramViewCache = paramViewCache.getEventCache();
    IndexedNode localIndexedNode2 = IndexedNode.from(EmptyNode.Empty(), paramQuerySpec.getIndex());
    IndexedNode localIndexedNode1 = ((IndexedFilter)localObject).updateFullNode(localIndexedNode2, localCacheNode.getIndexedNode(), null);
    localIndexedNode2 = localNodeFilter.updateFullNode(localIndexedNode2, paramViewCache.getIndexedNode(), null);
    localObject = new CacheNode(localIndexedNode1, localCacheNode.isFullyInitialized(), ((IndexedFilter)localObject).filtersNodes());
    viewCache = new ViewCache(new CacheNode(localIndexedNode2, paramViewCache.isFullyInitialized(), localNodeFilter.filtersNodes()), (CacheNode)localObject);
    eventRegistrations = new ArrayList();
    eventGenerator = new EventGenerator(paramQuerySpec);
  }
  
  private List generateEventsForChanges(List paramList, IndexedNode paramIndexedNode, EventRegistration paramEventRegistration)
  {
    if (paramEventRegistration == null) {
      paramEventRegistration = eventRegistrations;
    } else {
      paramEventRegistration = Arrays.asList(new EventRegistration[] { paramEventRegistration });
    }
    return eventGenerator.generateEventsForChanges(paramList, paramIndexedNode, paramEventRegistration);
  }
  
  public void addEventRegistration(EventRegistration paramEventRegistration)
  {
    eventRegistrations.add(paramEventRegistration);
  }
  
  public OperationResult applyOperation(Operation paramOperation, WriteTreeRef paramWriteTreeRef, Node paramNode)
  {
    if (paramOperation.getType() == Operation.OperationType.Merge) {
      paramOperation.getSource().getQueryParams();
    }
    ViewCache localViewCache = viewCache;
    paramOperation = processor.applyOperation(localViewCache, paramOperation, paramWriteTreeRef, paramNode);
    viewCache = viewCache;
    return new OperationResult(generateEventsForChanges(changes, viewCache.getEventCache().getIndexedNode(), null), changes);
  }
  
  public Node getCompleteNode()
  {
    return viewCache.getCompleteEventSnap();
  }
  
  public Node getCompleteServerCache(Path paramPath)
  {
    Node localNode = viewCache.getCompleteServerSnap();
    if ((localNode != null) && ((query.loadsAllData()) || ((!paramPath.isEmpty()) && (!localNode.getImmediateChild(paramPath.getFront()).isEmpty())))) {
      return localNode.getChild(paramPath);
    }
    return null;
  }
  
  public Node getEventCache()
  {
    return viewCache.getEventCache().getNode();
  }
  
  List getEventRegistrations()
  {
    return eventRegistrations;
  }
  
  public List getInitialEvents(EventRegistration paramEventRegistration)
  {
    CacheNode localCacheNode = viewCache.getEventCache();
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = localCacheNode.getNode().iterator();
    while (localIterator.hasNext())
    {
      NamedNode localNamedNode = (NamedNode)localIterator.next();
      localArrayList.add(Change.childAddedChange(localNamedNode.getName(), localNamedNode.getNode()));
    }
    if (localCacheNode.isFullyInitialized()) {
      localArrayList.add(Change.valueChange(localCacheNode.getIndexedNode()));
    }
    return generateEventsForChanges(localArrayList, localCacheNode.getIndexedNode(), paramEventRegistration);
  }
  
  public QuerySpec getQuery()
  {
    return query;
  }
  
  public Node getServerCache()
  {
    return viewCache.getServerCache().getNode();
  }
  
  public boolean isEmpty()
  {
    return eventRegistrations.isEmpty();
  }
  
  public List removeEventRegistration(EventRegistration paramEventRegistration, DatabaseError paramDatabaseError)
  {
    throw new Runtime("d2j fail translate: java.lang.RuntimeException: fail exe a4 = a3\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.exec(BaseAnalyze.java:92)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.exec(BaseAnalyze.java:1)\n\tat com.googlecode.dex2jar.ir.ts.Cfg.dfs(Cfg.java:255)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.analyze0(BaseAnalyze.java:75)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.analyze(BaseAnalyze.java:69)\n\tat com.googlecode.dex2jar.ir.ts.UnSSATransformer.transform(UnSSATransformer.java:274)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:163)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\nCaused by: java.lang.NullPointerException\n");
  }
  
  public static class OperationResult
  {
    public final List<Change> changes;
    public final List<DataEvent> events;
    
    public OperationResult(List paramList1, List paramList2)
    {
      events = paramList1;
      changes = paramList2;
    }
  }
}
