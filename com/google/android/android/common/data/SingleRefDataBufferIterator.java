package com.google.android.android.common.data;

import java.util.NoSuchElementException;

public class SingleRefDataBufferIterator<T>
  extends com.google.android.gms.common.data.DataBufferIterator<T>
{
  private T zamf;
  
  public SingleRefDataBufferIterator(DataBuffer paramDataBuffer)
  {
    super(paramDataBuffer);
  }
  
  public Object next()
  {
    if (hasNext())
    {
      zalk += 1;
      if (zalk == 0)
      {
        zamf = zalj.get(0);
        localObject = zamf;
        if (!(localObject instanceof DataBufferRef))
        {
          localObject = String.valueOf(localObject.getClass());
          StringBuilder localStringBuilder = new StringBuilder(String.valueOf(localObject).length() + 44);
          localStringBuilder.append("DataBuffer reference of type ");
          localStringBuilder.append((String)localObject);
          localStringBuilder.append(" is not movable");
          throw new IllegalStateException(localStringBuilder.toString());
        }
      }
      else
      {
        ((DataBufferRef)zamf).register(zalk);
      }
      return zamf;
    }
    int i = zalk;
    Object localObject = new StringBuilder(46);
    ((StringBuilder)localObject).append("Cannot advance the iterator beyond ");
    ((StringBuilder)localObject).append(i);
    throw new NoSuchElementException(((StringBuilder)localObject).toString());
  }
}
