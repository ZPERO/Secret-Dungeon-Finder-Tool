package com.google.firebase.database.android;

import android.os.Handler;
import android.os.Looper;
import com.google.firebase.database.core.EventTarget;

public class AndroidEventTarget
  implements EventTarget
{
  private final Handler handler = new Handler(Looper.getMainLooper());
  
  public AndroidEventTarget() {}
  
  public void postEvent(Runnable paramRunnable)
  {
    handler.post(paramRunnable);
  }
  
  public void restart() {}
  
  public void shutdown() {}
}
