package com.google.android.android.common.internal;

import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.android.common.Script;
import com.google.android.android.dynamic.IObjectWrapper;

public abstract interface IStatusBarCustomTileHolder
  extends IInterface
{
  public abstract boolean get(Script paramScript, IObjectWrapper paramIObjectWrapper)
    throws RemoteException;
}
