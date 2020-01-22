package com.google.firebase.analytics.connector.internal;

import android.os.Bundle;
import com.google.android.android.common.util.ArrayUtils;
import com.google.android.android.measurement.AppMeasurement.ConditionalUserProperty;
import com.google.android.android.measurement.AppMeasurement.Event;
import com.google.android.android.measurement.AppMeasurement.UserProperty;
import com.google.android.android.measurement.internal.zzfk;
import com.google.firebase.analytics.connector.AnalyticsConnector.ConditionalUserProperty;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public final class Volume
{
  private static final Set<String> zzbsm = new HashSet(Arrays.asList(new String[] { "_in", "_xa", "_xu", "_aq", "_aa", "_ai", "_ac", "campaign_details", "_ug", "_iapx", "_exp_set", "_exp_clear", "_exp_activate", "_exp_timeout", "_exp_expire" }));
  private static final List<String> zzbsn = Arrays.asList(new String[] { "_e", "_f", "_iap", "_s", "_au", "_ui", "_cd", "app_open" });
  private static final List<String> zzbso = Arrays.asList(new String[] { "auto", "app", "am" });
  private static final List<String> zzbsp = Arrays.asList(new String[] { "_r", "_dbg" });
  private static final List<String> zzbsq = Arrays.asList((String[])ArrayUtils.concat(new String[][] { AppMeasurement.UserProperty.zzado, AppMeasurement.UserProperty.zzadp }));
  private static final List<String> zzbsr = Arrays.asList(new String[] { "^_ltv_[A-Z]{3}$", "^_cc[1-5]{1}$" });
  
  public static AppMeasurement.ConditionalUserProperty decompress(AnalyticsConnector.ConditionalUserProperty paramConditionalUserProperty)
  {
    AppMeasurement.ConditionalUserProperty localConditionalUserProperty = new AppMeasurement.ConditionalUserProperty();
    mOrigin = origin;
    mActive = active;
    mCreationTimestamp = creationTimestamp;
    mExpiredEventName = expiredEventName;
    if (expiredEventParams != null) {
      mExpiredEventParams = new Bundle(expiredEventParams);
    }
    mName = name;
    mTimedOutEventName = timedOutEventName;
    if (timedOutEventParams != null) {
      mTimedOutEventParams = new Bundle(timedOutEventParams);
    }
    mTimeToLive = timeToLive;
    mTriggeredEventName = triggeredEventName;
    if (triggeredEventParams != null) {
      mTriggeredEventParams = new Bundle(triggeredEventParams);
    }
    mTriggeredTimestamp = triggeredTimestamp;
    mTriggerEventName = triggerEventName;
    mTriggerTimeout = triggerTimeout;
    if (value != null) {
      mValue = zzfk.put(value);
    }
    return localConditionalUserProperty;
  }
  
  public static boolean get(String paramString, Bundle paramBundle)
  {
    if (zzbsn.contains(paramString)) {
      return false;
    }
    if (paramBundle != null)
    {
      paramString = zzbsp.iterator();
      while (paramString.hasNext()) {
        if (paramBundle.containsKey((String)paramString.next())) {
          return false;
        }
      }
    }
    return true;
  }
  
  public static boolean get(String paramString1, String paramString2)
  {
    if ((!"_ce1".equals(paramString2)) && (!"_ce2".equals(paramString2)))
    {
      if ("_ln".equals(paramString2))
      {
        if (!paramString1.equals("fcm")) {
          return paramString1.equals("fiam");
        }
        return true;
      }
      if (zzbsq.contains(paramString2)) {
        return false;
      }
      paramString1 = zzbsr.iterator();
      while (paramString1.hasNext()) {
        if (paramString2.matches((String)paramString1.next())) {
          return false;
        }
      }
      return true;
    }
    if (!paramString1.equals("fcm")) {
      return paramString1.equals("frc");
    }
    return true;
  }
  
  public static boolean get(String paramString1, String paramString2, Bundle paramBundle)
  {
    if (!"_cmp".equals(paramString2)) {
      return true;
    }
    if (!zzfo(paramString1)) {
      return false;
    }
    if (paramBundle == null) {
      return false;
    }
    paramString2 = zzbsp.iterator();
    while (paramString2.hasNext()) {
      if (paramBundle.containsKey((String)paramString2.next())) {
        return false;
      }
    }
    int i = -1;
    int j = paramString1.hashCode();
    if (j != 101200)
    {
      if (j != 101230)
      {
        if ((j == 3142703) && (paramString1.equals("fiam"))) {
          i = 2;
        }
      }
      else if (paramString1.equals("fdl")) {
        i = 1;
      }
    }
    else if (paramString1.equals("fcm")) {
      i = 0;
    }
    if (i != 0)
    {
      if (i != 1)
      {
        if (i != 2) {
          return false;
        }
        paramBundle.putString("_cis", "fiam_integration");
        return true;
      }
      paramBundle.putString("_cis", "fdl_integration");
      return true;
    }
    paramBundle.putString("_cis", "fcm_integration");
    return true;
  }
  
  public static AnalyticsConnector.ConditionalUserProperty insertFolder(AppMeasurement.ConditionalUserProperty paramConditionalUserProperty)
  {
    AnalyticsConnector.ConditionalUserProperty localConditionalUserProperty = new AnalyticsConnector.ConditionalUserProperty();
    origin = mOrigin;
    active = mActive;
    creationTimestamp = mCreationTimestamp;
    expiredEventName = mExpiredEventName;
    if (mExpiredEventParams != null) {
      expiredEventParams = new Bundle(mExpiredEventParams);
    }
    name = mName;
    timedOutEventName = mTimedOutEventName;
    if (mTimedOutEventParams != null) {
      timedOutEventParams = new Bundle(mTimedOutEventParams);
    }
    timeToLive = mTimeToLive;
    triggeredEventName = mTriggeredEventName;
    if (mTriggeredEventParams != null) {
      triggeredEventParams = new Bundle(mTriggeredEventParams);
    }
    triggeredTimestamp = mTriggeredTimestamp;
    triggerEventName = mTriggerEventName;
    triggerTimeout = mTriggerTimeout;
    if (mValue != null) {
      value = zzfk.put(mValue);
    }
    return localConditionalUserProperty;
  }
  
  public static boolean renameItem(AnalyticsConnector.ConditionalUserProperty paramConditionalUserProperty)
  {
    if (paramConditionalUserProperty == null) {
      return false;
    }
    String str = origin;
    if (str != null)
    {
      if (str.isEmpty()) {
        return false;
      }
      if ((value != null) && (zzfk.put(value) == null)) {
        return false;
      }
      if (!zzfo(str)) {
        return false;
      }
      if (!get(str, name)) {
        return false;
      }
      if (expiredEventName != null)
      {
        if (!get(expiredEventName, expiredEventParams)) {
          return false;
        }
        if (!get(str, expiredEventName, expiredEventParams)) {
          return false;
        }
      }
      if (triggeredEventName != null)
      {
        if (!get(triggeredEventName, triggeredEventParams)) {
          return false;
        }
        if (!get(str, triggeredEventName, triggeredEventParams)) {
          return false;
        }
      }
      if (timedOutEventName != null)
      {
        if (!get(timedOutEventName, timedOutEventParams)) {
          return false;
        }
        if (!get(str, timedOutEventName, timedOutEventParams)) {
          return false;
        }
      }
      return true;
    }
    return false;
  }
  
  public static boolean zzfo(String paramString)
  {
    return !zzbso.contains(paramString);
  }
  
  public static boolean zzfp(String paramString)
  {
    return !zzbsm.contains(paramString);
  }
  
  public static boolean zzfq(String paramString)
  {
    if (paramString == null) {
      return false;
    }
    if (paramString.length() == 0) {
      return false;
    }
    int i = paramString.codePointAt(0);
    if (!Character.isLetter(i)) {
      return false;
    }
    int j = paramString.length();
    i = Character.charCount(i);
    while (i < j)
    {
      int k = paramString.codePointAt(i);
      if ((k != 95) && (!Character.isLetterOrDigit(k))) {
        return false;
      }
      i += Character.charCount(k);
    }
    return true;
  }
  
  public static boolean zzfr(String paramString)
  {
    if (paramString == null) {
      return false;
    }
    if (paramString.length() == 0) {
      return false;
    }
    int i = paramString.codePointAt(0);
    if ((!Character.isLetter(i)) && (i != 95)) {
      return false;
    }
    int j = paramString.length();
    i = Character.charCount(i);
    while (i < j)
    {
      int k = paramString.codePointAt(i);
      if ((k != 95) && (!Character.isLetterOrDigit(k))) {
        return false;
      }
      i += Character.charCount(k);
    }
    return true;
  }
  
  public static String zzfs(String paramString)
  {
    String str = AppMeasurement.Event.zzak(paramString);
    if (str != null) {
      return str;
    }
    return paramString;
  }
  
  public static String zzft(String paramString)
  {
    String str = AppMeasurement.Event.zzal(paramString);
    if (str != null) {
      return str;
    }
    return paramString;
  }
}