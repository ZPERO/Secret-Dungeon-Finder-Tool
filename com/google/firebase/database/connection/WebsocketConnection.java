package com.google.firebase.database.connection;

import com.google.firebase.database.connection.util.StringListReader;
import com.google.firebase.database.logging.LogWrapper;
import com.google.firebase.database.tubesock.WebSocket;
import com.google.firebase.database.tubesock.WebSocketEventHandler;
import com.google.firebase.database.tubesock.WebSocketException;
import com.google.firebase.database.tubesock.WebSocketMessage;
import com.google.firebase.database.util.JsonMapper;
import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

class WebsocketConnection
{
  private static final long CONNECT_TIMEOUT_MS = 30000L;
  private static final long KEEP_ALIVE_TIMEOUT_MS = 45000L;
  private static final int MAX_FRAME_SIZE = 16384;
  private static long connectionId;
  private WSClient conn;
  private ScheduledFuture<?> connectTimeout;
  private final ConnectionContext connectionContext;
  private Delegate delegate;
  private boolean everConnected = false;
  private final ScheduledExecutorService executorService;
  private StringListReader frameReader;
  private boolean isClosed = false;
  private ScheduledFuture<?> keepAlive;
  private final LogWrapper logger;
  private long totalFrames = 0L;
  
  public WebsocketConnection(ConnectionContext paramConnectionContext, HostInfo paramHostInfo, String paramString1, Delegate paramDelegate, String paramString2)
  {
    connectionContext = paramConnectionContext;
    executorService = paramConnectionContext.getExecutorService();
    delegate = paramDelegate;
    long l = connectionId;
    connectionId = 1L + l;
    paramConnectionContext = paramConnectionContext.getLogger();
    paramDelegate = new StringBuilder();
    paramDelegate.append("ws_");
    paramDelegate.append(l);
    logger = new LogWrapper(paramConnectionContext, "WebSocket", paramDelegate.toString());
    conn = createConnection(paramHostInfo, paramString1, paramString2);
  }
  
  private void appendFrame(String paramString)
  {
    frameReader.addString(paramString);
    totalFrames -= 1L;
    if (totalFrames == 0L)
    {
      paramString = frameReader;
      try
      {
        paramString.freeze();
        paramString = frameReader;
        paramString = JsonMapper.parseJson(paramString.toString());
        frameReader = null;
        localObject1 = logger;
        boolean bool = ((LogWrapper)localObject1).logsDebug();
        if (bool)
        {
          localObject1 = logger;
          localObject2 = new StringBuilder();
          ((StringBuilder)localObject2).append("handleIncomingFrame complete frame: ");
          ((StringBuilder)localObject2).append(paramString);
          localObject2 = ((StringBuilder)localObject2).toString();
          ((LogWrapper)localObject1).debug((String)localObject2, new Object[0]);
        }
        localObject1 = delegate;
        ((Delegate)localObject1).onMessage(paramString);
        return;
      }
      catch (ClassCastException paramString)
      {
        localObject1 = logger;
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append("Error parsing frame (cast error): ");
        ((StringBuilder)localObject2).append(frameReader.toString());
        ((LogWrapper)localObject1).error(((StringBuilder)localObject2).toString(), paramString);
        close();
        shutdown();
        return;
      }
      catch (IOException paramString)
      {
        Object localObject1 = logger;
        Object localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append("Error parsing frame: ");
        ((StringBuilder)localObject2).append(frameReader.toString());
        ((LogWrapper)localObject1).error(((StringBuilder)localObject2).toString(), paramString);
        close();
        shutdown();
      }
    }
  }
  
  private void closeIfNeverConnected()
  {
    if ((!everConnected) && (!isClosed))
    {
      if (logger.logsDebug()) {
        logger.debug("timed out on connect", new Object[0]);
      }
      conn.close();
    }
  }
  
  private WSClient createConnection(HostInfo paramHostInfo, String paramString1, String paramString2)
  {
    if (paramString1 == null) {
      paramString1 = paramHostInfo.getHost();
    }
    paramHostInfo = HostInfo.getConnectionUrl(paramString1, paramHostInfo.isSecure(), paramHostInfo.getNamespace(), paramString2);
    paramString1 = new HashMap();
    paramString1.put("User-Agent", connectionContext.getUserAgent());
    return new WSClientTubesock(new WebSocket(connectionContext, paramHostInfo, null, paramString1), null);
  }
  
