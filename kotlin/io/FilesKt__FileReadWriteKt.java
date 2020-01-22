package kotlin.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysJvmKt;
import kotlin.internal.PlatformImplementationsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

@Metadata(bv={1, 0, 3}, d1={"\000z\n\000\n\002\020\002\n\002\030\002\n\000\n\002\020\022\n\002\b\002\n\002\020\016\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\b\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\030\002\n\002\b\005\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020 \n\002\b\002\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\005\n\002\030\002\n\000\032\022\020\000\032\0020\001*\0020\0022\006\020\003\032\0020\004\032\034\020\005\032\0020\001*\0020\0022\006\020\006\032\0020\0072\b\b\002\020\b\032\0020\t\032!\020\n\032\0020\013*\0020\0022\b\b\002\020\b\032\0020\t2\b\b\002\020\f\032\0020\rH?\b\032!\020\016\032\0020\017*\0020\0022\b\b\002\020\b\032\0020\t2\b\b\002\020\f\032\0020\rH?\b\032B\020\020\032\0020\001*\0020\00226\020\021\0322\022\023\022\0210\004?\006\f\b\023\022\b\b\024\022\004\b\b(\025\022\023\022\0210\r?\006\f\b\023\022\b\b\024\022\004\b\b(\026\022\004\022\0020\0010\022\032J\020\020\032\0020\001*\0020\0022\006\020\027\032\0020\r26\020\021\0322\022\023\022\0210\004?\006\f\b\023\022\b\b\024\022\004\b\b(\025\022\023\022\0210\r?\006\f\b\023\022\b\b\024\022\004\b\b(\026\022\004\022\0020\0010\022\0327\020\030\032\0020\001*\0020\0022\b\b\002\020\b\032\0020\t2!\020\021\032\035\022\023\022\0210\007?\006\f\b\023\022\b\b\024\022\004\b\b(\032\022\004\022\0020\0010\031\032\r\020\033\032\0020\034*\0020\002H?\b\032\r\020\035\032\0020\036*\0020\002H?\b\032\027\020\037\032\0020 *\0020\0022\b\b\002\020\b\032\0020\tH?\b\032\n\020!\032\0020\004*\0020\002\032\032\020\"\032\b\022\004\022\0020\0070#*\0020\0022\b\b\002\020\b\032\0020\t\032\024\020$\032\0020\007*\0020\0022\b\b\002\020\b\032\0020\t\032\027\020%\032\0020&*\0020\0022\b\b\002\020\b\032\0020\tH?\b\032?\020'\032\002H(\"\004\b\000\020(*\0020\0022\b\b\002\020\b\032\0020\t2\030\020)\032\024\022\n\022\b\022\004\022\0020\0070*\022\004\022\002H(0\031H?\b?\001\000?\006\002\020,\032\022\020-\032\0020\001*\0020\0022\006\020\003\032\0020\004\032\034\020.\032\0020\001*\0020\0022\006\020\006\032\0020\0072\b\b\002\020\b\032\0020\t\032\027\020/\032\00200*\0020\0022\b\b\002\020\b\032\0020\tH?\b?\002\b\n\006\b\021(+0\001?\0061"}, d2={"appendBytes", "", "Ljava/io/File;", "array", "", "appendText", "text", "", "charset", "Ljava/nio/charset/Charset;", "bufferedReader", "Ljava/io/BufferedReader;", "bufferSize", "", "bufferedWriter", "Ljava/io/BufferedWriter;", "forEachBlock", "action", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "buffer", "bytesRead", "blockSize", "forEachLine", "Lkotlin/Function1;", "line", "inputStream", "Ljava/io/FileInputStream;", "outputStream", "Ljava/io/FileOutputStream;", "printWriter", "Ljava/io/PrintWriter;", "readBytes", "readLines", "", "readText", "reader", "Ljava/io/InputStreamReader;", "useLines", "T", "block", "Lkotlin/sequences/Sequence;", "Requires newer compiler version to be inlined correctly.", "(Ljava/io/File;Ljava/nio/charset/Charset;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "writeBytes", "writeText", "writer", "Ljava/io/OutputStreamWriter;", "kotlin-stdlib"}, k=5, mv={1, 1, 15}, xi=1, xs="kotlin/io/FilesKt")
class FilesKt__FileReadWriteKt
  extends FilesKt__FilePathComponentsKt
{
  public FilesKt__FileReadWriteKt() {}
  
  public static final void appendBytes(File paramFile, byte[] paramArrayOfByte)
  {
    Intrinsics.checkParameterIsNotNull(paramFile, "$this$appendBytes");
    Intrinsics.checkParameterIsNotNull(paramArrayOfByte, "array");
    paramFile = (Closeable)new FileOutputStream(paramFile, true);
    try
    {
      ((FileOutputStream)paramFile).write(paramArrayOfByte);
      paramArrayOfByte = Unit.INSTANCE;
      CloseableKt.closeFinally(paramFile, null);
      return;
    }
    catch (Throwable paramArrayOfByte)
    {
      try
      {
        throw paramArrayOfByte;
      }
      catch (Throwable localThrowable)
      {
        CloseableKt.closeFinally(paramFile, paramArrayOfByte);
        throw localThrowable;
      }
    }
  }
  
  public static final void appendText(File paramFile, String paramString, Charset paramCharset)
  {
    Intrinsics.checkParameterIsNotNull(paramFile, "$this$appendText");
    Intrinsics.checkParameterIsNotNull(paramString, "text");
    Intrinsics.checkParameterIsNotNull(paramCharset, "charset");
    paramString = paramString.getBytes(paramCharset);
    Intrinsics.checkExpressionValueIsNotNull(paramString, "(this as java.lang.String).getBytes(charset)");
    appendBytes(paramFile, paramString);
  }
  
  private static final BufferedReader bufferedReader(File paramFile, Charset paramCharset, int paramInt)
  {
    paramFile = (Reader)new InputStreamReader((InputStream)new FileInputStream(paramFile), paramCharset);
    if ((paramFile instanceof BufferedReader)) {
      return (BufferedReader)paramFile;
    }
    return new BufferedReader(paramFile, paramInt);
  }
  
  private static final BufferedWriter bufferedWriter(File paramFile, Charset paramCharset, int paramInt)
  {
    paramFile = (Writer)new OutputStreamWriter((OutputStream)new FileOutputStream(paramFile), paramCharset);
    if ((paramFile instanceof BufferedWriter)) {
      return (BufferedWriter)paramFile;
    }
    return new BufferedWriter(paramFile, paramInt);
  }
  
  /* Error */
  public static final void forEachBlock(File paramFile, int paramInt, Function2 paramFunction2)
  {
    // Byte code:
    //   0: aload_0
    //   1: ldc -79
    //   3: invokestatic 83	kotlin/jvm/internal/Intrinsics:checkParameterIsNotNull	(Ljava/lang/Object;Ljava/lang/String;)V
    //   6: aload_2
    //   7: ldc -78
    //   9: invokestatic 83	kotlin/jvm/internal/Intrinsics:checkParameterIsNotNull	(Ljava/lang/Object;Ljava/lang/String;)V
    //   12: iload_1
    //   13: sipush 512
    //   16: invokestatic 184	kotlin/ranges/RangesKt___RangesKt:coerceAtLeast	(II)I
    //   19: newarray byte
    //   21: astore_3
    //   22: new 139	java/io/FileInputStream
    //   25: dup
    //   26: aload_0
    //   27: invokespecial 142	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   30: checkcast 91	java/io/Closeable
    //   33: astore_0
    //   34: aload_0
    //   35: checkcast 139	java/io/FileInputStream
    //   38: astore 4
    //   40: aload 4
    //   42: aload_3
    //   43: invokevirtual 188	java/io/FileInputStream:read	([B)I
    //   46: istore_1
    //   47: iload_1
    //   48: ifgt +13 -> 61
    //   51: getstatic 101	kotlin/Unit:INSTANCE	Lkotlin/Unit;
    //   54: astore_2
    //   55: aload_0
    //   56: aconst_null
    //   57: invokestatic 107	kotlin/io/CloseableKt:closeFinally	(Ljava/io/Closeable;Ljava/lang/Throwable;)V
    //   60: return
    //   61: aload_2
    //   62: aload_3
    //   63: iload_1
    //   64: invokestatic 194	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   67: invokeinterface 200 3 0
    //   72: pop
    //   73: goto -33 -> 40
    //   76: astore_2
    //   77: aload_2
    //   78: athrow
    //   79: astore_3
    //   80: aload_0
    //   81: aload_2
    //   82: invokestatic 107	kotlin/io/CloseableKt:closeFinally	(Ljava/io/Closeable;Ljava/lang/Throwable;)V
    //   85: goto +3 -> 88
    //   88: aload_3
    //   89: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	90	0	paramFile	File
    //   0	90	1	paramInt	int
    //   0	90	2	paramFunction2	Function2
    //   21	42	3	arrayOfByte	byte[]
    //   79	10	3	localThrowable	Throwable
    //   38	3	4	localFileInputStream	FileInputStream
    // Exception table:
    //   from	to	target	type
    //   34	40	76	java/lang/Throwable
    //   40	47	76	java/lang/Throwable
    //   51	55	76	java/lang/Throwable
    //   61	73	76	java/lang/Throwable
    //   77	79	79	java/lang/Throwable
  }
  
  public static final void forEachBlock(File paramFile, Function2 paramFunction2)
  {
    Intrinsics.checkParameterIsNotNull(paramFile, "$this$forEachBlock");
    Intrinsics.checkParameterIsNotNull(paramFunction2, "action");
    forEachBlock(paramFile, 4096, paramFunction2);
  }
  
  public static final void forEachLine(File paramFile, Charset paramCharset, Function1 paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramFile, "$this$forEachLine");
    Intrinsics.checkParameterIsNotNull(paramCharset, "charset");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "action");
    TextStreamsKt.forEachLine((Reader)new BufferedReader((Reader)new InputStreamReader((InputStream)new FileInputStream(paramFile), paramCharset)), paramFunction1);
  }
  
  private static final FileInputStream inputStream(File paramFile)
  {
    return new FileInputStream(paramFile);
  }
  
  private static final FileOutputStream outputStream(File paramFile)
  {
    return new FileOutputStream(paramFile);
  }
  
  private static final PrintWriter printWriter(File paramFile, Charset paramCharset)
  {
    paramFile = (Writer)new OutputStreamWriter((OutputStream)new FileOutputStream(paramFile), paramCharset);
    if ((paramFile instanceof BufferedWriter)) {
      paramFile = (BufferedWriter)paramFile;
    } else {
      paramFile = new BufferedWriter(paramFile, 8192);
    }
    return new PrintWriter((Writer)paramFile);
  }
  
  public static final byte[] readBytes(File paramFile)
  {
    Intrinsics.checkParameterIsNotNull(paramFile, "$this$readBytes");
    Closeable localCloseable = (Closeable)new FileInputStream(paramFile);
    try
    {
      Object localObject2 = (FileInputStream)localCloseable;
      long l = paramFile.length();
      if (l <= Integer.MAX_VALUE)
      {
        int j = (int)l;
        localObject1 = new byte[j];
        int i = 0;
        while (j > 0)
        {
          int k = ((FileInputStream)localObject2).read((byte[])localObject1, i, j);
          if (k < 0) {
            break;
          }
          j -= k;
          i += k;
        }
        if (j > 0)
        {
          localObject1 = Arrays.copyOf((byte[])localObject1, i);
          paramFile = (File)localObject1;
          Intrinsics.checkExpressionValueIsNotNull(localObject1, "java.util.Arrays.copyOf(this, newSize)");
        }
        else
        {
          i = ((FileInputStream)localObject2).read();
          if (i == -1)
          {
            paramFile = (File)localObject1;
          }
          else
          {
            ExposingBufferByteArrayOutputStream localExposingBufferByteArrayOutputStream = new ExposingBufferByteArrayOutputStream(8193);
            localExposingBufferByteArrayOutputStream.write(i);
            ByteStreamsKt.copyTo$default((InputStream)localObject2, (OutputStream)localExposingBufferByteArrayOutputStream, 0, 2, null);
            i = localObject1.length;
            j = localExposingBufferByteArrayOutputStream.size();
            i += j;
            if (i < 0) {
              break label224;
            }
            paramFile = localExposingBufferByteArrayOutputStream.getBuffer();
            localObject2 = Arrays.copyOf((byte[])localObject1, i);
            Intrinsics.checkExpressionValueIsNotNull(localObject2, "java.util.Arrays.copyOf(this, newSize)");
            paramFile = ArraysKt___ArraysJvmKt.copyInto(paramFile, (byte[])localObject2, localObject1.length, 0, localExposingBufferByteArrayOutputStream.size());
          }
        }
        CloseableKt.closeFinally(localCloseable, null);
        return paramFile;
        label224:
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("File ");
        ((StringBuilder)localObject1).append(paramFile);
        ((StringBuilder)localObject1).append(" is too big to fit in memory.");
        throw ((Throwable)new OutOfMemoryError(((StringBuilder)localObject1).toString()));
      }
      Object localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("File ");
      ((StringBuilder)localObject1).append(paramFile);
      ((StringBuilder)localObject1).append(" is too big (");
      ((StringBuilder)localObject1).append(l);
      ((StringBuilder)localObject1).append(" bytes) to fit in memory.");
      throw ((Throwable)new OutOfMemoryError(((StringBuilder)localObject1).toString()));
    }
    catch (Throwable paramFile)
    {
      try
      {
        throw paramFile;
      }
      catch (Throwable localThrowable)
      {
        CloseableKt.closeFinally(localCloseable, paramFile);
        throw localThrowable;
      }
    }
  }
  
  public static final List readLines(File paramFile, Charset paramCharset)
  {
    Intrinsics.checkParameterIsNotNull(paramFile, "$this$readLines");
    Intrinsics.checkParameterIsNotNull(paramCharset, "charset");
    ArrayList localArrayList = new ArrayList();
    forEachLine(paramFile, paramCharset, (Function1)new Lambda(localArrayList)
    {
      public final void invoke(String paramAnonymousString)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousString, "it");
        add(paramAnonymousString);
      }
    });
    return (List)localArrayList;
  }
  
  public static final String readText(File paramFile, Charset paramCharset)
  {
    Intrinsics.checkParameterIsNotNull(paramFile, "$this$readText");
    Intrinsics.checkParameterIsNotNull(paramCharset, "charset");
    paramFile = (Closeable)new InputStreamReader((InputStream)new FileInputStream(paramFile), paramCharset);
    try
    {
      paramCharset = TextStreamsKt.readText((Reader)paramFile);
      CloseableKt.closeFinally(paramFile, null);
      return paramCharset;
    }
    catch (Throwable paramCharset)
    {
      try
      {
        throw paramCharset;
      }
      catch (Throwable localThrowable)
      {
        CloseableKt.closeFinally(paramFile, paramCharset);
        throw localThrowable;
      }
    }
  }
  
  private static final InputStreamReader reader(File paramFile, Charset paramCharset)
  {
    return new InputStreamReader((InputStream)new FileInputStream(paramFile), paramCharset);
  }
  
  public static final Object useLines(File paramFile, Charset paramCharset, Function1 paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramFile, "$this$useLines");
    Intrinsics.checkParameterIsNotNull(paramCharset, "charset");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "block");
    paramFile = (Reader)new InputStreamReader((InputStream)new FileInputStream(paramFile), paramCharset);
    if ((paramFile instanceof BufferedReader)) {
      paramFile = (BufferedReader)paramFile;
    } else {
      paramFile = new BufferedReader(paramFile, 8192);
    }
    paramFile = (Closeable)paramFile;
    try
    {
      paramCharset = paramFunction1.invoke(TextStreamsKt.lineSequence((BufferedReader)paramFile));
      InlineMarker.finallyStart(1);
      if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 1, 0)) {
        CloseableKt.closeFinally(paramFile, null);
      } else {
        paramFile.close();
      }
      InlineMarker.finallyEnd(1);
      return paramCharset;
    }
    catch (Throwable paramFunction1)
    {
      try
      {
        throw paramFunction1;
      }
      catch (Throwable paramCharset)
      {
        InlineMarker.finallyStart(1);
        if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 1, 0)) {}
      }
      try
      {
        paramFile.close();
      }
      catch (Throwable paramFile)
      {
        for (;;) {}
      }
      CloseableKt.closeFinally(paramFile, paramFunction1);
    }
    InlineMarker.finallyEnd(1);
    throw paramCharset;
  }
  
  public static final void writeBytes(File paramFile, byte[] paramArrayOfByte)
  {
    Intrinsics.checkParameterIsNotNull(paramFile, "$this$writeBytes");
    Intrinsics.checkParameterIsNotNull(paramArrayOfByte, "array");
    paramFile = (Closeable)new FileOutputStream(paramFile);
    try
    {
      ((FileOutputStream)paramFile).write(paramArrayOfByte);
      paramArrayOfByte = Unit.INSTANCE;
      CloseableKt.closeFinally(paramFile, null);
      return;
    }
    catch (Throwable paramArrayOfByte)
    {
      try
      {
        throw paramArrayOfByte;
      }
      catch (Throwable localThrowable)
      {
        CloseableKt.closeFinally(paramFile, paramArrayOfByte);
        throw localThrowable;
      }
    }
  }
  
  public static final void writeText(File paramFile, String paramString, Charset paramCharset)
  {
    Intrinsics.checkParameterIsNotNull(paramFile, "$this$writeText");
    Intrinsics.checkParameterIsNotNull(paramString, "text");
    Intrinsics.checkParameterIsNotNull(paramCharset, "charset");
    paramString = paramString.getBytes(paramCharset);
    Intrinsics.checkExpressionValueIsNotNull(paramString, "(this as java.lang.String).getBytes(charset)");
    writeBytes(paramFile, paramString);
  }
  
  private static final OutputStreamWriter writer(File paramFile, Charset paramCharset)
  {
    return new OutputStreamWriter((OutputStream)new FileOutputStream(paramFile), paramCharset);
  }
}