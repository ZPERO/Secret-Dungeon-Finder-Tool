package okio;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;

public class AsyncTimeout
  extends Timeout
{
  private static final long IDLE_TIMEOUT_MILLIS = TimeUnit.SECONDS.toMillis(60L);
  private static final long IDLE_TIMEOUT_NANOS = TimeUnit.MILLISECONDS.toNanos(IDLE_TIMEOUT_MILLIS);
  private static final int TIMEOUT_WRITE_SIZE = 65536;
  @Nullable
  static AsyncTimeout head;
  private boolean inQueue;
  @Nullable
  private AsyncTimeout next;
  private long timeoutAt;
  
  public AsyncTimeout() {}
  
  static AsyncTimeout awaitTimeout()
    throws InterruptedException
  {
    AsyncTimeout localAsyncTimeout = headnext;
    long l1;
    if (localAsyncTimeout == null)
    {
      l1 = System.nanoTime();
      AsyncTimeout.class.wait(IDLE_TIMEOUT_MILLIS);
      if ((headnext == null) && (System.nanoTime() - l1 >= IDLE_TIMEOUT_NANOS)) {
        return head;
      }
    }
    else
    {
      l1 = localAsyncTimeout.remainingNanos(System.nanoTime());
      if (l1 > 0L)
      {
        long l2 = l1 / 1000000L;
        AsyncTimeout.class.wait(l2, (int)(l1 - 1000000L * l2));
        return null;
      }
      headnext = next;
      next = null;
      return localAsyncTimeout;
    }
    return null;
  }
  
  private static boolean cancelScheduledTimeout(AsyncTimeout paramAsyncTimeout)
  {
    try
    {
      for (AsyncTimeout localAsyncTimeout = head; localAsyncTimeout != null; localAsyncTimeout = next) {
        if (next == paramAsyncTimeout)
        {
          next = next;
          next = null;
          return false;
        }
      }
      return true;
    }
    catch (Throwable paramAsyncTimeout)
    {
      throw paramAsyncTimeout;
    }
  }
  
  private long remainingNanos(long paramLong)
  {
    return timeoutAt - paramLong;
  }
  
  private static void scheduleTimeout(AsyncTimeout paramAsyncTimeout, long paramLong, boolean paramBoolean)
  {
    try
    {
      if (head == null)
      {
        head = new AsyncTimeout();
        new Watchdog().start();
      }
      long l = System.nanoTime();
      if ((paramLong != 0L) && (paramBoolean))
      {
        timeoutAt = (Math.min(paramLong, paramAsyncTimeout.deadlineNanoTime() - l) + l);
      }
      else if (paramLong != 0L)
      {
        timeoutAt = (paramLong + l);
      }
      else
      {
        if (!paramBoolean) {
          break label174;
        }
        timeoutAt = paramAsyncTimeout.deadlineNanoTime();
      }
      paramLong = paramAsyncTimeout.remainingNanos(l);
      for (AsyncTimeout localAsyncTimeout = head; (next != null) && (paramLong >= next.remainingNanos(l)); localAsyncTimeout = next) {}
      next = next;
      next = paramAsyncTimeout;
      if (localAsyncTimeout == head) {
        AsyncTimeout.class.notify();
      }
      return;
      label174:
      throw new AssertionError();
    }
    catch (Throwable paramAsyncTimeout)
    {
      throw paramAsyncTimeout;
    }
  }
  
  public final void enter()
  {
    if (!inQueue)
    {
      long l = timeoutNanos();
      boolean bool = hasDeadline();
      if ((l == 0L) && (!bool)) {
        return;
      }
      inQueue = true;
      scheduleTimeout(this, l, bool);
      return;
    }
    throw new IllegalStateException("Unbalanced enter/exit");
  }
  
  final IOException exit(IOException paramIOException)
    throws IOException
  {
    if (!exit()) {
      return paramIOException;
    }
    return newTimeoutException(paramIOException);
  }
  
  final void exit(boolean paramBoolean)
    throws IOException
  {
    if (exit())
    {
      if (!paramBoolean) {
        return;
      }
      throw newTimeoutException(null);
    }
  }
  
  public final boolean exit()
  {
    if (!inQueue) {
      return false;
    }
    inQueue = false;
    return cancelScheduledTimeout(this);
  }
  
  protected IOException newTimeoutException(IOException paramIOException)
  {
    InterruptedIOException localInterruptedIOException = new InterruptedIOException("timeout");
    if (paramIOException != null) {
      localInterruptedIOException.initCause(paramIOException);
    }
    return localInterruptedIOException;
  }
  
  public final Sink sink(final Sink paramSink)
  {
    new Sink()
    {
      public void close()
        throws IOException
      {
        enter();
        Sink localSink = paramSink;
        try
        {
          localSink.close();
          exit(true);
          return;
        }
        catch (Throwable localThrowable) {}catch (IOException localIOException)
        {
          throw exit(localIOException);
        }
        exit(false);
        throw localIOException;
      }
      
      public void flush()
        throws IOException
      {
        enter();
        Sink localSink = paramSink;
        try
        {
          localSink.flush();
          exit(true);
          return;
        }
        catch (Throwable localThrowable) {}catch (IOException localIOException)
        {
          throw exit(localIOException);
        }
        exit(false);
        throw localIOException;
      }
      
      public Timeout timeout()
      {
        return AsyncTimeout.this;
      }
      
      public String toString()
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("AsyncTimeout.sink(");
        localStringBuilder.append(paramSink);
        localStringBuilder.append(")");
        return localStringBuilder.toString();
      }
      
      public void write(Buffer paramAnonymousBuffer, long paramAnonymousLong)
        throws IOException
      {
        Util.checkOffsetAndCount(size, 0L, paramAnonymousLong);
        for (;;)
        {
          long l1 = 0L;
          if (paramAnonymousLong <= 0L) {
            return;
          }
          long l2;
          for (Object localObject = head;; localObject = next)
          {
            l2 = l1;
            if (l1 >= 65536L) {
              break;
            }
            l1 += limit - pos;
            if (l1 >= paramAnonymousLong)
            {
              l2 = paramAnonymousLong;
              break;
            }
          }
          enter();
          localObject = paramSink;
          try
          {
            ((Sink)localObject).write(paramAnonymousBuffer, l2);
            paramAnonymousLong -= l2;
            exit(true);
          }
          catch (Throwable paramAnonymousBuffer) {}catch (IOException paramAnonymousBuffer)
          {
            throw exit(paramAnonymousBuffer);
          }
        }
        exit(false);
        throw paramAnonymousBuffer;
      }
    };
  }
  
  public final Source source(final Source paramSource)
  {
    new Source()
    {
      public void close()
        throws IOException
      {
        enter();
        Source localSource = paramSource;
        try
        {
          localSource.close();
          exit(true);
          return;
        }
        catch (Throwable localThrowable) {}catch (IOException localIOException)
        {
          throw exit(localIOException);
        }
        exit(false);
        throw localIOException;
      }
      
      public long read(Buffer paramAnonymousBuffer, long paramAnonymousLong)
        throws IOException
      {
        enter();
        Source localSource = paramSource;
        try
        {
          paramAnonymousLong = localSource.read(paramAnonymousBuffer, paramAnonymousLong);
          exit(true);
          return paramAnonymousLong;
        }
        catch (Throwable paramAnonymousBuffer) {}catch (IOException paramAnonymousBuffer)
        {
          throw exit(paramAnonymousBuffer);
        }
        exit(false);
        throw paramAnonymousBuffer;
      }
      
      public Timeout timeout()
      {
        return AsyncTimeout.this;
      }
      
      public String toString()
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("AsyncTimeout.source(");
        localStringBuilder.append(paramSource);
        localStringBuilder.append(")");
        return localStringBuilder.toString();
      }
    };
  }
  
  protected void timedOut() {}
  
  private static final class Watchdog
    extends Thread
  {
    Watchdog()
    {
      super();
      setDaemon(true);
    }
    
    public void run()
    {
      for (;;)
      {
        try
        {
          localAsyncTimeout = AsyncTimeout.awaitTimeout();
          if (localAsyncTimeout == null) {}
          if (localAsyncTimeout == AsyncTimeout.head)
          {
            AsyncTimeout.head = null;
            return;
          }
        }
        catch (Throwable localThrowable)
        {
          try
          {
            AsyncTimeout localAsyncTimeout;
            localAsyncTimeout.timedOut();
          }
          catch (InterruptedException localInterruptedException) {}
          localThrowable = localThrowable;
          throw localThrowable;
        }
      }
    }
  }
}
