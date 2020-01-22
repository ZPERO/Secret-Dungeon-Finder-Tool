package com.google.firebase.database.collection;

import java.util.Comparator;

public abstract interface LLRBNode<K, V>
{
  public abstract LLRBNode copy(Object paramObject1, Object paramObject2, Color paramColor, LLRBNode paramLLRBNode1, LLRBNode paramLLRBNode2);
  
  public abstract Object getKey();
  
  public abstract LLRBNode getLeft();
  
  public abstract LLRBNode getMax();
  
  public abstract LLRBNode getMin();
  
  public abstract LLRBNode getRight();
  
  public abstract Object getValue();
  
  public abstract void inOrderTraversal(NodeVisitor paramNodeVisitor);
  
  public abstract LLRBNode insert(Object paramObject1, Object paramObject2, Comparator paramComparator);
  
  public abstract boolean isEmpty();
  
  public abstract boolean isRed();
  
  public abstract LLRBNode remove(Object paramObject, Comparator paramComparator);
  
  public abstract boolean shortCircuitingInOrderTraversal(ShortCircuitingNodeVisitor paramShortCircuitingNodeVisitor);
  
  public abstract boolean shortCircuitingReverseOrderTraversal(ShortCircuitingNodeVisitor paramShortCircuitingNodeVisitor);
  
  public abstract int size();
  
  public static enum Color {}
  
  public static abstract class NodeVisitor<K, V>
    implements LLRBNode.ShortCircuitingNodeVisitor<K, V>
  {
    public NodeVisitor() {}
    
    public boolean shouldContinue(Object paramObject1, Object paramObject2)
    {
      visitEntry(paramObject1, paramObject2);
      return true;
    }
    
    public abstract void visitEntry(Object paramObject1, Object paramObject2);
  }
  
  public static abstract interface ShortCircuitingNodeVisitor<K, V>
  {
    public abstract boolean shouldContinue(Object paramObject1, Object paramObject2);
  }
}
