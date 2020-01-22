package com.google.android.android.auth.params.signin.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.android.auth.params.signin.GoogleSignInOptionsExtension;
import com.google.android.android.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.android.common.internal.safeparcel.SafeParcelWriter;

public class GoogleSignInOptionsExtensionParcelable
  extends AbstractSafeParcelable
{
  public static final Parcelable.Creator<com.google.android.gms.auth.api.signin.internal.GoogleSignInOptionsExtensionParcelable> CREATOR = new VerticalProgressBar.SavedState.1();
  private Bundle mBundle;
  private int mType;
  private final int versionCode;
  
  GoogleSignInOptionsExtensionParcelable(int paramInt1, int paramInt2, Bundle paramBundle)
  {
    versionCode = paramInt1;
    mType = paramInt2;
    mBundle = paramBundle;
  }
  
  public GoogleSignInOptionsExtensionParcelable(GoogleSignInOptionsExtension paramGoogleSignInOptionsExtension)
  {
    this(1, paramGoogleSignInOptionsExtension.getExtensionType(), paramGoogleSignInOptionsExtension.toBundle());
  }
  
  public int getType()
  {
    return mType;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramInt = SafeParcelWriter.beginObjectHeader(paramParcel);
    SafeParcelWriter.writeInt(paramParcel, 1, versionCode);
    SafeParcelWriter.writeInt(paramParcel, 2, getType());
    SafeParcelWriter.writeBundle(paramParcel, 3, mBundle, false);
    SafeParcelWriter.finishObjectHeader(paramParcel, paramInt);
  }
}
