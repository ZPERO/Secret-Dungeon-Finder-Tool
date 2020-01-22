package com.google.android.material.behavior;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewPropertyAnimator;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior;
import com.google.android.material.animation.AnimationUtils;

public class HideBottomViewOnScrollBehavior<V extends View>
  extends CoordinatorLayout.Behavior<V>
{
  protected static final int ENTER_ANIMATION_DURATION = 225;
  protected static final int EXIT_ANIMATION_DURATION = 175;
  private static final int STATE_SCROLLED_DOWN = 1;
  private static final int STATE_SCROLLED_UP = 2;
  private ViewPropertyAnimator currentAnimator;
  private int currentState = 2;
  private int height = 0;
  
  public HideBottomViewOnScrollBehavior() {}
  
  public HideBottomViewOnScrollBehavior(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  private void animateChildTo(View paramView, int paramInt, long paramLong, TimeInterpolator paramTimeInterpolator)
  {
    currentAnimator = paramView.animate().translationY(paramInt).setInterpolator(paramTimeInterpolator).setDuration(paramLong).setListener(new AnimatorListenerAdapter()
    {
      public void onAnimationEnd(Animator paramAnonymousAnimator)
      {
        HideBottomViewOnScrollBehavior.access$002(HideBottomViewOnScrollBehavior.this, null);
      }
    });
  }
  
  public boolean onLayoutChild(CoordinatorLayout paramCoordinatorLayout, View paramView, int paramInt)
  {
    height = paramView.getMeasuredHeight();
    return super.onLayoutChild(paramCoordinatorLayout, paramView, paramInt);
  }
  
  public void onNestedScroll(CoordinatorLayout paramCoordinatorLayout, View paramView1, View paramView2, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if ((currentState != 1) && (paramInt2 > 0))
    {
      slideDown(paramView1);
      return;
    }
    if ((currentState != 2) && (paramInt2 < 0)) {
      slideUp(paramView1);
    }
  }
  
  public boolean onStartNestedScroll(CoordinatorLayout paramCoordinatorLayout, View paramView1, View paramView2, View paramView3, int paramInt)
  {
    return paramInt == 2;
  }
  
  protected void slideDown(View paramView)
  {
    ViewPropertyAnimator localViewPropertyAnimator = currentAnimator;
    if (localViewPropertyAnimator != null)
    {
      localViewPropertyAnimator.cancel();
      paramView.clearAnimation();
    }
    currentState = 1;
    animateChildTo(paramView, height, 175L, AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR);
  }
  
  protected void slideUp(View paramView)
  {
    ViewPropertyAnimator localViewPropertyAnimator = currentAnimator;
    if (localViewPropertyAnimator != null)
    {
      localViewPropertyAnimator.cancel();
      paramView.clearAnimation();
    }
    currentState = 2;
    animateChildTo(paramView, 0, 225L, AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR);
  }
}
