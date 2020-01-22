package androidx.media;

import android.content.Context;

class MediaSessionManagerImplApi21
  extends MediaSessionManagerImplBase
{
  MediaSessionManagerImplApi21(Context paramContext)
  {
    super(paramContext);
    mContext = paramContext;
  }
  
  private boolean hasMediaControlPermission(MediaSessionManager.RemoteUserInfoImpl paramRemoteUserInfoImpl)
  {
    return getContext().checkPermission("android.permission.MEDIA_CONTENT_CONTROL", paramRemoteUserInfoImpl.getPid(), paramRemoteUserInfoImpl.getUid()) == 0;
  }
  
  public boolean isTrustedForMediaControl(MediaSessionManager.RemoteUserInfoImpl paramRemoteUserInfoImpl)
  {
    return (hasMediaControlPermission(paramRemoteUserInfoImpl)) || (super.isTrustedForMediaControl(paramRemoteUserInfoImpl));
  }
}
