package com.google.firebase.database.android;

import android.os.Build.VERSION;
import android.os.Handler;
import android.util.Log;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseApp.BackgroundStateChangeListener;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.connection.ConnectionContext;
import com.google.firebase.database.connection.HostInfo;
import com.google.firebase.database.connection.PersistentConnection;
import com.google.firebase.database.connection.PersistentConnection.Delegate;
import com.google.firebase.database.connection.PersistentConnectionImpl;
import com.google.firebase.database.core.AuthTokenProvider;
import com.google.firebase.database.core.EventTarget;
import com.google.firebase.database.core.Platform;
import com.google.firebase.database.core.RunLoop;
import com.google.firebase.database.core.persistence.DefaultPersistenceManager;
import com.google.firebase.database.core.persistence.LRUCachePolicy;
import com.google.firebase.database.core.persistence.PersistenceManager;
import com.google.firebase.database.core.utilities.DefaultRunLoop;
import com.google.firebase.database.logging.AndroidLogger;
import com.google.firebase.database.logging.LogWrapper;
import com.google.firebase.database.logging.Logger;
import com.google.firebase.database.logging.Logger.Level;
import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;

public class AndroidPlatform
  implements Platform
{
  private static final String APP_IN_BACKGROUND_INTERRUPT_REASON = "app_in_background";
  private final android.content.Context applicationContext;
  private final Set<String> createdPersistenceCaches = new HashSet();
  private final FirebaseApp firebaseApp;
  
  public AndroidPlatform(FirebaseApp paramFirebaseApp)
  {
    firebaseApp = paramFirebaseApp;
    paramFirebaseApp = firebaseApp;
    if (paramFirebaseApp != null)
    {
      applicationContext = paramFirebaseApp.getApplicationContext();
      return;
    }
    Log.e("FirebaseDatabase", "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    Log.e("FirebaseDatabase", "ERROR: You must call FirebaseApp.initializeApp() before using Firebase Database.");
    Log.e("FirebaseDatabase", "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    throw new RuntimeException("You need to call FirebaseApp.initializeApp() before using Firebase Database.");
  }
  
  public PersistenceManager createPersistenceManager(com.google.firebase.database.core.Context paramContext, String paramString)
  {
    String str = paramContext.getSessionPersistenceKey();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramString);
    localStringBuilder.append("_");
    localStringBuilder.append(str);
    paramString = localStringBuilder.toString();
    if (!createdPersistenceCaches.contains(paramString))
    {
      createdPersistenceCaches.add(paramString);
      return new DefaultPersistenceManager(paramContext, new SqlPersistenceStorageEngine(applicationContext, paramContext, paramString), new LRUCachePolicy(paramContext.getPersistenceCacheSizeBytes()));
    }
    paramContext = new StringBuilder();
    paramContext.append("SessionPersistenceKey '");
    paramContext.append(str);
    paramContext.append("' has already been used.");
    throw new DatabaseException(paramContext.toString());
  }
  
  public String getPlatformVersion()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("android-");
    localStringBuilder.append(FirebaseDatabase.getSdkVersion());
    return localStringBuilder.toString();
  }
  
  public File getSSLCacheDirectory()
  {
    return applicationContext.getApplicationContext().getDir("sslcache", 0);
  }
  
  public String getUserAgent(com.google.firebase.database.core.Context paramContext)
  {
    paramContext = new StringBuilder();
    paramContext.append(Build.VERSION.SDK_INT);
    paramContext.append("/Android");
    return paramContext.toString();
  }
  
  public AuthTokenProvider newAuthTokenProvider(ScheduledExecutorService paramScheduledExecutorService)
  {
    return new AndroidAuthTokenProvider(firebaseApp, paramScheduledExecutorService);
  }
  
  public EventTarget newEventTarget(com.google.firebase.database.core.Context paramContext)
  {
    return new AndroidEventTarget();
  }
  
  public Logger newLogger(com.google.firebase.database.core.Context paramContext, Logger.Level paramLevel, List paramList)
  {
    return new AndroidLogger(paramLevel, paramList);
  }
  
  public PersistentConnection newPersistentConnection(final com.google.firebase.database.core.Context paramContext, ConnectionContext paramConnectionContext, HostInfo paramHostInfo, PersistentConnection.Delegate paramDelegate)
  {
    paramContext = new PersistentConnectionImpl(paramConnectionContext, paramHostInfo, paramDelegate);
    firebaseApp.addBackgroundStateChangeListener(new FirebaseApp.BackgroundStateChangeListener()
    {
      public void onBackgroundStateChanged(boolean paramAnonymousBoolean)
      {
        if (paramAnonymousBoolean)
        {
          paramContext.interrupt("app_in_background");
          return;
        }
        paramContext.resume("app_in_background");
      }
    });
    return paramContext;
  }
  
  public RunLoop newRunLoop(com.google.firebase.database.core.Context paramContext)
  {
    new DefaultRunLoop()
    {
      public void handleException(final Throwable paramAnonymousThrowable)
      {
        final String str = DefaultRunLoop.messageForException(paramAnonymousThrowable);
        val$logger.error(str, paramAnonymousThrowable);
        new Handler(applicationContext.getMainLooper()).post(new Runnable()
        {
          public void run()
          {
            throw new RuntimeException(str, paramAnonymousThrowable);
          }
        });
        getExecutorService().shutdownNow();
      }
    };
  }
}
