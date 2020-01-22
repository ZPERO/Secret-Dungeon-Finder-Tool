package com.squareup.okhttp;

import com.squareup.okhttp.internal.Util;
import com.squareup.okhttp.internal.http.HttpEngine;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public final class Dispatcher
{
  private final Deque<Call> executedCalls = new ArrayDeque();
  private ExecutorService executorService;
  private int maxRequests = 64;
  private int maxRequestsPerHost = 5;
  private final Deque<Call.AsyncCall> readyCalls = new ArrayDeque();
  private final Deque<Call.AsyncCall> runningCalls = new ArrayDeque();
  
  public Dispatcher() {}
  
  public Dispatcher(ExecutorService paramExecutorService)
  {
    executorService = paramExecutorService;
  }
  
  private void promoteCalls()
  {
    if (runningCalls.size() >= maxRequests) {
      return;
    }
    if (readyCalls.isEmpty()) {
      return;
    }
    Iterator localIterator = readyCalls.iterator();
    do
    {
      if (!localIterator.hasNext()) {
        break;
      }
      Call.AsyncCall localAsyncCall = (Call.AsyncCall)localIterator.next();
      if (runningCallsForHost(localAsyncCall) < maxRequestsPerHost)
      {
        localIterator.remove();
        runningCalls.add(localAsyncCall);
        getExecutorService().execute(localAsyncCall);
      }
    } while (runningCalls.size() < maxRequests);
  }
  
  private int runningCallsForHost(Call.AsyncCall paramAsyncCall)
  {
    Iterator localIterator = runningCalls.iterator();
    int i = 0;
    while (localIterator.hasNext()) {
      if (((Call.AsyncCall)localIterator.next()).host().equals(paramAsyncCall.host())) {
        i += 1;
      }
    }
    return i;
  }
  
  public void cancel(Object paramObject)
  {
    try
    {
      Iterator localIterator = readyCalls.iterator();
      Object localObject;
      while (localIterator.hasNext())
      {
        localObject = (Call.AsyncCall)localIterator.next();
        if (Util.equal(paramObject, ((Call.AsyncCall)localObject).tag())) {
          ((Call.AsyncCall)localObject).cancel();
        }
      }
      localIterator = runningCalls.iterator();
      while (localIterator.hasNext())
      {
        localObject = (Call.AsyncCall)localIterator.next();
        if (Util.equal(paramObject, ((Call.AsyncCall)localObject).tag()))
        {
          getcanceled = true;
          localObject = getengine;
          if (localObject != null) {
            ((HttpEngine)localObject).cancel();
          }
        }
      }
      localIterator = executedCalls.iterator();
      while (localIterator.hasNext())
      {
        localObject = (Call)localIterator.next();
        if (Util.equal(paramObject, ((Call)localObject).tag())) {
          ((Call)localObject).cancel();
        }
      }
      return;
    }
    catch (Throwable paramObject)
    {
      throw paramObject;
    }
  }
  
  void enqueue(Call.AsyncCall paramAsyncCall)
  {
    try
    {
      if ((runningCalls.size() < maxRequests) && (runningCallsForHost(paramAsyncCall) < maxRequestsPerHost))
      {
        runningCalls.add(paramAsyncCall);
        getExecutorService().execute(paramAsyncCall);
      }
      else
      {
        readyCalls.add(paramAsyncCall);
      }
      return;
    }
    catch (Throwable paramAsyncCall)
    {
      throw paramAsyncCall;
    }
  }
  
  void executed(Call paramCall)
  {
    try
    {
      executedCalls.add(paramCall);
      return;
    }
    catch (Throwable paramCall)
    {
      throw paramCall;
    }
  }
  
  void finished(Call.AsyncCall paramAsyncCall)
  {
    try
    {
      if (runningCalls.remove(paramAsyncCall))
      {
        promoteCalls();
        return;
      }
      throw new AssertionError("AsyncCall wasn't running!");
    }
    catch (Throwable paramAsyncCall)
    {
      throw paramAsyncCall;
    }
  }
  
  void finished(Call paramCall)
  {
    try
    {
      boolean bool = executedCalls.remove(paramCall);
      if (bool) {
        return;
      }
      throw new AssertionError("Call wasn't in-flight!");
    }
    catch (Throwable paramCall)
    {
      throw paramCall;
    }
  }
  
  public ExecutorService getExecutorService()
  {
    try
    {
      if (executorService == null) {
        executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue(), Util.threadFactory("OkHttp Dispatcher", false));
      }
      ExecutorService localExecutorService = executorService;
      return localExecutorService;
    }
    catch (Throwable localThrowable)
    {
      throw localThrowable;
    }
  }
  
  public int getMaxRequests()
  {
    try
    {
      int i = maxRequests;
      return i;
    }
    catch (Throwable localThrowable)
    {
      throw localThrowable;
    }
  }
  
  public int getMaxRequestsPerHost()
  {
    try
    {
      int i = maxRequestsPerHost;
      return i;
    }
    catch (Throwable localThrowable)
    {
      throw localThrowable;
    }
  }
  
  public int getQueuedCallCount()
  {
    try
    {
      int i = readyCalls.size();
      return i;
    }
    catch (Throwable localThrowable)
    {
      throw localThrowable;
    }
  }
  
  public int getRunningCallCount()
  {
    try
    {
      int i = runningCalls.size();
      return i;
    }
    catch (Throwable localThrowable)
    {
      throw localThrowable;
    }
  }
  
  public void setMaxRequests(int paramInt)
  {
    StringBuilder localStringBuilder;
    if (paramInt >= 1)
    {
      try
      {
        maxRequests = paramInt;
        promoteCalls();
        return;
      }
      catch (Throwable localThrowable) {}
    }
    else
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("max < 1: ");
      localStringBuilder.append(paramInt);
      throw new IllegalArgumentException(localStringBuilder.toString());
    }
    throw localStringBuilder;
  }
  
  public void setMaxRequestsPerHost(int paramInt)
  {
    StringBuilder localStringBuilder;
    if (paramInt >= 1)
    {
      try
      {
        maxRequestsPerHost = paramInt;
        promoteCalls();
        return;
      }
      catch (Throwable localThrowable) {}
    }
    else
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("max < 1: ");
      localStringBuilder.append(paramInt);
      throw new IllegalArgumentException(localStringBuilder.toString());
    }
    throw localStringBuilder;
  }
}
