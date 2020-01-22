package com.google.android.android.internal.measurement;

import java.io.IOException;

public final class zzfu
  extends com.google.android.gms.internal.measurement.zzza<com.google.android.gms.internal.measurement.zzfu>
{
  private static volatile zzfu[] zzaux;
  public Integer zzauy = null;
  public zzfy[] zzauz = zzfy.zzml();
  public zzfv[] zzava = zzfv.zzmj();
  private Boolean zzavb = null;
  private Boolean zzavc = null;
  
  public zzfu()
  {
    zzcfc = null;
    zzcfm = -1;
  }
  
  public static zzfu[] zzmi()
  {
    if (zzaux == null)
    {
      Object localObject = zzze.zzcfl;
      try
      {
        if (zzaux == null) {
          zzaux = new zzfu[0];
        }
      }
      catch (Throwable localThrowable)
      {
        throw localThrowable;
      }
    }
    return zzaux;
  }
  
  public final boolean equals(Object paramObject)
  {
    if (paramObject == this) {
      return true;
    }
    if (!(paramObject instanceof zzfu)) {
      return false;
    }
    paramObject = (zzfu)paramObject;
    Object localObject = zzauy;
    if (localObject == null)
    {
      if (zzauy != null) {
        return false;
      }
    }
    else if (!((Integer)localObject).equals(zzauy)) {
      return false;
    }
    if (!zzze.equals(zzauz, zzauz)) {
      return false;
    }
    if (!zzze.equals(zzava, zzava)) {
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
    int i1 = getClass().getName().hashCode();
    Object localObject = zzauy;
    int n = 0;
    int i;
    if (localObject == null) {
      i = 0;
    } else {
      i = ((Integer)localObject).hashCode();
    }
    int i2 = zzze.hashCode(zzauz);
    int i3 = zzze.hashCode(zzava);
    localObject = zzavb;
    int j;
    if (localObject == null) {
      j = 0;
    } else {
      j = ((Boolean)localObject).hashCode();
    }
    localObject = zzavc;
    int k;
    if (localObject == null) {
      k = 0;
    } else {
      k = ((Boolean)localObject).hashCode();
    }
    int m = n;
    if (zzcfc != null) {
      if (zzcfc.isEmpty()) {
        m = n;
      } else {
        m = zzcfc.hashCode();
      }
    }
    return ((((((i1 + 527) * 31 + i) * 31 + i2) * 31 + i3) * 31 + j) * 31 + k) * 31 + m;
  }
  
  protected final int multiply()
  {
    int j = super.multiply();
    int i = j;
    Object localObject = zzauy;
    if (localObject != null) {
      i = j + zzyy.sendCommand(1, ((Integer)localObject).intValue());
    }
    localObject = zzauz;
    int m = 0;
    j = i;
    int k;
    if (localObject != null)
    {
      j = i;
      if (localObject.length > 0)
      {
        k = 0;
        for (;;)
        {
          localObject = zzauz;
          j = i;
          if (k >= localObject.length) {
            break;
          }
          localObject = localObject[k];
          j = i;
          if (localObject != null) {
            j = i + zzyy.addMenuItem(2, (zzzg)localObject);
          }
          k += 1;
          i = j;
        }
      }
    }
    localObject = zzava;
    i = j;
    if (localObject != null)
    {
      i = j;
      if (localObject.length > 0)
      {
        k = m;
        for (;;)
        {
          localObject = zzava;
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
    Object localObject = zzauy;
    if (localObject != null) {
      paramZzyy.addItem(1, ((Integer)localObject).intValue());
    }
    localObject = zzauz;
    int j = 0;
    int i;
    if ((localObject != null) && (localObject.length > 0))
    {
      i = 0;
      for (;;)
      {
        localObject = zzauz;
        if (i >= localObject.length) {
          break;
        }
        localObject = localObject[i];
        if (localObject != null) {
          paramZzyy.writeHeader(2, (zzzg)localObject);
        }
        i += 1;
      }
    }
    localObject = zzava;
    if ((localObject != null) && (localObject.length > 0))
    {
      i = j;
      for (;;)
      {
        localObject = zzava;
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
