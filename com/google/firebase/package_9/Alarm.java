package com.google.firebase.package_9;

import android.content.Intent;

final class Alarm
  implements Runnable
{
  Alarm(IRCService paramIRCService, Intent paramIntent1, Intent paramIntent2) {}
  
  public final void run()
  {
    this$0.doInBackground(i);
    IRCService.access$getShowNotification(this$0, mHandler);
  }
}
