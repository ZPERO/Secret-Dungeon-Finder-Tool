package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.Connection;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Headers.Builder;
import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.Response.Builder;
import com.squareup.okhttp.ResponseBody;
import com.squareup.okhttp.Route;
import com.squareup.okhttp.internal.Internal;
import com.squareup.okhttp.internal.Util;
import com.squareup.okhttp.internal.io.RealConnection;
import java.io.EOFException;
import java.io.IOException;
import java.net.ProtocolException;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ForwardingTimeout;
import okio.Okio;
import okio.Sink;
import okio.Source;
import okio.Timeout;

public final class Http1xStream
  implements HttpStream
{
  private static final int STATE_CLOSED = 6;
  private static final int STATE_IDLE = 0;
  private static final int STATE_OPEN_REQUEST_BODY = 1;
  private static final int STATE_OPEN_RESPONSE_BODY = 4;
  private static final int STATE_READING_RESPONSE_BODY = 5;
  private static final int STATE_READ_RESPONSE_HEADERS = 3;
  private static final int STATE_WRITING_REQUEST_BODY = 2;
  private HttpEngine httpEngine;
  private final BufferedSink sink;
  private final BufferedSource source;
  private int state = 0;
  private final StreamAllocation streamAllocation;
  
  public Http1xStream(StreamAllocation paramStreamAllocation, BufferedSource paramBufferedSource, BufferedSink paramBufferedSink)
  {
    streamAllocation = paramStreamAllocation;
    source = paramBufferedSource;
    sink = paramBufferedSink;
  }
  
  private void detachTimeout(ForwardingTimeout paramForwardingTimeout)
  {
    Timeout localTimeout = paramForwardingTimeout.delegate();
    paramForwardingTimeout.setDelegate(Timeout.NONE);
    localTimeout.clearDeadline();
    localTimeout.clearTimeout();
  }
  
  private Source getTransferStream(Response paramResponse)
    throws IOException
  {
    if (!HttpEngine.hasBody(paramResponse)) {
      return newFixedLengthSource(0L);
    }
    if ("chunked".equalsIgnoreCase(paramResponse.header("Transfer-Encoding"))) {
      return newChunkedSource(httpEngine);
    }
    long l = OkHeaders.contentLength(paramResponse);
    if (l != -1L) {
      return newFixedLengthSource(l);
    }
    return newUnknownLengthSource();
  }
  
  public void cancel()
  {
    RealConnection localRealConnection = streamAllocation.connection();
    if (localRealConnection != null) {
      localRealConnection.cancel();
    }
  }
  
  public Sink createRequestBody(Request paramRequest, long paramLong)
    throws IOException
  {
    if ("chunked".equalsIgnoreCase(paramRequest.header("Transfer-Encoding"))) {
      return newChunkedSink();
    }
    if (paramLong != -1L) {
      return newFixedLengthSink(paramLong);
    }
    throw new IllegalStateException("Cannot stream a request body without chunked encoding or a known content length!");
  }
  
  public void finishRequest()
    throws IOException
  {
    sink.flush();
  }
  
  public boolean isClosed()
  {
    return state == 6;
  }
  
  public Sink newChunkedSink()
  {
    if (state == 1)
    {
      state = 2;
      return new ChunkedSink(null);
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("state: ");
    localStringBuilder.append(state);
    throw new IllegalStateException(localStringBuilder.toString());
  }
  
  public Source newChunkedSource(HttpEngine paramHttpEngine)
    throws IOException
  {
    if (state == 4)
    {
      state = 5;
      return new ChunkedSource(paramHttpEngine);
    }
    paramHttpEngine = new StringBuilder();
    paramHttpEngine.append("state: ");
    paramHttpEngine.append(state);
    throw new IllegalStateException(paramHttpEngine.toString());
  }
  
  public Sink newFixedLengthSink(long paramLong)
  {
    if (state == 1)
    {
      state = 2;
      return new FixedLengthSink(paramLong, null);
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("state: ");
    localStringBuilder.append(state);
    throw new IllegalStateException(localStringBuilder.toString());
  }
  
  public Source newFixedLengthSource(long paramLong)
    throws IOException
  {
    if (state == 4)
    {
      state = 5;
      return new FixedLengthSource(paramLong);
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("state: ");
    localStringBuilder.append(state);
    throw new IllegalStateException(localStringBuilder.toString());
  }
  
  public Source newUnknownLengthSource()
    throws IOException
  {
    if (state == 4)
    {
      localObject = streamAllocation;
      if (localObject != null)
      {
        state = 5;
        ((StreamAllocation)localObject).noNewStreams();
        return new UnknownLengthSource(null);
      }
      throw new IllegalStateException("streamAllocation == null");
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("state: ");
    ((StringBuilder)localObject).append(state);
    throw new IllegalStateException(((StringBuilder)localObject).toString());
  }
  
  public ResponseBody openResponseBody(Response paramResponse)
    throws IOException
  {
    Source localSource = getTransferStream(paramResponse);
    return new RealResponseBody(paramResponse.headers(), Okio.buffer(localSource));
  }
  
  public Headers readHeaders()
    throws IOException
  {
    Headers.Builder localBuilder = new Headers.Builder();
    for (;;)
    {
      String str = source.readUtf8LineStrict();
      if (str.length() == 0) {
        break;
      }
      Internal.instance.addLenient(localBuilder, str);
    }
    return localBuilder.build();
  }
  
  public Response.Builder readResponse()
    throws IOException
  {
    int i = state;
    Object localObject1;
    if ((i != 1) && (i != 3))
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("state: ");
      ((StringBuilder)localObject1).append(state);
      throw new IllegalStateException(((StringBuilder)localObject1).toString());
    }
    for (;;)
    {
      localObject1 = source;
      try
      {
        localObject1 = StatusLine.parse(((BufferedSource)localObject1).readUtf8LineStrict());
        localObject2 = new Response.Builder();
        Object localObject3 = protocol;
        localObject2 = ((Response.Builder)localObject2).protocol((Protocol)localObject3);
        i = code;
        localObject2 = ((Response.Builder)localObject2).code(i);
        localObject3 = message;
        localObject2 = ((Response.Builder)localObject2).message((String)localObject3).headers(readHeaders());
        if (code == 100) {
          continue;
        }
        state = 4;
        return localObject2;
      }
      catch (EOFException localEOFException)
      {
        Object localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append("unexpected end of stream on ");
        ((StringBuilder)localObject2).append(streamAllocation);
        localObject2 = new IOException(((StringBuilder)localObject2).toString());
        ((IOException)localObject2).initCause(localEOFException);
        throw ((Throwable)localObject2);
      }
    }
  }
  
  public Response.Builder readResponseHeaders()
    throws IOException
  {
    return readResponse();
  }
  
  public void setHttpEngine(HttpEngine paramHttpEngine)
  {
    httpEngine = paramHttpEngine;
  }
  
  public void writeRequest(Headers paramHeaders, String paramString)
    throws IOException
  {
    if (state == 0)
    {
      sink.writeUtf8(paramString).writeUtf8("\r\n");
      int i = 0;
      int j = paramHeaders.size();
      while (i < j)
      {
        sink.writeUtf8(paramHeaders.name(i)).writeUtf8(": ").writeUtf8(paramHeaders.value(i)).writeUtf8("\r\n");
        i += 1;
      }
      sink.writeUtf8("\r\n");
      state = 1;
      return;
    }
    paramHeaders = new StringBuilder();
    paramHeaders.append("state: ");
    paramHeaders.append(state);
    paramHeaders = new IllegalStateException(paramHeaders.toString());
    throw paramHeaders;
  }
  
  public void writeRequestBody(RetryableSink paramRetryableSink)
    throws IOException
  {
    if (state == 1)
    {
      state = 3;
      paramRetryableSink.writeToSocket(sink);
      return;
    }
    paramRetryableSink = new StringBuilder();
    paramRetryableSink.append("state: ");
    paramRetryableSink.append(state);
    throw new IllegalStateException(paramRetryableSink.toString());
  }
  
  public void writeRequestHeaders(Request paramRequest)
    throws IOException
  {
    httpEngine.writingRequestHeaders();
    String str = RequestLine.get(paramRequest, httpEngine.getConnection().getRoute().getProxy().type());
    writeRequest(paramRequest.headers(), str);
  }
  
  private abstract class AbstractSource
    implements Source
  {
    protected boolean closed;
    protected final ForwardingTimeout timeout = new ForwardingTimeout(source.timeout());
    
    private AbstractSource() {}
    
    protected final void endOfInput()
      throws IOException
    {
      if (state == 5)
      {
        Http1xStream.this.detachTimeout(timeout);
        Http1xStream.access$502(Http1xStream.this, 6);
        if (streamAllocation != null) {
          streamAllocation.streamFinished(Http1xStream.this);
        }
      }
      else
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("state: ");
        localStringBuilder.append(state);
        throw new IllegalStateException(localStringBuilder.toString());
      }
    }
    
    public Timeout timeout()
    {
      return timeout;
    }
    
    protected final void unexpectedEndOfInput()
    {
      if (state == 6) {
        return;
      }
      Http1xStream.access$502(Http1xStream.this, 6);
      if (streamAllocation != null)
      {
        streamAllocation.noNewStreams();
        streamAllocation.streamFinished(Http1xStream.this);
      }
    }
  }
  
  private final class ChunkedSink
    implements Sink
  {
    private boolean closed;
    private final ForwardingTimeout timeout = new ForwardingTimeout(sink.timeout());
    
    private ChunkedSink() {}
    
    public void close()
      throws IOException
    {
      try
      {
        boolean bool = closed;
        if (bool) {
          return;
        }
        closed = true;
        sink.writeUtf8("0\r\n\r\n");
        Http1xStream.this.detachTimeout(timeout);
        Http1xStream.access$502(Http1xStream.this, 3);
        return;
      }
      catch (Throwable localThrowable)
      {
        throw localThrowable;
      }
    }
    
    public void flush()
      throws IOException
    {
      try
      {
        boolean bool = closed;
        if (bool) {
          return;
        }
        sink.flush();
        return;
      }
      catch (Throwable localThrowable)
      {
        throw localThrowable;
      }
    }
    
    public Timeout timeout()
    {
      return timeout;
    }
    
    public void write(Buffer paramBuffer, long paramLong)
      throws IOException
    {
      if (!closed)
      {
        if (paramLong == 0L) {
          return;
        }
        sink.writeHexadecimalUnsignedLong(paramLong);
        sink.writeUtf8("\r\n");
        sink.write(paramBuffer, paramLong);
        sink.writeUtf8("\r\n");
        return;
      }
      throw new IllegalStateException("closed");
    }
  }
  
  private class ChunkedSource
    extends Http1xStream.AbstractSource
  {
    private static final long NO_CHUNK_YET = -1L;
    private long bytesRemainingInChunk = -1L;
    private boolean hasMoreChunks = true;
    private final HttpEngine httpEngine;
    
    ChunkedSource(HttpEngine paramHttpEngine)
      throws IOException
    {
      super(null);
      httpEngine = paramHttpEngine;
    }
    
    private void readChunkSize()
      throws IOException
    {
      if (bytesRemainingInChunk != -1L) {
        source.readUtf8LineStrict();
      }
      Object localObject = Http1xStream.this;
      try
      {
        long l = source.readHexadecimalUnsignedLong();
        bytesRemainingInChunk = l;
        localObject = Http1xStream.this;
        localObject = source.readUtf8LineStrict().trim();
        if (bytesRemainingInChunk >= 0L)
        {
          boolean bool = ((String)localObject).isEmpty();
          if (!bool)
          {
            bool = ((String)localObject).startsWith(";");
            if (!bool) {}
          }
          else
          {
            if (bytesRemainingInChunk != 0L) {
              return;
            }
            hasMoreChunks = false;
            httpEngine.receiveHeaders(readHeaders());
            endOfInput();
            return;
          }
        }
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("expected chunk size and optional extensions but was \"");
        l = bytesRemainingInChunk;
        localStringBuilder.append(l);
        localStringBuilder.append((String)localObject);
        localStringBuilder.append("\"");
        localObject = new ProtocolException(localStringBuilder.toString());
        throw ((Throwable)localObject);
      }
      catch (NumberFormatException localNumberFormatException)
      {
        throw new ProtocolException(((NumberFormatException)localNumberFormatException).getMessage());
      }
    }
    
    public void close()
      throws IOException
    {
      if (closed) {
        return;
      }
      if ((hasMoreChunks) && (!Util.discard(this, 100, TimeUnit.MILLISECONDS))) {
        unexpectedEndOfInput();
      }
      closed = true;
    }
    
    public long read(Buffer paramBuffer, long paramLong)
      throws IOException
    {
      if (paramLong >= 0L)
      {
        if (!closed)
        {
          if (!hasMoreChunks) {
            return -1L;
          }
          long l = bytesRemainingInChunk;
          if ((l == 0L) || (l == -1L))
          {
            readChunkSize();
            if (!hasMoreChunks) {
              return -1L;
            }
          }
          paramLong = source.read(paramBuffer, Math.min(paramLong, bytesRemainingInChunk));
          if (paramLong != -1L)
          {
            bytesRemainingInChunk -= paramLong;
            return paramLong;
          }
          unexpectedEndOfInput();
          throw new ProtocolException("unexpected end of stream");
        }
        throw new IllegalStateException("closed");
      }
      paramBuffer = new StringBuilder();
      paramBuffer.append("byteCount < 0: ");
      paramBuffer.append(paramLong);
      throw new IllegalArgumentException(paramBuffer.toString());
    }
  }
  
  private final class FixedLengthSink
    implements Sink
  {
    private long bytesRemaining;
    private boolean closed;
    private final ForwardingTimeout timeout = new ForwardingTimeout(sink.timeout());
    
    private FixedLengthSink(long paramLong)
    {
      bytesRemaining = paramLong;
    }
    
    public void close()
      throws IOException
    {
      if (closed) {
        return;
      }
      closed = true;
      if (bytesRemaining <= 0L)
      {
        Http1xStream.this.detachTimeout(timeout);
        Http1xStream.access$502(Http1xStream.this, 3);
        return;
      }
      throw new ProtocolException("unexpected end of stream");
    }
    
    public void flush()
      throws IOException
    {
      if (closed) {
        return;
      }
      sink.flush();
    }
    
    public Timeout timeout()
    {
      return timeout;
    }
    
    public void write(Buffer paramBuffer, long paramLong)
      throws IOException
    {
      if (!closed)
      {
        Util.checkOffsetAndCount(paramBuffer.size(), 0L, paramLong);
        if (paramLong <= bytesRemaining)
        {
          sink.write(paramBuffer, paramLong);
          bytesRemaining -= paramLong;
          return;
        }
        paramBuffer = new StringBuilder();
        paramBuffer.append("expected ");
        paramBuffer.append(bytesRemaining);
        paramBuffer.append(" bytes but received ");
        paramBuffer.append(paramLong);
        throw new ProtocolException(paramBuffer.toString());
      }
      throw new IllegalStateException("closed");
    }
  }
  
  private class FixedLengthSource
    extends Http1xStream.AbstractSource
  {
    private long bytesRemaining;
    
    public FixedLengthSource(long paramLong)
      throws IOException
    {
      super(null);
      bytesRemaining = paramLong;
      if (bytesRemaining == 0L) {
        endOfInput();
      }
    }
    
    public void close()
      throws IOException
    {
      if (closed) {
        return;
      }
      if ((bytesRemaining != 0L) && (!Util.discard(this, 100, TimeUnit.MILLISECONDS))) {
        unexpectedEndOfInput();
      }
      closed = true;
    }
    
    public long read(Buffer paramBuffer, long paramLong)
      throws IOException
    {
      if (paramLong >= 0L)
      {
        if (!closed)
        {
          if (bytesRemaining == 0L) {
            return -1L;
          }
          paramLong = source.read(paramBuffer, Math.min(bytesRemaining, paramLong));
          if (paramLong != -1L)
          {
            bytesRemaining -= paramLong;
            if (bytesRemaining == 0L)
            {
              endOfInput();
              return paramLong;
            }
          }
          else
          {
            unexpectedEndOfInput();
            throw new ProtocolException("unexpected end of stream");
          }
        }
        else
        {
          throw new IllegalStateException("closed");
        }
      }
      else
      {
        paramBuffer = new StringBuilder();
        paramBuffer.append("byteCount < 0: ");
        paramBuffer.append(paramLong);
        throw new IllegalArgumentException(paramBuffer.toString());
      }
      return paramLong;
    }
  }
  
  private class UnknownLengthSource
    extends Http1xStream.AbstractSource
  {
    private boolean inputExhausted;
    
    private UnknownLengthSource()
    {
      super(null);
    }
    
    public void close()
      throws IOException
    {
      if (closed) {
        return;
      }
      if (!inputExhausted) {
        unexpectedEndOfInput();
      }
      closed = true;
    }
    
    public long read(Buffer paramBuffer, long paramLong)
      throws IOException
    {
      if (paramLong >= 0L)
      {
        if (!closed)
        {
          if (inputExhausted) {
            return -1L;
          }
          paramLong = source.read(paramBuffer, paramLong);
          if (paramLong == -1L)
          {
            inputExhausted = true;
            endOfInput();
            return -1L;
          }
          return paramLong;
        }
        throw new IllegalStateException("closed");
      }
      paramBuffer = new StringBuilder();
      paramBuffer.append("byteCount < 0: ");
      paramBuffer.append(paramLong);
      throw new IllegalArgumentException(paramBuffer.toString());
    }
  }
}
