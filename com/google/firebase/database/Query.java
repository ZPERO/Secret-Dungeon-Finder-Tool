package com.google.firebase.database;

import com.google.android.android.common.internal.Objects;
import com.google.firebase.database.core.ChildEventRegistration;
import com.google.firebase.database.core.EventRegistration;
import com.google.firebase.database.core.Path;
import com.google.firebase.database.core.Repo;
import com.google.firebase.database.core.ValueEventRegistration;
import com.google.firebase.database.core.ZombieEventManager;
import com.google.firebase.database.core.utilities.Utilities;
import com.google.firebase.database.core.utilities.Validation;
import com.google.firebase.database.core.view.QueryParams;
import com.google.firebase.database.core.view.QuerySpec;
import com.google.firebase.database.snapshot.BooleanNode;
import com.google.firebase.database.snapshot.ChildKey;
import com.google.firebase.database.snapshot.DoubleNode;
import com.google.firebase.database.snapshot.KeyIndex;
import com.google.firebase.database.snapshot.Node;
import com.google.firebase.database.snapshot.PathIndex;
import com.google.firebase.database.snapshot.PriorityIndex;
import com.google.firebase.database.snapshot.PriorityUtilities;
import com.google.firebase.database.snapshot.StringNode;
import com.google.firebase.database.snapshot.ValueIndex;

public class Query
{
  private final boolean orderByCalled;
  protected final QueryParams params;
  protected final Path path;
  protected final Repo repo;
  
  Query(Repo paramRepo, Path paramPath)
  {
    repo = paramRepo;
    path = paramPath;
    params = QueryParams.DEFAULT_PARAMS;
    orderByCalled = false;
  }
  
  Query(Repo paramRepo, Path paramPath, QueryParams paramQueryParams, boolean paramBoolean)
    throws DatabaseException
  {
    repo = paramRepo;
    path = paramPath;
    params = paramQueryParams;
    orderByCalled = paramBoolean;
    Utilities.hardAssert(paramQueryParams.isValid(), "Validation of queries failed.");
  }
  
  private void addEventRegistration(final EventRegistration paramEventRegistration)
  {
    ZombieEventManager.getInstance().recordEventRegistration(paramEventRegistration);
    repo.scheduleNow(new Runnable()
    {
      public void run()
      {
        repo.addEventCallback(paramEventRegistration);
      }
    });
  }
  
  private Query endAt(Node paramNode, String paramString)
  {
    Validation.validateNullableKey(paramString);
    if ((!paramNode.isLeafNode()) && (!paramNode.isEmpty())) {
      throw new IllegalArgumentException("Can only use simple values for endAt()");
    }
    if (paramString != null) {
      paramString = ChildKey.fromString(paramString);
    } else {
      paramString = null;
    }
    if (!params.hasEnd())
    {
      paramNode = params.endAt(paramNode, paramString);
      validateLimit(paramNode);
      validateQueryEndpoints(paramNode);
      return new Query(repo, path, paramNode, orderByCalled);
    }
    throw new IllegalArgumentException("Can't call endAt() or equalTo() multiple times");
  }
  
  private void removeEventRegistration(final EventRegistration paramEventRegistration)
  {
    ZombieEventManager.getInstance().zombifyForRemove(paramEventRegistration);
    repo.scheduleNow(new Runnable()
    {
      public void run()
      {
        repo.removeEventCallback(paramEventRegistration);
      }
    });
  }
  
  private Query startAt(Node paramNode, String paramString)
  {
    Validation.validateNullableKey(paramString);
    if ((!paramNode.isLeafNode()) && (!paramNode.isEmpty())) {
      throw new IllegalArgumentException("Can only use simple values for startAt()");
    }
    if (!params.hasStart())
    {
      if (paramString != null) {
        paramString = ChildKey.fromString(paramString);
      } else {
        paramString = null;
      }
      paramNode = params.startAt(paramNode, paramString);
      validateLimit(paramNode);
      validateQueryEndpoints(paramNode);
      return new Query(repo, path, paramNode, orderByCalled);
    }
    throw new IllegalArgumentException("Can't call startAt() or equalTo() multiple times");
  }
  
