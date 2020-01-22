package com.google.firebase.package_9;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import androidx.core.content.ContextCompat;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Properties;

final class Cache
{
  Cache() {}
  
  private final Buffer create(Context paramContext, String paramString)
    throws zzaa
  {
    try
    {
      Buffer localBuffer1 = load(paramContext, paramString);
      if (localBuffer1 != null)
      {
        put(paramContext, paramString, localBuffer1);
        return localBuffer1;
      }
      localBuffer1 = null;
    }
    catch (zzaa localZzaa1) {}
    try
    {
      Buffer localBuffer2 = get(paramContext.getSharedPreferences("com.google.android.gms.appid", 0), paramString);
      if (localBuffer2 != null)
      {
        read(paramContext, paramString, localBuffer2, false);
        return localBuffer2;
      }
    }
    catch (zzaa localZzaa2)
    {
      if (localZzaa2 == null) {
        return null;
      }
      throw localZzaa2;
    }
  }
  
  private static Buffer get(SharedPreferences paramSharedPreferences, String paramString)
    throws zzaa
  {
    String str1 = paramSharedPreferences.getString(zzaw.getName(paramString, "|P|"), null);
    String str2 = paramSharedPreferences.getString(zzaw.getName(paramString, "|K|"), null);
    if (str1 != null)
    {
      if (str2 == null) {
        return null;
      }
      return new Buffer(load(str1, str2), getList(paramSharedPreferences, paramString));
    }
    return null;
  }
  
  private static Buffer get(FileChannel paramFileChannel)
    throws zzaa, IOException
  {
    Properties localProperties = new Properties();
    localProperties.load(Channels.newInputStream(paramFileChannel));
    paramFileChannel = localProperties.getProperty("pub");
    String str = localProperties.getProperty("pri");
    if ((paramFileChannel != null) && (str != null))
    {
      paramFileChannel = load(paramFileChannel, str);
      try
      {
        long l = Long.parseLong(localProperties.getProperty("cre"));
        return new Buffer(paramFileChannel, l);
      }
      catch (NumberFormatException paramFileChannel)
      {
        throw new zzaa(paramFileChannel);
      }
    }
    throw new zzaa("Invalid properties file");
  }
  
  private static File get(Context paramContext)
  {
    File localFile = ContextCompat.getNoBackupFilesDir(paramContext);
    if ((localFile != null) && (localFile.isDirectory())) {
      return localFile;
    }
    Log.w("FirebaseInstanceId", "noBackupFilesDir doesn't exist, using regular files directory instead");
    return paramContext.getFilesDir();
  }
  
  private static long getList(SharedPreferences paramSharedPreferences, String paramString)
  {
    paramSharedPreferences = paramSharedPreferences.getString(zzaw.getName(paramString, "cre"), null);
    if (paramSharedPreferences != null) {}
    try
    {
      long l = Long.parseLong(paramSharedPreferences);
      return l;
    }
    catch (NumberFormatException paramSharedPreferences)
    {
      for (;;) {}
    }
    return 0L;
  }
  
  static void initialize(Context paramContext)
  {
    paramContext = get(paramContext).listFiles();
    int j = paramContext.length;
    int i = 0;
    while (i < j)
    {
      Object localObject = paramContext[i];
      if (localObject.getName().startsWith("com.google.InstanceId")) {
        localObject.delete();
      }
      i += 1;
    }
  }
  
  private final Buffer load(Context paramContext, String paramString)
    throws zzaa
  {
    paramString = read(paramContext, paramString);
    if (!paramString.exists()) {
      return null;
    }
    try
    {
      paramContext = read(paramString);
      return paramContext;
    }
    catch (IOException paramContext) {}catch (zzaa paramContext) {}
    StringBuilder localStringBuilder;
    if (Log.isLoggable("FirebaseInstanceId", 3))
    {
      paramContext = String.valueOf(paramContext);
      localStringBuilder = new StringBuilder(String.valueOf(paramContext).length() + 40);
      localStringBuilder.append("Failed to read key from file, retrying: ");
      localStringBuilder.append(paramContext);
      Log.d("FirebaseInstanceId", localStringBuilder.toString());
    }
    try
    {
      paramContext = read(paramString);
      return paramContext;
    }
    catch (IOException paramContext)
    {
      paramString = String.valueOf(paramContext);
      localStringBuilder = new StringBuilder(String.valueOf(paramString).length() + 45);
      localStringBuilder.append("IID file exists, but failed to read from it: ");
      localStringBuilder.append(paramString);
      Log.w("FirebaseInstanceId", localStringBuilder.toString());
      throw new zzaa(paramContext);
    }
  }
  
