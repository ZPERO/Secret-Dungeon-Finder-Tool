package okio;

import java.io.IOException;
import java.util.zip.CRC32;
import java.util.zip.Deflater;

public final class GzipSink
  implements Sink
{
  private boolean closed;
  private final CRC32 crc = new CRC32();
  private final Deflater deflater;
  private final DeflaterSink deflaterSink;
  private final BufferedSink sink;
  
  public GzipSink(Sink paramSink)
  {
    if (paramSink != null)
    {
      deflater = new Deflater(-1, true);
      sink = Okio.buffer(paramSink);
      deflaterSink = new DeflaterSink(sink, deflater);
      writeHeader();
      return;
    }
    throw new IllegalArgumentException("sink == null");
  }
  
  private void updateCrc(Buffer paramBuffer, long paramLong)
  {
    for (paramBuffer = head; paramLong > 0L; paramBuffer = next)
    {
      int i = (int)Math.min(paramLong, limit - pos);
      crc.update(data, pos, i);
      paramLong -= i;
    }
  }
  
  private void writeFooter()
    throws IOException
  {
    sink.writeIntLe((int)crc.getValue());
    sink.writeIntLe((int)deflater.getBytesRead());
  }
  
  private void writeHeader()
  {
    Buffer localBuffer = sink.buffer();
    localBuffer.writeShort(8075);
    localBuffer.writeByte(8);
    localBuffer.writeByte(0);
    localBuffer.writeInt(0);
    localBuffer.writeByte(0);
    localBuffer.writeByte(0);
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
      deflaterSink.finishDeflate();
      writeFooter();
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
  
  public final Deflater deflater()
  {
    return deflater;
  }
  
  public void flush()
    throws IOException
  {
    deflaterSink.flush();
  }
  
  public Timeout timeout()
  {
    return sink.timeout();
  }
  
  public void write(Buffer paramBuffer, long paramLong)
    throws IOException
  {
    if (paramLong >= 0L)
    {
      if (paramLong == 0L) {
        return;
      }
      updateCrc(paramBuffer, paramLong);
      deflaterSink.write(paramBuffer, paramLong);
      return;
    }
    paramBuffer = new StringBuilder();
    paramBuffer.append("byteCount < 0: ");
    paramBuffer.append(paramLong);
    throw new IllegalArgumentException(paramBuffer.toString());
  }
}
