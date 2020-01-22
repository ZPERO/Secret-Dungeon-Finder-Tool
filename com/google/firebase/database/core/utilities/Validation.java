package com.google.firebase.database.core.utilities;

import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.core.Path;
import com.google.firebase.database.core.ValidationPath;
import com.google.firebase.database.snapshot.ChildKey;
import com.google.firebase.database.snapshot.NodeUtilities;
import com.google.firebase.database.snapshot.PriorityUtilities;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation
{
  private static final Pattern INVALID_KEY_REGEX = Pattern.compile("[\\[\\]\\.#\\$\\/\\u0000-\\u001F\\u007F]");
  private static final Pattern INVALID_PATH_REGEX = Pattern.compile("[\\[\\]\\.#$]");
  
  public Validation() {}
  
  private static boolean isValidKey(String paramString)
  {
    return (paramString.equals(".info")) || (!INVALID_KEY_REGEX.matcher(paramString).find());
  }
  
  private static boolean isValidPathString(String paramString)
  {
    return INVALID_PATH_REGEX.matcher(paramString).find() ^ true;
  }
  
  private static boolean isWritableKey(String paramString)
  {
    return (paramString != null) && (paramString.length() > 0) && ((paramString.equals(".value")) || (paramString.equals(".priority")) || ((!paramString.startsWith(".")) && (!INVALID_KEY_REGEX.matcher(paramString).find())));
  }
  
  private static boolean isWritablePath(Path paramPath)
  {
    paramPath = paramPath.getFront();
    return (paramPath == null) || (!paramPath.asString().startsWith("."));
  }
  
  public static Map parseAndValidateUpdate(Path paramPath, Map paramMap)
    throws DatabaseException
  {
    Object localObject1 = new TreeMap();
    Iterator localIterator = paramMap.entrySet().iterator();
    while (localIterator.hasNext())
    {
      paramMap = (Map.Entry)localIterator.next();
      localObject2 = new Path((String)paramMap.getKey());
      Object localObject3 = paramMap.getValue();
      ValidationPath.validateWithObject(paramPath.child((Path)localObject2), localObject3);
      if (!((Path)localObject2).isEmpty()) {
        paramMap = ((Path)localObject2).getBack().asString();
      } else {
        paramMap = "";
      }
      if ((!paramMap.equals(".sv")) && (!paramMap.equals(".value")))
      {
        if (paramMap.equals(".priority")) {
          paramMap = PriorityUtilities.parsePriority((Path)localObject2, localObject3);
        } else {
          paramMap = NodeUtilities.NodeFromJSON(localObject3);
        }
        validateWritableObject(localObject3);
        ((SortedMap)localObject1).put(localObject2, paramMap);
      }
      else
      {
        paramPath = new StringBuilder();
        paramPath.append("Path '");
        paramPath.append(localObject2);
        paramPath.append("' contains disallowed child name: ");
        paramPath.append(paramMap);
        throw new DatabaseException(paramPath.toString());
      }
    }
    paramPath = null;
    Object localObject2 = ((SortedMap)localObject1).keySet().iterator();
    while (((Iterator)localObject2).hasNext())
    {
      paramMap = (Path)((Iterator)localObject2).next();
      boolean bool;
      if ((paramPath != null) && (paramPath.compareTo(paramMap) >= 0)) {
        bool = false;
      } else {
        bool = true;
      }
      Utilities.hardAssert(bool);
      if ((paramPath != null) && (paramPath.contains(paramMap)))
      {
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("Path '");
        ((StringBuilder)localObject1).append(paramPath);
        ((StringBuilder)localObject1).append("' is an ancestor of '");
        ((StringBuilder)localObject1).append(paramMap);
        ((StringBuilder)localObject1).append("' in an update.");
        throw new DatabaseException(((StringBuilder)localObject1).toString());
      }
      paramPath = paramMap;
    }
    return localObject1;
  }
  
  private static void validateDoubleValue(double paramDouble)
  {
    if ((!Double.isInfinite(paramDouble)) && (!Double.isNaN(paramDouble))) {
      return;
    }
    throw new DatabaseException("Invalid value: Value cannot be NaN, Inf or -Inf.");
  }
  
  public static void validateNullableKey(String paramString)
    throws DatabaseException
  {
    if (paramString != null)
    {
      if (isValidKey(paramString)) {
        return;
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Invalid key: ");
      localStringBuilder.append(paramString);
      localStringBuilder.append(". Keys must not contain '/', '.', '#', '$', '[', or ']'");
      throw new DatabaseException(localStringBuilder.toString());
    }
  }
  
  public static void validatePathString(String paramString)
    throws DatabaseException
  {
    if (isValidPathString(paramString)) {
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Invalid Firebase Database path: ");
    localStringBuilder.append(paramString);
    localStringBuilder.append(". Firebase Database paths must not contain '.', '#', '$', '[', or ']'");
    throw new DatabaseException(localStringBuilder.toString());
  }
  
  public static void validateRootPathString(String paramString)
    throws DatabaseException
  {
    if (paramString.startsWith(".info"))
    {
      validatePathString(paramString.substring(5));
      return;
    }
    if (paramString.startsWith("/.info"))
    {
      validatePathString(paramString.substring(6));
      return;
    }
    validatePathString(paramString);
  }
  
  public static void validateWritableKey(String paramString)
    throws DatabaseException
  {
    if (isWritableKey(paramString)) {
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Invalid key: ");
    localStringBuilder.append(paramString);
    localStringBuilder.append(". Keys must not contain '/', '.', '#', '$', '[', or ']'");
    throw new DatabaseException(localStringBuilder.toString());
  }
  
  public static void validateWritableObject(Object paramObject)
  {
    if ((paramObject instanceof Map))
    {
      paramObject = (Map)paramObject;
      if (paramObject.containsKey(".sv")) {
        return;
      }
      paramObject = paramObject.entrySet().iterator();
      while (paramObject.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)paramObject.next();
        validateWritableKey((String)localEntry.getKey());
        validateWritableObject(localEntry.getValue());
      }
    }
    if ((paramObject instanceof List))
    {
      paramObject = ((List)paramObject).iterator();
      while (paramObject.hasNext()) {
        validateWritableObject(paramObject.next());
      }
    }
    if (((paramObject instanceof Double)) || ((paramObject instanceof Float))) {
      validateDoubleValue(((Double)paramObject).doubleValue());
    }
  }
  
  public static void validateWritablePath(Path paramPath)
    throws DatabaseException
  {
    if (isWritablePath(paramPath)) {
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Invalid write location: ");
    localStringBuilder.append(paramPath.toString());
    throw new DatabaseException(localStringBuilder.toString());
  }
}
