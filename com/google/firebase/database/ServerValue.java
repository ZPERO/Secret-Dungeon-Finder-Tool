package com.google.firebase.database;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ServerValue
{
  public static final Map<String, String> TIMESTAMP = createServerValuePlaceholder("timestamp");
  
  public ServerValue() {}
  
  private static Map createServerValuePlaceholder(String paramString)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put(".sv", paramString);
    return Collections.unmodifiableMap(localHashMap);
  }
}
