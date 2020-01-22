package com.google.firebase.database.core;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.logging.Logger;
import java.util.List;

public class DatabaseConfig
  extends Context
{
  public DatabaseConfig() {}
  
  public void setAuthTokenProvider(AuthTokenProvider paramAuthTokenProvider)
  {
    authTokenProvider = paramAuthTokenProvider;
  }
  
  public void setDebugLogComponents(List paramList)
  {
    try
    {
      assertUnfrozen();
      setLogLevel(com.google.firebase.database.Logger.Level.DEBUG);
      loggedComponents = paramList;
      return;
    }
    catch (Throwable paramList)
    {
      throw paramList;
    }
  }
  
  public void setEventTarget(EventTarget paramEventTarget)
  {
    try
    {
      assertUnfrozen();
      eventTarget = paramEventTarget;
      return;
    }
    catch (Throwable paramEventTarget)
    {
      throw paramEventTarget;
    }
  }
  
  public void setFirebaseApp(FirebaseApp paramFirebaseApp)
  {
    try
    {
      firebaseApp = paramFirebaseApp;
      return;
    }
    catch (Throwable paramFirebaseApp)
    {
      throw paramFirebaseApp;
    }
  }
  
  public void setLogLevel(com.google.firebase.database.Logger.Level paramLevel)
  {
    try
    {
      assertUnfrozen();
      int i = 1.$SwitchMap$com$google$firebase$database$Logger$Level[paramLevel.ordinal()];
      if (i != 1)
      {
        if (i != 2)
        {
          if (i != 3)
          {
            if (i != 4)
            {
              if (i == 5)
              {
                logLevel = com.google.firebase.database.logging.Logger.Level.NONE;
              }
              else
              {
                StringBuilder localStringBuilder = new StringBuilder();
                localStringBuilder.append("Unknown log level: ");
                localStringBuilder.append(paramLevel);
                throw new IllegalArgumentException(localStringBuilder.toString());
              }
            }
            else {
              logLevel = com.google.firebase.database.logging.Logger.Level.ERROR;
            }
          }
          else {
            logLevel = com.google.firebase.database.logging.Logger.Level.WARN;
          }
        }
        else {
          logLevel = com.google.firebase.database.logging.Logger.Level.INFO;
        }
      }
      else {
        logLevel = com.google.firebase.database.logging.Logger.Level.DEBUG;
      }
      return;
    }
    catch (Throwable paramLevel)
    {
      throw paramLevel;
    }
  }
  
  public void setLogger(Logger paramLogger)
  {
    try
    {
      assertUnfrozen();
      logger = paramLogger;
      return;
    }
    catch (Throwable paramLogger)
    {
      throw paramLogger;
    }
  }
  
  public void setPersistenceCacheSizeBytes(long paramLong)
  {
    try
    {
      assertUnfrozen();
      if (paramLong >= 1048576L)
      {
        if (paramLong <= 104857600L)
        {
          cacheSize = paramLong;
          return;
        }
        throw new DatabaseException("Firebase Database currently doesn't support a cache size larger than 100MB");
      }
      throw new DatabaseException("The minimum cache size must be at least 1MB");
    }
    catch (Throwable localThrowable)
    {
      throw localThrowable;
    }
  }
  
  public void setPersistenceEnabled(boolean paramBoolean)
  {
    try
    {
      assertUnfrozen();
      persistenceEnabled = paramBoolean;
      return;
    }
    catch (Throwable localThrowable)
    {
      throw localThrowable;
    }
  }
  
  public void setRunLoop(RunLoop paramRunLoop)
  {
    runLoop = paramRunLoop;
  }
  
  public void setSessionPersistenceKey(String paramString)
  {
    try
    {
      assertUnfrozen();
      if ((paramString != null) && (!paramString.isEmpty()))
      {
        persistenceKey = paramString;
        return;
      }
      throw new IllegalArgumentException("Session identifier is not allowed to be empty or null!");
    }
    catch (Throwable paramString)
    {
      throw paramString;
    }
  }
}
