package com.airbnb.lottie.animation.keyframe;

import android.graphics.Matrix;
import android.graphics.PointF;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.animatable.AnimatableIntegerValue;
import com.airbnb.lottie.model.animatable.AnimatablePathValue;
import com.airbnb.lottie.model.animatable.AnimatableScaleValue;
import com.airbnb.lottie.model.animatable.AnimatableTransform;
import com.airbnb.lottie.model.animatable.AnimatableValue;
import com.airbnb.lottie.model.layer.BaseLayer;
import com.airbnb.lottie.value.Keyframe;
import com.airbnb.lottie.value.LottieValueCallback;
import com.airbnb.lottie.value.ScaleXY;
import java.util.Collections;

public class TransformKeyframeAnimation
{
  private BaseKeyframeAnimation<PointF, PointF> anchorPoint;
  private BaseKeyframeAnimation<?, Float> endOpacity;
  private final Matrix matrix = new Matrix();
  private BaseKeyframeAnimation<Integer, Integer> opacity;
  private BaseKeyframeAnimation<?, PointF> position;
  private BaseKeyframeAnimation<Float, Float> rotation;
  private BaseKeyframeAnimation<ScaleXY, ScaleXY> scale;
  private FloatKeyframeAnimation skew;
  private FloatKeyframeAnimation skewAngle;
  private final Matrix skewMatrix1;
  private final Matrix skewMatrix2;
  private final Matrix skewMatrix3;
  private final float[] skewValues;
  private BaseKeyframeAnimation<?, Float> startOpacity;
  
  public TransformKeyframeAnimation(AnimatableTransform paramAnimatableTransform)
  {
    Object localObject;
    if (paramAnimatableTransform.getAnchorPoint() == null) {
      localObject = null;
    } else {
      localObject = paramAnimatableTransform.getAnchorPoint().createAnimation();
    }
    anchorPoint = ((BaseKeyframeAnimation)localObject);
    if (paramAnimatableTransform.getPosition() == null) {
      localObject = null;
    } else {
      localObject = paramAnimatableTransform.getPosition().createAnimation();
    }
    position = ((BaseKeyframeAnimation)localObject);
    if (paramAnimatableTransform.getScale() == null) {
      localObject = null;
    } else {
      localObject = paramAnimatableTransform.getScale().createAnimation();
    }
    scale = ((BaseKeyframeAnimation)localObject);
    if (paramAnimatableTransform.getRotation() == null) {
      localObject = null;
    } else {
      localObject = paramAnimatableTransform.getRotation().createAnimation();
    }
    rotation = ((BaseKeyframeAnimation)localObject);
    if (paramAnimatableTransform.getSkew() == null) {
      localObject = null;
    } else {
      localObject = (FloatKeyframeAnimation)paramAnimatableTransform.getSkew().createAnimation();
    }
    skew = ((FloatKeyframeAnimation)localObject);
    if (skew != null)
    {
      skewMatrix1 = new Matrix();
      skewMatrix2 = new Matrix();
      skewMatrix3 = new Matrix();
      skewValues = new float[9];
    }
    else
    {
      skewMatrix1 = null;
      skewMatrix2 = null;
      skewMatrix3 = null;
      skewValues = null;
    }
    if (paramAnimatableTransform.getSkewAngle() == null) {
      localObject = null;
    } else {
      localObject = (FloatKeyframeAnimation)paramAnimatableTransform.getSkewAngle().createAnimation();
    }
    skewAngle = ((FloatKeyframeAnimation)localObject);
    if (paramAnimatableTransform.getOpacity() != null) {
      opacity = paramAnimatableTransform.getOpacity().createAnimation();
    }
    if (paramAnimatableTransform.getStartOpacity() != null) {
      startOpacity = paramAnimatableTransform.getStartOpacity().createAnimation();
    } else {
      startOpacity = null;
    }
    if (paramAnimatableTransform.getEndOpacity() != null)
    {
      endOpacity = paramAnimatableTransform.getEndOpacity().createAnimation();
      return;
    }
    endOpacity = null;
  }
  
