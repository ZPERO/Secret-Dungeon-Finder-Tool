package com.google.firebase.database.core.view;

import com.google.firebase.database.core.Path;
import com.google.firebase.database.snapshot.ChildKey;
import com.google.firebase.database.snapshot.IndexedNode;
import com.google.firebase.database.snapshot.Node;

public class CacheNode
{
  private final boolean filtered;
  private final boolean fullyInitialized;
  private final IndexedNode indexedNode;
  
  public CacheNode(IndexedNode paramIndexedNode, boolean paramBoolean1, boolean paramBoolean2)
  {
    indexedNode = paramIndexedNode;
    fullyInitialized = paramBoolean1;
    filtered = paramBoolean2;
  }
  
  public IndexedNode getIndexedNode()
  {
    return indexedNode;
  }
  
  public Node getNode()
  {
    return indexedNode.getNode();
  }
  
  public boolean isCompleteForChild(ChildKey paramChildKey)
  {
    return ((isFullyInitialized()) && (!filtered)) || (indexedNode.getNode().hasChild(paramChildKey));
  }
  
  public boolean isCompleteForPath(Path paramPath)
  {
    if (paramPath.isEmpty()) {
      return (isFullyInitialized()) && (!filtered);
    }
    return isCompleteForChild(paramPath.getFront());
  }
  
  public boolean isFiltered()
  {
    return filtered;
  }
  
  public boolean isFullyInitialized()
  {
    return fullyInitialized;
  }
}
