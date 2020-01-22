package com.squareup.okhttp.internal.tls;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import javax.net.ssl.X509TrustManager;

public final class AndroidTrustRootIndex
  implements TrustRootIndex
{
  private final Method findByIssuerAndSignatureMethod;
  private final X509TrustManager trustManager;
  
  public AndroidTrustRootIndex(X509TrustManager paramX509TrustManager, Method paramMethod)
  {
    findByIssuerAndSignatureMethod = paramMethod;
    trustManager = paramX509TrustManager;
  }
  
  public static TrustRootIndex get(X509TrustManager paramX509TrustManager)
  {
    try
    {
      Object localObject = paramX509TrustManager.getClass();
      localObject = ((Class)localObject).getDeclaredMethod("findTrustAnchorByIssuerAndSignature", new Class[] { X509Certificate.class });
      ((Method)localObject).setAccessible(true);
      paramX509TrustManager = new AndroidTrustRootIndex(paramX509TrustManager, (Method)localObject);
      return paramX509TrustManager;
    }
    catch (NoSuchMethodException paramX509TrustManager)
    {
      for (;;) {}
    }
    return null;
  }
  
  public X509Certificate findByIssuerAndSignature(X509Certificate paramX509Certificate)
  {
    Method localMethod = findByIssuerAndSignatureMethod;
    X509TrustManager localX509TrustManager = trustManager;
    try
    {
      paramX509Certificate = localMethod.invoke(localX509TrustManager, new Object[] { paramX509Certificate });
      paramX509Certificate = (TrustAnchor)paramX509Certificate;
      if (paramX509Certificate == null) {
        break label53;
      }
      paramX509Certificate = paramX509Certificate.getTrustedCert();
      return paramX509Certificate;
    }
    catch (IllegalAccessException paramX509Certificate)
    {
      for (;;) {}
    }
    catch (InvocationTargetException paramX509Certificate) {}
    throw new AssertionError();
    label53:
    return null;
  }
}
