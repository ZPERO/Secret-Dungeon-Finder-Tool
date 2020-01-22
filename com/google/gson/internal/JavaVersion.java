package com.google.gson.internal;

public final class JavaVersion
{
  private static final int majorJavaVersion = ;
  
  private JavaVersion() {}
  
  private static int determineMajorJavaVersion()
  {
    return getMajorJavaVersion(System.getProperty("java.version"));
  }
  
  private static int extractBeginningInt(String paramString)
  {
    try
    {
      StringBuilder localStringBuilder = new StringBuilder();
      int i = 0;
      for (;;)
      {
        int j = paramString.length();
        if (i >= j) {
          break;
        }
        char c = paramString.charAt(i);
        boolean bool = Character.isDigit(c);
        if (!bool) {
          break;
        }
        localStringBuilder.append(c);
        i += 1;
      }
      i = Integer.parseInt(localStringBuilder.toString());
      return i;
    }
    catch (NumberFormatException paramString)
    {
      for (;;) {}
    }
    return -1;
  }
  
  public static int getMajorJavaVersion()
  {
    return majorJavaVersion;
  }
  
  static int getMajorJavaVersion(String paramString)
  {
    int j = parseDotted(paramString);
    int i = j;
    if (j == -1) {
      i = extractBeginningInt(paramString);
    }
    if (i == -1) {
      return 6;
    }
    return i;
  }
  
  public static boolean isJava9OrLater()
  {
    return majorJavaVersion >= 9;
  }
  
  private static int parseDotted(String paramString)
  {
    int i;
    try
    {
      paramString = paramString.split("[._]");
      String str = paramString[0];
      i = Integer.parseInt(str);
      if (i == 1)
      {
        if (paramString.length <= 1) {
          return i;
        }
        paramString = paramString[1];
        i = Integer.parseInt(paramString);
        return i;
      }
      return i;
    }
    catch (NumberFormatException paramString)
    {
      for (;;) {}
    }
    return -1;
    return i;
  }
}
