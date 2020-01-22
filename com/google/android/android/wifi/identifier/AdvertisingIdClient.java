package com.google.android.android.wifi.identifier;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.SystemClock;
import android.util.Log;
import com.google.android.android.common.BlockingServiceConnection;
import com.google.android.android.common.GoogleApiAvailabilityLight;
import com.google.android.android.common.GooglePlayServicesNotAvailableException;
import com.google.android.android.common.GooglePlayServicesRepairableException;
import com.google.android.android.common.internal.Preconditions;
import com.google.android.android.common.stats.ConnectionTracker;
import com.google.android.android.internal.ads_identifier.Data;
import com.google.android.android.internal.ads_identifier.IFileReadModuleService.Stub;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class AdvertisingIdClient
{
  private Data data;
  private final long finished;
  private final Context mContext;
  private final boolean mRoot;
  private BlockingServiceConnection name;
  private final Object next = new Object();
  private zza this$0;
  private boolean value;
  
  public AdvertisingIdClient(Context paramContext)
  {
    this(paramContext, 30000L, false, false);
  }
  
  private AdvertisingIdClient(Context paramContext, long paramLong, boolean paramBoolean1, boolean paramBoolean2)
  {
    Preconditions.checkNotNull(paramContext);
    Context localContext = paramContext;
    if (paramBoolean1)
    {
      localContext = paramContext.getApplicationContext();
      if (localContext == null) {
        localContext = paramContext;
      }
    }
    mContext = localContext;
    value = false;
    finished = paramLong;
    mRoot = paramBoolean2;
  }
  
  private final boolean add(Info paramInfo, boolean paramBoolean, float paramFloat, long paramLong, String paramString, Throwable paramThrowable)
  {
    if (Math.random() > paramFloat) {
      return false;
    }
    HashMap localHashMap = new HashMap();
    String str2 = "1";
    String str1;
    if (paramBoolean) {
      str1 = "1";
    } else {
      str1 = "0";
    }
    localHashMap.put("app_context", str1);
    if (paramInfo != null)
    {
      if (paramInfo.isLimitAdTrackingEnabled()) {
        str1 = str2;
      } else {
        str1 = "0";
      }
      localHashMap.put("limit_ad_tracking", str1);
    }
    if ((paramInfo != null) && (paramInfo.getId() != null)) {
      localHashMap.put("ad_id_size", Integer.toString(paramInfo.getId().length()));
    }
    if (paramThrowable != null) {
      localHashMap.put("error", paramThrowable.getClass().getName());
    }
    if ((paramString != null) && (!paramString.isEmpty())) {
      localHashMap.put("experiment_id", paramString);
    }
    localHashMap.put("tag", "AdvertisingIdClient");
    localHashMap.put("time_spent", Long.toString(paramLong));
    new SqlTileWriter.1(this, localHashMap).start();
    return true;
  }
  
  /* Error */
  private final void close()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 37	com/google/android/android/wifi/identifier/AdvertisingIdClient:next	Ljava/lang/Object;
    //   4: astore_1
    //   5: aload_1
    //   6: monitorenter
    //   7: aload_0
    //   8: getfield 148	com/google/android/android/wifi/identifier/AdvertisingIdClient:this$0	Lcom/google/android/android/wifi/identifier/AdvertisingIdClient$zza;
    //   11: ifnull +22 -> 33
    //   14: aload_0
    //   15: getfield 148	com/google/android/android/wifi/identifier/AdvertisingIdClient:this$0	Lcom/google/android/android/wifi/identifier/AdvertisingIdClient$zza;
    //   18: getfield 152	com/google/android/android/wifi/identifier/AdvertisingIdClient$zza:ready	Ljava/util/concurrent/CountDownLatch;
    //   21: invokevirtual 157	java/util/concurrent/CountDownLatch:countDown	()V
    //   24: aload_0
    //   25: getfield 148	com/google/android/android/wifi/identifier/AdvertisingIdClient:this$0	Lcom/google/android/android/wifi/identifier/AdvertisingIdClient$zza;
    //   28: astore_2
    //   29: aload_2
    //   30: invokevirtual 160	java/lang/Thread:join	()V
    //   33: aload_0
    //   34: getfield 55	com/google/android/android/wifi/identifier/AdvertisingIdClient:finished	J
    //   37: lconst_0
    //   38: lcmp
    //   39: ifle +19 -> 58
    //   42: aload_0
    //   43: new 8	com/google/android/android/wifi/identifier/AdvertisingIdClient$zza
    //   46: dup
    //   47: aload_0
    //   48: aload_0
    //   49: getfield 55	com/google/android/android/wifi/identifier/AdvertisingIdClient:finished	J
    //   52: invokespecial 163	com/google/android/android/wifi/identifier/AdvertisingIdClient$zza:<init>	(Lcom/google/android/android/wifi/identifier/AdvertisingIdClient;J)V
    //   55: putfield 148	com/google/android/android/wifi/identifier/AdvertisingIdClient:this$0	Lcom/google/android/android/wifi/identifier/AdvertisingIdClient$zza;
    //   58: aload_1
    //   59: monitorexit
    //   60: return
    //   61: astore_2
    //   62: aload_1
    //   63: monitorexit
    //   64: aload_2
    //   65: athrow
    //   66: astore_2
    //   67: goto -34 -> 33
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	70	0	this	AdvertisingIdClient
    //   4	59	1	localObject	Object
    //   28	2	2	localZza	zza
    //   61	4	2	localThrowable	Throwable
    //   66	1	2	localInterruptedException	InterruptedException
    // Exception table:
    //   from	to	target	type
    //   7	24	61	java/lang/Throwable
    //   29	33	61	java/lang/Throwable
    //   33	58	61	java/lang/Throwable
    //   58	60	61	java/lang/Throwable
    //   62	64	61	java/lang/Throwable
    //   29	33	66	java/lang/InterruptedException
  }
  
  /* Error */
  private final boolean get()
    throws IOException
  {
    // Byte code:
    //   0: ldc -84
    //   2: invokestatic 176	com/google/android/android/common/internal/Preconditions:checkNotMainThread	(Ljava/lang/String;)V
    //   5: aload_0
    //   6: monitorenter
    //   7: aload_0
    //   8: getfield 53	com/google/android/android/wifi/identifier/AdvertisingIdClient:value	Z
    //   11: ifne +83 -> 94
    //   14: aload_0
    //   15: getfield 37	com/google/android/android/wifi/identifier/AdvertisingIdClient:next	Ljava/lang/Object;
    //   18: astore_2
    //   19: aload_2
    //   20: monitorenter
    //   21: aload_0
    //   22: getfield 148	com/google/android/android/wifi/identifier/AdvertisingIdClient:this$0	Lcom/google/android/android/wifi/identifier/AdvertisingIdClient$zza;
    //   25: ifnull +54 -> 79
    //   28: aload_0
    //   29: getfield 148	com/google/android/android/wifi/identifier/AdvertisingIdClient:this$0	Lcom/google/android/android/wifi/identifier/AdvertisingIdClient$zza;
    //   32: getfield 177	com/google/android/android/wifi/identifier/AdvertisingIdClient$zza:value	Z
    //   35: ifeq +44 -> 79
    //   38: aload_2
    //   39: monitorexit
    //   40: aload_0
    //   41: iconst_0
    //   42: invokespecial 181	com/google/android/android/wifi/identifier/AdvertisingIdClient:init	(Z)V
    //   45: aload_0
    //   46: getfield 53	com/google/android/android/wifi/identifier/AdvertisingIdClient:value	Z
    //   49: istore_1
    //   50: iload_1
    //   51: ifeq +6 -> 57
    //   54: goto +40 -> 94
    //   57: new 166	java/io/IOException
    //   60: dup
    //   61: ldc -73
    //   63: invokespecial 185	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   66: athrow
    //   67: astore_2
    //   68: new 166	java/io/IOException
    //   71: dup
    //   72: ldc -73
    //   74: aload_2
    //   75: invokespecial 188	java/io/IOException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   78: athrow
    //   79: new 166	java/io/IOException
    //   82: dup
    //   83: ldc -66
    //   85: invokespecial 185	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   88: athrow
    //   89: astore_3
    //   90: aload_2
    //   91: monitorexit
    //   92: aload_3
    //   93: athrow
    //   94: aload_0
    //   95: getfield 192	com/google/android/android/wifi/identifier/AdvertisingIdClient:name	Lcom/google/android/android/common/BlockingServiceConnection;
    //   98: invokestatic 43	com/google/android/android/common/internal/Preconditions:checkNotNull	(Ljava/lang/Object;)Ljava/lang/Object;
    //   101: pop
    //   102: aload_0
    //   103: getfield 194	com/google/android/android/wifi/identifier/AdvertisingIdClient:data	Lcom/google/android/android/internal/ads_identifier/Data;
    //   106: invokestatic 43	com/google/android/android/common/internal/Preconditions:checkNotNull	(Ljava/lang/Object;)Ljava/lang/Object;
    //   109: pop
    //   110: aload_0
    //   111: getfield 194	com/google/android/android/wifi/identifier/AdvertisingIdClient:data	Lcom/google/android/android/internal/ads_identifier/Data;
    //   114: astore_2
    //   115: aload_2
    //   116: invokeinterface 199 1 0
    //   121: istore_1
    //   122: aload_0
    //   123: monitorexit
    //   124: aload_0
    //   125: invokespecial 201	com/google/android/android/wifi/identifier/AdvertisingIdClient:close	()V
    //   128: iload_1
    //   129: ireturn
    //   130: astore_2
    //   131: ldc 124
    //   133: ldc -53
    //   135: aload_2
    //   136: invokestatic 209	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   139: pop
    //   140: new 166	java/io/IOException
    //   143: dup
    //   144: ldc -45
    //   146: invokespecial 185	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   149: athrow
    //   150: astore_2
    //   151: aload_0
    //   152: monitorexit
    //   153: aload_2
    //   154: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	155	0	this	AdvertisingIdClient
    //   49	80	1	bool	boolean
    //   18	21	2	localObject	Object
    //   67	24	2	localException	Exception
    //   114	2	2	localData	Data
    //   130	6	2	localRemoteException	android.os.RemoteException
    //   150	4	2	localThrowable1	Throwable
    //   89	4	3	localThrowable2	Throwable
    // Exception table:
    //   from	to	target	type
    //   40	45	67	java/lang/Exception
    //   21	40	89	java/lang/Throwable
    //   79	89	89	java/lang/Throwable
    //   90	92	89	java/lang/Throwable
    //   115	122	130	android/os/RemoteException
    //   7	21	150	java/lang/Throwable
    //   40	45	150	java/lang/Throwable
    //   45	50	150	java/lang/Throwable
    //   57	67	150	java/lang/Throwable
    //   68	79	150	java/lang/Throwable
    //   92	94	150	java/lang/Throwable
    //   94	110	150	java/lang/Throwable
    //   115	122	150	java/lang/Throwable
    //   122	124	150	java/lang/Throwable
    //   131	150	150	java/lang/Throwable
    //   151	153	150	java/lang/Throwable
  }
  
  public static Info getAdvertisingIdInfo(Context paramContext)
    throws IOException, IllegalStateException, GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException
  {
    Object localObject = new AndroidPreferences(paramContext);
    boolean bool = ((AndroidPreferences)localObject).getBoolean("gads:ad_id_app_context:enabled", false);
    float f = ((AndroidPreferences)localObject).getFloat("gads:ad_id_app_context:ping_ratio", 0.0F);
    String str = ((AndroidPreferences)localObject).getString("gads:ad_id_use_shared_preference:experiment_id", "");
    paramContext = new AdvertisingIdClient(paramContext, -1L, bool, ((AndroidPreferences)localObject).getBoolean("gads:ad_id_use_persistent_service:enabled", false));
    try
    {
      long l1 = SystemClock.elapsedRealtime();
      paramContext.init(false);
      localObject = paramContext.getInfo();
      long l2 = SystemClock.elapsedRealtime();
      paramContext.add((Info)localObject, bool, f, l2 - l1, str, null);
      paramContext.finish();
      return localObject;
    }
    catch (Throwable localThrowable2)
    {
      try
      {
        paramContext.add(null, bool, f, -1L, str, localThrowable2);
        throw localThrowable2;
      }
      catch (Throwable localThrowable1)
      {
        paramContext.finish();
        throw localThrowable1;
      }
    }
  }
  
  private static Data getInstance(Context paramContext, BlockingServiceConnection paramBlockingServiceConnection)
    throws IOException
  {
    paramContext = TimeUnit.MILLISECONDS;
    try
    {
      paramContext = IFileReadModuleService.Stub.asInterface(paramBlockingServiceConnection.getServiceWithTimeout(10000L, paramContext));
      return paramContext;
    }
    catch (Throwable paramContext)
    {
      throw new IOException(paramContext);
      throw new IOException("Interrupted exception");
    }
    catch (InterruptedException paramContext)
    {
      for (;;) {}
    }
  }
  
  public static boolean getIsAdIdFakeForDebugLogging(Context paramContext)
    throws IOException, GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException
  {
    AndroidPreferences localAndroidPreferences = new AndroidPreferences(paramContext);
    paramContext = new AdvertisingIdClient(paramContext, -1L, localAndroidPreferences.getBoolean("gads:ad_id_app_context:enabled", false), localAndroidPreferences.getBoolean("com.google.android.gms.ads.identifier.service.PERSISTENT_START", false));
    try
    {
      paramContext.init(false);
      boolean bool = paramContext.get();
      paramContext.finish();
      return bool;
    }
    catch (Throwable localThrowable)
    {
      paramContext.finish();
      throw localThrowable;
    }
  }
  
  private final void init(boolean paramBoolean)
    throws IOException, IllegalStateException, GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException
  {
    Preconditions.checkNotMainThread("Calling this from your main thread can lead to deadlock");
    try
    {
      if (value) {
        finish();
      }
      name = initialize(mContext, mRoot);
      data = getInstance(mContext, name);
      value = true;
      if (paramBoolean) {
        close();
      }
      return;
    }
    catch (Throwable localThrowable)
    {
      throw localThrowable;
    }
  }
  
  private static BlockingServiceConnection initialize(Context paramContext, boolean paramBoolean)
    throws IOException, GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException
  {
    try
    {
      paramContext.getPackageManager().getPackageInfo("com.android.vending", 0);
      int i = GoogleApiAvailabilityLight.getInstance().isGooglePlayServicesAvailable(paramContext, 12451000);
      if ((i != 0) && (i != 2)) {
        throw new IOException("Google Play services not available");
      }
      if (paramBoolean) {
        localObject = "com.google.android.gms.ads.identifier.service.PERSISTENT_START";
      } else {
        localObject = "com.google.android.gms.ads.identifier.service.START";
      }
      BlockingServiceConnection localBlockingServiceConnection = new BlockingServiceConnection();
      Object localObject = new Intent((String)localObject);
      ((Intent)localObject).setPackage("com.google.android.gms");
      try
      {
        paramBoolean = ConnectionTracker.getInstance().bindService(paramContext, (Intent)localObject, localBlockingServiceConnection, 1);
        if (paramBoolean) {
          return localBlockingServiceConnection;
        }
        throw new IOException("Connection failure");
      }
      catch (Throwable paramContext)
      {
        throw new IOException(paramContext);
      }
    }
    catch (PackageManager.NameNotFoundException paramContext)
    {
      for (;;) {}
    }
    throw new GooglePlayServicesNotAvailableException(9);
  }
  
  public static void setShouldSkipGmsCoreVersionCheck(boolean paramBoolean) {}
  
  protected void finalize()
    throws Throwable
  {
    finish();
    super.finalize();
  }
  
  public final void finish()
  {
    Preconditions.checkNotMainThread("Calling this from your main thread can lead to deadlock");
    try
    {
      if (mContext != null)
      {
        BlockingServiceConnection localBlockingServiceConnection = name;
        if (localBlockingServiceConnection != null)
        {
          try
          {
            if (value) {
              ConnectionTracker.getInstance().unbindService(mContext, name);
            }
          }
          catch (Throwable localThrowable1)
          {
            Log.i("AdvertisingIdClient", "AdvertisingIdClient unbindService failed.", localThrowable1);
          }
          value = false;
          data = null;
          name = null;
          return;
        }
      }
      return;
    }
    catch (Throwable localThrowable2)
    {
      throw localThrowable2;
    }
  }
  
  /* Error */
  public Info getInfo()
    throws IOException
  {
    // Byte code:
    //   0: ldc -84
    //   2: invokestatic 176	com/google/android/android/common/internal/Preconditions:checkNotMainThread	(Ljava/lang/String;)V
    //   5: aload_0
    //   6: monitorenter
    //   7: aload_0
    //   8: getfield 53	com/google/android/android/wifi/identifier/AdvertisingIdClient:value	Z
    //   11: ifne +83 -> 94
    //   14: aload_0
    //   15: getfield 37	com/google/android/android/wifi/identifier/AdvertisingIdClient:next	Ljava/lang/Object;
    //   18: astore_2
    //   19: aload_2
    //   20: monitorenter
    //   21: aload_0
    //   22: getfield 148	com/google/android/android/wifi/identifier/AdvertisingIdClient:this$0	Lcom/google/android/android/wifi/identifier/AdvertisingIdClient$zza;
    //   25: ifnull +54 -> 79
    //   28: aload_0
    //   29: getfield 148	com/google/android/android/wifi/identifier/AdvertisingIdClient:this$0	Lcom/google/android/android/wifi/identifier/AdvertisingIdClient$zza;
    //   32: getfield 177	com/google/android/android/wifi/identifier/AdvertisingIdClient$zza:value	Z
    //   35: ifeq +44 -> 79
    //   38: aload_2
    //   39: monitorexit
    //   40: aload_0
    //   41: iconst_0
    //   42: invokespecial 181	com/google/android/android/wifi/identifier/AdvertisingIdClient:init	(Z)V
    //   45: aload_0
    //   46: getfield 53	com/google/android/android/wifi/identifier/AdvertisingIdClient:value	Z
    //   49: istore_1
    //   50: iload_1
    //   51: ifeq +6 -> 57
    //   54: goto +40 -> 94
    //   57: new 166	java/io/IOException
    //   60: dup
    //   61: ldc -73
    //   63: invokespecial 185	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   66: athrow
    //   67: astore_2
    //   68: new 166	java/io/IOException
    //   71: dup
    //   72: ldc -73
    //   74: aload_2
    //   75: invokespecial 188	java/io/IOException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   78: athrow
    //   79: new 166	java/io/IOException
    //   82: dup
    //   83: ldc -66
    //   85: invokespecial 185	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   88: athrow
    //   89: astore_3
    //   90: aload_2
    //   91: monitorexit
    //   92: aload_3
    //   93: athrow
    //   94: aload_0
    //   95: getfield 192	com/google/android/android/wifi/identifier/AdvertisingIdClient:name	Lcom/google/android/android/common/BlockingServiceConnection;
    //   98: invokestatic 43	com/google/android/android/common/internal/Preconditions:checkNotNull	(Ljava/lang/Object;)Ljava/lang/Object;
    //   101: pop
    //   102: aload_0
    //   103: getfield 194	com/google/android/android/wifi/identifier/AdvertisingIdClient:data	Lcom/google/android/android/internal/ads_identifier/Data;
    //   106: invokestatic 43	com/google/android/android/common/internal/Preconditions:checkNotNull	(Ljava/lang/Object;)Ljava/lang/Object;
    //   109: pop
    //   110: aload_0
    //   111: getfield 194	com/google/android/android/wifi/identifier/AdvertisingIdClient:data	Lcom/google/android/android/internal/ads_identifier/Data;
    //   114: astore_2
    //   115: aload_2
    //   116: invokeinterface 365 1 0
    //   121: astore_2
    //   122: aload_0
    //   123: getfield 194	com/google/android/android/wifi/identifier/AdvertisingIdClient:data	Lcom/google/android/android/internal/ads_identifier/Data;
    //   126: astore_3
    //   127: new 6	com/google/android/android/wifi/identifier/AdvertisingIdClient$Info
    //   130: dup
    //   131: aload_2
    //   132: aload_3
    //   133: iconst_1
    //   134: invokeinterface 368 2 0
    //   139: invokespecial 371	com/google/android/android/wifi/identifier/AdvertisingIdClient$Info:<init>	(Ljava/lang/String;Z)V
    //   142: astore_2
    //   143: aload_0
    //   144: monitorexit
    //   145: aload_0
    //   146: invokespecial 201	com/google/android/android/wifi/identifier/AdvertisingIdClient:close	()V
    //   149: aload_2
    //   150: areturn
    //   151: astore_2
    //   152: ldc 124
    //   154: ldc -53
    //   156: aload_2
    //   157: invokestatic 209	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   160: pop
    //   161: new 166	java/io/IOException
    //   164: dup
    //   165: ldc -45
    //   167: invokespecial 185	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   170: athrow
    //   171: astore_2
    //   172: aload_0
    //   173: monitorexit
    //   174: aload_2
    //   175: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	176	0	this	AdvertisingIdClient
    //   49	2	1	bool	boolean
    //   18	21	2	localObject1	Object
    //   67	24	2	localException	Exception
    //   114	36	2	localObject2	Object
    //   151	6	2	localRemoteException	android.os.RemoteException
    //   171	4	2	localThrowable1	Throwable
    //   89	4	3	localThrowable2	Throwable
    //   126	7	3	localData	Data
    // Exception table:
    //   from	to	target	type
    //   40	45	67	java/lang/Exception
    //   21	40	89	java/lang/Throwable
    //   79	89	89	java/lang/Throwable
    //   90	92	89	java/lang/Throwable
    //   115	122	151	android/os/RemoteException
    //   127	143	151	android/os/RemoteException
    //   7	21	171	java/lang/Throwable
    //   40	45	171	java/lang/Throwable
    //   45	50	171	java/lang/Throwable
    //   57	67	171	java/lang/Throwable
    //   68	79	171	java/lang/Throwable
    //   92	94	171	java/lang/Throwable
    //   94	115	171	java/lang/Throwable
    //   115	122	171	java/lang/Throwable
    //   127	143	171	java/lang/Throwable
    //   143	145	171	java/lang/Throwable
    //   152	171	171	java/lang/Throwable
    //   172	174	171	java/lang/Throwable
  }
  
  public void start()
    throws IOException, IllegalStateException, GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException
  {
    init(true);
  }
  
  public final class Info
  {
    private final boolean mIsValid;
    
    public Info(boolean paramBoolean)
    {
      mIsValid = paramBoolean;
    }
    
    public final String getId()
    {
      return AdvertisingIdClient.this;
    }
    
    public final boolean isLimitAdTrackingEnabled()
    {
      return mIsValid;
    }
    
    public final String toString()
    {
      String str = AdvertisingIdClient.this;
      boolean bool = mIsValid;
      StringBuilder localStringBuilder = new StringBuilder(String.valueOf(str).length() + 7);
      localStringBuilder.append("{");
      localStringBuilder.append(str);
      localStringBuilder.append("}");
      localStringBuilder.append(bool);
      return localStringBuilder.toString();
    }
  }
  
  final class zza
    extends Thread
  {
    private WeakReference<com.google.android.gms.ads.identifier.AdvertisingIdClient> instance;
    private long lock;
    CountDownLatch ready;
    boolean value;
    
    public zza(long paramLong)
    {
      instance = new WeakReference(this$1);
      lock = paramLong;
      ready = new CountDownLatch(1);
      value = false;
      start();
    }
    
    private final void disconnect()
    {
      AdvertisingIdClient localAdvertisingIdClient = (AdvertisingIdClient)instance.get();
      if (localAdvertisingIdClient != null)
      {
        localAdvertisingIdClient.finish();
        value = true;
      }
    }
    
    public final void run()
    {
      CountDownLatch localCountDownLatch = ready;
      long l = lock;
      TimeUnit localTimeUnit = TimeUnit.MILLISECONDS;
      try
      {
        boolean bool = localCountDownLatch.await(l, localTimeUnit);
        if (bool) {
          return;
        }
        disconnect();
        return;
      }
      catch (InterruptedException localInterruptedException)
      {
        for (;;) {}
      }
      disconnect();
      return;
    }
  }
}
