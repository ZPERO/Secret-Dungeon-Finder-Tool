package androidx.appcompat.content.wiki;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import androidx.appcompat.widget.ResourceManagerInternal;
import androidx.core.content.ContextCompat;
import androidx.core.content.delay.ColorStateListInflaterCompat;
import java.util.WeakHashMap;

public final class AppCompatResources
{
  private static final String LOG_TAG = "AppCompatResources";
  private static final ThreadLocal<TypedValue> TL_TYPED_VALUE = new ThreadLocal();
  private static final Object sColorStateCacheLock = new Object();
  private static final WeakHashMap<Context, SparseArray<androidx.appcompat.content.res.AppCompatResources.ColorStateListCacheEntry>> sColorStateCaches = new WeakHashMap(0);
  
  private AppCompatResources() {}
  
  private static void addColorStateListToCache(Context paramContext, int paramInt, ColorStateList paramColorStateList)
  {
    Object localObject = sColorStateCacheLock;
    try
    {
      SparseArray localSparseArray2 = (SparseArray)sColorStateCaches.get(paramContext);
      SparseArray localSparseArray1 = localSparseArray2;
      if (localSparseArray2 == null)
      {
        localSparseArray1 = new SparseArray();
        sColorStateCaches.put(paramContext, localSparseArray1);
      }
      localSparseArray1.append(paramInt, new ColorStateListCacheEntry(paramColorStateList, paramContext.getResources().getConfiguration()));
      return;
    }
    catch (Throwable paramContext)
    {
      throw paramContext;
    }
  }
  
  private static ColorStateList getCachedColorStateList(Context paramContext, int paramInt)
  {
    Object localObject = sColorStateCacheLock;
    try
    {
      SparseArray localSparseArray = (SparseArray)sColorStateCaches.get(paramContext);
      if ((localSparseArray != null) && (localSparseArray.size() > 0))
      {
        ColorStateListCacheEntry localColorStateListCacheEntry = (ColorStateListCacheEntry)localSparseArray.get(paramInt);
        if (localColorStateListCacheEntry != null)
        {
          if (configuration.equals(paramContext.getResources().getConfiguration()))
          {
            paramContext = value;
            return paramContext;
          }
          localSparseArray.remove(paramInt);
        }
      }
      return null;
    }
    catch (Throwable paramContext)
    {
      throw paramContext;
    }
  }
  
  public static ColorStateList getColorStateList(Context paramContext, int paramInt)
  {
    if (Build.VERSION.SDK_INT >= 23) {
      return paramContext.getColorStateList(paramInt);
    }
    ColorStateList localColorStateList = getCachedColorStateList(paramContext, paramInt);
    if (localColorStateList != null) {
      return localColorStateList;
    }
    localColorStateList = inflateColorStateList(paramContext, paramInt);
    if (localColorStateList != null)
    {
      addColorStateListToCache(paramContext, paramInt, localColorStateList);
      return localColorStateList;
    }
    return ContextCompat.getColorStateList(paramContext, paramInt);
  }
  
  public static Drawable getDrawable(Context paramContext, int paramInt)
  {
    return ResourceManagerInternal.get().getDrawable(paramContext, paramInt);
  }
  
  private static TypedValue getTypedValue()
  {
    TypedValue localTypedValue2 = (TypedValue)TL_TYPED_VALUE.get();
    TypedValue localTypedValue1 = localTypedValue2;
    if (localTypedValue2 == null)
    {
      localTypedValue1 = new TypedValue();
      TL_TYPED_VALUE.set(localTypedValue1);
    }
    return localTypedValue1;
  }
  
  private static ColorStateList inflateColorStateList(Context paramContext, int paramInt)
  {
    if (isColorInt(paramContext, paramInt)) {
      return null;
    }
    Resources localResources = paramContext.getResources();
    XmlResourceParser localXmlResourceParser = localResources.getXml(paramInt);
    try
    {
      paramContext = ColorStateListInflaterCompat.createFromXml(localResources, localXmlResourceParser, paramContext.getTheme());
      return paramContext;
    }
    catch (Exception paramContext)
    {
      Log.e("AppCompatResources", "Failed to inflate ColorStateList, leaving it to the framework", paramContext);
    }
    return null;
  }
  
  private static boolean isColorInt(Context paramContext, int paramInt)
  {
    paramContext = paramContext.getResources();
    TypedValue localTypedValue = getTypedValue();
    paramContext.getValue(paramInt, localTypedValue, true);
    return (type >= 28) && (type <= 31);
  }
  
  class ColorStateListCacheEntry
  {
    final Configuration configuration;
    
    ColorStateListCacheEntry(Configuration paramConfiguration)
    {
      configuration = paramConfiguration;
    }
  }
}
