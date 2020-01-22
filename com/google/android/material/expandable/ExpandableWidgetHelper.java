package com.google.android.material.expandable;

import android.os.Bundle;
import android.view.View;
import android.view.ViewParent;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

public final class ExpandableWidgetHelper
{
  private boolean expanded = false;
  private int expandedComponentIdHint = 0;
  private final View widget;
  
  public ExpandableWidgetHelper(ExpandableWidget paramExpandableWidget)
  {
    widget = ((View)paramExpandableWidget);
  }
  
  private void dispatchExpandedStateChanged()
  {
    ViewParent localViewParent = widget.getParent();
    if ((localViewParent instanceof CoordinatorLayout)) {
      ((CoordinatorLayout)localViewParent).dispatchDependentViewsChanged(widget);
    }
  }
  
  public int getExpandedComponentIdHint()
  {
    return expandedComponentIdHint;
  }
  
  public boolean isExpanded()
  {
    return expanded;
  }
  
  public void onRestoreInstanceState(Bundle paramBundle)
  {
    expanded = paramBundle.getBoolean("expanded", false);
    expandedComponentIdHint = paramBundle.getInt("expandedComponentIdHint", 0);
    if (expanded) {
      dispatchExpandedStateChanged();
    }
  }
  
  public Bundle onSaveInstanceState()
  {
    Bundle localBundle = new Bundle();
    localBundle.putBoolean("expanded", expanded);
    localBundle.putInt("expandedComponentIdHint", expandedComponentIdHint);
    return localBundle;
  }
  
  public boolean setExpanded(boolean paramBoolean)
  {
    if (expanded != paramBoolean)
    {
      expanded = paramBoolean;
      dispatchExpandedStateChanged();
      return true;
    }
    return false;
  }
  
  public void setExpandedComponentIdHint(int paramInt)
  {
    expandedComponentIdHint = paramInt;
  }
}
