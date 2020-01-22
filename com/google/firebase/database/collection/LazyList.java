package com.google.firebase.database.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class LazyList<K, V>
  extends ImmutableSortedMap<K, V>
{
  private final V[] buffer;
  private final Comparator<K> comparator;
  private final K[] size;
  
  public LazyList(Comparator paramComparator)
  {
    size = new Object[0];
    buffer = new Object[0];
    comparator = paramComparator;
  }
  
  private LazyList(Comparator paramComparator, Object[] paramArrayOfObject1, Object[] paramArrayOfObject2)
  {
    size = paramArrayOfObject1;
    buffer = paramArrayOfObject2;
    comparator = paramComparator;
  }
  
  public static LazyList add(List paramList, Map paramMap, ImmutableSortedMap.Builder.KeyTranslator paramKeyTranslator, Comparator paramComparator)
  {
    Collections.sort(paramList, paramComparator);
    int i = paramList.size();
    Object[] arrayOfObject1 = new Object[i];
    Object[] arrayOfObject2 = new Object[i];
    paramList = paramList.iterator();
    i = 0;
    while (paramList.hasNext())
    {
      Object localObject = paramList.next();
      arrayOfObject1[i] = localObject;
      arrayOfObject2[i] = paramMap.get(paramKeyTranslator.translate(localObject));
      i += 1;
    }
    return new LazyList(paramComparator, arrayOfObject1, arrayOfObject2);
  }
  
  public static LazyList add(Map paramMap, Comparator paramComparator)
  {
    return add(new ArrayList(paramMap.keySet()), paramMap, ImmutableSortedMap.Builder.identityTranslator(), paramComparator);
  }
  
  private static Object[] add(Object[] paramArrayOfObject, int paramInt, Object paramObject)
  {
    int i = paramArrayOfObject.length + 1;
    Object[] arrayOfObject = new Object[i];
    System.arraycopy(paramArrayOfObject, 0, arrayOfObject, 0, paramInt);
    arrayOfObject[paramInt] = paramObject;
    System.arraycopy(paramArrayOfObject, paramInt, arrayOfObject, paramInt + 1, i - paramInt - 1);
    return arrayOfObject;
  }
  
  private final int binarySearch(Object paramObject)
  {
    Object[] arrayOfObject = size;
    int k = arrayOfObject.length;
    int i = 0;
    int j = 0;
    while (i < k)
    {
      Object localObject = arrayOfObject[i];
      if (comparator.compare(paramObject, localObject) == 0) {
        return j;
      }
      j += 1;
      i += 1;
    }
    return -1;
  }
  
  private static Object[] get(Object[] paramArrayOfObject, int paramInt)
  {
    int i = paramArrayOfObject.length - 1;
    Object[] arrayOfObject = new Object[i];
    System.arraycopy(paramArrayOfObject, 0, arrayOfObject, 0, paramInt);
    System.arraycopy(paramArrayOfObject, paramInt + 1, arrayOfObject, paramInt, i - paramInt);
    return arrayOfObject;
  }
  
  private static Object[] get(Object[] paramArrayOfObject, int paramInt, Object paramObject)
  {
    int i = paramArrayOfObject.length;
    Object[] arrayOfObject = new Object[i];
    System.arraycopy(paramArrayOfObject, 0, arrayOfObject, 0, i);
    arrayOfObject[paramInt] = paramObject;
    return arrayOfObject;
  }
  
  private final int getEntry(Object paramObject)
  {
    int i = 0;
    for (;;)
    {
      Object[] arrayOfObject = size;
      if ((i >= arrayOfObject.length) || (comparator.compare(arrayOfObject[i], paramObject) >= 0)) {
        break;
      }
      i += 1;
    }
    return i;
  }
  
  private final Iterator iterate(int paramInt, boolean paramBoolean)
  {
    return new EagerForeignCollection.1(this, paramInt, paramBoolean);
  }
  
  public final boolean containsKey(Object paramObject)
  {
    return binarySearch(paramObject) != -1;
  }
  
  public final Object get(Object paramObject)
  {
    int i = binarySearch(paramObject);
    if (i != -1) {
      return buffer[i];
    }
    return null;
  }
  
  public final Comparator getComparator()
  {
    return comparator;
  }
  
  public final Object getMaxKey()
  {
    Object[] arrayOfObject = size;
    if (arrayOfObject.length > 0) {
      return arrayOfObject[(arrayOfObject.length - 1)];
    }
    return null;
  }
  
  public final Object getMinKey()
  {
    Object[] arrayOfObject = size;
    if (arrayOfObject.length > 0) {
      return arrayOfObject[0];
    }
    return null;
  }
  
  public final Object getPredecessorKey(Object paramObject)
  {
    int i = binarySearch(paramObject);
    if (i != -1)
    {
      if (i > 0) {
        return size[(i - 1)];
      }
      return null;
    }
    throw new IllegalArgumentException("Can't find predecessor of nonexistent key");
  }
  
  public final Object getSuccessorKey(Object paramObject)
  {
    int i = binarySearch(paramObject);
    if (i != -1)
    {
      paramObject = size;
      if (i < paramObject.length - 1) {
        return paramObject[(i + 1)];
      }
      return null;
    }
    throw new IllegalArgumentException("Can't find successor of nonexistent key");
  }
  
  public final void inOrderTraversal(LLRBNode.NodeVisitor paramNodeVisitor)
  {
    int i = 0;
    for (;;)
    {
      Object[] arrayOfObject = size;
      if (i >= arrayOfObject.length) {
        break;
      }
      paramNodeVisitor.visitEntry(arrayOfObject[i], buffer[i]);
      i += 1;
    }
  }
  
  public final int indexOf(Object paramObject)
  {
    return binarySearch(paramObject);
  }
  
  public final ImmutableSortedMap insert(Object paramObject1, Object paramObject2)
  {
    int i = binarySearch(paramObject1);
    if (i != -1)
    {
      if ((size[i] == paramObject1) && (buffer[i] == paramObject2)) {
        return this;
      }
      paramObject1 = get(size, i, paramObject1);
      paramObject2 = get(buffer, i, paramObject2);
      return new LazyList(comparator, paramObject1, paramObject2);
    }
    Object localObject = size;
    if (localObject.length > 25)
    {
      localObject = new HashMap(localObject.length + 1);
      i = 0;
      for (;;)
      {
        Object[] arrayOfObject = size;
        if (i >= arrayOfObject.length) {
          break;
        }
        ((Map)localObject).put(arrayOfObject[i], buffer[i]);
        i += 1;
      }
      ((Map)localObject).put(paramObject1, paramObject2);
      return Cache.create((Map)localObject, comparator);
    }
    i = getEntry(paramObject1);
    paramObject1 = add(size, i, paramObject1);
    paramObject2 = add(buffer, i, paramObject2);
    return new LazyList(comparator, paramObject1, paramObject2);
  }
  
  public final boolean isEmpty()
  {
    return size.length == 0;
  }
  
  public final Iterator iterator()
  {
    return iterate(0, false);
  }
  
  public final Iterator iteratorFrom(Object paramObject)
  {
    return iterate(getEntry(paramObject), false);
  }
  
  public final ImmutableSortedMap remove(Object paramObject)
  {
    int i = binarySearch(paramObject);
    if (i == -1) {
      return this;
    }
    paramObject = get(size, i);
    Object[] arrayOfObject = get(buffer, i);
    return new LazyList(comparator, paramObject, arrayOfObject);
  }
  
  public final Iterator reverseIterator()
  {
    return iterate(size.length - 1, true);
  }
  
  public final Iterator reverseIteratorFrom(Object paramObject)
  {
    int i = getEntry(paramObject);
    Object[] arrayOfObject = size;
    if ((i < arrayOfObject.length) && (comparator.compare(arrayOfObject[i], paramObject) == 0)) {
      return iterate(i, true);
    }
    return iterate(i - 1, true);
  }
  
  public final int size()
  {
    return size.length;
  }
}
