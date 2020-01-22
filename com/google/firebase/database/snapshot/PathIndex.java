package com.google.firebase.database.snapshot;

import com.google.firebase.database.core.Path;

public class PathIndex
  extends Index
{
  private final Path indexPath;
  
  public PathIndex(Path paramPath)
  {
    if ((paramPath.size() == 1) && (paramPath.getFront().isPriorityChildName())) {
      throw new IllegalArgumentException("Can't create PathIndex with '.priority' as key. Please use PriorityIndex instead!");
    }
    indexPath = paramPath;
  }
  
  public int compare(NamedNode paramNamedNode1, NamedNode paramNamedNode2)
  {
    int j = paramNamedNode1.getNode().getChild(indexPath).compareTo(paramNamedNode2.getNode().getChild(indexPath));
    int i = j;
    if (j == 0) {
      i = paramNamedNode1.getName().compareTo(paramNamedNode2.getName());
    }
    return i;
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
      paramObject = (PathIndex)paramObject;
      return indexPath.equals(indexPath);
    }
    return false;
  }
  
  public String getQueryDefinition()
  {
    return indexPath.wireFormat();
  }
  
  public int hashCode()
  {
    return indexPath.hashCode();
  }
  
  public boolean isDefinedOn(Node paramNode)
  {
    return paramNode.getChild(indexPath).isEmpty() ^ true;
  }
  
  public NamedNode makePost(ChildKey paramChildKey, Node paramNode)
  {
    return new NamedNode(paramChildKey, EmptyNode.Empty().updateChild(indexPath, paramNode));
  }
  
  public NamedNode maxPost()
  {
    Node localNode = EmptyNode.Empty().updateChild(indexPath, Node.MAX_NODE);
    return new NamedNode(ChildKey.getMaxName(), localNode);
  }
}