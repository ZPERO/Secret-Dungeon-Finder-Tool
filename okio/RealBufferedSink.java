package okio;

import java.io.EOFException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

final class RealBufferedSink
  implements BufferedSink
{
  public final Buffer buffer = new Buffer();
  boolean closed;
  public final Sink sink;
  
  RealBufferedSink(Sink paramSink)
  {
    if (paramSink != null)
    {
      sink = paramSink;
      return;
    }
    throw new NullPointerException("sink == null");
  }
  
  public Buffer buffer()
  {
    return buffer;
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
      long l = buffer.size;
      Object localObject1 = localObject2;
      if (l > 0L)
      {
        sink.write(buffer, buffer.size);
        localObject1 = localObject2;
      }
    }
    catch (Throwable localThrowable1) {}
    try
    {
      sink.close();
      localObject2 = localThrowable1;
    }
    catch (Throwable localThrowable2)
    {
      localObject2 = localThrowable1;
      if (localThrowable1 == null) {
        localObject2 = localThrowable2;
      }
    }
    closed = true;
    if (localObject2 != null) {
      Util.sneakyRethrow(localObject2);
    }
  }
  
  public BufferedSink emit()
    throws IOException
  {
    if (!closed)
    {
      long l = buffer.size();
      if (l > 0L)
      {
        sink.write(buffer, l);
        return this;
      }
    }
    else
    {
      throw new IllegalStateException("closed");
    }
    return this;
  }
  
  public BufferedSink emitCompleteSegments()
    throws IOException
  {
    if (!closed)
    {
      long l = buffer.completeSegmentByteCount();
      if (l > 0L)
      {
        sink.write(buffer, l);
        return this;
      }
    }
    else
    {
      throw new IllegalStateException("closed");
    }
    return this;
  }
  
  public void flush()
    throws IOException
  {
    if (!closed)
    {
      if (buffer.size > 0L)
      {
        Sink localSink = sink;
        Buffer localBuffer = buffer;
        localSink.write(localBuffer, size);
      }
      sink.flush();
      return;
    }
    throw new IllegalStateException("closed");
  }
  
  public boolean isOpen()
  {
    return closed ^ true;
  }
  
  public OutputStream outputStream()
  {
    new OutputStream()
    {
      public void close()
        throws IOException
      {
        RealBufferedSink.this.close();
      }
      
      public void flush()
        throws IOException
      {
        if (!closed) {
          RealBufferedSink.this.flush();
        }
      }
      
      public String toString()
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(RealBufferedSink.this);
        localStringBuilder.append(".outputStream()");
        return localStringBuilder.toString();
      }
      
      public void write(int paramAnonymousInt)
        throws IOException
      {
        if (!closed)
        {
          buffer.writeByte((byte)paramAnonymousInt);
          emitCompleteSegments();
          return;
        }
        throw new IOException("closed");
      }
      
      public void write(byte[] paramAnonymousArrayOfByte, int paramAnonymousInt1, int paramAnonymousInt2)
        throws IOException
      {
        if (!closed)
        {
          buffer.write(paramAnonymousArrayOfByte, paramAnonymousInt1, paramAnonymousInt2);
          emitCompleteSegments();
          return;
        }
        throw new IOException("closed");
      }
    };
  }
  
  public Timeout timeout()
  {
    return sink.timeout();
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("buffer(");
    localStringBuilder.append(sink);
    localStringBuilder.append(")");
    return localStringBuilder.toString();
  }
  
  public int write(ByteBuffer paramByteBuffer)
    throws IOException
  {
    if (!closed)
    {
      int i = buffer.write(paramByteBuffer);
      emitCompleteSegments();
      return i;
    }
    throw new IllegalStateException("closed");
  }
  
  public BufferedSink write(ByteString paramByteString)
    throws IOException
  {
    if (!closed)
    {
      buffer.write(paramByteString);
      return emitCompleteSegments();
    }
    throw new IllegalStateException("closed");
  }
  
  public BufferedSink write(Source paramSource, long paramLong)
    throws IOException
  {
    while (paramLong > 0L)
    {
      long l = paramSource.read(buffer, paramLong);
      if (l != -1L)
      {
        paramLong -= l;
        emitCompleteSegments();
      }
      else
      {
        throw new EOFException();
      }
    }
    return this;
  }
  
  public BufferedSink write(byte[] paramArrayOfByte)
    throws IOException
  {
    if (!closed)
    {
      buffer.write(paramArrayOfByte);
      return emitCompleteSegments();
    }
    throw new IllegalStateException("closed");
  }
  
  public BufferedSink write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    if (!closed)
    {
      buffer.write(paramArrayOfByte, paramInt1, paramInt2);
      return emitCompleteSegments();
    }
    throw new IllegalStateException("closed");
  }
  
  public void write(Buffer paramBuffer, long paramLong)
    throws IOException
  {
    if (!closed)
    {
      buffer.write(paramBuffer, paramLong);
      emitCompleteSegments();
      return;
    }
    throw new IllegalStateException("closed");
  }
  
  public long writeAll(Source paramSource)
    throws IOException
  {
    if (paramSource != null)
    {
      long l1 = 0L;
      for (;;)
      {
        long l2 = paramSource.read(buffer, 8192L);
        if (l2 == -1L) {
          break;
        }
        l1 += l2;
        emitCompleteSegments();
      }
      return l1;
    }
    paramSource = new IllegalArgumentException("source == null");
    throw paramSource;
  }
  
  public BufferedSink writeByte(int paramInt)
    throws IOException
  {
    if (!closed)
    {
      buffer.writeByte(paramInt);
      return emitCompleteSegments();
    }
    throw new IllegalStateException("closed");
  }
  
  public BufferedSink writeDecimalLong(long paramLong)
    throws IOException
  {
    if (!closed)
    {
      buffer.writeDecimalLong(paramLong);
      return emitCompleteSegments();
    }
    throw new IllegalStateException("closed");
  }
  
  public BufferedSink writeHexadecimalUnsignedLong(long paramLong)
    throws IOException
  {
    if (!closed)
    {
      buffer.writeHexadecimalUnsignedLong(paramLong);
      return emitCompleteSegments();
    }
    throw new IllegalStateException("closed");
  }
  
  public BufferedSink writeInt(int paramInt)
    throws IOException
  {
    if (!closed)
    {
      buffer.writeInt(paramInt);
      return emitCompleteSegments();
    }
    throw new IllegalStateException("closed");
  }
  
  public BufferedSink writeIntLe(int paramInt)
    throws IOException
  {
    if (!closed)
    {
      buffer.writeIntLe(paramInt);
      return emitCompleteSegments();
    }
    throw new IllegalStateException("closed");
  }
  
  public BufferedSink writeLong(long paramLong)
    throws IOException
  {
    if (!closed)
    {
      buffer.writeLong(paramLong);
      return emitCompleteSegments();
    }
    throw new IllegalStateException("closed");
  }
  
  public BufferedSink writeLongLe(long paramLong)
    throws IOException
  {
    if (!closed)
    {
      buffer.writeLongLe(paramLong);
      return emitCompleteSegments();
    }
    throw new IllegalStateException("closed");
  }
  
  public BufferedSink writeShort(int paramInt)
    throws IOException
  {
    if (!closed)
    {
      buffer.writeShort(paramInt);
      return emitCompleteSegments();
    }
    throw new IllegalStateException("closed");
  }
  
  public BufferedSink writeShortLe(int paramInt)
    throws IOException
  {
    if (!closed)
    {
      buffer.writeShortLe(paramInt);
      return emitCompleteSegments();
    }
    throw new IllegalStateException("closed");
  }
  
  public BufferedSink writeString(String paramString, int paramInt1, int paramInt2, Charset paramCharset)
    throws IOException
  {
    if (!closed)
    {
      buffer.writeString(paramString, paramInt1, paramInt2, paramCharset);
      return emitCompleteSegments();
    }
    throw new IllegalStateException("closed");
  }
  
  public BufferedSink writeString(String paramString, Charset paramCharset)
    throws IOException
  {
    if (!closed)
    {
      buffer.writeString(paramString, paramCharset);
      return emitCompleteSegments();
    }
    throw new IllegalStateException("closed");
  }
  
  public BufferedSink writeUtf8(String paramString)
    throws IOException
  {
    if (!closed)
    {
      buffer.writeUtf8(paramString);
      return emitCompleteSegments();
    }
    throw new IllegalStateException("closed");
  }
  
  public BufferedSink writeUtf8(String paramString, int paramInt1, int paramInt2)
    throws IOException
  {
    if (!closed)
    {
      buffer.writeUtf8(paramString, paramInt1, paramInt2);
      return emitCompleteSegments();
    }
    throw new IllegalStateException("closed");
  }
  
  public BufferedSink writeUtf8CodePoint(int paramInt)
    throws IOException
  {
    if (!closed)
    {
      buffer.writeUtf8CodePoint(paramInt);
      return emitCompleteSegments();
    }
    throw new IllegalStateException("closed");
  }
}
