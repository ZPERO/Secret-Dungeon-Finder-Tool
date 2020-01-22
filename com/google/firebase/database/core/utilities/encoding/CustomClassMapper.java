package com.google.firebase.database.core.utilities.encoding;

import android.util.Log;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;
import com.google.firebase.database.ThrowOnExtraProperties;
import com.google.firebase.database.core.utilities.Utilities;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class CustomClassMapper
{
  private static final String LOG_TAG = "ClassMapper";
  private static final ConcurrentMap<Class<?>, BeanMapper<?>> mappers = new ConcurrentHashMap();
  
  public CustomClassMapper() {}
  
  private static Object convertBean(Object paramObject, Class paramClass)
  {
    Object localObject = loadOrCreateBeanMapperForClass(paramClass);
    if ((paramObject instanceof Map)) {
      return ((BeanMapper)localObject).deserialize(expectMap(paramObject));
    }
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Can't convert object of type ");
    ((StringBuilder)localObject).append(paramObject.getClass().getName());
    ((StringBuilder)localObject).append(" to type ");
    ((StringBuilder)localObject).append(paramClass.getName());
    throw new DatabaseException(((StringBuilder)localObject).toString());
  }
  
  private static Boolean convertBoolean(Object paramObject)
  {
    if ((paramObject instanceof Boolean)) {
      return (Boolean)paramObject;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Failed to convert value of type ");
    localStringBuilder.append(paramObject.getClass().getName());
    localStringBuilder.append(" to boolean");
    throw new DatabaseException(localStringBuilder.toString());
  }
  
  private static Double convertDouble(Object paramObject)
  {
    if ((paramObject instanceof Integer)) {
      return Double.valueOf(((Integer)paramObject).doubleValue());
    }
    if ((paramObject instanceof Long))
    {
      localObject = (Long)paramObject;
      Double localDouble = Double.valueOf(((Long)localObject).doubleValue());
      if (localDouble.longValue() == ((Long)localObject).longValue()) {
        return localDouble;
      }
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("Loss of precision while converting number to double: ");
      ((StringBuilder)localObject).append(paramObject);
      ((StringBuilder)localObject).append(". Did you mean to use a 64-bit long instead?");
      throw new DatabaseException(((StringBuilder)localObject).toString());
    }
    if ((paramObject instanceof Double)) {
      return (Double)paramObject;
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Failed to convert a value of type ");
    ((StringBuilder)localObject).append(paramObject.getClass().getName());
    ((StringBuilder)localObject).append(" to double");
    throw new DatabaseException(((StringBuilder)localObject).toString());
  }
  
  private static Integer convertInteger(Object paramObject)
  {
    if ((paramObject instanceof Integer)) {
      return (Integer)paramObject;
    }
    boolean bool = paramObject instanceof Long;
    Object localObject = paramObject;
    if ((!bool) && (!(localObject instanceof Double)))
    {
      paramObject = new StringBuilder();
      paramObject.append("Failed to convert a value of type ");
      paramObject.append(localObject.getClass().getName());
      paramObject.append(" to int");
      throw new DatabaseException(paramObject.toString());
    }
    paramObject = (Number)paramObject;
    double d = paramObject.doubleValue();
    if ((d >= -2.147483648E9D) && (d <= 2.147483647E9D)) {
      return Integer.valueOf(paramObject.intValue());
    }
    paramObject = new StringBuilder();
    paramObject.append("Numeric value out of 32-bit integer range: ");
    paramObject.append(d);
    paramObject.append(". Did you mean to use a long or double instead of an int?");
    throw new DatabaseException(paramObject.toString());
  }
  
  private static Long convertLong(Object paramObject)
  {
    if ((paramObject instanceof Integer)) {
      return Long.valueOf(((Integer)paramObject).longValue());
    }
    if ((paramObject instanceof Long)) {
      return (Long)paramObject;
    }
    if ((paramObject instanceof Double))
    {
      paramObject = (Double)paramObject;
      if ((paramObject.doubleValue() >= -9.223372036854776E18D) && (paramObject.doubleValue() <= 9.223372036854776E18D)) {
        return Long.valueOf(paramObject.longValue());
      }
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("Numeric value out of 64-bit long range: ");
      localStringBuilder.append(paramObject);
      localStringBuilder.append(". Did you mean to use a double instead of a long?");
      throw new DatabaseException(localStringBuilder.toString());
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Failed to convert a value of type ");
    localStringBuilder.append(paramObject.getClass().getName());
    localStringBuilder.append(" to long");
    throw new DatabaseException(localStringBuilder.toString());
  }
  
  private static String convertString(Object paramObject)
  {
    if ((paramObject instanceof String)) {
      return (String)paramObject;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Failed to convert value of type ");
    localStringBuilder.append(paramObject.getClass().getName());
    localStringBuilder.append(" to String");
    throw new DatabaseException(localStringBuilder.toString());
  }
  
  public static Object convertToCustomClass(Object paramObject, GenericTypeIndicator paramGenericTypeIndicator)
  {
    paramGenericTypeIndicator = paramGenericTypeIndicator.getClass().getGenericSuperclass();
    if ((paramGenericTypeIndicator instanceof ParameterizedType))
    {
      ParameterizedType localParameterizedType = (ParameterizedType)paramGenericTypeIndicator;
      if (localParameterizedType.getRawType().equals(GenericTypeIndicator.class)) {
        return deserializeToType(paramObject, localParameterizedType.getActualTypeArguments()[0]);
      }
      paramObject = new StringBuilder();
      paramObject.append("Not a direct subclass of GenericTypeIndicator: ");
      paramObject.append(paramGenericTypeIndicator);
      throw new DatabaseException(paramObject.toString());
    }
    paramObject = new StringBuilder();
    paramObject.append("Not a direct subclass of GenericTypeIndicator: ");
    paramObject.append(paramGenericTypeIndicator);
    throw new DatabaseException(paramObject.toString());
  }
  
  public static Object convertToCustomClass(Object paramObject, Class paramClass)
  {
    return deserializeToClass(paramObject, paramClass);
  }
  
  public static Object convertToPlainJavaTypes(Object paramObject)
  {
    return serialize(paramObject);
  }
  
  public static Map convertToPlainJavaTypes(Map paramMap)
  {
    paramMap = serialize(paramMap);
    Utilities.hardAssert(paramMap instanceof Map);
    return (Map)paramMap;
  }
  
  private static Object deserializeToClass(Object paramObject, Class paramClass)
  {
    if (paramObject == null) {
      return null;
    }
    if ((!paramClass.isPrimitive()) && (!Number.class.isAssignableFrom(paramClass)) && (!Boolean.class.isAssignableFrom(paramClass)) && (!Character.class.isAssignableFrom(paramClass)))
    {
      if (String.class.isAssignableFrom(paramClass)) {
        return convertString(paramObject);
      }
      if (!paramClass.isArray())
      {
        if (paramClass.getTypeParameters().length <= 0)
        {
          if (paramClass.equals(Object.class)) {
            return paramObject;
          }
          if (paramClass.isEnum()) {
            return deserializeToEnum(paramObject, paramClass);
          }
          return convertBean(paramObject, paramClass);
        }
        paramObject = new StringBuilder();
        paramObject.append("Class ");
        paramObject.append(paramClass.getName());
        paramObject.append(" has generic type parameters, please use GenericTypeIndicator instead");
        throw new DatabaseException(paramObject.toString());
      }
      throw new DatabaseException("Converting to Arrays is not supported, please use Listsinstead");
    }
    return deserializeToPrimitive(paramObject, paramClass);
  }
  
  private static Object deserializeToEnum(Object paramObject, Class paramClass)
  {
    if ((paramObject instanceof String)) {
      paramObject = (String)paramObject;
    }
    try
    {
      localObject = Enum.valueOf(paramClass, paramObject);
      return localObject;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      Object localObject;
      for (;;) {}
    }
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Could not find enum value of ");
    ((StringBuilder)localObject).append(paramClass.getName());
    ((StringBuilder)localObject).append(" for value \"");
    ((StringBuilder)localObject).append(paramObject);
    ((StringBuilder)localObject).append("\"");
    throw new DatabaseException(((StringBuilder)localObject).toString());
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Expected a String while deserializing to enum ");
    ((StringBuilder)localObject).append(paramClass);
    ((StringBuilder)localObject).append(" but got a ");
    ((StringBuilder)localObject).append(paramObject.getClass());
    throw new DatabaseException(((StringBuilder)localObject).toString());
  }
  
  private static Object deserializeToParameterizedType(Object paramObject, ParameterizedType paramParameterizedType)
  {
    Object localObject1 = (Class)paramParameterizedType.getRawType();
    boolean bool = List.class.isAssignableFrom((Class)localObject1);
    int i = 0;
    if (bool)
    {
      paramParameterizedType = paramParameterizedType.getActualTypeArguments()[0];
      if ((paramObject instanceof List))
      {
        localObject1 = (List)paramObject;
        paramObject = new ArrayList(((List)localObject1).size());
        localObject1 = ((List)localObject1).iterator();
        while (((Iterator)localObject1).hasNext()) {
          paramObject.add(deserializeToType(((Iterator)localObject1).next(), paramParameterizedType));
        }
        return paramObject;
      }
      paramParameterizedType = new StringBuilder();
      paramParameterizedType.append("Expected a List while deserializing, but got a ");
      paramParameterizedType.append(paramObject.getClass());
      throw new DatabaseException(paramParameterizedType.toString());
    }
    Object localObject2;
    if (Map.class.isAssignableFrom((Class)localObject1))
    {
      localObject1 = paramParameterizedType.getActualTypeArguments()[0];
      paramParameterizedType = paramParameterizedType.getActualTypeArguments()[1];
      if (localObject1.equals(String.class))
      {
        localObject1 = expectMap(paramObject);
        paramObject = new HashMap();
        localObject1 = ((Map)localObject1).entrySet().iterator();
        while (((Iterator)localObject1).hasNext())
        {
          localObject2 = (Map.Entry)((Iterator)localObject1).next();
          paramObject.put((String)((Map.Entry)localObject2).getKey(), deserializeToType(((Map.Entry)localObject2).getValue(), paramParameterizedType));
        }
        return paramObject;
      }
      paramObject = new StringBuilder();
      paramObject.append("Only Maps with string keys are supported, but found Map with key type ");
      paramObject.append(localObject1);
      throw new DatabaseException(paramObject.toString());
    }
    if (!Collection.class.isAssignableFrom((Class)localObject1))
    {
      paramObject = expectMap(paramObject);
      localObject1 = loadOrCreateBeanMapperForClass((Class)localObject1);
      localObject2 = new HashMap();
      TypeVariable[] arrayOfTypeVariable = clazz.getTypeParameters();
      paramParameterizedType = paramParameterizedType.getActualTypeArguments();
      if (paramParameterizedType.length == arrayOfTypeVariable.length)
      {
        while (i < arrayOfTypeVariable.length)
        {
          ((HashMap)localObject2).put(arrayOfTypeVariable[i], paramParameterizedType[i]);
          i += 1;
        }
        return ((BeanMapper)localObject1).deserialize(paramObject, (Map)localObject2);
      }
      throw new IllegalStateException("Mismatched lengths for type variables and actual types");
    }
    paramObject = new DatabaseException("Collections are not supported, please use Lists instead");
    throw paramObject;
  }
  
  private static Object deserializeToPrimitive(Object paramObject, Class paramClass)
  {
    if ((!Integer.class.isAssignableFrom(paramClass)) && (!Integer.TYPE.isAssignableFrom(paramClass)))
    {
      if ((!Boolean.class.isAssignableFrom(paramClass)) && (!Boolean.TYPE.isAssignableFrom(paramClass)))
      {
        if ((!Double.class.isAssignableFrom(paramClass)) && (!Double.TYPE.isAssignableFrom(paramClass)))
        {
          if ((!Long.class.isAssignableFrom(paramClass)) && (!Long.TYPE.isAssignableFrom(paramClass)))
          {
            if ((!Float.class.isAssignableFrom(paramClass)) && (!Float.TYPE.isAssignableFrom(paramClass)))
            {
              if ((!Short.class.isAssignableFrom(paramClass)) && (!Short.TYPE.isAssignableFrom(paramClass)))
              {
                if ((!Byte.class.isAssignableFrom(paramClass)) && (!Byte.TYPE.isAssignableFrom(paramClass)))
                {
                  if ((!Character.class.isAssignableFrom(paramClass)) && (!Character.TYPE.isAssignableFrom(paramClass)))
                  {
                    paramObject = new StringBuilder();
                    paramObject.append("Unknown primitive type: ");
                    paramObject.append(paramClass);
                    throw new IllegalArgumentException(paramObject.toString());
                  }
                  throw new DatabaseException("Deserializing to char is not supported");
                }
                throw new DatabaseException("Deserializing to bytes is not supported");
              }
              throw new DatabaseException("Deserializing to shorts is not supported");
            }
            return Float.valueOf(convertDouble(paramObject).floatValue());
          }
          return convertLong(paramObject);
        }
        return convertDouble(paramObject);
      }
      return convertBoolean(paramObject);
    }
    return convertInteger(paramObject);
  }
  
  private static Object deserializeToType(Object paramObject, Type paramType)
  {
    if (paramObject == null) {
      return null;
    }
    if ((paramType instanceof ParameterizedType)) {
      return deserializeToParameterizedType(paramObject, (ParameterizedType)paramType);
    }
    if ((paramType instanceof Class)) {
      return deserializeToClass(paramObject, (Class)paramType);
    }
    if (!(paramType instanceof WildcardType))
    {
      if ((paramType instanceof GenericArrayType)) {
        throw new DatabaseException("Generic Arrays are not supported, please use Lists instead");
      }
      paramObject = new StringBuilder();
      paramObject.append("Unknown type encountered: ");
      paramObject.append(paramType);
      throw new IllegalStateException(paramObject.toString());
    }
    throw new DatabaseException("Generic wildcard types are not supported");
  }
  
  private static Map expectMap(Object paramObject)
  {
    if ((paramObject instanceof Map)) {
      return (Map)paramObject;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Expected a Map while deserializing, but got a ");
    localStringBuilder.append(paramObject.getClass());
    throw new DatabaseException(localStringBuilder.toString());
  }
  
  private static BeanMapper loadOrCreateBeanMapperForClass(Class paramClass)
  {
    BeanMapper localBeanMapper2 = (BeanMapper)mappers.get(paramClass);
    BeanMapper localBeanMapper1 = localBeanMapper2;
    if (localBeanMapper2 == null)
    {
      localBeanMapper1 = new BeanMapper(paramClass);
      mappers.put(paramClass, localBeanMapper1);
    }
    return localBeanMapper1;
  }
  
  private static Object serialize(Object paramObject)
  {
    if (paramObject == null) {
      return null;
    }
    if ((paramObject instanceof Number))
    {
      if ((!(paramObject instanceof Float)) && (!(paramObject instanceof Double)))
      {
        if (!(paramObject instanceof Short))
        {
          if (!(paramObject instanceof Byte)) {
            return paramObject;
          }
          throw new DatabaseException("Bytes are not supported, please use int or long");
        }
        throw new DatabaseException("Shorts are not supported, please use int or long");
      }
      paramObject = (Number)paramObject;
      double d = paramObject.doubleValue();
      if ((d <= 9.223372036854776E18D) && (d >= -9.223372036854776E18D) && (Math.floor(d) == d)) {
        return Long.valueOf(paramObject.longValue());
      }
      return Double.valueOf(d);
    }
    if ((paramObject instanceof String)) {
      return paramObject;
    }
    if ((paramObject instanceof Boolean)) {
      return paramObject;
    }
    Object localObject1;
    if (!(paramObject instanceof Character))
    {
      if ((paramObject instanceof Map))
      {
        localObject1 = new HashMap();
        paramObject = ((Map)paramObject).entrySet().iterator();
        for (;;)
        {
          if (!paramObject.hasNext()) {
            return localObject1;
          }
          Map.Entry localEntry = (Map.Entry)paramObject.next();
          Object localObject2 = localEntry.getKey();
          if (!(localObject2 instanceof String)) {
            break;
          }
          ((Map)localObject1).put((String)localObject2, serialize(localEntry.getValue()));
        }
        throw new DatabaseException("Maps with non-string keys are not supported");
      }
      if ((paramObject instanceof Collection))
      {
        if ((paramObject instanceof List))
        {
          localObject1 = (List)paramObject;
          paramObject = new ArrayList(((List)localObject1).size());
          localObject1 = ((List)localObject1).iterator();
          while (((Iterator)localObject1).hasNext()) {
            paramObject.add(serialize(((Iterator)localObject1).next()));
          }
          return paramObject;
        }
        throw new DatabaseException("Serializing Collections is not supported, please use Lists instead");
      }
      if (!paramObject.getClass().isArray())
      {
        if ((paramObject instanceof Enum)) {
          return ((Enum)paramObject).name();
        }
        return loadOrCreateBeanMapperForClass(paramObject.getClass()).serialize(paramObject);
      }
      throw new DatabaseException("Serializing Arrays is not supported, please use Lists instead");
    }
    paramObject = new DatabaseException("Characters are not supported, please use Strings");
    throw paramObject;
    return localObject1;
  }
  
  private static class BeanMapper<T>
  {
    private final Class<T> clazz;
    private final Constructor<T> constructor;
    private final Map<String, Field> fields;
    private final Map<String, Method> getters;
    private final Map<String, String> properties;
    private final Map<String, Method> setters;
    private final boolean throwOnUnknownProperties;
    private final boolean warnOnUnknownProperties;
    
    public BeanMapper(Class paramClass)
    {
      clazz = paramClass;
      throwOnUnknownProperties = paramClass.isAnnotationPresent(ThrowOnExtraProperties.class);
      warnOnUnknownProperties = (paramClass.isAnnotationPresent(IgnoreExtraProperties.class) ^ true);
      properties = new HashMap();
      setters = new HashMap();
      getters = new HashMap();
      fields = new HashMap();
      try
      {
        localObject2 = paramClass.getDeclaredConstructor(new Class[0]);
        localObject1 = localObject2;
        ((Constructor)localObject2).setAccessible(true);
      }
      catch (NoSuchMethodException localNoSuchMethodException)
      {
        Object localObject2;
        Object localObject1;
        int j;
        int i;
        Object localObject3;
        for (;;) {}
      }
      localObject1 = null;
      constructor = ((Constructor)localObject1);
      localObject2 = paramClass.getMethods();
      j = localObject2.length;
      i = 0;
      while (i < j)
      {
        localObject1 = localObject2[i];
        if (shouldIncludeGetter((Method)localObject1))
        {
          localObject3 = propertyName((Method)localObject1);
          addProperty((String)localObject3);
          ((Method)localObject1).setAccessible(true);
          if (!getters.containsKey(localObject3))
          {
            getters.put(localObject3, localObject1);
          }
          else
          {
            paramClass = new StringBuilder();
            paramClass.append("Found conflicting getters for name: ");
            paramClass.append(((Method)localObject1).getName());
            throw new DatabaseException(paramClass.toString());
          }
        }
        i += 1;
      }
      localObject1 = paramClass.getFields();
      j = localObject1.length;
      i = 0;
      while (i < j)
      {
        localObject2 = localObject1[i];
        if (shouldIncludeField((Field)localObject2)) {
          addProperty(propertyName((Field)localObject2));
        }
        i += 1;
      }
      localObject1 = paramClass;
      do
      {
        localObject3 = ((Class)localObject1).getDeclaredMethods();
        j = localObject3.length;
        i = 0;
        String str;
        while (i < j)
        {
          localObject2 = localObject3[i];
          if (shouldIncludeSetter((Method)localObject2))
          {
            str = propertyName((Method)localObject2);
            Object localObject4 = (String)properties.get(str.toLowerCase());
            if (localObject4 != null) {
              if (((String)localObject4).equals(str))
              {
                localObject4 = (Method)setters.get(str);
                if (localObject4 == null)
                {
                  ((Method)localObject2).setAccessible(true);
                  setters.put(str, localObject2);
                }
                else if (!isSetterOverride((Method)localObject2, (Method)localObject4))
                {
                  paramClass = new StringBuilder();
                  paramClass.append("Found a conflicting setters with name: ");
                  paramClass.append(((Method)localObject2).getName());
                  paramClass.append(" (conflicts with ");
                  paramClass.append(((Method)localObject4).getName());
                  paramClass.append(" defined on ");
                  paramClass.append(((Method)localObject4).getDeclaringClass().getName());
                  paramClass.append(")");
                  throw new DatabaseException(paramClass.toString());
                }
              }
              else
              {
                paramClass = new StringBuilder();
                paramClass.append("Found setter with invalid case-sensitive name: ");
                paramClass.append(((Method)localObject2).getName());
                throw new DatabaseException(paramClass.toString());
              }
            }
          }
          i += 1;
        }
        localObject2 = ((Class)localObject1).getDeclaredFields();
        j = localObject2.length;
        i = 0;
        while (i < j)
        {
          localObject3 = localObject2[i];
          str = propertyName((Field)localObject3);
          if ((properties.containsKey(str.toLowerCase())) && (!fields.containsKey(str)))
          {
            ((Field)localObject3).setAccessible(true);
            fields.put(str, localObject3);
          }
          i += 1;
        }
        localObject2 = ((Class)localObject1).getSuperclass();
        localObject1 = localObject2;
      } while ((localObject2 != null) && (!localObject2.equals(Object.class)));
      if (!properties.isEmpty()) {
        return;
      }
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("No properties to serialize found on class ");
      ((StringBuilder)localObject1).append(paramClass.getName());
      paramClass = new DatabaseException(((StringBuilder)localObject1).toString());
      throw paramClass;
    }
    
    private void addProperty(String paramString)
    {
      Object localObject = (String)properties.put(paramString.toLowerCase(), paramString);
      if (localObject != null)
      {
        if (paramString.equals(localObject)) {
          return;
        }
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("Found two getters or fields with conflicting case sensitivity for property: ");
        ((StringBuilder)localObject).append(paramString.toLowerCase());
        throw new DatabaseException(((StringBuilder)localObject).toString());
      }
    }
    
    private static String annotatedName(AccessibleObject paramAccessibleObject)
    {
      if (paramAccessibleObject.isAnnotationPresent(PropertyName.class)) {
        return ((PropertyName)paramAccessibleObject.getAnnotation(PropertyName.class)).value();
      }
      return null;
    }
    
    private static boolean isSetterOverride(Method paramMethod1, Method paramMethod2)
    {
      Utilities.hardAssert(paramMethod1.getDeclaringClass().isAssignableFrom(paramMethod2.getDeclaringClass()), "Expected override from a base class");
      Utilities.hardAssert(paramMethod1.getReturnType().equals(Void.TYPE), "Expected void return type");
      Utilities.hardAssert(paramMethod2.getReturnType().equals(Void.TYPE), "Expected void return type");
      Class[] arrayOfClass1 = paramMethod1.getParameterTypes();
      Class[] arrayOfClass2 = paramMethod2.getParameterTypes();
      boolean bool;
      if (arrayOfClass1.length == 1) {
        bool = true;
      } else {
        bool = false;
      }
      Utilities.hardAssert(bool, "Expected exactly one parameter");
      if (arrayOfClass2.length == 1) {
        bool = true;
      } else {
        bool = false;
      }
      Utilities.hardAssert(bool, "Expected exactly one parameter");
      return (paramMethod1.getName().equals(paramMethod2.getName())) && (arrayOfClass1[0].equals(arrayOfClass2[0]));
    }
    
    private static String propertyName(Field paramField)
    {
      String str = annotatedName(paramField);
      if (str != null) {
        return str;
      }
      return paramField.getName();
    }
    
    private static String propertyName(Method paramMethod)
    {
      String str = annotatedName(paramMethod);
      if (str != null) {
        return str;
      }
      return serializedName(paramMethod.getName());
    }
    
    private Type resolveType(Type paramType, Map paramMap)
    {
      if ((paramType instanceof TypeVariable))
      {
        paramMap = (Type)paramMap.get(paramType);
        if (paramMap != null) {
          return paramMap;
        }
        paramMap = new StringBuilder();
        paramMap.append("Could not resolve type ");
        paramMap.append(paramType);
        throw new IllegalStateException(paramMap.toString());
      }
      return paramType;
    }
    
    private static String serializedName(String paramString)
    {
      String[] arrayOfString = new String[3];
      int j = 0;
      arrayOfString[0] = "get";
      arrayOfString[1] = "set";
      arrayOfString[2] = "is";
      int k = arrayOfString.length;
      Object localObject = null;
      int i = 0;
      while (i < k)
      {
        String str = arrayOfString[i];
        if (paramString.startsWith(str)) {
          localObject = str;
        }
        i += 1;
      }
      if (localObject != null)
      {
        paramString = paramString.substring(((String)localObject).length()).toCharArray();
        i = j;
        while ((i < paramString.length) && (Character.isUpperCase(paramString[i])))
        {
          paramString[i] = Character.toLowerCase(paramString[i]);
          i += 1;
        }
        return new String(paramString);
      }
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("Unknown Bean prefix for method: ");
      ((StringBuilder)localObject).append(paramString);
      paramString = new IllegalArgumentException(((StringBuilder)localObject).toString());
      throw paramString;
    }
    
    private static boolean shouldIncludeField(Field paramField)
    {
      if (paramField.getDeclaringClass().equals(Object.class)) {
        return false;
      }
      if (!Modifier.isPublic(paramField.getModifiers())) {
        return false;
      }
      if (Modifier.isStatic(paramField.getModifiers())) {
        return false;
      }
      if (Modifier.isTransient(paramField.getModifiers())) {
        return false;
      }
      return !paramField.isAnnotationPresent(Exclude.class);
    }
    
    private static boolean shouldIncludeGetter(Method paramMethod)
    {
      if ((!paramMethod.getName().startsWith("get")) && (!paramMethod.getName().startsWith("is"))) {
        return false;
      }
      if (paramMethod.getDeclaringClass().equals(Object.class)) {
        return false;
      }
      if (!Modifier.isPublic(paramMethod.getModifiers())) {
        return false;
      }
      if (Modifier.isStatic(paramMethod.getModifiers())) {
        return false;
      }
      if (paramMethod.getReturnType().equals(Void.TYPE)) {
        return false;
      }
      if (paramMethod.getParameterTypes().length != 0) {
        return false;
      }
      return !paramMethod.isAnnotationPresent(Exclude.class);
    }
    
    private static boolean shouldIncludeSetter(Method paramMethod)
    {
      if (!paramMethod.getName().startsWith("set")) {
        return false;
      }
      if (paramMethod.getDeclaringClass().equals(Object.class)) {
        return false;
      }
      if (Modifier.isStatic(paramMethod.getModifiers())) {
        return false;
      }
      if (!paramMethod.getReturnType().equals(Void.TYPE)) {
        return false;
      }
      if (paramMethod.getParameterTypes().length != 1) {
        return false;
      }
      return !paramMethod.isAnnotationPresent(Exclude.class);
    }
    
    public Object deserialize(Map paramMap)
    {
      return deserialize(paramMap, Collections.emptyMap());
    }
    
    public Object deserialize(Map paramMap1, Map paramMap2)
    {
      Object localObject1 = constructor;
      Object localObject2;
      if (localObject1 != null) {
        try
        {
          localObject2 = ((Constructor)localObject1).newInstance(new Object[0]);
          Iterator localIterator = paramMap1.entrySet().iterator();
          for (;;)
          {
            if (!localIterator.hasNext()) {
              break label461;
            }
            paramMap1 = (Map.Entry)localIterator.next();
            Object localObject3 = (String)paramMap1.getKey();
            if (setters.containsKey(localObject3))
            {
              localObject1 = (Method)setters.get(localObject3);
              localObject3 = ((Method)localObject1).getGenericParameterTypes();
              if (localObject3.length == 1)
              {
                localObject3 = resolveType(localObject3[0], paramMap2);
                paramMap1 = CustomClassMapper.deserializeToType(paramMap1.getValue(), (Type)localObject3);
                try
                {
                  ((Method)localObject1).invoke(localObject2, new Object[] { paramMap1 });
                }
                catch (InvocationTargetException paramMap1)
                {
                  throw new RuntimeException(paramMap1);
                }
                catch (IllegalAccessException paramMap1)
                {
                  throw new RuntimeException(paramMap1);
                }
              }
              else
              {
                throw new IllegalStateException("Setter does not have exactly one parameter");
              }
            }
            else if (fields.containsKey(localObject3))
            {
              localObject1 = (Field)fields.get(localObject3);
              localObject3 = resolveType(((Field)localObject1).getGenericType(), paramMap2);
              paramMap1 = CustomClassMapper.deserializeToType(paramMap1.getValue(), (Type)localObject3);
              try
              {
                ((Field)localObject1).set(localObject2, paramMap1);
              }
              catch (IllegalAccessException paramMap1)
              {
                throw new RuntimeException(paramMap1);
              }
            }
            else
            {
              paramMap1 = new StringBuilder();
              paramMap1.append("No setter/field for ");
              paramMap1.append((String)localObject3);
              paramMap1.append(" found on class ");
              paramMap1.append(clazz.getName());
              localObject1 = paramMap1.toString();
              paramMap1 = (Map)localObject1;
              if (properties.containsKey(((String)localObject3).toLowerCase()))
              {
                paramMap1 = new StringBuilder();
                paramMap1.append((String)localObject1);
                paramMap1.append(" (fields/setters are case sensitive!)");
                paramMap1 = paramMap1.toString();
              }
              if (throwOnUnknownProperties) {
                break;
              }
              if (warnOnUnknownProperties) {
                Log.w("ClassMapper", paramMap1);
              }
            }
          }
          throw new DatabaseException(paramMap1);
        }
        catch (InvocationTargetException paramMap1)
        {
          throw new RuntimeException(paramMap1);
        }
        catch (IllegalAccessException paramMap1)
        {
          throw new RuntimeException(paramMap1);
        }
        catch (InstantiationException paramMap1)
        {
          throw new RuntimeException(paramMap1);
        }
      }
      paramMap1 = new StringBuilder();
      paramMap1.append("Class ");
      paramMap1.append(clazz.getName());
      paramMap1.append(" does not define a no-argument constructor. If you are using ProGuard, make sure these constructors are not stripped.");
      paramMap1 = new DatabaseException(paramMap1.toString());
      throw paramMap1;
      label461:
      return localObject2;
    }
    
    public Map serialize(Object paramObject)
    {
      HashMap localHashMap;
      if (clazz.isAssignableFrom(paramObject.getClass()))
      {
        localHashMap = new HashMap();
        Iterator localIterator = properties.values().iterator();
        String str;
        for (;;)
        {
          if (!localIterator.hasNext()) {
            break label263;
          }
          str = (String)localIterator.next();
          if (getters.containsKey(str))
          {
            localObject = (Method)getters.get(str);
            try
            {
              localObject = ((Method)localObject).invoke(paramObject, new Object[0]);
            }
            catch (InvocationTargetException paramObject)
            {
              throw new RuntimeException(paramObject);
            }
            catch (IllegalAccessException paramObject)
            {
              throw new RuntimeException(paramObject);
            }
          }
          else
          {
            localObject = (Field)fields.get(str);
            if (localObject == null) {
              break;
            }
          }
          try
          {
            localObject = ((Field)localObject).get(paramObject);
            localHashMap.put(str, CustomClassMapper.serialize(localObject));
          }
          catch (IllegalAccessException paramObject)
          {
            throw new RuntimeException(paramObject);
          }
        }
        paramObject = new StringBuilder();
        paramObject.append("Bean property without field or getter:");
        paramObject.append(str);
        throw new IllegalStateException(paramObject.toString());
      }
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("Can't serialize object of class ");
      ((StringBuilder)localObject).append(paramObject.getClass());
      ((StringBuilder)localObject).append(" with BeanMapper for class ");
      ((StringBuilder)localObject).append(clazz);
      paramObject = new IllegalArgumentException(((StringBuilder)localObject).toString());
      throw paramObject;
      label263:
      return localHashMap;
    }
  }
}
