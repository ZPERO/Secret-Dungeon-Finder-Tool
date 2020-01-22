package com.squareup.okhttp.internal.io;

import com.squareup.okhttp.Address;
import com.squareup.okhttp.Connection;
import com.squareup.okhttp.ConnectionSpec;
import com.squareup.okhttp.Handshake;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Request.Builder;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.Response.Builder;
import com.squareup.okhttp.Route;
import com.squareup.okhttp.internal.ConnectionSpecSelector;
import com.squareup.okhttp.internal.Platform;
import com.squareup.okhttp.internal.Util;
import com.squareup.okhttp.internal.Version;
import com.squareup.okhttp.internal.framed.FramedConnection;
import com.squareup.okhttp.internal.framed.FramedConnection.Builder;
import com.squareup.okhttp.internal.http.Http1xStream;
import com.squareup.okhttp.internal.http.OkHeaders;
import com.squareup.okhttp.internal.http.RouteException;
import com.squareup.okhttp.internal.http.StreamAllocation;
import com.squareup.okhttp.internal.tls.TrustRootIndex;
import java.io.IOException;
import java.lang.ref.Reference;
import java.net.ConnectException;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.Socket;
import java.net.UnknownServiceException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Sink;
import okio.Source;
import okio.Timeout;

public final class RealConnection
  implements Connection
{
  private static SSLSocketFactory lastSslSocketFactory;
  private static TrustRootIndex lastTrustRootIndex;
  public final List<Reference<StreamAllocation>> allocations = new ArrayList();
  public volatile FramedConnection framedConnection;
  private Handshake handshake;
  public long idleAtNanos = Long.MAX_VALUE;
  public boolean noNewStreams;
  private Protocol protocol;
  private Socket rawSocket;
  private final Route route;
  public BufferedSink sink;
  public Socket socket;
  public BufferedSource source;
  public int streamCount;
  
  public RealConnection(Route paramRoute)
  {
    route = paramRoute;
  }
  
  private void connectSocket(int paramInt1, int paramInt2, int paramInt3, ConnectionSpecSelector paramConnectionSpecSelector)
    throws IOException
  {
    rawSocket.setSoTimeout(paramInt2);
    try
    {
      Platform localPlatform = Platform.get();
      Socket localSocket = rawSocket;
      Route localRoute = route;
      localPlatform.connectSocket(localSocket, localRoute.getSocketAddress(), paramInt1);
      source = Okio.buffer(Okio.source(rawSocket));
      sink = Okio.buffer(Okio.sink(rawSocket));
      if (route.getAddress().getSslSocketFactory() != null)
      {
        connectTls(paramInt2, paramInt3, paramConnectionSpecSelector);
      }
      else
      {
        protocol = Protocol.HTTP_1_1;
        socket = rawSocket;
      }
      if ((protocol != Protocol.SPDY_3) && (protocol != Protocol.HTTP_2)) {
        return;
      }
      socket.setSoTimeout(0);
      paramConnectionSpecSelector = new FramedConnection.Builder(true).socket(socket, route.getAddress().url().host(), source, sink).protocol(protocol).build();
      paramConnectionSpecSelector.sendConnectionPreface();
      framedConnection = paramConnectionSpecSelector;
      return;
    }
    catch (ConnectException paramConnectionSpecSelector)
    {
      for (;;) {}
    }
    paramConnectionSpecSelector = new StringBuilder();
    paramConnectionSpecSelector.append("Failed to connect to ");
    paramConnectionSpecSelector.append(route.getSocketAddress());
    throw new ConnectException(paramConnectionSpecSelector.toString());
  }
  
  /* Error */
  private void connectTls(int paramInt1, int paramInt2, ConnectionSpecSelector paramConnectionSpecSelector)
    throws IOException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 50	com/squareup/okhttp/internal/io/RealConnection:route	Lcom/squareup/okhttp/Route;
    //   4: invokevirtual 187	com/squareup/okhttp/Route:requiresTunnel	()Z
    //   7: ifeq +9 -> 16
    //   10: aload_0
    //   11: iload_1
    //   12: iload_2
    //   13: invokespecial 191	com/squareup/okhttp/internal/io/RealConnection:createTunnel	(II)V
    //   16: aload_0
    //   17: getfield 50	com/squareup/okhttp/internal/io/RealConnection:route	Lcom/squareup/okhttp/Route;
    //   20: invokevirtual 103	com/squareup/okhttp/Route:getAddress	()Lcom/squareup/okhttp/Address;
    //   23: astore 9
    //   25: aload 9
    //   27: invokevirtual 109	com/squareup/okhttp/Address:getSslSocketFactory	()Ljavax/net/ssl/SSLSocketFactory;
    //   30: astore 10
    //   32: aconst_null
    //   33: astore 6
    //   35: aconst_null
    //   36: astore 8
    //   38: aconst_null
    //   39: astore 7
    //   41: aload 6
    //   43: astore 5
    //   45: aload_0
    //   46: getfield 59	com/squareup/okhttp/internal/io/RealConnection:rawSocket	Ljava/net/Socket;
    //   49: astore 11
    //   51: aload 6
    //   53: astore 5
    //   55: aload 10
    //   57: aload 11
    //   59: aload 9
    //   61: invokevirtual 194	com/squareup/okhttp/Address:getUriHost	()Ljava/lang/String;
    //   64: aload 9
    //   66: invokevirtual 198	com/squareup/okhttp/Address:getUriPort	()I
    //   69: iconst_1
    //   70: invokevirtual 204	javax/net/ssl/SSLSocketFactory:createSocket	(Ljava/net/Socket;Ljava/lang/String;IZ)Ljava/net/Socket;
    //   73: astore 10
    //   75: aload 6
    //   77: astore 5
    //   79: aload 10
    //   81: checkcast 206	javax/net/ssl/SSLSocket
    //   84: astore 6
    //   86: aload 6
    //   88: checkcast 206	javax/net/ssl/SSLSocket
    //   91: astore 5
    //   93: aload_3
    //   94: aload 5
    //   96: invokevirtual 212	com/squareup/okhttp/internal/ConnectionSpecSelector:configureSecureSocket	(Ljavax/net/ssl/SSLSocket;)Lcom/squareup/okhttp/ConnectionSpec;
    //   99: astore_3
    //   100: aload_3
    //   101: invokevirtual 217	com/squareup/okhttp/ConnectionSpec:supportsTlsExtensions	()Z
    //   104: istore 4
    //   106: iload 4
    //   108: ifeq +40 -> 148
    //   111: invokestatic 71	com/squareup/okhttp/internal/Platform:get	()Lcom/squareup/okhttp/internal/Platform;
    //   114: astore 5
    //   116: aload 9
    //   118: invokevirtual 194	com/squareup/okhttp/Address:getUriHost	()Ljava/lang/String;
    //   121: astore 8
    //   123: aload 9
    //   125: invokevirtual 221	com/squareup/okhttp/Address:getProtocols	()Ljava/util/List;
    //   128: astore 10
    //   130: aload 6
    //   132: checkcast 206	javax/net/ssl/SSLSocket
    //   135: astore 11
    //   137: aload 5
    //   139: aload 11
    //   141: aload 8
    //   143: aload 10
    //   145: invokevirtual 225	com/squareup/okhttp/internal/Platform:configureTlsExtensions	(Ljavax/net/ssl/SSLSocket;Ljava/lang/String;Ljava/util/List;)V
    //   148: aload 6
    //   150: checkcast 206	javax/net/ssl/SSLSocket
    //   153: astore 5
    //   155: aload 5
    //   157: invokevirtual 228	javax/net/ssl/SSLSocket:startHandshake	()V
    //   160: aload 6
    //   162: checkcast 206	javax/net/ssl/SSLSocket
    //   165: astore 5
    //   167: aload 5
    //   169: invokevirtual 232	javax/net/ssl/SSLSocket:getSession	()Ljavax/net/ssl/SSLSession;
    //   172: invokestatic 237	com/squareup/okhttp/Handshake:get	(Ljavax/net/ssl/SSLSession;)Lcom/squareup/okhttp/Handshake;
    //   175: astore 5
    //   177: aload 9
    //   179: invokevirtual 241	com/squareup/okhttp/Address:getHostnameVerifier	()Ljavax/net/ssl/HostnameVerifier;
    //   182: astore 8
    //   184: aload 9
    //   186: invokevirtual 194	com/squareup/okhttp/Address:getUriHost	()Ljava/lang/String;
    //   189: astore 10
    //   191: aload 6
    //   193: checkcast 206	javax/net/ssl/SSLSocket
    //   196: astore 11
    //   198: aload 8
    //   200: aload 10
    //   202: aload 11
    //   204: invokevirtual 232	javax/net/ssl/SSLSocket:getSession	()Ljavax/net/ssl/SSLSession;
    //   207: invokeinterface 247 3 0
    //   212: istore 4
    //   214: iload 4
    //   216: ifeq +200 -> 416
    //   219: aload 9
    //   221: invokevirtual 251	com/squareup/okhttp/Address:getCertificatePinner	()Lcom/squareup/okhttp/CertificatePinner;
    //   224: astore 8
    //   226: getstatic 257	com/squareup/okhttp/CertificatePinner:DEFAULT	Lcom/squareup/okhttp/CertificatePinner;
    //   229: astore 10
    //   231: aload 8
    //   233: aload 10
    //   235: if_acmpeq +47 -> 282
    //   238: aload 9
    //   240: invokevirtual 109	com/squareup/okhttp/Address:getSslSocketFactory	()Ljavax/net/ssl/SSLSocketFactory;
    //   243: invokestatic 261	com/squareup/okhttp/internal/io/RealConnection:trustRootIndex	(Ljavax/net/ssl/SSLSocketFactory;)Lcom/squareup/okhttp/internal/tls/TrustRootIndex;
    //   246: astore 8
    //   248: new 263	com/squareup/okhttp/internal/tls/CertificateChainCleaner
    //   251: dup
    //   252: aload 8
    //   254: invokespecial 266	com/squareup/okhttp/internal/tls/CertificateChainCleaner:<init>	(Lcom/squareup/okhttp/internal/tls/TrustRootIndex;)V
    //   257: aload 5
    //   259: invokevirtual 269	com/squareup/okhttp/Handshake:peerCertificates	()Ljava/util/List;
    //   262: invokevirtual 273	com/squareup/okhttp/internal/tls/CertificateChainCleaner:clean	(Ljava/util/List;)Ljava/util/List;
    //   265: astore 8
    //   267: aload 9
    //   269: invokevirtual 251	com/squareup/okhttp/Address:getCertificatePinner	()Lcom/squareup/okhttp/CertificatePinner;
    //   272: aload 9
    //   274: invokevirtual 194	com/squareup/okhttp/Address:getUriHost	()Ljava/lang/String;
    //   277: aload 8
    //   279: invokevirtual 277	com/squareup/okhttp/CertificatePinner:check	(Ljava/lang/String;Ljava/util/List;)V
    //   282: aload_3
    //   283: invokevirtual 217	com/squareup/okhttp/ConnectionSpec:supportsTlsExtensions	()Z
    //   286: istore 4
    //   288: aload 7
    //   290: astore_3
    //   291: iload 4
    //   293: ifeq +21 -> 314
    //   296: invokestatic 71	com/squareup/okhttp/internal/Platform:get	()Lcom/squareup/okhttp/internal/Platform;
    //   299: astore_3
    //   300: aload 6
    //   302: checkcast 206	javax/net/ssl/SSLSocket
    //   305: astore 7
    //   307: aload_3
    //   308: aload 7
    //   310: invokevirtual 281	com/squareup/okhttp/internal/Platform:getSelectedProtocol	(Ljavax/net/ssl/SSLSocket;)Ljava/lang/String;
    //   313: astore_3
    //   314: aload_0
    //   315: aload 6
    //   317: checkcast 61	java/net/Socket
    //   320: putfield 122	com/squareup/okhttp/internal/io/RealConnection:socket	Ljava/net/Socket;
    //   323: aload_0
    //   324: getfield 122	com/squareup/okhttp/internal/io/RealConnection:socket	Ljava/net/Socket;
    //   327: astore 7
    //   329: aload 7
    //   331: invokestatic 85	okio/Okio:source	(Ljava/net/Socket;)Lokio/Source;
    //   334: invokestatic 89	okio/Okio:buffer	(Lokio/Source;)Lokio/BufferedSource;
    //   337: astore 7
    //   339: aload_0
    //   340: aload 7
    //   342: putfield 91	com/squareup/okhttp/internal/io/RealConnection:source	Lokio/BufferedSource;
    //   345: aload_0
    //   346: getfield 122	com/squareup/okhttp/internal/io/RealConnection:socket	Ljava/net/Socket;
    //   349: astore 7
    //   351: aload 7
    //   353: invokestatic 94	okio/Okio:sink	(Ljava/net/Socket;)Lokio/Sink;
    //   356: invokestatic 97	okio/Okio:buffer	(Lokio/Sink;)Lokio/BufferedSink;
    //   359: astore 7
    //   361: aload_0
    //   362: aload 7
    //   364: putfield 99	com/squareup/okhttp/internal/io/RealConnection:sink	Lokio/BufferedSink;
    //   367: aload_0
    //   368: aload 5
    //   370: putfield 283	com/squareup/okhttp/internal/io/RealConnection:handshake	Lcom/squareup/okhttp/Handshake;
    //   373: aload_3
    //   374: ifnull +16 -> 390
    //   377: aload_3
    //   378: checkcast 285	java/lang/String
    //   381: astore_3
    //   382: aload_3
    //   383: invokestatic 288	com/squareup/okhttp/Protocol:get	(Ljava/lang/String;)Lcom/squareup/okhttp/Protocol;
    //   386: astore_3
    //   387: goto +7 -> 394
    //   390: getstatic 118	com/squareup/okhttp/Protocol:HTTP_1_1	Lcom/squareup/okhttp/Protocol;
    //   393: astore_3
    //   394: aload_0
    //   395: aload_3
    //   396: putfield 120	com/squareup/okhttp/internal/io/RealConnection:protocol	Lcom/squareup/okhttp/Protocol;
    //   399: aload 6
    //   401: ifnull +236 -> 637
    //   404: invokestatic 71	com/squareup/okhttp/internal/Platform:get	()Lcom/squareup/okhttp/internal/Platform;
    //   407: aload 6
    //   409: checkcast 206	javax/net/ssl/SSLSocket
    //   412: invokevirtual 292	com/squareup/okhttp/internal/Platform:afterHandshake	(Ljavax/net/ssl/SSLSocket;)V
    //   415: return
    //   416: aload 5
    //   418: invokevirtual 269	com/squareup/okhttp/Handshake:peerCertificates	()Ljava/util/List;
    //   421: iconst_0
    //   422: invokeinterface 297 2 0
    //   427: astore_3
    //   428: aload_3
    //   429: checkcast 299	java/security/cert/X509Certificate
    //   432: astore_3
    //   433: new 162	java/lang/StringBuilder
    //   436: dup
    //   437: invokespecial 163	java/lang/StringBuilder:<init>	()V
    //   440: astore 5
    //   442: aload 5
    //   444: ldc_w 301
    //   447: invokevirtual 169	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   450: pop
    //   451: aload 5
    //   453: aload 9
    //   455: invokevirtual 194	com/squareup/okhttp/Address:getUriHost	()Ljava/lang/String;
    //   458: invokevirtual 169	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   461: pop
    //   462: aload 5
    //   464: ldc_w 303
    //   467: invokevirtual 169	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   470: pop
    //   471: aload 5
    //   473: ldc_w 305
    //   476: invokevirtual 169	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   479: pop
    //   480: aload 5
    //   482: aload_3
    //   483: invokestatic 309	com/squareup/okhttp/CertificatePinner:pin	(Ljava/security/cert/Certificate;)Ljava/lang/String;
    //   486: invokevirtual 169	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   489: pop
    //   490: aload 5
    //   492: ldc_w 311
    //   495: invokevirtual 169	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   498: pop
    //   499: aload 5
    //   501: aload_3
    //   502: invokevirtual 315	java/security/cert/X509Certificate:getSubjectDN	()Ljava/security/Principal;
    //   505: invokeinterface 320 1 0
    //   510: invokevirtual 169	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   513: pop
    //   514: aload 5
    //   516: ldc_w 322
    //   519: invokevirtual 169	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   522: pop
    //   523: aload 5
    //   525: aload_3
    //   526: invokestatic 328	com/squareup/okhttp/internal/tls/OkHostnameVerifier:allSubjectAltNames	(Ljava/security/cert/X509Certificate;)Ljava/util/List;
    //   529: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   532: pop
    //   533: new 330	javax/net/ssl/SSLPeerUnverifiedException
    //   536: dup
    //   537: aload 5
    //   539: invokevirtual 175	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   542: invokespecial 331	javax/net/ssl/SSLPeerUnverifiedException:<init>	(Ljava/lang/String;)V
    //   545: astore_3
    //   546: aload_3
    //   547: athrow
    //   548: astore_3
    //   549: goto +62 -> 611
    //   552: astore 5
    //   554: aload 6
    //   556: astore_3
    //   557: aload 5
    //   559: astore 6
    //   561: goto +16 -> 577
    //   564: astore_3
    //   565: aload 5
    //   567: astore 6
    //   569: goto +42 -> 611
    //   572: astore 6
    //   574: aload 8
    //   576: astore_3
    //   577: aload_3
    //   578: astore 5
    //   580: aload 6
    //   582: invokestatic 337	com/squareup/okhttp/internal/Util:isAndroidGetsocknameError	(Ljava/lang/AssertionError;)Z
    //   585: istore 4
    //   587: iload 4
    //   589: ifeq +16 -> 605
    //   592: aload_3
    //   593: astore 5
    //   595: new 55	java/io/IOException
    //   598: dup
    //   599: aload 6
    //   601: invokespecial 340	java/io/IOException:<init>	(Ljava/lang/Throwable;)V
    //   604: athrow
    //   605: aload_3
    //   606: astore 5
    //   608: aload 6
    //   610: athrow
    //   611: aload 6
    //   613: ifnull +14 -> 627
    //   616: invokestatic 71	com/squareup/okhttp/internal/Platform:get	()Lcom/squareup/okhttp/internal/Platform;
    //   619: aload 6
    //   621: checkcast 206	javax/net/ssl/SSLSocket
    //   624: invokevirtual 292	com/squareup/okhttp/internal/Platform:afterHandshake	(Ljavax/net/ssl/SSLSocket;)V
    //   627: aload 6
    //   629: checkcast 61	java/net/Socket
    //   632: invokestatic 344	com/squareup/okhttp/internal/Util:closeQuietly	(Ljava/net/Socket;)V
    //   635: aload_3
    //   636: athrow
    //   637: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	638	0	this	RealConnection
    //   0	638	1	paramInt1	int
    //   0	638	2	paramInt2	int
    //   0	638	3	paramConnectionSpecSelector	ConnectionSpecSelector
    //   104	484	4	bool	boolean
    //   43	495	5	localObject1	Object
    //   552	14	5	localAssertionError1	AssertionError
    //   578	29	5	localConnectionSpecSelector	ConnectionSpecSelector
    //   33	535	6	localObject2	Object
    //   572	56	6	localAssertionError2	AssertionError
    //   39	324	7	localObject3	Object
    //   36	539	8	localObject4	Object
    //   23	431	9	localAddress	Address
    //   30	204	10	localObject5	Object
    //   49	154	11	localObject6	Object
    // Exception table:
    //   from	to	target	type
    //   93	106	548	java/lang/Throwable
    //   111	130	548	java/lang/Throwable
    //   130	137	548	java/lang/Throwable
    //   137	148	548	java/lang/Throwable
    //   148	155	548	java/lang/Throwable
    //   155	160	548	java/lang/Throwable
    //   160	167	548	java/lang/Throwable
    //   167	191	548	java/lang/Throwable
    //   198	214	548	java/lang/Throwable
    //   219	231	548	java/lang/Throwable
    //   238	248	548	java/lang/Throwable
    //   248	282	548	java/lang/Throwable
    //   282	288	548	java/lang/Throwable
    //   296	300	548	java/lang/Throwable
    //   307	314	548	java/lang/Throwable
    //   314	329	548	java/lang/Throwable
    //   329	339	548	java/lang/Throwable
    //   339	351	548	java/lang/Throwable
    //   351	361	548	java/lang/Throwable
    //   361	373	548	java/lang/Throwable
    //   382	387	548	java/lang/Throwable
    //   390	394	548	java/lang/Throwable
    //   394	399	548	java/lang/Throwable
    //   416	428	548	java/lang/Throwable
    //   433	546	548	java/lang/Throwable
    //   546	548	548	java/lang/Throwable
    //   93	106	552	java/lang/AssertionError
    //   111	130	552	java/lang/AssertionError
    //   137	148	552	java/lang/AssertionError
    //   155	160	552	java/lang/AssertionError
    //   167	191	552	java/lang/AssertionError
    //   198	214	552	java/lang/AssertionError
    //   219	231	552	java/lang/AssertionError
    //   238	248	552	java/lang/AssertionError
    //   248	282	552	java/lang/AssertionError
    //   282	288	552	java/lang/AssertionError
    //   296	300	552	java/lang/AssertionError
    //   307	314	552	java/lang/AssertionError
    //   329	339	552	java/lang/AssertionError
    //   351	361	552	java/lang/AssertionError
    //   382	387	552	java/lang/AssertionError
    //   390	394	552	java/lang/AssertionError
    //   416	428	552	java/lang/AssertionError
    //   433	546	552	java/lang/AssertionError
    //   45	51	564	java/lang/Throwable
    //   55	75	564	java/lang/Throwable
    //   79	86	564	java/lang/Throwable
    //   580	587	564	java/lang/Throwable
    //   595	605	564	java/lang/Throwable
    //   608	611	564	java/lang/Throwable
    //   55	75	572	java/lang/AssertionError
  }
  
  private void createTunnel(int paramInt1, int paramInt2)
    throws IOException
  {
    Object localObject2 = createTunnelRequest();
    Object localObject1 = localObject2;
    localObject2 = ((Request)localObject2).httpUrl();
    Object localObject3 = new StringBuilder();
    ((StringBuilder)localObject3).append("CONNECT ");
    ((StringBuilder)localObject3).append(((HttpUrl)localObject2).host());
    ((StringBuilder)localObject3).append(":");
    ((StringBuilder)localObject3).append(((HttpUrl)localObject2).port());
    ((StringBuilder)localObject3).append(" HTTP/1.1");
    localObject3 = ((StringBuilder)localObject3).toString();
    do
    {
      localObject2 = new Http1xStream(null, source, sink);
      source.timeout().timeout(paramInt1, TimeUnit.MILLISECONDS);
      sink.timeout().timeout(paramInt2, TimeUnit.MILLISECONDS);
      ((Http1xStream)localObject2).writeRequest(((Request)localObject1).headers(), (String)localObject3);
      ((Http1xStream)localObject2).finishRequest();
      localObject1 = ((Http1xStream)localObject2).readResponse().request((Request)localObject1).build();
      long l2 = OkHeaders.contentLength((Response)localObject1);
      long l1 = l2;
      if (l2 == -1L) {
        l1 = 0L;
      }
      localObject2 = ((Http1xStream)localObject2).newFixedLengthSource(l1);
      Util.skipAll((Source)localObject2, Integer.MAX_VALUE, TimeUnit.MILLISECONDS);
      ((Source)localObject2).close();
      int i = ((Response)localObject1).code();
      if (i == 200) {
        break label328;
      }
      if (i != 407) {
        break;
      }
      localObject2 = OkHeaders.processAuthHeader(route.getAddress().getAuthenticator(), (Response)localObject1, route.getProxy());
      localObject1 = localObject2;
    } while (localObject2 != null);
    throw new IOException("Failed to authenticate with proxy");
    localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append("Unexpected response code for CONNECT: ");
    ((StringBuilder)localObject2).append(((Response)localObject1).code());
    throw new IOException(((StringBuilder)localObject2).toString());
    label328:
    if ((source.buffer().exhausted()) && (sink.buffer().exhausted())) {
      return;
    }
    localObject1 = new IOException("TLS tunnel buffered too many bytes!");
    throw ((Throwable)localObject1);
  }
  
  private Request createTunnelRequest()
    throws IOException
  {
    return new Request.Builder().url(route.getAddress().url()).header("Host", Util.hostHeader(route.getAddress().url())).header("Proxy-Connection", "Keep-Alive").header("User-Agent", Version.userAgent()).build();
  }
  
  private static TrustRootIndex trustRootIndex(SSLSocketFactory paramSSLSocketFactory)
  {
    try
    {
      if (paramSSLSocketFactory != lastSslSocketFactory)
      {
        X509TrustManager localX509TrustManager = Platform.get().trustManager(paramSSLSocketFactory);
        lastTrustRootIndex = Platform.get().trustRootIndex(localX509TrustManager);
        lastSslSocketFactory = paramSSLSocketFactory;
      }
      paramSSLSocketFactory = lastTrustRootIndex;
      return paramSSLSocketFactory;
    }
    catch (Throwable paramSSLSocketFactory)
    {
      throw paramSSLSocketFactory;
    }
  }
  
  public int allocationLimit()
  {
    FramedConnection localFramedConnection = framedConnection;
    if (localFramedConnection != null) {
      return localFramedConnection.maxConcurrentStreams();
    }
    return 1;
  }
  
  public void cancel()
  {
    Util.closeQuietly(rawSocket);
  }
  
  public void connect(int paramInt1, int paramInt2, int paramInt3, List paramList, boolean paramBoolean)
    throws RouteException
  {
    if (protocol == null)
    {
      ConnectionSpecSelector localConnectionSpecSelector = new ConnectionSpecSelector(paramList);
      Proxy localProxy = route.getProxy();
      Address localAddress = route.getAddress();
      Object localObject;
      if ((route.getAddress().getSslSocketFactory() == null) && (!paramList.contains(ConnectionSpec.CLEARTEXT)))
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("CLEARTEXT communication not supported: ");
        ((StringBuilder)localObject).append(paramList);
        throw new RouteException(new UnknownServiceException(((StringBuilder)localObject).toString()));
      }
      paramList = null;
      label178:
      do
      {
        for (;;)
        {
          if (protocol != null) {
            return;
          }
          try
          {
            localObject = localProxy.type();
            if (localObject != Proxy.Type.DIRECT)
            {
              localObject = localProxy.type();
              if (localObject != Proxy.Type.HTTP)
              {
                localObject = new Socket(localProxy);
                break label178;
              }
            }
            localObject = localAddress.getSocketFactory().createSocket();
            rawSocket = ((Socket)localObject);
            connectSocket(paramInt1, paramInt2, paramInt3, localConnectionSpecSelector);
          }
          catch (IOException localIOException)
          {
            Util.closeQuietly(socket);
            Util.closeQuietly(rawSocket);
            socket = null;
            rawSocket = null;
            source = null;
            sink = null;
            handshake = null;
            protocol = null;
            if (paramList == null) {
              paramList = new RouteException(localIOException);
            } else {
              paramList.addConnectException(localIOException);
            }
          }
        }
      } while ((paramBoolean) && (localConnectionSpecSelector.connectionFailed(localIOException)));
      throw paramList;
    }
    paramList = new IllegalStateException("already connected");
    throw paramList;
  }
  
  public Handshake getHandshake()
  {
    return handshake;
  }
  
  public Protocol getProtocol()
  {
    Protocol localProtocol = protocol;
    if (localProtocol != null) {
      return localProtocol;
    }
    return Protocol.HTTP_1_1;
  }
  
  public Route getRoute()
  {
    return route;
  }
  
  public Socket getSocket()
  {
    return socket;
  }
  
  boolean isConnected()
  {
    return protocol != null;
  }
  
  /* Error */
  public boolean isHealthy(boolean paramBoolean)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 122	com/squareup/okhttp/internal/io/RealConnection:socket	Ljava/net/Socket;
    //   4: invokevirtual 595	java/net/Socket:isClosed	()Z
    //   7: ifne +111 -> 118
    //   10: aload_0
    //   11: getfield 122	com/squareup/okhttp/internal/io/RealConnection:socket	Ljava/net/Socket;
    //   14: invokevirtual 598	java/net/Socket:isInputShutdown	()Z
    //   17: ifne +113 -> 130
    //   20: aload_0
    //   21: getfield 122	com/squareup/okhttp/internal/io/RealConnection:socket	Ljava/net/Socket;
    //   24: invokevirtual 601	java/net/Socket:isOutputShutdown	()Z
    //   27: ifeq +5 -> 32
    //   30: iconst_0
    //   31: ireturn
    //   32: aload_0
    //   33: getfield 160	com/squareup/okhttp/internal/io/RealConnection:framedConnection	Lcom/squareup/okhttp/internal/framed/FramedConnection;
    //   36: ifnull +5 -> 41
    //   39: iconst_1
    //   40: ireturn
    //   41: iload_1
    //   42: ifeq +74 -> 116
    //   45: aload_0
    //   46: getfield 122	com/squareup/okhttp/internal/io/RealConnection:socket	Ljava/net/Socket;
    //   49: astore_3
    //   50: aload_3
    //   51: invokevirtual 604	java/net/Socket:getSoTimeout	()I
    //   54: istore_2
    //   55: aload_0
    //   56: getfield 122	com/squareup/okhttp/internal/io/RealConnection:socket	Ljava/net/Socket;
    //   59: iconst_1
    //   60: invokevirtual 65	java/net/Socket:setSoTimeout	(I)V
    //   63: aload_0
    //   64: getfield 91	com/squareup/okhttp/internal/io/RealConnection:source	Lokio/BufferedSource;
    //   67: invokeinterface 605 1 0
    //   72: istore_1
    //   73: iload_1
    //   74: ifeq +15 -> 89
    //   77: aload_0
    //   78: getfield 122	com/squareup/okhttp/internal/io/RealConnection:socket	Ljava/net/Socket;
    //   81: astore_3
    //   82: aload_3
    //   83: iload_2
    //   84: invokevirtual 65	java/net/Socket:setSoTimeout	(I)V
    //   87: iconst_0
    //   88: ireturn
    //   89: aload_0
    //   90: getfield 122	com/squareup/okhttp/internal/io/RealConnection:socket	Ljava/net/Socket;
    //   93: astore_3
    //   94: aload_3
    //   95: iload_2
    //   96: invokevirtual 65	java/net/Socket:setSoTimeout	(I)V
    //   99: iconst_1
    //   100: ireturn
    //   101: astore_3
    //   102: aload_0
    //   103: getfield 122	com/squareup/okhttp/internal/io/RealConnection:socket	Ljava/net/Socket;
    //   106: astore 4
    //   108: aload 4
    //   110: iload_2
    //   111: invokevirtual 65	java/net/Socket:setSoTimeout	(I)V
    //   114: aload_3
    //   115: athrow
    //   116: iconst_1
    //   117: ireturn
    //   118: iconst_0
    //   119: ireturn
    //   120: astore_3
    //   121: iconst_1
    //   122: ireturn
    //   123: astore_3
    //   124: iconst_0
    //   125: ireturn
    //   126: astore_3
    //   127: iconst_1
    //   128: ireturn
    //   129: astore_3
    //   130: iconst_0
    //   131: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	132	0	this	RealConnection
    //   0	132	1	paramBoolean	boolean
    //   54	57	2	i	int
    //   49	46	3	localSocket1	Socket
    //   101	14	3	localThrowable	Throwable
    //   120	1	3	localSocketTimeoutException1	java.net.SocketTimeoutException
    //   123	1	3	localIOException1	IOException
    //   126	1	3	localSocketTimeoutException2	java.net.SocketTimeoutException
    //   129	1	3	localIOException2	IOException
    //   106	3	4	localSocket2	Socket
    // Exception table:
    //   from	to	target	type
    //   55	73	101	java/lang/Throwable
    //   50	55	120	java/net/SocketTimeoutException
    //   50	55	123	java/io/IOException
    //   82	87	126	java/net/SocketTimeoutException
    //   94	99	126	java/net/SocketTimeoutException
    //   108	116	126	java/net/SocketTimeoutException
    //   82	87	129	java/io/IOException
    //   94	99	129	java/io/IOException
    //   108	116	129	java/io/IOException
  }
  
  public boolean isMultiplexed()
  {
    return framedConnection != null;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Connection{");
    localStringBuilder.append(route.getAddress().url().host());
    localStringBuilder.append(":");
    localStringBuilder.append(route.getAddress().url().port());
    localStringBuilder.append(", proxy=");
    localStringBuilder.append(route.getProxy());
    localStringBuilder.append(" hostAddress=");
    localStringBuilder.append(route.getSocketAddress());
    localStringBuilder.append(" cipherSuite=");
    Object localObject = handshake;
    if (localObject != null) {
      localObject = ((Handshake)localObject).cipherSuite();
    } else {
      localObject = "none";
    }
    localStringBuilder.append((String)localObject);
    localStringBuilder.append(" protocol=");
    localStringBuilder.append(protocol);
    localStringBuilder.append('}');
    return localStringBuilder.toString();
  }
}
