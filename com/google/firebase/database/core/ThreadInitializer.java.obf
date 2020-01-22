package com.google.firebase.database.core;

public abstract interface ThreadInitializer
{
  public static final ThreadInitializer defaultInstance = new ThreadInitializer()
  {
    public void setDaemon(Thread paramAnonymousThread, boolean paramAnonymousBoolean)
    {
      paramAnonymousThread.setDaemon(paramAnonymousBoolean);
    }
    
    public void setName(Thread paramAnonymousThread, String paramAnonymousString)
    {
      paramAnonymousThread.setName(paramAnonymousString);
    }
    
    public void setUncaughtExceptionHandler(Thread paramAnonymousThread, Thread.UncaughtExceptionHandler paramAnonymousUncaughtExceptionHandler)
    {
      paramAnonymousThread.setUncaughtExceptionHandler(paramAnonymousUncaughtExceptionHandler);
    }
  };
  
  public abstract void setDaemon(Thread paramThread, boolean paramBoolean);
  
  public abstract void setName(Thread paramThread, String paramString);
  
  public abstract void setUncaughtExceptionHandler(Thread paramThread, Thread.UncaughtExceptionHandler paramUncaughtExceptionHandler);
}