  private void clearSkewValues()
  {
    int i = 0;
    while (i < 9)
    {
      skewValues[i] = 0.0F;
      i += 1;
    }
  }
  
  public void addAnimationsToLayer(BaseLayer paramBaseLayer)
  {
    paramBaseLayer.addAnimation(opacity);
    paramBaseLayer.addAnimation(startOpacity);
    paramBaseLayer.addAnimation(endOpacity);
    paramBaseLayer.addAnimation(anchorPoint);
    paramBaseLayer.addAnimation(position);
    paramBaseLayer.addAnimation(scale);
    paramBaseLayer.addAnimation(rotation);
    paramBaseLayer.addAnimation(skew);
    paramBaseLayer.addAnimation(skewAngle);
  }
  
  public void addListener(BaseKeyframeAnimation.AnimationListener paramAnimationListener)
  {
    Object localObject = opacity;
    if (localObject != null) {
      ((BaseKeyframeAnimation)localObject).addUpdateListener(paramAnimationListener);
    }
    localObject = startOpacity;
    if (localObject != null) {
      ((BaseKeyframeAnimation)localObject).addUpdateListener(paramAnimationListener);
    }
    localObject = endOpacity;
    if (localObject != null) {
      ((BaseKeyframeAnimation)localObject).addUpdateListener(paramAnimationListener);
    }
    localObject = anchorPoint;
    if (localObject != null) {
      ((BaseKeyframeAnimation)localObject).addUpdateListener(paramAnimationListener);
    }
    localObject = position;
    if (localObject != null) {
      ((BaseKeyframeAnimation)localObject).addUpdateListener(paramAnimationListener);
    }
    localObject = scale;
    if (localObject != null) {
      ((BaseKeyframeAnimation)localObject).addUpdateListener(paramAnimationListener);
    }
    localObject = rotation;
    if (localObject != null) {
      ((BaseKeyframeAnimation)localObject).addUpdateListener(paramAnimationListener);
    }
    localObject = skew;
    if (localObject != null) {
      ((BaseKeyframeAnimation)localObject).addUpdateListener(paramAnimationListener);
    }
    localObject = skewAngle;
    if (localObject != null) {
      ((BaseKeyframeAnimation)localObject).addUpdateListener(paramAnimationListener);
    }
  }
  
  public boolean applyValueCallback(Object paramObject, LottieValueCallback paramLottieValueCallback)
  {
    if (paramObject == LottieProperty.TRANSFORM_ANCHOR_POINT)
    {
      paramObject = anchorPoint;
      if (paramObject == null) {
        anchorPoint = new ValueCallbackKeyframeAnimation(paramLottieValueCallback, new PointF());
      } else {
        paramObject.setValueCallback(paramLottieValueCallback);
      }
    }
    else if (paramObject == LottieProperty.TRANSFORM_POSITION)
    {
      paramObject = position;
      if (paramObject == null) {
        position = new ValueCallbackKeyframeAnimation(paramLottieValueCallback, new PointF());
      } else {
        paramObject.setValueCallback(paramLottieValueCallback);
      }
    }
    else if (paramObject == LottieProperty.TRANSFORM_SCALE)
    {
      paramObject = scale;
      if (paramObject == null) {
        scale = new ValueCallbackKeyframeAnimation(paramLottieValueCallback, new ScaleXY());
      } else {
        paramObject.setValueCallback(paramLottieValueCallback);
      }
    }
    else if (paramObject == LottieProperty.TRANSFORM_ROTATION)
    {
      paramObject = rotation;
      if (paramObject == null) {
        rotation = new ValueCallbackKeyframeAnimation(paramLottieValueCallback, Float.valueOf(0.0F));
      } else {
        paramObject.setValueCallback(paramLottieValueCallback);
      }
    }
    else if (paramObject == LottieProperty.TRANSFORM_OPACITY)
    {
      paramObject = opacity;
      if (paramObject == null) {
        opacity = new ValueCallbackKeyframeAnimation(paramLottieValueCallback, Integer.valueOf(100));
      } else {
        paramObject.setValueCallback(paramLottieValueCallback);
      }
    }
    else
    {
      Object localObject;
      if (paramObject == LottieProperty.TRANSFORM_START_OPACITY)
      {
        localObject = startOpacity;
        if (localObject != null)
        {
          if (localObject == null)
          {
            startOpacity = new ValueCallbackKeyframeAnimation(paramLottieValueCallback, Integer.valueOf(100));
            break label430;
          }
          ((BaseKeyframeAnimation)localObject).setValueCallback(paramLottieValueCallback);
          break label430;
        }
      }
      if (paramObject == LottieProperty.TRANSFORM_END_OPACITY)
      {
        localObject = endOpacity;
        if (localObject != null)
        {
          if (localObject == null)
          {
            endOpacity = new ValueCallbackKeyframeAnimation(paramLottieValueCallback, Integer.valueOf(100));
            break label430;
          }
          ((BaseKeyframeAnimation)localObject).setValueCallback(paramLottieValueCallback);
          break label430;
        }
      }
      if (paramObject == LottieProperty.TRANSFORM_SKEW)
      {
        localObject = skew;
        if (localObject != null)
        {
          if (localObject == null) {
            skew = new FloatKeyframeAnimation(Collections.singletonList(new Keyframe(Float.valueOf(0.0F))));
          }
          skew.setValueCallback(paramLottieValueCallback);
          break label430;
        }
      }
      if (paramObject != LottieProperty.TRANSFORM_SKEW_ANGLE) {
        break label432;
      }
      paramObject = skewAngle;
      if (paramObject == null) {
        break label432;
      }
      if (paramObject == null) {
        skewAngle = new FloatKeyframeAnimation(Collections.singletonList(new Keyframe(Float.valueOf(0.0F))));
      }
      skewAngle.setValueCallback(paramLottieValueCallback);
    }
    label430:
    return true;
    label432:
    return false;
  }
  
