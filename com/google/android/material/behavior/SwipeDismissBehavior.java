package com.google.android.material.behavior;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior;
import androidx.core.view.ViewCompat;
import androidx.customview.widget.ViewDragHelper;
import androidx.customview.widget.ViewDragHelper.Callback;

public class SwipeDismissBehavior<V extends View>
  extends CoordinatorLayout.Behavior<V>
{
  private static final float DEFAULT_ALPHA_END_DISTANCE = 0.5F;
  private static final float DEFAULT_ALPHA_START_DISTANCE = 0.0F;
  private static final float DEFAULT_DRAG_DISMISS_THRESHOLD = 0.5F;
  public static final int STATE_DRAGGING = 1;
  public static final int STATE_IDLE = 0;
  public static final int STATE_SETTLING = 2;
  public static final int SWIPE_DIRECTION_ANY = 2;
  public static final int SWIPE_DIRECTION_END_TO_START = 1;
  public static final int SWIPE_DIRECTION_START_TO_END = 0;
  float alphaEndSwipeDistance = 0.5F;
  float alphaStartSwipeDistance = 0.0F;
  private final ViewDragHelper.Callback dragCallback = new ViewDragHelper.Callback()
  {
    private static final int INVALID_POINTER_ID = -1;
    private int activePointerId = -1;
    private int originalCapturedViewLeft;
    
    private boolean shouldDismiss(View paramAnonymousView, float paramAnonymousFloat)
    {
      int i;
      if (paramAnonymousFloat != 0.0F)
      {
        if (ViewCompat.getLayoutDirection(paramAnonymousView) == 1) {
          i = 1;
        } else {
          i = 0;
        }
        if (swipeDirection == 2) {
          return true;
        }
        if (swipeDirection == 0)
        {
          if (i != 0 ? paramAnonymousFloat < 0.0F : paramAnonymousFloat > 0.0F) {
            return true;
          }
        }
        else if ((swipeDirection == 1) && (i != 0 ? paramAnonymousFloat > 0.0F : paramAnonymousFloat < 0.0F)) {
          return true;
        }
      }
      else
      {
        i = paramAnonymousView.getLeft();
        int j = originalCapturedViewLeft;
        int k = Math.round(paramAnonymousView.getWidth() * dragDismissThreshold);
        if (Math.abs(i - j) >= k) {
          return true;
        }
      }
      return false;
    }
    
    public int clampViewPositionHorizontal(View paramAnonymousView, int paramAnonymousInt1, int paramAnonymousInt2)
    {
      if (ViewCompat.getLayoutDirection(paramAnonymousView) == 1) {
        paramAnonymousInt2 = 1;
      } else {
        paramAnonymousInt2 = 0;
      }
      int i;
      if (swipeDirection == 0)
      {
        if (paramAnonymousInt2 != 0)
        {
          i = originalCapturedViewLeft - paramAnonymousView.getWidth();
          paramAnonymousInt2 = originalCapturedViewLeft;
        }
        else
        {
          i = originalCapturedViewLeft;
        }
      }
      else
      {
        for (paramAnonymousInt2 = paramAnonymousView.getWidth();; paramAnonymousInt2 = paramAnonymousView.getWidth())
        {
          paramAnonymousInt2 += i;
          break label138;
          if (swipeDirection != 1) {
            break label115;
          }
          if (paramAnonymousInt2 == 0) {
            break;
          }
          i = originalCapturedViewLeft;
        }
        i = originalCapturedViewLeft - paramAnonymousView.getWidth();
        paramAnonymousInt2 = originalCapturedViewLeft;
        break label138;
        label115:
        i = originalCapturedViewLeft - paramAnonymousView.getWidth();
        paramAnonymousInt2 = originalCapturedViewLeft;
        paramAnonymousInt2 = paramAnonymousView.getWidth() + paramAnonymousInt2;
      }
      label138:
      return SwipeDismissBehavior.clamp(i, paramAnonymousInt1, paramAnonymousInt2);
    }
    
    public int clampViewPositionVertical(View paramAnonymousView, int paramAnonymousInt1, int paramAnonymousInt2)
    {
      return paramAnonymousView.getTop();
    }
    
    public int getViewHorizontalDragRange(View paramAnonymousView)
    {
      return paramAnonymousView.getWidth();
    }
    
    public void onViewCaptured(View paramAnonymousView, int paramAnonymousInt)
    {
      activePointerId = paramAnonymousInt;
      originalCapturedViewLeft = paramAnonymousView.getLeft();
      paramAnonymousView = paramAnonymousView.getParent();
      if (paramAnonymousView != null) {
        paramAnonymousView.requestDisallowInterceptTouchEvent(true);
      }
    }
    
    public void onViewDragStateChanged(int paramAnonymousInt)
    {
      if (listener != null) {
        listener.onDragStateChanged(paramAnonymousInt);
      }
    }
    
    public void onViewPositionChanged(View paramAnonymousView, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4)
    {
      float f1 = originalCapturedViewLeft + paramAnonymousView.getWidth() * alphaStartSwipeDistance;
      float f2 = originalCapturedViewLeft + paramAnonymousView.getWidth() * alphaEndSwipeDistance;
      float f3 = paramAnonymousInt1;
      if (f3 <= f1)
      {
        paramAnonymousView.setAlpha(1.0F);
        return;
      }
      if (f3 >= f2)
      {
        paramAnonymousView.setAlpha(0.0F);
        return;
      }
      paramAnonymousView.setAlpha(SwipeDismissBehavior.clamp(0.0F, 1.0F - SwipeDismissBehavior.fraction(f1, f2, f3), 1.0F));
    }
    
    public void onViewReleased(View paramAnonymousView, float paramAnonymousFloat1, float paramAnonymousFloat2)
    {
      activePointerId = -1;
      int i = paramAnonymousView.getWidth();
      boolean bool;
      if (shouldDismiss(paramAnonymousView, paramAnonymousFloat1))
      {
        int j = paramAnonymousView.getLeft();
        int k = originalCapturedViewLeft;
        if (j < k) {
          i = k - i;
        } else {
          i = k + i;
        }
        bool = true;
      }
      else
      {
        i = originalCapturedViewLeft;
        bool = false;
      }
      if (viewDragHelper.settleCapturedViewAt(i, paramAnonymousView.getTop()))
      {
        ViewCompat.postOnAnimation(paramAnonymousView, new SwipeDismissBehavior.SettleRunnable(SwipeDismissBehavior.this, paramAnonymousView, bool));
        return;
      }
      if ((bool) && (listener != null)) {
        listener.onDismiss(paramAnonymousView);
      }
    }
    
    public boolean tryCaptureView(View paramAnonymousView, int paramAnonymousInt)
    {
      return (activePointerId == -1) && (canSwipeDismissView(paramAnonymousView));
    }
  };
  float dragDismissThreshold = 0.5F;
  private boolean interceptingEvents;
  OnDismissListener listener;
  private float sensitivity = 0.0F;
  private boolean sensitivitySet;
  int swipeDirection = 2;
  ViewDragHelper viewDragHelper;
  
  public SwipeDismissBehavior() {}
  
  static float clamp(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    return Math.min(Math.max(paramFloat1, paramFloat2), paramFloat3);
  }
  
  static int clamp(int paramInt1, int paramInt2, int paramInt3)
  {
    return Math.min(Math.max(paramInt1, paramInt2), paramInt3);
  }
  
  private void ensureViewDragHelper(ViewGroup paramViewGroup)
  {
    if (viewDragHelper == null)
    {
      if (sensitivitySet) {
        paramViewGroup = ViewDragHelper.create(paramViewGroup, sensitivity, dragCallback);
      } else {
        paramViewGroup = ViewDragHelper.create(paramViewGroup, dragCallback);
      }
      viewDragHelper = paramViewGroup;
    }
  }
  
  static float fraction(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    return (paramFloat3 - paramFloat1) / (paramFloat2 - paramFloat1);
  }
  
  public boolean canSwipeDismissView(View paramView)
  {
    return true;
  }
  
  public int getDragState()
  {
    ViewDragHelper localViewDragHelper = viewDragHelper;
    if (localViewDragHelper != null) {
      return localViewDragHelper.getViewDragState();
    }
    return 0;
  }
  
  public boolean onInterceptTouchEvent(CoordinatorLayout paramCoordinatorLayout, View paramView, MotionEvent paramMotionEvent)
  {
    boolean bool = interceptingEvents;
    int i = paramMotionEvent.getActionMasked();
    if (i != 0)
    {
      if ((i == 1) || (i == 3)) {
        interceptingEvents = false;
      }
    }
    else
    {
      interceptingEvents = paramCoordinatorLayout.isPointInChildBounds(paramView, (int)paramMotionEvent.getX(), (int)paramMotionEvent.getY());
      bool = interceptingEvents;
    }
    if (bool)
    {
      ensureViewDragHelper(paramCoordinatorLayout);
      return viewDragHelper.shouldInterceptTouchEvent(paramMotionEvent);
    }
    return false;
  }
  
  public boolean onTouchEvent(CoordinatorLayout paramCoordinatorLayout, View paramView, MotionEvent paramMotionEvent)
  {
    paramCoordinatorLayout = viewDragHelper;
    if (paramCoordinatorLayout != null)
    {
      paramCoordinatorLayout.processTouchEvent(paramMotionEvent);
      return true;
    }
    return false;
  }
  
  public void setDragDismissDistance(float paramFloat)
  {
    dragDismissThreshold = clamp(0.0F, paramFloat, 1.0F);
  }
  
  public void setEndAlphaSwipeDistance(float paramFloat)
  {
    alphaEndSwipeDistance = clamp(0.0F, paramFloat, 1.0F);
  }
  
  public void setListener(OnDismissListener paramOnDismissListener)
  {
    listener = paramOnDismissListener;
  }
  
  public void setSensitivity(float paramFloat)
  {
    sensitivity = paramFloat;
    sensitivitySet = true;
  }
  
  public void setStartAlphaSwipeDistance(float paramFloat)
  {
    alphaStartSwipeDistance = clamp(0.0F, paramFloat, 1.0F);
  }
  
  public void setSwipeDirection(int paramInt)
  {
    swipeDirection = paramInt;
  }
  
  public static abstract interface OnDismissListener
  {
    public abstract void onDismiss(View paramView);
    
    public abstract void onDragStateChanged(int paramInt);
  }
  
  private class SettleRunnable
    implements Runnable
  {
    private final boolean dismiss;
    private final View view;
    
    SettleRunnable(View paramView, boolean paramBoolean)
    {
      view = paramView;
      dismiss = paramBoolean;
    }
    
    public void run()
    {
      if ((viewDragHelper != null) && (viewDragHelper.continueSettling(true)))
      {
        ViewCompat.postOnAnimation(view, this);
        return;
      }
      if ((dismiss) && (listener != null)) {
        listener.onDismiss(view);
      }
    }
  }
}
