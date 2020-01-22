package androidx.preference.internal;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.ImageView;
import androidx.preference.R.styleable;

public class PreferenceImageView
  extends ImageView
{
  private int mMaxHeight = Integer.MAX_VALUE;
  private int mMaxWidth = Integer.MAX_VALUE;
  
  public PreferenceImageView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PreferenceImageView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public PreferenceImageView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    paramContext = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.PreferenceImageView, paramInt, 0);
    setMaxWidth(paramContext.getDimensionPixelSize(R.styleable.PreferenceImageView_maxWidth, Integer.MAX_VALUE));
    setMaxHeight(paramContext.getDimensionPixelSize(R.styleable.PreferenceImageView_maxHeight, Integer.MAX_VALUE));
    paramContext.recycle();
  }
  
  public int getMaxHeight()
  {
    return mMaxHeight;
  }
  
  public int getMaxWidth()
  {
    return mMaxWidth;
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int j = View.MeasureSpec.getMode(paramInt1);
    int i;
    int k;
    int m;
    if (j != Integer.MIN_VALUE)
    {
      i = paramInt1;
      if (j != 0) {}
    }
    else
    {
      k = View.MeasureSpec.getSize(paramInt1);
      m = getMaxWidth();
      i = paramInt1;
      if (m != Integer.MAX_VALUE) {
        if (m >= k)
        {
          i = paramInt1;
          if (j != 0) {}
        }
        else
        {
          i = View.MeasureSpec.makeMeasureSpec(m, Integer.MIN_VALUE);
        }
      }
    }
    j = View.MeasureSpec.getMode(paramInt2);
    if (j != Integer.MIN_VALUE)
    {
      paramInt1 = paramInt2;
      if (j != 0) {}
    }
    else
    {
      k = View.MeasureSpec.getSize(paramInt2);
      m = getMaxHeight();
      paramInt1 = paramInt2;
      if (m != Integer.MAX_VALUE) {
        if (m >= k)
        {
          paramInt1 = paramInt2;
          if (j != 0) {}
        }
        else
        {
          paramInt1 = View.MeasureSpec.makeMeasureSpec(m, Integer.MIN_VALUE);
        }
      }
    }
    super.onMeasure(i, paramInt1);
  }
  
  public void setMaxHeight(int paramInt)
  {
    mMaxHeight = paramInt;
    super.setMaxHeight(paramInt);
  }
  
  public void setMaxWidth(int paramInt)
  {
    mMaxWidth = paramInt;
    super.setMaxWidth(paramInt);
  }
}
