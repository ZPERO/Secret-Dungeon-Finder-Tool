package com.squareup.okhttp;

import com.squareup.okhttp.internal.Util;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import okio.Buffer;
import okio.BufferedSource;
import okio.Source;

public abstract class ResponseBody
  implements Closeable
{
  private Reader reader;
  
  public ResponseBody() {}
  
  private Charset charset()
  {
    MediaType localMediaType = contentType();
    if (localMediaType != null) {
      return localMediaType.charset(Util.UTF_8);
    }
    return Util.UTF_8;
  }
  
  public static ResponseBody create(MediaType paramMediaType, final long paramLong, BufferedSource paramBufferedSource)
  {
    if (paramBufferedSource != null) {
      new ResponseBody()
      {
        public long contentLength()
        {
          return paramLong;
        }
        
        public MediaType contentType()
        {
          return val$contentType;
        }
        
        public BufferedSource source()
        {
          return val$content;
        }
      };
    }
    throw new NullPointerException("source == null");
  }
  
  public static ResponseBody create(MediaType paramMediaType, String paramString)
  {
    Object localObject1 = Util.UTF_8;
    Object localObject2 = paramMediaType;
    if (paramMediaType != null)
    {
      Charset localCharset = paramMediaType.charset();
      localObject1 = localCharset;
      localObject2 = paramMediaType;
      if (localCharset == null)
      {
        localObject1 = Util.UTF_8;
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append(paramMediaType);
        ((StringBuilder)localObject2).append("; charset=utf-8");
        localObject2 = MediaType.parse(((StringBuilder)localObject2).toString());
      }
    }
    paramMediaType = new Buffer().writeString(paramString, (Charset)localObject1);
    return create((MediaType)localObject2, paramMediaType.size(), paramMediaType);
  }
  
  public static ResponseBody create(MediaType paramMediaType, byte[] paramArrayOfByte)
  {
    Buffer localBuffer = new Buffer().write(paramArrayOfByte);
    return create(paramMediaType, paramArrayOfByte.length, localBuffer);
  }
  
  public final InputStream byteStream()
    throws IOException
  {
    return source().inputStream();
  }
  
  public final byte[] bytes()
    throws IOException
  {
    long l = contentLength();
    if (l <= 2147483647L)
    {
      localObject = source();
      try
      {
        byte[] arrayOfByte = ((BufferedSource)localObject).readByteArray();
        Util.closeQuietly((Closeable)localObject);
        if (l == -1L) {
          break label102;
        }
        if (l == arrayOfByte.length) {
          return arrayOfByte;
        }
        throw new IOException("Content-Length and stream length disagree");
      }
      catch (Throwable localThrowable)
      {
        Util.closeQuietly((Closeable)localObject);
        throw localThrowable;
      }
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Cannot buffer entire body for content length: ");
    ((StringBuilder)localObject).append(l);
    throw new IOException(((StringBuilder)localObject).toString());
    label102:
    return localThrowable;
  }
  
  public final Reader charStream()
    throws IOException
  {
    Object localObject = reader;
    if (localObject != null) {
      return localObject;
    }
    localObject = new InputStreamReader(byteStream(), charset());
    reader = ((Reader)localObject);
    return localObject;
  }
  
  public void close()
    throws IOException
  {
    source().close();
  }
  
  public abstract long contentLength()
    throws IOException;
  
  public abstract MediaType contentType();
  
  public abstract BufferedSource source()
    throws IOException;
  
  public final String string()
    throws IOException
  {
    return new String(bytes(), charset().name());
  }
}