  private void validateEqualToCall()
  {
    if (!params.hasStart())
    {
      if (!params.hasEnd()) {
        return;
      }
      throw new IllegalArgumentException("Can't call equalTo() and endAt() combined");
    }
    throw new IllegalArgumentException("Can't call equalTo() and startAt() combined");
  }
  
  private void validateLimit(QueryParams paramQueryParams)
  {
    if ((paramQueryParams.hasStart()) && (paramQueryParams.hasEnd()) && (paramQueryParams.hasLimit()))
    {
      if (paramQueryParams.hasAnchoredLimit()) {
        return;
      }
      throw new IllegalArgumentException("Can't combine startAt(), endAt() and limit(). Use limitToFirst() or limitToLast() instead");
    }
  }
  
  private void validateNoOrderByCall()
  {
    if (!orderByCalled) {
      return;
    }
    throw new IllegalArgumentException("You can't combine multiple orderBy calls!");
  }
  
  private void validateQueryEndpoints(QueryParams paramQueryParams)
  {
    if (paramQueryParams.getIndex().equals(KeyIndex.getInstance()))
    {
      Node localNode;
      if (paramQueryParams.hasStart())
      {
        localNode = paramQueryParams.getIndexStartValue();
        if ((!Objects.equal(paramQueryParams.getIndexStartName(), ChildKey.getMinName())) || (!(localNode instanceof StringNode))) {
          throw new IllegalArgumentException("You must use startAt(String value), endAt(String value) or equalTo(String value) in combination with orderByKey(). Other type of values or using the version with 2 parameters is not supported");
        }
      }
      if (paramQueryParams.hasEnd())
      {
        localNode = paramQueryParams.getIndexEndValue();
        if ((paramQueryParams.getIndexEndName().equals(ChildKey.getMaxName())) && ((localNode instanceof StringNode))) {
          return;
        }
        throw new IllegalArgumentException("You must use startAt(String value), endAt(String value) or equalTo(String value) in combination with orderByKey(). Other type of values or using the version with 2 parameters is not supported");
      }
    }
    else if (paramQueryParams.getIndex().equals(PriorityIndex.getInstance()))
    {
      if ((!paramQueryParams.hasStart()) || (PriorityUtilities.isValidPriority(paramQueryParams.getIndexStartValue())))
      {
        if (!paramQueryParams.hasEnd()) {
          return;
        }
        if (PriorityUtilities.isValidPriority(paramQueryParams.getIndexEndValue())) {
          return;
        }
      }
      throw new IllegalArgumentException("When using orderByPriority(), values provided to startAt(), endAt(), or equalTo() must be valid priorities.");
    }
  }
  
  public ChildEventListener addChildEventListener(ChildEventListener paramChildEventListener)
  {
    addEventRegistration(new ChildEventRegistration(repo, paramChildEventListener, getSpec()));
    return paramChildEventListener;
  }
  
  public void addListenerForSingleValueEvent(final ValueEventListener paramValueEventListener)
  {
    addEventRegistration(new ValueEventRegistration(repo, new ValueEventListener()
    {
      public void onCancelled(DatabaseError paramAnonymousDatabaseError)
      {
        paramValueEventListener.onCancelled(paramAnonymousDatabaseError);
      }
      
      public void onDataChange(DataSnapshot paramAnonymousDataSnapshot)
      {
        removeEventListener(this);
        paramValueEventListener.onDataChange(paramAnonymousDataSnapshot);
      }
    }, getSpec()));
  }
  
  public ValueEventListener addValueEventListener(ValueEventListener paramValueEventListener)
  {
    addEventRegistration(new ValueEventRegistration(repo, paramValueEventListener, getSpec()));
    return paramValueEventListener;
  }
  
  public Query endAt(double paramDouble)
  {
    return endAt(paramDouble, null);
  }
  
