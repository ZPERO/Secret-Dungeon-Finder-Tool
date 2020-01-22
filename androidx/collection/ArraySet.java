package androidx.collection;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public final class ArraySet<E>
  implements Collection<E>, Set<E>
{
  private static final int BASE_SIZE = 4;
  private static final int CACHE_SIZE = 10;
  private static final boolean DEBUG = false;
  private static final int[] EMPTY_INTS = new int[0];
  private static final Object[] OBJECT = new Object[0];
  private static final String PAGE_KEY = "ArraySet";
  private static Object[] sBaseCache;
  private static int sBaseCacheSize;
  private static Object[] sTwiceBaseCache;
  private static int sTwiceBaseCacheSize;
  Object[] mArray;
  private MapCollections<E, E> mCollections;
  private int[] mHashes;
  int mSize;
  
  public ArraySet()
  {
    this(0);
  }
  
  public ArraySet(int paramInt)
  {
    if (paramInt == 0)
    {
      mHashes = EMPTY_INTS;
      mArray = OBJECT;
    }
    else
    {
      allocArrays(paramInt);
    }
    mSize = 0;
  }
  
  public ArraySet(ArraySet paramArraySet)
  {
    this();
    if (paramArraySet != null) {
      addAll(paramArraySet);
    }
  }
  
  public ArraySet(Collection paramCollection)
  {
    this();
    if (paramCollection != null) {
      addAll(paramCollection);
    }
  }
  
  private void allocArrays(int paramInt)
  {
    if (paramInt == 8) {
      try
      {
        if (sTwiceBaseCache != null)
        {
          Object[] arrayOfObject1 = sTwiceBaseCache;
          mArray = arrayOfObject1;
          sTwiceBaseCache = (Object[])arrayOfObject1[0];
          mHashes = ((int[])arrayOfObject1[1]);
          arrayOfObject1[1] = null;
          arrayOfObject1[0] = null;
          sTwiceBaseCacheSize -= 1;
          return;
        }
      }
      catch (Throwable localThrowable1)
      {
        throw localThrowable1;
      }
    }
    if (paramInt == 4) {
      try
      {
        if (sBaseCache != null)
        {
          Object[] arrayOfObject2 = sBaseCache;
          mArray = arrayOfObject2;
          sBaseCache = (Object[])arrayOfObject2[0];
          mHashes = ((int[])arrayOfObject2[1]);
          arrayOfObject2[1] = null;
          arrayOfObject2[0] = null;
          sBaseCacheSize -= 1;
          return;
        }
      }
      catch (Throwable localThrowable2)
      {
        throw localThrowable2;
      }
    }
    mHashes = new int[paramInt];
    mArray = new Object[paramInt];
  }
  
  private static void freeArrays(int[] paramArrayOfInt, Object[] paramArrayOfObject, int paramInt)
  {
    if (paramArrayOfInt.length == 8) {
      try
      {
        if (sTwiceBaseCacheSize < 10)
        {
          paramArrayOfObject[0] = sTwiceBaseCache;
          paramArrayOfObject[1] = paramArrayOfInt;
          paramInt -= 1;
          break label114;
          sTwiceBaseCache = paramArrayOfObject;
          sTwiceBaseCacheSize += 1;
        }
        return;
      }
      catch (Throwable paramArrayOfInt)
      {
        throw paramArrayOfInt;
      }
    }
    if (paramArrayOfInt.length == 4) {}
    for (;;)
    {
      try
      {
        if (sBaseCacheSize < 10)
        {
          paramArrayOfObject[0] = sBaseCache;
          paramArrayOfObject[1] = paramArrayOfInt;
          paramInt -= 1;
          break label130;
          sBaseCache = paramArrayOfObject;
          sBaseCacheSize += 1;
        }
        return;
      }
      catch (Throwable paramArrayOfInt)
      {
        throw paramArrayOfInt;
      }
      return;
      label114:
      while (paramInt >= 2)
      {
        paramArrayOfObject[paramInt] = null;
        paramInt -= 1;
      }
      break;
      label130:
      while (paramInt >= 2)
      {
        paramArrayOfObject[paramInt] = null;
        paramInt -= 1;
      }
    }
  }
  
  private MapCollections getCollection()
  {
    if (mCollections == null) {
      mCollections = new MapCollections()
      {
        protected void colClear()
        {
          clear();
        }
        
        protected Object colGetEntry(int paramAnonymousInt1, int paramAnonymousInt2)
        {
          return mArray[paramAnonymousInt1];
        }
        
        protected Map colGetMap()
        {
          throw new UnsupportedOperationException("not a map");
        }
        
        protected int colGetSize()
        {
          return mSize;
        }
        
        protected int colIndexOfKey(Object paramAnonymousObject)
        {
          return indexOf(paramAnonymousObject);
        }
        
        protected int colIndexOfValue(Object paramAnonymousObject)
        {
          return indexOf(paramAnonymousObject);
        }
        
        protected void colPut(Object paramAnonymousObject1, Object paramAnonymousObject2)
        {
          add(paramAnonymousObject1);
        }
        
        protected void colRemoveAt(int paramAnonymousInt)
        {
          removeAt(paramAnonymousInt);
        }
        
        protected Object colSetValue(int paramAnonymousInt, Object paramAnonymousObject)
        {
          throw new UnsupportedOperationException("not a map");
        }
      };
    }
    return mCollections;
  }
  
  private int indexOf(Object paramObject, int paramInt)
  {
    int j = mSize;
    if (j == 0) {
      return -1;
    }
    int k = ContainerHelpers.binarySearch(mHashes, j, paramInt);
    if (k < 0) {
      return k;
    }
    if (paramObject.equals(mArray[k])) {
      return k;
    }
    int i = k + 1;
    while ((i < j) && (mHashes[i] == paramInt))
    {
      if (paramObject.equals(mArray[i])) {
        return i;
      }
      i += 1;
    }
    j = k - 1;
    while ((j >= 0) && (mHashes[j] == paramInt))
    {
      if (paramObject.equals(mArray[j])) {
        return j;
      }
      j -= 1;
    }
    return i;
  }
  
  private int indexOfNull()
  {
    int j = mSize;
    if (j == 0) {
      return -1;
    }
    int k = ContainerHelpers.binarySearch(mHashes, j, 0);
    if (k < 0) {
      return k;
    }
    if (mArray[k] == null) {
      return k;
    }
    int i = k + 1;
    while ((i < j) && (mHashes[i] == 0))
    {
      if (mArray[i] == null) {
        return i;
      }
      i += 1;
    }
    j = k - 1;
    while ((j >= 0) && (mHashes[j] == 0))
    {
      if (mArray[j] == null) {
        return j;
      }
      j -= 1;
    }
    return i;
  }
  
  public boolean add(Object paramObject)
  {
    int j;
    if (paramObject == null)
    {
      i = indexOfNull();
      j = 0;
    }
    else
    {
      j = paramObject.hashCode();
      i = indexOf(paramObject, j);
    }
    if (i >= 0) {
      return false;
    }
    int k = i;
    int m = mSize;
    Object localObject;
    if (m >= mHashes.length)
    {
      i = 4;
      if (m >= 8) {
        i = (m >> 1) + m;
      } else if (m >= 4) {
        i = 8;
      }
      localObject = mHashes;
      Object[] arrayOfObject = mArray;
      allocArrays(i);
      int[] arrayOfInt = mHashes;
      if (arrayOfInt.length > 0)
      {
        System.arraycopy(localObject, 0, arrayOfInt, 0, localObject.length);
        System.arraycopy(arrayOfObject, 0, mArray, 0, arrayOfObject.length);
      }
      freeArrays((int[])localObject, arrayOfObject, mSize);
    }
    int i = mSize;
    if (k < i)
    {
      localObject = mHashes;
      m = k + 1;
      System.arraycopy(localObject, k, localObject, m, i - k);
      localObject = mArray;
      System.arraycopy(localObject, k, localObject, m, mSize - k);
    }
    mHashes[k] = j;
    mArray[k] = paramObject;
    mSize += 1;
    return true;
  }
  
  public void addAll(ArraySet paramArraySet)
  {
    int j = mSize;
    ensureCapacity(mSize + j);
    int k = mSize;
    int i = 0;
    if (k == 0)
    {
      if (j > 0)
      {
        System.arraycopy(mHashes, 0, mHashes, 0, j);
        System.arraycopy(mArray, 0, mArray, 0, j);
        mSize = j;
      }
    }
    else {
      while (i < j)
      {
        add(paramArraySet.valueAt(i));
        i += 1;
      }
    }
  }
  
  public boolean addAll(Collection paramCollection)
  {
    ensureCapacity(mSize + paramCollection.size());
    paramCollection = paramCollection.iterator();
    boolean bool = false;
    while (paramCollection.hasNext()) {
      bool |= add(paramCollection.next());
    }
    return bool;
  }
  
  public void clear()
  {
    int i = mSize;
    if (i != 0)
    {
      freeArrays(mHashes, mArray, i);
      mHashes = EMPTY_INTS;
      mArray = OBJECT;
      mSize = 0;
    }
  }
  
  public boolean contains(Object paramObject)
  {
    return indexOf(paramObject) >= 0;
  }
  
  public boolean containsAll(Collection paramCollection)
  {
    paramCollection = paramCollection.iterator();
    while (paramCollection.hasNext()) {
      if (!contains(paramCollection.next())) {
        return false;
      }
    }
    return true;
  }
  
  public void ensureCapacity(int paramInt)
  {
    int[] arrayOfInt = mHashes;
    if (arrayOfInt.length < paramInt)
    {
      Object[] arrayOfObject = mArray;
      allocArrays(paramInt);
      paramInt = mSize;
      if (paramInt > 0)
      {
        System.arraycopy(arrayOfInt, 0, mHashes, 0, paramInt);
        System.arraycopy(arrayOfObject, 0, mArray, 0, mSize);
      }
      freeArrays(arrayOfInt, arrayOfObject, mSize);
    }
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    int i;
    if ((paramObject instanceof Set))
    {
      paramObject = (Set)paramObject;
      if (size() != paramObject.size()) {
        return false;
      }
      i = 0;
    }
    for (;;)
    {
      try
      {
        int j = mSize;
        if (i >= j) {}
      }
      catch (NullPointerException paramObject)
      {
        boolean bool;
        return false;
      }
      try
      {
        bool = paramObject.contains(valueAt(i));
        if (!bool) {
          return false;
        }
        i += 1;
      }
      catch (ClassCastException paramObject) {}
    }
    return true;
    return false;
    return false;
  }
  
  public int hashCode()
  {
    int[] arrayOfInt = mHashes;
    int k = mSize;
    int i = 0;
    int j = 0;
    while (i < k)
    {
      j += arrayOfInt[i];
      i += 1;
    }
    return j;
  }
  
  public int indexOf(Object paramObject)
  {
    if (paramObject == null) {
      return indexOfNull();
    }
    return indexOf(paramObject, paramObject.hashCode());
  }
  
  public boolean isEmpty()
  {
    return mSize <= 0;
  }
  
  public Iterator iterator()
  {
    return getCollection().getKeySet().iterator();
  }
  
  public boolean remove(Object paramObject)
  {
    int i = indexOf(paramObject);
    if (i >= 0)
    {
      removeAt(i);
      return true;
    }
    return false;
  }
  
  public boolean removeAll(ArraySet paramArraySet)
  {
    int j = mSize;
    int k = mSize;
    int i = 0;
    while (i < j)
    {
      remove(paramArraySet.valueAt(i));
      i += 1;
    }
    return k != mSize;
  }
  
  public boolean removeAll(Collection paramCollection)
  {
    paramCollection = paramCollection.iterator();
    boolean bool = false;
    while (paramCollection.hasNext()) {
      bool |= remove(paramCollection.next());
    }
    return bool;
  }
  
  public Object removeAt(int paramInt)
  {
    Object localObject2 = mArray;
    Object localObject1 = localObject2[paramInt];
    int j = mSize;
    if (j <= 1)
    {
      freeArrays(mHashes, (Object[])localObject2, j);
      mHashes = EMPTY_INTS;
      mArray = OBJECT;
      mSize = 0;
      return localObject1;
    }
    localObject2 = mHashes;
    int k = localObject2.length;
    int i = 8;
    if ((k > 8) && (j < localObject2.length / 3))
    {
      if (j > 8) {
        i = j + (j >> 1);
      }
      localObject2 = mHashes;
      Object[] arrayOfObject = mArray;
      allocArrays(i);
      mSize -= 1;
      if (paramInt > 0)
      {
        System.arraycopy(localObject2, 0, mHashes, 0, paramInt);
        System.arraycopy(arrayOfObject, 0, mArray, 0, paramInt);
      }
      i = mSize;
      if (paramInt < i)
      {
        j = paramInt + 1;
        System.arraycopy(localObject2, j, mHashes, paramInt, i - paramInt);
        System.arraycopy(arrayOfObject, j, mArray, paramInt, mSize - paramInt);
        return localObject1;
      }
    }
    else
    {
      mSize -= 1;
      i = mSize;
      if (paramInt < i)
      {
        localObject2 = mHashes;
        j = paramInt + 1;
        System.arraycopy(localObject2, j, localObject2, paramInt, i - paramInt);
        localObject2 = mArray;
        System.arraycopy(localObject2, j, localObject2, paramInt, mSize - paramInt);
      }
      mArray[mSize] = null;
    }
    return localObject1;
  }
  
  public boolean retainAll(Collection paramCollection)
  {
    int i = mSize - 1;
    boolean bool = false;
    while (i >= 0)
    {
      if (!paramCollection.contains(mArray[i]))
      {
        removeAt(i);
        bool = true;
      }
      i -= 1;
    }
    return bool;
  }
  
  public int size()
  {
    return mSize;
  }
  
  public Object[] toArray()
  {
    int i = mSize;
    Object[] arrayOfObject = new Object[i];
    System.arraycopy(mArray, 0, arrayOfObject, 0, i);
    return arrayOfObject;
  }
  
  public Object[] toArray(Object[] paramArrayOfObject)
  {
    Object[] arrayOfObject = paramArrayOfObject;
    if (paramArrayOfObject.length < mSize) {
      arrayOfObject = (Object[])Array.newInstance(paramArrayOfObject.getClass().getComponentType(), mSize);
    }
    System.arraycopy(mArray, 0, arrayOfObject, 0, mSize);
    int i = arrayOfObject.length;
    int j = mSize;
    if (i > j) {
      arrayOfObject[j] = null;
    }
    return arrayOfObject;
  }
  
  public String toString()
  {
    if (isEmpty()) {
      return "{}";
    }
    StringBuilder localStringBuilder = new StringBuilder(mSize * 14);
    localStringBuilder.append('{');
    int i = 0;
    while (i < mSize)
    {
      if (i > 0) {
        localStringBuilder.append(", ");
      }
      Object localObject = valueAt(i);
      if (localObject != this) {
        localStringBuilder.append(localObject);
      } else {
        localStringBuilder.append("(this Set)");
      }
      i += 1;
    }
    localStringBuilder.append('}');
    return localStringBuilder.toString();
  }
  
  public Object valueAt(int paramInt)
  {
    return mArray[paramInt];
  }
}
