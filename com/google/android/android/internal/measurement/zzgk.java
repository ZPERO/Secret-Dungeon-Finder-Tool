package com.google.android.android.internal.measurement;

import java.io.IOException;

public final class zzgk
  extends com.google.android.gms.internal.measurement.zzza<com.google.android.gms.internal.measurement.zzgk>
{
  private static volatile zzgk[] zzayi;
  public Integer zzawq = null;
  public long[] zzayj = zzzj.zzcfr;
  
  public zzgk()
  {
    zzcfc = null;
    zzcfm = -1;
  }
  
  public static zzgk[] zzmt()
  {
    if (zzayi == null)
    {
      Object localObject = zzze.zzcfl;
      try
      {
        if (zzayi == null) {
          zzayi = new zzgk[0];
        }
      }
      catch (Throwable localThrowable)
      {
        throw localThrowable;
      }
    }
    return zzayi;
  }
  
  public final boolean equals(Object paramObject)
  {
    if (paramObject == this) {
      return true;
    }
    if (!(paramObject instanceof zzgk)) {
      return false;
    }
    paramObject = (zzgk)paramObject;
    Integer localInteger = zzawq;
    if (localInteger == null)
    {
      if (zzawq != null) {
        return false;
      }
    }
    else if (!localInteger.equals(zzawq)) {
      return false;
    }
    if (!zzze.equals(zzayj, zzayj)) {
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
    int m = getClass().getName().hashCode();
    Integer localInteger = zzawq;
    int k = 0;
    int i;
    if (localInteger == null) {
      i = 0;
    } else {
      i = localInteger.hashCode();
    }
    int n = zzze.hashCode(zzayj);
    int j = k;
    if (zzcfc != null) {
      if (zzcfc.isEmpty()) {
        j = k;
      } else {
        j = zzcfc.hashCode();
      }
    }
    return (((m + 527) * 31 + i) * 31 + n) * 31 + j;
  }
  
  protected final int multiply()
  {
    int j = super.multiply();
    int i = j;
    Object localObject = zzawq;
    if (localObject != null) {
      i = j + zzyy.sendCommand(1, ((Integer)localObject).intValue());
    }
    localObject = zzayj;
    j = i;
    if (localObject != null)
    {
      j = i;
      if (localObject.length > 0)
      {
        j = 0;
        int k = 0;
        for (;;)
        {
          localObject = zzayj;
          if (j >= localObject.length) {
            break;
          }
          k += zzyy.zzbi(localObject[j]);
          j += 1;
        }
        j = i + k + localObject.length * 1;
      }
    }
    return j;
  }
  
  public final void multiply(zzyy paramZzyy)
    throws IOException
  {
    Object localObject = zzawq;
    if (localObject != null) {
      paramZzyy.addItem(1, ((Integer)localObject).intValue());
    }
    localObject = zzayj;
    if ((localObject != null) && (localObject.length > 0))
    {
      int i = 0;
      for (;;)
      {
        localObject = zzayj;
        if (i >= localObject.length) {
          break;
        }
        paramZzyy.add(2, localObject[i]);
        i += 1;
      }
    }
    super.multiply(paramZzyy);
  }
}
