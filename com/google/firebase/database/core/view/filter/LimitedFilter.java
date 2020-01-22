package com.google.firebase.database.core.view.filter;

import com.google.firebase.database.core.Path;
import com.google.firebase.database.core.view.Change;
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

public class LimitedFilter
  implements NodeFilter
{
  private final Index index;
  private final int limit;
  private final RangedFilter rangedFilter;
  private final boolean reverse;
  
  public LimitedFilter(QueryParams paramQueryParams)
  {
    rangedFilter = new RangedFilter(paramQueryParams);
    index = paramQueryParams.getIndex();
    limit = paramQueryParams.getLimit();
    reverse = (paramQueryParams.isViewFromLeft() ^ true);
  }
  
  private IndexedNode fullLimitUpdateChild(IndexedNode paramIndexedNode, ChildKey paramChildKey, Node paramNode, NodeFilter.CompleteChildSource paramCompleteChildSource, ChildChangeAccumulator paramChildChangeAccumulator)
  {
    NamedNode localNamedNode2 = new NamedNode(paramChildKey, paramNode);
    NamedNode localNamedNode1;
    if (reverse) {
      localNamedNode1 = paramIndexedNode.getFirstChild();
    } else {
      localNamedNode1 = paramIndexedNode.getLastChild();
    }
    boolean bool = rangedFilter.matches(localNamedNode2);
    if (paramIndexedNode.getNode().hasChild(paramChildKey))
    {
      Node localNode = paramIndexedNode.getNode().getImmediateChild(paramChildKey);
      for (localNamedNode1 = paramCompleteChildSource.getChildAfterChild(index, localNamedNode1, reverse); (localNamedNode1 != null) && ((localNamedNode1.getName().equals(paramChildKey)) || (paramIndexedNode.getNode().hasChild(localNamedNode1.getName()))); localNamedNode1 = paramCompleteChildSource.getChildAfterChild(index, localNamedNode1, reverse)) {}
      int j = 1;
      int i;
      if (localNamedNode1 == null) {
        i = 1;
      } else {
        i = index.compare(localNamedNode1, localNamedNode2, reverse);
      }
      if ((bool) && (!paramNode.isEmpty()) && (i >= 0)) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0)
      {
        if (paramChildChangeAccumulator != null) {
          paramChildChangeAccumulator.trackChildChange(Change.childChangedChange(paramChildKey, paramNode, localNode));
        }
        return paramIndexedNode.updateChild(paramChildKey, paramNode);
      }
      if (paramChildChangeAccumulator != null) {
        paramChildChangeAccumulator.trackChildChange(Change.childRemovedChange(paramChildKey, localNode));
      }
      paramIndexedNode = paramIndexedNode.updateChild(paramChildKey, EmptyNode.Empty());
      if ((localNamedNode1 != null) && (rangedFilter.matches(localNamedNode1))) {
        i = j;
      } else {
        i = 0;
      }
      paramCompleteChildSource = paramIndexedNode;
      if (i != 0)
      {
        if (paramChildChangeAccumulator != null) {
          paramChildChangeAccumulator.trackChildChange(Change.childAddedChange(localNamedNode1.getName(), localNamedNode1.getNode()));
        }
        return paramIndexedNode.updateChild(localNamedNode1.getName(), localNamedNode1.getNode());
      }
    }
    else
    {
      if (paramNode.isEmpty()) {
        return paramIndexedNode;
      }
      paramCompleteChildSource = paramIndexedNode;
      if (bool)
      {
        paramCompleteChildSource = paramIndexedNode;
        if (index.compare(localNamedNode1, localNamedNode2, reverse) >= 0)
        {
          if (paramChildChangeAccumulator != null)
          {
            paramChildChangeAccumulator.trackChildChange(Change.childRemovedChange(localNamedNode1.getName(), localNamedNode1.getNode()));
            paramChildChangeAccumulator.trackChildChange(Change.childAddedChange(paramChildKey, paramNode));
          }
          paramCompleteChildSource = paramIndexedNode.updateChild(paramChildKey, paramNode).updateChild(localNamedNode1.getName(), EmptyNode.Empty());
        }
      }
    }
    return paramCompleteChildSource;
  }
  
  public boolean filtersNodes()
  {
    return true;
  }
  
  public Index getIndex()
  {
    return index;
  }
  
  public NodeFilter getIndexedFilter()
  {
    return rangedFilter.getIndexedFilter();
  }
  
  public IndexedNode updateChild(IndexedNode paramIndexedNode, ChildKey paramChildKey, Node paramNode, Path paramPath, NodeFilter.CompleteChildSource paramCompleteChildSource, ChildChangeAccumulator paramChildChangeAccumulator)
  {
    Object localObject = paramNode;
    if (!rangedFilter.matches(new NamedNode(paramChildKey, (Node)paramNode))) {
      localObject = EmptyNode.Empty();
    }
    if (paramIndexedNode.getNode().getImmediateChild(paramChildKey).equals(localObject)) {
      return paramIndexedNode;
    }
    if (paramIndexedNode.getNode().getChildCount() < limit) {
      return rangedFilter.getIndexedFilter().updateChild(paramIndexedNode, paramChildKey, (Node)localObject, paramPath, paramCompleteChildSource, paramChildChangeAccumulator);
    }
    return fullLimitUpdateChild(paramIndexedNode, paramChildKey, (Node)localObject, paramCompleteChildSource, paramChildChangeAccumulator);
  }
  
  public IndexedNode updateFullNode(IndexedNode paramIndexedNode1, IndexedNode paramIndexedNode2, ChildChangeAccumulator paramChildChangeAccumulator)
  {
    if ((!paramIndexedNode2.getNode().isLeafNode()) && (!paramIndexedNode2.getNode().isEmpty()))
    {
      localObject = paramIndexedNode2.updatePriority(PriorityUtilities.NullPriority());
      Iterator localIterator;
      NamedNode localNamedNode1;
      NamedNode localNamedNode2;
      int i;
      if (reverse)
      {
        localIterator = paramIndexedNode2.reverseIterator();
        localNamedNode1 = rangedFilter.getEndPost();
        localNamedNode2 = rangedFilter.getStartPost();
        i = -1;
      }
      else
      {
        localIterator = paramIndexedNode2.iterator();
        localNamedNode1 = rangedFilter.getStartPost();
        localNamedNode2 = rangedFilter.getEndPost();
        i = 1;
      }
      int m = 0;
      int j = 0;
      paramIndexedNode2 = (IndexedNode)localObject;
      for (;;)
      {
        localObject = paramIndexedNode2;
        if (!localIterator.hasNext()) {
          break;
        }
        localObject = (NamedNode)localIterator.next();
        int k = m;
        if (m == 0)
        {
          k = m;
          if (index.compare(localNamedNode1, localObject) * i <= 0) {
            k = 1;
          }
        }
        if ((k != 0) && (j < limit) && (index.compare(localObject, localNamedNode2) * i <= 0)) {
          m = 1;
        } else {
          m = 0;
        }
        if (m != 0)
        {
          j += 1;
          m = k;
        }
        else
        {
          paramIndexedNode2 = paramIndexedNode2.updateChild(((NamedNode)localObject).getName(), EmptyNode.Empty());
          m = k;
        }
      }
    }
    Object localObject = IndexedNode.from(EmptyNode.Empty(), index);
    return rangedFilter.getIndexedFilter().updateFullNode(paramIndexedNode1, (IndexedNode)localObject, paramChildChangeAccumulator);
  }
  
  public IndexedNode updatePriority(IndexedNode paramIndexedNode, Node paramNode)
  {
    return paramIndexedNode;
  }
}
