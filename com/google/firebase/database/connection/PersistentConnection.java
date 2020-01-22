package com.google.firebase.database.connection;

import java.util.List;
import java.util.Map;

public abstract interface PersistentConnection
{
  public abstract void compareAndPut(List paramList, Object paramObject, String paramString, RequestResultCallback paramRequestResultCallback);
  
  public abstract void initialize();
  
  public abstract void interrupt(String paramString);
  
  public abstract boolean isInterrupted(String paramString);
  
  public abstract void listen(List paramList, Map paramMap, ListenHashProvider paramListenHashProvider, Long paramLong, RequestResultCallback paramRequestResultCallback);
  
  public abstract void merge(List paramList, Map paramMap, RequestResultCallback paramRequestResultCallback);
  
  public abstract void onDisconnectCancel(List paramList, RequestResultCallback paramRequestResultCallback);
  
  public abstract void onDisconnectMerge(List paramList, Map paramMap, RequestResultCallback paramRequestResultCallback);
  
  public abstract void onDisconnectPut(List paramList, Object paramObject, RequestResultCallback paramRequestResultCallback);
  
  public abstract void purgeOutstandingWrites();
  
  public abstract void refreshAuthToken();
  
  public abstract void refreshAuthToken(String paramString);
  
  public abstract void resume(String paramString);
  
  public abstract void shutdown();
  
  public abstract void unlisten(List paramList, Map paramMap);
  
  public abstract void update(List paramList, Object paramObject, RequestResultCallback paramRequestResultCallback);
  
  public static abstract interface Delegate
  {
    public abstract void onAuthStatus(boolean paramBoolean);
    
    public abstract void onConnect();
    
    public abstract void onDataUpdate(List paramList, Object paramObject, boolean paramBoolean, Long paramLong);
    
    public abstract void onDisconnect();
    
    public abstract void onRangeMergeUpdate(List paramList1, List paramList2, Long paramLong);
    
    public abstract void onServerInfoUpdate(Map paramMap);
  }
}
