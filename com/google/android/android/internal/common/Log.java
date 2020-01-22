package com.google.android.android.internal.common;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public class Log
  implements IInterface
{
  private final String id;
  private final IBinder mRemote;
  
  protected Log(IBinder paramIBinder, String paramString)
  {
    mRemote = paramIBinder;
    id = paramString;
  }
  
  public IBinder asBinder()
  {
    return mRemote;
  }
  
  protected final Parcel get()
  {
    Parcel localParcel = Parcel.obtain();
    localParcel.writeInterfaceToken(id);
    return localParcel;
  }
  
  protected final Parcel get(int paramInt, Parcel paramParcel)
    throws RemoteException
  {
    Parcel localParcel = Parcel.obtain();
    try
    {
      mRemote.transact(paramInt, paramParcel, localParcel, 0);
      localParcel.readException();
      paramParcel.recycle();
      return localParcel;
    }
    catch (Throwable localThrowable) {}catch (RuntimeException localRuntimeException)
    {
      localThrowable.recycle();
      throw localRuntimeException;
    }
    paramParcel.recycle();
    throw localThrowable;
  }
  
  protected final void register(int paramInt, Parcel paramParcel)
    throws RemoteException
  {
    Parcel localParcel = Parcel.obtain();
    try
    {
      mRemote.transact(paramInt, paramParcel, localParcel, 0);
      localParcel.readException();
      paramParcel.recycle();
      localParcel.recycle();
      return;
    }
    catch (Throwable localThrowable)
    {
      paramParcel.recycle();
      localParcel.recycle();
      throw localThrowable;
    }
  }
  
  protected final void setText(int paramInt, Parcel paramParcel)
    throws RemoteException
  {
    try
    {
      mRemote.transact(2, paramParcel, null, 1);
      paramParcel.recycle();
      return;
    }
    catch (Throwable localThrowable)
    {
      paramParcel.recycle();
      throw localThrowable;
    }
  }
}
