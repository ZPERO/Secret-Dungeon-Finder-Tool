package com.google.android.android.common.aimsicd.internal;

import com.google.android.android.common.data.DataHolder;
import com.google.android.gms.common.api.internal.ListenerHolder.Notifier;

public abstract class DataHolderNotifier<L>
  implements ListenerHolder.Notifier<L>
{
  private final DataHolder mDataHolder;
  
  protected DataHolderNotifier(DataHolder paramDataHolder)
  {
    mDataHolder = paramDataHolder;
  }
  
  public final void notifyListener(Object paramObject)
  {
    notifyListener(paramObject, mDataHolder);
  }
  
  protected abstract void notifyListener(Object paramObject, DataHolder paramDataHolder);
  
  public void onNotifyListenerFailed()
  {
    DataHolder localDataHolder = mDataHolder;
    if (localDataHolder != null) {
      localDataHolder.close();
    }
  }
}
