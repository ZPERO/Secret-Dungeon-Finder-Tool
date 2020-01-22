package com.google.firebase.database.core.operation;

import com.google.firebase.database.core.Path;
import com.google.firebase.database.snapshot.ChildKey;

public class ListenComplete
  extends Operation
{
  public ListenComplete(OperationSource paramOperationSource, Path paramPath)
  {
    super(Operation.OperationType.ListenComplete, paramOperationSource, paramPath);
  }
  
  public Operation operationForChild(ChildKey paramChildKey)
  {
    if (path.isEmpty()) {
      return new ListenComplete(source, Path.getEmptyPath());
    }
    return new ListenComplete(source, path.popFront());
  }
  
  public String toString()
  {
    return String.format("ListenComplete { path=%s, source=%s }", new Object[] { getPath(), getSource() });
  }
}
