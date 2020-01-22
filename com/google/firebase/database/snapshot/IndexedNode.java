package com.google.firebase.database.snapshot;

import com.google.android.android.common.internal.Objects;
import com.google.firebase.database.collection.ImmutableSortedSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class IndexedNode
  implements Iterable<NamedNode>
{
  private static final ImmutableSortedSet<NamedNode> FALLBACK_INDEX = new ImmutableSortedSet(Collections.emptyList(), null);
  private final Index index;
  private ImmutableSortedSet<NamedNode> indexed;
  private final Node node;
  
  private IndexedNode(Node paramNode, Index paramIndex)
  {
    index = paramIndex;
    node = paramNode;
    indexed = null;
  }
  
  private IndexedNode(Node paramNode, Index paramIndex, ImmutableSortedSet paramImmutableSortedSet)
  {
    index = paramIndex;
    node = paramNode;
    indexed = paramImmutableSortedSet;
  }
  
  private void ensureIndexed()
  {
    if (indexed == null)
    {
      if (index.equals(KeyIndex.getInstance()))
      {
        indexed = FALLBACK_INDEX;
        return;
      }
      ArrayList localArrayList = new ArrayList();
      Iterator localIterator = node.iterator();
      int i = 0;
      while (localIterator.hasNext())
      {
        NamedNode localNamedNode = (NamedNode)localIterator.next();
        if ((i == 0) && (!index.isDefinedOn(localNamedNode.getNode()))) {
          i = 0;
        } else {
          i = 1;
        }
        localArrayList.add(new NamedNode(localNamedNode.getName(), localNamedNode.getNode()));
      }
      if (i != 0)
      {
        indexed = new ImmutableSortedSet(localArrayList, index);
        return;
      }
      indexed = FALLBACK_INDEX;
    }
  }
  
  public static IndexedNode from(Node paramNode)
  {
    return new IndexedNode(paramNode, PriorityIndex.getInstance());
  }
  
  public static IndexedNode from(Node paramNode, Index paramIndex)
  {
    return new IndexedNode(paramNode, paramIndex);
  }
  
  public NamedNode getFirstChild()
  {
    if (!(node instanceof ChildrenNode)) {
      return null;
    }
    ensureIndexed();
    if (Objects.equal(indexed, FALLBACK_INDEX))
    {
      ChildKey localChildKey = ((ChildrenNode)node).getFirstChildKey();
      return new NamedNode(localChildKey, node.getImmediateChild(localChildKey));
    }
    return (NamedNode)indexed.getMinEntry();
  }
  
  public NamedNode getLastChild()
  {
    if (!(node instanceof ChildrenNode)) {
      return null;
    }
    ensureIndexed();
    if (Objects.equal(indexed, FALLBACK_INDEX))
    {
      ChildKey localChildKey = ((ChildrenNode)node).getLastChildKey();
      return new NamedNode(localChildKey, node.getImmediateChild(localChildKey));
    }
    return (NamedNode)indexed.getMaxEntry();
  }
  
  public Node getNode()
  {
    return node;
  }
  
  public ChildKey getPredecessorChildName(ChildKey paramChildKey, Node paramNode, Index paramIndex)
  {
    if ((!index.equals(KeyIndex.getInstance())) && (!index.equals(paramIndex))) {
      throw new IllegalArgumentException("Index not available in IndexedNode!");
    }
    ensureIndexed();
    if (Objects.equal(indexed, FALLBACK_INDEX)) {
      return node.getPredecessorChildKey(paramChildKey);
    }
    paramChildKey = (NamedNode)indexed.getPredecessorEntry(new NamedNode(paramChildKey, paramNode));
    if (paramChildKey != null) {
      return paramChildKey.getName();
    }
    return null;
  }
  
  public boolean hasIndex(Index paramIndex)
  {
    return index == paramIndex;
  }
  
  public Iterator iterator()
  {
    ensureIndexed();
    if (Objects.equal(indexed, FALLBACK_INDEX)) {
      return node.iterator();
    }
    return indexed.iterator();
  }
  
  public Iterator reverseIterator()
  {
    ensureIndexed();
    if (Objects.equal(indexed, FALLBACK_INDEX)) {
      return node.reverseIterator();
    }
    return indexed.reverseIterator();
  }
  
  public IndexedNode updateChild(ChildKey paramChildKey, Node paramNode)
  {
    Node localNode = node.updateImmediateChild(paramChildKey, paramNode);
    if ((Objects.equal(indexed, FALLBACK_INDEX)) && (!index.isDefinedOn(paramNode))) {
      return new IndexedNode(localNode, index, FALLBACK_INDEX);
    }
    Object localObject = indexed;
    if ((localObject != null) && (!Objects.equal(localObject, FALLBACK_INDEX)))
    {
      localObject = node.getImmediateChild(paramChildKey);
      ImmutableSortedSet localImmutableSortedSet = indexed.remove(new NamedNode(paramChildKey, (Node)localObject));
      localObject = localImmutableSortedSet;
      if (!paramNode.isEmpty()) {
        localObject = localImmutableSortedSet.insert(new NamedNode(paramChildKey, paramNode));
      }
      return new IndexedNode(localNode, index, (ImmutableSortedSet)localObject);
    }
    return new IndexedNode(localNode, index, null);
  }
  
  public IndexedNode updatePriority(Node paramNode)
  {
    return new IndexedNode(node.updatePriority(paramNode), index, indexed);
  }
}
