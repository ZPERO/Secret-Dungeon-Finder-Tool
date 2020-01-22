package com.squareup.okhttp.internal;

public abstract class NamedRunnable
  implements Runnable
{
  protected final String name;
  
  public NamedRunnable(String paramString, Object... paramVarArgs)
  {
    name = String.format(paramString, paramVarArgs);
  }
  
  protected abstract void execute();
  
  public final void run()
  {
    String str = Thread.currentThread().getName();
    Thread.currentThread().setName(name);
    try
    {
      execute();
      Thread.currentThread().setName(str);
      return;
    }
    catch (Throwable localThrowable)
    {
      Thread.currentThread().setName(str);
      throw localThrowable;
    }
  }
}
