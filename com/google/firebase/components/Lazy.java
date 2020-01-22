package com.google.firebase.components;

import com.google.firebase.inject.Provider;

class Lazy<T>
  implements Provider<T>
{
  private static final Object UNINITIALIZED = new Object();
  private volatile Object instance = UNINITIALIZED;
  private volatile Provider<T> provider;
  
  Lazy(ComponentFactory paramComponentFactory, ComponentContainer paramComponentContainer)
  {
    provider = Lazy..Lambda.1.lambdaFactory$(paramComponentFactory, paramComponentContainer);
  }
  
  Lazy(Object paramObject)
  {
    instance = paramObject;
  }
  
  public Object get()
  {
    Object localObject1 = instance;
    if (localObject1 == UNINITIALIZED) {
      try
      {
        Object localObject2 = instance;
        localObject1 = localObject2;
        if (localObject2 == UNINITIALIZED)
        {
          localObject2 = provider.get();
          localObject1 = localObject2;
          instance = localObject2;
          provider = null;
        }
        return localObject1;
      }
      catch (Throwable localThrowable)
      {
        throw localThrowable;
      }
    }
    return localThrowable;
  }
  
  boolean isInitialized()
  {
    return instance != UNINITIALIZED;
  }
}
