package com.google.firebase.database.snapshot;

public class KeyIndex
  extends Index
{
  private static final KeyIndex INSTANCE = new KeyIndex();
  
  private KeyIndex() {}
  
  public static KeyIndex getInstance()
  {
    return INSTANCE;
  }
  
  public int compare(NamedNode paramNamedNode1, NamedNode paramNamedNode2)
  {
    return paramNamedNode1.getName().compareTo(paramNamedNode2.getName());
  }
  
  public boolean equals(Object paramObject)
  {
    return paramObject instanceof KeyIndex;
  }
  
  public String getQueryDefinition()
  {
    return ".key";
  }
  
  public int hashCode()
  {
    return 37;
  }
  
  public boolean isDefinedOn(Node paramNode)
  {
    return true;
  }
  
  public NamedNode makePost(ChildKey paramChildKey, Node paramNode)
  {
    return new NamedNode(ChildKey.fromString((String)paramNode.getValue()), EmptyNode.Empty());
  }
  
  public NamedNode maxPost()
  {
    return NamedNode.getMaxNode();
  }
  
  public String toString()
  {
    return "KeyIndex";
  }
}
