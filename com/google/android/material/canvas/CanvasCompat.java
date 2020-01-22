package com.google.android.material.canvas;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.os.Build.VERSION;

public class CanvasCompat
{
  private CanvasCompat() {}
  
  public static int saveLayerAlpha(Canvas paramCanvas, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, int paramInt)
  {
    if (Build.VERSION.SDK_INT > 21) {
      return paramCanvas.saveLayerAlpha(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramInt);
    }
    return paramCanvas.saveLayerAlpha(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramInt, 31);
  }
  
  public static int saveLayerAlpha(Canvas paramCanvas, RectF paramRectF, int paramInt)
  {
    if (Build.VERSION.SDK_INT > 21) {
      return paramCanvas.saveLayerAlpha(paramRectF, paramInt);
    }
    return paramCanvas.saveLayerAlpha(paramRectF, paramInt, 31);
  }
}
