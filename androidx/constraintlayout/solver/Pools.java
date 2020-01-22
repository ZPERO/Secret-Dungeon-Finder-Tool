package androidx.constraintlayout.solver;

final class Pools
{
  private static final boolean DEBUG = false;
  
  private Pools() {}
  
  static abstract interface Pool<T>
  {
    public abstract Object acquire();
    
    public abstract boolean release(Object paramObject);
    
    public abstract void releaseAll(Object[] paramArrayOfObject, int paramInt);
  }
  
  static class SimplePool<T>
    implements Pools.Pool<T>
  {
    private final Object[] mPool;
    private int mPoolSize;
    
    SimplePool(int paramInt)
    {
      if (paramInt > 0)
      {
        mPool = new Object[paramInt];
        return;
      }
      throw new IllegalArgumentException("The max pool size must be > 0");
    }
    
    private boolean isInPool(Object paramObject)
    {
      int i = 0;
      while (i < mPoolSize)
      {
        if (mPool[i] == paramObject) {
          return true;
        }
        i += 1;
      }
      return false;
    }
    
    public Object acquire()
    {
      int i = mPoolSize;
      if (i > 0)
      {
        int j = i - 1;
        Object[] arrayOfObject = mPool;
        Object localObject = arrayOfObject[j];
        arrayOfObject[j] = null;
        mPoolSize = (i - 1);
        return localObject;
      }
      return null;
    }
    
    public boolean release(Object paramObject)
    {
      int i = mPoolSize;
      Object[] arrayOfObject = mPool;
      if (i < arrayOfObject.length)
      {
        arrayOfObject[i] = paramObject;
        mPoolSize = (i + 1);
        return true;
      }
      return false;
    }
    
    public void releaseAll(Object[] paramArrayOfObject, int paramInt)
    {
      int i = paramInt;
      if (paramInt > paramArrayOfObject.length) {
        i = paramArrayOfObject.length;
      }
      paramInt = 0;
      while (paramInt < i)
      {
        Object localObject = paramArrayOfObject[paramInt];
        int j = mPoolSize;
        Object[] arrayOfObject = mPool;
        if (j < arrayOfObject.length)
        {
          arrayOfObject[j] = localObject;
          mPoolSize = (j + 1);
        }
        paramInt += 1;
      }
    }
  }
}
