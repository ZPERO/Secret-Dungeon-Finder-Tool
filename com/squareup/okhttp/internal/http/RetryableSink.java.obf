package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.internal.Util;
import java.io.IOException;
import java.net.ProtocolException;
import okio.Buffer;
import okio.Sink;
import okio.Timeout;

public final class RetryableSink
  implements Sink
{
  private boolean closed;
  private final Buffer content = new Buffer();
  private final int limit;
  
  public RetryableSink()
  {
    this(-1);
  }
  
  public RetryableSink(int paramInt)
  {
    limit = paramInt;
  }
  
  public void close()
    throws IOException
  {
    if (closed) {
      return;
    }
    closed = true;
    if (content.size() >= limit) {
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("content-length promised ");
    localStringBuilder.append(limit);
    localStringBuilder.append(" bytes, but received ");
    localStringBuilder.append(content.size());
    throw new ProtocolException(localStringBuilder.toString());
  }
  
  public long contentLength()
    throws IOException
  {
    return content.size();
  }
  
  public void flush()
    throws IOException
  {}
  
  public Timeout timeout()
  {
    return Timeout.NONE;
  }
  
  public void write(Buffer paramBuffer, long paramLong)
    throws IOException
  {
    if (!closed)
    {
      Util.checkOffsetAndCount(paramBuffer.size(), 0L, paramLong);
      if ((limit != -1) && (content.size() > limit - paramLong))
      {
        paramBuffer = new StringBuilder();
        paramBuffer.append("exceeded content-length limit of ");
        paramBuffer.append(limit);
        paramBuffer.append(" bytes");
        throw new ProtocolException(paramBuffer.toString());
      }
      content.write(paramBuffer, paramLong);
      return;
    }
    throw new IllegalStateException("closed");
  }
  
  public void writeToSocket(Sink paramSink)
    throws IOException
  {
    Buffer localBuffer1 = new Buffer();
    Buffer localBuffer2 = content;
    localBuffer2.copyTo(localBuffer1, 0L, localBuffer2.size());
    paramSink.write(localBuffer1, localBuffer1.size());
  }
}
