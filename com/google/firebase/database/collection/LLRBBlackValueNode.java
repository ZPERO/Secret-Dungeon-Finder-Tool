package com.google.firebase.database.collection;

public class LLRBBlackValueNode<K, V>
  extends LLRBValueNode<K, V>
{
  private int size = -1;
  
  LLRBBlackValueNode(Object paramObject1, Object paramObject2, LLRBNode paramLLRBNode1, LLRBNode paramLLRBNode2)
  {
    super(paramObject1, paramObject2, paramLLRBNode1, paramLLRBNode2);
  }
  
  protected final LLRBValueNode add(Object paramObject1, Object paramObject2, LLRBNode paramLLRBNode1, LLRBNode paramLLRBNode2)
  {
    Object localObject = paramObject1;
    if (paramObject1 == null) {
      localObject = getKey();
    }
    paramObject1 = paramObject2;
    if (paramObject2 == null) {
      paramObject1 = getValue();
    }
    paramObject2 = paramLLRBNode1;
    if (paramLLRBNode1 == null) {
      paramObject2 = getLeft();
    }
    paramLLRBNode1 = paramLLRBNode2;
    if (paramLLRBNode2 == null) {
      paramLLRBNode1 = getRight();
    }
    return new LLRBBlackValueNode(localObject, paramObject1, paramObject2, paramLLRBNode1);
  }
  
  final void delete(LLRBNode paramLLRBNode)
  {
    if (size == -1)
    {
      super.delete(paramLLRBNode);
      return;
    }
    throw new IllegalStateException("Can't set left after using size");
  }
  
  protected final LLRBNode.Color getEntry()
  {
    return LLRBNode.Color.next;
  }
  
  public boolean isRed()
  {
    return false;
  }
  
  public int size()
  {
    if (size == -1) {
      size = (getLeft().size() + 1 + getRight().size());
    }
    return size;
  }
}
