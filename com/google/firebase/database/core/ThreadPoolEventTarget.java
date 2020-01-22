package com.google.firebase.database.core;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

class ThreadPoolEventTarget
  implements EventTarget
{
  private final ThreadPoolExecutor executor;
  
  public ThreadPoolEventTarget(final ThreadFactory paramThreadFactory, final ThreadInitializer paramThreadInitializer)
  {
    LinkedBlockingQueue localLinkedBlockingQueue = new LinkedBlockingQueue();
    executor = new ThreadPoolExecutor(1, 1, 3L, TimeUnit.SECONDS, localLinkedBlockingQueue, new ThreadFactory()
    {
      public Thread newThread(Runnable paramAnonymousRunnable)
      {
        paramAnonymousRunnable = paramThreadFactory.newThread(paramAnonymousRunnable);
        paramThreadInitializer.setName(paramAnonymousRunnable, "FirebaseDatabaseEventTarget");
        paramThreadInitializer.setDaemon(paramAnonymousRunnable, true);
        return paramAnonymousRunnable;
      }
    });
  }
  
  public void postEvent(Runnable paramRunnable)
  {
    executor.execute(paramRunnable);
  }
  
  public void restart()
  {
    executor.setCorePoolSize(1);
  }
  
  public void shutdown()
  {
    executor.setCorePoolSize(0);
  }
}
