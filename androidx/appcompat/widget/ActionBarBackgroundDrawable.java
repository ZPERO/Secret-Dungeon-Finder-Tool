package androidx.appcompat.widget;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.drawable.Drawable;

class ActionBarBackgroundDrawable
  extends Drawable
{
  final ActionBarContainer mContainer;
  
  public ActionBarBackgroundDrawable(ActionBarContainer paramActionBarContainer)
  {
    mContainer = paramActionBarContainer;
  }
  
  public void draw(Canvas paramCanvas)
  {
    if (mContainer.mIsSplit)
    {
      if (mContainer.mSplitBackground != null) {
        mContainer.mSplitBackground.draw(paramCanvas);
      }
    }
    else
    {
      if (mContainer.mBackground != null) {
        mContainer.mBackground.draw(paramCanvas);
      }
      if ((mContainer.mStackedBackground != null) && (mContainer.mIsStacked)) {
        mContainer.mStackedBackground.draw(paramCanvas);
      }
    }
  }
  
  public int getOpacity()
  {
    return 0;
  }
  
  public void getOutline(Outline paramOutline)
  {
    if (mContainer.mIsSplit)
    {
      if (mContainer.mSplitBackground != null) {
        mContainer.mSplitBackground.getOutline(paramOutline);
      }
    }
    else if (mContainer.mBackground != null) {
      mContainer.mBackground.getOutline(paramOutline);
    }
  }
  
  public void setAlpha(int paramInt) {}
  
  public void setColorFilter(ColorFilter paramColorFilter) {}
}
