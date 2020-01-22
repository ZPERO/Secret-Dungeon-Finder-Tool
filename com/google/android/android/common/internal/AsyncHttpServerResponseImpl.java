package com.google.android.android.common.internal;

import android.content.Intent;
import com.google.android.android.common.aimsicd.internal.LifecycleFragment;

final class AsyncHttpServerResponseImpl
  extends DialogRedirect
{
  AsyncHttpServerResponseImpl(Intent paramIntent, LifecycleFragment paramLifecycleFragment, int paramInt) {}
  
  public final void redirect()
  {
    Intent localIntent = zaog;
    if (localIntent != null) {
      zaoh.startActivityForResult(localIntent, val$requestCode);
    }
  }
}
