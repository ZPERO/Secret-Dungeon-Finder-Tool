package com.google.android.android.internal.measurement;

import java.io.IOException;

public final class zzgd
  extends com.google.android.gms.internal.measurement.zzza<com.google.android.gms.internal.measurement.zzgd>
{
  private static volatile zzgd[] zzawl;
  public Integer zzauy = null;
  public zzgj zzawm = null;
  public zzgj zzawn = null;
  public Boolean zzawo = null;
  
  public zzgd()
  {
    zzcfc = null;
    zzcfm = -1;
  }
  
  public static zzgd[] zzmo()
  {
    if (zzawl == null)
    {
      Object localObject = zzze.zzcfl;
      try
      {
        if (zzawl == null) {
          zzawl = new zzgd[0];
        }
      }
      catch (Throwable localThrowable)
      {
        throw localThrowable;
      }
    }
    return zzawl;
  }
  
  public final boolean equals(Object paramObject)
  {
    if (paramObject == this) {
      return true;
    }
    if (!(paramObject instanceof zzgd)) {
      return false;
    }
    paramObject = (zzgd)paramObject;
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
    localObject = zzawm;
    if (localObject == null)
    {
      if (zzawm != null) {
        return false;
      }
    }
    else if (!((zzgj)localObject).equals(zzawm)) {
      return false;
    }
    localObject = zzawn;
    if (localObject == null)
    {
      if (zzawn != null) {
        return false;
      }
    }
    else if (!((zzgj)localObject).equals(zzawn)) {
      return false;
    }
    localObject = zzawo;
    if (localObject == null)
    {
      if (zzawo != null) {
        return false;
      }
    }
    else if (!((Boolean)localObject).equals(zzawo)) {
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
    Object localObject = zzauy;
    int i1 = 0;
    int i;
    if (localObject == null) {
      i = 0;
    } else {
      i = ((Integer)localObject).hashCode();
    }
    localObject = zzawm;
    int j;
    if (localObject == null) {
      j = 0;
    } else {
      j = ((zzgj)localObject).hashCode();
    }
    localObject = zzawn;
    int k;
    if (localObject == null) {
      k = 0;
    } else {
      k = ((zzgj)localObject).hashCode();
    }
    localObject = zzawo;
    int m;
    if (localObject == null) {
      m = 0;
    } else {
      m = ((Boolean)localObject).hashCode();
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
    Object localObject = zzauy;
    if (localObject != null) {
      i = j + zzyy.sendCommand(1, ((Integer)localObject).intValue());
    }
    localObject = zzawm;
    j = i;
    if (localObject != null) {
      j = i + zzyy.addMenuItem(2, (zzzg)localObject);
    }
    localObject = zzawn;
    i = j;
    if (localObject != null) {
      i = j + zzyy.addMenuItem(3, (zzzg)localObject);
    }
    localObject = zzawo;
    j = i;
    if (localObject != null)
    {
      ((Boolean)localObject).booleanValue();
      j = i + (zzyy.zzbb(4) + 1);
    }
    return j;
  }
  
  public final void multiply(zzyy paramZzyy)
    throws IOException
  {
    Object localObject = zzauy;
    if (localObject != null) {
      paramZzyy.addItem(1, ((Integer)localObject).intValue());
    }
    localObject = zzawm;
    if (localObject != null) {
      paramZzyy.writeHeader(2, (zzzg)localObject);
    }
    localObject = zzawn;
    if (localObject != null) {
      paramZzyy.writeHeader(3, (zzzg)localObject);
    }
    localObject = zzawo;
    if (localObject != null) {
      paramZzyy.add(4, ((Boolean)localObject).booleanValue());
    }
    super.multiply(paramZzyy);
  }
}
