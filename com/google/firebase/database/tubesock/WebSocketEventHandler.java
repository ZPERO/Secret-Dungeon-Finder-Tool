package com.google.firebase.database.tubesock;

public abstract interface WebSocketEventHandler
{
  public abstract void onClose();
  
  public abstract void onError(WebSocketException paramWebSocketException);
  
  public abstract void onLogMessage(String paramString);
  
  public abstract void onMessage(WebSocketMessage paramWebSocketMessage);
  
  public abstract void onOpen();
}
