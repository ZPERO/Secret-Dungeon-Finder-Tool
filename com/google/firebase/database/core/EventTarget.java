package com.google.firebase.database.core;

public abstract interface EventTarget
{
  public abstract void postEvent(Runnable paramRunnable);
  
  public abstract void restart();
  
  public abstract void shutdown();
}
