package com.squareup.okhttp.internal.framed;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;
import okio.ForwardingSource;
import okio.InflaterSource;
import okio.Okio;
import okio.Source;

class NameValueBlockReader
{
  private int compressedLimit;
  private final InflaterSource inflaterSource = new InflaterSource(new ForwardingSource(paramBufferedSource)new Inflater
  {
    public long read(Buffer paramAnonymousBuffer, long paramAnonymousLong)
      throws IOException
    {
      if (compressedLimit == 0) {
        return -1L;
      }
      paramAnonymousLong = super.read(paramAnonymousBuffer, Math.min(paramAnonymousLong, compressedLimit));
      if (paramAnonymousLong == -1L) {
        return -1L;
      }
      paramAnonymousBuffer = NameValueBlockReader.this;
      NameValueBlockReader.access$002(paramAnonymousBuffer, (int)(compressedLimit - paramAnonymousLong));
      return paramAnonymousLong;
    }
  }, new Inflater()
  {
    public int inflate(byte[] paramAnonymousArrayOfByte, int paramAnonymousInt1, int paramAnonymousInt2)
      throws DataFormatException
    {
      int i = super.inflate(paramAnonymousArrayOfByte, paramAnonymousInt1, paramAnonymousInt2);
      if ((i == 0) && (needsDictionary()))
      {
        setDictionary(Spdy3.DICTIONARY);
        return super.inflate(paramAnonymousArrayOfByte, paramAnonymousInt1, paramAnonymousInt2);
      }
      return i;
    }
  });
  private final BufferedSource source = Okio.buffer(inflaterSource);
  
  public NameValueBlockReader(BufferedSource paramBufferedSource) {}
  
  private void doneReading()
    throws IOException
  {
    if (compressedLimit > 0)
    {
      inflaterSource.refill();
      if (compressedLimit == 0) {
        return;
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("compressedLimit > 0: ");
      localStringBuilder.append(compressedLimit);
      throw new IOException(localStringBuilder.toString());
    }
  }
  
  private ByteString readByteString()
    throws IOException
  {
    int i = source.readInt();
    return source.readByteString(i);
  }
  
  public void close()
    throws IOException
  {
    source.close();
  }
  
  public List readNameValueBlock(int paramInt)
    throws IOException
  {
    compressedLimit += paramInt;
    int i = source.readInt();
    if (i >= 0)
    {
      if (i <= 1024)
      {
        localObject = new ArrayList(i);
        paramInt = 0;
        while (paramInt < i)
        {
          ByteString localByteString1 = readByteString().toAsciiLowercase();
          ByteString localByteString2 = readByteString();
          if (localByteString1.size() != 0)
          {
            ((List)localObject).add(new Header(localByteString1, localByteString2));
            paramInt += 1;
          }
          else
          {
            throw new IOException("name.size == 0");
          }
        }
        doneReading();
        return localObject;
      }
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("numberOfPairs > 1024: ");
      ((StringBuilder)localObject).append(i);
      throw new IOException(((StringBuilder)localObject).toString());
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("numberOfPairs < 0: ");
    ((StringBuilder)localObject).append(i);
    localObject = new IOException(((StringBuilder)localObject).toString());
    throw ((Throwable)localObject);
  }
}
