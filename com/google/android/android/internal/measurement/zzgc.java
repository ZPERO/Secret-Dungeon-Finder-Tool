package com.google.android.android.internal.measurement;

import java.io.IOException;

public final class zzgc
  extends com.google.android.gms.internal.measurement.zzza<com.google.android.gms.internal.measurement.zzgc>
{
  private static volatile zzgc[] zzawk;
  public String value = null;
  public String zzoj = null;
  
  public zzgc()
  {
    zzcfc = null;
    zzcfm = -1;
  }
  
  public static zzgc[] zzmn()
  {
    if (zzawk == null)
    {
      Object localObject = zzze.zzcfl;
      try
      {
        if (zzawk == null) {
          zzawk = new zzgc[0];
        }
      }
      catch (Throwable localThrowable)
      {
        throw localThrowable;
      }
    }
    return zzawk;
  }
  
  public final boolean equals(Object paramObject)
  {
    if (paramObject == this) {
      return true;
    }
    if (!(paramObject instanceof zzgc)) {
      return false;
    }
    paramObject = (zzgc)paramObject;
    String str = zzoj;
    if (str == null)
    {
      if (zzoj != null) {
        return false;
      }
    }
    else if (!str.equals(zzoj)) {
      return false;
    }
    str = value;
    if (str == null)
    {
      if (value != null) {
        return false;
      }
    }
    else if (!str.equals(value)) {
      return false;
    }
    if ((zzcfc != null) && (!zzcfc.isEmpty())) {
      return zzcfc.equals(zzcfc);
    }
    if (zzcfc != null) {
      return zzcfc.isEmpty();
    }
    return true;
  }
  
  public final int hashCode()
  {
    int n = getClass().getName().hashCode();
    String str = zzoj;
    int m = 0;
    int i;
    if (str == null) {
      i = 0;
    } else {
      i = str.hashCode();
    }
    str = value;
    int j;
    if (str == null) {
      j = 0;
    } else {
      j = str.hashCode();
    }
    int k = m;
    if (zzcfc != null) {
      if (zzcfc.isEmpty()) {
        k = m;
      } else {
        k = zzcfc.hashCode();
      }
    }
    return (((n + 527) * 31 + i) * 31 + j) * 31 + k;
  }
  
  protected final int multiply()
  {
    int j = super.multiply();
    int i = j;
    String str = zzoj;
    if (str != null) {
      i = j + zzyy.setProperty(1, str);
    }
    str = value;
    if (str != null) {
      return i + zzyy.setProperty(2, str);
    }
    return i;
  }
  
  public final void multiply(zzyy paramZzyy)
    throws IOException
  {
    String str = zzoj;
    if (str != null) {
      paramZzyy.add(1, str);
    }
    str = value;
    if (str != null) {
      paramZzyy.add(2, str);
    }
    super.multiply(paramZzyy);
  }
}
