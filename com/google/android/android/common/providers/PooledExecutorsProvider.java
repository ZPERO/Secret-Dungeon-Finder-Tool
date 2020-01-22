package com.google.android.android.common.providers;

import java.util.concurrent.ScheduledExecutorService;

public class PooledExecutorsProvider
{
  private static PooledExecutorFactory zzey;
  
  private PooledExecutorsProvider() {}
  
  public static PooledExecutorFactory getInstance()
  {
    try
    {
      if (zzey == null) {
        zzey = new UnsignedInteger();
      }
      PooledExecutorFactory localPooledExecutorFactory = zzey;
      return localPooledExecutorFactory;
    }
    catch (Throwable localThrowable)
    {
      throw localThrowable;
    }
  }
  
  public abstract interface PooledExecutorFactory
  {
    public abstract ScheduledExecutorService newSingleThreadScheduledExecutor();
  }
}
