package androidx.core.graphics;

import android.content.Context;
import android.content.res.Resources;
import android.os.Process;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import android.util.Log;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

public class TypefaceCompatUtil
{
  private static final String CACHE_FILE_PREFIX = ".font";
  private static final String PAGE_KEY = "TypefaceCompatUtil";
  
  private TypefaceCompatUtil() {}
  
  public static void closeQuietly(Closeable paramCloseable)
  {
    if (paramCloseable != null) {
      try
      {
        paramCloseable.close();
        return;
      }
      catch (IOException paramCloseable) {}
    }
  }
  
  public static ByteBuffer copyToDirectBuffer(Context paramContext, Resources paramResources, int paramInt)
  {
    paramContext = getTempFile(paramContext);
    if (paramContext == null) {
      return null;
    }
    try
    {
      boolean bool = copyToFile(paramContext, paramResources, paramInt);
      if (!bool)
      {
        paramContext.delete();
        return null;
      }
      paramResources = mmap(paramContext);
      paramContext.delete();
      return paramResources;
    }
    catch (Throwable paramResources)
    {
      paramContext.delete();
      throw paramResources;
    }
  }
  
  public static boolean copyToFile(File paramFile, Resources paramResources, int paramInt)
  {
    try
    {
      InputStream localInputStream = paramResources.openRawResource(paramInt);
      paramResources = localInputStream;
      try
      {
        boolean bool = copyToFile(paramFile, localInputStream);
        closeQuietly(localInputStream);
        return bool;
      }
      catch (Throwable paramFile) {}
      closeQuietly(paramResources);
    }
    catch (Throwable paramFile)
    {
      paramResources = null;
    }
    throw paramFile;
  }
  
  public static boolean copyToFile(File paramFile, InputStream paramInputStream)
  {
    StrictMode.ThreadPolicy localThreadPolicy = StrictMode.allowThreadDiskWrites();
    StringBuilder localStringBuilder = null;
    Object localObject = null;
    try
    {
      paramFile = new FileOutputStream(paramFile, false);
      localObject = new byte['?'];
      try
      {
        for (;;)
        {
          int i = paramInputStream.read((byte[])localObject);
          if (i == -1) {
            break;
          }
          paramFile.write((byte[])localObject, 0, i);
        }
        closeQuietly(paramFile);
        StrictMode.setThreadPolicy(localThreadPolicy);
        return true;
      }
      catch (Throwable paramInputStream)
      {
        localObject = paramFile;
        paramFile = paramInputStream;
        break label135;
      }
      catch (IOException paramInputStream) {}
      localObject = paramFile;
    }
    catch (Throwable paramFile) {}catch (IOException paramInputStream)
    {
      paramFile = localStringBuilder;
    }
    localStringBuilder = new StringBuilder();
    localObject = paramFile;
    localStringBuilder.append("Error copying resource contents to temp file: ");
    localObject = paramFile;
    localStringBuilder.append(paramInputStream.getMessage());
    localObject = paramFile;
    Log.e("TypefaceCompatUtil", localStringBuilder.toString());
    closeQuietly(paramFile);
    StrictMode.setThreadPolicy(localThreadPolicy);
    return false;
    label135:
    closeQuietly((Closeable)localObject);
    StrictMode.setThreadPolicy(localThreadPolicy);
    throw paramFile;
  }
  
  public static File getTempFile(Context paramContext)
  {
    paramContext = paramContext.getCacheDir();
    if (paramContext == null) {
      return null;
    }
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append(".font");
    ((StringBuilder)localObject1).append(Process.myPid());
    ((StringBuilder)localObject1).append("-");
    ((StringBuilder)localObject1).append(Process.myTid());
    ((StringBuilder)localObject1).append("-");
    localObject1 = ((StringBuilder)localObject1).toString();
    int i = 0;
    while (i < 100)
    {
      Object localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append((String)localObject1);
      ((StringBuilder)localObject2).append(i);
      localObject2 = new File(paramContext, ((StringBuilder)localObject2).toString());
      try
      {
        boolean bool = ((File)localObject2).createNewFile();
        if (bool) {
          return localObject2;
        }
      }
      catch (IOException localIOException)
      {
        for (;;) {}
      }
      i += 1;
    }
    return null;
  }
  
