package com.google.firebase.database.core.view.filter;

import com.google.firebase.database.core.view.Change;
import com.google.firebase.database.core.view.Event.EventType;
import com.google.firebase.database.snapshot.ChildKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChildChangeAccumulator
{
  private final Map<ChildKey, Change> changeMap = new HashMap();
  
  public ChildChangeAccumulator() {}
  
  public List getChanges()
  {
    return new ArrayList(changeMap.values());
  }
  
  public void trackChildChange(Change paramChange)
  {
    Object localObject = paramChange.getEventType();
    ChildKey localChildKey = paramChange.getChildKey();
    if (changeMap.containsKey(localChildKey))
    {
      Change localChange = (Change)changeMap.get(localChildKey);
      Event.EventType localEventType = localChange.getEventType();
      if ((localObject == Event.EventType.CHILD_ADDED) && (localEventType == Event.EventType.CHILD_REMOVED))
      {
        changeMap.put(paramChange.getChildKey(), Change.childChangedChange(localChildKey, paramChange.getIndexedNode(), localChange.getIndexedNode()));
        return;
      }
      if ((localObject == Event.EventType.CHILD_REMOVED) && (localEventType == Event.EventType.CHILD_ADDED))
      {
        changeMap.remove(localChildKey);
        return;
      }
      if ((localObject == Event.EventType.CHILD_REMOVED) && (localEventType == Event.EventType.CHILD_CHANGED))
      {
        changeMap.put(localChildKey, Change.childRemovedChange(localChildKey, localChange.getOldIndexedNode()));
        return;
      }
      if ((localObject == Event.EventType.CHILD_CHANGED) && (localEventType == Event.EventType.CHILD_ADDED))
      {
        changeMap.put(localChildKey, Change.childAddedChange(localChildKey, paramChange.getIndexedNode()));
        return;
      }
      if ((localObject == Event.EventType.CHILD_CHANGED) && (localEventType == Event.EventType.CHILD_CHANGED))
      {
        changeMap.put(localChildKey, Change.childChangedChange(localChildKey, paramChange.getIndexedNode(), localChange.getOldIndexedNode()));
        return;
      }
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("Illegal combination of changes: ");
      ((StringBuilder)localObject).append(paramChange);
      ((StringBuilder)localObject).append(" occurred after ");
      ((StringBuilder)localObject).append(localChange);
      throw new IllegalStateException(((StringBuilder)localObject).toString());
    }
    changeMap.put(paramChange.getChildKey(), paramChange);
  }
}
