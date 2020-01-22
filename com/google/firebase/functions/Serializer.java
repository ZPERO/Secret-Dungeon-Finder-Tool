package com.google.firebase.functions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class Serializer
{
  static final String LONG_TYPE = "type.googleapis.com/google.protobuf.Int64Value";
  static final String UNSIGNED_LONG_TYPE = "type.googleapis.com/google.protobuf.UInt64Value";
  private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
  
  public Serializer()
  {
    dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
  }
  
  public Object decode(Object paramObject)
  {
    if ((paramObject instanceof Number)) {
      return paramObject;
    }
    if ((paramObject instanceof String)) {
      return paramObject;
    }
    if ((paramObject instanceof Boolean)) {
      return paramObject;
    }
    if ((paramObject instanceof JSONObject))
    {
      localObject1 = (JSONObject)paramObject;
      if (((JSONObject)localObject1).has("@type"))
      {
        localObject2 = ((JSONObject)localObject1).optString("@type");
        paramObject = ((JSONObject)localObject1).optString("value");
        if (!((String)localObject2).equals("type.googleapis.com/google.protobuf.Int64Value")) {}
      }
    }
    try
    {
      l = Long.parseLong(paramObject);
      return Long.valueOf(l);
    }
    catch (NumberFormatException localNumberFormatException1)
    {
      long l;
      for (;;) {}
    }
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("Invalid Long format:");
    ((StringBuilder)localObject1).append(paramObject);
    throw new IllegalArgumentException(((StringBuilder)localObject1).toString());
    if (((String)localObject2).equals("type.googleapis.com/google.protobuf.UInt64Value")) {}
    try
    {
      l = Long.parseLong(paramObject);
      return Long.valueOf(l);
    }
    catch (NumberFormatException localNumberFormatException2)
    {
      int i;
      for (;;) {}
    }
    localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("Invalid Long format:");
    ((StringBuilder)localObject1).append(paramObject);
    throw new IllegalArgumentException(((StringBuilder)localObject1).toString());
    paramObject = new HashMap();
    Object localObject2 = ((JSONObject)localObject1).keys();
    while (((Iterator)localObject2).hasNext())
    {
      String str = (String)((Iterator)localObject2).next();
      paramObject.put(str, decode(((JSONObject)localObject1).opt(str)));
    }
    return paramObject;
    if ((paramObject instanceof JSONArray))
    {
      localObject1 = new ArrayList();
      i = 0;
      for (;;)
      {
        localObject2 = (JSONArray)paramObject;
        if (i >= ((JSONArray)localObject2).length()) {
          break;
        }
        ((List)localObject1).add(decode(((JSONArray)localObject2).opt(i)));
        i += 1;
      }
      return localObject1;
    }
    if (paramObject == JSONObject.NULL) {
      return null;
    }
    localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("Object cannot be decoded from JSON: ");
    ((StringBuilder)localObject1).append(paramObject);
    paramObject = new IllegalArgumentException(((StringBuilder)localObject1).toString());
    throw paramObject;
  }
  
  public Object encode(Object paramObject)
  {
    if ((paramObject != null) && (paramObject != JSONObject.NULL))
    {
      if ((paramObject instanceof Long))
      {
        localObject1 = new JSONObject();
        try
        {
          ((JSONObject)localObject1).put("@type", "type.googleapis.com/google.protobuf.Int64Value");
          ((JSONObject)localObject1).put("value", paramObject.toString());
          return localObject1;
        }
        catch (JSONException paramObject)
        {
          throw new RuntimeException("Error encoding Long.", paramObject);
        }
      }
      if ((paramObject instanceof Number)) {
        return paramObject;
      }
      if ((paramObject instanceof String)) {
        return paramObject;
      }
      if ((paramObject instanceof Boolean)) {
        return paramObject;
      }
      boolean bool1 = paramObject instanceof JSONObject;
      if (bool1) {
        return paramObject;
      }
      boolean bool2 = paramObject instanceof JSONArray;
      if (bool2) {
        return paramObject;
      }
      Object localObject2;
      Iterator localIterator;
      Object localObject3;
      if ((paramObject instanceof Map))
      {
        localObject1 = new JSONObject();
        localObject2 = (Map)paramObject;
        localIterator = ((Map)localObject2).keySet().iterator();
        for (;;)
        {
          paramObject = localObject1;
          if (!localIterator.hasNext()) {
            return paramObject;
          }
          localObject3 = localIterator.next();
          if ((localObject3 instanceof String))
          {
            paramObject = (String)localObject3;
            localObject3 = encode(((Map)localObject2).get(localObject3));
            try
            {
              ((JSONObject)localObject1).put(paramObject, localObject3);
            }
            catch (JSONException paramObject)
            {
              throw new RuntimeException(paramObject);
            }
          }
        }
        throw new IllegalArgumentException("Object keys must be strings.");
      }
      if ((paramObject instanceof List))
      {
        localObject1 = new JSONArray();
        paramObject = ((List)paramObject).iterator();
        while (paramObject.hasNext()) {
          ((JSONArray)localObject1).put(encode(paramObject.next()));
        }
        return localObject1;
      }
      if (bool1)
      {
        localObject1 = new JSONObject();
        localObject2 = (JSONObject)paramObject;
        localIterator = ((JSONObject)localObject2).keys();
        for (;;)
        {
          paramObject = localObject1;
          if (!localIterator.hasNext()) {
            return paramObject;
          }
          paramObject = (String)localIterator.next();
          if ((paramObject instanceof String))
          {
            localObject3 = encode(((JSONObject)localObject2).opt(paramObject));
            try
            {
              ((JSONObject)localObject1).put(paramObject, localObject3);
            }
            catch (JSONException paramObject)
            {
              throw new RuntimeException(paramObject);
            }
          }
        }
        throw new IllegalArgumentException("Object keys must be strings.");
      }
      if (bool2)
      {
        localObject1 = new JSONArray();
        paramObject = (JSONArray)paramObject;
        int i = 0;
        while (i < paramObject.length())
        {
          ((JSONArray)localObject1).put(encode(paramObject.opt(i)));
          i += 1;
        }
        return localObject1;
      }
      Object localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("Object cannot be encoded in JSON: ");
      ((StringBuilder)localObject1).append(paramObject);
      throw new IllegalArgumentException(((StringBuilder)localObject1).toString());
    }
    return JSONObject.NULL;
    return paramObject;
  }
}
