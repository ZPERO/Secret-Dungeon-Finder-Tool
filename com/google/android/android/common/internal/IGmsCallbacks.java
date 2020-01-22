package com.google.android.android.common.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.android.internal.common.Context;
import com.google.android.android.internal.common.IExtensionHost.Stub;

public abstract interface IGmsCallbacks
  extends IInterface
{
  public abstract void init(int paramInt, Bundle paramBundle)
    throws RemoteException;
  
  public abstract void onPostInitComplete(int paramInt, IBinder paramIBinder, Bundle paramBundle)
    throws RemoteException;
  
  public abstract void write(int paramInt, IBinder paramIBinder, Entry paramEntry)
    throws RemoteException;
  
  public abstract class zza
    extends IExtensionHost.Stub
    implements IGmsCallbacks
  {
    public zza()
    {
      super();
    }
    
    protected final boolean execute(int paramInt1, Parcel paramParcel1, Parcel paramParcel2, int paramInt2)
      throws RemoteException
    {
      if (paramInt1 != 1)
      {
        if (paramInt1 != 2)
        {
          if (paramInt1 != 3) {
            return false;
          }
          write(paramParcel1.readInt(), paramParcel1.readStrongBinder(), (Entry)Context.get(paramParcel1, Entry.CREATOR));
        }
        else
        {
          init(paramParcel1.readInt(), (Bundle)Context.get(paramParcel1, Bundle.CREATOR));
        }
      }
      else {
        onPostInitComplete(paramParcel1.readInt(), paramParcel1.readStrongBinder(), (Bundle)Context.get(paramParcel1, Bundle.CREATOR));
      }
      paramParcel2.writeNoException();
      return true;
    }
  }
}
