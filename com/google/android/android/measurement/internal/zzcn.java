package com.google.android.android.measurement.internal;

final class zzcn
  implements Runnable
{
  zzcn(zzbv paramZzbv, String paramString1, String paramString2, String paramString3, long paramLong) {}
  
  public final void run()
  {
    Object localObject = zzaqt;
    if (localObject == null)
    {
      zzbv.getSession(zzaqo).zzmb().zzgh().purchaseBook(zzaqq, null);
      return;
    }
    localObject = new zzdn(zzaeq, (String)localObject, zzaqu);
    zzbv.getSession(zzaqo).zzmb().zzgh().purchaseBook(zzaqq, (zzdn)localObject);
  }
}
