package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.Authenticator;
import com.squareup.okhttp.Challenge;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Headers.Builder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Request.Builder;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.internal.Platform;
import com.squareup.okhttp.internal.Util;
import java.io.IOException;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public final class OkHeaders
{
  private static final Comparator<String> FIELD_NAME_COMPARATOR = new Comparator()
  {
    public int compare(String paramAnonymousString1, String paramAnonymousString2)
    {
      if (paramAnonymousString1 == paramAnonymousString2) {
        return 0;
      }
      if (paramAnonymousString1 == null) {
        return -1;
      }
      if (paramAnonymousString2 == null) {
        return 1;
      }
      return String.CASE_INSENSITIVE_ORDER.compare(paramAnonymousString1, paramAnonymousString2);
    }
  };
  static final String PREFIX = Platform.get().getPrefix();
  public static final String RECEIVED_MILLIS;
  public static final String RESPONSE_SOURCE;
  public static final String SELECTED_PROTOCOL;
  public static final String SENT_MILLIS;
  
  static
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(PREFIX);
    localStringBuilder.append("-Sent-Millis");
    SENT_MILLIS = localStringBuilder.toString();
    localStringBuilder = new StringBuilder();
    localStringBuilder.append(PREFIX);
    localStringBuilder.append("-Received-Millis");
    RECEIVED_MILLIS = localStringBuilder.toString();
    localStringBuilder = new StringBuilder();
    localStringBuilder.append(PREFIX);
    localStringBuilder.append("-Selected-Protocol");
    SELECTED_PROTOCOL = localStringBuilder.toString();
    localStringBuilder = new StringBuilder();
    localStringBuilder.append(PREFIX);
    localStringBuilder.append("-Response-Source");
    RESPONSE_SOURCE = localStringBuilder.toString();
  }
  
  private OkHeaders() {}
  
  public static void addCookies(Request.Builder paramBuilder, Map paramMap)
  {
    paramMap = paramMap.entrySet().iterator();
    while (paramMap.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)paramMap.next();
      String str = (String)localEntry.getKey();
      if ((("Cookie".equalsIgnoreCase(str)) || ("Cookie2".equalsIgnoreCase(str))) && (!((List)localEntry.getValue()).isEmpty())) {
        paramBuilder.addHeader(str, buildCookieHeader((List)localEntry.getValue()));
      }
    }
  }
  
  private static String buildCookieHeader(List paramList)
  {
    int j = paramList.size();
    int i = 0;
    if (j == 1) {
      return (String)paramList.get(0);
    }
    StringBuilder localStringBuilder = new StringBuilder();
    j = paramList.size();
    while (i < j)
    {
      if (i > 0) {
        localStringBuilder.append("; ");
      }
      localStringBuilder.append((String)paramList.get(i));
      i += 1;
    }
    return localStringBuilder.toString();
  }
  
  public static long contentLength(Headers paramHeaders)
  {
    return stringToLong(paramHeaders.get("Content-Length"));
  }
  
  public static long contentLength(Request paramRequest)
  {
    return contentLength(paramRequest.headers());
  }
  
  public static long contentLength(Response paramResponse)
  {
    return contentLength(paramResponse.headers());
  }
  
  public static boolean hasVaryAll(Headers paramHeaders)
  {
    return varyFields(paramHeaders).contains("*");
  }
  
  public static boolean hasVaryAll(Response paramResponse)
  {
    return hasVaryAll(paramResponse.headers());
  }
  
  static boolean isEndToEnd(String paramString)
  {
    return (!"Connection".equalsIgnoreCase(paramString)) && (!"Keep-Alive".equalsIgnoreCase(paramString)) && (!"Proxy-Authenticate".equalsIgnoreCase(paramString)) && (!"Proxy-Authorization".equalsIgnoreCase(paramString)) && (!"TE".equalsIgnoreCase(paramString)) && (!"Trailers".equalsIgnoreCase(paramString)) && (!"Transfer-Encoding".equalsIgnoreCase(paramString)) && (!"Upgrade".equalsIgnoreCase(paramString));
  }
  
  public static List parseChallenges(Headers paramHeaders, String paramString)
  {
    ArrayList localArrayList = new ArrayList();
    int k = paramHeaders.size();
    int i = 0;
    while (i < k)
    {
      if (paramString.equalsIgnoreCase(paramHeaders.name(i)))
      {
        String str1 = paramHeaders.value(i);
        int j = 0;
        while (j < str1.length())
        {
          int m = HeaderParser.skipUntil(str1, j, " ");
          String str2 = str1.substring(j, m).trim();
          j = HeaderParser.skipWhitespace(str1, m);
          if (!str1.regionMatches(true, j, "realm=\"", 0, 7)) {
            break;
          }
          j += 7;
          m = HeaderParser.skipUntil(str1, j, "\"");
          String str3 = str1.substring(j, m);
          j = HeaderParser.skipWhitespace(str1, HeaderParser.skipUntil(str1, m + 1, ",") + 1);
          localArrayList.add(new Challenge(str2, str3));
        }
      }
      i += 1;
    }
    return localArrayList;
  }
  
  public static Request processAuthHeader(Authenticator paramAuthenticator, Response paramResponse, Proxy paramProxy)
    throws IOException
  {
    if (paramResponse.code() == 407) {
      return paramAuthenticator.authenticateProxy(paramProxy, paramResponse);
    }
    return paramAuthenticator.authenticate(paramProxy, paramResponse);
  }
  
  private static long stringToLong(String paramString)
  {
    if (paramString == null) {
      return -1L;
    }
    try
    {
      long l = Long.parseLong(paramString);
      return l;
    }
    catch (NumberFormatException paramString) {}
    return -1L;
  }
  
  public static Map toMultimap(Headers paramHeaders, String paramString)
  {
    TreeMap localTreeMap = new TreeMap(FIELD_NAME_COMPARATOR);
    int j = paramHeaders.size();
    int i = 0;
    while (i < j)
    {
      String str1 = paramHeaders.name(i);
      String str2 = paramHeaders.value(i);
      ArrayList localArrayList = new ArrayList();
      List localList = (List)localTreeMap.get(str1);
      if (localList != null) {
        localArrayList.addAll(localList);
      }
      localArrayList.add(str2);
      localTreeMap.put(str1, Collections.unmodifiableList(localArrayList));
      i += 1;
    }
    if (paramString != null) {
      localTreeMap.put(null, Collections.unmodifiableList(Collections.singletonList(paramString)));
    }
    return Collections.unmodifiableMap(localTreeMap);
  }
  
  public static Set varyFields(Headers paramHeaders)
  {
    throw new Runtime("d2j fail translate: java.lang.RuntimeException: fail exe a9 = a8\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.exec(BaseAnalyze.java:92)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.exec(BaseAnalyze.java:1)\n\tat com.googlecode.dex2jar.ir.ts.Cfg.dfs(Cfg.java:255)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.analyze0(BaseAnalyze.java:75)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.analyze(BaseAnalyze.java:69)\n\tat com.googlecode.dex2jar.ir.ts.UnSSATransformer.transform(UnSSATransformer.java:274)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:163)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\nCaused by: java.lang.NullPointerException\n");
  }
  
  private static Set varyFields(Response paramResponse)
  {
    return varyFields(paramResponse.headers());
  }
  
  public static Headers varyHeaders(Headers paramHeaders1, Headers paramHeaders2)
  {
    paramHeaders2 = varyFields(paramHeaders2);
    if (paramHeaders2.isEmpty()) {
      return new Headers.Builder().build();
    }
    Headers.Builder localBuilder = new Headers.Builder();
    int i = 0;
    int j = paramHeaders1.size();
    while (i < j)
    {
      String str = paramHeaders1.name(i);
      if (paramHeaders2.contains(str)) {
        localBuilder.add(str, paramHeaders1.value(i));
      }
      i += 1;
    }
    return localBuilder.build();
  }
  
  public static Headers varyHeaders(Response paramResponse)
  {
    return varyHeaders(paramResponse.networkResponse().request().headers(), paramResponse.headers());
  }
  
  public static boolean varyMatches(Response paramResponse, Headers paramHeaders, Request paramRequest)
  {
    paramResponse = varyFields(paramResponse).iterator();
    while (paramResponse.hasNext())
    {
      String str = (String)paramResponse.next();
      if (!Util.equal(paramHeaders.values(str), paramRequest.headers(str))) {
        return false;
      }
    }
    return true;
  }
}
