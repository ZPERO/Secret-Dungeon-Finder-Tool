package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.Address;
import com.squareup.okhttp.ConnectionPool;
import com.squareup.okhttp.Route;
import com.squareup.okhttp.internal.Internal;
import com.squareup.okhttp.internal.RouteDatabase;
import com.squareup.okhttp.internal.Util;
import com.squareup.okhttp.internal.io.RealConnection;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.security.cert.CertificateException;
import java.util.List;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLPeerUnverifiedException;
import okio.Sink;

public final class StreamAllocation
{
  public final Address address;
  private boolean canceled;
  private RealConnection connection;
  private final ConnectionPool connectionPool;
  private boolean released;
  private RouteSelector routeSelector;
  private HttpStream stream;
  
  public StreamAllocation(ConnectionPool paramConnectionPool, Address paramAddress)
  {
    connectionPool = paramConnectionPool;
    address = paramAddress;
  }
  
  private void connectionFailed(IOException paramIOException)
  {
    ConnectionPool localConnectionPool = connectionPool;
    try
    {
      if (routeSelector != null) {
        if (connection.streamCount == 0)
        {
          Route localRoute = connection.getRoute();
          routeSelector.connectFailed(localRoute, paramIOException);
        }
        else
        {
          routeSelector = null;
        }
      }
      connectionFailed();
      return;
    }
    catch (Throwable paramIOException)
    {
      throw paramIOException;
    }
  }
  
  private void deallocate(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    ConnectionPool localConnectionPool = connectionPool;
    if (paramBoolean3) {}
    try
    {
      stream = null;
      if (paramBoolean2) {
        released = true;
      }
      if (connection == null) {
        break label190;
      }
      if (paramBoolean1) {
        connection.noNewStreams = true;
      }
      if ((stream != null) || ((!released) && (!connection.noNewStreams))) {
        break label190;
      }
      release(connection);
      if (connection.streamCount > 0) {
        routeSelector = null;
      }
      if (!connection.allocations.isEmpty()) {
        break label184;
      }
      connection.idleAtNanos = System.nanoTime();
      if (!Internal.instance.connectionBecameIdle(connectionPool, connection)) {
        break label184;
      }
      localRealConnection = connection;
    }
    catch (Throwable localThrowable)
    {
      for (;;)
      {
        RealConnection localRealConnection;
        continue;
        Object localObject = null;
        continue;
        localObject = null;
      }
    }
    connection = null;
    if (localRealConnection != null)
    {
      Util.closeQuietly(localRealConnection.getSocket());
      return;
      throw localRealConnection;
    }
  }
  
