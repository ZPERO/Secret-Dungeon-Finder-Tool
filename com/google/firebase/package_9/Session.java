package com.google.firebase.package_9;

import android.content.BroadcastReceiver.PendingResult;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import com.google.android.android.common.stats.ConnectionTracker;
import com.google.android.android.common.util.concurrent.NamedThreadFactory;
import com.google.firebase.iid.zzd;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public final class Session
  implements ServiceConnection
{
  private final ScheduledExecutorService mAddress;
  private final Context mContext;
  private final Intent mType;
  private final Queue<zzd> zzaa = new ArrayDeque();
  private SyncManager zzab;
  private boolean zzac = false;
  
  public Session(Context paramContext, String paramString)
  {
    this(paramContext, paramString, new ScheduledThreadPoolExecutor(0, new NamedThreadFactory("Firebase-FirebaseInstanceIdServiceConnection")));
  }
  
  private Session(Context paramContext, String paramString, ScheduledExecutorService paramScheduledExecutorService)
  {
    mContext = paramContext.getApplicationContext();
    mType = new Intent(paramString).setPackage(mContext.getPackageName());
    mAddress = paramScheduledExecutorService;
  }
  
  private final void connect()
  {
    Object localObject1 = this;
    for (;;)
    {
      try
      {
        if (Log.isLoggable("EnhancedIntentService", 3))
        {
          localObject1 = this;
          Log.d("EnhancedIntentService", "flush queue called");
        }
        localObject1 = this;
        Object localObject2;
        if (!zzaa.isEmpty())
        {
          localObject1 = this;
          if (Log.isLoggable("EnhancedIntentService", 3))
          {
            localObject1 = this;
            Log.d("EnhancedIntentService", "found intent to be delivered");
          }
          localObject1 = this;
          if (zzab != null)
          {
            localObject1 = this;
            if (zzab.isBinderAlive())
            {
              localObject1 = this;
              if (Log.isLoggable("EnhancedIntentService", 3))
              {
                localObject1 = this;
                Log.d("EnhancedIntentService", "binder is alive, sending the intent.");
              }
              localObject1 = this;
              localObject2 = (Request)zzaa.poll();
              localObject1 = this;
              zzab.connect((Request)localObject2);
              continue;
            }
          }
          localObject2 = this;
          localObject1 = this;
          Object localObject3;
          if (Log.isLoggable("EnhancedIntentService", 3))
          {
            localObject1 = this;
            bool = zzac;
            localObject2 = this;
            if (!bool)
            {
              bool = true;
              localObject1 = localObject2;
              localObject3 = new StringBuilder(39);
              localObject1 = localObject2;
              ((StringBuilder)localObject3).append("binder is dead. start connection? ");
              localObject1 = localObject2;
              ((StringBuilder)localObject3).append(bool);
              localObject1 = localObject2;
              Log.d("EnhancedIntentService", ((StringBuilder)localObject3).toString());
            }
          }
          else
          {
            localObject1 = localObject2;
            bool = zzac;
            if (!bool)
            {
              localObject1 = localObject2;
              zzac = true;
              localObject1 = localObject2;
              try
              {
                localObject3 = ConnectionTracker.getInstance();
                Context localContext = mContext;
                Intent localIntent = mType;
                localObject1 = localObject2;
                bool = ((ConnectionTracker)localObject3).bindService(localContext, localIntent, (ServiceConnection)localObject2, 65);
                if (bool) {
                  return;
                }
                localObject1 = localObject2;
                Log.e("EnhancedIntentService", "binding to the service failed");
              }
              catch (SecurityException localSecurityException)
              {
                localObject1 = localObject2;
                Log.e("EnhancedIntentService", "Exception while binding the service", localSecurityException);
              }
              localObject1 = localObject2;
              zzac = false;
              localObject1 = localObject2;
              ((Session)localObject2).get();
            }
          }
        }
        else
        {
          return;
        }
      }
      catch (Throwable localThrowable)
      {
        throw localThrowable;
      }
      boolean bool = false;
    }
  }
  
  private final void get()
  {
    while (!zzaa.isEmpty()) {
      ((Request)zzaa.poll()).finish();
    }
  }
  
  public final void connect(Intent paramIntent, BroadcastReceiver.PendingResult paramPendingResult)
  {
    try
    {
      if (Log.isLoggable("EnhancedIntentService", 3)) {
        Log.d("EnhancedIntentService", "new intent queued in the bind-strategy delivery");
      }
      zzaa.add(new Request(paramIntent, paramPendingResult, mAddress));
      connect();
      return;
    }
    catch (Throwable paramIntent)
    {
      throw paramIntent;
    }
  }
  
  public final void onServiceConnected(ComponentName paramComponentName, IBinder paramIBinder)
  {
    try
    {
      zzac = false;
      zzab = ((SyncManager)paramIBinder);
      if (Log.isLoggable("EnhancedIntentService", 3))
      {
        paramComponentName = String.valueOf(paramComponentName);
        StringBuilder localStringBuilder = new StringBuilder(String.valueOf(paramComponentName).length() + 20);
        localStringBuilder.append("onServiceConnected: ");
        localStringBuilder.append(paramComponentName);
        Log.d("EnhancedIntentService", localStringBuilder.toString());
      }
      if (paramIBinder == null)
      {
        Log.e("EnhancedIntentService", "Null service connection");
        get();
      }
      else
      {
        connect();
      }
      return;
    }
    catch (Throwable paramComponentName)
    {
      throw paramComponentName;
    }
  }
  
  public final void onServiceDisconnected(ComponentName paramComponentName)
  {
    if (Log.isLoggable("EnhancedIntentService", 3))
    {
      paramComponentName = String.valueOf(paramComponentName);
      StringBuilder localStringBuilder = new StringBuilder(String.valueOf(paramComponentName).length() + 23);
      localStringBuilder.append("onServiceDisconnected: ");
      localStringBuilder.append(paramComponentName);
      Log.d("EnhancedIntentService", localStringBuilder.toString());
    }
    connect();
  }
}
