package com.google.android.android.common.aimsicd;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.android.common.internal.Preconditions;
import com.google.android.android.common.internal.ReflectedParcelable;
import com.google.android.android.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.android.common.internal.safeparcel.SafeParcelWriter;

public final class Scope
  extends AbstractSafeParcelable
  implements ReflectedParcelable
{
  public static final Parcelable.Creator<com.google.android.gms.common.api.Scope> CREATOR = new DiscreteSeekBar.CustomState.1();
  private final int max;
  private final String zzap;
  
  Scope(int paramInt, String paramString)
  {
    Preconditions.checkNotEmpty(paramString, "scopeUri must not be null or empty");
    max = paramInt;
    zzap = paramString;
  }
  
  public Scope(String paramString)
  {
    this(1, paramString);
  }
  
  public final boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if (!(paramObject instanceof Scope)) {
      return false;
    }
    return zzap.equals(zzap);
  }
  
  public final String getScopeUri()
  {
    return zzap;
  }
  
  public final int hashCode()
  {
    return zzap.hashCode();
  }
  
  public final String toString()
  {
    return zzap;
  }
  
  public final void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramInt = SafeParcelWriter.beginObjectHeader(paramParcel);
    SafeParcelWriter.writeInt(paramParcel, 1, max);
    SafeParcelWriter.writeString(paramParcel, 2, getScopeUri(), false);
    SafeParcelWriter.finishObjectHeader(paramParcel, paramInt);
  }
}
