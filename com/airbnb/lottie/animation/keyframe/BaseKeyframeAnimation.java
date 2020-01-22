package com.airbnb.lottie.animation.keyframe;

import android.animation.TimeInterpolator;
import com.airbnb.lottie.Way;
import com.airbnb.lottie.value.Keyframe;
import com.airbnb.lottie.value.LottieValueCallback;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseKeyframeAnimation<K, A>
{
  private float cachedEndProgress = -1.0F;
  private A cachedGetValue = null;
  private float cachedStartDelayProgress = -1.0F;
  private boolean isDiscrete = false;
  private final KeyframesWrapper<K> keyframesWrapper;
  final List<AnimationListener> listeners = new ArrayList(1);
  private float progress = 0.0F;
  protected LottieValueCallback<A> valueCallback;
  
  BaseKeyframeAnimation(List paramList)
  {
    keyframesWrapper = wrap(paramList);
  }
  
  private float getStartDelayProgress()
  {
    if (cachedStartDelayProgress == -1.0F) {
      cachedStartDelayProgress = keyframesWrapper.getStartDelayProgress();
    }
    return cachedStartDelayProgress;
  }
  
  private static KeyframesWrapper wrap(List paramList)
  {
    if (paramList.isEmpty()) {
      return new EmptyKeyframeWrapper(null);
    }
    if (paramList.size() == 1) {
      return new SingleKeyframeWrapper(paramList);
    }
    return new KeyframesWrapperImpl(paramList);
  }
  
  public void addUpdateListener(AnimationListener paramAnimationListener)
  {
    listeners.add(paramAnimationListener);
  }
  
  protected Keyframe getCurrentKeyframe()
  {
    Way.beginSection("BaseKeyframeAnimation#getCurrentKeyframe");
    Keyframe localKeyframe = keyframesWrapper.getCurrentKeyframe();
    Way.endSection("BaseKeyframeAnimation#getCurrentKeyframe");
    return localKeyframe;
  }
  
  float getEndProgress()
  {
    if (cachedEndProgress == -1.0F) {
      cachedEndProgress = keyframesWrapper.getEndProgress();
    }
    return cachedEndProgress;
  }
  
  protected float getInterpolatedCurrentKeyframeProgress()
  {
    Keyframe localKeyframe = getCurrentKeyframe();
    if (localKeyframe.isStatic()) {
      return 0.0F;
    }
    return interpolator.getInterpolation(getLinearCurrentKeyframeProgress());
  }
  
  float getLinearCurrentKeyframeProgress()
  {
    if (isDiscrete) {
      return 0.0F;
    }
    Keyframe localKeyframe = getCurrentKeyframe();
    if (localKeyframe.isStatic()) {
      return 0.0F;
    }
    return (progress - localKeyframe.getStartProgress()) / (localKeyframe.getEndProgress() - localKeyframe.getStartProgress());
  }
  
  public float getProgress()
  {
    return progress;
  }
  
  public Object getValue()
  {
    float f = getInterpolatedCurrentKeyframeProgress();
    if ((valueCallback == null) && (keyframesWrapper.isCachedValueEnabled(f))) {
      return cachedGetValue;
    }
    Object localObject = getValue(getCurrentKeyframe(), f);
    cachedGetValue = localObject;
    return localObject;
  }
  
  abstract Object getValue(Keyframe paramKeyframe, float paramFloat);
  
  public void notifyListeners()
  {
    int i = 0;
    while (i < listeners.size())
    {
      ((AnimationListener)listeners.get(i)).onValueChanged();
      i += 1;
    }
  }
  
  public void setIsDiscrete()
  {
    isDiscrete = true;
  }
  
  public void setProgress(float paramFloat)
  {
    if (keyframesWrapper.isEmpty()) {
      return;
    }
    float f;
    if (paramFloat < getStartDelayProgress())
    {
      f = getStartDelayProgress();
    }
    else
    {
      f = paramFloat;
      if (paramFloat > getEndProgress()) {
        f = getEndProgress();
      }
    }
    if (f == progress) {
      return;
    }
    progress = f;
    if (keyframesWrapper.isValueChanged(f)) {
      notifyListeners();
    }
  }
  
  public void setValueCallback(LottieValueCallback paramLottieValueCallback)
  {
    LottieValueCallback localLottieValueCallback = valueCallback;
    if (localLottieValueCallback != null) {
      localLottieValueCallback.setAnimation(null);
    }
    valueCallback = paramLottieValueCallback;
    if (paramLottieValueCallback != null) {
      paramLottieValueCallback.setAnimation(this);
    }
  }
  
  public static abstract interface AnimationListener
  {
    public abstract void onValueChanged();
  }
  
  private static final class EmptyKeyframeWrapper<T>
    implements BaseKeyframeAnimation.KeyframesWrapper<T>
  {
    private EmptyKeyframeWrapper() {}
    
    public Keyframe getCurrentKeyframe()
    {
      throw new IllegalStateException("not implemented");
    }
    
    public float getEndProgress()
    {
      return 1.0F;
    }
    
    public float getStartDelayProgress()
    {
      return 0.0F;
    }
    
    public boolean isCachedValueEnabled(float paramFloat)
    {
      throw new IllegalStateException("not implemented");
    }
    
    public boolean isEmpty()
    {
      return true;
    }
    
    public boolean isValueChanged(float paramFloat)
    {
      return false;
    }
  }
  
  private static abstract interface KeyframesWrapper<T>
  {
    public abstract Keyframe getCurrentKeyframe();
    
    public abstract float getEndProgress();
    
    public abstract float getStartDelayProgress();
    
    public abstract boolean isCachedValueEnabled(float paramFloat);
    
    public abstract boolean isEmpty();
    
    public abstract boolean isValueChanged(float paramFloat);
  }
  
  private static final class KeyframesWrapperImpl<T>
    implements BaseKeyframeAnimation.KeyframesWrapper<T>
  {
    private Keyframe<T> cachedCurrentKeyframe = null;
    private float cachedInterpolatedProgress = -1.0F;
    private Keyframe<T> currentKeyframe;
    private final List<? extends Keyframe<T>> keyframes;
    
    KeyframesWrapperImpl(List paramList)
    {
      keyframes = paramList;
      currentKeyframe = findKeyframe(0.0F);
    }
    
    private Keyframe findKeyframe(float paramFloat)
    {
      Object localObject = keyframes;
      localObject = (Keyframe)((List)localObject).get(((List)localObject).size() - 1);
      if (paramFloat >= ((Keyframe)localObject).getStartProgress()) {
        return localObject;
      }
      int i = keyframes.size() - 2;
      while (i >= 1)
      {
        localObject = (Keyframe)keyframes.get(i);
        if ((currentKeyframe != localObject) && (((Keyframe)localObject).containsProgress(paramFloat))) {
          return localObject;
        }
        i -= 1;
      }
      return (Keyframe)keyframes.get(0);
    }
    
    public Keyframe getCurrentKeyframe()
    {
      return currentKeyframe;
    }
    
    public float getEndProgress()
    {
      List localList = keyframes;
      return ((Keyframe)localList.get(localList.size() - 1)).getEndProgress();
    }
    
    public float getStartDelayProgress()
    {
      return ((Keyframe)keyframes.get(0)).getStartProgress();
    }
    
    public boolean isCachedValueEnabled(float paramFloat)
    {
      if ((cachedCurrentKeyframe == currentKeyframe) && (cachedInterpolatedProgress == paramFloat)) {
        return true;
      }
      cachedCurrentKeyframe = currentKeyframe;
      cachedInterpolatedProgress = paramFloat;
      return false;
    }
    
    public boolean isEmpty()
    {
      return false;
    }
    
    public boolean isValueChanged(float paramFloat)
    {
      if (currentKeyframe.containsProgress(paramFloat)) {
        return currentKeyframe.isStatic() ^ true;
      }
      currentKeyframe = findKeyframe(paramFloat);
      return true;
    }
  }
  
  private static final class SingleKeyframeWrapper<T>
    implements BaseKeyframeAnimation.KeyframesWrapper<T>
  {
    private float cachedInterpolatedProgress = -1.0F;
    private final Keyframe<T> keyframe;
    
    SingleKeyframeWrapper(List paramList)
    {
      keyframe = ((Keyframe)paramList.get(0));
    }
    
    public Keyframe getCurrentKeyframe()
    {
      return keyframe;
    }
    
    public float getEndProgress()
    {
      return keyframe.getEndProgress();
    }
    
    public float getStartDelayProgress()
    {
      return keyframe.getStartProgress();
    }
    
    public boolean isCachedValueEnabled(float paramFloat)
    {
      if (cachedInterpolatedProgress == paramFloat) {
        return true;
      }
      cachedInterpolatedProgress = paramFloat;
      return false;
    }
    
    public boolean isEmpty()
    {
      return false;
    }
    
    public boolean isValueChanged(float paramFloat)
    {
      return keyframe.isStatic() ^ true;
    }
  }
}