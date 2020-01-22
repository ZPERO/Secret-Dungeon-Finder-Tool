package com.google.firebase.package_9;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import javax.annotation.Nullable;

final class zzaz
  extends BroadcastReceiver
{
  @Nullable
  private zzay zzdk;
  
  public zzaz(zzay paramZzay)
  {
    zzdk = paramZzay;
  }
  
  public final void onReceive(Context paramContext, Intent paramIntent)
  {
    paramContext = zzdk;
    if (paramContext == null) {
      return;
    }
    if (!paramContext.zzao()) {
      return;
    }
    if (FirebaseInstanceId.get()) {
      Log.d("FirebaseInstanceId", "Connectivity changed. Starting background sync.");
    }
    FirebaseInstanceId.schedule(zzdk, 0L);
    zzdk.getContext().unregisterReceiver(this);
    zzdk = null;
  }
  
  public final void zzap()
  {
    if (FirebaseInstanceId.get()) {
      Log.d("FirebaseInstanceId", "Connectivity change received registered");
    }
    IntentFilter localIntentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
    zzdk.getContext().registerReceiver(this, localIntentFilter);
  }
}
