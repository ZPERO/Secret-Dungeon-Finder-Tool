package com.google.firebase.database.snapshot;

public class ValueIndex
  extends Index
{
  private static final ValueIndex INSTANCE = new ValueIndex();
  
  private ValueIndex() {}
  
  public static ValueIndex getInstance()
  {
    return INSTANCE;
  }
  
  public int compare(NamedNode paramNamedNode1, NamedNode paramNamedNode2)
  {
    int j = paramNamedNode1.getNode().compareTo(paramNamedNode2.getNode());
    int i = j;
    if (j == 0) {
      i = paramNamedNode1.getName().compareTo(paramNamedNode2.getName());
    }
    return i;
  }
  
  public boolean equals(Object paramObject)
  {
    return paramObject instanceof ValueIndex;
  }
  
  public String getQueryDefinition()
  {
    return ".value";
  }
  
  public int hashCode()
  {
    return 4;
  }
  
  public boolean isDefinedOn(Node paramNode)
  {
    return true;
  }
  
  public NamedNode makePost(ChildKey paramChildKey, Node paramNode)
  {
    return new NamedNode(paramChildKey, paramNode);
  }
  
  public NamedNode maxPost()
  {
    return new NamedNode(ChildKey.getMaxName(), Node.MAX_NODE);
  }
  
  public String toString()
  {
    return "ValueIndex";
  }
}
