package com.google.android.material.circularreveal;

import android.animation.TypeEvaluator;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.Property;
import com.google.android.material.math.MathUtils;

public abstract interface CircularRevealWidget
  extends CircularRevealHelper.Delegate
{
  public abstract void buildCircularRevealCache();
  
  public abstract void destroyCircularRevealCache();
  
  public abstract void draw(Canvas paramCanvas);
  
  public abstract Drawable getCircularRevealOverlayDrawable();
  
  public abstract int getCircularRevealScrimColor();
  
  public abstract RevealInfo getRevealInfo();
  
  public abstract boolean isOpaque();
  
  public abstract void setCircularRevealOverlayDrawable(Drawable paramDrawable);
  
  public abstract void setCircularRevealScrimColor(int paramInt);
  
  public abstract void setRevealInfo(RevealInfo paramRevealInfo);
  
  public static class CircularRevealEvaluator
    implements TypeEvaluator<CircularRevealWidget.RevealInfo>
  {
    public static final TypeEvaluator<CircularRevealWidget.RevealInfo> CIRCULAR_REVEAL = new CircularRevealEvaluator();
    private final CircularRevealWidget.RevealInfo revealInfo = new CircularRevealWidget.RevealInfo(null);
    
    public CircularRevealEvaluator() {}
    
    public CircularRevealWidget.RevealInfo evaluate(float paramFloat, CircularRevealWidget.RevealInfo paramRevealInfo1, CircularRevealWidget.RevealInfo paramRevealInfo2)
    {
      revealInfo.set(MathUtils.lerp(centerX, centerX, paramFloat), MathUtils.lerp(centerY, centerY, paramFloat), MathUtils.lerp(radius, radius, paramFloat));
      return revealInfo;
    }
  }
  
  public static class CircularRevealProperty
    extends Property<CircularRevealWidget, CircularRevealWidget.RevealInfo>
  {
    public static final Property<CircularRevealWidget, CircularRevealWidget.RevealInfo> CIRCULAR_REVEAL = new CircularRevealProperty("circularReveal");
    
    private CircularRevealProperty(String paramString)
    {
      super(paramString);
    }
    
    public CircularRevealWidget.RevealInfo findDevice(CircularRevealWidget paramCircularRevealWidget)
    {
      return paramCircularRevealWidget.getRevealInfo();
    }
    
    public void hasClassAnnotation(CircularRevealWidget paramCircularRevealWidget, CircularRevealWidget.RevealInfo paramRevealInfo)
    {
      paramCircularRevealWidget.setRevealInfo(paramRevealInfo);
    }
  }
  
  public static class CircularRevealScrimColorProperty
    extends Property<CircularRevealWidget, Integer>
  {
    public static final Property<CircularRevealWidget, Integer> CIRCULAR_REVEAL_SCRIM_COLOR = new CircularRevealScrimColorProperty("circularRevealScrimColor");
    
    private CircularRevealScrimColorProperty(String paramString)
    {
      super(paramString);
    }
    
    public Integer getOr(CircularRevealWidget paramCircularRevealWidget)
    {
      return Integer.valueOf(paramCircularRevealWidget.getCircularRevealScrimColor());
    }
    
    public void updateCurrent(CircularRevealWidget paramCircularRevealWidget, Integer paramInteger)
    {
      paramCircularRevealWidget.setCircularRevealScrimColor(paramInteger.intValue());
    }
  }
  
  public static class RevealInfo
  {
    public static final float INVALID_RADIUS = Float.MAX_VALUE;
    public float centerX;
    public float centerY;
    public float radius;
    
    private RevealInfo() {}
    
    public RevealInfo(float paramFloat1, float paramFloat2, float paramFloat3)
    {
      centerX = paramFloat1;
      centerY = paramFloat2;
      radius = paramFloat3;
    }
    
    public RevealInfo(RevealInfo paramRevealInfo)
    {
      this(centerX, centerY, radius);
    }
    
    public boolean isInvalid()
    {
      return radius == Float.MAX_VALUE;
    }
    
    public void set(float paramFloat1, float paramFloat2, float paramFloat3)
    {
      centerX = paramFloat1;
      centerY = paramFloat2;
      radius = paramFloat3;
    }
    
    public void setTileSource(RevealInfo paramRevealInfo)
    {
      set(centerX, centerY, radius);
    }
  }
}
