package com.google.android.android.common.aimsicd.internal;

import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.google.android.android.internal.base.Credentials;

final class zabg
  extends Credentials
{
  zabg(zabe paramZabe, Looper paramLooper)
  {
    super(paramLooper);
  }
  
  public final void handleMessage(Message paramMessage)
  {
    int i = what;
    if (i != 1)
    {
      if (i != 2)
      {
        i = what;
        paramMessage = new StringBuilder(31);
        paramMessage.append("Unknown message id: ");
        paramMessage.append(i);
        Log.w("GACStateManager", paramMessage.toString());
        return;
      }
      throw ((RuntimeException)obj);
    }
    ((zabf)obj).removeFile(zahu);
  }
}
