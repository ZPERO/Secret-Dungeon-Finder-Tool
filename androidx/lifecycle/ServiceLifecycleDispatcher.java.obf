package androidx.lifecycle;

import android.os.Handler;

public class ServiceLifecycleDispatcher
{
  private final Handler mHandler;
  private DispatchRunnable mLastDispatchRunnable;
  private final LifecycleRegistry mRegistry;
  
  public ServiceLifecycleDispatcher(LifecycleOwner paramLifecycleOwner)
  {
    mRegistry = new LifecycleRegistry(paramLifecycleOwner);
    mHandler = new Handler();
  }
  
  private void postDispatchRunnable(Lifecycle.Event paramEvent)
  {
    DispatchRunnable localDispatchRunnable = mLastDispatchRunnable;
    if (localDispatchRunnable != null) {
      localDispatchRunnable.run();
    }
    mLastDispatchRunnable = new DispatchRunnable(mRegistry, paramEvent);
    mHandler.postAtFrontOfQueue(mLastDispatchRunnable);
  }
  
  public Lifecycle getLifecycle()
  {
    return mRegistry;
  }
  
  public void onServicePreSuperOnBind()
  {
    postDispatchRunnable(Lifecycle.Event.ON_START);
  }
  
  public void onServicePreSuperOnCreate()
  {
    postDispatchRunnable(Lifecycle.Event.ON_CREATE);
  }
  
  public void onServicePreSuperOnDestroy()
  {
    postDispatchRunnable(Lifecycle.Event.ON_STOP);
    postDispatchRunnable(Lifecycle.Event.ON_DESTROY);
  }
  
  public void onServicePreSuperOnStart()
  {
    postDispatchRunnable(Lifecycle.Event.ON_START);
  }
  
  static class DispatchRunnable
    implements Runnable
  {
    final Lifecycle.Event mEvent;
    private final LifecycleRegistry mRegistry;
    private boolean mWasExecuted = false;
    
    DispatchRunnable(LifecycleRegistry paramLifecycleRegistry, Lifecycle.Event paramEvent)
    {
      mRegistry = paramLifecycleRegistry;
      mEvent = paramEvent;
    }
    
    public void run()
    {
      if (!mWasExecuted)
      {
        mRegistry.handleLifecycleEvent(mEvent);
        mWasExecuted = true;
      }
    }
  }
}
