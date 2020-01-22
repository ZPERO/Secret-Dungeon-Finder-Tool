package com.google.android.material.transformation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior;
import androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams;
import androidx.core.view.ViewCompat;
import com.google.android.material.expandable.ExpandableWidget;
import java.util.List;

public abstract class ExpandableBehavior
  extends CoordinatorLayout.Behavior<View>
{
  private static final int STATE_COLLAPSED = 2;
  private static final int STATE_EXPANDED = 1;
  private static final int STATE_UNINITIALIZED = 0;
  private int currentState = 0;
  
  public ExpandableBehavior() {}
  
  public ExpandableBehavior(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  private boolean didStateChange(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      int i = currentState;
      if ((i == 0) || (i == 2)) {
        return true;
      }
    }
    else if (currentState == 1)
    {
      return true;
    }
    return false;
  }
  
  public static ExpandableBehavior from(View paramView, Class paramClass)
  {
    paramView = paramView.getLayoutParams();
    if ((paramView instanceof CoordinatorLayout.LayoutParams))
    {
      paramView = ((CoordinatorLayout.LayoutParams)paramView).getBehavior();
      if ((paramView instanceof ExpandableBehavior)) {
        return (ExpandableBehavior)paramClass.cast(paramView);
      }
      throw new IllegalArgumentException("The view is not associated with ExpandableBehavior");
    }
    throw new IllegalArgumentException("The view is not a child of CoordinatorLayout");
  }
  
  protected ExpandableWidget findExpandableWidget(CoordinatorLayout paramCoordinatorLayout, View paramView)
  {
    List localList = paramCoordinatorLayout.getDependencies(paramView);
    int j = localList.size();
    int i = 0;
    while (i < j)
    {
      View localView = (View)localList.get(i);
      if (layoutDependsOn(paramCoordinatorLayout, paramView, localView)) {
        return (ExpandableWidget)localView;
      }
      i += 1;
    }
    return null;
  }
  
  public abstract boolean layoutDependsOn(CoordinatorLayout paramCoordinatorLayout, View paramView1, View paramView2);
  
  public boolean onDependentViewChanged(CoordinatorLayout paramCoordinatorLayout, View paramView1, View paramView2)
  {
    paramCoordinatorLayout = (ExpandableWidget)paramView2;
    if (didStateChange(paramCoordinatorLayout.isExpanded()))
    {
      int i;
      if (paramCoordinatorLayout.isExpanded()) {
        i = 1;
      } else {
        i = 2;
      }
      currentState = i;
      return onExpandedStateChange((View)paramCoordinatorLayout, paramView1, paramCoordinatorLayout.isExpanded(), true);
    }
    return false;
  }
  
  protected abstract boolean onExpandedStateChange(View paramView1, View paramView2, boolean paramBoolean1, boolean paramBoolean2);
  
  public boolean onLayoutChild(final CoordinatorLayout paramCoordinatorLayout, final View paramView, final int paramInt)
  {
    if (!ViewCompat.isLaidOut(paramView))
    {
      paramCoordinatorLayout = findExpandableWidget(paramCoordinatorLayout, paramView);
      if ((paramCoordinatorLayout != null) && (didStateChange(paramCoordinatorLayout.isExpanded())))
      {
        if (paramCoordinatorLayout.isExpanded()) {
          paramInt = 1;
        } else {
          paramInt = 2;
        }
        currentState = paramInt;
        paramInt = currentState;
        paramView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener()
        {
          public boolean onPreDraw()
          {
            paramView.getViewTreeObserver().removeOnPreDrawListener(this);
            if (currentState == paramInt)
            {
              ExpandableBehavior localExpandableBehavior = ExpandableBehavior.this;
              ExpandableWidget localExpandableWidget = paramCoordinatorLayout;
              localExpandableBehavior.onExpandedStateChange((View)localExpandableWidget, paramView, localExpandableWidget.isExpanded(), false);
            }
            return false;
          }
        });
      }
    }
    return false;
  }
}
