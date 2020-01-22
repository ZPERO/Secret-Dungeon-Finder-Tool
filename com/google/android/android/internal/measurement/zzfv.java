package com.google.android.android.internal.measurement;

import java.io.IOException;

public final class zzfv
  extends com.google.android.gms.internal.measurement.zzza<com.google.android.gms.internal.measurement.zzfv>
{
  private static volatile zzfv[] zzavd;
  public Boolean zzavb = null;
  public Boolean zzavc = null;
  public Integer zzave = null;
  public String zzavf = null;
  public zzfw[] zzavg = zzfw.zzmk();
  private Boolean zzavh = null;
  public zzfx zzavi = null;
  
  public zzfv()
  {
    zzcfc = null;
    zzcfm = -1;
  }
  
  public static zzfv[] zzmj()
  {
    if (zzavd == null)
    {
      Object localObject = zzze.zzcfl;
      try
      {
        if (zzavd == null) {
          zzavd = new zzfv[0];
        }
      }
      catch (Throwable localThrowable)
      {
        throw localThrowable;
      }
    }
    return zzavd;
  }
  
  public final boolean equals(Object paramObject)
  {
    if (paramObject == this) {
      return true;
    }
    if (!(paramObject instanceof zzfv)) {
      return false;
    }
    paramObject = (zzfv)paramObject;
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
    localObject = zzavf;
    if (localObject == null)
    {
      if (zzavf != null) {
        return false;
      }
    }
    else if (!((String)localObject).equals(zzavf)) {
      return false;
    }
    if (!zzze.equals(zzavg, zzavg)) {
      return false;
    }
    localObject = zzavh;
    if (localObject == null)
    {
      if (zzavh != null) {
        return false;
      }
    }
    else if (!((Boolean)localObject).equals(zzavh)) {
      return false;
    }
    localObject = zzavi;
    if (localObject == null)
    {
      if (zzavi != null) {
        return false;
      }
    }
    else if (!((zzfx)localObject).equals(zzavi)) {
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
    int i4 = getClass().getName().hashCode();
    Object localObject = zzave;
    int i3 = 0;
    int i;
    if (localObject == null) {
      i = 0;
    } else {
      i = ((Integer)localObject).hashCode();
    }
    localObject = zzavf;
    int j;
    if (localObject == null) {
      j = 0;
    } else {
      j = ((String)localObject).hashCode();
    }
    int i5 = zzze.hashCode(zzavg);
    localObject = zzavh;
    int k;
    if (localObject == null) {
      k = 0;
    } else {
      k = ((Boolean)localObject).hashCode();
    }
    localObject = zzavi;
    int m;
    if (localObject == null) {
      m = 0;
    } else {
      m = ((zzfx)localObject).hashCode();
    }
    localObject = zzavb;
    int n;
    if (localObject == null) {
      n = 0;
    } else {
      n = ((Boolean)localObject).hashCode();
    }
    localObject = zzavc;
    int i1;
    if (localObject == null) {
      i1 = 0;
    } else {
      i1 = ((Boolean)localObject).hashCode();
    }
    int i2 = i3;
    if (zzcfc != null) {
      if (zzcfc.isEmpty()) {
        i2 = i3;
      } else {
        i2 = zzcfc.hashCode();
      }
    }
    return ((((((((i4 + 527) * 31 + i) * 31 + j) * 31 + i5) * 31 + k) * 31 + m) * 31 + n) * 31 + i1) * 31 + i2;
  }
  
  protected final int multiply()
  {
    int i = super.multiply();
    int j = i;
    Object localObject = zzave;
    if (localObject != null) {
      j = i + zzyy.sendCommand(1, ((Integer)localObject).intValue());
    }
    localObject = zzavf;
    i = j;
    if (localObject != null) {
      i = j + zzyy.setProperty(2, (String)localObject);
    }
    localObject = zzavg;
    j = i;
    if (localObject != null)
    {
      j = i;
      if (localObject.length > 0)
      {
        int k = 0;
        for (;;)
        {
          localObject = zzavg;
          j = i;
          if (k >= localObject.length) {
            break;
          }
          localObject = localObject[k];
          j = i;
          if (localObject != null) {
            j = i + zzyy.addMenuItem(3, (zzzg)localObject);
          }
          k += 1;
          i = j;
        }
      }
    }
    localObject = zzavh;
    i = j;
    if (localObject != null)
    {
      ((Boolean)localObject).booleanValue();
      i = j + (zzyy.zzbb(4) + 1);
    }
    localObject = zzavi;
    j = i;
    if (localObject != null) {
      j = i + zzyy.addMenuItem(5, (zzzg)localObject);
    }
    localObject = zzavb;
    i = j;
    if (localObject != null)
    {
      ((Boolean)localObject).booleanValue();
      i = j + (zzyy.zzbb(6) + 1);
    }
    localObject = zzavc;
    j = i;
    if (localObject != null)
    {
      ((Boolean)localObject).booleanValue();
      j = i + (zzyy.zzbb(7) + 1);
    }
    return j;
  }
  
  public final void multiply(zzyy paramZzyy)
    throws IOException
  {
    Object localObject = zzave;
    if (localObject != null) {
      paramZzyy.addItem(1, ((Integer)localObject).intValue());
    }
    localObject = zzavf;
    if (localObject != null) {
      paramZzyy.add(2, (String)localObject);
    }
    localObject = zzavg;
    if ((localObject != null) && (localObject.length > 0))
    {
      int i = 0;
      for (;;)
      {
        localObject = zzavg;
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
    localObject = zzavh;
    if (localObject != null) {
      paramZzyy.add(4, ((Boolean)localObject).booleanValue());
    }
    localObject = zzavi;
    if (localObject != null) {
      paramZzyy.writeHeader(5, (zzzg)localObject);
    }
    localObject = zzavb;
    if (localObject != null) {
      paramZzyy.add(6, ((Boolean)localObject).booleanValue());
    }
    localObject = zzavc;
    if (localObject != null) {
      paramZzyy.add(7, ((Boolean)localObject).booleanValue());
    }
    super.multiply(paramZzyy);
  }
}
