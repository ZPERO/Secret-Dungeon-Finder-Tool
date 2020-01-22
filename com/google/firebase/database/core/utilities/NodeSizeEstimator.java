package com.google.firebase.database.core.utilities;

import com.google.firebase.database.snapshot.BooleanNode;
import com.google.firebase.database.snapshot.ChildKey;
import com.google.firebase.database.snapshot.DoubleNode;
import com.google.firebase.database.snapshot.LeafNode;
import com.google.firebase.database.snapshot.LongNode;
import com.google.firebase.database.snapshot.NamedNode;
import com.google.firebase.database.snapshot.Node;
import com.google.firebase.database.snapshot.StringNode;
import java.util.Iterator;

public class NodeSizeEstimator
{
  private static final int LEAF_PRIORITY_OVERHEAD = 24;
  
  public NodeSizeEstimator() {}
  
  private static long estimateLeafNodeSize(LeafNode paramLeafNode)
  {
    boolean bool = paramLeafNode instanceof DoubleNode;
    LeafNode localLeafNode = paramLeafNode;
    long l = 8L;
    if (!bool)
    {
      bool = localLeafNode instanceof LongNode;
      if (!bool)
      {
        bool = localLeafNode instanceof BooleanNode;
        if (bool)
        {
          l = 4L;
        }
        else
        {
          bool = localLeafNode instanceof StringNode;
          if (!bool) {
            break label107;
          }
          l = 2L + ((String)localLeafNode.getValue()).length();
        }
      }
    }
    if (paramLeafNode.getPriority().isEmpty()) {
      return l;
    }
    return l + 24L + estimateLeafNodeSize((LeafNode)paramLeafNode.getPriority());
    label107:
    paramLeafNode = new StringBuilder();
    paramLeafNode.append("Unknown leaf node type: ");
    paramLeafNode.append(localLeafNode.getClass());
    throw new IllegalArgumentException(paramLeafNode.toString());
  }
  
  public static long estimateSerializedNodeSize(Node paramNode)
  {
    if (paramNode.isEmpty()) {
      return 4L;
    }
    if (paramNode.isLeafNode()) {
      return estimateLeafNodeSize((LeafNode)paramNode);
    }
    long l1 = 1L;
    Iterator localIterator = paramNode.iterator();
    while (localIterator.hasNext())
    {
      NamedNode localNamedNode = (NamedNode)localIterator.next();
      l1 = l1 + localNamedNode.getName().asString().length() + 4L + estimateSerializedNodeSize(localNamedNode.getNode());
    }
    long l2 = l1;
    if (!paramNode.getPriority().isEmpty()) {
      l2 = l1 + 12L + estimateLeafNodeSize((LeafNode)paramNode.getPriority());
    }
    return l2;
  }
  
  public static int nodeCount(Node paramNode)
  {
    boolean bool = paramNode.isEmpty();
    int i = 0;
    if (bool) {
      return 0;
    }
    if (paramNode.isLeafNode()) {
      return 1;
    }
    paramNode = paramNode.iterator();
    while (paramNode.hasNext()) {
      i += nodeCount(((NamedNode)paramNode.next()).getNode());
    }
    return i;
  }
}
