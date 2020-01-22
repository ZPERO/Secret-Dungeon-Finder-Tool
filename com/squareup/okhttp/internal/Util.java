package com.squareup.okhttp.internal;

import com.squareup.okhttp.HttpUrl;
import java.io.Closeable;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import okio.Buffer;
import okio.ByteString;
import okio.Source;
import okio.Timeout;

public final class Util
{
  public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
  public static final String[] EMPTY_STRING_ARRAY = new String[0];
  public static final Charset UTF_8 = Charset.forName("UTF-8");
  
  private Util() {}
  
  public static void checkOffsetAndCount(long paramLong1, long paramLong2, long paramLong3)
  {
    if (((paramLong2 | paramLong3) >= 0L) && (paramLong2 <= paramLong1) && (paramLong1 - paramLong2 >= paramLong3)) {
      return;
    }
    throw new ArrayIndexOutOfBoundsException();
  }
  
  public static void closeAll(Closeable paramCloseable1, Closeable paramCloseable2)
    throws IOException
  {
    try
    {
      paramCloseable1.close();
      paramCloseable1 = null;
    }
    catch (Throwable paramCloseable1) {}
    try
    {
      paramCloseable2.close();
      paramCloseable2 = paramCloseable1;
    }
    catch (Throwable localThrowable)
    {
      paramCloseable2 = paramCloseable1;
      if (paramCloseable1 == null) {
        paramCloseable2 = localThrowable;
      }
    }
    if (paramCloseable2 == null) {
      return;
    }
    if (!(paramCloseable2 instanceof IOException))
    {
      if (!(paramCloseable2 instanceof RuntimeException))
      {
        if ((paramCloseable2 instanceof Error)) {
          throw ((Error)paramCloseable2);
        }
        throw new AssertionError(paramCloseable2);
      }
      throw ((RuntimeException)paramCloseable2);
    }
    throw ((IOException)paramCloseable2);
  }
  
  public static void closeQuietly(Closeable paramCloseable)
  {
    if (paramCloseable != null) {
      try
      {
        paramCloseable.close();
        return;
      }
      catch (RuntimeException paramCloseable)
      {
        throw paramCloseable;
      }
      catch (Exception paramCloseable) {}
    }
  }
  
  public static void closeQuietly(ServerSocket paramServerSocket)
  {
    if (paramServerSocket != null) {
      try
      {
        paramServerSocket.close();
        return;
      }
      catch (RuntimeException paramServerSocket)
      {
        throw paramServerSocket;
      }
      catch (Exception paramServerSocket) {}
    }
  }
  
  public static void closeQuietly(Socket paramSocket)
  {
    if (paramSocket != null) {
      try
      {
        paramSocket.close();
        return;
      }
      catch (RuntimeException paramSocket)
      {
        throw paramSocket;
      }
      catch (AssertionError paramSocket)
      {
        if (isAndroidGetsocknameError(paramSocket)) {
          return;
        }
        throw paramSocket;
      }
      catch (Exception paramSocket) {}
    }
  }
  
  public static String[] concat(String[] paramArrayOfString, String paramString)
  {
    String[] arrayOfString = new String[paramArrayOfString.length + 1];
    System.arraycopy(paramArrayOfString, 0, arrayOfString, 0, paramArrayOfString.length);
    arrayOfString[(arrayOfString.length - 1)] = paramString;
    return arrayOfString;
  }
  
  public static boolean contains(String[] paramArrayOfString, String paramString)
  {
    return Arrays.asList(paramArrayOfString).contains(paramString);
  }
  
  public static boolean discard(Source paramSource, int paramInt, TimeUnit paramTimeUnit)
  {
    try
    {
      boolean bool = skipAll(paramSource, paramInt, paramTimeUnit);
      return bool;
    }
    catch (IOException paramSource)
    {
      for (;;) {}
    }
    return false;
  }
  
  public static boolean equal(Object paramObject1, Object paramObject2)
  {
    return (paramObject1 == paramObject2) || ((paramObject1 != null) && (paramObject1.equals(paramObject2)));
  }
  
