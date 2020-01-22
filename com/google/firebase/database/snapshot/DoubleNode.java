package com.google.firebase.database.snapshot;

import com.google.firebase.database.core.utilities.Utilities;

public class DoubleNode
  extends LeafNode<DoubleNode>
{
  private final Double value;
  
  public DoubleNode(Double paramDouble, Node paramNode)
  {
    super(paramNode);
    value = paramDouble;
  }
  
  protected int compareLeafValues(DoubleNode paramDoubleNode)
  {
    return value.compareTo(value);
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof DoubleNode)) {
      return false;
    }
    paramObject = (DoubleNode)paramObject;
    return (value.equals(value)) && (priority.equals(priority));
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
    localStringBuilder.append(Utilities.doubleToHashString(value.doubleValue()));
    return localStringBuilder.toString();
  }
  
  protected LeafNode.LeafType getLeafType()
  {
    return LeafNode.LeafType.Number;
  }
  
  public Object getValue()
  {
    return value;
  }
  
  public int hashCode()
  {
    return value.hashCode() + priority.hashCode();
  }
  
  public DoubleNode updatePriority(Node paramNode)
  {
    return new DoubleNode(value, paramNode);
  }
}
