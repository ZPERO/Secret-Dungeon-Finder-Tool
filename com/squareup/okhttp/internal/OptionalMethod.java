package com.squareup.okhttp.internal;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class OptionalMethod<T>
{
  private final String methodName;
  private final Class[] methodParams;
  private final Class<?> returnType;
  
  public OptionalMethod(Class paramClass, String paramString, Class... paramVarArgs)
  {
    returnType = paramClass;
    methodName = paramString;
    methodParams = paramVarArgs;
  }
  
  private Method getMethod(Class paramClass)
  {
    Object localObject = methodName;
    if (localObject != null)
    {
      paramClass = getPublicMethod(paramClass, (String)localObject, methodParams);
      if (paramClass != null)
      {
        localObject = returnType;
        if ((localObject != null) && (!((Class)localObject).isAssignableFrom(paramClass.getReturnType()))) {
          return null;
        }
      }
      return paramClass;
    }
    return null;
  }
  
  private static Method getPublicMethod(Class paramClass, String paramString, Class[] paramArrayOfClass)
  {
    try
    {
      paramClass = paramClass.getMethod(paramString, paramArrayOfClass);
      int i;
      return paramClass;
    }
    catch (NoSuchMethodException paramClass)
    {
      for (;;)
      {
        try
        {
          i = paramClass.getModifiers();
          if ((i & 0x1) != 0) {
            break;
          }
          return null;
        }
        catch (NoSuchMethodException paramString) {}
        paramClass = paramClass;
      }
    }
  }
  
  public Object invoke(Object paramObject, Object... paramVarArgs)
    throws InvocationTargetException
  {
    Method localMethod = getMethod(paramObject.getClass());
    if (localMethod != null) {
      try
      {
        paramObject = localMethod.invoke(paramObject, paramVarArgs);
        return paramObject;
      }
      catch (IllegalAccessException paramObject)
      {
        paramVarArgs = new StringBuilder();
        paramVarArgs.append("Unexpectedly could not call: ");
        paramVarArgs.append(localMethod);
        paramVarArgs = new AssertionError(paramVarArgs.toString());
        paramVarArgs.initCause(paramObject);
        throw paramVarArgs;
      }
    }
    paramVarArgs = new StringBuilder();
    paramVarArgs.append("Method ");
    paramVarArgs.append(methodName);
    paramVarArgs.append(" not supported for object ");
    paramVarArgs.append(paramObject);
    throw new AssertionError(paramVarArgs.toString());
  }
  
  public Object invokeOptional(Object paramObject, Object... paramVarArgs)
    throws InvocationTargetException
  {
    Method localMethod = getMethod(paramObject.getClass());
    if (localMethod == null) {
      return null;
    }
    try
    {
      paramObject = localMethod.invoke(paramObject, paramVarArgs);
      return paramObject;
    }
    catch (IllegalAccessException paramObject) {}
    return null;
  }
  
  public Object invokeOptionalWithoutCheckedException(Object paramObject, Object... paramVarArgs)
  {
    try
    {
      paramObject = invokeOptional(paramObject, paramVarArgs);
      return paramObject;
    }
    catch (InvocationTargetException paramObject)
    {
      paramObject = paramObject.getTargetException();
      if ((paramObject instanceof RuntimeException)) {
        throw ((RuntimeException)paramObject);
      }
      paramVarArgs = new AssertionError("Unexpected exception");
      paramVarArgs.initCause(paramObject);
      throw paramVarArgs;
    }
  }
  
  public Object invokeWithoutCheckedException(Object paramObject, Object... paramVarArgs)
  {
    try
    {
      paramObject = invoke(paramObject, paramVarArgs);
      return paramObject;
    }
    catch (InvocationTargetException paramObject)
    {
      paramObject = paramObject.getTargetException();
      if ((paramObject instanceof RuntimeException)) {
        throw ((RuntimeException)paramObject);
      }
      paramVarArgs = new AssertionError("Unexpected exception");
      paramVarArgs.initCause(paramObject);
      throw paramVarArgs;
    }
  }
  
  public boolean isSupported(Object paramObject)
  {
    return getMethod(paramObject.getClass()) != null;
  }
}
