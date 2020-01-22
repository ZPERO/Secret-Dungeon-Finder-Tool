package com.google.android.android.common.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.android.common.aimsicd.Scope;
import com.google.android.android.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.android.common.internal.safeparcel.SafeParcelWriter;

public class SignInButtonConfig
  extends AbstractSafeParcelable
{
  public static final Parcelable.Creator<com.google.android.gms.common.internal.SignInButtonConfig> CREATOR = new PaymentIntent.Output.1();
  private final int zale;
  @Deprecated
  private final Scope[] zanx;
  private final int zapc;
  private final int zapd;
  
  SignInButtonConfig(int paramInt1, int paramInt2, int paramInt3, Scope[] paramArrayOfScope)
  {
    zale = paramInt1;
    zapc = paramInt2;
    zapd = paramInt3;
    zanx = paramArrayOfScope;
  }
  
  public SignInButtonConfig(int paramInt1, int paramInt2, Scope[] paramArrayOfScope)
  {
    this(1, paramInt1, paramInt2, null);
  }
  
  public int getButtonSize()
  {
    return zapc;
  }
  
  public int getColorScheme()
  {
    return zapd;
  }
  
  public Scope[] getScopes()
  {
    return zanx;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    int i = SafeParcelWriter.beginObjectHeader(paramParcel);
    SafeParcelWriter.writeInt(paramParcel, 1, zale);
    SafeParcelWriter.writeInt(paramParcel, 2, getButtonSize());
    SafeParcelWriter.writeInt(paramParcel, 3, getColorScheme());
    SafeParcelWriter.writeTypedArray(paramParcel, 4, getScopes(), paramInt, false);
    SafeParcelWriter.finishObjectHeader(paramParcel, i);
  }
}
