package com.google.android.material.shadow;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.FillType;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
import androidx.appcompat.graphics.drawable.DrawableWrapper;
import androidx.core.content.ContextCompat;
import com.google.android.material.R.color;

public class ShadowDrawableWrapper
  extends DrawableWrapper
{
  static final double COS_45 = Math.cos(Math.toRadians(45.0D));
  static final float SHADOW_BOTTOM_SCALE = 1.0F;
  static final float SHADOW_HORIZ_SCALE = 0.5F;
  static final float SHADOW_MULTIPLIER = 1.5F;
  static final float SHADOW_TOP_SCALE = 0.25F;
  private boolean addPaddingForCorners = true;
  final RectF contentBounds;
  float cornerRadius;
  final Paint cornerShadowPaint;
  Path cornerShadowPath;
  private boolean dirty = true;
  final Paint edgeShadowPaint;
  float maxShadowSize;
  private boolean printedShadowClipWarning = false;
  float rawMaxShadowSize;
  float rawShadowSize;
  private float rotation;
  private final int shadowEndColor;
  private final int shadowMiddleColor;
  float shadowSize;
  private final int shadowStartColor;
  
  public ShadowDrawableWrapper(Context paramContext, Drawable paramDrawable, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    super(paramDrawable);
    shadowStartColor = ContextCompat.getColor(paramContext, R.color.design_fab_shadow_start_color);
    shadowMiddleColor = ContextCompat.getColor(paramContext, R.color.design_fab_shadow_mid_color);
    shadowEndColor = ContextCompat.getColor(paramContext, R.color.design_fab_shadow_end_color);
    cornerShadowPaint = new Paint(5);
    cornerShadowPaint.setStyle(Paint.Style.FILL);
    cornerRadius = Math.round(paramFloat1);
    contentBounds = new RectF();
    edgeShadowPaint = new Paint(cornerShadowPaint);
    edgeShadowPaint.setAntiAlias(false);
    setShadowSize(paramFloat2, paramFloat3);
  }
  
  private void buildComponents(Rect paramRect)
  {
    float f = rawMaxShadowSize * 1.5F;
    contentBounds.set(left + rawMaxShadowSize, top + f, right - rawMaxShadowSize, bottom - f);
    getWrappedDrawable().setBounds((int)contentBounds.left, (int)contentBounds.top, (int)contentBounds.right, (int)contentBounds.bottom);
    buildShadowCorners();
  }
  
  private void buildShadowCorners()
  {
    float f1 = cornerRadius;
    Object localObject1 = new RectF(-f1, -f1, f1, f1);
    RectF localRectF = new RectF((RectF)localObject1);
    f1 = shadowSize;
    localRectF.inset(-f1, -f1);
    Object localObject2 = cornerShadowPath;
    if (localObject2 == null) {
      cornerShadowPath = new Path();
    } else {
      ((Path)localObject2).reset();
    }
    cornerShadowPath.setFillType(Path.FillType.EVEN_ODD);
    cornerShadowPath.moveTo(-cornerRadius, 0.0F);
    cornerShadowPath.rLineTo(-shadowSize, 0.0F);
    cornerShadowPath.arcTo(localRectF, 180.0F, 90.0F, false);
    cornerShadowPath.arcTo((RectF)localObject1, 270.0F, -90.0F, false);
    cornerShadowPath.close();
    f1 = -top;
    if (f1 > 0.0F)
    {
      f2 = cornerRadius / f1;
      float f3 = (1.0F - f2) / 2.0F;
      localObject2 = cornerShadowPaint;
      i = shadowStartColor;
      j = shadowMiddleColor;
      k = shadowEndColor;
      Shader.TileMode localTileMode = Shader.TileMode.CLAMP;
      ((Paint)localObject2).setShader(new RadialGradient(0.0F, 0.0F, f1, new int[] { 0, i, j, k }, new float[] { 0.0F, f2, f3 + f2, 1.0F }, localTileMode));
    }
    localObject2 = edgeShadowPaint;
    f1 = top;
    float f2 = top;
    int i = shadowStartColor;
    int j = shadowMiddleColor;
    int k = shadowEndColor;
    localObject1 = Shader.TileMode.CLAMP;
    ((Paint)localObject2).setShader(new LinearGradient(0.0F, f1, 0.0F, f2, new int[] { i, j, k }, new float[] { 0.0F, 0.5F, 1.0F }, (Shader.TileMode)localObject1));
    edgeShadowPaint.setAntiAlias(false);
  }
  
  public static float calculateHorizontalPadding(float paramFloat1, float paramFloat2, boolean paramBoolean)
  {
    float f = paramFloat1;
    if (paramBoolean)
    {
      double d1 = paramFloat1;
      double d2 = COS_45;
      double d3 = paramFloat2;
      Double.isNaN(d3);
      Double.isNaN(d1);
      f = (float)(d1 + (1.0D - d2) * d3);
    }
    return f;
  }
  
  public static float calculateVerticalPadding(float paramFloat1, float paramFloat2, boolean paramBoolean)
  {
    if (paramBoolean)
    {
      double d1 = paramFloat1 * 1.5F;
      double d2 = COS_45;
      double d3 = paramFloat2;
      Double.isNaN(d3);
      Double.isNaN(d1);
      return (float)(d1 + (1.0D - d2) * d3);
    }
    return paramFloat1 * 1.5F;
  }
  
  private void drawShadow(Canvas paramCanvas)
  {
    int k = paramCanvas.save();
    paramCanvas.rotate(rotation, contentBounds.centerX(), contentBounds.centerY());
    float f1 = cornerRadius;
    float f2 = -f1 - shadowSize;
    float f4 = contentBounds.width();
    float f3 = f1 * 2.0F;
    if (f4 - f3 > 0.0F) {
      i = 1;
    } else {
      i = 0;
    }
    int j;
    if (contentBounds.height() - f3 > 0.0F) {
      j = 1;
    } else {
      j = 0;
    }
    float f6 = rawShadowSize;
    f4 = f1 / (f6 - 0.5F * f6 + f1);
    float f5 = f1 / (f6 - 0.25F * f6 + f1);
    f6 = f1 / (f6 - f6 * 1.0F + f1);
    int m = paramCanvas.save();
    paramCanvas.translate(contentBounds.left + f1, contentBounds.top + f1);
    paramCanvas.scale(f4, f5);
    paramCanvas.drawPath(cornerShadowPath, cornerShadowPaint);
    if (i != 0)
    {
      paramCanvas.scale(1.0F / f4, 1.0F);
      paramCanvas.drawRect(0.0F, f2, contentBounds.width() - f3, -cornerRadius, edgeShadowPaint);
    }
    paramCanvas.restoreToCount(m);
    m = paramCanvas.save();
    paramCanvas.translate(contentBounds.right - f1, contentBounds.bottom - f1);
    paramCanvas.scale(f4, f6);
    paramCanvas.rotate(180.0F);
    paramCanvas.drawPath(cornerShadowPath, cornerShadowPaint);
    if (i != 0)
    {
      paramCanvas.scale(1.0F / f4, 1.0F);
      paramCanvas.drawRect(0.0F, f2, contentBounds.width() - f3, -cornerRadius + shadowSize, edgeShadowPaint);
    }
    paramCanvas.restoreToCount(m);
    int i = paramCanvas.save();
    paramCanvas.translate(contentBounds.left + f1, contentBounds.bottom - f1);
    paramCanvas.scale(f4, f6);
    paramCanvas.rotate(270.0F);
    paramCanvas.drawPath(cornerShadowPath, cornerShadowPaint);
    if (j != 0)
    {
      paramCanvas.scale(1.0F / f6, 1.0F);
      paramCanvas.drawRect(0.0F, f2, contentBounds.height() - f3, -cornerRadius, edgeShadowPaint);
    }
    paramCanvas.restoreToCount(i);
    i = paramCanvas.save();
    paramCanvas.translate(contentBounds.right - f1, contentBounds.top + f1);
    paramCanvas.scale(f4, f5);
    paramCanvas.rotate(90.0F);
    paramCanvas.drawPath(cornerShadowPath, cornerShadowPaint);
    if (j != 0)
    {
      paramCanvas.scale(1.0F / f5, 1.0F);
      paramCanvas.drawRect(0.0F, f2, contentBounds.height() - f3, -cornerRadius, edgeShadowPaint);
    }
    paramCanvas.restoreToCount(i);
    paramCanvas.restoreToCount(k);
  }
  
  private static int toEven(float paramFloat)
  {
    int i = Math.round(paramFloat);
    if (i % 2 == 1) {
      return i - 1;
    }
    return i;
  }
  
  public void draw(Canvas paramCanvas)
  {
    if (dirty)
    {
      buildComponents(getBounds());
      dirty = false;
    }
    drawShadow(paramCanvas);
    super.draw(paramCanvas);
  }
  
  public float getCornerRadius()
  {
    return cornerRadius;
  }
  
  public float getMaxShadowSize()
  {
    return rawMaxShadowSize;
  }
  
  public float getMinHeight()
  {
    float f = rawMaxShadowSize;
    return Math.max(f, cornerRadius + f * 1.5F / 2.0F) * 2.0F + rawMaxShadowSize * 1.5F * 2.0F;
  }
  
  public float getMinWidth()
  {
    float f = rawMaxShadowSize;
    return Math.max(f, cornerRadius + f / 2.0F) * 2.0F + rawMaxShadowSize * 2.0F;
  }
  
  public int getOpacity()
  {
    return -3;
  }
  
  public boolean getPadding(Rect paramRect)
  {
    int i = (int)Math.ceil(calculateVerticalPadding(rawMaxShadowSize, cornerRadius, addPaddingForCorners));
    int j = (int)Math.ceil(calculateHorizontalPadding(rawMaxShadowSize, cornerRadius, addPaddingForCorners));
    paramRect.set(j, i, j, i);
    return true;
  }
  
  public float getShadowSize()
  {
    return rawShadowSize;
  }
  
  protected void onBoundsChange(Rect paramRect)
  {
    dirty = true;
  }
  
  public void setAddPaddingForCorners(boolean paramBoolean)
  {
    addPaddingForCorners = paramBoolean;
    invalidateSelf();
  }
  
  public void setAlpha(int paramInt)
  {
    super.setAlpha(paramInt);
    cornerShadowPaint.setAlpha(paramInt);
    edgeShadowPaint.setAlpha(paramInt);
  }
  
  public void setCornerRadius(float paramFloat)
  {
    paramFloat = Math.round(paramFloat);
    if (cornerRadius == paramFloat) {
      return;
    }
    cornerRadius = paramFloat;
    dirty = true;
    invalidateSelf();
  }
  
  public void setMaxShadowSize(float paramFloat)
  {
    setShadowSize(rawShadowSize, paramFloat);
  }
  
  public final void setRotation(float paramFloat)
  {
    if (rotation != paramFloat)
    {
      rotation = paramFloat;
      invalidateSelf();
    }
  }
  
  public void setShadowSize(float paramFloat)
  {
    setShadowSize(paramFloat, rawMaxShadowSize);
  }
  
  public void setShadowSize(float paramFloat1, float paramFloat2)
  {
    if ((paramFloat1 >= 0.0F) && (paramFloat2 >= 0.0F))
    {
      float f = toEven(paramFloat1);
      paramFloat2 = toEven(paramFloat2);
      paramFloat1 = f;
      if (f > paramFloat2)
      {
        if (!printedShadowClipWarning) {
          printedShadowClipWarning = true;
        }
        paramFloat1 = paramFloat2;
      }
      if ((rawShadowSize == paramFloat1) && (rawMaxShadowSize == paramFloat2)) {
        return;
      }
      rawShadowSize = paramFloat1;
      rawMaxShadowSize = paramFloat2;
      shadowSize = Math.round(paramFloat1 * 1.5F);
      maxShadowSize = paramFloat2;
      dirty = true;
      invalidateSelf();
      return;
    }
    throw new IllegalArgumentException("invalid shadow size");
  }
}