  private Runnable del()
  {
    new Runnable()
    {
      public void run()
      {
        if (conn != null)
        {
          conn.send("0");
          WebsocketConnection.this.resetKeepAlive();
        }
      }
    };
  }
  
  private String extractFrameCount(String paramString)
  {
    if (paramString.length() <= 6) {}
    try
    {
      int i = Integer.parseInt(paramString);
      if (i > 0) {
        handleNewFrameCount(i);
      }
      return null;
    }
    catch (NumberFormatException localNumberFormatException)
    {
      for (;;) {}
    }
    handleNewFrameCount(1);
    return paramString;
  }
  
  private void handleIncomingFrame(String paramString)
  {
    if (!isClosed)
    {
      resetKeepAlive();
      if (isBuffering())
      {
        appendFrame(paramString);
        return;
      }
      paramString = extractFrameCount(paramString);
      if (paramString != null) {
        appendFrame(paramString);
      }
    }
  }
  
  private void handleNewFrameCount(int paramInt)
  {
    totalFrames = paramInt;
    frameReader = new StringListReader();
    if (logger.logsDebug())
    {
      LogWrapper localLogWrapper = logger;
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("HandleNewFrameCount: ");
      localStringBuilder.append(totalFrames);
      localLogWrapper.debug(localStringBuilder.toString(), new Object[0]);
    }
  }
  
  private boolean isBuffering()
  {
    return frameReader != null;
  }
  
  private void onClosed()
  {
    if (!isClosed)
    {
      if (logger.logsDebug()) {
        logger.debug("closing itself", new Object[0]);
      }
      shutdown();
    }
    conn = null;
    ScheduledFuture localScheduledFuture = keepAlive;
    if (localScheduledFuture != null) {
      localScheduledFuture.cancel(false);
    }
  }
  
  private void resetKeepAlive()
  {
    if (!isClosed)
    {
      Object localObject = keepAlive;
      if (localObject != null)
      {
        ((ScheduledFuture)localObject).cancel(false);
        if (logger.logsDebug())
        {
          localObject = logger;
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("Reset keepAlive. Remaining: ");
          localStringBuilder.append(keepAlive.getDelay(TimeUnit.MILLISECONDS));
          ((LogWrapper)localObject).debug(localStringBuilder.toString(), new Object[0]);
        }
      }
      else if (logger.logsDebug())
      {
        logger.debug("Reset keepAlive", new Object[0]);
      }
      keepAlive = executorService.schedule(del(), 45000L, TimeUnit.MILLISECONDS);
    }
  }
  
  private void shutdown()
  {
    isClosed = true;
    delegate.onDisconnect(everConnected);
  }
  
  private static String[] splitIntoFrames(String paramString, int paramInt)
  {
    int j = paramString.length();
    int i = 0;
    if (j <= paramInt) {
      return new String[] { paramString };
    }
    ArrayList localArrayList = new ArrayList();
    while (i < paramString.length())
    {
      j = i + paramInt;
      localArrayList.add(paramString.substring(i, Math.min(j, paramString.length())));
      i = j;
    }
    return (String[])localArrayList.toArray(new String[localArrayList.size()]);
  }
  
  public void close()
  {
    if (logger.logsDebug()) {
      logger.debug("websocket is being closed", new Object[0]);
    }
    isClosed = true;
    conn.close();
    ScheduledFuture localScheduledFuture = connectTimeout;
    if (localScheduledFuture != null) {
      localScheduledFuture.cancel(true);
    }
    localScheduledFuture = keepAlive;
    if (localScheduledFuture != null) {
      localScheduledFuture.cancel(true);
    }
  }
  
  public void open()
  {
    conn.connect();
    connectTimeout = executorService.schedule(new Runnable()
    {
      public void run()
      {
        WebsocketConnection.this.closeIfNeverConnected();
      }
    }, 30000L, TimeUnit.MILLISECONDS);
  }
  
