package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.Address;
import com.squareup.okhttp.Dns;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.Route;
import com.squareup.okhttp.internal.RouteDatabase;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.ProxySelector;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

public final class RouteSelector
{
  private final Address address;
  private List<InetSocketAddress> inetSocketAddresses = Collections.emptyList();
  private InetSocketAddress lastInetSocketAddress;
  private Proxy lastProxy;
  private int nextInetSocketAddressIndex;
  private int nextProxyIndex;
  private final List<Route> postponedRoutes = new ArrayList();
  private List<Proxy> proxies = Collections.emptyList();
  private final RouteDatabase routeDatabase;
  
  public RouteSelector(Address paramAddress, RouteDatabase paramRouteDatabase)
  {
    address = paramAddress;
    routeDatabase = paramRouteDatabase;
    resetNextProxy(paramAddress.url(), paramAddress.getProxy());
  }
  
  static String getHostString(InetSocketAddress paramInetSocketAddress)
  {
    InetAddress localInetAddress = paramInetSocketAddress.getAddress();
    if (localInetAddress == null) {
      return paramInetSocketAddress.getHostName();
    }
    return localInetAddress.getHostAddress();
  }
  
  private boolean hasNextInetSocketAddress()
  {
    return nextInetSocketAddressIndex < inetSocketAddresses.size();
  }
  
  private boolean hasNextPostponed()
  {
    return postponedRoutes.isEmpty() ^ true;
  }
  
  private boolean hasNextProxy()
  {
    return nextProxyIndex < proxies.size();
  }
  
  private InetSocketAddress nextInetSocketAddress()
    throws IOException
  {
    if (hasNextInetSocketAddress())
    {
      localObject = inetSocketAddresses;
      int i = nextInetSocketAddressIndex;
      nextInetSocketAddressIndex = (i + 1);
      return (InetSocketAddress)((List)localObject).get(i);
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("No route to ");
    ((StringBuilder)localObject).append(address.getUriHost());
    ((StringBuilder)localObject).append("; exhausted inet socket addresses: ");
    ((StringBuilder)localObject).append(inetSocketAddresses);
    throw new SocketException(((StringBuilder)localObject).toString());
  }
  
  private Route nextPostponed()
  {
    return (Route)postponedRoutes.remove(0);
  }
  
  private Proxy nextProxy()
    throws IOException
  {
    if (hasNextProxy())
    {
      localObject = proxies;
      int i = nextProxyIndex;
      nextProxyIndex = (i + 1);
      localObject = (Proxy)((List)localObject).get(i);
      resetNextInetSocketAddress((Proxy)localObject);
      return localObject;
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("No route to ");
    ((StringBuilder)localObject).append(address.getUriHost());
    ((StringBuilder)localObject).append("; exhausted proxy configurations: ");
    ((StringBuilder)localObject).append(proxies);
    throw new SocketException(((StringBuilder)localObject).toString());
  }
  
  private void resetNextInetSocketAddress(Proxy paramProxy)
    throws IOException
  {
    inetSocketAddresses = new ArrayList();
    Object localObject;
    int i;
    if ((paramProxy.type() != Proxy.Type.DIRECT) && (paramProxy.type() != Proxy.Type.SOCKS))
    {
      localObject = paramProxy.address();
      if ((localObject instanceof InetSocketAddress))
      {
        InetSocketAddress localInetSocketAddress = (InetSocketAddress)localObject;
        localObject = getHostString(localInetSocketAddress);
        i = localInetSocketAddress.getPort();
      }
      else
      {
        paramProxy = new StringBuilder();
        paramProxy.append("Proxy.address() is not an InetSocketAddress: ");
        paramProxy.append(localObject.getClass());
        throw new IllegalArgumentException(paramProxy.toString());
      }
    }
    else
    {
      localObject = address.getUriHost();
      i = address.getUriPort();
    }
    if ((i >= 1) && (i <= 65535))
    {
      if (paramProxy.type() == Proxy.Type.SOCKS)
      {
        inetSocketAddresses.add(InetSocketAddress.createUnresolved((String)localObject, i));
      }
      else
      {
        paramProxy = address.getDns().lookup((String)localObject);
        int k = paramProxy.size();
        int j = 0;
        while (j < k)
        {
          localObject = (InetAddress)paramProxy.get(j);
          inetSocketAddresses.add(new InetSocketAddress((InetAddress)localObject, i));
          j += 1;
        }
      }
      nextInetSocketAddressIndex = 0;
      return;
    }
    paramProxy = new StringBuilder();
    paramProxy.append("No route to ");
    paramProxy.append((String)localObject);
    paramProxy.append(":");
    paramProxy.append(i);
    paramProxy.append("; port is out of range");
    paramProxy = new SocketException(paramProxy.toString());
    throw paramProxy;
  }
  
  private void resetNextProxy(HttpUrl paramHttpUrl, Proxy paramProxy)
  {
    if (paramProxy != null)
    {
      proxies = Collections.singletonList(paramProxy);
    }
    else
    {
      proxies = new ArrayList();
      paramHttpUrl = address.getProxySelector().select(paramHttpUrl.uri());
      if (paramHttpUrl != null) {
        proxies.addAll(paramHttpUrl);
      }
      proxies.removeAll(Collections.singleton(Proxy.NO_PROXY));
      proxies.add(Proxy.NO_PROXY);
    }
    nextProxyIndex = 0;
  }
  
  public void connectFailed(Route paramRoute, IOException paramIOException)
  {
    if ((paramRoute.getProxy().type() != Proxy.Type.DIRECT) && (address.getProxySelector() != null)) {
      address.getProxySelector().connectFailed(address.url().uri(), paramRoute.getProxy().address(), paramIOException);
    }
    routeDatabase.failed(paramRoute);
  }
  
  public boolean hasNext()
  {
    return (hasNextInetSocketAddress()) || (hasNextProxy()) || (hasNextPostponed());
  }
  
  public Route next()
    throws IOException
  {
    if (!hasNextInetSocketAddress())
    {
      if (!hasNextProxy())
      {
        if (hasNextPostponed()) {
          return nextPostponed();
        }
        throw new NoSuchElementException();
      }
      lastProxy = nextProxy();
    }
    lastInetSocketAddress = nextInetSocketAddress();
    Route localRoute2 = new Route(address, lastProxy, lastInetSocketAddress);
    Route localRoute1 = localRoute2;
    if (routeDatabase.shouldPostpone(localRoute2))
    {
      postponedRoutes.add(localRoute2);
      localRoute1 = next();
    }
    return localRoute1;
  }
}
