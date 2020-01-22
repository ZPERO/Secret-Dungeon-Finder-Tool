package com.google.android.android.internal.measurement;

import java.io.IOException;

public final class zzgf
  extends com.google.android.gms.internal.measurement.zzza<com.google.android.gms.internal.measurement.zzgf>
{
  private static volatile zzgf[] zzaws;
  public Integer count = null;
  public String name = null;
  public zzgg[] zzawt = zzgg.zzmr();
  public Long zzawu = null;
  public Long zzawv = null;
  
  public zzgf()
  {
    zzcfc = null;
    zzcfm = -1;
  }
  
  public static zzgf[] zzmq()
  {
    if (zzaws == null)
    {
      Object localObject = zzze.zzcfl;
      try
      {
        if (zzaws == null) {
          zzaws = new zzgf[0];
        }
      }
      catch (Throwable localThrowable)
      {
        throw localThrowable;
      }
    }
    return zzaws;
  }
  
  public final boolean equals(Object paramObject)
  {
    if (paramObject == this) {
      return true;
    }
    if (!(paramObject instanceof zzgf)) {
      return false;
    }
    paramObject = (zzgf)paramObject;
    if (!zzze.equals(zzawt, zzawt)) {
      return false;
    }
    Object localObject = name;
    if (localObject == null)
    {
      if (name != null) {
        return false;
      }
    }
    else if (!((String)localObject).equals(name)) {
      return false;
    }
    localObject = zzawu;
    if (localObject == null)
    {
      if (zzawu != null) {
        return false;
      }
    }
    else if (!((Long)localObject).equals(zzawu)) {
      return false;
    }
    localObject = zzawv;
    if (localObject == null)
    {
      if (zzawv != null) {
        return false;
      }
    }
    else if (!((Long)localObject).equals(zzawv)) {
      return false;
    }
    localObject = count;
    if (localObject == null)
    {
      if (count != null) {
        return false;
      }
    }
    else if (!((Integer)localObject).equals(count)) {
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
    int i2 = getClass().getName().hashCode();
    int i3 = zzze.hashCode(zzawt);
    Object localObject = name;
    int i1 = 0;
    int i;
    if (localObject == null) {
      i = 0;
    } else {
      i = ((String)localObject).hashCode();
    }
    localObject = zzawu;
    int j;
    if (localObject == null) {
      j = 0;
    } else {
      j = ((Long)localObject).hashCode();
    }
    localObject = zzawv;
    int k;
    if (localObject == null) {
      k = 0;
    } else {
      k = ((Long)localObject).hashCode();
    }
    localObject = count;
    int m;
    if (localObject == null) {
      m = 0;
    } else {
      m = ((Integer)localObject).hashCode();
    }
    int n = i1;
    if (zzcfc != null) {
      if (zzcfc.isEmpty()) {
        n = i1;
      } else {
        n = zzcfc.hashCode();
      }
    }
    return ((((((i2 + 527) * 31 + i3) * 31 + i) * 31 + j) * 31 + k) * 31 + m) * 31 + n;
  }
  
  protected final int multiply()
  {
    int i = super.multiply();
    Object localObject = zzawt;
    int j = i;
    if (localObject != null)
    {
      j = i;
      if (localObject.length > 0)
      {
        int k = 0;
        for (;;)
        {
          localObject = zzawt;
          j = i;
          if (k >= localObject.length) {
            break;
          }
          localObject = localObject[k];
          j = i;
          if (localObject != null) {
            j = i + zzyy.addMenuItem(1, (zzzg)localObject);
          }
          k += 1;
          i = j;
        }
      }
    }
    localObject = name;
    i = j;
    if (localObject != null) {
      i = j + zzyy.setProperty(2, (String)localObject);
    }
    localObject = zzawu;
    j = i;
    if (localObject != null) {
      j = i + zzyy.write(3, ((Long)localObject).longValue());
    }
    localObject = zzawv;
    i = j;
    if (localObject != null) {
      i = j + zzyy.write(4, ((Long)localObject).longValue());
    }
    localObject = count;
    j = i;
    if (localObject != null) {
      j = i + zzyy.sendCommand(5, ((Integer)localObject).intValue());
    }
    return j;
  }
  
  public final void multiply(zzyy paramZzyy)
    throws IOException
  {
    Object localObject = zzawt;
    if ((localObject != null) && (localObject.length > 0))
    {
      int i = 0;
      for (;;)
      {
        localObject = zzawt;
        if (i >= localObject.length) {
          break;
        }
        localObject = localObject[i];
        if (localObject != null) {
          paramZzyy.writeHeader(1, (zzzg)localObject);
        }
        i += 1;
      }
    }
    localObject = name;
    if (localObject != null) {
      paramZzyy.add(2, (String)localObject);
    }
    localObject = zzawu;
    if (localObject != null) {
      paramZzyy.add(3, ((Long)localObject).longValue());
    }
    localObject = zzawv;
    if (localObject != null) {
      paramZzyy.add(4, ((Long)localObject).longValue());
    }
    localObject = count;
    if (localObject != null) {
      paramZzyy.addItem(5, ((Integer)localObject).intValue());
    }
    super.multiply(paramZzyy);
  }
}
