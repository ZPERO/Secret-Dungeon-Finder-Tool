package com.google.firebase.database.snapshot;

import com.google.firebase.database.core.utilities.Utilities;

public class ChildKey
  implements Comparable<ChildKey>
{
  private static final ChildKey INFO_CHILD_KEY = new ChildKey(".info");
  private static final ChildKey MAX_KEY;
  private static final ChildKey MIN_KEY = new ChildKey("[MIN_KEY]");
  private static final ChildKey PRIORITY_CHILD_KEY;
  private final String string;
  
  static
  {
    MAX_KEY = new ChildKey("[MAX_KEY]");
    PRIORITY_CHILD_KEY = new ChildKey(".priority");
  }
  
  private ChildKey(String paramString)
  {
    string = paramString;
  }
  
  public static ChildKey fromString(String paramString)
  {
    Integer localInteger = Utilities.tryParseInt(paramString);
    if (localInteger != null) {
      return new IntegerChildKey(paramString, localInteger.intValue());
    }
    if (paramString.equals(".priority")) {
      return PRIORITY_CHILD_KEY;
    }
    return new ChildKey(paramString);
  }
  
  public static ChildKey getInfoKey()
  {
    return INFO_CHILD_KEY;
  }
  
  public static ChildKey getMaxName()
  {
    return MAX_KEY;
  }
  
  public static ChildKey getMinName()
  {
    return MIN_KEY;
  }
  
  public static ChildKey getPriorityKey()
  {
    return PRIORITY_CHILD_KEY;
  }
  
  public String asString()
  {
    return string;
  }
  
  public int compareTo(ChildKey paramChildKey)
  {
    if (this == paramChildKey) {
      return 0;
    }
    ChildKey localChildKey1 = MIN_KEY;
    int i;
    if (this != localChildKey1)
    {
      ChildKey localChildKey2 = MAX_KEY;
      if (paramChildKey == localChildKey2) {
        return -1;
      }
      if (paramChildKey != localChildKey1)
      {
        if (this == localChildKey2) {
          return 1;
        }
        if (isInt())
        {
          if (paramChildKey.isInt())
          {
            i = Utilities.compareInts(intValue(), paramChildKey.intValue());
            if (i == 0) {
              return Utilities.compareInts(string.length(), string.length());
            }
          }
          else
          {
            return -1;
          }
        }
        else
        {
          if (paramChildKey.isInt()) {
            return 1;
          }
          return string.compareTo(string);
        }
      }
      else
      {
        return 1;
      }
    }
    else
    {
      return -1;
    }
    return i;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof ChildKey)) {
      return false;
    }
    if (this == paramObject) {
      return true;
    }
    paramObject = (ChildKey)paramObject;
    return string.equals(string);
  }
  
  public int hashCode()
  {
    return string.hashCode();
  }
  
  protected int intValue()
  {
    return 0;
  }
  
  protected boolean isInt()
  {
    return false;
  }
  
  public boolean isPriorityChildName()
  {
    return equals(PRIORITY_CHILD_KEY);
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("ChildKey(\"");
    localStringBuilder.append(string);
    localStringBuilder.append("\")");
    return localStringBuilder.toString();
  }
  
  private static class IntegerChildKey
    extends ChildKey
  {
    private final int intValue;
    
    IntegerChildKey(String paramString, int paramInt)
    {
      super(null);
      intValue = paramInt;
    }
    
    protected int intValue()
    {
      return intValue;
    }
    
    protected boolean isInt()
    {
      return true;
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("IntegerChildName(\"");
      localStringBuilder.append(string);
      localStringBuilder.append("\")");
      return localStringBuilder.toString();
    }
  }
}
