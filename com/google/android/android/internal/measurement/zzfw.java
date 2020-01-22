package com.google.android.android.internal.measurement;

import java.io.IOException;

public final class zzfw
  extends com.google.android.gms.internal.measurement.zzza<com.google.android.gms.internal.measurement.zzfw>
{
  private static volatile zzfw[] zzavj;
  public zzfz zzavk = null;
  public zzfx zzavl = null;
  public Boolean zzavm = null;
  public String zzavn = null;
  
  public zzfw()
  {
    zzcfc = null;
    zzcfm = -1;
  }
  
  public static zzfw[] zzmk()
  {
    if (zzavj == null)
    {
      Object localObject = zzze.zzcfl;
      try
      {
        if (zzavj == null) {
          zzavj = new zzfw[0];
        }
      }
      catch (Throwable localThrowable)
      {
        throw localThrowable;
      }
    }
    return zzavj;
  }
  
  public final boolean equals(Object paramObject)
  {
    if (paramObject == this) {
      return true;
    }
    if (!(paramObject instanceof zzfw)) {
      return false;
    }
    paramObject = (zzfw)paramObject;
    Object localObject = zzavk;
    if (localObject == null)
    {
      if (zzavk != null) {
        return false;
      }
    }
    else if (!((zzfz)localObject).equals(zzavk)) {
      return false;
    }
    localObject = zzavl;
    if (localObject == null)
    {
      if (zzavl != null) {
        return false;
      }
    }
    else if (!((zzfx)localObject).equals(zzavl)) {
      return false;
    }
    localObject = zzavm;
    if (localObject == null)
    {
      if (zzavm != null) {
        return false;
      }
    }
    else if (!((Boolean)localObject).equals(zzavm)) {
      return false;
    }
    localObject = zzavn;
    if (localObject == null)
    {
      if (zzavn != null) {
        return false;
      }
    }
    else if (!((String)localObject).equals(zzavn)) {
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
    Object localObject = zzavk;
    int i1 = 0;
    int i;
    if (localObject == null) {
      i = 0;
    } else {
      i = ((zzfz)localObject).hashCode();
    }
    localObject = zzavl;
    int j;
    if (localObject == null) {
      j = 0;
    } else {
      j = ((zzfx)localObject).hashCode();
    }
    localObject = zzavm;
    int k;
    if (localObject == null) {
      k = 0;
    } else {
      k = ((Boolean)localObject).hashCode();
    }
    localObject = zzavn;
    int m;
    if (localObject == null) {
      m = 0;
    } else {
      m = ((String)localObject).hashCode();
    }
    int n = i1;
    if (zzcfc != null) {
      if (zzcfc.isEmpty()) {
        n = i1;
      } else {
        n = zzcfc.hashCode();
      }
    }
    return (((((i2 + 527) * 31 + i) * 31 + j) * 31 + k) * 31 + m) * 31 + n;
  }
  
  protected final int multiply()
  {
    int j = super.multiply();
    int i = j;
    Object localObject = zzavk;
    if (localObject != null) {
      i = j + zzyy.addMenuItem(1, (zzzg)localObject);
    }
    localObject = zzavl;
    j = i;
    if (localObject != null) {
      j = i + zzyy.addMenuItem(2, (zzzg)localObject);
    }
    localObject = zzavm;
    i = j;
    if (localObject != null)
    {
      ((Boolean)localObject).booleanValue();
      i = j + (zzyy.zzbb(3) + 1);
    }
    localObject = zzavn;
    j = i;
    if (localObject != null) {
      j = i + zzyy.setProperty(4, (String)localObject);
    }
    return j;
  }
  
  public final void multiply(zzyy paramZzyy)
    throws IOException
  {
    Object localObject = zzavk;
    if (localObject != null) {
      paramZzyy.writeHeader(1, (zzzg)localObject);
    }
    localObject = zzavl;
    if (localObject != null) {
      paramZzyy.writeHeader(2, (zzzg)localObject);
    }
    localObject = zzavm;
    if (localObject != null) {
      paramZzyy.add(3, ((Boolean)localObject).booleanValue());
    }
    localObject = zzavn;
    if (localObject != null) {
      paramZzyy.add(4, (String)localObject);
    }
    super.multiply(paramZzyy);
  }
}
