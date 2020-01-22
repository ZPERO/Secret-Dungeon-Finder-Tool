package com.google.android.android.common;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.android.common.internal.Objects;
import com.google.android.android.common.internal.Objects.ToStringHelper;
import com.google.android.android.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.android.common.internal.safeparcel.SafeParcelWriter;

public class Feature
  extends AbstractSafeParcelable
{
  public static final Parcelable.Creator<com.google.android.gms.common.Feature> CREATOR = new DiscreteSeekBar.CustomState.1();
  private final long author;
  private final String name;
  @Deprecated
  private final int version;
  
  public Feature(String paramString, int paramInt, long paramLong)
  {
    name = paramString;
    version = paramInt;
    author = paramLong;
  }
  
  public Feature(String paramString, long paramLong)
  {
    name = paramString;
    author = paramLong;
    version = -1;
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject instanceof Feature))
    {
      paramObject = (Feature)paramObject;
      if (((getName() != null) && (getName().equals(paramObject.getName()))) || ((getName() == null) && (paramObject.getName() == null) && (getVersion() == paramObject.getVersion()))) {
        return true;
      }
    }
    return false;
  }
  
  public String getName()
  {
    return name;
  }
  
  public long getVersion()
  {
    long l2 = author;
    long l1 = l2;
    if (l2 == -1L) {
      l1 = version;
    }
    return l1;
  }
  
  public int hashCode()
  {
    return Objects.hashCode(new Object[] { getName(), Long.valueOf(getVersion()) });
  }
  
  public String toString()
  {
    return Objects.toStringHelper(this).add("name", getName()).add("version", Long.valueOf(getVersion())).toString();
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramInt = SafeParcelWriter.beginObjectHeader(paramParcel);
    SafeParcelWriter.writeString(paramParcel, 1, getName(), false);
    SafeParcelWriter.writeInt(paramParcel, 2, version);
    SafeParcelWriter.writeLong(paramParcel, 3, getVersion());
    SafeParcelWriter.finishObjectHeader(paramParcel, paramInt);
  }
}
