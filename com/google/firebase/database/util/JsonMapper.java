package com.google.firebase.database.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.json.JSONTokener;

public class JsonMapper
{
  public JsonMapper() {}
  
  public static Map parseJson(String paramString)
    throws IOException
  {
    try
    {
      paramString = unwrapJsonObject(new JSONObject(paramString));
      return paramString;
    }
    catch (JSONException paramString)
    {
      throw new IOException(paramString);
    }
  }
  
  public static Object parseJsonValue(String paramString)
    throws IOException
  {
    try
    {
      paramString = unwrapJson(new JSONTokener(paramString).nextValue());
      return paramString;
    }
    catch (JSONException paramString)
    {
      throw new IOException(paramString);
    }
  }
  
  public static String serializeJson(Map paramMap)
    throws IOException
  {
    return serializeJsonValue(paramMap);
  }
  
  public static String serializeJsonValue(Object paramObject)
    throws IOException
  {
    if (paramObject == null) {
      return "null";
    }
    if ((paramObject instanceof String)) {
      return JSONObject.quote((String)paramObject);
    }
    if ((paramObject instanceof Number))
    {
      paramObject = (Number)paramObject;
      try
      {
        paramObject = JSONObject.numberToString(paramObject);
        return paramObject;
      }
      catch (JSONException paramObject)
      {
        throw new IOException("Could not serialize number", paramObject);
      }
    }
    if ((paramObject instanceof Boolean))
    {
      if (((Boolean)paramObject).booleanValue()) {
        return "true";
      }
      return "false";
    }
    try
    {
      JSONStringer localJSONStringer = new JSONStringer();
      serializeJsonValue(paramObject, localJSONStringer);
      paramObject = localJSONStringer.toString();
      return paramObject;
    }
    catch (JSONException paramObject)
    {
      throw new IOException("Failed to serialize JSON", paramObject);
    }
  }
  
  private static void serializeJsonValue(Object paramObject, JSONStringer paramJSONStringer)
    throws IOException, JSONException
  {
    if ((paramObject instanceof Map))
    {
      paramJSONStringer.object();
      paramObject = ((Map)paramObject).entrySet().iterator();
      while (paramObject.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)paramObject.next();
        paramJSONStringer.key((String)localEntry.getKey());
        serializeJsonValue(localEntry.getValue(), paramJSONStringer);
      }
      paramJSONStringer.endObject();
      return;
    }
    if ((paramObject instanceof Collection))
    {
      paramObject = (Collection)paramObject;
      paramJSONStringer.array();
      paramObject = paramObject.iterator();
      while (paramObject.hasNext()) {
        serializeJsonValue(paramObject.next(), paramJSONStringer);
      }
      paramJSONStringer.endArray();
      return;
    }
    paramJSONStringer.value(paramObject);
  }
  
  private static Object unwrapJson(Object paramObject)
    throws JSONException
  {
    if ((paramObject instanceof JSONObject)) {
      return unwrapJsonObject((JSONObject)paramObject);
    }
    if ((paramObject instanceof JSONArray)) {
      return unwrapJsonArray((JSONArray)paramObject);
    }
    if (paramObject.equals(JSONObject.NULL)) {
      return null;
    }
    return paramObject;
  }
  
  private static List unwrapJsonArray(JSONArray paramJSONArray)
    throws JSONException
  {
    ArrayList localArrayList = new ArrayList(paramJSONArray.length());
    int i = 0;
    while (i < paramJSONArray.length())
    {
      localArrayList.add(unwrapJson(paramJSONArray.get(i)));
      i += 1;
    }
    return localArrayList;
  }
  
  private static Map unwrapJsonObject(JSONObject paramJSONObject)
    throws JSONException
  {
    HashMap localHashMap = new HashMap(paramJSONObject.length());
    Iterator localIterator = paramJSONObject.keys();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      localHashMap.put(str, unwrapJson(paramJSONObject.get(str)));
    }
    return localHashMap;
  }
}
