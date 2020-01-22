package com.google.android.android.internal.measurement;

import java.io.IOException;

public final class zzgb
  extends com.google.android.gms.internal.measurement.zzza<com.google.android.gms.internal.measurement.zzgb>
{
  public String zzafx = null;
  public Long zzawe = null;
  private Integer zzawf = null;
  public zzgc[] zzawg = zzgc.zzmn();
  public zzga[] zzawh = zzga.zzmm();
  public zzfu[] zzawi = zzfu.zzmi();
  private String zzawj = null;
  
  public zzgb()
  {
    zzcfc = null;
    zzcfm = -1;
  }
  
  public final boolean equals(Object paramObject)
  {
    if (paramObject == this) {
      return true;
    }
    if (!(paramObject instanceof zzgb)) {
      return false;
    }
    paramObject = (zzgb)paramObject;
    Object localObject = zzawe;
    if (localObject == null)
    {
      if (zzawe != null) {
        return false;
      }
    }
    else if (!((Long)localObject).equals(zzawe)) {
      return false;
    }
    localObject = zzafx;
    if (localObject == null)
    {
      if (zzafx != null) {
        return false;
      }
    }
    else if (!((String)localObject).equals(zzafx)) {
      return false;
    }
    localObject = zzawf;
    if (localObject == null)
    {
      if (zzawf != null) {
        return false;
      }
    }
    else if (!((Integer)localObject).equals(zzawf)) {
      return false;
    }
    if (!zzze.equals(zzawg, zzawg)) {
      return false;
    }
    if (!zzze.equals(zzawh, zzawh)) {
      return false;
    }
    if (!zzze.equals(zzawi, zzawi)) {
      return false;
    }
    localObject = zzawj;
    if (localObject == null)
    {
      if (zzawj != null) {
        return false;
      }
    }
    else if (!((String)localObject).equals(zzawj)) {
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
    Object localObject = zzawe;
    int i1 = 0;
    int i;
    if (localObject == null) {
      i = 0;
    } else {
      i = ((Long)localObject).hashCode();
    }
    localObject = zzafx;
    int j;
    if (localObject == null) {
      j = 0;
    } else {
      j = ((String)localObject).hashCode();
    }
    localObject = zzawf;
    int k;
    if (localObject == null) {
      k = 0;
    } else {
      k = ((Integer)localObject).hashCode();
    }
    int i3 = zzze.hashCode(zzawg);
    int i4 = zzze.hashCode(zzawh);
    int i5 = zzze.hashCode(zzawi);
    localObject = zzawj;
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
    return ((((((((i2 + 527) * 31 + i) * 31 + j) * 31 + k) * 31 + i3) * 31 + i4) * 31 + i5) * 31 + m) * 31 + n;
  }
  
  protected final int multiply()
  {
    int j = super.multiply();
    int i = j;
    Object localObject = zzawe;
    if (localObject != null) {
      i = j + zzyy.write(1, ((Long)localObject).longValue());
    }
    localObject = zzafx;
    int k = i;
    if (localObject != null) {
      k = i + zzyy.setProperty(2, (String)localObject);
    }
    localObject = zzawf;
    j = k;
    if (localObject != null) {
      j = k + zzyy.sendCommand(3, ((Integer)localObject).intValue());
    }
    localObject = zzawg;
    int m = 0;
    i = j;
    if (localObject != null)
    {
      i = j;
      if (localObject.length > 0)
      {
        k = 0;
        for (;;)
        {
          localObject = zzawg;
          i = j;
          if (k >= localObject.length) {
            break;
          }
          localObject = localObject[k];
          i = j;
          if (localObject != null) {
            i = j + zzyy.addMenuItem(4, (zzzg)localObject);
          }
          k += 1;
          j = i;
        }
      }
    }
    localObject = zzawh;
    j = i;
    if (localObject != null)
    {
      j = i;
      if (localObject.length > 0)
      {
        k = 0;
        for (;;)
        {
          localObject = zzawh;
          j = i;
          if (k >= localObject.length) {
            break;
          }
          localObject = localObject[k];
          j = i;
          if (localObject != null) {
            j = i + zzyy.addMenuItem(5, (zzzg)localObject);
          }
          k += 1;
          i = j;
        }
      }
    }
    localObject = zzawi;
    k = j;
    if (localObject != null)
    {
      k = j;
      if (localObject.length > 0)
      {
        i = m;
        for (;;)
        {
          localObject = zzawi;
          k = j;
          if (i >= localObject.length) {
            break;
          }
          localObject = localObject[i];
          k = j;
          if (localObject != null) {
            k = j + zzyy.addMenuItem(6, (zzzg)localObject);
          }
          i += 1;
          j = k;
        }
      }
    }
    localObject = zzawj;
    if (localObject != null) {
      return k + zzyy.setProperty(7, (String)localObject);
    }
    return k;
  }
  
  public final void multiply(zzyy paramZzyy)
    throws IOException
  {
    Object localObject = zzawe;
    if (localObject != null) {
      paramZzyy.add(1, ((Long)localObject).longValue());
    }
    localObject = zzafx;
    if (localObject != null) {
      paramZzyy.add(2, (String)localObject);
    }
    localObject = zzawf;
    if (localObject != null) {
      paramZzyy.addItem(3, ((Integer)localObject).intValue());
    }
    localObject = zzawg;
    int j = 0;
    int i;
    if ((localObject != null) && (localObject.length > 0))
    {
      i = 0;
      for (;;)
      {
        localObject = zzawg;
        if (i >= localObject.length) {
          break;
        }
        localObject = localObject[i];
        if (localObject != null) {
          paramZzyy.writeHeader(4, (zzzg)localObject);
        }
        i += 1;
      }
    }
    localObject = zzawh;
    if ((localObject != null) && (localObject.length > 0))
    {
      i = 0;
      for (;;)
      {
        localObject = zzawh;
        if (i >= localObject.length) {
          break;
        }
        localObject = localObject[i];
        if (localObject != null) {
          paramZzyy.writeHeader(5, (zzzg)localObject);
        }
        i += 1;
      }
    }
    localObject = zzawi;
    if ((localObject != null) && (localObject.length > 0))
    {
      i = j;
      for (;;)
      {
        localObject = zzawi;
        if (i >= localObject.length) {
          break;
        }
        localObject = localObject[i];
        if (localObject != null) {
          paramZzyy.writeHeader(6, (zzzg)localObject);
        }
        i += 1;
      }
    }
    localObject = zzawj;
    if (localObject != null) {
      paramZzyy.add(7, (String)localObject);
    }
    super.multiply(paramZzyy);
  }
}
