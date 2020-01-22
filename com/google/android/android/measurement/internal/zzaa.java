package com.google.android.android.measurement.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.android.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import java.util.Iterator;

public final class zzaa
  extends AbstractSafeParcelable
  implements Iterable<String>
{
  public static final Parcelable.Creator<com.google.android.gms.measurement.internal.zzaa> CREATOR = new zzac();
  private final Bundle zzaim;
  
  zzaa(Bundle paramBundle)
  {
    zzaim = paramBundle;
  }
  
  final Long getLong(String paramString)
  {
    return Long.valueOf(zzaim.getLong(paramString));
  }
  
  final String getString(String paramString)
  {
    return zzaim.getString(paramString);
  }
  
  final Object getValue(String paramString)
  {
    return zzaim.get(paramString);
  }
  
  public final Iterator iterator()
  {
    return new zzab(this);
  }
  
  public final int size()
  {
    return zzaim.size();
  }
  
  public final String toString()
  {
    return zzaim.toString();
  }
  
  public final void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramInt = SafeParcelWriter.beginObjectHeader(paramParcel);
    SafeParcelWriter.writeBundle(paramParcel, 2, zziv(), false);
    SafeParcelWriter.finishObjectHeader(paramParcel, paramInt);
  }
  
  final Double zzbq(String paramString)
  {
    return Double.valueOf(zzaim.getDouble(paramString));
  }
  
  public final Bundle zziv()
  {
    return new Bundle(zzaim);
  }
}
