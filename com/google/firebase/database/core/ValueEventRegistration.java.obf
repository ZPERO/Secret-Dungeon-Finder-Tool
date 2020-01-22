package com.google.firebase.database.core;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.InternalHelpers;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.view.Change;
import com.google.firebase.database.core.view.DataEvent;
import com.google.firebase.database.core.view.Event.EventType;
import com.google.firebase.database.core.view.QuerySpec;

public class ValueEventRegistration
  extends EventRegistration
{
  private final ValueEventListener eventListener;
  private final Repo repo;
  private final QuerySpec spec;
  
  public ValueEventRegistration(Repo paramRepo, ValueEventListener paramValueEventListener, QuerySpec paramQuerySpec)
  {
    repo = paramRepo;
    eventListener = paramValueEventListener;
    spec = paramQuerySpec;
  }
  
  public EventRegistration clone(QuerySpec paramQuerySpec)
  {
    return new ValueEventRegistration(repo, eventListener, paramQuerySpec);
  }
  
  public DataEvent createEvent(Change paramChange, QuerySpec paramQuerySpec)
  {
    paramChange = InternalHelpers.createDataSnapshot(InternalHelpers.createReference(repo, paramQuerySpec.getPath()), paramChange.getIndexedNode());
    return new DataEvent(Event.EventType.VALUE, this, paramChange, null);
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject instanceof ValueEventRegistration))
    {
      paramObject = (ValueEventRegistration)paramObject;
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
    eventListener.onDataChange(paramDataEvent.getSnapshot());
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
    return ((paramEventRegistration instanceof ValueEventRegistration)) && (eventListener.equals(eventListener));
  }
  
  public boolean respondsTo(Event.EventType paramEventType)
  {
    return paramEventType == Event.EventType.VALUE;
  }
  
  public String toString()
  {
    return "ValueEventRegistration";
  }
}
