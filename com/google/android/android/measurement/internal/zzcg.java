package com.google.android.android.measurement.internal;

final class zzcg
  implements Runnable
{
  zzcg(zzbv paramZzbv, zzad paramZzad, VideoItem paramVideoItem) {}
  
  public final void run()
  {
    zzad localZzad = zzaqo.toJSONObject(zzaqr, zzaqn);
    zzbv.getSession(zzaqo).zzly();
    zzbv.getSession(zzaqo).getInstalledApps(localZzad, zzaqn);
  }
}
