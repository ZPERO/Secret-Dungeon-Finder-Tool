package com.airbnb.lottie.animation.content;

import android.graphics.Path;
import android.graphics.PointF;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation.AnimationListener;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.model.animatable.AnimatablePointValue;
import com.airbnb.lottie.model.animatable.AnimatableValue;
import com.airbnb.lottie.model.content.CircleShape;
import com.airbnb.lottie.model.content.ShapeTrimPath.Type;
import com.airbnb.lottie.model.layer.BaseLayer;
import com.airbnb.lottie.utils.MiscUtils;
import com.airbnb.lottie.value.LottieValueCallback;
import java.util.List;

public class EllipseContent
  implements PathContent, BaseKeyframeAnimation.AnimationListener, KeyPathElementContent
{
  private static final float ELLIPSE_CONTROL_POINT_PERCENTAGE = 0.55228F;
  private final CircleShape circleShape;
  private boolean isPathValid;
  private final LottieDrawable lottieDrawable;
  private final String name;
  private final Path path = new Path();
  private final BaseKeyframeAnimation<?, PointF> positionAnimation;
  private final BaseKeyframeAnimation<?, PointF> sizeAnimation;
  private CompoundTrimPathContent trimPaths = new CompoundTrimPathContent();
  
  public EllipseContent(LottieDrawable paramLottieDrawable, BaseLayer paramBaseLayer, CircleShape paramCircleShape)
  {
    name = paramCircleShape.getName();
    lottieDrawable = paramLottieDrawable;
    sizeAnimation = paramCircleShape.getSize().createAnimation();
    positionAnimation = paramCircleShape.getPosition().createAnimation();
    circleShape = paramCircleShape;
    paramBaseLayer.addAnimation(sizeAnimation);
    paramBaseLayer.addAnimation(positionAnimation);
    sizeAnimation.addUpdateListener(this);
    positionAnimation.addUpdateListener(this);
  }
  
  private void invalidate()
  {
    isPathValid = false;
    lottieDrawable.invalidateSelf();
  }
  
  public void addValueCallback(Object paramObject, LottieValueCallback paramLottieValueCallback)
  {
    if (paramObject == LottieProperty.ELLIPSE_SIZE)
    {
      sizeAnimation.setValueCallback(paramLottieValueCallback);
      return;
    }
    if (paramObject == LottieProperty.POSITION) {
      positionAnimation.setValueCallback(paramLottieValueCallback);
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
    if (circleShape.isHidden())
    {
      isPathValid = true;
      return path;
    }
    Object localObject = (PointF)sizeAnimation.getValue();
    float f2 = x / 2.0F;
    float f1 = y / 2.0F;
    float f3 = f2 * 0.55228F;
    float f4 = 0.55228F * f1;
    path.reset();
    float f5;
    float f7;
    float f6;
    if (circleShape.isReversed())
    {
      localObject = path;
      f5 = -f1;
      ((Path)localObject).moveTo(0.0F, f5);
      localObject = path;
      f7 = 0.0F - f3;
      float f8 = -f2;
      f6 = 0.0F - f4;
      ((Path)localObject).cubicTo(f7, f5, f8, f6, f8, 0.0F);
      localObject = path;
      f4 += 0.0F;
      ((Path)localObject).cubicTo(f8, f4, f7, f1, 0.0F, f1);
      localObject = path;
      f3 += 0.0F;
      ((Path)localObject).cubicTo(f3, f1, f2, f4, f2, 0.0F);
      path.cubicTo(f2, f6, f3, f5, 0.0F, f5);
    }
    else
    {
      localObject = path;
      f5 = -f1;
      ((Path)localObject).moveTo(0.0F, f5);
      localObject = path;
      f7 = f3 + 0.0F;
      f6 = 0.0F - f4;
      ((Path)localObject).cubicTo(f7, f5, f2, f6, f2, 0.0F);
      localObject = path;
      f4 += 0.0F;
      ((Path)localObject).cubicTo(f2, f4, f7, f1, 0.0F, f1);
      localObject = path;
      f3 = 0.0F - f3;
      f2 = -f2;
      ((Path)localObject).cubicTo(f3, f1, f2, f4, f2, 0.0F);
      path.cubicTo(f2, f6, f3, f5, 0.0F, f5);
    }
    localObject = (PointF)positionAnimation.getValue();
    path.offset(x, y);
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