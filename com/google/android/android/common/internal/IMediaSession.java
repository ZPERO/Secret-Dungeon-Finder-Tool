package com.google.android.android.common.internal;

import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.android.dynamic.IObjectWrapper;

public abstract interface IMediaSession
  extends IInterface
{
  public abstract IObjectWrapper next()
    throws RemoteException;
  
  public abstract int stop()
    throws RemoteException;
}
