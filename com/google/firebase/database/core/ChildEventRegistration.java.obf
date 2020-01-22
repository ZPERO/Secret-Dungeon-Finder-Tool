package com.google.firebase.database.core;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.InternalHelpers;
import com.google.firebase.database.core.view.Change;
import com.google.firebase.database.core.view.DataEvent;
import com.google.firebase.database.core.view.Event.EventType;
import com.google.firebase.database.core.view.QuerySpec;
import com.google.firebase.database.snapshot.ChildKey;

public class ChildEventRegistration
  extends EventRegistration
{
  private final ChildEventListener eventListener;
  private final Repo repo;
  private final QuerySpec spec;
  
  public ChildEventRegistration(Repo paramRepo, ChildEventListener paramChildEventListener, QuerySpec paramQuerySpec)
  {
    repo = paramRepo;
    eventListener = paramChildEventListener;
    spec = paramQuerySpec;
  }
  
  public EventRegistration clone(QuerySpec paramQuerySpec)
  {
    return new ChildEventRegistration(repo, eventListener, paramQuerySpec);
  }
  
  public DataEvent createEvent(Change paramChange, QuerySpec paramQuerySpec)
  {
    DataSnapshot localDataSnapshot = InternalHelpers.createDataSnapshot(InternalHelpers.createReference(repo, paramQuerySpec.getPath().child(paramChange.getChildKey())), paramChange.getIndexedNode());
    if (paramChange.getPrevName() != null) {
      paramQuerySpec = paramChange.getPrevName().asString();
    } else {
      paramQuerySpec = null;
    }
    return new DataEvent(paramChange.getEventType(), this, localDataSnapshot, paramQuerySpec);
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject instanceof ChildEventRegistration))
    {
      paramObject = (ChildEventRegistration)paramObject;
      if ((eventListener.equals(eventListener)) && (repo.equals(repo)) && (spec.equals(spec))) {
        return true;
      }
    }
    return false;
  }
  
  public void fireCancelEvent(DatabaseError paramDatabaseError)
  {
    eventListener.onCancelled(paramDatabaseError);
  }
  
  public void fireEvent(DataEvent paramDataEvent)
  {
    if (isZombied()) {
      return;
    }
    int i = 1.$SwitchMap$com$google$firebase$database$core$view$Event$EventType[paramDataEvent.getEventType().ordinal()];
    if (i != 1)
    {
      if (i != 2)
      {
        if (i != 3)
        {
          if (i != 4) {
            return;
          }
          eventListener.onChildRemoved(paramDataEvent.getSnapshot());
          return;
        }
        eventListener.onChildMoved(paramDataEvent.getSnapshot(), paramDataEvent.getPreviousName());
        return;
      }
      eventListener.onChildChanged(paramDataEvent.getSnapshot(), paramDataEvent.getPreviousName());
      return;
    }
    eventListener.onChildAdded(paramDataEvent.getSnapshot(), paramDataEvent.getPreviousName());
  }
  
  public QuerySpec getQuerySpec()
  {
    return spec;
  }
  
  Repo getRepo()
  {
    return repo;
  }
  
  public int hashCode()
  {
    return (eventListener.hashCode() * 31 + repo.hashCode()) * 31 + spec.hashCode();
  }
  
  public boolean isSameListener(EventRegistration paramEventRegistration)
  {
    return ((paramEventRegistration instanceof ChildEventRegistration)) && (eventListener.equals(eventListener));
  }
  
  public boolean respondsTo(Event.EventType paramEventType)
  {
    return paramEventType != Event.EventType.VALUE;
  }
  
  public String toString()
  {
    return "ChildEventRegistration";
  }
}
