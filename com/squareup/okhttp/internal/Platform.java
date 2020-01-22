package com.squareup.okhttp.internal;

import android.util.Log;
import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.internal.tls.AndroidTrustRootIndex;
import com.squareup.okhttp.internal.tls.RealTrustRootIndex;
import com.squareup.okhttp.internal.tls.TrustRootIndex;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import okio.Buffer;

public class Platform
{
  private static final Platform PLATFORM = ;
  
  public Platform() {}
  
  static byte[] concatLengthPrefixed(List paramList)
  {
    Buffer localBuffer = new Buffer();
    int j = paramList.size();
    int i = 0;
    while (i < j)
    {
      Protocol localProtocol = (Protocol)paramList.get(i);
      if (localProtocol != Protocol.HTTP_1_0)
      {
        localBuffer.writeByte(localProtocol.toString().length());
        localBuffer.writeUtf8(localProtocol.toString());
      }
      i += 1;
    }
    return localBuffer.readByteArray();
  }
  
  private static Platform findPlatform()
  {
    for (;;)
    {
      try
      {
        localObject3 = Class.forName("com.android.org.conscrypt.SSLParametersImpl");
      }
      catch (ClassNotFoundException localClassNotFoundException1)
      {
        Object localObject3;
        Object localObject1;
        Object localObject4;
        OptionalMethod localOptionalMethod1;
        OptionalMethod localOptionalMethod2;
        label172:
        label368:
        label379:
        continue;
      }
      try
      {
        localObject3 = Class.forName("org.apache.harmony.xnet.provider.jsse.SSLParametersImpl");
      }
      catch (ClassNotFoundException localClassNotFoundException2) {}
    }
    localObject1 = Boolean.TYPE;
    localObject4 = null;
    localOptionalMethod1 = new OptionalMethod(null, "setUseSessionTickets", new Class[] { localObject1 });
    localOptionalMethod2 = new OptionalMethod(null, "setHostname", new Class[] { String.class });
    for (;;)
    {
      try
      {
        localObject1 = Class.forName("android.net.TrafficStats");
        localObject5 = ((Class)localObject1).getMethod("tagSocket", new Class[] { Socket.class });
      }
      catch (ClassNotFoundException localClassNotFoundException3)
      {
        Object localObject5;
        Object localObject2;
        Object localObject6;
        continue;
      }
      catch (NoSuchMethodException localNoSuchMethodException1)
      {
        continue;
      }
      try
      {
        localObject2 = ((Class)localObject1).getMethod("untagSocket", new Class[] { Socket.class });
      }
      catch (ClassNotFoundException localClassNotFoundException4)
      {
        continue;
      }
      catch (NoSuchMethodException localNoSuchMethodException2)
      {
        continue;
      }
      try
      {
        Class.forName("android.net.Network");
        localObject1 = new OptionalMethod([B.class, "getAlpnSelectedProtocol", new Class[0]);
      }
      catch (ClassNotFoundException localClassNotFoundException5)
      {
        continue;
      }
      catch (NoSuchMethodException localNoSuchMethodException3)
      {
        continue;
      }
      try
      {
        localObject6 = new OptionalMethod(null, "setAlpnProtocols", new Class[] { [B.class });
        localObject4 = localObject6;
      }
      catch (ClassNotFoundException localClassNotFoundException10) {}catch (NoSuchMethodException localNoSuchMethodException5) {}
    }
    localObject1 = null;
    break label172;
    localObject2 = null;
    localObject1 = null;
    break label172;
    localObject2 = null;
    localObject5 = null;
    localObject1 = null;
    try
    {
      localObject1 = new Android((Class)localObject3, localOptionalMethod1, localOptionalMethod2, (Method)localObject5, (Method)localObject2, (OptionalMethod)localObject1, (OptionalMethod)localObject4);
      return localObject1;
    }
    catch (ClassNotFoundException localClassNotFoundException6)
    {
      for (;;) {}
    }
    try
    {
      localObject1 = Class.forName("sun.security.ssl.SSLContextImpl");
    }
    catch (ClassNotFoundException localClassNotFoundException7)
    {
      break label379;
    }
    try
    {
      localObject2 = Class.forName("org.eclipse.jetty.alpn.ALPN");
      localObject3 = new StringBuilder();
      ((StringBuilder)localObject3).append("org.eclipse.jetty.alpn.ALPN");
      ((StringBuilder)localObject3).append("$Provider");
      localObject5 = Class.forName(((StringBuilder)localObject3).toString());
      localObject3 = new StringBuilder();
      ((StringBuilder)localObject3).append("org.eclipse.jetty.alpn.ALPN");
      ((StringBuilder)localObject3).append("$ClientProvider");
      localObject3 = Class.forName(((StringBuilder)localObject3).toString());
      localObject4 = new StringBuilder();
      ((StringBuilder)localObject4).append("org.eclipse.jetty.alpn.ALPN");
      ((StringBuilder)localObject4).append("$ServerProvider");
      localObject4 = Class.forName(((StringBuilder)localObject4).toString());
      localObject5 = ((Class)localObject2).getMethod("put", new Class[] { SSLSocket.class, localObject5 });
      localObject6 = ((Class)localObject2).getMethod("get", new Class[] { SSLSocket.class });
      localObject2 = ((Class)localObject2).getMethod("remove", new Class[] { SSLSocket.class });
      localObject2 = new JdkWithJettyBootPlatform((Class)localObject1, (Method)localObject5, (Method)localObject6, (Method)localObject2, (Class)localObject3, (Class)localObject4);
      return localObject2;
    }
    catch (ClassNotFoundException localClassNotFoundException9)
    {
      break label368;
    }
    catch (NoSuchMethodException localNoSuchMethodException4)
    {
      break label368;
    }
    try
    {
      localObject1 = new JdkPlatform((Class)localObject1);
      return localObject1;
    }
    catch (ClassNotFoundException localClassNotFoundException8)
    {
      break label379;
    }
    return new Platform();
  }
  
  public static Platform get()
  {
    return PLATFORM;
  }
  
  static Object readFieldOrNull(Object paramObject, Class paramClass, String paramString)
  {
    for (Class localClass = paramObject.getClass(); localClass != Object.class; localClass = localClass.getSuperclass())
    {
      try
      {
        Object localObject = localClass.getDeclaredField(paramString);
        ((Field)localObject).setAccessible(true);
        localObject = ((Field)localObject).get(paramObject);
        if (localObject != null)
        {
          boolean bool = paramClass.isInstance(localObject);
          if (!bool) {
            return null;
          }
          localObject = paramClass.cast(localObject);
          return localObject;
        }
        return null;
      }
      catch (NoSuchFieldException localNoSuchFieldException)
      {
        continue;
      }
      catch (IllegalAccessException paramObject)
      {
        for (;;) {}
      }
      throw new AssertionError();
    }
    if (!paramString.equals("delegate"))
    {
      paramObject = readFieldOrNull(paramObject, Object.class, "delegate");
      if (paramObject != null) {
        return readFieldOrNull(paramObject, paramClass, paramString);
      }
    }
    else
    {
      return null;
    }
    return null;
  }
  
  public void afterHandshake(SSLSocket paramSSLSocket) {}
  
  public void configureTlsExtensions(SSLSocket paramSSLSocket, String paramString, List paramList) {}
  
  public void connectSocket(Socket paramSocket, InetSocketAddress paramInetSocketAddress, int paramInt)
    throws IOException
  {
    paramSocket.connect(paramInetSocketAddress, paramInt);
  }
  
  public String getPrefix()
  {
    return "OkHttp";
  }
  
  public String getSelectedProtocol(SSLSocket paramSSLSocket)
  {
    return null;
  }
  
  public void log(String paramString)
  {
    System.out.println(paramString);
  }
  
  public void logW(String paramString)
  {
    System.out.println(paramString);
  }
  
  public void tagSocket(Socket paramSocket)
    throws SocketException
  {}
  
  public X509TrustManager trustManager(SSLSocketFactory paramSSLSocketFactory)
  {
    return null;
  }
  
  public TrustRootIndex trustRootIndex(X509TrustManager paramX509TrustManager)
  {
    return new RealTrustRootIndex(paramX509TrustManager.getAcceptedIssuers());
  }
  
  public void untagSocket(Socket paramSocket)
    throws SocketException
  {}
  
  private static class Android
    extends Platform
  {
    private static final int MAX_LOG_LENGTH = 4000;
    private final OptionalMethod<Socket> getAlpnSelectedProtocol;
    private final OptionalMethod<Socket> setAlpnProtocols;
    private final OptionalMethod<Socket> setHostname;
    private final OptionalMethod<Socket> setUseSessionTickets;
    private final Class<?> sslParametersClass;
    private final Method trafficStatsTagSocket;
    private final Method trafficStatsUntagSocket;
    
    public Android(Class paramClass, OptionalMethod paramOptionalMethod1, OptionalMethod paramOptionalMethod2, Method paramMethod1, Method paramMethod2, OptionalMethod paramOptionalMethod3, OptionalMethod paramOptionalMethod4)
    {
      sslParametersClass = paramClass;
      setUseSessionTickets = paramOptionalMethod1;
      setHostname = paramOptionalMethod2;
      trafficStatsTagSocket = paramMethod1;
      trafficStatsUntagSocket = paramMethod2;
      getAlpnSelectedProtocol = paramOptionalMethod3;
      setAlpnProtocols = paramOptionalMethod4;
    }
    
    public void configureTlsExtensions(SSLSocket paramSSLSocket, String paramString, List paramList)
    {
      if (paramString != null)
      {
        setUseSessionTickets.invokeOptionalWithoutCheckedException(paramSSLSocket, new Object[] { Boolean.valueOf(true) });
        setHostname.invokeOptionalWithoutCheckedException(paramSSLSocket, new Object[] { paramString });
      }
      paramString = setAlpnProtocols;
      if ((paramString != null) && (paramString.isSupported(paramSSLSocket)))
      {
        paramString = Platform.concatLengthPrefixed(paramList);
        setAlpnProtocols.invokeWithoutCheckedException(paramSSLSocket, new Object[] { paramString });
      }
    }
    
    public void connectSocket(Socket paramSocket, InetSocketAddress paramInetSocketAddress, int paramInt)
      throws IOException
    {
      try
      {
        paramSocket.connect(paramInetSocketAddress, paramInt);
        return;
      }
      catch (SecurityException paramSocket)
      {
        paramInetSocketAddress = new IOException("Exception in connect");
        paramInetSocketAddress.initCause(paramSocket);
        throw paramInetSocketAddress;
      }
      catch (AssertionError paramSocket)
      {
        if (Util.isAndroidGetsocknameError(paramSocket)) {
          throw new IOException(paramSocket);
        }
        throw paramSocket;
      }
    }
    
    public String getSelectedProtocol(SSLSocket paramSSLSocket)
    {
      OptionalMethod localOptionalMethod = getAlpnSelectedProtocol;
      if (localOptionalMethod == null) {
        return null;
      }
      if (!localOptionalMethod.isSupported(paramSSLSocket)) {
        return null;
      }
      paramSSLSocket = (byte[])getAlpnSelectedProtocol.invokeWithoutCheckedException(paramSSLSocket, new Object[0]);
      if (paramSSLSocket != null) {
        return new String(paramSSLSocket, Util.UTF_8);
      }
      return null;
    }
    
    public void log(String paramString)
    {
      int k = paramString.length();
      int i = 0;
      if (i < k)
      {
        int m = paramString.indexOf('\n', i);
        int j = m;
        if (m == -1) {
          j = k;
        }
        for (;;)
        {
          m = Math.min(j, i + 4000);
          Log.d("OkHttp", paramString.substring(i, m));
          if (m >= j)
          {
            i = m + 1;
            break;
          }
          i = m;
        }
      }
    }
    
    public void tagSocket(Socket paramSocket)
      throws SocketException
    {
      Method localMethod = trafficStatsTagSocket;
      if (localMethod == null) {
        return;
      }
      try
      {
        localMethod.invoke(null, new Object[] { paramSocket });
        return;
      }
      catch (InvocationTargetException paramSocket)
      {
        throw new RuntimeException(paramSocket.getCause());
      }
      catch (IllegalAccessException paramSocket)
      {
        throw new RuntimeException(paramSocket);
      }
    }
    
    public X509TrustManager trustManager(SSLSocketFactory paramSSLSocketFactory)
    {
      Object localObject2 = Platform.readFieldOrNull(paramSSLSocketFactory, sslParametersClass, "sslParameters");
      Object localObject1 = localObject2;
      if (localObject2 == null) {}
      try
      {
        localObject1 = Platform.readFieldOrNull(paramSSLSocketFactory, Class.forName("com.google.android.gms.org.conscrypt.SSLParametersImpl", false, paramSSLSocketFactory.getClass().getClassLoader()), "sslParameters");
      }
      catch (ClassNotFoundException paramSSLSocketFactory)
      {
        for (;;) {}
      }
      return null;
      paramSSLSocketFactory = (X509TrustManager)Platform.readFieldOrNull(localObject1, X509TrustManager.class, "x509TrustManager");
      if (paramSSLSocketFactory != null) {
        return paramSSLSocketFactory;
      }
      return (X509TrustManager)Platform.readFieldOrNull(localObject1, X509TrustManager.class, "trustManager");
    }
    
    public TrustRootIndex trustRootIndex(X509TrustManager paramX509TrustManager)
    {
      TrustRootIndex localTrustRootIndex = AndroidTrustRootIndex.get(paramX509TrustManager);
      if (localTrustRootIndex != null) {
        return localTrustRootIndex;
      }
      return super.trustRootIndex(paramX509TrustManager);
    }
    
    public void untagSocket(Socket paramSocket)
      throws SocketException
    {
      Method localMethod = trafficStatsUntagSocket;
      if (localMethod == null) {
        return;
      }
      try
      {
        localMethod.invoke(null, new Object[] { paramSocket });
        return;
      }
      catch (InvocationTargetException paramSocket)
      {
        throw new RuntimeException(paramSocket.getCause());
      }
      catch (IllegalAccessException paramSocket)
      {
        throw new RuntimeException(paramSocket);
      }
    }
  }
  
  private static class JdkPlatform
    extends Platform
  {
    private final Class<?> sslContextClass;
    
    public JdkPlatform(Class paramClass)
    {
      sslContextClass = paramClass;
    }
    
    public X509TrustManager trustManager(SSLSocketFactory paramSSLSocketFactory)
    {
      paramSSLSocketFactory = Platform.readFieldOrNull(paramSSLSocketFactory, sslContextClass, "context");
      if (paramSSLSocketFactory == null) {
        return null;
      }
      return (X509TrustManager)Platform.readFieldOrNull(paramSSLSocketFactory, X509TrustManager.class, "trustManager");
    }
  }
  
  private static class JdkWithJettyBootPlatform
    extends Platform.JdkPlatform
  {
    private final Class<?> clientProviderClass;
    private final Method getMethod;
    private final Method putMethod;
    private final Method removeMethod;
    private final Class<?> serverProviderClass;
    
    public JdkWithJettyBootPlatform(Class paramClass1, Method paramMethod1, Method paramMethod2, Method paramMethod3, Class paramClass2, Class paramClass3)
    {
      super();
      putMethod = paramMethod1;
      getMethod = paramMethod2;
      removeMethod = paramMethod3;
      clientProviderClass = paramClass2;
      serverProviderClass = paramClass3;
    }
    
    public void afterHandshake(SSLSocket paramSSLSocket)
    {
      Method localMethod = removeMethod;
      try
      {
        localMethod.invoke(null, new Object[] { paramSSLSocket });
        return;
      }
      catch (IllegalAccessException paramSSLSocket)
      {
        for (;;) {}
      }
      catch (InvocationTargetException paramSSLSocket)
      {
        for (;;) {}
      }
      throw new AssertionError();
    }
    
    public void configureTlsExtensions(SSLSocket paramSSLSocket, String paramString, List paramList)
    {
      paramString = new ArrayList(paramList.size());
      int j = paramList.size();
      int i = 0;
      Object localObject;
      while (i < j)
      {
        localObject = (Protocol)paramList.get(i);
        if (localObject != Protocol.HTTP_1_0) {
          paramString.add(((Protocol)localObject).toString());
        }
        i += 1;
      }
      try
      {
        paramList = Platform.class.getClassLoader();
        localObject = clientProviderClass;
        Class localClass = serverProviderClass;
        paramString = new Platform.JettyNegoProvider(paramString);
        paramString = Proxy.newProxyInstance(paramList, new Class[] { localObject, localClass }, paramString);
        paramList = putMethod;
        paramList.invoke(null, new Object[] { paramSSLSocket, paramString });
        return;
      }
      catch (IllegalAccessException paramSSLSocket) {}catch (InvocationTargetException paramSSLSocket) {}
      paramSSLSocket = new AssertionError(paramSSLSocket);
      throw paramSSLSocket;
    }
    
    public String getSelectedProtocol(SSLSocket paramSSLSocket)
    {
      Object localObject = getMethod;
      try
      {
        paramSSLSocket = Proxy.getInvocationHandler(((Method)localObject).invoke(null, new Object[] { paramSSLSocket }));
        paramSSLSocket = (Platform.JettyNegoProvider)paramSSLSocket;
        boolean bool = Platform.JettyNegoProvider.access$000(paramSSLSocket);
        if (!bool)
        {
          localObject = Platform.JettyNegoProvider.access$100(paramSSLSocket);
          if (localObject == null)
          {
            paramSSLSocket = Internal.logger;
            localObject = Level.INFO;
            paramSSLSocket.log((Level)localObject, "ALPN callback dropped: SPDY and HTTP/2 are disabled. Is alpn-boot on the boot class path?");
            return null;
          }
        }
        bool = Platform.JettyNegoProvider.access$000(paramSSLSocket);
        if (bool) {
          return null;
        }
        paramSSLSocket = Platform.JettyNegoProvider.access$100(paramSSLSocket);
        return paramSSLSocket;
      }
      catch (InvocationTargetException paramSSLSocket)
      {
        for (;;) {}
      }
      catch (IllegalAccessException paramSSLSocket)
      {
        for (;;) {}
      }
      throw new AssertionError();
    }
  }
  
  private static class JettyNegoProvider
    implements InvocationHandler
  {
    private final List<String> protocols;
    private String selected;
    private boolean unsupported;
    
    public JettyNegoProvider(List paramList)
    {
      protocols = paramList;
    }
    
    public Object invoke(Object paramObject, Method paramMethod, Object[] paramArrayOfObject)
      throws Throwable
    {
      String str = paramMethod.getName();
      Class localClass = paramMethod.getReturnType();
      paramObject = paramArrayOfObject;
      if (paramArrayOfObject == null) {
        paramObject = Util.EMPTY_STRING_ARRAY;
      }
      if ((str.equals("supports")) && (Boolean.TYPE == localClass)) {
        return Boolean.valueOf(true);
      }
      if ((str.equals("unsupported")) && (Void.TYPE == localClass))
      {
        unsupported = true;
        return null;
      }
      if ((str.equals("protocols")) && (paramObject.length == 0)) {
        return protocols;
      }
      if (((str.equals("selectProtocol")) || (str.equals("select"))) && (String.class == localClass) && (paramObject.length == 1) && ((paramObject[0] instanceof List)))
      {
        paramObject = (List)paramObject[0];
        int j = paramObject.size();
        int i = 0;
        while (i < j)
        {
          if (protocols.contains(paramObject.get(i)))
          {
            paramObject = (String)paramObject.get(i);
            selected = paramObject;
            return paramObject;
          }
          i += 1;
        }
        paramObject = (String)protocols.get(0);
        selected = paramObject;
        return paramObject;
      }
      if (((str.equals("protocolSelected")) || (str.equals("selected"))) && (paramObject.length == 1))
      {
        selected = ((String)paramObject[0]);
        return null;
      }
      return paramMethod.invoke(this, paramObject);
    }
  }
}
