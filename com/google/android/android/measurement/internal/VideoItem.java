package com.google.android.android.measurement.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.google.android.android.common.internal.Preconditions;
import com.google.android.android.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.android.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.measurement.internal.zzh;

public final class VideoItem
  extends AbstractSafeParcelable
{
  public static final Parcelable.Creator<zzh> CREATOR = new DiscreteSeekBar.CustomState.1();
  public final String packageName;
  public final long zzadt;
  public final String zzafx;
  public final String zzafz;
  public final long zzagd;
  public final String zzage;
  public final long zzagf;
  public final boolean zzagg;
  public final long zzagh;
  public final boolean zzagi;
  public final boolean zzagj;
  public final String zzagk;
  public final String zzagv;
  public final boolean zzagw;
  public final long zzagx;
  public final int zzagy;
  public final boolean zzagz;
  public final String zzts;
  
  VideoItem(String paramString1, String paramString2, String paramString3, long paramLong1, String paramString4, long paramLong2, long paramLong3, String paramString5, boolean paramBoolean1, boolean paramBoolean2, String paramString6, long paramLong4, long paramLong5, int paramInt, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5, String paramString7)
  {
    Preconditions.checkNotEmpty(paramString1);
    packageName = paramString1;
    if (TextUtils.isEmpty(paramString2)) {
      paramString2 = null;
    }
    zzafx = paramString2;
    zzts = paramString3;
    zzagd = paramLong1;
    zzage = paramString4;
    zzadt = paramLong2;
    zzagf = paramLong3;
    zzagv = paramString5;
    zzagg = paramBoolean1;
    zzagw = paramBoolean2;
    zzafz = paramString6;
    zzagh = paramLong4;
    zzagx = paramLong5;
    zzagy = paramInt;
    zzagi = paramBoolean3;
    zzagj = paramBoolean4;
    zzagz = paramBoolean5;
    zzagk = paramString7;
  }
  
  VideoItem(String paramString1, String paramString2, String paramString3, String paramString4, long paramLong1, long paramLong2, String paramString5, boolean paramBoolean1, boolean paramBoolean2, long paramLong3, String paramString6, long paramLong4, long paramLong5, int paramInt, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5, String paramString7)
  {
    packageName = paramString1;
    zzafx = paramString2;
    zzts = paramString3;
    zzagd = paramLong3;
    zzage = paramString4;
    zzadt = paramLong1;
    zzagf = paramLong2;
    zzagv = paramString5;
    zzagg = paramBoolean1;
    zzagw = paramBoolean2;
    zzafz = paramString6;
    zzagh = paramLong4;
    zzagx = paramLong5;
    zzagy = paramInt;
    zzagi = paramBoolean3;
    zzagj = paramBoolean4;
    zzagz = paramBoolean5;
    zzagk = paramString7;
  }
  
  public final void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramInt = SafeParcelWriter.beginObjectHeader(paramParcel);
    SafeParcelWriter.writeString(paramParcel, 2, packageName, false);
    SafeParcelWriter.writeString(paramParcel, 3, zzafx, false);
    SafeParcelWriter.writeString(paramParcel, 4, zzts, false);
    SafeParcelWriter.writeString(paramParcel, 5, zzage, false);
    SafeParcelWriter.writeLong(paramParcel, 6, zzadt);
    SafeParcelWriter.writeLong(paramParcel, 7, zzagf);
    SafeParcelWriter.writeString(paramParcel, 8, zzagv, false);
    SafeParcelWriter.writeBoolean(paramParcel, 9, zzagg);
    SafeParcelWriter.writeBoolean(paramParcel, 10, zzagw);
    SafeParcelWriter.writeLong(paramParcel, 11, zzagd);
    SafeParcelWriter.writeString(paramParcel, 12, zzafz, false);
    SafeParcelWriter.writeLong(paramParcel, 13, zzagh);
    SafeParcelWriter.writeLong(paramParcel, 14, zzagx);
    SafeParcelWriter.writeInt(paramParcel, 15, zzagy);
    SafeParcelWriter.writeBoolean(paramParcel, 16, zzagi);
    SafeParcelWriter.writeBoolean(paramParcel, 17, zzagj);
    SafeParcelWriter.writeBoolean(paramParcel, 18, zzagz);
    SafeParcelWriter.writeString(paramParcel, 19, zzagk, false);
    SafeParcelWriter.finishObjectHeader(paramParcel, paramInt);
  }
}
