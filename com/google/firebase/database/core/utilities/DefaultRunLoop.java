package com.google.firebase.database.core.utilities;

import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.RunLoop;
import com.google.firebase.database.core.ThreadInitializer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public abstract class DefaultRunLoop
  implements RunLoop
{
  private ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1, new FirebaseThreadFactory(null))
  {
    /* Error */
    protected void afterExecute(Runnable paramAnonymousRunnable, Throwable paramAnonymousThrowable)
    {
      // Byte code:
      //   0: aload_0
      //   1: aload_1
      //   2: aload_2
      //   3: invokespecial 28	java/util/concurrent/ScheduledThreadPoolExecutor:afterExecute	(Ljava/lang/Runnable;Ljava/lang/Throwable;)V
      //   6: aload_2
      //   7: astore 4
      //   9: aload_2
      //   10: ifnonnull +67 -> 77
      //   13: aload_2
      //   14: astore 4
      //   16: aload_1
      //   17: instanceof 30
      //   20: ifeq +57 -> 77
      //   23: aload_1
      //   24: checkcast 30	java/util/concurrent/Future
      //   27: astore_1
      //   28: aload_1
      //   29: invokeinterface 34 1 0
      //   34: istore_3
      //   35: aload_2
      //   36: astore 4
      //   38: iload_3
      //   39: ifeq +38 -> 77
      //   42: aload_1
      //   43: invokeinterface 38 1 0
      //   48: pop
      //   49: aload_2
      //   50: astore 4
      //   52: goto +25 -> 77
      //   55: invokestatic 44	java/lang/Thread:currentThread	()Ljava/lang/Thread;
      //   58: invokevirtual 47	java/lang/Thread:interrupt	()V
      //   61: aload_2
      //   62: astore 4
      //   64: goto +13 -> 77
      //   67: astore_1
      //   68: aload_1
      //   69: invokevirtual 51	java/util/concurrent/ExecutionException:getCause	()Ljava/lang/Throwable;
      //   72: astore 4
      //   74: goto +3 -> 77
      //   77: aload 4
      //   79: ifnull +24 -> 103
      //   82: aload_0
      //   83: getfield 14	com/google/firebase/database/core/utilities/DefaultRunLoop$1:this$0	Lcom/google/firebase/database/core/utilities/DefaultRunLoop;
      //   86: aload 4
      //   88: invokevirtual 55	com/google/firebase/database/core/utilities/DefaultRunLoop:handleException	(Ljava/lang/Throwable;)V
      //   91: return
      //   92: astore_1
      //   93: aload_2
      //   94: astore 4
      //   96: goto -19 -> 77
      //   99: astore_1
      //   100: goto -45 -> 55
      //   103: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	104	0	this	1
      //   0	104	1	paramAnonymousRunnable	Runnable
      //   0	104	2	paramAnonymousThrowable	Throwable
      //   34	5	3	bool	boolean
      //   7	88	4	localThrowable	Throwable
      // Exception table:
      //   from	to	target	type
      //   28	35	67	java/util/concurrent/ExecutionException
      //   42	49	67	java/util/concurrent/ExecutionException
      //   28	35	92	java/util/concurrent/CancellationException
      //   42	49	92	java/util/concurrent/CancellationException
      //   28	35	99	java/lang/InterruptedException
      //   42	49	99	java/lang/InterruptedException
    }
  };
  
  public DefaultRunLoop()
  {
    executor.setKeepAliveTime(3L, TimeUnit.SECONDS);
  }
  
  public static String messageForException(Throwable paramThrowable)
  {
    if ((paramThrowable instanceof OutOfMemoryError)) {
      return "Firebase Database encountered an OutOfMemoryError. You may need to reduce the amount of data you are syncing to the client (e.g. by using queries or syncing a deeper path). See https://firebase.google.com/docs/database/ios/structure-data#best_practices_for_data_structure and https://firebase.google.com/docs/database/android/retrieve-data#filtering_data";
    }
    if ((paramThrowable instanceof DatabaseException)) {
      return "";
    }
    paramThrowable = new StringBuilder();
    paramThrowable.append("Uncaught exception in Firebase Database runloop (");
    paramThrowable.append(FirebaseDatabase.getSdkVersion());
    paramThrowable.append("). Please report to firebase-database-client@google.com");
    return paramThrowable.toString();
  }
  
  public ScheduledExecutorService getExecutorService()
  {
    return executor;
  }
  
  protected ThreadFactory getThreadFactory()
  {
    return Executors.defaultThreadFactory();
  }
  
  protected ThreadInitializer getThreadInitializer()
  {
    return ThreadInitializer.defaultInstance;
  }
  
  public abstract void handleException(Throwable paramThrowable);
  
  public void restart()
  {
    executor.setCorePoolSize(1);
  }
  
  public ScheduledFuture schedule(Runnable paramRunnable, long paramLong)
  {
    return executor.schedule(paramRunnable, paramLong, TimeUnit.MILLISECONDS);
  }
  
  public void scheduleNow(Runnable paramRunnable)
  {
    executor.execute(paramRunnable);
  }
  
  public void shutdown()
  {
    executor.setCorePoolSize(0);
  }
  
  private class FirebaseThreadFactory
    implements ThreadFactory
  {
    private FirebaseThreadFactory() {}
    
    public Thread newThread(Runnable paramRunnable)
    {
      paramRunnable = getThreadFactory().newThread(paramRunnable);
      ThreadInitializer localThreadInitializer = getThreadInitializer();
      localThreadInitializer.setName(paramRunnable, "FirebaseDatabaseWorker");
      localThreadInitializer.setDaemon(paramRunnable, true);
      localThreadInitializer.setUncaughtExceptionHandler(paramRunnable, new Thread.UncaughtExceptionHandler()
      {
        public void uncaughtException(Thread paramAnonymousThread, Throwable paramAnonymousThrowable)
        {
          handleException(paramAnonymousThrowable);
        }
      });
      return paramRunnable;
    }
  }
}