  public BaseKeyframeAnimation getEndOpacity()
  {
    return endOpacity;
  }
  
  public Matrix getMatrix()
  {
    matrix.reset();
    Object localObject = position;
    if (localObject != null)
    {
      localObject = (PointF)((BaseKeyframeAnimation)localObject).getValue();
      if ((x != 0.0F) || (y != 0.0F)) {
        matrix.preTranslate(x, y);
      }
    }
    localObject = rotation;
    float f1;
    if (localObject != null)
    {
      if ((localObject instanceof ValueCallbackKeyframeAnimation)) {
        f1 = ((Float)((BaseKeyframeAnimation)localObject).getValue()).floatValue();
      } else {
        f1 = ((FloatKeyframeAnimation)localObject).getFloatValue();
      }
      if (f1 != 0.0F) {
        matrix.preRotate(f1);
      }
    }
    if (skew != null)
    {
      localObject = skewAngle;
      if (localObject == null) {
        f1 = 0.0F;
      } else {
        f1 = (float)Math.cos(Math.toRadians(-((FloatKeyframeAnimation)localObject).getFloatValue() + 90.0F));
      }
      localObject = skewAngle;
      float f2;
      if (localObject == null) {
        f2 = 1.0F;
      } else {
        f2 = (float)Math.sin(Math.toRadians(-((FloatKeyframeAnimation)localObject).getFloatValue() + 90.0F));
      }
      float f3 = (float)Math.tan(Math.toRadians(skew.getFloatValue()));
      clearSkewValues();
      localObject = skewValues;
      localObject[0] = f1;
      localObject[1] = f2;
      float f4 = -f2;
      localObject[3] = f4;
      localObject[4] = f1;
      localObject[8] = 1.0F;
      skewMatrix1.setValues((float[])localObject);
      clearSkewValues();
      localObject = skewValues;
      localObject[0] = 1.0F;
      localObject[3] = f3;
      localObject[4] = 1.0F;
      localObject[8] = 1.0F;
      skewMatrix2.setValues((float[])localObject);
      clearSkewValues();
      localObject = skewValues;
      localObject[0] = f1;
      localObject[1] = f4;
      localObject[3] = f2;
      localObject[4] = f1;
      localObject[8] = 1.0F;
      skewMatrix3.setValues((float[])localObject);
      skewMatrix2.preConcat(skewMatrix1);
      skewMatrix3.preConcat(skewMatrix2);
      matrix.preConcat(skewMatrix3);
    }
    localObject = scale;
    if (localObject != null)
    {
      localObject = (ScaleXY)((BaseKeyframeAnimation)localObject).getValue();
      if ((((ScaleXY)localObject).getScaleX() != 1.0F) || (((ScaleXY)localObject).getScaleY() != 1.0F)) {
        matrix.preScale(((ScaleXY)localObject).getScaleX(), ((ScaleXY)localObject).getScaleY());
      }
    }
    localObject = anchorPoint;
    if (localObject != null)
    {
      localObject = (PointF)((BaseKeyframeAnimation)localObject).getValue();
      if ((x != 0.0F) || (y != 0.0F)) {
        matrix.preTranslate(-x, -y);
      }
    }
    return matrix;
  }
  
