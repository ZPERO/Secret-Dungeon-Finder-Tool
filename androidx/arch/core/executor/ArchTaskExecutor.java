package androidx.arch.core.executor;

import java.util.concurrent.Executor;

public class ArchTaskExecutor
  extends TaskExecutor
{
  private static final Executor sIOThreadExecutor = new Executor()
  {
    public void execute(Runnable paramAnonymousRunnable)
    {
      ArchTaskExecutor.getInstance().executeOnDiskIO(paramAnonymousRunnable);
    }
  };
  private static volatile ArchTaskExecutor sInstance;
  private static final Executor sMainThreadExecutor = new Executor()
  {
    public void execute(Runnable paramAnonymousRunnable)
    {
      ArchTaskExecutor.getInstance().postToMainThread(paramAnonymousRunnable);
    }
  };
  private TaskExecutor mDefaultTaskExecutor = new DefaultTaskExecutor();
  private TaskExecutor mDelegate = mDefaultTaskExecutor;
  
  private ArchTaskExecutor() {}
  
  public static Executor getIOThreadExecutor()
  {
    return sIOThreadExecutor;
  }
  
  public static ArchTaskExecutor getInstance()
  {
    if (sInstance != null) {
      return sInstance;
    }
    try
    {
      if (sInstance == null) {
        sInstance = new ArchTaskExecutor();
      }
      return sInstance;
    }
    catch (Throwable localThrowable)
    {
      throw localThrowable;
    }
  }
  
  public static Executor getMainThreadExecutor()
  {
    return sMainThreadExecutor;
  }
  
  public void executeOnDiskIO(Runnable paramRunnable)
  {
    mDelegate.executeOnDiskIO(paramRunnable);
  }
  
  public boolean isMainThread()
  {
    return mDelegate.isMainThread();
  }
  
  public void postToMainThread(Runnable paramRunnable)
  {
    mDelegate.postToMainThread(paramRunnable);
  }
  
  public void setDelegate(TaskExecutor paramTaskExecutor)
  {
    TaskExecutor localTaskExecutor = paramTaskExecutor;
    if (paramTaskExecutor == null) {
      localTaskExecutor = mDefaultTaskExecutor;
    }
    mDelegate = localTaskExecutor;
  }
}
