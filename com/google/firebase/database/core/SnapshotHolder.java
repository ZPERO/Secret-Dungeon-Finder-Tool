package com.google.firebase.database.core;

import com.google.firebase.database.snapshot.EmptyNode;
import com.google.firebase.database.snapshot.Node;

public class SnapshotHolder
{
  private Node rootNode;
  
  SnapshotHolder()
  {
    rootNode = EmptyNode.Empty();
  }
  
  public SnapshotHolder(Node paramNode)
  {
    rootNode = paramNode;
  }
  
  public Node getNode(Path paramPath)
  {
    return rootNode.getChild(paramPath);
  }
  
  public Node getRootNode()
  {
    return rootNode;
  }
  
  public void update(Path paramPath, Node paramNode)
  {
    rootNode = rootNode.updateChild(paramPath, paramNode);
  }
}
