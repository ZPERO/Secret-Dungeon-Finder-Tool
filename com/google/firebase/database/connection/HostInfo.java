package com.google.firebase.database.connection;

import java.net.URI;

public class HostInfo
{
  private static final String LAST_SESSION_ID_PARAM = "ls";
  private static final String VERSION_PARAM = "v";
  private final String host;
  private final String namespace;
  private final boolean secure;
  
  public HostInfo(String paramString1, String paramString2, boolean paramBoolean)
  {
    host = paramString1;
    namespace = paramString2;
    secure = paramBoolean;
  }
  
  public static URI getConnectionUrl(String paramString1, boolean paramBoolean, String paramString2, String paramString3)
  {
    String str;
    if (paramBoolean) {
      str = "wss";
    } else {
      str = "ws";
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(str);
    localStringBuilder.append("://");
    localStringBuilder.append(paramString1);
    localStringBuilder.append("/.ws?ns=");
    localStringBuilder.append(paramString2);
    localStringBuilder.append("&");
    localStringBuilder.append("v");
    localStringBuilder.append("=");
    localStringBuilder.append("5");
    paramString2 = localStringBuilder.toString();
    paramString1 = paramString2;
    if (paramString3 != null)
    {
      paramString1 = new StringBuilder();
      paramString1.append(paramString2);
      paramString1.append("&ls=");
      paramString1.append(paramString3);
      paramString1 = paramString1.toString();
    }
    return URI.create(paramString1);
  }
  
  public String getHost()
  {
    return host;
  }
  
  public String getNamespace()
  {
    return namespace;
  }
  
  public boolean isSecure()
  {
    return secure;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("http");
    String str;
    if (secure) {
      str = "s";
    } else {
      str = "";
    }
    localStringBuilder.append(str);
    localStringBuilder.append("://");
    localStringBuilder.append(host);
    return localStringBuilder.toString();
  }
}