  public void send(Map paramMap)
  {
    resetKeepAlive();
    try
    {
      String[] arrayOfString = splitIntoFrames(JsonMapper.serializeJson(paramMap), 16384);
      Object localObject1;
      Object localObject2;
      if (arrayOfString.length > 1)
      {
        localObject1 = conn;
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append("");
        i = arrayOfString.length;
        ((StringBuilder)localObject2).append(i);
        ((WSClient)localObject1).send(((StringBuilder)localObject2).toString());
      }
      int i = 0;
      while (i < arrayOfString.length)
      {
        localObject1 = conn;
        localObject2 = arrayOfString[i];
        ((WSClient)localObject1).send((String)localObject2);
        i += 1;
      }
      return;
    }
    catch (IOException localIOException)
    {
      localObject1 = logger;
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("Failed to serialize message: ");
      ((StringBuilder)localObject2).append(paramMap.toString());
      ((LogWrapper)localObject1).error(((StringBuilder)localObject2).toString(), localIOException);
      shutdown();
    }
  }
  
  public void start() {}
  
  public static abstract interface Delegate
  {
    public abstract void onDisconnect(boolean paramBoolean);
    
    public abstract void onMessage(Map paramMap);
  }
  
  private static abstract interface WSClient
  {
    public abstract void close();
    
    public abstract void connect();
    
    public abstract void send(String paramString);
  }
  
  private class WSClientTubesock
    implements WebsocketConnection.WSClient, WebSocketEventHandler
  {
    private WebSocket conn;
    
    private WSClientTubesock(WebSocket paramWebSocket)
    {
      conn = paramWebSocket;
      conn.setEventHandler(this);
    }
    
    private void shutdown()
    {
      conn.close();
      WebSocket localWebSocket = conn;
      try
      {
        localWebSocket.blockClose();
        return;
      }
      catch (InterruptedException localInterruptedException)
      {
        logger.error("Interrupted while shutting down websocket threads", localInterruptedException);
      }
    }
    
    public void close()
    {
      conn.close();
    }
    
    public void connect()
    {
      WebSocket localWebSocket = conn;
      try
      {
        localWebSocket.connect();
        return;
      }
      catch (WebSocketException localWebSocketException)
      {
        if (logger.logsDebug()) {
          logger.debug("Error connecting", localWebSocketException, new Object[0]);
        }
        shutdown();
      }
    }
    
    public void onClose()
    {
      executorService.execute(new Runnable()
      {
        public void run()
        {
          if (logger.logsDebug()) {
            logger.debug("closed", new Object[0]);
          }
          WebsocketConnection.this.onClosed();
        }
      });
    }
    
    public void onError(final WebSocketException paramWebSocketException)
    {
      executorService.execute(new Runnable()
      {
        public void run()
        {
          if ((paramWebSocketException.getCause() != null) && ((paramWebSocketException.getCause() instanceof EOFException))) {
            logger.debug("WebSocket reached EOF.", new Object[0]);
          } else {
            logger.debug("WebSocket error.", paramWebSocketException, new Object[0]);
          }
          WebsocketConnection.this.onClosed();
        }
      });
    }
    
    public void onLogMessage(String paramString)
    {
      if (logger.logsDebug())
      {
        LogWrapper localLogWrapper = logger;
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("Tubesock: ");
        localStringBuilder.append(paramString);
        localLogWrapper.debug(localStringBuilder.toString(), new Object[0]);
      }
    }
    
    public void onMessage(final WebSocketMessage paramWebSocketMessage)
    {
      paramWebSocketMessage = paramWebSocketMessage.getText();
      if (logger.logsDebug())
      {
        LogWrapper localLogWrapper = logger;
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("ws message: ");
        localStringBuilder.append(paramWebSocketMessage);
        localLogWrapper.debug(localStringBuilder.toString(), new Object[0]);
      }
      executorService.execute(new Runnable()
      {
        public void run()
        {
          WebsocketConnection.this.handleIncomingFrame(paramWebSocketMessage);
        }
      });
    }
    
    public void onOpen()
    {
      executorService.execute(new Runnable()
      {
        public void run()
        {
          connectTimeout.cancel(false);
          WebsocketConnection.access$102(WebsocketConnection.this, true);
          if (logger.logsDebug()) {
            logger.debug("websocket opened", new Object[0]);
          }
          WebsocketConnection.this.resetKeepAlive();
        }
      });
    }
    
    public void send(String paramString)
    {
      conn.send(paramString);
    }
  }
}
