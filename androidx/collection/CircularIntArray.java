package androidx.collection;

public final class CircularIntArray
{
  private int mCapacityBitmask;
  private int[] mElements;
  private int mHead;
  private int mTail;
  
  public CircularIntArray()
  {
    this(8);
  }
  
  public CircularIntArray(int paramInt)
  {
    if (paramInt >= 1)
    {
      if (paramInt <= 1073741824)
      {
        int i = paramInt;
        if (Integer.bitCount(paramInt) != 1) {
          i = Integer.highestOneBit(paramInt - 1) << 1;
        }
        mCapacityBitmask = (i - 1);
        mElements = new int[i];
        return;
      }
      throw new IllegalArgumentException("capacity must be <= 2^30");
    }
    throw new IllegalArgumentException("capacity must be >= 1");
  }
  
  private void doubleCapacity()
  {
    int[] arrayOfInt1 = mElements;
    int i = arrayOfInt1.length;
    int j = mHead;
    int k = i - j;
    int m = i << 1;
    if (m >= 0)
    {
      int[] arrayOfInt2 = new int[m];
      System.arraycopy(arrayOfInt1, j, arrayOfInt2, 0, k);
      System.arraycopy(mElements, 0, arrayOfInt2, k, mHead);
      mElements = arrayOfInt2;
      mHead = 0;
      mTail = i;
      mCapacityBitmask = (m - 1);
      return;
    }
    throw new RuntimeException("Max array capacity exceeded");
  }
  
  public void addFirst(int paramInt)
  {
    mHead = (mHead - 1 & mCapacityBitmask);
    int[] arrayOfInt = mElements;
    int i = mHead;
    arrayOfInt[i] = paramInt;
    if (i == mTail) {
      doubleCapacity();
    }
  }
  
  public void addLast(int paramInt)
  {
    int[] arrayOfInt = mElements;
    int i = mTail;
    arrayOfInt[i] = paramInt;
    mTail = (mCapacityBitmask & i + 1);
    if (mTail == mHead) {
      doubleCapacity();
    }
  }
  
  public void clear()
  {
    mTail = mHead;
  }
  
  public int get(int paramInt)
  {
    if ((paramInt >= 0) && (paramInt < size()))
    {
      int[] arrayOfInt = mElements;
      int i = mHead;
      return arrayOfInt[(mCapacityBitmask & i + paramInt)];
    }
    throw new ArrayIndexOutOfBoundsException();
  }
  
  public int getFirst()
  {
    int i = mHead;
    if (i != mTail) {
      return mElements[i];
    }
    throw new ArrayIndexOutOfBoundsException();
  }
  
  public int getLast()
  {
    int i = mHead;
    int j = mTail;
    if (i != j) {
      return mElements[(j - 1 & mCapacityBitmask)];
    }
    throw new ArrayIndexOutOfBoundsException();
  }
  
  public boolean isEmpty()
  {
    return mHead == mTail;
  }
  
  public int popFirst()
  {
    int i = mHead;
    if (i != mTail)
    {
      int j = mElements[i];
      mHead = (i + 1 & mCapacityBitmask);
      return j;
    }
    throw new ArrayIndexOutOfBoundsException();
  }
  
  public int popLast()
  {
    int i = mHead;
    int j = mTail;
    if (i != j)
    {
      i = mCapacityBitmask & j - 1;
      j = mElements[i];
      mTail = i;
      return j;
    }
    throw new ArrayIndexOutOfBoundsException();
  }
  
  public void removeFromEnd(int paramInt)
  {
    if (paramInt <= 0) {
      return;
    }
    if (paramInt <= size())
    {
      int i = mTail;
      mTail = (mCapacityBitmask & i - paramInt);
      return;
    }
    throw new ArrayIndexOutOfBoundsException();
  }
  
  public void removeFromStart(int paramInt)
  {
    if (paramInt <= 0) {
      return;
    }
    if (paramInt <= size())
    {
      int i = mHead;
      mHead = (mCapacityBitmask & i + paramInt);
      return;
    }
    throw new ArrayIndexOutOfBoundsException();
  }
  
  public int size()
  {
    return mTail - mHead & mCapacityBitmask;
  }
}
