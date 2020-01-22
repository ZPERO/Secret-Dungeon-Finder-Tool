package com.google.android.material.appbar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior;

class ViewOffsetBehavior<V extends View>
  extends CoordinatorLayout.Behavior<V>
{
  private int tempLeftRightOffset = 0;
  private int tempTopBottomOffset = 0;
  private ViewOffsetHelper viewOffsetHelper;
  
  public ViewOffsetBehavior() {}
  
  public ViewOffsetBehavior(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public int getLeftAndRightOffset()
  {
    ViewOffsetHelper localViewOffsetHelper = viewOffsetHelper;
    if (localViewOffsetHelper != null) {
      return localViewOffsetHelper.getLeftAndRightOffset();
    }
    return 0;
  }
  
  public int getTopAndBottomOffset()
  {
    ViewOffsetHelper localViewOffsetHelper = viewOffsetHelper;
    if (localViewOffsetHelper != null) {
      return localViewOffsetHelper.getTopAndBottomOffset();
    }
    return 0;
  }
  
  protected void layoutChild(CoordinatorLayout paramCoordinatorLayout, View paramView, int paramInt)
  {
    paramCoordinatorLayout.onLayoutChild(paramView, paramInt);
  }
  
  public boolean onLayoutChild(CoordinatorLayout paramCoordinatorLayout, View paramView, int paramInt)
  {
    layoutChild(paramCoordinatorLayout, paramView, paramInt);
    if (viewOffsetHelper == null) {
      viewOffsetHelper = new ViewOffsetHelper(paramView);
    }
    viewOffsetHelper.onViewLayout();
    paramInt = tempTopBottomOffset;
    if (paramInt != 0)
    {
      viewOffsetHelper.setTopAndBottomOffset(paramInt);
      tempTopBottomOffset = 0;
    }
    paramInt = tempLeftRightOffset;
    if (paramInt != 0)
    {
      viewOffsetHelper.setLeftAndRightOffset(paramInt);
      tempLeftRightOffset = 0;
    }
    return true;
  }
  
  public boolean setLeftAndRightOffset(int paramInt)
  {
    ViewOffsetHelper localViewOffsetHelper = viewOffsetHelper;
    if (localViewOffsetHelper != null) {
      return localViewOffsetHelper.setLeftAndRightOffset(paramInt);
    }
    tempLeftRightOffset = paramInt;
    return false;
  }
  
  public boolean setTopAndBottomOffset(int paramInt)
  {
    ViewOffsetHelper localViewOffsetHelper = viewOffsetHelper;
    if (localViewOffsetHelper != null) {
      return localViewOffsetHelper.setTopAndBottomOffset(paramInt);
    }
    tempTopBottomOffset = paramInt;
    return false;
  }
}
