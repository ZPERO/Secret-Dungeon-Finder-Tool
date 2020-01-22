package com.airbnb.lottie;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import androidx.collection.LongSparseArray;
import androidx.collection.SparseArrayCompat;
import com.airbnb.lottie.model.Font;
import com.airbnb.lottie.model.FontCharacter;
import com.airbnb.lottie.model.Marker;
import com.airbnb.lottie.model.layer.Layer;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.airbnb.lottie.utils.Logger;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

public class LottieComposition
{
  private Rect bounds;
  private SparseArrayCompat<FontCharacter> characters;
  private float endFrame;
  private Map<String, Font> fonts;
  private float frameRate;
  private boolean hasDashPattern;
  private Map<String, LottieImageAsset> images;
  private LongSparseArray<Layer> layerMap;
  private List<Layer> layers;
  private List<Marker> markers;
  private int maskAndMatteCount = 0;
  private final PerformanceTracker performanceTracker = new PerformanceTracker();
  private Map<String, List<Layer>> precomps;
  private float startFrame;
  private final HashSet<String> warnings = new HashSet();
  
  public LottieComposition() {}
  
  public void addWarning(String paramString)
  {
    Logger.warning(paramString);
    warnings.add(paramString);
  }
  
  public Rect getBounds()
  {
    return bounds;
  }
  
  public SparseArrayCompat getCharacters()
  {
    return characters;
  }
  
  public float getDuration()
  {
    return (float)(getDurationFrames() / frameRate * 1000.0F);
  }
  
  public float getDurationFrames()
  {
    return endFrame - startFrame;
  }
  
  public float getEndFrame()
  {
    return endFrame;
  }
  
  public Map getFonts()
  {
    return fonts;
  }
  
  public float getFrameRate()
  {
    return frameRate;
  }
  
  public Map getImages()
  {
    return images;
  }
  
  public List getLayers()
  {
    return layers;
  }
  
  public Marker getMarker(String paramString)
  {
    markers.size();
    int i = 0;
    while (i < markers.size())
    {
      Marker localMarker = (Marker)markers.get(i);
      if (localMarker.matchesName(paramString)) {
        return localMarker;
      }
      i += 1;
    }
    return null;
  }
  
  public List getMarkers()
  {
    return markers;
  }
  
  public int getMaskAndMatteCount()
  {
    return maskAndMatteCount;
  }
  
  public PerformanceTracker getPerformanceTracker()
  {
    return performanceTracker;
  }
  
  public List getPrecomps(String paramString)
  {
    return (List)precomps.get(paramString);
  }
  
  public float getStartFrame()
  {
    return startFrame;
  }
  
  public ArrayList getWarnings()
  {
    HashSet localHashSet = warnings;
    return new ArrayList(Arrays.asList(localHashSet.toArray(new String[localHashSet.size()])));
  }
  
  public boolean hasDashPattern()
  {
    return hasDashPattern;
  }
  
  public boolean hasImages()
  {
    return images.isEmpty() ^ true;
  }
  
  public void incrementMatteOrMaskCount(int paramInt)
  {
    maskAndMatteCount += paramInt;
  }
  
  public void init(Rect paramRect, float paramFloat1, float paramFloat2, float paramFloat3, List paramList1, LongSparseArray paramLongSparseArray, Map paramMap1, Map paramMap2, SparseArrayCompat paramSparseArrayCompat, Map paramMap3, List paramList2)
  {
    bounds = paramRect;
    startFrame = paramFloat1;
    endFrame = paramFloat2;
    frameRate = paramFloat3;
    layers = paramList1;
    layerMap = paramLongSparseArray;
    precomps = paramMap1;
    images = paramMap2;
    characters = paramSparseArrayCompat;
    fonts = paramMap3;
    markers = paramList2;
  }
  
  public Layer layerModelForId(long paramLong)
  {
    return (Layer)layerMap.get(paramLong);
  }
  
  public void setHasDashPattern(boolean paramBoolean)
  {
    hasDashPattern = paramBoolean;
  }
  
  public void setPerformanceTrackingEnabled(boolean paramBoolean)
  {
    performanceTracker.setEnabled(paramBoolean);
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder("LottieComposition:\n");
    Iterator localIterator = layers.iterator();
    while (localIterator.hasNext()) {
      localStringBuilder.append(((Layer)localIterator.next()).toString("\t"));
    }
    return localStringBuilder.toString();
  }
  
