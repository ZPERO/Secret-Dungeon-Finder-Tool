package com.google.firebase.events;

import com.google.android.android.common.internal.Preconditions;

public class Event<T>
{
  private final T payload;
  private final Class<T> type;
  
  public Event(Class paramClass, Object paramObject)
  {
    type = ((Class)Preconditions.checkNotNull(paramClass));
    payload = Preconditions.checkNotNull(paramObject);
  }
  
  public Object getPayload()
  {
    return payload;
  }
  
  public Class getType()
  {
    return type;
  }
  
  public String toString()
  {
    return String.format("Event{type: %s, payload: %s}", new Object[] { type, payload });
  }
}
