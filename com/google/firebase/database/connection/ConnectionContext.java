package com.google.firebase.database.connection;

import com.google.firebase.database.logging.Logger;
import java.util.concurrent.ScheduledExecutorService;

public class ConnectionContext
{
  private final ConnectionAuthTokenProvider authTokenProvider;
  private final String clientSdkVersion;
  private final ScheduledExecutorService executorService;
  private final Logger logger;
  private final boolean persistenceEnabled;
  private final String sslCacheDirectory;
  private final String userAgent;
  
  public ConnectionContext(Logger paramLogger, ConnectionAuthTokenProvider paramConnectionAuthTokenProvider, ScheduledExecutorService paramScheduledExecutorService, boolean paramBoolean, String paramString1, String paramString2, String paramString3)
  {
    logger = paramLogger;
    authTokenProvider = paramConnectionAuthTokenProvider;
    executorService = paramScheduledExecutorService;
    persistenceEnabled = paramBoolean;
    clientSdkVersion = paramString1;
    userAgent = paramString2;
    sslCacheDirectory = paramString3;
  }
  
  public ConnectionAuthTokenProvider getAuthTokenProvider()
  {
    return authTokenProvider;
  }
  
  public String getClientSdkVersion()
  {
    return clientSdkVersion;
  }
  
  public ScheduledExecutorService getExecutorService()
  {
    return executorService;
  }
  
  public Logger getLogger()
  {
    return logger;
  }
  
  public String getSslCacheDirectory()
  {
    return sslCacheDirectory;
  }
  
  public String getUserAgent()
  {
    return userAgent;
  }
  
  public boolean isPersistenceEnabled()
  {
    return persistenceEnabled;
  }
}
