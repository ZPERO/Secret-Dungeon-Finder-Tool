package com.google.android.android.common.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.android.common.ConnectionResult;
import com.google.android.android.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.android.common.internal.safeparcel.SafeParcelWriter;

public class ResolveAccountResponse
  extends AbstractSafeParcelable
{
  public static final Parcelable.Creator<com.google.android.gms.common.internal.ResolveAccountResponse> CREATOR = new DownloadProgressInfo.1();
  private ConnectionResult zadh;
  private boolean zagf;
  private final int zale;
  private IBinder zanw;
  private boolean zapb;
  
  public ResolveAccountResponse(int paramInt)
  {
    this(new ConnectionResult(paramInt, null));
  }
  
  ResolveAccountResponse(int paramInt, IBinder paramIBinder, ConnectionResult paramConnectionResult, boolean paramBoolean1, boolean paramBoolean2)
  {
    zale = paramInt;
    zanw = paramIBinder;
    zadh = paramConnectionResult;
    zagf = paramBoolean1;
    zapb = paramBoolean2;
  }
  
  public ResolveAccountResponse(ConnectionResult paramConnectionResult)
  {
    this(1, null, paramConnectionResult, false, false);
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if (!(paramObject instanceof ResolveAccountResponse)) {
      return false;
    }
    paramObject = (ResolveAccountResponse)paramObject;
    return (zadh.equals(zadh)) && (getAccountAccessor().equals(paramObject.getAccountAccessor()));
  }
  
  public IAccountAccessor getAccountAccessor()
  {
    return IAccountAccessor.Stub.asInterface(zanw);
  }
  
  public ConnectionResult getConnectionResult()
  {
    return zadh;
  }
  
  public boolean getSaveDefaultAccount()
  {
    return zagf;
  }
  
  public boolean isFromCrossClientAuth()
  {
    return zapb;
  }
  
  public ResolveAccountResponse setAccountAccessor(IAccountAccessor paramIAccountAccessor)
  {
    if (paramIAccountAccessor == null) {
      paramIAccountAccessor = null;
    } else {
      paramIAccountAccessor = paramIAccountAccessor.asBinder();
    }
    zanw = paramIAccountAccessor;
    return this;
  }
  
  public ResolveAccountResponse setIsFromCrossClientAuth(boolean paramBoolean)
  {
    zapb = paramBoolean;
    return this;
  }
  
  public ResolveAccountResponse setSaveDefaultAccount(boolean paramBoolean)
  {
    zagf = paramBoolean;
    return this;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    int i = SafeParcelWriter.beginObjectHeader(paramParcel);
    SafeParcelWriter.writeInt(paramParcel, 1, zale);
    SafeParcelWriter.writeIBinder(paramParcel, 2, zanw, false);
    SafeParcelWriter.writeParcelable(paramParcel, 3, getConnectionResult(), paramInt, false);
    SafeParcelWriter.writeBoolean(paramParcel, 4, getSaveDefaultAccount());
    SafeParcelWriter.writeBoolean(paramParcel, 5, isFromCrossClientAuth());
    SafeParcelWriter.finishObjectHeader(paramParcel, i);
  }
}
