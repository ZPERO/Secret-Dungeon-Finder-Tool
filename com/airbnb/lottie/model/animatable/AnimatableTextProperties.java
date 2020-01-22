package com.airbnb.lottie.model.animatable;

public class AnimatableTextProperties
{
  public final AnimatableColorValue color;
  public final AnimatableColorValue stroke;
  public final AnimatableFloatValue strokeWidth;
  public final AnimatableFloatValue tracking;
  
  public AnimatableTextProperties(AnimatableColorValue paramAnimatableColorValue1, AnimatableColorValue paramAnimatableColorValue2, AnimatableFloatValue paramAnimatableFloatValue1, AnimatableFloatValue paramAnimatableFloatValue2)
  {
    color = paramAnimatableColorValue1;
    stroke = paramAnimatableColorValue2;
    strokeWidth = paramAnimatableFloatValue1;
    tracking = paramAnimatableFloatValue2;
  }
}
