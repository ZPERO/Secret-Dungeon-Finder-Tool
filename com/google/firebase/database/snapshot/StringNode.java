package com.google.firebase.database.snapshot;

import com.google.firebase.database.core.utilities.Utilities;

public class StringNode
  extends LeafNode<StringNode>
{
  private final String value;
  
  public StringNode(String paramString, Node paramNode)
  {
    super(paramNode);
    value = paramString;
  }
  
  protected int compareLeafValues(StringNode paramStringNode)
  {
    return value.compareTo(value);
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof StringNode)) {
      return false;
    }
    paramObject = (StringNode)paramObject;
    return (value.equals(value)) && (priority.equals(priority));
  }
  
  public String getHashRepresentation(Node.HashVersion paramHashVersion)
  {
    int i = 1.$SwitchMap$com$google$firebase$database$snapshot$Node$HashVersion[paramHashVersion.ordinal()];
    if (i != 1)
    {
      if (i == 2)
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append(getPriorityHash(paramHashVersion));
        localStringBuilder.append("string:");
        localStringBuilder.append(Utilities.stringHashV2Representation(value));
        return localStringBuilder.toString();
      }
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("Invalid hash version for string node: ");
      localStringBuilder.append(paramHashVersion);
      throw new IllegalArgumentException(localStringBuilder.toString());
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(getPriorityHash(paramHashVersion));
    localStringBuilder.append("string:");
    localStringBuilder.append(value);
    return localStringBuilder.toString();
  }
  
  protected LeafNode.LeafType getLeafType()
  {
    return LeafNode.LeafType.String;
  }
  
  public Object getValue()
  {
    return value;
  }
  
  public int hashCode()
  {
    return value.hashCode() + priority.hashCode();
  }
  
  public StringNode updatePriority(Node paramNode)
  {
    return new StringNode(value, paramNode);
  }
}
