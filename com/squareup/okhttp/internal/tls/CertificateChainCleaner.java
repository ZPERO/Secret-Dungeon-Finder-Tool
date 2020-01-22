package com.squareup.okhttp.internal.tls;

import java.security.GeneralSecurityException;
import java.security.Principal;
import java.security.cert.X509Certificate;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import javax.net.ssl.SSLPeerUnverifiedException;

public final class CertificateChainCleaner
{
  private static final int MAX_SIGNERS = 9;
  private final TrustRootIndex trustRootIndex;
  
  public CertificateChainCleaner(TrustRootIndex paramTrustRootIndex)
  {
    trustRootIndex = paramTrustRootIndex;
  }
  
  private boolean verifySignature(X509Certificate paramX509Certificate1, X509Certificate paramX509Certificate2)
  {
    if (!paramX509Certificate1.getIssuerDN().equals(paramX509Certificate2.getSubjectDN())) {
      return false;
    }
    try
    {
      paramX509Certificate1.verify(paramX509Certificate2.getPublicKey());
      return true;
    }
    catch (GeneralSecurityException paramX509Certificate1) {}
    return false;
  }
  
  public List clean(List paramList)
    throws SSLPeerUnverifiedException
  {
    ArrayDeque localArrayDeque = new ArrayDeque(paramList);
    paramList = new ArrayList();
    paramList.add(localArrayDeque.removeFirst());
    int i = 0;
    int j = 0;
    while (i < 9)
    {
      localObject1 = (X509Certificate)paramList.get(paramList.size() - 1);
      Object localObject2 = trustRootIndex.findByIssuerAndSignature((X509Certificate)localObject1);
      if (localObject2 != null)
      {
        if ((paramList.size() > 1) || (!((X509Certificate)localObject1).equals(localObject2))) {
          paramList.add(localObject2);
        }
        if (verifySignature((X509Certificate)localObject2, (X509Certificate)localObject2)) {
          return paramList;
        }
        j = 1;
      }
      else
      {
        localObject2 = localArrayDeque.iterator();
        X509Certificate localX509Certificate;
        do
        {
          if (!((Iterator)localObject2).hasNext()) {
            break;
          }
          localX509Certificate = (X509Certificate)((Iterator)localObject2).next();
        } while (!verifySignature((X509Certificate)localObject1, localX509Certificate));
        ((Iterator)localObject2).remove();
        paramList.add(localX509Certificate);
      }
      i += 1;
      continue;
      if (j != 0) {
        return paramList;
      }
      paramList = new StringBuilder();
      paramList.append("Failed to find a trusted cert that signed ");
      paramList.append(localObject1);
      throw new SSLPeerUnverifiedException(paramList.toString());
    }
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("Certificate chain too long: ");
    ((StringBuilder)localObject1).append(paramList);
    paramList = new SSLPeerUnverifiedException(((StringBuilder)localObject1).toString());
    throw paramList;
  }
}
