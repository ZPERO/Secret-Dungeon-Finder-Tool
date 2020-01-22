package com.google.firebase.database.snapshot;

public class PriorityIndex
  extends Index
{
  private static final PriorityIndex INSTANCE = new PriorityIndex();
  
  private PriorityIndex() {}
  
  public static PriorityIndex getInstance()
  {
    return INSTANCE;
  }
  
  public int compare(NamedNode paramNamedNode1, NamedNode paramNamedNode2)
  {
    Node localNode1 = paramNamedNode1.getNode().getPriority();
    Node localNode2 = paramNamedNode2.getNode().getPriority();
    return NodeUtilities.nameAndPriorityCompare(paramNamedNode1.getName(), localNode1, paramNamedNode2.getName(), localNode2);
  }
  
  public boolean equals(Object paramObject)
  {
    return paramObject instanceof PriorityIndex;
  }
  
  public String getQueryDefinition()
  {
    throw new IllegalArgumentException("Can't get query definition on priority index!");
  }
  
  public int hashCode()
  {
    return 3155577;
  }
  
  public boolean isDefinedOn(Node paramNode)
  {
    return paramNode.getPriority().isEmpty() ^ true;
  }
  
  public NamedNode makePost(ChildKey paramChildKey, Node paramNode)
  {
    return new NamedNode(paramChildKey, new StringNode("[PRIORITY-POST]", paramNode));
  }
  
  public NamedNode maxPost()
  {
    return makePost(ChildKey.getMaxName(), Node.MAX_NODE);
  }
  
  public String toString()
  {
    return "PriorityIndex";
  }
}
