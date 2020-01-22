package com.airbnb.lottie;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.airbnb.lottie.model.LottieCompositionCache;
import com.airbnb.lottie.network.NetworkFetcher;
import com.airbnb.lottie.parser.LottieCompositionMoshiParser;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.airbnb.lottie.utils.Utils;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import okio.Okio;
import org.json.JSONObject;

public class LottieCompositionFactory
{
  private static final Map<String, LottieTask<LottieComposition>> taskCache = new HashMap();
  
  private LottieCompositionFactory() {}
  
  private static LottieTask cache(String paramString, Callable paramCallable)
  {
    LottieComposition localLottieComposition;
    if (paramString == null) {
      localLottieComposition = null;
    } else {
      localLottieComposition = LottieCompositionCache.getInstance().remove(paramString);
    }
    if (localLottieComposition != null) {
      new LottieTask(new Callable()
      {
        public LottieResult call()
        {
          return new LottieResult(LottieCompositionFactory.this);
        }
      });
    }
    if ((paramString != null) && (taskCache.containsKey(paramString))) {
      return (LottieTask)taskCache.get(paramString);
    }
    paramCallable = new LottieTask(paramCallable);
    if (paramString != null)
    {
      paramCallable.addListener(new LottieListener()
      {
        public void onResult(LottieComposition paramAnonymousLottieComposition)
        {
          LottieCompositionFactory.taskCache.remove(LottieCompositionFactory.this);
        }
      });
      paramCallable.addFailureListener(new LottieListener()
      {
        public void onResult(Throwable paramAnonymousThrowable)
        {
          LottieCompositionFactory.taskCache.remove(LottieCompositionFactory.this);
        }
      });
      taskCache.put(paramString, paramCallable);
    }
    return paramCallable;
  }
  
  private static LottieImageAsset findImageAssetForFileName(LottieComposition paramLottieComposition, String paramString)
  {
    paramLottieComposition = paramLottieComposition.getImages().values().iterator();
    while (paramLottieComposition.hasNext())
    {
      LottieImageAsset localLottieImageAsset = (LottieImageAsset)paramLottieComposition.next();
      if (localLottieImageAsset.getFileName().equals(paramString)) {
        return localLottieImageAsset;
      }
    }
    return null;
  }
  
  public static LottieTask fromAsset(Context paramContext, String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("asset_");
    localStringBuilder.append(paramString);
    return fromAsset(paramContext, paramString, localStringBuilder.toString());
  }
  
  public static LottieTask fromAsset(Context paramContext, final String paramString1, final String paramString2)
  {
    cache(paramString2, new Callable()
    {
      public LottieResult call()
      {
        return LottieCompositionFactory.fromAssetSync(LottieCompositionFactory.this, paramString1, paramString2);
      }
    });
  }
  
  public static LottieResult fromAssetSync(Context paramContext, String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("asset_");
    localStringBuilder.append(paramString);
    return fromAssetSync(paramContext, paramString, localStringBuilder.toString());
  }
  
  public static LottieResult fromAssetSync(Context paramContext, String paramString1, String paramString2)
  {
    try
    {
      boolean bool = paramString1.endsWith(".zip");
      if (bool)
      {
        paramContext = fromZipStreamSync(new ZipInputStream(paramContext.getAssets().open(paramString1)), paramString2);
        return paramContext;
      }
      paramContext = fromJsonInputStreamSync(paramContext.getAssets().open(paramString1), paramString2);
      return paramContext;
    }
    catch (IOException paramContext) {}
    return new LottieResult(paramContext);
  }
  
  public static LottieTask fromJson(JSONObject paramJSONObject, final String paramString)
  {
    cache(paramString, new Callable()
    {
      public LottieResult call()
      {
        return LottieCompositionFactory.fromJsonSync(LottieCompositionFactory.this, paramString);
      }
    });
  }
  
  public static LottieTask fromJsonInputStream(InputStream paramInputStream, final String paramString)
  {
    cache(paramString, new Callable()
    {
      public LottieResult call()
      {
        return LottieCompositionFactory.fromJsonInputStreamSync(LottieCompositionFactory.this, paramString);
      }
    });
  }
  
  public static LottieResult fromJsonInputStreamSync(InputStream paramInputStream, String paramString)
  {
    return fromJsonInputStreamSync(paramInputStream, paramString, true);
  }
  
