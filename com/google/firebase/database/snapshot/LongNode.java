package com.google.firebase.database.snapshot;

import com.google.firebase.database.core.utilities.Utilities;

public class LongNode
  extends LeafNode<LongNode>
{
  private final long value;
  
  public LongNode(Long paramLong, Node paramNode)
  {
    super(paramNode);
    value = paramLong.longValue();
  }
  
  protected int compareLeafValues(LongNode paramLongNode)
  {
    return Utilities.compareLongs(value, value);
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof LongNode)) {
      return false;
    }
    paramObject = (LongNode)paramObject;
    return (value == value) && (priority.equals(priority));
  }
  
  public String getHashRepresentation(Node.HashVersion paramHashVersion)
  {
    paramHashVersion = getPriorityHash(paramHashVersion);
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramHashVersion);
    localStringBuilder.append("number:");
    paramHashVersion = localStringBuilder.toString();
    localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramHashVersion);
    localStringBuilder.append(Utilities.doubleToHashString(value));
    return localStringBuilder.toString();
  }
  
  protected LeafNode.LeafType getLeafType()
  {
    return LeafNode.LeafType.Number;
  }
  
  public Object getValue()
  {
    return Long.valueOf(value);
  }
  
  public int hashCode()
  {
    long l = value;
    return (int)(l ^ l >>> 32) + priority.hashCode();
  }
  
  public LongNode updatePriority(Node paramNode)
  {
    return new LongNode(Long.valueOf(value), paramNode);
  }
}
