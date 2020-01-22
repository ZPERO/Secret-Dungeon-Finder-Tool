package com.google.firebase.database.snapshot;

import com.google.firebase.database.core.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class RangeMerge
{
  private final Path optExclusiveStart;
  private final Path optInclusiveEnd;
  private final Node snap;
  
  public RangeMerge(com.google.firebase.database.connection.RangeMerge paramRangeMerge)
  {
    Object localObject1 = paramRangeMerge.getOptExclusiveStart();
    Object localObject2 = null;
    if (localObject1 != null) {
      localObject1 = new Path((List)localObject1);
    } else {
      localObject1 = null;
    }
    optExclusiveStart = ((Path)localObject1);
    List localList = paramRangeMerge.getOptInclusiveEnd();
    localObject1 = localObject2;
    if (localList != null) {
      localObject1 = new Path(localList);
    }
    optInclusiveEnd = ((Path)localObject1);
    snap = NodeUtilities.NodeFromJSON(paramRangeMerge.getSnap());
  }
  
  public RangeMerge(Path paramPath1, Path paramPath2, Node paramNode)
  {
    optExclusiveStart = paramPath1;
    optInclusiveEnd = paramPath2;
    snap = paramNode;
  }
  
  private Node updateRangeInNode(Path paramPath, Node paramNode1, Node paramNode2)
  {
    Object localObject1 = optExclusiveStart;
    int i;
    if (localObject1 == null) {
      i = 1;
    } else {
      i = paramPath.compareTo((Path)localObject1);
    }
    localObject1 = optInclusiveEnd;
    int j;
    if (localObject1 == null) {
      j = -1;
    } else {
      j = paramPath.compareTo((Path)localObject1);
    }
    localObject1 = optExclusiveStart;
    int n = 0;
    int k;
    if ((localObject1 != null) && (paramPath.contains((Path)localObject1))) {
      k = 1;
    } else {
      k = 0;
    }
    localObject1 = optInclusiveEnd;
    int m = n;
    if (localObject1 != null)
    {
      m = n;
      if (paramPath.contains((Path)localObject1)) {
        m = 1;
      }
    }
    if ((i > 0) && (j < 0) && (m == 0)) {
      return paramNode2;
    }
    if ((i > 0) && (m != 0) && (paramNode2.isLeafNode())) {
      return paramNode2;
    }
    if ((i > 0) && (j == 0))
    {
      if (paramNode1.isLeafNode()) {
        return EmptyNode.Empty();
      }
      return paramNode1;
    }
    if ((k == 0) && (m == 0)) {
      return paramNode1;
    }
    localObject1 = new HashSet();
    Object localObject2 = paramNode1.iterator();
    while (((Iterator)localObject2).hasNext()) {
      ((Set)localObject1).add(((NamedNode)((Iterator)localObject2).next()).getName());
    }
    localObject2 = paramNode2.iterator();
    while (((Iterator)localObject2).hasNext()) {
      ((Set)localObject1).add(((NamedNode)((Iterator)localObject2).next()).getName());
    }
    localObject2 = new ArrayList(((Set)localObject1).size() + 1);
    ((List)localObject2).addAll((Collection)localObject1);
    if ((!paramNode2.getPriority().isEmpty()) || (!paramNode1.getPriority().isEmpty())) {
      ((List)localObject2).add(ChildKey.getPriorityKey());
    }
    localObject2 = ((List)localObject2).iterator();
    localObject1 = paramNode1;
    while (((Iterator)localObject2).hasNext())
    {
      ChildKey localChildKey = (ChildKey)((Iterator)localObject2).next();
      Node localNode1 = paramNode1.getImmediateChild(localChildKey);
      Node localNode2 = updateRangeInNode(paramPath.child(localChildKey), paramNode1.getImmediateChild(localChildKey), paramNode2.getImmediateChild(localChildKey));
      if (localNode2 != localNode1) {
        localObject1 = ((Node)localObject1).updateImmediateChild(localChildKey, localNode2);
      }
    }
    return localObject1;
  }
  
  public Node applyTo(Node paramNode)
  {
    return updateRangeInNode(Path.getEmptyPath(), paramNode, snap);
  }
  
  Path getEnd()
  {
    return optInclusiveEnd;
  }
  
  Path getStart()
  {
    return optExclusiveStart;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("RangeMerge{optExclusiveStart=");
    localStringBuilder.append(optExclusiveStart);
    localStringBuilder.append(", optInclusiveEnd=");
    localStringBuilder.append(optInclusiveEnd);
    localStringBuilder.append(", snap=");
    localStringBuilder.append(snap);
    localStringBuilder.append('}');
    return localStringBuilder.toString();
  }
}
