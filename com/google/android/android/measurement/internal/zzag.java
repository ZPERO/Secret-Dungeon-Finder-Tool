package com.google.android.android.measurement.internal;

import android.os.IInterface;
import android.os.RemoteException;
import java.util.List;

public abstract interface zzag
  extends IInterface
{
  public abstract void chmod(VideoItem paramVideoItem)
    throws RemoteException;
  
  public abstract byte[] get(zzad paramZzad, String paramString)
    throws RemoteException;
  
  public abstract String getAbsoluteUrl(VideoItem paramVideoItem)
    throws RemoteException;
  
  public abstract List getFromLocationName(VideoItem paramVideoItem, boolean paramBoolean)
    throws RemoteException;
  
  public abstract List getFromLocationName(String paramString1, String paramString2, VideoItem paramVideoItem)
    throws RemoteException;
  
  public abstract List getFromLocationName(String paramString1, String paramString2, String paramString3)
    throws RemoteException;
  
  public abstract List getFromLocationName(String paramString1, String paramString2, String paramString3, boolean paramBoolean)
    throws RemoteException;
  
  public abstract List getFromLocationName(String paramString1, String paramString2, boolean paramBoolean, VideoItem paramVideoItem)
    throws RemoteException;
  
  public abstract void getPackageInfo(ComponentInfo paramComponentInfo, VideoItem paramVideoItem)
    throws RemoteException;
  
  public abstract void handleResult(VideoItem paramVideoItem)
    throws RemoteException;
  
  public abstract void handleResult(zzad paramZzad, VideoItem paramVideoItem)
    throws RemoteException;
  
  public abstract void handleResult(zzad paramZzad, String paramString1, String paramString2)
    throws RemoteException;
  
  public abstract void onNetworkStateChanged(long paramLong, String paramString1, String paramString2, String paramString3)
    throws RemoteException;
  
  public abstract void openInPhone(VideoItem paramVideoItem)
    throws RemoteException;
  
  public abstract void toggleState(ComponentInfo paramComponentInfo)
    throws RemoteException;
  
  public abstract void write(zzfh paramZzfh, VideoItem paramVideoItem)
    throws RemoteException;
}
