package com.google.firebase.database.core.operation;

import com.google.firebase.database.collection.ImmutableSortedMap;
import com.google.firebase.database.core.Path;
import com.google.firebase.database.core.utilities.ImmutableTree;
import com.google.firebase.database.core.utilities.Utilities;
import com.google.firebase.database.snapshot.ChildKey;

public class AckUserWrite
  extends Operation
{
  private final ImmutableTree<Boolean> affectedTree;
  private final boolean revert;
  
  public AckUserWrite(Path paramPath, ImmutableTree paramImmutableTree, boolean paramBoolean)
  {
    super(Operation.OperationType.AckUserWrite, OperationSource.USER, paramPath);
    affectedTree = paramImmutableTree;
    revert = paramBoolean;
  }
  
  public ImmutableTree getAffectedTree()
  {
    return affectedTree;
  }
  
  public boolean isRevert()
  {
    return revert;
  }
  
  public Operation operationForChild(ChildKey paramChildKey)
  {
    if (!path.isEmpty())
    {
      Utilities.hardAssert(path.getFront().equals(paramChildKey), "operationForChild called for unrelated child.");
      return new AckUserWrite(path.popFront(), affectedTree, revert);
    }
    if (affectedTree.getValue() != null)
    {
      Utilities.hardAssert(affectedTree.getChildren().isEmpty(), "affectedTree should not have overlapping affected paths.");
      return this;
    }
    paramChildKey = affectedTree.subtree(new Path(new ChildKey[] { paramChildKey }));
    return new AckUserWrite(Path.getEmptyPath(), paramChildKey, revert);
  }
  
  public String toString()
  {
    return String.format("AckUserWrite { path=%s, revert=%s, affectedTree=%s }", new Object[] { getPath(), Boolean.valueOf(revert), affectedTree });
  }
}
