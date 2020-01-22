package com.google.android.android.dynamite;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.android.common.internal.Preconditions;
import com.google.android.android.dynamic.ObjectWrapper;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public final class DynamiteModule
{
  public static final VersionPolicy PREFER_HIGHEST_OR_LOCAL_VERSION = new SolidColor();
  public static final VersionPolicy PREFER_HIGHEST_OR_LOCAL_VERSION_NO_FORCE_STAGING = new Marker();
  public static final VersionPolicy PREFER_HIGHEST_OR_REMOTE_VERSION = new UnsignedInteger();
  public static final VersionPolicy PREFER_REMOTE;
  private static Boolean zzid;
  private static HttpClient zzie;
  private static CharArray zzif;
  private static String zzig;
  private static int zzih;
  private static final ThreadLocal<com.google.android.gms.dynamite.DynamiteModule.zza> zzii = new ThreadLocal();
  private static final DynamiteModule.VersionPolicy.zza zzij = new Max();
  private static final VersionPolicy zzik;
  private static final VersionPolicy zzil = new Key();
  private final Context zzim;
  
  static
  {
    PREFER_REMOTE = new Macro();
    zzik = new NameFilter();
  }
  
  private DynamiteModule(Context paramContext)
  {
    zzim = ((Context)Preconditions.checkNotNull(paramContext));
  }
  
  /* Error */
  public static int create(Context paramContext, String paramString, boolean paramBoolean)
  {
    // Byte code:
    //   0: ldc 111
    //   2: monitorenter
    //   3: getstatic 113	com/google/android/android/dynamite/DynamiteModule:zzid	Ljava/lang/Boolean;
    //   6: astore 6
    //   8: aload 6
    //   10: astore 5
    //   12: aload 6
    //   14: ifnonnull +304 -> 318
    //   17: aload_0
    //   18: invokevirtual 117	android/content/Context:getApplicationContext	()Landroid/content/Context;
    //   21: invokevirtual 121	android/content/Context:getClassLoader	()Ljava/lang/ClassLoader;
    //   24: astore 5
    //   26: aload 5
    //   28: ldc 123
    //   30: invokevirtual 129	java/lang/Class:getName	()Ljava/lang/String;
    //   33: invokevirtual 135	java/lang/ClassLoader:loadClass	(Ljava/lang/String;)Ljava/lang/Class;
    //   36: astore 6
    //   38: aload 6
    //   40: ldc -119
    //   42: invokevirtual 141	java/lang/Class:getDeclaredField	(Ljava/lang/String;)Ljava/lang/reflect/Field;
    //   45: astore 5
    //   47: aload 6
    //   49: monitorenter
    //   50: aload 5
    //   52: aconst_null
    //   53: invokevirtual 146	java/lang/reflect/Field:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   56: checkcast 131	java/lang/ClassLoader
    //   59: astore 7
    //   61: aload 7
    //   63: ifnull +32 -> 95
    //   66: aload 7
    //   68: invokestatic 149	java/lang/ClassLoader:getSystemClassLoader	()Ljava/lang/ClassLoader;
    //   71: if_acmpne +11 -> 82
    //   74: getstatic 154	java/lang/Boolean:FALSE	Ljava/lang/Boolean;
    //   77: astore 5
    //   79: goto +149 -> 228
    //   82: aload 7
    //   84: invokestatic 158	com/google/android/android/dynamite/DynamiteModule:getInstance	(Ljava/lang/ClassLoader;)V
    //   87: getstatic 161	java/lang/Boolean:TRUE	Ljava/lang/Boolean;
    //   90: astore 5
    //   92: goto +136 -> 228
    //   95: ldc -93
    //   97: aload_0
    //   98: invokevirtual 117	android/content/Context:getApplicationContext	()Landroid/content/Context;
    //   101: invokevirtual 166	android/content/Context:getPackageName	()Ljava/lang/String;
    //   104: invokevirtual 172	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   107: ifeq +20 -> 127
    //   110: aload 5
    //   112: aconst_null
    //   113: invokestatic 149	java/lang/ClassLoader:getSystemClassLoader	()Ljava/lang/ClassLoader;
    //   116: invokevirtual 176	java/lang/reflect/Field:set	(Ljava/lang/Object;Ljava/lang/Object;)V
    //   119: getstatic 154	java/lang/Boolean:FALSE	Ljava/lang/Boolean;
    //   122: astore 5
    //   124: goto +104 -> 228
    //   127: aload_0
    //   128: aload_1
    //   129: iload_2
    //   130: invokestatic 179	com/google/android/android/dynamite/DynamiteModule:lookup	(Landroid/content/Context;Ljava/lang/String;Z)I
    //   133: istore_3
    //   134: getstatic 181	com/google/android/android/dynamite/DynamiteModule:zzig	Ljava/lang/String;
    //   137: ifnull +69 -> 206
    //   140: getstatic 181	com/google/android/android/dynamite/DynamiteModule:zzig	Ljava/lang/String;
    //   143: astore 7
    //   145: aload 7
    //   147: invokevirtual 185	java/lang/String:isEmpty	()Z
    //   150: istore 4
    //   152: iload 4
    //   154: ifeq +6 -> 160
    //   157: goto +49 -> 206
    //   160: getstatic 181	com/google/android/android/dynamite/DynamiteModule:zzig	Ljava/lang/String;
    //   163: astore 7
    //   165: new 187	com/google/android/android/dynamite/Loader
    //   168: dup
    //   169: aload 7
    //   171: invokestatic 149	java/lang/ClassLoader:getSystemClassLoader	()Ljava/lang/ClassLoader;
    //   174: invokespecial 190	com/google/android/android/dynamite/Loader:<init>	(Ljava/lang/String;Ljava/lang/ClassLoader;)V
    //   177: astore 7
    //   179: aload 7
    //   181: invokestatic 158	com/google/android/android/dynamite/DynamiteModule:getInstance	(Ljava/lang/ClassLoader;)V
    //   184: aload 5
    //   186: aconst_null
    //   187: aload 7
    //   189: invokevirtual 176	java/lang/reflect/Field:set	(Ljava/lang/Object;Ljava/lang/Object;)V
    //   192: getstatic 161	java/lang/Boolean:TRUE	Ljava/lang/Boolean;
    //   195: putstatic 113	com/google/android/android/dynamite/DynamiteModule:zzid	Ljava/lang/Boolean;
    //   198: aload 6
    //   200: monitorexit
    //   201: ldc 111
    //   203: monitorexit
    //   204: iload_3
    //   205: ireturn
    //   206: aload 6
    //   208: monitorexit
    //   209: ldc 111
    //   211: monitorexit
    //   212: iload_3
    //   213: ireturn
    //   214: aload 5
    //   216: aconst_null
    //   217: invokestatic 149	java/lang/ClassLoader:getSystemClassLoader	()Ljava/lang/ClassLoader;
    //   220: invokevirtual 176	java/lang/reflect/Field:set	(Ljava/lang/Object;Ljava/lang/Object;)V
    //   223: getstatic 154	java/lang/Boolean:FALSE	Ljava/lang/Boolean;
    //   226: astore 5
    //   228: aload 6
    //   230: monitorexit
    //   231: goto +82 -> 313
    //   234: astore 5
    //   236: aload 6
    //   238: monitorexit
    //   239: aload 5
    //   241: athrow
    //   242: astore 5
    //   244: goto +10 -> 254
    //   247: astore 5
    //   249: goto +5 -> 254
    //   252: astore 5
    //   254: aload 5
    //   256: invokestatic 194	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   259: astore 5
    //   261: new 196	java/lang/StringBuilder
    //   264: dup
    //   265: aload 5
    //   267: invokestatic 194	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   270: invokevirtual 200	java/lang/String:length	()I
    //   273: bipush 30
    //   275: iadd
    //   276: invokespecial 203	java/lang/StringBuilder:<init>	(I)V
    //   279: astore 6
    //   281: aload 6
    //   283: ldc -51
    //   285: invokevirtual 209	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   288: pop
    //   289: aload 6
    //   291: aload 5
    //   293: invokevirtual 209	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   296: pop
    //   297: ldc -45
    //   299: aload 6
    //   301: invokevirtual 214	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   304: invokestatic 220	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
    //   307: pop
    //   308: getstatic 154	java/lang/Boolean:FALSE	Ljava/lang/Boolean;
    //   311: astore 5
    //   313: aload 5
    //   315: putstatic 113	com/google/android/android/dynamite/DynamiteModule:zzid	Ljava/lang/Boolean;
    //   318: ldc 111
    //   320: monitorexit
    //   321: aload 5
    //   323: invokevirtual 223	java/lang/Boolean:booleanValue	()Z
    //   326: istore 4
    //   328: iload 4
    //   330: ifeq +59 -> 389
    //   333: aload_0
    //   334: aload_1
    //   335: iload_2
    //   336: invokestatic 179	com/google/android/android/dynamite/DynamiteModule:lookup	(Landroid/content/Context;Ljava/lang/String;Z)I
    //   339: istore_3
    //   340: iload_3
    //   341: ireturn
    //   342: astore_1
    //   343: aload_1
    //   344: invokevirtual 228	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   347: invokestatic 194	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   350: astore_1
    //   351: aload_1
    //   352: invokevirtual 200	java/lang/String:length	()I
    //   355: istore_3
    //   356: iload_3
    //   357: ifeq +13 -> 370
    //   360: ldc -26
    //   362: aload_1
    //   363: invokevirtual 234	java/lang/String:concat	(Ljava/lang/String;)Ljava/lang/String;
    //   366: astore_1
    //   367: goto +13 -> 380
    //   370: new 168	java/lang/String
    //   373: dup
    //   374: ldc -26
    //   376: invokespecial 237	java/lang/String:<init>	(Ljava/lang/String;)V
    //   379: astore_1
    //   380: ldc -45
    //   382: aload_1
    //   383: invokestatic 220	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
    //   386: pop
    //   387: iconst_0
    //   388: ireturn
    //   389: aload_0
    //   390: aload_1
    //   391: iload_2
    //   392: invokestatic 239	com/google/android/android/dynamite/DynamiteModule:get	(Landroid/content/Context;Ljava/lang/String;Z)I
    //   395: istore_3
    //   396: iload_3
    //   397: ireturn
    //   398: astore_1
    //   399: ldc 111
    //   401: monitorexit
    //   402: aload_1
    //   403: athrow
    //   404: astore_1
    //   405: aload_0
    //   406: aload_1
    //   407: invokestatic 245	com/google/android/android/common/util/CrashUtils:addDynamiteErrorToDropBox	(Landroid/content/Context;Ljava/lang/Throwable;)Z
    //   410: pop
    //   411: aload_1
    //   412: athrow
    //   413: astore 5
    //   415: goto -328 -> 87
    //   418: astore 7
    //   420: goto -206 -> 214
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	423	0	paramContext	Context
    //   0	423	1	paramString	String
    //   0	423	2	paramBoolean	boolean
    //   133	264	3	i	int
    //   150	179	4	bool	boolean
    //   10	217	5	localObject1	Object
    //   234	6	5	localThrowable	Throwable
    //   242	1	5	localNoSuchFieldException	NoSuchFieldException
    //   247	1	5	localIllegalAccessException	IllegalAccessException
    //   252	3	5	localClassNotFoundException	ClassNotFoundException
    //   259	63	5	localObject2	Object
    //   413	1	5	localLoadingException1	LoadingException
    //   6	294	6	localObject3	Object
    //   59	129	7	localObject4	Object
    //   418	1	7	localLoadingException2	LoadingException
    // Exception table:
    //   from	to	target	type
    //   50	61	234	java/lang/Throwable
    //   66	79	234	java/lang/Throwable
    //   82	87	234	java/lang/Throwable
    //   87	92	234	java/lang/Throwable
    //   95	124	234	java/lang/Throwable
    //   127	134	234	java/lang/Throwable
    //   134	145	234	java/lang/Throwable
    //   145	152	234	java/lang/Throwable
    //   160	165	234	java/lang/Throwable
    //   165	192	234	java/lang/Throwable
    //   192	201	234	java/lang/Throwable
    //   206	209	234	java/lang/Throwable
    //   214	228	234	java/lang/Throwable
    //   228	231	234	java/lang/Throwable
    //   236	239	234	java/lang/Throwable
    //   17	26	242	java/lang/NoSuchFieldException
    //   26	47	242	java/lang/NoSuchFieldException
    //   239	242	242	java/lang/NoSuchFieldException
    //   17	26	247	java/lang/IllegalAccessException
    //   26	47	247	java/lang/IllegalAccessException
    //   239	242	247	java/lang/IllegalAccessException
    //   17	26	252	java/lang/ClassNotFoundException
    //   26	47	252	java/lang/ClassNotFoundException
    //   239	242	252	java/lang/ClassNotFoundException
    //   333	340	342	com/google/android/android/dynamite/DynamiteModule$LoadingException
    //   3	8	398	java/lang/Throwable
    //   17	26	398	java/lang/Throwable
    //   26	47	398	java/lang/Throwable
    //   47	50	398	java/lang/Throwable
    //   201	204	398	java/lang/Throwable
    //   209	212	398	java/lang/Throwable
    //   239	242	398	java/lang/Throwable
    //   254	313	398	java/lang/Throwable
    //   313	318	398	java/lang/Throwable
    //   318	321	398	java/lang/Throwable
    //   399	402	398	java/lang/Throwable
    //   0	3	404	java/lang/Throwable
    //   321	328	404	java/lang/Throwable
    //   333	340	404	java/lang/Throwable
    //   343	356	404	java/lang/Throwable
    //   360	367	404	java/lang/Throwable
    //   370	380	404	java/lang/Throwable
    //   380	387	404	java/lang/Throwable
    //   389	396	404	java/lang/Throwable
    //   402	404	404	java/lang/Throwable
    //   82	87	413	com/google/android/android/dynamite/DynamiteModule$LoadingException
    //   127	134	418	com/google/android/android/dynamite/DynamiteModule$LoadingException
    //   145	152	418	com/google/android/android/dynamite/DynamiteModule$LoadingException
    //   165	192	418	com/google/android/android/dynamite/DynamiteModule$LoadingException
  }
  
  /* Error */
  private static DynamiteModule execute(Context paramContext, String paramString, int paramInt)
    throws DynamiteModule.LoadingException
  {
    // Byte code:
    //   0: ldc 111
    //   2: monitorenter
    //   3: getstatic 113	com/google/android/android/dynamite/DynamiteModule:zzid	Ljava/lang/Boolean;
    //   6: astore 4
    //   8: ldc 111
    //   10: monitorexit
    //   11: aload 4
    //   13: ifnull +31 -> 44
    //   16: aload 4
    //   18: invokevirtual 223	java/lang/Boolean:booleanValue	()Z
    //   21: istore_3
    //   22: iload_3
    //   23: ifeq +12 -> 35
    //   26: aload_0
    //   27: aload_1
    //   28: iload_2
    //   29: invokestatic 250	com/google/android/android/dynamite/DynamiteModule:update	(Landroid/content/Context;Ljava/lang/String;I)Lcom/google/android/android/dynamite/DynamiteModule;
    //   32: astore_1
    //   33: aload_1
    //   34: areturn
    //   35: aload_0
    //   36: aload_1
    //   37: iload_2
    //   38: invokestatic 252	com/google/android/android/dynamite/DynamiteModule:get	(Landroid/content/Context;Ljava/lang/String;I)Lcom/google/android/android/dynamite/DynamiteModule;
    //   41: astore_1
    //   42: aload_1
    //   43: areturn
    //   44: new 8	com/google/android/android/dynamite/DynamiteModule$LoadingException
    //   47: dup
    //   48: ldc -2
    //   50: aconst_null
    //   51: invokespecial 257	com/google/android/android/dynamite/DynamiteModule$LoadingException:<init>	(Ljava/lang/String;Lcom/google/android/android/dynamite/Max;)V
    //   54: athrow
    //   55: astore_1
    //   56: ldc 111
    //   58: monitorexit
    //   59: aload_1
    //   60: athrow
    //   61: astore_1
    //   62: aload_0
    //   63: aload_1
    //   64: invokestatic 245	com/google/android/android/common/util/CrashUtils:addDynamiteErrorToDropBox	(Landroid/content/Context;Ljava/lang/Throwable;)Z
    //   67: pop
    //   68: aload_1
    //   69: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	70	0	paramContext	Context
    //   0	70	1	paramString	String
    //   0	70	2	paramInt	int
    //   21	2	3	bool	boolean
    //   6	11	4	localBoolean	Boolean
    // Exception table:
    //   from	to	target	type
    //   3	11	55	java/lang/Throwable
    //   56	59	55	java/lang/Throwable
    //   0	3	61	java/lang/Throwable
    //   16	22	61	java/lang/Throwable
    //   26	33	61	java/lang/Throwable
    //   35	42	61	java/lang/Throwable
    //   44	55	61	java/lang/Throwable
    //   59	61	61	java/lang/Throwable
  }
  
  private static int get(Context paramContext, String paramString, boolean paramBoolean)
  {
    HttpClient localHttpClient = get(paramContext);
    if (localHttpClient == null) {
      return 0;
    }
    try
    {
      int i = localHttpClient.zzaj();
      if (i >= 2)
      {
        i = localHttpClient.get(ObjectWrapper.wrap(paramContext), paramString, paramBoolean);
        return i;
      }
      Log.w("DynamiteModule", "IDynamite loader version < 2, falling back to getModuleVersion2");
      i = localHttpClient.execute(ObjectWrapper.wrap(paramContext), paramString, paramBoolean);
      return i;
    }
    catch (RemoteException paramContext)
    {
      paramContext = String.valueOf(paramContext.getMessage());
      if (paramContext.length() != 0) {
        paramContext = "Failed to retrieve remote module version: ".concat(paramContext);
      } else {
        paramContext = new String("Failed to retrieve remote module version: ");
      }
      Log.w("DynamiteModule", paramContext);
    }
    return 0;
  }
  
  private static Context get(Context paramContext, String paramString, int paramInt, Cursor paramCursor, CharArray paramCharArray)
  {
    try
    {
      ObjectWrapper.wrap(null);
      boolean bool = zzai().booleanValue();
      if (bool)
      {
        Log.v("DynamiteModule", "Dynamite loader version >= 2, using loadModule2NoCrashUtils");
        paramContext = paramCharArray.execute(ObjectWrapper.wrap(paramContext), paramString, paramInt, ObjectWrapper.wrap(paramCursor));
      }
      else
      {
        Log.w("DynamiteModule", "Dynamite loader version < 2, falling back to loadModule2");
        paramContext = paramCharArray.get(ObjectWrapper.wrap(paramContext), paramString, paramInt, ObjectWrapper.wrap(paramCursor));
      }
      paramContext = ObjectWrapper.unwrap(paramContext);
      return (Context)paramContext;
    }
    catch (Exception paramContext)
    {
      paramContext = String.valueOf(paramContext.toString());
      if (paramContext.length() != 0) {
        paramContext = "Failed to load DynamiteLoader: ".concat(paramContext);
      } else {
        paramContext = new String("Failed to load DynamiteLoader: ");
      }
      Log.e("DynamiteModule", paramContext);
    }
    return null;
  }
  
  private static DynamiteModule get(Context paramContext, String paramString, int paramInt)
    throws DynamiteModule.LoadingException
  {
    Object localObject = new StringBuilder(String.valueOf(paramString).length() + 51);
    ((StringBuilder)localObject).append("Selected remote version of ");
    ((StringBuilder)localObject).append(paramString);
    ((StringBuilder)localObject).append(", version >= ");
    ((StringBuilder)localObject).append(paramInt);
    Log.i("DynamiteModule", ((StringBuilder)localObject).toString());
    localObject = get(paramContext);
    if (localObject != null) {
      try
      {
        int i = ((HttpClient)localObject).zzaj();
        if (i >= 2)
        {
          paramContext = ((HttpClient)localObject).execute(ObjectWrapper.wrap(paramContext), paramString, paramInt);
        }
        else
        {
          Log.w("DynamiteModule", "Dynamite loader version < 2, falling back to createModuleContext");
          paramContext = ((HttpClient)localObject).get(ObjectWrapper.wrap(paramContext), paramString, paramInt);
        }
        if (ObjectWrapper.unwrap(paramContext) != null) {
          return new DynamiteModule((Context)ObjectWrapper.unwrap(paramContext));
        }
        throw new LoadingException("Failed to load remote module.", null);
      }
      catch (RemoteException paramContext)
      {
        throw new LoadingException("Failed to load remote module.", paramContext, null);
      }
    }
    throw new LoadingException("Failed to create IDynamiteLoader.", null);
  }
  
  /* Error */
  private static HttpClient get(Context paramContext)
  {
    // Byte code:
    //   0: ldc 111
    //   2: monitorenter
    //   3: getstatic 338	com/google/android/android/dynamite/DynamiteModule:zzie	Lcom/google/android/android/dynamite/HttpClient;
    //   6: ifnull +12 -> 18
    //   9: getstatic 338	com/google/android/android/dynamite/DynamiteModule:zzie	Lcom/google/android/android/dynamite/HttpClient;
    //   12: astore_0
    //   13: ldc 111
    //   15: monitorexit
    //   16: aload_0
    //   17: areturn
    //   18: invokestatic 343	com/google/android/android/common/GoogleApiAvailabilityLight:getInstance	()Lcom/google/android/android/common/GoogleApiAvailabilityLight;
    //   21: aload_0
    //   22: invokevirtual 347	com/google/android/android/common/GoogleApiAvailabilityLight:isGooglePlayServicesAvailable	(Landroid/content/Context;)I
    //   25: ifeq +8 -> 33
    //   28: ldc 111
    //   30: monitorexit
    //   31: aconst_null
    //   32: areturn
    //   33: aload_0
    //   34: ldc -93
    //   36: iconst_3
    //   37: invokevirtual 351	android/content/Context:createPackageContext	(Ljava/lang/String;I)Landroid/content/Context;
    //   40: invokevirtual 121	android/content/Context:getClassLoader	()Ljava/lang/ClassLoader;
    //   43: ldc_w 353
    //   46: invokevirtual 135	java/lang/ClassLoader:loadClass	(Ljava/lang/String;)Ljava/lang/Class;
    //   49: invokevirtual 357	java/lang/Class:newInstance	()Ljava/lang/Object;
    //   52: astore_0
    //   53: aload_0
    //   54: checkcast 359	android/os/IBinder
    //   57: astore_0
    //   58: aload_0
    //   59: ifnonnull +8 -> 67
    //   62: aconst_null
    //   63: astore_0
    //   64: goto +37 -> 101
    //   67: aload_0
    //   68: ldc_w 361
    //   71: invokeinterface 365 2 0
    //   76: astore_1
    //   77: aload_1
    //   78: instanceof 265
    //   81: ifeq +11 -> 92
    //   84: aload_1
    //   85: checkcast 265	com/google/android/android/dynamite/HttpClient
    //   88: astore_0
    //   89: goto +12 -> 101
    //   92: new 367	com/google/android/android/dynamite/HttpClientWrapper
    //   95: dup
    //   96: aload_0
    //   97: invokespecial 370	com/google/android/android/dynamite/HttpClientWrapper:<init>	(Landroid/os/IBinder;)V
    //   100: astore_0
    //   101: aload_0
    //   102: ifnull +57 -> 159
    //   105: aload_0
    //   106: putstatic 338	com/google/android/android/dynamite/DynamiteModule:zzie	Lcom/google/android/android/dynamite/HttpClient;
    //   109: ldc 111
    //   111: monitorexit
    //   112: aload_0
    //   113: areturn
    //   114: astore_0
    //   115: aload_0
    //   116: invokevirtual 228	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   119: invokestatic 194	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   122: astore_0
    //   123: aload_0
    //   124: invokevirtual 200	java/lang/String:length	()I
    //   127: ifeq +14 -> 141
    //   130: ldc_w 372
    //   133: aload_0
    //   134: invokevirtual 234	java/lang/String:concat	(Ljava/lang/String;)Ljava/lang/String;
    //   137: astore_0
    //   138: goto +14 -> 152
    //   141: new 168	java/lang/String
    //   144: dup
    //   145: ldc_w 372
    //   148: invokespecial 237	java/lang/String:<init>	(Ljava/lang/String;)V
    //   151: astore_0
    //   152: ldc -45
    //   154: aload_0
    //   155: invokestatic 310	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   158: pop
    //   159: ldc 111
    //   161: monitorexit
    //   162: aconst_null
    //   163: areturn
    //   164: astore_0
    //   165: ldc 111
    //   167: monitorexit
    //   168: aload_0
    //   169: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	170	0	paramContext	Context
    //   76	9	1	localIInterface	IInterface
    // Exception table:
    //   from	to	target	type
    //   33	53	114	java/lang/Exception
    //   67	77	114	java/lang/Exception
    //   92	101	114	java/lang/Exception
    //   3	16	164	java/lang/Throwable
    //   18	31	164	java/lang/Throwable
    //   33	53	164	java/lang/Throwable
    //   53	58	164	java/lang/Throwable
    //   67	77	164	java/lang/Throwable
    //   77	89	164	java/lang/Throwable
    //   92	101	164	java/lang/Throwable
    //   105	112	164	java/lang/Throwable
    //   115	138	164	java/lang/Throwable
    //   141	152	164	java/lang/Throwable
    //   152	159	164	java/lang/Throwable
    //   159	162	164	java/lang/Throwable
    //   165	168	164	java/lang/Throwable
  }
  
  private static void getInstance(ClassLoader paramClassLoader)
    throws DynamiteModule.LoadingException
  {
    try
    {
      paramClassLoader = paramClassLoader.loadClass("com.google.android.gms.dynamiteloader.DynamiteLoaderV2");
      paramClassLoader = paramClassLoader.getConstructor(new Class[0]);
      paramClassLoader = paramClassLoader.newInstance(new Object[0]);
      paramClassLoader = (IBinder)paramClassLoader;
      if (paramClassLoader == null)
      {
        paramClassLoader = null;
      }
      else
      {
        IInterface localIInterface = paramClassLoader.queryLocalInterface("com.google.android.gms.dynamite.IDynamiteLoaderV2");
        if ((localIInterface instanceof CharArray)) {
          paramClassLoader = (CharArray)localIInterface;
        } else {
          paramClassLoader = new IStatusBarCustomTileHolder.Stub.Proxy(paramClassLoader);
        }
      }
      zzif = paramClassLoader;
      return;
    }
    catch (NoSuchMethodException paramClassLoader) {}catch (InvocationTargetException paramClassLoader) {}catch (InstantiationException paramClassLoader) {}catch (IllegalAccessException paramClassLoader) {}catch (ClassNotFoundException paramClassLoader) {}
    throw new LoadingException("Failed to instantiate dynamite loader", (Throwable)paramClassLoader, null);
  }
  
  public static int getLocalVersion(Context paramContext, String paramString)
  {
    try
    {
      paramContext = paramContext.getApplicationContext().getClassLoader();
      int i = String.valueOf(paramString).length();
      Object localObject = new StringBuilder(i + 61);
      ((StringBuilder)localObject).append("com.google.android.gms.dynamite.descriptors.");
      ((StringBuilder)localObject).append(paramString);
      ((StringBuilder)localObject).append(".ModuleDescriptor");
      localObject = paramContext.loadClass(((StringBuilder)localObject).toString());
      paramContext = ((Class)localObject).getDeclaredField("MODULE_ID");
      localObject = ((Class)localObject).getDeclaredField("MODULE_VERSION");
      boolean bool = paramContext.get(null).equals(paramString);
      if (!bool)
      {
        paramContext = String.valueOf(paramContext.get(null));
        i = String.valueOf(paramContext).length();
        int j = String.valueOf(paramString).length();
        localObject = new StringBuilder(i + 51 + j);
        ((StringBuilder)localObject).append("Module descriptor id '");
        ((StringBuilder)localObject).append(paramContext);
        ((StringBuilder)localObject).append("' didn't match expected id '");
        ((StringBuilder)localObject).append(paramString);
        ((StringBuilder)localObject).append("'");
        Log.e("DynamiteModule", ((StringBuilder)localObject).toString());
        return 0;
      }
      i = ((Field)localObject).getInt(null);
      return i;
    }
    catch (Exception paramContext)
    {
      paramContext = String.valueOf(paramContext.getMessage());
      if (paramContext.length() != 0) {
        paramContext = "Failed to load module descriptor class: ".concat(paramContext);
      } else {
        paramContext = new String("Failed to load module descriptor class: ");
      }
      Log.e("DynamiteModule", paramContext);
      return 0;
      paramContext = new StringBuilder(String.valueOf(paramString).length() + 45);
      paramContext.append("Local module descriptor class for ");
      paramContext.append(paramString);
      paramContext.append(" not found.");
      Log.w("DynamiteModule", paramContext.toString());
      return 0;
    }
    catch (ClassNotFoundException paramContext)
    {
      for (;;) {}
    }
  }
  
  public static int getRemoteVersion(Context paramContext, String paramString)
  {
    return create(paramContext, paramString, false);
  }
  
  public static DynamiteModule load(Context paramContext, VersionPolicy paramVersionPolicy, String paramString)
    throws DynamiteModule.LoadingException
  {
    zza localZza1 = (zza)zzii.get();
    zza localZza2 = new zza(null);
    zzii.set(localZza2);
    try
    {
      DynamiteModule.VersionPolicy.zzb localZzb = paramVersionPolicy.blur(paramContext, paramString, zzij);
      int i = zziq;
      int j = zzir;
      int k = String.valueOf(paramString).length();
      int m = String.valueOf(paramString).length();
      Object localObject = new StringBuilder(k + 68 + m);
      ((StringBuilder)localObject).append("Considering local module ");
      ((StringBuilder)localObject).append(paramString);
      ((StringBuilder)localObject).append(":");
      ((StringBuilder)localObject).append(i);
      ((StringBuilder)localObject).append(" and remote module ");
      ((StringBuilder)localObject).append(paramString);
      ((StringBuilder)localObject).append(":");
      ((StringBuilder)localObject).append(j);
      Log.i("DynamiteModule", ((StringBuilder)localObject).toString());
      i = zzis;
      if (i != 0)
      {
        i = zzis;
        if (i == -1)
        {
          i = zziq;
          if (i == 0) {}
        }
        else
        {
          i = zzis;
          if (i == 1)
          {
            i = zzir;
            if (i == 0) {}
          }
          else
          {
            i = zzis;
            if (i == -1)
            {
              paramContext = loadData(paramContext, paramString);
              if (zzin != null) {
                zzin.close();
              }
              zzii.set(localZza1);
              return paramContext;
            }
            i = zzis;
            if (i == 1)
            {
              i = zzir;
              try
              {
                localObject = execute(paramContext, paramString, i);
                if (zzin != null) {
                  zzin.close();
                }
                zzii.set(localZza1);
                return localObject;
              }
              catch (LoadingException localLoadingException)
              {
                localObject = String.valueOf(localLoadingException.getMessage());
                i = ((String)localObject).length();
                if (i != 0) {
                  localObject = "Failed to load remote module: ".concat((String)localObject);
                } else {
                  localObject = new String("Failed to load remote module: ");
                }
                Log.w("DynamiteModule", (String)localObject);
                i = zziq;
                if (i != 0)
                {
                  i = blurzzbzziq, 0)).zzis;
                  if (i == -1)
                  {
                    paramContext = loadData(paramContext, paramString);
                    if (zzin != null) {
                      zzin.close();
                    }
                    zzii.set(localZza1);
                    return paramContext;
                  }
                }
                throw new LoadingException("Remote load failed. No local fallback found.", localLoadingException, null);
              }
            }
            i = zzis;
            paramContext = new StringBuilder(47);
            paramContext.append("VersionPolicy returned invalid code:");
            paramContext.append(i);
            throw new LoadingException(paramContext.toString(), null);
          }
        }
      }
      i = zziq;
      j = zzir;
      paramContext = new StringBuilder(91);
      paramContext.append("No acceptable module found. Local version is ");
      paramContext.append(i);
      paramContext.append(" and remote version is ");
      paramContext.append(j);
      paramContext.append(".");
      throw new LoadingException(paramContext.toString(), null);
    }
    catch (Throwable paramContext)
    {
      if (zzin != null) {
        zzin.close();
      }
      zzii.set(localZza1);
      throw paramContext;
    }
  }
  
  private static DynamiteModule loadData(Context paramContext, String paramString)
  {
    paramString = String.valueOf(paramString);
    if (paramString.length() != 0) {
      paramString = "Selected local version of ".concat(paramString);
    } else {
      paramString = new String("Selected local version of ");
    }
    Log.i("DynamiteModule", paramString);
    return new DynamiteModule(paramContext.getApplicationContext());
  }
  
  private static int lookup(Context paramContext, String paramString, boolean paramBoolean)
    throws DynamiteModule.LoadingException
  {
    Object localObject1 = null;
    int i;
    try
    {
      Object localObject2 = paramContext.getContentResolver();
      if (paramBoolean) {
        paramContext = "api_force_staging";
      } else {
        paramContext = "api";
      }
      i = paramContext.length();
      int j = String.valueOf(paramString).length();
      Object localObject3 = new StringBuilder(i + 42 + j);
      ((StringBuilder)localObject3).append("content://com.google.android.gms.chimera/");
      ((StringBuilder)localObject3).append(paramContext);
      ((StringBuilder)localObject3).append("/");
      ((StringBuilder)localObject3).append(paramString);
      paramContext = ((ContentResolver)localObject2).query(Uri.parse(((StringBuilder)localObject3).toString()), null, null, null, null);
      paramString = paramContext;
      if (paramContext != null) {}
      try
      {
        paramBoolean = paramContext.moveToFirst();
        if (paramBoolean)
        {
          i = paramContext.getInt(0);
          localObject1 = paramString;
          if (i > 0) {
            try
            {
              zzig = paramContext.getString(2);
              j = paramContext.getColumnIndex("loaderVersion");
              if (j >= 0) {
                zzih = paramContext.getInt(j);
              }
              localObject1 = zzii;
              localObject1 = ((ThreadLocal)localObject1).get();
              localObject2 = (zza)localObject1;
              localObject1 = paramString;
              if (localObject2 != null)
              {
                localObject3 = zzin;
                localObject1 = paramString;
                if (localObject3 == null)
                {
                  zzin = paramContext;
                  localObject1 = null;
                }
              }
            }
            catch (Throwable paramString)
            {
              throw paramString;
            }
          }
          if (localObject1 == null) {
            return i;
          }
          ((Cursor)localObject1).close();
          return i;
        }
        Log.w("DynamiteModule", "Failed to retrieve remote module version.");
        paramString = new LoadingException("Failed to connect to dynamite module ContentResolver.", null);
        throw paramString;
      }
      catch (Throwable paramString)
      {
        break label319;
      }
      catch (Exception paramString) {}
      try
      {
        paramBoolean = paramString instanceof LoadingException;
        if (paramBoolean) {
          throw paramString;
        }
        throw new LoadingException("V2 version check failed", paramString, null);
      }
      catch (Throwable paramString) {}
    }
    catch (Throwable paramString)
    {
      paramContext = (Context)localObject1;
    }
    catch (Exception paramString)
    {
      paramContext = null;
    }
    label319:
    if (paramContext != null) {
      paramContext.close();
    }
    throw paramString;
    return i;
  }
  
  private static DynamiteModule update(Context paramContext, String paramString, int paramInt)
    throws DynamiteModule.LoadingException
  {
    Object localObject = new StringBuilder(String.valueOf(paramString).length() + 51);
    ((StringBuilder)localObject).append("Selected remote version of ");
    ((StringBuilder)localObject).append(paramString);
    ((StringBuilder)localObject).append(", version >= ");
    ((StringBuilder)localObject).append(paramInt);
    Log.i("DynamiteModule", ((StringBuilder)localObject).toString());
    try
    {
      localObject = zzif;
      if (localObject != null)
      {
        zza localZza = (zza)zzii.get();
        if ((localZza != null) && (zzin != null))
        {
          paramContext = get(paramContext.getApplicationContext(), paramString, paramInt, zzin, (CharArray)localObject);
          if (paramContext != null) {
            return new DynamiteModule(paramContext);
          }
          throw new LoadingException("Failed to get module context", null);
        }
        throw new LoadingException("No result cursor", null);
      }
      throw new LoadingException("DynamiteLoaderV2 was not cached.", null);
    }
    catch (Throwable paramContext)
    {
      throw paramContext;
    }
  }
  
  private static Boolean zzai()
  {
    for (;;)
    {
      try
      {
        if (zzih >= 2)
        {
          bool = true;
          return Boolean.valueOf(bool);
        }
      }
      catch (Throwable localThrowable)
      {
        throw localThrowable;
      }
      boolean bool = false;
    }
  }
  
  public final Context getModuleContext()
  {
    return zzim;
  }
  
  public final IBinder instantiate(String paramString)
    throws DynamiteModule.LoadingException
  {
    throw new Runtime("d2j fail translate: java.lang.RuntimeException: fail exe a11 = a10\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.exec(BaseAnalyze.java:92)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.exec(BaseAnalyze.java:1)\n\tat com.googlecode.dex2jar.ir.ts.Cfg.dfs(Cfg.java:255)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.analyze0(BaseAnalyze.java:75)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.analyze(BaseAnalyze.java:69)\n\tat com.googlecode.dex2jar.ir.ts.UnSSATransformer.transform(UnSSATransformer.java:274)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:163)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\nCaused by: java.lang.NullPointerException\n");
  }
  
  public class DynamiteLoaderClassLoader
  {
    public static ClassLoader sClassLoader;
    
    public DynamiteLoaderClassLoader() {}
  }
  
  public class LoadingException
    extends Exception
  {
    private LoadingException()
    {
      super();
    }
    
    private LoadingException(Throwable paramThrowable)
    {
      super(paramThrowable);
    }
  }
  
  public abstract interface VersionPolicy
  {
    public abstract zzb blur(Context paramContext, String paramString, zza paramZza)
      throws DynamiteModule.LoadingException;
    
    public abstract interface zza
    {
      public abstract int copy(Context paramContext, String paramString, boolean paramBoolean)
        throws DynamiteModule.LoadingException;
      
      public abstract int getLocalVersion(Context paramContext, String paramString);
    }
    
    public final class zzb
    {
      public int zziq = 0;
      public int zzir = 0;
      public int zzis = 0;
      
      public zzb() {}
    }
  }
  
  final class zza
  {
    public Cursor zzin;
    
    private zza() {}
  }
  
  final class zzb
    implements DynamiteModule.VersionPolicy.zza
  {
    private final int zzio;
    private final int zzip;
    
    public zzb(int paramInt)
    {
      zzio = this$1;
      zzip = 0;
    }
    
    public final int copy(Context paramContext, String paramString, boolean paramBoolean)
    {
      return 0;
    }
    
    public final int getLocalVersion(Context paramContext, String paramString)
    {
      return zzio;
    }
  }
}
