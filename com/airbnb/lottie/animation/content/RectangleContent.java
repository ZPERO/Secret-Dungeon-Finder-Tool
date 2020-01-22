package com.airbnb.lottie.animation.content;

import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation.AnimationListener;
import com.airbnb.lottie.animation.keyframe.FloatKeyframeAnimation;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.animatable.AnimatablePointValue;
import com.airbnb.lottie.model.animatable.AnimatableValue;
import com.airbnb.lottie.model.content.RectangleShape;
import com.airbnb.lottie.model.content.ShapeTrimPath.Type;
import com.airbnb.lottie.model.layer.BaseLayer;
import com.airbnb.lottie.utils.MiscUtils;
import com.airbnb.lottie.value.LottieValueCallback;
import java.util.List;

public class RectangleContent
  implements BaseKeyframeAnimation.AnimationListener, KeyPathElementContent, PathContent
{
  private final BaseKeyframeAnimation<?, Float> cornerRadiusAnimation;
  private final boolean hidden;
  private boolean isPathValid;
  private final LottieDrawable lottieDrawable;
  private final String name;
  private final Path path = new Path();
  private final BaseKeyframeAnimation<?, PointF> positionAnimation;
  private final RectF rect = new RectF();
  private final BaseKeyframeAnimation<?, PointF> sizeAnimation;
  private CompoundTrimPathContent trimPaths = new CompoundTrimPathContent();
  
  public RectangleContent(LottieDrawable paramLottieDrawable, BaseLayer paramBaseLayer, RectangleShape paramRectangleShape)
  {
    name = paramRectangleShape.getName();
    hidden = paramRectangleShape.isHidden();
    lottieDrawable = paramLottieDrawable;
    positionAnimation = paramRectangleShape.getPosition().createAnimation();
    sizeAnimation = paramRectangleShape.getSize().createAnimation();
    cornerRadiusAnimation = paramRectangleShape.getCornerRadius().createAnimation();
    paramBaseLayer.addAnimation(positionAnimation);
    paramBaseLayer.addAnimation(sizeAnimation);
    paramBaseLayer.addAnimation(cornerRadiusAnimation);
    positionAnimation.addUpdateListener(this);
    sizeAnimation.addUpdateListener(this);
    cornerRadiusAnimation.addUpdateListener(this);
  }
  
  private void invalidate()
  {
    isPathValid = false;
    lottieDrawable.invalidateSelf();
  }
  
  public void addValueCallback(Object paramObject, LottieValueCallback paramLottieValueCallback)
  {
    if (paramObject == LottieProperty.RECTANGLE_SIZE)
    {
      sizeAnimation.setValueCallback(paramLottieValueCallback);
      return;
    }
    if (paramObject == LottieProperty.POSITION)
    {
      positionAnimation.setValueCallback(paramLottieValueCallback);
      return;
    }
    if (paramObject == LottieProperty.CORNER_RADIUS) {
      cornerRadiusAnimation.setValueCallback(paramLottieValueCallback);
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
    Object localObject = (PointF)sizeAnimation.getValue();
    float f4 = x / 2.0F;
    float f5 = y / 2.0F;
    localObject = cornerRadiusAnimation;
    float f1;
    if (localObject == null) {
      f1 = 0.0F;
    } else {
      f1 = ((FloatKeyframeAnimation)localObject).getFloatValue();
    }
    float f3 = Math.min(f4, f5);
    float f2 = f1;
    if (f1 > f3) {
      f2 = f3;
    }
    localObject = (PointF)positionAnimation.getValue();
    path.moveTo(x + f4, y - f5 + f2);
    path.lineTo(x + f4, y + f5 - f2);
    RectF localRectF;
    if (f2 > 0.0F)
    {
      localRectF = rect;
      f1 = x;
      f3 = f2 * 2.0F;
      localRectF.set(f1 + f4 - f3, y + f5 - f3, x + f4, y + f5);
      path.arcTo(rect, 0.0F, 90.0F, false);
    }
    path.lineTo(x - f4 + f2, y + f5);
    float f6;
    if (f2 > 0.0F)
    {
      localRectF = rect;
      f1 = x;
      f3 = y;
      f6 = f2 * 2.0F;
      localRectF.set(f1 - f4, f3 + f5 - f6, x - f4 + f6, y + f5);
      path.arcTo(rect, 90.0F, 90.0F, false);
    }
    path.lineTo(x - f4, y - f5 + f2);
    if (f2 > 0.0F)
    {
      localRectF = rect;
      f1 = x;
      f3 = y;
      f6 = x;
      float f7 = f2 * 2.0F;
      localRectF.set(f1 - f4, f3 - f5, f6 - f4 + f7, y - f5 + f7);
      path.arcTo(rect, 180.0F, 90.0F, false);
    }
    path.lineTo(x + f4 - f2, y - f5);
    if (f2 > 0.0F)
    {
      localRectF = rect;
      f1 = x;
      f2 *= 2.0F;
      localRectF.set(f1 + f4 - f2, y - f5, x + f4, y - f5 + f2);
      path.arcTo(rect, 270.0F, 90.0F, false);
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