  private static KeyPair load(String paramString1, String paramString2)
    throws zzaa
  {
    try
    {
      paramString1 = Base64.decode(paramString1, 8);
      paramString2 = Base64.decode(paramString2, 8);
      try
      {
        localObject = KeyFactory.getInstance("RSA");
        paramString1 = ((KeyFactory)localObject).generatePublic(new X509EncodedKeySpec(paramString1));
        paramString2 = ((KeyFactory)localObject).generatePrivate(new PKCS8EncodedKeySpec(paramString2));
        paramString1 = new KeyPair(paramString1, paramString2);
        return paramString1;
      }
      catch (NoSuchAlgorithmException paramString1) {}catch (InvalidKeySpecException paramString1) {}
      paramString2 = String.valueOf(paramString1);
      Object localObject = new StringBuilder(String.valueOf(paramString2).length() + 19);
      ((StringBuilder)localObject).append("Invalid key stored ");
      ((StringBuilder)localObject).append(paramString2);
      Log.w("FirebaseInstanceId", ((StringBuilder)localObject).toString());
      throw new zzaa((Exception)paramString1);
    }
    catch (IllegalArgumentException paramString1)
    {
      throw new zzaa(paramString1);
    }
  }
  
  private final void put(Context paramContext, String paramString, Buffer paramBuffer)
  {
    paramContext = paramContext.getSharedPreferences("com.google.android.gms.appid", 0);
    try
    {
      boolean bool = paramBuffer.equals(get(paramContext, paramString));
      if (bool) {
        return;
      }
    }
    catch (zzaa localZzaa)
    {
      for (;;) {}
    }
    if (Log.isLoggable("FirebaseInstanceId", 3)) {
      Log.d("FirebaseInstanceId", "Writing key to shared preferences");
    }
    paramContext = paramContext.edit();
    paramContext.putString(zzaw.getName(paramString, "|P|"), Buffer.encode(paramBuffer));
    paramContext.putString(zzaw.getName(paramString, "|K|"), Buffer.toString(paramBuffer));
    paramContext.putString(zzaw.getName(paramString, "cre"), String.valueOf(Buffer.copyTo(paramBuffer)));
    paramContext.commit();
  }
  
