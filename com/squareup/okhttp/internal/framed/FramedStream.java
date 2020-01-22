package com.squareup.okhttp.internal.framed;

import java.io.EOFException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import okio.AsyncTimeout;
import okio.Buffer;
import okio.BufferedSource;
import okio.Sink;
import okio.Source;
import okio.Timeout;

public final class FramedStream
{
  long bytesLeftInWriteWindow;
  private final FramedConnection connection;
  private ErrorCode errorCode = null;
  private final int id;
  private final StreamTimeout readTimeout = new StreamTimeout();
  private final List<Header> requestHeaders;
  private List<Header> responseHeaders;
  final FramedDataSink sink;
  private final FramedDataSource source;
  long unacknowledgedBytesRead = 0L;
  private final StreamTimeout writeTimeout = new StreamTimeout();
  
  FramedStream(int paramInt, FramedConnection paramFramedConnection, boolean paramBoolean1, boolean paramBoolean2, List paramList)
  {
    if (paramFramedConnection != null)
    {
      if (paramList != null)
      {
        id = paramInt;
        connection = paramFramedConnection;
        bytesLeftInWriteWindow = peerSettings.getInitialWindowSize(65536);
        source = new FramedDataSource(okHttpSettings.getInitialWindowSize(65536), null);
        sink = new FramedDataSink();
        FramedDataSource.access$102(source, paramBoolean2);
        FramedDataSink.access$202(sink, paramBoolean1);
        requestHeaders = paramList;
        return;
      }
      throw new NullPointerException("requestHeaders == null");
    }
    throw new NullPointerException("connection == null");
  }
  
  private void cancelStreamIfNecessary()
    throws IOException
  {
    for (;;)
    {
      try
      {
        if ((source.finished) || (!source.closed)) {
          break label92;
        }
        if (sink.finished) {
          break label87;
        }
        if (!sink.closed) {
          break label92;
        }
      }
      catch (Throwable localThrowable)
      {
        boolean bool;
        throw localThrowable;
      }
      bool = isOpen();
      if (i != 0)
      {
        close(ErrorCode.CANCEL);
        return;
      }
      if (!bool)
      {
        connection.removeStream(id);
        return;
      }
      return;
      label87:
      int i = 1;
      continue;
      label92:
      i = 0;
    }
  }
  
  private void checkOutNotClosed()
    throws IOException
  {
    if (!sink.closed)
    {
      if (!sink.finished)
      {
        if (errorCode == null) {
          return;
        }
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("stream was reset: ");
        localStringBuilder.append(errorCode);
        throw new IOException(localStringBuilder.toString());
      }
      throw new IOException("stream finished");
    }
    throw new IOException("stream closed");
  }
  
  private boolean closeInternal(ErrorCode paramErrorCode)
  {
    try
    {
      if (errorCode != null) {
        return false;
      }
      if ((source.finished) && (sink.finished)) {
        return false;
      }
      errorCode = paramErrorCode;
      notifyAll();
      connection.removeStream(id);
      return true;
    }
    catch (Throwable paramErrorCode)
    {
      throw paramErrorCode;
    }
  }
  
  private void waitForIo()
    throws InterruptedIOException
  {
    try
    {
      wait();
      return;
    }
    catch (InterruptedException localInterruptedException)
    {
      for (;;) {}
    }
    throw new InterruptedIOException();
  }
  
  void addBytesToWriteWindow(long paramLong)
  {
    bytesLeftInWriteWindow += paramLong;
    if (paramLong > 0L) {
      notifyAll();
    }
  }
  
  public void close(ErrorCode paramErrorCode)
    throws IOException
  {
    if (!closeInternal(paramErrorCode)) {
      return;
    }
    connection.writeSynReset(id, paramErrorCode);
  }
  
  public void closeLater(ErrorCode paramErrorCode)
  {
    if (!closeInternal(paramErrorCode)) {
      return;
    }
    connection.writeSynResetLater(id, paramErrorCode);
  }
  
  public FramedConnection getConnection()
  {
    return connection;
  }
  
  public ErrorCode getErrorCode()
  {
    try
    {
      ErrorCode localErrorCode = errorCode;
      return localErrorCode;
    }
    catch (Throwable localThrowable)
    {
      throw localThrowable;
    }
  }
  
