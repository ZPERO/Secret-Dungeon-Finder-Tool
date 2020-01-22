package com.google.firebase.components;

public abstract interface ComponentFactory<T>
{
  public abstract Object create(ComponentContainer paramComponentContainer);
}
