package com.google.android.android.measurement.internal;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Pair;
import com.google.android.android.common.internal.Preconditions;
import com.google.android.android.common.util.Clock;
import java.security.SecureRandom;

public final class zzbe
{
  private final long zzabv;
  private final String zzaoa;
  private final String zzaob;
  private final String zzaoc;
  
  private zzbe(zzba paramZzba, String paramString, long paramLong)
  {
    Preconditions.checkNotEmpty(paramString);
    boolean bool;
    if (paramLong > 0L) {
      bool = true;
    } else {
      bool = false;
    }
    Preconditions.checkArgument(bool);
    zzaoa = String.valueOf(paramString).concat(":start");
    zzaob = String.valueOf(paramString).concat(":count");
    zzaoc = String.valueOf(paramString).concat(":value");
    zzabv = paramLong;
  }
  
  private final void zzfl()
  {
    zzany.zzaf();
    long l = zzany.zzbx().currentTimeMillis();
    SharedPreferences.Editor localEditor = zzba.prefs(zzany).edit();
    localEditor.remove(zzaob);
    localEditor.remove(zzaoc);
    localEditor.putLong(zzaoa, l);
    localEditor.apply();
  }
  
  private final long zzfn()
  {
    return zzba.prefs(zzany).getLong(zzaoa, 0L);
  }
  
  public final void store(String paramString, long paramLong)
  {
    zzany.zzaf();
    if (zzfn() == 0L) {
      zzfl();
    }
    String str = paramString;
    if (paramString == null) {
      str = "";
    }
    long l = zzba.prefs(zzany).getLong(zzaob, 0L);
    if (l <= 0L)
    {
      paramString = zzba.prefs(zzany).edit();
      paramString.putString(zzaoc, str);
      paramString.putLong(zzaob, 1L);
      paramString.apply();
      return;
    }
    paramLong = zzany.zzgm().zzmd().nextLong();
    l += 1L;
    int i;
    if ((paramLong & 0x7FFFFFFFFFFFFFFF) < Long.MAX_VALUE / l) {
      i = 1;
    } else {
      i = 0;
    }
    paramString = zzba.prefs(zzany).edit();
    if (i != 0) {
      paramString.putString(zzaoc, str);
    }
    paramString.putLong(zzaob, l);
    paramString.apply();
  }
  
  public final Pair zzfm()
  {
    zzany.zzaf();
    Object localObject = zzany;
    zzbe localZzbe = this;
    ((zzco)localObject).zzaf();
    long l1 = localZzbe.zzfn();
    if (l1 == 0L)
    {
      localZzbe.zzfl();
      l1 = 0L;
    }
    else
    {
      l1 = Math.abs(l1 - zzany.zzbx().currentTimeMillis());
    }
    localZzbe = this;
    long l2 = zzabv;
    if (l1 < l2) {
      return null;
    }
    if (l1 > l2 << 1)
    {
      localZzbe.zzfl();
      return null;
    }
    localObject = zzba.prefs(zzany).getString(zzaoc, null);
    l1 = zzba.prefs(zzany).getLong(zzaob, 0L);
    localZzbe.zzfl();
    if ((localObject != null) && (l1 > 0L)) {
      return new Pair(localObject, Long.valueOf(l1));
    }
    return zzba.zzanc;
  }
}
