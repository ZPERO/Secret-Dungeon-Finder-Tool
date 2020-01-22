package com.google.firebase.database.core.view;

import com.google.firebase.database.core.EventRegistration;
import com.google.firebase.database.snapshot.Index;
import com.google.firebase.database.snapshot.IndexedNode;
import com.google.firebase.database.snapshot.NamedNode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class EventGenerator
{
  private final Index index;
  private final QuerySpec query;
  
  public EventGenerator(QuerySpec paramQuerySpec)
  {
    query = paramQuerySpec;
    index = paramQuerySpec.getIndex();
  }
  
  private Comparator changeComparator()
  {
    new Comparator()
    {
      public int compare(Change paramAnonymousChange1, Change paramAnonymousChange2)
      {
        paramAnonymousChange1 = new NamedNode(paramAnonymousChange1.getChildKey(), paramAnonymousChange1.getIndexedNode().getNode());
        paramAnonymousChange2 = new NamedNode(paramAnonymousChange2.getChildKey(), paramAnonymousChange2.getIndexedNode().getNode());
        return index.compare(paramAnonymousChange1, paramAnonymousChange2);
      }
    };
  }
  
  private DataEvent generateEvent(Change paramChange, EventRegistration paramEventRegistration, IndexedNode paramIndexedNode)
  {
    Change localChange = paramChange;
    if (!paramChange.getEventType().equals(Event.EventType.VALUE)) {
      if (paramChange.getEventType().equals(Event.EventType.CHILD_REMOVED)) {
        localChange = paramChange;
      } else {
        localChange = paramChange.changeWithPrevName(paramIndexedNode.getPredecessorChildName(paramChange.getChildKey(), paramChange.getIndexedNode().getNode(), index));
      }
    }
    return paramEventRegistration.createEvent(localChange, query);
  }
  
  private void generateEventsForType(List paramList1, Event.EventType paramEventType, List paramList2, List paramList3, IndexedNode paramIndexedNode)
  {
    Object localObject1 = new ArrayList();
    paramList2 = paramList2.iterator();
    Object localObject2;
    while (paramList2.hasNext())
    {
      localObject2 = (Change)paramList2.next();
      if (((Change)localObject2).getEventType().equals(paramEventType)) {
        ((List)localObject1).add(localObject2);
      }
    }
    Collections.sort((List)localObject1, changeComparator());
    paramList2 = ((List)localObject1).iterator();
    while (paramList2.hasNext())
    {
      localObject1 = (Change)paramList2.next();
      localObject2 = paramList3.iterator();
      while (((Iterator)localObject2).hasNext())
      {
        EventRegistration localEventRegistration = (EventRegistration)((Iterator)localObject2).next();
        if (localEventRegistration.respondsTo(paramEventType)) {
          paramList1.add(generateEvent((Change)localObject1, localEventRegistration, paramIndexedNode));
        }
      }
    }
  }
  
  public List generateEventsForChanges(List paramList1, IndexedNode paramIndexedNode, List paramList2)
  {
    ArrayList localArrayList1 = new ArrayList();
    ArrayList localArrayList2 = new ArrayList();
    Iterator localIterator = paramList1.iterator();
    while (localIterator.hasNext())
    {
      Change localChange = (Change)localIterator.next();
      if ((localChange.getEventType().equals(Event.EventType.CHILD_CHANGED)) && (index.indexedValueChanged(localChange.getOldIndexedNode().getNode(), localChange.getIndexedNode().getNode()))) {
        localArrayList2.add(Change.childMovedChange(localChange.getChildKey(), localChange.getIndexedNode()));
      }
    }
    generateEventsForType(localArrayList1, Event.EventType.CHILD_REMOVED, paramList1, paramList2, paramIndexedNode);
    generateEventsForType(localArrayList1, Event.EventType.CHILD_ADDED, paramList1, paramList2, paramIndexedNode);
    generateEventsForType(localArrayList1, Event.EventType.CHILD_MOVED, localArrayList2, paramList2, paramIndexedNode);
    generateEventsForType(localArrayList1, Event.EventType.CHILD_CHANGED, paramList1, paramList2, paramIndexedNode);
    generateEventsForType(localArrayList1, Event.EventType.VALUE, paramList1, paramList2, paramIndexedNode);
    return localArrayList1;
  }
}
