package com.google.firebase.database.connection;

import java.util.Collections;
import java.util.List;

public class CompoundHash
{
  private final List<String> hashes;
  private final List<List<String>> posts;
  
  public CompoundHash(List paramList1, List paramList2)
  {
    if (paramList1.size() == paramList2.size() - 1)
    {
      posts = paramList1;
      hashes = paramList2;
      return;
    }
    throw new IllegalArgumentException("Number of posts need to be n-1 for n hashes in CompoundHash");
  }
  
  public List getHashes()
  {
    return Collections.unmodifiableList(hashes);
  }
  
  public List getPosts()
  {
    return Collections.unmodifiableList(posts);
  }
}
