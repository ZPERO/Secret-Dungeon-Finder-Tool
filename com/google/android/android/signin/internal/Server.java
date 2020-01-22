package com.google.android.android.signin.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.android.common.ConnectionResult;
import com.google.android.android.common.internal.ResolveAccountResponse;
import com.google.android.android.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.android.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.signin.internal.zaj;

public final class Server
  extends AbstractSafeParcelable
{
  public static final Parcelable.Creator<zaj> CREATOR = new DiscreteSeekBar.CustomState.1();
  private final ConnectionResult zadh;
  private final int zale;
  private final ResolveAccountResponse zasb;
  
  public Server(int paramInt)
  {
    this(new ConnectionResult(8, null), null);
  }
  
  Server(int paramInt, ConnectionResult paramConnectionResult, ResolveAccountResponse paramResolveAccountResponse)
  {
    zale = paramInt;
    zadh = paramConnectionResult;
    zasb = paramResolveAccountResponse;
  }
  
  private Server(ConnectionResult paramConnectionResult, ResolveAccountResponse paramResolveAccountResponse)
  {
    this(1, paramConnectionResult, null);
  }
  
  public final ConnectionResult getConnectionResult()
  {
    return zadh;
  }
  
  public final void writeToParcel(Parcel paramParcel, int paramInt)
  {
    int i = SafeParcelWriter.beginObjectHeader(paramParcel);
    SafeParcelWriter.writeInt(paramParcel, 1, zale);
    SafeParcelWriter.writeParcelable(paramParcel, 2, zadh, paramInt, false);
    SafeParcelWriter.writeParcelable(paramParcel, 3, zasb, paramInt, false);
    SafeParcelWriter.finishObjectHeader(paramParcel, i);
  }
  
  public final ResolveAccountResponse zacw()
  {
    return zasb;
  }
}
