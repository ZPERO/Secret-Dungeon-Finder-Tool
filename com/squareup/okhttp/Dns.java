package com.squareup.okhttp;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

public abstract interface Dns
{
  public static final Dns SYSTEM = new Dns()
  {
    public List lookup(String paramAnonymousString)
      throws UnknownHostException
    {
      if (paramAnonymousString != null) {
        return Arrays.asList(InetAddress.getAllByName(paramAnonymousString));
      }
      throw new UnknownHostException("hostname == null");
    }
  };
  
  public abstract List lookup(String paramString)
    throws UnknownHostException;
}