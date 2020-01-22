package com.google.firebase.database.core.utilities;

public class OffsetClock
  implements Clock
{
  private final Clock baseClock;
  private long offset = 0L;
  
  public OffsetClock(Clock paramClock, long paramLong)
  {
    baseClock = paramClock;
    offset = paramLong;
  }
  
  public long millis()
  {
    return baseClock.millis() + offset;
  }
  
  public void setOffset(long paramLong)
  {
    offset = paramLong;
  }
}
