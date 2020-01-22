package com.google.firebase.database.core.view.filter;

import com.google.firebase.database.core.Path;
import com.google.firebase.database.core.view.Change;
import com.google.firebase.database.snapshot.ChildKey;
import com.google.firebase.database.snapshot.Index;
import com.google.firebase.database.snapshot.IndexedNode;
import com.google.firebase.database.snapshot.NamedNode;
import com.google.firebase.database.snapshot.Node;
import java.util.Iterator;

public class IndexedFilter
  implements NodeFilter
{
  private final Index index;
  
  public IndexedFilter(Index paramIndex)
  {
    index = paramIndex;
  }
  
  public boolean filtersNodes()
  {
    return false;
  }
  
  public Index getIndex()
  {
    return index;
  }
  
  public NodeFilter getIndexedFilter()
  {
    return this;
  }
  
  public IndexedNode updateChild(IndexedNode paramIndexedNode, ChildKey paramChildKey, Node paramNode, Path paramPath, NodeFilter.CompleteChildSource paramCompleteChildSource, ChildChangeAccumulator paramChildChangeAccumulator)
  {
    paramCompleteChildSource = paramIndexedNode.getNode();
    Node localNode = paramCompleteChildSource.getImmediateChild(paramChildKey);
    if ((localNode.getChild(paramPath).equals(paramNode.getChild(paramPath))) && (localNode.isEmpty() == paramNode.isEmpty())) {
      return paramIndexedNode;
    }
    if (paramChildChangeAccumulator != null) {
      if (paramNode.isEmpty())
      {
        if (paramCompleteChildSource.hasChild(paramChildKey)) {
          paramChildChangeAccumulator.trackChildChange(Change.childRemovedChange(paramChildKey, localNode));
        }
      }
      else if (localNode.isEmpty()) {
        paramChildChangeAccumulator.trackChildChange(Change.childAddedChange(paramChildKey, paramNode));
      } else {
        paramChildChangeAccumulator.trackChildChange(Change.childChangedChange(paramChildKey, paramNode, localNode));
      }
    }
    if ((paramCompleteChildSource.isLeafNode()) && (paramNode.isEmpty())) {
      return paramIndexedNode;
    }
    return paramIndexedNode.updateChild(paramChildKey, paramNode);
  }
  
  public IndexedNode updateFullNode(IndexedNode paramIndexedNode1, IndexedNode paramIndexedNode2, ChildChangeAccumulator paramChildChangeAccumulator)
  {
    if (paramChildChangeAccumulator != null)
    {
      Iterator localIterator = paramIndexedNode1.getNode().iterator();
      NamedNode localNamedNode;
      while (localIterator.hasNext())
      {
        localNamedNode = (NamedNode)localIterator.next();
        if (!paramIndexedNode2.getNode().hasChild(localNamedNode.getName())) {
          paramChildChangeAccumulator.trackChildChange(Change.childRemovedChange(localNamedNode.getName(), localNamedNode.getNode()));
        }
      }
      if (!paramIndexedNode2.getNode().isLeafNode())
      {
        localIterator = paramIndexedNode2.getNode().iterator();
        while (localIterator.hasNext())
        {
          localNamedNode = (NamedNode)localIterator.next();
          if (paramIndexedNode1.getNode().hasChild(localNamedNode.getName()))
          {
            Node localNode = paramIndexedNode1.getNode().getImmediateChild(localNamedNode.getName());
            if (!localNode.equals(localNamedNode.getNode())) {
              paramChildChangeAccumulator.trackChildChange(Change.childChangedChange(localNamedNode.getName(), localNamedNode.getNode(), localNode));
            }
          }
          else
          {
            paramChildChangeAccumulator.trackChildChange(Change.childAddedChange(localNamedNode.getName(), localNamedNode.getNode()));
          }
        }
      }
    }
    return paramIndexedNode2;
  }
  
  public IndexedNode updatePriority(IndexedNode paramIndexedNode, Node paramNode)
  {
    if (paramIndexedNode.getNode().isEmpty()) {
      return paramIndexedNode;
    }
    return paramIndexedNode.updatePriority(paramNode);
  }
}
