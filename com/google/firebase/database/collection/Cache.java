package com.google.firebase.database.collection;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;

public final class Cache<K, V>
  extends ImmutableSortedMap<K, V>
{
  private Comparator<K> head;
  private LLRBNode<K, V> this$0;
  
  private Cache(LLRBNode paramLLRBNode, Comparator paramComparator)
  {
    this$0 = paramLLRBNode;
    head = paramComparator;
  }
  
  public static Cache create(Map paramMap, Comparator paramComparator)
  {
    return Option.toString(new ArrayList(paramMap.keySet()), paramMap, ImmutableSortedMap.Builder.identityTranslator(), paramComparator);
  }
  
  private final LLRBNode toString(Object paramObject)
  {
    LLRBNode localLLRBNode = this$0;
    while (!localLLRBNode.isEmpty())
    {
      int i = head.compare(paramObject, localLLRBNode.getKey());
      if (i < 0)
      {
        localLLRBNode = localLLRBNode.getLeft();
      }
      else
      {
        if (i == 0) {
          return localLLRBNode;
        }
        localLLRBNode = localLLRBNode.getRight();
      }
    }
    return null;
  }
  
  public final boolean containsKey(Object paramObject)
  {
    return toString(paramObject) != null;
  }
  
  public final Object get(Object paramObject)
  {
    paramObject = toString(paramObject);
    if (paramObject != null) {
      return paramObject.getValue();
    }
    return null;
  }
  
  public final Comparator getComparator()
  {
    return head;
  }
  
  public final Object getMaxKey()
  {
    return this$0.getMax().getKey();
  }
  
  public final Object getMinKey()
  {
    return this$0.getMin().getKey();
  }
  
  public final Object getPredecessorKey(Object paramObject)
  {
    Object localObject1 = this$0;
    Object localObject2 = null;
    while (!((LLRBNode)localObject1).isEmpty())
    {
      int i = head.compare(paramObject, ((LLRBNode)localObject1).getKey());
      if (i == 0)
      {
        if (!((LLRBNode)localObject1).getLeft().isEmpty())
        {
          for (paramObject = ((LLRBNode)localObject1).getLeft(); !paramObject.getRight().isEmpty(); paramObject = paramObject.getRight()) {}
          return paramObject.getKey();
        }
        if (localObject2 != null) {
          return localObject2.getKey();
        }
        return null;
      }
      if (i < 0)
      {
        localObject1 = ((LLRBNode)localObject1).getLeft();
      }
      else
      {
        localObject2 = localObject1;
        localObject1 = ((LLRBNode)localObject1).getRight();
      }
    }
    paramObject = String.valueOf(paramObject);
    localObject1 = new StringBuilder(String.valueOf(paramObject).length() + 50);
    ((StringBuilder)localObject1).append("Couldn't find predecessor key of non-present key: ");
    ((StringBuilder)localObject1).append(paramObject);
    paramObject = new IllegalArgumentException(((StringBuilder)localObject1).toString());
    throw paramObject;
  }
  
  public final Object getSuccessorKey(Object paramObject)
  {
    Object localObject1 = this$0;
    Object localObject2 = null;
    while (!((LLRBNode)localObject1).isEmpty())
    {
      int i = head.compare(((LLRBNode)localObject1).getKey(), paramObject);
      if (i == 0)
      {
        if (!((LLRBNode)localObject1).getRight().isEmpty())
        {
          for (paramObject = ((LLRBNode)localObject1).getRight(); !paramObject.getLeft().isEmpty(); paramObject = paramObject.getLeft()) {}
          return paramObject.getKey();
        }
        if (localObject2 != null) {
          return localObject2.getKey();
        }
        return null;
      }
      if (i < 0)
      {
        localObject1 = ((LLRBNode)localObject1).getRight();
      }
      else
      {
        localObject2 = localObject1;
        localObject1 = ((LLRBNode)localObject1).getLeft();
      }
    }
    paramObject = String.valueOf(paramObject);
    localObject1 = new StringBuilder(String.valueOf(paramObject).length() + 48);
    ((StringBuilder)localObject1).append("Couldn't find successor key of non-present key: ");
    ((StringBuilder)localObject1).append(paramObject);
    paramObject = new IllegalArgumentException(((StringBuilder)localObject1).toString());
    throw paramObject;
  }
  
  public final void inOrderTraversal(LLRBNode.NodeVisitor paramNodeVisitor)
  {
    this$0.inOrderTraversal(paramNodeVisitor);
  }
  
  public final int indexOf(Object paramObject)
  {
    LLRBNode localLLRBNode = this$0;
    int i = 0;
    while (!localLLRBNode.isEmpty())
    {
      int j = head.compare(paramObject, localLLRBNode.getKey());
      if (j == 0) {
        return i + localLLRBNode.getLeft().size();
      }
      if (j < 0)
      {
        localLLRBNode = localLLRBNode.getLeft();
      }
      else
      {
        i += localLLRBNode.getLeft().size() + 1;
        localLLRBNode = localLLRBNode.getRight();
      }
    }
    return -1;
  }
  
  public final ImmutableSortedMap insert(Object paramObject1, Object paramObject2)
  {
    return new Cache(this$0.insert(paramObject1, paramObject2, head).copy(null, null, LLRBNode.Color.next, null, null), head);
  }
  
  public final boolean isEmpty()
  {
    return this$0.isEmpty();
  }
  
  public final Iterator iterator()
  {
    return new ImmutableSortedMapIterator(this$0, null, head, false);
  }
  
  public final Iterator iteratorFrom(Object paramObject)
  {
    return new ImmutableSortedMapIterator(this$0, paramObject, head, false);
  }
  
  public final ImmutableSortedMap remove(Object paramObject)
  {
    if (!containsKey(paramObject)) {
      return this;
    }
    return new Cache(this$0.remove(paramObject, head).copy(null, null, LLRBNode.Color.next, null, null), head);
  }
  
  public final Iterator reverseIterator()
  {
    return new ImmutableSortedMapIterator(this$0, null, head, true);
  }
  
  public final Iterator reverseIteratorFrom(Object paramObject)
  {
    return new ImmutableSortedMapIterator(this$0, paramObject, head, true);
  }
  
  public final int size()
  {
    return this$0.size();
  }
}
