package com.google.android.material.appbar;

import android.view.View;
import androidx.core.view.ViewCompat;

class ViewOffsetHelper
{
  private int layoutLeft;
  private int layoutTop;
  private int offsetLeft;
  private int offsetTop;
  private final View view;
  
  public ViewOffsetHelper(View paramView)
  {
    view = paramView;
  }
  
  private void updateOffsets()
  {
    View localView = view;
    ViewCompat.offsetTopAndBottom(localView, offsetTop - (localView.getTop() - layoutTop));
    localView = view;
    ViewCompat.offsetLeftAndRight(localView, offsetLeft - (localView.getLeft() - layoutLeft));
  }
  
  public int getLayoutLeft()
  {
    return layoutLeft;
  }
  
  public int getLayoutTop()
  {
    return layoutTop;
  }
  
  public int getLeftAndRightOffset()
  {
    return offsetLeft;
  }
  
  public int getTopAndBottomOffset()
  {
    return offsetTop;
  }
  
  public void onViewLayout()
  {
    layoutTop = view.getTop();
    layoutLeft = view.getLeft();
    updateOffsets();
  }
  
  public boolean setLeftAndRightOffset(int paramInt)
  {
    if (offsetLeft != paramInt)
    {
      offsetLeft = paramInt;
      updateOffsets();
      return true;
    }
    return false;
  }
  
  public boolean setTopAndBottomOffset(int paramInt)
  {
    if (offsetTop != paramInt)
    {
      offsetTop = paramInt;
      updateOffsets();
      return true;
    }
    return false;
  }
}
