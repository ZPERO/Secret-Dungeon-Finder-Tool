package com.google.android.android.internal.measurement;

public final class zzzc
  implements Cloneable
{
  private static final zzzd zzcff = new zzzd();
  private int mSize;
  private boolean zzcfg = false;
  private int[] zzcfh;
  private zzzd[] zzcfi;
  
  zzzc()
  {
    this(10);
  }
  
  private zzzc(int paramInt)
  {
    paramInt = idealIntArraySize(paramInt);
    zzcfh = new int[paramInt];
    zzcfi = new zzzd[paramInt];
    mSize = 0;
  }
  
  private static int idealIntArraySize(int paramInt)
  {
    int j = paramInt << 2;
    paramInt = 4;
    int i;
    for (;;)
    {
      i = j;
      if (paramInt >= 32) {
        break;
      }
      i = (1 << paramInt) - 12;
      if (j <= i) {
        break;
      }
      paramInt += 1;
    }
    return i / 4;
  }
  
  private final int zzcd(int paramInt)
  {
    int j = mSize - 1;
    int i = 0;
    while (i <= j)
    {
      int k = i + j >>> 1;
      int m = zzcfh[k];
      if (m < paramInt) {
        i = k + 1;
      } else if (m > paramInt) {
        j = k - 1;
      } else {
        return k;
      }
    }
    return i;
  }
  
  final void ensureCapacity(int paramInt, zzzd paramZzzd)
  {
    int i = zzcd(paramInt);
    if (i >= 0)
    {
      zzcfi[i] = paramZzzd;
      return;
    }
    i = i;
    Object localObject1;
    if (i < mSize)
    {
      localObject1 = zzcfi;
      if (localObject1[i] == zzcff)
      {
        zzcfh[i] = paramInt;
        localObject1[i] = paramZzzd;
        return;
      }
    }
    int j = mSize;
    if (j >= zzcfh.length)
    {
      j = idealIntArraySize(j + 1);
      localObject1 = new int[j];
      zzzd[] arrayOfZzzd = new zzzd[j];
      Object localObject2 = zzcfh;
      System.arraycopy(localObject2, 0, localObject1, 0, localObject2.length);
      localObject2 = zzcfi;
      System.arraycopy(localObject2, 0, arrayOfZzzd, 0, localObject2.length);
      zzcfh = ((int[])localObject1);
      zzcfi = arrayOfZzzd;
    }
    j = mSize;
    if (j - i != 0)
    {
      localObject1 = zzcfh;
      int k = i + 1;
      System.arraycopy(localObject1, i, localObject1, k, j - i);
      localObject1 = zzcfi;
      System.arraycopy(localObject1, i, localObject1, k, mSize - i);
    }
    zzcfh[i] = paramInt;
    zzcfi[i] = paramZzzd;
    mSize += 1;
  }
  
  public final boolean equals(Object paramObject)
  {
    if (paramObject == this) {
      return true;
    }
    if (!(paramObject instanceof zzzc)) {
      return false;
    }
    paramObject = (zzzc)paramObject;
    int j = mSize;
    if (j != mSize) {
      return false;
    }
    Object localObject = zzcfh;
    int[] arrayOfInt = zzcfh;
    int i = 0;
    while (i < j)
    {
      if (localObject[i] != arrayOfInt[i])
      {
        i = 0;
        break label80;
      }
      i += 1;
    }
    i = 1;
    label80:
    if (i != 0)
    {
      localObject = zzcfi;
      paramObject = zzcfi;
      j = mSize;
      i = 0;
      while (i < j)
      {
        if (!localObject[i].equals(paramObject[i]))
        {
          i = 0;
          break label134;
        }
        i += 1;
      }
      i = 1;
      label134:
      if (i != 0) {
        return true;
      }
    }
    return false;
  }
  
  public final int hashCode()
  {
    int j = 17;
    int i = 0;
    while (i < mSize)
    {
      j = (j * 31 + zzcfh[i]) * 31 + zzcfi[i].hashCode();
      i += 1;
    }
    return j;
  }
  
  public final boolean isEmpty()
  {
    return mSize == 0;
  }
  
  final int size()
  {
    return mSize;
  }
  
  final zzzd zzcb(int paramInt)
  {
    paramInt = zzcd(paramInt);
    if (paramInt >= 0)
    {
      zzzd[] arrayOfZzzd = zzcfi;
      if (arrayOfZzzd[paramInt] != zzcff) {
        return arrayOfZzzd[paramInt];
      }
    }
    return null;
  }
  
  final zzzd zzcc(int paramInt)
  {
    return zzcfi[paramInt];
  }
}
