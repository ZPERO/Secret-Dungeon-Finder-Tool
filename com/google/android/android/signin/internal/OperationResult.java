package com.google.android.android.signin.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.android.common.internal.ResolveAccountRequest;
import com.google.android.android.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.android.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.signin.internal.zah;

public final class OperationResult
  extends AbstractSafeParcelable
{
  public static final Parcelable.Creator<zah> CREATOR = new DownloadProgressInfo.1();
  private final int zale;
  private final ResolveAccountRequest zasa;
  
  OperationResult(int paramInt, ResolveAccountRequest paramResolveAccountRequest)
  {
    zale = paramInt;
    zasa = paramResolveAccountRequest;
  }
  
  public OperationResult(ResolveAccountRequest paramResolveAccountRequest)
  {
    this(1, paramResolveAccountRequest);
  }
  
  public final void writeToParcel(Parcel paramParcel, int paramInt)
  {
    int i = SafeParcelWriter.beginObjectHeader(paramParcel);
    SafeParcelWriter.writeInt(paramParcel, 1, zale);
    SafeParcelWriter.writeParcelable(paramParcel, 2, zasa, paramInt, false);
    SafeParcelWriter.finishObjectHeader(paramParcel, i);
  }
}
