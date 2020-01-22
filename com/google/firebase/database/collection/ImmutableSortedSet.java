package com.google.firebase.database.collection;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public class ImmutableSortedSet<T>
  implements Iterable<T>
{
  private final ImmutableSortedMap<T, Void> keys;
  
  private ImmutableSortedSet(ImmutableSortedMap paramImmutableSortedMap)
  {
    keys = paramImmutableSortedMap;
  }
  
  public ImmutableSortedSet(List paramList, Comparator paramComparator)
  {
    keys = ImmutableSortedMap.Builder.buildFrom(paramList, Collections.emptyMap(), ImmutableSortedMap.Builder.identityTranslator(), paramComparator);
  }
  
  public boolean contains(Object paramObject)
  {
    return keys.containsKey(paramObject);
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if (!(paramObject instanceof ImmutableSortedSet)) {
      return false;
    }
    paramObject = (ImmutableSortedSet)paramObject;
    return keys.equals(keys);
  }
  
  public Object getMaxEntry()
  {
    return keys.getMaxKey();
  }
  
  public Object getMinEntry()
  {
    return keys.getMinKey();
  }
  
  public Object getPredecessorEntry(Object paramObject)
  {
    return keys.getPredecessorKey(paramObject);
  }
  
  public int hashCode()
  {
    return keys.hashCode();
  }
  
  public int indexOf(Object paramObject)
  {
    return keys.indexOf(paramObject);
  }
  
  public ImmutableSortedSet insert(Object paramObject)
  {
    return new ImmutableSortedSet(keys.insert(paramObject, null));
  }
  
  public boolean isEmpty()
  {
    return keys.isEmpty();
  }
  
  public Iterator iterator()
  {
    return new WrappedEntryIterator(keys.iterator());
  }
  
  public Iterator iteratorFrom(Object paramObject)
  {
    return new WrappedEntryIterator(keys.iteratorFrom(paramObject));
  }
  
  public ImmutableSortedSet remove(Object paramObject)
  {
    paramObject = keys.remove(paramObject);
    if (paramObject == keys) {
      return this;
    }
    return new ImmutableSortedSet(paramObject);
  }
  
  public Iterator reverseIterator()
  {
    return new WrappedEntryIterator(keys.reverseIterator());
  }
  
  public Iterator reverseIteratorFrom(Object paramObject)
  {
    return new WrappedEntryIterator(keys.reverseIteratorFrom(paramObject));
  }
  
  public int size()
  {
    return keys.size();
  }
  
  private static class WrappedEntryIterator<T>
    implements Iterator<T>
  {
    private final Iterator<Map.Entry<T, Void>> val$backingEntries;
    
    public WrappedEntryIterator(Iterator paramIterator)
    {
      val$backingEntries = paramIterator;
    }
    
    public boolean hasNext()
    {
      return val$backingEntries.hasNext();
    }
    
    public Object next()
    {
      return ((Map.Entry)val$backingEntries.next()).getKey();
    }
    
    public void remove()
    {
      val$backingEntries.remove();
    }
  }
}
