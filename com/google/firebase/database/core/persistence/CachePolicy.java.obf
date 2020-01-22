package com.google.firebase.database.core.persistence;

public abstract interface CachePolicy
{
  public static final CachePolicy NONE = new CachePolicy()
  {
    public long getMaxNumberOfQueriesToKeep()
    {
      return Long.MAX_VALUE;
    }
    
    public float getPercentOfQueriesToPruneAtOnce()
    {
      return 0.0F;
    }
    
    public boolean shouldCheckCacheSize(long paramAnonymousLong)
    {
      return false;
    }
    
    public boolean shouldPrune(long paramAnonymousLong1, long paramAnonymousLong2)
    {
      return false;
    }
  };
  
  public abstract long getMaxNumberOfQueriesToKeep();
  
  public abstract float getPercentOfQueriesToPruneAtOnce();
  
  public abstract boolean shouldCheckCacheSize(long paramLong);
  
  public abstract boolean shouldPrune(long paramLong1, long paramLong2);
}
