package com.squareup.okhttp;

import com.squareup.okhttp.internal.DiskLruCache;
import com.squareup.okhttp.internal.DiskLruCache.Editor;
import com.squareup.okhttp.internal.DiskLruCache.Snapshot;
import com.squareup.okhttp.internal.InternalCache;
import com.squareup.okhttp.internal.Util;
import com.squareup.okhttp.internal.http.CacheRequest;
import com.squareup.okhttp.internal.http.CacheStrategy;
import com.squareup.okhttp.internal.http.HttpMethod;
import com.squareup.okhttp.internal.http.OkHeaders;
import com.squareup.okhttp.internal.http.StatusLine;
import com.squareup.okhttp.internal.io.FileSystem;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ByteString;
import okio.ForwardingSink;
import okio.ForwardingSource;
import okio.Okio;
import okio.Sink;
import okio.Source;

public final class Cache
{
  private static final int ENTRY_BODY = 1;
  private static final int ENTRY_COUNT = 2;
  private static final int ENTRY_METADATA = 0;
  private static final int VERSION = 201105;
  private final DiskLruCache cache;
  private int hitCount;
  final InternalCache internalCache = new InternalCache()
  {
    public Response get(Request paramAnonymousRequest)
      throws IOException
    {
      return Cache.this.get(paramAnonymousRequest);
    }
    
    public CacheRequest put(Response paramAnonymousResponse)
      throws IOException
    {
      return Cache.this.put(paramAnonymousResponse);
    }
    
    public void remove(Request paramAnonymousRequest)
      throws IOException
    {
      Cache.this.remove(paramAnonymousRequest);
    }
    
    public void trackConditionalCacheHit()
    {
      Cache.this.trackConditionalCacheHit();
    }
    
    public void trackResponse(CacheStrategy paramAnonymousCacheStrategy)
    {
      Cache.this.trackResponse(paramAnonymousCacheStrategy);
    }
    
    public void update(Response paramAnonymousResponse1, Response paramAnonymousResponse2)
      throws IOException
    {
      Cache.this.update(paramAnonymousResponse1, paramAnonymousResponse2);
    }
  };
  private int networkCount;
  private int requestCount;
  private int writeAbortCount;
  private int writeSuccessCount;
  
  public Cache(File paramFile, long paramLong)
  {
    this(paramFile, paramLong, FileSystem.SYSTEM);
  }
  
  Cache(File paramFile, long paramLong, FileSystem paramFileSystem)
  {
    cache = DiskLruCache.create(paramFileSystem, paramFile, 201105, 2, paramLong);
  }
  
  private void abortQuietly(DiskLruCache.Editor paramEditor)
  {
    if (paramEditor != null) {
      try
      {
        paramEditor.abort();
        return;
      }
      catch (IOException paramEditor) {}
    }
  }
  
  private CacheRequest put(Response paramResponse)
    throws IOException
  {
    Object localObject = paramResponse.request().method();
    if (HttpMethod.invalidatesCache(paramResponse.request().method())) {}
    try
    {
      remove(paramResponse.request());
      return null;
    }
    catch (IOException paramResponse)
    {
      Entry localEntry;
      return null;
    }
    if (!((String)localObject).equals("GET")) {
      return null;
    }
    if (OkHeaders.hasVaryAll(paramResponse)) {
      return null;
    }
    localEntry = new Entry(paramResponse);
    localObject = cache;
    try
    {
      localObject = ((DiskLruCache)localObject).edit(urlToKey(paramResponse.request()));
      paramResponse = (Response)localObject;
      if (localObject == null) {
        return null;
      }
    }
    catch (IOException paramResponse)
    {
      label104:
      for (;;) {}
    }
    try
    {
      localEntry.writeTo((DiskLruCache.Editor)localObject);
      localObject = new CacheRequestImpl((DiskLruCache.Editor)localObject);
      return localObject;
    }
    catch (IOException localIOException)
    {
      break label104;
    }
    paramResponse = null;
    abortQuietly(paramResponse);
    return null;
  }
  
