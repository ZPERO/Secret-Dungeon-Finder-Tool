package com.squareup.okhttp.internal.http;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class RouteException
  extends Exception
{
  private static final Method addSuppressedExceptionMethod;
  private IOException lastException;
  
  static
  {
    try
    {
      localMethod = Throwable.class.getDeclaredMethod("addSuppressed", new Class[] { Throwable.class });
    }
    catch (Exception localException)
    {
      Method localMethod;
      for (;;) {}
    }
    localMethod = null;
    addSuppressedExceptionMethod = localMethod;
  }
  
  public RouteException(IOException paramIOException)
  {
    super(paramIOException);
    lastException = paramIOException;
  }
  
  private void addSuppressedIfPossible(IOException paramIOException1, IOException paramIOException2)
  {
    Method localMethod = addSuppressedExceptionMethod;
    if (localMethod != null) {
      try
      {
        localMethod.invoke(paramIOException1, new Object[] { paramIOException2 });
        return;
      }
      catch (InvocationTargetException paramIOException1) {}catch (IllegalAccessException paramIOException1) {}
    }
  }
  
  public void addConnectException(IOException paramIOException)
  {
    addSuppressedIfPossible(paramIOException, lastException);
    lastException = paramIOException;
  }
  
  public IOException getLastConnectException()
  {
    return lastException;
  }
}