  private static LottieResult fromJsonInputStreamSync(InputStream paramInputStream, String paramString, boolean paramBoolean)
  {
    try
    {
      paramString = fromJsonReaderSync(JsonReader.socket(Okio.buffer(Okio.source(paramInputStream))), paramString);
      if (paramBoolean)
      {
        Utils.closeQuietly(paramInputStream);
        return paramString;
      }
    }
    catch (Throwable paramString)
    {
      if (paramBoolean) {
        Utils.closeQuietly(paramInputStream);
      }
      throw paramString;
    }
    return paramString;
  }
  
  public static LottieTask fromJsonReader(JsonReader paramJsonReader, final String paramString)
  {
    cache(paramString, new Callable()
    {
      public LottieResult call()
      {
        return LottieCompositionFactory.fromJsonReaderSync(LottieCompositionFactory.this, paramString);
      }
    });
  }
  
  public static LottieResult fromJsonReaderSync(JsonReader paramJsonReader, String paramString)
  {
    return fromJsonReaderSyncInternal(paramJsonReader, paramString, true);
  }
  
  private static LottieResult fromJsonReaderSyncInternal(JsonReader paramJsonReader, String paramString, boolean paramBoolean)
  {
    Object localObject;
    try
    {
      localObject = LottieCompositionMoshiParser.parse(paramJsonReader);
      if (paramString != null) {
        LottieCompositionCache.getInstance().put(paramString, (LottieComposition)localObject);
      }
      localObject = new LottieResult(localObject);
      paramString = (String)localObject;
      if (!paramBoolean) {
        return paramString;
      }
      Utils.closeQuietly(paramJsonReader);
      return localObject;
    }
    catch (Throwable paramString) {}catch (Exception paramString)
    {
      localObject = new LottieResult(paramString);
      paramString = (String)localObject;
      if (!paramBoolean) {
        return paramString;
      }
    }
    Utils.closeQuietly(paramJsonReader);
    return localObject;
    if (paramBoolean) {
      Utils.closeQuietly(paramJsonReader);
    }
    throw paramString;
    return paramString;
  }
  
  public static LottieTask fromJsonString(String paramString1, final String paramString2)
  {
    cache(paramString2, new Callable()
    {
      public LottieResult call()
      {
        return LottieCompositionFactory.fromJsonStringSync(LottieCompositionFactory.this, paramString2);
      }
    });
  }
  
  public static LottieResult fromJsonStringSync(String paramString1, String paramString2)
  {
    return fromJsonReaderSync(JsonReader.socket(Okio.buffer(Okio.source(new ByteArrayInputStream(paramString1.getBytes())))), paramString2);
  }
  
  public static LottieResult fromJsonSync(JSONObject paramJSONObject, String paramString)
  {
    return fromJsonStringSync(paramJSONObject.toString(), paramString);
  }
  
  public static LottieTask fromRawRes(Context paramContext, int paramInt)
  {
    return fromRawRes(paramContext, paramInt, rawResCacheKey(paramContext, paramInt));
  }
  
  public static LottieTask fromRawRes(Context paramContext, final int paramInt, String paramString)
  {
    cache(paramString, new Callable()
    {
      public LottieResult call()
      {
        Context localContext = (Context)get();
        if (localContext == null) {
          localContext = val$appContext;
        }
        return LottieCompositionFactory.fromRawResSync(localContext, paramInt);
      }
    });
  }
  
  public static LottieResult fromRawResSync(Context paramContext, int paramInt)
  {
    return fromRawResSync(paramContext, paramInt, rawResCacheKey(paramContext, paramInt));
  }
  
  public static LottieResult fromRawResSync(Context paramContext, int paramInt, String paramString)
  {
    try
    {
      paramContext = fromJsonInputStreamSync(paramContext.getResources().openRawResource(paramInt), paramString);
      return paramContext;
    }
    catch (Resources.NotFoundException paramContext) {}
    return new LottieResult(paramContext);
  }
  
  public static LottieTask fromUrl(Context paramContext, String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("url_");
    localStringBuilder.append(paramString);
    return fromUrl(paramContext, paramString, localStringBuilder.toString());
  }
  
  public static LottieTask fromUrl(Context paramContext, final String paramString1, String paramString2)
  {
    cache(paramString2, new Callable()
    {
      public LottieResult call()
      {
        return NetworkFetcher.fetchSync(LottieCompositionFactory.this, paramString1);
      }
    });
  }
  
