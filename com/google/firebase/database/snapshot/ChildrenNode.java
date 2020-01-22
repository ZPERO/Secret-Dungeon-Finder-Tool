package com.google.firebase.database.snapshot;

import com.google.firebase.database.collection.ImmutableSortedMap;
import com.google.firebase.database.collection.ImmutableSortedMap.Builder;
import com.google.firebase.database.collection.LLRBNode.NodeVisitor;
import com.google.firebase.database.core.Path;
import com.google.firebase.database.core.utilities.Utilities;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ChildrenNode
  implements Node
{
  public static Comparator<ChildKey> NAME_ONLY_COMPARATOR = new Comparator()
  {
    public int compare(ChildKey paramAnonymousChildKey1, ChildKey paramAnonymousChildKey2)
    {
      return paramAnonymousChildKey1.compareTo(paramAnonymousChildKey2);
    }
  };
  private final ImmutableSortedMap<ChildKey, Node> children;
  private String lazyHash = null;
  private final Node priority;
  
  protected ChildrenNode()
  {
    children = ImmutableSortedMap.Builder.emptyMap(NAME_ONLY_COMPARATOR);
    priority = PriorityUtilities.NullPriority();
  }
  
  protected ChildrenNode(ImmutableSortedMap paramImmutableSortedMap, Node paramNode)
  {
    if ((paramImmutableSortedMap.isEmpty()) && (!paramNode.isEmpty())) {
      throw new IllegalArgumentException("Can't create empty ChildrenNode with priority!");
    }
    priority = paramNode;
    children = paramImmutableSortedMap;
  }
  
  private static void addIndentation(StringBuilder paramStringBuilder, int paramInt)
  {
    int i = 0;
    while (i < paramInt)
    {
      paramStringBuilder.append(" ");
      i += 1;
    }
  }
  
  private void toString(StringBuilder paramStringBuilder, int paramInt)
  {
    if ((children.isEmpty()) && (priority.isEmpty()))
    {
      paramStringBuilder.append("{ }");
      return;
    }
    paramStringBuilder.append("{\n");
    Iterator localIterator = children.iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      int i = paramInt + 2;
      addIndentation(paramStringBuilder, i);
      paramStringBuilder.append(((ChildKey)localEntry.getKey()).asString());
      paramStringBuilder.append("=");
      if ((localEntry.getValue() instanceof ChildrenNode)) {
        ((ChildrenNode)localEntry.getValue()).toString(paramStringBuilder, i);
      } else {
        paramStringBuilder.append(((Node)localEntry.getValue()).toString());
      }
      paramStringBuilder.append("\n");
    }
    if (!priority.isEmpty())
    {
      addIndentation(paramStringBuilder, paramInt + 2);
      paramStringBuilder.append(".priority=");
      paramStringBuilder.append(priority.toString());
      paramStringBuilder.append("\n");
    }
    addIndentation(paramStringBuilder, paramInt);
    paramStringBuilder.append("}");
  }
  
  public int compareTo(Node paramNode)
  {
    if (isEmpty())
    {
      if (paramNode.isEmpty()) {
        return 0;
      }
      return -1;
    }
    if (paramNode.isLeafNode()) {
      return 1;
    }
    if (paramNode.isEmpty()) {
      return 1;
    }
    if (paramNode == Node.MAX_NODE) {
      return -1;
    }
    return 0;
  }
  
  public boolean equals(Object paramObject)
  {
    if (paramObject == null) {
      return false;
    }
    if (paramObject == this) {
      return true;
    }
    if (!(paramObject instanceof ChildrenNode)) {
      return false;
    }
    Object localObject = (ChildrenNode)paramObject;
    if (!getPriority().equals(((ChildrenNode)localObject).getPriority())) {
      return false;
    }
    if (children.size() != children.size()) {
      return false;
    }
    paramObject = children.iterator();
    localObject = children.iterator();
    while ((paramObject.hasNext()) && (((Iterator)localObject).hasNext()))
    {
      Map.Entry localEntry1 = (Map.Entry)paramObject.next();
      Map.Entry localEntry2 = (Map.Entry)((Iterator)localObject).next();
      if (!((ChildKey)localEntry1.getKey()).equals(localEntry2.getKey())) {
        break label198;
      }
      if (!((Node)localEntry1.getValue()).equals(localEntry2.getValue())) {
        return false;
      }
    }
    if ((!paramObject.hasNext()) && (!((Iterator)localObject).hasNext())) {
      return true;
    }
    paramObject = new IllegalStateException("Something went wrong internally.");
    throw paramObject;
    label198:
    return false;
  }
  
  public void forEachChild(ChildVisitor paramChildVisitor)
  {
    forEachChild(paramChildVisitor, false);
  }
  
  public void forEachChild(final ChildVisitor paramChildVisitor, boolean paramBoolean)
  {
    if ((paramBoolean) && (!getPriority().isEmpty()))
    {
      children.inOrderTraversal(new LLRBNode.NodeVisitor()
      {
        boolean passedPriorityKey = false;
        
        public void visitEntry(ChildKey paramAnonymousChildKey, Node paramAnonymousNode)
        {
          if ((!passedPriorityKey) && (paramAnonymousChildKey.compareTo(ChildKey.getPriorityKey()) > 0))
          {
            passedPriorityKey = true;
            paramChildVisitor.visitChild(ChildKey.getPriorityKey(), getPriority());
          }
          paramChildVisitor.visitChild(paramAnonymousChildKey, paramAnonymousNode);
        }
      });
      return;
    }
    children.inOrderTraversal(paramChildVisitor);
  }
  
  public Node getChild(Path paramPath)
  {
    ChildKey localChildKey = paramPath.getFront();
    if (localChildKey == null) {
      return this;
    }
    return getImmediateChild(localChildKey).getChild(paramPath.popFront());
  }
  
  public int getChildCount()
  {
    return children.size();
  }
  
  public ChildKey getFirstChildKey()
  {
    return (ChildKey)children.getMinKey();
  }
  
  public String getHash()
  {
    if (lazyHash == null)
    {
      String str = getHashRepresentation(Node.HashVersion.ASSIGN);
      if (str.isEmpty()) {
        str = "";
      } else {
        str = Utilities.sha1HexDigest(str);
      }
      lazyHash = str;
    }
    return lazyHash;
  }
  
  public String getHashRepresentation(Node.HashVersion paramHashVersion)
  {
    if (paramHashVersion == Node.HashVersion.ASSIGN)
    {
      paramHashVersion = new StringBuilder();
      if (!priority.isEmpty())
      {
        paramHashVersion.append("priority:");
        paramHashVersion.append(priority.getHashRepresentation(Node.HashVersion.ASSIGN));
        paramHashVersion.append(":");
      }
      Object localObject1 = new ArrayList();
      Object localObject2 = iterator();
      Object localObject3;
      for (int i = 0;; i = 1)
      {
        if (!((Iterator)localObject2).hasNext()) {
          break label132;
        }
        localObject3 = (NamedNode)((Iterator)localObject2).next();
        ((List)localObject1).add(localObject3);
        if ((i == 0) && (((NamedNode)localObject3).getNode().getPriority().isEmpty())) {
          break;
        }
      }
      label132:
      if (i != 0) {
        Collections.sort((List)localObject1, PriorityIndex.getInstance());
      }
      localObject1 = ((List)localObject1).iterator();
      while (((Iterator)localObject1).hasNext())
      {
        localObject2 = (NamedNode)((Iterator)localObject1).next();
        localObject3 = ((NamedNode)localObject2).getNode().getHash();
        if (!((String)localObject3).equals(""))
        {
          paramHashVersion.append(":");
          paramHashVersion.append(((NamedNode)localObject2).getName().asString());
          paramHashVersion.append(":");
          paramHashVersion.append((String)localObject3);
        }
      }
      return paramHashVersion.toString();
    }
    paramHashVersion = new IllegalArgumentException("Hashes on children nodes only supported for V1");
    throw paramHashVersion;
  }
  
  public Node getImmediateChild(ChildKey paramChildKey)
  {
    if ((paramChildKey.isPriorityChildName()) && (!priority.isEmpty())) {
      return priority;
    }
    if (children.containsKey(paramChildKey)) {
      return (Node)children.get(paramChildKey);
    }
    return EmptyNode.Empty();
  }
  
  public ChildKey getLastChildKey()
  {
    return (ChildKey)children.getMaxKey();
  }
  
  public ChildKey getPredecessorChildKey(ChildKey paramChildKey)
  {
    return (ChildKey)children.getPredecessorKey(paramChildKey);
  }
  
  public Node getPriority()
  {
    return priority;
  }
  
  public ChildKey getSuccessorChildKey(ChildKey paramChildKey)
  {
    return (ChildKey)children.getSuccessorKey(paramChildKey);
  }
  
  public Object getValue()
  {
    return getValue(false);
  }
  
  public Object getValue(boolean paramBoolean)
  {
    if (isEmpty()) {
      return null;
    }
    HashMap localHashMap = new HashMap();
    Object localObject1 = children.iterator();
    int m = 0;
    int j = 0;
    int k = 1;
    int i = 0;
    Object localObject2;
    while (((Iterator)localObject1).hasNext())
    {
      localObject2 = (Map.Entry)((Iterator)localObject1).next();
      String str = ((ChildKey)((Map.Entry)localObject2).getKey()).asString();
      localHashMap.put(str, ((Node)((Map.Entry)localObject2).getValue()).getValue(paramBoolean));
      int n = j + 1;
      j = n;
      if (k != 0)
      {
        if ((str.length() <= 1) || (str.charAt(0) != '0'))
        {
          localObject2 = Utilities.tryParseInt(str);
          if ((localObject2 != null) && (((Integer)localObject2).intValue() >= 0))
          {
            j = n;
            if (((Integer)localObject2).intValue() <= i) {
              continue;
            }
            i = ((Integer)localObject2).intValue();
            j = n;
            continue;
          }
        }
        k = 0;
        j = n;
      }
    }
    if ((!paramBoolean) && (k != 0) && (i < j * 2))
    {
      localObject1 = new ArrayList(i + 1);
      j = m;
      while (j <= i)
      {
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append("");
        ((StringBuilder)localObject2).append(j);
        ((List)localObject1).add(localHashMap.get(((StringBuilder)localObject2).toString()));
        j += 1;
      }
      return localObject1;
    }
    if ((paramBoolean) && (!priority.isEmpty())) {
      localHashMap.put(".priority", priority.getValue());
    }
    return localHashMap;
  }
  
  public boolean hasChild(ChildKey paramChildKey)
  {
    return getImmediateChild(paramChildKey).isEmpty() ^ true;
  }
  
  public int hashCode()
  {
    Iterator localIterator = iterator();
    NamedNode localNamedNode;
    for (int i = 0; localIterator.hasNext(); i = (i * 31 + localNamedNode.getName().hashCode()) * 17 + localNamedNode.getNode().hashCode()) {
      localNamedNode = (NamedNode)localIterator.next();
    }
    return i;
  }
  
  public boolean isEmpty()
  {
    return children.isEmpty();
  }
  
  public boolean isLeafNode()
  {
    return false;
  }
  
  public Iterator iterator()
  {
    return new NamedNodeIterator(children.iterator());
  }
  
  public Iterator reverseIterator()
  {
    return new NamedNodeIterator(children.reverseIterator());
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    toString(localStringBuilder, 0);
    return localStringBuilder.toString();
  }
  
  public Node updateChild(Path paramPath, Node paramNode)
  {
    ChildKey localChildKey = paramPath.getFront();
    if (localChildKey == null) {
      return paramNode;
    }
    if (localChildKey.isPriorityChildName()) {
      return updatePriority(paramNode);
    }
    return updateImmediateChild(localChildKey, getImmediateChild(localChildKey).updateChild(paramPath.popFront(), paramNode));
  }
  
  public Node updateImmediateChild(ChildKey paramChildKey, Node paramNode)
  {
    if (paramChildKey.isPriorityChildName()) {
      return updatePriority(paramNode);
    }
    Object localObject2 = children;
    Object localObject1 = localObject2;
    if (((ImmutableSortedMap)localObject2).containsKey(paramChildKey)) {
      localObject1 = ((ImmutableSortedMap)localObject2).remove(paramChildKey);
    }
    localObject2 = localObject1;
    if (!paramNode.isEmpty()) {
      localObject2 = ((ImmutableSortedMap)localObject1).insert(paramChildKey, paramNode);
    }
    if (((ImmutableSortedMap)localObject2).isEmpty()) {
      return EmptyNode.Empty();
    }
    return new ChildrenNode((ImmutableSortedMap)localObject2, priority);
  }
  
  public Node updatePriority(Node paramNode)
  {
    if (children.isEmpty()) {
      return EmptyNode.Empty();
    }
    return new ChildrenNode(children, paramNode);
  }
  
  public static abstract class ChildVisitor
    extends LLRBNode.NodeVisitor<ChildKey, Node>
  {
    public ChildVisitor() {}
    
    public abstract void visitChild(ChildKey paramChildKey, Node paramNode);
    
    public void visitEntry(ChildKey paramChildKey, Node paramNode)
    {
      visitChild(paramChildKey, paramNode);
    }
  }
  
  private static class NamedNodeIterator
    implements Iterator<NamedNode>
  {
    private final Iterator<Map.Entry<ChildKey, Node>> iterator;
    
    public NamedNodeIterator(Iterator paramIterator)
    {
      iterator = paramIterator;
    }
    
    public boolean hasNext()
    {
      return iterator.hasNext();
    }
    
    public NamedNode next()
    {
      Map.Entry localEntry = (Map.Entry)iterator.next();
      return new NamedNode((ChildKey)localEntry.getKey(), (Node)localEntry.getValue());
    }
    
    public void remove()
    {
      iterator.remove();
    }
  }
}
