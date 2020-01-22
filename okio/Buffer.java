package okio;

import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public final class Buffer
  implements BufferedSource, BufferedSink, Cloneable, ByteChannel
{
  private static final byte[] DIGITS = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102 };
  static final int REPLACEMENT_CHARACTER = 65533;
  @Nullable
  Segment head;
  long size;
  
  public Buffer() {}
  
  private ByteString digest(String paramString)
  {
    try
    {
      MessageDigest localMessageDigest = MessageDigest.getInstance(paramString);
      if (head != null)
      {
        paramString = head.data;
        int i = head.pos;
        int j = head.limit;
        int k = head.pos;
        localMessageDigest.update(paramString, i, j - k);
        paramString = head;
        for (;;)
        {
          paramString = next;
          if (paramString == head) {
            break;
          }
          byte[] arrayOfByte = data;
          i = pos;
          j = limit;
          k = pos;
          localMessageDigest.update(arrayOfByte, i, j - k);
        }
      }
      paramString = ByteString.of(localMessageDigest.digest());
      return paramString;
    }
    catch (NoSuchAlgorithmException paramString)
    {
      for (;;) {}
    }
    paramString = new AssertionError();
    throw paramString;
  }
  
  private ByteString hmac(String paramString, ByteString paramByteString)
  {
    try
    {
      Mac localMac = Mac.getInstance(paramString);
      localMac.init(new SecretKeySpec(paramByteString.toByteArray(), paramString));
      if (head != null)
      {
        paramString = head.data;
        int i = head.pos;
        int j = head.limit;
        int k = head.pos;
        localMac.update(paramString, i, j - k);
        paramString = head;
        for (;;)
        {
          paramString = next;
          if (paramString == head) {
            break;
          }
          paramByteString = data;
          i = pos;
          j = limit;
          k = pos;
          localMac.update(paramByteString, i, j - k);
        }
      }
      paramString = ByteString.of(localMac.doFinal());
      return paramString;
    }
    catch (InvalidKeyException paramString)
    {
      throw new IllegalArgumentException(paramString);
      paramString = new AssertionError();
      throw paramString;
    }
    catch (NoSuchAlgorithmException paramString)
    {
      for (;;) {}
    }
  }
  
  private boolean rangeEquals(Segment paramSegment, int paramInt1, ByteString paramByteString, int paramInt2, int paramInt3)
  {
    int i = limit;
    byte[] arrayOfByte = data;
    while (paramInt2 < paramInt3)
    {
      int j = i;
      Segment localSegment = paramSegment;
      int k = paramInt1;
      if (paramInt1 == i)
      {
        localSegment = next;
        arrayOfByte = data;
        k = pos;
        j = limit;
      }
      if (arrayOfByte[k] != paramByteString.getByte(paramInt2)) {
        return false;
      }
      paramInt1 = k + 1;
      paramInt2 += 1;
      i = j;
      paramSegment = localSegment;
    }
    return true;
  }
  
  private void readFrom(InputStream paramInputStream, long paramLong, boolean paramBoolean)
    throws IOException
  {
    if (paramInputStream != null) {
      for (;;)
      {
        if ((paramLong <= 0L) && (!paramBoolean)) {
          return;
        }
        Segment localSegment = writableSegment(1);
        int i = (int)Math.min(paramLong, 8192 - limit);
        i = paramInputStream.read(data, limit, i);
        if (i == -1)
        {
          if (paramBoolean) {
            return;
          }
          throw new EOFException();
        }
        limit += i;
        long l1 = size;
        long l2 = i;
        size = (l1 + l2);
        paramLong -= l2;
      }
    }
    paramInputStream = new IllegalArgumentException("in == null");
    throw paramInputStream;
  }
  
  public Buffer buffer()
  {
    return this;
  }
  
  public final void clear()
  {
    long l = size;
    try
    {
      skip(l);
      return;
    }
    catch (EOFException localEOFException)
    {
      throw new AssertionError(localEOFException);
    }
  }
  
  public Buffer clone()
  {
    Buffer localBuffer = new Buffer();
    if (size == 0L) {
      return localBuffer;
    }
    head = head.sharedCopy();
    Segment localSegment = head;
    prev = localSegment;
    next = localSegment;
    localSegment = head;
    for (;;)
    {
      localSegment = next;
      if (localSegment == head) {
        break;
      }
      head.prev.push(localSegment.sharedCopy());
    }
    size = size;
    return localBuffer;
  }
  
  public void close() {}
  
  public final long completeSegmentByteCount()
  {
    long l2 = size;
    if (l2 == 0L) {
      return 0L;
    }
    Segment localSegment = head.prev;
    long l1 = l2;
    if (limit < 8192)
    {
      l1 = l2;
      if (owner) {
        l1 = l2 - (limit - pos);
      }
    }
    return l1;
  }
  
  public final Buffer copyTo(OutputStream paramOutputStream)
    throws IOException
  {
    return copyTo(paramOutputStream, 0L, size);
  }
  
  public final Buffer copyTo(OutputStream paramOutputStream, long paramLong1, long paramLong2)
    throws IOException
  {
    if (paramOutputStream != null)
    {
      Util.checkOffsetAndCount(size, paramLong1, paramLong2);
      if (paramLong2 == 0L) {
        return this;
      }
      Segment localSegment2;
      long l1;
      long l2;
      for (Segment localSegment1 = head;; localSegment1 = next)
      {
        localSegment2 = localSegment1;
        l1 = paramLong1;
        l2 = paramLong2;
        if (paramLong1 < limit - pos) {
          break;
        }
        paramLong1 -= limit - pos;
      }
      while (l2 > 0L)
      {
        int i = (int)(pos + l1);
        int j = (int)Math.min(limit - i, l2);
        paramOutputStream.write(data, i, j);
        l2 -= j;
        localSegment2 = next;
        l1 = 0L;
      }
      return this;
    }
    paramOutputStream = new IllegalArgumentException("out == null");
    throw paramOutputStream;
  }
  
  public final Buffer copyTo(Buffer paramBuffer, long paramLong1, long paramLong2)
  {
    if (paramBuffer != null)
    {
      Util.checkOffsetAndCount(size, paramLong1, paramLong2);
      if (paramLong2 == 0L) {
        return this;
      }
      size += paramLong2;
      Segment localSegment2;
      long l1;
      long l2;
      for (Segment localSegment1 = head;; localSegment1 = next)
      {
        localSegment2 = localSegment1;
        l1 = paramLong1;
        l2 = paramLong2;
        if (paramLong1 < limit - pos) {
          break;
        }
        paramLong1 -= limit - pos;
      }
      while (l2 > 0L)
      {
        localSegment1 = localSegment2.sharedCopy();
        pos = ((int)(pos + l1));
        limit = Math.min(pos + (int)l2, limit);
        Segment localSegment3 = head;
        if (localSegment3 == null)
        {
          prev = localSegment1;
          next = localSegment1;
          head = localSegment1;
        }
        else
        {
          prev.push(localSegment1);
        }
        l2 -= limit - pos;
        localSegment2 = next;
        l1 = 0L;
      }
      return this;
    }
    paramBuffer = new IllegalArgumentException("out == null");
    throw paramBuffer;
  }
  
  public BufferedSink emit()
  {
    return this;
  }
  
  public Buffer emitCompleteSegments()
  {
    return this;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if (!(paramObject instanceof Buffer)) {
      return false;
    }
    paramObject = (Buffer)paramObject;
    long l2 = size;
    if (l2 != size) {
      return false;
    }
    long l1 = 0L;
    if (l2 == 0L) {
      return true;
    }
    Object localObject2 = head;
    paramObject = head;
    int j = pos;
    int m;
    for (int i = pos; l1 < size; i = m)
    {
      l2 = Math.min(limit - j, limit - i);
      int k = 0;
      while (k < l2)
      {
        if (data[j] != data[i]) {
          return false;
        }
        k += 1;
        j += 1;
        i += 1;
      }
      Object localObject1 = localObject2;
      k = j;
      if (j == limit)
      {
        localObject1 = next;
        k = pos;
      }
      Object localObject3 = paramObject;
      m = i;
      if (i == limit)
      {
        localObject3 = next;
        m = pos;
      }
      l1 += l2;
      localObject2 = localObject1;
      paramObject = localObject3;
      j = k;
    }
    return true;
  }
  
  public boolean exhausted()
  {
    return size == 0L;
  }
  
  public void flush() {}
  
  public Buffer getBuffer()
  {
    return this;
  }
  
  public final byte getByte(long paramLong)
  {
    Util.checkOffsetAndCount(size, paramLong, 1L);
    long l = size;
    if (l - paramLong > paramLong) {
      for (localObject = head;; localObject = next)
      {
        l = limit - pos;
        if (paramLong < l) {
          return data[(pos + (int)paramLong)];
        }
        paramLong -= l;
      }
    }
    paramLong -= l;
    Object localObject = head;
    Segment localSegment;
    do
    {
      localSegment = prev;
      l = paramLong + (limit - pos);
      localObject = localSegment;
      paramLong = l;
    } while (l < 0L);
    return data[(pos + (int)l)];
  }
  
  public int hashCode()
  {
    Object localObject = head;
    if (localObject == null) {
      return 0;
    }
    int i = 1;
    int j;
    Segment localSegment;
    do
    {
      int k = pos;
      int m = limit;
      j = i;
      while (k < m)
      {
        j = j * 31 + data[k];
        k += 1;
      }
      localSegment = next;
      localObject = localSegment;
      i = j;
    } while (localSegment != head);
    return j;
  }
  
  public final ByteString hmacSha1(ByteString paramByteString)
  {
    return hmac("HmacSHA1", paramByteString);
  }
  
  public final ByteString hmacSha256(ByteString paramByteString)
  {
    return hmac("HmacSHA256", paramByteString);
  }
  
  public final ByteString hmacSha512(ByteString paramByteString)
  {
    return hmac("HmacSHA512", paramByteString);
  }
  
  public long indexOf(byte paramByte)
  {
    return indexOf(paramByte, 0L, Long.MAX_VALUE);
  }
  
  public long indexOf(byte paramByte, long paramLong)
  {
    return indexOf(paramByte, paramLong, Long.MAX_VALUE);
  }
  
  public long indexOf(byte paramByte, long paramLong1, long paramLong2)
  {
    long l1 = 0L;
    if ((paramLong1 >= 0L) && (paramLong2 >= paramLong1))
    {
      long l2 = size;
      if (paramLong2 <= l2) {
        l2 = paramLong2;
      }
      if (paramLong1 == l2) {
        return -1L;
      }
      localObject1 = head;
      if (localObject1 == null) {
        return -1L;
      }
      long l3 = size;
      paramLong2 = l1;
      Object localObject2 = localObject1;
      if (l3 - paramLong1 < paramLong1)
      {
        l1 = l3;
        localObject2 = localObject1;
        for (;;)
        {
          localObject1 = localObject2;
          l3 = paramLong1;
          paramLong2 = l1;
          if (l1 <= paramLong1) {
            break;
          }
          localObject2 = prev;
          l1 -= limit - pos;
        }
      }
      for (;;)
      {
        l1 = paramLong2;
        long l4 = limit - pos + paramLong2;
        localObject1 = localObject2;
        l3 = paramLong1;
        paramLong2 = l1;
        if (l4 >= paramLong1) {
          break;
        }
        localObject2 = next;
        paramLong2 = l4;
      }
      while (paramLong2 < l2)
      {
        localObject2 = data;
        int j = (int)Math.min(limit, pos + l2 - paramLong2);
        int i = (int)(pos + l3 - paramLong2);
        while (i < j)
        {
          if (localObject2[i] == paramByte) {
            return i - pos + paramLong2;
          }
          i += 1;
        }
        l3 = limit - pos + paramLong2;
        localObject1 = next;
        paramLong2 = l3;
      }
      return -1L;
    }
    Object localObject1 = new IllegalArgumentException(String.format("size=%s fromIndex=%s toIndex=%s", new Object[] { Long.valueOf(size), Long.valueOf(paramLong1), Long.valueOf(paramLong2) }));
    throw ((Throwable)localObject1);
  }
  
  public long indexOf(ByteString paramByteString)
    throws IOException
  {
    return indexOf(paramByteString, 0L);
  }
  
  public long indexOf(ByteString paramByteString, long paramLong)
    throws IOException
  {
    if (paramByteString.size() != 0)
    {
      long l1 = 0L;
      if (paramLong >= 0L)
      {
        Object localObject2 = head;
        if (localObject2 == null) {
          return -1L;
        }
        long l2 = size;
        Object localObject1 = localObject2;
        if (l2 - paramLong < paramLong)
        {
          localObject1 = localObject2;
          for (;;)
          {
            localObject2 = localObject1;
            l1 = l2;
            if (l2 <= paramLong) {
              break;
            }
            localObject1 = prev;
            l2 -= limit - pos;
          }
        }
        for (;;)
        {
          l2 = l1;
          long l3 = limit - pos + l1;
          localObject2 = localObject1;
          l1 = l2;
          if (l3 >= paramLong) {
            break;
          }
          localObject1 = next;
          l1 = l3;
        }
        int j = paramByteString.getByte(0);
        int k = paramByteString.size();
        l2 = 1L + (size - k);
        while (l1 < l2)
        {
          localObject1 = data;
          int m = (int)Math.min(limit, pos + l2 - l1);
          int i = (int)(pos + paramLong - l1);
          while (i < m)
          {
            if ((localObject1[i] == j) && (rangeEquals((Segment)localObject2, i + 1, paramByteString, 1, k))) {
              return i - pos + l1;
            }
            i += 1;
          }
          paramLong = limit - pos + l1;
          localObject2 = next;
          l1 = paramLong;
        }
        return -1L;
      }
      throw new IllegalArgumentException("fromIndex < 0");
    }
    paramByteString = new IllegalArgumentException("bytes is empty");
    throw paramByteString;
  }
  
  public long indexOfElement(ByteString paramByteString)
  {
    return indexOfElement(paramByteString, 0L);
  }
  
  public long indexOfElement(ByteString paramByteString, long paramLong)
  {
    long l1 = 0L;
    if (paramLong >= 0L)
    {
      Object localObject1 = head;
      if (localObject1 == null) {
        return -1L;
      }
      long l2 = size;
      Object localObject2 = localObject1;
      if (l2 - paramLong < paramLong)
      {
        localObject2 = localObject1;
        for (;;)
        {
          localObject1 = localObject2;
          l1 = l2;
          if (l2 <= paramLong) {
            break;
          }
          localObject2 = prev;
          l2 -= limit - pos;
        }
      }
      for (;;)
      {
        l2 = l1;
        long l3 = limit - pos + l1;
        localObject1 = localObject2;
        l1 = l2;
        if (l3 >= paramLong) {
          break;
        }
        localObject2 = next;
        l1 = l3;
      }
      int j;
      int k;
      int i;
      int m;
      int n;
      if (paramByteString.size() == 2)
      {
        j = paramByteString.getByte(0);
        k = paramByteString.getByte(1);
        while (l1 < size)
        {
          paramByteString = data;
          i = (int)(pos + paramLong - l1);
          m = limit;
          while (i < m)
          {
            n = paramByteString[i];
            if ((n != j) && (n != k))
            {
              i += 1;
            }
            else
            {
              j = pos;
              return i - j + l1;
            }
          }
          paramLong = limit - pos + l1;
          localObject1 = next;
          l1 = paramLong;
        }
      }
      paramByteString = paramByteString.internalArray();
      for (;;)
      {
        if (l1 >= size) {
          break label440;
        }
        localObject2 = data;
        i = (int)(pos + paramLong - l1);
        k = limit;
        for (;;)
        {
          if (i >= k) {
            break label391;
          }
          m = localObject2[i];
          n = paramByteString.length;
          j = 0;
          for (;;)
          {
            if (j >= n) {
              break label382;
            }
            if (m == paramByteString[j])
            {
              j = pos;
              break;
            }
            j += 1;
          }
          label382:
          i += 1;
        }
        label391:
        paramLong = limit - pos + l1;
        localObject1 = next;
        l1 = paramLong;
      }
      return -1L;
    }
    paramByteString = new IllegalArgumentException("fromIndex < 0");
    throw paramByteString;
    label440:
    return -1L;
  }
  
  public InputStream inputStream()
  {
    new InputStream()
    {
      public int available()
      {
        return (int)Math.min(size, 2147483647L);
      }
      
      public void close() {}
      
      public int read()
      {
        if (size > 0L) {
          return readByte() & 0xFF;
        }
        return -1;
      }
      
      public int read(byte[] paramAnonymousArrayOfByte, int paramAnonymousInt1, int paramAnonymousInt2)
      {
        return Buffer.this.read(paramAnonymousArrayOfByte, paramAnonymousInt1, paramAnonymousInt2);
      }
      
      public String toString()
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(Buffer.this);
        localStringBuilder.append(".inputStream()");
        return localStringBuilder.toString();
      }
    };
  }
  
  public boolean isOpen()
  {
    return true;
  }
  
  public final ByteString md5()
  {
    return digest("MD5");
  }
  
  public OutputStream outputStream()
  {
    new OutputStream()
    {
      public void close() {}
      
      public void flush() {}
      
      public String toString()
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(Buffer.this);
        localStringBuilder.append(".outputStream()");
        return localStringBuilder.toString();
      }
      
      public void write(int paramAnonymousInt)
      {
        writeByte((byte)paramAnonymousInt);
      }
      
      public void write(byte[] paramAnonymousArrayOfByte, int paramAnonymousInt1, int paramAnonymousInt2)
      {
        write(paramAnonymousArrayOfByte, paramAnonymousInt1, paramAnonymousInt2);
      }
    };
  }
  
  public BufferedSource peek()
  {
    return Okio.buffer(new PeekSource(this));
  }
  
  public boolean rangeEquals(long paramLong, ByteString paramByteString)
  {
    return rangeEquals(paramLong, paramByteString, 0, paramByteString.size());
  }
  
  public boolean rangeEquals(long paramLong, ByteString paramByteString, int paramInt1, int paramInt2)
  {
    if ((paramLong >= 0L) && (paramInt1 >= 0) && (paramInt2 >= 0) && (size - paramLong >= paramInt2))
    {
      if (paramByteString.size() - paramInt1 < paramInt2) {
        return false;
      }
      int i = 0;
      while (i < paramInt2)
      {
        if (getByte(i + paramLong) != paramByteString.getByte(paramInt1 + i)) {
          return false;
        }
        i += 1;
      }
      return true;
    }
    return false;
  }
  
  public int read(ByteBuffer paramByteBuffer)
    throws IOException
  {
    Segment localSegment = head;
    if (localSegment == null) {
      return -1;
    }
    int i = Math.min(paramByteBuffer.remaining(), limit - pos);
    paramByteBuffer.put(data, pos, i);
    pos += i;
    size -= i;
    if (pos == limit)
    {
      head = localSegment.pop();
      SegmentPool.recycle(localSegment);
    }
    return i;
  }
  
  public int read(byte[] paramArrayOfByte)
  {
    return read(paramArrayOfByte, 0, paramArrayOfByte.length);
  }
  
  public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    Util.checkOffsetAndCount(paramArrayOfByte.length, paramInt1, paramInt2);
    Segment localSegment = head;
    if (localSegment == null) {
      return -1;
    }
    paramInt2 = Math.min(paramInt2, limit - pos);
    System.arraycopy(data, pos, paramArrayOfByte, paramInt1, paramInt2);
    pos += paramInt2;
    size -= paramInt2;
    if (pos == limit)
    {
      head = localSegment.pop();
      SegmentPool.recycle(localSegment);
    }
    return paramInt2;
  }
  
  public long read(Buffer paramBuffer, long paramLong)
  {
    if (paramBuffer != null)
    {
      if (paramLong >= 0L)
      {
        long l2 = size;
        if (l2 == 0L) {
          return -1L;
        }
        long l1 = paramLong;
        if (paramLong > l2) {
          l1 = l2;
        }
        paramBuffer.write(this, l1);
        return l1;
      }
      paramBuffer = new StringBuilder();
      paramBuffer.append("byteCount < 0: ");
      paramBuffer.append(paramLong);
      throw new IllegalArgumentException(paramBuffer.toString());
    }
    throw new IllegalArgumentException("sink == null");
  }
  
  public long readAll(Sink paramSink)
    throws IOException
  {
    long l = size;
    if (l > 0L) {
      paramSink.write(this, l);
    }
    return l;
  }
  
  public final UnsafeCursor readAndWriteUnsafe()
  {
    return readAndWriteUnsafe(new UnsafeCursor());
  }
  
  public final UnsafeCursor readAndWriteUnsafe(UnsafeCursor paramUnsafeCursor)
  {
    if (buffer == null)
    {
      buffer = this;
      readWrite = true;
      return paramUnsafeCursor;
    }
    throw new IllegalStateException("already attached to a buffer");
  }
  
  public byte readByte()
  {
    if (size != 0L)
    {
      Segment localSegment = head;
      int i = pos;
      int j = limit;
      byte[] arrayOfByte = data;
      int k = i + 1;
      byte b = arrayOfByte[i];
      size -= 1L;
      if (k == j)
      {
        head = localSegment.pop();
        SegmentPool.recycle(localSegment);
        return b;
      }
      pos = k;
      return b;
    }
    throw new IllegalStateException("size == 0");
  }
  
  public byte[] readByteArray()
  {
    long l = size;
    try
    {
      byte[] arrayOfByte = readByteArray(l);
      return arrayOfByte;
    }
    catch (EOFException localEOFException)
    {
      throw new AssertionError(localEOFException);
    }
  }
  
  public byte[] readByteArray(long paramLong)
    throws EOFException
  {
    Util.checkOffsetAndCount(size, 0L, paramLong);
    if (paramLong <= 2147483647L)
    {
      localObject = new byte[(int)paramLong];
      readFully((byte[])localObject);
      return localObject;
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("byteCount > Integer.MAX_VALUE: ");
    ((StringBuilder)localObject).append(paramLong);
    throw new IllegalArgumentException(((StringBuilder)localObject).toString());
  }
  
  public ByteString readByteString()
  {
    return new ByteString(readByteArray());
  }
  
  public ByteString readByteString(long paramLong)
    throws EOFException
  {
    return new ByteString(readByteArray(paramLong));
  }
  
  public long readDecimalLong()
  {
    long l1 = size;
    long l2 = 0L;
    if (l1 != 0L)
    {
      int i1 = 0;
      long l3 = -7L;
      int n = 0;
      int i = 0;
      int m;
      int j;
      label306:
      do
      {
        localObject1 = head;
        Object localObject2 = data;
        int k = pos;
        int i3 = limit;
        m = n;
        j = i1;
        l1 = l2;
        l2 = l3;
        int i2;
        for (;;)
        {
          i2 = i;
          if (k >= i3) {
            break label306;
          }
          n = localObject2[k];
          if ((n >= 48) && (n <= 57))
          {
            i1 = 48 - n;
            if ((l1 >= -922337203685477580L) && ((l1 != -922337203685477580L) || (i1 >= l2)))
            {
              l1 = l1 * 10L + i1;
            }
            else
            {
              localObject1 = new Buffer().writeDecimalLong(l1).writeByte(n);
              if (m == 0) {
                ((Buffer)localObject1).readByte();
              }
              localObject2 = new StringBuilder();
              ((StringBuilder)localObject2).append("Number too large: ");
              ((StringBuilder)localObject2).append(((Buffer)localObject1).readUtf8());
              throw new NumberFormatException(((StringBuilder)localObject2).toString());
            }
          }
          else
          {
            if ((n != 45) || (j != 0)) {
              break;
            }
            l2 -= 1L;
            m = 1;
          }
          k += 1;
          j += 1;
        }
        if (j != 0)
        {
          i2 = 1;
        }
        else
        {
          localObject1 = new StringBuilder();
          ((StringBuilder)localObject1).append("Expected leading [0-9] or '-' character but was 0x");
          ((StringBuilder)localObject1).append(Integer.toHexString(n));
          throw new NumberFormatException(((StringBuilder)localObject1).toString());
        }
        if (k == i3)
        {
          head = ((Segment)localObject1).pop();
          SegmentPool.recycle((Segment)localObject1);
        }
        else
        {
          pos = k;
        }
        if (i2 != 0) {
          break;
        }
        l3 = l2;
        l2 = l1;
        i1 = j;
        n = m;
        i = i2;
      } while (head != null);
      size -= j;
      if (m != 0) {
        return l1;
      }
      return -l1;
    }
    Object localObject1 = new IllegalStateException("size == 0");
    throw ((Throwable)localObject1);
  }
  
  public final Buffer readFrom(InputStream paramInputStream)
    throws IOException
  {
    readFrom(paramInputStream, Long.MAX_VALUE, true);
    return this;
  }
  
  public final Buffer readFrom(InputStream paramInputStream, long paramLong)
    throws IOException
  {
    if (paramLong >= 0L)
    {
      readFrom(paramInputStream, paramLong, false);
      return this;
    }
    paramInputStream = new StringBuilder();
    paramInputStream.append("byteCount < 0: ");
    paramInputStream.append(paramLong);
    throw new IllegalArgumentException(paramInputStream.toString());
  }
  
  public void readFully(Buffer paramBuffer, long paramLong)
    throws EOFException
  {
    long l = size;
    if (l >= paramLong)
    {
      paramBuffer.write(this, paramLong);
      return;
    }
    paramBuffer.write(this, l);
    throw new EOFException();
  }
  
  public void readFully(byte[] paramArrayOfByte)
    throws EOFException
  {
    int i = 0;
    while (i < paramArrayOfByte.length)
    {
      int j = read(paramArrayOfByte, i, paramArrayOfByte.length - i);
      if (j != -1) {
        i += j;
      } else {
        throw new EOFException();
      }
    }
  }
  
  public long readHexadecimalUnsignedLong()
  {
    if (size != 0L)
    {
      int i = 0;
      long l1 = 0L;
      int m = 0;
      int j;
      long l2;
      label237:
      label289:
      do
      {
        localObject1 = head;
        Object localObject2 = data;
        int k = pos;
        int n = limit;
        j = m;
        l2 = l1;
        int i1;
        for (;;)
        {
          m = i;
          if (k >= n) {
            break label289;
          }
          i1 = localObject2[k];
          if ((i1 >= 48) && (i1 <= 57))
          {
            m = i1 - 48;
          }
          else
          {
            if ((i1 >= 97) && (i1 <= 102)) {}
            for (m = i1 - 97;; m = i1 - 65)
            {
              m += 10;
              break;
              if ((i1 < 65) || (i1 > 70)) {
                break label237;
              }
            }
          }
          if ((0xF000000000000000 & l2) != 0L) {
            break;
          }
          l2 = l2 << 4 | m;
          k += 1;
          j += 1;
        }
        localObject1 = new Buffer().writeHexadecimalUnsignedLong(l2).writeByte(i1);
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append("Number too large: ");
        ((StringBuilder)localObject2).append(((Buffer)localObject1).readUtf8());
        throw new NumberFormatException(((StringBuilder)localObject2).toString());
        if (j != 0)
        {
          m = 1;
        }
        else
        {
          localObject1 = new StringBuilder();
          ((StringBuilder)localObject1).append("Expected leading [0-9a-fA-F] character but was 0x");
          ((StringBuilder)localObject1).append(Integer.toHexString(i1));
          throw new NumberFormatException(((StringBuilder)localObject1).toString());
        }
        if (k == n)
        {
          head = ((Segment)localObject1).pop();
          SegmentPool.recycle((Segment)localObject1);
        }
        else
        {
          pos = k;
        }
        if (m != 0) {
          break;
        }
        l1 = l2;
        i = m;
        m = j;
      } while (head != null);
      size -= j;
      return l2;
    }
    Object localObject1 = new IllegalStateException("size == 0");
    throw ((Throwable)localObject1);
  }
  
  public int readInt()
  {
    if (size >= 4L)
    {
      localObject = head;
      int j = pos;
      int i = limit;
      if (i - j < 4) {
        return (readByte() & 0xFF) << 24 | (readByte() & 0xFF) << 16 | (readByte() & 0xFF) << 8 | readByte() & 0xFF;
      }
      byte[] arrayOfByte = data;
      int k = j + 1;
      j = arrayOfByte[j];
      int n = k + 1;
      k = arrayOfByte[k];
      int m = n + 1;
      int i1 = arrayOfByte[n];
      n = m + 1;
      j = (j & 0xFF) << 24 | (k & 0xFF) << 16 | (i1 & 0xFF) << 8 | arrayOfByte[m] & 0xFF;
      size -= 4L;
      if (n == i)
      {
        head = ((Segment)localObject).pop();
        SegmentPool.recycle((Segment)localObject);
        return j;
      }
      pos = n;
      return j;
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("size < 4: ");
    ((StringBuilder)localObject).append(size);
    throw new IllegalStateException(((StringBuilder)localObject).toString());
  }
  
  public int readIntLe()
  {
    return Util.reverseBytesInt(readInt());
  }
  
  public long readLong()
  {
    if (size >= 8L)
    {
      localObject = head;
      int k = pos;
      int i = limit;
      if (i - k < 8) {
        return (readInt() & 0xFFFFFFFF) << 32 | 0xFFFFFFFF & readInt();
      }
      byte[] arrayOfByte = data;
      int j = k + 1;
      long l1 = arrayOfByte[k];
      k = j + 1;
      long l2 = arrayOfByte[j];
      j = k + 1;
      long l3 = arrayOfByte[k];
      k = j + 1;
      long l4 = arrayOfByte[j];
      j = k + 1;
      long l5 = arrayOfByte[k];
      k = j + 1;
      long l6 = arrayOfByte[j];
      j = k + 1;
      long l7 = arrayOfByte[k];
      k = j + 1;
      l1 = arrayOfByte[j] & 0xFF | (l7 & 0xFF) << 8 | (l1 & 0xFF) << 56 | (l2 & 0xFF) << 48 | (l3 & 0xFF) << 40 | (l4 & 0xFF) << 32 | (l5 & 0xFF) << 24 | (l6 & 0xFF) << 16;
      size -= 8L;
      if (k == i)
      {
        head = ((Segment)localObject).pop();
        SegmentPool.recycle((Segment)localObject);
        return l1;
      }
      pos = k;
      return l1;
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("size < 8: ");
    ((StringBuilder)localObject).append(size);
    throw new IllegalStateException(((StringBuilder)localObject).toString());
  }
  
  public long readLongLe()
  {
    return Util.reverseBytesLong(readLong());
  }
  
  public short readShort()
  {
    if (size >= 2L)
    {
      localObject = head;
      int k = pos;
      int i = limit;
      if (i - k < 2) {
        return (short)((readByte() & 0xFF) << 8 | readByte() & 0xFF);
      }
      byte[] arrayOfByte = data;
      int j = k + 1;
      k = arrayOfByte[k];
      int m = j + 1;
      j = arrayOfByte[j];
      size -= 2L;
      if (m == i)
      {
        head = ((Segment)localObject).pop();
        SegmentPool.recycle((Segment)localObject);
      }
      else
      {
        pos = m;
      }
      return (short)((k & 0xFF) << 8 | j & 0xFF);
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("size < 2: ");
    ((StringBuilder)localObject).append(size);
    throw new IllegalStateException(((StringBuilder)localObject).toString());
  }
  
  public short readShortLe()
  {
    return Util.reverseBytesShort(readShort());
  }
  
  public String readString(long paramLong, Charset paramCharset)
    throws EOFException
  {
    Util.checkOffsetAndCount(size, 0L, paramLong);
    if (paramCharset != null)
    {
      if (paramLong <= 2147483647L)
      {
        if (paramLong == 0L) {
          return "";
        }
        Segment localSegment = head;
        if (pos + paramLong > limit) {
          return new String(readByteArray(paramLong), paramCharset);
        }
        paramCharset = new String(data, pos, (int)paramLong, paramCharset);
        pos = ((int)(pos + paramLong));
        size -= paramLong;
        if (pos == limit)
        {
          head = localSegment.pop();
          SegmentPool.recycle(localSegment);
          return paramCharset;
        }
      }
      else
      {
        paramCharset = new StringBuilder();
        paramCharset.append("byteCount > Integer.MAX_VALUE: ");
        paramCharset.append(paramLong);
        throw new IllegalArgumentException(paramCharset.toString());
      }
    }
    else {
      throw new IllegalArgumentException("charset == null");
    }
    return paramCharset;
  }
  
  public String readString(Charset paramCharset)
  {
    long l = size;
    try
    {
      paramCharset = readString(l, paramCharset);
      return paramCharset;
    }
    catch (EOFException paramCharset)
    {
      throw new AssertionError(paramCharset);
    }
  }
  
  public final UnsafeCursor readUnsafe()
  {
    return readUnsafe(new UnsafeCursor());
  }
  
  public final UnsafeCursor readUnsafe(UnsafeCursor paramUnsafeCursor)
  {
    if (buffer == null)
    {
      buffer = this;
      readWrite = false;
      return paramUnsafeCursor;
    }
    throw new IllegalStateException("already attached to a buffer");
  }
  
  public String readUtf8()
  {
    long l = size;
    Object localObject = Util.UTF_8;
    try
    {
      localObject = readString(l, (Charset)localObject);
      return localObject;
    }
    catch (EOFException localEOFException)
    {
      throw new AssertionError(localEOFException);
    }
  }
  
  public String readUtf8(long paramLong)
    throws EOFException
  {
    return readString(paramLong, Util.UTF_8);
  }
  
  public int readUtf8CodePoint()
    throws EOFException
  {
    if (size != 0L)
    {
      int m = getByte(0L);
      int n = 1;
      int i;
      int j;
      int k;
      if ((m & 0x80) == 0)
      {
        i = m & 0x7F;
        j = 1;
        k = 0;
      }
      else if ((m & 0xE0) == 192)
      {
        i = m & 0x1F;
        j = 2;
        k = 128;
      }
      else if ((m & 0xF0) == 224)
      {
        i = m & 0xF;
        j = 3;
        k = 2048;
      }
      else
      {
        if ((m & 0xF8) != 240) {
          break label335;
        }
        i = m & 0x7;
        j = 4;
        k = 65536;
      }
      long l2 = size;
      long l1 = j;
      if (l2 >= l1)
      {
        m = i;
        i = n;
        while (i < j)
        {
          l2 = i;
          n = getByte(l2);
          if ((n & 0xC0) == 128)
          {
            m = m << 6 | n & 0x3F;
            i += 1;
          }
          else
          {
            skip(l2);
            return 65533;
          }
        }
        skip(l1);
        if (m > 1114111) {
          return 65533;
        }
        if ((m >= 55296) && (m <= 57343)) {
          return 65533;
        }
        if (m < k) {
          return 65533;
        }
        return m;
      }
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("size < ");
      ((StringBuilder)localObject).append(j);
      ((StringBuilder)localObject).append(": ");
      ((StringBuilder)localObject).append(size);
      ((StringBuilder)localObject).append(" (to read code point prefixed 0x");
      ((StringBuilder)localObject).append(Integer.toHexString(m));
      ((StringBuilder)localObject).append(")");
      throw new EOFException(((StringBuilder)localObject).toString());
      label335:
      skip(1L);
      return 65533;
    }
    Object localObject = new EOFException();
    throw ((Throwable)localObject);
  }
  
  public String readUtf8Line()
    throws EOFException
  {
    long l = indexOf((byte)10);
    if (l == -1L)
    {
      l = size;
      if (l != 0L) {
        return readUtf8(l);
      }
      return null;
    }
    return readUtf8Line(l);
  }
  
  String readUtf8Line(long paramLong)
    throws EOFException
  {
    if (paramLong > 0L)
    {
      long l = paramLong - 1L;
      if (getByte(l) == 13)
      {
        str = readUtf8(l);
        skip(2L);
        return str;
      }
    }
    String str = readUtf8(paramLong);
    skip(1L);
    return str;
  }
  
  public String readUtf8LineStrict()
    throws EOFException
  {
    return readUtf8LineStrict(Long.MAX_VALUE);
  }
  
  public String readUtf8LineStrict(long paramLong)
    throws EOFException
  {
    if (paramLong >= 0L)
    {
      long l1 = Long.MAX_VALUE;
      if (paramLong != Long.MAX_VALUE) {
        l1 = paramLong + 1L;
      }
      long l2 = indexOf((byte)10, 0L, l1);
      if (l2 != -1L) {
        return readUtf8Line(l2);
      }
      if ((l1 < size()) && (getByte(l1 - 1L) == 13) && (getByte(l1) == 10)) {
        return readUtf8Line(l1);
      }
      localObject = new Buffer();
      copyTo((Buffer)localObject, 0L, Math.min(32L, size()));
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("\\n not found: limit=");
      localStringBuilder.append(Math.min(size(), paramLong));
      localStringBuilder.append(" content=");
      localStringBuilder.append(((Buffer)localObject).readByteString().hex());
      localStringBuilder.append('?');
      throw new EOFException(localStringBuilder.toString());
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("limit < 0: ");
    ((StringBuilder)localObject).append(paramLong);
    throw new IllegalArgumentException(((StringBuilder)localObject).toString());
  }
  
  public boolean request(long paramLong)
  {
    return size >= paramLong;
  }
  
  public void require(long paramLong)
    throws EOFException
  {
    if (size >= paramLong) {
      return;
    }
    throw new EOFException();
  }
  
  List segmentSizes()
  {
    if (head == null) {
      return Collections.emptyList();
    }
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(Integer.valueOf(head.limit - head.pos));
    Segment localSegment = head;
    for (;;)
    {
      localSegment = next;
      if (localSegment == head) {
        break;
      }
      localArrayList.add(Integer.valueOf(limit - pos));
    }
    return localArrayList;
  }
  
  public int select(Options paramOptions)
  {
    int i = selectPrefix(paramOptions, false);
    if (i == -1) {
      return -1;
    }
    long l = byteStrings[i].size();
    try
    {
      skip(l);
      return i;
    }
    catch (EOFException paramOptions)
    {
      for (;;) {}
    }
    throw new AssertionError();
  }
  
  int selectPrefix(Options paramOptions, boolean paramBoolean)
  {
    Segment localSegment = head;
    if (localSegment == null)
    {
      if (paramBoolean) {
        return -2;
      }
      return paramOptions.indexOf(ByteString.EMPTY);
    }
    int[] arrayOfInt = trie;
    int i = pos;
    int j = limit;
    int m = -1;
    paramOptions = localSegment;
    byte[] arrayOfByte = data;
    int k = 0;
    int i1 = k + 1;
    int i4 = arrayOfInt[k];
    int n = i1 + 1;
    k = arrayOfInt[i1];
    if (k != -1) {
      m = k;
    }
    Options localOptions;
    if (paramOptions != null)
    {
      if (i4 < 0)
      {
        k = n;
        localOptions = paramOptions;
        i1 = j;
      }
    }
    else {
      for (;;)
      {
        int i3 = i + 1;
        i = arrayOfByte[i];
        int i2 = k + 1;
        if ((i & 0xFF) != arrayOfInt[k]) {
          return m;
        }
        if (i2 == n + i4 * -1) {
          k = 1;
        } else {
          k = 0;
        }
        j = i1;
        paramOptions = localOptions;
        i = i3;
        if (i3 == i1)
        {
          paramOptions = next;
          i = pos;
          arrayOfByte = data;
          j = limit;
          if (paramOptions == localSegment)
          {
            if (k == 0)
            {
              if (paramBoolean) {
                return -2;
              }
              return m;
            }
            paramOptions = null;
          }
        }
        if (k != 0)
        {
          k = arrayOfInt[i2];
          break;
        }
        k = i2;
        i1 = j;
        localOptions = paramOptions;
      }
    }
    k = i + 1;
    i1 = arrayOfByte[i];
    i = n;
    for (;;)
    {
      if (i == n + i4) {
        return m;
      }
      if ((i1 & 0xFF) == arrayOfInt[i])
      {
        n = arrayOfInt[(i + i4)];
        if (k == j)
        {
          paramOptions = next;
          i = pos;
          j = limit;
          arrayOfByte = data;
          if (paramOptions == localSegment)
          {
            paramOptions = null;
            k = n;
          }
          else
          {
            k = n;
          }
        }
        else
        {
          i = k;
          k = n;
        }
        if (k >= 0) {
          return k;
        }
        k = -k;
        break;
      }
      i += 1;
    }
  }
  
  public final ByteString sha1()
  {
    return digest("SHA-1");
  }
  
  public final ByteString sha256()
  {
    return digest("SHA-256");
  }
  
  public final ByteString sha512()
  {
    return digest("SHA-512");
  }
  
  public final long size()
  {
    return size;
  }
  
  public void skip(long paramLong)
    throws EOFException
  {
    while (paramLong > 0L)
    {
      Segment localSegment = head;
      if (localSegment != null)
      {
        int i = (int)Math.min(paramLong, limit - head.pos);
        long l1 = size;
        long l2 = i;
        size = (l1 - l2);
        l1 = paramLong - l2;
        localSegment = head;
        pos += i;
        paramLong = l1;
        if (head.pos == head.limit)
        {
          localSegment = head;
          head = localSegment.pop();
          SegmentPool.recycle(localSegment);
          paramLong = l1;
        }
      }
      else
      {
        throw new EOFException();
      }
    }
  }
  
  public final ByteString snapshot()
  {
    long l = size;
    if (l <= 2147483647L) {
      return snapshot((int)l);
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("size > Integer.MAX_VALUE: ");
    localStringBuilder.append(size);
    throw new IllegalArgumentException(localStringBuilder.toString());
  }
  
  public final ByteString snapshot(int paramInt)
  {
    if (paramInt == 0) {
      return ByteString.EMPTY;
    }
    return new SegmentedByteString(this, paramInt);
  }
  
  public Timeout timeout()
  {
    return Timeout.NONE;
  }
  
  public String toString()
  {
    return snapshot().toString();
  }
  
  Segment writableSegment(int paramInt)
  {
    Segment localSegment;
    if ((paramInt >= 1) && (paramInt <= 8192))
    {
      localSegment = head;
      if (localSegment == null)
      {
        head = SegmentPool.take();
        localSegment = head;
        prev = localSegment;
        next = localSegment;
        return localSegment;
      }
      localSegment = prev;
      if ((limit + paramInt > 8192) || (!owner)) {
        return localSegment.push(SegmentPool.take());
      }
    }
    else
    {
      throw new IllegalArgumentException();
    }
    return localSegment;
  }
  
  public int write(ByteBuffer paramByteBuffer)
    throws IOException
  {
    if (paramByteBuffer != null)
    {
      int j = paramByteBuffer.remaining();
      int i = j;
      while (i > 0)
      {
        Segment localSegment = writableSegment(1);
        int k = Math.min(i, 8192 - limit);
        paramByteBuffer.get(data, limit, k);
        i -= k;
        limit += k;
      }
      size += j;
      return j;
    }
    paramByteBuffer = new IllegalArgumentException("source == null");
    throw paramByteBuffer;
  }
  
  public Buffer write(ByteString paramByteString)
  {
    if (paramByteString != null)
    {
      paramByteString.write(this);
      return this;
    }
    throw new IllegalArgumentException("byteString == null");
  }
  
  public Buffer write(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte != null) {
      return write(paramArrayOfByte, 0, paramArrayOfByte.length);
    }
    throw new IllegalArgumentException("source == null");
  }
  
  public Buffer write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    if (paramArrayOfByte != null)
    {
      long l1 = paramArrayOfByte.length;
      long l2 = paramInt1;
      long l3 = paramInt2;
      Util.checkOffsetAndCount(l1, l2, l3);
      paramInt2 += paramInt1;
      while (paramInt1 < paramInt2)
      {
        Segment localSegment = writableSegment(1);
        int i = Math.min(paramInt2 - paramInt1, 8192 - limit);
        System.arraycopy(paramArrayOfByte, paramInt1, data, limit, i);
        paramInt1 += i;
        limit += i;
      }
      size += l3;
      return this;
    }
    paramArrayOfByte = new IllegalArgumentException("source == null");
    throw paramArrayOfByte;
  }
  
  public BufferedSink write(Source paramSource, long paramLong)
    throws IOException
  {
    while (paramLong > 0L)
    {
      long l = paramSource.read(this, paramLong);
      if (l != -1L) {
        paramLong -= l;
      } else {
        throw new EOFException();
      }
    }
    return this;
  }
  
  public void write(Buffer paramBuffer, long paramLong)
  {
    if (paramBuffer != null)
    {
      if (paramBuffer != this)
      {
        Util.checkOffsetAndCount(size, 0L, paramLong);
        while (paramLong > 0L)
        {
          if (paramLong < head.limit - head.pos)
          {
            localSegment1 = head;
            if (localSegment1 != null) {
              localSegment1 = prev;
            } else {
              localSegment1 = null;
            }
            if ((localSegment1 != null) && (owner))
            {
              l = limit;
              int i;
              if (shared) {
                i = 0;
              } else {
                i = pos;
              }
              if (l + paramLong - i <= 8192L)
              {
                head.writeTo(localSegment1, (int)paramLong);
                size -= paramLong;
                size += paramLong;
                return;
              }
            }
            head = head.split((int)paramLong);
          }
          Segment localSegment1 = head;
          long l = limit - pos;
          head = localSegment1.pop();
          Segment localSegment2 = head;
          if (localSegment2 == null)
          {
            head = localSegment1;
            localSegment1 = head;
            prev = localSegment1;
            next = localSegment1;
          }
          else
          {
            prev.push(localSegment1).compact();
          }
          size -= l;
          size += l;
          paramLong -= l;
        }
        return;
      }
      throw new IllegalArgumentException("source == this");
    }
    paramBuffer = new IllegalArgumentException("source == null");
    throw paramBuffer;
  }
  
  public long writeAll(Source paramSource)
    throws IOException
  {
    if (paramSource != null)
    {
      long l2;
      for (long l1 = 0L;; l1 += l2)
      {
        l2 = paramSource.read(this, 8192L);
        if (l2 == -1L) {
          break;
        }
      }
      return l1;
    }
    paramSource = new IllegalArgumentException("source == null");
    throw paramSource;
  }
  
  public Buffer writeByte(int paramInt)
  {
    Segment localSegment = writableSegment(1);
    byte[] arrayOfByte = data;
    int i = limit;
    limit = (i + 1);
    arrayOfByte[i] = ((byte)paramInt);
    size += 1L;
    return this;
  }
  
  public Buffer writeDecimalLong(long paramLong)
  {
    if (paramLong == 0L) {
      return writeByte(48);
    }
    int j = 0;
    int i = 1;
    long l = paramLong;
    if (paramLong < 0L)
    {
      l = -paramLong;
      if (l < 0L) {
        return writeUtf8("-9223372036854775808");
      }
      j = 1;
    }
    if (l < 100000000L)
    {
      if (l < 10000L)
      {
        if (l < 100L)
        {
          if (l >= 10L) {
            i = 2;
          }
        }
        else if (l < 1000L) {
          i = 3;
        } else {
          i = 4;
        }
      }
      else if (l < 1000000L)
      {
        if (l < 100000L) {
          i = 5;
        } else {
          i = 6;
        }
      }
      else if (l < 10000000L) {
        i = 7;
      } else {
        i = 8;
      }
    }
    else if (l < 1000000000000L)
    {
      if (l < 10000000000L)
      {
        if (l < 1000000000L) {
          i = 9;
        } else {
          i = 10;
        }
      }
      else if (l < 100000000000L) {
        i = 11;
      } else {
        i = 12;
      }
    }
    else if (l < 1000000000000000L)
    {
      if (l < 10000000000000L) {
        i = 13;
      } else if (l < 100000000000000L) {
        i = 14;
      } else {
        i = 15;
      }
    }
    else if (l < 100000000000000000L)
    {
      if (l < 10000000000000000L) {
        i = 16;
      } else {
        i = 17;
      }
    }
    else if (l < 1000000000000000000L) {
      i = 18;
    } else {
      i = 19;
    }
    int k = i;
    if (j != 0) {
      k = i + 1;
    }
    Segment localSegment = writableSegment(k);
    byte[] arrayOfByte = data;
    i = limit + k;
    while (l != 0L)
    {
      int m = (int)(l % 10L);
      i -= 1;
      arrayOfByte[i] = DIGITS[m];
      l /= 10L;
    }
    if (j != 0) {
      arrayOfByte[(i - 1)] = 45;
    }
    limit += k;
    size += k;
    return this;
  }
  
  public Buffer writeHexadecimalUnsignedLong(long paramLong)
  {
    if (paramLong == 0L) {
      return writeByte(48);
    }
    int j = Long.numberOfTrailingZeros(Long.highestOneBit(paramLong)) / 4 + 1;
    Segment localSegment = writableSegment(j);
    byte[] arrayOfByte = data;
    int i = limit + j - 1;
    int k = limit;
    while (i >= k)
    {
      arrayOfByte[i] = DIGITS[((int)(0xF & paramLong))];
      paramLong >>>= 4;
      i -= 1;
    }
    limit += j;
    size += j;
    return this;
  }
  
  public Buffer writeInt(int paramInt)
  {
    Segment localSegment = writableSegment(4);
    byte[] arrayOfByte = data;
    int j = limit;
    int i = j + 1;
    arrayOfByte[j] = ((byte)(paramInt >>> 24 & 0xFF));
    j = i + 1;
    arrayOfByte[i] = ((byte)(paramInt >>> 16 & 0xFF));
    i = j + 1;
    arrayOfByte[j] = ((byte)(paramInt >>> 8 & 0xFF));
    arrayOfByte[i] = ((byte)(paramInt & 0xFF));
    limit = (i + 1);
    size += 4L;
    return this;
  }
  
  public Buffer writeIntLe(int paramInt)
  {
    return writeInt(Util.reverseBytesInt(paramInt));
  }
  
  public Buffer writeLong(long paramLong)
  {
    Segment localSegment = writableSegment(8);
    byte[] arrayOfByte = data;
    int j = limit;
    int i = j + 1;
    arrayOfByte[j] = ((byte)(int)(paramLong >>> 56 & 0xFF));
    j = i + 1;
    arrayOfByte[i] = ((byte)(int)(paramLong >>> 48 & 0xFF));
    i = j + 1;
    arrayOfByte[j] = ((byte)(int)(paramLong >>> 40 & 0xFF));
    j = i + 1;
    arrayOfByte[i] = ((byte)(int)(paramLong >>> 32 & 0xFF));
    i = j + 1;
    arrayOfByte[j] = ((byte)(int)(paramLong >>> 24 & 0xFF));
    j = i + 1;
    arrayOfByte[i] = ((byte)(int)(paramLong >>> 16 & 0xFF));
    i = j + 1;
    arrayOfByte[j] = ((byte)(int)(paramLong >>> 8 & 0xFF));
    arrayOfByte[i] = ((byte)(int)(paramLong & 0xFF));
    limit = (i + 1);
    size += 8L;
    return this;
  }
  
  public Buffer writeLongLe(long paramLong)
  {
    return writeLong(Util.reverseBytesLong(paramLong));
  }
  
  public Buffer writeShort(int paramInt)
  {
    Segment localSegment = writableSegment(2);
    byte[] arrayOfByte = data;
    int i = limit;
    int j = i + 1;
    arrayOfByte[i] = ((byte)(paramInt >>> 8 & 0xFF));
    arrayOfByte[j] = ((byte)(paramInt & 0xFF));
    limit = (j + 1);
    size += 2L;
    return this;
  }
  
  public Buffer writeShortLe(int paramInt)
  {
    return writeShort(Util.reverseBytesShort((short)paramInt));
  }
  
  public Buffer writeString(String paramString, int paramInt1, int paramInt2, Charset paramCharset)
  {
    if (paramString != null)
    {
      if (paramInt1 >= 0)
      {
        if (paramInt2 >= paramInt1)
        {
          if (paramInt2 <= paramString.length())
          {
            if (paramCharset != null)
            {
              if (paramCharset.equals(Util.UTF_8)) {
                return writeUtf8(paramString, paramInt1, paramInt2);
              }
              paramString = paramString.substring(paramInt1, paramInt2).getBytes(paramCharset);
              return write(paramString, 0, paramString.length);
            }
            throw new IllegalArgumentException("charset == null");
          }
          paramCharset = new StringBuilder();
          paramCharset.append("endIndex > string.length: ");
          paramCharset.append(paramInt2);
          paramCharset.append(" > ");
          paramCharset.append(paramString.length());
          throw new IllegalArgumentException(paramCharset.toString());
        }
        paramString = new StringBuilder();
        paramString.append("endIndex < beginIndex: ");
        paramString.append(paramInt2);
        paramString.append(" < ");
        paramString.append(paramInt1);
        throw new IllegalArgumentException(paramString.toString());
      }
      paramString = new StringBuilder();
      paramString.append("beginIndex < 0: ");
      paramString.append(paramInt1);
      throw new IllegalAccessError(paramString.toString());
    }
    throw new IllegalArgumentException("string == null");
  }
  
  public Buffer writeString(String paramString, Charset paramCharset)
  {
    return writeString(paramString, 0, paramString.length(), paramCharset);
  }
  
  public final Buffer writeTo(OutputStream paramOutputStream)
    throws IOException
  {
    return writeTo(paramOutputStream, size);
  }
  
  public final Buffer writeTo(OutputStream paramOutputStream, long paramLong)
    throws IOException
  {
    if (paramOutputStream != null)
    {
      Util.checkOffsetAndCount(size, 0L, paramLong);
      Object localObject = head;
      while (paramLong > 0L)
      {
        int i = (int)Math.min(paramLong, limit - pos);
        paramOutputStream.write(data, pos, i);
        pos += i;
        long l1 = size;
        long l2 = i;
        size = (l1 - l2);
        l1 = paramLong - l2;
        paramLong = l1;
        if (pos == limit)
        {
          Segment localSegment = ((Segment)localObject).pop();
          head = localSegment;
          SegmentPool.recycle((Segment)localObject);
          localObject = localSegment;
          paramLong = l1;
        }
      }
      return this;
    }
    paramOutputStream = new IllegalArgumentException("out == null");
    throw paramOutputStream;
  }
  
  public Buffer writeUtf8(String paramString)
  {
    return writeUtf8(paramString, 0, paramString.length());
  }
  
  public Buffer writeUtf8(String paramString, int paramInt1, int paramInt2)
  {
    if (paramString != null)
    {
      if (paramInt1 >= 0)
      {
        if (paramInt2 >= paramInt1)
        {
          if (paramInt2 <= paramString.length())
          {
            while (paramInt1 < paramInt2)
            {
              int k = paramString.charAt(paramInt1);
              int j;
              int i;
              if (k < 128)
              {
                localObject = writableSegment(1);
                byte[] arrayOfByte = data;
                j = limit - paramInt1;
                int m = Math.min(paramInt2, 8192 - j);
                i = paramInt1 + 1;
                arrayOfByte[(paramInt1 + j)] = ((byte)k);
                paramInt1 = i;
                while (paramInt1 < m)
                {
                  i = paramString.charAt(paramInt1);
                  if (i >= 128) {
                    break;
                  }
                  arrayOfByte[(paramInt1 + j)] = ((byte)i);
                  paramInt1 += 1;
                }
                i = j + paramInt1 - limit;
                limit += i;
                size += i;
              }
              else
              {
                if (k < 2048)
                {
                  writeByte(k >> 6 | 0xC0);
                  writeByte(k & 0x3F | 0x80);
                }
                for (;;)
                {
                  paramInt1 += 1;
                  break;
                  if ((k >= 55296) && (k <= 57343))
                  {
                    j = paramInt1 + 1;
                    if (j < paramInt2) {
                      i = paramString.charAt(j);
                    } else {
                      i = 0;
                    }
                    if ((k <= 56319) && (i >= 56320) && (i <= 57343))
                    {
                      i = ((k & 0xFFFF27FF) << 10 | 0xFFFF23FF & i) + 65536;
                      writeByte(i >> 18 | 0xF0);
                      writeByte(i >> 12 & 0x3F | 0x80);
                      writeByte(i >> 6 & 0x3F | 0x80);
                      writeByte(i & 0x3F | 0x80);
                      paramInt1 += 2;
                      break;
                    }
                    writeByte(63);
                    paramInt1 = j;
                    break;
                  }
                  writeByte(k >> 12 | 0xE0);
                  writeByte(k >> 6 & 0x3F | 0x80);
                  writeByte(k & 0x3F | 0x80);
                }
              }
            }
            return this;
          }
          Object localObject = new StringBuilder();
          ((StringBuilder)localObject).append("endIndex > string.length: ");
          ((StringBuilder)localObject).append(paramInt2);
          ((StringBuilder)localObject).append(" > ");
          ((StringBuilder)localObject).append(paramString.length());
          throw new IllegalArgumentException(((StringBuilder)localObject).toString());
        }
        paramString = new StringBuilder();
        paramString.append("endIndex < beginIndex: ");
        paramString.append(paramInt2);
        paramString.append(" < ");
        paramString.append(paramInt1);
        throw new IllegalArgumentException(paramString.toString());
      }
      paramString = new StringBuilder();
      paramString.append("beginIndex < 0: ");
      paramString.append(paramInt1);
      throw new IllegalArgumentException(paramString.toString());
    }
    paramString = new IllegalArgumentException("string == null");
    throw paramString;
  }
  
  public Buffer writeUtf8CodePoint(int paramInt)
  {
    if (paramInt < 128)
    {
      writeByte(paramInt);
      return this;
    }
    if (paramInt < 2048)
    {
      writeByte(paramInt >> 6 | 0xC0);
      writeByte(paramInt & 0x3F | 0x80);
      return this;
    }
    if (paramInt < 65536)
    {
      if ((paramInt >= 55296) && (paramInt <= 57343))
      {
        writeByte(63);
        return this;
      }
      writeByte(paramInt >> 12 | 0xE0);
      writeByte(paramInt >> 6 & 0x3F | 0x80);
      writeByte(paramInt & 0x3F | 0x80);
      return this;
    }
    if (paramInt <= 1114111)
    {
      writeByte(paramInt >> 18 | 0xF0);
      writeByte(paramInt >> 12 & 0x3F | 0x80);
      writeByte(paramInt >> 6 & 0x3F | 0x80);
      writeByte(paramInt & 0x3F | 0x80);
      return this;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Unexpected code point: ");
    localStringBuilder.append(Integer.toHexString(paramInt));
    throw new IllegalArgumentException(localStringBuilder.toString());
  }
  
  public static final class UnsafeCursor
    implements Closeable
  {
    public Buffer buffer;
    public byte[] data;
    public long offset = -1L;
    public int pos = -1;
    public boolean readWrite;
    private Segment segment;
    public int start = -1;
    
    public UnsafeCursor() {}
    
    public void close()
    {
      if (buffer != null)
      {
        buffer = null;
        segment = null;
        offset = -1L;
        data = null;
        start = -1;
        pos = -1;
        return;
      }
      throw new IllegalStateException("not attached to a buffer");
    }
    
    public final long expandBuffer(int paramInt)
    {
      if (paramInt > 0)
      {
        if (paramInt <= 8192)
        {
          localObject = buffer;
          if (localObject != null)
          {
            if (readWrite)
            {
              long l1 = size;
              localObject = buffer.writableSegment(paramInt);
              paramInt = 8192 - limit;
              limit = 8192;
              Buffer localBuffer = buffer;
              long l2 = paramInt;
              size = (l1 + l2);
              segment = ((Segment)localObject);
              offset = l1;
              data = data;
              start = (8192 - paramInt);
              pos = 8192;
              return l2;
            }
            throw new IllegalStateException("expandBuffer() only permitted for read/write buffers");
          }
          throw new IllegalStateException("not attached to a buffer");
        }
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("minByteCount > Segment.SIZE: ");
        ((StringBuilder)localObject).append(paramInt);
        throw new IllegalArgumentException(((StringBuilder)localObject).toString());
      }
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("minByteCount <= 0: ");
      ((StringBuilder)localObject).append(paramInt);
      throw new IllegalArgumentException(((StringBuilder)localObject).toString());
    }
    
    public final int next()
    {
      if (offset != buffer.size)
      {
        long l = offset;
        if (l == -1L) {
          return seek(0L);
        }
        return seek(l + (pos - start));
      }
      throw new IllegalStateException();
    }
    
    public final long resizeBuffer(long paramLong)
    {
      Object localObject = buffer;
      if (localObject != null)
      {
        if (readWrite)
        {
          long l3 = size;
          long l1;
          long l2;
          if (paramLong <= l3)
          {
            if (paramLong >= 0L)
            {
              l1 = l3 - paramLong;
              while (l1 > 0L)
              {
                localObject = buffer.head.prev;
                l2 = limit - pos;
                if (l2 <= l1)
                {
                  buffer.head = ((Segment)localObject).pop();
                  SegmentPool.recycle((Segment)localObject);
                  l1 -= l2;
                }
                else
                {
                  limit = ((int)(limit - l1));
                }
              }
              segment = null;
              offset = paramLong;
              data = null;
              start = -1;
              pos = -1;
            }
            else
            {
              localObject = new StringBuilder();
              ((StringBuilder)localObject).append("newSize < 0: ");
              ((StringBuilder)localObject).append(paramLong);
              throw new IllegalArgumentException(((StringBuilder)localObject).toString());
            }
          }
          else if (paramLong > l3)
          {
            l1 = paramLong - l3;
            int i = 1;
            while (l1 > 0L)
            {
              localObject = buffer.writableSegment(1);
              int j = (int)Math.min(l1, 8192 - limit);
              limit += j;
              l2 = l1 - j;
              l1 = l2;
              if (i != 0)
              {
                segment = ((Segment)localObject);
                offset = l3;
                data = data;
                start = (limit - j);
                pos = limit;
                i = 0;
                l1 = l2;
              }
            }
          }
          buffer.size = paramLong;
          return l3;
        }
        throw new IllegalStateException("resizeBuffer() only permitted for read/write buffers");
      }
      localObject = new IllegalStateException("not attached to a buffer");
      throw ((Throwable)localObject);
    }
    
    public final int seek(long paramLong)
    {
      if ((paramLong >= -1L) && (paramLong <= buffer.size))
      {
        if ((paramLong != -1L) && (paramLong != buffer.size))
        {
          long l3 = 0L;
          long l4 = buffer.size;
          Segment localSegment1 = buffer.head;
          Segment localSegment2 = buffer.head;
          Segment localSegment3 = segment;
          long l1 = l3;
          long l2 = l4;
          localObject1 = localSegment1;
          Object localObject2 = localSegment2;
          if (localSegment3 != null)
          {
            l1 = offset - (start - pos);
            if (l1 > paramLong)
            {
              localObject2 = segment;
              l2 = l1;
              l1 = l3;
              localObject1 = localSegment1;
            }
            else
            {
              localObject1 = segment;
              localObject2 = localSegment2;
              l2 = l4;
            }
          }
          if (l2 - paramLong > paramLong - l1) {
            for (localObject2 = localObject1;; localObject2 = next)
            {
              l2 = l1;
              localObject1 = localObject2;
              if (paramLong < limit - pos + l1) {
                break;
              }
              l1 += limit - pos;
            }
          }
          for (l1 = l2;; l1 -= limit - pos)
          {
            l2 = l1;
            localObject1 = localObject2;
            if (l1 <= paramLong) {
              break;
            }
            localObject2 = prev;
          }
          localObject2 = localObject1;
          if (readWrite)
          {
            localObject2 = localObject1;
            if (shared)
            {
              localObject2 = ((Segment)localObject1).unsharedCopy();
              if (buffer.head == localObject1) {
                buffer.head = ((Segment)localObject2);
              }
              localObject2 = ((Segment)localObject1).push((Segment)localObject2);
              localObject1 = localObject2;
              prev.pop();
              localObject2 = localObject1;
            }
          }
          segment = ((Segment)localObject2);
          offset = paramLong;
          data = data;
          start = (pos + (int)(paramLong - l2));
          pos = limit;
          return pos - start;
        }
        segment = null;
        offset = paramLong;
        data = null;
        start = -1;
        pos = -1;
        return -1;
      }
      Object localObject1 = new ArrayIndexOutOfBoundsException(String.format("offset=%s > size=%s", new Object[] { Long.valueOf(paramLong), Long.valueOf(buffer.size) }));
      throw ((Throwable)localObject1);
    }
  }
}
