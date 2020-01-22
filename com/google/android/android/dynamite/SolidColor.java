package com.google.android.android.dynamite;

import android.content.Context;

final class SolidColor
  implements DynamiteModule.VersionPolicy
{
  SolidColor() {}
  
  public final DynamiteModule.VersionPolicy.zzb blur(Context paramContext, String paramString, DynamiteModule.VersionPolicy.zza paramZza)
    throws DynamiteModule.LoadingException
  {
    DynamiteModule.VersionPolicy.zzb localZzb = new DynamiteModule.VersionPolicy.zzb();
    zziq = paramZza.getLocalVersion(paramContext, paramString);
    zzir = paramZza.copy(paramContext, paramString, true);
    if ((zziq == 0) && (zzir == 0))
    {
      zzis = 0;
      return localZzb;
    }
    if (zziq >= zzir)
    {
      zzis = -1;
      return localZzb;
    }
    zzis = 1;
    return localZzb;
  }
}
