package com.airbnb.lottie.model;

import androidx.collection.LruCache;
import com.airbnb.lottie.LottieComposition;

public class LottieCompositionCache
{
  private static final LottieCompositionCache INSTANCE = new LottieCompositionCache();
  private final LruCache<String, LottieComposition> cache = new LruCache(20);
  
  LottieCompositionCache() {}
  
  public static LottieCompositionCache getInstance()
  {
    return INSTANCE;
  }
  
  public void clear()
  {
    cache.evictAll();
  }
  
  public void put(String paramString, LottieComposition paramLottieComposition)
  {
    if (paramString == null) {
      return;
    }
    cache.put(paramString, paramLottieComposition);
  }
  
  public LottieComposition remove(String paramString)
  {
    if (paramString == null) {
      return null;
    }
    return (LottieComposition)cache.get(paramString);
  }
  
  public void resize(int paramInt)
  {
    cache.resize(paramInt);
  }
}
