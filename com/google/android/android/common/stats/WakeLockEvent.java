package com.google.android.android.common.stats;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.google.android.android.common.internal.safeparcel.SafeParcelWriter;
import java.util.List;

public final class WakeLockEvent
  extends StatsEvent
{
  public static final Parcelable.Creator<com.google.android.gms.common.stats.WakeLockEvent> CREATOR = new VerticalProgressBar.SavedState.1();
  private final long mTimeout;
  private final int startMinute;
  private final long zzfo;
  private int zzfp;
  private final String zzfq;
  private final String zzfr;
  private final String zzfs;
  private final int zzft;
  private final List<String> zzfu;
  private final String zzfv;
  private final long zzfw;
  private int zzfx;
  private final String zzfy;
  private final float zzfz;
  private long zzga;
  
  WakeLockEvent(int paramInt1, long paramLong1, int paramInt2, String paramString1, int paramInt3, List paramList, String paramString2, long paramLong2, int paramInt4, String paramString3, String paramString4, float paramFloat, long paramLong3, String paramString5)
  {
    startMinute = paramInt1;
    zzfo = paramLong1;
    zzfp = paramInt2;
    zzfq = paramString1;
    zzfr = paramString3;
    zzfs = paramString5;
    zzft = paramInt3;
    zzga = -1L;
    zzfu = paramList;
    zzfv = paramString2;
    zzfw = paramLong2;
    zzfx = paramInt4;
    zzfy = paramString4;
    zzfz = paramFloat;
    mTimeout = paramLong3;
  }
  
  public WakeLockEvent(long paramLong1, int paramInt1, String paramString1, int paramInt2, List paramList, String paramString2, long paramLong2, int paramInt3, String paramString3, String paramString4, float paramFloat, long paramLong3, String paramString5)
  {
    this(2, paramLong1, paramInt1, paramString1, paramInt2, paramList, paramString2, paramLong2, paramInt3, paramString3, paramString4, paramFloat, paramLong3, paramString5);
  }
  
  public final long getDateAsString()
  {
    return zzga;
  }
  
  public final int getEventType()
  {
    return zzfp;
  }
  
  public final String getRawText()
  {
    String str = zzfq;
    int i = zzft;
    Object localObject1 = zzfu;
    Object localObject4 = "";
    if (localObject1 == null) {
      localObject1 = "";
    } else {
      localObject1 = TextUtils.join(",", (Iterable)localObject1);
    }
    int j = zzfx;
    Object localObject3 = zzfr;
    Object localObject2 = localObject3;
    if (localObject3 == null) {
      localObject2 = "";
    }
    Object localObject5 = zzfy;
    localObject3 = localObject5;
    if (localObject5 == null) {
      localObject3 = "";
    }
    float f = zzfz;
    localObject5 = zzfs;
    if (localObject5 != null) {
      localObject4 = localObject5;
    }
    localObject5 = new StringBuilder(String.valueOf(str).length() + 45 + String.valueOf(localObject1).length() + String.valueOf(localObject2).length() + String.valueOf(localObject3).length() + String.valueOf(localObject4).length());
    ((StringBuilder)localObject5).append("\t");
    ((StringBuilder)localObject5).append(str);
    ((StringBuilder)localObject5).append("\t");
    ((StringBuilder)localObject5).append(i);
    ((StringBuilder)localObject5).append("\t");
    ((StringBuilder)localObject5).append((String)localObject1);
    ((StringBuilder)localObject5).append("\t");
    ((StringBuilder)localObject5).append(j);
    ((StringBuilder)localObject5).append("\t");
    ((StringBuilder)localObject5).append((String)localObject2);
    ((StringBuilder)localObject5).append("\t");
    ((StringBuilder)localObject5).append((String)localObject3);
    ((StringBuilder)localObject5).append("\t");
    ((StringBuilder)localObject5).append(f);
    ((StringBuilder)localObject5).append("\t");
    ((StringBuilder)localObject5).append((String)localObject4);
    return ((StringBuilder)localObject5).toString();
  }
  
  public final long getTimeMillis()
  {
    return zzfo;
  }
  
  public final void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramInt = SafeParcelWriter.beginObjectHeader(paramParcel);
    SafeParcelWriter.writeInt(paramParcel, 1, startMinute);
    SafeParcelWriter.writeLong(paramParcel, 2, getTimeMillis());
    SafeParcelWriter.writeString(paramParcel, 4, zzfq, false);
    SafeParcelWriter.writeInt(paramParcel, 5, zzft);
    SafeParcelWriter.writeStringList(paramParcel, 6, zzfu, false);
    SafeParcelWriter.writeLong(paramParcel, 8, zzfw);
    SafeParcelWriter.writeString(paramParcel, 10, zzfr, false);
    SafeParcelWriter.writeInt(paramParcel, 11, getEventType());
    SafeParcelWriter.writeString(paramParcel, 12, zzfv, false);
    SafeParcelWriter.writeString(paramParcel, 13, zzfy, false);
    SafeParcelWriter.writeInt(paramParcel, 14, zzfx);
    SafeParcelWriter.writeFloat(paramParcel, 15, zzfz);
    SafeParcelWriter.writeLong(paramParcel, 16, mTimeout);
    SafeParcelWriter.writeString(paramParcel, 17, zzfs, false);
    SafeParcelWriter.finishObjectHeader(paramParcel, paramInt);
  }
}
