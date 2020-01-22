package com.google.firebase.database.core.utilities;

import com.google.firebase.database.snapshot.ChildKey;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class TreeNode<T>
{
  public Map<ChildKey, TreeNode<T>> children = new HashMap();
  public T value;
  
  public TreeNode() {}
  
  String toString(String paramString)
  {
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append(paramString);
    ((StringBuilder)localObject1).append("<value>: ");
    ((StringBuilder)localObject1).append(value);
    ((StringBuilder)localObject1).append("\n");
    Object localObject2 = ((StringBuilder)localObject1).toString();
    localObject1 = localObject2;
    if (children.isEmpty())
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append((String)localObject2);
      ((StringBuilder)localObject1).append(paramString);
      ((StringBuilder)localObject1).append("<empty>");
      return ((StringBuilder)localObject1).toString();
    }
    localObject2 = children.entrySet().iterator();
    while (((Iterator)localObject2).hasNext())
    {
      Object localObject3 = (Map.Entry)((Iterator)localObject2).next();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append((String)localObject1);
      localStringBuilder.append(paramString);
      localStringBuilder.append(((Map.Entry)localObject3).getKey());
      localStringBuilder.append(":\n");
      localObject1 = (TreeNode)((Map.Entry)localObject3).getValue();
      localObject3 = new StringBuilder();
      ((StringBuilder)localObject3).append(paramString);
      ((StringBuilder)localObject3).append("\t");
      localStringBuilder.append(((TreeNode)localObject1).toString(((StringBuilder)localObject3).toString()));
      localStringBuilder.append("\n");
      localObject1 = localStringBuilder.toString();
    }
    return localObject1;
  }
}
