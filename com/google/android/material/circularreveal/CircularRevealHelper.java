package com.google.android.material.circularreveal;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.Rect;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.view.View;
import com.google.android.material.math.MathUtils;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class CircularRevealHelper
{
  public static final int BITMAP_SHADER = 0;
  public static final int CLIP_PATH = 1;
  private static final boolean DEBUG = false;
  public static final int REVEAL_ANIMATOR = 2;
  public static final int STRATEGY = 0;
  private boolean buildingCircularRevealCache;
  private Paint debugPaint;
  private final Delegate delegate;
  private boolean hasCircularRevealCache;
  private Drawable overlayDrawable;
  private CircularRevealWidget.RevealInfo revealInfo;
  private final Paint revealPaint;
  private final Path revealPath;
  private final Paint scrimPaint;
  private final View view;
  
  static
  {
    if (Build.VERSION.SDK_INT >= 21)
    {
      STRATEGY = 2;
      return;
    }
    if (Build.VERSION.SDK_INT >= 18)
    {
      STRATEGY = 1;
      return;
    }
  }
  
  public CircularRevealHelper(Delegate paramDelegate)
  {
    delegate = paramDelegate;
    view = ((View)paramDelegate);
    view.setWillNotDraw(false);
    revealPath = new Path();
    revealPaint = new Paint(7);
    scrimPaint = new Paint(1);
    scrimPaint.setColor(0);
  }
  
  private void drawDebugCircle(Canvas paramCanvas, int paramInt, float paramFloat)
  {
    debugPaint.setColor(paramInt);
    debugPaint.setStrokeWidth(paramFloat);
    paramCanvas.drawCircle(revealInfo.centerX, revealInfo.centerY, revealInfo.radius - paramFloat / 2.0F, debugPaint);
  }
  
  private void drawDebugMode(Canvas paramCanvas)
  {
    delegate.actualDraw(paramCanvas);
    if (shouldDrawScrim()) {
      paramCanvas.drawCircle(revealInfo.centerX, revealInfo.centerY, revealInfo.radius, scrimPaint);
    }
    if (shouldDrawCircularReveal())
    {
      drawDebugCircle(paramCanvas, -16777216, 10.0F);
      drawDebugCircle(paramCanvas, -65536, 5.0F);
    }
    drawOverlayDrawable(paramCanvas);
  }
  
  private void drawOverlayDrawable(Canvas paramCanvas)
  {
    if (shouldDrawOverlayDrawable())
    {
      Rect localRect = overlayDrawable.getBounds();
      float f1 = revealInfo.centerX - localRect.width() / 2.0F;
      float f2 = revealInfo.centerY - localRect.height() / 2.0F;
      paramCanvas.translate(f1, f2);
      overlayDrawable.draw(paramCanvas);
      paramCanvas.translate(-f1, -f2);
    }
  }
  
  private float getDistanceToFurthestCorner(CircularRevealWidget.RevealInfo paramRevealInfo)
  {
    return MathUtils.distanceToFurthestCorner(centerX, centerY, 0.0F, 0.0F, view.getWidth(), view.getHeight());
  }
  
  private void invalidateRevealInfo()
  {
    if (STRATEGY == 1)
    {
      revealPath.rewind();
      CircularRevealWidget.RevealInfo localRevealInfo = revealInfo;
      if (localRevealInfo != null) {
        revealPath.addCircle(centerX, revealInfo.centerY, revealInfo.radius, Path.Direction.CW);
      }
    }
    view.invalidate();
  }
  
  private boolean shouldDrawCircularReveal()
  {
    CircularRevealWidget.RevealInfo localRevealInfo = revealInfo;
    int i;
    if ((localRevealInfo != null) && (!localRevealInfo.isInvalid())) {
      i = 0;
    } else {
      i = 1;
    }
    if (STRATEGY == 0)
    {
      if ((i == 0) && (hasCircularRevealCache)) {
        return true;
      }
    }
    else {
      return i ^ 0x1;
    }
    return false;
  }
  
  private boolean shouldDrawOverlayDrawable()
  {
    return (!buildingCircularRevealCache) && (overlayDrawable != null) && (revealInfo != null);
  }
  
  private boolean shouldDrawScrim()
  {
    return (!buildingCircularRevealCache) && (Color.alpha(scrimPaint.getColor()) != 0);
  }
  
  public void buildCircularRevealCache()
  {
    if (STRATEGY == 0)
    {
      buildingCircularRevealCache = true;
      hasCircularRevealCache = false;
      view.buildDrawingCache();
      Bitmap localBitmap = view.getDrawingCache();
      Object localObject1 = localBitmap;
      Object localObject2 = localObject1;
      if (localBitmap == null)
      {
        localObject2 = localObject1;
        if (view.getWidth() != 0)
        {
          localObject2 = localObject1;
          if (view.getHeight() != 0)
          {
            localObject2 = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
            localObject1 = localObject2;
            localObject2 = new Canvas((Bitmap)localObject2);
            view.draw((Canvas)localObject2);
            localObject2 = localObject1;
          }
        }
      }
      if (localObject2 != null) {
        revealPaint.setShader(new BitmapShader((Bitmap)localObject2, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
      }
      buildingCircularRevealCache = false;
      hasCircularRevealCache = true;
    }
  }
  
  public void destroyCircularRevealCache()
  {
    if (STRATEGY == 0)
    {
      hasCircularRevealCache = false;
      view.destroyDrawingCache();
      revealPaint.setShader(null);
      view.invalidate();
    }
  }
  
  public void draw(Canvas paramCanvas)
  {
    if (shouldDrawCircularReveal())
    {
      int i = STRATEGY;
      if (i != 0)
      {
        if (i != 1)
        {
          if (i == 2)
          {
            delegate.actualDraw(paramCanvas);
            if (shouldDrawScrim()) {
              paramCanvas.drawRect(0.0F, 0.0F, view.getWidth(), view.getHeight(), scrimPaint);
            }
          }
          else
          {
            paramCanvas = new StringBuilder();
            paramCanvas.append("Unsupported strategy ");
            paramCanvas.append(STRATEGY);
            throw new IllegalStateException(paramCanvas.toString());
          }
        }
        else
        {
          i = paramCanvas.save();
          paramCanvas.clipPath(revealPath);
          delegate.actualDraw(paramCanvas);
          if (shouldDrawScrim()) {
            paramCanvas.drawRect(0.0F, 0.0F, view.getWidth(), view.getHeight(), scrimPaint);
          }
          paramCanvas.restoreToCount(i);
        }
      }
      else
      {
        paramCanvas.drawCircle(revealInfo.centerX, revealInfo.centerY, revealInfo.radius, revealPaint);
        if (shouldDrawScrim()) {
          paramCanvas.drawCircle(revealInfo.centerX, revealInfo.centerY, revealInfo.radius, scrimPaint);
        }
      }
    }
    else
    {
      delegate.actualDraw(paramCanvas);
      if (shouldDrawScrim()) {
        paramCanvas.drawRect(0.0F, 0.0F, view.getWidth(), view.getHeight(), scrimPaint);
      }
    }
    drawOverlayDrawable(paramCanvas);
  }
  
  public Drawable getCircularRevealOverlayDrawable()
  {
    return overlayDrawable;
  }
  
  public int getCircularRevealScrimColor()
  {
    return scrimPaint.getColor();
  }
  
  public CircularRevealWidget.RevealInfo getRevealInfo()
  {
    CircularRevealWidget.RevealInfo localRevealInfo = revealInfo;
    if (localRevealInfo == null) {
      return null;
    }
    localRevealInfo = new CircularRevealWidget.RevealInfo(localRevealInfo);
    if (localRevealInfo.isInvalid()) {
      radius = getDistanceToFurthestCorner(localRevealInfo);
    }
    return localRevealInfo;
  }
  
  public boolean isOpaque()
  {
    return (delegate.actualIsOpaque()) && (!shouldDrawCircularReveal());
  }
  
  public void setCircularRevealOverlayDrawable(Drawable paramDrawable)
  {
    overlayDrawable = paramDrawable;
    view.invalidate();
  }
  
  public void setCircularRevealScrimColor(int paramInt)
  {
    scrimPaint.setColor(paramInt);
    view.invalidate();
  }
  
  public void setRevealInfo(CircularRevealWidget.RevealInfo paramRevealInfo)
  {
    if (paramRevealInfo == null)
    {
      revealInfo = null;
    }
    else
    {
      CircularRevealWidget.RevealInfo localRevealInfo = revealInfo;
      if (localRevealInfo == null) {
        revealInfo = new CircularRevealWidget.RevealInfo(paramRevealInfo);
      } else {
        localRevealInfo.setTileSource(paramRevealInfo);
      }
      if (MathUtils.isBetween(radius, getDistanceToFurthestCorner(paramRevealInfo), 1.0E-4F)) {
        revealInfo.radius = Float.MAX_VALUE;
      }
    }
    invalidateRevealInfo();
  }
  
  static abstract interface Delegate
  {
    public abstract void actualDraw(Canvas paramCanvas);
    
    public abstract boolean actualIsOpaque();
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface Strategy {}
}
