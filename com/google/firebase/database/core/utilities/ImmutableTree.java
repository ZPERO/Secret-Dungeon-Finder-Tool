package com.google.firebase.database.core.utilities;

import com.google.firebase.database.collection.ImmutableSortedMap;
import com.google.firebase.database.collection.ImmutableSortedMap.Builder;
import com.google.firebase.database.collection.StandardComparator;
import com.google.firebase.database.core.Path;
import com.google.firebase.database.snapshot.ChildKey;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public class ImmutableTree<T>
  implements Iterable<Map.Entry<Path, T>>
{
  private static final ImmutableTree EMPTY = new ImmutableTree(null, EMPTY_CHILDREN);
  private static final ImmutableSortedMap EMPTY_CHILDREN = ImmutableSortedMap.Builder.emptyMap(StandardComparator.getComparator(ChildKey.class));
  private final ImmutableSortedMap<ChildKey, ImmutableTree<T>> children;
  private final T value;
  
  public ImmutableTree(Object paramObject)
  {
    this(paramObject, EMPTY_CHILDREN);
  }
  
  public ImmutableTree(Object paramObject, ImmutableSortedMap paramImmutableSortedMap)
  {
    value = paramObject;
    children = paramImmutableSortedMap;
  }
  
  public static ImmutableTree emptyInstance()
  {
    return EMPTY;
  }
  
  private Object fold(Path paramPath, TreeVisitor paramTreeVisitor, Object paramObject)
  {
    Object localObject1 = children.iterator();
    while (((Iterator)localObject1).hasNext())
    {
      localObject2 = (Map.Entry)((Iterator)localObject1).next();
      paramObject = ((ImmutableTree)((Map.Entry)localObject2).getValue()).fold(paramPath.child((ChildKey)((Map.Entry)localObject2).getKey()), paramTreeVisitor, paramObject);
    }
    Object localObject2 = value;
    localObject1 = paramObject;
    if (localObject2 != null) {
      localObject1 = paramTreeVisitor.onNodeValue(paramPath, localObject2, paramObject);
    }
    return localObject1;
  }
  
  public boolean containsMatchingValue(Predicate paramPredicate)
  {
    Object localObject = value;
    if ((localObject != null) && (paramPredicate.evaluate(localObject))) {
      return true;
    }
    localObject = children.iterator();
    while (((Iterator)localObject).hasNext()) {
      if (((ImmutableTree)((Map.Entry)((Iterator)localObject).next()).getValue()).containsMatchingValue(paramPredicate)) {
        return true;
      }
    }
    return false;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if (paramObject != null)
    {
      if (getClass() != paramObject.getClass()) {
        return false;
      }
      paramObject = (ImmutableTree)paramObject;
      Object localObject = children;
      if (localObject != null)
      {
        if (!((ImmutableSortedMap)localObject).equals(children)) {
          return false;
        }
      }
      else if (children != null) {
        return false;
      }
      localObject = value;
      paramObject = value;
      if (localObject != null)
      {
        if (!localObject.equals(paramObject)) {
          return false;
        }
      }
      else
      {
        if (paramObject == null) {
          break label94;
        }
        return false;
      }
      return true;
    }
    else
    {
      return false;
    }
    label94:
    return true;
  }
  
  public Path findRootMostMatchingPath(Path paramPath, Predicate paramPredicate)
  {
    Object localObject = value;
    if ((localObject != null) && (paramPredicate.evaluate(localObject))) {
      return Path.getEmptyPath();
    }
    if (paramPath.isEmpty()) {
      return null;
    }
    localObject = paramPath.getFront();
    ImmutableTree localImmutableTree = (ImmutableTree)children.get(localObject);
    if (localImmutableTree != null)
    {
      paramPath = localImmutableTree.findRootMostMatchingPath(paramPath.popFront(), paramPredicate);
      if (paramPath != null) {
        return new Path(new ChildKey[] { localObject }).child(paramPath);
      }
    }
    return null;
  }
  
  public Path findRootMostPathWithValue(Path paramPath)
  {
    return findRootMostMatchingPath(paramPath, Predicate.TRUE);
  }
  
  public Object fold(Object paramObject, TreeVisitor paramTreeVisitor)
  {
    return fold(Path.getEmptyPath(), paramTreeVisitor, paramObject);
  }
  
  public void foreach(TreeVisitor paramTreeVisitor)
  {
    fold(Path.getEmptyPath(), paramTreeVisitor, null);
  }
  
  public ImmutableTree getChild(ChildKey paramChildKey)
  {
    paramChildKey = (ImmutableTree)children.get(paramChildKey);
    if (paramChildKey != null) {
      return paramChildKey;
    }
    return emptyInstance();
  }
  
  public ImmutableSortedMap getChildren()
  {
    return children;
  }
  
  public ImmutableTree getKey(Path paramPath, Object paramObject)
  {
    if (paramPath.isEmpty()) {
      return new ImmutableTree(paramObject, children);
    }
    ChildKey localChildKey = paramPath.getFront();
    ImmutableTree localImmutableTree2 = (ImmutableTree)children.get(localChildKey);
    ImmutableTree localImmutableTree1 = localImmutableTree2;
    if (localImmutableTree2 == null) {
      localImmutableTree1 = emptyInstance();
    }
    paramPath = localImmutableTree1.getKey(paramPath.popFront(), paramObject);
    paramPath = children.insert(localChildKey, paramPath);
    return new ImmutableTree(value, paramPath);
  }
  
  public Object getValue()
  {
    return value;
  }
  
  public int hashCode()
  {
    Object localObject = value;
    int j = 0;
    int i;
    if (localObject != null) {
      i = localObject.hashCode();
    } else {
      i = 0;
    }
    localObject = children;
    if (localObject != null) {
      j = ((ImmutableSortedMap)localObject).hashCode();
    }
    return i * 31 + j;
  }
  
  public boolean isEmpty()
  {
    return (value == null) && (children.isEmpty());
  }
  
  public Iterator iterator()
  {
    final ArrayList localArrayList = new ArrayList();
    foreach(new TreeVisitor()
    {
      public Void onNodeValue(Path paramAnonymousPath, Object paramAnonymousObject, Void paramAnonymousVoid)
      {
        localArrayList.add(new AbstractMap.SimpleImmutableEntry(paramAnonymousPath, paramAnonymousObject));
        return null;
      }
    });
    return localArrayList.iterator();
  }
  
  public Object leafMostValue(Path paramPath)
  {
    return leafMostValueMatching(paramPath, Predicate.TRUE);
  }
  
  public Object leafMostValueMatching(Path paramPath, Predicate paramPredicate)
  {
    Object localObject1 = value;
    if ((localObject1 != null) && (paramPredicate.evaluate(localObject1))) {
      localObject1 = value;
    } else {
      localObject1 = null;
    }
    Iterator localIterator = paramPath.iterator();
    paramPath = this;
    while (localIterator.hasNext())
    {
      Object localObject2 = (ChildKey)localIterator.next();
      localObject2 = (ImmutableTree)children.get(localObject2);
      if (localObject2 == null) {
        return localObject1;
      }
      Object localObject3 = value;
      paramPath = (Path)localObject2;
      if (localObject3 != null)
      {
        paramPath = (Path)localObject2;
        if (paramPredicate.evaluate(localObject3))
        {
          localObject1 = value;
          paramPath = (Path)localObject2;
        }
      }
    }
    return localObject1;
  }
  
  public Object readLong(Path paramPath)
  {
    if (paramPath.isEmpty()) {
      return value;
    }
    Object localObject = paramPath.getFront();
    localObject = (ImmutableTree)children.get(localObject);
    if (localObject != null) {
      return ((ImmutableTree)localObject).readLong(paramPath.popFront());
    }
    return null;
  }
  
  public ImmutableTree remove(Path paramPath)
  {
    if (paramPath.isEmpty())
    {
      if (children.isEmpty()) {
        return emptyInstance();
      }
      return new ImmutableTree(null, children);
    }
    ChildKey localChildKey = paramPath.getFront();
    ImmutableTree localImmutableTree = (ImmutableTree)children.get(localChildKey);
    if (localImmutableTree != null)
    {
      paramPath = localImmutableTree.remove(paramPath.popFront());
      if (paramPath.isEmpty()) {
        paramPath = children.remove(localChildKey);
      } else {
        paramPath = children.insert(localChildKey, paramPath);
      }
      if ((value == null) && (paramPath.isEmpty())) {
        return emptyInstance();
      }
      return new ImmutableTree(value, paramPath);
    }
    return this;
  }
  
  public Object rootMostValue(Path paramPath)
  {
    return rootMostValueMatching(paramPath, Predicate.TRUE);
  }
  
  public Object rootMostValueMatching(Path paramPath, Predicate paramPredicate)
  {
    Object localObject1 = value;
    if ((localObject1 != null) && (paramPredicate.evaluate(localObject1))) {
      return value;
    }
    Iterator localIterator = paramPath.iterator();
    paramPath = this;
    while (localIterator.hasNext())
    {
      localObject1 = (ChildKey)localIterator.next();
      localObject1 = (ImmutableTree)children.get(localObject1);
      if (localObject1 == null) {
        return null;
      }
      Object localObject2 = value;
      paramPath = (Path)localObject1;
      if (localObject2 != null)
      {
        paramPath = (Path)localObject1;
        if (paramPredicate.evaluate(localObject2)) {
          return value;
        }
      }
    }
    return null;
  }
  
  public ImmutableTree setTree(Path paramPath, ImmutableTree paramImmutableTree)
  {
    if (paramPath.isEmpty()) {
      return paramImmutableTree;
    }
    ChildKey localChildKey = paramPath.getFront();
    ImmutableTree localImmutableTree2 = (ImmutableTree)children.get(localChildKey);
    ImmutableTree localImmutableTree1 = localImmutableTree2;
    if (localImmutableTree2 == null) {
      localImmutableTree1 = emptyInstance();
    }
    paramPath = localImmutableTree1.setTree(paramPath.popFront(), paramImmutableTree);
    if (paramPath.isEmpty()) {
      paramPath = children.remove(localChildKey);
    } else {
      paramPath = children.insert(localChildKey, paramPath);
    }
    return new ImmutableTree(value, paramPath);
  }
  
  public ImmutableTree subtree(Path paramPath)
  {
    if (paramPath.isEmpty()) {
      return this;
    }
    Object localObject = paramPath.getFront();
    localObject = (ImmutableTree)children.get(localObject);
    if (localObject != null) {
      return ((ImmutableTree)localObject).subtree(paramPath.popFront());
    }
    return emptyInstance();
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("ImmutableTree { value=");
    localStringBuilder.append(getValue());
    localStringBuilder.append(", children={");
    Iterator localIterator = children.iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      localStringBuilder.append(((ChildKey)localEntry.getKey()).asString());
      localStringBuilder.append("=");
      localStringBuilder.append(localEntry.getValue());
    }
    localStringBuilder.append("} }");
    return localStringBuilder.toString();
  }
  
  public Collection values()
  {
    final ArrayList localArrayList = new ArrayList();
    foreach(new TreeVisitor()
    {
      public Void onNodeValue(Path paramAnonymousPath, Object paramAnonymousObject, Void paramAnonymousVoid)
      {
        localArrayList.add(paramAnonymousObject);
        return null;
      }
    });
    return localArrayList;
  }
  
  public static abstract interface TreeVisitor<T, R>
  {
    public abstract Object onNodeValue(Path paramPath, Object paramObject1, Object paramObject2);
  }
}
