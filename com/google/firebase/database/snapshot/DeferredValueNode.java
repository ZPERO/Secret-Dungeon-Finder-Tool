package com.google.firebase.database.snapshot;

import java.util.Map;

public class DeferredValueNode
  extends LeafNode<DeferredValueNode>
{
  private Map<Object, Object> value;
  
  public DeferredValueNode(Map paramMap, Node paramNode)
  {
    super(paramNode);
    value = paramMap;
  }
  
  protected int compareLeafValues(DeferredValueNode paramDeferredValueNode)
  {
    return 0;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof DeferredValueNode)) {
      return false;
    }
    paramObject = (DeferredValueNode)paramObject;
    return (value.equals(value)) && (priority.equals(priority));
  }
  
  public String getHashRepresentation(Node.HashVersion paramHashVersion)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(getPriorityHash(paramHashVersion));
    localStringBuilder.append("deferredValue:");
    localStringBuilder.append(value);
    return localStringBuilder.toString();
  }
  
  protected LeafNode.LeafType getLeafType()
  {
    return LeafNode.LeafType.DeferredValue;
  }
  
  public Object getValue()
  {
    return value;
  }
  
  public int hashCode()
  {
    return value.hashCode() + priority.hashCode();
  }
  
  public DeferredValueNode updatePriority(Node paramNode)
  {
    return new DeferredValueNode(value, paramNode);
  }
}
