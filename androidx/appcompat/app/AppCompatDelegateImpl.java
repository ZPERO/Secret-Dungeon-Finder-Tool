package androidx.appcompat.app;

import android.app.Activity;
import android.app.Dialog;
import android.app.UiModeManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.ClassLoaderCreator;
import android.os.Parcelable.Creator;
import android.os.PowerManager;
import android.text.TextUtils;
import android.util.AndroidRuntimeException;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Factory2;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewManager;
import android.view.ViewParent;
import android.view.Window;
import android.view.Window.Callback;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Adapter;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.appcompat.R.attr;
import androidx.appcompat.R.color;
import androidx.appcompat.R.layout;
import androidx.appcompat.R.style;
import androidx.appcompat.R.styleable;
import androidx.appcompat.content.wiki.AppCompatResources;
import androidx.appcompat.view.SupportActionModeWrapper.CallbackWrapper;
import androidx.appcompat.view.SupportMenuInflater;
import androidx.appcompat.view.WindowCallbackWrapper;
import androidx.appcompat.view.menu.ListMenuPresenter;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuBuilder.Callback;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.appcompat.view.menu.MenuPresenter.Callback;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.widget.ActionBarContextView;
import androidx.appcompat.widget.AppCompatDrawableManager;
import androidx.appcompat.widget.ContentFrameLayout;
import androidx.appcompat.widget.ContentFrameLayout.OnAttachListener;
import androidx.appcompat.widget.DecorContentParent;
import androidx.appcompat.widget.FitWindowsViewGroup.OnFitSystemWindowsListener;
import androidx.appcompat.widget.TintTypedArray;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.VectorEnabledTintResources;
import androidx.appcompat.widget.ViewUtils;
import androidx.collection.ArrayMap;
import androidx.core.package_7.ActivityCompat;
import androidx.core.package_7.NavUtils;
import androidx.core.view.KeyEventDispatcher;
import androidx.core.view.KeyEventDispatcher.Component;
import androidx.core.view.LayoutInflaterCompat;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.core.view.ViewPropertyAnimatorListenerAdapter;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Lifecycle.State;
import androidx.lifecycle.LifecycleOwner;
import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;
import org.xmlpull.v1.XmlPullParser;

