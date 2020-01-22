package com.google.firebase.database.snapshot;

import com.google.firebase.database.core.Path;
import java.util.Comparator;

public abstract class Index
  implements Comparator<NamedNode>
{
  public Index() {}
  
  public static Index fromQueryDefinition(String paramString)
  {
    if (paramString.equals(".value")) {
      return ValueIndex.getInstance();
    }
    if (paramString.equals(".key")) {
      return KeyIndex.getInstance();
    }
    if (!paramString.equals(".priority")) {
      return new PathIndex(new Path(paramString));
    }
    throw new IllegalStateException("queryDefinition shouldn't ever be .priority since it's the default");
  }
  
  public int compare(NamedNode paramNamedNode1, NamedNode paramNamedNode2, boolean paramBoolean)
  {
    if (paramBoolean) {
      return compare(paramNamedNode2, paramNamedNode1);
    }
    return compare(paramNamedNode1, paramNamedNode2);
  }
  
  public abstract String getQueryDefinition();
  
  public boolean indexedValueChanged(Node paramNode1, Node paramNode2)
  {
    return compare(new NamedNode(ChildKey.getMinName(), paramNode1), new NamedNode(ChildKey.getMinName(), paramNode2)) != 0;
  }
  
  public abstract boolean isDefinedOn(Node paramNode);
  
  public abstract NamedNode makePost(ChildKey paramChildKey, Node paramNode);
  
  public abstract NamedNode maxPost();
  
  public NamedNode minPost()
  {
    return NamedNode.getMinNode();
  }
}
