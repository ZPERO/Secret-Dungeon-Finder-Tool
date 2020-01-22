package com.google.android.android.internal.measurement;

import com.google.android.gms.internal.measurement.zzvs;
import com.google.android.gms.internal.measurement.zzxe;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

final class zzvn
  extends com.google.android.gms.internal.measurement.zztz<Integer>
  implements zzvs<Integer>, zzxe, RandomAccess
{
  private static final zzvn zzbzh;
  private int size;
  private int[] zzbzi;
  
  static
  {
    zzvn localZzvn = new zzvn();
    zzbzh = localZzvn;
    localZzvn.zzsm();
  }
  
  zzvn()
  {
    this(new int[10], 0);
  }
  
  private zzvn(int[] paramArrayOfInt, int paramInt)
  {
    zzbzi = paramArrayOfInt;
    size = paramInt;
  }
  
  private final void ensureCapacity(int paramInt1, int paramInt2)
  {
    zztx();
    if (paramInt1 >= 0)
    {
      int i = size;
      if (paramInt1 <= i)
      {
        int[] arrayOfInt1 = zzbzi;
        if (i < arrayOfInt1.length)
        {
          System.arraycopy(arrayOfInt1, paramInt1, arrayOfInt1, paramInt1 + 1, i - paramInt1);
        }
        else
        {
          int[] arrayOfInt2 = new int[i * 3 / 2 + 1];
          System.arraycopy(arrayOfInt1, 0, arrayOfInt2, 0, paramInt1);
          System.arraycopy(zzbzi, paramInt1, arrayOfInt2, paramInt1 + 1, size - paramInt1);
          zzbzi = arrayOfInt2;
        }
        zzbzi[paramInt1] = paramInt2;
        size += 1;
        modCount += 1;
        return;
      }
    }
    throw new IndexOutOfBoundsException(zzaj(paramInt1));
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
    if (!(paramCollection instanceof zzvn)) {
      return super.addAll(paramCollection);
    }
    paramCollection = (zzvn)paramCollection;
    int i = size;
    if (i == 0) {
      return false;
    }
    int j = size;
    if (Integer.MAX_VALUE - j >= i)
    {
      i = j + i;
      int[] arrayOfInt = zzbzi;
      if (i > arrayOfInt.length) {
        zzbzi = Arrays.copyOf(arrayOfInt, i);
      }
      System.arraycopy(zzbzi, 0, zzbzi, size, size);
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
    if (!(paramObject instanceof zzvn)) {
      return super.equals(paramObject);
    }
    paramObject = (zzvn)paramObject;
    if (size != size) {
      return false;
    }
    paramObject = zzbzi;
    int i = 0;
    while (i < size)
    {
      if (zzbzi[i] != paramObject[i]) {
        return false;
      }
      i += 1;
    }
    return true;
  }
  
  public final int getInt(int paramInt)
  {
    zzai(paramInt);
    return zzbzi[paramInt];
  }
  
  public final int hashCode()
  {
    int j = 1;
    int i = 0;
    while (i < size)
    {
      j = j * 31 + zzbzi[i];
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
      if (paramObject.equals(Integer.valueOf(zzbzi[i])))
      {
        paramObject = zzbzi;
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
      int[] arrayOfInt = zzbzi;
      System.arraycopy(arrayOfInt, paramInt2, arrayOfInt, paramInt1, size - paramInt2);
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
  
  public final void zzbm(int paramInt)
  {
    ensureCapacity(size, paramInt);
  }
}
