package com.google.firebase.functions;

class HttpsCallableContext
{
  private final String authToken;
  private final String instanceIdToken;
  
  HttpsCallableContext(String paramString1, String paramString2)
  {
    authToken = paramString1;
    instanceIdToken = paramString2;
  }
  
  public String getAuthToken()
  {
    return authToken;
  }
  
  public String getInstanceIdToken()
  {
    return instanceIdToken;
  }
}
