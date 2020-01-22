package com.google.firebase.database.core.utilities;

import com.google.firebase.database.core.Path;
import com.google.firebase.database.snapshot.ChildKey;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Tree<T>
{
  private ChildKey name;
  private TreeNode<T> node;
  private Tree<T> parent;
  
  public Tree()
  {
    this(null, null, new TreeNode());
  }
  
  public Tree(ChildKey paramChildKey, Tree paramTree, TreeNode paramTreeNode)
  {
    name = paramChildKey;
    parent = paramTree;
    node = paramTreeNode;
  }
  
  private void updateChild(ChildKey paramChildKey, Tree paramTree)
  {
    boolean bool1 = paramTree.isEmpty();
    boolean bool2 = node.children.containsKey(paramChildKey);
    if ((bool1) && (bool2))
    {
      node.children.remove(paramChildKey);
      updateParents();
      return;
    }
    if ((!bool1) && (!bool2))
    {
      node.children.put(paramChildKey, node);
      updateParents();
    }
  }
  
  private void updateParents()
  {
    Tree localTree = parent;
    if (localTree != null) {
      localTree.updateChild(name, this);
    }
  }
  
  public boolean forEachAncestor(TreeFilter paramTreeFilter)
  {
    return forEachAncestor(paramTreeFilter, false);
  }
  
  public boolean forEachAncestor(TreeFilter paramTreeFilter, boolean paramBoolean)
  {
    if (paramBoolean) {
      localTree = this;
    }
    for (Tree localTree = parent; localTree != null; localTree = parent) {
      if (paramTreeFilter.filterTreeNode(localTree)) {
        return true;
      }
    }
    return false;
  }
  
  public void forEachChild(TreeVisitor paramTreeVisitor)
  {
    Object[] arrayOfObject = node.children.entrySet().toArray();
    int i = 0;
    while (i < arrayOfObject.length)
    {
      Map.Entry localEntry = (Map.Entry)arrayOfObject[i];
      paramTreeVisitor.visitTree(new Tree((ChildKey)localEntry.getKey(), this, (TreeNode)localEntry.getValue()));
      i += 1;
    }
  }
  
  public void forEachDescendant(TreeVisitor paramTreeVisitor)
  {
    forEachDescendant(paramTreeVisitor, false, false);
  }
  
  public void forEachDescendant(TreeVisitor paramTreeVisitor, boolean paramBoolean)
  {
    forEachDescendant(paramTreeVisitor, paramBoolean, false);
  }
  
  public void forEachDescendant(final TreeVisitor paramTreeVisitor, boolean paramBoolean1, final boolean paramBoolean2)
  {
    if ((paramBoolean1) && (!paramBoolean2)) {
      paramTreeVisitor.visitTree(this);
    }
    forEachChild(new TreeVisitor()
    {
      public void visitTree(Tree paramAnonymousTree)
      {
        paramAnonymousTree.forEachDescendant(paramTreeVisitor, true, paramBoolean2);
      }
    });
    if ((paramBoolean1) && (paramBoolean2)) {
      paramTreeVisitor.visitTree(this);
    }
  }
  
  public ChildKey getName()
  {
    return name;
  }
  
  public Tree getParent()
  {
    return parent;
  }
  
  public Path getPath()
  {
    Object localObject = parent;
    if (localObject != null) {
      return ((Tree)localObject).getPath().child(name);
    }
    localObject = name;
    if (localObject != null) {
      return new Path(new ChildKey[] { localObject });
    }
    return Path.getEmptyPath();
  }
  
  public Object getValue()
  {
    return node.value;
  }
  
  public boolean hasChildren()
  {
    return node.children.isEmpty() ^ true;
  }
  
  public boolean isEmpty()
  {
    return (node.value == null) && (node.children.isEmpty());
  }
  
  public TreeNode lastNodeOnPath(Path paramPath)
  {
    Object localObject1 = node;
    ChildKey localChildKey = paramPath.getFront();
    Object localObject2 = paramPath;
    for (paramPath = localChildKey; paramPath != null; paramPath = localChildKey)
    {
      if (children.containsKey(paramPath)) {
        paramPath = (TreeNode)children.get(paramPath);
      } else {
        paramPath = null;
      }
      if (paramPath == null) {
        return localObject1;
      }
      localObject1 = ((Path)localObject2).popFront();
      localObject2 = localObject1;
      localChildKey = ((Path)localObject1).getFront();
      localObject1 = paramPath;
    }
    return localObject1;
  }
  
  public void setValue(Object paramObject)
  {
    node.value = paramObject;
    updateParents();
  }
  
  public Tree subTree(Path paramPath)
  {
    Object localObject = paramPath.getFront();
    Tree localTree = this;
    while (localObject != null)
    {
      TreeNode localTreeNode;
      if (node.children.containsKey(localObject)) {
        localTreeNode = (TreeNode)node.children.get(localObject);
      } else {
        localTreeNode = new TreeNode();
      }
      localTree = new Tree((ChildKey)localObject, localTree, localTreeNode);
      localObject = paramPath.popFront();
      paramPath = (Path)localObject;
      localObject = ((Path)localObject).getFront();
    }
    return localTree;
  }
  
  public String toString()
  {
    return toString("");
  }
  
  String toString(String paramString)
  {
    Object localObject = name;
    if (localObject == null) {
      localObject = "<anon>";
    } else {
      localObject = ((ChildKey)localObject).asString();
    }
    StringBuilder localStringBuilder1 = new StringBuilder();
    localStringBuilder1.append(paramString);
    localStringBuilder1.append((String)localObject);
    localStringBuilder1.append("\n");
    localObject = node;
    StringBuilder localStringBuilder2 = new StringBuilder();
    localStringBuilder2.append(paramString);
    localStringBuilder2.append("\t");
    localStringBuilder1.append(((TreeNode)localObject).toString(localStringBuilder2.toString()));
    return localStringBuilder1.toString();
  }
  
  public static abstract interface TreeFilter<T>
  {
    public abstract boolean filterTreeNode(Tree paramTree);
  }
  
  public static abstract interface TreeVisitor<T>
  {
    public abstract void visitTree(Tree paramTree);
  }
}
