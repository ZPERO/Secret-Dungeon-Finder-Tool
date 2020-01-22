package com.google.android.android.common.aimsicd.internal;

import com.google.android.android.common.aimsicd.Status;

public abstract interface StatusExceptionMapper
{
  public abstract Exception getException(Status paramStatus);
}
