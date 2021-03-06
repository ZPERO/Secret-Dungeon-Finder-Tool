package com.google.firebase.database.core.view.filter;

import com.google.firebase.database.core.Path;
import com.google.firebase.database.core.view.QueryParams;
import com.google.firebase.database.snapshot.ChildKey;
import com.google.firebase.database.snapshot.EmptyNode;
import com.google.firebase.database.snapshot.Index;
import com.google.firebase.database.snapshot.IndexedNode;
import com.google.firebase.database.snapshot.NamedNode;
import com.google.firebase.database.snapshot.Node;
import com.google.firebase.database.snapshot.PriorityUtilities;
import java.util.Comparator;
import java.util.Iterator;

public class RangedFilter
  implements NodeFilter
{
  private final NamedNode endPost;
  private final Index index;
  private final IndexedFilter indexedFilter;
  private final NamedNode startPost;
  
  public RangedFilter(QueryParams paramQueryParams)
  {
    indexedFilter = new IndexedFilter(paramQueryParams.getIndex());
    index = paramQueryParams.getIndex();
    startPost = getStartPost(paramQueryParams);
    endPost = getEndPost(paramQueryParams);
  }
  
  private static NamedNode getEndPost(QueryParams paramQueryParams)
  {
    if (paramQueryParams.hasEnd())
    {
      ChildKey localChildKey = paramQueryParams.getIndexEndName();
      return paramQueryParams.getIndex().makePost(localChildKey, paramQueryParams.getIndexEndValue());
    }
    return paramQueryParams.getIndex().maxPost();
  }
  
  private static NamedNode getStartPost(QueryParams paramQueryParams)
  {
    if (paramQueryParams.hasStart())
    {
      ChildKey localChildKey = paramQueryParams.getIndexStartName();
      return paramQueryParams.getIndex().makePost(localChildKey, paramQueryParams.getIndexStartValue());
    }
    return paramQueryParams.getIndex().minPost();
  }
  
  public boolean filtersNodes()
  {
    return true;
  }
  
  public NamedNode getEndPost()
  {
    return endPost;
  }
  
  public Index getIndex()
  {
    return index;
  }
  
  public NodeFilter getIndexedFilter()
  {
    return indexedFilter;
  }
  
  public NamedNode getStartPost()
  {
    return startPost;
  }
  
  public boolean matches(NamedNode paramNamedNode)
  {
    return (index.compare(getStartPost(), paramNamedNode) <= 0) && (index.compare(paramNamedNode, getEndPost()) <= 0);
  }
  
  public IndexedNode updateChild(IndexedNode paramIndexedNode, ChildKey paramChildKey, Node paramNode, Path paramPath, NodeFilter.CompleteChildSource paramCompleteChildSource, ChildChangeAccumulator paramChildChangeAccumulator)
  {
    Object localObject = paramNode;
    if (!matches(new NamedNode(paramChildKey, (Node)paramNode))) {
      localObject = EmptyNode.Empty();
    }
    return indexedFilter.updateChild(paramIndexedNode, paramChildKey, (Node)localObject, paramPath, paramCompleteChildSource, paramChildChangeAccumulator);
  }
  
  public IndexedNode updateFullNode(IndexedNode paramIndexedNode1, IndexedNode paramIndexedNode2, ChildChangeAccumulator paramChildChangeAccumulator)
  {
    if (paramIndexedNode2.getNode().isLeafNode())
    {
      paramIndexedNode2 = IndexedNode.from(EmptyNode.Empty(), index);
    }
    else
    {
      Object localObject = paramIndexedNode2.updatePriority(PriorityUtilities.NullPriority());
      Iterator localIterator = paramIndexedNode2.iterator();
      paramIndexedNode2 = (IndexedNode)localObject;
      while (localIterator.hasNext())
      {
        localObject = (NamedNode)localIterator.next();
        if (!matches((NamedNode)localObject)) {
          paramIndexedNode2 = paramIndexedNode2.updateChild(((NamedNode)localObject).getName(), EmptyNode.Empty());
        }
      }
    }
    return indexedFilter.updateFullNode(paramIndexedNode1, paramIndexedNode2, paramChildChangeAccumulator);
  }
  
  public IndexedNode updatePriority(IndexedNode paramIndexedNode, Node paramNode)
  {
    return paramIndexedNode;
  }
}
