package com.google.firebase.database.collection;

import java.util.Comparator;

public class LLRBEmptyNode<K, V>
  implements LLRBNode<K, V>
{
  private static final LLRBEmptyNode SFTP = new LLRBEmptyNode();
  
  private LLRBEmptyNode() {}
  
  public static LLRBEmptyNode getInstance()
  {
    return SFTP;
  }
  
  public LLRBNode copy(Object paramObject1, Object paramObject2, LLRBNode.Color paramColor, LLRBNode paramLLRBNode1, LLRBNode paramLLRBNode2)
  {
    return this;
  }
  
  public Object getKey()
  {
    return null;
  }
  
  public LLRBNode getLeft()
  {
    return this;
  }
  
  public LLRBNode getMax()
  {
    return this;
  }
  
  public LLRBNode getMin()
  {
    return this;
  }
  
  public LLRBNode getRight()
  {
    return this;
  }
  
  public Object getValue()
  {
    return null;
  }
  
  public void inOrderTraversal(LLRBNode.NodeVisitor paramNodeVisitor) {}
  
  public LLRBNode insert(Object paramObject1, Object paramObject2, Comparator paramComparator)
  {
    return new LLRBRedValueNode(paramObject1, paramObject2);
  }
  
  public boolean isEmpty()
  {
    return true;
  }
  
  public boolean isRed()
  {
    return false;
  }
  
  public LLRBNode remove(Object paramObject, Comparator paramComparator)
  {
    return this;
  }
  
  public boolean shortCircuitingInOrderTraversal(LLRBNode.ShortCircuitingNodeVisitor paramShortCircuitingNodeVisitor)
  {
    return true;
  }
  
  public boolean shortCircuitingReverseOrderTraversal(LLRBNode.ShortCircuitingNodeVisitor paramShortCircuitingNodeVisitor)
  {
    return true;
  }
  
  public int size()
  {
    return 0;
  }
}
