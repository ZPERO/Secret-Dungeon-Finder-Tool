package com.airbnb.lottie.network;

import android.content.Context;
import androidx.core.util.Pair;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieCompositionFactory;
import com.airbnb.lottie.LottieResult;
import com.airbnb.lottie.utils.Logger;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.ZipInputStream;

public class NetworkFetcher
{
  private final Context appContext;
  private final String mMimeType;
  private final NetworkCache networkCache;
  
  private NetworkFetcher(Context paramContext, String paramString)
  {
    appContext = paramContext.getApplicationContext();
    mMimeType = paramString;
    networkCache = new NetworkCache(appContext, paramString);
  }
  
  private LottieComposition fetchFromCache()
  {
    Object localObject2 = networkCache.fetch();
    if (localObject2 == null) {
      return null;
    }
    Object localObject1 = (FileExtension)first;
    localObject2 = (InputStream)second;
    if (localObject1 == FileExtension.HTML) {
      localObject1 = LottieCompositionFactory.fromZipStreamSync(new ZipInputStream((InputStream)localObject2), mMimeType);
    } else {
      localObject1 = LottieCompositionFactory.fromJsonInputStreamSync((InputStream)localObject2, mMimeType);
    }
    if (((LottieResult)localObject1).getValue() != null) {
      return (LottieComposition)((LottieResult)localObject1).getValue();
    }
    return null;
  }
  
  private LottieResult fetchFromNetwork()
  {
    try
    {
      LottieResult localLottieResult = fetchFromNetworkInternal();
      return localLottieResult;
    }
    catch (IOException localIOException)
    {
      return new LottieResult(localIOException);
    }
  }
  
  private LottieResult fetchFromNetworkInternal()
    throws IOException
  {
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("Fetching ");
    ((StringBuilder)localObject1).append(mMimeType);
    Logger.debug(((StringBuilder)localObject1).toString());
    localObject1 = (HttpURLConnection)new URL(mMimeType).openConnection();
    ((HttpURLConnection)localObject1).setRequestMethod("GET");
    LottieResult localLottieResult;
    try
    {
      ((HttpURLConnection)localObject1).connect();
      Object localObject2 = ((HttpURLConnection)localObject1).getErrorStream();
      if (localObject2 == null)
      {
        int i = ((HttpURLConnection)localObject1).getResponseCode();
        if (i == 200)
        {
          localObject2 = getResultFromConnection((HttpURLConnection)localObject1);
          localStringBuilder = new StringBuilder();
          localStringBuilder.append("Completed fetch from network. Success: ");
          localObject3 = ((LottieResult)localObject2).getValue();
          boolean bool;
          if (localObject3 != null) {
            bool = true;
          } else {
            bool = false;
          }
          localStringBuilder.append(bool);
          Logger.debug(localStringBuilder.toString());
          ((HttpURLConnection)localObject1).disconnect();
          return localObject2;
        }
      }
      localObject2 = getErrorFromConnection((HttpURLConnection)localObject1);
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Unable to fetch ");
      Object localObject3 = mMimeType;
      localStringBuilder.append((String)localObject3);
      localStringBuilder.append(". Failed with ");
      localStringBuilder.append(((HttpURLConnection)localObject1).getResponseCode());
      localStringBuilder.append("\n");
      localStringBuilder.append((String)localObject2);
      localObject2 = new LottieResult(new IllegalArgumentException(localStringBuilder.toString()));
      ((HttpURLConnection)localObject1).disconnect();
      return localObject2;
    }
    catch (Throwable localThrowable) {}catch (Exception localException)
    {
      localLottieResult = new LottieResult(localException);
      ((HttpURLConnection)localObject1).disconnect();
      return localLottieResult;
    }
    ((HttpURLConnection)localObject1).disconnect();
    throw localLottieResult;
  }
  
  public static LottieResult fetchSync(Context paramContext, String paramString)
  {
    return new NetworkFetcher(paramContext, paramString).fetchSync();
  }
  
