package com.airbnb.lottie.animation.content;

import android.graphics.Path;
import android.graphics.PointF;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation.AnimationListener;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.animatable.AnimatableValue;
import com.airbnb.lottie.model.content.PolystarShape;
import com.airbnb.lottie.model.content.PolystarShape.Type;
import com.airbnb.lottie.model.content.ShapeTrimPath.Type;
import com.airbnb.lottie.model.layer.BaseLayer;
import com.airbnb.lottie.utils.MiscUtils;
import com.airbnb.lottie.value.LottieValueCallback;
import java.util.List;

public class PolystarContent
  implements PathContent, BaseKeyframeAnimation.AnimationListener, KeyPathElementContent
{
  private static final float POLYGON_MAGIC_NUMBER = 0.25F;
  private static final float POLYSTAR_MAGIC_NUMBER = 0.47829F;
  private final boolean hidden;
  private final BaseKeyframeAnimation<?, Float> innerRadiusAnimation;
  private final BaseKeyframeAnimation<?, Float> innerRoundednessAnimation;
  private boolean isPathValid;
  private final LottieDrawable lottieDrawable;
  private final String name;
  private final BaseKeyframeAnimation<?, Float> outerRadiusAnimation;
  private final BaseKeyframeAnimation<?, Float> outerRoundednessAnimation;
  private final Path path = new Path();
  private final BaseKeyframeAnimation<?, Float> pointsAnimation;
  private final BaseKeyframeAnimation<?, PointF> positionAnimation;
  private final BaseKeyframeAnimation<?, Float> rotationAnimation;
  private CompoundTrimPathContent trimPaths = new CompoundTrimPathContent();
  private final PolystarShape.Type type;
  
  public PolystarContent(LottieDrawable paramLottieDrawable, BaseLayer paramBaseLayer, PolystarShape paramPolystarShape)
  {
    lottieDrawable = paramLottieDrawable;
    name = paramPolystarShape.getName();
    type = paramPolystarShape.getType();
    hidden = paramPolystarShape.isHidden();
    pointsAnimation = paramPolystarShape.getPoints().createAnimation();
    positionAnimation = paramPolystarShape.getPosition().createAnimation();
    rotationAnimation = paramPolystarShape.getRotation().createAnimation();
    outerRadiusAnimation = paramPolystarShape.getOuterRadius().createAnimation();
    outerRoundednessAnimation = paramPolystarShape.getOuterRoundedness().createAnimation();
    if (type == PolystarShape.Type.STAR)
    {
      innerRadiusAnimation = paramPolystarShape.getInnerRadius().createAnimation();
      innerRoundednessAnimation = paramPolystarShape.getInnerRoundedness().createAnimation();
    }
    else
    {
      innerRadiusAnimation = null;
      innerRoundednessAnimation = null;
    }
    paramBaseLayer.addAnimation(pointsAnimation);
    paramBaseLayer.addAnimation(positionAnimation);
    paramBaseLayer.addAnimation(rotationAnimation);
    paramBaseLayer.addAnimation(outerRadiusAnimation);
    paramBaseLayer.addAnimation(outerRoundednessAnimation);
    if (type == PolystarShape.Type.STAR)
    {
      paramBaseLayer.addAnimation(innerRadiusAnimation);
      paramBaseLayer.addAnimation(innerRoundednessAnimation);
    }
    pointsAnimation.addUpdateListener(this);
    positionAnimation.addUpdateListener(this);
    rotationAnimation.addUpdateListener(this);
    outerRadiusAnimation.addUpdateListener(this);
    outerRoundednessAnimation.addUpdateListener(this);
    if (type == PolystarShape.Type.STAR)
    {
      innerRadiusAnimation.addUpdateListener(this);
      innerRoundednessAnimation.addUpdateListener(this);
    }
  }
  
  private void createPolygonPath()
  {
    int i = (int)Math.floor(((Float)pointsAnimation.getValue()).floatValue());
    Object localObject = rotationAnimation;
    if (localObject == null) {
      d1 = 0.0D;
    } else {
      d1 = ((Float)((BaseKeyframeAnimation)localObject).getValue()).floatValue();
    }
    double d1 = Math.toRadians(d1 - 90.0D);
    double d4 = i;
    Double.isNaN(d4);
    float f3 = (float)(6.283185307179586D / d4);
    float f5 = ((Float)outerRoundednessAnimation.getValue()).floatValue() / 100.0F;
    float f6 = ((Float)outerRadiusAnimation.getValue()).floatValue();
    double d2 = f6;
    double d3 = Math.cos(d1);
    Double.isNaN(d2);
    float f1 = (float)(d3 * d2);
    d3 = Math.sin(d1);
    Double.isNaN(d2);
    float f2 = (float)(d3 * d2);
    path.moveTo(f1, f2);
    d3 = f3;
    Double.isNaN(d3);
    d1 += d3;
    d4 = Math.ceil(d4);
    i = 0;
    while (i < d4)
    {
      double d5 = Math.cos(d1);
      Double.isNaN(d2);
      f3 = (float)(d5 * d2);
      d5 = Math.sin(d1);
      Double.isNaN(d2);
      float f4 = (float)(d2 * d5);
      if (f5 != 0.0F)
      {
        d5 = (float)(Math.atan2(f2, f1) - 1.5707963267948966D);
        float f7 = (float)Math.cos(d5);
        float f8 = (float)Math.sin(d5);
        d5 = (float)(Math.atan2(f4, f3) - 1.5707963267948966D);
        float f9 = (float)Math.cos(d5);
        float f10 = (float)Math.sin(d5);
        float f11 = f6 * f5 * 0.25F;
        path.cubicTo(f1 - f7 * f11, f2 - f8 * f11, f3 + f9 * f11, f4 + f11 * f10, f3, f4);
      }
      else
      {
        path.lineTo(f3, f4);
      }
      Double.isNaN(d3);
      d1 += d3;
      i += 1;
      f2 = f4;
      f1 = f3;
    }
    localObject = (PointF)positionAnimation.getValue();
    path.offset(x, y);
    path.close();
  }
  
  private void createStarPath()
  {
    float f1 = ((Float)pointsAnimation.getValue()).floatValue();
    Object localObject = rotationAnimation;
    if (localObject == null) {
      d1 = 0.0D;
    } else {
      d1 = ((Float)((BaseKeyframeAnimation)localObject).getValue()).floatValue();
    }
    double d2 = Math.toRadians(d1 - 90.0D);
    double d1 = d2;
    double d3 = f1;
    Double.isNaN(d3);
    float f20 = (float)(6.283185307179586D / d3);
    float f13 = f20 / 2.0F;
    float f21 = f1 - (int)f1;
    if (f21 != 0.0F)
    {
      d1 = (1.0F - f21) * f13;
      Double.isNaN(d1);
      d1 = d2 + d1;
    }
    f1 = ((Float)outerRadiusAnimation.getValue()).floatValue();
    float f4 = ((Float)innerRadiusAnimation.getValue()).floatValue();
    localObject = innerRoundednessAnimation;
    float f2;
    if (localObject != null) {
      f2 = ((Float)((BaseKeyframeAnimation)localObject).getValue()).floatValue() / 100.0F;
    } else {
      f2 = 0.0F;
    }
    localObject = outerRoundednessAnimation;
    float f3;
    if (localObject != null) {
      f3 = ((Float)((BaseKeyframeAnimation)localObject).getValue()).floatValue() / 100.0F;
    } else {
      f3 = 0.0F;
    }
    float f5;
    double d4;
    float f6;
    if (f21 != 0.0F)
    {
      f5 = (f1 - f4) * f21 + f4;
      d2 = f5;
      d4 = Math.cos(d1);
      Double.isNaN(d2);
      f7 = (float)(d2 * d4);
      d4 = Math.sin(d1);
      Double.isNaN(d2);
      f6 = (float)(d2 * d4);
      path.moveTo(f7, f6);
      d2 = f20 * f21 / 2.0F;
      Double.isNaN(d2);
      d1 += d2;
    }
    else
    {
      d2 = f1;
      d4 = Math.cos(d1);
      Double.isNaN(d2);
      f7 = (float)(d2 * d4);
      d4 = Math.sin(d1);
      Double.isNaN(d2);
      f6 = (float)(d2 * d4);
      path.moveTo(f7, f6);
      d2 = f13;
      Double.isNaN(d2);
      d1 += d2;
      f5 = 0.0F;
    }
    d2 = Math.ceil(d3) * 2.0D;
    int i = 0;
    int j = 0;
    float f8 = f7;
    float f14;
    for (float f7 = f6;; f7 = f14)
    {
      d3 = i;
      if (d3 >= d2) {
        break;
      }
      if (j != 0) {
        f6 = f1;
      } else {
        f6 = f4;
      }
      float f9;
      if ((f5 != 0.0F) && (d3 == d2 - 2.0D)) {
        f9 = f20 * f21 / 2.0F;
      } else {
        f9 = f13;
      }
      float f10 = f6;
      if (f5 != 0.0F)
      {
        f10 = f6;
        if (d3 == d2 - 1.0D) {
          f10 = f5;
        }
      }
      d4 = f10;
      double d5 = Math.cos(d1);
      Double.isNaN(d4);
      float f15 = (float)(d4 * d5);
      d5 = Math.sin(d1);
      Double.isNaN(d4);
      f14 = (float)(d4 * d5);
      if ((f2 == 0.0F) && (f3 == 0.0F))
      {
        path.lineTo(f15, f14);
      }
      else
      {
        d4 = (float)(Math.atan2(f7, f8) - 1.5707963267948966D);
        float f16 = (float)Math.cos(d4);
        float f17 = (float)Math.sin(d4);
        d4 = (float)(Math.atan2(f14, f15) - 1.5707963267948966D);
        float f18 = (float)Math.cos(d4);
        float f19 = (float)Math.sin(d4);
        if (j != 0) {
          f10 = f2;
        } else {
          f10 = f3;
        }
        if (j != 0) {
          f6 = f3;
        } else {
          f6 = f2;
        }
        if (j != 0) {
          f12 = f4;
        } else {
          f12 = f1;
        }
        if (j != 0) {
          f11 = f1;
        } else {
          f11 = f4;
        }
        f10 = f12 * f10 * 0.47829F;
        f16 *= f10;
        f17 = f10 * f17;
        f6 = f11 * f6 * 0.47829F;
        f18 *= f6;
        f19 = f6 * f19;
        float f11 = f18;
        f6 = f19;
        f10 = f16;
        float f12 = f17;
        if (f21 != 0.0F) {
          if (i == 0)
          {
            f10 = f16 * f21;
            f12 = f17 * f21;
            f11 = f18;
            f6 = f19;
          }
          else
          {
            f11 = f18;
            f6 = f19;
            f10 = f16;
            f12 = f17;
            if (d3 == d2 - 1.0D)
            {
              f11 = f18 * f21;
              f6 = f19 * f21;
              f12 = f17;
              f10 = f16;
            }
          }
        }
        path.cubicTo(f8 - f10, f7 - f12, f15 + f11, f14 + f6, f15, f14);
      }
      d3 = f9;
      Double.isNaN(d3);
      d1 += d3;
      j ^= 0x1;
      i += 1;
      f8 = f15;
    }
    localObject = (PointF)positionAnimation.getValue();
    path.offset(x, y);
    path.close();
  }
  
  private void invalidate()
  {
    isPathValid = false;
    lottieDrawable.invalidateSelf();
  }
  
  public void addValueCallback(Object paramObject, LottieValueCallback paramLottieValueCallback)
  {
    if (paramObject == LottieProperty.POLYSTAR_POINTS)
    {
      pointsAnimation.setValueCallback(paramLottieValueCallback);
      return;
    }
    if (paramObject == LottieProperty.POLYSTAR_ROTATION)
    {
      rotationAnimation.setValueCallback(paramLottieValueCallback);
      return;
    }
    if (paramObject == LottieProperty.POSITION)
    {
      positionAnimation.setValueCallback(paramLottieValueCallback);
      return;
    }
    BaseKeyframeAnimation localBaseKeyframeAnimation;
    if (paramObject == LottieProperty.POLYSTAR_INNER_RADIUS)
    {
      localBaseKeyframeAnimation = innerRadiusAnimation;
      if (localBaseKeyframeAnimation != null)
      {
        localBaseKeyframeAnimation.setValueCallback(paramLottieValueCallback);
        return;
      }
    }
    if (paramObject == LottieProperty.POLYSTAR_OUTER_RADIUS)
    {
      outerRadiusAnimation.setValueCallback(paramLottieValueCallback);
      return;
    }
    if (paramObject == LottieProperty.POLYSTAR_INNER_ROUNDEDNESS)
    {
      localBaseKeyframeAnimation = innerRoundednessAnimation;
      if (localBaseKeyframeAnimation != null)
      {
        localBaseKeyframeAnimation.setValueCallback(paramLottieValueCallback);
        return;
      }
    }
    if (paramObject == LottieProperty.POLYSTAR_OUTER_ROUNDEDNESS) {
      outerRoundednessAnimation.setValueCallback(paramLottieValueCallback);
    }
  }
  
  public String getName()
  {
    return name;
  }
  
  public Path getPath()
  {
    if (isPathValid) {
      return path;
    }
    path.reset();
    if (hidden)
    {
      isPathValid = true;
      return path;
    }
    int i = 1.$SwitchMap$com$airbnb$lottie$model$content$PolystarShape$Type[type.ordinal()];
    if (i != 1)
    {
      if (i == 2) {
        createPolygonPath();
      }
    }
    else {
      createStarPath();
    }
    path.close();
    trimPaths.apply(path);
    isPathValid = true;
    return path;
  }
  
  public void onValueChanged()
  {
    invalidate();
  }
  
  public void resolveKeyPath(KeyPath paramKeyPath1, int paramInt, List paramList, KeyPath paramKeyPath2)
  {
    MiscUtils.resolveKeyPath(paramKeyPath1, paramInt, paramList, paramKeyPath2, this);
  }
  
  public void setContents(List paramList1, List paramList2)
  {
    int i = 0;
    while (i < paramList1.size())
    {
      paramList2 = (Content)paramList1.get(i);
      if ((paramList2 instanceof TrimPathContent))
      {
        paramList2 = (TrimPathContent)paramList2;
        if (paramList2.getType() == ShapeTrimPath.Type.SIMULTANEOUSLY)
        {
          trimPaths.addTrimPath(paramList2);
          paramList2.addListener(this);
        }
      }
      i += 1;
    }
  }
}