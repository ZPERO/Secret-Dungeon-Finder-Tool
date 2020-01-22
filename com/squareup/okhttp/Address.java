package com.squareup.okhttp;

import com.squareup.okhttp.internal.Util;
import java.net.Proxy;
import java.net.ProxySelector;
import java.util.List;
import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;

public final class Address
{
  final Authenticator authenticator;
  final CertificatePinner certificatePinner;
  final List<ConnectionSpec> connectionSpecs;
  final Dns dns;
  final HostnameVerifier hostnameVerifier;
  final List<Protocol> protocols;
  final Proxy proxy;
  final ProxySelector proxySelector;
  final SocketFactory socketFactory;
  final SSLSocketFactory sslSocketFactory;
  final HttpUrl url;
  
  public Address(String paramString, int paramInt, Dns paramDns, SocketFactory paramSocketFactory, SSLSocketFactory paramSSLSocketFactory, HostnameVerifier paramHostnameVerifier, CertificatePinner paramCertificatePinner, Authenticator paramAuthenticator, Proxy paramProxy, List paramList1, List paramList2, ProxySelector paramProxySelector)
  {
    HttpUrl.Builder localBuilder = new HttpUrl.Builder();
    String str;
    if (paramSSLSocketFactory != null) {
      str = "https";
    } else {
      str = "http";
    }
    url = localBuilder.scheme(str).host(paramString).port(paramInt).build();
    if (paramDns != null)
    {
      dns = paramDns;
      if (paramSocketFactory != null)
      {
        socketFactory = paramSocketFactory;
        if (paramAuthenticator != null)
        {
          authenticator = paramAuthenticator;
          if (paramList1 != null)
          {
            protocols = Util.immutableList(paramList1);
            if (paramList2 != null)
            {
              connectionSpecs = Util.immutableList(paramList2);
              if (paramProxySelector != null)
              {
                proxySelector = paramProxySelector;
                proxy = paramProxy;
                sslSocketFactory = paramSSLSocketFactory;
                hostnameVerifier = paramHostnameVerifier;
                certificatePinner = paramCertificatePinner;
                return;
              }
              throw new IllegalArgumentException("proxySelector == null");
            }
            throw new IllegalArgumentException("connectionSpecs == null");
          }
          throw new IllegalArgumentException("protocols == null");
        }
        throw new IllegalArgumentException("authenticator == null");
      }
      throw new IllegalArgumentException("socketFactory == null");
    }
    throw new IllegalArgumentException("dns == null");
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject instanceof Address))
    {
      paramObject = (Address)paramObject;
      if ((url.equals(url)) && (dns.equals(dns)) && (authenticator.equals(authenticator)) && (protocols.equals(protocols)) && (connectionSpecs.equals(connectionSpecs)) && (proxySelector.equals(proxySelector)) && (Util.equal(proxy, proxy)) && (Util.equal(sslSocketFactory, sslSocketFactory)) && (Util.equal(hostnameVerifier, hostnameVerifier)) && (Util.equal(certificatePinner, certificatePinner))) {
        return true;
      }
    }
    return false;
  }
  
  public Authenticator getAuthenticator()
  {
    return authenticator;
  }
  
  public CertificatePinner getCertificatePinner()
  {
    return certificatePinner;
  }
  
  public List getConnectionSpecs()
  {
    return connectionSpecs;
  }
  
  public Dns getDns()
  {
    return dns;
  }
  
  public HostnameVerifier getHostnameVerifier()
  {
    return hostnameVerifier;
  }
  
  public List getProtocols()
  {
    return protocols;
  }
  
  public Proxy getProxy()
  {
    return proxy;
  }
  
  public ProxySelector getProxySelector()
  {
    return proxySelector;
  }
  
  public SocketFactory getSocketFactory()
  {
    return socketFactory;
  }
  
  public SSLSocketFactory getSslSocketFactory()
  {
    return sslSocketFactory;
  }
  
  public String getUriHost()
  {
    return url.host();
  }
  
  public int getUriPort()
  {
    return url.port();
  }
  
  public int hashCode()
  {
    int n = url.hashCode();
    int i1 = dns.hashCode();
    int i2 = authenticator.hashCode();
    int i3 = protocols.hashCode();
    int i4 = connectionSpecs.hashCode();
    int i5 = proxySelector.hashCode();
    Object localObject = proxy;
    int m = 0;
    int i;
    if (localObject != null) {
      i = ((Proxy)localObject).hashCode();
    } else {
      i = 0;
    }
    localObject = sslSocketFactory;
    int j;
    if (localObject != null) {
      j = localObject.hashCode();
    } else {
      j = 0;
    }
    localObject = hostnameVerifier;
    int k;
    if (localObject != null) {
      k = localObject.hashCode();
    } else {
      k = 0;
    }
    localObject = certificatePinner;
    if (localObject != null) {
      m = localObject.hashCode();
    }
    return (((((((((527 + n) * 31 + i1) * 31 + i2) * 31 + i3) * 31 + i4) * 31 + i5) * 31 + i) * 31 + j) * 31 + k) * 31 + m;
  }
  
  public HttpUrl url()
  {
    return url;
  }
}
