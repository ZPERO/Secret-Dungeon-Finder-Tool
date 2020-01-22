package com.google.firebase.functions;

import com.google.android.android.tasks.Task;

public class HttpsCallableReference
{
  private final FirebaseFunctions functionsClient;
  private final String name;
  
  HttpsCallableReference(FirebaseFunctions paramFirebaseFunctions, String paramString)
  {
    functionsClient = paramFirebaseFunctions;
    name = paramString;
  }
  
  public Task call()
  {
    return functionsClient.call(name, null);
  }
  
  public Task call(Object paramObject)
  {
    return functionsClient.call(name, paramObject);
  }
}
