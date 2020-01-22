package com.google.firebase.functions;

import android.content.Context;
import java.util.HashMap;
import java.util.Map;

class FunctionsMultiResourceComponent
{
  private final Context applicationContext;
  private final ContextProvider contextProvider;
  private final Map<String, FirebaseFunctions> instances = new HashMap();
  private final String projectId;
  
  FunctionsMultiResourceComponent(Context paramContext, ContextProvider paramContextProvider, String paramString)
  {
    applicationContext = paramContext;
    contextProvider = paramContextProvider;
    projectId = paramString;
  }
  
  FirebaseFunctions getInstance(String paramString)
  {
    try
    {
      FirebaseFunctions localFirebaseFunctions2 = (FirebaseFunctions)instances.get(paramString);
      FirebaseFunctions localFirebaseFunctions1 = localFirebaseFunctions2;
      if (localFirebaseFunctions2 == null)
      {
        localFirebaseFunctions1 = new FirebaseFunctions(applicationContext, projectId, paramString, contextProvider);
        instances.put(paramString, localFirebaseFunctions1);
      }
      return localFirebaseFunctions1;
    }
    catch (Throwable paramString)
    {
      throw paramString;
    }
  }
}
