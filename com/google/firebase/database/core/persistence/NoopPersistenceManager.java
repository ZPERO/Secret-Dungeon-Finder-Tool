package com.google.firebase.database.core.persistence;

import android.util.Log;
import com.google.firebase.database.core.CompoundWrite;
import com.google.firebase.database.core.Path;
import com.google.firebase.database.core.utilities.Utilities;
import com.google.firebase.database.core.view.CacheNode;
import com.google.firebase.database.core.view.QuerySpec;
import com.google.firebase.database.snapshot.EmptyNode;
import com.google.firebase.database.snapshot.IndexedNode;
import com.google.firebase.database.snapshot.Node;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

public class NoopPersistenceManager
  implements PersistenceManager
{
  private static final String PAGE_KEY = "NoopPersistenceManager";
  private boolean insideTransaction = false;
  
  public NoopPersistenceManager() {}
  
  private void verifyInsideTransaction()
  {
    Utilities.hardAssert(insideTransaction, "Transaction expected to already be in progress.");
  }
  
  public void applyUserWriteToServerCache(Path paramPath, CompoundWrite paramCompoundWrite)
  {
    verifyInsideTransaction();
  }
  
  public void applyUserWriteToServerCache(Path paramPath, Node paramNode)
  {
    verifyInsideTransaction();
  }
  
  public List loadUserWrites()
  {
    return Collections.emptyList();
  }
  
  public void removeAllUserWrites()
  {
    verifyInsideTransaction();
  }
  
  public void removeUserWrite(long paramLong)
  {
    verifyInsideTransaction();
  }
  
  public Object runInTransaction(Callable paramCallable)
  {
    Utilities.hardAssert(insideTransaction ^ true, "runInTransaction called when an existing transaction is already in progress.");
    insideTransaction = true;
    try
    {
      paramCallable = paramCallable.call();
      insideTransaction = false;
      return paramCallable;
    }
    catch (Throwable paramCallable)
    {
      try
      {
        Log.e("NoopPersistenceManager", "Caught Throwable.", paramCallable);
        throw new RuntimeException(paramCallable);
      }
      catch (Throwable paramCallable)
      {
        insideTransaction = false;
        throw paramCallable;
      }
    }
  }
  
  public void saveUserMerge(Path paramPath, CompoundWrite paramCompoundWrite, long paramLong)
  {
    verifyInsideTransaction();
  }
  
  public void saveUserOverwrite(Path paramPath, Node paramNode, long paramLong)
  {
    verifyInsideTransaction();
  }
  
  public CacheNode serverCache(QuerySpec paramQuerySpec)
  {
    return new CacheNode(IndexedNode.from(EmptyNode.Empty(), paramQuerySpec.getIndex()), false, false);
  }
  
  public void setQueryActive(QuerySpec paramQuerySpec)
  {
    verifyInsideTransaction();
  }
  
  public void setQueryComplete(QuerySpec paramQuerySpec)
  {
    verifyInsideTransaction();
  }
  
  public void setQueryInactive(QuerySpec paramQuerySpec)
  {
    verifyInsideTransaction();
  }
  
  public void setTrackedQueryKeys(QuerySpec paramQuerySpec, Set paramSet)
  {
    verifyInsideTransaction();
  }
  
  public void updateServerCache(Path paramPath, CompoundWrite paramCompoundWrite)
  {
    verifyInsideTransaction();
  }
  
  public void updateServerCache(QuerySpec paramQuerySpec, Node paramNode)
  {
    verifyInsideTransaction();
  }
  
  public void updateTrackedQueryKeys(QuerySpec paramQuerySpec, Set paramSet1, Set paramSet2)
  {
    verifyInsideTransaction();
  }
}
