package com.google.android.android.measurement.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.android.common.internal.Preconditions;
import com.google.android.android.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.android.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.measurement.internal.zzl;

public final class ComponentInfo
  extends AbstractSafeParcelable
{
  public static final Parcelable.Creator<zzl> CREATOR = new VerticalProgressBar.SavedState.1();
  public boolean active;
  public long creationTimestamp;
  public String origin;
  public String packageName;
  public long timeToLive;
  public String triggerEventName;
  public long triggerTimeout;
  public zzfh zzahb;
  public zzad zzahc;
  public zzad zzahd;
  public zzad zzahe;
  
  ComponentInfo(ComponentInfo paramComponentInfo)
  {
    Preconditions.checkNotNull(paramComponentInfo);
    packageName = packageName;
    origin = origin;
    zzahb = zzahb;
    creationTimestamp = creationTimestamp;
    active = active;
    triggerEventName = triggerEventName;
    zzahc = zzahc;
    triggerTimeout = triggerTimeout;
    zzahd = zzahd;
    timeToLive = timeToLive;
    zzahe = zzahe;
  }
  
  ComponentInfo(String paramString1, String paramString2, zzfh paramZzfh, long paramLong1, boolean paramBoolean, String paramString3, zzad paramZzad1, long paramLong2, zzad paramZzad2, long paramLong3, zzad paramZzad3)
  {
    packageName = paramString1;
    origin = paramString2;
    zzahb = paramZzfh;
    creationTimestamp = paramLong1;
    active = paramBoolean;
    triggerEventName = paramString3;
    zzahc = paramZzad1;
    triggerTimeout = paramLong2;
    zzahd = paramZzad2;
    timeToLive = paramLong3;
    zzahe = paramZzad3;
  }
  
  public final void writeToParcel(Parcel paramParcel, int paramInt)
  {
    int i = SafeParcelWriter.beginObjectHeader(paramParcel);
    SafeParcelWriter.writeString(paramParcel, 2, packageName, false);
    SafeParcelWriter.writeString(paramParcel, 3, origin, false);
    SafeParcelWriter.writeParcelable(paramParcel, 4, zzahb, paramInt, false);
    SafeParcelWriter.writeLong(paramParcel, 5, creationTimestamp);
    SafeParcelWriter.writeBoolean(paramParcel, 6, active);
    SafeParcelWriter.writeString(paramParcel, 7, triggerEventName, false);
    SafeParcelWriter.writeParcelable(paramParcel, 8, zzahc, paramInt, false);
    SafeParcelWriter.writeLong(paramParcel, 9, triggerTimeout);
    SafeParcelWriter.writeParcelable(paramParcel, 10, zzahd, paramInt, false);
    SafeParcelWriter.writeLong(paramParcel, 11, timeToLive);
    SafeParcelWriter.writeParcelable(paramParcel, 12, zzahe, paramInt, false);
    SafeParcelWriter.finishObjectHeader(paramParcel, i);
  }
}
