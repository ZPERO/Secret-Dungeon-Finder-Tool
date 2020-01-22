package com.google.android.android.internal.measurement;

import java.io.IOException;

public final class zzgl
  extends com.google.android.gms.internal.measurement.zzza<com.google.android.gms.internal.measurement.zzgl>
{
  private static volatile zzgl[] zzayk;
  public String name = null;
  public String zzamp = null;
  private Float zzaug = null;
  public Double zzauh = null;
  public Long zzawx = null;
  public Long zzayl = null;
  
  public zzgl()
  {
    zzcfc = null;
    zzcfm = -1;
  }
  
  public static zzgl[] zzmu()
  {
    if (zzayk == null)
    {
      Object localObject = zzze.zzcfl;
      try
      {
        if (zzayk == null) {
          zzayk = new zzgl[0];
        }
      }
      catch (Throwable localThrowable)
      {
        throw localThrowable;
      }
    }
    return zzayk;
  }
  
  public final boolean equals(Object paramObject)
  {
    if (paramObject == this) {
      return true;
    }
    if (!(paramObject instanceof zzgl)) {
      return false;
    }
    paramObject = (zzgl)paramObject;
    Object localObject = zzayl;
    if (localObject == null)
    {
      if (zzayl != null) {
        return false;
      }
    }
    else if (!((Long)localObject).equals(zzayl)) {
      return false;
    }
    localObject = name;
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
    int i4 = getClass().getName().hashCode();
    Object localObject = zzayl;
    int i3 = 0;
    int i;
    if (localObject == null) {
      i = 0;
    } else {
      i = ((Long)localObject).hashCode();
    }
    localObject = name;
    int j;
    if (localObject == null) {
      j = 0;
    } else {
      j = ((String)localObject).hashCode();
    }
    localObject = zzamp;
    int k;
    if (localObject == null) {
      k = 0;
    } else {
      k = ((String)localObject).hashCode();
    }
    localObject = zzawx;
    int m;
    if (localObject == null) {
      m = 0;
    } else {
      m = ((Long)localObject).hashCode();
    }
    localObject = zzaug;
    int n;
    if (localObject == null) {
      n = 0;
    } else {
      n = ((Float)localObject).hashCode();
    }
    localObject = zzauh;
    int i1;
    if (localObject == null) {
      i1 = 0;
    } else {
      i1 = ((Double)localObject).hashCode();
    }
    int i2 = i3;
    if (zzcfc != null) {
      if (zzcfc.isEmpty()) {
        i2 = i3;
      } else {
        i2 = zzcfc.hashCode();
      }
    }
    return (((((((i4 + 527) * 31 + i) * 31 + j) * 31 + k) * 31 + m) * 31 + n) * 31 + i1) * 31 + i2;
  }
  
  protected final int multiply()
  {
    int j = super.multiply();
    int i = j;
    Object localObject = zzayl;
    if (localObject != null) {
      i = j + zzyy.write(1, ((Long)localObject).longValue());
    }
    localObject = name;
    j = i;
    if (localObject != null) {
      j = i + zzyy.setProperty(2, (String)localObject);
    }
    localObject = zzamp;
    i = j;
    if (localObject != null) {
      i = j + zzyy.setProperty(3, (String)localObject);
    }
    localObject = zzawx;
    j = i;
    if (localObject != null) {
      j = i + zzyy.write(4, ((Long)localObject).longValue());
    }
    localObject = zzaug;
    i = j;
    if (localObject != null)
    {
      ((Float)localObject).floatValue();
      i = j + (zzyy.zzbb(5) + 4);
    }
    localObject = zzauh;
    j = i;
    if (localObject != null)
    {
      ((Double)localObject).doubleValue();
      j = i + (zzyy.zzbb(6) + 8);
    }
    return j;
  }
  
  public final void multiply(zzyy paramZzyy)
    throws IOException
  {
    Object localObject = zzayl;
    if (localObject != null) {
      paramZzyy.add(1, ((Long)localObject).longValue());
    }
    localObject = name;
    if (localObject != null) {
      paramZzyy.add(2, (String)localObject);
    }
    localObject = zzamp;
    if (localObject != null) {
      paramZzyy.add(3, (String)localObject);
    }
    localObject = zzawx;
    if (localObject != null) {
      paramZzyy.add(4, ((Long)localObject).longValue());
    }
    localObject = zzaug;
    if (localObject != null) {
      paramZzyy.write(5, ((Float)localObject).floatValue());
    }
    localObject = zzauh;
    if (localObject != null) {
      paramZzyy.add(6, ((Double)localObject).doubleValue());
    }
    super.multiply(paramZzyy);
  }
}
