package com.google.firebase.database.core.persistence;

import com.google.firebase.database.core.CompoundWrite;
import com.google.firebase.database.core.Context;
import com.google.firebase.database.core.Path;
import com.google.firebase.database.core.utilities.Clock;
import com.google.firebase.database.core.utilities.DefaultClock;
import com.google.firebase.database.core.view.CacheNode;
import com.google.firebase.database.core.view.QuerySpec;
import com.google.firebase.database.logging.LogWrapper;
import com.google.firebase.database.snapshot.ChildKey;
import com.google.firebase.database.snapshot.EmptyNode;
import com.google.firebase.database.snapshot.IndexedNode;
import com.google.firebase.database.snapshot.Node;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Callable;

public class DefaultPersistenceManager
  implements PersistenceManager
{
  private final CachePolicy cachePolicy;
  private final LogWrapper logger;
  private long serverCacheUpdatesSinceLastPruneCheck = 0L;
  private final PersistenceStorageEngine storageLayer;
  private final TrackedQueryManager trackedQueryManager;
  
  public DefaultPersistenceManager(Context paramContext, PersistenceStorageEngine paramPersistenceStorageEngine, CachePolicy paramCachePolicy)
  {
    this(paramContext, paramPersistenceStorageEngine, paramCachePolicy, new DefaultClock());
  }
  
  public DefaultPersistenceManager(Context paramContext, PersistenceStorageEngine paramPersistenceStorageEngine, CachePolicy paramCachePolicy, Clock paramClock)
  {
    storageLayer = paramPersistenceStorageEngine;
    logger = paramContext.getLogger("Persistence");
    trackedQueryManager = new TrackedQueryManager(storageLayer, logger, paramClock);
    cachePolicy = paramCachePolicy;
  }
  
  private void doPruneCheckAfterServerUpdate()
  {
    serverCacheUpdatesSinceLastPruneCheck += 1L;
    if (cachePolicy.shouldCheckCacheSize(serverCacheUpdatesSinceLastPruneCheck))
    {
      if (logger.logsDebug()) {
        logger.debug("Reached prune check threshold.", new Object[0]);
      }
      serverCacheUpdatesSinceLastPruneCheck = 0L;
      int j = 1;
      long l3 = storageLayer.serverCacheEstimatedSizeInBytes();
      long l2 = l3;
      long l1 = l2;
      int i = j;
      Object localObject;
      StringBuilder localStringBuilder;
      if (logger.logsDebug())
      {
        localObject = logger;
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("Cache size: ");
        localStringBuilder.append(l3);
        ((LogWrapper)localObject).debug(localStringBuilder.toString(), new Object[0]);
        i = j;
        l1 = l2;
      }
      while ((i != 0) && (cachePolicy.shouldPrune(l1, trackedQueryManager.countOfPrunableQueries())))
      {
        localObject = trackedQueryManager.pruneOldQueries(cachePolicy);
        if (((PruneForest)localObject).prunesAnything())
        {
          storageLayer.pruneCache(Path.getEmptyPath(), (PruneForest)localObject);
          j = i;
        }
        else
        {
          j = 0;
        }
        l3 = storageLayer.serverCacheEstimatedSizeInBytes();
        l2 = l3;
        l1 = l2;
        i = j;
        if (logger.logsDebug())
        {
          localObject = logger;
          localStringBuilder = new StringBuilder();
          localStringBuilder.append("Cache size after prune: ");
          localStringBuilder.append(l3);
          ((LogWrapper)localObject).debug(localStringBuilder.toString(), new Object[0]);
          l1 = l2;
          i = j;
        }
      }
    }
  }
  
  public void applyUserWriteToServerCache(Path paramPath, CompoundWrite paramCompoundWrite)
  {
    paramCompoundWrite = paramCompoundWrite.iterator();
    while (paramCompoundWrite.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)paramCompoundWrite.next();
      applyUserWriteToServerCache(paramPath.child((Path)localEntry.getKey()), (Node)localEntry.getValue());
    }
  }
  
  public void applyUserWriteToServerCache(Path paramPath, Node paramNode)
  {
    if (!trackedQueryManager.hasActiveDefaultQuery(paramPath))
    {
      storageLayer.overwriteServerCache(paramPath, paramNode);
      trackedQueryManager.ensureCompleteTrackedQuery(paramPath);
    }
  }
  
  public List loadUserWrites()
  {
    return storageLayer.loadUserWrites();
  }
  
  public void removeAllUserWrites()
  {
    storageLayer.removeAllUserWrites();
  }
  
  public void removeUserWrite(long paramLong)
  {
    storageLayer.removeUserWrite(paramLong);
  }
  
  public Object runInTransaction(Callable paramCallable)
  {
    storageLayer.beginTransaction();
    try
    {
      paramCallable = paramCallable.call();
      storageLayer.setTransactionSuccessful();
      storageLayer.endTransaction();
      return paramCallable;
    }
    catch (Throwable paramCallable)
    {
      try
      {
        logger.error("Caught Throwable.", paramCallable);
        throw new RuntimeException(paramCallable);
      }
      catch (Throwable paramCallable)
      {
        storageLayer.endTransaction();
        throw paramCallable;
      }
    }
  }
  
  public void saveUserMerge(Path paramPath, CompoundWrite paramCompoundWrite, long paramLong)
  {
    storageLayer.saveUserMerge(paramPath, paramCompoundWrite, paramLong);
  }
  
  public void saveUserOverwrite(Path paramPath, Node paramNode, long paramLong)
  {
    storageLayer.saveUserOverwrite(paramPath, paramNode, paramLong);
  }
  
  public CacheNode serverCache(QuerySpec paramQuerySpec)
  {
    Object localObject1;
    boolean bool;
    if (trackedQueryManager.isQueryComplete(paramQuerySpec))
    {
      localObject1 = trackedQueryManager.findTrackedQuery(paramQuerySpec);
      if ((!paramQuerySpec.loadsAllData()) && (localObject1 != null) && (complete)) {
        localObject1 = storageLayer.loadTrackedQueryKeys(zoom);
      } else {
        localObject1 = null;
      }
      bool = true;
    }
    else
    {
      localObject1 = trackedQueryManager.getKnownCompleteChildren(paramQuerySpec.getPath());
      bool = false;
    }
    Node localNode1 = storageLayer.serverCache(paramQuerySpec.getPath());
    if (localObject1 != null)
    {
      Object localObject2 = EmptyNode.Empty();
      Iterator localIterator = ((Set)localObject1).iterator();
      Node localNode2;
      for (localObject1 = localObject2; localIterator.hasNext(); localObject1 = ((Node)localObject1).updateImmediateChild((ChildKey)localObject2, localNode2))
      {
        localObject2 = (ChildKey)localIterator.next();
        localNode2 = localNode1.getImmediateChild((ChildKey)localObject2);
      }
      paramQuerySpec = paramQuerySpec.getIndex();
      return new CacheNode(IndexedNode.from((Node)localObject1, paramQuerySpec), bool, true);
    }
    return new CacheNode(IndexedNode.from(localNode1, paramQuerySpec.getIndex()), bool, false);
  }
  
  public void setQueryActive(QuerySpec paramQuerySpec)
  {
    trackedQueryManager.setQueryActive(paramQuerySpec);
  }
  
  public void setQueryComplete(QuerySpec paramQuerySpec)
  {
    if (paramQuerySpec.loadsAllData())
    {
      trackedQueryManager.setQueriesComplete(paramQuerySpec.getPath());
      return;
    }
    trackedQueryManager.setQueryCompleteIfExists(paramQuerySpec);
  }
  
  public void setQueryInactive(QuerySpec paramQuerySpec)
  {
    trackedQueryManager.setQueryInactive(paramQuerySpec);
  }
  
  public void setTrackedQueryKeys(QuerySpec paramQuerySpec, Set paramSet)
  {
    paramQuerySpec = trackedQueryManager.findTrackedQuery(paramQuerySpec);
    storageLayer.saveTrackedQueryKeys(zoom, paramSet);
  }
  
  public void updateServerCache(Path paramPath, CompoundWrite paramCompoundWrite)
  {
    storageLayer.mergeIntoServerCache(paramPath, paramCompoundWrite);
    doPruneCheckAfterServerUpdate();
  }
  
  public void updateServerCache(QuerySpec paramQuerySpec, Node paramNode)
  {
    if (paramQuerySpec.loadsAllData()) {
      storageLayer.overwriteServerCache(paramQuerySpec.getPath(), paramNode);
    } else {
      storageLayer.mergeIntoServerCache(paramQuerySpec.getPath(), paramNode);
    }
    setQueryComplete(paramQuerySpec);
    doPruneCheckAfterServerUpdate();
  }
  
  public void updateTrackedQueryKeys(QuerySpec paramQuerySpec, Set paramSet1, Set paramSet2)
  {
    paramQuerySpec = trackedQueryManager.findTrackedQuery(paramQuerySpec);
    storageLayer.updateTrackedQueryKeys(zoom, paramSet1, paramSet2);
  }
}
