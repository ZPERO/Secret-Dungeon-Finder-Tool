package androidx.core.graphics;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.ParcelFileDescriptor;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.system.StructStat;
import android.util.Log;
import androidx.core.content.delay.FontResourcesParserCompat.FontFamilyFilesResourceEntry;
import androidx.core.content.delay.FontResourcesParserCompat.FontFileResourceEntry;
import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class TypefaceCompatApi21Impl
  extends TypefaceCompatBaseImpl
{
  private static final String ADD_FONT_WEIGHT_STYLE_METHOD = "addFontWeightStyle";
  private static final String CREATE_FROM_FAMILIES_WITH_DEFAULT_METHOD = "createFromFamiliesWithDefault";
  private static final String FONT_FAMILY_CLASS = "android.graphics.FontFamily";
  private static final String PAGE_KEY = "TypefaceCompatApi21Impl";
  private static Method sAddFontWeightStyle;
  private static Method sCreateFromFamiliesWithDefault;
  private static Class sFontFamily;
  private static Constructor sFontFamilyCtor;
  private static boolean sHasInitBeenCalled;
  
  TypefaceCompatApi21Impl() {}
  
  private static boolean addFontWeightStyle(Object paramObject, String paramString, int paramInt, boolean paramBoolean)
  {
    init();
    Method localMethod = sAddFontWeightStyle;
    try
    {
      paramObject = localMethod.invoke(paramObject, new Object[] { paramString, Integer.valueOf(paramInt), Boolean.valueOf(paramBoolean) });
      paramObject = (Boolean)paramObject;
      paramBoolean = paramObject.booleanValue();
      return paramBoolean;
    }
    catch (InvocationTargetException paramObject) {}catch (IllegalAccessException paramObject) {}
    throw new RuntimeException(paramObject);
  }
  
  private static Typeface createFromFamiliesWithDefault(Object paramObject)
  {
    init();
    Object localObject = sFontFamily;
    try
    {
      localObject = Array.newInstance((Class)localObject, 1);
      Array.set(localObject, 0, paramObject);
      paramObject = sCreateFromFamiliesWithDefault;
      paramObject = paramObject.invoke(null, new Object[] { localObject });
      return (Typeface)paramObject;
    }
    catch (InvocationTargetException paramObject) {}catch (IllegalAccessException paramObject) {}
    throw new RuntimeException(paramObject);
  }
  
  private File getFile(ParcelFileDescriptor paramParcelFileDescriptor)
  {
    try
    {
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("/proc/self/fd/");
      ((StringBuilder)localObject).append(paramParcelFileDescriptor.getFd());
      paramParcelFileDescriptor = Os.readlink(((StringBuilder)localObject).toString());
      localObject = Os.stat(paramParcelFileDescriptor);
      int i = st_mode;
      boolean bool = OsConstants.S_ISREG(i);
      if (bool)
      {
        paramParcelFileDescriptor = new File(paramParcelFileDescriptor);
        return paramParcelFileDescriptor;
      }
      return null;
    }
    catch (ErrnoException paramParcelFileDescriptor) {}
    return null;
  }
  
  private static void init()
  {
    if (sHasInitBeenCalled) {
      return;
    }
    sHasInitBeenCalled = true;
    Object localObject3 = null;
    try
    {
      Object localObject6 = Class.forName("android.graphics.FontFamily");
      Object localObject1 = localObject6;
      localObject5 = ((Class)localObject6).getConstructor(new Class[0]);
      localObject4 = Integer.TYPE;
      Class localClass = Boolean.TYPE;
      localObject4 = ((Class)localObject6).getMethod("addFontWeightStyle", new Class[] { String.class, localObject4, localClass });
      localObject6 = Array.newInstance((Class)localObject6, 1);
      localObject6 = localObject6.getClass();
      localObject6 = Typeface.class.getMethod("createFromFamiliesWithDefault", new Class[] { localObject6 });
      localObject3 = localObject5;
      localObject5 = localObject6;
    }
    catch (NoSuchMethodException localNoSuchMethodException) {}catch (ClassNotFoundException localClassNotFoundException) {}
    Log.e("TypefaceCompatApi21Impl", localClassNotFoundException.getClass().getName(), localClassNotFoundException);
    Object localObject5 = null;
    Object localObject2 = null;
    Object localObject4 = null;
    sFontFamilyCtor = localObject3;
    sFontFamily = localObject2;
    sAddFontWeightStyle = (Method)localObject4;
    sCreateFromFamiliesWithDefault = (Method)localObject5;
  }
  
  private static Object newFamily()
  {
    init();
    Object localObject = sFontFamilyCtor;
    try
    {
      localObject = ((Constructor)localObject).newInstance(new Object[0]);
      return localObject;
    }
    catch (InvocationTargetException localInvocationTargetException) {}catch (InstantiationException localInstantiationException) {}catch (IllegalAccessException localIllegalAccessException) {}
    throw new RuntimeException(localIllegalAccessException);
  }
  
  public Typeface createFromFontFamilyFilesResourceEntry(Context paramContext, FontResourcesParserCompat.FontFamilyFilesResourceEntry paramFontFamilyFilesResourceEntry, Resources paramResources, int paramInt)
  {
    Object localObject = newFamily();
    FontResourcesParserCompat.FontFileResourceEntry[] arrayOfFontFileResourceEntry = paramFontFamilyFilesResourceEntry.getEntries();
    int i = arrayOfFontFileResourceEntry.length;
    paramInt = 0;
    for (;;)
    {
      FontResourcesParserCompat.FontFileResourceEntry localFontFileResourceEntry;
      if (paramInt < i)
      {
        localFontFileResourceEntry = arrayOfFontFileResourceEntry[paramInt];
        paramFontFamilyFilesResourceEntry = TypefaceCompatUtil.getTempFile(paramContext);
        if (paramFontFamilyFilesResourceEntry == null) {
          return null;
        }
      }
      try
      {
        bool = TypefaceCompatUtil.copyToFile(paramFontFamilyFilesResourceEntry, paramResources, localFontFileResourceEntry.getResourceId());
        if (!bool)
        {
          paramFontFamilyFilesResourceEntry.delete();
          return null;
        }
      }
      catch (Throwable paramContext)
      {
        try
        {
          boolean bool = addFontWeightStyle(localObject, paramFontFamilyFilesResourceEntry.getPath(), localFontFileResourceEntry.getWeight(), localFontFileResourceEntry.isItalic());
          if (!bool)
          {
            paramFontFamilyFilesResourceEntry.delete();
            return null;
          }
          paramFontFamilyFilesResourceEntry.delete();
          paramInt += 1;
        }
        catch (RuntimeException paramContext)
        {
          for (;;) {}
        }
        paramContext = paramContext;
        paramFontFamilyFilesResourceEntry.delete();
        throw paramContext;
        paramFontFamilyFilesResourceEntry.delete();
        return null;
        return createFromFamiliesWithDefault(localObject);
      }
      catch (RuntimeException paramContext)
      {
        for (;;) {}
      }
    }
  }
  
  /* Error */
  public Typeface createFromFontInfo(Context paramContext, android.os.CancellationSignal paramCancellationSignal, androidx.core.provider.FontsContractCompat.FontInfo[] paramArrayOfFontInfo, int paramInt)
  {
    // Byte code:
    //   0: aload_3
    //   1: arraylength
    //   2: iconst_1
    //   3: if_icmpge +5 -> 8
    //   6: aconst_null
    //   7: areturn
    //   8: aload_0
    //   9: aload_3
    //   10: iload 4
    //   12: invokevirtual 241	androidx/core/graphics/TypefaceCompatBaseImpl:findBestInfo	([Landroidx/core/provider/FontsContractCompat$FontInfo;I)Landroidx/core/provider/FontsContractCompat$FontInfo;
    //   15: astore_3
    //   16: aload_1
    //   17: invokevirtual 247	android/content/Context:getContentResolver	()Landroid/content/ContentResolver;
    //   20: astore 6
    //   22: aload 6
    //   24: aload_3
    //   25: invokevirtual 253	androidx/core/provider/FontsContractCompat$FontInfo:getUri	()Landroid/net/Uri;
    //   28: ldc -1
    //   30: aload_2
    //   31: invokevirtual 261	android/content/ContentResolver:openFileDescriptor	(Landroid/net/Uri;Ljava/lang/String;Landroid/os/CancellationSignal;)Landroid/os/ParcelFileDescriptor;
    //   34: astore_3
    //   35: aload_3
    //   36: ifnonnull +13 -> 49
    //   39: aload_3
    //   40: ifnull +143 -> 183
    //   43: aload_3
    //   44: invokevirtual 264	android/os/ParcelFileDescriptor:close	()V
    //   47: aconst_null
    //   48: areturn
    //   49: aload_0
    //   50: aload_3
    //   51: invokespecial 266	androidx/core/graphics/TypefaceCompatApi21Impl:getFile	(Landroid/os/ParcelFileDescriptor;)Ljava/io/File;
    //   54: astore_2
    //   55: aload_2
    //   56: ifnull +34 -> 90
    //   59: aload_2
    //   60: invokevirtual 269	java/io/File:canRead	()Z
    //   63: istore 5
    //   65: iload 5
    //   67: ifne +6 -> 73
    //   70: goto +20 -> 90
    //   73: aload_2
    //   74: invokestatic 273	android/graphics/Typeface:createFromFile	(Ljava/io/File;)Landroid/graphics/Typeface;
    //   77: astore_2
    //   78: aload_2
    //   79: astore_1
    //   80: aload_3
    //   81: ifnull +104 -> 185
    //   84: aload_3
    //   85: invokevirtual 264	android/os/ParcelFileDescriptor:close	()V
    //   88: aload_2
    //   89: areturn
    //   90: new 275	java/io/FileInputStream
    //   93: dup
    //   94: aload_3
    //   95: invokevirtual 279	android/os/ParcelFileDescriptor:getFileDescriptor	()Ljava/io/FileDescriptor;
    //   98: invokespecial 282	java/io/FileInputStream:<init>	(Ljava/io/FileDescriptor;)V
    //   101: astore 6
    //   103: aload_0
    //   104: aload_1
    //   105: aload 6
    //   107: invokespecial 286	androidx/core/graphics/TypefaceCompatBaseImpl:createFromInputStream	(Landroid/content/Context;Ljava/io/InputStream;)Landroid/graphics/Typeface;
    //   110: astore_2
    //   111: aload 6
    //   113: invokevirtual 287	java/io/FileInputStream:close	()V
    //   116: aload_2
    //   117: astore_1
    //   118: aload_3
    //   119: ifnull +66 -> 185
    //   122: aload_3
    //   123: invokevirtual 264	android/os/ParcelFileDescriptor:close	()V
    //   126: aload_2
    //   127: areturn
    //   128: astore_1
    //   129: aload_1
    //   130: athrow
    //   131: astore_2
    //   132: aload 6
    //   134: invokevirtual 287	java/io/FileInputStream:close	()V
    //   137: goto +11 -> 148
    //   140: astore 6
    //   142: aload_1
    //   143: aload 6
    //   145: invokevirtual 290	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
    //   148: aload_2
    //   149: athrow
    //   150: astore_1
    //   151: aload_1
    //   152: athrow
    //   153: astore_2
    //   154: aload_3
    //   155: ifnull +16 -> 171
    //   158: aload_3
    //   159: invokevirtual 264	android/os/ParcelFileDescriptor:close	()V
    //   162: goto +9 -> 171
    //   165: astore_3
    //   166: aload_1
    //   167: aload_3
    //   168: invokevirtual 290	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
    //   171: aload_2
    //   172: athrow
    //   173: astore_1
    //   174: aconst_null
    //   175: areturn
    //   176: astore_1
    //   177: aconst_null
    //   178: areturn
    //   179: astore_1
    //   180: aconst_null
    //   181: areturn
    //   182: astore_1
    //   183: aconst_null
    //   184: areturn
    //   185: aload_1
    //   186: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	187	0	this	TypefaceCompatApi21Impl
    //   0	187	1	paramContext	Context
    //   0	187	2	paramCancellationSignal	android.os.CancellationSignal
    //   0	187	3	paramArrayOfFontInfo	androidx.core.provider.FontsContractCompat.FontInfo[]
    //   0	187	4	paramInt	int
    //   63	3	5	bool	boolean
    //   20	113	6	localObject	Object
    //   140	4	6	localThrowable	Throwable
    // Exception table:
    //   from	to	target	type
    //   103	111	128	java/lang/Throwable
    //   129	131	131	java/lang/Throwable
    //   132	137	140	java/lang/Throwable
    //   49	55	150	java/lang/Throwable
    //   59	65	150	java/lang/Throwable
    //   73	78	150	java/lang/Throwable
    //   90	103	150	java/lang/Throwable
    //   111	116	150	java/lang/Throwable
    //   142	148	150	java/lang/Throwable
    //   148	150	150	java/lang/Throwable
    //   151	153	153	java/lang/Throwable
    //   158	162	165	java/lang/Throwable
    //   22	35	173	java/io/IOException
    //   43	47	173	java/io/IOException
    //   84	88	176	java/io/IOException
    //   122	126	179	java/io/IOException
    //   166	171	182	java/io/IOException
    //   171	173	182	java/io/IOException
  }
}
