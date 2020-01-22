package com.google.android.android.common.aimsicd.internal;

import android.os.RemoteException;

public abstract interface RemoteCall<T, U>
{
  public abstract void accept(Object paramObject1, Object paramObject2)
    throws RemoteException;
}
