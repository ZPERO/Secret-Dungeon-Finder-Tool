package com.google.android.android.measurement.internal;

final class zzed
  implements Runnable
{
  zzed(zzdr paramZzdr, boolean paramBoolean, zzfh paramZzfh, VideoItem paramVideoItem) {}
  
  public final void run()
  {
    zzag localZzag = zzdr.method_3(zzasg);
    if (localZzag == null)
    {
      zzasg.zzgo().zzjd().zzbx("Discarding data. Failed to set user attribute");
      return;
    }
    zzdr localZzdr = zzasg;
    zzfh localZzfh;
    if (zzasj) {
      localZzfh = null;
    } else {
      localZzfh = zzaqs;
    }
    localZzdr.sendRequest(localZzag, localZzfh, zzaqn);
    zzdr.Refresh(zzasg);
  }
}
