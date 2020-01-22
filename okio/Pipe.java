package okio;

import java.io.IOException;
import javax.annotation.Nullable;

public final class Pipe
{
  final Buffer buffer = new Buffer();
  @Nullable
  private Sink foldedSink;
  final long maxBufferSize;
  private final Sink sink = new PipeSink();
  boolean sinkClosed;
  private final Source source = new PipeSource();
  boolean sourceClosed;
  
  public Pipe(long paramLong)
  {
    if (paramLong >= 1L)
    {
      maxBufferSize = paramLong;
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("maxBufferSize < 1: ");
    localStringBuilder.append(paramLong);
    throw new IllegalArgumentException(localStringBuilder.toString());
  }
  
  public void fold(Sink paramSink)
    throws IOException
  {
    for (;;)
    {
      Buffer localBuffer1 = buffer;
      try
      {
        if (foldedSink == null)
        {
          if (buffer.exhausted())
          {
            sourceClosed = true;
            foldedSink = paramSink;
            return;
          }
          boolean bool = sinkClosed;
          Buffer localBuffer2 = new Buffer();
          localBuffer2.write(buffer, buffer.size);
          buffer.notifyAll();
          try
          {
            paramSink.write(localBuffer2, size);
            if (bool)
            {
              paramSink.close();
              continue;
            }
            paramSink.flush();
          }
          catch (Throwable localThrowable1)
          {
            paramSink = buffer;
            try
            {
              sourceClosed = true;
              buffer.notifyAll();
              throw localThrowable1;
            }
            catch (Throwable localThrowable2)
            {
              throw localThrowable2;
            }
          }
        }
        throw new IllegalStateException("sink already folded");
      }
      catch (Throwable paramSink)
      {
        throw paramSink;
      }
    }
  }
  
  public final Sink sink()
  {
    return sink;
  }
  
  public final Source source()
  {
    return source;
  }
  
  final class PipeSink
    implements Sink
  {
    final PushableTimeout timeout = new PushableTimeout();
    
    PipeSink() {}
    
    public void close()
      throws IOException
    {
      Buffer localBuffer = buffer;
      try
      {
        if (sinkClosed) {
          return;
        }
        Object localObject;
        PipeSink localPipeSink;
        if (foldedSink != null)
        {
          localObject = Pipe.this;
          localPipeSink = this;
          localObject = foldedSink;
        }
        else
        {
          localObject = Pipe.this;
          localPipeSink = this;
          if ((sourceClosed) && (this$0.buffer.size() > 0L)) {
            throw new IOException("source is closed");
          }
          localPipeSink = this;
          this$0.sinkClosed = true;
          localObject = this$0;
          buffer.notifyAll();
          localObject = null;
        }
        if (localObject != null)
        {
          timeout.push(((Sink)localObject).timeout());
          try
          {
            ((Sink)localObject).close();
            timeout.enter();
            return;
          }
          catch (Throwable localThrowable2)
          {
            timeout.enter();
            throw localThrowable2;
          }
        }
        return;
      }
      catch (Throwable localThrowable1)
      {
        throw localThrowable1;
      }
    }
    
    public void flush()
      throws IOException
    {
      Buffer localBuffer = buffer;
      for (;;)
      {
        try
        {
          if (!sinkClosed)
          {
            Object localObject1;
            if (foldedSink != null)
            {
              localObject1 = foldedSink;
            }
            else
            {
              localObject3 = Pipe.this;
              localObject1 = this;
              if ((!sourceClosed) || (this$0.buffer.size() <= 0L)) {
                break label144;
              }
              throw new IOException("source is closed");
            }
            Object localObject3 = this;
            if (localObject1 == null) {
              break label143;
            }
            timeout.push(((Sink)localObject1).timeout());
            try
            {
              ((Sink)localObject1).flush();
              timeout.enter();
              return;
            }
            catch (Throwable localThrowable1)
            {
              timeout.enter();
              throw localThrowable1;
            }
          }
          throw new IllegalStateException("closed");
        }
        catch (Throwable localThrowable2)
        {
          throw localThrowable2;
        }
        label143:
        return;
        label144:
        Object localObject2 = null;
      }
    }
    
    public Timeout timeout()
    {
      return timeout;
    }
    
    public void write(Buffer paramBuffer, long paramLong)
      throws IOException
    {
      Buffer localBuffer = buffer;
      for (;;)
      {
        try
        {
          if (!sinkClosed)
          {
            if (paramLong <= 0L) {
              break label222;
            }
            if (foldedSink != null)
            {
              localSink = foldedSink;
            }
            else
            {
              if (!sourceClosed)
              {
                long l = maxBufferSize - buffer.size();
                if (l == 0L)
                {
                  timeout.waitUntilNotified(buffer);
                  continue;
                }
                l = Math.min(l, paramLong);
                buffer.write(paramBuffer, l);
                paramLong -= l;
                buffer.notifyAll();
                continue;
              }
              throw new IOException("source is closed");
            }
            if (localSink == null) {
              break label221;
            }
            timeout.push(localSink.timeout());
            try
            {
              localSink.write(paramBuffer, paramLong);
              timeout.enter();
              return;
            }
            catch (Throwable paramBuffer)
            {
              timeout.enter();
              throw paramBuffer;
            }
          }
          throw new IllegalStateException("closed");
        }
        catch (Throwable paramBuffer)
        {
          throw paramBuffer;
        }
        label221:
        return;
        label222:
        Sink localSink = null;
      }
    }
  }
  
  final class PipeSource
    implements Source
  {
    final Timeout timeout = new Timeout();
    
    PipeSource() {}
    
    public void close()
      throws IOException
    {
      Buffer localBuffer = buffer;
      try
      {
        sourceClosed = true;
        buffer.notifyAll();
        return;
      }
      catch (Throwable localThrowable)
      {
        throw localThrowable;
      }
    }
    
    public long read(Buffer paramBuffer, long paramLong)
      throws IOException
    {
      Buffer localBuffer = buffer;
      try
      {
        if (!sourceClosed)
        {
          while (buffer.size() == 0L)
          {
            if (sinkClosed) {
              return -1L;
            }
            timeout.waitUntilNotified(buffer);
          }
          paramLong = buffer.read(paramBuffer, paramLong);
          buffer.notifyAll();
          return paramLong;
        }
        throw new IllegalStateException("closed");
      }
      catch (Throwable paramBuffer)
      {
        throw paramBuffer;
      }
    }
    
    public Timeout timeout()
    {
      return timeout;
    }
  }
}
