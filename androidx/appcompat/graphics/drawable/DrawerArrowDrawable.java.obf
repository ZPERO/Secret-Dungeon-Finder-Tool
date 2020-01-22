package androidx.appcompat.graphics.drawable;

import android.content.Context;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import androidx.appcompat.R.attr;
import androidx.appcompat.R.style;
import androidx.appcompat.R.styleable;
import androidx.core.graphics.drawable.DrawableCompat;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class DrawerArrowDrawable
  extends Drawable
{
  public static final int ARROW_DIRECTION_END = 3;
  public static final int ARROW_DIRECTION_LEFT = 0;
  public static final int ARROW_DIRECTION_RIGHT = 1;
  public static final int ARROW_DIRECTION_START = 2;
  private static final float ARROW_HEAD_ANGLE = (float)Math.toRadians(45.0D);
  private float mArrowHeadLength;
  private float mArrowShaftLength;
  private float mBarGap;
  private float mBarLength;
  private int mDirection = 2;
  private float mMaxCutForBarSize;
  private final Paint mPaint = new Paint();
  private final Path mPath = new Path();
  private float mProgress;
  private final int mSize;
  private boolean mSpin;
  private boolean mVerticalMirror = false;
  
  public DrawerArrowDrawable(Context paramContext)
  {
    mPaint.setStyle(Paint.Style.STROKE);
    mPaint.setStrokeJoin(Paint.Join.MITER);
    mPaint.setStrokeCap(Paint.Cap.BUTT);
    mPaint.setAntiAlias(true);
    paramContext = paramContext.getTheme().obtainStyledAttributes(null, R.styleable.DrawerArrowToggle, R.attr.drawerArrowStyle, R.style.Base_Widget_AppCompat_DrawerArrowToggle);
    setColor(paramContext.getColor(R.styleable.DrawerArrowToggle_color, 0));
    setBarThickness(paramContext.getDimension(R.styleable.DrawerArrowToggle_thickness, 0.0F));
    setSpinEnabled(paramContext.getBoolean(R.styleable.DrawerArrowToggle_spinBars, true));
    setGapSize(Math.round(paramContext.getDimension(R.styleable.DrawerArrowToggle_gapBetweenBars, 0.0F)));
    mSize = paramContext.getDimensionPixelSize(R.styleable.DrawerArrowToggle_drawableSize, 0);
    mBarLength = Math.round(paramContext.getDimension(R.styleable.DrawerArrowToggle_barLength, 0.0F));
    mArrowHeadLength = Math.round(paramContext.getDimension(R.styleable.DrawerArrowToggle_arrowHeadLength, 0.0F));
    mArrowShaftLength = paramContext.getDimension(R.styleable.DrawerArrowToggle_arrowShaftLength, 0.0F);
    paramContext.recycle();
  }
  
  private static float lerp(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    return paramFloat1 + (paramFloat2 - paramFloat1) * paramFloat3;
  }
  
  public void draw(Canvas paramCanvas)
  {
    Rect localRect = getBounds();
    int k = mDirection;
    int j = 0;
    int i = j;
    if (k != 0)
    {
      if (k != 1) {
        if (k != 3)
        {
          i = j;
          if (DrawableCompat.getLayoutDirection(this) != 1) {
            break label65;
          }
        }
        else
        {
          i = j;
          if (DrawableCompat.getLayoutDirection(this) != 0) {
            break label65;
          }
        }
      }
      i = 1;
    }
    label65:
    float f1 = mArrowHeadLength;
    f1 = (float)Math.sqrt(f1 * f1 * 2.0F);
    float f5 = lerp(mBarLength, f1, mProgress);
    float f3 = lerp(mBarLength, mArrowShaftLength, mProgress);
    float f4 = Math.round(lerp(0.0F, mMaxCutForBarSize, mProgress));
    float f6 = lerp(0.0F, ARROW_HEAD_ANGLE, mProgress);
    if (i != 0) {
      f1 = 0.0F;
    } else {
      f1 = -180.0F;
    }
    if (i != 0) {
      f2 = 180.0F;
    } else {
      f2 = 0.0F;
    }
    f1 = lerp(f1, f2, mProgress);
    double d1 = f5;
    double d2 = f6;
    double d3 = Math.cos(d2);
    Double.isNaN(d1);
    float f2 = (float)Math.round(d3 * d1);
    d2 = Math.sin(d2);
    Double.isNaN(d1);
    f5 = (float)Math.round(d1 * d2);
    mPath.rewind();
    f6 = lerp(mBarGap + mPaint.getStrokeWidth(), -mMaxCutForBarSize, mProgress);
    float f7 = -f3 / 2.0F;
    mPath.moveTo(f7 + f4, 0.0F);
    mPath.rLineTo(f3 - f4 * 2.0F, 0.0F);
    mPath.moveTo(f7, f6);
    mPath.rLineTo(f2, f5);
    mPath.moveTo(f7, -f6);
    mPath.rLineTo(f2, -f5);
    mPath.close();
    paramCanvas.save();
    f2 = mPaint.getStrokeWidth();
    f4 = localRect.height();
    f3 = mBarGap;
    f4 = (int)(f4 - 3.0F * f2 - 2.0F * f3) / 4 * 2;
    paramCanvas.translate(localRect.centerX(), f4 + (f2 * 1.5F + f3));
    if (mSpin)
    {
      if ((mVerticalMirror ^ i)) {
        i = -1;
      } else {
        i = 1;
      }
      paramCanvas.rotate(f1 * i);
    }
    else if (i != 0)
    {
      paramCanvas.rotate(180.0F);
    }
    paramCanvas.drawPath(mPath, mPaint);
    paramCanvas.restore();
  }
  
  public float getArrowHeadLength()
  {
    return mArrowHeadLength;
  }
  
  public float getArrowShaftLength()
  {
    return mArrowShaftLength;
  }
  
  public float getBarLength()
  {
    return mBarLength;
  }
  
  public float getBarThickness()
  {
    return mPaint.getStrokeWidth();
  }
  
  public int getColor()
  {
    return mPaint.getColor();
  }
  
  public int getDirection()
  {
    return mDirection;
  }
  
  public float getGapSize()
  {
    return mBarGap;
  }
  
  public int getIntrinsicHeight()
  {
    return mSize;
  }
  
  public int getIntrinsicWidth()
  {
    return mSize;
  }
  
  public int getOpacity()
  {
    return -3;
  }
  
  public final Paint getPaint()
  {
    return mPaint;
  }
  
  public float getProgress()
  {
    return mProgress;
  }
  
  public boolean isSpinEnabled()
  {
    return mSpin;
  }
  
  public void setAlpha(int paramInt)
  {
    if (paramInt != mPaint.getAlpha())
    {
      mPaint.setAlpha(paramInt);
      invalidateSelf();
    }
  }
  
  public void setArrowHeadLength(float paramFloat)
  {
    if (mArrowHeadLength != paramFloat)
    {
      mArrowHeadLength = paramFloat;
      invalidateSelf();
    }
  }
  
  public void setArrowShaftLength(float paramFloat)
  {
    if (mArrowShaftLength != paramFloat)
    {
      mArrowShaftLength = paramFloat;
      invalidateSelf();
    }
  }
  
  public void setBarLength(float paramFloat)
  {
    if (mBarLength != paramFloat)
    {
      mBarLength = paramFloat;
      invalidateSelf();
    }
  }
  
  public void setBarThickness(float paramFloat)
  {
    if (mPaint.getStrokeWidth() != paramFloat)
    {
      mPaint.setStrokeWidth(paramFloat);
      double d1 = paramFloat / 2.0F;
      double d2 = Math.cos(ARROW_HEAD_ANGLE);
      Double.isNaN(d1);
      mMaxCutForBarSize = ((float)(d1 * d2));
      invalidateSelf();
    }
  }
  
  public void setColor(int paramInt)
  {
    if (paramInt != mPaint.getColor())
    {
      mPaint.setColor(paramInt);
      invalidateSelf();
    }
  }
  
  public void setColorFilter(ColorFilter paramColorFilter)
  {
    mPaint.setColorFilter(paramColorFilter);
    invalidateSelf();
  }
  
  public void setDirection(int paramInt)
  {
    if (paramInt != mDirection)
    {
      mDirection = paramInt;
      invalidateSelf();
    }
  }
  
  public void setGapSize(float paramFloat)
  {
    if (paramFloat != mBarGap)
    {
      mBarGap = paramFloat;
      invalidateSelf();
    }
  }
  
  public void setProgress(float paramFloat)
  {
    if (mProgress != paramFloat)
    {
      mProgress = paramFloat;
      invalidateSelf();
    }
  }
  
  public void setSpinEnabled(boolean paramBoolean)
  {
    if (mSpin != paramBoolean)
    {
      mSpin = paramBoolean;
      invalidateSelf();
    }
  }
  
  public void setVerticalMirror(boolean paramBoolean)
  {
    if (mVerticalMirror != paramBoolean)
    {
      mVerticalMirror = paramBoolean;
      invalidateSelf();
    }
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface ArrowDirection {}
}
