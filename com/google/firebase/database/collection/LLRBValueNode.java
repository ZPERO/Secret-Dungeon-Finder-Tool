package com.google.firebase.database.collection;

import java.util.Comparator;

public abstract class LLRBValueNode<K, V>
  implements LLRBNode<K, V>
{
  private final K head;
  private LLRBNode<K, V> left;
  private final LLRBNode<K, V> right;
  private final V value;
  
  LLRBValueNode(Object paramObject1, Object paramObject2, LLRBNode paramLLRBNode1, LLRBNode paramLLRBNode2)
  {
    head = paramObject1;
    value = paramObject2;
    paramObject1 = paramLLRBNode1;
    if (paramLLRBNode1 == null) {
      paramObject1 = LLRBEmptyNode.getInstance();
    }
    left = paramObject1;
    paramObject1 = paramLLRBNode2;
    if (paramLLRBNode2 == null) {
      paramObject1 = LLRBEmptyNode.getInstance();
    }
    right = paramObject1;
  }
  
  private final LLRBValueNode add()
  {
    LLRBValueNode localLLRBValueNode = (LLRBValueNode)copy(null, null, LLRBNode.Color.data, left).right, null);
    return (LLRBValueNode)left.copy(null, null, getEntry(), null, localLLRBValueNode);
  }
  
  private final LLRBValueNode multiply()
  {
    LLRBValueNode localLLRBValueNode = (LLRBValueNode)copy(null, null, LLRBNode.Color.data, null, right).left);
    return (LLRBValueNode)right.copy(null, null, getEntry(), localLLRBValueNode, null);
  }
  
  private final LLRBValueNode remainder()
  {
    LLRBValueNode localLLRBValueNode2 = subtract();
    LLRBValueNode localLLRBValueNode1 = localLLRBValueNode2;
    if (localLLRBValueNode2.getRight().getLeft().isRed()) {
      localLLRBValueNode1 = localLLRBValueNode2.add(null, null, null, ((LLRBValueNode)localLLRBValueNode2.getRight()).add()).multiply().subtract();
    }
    return localLLRBValueNode1;
  }
  
  private final LLRBNode remove()
  {
    if (left.isEmpty()) {
      return LLRBEmptyNode.getInstance();
    }
    LLRBValueNode localLLRBValueNode = this;
    if (!getLeft().isRed())
    {
      localLLRBValueNode = this;
      if (!getLeft().getLeft().isRed()) {
        localLLRBValueNode = remainder();
      }
    }
    return localLLRBValueNode.add(null, null, ((LLRBValueNode)left).remove(), null).split();
  }
  
  private static LLRBNode.Color removeEntry(LLRBNode paramLLRBNode)
  {
    if (paramLLRBNode.isRed()) {
      return LLRBNode.Color.next;
    }
    return LLRBNode.Color.data;
  }
  
  private final LLRBValueNode split()
  {
    Object localObject2 = this;
    if (right.isRed())
    {
      localObject2 = this;
      if (!left.isRed()) {
        localObject2 = multiply();
      }
    }
    Object localObject1 = localObject2;
    if (left.isRed())
    {
      localObject1 = localObject2;
      if (left).left.isRed()) {
        localObject1 = ((LLRBValueNode)localObject2).add();
      }
    }
    localObject2 = localObject1;
    if (left.isRed())
    {
      localObject2 = localObject1;
      if (right.isRed()) {
        localObject2 = ((LLRBValueNode)localObject1).subtract();
      }
    }
    return localObject2;
  }
  
  private final LLRBValueNode subtract()
  {
    LLRBNode localLLRBNode1 = left;
    localLLRBNode1 = localLLRBNode1.copy(null, null, removeEntry(localLLRBNode1), null, null);
    LLRBNode localLLRBNode2 = right;
    localLLRBNode2 = localLLRBNode2.copy(null, null, removeEntry(localLLRBNode2), null, null);
    return (LLRBValueNode)copy(null, null, removeEntry(this), localLLRBNode1, localLLRBNode2);
  }
  
  protected abstract LLRBValueNode add(Object paramObject1, Object paramObject2, LLRBNode paramLLRBNode1, LLRBNode paramLLRBNode2);
  
  public LLRBValueNode copy(Object paramObject1, Object paramObject2, LLRBNode.Color paramColor, LLRBNode paramLLRBNode1, LLRBNode paramLLRBNode2)
  {
    Object localObject = paramObject1;
    if (paramObject1 == null) {
      localObject = head;
    }
    paramObject1 = paramObject2;
    if (paramObject2 == null) {
      paramObject1 = value;
    }
    paramObject2 = paramLLRBNode1;
    if (paramLLRBNode1 == null) {
      paramObject2 = left;
    }
    paramLLRBNode1 = paramLLRBNode2;
    if (paramLLRBNode2 == null) {
      paramLLRBNode1 = right;
    }
    if (paramColor == LLRBNode.Color.data) {
      return new LLRBRedValueNode(localObject, paramObject1, paramObject2, paramLLRBNode1);
    }
    return new LLRBBlackValueNode(localObject, paramObject1, paramObject2, paramLLRBNode1);
  }
  
  void delete(LLRBNode paramLLRBNode)
  {
    left = paramLLRBNode;
  }
  
  protected abstract LLRBNode.Color getEntry();
  
  public Object getKey()
  {
    return head;
  }
  
  public LLRBNode getLeft()
  {
    return left;
  }
  
  public LLRBNode getMax()
  {
    if (right.isEmpty()) {
      return this;
    }
    return right.getMax();
  }
  
  public LLRBNode getMin()
  {
    if (left.isEmpty()) {
      return this;
    }
    return left.getMin();
  }
  
  public LLRBNode getRight()
  {
    return right;
  }
  
  public Object getValue()
  {
    return value;
  }
  
  public void inOrderTraversal(LLRBNode.NodeVisitor paramNodeVisitor)
  {
    left.inOrderTraversal(paramNodeVisitor);
    paramNodeVisitor.visitEntry(head, value);
    right.inOrderTraversal(paramNodeVisitor);
  }
  
  public LLRBNode insert(Object paramObject1, Object paramObject2, Comparator paramComparator)
  {
    int i = paramComparator.compare(paramObject1, head);
    if (i < 0) {
      paramObject1 = add(null, null, left.insert(paramObject1, paramObject2, paramComparator), null);
    } else if (i == 0) {
      paramObject1 = add(paramObject1, paramObject2, null, null);
    } else {
      paramObject1 = add(null, null, null, right.insert(paramObject1, paramObject2, paramComparator));
    }
    return paramObject1.split();
  }
  
  public boolean isEmpty()
  {
    return false;
  }
  
  public LLRBNode remove(Object paramObject, Comparator paramComparator)
  {
    Object localObject1;
    if (paramComparator.compare(paramObject, head) < 0)
    {
      localObject1 = this;
      if (!left.isEmpty())
      {
        localObject1 = this;
        if (!left.isRed())
        {
          localObject1 = this;
          if (!left).left.isRed()) {
            localObject1 = remainder();
          }
        }
      }
      paramObject = ((LLRBValueNode)localObject1).add(null, null, left.remove(paramObject, paramComparator), null);
    }
    else
    {
      Object localObject2 = this;
      if (left.isRed()) {
        localObject2 = add();
      }
      localObject1 = localObject2;
      if (!right.isEmpty())
      {
        localObject1 = localObject2;
        if (!right.isRed())
        {
          localObject1 = localObject2;
          if (!right).left.isRed())
          {
            localObject2 = ((LLRBValueNode)localObject2).subtract();
            localObject1 = localObject2;
            if (((LLRBValueNode)localObject2).getLeft().getLeft().isRed()) {
              localObject1 = ((LLRBValueNode)localObject2).add().subtract();
            }
          }
        }
      }
      localObject2 = localObject1;
      if (paramComparator.compare(paramObject, head) == 0)
      {
        if (right.isEmpty()) {
          return LLRBEmptyNode.getInstance();
        }
        localObject2 = right.getMin();
        localObject2 = ((LLRBValueNode)localObject1).add(((LLRBNode)localObject2).getKey(), ((LLRBNode)localObject2).getValue(), null, ((LLRBValueNode)right).remove());
      }
      paramObject = ((LLRBValueNode)localObject2).add(null, null, null, right.remove(paramObject, paramComparator));
    }
    return paramObject.split();
  }
  
  public boolean shortCircuitingInOrderTraversal(LLRBNode.ShortCircuitingNodeVisitor paramShortCircuitingNodeVisitor)
  {
    if ((left.shortCircuitingInOrderTraversal(paramShortCircuitingNodeVisitor)) && (paramShortCircuitingNodeVisitor.shouldContinue(head, value))) {
      return right.shortCircuitingInOrderTraversal(paramShortCircuitingNodeVisitor);
    }
    return false;
  }
  
  public boolean shortCircuitingReverseOrderTraversal(LLRBNode.ShortCircuitingNodeVisitor paramShortCircuitingNodeVisitor)
  {
    if ((right.shortCircuitingReverseOrderTraversal(paramShortCircuitingNodeVisitor)) && (paramShortCircuitingNodeVisitor.shouldContinue(head, value))) {
      return left.shortCircuitingReverseOrderTraversal(paramShortCircuitingNodeVisitor);
    }
    return false;
  }
}
