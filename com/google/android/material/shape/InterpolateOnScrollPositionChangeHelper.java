package com.google.android.material.shape;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.widget.ScrollView;

public class InterpolateOnScrollPositionChangeHelper
{
  private final int[] containerLocation = new int[2];
  private ScrollView containingScrollView;
  private MaterialShapeDrawable materialShapeDrawable;
  private final ViewTreeObserver.OnScrollChangedListener scrollChangedListener = new ViewTreeObserver.OnScrollChangedListener()
  {
    public void onScrollChanged()
    {
      updateInterpolationForScreenPosition();
    }
  };
  private final int[] scrollLocation = new int[2];
  private View shapedView;
  
  public InterpolateOnScrollPositionChangeHelper(View paramView, MaterialShapeDrawable paramMaterialShapeDrawable, ScrollView paramScrollView)
  {
    shapedView = paramView;
    materialShapeDrawable = paramMaterialShapeDrawable;
    containingScrollView = paramScrollView;
  }
  
  public void setContainingScrollView(ScrollView paramScrollView)
  {
    containingScrollView = paramScrollView;
  }
  
  public void setMaterialShapeDrawable(MaterialShapeDrawable paramMaterialShapeDrawable)
  {
    materialShapeDrawable = paramMaterialShapeDrawable;
  }
  
  public void startListeningForScrollChanges(ViewTreeObserver paramViewTreeObserver)
  {
    paramViewTreeObserver.addOnScrollChangedListener(scrollChangedListener);
  }
  
  public void stopListeningForScrollChanges(ViewTreeObserver paramViewTreeObserver)
  {
    paramViewTreeObserver.removeOnScrollChangedListener(scrollChangedListener);
  }
  
  public void updateInterpolationForScreenPosition()
  {
    ScrollView localScrollView = containingScrollView;
    if (localScrollView == null) {
      return;
    }
    if (localScrollView.getChildCount() != 0)
    {
      containingScrollView.getLocationInWindow(scrollLocation);
      containingScrollView.getChildAt(0).getLocationInWindow(containerLocation);
      int k = shapedView.getTop() - scrollLocation[1] + containerLocation[1];
      int i = shapedView.getHeight();
      int j = containingScrollView.getHeight();
      if (k < 0)
      {
        materialShapeDrawable.setInterpolation(Math.max(0.0F, Math.min(1.0F, k / i + 1.0F)));
        shapedView.invalidate();
        return;
      }
      k += i;
      if (k > j)
      {
        materialShapeDrawable.setInterpolation(Math.max(0.0F, Math.min(1.0F, 1.0F - (k - j) / i)));
        shapedView.invalidate();
        return;
      }
      if (materialShapeDrawable.getInterpolation() != 1.0F)
      {
        materialShapeDrawable.setInterpolation(1.0F);
        shapedView.invalidate();
      }
    }
    else
    {
      throw new IllegalStateException("Scroll bar must contain a child to calculate interpolation.");
    }
  }
}
