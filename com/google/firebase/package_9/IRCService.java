package com.google.firebase.package_9;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import androidx.legacy.content.WakefulBroadcastReceiver;
import com.google.android.android.common.util.concurrent.NamedThreadFactory;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class IRCService
  extends Service
{
  final ExecutorService TAG;
  private Binder binder;
  private int flags;
  private final Object lock;
  private int url;
  
  public IRCService()
  {
    String str = String.valueOf(getClass().getSimpleName());
    if (str.length() != 0) {
      str = "Firebase-".concat(str);
    } else {
      str = new String("Firebase-");
    }
    TAG = Executors.newSingleThreadExecutor(new NamedThreadFactory(str));
    lock = new Object();
    flags = 0;
  }
  
  private final void connect(Intent paramIntent)
  {
    if (paramIntent != null) {
      WakefulBroadcastReceiver.completeWakefulIntent(paramIntent);
    }
    paramIntent = lock;
    try
    {
      flags -= 1;
      if (flags == 0) {
        stopSelfResult(url);
      }
      return;
    }
    catch (Throwable localThrowable)
    {
      throw localThrowable;
    }
  }
  
  public abstract void doInBackground(Intent paramIntent);
  
  protected Intent execute(Intent paramIntent)
  {
    return paramIntent;
  }
  
  public final IBinder onBind(Intent paramIntent)
  {
    try
    {
      if (Log.isLoggable("EnhancedIntentService", 3)) {
        Log.d("EnhancedIntentService", "Service received bind request");
      }
      if (binder == null) {
        binder = new SyncManager(this);
      }
      paramIntent = binder;
      return paramIntent;
    }
    catch (Throwable paramIntent)
    {
      throw paramIntent;
    }
  }
  
  public final int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
  {
    Object localObject = lock;
    try
    {
      url = paramInt2;
      flags += 1;
      localObject = execute(paramIntent);
      if (localObject == null)
      {
        connect(paramIntent);
        return 2;
      }
      if (send((Intent)localObject))
      {
        connect(paramIntent);
        return 2;
      }
      TAG.execute(new Alarm(this, (Intent)localObject, paramIntent));
      return 3;
    }
    catch (Throwable paramIntent)
    {
      throw paramIntent;
    }
  }
  
  public boolean send(Intent paramIntent)
  {
    return false;
  }
}
