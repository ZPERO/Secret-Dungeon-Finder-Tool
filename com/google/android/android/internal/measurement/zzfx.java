package com.google.android.android.internal.measurement;

import java.io.IOException;

public final class zzfx
  extends com.google.android.gms.internal.measurement.zzza<com.google.android.gms.internal.measurement.zzfx>
{
  public Integer zzavo = null;
  public Boolean zzavp = null;
  public String zzavq = null;
  public String zzavr = null;
  public String zzavs = null;
  
  public zzfx()
  {
    zzcfc = null;
    zzcfm = -1;
  }
  
  private final zzfx parse(zzyx paramZzyx)
    throws IOException
  {
    for (;;)
    {
      int i = paramZzyx.zzug();
      if (i == 0) {
        break;
      }
      int j;
      if (i != 8)
      {
        if (i != 16)
        {
          if (i != 26)
          {
            if (i != 34)
            {
              if (i != 42)
              {
                if (!super.read(paramZzyx, i)) {
                  return this;
                }
              }
              else {
                zzavs = paramZzyx.readString();
              }
            }
            else {
              zzavr = paramZzyx.readString();
            }
          }
          else {
            zzavq = paramZzyx.readString();
          }
        }
        else {
          zzavp = Boolean.valueOf(paramZzyx.zzum());
        }
      }
      else {
        j = paramZzyx.getPosition();
      }
      try
      {
        int k = paramZzyx.zzuy();
        if ((k >= 0) && (k <= 4))
        {
          zzavo = Integer.valueOf(k);
          continue;
        }
        StringBuilder localStringBuilder = new StringBuilder(46);
        localStringBuilder.append(k);
        localStringBuilder.append(" is not a valid enum ComparisonType");
        throw new IllegalArgumentException(localStringBuilder.toString());
      }
      catch (IllegalArgumentException localIllegalArgumentException)
      {
        for (;;) {}
      }
      paramZzyx.zzby(j);
      read(paramZzyx, i);
    }
    return this;
  }
  
  public final boolean equals(Object paramObject)
  {
    if (paramObject == this) {
      return true;
    }
    if (!(paramObject instanceof zzfx)) {
      return false;
    }
    paramObject = (zzfx)paramObject;
    Object localObject = zzavo;
    if (localObject == null)
    {
      if (zzavo != null) {
        return false;
      }
    }
    else if (!((Integer)localObject).equals(zzavo)) {
      return false;
    }
    localObject = zzavp;
    if (localObject == null)
    {
      if (zzavp != null) {
        return false;
      }
    }
    else if (!((Boolean)localObject).equals(zzavp)) {
      return false;
    }
    localObject = zzavq;
    if (localObject == null)
    {
      if (zzavq != null) {
        return false;
      }
    }
    else if (!((String)localObject).equals(zzavq)) {
      return false;
    }
    localObject = zzavr;
    if (localObject == null)
    {
      if (zzavr != null) {
        return false;
      }
    }
    else if (!((String)localObject).equals(zzavr)) {
      return false;
    }
    localObject = zzavs;
    if (localObject == null)
    {
      if (zzavs != null) {
        return false;
      }
    }
    else if (!((String)localObject).equals(zzavs)) {
      return false;
    }
    if ((zzcfc != null) && (!zzcfc.isEmpty())) {
      return zzcfc.equals(zzcfc);
    }
    if (zzcfc != null) {
      return zzcfc.isEmpty();
    }
    return true;
  }
  
  public final int hashCode()
  {
    int i3 = getClass().getName().hashCode();
    Object localObject = zzavo;
    int i2 = 0;
    int i;
    if (localObject == null) {
      i = 0;
    } else {
      i = ((Integer)localObject).intValue();
    }
    localObject = zzavp;
    int j;
    if (localObject == null) {
      j = 0;
    } else {
      j = ((Boolean)localObject).hashCode();
    }
    localObject = zzavq;
    int k;
    if (localObject == null) {
      k = 0;
    } else {
      k = ((String)localObject).hashCode();
    }
    localObject = zzavr;
    int m;
    if (localObject == null) {
      m = 0;
    } else {
      m = ((String)localObject).hashCode();
    }
    localObject = zzavs;
    int n;
    if (localObject == null) {
      n = 0;
    } else {
      n = ((String)localObject).hashCode();
    }
    int i1 = i2;
    if (zzcfc != null) {
      if (zzcfc.isEmpty()) {
        i1 = i2;
      } else {
        i1 = zzcfc.hashCode();
      }
    }
    return ((((((i3 + 527) * 31 + i) * 31 + j) * 31 + k) * 31 + m) * 31 + n) * 31 + i1;
  }
  
  protected final int multiply()
  {
    int j = super.multiply();
    int i = j;
    Object localObject = zzavo;
    if (localObject != null) {
      i = j + zzyy.sendCommand(1, ((Integer)localObject).intValue());
    }
    localObject = zzavp;
    j = i;
    if (localObject != null)
    {
      ((Boolean)localObject).booleanValue();
      j = i + (zzyy.zzbb(2) + 1);
    }
    localObject = zzavq;
    i = j;
    if (localObject != null) {
      i = j + zzyy.setProperty(3, (String)localObject);
    }
    localObject = zzavr;
    j = i;
    if (localObject != null) {
      j = i + zzyy.setProperty(4, (String)localObject);
    }
    localObject = zzavs;
    i = j;
    if (localObject != null) {
      i = j + zzyy.setProperty(5, (String)localObject);
    }
    return i;
  }
  
  public final void multiply(zzyy paramZzyy)
    throws IOException
  {
    Object localObject = zzavo;
    if (localObject != null) {
      paramZzyy.addItem(1, ((Integer)localObject).intValue());
    }
    localObject = zzavp;
    if (localObject != null) {
      paramZzyy.add(2, ((Boolean)localObject).booleanValue());
    }
    localObject = zzavq;
    if (localObject != null) {
      paramZzyy.add(3, (String)localObject);
    }
    localObject = zzavr;
    if (localObject != null) {
      paramZzyy.add(4, (String)localObject);
    }
    localObject = zzavs;
    if (localObject != null) {
      paramZzyy.add(5, (String)localObject);
    }
    super.multiply(paramZzyy);
  }
}
