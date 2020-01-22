package com.google.android.android.common.util.concurrent;

import android.os.Handler;
import android.os.Looper;
import com.google.android.android.internal.common.Timer;
import java.util.concurrent.Executor;

public class HandlerExecutor
  implements Executor
{
  private final Handler handler;
  
  public HandlerExecutor(Looper paramLooper)
  {
    handler = new Timer(paramLooper);
  }
  
  public void execute(Runnable paramRunnable)
  {
    handler.post(paramRunnable);
  }
}