  public Matrix getMatrixForRepeater(float paramFloat)
  {
    Object localObject1 = position;
    Object localObject3 = null;
    if (localObject1 == null) {
      localObject1 = null;
    } else {
      localObject1 = (PointF)((BaseKeyframeAnimation)localObject1).getValue();
    }
    Object localObject2 = scale;
    if (localObject2 == null) {
      localObject2 = null;
    } else {
      localObject2 = (ScaleXY)((BaseKeyframeAnimation)localObject2).getValue();
    }
    matrix.reset();
    if (localObject1 != null) {
      matrix.preTranslate(x * paramFloat, y * paramFloat);
    }
    if (localObject2 != null)
    {
      localObject1 = matrix;
      double d1 = ((ScaleXY)localObject2).getScaleX();
      double d2 = paramFloat;
      ((Matrix)localObject1).preScale((float)Math.pow(d1, d2), (float)Math.pow(((ScaleXY)localObject2).getScaleY(), d2));
    }
    localObject1 = rotation;
    localObject2 = this;
    if (localObject1 != null)
    {
      float f3 = ((Float)((BaseKeyframeAnimation)localObject1).getValue()).floatValue();
      localObject1 = anchorPoint;
      if (localObject1 == null) {
        localObject1 = localObject3;
      } else {
        localObject1 = (PointF)((BaseKeyframeAnimation)localObject1).getValue();
      }
      localObject2 = matrix;
      float f2 = 0.0F;
      float f1;
      if (localObject1 == null) {
        f1 = 0.0F;
      } else {
        f1 = x;
      }
      if (localObject1 != null) {
        f2 = y;
      }
      ((Matrix)localObject2).preRotate(f3 * paramFloat, f1, f2);
    }
    return matrix;
  }
  
  public BaseKeyframeAnimation getOpacity()
  {
    return opacity;
  }
  
  public BaseKeyframeAnimation getStartOpacity()
  {
    return startOpacity;
  }
  
  public void setProgress(float paramFloat)
  {
    Object localObject = opacity;
    if (localObject != null) {
      ((BaseKeyframeAnimation)localObject).setProgress(paramFloat);
    }
    localObject = startOpacity;
    if (localObject != null) {
      ((BaseKeyframeAnimation)localObject).setProgress(paramFloat);
    }
    localObject = endOpacity;
    if (localObject != null) {
      ((BaseKeyframeAnimation)localObject).setProgress(paramFloat);
    }
    localObject = anchorPoint;
    if (localObject != null) {
      ((BaseKeyframeAnimation)localObject).setProgress(paramFloat);
    }
    localObject = position;
    if (localObject != null) {
      ((BaseKeyframeAnimation)localObject).setProgress(paramFloat);
    }
    localObject = scale;
    if (localObject != null) {
      ((BaseKeyframeAnimation)localObject).setProgress(paramFloat);
    }
    localObject = rotation;
    if (localObject != null) {
      ((BaseKeyframeAnimation)localObject).setProgress(paramFloat);
    }
    localObject = skew;
    if (localObject != null) {
      ((BaseKeyframeAnimation)localObject).setProgress(paramFloat);
    }
    localObject = skewAngle;
    if (localObject != null) {
      ((BaseKeyframeAnimation)localObject).setProgress(paramFloat);
    }
  }
}
