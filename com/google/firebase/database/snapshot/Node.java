package com.google.firebase.database.snapshot;

import com.google.firebase.database.core.Path;
import java.util.Iterator;

public abstract interface Node
  extends Comparable<Node>, Iterable<NamedNode>
{
  public static final ChildrenNode MAX_NODE = new ChildrenNode()
  {
    public int compareTo(Node paramAnonymousNode)
    {
      if (paramAnonymousNode == this) {
        return 0;
      }
      return 1;
    }
    
    public boolean equals(Object paramAnonymousObject)
    {
      return paramAnonymousObject == this;
    }
    
    public Node getImmediateChild(ChildKey paramAnonymousChildKey)
    {
      if (paramAnonymousChildKey.isPriorityChildName()) {
        return getPriority();
      }
      return EmptyNode.Empty();
    }
    
    public Node getPriority()
    {
      return this;
    }
    
    public boolean hasChild(ChildKey paramAnonymousChildKey)
    {
      return false;
    }
    
    public boolean isEmpty()
    {
      return false;
    }
    
    public String toString()
    {
      return "<Max Node>";
    }
  };
  
  public abstract Node getChild(Path paramPath);
  
  public abstract int getChildCount();
  
  public abstract String getHash();
  
  public abstract String getHashRepresentation(HashVersion paramHashVersion);
  
  public abstract Node getImmediateChild(ChildKey paramChildKey);
  
  public abstract ChildKey getPredecessorChildKey(ChildKey paramChildKey);
  
  public abstract Node getPriority();
  
  public abstract ChildKey getSuccessorChildKey(ChildKey paramChildKey);
  
  public abstract Object getValue();
  
  public abstract Object getValue(boolean paramBoolean);
  
  public abstract boolean hasChild(ChildKey paramChildKey);
  
  public abstract boolean isEmpty();
  
  public abstract boolean isLeafNode();
  
  public abstract Iterator reverseIterator();
  
  public abstract Node updateChild(Path paramPath, Node paramNode);
  
  public abstract Node updateImmediateChild(ChildKey paramChildKey, Node paramNode);
  
  public abstract Node updatePriority(Node paramNode);
  
  public static enum HashVersion
  {
    ASSIGN,  BITWISE_OR;
  }
}
