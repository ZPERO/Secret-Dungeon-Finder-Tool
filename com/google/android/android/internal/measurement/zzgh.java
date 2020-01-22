package com.google.android.android.internal.measurement;

import java.io.IOException;

public final class zzgh
  extends com.google.android.gms.internal.measurement.zzza<com.google.android.gms.internal.measurement.zzgh>
{
  public zzgi[] zzawy = zzgi.zzms();
  
  public zzgh()
  {
    zzcfc = null;
    zzcfm = -1;
  }
  
  public final boolean equals(Object paramObject)
  {
    if (paramObject == this) {
      return true;
    }
    if (!(paramObject instanceof zzgh)) {
      return false;
    }
    paramObject = (zzgh)paramObject;
    if (!zzze.equals(zzawy, zzawy)) {
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
    int k = zzze.hashCode(zzawy);
    int i;
    if ((zzcfc != null) && (!zzcfc.isEmpty())) {
      i = zzcfc.hashCode();
    } else {
      i = 0;
    }
    return ((j + 527) * 31 + k) * 31 + i;
  }
  
  protected final int multiply()
  {
    int i = super.multiply();
    int j = i;
    Object localObject = zzawy;
    if ((localObject != null) && (localObject.length > 0))
    {
      i = 0;
      for (;;)
      {
        localObject = zzawy;
        if (i >= localObject.length) {
          break;
        }
        localObject = localObject[i];
        int k = j;
        if (localObject != null) {
          k = j + zzyy.addMenuItem(1, (zzzg)localObject);
        }
        i += 1;
        j = k;
      }
    }
    return i;
    return j;
  }
  
  public final void multiply(zzyy paramZzyy)
    throws IOException
  {
    Object localObject = zzawy;
    if ((localObject != null) && (localObject.length > 0))
    {
      int i = 0;
      for (;;)
      {
        localObject = zzawy;
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
    super.multiply(paramZzyy);
  }
}
