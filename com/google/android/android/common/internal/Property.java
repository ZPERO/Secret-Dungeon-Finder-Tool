package com.google.android.android.common.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.android.internal.common.Context;
import com.google.android.android.internal.common.IExtensionHost.Stub;

public abstract class Property
  extends IExtensionHost.Stub
  implements IMediaSession
{
  public Property()
  {
    super("com.google.android.gms.common.internal.ICertData");
  }
  
  public static IMediaSession asInterface(IBinder paramIBinder)
  {
    if (paramIBinder == null) {
      return null;
    }
    IInterface localIInterface = paramIBinder.queryLocalInterface("com.google.android.gms.common.internal.ICertData");
    if ((localIInterface instanceof IMediaSession)) {
      return (IMediaSession)localIInterface;
    }
    return new IMediaSession.Stub.Proxy(paramIBinder);
  }
  
  protected final boolean execute(int paramInt1, Parcel paramParcel1, Parcel paramParcel2, int paramInt2)
    throws RemoteException
  {
    if (paramInt1 != 1)
    {
      if (paramInt1 != 2) {
        return false;
      }
      paramInt1 = stop();
      paramParcel2.writeNoException();
      paramParcel2.writeInt(paramInt1);
      return true;
    }
    paramParcel1 = next();
    paramParcel2.writeNoException();
    Context.register(paramParcel2, paramParcel1);
    return true;
  }
}
