package com.google.firebase.database.core.operation;

import com.google.firebase.database.core.CompoundWrite;
import com.google.firebase.database.core.Path;
import com.google.firebase.database.snapshot.ChildKey;

public class Merge
  extends Operation
{
  private final CompoundWrite children;
  
  public Merge(OperationSource paramOperationSource, Path paramPath, CompoundWrite paramCompoundWrite)
  {
    super(Operation.OperationType.Merge, paramOperationSource, paramPath);
    children = paramCompoundWrite;
  }
  
  public CompoundWrite getChildren()
  {
    return children;
  }
  
  public Operation operationForChild(ChildKey paramChildKey)
  {
    if (path.isEmpty())
    {
      paramChildKey = children.childCompoundWrite(new Path(new ChildKey[] { paramChildKey }));
      if (paramChildKey.isEmpty()) {
        return null;
      }
      if (paramChildKey.rootWrite() != null) {
        return new Overwrite(source, Path.getEmptyPath(), paramChildKey.rootWrite());
      }
      return new Merge(source, Path.getEmptyPath(), paramChildKey);
    }
    if (path.getFront().equals(paramChildKey)) {
      return new Merge(source, path.popFront(), children);
    }
    return null;
  }
  
  public String toString()
  {
    return String.format("Merge { path=%s, source=%s, children=%s }", new Object[] { getPath(), getSource(), children });
  }
}
