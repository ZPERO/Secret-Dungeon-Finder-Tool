package androidx.lifecycle;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class LifecycleService
  extends Service
  implements LifecycleOwner
{
  private final ServiceLifecycleDispatcher mDispatcher = new ServiceLifecycleDispatcher(this);
  
  public LifecycleService() {}
  
  public Lifecycle getLifecycle()
  {
    return mDispatcher.getLifecycle();
  }
  
  public IBinder onBind(Intent paramIntent)
  {
    mDispatcher.onServicePreSuperOnBind();
    return null;
  }
  
  public void onCreate()
  {
    mDispatcher.onServicePreSuperOnCreate();
    super.onCreate();
  }
  
  public void onDestroy()
  {
    mDispatcher.onServicePreSuperOnDestroy();
    super.onDestroy();
  }
  
  public void onStart(Intent paramIntent, int paramInt)
  {
    mDispatcher.onServicePreSuperOnStart();
    super.onStart(paramIntent, paramInt);
  }
  
  public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
  {
    return super.onStartCommand(paramIntent, paramInt1, paramInt2);
  }
}
