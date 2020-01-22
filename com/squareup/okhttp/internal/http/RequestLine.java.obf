package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.Request;
import java.net.Proxy.Type;

public final class RequestLine
{
  private RequestLine() {}
  
  static String get(Request paramRequest, Proxy.Type paramType)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramRequest.method());
    localStringBuilder.append(' ');
    if (includeAuthorityInRequestLine(paramRequest, paramType)) {
      localStringBuilder.append(paramRequest.httpUrl());
    } else {
      localStringBuilder.append(requestPath(paramRequest.httpUrl()));
    }
    localStringBuilder.append(" HTTP/1.1");
    return localStringBuilder.toString();
  }
  
  private static boolean includeAuthorityInRequestLine(Request paramRequest, Proxy.Type paramType)
  {
    return (!paramRequest.isHttps()) && (paramType == Proxy.Type.HTTP);
  }
  
  public static String requestPath(HttpUrl paramHttpUrl)
  {
    String str1 = paramHttpUrl.encodedPath();
    String str2 = paramHttpUrl.encodedQuery();
    paramHttpUrl = str1;
    if (str2 != null)
    {
      paramHttpUrl = new StringBuilder();
      paramHttpUrl.append(str1);
      paramHttpUrl.append('?');
      paramHttpUrl.append(str2);
      paramHttpUrl = paramHttpUrl.toString();
    }
    return paramHttpUrl;
  }
}
