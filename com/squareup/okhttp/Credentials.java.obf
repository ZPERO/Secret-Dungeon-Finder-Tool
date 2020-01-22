package com.squareup.okhttp;

import java.io.UnsupportedEncodingException;
import okio.ByteString;

public final class Credentials
{
  private Credentials() {}
  
  public static String basic(String paramString1, String paramString2)
  {
    try
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(paramString1);
      localStringBuilder.append(":");
      localStringBuilder.append(paramString2);
      paramString1 = ByteString.of(localStringBuilder.toString().getBytes("ISO-8859-1")).base64();
      paramString2 = new StringBuilder();
      paramString2.append("Basic ");
      paramString2.append(paramString1);
      paramString1 = paramString2.toString();
      return paramString1;
    }
    catch (UnsupportedEncodingException paramString1)
    {
      for (;;) {}
    }
    throw new AssertionError();
  }
}
