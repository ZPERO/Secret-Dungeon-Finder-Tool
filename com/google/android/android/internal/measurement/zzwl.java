package com.google.android.android.internal.measurement;

import java.io.IOException;

public final class zzwl<K, V>
{
  static void addElement(zzut paramZzut, zzwm paramZzwm, Object paramObject1, Object paramObject2)
    throws IOException
  {
    zzvd.writeField(paramZzut, zzcar, 1, paramObject1);
    zzvd.writeField(paramZzut, zzcat, 2, paramObject2);
  }
  
  static int compare(zzwm paramZzwm, Object paramObject1, Object paramObject2)
  {
    return zzvd.getKey(zzcar, 1, paramObject1) + zzvd.getKey(zzcat, 2, paramObject2);
  }
}
