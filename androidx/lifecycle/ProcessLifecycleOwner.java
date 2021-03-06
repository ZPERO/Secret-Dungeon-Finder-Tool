package androidx.lifecycle;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;

public class ProcessLifecycleOwner
  implements LifecycleOwner
{
  static final long TIMEOUT_MS = 700L;
  private static final ProcessLifecycleOwner sInstance = new ProcessLifecycleOwner();
  private Runnable mDelayedPauseRunnable = new Runnable()
  {
    public void run()
    {
      dispatchPauseIfNeeded();
      dispatchStopIfNeeded();
    }
  };
  private Handler mHandler;
  ReportFragment.ActivityInitializationListener mInitializationListener = new ReportFragment.ActivityInitializationListener()
  {
    public void onCreate() {}
    
    public void onResume()
    {
      activityResumed();
    }
    
    public void onStart()
    {
      activityStarted();
    }
  };
  private boolean mPauseSent = true;
  private final LifecycleRegistry mRegistry = new LifecycleRegistry(this);
  private int mResumedCounter = 0;
  private int mStartedCounter = 0;
  private boolean mStopSent = true;
  
  private ProcessLifecycleOwner() {}
  
  public static LifecycleOwner getInstanceOrNull()
  {
    return sInstance;
  }
  
  static void init(Context paramContext)
  {
    sInstance.attach(paramContext);
  }
  
  void activityPaused()
  {
    mResumedCounter -= 1;
    if (mResumedCounter == 0) {
      mHandler.postDelayed(mDelayedPauseRunnable, 700L);
    }
  }
  
  void activityResumed()
  {
    mResumedCounter += 1;
    if (mResumedCounter == 1)
    {
      if (mPauseSent)
      {
        mRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
        mPauseSent = false;
        return;
      }
      mHandler.removeCallbacks(mDelayedPauseRunnable);
    }
  }
  
  void activityStarted()
  {
    mStartedCounter += 1;
    if ((mStartedCounter == 1) && (mStopSent))
    {
      mRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START);
      mStopSent = false;
    }
  }
  
  void activityStopped()
  {
    mStartedCounter -= 1;
    dispatchStopIfNeeded();
  }
  
  void attach(Context paramContext)
  {
    mHandler = new Handler();
    mRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
    ((Application)paramContext.getApplicationContext()).registerActivityLifecycleCallbacks(new EmptyActivityLifecycleCallbacks()
    {
      public void onActivityCreated(Activity paramAnonymousActivity, Bundle paramAnonymousBundle)
      {
        ReportFragment.showDialog(paramAnonymousActivity).setProcessListener(mInitializationListener);
      }
      
      public void onActivityPaused(Activity paramAnonymousActivity)
      {
        activityPaused();
      }
      
      public void onActivityStopped(Activity paramAnonymousActivity)
      {
        activityStopped();
      }
    });
  }
  
  void dispatchPauseIfNeeded()
  {
    if (mResumedCounter == 0)
    {
      mPauseSent = true;
      mRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE);
    }
  }
  
  void dispatchStopIfNeeded()
  {
    if ((mStartedCounter == 0) && (mPauseSent))
    {
      mRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
      mStopSent = true;
    }
  }
  
  public Lifecycle getLifecycle()
  {
    return mRegistry;
  }
}
