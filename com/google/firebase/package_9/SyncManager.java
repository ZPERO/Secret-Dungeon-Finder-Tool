package com.google.firebase.package_9;

import android.os.Binder;
import android.os.Process;
import android.util.Log;
import java.util.concurrent.ExecutorService;

public final class SyncManager
  extends Binder
{
  private final IRCService this$0;
  
  SyncManager(IRCService paramIRCService)
  {
    this$0 = paramIRCService;
  }
  
  public final void connect(Request paramRequest)
  {
    if (Binder.getCallingUid() == Process.myUid())
    {
      if (Log.isLoggable("EnhancedIntentService", 3)) {
        Log.d("EnhancedIntentService", "service received new intent via bind strategy");
      }
      if (this$0.send(intent))
      {
        paramRequest.finish();
        return;
      }
      if (Log.isLoggable("EnhancedIntentService", 3)) {
        Log.d("EnhancedIntentService", "intent being queued for bg execution");
      }
      this$0.TAG.execute(new ArrayHandler.3(this, paramRequest));
      return;
    }
    throw new SecurityException("Binding only allowed within app");
  }
}
