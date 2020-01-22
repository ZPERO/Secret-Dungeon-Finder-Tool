package com.google.firebase.database.collection;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public abstract class ImmutableSortedMap<K, V>
  implements Iterable<Map.Entry<K, V>>
{
  public ImmutableSortedMap() {}
  
  public abstract boolean containsKey(Object paramObject);
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if (!(paramObject instanceof ImmutableSortedMap)) {
      return false;
    }
    Object localObject = (ImmutableSortedMap)paramObject;
    if (!getComparator().equals(((ImmutableSortedMap)localObject).getComparator())) {
      return false;
    }
    if (size() != ((ImmutableSortedMap)localObject).size()) {
      return false;
    }
    paramObject = iterator();
    localObject = ((ImmutableSortedMap)localObject).iterator();
    while (paramObject.hasNext()) {
      if (!((Map.Entry)paramObject.next()).equals(((Iterator)localObject).next())) {
        return false;
      }
    }
    return true;
  }
  
  public abstract Object get(Object paramObject);
  
  public abstract Comparator getComparator();
  
  public abstract Object getMaxKey();
  
  public abstract Object getMinKey();
  
  public abstract Object getPredecessorKey(Object paramObject);
  
  public abstract Object getSuccessorKey(Object paramObject);
  
  public int hashCode()
  {
    int i = getComparator().hashCode();
    Iterator localIterator = iterator();
    while (localIterator.hasNext()) {
      i = i * 31 + ((Map.Entry)localIterator.next()).hashCode();
    }
    return i;
  }
  
  public abstract void inOrderTraversal(LLRBNode.NodeVisitor paramNodeVisitor);
  
  public abstract int indexOf(Object paramObject);
  
  public abstract ImmutableSortedMap insert(Object paramObject1, Object paramObject2);
  
  public abstract boolean isEmpty();
  
  public abstract Iterator iterator();
  
  public abstract Iterator iteratorFrom(Object paramObject);
  
  public abstract ImmutableSortedMap remove(Object paramObject);
  
  public abstract Iterator reverseIterator();
  
  public abstract Iterator reverseIteratorFrom(Object paramObject);
  
  public abstract int size();
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(getClass().getSimpleName());
    localStringBuilder.append("{");
    Iterator localIterator = iterator();
    int i = 1;
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      if (i != 0) {
        i = 0;
      } else {
        localStringBuilder.append(", ");
      }
      localStringBuilder.append("(");
      localStringBuilder.append(localEntry.getKey());
      localStringBuilder.append("=>");
      localStringBuilder.append(localEntry.getValue());
      localStringBuilder.append(")");
    }
    localStringBuilder.append("};");
    return localStringBuilder.toString();
  }
  
  public static class Builder
  {
    private static final KeyTranslator<?, ?> comparator = new KeyTranslator()
    {
      public Object translate(Object paramAnonymousObject)
      {
        return paramAnonymousObject;
      }
    };
    
    public Builder() {}
    
    public static ImmutableSortedMap buildFrom(List paramList, Map paramMap, KeyTranslator paramKeyTranslator, Comparator paramComparator)
    {
      if (paramList.size() < 25) {
        return LazyList.add(paramList, paramMap, paramKeyTranslator, paramComparator);
      }
      return Option.toString(paramList, paramMap, paramKeyTranslator, paramComparator);
    }
    
    public static ImmutableSortedMap emptyMap(Comparator paramComparator)
    {
      return new LazyList(paramComparator);
    }
    
    public static ImmutableSortedMap fromMap(Map paramMap, Comparator paramComparator)
    {
      if (paramMap.size() < 25) {
        return LazyList.add(paramMap, paramComparator);
      }
      return Cache.create(paramMap, paramComparator);
    }
    
    public static KeyTranslator identityTranslator()
    {
      return comparator;
    }
    
    public static abstract interface KeyTranslator<C, D>
    {
      public abstract Object translate(Object paramObject);
    }
  }
}
