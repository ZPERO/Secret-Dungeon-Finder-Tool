package com.google.android.android.internal.measurement;

import java.io.IOException;

public final class zzga
  extends com.google.android.gms.internal.measurement.zzza<com.google.android.gms.internal.measurement.zzga>
{
  private static volatile zzga[] zzawa;
  public String name = null;
  public Boolean zzawb = null;
  public Boolean zzawc = null;
  public Integer zzawd = null;
  
  public zzga()
  {
    zzcfc = null;
    zzcfm = -1;
  }
  
  public static zzga[] zzmm()
  {
    if (zzawa == null)
    {
      Object localObject = zzze.zzcfl;
      try
      {
        if (zzawa == null) {
          zzawa = new zzga[0];
        }
      }
      catch (Throwable localThrowable)
      {
        throw localThrowable;
      }
    }
    return zzawa;
  }
  
  public final boolean equals(Object paramObject)
  {
    if (paramObject == this) {
      return true;
    }
    if (!(paramObject instanceof zzga)) {
      return false;
    }
    paramObject = (zzga)paramObject;
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
    localObject = zzawb;
    if (localObject == null)
    {
      if (zzawb != null) {
        return false;
      }
    }
    else if (!((Boolean)localObject).equals(zzawb)) {
      return false;
    }
    localObject = zzawc;
    if (localObject == null)
    {
      if (zzawc != null) {
        return false;
      }
    }
    else if (!((Boolean)localObject).equals(zzawc)) {
      return false;
    }
    localObject = zzawd;
    if (localObject == null)
    {
      if (zzawd != null) {
        return false;
      }
    }
    else if (!((Integer)localObject).equals(zzawd)) {
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
    Object localObject = name;
    int i1 = 0;
    int i;
    if (localObject == null) {
      i = 0;
    } else {
      i = ((String)localObject).hashCode();
    }
    localObject = zzawb;
    int j;
    if (localObject == null) {
      j = 0;
    } else {
      j = ((Boolean)localObject).hashCode();
    }
    localObject = zzawc;
    int k;
    if (localObject == null) {
      k = 0;
    } else {
      k = ((Boolean)localObject).hashCode();
    }
    localObject = zzawd;
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
    return (((((i2 + 527) * 31 + i) * 31 + j) * 31 + k) * 31 + m) * 31 + n;
  }
  
  protected final int multiply()
  {
    int j = super.multiply();
    int i = j;
    Object localObject = name;
    if (localObject != null) {
      i = j + zzyy.setProperty(1, (String)localObject);
    }
    localObject = zzawb;
    j = i;
    if (localObject != null)
    {
      ((Boolean)localObject).booleanValue();
      j = i + (zzyy.zzbb(2) + 1);
    }
    localObject = zzawc;
    i = j;
    if (localObject != null)
    {
      ((Boolean)localObject).booleanValue();
      i = j + (zzyy.zzbb(3) + 1);
    }
    localObject = zzawd;
    j = i;
    if (localObject != null) {
      j = i + zzyy.sendCommand(4, ((Integer)localObject).intValue());
    }
    return j;
  }
  
  public final void multiply(zzyy paramZzyy)
    throws IOException
  {
    Object localObject = name;
    if (localObject != null) {
      paramZzyy.add(1, (String)localObject);
    }
    localObject = zzawb;
    if (localObject != null) {
      paramZzyy.add(2, ((Boolean)localObject).booleanValue());
    }
    localObject = zzawc;
    if (localObject != null) {
      paramZzyy.add(3, ((Boolean)localObject).booleanValue());
    }
    localObject = zzawd;
    if (localObject != null) {
      paramZzyy.addItem(4, ((Integer)localObject).intValue());
    }
    super.multiply(paramZzyy);
  }
}
