package com.google.android.android.dynamite;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.android.dynamic.IObjectWrapper;
import com.google.android.android.dynamic.IObjectWrapper.Stub;
import com.google.android.android.internal.common.Context;
import com.google.android.android.internal.common.Log;

public final class HttpClientWrapper
  extends Log
  implements HttpClient
{
  HttpClientWrapper(IBinder paramIBinder)
  {
    super(paramIBinder, "com.google.android.gms.dynamite.IDynamiteLoader");
  }
  
  public final int execute(IObjectWrapper paramIObjectWrapper, String paramString, boolean paramBoolean)
    throws RemoteException
  {
    Parcel localParcel = get();
    Context.register(localParcel, paramIObjectWrapper);
    localParcel.writeString(paramString);
    Context.writeBoolean(localParcel, paramBoolean);
    paramIObjectWrapper = get(3, localParcel);
    int i = paramIObjectWrapper.readInt();
    paramIObjectWrapper.recycle();
    return i;
  }
  
  public final IObjectWrapper execute(IObjectWrapper paramIObjectWrapper, String paramString, int paramInt)
    throws RemoteException
  {
    Parcel localParcel = get();
    Context.register(localParcel, paramIObjectWrapper);
    localParcel.writeString(paramString);
    localParcel.writeInt(paramInt);
    paramIObjectWrapper = get(4, localParcel);
    paramString = IObjectWrapper.Stub.asInterface(paramIObjectWrapper.readStrongBinder());
    paramIObjectWrapper.recycle();
    return paramString;
  }
  
  public final int get(IObjectWrapper paramIObjectWrapper, String paramString, boolean paramBoolean)
    throws RemoteException
  {
    Parcel localParcel = get();
    Context.register(localParcel, paramIObjectWrapper);
    localParcel.writeString(paramString);
    Context.writeBoolean(localParcel, paramBoolean);
    paramIObjectWrapper = get(5, localParcel);
    int i = paramIObjectWrapper.readInt();
    paramIObjectWrapper.recycle();
    return i;
  }
  
  public final IObjectWrapper get(IObjectWrapper paramIObjectWrapper, String paramString, int paramInt)
    throws RemoteException
  {
    Parcel localParcel = get();
    Context.register(localParcel, paramIObjectWrapper);
    localParcel.writeString(paramString);
    localParcel.writeInt(paramInt);
    paramIObjectWrapper = get(2, localParcel);
    paramString = IObjectWrapper.Stub.asInterface(paramIObjectWrapper.readStrongBinder());
    paramIObjectWrapper.recycle();
    return paramString;
  }
  
  public final int zzaj()
    throws RemoteException
  {
    Parcel localParcel = get(6, get());
    int i = localParcel.readInt();
    localParcel.recycle();
    return i;
  }
}