  /* Error */
  private String getErrorFromConnection(HttpURLConnection paramHttpURLConnection)
    throws IOException
  {
    // Byte code:
    //   0: aload_1
    //   1: invokevirtual 137	java/net/HttpURLConnection:getResponseCode	()I
    //   4: pop
    //   5: new 173	java/io/BufferedReader
    //   8: dup
    //   9: new 175	java/io/InputStreamReader
    //   12: dup
    //   13: aload_1
    //   14: invokevirtual 133	java/net/HttpURLConnection:getErrorStream	()Ljava/io/InputStream;
    //   17: invokespecial 176	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;)V
    //   20: invokespecial 179	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   23: astore_1
    //   24: new 94	java/lang/StringBuilder
    //   27: dup
    //   28: invokespecial 95	java/lang/StringBuilder:<init>	()V
    //   31: astore_2
    //   32: aload_1
    //   33: invokevirtual 182	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   36: astore_3
    //   37: aload_3
    //   38: ifnull +19 -> 57
    //   41: aload_2
    //   42: aload_3
    //   43: invokevirtual 101	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   46: pop
    //   47: aload_2
    //   48: bipush 10
    //   50: invokevirtual 185	java/lang/StringBuilder:append	(C)Ljava/lang/StringBuilder;
    //   53: pop
    //   54: goto -22 -> 32
    //   57: aload_1
    //   58: invokevirtual 188	java/io/BufferedReader:close	()V
    //   61: aload_2
    //   62: invokevirtual 105	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   65: areturn
    //   66: astore_2
    //   67: goto +6 -> 73
    //   70: astore_2
    //   71: aload_2
    //   72: athrow
    //   73: aload_1
    //   74: invokevirtual 188	java/io/BufferedReader:close	()V
    //   77: goto +3 -> 80
    //   80: aload_2
    //   81: athrow
    //   82: astore_1
    //   83: goto -22 -> 61
    //   86: astore_1
    //   87: goto -7 -> 80
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	90	0	this	NetworkFetcher
    //   0	90	1	paramHttpURLConnection	HttpURLConnection
    //   31	31	2	localStringBuilder	StringBuilder
    //   66	1	2	localThrowable	Throwable
    //   70	11	2	localException	Exception
    //   36	7	3	str	String
    // Exception table:
    //   from	to	target	type
    //   32	37	66	java/lang/Throwable
    //   41	54	66	java/lang/Throwable
    //   71	73	66	java/lang/Throwable
    //   32	37	70	java/lang/Exception
    //   41	54	70	java/lang/Exception
    //   57	61	82	java/lang/Exception
    //   73	77	86	java/lang/Exception
  }
  
  private LottieResult getResultFromConnection(HttpURLConnection paramHttpURLConnection)
    throws IOException
  {
    Object localObject2 = paramHttpURLConnection.getContentType();
    Object localObject1 = localObject2;
    if (localObject2 == null) {
      localObject1 = "application/json";
    }
    if (((String)localObject1).contains("application/zip"))
    {
      Logger.debug("Handling zip response.");
      localObject1 = FileExtension.HTML;
      localObject2 = LottieCompositionFactory.fromZipStreamSync(new ZipInputStream(new FileInputStream(networkCache.writeTempCacheFile(paramHttpURLConnection.getInputStream(), (FileExtension)localObject1))), mMimeType);
      paramHttpURLConnection = (HttpURLConnection)localObject1;
      localObject1 = localObject2;
    }
    else
    {
      Logger.debug("Received json response.");
      localObject2 = FileExtension.JSON;
      localObject1 = LottieCompositionFactory.fromJsonInputStreamSync(new FileInputStream(new File(networkCache.writeTempCacheFile(paramHttpURLConnection.getInputStream(), (FileExtension)localObject2).getAbsolutePath())), mMimeType);
      paramHttpURLConnection = (HttpURLConnection)localObject2;
    }
    if (((LottieResult)localObject1).getValue() != null) {
      networkCache.renameTempFile(paramHttpURLConnection);
    }
    return localObject1;
  }
  
  public LottieResult fetchSync()
  {
    Object localObject = fetchFromCache();
    if (localObject != null) {
      return new LottieResult(localObject);
    }
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Animation for ");
    ((StringBuilder)localObject).append(mMimeType);
    ((StringBuilder)localObject).append(" not found in cache. Fetching from network.");
    Logger.debug(((StringBuilder)localObject).toString());
    return fetchFromNetwork();
  }
}