  /* Error */
  private final Buffer read(Context paramContext, String paramString, Buffer paramBuffer, boolean paramBoolean)
  {
    // Byte code:
    //   0: ldc -123
    //   2: iconst_3
    //   3: invokestatic 180	android/util/Log:isLoggable	(Ljava/lang/String;I)Z
    //   6: ifeq +12 -> 18
    //   9: ldc -123
    //   11: ldc_w 290
    //   14: invokestatic 205	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   17: pop
    //   18: new 85	java/util/Properties
    //   21: dup
    //   22: invokespecial 86	java/util/Properties:<init>	()V
    //   25: astore 8
    //   27: aload 8
    //   29: ldc 97
    //   31: aload_3
    //   32: invokestatic 270	com/google/firebase/package_9/Buffer:encode	(Lcom/google/firebase/package_9/Buffer;)Ljava/lang/String;
    //   35: invokevirtual 294	java/util/Properties:setProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;
    //   38: pop
    //   39: aload 8
    //   41: ldc 103
    //   43: aload_3
    //   44: invokestatic 278	com/google/firebase/package_9/Buffer:toString	(Lcom/google/firebase/package_9/Buffer;)Ljava/lang/String;
    //   47: invokevirtual 294	java/util/Properties:setProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;
    //   50: pop
    //   51: aload 8
    //   53: ldc 105
    //   55: aload_3
    //   56: invokestatic 282	com/google/firebase/package_9/Buffer:copyTo	(Lcom/google/firebase/package_9/Buffer;)J
    //   59: invokestatic 285	java/lang/String:valueOf	(J)Ljava/lang/String;
    //   62: invokevirtual 294	java/util/Properties:setProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;
    //   65: pop
    //   66: aload_1
    //   67: aload_2
    //   68: invokestatic 170	com/google/firebase/package_9/Cache:read	(Landroid/content/Context;Ljava/lang/String;)Ljava/io/File;
    //   71: astore_1
    //   72: aload_1
    //   73: invokevirtual 297	java/io/File:createNewFile	()Z
    //   76: pop
    //   77: new 299	java/io/RandomAccessFile
    //   80: dup
    //   81: aload_1
    //   82: ldc_w 301
    //   85: invokespecial 304	java/io/RandomAccessFile:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   88: astore_2
    //   89: aload_2
    //   90: invokevirtual 308	java/io/RandomAccessFile:getChannel	()Ljava/nio/channels/FileChannel;
    //   93: astore 9
    //   95: aload 9
    //   97: invokevirtual 312	java/nio/channels/FileChannel:lock	()Ljava/nio/channels/FileLock;
    //   100: pop
    //   101: iload 4
    //   103: ifeq +121 -> 224
    //   106: aload 9
    //   108: invokevirtual 316	java/nio/channels/FileChannel:size	()J
    //   111: lstore 6
    //   113: lload 6
    //   115: lconst_0
    //   116: lcmp
    //   117: ifle +107 -> 224
    //   120: aload 9
    //   122: lconst_0
    //   123: invokevirtual 320	java/nio/channels/FileChannel:position	(J)Ljava/nio/channels/FileChannel;
    //   126: pop
    //   127: aload 9
    //   129: invokestatic 322	com/google/firebase/package_9/Cache:get	(Ljava/nio/channels/FileChannel;)Lcom/google/firebase/package_9/Buffer;
    //   132: astore_1
    //   133: aload 9
    //   135: ifnull +9 -> 144
    //   138: aconst_null
    //   139: aload 9
    //   141: invokestatic 324	com/google/firebase/package_9/Cache:close	(Ljava/lang/Throwable;Ljava/nio/channels/FileChannel;)V
    //   144: aconst_null
    //   145: aload_2
    //   146: invokestatic 328	com/google/firebase/package_9/Cache:write	(Ljava/lang/Throwable;Ljava/io/RandomAccessFile;)V
    //   149: aload_1
    //   150: areturn
    //   151: astore_1
    //   152: goto +4 -> 156
    //   155: astore_1
    //   156: ldc -123
    //   158: iconst_3
    //   159: invokestatic 180	android/util/Log:isLoggable	(Ljava/lang/String;I)Z
    //   162: istore 4
    //   164: iload 4
    //   166: ifeq +58 -> 224
    //   169: aload_1
    //   170: invokestatic 184	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   173: astore_1
    //   174: aload_1
    //   175: invokestatic 184	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   178: invokevirtual 190	java/lang/String:length	()I
    //   181: istore 5
    //   183: new 186	java/lang/StringBuilder
    //   186: dup
    //   187: iload 5
    //   189: bipush 64
    //   191: iadd
    //   192: invokespecial 193	java/lang/StringBuilder:<init>	(I)V
    //   195: astore 10
    //   197: aload 10
    //   199: ldc_w 330
    //   202: invokevirtual 199	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   205: pop
    //   206: aload 10
    //   208: aload_1
    //   209: invokevirtual 199	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   212: pop
    //   213: ldc -123
    //   215: aload 10
    //   217: invokevirtual 202	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   220: invokestatic 205	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   223: pop
    //   224: aload 9
    //   226: lconst_0
    //   227: invokevirtual 320	java/nio/channels/FileChannel:position	(J)Ljava/nio/channels/FileChannel;
    //   230: pop
    //   231: aload 8
    //   233: aload 9
    //   235: invokestatic 334	java/nio/channels/Channels:newOutputStream	(Ljava/nio/channels/WritableByteChannel;)Ljava/io/OutputStream;
    //   238: aconst_null
    //   239: invokevirtual 338	java/util/Properties:store	(Ljava/io/OutputStream;Ljava/lang/String;)V
    //   242: aload 9
    //   244: ifnull +9 -> 253
    //   247: aconst_null
    //   248: aload 9
    //   250: invokestatic 324	com/google/firebase/package_9/Cache:close	(Ljava/lang/Throwable;Ljava/nio/channels/FileChannel;)V
    //   253: aconst_null
    //   254: aload_2
    //   255: invokestatic 328	com/google/firebase/package_9/Cache:write	(Ljava/lang/Throwable;Ljava/io/RandomAccessFile;)V
    //   258: aload_3
    //   259: areturn
    //   260: astore_1
    //   261: aload_1
    //   262: athrow
    //   263: astore_3
    //   264: aload 9
    //   266: ifnull +9 -> 275
    //   269: aload_1
    //   270: aload 9
    //   272: invokestatic 324	com/google/firebase/package_9/Cache:close	(Ljava/lang/Throwable;Ljava/nio/channels/FileChannel;)V
    //   275: aload_3
    //   276: athrow
    //   277: astore_1
    //   278: aload_1
    //   279: athrow
    //   280: astore_3
    //   281: aload_1
    //   282: aload_2
    //   283: invokestatic 328	com/google/firebase/package_9/Cache:write	(Ljava/lang/Throwable;Ljava/io/RandomAccessFile;)V
    //   286: aload_3
    //   287: athrow
    //   288: astore_1
    //   289: aload_1
    //   290: invokestatic 184	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   293: astore_1
    //   294: new 186	java/lang/StringBuilder
    //   297: dup
    //   298: aload_1
    //   299: invokestatic 184	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   302: invokevirtual 190	java/lang/String:length	()I
    //   305: bipush 21
    //   307: iadd
    //   308: invokespecial 193	java/lang/StringBuilder:<init>	(I)V
    //   311: astore_2
    //   312: aload_2
    //   313: ldc_w 340
    //   316: invokevirtual 199	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   319: pop
    //   320: aload_2
    //   321: aload_1
    //   322: invokevirtual 199	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   325: pop
    //   326: ldc -123
    //   328: aload_2
    //   329: invokevirtual 202	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   332: invokestatic 141	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
    //   335: pop
    //   336: aconst_null
    //   337: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	338	0	this	Cache
    //   0	338	1	paramContext	Context
    //   0	338	2	paramString	String
    //   0	338	3	paramBuffer	Buffer
    //   0	338	4	paramBoolean	boolean
    //   181	11	5	i	int
    //   111	3	6	l	long
    //   25	207	8	localProperties	Properties
    //   93	178	9	localFileChannel	FileChannel
    //   195	21	10	localStringBuilder	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   120	133	151	com/google/firebase/package_9/zzaa
    //   120	133	155	java/io/IOException
    //   95	101	260	java/lang/Throwable
    //   106	113	260	java/lang/Throwable
    //   120	133	260	java/lang/Throwable
    //   156	164	260	java/lang/Throwable
    //   169	183	260	java/lang/Throwable
    //   183	224	260	java/lang/Throwable
    //   224	242	260	java/lang/Throwable
    //   261	263	263	java/lang/Throwable
    //   89	95	277	java/lang/Throwable
    //   138	144	277	java/lang/Throwable
    //   247	253	277	java/lang/Throwable
    //   269	275	277	java/lang/Throwable
    //   275	277	277	java/lang/Throwable
    //   278	280	280	java/lang/Throwable
    //   72	77	288	java/io/IOException
    //   77	89	288	java/io/IOException
    //   144	149	288	java/io/IOException
    //   253	258	288	java/io/IOException
    //   281	288	288	java/io/IOException
  }
  
