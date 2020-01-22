package com.google.firebase.database.core;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.core.view.Change;
import com.google.firebase.database.core.view.DataEvent;
import com.google.firebase.database.core.view.Event.EventType;
import com.google.firebase.database.core.view.QuerySpec;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class EventRegistration
{
  private boolean isUserInitiated = false;
  private EventRegistrationZombieListener listener;
  private AtomicBoolean zombied = new AtomicBoolean(false);
  
  public EventRegistration() {}
  
  public abstract EventRegistration clone(QuerySpec paramQuerySpec);
  
  public abstract DataEvent createEvent(Change paramChange, QuerySpec paramQuerySpec);
  
  public abstract void fireCancelEvent(DatabaseError paramDatabaseError);
  
  public abstract void fireEvent(DataEvent paramDataEvent);
  
  public abstract QuerySpec getQuerySpec();
  
  Repo getRepo()
  {
    return null;
  }
  
  public abstract boolean isSameListener(EventRegistration paramEventRegistration);
  
  public boolean isUserInitiated()
  {
    return isUserInitiated;
  }
  
  public boolean isZombied()
  {
    return zombied.get();
  }
  
  public abstract boolean respondsTo(Event.EventType paramEventType);
  
  public void setIsUserInitiated(boolean paramBoolean)
  {
    isUserInitiated = paramBoolean;
  }
  
  public void setOnZombied(EventRegistrationZombieListener paramEventRegistrationZombieListener)
  {
    listener = paramEventRegistrationZombieListener;
  }
  
  public void zombify()
  {
    if (zombied.compareAndSet(false, true))
    {
      EventRegistrationZombieListener localEventRegistrationZombieListener = listener;
      if (localEventRegistrationZombieListener != null)
      {
        localEventRegistrationZombieListener.onZombied(this);
        listener = null;
      }
    }
  }
}
