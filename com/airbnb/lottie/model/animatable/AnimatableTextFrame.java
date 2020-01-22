package com.airbnb.lottie.model.animatable;

import com.airbnb.lottie.animation.keyframe.TextKeyframeAnimation;
import com.airbnb.lottie.model.DocumentData;
import java.util.List;

public class AnimatableTextFrame
  extends BaseAnimatableValue<DocumentData, DocumentData>
{
  public AnimatableTextFrame(List paramList)
  {
    super(paramList);
  }
  
  public TextKeyframeAnimation createAnimation()
  {
    return new TextKeyframeAnimation(keyframes);
  }
}
