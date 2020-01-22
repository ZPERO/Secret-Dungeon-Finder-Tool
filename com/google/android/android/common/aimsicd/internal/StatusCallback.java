package com.google.android.android.common.aimsicd.internal;

public class StatusCallback
  extends IStatusCallback.Stub
{
  private final com.google.android.gms.common.api.internal.BaseImplementation.ResultHolder<com.google.android.gms.common.api.Status> mResultHolder;
  
  public StatusCallback(BaseImplementation.ResultHolder paramResultHolder)
  {
    mResultHolder = paramResultHolder;
  }
  
  public void onResult(com.google.android.android.common.aimsicd.Status paramStatus)
  {
    mResultHolder.setResult(paramStatus);
  }
}