class AppCompatDelegateImpl
  extends AppCompatDelegate
  implements MenuBuilder.Callback, LayoutInflater.Factory2
{
  private static final boolean DEBUG = false;
  static final String EXCEPTION_HANDLER_MESSAGE_SUFFIX = ". If the resource you are trying to use is a vector resource, you may be referencing it in an unsupported way. See AppCompatDelegate.setCompatVectorFromResourcesEnabled() for more info.";
  private static final boolean IS_PRE_LOLLIPOP;
  private static final boolean sAlwaysOverrideConfiguration;
  private static boolean sInstalledExceptionHandler;
  private static final Map<Class<?>, Integer> sLocalNightModes = new ArrayMap();
  private static final int[] sWindowBackgroundStyleable;
  ActionBar mActionBar;
  private ActionMenuPresenterCallback mActionMenuPresenterCallback;
  androidx.appcompat.view.ActionMode mActionMode;
  PopupWindow mActionModePopup;
  ActionBarContextView mActionModeView;
  private boolean mActivityHandlesUiMode;
  private boolean mActivityHandlesUiModeChecked;
  final AppCompatCallback mAppCompatCallback;
  private AppCompatViewInflater mAppCompatViewInflater;
  private AppCompatWindowCallback mAppCompatWindowCallback;
  private AutoNightModeManager mAutoBatteryNightModeManager;
  private AutoNightModeManager mAutoTimeNightModeManager;
  private boolean mBaseContextAttached;
  private boolean mClosingActionMenu;
  final Context mContext;
  private boolean mCreated;
  private DecorContentParent mDecorContentParent;
  private boolean mEnableDefaultActionBarUp;
  ViewPropertyAnimatorCompat mFadeAnim = null;
  private boolean mFeatureIndeterminateProgress;
  private boolean mFeatureProgress;
  private boolean mHandleNativeActionModes = true;
  boolean mHasActionBar;
  final Object mHost;
  int mInvalidatePanelMenuFeatures;
  boolean mInvalidatePanelMenuPosted;
  private final Runnable mInvalidatePanelMenuRunnable = new Runnable()
  {
    public void run()
    {
      if ((mInvalidatePanelMenuFeatures & 0x1) != 0) {
        doInvalidatePanelMenu(0);
      }
      if ((mInvalidatePanelMenuFeatures & 0x1000) != 0) {
        doInvalidatePanelMenu(108);
      }
      AppCompatDelegateImpl localAppCompatDelegateImpl = AppCompatDelegateImpl.this;
      mInvalidatePanelMenuPosted = false;
      mInvalidatePanelMenuFeatures = 0;
    }
  };
  boolean mIsDestroyed;
  boolean mIsFloating;
  private int mLocalNightMode = -100;
  private boolean mLongPressBackDown;
  MenuInflater mMenuInflater;
  boolean mOverlayActionBar;
  boolean mOverlayActionMode;
  private PanelMenuPresenterCallback mPanelMenuPresenterCallback;
  private PanelFeatureState[] mPanels;
  private PanelFeatureState mPreparedPanel;
  Runnable mShowActionModePopup;
  private boolean mStarted;
  private View mStatusGuard;
  private ViewGroup mSubDecor;
  private boolean mSubDecorInstalled;
  private Rect mTempRect1;
  private Rect mTempRect2;
  private int mThemeResId;
  private CharSequence mTitle;
  private TextView mTitleView;
  Window mWindow;
  boolean mWindowNoTitle;
  
  static
  {
    int i = Build.VERSION.SDK_INT;
    boolean bool2 = false;
    if (i < 21) {
      bool1 = true;
    } else {
      bool1 = false;
    }
    IS_PRE_LOLLIPOP = bool1;
    sWindowBackgroundStyleable = new int[] { 16842836 };
    boolean bool1 = bool2;
    if (Build.VERSION.SDK_INT >= 21)
    {
      bool1 = bool2;
      if (Build.VERSION.SDK_INT <= 25) {
        bool1 = true;
      }
    }
    sAlwaysOverrideConfiguration = bool1;
    if ((IS_PRE_LOLLIPOP) && (!sInstalledExceptionHandler))
    {
      Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler()
      {
        private boolean shouldWrapException(Throwable paramAnonymousThrowable)
        {
          if ((paramAnonymousThrowable instanceof Resources.NotFoundException))
          {
            paramAnonymousThrowable = paramAnonymousThrowable.getMessage();
            if ((paramAnonymousThrowable != null) && ((paramAnonymousThrowable.contains("drawable")) || (paramAnonymousThrowable.contains("Drawable")))) {
              return true;
            }
          }
          return false;
        }
        
        public void uncaughtException(Thread paramAnonymousThread, Throwable paramAnonymousThrowable)
        {
          if (shouldWrapException(paramAnonymousThrowable))
          {
            Object localObject = new StringBuilder();
            ((StringBuilder)localObject).append(paramAnonymousThrowable.getMessage());
            ((StringBuilder)localObject).append(". If the resource you are trying to use is a vector resource, you may be referencing it in an unsupported way. See AppCompatDelegate.setCompatVectorFromResourcesEnabled() for more info.");
            localObject = new Resources.NotFoundException(((StringBuilder)localObject).toString());
            ((Throwable)localObject).initCause(paramAnonymousThrowable.getCause());
            ((Throwable)localObject).setStackTrace(paramAnonymousThrowable.getStackTrace());
            val$defHandler.uncaughtException(paramAnonymousThread, (Throwable)localObject);
            return;
          }
          val$defHandler.uncaughtException(paramAnonymousThread, paramAnonymousThrowable);
        }
      });
      sInstalledExceptionHandler = true;
    }
  }
  
  AppCompatDelegateImpl(Activity paramActivity, AppCompatCallback paramAppCompatCallback)
  {
    this(paramActivity, null, paramAppCompatCallback, paramActivity);
  }
  
  AppCompatDelegateImpl(Dialog paramDialog, AppCompatCallback paramAppCompatCallback)
  {
    this(paramDialog.getContext(), paramDialog.getWindow(), paramAppCompatCallback, paramDialog);
  }
  
  AppCompatDelegateImpl(Context paramContext, Activity paramActivity, AppCompatCallback paramAppCompatCallback)
  {
    this(paramContext, null, paramAppCompatCallback, paramActivity);
  }
  
  AppCompatDelegateImpl(Context paramContext, Window paramWindow, AppCompatCallback paramAppCompatCallback)
  {
    this(paramContext, paramWindow, paramAppCompatCallback, paramContext);
  }
  
  private AppCompatDelegateImpl(Context paramContext, Window paramWindow, AppCompatCallback paramAppCompatCallback, Object paramObject)
  {
    mContext = paramContext;
    mAppCompatCallback = paramAppCompatCallback;
    mHost = paramObject;
    if ((mLocalNightMode == -100) && ((mHost instanceof Dialog)))
    {
      paramContext = tryUnwrapContext();
      if (paramContext != null) {
        mLocalNightMode = paramContext.getDelegate().getLocalNightMode();
      }
    }
    if (mLocalNightMode == -100)
    {
      paramContext = (Integer)sLocalNightModes.get(mHost.getClass());
      if (paramContext != null)
      {
        mLocalNightMode = paramContext.intValue();
        sLocalNightModes.remove(mHost.getClass());
      }
    }
    if (paramWindow != null) {
      attachToWindow(paramWindow);
    }
    AppCompatDrawableManager.preload();
  }
  
  private boolean applyDayNight(boolean paramBoolean)
  {
    if (mIsDestroyed) {
      return false;
    }
    int i = calculateNightMode();
    paramBoolean = updateForNightMode(mapNightMode(i), paramBoolean);
    if (i == 0)
    {
      getAutoTimeNightModeManager().setup();
    }
    else
    {
      localAutoNightModeManager = mAutoTimeNightModeManager;
      if (localAutoNightModeManager != null) {
        localAutoNightModeManager.cleanup();
      }
    }
    if (i == 3)
    {
      getAutoBatteryNightModeManager().setup();
      return paramBoolean;
    }
    AutoNightModeManager localAutoNightModeManager = mAutoBatteryNightModeManager;
    if (localAutoNightModeManager != null) {
      localAutoNightModeManager.cleanup();
    }
    return paramBoolean;
  }
  
  private void applyFixedSizeWindow()
  {
    ContentFrameLayout localContentFrameLayout = (ContentFrameLayout)mSubDecor.findViewById(16908290);
    Object localObject = mWindow.getDecorView();
    localContentFrameLayout.setDecorPadding(((View)localObject).getPaddingLeft(), ((View)localObject).getPaddingTop(), ((View)localObject).getPaddingRight(), ((View)localObject).getPaddingBottom());
    localObject = mContext.obtainStyledAttributes(R.styleable.AppCompatTheme);
    ((TypedArray)localObject).getValue(R.styleable.AppCompatTheme_windowMinWidthMajor, localContentFrameLayout.getMinWidthMajor());
    ((TypedArray)localObject).getValue(R.styleable.AppCompatTheme_windowMinWidthMinor, localContentFrameLayout.getMinWidthMinor());
    if (((TypedArray)localObject).hasValue(R.styleable.AppCompatTheme_windowFixedWidthMajor)) {
      ((TypedArray)localObject).getValue(R.styleable.AppCompatTheme_windowFixedWidthMajor, localContentFrameLayout.getFixedWidthMajor());
    }
    if (((TypedArray)localObject).hasValue(R.styleable.AppCompatTheme_windowFixedWidthMinor)) {
      ((TypedArray)localObject).getValue(R.styleable.AppCompatTheme_windowFixedWidthMinor, localContentFrameLayout.getFixedWidthMinor());
    }
    if (((TypedArray)localObject).hasValue(R.styleable.AppCompatTheme_windowFixedHeightMajor)) {
      ((TypedArray)localObject).getValue(R.styleable.AppCompatTheme_windowFixedHeightMajor, localContentFrameLayout.getFixedHeightMajor());
    }
    if (((TypedArray)localObject).hasValue(R.styleable.AppCompatTheme_windowFixedHeightMinor)) {
      ((TypedArray)localObject).getValue(R.styleable.AppCompatTheme_windowFixedHeightMinor, localContentFrameLayout.getFixedHeightMinor());
    }
    ((TypedArray)localObject).recycle();
    localContentFrameLayout.requestLayout();
  }
  
  private void attachToWindow(Window paramWindow)
  {
    if (mWindow == null)
    {
      Object localObject = paramWindow.getCallback();
      if (!(localObject instanceof AppCompatWindowCallback))
      {
        mAppCompatWindowCallback = new AppCompatWindowCallback((Window.Callback)localObject);
        paramWindow.setCallback(mAppCompatWindowCallback);
        localObject = TintTypedArray.obtainStyledAttributes(mContext, null, sWindowBackgroundStyleable);
        Drawable localDrawable = ((TintTypedArray)localObject).getDrawableIfKnown(0);
        if (localDrawable != null) {
          paramWindow.setBackgroundDrawable(localDrawable);
        }
        ((TintTypedArray)localObject).recycle();
        mWindow = paramWindow;
        return;
      }
      throw new IllegalStateException("AppCompat has already installed itself into the Window");
    }
    throw new IllegalStateException("AppCompat has already installed itself into the Window");
  }
  
  private int calculateNightMode()
  {
    int i = mLocalNightMode;
    if (i != -100) {
      return i;
    }
    return AppCompatDelegate.getDefaultNightMode();
  }
  
  private void cleanupAutoManagers()
  {
    AutoNightModeManager localAutoNightModeManager = mAutoTimeNightModeManager;
    if (localAutoNightModeManager != null) {
      localAutoNightModeManager.cleanup();
    }
    localAutoNightModeManager = mAutoBatteryNightModeManager;
    if (localAutoNightModeManager != null) {
      localAutoNightModeManager.cleanup();
    }
  }
  
  private ViewGroup createSubDecor()
  {
    throw new Runtime("d2j fail translate: java.lang.RuntimeException: fail exe a6 = a5\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.exec(BaseAnalyze.java:92)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.exec(BaseAnalyze.java:1)\n\tat com.googlecode.dex2jar.ir.ts.Cfg.dfs(Cfg.java:255)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.analyze0(BaseAnalyze.java:75)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.analyze(BaseAnalyze.java:69)\n\tat com.googlecode.dex2jar.ir.ts.UnSSATransformer.transform(UnSSATransformer.java:274)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:163)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\nCaused by: java.lang.NullPointerException\n\tat com.googlecode.dex2jar.ir.ts.UnSSATransformer$LiveA.onUseLocal(UnSSATransformer.java:552)\n\tat com.googlecode.dex2jar.ir.ts.UnSSATransformer$LiveA.onUseLocal(UnSSATransformer.java:1)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.onUse(BaseAnalyze.java:166)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.onUse(BaseAnalyze.java:1)\n\tat com.googlecode.dex2jar.ir.ts.Cfg.travel(Cfg.java:331)\n\tat com.googlecode.dex2jar.ir.ts.Cfg.travel(Cfg.java:387)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.exec(BaseAnalyze.java:90)\n\t... 17 more\n");
  }
  
  private void ensureSubDecor()
  {
    if (!mSubDecorInstalled)
    {
      mSubDecor = createSubDecor();
      Object localObject1 = getTitle();
      if (!TextUtils.isEmpty((CharSequence)localObject1))
      {
        Object localObject2 = mDecorContentParent;
        if (localObject2 != null)
        {
          ((DecorContentParent)localObject2).setWindowTitle((CharSequence)localObject1);
        }
        else if (peekSupportActionBar() != null)
        {
          peekSupportActionBar().setWindowTitle((CharSequence)localObject1);
        }
        else
        {
          localObject2 = mTitleView;
          if (localObject2 != null) {
            ((TextView)localObject2).setText((CharSequence)localObject1);
          }
        }
      }
      applyFixedSizeWindow();
      onSubDecorInstalled(mSubDecor);
      mSubDecorInstalled = true;
      localObject1 = getPanelState(0, false);
      if ((!mIsDestroyed) && ((localObject1 == null) || (menu == null))) {
        invalidatePanelMenu(108);
      }
    }
  }
  
  private void ensureWindow()
  {
    if (mWindow == null)
    {
      Object localObject = mHost;
      if ((localObject instanceof Activity)) {
        attachToWindow(((Activity)localObject).getWindow());
      }
    }
    if (mWindow != null) {
      return;
    }
    throw new IllegalStateException("We have not been given a Window");
  }
  
  private AutoNightModeManager getAutoBatteryNightModeManager()
  {
    if (mAutoBatteryNightModeManager == null) {
      mAutoBatteryNightModeManager = new AutoBatteryNightModeManager(mContext);
    }
    return mAutoBatteryNightModeManager;
  }
  
  private void initWindowDecorActionBar()
  {
    ensureSubDecor();
    if (mHasActionBar)
    {
      if (mActionBar != null) {
        return;
      }
      Object localObject = mHost;
      if ((localObject instanceof Activity)) {
        mActionBar = new WindowDecorActionBar((Activity)localObject, mOverlayActionBar);
      } else if ((localObject instanceof Dialog)) {
        mActionBar = new WindowDecorActionBar((Dialog)localObject);
      }
      localObject = mActionBar;
      if (localObject != null) {
        ((ActionBar)localObject).setDefaultDisplayHomeAsUpEnabled(mEnableDefaultActionBarUp);
      }
    }
  }
  
  private boolean initializePanelContent(PanelFeatureState paramPanelFeatureState)
  {
    if (createdPanelView != null)
    {
      shownPanelView = createdPanelView;
      return true;
    }
    if (menu == null) {
      return false;
    }
    if (mPanelMenuPresenterCallback == null) {
      mPanelMenuPresenterCallback = new PanelMenuPresenterCallback();
    }
    shownPanelView = ((View)paramPanelFeatureState.getListMenuView(mPanelMenuPresenterCallback));
    return shownPanelView != null;
  }
  
  private boolean initializePanelDecor(PanelFeatureState paramPanelFeatureState)
  {
    paramPanelFeatureState.setStyle(getActionBarThemedContext());
    decorView = new ListMenuDecorView(listPresenterContext);
    gravity = 81;
    return true;
  }
  
  private boolean initializePanelMenu(PanelFeatureState paramPanelFeatureState)
  {
    Context localContext = mContext;
    if (featureId != 0)
    {
      localObject1 = localContext;
      if (featureId != 108) {}
    }
    else
    {
      localObject1 = localContext;
      if (mDecorContentParent != null)
      {
        TypedValue localTypedValue = new TypedValue();
        Resources.Theme localTheme = localContext.getTheme();
        localTheme.resolveAttribute(R.attr.actionBarTheme, localTypedValue, true);
        localObject1 = null;
        if (resourceId != 0)
        {
          localObject2 = localContext.getResources().newTheme();
          localObject1 = localObject2;
          ((Resources.Theme)localObject2).setTo(localTheme);
          ((Resources.Theme)localObject2).applyStyle(resourceId, true);
          ((Resources.Theme)localObject2).resolveAttribute(R.attr.actionBarWidgetTheme, localTypedValue, true);
        }
        else
        {
          localTheme.resolveAttribute(R.attr.actionBarWidgetTheme, localTypedValue, true);
        }
        Object localObject2 = localObject1;
        if (resourceId != 0)
        {
          localObject2 = localObject1;
          if (localObject1 == null)
          {
            localObject1 = localContext.getResources().newTheme();
            localObject2 = localObject1;
            ((Resources.Theme)localObject1).setTo(localTheme);
          }
          ((Resources.Theme)localObject2).applyStyle(resourceId, true);
        }
        localObject1 = localContext;
        if (localObject2 != null)
        {
          localObject1 = new androidx.appcompat.view.ContextThemeWrapper(localContext, 0);
          ((Context)localObject1).getTheme().setTo((Resources.Theme)localObject2);
        }
      }
    }
    Object localObject1 = new MenuBuilder((Context)localObject1);
    ((MenuBuilder)localObject1).setCallback(this);
    paramPanelFeatureState.setMenu((MenuBuilder)localObject1);
    return true;
  }
  
  private void invalidatePanelMenu(int paramInt)
  {
    mInvalidatePanelMenuFeatures = (1 << paramInt | mInvalidatePanelMenuFeatures);
    if (!mInvalidatePanelMenuPosted)
    {
      ViewCompat.postOnAnimation(mWindow.getDecorView(), mInvalidatePanelMenuRunnable);
      mInvalidatePanelMenuPosted = true;
    }
  }
  
  private boolean isActivityManifestHandlingUiMode()
  {
    if ((!mActivityHandlesUiModeChecked) && ((mHost instanceof Activity)))
    {
      Object localObject1 = mContext.getPackageManager();
      if (localObject1 == null) {
        return false;
      }
      Context localContext = mContext;
      Object localObject2 = mHost;
      try
      {
        localObject1 = ((PackageManager)localObject1).getActivityInfo(new ComponentName(localContext, localObject2.getClass()), 0);
        boolean bool;
        if ((localObject1 != null) && ((configChanges & 0x200) != 0)) {
          bool = true;
        } else {
          bool = false;
        }
        mActivityHandlesUiMode = bool;
      }
      catch (PackageManager.NameNotFoundException localNameNotFoundException)
      {
        Log.d("AppCompatDelegate", "Exception while getting ActivityInfo", localNameNotFoundException);
        mActivityHandlesUiMode = false;
      }
    }
    mActivityHandlesUiModeChecked = true;
    return mActivityHandlesUiMode;
  }
  
  private boolean onKeyDownPanel(int paramInt, KeyEvent paramKeyEvent)
  {
    if (paramKeyEvent.getRepeatCount() == 0)
    {
      PanelFeatureState localPanelFeatureState = getPanelState(paramInt, true);
      if (!isOpen) {
        return preparePanel(localPanelFeatureState, paramKeyEvent);
      }
    }
    return false;
  }
  
  private boolean onKeyUpPanel(int paramInt, KeyEvent paramKeyEvent)
  {
    if (mActionMode != null) {
      return false;
    }
    PanelFeatureState localPanelFeatureState = getPanelState(paramInt, true);
    boolean bool;
    if (paramInt == 0)
    {
      DecorContentParent localDecorContentParent = mDecorContentParent;
      if ((localDecorContentParent != null) && (localDecorContentParent.canShowOverflowMenu()) && (!ViewConfiguration.get(mContext).hasPermanentMenuKey()))
      {
        if (!mDecorContentParent.isOverflowMenuShowing())
        {
          if ((mIsDestroyed) || (!preparePanel(localPanelFeatureState, paramKeyEvent))) {
            break label180;
          }
          bool = mDecorContentParent.showOverflowMenu();
          break label198;
        }
        bool = mDecorContentParent.hideOverflowMenu();
        break label198;
      }
    }
    if ((!isOpen) && (!isHandled))
    {
      if (isPrepared)
      {
        if (refreshMenuContent)
        {
          isPrepared = false;
          bool = preparePanel(localPanelFeatureState, paramKeyEvent);
        }
        else
        {
          bool = true;
        }
        if (bool)
        {
          openPanel(localPanelFeatureState, paramKeyEvent);
          bool = true;
          break label198;
        }
      }
      label180:
      bool = false;
    }
    else
    {
      bool = isOpen;
      closePanel(localPanelFeatureState, true);
    }
    label198:
    if (bool)
    {
      paramKeyEvent = (AudioManager)mContext.getSystemService("audio");
      if (paramKeyEvent != null)
      {
        paramKeyEvent.playSoundEffect(0);
        return bool;
      }
      Log.w("AppCompatDelegate", "Couldn't get audio manager");
    }
    return bool;
  }
  
  private void openPanel(PanelFeatureState paramPanelFeatureState, KeyEvent paramKeyEvent)
  {
    if (!isOpen)
    {
      if (mIsDestroyed) {
        return;
      }
      if (featureId == 0)
      {
        if ((mContext.getResources().getConfiguration().screenLayout & 0xF) == 4) {
          i = 1;
        } else {
          i = 0;
        }
        if (i != 0) {
          return;
        }
      }
      Object localObject = getWindowCallback();
      if ((localObject != null) && (!((Window.Callback)localObject).onMenuOpened(featureId, menu)))
      {
        closePanel(paramPanelFeatureState, true);
        return;
      }
      WindowManager localWindowManager = (WindowManager)mContext.getSystemService("window");
      if (localWindowManager == null) {
        return;
      }
      if (!preparePanel(paramPanelFeatureState, paramKeyEvent)) {
        return;
      }
      if ((decorView != null) && (!refreshDecorView))
      {
        if (createdPanelView != null)
        {
          paramKeyEvent = createdPanelView.getLayoutParams();
          if ((paramKeyEvent != null) && (width == -1))
          {
            i = -1;
            break label337;
          }
        }
      }
      else
      {
        if (decorView == null)
        {
          if (!initializePanelDecor(paramPanelFeatureState)) {
            return;
          }
          if (decorView != null) {}
        }
        else if ((refreshDecorView) && (decorView.getChildCount() > 0))
        {
          decorView.removeAllViews();
        }
        if (!initializePanelContent(paramPanelFeatureState)) {
          return;
        }
        if (!paramPanelFeatureState.hasPanelItems()) {
          return;
        }
        localObject = shownPanelView.getLayoutParams();
        paramKeyEvent = (KeyEvent)localObject;
        if (localObject == null) {
          paramKeyEvent = new ViewGroup.LayoutParams(-2, -2);
        }
        i = background;
        decorView.setBackgroundResource(i);
        localObject = shownPanelView.getParent();
        if ((localObject instanceof ViewGroup)) {
          ((ViewGroup)localObject).removeView(shownPanelView);
        }
        decorView.addView(shownPanelView, paramKeyEvent);
        if (!shownPanelView.hasFocus()) {
          shownPanelView.requestFocus();
        }
      }
      int i = -2;
      label337:
      isHandled = false;
      paramKeyEvent = new WindowManager.LayoutParams(i, -2, x, y, 1002, 8519680, -3);
      gravity = gravity;
      windowAnimations = windowAnimations;
      localWindowManager.addView(decorView, paramKeyEvent);
      isOpen = true;
    }
  }
  
  private boolean performPanelShortcut(PanelFeatureState paramPanelFeatureState, int paramInt1, KeyEvent paramKeyEvent, int paramInt2)
  {
    boolean bool1 = paramKeyEvent.isSystem();
    boolean bool2 = false;
    if (bool1) {
      return false;
    }
    if (!isPrepared)
    {
      bool1 = bool2;
      if (!preparePanel(paramPanelFeatureState, paramKeyEvent)) {}
    }
    else
    {
      bool1 = bool2;
      if (menu != null) {
        bool1 = menu.performShortcut(paramInt1, paramKeyEvent, paramInt2);
      }
    }
    if ((bool1) && ((paramInt2 & 0x1) == 0) && (mDecorContentParent == null)) {
      closePanel(paramPanelFeatureState, true);
    }
    return bool1;
  }
  
  private boolean preparePanel(PanelFeatureState paramPanelFeatureState, KeyEvent paramKeyEvent)
  {
    if (mIsDestroyed) {
      return false;
    }
    boolean bool = isPrepared;
    PanelFeatureState localPanelFeatureState = paramPanelFeatureState;
    if (bool) {
      return true;
    }
    Object localObject = mPreparedPanel;
    if ((localObject != null) && (localObject != localPanelFeatureState)) {
      closePanel((PanelFeatureState)localObject, false);
    }
    localObject = getWindowCallback();
    if (localObject != null) {
      createdPanelView = ((Window.Callback)localObject).onCreatePanelView(featureId);
    }
    int i;
    if ((featureId != 0) && (featureId != 108)) {
      i = 0;
    } else {
      i = 1;
    }
    if (i != 0)
    {
      DecorContentParent localDecorContentParent = mDecorContentParent;
      if (localDecorContentParent != null) {
        localDecorContentParent.setMenuPrepared();
      }
    }
    if ((createdPanelView == null) && ((i == 0) || (!(peekSupportActionBar() instanceof ToolbarActionBar))))
    {
      if ((menu == null) || (refreshMenuContent))
      {
        if (menu == null)
        {
          if (!initializePanelMenu(localPanelFeatureState)) {
            break label476;
          }
          if (menu == null) {
            return false;
          }
        }
        if ((i != 0) && (mDecorContentParent != null))
        {
          if (mActionMenuPresenterCallback == null) {
            mActionMenuPresenterCallback = new ActionMenuPresenterCallback();
          }
          mDecorContentParent.setMenu(menu, mActionMenuPresenterCallback);
        }
        menu.stopDispatchingItemsChanged();
        if (!((Window.Callback)localObject).onCreatePanelMenu(featureId, menu))
        {
          localPanelFeatureState.setMenu(null);
          if (i != 0)
          {
            paramPanelFeatureState = mDecorContentParent;
            if (paramPanelFeatureState != null)
            {
              paramPanelFeatureState.setMenu(null, mActionMenuPresenterCallback);
              return false;
            }
          }
        }
        else
        {
          refreshMenuContent = false;
        }
      }
      else
      {
        menu.stopDispatchingItemsChanged();
        if (frozenActionViewState != null)
        {
          menu.restoreActionViewStates(frozenActionViewState);
          frozenActionViewState = null;
        }
        if (!((Window.Callback)localObject).onPreparePanel(0, createdPanelView, menu))
        {
          if (i != 0)
          {
            paramPanelFeatureState = mDecorContentParent;
            if (paramPanelFeatureState != null) {
              paramPanelFeatureState.setMenu(null, mActionMenuPresenterCallback);
            }
          }
          menu.startDispatchingItemsChanged();
          return false;
        }
        if (paramKeyEvent != null) {
          i = paramKeyEvent.getDeviceId();
        } else {
          i = -1;
        }
        if (KeyCharacterMap.load(i).getKeyboardType() != 1) {
          bool = true;
        } else {
          bool = false;
        }
        qwertyMode = bool;
        menu.setQwertyMode(qwertyMode);
        menu.startDispatchingItemsChanged();
      }
    }
    else
    {
      isPrepared = true;
      isHandled = false;
      mPreparedPanel = paramPanelFeatureState;
      return true;
    }
    label476:
    return false;
  }
  
  private void reopenMenu(MenuBuilder paramMenuBuilder, boolean paramBoolean)
  {
    paramMenuBuilder = mDecorContentParent;
    if ((paramMenuBuilder != null) && (paramMenuBuilder.canShowOverflowMenu()) && ((!ViewConfiguration.get(mContext).hasPermanentMenuKey()) || (mDecorContentParent.isOverflowMenuShowPending())))
    {
      paramMenuBuilder = getWindowCallback();
      if ((mDecorContentParent.isOverflowMenuShowing()) && (paramBoolean))
      {
        mDecorContentParent.hideOverflowMenu();
        if (!mIsDestroyed) {
          paramMenuBuilder.onPanelClosed(108, getPanelState0menu);
        }
      }
      else if ((paramMenuBuilder != null) && (!mIsDestroyed))
      {
        if ((mInvalidatePanelMenuPosted) && ((mInvalidatePanelMenuFeatures & 0x1) != 0))
        {
          mWindow.getDecorView().removeCallbacks(mInvalidatePanelMenuRunnable);
          mInvalidatePanelMenuRunnable.run();
        }
        PanelFeatureState localPanelFeatureState = getPanelState(0, true);
        if ((menu != null) && (!refreshMenuContent) && (paramMenuBuilder.onPreparePanel(0, createdPanelView, menu)))
        {
          paramMenuBuilder.onMenuOpened(108, menu);
          mDecorContentParent.showOverflowMenu();
        }
      }
    }
    else
    {
      paramMenuBuilder = getPanelState(0, true);
      refreshDecorView = true;
      closePanel(paramMenuBuilder, false);
      openPanel(paramMenuBuilder, null);
    }
  }
  
  private int sanitizeWindowFeatureId(int paramInt)
  {
    if (paramInt == 8)
    {
      Log.i("AppCompatDelegate", "You should now use the AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR id when requesting this feature.");
      return 108;
    }
    if (paramInt == 9)
    {
      Log.i("AppCompatDelegate", "You should now use the AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR_OVERLAY id when requesting this feature.");
      return 109;
    }
    return paramInt;
  }
  
  private boolean shouldInheritContext(ViewParent paramViewParent)
  {
    if (paramViewParent == null) {
      return false;
    }
    View localView = mWindow.getDecorView();
    for (;;)
    {
      if (paramViewParent == null) {
        return true;
      }
      if ((paramViewParent == localView) || (!(paramViewParent instanceof View))) {
        break;
      }
      if (ViewCompat.isAttachedToWindow((View)paramViewParent)) {
        return false;
      }
      paramViewParent = paramViewParent.getParent();
    }
    return false;
  }
  
  private void throwFeatureRequestIfSubDecorInstalled()
  {
    if (!mSubDecorInstalled) {
      return;
    }
    throw new AndroidRuntimeException("Window feature must be requested before adding content");
  }
  
  private AppCompatActivity tryUnwrapContext()
  {
    for (Context localContext = mContext; localContext != null; localContext = ((ContextWrapper)localContext).getBaseContext())
    {
      if ((localContext instanceof AppCompatActivity)) {
        return (AppCompatActivity)localContext;
      }
      if (!(localContext instanceof ContextWrapper)) {
        break;
      }
    }
    return null;
  }
  
  private boolean updateForNightMode(int paramInt, boolean paramBoolean)
  {
    int j = mContext.getApplicationContext().getResources().getConfiguration().uiMode & 0x30;
    boolean bool3 = true;
    int i;
    if (paramInt != 1)
    {
      if (paramInt != 2) {
        i = j;
      } else {
        i = 32;
      }
    }
    else {
      i = 16;
    }
    boolean bool4 = isActivityManifestHandlingUiMode();
    boolean bool1 = sAlwaysOverrideConfiguration;
    boolean bool2 = false;
    if (!bool1)
    {
      bool1 = bool2;
      if (i == j) {}
    }
    else
    {
      bool1 = bool2;
      if (!bool4)
      {
        bool1 = bool2;
        if (Build.VERSION.SDK_INT >= 17)
        {
          bool1 = bool2;
          if (!mBaseContextAttached)
          {
            bool1 = bool2;
            if ((mHost instanceof android.view.ContextThemeWrapper))
            {
              Configuration localConfiguration = new Configuration();
              uiMode = (uiMode & 0xFFFFFFCF | i);
              android.view.ContextThemeWrapper localContextThemeWrapper = (android.view.ContextThemeWrapper)mHost;
              try
              {
                localContextThemeWrapper.applyOverrideConfiguration(localConfiguration);
                bool1 = true;
              }
              catch (IllegalStateException localIllegalStateException)
              {
                Log.e("AppCompatDelegate", "updateForNightMode. Calling applyOverrideConfiguration() failed with an exception. Will fall back to using Resources.updateConfiguration()", localIllegalStateException);
                bool1 = bool2;
              }
            }
          }
        }
      }
    }
    j = mContext.getResources().getConfiguration().uiMode & 0x30;
    bool2 = bool1;
    Object localObject;
    if (!bool1)
    {
      bool2 = bool1;
      if (j != i)
      {
        bool2 = bool1;
        if (paramBoolean)
        {
          bool2 = bool1;
          if (!bool4)
          {
            bool2 = bool1;
            if (mBaseContextAttached) {
              if (Build.VERSION.SDK_INT < 17)
              {
                bool2 = bool1;
                if (!mCreated) {}
              }
              else
              {
                localObject = mHost;
                bool2 = bool1;
                if ((localObject instanceof Activity))
                {
                  ActivityCompat.recreate((Activity)localObject);
                  bool2 = true;
                }
              }
            }
          }
        }
      }
    }
    if ((!bool2) && (j != i))
    {
      updateResourcesConfigurationForNightMode(i, bool4);
      bool2 = bool3;
    }
    if (bool2)
    {
      localObject = mHost;
      if ((localObject instanceof AppCompatActivity)) {
        ((AppCompatActivity)localObject).onNightModeChanged(paramInt);
      }
    }
    return bool2;
  }
  
  private void updateResourcesConfigurationForNightMode(int paramInt, boolean paramBoolean)
  {
    Object localObject1 = mContext;
    AppCompatDelegateImpl localAppCompatDelegateImpl = this;
    Object localObject2 = ((Context)localObject1).getResources();
    localObject1 = new Configuration(((Resources)localObject2).getConfiguration());
    uiMode = (paramInt | getConfigurationuiMode & 0xFFFFFFCF);
    ((Resources)localObject2).updateConfiguration((Configuration)localObject1, null);
    if (Build.VERSION.SDK_INT < 26) {
      ResourcesFlusher.flush((Resources)localObject2);
    }
    paramInt = mThemeResId;
    if (paramInt != 0)
    {
      localObject2 = mContext;
      ((Context)localObject2).setTheme(paramInt);
      if (Build.VERSION.SDK_INT >= 23) {
        mContext.getTheme().applyStyle(mThemeResId, true);
      }
    }
    localAppCompatDelegateImpl = this;
    if (paramBoolean)
    {
      localObject2 = mHost;
      if ((localObject2 instanceof Activity))
      {
        localObject2 = (Activity)localObject2;
        if ((localObject2 instanceof LifecycleOwner))
        {
          if (((LifecycleOwner)localObject2).getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            ((Activity)localObject2).onConfigurationChanged((Configuration)localObject1);
          }
        }
        else if (mStarted) {
          ((Activity)localObject2).onConfigurationChanged((Configuration)localObject1);
        }
      }
    }
  }
  
  public void addContentView(View paramView, ViewGroup.LayoutParams paramLayoutParams)
  {
    ensureSubDecor();
    ((ViewGroup)mSubDecor.findViewById(16908290)).addView(paramView, paramLayoutParams);
    mAppCompatWindowCallback.getWrapped().onContentChanged();
  }
  
  public boolean applyDayNight()
  {
    return applyDayNight(true);
  }
  
  public void attachBaseContext(Context paramContext)
  {
    applyDayNight(false);
    mBaseContextAttached = true;
  }
  
  void callOnPanelClosed(int paramInt, PanelFeatureState paramPanelFeatureState, Menu paramMenu)
  {
    Object localObject1 = paramPanelFeatureState;
    Object localObject2 = paramMenu;
    if (paramMenu == null)
    {
      PanelFeatureState localPanelFeatureState = paramPanelFeatureState;
      if (paramPanelFeatureState == null)
      {
        localPanelFeatureState = paramPanelFeatureState;
        if (paramInt >= 0)
        {
          localObject1 = mPanels;
          localPanelFeatureState = paramPanelFeatureState;
          if (paramInt < localObject1.length) {
            localPanelFeatureState = localObject1[paramInt];
          }
        }
      }
      localObject1 = localPanelFeatureState;
      localObject2 = paramMenu;
      if (localPanelFeatureState != null)
      {
        localObject2 = menu;
        localObject1 = localPanelFeatureState;
      }
    }
    if ((localObject1 != null) && (!isOpen)) {
      return;
    }
    if (!mIsDestroyed) {
      mAppCompatWindowCallback.getWrapped().onPanelClosed(paramInt, (Menu)localObject2);
    }
  }
  
  void checkCloseActionMenu(MenuBuilder paramMenuBuilder)
  {
    if (mClosingActionMenu) {
      return;
    }
    mClosingActionMenu = true;
    mDecorContentParent.dismissPopups();
    Window.Callback localCallback = getWindowCallback();
    if ((localCallback != null) && (!mIsDestroyed)) {
      localCallback.onPanelClosed(108, paramMenuBuilder);
    }
    mClosingActionMenu = false;
  }
  
  void closePanel(int paramInt)
  {
    closePanel(getPanelState(paramInt, true), true);
  }
  
  void closePanel(PanelFeatureState paramPanelFeatureState, boolean paramBoolean)
  {
    if ((paramBoolean) && (featureId == 0))
    {
      localObject = mDecorContentParent;
      if ((localObject != null) && (((DecorContentParent)localObject).isOverflowMenuShowing()))
      {
        checkCloseActionMenu(menu);
        return;
      }
    }
    Object localObject = (WindowManager)mContext.getSystemService("window");
    if ((localObject != null) && (isOpen) && (decorView != null))
    {
      ((ViewManager)localObject).removeView(decorView);
      if (paramBoolean) {
        callOnPanelClosed(featureId, paramPanelFeatureState, null);
      }
    }
    isPrepared = false;
    isHandled = false;
    isOpen = false;
    shownPanelView = null;
    refreshDecorView = true;
    if (mPreparedPanel == paramPanelFeatureState) {
      mPreparedPanel = null;
    }
  }
  
  public View createView(View paramView, String paramString, Context paramContext, AttributeSet paramAttributeSet)
  {
    Object localObject = mAppCompatViewInflater;
    boolean bool = false;
    if (localObject == null)
    {
      localObject = mContext.obtainStyledAttributes(R.styleable.AppCompatTheme).getString(R.styleable.AppCompatTheme_viewInflaterClass);
      if ((localObject != null) && (!AppCompatViewInflater.class.getName().equals(localObject))) {
        try
        {
          mAppCompatViewInflater = ((AppCompatViewInflater)Class.forName((String)localObject).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]));
        }
        catch (Throwable localThrowable)
        {
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("Failed to instantiate custom view inflater ");
          localStringBuilder.append((String)localObject);
          localStringBuilder.append(". Falling back to default.");
          Log.i("AppCompatDelegate", localStringBuilder.toString(), localThrowable);
          mAppCompatViewInflater = new AppCompatViewInflater();
        }
      } else {
        mAppCompatViewInflater = new AppCompatViewInflater();
      }
    }
    if (IS_PRE_LOLLIPOP)
    {
      if ((paramAttributeSet instanceof XmlPullParser))
      {
        if (((XmlPullParser)paramAttributeSet).getDepth() > 1) {
          bool = true;
        }
      }
      else {
        bool = shouldInheritContext((ViewParent)paramView);
      }
    }
    else {
      bool = false;
    }
    return mAppCompatViewInflater.createView(paramView, paramString, paramContext, paramAttributeSet, bool, IS_PRE_LOLLIPOP, true, VectorEnabledTintResources.shouldBeUsed());
  }
  
  void dismissPopups()
  {
    Object localObject = mDecorContentParent;
    if (localObject != null) {
      ((DecorContentParent)localObject).dismissPopups();
    }
    if (mActionModePopup != null)
    {
      mWindow.getDecorView().removeCallbacks(mShowActionModePopup);
      if (mActionModePopup.isShowing()) {
        localObject = mActionModePopup;
      }
    }
    try
    {
      ((PopupWindow)localObject).dismiss();
      mActionModePopup = null;
      endOnGoingFadeAnimation();
      localObject = getPanelState(0, false);
      if ((localObject != null) && (menu != null))
      {
        menu.close();
        return;
      }
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      for (;;) {}
    }
  }
  
  boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
  {
    Object localObject = mHost;
    boolean bool = localObject instanceof KeyEventDispatcher.Component;
    int i = 1;
    if ((bool) || ((localObject instanceof AppCompatDialog)))
    {
      localObject = mWindow.getDecorView();
      if ((localObject != null) && (KeyEventDispatcher.dispatchBeforeHierarchy((View)localObject, paramKeyEvent))) {
        return true;
      }
    }
    if ((paramKeyEvent.getKeyCode() == 82) && (mAppCompatWindowCallback.getWrapped().dispatchKeyEvent(paramKeyEvent))) {
      return true;
    }
    int j = paramKeyEvent.getKeyCode();
    if (paramKeyEvent.getAction() != 0) {
      i = 0;
    }
    if (i != 0) {
      return onKeyDown(j, paramKeyEvent);
    }
    return onKeyUp(j, paramKeyEvent);
  }
  
  void doInvalidatePanelMenu(int paramInt)
  {
    PanelFeatureState localPanelFeatureState = getPanelState(paramInt, true);
    if (menu != null)
    {
      Bundle localBundle = new Bundle();
      menu.saveActionViewStates(localBundle);
      if (localBundle.size() > 0) {
        frozenActionViewState = localBundle;
      }
      menu.stopDispatchingItemsChanged();
      menu.clear();
    }
    refreshMenuContent = true;
    refreshDecorView = true;
    if (((paramInt == 108) || (paramInt == 0)) && (mDecorContentParent != null))
    {
      localPanelFeatureState = getPanelState(0, false);
      if (localPanelFeatureState != null)
      {
        isPrepared = false;
        preparePanel(localPanelFeatureState, null);
      }
    }
  }
  
  void endOnGoingFadeAnimation()
  {
    ViewPropertyAnimatorCompat localViewPropertyAnimatorCompat = mFadeAnim;
    if (localViewPropertyAnimatorCompat != null) {
      localViewPropertyAnimatorCompat.cancel();
    }
  }
  
  PanelFeatureState findMenuPanel(Menu paramMenu)
  {
    PanelFeatureState[] arrayOfPanelFeatureState = mPanels;
    int j = 0;
    int i;
    if (arrayOfPanelFeatureState != null) {
      i = arrayOfPanelFeatureState.length;
    } else {
      i = 0;
    }
    while (j < i)
    {
      PanelFeatureState localPanelFeatureState = arrayOfPanelFeatureState[j];
      if ((localPanelFeatureState != null) && (menu == paramMenu)) {
        return localPanelFeatureState;
      }
      j += 1;
    }
    return null;
  }
  
  public View findViewById(int paramInt)
  {
    ensureSubDecor();
    return mWindow.findViewById(paramInt);
  }
  
  final Context getActionBarThemedContext()
  {
    Object localObject1 = getSupportActionBar();
    if (localObject1 != null) {
      localObject1 = ((ActionBar)localObject1).getThemedContext();
    } else {
      localObject1 = null;
    }
    Object localObject2 = localObject1;
    if (localObject1 == null) {
      localObject2 = mContext;
    }
    return localObject2;
  }
  
  final AutoNightModeManager getAutoTimeNightModeManager()
  {
    if (mAutoTimeNightModeManager == null) {
      mAutoTimeNightModeManager = new AutoTimeNightModeManager(TwilightManager.getInstance(mContext));
    }
    return mAutoTimeNightModeManager;
  }
  
  public final ActionBarDrawerToggle.Delegate getDrawerToggleDelegate()
  {
    return new ActionBarDrawableToggleImpl();
  }
  
  public int getLocalNightMode()
  {
    return mLocalNightMode;
  }
  
  public MenuInflater getMenuInflater()
  {
    if (mMenuInflater == null)
    {
      initWindowDecorActionBar();
      Object localObject = mActionBar;
      if (localObject != null) {
        localObject = ((ActionBar)localObject).getThemedContext();
      } else {
        localObject = mContext;
      }
      mMenuInflater = new SupportMenuInflater((Context)localObject);
    }
    return mMenuInflater;
  }
  
  protected PanelFeatureState getPanelState(int paramInt, boolean paramBoolean)
  {
    Object localObject2 = mPanels;
    Object localObject1;
    if (localObject2 != null)
    {
      localObject1 = localObject2;
      if (localObject2.length > paramInt) {}
    }
    else
    {
      localObject1 = new PanelFeatureState[paramInt + 1];
      if (localObject2 != null) {
        System.arraycopy(localObject2, 0, localObject1, 0, localObject2.length);
      }
      mPanels = ((PanelFeatureState[])localObject1);
    }
    Object localObject3 = localObject1[paramInt];
    localObject2 = localObject3;
    if (localObject3 == null)
    {
      localObject2 = new PanelFeatureState(paramInt);
      localObject1[paramInt] = localObject2;
    }
    return localObject2;
  }
  
  ViewGroup getSubDecor()
  {
    return mSubDecor;
  }
  
  public ActionBar getSupportActionBar()
  {
    initWindowDecorActionBar();
    return mActionBar;
  }
  
  final CharSequence getTitle()
  {
    Object localObject = mHost;
    if ((localObject instanceof Activity)) {
      return ((Activity)localObject).getTitle();
    }
    return mTitle;
  }
  
  final Window.Callback getWindowCallback()
  {
    return mWindow.getCallback();
  }
  
  public boolean hasWindowFeature(int paramInt)
  {
    int i = sanitizeWindowFeatureId(paramInt);
    boolean bool;
    if (i != 1)
    {
      if (i != 2)
      {
        if (i != 5)
        {
          if (i != 10)
          {
            if (i != 108)
            {
              if (i != 109) {
                bool = false;
              } else {
                bool = mOverlayActionBar;
              }
            }
            else {
              bool = mHasActionBar;
            }
          }
          else {
            bool = mOverlayActionMode;
          }
        }
        else {
          bool = mFeatureIndeterminateProgress;
        }
      }
      else {
        bool = mFeatureProgress;
      }
    }
    else {
      bool = mWindowNoTitle;
    }
    if (!bool) {
      return mWindow.hasFeature(paramInt);
    }
    return true;
  }
  
  public void installViewFactory()
  {
    LayoutInflater localLayoutInflater = LayoutInflater.from(mContext);
    if (localLayoutInflater.getFactory() == null)
    {
      LayoutInflaterCompat.setFactory2(localLayoutInflater, this);
      return;
    }
    if (!(localLayoutInflater.getFactory2() instanceof AppCompatDelegateImpl)) {
      Log.i("AppCompatDelegate", "The Activity's LayoutInflater already has a Factory installed so we can not install AppCompat's");
    }
  }
  
  public void invalidateOptionsMenu()
  {
    ActionBar localActionBar = getSupportActionBar();
    if ((localActionBar != null) && (localActionBar.invalidateOptionsMenu())) {
      return;
    }
    invalidatePanelMenu(0);
  }
  
  public boolean isHandleNativeActionModesEnabled()
  {
    return mHandleNativeActionModes;
  }
  
  int mapNightMode(int paramInt)
  {
    if (paramInt != -100)
    {
      if (paramInt != -1) {
        if (paramInt != 0)
        {
          if ((paramInt != 1) && (paramInt != 2))
          {
            if (paramInt == 3) {
              return getAutoBatteryNightModeManager().getApplyableNightMode();
            }
            throw new IllegalStateException("Unknown value set for night mode. Please use one of the MODE_NIGHT values from AppCompatDelegate.");
          }
        }
        else
        {
          if ((Build.VERSION.SDK_INT >= 23) && (((UiModeManager)mContext.getSystemService(UiModeManager.class)).getNightMode() == 0)) {
            return -1;
          }
          return getAutoTimeNightModeManager().getApplyableNightMode();
        }
      }
    }
    else {
      return -1;
    }
    return paramInt;
  }
  
  boolean onBackPressed()
  {
    Object localObject = mActionMode;
    if (localObject != null)
    {
      ((androidx.appcompat.view.ActionMode)localObject).finish();
      return true;
    }
    localObject = getSupportActionBar();
    return (localObject != null) && (((ActionBar)localObject).collapseActionView());
  }
  
  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    if ((mHasActionBar) && (mSubDecorInstalled))
    {
      ActionBar localActionBar = getSupportActionBar();
      if (localActionBar != null) {
        localActionBar.onConfigurationChanged(paramConfiguration);
      }
    }
    AppCompatDrawableManager.get().onConfigurationChanged(mContext);
    applyDayNight(false);
  }
  
  public void onCreate(Bundle paramBundle)
  {
    mBaseContextAttached = true;
    applyDayNight(false);
    ensureWindow();
    Object localObject = mHost;
    if ((localObject instanceof Activity))
    {
      paramBundle = null;
      localObject = (Activity)localObject;
    }
    try
    {
      localObject = NavUtils.getParentActivityName((Activity)localObject);
      paramBundle = (Bundle)localObject;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      for (;;) {}
    }
    if (paramBundle != null)
    {
      paramBundle = peekSupportActionBar();
      if (paramBundle == null) {
        mEnableDefaultActionBarUp = true;
      } else {
        paramBundle.setDefaultDisplayHomeAsUpEnabled(true);
      }
    }
    mCreated = true;
  }
  
  public final View onCreateView(View paramView, String paramString, Context paramContext, AttributeSet paramAttributeSet)
  {
    return createView(paramView, paramString, paramContext, paramAttributeSet);
  }
  
  public View onCreateView(String paramString, Context paramContext, AttributeSet paramAttributeSet)
  {
    return onCreateView(null, paramString, paramContext, paramAttributeSet);
  }
  
  public void onDestroy()
  {
    AppCompatDelegate.markStopped(this);
    if (mInvalidatePanelMenuPosted) {
      mWindow.getDecorView().removeCallbacks(mInvalidatePanelMenuRunnable);
    }
    mStarted = false;
    mIsDestroyed = true;
    ActionBar localActionBar = mActionBar;
    if (localActionBar != null) {
      localActionBar.onDestroy();
    }
    cleanupAutoManagers();
  }
  
  boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    boolean bool = true;
    if (paramInt != 4)
    {
      if (paramInt != 82) {
        return false;
      }
      onKeyDownPanel(0, paramKeyEvent);
      return true;
    }
    if ((paramKeyEvent.getFlags() & 0x80) == 0) {
      bool = false;
    }
    mLongPressBackDown = bool;
    return false;
  }
  
  boolean onKeyShortcut(int paramInt, KeyEvent paramKeyEvent)
  {
    Object localObject = getSupportActionBar();
    if ((localObject != null) && (((ActionBar)localObject).onKeyShortcut(paramInt, paramKeyEvent))) {
      return true;
    }
    localObject = mPreparedPanel;
    if ((localObject != null) && (performPanelShortcut((PanelFeatureState)localObject, paramKeyEvent.getKeyCode(), paramKeyEvent, 1)))
    {
      paramKeyEvent = mPreparedPanel;
      if (paramKeyEvent != null)
      {
        isHandled = true;
        return true;
      }
    }
    else
    {
      if (mPreparedPanel == null)
      {
        localObject = getPanelState(0, true);
        preparePanel((PanelFeatureState)localObject, paramKeyEvent);
        boolean bool = performPanelShortcut((PanelFeatureState)localObject, paramKeyEvent.getKeyCode(), paramKeyEvent, 1);
        isPrepared = false;
        if (!bool) {
          break label117;
        }
        return true;
      }
      return false;
    }
    return true;
    label117:
    return false;
  }
  
  boolean onKeyUp(int paramInt, KeyEvent paramKeyEvent)
  {
    if (paramInt != 4)
    {
      if (paramInt != 82) {
        return false;
      }
      onKeyUpPanel(0, paramKeyEvent);
      return true;
    }
    boolean bool = mLongPressBackDown;
    mLongPressBackDown = false;
    paramKeyEvent = getPanelState(0, false);
    if ((paramKeyEvent != null) && (isOpen))
    {
      if (!bool)
      {
        closePanel(paramKeyEvent, true);
        return true;
      }
    }
    else {
      return onBackPressed();
    }
    return true;
  }
  
  public boolean onMenuItemSelected(MenuBuilder paramMenuBuilder, MenuItem paramMenuItem)
  {
    Window.Callback localCallback = getWindowCallback();
    if ((localCallback != null) && (!mIsDestroyed))
    {
      paramMenuBuilder = findMenuPanel(paramMenuBuilder.getRootMenu());
      if (paramMenuBuilder != null) {
        return localCallback.onMenuItemSelected(featureId, paramMenuItem);
      }
    }
    return false;
  }
  
  public void onMenuModeChange(MenuBuilder paramMenuBuilder)
  {
    reopenMenu(paramMenuBuilder, true);
  }
  
  void onMenuOpened(int paramInt)
  {
    if (paramInt == 108)
    {
      ActionBar localActionBar = getSupportActionBar();
      if (localActionBar != null) {
        localActionBar.dispatchMenuVisibilityChanged(true);
      }
    }
  }
  
  void onPanelClosed(int paramInt)
  {
    Object localObject;
    if (paramInt == 108)
    {
      localObject = getSupportActionBar();
      if (localObject != null) {
        ((ActionBar)localObject).dispatchMenuVisibilityChanged(false);
      }
    }
    else if (paramInt == 0)
    {
      localObject = getPanelState(paramInt, true);
      if (isOpen) {
        closePanel((PanelFeatureState)localObject, false);
      }
    }
  }
  
  public void onPostCreate(Bundle paramBundle)
  {
    ensureSubDecor();
  }
  
  public void onPostResume()
  {
    ActionBar localActionBar = getSupportActionBar();
    if (localActionBar != null) {
      localActionBar.setShowHideAnimationEnabled(true);
    }
  }
  
  public void onSaveInstanceState(Bundle paramBundle)
  {
    if (mLocalNightMode != -100) {
      sLocalNightModes.put(mHost.getClass(), Integer.valueOf(mLocalNightMode));
    }
  }
  
  public void onStart()
  {
    mStarted = true;
    applyDayNight();
    AppCompatDelegate.markStarted(this);
  }
  
  public void onStop()
  {
    mStarted = false;
    AppCompatDelegate.markStopped(this);
    ActionBar localActionBar = getSupportActionBar();
    if (localActionBar != null) {
      localActionBar.setShowHideAnimationEnabled(false);
    }
    if ((mHost instanceof Dialog)) {
      cleanupAutoManagers();
    }
  }
  
  void onSubDecorInstalled(ViewGroup paramViewGroup) {}
  
  final ActionBar peekSupportActionBar()
  {
    return mActionBar;
  }
  
  public boolean requestWindowFeature(int paramInt)
  {
    paramInt = sanitizeWindowFeatureId(paramInt);
    if ((mWindowNoTitle) && (paramInt == 108)) {
      return false;
    }
    if ((mHasActionBar) && (paramInt == 1)) {
      mHasActionBar = false;
    }
    if (paramInt != 1)
    {
      if (paramInt != 2)
      {
        if (paramInt != 5)
        {
          if (paramInt != 10)
          {
            if (paramInt != 108)
            {
              if (paramInt != 109) {
                return mWindow.requestFeature(paramInt);
              }
              throwFeatureRequestIfSubDecorInstalled();
              mOverlayActionBar = true;
              return true;
            }
            throwFeatureRequestIfSubDecorInstalled();
            mHasActionBar = true;
            return true;
          }
          throwFeatureRequestIfSubDecorInstalled();
          mOverlayActionMode = true;
          return true;
        }
        throwFeatureRequestIfSubDecorInstalled();
        mFeatureIndeterminateProgress = true;
        return true;
      }
      throwFeatureRequestIfSubDecorInstalled();
      mFeatureProgress = true;
      return true;
    }
    throwFeatureRequestIfSubDecorInstalled();
    mWindowNoTitle = true;
    return true;
  }
  
  public void setContentView(int paramInt)
  {
    ensureSubDecor();
    ViewGroup localViewGroup = (ViewGroup)mSubDecor.findViewById(16908290);
    localViewGroup.removeAllViews();
    LayoutInflater.from(mContext).inflate(paramInt, localViewGroup);
    mAppCompatWindowCallback.getWrapped().onContentChanged();
  }
  
  public void setContentView(View paramView)
  {
    ensureSubDecor();
    ViewGroup localViewGroup = (ViewGroup)mSubDecor.findViewById(16908290);
    localViewGroup.removeAllViews();
    localViewGroup.addView(paramView);
    mAppCompatWindowCallback.getWrapped().onContentChanged();
  }
  
  public void setContentView(View paramView, ViewGroup.LayoutParams paramLayoutParams)
  {
    ensureSubDecor();
    ViewGroup localViewGroup = (ViewGroup)mSubDecor.findViewById(16908290);
    localViewGroup.removeAllViews();
    localViewGroup.addView(paramView, paramLayoutParams);
    mAppCompatWindowCallback.getWrapped().onContentChanged();
  }
  
  public void setHandleNativeActionModesEnabled(boolean paramBoolean)
  {
    mHandleNativeActionModes = paramBoolean;
  }
  
  public void setLocalNightMode(int paramInt)
  {
    if (mLocalNightMode != paramInt)
    {
      mLocalNightMode = paramInt;
      applyDayNight();
    }
  }
  
  public void setSupportActionBar(Toolbar paramToolbar)
  {
    if (!(mHost instanceof Activity)) {
      return;
    }
    ActionBar localActionBar = getSupportActionBar();
    if (!(localActionBar instanceof WindowDecorActionBar))
    {
      mMenuInflater = null;
      if (localActionBar != null) {
        localActionBar.onDestroy();
      }
      if (paramToolbar != null)
      {
        paramToolbar = new ToolbarActionBar(paramToolbar, getTitle(), mAppCompatWindowCallback);
        mActionBar = paramToolbar;
        mWindow.setCallback(paramToolbar.getWrappedWindowCallback());
      }
      else
      {
        mActionBar = null;
        mWindow.setCallback(mAppCompatWindowCallback);
      }
      invalidateOptionsMenu();
      return;
    }
    throw new IllegalStateException("This Activity already has an action bar supplied by the window decor. Do not request Window.FEATURE_SUPPORT_ACTION_BAR and set windowActionBar to false in your theme to use a Toolbar instead.");
  }
  
  public void setTheme(int paramInt)
  {
    mThemeResId = paramInt;
  }
  
  public final void setTitle(CharSequence paramCharSequence)
  {
    mTitle = paramCharSequence;
    Object localObject = mDecorContentParent;
    if (localObject != null)
    {
      ((DecorContentParent)localObject).setWindowTitle(paramCharSequence);
      return;
    }
    if (peekSupportActionBar() != null)
    {
      peekSupportActionBar().setWindowTitle(paramCharSequence);
      return;
    }
    localObject = mTitleView;
    if (localObject != null) {
      ((TextView)localObject).setText(paramCharSequence);
    }
  }
  
  final boolean shouldAnimateActionModeView()
  {
    if (mSubDecorInstalled)
    {
      ViewGroup localViewGroup = mSubDecor;
      if ((localViewGroup != null) && (ViewCompat.isLaidOut(localViewGroup))) {
        return true;
      }
    }
    return false;
  }
  
  public androidx.appcompat.view.ActionMode startSupportActionMode(androidx.appcompat.view.ActionMode.Callback paramCallback)
  {
    if (paramCallback != null)
    {
      Object localObject = mActionMode;
      if (localObject != null) {
        ((androidx.appcompat.view.ActionMode)localObject).finish();
      }
      paramCallback = new ActionModeCallbackWrapperV9(paramCallback);
      localObject = getSupportActionBar();
      if (localObject != null)
      {
        mActionMode = ((ActionBar)localObject).startActionMode(paramCallback);
        localObject = mActionMode;
        if (localObject != null)
        {
          AppCompatCallback localAppCompatCallback = mAppCompatCallback;
          if (localAppCompatCallback != null) {
            localAppCompatCallback.onSupportActionModeStarted((androidx.appcompat.view.ActionMode)localObject);
          }
        }
      }
      if (mActionMode == null) {
        mActionMode = startSupportActionModeFromWindow(paramCallback);
      }
      return mActionMode;
    }
    throw new IllegalArgumentException("ActionMode callback can not be null.");
  }
  
  androidx.appcompat.view.ActionMode startSupportActionModeFromWindow(androidx.appcompat.view.ActionMode.Callback paramCallback)
  {
    throw new Runtime("d2j fail translate: java.lang.RuntimeException: fail exe a15 = a14\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.exec(BaseAnalyze.java:92)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.exec(BaseAnalyze.java:1)\n\tat com.googlecode.dex2jar.ir.ts.Cfg.dfs(Cfg.java:255)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.analyze0(BaseAnalyze.java:75)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.analyze(BaseAnalyze.java:69)\n\tat com.googlecode.dex2jar.ir.ts.UnSSATransformer.transform(UnSSATransformer.java:274)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:163)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\nCaused by: java.lang.NullPointerException\n\tat com.googlecode.dex2jar.ir.ts.UnSSATransformer$LiveA.onUseLocal(UnSSATransformer.java:552)\n\tat com.googlecode.dex2jar.ir.ts.UnSSATransformer$LiveA.onUseLocal(UnSSATransformer.java:1)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.onUse(BaseAnalyze.java:166)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.onUse(BaseAnalyze.java:1)\n\tat com.googlecode.dex2jar.ir.ts.Cfg.travel(Cfg.java:331)\n\tat com.googlecode.dex2jar.ir.ts.Cfg.travel(Cfg.java:387)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.exec(BaseAnalyze.java:90)\n\t... 17 more\n");
  }
  
  int updateStatusGuard(int paramInt)
  {
    Object localObject1 = mActionModeView;
    int i1 = 0;
    int i;
    int j;
    if ((localObject1 != null) && ((((View)localObject1).getLayoutParams() instanceof ViewGroup.MarginLayoutParams)))
    {
      localObject1 = (ViewGroup.MarginLayoutParams)mActionModeView.getLayoutParams();
      boolean bool = mActionModeView.isShown();
      int m = 1;
      int k;
      int n;
      if (bool)
      {
        if (mTempRect1 == null)
        {
          mTempRect1 = new Rect();
          mTempRect2 = new Rect();
        }
        Object localObject2 = mTempRect1;
        Rect localRect = mTempRect2;
        ((Rect)localObject2).set(0, paramInt, 0, 0);
        ViewUtils.computeFitSystemWindows(mSubDecor, (Rect)localObject2, localRect);
        if (top == 0) {
          i = paramInt;
        } else {
          i = 0;
        }
        if (topMargin != i)
        {
          topMargin = paramInt;
          localObject2 = mStatusGuard;
          if (localObject2 == null)
          {
            mStatusGuard = new View(mContext);
            mStatusGuard.setBackgroundColor(mContext.getResources().getColor(R.color.abc_input_method_navigation_guard));
            mSubDecor.addView(mStatusGuard, -1, new ViewGroup.LayoutParams(-1, paramInt));
          }
          else
          {
            localObject2 = ((View)localObject2).getLayoutParams();
            if (height != paramInt)
            {
              height = paramInt;
              mStatusGuard.setLayoutParams((ViewGroup.LayoutParams)localObject2);
            }
          }
          j = 1;
        }
        else
        {
          j = 0;
        }
        if (mStatusGuard == null) {
          m = 0;
        }
        k = m;
        n = j;
        i = paramInt;
        if (!mOverlayActionMode)
        {
          k = m;
          n = j;
          i = paramInt;
          if (m != 0)
          {
            i = 0;
            k = m;
            n = j;
          }
        }
      }
      else
      {
        if (topMargin != 0)
        {
          topMargin = 0;
          j = 1;
        }
        else
        {
          j = 0;
        }
        k = 0;
        i = paramInt;
        n = j;
      }
      j = k;
      paramInt = i;
      if (n != 0)
      {
        mActionModeView.setLayoutParams((ViewGroup.LayoutParams)localObject1);
        j = k;
        paramInt = i;
      }
    }
    else
    {
      j = 0;
    }
    localObject1 = mStatusGuard;
    if (localObject1 != null)
    {
      if (j != 0) {
        i = i1;
      } else {
        i = 8;
      }
      ((View)localObject1).setVisibility(i);
    }
    return paramInt;
  }
  
  private class ActionBarDrawableToggleImpl
    implements ActionBarDrawerToggle.Delegate
  {
    ActionBarDrawableToggleImpl() {}
    
    public Context getActionBarThemedContext()
    {
      return AppCompatDelegateImpl.this.getActionBarThemedContext();
    }
    
    public Drawable getThemeUpIndicator()
    {
      TintTypedArray localTintTypedArray = TintTypedArray.obtainStyledAttributes(getActionBarThemedContext(), null, new int[] { R.attr.homeAsUpIndicator });
      Drawable localDrawable = localTintTypedArray.getDrawable(0);
      localTintTypedArray.recycle();
      return localDrawable;
    }
    
    public boolean isNavigationVisible()
    {
      ActionBar localActionBar = getSupportActionBar();
      return (localActionBar != null) && ((localActionBar.getDisplayOptions() & 0x4) != 0);
    }
    
    public void setActionBarDescription(int paramInt)
    {
      ActionBar localActionBar = getSupportActionBar();
      if (localActionBar != null) {
        localActionBar.setHomeActionContentDescription(paramInt);
      }
    }
    
    public void setActionBarUpIndicator(Drawable paramDrawable, int paramInt)
    {
      ActionBar localActionBar = getSupportActionBar();
      if (localActionBar != null)
      {
        localActionBar.setHomeAsUpIndicator(paramDrawable);
        localActionBar.setHomeActionContentDescription(paramInt);
      }
    }
  }
  
  private final class ActionMenuPresenterCallback
    implements MenuPresenter.Callback
  {
    ActionMenuPresenterCallback() {}
    
    public void onCloseMenu(MenuBuilder paramMenuBuilder, boolean paramBoolean)
    {
      checkCloseActionMenu(paramMenuBuilder);
    }
    
    public boolean onOpenSubMenu(MenuBuilder paramMenuBuilder)
    {
      Window.Callback localCallback = getWindowCallback();
      if (localCallback != null) {
        localCallback.onMenuOpened(108, paramMenuBuilder);
      }
      return true;
    }
  }
  
  class ActionModeCallbackWrapperV9
    implements androidx.appcompat.view.ActionMode.Callback
  {
    private androidx.appcompat.view.ActionMode.Callback mWrapped;
    
    public ActionModeCallbackWrapperV9(androidx.appcompat.view.ActionMode.Callback paramCallback)
    {
      mWrapped = paramCallback;
    }
    
    public boolean onActionItemClicked(androidx.appcompat.view.ActionMode paramActionMode, MenuItem paramMenuItem)
    {
      return mWrapped.onActionItemClicked(paramActionMode, paramMenuItem);
    }
    
    public boolean onCreateActionMode(androidx.appcompat.view.ActionMode paramActionMode, Menu paramMenu)
    {
      return mWrapped.onCreateActionMode(paramActionMode, paramMenu);
    }
    
    public void onDestroyActionMode(androidx.appcompat.view.ActionMode paramActionMode)
    {
      mWrapped.onDestroyActionMode(paramActionMode);
      if (mActionModePopup != null) {
        mWindow.getDecorView().removeCallbacks(mShowActionModePopup);
      }
      if (mActionModeView != null)
      {
        endOnGoingFadeAnimation();
        paramActionMode = AppCompatDelegateImpl.this;
        mFadeAnim = ViewCompat.animate(mActionModeView).alpha(0.0F);
        mFadeAnim.setListener(new ViewPropertyAnimatorListenerAdapter()
        {
          public void onAnimationEnd(View paramAnonymousView)
          {
            mActionModeView.setVisibility(8);
            if (mActionModePopup != null) {
              mActionModePopup.dismiss();
            } else if ((mActionModeView.getParent() instanceof View)) {
              ViewCompat.requestApplyInsets((View)mActionModeView.getParent());
            }
            mActionModeView.removeAllViews();
            mFadeAnim.setListener(null);
            mFadeAnim = null;
          }
        });
      }
      if (mAppCompatCallback != null) {
        mAppCompatCallback.onSupportActionModeFinished(mActionMode);
      }
      mActionMode = null;
    }
    
    public boolean onPrepareActionMode(androidx.appcompat.view.ActionMode paramActionMode, Menu paramMenu)
    {
      return mWrapped.onPrepareActionMode(paramActionMode, paramMenu);
    }
  }
  
  class AppCompatWindowCallback
    extends WindowCallbackWrapper
  {
    AppCompatWindowCallback(Window.Callback paramCallback)
    {
      super();
    }
    
    public boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
    {
      return (AppCompatDelegateImpl.this.dispatchKeyEvent(paramKeyEvent)) || (super.dispatchKeyEvent(paramKeyEvent));
    }
    
    public boolean dispatchKeyShortcutEvent(KeyEvent paramKeyEvent)
    {
      return (super.dispatchKeyShortcutEvent(paramKeyEvent)) || (onKeyShortcut(paramKeyEvent.getKeyCode(), paramKeyEvent));
    }
    
    public void onContentChanged() {}
    
    public boolean onCreatePanelMenu(int paramInt, Menu paramMenu)
    {
      if ((paramInt == 0) && (!(paramMenu instanceof MenuBuilder))) {
        return false;
      }
      return super.onCreatePanelMenu(paramInt, paramMenu);
    }
    
    public boolean onMenuOpened(int paramInt, Menu paramMenu)
    {
      super.onMenuOpened(paramInt, paramMenu);
      onMenuOpened(paramInt);
      return true;
    }
    
    public void onPanelClosed(int paramInt, Menu paramMenu)
    {
      super.onPanelClosed(paramInt, paramMenu);
      onPanelClosed(paramInt);
    }
    
    public boolean onPreparePanel(int paramInt, View paramView, Menu paramMenu)
    {
      MenuBuilder localMenuBuilder;
      if ((paramMenu instanceof MenuBuilder)) {
        localMenuBuilder = (MenuBuilder)paramMenu;
      } else {
        localMenuBuilder = null;
      }
      if ((paramInt == 0) && (localMenuBuilder == null)) {
        return false;
      }
      if (localMenuBuilder != null) {
        localMenuBuilder.setOverrideVisibleItems(true);
      }
      boolean bool = super.onPreparePanel(paramInt, paramView, paramMenu);
      if (localMenuBuilder != null) {
        localMenuBuilder.setOverrideVisibleItems(false);
      }
      return bool;
    }
    
    public void onProvideKeyboardShortcuts(List paramList, Menu paramMenu, int paramInt)
    {
      AppCompatDelegateImpl.PanelFeatureState localPanelFeatureState = getPanelState(0, true);
      if ((localPanelFeatureState != null) && (menu != null))
      {
        super.onProvideKeyboardShortcuts(paramList, menu, paramInt);
        return;
      }
      super.onProvideKeyboardShortcuts(paramList, paramMenu, paramInt);
    }
    
    public android.view.ActionMode onWindowStartingActionMode(android.view.ActionMode.Callback paramCallback)
    {
      if (Build.VERSION.SDK_INT >= 23) {
        return null;
      }
      if (isHandleNativeActionModesEnabled()) {
        return startAsSupportActionMode(paramCallback);
      }
      return super.onWindowStartingActionMode(paramCallback);
    }
    
    public android.view.ActionMode onWindowStartingActionMode(android.view.ActionMode.Callback paramCallback, int paramInt)
    {
      if ((isHandleNativeActionModesEnabled()) && (paramInt == 0)) {
        return startAsSupportActionMode(paramCallback);
      }
      return super.onWindowStartingActionMode(paramCallback, paramInt);
    }
    
    final android.view.ActionMode startAsSupportActionMode(android.view.ActionMode.Callback paramCallback)
    {
      paramCallback = new SupportActionModeWrapper.CallbackWrapper(mContext, paramCallback);
      androidx.appcompat.view.ActionMode localActionMode = startSupportActionMode(paramCallback);
      if (localActionMode != null) {
        return paramCallback.getActionModeWrapper(localActionMode);
      }
      return null;
    }
  }
  
  private class AutoBatteryNightModeManager
    extends AppCompatDelegateImpl.AutoNightModeManager
  {
    private final PowerManager mPowerManager;
    
    AutoBatteryNightModeManager(Context paramContext)
    {
      super();
      mPowerManager = ((PowerManager)paramContext.getSystemService("power"));
    }
    
    IntentFilter createIntentFilterForBroadcastReceiver()
    {
      if (Build.VERSION.SDK_INT >= 21)
      {
        IntentFilter localIntentFilter = new IntentFilter();
        localIntentFilter.addAction("android.os.action.POWER_SAVE_MODE_CHANGED");
        return localIntentFilter;
      }
      return null;
    }
    
    public int getApplyableNightMode()
    {
      if ((Build.VERSION.SDK_INT >= 21) && (mPowerManager.isPowerSaveMode())) {
        return 2;
      }
      return 1;
    }
    
    public void onChange()
    {
      applyDayNight();
    }
  }
  
  abstract class AutoNightModeManager
  {
    private BroadcastReceiver mReceiver;
    
    AutoNightModeManager() {}
    
    void cleanup()
    {
      if (mReceiver != null)
      {
        Context localContext = mContext;
        BroadcastReceiver localBroadcastReceiver = mReceiver;
        try
        {
          localContext.unregisterReceiver(localBroadcastReceiver);
          mReceiver = null;
          return;
        }
        catch (IllegalArgumentException localIllegalArgumentException)
        {
          for (;;) {}
        }
      }
    }
    
    abstract IntentFilter createIntentFilterForBroadcastReceiver();
    
    abstract int getApplyableNightMode();
    
    boolean isListening()
    {
      return mReceiver != null;
    }
    
    abstract void onChange();
    
    void setup()
    {
      cleanup();
      IntentFilter localIntentFilter = createIntentFilterForBroadcastReceiver();
      if (localIntentFilter != null)
      {
        if (localIntentFilter.countActions() == 0) {
          return;
        }
        if (mReceiver == null) {
          mReceiver = new BroadcastReceiver()
          {
            public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
            {
              onChange();
            }
          };
        }
        mContext.registerReceiver(mReceiver, localIntentFilter);
      }
    }
  }
  
  private class AutoTimeNightModeManager
    extends AppCompatDelegateImpl.AutoNightModeManager
  {
    private final TwilightManager mTwilightManager;
    
    AutoTimeNightModeManager(TwilightManager paramTwilightManager)
    {
      super();
      mTwilightManager = paramTwilightManager;
    }
    
    IntentFilter createIntentFilterForBroadcastReceiver()
    {
      IntentFilter localIntentFilter = new IntentFilter();
      localIntentFilter.addAction("android.intent.action.TIME_SET");
      localIntentFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
      localIntentFilter.addAction("android.intent.action.TIME_TICK");
      return localIntentFilter;
    }
    
    public int getApplyableNightMode()
    {
      if (mTwilightManager.isNight()) {
        return 2;
      }
      return 1;
    }
    
    public void onChange()
    {
      applyDayNight();
    }
  }
  
  private class ListMenuDecorView
    extends ContentFrameLayout
  {
    public ListMenuDecorView(Context paramContext)
    {
      super();
    }
    
    private boolean isOutOfBounds(int paramInt1, int paramInt2)
    {
      return (paramInt1 < -5) || (paramInt2 < -5) || (paramInt1 > getWidth() + 5) || (paramInt2 > getHeight() + 5);
    }
    
    public boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
    {
      return (AppCompatDelegateImpl.this.dispatchKeyEvent(paramKeyEvent)) || (super.dispatchKeyEvent(paramKeyEvent));
    }
    
    public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
    {
      if ((paramMotionEvent.getAction() == 0) && (isOutOfBounds((int)paramMotionEvent.getX(), (int)paramMotionEvent.getY())))
      {
        closePanel(0);
        return true;
      }
      return super.onInterceptTouchEvent(paramMotionEvent);
    }
    
    public void setBackgroundResource(int paramInt)
    {
      setBackgroundDrawable(AppCompatResources.getDrawable(getContext(), paramInt));
    }
  }
  
  protected static final class PanelFeatureState
  {
    int background;
    View createdPanelView;
    ViewGroup decorView;
    int featureId;
    Bundle frozenActionViewState;
    Bundle frozenMenuState;
    int gravity;
    boolean isHandled;
    boolean isOpen;
    boolean isPrepared;
    ListMenuPresenter listMenuPresenter;
    Context listPresenterContext;
    MenuBuilder menu;
    public boolean qwertyMode;
    boolean refreshDecorView;
    boolean refreshMenuContent;
    View shownPanelView;
    boolean wasLastOpen;
    int windowAnimations;
    int x;
    int y;
    
    PanelFeatureState(int paramInt)
    {
      featureId = paramInt;
      refreshDecorView = false;
    }
    
    void applyFrozenState()
    {
      MenuBuilder localMenuBuilder = menu;
      if (localMenuBuilder != null)
      {
        Bundle localBundle = frozenMenuState;
        if (localBundle != null)
        {
          localMenuBuilder.restorePresenterStates(localBundle);
          frozenMenuState = null;
        }
      }
    }
    
    public void clearMenuPresenters()
    {
      MenuBuilder localMenuBuilder = menu;
      if (localMenuBuilder != null) {
        localMenuBuilder.removeMenuPresenter(listMenuPresenter);
      }
      listMenuPresenter = null;
    }
    
    MenuView getListMenuView(MenuPresenter.Callback paramCallback)
    {
      if (menu == null) {
        return null;
      }
      if (listMenuPresenter == null)
      {
        listMenuPresenter = new ListMenuPresenter(listPresenterContext, R.layout.abc_list_menu_item_layout);
        listMenuPresenter.setCallback(paramCallback);
        menu.addMenuPresenter(listMenuPresenter);
      }
      return listMenuPresenter.getMenuView(decorView);
    }
    
    public boolean hasPanelItems()
    {
      if (shownPanelView == null) {
        return false;
      }
      if (createdPanelView != null) {
        return true;
      }
      return listMenuPresenter.getAdapter().getCount() > 0;
    }
    
    void onRestoreInstanceState(Parcelable paramParcelable)
    {
      paramParcelable = (SavedState)paramParcelable;
      featureId = featureId;
      wasLastOpen = isOpen;
      frozenMenuState = menuState;
      shownPanelView = null;
      decorView = null;
    }
    
    Parcelable onSaveInstanceState()
    {
      SavedState localSavedState = new SavedState();
      featureId = featureId;
      isOpen = isOpen;
      if (menu != null)
      {
        menuState = new Bundle();
        menu.savePresenterStates(menuState);
      }
      return localSavedState;
    }
    
    void setMenu(MenuBuilder paramMenuBuilder)
    {
      Object localObject = menu;
      if (paramMenuBuilder == localObject) {
        return;
      }
      if (localObject != null) {
        ((MenuBuilder)localObject).removeMenuPresenter(listMenuPresenter);
      }
      menu = paramMenuBuilder;
      if (paramMenuBuilder != null)
      {
        localObject = listMenuPresenter;
        if (localObject != null) {
          paramMenuBuilder.addMenuPresenter((MenuPresenter)localObject);
        }
      }
    }
    
    void setStyle(Context paramContext)
    {
      TypedValue localTypedValue = new TypedValue();
      Resources.Theme localTheme = paramContext.getResources().newTheme();
      localTheme.setTo(paramContext.getTheme());
      localTheme.resolveAttribute(R.attr.actionBarPopupTheme, localTypedValue, true);
      if (resourceId != 0) {
        localTheme.applyStyle(resourceId, true);
      }
      localTheme.resolveAttribute(R.attr.panelMenuListTheme, localTypedValue, true);
      if (resourceId != 0) {
        localTheme.applyStyle(resourceId, true);
      } else {
        localTheme.applyStyle(R.style.Theme_AppCompat_CompactMenu, true);
      }
      paramContext = new androidx.appcompat.view.ContextThemeWrapper(paramContext, 0);
      paramContext.getTheme().setTo(localTheme);
      listPresenterContext = paramContext;
      paramContext = paramContext.obtainStyledAttributes(R.styleable.AppCompatTheme);
      background = paramContext.getResourceId(R.styleable.AppCompatTheme_panelBackground, 0);
      windowAnimations = paramContext.getResourceId(R.styleable.AppCompatTheme_android_windowAnimationStyle, 0);
      paramContext.recycle();
    }
    
    private static class SavedState
      implements Parcelable
    {
      public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator()
      {
        public AppCompatDelegateImpl.PanelFeatureState.SavedState createFromParcel(Parcel paramAnonymousParcel)
        {
          return AppCompatDelegateImpl.PanelFeatureState.SavedState.readFromParcel(paramAnonymousParcel, null);
        }
        
        public AppCompatDelegateImpl.PanelFeatureState.SavedState createFromParcel(Parcel paramAnonymousParcel, ClassLoader paramAnonymousClassLoader)
        {
          return AppCompatDelegateImpl.PanelFeatureState.SavedState.readFromParcel(paramAnonymousParcel, paramAnonymousClassLoader);
        }
        
        public AppCompatDelegateImpl.PanelFeatureState.SavedState[] newArray(int paramAnonymousInt)
        {
          return new AppCompatDelegateImpl.PanelFeatureState.SavedState[paramAnonymousInt];
        }
      };
      int featureId;
      boolean isOpen;
      Bundle menuState;
      
      SavedState() {}
      
      static SavedState readFromParcel(Parcel paramParcel, ClassLoader paramClassLoader)
      {
        SavedState localSavedState = new SavedState();
        featureId = paramParcel.readInt();
        int i = paramParcel.readInt();
        boolean bool = true;
        if (i != 1) {
          bool = false;
        }
        isOpen = bool;
        if (isOpen) {
          menuState = paramParcel.readBundle(paramClassLoader);
        }
        return localSavedState;
      }
      
      public int describeContents()
      {
        return 0;
      }
      
      public void writeToParcel(Parcel paramParcel, int paramInt)
      {
        throw new Runtime("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.provideAs(TypeTransformer.java:780)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.e1expr(TypeTransformer.java:496)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:713)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:698)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.s1stmt(TypeTransformer.java:810)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.sxStmt(TypeTransformer.java:840)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:206)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\n");
      }
    }
  }
  
  private final class PanelMenuPresenterCallback
    implements MenuPresenter.Callback
  {
    PanelMenuPresenterCallback() {}
    
    public void onCloseMenu(MenuBuilder paramMenuBuilder, boolean paramBoolean)
    {
      MenuBuilder localMenuBuilder = paramMenuBuilder.getRootMenu();
      int i;
      if (localMenuBuilder != paramMenuBuilder) {
        i = 1;
      } else {
        i = 0;
      }
      AppCompatDelegateImpl localAppCompatDelegateImpl = AppCompatDelegateImpl.this;
      if (i != 0) {
        paramMenuBuilder = localMenuBuilder;
      }
      paramMenuBuilder = localAppCompatDelegateImpl.findMenuPanel(paramMenuBuilder);
      if (paramMenuBuilder != null)
      {
        if (i != 0)
        {
          callOnPanelClosed(featureId, paramMenuBuilder, localMenuBuilder);
          closePanel(paramMenuBuilder, true);
          return;
        }
        closePanel(paramMenuBuilder, paramBoolean);
      }
    }
    
    public boolean onOpenSubMenu(MenuBuilder paramMenuBuilder)
    {
      if ((paramMenuBuilder == null) && (mHasActionBar))
      {
        Window.Callback localCallback = getWindowCallback();
        if ((localCallback != null) && (!mIsDestroyed)) {
          localCallback.onMenuOpened(108, paramMenuBuilder);
        }
      }
      return true;
    }
  }
}
