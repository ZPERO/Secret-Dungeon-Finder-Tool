package com.google.firebase.database.connection;

import java.util.List;

public class RangeMerge
{
  private final List<String> optExclusiveStart;
  private final List<String> optInclusiveEnd;
  private final Object snap;
  
  public RangeMerge(List paramList1, List paramList2, Object paramObject)
  {
    optExclusiveStart = paramList1;
    optInclusiveEnd = paramList2;
    snap = paramObject;
  }
  
  public List getOptExclusiveStart()
  {
    return optExclusiveStart;
  }
  
  public List getOptInclusiveEnd()
  {
    return optInclusiveEnd;
  }
  
  public Object getSnap()
  {
    return snap;
  }
}
