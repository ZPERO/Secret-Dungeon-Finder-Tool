package com.google.firebase.database.core;

public class Permutation
{
  private final long tagNumber;
  
  public Permutation(long paramLong)
  {
    tagNumber = paramLong;
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
      paramObject = (Permutation)paramObject;
      return tagNumber == tagNumber;
    }
    return false;
  }
  
  public long getTagNumber()
  {
    return tagNumber;
  }
  
  public int hashCode()
  {
    long l = tagNumber;
    return (int)(l ^ l >>> 32);
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Tag{tagNumber=");
    localStringBuilder.append(tagNumber);
    localStringBuilder.append('}');
    return localStringBuilder.toString();
  }
}
