package com.google.firebase.components;

import com.google.firebase.inject.Provider;

public abstract interface ComponentContainer
{
  public abstract Provider getProvider(Class paramClass);
  
  public abstract Object getValue(Class paramClass);
}
