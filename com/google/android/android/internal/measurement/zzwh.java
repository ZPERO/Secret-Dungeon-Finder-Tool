package com.google.android.android.internal.measurement;

import com.google.android.gms.internal.measurement.zzvs;
import com.google.android.gms.internal.measurement.zzxe;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

final class zzwh
  extends com.google.android.gms.internal.measurement.zztz<Long>
  implements zzvs<Long>, zzxe, RandomAccess
{
  private static final zzwh zzcam;
  private int size;
  private long[] zzcan;
  
  static
  {
    zzwh localZzwh = new zzwh();
    zzcam = localZzwh;
    localZzwh.zzsm();
  }
  
  zzwh()
  {
    this(new long[10], 0);
  }
  
  private zzwh(long[] paramArrayOfLong, int paramInt)
  {
    zzcan = paramArrayOfLong;
    size = paramInt;
  }
  
  private final void ensureCapacity(int paramInt, long paramLong)
  {
    zztx();
    if (paramInt >= 0)
    {
      int i = size;
      if (paramInt <= i)
      {
        long[] arrayOfLong1 = zzcan;
        if (i < arrayOfLong1.length)
        {
          System.arraycopy(arrayOfLong1, paramInt, arrayOfLong1, paramInt + 1, i - paramInt);
        }
        else
        {
          long[] arrayOfLong2 = new long[i * 3 / 2 + 1];
          System.arraycopy(arrayOfLong1, 0, arrayOfLong2, 0, paramInt);
          System.arraycopy(zzcan, paramInt, arrayOfLong2, paramInt + 1, size - paramInt);
          zzcan = arrayOfLong2;
        }
        zzcan[paramInt] = paramLong;
        size += 1;
        modCount += 1;
        return;
      }
    }
    throw new IndexOutOfBoundsException(zzaj(paramInt));
  }
  
  private final void zzai(int paramInt)
  {
    if ((paramInt >= 0) && (paramInt < size)) {
      return;
    }
    throw new IndexOutOfBoundsException(zzaj(paramInt));
  }
  
  private final String zzaj(int paramInt)
  {
    int i = size;
    StringBuilder localStringBuilder = new StringBuilder(35);
    localStringBuilder.append("Index:");
    localStringBuilder.append(paramInt);
    localStringBuilder.append(", Size:");
    localStringBuilder.append(i);
    return localStringBuilder.toString();
  }
  
  public final boolean addAll(Collection paramCollection)
  {
    zztx();
    zzvo.checkNotNull(paramCollection);
    if (!(paramCollection instanceof zzwh)) {
      return super.addAll(paramCollection);
    }
    paramCollection = (zzwh)paramCollection;
    int i = size;
    if (i == 0) {
      return false;
    }
    int j = size;
    if (Integer.MAX_VALUE - j >= i)
    {
      i = j + i;
      long[] arrayOfLong = zzcan;
      if (i > arrayOfLong.length) {
        zzcan = Arrays.copyOf(arrayOfLong, i);
      }
      System.arraycopy(zzcan, 0, zzcan, size, size);
      size = i;
      modCount += 1;
      return true;
    }
    throw new OutOfMemoryError();
  }
  
  public final boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if (!(paramObject instanceof zzwh)) {
      return super.equals(paramObject);
    }
    paramObject = (zzwh)paramObject;
    if (size != size) {
      return false;
    }
    paramObject = zzcan;
    int i = 0;
    while (i < size)
    {
      if (zzcan[i] != paramObject[i]) {
        return false;
      }
      i += 1;
    }
    return true;
  }
  
  public final long getLong(int paramInt)
  {
    zzai(paramInt);
    return zzcan[paramInt];
  }
  
  public final int hashCode()
  {
    int j = 1;
    int i = 0;
    while (i < size)
    {
      j = j * 31 + zzvo.zzbf(zzcan[i]);
      i += 1;
    }
    return j;
  }
  
  public final boolean remove(Object paramObject)
  {
    zztx();
    int i = 0;
    while (i < size)
    {
      if (paramObject.equals(Long.valueOf(zzcan[i])))
      {
        paramObject = zzcan;
        System.arraycopy(paramObject, i + 1, paramObject, i, size - i);
        size -= 1;
        modCount += 1;
        return true;
      }
      i += 1;
    }
    return false;
  }
  
  protected final void removeRange(int paramInt1, int paramInt2)
  {
    zztx();
    if (paramInt2 >= paramInt1)
    {
      long[] arrayOfLong = zzcan;
      System.arraycopy(arrayOfLong, paramInt2, arrayOfLong, paramInt1, size - paramInt2);
      size -= paramInt2 - paramInt1;
      modCount += 1;
      return;
    }
    throw new IndexOutOfBoundsException("toIndex < fromIndex");
  }
  
  public final int size()
  {
    return size;
  }
  
  public final void zzbg(long paramLong)
  {
    ensureCapacity(size, paramLong);
  }
}
