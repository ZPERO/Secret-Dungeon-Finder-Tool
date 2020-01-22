package com.google.firebase.database.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GAuthToken
{
  private static final String AUTH_KEY = "auth";
  private static final String TOKEN_KEY = "token";
  private static final String TOKEN_PREFIX = "gauth|";
  private final Map<String, Object> auth;
  private final String token;
  
  public GAuthToken(String paramString, Map paramMap)
  {
    token = paramString;
    auth = paramMap;
  }
  
  public static GAuthToken tryParseFromString(String paramString)
  {
    if (!paramString.startsWith("gauth|")) {
      return null;
    }
    paramString = paramString.substring(6);
    try
    {
      Object localObject = JsonMapper.parseJson(paramString);
      paramString = ((Map)localObject).get("token");
      paramString = (String)paramString;
      localObject = ((Map)localObject).get("auth");
      localObject = (Map)localObject;
      paramString = new GAuthToken(paramString, (Map)localObject);
      return paramString;
    }
    catch (IOException paramString)
    {
      throw new RuntimeException("Failed to parse gauth token", paramString);
    }
  }
  
  public Map getAuth()
  {
    return auth;
  }
  
  public String getToken()
  {
    return token;
  }
  
  public String serializeToString()
  {
    Object localObject = new HashMap();
    ((Map)localObject).put("token", token);
    ((Map)localObject).put("auth", auth);
    try
    {
      localObject = JsonMapper.serializeJson((Map)localObject);
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("gauth|");
      localStringBuilder.append((String)localObject);
      localObject = localStringBuilder.toString();
      return localObject;
    }
    catch (IOException localIOException)
    {
      throw new RuntimeException("Failed to serialize gauth token", localIOException);
    }
  }
}
