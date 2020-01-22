package com.google.firebase.database.core.view;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.core.EventRegistration;
import com.google.firebase.database.core.Path;

public class CancelEvent
  implements Event
{
  private final DatabaseError error;
  private final EventRegistration eventRegistration;
  private final Path path;
  
  public CancelEvent(EventRegistration paramEventRegistration, DatabaseError paramDatabaseError, Path paramPath)
  {
    eventRegistration = paramEventRegistration;
    path = paramPath;
    error = paramDatabaseError;
  }
  
  public void fire()
  {
    eventRegistration.fireCancelEvent(error);
  }
  
  public Path getPath()
  {
    return path;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(getPath());
    localStringBuilder.append(":CANCEL");
    return localStringBuilder.toString();
  }
}
