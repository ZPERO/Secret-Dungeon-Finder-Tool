package com.google.android.android.measurement.internal;

import android.os.RemoteException;

final class zzdt
  implements Runnable
{
  zzdt(zzdr paramZzdr, VideoItem paramVideoItem) {}
  
  public final void run()
  {
    zzag localZzag = zzdr.method_3(zzasg);
    if (localZzag == null)
    {
      zzasg.zzgo().zzjd().zzbx("Failed to reset data on the service; null service");
      return;
    }
    VideoItem localVideoItem = zzaqn;
    try
    {
      localZzag.handleResult(localVideoItem);
    }
    catch (RemoteException localRemoteException)
    {
      zzasg.zzgo().zzjd().append("Failed to reset data on the service", localRemoteException);
    }
    zzdr.Refresh(zzasg);
  }
}
