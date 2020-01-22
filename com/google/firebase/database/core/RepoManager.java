package com.google.firebase.database.core;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.InternalHelpers;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class RepoManager
{
  private static final RepoManager instance = new RepoManager();
  private final Map<Context, Map<String, Repo>> repos = new HashMap();
  
  public RepoManager() {}
  
  private Repo createLocalRepo(Context paramContext, RepoInfo paramRepoInfo, FirebaseDatabase paramFirebaseDatabase)
    throws DatabaseException
  {
    paramContext.freeze();
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("https://");
    ((StringBuilder)localObject1).append(host);
    ((StringBuilder)localObject1).append("/");
    ((StringBuilder)localObject1).append(namespace);
    String str = ((StringBuilder)localObject1).toString();
    localObject1 = repos;
    try
    {
      if (!repos.containsKey(paramContext))
      {
        localObject2 = new HashMap();
        repos.put(paramContext, localObject2);
      }
      Object localObject2 = (Map)repos.get(paramContext);
      if (!((Map)localObject2).containsKey(str))
      {
        paramContext = new Repo(paramRepoInfo, paramContext, paramFirebaseDatabase);
        ((Map)localObject2).put(str, paramContext);
        return paramContext;
      }
      throw new IllegalStateException("createLocalRepo() called for existing repo.");
    }
    catch (Throwable paramContext)
    {
      throw paramContext;
    }
  }
  
  public static Repo createRepo(Context paramContext, RepoInfo paramRepoInfo, FirebaseDatabase paramFirebaseDatabase)
    throws DatabaseException
  {
    return instance.createLocalRepo(paramContext, paramRepoInfo, paramFirebaseDatabase);
  }
  
  private Repo getLocalRepo(Context paramContext, RepoInfo paramRepoInfo)
    throws DatabaseException
  {
    paramContext.freeze();
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("https://");
    ((StringBuilder)localObject).append(host);
    ((StringBuilder)localObject).append("/");
    ((StringBuilder)localObject).append(namespace);
    String str = ((StringBuilder)localObject).toString();
    localObject = repos;
    try
    {
      if ((!repos.containsKey(paramContext)) || (!((Map)repos.get(paramContext)).containsKey(str))) {
        InternalHelpers.createDatabaseForTests(FirebaseApp.getInstance(), paramRepoInfo, (DatabaseConfig)paramContext);
      }
      paramContext = (Repo)((Map)repos.get(paramContext)).get(str);
      return paramContext;
    }
    catch (Throwable paramContext)
    {
      throw paramContext;
    }
  }
  
  public static Repo getRepo(Context paramContext, RepoInfo paramRepoInfo)
    throws DatabaseException
  {
    return instance.getLocalRepo(paramContext, paramRepoInfo);
  }
  
  public static void interrupt(Context paramContext)
  {
    instance.interruptInternal(paramContext);
  }
  
  public static void interrupt(Repo paramRepo)
  {
    paramRepo.scheduleNow(new Runnable()
    {
      public void run()
      {
        interrupt();
      }
    });
  }
  
  private void interruptInternal(final Context paramContext)
  {
    RunLoop localRunLoop = paramContext.getRunLoop();
    if (localRunLoop != null) {
      localRunLoop.scheduleNow(new Runnable()
      {
        public void run()
        {
          Map localMap = repos;
          for (;;)
          {
            try
            {
              if (repos.containsKey(paramContext))
              {
                Iterator localIterator = ((Map)repos.get(paramContext)).values().iterator();
                break label124;
                if (localIterator.hasNext())
                {
                  Repo localRepo = (Repo)localIterator.next();
                  localRepo.interrupt();
                  if ((i == 0) || (localRepo.hasListeners())) {
                    break label129;
                  }
                  break label124;
                }
                if (i != 0) {
                  paramContext.stop();
                }
              }
              return;
            }
            catch (Throwable localThrowable)
            {
              throw localThrowable;
            }
            label124:
            int i = 1;
            continue;
            label129:
            i = 0;
          }
        }
      });
    }
  }
  
  public static void resume(Context paramContext)
  {
    instance.resumeInternal(paramContext);
  }
  
  public static void resume(Repo paramRepo)
  {
    paramRepo.scheduleNow(new Runnable()
    {
      public void run()
      {
        resume();
      }
    });
  }
  
  private void resumeInternal(final Context paramContext)
  {
    RunLoop localRunLoop = paramContext.getRunLoop();
    if (localRunLoop != null) {
      localRunLoop.scheduleNow(new Runnable()
      {
        public void run()
        {
          Map localMap = repos;
          try
          {
            if (repos.containsKey(paramContext))
            {
              Iterator localIterator = ((Map)repos.get(paramContext)).values().iterator();
              while (localIterator.hasNext()) {
                ((Repo)localIterator.next()).resume();
              }
            }
            return;
          }
          catch (Throwable localThrowable)
          {
            throw localThrowable;
          }
        }
      });
    }
  }
}
