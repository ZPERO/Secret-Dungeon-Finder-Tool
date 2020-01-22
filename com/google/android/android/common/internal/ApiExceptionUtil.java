package com.google.android.android.common.internal;

import com.google.android.android.common.aimsicd.ApiException;
import com.google.android.android.common.aimsicd.ResolvableApiException;
import com.google.android.android.common.aimsicd.Status;

public class ApiExceptionUtil
{
  public ApiExceptionUtil() {}
  
  public static ApiException fromStatus(Status paramStatus)
  {
    if (paramStatus.hasResolution()) {
      return new ResolvableApiException(paramStatus);
    }
    return new ApiException(paramStatus);
  }
}