  public int getId()
  {
    return id;
  }
  
  public List getRequestHeaders()
  {
    return requestHeaders;
  }
  
  /* Error */
  public List getResponseHeaders()
    throws IOException
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 52	com/squareup/okhttp/internal/framed/FramedStream:readTimeout	Lcom/squareup/okhttp/internal/framed/FramedStream$StreamTimeout;
    //   6: invokevirtual 222	okio/AsyncTimeout:enter	()V
    //   9: aload_0
    //   10: getfield 224	com/squareup/okhttp/internal/framed/FramedStream:responseHeaders	Ljava/util/List;
    //   13: ifnonnull +17 -> 30
    //   16: aload_0
    //   17: getfield 56	com/squareup/okhttp/internal/framed/FramedStream:errorCode	Lcom/squareup/okhttp/internal/framed/ErrorCode;
    //   20: ifnonnull +10 -> 30
    //   23: aload_0
    //   24: invokespecial 131	com/squareup/okhttp/internal/framed/FramedStream:waitForIo	()V
    //   27: goto -18 -> 9
    //   30: aload_0
    //   31: getfield 52	com/squareup/okhttp/internal/framed/FramedStream:readTimeout	Lcom/squareup/okhttp/internal/framed/FramedStream$StreamTimeout;
    //   34: invokevirtual 227	com/squareup/okhttp/internal/framed/FramedStream$StreamTimeout:exitAndThrowIfTimedOut	()V
    //   37: aload_0
    //   38: getfield 224	com/squareup/okhttp/internal/framed/FramedStream:responseHeaders	Ljava/util/List;
    //   41: ifnull +12 -> 53
    //   44: aload_0
    //   45: getfield 224	com/squareup/okhttp/internal/framed/FramedStream:responseHeaders	Ljava/util/List;
    //   48: astore_1
    //   49: aload_0
    //   50: monitorexit
    //   51: aload_1
    //   52: areturn
    //   53: new 166	java/lang/StringBuilder
    //   56: dup
    //   57: invokespecial 167	java/lang/StringBuilder:<init>	()V
    //   60: astore_1
    //   61: aload_1
    //   62: ldc -87
    //   64: invokevirtual 173	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   67: pop
    //   68: aload_1
    //   69: aload_0
    //   70: getfield 56	com/squareup/okhttp/internal/framed/FramedStream:errorCode	Lcom/squareup/okhttp/internal/framed/ErrorCode;
    //   73: invokevirtual 176	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   76: pop
    //   77: new 108	java/io/IOException
    //   80: dup
    //   81: aload_1
    //   82: invokevirtual 180	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   85: invokespecial 181	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   88: athrow
    //   89: astore_1
    //   90: aload_0
    //   91: getfield 52	com/squareup/okhttp/internal/framed/FramedStream:readTimeout	Lcom/squareup/okhttp/internal/framed/FramedStream$StreamTimeout;
    //   94: invokevirtual 227	com/squareup/okhttp/internal/framed/FramedStream$StreamTimeout:exitAndThrowIfTimedOut	()V
    //   97: aload_1
    //   98: athrow
    //   99: astore_1
    //   100: aload_0
    //   101: monitorexit
    //   102: goto +3 -> 105
    //   105: aload_1
    //   106: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	107	0	this	FramedStream
    //   48	34	1	localObject	Object
    //   89	9	1	localThrowable1	Throwable
    //   99	7	1	localThrowable2	Throwable
    // Exception table:
    //   from	to	target	type
    //   9	27	89	java/lang/Throwable
    //   2	9	99	java/lang/Throwable
    //   30	49	99	java/lang/Throwable
    //   53	89	99	java/lang/Throwable
    //   90	99	99	java/lang/Throwable
  }
  
  public Sink getSink()
  {
    try
    {
      if ((responseHeaders == null) && (!isLocallyInitiated())) {
        throw new IllegalStateException("reply before requesting the sink");
      }
      return sink;
    }
    catch (Throwable localThrowable)
    {
      throw localThrowable;
    }
  }
  
  public Source getSource()
  {
    return source;
  }
  
  public boolean isLocallyInitiated()
  {
    int i;
    if ((id & 0x1) == 1) {
      i = 1;
    } else {
      i = 0;
    }
    return connection.client == i;
  }
  
  public boolean isOpen()
  {
    try
    {
      Object localObject = errorCode;
      if (localObject != null) {
        return false;
      }
      if (((source.finished) || (source.closed)) && ((sink.finished) || (sink.closed)))
      {
        localObject = responseHeaders;
        if (localObject != null) {
          return false;
        }
      }
      return true;
    }
    catch (Throwable localThrowable)
    {
      throw localThrowable;
    }
  }
  
  public Timeout readTimeout()
  {
    return readTimeout;
  }
  
  void receiveData(BufferedSource paramBufferedSource, int paramInt)
    throws IOException
  {
    source.receive(paramBufferedSource, paramInt);
  }
  
  void receiveFin()
  {
    try
    {
      FramedDataSource.access$102(source, true);
      boolean bool = isOpen();
      notifyAll();
      if (!bool)
      {
        connection.removeStream(id);
        return;
      }
    }
    catch (Throwable localThrowable)
    {
      throw localThrowable;
    }
  }
  
  void receiveHeaders(List paramList, HeadersMode paramHeadersMode)
  {
    Object localObject = null;
    boolean bool = true;
    try
    {
      if (responseHeaders == null)
      {
        if (paramHeadersMode.failIfHeadersAbsent())
        {
          paramList = ErrorCode.PROTOCOL_ERROR;
        }
        else
        {
          responseHeaders = paramList;
          bool = isOpen();
          notifyAll();
          paramList = localObject;
        }
      }
      else if (paramHeadersMode.failIfHeadersPresent())
      {
        paramList = ErrorCode.STREAM_IN_USE;
      }
      else
      {
        paramHeadersMode = new ArrayList();
        paramHeadersMode.addAll(responseHeaders);
        paramHeadersMode.addAll(paramList);
        responseHeaders = paramHeadersMode;
        paramList = localObject;
      }
      if (paramList != null)
      {
        closeLater(paramList);
        return;
      }
      if (!bool)
      {
        connection.removeStream(id);
        return;
      }
    }
    catch (Throwable paramList)
    {
      throw paramList;
    }
  }
  
  void receiveRstStream(ErrorCode paramErrorCode)
  {
    try
    {
      if (errorCode == null)
      {
        errorCode = paramErrorCode;
        notifyAll();
      }
      return;
    }
    catch (Throwable paramErrorCode)
    {
      throw paramErrorCode;
    }
  }
  
  public void reply(List paramList, boolean paramBoolean)
    throws IOException
  {
    boolean bool = false;
    if (paramList != null) {}
    try
    {
      if (responseHeaders == null)
      {
        responseHeaders = paramList;
        if (!paramBoolean)
        {
          FramedDataSink.access$202(sink, true);
          bool = true;
        }
        connection.writeSynReply(id, bool, paramList);
        if (!bool) {
          break label88;
        }
        connection.flush();
        return;
      }
      throw new IllegalStateException("reply already sent");
    }
    catch (Throwable paramList)
    {
      label88:
      for (;;) {}
    }
    throw new NullPointerException("responseHeaders == null");
    throw paramList;
  }
  
  public Timeout writeTimeout()
  {
    return writeTimeout;
  }
  
  final class FramedDataSink
    implements Sink
  {
    private static final long EMIT_BUFFER_SIZE = 16384L;
    private boolean closed;
    private boolean finished;
    private final Buffer sendBuffer = new Buffer();
    
    FramedDataSink() {}
    
    /* Error */
    private void emitDataFrame(boolean paramBoolean)
      throws IOException
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 29	com/squareup/okhttp/internal/framed/FramedStream$FramedDataSink:this$0	Lcom/squareup/okhttp/internal/framed/FramedStream;
      //   4: astore 7
      //   6: aload 7
      //   8: monitorenter
      //   9: aload_0
      //   10: getfield 29	com/squareup/okhttp/internal/framed/FramedStream$FramedDataSink:this$0	Lcom/squareup/okhttp/internal/framed/FramedStream;
      //   13: invokestatic 55	com/squareup/okhttp/internal/framed/FramedStream:access$1100	(Lcom/squareup/okhttp/internal/framed/FramedStream;)Lcom/squareup/okhttp/internal/framed/FramedStream$StreamTimeout;
      //   16: invokevirtual 60	okio/AsyncTimeout:enter	()V
      //   19: aload_0
      //   20: getfield 29	com/squareup/okhttp/internal/framed/FramedStream$FramedDataSink:this$0	Lcom/squareup/okhttp/internal/framed/FramedStream;
      //   23: getfield 63	com/squareup/okhttp/internal/framed/FramedStream:bytesLeftInWriteWindow	J
      //   26: lconst_0
      //   27: lcmp
      //   28: ifgt +37 -> 65
      //   31: aload_0
      //   32: getfield 40	com/squareup/okhttp/internal/framed/FramedStream$FramedDataSink:finished	Z
      //   35: ifne +30 -> 65
      //   38: aload_0
      //   39: getfield 45	com/squareup/okhttp/internal/framed/FramedStream$FramedDataSink:closed	Z
      //   42: ifne +23 -> 65
      //   45: aload_0
      //   46: getfield 29	com/squareup/okhttp/internal/framed/FramedStream$FramedDataSink:this$0	Lcom/squareup/okhttp/internal/framed/FramedStream;
      //   49: invokestatic 67	com/squareup/okhttp/internal/framed/FramedStream:access$800	(Lcom/squareup/okhttp/internal/framed/FramedStream;)Lcom/squareup/okhttp/internal/framed/ErrorCode;
      //   52: ifnonnull +13 -> 65
      //   55: aload_0
      //   56: getfield 29	com/squareup/okhttp/internal/framed/FramedStream$FramedDataSink:this$0	Lcom/squareup/okhttp/internal/framed/FramedStream;
      //   59: invokestatic 70	com/squareup/okhttp/internal/framed/FramedStream:access$900	(Lcom/squareup/okhttp/internal/framed/FramedStream;)V
      //   62: goto -43 -> 19
      //   65: aload_0
      //   66: getfield 29	com/squareup/okhttp/internal/framed/FramedStream$FramedDataSink:this$0	Lcom/squareup/okhttp/internal/framed/FramedStream;
      //   69: invokestatic 55	com/squareup/okhttp/internal/framed/FramedStream:access$1100	(Lcom/squareup/okhttp/internal/framed/FramedStream;)Lcom/squareup/okhttp/internal/framed/FramedStream$StreamTimeout;
      //   72: invokevirtual 75	com/squareup/okhttp/internal/framed/FramedStream$StreamTimeout:exitAndThrowIfTimedOut	()V
      //   75: aload_0
      //   76: getfield 29	com/squareup/okhttp/internal/framed/FramedStream$FramedDataSink:this$0	Lcom/squareup/okhttp/internal/framed/FramedStream;
      //   79: invokestatic 78	com/squareup/okhttp/internal/framed/FramedStream:access$1200	(Lcom/squareup/okhttp/internal/framed/FramedStream;)V
      //   82: aload_0
      //   83: getfield 29	com/squareup/okhttp/internal/framed/FramedStream$FramedDataSink:this$0	Lcom/squareup/okhttp/internal/framed/FramedStream;
      //   86: getfield 63	com/squareup/okhttp/internal/framed/FramedStream:bytesLeftInWriteWindow	J
      //   89: aload_0
      //   90: getfield 36	com/squareup/okhttp/internal/framed/FramedStream$FramedDataSink:sendBuffer	Lokio/Buffer;
      //   93: invokevirtual 82	okio/Buffer:size	()J
      //   96: invokestatic 88	java/lang/Math:min	(JJ)J
      //   99: lstore_3
      //   100: aload_0
      //   101: getfield 29	com/squareup/okhttp/internal/framed/FramedStream$FramedDataSink:this$0	Lcom/squareup/okhttp/internal/framed/FramedStream;
      //   104: astore 8
      //   106: aload 8
      //   108: aload 8
      //   110: getfield 63	com/squareup/okhttp/internal/framed/FramedStream:bytesLeftInWriteWindow	J
      //   113: lload_3
      //   114: lsub
      //   115: putfield 63	com/squareup/okhttp/internal/framed/FramedStream:bytesLeftInWriteWindow	J
      //   118: aload 7
      //   120: monitorexit
      //   121: aload_0
      //   122: getfield 29	com/squareup/okhttp/internal/framed/FramedStream$FramedDataSink:this$0	Lcom/squareup/okhttp/internal/framed/FramedStream;
      //   125: invokestatic 55	com/squareup/okhttp/internal/framed/FramedStream:access$1100	(Lcom/squareup/okhttp/internal/framed/FramedStream;)Lcom/squareup/okhttp/internal/framed/FramedStream$StreamTimeout;
      //   128: invokevirtual 60	okio/AsyncTimeout:enter	()V
      //   131: aload_0
      //   132: getfield 29	com/squareup/okhttp/internal/framed/FramedStream$FramedDataSink:this$0	Lcom/squareup/okhttp/internal/framed/FramedStream;
      //   135: invokestatic 92	com/squareup/okhttp/internal/framed/FramedStream:access$500	(Lcom/squareup/okhttp/internal/framed/FramedStream;)Lcom/squareup/okhttp/internal/framed/FramedConnection;
      //   138: astore 7
      //   140: aload_0
      //   141: getfield 29	com/squareup/okhttp/internal/framed/FramedStream$FramedDataSink:this$0	Lcom/squareup/okhttp/internal/framed/FramedStream;
      //   144: invokestatic 96	com/squareup/okhttp/internal/framed/FramedStream:access$600	(Lcom/squareup/okhttp/internal/framed/FramedStream;)I
      //   147: istore_2
      //   148: iload_1
      //   149: ifeq +24 -> 173
      //   152: aload_0
      //   153: getfield 36	com/squareup/okhttp/internal/framed/FramedStream$FramedDataSink:sendBuffer	Lokio/Buffer;
      //   156: invokevirtual 82	okio/Buffer:size	()J
      //   159: lstore 5
      //   161: lload_3
      //   162: lload 5
      //   164: lcmp
      //   165: ifne +8 -> 173
      //   168: iconst_1
      //   169: istore_1
      //   170: goto +5 -> 175
      //   173: iconst_0
      //   174: istore_1
      //   175: aload 7
      //   177: iload_2
      //   178: iload_1
      //   179: aload_0
      //   180: getfield 36	com/squareup/okhttp/internal/framed/FramedStream$FramedDataSink:sendBuffer	Lokio/Buffer;
      //   183: lload_3
      //   184: invokevirtual 102	com/squareup/okhttp/internal/framed/FramedConnection:writeData	(IZLokio/Buffer;J)V
      //   187: aload_0
      //   188: getfield 29	com/squareup/okhttp/internal/framed/FramedStream$FramedDataSink:this$0	Lcom/squareup/okhttp/internal/framed/FramedStream;
      //   191: invokestatic 55	com/squareup/okhttp/internal/framed/FramedStream:access$1100	(Lcom/squareup/okhttp/internal/framed/FramedStream;)Lcom/squareup/okhttp/internal/framed/FramedStream$StreamTimeout;
      //   194: invokevirtual 75	com/squareup/okhttp/internal/framed/FramedStream$StreamTimeout:exitAndThrowIfTimedOut	()V
      //   197: return
      //   198: astore 7
      //   200: aload_0
      //   201: getfield 29	com/squareup/okhttp/internal/framed/FramedStream$FramedDataSink:this$0	Lcom/squareup/okhttp/internal/framed/FramedStream;
      //   204: invokestatic 55	com/squareup/okhttp/internal/framed/FramedStream:access$1100	(Lcom/squareup/okhttp/internal/framed/FramedStream;)Lcom/squareup/okhttp/internal/framed/FramedStream$StreamTimeout;
      //   207: invokevirtual 75	com/squareup/okhttp/internal/framed/FramedStream$StreamTimeout:exitAndThrowIfTimedOut	()V
      //   210: aload 7
      //   212: athrow
      //   213: astore 8
      //   215: aload_0
      //   216: getfield 29	com/squareup/okhttp/internal/framed/FramedStream$FramedDataSink:this$0	Lcom/squareup/okhttp/internal/framed/FramedStream;
      //   219: invokestatic 55	com/squareup/okhttp/internal/framed/FramedStream:access$1100	(Lcom/squareup/okhttp/internal/framed/FramedStream;)Lcom/squareup/okhttp/internal/framed/FramedStream$StreamTimeout;
      //   222: invokevirtual 75	com/squareup/okhttp/internal/framed/FramedStream$StreamTimeout:exitAndThrowIfTimedOut	()V
      //   225: aload 8
      //   227: athrow
      //   228: astore 8
      //   230: aload 7
      //   232: monitorexit
      //   233: goto +3 -> 236
      //   236: aload 8
      //   238: athrow
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	239	0	this	FramedDataSink
      //   0	239	1	paramBoolean	boolean
      //   147	31	2	i	int
      //   99	85	3	l1	long
      //   159	4	5	l2	long
      //   4	172	7	localObject	Object
      //   198	33	7	localThrowable1	Throwable
      //   104	5	8	localFramedStream	FramedStream
      //   213	13	8	localThrowable2	Throwable
      //   228	9	8	localThrowable3	Throwable
      // Exception table:
      //   from	to	target	type
      //   131	148	198	java/lang/Throwable
      //   152	161	198	java/lang/Throwable
      //   175	187	198	java/lang/Throwable
      //   19	62	213	java/lang/Throwable
      //   9	19	228	java/lang/Throwable
      //   65	121	228	java/lang/Throwable
      //   215	228	228	java/lang/Throwable
      //   230	233	228	java/lang/Throwable
    }
    
    /* Error */
    public void close()
      throws IOException
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 29	com/squareup/okhttp/internal/framed/FramedStream$FramedDataSink:this$0	Lcom/squareup/okhttp/internal/framed/FramedStream;
      //   4: astore_1
      //   5: aload_1
      //   6: monitorenter
      //   7: aload_0
      //   8: getfield 45	com/squareup/okhttp/internal/framed/FramedStream$FramedDataSink:closed	Z
      //   11: ifeq +6 -> 17
      //   14: aload_1
      //   15: monitorexit
      //   16: return
      //   17: aload_1
      //   18: monitorexit
      //   19: aload_0
      //   20: getfield 29	com/squareup/okhttp/internal/framed/FramedStream$FramedDataSink:this$0	Lcom/squareup/okhttp/internal/framed/FramedStream;
      //   23: getfield 108	com/squareup/okhttp/internal/framed/FramedStream:sink	Lcom/squareup/okhttp/internal/framed/FramedStream$FramedDataSink;
      //   26: getfield 40	com/squareup/okhttp/internal/framed/FramedStream$FramedDataSink:finished	Z
      //   29: ifne +55 -> 84
      //   32: aload_0
      //   33: getfield 36	com/squareup/okhttp/internal/framed/FramedStream$FramedDataSink:sendBuffer	Lokio/Buffer;
      //   36: invokevirtual 82	okio/Buffer:size	()J
      //   39: lconst_0
      //   40: lcmp
      //   41: ifle +23 -> 64
      //   44: aload_0
      //   45: getfield 36	com/squareup/okhttp/internal/framed/FramedStream$FramedDataSink:sendBuffer	Lokio/Buffer;
      //   48: invokevirtual 82	okio/Buffer:size	()J
      //   51: lconst_0
      //   52: lcmp
      //   53: ifle +31 -> 84
      //   56: aload_0
      //   57: iconst_1
      //   58: invokespecial 110	com/squareup/okhttp/internal/framed/FramedStream$FramedDataSink:emitDataFrame	(Z)V
      //   61: goto -17 -> 44
      //   64: aload_0
      //   65: getfield 29	com/squareup/okhttp/internal/framed/FramedStream$FramedDataSink:this$0	Lcom/squareup/okhttp/internal/framed/FramedStream;
      //   68: invokestatic 92	com/squareup/okhttp/internal/framed/FramedStream:access$500	(Lcom/squareup/okhttp/internal/framed/FramedStream;)Lcom/squareup/okhttp/internal/framed/FramedConnection;
      //   71: aload_0
      //   72: getfield 29	com/squareup/okhttp/internal/framed/FramedStream$FramedDataSink:this$0	Lcom/squareup/okhttp/internal/framed/FramedStream;
      //   75: invokestatic 96	com/squareup/okhttp/internal/framed/FramedStream:access$600	(Lcom/squareup/okhttp/internal/framed/FramedStream;)I
      //   78: iconst_1
      //   79: aconst_null
      //   80: lconst_0
      //   81: invokevirtual 102	com/squareup/okhttp/internal/framed/FramedConnection:writeData	(IZLokio/Buffer;J)V
      //   84: aload_0
      //   85: getfield 29	com/squareup/okhttp/internal/framed/FramedStream$FramedDataSink:this$0	Lcom/squareup/okhttp/internal/framed/FramedStream;
      //   88: astore_1
      //   89: aload_1
      //   90: monitorenter
      //   91: aload_0
      //   92: iconst_1
      //   93: putfield 45	com/squareup/okhttp/internal/framed/FramedStream$FramedDataSink:closed	Z
      //   96: aload_1
      //   97: monitorexit
      //   98: aload_0
      //   99: getfield 29	com/squareup/okhttp/internal/framed/FramedStream$FramedDataSink:this$0	Lcom/squareup/okhttp/internal/framed/FramedStream;
      //   102: invokestatic 92	com/squareup/okhttp/internal/framed/FramedStream:access$500	(Lcom/squareup/okhttp/internal/framed/FramedStream;)Lcom/squareup/okhttp/internal/framed/FramedConnection;
      //   105: invokevirtual 113	com/squareup/okhttp/internal/framed/FramedConnection:flush	()V
      //   108: aload_0
      //   109: getfield 29	com/squareup/okhttp/internal/framed/FramedStream$FramedDataSink:this$0	Lcom/squareup/okhttp/internal/framed/FramedStream;
      //   112: invokestatic 116	com/squareup/okhttp/internal/framed/FramedStream:access$1000	(Lcom/squareup/okhttp/internal/framed/FramedStream;)V
      //   115: return
      //   116: astore_2
      //   117: aload_1
      //   118: monitorexit
      //   119: aload_2
      //   120: athrow
      //   121: astore_2
      //   122: aload_1
      //   123: monitorexit
      //   124: goto +3 -> 127
      //   127: aload_2
      //   128: athrow
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	129	0	this	FramedDataSink
      //   4	119	1	localFramedStream	FramedStream
      //   116	4	2	localThrowable1	Throwable
      //   121	7	2	localThrowable2	Throwable
      // Exception table:
      //   from	to	target	type
      //   91	98	116	java/lang/Throwable
      //   117	119	116	java/lang/Throwable
      //   7	16	121	java/lang/Throwable
      //   17	19	121	java/lang/Throwable
      //   122	124	121	java/lang/Throwable
    }
    
    public void flush()
      throws IOException
    {
      FramedStream localFramedStream = FramedStream.this;
      try
      {
        FramedStream.this.checkOutNotClosed();
        while (sendBuffer.size() > 0L)
        {
          emitDataFrame(false);
          connection.flush();
        }
        return;
      }
      catch (Throwable localThrowable)
      {
        throw localThrowable;
      }
    }
    
    public Timeout timeout()
    {
      return writeTimeout;
    }
    
    public void write(Buffer paramBuffer, long paramLong)
      throws IOException
    {
      sendBuffer.write(paramBuffer, paramLong);
      while (sendBuffer.size() >= 16384L) {
        emitDataFrame(false);
      }
    }
  }
  
  private final class FramedDataSource
    implements Source
  {
    private boolean closed;
    private boolean finished;
    private final long maxByteCount;
    private final Buffer readBuffer = new Buffer();
    private final Buffer receiveBuffer = new Buffer();
    
    private FramedDataSource(long paramLong)
    {
      maxByteCount = paramLong;
    }
    
    private void checkNotClosed()
      throws IOException
    {
      if (!closed)
      {
        if (errorCode == null) {
          return;
        }
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("stream was reset: ");
        localStringBuilder.append(errorCode);
        throw new IOException(localStringBuilder.toString());
      }
      throw new IOException("stream closed");
    }
    
    private void waitUntilReadable()
      throws IOException
    {
      readTimeout.enter();
      try
      {
        for (;;)
        {
          long l = readBuffer.size();
          if (l != 0L) {
            break;
          }
          boolean bool = finished;
          if (bool) {
            break;
          }
          bool = closed;
          if (bool) {
            break;
          }
          ErrorCode localErrorCode = errorCode;
          if (localErrorCode != null) {
            break;
          }
          FramedStream.this.waitForIo();
        }
        readTimeout.exitAndThrowIfTimedOut();
        return;
      }
      catch (Throwable localThrowable)
      {
        readTimeout.exitAndThrowIfTimedOut();
        throw localThrowable;
      }
    }
    
    public void close()
      throws IOException
    {
      FramedStream localFramedStream = FramedStream.this;
      try
      {
        closed = true;
        readBuffer.clear();
        notifyAll();
        FramedStream.this.cancelStreamIfNecessary();
        return;
      }
      catch (Throwable localThrowable)
      {
        throw localThrowable;
      }
    }
    
    public long read(Buffer paramBuffer, long paramLong)
      throws IOException
    {
      if (paramLong >= 0L)
      {
        Object localObject = FramedStream.this;
        try
        {
          waitUntilReadable();
          checkNotClosed();
          if (readBuffer.size() == 0L) {
            return -1L;
          }
          paramLong = readBuffer.read(paramBuffer, Math.min(paramLong, readBuffer.size()));
          paramBuffer = FramedStream.this;
          unacknowledgedBytesRead += paramLong;
          if (unacknowledgedBytesRead >= connection.okHttpSettings.getInitialWindowSize(65536) / 2)
          {
            connection.writeWindowUpdateLater(id, unacknowledgedBytesRead);
            unacknowledgedBytesRead = 0L;
          }
          paramBuffer = connection;
          try
          {
            localObject = connection;
            unacknowledgedBytesRead += paramLong;
            if (connection.unacknowledgedBytesRead >= connection.okHttpSettings.getInitialWindowSize(65536) / 2)
            {
              connection.writeWindowUpdateLater(0, connection.unacknowledgedBytesRead);
              connection.unacknowledgedBytesRead = 0L;
            }
            return paramLong;
          }
          catch (Throwable localThrowable)
          {
            throw localThrowable;
          }
          paramBuffer = new StringBuilder();
        }
        catch (Throwable paramBuffer)
        {
          throw paramBuffer;
        }
      }
      paramBuffer.append("byteCount < 0: ");
      paramBuffer.append(paramLong);
      throw new IllegalArgumentException(paramBuffer.toString());
    }
    
    void receive(BufferedSource paramBufferedSource, long paramLong)
      throws IOException
    {
      FramedStream localFramedStream;
      if (paramLong > 0L) {
        localFramedStream = FramedStream.this;
      }
      for (;;)
      {
        try
        {
          boolean bool = finished;
          long l1 = readBuffer.size();
          long l2 = maxByteCount;
          int j = 1;
          if (l1 + paramLong <= l2) {
            break label200;
          }
          i = 1;
          if (i != 0)
          {
            paramBufferedSource.skip(paramLong);
            closeLater(ErrorCode.FLOW_CONTROL_ERROR);
            return;
          }
          if (bool)
          {
            paramBufferedSource.skip(paramLong);
            return;
          }
          l1 = paramBufferedSource.read(receiveBuffer, paramLong);
          if (l1 != -1L)
          {
            paramLong -= l1;
            localFramedStream = FramedStream.this;
            try
            {
              if (readBuffer.size() != 0L) {
                break label206;
              }
              i = j;
              readBuffer.writeAll(receiveBuffer);
              if (i != 0) {
                notifyAll();
              }
            }
            catch (Throwable paramBufferedSource)
            {
              throw paramBufferedSource;
            }
          }
          throw new EOFException();
        }
        catch (Throwable paramBufferedSource)
        {
          throw paramBufferedSource;
        }
        return;
        label200:
        int i = 0;
        continue;
        label206:
        i = 0;
      }
    }
    
    public Timeout timeout()
    {
      return readTimeout;
    }
  }
  
  class StreamTimeout
    extends AsyncTimeout
  {
    StreamTimeout() {}
    
    public void exitAndThrowIfTimedOut()
      throws IOException
    {
      if (!exit()) {
        return;
      }
      throw newTimeoutException(null);
    }
    
    protected IOException newTimeoutException(IOException paramIOException)
    {
      SocketTimeoutException localSocketTimeoutException = new SocketTimeoutException("timeout");
      if (paramIOException != null) {
        localSocketTimeoutException.initCause(paramIOException);
      }
      return localSocketTimeoutException;
    }
    
    protected void timedOut()
    {
      closeLater(ErrorCode.CANCEL);
    }
  }
}
