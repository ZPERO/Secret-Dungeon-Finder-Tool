package com.google.android.android.internal.measurement;

import java.io.IOException;

public final class zzfy
  extends com.google.android.gms.internal.measurement.zzza<com.google.android.gms.internal.measurement.zzfy>
{
  private static volatile zzfy[] zzavt;
  public Boolean zzavb = null;
  public Boolean zzavc = null;
  public Integer zzave = null;
  public String zzavu = null;
  public zzfw zzavv = null;
  
  public zzfy()
  {
    zzcfc = null;
    zzcfm = -1;
  }
  
  public static zzfy[] zzml()
  {
    if (zzavt == null)
    {
      Object localObject = zzze.zzcfl;
      try
      {
        if (zzavt == null) {
          zzavt = new zzfy[0];
        }
      }
      catch (Throwable localThrowable)
      {
        throw localThrowable;
      }
    }
    return zzavt;
  }
  
  public final boolean equals(Object paramObject)
  {
    if (paramObject == this) {
      return true;
    }
    if (!(paramObject instanceof zzfy)) {
      return false;
    }
    paramObject = (zzfy)paramObject;
    Object localObject = zzave;
    if (localObject == null)
    {
      if (zzave != null) {
        return false;
      }
    }
    else if (!((Integer)localObject).equals(zzave)) {
      return false;
    }
    localObject = zzavu;
    if (localObject == null)
    {
      if (zzavu != null) {
        return false;
      }
    }
    else if (!((String)localObject).equals(zzavu)) {
      return false;
    }
    localObject = zzavv;
    if (localObject == null)
    {
      if (zzavv != null) {
        return false;
      }
    }
    else if (!((zzfw)localObject).equals(zzavv)) {
      return false;
    }
    localObject = zzavb;
    if (localObject == null)
    {
      if (zzavb != null) {
        return false;
      }
    }
    else if (!((Boolean)localObject).equals(zzavb)) {
      return false;
    }
    localObject = zzavc;
    if (localObject == null)
    {
      if (zzavc != null) {
        return false;
      }
    }
    else if (!((Boolean)localObject).equals(zzavc)) {
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
    Object localObject = zzave;
    int i2 = 0;
    int i;
    if (localObject == null) {
      i = 0;
    } else {
      i = ((Integer)localObject).hashCode();
    }
    localObject = zzavu;
    int j;
    if (localObject == null) {
      j = 0;
    } else {
      j = ((String)localObject).hashCode();
    }
    localObject = zzavv;
    int k;
    if (localObject == null) {
      k = 0;
    } else {
      k = ((zzfw)localObject).hashCode();
    }
    localObject = zzavb;
    int m;
    if (localObject == null) {
      m = 0;
    } else {
      m = ((Boolean)localObject).hashCode();
    }
    localObject = zzavc;
    int n;
    if (localObject == null) {
      n = 0;
    } else {
      n = ((Boolean)localObject).hashCode();
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
    Object localObject = zzave;
    if (localObject != null) {
      i = j + zzyy.sendCommand(1, ((Integer)localObject).intValue());
    }
    localObject = zzavu;
    j = i;
    if (localObject != null) {
      j = i + zzyy.setProperty(2, (String)localObject);
    }
    localObject = zzavv;
    i = j;
    if (localObject != null) {
      i = j + zzyy.addMenuItem(3, (zzzg)localObject);
    }
    localObject = zzavb;
    j = i;
    if (localObject != null)
    {
      ((Boolean)localObject).booleanValue();
      j = i + (zzyy.zzbb(4) + 1);
    }
    localObject = zzavc;
    i = j;
    if (localObject != null)
    {
      ((Boolean)localObject).booleanValue();
      i = j + (zzyy.zzbb(5) + 1);
    }
    return i;
  }
  
  public final void multiply(zzyy paramZzyy)
    throws IOException
  {
    Object localObject = zzave;
    if (localObject != null) {
      paramZzyy.addItem(1, ((Integer)localObject).intValue());
    }
    localObject = zzavu;
    if (localObject != null) {
      paramZzyy.add(2, (String)localObject);
    }
    localObject = zzavv;
    if (localObject != null) {
      paramZzyy.writeHeader(3, (zzzg)localObject);
    }
    localObject = zzavb;
    if (localObject != null) {
      paramZzyy.add(4, ((Boolean)localObject).booleanValue());
    }
    localObject = zzavc;
    if (localObject != null) {
      paramZzyy.add(5, ((Boolean)localObject).booleanValue());
    }
    super.multiply(paramZzyy);
  }
}
