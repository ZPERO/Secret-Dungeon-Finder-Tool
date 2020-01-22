package com.google.android.android.internal.measurement;

import com.google.android.gms.internal.measurement.zzvs;
import com.google.android.gms.internal.measurement.zzxe;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

final class zzub
  extends com.google.android.gms.internal.measurement.zztz<Boolean>
  implements zzvs<Boolean>, zzxe, RandomAccess
{
  private static final zzub zzbtx;
  private int size;
  private boolean[] zzbty;
  
  static
  {
    zzub localZzub = new zzub();
    zzbtx = localZzub;
    localZzub.zzsm();
  }
  
  zzub()
  {
    this(new boolean[10], 0);
  }
  
  private zzub(boolean[] paramArrayOfBoolean, int paramInt)
  {
    zzbty = paramArrayOfBoolean;
    size = paramInt;
  }
  
  private final void add(int paramInt, boolean paramBoolean)
  {
    zztx();
    if (paramInt >= 0)
    {
      int i = size;
      if (paramInt <= i)
      {
        boolean[] arrayOfBoolean1 = zzbty;
        if (i < arrayOfBoolean1.length)
        {
          System.arraycopy(arrayOfBoolean1, paramInt, arrayOfBoolean1, paramInt + 1, i - paramInt);
        }
        else
        {
          boolean[] arrayOfBoolean2 = new boolean[i * 3 / 2 + 1];
          System.arraycopy(arrayOfBoolean1, 0, arrayOfBoolean2, 0, paramInt);
          System.arraycopy(zzbty, paramInt, arrayOfBoolean2, paramInt + 1, size - paramInt);
          zzbty = arrayOfBoolean2;
        }
        zzbty[paramInt] = paramBoolean;
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
    if (!(paramCollection instanceof zzub)) {
      return super.addAll(paramCollection);
    }
    paramCollection = (zzub)paramCollection;
    int i = size;
    if (i == 0) {
      return false;
    }
    int j = size;
    if (Integer.MAX_VALUE - j >= i)
    {
      i = j + i;
      boolean[] arrayOfBoolean = zzbty;
      if (i > arrayOfBoolean.length) {
        zzbty = Arrays.copyOf(arrayOfBoolean, i);
      }
      System.arraycopy(zzbty, 0, zzbty, size, size);
      size = i;
      modCount += 1;
      return true;
    }
    throw new OutOfMemoryError();
  }
  
  public final void addBoolean(boolean paramBoolean)
  {
    add(size, paramBoolean);
  }
  
  public final boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if (!(paramObject instanceof zzub)) {
      return super.equals(paramObject);
    }
    paramObject = (zzub)paramObject;
    if (size != size) {
      return false;
    }
    paramObject = zzbty;
    int i = 0;
    while (i < size)
    {
      if (zzbty[i] != paramObject[i]) {
        return false;
      }
      i += 1;
    }
    return true;
  }
  
  public final int hashCode()
  {
    int j = 1;
    int i = 0;
    while (i < size)
    {
      j = j * 31 + zzvo.hashCode(zzbty[i]);
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
      if (paramObject.equals(Boolean.valueOf(zzbty[i])))
      {
        paramObject = zzbty;
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
      boolean[] arrayOfBoolean = zzbty;
      System.arraycopy(arrayOfBoolean, paramInt2, arrayOfBoolean, paramInt1, size - paramInt2);
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
}