  /* Error */
  public static ByteBuffer mmap(Context paramContext, android.os.CancellationSignal paramCancellationSignal, android.net.Uri paramUri)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 136	android/content/Context:getContentResolver	()Landroid/content/ContentResolver;
    //   4: astore_0
    //   5: aload_0
    //   6: aload_2
    //   7: ldc -118
    //   9: aload_1
    //   10: invokevirtual 144	android/content/ContentResolver:openFileDescriptor	(Landroid/net/Uri;Ljava/lang/String;Landroid/os/CancellationSignal;)Landroid/os/ParcelFileDescriptor;
    //   13: astore_0
    //   14: aload_0
    //   15: ifnonnull +13 -> 28
    //   18: aload_0
    //   19: ifnull +106 -> 125
    //   22: aload_0
    //   23: invokevirtual 147	android/os/ParcelFileDescriptor:close	()V
    //   26: aconst_null
    //   27: areturn
    //   28: new 149	java/io/FileInputStream
    //   31: dup
    //   32: aload_0
    //   33: invokevirtual 153	android/os/ParcelFileDescriptor:getFileDescriptor	()Ljava/io/FileDescriptor;
    //   36: invokespecial 156	java/io/FileInputStream:<init>	(Ljava/io/FileDescriptor;)V
    //   39: astore_1
    //   40: aload_1
    //   41: invokevirtual 160	java/io/FileInputStream:getChannel	()Ljava/nio/channels/FileChannel;
    //   44: astore_2
    //   45: aload_2
    //   46: invokevirtual 166	java/nio/channels/FileChannel:size	()J
    //   49: lstore_3
    //   50: aload_2
    //   51: getstatic 172	java/nio/channels/FileChannel$MapMode:READ_ONLY	Ljava/nio/channels/FileChannel$MapMode;
    //   54: lconst_0
    //   55: lload_3
    //   56: invokevirtual 176	java/nio/channels/FileChannel:map	(Ljava/nio/channels/FileChannel$MapMode;JJ)Ljava/nio/MappedByteBuffer;
    //   59: astore_2
    //   60: aload_1
    //   61: invokevirtual 177	java/io/FileInputStream:close	()V
    //   64: aload_0
    //   65: ifnull +62 -> 127
    //   68: aload_0
    //   69: invokevirtual 147	android/os/ParcelFileDescriptor:close	()V
    //   72: aload_2
    //   73: areturn
    //   74: astore_2
    //   75: aload_2
    //   76: athrow
    //   77: astore 5
    //   79: aload_1
    //   80: invokevirtual 177	java/io/FileInputStream:close	()V
    //   83: goto +9 -> 92
    //   86: astore_1
    //   87: aload_2
    //   88: aload_1
    //   89: invokevirtual 181	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
    //   92: aload 5
    //   94: athrow
    //   95: astore_1
    //   96: aload_1
    //   97: athrow
    //   98: astore_2
    //   99: aload_0
    //   100: ifnull +16 -> 116
    //   103: aload_0
    //   104: invokevirtual 147	android/os/ParcelFileDescriptor:close	()V
    //   107: goto +9 -> 116
    //   110: astore_0
    //   111: aload_1
    //   112: aload_0
    //   113: invokevirtual 181	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
    //   116: aload_2
    //   117: athrow
    //   118: astore_0
    //   119: aconst_null
    //   120: areturn
    //   121: astore_0
    //   122: aconst_null
    //   123: areturn
    //   124: astore_0
    //   125: aconst_null
    //   126: areturn
    //   127: aload_2
    //   128: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	129	0	paramContext	Context
    //   0	129	1	paramCancellationSignal	android.os.CancellationSignal
    //   0	129	2	paramUri	android.net.Uri
    //   49	7	3	l	long
    //   77	16	5	localThrowable	Throwable
    // Exception table:
    //   from	to	target	type
    //   40	60	74	java/lang/Throwable
    //   75	77	77	java/lang/Throwable
    //   79	83	86	java/lang/Throwable
    //   28	40	95	java/lang/Throwable
    //   60	64	95	java/lang/Throwable
    //   87	92	95	java/lang/Throwable
    //   92	95	95	java/lang/Throwable
    //   96	98	98	java/lang/Throwable
    //   103	107	110	java/lang/Throwable
    //   5	14	118	java/io/IOException
    //   22	26	118	java/io/IOException
    //   68	72	121	java/io/IOException
    //   111	116	124	java/io/IOException
    //   116	118	124	java/io/IOException
  }
  
  private static ByteBuffer mmap(File paramFile)
  {
    try
    {
      paramFile = new FileInputStream(paramFile);
      try
      {
        Object localObject = paramFile.getChannel();
        long l = ((FileChannel)localObject).size();
        localObject = ((FileChannel)localObject).map(FileChannel.MapMode.READ_ONLY, 0L, l);
        try
        {
          localThrowable1.addSuppressed(paramFile);
          throw localThrowable2;
        }
        catch (IOException paramFile)
        {
          for (;;) {}
        }
      }
      catch (Throwable localThrowable1)
      {
        try
        {
          paramFile.close();
          return localObject;
        }
        catch (IOException paramFile)
        {
          for (;;) {}
        }
        localThrowable1 = localThrowable1;
        try
        {
          throw localThrowable1;
        }
        catch (Throwable localThrowable2)
        {
          try
          {
            paramFile.close();
          }
          catch (Throwable paramFile) {}
        }
      }
    }
    catch (IOException paramFile)
    {
      for (;;) {}
    }
    return null;
  }
}
