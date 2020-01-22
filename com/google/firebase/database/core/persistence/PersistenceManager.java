package com.google.firebase.database.core.persistence;

import com.google.firebase.database.core.CompoundWrite;
import com.google.firebase.database.core.Path;
import com.google.firebase.database.core.view.CacheNode;
import com.google.firebase.database.core.view.QuerySpec;
import com.google.firebase.database.snapshot.Node;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

public abstract interface PersistenceManager
{
  public abstract void applyUserWriteToServerCache(Path paramPath, CompoundWrite paramCompoundWrite);
  
  public abstract void applyUserWriteToServerCache(Path paramPath, Node paramNode);
  
  public abstract List loadUserWrites();
  
  public abstract void removeAllUserWrites();
  
  public abstract void removeUserWrite(long paramLong);
  
  public abstract Object runInTransaction(Callable paramCallable);
  
  public abstract void saveUserMerge(Path paramPath, CompoundWrite paramCompoundWrite, long paramLong);
  
  public abstract void saveUserOverwrite(Path paramPath, Node paramNode, long paramLong);
  
  public abstract CacheNode serverCache(QuerySpec paramQuerySpec);
  
  public abstract void setQueryActive(QuerySpec paramQuerySpec);
  
  public abstract void setQueryComplete(QuerySpec paramQuerySpec);
  
  public abstract void setQueryInactive(QuerySpec paramQuerySpec);
  
  public abstract void setTrackedQueryKeys(QuerySpec paramQuerySpec, Set paramSet);
  
  public abstract void updateServerCache(Path paramPath, CompoundWrite paramCompoundWrite);
  
  public abstract void updateServerCache(QuerySpec paramQuerySpec, Node paramNode);
  
  public abstract void updateTrackedQueryKeys(QuerySpec paramQuerySpec, Set paramSet1, Set paramSet2);
}
