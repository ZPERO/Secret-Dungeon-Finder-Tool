package com.google.android.android.internal.measurement;

import java.io.IOException;

abstract class zzyb<T, B>
{
  zzyb() {}
  
  abstract boolean addValue(zzxi paramZzxi);
  
  abstract void close(Object paramObject, int paramInt, zzud paramZzud);
  
  abstract void element(Object paramObject, zzyw paramZzyw)
    throws IOException;
  
  final boolean next(Object paramObject, zzxi paramZzxi)
    throws IOException
  {
    int j = paramZzxi.getTag();
    int i = j >>> 3;
    j &= 0x7;
    if (j != 0)
    {
      if (j != 1)
      {
        if (j != 2)
        {
          if (j != 3)
          {
            if (j != 4)
            {
              if (j == 5)
              {
                readTag(paramObject, i, paramZzxi.zzul());
                return true;
              }
              throw zzvt.zzwo();
            }
            return false;
          }
          Object localObject = zzye();
          while ((paramZzxi.zzve() != Integer.MAX_VALUE) && (next(localObject, paramZzxi))) {}
          if ((0x4 | i << 3) == paramZzxi.getTag())
          {
            readTag(paramObject, i, zzab(localObject));
            return true;
          }
          throw zzvt.zzwn();
        }
        close(paramObject, i, paramZzxi.zzuo());
        return true;
      }
      readTag(paramObject, i, paramZzxi.zzuk());
      return true;
    }
    updateImage(paramObject, i, paramZzxi.zzui());
    return true;
  }
  
  abstract void operate(Object paramObject, zzyw paramZzyw)
    throws IOException;
  
  abstract void operate(Object paramObject1, Object paramObject2);
  
  abstract void readTag(Object paramObject, int paramInt1, int paramInt2);
  
  abstract void readTag(Object paramObject, int paramInt, long paramLong);
  
  abstract void readTag(Object paramObject1, int paramInt, Object paramObject2);
  
  abstract void scheduleRefresh(Object paramObject1, Object paramObject2);
  
  abstract void setEntry(Object paramObject);
  
  abstract Object subtract(Object paramObject1, Object paramObject2);
  
  abstract void updateImage(Object paramObject, int paramInt, long paramLong);
  
  abstract Object zzab(Object paramObject);
  
  abstract int zzae(Object paramObject);
  
  abstract Object zzah(Object paramObject);
  
  abstract Object zzai(Object paramObject);
  
  abstract int zzaj(Object paramObject);
  
  abstract Object zzye();
}
