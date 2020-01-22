package com.squareup.okhttp;

import com.squareup.okhttp.internal.Util;
import java.security.Principal;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.List;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;

public final class Handshake
{
  private final String cipherSuite;
  private final List<Certificate> localCertificates;
  private final List<Certificate> peerCertificates;
  
  private Handshake(String paramString, List paramList1, List paramList2)
  {
    cipherSuite = paramString;
    peerCertificates = paramList1;
    localCertificates = paramList2;
  }
  
  public static Handshake get(String paramString, List paramList1, List paramList2)
  {
    if (paramString != null) {
      return new Handshake(paramString, Util.immutableList(paramList1), Util.immutableList(paramList2));
    }
    throw new IllegalArgumentException("cipherSuite == null");
  }
  
  public static Handshake get(SSLSession paramSSLSession)
  {
    String str = paramSSLSession.getCipherSuite();
    if (str != null) {}
    try
    {
      localObject = paramSSLSession.getPeerCertificates();
    }
    catch (SSLPeerUnverifiedException localSSLPeerUnverifiedException)
    {
      Object localObject;
      for (;;) {}
    }
    localObject = null;
    if (localObject != null) {
      localObject = Util.immutableList((Object[])localObject);
    } else {
      localObject = Collections.emptyList();
    }
    paramSSLSession = paramSSLSession.getLocalCertificates();
    if (paramSSLSession != null) {
      paramSSLSession = Util.immutableList(paramSSLSession);
    } else {
      paramSSLSession = Collections.emptyList();
    }
    return new Handshake(str, (List)localObject, paramSSLSession);
    throw new IllegalStateException("cipherSuite == null");
  }
  
  public String cipherSuite()
  {
    return cipherSuite;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof Handshake)) {
      return false;
    }
    paramObject = (Handshake)paramObject;
    return (cipherSuite.equals(cipherSuite)) && (peerCertificates.equals(peerCertificates)) && (localCertificates.equals(localCertificates));
  }
  
  public int hashCode()
  {
    return ((527 + cipherSuite.hashCode()) * 31 + peerCertificates.hashCode()) * 31 + localCertificates.hashCode();
  }
  
  public List localCertificates()
  {
    return localCertificates;
  }
  
  public Principal localPrincipal()
  {
    if (!localCertificates.isEmpty()) {
      return ((X509Certificate)localCertificates.get(0)).getSubjectX500Principal();
    }
    return null;
  }
  
  public List peerCertificates()
  {
    return peerCertificates;
  }
  
  public Principal peerPrincipal()
  {
    if (!peerCertificates.isEmpty()) {
      return ((X509Certificate)peerCertificates.get(0)).getSubjectX500Principal();
    }
    return null;
  }
}
