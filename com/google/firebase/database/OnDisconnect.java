package com.google.firebase.database;

import com.google.android.android.tasks.Task;
import com.google.firebase.database.core.Path;
import com.google.firebase.database.core.Repo;
import com.google.firebase.database.core.ValidationPath;
import com.google.firebase.database.core.utilities.Pair;
import com.google.firebase.database.core.utilities.Utilities;
import com.google.firebase.database.core.utilities.Validation;
import com.google.firebase.database.core.utilities.encoding.CustomClassMapper;
import com.google.firebase.database.snapshot.Node;
import com.google.firebase.database.snapshot.NodeUtilities;
import com.google.firebase.database.snapshot.PriorityUtilities;
import java.util.Map;

public class OnDisconnect
{
  private Path path;
  private Repo repo;
  
  OnDisconnect(Repo paramRepo, Path paramPath)
  {
    repo = paramRepo;
    path = paramPath;
  }
  
  private Task cancelInternal(final DatabaseReference.CompletionListener paramCompletionListener)
  {
    paramCompletionListener = Utilities.wrapOnComplete(paramCompletionListener);
    repo.scheduleNow(new Runnable()
    {
      public void run()
      {
        repo.onDisconnectCancel(path, (DatabaseReference.CompletionListener)paramCompletionListener.getSecond());
      }
    });
    return (Task)paramCompletionListener.getFirst();
  }
  
  private Task onDisconnectSetInternal(final Object paramObject, final Node paramNode, DatabaseReference.CompletionListener paramCompletionListener)
  {
    Validation.validateWritablePath(path);
    ValidationPath.validateWithObject(path, paramObject);
    paramObject = CustomClassMapper.convertToPlainJavaTypes(paramObject);
    Validation.validateWritableObject(paramObject);
    paramObject = NodeUtilities.NodeFromJSON(paramObject, paramNode);
    paramNode = Utilities.wrapOnComplete(paramCompletionListener);
    repo.scheduleNow(new Runnable()
    {
      public void run()
      {
        repo.onDisconnectSetValue(path, paramObject, (DatabaseReference.CompletionListener)paramNode.getSecond());
      }
    });
    return (Task)paramNode.getFirst();
  }
  
  private Task updateChildrenInternal(final Map paramMap, final DatabaseReference.CompletionListener paramCompletionListener)
  {
    final Map localMap = Validation.parseAndValidateUpdate(path, paramMap);
    paramCompletionListener = Utilities.wrapOnComplete(paramCompletionListener);
    repo.scheduleNow(new Runnable()
    {
      public void run()
      {
        repo.onDisconnectUpdate(path, localMap, (DatabaseReference.CompletionListener)paramCompletionListener.getSecond(), paramMap);
      }
    });
    return (Task)paramCompletionListener.getFirst();
  }
  
  public Task cancel()
  {
    return cancelInternal(null);
  }
  
  public void cancel(DatabaseReference.CompletionListener paramCompletionListener)
  {
    cancelInternal(paramCompletionListener);
  }
  
  public Task removeValue()
  {
    return setValue(null);
  }
  
  public void removeValue(DatabaseReference.CompletionListener paramCompletionListener)
  {
    setValue(null, paramCompletionListener);
  }
  
  public Task setValue(Object paramObject)
  {
    return onDisconnectSetInternal(paramObject, PriorityUtilities.NullPriority(), null);
  }
  
  public Task setValue(Object paramObject, double paramDouble)
  {
    return onDisconnectSetInternal(paramObject, PriorityUtilities.parsePriority(path, Double.valueOf(paramDouble)), null);
  }
  
  public Task setValue(Object paramObject, String paramString)
  {
    return onDisconnectSetInternal(paramObject, PriorityUtilities.parsePriority(path, paramString), null);
  }
  
  public void setValue(Object paramObject, double paramDouble, DatabaseReference.CompletionListener paramCompletionListener)
  {
    onDisconnectSetInternal(paramObject, PriorityUtilities.parsePriority(path, Double.valueOf(paramDouble)), paramCompletionListener);
  }
  
  public void setValue(Object paramObject, DatabaseReference.CompletionListener paramCompletionListener)
  {
    onDisconnectSetInternal(paramObject, PriorityUtilities.NullPriority(), paramCompletionListener);
  }
  
  public void setValue(Object paramObject, String paramString, DatabaseReference.CompletionListener paramCompletionListener)
  {
    onDisconnectSetInternal(paramObject, PriorityUtilities.parsePriority(path, paramString), paramCompletionListener);
  }
  
  public void setValue(Object paramObject, Map paramMap, DatabaseReference.CompletionListener paramCompletionListener)
  {
    onDisconnectSetInternal(paramObject, PriorityUtilities.parsePriority(path, paramMap), paramCompletionListener);
  }
  
  public Task updateChildren(Map paramMap)
  {
    return updateChildrenInternal(paramMap, null);
  }
  
  public void updateChildren(Map paramMap, DatabaseReference.CompletionListener paramCompletionListener)
  {
    updateChildrenInternal(paramMap, paramCompletionListener);
  }
}
