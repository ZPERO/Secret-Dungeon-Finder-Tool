package com.google.firebase.database.snapshot;

public class NamedNode
{
  private static final NamedNode MAX_NODE = new NamedNode(ChildKey.getMaxName(), Node.MAX_NODE);
  private static final NamedNode MIN_NODE = new NamedNode(ChildKey.getMinName(), EmptyNode.Empty());
  private final ChildKey name;
  private final Node node;
  
  public NamedNode(ChildKey paramChildKey, Node paramNode)
  {
    name = paramChildKey;
    node = paramNode;
  }
  
  public static NamedNode getMaxNode()
  {
    return MAX_NODE;
  }
  
  public static NamedNode getMinNode()
  {
    return MIN_NODE;
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
      paramObject = (NamedNode)paramObject;
      if (!name.equals(name)) {
        return false;
      }
      return node.equals(node);
    }
    return false;
  }
  
  public ChildKey getName()
  {
    return name;
  }
  
  public Node getNode()
  {
    return node;
  }
  
  public int hashCode()
  {
    return name.hashCode() * 31 + node.hashCode();
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("NamedNode{name=");
    localStringBuilder.append(name);
    localStringBuilder.append(", node=");
    localStringBuilder.append(node);
    localStringBuilder.append('}');
    return localStringBuilder.toString();
  }
}
