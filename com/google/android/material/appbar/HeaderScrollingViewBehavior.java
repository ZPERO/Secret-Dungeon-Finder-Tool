package com.google.android.material.appbar;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams;
import androidx.core.math.MathUtils;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.List;

abstract class HeaderScrollingViewBehavior
  extends ViewOffsetBehavior<View>
{
  private int overlayTop;
  final Rect tempRect1 = new Rect();
  final Rect tempRect2 = new Rect();
  private int verticalLayoutGap = 0;
  
  public HeaderScrollingViewBehavior() {}
  
  public HeaderScrollingViewBehavior(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  private static int resolveGravity(int paramInt)
  {
    if (paramInt == 0) {
      return 8388659;
    }
    return paramInt;
  }
  
  abstract View findFirstDependency(List paramList);
  
  final int getOverlapPixelsForOffset(View paramView)
  {
    if (overlayTop == 0) {
      return 0;
    }
    float f = getOverlapRatioForOffset(paramView);
    int i = overlayTop;
    return MathUtils.clamp((int)(f * i), 0, i);
  }
  
  float getOverlapRatioForOffset(View paramView)
  {
    return 1.0F;
  }
  
  public final int getOverlayTop()
  {
    return overlayTop;
  }
  
  int getScrollRange(View paramView)
  {
    return paramView.getMeasuredHeight();
  }
  
  final int getVerticalLayoutGap()
  {
    return verticalLayoutGap;
  }
  
  protected void layoutChild(CoordinatorLayout paramCoordinatorLayout, View paramView, int paramInt)
  {
    View localView = findFirstDependency(paramCoordinatorLayout.getDependencies(paramView));
    if (localView != null)
    {
      CoordinatorLayout.LayoutParams localLayoutParams = (CoordinatorLayout.LayoutParams)paramView.getLayoutParams();
      Rect localRect = tempRect1;
      localRect.set(paramCoordinatorLayout.getPaddingLeft() + leftMargin, localView.getBottom() + topMargin, paramCoordinatorLayout.getWidth() - paramCoordinatorLayout.getPaddingRight() - rightMargin, paramCoordinatorLayout.getHeight() + localView.getBottom() - paramCoordinatorLayout.getPaddingBottom() - bottomMargin);
      WindowInsetsCompat localWindowInsetsCompat = paramCoordinatorLayout.getLastWindowInsets();
      if ((localWindowInsetsCompat != null) && (ViewCompat.getFitsSystemWindows(paramCoordinatorLayout)) && (!ViewCompat.getFitsSystemWindows(paramView)))
      {
        left += localWindowInsetsCompat.getSystemWindowInsetLeft();
        right -= localWindowInsetsCompat.getSystemWindowInsetRight();
      }
      paramCoordinatorLayout = tempRect2;
      GravityCompat.apply(resolveGravity(gravity), paramView.getMeasuredWidth(), paramView.getMeasuredHeight(), localRect, paramCoordinatorLayout, paramInt);
      paramInt = getOverlapPixelsForOffset(localView);
      paramView.layout(left, top - paramInt, right, bottom - paramInt);
      verticalLayoutGap = (top - localView.getBottom());
      return;
    }
    super.layoutChild(paramCoordinatorLayout, paramView, paramInt);
    verticalLayoutGap = 0;
  }
  
  public boolean onMeasureChild(CoordinatorLayout paramCoordinatorLayout, View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int j = getLayoutParamsheight;
    if ((j == -1) || (j == -2))
    {
      View localView = findFirstDependency(paramCoordinatorLayout.getDependencies(paramView));
      if (localView != null)
      {
        if ((ViewCompat.getFitsSystemWindows(localView)) && (!ViewCompat.getFitsSystemWindows(paramView)))
        {
          ViewCompat.setFitsSystemWindows(paramView, true);
          if (ViewCompat.getFitsSystemWindows(paramView))
          {
            paramView.requestLayout();
            return true;
          }
        }
        int i = View.MeasureSpec.getSize(paramInt3);
        paramInt3 = i;
        if (i == 0) {
          paramInt3 = paramCoordinatorLayout.getHeight();
        }
        int k = localView.getMeasuredHeight();
        int m = getScrollRange(localView);
        if (j == -1) {
          i = 1073741824;
        } else {
          i = Integer.MIN_VALUE;
        }
        paramCoordinatorLayout.onMeasureChild(paramView, paramInt1, paramInt2, View.MeasureSpec.makeMeasureSpec(paramInt3 - k + m, i), paramInt4);
        return true;
      }
    }
    return false;
  }
  
  public final void setOverlayTop(int paramInt)
  {
    overlayTop = paramInt;
  }
}
