package com.google.firebase.inject;

public abstract interface Provider<T>
{
  public abstract Object get();
}
