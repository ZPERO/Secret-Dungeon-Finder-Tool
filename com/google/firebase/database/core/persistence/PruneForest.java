package com.google.firebase.database.core.persistence;

import com.google.firebase.database.collection.ImmutableSortedMap;
import com.google.firebase.database.core.Path;
import com.google.firebase.database.core.utilities.ImmutableTree;
import com.google.firebase.database.core.utilities.ImmutableTree.TreeVisitor;
import com.google.firebase.database.core.utilities.Predicate;
import com.google.firebase.database.snapshot.ChildKey;
import java.util.Iterator;
import java.util.Set;

public class PruneForest
{
  private static final Predicate<Boolean> KEEP_PREDICATE = new Predicate()
  {
    public boolean evaluate(Boolean paramAnonymousBoolean)
    {
      return paramAnonymousBoolean.booleanValue() ^ true;
    }
  };
  private static final ImmutableTree<Boolean> KEEP_TREE = new ImmutableTree(Boolean.valueOf(false));
  private static final Predicate<Boolean> PRUNE_PREDICATE = new Predicate()
  {
    public boolean evaluate(Boolean paramAnonymousBoolean)
    {
      return paramAnonymousBoolean.booleanValue();
    }
  };
  private static final ImmutableTree<Boolean> PRUNE_TREE = new ImmutableTree(Boolean.valueOf(true));
  private final ImmutableTree<Boolean> pruneForest;
  
  public PruneForest()
  {
    pruneForest = ImmutableTree.emptyInstance();
  }
  
  private PruneForest(ImmutableTree paramImmutableTree)
  {
    pruneForest = paramImmutableTree;
  }
  
  private PruneForest doAll(Path paramPath, Set paramSet, ImmutableTree paramImmutableTree)
  {
    ImmutableTree localImmutableTree = pruneForest.subtree(paramPath);
    ImmutableSortedMap localImmutableSortedMap = localImmutableTree.getChildren();
    Iterator localIterator = paramSet.iterator();
    for (paramSet = localImmutableSortedMap; localIterator.hasNext(); paramSet = paramSet.insert((ChildKey)localIterator.next(), paramImmutableTree)) {}
    return new PruneForest(pruneForest.setTree(paramPath, new ImmutableTree((Boolean)localImmutableTree.getValue(), paramSet)));
  }
  
  public boolean affectsPath(Path paramPath)
  {
    return (pruneForest.rootMostValue(paramPath) != null) || (!pruneForest.subtree(paramPath).isEmpty());
  }
  
  public PruneForest child(Path paramPath)
  {
    if (paramPath.isEmpty()) {
      return this;
    }
    return child(paramPath.getFront()).child(paramPath.popFront());
  }
  
  public PruneForest child(ChildKey paramChildKey)
  {
    ImmutableTree localImmutableTree2 = pruneForest.getChild(paramChildKey);
    ImmutableTree localImmutableTree1 = localImmutableTree2;
    if (localImmutableTree2 == null)
    {
      paramChildKey = new ImmutableTree((Boolean)pruneForest.getValue());
    }
    else
    {
      paramChildKey = localImmutableTree1;
      if (localImmutableTree2.getValue() == null)
      {
        paramChildKey = localImmutableTree1;
        if (pruneForest.getValue() != null) {
          paramChildKey = localImmutableTree2.getKey(Path.getEmptyPath(), (Boolean)pruneForest.getValue());
        }
      }
    }
    return new PruneForest(paramChildKey);
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if (!(paramObject instanceof PruneForest)) {
      return false;
    }
    paramObject = (PruneForest)paramObject;
    return pruneForest.equals(pruneForest);
  }
  
  public Object foldKeptNodes(Object paramObject, final ImmutableTree.TreeVisitor paramTreeVisitor)
  {
    pruneForest.fold(paramObject, new ImmutableTree.TreeVisitor()
    {
      public Object onNodeValue(Path paramAnonymousPath, Boolean paramAnonymousBoolean, Object paramAnonymousObject)
      {
        Object localObject = paramAnonymousObject;
        if (!paramAnonymousBoolean.booleanValue()) {
          localObject = paramTreeVisitor.onNodeValue(paramAnonymousPath, null, paramAnonymousObject);
        }
        return localObject;
      }
    });
  }
  
  public int hashCode()
  {
    return pruneForest.hashCode();
  }
  
  public PruneForest keep(Path paramPath)
  {
    if (pruneForest.rootMostValueMatching(paramPath, KEEP_PREDICATE) != null) {
      return this;
    }
    return new PruneForest(pruneForest.setTree(paramPath, KEEP_TREE));
  }
  
  public PruneForest keepAll(Path paramPath, Set paramSet)
  {
    if (pruneForest.rootMostValueMatching(paramPath, KEEP_PREDICATE) != null) {
      return this;
    }
    return doAll(paramPath, paramSet, KEEP_TREE);
  }
  
  public PruneForest prune(Path paramPath)
  {
    if (pruneForest.rootMostValueMatching(paramPath, KEEP_PREDICATE) == null)
    {
      if (pruneForest.rootMostValueMatching(paramPath, PRUNE_PREDICATE) != null) {
        return this;
      }
      return new PruneForest(pruneForest.setTree(paramPath, PRUNE_TREE));
    }
    throw new IllegalArgumentException("Can't prune path that was kept previously!");
  }
  
  public PruneForest pruneAll(Path paramPath, Set paramSet)
  {
    if (pruneForest.rootMostValueMatching(paramPath, KEEP_PREDICATE) == null)
    {
      if (pruneForest.rootMostValueMatching(paramPath, PRUNE_PREDICATE) != null) {
        return this;
      }
      return doAll(paramPath, paramSet, PRUNE_TREE);
    }
    throw new IllegalArgumentException("Can't prune path that was kept previously!");
  }
  
  public boolean prunesAnything()
  {
    return pruneForest.containsMatchingValue(PRUNE_PREDICATE);
  }
  
  public boolean shouldKeep(Path paramPath)
  {
    paramPath = (Boolean)pruneForest.leafMostValue(paramPath);
    return (paramPath != null) && (!paramPath.booleanValue());
  }
  
  public boolean shouldPruneUnkeptDescendants(Path paramPath)
  {
    paramPath = (Boolean)pruneForest.leafMostValue(paramPath);
    return (paramPath != null) && (paramPath.booleanValue());
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("{PruneForest:");
    localStringBuilder.append(pruneForest.toString());
    localStringBuilder.append("}");
    return localStringBuilder.toString();
  }
}
