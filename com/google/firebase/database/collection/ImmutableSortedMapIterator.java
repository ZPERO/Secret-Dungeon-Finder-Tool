package com.google.firebase.database.collection;

import java.util.AbstractMap.SimpleEntry;
import java.util.Comparator;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Stack;

public class ImmutableSortedMapIterator<K, V>
  implements Iterator<Map.Entry<K, V>>
{
  private final boolean i;
  private final Stack<LLRBValueNode<K, V>> table = new Stack();
  
  ImmutableSortedMapIterator(LLRBNode paramLLRBNode, Object paramObject, Comparator paramComparator, boolean paramBoolean)
  {
    i = paramBoolean;
    while (!paramLLRBNode.isEmpty())
    {
      int j;
      if (paramObject != null)
      {
        Object localObject = paramLLRBNode.getKey();
        if (paramBoolean) {
          j = paramComparator.compare(paramObject, localObject);
        } else {
          j = paramComparator.compare(localObject, paramObject);
        }
      }
      else
      {
        j = 1;
      }
      if (j < 0)
      {
        if (paramBoolean) {}
      }
      else {
        do
        {
          paramLLRBNode = paramLLRBNode.getRight();
          break;
          if (j == 0)
          {
            table.push((LLRBValueNode)paramLLRBNode);
            return;
          }
          table.push((LLRBValueNode)paramLLRBNode);
        } while (paramBoolean);
      }
      paramLLRBNode = paramLLRBNode.getLeft();
    }
  }
  
  public boolean hasNext()
  {
    return table.size() > 0;
  }
  
  public Map.Entry next()
  {
    Object localObject = table;
    AbstractMap.SimpleEntry localSimpleEntry;
    try
    {
      localObject = ((Stack)localObject).pop();
      localObject = (LLRBValueNode)localObject;
      localSimpleEntry = new AbstractMap.SimpleEntry(((LLRBValueNode)localObject).getKey(), ((LLRBValueNode)localObject).getValue());
      boolean bool;
      Stack localStack;
      LLRBValueNode localLLRBValueNode;
      if (i) {
        for (localObject = ((LLRBValueNode)localObject).getLeft();; localObject = ((LLRBNode)localObject).getRight())
        {
          bool = ((LLRBNode)localObject).isEmpty();
          if (bool) {
            break;
          }
          localStack = table;
          localLLRBValueNode = (LLRBValueNode)localObject;
          localStack.push(localLLRBValueNode);
        }
      }
      for (localObject = ((LLRBValueNode)localObject).getRight();; localObject = ((LLRBNode)localObject).getLeft())
      {
        bool = ((LLRBNode)localObject).isEmpty();
        if (bool) {
          break;
        }
        localStack = table;
        localLLRBValueNode = (LLRBValueNode)localObject;
        localStack.push(localLLRBValueNode);
      }
      return localSimpleEntry;
    }
    catch (EmptyStackException localEmptyStackException)
    {
      for (;;) {}
    }
    localObject = new NoSuchElementException();
    throw ((Throwable)localObject);
    return localSimpleEntry;
  }
  
  public void remove()
  {
    throw new UnsupportedOperationException("remove called on immutable collection");
  }
}
