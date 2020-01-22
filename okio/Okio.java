package okio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Okio
{
  static final Logger logger = Logger.getLogger(Okio.class.getName());
  
  private Okio() {}
  
  public static Sink appendingSink(File paramFile)
    throws FileNotFoundException
  {
    if (paramFile != null) {
      return sink(new FileOutputStream(paramFile, true));
    }
    throw new IllegalArgumentException("file == null");
  }
  
  public static Sink blackhole()
  {
    new Sink()
    {
      public void close()
        throws IOException
      {}
      
      public void flush()
        throws IOException
      {}
      
      public Timeout timeout()
      {
        return Timeout.NONE;
      }
      
      public void write(Buffer paramAnonymousBuffer, long paramAnonymousLong)
        throws IOException
      {
        paramAnonymousBuffer.skip(paramAnonymousLong);
      }
    };
  }
  
  public static BufferedSink buffer(Sink paramSink)
  {
    return new RealBufferedSink(paramSink);
  }
  
  public static BufferedSource buffer(Source paramSource)
  {
    return new RealBufferedSource(paramSource);
  }
  
  static boolean isAndroidGetsocknameError(AssertionError paramAssertionError)
  {
    return (paramAssertionError.getCause() != null) && (paramAssertionError.getMessage() != null) && (paramAssertionError.getMessage().contains("getsockname failed"));
  }
  
  public static Sink sink(File paramFile)
    throws FileNotFoundException
  {
    if (paramFile != null) {
      return sink(new FileOutputStream(paramFile));
    }
    throw new IllegalArgumentException("file == null");
  }
  
  public static Sink sink(OutputStream paramOutputStream)
  {
    return sink(paramOutputStream, new Timeout());
  }
  
  private static Sink sink(final OutputStream paramOutputStream, Timeout paramTimeout)
  {
    if (paramOutputStream != null)
    {
      if (paramTimeout != null) {
        new Sink()
        {
          public void close()
            throws IOException
          {
            paramOutputStream.close();
          }
          
          public void flush()
            throws IOException
          {
            paramOutputStream.flush();
          }
          
          public Timeout timeout()
          {
            return Okio.this;
          }
          
          public String toString()
          {
            StringBuilder localStringBuilder = new StringBuilder();
            localStringBuilder.append("sink(");
            localStringBuilder.append(paramOutputStream);
            localStringBuilder.append(")");
            return localStringBuilder.toString();
          }
          
          public void write(Buffer paramAnonymousBuffer, long paramAnonymousLong)
            throws IOException
          {
            Util.checkOffsetAndCount(size, 0L, paramAnonymousLong);
            while (paramAnonymousLong > 0L)
            {
              throwIfReached();
              Segment localSegment = head;
              int i = (int)Math.min(paramAnonymousLong, limit - pos);
              paramOutputStream.write(data, pos, i);
              pos += i;
              long l2 = i;
              long l1 = paramAnonymousLong - l2;
              size -= l2;
              paramAnonymousLong = l1;
              if (pos == limit)
              {
                head = localSegment.pop();
                SegmentPool.recycle(localSegment);
                paramAnonymousLong = l1;
              }
            }
          }
        };
      }
      throw new IllegalArgumentException("timeout == null");
    }
    throw new IllegalArgumentException("out == null");
  }
  
  public static Sink sink(Socket paramSocket)
    throws IOException
  {
    if (paramSocket != null)
    {
      if (paramSocket.getOutputStream() != null)
      {
        AsyncTimeout localAsyncTimeout = timeout(paramSocket);
        return localAsyncTimeout.sink(sink(paramSocket.getOutputStream(), localAsyncTimeout));
      }
      throw new IOException("socket's output stream == null");
    }
    throw new IllegalArgumentException("socket == null");
  }
  
  public static Sink sink(Path paramPath, OpenOption... paramVarArgs)
    throws IOException
  {
    if (paramPath != null) {
      return sink(Files.newOutputStream(paramPath, paramVarArgs));
    }
    throw new IllegalArgumentException("path == null");
  }
  
  public static Source source(File paramFile)
    throws FileNotFoundException
  {
    if (paramFile != null) {
      return source(new FileInputStream(paramFile));
    }
    throw new IllegalArgumentException("file == null");
  }
  
  public static Source source(InputStream paramInputStream)
  {
    return source(paramInputStream, new Timeout());
  }
  
  private static Source source(final InputStream paramInputStream, Timeout paramTimeout)
  {
    if (paramInputStream != null)
    {
      if (paramTimeout != null) {
        new Source()
        {
          public void close()
            throws IOException
          {
            paramInputStream.close();
          }
          
          public long read(Buffer paramAnonymousBuffer, long paramAnonymousLong)
            throws IOException
          {
            if (paramAnonymousLong >= 0L)
            {
              if (paramAnonymousLong == 0L) {
                return 0L;
              }
              Object localObject = Okio.this;
              try
              {
                ((Timeout)localObject).throwIfReached();
                localObject = paramAnonymousBuffer.writableSegment(1);
                long l = 8192 - limit;
                paramAnonymousLong = Math.min(paramAnonymousLong, l);
                int i = (int)paramAnonymousLong;
                InputStream localInputStream = paramInputStream;
                byte[] arrayOfByte = data;
                int j = limit;
                i = localInputStream.read(arrayOfByte, j, i);
                if (i == -1) {
                  return -1L;
                }
                limit += i;
                paramAnonymousLong = size;
                l = i;
                size = (paramAnonymousLong + l);
                return l;
              }
              catch (AssertionError paramAnonymousBuffer)
              {
                if (Okio.isAndroidGetsocknameError(paramAnonymousBuffer)) {
                  throw new IOException(paramAnonymousBuffer);
                }
                throw paramAnonymousBuffer;
              }
            }
            paramAnonymousBuffer = new StringBuilder();
            paramAnonymousBuffer.append("byteCount < 0: ");
            paramAnonymousBuffer.append(paramAnonymousLong);
            throw new IllegalArgumentException(paramAnonymousBuffer.toString());
          }
          
          public Timeout timeout()
          {
            return Okio.this;
          }
          
          public String toString()
          {
            StringBuilder localStringBuilder = new StringBuilder();
            localStringBuilder.append("source(");
            localStringBuilder.append(paramInputStream);
            localStringBuilder.append(")");
            return localStringBuilder.toString();
          }
        };
      }
      throw new IllegalArgumentException("timeout == null");
    }
    throw new IllegalArgumentException("in == null");
  }
  
  public static Source source(Socket paramSocket)
    throws IOException
  {
    if (paramSocket != null)
    {
      if (paramSocket.getInputStream() != null)
      {
        AsyncTimeout localAsyncTimeout = timeout(paramSocket);
        return localAsyncTimeout.source(source(paramSocket.getInputStream(), localAsyncTimeout));
      }
      throw new IOException("socket's input stream == null");
    }
    throw new IllegalArgumentException("socket == null");
  }
  
  public static Source source(Path paramPath, OpenOption... paramVarArgs)
    throws IOException
  {
    if (paramPath != null) {
      return source(Files.newInputStream(paramPath, paramVarArgs));
    }
    throw new IllegalArgumentException("path == null");
  }
  
  private static AsyncTimeout timeout(Socket paramSocket)
  {
    new AsyncTimeout()
    {
      protected IOException newTimeoutException(IOException paramAnonymousIOException)
      {
        SocketTimeoutException localSocketTimeoutException = new SocketTimeoutException("timeout");
        if (paramAnonymousIOException != null) {
          localSocketTimeoutException.initCause(paramAnonymousIOException);
        }
        return localSocketTimeoutException;
      }
      
      protected void timedOut()
      {
        Socket localSocket = Okio.this;
        try
        {
          localSocket.close();
          return;
        }
        catch (AssertionError localAssertionError)
        {
          if (Okio.isAndroidGetsocknameError(localAssertionError))
          {
            localLogger = Okio.logger;
            localLevel = Level.WARNING;
            localStringBuilder = new StringBuilder();
            localStringBuilder.append("Failed to close timed out socket ");
            localStringBuilder.append(Okio.this);
            localLogger.log(localLevel, localStringBuilder.toString(), localAssertionError);
            return;
          }
          throw localAssertionError;
        }
        catch (Exception localException)
        {
          Logger localLogger = Okio.logger;
          Level localLevel = Level.WARNING;
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("Failed to close timed out socket ");
          localStringBuilder.append(Okio.this);
          localLogger.log(localLevel, localStringBuilder.toString(), localException);
        }
      }
    };
  }
}
