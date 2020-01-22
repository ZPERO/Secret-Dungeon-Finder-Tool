package com.google.android.android.common.aimsicd;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.IntentSender.SendIntentException;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.android.common.internal.Objects;
import com.google.android.android.common.internal.Objects.ToStringHelper;
import com.google.android.android.common.internal.ReflectedParcelable;
import com.google.android.android.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.android.common.internal.safeparcel.SafeParcelWriter;

public final class Status
  extends AbstractSafeParcelable
  implements Result, ReflectedParcelable
{
  public static final Parcelable.Creator<com.google.android.gms.common.api.Status> CREATOR = new VerticalProgressBar.SavedState.1();
  public static final Status RESULT_CANCELED;
  public static final Status RESULT_DEAD_CLIENT;
  public static final Status RESULT_INTERNAL_ERROR;
  public static final Status RESULT_INTERRUPTED;
  public static final Status RESULT_SUCCESS = new Status(0);
  public static final Status RESULT_TIMEOUT;
  private static final Status zzaq;
  private final int code;
  private final String message;
  private final PendingIntent title;
  private final int value;
  
  static
  {
    RESULT_INTERRUPTED = new Status(14);
    RESULT_INTERNAL_ERROR = new Status(8);
    RESULT_TIMEOUT = new Status(15);
    RESULT_CANCELED = new Status(16);
    zzaq = new Status(17);
    RESULT_DEAD_CLIENT = new Status(18);
  }
  
  public Status(int paramInt)
  {
    this(paramInt, null);
  }
  
  Status(int paramInt1, int paramInt2, String paramString, PendingIntent paramPendingIntent)
  {
    value = paramInt1;
    code = paramInt2;
    message = paramString;
    title = paramPendingIntent;
  }
  
  public Status(int paramInt, String paramString)
  {
    this(1, paramInt, paramString, null);
  }
  
  public Status(int paramInt, String paramString, PendingIntent paramPendingIntent)
  {
    this(1, paramInt, paramString, paramPendingIntent);
  }
  
  public final boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof Status)) {
      return false;
    }
    paramObject = (Status)paramObject;
    return (value == value) && (code == code) && (Objects.equal(message, message)) && (Objects.equal(title, title));
  }
  
  public final String getMessage()
  {
    String str = message;
    if (str != null) {
      return str;
    }
    return CommonStatusCodes.getStatusCodeString(code);
  }
  
  public final PendingIntent getResolution()
  {
    return title;
  }
  
  public final Status getStatus()
  {
    return this;
  }
  
  public final int getStatusCode()
  {
    return code;
  }
  
  public final String getStatusMessage()
  {
    return message;
  }
  
  public final boolean hasResolution()
  {
    return title != null;
  }
  
  public final int hashCode()
  {
    return Objects.hashCode(new Object[] { Integer.valueOf(value), Integer.valueOf(code), message, title });
  }
  
  public final boolean isCanceled()
  {
    return code == 16;
  }
  
  public final boolean isInterrupted()
  {
    return code == 14;
  }
  
  public final boolean isSuccess()
  {
    return code <= 0;
  }
  
  public final void startResolutionForResult(Activity paramActivity, int paramInt)
    throws IntentSender.SendIntentException
  {
    if (!hasResolution()) {
      return;
    }
    paramActivity.startIntentSenderForResult(title.getIntentSender(), paramInt, null, 0, 0, 0);
  }
  
  public final String toString()
  {
    return Objects.toStringHelper(this).add("statusCode", getMessage()).add("resolution", title).toString();
  }
  
  public final void writeToParcel(Parcel paramParcel, int paramInt)
  {
    int i = SafeParcelWriter.beginObjectHeader(paramParcel);
    SafeParcelWriter.writeInt(paramParcel, 1, getStatusCode());
    SafeParcelWriter.writeString(paramParcel, 2, getStatusMessage(), false);
    SafeParcelWriter.writeParcelable(paramParcel, 3, title, paramInt, false);
    SafeParcelWriter.writeInt(paramParcel, 1000, value);
    SafeParcelWriter.finishObjectHeader(paramParcel, i);
  }
}
