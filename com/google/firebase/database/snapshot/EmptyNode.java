package com.google.firebase.database.snapshot;

import com.google.firebase.database.core.Path;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class EmptyNode
  extends ChildrenNode
  implements Node
{
  private static final EmptyNode empty = new EmptyNode();
  
  private EmptyNode() {}
  
  public static EmptyNode Empty()
  {
    return empty;
  }
  
  public int compareTo(Node paramNode)
  {
    if (paramNode.isEmpty()) {
      return 0;
    }
    return -1;
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject instanceof EmptyNode)) {
      return true;
    }
    if ((paramObject instanceof Node))
    {
      paramObject = (Node)paramObject;
      if ((paramObject.isEmpty()) && (getPriority().equals(paramObject.getPriority()))) {
        return true;
      }
    }
    return false;
  }
  
  public Node getChild(Path paramPath)
  {
    return this;
  }
  
  public int getChildCount()
  {
    return 0;
  }
  
  public String getHash()
  {
    return "";
  }
  
  public String getHashRepresentation(Node.HashVersion paramHashVersion)
  {
    return "";
  }
  
  public Node getImmediateChild(ChildKey paramChildKey)
  {
    return this;
  }
  
  public ChildKey getPredecessorChildKey(ChildKey paramChildKey)
  {
    return null;
  }
  
  public Node getPriority()
  {
    return this;
  }
  
  public ChildKey getSuccessorChildKey(ChildKey paramChildKey)
  {
    return null;
  }
  
  public Object getValue()
  {
    return null;
  }
  
  public Object getValue(boolean paramBoolean)
  {
    return null;
  }
  
  public boolean hasChild(ChildKey paramChildKey)
  {
    return false;
  }
  
  public int hashCode()
  {
    return 0;
  }
  
  public boolean isEmpty()
  {
    return true;
  }
  
  public boolean isLeafNode()
  {
    return false;
  }
  
  public Iterator iterator()
  {
    return Collections.emptyList().iterator();
  }
  
  public Iterator reverseIterator()
  {
    return Collections.emptyList().iterator();
  }
  
  public String toString()
  {
    return "<Empty Node>";
  }
  
  public Node updateChild(Path paramPath, Node paramNode)
  {
    if (paramPath.isEmpty()) {
      return paramNode;
    }
    ChildKey localChildKey = paramPath.getFront();
    return updateImmediateChild(localChildKey, getImmediateChild(localChildKey).updateChild(paramPath.popFront(), paramNode));
  }
  
  public Node updateImmediateChild(ChildKey paramChildKey, Node paramNode)
  {
    if (paramNode.isEmpty()) {
      return this;
    }
    if (paramChildKey.isPriorityChildName()) {
      return this;
    }
    return new ChildrenNode().updateImmediateChild(paramChildKey, paramNode);
  }
  
  public EmptyNode updatePriority(Node paramNode)
  {
    return this;
  }
}