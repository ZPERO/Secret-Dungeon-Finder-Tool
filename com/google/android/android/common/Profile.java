package com.google.android.android.common;

import android.content.Context;
import android.os.RemoteException;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import android.util.Log;
import com.google.android.android.common.internal.IStatusBarCustomTileHolder;
import com.google.android.android.common.internal.IStatusBarCustomTileHolder.Stub;
import com.google.android.android.common.internal.Preconditions;
import com.google.android.android.dynamic.ObjectWrapper;
import com.google.android.android.dynamite.DynamiteModule;
import com.google.android.android.dynamite.DynamiteModule.LoadingException;
import javax.annotation.CheckReturnValue;

@CheckReturnValue
final class Profile
{
  private static Context context;
  private static volatile IStatusBarCustomTileHolder instance;
  private static final Object list = new Object();
  
  private static Message getInstance(String paramString, Name paramName, boolean paramBoolean)
  {
    Object localObject;
    if (instance == null) {
      localObject = context;
    }
    try
    {
      Preconditions.checkNotNull(localObject);
      localObject = list;
      try
      {
        if (instance == null) {
          instance = IStatusBarCustomTileHolder.Stub.asInterface(DynamiteModule.load(context, DynamiteModule.PREFER_HIGHEST_OR_LOCAL_VERSION_NO_FORCE_STAGING, "com.google.android.gms.googlecertificates").instantiate("com.google.android.gms.common.GoogleCertificatesImpl"));
        }
      }
      catch (Throwable paramString)
      {
        throw paramString;
      }
      Preconditions.checkNotNull(context);
      localObject = new Script(paramString, paramName, paramBoolean);
      IStatusBarCustomTileHolder localIStatusBarCustomTileHolder = instance;
      Context localContext = context;
      try
      {
        boolean bool = localIStatusBarCustomTileHolder.get((Script)localObject, ObjectWrapper.wrap(localContext.getPackageManager()));
        if (bool) {
          return Message.getInstance();
        }
        return Message.create(new Preferences.1(paramBoolean, paramString, paramName));
      }
      catch (RemoteException paramString)
      {
        Log.e("GoogleCertificates", "Failed to get Google certificates from remote", paramString);
        return Message.create("module call", paramString);
      }
      return Message.create(paramString, paramName);
    }
    catch (DynamiteModule.LoadingException paramName)
    {
      Log.e("GoogleCertificates", "Failed to get Google certificates from remote", paramName);
      paramString = String.valueOf(paramName.getMessage());
      if (paramString.length() != 0) {
        paramString = "module init: ".concat(paramString);
      } else {
        paramString = new String("module init: ");
      }
    }
  }
  
  static void init(Context paramContext)
  {
    try
    {
      if (context == null)
      {
        if (paramContext != null) {
          context = paramContext.getApplicationContext();
        }
      }
      else {
        Log.w("GoogleCertificates", "GoogleCertificates has been initialized already");
      }
      return;
    }
    catch (Throwable paramContext)
    {
      throw paramContext;
    }
  }
  
  static Message read(String paramString, Name paramName, boolean paramBoolean)
  {
    StrictMode.ThreadPolicy localThreadPolicy = StrictMode.allowThreadDiskReads();
    try
    {
      paramString = getInstance(paramString, paramName, paramBoolean);
      StrictMode.setThreadPolicy(localThreadPolicy);
      return paramString;
    }
    catch (Throwable paramString)
    {
      StrictMode.setThreadPolicy(localThreadPolicy);
      throw paramString;
    }
  }
}
