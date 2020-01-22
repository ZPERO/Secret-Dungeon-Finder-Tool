package com.google.firebase.database.core.utilities.tuple;

import com.google.firebase.database.snapshot.ChildKey;
import com.google.firebase.database.snapshot.Node;
import com.google.firebase.database.snapshot.NodeUtilities;

public class NameAndPriority
  implements Comparable<NameAndPriority>
{
  private ChildKey name;
  private Node priority;
  
  public NameAndPriority(ChildKey paramChildKey, Node paramNode)
  {
    name = paramChildKey;
    priority = paramNode;
  }
  
  public int compareTo(NameAndPriority paramNameAndPriority)
  {
    return NodeUtilities.nameAndPriorityCompare(name, priority, name, priority);
  }
  
  public ChildKey getName()
  {
    return name;
  }
  
  public Node getPriority()
  {
    return priority;
  }
}
