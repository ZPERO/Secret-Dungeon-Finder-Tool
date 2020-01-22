package com.google.android.android.common.util;

import android.text.TextUtils;
import java.util.regex.Pattern;

public class Strings
{
  private static final Pattern zzhf = Pattern.compile("\\$\\{(.*?)\\}");
  
  private Strings() {}
  
  public static String emptyToNull(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return null;
    }
    return paramString;
  }
  
  public static boolean isEmptyOrWhitespace(String paramString)
  {
    return (paramString == null) || (paramString.trim().isEmpty());
  }
}
