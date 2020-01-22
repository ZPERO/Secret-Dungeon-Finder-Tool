package com.google.firebase.database.core.utilities.tuple;

import com.google.firebase.database.core.Path;
import com.google.firebase.database.snapshot.Node;

public class NodeAndPath
{
  private Node node;
  private Path path;
  
  public NodeAndPath(Node paramNode, Path paramPath)
  {
    node = paramNode;
    path = paramPath;
  }
  
  public Node getNode()
  {
    return node;
  }
  
  public Path getPath()
  {
    return path;
  }
  
  public void setNode(Node paramNode)
  {
    node = paramNode;
  }
  
  public void setPath(Path paramPath)
  {
    path = paramPath;
  }
}
