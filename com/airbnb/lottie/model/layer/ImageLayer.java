package com.airbnb.lottie.model.layer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.animation.LPaint;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.ValueCallbackKeyframeAnimation;
import com.airbnb.lottie.utils.Utils;
import com.airbnb.lottie.value.LottieValueCallback;

public class ImageLayer
  extends BaseLayer
{
  private BaseKeyframeAnimation<ColorFilter, ColorFilter> colorFilterAnimation;
  private final Rect destRect = new Rect();
  private final Paint paint = new LPaint(3);
  private final Rect srcRect = new Rect();
  
  ImageLayer(LottieDrawable paramLottieDrawable, Layer paramLayer)
  {
    super(paramLottieDrawable, paramLayer);
  }
  
  private Bitmap getBitmap()
  {
    String str = layerModel.getRefId();
    return lottieDrawable.getImageAsset(str);
  }
  
  public void addValueCallback(Object paramObject, LottieValueCallback paramLottieValueCallback)
  {
    super.addValueCallback(paramObject, paramLottieValueCallback);
    if (paramObject == LottieProperty.COLOR_FILTER)
    {
      if (paramLottieValueCallback == null)
      {
        colorFilterAnimation = null;
        return;
      }
      colorFilterAnimation = new ValueCallbackKeyframeAnimation(paramLottieValueCallback);
    }
  }
  
  public void drawLayer(Canvas paramCanvas, Matrix paramMatrix, int paramInt)
  {
    Bitmap localBitmap = getBitmap();
    if (localBitmap != null)
    {
      if (localBitmap.isRecycled()) {
        return;
      }
      float f = Utils.dpScale();
      paint.setAlpha(paramInt);
      BaseKeyframeAnimation localBaseKeyframeAnimation = colorFilterAnimation;
      if (localBaseKeyframeAnimation != null) {
        paint.setColorFilter((ColorFilter)localBaseKeyframeAnimation.getValue());
      }
      paramCanvas.save();
      paramCanvas.concat(paramMatrix);
      srcRect.set(0, 0, localBitmap.getWidth(), localBitmap.getHeight());
      destRect.set(0, 0, (int)(localBitmap.getWidth() * f), (int)(localBitmap.getHeight() * f));
      paramCanvas.drawBitmap(localBitmap, srcRect, destRect, paint);
      paramCanvas.restore();
    }
  }
  
  public void getBounds(RectF paramRectF, Matrix paramMatrix, boolean paramBoolean)
  {
    super.getBounds(paramRectF, paramMatrix, paramBoolean);
    paramMatrix = getBitmap();
    if (paramMatrix != null)
    {
      paramRectF.set(0.0F, 0.0F, paramMatrix.getWidth() * Utils.dpScale(), paramMatrix.getHeight() * Utils.dpScale());
      boundsMatrix.mapRect(paramRectF);
    }
  }
}