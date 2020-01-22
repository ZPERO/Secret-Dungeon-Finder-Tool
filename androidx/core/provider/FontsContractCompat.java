package androidx.core.provider;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ProviderInfo;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Build.VERSION;
import android.os.CancellationSignal;
import android.os.Handler;
import android.provider.BaseColumns;
import androidx.collection.LruCache;
import androidx.collection.SimpleArrayMap;
import androidx.core.content.delay.FontResourcesParserCompat;
import androidx.core.content.delay.ResourcesCompat.FontCallback;
import androidx.core.graphics.TypefaceCompat;
import androidx.core.graphics.TypefaceCompatUtil;
import androidx.core.util.Preconditions;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class FontsContractCompat
{
  private static final int BACKGROUND_THREAD_KEEP_ALIVE_DURATION_MS = 10000;
  public static final String PARCEL_FONT_RESULTS = "font_results";
  static final int RESULT_CODE_PROVIDER_NOT_FOUND = -1;
  static final int RESULT_CODE_WRONG_CERTIFICATES = -2;
  private static final SelfDestructiveThread sBackgroundThread;
  private static final Comparator<byte[]> sByteArrayComparator = new Comparator()
  {
    public int compare(byte[] paramAnonymousArrayOfByte1, byte[] paramAnonymousArrayOfByte2)
    {
      int j;
      if (paramAnonymousArrayOfByte1.length != paramAnonymousArrayOfByte2.length)
      {
        i = paramAnonymousArrayOfByte1.length;
        j = paramAnonymousArrayOfByte2.length;
        return i - j;
      }
      int i = 0;
      for (;;)
      {
        if (i >= paramAnonymousArrayOfByte1.length) {
          break label63;
        }
        if (paramAnonymousArrayOfByte1[i] != paramAnonymousArrayOfByte2[i])
        {
          j = paramAnonymousArrayOfByte1[i];
          int k = paramAnonymousArrayOfByte2[i];
          i = j;
          j = k;
          break;
        }
        i += 1;
      }
      label63:
      return 0;
    }
  };
  static final Object sLock;
  static final SimpleArrayMap<String, ArrayList<SelfDestructiveThread.ReplyCallback<TypefaceResult>>> sPendingReplies;
  static final LruCache<String, Typeface> sTypefaceCache = new LruCache(16);
  
  static
  {
    sBackgroundThread = new SelfDestructiveThread("fonts", 10, 10000);
    sLock = new Object();
    sPendingReplies = new SimpleArrayMap();
  }
  
  private FontsContractCompat() {}
  
  public static Typeface buildTypeface(Context paramContext, CancellationSignal paramCancellationSignal, FontInfo[] paramArrayOfFontInfo)
  {
    return TypefaceCompat.createFromFontInfo(paramContext, paramCancellationSignal, paramArrayOfFontInfo, 0);
  }
  
  private static List convertToByteArrayList(Signature[] paramArrayOfSignature)
  {
    ArrayList localArrayList = new ArrayList();
    int i = 0;
    while (i < paramArrayOfSignature.length)
    {
      localArrayList.add(paramArrayOfSignature[i].toByteArray());
      i += 1;
    }
    return localArrayList;
  }
  
  private static boolean equalsByteArrayList(List paramList1, List paramList2)
  {
    if (paramList1.size() != paramList2.size()) {
      return false;
    }
    int i = 0;
    while (i < paramList1.size())
    {
      if (!Arrays.equals((byte[])paramList1.get(i), (byte[])paramList2.get(i))) {
        return false;
      }
      i += 1;
    }
    return true;
  }
  
  public static FontFamilyResult fetchFonts(Context paramContext, CancellationSignal paramCancellationSignal, FontRequest paramFontRequest)
    throws PackageManager.NameNotFoundException
  {
    ProviderInfo localProviderInfo = getProvider(paramContext.getPackageManager(), paramFontRequest, paramContext.getResources());
    if (localProviderInfo == null) {
      return new FontFamilyResult(1, null);
    }
    return new FontFamilyResult(0, getFontFromProvider(paramContext, paramFontRequest, authority, paramCancellationSignal));
  }
  
  private static List getCertificates(FontRequest paramFontRequest, Resources paramResources)
  {
    if (paramFontRequest.getCertificates() != null) {
      return paramFontRequest.getCertificates();
    }
    return FontResourcesParserCompat.readCerts(paramResources, paramFontRequest.getCertificatesArrayResId());
  }
  
  static FontInfo[] getFontFromProvider(Context paramContext, FontRequest paramFontRequest, String paramString, CancellationSignal paramCancellationSignal)
  {
    ArrayList localArrayList = new ArrayList();
    Uri localUri1 = new Uri.Builder().scheme("content").authority(paramString).build();
    Uri localUri2 = new Uri.Builder().scheme("content").authority(paramString).appendPath("file").build();
    try
    {
      int i = Build.VERSION.SDK_INT;
      if (i > 16)
      {
        paramContext = paramContext.getContentResolver();
        paramFontRequest = paramFontRequest.getQuery();
        paramContext = paramContext.query(localUri1, new String[] { "_id", "file_id", "font_ttc_index", "font_variation_settings", "font_weight", "font_italic", "result_code" }, "query = ?", new String[] { paramFontRequest }, null, paramCancellationSignal);
      }
      else
      {
        paramContext = paramContext.getContentResolver();
        paramFontRequest = paramFontRequest.getQuery();
        paramContext = paramContext.query(localUri1, new String[] { "_id", "file_id", "font_ttc_index", "font_variation_settings", "font_weight", "font_italic", "result_code" }, "query = ?", new String[] { paramFontRequest }, null);
      }
      paramFontRequest = localArrayList;
      if (paramContext != null) {
        try
        {
          i = paramContext.getCount();
          paramFontRequest = localArrayList;
          if (i > 0)
          {
            int m = paramContext.getColumnIndex("result_code");
            paramString = new ArrayList();
            int n = paramContext.getColumnIndex("_id");
            int i1 = paramContext.getColumnIndex("file_id");
            int i2 = paramContext.getColumnIndex("font_ttc_index");
            int i3 = paramContext.getColumnIndex("font_weight");
            int i4 = paramContext.getColumnIndex("font_italic");
            for (;;)
            {
              boolean bool = paramContext.moveToNext();
              paramFontRequest = paramString;
              if (!bool) {
                break;
              }
              if (m != -1) {
                i = paramContext.getInt(m);
              } else {
                i = 0;
              }
              int j;
              if (i2 != -1) {
                j = paramContext.getInt(i2);
              } else {
                j = 0;
              }
              if (i1 == -1) {
                paramFontRequest = ContentUris.withAppendedId(localUri1, paramContext.getLong(n));
              } else {
                paramFontRequest = ContentUris.withAppendedId(localUri2, paramContext.getLong(i1));
              }
              int k;
              if (i3 != -1) {
                k = paramContext.getInt(i3);
              } else {
                k = 400;
              }
              if (i4 != -1)
              {
                int i5 = paramContext.getInt(i4);
                if (i5 == 1)
                {
                  bool = true;
                  break label452;
                }
              }
              bool = false;
              label452:
              paramString.add(new FontInfo(paramFontRequest, j, k, bool, i));
            }
          }
          if (paramContext == null) {
            break label490;
          }
        }
        catch (Throwable paramFontRequest) {}
      }
      paramContext.close();
      label490:
      return (FontInfo[])paramFontRequest.toArray(new FontInfo[0]);
    }
    catch (Throwable paramFontRequest)
    {
      paramContext = null;
      if (paramContext != null) {
        paramContext.close();
      }
      throw paramFontRequest;
    }
  }
  
  static TypefaceResult getFontInternal(Context paramContext, FontRequest paramFontRequest, int paramInt)
  {
    try
    {
      paramFontRequest = fetchFonts(paramContext, null, paramFontRequest);
      int j = paramFontRequest.getStatusCode();
      int i = -3;
      if (j == 0)
      {
        paramContext = TypefaceCompat.createFromFontInfo(paramContext, null, paramFontRequest.getFonts(), paramInt);
        if (paramContext != null) {
          i = 0;
        }
        return new TypefaceResult(paramContext, i);
      }
      if (paramFontRequest.getStatusCode() == 1) {
        i = -2;
      }
      return new TypefaceResult(null, i);
    }
    catch (PackageManager.NameNotFoundException paramContext)
    {
      for (;;) {}
    }
    return new TypefaceResult(null, -1);
  }
  
  public static Typeface getFontSync(Context paramContext, final FontRequest paramFontRequest, ResourcesCompat.FontCallback paramFontCallback, final Handler paramHandler, boolean paramBoolean, int paramInt1, final int paramInt2)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append(paramFontRequest.getIdentifier());
    ((StringBuilder)localObject).append("-");
    ((StringBuilder)localObject).append(paramInt2);
    localObject = ((StringBuilder)localObject).toString();
    Typeface localTypeface = (Typeface)sTypefaceCache.get(localObject);
    if (localTypeface != null)
    {
      if (paramFontCallback != null)
      {
        paramFontCallback.onFontRetrieved(localTypeface);
        return localTypeface;
      }
    }
    else
    {
      if ((paramBoolean) && (paramInt1 == -1))
      {
        paramContext = getFontInternal(paramContext, paramFontRequest, paramInt2);
        if (paramFontCallback != null) {
          if (mResult == 0) {
            paramFontCallback.callbackSuccessAsync(mTypeface, paramHandler);
          } else {
            paramFontCallback.callbackFailAsync(mResult, paramHandler);
          }
        }
        return mTypeface;
      }
      paramFontRequest = new Callable()
      {
        public FontsContractCompat.TypefaceResult call()
          throws Exception
        {
          FontsContractCompat.TypefaceResult localTypefaceResult = FontsContractCompat.getFontInternal(val$context, paramFontRequest, paramInt2);
          if (mTypeface != null) {
            FontsContractCompat.sTypefaceCache.put(val$id, mTypeface);
          }
          return localTypefaceResult;
        }
      };
      if (paramBoolean) {
        paramContext = sBackgroundThread;
      }
      try
      {
        paramContext = paramContext.postAndWait(paramFontRequest, paramInt1);
        return mTypeface;
      }
      catch (InterruptedException paramContext)
      {
        return null;
      }
      if (paramFontCallback == null) {
        paramContext = null;
      } else {
        paramContext = new SelfDestructiveThread.ReplyCallback()
        {
          public void onReply(FontsContractCompat.TypefaceResult paramAnonymousTypefaceResult)
          {
            if (paramAnonymousTypefaceResult == null)
            {
              val$fontCallback.callbackFailAsync(1, paramHandler);
              return;
            }
            if (mResult == 0)
            {
              val$fontCallback.callbackSuccessAsync(mTypeface, paramHandler);
              return;
            }
            val$fontCallback.callbackFailAsync(mResult, paramHandler);
          }
        };
      }
      paramFontCallback = sLock;
      try
      {
        paramHandler = (ArrayList)sPendingReplies.get(localObject);
        if (paramHandler != null)
        {
          if (paramContext != null) {
            paramHandler.add(paramContext);
          }
          return null;
        }
        if (paramContext != null)
        {
          paramHandler = new ArrayList();
          paramHandler.add(paramContext);
          sPendingReplies.put(localObject, paramHandler);
        }
        sBackgroundThread.postAndReply(paramFontRequest, new SelfDestructiveThread.ReplyCallback()
        {
          public void onReply(FontsContractCompat.TypefaceResult paramAnonymousTypefaceResult)
          {
            Object localObject = FontsContractCompat.sLock;
            try
            {
              ArrayList localArrayList = (ArrayList)FontsContractCompat.sPendingReplies.get(val$id);
              if (localArrayList == null) {
                return;
              }
              FontsContractCompat.sPendingReplies.remove(val$id);
              int i = 0;
              while (i < localArrayList.size())
              {
                ((SelfDestructiveThread.ReplyCallback)localArrayList.get(i)).onReply(paramAnonymousTypefaceResult);
                i += 1;
              }
              return;
            }
            catch (Throwable paramAnonymousTypefaceResult)
            {
              throw paramAnonymousTypefaceResult;
            }
          }
        });
        return null;
      }
      catch (Throwable paramContext)
      {
        throw paramContext;
      }
    }
    return localTypeface;
  }
  
  public static ProviderInfo getProvider(PackageManager paramPackageManager, FontRequest paramFontRequest, Resources paramResources)
    throws PackageManager.NameNotFoundException
  {
    String str = paramFontRequest.getProviderAuthority();
    int i = 0;
    ProviderInfo localProviderInfo = paramPackageManager.resolveContentProvider(str, 0);
    if (localProviderInfo != null)
    {
      if (packageName.equals(paramFontRequest.getProviderPackage()))
      {
        paramPackageManager = convertToByteArrayList(getPackageInfopackageName, 64).signatures);
        Collections.sort(paramPackageManager, sByteArrayComparator);
        paramFontRequest = getCertificates(paramFontRequest, paramResources);
        while (i < paramFontRequest.size())
        {
          paramResources = new ArrayList((Collection)paramFontRequest.get(i));
          Collections.sort(paramResources, sByteArrayComparator);
          if (equalsByteArrayList(paramPackageManager, paramResources)) {
            return localProviderInfo;
          }
          i += 1;
        }
        return null;
      }
      paramPackageManager = new StringBuilder();
      paramPackageManager.append("Found content provider ");
      paramPackageManager.append(str);
      paramPackageManager.append(", but package was not ");
      paramPackageManager.append(paramFontRequest.getProviderPackage());
      throw new PackageManager.NameNotFoundException(paramPackageManager.toString());
    }
    paramPackageManager = new StringBuilder();
    paramPackageManager.append("No package found for authority: ");
    paramPackageManager.append(str);
    paramPackageManager = new PackageManager.NameNotFoundException(paramPackageManager.toString());
    throw paramPackageManager;
  }
  
  public static Map prepareFontData(Context paramContext, FontInfo[] paramArrayOfFontInfo, CancellationSignal paramCancellationSignal)
  {
    HashMap localHashMap = new HashMap();
    int j = paramArrayOfFontInfo.length;
    int i = 0;
    while (i < j)
    {
      Object localObject = paramArrayOfFontInfo[i];
      if (((FontInfo)localObject).getResultCode() == 0)
      {
        localObject = ((FontInfo)localObject).getUri();
        if (!localHashMap.containsKey(localObject)) {
          localHashMap.put(localObject, TypefaceCompatUtil.mmap(paramContext, paramCancellationSignal, (Uri)localObject));
        }
      }
      i += 1;
    }
    return Collections.unmodifiableMap(localHashMap);
  }
  
  public static void requestFont(Context paramContext, FontRequest paramFontRequest, FontRequestCallback paramFontRequestCallback, Handler paramHandler)
  {
    requestFontInternal(paramContext.getApplicationContext(), paramFontRequest, paramFontRequestCallback, paramHandler);
  }
  
  private static void requestFontInternal(Context paramContext, final FontRequest paramFontRequest, final FontRequestCallback paramFontRequestCallback, Handler paramHandler)
  {
    paramHandler.post(new Runnable()
    {
      public void run()
      {
        Object localObject = val$appContext;
        FontRequest localFontRequest = paramFontRequest;
        try
        {
          localObject = FontsContractCompat.fetchFonts((Context)localObject, null, localFontRequest);
          final int i;
          if (((FontsContractCompat.FontFamilyResult)localObject).getStatusCode() != 0)
          {
            i = ((FontsContractCompat.FontFamilyResult)localObject).getStatusCode();
            if (i != 1)
            {
              if (i != 2)
              {
                val$callerThreadHandler.post(new Runnable()
                {
                  public void run()
                  {
                    val$callback.onTypefaceRequestFailed(-3);
                  }
                });
                return;
              }
              val$callerThreadHandler.post(new Runnable()
              {
                public void run()
                {
                  val$callback.onTypefaceRequestFailed(-3);
                }
              });
              return;
            }
            val$callerThreadHandler.post(new Runnable()
            {
              public void run()
              {
                val$callback.onTypefaceRequestFailed(-2);
              }
            });
            return;
          }
          localObject = ((FontsContractCompat.FontFamilyResult)localObject).getFonts();
          if ((localObject != null) && (localObject.length != 0))
          {
            int j = localObject.length;
            i = 0;
            while (i < j)
            {
              localFontRequest = localObject[i];
              if (localFontRequest.getResultCode() != 0)
              {
                i = localFontRequest.getResultCode();
                if (i < 0)
                {
                  val$callerThreadHandler.post(new Runnable()
                  {
                    public void run()
                    {
                      val$callback.onTypefaceRequestFailed(-3);
                    }
                  });
                  return;
                }
                val$callerThreadHandler.post(new Runnable()
                {
                  public void run()
                  {
                    val$callback.onTypefaceRequestFailed(i);
                  }
                });
                return;
              }
              i += 1;
            }
            localObject = FontsContractCompat.buildTypeface(val$appContext, null, (FontsContractCompat.FontInfo[])localObject);
            if (localObject == null)
            {
              val$callerThreadHandler.post(new Runnable()
              {
                public void run()
                {
                  val$callback.onTypefaceRequestFailed(-3);
                }
              });
              return;
            }
            val$callerThreadHandler.post(new Runnable()
            {
              public void run()
              {
                val$callback.onTypefaceRetrieved(val$typeface);
              }
            });
            return;
          }
          val$callerThreadHandler.post(new Runnable()
          {
            public void run()
            {
              val$callback.onTypefaceRequestFailed(1);
            }
          });
          return;
        }
        catch (PackageManager.NameNotFoundException localNameNotFoundException)
        {
          for (;;) {}
        }
        val$callerThreadHandler.post(new Runnable()
        {
          public void run()
          {
            val$callback.onTypefaceRequestFailed(-1);
          }
        });
      }
    });
  }
  
  public static void resetCache()
  {
    sTypefaceCache.evictAll();
  }
  
  public static final class Columns
    implements BaseColumns
  {
    public static final String FILE_ID = "file_id";
    public static final String ITALIC = "font_italic";
    public static final String RESULT_CODE = "result_code";
    public static final int RESULT_CODE_FONT_NOT_FOUND = 1;
    public static final int RESULT_CODE_FONT_UNAVAILABLE = 2;
    public static final int RESULT_CODE_MALFORMED_QUERY = 3;
    public static final int RESULT_CODE_OK = 0;
    public static final String TTC_INDEX = "font_ttc_index";
    public static final String VARIATION_SETTINGS = "font_variation_settings";
    public static final String WEIGHT = "font_weight";
    
    public Columns() {}
  }
  
  public static class FontFamilyResult
  {
    public static final int STATUS_OK = 0;
    public static final int STATUS_UNEXPECTED_DATA_PROVIDED = 2;
    public static final int STATUS_WRONG_CERTIFICATES = 1;
    private final FontsContractCompat.FontInfo[] mFonts;
    private final int mStatusCode;
    
    public FontFamilyResult(int paramInt, FontsContractCompat.FontInfo[] paramArrayOfFontInfo)
    {
      mStatusCode = paramInt;
      mFonts = paramArrayOfFontInfo;
    }
    
    public FontsContractCompat.FontInfo[] getFonts()
    {
      return mFonts;
    }
    
    public int getStatusCode()
    {
      return mStatusCode;
    }
  }
  
  public static class FontInfo
  {
    private final boolean mItalic;
    private final int mResultCode;
    private final int mTtcIndex;
    private final Uri mUri;
    private final int mWeight;
    
    public FontInfo(Uri paramUri, int paramInt1, int paramInt2, boolean paramBoolean, int paramInt3)
    {
      mUri = ((Uri)Preconditions.checkNotNull(paramUri));
      mTtcIndex = paramInt1;
      mWeight = paramInt2;
      mItalic = paramBoolean;
      mResultCode = paramInt3;
    }
    
    public int getResultCode()
    {
      return mResultCode;
    }
    
    public int getTtcIndex()
    {
      return mTtcIndex;
    }
    
    public Uri getUri()
    {
      return mUri;
    }
    
    public int getWeight()
    {
      return mWeight;
    }
    
    public boolean isItalic()
    {
      return mItalic;
    }
  }
  
  public static class FontRequestCallback
  {
    public static final int FAIL_REASON_FONT_LOAD_ERROR = -3;
    public static final int FAIL_REASON_FONT_NOT_FOUND = 1;
    public static final int FAIL_REASON_FONT_UNAVAILABLE = 2;
    public static final int FAIL_REASON_MALFORMED_QUERY = 3;
    public static final int FAIL_REASON_PROVIDER_NOT_FOUND = -1;
    public static final int FAIL_REASON_SECURITY_VIOLATION = -4;
    public static final int FAIL_REASON_WRONG_CERTIFICATES = -2;
    public static final int RESULT_OK = 0;
    
    public FontRequestCallback() {}
    
    public void onTypefaceRequestFailed(int paramInt) {}
    
    public void onTypefaceRetrieved(Typeface paramTypeface) {}
    
    @Retention(RetentionPolicy.SOURCE)
    public static @interface FontRequestFailReason {}
  }
  
  private static final class TypefaceResult
  {
    final int mResult;
    final Typeface mTypeface;
    
    TypefaceResult(Typeface paramTypeface, int paramInt)
    {
      mTypeface = paramTypeface;
      mResult = paramInt;
    }
  }
}