  /* Error */
  private final Buffer read(File paramFile)
    throws zzaa, IOException
  {
    // Byte code:
    //   0: new 255	java/io/FileInputStream
    //   3: dup
    //   4: aload_1
    //   5: invokespecial 343	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   8: astore_1
    //   9: aload_1
    //   10: invokevirtual 344	java/io/FileInputStream:getChannel	()Ljava/nio/channels/FileChannel;
    //   13: astore_2
    //   14: aload_2
    //   15: lconst_0
    //   16: ldc2_w 345
    //   19: iconst_1
    //   20: invokevirtual 349	java/nio/channels/FileChannel:lock	(JJZ)Ljava/nio/channels/FileLock;
    //   23: pop
    //   24: aload_2
    //   25: invokestatic 322	com/google/firebase/package_9/Cache:get	(Ljava/nio/channels/FileChannel;)Lcom/google/firebase/package_9/Buffer;
    //   28: astore_3
    //   29: aload_2
    //   30: ifnull +8 -> 38
    //   33: aconst_null
    //   34: aload_2
    //   35: invokestatic 324	com/google/firebase/package_9/Cache:close	(Ljava/lang/Throwable;Ljava/nio/channels/FileChannel;)V
    //   38: aconst_null
    //   39: aload_1
    //   40: invokestatic 351	com/google/firebase/package_9/Cache:load	(Ljava/lang/Throwable;Ljava/io/FileInputStream;)V
    //   43: aload_3
    //   44: areturn
    //   45: astore_3
    //   46: aload_3
    //   47: athrow
    //   48: astore 4
    //   50: aload_2
    //   51: ifnull +8 -> 59
    //   54: aload_3
    //   55: aload_2
    //   56: invokestatic 324	com/google/firebase/package_9/Cache:close	(Ljava/lang/Throwable;Ljava/nio/channels/FileChannel;)V
    //   59: aload 4
    //   61: athrow
    //   62: astore_2
    //   63: aload_2
    //   64: athrow
    //   65: astore_3
    //   66: aload_2
    //   67: aload_1
    //   68: invokestatic 351	com/google/firebase/package_9/Cache:load	(Ljava/lang/Throwable;Ljava/io/FileInputStream;)V
    //   71: aload_3
    //   72: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	73	0	this	Cache
    //   0	73	1	paramFile	File
    //   13	43	2	localFileChannel	FileChannel
    //   62	5	2	localThrowable1	Throwable
    //   28	16	3	localBuffer	Buffer
    //   45	10	3	localThrowable2	Throwable
    //   65	7	3	localThrowable3	Throwable
    //   48	12	4	localThrowable4	Throwable
    // Exception table:
    //   from	to	target	type
    //   14	29	45	java/lang/Throwable
    //   46	48	48	java/lang/Throwable
    //   9	14	62	java/lang/Throwable
    //   33	38	62	java/lang/Throwable
    //   54	59	62	java/lang/Throwable
    //   59	62	62	java/lang/Throwable
    //   63	65	65	java/lang/Throwable
  }
  
