package com.google.firebase.database.core.utilities;

public class DefaultClock
  implements Clock
{
  public DefaultClock() {}
  
  public long millis()
  {
    return System.currentTimeMillis();
  }
}
