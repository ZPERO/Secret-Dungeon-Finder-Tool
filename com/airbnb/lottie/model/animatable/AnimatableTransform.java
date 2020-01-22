package com.airbnb.lottie.model.animatable;

import android.graphics.PointF;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.animation.content.Content;
import com.airbnb.lottie.animation.content.ModifierContent;
import com.airbnb.lottie.animation.keyframe.TransformKeyframeAnimation;
import com.airbnb.lottie.model.content.ContentModel;
import com.airbnb.lottie.model.layer.BaseLayer;

public class AnimatableTransform
  implements ModifierContent, ContentModel
{
  private final AnimatablePathValue anchorPoint;
  private final AnimatableFloatValue endOpacity;
  private final AnimatableIntegerValue opacity;
  private final AnimatableValue<PointF, PointF> position;
  private final AnimatableFloatValue rotation;
  private final AnimatableScaleValue scale;
  private final AnimatableFloatValue skew;
  private final AnimatableFloatValue skewAngle;
  private final AnimatableFloatValue startOpacity;
  
  public AnimatableTransform()
  {
    this(null, null, null, null, null, null, null, null, null);
  }
  
  public AnimatableTransform(AnimatablePathValue paramAnimatablePathValue, AnimatableValue paramAnimatableValue, AnimatableScaleValue paramAnimatableScaleValue, AnimatableFloatValue paramAnimatableFloatValue1, AnimatableIntegerValue paramAnimatableIntegerValue, AnimatableFloatValue paramAnimatableFloatValue2, AnimatableFloatValue paramAnimatableFloatValue3, AnimatableFloatValue paramAnimatableFloatValue4, AnimatableFloatValue paramAnimatableFloatValue5)
  {
    anchorPoint = paramAnimatablePathValue;
    position = paramAnimatableValue;
    scale = paramAnimatableScaleValue;
    rotation = paramAnimatableFloatValue1;
    opacity = paramAnimatableIntegerValue;
    startOpacity = paramAnimatableFloatValue2;
    endOpacity = paramAnimatableFloatValue3;
    skew = paramAnimatableFloatValue4;
    skewAngle = paramAnimatableFloatValue5;
  }
  
  public TransformKeyframeAnimation createAnimation()
  {
    return new TransformKeyframeAnimation(this);
  }
  
  public AnimatablePathValue getAnchorPoint()
  {
    return anchorPoint;
  }
  
  public AnimatableFloatValue getEndOpacity()
  {
    return endOpacity;
  }
  
  public AnimatableIntegerValue getOpacity()
  {
    return opacity;
  }
  
  public AnimatableValue getPosition()
  {
    return position;
  }
  
  public AnimatableFloatValue getRotation()
  {
    return rotation;
  }
  
  public AnimatableScaleValue getScale()
  {
    return scale;
  }
  
  public AnimatableFloatValue getSkew()
  {
    return skew;
  }
  
  public AnimatableFloatValue getSkewAngle()
  {
    return skewAngle;
  }
  
  public AnimatableFloatValue getStartOpacity()
  {
    return startOpacity;
  }
  
  public Content toContent(LottieDrawable paramLottieDrawable, BaseLayer paramBaseLayer)
  {
    return null;
  }
}
