package com.google.firebase.database.core.utilities;

public class Pair<T, U>
{
  private final T first;
  private final U second;
  
  public Pair(Object paramObject1, Object paramObject2)
  {
    first = paramObject1;
    second = paramObject2;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if (paramObject != null)
    {
      if (getClass() != paramObject.getClass()) {
        return false;
      }
      paramObject = (Pair)paramObject;
      Object localObject = first;
      if (localObject != null)
      {
        if (!localObject.equals(first)) {
          return false;
        }
      }
      else if (first != null) {
        return false;
      }
      localObject = second;
      paramObject = second;
      if (localObject != null)
      {
        if (!localObject.equals(paramObject)) {
          return false;
        }
      }
      else
      {
        if (paramObject == null) {
          break label94;
        }
        return false;
      }
      return true;
    }
    else
    {
      return false;
    }
    label94:
    return true;
  }
  
  public Object getFirst()
  {
    return first;
  }
  
  public Object getSecond()
  {
    return second;
  }
  
  public int hashCode()
  {
    Object localObject = first;
    int j = 0;
    int i;
    if (localObject != null) {
      i = localObject.hashCode();
    } else {
      i = 0;
    }
    localObject = second;
    if (localObject != null) {
      j = localObject.hashCode();
    }
    return i * 31 + j;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Pair(");
    localStringBuilder.append(first);
    localStringBuilder.append(",");
    localStringBuilder.append(second);
    localStringBuilder.append(")");
    return localStringBuilder.toString();
  }
}