  public static String hostHeader(HttpUrl paramHttpUrl)
  {
    if (paramHttpUrl.port() != HttpUrl.defaultPort(paramHttpUrl.scheme()))
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(paramHttpUrl.host());
      localStringBuilder.append(":");
      localStringBuilder.append(paramHttpUrl.port());
      return localStringBuilder.toString();
    }
    return paramHttpUrl.host();
  }
  
  public static List immutableList(List paramList)
  {
    return Collections.unmodifiableList(new ArrayList(paramList));
  }
  
  public static List immutableList(Object... paramVarArgs)
  {
    return Collections.unmodifiableList(Arrays.asList((Object[])paramVarArgs.clone()));
  }
  
  public static Map immutableMap(Map paramMap)
  {
    return Collections.unmodifiableMap(new LinkedHashMap(paramMap));
  }
  
  private static List intersect(Object[] paramArrayOfObject1, Object[] paramArrayOfObject2)
  {
    ArrayList localArrayList = new ArrayList();
    int k = paramArrayOfObject1.length;
    int i = 0;
    while (i < k)
    {
      Object localObject1 = paramArrayOfObject1[i];
      int m = paramArrayOfObject2.length;
      int j = 0;
      while (j < m)
      {
        Object localObject2 = paramArrayOfObject2[j];
        if (localObject1.equals(localObject2))
        {
          localArrayList.add(localObject2);
          break;
        }
        j += 1;
      }
      i += 1;
    }
    return localArrayList;
  }
  
  public static Object[] intersect(Class paramClass, Object[] paramArrayOfObject1, Object[] paramArrayOfObject2)
  {
    paramArrayOfObject1 = intersect(paramArrayOfObject1, paramArrayOfObject2);
    return paramArrayOfObject1.toArray((Object[])Array.newInstance(paramClass, paramArrayOfObject1.size()));
  }
  
  public static boolean isAndroidGetsocknameError(AssertionError paramAssertionError)
  {
    return (paramAssertionError.getCause() != null) && (paramAssertionError.getMessage() != null) && (paramAssertionError.getMessage().contains("getsockname failed"));
  }
  
  public static String md5Hex(String paramString)
  {
    try
    {
      paramString = ByteString.of(MessageDigest.getInstance("MD5").digest(paramString.getBytes("UTF-8"))).hex();
      return paramString;
    }
    catch (UnsupportedEncodingException paramString) {}catch (NoSuchAlgorithmException paramString) {}
    throw new AssertionError(paramString);
  }
  
  public static ByteString sha1(ByteString paramByteString)
  {
    try
    {
      paramByteString = ByteString.of(MessageDigest.getInstance("SHA-1").digest(paramByteString.toByteArray()));
      return paramByteString;
    }
    catch (NoSuchAlgorithmException paramByteString)
    {
      throw new AssertionError(paramByteString);
    }
  }
  
  public static String shaBase64(String paramString)
  {
    try
    {
      paramString = ByteString.of(MessageDigest.getInstance("SHA-1").digest(paramString.getBytes("UTF-8"))).base64();
      return paramString;
    }
    catch (UnsupportedEncodingException paramString) {}catch (NoSuchAlgorithmException paramString) {}
    throw new AssertionError(paramString);
  }
  
  public static boolean skipAll(Source paramSource, int paramInt, TimeUnit paramTimeUnit)
    throws IOException
  {
    long l2 = System.nanoTime();
    long l1;
    if (paramSource.timeout().hasDeadline()) {
      l1 = paramSource.timeout().deadlineNanoTime() - l2;
    } else {
      l1 = Long.MAX_VALUE;
    }
    paramSource.timeout().deadlineNanoTime(Math.min(l1, paramTimeUnit.toNanos(paramInt)) + l2);
    try
    {
      paramTimeUnit = new Buffer();
      for (;;)
      {
        long l3 = paramSource.read(paramTimeUnit, 2048L);
        if (l3 == -1L) {
          break;
        }
        paramTimeUnit.clear();
      }
      if (l1 == Long.MAX_VALUE)
      {
        paramSource.timeout().clearDeadline();
        return true;
      }
      paramSource.timeout().deadlineNanoTime(l2 + l1);
      return true;
    }
    catch (Throwable paramTimeUnit)
    {
      if (l1 == Long.MAX_VALUE) {
        paramSource.timeout().clearDeadline();
      } else {
        paramSource.timeout().deadlineNanoTime(l2 + l1);
      }
      throw paramTimeUnit;
      if (l1 == Long.MAX_VALUE)
      {
        paramSource.timeout().clearDeadline();
        return false;
      }
      paramSource.timeout().deadlineNanoTime(l2 + l1);
      return false;
    }
    catch (InterruptedIOException paramTimeUnit)
    {
      for (;;) {}
    }
  }
  
  public static ThreadFactory threadFactory(String paramString, final boolean paramBoolean)
  {
    new ThreadFactory()
    {
      public Thread newThread(Runnable paramAnonymousRunnable)
      {
        paramAnonymousRunnable = new Thread(paramAnonymousRunnable, val$name);
        paramAnonymousRunnable.setDaemon(paramBoolean);
        return paramAnonymousRunnable;
      }
    };
  }
  
  public static String toHumanReadableAscii(String paramString)
  {
    int m = paramString.length();
    int i = 0;
    int j;
    for (;;)
    {
      localObject = paramString;
      if (i >= m) {
        break label119;
      }
      j = paramString.codePointAt(i);
      if ((j <= 31) || (j >= 127)) {
        break;
      }
      i += Character.charCount(j);
    }
    Object localObject = new Buffer();
    ((Buffer)localObject).writeUtf8(paramString, 0, i);
    while (i < m)
    {
      int k = paramString.codePointAt(i);
      if ((k > 31) && (k < 127)) {
        j = k;
      } else {
        j = 63;
      }
      ((Buffer)localObject).writeUtf8CodePoint(j);
      i += Character.charCount(k);
    }
    localObject = ((Buffer)localObject).readUtf8();
    label119:
    return localObject;
  }
}
