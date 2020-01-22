package com.google.android.material.tabs;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build.VERSION;
import android.text.Layout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityRecord;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar.Tab;
import androidx.appcompat.content.wiki.AppCompatResources;
import androidx.appcompat.widget.TooltipCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.util.Pools.Pool;
import androidx.core.util.Pools.SimplePool;
import androidx.core.util.Pools.SynchronizedPool;
import androidx.core.view.MarginLayoutParamsCompat;
import androidx.core.view.PointerIconCompat;
import androidx.core.view.ViewCompat;
import androidx.core.widget.TextViewCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.DecorView;
import androidx.viewpager.widget.ViewPager.OnAdapterChangeListener;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;
import com.google.android.material.R.attr;
import com.google.android.material.R.dimen;
import com.google.android.material.R.layout;
import com.google.android.material.R.style;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialResources;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;

@ViewPager.DecorView
public class TabLayout
  extends HorizontalScrollView
{
  private static final int ANIMATION_DURATION = 300;
  static final int DEFAULT_GAP_TEXT_ICON = 8;
  private static final int DEFAULT_HEIGHT = 48;
  private static final int DEFAULT_HEIGHT_WITH_TEXT_ICON = 72;
  static final int FIXED_WRAP_GUTTER_MIN = 16;
  public static final int GRAVITY_CENTER = 1;
  public static final int GRAVITY_FILL = 0;
  public static final int INDICATOR_GRAVITY_BOTTOM = 0;
  public static final int INDICATOR_GRAVITY_CENTER = 1;
  public static final int INDICATOR_GRAVITY_STRETCH = 3;
  public static final int INDICATOR_GRAVITY_TOP = 2;
  private static final int INVALID_WIDTH = -1;
  private static final int MIN_INDICATOR_WIDTH = 24;
  public static final int MODE_FIXED = 1;
  public static final int MODE_SCROLLABLE = 0;
  private static final int TAB_MIN_WIDTH_MARGIN = 56;
  private static final Pools.Pool<Tab> tabPool = new Pools.SynchronizedPool(16);
  private AdapterChangeListener adapterChangeListener;
  private int contentInsetStart;
  private BaseOnTabSelectedListener currentVpSelectedListener;
  boolean inlineLabel;
  int mode;
  private TabLayoutOnPageChangeListener pageChangeListener;
  private PagerAdapter pagerAdapter;
  private DataSetObserver pagerAdapterObserver;
  private final int requestedTabMaxWidth;
  private final int requestedTabMinWidth;
  private ValueAnimator scrollAnimator;
  private final int scrollableTabMinWidth;
  private BaseOnTabSelectedListener selectedListener;
  private final ArrayList<BaseOnTabSelectedListener> selectedListeners = new ArrayList();
  private Tab selectedTab;
  private boolean setupViewPagerImplicitly;
  private final SlidingTabIndicator slidingTabIndicator;
  final int tabBackgroundResId;
  int tabGravity;
  ColorStateList tabIconTint;
  PorterDuff.Mode tabIconTintMode;
  int tabIndicatorAnimationDuration;
  boolean tabIndicatorFullWidth;
  int tabIndicatorGravity;
  int tabMaxWidth = Integer.MAX_VALUE;
  int tabPaddingBottom;
  int tabPaddingEnd;
  int tabPaddingStart;
  int tabPaddingTop;
  ColorStateList tabRippleColorStateList;
  Drawable tabSelectedIndicator;
  int tabTextAppearance;
  ColorStateList tabTextColors;
  float tabTextMultiLineSize;
  float tabTextSize;
  private final RectF tabViewContentBounds = new RectF();
  private final Pools.Pool<TabView> tabViewPool = new Pools.SimplePool(12);
  private final ArrayList<Tab> tabs = new ArrayList();
  boolean unboundedRipple;
  ViewPager viewPager;
  
  public TabLayout(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public TabLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, R.attr.tabStyle);
  }
  
  public TabLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    setHorizontalScrollBarEnabled(false);
    slidingTabIndicator = new SlidingTabIndicator(paramContext);
    super.addView(slidingTabIndicator, 0, new FrameLayout.LayoutParams(-2, -1));
    TypedArray localTypedArray = ThemeEnforcement.obtainStyledAttributes(paramContext, paramAttributeSet, com.google.android.material.R.styleable.TabLayout, paramInt, R.style.Widget_Design_TabLayout, new int[] { com.google.android.material.R.styleable.TabLayout_tabTextAppearance });
    slidingTabIndicator.setSelectedIndicatorHeight(localTypedArray.getDimensionPixelSize(com.google.android.material.R.styleable.TabLayout_tabIndicatorHeight, -1));
    slidingTabIndicator.setSelectedIndicatorColor(localTypedArray.getColor(com.google.android.material.R.styleable.TabLayout_tabIndicatorColor, 0));
    setSelectedTabIndicator(MaterialResources.getDrawable(paramContext, localTypedArray, com.google.android.material.R.styleable.TabLayout_tabIndicator));
    setSelectedTabIndicatorGravity(localTypedArray.getInt(com.google.android.material.R.styleable.TabLayout_tabIndicatorGravity, 0));
    setTabIndicatorFullWidth(localTypedArray.getBoolean(com.google.android.material.R.styleable.TabLayout_tabIndicatorFullWidth, true));
    paramInt = localTypedArray.getDimensionPixelSize(com.google.android.material.R.styleable.TabLayout_tabPadding, 0);
    tabPaddingBottom = paramInt;
    tabPaddingEnd = paramInt;
    tabPaddingTop = paramInt;
    tabPaddingStart = paramInt;
    tabPaddingStart = localTypedArray.getDimensionPixelSize(com.google.android.material.R.styleable.TabLayout_tabPaddingStart, tabPaddingStart);
    tabPaddingTop = localTypedArray.getDimensionPixelSize(com.google.android.material.R.styleable.TabLayout_tabPaddingTop, tabPaddingTop);
    tabPaddingEnd = localTypedArray.getDimensionPixelSize(com.google.android.material.R.styleable.TabLayout_tabPaddingEnd, tabPaddingEnd);
    tabPaddingBottom = localTypedArray.getDimensionPixelSize(com.google.android.material.R.styleable.TabLayout_tabPaddingBottom, tabPaddingBottom);
    tabTextAppearance = localTypedArray.getResourceId(com.google.android.material.R.styleable.TabLayout_tabTextAppearance, R.style.TextAppearance_Design_Tab);
    paramAttributeSet = paramContext.obtainStyledAttributes(tabTextAppearance, androidx.appcompat.R.styleable.TextAppearance);
    try
    {
      tabTextSize = paramAttributeSet.getDimensionPixelSize(androidx.appcompat.R.styleable.TextAppearance_android_textSize, 0);
      tabTextColors = MaterialResources.getColorStateList(paramContext, paramAttributeSet, androidx.appcompat.R.styleable.TextAppearance_android_textColor);
      paramAttributeSet.recycle();
      if (localTypedArray.hasValue(com.google.android.material.R.styleable.TabLayout_tabTextColor)) {
        tabTextColors = MaterialResources.getColorStateList(paramContext, localTypedArray, com.google.android.material.R.styleable.TabLayout_tabTextColor);
      }
      if (localTypedArray.hasValue(com.google.android.material.R.styleable.TabLayout_tabSelectedTextColor))
      {
        paramInt = localTypedArray.getColor(com.google.android.material.R.styleable.TabLayout_tabSelectedTextColor, 0);
        tabTextColors = createColorStateList(tabTextColors.getDefaultColor(), paramInt);
      }
      tabIconTint = MaterialResources.getColorStateList(paramContext, localTypedArray, com.google.android.material.R.styleable.TabLayout_tabIconTint);
      tabIconTintMode = ViewUtils.parseTintMode(localTypedArray.getInt(com.google.android.material.R.styleable.TabLayout_tabIconTintMode, -1), null);
      tabRippleColorStateList = MaterialResources.getColorStateList(paramContext, localTypedArray, com.google.android.material.R.styleable.TabLayout_tabRippleColor);
      tabIndicatorAnimationDuration = localTypedArray.getInt(com.google.android.material.R.styleable.TabLayout_tabIndicatorAnimationDuration, 300);
      requestedTabMinWidth = localTypedArray.getDimensionPixelSize(com.google.android.material.R.styleable.TabLayout_tabMinWidth, -1);
      requestedTabMaxWidth = localTypedArray.getDimensionPixelSize(com.google.android.material.R.styleable.TabLayout_tabMaxWidth, -1);
      tabBackgroundResId = localTypedArray.getResourceId(com.google.android.material.R.styleable.TabLayout_tabBackground, 0);
      contentInsetStart = localTypedArray.getDimensionPixelSize(com.google.android.material.R.styleable.TabLayout_tabContentStart, 0);
      mode = localTypedArray.getInt(com.google.android.material.R.styleable.TabLayout_tabMode, 1);
      tabGravity = localTypedArray.getInt(com.google.android.material.R.styleable.TabLayout_tabGravity, 0);
      inlineLabel = localTypedArray.getBoolean(com.google.android.material.R.styleable.TabLayout_tabInlineLabel, false);
      unboundedRipple = localTypedArray.getBoolean(com.google.android.material.R.styleable.TabLayout_tabUnboundedRipple, false);
      localTypedArray.recycle();
      paramContext = getResources();
      tabTextMultiLineSize = paramContext.getDimensionPixelSize(R.dimen.design_tab_text_size_2line);
      scrollableTabMinWidth = paramContext.getDimensionPixelSize(R.dimen.design_tab_scrollable_min_width);
      applyModeAndGravity();
      return;
    }
    catch (Throwable paramContext)
    {
      paramAttributeSet.recycle();
      throw paramContext;
    }
  }
  
  private void addTabFromItemView(TabItem paramTabItem)
  {
    Tab localTab = newTab();
    if (text != null) {
      localTab.setText(text);
    }
    if (icon != null) {
      localTab.setIcon(icon);
    }
    if (customLayout != 0) {
      localTab.setCustomView(customLayout);
    }
    if (!TextUtils.isEmpty(paramTabItem.getContentDescription())) {
      localTab.setContentDescription(paramTabItem.getContentDescription());
    }
    addTab(localTab);
  }
  
  private void addTabView(Tab paramTab)
  {
    TabView localTabView = view;
    slidingTabIndicator.addView(localTabView, paramTab.getPosition(), createLayoutParamsForTabs());
  }
  
  private void addViewInternal(View paramView)
  {
    if ((paramView instanceof TabItem))
    {
      addTabFromItemView((TabItem)paramView);
      return;
    }
    throw new IllegalArgumentException("Only TabItem instances can be added to TabLayout");
  }
  
  private void animateToTab(int paramInt)
  {
    if (paramInt == -1) {
      return;
    }
    if ((getWindowToken() != null) && (ViewCompat.isLaidOut(this)) && (!slidingTabIndicator.childrenNeedLayout()))
    {
      int i = getScrollX();
      int j = calculateScrollXForTab(paramInt, 0.0F);
      if (i != j)
      {
        ensureScrollAnimator();
        scrollAnimator.setIntValues(new int[] { i, j });
        scrollAnimator.start();
      }
      slidingTabIndicator.animateIndicatorToPosition(paramInt, tabIndicatorAnimationDuration);
      return;
    }
    setScrollPosition(paramInt, 0.0F, true);
  }
  
  private void applyModeAndGravity()
  {
    if (mode == 0) {
      i = Math.max(0, contentInsetStart - tabPaddingStart);
    } else {
      i = 0;
    }
    ViewCompat.setPaddingRelative(slidingTabIndicator, i, 0, 0, 0);
    int i = mode;
    if (i != 0)
    {
      if (i == 1) {
        slidingTabIndicator.setGravity(1);
      }
    }
    else {
      slidingTabIndicator.setGravity(8388611);
    }
    updateTabViews(true);
  }
  
  private int calculateScrollXForTab(int paramInt, float paramFloat)
  {
    int j = mode;
    int i = 0;
    if (j == 0)
    {
      View localView2 = slidingTabIndicator.getChildAt(paramInt);
      paramInt += 1;
      View localView1;
      if (paramInt < slidingTabIndicator.getChildCount()) {
        localView1 = slidingTabIndicator.getChildAt(paramInt);
      } else {
        localView1 = null;
      }
      if (localView2 != null) {
        paramInt = localView2.getWidth();
      } else {
        paramInt = 0;
      }
      if (localView1 != null) {
        i = localView1.getWidth();
      }
      j = localView2.getLeft() + paramInt / 2 - getWidth() / 2;
      paramInt = (int)((paramInt + i) * 0.5F * paramFloat);
      if (ViewCompat.getLayoutDirection(this) == 0) {
        return j + paramInt;
      }
      return j - paramInt;
    }
    return 0;
  }
  
  private void configureTab(Tab paramTab, int paramInt)
  {
    paramTab.setPosition(paramInt);
    tabs.add(paramInt, paramTab);
    int i = tabs.size();
    for (;;)
    {
      paramInt += 1;
      if (paramInt >= i) {
        break;
      }
      ((Tab)tabs.get(paramInt)).setPosition(paramInt);
    }
  }
  
  private static ColorStateList createColorStateList(int paramInt1, int paramInt2)
  {
    return new ColorStateList(new int[][] { View.SELECTED_STATE_SET, View.EMPTY_STATE_SET }, new int[] { paramInt2, paramInt1 });
  }
  
  private LinearLayout.LayoutParams createLayoutParamsForTabs()
  {
    LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(-2, -1);
    updateTabViewLayoutParams(localLayoutParams);
    return localLayoutParams;
  }
  
  private TabView createTabView(Tab paramTab)
  {
    Object localObject1 = tabViewPool;
    if (localObject1 != null) {
      localObject1 = (TabView)((Pools.Pool)localObject1).acquire();
    } else {
      localObject1 = null;
    }
    Object localObject2 = localObject1;
    if (localObject1 == null) {
      localObject2 = new TabView(getContext());
    }
    ((TabView)localObject2).setTab(paramTab);
    ((View)localObject2).setFocusable(true);
    ((View)localObject2).setMinimumWidth(getTabMinWidth());
    if (TextUtils.isEmpty(contentDesc))
    {
      ((View)localObject2).setContentDescription(text);
      return localObject2;
    }
    ((View)localObject2).setContentDescription(contentDesc);
    return localObject2;
  }
  
  private void dispatchTabReselected(Tab paramTab)
  {
    int i = selectedListeners.size() - 1;
    while (i >= 0)
    {
      ((BaseOnTabSelectedListener)selectedListeners.get(i)).onTabReselected(paramTab);
      i -= 1;
    }
  }
  
  private void dispatchTabSelected(Tab paramTab)
  {
    int i = selectedListeners.size() - 1;
    while (i >= 0)
    {
      ((BaseOnTabSelectedListener)selectedListeners.get(i)).onTabSelected(paramTab);
      i -= 1;
    }
  }
  
  private void dispatchTabUnselected(Tab paramTab)
  {
    int i = selectedListeners.size() - 1;
    while (i >= 0)
    {
      ((BaseOnTabSelectedListener)selectedListeners.get(i)).onTabUnselected(paramTab);
      i -= 1;
    }
  }
  
  private void ensureScrollAnimator()
  {
    if (scrollAnimator == null)
    {
      scrollAnimator = new ValueAnimator();
      scrollAnimator.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
      scrollAnimator.setDuration(tabIndicatorAnimationDuration);
      scrollAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
      {
        public void onAnimationUpdate(ValueAnimator paramAnonymousValueAnimator)
        {
          scrollTo(((Integer)paramAnonymousValueAnimator.getAnimatedValue()).intValue(), 0);
        }
      });
    }
  }
  
  private int getDefaultHeight()
  {
    int m = tabs.size();
    int k = 0;
    int i = 0;
    int j;
    for (;;)
    {
      j = k;
      if (i >= m) {
        break;
      }
      Tab localTab = (Tab)tabs.get(i);
      if ((localTab != null) && (localTab.getIcon() != null) && (!TextUtils.isEmpty(localTab.getText())))
      {
        j = 1;
        break;
      }
      i += 1;
    }
    if ((j != 0) && (!inlineLabel)) {
      return 72;
    }
    return 48;
  }
  
  private int getTabMinWidth()
  {
    int i = requestedTabMinWidth;
    if (i != -1) {
      return i;
    }
    if (mode == 0) {
      return scrollableTabMinWidth;
    }
    return 0;
  }
  
  private int getTabScrollRange()
  {
    return Math.max(0, slidingTabIndicator.getWidth() - getWidth() - getPaddingLeft() - getPaddingRight());
  }
  
  private void removeTabViewAt(int paramInt)
  {
    TabView localTabView = (TabView)slidingTabIndicator.getChildAt(paramInt);
    slidingTabIndicator.removeViewAt(paramInt);
    if (localTabView != null)
    {
      localTabView.reset();
      tabViewPool.release(localTabView);
    }
    requestLayout();
  }
  
  private void setSelectedTabView(int paramInt)
  {
    int j = slidingTabIndicator.getChildCount();
    if (paramInt < j)
    {
      int i = 0;
      while (i < j)
      {
        View localView = slidingTabIndicator.getChildAt(i);
        boolean bool2 = true;
        boolean bool1;
        if (i == paramInt) {
          bool1 = true;
        } else {
          bool1 = false;
        }
        localView.setSelected(bool1);
        if (i == paramInt) {
          bool1 = bool2;
        } else {
          bool1 = false;
        }
        localView.setActivated(bool1);
        i += 1;
      }
    }
  }
  
  private void setupWithViewPager(ViewPager paramViewPager, boolean paramBoolean1, boolean paramBoolean2)
  {
    Object localObject = viewPager;
    if (localObject != null)
    {
      TabLayoutOnPageChangeListener localTabLayoutOnPageChangeListener = pageChangeListener;
      if (localTabLayoutOnPageChangeListener != null) {
        ((ViewPager)localObject).removeOnPageChangeListener(localTabLayoutOnPageChangeListener);
      }
      localObject = adapterChangeListener;
      if (localObject != null) {
        viewPager.removeOnAdapterChangeListener((ViewPager.OnAdapterChangeListener)localObject);
      }
    }
    localObject = currentVpSelectedListener;
    if (localObject != null)
    {
      removeOnTabSelectedListener((BaseOnTabSelectedListener)localObject);
      currentVpSelectedListener = null;
    }
    if (paramViewPager != null)
    {
      viewPager = paramViewPager;
      if (pageChangeListener == null) {
        pageChangeListener = new TabLayoutOnPageChangeListener(this);
      }
      pageChangeListener.reset();
      paramViewPager.addOnPageChangeListener(pageChangeListener);
      currentVpSelectedListener = new ViewPagerOnTabSelectedListener(paramViewPager);
      addOnTabSelectedListener(currentVpSelectedListener);
      localObject = paramViewPager.getAdapter();
      if (localObject != null) {
        setPagerAdapter((PagerAdapter)localObject, paramBoolean1);
      }
      if (adapterChangeListener == null) {
        adapterChangeListener = new AdapterChangeListener();
      }
      adapterChangeListener.setAutoRefresh(paramBoolean1);
      paramViewPager.addOnAdapterChangeListener(adapterChangeListener);
      setScrollPosition(paramViewPager.getCurrentItem(), 0.0F, true);
    }
    else
    {
      viewPager = null;
      setPagerAdapter(null, false);
    }
    setupViewPagerImplicitly = paramBoolean2;
  }
  
  private void updateAllTabs()
  {
    int j = tabs.size();
    int i = 0;
    while (i < j)
    {
      ((Tab)tabs.get(i)).updateView();
      i += 1;
    }
  }
  
  private void updateTabViewLayoutParams(LinearLayout.LayoutParams paramLayoutParams)
  {
    if ((mode == 1) && (tabGravity == 0))
    {
      width = 0;
      weight = 1.0F;
      return;
    }
    width = -2;
    weight = 0.0F;
  }
  
  public void addOnTabSelectedListener(BaseOnTabSelectedListener paramBaseOnTabSelectedListener)
  {
    if (!selectedListeners.contains(paramBaseOnTabSelectedListener)) {
      selectedListeners.add(paramBaseOnTabSelectedListener);
    }
  }
  
  public void addTab(Tab paramTab)
  {
    addTab(paramTab, tabs.isEmpty());
  }
  
  public void addTab(Tab paramTab, int paramInt)
  {
    addTab(paramTab, paramInt, tabs.isEmpty());
  }
  
  public void addTab(Tab paramTab, int paramInt, boolean paramBoolean)
  {
    if (parent == this)
    {
      configureTab(paramTab, paramInt);
      addTabView(paramTab);
      if (paramBoolean) {
        paramTab.select();
      }
    }
    else
    {
      throw new IllegalArgumentException("Tab belongs to a different TabLayout.");
    }
  }
  
  public void addTab(Tab paramTab, boolean paramBoolean)
  {
    addTab(paramTab, tabs.size(), paramBoolean);
  }
  
  public void addView(View paramView)
  {
    addViewInternal(paramView);
  }
  
  public void addView(View paramView, int paramInt)
  {
    addViewInternal(paramView);
  }
  
  public void addView(View paramView, int paramInt, ViewGroup.LayoutParams paramLayoutParams)
  {
    addViewInternal(paramView);
  }
  
  public void addView(View paramView, ViewGroup.LayoutParams paramLayoutParams)
  {
    addViewInternal(paramView);
  }
  
  public void clearOnTabSelectedListeners()
  {
    selectedListeners.clear();
  }
  
  protected Tab createTabFromPool()
  {
    Tab localTab2 = (Tab)tabPool.acquire();
    Tab localTab1 = localTab2;
    if (localTab2 == null) {
      localTab1 = new Tab();
    }
    return localTab1;
  }
  
  int dpToPx(int paramInt)
  {
    return Math.round(getResourcesgetDisplayMetricsdensity * paramInt);
  }
  
  public FrameLayout.LayoutParams generateLayoutParams(AttributeSet paramAttributeSet)
  {
    return generateDefaultLayoutParams();
  }
  
  public int getSelectedTabPosition()
  {
    Tab localTab = selectedTab;
    if (localTab != null) {
      return localTab.getPosition();
    }
    return -1;
  }
  
  public Tab getTabAt(int paramInt)
  {
    if ((paramInt >= 0) && (paramInt < getTabCount())) {
      return (Tab)tabs.get(paramInt);
    }
    return null;
  }
  
  public int getTabCount()
  {
    return tabs.size();
  }
  
  public int getTabGravity()
  {
    return tabGravity;
  }
  
  public ColorStateList getTabIconTint()
  {
    return tabIconTint;
  }
  
  public int getTabIndicatorGravity()
  {
    return tabIndicatorGravity;
  }
  
  int getTabMaxWidth()
  {
    return tabMaxWidth;
  }
  
  public int getTabMode()
  {
    return mode;
  }
  
  public ColorStateList getTabRippleColor()
  {
    return tabRippleColorStateList;
  }
  
  public Drawable getTabSelectedIndicator()
  {
    return tabSelectedIndicator;
  }
  
  public ColorStateList getTabTextColors()
  {
    return tabTextColors;
  }
  
  public boolean hasUnboundedRipple()
  {
    return unboundedRipple;
  }
  
  public boolean isInlineLabel()
  {
    return inlineLabel;
  }
  
  public boolean isTabIndicatorFullWidth()
  {
    return tabIndicatorFullWidth;
  }
  
  public Tab newTab()
  {
    Tab localTab = createTabFromPool();
    parent = this;
    view = createTabView(localTab);
    return localTab;
  }
  
  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    if (viewPager == null)
    {
      ViewParent localViewParent = getParent();
      if ((localViewParent instanceof ViewPager)) {
        setupWithViewPager((ViewPager)localViewParent, true, true);
      }
    }
  }
  
  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    if (setupViewPagerImplicitly)
    {
      setupWithViewPager(null);
      setupViewPagerImplicitly = false;
    }
  }
  
  protected void onDraw(Canvas paramCanvas)
  {
    int i = 0;
    while (i < slidingTabIndicator.getChildCount())
    {
      View localView = slidingTabIndicator.getChildAt(i);
      if ((localView instanceof TabView)) {
        ((TabView)localView).drawBackground(paramCanvas);
      }
      i += 1;
    }
    super.onDraw(paramCanvas);
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = dpToPx(getDefaultHeight()) + getPaddingTop() + getPaddingBottom();
    int j = View.MeasureSpec.getMode(paramInt2);
    if (j != Integer.MIN_VALUE)
    {
      if (j == 0) {
        paramInt2 = View.MeasureSpec.makeMeasureSpec(i, 1073741824);
      }
    }
    else {
      paramInt2 = View.MeasureSpec.makeMeasureSpec(Math.min(i, View.MeasureSpec.getSize(paramInt2)), 1073741824);
    }
    j = View.MeasureSpec.getSize(paramInt1);
    if (View.MeasureSpec.getMode(paramInt1) != 0)
    {
      i = requestedTabMaxWidth;
      if (i <= 0) {
        i = j - dpToPx(56);
      }
      tabMaxWidth = i;
    }
    super.onMeasure(paramInt1, paramInt2);
    if (getChildCount() == 1)
    {
      paramInt1 = 0;
      View localView = getChildAt(0);
      i = mode;
      if (i != 0)
      {
        if ((i != 1) || (localView.getMeasuredWidth() == getMeasuredWidth())) {}
      }
      else {
        while (localView.getMeasuredWidth() < getMeasuredWidth())
        {
          paramInt1 = 1;
          break;
        }
      }
      if (paramInt1 != 0)
      {
        paramInt1 = ViewGroup.getChildMeasureSpec(paramInt2, getPaddingTop() + getPaddingBottom(), getLayoutParamsheight);
        localView.measure(View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824), paramInt1);
      }
    }
  }
  
  void populateFromPagerAdapter()
  {
    removeAllTabs();
    Object localObject = pagerAdapter;
    if (localObject != null)
    {
      int j = ((PagerAdapter)localObject).getCount();
      int i = 0;
      while (i < j)
      {
        addTab(newTab().setText(pagerAdapter.getPageTitle(i)), false);
        i += 1;
      }
      localObject = viewPager;
      if ((localObject != null) && (j > 0))
      {
        i = ((ViewPager)localObject).getCurrentItem();
        if ((i != getSelectedTabPosition()) && (i < getTabCount())) {
          selectTab(getTabAt(i));
        }
      }
    }
  }
  
  protected boolean releaseFromTabPool(Tab paramTab)
  {
    return tabPool.release(paramTab);
  }
  
  public void removeAllTabs()
  {
    int i = slidingTabIndicator.getChildCount() - 1;
    while (i >= 0)
    {
      removeTabViewAt(i);
      i -= 1;
    }
    Iterator localIterator = tabs.iterator();
    while (localIterator.hasNext())
    {
      Tab localTab = (Tab)localIterator.next();
      localIterator.remove();
      localTab.reset();
      releaseFromTabPool(localTab);
    }
    selectedTab = null;
  }
  
  public void removeOnTabSelectedListener(BaseOnTabSelectedListener paramBaseOnTabSelectedListener)
  {
    selectedListeners.remove(paramBaseOnTabSelectedListener);
  }
  
  public void removeTab(Tab paramTab)
  {
    if (parent == this)
    {
      removeTabAt(paramTab.getPosition());
      return;
    }
    throw new IllegalArgumentException("Tab does not belong to this TabLayout.");
  }
  
  public void removeTabAt(int paramInt)
  {
    Tab localTab = selectedTab;
    int i;
    if (localTab != null) {
      i = localTab.getPosition();
    } else {
      i = 0;
    }
    removeTabViewAt(paramInt);
    localTab = (Tab)tabs.remove(paramInt);
    if (localTab != null)
    {
      localTab.reset();
      releaseFromTabPool(localTab);
    }
    int k = tabs.size();
    int j = paramInt;
    while (j < k)
    {
      ((Tab)tabs.get(j)).setPosition(j);
      j += 1;
    }
    if (i == paramInt)
    {
      if (tabs.isEmpty()) {
        localTab = null;
      } else {
        localTab = (Tab)tabs.get(Math.max(0, paramInt - 1));
      }
      selectTab(localTab);
    }
  }
  
  void selectTab(Tab paramTab)
  {
    selectTab(paramTab, true);
  }
  
  void selectTab(Tab paramTab, boolean paramBoolean)
  {
    Tab localTab = selectedTab;
    if (localTab == paramTab)
    {
      if (localTab != null)
      {
        dispatchTabReselected(paramTab);
        animateToTab(paramTab.getPosition());
      }
    }
    else
    {
      int i;
      if (paramTab != null) {
        i = paramTab.getPosition();
      } else {
        i = -1;
      }
      if (paramBoolean)
      {
        if (((localTab == null) || (localTab.getPosition() == -1)) && (i != -1)) {
          setScrollPosition(i, 0.0F, true);
        } else {
          animateToTab(i);
        }
        if (i != -1) {
          setSelectedTabView(i);
        }
      }
      selectedTab = paramTab;
      if (localTab != null) {
        dispatchTabUnselected(localTab);
      }
      if (paramTab != null) {
        dispatchTabSelected(paramTab);
      }
    }
  }
  
  public void setInlineLabel(boolean paramBoolean)
  {
    if (inlineLabel != paramBoolean)
    {
      inlineLabel = paramBoolean;
      int i = 0;
      while (i < slidingTabIndicator.getChildCount())
      {
        View localView = slidingTabIndicator.getChildAt(i);
        if ((localView instanceof TabView)) {
          ((TabView)localView).updateOrientation();
        }
        i += 1;
      }
      applyModeAndGravity();
    }
  }
  
  public void setInlineLabelResource(int paramInt)
  {
    setInlineLabel(getResources().getBoolean(paramInt));
  }
  
  public void setOnTabSelectedListener(BaseOnTabSelectedListener paramBaseOnTabSelectedListener)
  {
    BaseOnTabSelectedListener localBaseOnTabSelectedListener = selectedListener;
    if (localBaseOnTabSelectedListener != null) {
      removeOnTabSelectedListener(localBaseOnTabSelectedListener);
    }
    selectedListener = paramBaseOnTabSelectedListener;
    if (paramBaseOnTabSelectedListener != null) {
      addOnTabSelectedListener(paramBaseOnTabSelectedListener);
    }
  }
  
  void setPagerAdapter(PagerAdapter paramPagerAdapter, boolean paramBoolean)
  {
    PagerAdapter localPagerAdapter = pagerAdapter;
    if (localPagerAdapter != null)
    {
      DataSetObserver localDataSetObserver = pagerAdapterObserver;
      if (localDataSetObserver != null) {
        localPagerAdapter.unregisterDataSetObserver(localDataSetObserver);
      }
    }
    pagerAdapter = paramPagerAdapter;
    if ((paramBoolean) && (paramPagerAdapter != null))
    {
      if (pagerAdapterObserver == null) {
        pagerAdapterObserver = new PagerAdapterObserver();
      }
      paramPagerAdapter.registerDataSetObserver(pagerAdapterObserver);
    }
    populateFromPagerAdapter();
  }
  
  void setScrollAnimatorListener(Animator.AnimatorListener paramAnimatorListener)
  {
    ensureScrollAnimator();
    scrollAnimator.addListener(paramAnimatorListener);
  }
  
  public void setScrollPosition(int paramInt, float paramFloat, boolean paramBoolean)
  {
    setScrollPosition(paramInt, paramFloat, paramBoolean, true);
  }
  
  void setScrollPosition(int paramInt, float paramFloat, boolean paramBoolean1, boolean paramBoolean2)
  {
    int i = Math.round(paramInt + paramFloat);
    if (i >= 0)
    {
      if (i >= slidingTabIndicator.getChildCount()) {
        return;
      }
      if (paramBoolean2) {
        slidingTabIndicator.setIndicatorPositionFromTabPosition(paramInt, paramFloat);
      }
      ValueAnimator localValueAnimator = scrollAnimator;
      if ((localValueAnimator != null) && (localValueAnimator.isRunning())) {
        scrollAnimator.cancel();
      }
      scrollTo(calculateScrollXForTab(paramInt, paramFloat), 0);
      if (paramBoolean1) {
        setSelectedTabView(i);
      }
    }
  }
  
  public void setSelectedTabIndicator(int paramInt)
  {
    if (paramInt != 0)
    {
      setSelectedTabIndicator(AppCompatResources.getDrawable(getContext(), paramInt));
      return;
    }
    setSelectedTabIndicator(null);
  }
  
  public void setSelectedTabIndicator(Drawable paramDrawable)
  {
    if (tabSelectedIndicator != paramDrawable)
    {
      tabSelectedIndicator = paramDrawable;
      ViewCompat.postInvalidateOnAnimation(slidingTabIndicator);
    }
  }
  
  public void setSelectedTabIndicatorColor(int paramInt)
  {
    slidingTabIndicator.setSelectedIndicatorColor(paramInt);
  }
  
  public void setSelectedTabIndicatorGravity(int paramInt)
  {
    if (tabIndicatorGravity != paramInt)
    {
      tabIndicatorGravity = paramInt;
      ViewCompat.postInvalidateOnAnimation(slidingTabIndicator);
    }
  }
  
  public void setSelectedTabIndicatorHeight(int paramInt)
  {
    slidingTabIndicator.setSelectedIndicatorHeight(paramInt);
  }
  
  public void setTabGravity(int paramInt)
  {
    if (tabGravity != paramInt)
    {
      tabGravity = paramInt;
      applyModeAndGravity();
    }
  }
  
  public void setTabIconTint(ColorStateList paramColorStateList)
  {
    if (tabIconTint != paramColorStateList)
    {
      tabIconTint = paramColorStateList;
      updateAllTabs();
    }
  }
  
  public void setTabIconTintResource(int paramInt)
  {
    setTabIconTint(AppCompatResources.getColorStateList(getContext(), paramInt));
  }
  
  public void setTabIndicatorFullWidth(boolean paramBoolean)
  {
    tabIndicatorFullWidth = paramBoolean;
    ViewCompat.postInvalidateOnAnimation(slidingTabIndicator);
  }
  
  public void setTabMode(int paramInt)
  {
    if (paramInt != mode)
    {
      mode = paramInt;
      applyModeAndGravity();
    }
  }
  
  public void setTabRippleColor(ColorStateList paramColorStateList)
  {
    if (tabRippleColorStateList != paramColorStateList)
    {
      tabRippleColorStateList = paramColorStateList;
      int i = 0;
      while (i < slidingTabIndicator.getChildCount())
      {
        paramColorStateList = slidingTabIndicator.getChildAt(i);
        if ((paramColorStateList instanceof TabView)) {
          ((TabView)paramColorStateList).updateBackgroundDrawable(getContext());
        }
        i += 1;
      }
    }
  }
  
  public void setTabRippleColorResource(int paramInt)
  {
    setTabRippleColor(AppCompatResources.getColorStateList(getContext(), paramInt));
  }
  
  public void setTabTextColors(int paramInt1, int paramInt2)
  {
    setTabTextColors(createColorStateList(paramInt1, paramInt2));
  }
  
  public void setTabTextColors(ColorStateList paramColorStateList)
  {
    if (tabTextColors != paramColorStateList)
    {
      tabTextColors = paramColorStateList;
      updateAllTabs();
    }
  }
  
  public void setTabsFromPagerAdapter(PagerAdapter paramPagerAdapter)
  {
    setPagerAdapter(paramPagerAdapter, false);
  }
  
  public void setUnboundedRipple(boolean paramBoolean)
  {
    if (unboundedRipple != paramBoolean)
    {
      unboundedRipple = paramBoolean;
      int i = 0;
      while (i < slidingTabIndicator.getChildCount())
      {
        View localView = slidingTabIndicator.getChildAt(i);
        if ((localView instanceof TabView)) {
          ((TabView)localView).updateBackgroundDrawable(getContext());
        }
        i += 1;
      }
    }
  }
  
  public void setUnboundedRippleResource(int paramInt)
  {
    setUnboundedRipple(getResources().getBoolean(paramInt));
  }
  
  public void setupWithViewPager(ViewPager paramViewPager)
  {
    setupWithViewPager(paramViewPager, true);
  }
  
  public void setupWithViewPager(ViewPager paramViewPager, boolean paramBoolean)
  {
    setupWithViewPager(paramViewPager, paramBoolean, false);
  }
  
  public boolean shouldDelayChildPressedState()
  {
    return getTabScrollRange() > 0;
  }
  
  void updateTabViews(boolean paramBoolean)
  {
    int i = 0;
    while (i < slidingTabIndicator.getChildCount())
    {
      View localView = slidingTabIndicator.getChildAt(i);
      localView.setMinimumWidth(getTabMinWidth());
      updateTabViewLayoutParams((LinearLayout.LayoutParams)localView.getLayoutParams());
      if (paramBoolean) {
        localView.requestLayout();
      }
      i += 1;
    }
  }
  
  private class AdapterChangeListener
    implements ViewPager.OnAdapterChangeListener
  {
    private boolean autoRefresh;
    
    AdapterChangeListener() {}
    
    public void onAdapterChanged(ViewPager paramViewPager, PagerAdapter paramPagerAdapter1, PagerAdapter paramPagerAdapter2)
    {
      if (viewPager == paramViewPager) {
        setPagerAdapter(paramPagerAdapter2, autoRefresh);
      }
    }
    
    void setAutoRefresh(boolean paramBoolean)
    {
      autoRefresh = paramBoolean;
    }
  }
  
  public static abstract interface BaseOnTabSelectedListener<T extends TabLayout.Tab>
  {
    public abstract void onTabReselected(TabLayout.Tab paramTab);
    
    public abstract void onTabSelected(TabLayout.Tab paramTab);
    
    public abstract void onTabUnselected(TabLayout.Tab paramTab);
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface Mode {}
  
  public static abstract interface OnTabSelectedListener
    extends TabLayout.BaseOnTabSelectedListener<TabLayout.Tab>
  {}
  
  private class PagerAdapterObserver
    extends DataSetObserver
  {
    PagerAdapterObserver() {}
    
    public void onChanged()
    {
      populateFromPagerAdapter();
    }
    
    public void onInvalidated()
    {
      populateFromPagerAdapter();
    }
  }
  
  private class SlidingTabIndicator
    extends LinearLayout
  {
    private final GradientDrawable defaultSelectionIndicator;
    private ValueAnimator indicatorAnimator;
    private int indicatorLeft = -1;
    private int indicatorRight = -1;
    private int layoutDirection = -1;
    private int selectedIndicatorHeight;
    private final Paint selectedIndicatorPaint;
    int selectedPosition = -1;
    float selectionOffset;
    
    SlidingTabIndicator(Context paramContext)
    {
      super();
      setWillNotDraw(false);
      selectedIndicatorPaint = new Paint();
      defaultSelectionIndicator = new GradientDrawable();
    }
    
    private void calculateTabViewContentBounds(TabLayout.TabView paramTabView, RectF paramRectF)
    {
      int j = TabLayout.TabView.access$500(paramTabView);
      int i = j;
      if (j < dpToPx(24)) {
        i = dpToPx(24);
      }
      j = (paramTabView.getLeft() + paramTabView.getRight()) / 2;
      i /= 2;
      paramRectF.set(j - i, 0.0F, j + i, 0.0F);
    }
    
    private void updateIndicatorPosition()
    {
      View localView = getChildAt(selectedPosition);
      int k;
      int m;
      if ((localView != null) && (localView.getWidth() > 0))
      {
        k = localView.getLeft();
        m = localView.getRight();
        int j = k;
        int i = m;
        if (!tabIndicatorFullWidth)
        {
          j = k;
          i = m;
          if ((localView instanceof TabLayout.TabView))
          {
            calculateTabViewContentBounds((TabLayout.TabView)localView, tabViewContentBounds);
            j = (int)tabViewContentBounds.left;
            i = (int)tabViewContentBounds.right;
          }
        }
        k = j;
        m = i;
        if (selectionOffset > 0.0F)
        {
          k = j;
          m = i;
          if (selectedPosition < getChildCount() - 1)
          {
            localView = getChildAt(selectedPosition + 1);
            int n = localView.getLeft();
            int i1 = localView.getRight();
            m = n;
            k = i1;
            if (!tabIndicatorFullWidth)
            {
              m = n;
              k = i1;
              if ((localView instanceof TabLayout.TabView))
              {
                calculateTabViewContentBounds((TabLayout.TabView)localView, tabViewContentBounds);
                m = (int)tabViewContentBounds.left;
                k = (int)tabViewContentBounds.right;
              }
            }
            float f = selectionOffset;
            j = (int)(m * f + (1.0F - f) * j);
            m = (int)(k * f + (1.0F - f) * i);
            k = j;
          }
        }
      }
      else
      {
        k = -1;
        m = -1;
      }
      setIndicatorPosition(k, m);
    }
    
    void animateIndicatorToPosition(final int paramInt1, int paramInt2)
    {
      Object localObject = indicatorAnimator;
      if ((localObject != null) && (((ValueAnimator)localObject).isRunning())) {
        indicatorAnimator.cancel();
      }
      localObject = getChildAt(paramInt1);
      if (localObject == null)
      {
        updateIndicatorPosition();
        return;
      }
      final int k = ((View)localObject).getLeft();
      final int m = ((View)localObject).getRight();
      final int j = k;
      final int i = m;
      if (!tabIndicatorFullWidth)
      {
        j = k;
        i = m;
        if ((localObject instanceof TabLayout.TabView))
        {
          calculateTabViewContentBounds((TabLayout.TabView)localObject, tabViewContentBounds);
          j = (int)tabViewContentBounds.left;
          i = (int)tabViewContentBounds.right;
        }
      }
      k = indicatorLeft;
      m = indicatorRight;
      if ((k != j) || (m != i))
      {
        localObject = new ValueAnimator();
        indicatorAnimator = ((ValueAnimator)localObject);
        ((ValueAnimator)localObject).setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
        ((ValueAnimator)localObject).setDuration(paramInt2);
        ((ValueAnimator)localObject).setFloatValues(new float[] { 0.0F, 1.0F });
        ((ValueAnimator)localObject).addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
          public void onAnimationUpdate(ValueAnimator paramAnonymousValueAnimator)
          {
            float f = paramAnonymousValueAnimator.getAnimatedFraction();
            setIndicatorPosition(AnimationUtils.lerp(k, j, f), AnimationUtils.lerp(m, i, f));
          }
        });
        ((Animator)localObject).addListener(new AnimatorListenerAdapter()
        {
          public void onAnimationEnd(Animator paramAnonymousAnimator)
          {
            paramAnonymousAnimator = TabLayout.SlidingTabIndicator.this;
            selectedPosition = paramInt1;
            selectionOffset = 0.0F;
          }
        });
        ((ValueAnimator)localObject).start();
      }
    }
    
    boolean childrenNeedLayout()
    {
      int j = getChildCount();
      int i = 0;
      while (i < j)
      {
        if (getChildAt(i).getWidth() <= 0) {
          return true;
        }
        i += 1;
      }
      return false;
    }
    
    public void draw(Canvas paramCanvas)
    {
      Object localObject = tabSelectedIndicator;
      int k = 0;
      int i;
      if (localObject != null) {
        i = tabSelectedIndicator.getIntrinsicHeight();
      } else {
        i = 0;
      }
      int j = selectedIndicatorHeight;
      if (j >= 0) {
        i = j;
      }
      int m = tabIndicatorGravity;
      if (m != 0)
      {
        if (m != 1)
        {
          j = k;
          if (m != 2) {
            if (m != 3)
            {
              i = 0;
              j = k;
            }
            else
            {
              i = getHeight();
              j = k;
            }
          }
        }
        else
        {
          j = (getHeight() - i) / 2;
          i = (getHeight() + i) / 2;
        }
      }
      else
      {
        j = getHeight() - i;
        i = getHeight();
      }
      k = indicatorLeft;
      if ((k >= 0) && (indicatorRight > k))
      {
        if (tabSelectedIndicator != null) {
          localObject = tabSelectedIndicator;
        } else {
          localObject = defaultSelectionIndicator;
        }
        localObject = DrawableCompat.wrap((Drawable)localObject);
        ((Drawable)localObject).setBounds(indicatorLeft, j, indicatorRight, i);
        if (selectedIndicatorPaint != null) {
          if (Build.VERSION.SDK_INT == 21) {
            ((Drawable)localObject).setColorFilter(selectedIndicatorPaint.getColor(), PorterDuff.Mode.SRC_IN);
          } else {
            DrawableCompat.setTint((Drawable)localObject, selectedIndicatorPaint.getColor());
          }
        }
        ((Drawable)localObject).draw(paramCanvas);
      }
      super.draw(paramCanvas);
    }
    
    float getIndicatorPosition()
    {
      return selectedPosition + selectionOffset;
    }
    
    protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
      ValueAnimator localValueAnimator = indicatorAnimator;
      if ((localValueAnimator != null) && (localValueAnimator.isRunning()))
      {
        indicatorAnimator.cancel();
        long l = indicatorAnimator.getDuration();
        animateIndicatorToPosition(selectedPosition, Math.round((1.0F - indicatorAnimator.getAnimatedFraction()) * (float)l));
        return;
      }
      updateIndicatorPosition();
    }
    
    protected void onMeasure(int paramInt1, int paramInt2)
    {
      super.onMeasure(paramInt1, paramInt2);
      if (View.MeasureSpec.getMode(paramInt1) != 1073741824) {
        return;
      }
      int i = mode;
      int m = 1;
      if ((i == 1) && (tabGravity == 1))
      {
        int i1 = getChildCount();
        int n = 0;
        i = 0;
        Object localObject;
        int k;
        for (int j = 0; i < i1; j = k)
        {
          localObject = getChildAt(i);
          k = j;
          if (((View)localObject).getVisibility() == 0) {
            k = Math.max(j, ((View)localObject).getMeasuredWidth());
          }
          i += 1;
        }
        if (j <= 0) {
          return;
        }
        i = dpToPx(16);
        if (j * i1 <= getMeasuredWidth() - i * 2)
        {
          i = 0;
          k = n;
          while (k < i1)
          {
            localObject = (LinearLayout.LayoutParams)getChildAt(k).getLayoutParams();
            if ((width != j) || (weight != 0.0F))
            {
              width = j;
              weight = 0.0F;
              i = 1;
            }
            k += 1;
          }
        }
        else
        {
          localObject = TabLayout.this;
          tabGravity = 0;
          ((TabLayout)localObject).updateTabViews(false);
          i = m;
        }
        if (i != 0) {
          super.onMeasure(paramInt1, paramInt2);
        }
      }
    }
    
    public void onRtlPropertiesChanged(int paramInt)
    {
      super.onRtlPropertiesChanged(paramInt);
      if ((Build.VERSION.SDK_INT < 23) && (layoutDirection != paramInt))
      {
        requestLayout();
        layoutDirection = paramInt;
      }
    }
    
    void setIndicatorPosition(int paramInt1, int paramInt2)
    {
      if ((paramInt1 != indicatorLeft) || (paramInt2 != indicatorRight))
      {
        indicatorLeft = paramInt1;
        indicatorRight = paramInt2;
        ViewCompat.postInvalidateOnAnimation(this);
      }
    }
    
    void setIndicatorPositionFromTabPosition(int paramInt, float paramFloat)
    {
      ValueAnimator localValueAnimator = indicatorAnimator;
      if ((localValueAnimator != null) && (localValueAnimator.isRunning())) {
        indicatorAnimator.cancel();
      }
      selectedPosition = paramInt;
      selectionOffset = paramFloat;
      updateIndicatorPosition();
    }
    
    void setSelectedIndicatorColor(int paramInt)
    {
      if (selectedIndicatorPaint.getColor() != paramInt)
      {
        selectedIndicatorPaint.setColor(paramInt);
        ViewCompat.postInvalidateOnAnimation(this);
      }
    }
    
    void setSelectedIndicatorHeight(int paramInt)
    {
      if (selectedIndicatorHeight != paramInt)
      {
        selectedIndicatorHeight = paramInt;
        ViewCompat.postInvalidateOnAnimation(this);
      }
    }
  }
  
  public static class Tab
  {
    public static final int INVALID_POSITION = -1;
    private CharSequence contentDesc;
    private View customView;
    private Drawable icon;
    private Object mTag;
    public TabLayout parent;
    private int position = -1;
    private CharSequence text;
    public TabLayout.TabView view;
    
    public Tab() {}
    
    public CharSequence getContentDescription()
    {
      TabLayout.TabView localTabView = view;
      if (localTabView == null) {
        return null;
      }
      return localTabView.getContentDescription();
    }
    
    public View getCustomView()
    {
      return customView;
    }
    
    public Drawable getIcon()
    {
      return icon;
    }
    
    public int getPosition()
    {
      return position;
    }
    
    public Object getTag()
    {
      return mTag;
    }
    
    public CharSequence getText()
    {
      return text;
    }
    
    public boolean isSelected()
    {
      TabLayout localTabLayout = parent;
      if (localTabLayout != null) {
        return localTabLayout.getSelectedTabPosition() == position;
      }
      throw new IllegalArgumentException("Tab not attached to a TabLayout");
    }
    
    void reset()
    {
      parent = null;
      view = null;
      mTag = null;
      icon = null;
      text = null;
      contentDesc = null;
      position = -1;
      customView = null;
    }
    
    public void select()
    {
      TabLayout localTabLayout = parent;
      if (localTabLayout != null)
      {
        localTabLayout.selectTab(this);
        return;
      }
      throw new IllegalArgumentException("Tab not attached to a TabLayout");
    }
    
    public Tab setContentDescription(int paramInt)
    {
      TabLayout localTabLayout = parent;
      if (localTabLayout != null) {
        return setContentDescription(localTabLayout.getResources().getText(paramInt));
      }
      throw new IllegalArgumentException("Tab not attached to a TabLayout");
    }
    
    public Tab setContentDescription(CharSequence paramCharSequence)
    {
      contentDesc = paramCharSequence;
      updateView();
      return this;
    }
    
    public Tab setCustomView(int paramInt)
    {
      return setCustomView(LayoutInflater.from(view.getContext()).inflate(paramInt, view, false));
    }
    
    public Tab setCustomView(View paramView)
    {
      customView = paramView;
      updateView();
      return this;
    }
    
    public Tab setIcon(int paramInt)
    {
      TabLayout localTabLayout = parent;
      if (localTabLayout != null) {
        return setIcon(AppCompatResources.getDrawable(localTabLayout.getContext(), paramInt));
      }
      throw new IllegalArgumentException("Tab not attached to a TabLayout");
    }
    
    public Tab setIcon(Drawable paramDrawable)
    {
      icon = paramDrawable;
      updateView();
      return this;
    }
    
    void setPosition(int paramInt)
    {
      position = paramInt;
    }
    
    public Tab setTag(Object paramObject)
    {
      mTag = paramObject;
      return this;
    }
    
    public Tab setText(int paramInt)
    {
      TabLayout localTabLayout = parent;
      if (localTabLayout != null) {
        return setText(localTabLayout.getResources().getText(paramInt));
      }
      throw new IllegalArgumentException("Tab not attached to a TabLayout");
    }
    
    public Tab setText(CharSequence paramCharSequence)
    {
      if ((TextUtils.isEmpty(contentDesc)) && (!TextUtils.isEmpty(paramCharSequence))) {
        view.setContentDescription(paramCharSequence);
      }
      text = paramCharSequence;
      updateView();
      return this;
    }
    
    void updateView()
    {
      TabLayout.TabView localTabView = view;
      if (localTabView != null) {
        localTabView.update();
      }
    }
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface TabGravity {}
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface TabIndicatorGravity {}
  
  public static class TabLayoutOnPageChangeListener
    implements ViewPager.OnPageChangeListener
  {
    private int previousScrollState;
    private int scrollState;
    private final WeakReference<TabLayout> tabLayoutRef;
    
    public TabLayoutOnPageChangeListener(TabLayout paramTabLayout)
    {
      tabLayoutRef = new WeakReference(paramTabLayout);
    }
    
    public void onPageScrollStateChanged(int paramInt)
    {
      previousScrollState = scrollState;
      scrollState = paramInt;
    }
    
    public void onPageScrolled(int paramInt1, float paramFloat, int paramInt2)
    {
      TabLayout localTabLayout = (TabLayout)tabLayoutRef.get();
      if (localTabLayout != null)
      {
        paramInt2 = scrollState;
        boolean bool2 = false;
        boolean bool1;
        if ((paramInt2 == 2) && (previousScrollState != 1)) {
          bool1 = false;
        } else {
          bool1 = true;
        }
        if ((scrollState != 2) || (previousScrollState != 0)) {
          bool2 = true;
        }
        localTabLayout.setScrollPosition(paramInt1, paramFloat, bool1, bool2);
      }
    }
    
    public void onPageSelected(int paramInt)
    {
      TabLayout localTabLayout = (TabLayout)tabLayoutRef.get();
      if ((localTabLayout != null) && (localTabLayout.getSelectedTabPosition() != paramInt) && (paramInt < localTabLayout.getTabCount()))
      {
        int i = scrollState;
        boolean bool;
        if ((i != 0) && ((i != 2) || (previousScrollState != 0))) {
          bool = false;
        } else {
          bool = true;
        }
        localTabLayout.selectTab(localTabLayout.getTabAt(paramInt), bool);
      }
    }
    
    void reset()
    {
      scrollState = 0;
      previousScrollState = 0;
    }
  }
  
  class TabView
    extends LinearLayout
  {
    private Drawable baseBackgroundDrawable;
    private ImageView customIconView;
    private TextView customTextView;
    private View customView;
    private int defaultMaxLines = 2;
    private ImageView iconView;
    private TabLayout.Tab mTab;
    private TextView textView;
    
    public TabView(Context paramContext)
    {
      super();
      updateBackgroundDrawable(paramContext);
      ViewCompat.setPaddingRelative(this, tabPaddingStart, tabPaddingTop, tabPaddingEnd, tabPaddingBottom);
      setGravity(17);
      setOrientation(inlineLabel ^ true);
      setClickable(true);
      ViewCompat.setPointerIcon(this, PointerIconCompat.getSystemIcon(getContext(), 1002));
    }
    
    private float approximateLineWidth(Layout paramLayout, int paramInt, float paramFloat)
    {
      return paramLayout.getLineWidth(paramInt) * (paramFloat / paramLayout.getPaint().getTextSize());
    }
    
    private void drawBackground(Canvas paramCanvas)
    {
      Drawable localDrawable = baseBackgroundDrawable;
      if (localDrawable != null)
      {
        localDrawable.setBounds(getLeft(), getTop(), getRight(), getBottom());
        baseBackgroundDrawable.draw(paramCanvas);
      }
    }
    
    private int getContentWidth()
    {
      View[] arrayOfView = new View[3];
      Object localObject = textView;
      int k = 0;
      arrayOfView[0] = localObject;
      arrayOfView[1] = iconView;
      arrayOfView[2] = customView;
      int i3 = arrayOfView.length;
      int m = 0;
      int i = 0;
      int n;
      for (int j = 0; k < i3; j = n)
      {
        localObject = arrayOfView[k];
        int i2 = m;
        int i1 = i;
        n = j;
        if (localObject != null)
        {
          i2 = m;
          i1 = i;
          n = j;
          if (((View)localObject).getVisibility() == 0)
          {
            if (j != 0) {
              i = Math.min(i, ((View)localObject).getLeft());
            } else {
              i = ((View)localObject).getLeft();
            }
            if (j != 0) {
              j = Math.max(m, ((View)localObject).getRight());
            } else {
              j = ((View)localObject).getRight();
            }
            n = 1;
            i1 = i;
            i2 = j;
          }
        }
        k += 1;
        m = i2;
        i = i1;
      }
      return m - i;
    }
    
    private void updateBackgroundDrawable(Context paramContext)
    {
      throw new Runtime("d2j fail translate: java.lang.RuntimeException: fail exe a4 = a3\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.exec(BaseAnalyze.java:92)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.exec(BaseAnalyze.java:1)\n\tat com.googlecode.dex2jar.ir.ts.Cfg.dfs(Cfg.java:255)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.analyze0(BaseAnalyze.java:75)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.analyze(BaseAnalyze.java:69)\n\tat com.googlecode.dex2jar.ir.ts.UnSSATransformer.transform(UnSSATransformer.java:274)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:163)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\nCaused by: java.lang.NullPointerException\n");
    }
    
    private void updateTextAndIcon(TextView paramTextView, ImageView paramImageView)
    {
      Object localObject1 = mTab;
      if ((localObject1 != null) && (((TabLayout.Tab)localObject1).getIcon() != null)) {
        localObject1 = DrawableCompat.wrap(mTab.getIcon()).mutate();
      } else {
        localObject1 = null;
      }
      Object localObject2 = mTab;
      if (localObject2 != null) {
        localObject2 = ((TabLayout.Tab)localObject2).getText();
      } else {
        localObject2 = null;
      }
      if (paramImageView != null) {
        if (localObject1 != null)
        {
          paramImageView.setImageDrawable((Drawable)localObject1);
          paramImageView.setVisibility(0);
          setVisibility(0);
        }
        else
        {
          paramImageView.setVisibility(8);
          paramImageView.setImageDrawable(null);
        }
      }
      boolean bool = TextUtils.isEmpty((CharSequence)localObject2) ^ true;
      if (paramTextView != null) {
        if (bool)
        {
          paramTextView.setText((CharSequence)localObject2);
          paramTextView.setVisibility(0);
          setVisibility(0);
        }
        else
        {
          paramTextView.setVisibility(8);
          paramTextView.setText(null);
        }
      }
      if (paramImageView != null)
      {
        paramTextView = (ViewGroup.MarginLayoutParams)paramImageView.getLayoutParams();
        int i;
        if ((bool) && (paramImageView.getVisibility() == 0)) {
          i = dpToPx(8);
        } else {
          i = 0;
        }
        if (inlineLabel)
        {
          if (i != MarginLayoutParamsCompat.getMarginEnd(paramTextView))
          {
            MarginLayoutParamsCompat.setMarginEnd(paramTextView, i);
            bottomMargin = 0;
            paramImageView.setLayoutParams(paramTextView);
            paramImageView.requestLayout();
          }
        }
        else if (i != bottomMargin)
        {
          bottomMargin = i;
          MarginLayoutParamsCompat.setMarginEnd(paramTextView, 0);
          paramImageView.setLayoutParams(paramTextView);
          paramImageView.requestLayout();
        }
      }
      paramTextView = mTab;
      if (paramTextView != null) {
        paramTextView = contentDesc;
      } else {
        paramTextView = null;
      }
      if (bool) {
        paramTextView = null;
      }
      TooltipCompat.setTooltipText(this, paramTextView);
    }
    
    protected void drawableStateChanged()
    {
      super.drawableStateChanged();
      int[] arrayOfInt = getDrawableState();
      Drawable localDrawable = baseBackgroundDrawable;
      boolean bool2 = false;
      boolean bool1 = bool2;
      if (localDrawable != null)
      {
        bool1 = bool2;
        if (localDrawable.isStateful()) {
          bool1 = false | baseBackgroundDrawable.setState(arrayOfInt);
        }
      }
      if (bool1)
      {
        invalidate();
        invalidate();
      }
    }
    
    public TabLayout.Tab getTab()
    {
      return mTab;
    }
    
    public void onInitializeAccessibilityEvent(AccessibilityEvent paramAccessibilityEvent)
    {
      super.onInitializeAccessibilityEvent(paramAccessibilityEvent);
      paramAccessibilityEvent.setClassName(ActionBar.Tab.class.getName());
    }
    
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo paramAccessibilityNodeInfo)
    {
      super.onInitializeAccessibilityNodeInfo(paramAccessibilityNodeInfo);
      paramAccessibilityNodeInfo.setClassName(ActionBar.Tab.class.getName());
    }
    
    public void onMeasure(int paramInt1, int paramInt2)
    {
      int j = View.MeasureSpec.getSize(paramInt1);
      int k = View.MeasureSpec.getMode(paramInt1);
      int m = getTabMaxWidth();
      int i = paramInt1;
      if (m > 0) {
        if (k != 0)
        {
          i = paramInt1;
          if (j <= m) {}
        }
        else
        {
          i = View.MeasureSpec.makeMeasureSpec(tabMaxWidth, Integer.MIN_VALUE);
        }
      }
      super.onMeasure(i, paramInt2);
      if (textView != null)
      {
        float f2 = tabTextSize;
        j = defaultMaxLines;
        Object localObject = iconView;
        k = 1;
        float f1;
        if ((localObject != null) && (((View)localObject).getVisibility() == 0))
        {
          paramInt1 = 1;
          f1 = f2;
        }
        else
        {
          localObject = textView;
          paramInt1 = j;
          f1 = f2;
          if (localObject != null)
          {
            paramInt1 = j;
            f1 = f2;
            if (((TextView)localObject).getLineCount() > 1)
            {
              f1 = tabTextMultiLineSize;
              paramInt1 = j;
            }
          }
        }
        f2 = textView.getTextSize();
        m = textView.getLineCount();
        j = TextViewCompat.getMaxLines(textView);
        if ((f1 != f2) || ((j >= 0) && (paramInt1 != j)))
        {
          j = k;
          if (mode == 1)
          {
            j = k;
            if (f1 > f2)
            {
              j = k;
              if (m == 1)
              {
                localObject = textView.getLayout();
                if (localObject != null)
                {
                  j = k;
                  if (approximateLineWidth((Layout)localObject, 0, f1) <= getMeasuredWidth() - getPaddingLeft() - getPaddingRight()) {}
                }
                else
                {
                  j = 0;
                }
              }
            }
          }
          if (j != 0)
          {
            textView.setTextSize(0, f1);
            textView.setMaxLines(paramInt1);
            super.onMeasure(i, paramInt2);
          }
        }
      }
    }
    
    public boolean performClick()
    {
      boolean bool = super.performClick();
      if (mTab != null)
      {
        if (!bool) {
          playSoundEffect(0);
        }
        mTab.select();
        return true;
      }
      return bool;
    }
    
    void reset()
    {
      setTab(null);
      setSelected(false);
    }
    
    public void setSelected(boolean paramBoolean)
    {
      int i;
      if (isSelected() != paramBoolean) {
        i = 1;
      } else {
        i = 0;
      }
      super.setSelected(paramBoolean);
      if ((i != 0) && (paramBoolean) && (Build.VERSION.SDK_INT < 16)) {
        sendAccessibilityEvent(4);
      }
      Object localObject = textView;
      if (localObject != null) {
        ((TextView)localObject).setSelected(paramBoolean);
      }
      localObject = iconView;
      if (localObject != null) {
        ((ImageView)localObject).setSelected(paramBoolean);
      }
      localObject = customView;
      if (localObject != null) {
        ((View)localObject).setSelected(paramBoolean);
      }
    }
    
    void setTab(TabLayout.Tab paramTab)
    {
      if (paramTab != mTab)
      {
        mTab = paramTab;
        update();
      }
    }
    
    final void update()
    {
      TabLayout.Tab localTab = mTab;
      Object localObject2 = null;
      if (localTab != null) {
        localObject1 = localTab.getCustomView();
      } else {
        localObject1 = null;
      }
      if (localObject1 != null)
      {
        Object localObject3 = ((View)localObject1).getParent();
        if (localObject3 != this)
        {
          if (localObject3 != null) {
            ((ViewGroup)localObject3).removeView((View)localObject1);
          }
          addView((View)localObject1);
        }
        customView = ((View)localObject1);
        localObject3 = textView;
        if (localObject3 != null) {
          ((View)localObject3).setVisibility(8);
        }
        localObject3 = iconView;
        if (localObject3 != null)
        {
          ((ImageView)localObject3).setVisibility(8);
          iconView.setImageDrawable(null);
        }
        customTextView = ((TextView)((View)localObject1).findViewById(16908308));
        localObject3 = customTextView;
        if (localObject3 != null) {
          defaultMaxLines = TextViewCompat.getMaxLines((TextView)localObject3);
        }
        customIconView = ((ImageView)((View)localObject1).findViewById(16908294));
      }
      else
      {
        localObject1 = customView;
        if (localObject1 != null)
        {
          removeView((View)localObject1);
          customView = null;
        }
        customTextView = null;
        customIconView = null;
      }
      Object localObject1 = customView;
      boolean bool2 = false;
      if (localObject1 == null)
      {
        if (iconView == null)
        {
          localObject1 = (ImageView)LayoutInflater.from(getContext()).inflate(R.layout.design_layout_tab_icon, this, false);
          addView((View)localObject1, 0);
          iconView = ((ImageView)localObject1);
        }
        localObject1 = localObject2;
        if (localTab != null)
        {
          localObject1 = localObject2;
          if (localTab.getIcon() != null) {
            localObject1 = DrawableCompat.wrap(localTab.getIcon()).mutate();
          }
        }
        if (localObject1 != null)
        {
          DrawableCompat.setTintList((Drawable)localObject1, tabIconTint);
          if (tabIconTintMode != null) {
            DrawableCompat.setTintMode((Drawable)localObject1, tabIconTintMode);
          }
        }
        if (textView == null)
        {
          localObject1 = (TextView)LayoutInflater.from(getContext()).inflate(R.layout.design_layout_tab_text, this, false);
          addView((View)localObject1);
          textView = ((TextView)localObject1);
          defaultMaxLines = TextViewCompat.getMaxLines(textView);
        }
        TextViewCompat.setTextAppearance(textView, tabTextAppearance);
        if (tabTextColors != null) {
          textView.setTextColor(tabTextColors);
        }
        updateTextAndIcon(textView, iconView);
      }
      else if ((customTextView != null) || (customIconView != null))
      {
        updateTextAndIcon(customTextView, customIconView);
      }
      if ((localTab != null) && (!TextUtils.isEmpty(contentDesc))) {
        setContentDescription(contentDesc);
      }
      boolean bool1 = bool2;
      if (localTab != null)
      {
        bool1 = bool2;
        if (localTab.isSelected()) {
          bool1 = true;
        }
      }
      setSelected(bool1);
    }
    
    final void updateOrientation()
    {
      setOrientation(inlineLabel ^ true);
      if ((customTextView == null) && (customIconView == null))
      {
        updateTextAndIcon(textView, iconView);
        return;
      }
      updateTextAndIcon(customTextView, customIconView);
    }
  }
  
  public static class ViewPagerOnTabSelectedListener
    implements TabLayout.OnTabSelectedListener
  {
    private final ViewPager viewPager;
    
    public ViewPagerOnTabSelectedListener(ViewPager paramViewPager)
    {
      viewPager = paramViewPager;
    }
    
    public void onTabReselected(TabLayout.Tab paramTab) {}
    
    public void onTabSelected(TabLayout.Tab paramTab)
    {
      viewPager.setCurrentItem(paramTab.getPosition());
    }
    
    public void onTabUnselected(TabLayout.Tab paramTab) {}
  }
}
