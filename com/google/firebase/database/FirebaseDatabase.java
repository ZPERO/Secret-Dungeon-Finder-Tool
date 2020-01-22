package com.google.firebase.database;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.core.DatabaseConfig;
import com.google.firebase.database.core.Path;
import com.google.firebase.database.core.Repo;
import com.google.firebase.database.core.RepoInfo;
import com.google.firebase.database.core.RepoManager;
import com.google.firebase.database.core.utilities.ParsedUrl;
import com.google.firebase.database.core.utilities.Utilities;
import com.google.firebase.database.core.utilities.Validation;
import java.util.HashMap;
import java.util.Map;

public class FirebaseDatabase
{
  private static final String SDK_VERSION = "3.0.0";
  private static final Map<String, Map<RepoInfo, FirebaseDatabase>> databaseInstances = new HashMap();
  private final DatabaseConfig config;
  private final FirebaseApp mApp;
  private Repo repo;
  private final RepoInfo repoInfo;
  
  private FirebaseDatabase(FirebaseApp paramFirebaseApp, RepoInfo paramRepoInfo, DatabaseConfig paramDatabaseConfig)
  {
    mApp = paramFirebaseApp;
    repoInfo = paramRepoInfo;
    config = paramDatabaseConfig;
  }
  
  private void assertUnfrozen(String paramString)
  {
    if (repo == null) {
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Calls to ");
    localStringBuilder.append(paramString);
    localStringBuilder.append("() must be made before any other usage of FirebaseDatabase instance.");
    throw new DatabaseException(localStringBuilder.toString());
  }
  
  static FirebaseDatabase createForTests(FirebaseApp paramFirebaseApp, RepoInfo paramRepoInfo, DatabaseConfig paramDatabaseConfig)
  {
    paramFirebaseApp = new FirebaseDatabase(paramFirebaseApp, paramRepoInfo, paramDatabaseConfig);
    paramFirebaseApp.ensureRepo();
    return paramFirebaseApp;
  }
  
  private void ensureRepo()
  {
    try
    {
      if (repo == null) {
        repo = RepoManager.createRepo(config, repoInfo, this);
      }
      return;
    }
    catch (Throwable localThrowable)
    {
      throw localThrowable;
    }
  }
  
  public static FirebaseDatabase getInstance()
  {
    FirebaseApp localFirebaseApp = FirebaseApp.getInstance();
    if (localFirebaseApp != null) {
      return getInstance(localFirebaseApp, localFirebaseApp.getOptions().getDatabaseUrl());
    }
    throw new DatabaseException("You must call FirebaseApp.initialize() first.");
  }
  
  public static FirebaseDatabase getInstance(FirebaseApp paramFirebaseApp)
  {
    return getInstance(paramFirebaseApp, paramFirebaseApp.getOptions().getDatabaseUrl());
  }
  
  public static FirebaseDatabase getInstance(FirebaseApp paramFirebaseApp, String paramString)
  {
    throw new Runtime("d2j fail translate: java.lang.RuntimeException: fail exe a4 = a3\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.exec(BaseAnalyze.java:92)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.exec(BaseAnalyze.java:1)\n\tat com.googlecode.dex2jar.ir.ts.Cfg.dfs(Cfg.java:255)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.analyze0(BaseAnalyze.java:75)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.analyze(BaseAnalyze.java:69)\n\tat com.googlecode.dex2jar.ir.ts.UnSSATransformer.transform(UnSSATransformer.java:274)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:163)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\nCaused by: java.lang.NullPointerException\n");
  }
  
  public static FirebaseDatabase getInstance(String paramString)
  {
    FirebaseApp localFirebaseApp = FirebaseApp.getInstance();
    if (localFirebaseApp != null) {
      return getInstance(localFirebaseApp, paramString);
    }
    throw new DatabaseException("You must call FirebaseApp.initialize() first.");
  }
  
  public static String getSdkVersion()
  {
    return "3.0.0";
  }
  
  public FirebaseApp getApp()
  {
    return mApp;
  }
  
  DatabaseConfig getConfig()
  {
    return config;
  }
  
  public DatabaseReference getReference()
  {
    ensureRepo();
    return new DatabaseReference(repo, Path.getEmptyPath());
  }
  
  public DatabaseReference getReference(String paramString)
  {
    ensureRepo();
    if (paramString != null)
    {
      Validation.validateRootPathString(paramString);
      paramString = new Path(paramString);
      return new DatabaseReference(repo, paramString);
    }
    throw new NullPointerException("Can't pass null for argument 'pathString' in FirebaseDatabase.getReference()");
  }
  
  public DatabaseReference getReferenceFromUrl(String paramString)
  {
    ensureRepo();
    if (paramString != null)
    {
      Object localObject = Utilities.parseUrl(paramString);
      if (repoInfo.host.equals(repo.getRepoInfo().host)) {
        return new DatabaseReference(repo, path);
      }
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("Invalid URL (");
      ((StringBuilder)localObject).append(paramString);
      ((StringBuilder)localObject).append(") passed to getReference().  URL was expected to match configured Database URL: ");
      ((StringBuilder)localObject).append(getReference().toString());
      throw new DatabaseException(((StringBuilder)localObject).toString());
    }
    throw new NullPointerException("Can't pass null for argument 'url' in FirebaseDatabase.getReferenceFromUrl()");
  }
  
  public void goOffline()
  {
    ensureRepo();
    RepoManager.interrupt(repo);
  }
  
  public void goOnline()
  {
    ensureRepo();
    RepoManager.resume(repo);
  }
  
  public void purgeOutstandingWrites()
  {
    ensureRepo();
    repo.scheduleNow(new Runnable()
    {
      public void run()
      {
        repo.purgeOutstandingWrites();
      }
    });
  }
  
  public void setLogLevel(Logger.Level paramLevel)
  {
    try
    {
      assertUnfrozen("setLogLevel");
      config.setLogLevel(paramLevel);
      return;
    }
    catch (Throwable paramLevel)
    {
      throw paramLevel;
    }
  }
  
  public void setPersistenceCacheSizeBytes(long paramLong)
  {
    try
    {
      assertUnfrozen("setPersistenceCacheSizeBytes");
      config.setPersistenceCacheSizeBytes(paramLong);
      return;
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
      assertUnfrozen("setPersistenceEnabled");
      config.setPersistenceEnabled(paramBoolean);
      return;
    }
    catch (Throwable localThrowable)
    {
      throw localThrowable;
    }
  }
}
