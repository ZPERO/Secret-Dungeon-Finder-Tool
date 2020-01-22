package okio;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ForwardingTimeout
  extends Timeout
{
  private Timeout delegate;
  
  public ForwardingTimeout(Timeout paramTimeout)
  {
    if (paramTimeout != null)
    {
      delegate = paramTimeout;
      return;
    }
    throw new IllegalArgumentException("delegate == null");
  }
  
  public Timeout clearDeadline()
  {
    return delegate.clearDeadline();
  }
  
  public Timeout clearTimeout()
  {
    return delegate.clearTimeout();
  }
  
  public long deadlineNanoTime()
  {
    return delegate.deadlineNanoTime();
  }
  
  public Timeout deadlineNanoTime(long paramLong)
  {
    return delegate.deadlineNanoTime(paramLong);
  }
  
  public final Timeout delegate()
  {
    return delegate;
  }
  
  public boolean hasDeadline()
  {
    return delegate.hasDeadline();
  }
  
  public final ForwardingTimeout setDelegate(Timeout paramTimeout)
  {
    if (paramTimeout != null)
    {
      delegate = paramTimeout;
      return this;
    }
    throw new IllegalArgumentException("delegate == null");
  }
  
  public void throwIfReached()
    throws IOException
  {
    delegate.throwIfReached();
  }
  
  public Timeout timeout(long paramLong, TimeUnit paramTimeUnit)
  {
    return delegate.timeout(paramLong, paramTimeUnit);
  }
  
  public long timeoutNanos()
  {
    return delegate.timeoutNanos();
  }
}
