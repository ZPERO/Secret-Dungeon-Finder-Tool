package okio;

import java.util.concurrent.TimeUnit;

final class PushableTimeout
  extends Timeout
{
  private long originalDeadlineNanoTime;
  private boolean originalHasDeadline;
  private long originalTimeoutNanos;
  private Timeout pushed;
  
  PushableTimeout() {}
  
  void enter()
  {
    pushed.timeout(originalTimeoutNanos, TimeUnit.NANOSECONDS);
    if (originalHasDeadline)
    {
      pushed.deadlineNanoTime(originalDeadlineNanoTime);
      return;
    }
    pushed.clearDeadline();
  }
  
  void push(Timeout paramTimeout)
  {
    pushed = paramTimeout;
    originalHasDeadline = paramTimeout.hasDeadline();
    long l;
    if (originalHasDeadline) {
      l = paramTimeout.deadlineNanoTime();
    } else {
      l = -1L;
    }
    originalDeadlineNanoTime = l;
    originalTimeoutNanos = paramTimeout.timeoutNanos();
    paramTimeout.timeout(Timeout.minTimeout(originalTimeoutNanos, timeoutNanos()), TimeUnit.NANOSECONDS);
    if ((originalHasDeadline) && (hasDeadline()))
    {
      paramTimeout.deadlineNanoTime(Math.min(deadlineNanoTime(), originalDeadlineNanoTime));
      return;
    }
    if (hasDeadline()) {
      paramTimeout.deadlineNanoTime(deadlineNanoTime());
    }
  }
}
