package com.squareup.okhttp.internal.tls;

import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.security.auth.x500.X500Principal;

public final class RealTrustRootIndex
  implements TrustRootIndex
{
  private final Map<X500Principal, List<X509Certificate>> subjectToCaCerts;
  
  public RealTrustRootIndex(X509Certificate... paramVarArgs) {}
  
  public X509Certificate findByIssuerAndSignature(X509Certificate paramX509Certificate)
  {
    Object localObject = paramX509Certificate.getIssuerX500Principal();
    localObject = (List)subjectToCaCerts.get(localObject);
    if (localObject == null) {
      return null;
    }
    localObject = ((List)localObject).iterator();
    while (((Iterator)localObject).hasNext())
    {
      X509Certificate localX509Certificate = (X509Certificate)((Iterator)localObject).next();
      PublicKey localPublicKey = localX509Certificate.getPublicKey();
      try
      {
        paramX509Certificate.verify(localPublicKey);
        return localX509Certificate;
      }
      catch (Exception localException)
      {
        for (;;) {}
      }
    }
    return null;
  }
}
