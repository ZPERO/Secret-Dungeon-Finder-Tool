package com.airbnb.lottie.animation.content;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import androidx.collection.LongSparseArray;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.Way;
import com.airbnb.lottie.animation.LPaint;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation.AnimationListener;
import com.airbnb.lottie.animation.keyframe.ValueCallbackKeyframeAnimation;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.model.animatable.AnimatableGradientColorValue;
import com.airbnb.lottie.model.animatable.AnimatableIntegerValue;
import com.airbnb.lottie.model.animatable.AnimatablePointValue;
import com.airbnb.lottie.model.content.GradientColor;
import com.airbnb.lottie.model.content.GradientFill;
import com.airbnb.lottie.model.content.GradientType;
import com.airbnb.lottie.model.layer.BaseLayer;
import com.airbnb.lottie.utils.MiscUtils;
import com.airbnb.lottie.value.LottieValueCallback;
import java.util.ArrayList;
import java.util.List;

public class GradientFillContent
  implements DrawingContent, BaseKeyframeAnimation.AnimationListener, KeyPathElementContent
{
  private static final int CACHE_STEPS_MS = 32;
  private final RectF boundsRect = new RectF();
  private final int cacheSteps;
  private final BaseKeyframeAnimation<GradientColor, GradientColor> colorAnimation;
  private ValueCallbackKeyframeAnimation colorCallbackAnimation;
  private BaseKeyframeAnimation<ColorFilter, ColorFilter> colorFilterAnimation;
  private final BaseKeyframeAnimation<PointF, PointF> endPointAnimation;
  private final boolean hidden;
  private final BaseLayer layer;
  private final LongSparseArray<LinearGradient> linearGradientCache = new LongSparseArray();
  private final LottieDrawable lottieDrawable;
  private final String name;
  private final BaseKeyframeAnimation<Integer, Integer> opacityAnimation;
  private final Paint paint = new LPaint(1);
  private final Path path = new Path();
  private final List<PathContent> paths = new ArrayList();
  private final LongSparseArray<RadialGradient> radialGradientCache = new LongSparseArray();
  private final BaseKeyframeAnimation<PointF, PointF> startPointAnimation;
  private final GradientType type;
  
  public GradientFillContent(LottieDrawable paramLottieDrawable, BaseLayer paramBaseLayer, GradientFill paramGradientFill)
  {
    layer = paramBaseLayer;
    name = paramGradientFill.getName();
    hidden = paramGradientFill.isHidden();
    lottieDrawable = paramLottieDrawable;
    type = paramGradientFill.getGradientType();
    path.setFillType(paramGradientFill.getFillType());
    cacheSteps = ((int)(paramLottieDrawable.getComposition().getDuration() / 32.0F));
    colorAnimation = paramGradientFill.getGradientColor().createAnimation();
    colorAnimation.addUpdateListener(this);
    paramBaseLayer.addAnimation(colorAnimation);
    opacityAnimation = paramGradientFill.getOpacity().createAnimation();
    opacityAnimation.addUpdateListener(this);
    paramBaseLayer.addAnimation(opacityAnimation);
    startPointAnimation = paramGradientFill.getStartPoint().createAnimation();
    startPointAnimation.addUpdateListener(this);
    paramBaseLayer.addAnimation(startPointAnimation);
    endPointAnimation = paramGradientFill.getEndPoint().createAnimation();
    endPointAnimation.addUpdateListener(this);
    paramBaseLayer.addAnimation(endPointAnimation);
  }
  
  private int[] applyDynamicColorsIfNeeded(int[] paramArrayOfInt)
  {
    Object localObject = colorCallbackAnimation;
    int[] arrayOfInt = paramArrayOfInt;
    if (localObject != null)
    {
      localObject = (Integer[])((ValueCallbackKeyframeAnimation)localObject).getValue();
      int k = paramArrayOfInt.length;
      int m = localObject.length;
      int j = 0;
      int i = 0;
      if (k == m) {
        for (;;)
        {
          arrayOfInt = paramArrayOfInt;
          if (i >= paramArrayOfInt.length) {
            break;
          }
          paramArrayOfInt[i] = localObject[i].intValue();
          i += 1;
        }
      }
      paramArrayOfInt = new int[localObject.length];
      i = j;
      for (;;)
      {
        arrayOfInt = paramArrayOfInt;
        if (i >= localObject.length) {
          break;
        }
        paramArrayOfInt[i] = localObject[i].intValue();
        i += 1;
      }
    }
    return arrayOfInt;
  }
  
  private int getGradientHash()
  {
    int i = Math.round(startPointAnimation.getProgress() * cacheSteps);
    int m = Math.round(endPointAnimation.getProgress() * cacheSteps);
    int k = Math.round(colorAnimation.getProgress() * cacheSteps);
    if (i != 0) {
      i = 527 * i;
    } else {
      i = 17;
    }
    int j = i;
    if (m != 0) {
      j = i * 31 * m;
    }
    if (k != 0) {
      return j * 31 * k;
    }
    return j;
  }
  
  private LinearGradient getLinearGradient()
  {
    int i = getGradientHash();
    Object localObject1 = linearGradientCache;
    long l = i;
    localObject1 = (LinearGradient)((LongSparseArray)localObject1).get(l);
    if (localObject1 != null) {
      return localObject1;
    }
    localObject1 = (PointF)startPointAnimation.getValue();
    PointF localPointF = (PointF)endPointAnimation.getValue();
    Object localObject2 = (GradientColor)colorAnimation.getValue();
    int[] arrayOfInt = applyDynamicColorsIfNeeded(((GradientColor)localObject2).getColors());
    localObject2 = ((GradientColor)localObject2).getPositions();
    localObject1 = new LinearGradient(x, y, x, y, arrayOfInt, (float[])localObject2, Shader.TileMode.CLAMP);
    linearGradientCache.put(l, localObject1);
    return localObject1;
  }
  
  private RadialGradient getRadialGradient()
  {
    int i = getGradientHash();
    Object localObject1 = radialGradientCache;
    long l = i;
    localObject1 = (RadialGradient)((LongSparseArray)localObject1).get(l);
    if (localObject1 != null) {
      return localObject1;
    }
    localObject1 = (PointF)startPointAnimation.getValue();
    PointF localPointF = (PointF)endPointAnimation.getValue();
    Object localObject2 = (GradientColor)colorAnimation.getValue();
    int[] arrayOfInt = applyDynamicColorsIfNeeded(((GradientColor)localObject2).getColors());
    localObject2 = ((GradientColor)localObject2).getPositions();
    float f3 = x;
    float f4 = y;
    float f1 = x;
    float f2 = y;
    f2 = (float)Math.hypot(f1 - f3, f2 - f4);
    f1 = f2;
    if (f2 <= 0.0F) {
      f1 = 0.001F;
    }
    localObject1 = new RadialGradient(f3, f4, f1, arrayOfInt, (float[])localObject2, Shader.TileMode.CLAMP);
    radialGradientCache.put(l, localObject1);
    return localObject1;
  }
  
  public void addValueCallback(Object paramObject, LottieValueCallback paramLottieValueCallback)
  {
    if (paramObject == LottieProperty.OPACITY)
    {
      opacityAnimation.setValueCallback(paramLottieValueCallback);
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
      return;
    }
    if (paramObject == LottieProperty.GRADIENT_COLOR)
    {
      if (paramLottieValueCallback == null)
      {
        paramObject = colorCallbackAnimation;
        if (paramObject != null) {
          layer.removeAnimation(paramObject);
        }
        colorCallbackAnimation = null;
        return;
      }
      colorCallbackAnimation = new ValueCallbackKeyframeAnimation(paramLottieValueCallback);
      colorCallbackAnimation.addUpdateListener(this);
      layer.addAnimation(colorCallbackAnimation);
    }
  }
  
  public void draw(Canvas paramCanvas, Matrix paramMatrix, int paramInt)
  {
    if (hidden) {
      return;
    }
    Way.beginSection("GradientFillContent#draw");
    path.reset();
    int i = 0;
    while (i < paths.size())
    {
      path.addPath(((PathContent)paths.get(i)).getPath(), paramMatrix);
      i += 1;
    }
    path.computeBounds(boundsRect, false);
    Object localObject;
    if (type == GradientType.LINEAR) {
      localObject = getLinearGradient();
    } else {
      localObject = getRadialGradient();
    }
    ((Shader)localObject).setLocalMatrix(paramMatrix);
    paint.setShader((Shader)localObject);
    paramMatrix = colorFilterAnimation;
    if (paramMatrix != null) {
      paint.setColorFilter((ColorFilter)paramMatrix.getValue());
    }
    paramInt = (int)(paramInt / 255.0F * ((Integer)opacityAnimation.getValue()).intValue() / 100.0F * 255.0F);
    paint.setAlpha(MiscUtils.clamp(paramInt, 0, 255));
    paramCanvas.drawPath(path, paint);
    Way.endSection("GradientFillContent#draw");
  }
  
  public void getBounds(RectF paramRectF, Matrix paramMatrix, boolean paramBoolean)
  {
    path.reset();
    int i = 0;
    while (i < paths.size())
    {
      path.addPath(((PathContent)paths.get(i)).getPath(), paramMatrix);
      i += 1;
    }
    path.computeBounds(paramRectF, false);
    paramRectF.set(left - 1.0F, top - 1.0F, right + 1.0F, bottom + 1.0F);
  }
  
  public String getName()
  {
    return name;
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
    int i = 0;
    while (i < paramList2.size())
    {
      paramList1 = (Content)paramList2.get(i);
      if ((paramList1 instanceof PathContent)) {
        paths.add((PathContent)paramList1);
      }
      i += 1;
    }
  }
}
