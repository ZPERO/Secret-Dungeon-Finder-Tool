package com.google.firebase.events;

public abstract interface EventHandler<T>
{
  public abstract void handle(Event paramEvent);
}