  private static int readInt(BufferedSource paramBufferedSource)
    throws IOException
  {
    try
    {
      long l = paramBufferedSource.readDecimalLong();
      paramBufferedSource = paramBufferedSource.readUtf8LineStrict();
      if ((l >= 0L) && (l <= 2147483647L))
      {
        boolean bool = paramBufferedSource.isEmpty();
        if (bool) {
          return (int)l;
        }
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("expected an int but was \"");
      localStringBuilder.append(l);
      localStringBuilder.append(paramBufferedSource);
      localStringBuilder.append("\"");
      paramBufferedSource = new IOException(localStringBuilder.toString());
      throw paramBufferedSource;
    }
    catch (NumberFormatException paramBufferedSource)
    {
      throw new IOException(paramBufferedSource.getMessage());
    }
  }
  
  private void remove(Request paramRequest)
    throws IOException
  {
    cache.remove(urlToKey(paramRequest));
  }
  
  private void trackConditionalCacheHit()
  {
    try
    {
      hitCount += 1;
      return;
    }
    catch (Throwable localThrowable)
    {
      throw localThrowable;
    }
  }
  
  private void trackResponse(CacheStrategy paramCacheStrategy)
  {
    try
    {
      requestCount += 1;
      if (networkRequest != null) {
        networkCount += 1;
      } else if (cacheResponse != null) {
        hitCount += 1;
      }
      return;
    }
    catch (Throwable paramCacheStrategy)
    {
      throw paramCacheStrategy;
    }
  }
  
  private void update(Response paramResponse1, Response paramResponse2)
  {
    Entry localEntry = new Entry(paramResponse2);
    paramResponse1 = bodysnapshot;
    try
    {
      paramResponse2 = paramResponse1.edit();
      paramResponse1 = paramResponse2;
      if (paramResponse2 == null) {
        return;
      }
    }
    catch (IOException paramResponse1)
    {
      label43:
      for (;;) {}
    }
    try
    {
      localEntry.writeTo(paramResponse2);
      paramResponse2.commit();
      return;
    }
    catch (IOException paramResponse2)
    {
      break label43;
    }
    paramResponse1 = null;
    abortQuietly(paramResponse1);
    return;
  }
  
  private static String urlToKey(Request paramRequest)
  {
    return Util.md5Hex(paramRequest.urlString());
  }
  
  public void close()
    throws IOException
  {
    cache.close();
  }
  
  public void delete()
    throws IOException
  {
    cache.delete();
  }
  
  public void evictAll()
    throws IOException
  {
    cache.evictAll();
  }
  
  public void flush()
    throws IOException
  {
    cache.flush();
  }
  
  Response get(Request paramRequest)
  {
    Object localObject1 = urlToKey(paramRequest);
    Object localObject2 = cache;
    for (;;)
    {
      try
      {
        localObject1 = ((DiskLruCache)localObject2).get((String)localObject1);
        if (localObject1 == null) {
          return null;
        }
      }
      catch (IOException paramRequest)
      {
        return null;
      }
      try
      {
        localObject2 = new Entry(((DiskLruCache.Snapshot)localObject1).getSource(0));
        localObject1 = ((Entry)localObject2).response(paramRequest, (DiskLruCache.Snapshot)localObject1);
        if (!((Entry)localObject2).matches(paramRequest, (Response)localObject1))
        {
          Util.closeQuietly(((Response)localObject1).body());
          return null;
        }
        return localObject1;
      }
      catch (IOException paramRequest) {}
    }
    Util.closeQuietly((Closeable)localObject1);
    return null;
  }
  
  public File getDirectory()
  {
    return cache.getDirectory();
  }
  
  public int getHitCount()
  {
    try
    {
      int i = hitCount;
      return i;
    }
    catch (Throwable localThrowable)
    {
      throw localThrowable;
    }
  }
  
  public long getMaxSize()
  {
    return cache.getMaxSize();
  }
  
  public int getNetworkCount()
  {
    try
    {
      int i = networkCount;
      return i;
    }
    catch (Throwable localThrowable)
    {
      throw localThrowable;
    }
  }
  
  public int getRequestCount()
  {
    try
    {
      int i = requestCount;
      return i;
    }
    catch (Throwable localThrowable)
    {
      throw localThrowable;
    }
  }
  
  public long getSize()
    throws IOException
  {
    return cache.size();
  }
  
  public int getWriteAbortCount()
  {
    try
    {
      int i = writeAbortCount;
      return i;
    }
    catch (Throwable localThrowable)
    {
      throw localThrowable;
    }
  }
  
  public int getWriteSuccessCount()
  {
    try
    {
      int i = writeSuccessCount;
      return i;
    }
    catch (Throwable localThrowable)
    {
      throw localThrowable;
    }
  }
  
  public void initialize()
    throws IOException
  {
    cache.initialize();
  }
  
  public boolean isClosed()
  {
    return cache.isClosed();
  }
  
  public Iterator urls()
    throws IOException
  {
    new Iterator()
    {
      boolean canRemove;
      final Iterator<DiskLruCache.Snapshot> delegate = cache.snapshots();
      String nextUrl;
      
      public boolean hasNext()
      {
        if (nextUrl != null) {
          return true;
        }
        canRemove = false;
        for (;;)
        {
          DiskLruCache.Snapshot localSnapshot;
          if (delegate.hasNext()) {
            localSnapshot = (DiskLruCache.Snapshot)delegate.next();
          }
          try
          {
            String str = Okio.buffer(localSnapshot.getSource(0)).readUtf8LineStrict();
            nextUrl = str;
            localSnapshot.close();
            return true;
          }
          catch (Throwable localThrowable)
          {
            localSnapshot.close();
            throw localThrowable;
            localSnapshot.close();
            continue;
            return false;
          }
          catch (IOException localIOException)
          {
            for (;;) {}
          }
        }
      }
      
      public String next()
      {
        if (hasNext())
        {
          String str = nextUrl;
          nextUrl = null;
          canRemove = true;
          return str;
        }
        throw new NoSuchElementException();
      }
      
      public void remove()
      {
        if (canRemove)
        {
          delegate.remove();
          return;
        }
        throw new IllegalStateException("remove() before next()");
      }
    };
  }
  
  private final class CacheRequestImpl
    implements CacheRequest
  {
    private Sink body;
    private Sink cacheOut;
    private boolean done;
    private final DiskLruCache.Editor editor;
    
    public CacheRequestImpl(final DiskLruCache.Editor paramEditor)
      throws IOException
    {
      editor = paramEditor;
      cacheOut = paramEditor.newSink(1);
      body = new ForwardingSink(cacheOut)
      {
        public void close()
          throws IOException
        {
          Cache localCache = Cache.this;
          try
          {
            if (done) {
              return;
            }
            Cache.CacheRequestImpl.access$702(Cache.CacheRequestImpl.this, true);
            Cache.access$808(Cache.this);
            super.close();
            paramEditor.commit();
            return;
          }
          catch (Throwable localThrowable)
          {
            throw localThrowable;
          }
        }
      };
    }
    
    public void abort()
    {
      Object localObject = Cache.this;
      try
      {
        if (done) {
          return;
        }
        done = true;
        Cache.access$908(Cache.this);
        Util.closeQuietly(cacheOut);
        localObject = editor;
        return;
      }
      catch (Throwable localThrowable)
      {
        try
        {
          ((DiskLruCache.Editor)localObject).abort();
          return;
        }
        catch (IOException localIOException) {}
        localThrowable = localThrowable;
        throw localThrowable;
      }
    }
    
    public Sink body()
    {
      return body;
    }
  }
  
  private static class CacheResponseBody
    extends ResponseBody
  {
    private final BufferedSource bodySource;
    private final String contentLength;
    private final String contentType;
    private final DiskLruCache.Snapshot snapshot;
    
    public CacheResponseBody(final DiskLruCache.Snapshot paramSnapshot, String paramString1, String paramString2)
    {
      snapshot = paramSnapshot;
      contentType = paramString1;
      contentLength = paramString2;
      bodySource = Okio.buffer(new ForwardingSource(paramSnapshot.getSource(1))
      {
        public void close()
          throws IOException
        {
          paramSnapshot.close();
          super.close();
        }
      });
    }
    
    public long contentLength()
    {
      if (contentLength != null)
      {
        String str = contentLength;
        try
        {
          long l = Long.parseLong(str);
          return l;
        }
        catch (NumberFormatException localNumberFormatException) {}
      }
      return -1L;
    }
    
    public MediaType contentType()
    {
      String str = contentType;
      if (str != null) {
        return MediaType.parse(str);
      }
      return null;
    }
    
    public BufferedSource source()
    {
      return bodySource;
    }
  }
  
  private static final class Entry
  {
    private final int code;
    private final Handshake handshake;
    private final String message;
    private final Protocol protocol;
    private final String requestMethod;
    private final Headers responseHeaders;
    private final String url;
    private final Headers varyHeaders;
    
    public Entry(Response paramResponse)
    {
      url = paramResponse.request().urlString();
      varyHeaders = OkHeaders.varyHeaders(paramResponse);
      requestMethod = paramResponse.request().method();
      protocol = paramResponse.protocol();
      code = paramResponse.code();
      message = paramResponse.message();
      responseHeaders = paramResponse.headers();
      handshake = paramResponse.handshake();
    }
    
    public Entry(Source paramSource)
      throws IOException
    {
      try
      {
        Object localObject1 = Okio.buffer(paramSource);
        url = ((BufferedSource)localObject1).readUtf8LineStrict();
        requestMethod = ((BufferedSource)localObject1).readUtf8LineStrict();
        Object localObject2 = new Headers.Builder();
        int k = Cache.readInt((BufferedSource)localObject1);
        int j = 0;
        int i = 0;
        while (i < k)
        {
          ((Headers.Builder)localObject2).addLenient(((BufferedSource)localObject1).readUtf8LineStrict());
          i += 1;
        }
        varyHeaders = ((Headers.Builder)localObject2).build();
        localObject2 = StatusLine.parse(((BufferedSource)localObject1).readUtf8LineStrict());
        protocol = protocol;
        code = code;
        message = message;
        localObject2 = new Headers.Builder();
        k = Cache.readInt((BufferedSource)localObject1);
        i = j;
        while (i < k)
        {
          ((Headers.Builder)localObject2).addLenient(((BufferedSource)localObject1).readUtf8LineStrict());
          i += 1;
        }
        responseHeaders = ((Headers.Builder)localObject2).build();
        boolean bool = isHttps();
        if (bool)
        {
          localObject2 = ((BufferedSource)localObject1).readUtf8LineStrict();
          i = ((String)localObject2).length();
          if (i <= 0)
          {
            handshake = Handshake.get(((BufferedSource)localObject1).readUtf8LineStrict(), readCertificateList((BufferedSource)localObject1), readCertificateList((BufferedSource)localObject1));
          }
          else
          {
            localObject1 = new StringBuilder();
            ((StringBuilder)localObject1).append("expected \"\" but was \"");
            ((StringBuilder)localObject1).append((String)localObject2);
            ((StringBuilder)localObject1).append("\"");
            throw new IOException(((StringBuilder)localObject1).toString());
          }
        }
        else
        {
          handshake = null;
        }
        paramSource.close();
        return;
      }
      catch (Throwable localThrowable)
      {
        paramSource.close();
        throw localThrowable;
      }
    }
    
    private boolean isHttps()
    {
      return url.startsWith("https://");
    }
    
    private List readCertificateList(BufferedSource paramBufferedSource)
      throws IOException
    {
      int j = Cache.readInt(paramBufferedSource);
      if (j == -1) {
        return Collections.emptyList();
      }
      try
      {
        CertificateFactory localCertificateFactory = CertificateFactory.getInstance("X.509");
        ArrayList localArrayList = new ArrayList(j);
        int i = 0;
        while (i < j)
        {
          String str = paramBufferedSource.readUtf8LineStrict();
          Buffer localBuffer = new Buffer();
          localBuffer.write(ByteString.decodeBase64(str));
          localArrayList.add(localCertificateFactory.generateCertificate(localBuffer.inputStream()));
          i += 1;
        }
        return localArrayList;
      }
      catch (CertificateException paramBufferedSource)
      {
        paramBufferedSource = new IOException(paramBufferedSource.getMessage());
        throw paramBufferedSource;
      }
    }
    
    private void writeCertList(BufferedSink paramBufferedSink, List paramList)
      throws IOException
    {
      try
      {
        int i = paramList.size();
        long l = i;
        paramBufferedSink.writeDecimalLong(l);
        paramBufferedSink.writeByte(10);
        i = 0;
        int j = paramList.size();
        while (i < j)
        {
          Object localObject = paramList.get(i);
          localObject = (Certificate)localObject;
          paramBufferedSink.writeUtf8(ByteString.of(((Certificate)localObject).getEncoded()).base64());
          paramBufferedSink.writeByte(10);
          i += 1;
        }
        return;
      }
      catch (CertificateEncodingException paramBufferedSink)
      {
        paramBufferedSink = new IOException(paramBufferedSink.getMessage());
        throw paramBufferedSink;
      }
    }
    
    public boolean matches(Request paramRequest, Response paramResponse)
    {
      return (url.equals(paramRequest.urlString())) && (requestMethod.equals(paramRequest.method())) && (OkHeaders.varyMatches(paramResponse, varyHeaders, paramRequest));
    }
    
    public Response response(Request paramRequest, DiskLruCache.Snapshot paramSnapshot)
    {
      paramRequest = responseHeaders.get("Content-Type");
      String str = responseHeaders.get("Content-Length");
      Request localRequest = new Request.Builder().url(url).method(requestMethod, null).headers(varyHeaders).build();
      return new Response.Builder().request(localRequest).protocol(protocol).code(code).message(message).headers(responseHeaders).body(new Cache.CacheResponseBody(paramSnapshot, paramRequest, str)).handshake(handshake).build();
    }
    
    public void writeTo(DiskLruCache.Editor paramEditor)
      throws IOException
    {
      int j = 0;
      paramEditor = Okio.buffer(paramEditor.newSink(0));
      paramEditor.writeUtf8(url);
      paramEditor.writeByte(10);
      paramEditor.writeUtf8(requestMethod);
      paramEditor.writeByte(10);
      paramEditor.writeDecimalLong(varyHeaders.size());
      paramEditor.writeByte(10);
      int k = varyHeaders.size();
      int i = 0;
      while (i < k)
      {
        paramEditor.writeUtf8(varyHeaders.name(i));
        paramEditor.writeUtf8(": ");
        paramEditor.writeUtf8(varyHeaders.value(i));
        paramEditor.writeByte(10);
        i += 1;
      }
      paramEditor.writeUtf8(new StatusLine(protocol, code, message).toString());
      paramEditor.writeByte(10);
      paramEditor.writeDecimalLong(responseHeaders.size());
      paramEditor.writeByte(10);
      k = responseHeaders.size();
      i = j;
      while (i < k)
      {
        paramEditor.writeUtf8(responseHeaders.name(i));
        paramEditor.writeUtf8(": ");
        paramEditor.writeUtf8(responseHeaders.value(i));
        paramEditor.writeByte(10);
        i += 1;
      }
      if (isHttps())
      {
        paramEditor.writeByte(10);
        paramEditor.writeUtf8(handshake.cipherSuite());
        paramEditor.writeByte(10);
        writeCertList(paramEditor, handshake.peerCertificates());
        writeCertList(paramEditor, handshake.localCertificates());
      }
      paramEditor.close();
    }
  }
}
