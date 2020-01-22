package androidx.preference;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.util.AttributeSet;
import android.view.InflateException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

class PreferenceInflater
{
  private static final HashMap<String, Constructor> CONSTRUCTOR_MAP = new HashMap();
  private static final Class<?>[] CONSTRUCTOR_SIGNATURE = { Context.class, AttributeSet.class };
  private static final String EXTRA_TAG_NAME = "extra";
  private static final String INTENT_TAG_NAME = "intent";
  private final Object[] mConstructorArgs = new Object[2];
  private final Context mContext;
  private String[] mDefaultPackages;
  private PreferenceManager mPreferenceManager;
  
  public PreferenceInflater(Context paramContext, PreferenceManager paramPreferenceManager)
  {
    mContext = paramContext;
    init(paramPreferenceManager);
  }
  
  private Preference createItem(String paramString, String[] paramArrayOfString, AttributeSet paramAttributeSet)
    throws ClassNotFoundException, InflateException
  {
    Constructor localConstructor = (Constructor)CONSTRUCTOR_MAP.get(paramString);
    Object localObject1 = localConstructor;
    if (localConstructor == null) {
      localObject1 = mContext;
    }
    try
    {
      ClassLoader localClassLoader = ((Context)localObject1).getClassLoader();
      if ((paramArrayOfString != null) && (paramArrayOfString.length != 0))
      {
        int j = paramArrayOfString.length;
        Object localObject2 = null;
        localConstructor = null;
        int i = 0;
        for (;;)
        {
          localObject1 = localObject2;
          if (i >= j) {
            break;
          }
          localObject1 = paramArrayOfString[i];
          try
          {
            StringBuilder localStringBuilder = new StringBuilder();
            localStringBuilder.append((String)localObject1);
            localStringBuilder.append(paramString);
            localObject1 = Class.forName(localStringBuilder.toString(), false, localClassLoader);
          }
          catch (ClassNotFoundException localClassNotFoundException)
          {
            i += 1;
          }
        }
        paramArrayOfString = (String[])localObject1;
        if (localObject1 == null)
        {
          if (localClassNotFoundException == null)
          {
            paramArrayOfString = new StringBuilder();
            paramArrayOfString.append(paramAttributeSet.getPositionDescription());
            paramArrayOfString.append(": Error inflating class ");
            paramArrayOfString.append(paramString);
            paramArrayOfString = new InflateException(paramArrayOfString.toString());
            throw paramArrayOfString;
          }
          throw localClassNotFoundException;
        }
      }
      else
      {
        paramArrayOfString = Class.forName(paramString, false, localClassLoader);
      }
      localObject1 = CONSTRUCTOR_SIGNATURE;
      paramArrayOfString = paramArrayOfString.getConstructor((Class[])localObject1);
      localObject1 = paramArrayOfString;
      paramArrayOfString.setAccessible(true);
      HashMap localHashMap = CONSTRUCTOR_MAP;
      localHashMap.put(paramString, paramArrayOfString);
      paramArrayOfString = mConstructorArgs;
      paramArrayOfString[1] = paramAttributeSet;
      paramArrayOfString = ((Constructor)localObject1).newInstance(paramArrayOfString);
      return (Preference)paramArrayOfString;
    }
    catch (Exception paramArrayOfString)
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append(paramAttributeSet.getPositionDescription());
      ((StringBuilder)localObject1).append(": Error inflating class ");
      ((StringBuilder)localObject1).append(paramString);
      paramString = new InflateException(((StringBuilder)localObject1).toString());
      paramString.initCause(paramArrayOfString);
      throw paramString;
    }
    catch (ClassNotFoundException paramString)
    {
      throw paramString;
    }
  }
  
  private Preference createItemFromTag(String paramString, AttributeSet paramAttributeSet)
  {
    try
    {
      int i = paramString.indexOf('.');
      if (-1 == i)
      {
        localPreference = onCreateItem(paramString, paramAttributeSet);
        return localPreference;
      }
      Preference localPreference = createItem(paramString, null, paramAttributeSet);
      return localPreference;
    }
    catch (Exception localException)
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append(paramAttributeSet.getPositionDescription());
      localStringBuilder.append(": Error inflating class ");
      localStringBuilder.append(paramString);
      paramString = new InflateException(localStringBuilder.toString());
      paramString.initCause(localException);
      throw paramString;
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(paramAttributeSet.getPositionDescription());
      localStringBuilder.append(": Error inflating class (not found)");
      localStringBuilder.append(paramString);
      paramString = new InflateException(localStringBuilder.toString());
      paramString.initCause(localClassNotFoundException);
      throw paramString;
    }
    catch (InflateException paramString)
    {
      throw paramString;
    }
  }
  
  private void init(PreferenceManager paramPreferenceManager)
  {
    mPreferenceManager = paramPreferenceManager;
    paramPreferenceManager = new StringBuilder();
    paramPreferenceManager.append(Preference.class.getPackage().getName());
    paramPreferenceManager.append(".");
    paramPreferenceManager = paramPreferenceManager.toString();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(SwitchPreference.class.getPackage().getName());
    localStringBuilder.append(".");
    setDefaultPackages(new String[] { paramPreferenceManager, localStringBuilder.toString() });
  }
  
  private PreferenceGroup onMergeRoots(PreferenceGroup paramPreferenceGroup1, PreferenceGroup paramPreferenceGroup2)
  {
    if (paramPreferenceGroup1 == null)
    {
      paramPreferenceGroup2.onAttachedToHierarchy(mPreferenceManager);
      return paramPreferenceGroup2;
    }
    return paramPreferenceGroup1;
  }
  
  private void rInflate(XmlPullParser paramXmlPullParser, Preference paramPreference, AttributeSet paramAttributeSet)
    throws XmlPullParserException, IOException
  {
    int i = paramXmlPullParser.getDepth();
    for (;;)
    {
      int j = paramXmlPullParser.next();
      if (((j == 3) && (paramXmlPullParser.getDepth() <= i)) || (j == 1)) {
        break;
      }
      if (j == 2)
      {
        Object localObject = paramXmlPullParser.getName();
        if ("intent".equals(localObject))
        {
          try
          {
            localObject = Intent.parseIntent(getContext().getResources(), paramXmlPullParser, paramAttributeSet);
            paramPreference.setIntent((Intent)localObject);
          }
          catch (IOException paramXmlPullParser)
          {
            paramPreference = new XmlPullParserException("Error parsing preference");
            paramPreference.initCause(paramXmlPullParser);
            throw paramPreference;
          }
        }
        else if ("extra".equals(localObject))
        {
          getContext().getResources().parseBundleExtra("extra", paramAttributeSet, paramPreference.getExtras());
          try
          {
            skipCurrentTag(paramXmlPullParser);
          }
          catch (IOException paramXmlPullParser)
          {
            paramPreference = new XmlPullParserException("Error parsing preference");
            paramPreference.initCause(paramXmlPullParser);
            throw paramPreference;
          }
        }
        else
        {
          localObject = createItemFromTag((String)localObject, paramAttributeSet);
          ((PreferenceGroup)paramPreference).addItemFromInflater((Preference)localObject);
          rInflate(paramXmlPullParser, (Preference)localObject, paramAttributeSet);
        }
      }
    }
  }
  
  private static void skipCurrentTag(XmlPullParser paramXmlPullParser)
    throws XmlPullParserException, IOException
  {
    int i = paramXmlPullParser.getDepth();
    int j;
    do
    {
      j = paramXmlPullParser.next();
    } while ((j != 1) && ((j != 3) || (paramXmlPullParser.getDepth() > i)));
  }
  
  public Context getContext()
  {
    return mContext;
  }
  
  public String[] getDefaultPackages()
  {
    return mDefaultPackages;
  }
  
  public Preference inflate(int paramInt, PreferenceGroup paramPreferenceGroup)
  {
    XmlResourceParser localXmlResourceParser = getContext().getResources().getXml(paramInt);
    try
    {
      paramPreferenceGroup = inflate(localXmlResourceParser, paramPreferenceGroup);
      localXmlResourceParser.close();
      return paramPreferenceGroup;
    }
    catch (Throwable paramPreferenceGroup)
    {
      localXmlResourceParser.close();
      throw paramPreferenceGroup;
    }
  }
  
  /* Error */
  public Preference inflate(XmlPullParser paramXmlPullParser, PreferenceGroup paramPreferenceGroup)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 47	androidx/preference/PreferenceInflater:mConstructorArgs	[Ljava/lang/Object;
    //   4: astore 4
    //   6: aload 4
    //   8: monitorenter
    //   9: aload_1
    //   10: invokestatic 247	android/util/Xml:asAttributeSet	(Lorg/xmlpull/v1/XmlPullParser;)Landroid/util/AttributeSet;
    //   13: astore 5
    //   15: aload_0
    //   16: getfield 47	androidx/preference/PreferenceInflater:mConstructorArgs	[Ljava/lang/Object;
    //   19: iconst_0
    //   20: aload_0
    //   21: getfield 49	androidx/preference/PreferenceInflater:mContext	Landroid/content/Context;
    //   24: aastore
    //   25: aload_1
    //   26: invokeinterface 171 1 0
    //   31: istore_3
    //   32: iload_3
    //   33: iconst_2
    //   34: if_icmpeq +8 -> 42
    //   37: iload_3
    //   38: iconst_1
    //   39: if_icmpne -14 -> 25
    //   42: iload_3
    //   43: iconst_2
    //   44: if_icmpne +45 -> 89
    //   47: aload_0
    //   48: aload_1
    //   49: invokeinterface 172 1 0
    //   54: aload 5
    //   56: invokespecial 213	androidx/preference/PreferenceInflater:createItemFromTag	(Ljava/lang/String;Landroid/util/AttributeSet;)Landroidx/preference/Preference;
    //   59: astore 6
    //   61: aload 6
    //   63: checkcast 215	androidx/preference/PreferenceGroup
    //   66: astore 6
    //   68: aload_0
    //   69: aload_2
    //   70: aload 6
    //   72: invokespecial 249	androidx/preference/PreferenceInflater:onMergeRoots	(Landroidx/preference/PreferenceGroup;Landroidx/preference/PreferenceGroup;)Landroidx/preference/PreferenceGroup;
    //   75: astore_2
    //   76: aload_0
    //   77: aload_1
    //   78: aload_2
    //   79: aload 5
    //   81: invokespecial 221	androidx/preference/PreferenceInflater:rInflate	(Lorg/xmlpull/v1/XmlPullParser;Landroidx/preference/Preference;Landroid/util/AttributeSet;)V
    //   84: aload 4
    //   86: monitorexit
    //   87: aload_2
    //   88: areturn
    //   89: new 73	java/lang/StringBuilder
    //   92: dup
    //   93: invokespecial 74	java/lang/StringBuilder:<init>	()V
    //   96: astore_2
    //   97: aload_2
    //   98: aload_1
    //   99: invokeinterface 250 1 0
    //   104: invokevirtual 78	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   107: pop
    //   108: aload_2
    //   109: ldc -4
    //   111: invokevirtual 78	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   114: pop
    //   115: new 59	android/view/InflateException
    //   118: dup
    //   119: aload_2
    //   120: invokevirtual 82	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   123: invokespecial 94	android/view/InflateException:<init>	(Ljava/lang/String;)V
    //   126: astore_2
    //   127: aload_2
    //   128: athrow
    //   129: astore_2
    //   130: new 73	java/lang/StringBuilder
    //   133: dup
    //   134: invokespecial 74	java/lang/StringBuilder:<init>	()V
    //   137: astore 5
    //   139: aload 5
    //   141: aload_1
    //   142: invokeinterface 250 1 0
    //   147: invokevirtual 78	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   150: pop
    //   151: aload 5
    //   153: ldc -2
    //   155: invokevirtual 78	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   158: pop
    //   159: aload 5
    //   161: aload_2
    //   162: checkcast 162	java/io/IOException
    //   165: invokevirtual 257	java/io/IOException:getMessage	()Ljava/lang/String;
    //   168: invokevirtual 78	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   171: pop
    //   172: new 59	android/view/InflateException
    //   175: dup
    //   176: aload 5
    //   178: invokevirtual 82	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   181: invokespecial 94	android/view/InflateException:<init>	(Ljava/lang/String;)V
    //   184: astore_1
    //   185: aload_1
    //   186: aload_2
    //   187: invokevirtual 116	java/lang/Exception:initCause	(Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   190: pop
    //   191: aload_1
    //   192: athrow
    //   193: astore_1
    //   194: new 59	android/view/InflateException
    //   197: dup
    //   198: aload_1
    //   199: invokevirtual 258	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   202: invokespecial 94	android/view/InflateException:<init>	(Ljava/lang/String;)V
    //   205: astore_2
    //   206: aload_2
    //   207: aload_1
    //   208: invokevirtual 116	java/lang/Exception:initCause	(Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   211: pop
    //   212: aload_2
    //   213: athrow
    //   214: astore_1
    //   215: aload_1
    //   216: athrow
    //   217: astore_1
    //   218: aload 4
    //   220: monitorexit
    //   221: goto +3 -> 224
    //   224: aload_1
    //   225: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	226	0	this	PreferenceInflater
    //   0	226	1	paramXmlPullParser	XmlPullParser
    //   0	226	2	paramPreferenceGroup	PreferenceGroup
    //   31	14	3	i	int
    //   4	215	4	arrayOfObject	Object[]
    //   13	164	5	localObject1	Object
    //   59	12	6	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   25	32	129	java/io/IOException
    //   47	61	129	java/io/IOException
    //   68	84	129	java/io/IOException
    //   89	127	129	java/io/IOException
    //   25	32	193	org/xmlpull/v1/XmlPullParserException
    //   47	61	193	org/xmlpull/v1/XmlPullParserException
    //   68	84	193	org/xmlpull/v1/XmlPullParserException
    //   89	127	193	org/xmlpull/v1/XmlPullParserException
    //   25	32	214	android/view/InflateException
    //   47	61	214	android/view/InflateException
    //   68	84	214	android/view/InflateException
    //   89	127	214	android/view/InflateException
    //   127	129	214	android/view/InflateException
    //   9	25	217	java/lang/Throwable
    //   25	32	217	java/lang/Throwable
    //   47	61	217	java/lang/Throwable
    //   68	84	217	java/lang/Throwable
    //   84	87	217	java/lang/Throwable
    //   89	127	217	java/lang/Throwable
    //   127	129	217	java/lang/Throwable
    //   130	193	217	java/lang/Throwable
    //   194	214	217	java/lang/Throwable
    //   215	217	217	java/lang/Throwable
    //   218	221	217	java/lang/Throwable
  }
  
  protected Preference onCreateItem(String paramString, AttributeSet paramAttributeSet)
    throws ClassNotFoundException
  {
    return createItem(paramString, mDefaultPackages, paramAttributeSet);
  }
  
  public void setDefaultPackages(String[] paramArrayOfString)
  {
    mDefaultPackages = paramArrayOfString;
  }
}
