package com.google.android.android.common.internal;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.os.RemoteException;

public abstract interface IGmsServiceBroker
  extends IInterface
{
  public abstract void getService(IGmsCallbacks paramIGmsCallbacks, GetServiceRequest paramGetServiceRequest)
    throws RemoteException;
  
  public abstract class Stub
    extends Binder
    implements IGmsServiceBroker
  {
    public Stub()
    {
      attachInterface(this, "com.google.android.gms.common.internal.IGmsServiceBroker");
    }
    
    public IBinder asBinder()
    {
      return this;
    }
    
    public boolean onTransact(int paramInt1, Parcel paramParcel1, Parcel paramParcel2, int paramInt2)
      throws RemoteException
    {
      if (paramInt1 > 16777215) {
        return super.onTransact(paramInt1, paramParcel1, paramParcel2, paramInt2);
      }
      paramParcel1.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
      Object localObject = paramParcel1.readStrongBinder();
      GetServiceRequest localGetServiceRequest = null;
      if (localObject == null)
      {
        localObject = null;
      }
      else
      {
        IInterface localIInterface = ((IBinder)localObject).queryLocalInterface("com.google.android.gms.common.internal.IGmsCallbacks");
        if ((localIInterface instanceof IGmsCallbacks)) {
          localObject = (IGmsCallbacks)localIInterface;
        } else {
          localObject = new e((IBinder)localObject);
        }
      }
      if (paramInt1 == 46)
      {
        if (paramParcel1.readInt() != 0) {
          localGetServiceRequest = (GetServiceRequest)GetServiceRequest.CREATOR.createFromParcel(paramParcel1);
        }
        getService((IGmsCallbacks)localObject, localGetServiceRequest);
        paramParcel2.writeNoException();
        return true;
      }
      if (paramInt1 == 47)
      {
        if (paramParcel1.readInt() != 0) {
          Message.CREATOR.createFromParcel(paramParcel1);
        }
        throw new UnsupportedOperationException();
      }
      paramParcel1.readInt();
      if (paramInt1 != 4) {
        paramParcel1.readString();
      }
      if (paramInt1 != 1)
      {
        if ((paramInt1 != 2) && (paramInt1 != 23) && (paramInt1 != 25) && (paramInt1 != 27)) {
          if (paramInt1 != 30) {
            if (paramInt1 != 34) {
              if ((paramInt1 == 41) || (paramInt1 == 43) || (paramInt1 == 37) || (paramInt1 == 38)) {
                break label499;
              }
            }
          }
        }
        switch (paramInt1)
        {
        default: 
          break;
        case 19: 
          paramParcel1.readStrongBinder();
          if (paramParcel1.readInt() == 0) {
            break;
          }
          Bundle.CREATOR.createFromParcel(paramParcel1);
          break;
        case 10: 
          paramParcel1.readString();
          paramParcel1.createStringArray();
          break;
        case 9: 
          paramParcel1.readString();
          paramParcel1.createStringArray();
          paramParcel1.readString();
          paramParcel1.readStrongBinder();
          paramParcel1.readString();
          if (paramParcel1.readInt() == 0) {
            break;
          }
          Bundle.CREATOR.createFromParcel(paramParcel1);
          break;
          paramParcel1.readString();
          break;
        case 20: 
          paramParcel1.createStringArray();
          paramParcel1.readString();
          if (paramParcel1.readInt() == 0) {
            break;
          }
          Bundle.CREATOR.createFromParcel(paramParcel1);
          break;
        case 5: 
        case 6: 
        case 7: 
        case 8: 
        case 11: 
        case 12: 
        case 13: 
        case 14: 
        case 15: 
        case 16: 
        case 17: 
        case 18: 
          label499:
          if (paramParcel1.readInt() == 0) {
            break;
          }
          Bundle.CREATOR.createFromParcel(paramParcel1);
          break;
        }
      }
      else
      {
        paramParcel1.readString();
        paramParcel1.createStringArray();
        paramParcel1.readString();
        if (paramParcel1.readInt() != 0) {
          Bundle.CREATOR.createFromParcel(paramParcel1);
        }
      }
      throw new UnsupportedOperationException();
    }
    
    final class zza
      implements IGmsServiceBroker
    {
      zza() {}
      
      public final IBinder asBinder()
      {
        return IGmsServiceBroker.Stub.this;
      }
      
      public final void getService(IGmsCallbacks paramIGmsCallbacks, GetServiceRequest paramGetServiceRequest)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.common.internal.IGmsServiceBroker");
          if (paramIGmsCallbacks != null) {
            paramIGmsCallbacks = paramIGmsCallbacks.asBinder();
          } else {
            paramIGmsCallbacks = null;
          }
          localParcel1.writeStrongBinder(paramIGmsCallbacks);
          if (paramGetServiceRequest != null)
          {
            localParcel1.writeInt(1);
            paramGetServiceRequest.writeToParcel(localParcel1, 0);
          }
          else
          {
            localParcel1.writeInt(0);
          }
          transact(46, localParcel1, localParcel2, 0);
          localParcel2.readException();
          localParcel2.recycle();
          localParcel1.recycle();
          return;
        }
        catch (Throwable paramIGmsCallbacks)
        {
          localParcel2.recycle();
          localParcel1.recycle();
          throw paramIGmsCallbacks;
        }
      }
    }
  }
}
