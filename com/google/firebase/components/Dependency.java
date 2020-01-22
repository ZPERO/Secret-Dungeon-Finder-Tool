package com.google.firebase.components;

import com.google.android.android.common.internal.Preconditions;

public final class Dependency
{
  private final Class<?> anInterface;
  private final int injection;
  private final int type;
  
  private Dependency(Class paramClass, int paramInt1, int paramInt2)
  {
    anInterface = ((Class)Preconditions.checkNotNull(paramClass, "Null dependency anInterface."));
    type = paramInt1;
    injection = paramInt2;
  }
  
  public static Dependency optional(Class paramClass)
  {
    return new Dependency(paramClass, 0, 0);
  }
  
  public static Dependency optionalProvider(Class paramClass)
  {
    return new Dependency(paramClass, 0, 1);
  }
  
  public static Dependency required(Class paramClass)
  {
    return new Dependency(paramClass, 1, 0);
  }
  
  public static Dependency requiredProvider(Class paramClass)
  {
    return new Dependency(paramClass, 1, 1);
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject instanceof Dependency))
    {
      paramObject = (Dependency)paramObject;
      if ((anInterface == anInterface) && (type == type) && (injection == injection)) {
        return true;
      }
    }
    return false;
  }
  
  public Class getInterface()
  {
    return anInterface;
  }
  
  public int hashCode()
  {
    return ((anInterface.hashCode() ^ 0xF4243) * 1000003 ^ type) * 1000003 ^ injection;
  }
  
  public boolean isDirectInjection()
  {
    return injection == 0;
  }
  
  public boolean isRequired()
  {
    return type == 1;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder("Dependency{anInterface=");
    localStringBuilder.append(anInterface);
    localStringBuilder.append(", required=");
    int i = type;
    boolean bool2 = false;
    if (i == 1) {
      bool1 = true;
    } else {
      bool1 = false;
    }
    localStringBuilder.append(bool1);
    localStringBuilder.append(", direct=");
    boolean bool1 = bool2;
    if (injection == 0) {
      bool1 = true;
    }
    localStringBuilder.append(bool1);
    localStringBuilder.append("}");
    return localStringBuilder.toString();
  }
}
