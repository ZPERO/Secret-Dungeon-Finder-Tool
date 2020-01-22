package com.airbnb.lottie.model.layer;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import androidx.collection.LongSparseArray;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.Way;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.ValueCallbackKeyframeAnimation;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.utils.Utils;
import com.airbnb.lottie.value.LottieValueCallback;
import java.util.ArrayList;
import java.util.List;

public class CompositionLayer
  extends BaseLayer
{
  private Boolean hasMasks;
  private Boolean hasMatte;
  private Paint layerPaint = new Paint();
  private final List<BaseLayer> layers = new ArrayList();
  private final RectF newClipRect = new RectF();
  private final RectF rect = new RectF();
  private BaseKeyframeAnimation<Float, Float> timeRemapping;
  
  public CompositionLayer(LottieDrawable paramLottieDrawable, Layer paramLayer, List paramList, LottieComposition paramLottieComposition)
  {
    super(paramLottieDrawable, paramLayer);
    paramLayer = paramLayer.getTimeRemapping();
    if (paramLayer != null)
    {
      timeRemapping = paramLayer.createAnimation();
      addAnimation(timeRemapping);
      timeRemapping.addUpdateListener(this);
    }
    else
    {
      timeRemapping = null;
    }
    LongSparseArray localLongSparseArray = new LongSparseArray(paramLottieComposition.getLayers().size());
    int i = paramList.size() - 1;
    paramLayer = null;
    int j;
    for (;;)
    {
      j = 0;
      if (i < 0) {
        break;
      }
      Layer localLayer = (Layer)paramList.get(i);
      BaseLayer localBaseLayer = BaseLayer.forModel(localLayer, paramLottieDrawable, paramLottieComposition);
      if (localBaseLayer != null)
      {
        localLongSparseArray.put(localBaseLayer.getLayerModel().getId(), localBaseLayer);
        if (paramLayer != null)
        {
          paramLayer.setMatteLayer(localBaseLayer);
          paramLayer = null;
        }
        else
        {
          layers.add(0, localBaseLayer);
          j = 1.$SwitchMap$com$airbnb$lottie$model$layer$Layer$MatteType[localLayer.getMatteType().ordinal()];
          if ((j == 1) || (j == 2)) {
            paramLayer = localBaseLayer;
          }
        }
      }
      i -= 1;
    }
    while (j < localLongSparseArray.size())
    {
      paramLottieDrawable = (BaseLayer)localLongSparseArray.get(localLongSparseArray.keyAt(j));
      if (paramLottieDrawable != null)
      {
        paramLayer = (BaseLayer)localLongSparseArray.get(paramLottieDrawable.getLayerModel().getParentId());
        if (paramLayer != null) {
          paramLottieDrawable.setParentLayer(paramLayer);
        }
      }
      j += 1;
    }
  }
  
  public void addValueCallback(Object paramObject, LottieValueCallback paramLottieValueCallback)
  {
    super.addValueCallback(paramObject, paramLottieValueCallback);
    if (paramObject == LottieProperty.TIME_REMAP)
    {
      if (paramLottieValueCallback == null)
      {
        timeRemapping = null;
        return;
      }
      timeRemapping = new ValueCallbackKeyframeAnimation(paramLottieValueCallback);
      addAnimation(timeRemapping);
    }
  }
  
  void drawLayer(Canvas paramCanvas, Matrix paramMatrix, int paramInt)
  {
    Way.beginSection("CompositionLayer#draw");
    newClipRect.set(0.0F, 0.0F, layerModel.getPreCompWidth(), layerModel.getPreCompHeight());
    paramMatrix.mapRect(newClipRect);
    if ((lottieDrawable.isApplyingOpacityToLayersEnabled()) && (layers.size() > 1) && (paramInt != 255)) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      layerPaint.setAlpha(paramInt);
      Utils.saveLayerCompat(paramCanvas, newClipRect, layerPaint);
    }
    else
    {
      paramCanvas.save();
    }
    if (i != 0) {
      paramInt = 255;
    }
    int i = layers.size() - 1;
    while (i >= 0)
    {
      boolean bool;
      if (!newClipRect.isEmpty()) {
        bool = paramCanvas.clipRect(newClipRect);
      } else {
        bool = true;
      }
      if (bool) {
        ((BaseLayer)layers.get(i)).draw(paramCanvas, paramMatrix, paramInt);
      }
      i -= 1;
    }
    paramCanvas.restore();
    Way.endSection("CompositionLayer#draw");
  }
  
  public void getBounds(RectF paramRectF, Matrix paramMatrix, boolean paramBoolean)
  {
    super.getBounds(paramRectF, paramMatrix, paramBoolean);
    int i = layers.size() - 1;
    while (i >= 0)
    {
      rect.set(0.0F, 0.0F, 0.0F, 0.0F);
      ((BaseLayer)layers.get(i)).getBounds(rect, boundsMatrix, true);
      paramRectF.union(rect);
      i -= 1;
    }
  }
  
  public boolean hasMasks()
  {
    if (hasMasks == null)
    {
      int i = layers.size() - 1;
      while (i >= 0)
      {
        BaseLayer localBaseLayer = (BaseLayer)layers.get(i);
        if ((localBaseLayer instanceof ShapeLayer))
        {
          if (localBaseLayer.hasMasksOnThisLayer())
          {
            hasMasks = Boolean.valueOf(true);
            return true;
          }
        }
        else if (((localBaseLayer instanceof CompositionLayer)) && (((CompositionLayer)localBaseLayer).hasMasks()))
        {
          hasMasks = Boolean.valueOf(true);
          return true;
        }
        i -= 1;
      }
      hasMasks = Boolean.valueOf(false);
    }
    return hasMasks.booleanValue();
  }
  
  public boolean hasMatte()
  {
    if (hasMatte == null)
    {
      if (hasMatteOnThisLayer())
      {
        hasMatte = Boolean.valueOf(true);
        return true;
      }
      int i = layers.size() - 1;
      while (i >= 0)
      {
        if (((BaseLayer)layers.get(i)).hasMatteOnThisLayer())
        {
          hasMatte = Boolean.valueOf(true);
          return true;
        }
        i -= 1;
      }
      hasMatte = Boolean.valueOf(false);
    }
    return hasMatte.booleanValue();
  }
  
  protected void resolveChildKeyPath(KeyPath paramKeyPath1, int paramInt, List paramList, KeyPath paramKeyPath2)
  {
    int i = 0;
    while (i < layers.size())
    {
      ((BaseLayer)layers.get(i)).resolveKeyPath(paramKeyPath1, paramInt, paramList, paramKeyPath2);
      i += 1;
    }
  }
  
  public void setProgress(float paramFloat)
  {
    super.setProgress(paramFloat);
    float f = paramFloat;
    if (timeRemapping != null)
    {
      paramFloat = lottieDrawable.getComposition().getDurationFrames();
      f = layerModel.getComposition().getStartFrame();
      f = (((Float)timeRemapping.getValue()).floatValue() * layerModel.getComposition().getFrameRate() - f) / (paramFloat + 0.01F);
    }
    paramFloat = f;
    if (layerModel.getTimeStretch() != 0.0F) {
      paramFloat = f / layerModel.getTimeStretch();
    }
    f = paramFloat;
    if (timeRemapping == null) {
      f = paramFloat - layerModel.getStartProgress();
    }
    int i = layers.size() - 1;
    while (i >= 0)
    {
      ((BaseLayer)layers.get(i)).setProgress(f);
      i -= 1;
    }
  }
}