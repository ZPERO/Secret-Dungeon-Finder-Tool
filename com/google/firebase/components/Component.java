package com.google.firebase.components;

import com.google.android.android.common.internal.Preconditions;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class Component<T>
{
  private final Set<Dependency> dependencies;
  private final ComponentFactory<T> factory;
  private final int instantiation;
  private final Set<Class<? super T>> providedInterfaces;
  private final Set<Class<?>> publishedEvents;
  
  private Component(Set paramSet1, Set paramSet2, int paramInt, ComponentFactory paramComponentFactory, Set paramSet3)
  {
    providedInterfaces = Collections.unmodifiableSet(paramSet1);
    dependencies = Collections.unmodifiableSet(paramSet2);
    instantiation = paramInt;
    factory = paramComponentFactory;
    publishedEvents = Collections.unmodifiableSet(paramSet3);
  }
  
  public static Builder builder(Class paramClass)
  {
    return new Builder(paramClass, new Class[0], null);
  }
  
  public static Builder builder(Class paramClass, Class... paramVarArgs)
  {
    return new Builder(paramClass, paramVarArgs, null);
  }
  
  public static Component getView(Object paramObject, Class paramClass, Class... paramVarArgs)
  {
    return builder(paramClass, paramVarArgs).factory(Component..Lambda.2.lambdaFactory$(paramObject)).build();
  }
  
  public static Component start(Class paramClass, Object paramObject)
  {
    return builder(paramClass).factory(Component..Lambda.1.lambdaFactory$(paramObject)).build();
  }
  
  public Set getDependencies()
  {
    return dependencies;
  }
  
  public ComponentFactory getFactory()
  {
    return factory;
  }
  
  public Set getProvidedInterfaces()
  {
    return providedInterfaces;
  }
  
  public Set getPublishedEvents()
  {
    return publishedEvents;
  }
  
  public boolean isAlwaysEager()
  {
    return instantiation == 1;
  }
  
  public boolean isEagerInDefaultApp()
  {
    return instantiation == 2;
  }
  
  public boolean isLazy()
  {
    return instantiation == 0;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder("Component<");
    localStringBuilder.append(Arrays.toString(providedInterfaces.toArray()));
    localStringBuilder.append(">{");
    localStringBuilder.append(instantiation);
    localStringBuilder.append(", deps=");
    localStringBuilder.append(Arrays.toString(dependencies.toArray()));
    localStringBuilder.append("}");
    return localStringBuilder.toString();
  }
  
  public static class Builder<T>
  {
    private final Set<Dependency> dependencies = new HashSet();
    private ComponentFactory<T> factory;
    private int instantiation;
    private final Set<Class<? super T>> providedInterfaces = new HashSet();
    private Set<Class<?>> publishedEvents;
    
    private Builder(Class paramClass, Class... paramVarArgs)
    {
      int i = 0;
      instantiation = 0;
      publishedEvents = new HashSet();
      Preconditions.checkNotNull(paramClass, "Null interface");
      providedInterfaces.add(paramClass);
      int j = paramVarArgs.length;
      while (i < j)
      {
        Preconditions.checkNotNull(paramVarArgs[i], "Null interface");
        i += 1;
      }
      Collections.addAll(providedInterfaces, paramVarArgs);
    }
    
    private Builder setInstantiation(int paramInt)
    {
      boolean bool;
      if (instantiation == 0) {
        bool = true;
      } else {
        bool = false;
      }
      Preconditions.checkState(bool, "Instantiation type has already been set.");
      instantiation = paramInt;
      return this;
    }
    
    private void validateInterface(Class paramClass)
    {
      Preconditions.checkArgument(providedInterfaces.contains(paramClass) ^ true, "Components are not allowed to depend on interfaces they themselves provide.");
    }
    
    public Builder alwaysEager()
    {
      return setInstantiation(1);
    }
    
    public Component build()
    {
      boolean bool;
      if (factory != null) {
        bool = true;
      } else {
        bool = false;
      }
      Preconditions.checkState(bool, "Missing required property: factory.");
      return new Component(new HashSet(providedInterfaces), new HashSet(dependencies), instantiation, factory, publishedEvents, null);
    }
    
    public Builder eagerInDefaultApp()
    {
      return setInstantiation(2);
    }
    
    public Builder factory(ComponentFactory paramComponentFactory)
    {
      factory = ((ComponentFactory)Preconditions.checkNotNull(paramComponentFactory, "Null factory"));
      return this;
    }
    
    public Builder publishes(Class paramClass)
    {
      publishedEvents.add(paramClass);
      return this;
    }
    
    public Builder putAll(Dependency paramDependency)
    {
      Preconditions.checkNotNull(paramDependency, "Null dependency");
      validateInterface(paramDependency.getInterface());
      dependencies.add(paramDependency);
      return this;
    }
  }
}
