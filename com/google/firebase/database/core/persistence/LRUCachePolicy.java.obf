package com.google.firebase.database.core.persistence;

public class LRUCachePolicy
  implements CachePolicy
{
  private static final long MAX_NUMBER_OF_PRUNABLE_QUERIES_TO_KEEP = 1000L;
  private static final float PERCENT_OF_QUERIES_TO_PRUNE_AT_ONCE = 0.2F;
  private static final long SERVER_UPDATES_BETWEEN_CACHE_SIZE_CHECKS = 1000L;
  public final long maxSizeBytes;
  
  public LRUCachePolicy(long paramLong)
  {
    maxSizeBytes = paramLong;
  }
  
  public long getMaxNumberOfQueriesToKeep()
  {
    return 1000L;
  }
  
  public float getPercentOfQueriesToPruneAtOnce()
  {
    return 0.2F;
  }
  
  public boolean shouldCheckCacheSize(long paramLong)
  {
    return paramLong > 1000L;
  }
  
  public boolean shouldPrune(long paramLong1, long paramLong2)
  {
    return (paramLong1 > maxSizeBytes) || (paramLong2 > 1000L);
  }
}
