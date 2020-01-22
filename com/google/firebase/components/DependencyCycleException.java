package com.google.firebase.components;

import java.util.List;

public class DependencyCycleException
  extends DependencyException
{
  private final List<Component<?>> componentsInCycle;
  
  public DependencyCycleException(List paramList)
  {
    super(localStringBuilder.toString());
    componentsInCycle = paramList;
  }
  
  public List getComponentsInCycle()
  {
    return componentsInCycle;
  }
}
