package com.squareup.okhttp;

import com.squareup.okhttp.internal.Util;
import java.security.Principal;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.net.ssl.SSLPeerUnverifiedException;
import okio.ByteString;

public final class CertificatePinner
{
  public static final CertificatePinner DEFAULT = new Builder().build();
  private final Map<String, Set<ByteString>> hostnameToPins;
  
  private CertificatePinner(Builder paramBuilder)
  {
    hostnameToPins = Util.immutableMap(hostnameToPins);
  }
  
  public static String pin(Certificate paramCertificate)
  {
    if ((paramCertificate instanceof X509Certificate))
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("sha1/");
      localStringBuilder.append(sha1((X509Certificate)paramCertificate).base64());
      return localStringBuilder.toString();
    }
    throw new IllegalArgumentException("Certificate pinning requires X509 certificates");
  }
  
  private static ByteString sha1(X509Certificate paramX509Certificate)
  {
    return Util.sha1(ByteString.of(paramX509Certificate.getPublicKey().getEncoded()));
  }
  
  public void check(String paramString, List paramList)
    throws SSLPeerUnverifiedException
  {
    Set localSet = findMatchingPins(paramString);
    if (localSet == null) {
      return;
    }
    int k = paramList.size();
    int j = 0;
    int i = 0;
    while (i < k)
    {
      if (localSet.contains(sha1((X509Certificate)paramList.get(i)))) {
        return;
      }
      i += 1;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Certificate pinning failure!");
    localStringBuilder.append("\n  Peer certificate chain:");
    k = paramList.size();
    i = j;
    while (i < k)
    {
      X509Certificate localX509Certificate = (X509Certificate)paramList.get(i);
      localStringBuilder.append("\n    ");
      localStringBuilder.append(pin(localX509Certificate));
      localStringBuilder.append(": ");
      localStringBuilder.append(localX509Certificate.getSubjectDN().getName());
      i += 1;
    }
    localStringBuilder.append("\n  Pinned certificates for ");
    localStringBuilder.append(paramString);
    localStringBuilder.append(":");
    paramString = localSet.iterator();
    while (paramString.hasNext())
    {
      paramList = (ByteString)paramString.next();
      localStringBuilder.append("\n    sha1/");
      localStringBuilder.append(paramList.base64());
    }
    paramString = new SSLPeerUnverifiedException(localStringBuilder.toString());
    throw paramString;
  }
  
  public void check(String paramString, Certificate... paramVarArgs)
    throws SSLPeerUnverifiedException
  {
    check(paramString, Arrays.asList(paramVarArgs));
  }
  
  Set findMatchingPins(String paramString)
  {
    Set localSet = (Set)hostnameToPins.get(paramString);
    int i = paramString.indexOf('.');
    Object localObject;
    if (i != paramString.lastIndexOf('.'))
    {
      localObject = hostnameToPins;
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("*.");
      localStringBuilder.append(paramString.substring(i + 1));
      paramString = (Set)((Map)localObject).get(localStringBuilder.toString());
    }
    else
    {
      paramString = null;
    }
    if ((localSet == null) && (paramString == null)) {
      return null;
    }
    if ((localSet != null) && (paramString != null))
    {
      localObject = new LinkedHashSet();
      ((Set)localObject).addAll(localSet);
      ((Set)localObject).addAll(paramString);
      return localObject;
    }
    if (localSet != null) {
      return localSet;
    }
    return paramString;
  }
  
  public static final class Builder
  {
    private final Map<String, Set<ByteString>> hostnameToPins = new LinkedHashMap();
    
    public Builder() {}
    
    public Builder add(String paramString, String... paramVarArgs)
    {
      if (paramString != null)
      {
        LinkedHashSet localLinkedHashSet = new LinkedHashSet();
        paramString = (Set)hostnameToPins.put(paramString, Collections.unmodifiableSet(localLinkedHashSet));
        if (paramString != null) {
          localLinkedHashSet.addAll(paramString);
        }
        int j = paramVarArgs.length;
        int i = 0;
        for (;;)
        {
          if (i >= j) {
            return this;
          }
          paramString = paramVarArgs[i];
          if (!paramString.startsWith("sha1/")) {
            break label135;
          }
          ByteString localByteString = ByteString.decodeBase64(paramString.substring(5));
          if (localByteString == null) {
            break;
          }
          localLinkedHashSet.add(localByteString);
          i += 1;
        }
        paramVarArgs = new StringBuilder();
        paramVarArgs.append("pins must be base64: ");
        paramVarArgs.append(paramString);
        throw new IllegalArgumentException(paramVarArgs.toString());
        label135:
        paramVarArgs = new StringBuilder();
        paramVarArgs.append("pins must start with 'sha1/': ");
        paramVarArgs.append(paramString);
        throw new IllegalArgumentException(paramVarArgs.toString());
      }
      paramString = new IllegalArgumentException("hostname == null");
      throw paramString;
      return this;
    }
    
    public CertificatePinner build()
    {
      return new CertificatePinner(this, null);
    }
  }
}
