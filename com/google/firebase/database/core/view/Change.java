package com.google.firebase.database.core.view;

import com.google.firebase.database.snapshot.ChildKey;
import com.google.firebase.database.snapshot.IndexedNode;
import com.google.firebase.database.snapshot.Node;

public class Change
{
  private final ChildKey childKey;
  private final Event.EventType eventType;
  private final IndexedNode indexedNode;
  private final IndexedNode oldIndexedNode;
  private final ChildKey prevName;
  
  private Change(Event.EventType paramEventType, IndexedNode paramIndexedNode1, ChildKey paramChildKey1, ChildKey paramChildKey2, IndexedNode paramIndexedNode2)
  {
    eventType = paramEventType;
    indexedNode = paramIndexedNode1;
    childKey = paramChildKey1;
    prevName = paramChildKey2;
    oldIndexedNode = paramIndexedNode2;
  }
  
  public static Change childAddedChange(ChildKey paramChildKey, IndexedNode paramIndexedNode)
  {
    return new Change(Event.EventType.CHILD_ADDED, paramIndexedNode, paramChildKey, null, null);
  }
  
  public static Change childAddedChange(ChildKey paramChildKey, Node paramNode)
  {
    return childAddedChange(paramChildKey, IndexedNode.from(paramNode));
  }
  
  public static Change childChangedChange(ChildKey paramChildKey, IndexedNode paramIndexedNode1, IndexedNode paramIndexedNode2)
  {
    return new Change(Event.EventType.CHILD_CHANGED, paramIndexedNode1, paramChildKey, null, paramIndexedNode2);
  }
  
  public static Change childChangedChange(ChildKey paramChildKey, Node paramNode1, Node paramNode2)
  {
    return childChangedChange(paramChildKey, IndexedNode.from(paramNode1), IndexedNode.from(paramNode2));
  }
  
  public static Change childMovedChange(ChildKey paramChildKey, IndexedNode paramIndexedNode)
  {
    return new Change(Event.EventType.CHILD_MOVED, paramIndexedNode, paramChildKey, null, null);
  }
  
  public static Change childMovedChange(ChildKey paramChildKey, Node paramNode)
  {
    return childMovedChange(paramChildKey, IndexedNode.from(paramNode));
  }
  
  public static Change childRemovedChange(ChildKey paramChildKey, IndexedNode paramIndexedNode)
  {
    return new Change(Event.EventType.CHILD_REMOVED, paramIndexedNode, paramChildKey, null, null);
  }
  
  public static Change childRemovedChange(ChildKey paramChildKey, Node paramNode)
  {
    return childRemovedChange(paramChildKey, IndexedNode.from(paramNode));
  }
  
  public static Change valueChange(IndexedNode paramIndexedNode)
  {
    return new Change(Event.EventType.VALUE, paramIndexedNode, null, null, null);
  }
  
  public Change changeWithPrevName(ChildKey paramChildKey)
  {
    return new Change(eventType, indexedNode, childKey, paramChildKey, oldIndexedNode);
  }
  
  public ChildKey getChildKey()
  {
    return childKey;
  }
  
  public Event.EventType getEventType()
  {
    return eventType;
  }
  
  public IndexedNode getIndexedNode()
  {
    return indexedNode;
  }
  
  public IndexedNode getOldIndexedNode()
  {
    return oldIndexedNode;
  }
  
  public ChildKey getPrevName()
  {
    return prevName;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Change: ");
    localStringBuilder.append(eventType);
    localStringBuilder.append(" ");
    localStringBuilder.append(childKey);
    return localStringBuilder.toString();
  }
}
