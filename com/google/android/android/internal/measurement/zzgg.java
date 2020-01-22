package com.google.android.android.internal.measurement;

import java.io.IOException;

public final class zzgg
  extends com.google.android.gms.internal.measurement.zzza<com.google.android.gms.internal.measurement.zzgg>
{
  private static volatile zzgg[] zzaww;
  public String name = null;
  public String zzamp = null;
  private Float zzaug = null;
  public Double zzauh = null;
  public Long zzawx = null;
  
  public zzgg()
  {
    zzcfc = null;
    zzcfm = -1;
  }
  
  public static zzgg[] zzmr()
  {
    if (zzaww == null)
    {
      Object localObject = zzze.zzcfl;
      try
      {
        if (zzaww == null) {
          zzaww = new zzgg[0];
        }
      }
      catch (Throwable localThrowable)
      {
        throw localThrowable;
      }
    }
    return zzaww;
  }
  
  public final boolean equals(Object paramObject)
  {
    if (paramObject == this) {
      return true;
    }
    if (!(paramObject instanceof zzgg)) {
      return false;
    }
    paramObject = (zzgg)paramObject;
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
    localObject = zzamp;
    if (localObject == null)
    {
      if (zzamp != null) {
        return false;
      }
    }
    else if (!((String)localObject).equals(zzamp)) {
      return false;
    }
    localObject = zzawx;
    if (localObject == null)
    {
      if (zzawx != null) {
        return false;
      }
    }
    else if (!((Long)localObject).equals(zzawx)) {
      return false;
    }
    localObject = zzaug;
    if (localObject == null)
    {
      if (zzaug != null) {
        return false;
      }
    }
    else if (!((Float)localObject).equals(zzaug)) {
      return false;
    }
    localObject = zzauh;
    if (localObject == null)
    {
      if (zzauh != null) {
        return false;
      }
    }
    else if (!((Double)localObject).equals(zzauh)) {
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
    int i3 = getClass().getName().hashCode();
    Object localObject = name;
    int i2 = 0;
    int i;
    if (localObject == null) {
      i = 0;
    } else {
      i = ((String)localObject).hashCode();
    }
    localObject = zzamp;
    int j;
    if (localObject == null) {
      j = 0;
    } else {
      j = ((String)localObject).hashCode();
    }
    localObject = zzawx;
    int k;
    if (localObject == null) {
      k = 0;
    } else {
      k = ((Long)localObject).hashCode();
    }
    localObject = zzaug;
    int m;
    if (localObject == null) {
      m = 0;
    } else {
      m = ((Float)localObject).hashCode();
    }
    localObject = zzauh;
    int n;
    if (localObject == null) {
      n = 0;
    } else {
      n = ((Double)localObject).hashCode();
    }
    int i1 = i2;
    if (zzcfc != null) {
      if (zzcfc.isEmpty()) {
        i1 = i2;
      } else {
        i1 = zzcfc.hashCode();
      }
    }
    return ((((((i3 + 527) * 31 + i) * 31 + j) * 31 + k) * 31 + m) * 31 + n) * 31 + i1;
  }
  
  protected final int multiply()
  {
    int j = super.multiply();
    int i = j;
    Object localObject = name;
    if (localObject != null) {
      i = j + zzyy.setProperty(1, (String)localObject);
    }
    localObject = zzamp;
    j = i;
    if (localObject != null) {
      j = i + zzyy.setProperty(2, (String)localObject);
    }
    localObject = zzawx;
    i = j;
    if (localObject != null) {
      i = j + zzyy.write(3, ((Long)localObject).longValue());
    }
    localObject = zzaug;
    j = i;
    if (localObject != null)
    {
      ((Float)localObject).floatValue();
      j = i + (zzyy.zzbb(4) + 4);
    }
    localObject = zzauh;
    i = j;
    if (localObject != null)
    {
      ((Double)localObject).doubleValue();
      i = j + (zzyy.zzbb(5) + 8);
    }
    return i;
  }
  
  public final void multiply(zzyy paramZzyy)
    throws IOException
  {
    Object localObject = name;
    if (localObject != null) {
      paramZzyy.add(1, (String)localObject);
    }
    localObject = zzamp;
    if (localObject != null) {
      paramZzyy.add(2, (String)localObject);
    }
    localObject = zzawx;
    if (localObject != null) {
      paramZzyy.add(3, ((Long)localObject).longValue());
    }
    localObject = zzaug;
    if (localObject != null) {
      paramZzyy.write(4, ((Float)localObject).floatValue());
    }
    localObject = zzauh;
    if (localObject != null) {
      paramZzyy.add(5, ((Double)localObject).doubleValue());
    }
    super.multiply(paramZzyy);
  }
}
