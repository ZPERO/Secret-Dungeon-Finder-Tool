package androidx.collection;

public class SparseArrayCompat<E>
  implements Cloneable
{
  private static final Object DELETED = new Object();
  private boolean mGarbage = false;
  private int[] mKeys;
  private int mSize;
  private Object[] mValues;
  
  public SparseArrayCompat()
  {
    this(10);
  }
  
  public SparseArrayCompat(int paramInt)
  {
    if (paramInt == 0)
    {
      mKeys = ContainerHelpers.EMPTY_INTS;
      mValues = ContainerHelpers.EMPTY_OBJECTS;
      return;
    }
    paramInt = ContainerHelpers.idealIntArraySize(paramInt);
    mKeys = new int[paramInt];
    mValues = new Object[paramInt];
  }
  
  private void gc()
  {
    int m = mSize;
    int[] arrayOfInt = mKeys;
    Object[] arrayOfObject = mValues;
    int i = 0;
    int k;
    for (int j = 0; i < m; j = k)
    {
      Object localObject = arrayOfObject[i];
      k = j;
      if (localObject != DELETED)
      {
        if (i != j)
        {
          arrayOfInt[j] = arrayOfInt[i];
          arrayOfObject[j] = localObject;
          arrayOfObject[i] = null;
        }
        k = j + 1;
      }
      i += 1;
    }
    mGarbage = false;
    mSize = j;
  }
  
  public void append(int paramInt, Object paramObject)
  {
    int i = mSize;
    if ((i != 0) && (paramInt <= mKeys[(i - 1)]))
    {
      put(paramInt, paramObject);
      return;
    }
    if ((mGarbage) && (mSize >= mKeys.length)) {
      gc();
    }
    i = mSize;
    if (i >= mKeys.length)
    {
      int j = ContainerHelpers.idealIntArraySize(i + 1);
      int[] arrayOfInt = new int[j];
      Object[] arrayOfObject = new Object[j];
      Object localObject = mKeys;
      System.arraycopy(localObject, 0, arrayOfInt, 0, localObject.length);
      localObject = mValues;
      System.arraycopy(localObject, 0, arrayOfObject, 0, localObject.length);
      mKeys = arrayOfInt;
      mValues = arrayOfObject;
    }
    mKeys[i] = paramInt;
    mValues[i] = paramObject;
    mSize = (i + 1);
  }
  
  public void clear()
  {
    int j = mSize;
    Object[] arrayOfObject = mValues;
    int i = 0;
    while (i < j)
    {
      arrayOfObject[i] = null;
      i += 1;
    }
    mSize = 0;
    mGarbage = false;
  }
  
  public SparseArrayCompat clone()
  {
    try
    {
      Object localObject1 = super.clone();
      localObject1 = (SparseArrayCompat)localObject1;
      Object localObject2 = mKeys;
      localObject2 = localObject2.clone();
      mKeys = ((int[])localObject2);
      localObject2 = mValues;
      localObject2 = localObject2.clone();
      mValues = ((Object[])localObject2);
      return localObject1;
    }
    catch (CloneNotSupportedException localCloneNotSupportedException)
    {
      throw new AssertionError(localCloneNotSupportedException);
    }
  }
  
  public boolean containsKey(int paramInt)
  {
    return indexOfKey(paramInt) >= 0;
  }
  
  public boolean containsValue(Object paramObject)
  {
    return indexOfValue(paramObject) >= 0;
  }
  
  public void delete(int paramInt)
  {
    remove(paramInt);
  }
  
  public Object get(int paramInt)
  {
    return get(paramInt, null);
  }
  
  public Object get(int paramInt, Object paramObject)
  {
    paramInt = ContainerHelpers.binarySearch(mKeys, mSize, paramInt);
    Object localObject = paramObject;
    if (paramInt >= 0)
    {
      localObject = mValues;
      if (localObject[paramInt] == DELETED) {
        return paramObject;
      }
      localObject = localObject[paramInt];
    }
    return localObject;
  }
  
  public int indexOfKey(int paramInt)
  {
    if (mGarbage) {
      gc();
    }
    return ContainerHelpers.binarySearch(mKeys, mSize, paramInt);
  }
  
  public int indexOfValue(Object paramObject)
  {
    if (mGarbage) {
      gc();
    }
    int i = 0;
    while (i < mSize)
    {
      if (mValues[i] == paramObject) {
        return i;
      }
      i += 1;
    }
    return -1;
  }
  
  public boolean isEmpty()
  {
    return size() == 0;
  }
  
  public int keyAt(int paramInt)
  {
    if (mGarbage) {
      gc();
    }
    return mKeys[paramInt];
  }
  
  public void put(int paramInt, Object paramObject)
  {
    int i = ContainerHelpers.binarySearch(mKeys, mSize, paramInt);
    if (i >= 0)
    {
      mValues[i] = paramObject;
      return;
    }
    int j = i;
    Object localObject1;
    if (j < mSize)
    {
      localObject1 = mValues;
      if (localObject1[j] == DELETED)
      {
        mKeys[j] = paramInt;
        localObject1[j] = paramObject;
        return;
      }
    }
    i = j;
    if (mGarbage)
    {
      i = j;
      if (mSize >= mKeys.length)
      {
        gc();
        i = ContainerHelpers.binarySearch(mKeys, mSize, paramInt);
      }
    }
    j = mSize;
    if (j >= mKeys.length)
    {
      j = ContainerHelpers.idealIntArraySize(j + 1);
      localObject1 = new int[j];
      Object[] arrayOfObject = new Object[j];
      Object localObject2 = mKeys;
      System.arraycopy(localObject2, 0, localObject1, 0, localObject2.length);
      localObject2 = mValues;
      System.arraycopy(localObject2, 0, arrayOfObject, 0, localObject2.length);
      mKeys = ((int[])localObject1);
      mValues = arrayOfObject;
    }
    j = mSize;
    if (j - i != 0)
    {
      localObject1 = mKeys;
      int k = i + 1;
      System.arraycopy(localObject1, i, localObject1, k, j - i);
      localObject1 = mValues;
      System.arraycopy(localObject1, i, localObject1, k, mSize - i);
    }
    mKeys[i] = paramInt;
    mValues[i] = paramObject;
    mSize += 1;
  }
  
  public void putAll(SparseArrayCompat paramSparseArrayCompat)
  {
    int j = paramSparseArrayCompat.size();
    int i = 0;
    while (i < j)
    {
      put(paramSparseArrayCompat.keyAt(i), paramSparseArrayCompat.valueAt(i));
      i += 1;
    }
  }
  
  public Object putIfAbsent(int paramInt, Object paramObject)
  {
    Object localObject = get(paramInt);
    if (localObject == null) {
      put(paramInt, paramObject);
    }
    return localObject;
  }
  
  public void remove(int paramInt)
  {
    paramInt = ContainerHelpers.binarySearch(mKeys, mSize, paramInt);
    if (paramInt >= 0)
    {
      Object[] arrayOfObject = mValues;
      Object localObject1 = arrayOfObject[paramInt];
      Object localObject2 = DELETED;
      if (localObject1 != localObject2)
      {
        arrayOfObject[paramInt] = localObject2;
        mGarbage = true;
      }
    }
  }
  
  public boolean remove(int paramInt, Object paramObject)
  {
    paramInt = indexOfKey(paramInt);
    if (paramInt >= 0)
    {
      Object localObject = valueAt(paramInt);
      if ((paramObject == localObject) || ((paramObject != null) && (paramObject.equals(localObject))))
      {
        removeAt(paramInt);
        return true;
      }
    }
    return false;
  }
  
  public void removeAt(int paramInt)
  {
    Object[] arrayOfObject = mValues;
    Object localObject1 = arrayOfObject[paramInt];
    Object localObject2 = DELETED;
    if (localObject1 != localObject2)
    {
      arrayOfObject[paramInt] = localObject2;
      mGarbage = true;
    }
  }
  
  public void removeAtRange(int paramInt1, int paramInt2)
  {
    paramInt2 = Math.min(mSize, paramInt2 + paramInt1);
    while (paramInt1 < paramInt2)
    {
      removeAt(paramInt1);
      paramInt1 += 1;
    }
  }
  
  public Object replace(int paramInt, Object paramObject)
  {
    paramInt = indexOfKey(paramInt);
    if (paramInt >= 0)
    {
      Object[] arrayOfObject = mValues;
      Object localObject = arrayOfObject[paramInt];
      arrayOfObject[paramInt] = paramObject;
      return localObject;
    }
    return null;
  }
  
  public boolean replace(int paramInt, Object paramObject1, Object paramObject2)
  {
    paramInt = indexOfKey(paramInt);
    if (paramInt >= 0)
    {
      Object localObject = mValues[paramInt];
      if ((localObject == paramObject1) || ((paramObject1 != null) && (paramObject1.equals(localObject))))
      {
        mValues[paramInt] = paramObject2;
        return true;
      }
    }
    return false;
  }
  
  public void setValueAt(int paramInt, Object paramObject)
  {
    if (mGarbage) {
      gc();
    }
    mValues[paramInt] = paramObject;
  }
  
  public int size()
  {
    if (mGarbage) {
      gc();
    }
    return mSize;
  }
  
  public String toString()
  {
    if (size() <= 0) {
      return "{}";
    }
    StringBuilder localStringBuilder = new StringBuilder(mSize * 28);
    localStringBuilder.append('{');
    int i = 0;
    while (i < mSize)
    {
      if (i > 0) {
        localStringBuilder.append(", ");
      }
      localStringBuilder.append(keyAt(i));
      localStringBuilder.append('=');
      Object localObject = valueAt(i);
      if (localObject != this) {
        localStringBuilder.append(localObject);
      } else {
        localStringBuilder.append("(this Map)");
      }
      i += 1;
    }
    localStringBuilder.append('}');
    return localStringBuilder.toString();
  }
  
  public Object valueAt(int paramInt)
  {
    if (mGarbage) {
      gc();
    }
    return mValues[paramInt];
  }
}
