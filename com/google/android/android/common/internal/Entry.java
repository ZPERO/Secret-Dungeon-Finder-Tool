package com.google.android.android.common.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.android.common.Feature;
import com.google.android.android.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.android.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.zzb;

public final class Entry
  extends AbstractSafeParcelable
{
  public static final Parcelable.Creator<zzb> CREATOR = new VerticalProgressBar.SavedState.1();
  Bundle zzcz;
  Feature[] zzda;
  
  public Entry() {}
  
  Entry(Bundle paramBundle, Feature[] paramArrayOfFeature)
  {
    zzcz = paramBundle;
    zzda = paramArrayOfFeature;
  }
  
  public final void writeToParcel(Parcel paramParcel, int paramInt)
  {
    int i = SafeParcelWriter.beginObjectHeader(paramParcel);
    SafeParcelWriter.writeBundle(paramParcel, 1, zzcz, false);
    SafeParcelWriter.writeTypedArray(paramParcel, 2, zzda, paramInt, false);
    SafeParcelWriter.finishObjectHeader(paramParcel, i);
  }
}
