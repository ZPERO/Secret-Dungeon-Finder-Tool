package com.airbnb.lottie.animation.content;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.Way;
import com.airbnb.lottie.animation.LPaint;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation.AnimationListener;
import com.airbnb.lottie.animation.keyframe.FloatKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.IntegerKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.ValueCallbackKeyframeAnimation;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.animatable.AnimatableIntegerValue;
import com.airbnb.lottie.model.content.ShapeTrimPath.Type;
import com.airbnb.lottie.model.layer.BaseLayer;
import com.airbnb.lottie.utils.MiscUtils;
import com.airbnb.lottie.utils.Utils;
import com.airbnb.lottie.value.LottieValueCallback;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseStrokeContent
  implements BaseKeyframeAnimation.AnimationListener, KeyPathElementContent, DrawingContent
{
  private BaseKeyframeAnimation<ColorFilter, ColorFilter> colorFilterAnimation;
  private final List<BaseKeyframeAnimation<?, Float>> dashPatternAnimations;
  private final BaseKeyframeAnimation<?, Float> dashPatternOffsetAnimation;
  private final float[] dashPatternValues;
  private final PathMeasure fileList = new PathMeasure();
  protected final BaseLayer layer;
  private final LottieDrawable lottieDrawable;
  private final BaseKeyframeAnimation<?, Integer> opacityAnimation;
  final Paint paint = new LPaint(1);
  private final Path path = new Path();
  private final List<PathGroup> pathGroups = new ArrayList();
  private final RectF rect = new RectF();
  private final Path trimPathPath = new Path();
  private final BaseKeyframeAnimation<?, Float> widthAnimation;
  
  BaseStrokeContent(LottieDrawable paramLottieDrawable, BaseLayer paramBaseLayer, Paint.Cap paramCap, Paint.Join paramJoin, float paramFloat, AnimatableIntegerValue paramAnimatableIntegerValue, AnimatableFloatValue paramAnimatableFloatValue1, List paramList, AnimatableFloatValue paramAnimatableFloatValue2)
  {
    lottieDrawable = paramLottieDrawable;
    layer = paramBaseLayer;
    paint.setStyle(Paint.Style.STROKE);
    paint.setStrokeCap(paramCap);
    paint.setStrokeJoin(paramJoin);
    paint.setStrokeMiter(paramFloat);
    opacityAnimation = paramAnimatableIntegerValue.createAnimation();
    widthAnimation = paramAnimatableFloatValue1.createAnimation();
    if (paramAnimatableFloatValue2 == null) {
      dashPatternOffsetAnimation = null;
    } else {
      dashPatternOffsetAnimation = paramAnimatableFloatValue2.createAnimation();
    }
    dashPatternAnimations = new ArrayList(paramList.size());
    dashPatternValues = new float[paramList.size()];
    int j = 0;
    int i = 0;
    while (i < paramList.size())
    {
      dashPatternAnimations.add(((AnimatableFloatValue)paramList.get(i)).createAnimation());
      i += 1;
    }
    paramBaseLayer.addAnimation(opacityAnimation);
    paramBaseLayer.addAnimation(widthAnimation);
    i = 0;
    while (i < dashPatternAnimations.size())
    {
      paramBaseLayer.addAnimation((BaseKeyframeAnimation)dashPatternAnimations.get(i));
      i += 1;
    }
    paramLottieDrawable = dashPatternOffsetAnimation;
    if (paramLottieDrawable != null) {
      paramBaseLayer.addAnimation(paramLottieDrawable);
    }
    opacityAnimation.addUpdateListener(this);
    widthAnimation.addUpdateListener(this);
    i = j;
    while (i < paramList.size())
    {
      ((BaseKeyframeAnimation)dashPatternAnimations.get(i)).addUpdateListener(this);
      i += 1;
    }
    paramLottieDrawable = dashPatternOffsetAnimation;
    if (paramLottieDrawable != null) {
      paramLottieDrawable.addUpdateListener(this);
    }
  }
  
  private void applyDashPatternIfNeeded(Matrix paramMatrix)
  {
    Way.beginSection("StrokeContent#applyDashPattern");
    if (dashPatternAnimations.isEmpty())
    {
      Way.endSection("StrokeContent#applyDashPattern");
      return;
    }
    float f = Utils.getScale(paramMatrix);
    int i = 0;
    while (i < dashPatternAnimations.size())
    {
      dashPatternValues[i] = ((Float)((BaseKeyframeAnimation)dashPatternAnimations.get(i)).getValue()).floatValue();
      if (i % 2 == 0)
      {
        paramMatrix = dashPatternValues;
        if (paramMatrix[i] < 1.0F) {
          paramMatrix[i] = 1.0F;
        }
      }
      else
      {
        paramMatrix = dashPatternValues;
        if (paramMatrix[i] < 0.1F) {
          paramMatrix[i] = 0.1F;
        }
      }
      paramMatrix = dashPatternValues;
      paramMatrix[i] *= f;
      i += 1;
    }
    paramMatrix = dashPatternOffsetAnimation;
    if (paramMatrix == null) {
      f = 0.0F;
    } else {
      f *= ((Float)paramMatrix.getValue()).floatValue();
    }
    paint.setPathEffect(new DashPathEffect(dashPatternValues, f));
    Way.endSection("StrokeContent#applyDashPattern");
  }
  
  private void applyTrimPath(Canvas paramCanvas, PathGroup paramPathGroup, Matrix paramMatrix)
  {
    Way.beginSection("StrokeContent#applyTrimPath");
    if (trimPath == null)
    {
      Way.endSection("StrokeContent#applyTrimPath");
      return;
    }
    path.reset();
    int i = paths.size() - 1;
    while (i >= 0)
    {
      path.addPath(((PathContent)paths.get(i)).getPath(), paramMatrix);
      i -= 1;
    }
    fileList.setPath(path, false);
    for (float f1 = fileList.getLength(); fileList.nextContour(); f1 += fileList.getLength()) {}
    float f2 = ((Float)trimPath.getOffset().getValue()).floatValue() * f1 / 360.0F;
    float f5 = ((Float)trimPath.getStart().getValue()).floatValue() * f1 / 100.0F + f2;
    float f6 = ((Float)trimPath.getEnd().getValue()).floatValue() * f1 / 100.0F + f2;
    i = paths.size() - 1;
    f2 = 0.0F;
    while (i >= 0)
    {
      trimPathPath.set(((PathContent)paths.get(i)).getPath());
      trimPathPath.transform(paramMatrix);
      fileList.setPath(trimPathPath, false);
      float f7 = fileList.getLength();
      float f4 = 1.0F;
      float f3;
      if (f6 > f1)
      {
        f8 = f6 - f1;
        if ((f8 < f2 + f7) && (f2 < f8))
        {
          if (f5 > f1) {
            f3 = (f5 - f1) / f7;
          } else {
            f3 = 0.0F;
          }
          f4 = Math.min(f8 / f7, 1.0F);
          Utils.applyTrimPathIfNeeded(trimPathPath, f3, f4, 0.0F);
          paramCanvas.drawPath(trimPathPath, paint);
          break label505;
        }
      }
      float f8 = f2 + f7;
      if ((f8 >= f5) && (f2 <= f6)) {
        if ((f8 <= f6) && (f5 < f2))
        {
          paramCanvas.drawPath(trimPathPath, paint);
        }
        else
        {
          if (f5 < f2) {
            f3 = 0.0F;
          } else {
            f3 = (f5 - f2) / f7;
          }
          if (f6 <= f8) {
            f4 = (f6 - f2) / f7;
          }
          Utils.applyTrimPathIfNeeded(trimPathPath, f3, f4, 0.0F);
          paramCanvas.drawPath(trimPathPath, paint);
        }
      }
      label505:
      f2 += f7;
      i -= 1;
    }
    Way.endSection("StrokeContent#applyTrimPath");
  }
  
  public void addValueCallback(Object paramObject, LottieValueCallback paramLottieValueCallback)
  {
    if (paramObject == LottieProperty.OPACITY)
    {
      opacityAnimation.setValueCallback(paramLottieValueCallback);
      return;
    }
    if (paramObject == LottieProperty.STROKE_WIDTH)
    {
      widthAnimation.setValueCallback(paramLottieValueCallback);
      return;
    }
    if (paramObject == LottieProperty.COLOR_FILTER)
    {
      if (paramLottieValueCallback == null)
      {
        colorFilterAnimation = null;
        return;
      }
      colorFilterAnimation = new ValueCallbackKeyframeAnimation(paramLottieValueCallback);
      colorFilterAnimation.addUpdateListener(this);
      layer.addAnimation(colorFilterAnimation);
    }
  }
  
  public void draw(Canvas paramCanvas, Matrix paramMatrix, int paramInt)
  {
    Way.beginSection("StrokeContent#draw");
    if (Utils.hasZeroScaleAxis(paramMatrix))
    {
      Way.endSection("StrokeContent#draw");
      return;
    }
    paramInt = (int)(paramInt / 255.0F * ((IntegerKeyframeAnimation)opacityAnimation).getIntValue() / 100.0F * 255.0F);
    Object localObject = paint;
    int i = 0;
    ((Paint)localObject).setAlpha(MiscUtils.clamp(paramInt, 0, 255));
    paint.setStrokeWidth(((FloatKeyframeAnimation)widthAnimation).getFloatValue() * Utils.getScale(paramMatrix));
    if (paint.getStrokeWidth() <= 0.0F)
    {
      Way.endSection("StrokeContent#draw");
      return;
    }
    applyDashPatternIfNeeded(paramMatrix);
    localObject = colorFilterAnimation;
    paramInt = i;
    if (localObject != null)
    {
      paint.setColorFilter((ColorFilter)((BaseKeyframeAnimation)localObject).getValue());
      paramInt = i;
    }
    while (paramInt < pathGroups.size())
    {
      localObject = (PathGroup)pathGroups.get(paramInt);
      if (trimPath != null)
      {
        applyTrimPath(paramCanvas, (PathGroup)localObject, paramMatrix);
      }
      else
      {
        Way.beginSection("StrokeContent#buildPath");
        path.reset();
        i = paths.size() - 1;
        while (i >= 0)
        {
          path.addPath(((PathContent)paths.get(i)).getPath(), paramMatrix);
          i -= 1;
        }
        Way.endSection("StrokeContent#buildPath");
        Way.beginSection("StrokeContent#drawPath");
        paramCanvas.drawPath(path, paint);
        Way.endSection("StrokeContent#drawPath");
      }
      paramInt += 1;
    }
    Way.endSection("StrokeContent#draw");
  }
  
  public void getBounds(RectF paramRectF, Matrix paramMatrix, boolean paramBoolean)
  {
    Way.beginSection("StrokeContent#getBounds");
    path.reset();
    int i = 0;
    while (i < pathGroups.size())
    {
      PathGroup localPathGroup = (PathGroup)pathGroups.get(i);
      int j = 0;
      while (j < paths.size())
      {
        path.addPath(((PathContent)paths.get(j)).getPath(), paramMatrix);
        j += 1;
      }
      i += 1;
    }
    path.computeBounds(rect, false);
    float f2 = ((FloatKeyframeAnimation)widthAnimation).getFloatValue();
    paramMatrix = rect;
    float f1 = left;
    f2 /= 2.0F;
    paramMatrix.set(f1 - f2, rect.top - f2, rect.right + f2, rect.bottom + f2);
    paramRectF.set(rect);
    paramRectF.set(left - 1.0F, top - 1.0F, right + 1.0F, bottom + 1.0F);
    Way.endSection("StrokeContent#getBounds");
  }
  
  public void onValueChanged()
  {
    lottieDrawable.invalidateSelf();
  }
  
  public void resolveKeyPath(KeyPath paramKeyPath1, int paramInt, List paramList, KeyPath paramKeyPath2)
  {
    MiscUtils.resolveKeyPath(paramKeyPath1, paramInt, paramList, paramKeyPath2, this);
  }
  
  public void setContents(List paramList1, List paramList2)
  {
    int i = paramList1.size() - 1;
    Object localObject3;
    Object localObject1;
    for (Object localObject2 = null; i >= 0; localObject2 = localObject1)
    {
      localObject3 = (Content)paramList1.get(i);
      localObject1 = localObject2;
      if ((localObject3 instanceof TrimPathContent))
      {
        localObject3 = (TrimPathContent)localObject3;
        localObject1 = localObject2;
        if (((TrimPathContent)localObject3).getType() == ShapeTrimPath.Type.INDIVIDUALLY) {
          localObject1 = localObject3;
        }
      }
      i -= 1;
    }
    if (localObject2 != null) {
      localObject2.addListener(this);
    }
    i = paramList2.size() - 1;
    for (paramList1 = null; i >= 0; paramList1 = (List)localObject1)
    {
      localObject3 = (Content)paramList2.get(i);
      if ((localObject3 instanceof TrimPathContent))
      {
        TrimPathContent localTrimPathContent = (TrimPathContent)localObject3;
        if (localTrimPathContent.getType() == ShapeTrimPath.Type.INDIVIDUALLY)
        {
          if (paramList1 != null) {
            pathGroups.add(paramList1);
          }
          localObject1 = new PathGroup(localTrimPathContent, null);
          localTrimPathContent.addListener(this);
          break label223;
        }
      }
      localObject1 = paramList1;
      if ((localObject3 instanceof PathContent))
      {
        localObject1 = paramList1;
        if (paramList1 == null) {
          localObject1 = new PathGroup(localObject2, null);
        }
        paths.add((PathContent)localObject3);
      }
      label223:
      i -= 1;
    }
    if (paramList1 != null) {
      pathGroups.add(paramList1);
    }
  }
  
  private static final class PathGroup
  {
    private final List<PathContent> paths = new ArrayList();
    private final TrimPathContent trimPath;
    
    private PathGroup(TrimPathContent paramTrimPathContent)
    {
      trimPath = paramTrimPathContent;
    }
  }
}
