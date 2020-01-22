package com.google.firebase.database.collection;

import java.util.Iterator;

final class MultidimensionalCounter
  implements Iterable<zzh>
{
  private final int length;
  private long value;
  
  public MultidimensionalCounter(int paramInt)
  {
    paramInt += 1;
    length = ((int)Math.floor(Math.log(paramInt) / Math.log(2.0D)));
    value = (Math.pow(2.0D, length) - 1L & paramInt);
  }
  
  public final Iterator iterator()
  {
    return new PackWriterBitmapPreparer.WalkResult.1(this);
  }
}
