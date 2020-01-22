package com.google.firebase.events;

import java.util.concurrent.Executor;

public abstract interface Subscriber
{
  public abstract void subscribe(Class paramClass, EventHandler paramEventHandler);
  
  public abstract void subscribe(Class paramClass, Executor paramExecutor, EventHandler paramEventHandler);
  
  public abstract void unsubscribe(Class paramClass, EventHandler paramEventHandler);
}
