package com.airbnb.lottie.value;

import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;

public class LottieValueCallback<T>
{
  private BaseKeyframeAnimation<?, ?> animation;
  private final LottieFrameInfo<T> frameInfo = new LottieFrameInfo();
  protected T value = null;
  
  public LottieValueCallback() {}
  
  public LottieValueCallback(Object paramObject)
  {
    value = paramObject;
  }
  
  public Object getValue(LottieFrameInfo paramLottieFrameInfo)
  {
    return value;
  }
  
  public final Object getValueInternal(float paramFloat1, float paramFloat2, Object paramObject1, Object paramObject2, float paramFloat3, float paramFloat4, float paramFloat5)
  {
    return getValue(frameInfo.map(paramFloat1, paramFloat2, paramObject1, paramObject2, paramFloat3, paramFloat4, paramFloat5));
  }
  
  public final void setAnimation(BaseKeyframeAnimation paramBaseKeyframeAnimation)
  {
    animation = paramBaseKeyframeAnimation;
  }
  
  public final void setValue(Object paramObject)
  {
    value = paramObject;
    paramObject = animation;
    if (paramObject != null) {
      paramObject.notifyListeners();
    }
  }
}