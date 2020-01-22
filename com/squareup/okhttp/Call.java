package com.squareup.okhttp;

import com.squareup.okhttp.internal.Internal;
import com.squareup.okhttp.internal.NamedRunnable;
import com.squareup.okhttp.internal.http.HttpEngine;
import com.squareup.okhttp.internal.http.RequestException;
import com.squareup.okhttp.internal.http.RouteException;
import com.squareup.okhttp.internal.http.StreamAllocation;
import java.io.IOException;
import java.net.ProtocolException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Call
{
  volatile boolean canceled;
  private final OkHttpClient client;
  HttpEngine engine;
  private boolean executed;
  Request originalRequest;
  
  protected Call(OkHttpClient paramOkHttpClient, Request paramRequest)
  {
    client = paramOkHttpClient.copyWithDefaults();
    originalRequest = paramRequest;
  }
  
  private Response getResponseWithInterceptorChain(boolean paramBoolean)
    throws IOException
  {
    return new ApplicationInterceptorChain(0, originalRequest, paramBoolean).proceed(originalRequest);
  }
  
  private String toLoggableString()
  {
    String str;
    if (canceled) {
      str = "canceled call";
    } else {
      str = "call";
    }
    HttpUrl localHttpUrl = originalRequest.httpUrl().resolve("/...");
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(str);
    localStringBuilder.append(" to ");
    localStringBuilder.append(localHttpUrl);
    return localStringBuilder.toString();
  }
  
  public void cancel()
  {
    canceled = true;
    HttpEngine localHttpEngine = engine;
    if (localHttpEngine != null) {
      localHttpEngine.cancel();
    }
  }
  
  public void enqueue(Callback paramCallback)
  {
    enqueue(paramCallback, false);
  }
  
  void enqueue(Callback paramCallback, boolean paramBoolean)
  {
    try
    {
      if (!executed)
      {
        executed = true;
        client.getDispatcher().enqueue(new AsyncCall(paramCallback, paramBoolean, null));
        return;
      }
      throw new IllegalStateException("Already Executed");
    }
    catch (Throwable paramCallback)
    {
      throw paramCallback;
    }
  }
  
  public Response execute()
    throws IOException
  {
    try
    {
      if (!executed)
      {
        executed = true;
        try
        {
          client.getDispatcher().executed(this);
          Response localResponse = getResponseWithInterceptorChain(false);
          if (localResponse != null)
          {
            client.getDispatcher().finished(this);
            return localResponse;
          }
          throw new IOException("Canceled");
        }
        catch (Throwable localThrowable1)
        {
          client.getDispatcher().finished(this);
          throw localThrowable1;
        }
      }
      throw new IllegalStateException("Already Executed");
    }
    catch (Throwable localThrowable2)
    {
      throw localThrowable2;
    }
  }
  
  Response getResponse(Request paramRequest, boolean paramBoolean)
    throws IOException
  {
    Object localObject2 = paramRequest.body();
    Object localObject1 = paramRequest;
    if (localObject2 != null)
    {
      paramRequest = paramRequest.newBuilder();
      localObject1 = ((RequestBody)localObject2).contentType();
      if (localObject1 != null) {
        paramRequest.header("Content-Type", ((MediaType)localObject1).toString());
      }
      long l = ((RequestBody)localObject2).contentLength();
      if (l != -1L)
      {
        paramRequest.header("Content-Length", Long.toString(l));
        paramRequest.removeHeader("Transfer-Encoding");
      }
      else
      {
        paramRequest.header("Transfer-Encoding", "chunked");
        paramRequest.removeHeader("Content-Length");
      }
      localObject1 = paramRequest.build();
    }
    engine = new HttpEngine(client, (Request)localObject1, false, false, paramBoolean, null, null, null);
    int i = 0;
    while (!canceled)
    {
      int j = 1;
      try
      {
        paramRequest = engine;
      }
      catch (Throwable paramRequest)
      {
        try
        {
          paramRequest.sendRequest();
          paramRequest = engine;
          paramRequest.readResponse();
          localObject2 = engine.getResponse();
          Request localRequest = engine.followUpRequest();
          if (localRequest == null)
          {
            if (paramBoolean) {
              break label420;
            }
            engine.releaseStreamAllocation();
            return localObject2;
          }
          localObject1 = engine.close();
          i += 1;
          if (i <= 20)
          {
            paramRequest = (Request)localObject1;
            if (!engine.sameConnection(localRequest.httpUrl()))
            {
              ((StreamAllocation)localObject1).release();
              paramRequest = null;
            }
            engine = new HttpEngine(client, localRequest, false, false, paramBoolean, paramRequest, null, (Response)localObject2);
            continue;
          }
          ((StreamAllocation)localObject1).release();
          paramRequest = new StringBuilder();
          paramRequest.append("Too many follow-up requests: ");
          paramRequest.append(i);
          throw new ProtocolException(paramRequest.toString());
          paramRequest = paramRequest;
          i = j;
        }
        catch (IOException paramRequest)
        {
          localObject1 = engine.recover(paramRequest, null);
          if (localObject1 != null) {}
          try
          {
            engine = ((HttpEngine)localObject1);
          }
          catch (Throwable paramRequest)
          {
            i = 0;
            break label382;
          }
          throw paramRequest;
        }
        catch (RouteException paramRequest)
        {
          localObject1 = engine.recover(paramRequest);
          if (localObject1 != null)
          {
            engine = ((HttpEngine)localObject1);
            continue;
          }
          throw paramRequest.getLastConnectException();
        }
        catch (RequestException paramRequest)
        {
          throw paramRequest.getCause();
        }
        label382:
        if (i != 0) {
          engine.close().release();
        }
        throw paramRequest;
      }
    }
    engine.releaseStreamAllocation();
    paramRequest = new IOException("Canceled");
    throw paramRequest;
    label420:
    return localObject2;
  }
  
  public boolean isCanceled()
  {
    return canceled;
  }
  
  public boolean isExecuted()
  {
    try
    {
      boolean bool = executed;
      return bool;
    }
    catch (Throwable localThrowable)
    {
      throw localThrowable;
    }
  }
  
  Object tag()
  {
    return originalRequest.tag();
  }
  
  class ApplicationInterceptorChain
    implements Interceptor.Chain
  {
    private final boolean forWebSocket;
    private final int index;
    private final Request request;
    
    ApplicationInterceptorChain(int paramInt, Request paramRequest, boolean paramBoolean)
    {
      index = paramInt;
      request = paramRequest;
      forWebSocket = paramBoolean;
    }
    
    public Connection connection()
    {
      return null;
    }
    
    public Response proceed(Request paramRequest)
      throws IOException
    {
      if (index < client.interceptors().size())
      {
        Object localObject = new ApplicationInterceptorChain(Call.this, index + 1, paramRequest, forWebSocket);
        paramRequest = (Interceptor)client.interceptors().get(index);
        localObject = paramRequest.intercept((Interceptor.Chain)localObject);
        if (localObject != null) {
          return localObject;
        }
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("application interceptor ");
        ((StringBuilder)localObject).append(paramRequest);
        ((StringBuilder)localObject).append(" returned null");
        throw new NullPointerException(((StringBuilder)localObject).toString());
      }
      return getResponse(paramRequest, forWebSocket);
    }
    
    public Request request()
    {
      return request;
    }
  }
  
  final class AsyncCall
    extends NamedRunnable
  {
    private final boolean forWebSocket;
    private final Callback responseCallback;
    
    private AsyncCall(Callback paramCallback, boolean paramBoolean)
    {
      super(new Object[] { originalRequest.urlString() });
      responseCallback = paramCallback;
      forWebSocket = paramBoolean;
    }
    
    void cancel()
    {
      Call.this.cancel();
    }
    
    protected void execute()
    {
      int i = 1;
      Object localObject2;
      for (;;)
      {
        Object localObject1;
        boolean bool;
        try
        {
          localObject1 = Call.this;
          bool = forWebSocket;
        }
        catch (Throwable localThrowable) {}
        try
        {
          localObject1 = ((Call)localObject1).getResponseWithInterceptorChain(bool);
          bool = canceled;
          if (bool)
          {
            localObject1 = responseCallback;
            localObject2 = originalRequest;
          }
        }
        catch (IOException localIOException2)
        {
          i = 0;
          if (i == 0) {
            break label164;
          }
          localObject2 = Internal.logger;
          Level localLevel = Level.INFO;
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("Callback failure for ");
          localStringBuilder.append(Call.this.toLoggableString());
          ((Logger)localObject2).log(localLevel, localStringBuilder.toString(), localIOException2);
          continue;
          localObject2 = engine;
          if (localObject2 != null) {
            break label190;
          }
          localObject2 = originalRequest;
          break label202;
          localObject2 = engine.getRequest();
          responseCallback.onFailure((Request)localObject2, localIOException2);
          continue;
        }
        try
        {
          ((Callback)localObject1).onFailure((Request)localObject2, new IOException("Canceled"));
          continue;
          localObject2 = responseCallback;
          ((Callback)localObject2).onResponse((Response)localObject1);
          client.getDispatcher().finished(this);
          return;
        }
        catch (IOException localIOException1) {}
      }
      label164:
      label190:
      label202:
      client.getDispatcher().finished(this);
      throw localIOException2;
    }
    
    Call get()
    {
      return Call.this;
    }
    
    String host()
    {
      return originalRequest.httpUrl().host();
    }
    
    Request request()
    {
      return originalRequest;
    }
    
    Object tag()
    {
      return originalRequest.tag();
    }
  }
}