  public static LottieResult fromUrlSync(Context paramContext, String paramString)
  {
    return NetworkFetcher.fetchSync(paramContext, paramString);
  }
  
  public static LottieTask fromZipStream(ZipInputStream paramZipInputStream, final String paramString)
  {
    cache(paramString, new Callable()
    {
      public LottieResult call()
      {
        return LottieCompositionFactory.fromZipStreamSync(LottieCompositionFactory.this, paramString);
      }
    });
  }
  
  public static LottieResult fromZipStreamSync(ZipInputStream paramZipInputStream, String paramString)
  {
    try
    {
      paramString = fromZipStreamSyncInternal(paramZipInputStream, paramString);
      Utils.closeQuietly(paramZipInputStream);
      return paramString;
    }
    catch (Throwable paramString)
    {
      Utils.closeQuietly(paramZipInputStream);
      throw paramString;
    }
  }
  
  private static LottieResult fromZipStreamSyncInternal(ZipInputStream paramZipInputStream, String paramString)
  {
    Object localObject3 = new HashMap();
    try
    {
      Object localObject2 = paramZipInputStream.getNextEntry();
      Object localObject1 = null;
      while (localObject2 != null)
      {
        String str = ((ZipEntry)localObject2).getName();
        boolean bool = str.contains("__MACOSX");
        if (bool)
        {
          paramZipInputStream.closeEntry();
        }
        else
        {
          bool = ((ZipEntry)localObject2).getName().contains(".json");
          if (bool)
          {
            localObject1 = fromJsonReaderSyncInternal(JsonReader.socket(Okio.buffer(Okio.source(paramZipInputStream))), null, false).getValue();
            localObject1 = (LottieComposition)localObject1;
          }
          else
          {
            bool = str.contains(".png");
            if (!bool)
            {
              bool = str.contains(".webp");
              if (!bool)
              {
                paramZipInputStream.closeEntry();
                break label162;
              }
            }
            localObject2 = str.split("/");
            localObject2 = localObject2[(localObject2.length - 1)];
            ((Map)localObject3).put(localObject2, BitmapFactory.decodeStream(paramZipInputStream));
          }
        }
        label162:
        localObject2 = paramZipInputStream.getNextEntry();
      }
      if (localObject1 == null) {
        return new LottieResult(new IllegalArgumentException("Unable to parse composition"));
      }
      paramZipInputStream = ((Map)localObject3).entrySet().iterator();
      while (paramZipInputStream.hasNext())
      {
        localObject2 = (Map.Entry)paramZipInputStream.next();
        localObject3 = findImageAssetForFileName((LottieComposition)localObject1, (String)((Map.Entry)localObject2).getKey());
        if (localObject3 != null) {
          ((LottieImageAsset)localObject3).setBitmap(Utils.resizeBitmapIfNeeded((Bitmap)((Map.Entry)localObject2).getValue(), ((LottieImageAsset)localObject3).getWidth(), ((LottieImageAsset)localObject3).getHeight()));
        }
      }
      localObject2 = ((LottieComposition)localObject1).getImages().entrySet().iterator();
      while (((Iterator)localObject2).hasNext())
      {
        paramZipInputStream = (Map.Entry)((Iterator)localObject2).next();
        if (((LottieImageAsset)paramZipInputStream.getValue()).getBitmap() == null)
        {
          paramString = new StringBuilder();
          paramString.append("There is no image for ");
          paramString.append(((LottieImageAsset)paramZipInputStream.getValue()).getFileName());
          return new LottieResult(new IllegalStateException(paramString.toString()));
        }
      }
      if (paramString != null) {
        LottieCompositionCache.getInstance().put(paramString, (LottieComposition)localObject1);
      }
      return new LottieResult(localObject1);
    }
    catch (IOException paramZipInputStream) {}
    return new LottieResult(paramZipInputStream);
  }
  
  private static boolean isNightMode(Context paramContext)
  {
    return (getResourcesgetConfigurationuiMode & 0x30) == 32;
  }
  
  private static String rawResCacheKey(Context paramContext, int paramInt)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("rawRes");
    if (isNightMode(paramContext)) {
      paramContext = "_night_";
    } else {
      paramContext = "_day_";
    }
    localStringBuilder.append(paramContext);
    localStringBuilder.append(paramInt);
    return localStringBuilder.toString();
  }
  
  public static void setMaxCacheSize(int paramInt)
  {
    LottieCompositionCache.getInstance().resize(paramInt);
  }
}
