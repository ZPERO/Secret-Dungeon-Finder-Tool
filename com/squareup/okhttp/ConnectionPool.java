package com.squareup.okhttp;

import com.squareup.okhttp.internal.Internal;
import com.squareup.okhttp.internal.RouteDatabase;
import com.squareup.okhttp.internal.Util;
import com.squareup.okhttp.internal.http.StreamAllocation;
import com.squareup.okhttp.internal.io.RealConnection;
import java.lang.ref.Reference;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public final class ConnectionPool
{
  private static final long DEFAULT_KEEP_ALIVE_DURATION_MS = 300000L;
  private static final ConnectionPool systemDefault;
  private Runnable cleanupRunnable = new Runnable()
  {
    public void run()
    {
      for (;;)
      {
        long l1 = cleanup(System.nanoTime());
        if (l1 == -1L) {
          return;
        }
        long l2;
        ConnectionPool localConnectionPool1;
        ConnectionPool localConnectionPool2;
        int i;
        if (l1 > 0L)
        {
          l2 = l1 / 1000000L;
          localConnectionPool1 = ConnectionPool.this;
          localConnectionPool2 = ConnectionPool.this;
          i = (int)(l1 - 1000000L * l2);
        }
        try
        {
          try
          {
            localConnectionPool2.wait(l2, i);
          }
          catch (Throwable localThrowable)
          {
            break;
          }
        }
        catch (InterruptedException localInterruptedException)
        {
          for (;;) {}
        }
      }
      throw localThrowable;
    }
  };
  private final Deque<RealConnection> connections = new ArrayDeque();
  private final Executor executor = new ThreadPoolExecutor(0, 1, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue(), Util.threadFactory("OkHttp ConnectionPool", true));
  private final long keepAliveDurationNs;
  private final int maxIdleConnections;
  final RouteDatabase routeDatabase = new RouteDatabase();
  
  static
  {
    String str1 = System.getProperty("http.keepAlive");
    String str2 = System.getProperty("http.keepAliveDuration");
    String str3 = System.getProperty("http.maxConnections");
    long l;
    if (str2 != null) {
      l = Long.parseLong(str2);
    } else {
      l = 300000L;
    }
    if ((str1 != null) && (!Boolean.parseBoolean(str1)))
    {
      systemDefault = new ConnectionPool(0, l);
      return;
    }
    if (str3 != null)
    {
      systemDefault = new ConnectionPool(Integer.parseInt(str3), l);
      return;
    }
    systemDefault = new ConnectionPool(5, l);
  }
  
  public ConnectionPool(int paramInt, long paramLong)
  {
    this(paramInt, paramLong, TimeUnit.MILLISECONDS);
  }
  
  public ConnectionPool(int paramInt, long paramLong, TimeUnit paramTimeUnit)
  {
    maxIdleConnections = paramInt;
    keepAliveDurationNs = paramTimeUnit.toNanos(paramLong);
    if (paramLong > 0L) {
      return;
    }
    paramTimeUnit = new StringBuilder();
    paramTimeUnit.append("keepAliveDuration <= 0: ");
    paramTimeUnit.append(paramLong);
    throw new IllegalArgumentException(paramTimeUnit.toString());
  }
  
  public static ConnectionPool getDefault()
  {
    return systemDefault;
  }
  
  private int pruneAndGetAllocationCount(RealConnection paramRealConnection, long paramLong)
  {
    List localList = allocations;
    int i = 0;
    while (i < localList.size()) {
      if (((Reference)localList.get(i)).get() != null)
      {
        i += 1;
      }
      else
      {
        Logger localLogger = Internal.logger;
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("A connection to ");
        localStringBuilder.append(paramRealConnection.getRoute().getAddress().url());
        localStringBuilder.append(" was leaked. Did you forget to close a response body?");
        localLogger.warning(localStringBuilder.toString());
        localList.remove(i);
        noNewStreams = true;
        if (localList.isEmpty())
        {
          idleAtNanos = (paramLong - keepAliveDurationNs);
          return 0;
        }
      }
    }
    return localList.size();
  }
  
  long cleanup(long paramLong)
  {
    try
    {
      Iterator localIterator = connections.iterator();
      int i = 0;
      Object localObject = null;
      long l1 = Long.MIN_VALUE;
      int j = 0;
      while (localIterator.hasNext())
      {
        RealConnection localRealConnection = (RealConnection)localIterator.next();
        if (pruneAndGetAllocationCount(localRealConnection, paramLong) > 0)
        {
          j += 1;
        }
        else
        {
          int k = i + 1;
          long l2 = paramLong - idleAtNanos;
          i = k;
          if (l2 > l1)
          {
            localObject = localRealConnection;
            l1 = l2;
            i = k;
          }
        }
      }
      if ((l1 < keepAliveDurationNs) && (i <= maxIdleConnections))
      {
        if (i > 0)
        {
          paramLong = keepAliveDurationNs;
          return paramLong - l1;
        }
        if (j > 0)
        {
          paramLong = keepAliveDurationNs;
          return paramLong;
        }
        return -1L;
      }
      connections.remove(localObject);
      Util.closeQuietly(localObject.getSocket());
      return 0L;
    }
    catch (Throwable localThrowable)
    {
      throw localThrowable;
    }
  }
  
  boolean connectionBecameIdle(RealConnection paramRealConnection)
  {
    if ((!noNewStreams) && (maxIdleConnections != 0))
    {
      notifyAll();
      return false;
    }
    connections.remove(paramRealConnection);
    return true;
  }
  
  public void evictAll()
  {
    Object localObject = new ArrayList();
    try
    {
      Iterator localIterator = connections.iterator();
      while (localIterator.hasNext())
      {
        RealConnection localRealConnection = (RealConnection)localIterator.next();
        if (allocations.isEmpty())
        {
          noNewStreams = true;
          ((List)localObject).add(localRealConnection);
          localIterator.remove();
        }
      }
      localObject = ((List)localObject).iterator();
      while (((Iterator)localObject).hasNext()) {
        Util.closeQuietly(((RealConnection)((Iterator)localObject).next()).getSocket());
      }
      return;
    }
    catch (Throwable localThrowable)
    {
      throw localThrowable;
    }
  }
  
  RealConnection get(Address paramAddress, StreamAllocation paramStreamAllocation)
  {
    Iterator localIterator = connections.iterator();
    while (localIterator.hasNext())
    {
      RealConnection localRealConnection = (RealConnection)localIterator.next();
      if ((allocations.size() < localRealConnection.allocationLimit()) && (paramAddress.equals(getRouteaddress)) && (!noNewStreams))
      {
        paramStreamAllocation.acquire(localRealConnection);
        return localRealConnection;
      }
    }
    return null;
  }
  
  public int getConnectionCount()
  {
    try
    {
      int i = connections.size();
      return i;
    }
    catch (Throwable localThrowable)
    {
      throw localThrowable;
    }
  }
  
  public int getHttpConnectionCount()
  {
    try
    {
      int i = connections.size();
      int j = getMultiplexedConnectionCount();
      return i - j;
    }
    catch (Throwable localThrowable)
    {
      throw localThrowable;
    }
  }
  
  public int getIdleConnectionCount()
  {
    int i = 0;
    try
    {
      Iterator localIterator = connections.iterator();
      while (localIterator.hasNext())
      {
        boolean bool = nextallocations.isEmpty();
        if (bool) {
          i += 1;
        }
      }
      return i;
    }
    catch (Throwable localThrowable)
    {
      throw localThrowable;
    }
  }
  
  public int getMultiplexedConnectionCount()
  {
    int i = 0;
    try
    {
      Iterator localIterator = connections.iterator();
      while (localIterator.hasNext())
      {
        boolean bool = ((RealConnection)localIterator.next()).isMultiplexed();
        if (bool) {
          i += 1;
        }
      }
      return i;
    }
    catch (Throwable localThrowable)
    {
      throw localThrowable;
    }
  }
  
  public int getSpdyConnectionCount()
  {
    try
    {
      int i = getMultiplexedConnectionCount();
      return i;
    }
    catch (Throwable localThrowable)
    {
      throw localThrowable;
    }
  }
  
  void put(RealConnection paramRealConnection)
  {
    if (connections.isEmpty()) {
      executor.execute(cleanupRunnable);
    }
    connections.add(paramRealConnection);
  }
  
  void setCleanupRunnableForTest(Runnable paramRunnable)
  {
    cleanupRunnable = paramRunnable;
  }
}
