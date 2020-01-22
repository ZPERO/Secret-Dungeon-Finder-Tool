package com.google.firebase.database.core.view.filter;

import com.google.firebase.database.core.Path;
import com.google.firebase.database.snapshot.ChildKey;
import com.google.firebase.database.snapshot.Index;
import com.google.firebase.database.snapshot.IndexedNode;
import com.google.firebase.database.snapshot.NamedNode;
import com.google.firebase.database.snapshot.Node;

public abstract interface NodeFilter
{
  public abstract boolean filtersNodes();
  
  public abstract Index getIndex();
  
  public abstract NodeFilter getIndexedFilter();
  
  public abstract IndexedNode updateChild(IndexedNode paramIndexedNode, ChildKey paramChildKey, Node paramNode, Path paramPath, CompleteChildSource paramCompleteChildSource, ChildChangeAccumulator paramChildChangeAccumulator);
  
  public abstract IndexedNode updateFullNode(IndexedNode paramIndexedNode1, IndexedNode paramIndexedNode2, ChildChangeAccumulator paramChildChangeAccumulator);
  
  public abstract IndexedNode updatePriority(IndexedNode paramIndexedNode, Node paramNode);
  
  public static abstract interface CompleteChildSource
  {
    public abstract NamedNode getChildAfterChild(Index paramIndex, NamedNode paramNamedNode, boolean paramBoolean);
    
    public abstract Node getCompleteChild(ChildKey paramChildKey);
  }
}
