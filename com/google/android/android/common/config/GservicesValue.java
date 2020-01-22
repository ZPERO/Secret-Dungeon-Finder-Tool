package com.google.android.android.common.config;

import android.content.Context;
import android.util.Log;
import java.util.HashSet;

public abstract class GservicesValue<T>
{
  private static final Object sLock = new Object();
  private static zza zzbl = null;
  private static int zzbm = 0;
  private static Context zzbn;
  private static HashSet<String> zzbo;
  protected final String mKey;
  protected final T zzbp;
  private T zzbq = null;
  
  protected GservicesValue(String paramString, Object paramObject)
  {
    mKey = paramString;
    zzbp = paramObject;
  }
  
  private static boolean hasMoreUpdates()
  {
    Object localObject = sLock;
    try
    {
      return false;
    }
    catch (Throwable localThrowable)
    {
      throw localThrowable;
    }
  }
  
  public static boolean isInitialized()
  {
    Object localObject = sLock;
    try
    {
      return false;
    }
    catch (Throwable localThrowable)
    {
      throw localThrowable;
    }
  }
  
  public static GservicesValue value(String paramString, Float paramFloat)
  {
    return new PolynomialFunction(paramString, paramFloat);
  }
  
  public static GservicesValue value(String paramString, Integer paramInteger)
  {
    return new Scorer(paramString, paramInteger);
  }
  
  public static GservicesValue value(String paramString, Long paramLong)
  {
    return new Array2DRowRealMatrix(paramString, paramLong);
  }
  
  public static GservicesValue value(String paramString1, String paramString2)
  {
    return new RealVector(paramString1, paramString2);
  }
  
  public static GservicesValue value(String paramString, boolean paramBoolean)
  {
    return new ArrayRealVector(paramString, Boolean.valueOf(paramBoolean));
  }
  
  protected abstract Object add(String paramString);
  
  public final Object getBinderSafe()
  {
    return rewrite();
  }
  
  public void override(Object paramObject)
  {
    Log.w("GservicesValue", "GservicesValue.override(): test should probably call initForTests() first");
    zzbq = paramObject;
    paramObject = sLock;
    try
    {
      hasMoreUpdates();
      return;
    }
    catch (Throwable localThrowable)
    {
      throw localThrowable;
    }
  }
  
  public void resetOverride()
  {
    zzbq = null;
  }
  
  /* Error */
  public final Object rewrite()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 38	com/google/android/android/common/config/GservicesValue:zzbq	Ljava/lang/Object;
    //   4: astore_3
    //   5: aload_3
    //   6: ifnull +5 -> 11
    //   9: aload_3
    //   10: areturn
    //   11: invokestatic 114	android/os/StrictMode:allowThreadDiskReads	()Landroid/os/StrictMode$ThreadPolicy;
    //   14: astore_3
    //   15: getstatic 30	com/google/android/android/common/config/GservicesValue:sLock	Ljava/lang/Object;
    //   18: astore 4
    //   20: aload 4
    //   22: monitorenter
    //   23: aload 4
    //   25: monitorexit
    //   26: getstatic 30	com/google/android/android/common/config/GservicesValue:sLock	Ljava/lang/Object;
    //   29: astore 4
    //   31: aload 4
    //   33: monitorenter
    //   34: aconst_null
    //   35: putstatic 116	com/google/android/android/common/config/GservicesValue:zzbo	Ljava/util/HashSet;
    //   38: aconst_null
    //   39: putstatic 118	com/google/android/android/common/config/GservicesValue:zzbn	Landroid/content/Context;
    //   42: aload 4
    //   44: monitorexit
    //   45: aload_0
    //   46: getfield 40	com/google/android/android/common/config/GservicesValue:mKey	Ljava/lang/String;
    //   49: astore 4
    //   51: aload_0
    //   52: aload 4
    //   54: invokevirtual 120	com/google/android/android/common/config/GservicesValue:add	(Ljava/lang/String;)Ljava/lang/Object;
    //   57: astore 4
    //   59: aload_3
    //   60: invokestatic 124	android/os/StrictMode:setThreadPolicy	(Landroid/os/StrictMode$ThreadPolicy;)V
    //   63: aload 4
    //   65: areturn
    //   66: astore 4
    //   68: goto +37 -> 105
    //   71: invokestatic 130	android/os/Binder:clearCallingIdentity	()J
    //   74: lstore_1
    //   75: aload_0
    //   76: aload_0
    //   77: getfield 40	com/google/android/android/common/config/GservicesValue:mKey	Ljava/lang/String;
    //   80: invokevirtual 120	com/google/android/android/common/config/GservicesValue:add	(Ljava/lang/String;)Ljava/lang/Object;
    //   83: astore 4
    //   85: lload_1
    //   86: invokestatic 134	android/os/Binder:restoreCallingIdentity	(J)V
    //   89: aload_3
    //   90: invokestatic 124	android/os/StrictMode:setThreadPolicy	(Landroid/os/StrictMode$ThreadPolicy;)V
    //   93: aload 4
    //   95: areturn
    //   96: astore 4
    //   98: lload_1
    //   99: invokestatic 134	android/os/Binder:restoreCallingIdentity	(J)V
    //   102: aload 4
    //   104: athrow
    //   105: aload_3
    //   106: invokestatic 124	android/os/StrictMode:setThreadPolicy	(Landroid/os/StrictMode$ThreadPolicy;)V
    //   109: aload 4
    //   111: athrow
    //   112: astore_3
    //   113: aload 4
    //   115: monitorexit
    //   116: aload_3
    //   117: athrow
    //   118: astore_3
    //   119: aload 4
    //   121: monitorexit
    //   122: aload_3
    //   123: athrow
    //   124: astore 4
    //   126: goto -55 -> 71
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	129	0	this	GservicesValue
    //   74	25	1	l	long
    //   4	102	3	localObject1	Object
    //   112	5	3	localThrowable1	Throwable
    //   118	5	3	localThrowable2	Throwable
    //   18	46	4	localObject2	Object
    //   66	1	4	localThrowable3	Throwable
    //   83	11	4	localObject3	Object
    //   96	24	4	localThrowable4	Throwable
    //   124	1	4	localSecurityException	SecurityException
    // Exception table:
    //   from	to	target	type
    //   51	59	66	java/lang/Throwable
    //   71	75	66	java/lang/Throwable
    //   85	89	66	java/lang/Throwable
    //   98	105	66	java/lang/Throwable
    //   75	85	96	java/lang/Throwable
    //   34	45	112	java/lang/Throwable
    //   113	116	112	java/lang/Throwable
    //   23	26	118	java/lang/Throwable
    //   119	122	118	java/lang/Throwable
    //   51	59	124	java/lang/SecurityException
  }
  
  abstract interface zza
  {
    public abstract Float convertTo(String paramString, Float paramFloat);
    
    public abstract Boolean getBooleanAttribute(String paramString, Boolean paramBoolean);
    
    public abstract Long getLong(String paramString, Long paramLong);
    
    public abstract String getString(String paramString1, String paramString2);
    
    public abstract Integer setDns1(String paramString, Integer paramInteger);
  }
}
