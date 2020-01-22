package com.google.android.android.common.util;

public abstract interface Clock
{
  public abstract long currentThreadTimeMillis();
  
  public abstract long currentTimeMillis();
  
  public abstract long elapsedRealtime();
  
  public abstract long nanoTime();
}
