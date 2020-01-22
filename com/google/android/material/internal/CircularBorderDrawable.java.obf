package com.google.android.material.internal;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import androidx.core.graphics.ColorUtils;

public class CircularBorderDrawable
  extends Drawable
{
  private static final float DRAW_STROKE_WIDTH_MULTIPLE = 1.3333F;
  private ColorStateList borderTint;
  float borderWidth;
  private int bottomInnerStrokeColor;
  private int bottomOuterStrokeColor;
  private int currentBorderTintColor;
  private boolean invalidateShader = true;
  final Paint paint = new Paint(1);
  final Rect rect = new Rect();
  final RectF rectF = new RectF();
  private float rotation;
  final CircularBorderState state = new CircularBorderState(null);
  private int topInnerStrokeColor;
  private int topOuterStrokeColor;
  
  public CircularBorderDrawable()
  {
    paint.setStyle(Paint.Style.STROKE);
  }
  
  private Shader createGradientShader()
  {
    Object localObject = rect;
    copyBounds((Rect)localObject);
    float f1 = borderWidth / ((Rect)localObject).height();
    int i = ColorUtils.compositeColors(topOuterStrokeColor, currentBorderTintColor);
    int j = ColorUtils.compositeColors(topInnerStrokeColor, currentBorderTintColor);
    int k = ColorUtils.compositeColors(ColorUtils.setAlphaComponent(topInnerStrokeColor, 0), currentBorderTintColor);
    int m = ColorUtils.compositeColors(ColorUtils.setAlphaComponent(bottomInnerStrokeColor, 0), currentBorderTintColor);
    int n = ColorUtils.compositeColors(bottomInnerStrokeColor, currentBorderTintColor);
    int i1 = ColorUtils.compositeColors(bottomOuterStrokeColor, currentBorderTintColor);
    float f2 = top;
    float f3 = bottom;
    localObject = Shader.TileMode.CLAMP;
    return new LinearGradient(0.0F, f2, 0.0F, f3, new int[] { i, j, k, m, n, i1 }, new float[] { 0.0F, f1, 0.5F, 0.5F, 1.0F - f1, 1.0F }, (Shader.TileMode)localObject);
  }
  
  public void draw(Canvas paramCanvas)
  {
    if (invalidateShader)
    {
      paint.setShader(createGradientShader());
      invalidateShader = false;
    }
    float f = paint.getStrokeWidth() / 2.0F;
    RectF localRectF = rectF;
    copyBounds(rect);
    localRectF.set(rect);
    left += f;
    top += f;
    right -= f;
    bottom -= f;
    paramCanvas.save();
    paramCanvas.rotate(rotation, localRectF.centerX(), localRectF.centerY());
    paramCanvas.drawOval(localRectF, paint);
    paramCanvas.restore();
  }
  
  public Drawable.ConstantState getConstantState()
  {
    return state;
  }
  
  public int getOpacity()
  {
    if (borderWidth > 0.0F) {
      return -3;
    }
    return -2;
  }
  
  public boolean getPadding(Rect paramRect)
  {
    int i = Math.round(borderWidth);
    paramRect.set(i, i, i, i);
    return true;
  }
  
  public boolean isStateful()
  {
    ColorStateList localColorStateList = borderTint;
    return ((localColorStateList != null) && (localColorStateList.isStateful())) || (super.isStateful());
  }
  
  protected void onBoundsChange(Rect paramRect)
  {
    invalidateShader = true;
  }
  
  protected boolean onStateChange(int[] paramArrayOfInt)
  {
    ColorStateList localColorStateList = borderTint;
    if (localColorStateList != null)
    {
      int i = localColorStateList.getColorForState(paramArrayOfInt, currentBorderTintColor);
      if (i != currentBorderTintColor)
      {
        invalidateShader = true;
        currentBorderTintColor = i;
      }
    }
    if (invalidateShader) {
      invalidateSelf();
    }
    return invalidateShader;
  }
  
  public void setAlpha(int paramInt)
  {
    paint.setAlpha(paramInt);
    invalidateSelf();
  }
  
  public void setBorderTint(ColorStateList paramColorStateList)
  {
    if (paramColorStateList != null) {
      currentBorderTintColor = paramColorStateList.getColorForState(getState(), currentBorderTintColor);
    }
    borderTint = paramColorStateList;
    invalidateShader = true;
    invalidateSelf();
  }
  
  public void setBorderWidth(float paramFloat)
  {
    if (borderWidth != paramFloat)
    {
      borderWidth = paramFloat;
      paint.setStrokeWidth(paramFloat * 1.3333F);
      invalidateShader = true;
      invalidateSelf();
    }
  }
  
  public void setColorFilter(ColorFilter paramColorFilter)
  {
    paint.setColorFilter(paramColorFilter);
    invalidateSelf();
  }
  
  public void setGradientColors(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    topOuterStrokeColor = paramInt1;
    topInnerStrokeColor = paramInt2;
    bottomOuterStrokeColor = paramInt3;
    bottomInnerStrokeColor = paramInt4;
  }
  
  public final void setRotation(float paramFloat)
  {
    if (paramFloat != rotation)
    {
      rotation = paramFloat;
      invalidateSelf();
    }
  }
  
  private class CircularBorderState
    extends Drawable.ConstantState
  {
    private CircularBorderState() {}
    
    public int getChangingConfigurations()
    {
      return 0;
    }
    
    public Drawable newDrawable()
    {
      return CircularBorderDrawable.this;
    }
  }
}