  public Query endAt(double paramDouble, String paramString)
  {
    return endAt(new DoubleNode(Double.valueOf(paramDouble), PriorityUtilities.NullPriority()), paramString);
  }
  
  public Query endAt(String paramString)
  {
    return endAt(paramString, null);
  }
  
  public Query endAt(String paramString1, String paramString2)
  {
    throw new Runtime("d2j fail translate: java.lang.RuntimeException: fail exe a4 = a3\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.exec(BaseAnalyze.java:92)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.exec(BaseAnalyze.java:1)\n\tat com.googlecode.dex2jar.ir.ts.Cfg.dfs(Cfg.java:255)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.analyze0(BaseAnalyze.java:75)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.analyze(BaseAnalyze.java:69)\n\tat com.googlecode.dex2jar.ir.ts.UnSSATransformer.transform(UnSSATransformer.java:274)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:163)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\nCaused by: java.lang.NullPointerException\n");
  }
  
  public Query endAt(boolean paramBoolean)
  {
    return endAt(paramBoolean, null);
  }
  
  public Query endAt(boolean paramBoolean, String paramString)
  {
    return endAt(new BooleanNode(Boolean.valueOf(paramBoolean), PriorityUtilities.NullPriority()), paramString);
  }
  
  public Query equalTo(double paramDouble)
  {
    validateEqualToCall();
    return startAt(paramDouble).endAt(paramDouble);
  }
  
  public Query equalTo(double paramDouble, String paramString)
  {
    validateEqualToCall();
    return startAt(paramDouble, paramString).endAt(paramDouble, paramString);
  }
  
  public Query equalTo(String paramString)
  {
    validateEqualToCall();
    return startAt(paramString).endAt(paramString);
  }
  
  public Query equalTo(String paramString1, String paramString2)
  {
    validateEqualToCall();
    return startAt(paramString1, paramString2).endAt(paramString1, paramString2);
  }
  
  public Query equalTo(boolean paramBoolean)
  {
    validateEqualToCall();
    return startAt(paramBoolean).endAt(paramBoolean);
  }
  
  public Query equalTo(boolean paramBoolean, String paramString)
  {
    validateEqualToCall();
    return startAt(paramBoolean, paramString).endAt(paramBoolean, paramString);
  }
  
  public Path getPath()
  {
    return path;
  }
  
  public DatabaseReference getRef()
  {
    return new DatabaseReference(repo, getPath());
  }
  
  public Repo getRepo()
  {
    return repo;
  }
  
  public QuerySpec getSpec()
  {
    return new QuerySpec(path, params);
  }
  
  public void keepSynced(final boolean paramBoolean)
  {
    if ((!path.isEmpty()) && (path.getFront().equals(ChildKey.getInfoKey()))) {
      throw new DatabaseException("Can't call keepSynced() on .info paths.");
    }
    repo.scheduleNow(new Runnable()
    {
      public void run()
      {
        repo.keepSynced(getSpec(), paramBoolean);
      }
    });
  }
  
  public Query limitToFirst(int paramInt)
  {
    if (paramInt > 0)
    {
      if (!params.hasLimit()) {
        return new Query(repo, path, params.limitToFirst(paramInt), orderByCalled);
      }
      throw new IllegalArgumentException("Can't call limitToLast on query with previously set limit!");
    }
    throw new IllegalArgumentException("Limit must be a positive integer!");
  }
  
  public Query limitToLast(int paramInt)
  {
    if (paramInt > 0)
    {
      if (!params.hasLimit()) {
        return new Query(repo, path, params.limitToLast(paramInt), orderByCalled);
      }
      throw new IllegalArgumentException("Can't call limitToLast on query with previously set limit!");
    }
    throw new IllegalArgumentException("Limit must be a positive integer!");
  }
  
