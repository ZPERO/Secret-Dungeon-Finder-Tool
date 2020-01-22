package com.google.android.android.common;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.android.common.internal.IMediaSession;
import com.google.android.android.common.internal.Property;
import com.google.android.android.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.android.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.android.dynamic.ObjectWrapper;
import com.google.android.android.internal.common.IExtensionHost.Stub;
import com.google.android.gms.common.zzk;
import javax.annotation.Nullable;

public final class Script
  extends AbstractSafeParcelable
{
  public static final Parcelable.Creator<zzk> CREATOR = new DownloadProgressInfo.1();
  private final String mId;
  @Nullable
  private final Name mName;
  private final boolean zzaa;
  
  Script(String paramString, IBinder paramIBinder, boolean paramBoolean)
  {
    mId = paramString;
    mName = toString(paramIBinder);
    zzaa = paramBoolean;
  }
  
  Script(String paramString, Name paramName, boolean paramBoolean)
  {
    mId = paramString;
    mName = paramName;
    zzaa = paramBoolean;
  }
  
  private static Name toString(IBinder paramIBinder)
  {
    if (paramIBinder == null) {
      return null;
    }
    try
    {
      paramIBinder = Property.asInterface(paramIBinder).next();
      if (paramIBinder == null) {
        paramIBinder = null;
      } else {
        paramIBinder = (byte[])ObjectWrapper.unwrap(paramIBinder);
      }
      if (paramIBinder != null) {
        return new TypedByteArray(paramIBinder);
      }
      Log.e("GoogleCertificatesQuery", "Could not unwrap certificate");
      return null;
    }
    catch (RemoteException paramIBinder)
    {
      Log.e("GoogleCertificatesQuery", "Could not unwrap certificate", paramIBinder);
    }
    return null;
  }
  
  public final void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramInt = SafeParcelWriter.beginObjectHeader(paramParcel);
    SafeParcelWriter.writeString(paramParcel, 1, mId, false);
    Object localObject = mName;
    if (localObject == null)
    {
      Log.w("GoogleCertificatesQuery", "certificate binder is null");
      localObject = null;
    }
    else
    {
      localObject = ((IExtensionHost.Stub)localObject).asBinder();
    }
    SafeParcelWriter.writeIBinder(paramParcel, 2, (IBinder)localObject, false);
    SafeParcelWriter.writeBoolean(paramParcel, 3, zzaa);
    SafeParcelWriter.finishObjectHeader(paramParcel, paramInt);
  }
}
