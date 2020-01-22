package com.google.firebase.database.core.operation;

import com.google.firebase.database.core.Path;
import com.google.firebase.database.snapshot.ChildKey;
import com.google.firebase.database.snapshot.Node;

public class Overwrite
  extends Operation
{
  private final Node snapshot;
  
  public Overwrite(OperationSource paramOperationSource, Path paramPath, Node paramNode)
  {
    super(Operation.OperationType.Overwrite, paramOperationSource, paramPath);
    snapshot = paramNode;
  }
  
  public Node getSnapshot()
  {
    return snapshot;
  }
  
  public Operation operationForChild(ChildKey paramChildKey)
  {
    if (path.isEmpty()) {
      return new Overwrite(source, Path.getEmptyPath(), snapshot.getImmediateChild(paramChildKey));
    }
    return new Overwrite(source, path.popFront(), snapshot);
  }
  
  public String toString()
  {
    return String.format("Overwrite { path=%s, source=%s, snapshot=%s }", new Object[] { getPath(), getSource(), snapshot });
  }
}
