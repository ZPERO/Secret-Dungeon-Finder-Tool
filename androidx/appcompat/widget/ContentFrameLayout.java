package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.core.view.ViewCompat;

public class ContentFrameLayout
  extends FrameLayout
{
  private OnAttachListener mAttachListener;
  private final Rect mDecorPadding = new Rect();
  private TypedValue mFixedHeightMajor;
  private TypedValue mFixedHeightMinor;
  private TypedValue mFixedWidthMajor;
  private TypedValue mFixedWidthMinor;
  private TypedValue mMinWidthMajor;
  private TypedValue mMinWidthMinor;
  
  public ContentFrameLayout(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public ContentFrameLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public ContentFrameLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  public void dispatchFitSystemWindows(Rect paramRect)
  {
    fitSystemWindows(paramRect);
  }
  
  public TypedValue getFixedHeightMajor()
  {
    if (mFixedHeightMajor == null) {
      mFixedHeightMajor = new TypedValue();
    }
    return mFixedHeightMajor;
  }
  
  public TypedValue getFixedHeightMinor()
  {
    if (mFixedHeightMinor == null) {
      mFixedHeightMinor = new TypedValue();
    }
    return mFixedHeightMinor;
  }
  
  public TypedValue getFixedWidthMajor()
  {
    if (mFixedWidthMajor == null) {
      mFixedWidthMajor = new TypedValue();
    }
    return mFixedWidthMajor;
  }
  
  public TypedValue getFixedWidthMinor()
  {
    if (mFixedWidthMinor == null) {
      mFixedWidthMinor = new TypedValue();
    }
    return mFixedWidthMinor;
  }
  
  public TypedValue getMinWidthMajor()
  {
    if (mMinWidthMajor == null) {
      mMinWidthMajor = new TypedValue();
    }
    return mMinWidthMajor;
  }
  
  public TypedValue getMinWidthMinor()
  {
    if (mMinWidthMinor == null) {
      mMinWidthMinor = new TypedValue();
    }
    return mMinWidthMinor;
  }
  
  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    OnAttachListener localOnAttachListener = mAttachListener;
    if (localOnAttachListener != null) {
      localOnAttachListener.onAttachedFromWindow();
    }
  }
  
  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    OnAttachListener localOnAttachListener = mAttachListener;
    if (localOnAttachListener != null) {
      localOnAttachListener.onDetachedFromWindow();
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    DisplayMetrics localDisplayMetrics = getContext().getResources().getDisplayMetrics();
    int i = widthPixels;
    int j = heightPixels;
    int n = 1;
    if (i < j) {
      i = 1;
    } else {
      i = 0;
    }
    int i1 = View.MeasureSpec.getMode(paramInt1);
    int m = View.MeasureSpec.getMode(paramInt2);
    TypedValue localTypedValue;
    float f;
    if (i1 == Integer.MIN_VALUE)
    {
      if (i != 0) {
        localTypedValue = mFixedWidthMinor;
      } else {
        localTypedValue = mFixedWidthMajor;
      }
      if ((localTypedValue != null) && (type != 0))
      {
        if (type == 5) {}
        for (f = localTypedValue.getDimension(localDisplayMetrics);; f = localTypedValue.getFraction(widthPixels, widthPixels))
        {
          j = (int)f;
          break label155;
          if (type != 6) {
            break;
          }
        }
        j = 0;
        label155:
        if (j > 0)
        {
          k = View.MeasureSpec.makeMeasureSpec(Math.min(j - (mDecorPadding.left + mDecorPadding.right), View.MeasureSpec.getSize(paramInt1)), 1073741824);
          paramInt1 = 1;
          break label206;
        }
      }
    }
    j = 0;
    int k = paramInt1;
    paramInt1 = j;
    label206:
    j = paramInt2;
    if (m == Integer.MIN_VALUE)
    {
      if (i != 0) {
        localTypedValue = mFixedHeightMajor;
      } else {
        localTypedValue = mFixedHeightMinor;
      }
      j = paramInt2;
      if (localTypedValue != null)
      {
        j = paramInt2;
        if (type != 0)
        {
          if (type == 5) {}
          for (f = localTypedValue.getDimension(localDisplayMetrics);; f = localTypedValue.getFraction(heightPixels, heightPixels))
          {
            m = (int)f;
            break label313;
            if (type != 6) {
              break;
            }
          }
          m = 0;
          label313:
          j = paramInt2;
          if (m > 0) {
            j = View.MeasureSpec.makeMeasureSpec(Math.min(m - (mDecorPadding.top + mDecorPadding.bottom), View.MeasureSpec.getSize(paramInt2)), 1073741824);
          }
        }
      }
    }
    super.onMeasure(k, j);
    m = getMeasuredWidth();
    k = View.MeasureSpec.makeMeasureSpec(m, 1073741824);
    if ((paramInt1 == 0) && (i1 == Integer.MIN_VALUE))
    {
      if (i != 0) {
        localTypedValue = mMinWidthMinor;
      } else {
        localTypedValue = mMinWidthMajor;
      }
      if ((localTypedValue != null) && (type != 0))
      {
        if (type == 5) {}
        for (f = localTypedValue.getDimension(localDisplayMetrics);; f = localTypedValue.getFraction(widthPixels, widthPixels))
        {
          paramInt1 = (int)f;
          break label476;
          if (type != 6) {
            break;
          }
        }
        paramInt1 = 0;
        label476:
        paramInt2 = paramInt1;
        if (paramInt1 > 0) {
          paramInt2 = paramInt1 - (mDecorPadding.left + mDecorPadding.right);
        }
        if (m < paramInt2)
        {
          paramInt1 = View.MeasureSpec.makeMeasureSpec(paramInt2, 1073741824);
          paramInt2 = n;
          break label524;
        }
      }
    }
    paramInt2 = 0;
    paramInt1 = k;
    label524:
    if (paramInt2 != 0) {
      super.onMeasure(paramInt1, j);
    }
  }
  
  public void setAttachListener(OnAttachListener paramOnAttachListener)
  {
    mAttachListener = paramOnAttachListener;
  }
  
  public void setDecorPadding(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    mDecorPadding.set(paramInt1, paramInt2, paramInt3, paramInt4);
    if (ViewCompat.isLaidOut(this)) {
      requestLayout();
    }
  }
  
  public static abstract interface OnAttachListener
  {
    public abstract void onAttachedFromWindow();
    
    public abstract void onDetachedFromWindow();
  }
}
