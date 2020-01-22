package androidx.appcompat.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.SpinnerAdapter;
import androidx.appcompat.R.attr;
import androidx.appcompat.R.id;
import androidx.appcompat.R.styleable;
import androidx.appcompat.content.wiki.AppCompatResources;
import androidx.appcompat.view.ActionBarPolicy;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.view.ActionMode.Callback;
import androidx.appcompat.view.SupportMenuInflater;
import androidx.appcompat.view.ViewPropertyAnimatorCompatSet;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuBuilder.Callback;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.view.menu.SubMenuBuilder;
import androidx.appcompat.widget.ActionBarContainer;
import androidx.appcompat.widget.ActionBarContextView;
import androidx.appcompat.widget.ActionBarOverlayLayout;
import androidx.appcompat.widget.ActionBarOverlayLayout.ActionBarVisibilityCallback;
import androidx.appcompat.widget.DecorToolbar;
import androidx.appcompat.widget.ScrollingTabContainerView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.core.view.ViewPropertyAnimatorListener;
import androidx.core.view.ViewPropertyAnimatorListenerAdapter;
import androidx.core.view.ViewPropertyAnimatorUpdateListener;
import androidx.fragment.package_8.FragmentActivity;
import androidx.fragment.package_8.FragmentManager;
import androidx.fragment.package_8.FragmentTransaction;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class WindowDecorActionBar
  extends ActionBar
  implements ActionBarOverlayLayout.ActionBarVisibilityCallback
{
  private static final long FADE_IN_DURATION_MS = 200L;
  private static final long FADE_OUT_DURATION_MS = 100L;
  private static final int INVALID_POSITION = -1;
  private static final String TAG = "WindowDecorActionBar";
  private static final Interpolator sHideInterpolator = new AccelerateInterpolator();
  private static final Interpolator sShowInterpolator = new DecelerateInterpolator();
  ActionModeImpl mActionMode;
  private Activity mActivity;
  ActionBarContainer mContainerView;
  boolean mContentAnimations = true;
  View mContentView;
  Context mContext;
  ActionBarContextView mContextView;
  private int mCurWindowVisibility = 0;
  ViewPropertyAnimatorCompatSet mCurrentShowAnim;
  DecorToolbar mDecorToolbar;
  ActionMode mDeferredDestroyActionMode;
  ActionMode.Callback mDeferredModeDestroyCallback;
  private Dialog mDialog;
  private boolean mDisplayHomeAsUpSet;
  private boolean mHasEmbeddedTabs;
  boolean mHiddenByApp;
  boolean mHiddenBySystem;
  final ViewPropertyAnimatorListener mHideListener = new ViewPropertyAnimatorListenerAdapter()
  {
    public void onAnimationEnd(View paramAnonymousView)
    {
      if ((mContentAnimations) && (mContentView != null))
      {
        mContentView.setTranslationY(0.0F);
        mContainerView.setTranslationY(0.0F);
      }
      mContainerView.setVisibility(8);
      mContainerView.setTransitioning(false);
      paramAnonymousView = WindowDecorActionBar.this;
      mCurrentShowAnim = null;
      paramAnonymousView.completeDeferredDestroyActionMode();
      if (mOverlayLayout != null) {
        ViewCompat.requestApplyInsets(mOverlayLayout);
      }
    }
  };
  boolean mHideOnContentScroll;
  private boolean mLastMenuVisibility;
  private ArrayList<ActionBar.OnMenuVisibilityListener> mMenuVisibilityListeners = new ArrayList();
  private boolean mNowShowing = true;
  ActionBarOverlayLayout mOverlayLayout;
  private int mSavedTabPosition = -1;
  private TabImpl mSelectedTab;
  private boolean mShowHideAnimationEnabled;
  final ViewPropertyAnimatorListener mShowListener = new ViewPropertyAnimatorListenerAdapter()
  {
    public void onAnimationEnd(View paramAnonymousView)
    {
      paramAnonymousView = WindowDecorActionBar.this;
      mCurrentShowAnim = null;
      mContainerView.requestLayout();
    }
  };
  private boolean mShowingForMode;
  ScrollingTabContainerView mTabScrollView;
  private ArrayList<TabImpl> mTabs = new ArrayList();
  private Context mThemedContext;
  final ViewPropertyAnimatorUpdateListener mUpdateListener = new ViewPropertyAnimatorUpdateListener()
  {
    public void onAnimationUpdate(View paramAnonymousView)
    {
      ((View)mContainerView.getParent()).invalidate();
    }
  };
  
  public WindowDecorActionBar(Activity paramActivity, boolean paramBoolean)
  {
    mActivity = paramActivity;
    paramActivity = paramActivity.getWindow().getDecorView();
    init(paramActivity);
    if (!paramBoolean) {
      mContentView = paramActivity.findViewById(16908290);
    }
  }
  
  public WindowDecorActionBar(Dialog paramDialog)
  {
    mDialog = paramDialog;
    init(paramDialog.getWindow().getDecorView());
  }
  
  public WindowDecorActionBar(View paramView)
  {
    init(paramView);
  }
  
  static boolean checkShowingFlags(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    if (paramBoolean3) {
      return true;
    }
    return (!paramBoolean1) && (!paramBoolean2);
  }
  
  private void cleanupTabs()
  {
    if (mSelectedTab != null) {
      selectTab(null);
    }
    mTabs.clear();
    ScrollingTabContainerView localScrollingTabContainerView = mTabScrollView;
    if (localScrollingTabContainerView != null) {
      localScrollingTabContainerView.removeAllTabs();
    }
    mSavedTabPosition = -1;
  }
  
  private void configureTab(ActionBar.Tab paramTab, int paramInt)
  {
    paramTab = (TabImpl)paramTab;
    if (paramTab.getCallback() != null)
    {
      paramTab.setPosition(paramInt);
      mTabs.add(paramInt, paramTab);
      int i = mTabs.size();
      for (;;)
      {
        paramInt += 1;
        if (paramInt >= i) {
          break;
        }
        ((TabImpl)mTabs.get(paramInt)).setPosition(paramInt);
      }
      return;
    }
    paramTab = new IllegalStateException("Action Bar Tab must have a Callback");
    throw paramTab;
  }
  
  private void ensureTabsExist()
  {
    if (mTabScrollView != null) {
      return;
    }
    ScrollingTabContainerView localScrollingTabContainerView = new ScrollingTabContainerView(mContext);
    if (mHasEmbeddedTabs)
    {
      localScrollingTabContainerView.setVisibility(0);
      mDecorToolbar.setEmbeddedTabView(localScrollingTabContainerView);
    }
    else
    {
      if (getNavigationMode() == 2)
      {
        localScrollingTabContainerView.setVisibility(0);
        ActionBarOverlayLayout localActionBarOverlayLayout = mOverlayLayout;
        if (localActionBarOverlayLayout != null) {
          ViewCompat.requestApplyInsets(localActionBarOverlayLayout);
        }
      }
      else
      {
        localScrollingTabContainerView.setVisibility(8);
      }
      mContainerView.setTabContainer(localScrollingTabContainerView);
    }
    mTabScrollView = localScrollingTabContainerView;
  }
  
  private DecorToolbar getDecorToolbar(View paramView)
  {
    if ((paramView instanceof DecorToolbar)) {
      return (DecorToolbar)paramView;
    }
    if ((paramView instanceof Toolbar)) {
      return ((Toolbar)paramView).getWrapper();
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Can't make a decor toolbar out of ");
    if (paramView != null) {
      paramView = paramView.getClass().getSimpleName();
    } else {
      paramView = "null";
    }
    localStringBuilder.append(paramView);
    throw new IllegalStateException(localStringBuilder.toString());
  }
  
  private void hideForActionMode()
  {
    if (mShowingForMode)
    {
      mShowingForMode = false;
      ActionBarOverlayLayout localActionBarOverlayLayout = mOverlayLayout;
      if (localActionBarOverlayLayout != null) {
        localActionBarOverlayLayout.setShowingForActionMode(false);
      }
      updateVisibility(false);
    }
  }
  
  private void init(View paramView)
  {
    mOverlayLayout = ((ActionBarOverlayLayout)paramView.findViewById(R.id.decor_content_parent));
    ActionBarOverlayLayout localActionBarOverlayLayout = mOverlayLayout;
    if (localActionBarOverlayLayout != null) {
      localActionBarOverlayLayout.setActionBarVisibilityCallback(this);
    }
    mDecorToolbar = getDecorToolbar(paramView.findViewById(R.id.action_bar));
    mContextView = ((ActionBarContextView)paramView.findViewById(R.id.action_context_bar));
    mContainerView = ((ActionBarContainer)paramView.findViewById(R.id.action_bar_container));
    paramView = mDecorToolbar;
    if ((paramView != null) && (mContextView != null) && (mContainerView != null))
    {
      mContext = paramView.getContext();
      if ((mDecorToolbar.getDisplayOptions() & 0x4) != 0) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0) {
        mDisplayHomeAsUpSet = true;
      }
      paramView = ActionBarPolicy.get(mContext);
      boolean bool;
      if ((!paramView.enableHomeButtonByDefault()) && (i == 0)) {
        bool = false;
      } else {
        bool = true;
      }
      setHomeButtonEnabled(bool);
      setHasEmbeddedTabs(paramView.hasEmbeddedTabs());
      paramView = mContext.obtainStyledAttributes(null, R.styleable.ActionBar, R.attr.actionBarStyle, 0);
      if (paramView.getBoolean(R.styleable.ActionBar_hideOnContentScroll, false)) {
        setHideOnContentScrollEnabled(true);
      }
      int i = paramView.getDimensionPixelSize(R.styleable.ActionBar_elevation, 0);
      if (i != 0) {
        setElevation(i);
      }
      paramView.recycle();
      return;
    }
    paramView = new StringBuilder();
    paramView.append(getClass().getSimpleName());
    paramView.append(" can only be used with a compatible window decor layout");
    throw new IllegalStateException(paramView.toString());
  }
  
  private void setHasEmbeddedTabs(boolean paramBoolean)
  {
    mHasEmbeddedTabs = paramBoolean;
    if (!mHasEmbeddedTabs)
    {
      mDecorToolbar.setEmbeddedTabView(null);
      mContainerView.setTabContainer(mTabScrollView);
    }
    else
    {
      mContainerView.setTabContainer(null);
      mDecorToolbar.setEmbeddedTabView(mTabScrollView);
    }
    int i = getNavigationMode();
    boolean bool = true;
    if (i == 2) {
      i = 1;
    } else {
      i = 0;
    }
    Object localObject = mTabScrollView;
    if (localObject != null) {
      if (i != 0)
      {
        ((View)localObject).setVisibility(0);
        localObject = mOverlayLayout;
        if (localObject != null) {
          ViewCompat.requestApplyInsets((View)localObject);
        }
      }
      else
      {
        ((View)localObject).setVisibility(8);
      }
    }
    localObject = mDecorToolbar;
    if ((!mHasEmbeddedTabs) && (i != 0)) {
      paramBoolean = true;
    } else {
      paramBoolean = false;
    }
    ((DecorToolbar)localObject).setCollapsible(paramBoolean);
    localObject = mOverlayLayout;
    if ((!mHasEmbeddedTabs) && (i != 0)) {
      paramBoolean = bool;
    } else {
      paramBoolean = false;
    }
    ((ActionBarOverlayLayout)localObject).setHasNonEmbeddedTabs(paramBoolean);
  }
  
  private boolean shouldAnimateContextView()
  {
    return ViewCompat.isLaidOut(mContainerView);
  }
  
  private void showForActionMode()
  {
    if (!mShowingForMode)
    {
      mShowingForMode = true;
      ActionBarOverlayLayout localActionBarOverlayLayout = mOverlayLayout;
      if (localActionBarOverlayLayout != null) {
        localActionBarOverlayLayout.setShowingForActionMode(true);
      }
      updateVisibility(false);
    }
  }
  
  private void updateVisibility(boolean paramBoolean)
  {
    if (checkShowingFlags(mHiddenByApp, mHiddenBySystem, mShowingForMode))
    {
      if (!mNowShowing)
      {
        mNowShowing = true;
        doShow(paramBoolean);
      }
    }
    else if (mNowShowing)
    {
      mNowShowing = false;
      doHide(paramBoolean);
    }
  }
  
  public void addOnMenuVisibilityListener(ActionBar.OnMenuVisibilityListener paramOnMenuVisibilityListener)
  {
    mMenuVisibilityListeners.add(paramOnMenuVisibilityListener);
  }
  
  public void addTab(ActionBar.Tab paramTab)
  {
    addTab(paramTab, mTabs.isEmpty());
  }
  
  public void addTab(ActionBar.Tab paramTab, int paramInt)
  {
    addTab(paramTab, paramInt, mTabs.isEmpty());
  }
  
  public void addTab(ActionBar.Tab paramTab, int paramInt, boolean paramBoolean)
  {
    ensureTabsExist();
    mTabScrollView.addTab(paramTab, paramInt, paramBoolean);
    configureTab(paramTab, paramInt);
    if (paramBoolean) {
      selectTab(paramTab);
    }
  }
  
  public void addTab(ActionBar.Tab paramTab, boolean paramBoolean)
  {
    ensureTabsExist();
    mTabScrollView.addTab(paramTab, paramBoolean);
    configureTab(paramTab, mTabs.size());
    if (paramBoolean) {
      selectTab(paramTab);
    }
  }
  
  public void animateToMode(boolean paramBoolean)
  {
    if (paramBoolean) {
      showForActionMode();
    } else {
      hideForActionMode();
    }
    if (shouldAnimateContextView())
    {
      ViewPropertyAnimatorCompat localViewPropertyAnimatorCompat1;
      ViewPropertyAnimatorCompat localViewPropertyAnimatorCompat2;
      if (paramBoolean)
      {
        localViewPropertyAnimatorCompat1 = mDecorToolbar.setupAnimatorToVisibility(4, 100L);
        localViewPropertyAnimatorCompat2 = mContextView.setupAnimatorToVisibility(0, 200L);
      }
      else
      {
        localViewPropertyAnimatorCompat2 = mDecorToolbar.setupAnimatorToVisibility(0, 200L);
        localViewPropertyAnimatorCompat1 = mContextView.setupAnimatorToVisibility(8, 100L);
      }
      ViewPropertyAnimatorCompatSet localViewPropertyAnimatorCompatSet = new ViewPropertyAnimatorCompatSet();
      localViewPropertyAnimatorCompatSet.playSequentially(localViewPropertyAnimatorCompat1, localViewPropertyAnimatorCompat2);
      localViewPropertyAnimatorCompatSet.start();
      return;
    }
    if (paramBoolean)
    {
      mDecorToolbar.setVisibility(4);
      mContextView.setVisibility(0);
      return;
    }
    mDecorToolbar.setVisibility(0);
    mContextView.setVisibility(8);
  }
  
  public boolean collapseActionView()
  {
    DecorToolbar localDecorToolbar = mDecorToolbar;
    if ((localDecorToolbar != null) && (localDecorToolbar.hasExpandedActionView()))
    {
      mDecorToolbar.collapseActionView();
      return true;
    }
    return false;
  }
  
  void completeDeferredDestroyActionMode()
  {
    ActionMode.Callback localCallback = mDeferredModeDestroyCallback;
    if (localCallback != null)
    {
      localCallback.onDestroyActionMode(mDeferredDestroyActionMode);
      mDeferredDestroyActionMode = null;
      mDeferredModeDestroyCallback = null;
    }
  }
  
  public void dispatchMenuVisibilityChanged(boolean paramBoolean)
  {
    if (paramBoolean == mLastMenuVisibility) {
      return;
    }
    mLastMenuVisibility = paramBoolean;
    int j = mMenuVisibilityListeners.size();
    int i = 0;
    while (i < j)
    {
      ((ActionBar.OnMenuVisibilityListener)mMenuVisibilityListeners.get(i)).onMenuVisibilityChanged(paramBoolean);
      i += 1;
    }
  }
  
  public void doHide(boolean paramBoolean)
  {
    ViewPropertyAnimatorCompatSet localViewPropertyAnimatorCompatSet = mCurrentShowAnim;
    if (localViewPropertyAnimatorCompatSet != null) {
      localViewPropertyAnimatorCompatSet.cancel();
    }
    if ((mCurWindowVisibility == 0) && ((mShowHideAnimationEnabled) || (paramBoolean)))
    {
      mContainerView.setAlpha(1.0F);
      mContainerView.setTransitioning(true);
      localViewPropertyAnimatorCompatSet = new ViewPropertyAnimatorCompatSet();
      float f2 = -mContainerView.getHeight();
      float f1 = f2;
      if (paramBoolean)
      {
        localObject = new int[2];
        Object tmp82_80 = localObject;
        tmp82_80[0] = 0;
        Object tmp86_82 = tmp82_80;
        tmp86_82[1] = 0;
        tmp86_82;
        mContainerView.getLocationInWindow((int[])localObject);
        f1 = f2 - localObject[1];
      }
      Object localObject = ViewCompat.animate(mContainerView).translationY(f1);
      ((ViewPropertyAnimatorCompat)localObject).setUpdateListener(mUpdateListener);
      localViewPropertyAnimatorCompatSet.play((ViewPropertyAnimatorCompat)localObject);
      if (mContentAnimations)
      {
        localObject = mContentView;
        if (localObject != null) {
          localViewPropertyAnimatorCompatSet.play(ViewCompat.animate((View)localObject).translationY(f1));
        }
      }
      localViewPropertyAnimatorCompatSet.setInterpolator(sHideInterpolator);
      localViewPropertyAnimatorCompatSet.setDuration(250L);
      localViewPropertyAnimatorCompatSet.setListener(mHideListener);
      mCurrentShowAnim = localViewPropertyAnimatorCompatSet;
      localViewPropertyAnimatorCompatSet.start();
      return;
    }
    mHideListener.onAnimationEnd(null);
  }
  
  public void doShow(boolean paramBoolean)
  {
    Object localObject1 = mCurrentShowAnim;
    if (localObject1 != null) {
      ((ViewPropertyAnimatorCompatSet)localObject1).cancel();
    }
    mContainerView.setVisibility(0);
    if ((mCurWindowVisibility == 0) && ((mShowHideAnimationEnabled) || (paramBoolean)))
    {
      mContainerView.setTranslationY(0.0F);
      float f2 = -mContainerView.getHeight();
      float f1 = f2;
      if (paramBoolean)
      {
        localObject1 = new int[2];
        Object tmp73_71 = localObject1;
        tmp73_71[0] = 0;
        Object tmp77_73 = tmp73_71;
        tmp77_73[1] = 0;
        tmp77_73;
        mContainerView.getLocationInWindow((int[])localObject1);
        f1 = f2 - localObject1[1];
      }
      mContainerView.setTranslationY(f1);
      localObject1 = new ViewPropertyAnimatorCompatSet();
      Object localObject2 = ViewCompat.animate(mContainerView).translationY(0.0F);
      ((ViewPropertyAnimatorCompat)localObject2).setUpdateListener(mUpdateListener);
      ((ViewPropertyAnimatorCompatSet)localObject1).play((ViewPropertyAnimatorCompat)localObject2);
      if (mContentAnimations)
      {
        localObject2 = mContentView;
        if (localObject2 != null)
        {
          ((View)localObject2).setTranslationY(f1);
          ((ViewPropertyAnimatorCompatSet)localObject1).play(ViewCompat.animate(mContentView).translationY(0.0F));
        }
      }
      ((ViewPropertyAnimatorCompatSet)localObject1).setInterpolator(sShowInterpolator);
      ((ViewPropertyAnimatorCompatSet)localObject1).setDuration(250L);
      ((ViewPropertyAnimatorCompatSet)localObject1).setListener(mShowListener);
      mCurrentShowAnim = ((ViewPropertyAnimatorCompatSet)localObject1);
      ((ViewPropertyAnimatorCompatSet)localObject1).start();
    }
    else
    {
      mContainerView.setAlpha(1.0F);
      mContainerView.setTranslationY(0.0F);
      if (mContentAnimations)
      {
        localObject1 = mContentView;
        if (localObject1 != null) {
          ((View)localObject1).setTranslationY(0.0F);
        }
      }
      mShowListener.onAnimationEnd(null);
    }
    localObject1 = mOverlayLayout;
    if (localObject1 != null) {
      ViewCompat.requestApplyInsets((View)localObject1);
    }
  }
  
  public void enableContentAnimations(boolean paramBoolean)
  {
    mContentAnimations = paramBoolean;
  }
  
  public View getCustomView()
  {
    return mDecorToolbar.getCustomView();
  }
  
  public int getDisplayOptions()
  {
    return mDecorToolbar.getDisplayOptions();
  }
  
  public float getElevation()
  {
    return ViewCompat.getElevation(mContainerView);
  }
  
  public int getHeight()
  {
    return mContainerView.getHeight();
  }
  
  public int getHideOffset()
  {
    return mOverlayLayout.getActionBarHideOffset();
  }
  
  public int getNavigationItemCount()
  {
    int i = mDecorToolbar.getNavigationMode();
    if (i != 1)
    {
      if (i != 2) {
        return 0;
      }
      return mTabs.size();
    }
    return mDecorToolbar.getDropdownItemCount();
  }
  
  public int getNavigationMode()
  {
    return mDecorToolbar.getNavigationMode();
  }
  
  public int getSelectedNavigationIndex()
  {
    int i = mDecorToolbar.getNavigationMode();
    if (i != 1)
    {
      if (i != 2) {
        return -1;
      }
      TabImpl localTabImpl = mSelectedTab;
      if (localTabImpl != null) {
        return localTabImpl.getPosition();
      }
    }
    else
    {
      return mDecorToolbar.getDropdownSelectedPosition();
    }
    return -1;
  }
  
  public ActionBar.Tab getSelectedTab()
  {
    return mSelectedTab;
  }
  
  public CharSequence getSubtitle()
  {
    return mDecorToolbar.getSubtitle();
  }
  
  public ActionBar.Tab getTabAt(int paramInt)
  {
    return (ActionBar.Tab)mTabs.get(paramInt);
  }
  
  public int getTabCount()
  {
    return mTabs.size();
  }
  
  public Context getThemedContext()
  {
    if (mThemedContext == null)
    {
      TypedValue localTypedValue = new TypedValue();
      mContext.getTheme().resolveAttribute(R.attr.actionBarWidgetTheme, localTypedValue, true);
      int i = resourceId;
      if (i != 0) {
        mThemedContext = new ContextThemeWrapper(mContext, i);
      } else {
        mThemedContext = mContext;
      }
    }
    return mThemedContext;
  }
  
  public CharSequence getTitle()
  {
    return mDecorToolbar.getTitle();
  }
  
  public boolean hasIcon()
  {
    return mDecorToolbar.hasIcon();
  }
  
  public boolean hasLogo()
  {
    return mDecorToolbar.hasLogo();
  }
  
  public void hide()
  {
    if (!mHiddenByApp)
    {
      mHiddenByApp = true;
      updateVisibility(false);
    }
  }
  
  public void hideForSystem()
  {
    if (!mHiddenBySystem)
    {
      mHiddenBySystem = true;
      updateVisibility(true);
    }
  }
  
  public boolean isHideOnContentScrollEnabled()
  {
    return mOverlayLayout.isHideOnContentScrollEnabled();
  }
  
  public boolean isShowing()
  {
    int i = getHeight();
    return (mNowShowing) && ((i == 0) || (getHideOffset() < i));
  }
  
  public boolean isTitleTruncated()
  {
    DecorToolbar localDecorToolbar = mDecorToolbar;
    return (localDecorToolbar != null) && (localDecorToolbar.isTitleTruncated());
  }
  
  public ActionBar.Tab newTab()
  {
    return new TabImpl();
  }
  
  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    setHasEmbeddedTabs(ActionBarPolicy.get(mContext).hasEmbeddedTabs());
  }
  
  public void onContentScrollStarted()
  {
    ViewPropertyAnimatorCompatSet localViewPropertyAnimatorCompatSet = mCurrentShowAnim;
    if (localViewPropertyAnimatorCompatSet != null)
    {
      localViewPropertyAnimatorCompatSet.cancel();
      mCurrentShowAnim = null;
    }
  }
  
  public void onContentScrollStopped() {}
  
  public boolean onKeyShortcut(int paramInt, KeyEvent paramKeyEvent)
  {
    Object localObject = mActionMode;
    if (localObject == null) {
      return false;
    }
    localObject = ((ActionModeImpl)localObject).getMenu();
    if (localObject != null)
    {
      if (paramKeyEvent != null) {
        i = paramKeyEvent.getDeviceId();
      } else {
        i = -1;
      }
      int i = KeyCharacterMap.load(i).getKeyboardType();
      boolean bool = true;
      if (i == 1) {
        bool = false;
      }
      ((Menu)localObject).setQwertyMode(bool);
      return ((Menu)localObject).performShortcut(paramInt, paramKeyEvent, 0);
    }
    return false;
  }
  
  public void onWindowVisibilityChanged(int paramInt)
  {
    mCurWindowVisibility = paramInt;
  }
  
  public void removeAllTabs()
  {
    cleanupTabs();
  }
  
  public void removeOnMenuVisibilityListener(ActionBar.OnMenuVisibilityListener paramOnMenuVisibilityListener)
  {
    mMenuVisibilityListeners.remove(paramOnMenuVisibilityListener);
  }
  
  public void removeTab(ActionBar.Tab paramTab)
  {
    removeTabAt(paramTab.getPosition());
  }
  
  public void removeTabAt(int paramInt)
  {
    if (mTabScrollView == null) {
      return;
    }
    TabImpl localTabImpl = mSelectedTab;
    int i;
    if (localTabImpl != null) {
      i = localTabImpl.getPosition();
    } else {
      i = mSavedTabPosition;
    }
    mTabScrollView.removeTabAt(paramInt);
    localTabImpl = (TabImpl)mTabs.remove(paramInt);
    if (localTabImpl != null) {
      localTabImpl.setPosition(-1);
    }
    int k = mTabs.size();
    int j = paramInt;
    while (j < k)
    {
      ((TabImpl)mTabs.get(j)).setPosition(j);
      j += 1;
    }
    if (i == paramInt)
    {
      if (mTabs.isEmpty()) {
        localTabImpl = null;
      } else {
        localTabImpl = (TabImpl)mTabs.get(Math.max(0, paramInt - 1));
      }
      selectTab(localTabImpl);
    }
  }
  
  public boolean requestFocus()
  {
    ViewGroup localViewGroup = mDecorToolbar.getViewGroup();
    if ((localViewGroup != null) && (!localViewGroup.hasFocus()))
    {
      localViewGroup.requestFocus();
      return true;
    }
    return false;
  }
  
  public void selectTab(ActionBar.Tab paramTab)
  {
    int j = getNavigationMode();
    int i = -1;
    if (j != 2)
    {
      if (paramTab != null) {
        i = paramTab.getPosition();
      }
      mSavedTabPosition = i;
      return;
    }
    FragmentTransaction localFragmentTransaction;
    if (((mActivity instanceof FragmentActivity)) && (!mDecorToolbar.getViewGroup().isInEditMode())) {
      localFragmentTransaction = ((FragmentActivity)mActivity).getSupportFragmentManager().beginTransaction().disallowAddToBackStack();
    } else {
      localFragmentTransaction = null;
    }
    Object localObject = mSelectedTab;
    if (localObject == paramTab)
    {
      if (localObject != null)
      {
        ((TabImpl)localObject).getCallback().onTabReselected(mSelectedTab, localFragmentTransaction);
        mTabScrollView.animateToTab(paramTab.getPosition());
      }
    }
    else
    {
      localObject = mTabScrollView;
      if (paramTab != null) {
        i = paramTab.getPosition();
      }
      ((ScrollingTabContainerView)localObject).setTabSelected(i);
      localObject = mSelectedTab;
      if (localObject != null) {
        ((TabImpl)localObject).getCallback().onTabUnselected(mSelectedTab, localFragmentTransaction);
      }
      mSelectedTab = ((TabImpl)paramTab);
      paramTab = mSelectedTab;
      if (paramTab != null) {
        paramTab.getCallback().onTabSelected(mSelectedTab, localFragmentTransaction);
      }
    }
    if ((localFragmentTransaction != null) && (!localFragmentTransaction.isEmpty())) {
      localFragmentTransaction.commit();
    }
  }
  
  public void setBackgroundDrawable(Drawable paramDrawable)
  {
    mContainerView.setPrimaryBackground(paramDrawable);
  }
  
  public void setCustomView(int paramInt)
  {
    setCustomView(LayoutInflater.from(getThemedContext()).inflate(paramInt, mDecorToolbar.getViewGroup(), false));
  }
  
  public void setCustomView(View paramView)
  {
    mDecorToolbar.setCustomView(paramView);
  }
  
  public void setCustomView(View paramView, ActionBar.LayoutParams paramLayoutParams)
  {
    paramView.setLayoutParams(paramLayoutParams);
    mDecorToolbar.setCustomView(paramView);
  }
  
  public void setDefaultDisplayHomeAsUpEnabled(boolean paramBoolean)
  {
    if (!mDisplayHomeAsUpSet) {
      setDisplayHomeAsUpEnabled(paramBoolean);
    }
  }
  
  public void setDisplayHomeAsUpEnabled(boolean paramBoolean)
  {
    int i;
    if (paramBoolean) {
      i = 4;
    } else {
      i = 0;
    }
    setDisplayOptions(i, 4);
  }
  
  public void setDisplayOptions(int paramInt)
  {
    if ((paramInt & 0x4) != 0) {
      mDisplayHomeAsUpSet = true;
    }
    mDecorToolbar.setDisplayOptions(paramInt);
  }
  
  public void setDisplayOptions(int paramInt1, int paramInt2)
  {
    int i = mDecorToolbar.getDisplayOptions();
    if ((paramInt2 & 0x4) != 0) {
      mDisplayHomeAsUpSet = true;
    }
    mDecorToolbar.setDisplayOptions(paramInt1 & paramInt2 | paramInt2 & i);
  }
  
  public void setDisplayShowCustomEnabled(boolean paramBoolean)
  {
    int i;
    if (paramBoolean) {
      i = 16;
    } else {
      i = 0;
    }
    setDisplayOptions(i, 16);
  }
  
  public void setDisplayShowHomeEnabled(boolean paramBoolean)
  {
    int i;
    if (paramBoolean) {
      i = 2;
    } else {
      i = 0;
    }
    setDisplayOptions(i, 2);
  }
  
  public void setDisplayShowTitleEnabled(boolean paramBoolean)
  {
    int i;
    if (paramBoolean) {
      i = 8;
    } else {
      i = 0;
    }
    setDisplayOptions(i, 8);
  }
  
  public void setDisplayUseLogoEnabled(boolean paramBoolean)
  {
    throw new Runtime("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\n");
  }
  
  public void setElevation(float paramFloat)
  {
    ViewCompat.setElevation(mContainerView, paramFloat);
  }
  
  public void setHideOffset(int paramInt)
  {
    if ((paramInt != 0) && (!mOverlayLayout.isInOverlayMode())) {
      throw new IllegalStateException("Action bar must be in overlay mode (Window.FEATURE_OVERLAY_ACTION_BAR) to set a non-zero hide offset");
    }
    mOverlayLayout.setActionBarHideOffset(paramInt);
  }
  
  public void setHideOnContentScrollEnabled(boolean paramBoolean)
  {
    if ((paramBoolean) && (!mOverlayLayout.isInOverlayMode())) {
      throw new IllegalStateException("Action bar must be in overlay mode (Window.FEATURE_OVERLAY_ACTION_BAR) to enable hide on content scroll");
    }
    mHideOnContentScroll = paramBoolean;
    mOverlayLayout.setHideOnContentScrollEnabled(paramBoolean);
  }
  
  public void setHomeActionContentDescription(int paramInt)
  {
    mDecorToolbar.setNavigationContentDescription(paramInt);
  }
  
  public void setHomeActionContentDescription(CharSequence paramCharSequence)
  {
    mDecorToolbar.setNavigationContentDescription(paramCharSequence);
  }
  
  public void setHomeAsUpIndicator(int paramInt)
  {
    mDecorToolbar.setNavigationIcon(paramInt);
  }
  
  public void setHomeAsUpIndicator(Drawable paramDrawable)
  {
    mDecorToolbar.setNavigationIcon(paramDrawable);
  }
  
  public void setHomeButtonEnabled(boolean paramBoolean)
  {
    mDecorToolbar.setHomeButtonEnabled(paramBoolean);
  }
  
  public void setIcon(int paramInt)
  {
    mDecorToolbar.setIcon(paramInt);
  }
  
  public void setIcon(Drawable paramDrawable)
  {
    mDecorToolbar.setIcon(paramDrawable);
  }
  
  public void setListNavigationCallbacks(SpinnerAdapter paramSpinnerAdapter, ActionBar.OnNavigationListener paramOnNavigationListener)
  {
    mDecorToolbar.setDropdownParams(paramSpinnerAdapter, new NavItemSelectedListener(paramOnNavigationListener));
  }
  
  public void setLogo(int paramInt)
  {
    mDecorToolbar.setLogo(paramInt);
  }
  
  public void setLogo(Drawable paramDrawable)
  {
    mDecorToolbar.setLogo(paramDrawable);
  }
  
  public void setNavigationMode(int paramInt)
  {
    int i = mDecorToolbar.getNavigationMode();
    if (i == 2)
    {
      mSavedTabPosition = getSelectedNavigationIndex();
      selectTab(null);
      mTabScrollView.setVisibility(8);
    }
    if ((i != paramInt) && (!mHasEmbeddedTabs))
    {
      localObject = mOverlayLayout;
      if (localObject != null) {
        ViewCompat.requestApplyInsets((View)localObject);
      }
    }
    mDecorToolbar.setNavigationMode(paramInt);
    boolean bool2 = false;
    if (paramInt == 2)
    {
      ensureTabsExist();
      mTabScrollView.setVisibility(0);
      i = mSavedTabPosition;
      if (i != -1)
      {
        setSelectedNavigationItem(i);
        mSavedTabPosition = -1;
      }
    }
    Object localObject = mDecorToolbar;
    if ((paramInt == 2) && (!mHasEmbeddedTabs)) {
      bool1 = true;
    } else {
      bool1 = false;
    }
    ((DecorToolbar)localObject).setCollapsible(bool1);
    localObject = mOverlayLayout;
    boolean bool1 = bool2;
    if (paramInt == 2)
    {
      bool1 = bool2;
      if (!mHasEmbeddedTabs) {
        bool1 = true;
      }
    }
    ((ActionBarOverlayLayout)localObject).setHasNonEmbeddedTabs(bool1);
  }
  
  public void setSelectedNavigationItem(int paramInt)
  {
    int i = mDecorToolbar.getNavigationMode();
    if (i != 1)
    {
      if (i == 2)
      {
        selectTab((ActionBar.Tab)mTabs.get(paramInt));
        return;
      }
      throw new IllegalStateException("setSelectedNavigationIndex not valid for current navigation mode");
    }
    mDecorToolbar.setDropdownSelectedPosition(paramInt);
  }
  
  public void setShowHideAnimationEnabled(boolean paramBoolean)
  {
    mShowHideAnimationEnabled = paramBoolean;
    if (!paramBoolean)
    {
      ViewPropertyAnimatorCompatSet localViewPropertyAnimatorCompatSet = mCurrentShowAnim;
      if (localViewPropertyAnimatorCompatSet != null) {
        localViewPropertyAnimatorCompatSet.cancel();
      }
    }
  }
  
  public void setSplitBackgroundDrawable(Drawable paramDrawable) {}
  
  public void setStackedBackgroundDrawable(Drawable paramDrawable)
  {
    mContainerView.setStackedBackground(paramDrawable);
  }
  
  public void setSubtitle(int paramInt)
  {
    setSubtitle(mContext.getString(paramInt));
  }
  
  public void setSubtitle(CharSequence paramCharSequence)
  {
    mDecorToolbar.setSubtitle(paramCharSequence);
  }
  
  public void setTitle(int paramInt)
  {
    setTitle(mContext.getString(paramInt));
  }
  
  public void setTitle(CharSequence paramCharSequence)
  {
    mDecorToolbar.setTitle(paramCharSequence);
  }
  
  public void setWindowTitle(CharSequence paramCharSequence)
  {
    mDecorToolbar.setWindowTitle(paramCharSequence);
  }
  
  public void show()
  {
    if (mHiddenByApp)
    {
      mHiddenByApp = false;
      updateVisibility(false);
    }
  }
  
  public void showForSystem()
  {
    if (mHiddenBySystem)
    {
      mHiddenBySystem = false;
      updateVisibility(true);
    }
  }
  
  public ActionMode startActionMode(ActionMode.Callback paramCallback)
  {
    ActionModeImpl localActionModeImpl = mActionMode;
    if (localActionModeImpl != null) {
      localActionModeImpl.finish();
    }
    mOverlayLayout.setHideOnContentScrollEnabled(false);
    mContextView.killMode();
    paramCallback = new ActionModeImpl(mContextView.getContext(), paramCallback);
    if (paramCallback.dispatchOnCreate())
    {
      mActionMode = paramCallback;
      paramCallback.invalidate();
      mContextView.initForMode(paramCallback);
      animateToMode(true);
      mContextView.sendAccessibilityEvent(32);
      return paramCallback;
    }
    return null;
  }
  
  public class ActionModeImpl
    extends ActionMode
    implements MenuBuilder.Callback
  {
    private final Context mActionModeContext;
    private ActionMode.Callback mCallback;
    private WeakReference<View> mCustomView;
    private final MenuBuilder mMenu;
    
    public ActionModeImpl(Context paramContext, ActionMode.Callback paramCallback)
    {
      mActionModeContext = paramContext;
      mCallback = paramCallback;
      mMenu = new MenuBuilder(paramContext).setDefaultShowAsAction(1);
      mMenu.setCallback(this);
    }
    
    public boolean dispatchOnCreate()
    {
      mMenu.stopDispatchingItemsChanged();
      try
      {
        boolean bool = mCallback.onCreateActionMode(this, mMenu);
        mMenu.startDispatchingItemsChanged();
        return bool;
      }
      catch (Throwable localThrowable)
      {
        mMenu.startDispatchingItemsChanged();
        throw localThrowable;
      }
    }
    
    public void finish()
    {
      if (mActionMode != this) {
        return;
      }
      if (!WindowDecorActionBar.checkShowingFlags(mHiddenByApp, mHiddenBySystem, false))
      {
        WindowDecorActionBar localWindowDecorActionBar = WindowDecorActionBar.this;
        mDeferredDestroyActionMode = this;
        mDeferredModeDestroyCallback = mCallback;
      }
      else
      {
        mCallback.onDestroyActionMode(this);
      }
      mCallback = null;
      animateToMode(false);
      mContextView.closeMode();
      mDecorToolbar.getViewGroup().sendAccessibilityEvent(32);
      mOverlayLayout.setHideOnContentScrollEnabled(mHideOnContentScroll);
      mActionMode = null;
    }
    
    public View getCustomView()
    {
      WeakReference localWeakReference = mCustomView;
      if (localWeakReference != null) {
        return (View)localWeakReference.get();
      }
      return null;
    }
    
    public Menu getMenu()
    {
      return mMenu;
    }
    
    public MenuInflater getMenuInflater()
    {
      return new SupportMenuInflater(mActionModeContext);
    }
    
    public CharSequence getSubtitle()
    {
      return mContextView.getSubtitle();
    }
    
    public CharSequence getTitle()
    {
      return mContextView.getTitle();
    }
    
    public void invalidate()
    {
      if (mActionMode != this) {
        return;
      }
      mMenu.stopDispatchingItemsChanged();
      try
      {
        mCallback.onPrepareActionMode(this, mMenu);
        mMenu.startDispatchingItemsChanged();
        return;
      }
      catch (Throwable localThrowable)
      {
        mMenu.startDispatchingItemsChanged();
        throw localThrowable;
      }
    }
    
    public boolean isTitleOptional()
    {
      return mContextView.isTitleOptional();
    }
    
    public void onCloseMenu(MenuBuilder paramMenuBuilder, boolean paramBoolean) {}
    
    public void onCloseSubMenu(SubMenuBuilder paramSubMenuBuilder) {}
    
    public boolean onMenuItemSelected(MenuBuilder paramMenuBuilder, MenuItem paramMenuItem)
    {
      paramMenuBuilder = mCallback;
      if (paramMenuBuilder != null) {
        return paramMenuBuilder.onActionItemClicked(this, paramMenuItem);
      }
      return false;
    }
    
    public void onMenuModeChange(MenuBuilder paramMenuBuilder)
    {
      if (mCallback == null) {
        return;
      }
      invalidate();
      mContextView.showOverflowMenu();
    }
    
    public boolean onSubMenuSelected(SubMenuBuilder paramSubMenuBuilder)
    {
      if (mCallback == null) {
        return false;
      }
      if (!paramSubMenuBuilder.hasVisibleItems()) {
        return true;
      }
      new MenuPopupHelper(getThemedContext(), paramSubMenuBuilder).show();
      return true;
    }
    
    public void setCustomView(View paramView)
    {
      mContextView.setCustomView(paramView);
      mCustomView = new WeakReference(paramView);
    }
    
    public void setSubtitle(int paramInt)
    {
      setSubtitle(mContext.getResources().getString(paramInt));
    }
    
    public void setSubtitle(CharSequence paramCharSequence)
    {
      mContextView.setSubtitle(paramCharSequence);
    }
    
    public void setTitle(int paramInt)
    {
      setTitle(mContext.getResources().getString(paramInt));
    }
    
    public void setTitle(CharSequence paramCharSequence)
    {
      mContextView.setTitle(paramCharSequence);
    }
    
    public void setTitleOptionalHint(boolean paramBoolean)
    {
      super.setTitleOptionalHint(paramBoolean);
      mContextView.setTitleOptional(paramBoolean);
    }
  }
  
  public class TabImpl
    extends ActionBar.Tab
  {
    private ActionBar.TabListener mCallback;
    private CharSequence mContentDesc;
    private View mCustomView;
    private Drawable mIcon;
    private int mPosition = -1;
    private Object mTag;
    private CharSequence mText;
    
    public TabImpl() {}
    
    public ActionBar.TabListener getCallback()
    {
      return mCallback;
    }
    
    public CharSequence getContentDescription()
    {
      return mContentDesc;
    }
    
    public View getCustomView()
    {
      return mCustomView;
    }
    
    public Drawable getIcon()
    {
      return mIcon;
    }
    
    public int getPosition()
    {
      return mPosition;
    }
    
    public Object getTag()
    {
      return mTag;
    }
    
    public CharSequence getText()
    {
      return mText;
    }
    
    public void select()
    {
      selectTab(this);
    }
    
    public ActionBar.Tab setContentDescription(int paramInt)
    {
      return setContentDescription(mContext.getResources().getText(paramInt));
    }
    
    public ActionBar.Tab setContentDescription(CharSequence paramCharSequence)
    {
      mContentDesc = paramCharSequence;
      if (mPosition >= 0) {
        mTabScrollView.updateTab(mPosition);
      }
      return this;
    }
    
    public ActionBar.Tab setCustomView(int paramInt)
    {
      return setCustomView(LayoutInflater.from(getThemedContext()).inflate(paramInt, null));
    }
    
    public ActionBar.Tab setCustomView(View paramView)
    {
      mCustomView = paramView;
      if (mPosition >= 0) {
        mTabScrollView.updateTab(mPosition);
      }
      return this;
    }
    
    public ActionBar.Tab setIcon(int paramInt)
    {
      return setIcon(AppCompatResources.getDrawable(mContext, paramInt));
    }
    
    public ActionBar.Tab setIcon(Drawable paramDrawable)
    {
      mIcon = paramDrawable;
      if (mPosition >= 0) {
        mTabScrollView.updateTab(mPosition);
      }
      return this;
    }
    
    public void setPosition(int paramInt)
    {
      mPosition = paramInt;
    }
    
    public ActionBar.Tab setTabListener(ActionBar.TabListener paramTabListener)
    {
      mCallback = paramTabListener;
      return this;
    }
    
    public ActionBar.Tab setTag(Object paramObject)
    {
      mTag = paramObject;
      return this;
    }
    
    public ActionBar.Tab setText(int paramInt)
    {
      return setText(mContext.getResources().getText(paramInt));
    }
    
    public ActionBar.Tab setText(CharSequence paramCharSequence)
    {
      mText = paramCharSequence;
      if (mPosition >= 0) {
        mTabScrollView.updateTab(mPosition);
      }
      return this;
    }
  }
}
