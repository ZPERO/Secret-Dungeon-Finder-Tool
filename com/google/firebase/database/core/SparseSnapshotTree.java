package com.google.firebase.database.core;

import com.google.firebase.database.snapshot.ChildKey;
import com.google.firebase.database.snapshot.ChildrenNode;
import com.google.firebase.database.snapshot.ChildrenNode.ChildVisitor;
import com.google.firebase.database.snapshot.Node;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

class SparseSnapshotTree
{
  private Map<ChildKey, SparseSnapshotTree> children = null;
  private Node value = null;
  
  public SparseSnapshotTree() {}
  
  public void forEachChild(SparseSnapshotChildVisitor paramSparseSnapshotChildVisitor)
  {
    Object localObject = children;
    if (localObject != null)
    {
      localObject = ((Map)localObject).entrySet().iterator();
      while (((Iterator)localObject).hasNext())
      {
        Map.Entry localEntry = (Map.Entry)((Iterator)localObject).next();
        paramSparseSnapshotChildVisitor.visitChild((ChildKey)localEntry.getKey(), (SparseSnapshotTree)localEntry.getValue());
      }
    }
  }
  
  public void forEachTree(final Path paramPath, final SparseSnapshotTreeVisitor paramSparseSnapshotTreeVisitor)
  {
    Node localNode = value;
    if (localNode != null)
    {
      paramSparseSnapshotTreeVisitor.visitTree(paramPath, localNode);
      return;
    }
    forEachChild(new SparseSnapshotChildVisitor()
    {
      public void visitChild(ChildKey paramAnonymousChildKey, SparseSnapshotTree paramAnonymousSparseSnapshotTree)
      {
        paramAnonymousSparseSnapshotTree.forEachTree(paramPath.child(paramAnonymousChildKey), paramSparseSnapshotTreeVisitor);
      }
    });
  }
  
  public boolean forget(final Path paramPath)
  {
    if (paramPath.isEmpty())
    {
      value = null;
      children = null;
      return true;
    }
    Object localObject = value;
    if (localObject != null)
    {
      if (((Node)localObject).isLeafNode()) {
        return false;
      }
      localObject = (ChildrenNode)value;
      value = null;
      ((ChildrenNode)localObject).forEachChild(new ChildrenNode.ChildVisitor()
      {
        public void visitChild(ChildKey paramAnonymousChildKey, Node paramAnonymousNode)
        {
          remember(paramPath.child(paramAnonymousChildKey), paramAnonymousNode);
        }
      });
      return forget(paramPath);
    }
    if (children != null)
    {
      localObject = paramPath.getFront();
      paramPath = paramPath.popFront();
      if ((children.containsKey(localObject)) && (((SparseSnapshotTree)children.get(localObject)).forget(paramPath))) {
        children.remove(localObject);
      }
      if (children.isEmpty())
      {
        children = null;
        return true;
      }
      return false;
    }
    return true;
  }
  
  public void remember(Path paramPath, Node paramNode)
  {
    if (paramPath.isEmpty())
    {
      value = paramNode;
      children = null;
      return;
    }
    Object localObject = value;
    if (localObject != null)
    {
      value = ((Node)localObject).updateChild(paramPath, paramNode);
      return;
    }
    if (children == null) {
      children = new HashMap();
    }
    localObject = paramPath.getFront();
    if (!children.containsKey(localObject)) {
      children.put(localObject, new SparseSnapshotTree());
    }
    ((SparseSnapshotTree)children.get(localObject)).remember(paramPath.popFront(), paramNode);
  }
  
  public static abstract interface SparseSnapshotChildVisitor
  {
    public abstract void visitChild(ChildKey paramChildKey, SparseSnapshotTree paramSparseSnapshotTree);
  }
  
  public static abstract interface SparseSnapshotTreeVisitor
  {
    public abstract void visitTree(Path paramPath, Node paramNode);
  }
}
