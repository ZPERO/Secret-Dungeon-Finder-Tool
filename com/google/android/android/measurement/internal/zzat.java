package com.google.android.android.measurement.internal;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build.VERSION;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

public final class zzat
  extends zzez
{
  private final SSLSocketFactory zzamq;
  
  public zzat(zzfa paramZzfa)
  {
    super(paramZzfa);
    if (Build.VERSION.SDK_INT < 19) {
      paramZzfa = new zzfl();
    } else {
      paramZzfa = null;
    }
    zzamq = paramZzfa;
  }
  
  private static byte[] fetch(HttpURLConnection paramHttpURLConnection)
    throws IOException
  {
    InputStream localInputStream = null;
    Object localObject = localInputStream;
    try
    {
      ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
      localObject = localInputStream;
      localInputStream = paramHttpURLConnection.getInputStream();
      paramHttpURLConnection = localInputStream;
      localObject = paramHttpURLConnection;
      byte[] arrayOfByte = new byte['?'];
      for (;;)
      {
        localObject = paramHttpURLConnection;
        int i = localInputStream.read(arrayOfByte);
        if (i <= 0) {
          break;
        }
        localObject = paramHttpURLConnection;
        localByteArrayOutputStream.write(arrayOfByte, 0, i);
      }
      localObject = paramHttpURLConnection;
      paramHttpURLConnection = localByteArrayOutputStream.toByteArray();
      if (localInputStream != null)
      {
        localInputStream.close();
        return paramHttpURLConnection;
      }
    }
    catch (Throwable paramHttpURLConnection)
    {
      if (localObject != null) {
        ((InputStream)localObject).close();
      }
      throw paramHttpURLConnection;
    }
    return paramHttpURLConnection;
  }
  
  protected final HttpURLConnection createConnection(URL paramURL)
    throws IOException
  {
    paramURL = paramURL.openConnection();
    if ((paramURL instanceof HttpURLConnection))
    {
      SSLSocketFactory localSSLSocketFactory = zzamq;
      if ((localSSLSocketFactory != null) && ((paramURL instanceof HttpsURLConnection))) {
        ((HttpsURLConnection)paramURL).setSSLSocketFactory(localSSLSocketFactory);
      }
      paramURL = (HttpURLConnection)paramURL;
      paramURL.setDefaultUseCaches(false);
      paramURL.setConnectTimeout(60000);
      paramURL.setReadTimeout(61000);
      paramURL.setInstanceFollowRedirects(false);
      paramURL.setDoInput(true);
      return paramURL;
    }
    throw new IOException("Failed to obtain HTTP connection");
  }
  
  public final boolean zzfb()
  {
    zzcl();
    Object localObject = (ConnectivityManager)getContext().getSystemService("connectivity");
    try
    {
      localObject = ((ConnectivityManager)localObject).getActiveNetworkInfo();
    }
    catch (SecurityException localSecurityException)
    {
      for (;;) {}
    }
    localObject = null;
    return (localObject != null) && (((NetworkInfo)localObject).isConnected());
  }
  
  protected final boolean zzgt()
  {
    return false;
  }
}
