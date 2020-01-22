package com.google.android.android.internal.measurement;

import java.io.IOException;

public final class zzgj
  extends com.google.android.gms.internal.measurement.zzza<com.google.android.gms.internal.measurement.zzgj>
{
  public long[] zzaye = zzzj.zzcfr;
  public long[] zzayf = zzzj.zzcfr;
  public zzge[] zzayg = zzge.zzmp();
  public zzgk[] zzayh = zzgk.zzmt();
  
  public zzgj()
  {
    zzcfc = null;
    zzcfm = -1;
  }
  
  public final boolean equals(Object paramObject)
  {
    if (paramObject == this) {
      return true;
    }
    if (!(paramObject instanceof zzgj)) {
      return false;
    }
    paramObject = (zzgj)paramObject;
    if (!zzze.equals(zzaye, zzaye)) {
      return false;
    }
    if (!zzze.equals(zzayf, zzayf)) {
      return false;
    }
    if (!zzze.equals(zzayg, zzayg)) {
      return false;
    }
    if (!zzze.equals(zzayh, zzayh)) {
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
    int j = getClass().getName().hashCode();
    int k = zzze.hashCode(zzaye);
    int m = zzze.hashCode(zzayf);
    int n = zzze.hashCode(zzayg);
    int i1 = zzze.hashCode(zzayh);
    int i;
    if ((zzcfc != null) && (!zzcfc.isEmpty())) {
      i = zzcfc.hashCode();
    } else {
      i = 0;
    }
    return (((((j + 527) * 31 + k) * 31 + m) * 31 + n) * 31 + i1) * 31 + i;
  }
  
  protected final int multiply()
  {
    int k = super.multiply();
    int j = k;
    Object localObject = zzaye;
    int m = 0;
    int i = j;
    if (localObject != null)
    {
      i = j;
      if (localObject.length > 0)
      {
        i = 0;
        j = 0;
        for (;;)
        {
          localObject = zzaye;
          if (i >= localObject.length) {
            break;
          }
          j += zzyy.zzbi(localObject[i]);
          i += 1;
        }
        i = k + j + localObject.length * 1;
      }
    }
    localObject = zzayf;
    j = i;
    if (localObject != null)
    {
      j = i;
      if (localObject.length > 0)
      {
        j = 0;
        k = 0;
        for (;;)
        {
          localObject = zzayf;
          if (j >= localObject.length) {
            break;
          }
          k += zzyy.zzbi(localObject[j]);
          j += 1;
        }
        j = i + k + localObject.length * 1;
      }
    }
    localObject = zzayg;
    i = j;
    if (localObject != null)
    {
      i = j;
      if (localObject.length > 0)
      {
        k = 0;
        for (;;)
        {
          localObject = zzayg;
          i = j;
          if (k >= localObject.length) {
            break;
          }
          localObject = localObject[k];
          i = j;
          if (localObject != null) {
            i = j + zzyy.addMenuItem(3, (zzzg)localObject);
          }
          k += 1;
          j = i;
        }
      }
    }
    localObject = zzayh;
    k = i;
    if (localObject != null)
    {
      k = i;
      if (localObject.length > 0)
      {
        j = m;
        for (;;)
        {
          localObject = zzayh;
          k = i;
          if (j >= localObject.length) {
            break;
          }
          localObject = localObject[j];
          k = i;
          if (localObject != null) {
            k = i + zzyy.addMenuItem(4, (zzzg)localObject);
          }
          j += 1;
          i = k;
        }
      }
    }
    return k;
  }
  
  public final void multiply(zzyy paramZzyy)
    throws IOException
  {
    Object localObject = zzaye;
    int j = 0;
    int i;
    if ((localObject != null) && (localObject.length > 0))
    {
      i = 0;
      for (;;)
      {
        localObject = zzaye;
        if (i >= localObject.length) {
          break;
        }
        paramZzyy.multiply(1, localObject[i]);
        i += 1;
      }
    }
    localObject = zzayf;
    if ((localObject != null) && (localObject.length > 0))
    {
      i = 0;
      for (;;)
      {
        localObject = zzayf;
        if (i >= localObject.length) {
          break;
        }
        paramZzyy.multiply(2, localObject[i]);
        i += 1;
      }
    }
    localObject = zzayg;
    if ((localObject != null) && (localObject.length > 0))
    {
      i = 0;
      for (;;)
      {
        localObject = zzayg;
        if (i >= localObject.length) {
          break;
        }
        localObject = localObject[i];
        if (localObject != null) {
          paramZzyy.writeHeader(3, (zzzg)localObject);
        }
        i += 1;
      }
    }
    localObject = zzayh;
    if ((localObject != null) && (localObject.length > 0))
    {
      i = j;
      for (;;)
      {
        localObject = zzayh;
        if (i >= localObject.length) {
          break;
        }
        localObject = localObject[i];
        if (localObject != null) {
          paramZzyy.writeHeader(4, (zzzg)localObject);
        }
        i += 1;
      }
    }
    super.multiply(paramZzyy);
  }
}
