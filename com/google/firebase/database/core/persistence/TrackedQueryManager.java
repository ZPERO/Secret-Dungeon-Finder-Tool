package com.google.firebase.database.core.persistence;

import com.google.firebase.database.collection.ImmutableSortedMap;
import com.google.firebase.database.core.Path;
import com.google.firebase.database.core.utilities.Clock;
import com.google.firebase.database.core.utilities.ImmutableTree;
import com.google.firebase.database.core.utilities.ImmutableTree.TreeVisitor;
import com.google.firebase.database.core.utilities.Predicate;
import com.google.firebase.database.core.utilities.Utilities;
import com.google.firebase.database.core.view.QueryParams;
import com.google.firebase.database.core.view.QuerySpec;
import com.google.firebase.database.logging.LogWrapper;
import com.google.firebase.database.snapshot.ChildKey;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class TrackedQueryManager
{
  private static final Predicate<Map<QueryParams, TrackedQuery>> HAS_ACTIVE_DEFAULT_PREDICATE = new Predicate()
  {
    public boolean evaluate(Map paramAnonymousMap)
    {
      paramAnonymousMap = (TrackedQuery)paramAnonymousMap.get(QueryParams.DEFAULT_PARAMS);
      return (paramAnonymousMap != null) && (active);
    }
  };
  private static final Predicate<Map<QueryParams, TrackedQuery>> HAS_DEFAULT_COMPLETE_PREDICATE = new Predicate()
  {
    public boolean evaluate(Map paramAnonymousMap)
    {
      paramAnonymousMap = (TrackedQuery)paramAnonymousMap.get(QueryParams.DEFAULT_PARAMS);
      return (paramAnonymousMap != null) && (complete);
    }
  };
  private static final Predicate<TrackedQuery> IS_QUERY_PRUNABLE_PREDICATE = new Predicate()
  {
    public boolean evaluate(TrackedQuery paramAnonymousTrackedQuery)
    {
      return active ^ true;
    }
  };
  private static final Predicate<TrackedQuery> IS_QUERY_UNPRUNABLE_PREDICATE = new Predicate()
  {
    public boolean evaluate(TrackedQuery paramAnonymousTrackedQuery)
    {
      return TrackedQueryManager.IS_QUERY_PRUNABLE_PREDICATE.evaluate(paramAnonymousTrackedQuery) ^ true;
    }
  };
  private final Clock clock;
  private long currentQueryId = 0L;
  private final LogWrapper logger;
  private final PersistenceStorageEngine storageLayer;
  private ImmutableTree<Map<QueryParams, TrackedQuery>> trackedQueryTree;
  
  public TrackedQueryManager(PersistenceStorageEngine paramPersistenceStorageEngine, LogWrapper paramLogWrapper, Clock paramClock)
  {
    storageLayer = paramPersistenceStorageEngine;
    logger = paramLogWrapper;
    clock = paramClock;
    trackedQueryTree = new ImmutableTree(null);
    resetPreviouslyActiveTrackedQueries();
    paramPersistenceStorageEngine = storageLayer.loadTrackedQueries().iterator();
    while (paramPersistenceStorageEngine.hasNext())
    {
      paramLogWrapper = (TrackedQuery)paramPersistenceStorageEngine.next();
      currentQueryId = Math.max(zoom + 1L, currentQueryId);
      cacheTrackedQuery(paramLogWrapper);
    }
  }
  
  private static void assertValidTrackedQuery(QuerySpec paramQuerySpec)
  {
    boolean bool;
    if ((paramQuerySpec.loadsAllData()) && (!paramQuerySpec.isDefault())) {
      bool = false;
    } else {
      bool = true;
    }
    Utilities.hardAssert(bool, "Can't have tracked non-default query that loads all data");
  }
  
  private void cacheTrackedQuery(TrackedQuery paramTrackedQuery)
  {
    throw new Runtime("d2j fail translate: java.lang.RuntimeException: fail exe a7 = a6\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.exec(BaseAnalyze.java:92)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.exec(BaseAnalyze.java:1)\n\tat com.googlecode.dex2jar.ir.ts.Cfg.dfs(Cfg.java:255)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.analyze0(BaseAnalyze.java:75)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.analyze(BaseAnalyze.java:69)\n\tat com.googlecode.dex2jar.ir.ts.UnSSATransformer.transform(UnSSATransformer.java:274)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:163)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\nCaused by: java.lang.NullPointerException\n");
  }
  
  private static long calculateCountToPrune(CachePolicy paramCachePolicy, long paramLong)
  {
    float f = paramCachePolicy.getPercentOfQueriesToPruneAtOnce();
    return paramLong - Math.min(Math.floor((float)paramLong * (1.0F - f)), paramCachePolicy.getMaxNumberOfQueriesToKeep());
  }
  
  private Set filteredQueryIdsAtPath(Path paramPath)
  {
    HashSet localHashSet = new HashSet();
    paramPath = (Map)trackedQueryTree.readLong(paramPath);
    if (paramPath != null)
    {
      paramPath = paramPath.values().iterator();
      while (paramPath.hasNext())
      {
        TrackedQuery localTrackedQuery = (TrackedQuery)paramPath.next();
        if (!querySpec.loadsAllData()) {
          localHashSet.add(Long.valueOf(zoom));
        }
      }
    }
    return localHashSet;
  }
  
  private List getQueriesMatching(Predicate paramPredicate)
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator1 = trackedQueryTree.iterator();
    while (localIterator1.hasNext())
    {
      Iterator localIterator2 = ((Map)((Map.Entry)localIterator1.next()).getValue()).values().iterator();
      while (localIterator2.hasNext())
      {
        TrackedQuery localTrackedQuery = (TrackedQuery)localIterator2.next();
        if (paramPredicate.evaluate(localTrackedQuery)) {
          localArrayList.add(localTrackedQuery);
        }
      }
    }
    return localArrayList;
  }
  
  private boolean includedInDefaultCompleteQuery(Path paramPath)
  {
    return trackedQueryTree.findRootMostMatchingPath(paramPath, HAS_DEFAULT_COMPLETE_PREDICATE) != null;
  }
  
  private static QuerySpec normalizeQuery(QuerySpec paramQuerySpec)
  {
    QuerySpec localQuerySpec = paramQuerySpec;
    if (paramQuerySpec.loadsAllData()) {
      localQuerySpec = QuerySpec.defaultQueryAtPath(paramQuerySpec.getPath());
    }
    return localQuerySpec;
  }
  
  private void resetPreviouslyActiveTrackedQueries()
  {
    try
    {
      storageLayer.beginTransaction();
      storageLayer.resetPreviouslyActiveTrackedQueries(clock.millis());
      storageLayer.setTransactionSuccessful();
      storageLayer.endTransaction();
      return;
    }
    catch (Throwable localThrowable)
    {
      storageLayer.endTransaction();
      throw localThrowable;
    }
  }
  
  private void saveTrackedQuery(TrackedQuery paramTrackedQuery)
  {
    cacheTrackedQuery(paramTrackedQuery);
    storageLayer.saveTrackedQuery(paramTrackedQuery);
  }
  
  private void setQueryActiveFlag(QuerySpec paramQuerySpec, boolean paramBoolean)
  {
    paramQuerySpec = normalizeQuery(paramQuerySpec);
    TrackedQuery localTrackedQuery = findTrackedQuery(paramQuerySpec);
    long l1 = clock.millis();
    if (localTrackedQuery != null)
    {
      paramQuerySpec = localTrackedQuery.updateLastUse(l1).setActiveState(paramBoolean);
    }
    else
    {
      long l2 = currentQueryId;
      currentQueryId = (1L + l2);
      paramQuerySpec = new TrackedQuery(l2, paramQuerySpec, l1, false, paramBoolean);
    }
    saveTrackedQuery(paramQuerySpec);
  }
  
  public long countOfPrunableQueries()
  {
    return getQueriesMatching(IS_QUERY_PRUNABLE_PREDICATE).size();
  }
  
  public void ensureCompleteTrackedQuery(Path paramPath)
  {
    if (!includedInDefaultCompleteQuery(paramPath))
    {
      paramPath = QuerySpec.defaultQueryAtPath(paramPath);
      TrackedQuery localTrackedQuery = findTrackedQuery(paramPath);
      if (localTrackedQuery == null)
      {
        long l = currentQueryId;
        currentQueryId = (1L + l);
        paramPath = new TrackedQuery(l, paramPath, clock.millis(), true, false);
      }
      else
      {
        paramPath = localTrackedQuery.setComplete();
      }
      saveTrackedQuery(paramPath);
    }
  }
  
  public TrackedQuery findTrackedQuery(QuerySpec paramQuerySpec)
  {
    paramQuerySpec = normalizeQuery(paramQuerySpec);
    Map localMap = (Map)trackedQueryTree.readLong(paramQuerySpec.getPath());
    if (localMap != null) {
      return (TrackedQuery)localMap.get(paramQuerySpec.getParams());
    }
    return null;
  }
  
  public Set getKnownCompleteChildren(Path paramPath)
  {
    HashSet localHashSet = new HashSet();
    Object localObject1 = filteredQueryIdsAtPath(paramPath);
    if (!((Set)localObject1).isEmpty()) {
      localHashSet.addAll(storageLayer.loadTrackedQueryKeys((Set)localObject1));
    }
    paramPath = trackedQueryTree.subtree(paramPath).getChildren().iterator();
    while (paramPath.hasNext())
    {
      Object localObject2 = (Map.Entry)paramPath.next();
      localObject1 = (ChildKey)((Map.Entry)localObject2).getKey();
      localObject2 = (ImmutableTree)((Map.Entry)localObject2).getValue();
      if ((((ImmutableTree)localObject2).getValue() != null) && (HAS_DEFAULT_COMPLETE_PREDICATE.evaluate((Map)((ImmutableTree)localObject2).getValue()))) {
        localHashSet.add(localObject1);
      }
    }
    return localHashSet;
  }
  
  public boolean hasActiveDefaultQuery(Path paramPath)
  {
    return trackedQueryTree.rootMostValueMatching(paramPath, HAS_ACTIVE_DEFAULT_PREDICATE) != null;
  }
  
  public boolean isQueryComplete(QuerySpec paramQuerySpec)
  {
    if (includedInDefaultCompleteQuery(paramQuerySpec.getPath())) {
      return true;
    }
    if (paramQuerySpec.loadsAllData()) {
      return false;
    }
    Map localMap = (Map)trackedQueryTree.readLong(paramQuerySpec.getPath());
    return (localMap != null) && (localMap.containsKey(paramQuerySpec.getParams())) && (getgetParamscomplete);
  }
  
  public PruneForest pruneOldQueries(CachePolicy paramCachePolicy)
  {
    Object localObject1 = getQueriesMatching(IS_QUERY_PRUNABLE_PREDICATE);
    long l = calculateCountToPrune(paramCachePolicy, ((List)localObject1).size());
    paramCachePolicy = new PruneForest();
    Object localObject2;
    StringBuilder localStringBuilder;
    if (logger.logsDebug())
    {
      localObject2 = logger;
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("Pruning old queries.  Prunable: ");
      localStringBuilder.append(((List)localObject1).size());
      localStringBuilder.append(" Count to prune: ");
      localStringBuilder.append(l);
      ((LogWrapper)localObject2).debug(localStringBuilder.toString(), new Object[0]);
    }
    Collections.sort((List)localObject1, new Comparator()
    {
      public int compare(TrackedQuery paramAnonymousTrackedQuery1, TrackedQuery paramAnonymousTrackedQuery2)
      {
        return Utilities.compareLongs(lastUse, lastUse);
      }
    });
    int i = 0;
    while (i < l)
    {
      localObject2 = (TrackedQuery)((List)localObject1).get(i);
      paramCachePolicy = paramCachePolicy.prune(querySpec.getPath());
      removeTrackedQuery(querySpec);
      i += 1;
    }
    i = (int)l;
    while (i < ((List)localObject1).size())
    {
      paramCachePolicy = paramCachePolicy.keep(getquerySpec.getPath());
      i += 1;
    }
    localObject1 = getQueriesMatching(IS_QUERY_UNPRUNABLE_PREDICATE);
    if (logger.logsDebug())
    {
      localObject2 = logger;
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("Unprunable queries: ");
      localStringBuilder.append(((List)localObject1).size());
      ((LogWrapper)localObject2).debug(localStringBuilder.toString(), new Object[0]);
    }
    localObject1 = ((List)localObject1).iterator();
    while (((Iterator)localObject1).hasNext()) {
      paramCachePolicy = paramCachePolicy.keep(nextquerySpec.getPath());
    }
    return paramCachePolicy;
  }
  
  public void removeTrackedQuery(QuerySpec paramQuerySpec)
  {
    paramQuerySpec = normalizeQuery(paramQuerySpec);
    Object localObject = findTrackedQuery(paramQuerySpec);
    storageLayer.deleteTrackedQuery(zoom);
    localObject = (Map)trackedQueryTree.readLong(paramQuerySpec.getPath());
    ((Map)localObject).remove(paramQuerySpec.getParams());
    if (((Map)localObject).isEmpty()) {
      trackedQueryTree = trackedQueryTree.remove(paramQuerySpec.getPath());
    }
  }
  
  public void setQueriesComplete(Path paramPath)
  {
    trackedQueryTree.subtree(paramPath).foreach(new ImmutableTree.TreeVisitor()
    {
      public Void onNodeValue(Path paramAnonymousPath, Map paramAnonymousMap, Void paramAnonymousVoid)
      {
        paramAnonymousPath = paramAnonymousMap.entrySet().iterator();
        while (paramAnonymousPath.hasNext())
        {
          paramAnonymousMap = (TrackedQuery)((Map.Entry)paramAnonymousPath.next()).getValue();
          if (!complete) {
            TrackedQueryManager.this.saveTrackedQuery(paramAnonymousMap.setComplete());
          }
        }
        return null;
      }
    });
  }
  
  public void setQueryActive(QuerySpec paramQuerySpec)
  {
    setQueryActiveFlag(paramQuerySpec, true);
  }
  
  public void setQueryCompleteIfExists(QuerySpec paramQuerySpec)
  {
    paramQuerySpec = findTrackedQuery(normalizeQuery(paramQuerySpec));
    if ((paramQuerySpec != null) && (!complete)) {
      saveTrackedQuery(paramQuerySpec.setComplete());
    }
  }
  
  public void setQueryInactive(QuerySpec paramQuerySpec)
  {
    setQueryActiveFlag(paramQuerySpec, false);
  }
  
  void verifyCache()
  {
    List localList = storageLayer.loadTrackedQueries();
    final ArrayList localArrayList = new ArrayList();
    trackedQueryTree.foreach(new ImmutableTree.TreeVisitor()
    {
      public Void onNodeValue(Path paramAnonymousPath, Map paramAnonymousMap, Void paramAnonymousVoid)
      {
        paramAnonymousPath = paramAnonymousMap.values().iterator();
        while (paramAnonymousPath.hasNext())
        {
          paramAnonymousMap = (TrackedQuery)paramAnonymousPath.next();
          localArrayList.add(paramAnonymousMap);
        }
        return null;
      }
    });
    Collections.sort(localArrayList, new Comparator()
    {
      public int compare(TrackedQuery paramAnonymousTrackedQuery1, TrackedQuery paramAnonymousTrackedQuery2)
      {
        return Utilities.compareLongs(zoom, zoom);
      }
    });
    boolean bool = localList.equals(localArrayList);
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Tracked queries out of sync.  Tracked queries: ");
    localStringBuilder.append(localArrayList);
    localStringBuilder.append(" Stored queries: ");
    localStringBuilder.append(localList);
    Utilities.hardAssert(bool, localStringBuilder.toString());
  }
}