  private static File read(Context paramContext, String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      paramString = "com.google.InstanceId.properties";
    }
    try
    {
      paramString = Base64.encodeToString(paramString.getBytes("UTF-8"), 11);
      int i = String.valueOf(paramString).length();
      StringBuilder localStringBuilder = new StringBuilder(i + 33);
      localStringBuilder.append("com.google.InstanceId_");
      localStringBuilder.append(paramString);
      localStringBuilder.append(".properties");
      paramString = localStringBuilder.toString();
      return new File(get(paramContext), paramString);
    }
    catch (UnsupportedEncodingException paramContext)
    {
      throw new AssertionError(paramContext);
    }
  }
  
  final Buffer get(Context paramContext, String paramString)
  {
    Buffer localBuffer1 = new Buffer(LocationServices.decode(), System.currentTimeMillis());
    Buffer localBuffer2 = read(paramContext, paramString, localBuffer1, true);
    if ((localBuffer2 != null) && (!localBuffer2.equals(localBuffer1)))
    {
      if (Log.isLoggable("FirebaseInstanceId", 3))
      {
        Log.d("FirebaseInstanceId", "Loaded key after generating new one, using loaded one");
        return localBuffer2;
      }
    }
    else
    {
      if (Log.isLoggable("FirebaseInstanceId", 3)) {
        Log.d("FirebaseInstanceId", "Generated new key");
      }
      put(paramContext, paramString, localBuffer1);
      return localBuffer1;
    }
    return localBuffer2;
  }
  
  final Buffer lookup(Context paramContext, String paramString)
    throws zzaa
  {
    Buffer localBuffer = create(paramContext, paramString);
    if (localBuffer != null) {
      return localBuffer;
    }
    return get(paramContext, paramString);
  }
}
