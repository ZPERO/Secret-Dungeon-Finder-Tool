package com.google.android.android.common.aimsicd;

import com.google.android.gms.common.api.zac;
import java.util.Map;
import java.util.WeakHashMap;

public abstract class Realm
{
  private static final Object sLock = new Object();
  private static final Map<Object, zac> zacj = new WeakHashMap();
  
  public Realm() {}
  
  public abstract void remove(int paramInt);
}
