package com.google.gson.internal.reflect;

import com.google.gson.JsonIOException;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

final class UnsafeReflectionAccessor
  extends ReflectionAccessor
{
  private static Class unsafeClass;
  private final Field overrideField = getOverrideField();
  private final Object theUnsafe = getUnsafeInstance();
  
  UnsafeReflectionAccessor() {}
  
  private static Field getOverrideField()
  {
    try
    {
      Field localField = AccessibleObject.class.getDeclaredField("override");
      return localField;
    }
    catch (NoSuchFieldException localNoSuchFieldException)
    {
      for (;;) {}
    }
    return null;
  }
  
  private static Object getUnsafeInstance()
  {
    try
    {
      Object localObject = Class.forName("sun.misc.Unsafe");
      unsafeClass = (Class)localObject;
      localObject = unsafeClass;
      localObject = ((Class)localObject).getDeclaredField("theUnsafe");
      ((Field)localObject).setAccessible(true);
      localObject = ((Field)localObject).get(null);
      return localObject;
    }
    catch (Exception localException) {}
    return null;
  }
  
  public void makeAccessible(AccessibleObject paramAccessibleObject)
  {
    if (!makeAccessibleWithUnsafe(paramAccessibleObject)) {
      try
      {
        paramAccessibleObject.setAccessible(true);
        return;
      }
      catch (SecurityException localSecurityException)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("Gson couldn't modify fields for ");
        localStringBuilder.append(paramAccessibleObject);
        localStringBuilder.append("\nand sun.misc.Unsafe not found.\nEither write a custom type adapter, or make fields accessible, or include sun.misc.Unsafe.");
        throw new JsonIOException(localStringBuilder.toString(), localSecurityException);
      }
    }
  }
  
  boolean makeAccessibleWithUnsafe(AccessibleObject paramAccessibleObject)
  {
    Object localObject1;
    if (theUnsafe != null)
    {
      if (overrideField != null) {
        localObject1 = unsafeClass;
      }
    }
    else
    {
      try
      {
        localObject1 = ((Class)localObject1).getMethod("objectFieldOffset", new Class[] { Field.class });
        Object localObject2 = theUnsafe;
        Object localObject3 = overrideField;
        localObject1 = ((Method)localObject1).invoke(localObject2, new Object[] { localObject3 });
        localObject1 = (Long)localObject1;
        long l = ((Long)localObject1).longValue();
        localObject1 = unsafeClass;
        localObject2 = Long.TYPE;
        localObject3 = Boolean.TYPE;
        localObject1 = ((Class)localObject1).getMethod("putBoolean", new Class[] { Object.class, localObject2, localObject3 });
        localObject2 = theUnsafe;
        ((Method)localObject1).invoke(localObject2, new Object[] { paramAccessibleObject, Long.valueOf(l), Boolean.valueOf(true) });
        return true;
      }
      catch (Exception paramAccessibleObject) {}
      return false;
    }
    return false;
  }
}
