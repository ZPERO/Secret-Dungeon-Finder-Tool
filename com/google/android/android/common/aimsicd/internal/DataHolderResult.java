package com.google.android.android.common.aimsicd.internal;

import com.google.android.android.common.aimsicd.Releasable;
import com.google.android.android.common.aimsicd.Result;
import com.google.android.android.common.aimsicd.Status;
import com.google.android.android.common.data.DataHolder;

public class DataHolderResult
  implements Releasable, Result
{
  protected final DataHolder mDataHolder;
  protected final Status mStatus;
  
  protected DataHolderResult(DataHolder paramDataHolder)
  {
    this(paramDataHolder, new Status(paramDataHolder.getStatusCode()));
  }
  
  protected DataHolderResult(DataHolder paramDataHolder, Status paramStatus)
  {
    mStatus = paramStatus;
    mDataHolder = paramDataHolder;
  }
  
  public Status getStatus()
  {
    return mStatus;
  }
  
  public void release()
  {
    DataHolder localDataHolder = mDataHolder;
    if (localDataHolder != null) {
      localDataHolder.close();
    }
  }
}
