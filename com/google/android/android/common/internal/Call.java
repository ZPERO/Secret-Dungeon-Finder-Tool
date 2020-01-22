package com.google.android.android.common.internal;

import android.content.Intent;
import androidx.fragment.package_8.Fragment;

final class Call
  extends DialogRedirect
{
  Call(Intent paramIntent, Fragment paramFragment, int paramInt) {}
  
  public final void redirect()
  {
    Intent localIntent = zaog;
    if (localIntent != null) {
      val$fragment.startActivityForResult(localIntent, val$requestCode);
    }
  }
}