  @Deprecated
  public static class Factory
  {
    private Factory() {}
    
    public static Cancellable fromAssetFileName(Context paramContext, String paramString, OnCompositionLoadedListener paramOnCompositionLoadedListener)
    {
      paramOnCompositionLoadedListener = new ListenerAdapter(paramOnCompositionLoadedListener, null);
      LottieCompositionFactory.fromAsset(paramContext, paramString).addListener(paramOnCompositionLoadedListener);
      return paramOnCompositionLoadedListener;
    }
    
    public static LottieComposition fromFileSync(Context paramContext, String paramString)
    {
      return (LottieComposition)LottieCompositionFactory.fromAssetSync(paramContext, paramString).getValue();
    }
    
    public static Cancellable fromInputStream(InputStream paramInputStream, OnCompositionLoadedListener paramOnCompositionLoadedListener)
    {
      paramOnCompositionLoadedListener = new ListenerAdapter(paramOnCompositionLoadedListener, null);
      LottieCompositionFactory.fromJsonInputStream(paramInputStream, null).addListener(paramOnCompositionLoadedListener);
      return paramOnCompositionLoadedListener;
    }
    
    public static LottieComposition fromInputStreamSync(InputStream paramInputStream)
    {
      return (LottieComposition)LottieCompositionFactory.fromJsonInputStreamSync(paramInputStream, null).getValue();
    }
    
    public static LottieComposition fromInputStreamSync(InputStream paramInputStream, boolean paramBoolean)
    {
      if (paramBoolean) {
        Logger.warning("Lottie now auto-closes input stream!");
      }
      return (LottieComposition)LottieCompositionFactory.fromJsonInputStreamSync(paramInputStream, null).getValue();
    }
    
    public static Cancellable fromJsonReader(JsonReader paramJsonReader, OnCompositionLoadedListener paramOnCompositionLoadedListener)
    {
      paramOnCompositionLoadedListener = new ListenerAdapter(paramOnCompositionLoadedListener, null);
      LottieCompositionFactory.fromJsonReader(paramJsonReader, null).addListener(paramOnCompositionLoadedListener);
      return paramOnCompositionLoadedListener;
    }
    
    public static Cancellable fromJsonString(String paramString, OnCompositionLoadedListener paramOnCompositionLoadedListener)
    {
      paramOnCompositionLoadedListener = new ListenerAdapter(paramOnCompositionLoadedListener, null);
      LottieCompositionFactory.fromJsonString(paramString, null).addListener(paramOnCompositionLoadedListener);
      return paramOnCompositionLoadedListener;
    }
    
    public static LottieComposition fromJsonSync(Resources paramResources, JSONObject paramJSONObject)
    {
      return (LottieComposition)LottieCompositionFactory.fromJsonSync(paramJSONObject, null).getValue();
    }
    
    public static LottieComposition fromJsonSync(JsonReader paramJsonReader)
      throws IOException
    {
      return (LottieComposition)LottieCompositionFactory.fromJsonReaderSync(paramJsonReader, null).getValue();
    }
    
    public static LottieComposition fromJsonSync(String paramString)
    {
      return (LottieComposition)LottieCompositionFactory.fromJsonStringSync(paramString, null).getValue();
    }
    
    public static Cancellable fromRawFile(Context paramContext, int paramInt, OnCompositionLoadedListener paramOnCompositionLoadedListener)
    {
      paramOnCompositionLoadedListener = new ListenerAdapter(paramOnCompositionLoadedListener, null);
      LottieCompositionFactory.fromRawRes(paramContext, paramInt).addListener(paramOnCompositionLoadedListener);
      return paramOnCompositionLoadedListener;
    }
    
    private static final class ListenerAdapter
      implements LottieListener<LottieComposition>, Cancellable
    {
      private boolean cancelled = false;
      private final OnCompositionLoadedListener listener;
      
      private ListenerAdapter(OnCompositionLoadedListener paramOnCompositionLoadedListener)
      {
        listener = paramOnCompositionLoadedListener;
      }
      
      public void cancel()
      {
        cancelled = true;
      }
      
      public void onResult(LottieComposition paramLottieComposition)
      {
        if (cancelled) {
          return;
        }
        listener.onCompositionLoaded(paramLottieComposition);
      }
    }
  }
}
