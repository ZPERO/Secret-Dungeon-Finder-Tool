package com.google.firebase.database.core;

import java.util.concurrent.ScheduledFuture;

public abstract interface RunLoop
{
  public abstract void restart();
  
  public abstract ScheduledFuture schedule(Runnable paramRunnable, long paramLong);
  
  public abstract void scheduleNow(Runnable paramRunnable);
  
  public abstract void shutdown();
}
