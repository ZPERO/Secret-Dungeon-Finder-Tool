package com.google.android.android.common.data;

import android.os.Bundle;
import com.google.android.gms.common.data.DataBuffer;
import java.util.Iterator;

public abstract class AbstractDataBuffer<T>
  implements DataBuffer<T>
{
  protected final DataHolder mDataHolder;
  
  protected AbstractDataBuffer(DataHolder paramDataHolder)
  {
    mDataHolder = paramDataHolder;
  }
  
  public final void close()
  {
    release();
  }
  
  public abstract Object get(int paramInt);
  
  public int getCount()
  {
    DataHolder localDataHolder = mDataHolder;
    if (localDataHolder == null) {
      return 0;
    }
    return localDataHolder.getCount();
  }
  
  public Bundle getMetadata()
  {
    return mDataHolder.getMetadata();
  }
  
  public boolean isClosed()
  {
    DataHolder localDataHolder = mDataHolder;
    return (localDataHolder == null) || (localDataHolder.isClosed());
  }
  
  public Iterator iterator()
  {
    return new DataBufferIterator(this);
  }
  
  public void release()
  {
    DataHolder localDataHolder = mDataHolder;
    if (localDataHolder != null) {
      localDataHolder.close();
    }
  }
  
  public Iterator singleRefIterator()
  {
    return new SingleRefDataBufferIterator(this);
  }
}