  private RealConnection findConnection(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
    throws IOException, RouteException
  {
    ConnectionPool localConnectionPool = connectionPool;
    try
    {
      if (!released)
      {
        if (stream == null)
        {
          if (!canceled)
          {
            RealConnection localRealConnection = connection;
            if ((localRealConnection != null) && (!noNewStreams)) {
              return localRealConnection;
            }
            localRealConnection = Internal.instance.get(connectionPool, address, this);
            if (localRealConnection != null)
            {
              connection = localRealConnection;
              return localRealConnection;
            }
            if (routeSelector == null) {
              routeSelector = new RouteSelector(address, routeDatabase());
            }
            localRealConnection = new RealConnection(routeSelector.next());
            acquire(localRealConnection);
            localConnectionPool = connectionPool;
            try
            {
              Internal.instance.put(connectionPool, localRealConnection);
              connection = localRealConnection;
              if (!canceled)
              {
                localRealConnection.connect(paramInt1, paramInt2, paramInt3, address.getConnectionSpecs(), paramBoolean);
                routeDatabase().connected(localRealConnection.getRoute());
                return localRealConnection;
              }
              throw new IOException("Canceled");
            }
            catch (Throwable localThrowable1)
            {
              throw localThrowable1;
            }
          }
          throw new IOException("Canceled");
        }
        throw new IllegalStateException("stream != null");
      }
      throw new IllegalStateException("released");
    }
    catch (Throwable localThrowable2)
    {
      throw localThrowable2;
    }
  }
  
  private RealConnection findHealthyConnection(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean1, boolean paramBoolean2)
    throws IOException, RouteException
  {
    for (;;)
    {
      RealConnection localRealConnection = findConnection(paramInt1, paramInt2, paramInt3, paramBoolean1);
      ConnectionPool localConnectionPool = connectionPool;
      try
      {
        if (streamCount == 0) {
          return localRealConnection;
        }
        if (localRealConnection.isHealthy(paramBoolean2)) {
          return localRealConnection;
        }
        connectionFailed();
      }
      catch (Throwable localThrowable)
      {
        throw localThrowable;
      }
    }
  }
  
  private boolean isRecoverable(RouteException paramRouteException)
  {
    paramRouteException = paramRouteException.getLastConnectException();
    if ((paramRouteException instanceof ProtocolException)) {
      return false;
    }
    if ((paramRouteException instanceof InterruptedIOException)) {
      return paramRouteException instanceof SocketTimeoutException;
    }
    if (((paramRouteException instanceof SSLHandshakeException)) && ((paramRouteException.getCause() instanceof CertificateException))) {
      return false;
    }
    return !(paramRouteException instanceof SSLPeerUnverifiedException);
  }
  
  private boolean isRecoverable(IOException paramIOException)
  {
    if ((paramIOException instanceof ProtocolException)) {
      return false;
    }
    return !(paramIOException instanceof InterruptedIOException);
  }
  
  private void release(RealConnection paramRealConnection)
  {
    int j = allocations.size();
    int i = 0;
    while (i < j)
    {
      if (((Reference)allocations.get(i)).get() == this)
      {
        allocations.remove(i);
        return;
      }
      i += 1;
    }
    paramRealConnection = new IllegalStateException();
    throw paramRealConnection;
  }
  
  private RouteDatabase routeDatabase()
  {
    return Internal.instance.routeDatabase(connectionPool);
  }
  
  public void acquire(RealConnection paramRealConnection)
  {
    allocations.add(new WeakReference(this));
  }
  
  public void cancel()
  {
    ConnectionPool localConnectionPool = connectionPool;
    try
    {
      canceled = true;
      HttpStream localHttpStream = stream;
      RealConnection localRealConnection = connection;
      if (localHttpStream != null)
      {
        localHttpStream.cancel();
        return;
      }
      if (localRealConnection != null)
      {
        localRealConnection.cancel();
        return;
      }
    }
    catch (Throwable localThrowable)
    {
      throw localThrowable;
    }
  }
  
  public RealConnection connection()
  {
    try
    {
      RealConnection localRealConnection = connection;
      return localRealConnection;
    }
    catch (Throwable localThrowable)
    {
      throw localThrowable;
    }
  }
  
  public void connectionFailed()
  {
    deallocate(true, false, true);
  }
  
  public HttpStream newStream(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean1, boolean paramBoolean2)
    throws RouteException, IOException
  {
    throw new Runtime("d2j fail translate: java.lang.RuntimeException: fail exe a8 = a7\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.exec(BaseAnalyze.java:92)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.exec(BaseAnalyze.java:1)\n\tat com.googlecode.dex2jar.ir.ts.Cfg.dfs(Cfg.java:255)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.analyze0(BaseAnalyze.java:75)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.analyze(BaseAnalyze.java:69)\n\tat com.googlecode.dex2jar.ir.ts.UnSSATransformer.transform(UnSSATransformer.java:274)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:163)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\nCaused by: java.lang.NullPointerException\n");
  }
  
  public void noNewStreams()
  {
    deallocate(true, false, false);
  }
  
  public boolean recover(RouteException paramRouteException)
  {
    if (connection != null) {
      connectionFailed(paramRouteException.getLastConnectException());
    }
    RouteSelector localRouteSelector = routeSelector;
    return ((localRouteSelector == null) || (localRouteSelector.hasNext())) && (isRecoverable(paramRouteException));
  }
  
  public boolean recover(IOException paramIOException, Sink paramSink)
  {
    RealConnection localRealConnection = connection;
    int i;
    if (localRealConnection != null)
    {
      i = streamCount;
      connectionFailed(paramIOException);
      if (i == 1) {
        return false;
      }
    }
    if ((paramSink != null) && (!(paramSink instanceof RetryableSink))) {
      i = 0;
    } else {
      i = 1;
    }
    paramSink = routeSelector;
    if (((paramSink == null) || (paramSink.hasNext())) && (isRecoverable(paramIOException))) {
      return i != 0;
    }
    return false;
  }
  
  public void release()
  {
    deallocate(false, true, false);
  }
  
  public HttpStream stream()
  {
    ConnectionPool localConnectionPool = connectionPool;
    try
    {
      HttpStream localHttpStream = stream;
      return localHttpStream;
    }
    catch (Throwable localThrowable)
    {
      throw localThrowable;
    }
  }
  
  public void streamFinished(HttpStream paramHttpStream)
  {
    ConnectionPool localConnectionPool = connectionPool;
    if (paramHttpStream != null) {}
    try
    {
      if (paramHttpStream == stream)
      {
        deallocate(false, false, true);
        return;
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("expected ");
      localStringBuilder.append(stream);
      localStringBuilder.append(" but was ");
      localStringBuilder.append(paramHttpStream);
      throw new IllegalStateException(localStringBuilder.toString());
    }
    catch (Throwable paramHttpStream)
    {
      throw paramHttpStream;
    }
  }
  
  public String toString()
  {
    return address.toString();
  }
}
