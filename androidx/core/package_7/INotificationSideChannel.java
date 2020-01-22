package androidx.core.package_7;

import android.app.Notification;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.os.RemoteException;

public abstract interface INotificationSideChannel
  extends IInterface
{
  public abstract void cancel(String paramString1, int paramInt, String paramString2)
    throws RemoteException;
  
  public abstract void cancelAll(String paramString)
    throws RemoteException;
  
  public abstract void notify(String paramString1, int paramInt, String paramString2, Notification paramNotification)
    throws RemoteException;
  
  public abstract class Stub
    extends Binder
    implements INotificationSideChannel
  {
    private static final String DESCRIPTOR = "androidx.core.app.INotificationSideChannel";
    static final int TRANSACTION_cancel = 2;
    static final int TRANSACTION_cancelAll = 3;
    static final int TRANSACTION_notify = 1;
    
    public Stub()
    {
      attachInterface(this, "androidx.core.app.INotificationSideChannel");
    }
    
    public static INotificationSideChannel asInterface(IBinder paramIBinder)
    {
      if (paramIBinder == null) {
        return null;
      }
      IInterface localIInterface = paramIBinder.queryLocalInterface("androidx.core.app.INotificationSideChannel");
      if ((localIInterface != null) && ((localIInterface instanceof INotificationSideChannel))) {
        return (INotificationSideChannel)localIInterface;
      }
      return new Proxy();
    }
    
    public IBinder asBinder()
    {
      return this;
    }
    
    public boolean onTransact(int paramInt1, Parcel paramParcel1, Parcel paramParcel2, int paramInt2)
      throws RemoteException
    {
      if (paramInt1 != 1)
      {
        if (paramInt1 != 2)
        {
          if (paramInt1 != 3)
          {
            if (paramInt1 != 1598968902) {
              return super.onTransact(paramInt1, paramParcel1, paramParcel2, paramInt2);
            }
            paramParcel2.writeString("androidx.core.app.INotificationSideChannel");
            return true;
          }
          paramParcel1.enforceInterface("androidx.core.app.INotificationSideChannel");
          cancelAll(paramParcel1.readString());
          return true;
        }
        paramParcel1.enforceInterface("androidx.core.app.INotificationSideChannel");
        cancel(paramParcel1.readString(), paramParcel1.readInt(), paramParcel1.readString());
        return true;
      }
      paramParcel1.enforceInterface("androidx.core.app.INotificationSideChannel");
      paramParcel2 = paramParcel1.readString();
      paramInt1 = paramParcel1.readInt();
      String str = paramParcel1.readString();
      if (paramParcel1.readInt() != 0) {
        paramParcel1 = (Notification)Notification.CREATOR.createFromParcel(paramParcel1);
      } else {
        paramParcel1 = null;
      }
      notify(paramParcel2, paramInt1, str, paramParcel1);
      return true;
    }
    
    class Proxy
      implements INotificationSideChannel
    {
      Proxy() {}
      
      public IBinder asBinder()
      {
        return INotificationSideChannel.Stub.this;
      }
      
      public void cancel(String paramString1, int paramInt, String paramString2)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("androidx.core.app.INotificationSideChannel");
          localParcel.writeString(paramString1);
          localParcel.writeInt(paramInt);
          localParcel.writeString(paramString2);
          transact(2, localParcel, null, 1);
          localParcel.recycle();
          return;
        }
        catch (Throwable paramString1)
        {
          localParcel.recycle();
          throw paramString1;
        }
      }
      
      public void cancelAll(String paramString)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("androidx.core.app.INotificationSideChannel");
          localParcel.writeString(paramString);
          transact(3, localParcel, null, 1);
          localParcel.recycle();
          return;
        }
        catch (Throwable paramString)
        {
          localParcel.recycle();
          throw paramString;
        }
      }
      
      public String getInterfaceDescriptor()
      {
        return "androidx.core.app.INotificationSideChannel";
      }
      
      public void notify(String paramString1, int paramInt, String paramString2, Notification paramNotification)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("androidx.core.app.INotificationSideChannel");
          localParcel.writeString(paramString1);
          localParcel.writeInt(paramInt);
          localParcel.writeString(paramString2);
          if (paramNotification != null)
          {
            localParcel.writeInt(1);
            paramNotification.writeToParcel(localParcel, 0);
          }
          else
          {
            localParcel.writeInt(0);
          }
          transact(1, localParcel, null, 1);
          localParcel.recycle();
          return;
        }
        catch (Throwable paramString1)
        {
          localParcel.recycle();
          throw paramString1;
        }
      }
    }
  }
}