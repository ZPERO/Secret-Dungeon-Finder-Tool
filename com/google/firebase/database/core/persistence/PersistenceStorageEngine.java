package com.google.firebase.database.core.persistence;

import com.google.firebase.database.core.CompoundWrite;
import com.google.firebase.database.core.Path;
import com.google.firebase.database.snapshot.Node;
import java.util.List;
import java.util.Set;

public abstract interface PersistenceStorageEngine
{
  public abstract void beginTransaction();
  
  public abstract void close();
  
  public abstract void deleteTrackedQuery(long paramLong);
  
  public abstract void endTransaction();
  
  public abstract List loadTrackedQueries();
  
  public abstract Set loadTrackedQueryKeys(long paramLong);
  
  public abstract Set loadTrackedQueryKeys(Set paramSet);
  
  public abstract List loadUserWrites();
  
  public abstract void mergeIntoServerCache(Path paramPath, CompoundWrite paramCompoundWrite);
  
  public abstract void mergeIntoServerCache(Path paramPath, Node paramNode);
  
  public abstract void overwriteServerCache(Path paramPath, Node paramNode);
  
  public abstract void pruneCache(Path paramPath, PruneForest paramPruneForest);
  
  public abstract void removeAllUserWrites();
  
  public abstract void removeUserWrite(long paramLong);
  
  public abstract void resetPreviouslyActiveTrackedQueries(long paramLong);
  
  public abstract void saveTrackedQuery(TrackedQuery paramTrackedQuery);
  
  public abstract void saveTrackedQueryKeys(long paramLong, Set paramSet);
  
  public abstract void saveUserMerge(Path paramPath, CompoundWrite paramCompoundWrite, long paramLong);
  
  public abstract void saveUserOverwrite(Path paramPath, Node paramNode, long paramLong);
  
  public abstract Node serverCache(Path paramPath);
  
  public abstract long serverCacheEstimatedSizeInBytes();
  
  public abstract void setTransactionSuccessful();
  
  public abstract void updateTrackedQueryKeys(long paramLong, Set paramSet1, Set paramSet2);
}
