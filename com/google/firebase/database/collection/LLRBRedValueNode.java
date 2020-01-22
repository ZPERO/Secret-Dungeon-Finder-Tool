package com.google.firebase.database.collection;

public class LLRBRedValueNode<K, V>
  extends LLRBValueNode<K, V>
{
  LLRBRedValueNode(Object paramObject1, Object paramObject2)
  {
    super(paramObject1, paramObject2, LLRBEmptyNode.getInstance(), LLRBEmptyNode.getInstance());
  }
  
  LLRBRedValueNode(Object paramObject1, Object paramObject2, LLRBNode paramLLRBNode1, LLRBNode paramLLRBNode2)
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
    return new LLRBRedValueNode(localObject, paramObject1, paramObject2, paramLLRBNode1);
  }
  
  protected final LLRBNode.Color getEntry()
  {
    return LLRBNode.Color.data;
  }
  
  public boolean isRed()
  {
    return true;
  }
  
  public int size()
  {
    return getLeft().size() + 1 + getRight().size();
  }
}
