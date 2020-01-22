package com.google.firebase.database.core.view;

import com.google.firebase.database.snapshot.IndexedNode;
import com.google.firebase.database.snapshot.Node;

public class ViewCache
{
  private final CacheNode eventSnap;
  private final CacheNode serverSnap;
  
  public ViewCache(CacheNode paramCacheNode1, CacheNode paramCacheNode2)
  {
    eventSnap = paramCacheNode1;
    serverSnap = paramCacheNode2;
  }
  
  public Node getCompleteEventSnap()
  {
    if (eventSnap.isFullyInitialized()) {
      return eventSnap.getNode();
    }
    return null;
  }
  
  public Node getCompleteServerSnap()
  {
    if (serverSnap.isFullyInitialized()) {
      return serverSnap.getNode();
    }
    return null;
  }
  
  public CacheNode getEventCache()
  {
    return eventSnap;
  }
  
  public CacheNode getServerCache()
  {
    return serverSnap;
  }
  
  public ViewCache updateEventSnap(IndexedNode paramIndexedNode, boolean paramBoolean1, boolean paramBoolean2)
  {
    return new ViewCache(new CacheNode(paramIndexedNode, paramBoolean1, paramBoolean2), serverSnap);
  }
  
  public ViewCache updateServerSnap(IndexedNode paramIndexedNode, boolean paramBoolean1, boolean paramBoolean2)
  {
    return new ViewCache(eventSnap, new CacheNode(paramIndexedNode, paramBoolean1, paramBoolean2));
  }
}