  public Query orderByChild(String paramString)
  {
    if (paramString != null)
    {
      if ((!paramString.equals("$key")) && (!paramString.equals(".key")))
      {
        if ((!paramString.equals("$priority")) && (!paramString.equals(".priority")))
        {
          if ((!paramString.equals("$value")) && (!paramString.equals(".value")))
          {
            Validation.validatePathString(paramString);
            validateNoOrderByCall();
            paramString = new Path(paramString);
            if (paramString.size() != 0)
            {
              paramString = new PathIndex(paramString);
              return new Query(repo, path, params.orderBy(paramString), true);
            }
            throw new IllegalArgumentException("Can't use empty path, use orderByValue() instead!");
          }
          localStringBuilder = new StringBuilder();
          localStringBuilder.append("Can't use '");
          localStringBuilder.append(paramString);
          localStringBuilder.append("' as path, please use orderByValue() instead!");
          throw new IllegalArgumentException(localStringBuilder.toString());
        }
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("Can't use '");
        localStringBuilder.append(paramString);
        localStringBuilder.append("' as path, please use orderByPriority() instead!");
        throw new IllegalArgumentException(localStringBuilder.toString());
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Can't use '");
      localStringBuilder.append(paramString);
      localStringBuilder.append("' as path, please use orderByKey() instead!");
      throw new IllegalArgumentException(localStringBuilder.toString());
    }
    throw new NullPointerException("Key can't be null");
  }
  
  public Query orderByKey()
  {
    validateNoOrderByCall();
    QueryParams localQueryParams = params.orderBy(KeyIndex.getInstance());
    validateQueryEndpoints(localQueryParams);
    return new Query(repo, path, localQueryParams, true);
  }
  
  public Query orderByPriority()
  {
    validateNoOrderByCall();
    QueryParams localQueryParams = params.orderBy(PriorityIndex.getInstance());
    validateQueryEndpoints(localQueryParams);
    return new Query(repo, path, localQueryParams, true);
  }
  
  public Query orderByValue()
  {
    validateNoOrderByCall();
    return new Query(repo, path, params.orderBy(ValueIndex.getInstance()), true);
  }
  
  public void removeEventListener(ChildEventListener paramChildEventListener)
  {
    if (paramChildEventListener != null)
    {
      removeEventRegistration(new ChildEventRegistration(repo, paramChildEventListener, getSpec()));
      return;
    }
    throw new NullPointerException("listener must not be null");
  }
  
  public void removeEventListener(ValueEventListener paramValueEventListener)
  {
    if (paramValueEventListener != null)
    {
      removeEventRegistration(new ValueEventRegistration(repo, paramValueEventListener, getSpec()));
      return;
    }
    throw new NullPointerException("listener must not be null");
  }
  
  public Query startAt(double paramDouble)
  {
    return startAt(paramDouble, null);
  }
  
  public Query startAt(double paramDouble, String paramString)
  {
    return startAt(new DoubleNode(Double.valueOf(paramDouble), PriorityUtilities.NullPriority()), paramString);
  }
  
  public Query startAt(String paramString)
  {
    return startAt(paramString, null);
  }
  
  public Query startAt(String paramString1, String paramString2)
  {
    throw new Runtime("d2j fail translate: java.lang.RuntimeException: fail exe a4 = a3\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.exec(BaseAnalyze.java:92)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.exec(BaseAnalyze.java:1)\n\tat com.googlecode.dex2jar.ir.ts.Cfg.dfs(Cfg.java:255)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.analyze0(BaseAnalyze.java:75)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.analyze(BaseAnalyze.java:69)\n\tat com.googlecode.dex2jar.ir.ts.UnSSATransformer.transform(UnSSATransformer.java:274)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:163)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\nCaused by: java.lang.NullPointerException\n");
  }
  
  public Query startAt(boolean paramBoolean)
  {
    return startAt(paramBoolean, null);
  }
  
  public Query startAt(boolean paramBoolean, String paramString)
  {
    return startAt(new BooleanNode(Boolean.valueOf(paramBoolean), PriorityUtilities.NullPriority()), paramString);
  }
}
