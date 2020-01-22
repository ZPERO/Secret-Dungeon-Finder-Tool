package com.google.android.android.common.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.android.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.android.common.internal.safeparcel.SafeParcelWriter;

public class ClientIdentity
  extends AbstractSafeParcelable
{
  public static final Parcelable.Creator<com.google.android.gms.common.internal.ClientIdentity> CREATOR = new PaymentIntent.1();
  private final String packageName;
  private final int pid;
  
  public ClientIdentity(int paramInt, String paramString)
  {
    pid = paramInt;
    packageName = paramString;
  }
  
  public boolean equals(Object paramObject)
  {
    if (paramObject == this) {
      return true;
    }
    if (paramObject != null)
    {
      if (!(paramObject instanceof ClientIdentity)) {
        return false;
      }
      paramObject = (ClientIdentity)paramObject;
      if ((pid == pid) && (Objects.equal(packageName, packageName))) {
        return true;
      }
    }
    return false;
  }
  
  public int hashCode()
  {
    return pid;
  }
  
  public String toString()
  {
    int i = pid;
    String str = packageName;
    StringBuilder localStringBuilder = new StringBuilder(String.valueOf(str).length() + 12);
    localStringBuilder.append(i);
    localStringBuilder.append(":");
    localStringBuilder.append(str);
    return localStringBuilder.toString();
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramInt = SafeParcelWriter.beginObjectHeader(paramParcel);
    SafeParcelWriter.writeInt(paramParcel, 1, pid);
    SafeParcelWriter.writeString(paramParcel, 2, packageName, false);
    SafeParcelWriter.finishObjectHeader(paramParcel, paramInt);
  }
}
