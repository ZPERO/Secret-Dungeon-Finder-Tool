package com.google.android.android.tasks;

import com.google.android.gms.tasks.zzq;
import java.util.concurrent.Executor;

final class SyncCampaign<TResult>
  implements zzq<TResult>
{
  private final Executor executor;
  private final Object mLock = new Object();
  private OnCanceledListener state;
  
  public SyncCampaign(Executor paramExecutor, OnCanceledListener paramOnCanceledListener)
  {
    executor = paramExecutor;
    state = paramOnCanceledListener;
  }
  
  public final void cancel()
  {
    Object localObject = mLock;
    try
    {
      state = null;
      return;
    }
    catch (Throwable localThrowable)
    {
      throw localThrowable;
    }
  }
  
  public final void onComplete(Task paramTask)
  {
    if (paramTask.isCanceled())
    {
      paramTask = mLock;
      try
      {
        if (state == null) {
          return;
        }
        executor.execute(new SplashScreen.5.1(this));
        return;
      }
      catch (Throwable localThrowable)
      {
        throw localThrowable;
      }
    }
  }
}
