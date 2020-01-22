package com.squareup.okhttp;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;

public final class Route
{
  final Address address;
  final InetSocketAddress inetSocketAddress;
  final Proxy proxy;
  
  public Route(Address paramAddress, Proxy paramProxy, InetSocketAddress paramInetSocketAddress)
  {
    if (paramAddress != null)
    {
      if (paramProxy != null)
      {
        if (paramInetSocketAddress != null)
        {
          address = paramAddress;
          proxy = paramProxy;
          inetSocketAddress = paramInetSocketAddress;
          return;
        }
        throw new NullPointerException("inetSocketAddress == null");
      }
      throw new NullPointerException("proxy == null");
    }
    throw new NullPointerException("address == null");
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject instanceof Route))
    {
      paramObject = (Route)paramObject;
      if ((address.equals(address)) && (proxy.equals(proxy)) && (inetSocketAddress.equals(inetSocketAddress))) {
        return true;
      }
    }
    return false;
  }
  
  public Address getAddress()
  {
    return address;
  }
  
  public Proxy getProxy()
  {
    return proxy;
  }
  
  public InetSocketAddress getSocketAddress()
  {
    return inetSocketAddress;
  }
  
  public int hashCode()
  {
    return ((527 + address.hashCode()) * 31 + proxy.hashCode()) * 31 + inetSocketAddress.hashCode();
  }
  
  public boolean requiresTunnel()
  {
    return (address.sslSocketFactory != null) && (proxy.type() == Proxy.Type.HTTP);
  }
}
