package com.google.android.android.common.aimsicd.internal;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import com.google.android.android.common.R.string;
import com.google.android.android.common.aimsicd.Status;
import com.google.android.android.common.internal.AppUtils;
import com.google.android.android.common.internal.Preconditions;
import com.google.android.android.common.internal.StringResourceValueReader;

@Deprecated
public final class GoogleServices
{
  private static final Object sLock = new Object();
  private static GoogleServices zzax;
  private final String zzay;
  private final Status zzaz;
  private final boolean zzba;
  private final boolean zzbb;
  
  GoogleServices(Context paramContext)
  {
    Object localObject = paramContext.getResources();
    int i = ((Resources)localObject).getIdentifier("google_app_measurement_enable", "integer", ((Resources)localObject).getResourcePackageName(R.string.common_google_play_services_unknown_issue));
    boolean bool2 = true;
    boolean bool1 = true;
    if (i != 0)
    {
      if (((Resources)localObject).getInteger(i) == 0) {
        bool1 = false;
      }
      zzbb = (bool1 ^ true);
    }
    else
    {
      zzbb = false;
      bool1 = bool2;
    }
    zzba = bool1;
    String str = AppUtils.getVersionString(paramContext);
    localObject = str;
    if (str == null) {
      localObject = new StringResourceValueReader(paramContext).getString("google_app_id");
    }
    if (TextUtils.isEmpty((CharSequence)localObject))
    {
      zzaz = new Status(10, "Missing google app id value from from string resources with name google_app_id.");
      zzay = null;
      return;
    }
    zzay = ((String)localObject);
    zzaz = Status.RESULT_SUCCESS;
  }
  
  GoogleServices(String paramString, boolean paramBoolean)
  {
    zzay = paramString;
    zzaz = Status.RESULT_SUCCESS;
    zzba = paramBoolean;
    zzbb = (paramBoolean ^ true);
  }
  
  private static GoogleServices checkInitialized(String paramString)
  {
    Object localObject = sLock;
    try
    {
      if (zzax != null)
      {
        paramString = zzax;
        return paramString;
      }
      StringBuilder localStringBuilder = new StringBuilder(String.valueOf(paramString).length() + 34);
      localStringBuilder.append("Initialize must be called before ");
      localStringBuilder.append(paramString);
      localStringBuilder.append(".");
      throw new IllegalStateException(localStringBuilder.toString());
    }
    catch (Throwable paramString)
    {
      throw paramString;
    }
  }
  
  static void clearInstanceForTest()
  {
    Object localObject = sLock;
    try
    {
      zzax = null;
      return;
    }
    catch (Throwable localThrowable)
    {
      throw localThrowable;
    }
  }
  
  public static String getGoogleAppId()
  {
    return checkInitialized"getGoogleAppId"zzay;
  }
  
  public static Status initialize(Context paramContext)
  {
    Preconditions.checkNotNull(paramContext, "Context must not be null.");
    Object localObject = sLock;
    try
    {
      if (zzax == null) {
        zzax = new GoogleServices(paramContext);
      }
      paramContext = zzaxzzaz;
      return paramContext;
    }
    catch (Throwable paramContext)
    {
      throw paramContext;
    }
  }
  
  public static Status initialize(Context paramContext, String paramString, boolean paramBoolean)
  {
    Preconditions.checkNotNull(paramContext, "Context must not be null.");
    Preconditions.checkNotEmpty(paramString, "App ID must be nonempty.");
    paramContext = sLock;
    try
    {
      if (zzax != null)
      {
        paramString = zzax.checkGoogleAppId(paramString);
        return paramString;
      }
      paramString = new GoogleServices(paramString, paramBoolean);
      zzax = paramString;
      paramString = zzaz;
      return paramString;
    }
    catch (Throwable paramString)
    {
      throw paramString;
    }
  }
  
  public static boolean isMeasurementEnabled()
  {
    GoogleServices localGoogleServices = checkInitialized("isMeasurementEnabled");
    return (zzaz.isSuccess()) && (zzba);
  }
  
  public static boolean isMeasurementExplicitlyDisabled()
  {
    return checkInitialized"isMeasurementExplicitlyDisabled"zzbb;
  }
  
  final Status checkGoogleAppId(String paramString)
  {
    Object localObject = zzay;
    if ((localObject != null) && (!((String)localObject).equals(paramString)))
    {
      paramString = zzay;
      localObject = new StringBuilder(String.valueOf(paramString).length() + 97);
      ((StringBuilder)localObject).append("Initialize was called with two different Google App IDs.  Only the first app ID will be used: '");
      ((StringBuilder)localObject).append(paramString);
      ((StringBuilder)localObject).append("'.");
      return new Status(10, ((StringBuilder)localObject).toString());
    }
    return Status.RESULT_SUCCESS;
  }
}
