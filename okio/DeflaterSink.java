package okio;

import java.io.IOException;
import java.util.zip.Deflater;

public final class DeflaterSink
  implements Sink
{
  private boolean closed;
  private final Deflater deflater;
  private final BufferedSink sink;
  
  DeflaterSink(BufferedSink paramBufferedSink, Deflater paramDeflater)
  {
    if (paramBufferedSink != null)
    {
      if (paramDeflater != null)
      {
        sink = paramBufferedSink;
        deflater = paramDeflater;
        return;
      }
      throw new IllegalArgumentException("inflater == null");
    }
    throw new IllegalArgumentException("source == null");
  }
  
  public DeflaterSink(Sink paramSink, Deflater paramDeflater)
  {
    this(Okio.buffer(paramSink), paramDeflater);
  }
  
  private void deflate(boolean paramBoolean)
    throws IOException
  {
    Buffer localBuffer = sink.buffer();
    Segment localSegment;
    do
    {
      for (;;)
      {
        localSegment = localBuffer.writableSegment(1);
        int i;
        if (paramBoolean) {
          i = deflater.deflate(data, limit, 8192 - limit, 2);
        } else {
          i = deflater.deflate(data, limit, 8192 - limit);
        }
        if (i <= 0) {
          break;
        }
        limit += i;
        size += i;
        sink.emitCompleteSegments();
      }
    } while (!deflater.needsInput());
    if (pos == limit)
    {
      head = localSegment.pop();
      SegmentPool.recycle(localSegment);
    }
  }
  
  public void close()
    throws IOException
  {
    if (closed) {
      return;
    }
    Object localObject2 = null;
    try
    {
      finishDeflate();
    }
    catch (Throwable localThrowable1) {}
    Object localObject1;
    try
    {
      deflater.end();
      localObject1 = localThrowable1;
    }
    catch (Throwable localThrowable2)
    {
      localObject1 = localThrowable1;
      if (localThrowable1 == null) {
        localObject1 = localThrowable2;
      }
    }
    Object localObject3;
    try
    {
      sink.close();
      localObject3 = localObject1;
    }
    catch (Throwable localThrowable3)
    {
      localObject3 = localObject1;
      if (localObject1 == null) {
        localObject3 = localThrowable3;
      }
    }
    closed = true;
    if (localObject3 != null) {
      Util.sneakyRethrow(localObject3);
    }
  }
  
  void finishDeflate()
    throws IOException
  {
    deflater.finish();
    deflate(false);
  }
  
  public void flush()
    throws IOException
  {
    deflate(true);
    sink.flush();
  }
  
  public Timeout timeout()
  {
    return sink.timeout();
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("DeflaterSink(");
    localStringBuilder.append(sink);
    localStringBuilder.append(")");
    return localStringBuilder.toString();
  }
  
  public void write(Buffer paramBuffer, long paramLong)
    throws IOException
  {
    Util.checkOffsetAndCount(size, 0L, paramLong);
    while (paramLong > 0L)
    {
      Segment localSegment = head;
      int i = (int)Math.min(paramLong, limit - pos);
      deflater.setInput(data, pos, i);
      deflate(false);
      long l1 = size;
      long l2 = i;
      size = (l1 - l2);
      pos += i;
      if (pos == limit)
      {
        head = localSegment.pop();
        SegmentPool.recycle(localSegment);
      }
      paramLong -= l2;
    }
  }
}
