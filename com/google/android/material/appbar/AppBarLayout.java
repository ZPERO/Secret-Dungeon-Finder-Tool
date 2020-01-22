package com.google.android.material.appbar;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.ClassLoaderCreator;
import android.os.Parcelable.Creator;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior;
import androidx.coordinatorlayout.widget.CoordinatorLayout.DefaultBehavior;
import androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams;
import androidx.core.math.MathUtils;
import androidx.core.util.ObjectsCompat;
import androidx.core.view.NestedScrollingChild;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.customview.view.AbsSavedState;
import com.google.android.material.R.attr;
import com.google.android.material.R.style;
import com.google.android.material.R.styleable;
import com.google.android.material.internal.ThemeEnforcement;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

@CoordinatorLayout.DefaultBehavior(Behavior.class)
public class AppBarLayout
  extends LinearLayout
{
  private static final int INVALID_SCROLL_RANGE = -1;
  static final int PENDING_ACTION_ANIMATE_ENABLED = 4;
  static final int PENDING_ACTION_COLLAPSED = 2;
  static final int PENDING_ACTION_EXPANDED = 1;
  static final int PENDING_ACTION_FORCE = 8;
  static final int PENDING_ACTION_NONE = 0;
  private int downPreScrollRange = -1;
  private int downScrollRange = -1;
  private boolean haveChildWithInterpolator;
  private WindowInsetsCompat lastInsets;
  private boolean liftOnScroll;
  private boolean liftable;
  private boolean liftableOverride;
  private boolean lifted;
  private List<BaseOnOffsetChangedListener> listeners;
  private int pendingAction = 0;
  private int[] tmpStatesArray;
  private int totalScrollRange = -1;
  
  public AppBarLayout(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public AppBarLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    setOrientation(1);
    if (Build.VERSION.SDK_INT >= 21)
    {
      ViewUtilsLollipop.setBoundsViewOutlineProvider(this);
      ViewUtilsLollipop.setStateListAnimatorFromAttrs(this, paramAttributeSet, 0, R.style.Widget_Design_AppBarLayout);
    }
    paramContext = ThemeEnforcement.obtainStyledAttributes(paramContext, paramAttributeSet, R.styleable.AppBarLayout, 0, R.style.Widget_Design_AppBarLayout, new int[0]);
    ViewCompat.setBackground(this, paramContext.getDrawable(R.styleable.AppBarLayout_android_background));
    if (paramContext.hasValue(R.styleable.AppBarLayout_expanded)) {
      setExpanded(paramContext.getBoolean(R.styleable.AppBarLayout_expanded, false), false, false);
    }
    if ((Build.VERSION.SDK_INT >= 21) && (paramContext.hasValue(R.styleable.AppBarLayout_elevation))) {
      ViewUtilsLollipop.setDefaultAppBarLayoutStateListAnimator(this, paramContext.getDimensionPixelSize(R.styleable.AppBarLayout_elevation, 0));
    }
    if (Build.VERSION.SDK_INT >= 26)
    {
      if (paramContext.hasValue(R.styleable.AppBarLayout_android_keyboardNavigationCluster)) {
        setKeyboardNavigationCluster(paramContext.getBoolean(R.styleable.AppBarLayout_android_keyboardNavigationCluster, false));
      }
      if (paramContext.hasValue(R.styleable.AppBarLayout_android_touchscreenBlocksFocus)) {
        setTouchscreenBlocksFocus(paramContext.getBoolean(R.styleable.AppBarLayout_android_touchscreenBlocksFocus, false));
      }
    }
    liftOnScroll = paramContext.getBoolean(R.styleable.AppBarLayout_liftOnScroll, false);
    paramContext.recycle();
    ViewCompat.setOnApplyWindowInsetsListener(this, new OnApplyWindowInsetsListener()
    {
      public WindowInsetsCompat onApplyWindowInsets(View paramAnonymousView, WindowInsetsCompat paramAnonymousWindowInsetsCompat)
      {
        return onWindowInsetChanged(paramAnonymousWindowInsetsCompat);
      }
    });
  }
  
  private boolean hasCollapsibleChild()
  {
    int j = getChildCount();
    int i = 0;
    while (i < j)
    {
      if (((LayoutParams)getChildAt(i).getLayoutParams()).isCollapsible()) {
        return true;
      }
      i += 1;
    }
    return false;
  }
  
  private void invalidateScrollRanges()
  {
    totalScrollRange = -1;
    downPreScrollRange = -1;
    downScrollRange = -1;
  }
  
  private void setExpanded(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    int i;
    if (paramBoolean1) {
      i = 1;
    } else {
      i = 2;
    }
    int k = 0;
    int j;
    if (paramBoolean2) {
      j = 4;
    } else {
      j = 0;
    }
    if (paramBoolean3) {
      k = 8;
    }
    pendingAction = (i | j | k);
    requestLayout();
  }
  
  private boolean setLiftableState(boolean paramBoolean)
  {
    if (liftable != paramBoolean)
    {
      liftable = paramBoolean;
      refreshDrawableState();
      return true;
    }
    return false;
  }
  
  public void addOnOffsetChangedListener(BaseOnOffsetChangedListener paramBaseOnOffsetChangedListener)
  {
    if (listeners == null) {
      listeners = new ArrayList();
    }
    if ((paramBaseOnOffsetChangedListener != null) && (!listeners.contains(paramBaseOnOffsetChangedListener))) {
      listeners.add(paramBaseOnOffsetChangedListener);
    }
  }
  
  public void addOnOffsetChangedListener(OnOffsetChangedListener paramOnOffsetChangedListener)
  {
    addOnOffsetChangedListener(paramOnOffsetChangedListener);
  }
  
  protected boolean checkLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    return paramLayoutParams instanceof LayoutParams;
  }
  
  void dispatchOffsetUpdates(int paramInt)
  {
    Object localObject = listeners;
    if (localObject != null)
    {
      int i = 0;
      int j = ((List)localObject).size();
      while (i < j)
      {
        localObject = (BaseOnOffsetChangedListener)listeners.get(i);
        if (localObject != null) {
          ((BaseOnOffsetChangedListener)localObject).onOffsetChanged(this, paramInt);
        }
        i += 1;
      }
    }
  }
  
  protected LayoutParams generateDefaultLayoutParams()
  {
    return new LayoutParams(-1, -2);
  }
  
  public LayoutParams generateLayoutParams(AttributeSet paramAttributeSet)
  {
    return new LayoutParams(getContext(), paramAttributeSet);
  }
  
  protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    if ((Build.VERSION.SDK_INT >= 19) && ((paramLayoutParams instanceof LinearLayout.LayoutParams))) {
      return new LayoutParams((LinearLayout.LayoutParams)paramLayoutParams);
    }
    if ((paramLayoutParams instanceof ViewGroup.MarginLayoutParams)) {
      return new LayoutParams((ViewGroup.MarginLayoutParams)paramLayoutParams);
    }
    return new LayoutParams(paramLayoutParams);
  }
  
  int getDownNestedPreScrollRange()
  {
    int i = downPreScrollRange;
    if (i != -1) {
      return i;
    }
    int j = getChildCount() - 1;
    for (int k = 0; j >= 0; k = i)
    {
      View localView = getChildAt(j);
      LayoutParams localLayoutParams = (LayoutParams)localView.getLayoutParams();
      int m = localView.getMeasuredHeight();
      i = scrollFlags;
      if ((i & 0x5) == 5)
      {
        k += topMargin + bottomMargin;
        if ((i & 0x8) != 0)
        {
          i = k + ViewCompat.getMinimumHeight(localView);
        }
        else
        {
          if ((i & 0x2) != 0) {
            i = ViewCompat.getMinimumHeight(localView);
          } else {
            i = getTopInset();
          }
          i = k + (m - i);
        }
      }
      else
      {
        i = k;
        if (k > 0) {
          break;
        }
      }
      j -= 1;
    }
    i = Math.max(0, k);
    downPreScrollRange = i;
    return i;
  }
  
  int getDownNestedScrollRange()
  {
    int i = downScrollRange;
    if (i != -1) {
      return i;
    }
    int m = getChildCount();
    int j = 0;
    i = 0;
    int k;
    for (;;)
    {
      k = i;
      if (j >= m) {
        break;
      }
      View localView = getChildAt(j);
      LayoutParams localLayoutParams = (LayoutParams)localView.getLayoutParams();
      int i1 = localView.getMeasuredHeight();
      int i2 = topMargin;
      int i3 = bottomMargin;
      int n = scrollFlags;
      k = i;
      if ((n & 0x1) == 0) {
        break;
      }
      i += i1 + (i2 + i3);
      if ((n & 0x2) != 0)
      {
        k = i - (ViewCompat.getMinimumHeight(localView) + getTopInset());
        break;
      }
      j += 1;
    }
    i = Math.max(0, k);
    downScrollRange = i;
    return i;
  }
  
  public final int getMinimumHeightForVisibleOverlappingContent()
  {
    int k = getTopInset();
    int j = ViewCompat.getMinimumHeight(this);
    int i = j;
    if (j != 0) {}
    do
    {
      return i * 2 + k;
      i = getChildCount();
      if (i >= 1) {
        i = ViewCompat.getMinimumHeight(getChildAt(i - 1));
      } else {
        i = 0;
      }
    } while (i != 0);
    return getHeight() / 3;
  }
  
  int getPendingAction()
  {
    return pendingAction;
  }
  
  public float getTargetElevation()
  {
    return 0.0F;
  }
  
  final int getTopInset()
  {
    WindowInsetsCompat localWindowInsetsCompat = lastInsets;
    if (localWindowInsetsCompat != null) {
      return localWindowInsetsCompat.getSystemWindowInsetTop();
    }
    return 0;
  }
  
  public final int getTotalScrollRange()
  {
    int i = totalScrollRange;
    if (i != -1) {
      return i;
    }
    int m = getChildCount();
    int j = 0;
    i = 0;
    int k;
    for (;;)
    {
      k = i;
      if (j >= m) {
        break;
      }
      View localView = getChildAt(j);
      LayoutParams localLayoutParams = (LayoutParams)localView.getLayoutParams();
      int i1 = localView.getMeasuredHeight();
      int n = scrollFlags;
      k = i;
      if ((n & 0x1) == 0) {
        break;
      }
      i += i1 + topMargin + bottomMargin;
      if ((n & 0x2) != 0)
      {
        k = i - ViewCompat.getMinimumHeight(localView);
        break;
      }
      j += 1;
    }
    i = Math.max(0, k - getTopInset());
    totalScrollRange = i;
    return i;
  }
  
  int getUpNestedPreScrollRange()
  {
    return getTotalScrollRange();
  }
  
  boolean hasChildWithInterpolator()
  {
    return haveChildWithInterpolator;
  }
  
  boolean hasScrollableChildren()
  {
    return getTotalScrollRange() != 0;
  }
  
  public boolean isLiftOnScroll()
  {
    return liftOnScroll;
  }
  
  protected int[] onCreateDrawableState(int paramInt)
  {
    if (tmpStatesArray == null) {
      tmpStatesArray = new int[4];
    }
    int[] arrayOfInt1 = tmpStatesArray;
    int[] arrayOfInt2 = super.onCreateDrawableState(paramInt + arrayOfInt1.length);
    if (liftable) {
      paramInt = R.attr.state_liftable;
    } else {
      paramInt = -R.attr.state_liftable;
    }
    arrayOfInt1[0] = paramInt;
    if ((liftable) && (lifted)) {
      paramInt = R.attr.state_lifted;
    } else {
      paramInt = -R.attr.state_lifted;
    }
    arrayOfInt1[1] = paramInt;
    if (liftable) {
      paramInt = R.attr.state_collapsible;
    } else {
      paramInt = -R.attr.state_collapsible;
    }
    arrayOfInt1[2] = paramInt;
    if ((liftable) && (lifted)) {
      paramInt = R.attr.state_collapsed;
    } else {
      paramInt = -R.attr.state_collapsed;
    }
    arrayOfInt1[3] = paramInt;
    return View.mergeDrawableStates(arrayOfInt2, arrayOfInt1);
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    invalidateScrollRanges();
    paramBoolean = false;
    haveChildWithInterpolator = false;
    paramInt2 = getChildCount();
    paramInt1 = 0;
    while (paramInt1 < paramInt2)
    {
      if (((LayoutParams)getChildAt(paramInt1).getLayoutParams()).getScrollInterpolator() != null)
      {
        haveChildWithInterpolator = true;
        break;
      }
      paramInt1 += 1;
    }
    if (!liftableOverride)
    {
      if ((liftOnScroll) || (hasCollapsibleChild())) {
        paramBoolean = true;
      }
      setLiftableState(paramBoolean);
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt2);
    invalidateScrollRanges();
  }
  
  WindowInsetsCompat onWindowInsetChanged(WindowInsetsCompat paramWindowInsetsCompat)
  {
    WindowInsetsCompat localWindowInsetsCompat;
    if (ViewCompat.getFitsSystemWindows(this)) {
      localWindowInsetsCompat = paramWindowInsetsCompat;
    } else {
      localWindowInsetsCompat = null;
    }
    if (!ObjectsCompat.equals(lastInsets, localWindowInsetsCompat))
    {
      lastInsets = localWindowInsetsCompat;
      invalidateScrollRanges();
    }
    return paramWindowInsetsCompat;
  }
  
  public void removeOnOffsetChangedListener(BaseOnOffsetChangedListener paramBaseOnOffsetChangedListener)
  {
    List localList = listeners;
    if ((localList != null) && (paramBaseOnOffsetChangedListener != null)) {
      localList.remove(paramBaseOnOffsetChangedListener);
    }
  }
  
  public void removeOnOffsetChangedListener(OnOffsetChangedListener paramOnOffsetChangedListener)
  {
    removeOnOffsetChangedListener(paramOnOffsetChangedListener);
  }
  
  void resetPendingAction()
  {
    pendingAction = 0;
  }
  
  public void setExpanded(boolean paramBoolean)
  {
    setExpanded(paramBoolean, ViewCompat.isLaidOut(this));
  }
  
  public void setExpanded(boolean paramBoolean1, boolean paramBoolean2)
  {
    setExpanded(paramBoolean1, paramBoolean2, true);
  }
  
  public void setLiftOnScroll(boolean paramBoolean)
  {
    liftOnScroll = paramBoolean;
  }
  
  public boolean setLiftable(boolean paramBoolean)
  {
    liftableOverride = true;
    return setLiftableState(paramBoolean);
  }
  
  public boolean setLifted(boolean paramBoolean)
  {
    return setLiftedState(paramBoolean);
  }
  
  boolean setLiftedState(boolean paramBoolean)
  {
    if (lifted != paramBoolean)
    {
      lifted = paramBoolean;
      refreshDrawableState();
      return true;
    }
    return false;
  }
  
  public void setOrientation(int paramInt)
  {
    if (paramInt == 1)
    {
      super.setOrientation(paramInt);
      return;
    }
    throw new IllegalArgumentException("AppBarLayout is always vertical and does not support horizontal orientation");
  }
  
  public void setTargetElevation(float paramFloat)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      ViewUtilsLollipop.setDefaultAppBarLayoutStateListAnimator(this, paramFloat);
    }
  }
  
  public void setTouchscreenBlocksFocus(boolean paramBoolean)
  {
    throw new Error("Unresolved compilation error: Method <com.google.android.material.appbar.AppBarLayout: void setTouchscreenBlocksFocus(boolean)> does not exist!");
  }
  
  protected static class BaseBehavior<T extends AppBarLayout>
    extends HeaderBehavior<T>
  {
    private static final int INVALID_POSITION = -1;
    private static final int MAX_OFFSET_ANIMATION_DURATION = 600;
    private WeakReference<View> lastNestedScrollingChildRef;
    private int lastStartedType;
    private ValueAnimator offsetAnimator;
    private int offsetDelta;
    private int offsetToChildIndexOnLayout = -1;
    private boolean offsetToChildIndexOnLayoutIsMinHeight;
    private float offsetToChildIndexOnLayoutPerc;
    private BaseDragCallback onDragCallback;
    
    public BaseBehavior() {}
    
    public BaseBehavior(Context paramContext, AttributeSet paramAttributeSet)
    {
      super(paramAttributeSet);
    }
    
    private void animateOffsetTo(CoordinatorLayout paramCoordinatorLayout, AppBarLayout paramAppBarLayout, int paramInt, float paramFloat)
    {
      int i = Math.abs(getTopBottomOffsetForScrollingSibling() - paramInt);
      paramFloat = Math.abs(paramFloat);
      if (paramFloat > 0.0F) {
        i = Math.round(i / paramFloat * 1000.0F) * 3;
      } else {
        i = (int)((i / paramAppBarLayout.getHeight() + 1.0F) * 150.0F);
      }
      animateOffsetWithDuration(paramCoordinatorLayout, paramAppBarLayout, paramInt, i);
    }
    
    private void animateOffsetWithDuration(final CoordinatorLayout paramCoordinatorLayout, final AppBarLayout paramAppBarLayout, int paramInt1, int paramInt2)
    {
      int i = getTopBottomOffsetForScrollingSibling();
      if (i == paramInt1)
      {
        paramCoordinatorLayout = offsetAnimator;
        if ((paramCoordinatorLayout != null) && (paramCoordinatorLayout.isRunning())) {
          offsetAnimator.cancel();
        }
      }
      else
      {
        ValueAnimator localValueAnimator = offsetAnimator;
        if (localValueAnimator == null)
        {
          offsetAnimator = new ValueAnimator();
          offsetAnimator.setInterpolator(com.google.android.material.animation.AnimationUtils.DECELERATE_INTERPOLATOR);
          offsetAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
          {
            public void onAnimationUpdate(ValueAnimator paramAnonymousValueAnimator)
            {
              setHeaderTopBottomOffset(paramCoordinatorLayout, paramAppBarLayout, ((Integer)paramAnonymousValueAnimator.getAnimatedValue()).intValue());
            }
          });
        }
        else
        {
          localValueAnimator.cancel();
        }
        offsetAnimator.setDuration(Math.min(paramInt2, 600));
        offsetAnimator.setIntValues(new int[] { i, paramInt1 });
        offsetAnimator.start();
      }
    }
    
    private boolean canScrollChildren(CoordinatorLayout paramCoordinatorLayout, AppBarLayout paramAppBarLayout, View paramView)
    {
      return (paramAppBarLayout.hasScrollableChildren()) && (paramCoordinatorLayout.getHeight() - paramView.getHeight() <= paramAppBarLayout.getHeight());
    }
    
    private static boolean checkFlag(int paramInt1, int paramInt2)
    {
      return (paramInt1 & paramInt2) == paramInt2;
    }
    
    private View findFirstScrollingChild(CoordinatorLayout paramCoordinatorLayout)
    {
      int j = paramCoordinatorLayout.getChildCount();
      int i = 0;
      while (i < j)
      {
        View localView = paramCoordinatorLayout.getChildAt(i);
        if ((localView instanceof NestedScrollingChild)) {
          return localView;
        }
        i += 1;
      }
      return null;
    }
    
    private static View getAppBarChildOnOffset(AppBarLayout paramAppBarLayout, int paramInt)
    {
      int i = Math.abs(paramInt);
      int j = paramAppBarLayout.getChildCount();
      paramInt = 0;
      while (paramInt < j)
      {
        View localView = paramAppBarLayout.getChildAt(paramInt);
        if ((i >= localView.getTop()) && (i <= localView.getBottom())) {
          return localView;
        }
        paramInt += 1;
      }
      return null;
    }
    
    private int getChildIndexOnOffset(AppBarLayout paramAppBarLayout, int paramInt)
    {
      int i1 = paramAppBarLayout.getChildCount();
      int i = 0;
      while (i < i1)
      {
        Object localObject = paramAppBarLayout.getChildAt(i);
        int n = ((View)localObject).getTop();
        int j = n;
        int m = ((View)localObject).getBottom();
        int k = m;
        localObject = (AppBarLayout.LayoutParams)((View)localObject).getLayoutParams();
        if (checkFlag(((AppBarLayout.LayoutParams)localObject).getScrollFlags(), 32))
        {
          j = n - topMargin;
          k = m + bottomMargin;
        }
        m = -paramInt;
        if ((j <= m) && (k >= m)) {
          return i;
        }
        i += 1;
      }
      return -1;
    }
    
    private int interpolateOffset(AppBarLayout paramAppBarLayout, int paramInt)
    {
      int k = Math.abs(paramInt);
      int m = paramAppBarLayout.getChildCount();
      int j = 0;
      int i = 0;
      while (i < m)
      {
        View localView = paramAppBarLayout.getChildAt(i);
        AppBarLayout.LayoutParams localLayoutParams = (AppBarLayout.LayoutParams)localView.getLayoutParams();
        Interpolator localInterpolator = localLayoutParams.getScrollInterpolator();
        if ((k >= localView.getTop()) && (k <= localView.getBottom()))
        {
          if (localInterpolator == null) {
            break;
          }
          m = localLayoutParams.getScrollFlags();
          i = j;
          if ((m & 0x1) != 0)
          {
            j = 0 + (localView.getHeight() + topMargin + bottomMargin);
            i = j;
            if ((m & 0x2) != 0) {
              i = j - ViewCompat.getMinimumHeight(localView);
            }
          }
          j = i;
          if (ViewCompat.getFitsSystemWindows(localView)) {
            j = i - paramAppBarLayout.getTopInset();
          }
          if (j <= 0) {
            break;
          }
          i = localView.getTop();
          float f = j;
          i = Math.round(f * localInterpolator.getInterpolation((k - i) / f));
          return Integer.signum(paramInt) * (localView.getTop() + i);
        }
        i += 1;
      }
      return paramInt;
    }
    
    private boolean shouldJumpElevationState(CoordinatorLayout paramCoordinatorLayout, AppBarLayout paramAppBarLayout)
    {
      paramCoordinatorLayout = paramCoordinatorLayout.getDependents(paramAppBarLayout);
      int j = paramCoordinatorLayout.size();
      int i = 0;
      while (i < j)
      {
        paramAppBarLayout = ((CoordinatorLayout.LayoutParams)((View)paramCoordinatorLayout.get(i)).getLayoutParams()).getBehavior();
        if ((paramAppBarLayout instanceof AppBarLayout.ScrollingViewBehavior))
        {
          if (((AppBarLayout.ScrollingViewBehavior)paramAppBarLayout).getOverlayTop() == 0) {
            break;
          }
          return true;
        }
        i += 1;
      }
      return false;
    }
    
    private void snapToChildIfNeeded(CoordinatorLayout paramCoordinatorLayout, AppBarLayout paramAppBarLayout)
    {
      int n = getTopBottomOffsetForScrollingSibling();
      int k = getChildIndexOnOffset(paramAppBarLayout, n);
      if (k >= 0)
      {
        View localView = paramAppBarLayout.getChildAt(k);
        AppBarLayout.LayoutParams localLayoutParams = (AppBarLayout.LayoutParams)localView.getLayoutParams();
        int i1 = localLayoutParams.getScrollFlags();
        if ((i1 & 0x11) == 17)
        {
          int m = -localView.getTop();
          int i = -localView.getBottom();
          int j = i;
          if (k == paramAppBarLayout.getChildCount() - 1) {
            j = i + paramAppBarLayout.getTopInset();
          }
          if (checkFlag(i1, 2))
          {
            i = j + ViewCompat.getMinimumHeight(localView);
            k = m;
          }
          else
          {
            k = m;
            i = j;
            if (checkFlag(i1, 5))
            {
              i = ViewCompat.getMinimumHeight(localView) + j;
              if (n < i)
              {
                k = i;
                i = j;
              }
              else
              {
                k = m;
              }
            }
          }
          m = k;
          j = i;
          if (checkFlag(i1, 32))
          {
            m = k + topMargin;
            j = i - bottomMargin;
          }
          i = m;
          if (n < (j + m) / 2) {
            i = j;
          }
          animateOffsetTo(paramCoordinatorLayout, paramAppBarLayout, MathUtils.clamp(i, -paramAppBarLayout.getTotalScrollRange(), 0), 0.0F);
        }
      }
    }
    
    private void stopNestedScrollIfNeeded(int paramInt1, AppBarLayout paramAppBarLayout, View paramView, int paramInt2)
    {
      if (paramInt2 == 1)
      {
        paramInt2 = getTopBottomOffsetForScrollingSibling();
        if (((paramInt1 < 0) && (paramInt2 == 0)) || ((paramInt1 > 0) && (paramInt2 == -paramAppBarLayout.getDownNestedScrollRange()))) {
          ViewCompat.stopNestedScroll(paramView, 1);
        }
      }
    }
    
    private void updateAppBarLayoutDrawableState(CoordinatorLayout paramCoordinatorLayout, AppBarLayout paramAppBarLayout, int paramInt1, int paramInt2, boolean paramBoolean)
    {
      View localView = getAppBarChildOnOffset(paramAppBarLayout, paramInt1);
      if (localView != null)
      {
        int i = ((AppBarLayout.LayoutParams)localView.getLayoutParams()).getScrollFlags();
        if ((i & 0x1) != 0)
        {
          int j = ViewCompat.getMinimumHeight(localView);
          if ((paramInt2 > 0) && ((i & 0xC) != 0))
          {
            if (-paramInt1 < localView.getBottom() - j - paramAppBarLayout.getTopInset()) {}
          }
          else {
            while (((i & 0x2) != 0) && (-paramInt1 >= localView.getBottom() - j - paramAppBarLayout.getTopInset()))
            {
              bool1 = true;
              break;
            }
          }
        }
        boolean bool1 = false;
        boolean bool2 = bool1;
        if (paramAppBarLayout.isLiftOnScroll())
        {
          localView = findFirstScrollingChild(paramCoordinatorLayout);
          bool2 = bool1;
          if (localView != null) {
            if (localView.getScrollY() > 0) {
              bool2 = true;
            } else {
              bool2 = false;
            }
          }
        }
        bool1 = paramAppBarLayout.setLiftedState(bool2);
        if ((Build.VERSION.SDK_INT >= 11) && ((paramBoolean) || ((bool1) && (shouldJumpElevationState(paramCoordinatorLayout, paramAppBarLayout))))) {
          paramAppBarLayout.jumpDrawablesToCurrentState();
        }
      }
    }
    
    boolean canDragView(AppBarLayout paramAppBarLayout)
    {
      BaseDragCallback localBaseDragCallback = onDragCallback;
      if (localBaseDragCallback != null) {
        return localBaseDragCallback.canDrag(paramAppBarLayout);
      }
      paramAppBarLayout = lastNestedScrollingChildRef;
      if (paramAppBarLayout != null)
      {
        paramAppBarLayout = (View)paramAppBarLayout.get();
        return (paramAppBarLayout != null) && (paramAppBarLayout.isShown()) && (!paramAppBarLayout.canScrollVertically(-1));
      }
      return true;
    }
    
    int getMaxDragOffset(AppBarLayout paramAppBarLayout)
    {
      return -paramAppBarLayout.getDownNestedScrollRange();
    }
    
    int getScrollRangeForDragFling(AppBarLayout paramAppBarLayout)
    {
      return paramAppBarLayout.getTotalScrollRange();
    }
    
    int getTopBottomOffsetForScrollingSibling()
    {
      return getTopAndBottomOffset() + offsetDelta;
    }
    
    boolean isOffsetAnimatorRunning()
    {
      ValueAnimator localValueAnimator = offsetAnimator;
      return (localValueAnimator != null) && (localValueAnimator.isRunning());
    }
    
    void onFlingFinished(CoordinatorLayout paramCoordinatorLayout, AppBarLayout paramAppBarLayout)
    {
      snapToChildIfNeeded(paramCoordinatorLayout, paramAppBarLayout);
    }
    
    public boolean onLayoutChild(CoordinatorLayout paramCoordinatorLayout, AppBarLayout paramAppBarLayout, int paramInt)
    {
      boolean bool = super.onLayoutChild(paramCoordinatorLayout, paramAppBarLayout, paramInt);
      int i = paramAppBarLayout.getPendingAction();
      paramInt = offsetToChildIndexOnLayout;
      if ((paramInt >= 0) && ((i & 0x8) == 0))
      {
        View localView = paramAppBarLayout.getChildAt(paramInt);
        i = -localView.getBottom();
        if (offsetToChildIndexOnLayoutIsMinHeight) {
          paramInt = ViewCompat.getMinimumHeight(localView) + paramAppBarLayout.getTopInset();
        } else {
          paramInt = Math.round(localView.getHeight() * offsetToChildIndexOnLayoutPerc);
        }
        setHeaderTopBottomOffset(paramCoordinatorLayout, paramAppBarLayout, i + paramInt);
      }
      else if (i != 0)
      {
        if ((i & 0x4) != 0) {
          paramInt = 1;
        } else {
          paramInt = 0;
        }
        if ((i & 0x2) != 0)
        {
          i = -paramAppBarLayout.getUpNestedPreScrollRange();
          if (paramInt != 0) {
            animateOffsetTo(paramCoordinatorLayout, paramAppBarLayout, i, 0.0F);
          } else {
            setHeaderTopBottomOffset(paramCoordinatorLayout, paramAppBarLayout, i);
          }
        }
        else if ((i & 0x1) != 0)
        {
          if (paramInt != 0) {
            animateOffsetTo(paramCoordinatorLayout, paramAppBarLayout, 0, 0.0F);
          } else {
            setHeaderTopBottomOffset(paramCoordinatorLayout, paramAppBarLayout, 0);
          }
        }
      }
      paramAppBarLayout.resetPendingAction();
      offsetToChildIndexOnLayout = -1;
      setTopAndBottomOffset(MathUtils.clamp(getTopAndBottomOffset(), -paramAppBarLayout.getTotalScrollRange(), 0));
      updateAppBarLayoutDrawableState(paramCoordinatorLayout, paramAppBarLayout, getTopAndBottomOffset(), 0, true);
      paramAppBarLayout.dispatchOffsetUpdates(getTopAndBottomOffset());
      return bool;
    }
    
    public boolean onMeasureChild(CoordinatorLayout paramCoordinatorLayout, AppBarLayout paramAppBarLayout, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      if (getLayoutParamsheight == -2)
      {
        paramCoordinatorLayout.onMeasureChild(paramAppBarLayout, paramInt1, paramInt2, View.MeasureSpec.makeMeasureSpec(0, 0), paramInt4);
        return true;
      }
      return super.onMeasureChild(paramCoordinatorLayout, paramAppBarLayout, paramInt1, paramInt2, paramInt3, paramInt4);
    }
    
    public void onNestedPreScroll(CoordinatorLayout paramCoordinatorLayout, AppBarLayout paramAppBarLayout, View paramView, int paramInt1, int paramInt2, int[] paramArrayOfInt, int paramInt3)
    {
      if (paramInt2 != 0)
      {
        int i;
        if (paramInt2 < 0)
        {
          i = -paramAppBarLayout.getTotalScrollRange();
          paramInt1 = paramAppBarLayout.getDownNestedPreScrollRange() + i;
        }
        else
        {
          i = -paramAppBarLayout.getUpNestedPreScrollRange();
          paramInt1 = 0;
        }
        if (i != paramInt1)
        {
          paramArrayOfInt[1] = scroll(paramCoordinatorLayout, paramAppBarLayout, paramInt2, i, paramInt1);
          stopNestedScrollIfNeeded(paramInt2, paramAppBarLayout, paramView, paramInt3);
        }
      }
    }
    
    public void onNestedScroll(CoordinatorLayout paramCoordinatorLayout, AppBarLayout paramAppBarLayout, View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
    {
      if (paramInt4 < 0)
      {
        scroll(paramCoordinatorLayout, paramAppBarLayout, paramInt4, -paramAppBarLayout.getDownNestedScrollRange(), 0);
        stopNestedScrollIfNeeded(paramInt4, paramAppBarLayout, paramView, paramInt5);
      }
      if (paramAppBarLayout.isLiftOnScroll())
      {
        boolean bool;
        if (paramView.getScrollY() > 0) {
          bool = true;
        } else {
          bool = false;
        }
        paramAppBarLayout.setLiftedState(bool);
      }
    }
    
    public void onRestoreInstanceState(CoordinatorLayout paramCoordinatorLayout, AppBarLayout paramAppBarLayout, Parcelable paramParcelable)
    {
      if ((paramParcelable instanceof SavedState))
      {
        paramParcelable = (SavedState)paramParcelable;
        super.onRestoreInstanceState(paramCoordinatorLayout, paramAppBarLayout, paramParcelable.getSuperState());
        offsetToChildIndexOnLayout = firstVisibleChildIndex;
        offsetToChildIndexOnLayoutPerc = firstVisibleChildPercentageShown;
        offsetToChildIndexOnLayoutIsMinHeight = firstVisibleChildAtMinimumHeight;
        return;
      }
      super.onRestoreInstanceState(paramCoordinatorLayout, paramAppBarLayout, paramParcelable);
      offsetToChildIndexOnLayout = -1;
    }
    
    public Parcelable onSaveInstanceState(CoordinatorLayout paramCoordinatorLayout, AppBarLayout paramAppBarLayout)
    {
      Object localObject = super.onSaveInstanceState(paramCoordinatorLayout, paramAppBarLayout);
      int j = getTopAndBottomOffset();
      int k = paramAppBarLayout.getChildCount();
      boolean bool = false;
      int i = 0;
      while (i < k)
      {
        paramCoordinatorLayout = paramAppBarLayout.getChildAt(i);
        int m = paramCoordinatorLayout.getBottom() + j;
        if ((paramCoordinatorLayout.getTop() + j <= 0) && (m >= 0))
        {
          localObject = new SavedState((Parcelable)localObject);
          firstVisibleChildIndex = i;
          if (m == ViewCompat.getMinimumHeight(paramCoordinatorLayout) + paramAppBarLayout.getTopInset()) {
            bool = true;
          }
          firstVisibleChildAtMinimumHeight = bool;
          firstVisibleChildPercentageShown = (m / paramCoordinatorLayout.getHeight());
          return localObject;
        }
        i += 1;
      }
      return localObject;
    }
    
    public boolean onStartNestedScroll(CoordinatorLayout paramCoordinatorLayout, AppBarLayout paramAppBarLayout, View paramView1, View paramView2, int paramInt1, int paramInt2)
    {
      boolean bool;
      if (((paramInt1 & 0x2) != 0) && ((paramAppBarLayout.isLiftOnScroll()) || (canScrollChildren(paramCoordinatorLayout, paramAppBarLayout, paramView1)))) {
        bool = true;
      } else {
        bool = false;
      }
      if (bool)
      {
        paramCoordinatorLayout = offsetAnimator;
        if (paramCoordinatorLayout != null) {
          paramCoordinatorLayout.cancel();
        }
      }
      lastNestedScrollingChildRef = null;
      lastStartedType = paramInt2;
      return bool;
    }
    
    public void onStopNestedScroll(CoordinatorLayout paramCoordinatorLayout, AppBarLayout paramAppBarLayout, View paramView, int paramInt)
    {
      if ((lastStartedType == 0) || (paramInt == 1)) {
        snapToChildIfNeeded(paramCoordinatorLayout, paramAppBarLayout);
      }
      lastNestedScrollingChildRef = new WeakReference(paramView);
    }
    
    public void setDragCallback(BaseDragCallback paramBaseDragCallback)
    {
      onDragCallback = paramBaseDragCallback;
    }
    
    int setHeaderTopBottomOffset(CoordinatorLayout paramCoordinatorLayout, AppBarLayout paramAppBarLayout, int paramInt1, int paramInt2, int paramInt3)
    {
      int i = getTopBottomOffsetForScrollingSibling();
      if ((paramInt2 != 0) && (i >= paramInt2) && (i <= paramInt3))
      {
        paramInt2 = MathUtils.clamp(paramInt1, paramInt2, paramInt3);
        if (i != paramInt2)
        {
          if (paramAppBarLayout.hasChildWithInterpolator()) {
            paramInt1 = interpolateOffset(paramAppBarLayout, paramInt2);
          } else {
            paramInt1 = paramInt2;
          }
          boolean bool = setTopAndBottomOffset(paramInt1);
          offsetDelta = (paramInt2 - paramInt1);
          if ((!bool) && (paramAppBarLayout.hasChildWithInterpolator())) {
            paramCoordinatorLayout.dispatchDependentViewsChanged(paramAppBarLayout);
          }
          paramAppBarLayout.dispatchOffsetUpdates(getTopAndBottomOffset());
          if (paramInt2 < i) {
            paramInt1 = -1;
          } else {
            paramInt1 = 1;
          }
          updateAppBarLayoutDrawableState(paramCoordinatorLayout, paramAppBarLayout, paramInt2, paramInt1, false);
          return i - paramInt2;
        }
      }
      else
      {
        offsetDelta = 0;
      }
      return 0;
    }
    
    public static abstract class BaseDragCallback<T extends AppBarLayout>
    {
      public BaseDragCallback() {}
      
      public abstract boolean canDrag(AppBarLayout paramAppBarLayout);
    }
    
    protected static class SavedState
      extends AbsSavedState
    {
      public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator()
      {
        public AppBarLayout.BaseBehavior.SavedState createFromParcel(Parcel paramAnonymousParcel)
        {
          return new AppBarLayout.BaseBehavior.SavedState(paramAnonymousParcel, null);
        }
        
        public AppBarLayout.BaseBehavior.SavedState createFromParcel(Parcel paramAnonymousParcel, ClassLoader paramAnonymousClassLoader)
        {
          return new AppBarLayout.BaseBehavior.SavedState(paramAnonymousParcel, paramAnonymousClassLoader);
        }
        
        public AppBarLayout.BaseBehavior.SavedState[] newArray(int paramAnonymousInt)
        {
          return new AppBarLayout.BaseBehavior.SavedState[paramAnonymousInt];
        }
      };
      boolean firstVisibleChildAtMinimumHeight;
      int firstVisibleChildIndex;
      float firstVisibleChildPercentageShown;
      
      public SavedState(Parcel paramParcel, ClassLoader paramClassLoader)
      {
        super(paramClassLoader);
        firstVisibleChildIndex = paramParcel.readInt();
        firstVisibleChildPercentageShown = paramParcel.readFloat();
        boolean bool;
        if (paramParcel.readByte() != 0) {
          bool = true;
        } else {
          bool = false;
        }
        firstVisibleChildAtMinimumHeight = bool;
      }
      
      public SavedState(Parcelable paramParcelable)
      {
        super();
      }
      
      public void writeToParcel(Parcel paramParcel, int paramInt)
      {
        super.writeToParcel(paramParcel, paramInt);
        paramParcel.writeInt(firstVisibleChildIndex);
        paramParcel.writeFloat(firstVisibleChildPercentageShown);
        paramParcel.writeByte((byte)firstVisibleChildAtMinimumHeight);
      }
    }
  }
  
  public static abstract interface BaseOnOffsetChangedListener<T extends AppBarLayout>
  {
    public abstract void onOffsetChanged(AppBarLayout paramAppBarLayout, int paramInt);
  }
  
  public static class Behavior
    extends AppBarLayout.BaseBehavior<AppBarLayout>
  {
    public Behavior() {}
    
    public Behavior(Context paramContext, AttributeSet paramAttributeSet)
    {
      super(paramAttributeSet);
    }
    
    public static abstract class DragCallback
      extends AppBarLayout.BaseBehavior.BaseDragCallback<AppBarLayout>
    {
      public DragCallback() {}
    }
  }
  
  public static class LayoutParams
    extends LinearLayout.LayoutParams
  {
    static final int COLLAPSIBLE_FLAGS = 10;
    static final int FLAG_QUICK_RETURN = 5;
    static final int FLAG_SNAP = 17;
    public static final int SCROLL_FLAG_ENTER_ALWAYS = 4;
    public static final int SCROLL_FLAG_ENTER_ALWAYS_COLLAPSED = 8;
    public static final int SCROLL_FLAG_EXIT_UNTIL_COLLAPSED = 2;
    public static final int SCROLL_FLAG_SCROLL = 1;
    public static final int SCROLL_FLAG_SNAP = 16;
    public static final int SCROLL_FLAG_SNAP_MARGINS = 32;
    int scrollFlags = 1;
    Interpolator scrollInterpolator;
    
    public LayoutParams(int paramInt1, int paramInt2)
    {
      super(paramInt2);
    }
    
    public LayoutParams(int paramInt1, int paramInt2, float paramFloat)
    {
      super(paramInt2, paramFloat);
    }
    
    public LayoutParams(Context paramContext, AttributeSet paramAttributeSet)
    {
      super(paramAttributeSet);
      paramAttributeSet = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.AppBarLayout_Layout);
      scrollFlags = paramAttributeSet.getInt(R.styleable.AppBarLayout_Layout_layout_scrollFlags, 0);
      if (paramAttributeSet.hasValue(R.styleable.AppBarLayout_Layout_layout_scrollInterpolator)) {
        scrollInterpolator = android.view.animation.AnimationUtils.loadInterpolator(paramContext, paramAttributeSet.getResourceId(R.styleable.AppBarLayout_Layout_layout_scrollInterpolator, 0));
      }
      paramAttributeSet.recycle();
    }
    
    public LayoutParams(ViewGroup.LayoutParams paramLayoutParams)
    {
      super();
    }
    
    public LayoutParams(ViewGroup.MarginLayoutParams paramMarginLayoutParams)
    {
      super();
    }
    
    public LayoutParams(LinearLayout.LayoutParams paramLayoutParams)
    {
      super();
    }
    
    public LayoutParams(LayoutParams paramLayoutParams)
    {
      super();
      scrollFlags = scrollFlags;
      scrollInterpolator = scrollInterpolator;
    }
    
    public int getScrollFlags()
    {
      return scrollFlags;
    }
    
    public Interpolator getScrollInterpolator()
    {
      return scrollInterpolator;
    }
    
    boolean isCollapsible()
    {
      int i = scrollFlags;
      return ((i & 0x1) == 1) && ((i & 0xA) != 0);
    }
    
    public void setScrollFlags(int paramInt)
    {
      scrollFlags = paramInt;
    }
    
    public void setScrollInterpolator(Interpolator paramInterpolator)
    {
      scrollInterpolator = paramInterpolator;
    }
    
    @Retention(RetentionPolicy.SOURCE)
    public static @interface ScrollFlags {}
  }
  
  public static abstract interface OnOffsetChangedListener
    extends AppBarLayout.BaseOnOffsetChangedListener<AppBarLayout>
  {
    public abstract void onOffsetChanged(AppBarLayout paramAppBarLayout, int paramInt);
  }
  
  public static class ScrollingViewBehavior
    extends HeaderScrollingViewBehavior
  {
    public ScrollingViewBehavior() {}
    
    public ScrollingViewBehavior(Context paramContext, AttributeSet paramAttributeSet)
    {
      super(paramAttributeSet);
      paramContext = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.ScrollingViewBehavior_Layout);
      setOverlayTop(paramContext.getDimensionPixelSize(R.styleable.ScrollingViewBehavior_Layout_behavior_overlapTop, 0));
      paramContext.recycle();
    }
    
    private static int getAppBarLayoutOffset(AppBarLayout paramAppBarLayout)
    {
      paramAppBarLayout = ((CoordinatorLayout.LayoutParams)paramAppBarLayout.getLayoutParams()).getBehavior();
      if ((paramAppBarLayout instanceof AppBarLayout.BaseBehavior)) {
        return ((AppBarLayout.BaseBehavior)paramAppBarLayout).getTopBottomOffsetForScrollingSibling();
      }
      return 0;
    }
    
    private void offsetChildAsNeeded(View paramView1, View paramView2)
    {
      Object localObject = ((CoordinatorLayout.LayoutParams)paramView2.getLayoutParams()).getBehavior();
      if ((localObject instanceof AppBarLayout.BaseBehavior))
      {
        localObject = (AppBarLayout.BaseBehavior)localObject;
        ViewCompat.offsetTopAndBottom(paramView1, paramView2.getBottom() - paramView1.getTop() + offsetDelta + getVerticalLayoutGap() - getOverlapPixelsForOffset(paramView2));
      }
    }
    
    private void updateLiftedStateIfNeeded(View paramView1, View paramView2)
    {
      if ((paramView2 instanceof AppBarLayout))
      {
        paramView2 = (AppBarLayout)paramView2;
        if (paramView2.isLiftOnScroll())
        {
          boolean bool;
          if (paramView1.getScrollY() > 0) {
            bool = true;
          } else {
            bool = false;
          }
          paramView2.setLiftedState(bool);
        }
      }
    }
    
    AppBarLayout findFirstDependency(List paramList)
    {
      int j = paramList.size();
      int i = 0;
      while (i < j)
      {
        View localView = (View)paramList.get(i);
        if ((localView instanceof AppBarLayout)) {
          return (AppBarLayout)localView;
        }
        i += 1;
      }
      return null;
    }
    
    float getOverlapRatioForOffset(View paramView)
    {
      if ((paramView instanceof AppBarLayout))
      {
        paramView = (AppBarLayout)paramView;
        int j = paramView.getTotalScrollRange();
        int k = paramView.getDownNestedPreScrollRange();
        int i = getAppBarLayoutOffset(paramView);
        if ((k != 0) && (j + i <= k)) {
          return 0.0F;
        }
        j -= k;
        if (j != 0) {
          return i / j + 1.0F;
        }
      }
      return 0.0F;
    }
    
    int getScrollRange(View paramView)
    {
      if ((paramView instanceof AppBarLayout)) {
        return ((AppBarLayout)paramView).getTotalScrollRange();
      }
      return super.getScrollRange(paramView);
    }
    
    public boolean layoutDependsOn(CoordinatorLayout paramCoordinatorLayout, View paramView1, View paramView2)
    {
      return paramView2 instanceof AppBarLayout;
    }
    
    public boolean onDependentViewChanged(CoordinatorLayout paramCoordinatorLayout, View paramView1, View paramView2)
    {
      offsetChildAsNeeded(paramView1, paramView2);
      updateLiftedStateIfNeeded(paramView1, paramView2);
      return false;
    }
    
    public boolean onRequestChildRectangleOnScreen(CoordinatorLayout paramCoordinatorLayout, View paramView, Rect paramRect, boolean paramBoolean)
    {
      AppBarLayout localAppBarLayout = findFirstDependency(paramCoordinatorLayout.getDependencies(paramView));
      if (localAppBarLayout != null)
      {
        paramRect.offset(paramView.getLeft(), paramView.getTop());
        paramView = tempRect1;
        paramView.set(0, 0, paramCoordinatorLayout.getWidth(), paramCoordinatorLayout.getHeight());
        if (!paramView.contains(paramRect))
        {
          localAppBarLayout.setExpanded(false, paramBoolean ^ true);
          return true;
        }
      }
      return false;
    }
  }
}
