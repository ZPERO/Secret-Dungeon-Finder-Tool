package com.google.android.android.internal.measurement;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

final class zzzd
  implements Cloneable
{
  private Object value;
  private com.google.android.gms.internal.measurement.zzzb<?, ?> zzcfj;
  private List<com.google.android.gms.internal.measurement.zzzi> zzcfk = new ArrayList();
  
  zzzd() {}
  
  private final byte[] toByteArray()
    throws IOException
  {
    byte[] arrayOfByte = new byte[multiply()];
    multiply(zzyy.readFully(arrayOfByte));
    return arrayOfByte;
  }
  
  private final zzzd zzyv()
  {
    zzzd localZzzd = new zzzd();
    zzcfj = zzcfj;
    Object localObject1;
    Object localObject2;
    if (zzcfk == null)
    {
      zzcfk = null;
    }
    else
    {
      localObject1 = zzcfk;
      localObject2 = zzcfk;
    }
    AssertionError localAssertionError;
    try
    {
      ((List)localObject1).addAll((Collection)localObject2);
      if (value != null)
      {
        if ((value instanceof zzzg))
        {
          localObject1 = (zzzg)value;
          localObject1 = ((zzzg)localObject1).clone();
          value = ((zzzg)localObject1);
          return localZzzd;
        }
        if ((value instanceof byte[]))
        {
          localObject1 = (byte[])value;
          localObject1 = localObject1.clone();
          value = localObject1;
          return localZzzd;
        }
        boolean bool = value instanceof byte[][];
        int j = 0;
        int i = 0;
        Object localObject3;
        if (bool)
        {
          localObject1 = (byte[][])value;
          localObject2 = new byte[localObject1.length][];
          value = localObject2;
          while (i < localObject1.length)
          {
            localObject3 = localObject1[i];
            localObject3 = localObject3.clone();
            localObject2[i] = ((byte[])localObject3);
            i += 1;
          }
        }
        if ((value instanceof boolean[]))
        {
          localObject1 = (boolean[])value;
          localObject1 = localObject1.clone();
          value = localObject1;
          return localZzzd;
        }
        if ((value instanceof int[]))
        {
          localObject1 = (int[])value;
          localObject1 = localObject1.clone();
          value = localObject1;
          return localZzzd;
        }
        if ((value instanceof long[]))
        {
          localObject1 = (long[])value;
          localObject1 = localObject1.clone();
          value = localObject1;
          return localZzzd;
        }
        if ((value instanceof float[]))
        {
          localObject1 = (float[])value;
          localObject1 = localObject1.clone();
          value = localObject1;
          return localZzzd;
        }
        if ((value instanceof double[]))
        {
          localObject1 = (double[])value;
          localObject1 = localObject1.clone();
          value = localObject1;
          return localZzzd;
        }
        if ((value instanceof zzzg[]))
        {
          localObject1 = (zzzg[])value;
          localObject2 = new zzzg[localObject1.length];
          value = localObject2;
          i = j;
          while (i < localObject1.length)
          {
            localObject3 = localObject1[i];
            localObject3 = localObject3.clone();
            localObject2[i] = ((zzzg)localObject3);
            i += 1;
          }
        }
      }
      else
      {
        return localZzzd;
      }
    }
    catch (CloneNotSupportedException localCloneNotSupportedException)
    {
      localAssertionError = new AssertionError(localCloneNotSupportedException);
      throw localAssertionError;
    }
    return localAssertionError;
  }
  
  public final boolean equals(Object paramObject)
  {
    if (paramObject == this) {
      return true;
    }
    if (!(paramObject instanceof zzzd)) {
      return false;
    }
    paramObject = (zzzd)paramObject;
    if ((value != null) && (value != null))
    {
      localObject = zzcfj;
      if (localObject != zzcfj) {
        return false;
      }
      if (!zzcfd.isArray()) {
        return value.equals(value);
      }
      localObject = value;
      if ((localObject instanceof byte[])) {
        return Arrays.equals((byte[])localObject, (byte[])value);
      }
      if ((localObject instanceof int[])) {
        return Arrays.equals((int[])localObject, (int[])value);
      }
      if ((localObject instanceof long[])) {
        return Arrays.equals((long[])localObject, (long[])value);
      }
      if ((localObject instanceof float[])) {
        return Arrays.equals((float[])localObject, (float[])value);
      }
      if ((localObject instanceof double[])) {
        return Arrays.equals((double[])localObject, (double[])value);
      }
      if ((localObject instanceof boolean[])) {
        return Arrays.equals((boolean[])localObject, (boolean[])value);
      }
      return Arrays.deepEquals((Object[])localObject, (Object[])value);
    }
    Object localObject = zzcfk;
    if (localObject != null)
    {
      List localList = zzcfk;
      if (localList != null) {
        return ((List)localObject).equals(localList);
      }
    }
    try
    {
      boolean bool = Arrays.equals(toByteArray(), paramObject.toByteArray());
      return bool;
    }
    catch (IOException paramObject)
    {
      throw new IllegalStateException(paramObject);
    }
  }
  
  public final int hashCode()
  {
    try
    {
      int i = Arrays.hashCode(toByteArray());
      return i + 527;
    }
    catch (IOException localIOException)
    {
      throw new IllegalStateException(localIOException);
    }
  }
  
  final int multiply()
  {
    Object localObject1 = value;
    int i = 0;
    Object localObject2;
    int j;
    if (localObject1 != null)
    {
      localObject2 = zzcfj;
      if (zzcfe)
      {
        int m = Array.getLength(localObject1);
        int k;
        for (j = 0; i < m; j = k)
        {
          k = j;
          if (Array.get(localObject1, i) != null) {
            k = j + ((zzzb)localObject2).zzak(Array.get(localObject1, i));
          }
          i += 1;
        }
      }
      return ((zzzb)localObject2).zzak(localObject1);
    }
    localObject1 = zzcfk.iterator();
    i = 0;
    while (((Iterator)localObject1).hasNext())
    {
      localObject2 = (zzzi)((Iterator)localObject1).next();
      i += zzyy.zzbj(columns) + 0 + zzbug.length;
    }
    return j;
    return i;
  }
  
  final void multiply(zzyy paramZzyy)
    throws IOException
  {
    Object localObject1 = value;
    Object localObject2;
    if (localObject1 != null)
    {
      localObject2 = zzcfj;
      if (zzcfe)
      {
        int j = Array.getLength(localObject1);
        int i = 0;
        while (i < j)
        {
          Object localObject3 = Array.get(localObject1, i);
          if (localObject3 != null) {
            ((zzzb)localObject2).add(localObject3, paramZzyy);
          }
          i += 1;
        }
        return;
      }
      ((zzzb)localObject2).add(localObject1, paramZzyy);
      return;
    }
    localObject1 = zzcfk.iterator();
    while (((Iterator)localObject1).hasNext())
    {
      localObject2 = (zzzi)((Iterator)localObject1).next();
      paramZzyy.zzca(columns);
      paramZzyy.add(zzbug);
    }
  }
  
  final Object readLong(zzzb paramZzzb)
  {
    if (value != null)
    {
      if (!zzcfj.equals(paramZzzb)) {
        throw new IllegalStateException("Tried to getExtension with a different Extension.");
      }
    }
    else
    {
      zzcfj = paramZzzb;
      value = paramZzzb.zzah(zzcfk);
      zzcfk = null;
    }
    return value;
  }
  
  final void sign(zzzi paramZzzi)
    throws IOException
  {
    Object localObject = zzcfk;
    if (localObject != null)
    {
      ((List)localObject).add(paramZzzi);
      return;
    }
    localObject = value;
    if ((localObject instanceof zzzg))
    {
      paramZzzi = zzbug;
      localObject = zzyx.get(paramZzzi, 0, paramZzzi.length);
      int i = ((zzyx)localObject).zzuy();
      if (i == paramZzzi.length - zzyy.zzbc(i)) {
        paramZzzi = ((zzzg)value).digest((zzyx)localObject);
      } else {
        throw zzzf.zzyw();
      }
    }
    else if ((localObject instanceof zzzg[]))
    {
      localObject = (zzzg[])zzcfj.zzah(Collections.singletonList(paramZzzi));
      zzzg[] arrayOfZzzg = (zzzg[])value;
      paramZzzi = (zzzg[])Arrays.copyOf(arrayOfZzzg, arrayOfZzzg.length + localObject.length);
      System.arraycopy(localObject, 0, paramZzzi, arrayOfZzzg.length, localObject.length);
    }
    else
    {
      paramZzzi = zzcfj.zzah(Collections.singletonList(paramZzzi));
    }
    zzcfj = zzcfj;
    value = paramZzzi;
    zzcfk = null;
  }
}
