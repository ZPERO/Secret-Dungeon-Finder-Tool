package com.google.android.android.common.internal;

import android.app.Activity;
import android.content.Intent;

final class UserAgent
  extends DialogRedirect
{
  UserAgent(Intent paramIntent, Activity paramActivity, int paramInt) {}
  
  public final void redirect()
  {
    Intent localIntent = zaog;
    if (localIntent != null) {
      val$activity.startActivityForResult(localIntent, val$requestCode);
    }
  }
}
