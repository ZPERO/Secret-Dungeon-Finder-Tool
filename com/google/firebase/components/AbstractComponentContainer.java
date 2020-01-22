package com.google.firebase.components;

import com.google.firebase.inject.Provider;

abstract class AbstractComponentContainer
  implements ComponentContainer
{
  AbstractComponentContainer() {}
  
  public Object getValue(Class paramClass)
  {
    paramClass = getProvider(paramClass);
    if (paramClass == null) {
      return null;
    }
    return paramClass.get();
  }
}
