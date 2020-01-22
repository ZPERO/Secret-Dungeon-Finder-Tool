package okio;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.concurrent.TimeUnit;

public class Timeout
{
  public static final Timeout NONE = new Timeout()
  {
    public Timeout deadlineNanoTime(long paramAnonymousLong)
    {
      return this;
    }
    
    public void throwIfReached()
      throws IOException
    {}
    
    public Timeout timeout(long paramAnonymousLong, TimeUnit paramAnonymousTimeUnit)
    {
      return this;
    }
  };
  private long deadlineNanoTime;
  private boolean hasDeadline;
  private long timeoutNanos;
  
  public Timeout() {}
  
  static long minTimeout(long paramLong1, long paramLong2)
  {
    if (paramLong1 == 0L) {
      return paramLong2;
    }
    if (paramLong2 == 0L) {
      return paramLong1;
    }
    if (paramLong1 < paramLong2) {
      return paramLong1;
    }
    return paramLong2;
  }
  
  public Timeout clearDeadline()
  {
    hasDeadline = false;
    return this;
  }
  
  public Timeout clearTimeout()
  {
    timeoutNanos = 0L;
    return this;
  }
  
  public final Timeout deadline(long paramLong, TimeUnit paramTimeUnit)
  {
    if (paramLong > 0L)
    {
      if (paramTimeUnit != null) {
        return deadlineNanoTime(System.nanoTime() + paramTimeUnit.toNanos(paramLong));
      }
      throw new IllegalArgumentException("unit == null");
    }
    paramTimeUnit = new StringBuilder();
    paramTimeUnit.append("duration <= 0: ");
    paramTimeUnit.append(paramLong);
    throw new IllegalArgumentException(paramTimeUnit.toString());
  }
  
  public long deadlineNanoTime()
  {
    if (hasDeadline) {
      return deadlineNanoTime;
    }
    throw new IllegalStateException("No deadline");
  }
  
  public Timeout deadlineNanoTime(long paramLong)
  {
    hasDeadline = true;
    deadlineNanoTime = paramLong;
    return this;
  }
  
  public boolean hasDeadline()
  {
    return hasDeadline;
  }
  
  public void throwIfReached()
    throws IOException
  {
    if (!Thread.interrupted())
    {
      if (hasDeadline)
      {
        if (deadlineNanoTime - System.nanoTime() > 0L) {
          return;
        }
        throw new InterruptedIOException("deadline reached");
      }
    }
    else
    {
      Thread.currentThread().interrupt();
      throw new InterruptedIOException("interrupted");
    }
  }
  
  public Timeout timeout(long paramLong, TimeUnit paramTimeUnit)
  {
    if (paramLong >= 0L)
    {
      if (paramTimeUnit != null)
      {
        timeoutNanos = paramTimeUnit.toNanos(paramLong);
        return this;
      }
      throw new IllegalArgumentException("unit == null");
    }
    paramTimeUnit = new StringBuilder();
    paramTimeUnit.append("timeout < 0: ");
    paramTimeUnit.append(paramLong);
    throw new IllegalArgumentException(paramTimeUnit.toString());
  }
  
  public long timeoutNanos()
  {
    return timeoutNanos;
  }
  
  public final void waitUntilNotified(Object paramObject)
    throws InterruptedIOException
  {
    for (;;)
    {
      try
      {
        boolean bool = hasDeadline();
        long l3 = timeoutNanos();
        l1 = l3;
        l2 = 0L;
        if ((!bool) && (l3 == 0L))
        {
          paramObject.wait();
          return;
        }
        l4 = System.nanoTime();
        if ((bool) && (l3 != 0L))
        {
          l1 = deadlineNanoTime();
          l1 = Math.min(l3, l1 - l4);
        }
        else if (bool)
        {
          l1 = deadlineNanoTime();
          l1 -= l4;
        }
        if (l1 > 0L)
        {
          l2 = l1 / 1000000L;
          Long.signum(l2);
          i = (int)(l1 - 1000000L * l2);
        }
      }
      catch (InterruptedException paramObject)
      {
        long l1;
        long l2;
        long l4;
        int i;
        continue;
      }
      try
      {
        paramObject.wait(l2, i);
        l2 = System.nanoTime();
        l2 -= l4;
        if (l2 < l1) {
          return;
        }
        paramObject = new InterruptedIOException("timeout");
        throw paramObject;
      }
      catch (InterruptedException paramObject) {}
    }
    Thread.currentThread().interrupt();
    throw new InterruptedIOException("interrupted");
  }
}
