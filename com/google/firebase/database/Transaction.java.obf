package com.google.firebase.database;

import com.google.firebase.database.snapshot.Node;

public class Transaction
{
  public Transaction() {}
  
  public static Result abort()
  {
    return new Result(false, null, null);
  }
  
  public static Result success(MutableData paramMutableData)
  {
    return new Result(true, paramMutableData.getNode(), null);
  }
  
  public static abstract interface Handler
  {
    public abstract Transaction.Result doTransaction(MutableData paramMutableData);
    
    public abstract void onComplete(DatabaseError paramDatabaseError, boolean paramBoolean, DataSnapshot paramDataSnapshot);
  }
  
  public static class Result
  {
    private Node data;
    private boolean success;
    
    private Result(boolean paramBoolean, Node paramNode)
    {
      success = paramBoolean;
      data = paramNode;
    }
    
    public Node getNode()
    {
      return data;
    }
    
    public boolean isSuccess()
    {
      return success;
    }
  }
}
