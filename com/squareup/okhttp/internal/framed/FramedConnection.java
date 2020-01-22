package com.squareup.okhttp.internal.framed;

import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.internal.Internal;
import com.squareup.okhttp.internal.NamedRunnable;
import com.squareup.okhttp.internal.Util;
import java.io.Closeable;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ByteString;
import okio.Okio;
import okio.Source;

public final class FramedConnection
  implements Closeable
{
  private static final int OKHTTP_CLIENT_WINDOW_SIZE = 16777216;
  private static final ExecutorService executor = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue(), Util.threadFactory("OkHttp FramedConnection", true));
  long bytesLeftInWriteWindow;
  final boolean client;
  private final Set<Integer> currentPushRequests = new LinkedHashSet();
  final FrameWriter frameWriter;
  private final String hostName;
  private long idleStartTimeNs = System.nanoTime();
  private int lastGoodStreamId;
  private final Listener listener;
  private int nextPingId;
  private int nextStreamId;
  Settings okHttpSettings = new Settings();
  final Settings peerSettings = new Settings();
  private Map<Integer, Ping> pings;
  final Protocol protocol;
  private final ExecutorService pushExecutor;
  private final PushObserver pushObserver;
  final Reader readerRunnable;
  private boolean receivedInitialPeerSettings = false;
  private boolean shutdown;
  final Socket socket;
  private final Map<Integer, FramedStream> streams = new HashMap();
  long unacknowledgedBytesRead = 0L;
  final Variant variant;
  
  private FramedConnection(Builder paramBuilder)
    throws IOException
  {
    protocol = protocol;
    pushObserver = pushObserver;
    client = client;
    listener = listener;
    boolean bool = client;
    int j = 2;
    if (bool) {
      i = 1;
    } else {
      i = 2;
    }
    nextStreamId = i;
    if ((client) && (protocol == Protocol.HTTP_2)) {
      nextStreamId += 2;
    }
    int i = j;
    if (client) {
      i = 1;
    }
    nextPingId = i;
    if (client) {
      okHttpSettings.set(7, 0, 16777216);
    }
    hostName = hostName;
    if (protocol == Protocol.HTTP_2)
    {
      variant = new Http2();
      pushExecutor = new ThreadPoolExecutor(0, 1, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue(), Util.threadFactory(String.format("OkHttp %s Push Observer", new Object[] { hostName }), true));
      peerSettings.set(7, 0, 65535);
      peerSettings.set(5, 0, 16384);
    }
    else
    {
      if (protocol != Protocol.SPDY_3) {
        break label403;
      }
      variant = new Spdy3();
      pushExecutor = null;
    }
    bytesLeftInWriteWindow = peerSettings.getInitialWindowSize(65536);
    socket = socket;
    frameWriter = variant.newWriter(sink, client);
    readerRunnable = new Reader(variant.newReader(source, client), null);
    new Thread(readerRunnable).start();
    return;
    label403:
    throw new AssertionError(protocol);
  }
  
  private void close(ErrorCode paramErrorCode1, ErrorCode paramErrorCode2)
    throws IOException
  {
    Ping[] arrayOfPing = null;
    try
    {
      shutdown(paramErrorCode1);
      paramErrorCode1 = null;
    }
    catch (IOException paramErrorCode1) {}
    for (;;)
    {
      try
      {
        boolean bool = streams.isEmpty();
        int j = 0;
        if (!bool)
        {
          arrayOfFramedStream = (FramedStream[])streams.values().toArray(new FramedStream[streams.size()]);
          streams.clear();
          setIdle(false);
          if (pings != null)
          {
            localObject = pings.values().toArray(new Ping[pings.size()]);
            pings = null;
            arrayOfPing = (Ping[])localObject;
          }
          Object localObject = paramErrorCode1;
          int k;
          int i;
          if (arrayOfFramedStream != null)
          {
            k = arrayOfFramedStream.length;
            i = 0;
            if (i < k)
            {
              localObject = arrayOfFramedStream[i];
              try
              {
                ((FramedStream)localObject).close(paramErrorCode2);
                localObject = paramErrorCode1;
              }
              catch (IOException localIOException)
              {
                localObject = paramErrorCode1;
                if (paramErrorCode1 != null) {
                  localObject = localIOException;
                }
              }
              i += 1;
              paramErrorCode1 = (ErrorCode)localObject;
              continue;
            }
            localObject = paramErrorCode1;
          }
          if (arrayOfPing != null)
          {
            k = arrayOfPing.length;
            i = j;
            if (i < k)
            {
              arrayOfPing[i].cancel();
              i += 1;
              continue;
            }
          }
          paramErrorCode1 = frameWriter;
          try
          {
            paramErrorCode1.close();
            paramErrorCode1 = (ErrorCode)localObject;
          }
          catch (IOException paramErrorCode2)
          {
            paramErrorCode1 = (ErrorCode)localObject;
            if (localObject == null) {
              paramErrorCode1 = paramErrorCode2;
            }
          }
          paramErrorCode2 = socket;
          try
          {
            paramErrorCode2.close();
          }
          catch (IOException paramErrorCode1) {}
          if (paramErrorCode1 == null) {
            return;
          }
          throw paramErrorCode1;
        }
      }
      catch (Throwable paramErrorCode1)
      {
        throw paramErrorCode1;
      }
      FramedStream[] arrayOfFramedStream = null;
    }
  }
  
  private FramedStream newStream(int paramInt, List paramList, boolean paramBoolean1, boolean paramBoolean2)
    throws IOException
  {
    boolean bool = paramBoolean1 ^ true;
    paramBoolean2 ^= true;
    FrameWriter localFrameWriter = frameWriter;
    try
    {
      FramedStream localFramedStream;
      try
      {
        if (!shutdown)
        {
          int i = nextStreamId;
          nextStreamId += 2;
          localFramedStream = new FramedStream(i, this, bool, paramBoolean2, paramList);
          if (localFramedStream.isOpen())
          {
            streams.put(Integer.valueOf(i), localFramedStream);
            setIdle(false);
          }
          if (paramInt == 0)
          {
            frameWriter.synStream(bool, paramBoolean2, i, paramInt, paramList);
          }
          else
          {
            paramBoolean2 = client;
            if (paramBoolean2) {
              break label161;
            }
            frameWriter.pushPromise(paramInt, i, paramList);
          }
          if (!paramBoolean1)
          {
            frameWriter.flush();
            return localFramedStream;
            label161:
            throw new IllegalArgumentException("client streams shouldn't have associated stream IDs");
          }
        }
        else
        {
          throw new IOException("shutdown");
        }
      }
      catch (Throwable paramList)
      {
        throw paramList;
      }
      return localFramedStream;
    }
    catch (Throwable paramList)
    {
      throw paramList;
    }
  }
  
  private void pushDataLater(final int paramInt1, BufferedSource paramBufferedSource, final int paramInt2, final boolean paramBoolean)
    throws IOException
  {
    final Buffer localBuffer = new Buffer();
    long l = paramInt2;
    paramBufferedSource.require(l);
    paramBufferedSource.read(localBuffer, l);
    if (localBuffer.size() == l)
    {
      pushExecutor.execute(new NamedRunnable("OkHttp %s Push Data[%s]", new Object[] { hostName, Integer.valueOf(paramInt1) })
      {
        public void execute()
        {
          Object localObject1 = FramedConnection.this;
          try
          {
            localObject1 = pushObserver;
            int i = paramInt1;
            Object localObject2 = localBuffer;
            int j = paramInt2;
            boolean bool = paramBoolean;
            bool = ((PushObserver)localObject1).onData(i, (BufferedSource)localObject2, j, bool);
            if (bool)
            {
              localObject1 = frameWriter;
              i = paramInt1;
              localObject2 = ErrorCode.CANCEL;
              ((FrameWriter)localObject1).rstStream(i, (ErrorCode)localObject2);
            }
            if ((bool) || (paramBoolean))
            {
              localObject1 = FramedConnection.this;
              try
              {
                currentPushRequests.remove(Integer.valueOf(paramInt1));
                return;
              }
              catch (Throwable localThrowable) {}
            }
            return;
          }
          catch (IOException localIOException1)
          {
            try
            {
              throw localThrowable;
            }
            catch (IOException localIOException2) {}
            localIOException1 = localIOException1;
            return;
          }
        }
      });
      return;
    }
    paramBufferedSource = new StringBuilder();
    paramBufferedSource.append(localBuffer.size());
    paramBufferedSource.append(" != ");
    paramBufferedSource.append(paramInt2);
    throw new IOException(paramBufferedSource.toString());
  }
  
  private void pushHeadersLater(final int paramInt, final List paramList, final boolean paramBoolean)
  {
    pushExecutor.execute(new NamedRunnable("OkHttp %s Push Headers[%s]", new Object[] { hostName, Integer.valueOf(paramInt) })
    {
      /* Error */
      public void execute()
      {
        // Byte code:
        //   0: aload_0
        //   1: getfield 21	com/squareup/okhttp/internal/framed/FramedConnection$5:this$0	Lcom/squareup/okhttp/internal/framed/FramedConnection;
        //   4: invokestatic 41	com/squareup/okhttp/internal/framed/FramedConnection:access$2700	(Lcom/squareup/okhttp/internal/framed/FramedConnection;)Lcom/squareup/okhttp/internal/framed/PushObserver;
        //   7: aload_0
        //   8: getfield 23	com/squareup/okhttp/internal/framed/FramedConnection$5:val$streamId	I
        //   11: aload_0
        //   12: getfield 25	com/squareup/okhttp/internal/framed/FramedConnection$5:val$requestHeaders	Ljava/util/List;
        //   15: aload_0
        //   16: getfield 27	com/squareup/okhttp/internal/framed/FramedConnection$5:val$inFinished	Z
        //   19: invokeinterface 47 4 0
        //   24: istore_2
        //   25: iload_2
        //   26: ifeq +30 -> 56
        //   29: aload_0
        //   30: getfield 21	com/squareup/okhttp/internal/framed/FramedConnection$5:this$0	Lcom/squareup/okhttp/internal/framed/FramedConnection;
        //   33: getfield 51	com/squareup/okhttp/internal/framed/FramedConnection:frameWriter	Lcom/squareup/okhttp/internal/framed/FrameWriter;
        //   36: astore_3
        //   37: aload_0
        //   38: getfield 23	com/squareup/okhttp/internal/framed/FramedConnection$5:val$streamId	I
        //   41: istore_1
        //   42: getstatic 57	com/squareup/okhttp/internal/framed/ErrorCode:CANCEL	Lcom/squareup/okhttp/internal/framed/ErrorCode;
        //   45: astore 4
        //   47: aload_3
        //   48: iload_1
        //   49: aload 4
        //   51: invokeinterface 63 3 0
        //   56: iload_2
        //   57: ifne +10 -> 67
        //   60: aload_0
        //   61: getfield 27	com/squareup/okhttp/internal/framed/FramedConnection$5:val$inFinished	Z
        //   64: ifeq +43 -> 107
        //   67: aload_0
        //   68: getfield 21	com/squareup/okhttp/internal/framed/FramedConnection$5:this$0	Lcom/squareup/okhttp/internal/framed/FramedConnection;
        //   71: astore_3
        //   72: aload_3
        //   73: monitorenter
        //   74: aload_0
        //   75: getfield 21	com/squareup/okhttp/internal/framed/FramedConnection$5:this$0	Lcom/squareup/okhttp/internal/framed/FramedConnection;
        //   78: invokestatic 67	com/squareup/okhttp/internal/framed/FramedConnection:access$2800	(Lcom/squareup/okhttp/internal/framed/FramedConnection;)Ljava/util/Set;
        //   81: aload_0
        //   82: getfield 23	com/squareup/okhttp/internal/framed/FramedConnection$5:val$streamId	I
        //   85: invokestatic 73	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
        //   88: invokeinterface 79 2 0
        //   93: pop
        //   94: aload_3
        //   95: monitorexit
        //   96: return
        //   97: astore 4
        //   99: aload_3
        //   100: monitorexit
        //   101: aload 4
        //   103: athrow
        //   104: astore_3
        //   105: return
        //   106: astore_3
        //   107: return
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	108	0	this	5
        //   41	8	1	i	int
        //   24	33	2	bool	boolean
        //   36	64	3	localObject	Object
        //   104	1	3	localIOException1	IOException
        //   106	1	3	localIOException2	IOException
        //   45	5	4	localErrorCode	ErrorCode
        //   97	5	4	localThrowable	Throwable
        // Exception table:
        //   from	to	target	type
        //   74	96	97	java/lang/Throwable
        //   99	101	97	java/lang/Throwable
        //   47	56	104	java/io/IOException
        //   101	104	106	java/io/IOException
      }
    });
  }
  
  private void pushRequestLater(final int paramInt, final List paramList)
  {
    try
    {
      if (currentPushRequests.contains(Integer.valueOf(paramInt)))
      {
        writeSynResetLater(paramInt, ErrorCode.PROTOCOL_ERROR);
        return;
      }
      currentPushRequests.add(Integer.valueOf(paramInt));
      pushExecutor.execute(new NamedRunnable("OkHttp %s Push Request[%s]", new Object[] { hostName, Integer.valueOf(paramInt) })
      {
        /* Error */
        public void execute()
        {
          // Byte code:
          //   0: aload_0
          //   1: getfield 19	com/squareup/okhttp/internal/framed/FramedConnection$4:this$0	Lcom/squareup/okhttp/internal/framed/FramedConnection;
          //   4: invokestatic 37	com/squareup/okhttp/internal/framed/FramedConnection:access$2700	(Lcom/squareup/okhttp/internal/framed/FramedConnection;)Lcom/squareup/okhttp/internal/framed/PushObserver;
          //   7: aload_0
          //   8: getfield 21	com/squareup/okhttp/internal/framed/FramedConnection$4:val$streamId	I
          //   11: aload_0
          //   12: getfield 23	com/squareup/okhttp/internal/framed/FramedConnection$4:val$requestHeaders	Ljava/util/List;
          //   15: invokeinterface 43 3 0
          //   20: ifeq +66 -> 86
          //   23: aload_0
          //   24: getfield 19	com/squareup/okhttp/internal/framed/FramedConnection$4:this$0	Lcom/squareup/okhttp/internal/framed/FramedConnection;
          //   27: getfield 47	com/squareup/okhttp/internal/framed/FramedConnection:frameWriter	Lcom/squareup/okhttp/internal/framed/FrameWriter;
          //   30: astore_2
          //   31: aload_0
          //   32: getfield 21	com/squareup/okhttp/internal/framed/FramedConnection$4:val$streamId	I
          //   35: istore_1
          //   36: getstatic 53	com/squareup/okhttp/internal/framed/ErrorCode:CANCEL	Lcom/squareup/okhttp/internal/framed/ErrorCode;
          //   39: astore_3
          //   40: aload_2
          //   41: iload_1
          //   42: aload_3
          //   43: invokeinterface 59 3 0
          //   48: aload_0
          //   49: getfield 19	com/squareup/okhttp/internal/framed/FramedConnection$4:this$0	Lcom/squareup/okhttp/internal/framed/FramedConnection;
          //   52: astore_2
          //   53: aload_2
          //   54: monitorenter
          //   55: aload_0
          //   56: getfield 19	com/squareup/okhttp/internal/framed/FramedConnection$4:this$0	Lcom/squareup/okhttp/internal/framed/FramedConnection;
          //   59: invokestatic 63	com/squareup/okhttp/internal/framed/FramedConnection:access$2800	(Lcom/squareup/okhttp/internal/framed/FramedConnection;)Ljava/util/Set;
          //   62: aload_0
          //   63: getfield 21	com/squareup/okhttp/internal/framed/FramedConnection$4:val$streamId	I
          //   66: invokestatic 69	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
          //   69: invokeinterface 75 2 0
          //   74: pop
          //   75: aload_2
          //   76: monitorexit
          //   77: return
          //   78: astore_3
          //   79: aload_2
          //   80: monitorexit
          //   81: aload_3
          //   82: athrow
          //   83: astore_2
          //   84: return
          //   85: astore_2
          //   86: return
          // Local variable table:
          //   start	length	slot	name	signature
          //   0	87	0	this	4
          //   35	7	1	i	int
          //   30	50	2	localObject	Object
          //   83	1	2	localIOException1	IOException
          //   85	1	2	localIOException2	IOException
          //   39	4	3	localErrorCode	ErrorCode
          //   78	4	3	localThrowable	Throwable
          // Exception table:
          //   from	to	target	type
          //   55	77	78	java/lang/Throwable
          //   79	81	78	java/lang/Throwable
          //   40	48	83	java/io/IOException
          //   81	83	85	java/io/IOException
        }
      });
      return;
    }
    catch (Throwable paramList)
    {
      throw paramList;
    }
  }
  
  private void pushResetLater(final int paramInt, final ErrorCode paramErrorCode)
  {
    pushExecutor.execute(new NamedRunnable("OkHttp %s Push Reset[%s]", new Object[] { hostName, Integer.valueOf(paramInt) })
    {
      public void execute()
      {
        pushObserver.onReset(paramInt, paramErrorCode);
        FramedConnection localFramedConnection = FramedConnection.this;
        try
        {
          currentPushRequests.remove(Integer.valueOf(paramInt));
          return;
        }
        catch (Throwable localThrowable)
        {
          throw localThrowable;
        }
      }
    });
  }
  
  private boolean pushedStream(int paramInt)
  {
    return (protocol == Protocol.HTTP_2) && (paramInt != 0) && ((paramInt & 0x1) == 0);
  }
  
  private Ping removePing(int paramInt)
  {
    try
    {
      Ping localPing;
      if (pings != null) {
        localPing = (Ping)pings.remove(Integer.valueOf(paramInt));
      } else {
        localPing = null;
      }
      return localPing;
    }
    catch (Throwable localThrowable)
    {
      throw localThrowable;
    }
  }
  
  private void setIdle(boolean paramBoolean)
  {
    if (paramBoolean) {}
    for (;;)
    {
      try
      {
        l = System.nanoTime();
        idleStartTimeNs = l;
        return;
      }
      catch (Throwable localThrowable)
      {
        Object localObject;
        continue;
      }
      throw localObject;
      long l = Long.MAX_VALUE;
    }
  }
  
  private void writePing(boolean paramBoolean, int paramInt1, int paramInt2, Ping paramPing)
    throws IOException
  {
    FrameWriter localFrameWriter = frameWriter;
    if (paramPing != null) {}
    try
    {
      paramPing.send();
      frameWriter.ping(paramBoolean, paramInt1, paramInt2);
      return;
    }
    catch (Throwable paramPing)
    {
      throw paramPing;
    }
  }
  
  private void writePingLater(final boolean paramBoolean, final int paramInt1, final int paramInt2, final Ping paramPing)
  {
    executor.execute(new NamedRunnable("OkHttp %s ping %08x%08x", new Object[] { hostName, Integer.valueOf(paramInt1), Integer.valueOf(paramInt2) })
    {
      public void execute()
      {
        FramedConnection localFramedConnection = FramedConnection.this;
        boolean bool = paramBoolean;
        int i = paramInt1;
        int j = paramInt2;
        Ping localPing = paramPing;
        try
        {
          localFramedConnection.writePing(bool, i, j, localPing);
          return;
        }
        catch (IOException localIOException) {}
      }
    });
  }
  
  void addBytesToWriteWindow(long paramLong)
  {
    bytesLeftInWriteWindow += paramLong;
    if (paramLong > 0L) {
      notifyAll();
    }
  }
  
  public void close()
    throws IOException
  {
    close(ErrorCode.NO_ERROR, ErrorCode.CANCEL);
  }
  
  public void flush()
    throws IOException
  {
    frameWriter.flush();
  }
  
  public long getIdleStartTimeNs()
  {
    try
    {
      long l = idleStartTimeNs;
      return l;
    }
    catch (Throwable localThrowable)
    {
      throw localThrowable;
    }
  }
  
  public Protocol getProtocol()
  {
    return protocol;
  }
  
  FramedStream getStream(int paramInt)
  {
    try
    {
      FramedStream localFramedStream = (FramedStream)streams.get(Integer.valueOf(paramInt));
      return localFramedStream;
    }
    catch (Throwable localThrowable)
    {
      throw localThrowable;
    }
  }
  
  public boolean isIdle()
  {
    try
    {
      long l = idleStartTimeNs;
      boolean bool;
      if (l != Long.MAX_VALUE) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    catch (Throwable localThrowable)
    {
      throw localThrowable;
    }
  }
  
  public int maxConcurrentStreams()
  {
    try
    {
      int i = peerSettings.getMaxConcurrentStreams(Integer.MAX_VALUE);
      return i;
    }
    catch (Throwable localThrowable)
    {
      throw localThrowable;
    }
  }
  
  public FramedStream newStream(List paramList, boolean paramBoolean1, boolean paramBoolean2)
    throws IOException
  {
    return newStream(0, paramList, paramBoolean1, paramBoolean2);
  }
  
  public int openStreamCount()
  {
    try
    {
      int i = streams.size();
      return i;
    }
    catch (Throwable localThrowable)
    {
      throw localThrowable;
    }
  }
  
  public Ping ping()
    throws IOException
  {
    Ping localPing = new Ping();
    try
    {
      if (!shutdown)
      {
        int i = nextPingId;
        nextPingId += 2;
        if (pings == null) {
          pings = new HashMap();
        }
        pings.put(Integer.valueOf(i), localPing);
        writePing(false, i, 1330343787, localPing);
        return localPing;
      }
      throw new IOException("shutdown");
    }
    catch (Throwable localThrowable)
    {
      throw localThrowable;
    }
  }
  
  public FramedStream pushStream(int paramInt, List paramList, boolean paramBoolean)
    throws IOException
  {
    if (!client)
    {
      if (protocol == Protocol.HTTP_2) {
        return newStream(paramInt, paramList, paramBoolean, false);
      }
      throw new IllegalStateException("protocol != HTTP_2");
    }
    throw new IllegalStateException("Client cannot push requests.");
  }
  
  FramedStream removeStream(int paramInt)
  {
    try
    {
      FramedStream localFramedStream = (FramedStream)streams.remove(Integer.valueOf(paramInt));
      if ((localFramedStream != null) && (streams.isEmpty())) {
        setIdle(true);
      }
      notifyAll();
      return localFramedStream;
    }
    catch (Throwable localThrowable)
    {
      throw localThrowable;
    }
  }
  
  public void sendConnectionPreface()
    throws IOException
  {
    frameWriter.connectionPreface();
    frameWriter.settings(okHttpSettings);
    int i = okHttpSettings.getInitialWindowSize(65536);
    if (i != 65536) {
      frameWriter.windowUpdate(0, i - 65536);
    }
  }
  
  /* Error */
  public void setSettings(Settings paramSettings)
    throws IOException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 241	com/squareup/okhttp/internal/framed/FramedConnection:frameWriter	Lcom/squareup/okhttp/internal/framed/FrameWriter;
    //   4: astore_2
    //   5: aload_2
    //   6: monitorenter
    //   7: aload_0
    //   8: monitorenter
    //   9: aload_0
    //   10: getfield 301	com/squareup/okhttp/internal/framed/FramedConnection:shutdown	Z
    //   13: ifne +26 -> 39
    //   16: aload_0
    //   17: getfield 139	com/squareup/okhttp/internal/framed/FramedConnection:okHttpSettings	Lcom/squareup/okhttp/internal/framed/Settings;
    //   20: aload_1
    //   21: invokevirtual 595	com/squareup/okhttp/internal/framed/Settings:merge	(Lcom/squareup/okhttp/internal/framed/Settings;)V
    //   24: aload_0
    //   25: getfield 241	com/squareup/okhttp/internal/framed/FramedConnection:frameWriter	Lcom/squareup/okhttp/internal/framed/FrameWriter;
    //   28: aload_1
    //   29: invokeinterface 587 2 0
    //   34: aload_0
    //   35: monitorexit
    //   36: aload_2
    //   37: monitorexit
    //   38: return
    //   39: new 118	java/io/IOException
    //   42: dup
    //   43: ldc_w 441
    //   46: invokespecial 442	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   49: athrow
    //   50: astore_1
    //   51: aload_0
    //   52: monitorexit
    //   53: aload_1
    //   54: athrow
    //   55: astore_1
    //   56: aload_2
    //   57: monitorexit
    //   58: aload_1
    //   59: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	60	0	this	FramedConnection
    //   0	60	1	paramSettings	Settings
    //   4	53	2	localFrameWriter	FrameWriter
    // Exception table:
    //   from	to	target	type
    //   9	36	50	java/lang/Throwable
    //   39	50	50	java/lang/Throwable
    //   51	53	50	java/lang/Throwable
    //   7	9	55	java/lang/Throwable
    //   36	38	55	java/lang/Throwable
    //   53	55	55	java/lang/Throwable
    //   56	58	55	java/lang/Throwable
  }
  
  /* Error */
  public void shutdown(ErrorCode paramErrorCode)
    throws IOException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 241	com/squareup/okhttp/internal/framed/FramedConnection:frameWriter	Lcom/squareup/okhttp/internal/framed/FrameWriter;
    //   4: astore_3
    //   5: aload_3
    //   6: monitorenter
    //   7: aload_0
    //   8: monitorenter
    //   9: aload_0
    //   10: getfield 301	com/squareup/okhttp/internal/framed/FramedConnection:shutdown	Z
    //   13: ifeq +8 -> 21
    //   16: aload_0
    //   17: monitorexit
    //   18: aload_3
    //   19: monitorexit
    //   20: return
    //   21: aload_0
    //   22: iconst_1
    //   23: putfield 301	com/squareup/okhttp/internal/framed/FramedConnection:shutdown	Z
    //   26: aload_0
    //   27: getfield 307	com/squareup/okhttp/internal/framed/FramedConnection:lastGoodStreamId	I
    //   30: istore_2
    //   31: aload_0
    //   32: monitorexit
    //   33: aload_0
    //   34: getfield 241	com/squareup/okhttp/internal/framed/FramedConnection:frameWriter	Lcom/squareup/okhttp/internal/framed/FrameWriter;
    //   37: iload_2
    //   38: aload_1
    //   39: getstatic 599	com/squareup/okhttp/internal/Util:EMPTY_BYTE_ARRAY	[B
    //   42: invokeinterface 603 4 0
    //   47: aload_3
    //   48: monitorexit
    //   49: return
    //   50: astore_1
    //   51: aload_0
    //   52: monitorexit
    //   53: aload_1
    //   54: athrow
    //   55: astore_1
    //   56: aload_3
    //   57: monitorexit
    //   58: aload_1
    //   59: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	60	0	this	FramedConnection
    //   0	60	1	paramErrorCode	ErrorCode
    //   30	8	2	i	int
    //   4	53	3	localFrameWriter	FrameWriter
    // Exception table:
    //   from	to	target	type
    //   9	18	50	java/lang/Throwable
    //   21	33	50	java/lang/Throwable
    //   51	53	50	java/lang/Throwable
    //   7	9	55	java/lang/Throwable
    //   18	20	55	java/lang/Throwable
    //   33	49	55	java/lang/Throwable
    //   53	55	55	java/lang/Throwable
    //   56	58	55	java/lang/Throwable
  }
  
  public void writeData(int paramInt, boolean paramBoolean, Buffer paramBuffer, long paramLong)
    throws IOException
  {
    long l1 = paramLong;
    if (paramLong == 0L)
    {
      frameWriter.data(paramBoolean, paramInt, paramBuffer, 0);
      return;
    }
    for (;;)
    {
      if (l1 <= 0L) {
        return;
      }
      for (;;)
      {
        try
        {
          if (bytesLeftInWriteWindow <= 0L) {
            localObject = streams;
          }
        }
        catch (Throwable paramBuffer)
        {
          Object localObject;
          boolean bool;
          int i;
          long l2;
          continue;
          throw new InterruptedIOException();
          throw paramBuffer;
        }
        try
        {
          bool = ((Map)localObject).containsKey(Integer.valueOf(paramInt));
          if (bool)
          {
            wait();
          }
          else
          {
            paramBuffer = new IOException("stream closed");
            throw paramBuffer;
          }
        }
        catch (InterruptedException paramBuffer) {}
      }
      i = Math.min((int)Math.min(l1, bytesLeftInWriteWindow), frameWriter.maxDataLength());
      paramLong = bytesLeftInWriteWindow;
      l2 = i;
      bytesLeftInWriteWindow = (paramLong - l2);
      l1 -= l2;
      localObject = frameWriter;
      if ((paramBoolean) && (l1 == 0L)) {
        bool = true;
      } else {
        bool = false;
      }
      ((FrameWriter)localObject).data(bool, paramInt, paramBuffer, i);
    }
  }
  
  void writeSynReply(int paramInt, boolean paramBoolean, List paramList)
    throws IOException
  {
    frameWriter.synReply(paramBoolean, paramInt, paramList);
  }
  
  void writeSynReset(int paramInt, ErrorCode paramErrorCode)
    throws IOException
  {
    frameWriter.rstStream(paramInt, paramErrorCode);
  }
  
  void writeSynResetLater(final int paramInt, final ErrorCode paramErrorCode)
  {
    executor.submit(new NamedRunnable("OkHttp %s stream %d", new Object[] { hostName, Integer.valueOf(paramInt) })
    {
      public void execute()
      {
        FramedConnection localFramedConnection = FramedConnection.this;
        int i = paramInt;
        ErrorCode localErrorCode = paramErrorCode;
        try
        {
          localFramedConnection.writeSynReset(i, localErrorCode);
          return;
        }
        catch (IOException localIOException) {}
      }
    });
  }
  
  void writeWindowUpdateLater(final int paramInt, final long paramLong)
  {
    executor.execute(new NamedRunnable("OkHttp Window Update %s stream %d", new Object[] { hostName, Integer.valueOf(paramInt) })
    {
      public void execute()
      {
        FrameWriter localFrameWriter = frameWriter;
        int i = paramInt;
        long l = paramLong;
        try
        {
          localFrameWriter.windowUpdate(i, l);
          return;
        }
        catch (IOException localIOException) {}
      }
    });
  }
  
  public static class Builder
  {
    private boolean client;
    private String hostName;
    private FramedConnection.Listener listener = FramedConnection.Listener.REFUSE_INCOMING_STREAMS;
    private Protocol protocol = Protocol.SPDY_3;
    private PushObserver pushObserver = PushObserver.CANCEL;
    private BufferedSink sink;
    private Socket socket;
    private BufferedSource source;
    
    public Builder(boolean paramBoolean)
      throws IOException
    {
      client = paramBoolean;
    }
    
    public FramedConnection build()
      throws IOException
    {
      return new FramedConnection(this, null);
    }
    
    public Builder listener(FramedConnection.Listener paramListener)
    {
      listener = paramListener;
      return this;
    }
    
    public Builder protocol(Protocol paramProtocol)
    {
      protocol = paramProtocol;
      return this;
    }
    
    public Builder pushObserver(PushObserver paramPushObserver)
    {
      pushObserver = paramPushObserver;
      return this;
    }
    
    public Builder socket(Socket paramSocket)
      throws IOException
    {
      return socket(paramSocket, ((InetSocketAddress)paramSocket.getRemoteSocketAddress()).getHostName(), Okio.buffer(Okio.source(paramSocket)), Okio.buffer(Okio.sink(paramSocket)));
    }
    
    public Builder socket(Socket paramSocket, String paramString, BufferedSource paramBufferedSource, BufferedSink paramBufferedSink)
    {
      socket = paramSocket;
      hostName = paramString;
      source = paramBufferedSource;
      sink = paramBufferedSink;
      return this;
    }
  }
  
  public static abstract class Listener
  {
    public static final Listener REFUSE_INCOMING_STREAMS = new Listener()
    {
      public void onStream(FramedStream paramAnonymousFramedStream)
        throws IOException
      {
        paramAnonymousFramedStream.close(ErrorCode.REFUSED_STREAM);
      }
    };
    
    public Listener() {}
    
    public void onSettings(FramedConnection paramFramedConnection) {}
    
    public abstract void onStream(FramedStream paramFramedStream)
      throws IOException;
  }
  
  class Reader
    extends NamedRunnable
    implements FrameReader.Handler
  {
    final FrameReader frameReader;
    
    private Reader(FrameReader paramFrameReader)
    {
      super(new Object[] { hostName });
      frameReader = paramFrameReader;
    }
    
    private void ackSettingsLater(final Settings paramSettings)
    {
      FramedConnection.executor.execute(new NamedRunnable("OkHttp %s ACK Settings", new Object[] { hostName })
      {
        public void execute()
        {
          FrameWriter localFrameWriter = frameWriter;
          Settings localSettings = paramSettings;
          try
          {
            localFrameWriter.ackSettings(localSettings);
            return;
          }
          catch (IOException localIOException) {}
        }
      });
    }
    
    public void ackSettings() {}
    
    public void alternateService(int paramInt1, String paramString1, ByteString paramByteString, String paramString2, int paramInt2, long paramLong) {}
    
    public void data(boolean paramBoolean, int paramInt1, BufferedSource paramBufferedSource, int paramInt2)
      throws IOException
    {
      if (FramedConnection.this.pushedStream(paramInt1))
      {
        FramedConnection.this.pushDataLater(paramInt1, paramBufferedSource, paramInt2, paramBoolean);
        return;
      }
      FramedStream localFramedStream = getStream(paramInt1);
      if (localFramedStream == null)
      {
        writeSynResetLater(paramInt1, ErrorCode.INVALID_STREAM);
        paramBufferedSource.skip(paramInt2);
        return;
      }
      localFramedStream.receiveData(paramBufferedSource, paramInt2);
      if (paramBoolean) {
        localFramedStream.receiveFin();
      }
    }
    
    /* Error */
    protected void execute()
    {
      // Byte code:
      //   0: getstatic 108	com/squareup/okhttp/internal/framed/ErrorCode:INTERNAL_ERROR	Lcom/squareup/okhttp/internal/framed/ErrorCode;
      //   3: astore_3
      //   4: getstatic 108	com/squareup/okhttp/internal/framed/ErrorCode:INTERNAL_ERROR	Lcom/squareup/okhttp/internal/framed/ErrorCode;
      //   7: astore 6
      //   9: aload_3
      //   10: astore_2
      //   11: aload_0
      //   12: getfield 23	com/squareup/okhttp/internal/framed/FramedConnection$Reader:this$0	Lcom/squareup/okhttp/internal/framed/FramedConnection;
      //   15: getfield 112	com/squareup/okhttp/internal/framed/FramedConnection:client	Z
      //   18: istore_1
      //   19: iload_1
      //   20: ifne +20 -> 40
      //   23: aload_3
      //   24: astore_2
      //   25: aload_0
      //   26: getfield 36	com/squareup/okhttp/internal/framed/FramedConnection$Reader:frameReader	Lcom/squareup/okhttp/internal/framed/FrameReader;
      //   29: astore 4
      //   31: aload_3
      //   32: astore_2
      //   33: aload 4
      //   35: invokeinterface 117 1 0
      //   40: aload_0
      //   41: getfield 36	com/squareup/okhttp/internal/framed/FramedConnection$Reader:frameReader	Lcom/squareup/okhttp/internal/framed/FrameReader;
      //   44: astore 4
      //   46: aload_3
      //   47: astore_2
      //   48: aload 4
      //   50: aload_0
      //   51: invokeinterface 121 2 0
      //   56: istore_1
      //   57: iload_1
      //   58: ifeq +6 -> 64
      //   61: goto -21 -> 40
      //   64: aload_3
      //   65: astore_2
      //   66: getstatic 124	com/squareup/okhttp/internal/framed/ErrorCode:NO_ERROR	Lcom/squareup/okhttp/internal/framed/ErrorCode;
      //   69: astore_3
      //   70: aload_3
      //   71: astore_2
      //   72: getstatic 127	com/squareup/okhttp/internal/framed/ErrorCode:CANCEL	Lcom/squareup/okhttp/internal/framed/ErrorCode;
      //   75: astore 4
      //   77: aload_0
      //   78: getfield 23	com/squareup/okhttp/internal/framed/FramedConnection$Reader:this$0	Lcom/squareup/okhttp/internal/framed/FramedConnection;
      //   81: astore 5
      //   83: aload 4
      //   85: astore_2
      //   86: aload 5
      //   88: astore 4
      //   90: goto +29 -> 119
      //   93: astore_3
      //   94: goto +40 -> 134
      //   97: aload_3
      //   98: astore_2
      //   99: getstatic 130	com/squareup/okhttp/internal/framed/ErrorCode:PROTOCOL_ERROR	Lcom/squareup/okhttp/internal/framed/ErrorCode;
      //   102: astore_3
      //   103: aload_3
      //   104: astore_2
      //   105: getstatic 130	com/squareup/okhttp/internal/framed/ErrorCode:PROTOCOL_ERROR	Lcom/squareup/okhttp/internal/framed/ErrorCode;
      //   108: astore 5
      //   110: aload_0
      //   111: getfield 23	com/squareup/okhttp/internal/framed/FramedConnection$Reader:this$0	Lcom/squareup/okhttp/internal/framed/FramedConnection;
      //   114: astore 4
      //   116: aload 5
      //   118: astore_2
      //   119: aload 4
      //   121: aload_3
      //   122: aload_2
      //   123: invokestatic 134	com/squareup/okhttp/internal/framed/FramedConnection:access$1200	(Lcom/squareup/okhttp/internal/framed/FramedConnection;Lcom/squareup/okhttp/internal/framed/ErrorCode;Lcom/squareup/okhttp/internal/framed/ErrorCode;)V
      //   126: aload_0
      //   127: getfield 36	com/squareup/okhttp/internal/framed/FramedConnection$Reader:frameReader	Lcom/squareup/okhttp/internal/framed/FrameReader;
      //   130: invokestatic 140	com/squareup/okhttp/internal/Util:closeQuietly	(Ljava/io/Closeable;)V
      //   133: return
      //   134: aload_0
      //   135: getfield 23	com/squareup/okhttp/internal/framed/FramedConnection$Reader:this$0	Lcom/squareup/okhttp/internal/framed/FramedConnection;
      //   138: astore 4
      //   140: aload 4
      //   142: aload_2
      //   143: aload 6
      //   145: invokestatic 134	com/squareup/okhttp/internal/framed/FramedConnection:access$1200	(Lcom/squareup/okhttp/internal/framed/FramedConnection;Lcom/squareup/okhttp/internal/framed/ErrorCode;Lcom/squareup/okhttp/internal/framed/ErrorCode;)V
      //   148: aload_0
      //   149: getfield 36	com/squareup/okhttp/internal/framed/FramedConnection$Reader:frameReader	Lcom/squareup/okhttp/internal/framed/FrameReader;
      //   152: invokestatic 140	com/squareup/okhttp/internal/Util:closeQuietly	(Ljava/io/Closeable;)V
      //   155: goto +3 -> 158
      //   158: aload_3
      //   159: athrow
      //   160: astore_2
      //   161: goto -64 -> 97
      //   164: astore_2
      //   165: goto -39 -> 126
      //   168: astore_2
      //   169: goto -21 -> 148
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	172	0	this	Reader
      //   18	40	1	bool	boolean
      //   10	133	2	localObject1	Object
      //   160	1	2	localIOException1	IOException
      //   164	1	2	localIOException2	IOException
      //   168	1	2	localIOException3	IOException
      //   3	68	3	localErrorCode1	ErrorCode
      //   93	5	3	localThrowable	Throwable
      //   102	57	3	localErrorCode2	ErrorCode
      //   29	112	4	localObject2	Object
      //   81	36	5	localObject3	Object
      //   7	137	6	localErrorCode3	ErrorCode
      // Exception table:
      //   from	to	target	type
      //   11	19	93	java/lang/Throwable
      //   25	31	93	java/lang/Throwable
      //   33	40	93	java/lang/Throwable
      //   48	57	93	java/lang/Throwable
      //   66	70	93	java/lang/Throwable
      //   72	77	93	java/lang/Throwable
      //   99	103	93	java/lang/Throwable
      //   105	110	93	java/lang/Throwable
      //   33	40	160	java/io/IOException
      //   48	57	160	java/io/IOException
      //   119	126	164	java/io/IOException
      //   140	148	168	java/io/IOException
    }
    
    public void goAway(int paramInt, ErrorCode paramErrorCode, ByteString paramByteString)
    {
      paramByteString.size();
      paramErrorCode = FramedConnection.this;
      try
      {
        paramByteString = (FramedStream[])streams.values().toArray(new FramedStream[streams.size()]);
        FramedConnection.access$1602(FramedConnection.this, true);
        int j = paramByteString.length;
        int i = 0;
        while (i < j)
        {
          paramErrorCode = paramByteString[i];
          if ((paramErrorCode.getId() > paramInt) && (paramErrorCode.isLocallyInitiated()))
          {
            paramErrorCode.receiveRstStream(ErrorCode.REFUSED_STREAM);
            removeStream(paramErrorCode.getId());
          }
          i += 1;
        }
        return;
      }
      catch (Throwable paramByteString)
      {
        throw paramByteString;
      }
    }
    
    public void headers(boolean paramBoolean1, boolean paramBoolean2, int paramInt1, int paramInt2, final List paramList, HeadersMode paramHeadersMode)
    {
      if (FramedConnection.this.pushedStream(paramInt1))
      {
        FramedConnection.this.pushHeadersLater(paramInt1, paramList, paramBoolean2);
        return;
      }
      FramedConnection localFramedConnection = FramedConnection.this;
      try
      {
        if (shutdown) {
          return;
        }
        FramedStream localFramedStream = getStream(paramInt1);
        if (localFramedStream == null)
        {
          if (paramHeadersMode.failIfStreamAbsent())
          {
            writeSynResetLater(paramInt1, ErrorCode.INVALID_STREAM);
            return;
          }
          if (paramInt1 <= lastGoodStreamId) {
            return;
          }
          if (paramInt1 % 2 == nextStreamId % 2) {
            return;
          }
          paramList = new FramedStream(paramInt1, FramedConnection.this, paramBoolean1, paramBoolean2, paramList);
          FramedConnection.access$1702(FramedConnection.this, paramInt1);
          streams.put(Integer.valueOf(paramInt1), paramList);
          FramedConnection.executor.execute(new NamedRunnable("OkHttp %s stream %d", new Object[] { hostName, Integer.valueOf(paramInt1) })
          {
            public void execute()
            {
              Object localObject1 = FramedConnection.this;
              try
              {
                localObject1 = listener;
                localObject2 = paramList;
                ((FramedConnection.Listener)localObject1).onStream((FramedStream)localObject2);
                return;
              }
              catch (IOException localIOException1)
              {
                Object localObject2 = Internal.logger;
                Level localLevel = Level.INFO;
                StringBuilder localStringBuilder = new StringBuilder();
                localStringBuilder.append("FramedConnection.Listener failure for ");
                localStringBuilder.append(hostName);
                ((Logger)localObject2).log(localLevel, localStringBuilder.toString(), localIOException1);
                FramedStream localFramedStream = paramList;
                localObject2 = ErrorCode.PROTOCOL_ERROR;
                try
                {
                  localFramedStream.close((ErrorCode)localObject2);
                  return;
                }
                catch (IOException localIOException2) {}
              }
            }
          });
          return;
        }
        if (paramHeadersMode.failIfStreamPresent())
        {
          localFramedStream.closeLater(ErrorCode.PROTOCOL_ERROR);
          removeStream(paramInt1);
          return;
        }
        localFramedStream.receiveHeaders(paramList, paramHeadersMode);
        if (paramBoolean2)
        {
          localFramedStream.receiveFin();
          return;
        }
      }
      catch (Throwable paramList)
      {
        throw paramList;
      }
    }
    
    public void ping(boolean paramBoolean, int paramInt1, int paramInt2)
    {
      if (paramBoolean)
      {
        Ping localPing = FramedConnection.this.removePing(paramInt1);
        if (localPing != null) {
          localPing.receive();
        }
      }
      else
      {
        FramedConnection.this.writePingLater(true, paramInt1, paramInt2, null);
      }
    }
    
    public void priority(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean) {}
    
    public void pushPromise(int paramInt1, int paramInt2, List paramList)
    {
      FramedConnection.this.pushRequestLater(paramInt2, paramList);
    }
    
    public void rstStream(int paramInt, ErrorCode paramErrorCode)
    {
      if (FramedConnection.this.pushedStream(paramInt))
      {
        FramedConnection.this.pushResetLater(paramInt, paramErrorCode);
        return;
      }
      FramedStream localFramedStream = removeStream(paramInt);
      if (localFramedStream != null) {
        localFramedStream.receiveRstStream(paramErrorCode);
      }
    }
    
    public void settings(boolean paramBoolean, Settings paramSettings)
    {
      FramedConnection localFramedConnection = FramedConnection.this;
      for (;;)
      {
        try
        {
          int i = peerSettings.getInitialWindowSize(65536);
          if (paramBoolean) {
            peerSettings.clear();
          }
          peerSettings.merge(paramSettings);
          if (getProtocol() == Protocol.HTTP_2) {
            ackSettingsLater(paramSettings);
          }
          int j = peerSettings.getInitialWindowSize(65536);
          paramSettings = null;
          if ((j != -1) && (j != i))
          {
            long l2 = j - i;
            if (!receivedInitialPeerSettings)
            {
              addBytesToWriteWindow(l2);
              FramedConnection.access$2302(FramedConnection.this, true);
            }
            l1 = l2;
            if (!streams.isEmpty())
            {
              paramSettings = (FramedStream[])streams.values().toArray(new FramedStream[streams.size()]);
              l1 = l2;
            }
            ExecutorService localExecutorService = FramedConnection.executor;
            String str = hostName;
            i = 0;
            localExecutorService.execute(new NamedRunnable("OkHttp %s settings", new Object[] { str })
            {
              public void execute()
              {
                listener.onSettings(FramedConnection.this);
              }
            });
            if ((paramSettings != null) && (l1 != 0L))
            {
              j = paramSettings.length;
              if (i < j)
              {
                localFramedConnection = paramSettings[i];
                try
                {
                  localFramedConnection.addBytesToWriteWindow(l1);
                  i += 1;
                }
                catch (Throwable paramSettings)
                {
                  throw paramSettings;
                }
              }
            }
            return;
          }
        }
        catch (Throwable paramSettings)
        {
          throw paramSettings;
        }
        long l1 = 0L;
      }
    }
    
    public void windowUpdate(int paramInt, long paramLong)
    {
      if (paramInt == 0)
      {
        localObject = FramedConnection.this;
        try
        {
          FramedConnection localFramedConnection = FramedConnection.this;
          bytesLeftInWriteWindow += paramLong;
          notifyAll();
          return;
        }
        catch (Throwable localThrowable1)
        {
          throw localThrowable1;
        }
      }
      Object localObject = getStream(paramInt);
      if (localObject != null) {
        try
        {
          ((FramedStream)localObject).addBytesToWriteWindow(paramLong);
          return;
        }
        catch (Throwable localThrowable2)
        {
          throw localThrowable2;
        }
      }
    }
  }
}
