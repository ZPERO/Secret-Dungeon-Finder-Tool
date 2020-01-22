package com.squareup.okhttp;

import java.net.Socket;

public abstract interface Connection
{
  public abstract Handshake getHandshake();
  
  public abstract Protocol getProtocol();
  
  public abstract Route getRoute();
  
  public abstract Socket getSocket();
}
