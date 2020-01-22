package androidx.recyclerview.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.Observable;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.ClassLoaderCreator;
import android.os.Parcelable.Creator;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.Display;
import android.view.FocusFinder;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityRecord;
import android.view.animation.Interpolator;
import android.widget.EdgeEffect;
import android.widget.OverScroller;
import androidx.core.os.TraceCompat;
import androidx.core.util.Preconditions;
import androidx.core.view.MotionEventCompat;
import androidx.core.view.NestedScrollingChild2;
import androidx.core.view.NestedScrollingChildHelper;
import androidx.core.view.ScrollingView;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewConfigurationCompat;
import androidx.core.view.accessibility.AccessibilityEventCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat.CollectionInfoCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat.CollectionItemInfoCompat;
import androidx.core.widget.EdgeEffectCompat;
import androidx.customview.view.AbsSavedState;
import androidx.recyclerview.R.dimen;
import androidx.recyclerview.R.styleable;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecyclerView
  extends ViewGroup
  implements ScrollingView, NestedScrollingChild2
{
  static final boolean ALLOW_SIZE_IN_UNSPECIFIED_SPEC;
  static final boolean ALLOW_THREAD_GAP_WORK;
  private static final int[] CLIP_TO_PADDING_ATTR;
  static final boolean DEBUG = false;
  static final int DEFAULT_ORIENTATION = 1;
  static final boolean DISPATCH_TEMP_DETACH = false;
  private static final boolean FORCE_ABS_FOCUS_SEARCH_DIRECTION;
  static final boolean FORCE_INVALIDATE_DISPLAY_LIST;
  static final long FOREVER_NS = Long.MAX_VALUE;
  public static final int HORIZONTAL = 0;
  private static final boolean IGNORE_DETACHED_FOCUSED_CHILD;
  private static final int INVALID_POINTER = -1;
  public static final int INVALID_TYPE = -1;
  private static final Class<?>[] LAYOUT_MANAGER_CONSTRUCTOR_SIGNATURE = { Context.class, AttributeSet.class, Integer.TYPE, Integer.TYPE };
  static final int MAX_SCROLL_DURATION = 2000;
  private static final int[] NESTED_SCROLLING_ATTRS = { 16843830 };
  public static final long NO_ID = -1L;
  public static final int NO_POSITION = -1;
  static final boolean POST_UPDATES_ON_ANIMATION;
  public static final int SCROLL_STATE_DRAGGING = 1;
  public static final int SCROLL_STATE_IDLE = 0;
  public static final int SCROLL_STATE_SETTLING = 2;
  static final String TAG = "RecyclerView";
  public static final int TOUCH_SLOP_DEFAULT = 0;
  public static final int TOUCH_SLOP_PAGING = 1;
  static final String TRACE_BIND_VIEW_TAG = "RV OnBindView";
  static final String TRACE_CREATE_VIEW_TAG = "RV CreateView";
  private static final String TRACE_HANDLE_ADAPTER_UPDATES_TAG = "RV PartialInvalidate";
  static final String TRACE_NESTED_PREFETCH_TAG = "RV Nested Prefetch";
  private static final String TRACE_ON_DATA_SET_CHANGE_LAYOUT_TAG = "RV FullInvalidate";
  private static final String TRACE_ON_LAYOUT_TAG = "RV OnLayout";
  static final String TRACE_PREFETCH_TAG = "RV Prefetch";
  static final String TRACE_SCROLL_TAG = "RV Scroll";
  static final boolean VERBOSE_TRACING = false;
  public static final int VERTICAL = 1;
  static final Interpolator sQuinticInterpolator = new Interpolator()
  {
    public float getInterpolation(float paramAnonymousFloat)
    {
      paramAnonymousFloat -= 1.0F;
      return paramAnonymousFloat * paramAnonymousFloat * paramAnonymousFloat * paramAnonymousFloat * paramAnonymousFloat + 1.0F;
    }
  };
  RecyclerViewAccessibilityDelegate mAccessibilityDelegate;
  private final AccessibilityManager mAccessibilityManager;
  private OnItemTouchListener mActiveOnItemTouchListener;
  Adapter mAdapter;
  AdapterHelper mAdapterHelper;
  boolean mAdapterUpdateDuringMeasure;
  private EdgeEffect mBottomGlow;
  private ChildDrawingOrderCallback mChildDrawingOrderCallback;
  ChildHelper mChildHelper;
  boolean mClipToPadding;
  boolean mDataSetHasChangedAfterLayout = false;
  boolean mDispatchItemsChangedEvent = false;
  private int mDispatchScrollCounter = 0;
  private int mEatenAccessibilityChangeFlags;
  private EdgeEffectFactory mEdgeEffectFactory = new EdgeEffectFactory();
  boolean mEnableFastScroller;
  boolean mFirstLayoutComplete;
  GapWorker mGapWorker;
  boolean mHasFixedSize;
  private boolean mIgnoreMotionEventTillDown;
  private int mInitialTouchX;
  private int mInitialTouchY;
  private int mInterceptRequestLayoutDepth = 0;
  boolean mIsAttached;
  ItemAnimator mItemAnimator = new DefaultItemAnimator();
  private RecyclerView.ItemAnimator.ItemAnimatorListener mItemAnimatorListener;
  private Runnable mItemAnimatorRunner;
  final ArrayList<ItemDecoration> mItemDecorations = new ArrayList();
  boolean mItemsAddedOrRemoved;
  boolean mItemsChanged;
  private int mLastTouchX;
  private int mLastTouchY;
  LayoutManager mLayout;
  boolean mLayoutFrozen;
  private int mLayoutOrScrollCounter = 0;
  boolean mLayoutWasDefered;
  private EdgeEffect mLeftGlow;
  private final int mMaxFlingVelocity;
  private final int mMinFlingVelocity;
  private final int[] mMinMaxLayoutPositions;
  private final int[] mNestedOffsets;
  private final RecyclerViewDataObserver mObserver = new RecyclerViewDataObserver();
  private List<OnChildAttachStateChangeListener> mOnChildAttachStateListeners;
  private OnFlingListener mOnFlingListener;
  private final ArrayList<OnItemTouchListener> mOnItemTouchListeners = new ArrayList();
  final List<ViewHolder> mPendingAccessibilityImportanceChange;
  private SavedState mPendingSavedState;
  boolean mPostedAnimatorRunner;
  GapWorker.LayoutPrefetchRegistryImpl mPrefetchRegistry;
  private boolean mPreserveFocusAfterLayout;
  final Recycler mRecycler = new Recycler();
  RecyclerListener mRecyclerListener;
  private EdgeEffect mRightGlow;
  private float mScaledHorizontalScrollFactor = Float.MIN_VALUE;
  private float mScaledVerticalScrollFactor = Float.MIN_VALUE;
  final int[] mScrollConsumed;
  private OnScrollListener mScrollListener;
  private List<OnScrollListener> mScrollListeners;
  private final int[] mScrollOffset;
  private int mScrollPointerId = -1;
  private int mScrollState = 0;
  final int[] mScrollStepConsumed;
  private NestedScrollingChildHelper mScrollingChildHelper;
  final State mState;
  final Rect mTempRect = new Rect();
  private final Rect mTempRect2 = new Rect();
  final RectF mTempRectF = new RectF();
  private EdgeEffect mTopGlow;
  private int mTouchSlop;
  final Runnable mUpdateChildViewsRunnable = new Runnable()
  {
    public void run()
    {
      if (mFirstLayoutComplete)
      {
        if (isLayoutRequested()) {
          return;
        }
        if (!mIsAttached)
        {
          requestLayout();
          return;
        }
        if (mLayoutFrozen)
        {
          mLayoutWasDefered = true;
          return;
        }
        consumePendingUpdateOperations();
      }
    }
  };
  private VelocityTracker mVelocityTracker;
  final ViewFlinger mViewFlinger;
  private final ViewInfoStore.ProcessCallback mViewInfoProcessCallback;
  final ViewInfoStore mViewInfoStore = new ViewInfoStore();
  
  static
  {
    CLIP_TO_PADDING_ATTR = new int[] { 16842987 };
    boolean bool;
    if ((Build.VERSION.SDK_INT != 18) && (Build.VERSION.SDK_INT != 19) && (Build.VERSION.SDK_INT != 20)) {
      bool = false;
    } else {
      bool = true;
    }
    FORCE_INVALIDATE_DISPLAY_LIST = bool;
    if (Build.VERSION.SDK_INT >= 23) {
      bool = true;
    } else {
      bool = false;
    }
    ALLOW_SIZE_IN_UNSPECIFIED_SPEC = bool;
    if (Build.VERSION.SDK_INT >= 16) {
      bool = true;
    } else {
      bool = false;
    }
    POST_UPDATES_ON_ANIMATION = bool;
    if (Build.VERSION.SDK_INT >= 21) {
      bool = true;
    } else {
      bool = false;
    }
    ALLOW_THREAD_GAP_WORK = bool;
    if (Build.VERSION.SDK_INT <= 15) {
      bool = true;
    } else {
      bool = false;
    }
    FORCE_ABS_FOCUS_SEARCH_DIRECTION = bool;
    if (Build.VERSION.SDK_INT <= 15) {
      bool = true;
    } else {
      bool = false;
    }
    IGNORE_DETACHED_FOCUSED_CHILD = bool;
  }
  
  public RecyclerView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public RecyclerView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public RecyclerView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    boolean bool2 = true;
    mPreserveFocusAfterLayout = true;
    mViewFlinger = new ViewFlinger();
    if (ALLOW_THREAD_GAP_WORK) {
      localObject = new GapWorker.LayoutPrefetchRegistryImpl();
    } else {
      localObject = null;
    }
    mPrefetchRegistry = ((GapWorker.LayoutPrefetchRegistryImpl)localObject);
    mState = new State();
    mItemsAddedOrRemoved = false;
    mItemsChanged = false;
    mItemAnimatorListener = new ItemAnimatorRestoreListener();
    mPostedAnimatorRunner = false;
    mMinMaxLayoutPositions = new int[2];
    mScrollOffset = new int[2];
    mScrollConsumed = new int[2];
    mNestedOffsets = new int[2];
    mScrollStepConsumed = new int[2];
    mPendingAccessibilityImportanceChange = new ArrayList();
    mItemAnimatorRunner = new Runnable()
    {
      public void run()
      {
        if (mItemAnimator != null) {
          mItemAnimator.runPendingAnimations();
        }
        mPostedAnimatorRunner = false;
      }
    };
    mViewInfoProcessCallback = new ViewInfoStore.ProcessCallback()
    {
      public void processAppeared(RecyclerView.ViewHolder paramAnonymousViewHolder, RecyclerView.ItemAnimator.ItemHolderInfo paramAnonymousItemHolderInfo1, RecyclerView.ItemAnimator.ItemHolderInfo paramAnonymousItemHolderInfo2)
      {
        animateAppearance(paramAnonymousViewHolder, paramAnonymousItemHolderInfo1, paramAnonymousItemHolderInfo2);
      }
      
      public void processDisappeared(RecyclerView.ViewHolder paramAnonymousViewHolder, RecyclerView.ItemAnimator.ItemHolderInfo paramAnonymousItemHolderInfo1, RecyclerView.ItemAnimator.ItemHolderInfo paramAnonymousItemHolderInfo2)
      {
        mRecycler.unscrapView(paramAnonymousViewHolder);
        animateDisappearance(paramAnonymousViewHolder, paramAnonymousItemHolderInfo1, paramAnonymousItemHolderInfo2);
      }
      
      public void processPersistent(RecyclerView.ViewHolder paramAnonymousViewHolder, RecyclerView.ItemAnimator.ItemHolderInfo paramAnonymousItemHolderInfo1, RecyclerView.ItemAnimator.ItemHolderInfo paramAnonymousItemHolderInfo2)
      {
        paramAnonymousViewHolder.setIsRecyclable(false);
        if (mDataSetHasChangedAfterLayout)
        {
          if (mItemAnimator.animateChange(paramAnonymousViewHolder, paramAnonymousViewHolder, paramAnonymousItemHolderInfo1, paramAnonymousItemHolderInfo2)) {
            postAnimationRunner();
          }
        }
        else if (mItemAnimator.animatePersistence(paramAnonymousViewHolder, paramAnonymousItemHolderInfo1, paramAnonymousItemHolderInfo2)) {
          postAnimationRunner();
        }
      }
      
      public void unused(RecyclerView.ViewHolder paramAnonymousViewHolder)
      {
        mLayout.removeAndRecycleView(itemView, mRecycler);
      }
    };
    if (paramAttributeSet != null)
    {
      localObject = paramContext.obtainStyledAttributes(paramAttributeSet, CLIP_TO_PADDING_ATTR, paramInt, 0);
      mClipToPadding = ((TypedArray)localObject).getBoolean(0, true);
      ((TypedArray)localObject).recycle();
    }
    else
    {
      mClipToPadding = true;
    }
    setScrollContainer(true);
    setFocusableInTouchMode(true);
    Object localObject = ViewConfiguration.get(paramContext);
    mTouchSlop = ((ViewConfiguration)localObject).getScaledTouchSlop();
    mScaledHorizontalScrollFactor = ViewConfigurationCompat.getScaledHorizontalScrollFactor((ViewConfiguration)localObject, paramContext);
    mScaledVerticalScrollFactor = ViewConfigurationCompat.getScaledVerticalScrollFactor((ViewConfiguration)localObject, paramContext);
    mMinFlingVelocity = ((ViewConfiguration)localObject).getScaledMinimumFlingVelocity();
    mMaxFlingVelocity = ((ViewConfiguration)localObject).getScaledMaximumFlingVelocity();
    boolean bool1;
    if (getOverScrollMode() == 2) {
      bool1 = true;
    } else {
      bool1 = false;
    }
    setWillNotDraw(bool1);
    mItemAnimator.setListener(mItemAnimatorListener);
    initAdapterManager();
    initChildrenHelper();
    initAutofill();
    if (ViewCompat.getImportantForAccessibility(this) == 0) {
      ViewCompat.setImportantForAccessibility(this, 1);
    }
    mAccessibilityManager = ((AccessibilityManager)getContext().getSystemService("accessibility"));
    setAccessibilityDelegateCompat(new RecyclerViewAccessibilityDelegate(this));
    if (paramAttributeSet != null)
    {
      localObject = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.RecyclerView, paramInt, 0);
      String str = ((TypedArray)localObject).getString(R.styleable.RecyclerView_layoutManager);
      if (((TypedArray)localObject).getInt(R.styleable.RecyclerView_android_descendantFocusability, -1) == -1) {
        setDescendantFocusability(262144);
      }
      mEnableFastScroller = ((TypedArray)localObject).getBoolean(R.styleable.RecyclerView_fastScrollEnabled, false);
      if (mEnableFastScroller) {
        initFastScroller((StateListDrawable)((TypedArray)localObject).getDrawable(R.styleable.RecyclerView_fastScrollVerticalThumbDrawable), ((TypedArray)localObject).getDrawable(R.styleable.RecyclerView_fastScrollVerticalTrackDrawable), (StateListDrawable)((TypedArray)localObject).getDrawable(R.styleable.RecyclerView_fastScrollHorizontalThumbDrawable), ((TypedArray)localObject).getDrawable(R.styleable.RecyclerView_fastScrollHorizontalTrackDrawable));
      }
      ((TypedArray)localObject).recycle();
      createLayoutManager(paramContext, str, paramAttributeSet, paramInt, 0);
      bool1 = bool2;
      if (Build.VERSION.SDK_INT >= 21)
      {
        paramContext = paramContext.obtainStyledAttributes(paramAttributeSet, NESTED_SCROLLING_ATTRS, paramInt, 0);
        bool1 = paramContext.getBoolean(0, true);
        paramContext.recycle();
      }
    }
    else
    {
      setDescendantFocusability(262144);
      bool1 = bool2;
    }
    setNestedScrollingEnabled(bool1);
  }
  
  private void addAnimatingView(ViewHolder paramViewHolder)
  {
    View localView = itemView;
    int i;
    if (localView.getParent() == this) {
      i = 1;
    } else {
      i = 0;
    }
    mRecycler.unscrapView(getChildViewHolder(localView));
    if (paramViewHolder.isTmpDetached())
    {
      mChildHelper.attachViewToParent(localView, -1, localView.getLayoutParams(), true);
      return;
    }
    if (i == 0)
    {
      mChildHelper.addView(localView, true);
      return;
    }
    mChildHelper.hide(localView);
  }
  
  private void animateChange(ViewHolder paramViewHolder1, ViewHolder paramViewHolder2, RecyclerView.ItemAnimator.ItemHolderInfo paramItemHolderInfo1, RecyclerView.ItemAnimator.ItemHolderInfo paramItemHolderInfo2, boolean paramBoolean1, boolean paramBoolean2)
  {
    paramViewHolder1.setIsRecyclable(false);
    if (paramBoolean1) {
      addAnimatingView(paramViewHolder1);
    }
    if (paramViewHolder1 != paramViewHolder2)
    {
      if (paramBoolean2) {
        addAnimatingView(paramViewHolder2);
      }
      mShadowedHolder = paramViewHolder2;
      addAnimatingView(paramViewHolder1);
      mRecycler.unscrapView(paramViewHolder1);
      paramViewHolder2.setIsRecyclable(false);
      mShadowingHolder = paramViewHolder1;
    }
    if (mItemAnimator.animateChange(paramViewHolder1, paramViewHolder2, paramItemHolderInfo1, paramItemHolderInfo2)) {
      postAnimationRunner();
    }
  }
  
  private void cancelTouch()
  {
    resetTouch();
    setScrollState(0);
  }
  
  static void clearNestedRecyclerViewIfNotNested(ViewHolder paramViewHolder)
  {
    if (mNestedRecyclerView != null)
    {
      Object localObject = (View)mNestedRecyclerView.get();
      while (localObject != null)
      {
        if (localObject == itemView) {
          return;
        }
        localObject = ((View)localObject).getParent();
        if ((localObject instanceof View)) {
          localObject = (View)localObject;
        } else {
          localObject = null;
        }
      }
      mNestedRecyclerView = null;
    }
  }
  
  private void createLayoutManager(Context paramContext, String paramString, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    if (paramString != null)
    {
      paramString = paramString.trim();
      if (!paramString.isEmpty())
      {
        String str = getFullClassName(paramContext, paramString);
        try
        {
          boolean bool = isInEditMode();
          if (bool) {
            paramString = getClass().getClassLoader();
          } else {
            paramString = paramContext.getClassLoader();
          }
          Class localClass = paramString.loadClass(str).asSubclass(LayoutManager.class);
          Object localObject = null;
          paramString = LAYOUT_MANAGER_CONSTRUCTOR_SIGNATURE;
          try
          {
            paramString = localClass.getConstructor(paramString);
            paramContext = new Object[] { paramContext, paramAttributeSet, Integer.valueOf(paramInt1), Integer.valueOf(paramInt2) };
          }
          catch (NoSuchMethodException paramContext) {}
          try
          {
            paramString = localClass.getConstructor(new Class[0]);
            paramContext = localObject;
            paramString.setAccessible(true);
            paramContext = paramString.newInstance(paramContext);
            paramContext = (LayoutManager)paramContext;
            setLayoutManager(paramContext);
            return;
          }
          catch (NoSuchMethodException paramString)
          {
            paramString.initCause(paramContext);
            paramContext = new StringBuilder();
            paramContext.append(paramAttributeSet.getPositionDescription());
            paramContext.append(": Error creating LayoutManager ");
            paramContext.append(str);
            paramContext = new IllegalStateException(paramContext.toString(), paramString);
            throw paramContext;
          }
          return;
        }
        catch (ClassCastException paramContext)
        {
          paramString = new StringBuilder();
          paramString.append(paramAttributeSet.getPositionDescription());
          paramString.append(": Class is not a LayoutManager ");
          paramString.append(str);
          throw new IllegalStateException(paramString.toString(), paramContext);
        }
        catch (IllegalAccessException paramContext)
        {
          paramString = new StringBuilder();
          paramString.append(paramAttributeSet.getPositionDescription());
          paramString.append(": Cannot access non-public constructor ");
          paramString.append(str);
          throw new IllegalStateException(paramString.toString(), paramContext);
        }
        catch (InstantiationException paramContext)
        {
          paramString = new StringBuilder();
          paramString.append(paramAttributeSet.getPositionDescription());
          paramString.append(": Could not instantiate the LayoutManager: ");
          paramString.append(str);
          throw new IllegalStateException(paramString.toString(), paramContext);
        }
        catch (InvocationTargetException paramContext)
        {
          paramString = new StringBuilder();
          paramString.append(paramAttributeSet.getPositionDescription());
          paramString.append(": Could not instantiate the LayoutManager: ");
          paramString.append(str);
          throw new IllegalStateException(paramString.toString(), paramContext);
        }
        catch (ClassNotFoundException paramContext)
        {
          paramString = new StringBuilder();
          paramString.append(paramAttributeSet.getPositionDescription());
          paramString.append(": Unable to find LayoutManager ");
          paramString.append(str);
          throw new IllegalStateException(paramString.toString(), paramContext);
        }
      }
    }
  }
  
  private boolean didChildRangeChange(int paramInt1, int paramInt2)
  {
    findMinMaxChildLayoutPositions(mMinMaxLayoutPositions);
    int[] arrayOfInt = mMinMaxLayoutPositions;
    return (arrayOfInt[0] != paramInt1) || (arrayOfInt[1] != paramInt2);
  }
  
  private void dispatchContentChangedIfNecessary()
  {
    int i = mEatenAccessibilityChangeFlags;
    mEatenAccessibilityChangeFlags = 0;
    if ((i != 0) && (isAccessibilityEnabled()))
    {
      AccessibilityEvent localAccessibilityEvent = AccessibilityEvent.obtain();
      localAccessibilityEvent.setEventType(2048);
      AccessibilityEventCompat.setContentChangeTypes(localAccessibilityEvent, i);
      sendAccessibilityEventUnchecked(localAccessibilityEvent);
    }
  }
  
  private void dispatchLayoutStep1()
  {
    Object localObject = mState;
    boolean bool = true;
    ((State)localObject).assertLayoutStep(1);
    fillRemainingScrollValues(mState);
    mState.mIsMeasuring = false;
    startInterceptRequestLayout();
    mViewInfoStore.clear();
    onEnterLayoutOrScroll();
    processAdapterUpdatesAndSetAnimationFlags();
    saveFocusInfo();
    localObject = mState;
    if ((!mRunSimpleAnimations) || (!mItemsChanged)) {
      bool = false;
    }
    mTrackOldChangeHolders = bool;
    mItemsChanged = false;
    mItemsAddedOrRemoved = false;
    localObject = mState;
    mInPreLayout = mRunPredictiveAnimations;
    mState.mItemCount = mAdapter.getItemCount();
    findMinMaxChildLayoutPositions(mMinMaxLayoutPositions);
    int j;
    int i;
    RecyclerView.ItemAnimator.ItemHolderInfo localItemHolderInfo;
    if (mState.mRunSimpleAnimations)
    {
      j = mChildHelper.getChildCount();
      i = 0;
      while (i < j)
      {
        localObject = getChildViewHolderInt(mChildHelper.getChildAt(i));
        if ((!((ViewHolder)localObject).shouldIgnore()) && ((!((ViewHolder)localObject).isInvalid()) || (mAdapter.hasStableIds())))
        {
          localItemHolderInfo = mItemAnimator.recordPreLayoutInformation(mState, (ViewHolder)localObject, ItemAnimator.buildAdapterChangeFlagsForAnimations((ViewHolder)localObject), ((ViewHolder)localObject).getUnmodifiedPayloads());
          mViewInfoStore.addToPreLayout((ViewHolder)localObject, localItemHolderInfo);
          if ((mState.mTrackOldChangeHolders) && (((ViewHolder)localObject).isUpdated()) && (!((ViewHolder)localObject).isRemoved()) && (!((ViewHolder)localObject).shouldIgnore()) && (!((ViewHolder)localObject).isInvalid()))
          {
            long l = getChangedHolderKey((ViewHolder)localObject);
            mViewInfoStore.addToOldChangeHolders(l, (ViewHolder)localObject);
          }
        }
        i += 1;
      }
    }
    if (mState.mRunPredictiveAnimations)
    {
      saveOldPositions();
      bool = mState.mStructureChanged;
      localObject = mState;
      mStructureChanged = false;
      mLayout.onLayoutChildren(mRecycler, (State)localObject);
      mState.mStructureChanged = bool;
      i = 0;
      while (i < mChildHelper.getChildCount())
      {
        localObject = getChildViewHolderInt(mChildHelper.getChildAt(i));
        if ((!((ViewHolder)localObject).shouldIgnore()) && (!mViewInfoStore.isInPreLayout((ViewHolder)localObject)))
        {
          int k = ItemAnimator.buildAdapterChangeFlagsForAnimations((ViewHolder)localObject);
          j = k;
          bool = ((ViewHolder)localObject).hasAnyOfTheFlags(8192);
          if (!bool) {
            j = k | 0x1000;
          }
          localItemHolderInfo = mItemAnimator.recordPreLayoutInformation(mState, (ViewHolder)localObject, j, ((ViewHolder)localObject).getUnmodifiedPayloads());
          if (bool) {
            recordAnimationInfoIfBouncedHiddenView((ViewHolder)localObject, localItemHolderInfo);
          } else {
            mViewInfoStore.addToAppearedInPreLayoutHolders((ViewHolder)localObject, localItemHolderInfo);
          }
        }
        i += 1;
      }
      clearOldPositions();
    }
    else
    {
      clearOldPositions();
    }
    onExitLayoutOrScroll();
    stopInterceptRequestLayout(false);
    mState.mLayoutStep = 2;
  }
  
  private void dispatchLayoutStep2()
  {
    startInterceptRequestLayout();
    onEnterLayoutOrScroll();
    mState.assertLayoutStep(6);
    mAdapterHelper.consumeUpdatesInOnePass();
    mState.mItemCount = mAdapter.getItemCount();
    State localState = mState;
    mDeletedInvisibleItemCountSincePreviousLayout = 0;
    mInPreLayout = false;
    mLayout.onLayoutChildren(mRecycler, localState);
    localState = mState;
    mStructureChanged = false;
    mPendingSavedState = null;
    boolean bool;
    if ((mRunSimpleAnimations) && (mItemAnimator != null)) {
      bool = true;
    } else {
      bool = false;
    }
    mRunSimpleAnimations = bool;
    mState.mLayoutStep = 4;
    onExitLayoutOrScroll();
    stopInterceptRequestLayout(false);
  }
  
  private void dispatchLayoutStep3()
  {
    mState.assertLayoutStep(4);
    startInterceptRequestLayout();
    onEnterLayoutOrScroll();
    Object localObject = mState;
    mLayoutStep = 1;
    if (mRunSimpleAnimations)
    {
      int i = mChildHelper.getChildCount() - 1;
      while (i >= 0)
      {
        localObject = getChildViewHolderInt(mChildHelper.getChildAt(i));
        if (!((ViewHolder)localObject).shouldIgnore())
        {
          long l = getChangedHolderKey((ViewHolder)localObject);
          RecyclerView.ItemAnimator.ItemHolderInfo localItemHolderInfo2 = mItemAnimator.recordPostLayoutInformation(mState, (ViewHolder)localObject);
          ViewHolder localViewHolder = mViewInfoStore.getFromOldChangeHolders(l);
          if ((localViewHolder != null) && (!localViewHolder.shouldIgnore()))
          {
            boolean bool1 = mViewInfoStore.isDisappearing(localViewHolder);
            boolean bool2 = mViewInfoStore.isDisappearing((ViewHolder)localObject);
            if ((bool1) && (localViewHolder == localObject))
            {
              mViewInfoStore.addToPostLayout((ViewHolder)localObject, localItemHolderInfo2);
            }
            else
            {
              RecyclerView.ItemAnimator.ItemHolderInfo localItemHolderInfo1 = mViewInfoStore.popFromPreLayout(localViewHolder);
              mViewInfoStore.addToPostLayout((ViewHolder)localObject, localItemHolderInfo2);
              localItemHolderInfo2 = mViewInfoStore.popFromPostLayout((ViewHolder)localObject);
              if (localItemHolderInfo1 == null) {
                handleMissingPreInfoForChangeError(l, (ViewHolder)localObject, localViewHolder);
              } else {
                animateChange(localViewHolder, (ViewHolder)localObject, localItemHolderInfo1, localItemHolderInfo2, bool1, bool2);
              }
            }
          }
          else
          {
            mViewInfoStore.addToPostLayout((ViewHolder)localObject, localItemHolderInfo2);
          }
        }
        i -= 1;
      }
      mViewInfoStore.process(mViewInfoProcessCallback);
    }
    mLayout.removeAndRecycleScrapInt(mRecycler);
    localObject = mState;
    mPreviousLayoutItemCount = mItemCount;
    mDataSetHasChangedAfterLayout = false;
    mDispatchItemsChangedEvent = false;
    localObject = mState;
    mRunSimpleAnimations = false;
    mRunPredictiveAnimations = false;
    mLayout.mRequestedSimpleAnimations = false;
    if (mRecycler.mChangedScrap != null) {
      mRecycler.mChangedScrap.clear();
    }
    if (mLayout.mPrefetchMaxObservedInInitialPrefetch)
    {
      localObject = mLayout;
      mPrefetchMaxCountObserved = 0;
      mPrefetchMaxObservedInInitialPrefetch = false;
      mRecycler.updateViewCacheSize();
    }
    mLayout.onLayoutCompleted(mState);
    onExitLayoutOrScroll();
    stopInterceptRequestLayout(false);
    mViewInfoStore.clear();
    localObject = mMinMaxLayoutPositions;
    if (didChildRangeChange(localObject[0], localObject[1])) {
      dispatchOnScrolled(0, 0);
    }
    recoverFocusFromState();
    resetFocusInfo();
  }
  
  private boolean dispatchOnItemTouch(MotionEvent paramMotionEvent)
  {
    int i = paramMotionEvent.getAction();
    OnItemTouchListener localOnItemTouchListener = mActiveOnItemTouchListener;
    if (localOnItemTouchListener != null) {
      if (i == 0)
      {
        mActiveOnItemTouchListener = null;
      }
      else
      {
        localOnItemTouchListener.onTouchEvent(this, paramMotionEvent);
        if ((i != 3) && (i != 1)) {
          break label115;
        }
        mActiveOnItemTouchListener = null;
        return true;
      }
    }
    if (i != 0)
    {
      int j = mOnItemTouchListeners.size();
      i = 0;
      while (i < j)
      {
        localOnItemTouchListener = (OnItemTouchListener)mOnItemTouchListeners.get(i);
        if (localOnItemTouchListener.onInterceptTouchEvent(this, paramMotionEvent))
        {
          mActiveOnItemTouchListener = localOnItemTouchListener;
          return true;
        }
        i += 1;
      }
    }
    return false;
    label115:
    return true;
    return false;
  }
  
  private boolean dispatchOnItemTouchIntercept(MotionEvent paramMotionEvent)
  {
    int j = paramMotionEvent.getAction();
    if ((j == 3) || (j == 0)) {
      mActiveOnItemTouchListener = null;
    }
    int k = mOnItemTouchListeners.size();
    int i = 0;
    while (i < k)
    {
      OnItemTouchListener localOnItemTouchListener = (OnItemTouchListener)mOnItemTouchListeners.get(i);
      if ((localOnItemTouchListener.onInterceptTouchEvent(this, paramMotionEvent)) && (j != 3))
      {
        mActiveOnItemTouchListener = localOnItemTouchListener;
        return true;
      }
      i += 1;
    }
    return false;
  }
  
  private void findMinMaxChildLayoutPositions(int[] paramArrayOfInt)
  {
    int i2 = mChildHelper.getChildCount();
    if (i2 == 0)
    {
      paramArrayOfInt[0] = -1;
      paramArrayOfInt[1] = -1;
      return;
    }
    int m = 0;
    int j = Integer.MAX_VALUE;
    int i1;
    for (int k = Integer.MIN_VALUE; m < i2; k = i1)
    {
      ViewHolder localViewHolder = getChildViewHolderInt(mChildHelper.getChildAt(m));
      if (localViewHolder.shouldIgnore())
      {
        i1 = k;
      }
      else
      {
        int n = localViewHolder.getLayoutPosition();
        int i = j;
        if (n < j) {
          i = n;
        }
        j = i;
        i1 = k;
        if (n > k)
        {
          i1 = n;
          j = i;
        }
      }
      m += 1;
    }
    paramArrayOfInt[0] = j;
    paramArrayOfInt[1] = k;
  }
  
  static RecyclerView findNestedRecyclerView(View paramView)
  {
    if (!(paramView instanceof ViewGroup)) {
      return null;
    }
    if ((paramView instanceof RecyclerView)) {
      return (RecyclerView)paramView;
    }
    paramView = (ViewGroup)paramView;
    int j = paramView.getChildCount();
    int i = 0;
    while (i < j)
    {
      RecyclerView localRecyclerView = findNestedRecyclerView(paramView.getChildAt(i));
      if (localRecyclerView != null) {
        return localRecyclerView;
      }
      i += 1;
    }
    return null;
  }
  
  private View findNextViewToFocus()
  {
    if (mState.mFocusedItemPosition != -1) {
      i = mState.mFocusedItemPosition;
    } else {
      i = 0;
    }
    int k = mState.getItemCount();
    int j = i;
    ViewHolder localViewHolder;
    while (j < k)
    {
      localViewHolder = findViewHolderForAdapterPosition(j);
      if (localViewHolder == null) {
        break;
      }
      if (itemView.hasFocusable()) {
        return itemView;
      }
      j += 1;
    }
    int i = Math.min(k, i) - 1;
    while (i >= 0)
    {
      localViewHolder = findViewHolderForAdapterPosition(i);
      if (localViewHolder == null) {
        return null;
      }
      if (itemView.hasFocusable()) {
        return itemView;
      }
      i -= 1;
    }
    return null;
  }
  
  static ViewHolder getChildViewHolderInt(View paramView)
  {
    if (paramView == null) {
      return null;
    }
    return getLayoutParamsmViewHolder;
  }
  
  static void getDecoratedBoundsWithMarginsInt(View paramView, Rect paramRect)
  {
    LayoutParams localLayoutParams = (LayoutParams)paramView.getLayoutParams();
    Rect localRect = mDecorInsets;
    paramRect.set(paramView.getLeft() - left - leftMargin, paramView.getTop() - top - topMargin, paramView.getRight() + right + rightMargin, paramView.getBottom() + bottom + bottomMargin);
  }
  
  private int getDeepestFocusedViewWithId(View paramView)
  {
    int i = paramView.getId();
    while ((!paramView.isFocused()) && ((paramView instanceof ViewGroup)) && (paramView.hasFocus()))
    {
      View localView2 = ((ViewGroup)paramView).getFocusedChild();
      View localView1 = localView2;
      paramView = localView1;
      if (localView2.getId() != -1)
      {
        i = localView2.getId();
        paramView = localView1;
      }
    }
    return i;
  }
  
  private String getFullClassName(Context paramContext, String paramString)
  {
    if (paramString.charAt(0) == '.')
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(paramContext.getPackageName());
      localStringBuilder.append(paramString);
      return localStringBuilder.toString();
    }
    if (paramString.contains(".")) {
      return paramString;
    }
    paramContext = new StringBuilder();
    paramContext.append(RecyclerView.class.getPackage().getName());
    paramContext.append('.');
    paramContext.append(paramString);
    return paramContext.toString();
  }
  
  private NestedScrollingChildHelper getScrollingChildHelper()
  {
    if (mScrollingChildHelper == null) {
      mScrollingChildHelper = new NestedScrollingChildHelper(this);
    }
    return mScrollingChildHelper;
  }
  
  private void handleMissingPreInfoForChangeError(long paramLong, ViewHolder paramViewHolder1, ViewHolder paramViewHolder2)
  {
    int j = mChildHelper.getChildCount();
    int i = 0;
    while (i < j)
    {
      localObject = getChildViewHolderInt(mChildHelper.getChildAt(i));
      if ((localObject != paramViewHolder1) && (getChangedHolderKey((ViewHolder)localObject) == paramLong))
      {
        paramViewHolder2 = mAdapter;
        if ((paramViewHolder2 != null) && (paramViewHolder2.hasStableIds()))
        {
          paramViewHolder2 = new StringBuilder();
          paramViewHolder2.append("Two different ViewHolders have the same stable ID. Stable IDs in your adapter MUST BE unique and SHOULD NOT change.\n ViewHolder 1:");
          paramViewHolder2.append(localObject);
          paramViewHolder2.append(" \n View Holder 2:");
          paramViewHolder2.append(paramViewHolder1);
          paramViewHolder2.append(exceptionLabel());
          throw new IllegalStateException(paramViewHolder2.toString());
        }
        paramViewHolder2 = new StringBuilder();
        paramViewHolder2.append("Two different ViewHolders have the same change ID. This might happen due to inconsistent Adapter update events or if the LayoutManager lays out the same View multiple times.\n ViewHolder 1:");
        paramViewHolder2.append(localObject);
        paramViewHolder2.append(" \n View Holder 2:");
        paramViewHolder2.append(paramViewHolder1);
        paramViewHolder2.append(exceptionLabel());
        throw new IllegalStateException(paramViewHolder2.toString());
      }
      i += 1;
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Problem while matching changed view holders with the newones. The pre-layout information for the change holder ");
    ((StringBuilder)localObject).append(paramViewHolder2);
    ((StringBuilder)localObject).append(" cannot be found but it is necessary for ");
    ((StringBuilder)localObject).append(paramViewHolder1);
    ((StringBuilder)localObject).append(exceptionLabel());
    Log.e("RecyclerView", ((StringBuilder)localObject).toString());
  }
  
  private boolean hasUpdatedView()
  {
    int j = mChildHelper.getChildCount();
    int i = 0;
    while (i < j)
    {
      ViewHolder localViewHolder = getChildViewHolderInt(mChildHelper.getChildAt(i));
      if ((localViewHolder != null) && (!localViewHolder.shouldIgnore()) && (localViewHolder.isUpdated())) {
        return true;
      }
      i += 1;
    }
    return false;
  }
  
  private void initAutofill()
  {
    if (ViewCompat.getImportantForAutofill(this) == 0) {
      ViewCompat.setImportantForAutofill(this, 8);
    }
  }
  
  private void initChildrenHelper()
  {
    mChildHelper = new ChildHelper(new ChildHelper.Callback()
    {
      public void addView(View paramAnonymousView, int paramAnonymousInt)
      {
        RecyclerView.this.addView(paramAnonymousView, paramAnonymousInt);
        dispatchChildAttached(paramAnonymousView);
      }
      
      public void attachViewToParent(View paramAnonymousView, int paramAnonymousInt, ViewGroup.LayoutParams paramAnonymousLayoutParams)
      {
        RecyclerView.ViewHolder localViewHolder = RecyclerView.getChildViewHolderInt(paramAnonymousView);
        if (localViewHolder != null)
        {
          if ((!localViewHolder.isTmpDetached()) && (!localViewHolder.shouldIgnore()))
          {
            paramAnonymousView = new StringBuilder();
            paramAnonymousView.append("Called attach on a child which is not detached: ");
            paramAnonymousView.append(localViewHolder);
            paramAnonymousView.append(exceptionLabel());
            throw new IllegalArgumentException(paramAnonymousView.toString());
          }
          localViewHolder.clearTmpDetachFlag();
        }
        RecyclerView.this.attachViewToParent(paramAnonymousView, paramAnonymousInt, paramAnonymousLayoutParams);
      }
      
      public void detachViewFromParent(int paramAnonymousInt)
      {
        Object localObject = getChildAt(paramAnonymousInt);
        if (localObject != null)
        {
          localObject = RecyclerView.getChildViewHolderInt((View)localObject);
          if (localObject != null)
          {
            if ((((RecyclerView.ViewHolder)localObject).isTmpDetached()) && (!((RecyclerView.ViewHolder)localObject).shouldIgnore()))
            {
              StringBuilder localStringBuilder = new StringBuilder();
              localStringBuilder.append("called detach on an already detached child ");
              localStringBuilder.append(localObject);
              localStringBuilder.append(exceptionLabel());
              throw new IllegalArgumentException(localStringBuilder.toString());
            }
            ((RecyclerView.ViewHolder)localObject).addFlags(256);
          }
        }
        RecyclerView.this.detachViewFromParent(paramAnonymousInt);
      }
      
      public View getChildAt(int paramAnonymousInt)
      {
        return RecyclerView.this.getChildAt(paramAnonymousInt);
      }
      
      public int getChildCount()
      {
        return RecyclerView.this.getChildCount();
      }
      
      public RecyclerView.ViewHolder getChildViewHolder(View paramAnonymousView)
      {
        return RecyclerView.getChildViewHolderInt(paramAnonymousView);
      }
      
      public int indexOfChild(View paramAnonymousView)
      {
        return RecyclerView.this.indexOfChild(paramAnonymousView);
      }
      
      public void onEnteredHiddenState(View paramAnonymousView)
      {
        paramAnonymousView = RecyclerView.getChildViewHolderInt(paramAnonymousView);
        if (paramAnonymousView != null) {
          paramAnonymousView.onEnteredHiddenState(RecyclerView.this);
        }
      }
      
      public void onLeftHiddenState(View paramAnonymousView)
      {
        paramAnonymousView = RecyclerView.getChildViewHolderInt(paramAnonymousView);
        if (paramAnonymousView != null) {
          paramAnonymousView.onLeftHiddenState(RecyclerView.this);
        }
      }
      
      public void removeAllViews()
      {
        int j = getChildCount();
        int i = 0;
        while (i < j)
        {
          View localView = getChildAt(i);
          dispatchChildDetached(localView);
          localView.clearAnimation();
          i += 1;
        }
        RecyclerView.this.removeAllViews();
      }
      
      public void removeViewAt(int paramAnonymousInt)
      {
        View localView = RecyclerView.this.getChildAt(paramAnonymousInt);
        if (localView != null)
        {
          dispatchChildDetached(localView);
          localView.clearAnimation();
        }
        RecyclerView.this.removeViewAt(paramAnonymousInt);
      }
    });
  }
  
  private boolean isPreferredNextFocus(View paramView1, View paramView2, int paramInt)
  {
    if (paramView2 != null)
    {
      if (paramView2 == this) {
        return false;
      }
      if (findContainingItemView(paramView2) == null) {
        return false;
      }
      if (paramView1 == null) {
        return true;
      }
      if (findContainingItemView(paramView1) == null) {
        return true;
      }
      mTempRect.set(0, 0, paramView1.getWidth(), paramView1.getHeight());
      mTempRect2.set(0, 0, paramView2.getWidth(), paramView2.getHeight());
      offsetDescendantRectToMyCoords(paramView1, mTempRect);
      offsetDescendantRectToMyCoords(paramView2, mTempRect2);
      int i = mLayout.getLayoutDirection();
      int j = -1;
      int k;
      if (i == 1) {
        k = -1;
      } else {
        k = 1;
      }
      if (((mTempRect.left < mTempRect2.left) || (mTempRect.right <= mTempRect2.left)) && (mTempRect.right < mTempRect2.right)) {
        i = 1;
      } else if (((mTempRect.right > mTempRect2.right) || (mTempRect.left >= mTempRect2.right)) && (mTempRect.left > mTempRect2.left)) {
        i = -1;
      } else {
        i = 0;
      }
      if (((mTempRect.top < mTempRect2.top) || (mTempRect.bottom <= mTempRect2.top)) && (mTempRect.bottom < mTempRect2.bottom)) {
        j = 1;
      } else if (((mTempRect.bottom <= mTempRect2.bottom) && (mTempRect.top < mTempRect2.bottom)) || (mTempRect.top <= mTempRect2.top)) {
        j = 0;
      }
      if (paramInt != 1)
      {
        if (paramInt != 2)
        {
          if (paramInt != 17)
          {
            if (paramInt != 33)
            {
              if (paramInt != 66)
              {
                if (paramInt == 130)
                {
                  if (j > 0) {
                    return true;
                  }
                }
                else
                {
                  paramView1 = new StringBuilder();
                  paramView1.append("Invalid direction: ");
                  paramView1.append(paramInt);
                  paramView1.append(exceptionLabel());
                  throw new IllegalArgumentException(paramView1.toString());
                }
              }
              else if (i > 0) {
                return true;
              }
            }
            else if (j < 0) {
              return true;
            }
          }
          else if (i < 0) {
            return true;
          }
        }
        else if ((j > 0) || ((j == 0) && (i * k >= 0))) {
          return true;
        }
      }
      else if ((j < 0) || ((j == 0) && (i * k <= 0))) {
        return true;
      }
    }
    return false;
  }
  
  private void onPointerUp(MotionEvent paramMotionEvent)
  {
    int i = paramMotionEvent.getActionIndex();
    if (paramMotionEvent.getPointerId(i) == mScrollPointerId)
    {
      if (i == 0) {
        i = 1;
      } else {
        i = 0;
      }
      mScrollPointerId = paramMotionEvent.getPointerId(i);
      int j = (int)(paramMotionEvent.getX(i) + 0.5F);
      mLastTouchX = j;
      mInitialTouchX = j;
      i = (int)(paramMotionEvent.getY(i) + 0.5F);
      mLastTouchY = i;
      mInitialTouchY = i;
    }
  }
  
  private boolean predictiveItemAnimationsEnabled()
  {
    return (mItemAnimator != null) && (mLayout.supportsPredictiveItemAnimations());
  }
  
  private void processAdapterUpdatesAndSetAnimationFlags()
  {
    if (mDataSetHasChangedAfterLayout)
    {
      mAdapterHelper.reset();
      if (mDispatchItemsChangedEvent) {
        mLayout.onItemsChanged(this);
      }
    }
    if (predictiveItemAnimationsEnabled()) {
      mAdapterHelper.preProcess();
    } else {
      mAdapterHelper.consumeUpdatesInOnePass();
    }
    boolean bool1 = mItemsAddedOrRemoved;
    boolean bool2 = false;
    int i;
    if ((!bool1) && (!mItemsChanged)) {
      i = 0;
    } else {
      i = 1;
    }
    State localState = mState;
    if ((mFirstLayoutComplete) && (mItemAnimator != null) && ((mDataSetHasChangedAfterLayout) || (i != 0) || (mLayout.mRequestedSimpleAnimations)) && ((!mDataSetHasChangedAfterLayout) || (mAdapter.hasStableIds()))) {
      bool1 = true;
    } else {
      bool1 = false;
    }
    mRunSimpleAnimations = bool1;
    localState = mState;
    bool1 = bool2;
    if (mRunSimpleAnimations)
    {
      bool1 = bool2;
      if (i != 0)
      {
        bool1 = bool2;
        if (!mDataSetHasChangedAfterLayout)
        {
          bool1 = bool2;
          if (predictiveItemAnimationsEnabled()) {
            bool1 = true;
          }
        }
      }
    }
    mRunPredictiveAnimations = bool1;
  }
  
  private void pullGlows(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    int j = 1;
    if (paramFloat2 < 0.0F)
    {
      ensureLeftGlow();
      EdgeEffectCompat.onPull(mLeftGlow, -paramFloat2 / getWidth(), 1.0F - paramFloat3 / getHeight());
    }
    for (;;)
    {
      i = 1;
      break label80;
      if (paramFloat2 <= 0.0F) {
        break;
      }
      ensureRightGlow();
      EdgeEffectCompat.onPull(mRightGlow, paramFloat2 / getWidth(), paramFloat3 / getHeight());
    }
    int i = 0;
    label80:
    if (paramFloat4 < 0.0F)
    {
      ensureTopGlow();
      EdgeEffectCompat.onPull(mTopGlow, -paramFloat4 / getHeight(), paramFloat1 / getWidth());
      i = j;
    }
    else if (paramFloat4 > 0.0F)
    {
      ensureBottomGlow();
      EdgeEffectCompat.onPull(mBottomGlow, paramFloat4 / getHeight(), 1.0F - paramFloat1 / getWidth());
      i = j;
    }
    if ((i != 0) || (paramFloat2 != 0.0F) || (paramFloat4 != 0.0F)) {
      ViewCompat.postInvalidateOnAnimation(this);
    }
  }
  
  private void recoverFocusFromState()
  {
    if ((mPreserveFocusAfterLayout) && (mAdapter != null) && (hasFocus()) && (getDescendantFocusability() != 393216))
    {
      if ((getDescendantFocusability() == 131072) && (isFocused())) {
        return;
      }
      Object localObject1;
      if (!isFocused())
      {
        localObject1 = getFocusedChild();
        if ((IGNORE_DETACHED_FOCUSED_CHILD) && ((((View)localObject1).getParent() == null) || (!((View)localObject1).hasFocus())))
        {
          if (mChildHelper.getChildCount() == 0) {
            requestFocus();
          }
        }
        else if (!mChildHelper.isHidden((View)localObject1)) {
          return;
        }
      }
      long l = mState.mFocusedItemId;
      Object localObject2 = null;
      if ((l != -1L) && (mAdapter.hasStableIds())) {
        localObject1 = findViewHolderForItemId(mState.mFocusedItemId);
      } else {
        localObject1 = null;
      }
      if ((localObject1 != null) && (!mChildHelper.isHidden(itemView)) && (itemView.hasFocusable()))
      {
        localObject1 = itemView;
      }
      else
      {
        localObject1 = localObject2;
        if (mChildHelper.getChildCount() > 0) {
          localObject1 = findNextViewToFocus();
        }
      }
      if (localObject1 != null)
      {
        if (mState.mFocusedSubChildId != -1L)
        {
          View localView = ((View)localObject1).findViewById(mState.mFocusedSubChildId);
          localObject2 = localView;
          if ((localView != null) && (localView.isFocusable())) {
            localObject1 = localObject2;
          }
        }
        ((View)localObject1).requestFocus();
      }
    }
  }
  
  private void releaseGlows()
  {
    EdgeEffect localEdgeEffect = mLeftGlow;
    if (localEdgeEffect != null)
    {
      localEdgeEffect.onRelease();
      bool2 = mLeftGlow.isFinished();
    }
    else
    {
      bool2 = false;
    }
    localEdgeEffect = mTopGlow;
    boolean bool1 = bool2;
    if (localEdgeEffect != null)
    {
      localEdgeEffect.onRelease();
      bool1 = bool2 | mTopGlow.isFinished();
    }
    localEdgeEffect = mRightGlow;
    boolean bool2 = bool1;
    if (localEdgeEffect != null)
    {
      localEdgeEffect.onRelease();
      bool2 = bool1 | mRightGlow.isFinished();
    }
    localEdgeEffect = mBottomGlow;
    bool1 = bool2;
    if (localEdgeEffect != null)
    {
      localEdgeEffect.onRelease();
      bool1 = bool2 | mBottomGlow.isFinished();
    }
    if (bool1) {
      ViewCompat.postInvalidateOnAnimation(this);
    }
  }
  
  private void requestChildOnScreen(View paramView1, View paramView2)
  {
    if (paramView2 != null) {
      localObject = paramView2;
    } else {
      localObject = paramView1;
    }
    mTempRect.set(0, 0, ((View)localObject).getWidth(), ((View)localObject).getHeight());
    Object localObject = ((View)localObject).getLayoutParams();
    if ((localObject instanceof LayoutParams))
    {
      localObject = (LayoutParams)localObject;
      if (!mInsetsDirty)
      {
        localObject = mDecorInsets;
        localRect = mTempRect;
        left -= left;
        localRect = mTempRect;
        right += right;
        localRect = mTempRect;
        top -= top;
        localRect = mTempRect;
        bottom += bottom;
      }
    }
    if (paramView2 != null)
    {
      offsetDescendantRectToMyCoords(paramView2, mTempRect);
      offsetRectIntoDescendantCoords(paramView1, mTempRect);
    }
    localObject = mLayout;
    Rect localRect = mTempRect;
    boolean bool2 = mFirstLayoutComplete;
    boolean bool1;
    if (paramView2 == null) {
      bool1 = true;
    } else {
      bool1 = false;
    }
    ((LayoutManager)localObject).requestChildRectangleOnScreen(this, paramView1, localRect, bool2 ^ true, bool1);
  }
  
  private void resetFocusInfo()
  {
    State localState = mState;
    mFocusedItemId = -1L;
    mFocusedItemPosition = -1;
    mFocusedSubChildId = -1;
  }
  
  private void resetTouch()
  {
    VelocityTracker localVelocityTracker = mVelocityTracker;
    if (localVelocityTracker != null) {
      localVelocityTracker.clear();
    }
    stopNestedScroll(0);
    releaseGlows();
  }
  
  private void saveFocusInfo()
  {
    boolean bool = mPreserveFocusAfterLayout;
    State localState = null;
    Object localObject;
    if ((bool) && (hasFocus()) && (mAdapter != null)) {
      localObject = getFocusedChild();
    } else {
      localObject = null;
    }
    if (localObject == null) {
      localObject = localState;
    } else {
      localObject = findContainingViewHolder((View)localObject);
    }
    if (localObject == null)
    {
      resetFocusInfo();
      return;
    }
    localState = mState;
    long l;
    if (mAdapter.hasStableIds()) {
      l = ((ViewHolder)localObject).getItemId();
    } else {
      l = -1L;
    }
    mFocusedItemId = l;
    localState = mState;
    int i;
    if (mDataSetHasChangedAfterLayout) {
      i = -1;
    } else if (((ViewHolder)localObject).isRemoved()) {
      i = mOldPosition;
    } else {
      i = ((ViewHolder)localObject).getAdapterPosition();
    }
    mFocusedItemPosition = i;
    mState.mFocusedSubChildId = getDeepestFocusedViewWithId(itemView);
  }
  
  private void setAdapterInternal(Adapter paramAdapter, boolean paramBoolean1, boolean paramBoolean2)
  {
    Adapter localAdapter = mAdapter;
    if (localAdapter != null)
    {
      localAdapter.unregisterAdapterDataObserver(mObserver);
      mAdapter.onDetachedFromRecyclerView(this);
    }
    if ((!paramBoolean1) || (paramBoolean2)) {
      removeAndRecycleViews();
    }
    mAdapterHelper.reset();
    localAdapter = mAdapter;
    mAdapter = paramAdapter;
    if (paramAdapter != null)
    {
      paramAdapter.registerAdapterDataObserver(mObserver);
      paramAdapter.onAttachedToRecyclerView(this);
    }
    paramAdapter = mLayout;
    if (paramAdapter != null) {
      paramAdapter.onAdapterChanged(localAdapter, mAdapter);
    }
    mRecycler.onAdapterChanged(localAdapter, mAdapter, paramBoolean1);
    mState.mStructureChanged = true;
  }
  
  private void stopScrollersInternal()
  {
    mViewFlinger.stop();
    LayoutManager localLayoutManager = mLayout;
    if (localLayoutManager != null) {
      localLayoutManager.stopSmoothScroller();
    }
  }
  
  void absorbGlows(int paramInt1, int paramInt2)
  {
    if (paramInt1 < 0)
    {
      ensureLeftGlow();
      mLeftGlow.onAbsorb(-paramInt1);
    }
    else if (paramInt1 > 0)
    {
      ensureRightGlow();
      mRightGlow.onAbsorb(paramInt1);
    }
    if (paramInt2 < 0)
    {
      ensureTopGlow();
      mTopGlow.onAbsorb(-paramInt2);
    }
    else if (paramInt2 > 0)
    {
      ensureBottomGlow();
      mBottomGlow.onAbsorb(paramInt2);
    }
    if ((paramInt1 != 0) || (paramInt2 != 0)) {
      ViewCompat.postInvalidateOnAnimation(this);
    }
  }
  
  public void addFocusables(ArrayList paramArrayList, int paramInt1, int paramInt2)
  {
    LayoutManager localLayoutManager = mLayout;
    if ((localLayoutManager == null) || (!localLayoutManager.onAddFocusables(this, paramArrayList, paramInt1, paramInt2))) {
      super.addFocusables(paramArrayList, paramInt1, paramInt2);
    }
  }
  
  public void addItemDecoration(ItemDecoration paramItemDecoration)
  {
    addItemDecoration(paramItemDecoration, -1);
  }
  
  public void addItemDecoration(ItemDecoration paramItemDecoration, int paramInt)
  {
    LayoutManager localLayoutManager = mLayout;
    if (localLayoutManager != null) {
      localLayoutManager.assertNotInLayoutOrScroll("Cannot add item decoration during a scroll  or layout");
    }
    if (mItemDecorations.isEmpty()) {
      setWillNotDraw(false);
    }
    if (paramInt < 0) {
      mItemDecorations.add(paramItemDecoration);
    } else {
      mItemDecorations.add(paramInt, paramItemDecoration);
    }
    markItemDecorInsetsDirty();
    requestLayout();
  }
  
  public void addOnChildAttachStateChangeListener(OnChildAttachStateChangeListener paramOnChildAttachStateChangeListener)
  {
    if (mOnChildAttachStateListeners == null) {
      mOnChildAttachStateListeners = new ArrayList();
    }
    mOnChildAttachStateListeners.add(paramOnChildAttachStateChangeListener);
  }
  
  public void addOnItemTouchListener(OnItemTouchListener paramOnItemTouchListener)
  {
    mOnItemTouchListeners.add(paramOnItemTouchListener);
  }
  
  public void addOnScrollListener(OnScrollListener paramOnScrollListener)
  {
    if (mScrollListeners == null) {
      mScrollListeners = new ArrayList();
    }
    mScrollListeners.add(paramOnScrollListener);
  }
  
  void animateAppearance(ViewHolder paramViewHolder, RecyclerView.ItemAnimator.ItemHolderInfo paramItemHolderInfo1, RecyclerView.ItemAnimator.ItemHolderInfo paramItemHolderInfo2)
  {
    paramViewHolder.setIsRecyclable(false);
    if (mItemAnimator.animateAppearance(paramViewHolder, paramItemHolderInfo1, paramItemHolderInfo2)) {
      postAnimationRunner();
    }
  }
  
  void animateDisappearance(ViewHolder paramViewHolder, RecyclerView.ItemAnimator.ItemHolderInfo paramItemHolderInfo1, RecyclerView.ItemAnimator.ItemHolderInfo paramItemHolderInfo2)
  {
    addAnimatingView(paramViewHolder);
    paramViewHolder.setIsRecyclable(false);
    if (mItemAnimator.animateDisappearance(paramViewHolder, paramItemHolderInfo1, paramItemHolderInfo2)) {
      postAnimationRunner();
    }
  }
  
  void assertInLayoutOrScroll(String paramString)
  {
    if (!isComputingLayout())
    {
      if (paramString == null)
      {
        paramString = new StringBuilder();
        paramString.append("Cannot call this method unless RecyclerView is computing a layout or scrolling");
        paramString.append(exceptionLabel());
        throw new IllegalStateException(paramString.toString());
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(paramString);
      localStringBuilder.append(exceptionLabel());
      throw new IllegalStateException(localStringBuilder.toString());
    }
  }
  
  void assertNotInLayoutOrScroll(String paramString)
  {
    if (isComputingLayout())
    {
      if (paramString == null)
      {
        paramString = new StringBuilder();
        paramString.append("Cannot call this method while RecyclerView is computing a layout or scrolling");
        paramString.append(exceptionLabel());
        throw new IllegalStateException(paramString.toString());
      }
      throw new IllegalStateException(paramString);
    }
    if (mDispatchScrollCounter > 0)
    {
      paramString = new StringBuilder();
      paramString.append("");
      paramString.append(exceptionLabel());
      Log.w("RecyclerView", "Cannot call this method in a scroll callback. Scroll callbacks mightbe run during a measure & layout pass where you cannot change theRecyclerView data. Any method call that might change the structureof the RecyclerView or the adapter contents should be postponed tothe next frame.", new IllegalStateException(paramString.toString()));
    }
  }
  
  boolean canReuseUpdatedViewHolder(ViewHolder paramViewHolder)
  {
    ItemAnimator localItemAnimator = mItemAnimator;
    return (localItemAnimator == null) || (localItemAnimator.canReuseUpdatedViewHolder(paramViewHolder, paramViewHolder.getUnmodifiedPayloads()));
  }
  
  protected boolean checkLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    return ((paramLayoutParams instanceof LayoutParams)) && (mLayout.checkLayoutParams((LayoutParams)paramLayoutParams));
  }
  
  void clearOldPositions()
  {
    int j = mChildHelper.getUnfilteredChildCount();
    int i = 0;
    while (i < j)
    {
      ViewHolder localViewHolder = getChildViewHolderInt(mChildHelper.getUnfilteredChildAt(i));
      if (!localViewHolder.shouldIgnore()) {
        localViewHolder.clearOldPosition();
      }
      i += 1;
    }
    mRecycler.clearOldPositions();
  }
  
  public void clearOnChildAttachStateChangeListeners()
  {
    List localList = mOnChildAttachStateListeners;
    if (localList != null) {
      localList.clear();
    }
  }
  
  public void clearOnScrollListeners()
  {
    List localList = mScrollListeners;
    if (localList != null) {
      localList.clear();
    }
  }
  
  public int computeHorizontalScrollExtent()
  {
    LayoutManager localLayoutManager = mLayout;
    if (localLayoutManager == null) {
      return 0;
    }
    if (localLayoutManager.canScrollHorizontally()) {
      return mLayout.computeHorizontalScrollExtent(mState);
    }
    return 0;
  }
  
  public int computeHorizontalScrollOffset()
  {
    LayoutManager localLayoutManager = mLayout;
    if (localLayoutManager == null) {
      return 0;
    }
    if (localLayoutManager.canScrollHorizontally()) {
      return mLayout.computeHorizontalScrollOffset(mState);
    }
    return 0;
  }
  
  public int computeHorizontalScrollRange()
  {
    LayoutManager localLayoutManager = mLayout;
    if (localLayoutManager == null) {
      return 0;
    }
    if (localLayoutManager.canScrollHorizontally()) {
      return mLayout.computeHorizontalScrollRange(mState);
    }
    return 0;
  }
  
  public int computeVerticalScrollExtent()
  {
    LayoutManager localLayoutManager = mLayout;
    if (localLayoutManager == null) {
      return 0;
    }
    if (localLayoutManager.canScrollVertically()) {
      return mLayout.computeVerticalScrollExtent(mState);
    }
    return 0;
  }
  
  public int computeVerticalScrollOffset()
  {
    LayoutManager localLayoutManager = mLayout;
    if (localLayoutManager == null) {
      return 0;
    }
    if (localLayoutManager.canScrollVertically()) {
      return mLayout.computeVerticalScrollOffset(mState);
    }
    return 0;
  }
  
  public int computeVerticalScrollRange()
  {
    LayoutManager localLayoutManager = mLayout;
    if (localLayoutManager == null) {
      return 0;
    }
    if (localLayoutManager.canScrollVertically()) {
      return mLayout.computeVerticalScrollRange(mState);
    }
    return 0;
  }
  
  void considerReleasingGlowsOnScroll(int paramInt1, int paramInt2)
  {
    EdgeEffect localEdgeEffect = mLeftGlow;
    if ((localEdgeEffect != null) && (!localEdgeEffect.isFinished()) && (paramInt1 > 0))
    {
      mLeftGlow.onRelease();
      bool2 = mLeftGlow.isFinished();
    }
    else
    {
      bool2 = false;
    }
    localEdgeEffect = mRightGlow;
    boolean bool1 = bool2;
    if (localEdgeEffect != null)
    {
      bool1 = bool2;
      if (!localEdgeEffect.isFinished())
      {
        bool1 = bool2;
        if (paramInt1 < 0)
        {
          mRightGlow.onRelease();
          bool1 = bool2 | mRightGlow.isFinished();
        }
      }
    }
    localEdgeEffect = mTopGlow;
    boolean bool2 = bool1;
    if (localEdgeEffect != null)
    {
      bool2 = bool1;
      if (!localEdgeEffect.isFinished())
      {
        bool2 = bool1;
        if (paramInt2 > 0)
        {
          mTopGlow.onRelease();
          bool2 = bool1 | mTopGlow.isFinished();
        }
      }
    }
    localEdgeEffect = mBottomGlow;
    bool1 = bool2;
    if (localEdgeEffect != null)
    {
      bool1 = bool2;
      if (!localEdgeEffect.isFinished())
      {
        bool1 = bool2;
        if (paramInt2 < 0)
        {
          mBottomGlow.onRelease();
          bool1 = bool2 | mBottomGlow.isFinished();
        }
      }
    }
    if (bool1) {
      ViewCompat.postInvalidateOnAnimation(this);
    }
  }
  
  void consumePendingUpdateOperations()
  {
    if ((mFirstLayoutComplete) && (!mDataSetHasChangedAfterLayout))
    {
      if (!mAdapterHelper.hasPendingUpdates()) {
        return;
      }
      if ((mAdapterHelper.hasAnyUpdateTypes(4)) && (!mAdapterHelper.hasAnyUpdateTypes(11)))
      {
        TraceCompat.beginSection("RV PartialInvalidate");
        startInterceptRequestLayout();
        onEnterLayoutOrScroll();
        mAdapterHelper.preProcess();
        if (!mLayoutWasDefered) {
          if (hasUpdatedView()) {
            dispatchLayout();
          } else {
            mAdapterHelper.consumePostponedUpdates();
          }
        }
        stopInterceptRequestLayout(true);
        onExitLayoutOrScroll();
        TraceCompat.endSection();
        return;
      }
      if (mAdapterHelper.hasPendingUpdates())
      {
        TraceCompat.beginSection("RV FullInvalidate");
        dispatchLayout();
        TraceCompat.endSection();
      }
    }
    else
    {
      TraceCompat.beginSection("RV FullInvalidate");
      dispatchLayout();
      TraceCompat.endSection();
    }
  }
  
  void defaultOnMeasure(int paramInt1, int paramInt2)
  {
    setMeasuredDimension(LayoutManager.chooseSize(paramInt1, getPaddingLeft() + getPaddingRight(), ViewCompat.getMinimumWidth(this)), LayoutManager.chooseSize(paramInt2, getPaddingTop() + getPaddingBottom(), ViewCompat.getMinimumHeight(this)));
  }
  
  void dispatchChildAttached(View paramView)
  {
    Object localObject = getChildViewHolderInt(paramView);
    onChildAttachedToWindow(paramView);
    Adapter localAdapter = mAdapter;
    if ((localAdapter != null) && (localObject != null)) {
      localAdapter.onViewAttachedToWindow((ViewHolder)localObject);
    }
    localObject = mOnChildAttachStateListeners;
    if (localObject != null)
    {
      int i = ((List)localObject).size() - 1;
      while (i >= 0)
      {
        ((OnChildAttachStateChangeListener)mOnChildAttachStateListeners.get(i)).onChildViewAttachedToWindow(paramView);
        i -= 1;
      }
    }
  }
  
  void dispatchChildDetached(View paramView)
  {
    Object localObject = getChildViewHolderInt(paramView);
    onChildDetachedFromWindow(paramView);
    Adapter localAdapter = mAdapter;
    if ((localAdapter != null) && (localObject != null)) {
      localAdapter.onViewDetachedFromWindow((ViewHolder)localObject);
    }
    localObject = mOnChildAttachStateListeners;
    if (localObject != null)
    {
      int i = ((List)localObject).size() - 1;
      while (i >= 0)
      {
        ((OnChildAttachStateChangeListener)mOnChildAttachStateListeners.get(i)).onChildViewDetachedFromWindow(paramView);
        i -= 1;
      }
    }
  }
  
  void dispatchLayout()
  {
    if (mAdapter == null)
    {
      Log.e("RecyclerView", "No adapter attached; skipping layout");
      return;
    }
    if (mLayout == null)
    {
      Log.e("RecyclerView", "No layout manager attached; skipping layout");
      return;
    }
    State localState = mState;
    mIsMeasuring = false;
    if (mLayoutStep == 1)
    {
      dispatchLayoutStep1();
      mLayout.setExactMeasureSpecsFrom(this);
      dispatchLayoutStep2();
    }
    else if ((!mAdapterHelper.hasUpdates()) && (mLayout.getWidth() == getWidth()) && (mLayout.getHeight() == getHeight()))
    {
      mLayout.setExactMeasureSpecsFrom(this);
    }
    else
    {
      mLayout.setExactMeasureSpecsFrom(this);
      dispatchLayoutStep2();
    }
    dispatchLayoutStep3();
  }
  
  public boolean dispatchNestedFling(float paramFloat1, float paramFloat2, boolean paramBoolean)
  {
    return getScrollingChildHelper().dispatchNestedFling(paramFloat1, paramFloat2, paramBoolean);
  }
  
  public boolean dispatchNestedPreFling(float paramFloat1, float paramFloat2)
  {
    return getScrollingChildHelper().dispatchNestedPreFling(paramFloat1, paramFloat2);
  }
  
  public boolean dispatchNestedPreScroll(int paramInt1, int paramInt2, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    return getScrollingChildHelper().dispatchNestedPreScroll(paramInt1, paramInt2, paramArrayOfInt1, paramArrayOfInt2);
  }
  
  public boolean dispatchNestedPreScroll(int paramInt1, int paramInt2, int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt3)
  {
    return getScrollingChildHelper().dispatchNestedPreScroll(paramInt1, paramInt2, paramArrayOfInt1, paramArrayOfInt2, paramInt3);
  }
  
  public boolean dispatchNestedScroll(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfInt)
  {
    return getScrollingChildHelper().dispatchNestedScroll(paramInt1, paramInt2, paramInt3, paramInt4, paramArrayOfInt);
  }
  
  public boolean dispatchNestedScroll(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfInt, int paramInt5)
  {
    return getScrollingChildHelper().dispatchNestedScroll(paramInt1, paramInt2, paramInt3, paramInt4, paramArrayOfInt, paramInt5);
  }
  
  void dispatchOnScrollStateChanged(int paramInt)
  {
    Object localObject = mLayout;
    if (localObject != null) {
      ((LayoutManager)localObject).onScrollStateChanged(paramInt);
    }
    onScrollStateChanged(paramInt);
    localObject = mScrollListener;
    if (localObject != null) {
      ((OnScrollListener)localObject).onScrollStateChanged(this, paramInt);
    }
    localObject = mScrollListeners;
    if (localObject != null)
    {
      int i = ((List)localObject).size() - 1;
      while (i >= 0)
      {
        ((OnScrollListener)mScrollListeners.get(i)).onScrollStateChanged(this, paramInt);
        i -= 1;
      }
    }
  }
  
  void dispatchOnScrolled(int paramInt1, int paramInt2)
  {
    mDispatchScrollCounter += 1;
    int i = getScrollX();
    int j = getScrollY();
    onScrollChanged(i, j, i, j);
    onScrolled(paramInt1, paramInt2);
    Object localObject = mScrollListener;
    if (localObject != null) {
      ((OnScrollListener)localObject).onScrolled(this, paramInt1, paramInt2);
    }
    localObject = mScrollListeners;
    if (localObject != null)
    {
      i = ((List)localObject).size() - 1;
      while (i >= 0)
      {
        ((OnScrollListener)mScrollListeners.get(i)).onScrolled(this, paramInt1, paramInt2);
        i -= 1;
      }
    }
    mDispatchScrollCounter -= 1;
  }
  
  void dispatchPendingImportantForAccessibilityChanges()
  {
    int i = mPendingAccessibilityImportanceChange.size() - 1;
    while (i >= 0)
    {
      ViewHolder localViewHolder = (ViewHolder)mPendingAccessibilityImportanceChange.get(i);
      if ((itemView.getParent() == this) && (!localViewHolder.shouldIgnore()))
      {
        int j = mPendingAccessibilityState;
        if (j != -1)
        {
          ViewCompat.setImportantForAccessibility(itemView, j);
          mPendingAccessibilityState = -1;
        }
      }
      i -= 1;
    }
    mPendingAccessibilityImportanceChange.clear();
  }
  
  protected void dispatchRestoreInstanceState(SparseArray paramSparseArray)
  {
    dispatchThawSelfOnly(paramSparseArray);
  }
  
  protected void dispatchSaveInstanceState(SparseArray paramSparseArray)
  {
    dispatchFreezeSelfOnly(paramSparseArray);
  }
  
  public void draw(Canvas paramCanvas)
  {
    super.draw(paramCanvas);
    int j = mItemDecorations.size();
    int k = 0;
    int i = 0;
    while (i < j)
    {
      ((ItemDecoration)mItemDecorations.get(i)).onDrawOver(paramCanvas, this, mState);
      i += 1;
    }
    EdgeEffect localEdgeEffect = mLeftGlow;
    int m;
    if ((localEdgeEffect != null) && (!localEdgeEffect.isFinished()))
    {
      m = paramCanvas.save();
      if (mClipToPadding) {
        i = getPaddingBottom();
      } else {
        i = 0;
      }
      paramCanvas.rotate(270.0F);
      paramCanvas.translate(-getHeight() + i, 0.0F);
      localEdgeEffect = mLeftGlow;
      if ((localEdgeEffect != null) && (localEdgeEffect.draw(paramCanvas))) {
        j = 1;
      } else {
        j = 0;
      }
      paramCanvas.restoreToCount(m);
    }
    else
    {
      j = 0;
    }
    localEdgeEffect = mTopGlow;
    i = j;
    if (localEdgeEffect != null)
    {
      i = j;
      if (!localEdgeEffect.isFinished())
      {
        m = paramCanvas.save();
        if (mClipToPadding) {
          paramCanvas.translate(getPaddingLeft(), getPaddingTop());
        }
        localEdgeEffect = mTopGlow;
        if ((localEdgeEffect != null) && (localEdgeEffect.draw(paramCanvas))) {
          i = 1;
        } else {
          i = 0;
        }
        i = j | i;
        paramCanvas.restoreToCount(m);
      }
    }
    localEdgeEffect = mRightGlow;
    j = i;
    if (localEdgeEffect != null)
    {
      j = i;
      if (!localEdgeEffect.isFinished())
      {
        m = paramCanvas.save();
        int n = getWidth();
        if (mClipToPadding) {
          j = getPaddingTop();
        } else {
          j = 0;
        }
        paramCanvas.rotate(90.0F);
        paramCanvas.translate(-j, -n);
        localEdgeEffect = mRightGlow;
        if ((localEdgeEffect != null) && (localEdgeEffect.draw(paramCanvas))) {
          j = 1;
        } else {
          j = 0;
        }
        j = i | j;
        paramCanvas.restoreToCount(m);
      }
    }
    localEdgeEffect = mBottomGlow;
    if ((localEdgeEffect != null) && (!localEdgeEffect.isFinished()))
    {
      m = paramCanvas.save();
      paramCanvas.rotate(180.0F);
      if (mClipToPadding) {
        paramCanvas.translate(-getWidth() + getPaddingRight(), -getHeight() + getPaddingBottom());
      } else {
        paramCanvas.translate(-getWidth(), -getHeight());
      }
      localEdgeEffect = mBottomGlow;
      i = k;
      if (localEdgeEffect != null)
      {
        i = k;
        if (localEdgeEffect.draw(paramCanvas)) {
          i = 1;
        }
      }
      i |= j;
      paramCanvas.restoreToCount(m);
    }
    else
    {
      i = j;
    }
    j = i;
    if (i == 0)
    {
      j = i;
      if (mItemAnimator != null)
      {
        j = i;
        if (mItemDecorations.size() > 0)
        {
          j = i;
          if (mItemAnimator.isRunning()) {
            j = 1;
          }
        }
      }
    }
    if (j != 0) {
      ViewCompat.postInvalidateOnAnimation(this);
    }
  }
  
  public boolean drawChild(Canvas paramCanvas, View paramView, long paramLong)
  {
    return super.drawChild(paramCanvas, paramView, paramLong);
  }
  
  void ensureBottomGlow()
  {
    if (mBottomGlow != null) {
      return;
    }
    mBottomGlow = mEdgeEffectFactory.createEdgeEffect(this, 3);
    if (mClipToPadding)
    {
      mBottomGlow.setSize(getMeasuredWidth() - getPaddingLeft() - getPaddingRight(), getMeasuredHeight() - getPaddingTop() - getPaddingBottom());
      return;
    }
    mBottomGlow.setSize(getMeasuredWidth(), getMeasuredHeight());
  }
  
  void ensureLeftGlow()
  {
    if (mLeftGlow != null) {
      return;
    }
    mLeftGlow = mEdgeEffectFactory.createEdgeEffect(this, 0);
    if (mClipToPadding)
    {
      mLeftGlow.setSize(getMeasuredHeight() - getPaddingTop() - getPaddingBottom(), getMeasuredWidth() - getPaddingLeft() - getPaddingRight());
      return;
    }
    mLeftGlow.setSize(getMeasuredHeight(), getMeasuredWidth());
  }
  
  void ensureRightGlow()
  {
    if (mRightGlow != null) {
      return;
    }
    mRightGlow = mEdgeEffectFactory.createEdgeEffect(this, 2);
    if (mClipToPadding)
    {
      mRightGlow.setSize(getMeasuredHeight() - getPaddingTop() - getPaddingBottom(), getMeasuredWidth() - getPaddingLeft() - getPaddingRight());
      return;
    }
    mRightGlow.setSize(getMeasuredHeight(), getMeasuredWidth());
  }
  
  void ensureTopGlow()
  {
    if (mTopGlow != null) {
      return;
    }
    mTopGlow = mEdgeEffectFactory.createEdgeEffect(this, 1);
    if (mClipToPadding)
    {
      mTopGlow.setSize(getMeasuredWidth() - getPaddingLeft() - getPaddingRight(), getMeasuredHeight() - getPaddingTop() - getPaddingBottom());
      return;
    }
    mTopGlow.setSize(getMeasuredWidth(), getMeasuredHeight());
  }
  
  String exceptionLabel()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(" ");
    localStringBuilder.append(super.toString());
    localStringBuilder.append(", adapter:");
    localStringBuilder.append(mAdapter);
    localStringBuilder.append(", layout:");
    localStringBuilder.append(mLayout);
    localStringBuilder.append(", context:");
    localStringBuilder.append(getContext());
    return localStringBuilder.toString();
  }
  
  final void fillRemainingScrollValues(State paramState)
  {
    if (getScrollState() == 2)
    {
      OverScroller localOverScroller = mViewFlinger.mScroller;
      mRemainingScrollHorizontal = (localOverScroller.getFinalX() - localOverScroller.getCurrX());
      mRemainingScrollVertical = (localOverScroller.getFinalY() - localOverScroller.getCurrY());
      return;
    }
    mRemainingScrollHorizontal = 0;
    mRemainingScrollVertical = 0;
  }
  
  public View findChildViewUnder(float paramFloat1, float paramFloat2)
  {
    int i = mChildHelper.getChildCount() - 1;
    while (i >= 0)
    {
      View localView = mChildHelper.getChildAt(i);
      float f1 = localView.getTranslationX();
      float f2 = localView.getTranslationY();
      if ((paramFloat1 >= localView.getLeft() + f1) && (paramFloat1 <= localView.getRight() + f1) && (paramFloat2 >= localView.getTop() + f2) && (paramFloat2 <= localView.getBottom() + f2)) {
        return localView;
      }
      i -= 1;
    }
    return null;
  }
  
  public View findContainingItemView(View paramView)
  {
    ViewParent localViewParent = paramView.getParent();
    View localView = paramView;
    for (paramView = localViewParent; (paramView != null) && (paramView != this) && ((paramView instanceof View)); paramView = localView.getParent()) {
      localView = (View)paramView;
    }
    if (paramView == this) {
      return localView;
    }
    return null;
  }
  
  public ViewHolder findContainingViewHolder(View paramView)
  {
    paramView = findContainingItemView(paramView);
    if (paramView == null) {
      return null;
    }
    return getChildViewHolder(paramView);
  }
  
  public ViewHolder findViewHolderForAdapterPosition(int paramInt)
  {
    boolean bool = mDataSetHasChangedAfterLayout;
    Object localObject1 = null;
    if (bool) {
      return null;
    }
    int j = mChildHelper.getUnfilteredChildCount();
    int i = 0;
    while (i < j)
    {
      ViewHolder localViewHolder = getChildViewHolderInt(mChildHelper.getUnfilteredChildAt(i));
      Object localObject2 = localObject1;
      if (localViewHolder != null)
      {
        localObject2 = localObject1;
        if (!localViewHolder.isRemoved())
        {
          localObject2 = localObject1;
          if (getAdapterPositionFor(localViewHolder) == paramInt) {
            if (mChildHelper.isHidden(itemView)) {
              localObject2 = localViewHolder;
            } else {
              return localViewHolder;
            }
          }
        }
      }
      i += 1;
      localObject1 = localObject2;
    }
    return localObject1;
  }
  
  public ViewHolder findViewHolderForItemId(long paramLong)
  {
    Object localObject2 = mAdapter;
    Object localObject1 = null;
    if (localObject2 != null)
    {
      if (!((Adapter)localObject2).hasStableIds()) {
        return null;
      }
      int j = mChildHelper.getUnfilteredChildCount();
      int i = 0;
      while (i < j)
      {
        ViewHolder localViewHolder = getChildViewHolderInt(mChildHelper.getUnfilteredChildAt(i));
        localObject2 = localObject1;
        if (localViewHolder != null)
        {
          localObject2 = localObject1;
          if (!localViewHolder.isRemoved())
          {
            localObject2 = localObject1;
            if (localViewHolder.getItemId() == paramLong) {
              if (mChildHelper.isHidden(itemView)) {
                localObject2 = localViewHolder;
              } else {
                return localViewHolder;
              }
            }
          }
        }
        i += 1;
        localObject1 = localObject2;
      }
    }
    return null;
    return localObject1;
  }
  
  public ViewHolder findViewHolderForLayoutPosition(int paramInt)
  {
    return findViewHolderForPosition(paramInt, false);
  }
  
  public ViewHolder findViewHolderForPosition(int paramInt)
  {
    return findViewHolderForPosition(paramInt, false);
  }
  
  ViewHolder findViewHolderForPosition(int paramInt, boolean paramBoolean)
  {
    int j = mChildHelper.getUnfilteredChildCount();
    Object localObject1 = null;
    int i = 0;
    while (i < j)
    {
      ViewHolder localViewHolder = getChildViewHolderInt(mChildHelper.getUnfilteredChildAt(i));
      Object localObject2 = localObject1;
      if (localViewHolder != null)
      {
        localObject2 = localObject1;
        if (!localViewHolder.isRemoved())
        {
          if (paramBoolean)
          {
            if (mPosition != paramInt)
            {
              localObject2 = localObject1;
              break label115;
            }
          }
          else if (localViewHolder.getLayoutPosition() != paramInt)
          {
            localObject2 = localObject1;
            break label115;
          }
          if (mChildHelper.isHidden(itemView)) {
            localObject2 = localViewHolder;
          } else {
            return localViewHolder;
          }
        }
      }
      label115:
      i += 1;
      localObject1 = localObject2;
    }
    return localObject1;
  }
  
  public boolean fling(int paramInt1, int paramInt2)
  {
    Object localObject = mLayout;
    int k = 0;
    if (localObject == null)
    {
      Log.e("RecyclerView", "Cannot fling without a LayoutManager set. Call setLayoutManager with a non-null argument.");
      return false;
    }
    if (mLayoutFrozen) {
      return false;
    }
    boolean bool2 = ((LayoutManager)localObject).canScrollHorizontally();
    boolean bool3 = mLayout.canScrollVertically();
    int i;
    if (bool2)
    {
      i = paramInt1;
      if (Math.abs(paramInt1) >= mMinFlingVelocity) {}
    }
    else
    {
      i = 0;
    }
    int j;
    if (bool3)
    {
      j = paramInt2;
      if (Math.abs(paramInt2) >= mMinFlingVelocity) {}
    }
    else
    {
      j = 0;
    }
    if ((i == 0) && (j == 0)) {
      return false;
    }
    float f1 = i;
    float f2 = j;
    if (!dispatchNestedPreFling(f1, f2))
    {
      boolean bool1;
      if ((!bool2) && (!bool3)) {
        bool1 = false;
      } else {
        bool1 = true;
      }
      dispatchNestedFling(f1, f2, bool1);
      localObject = mOnFlingListener;
      if ((localObject != null) && (((OnFlingListener)localObject).onFling(i, j))) {
        return true;
      }
      if (bool1)
      {
        paramInt1 = k;
        if (bool2) {
          paramInt1 = 1;
        }
        paramInt2 = paramInt1;
        if (bool3) {
          paramInt2 = paramInt1 | 0x2;
        }
        startNestedScroll(paramInt2, 1);
        paramInt1 = mMaxFlingVelocity;
        paramInt1 = Math.max(-paramInt1, Math.min(i, paramInt1));
        paramInt2 = mMaxFlingVelocity;
        paramInt2 = Math.max(-paramInt2, Math.min(j, paramInt2));
        mViewFlinger.fling(paramInt1, paramInt2);
        return true;
      }
    }
    return false;
  }
  
  public View focusSearch(View paramView, int paramInt)
  {
    Object localObject = mLayout.onInterceptFocusSearch(paramView, paramInt);
    if (localObject != null) {
      return localObject;
    }
    int i;
    if ((mAdapter != null) && (mLayout != null) && (!isComputingLayout()) && (!mLayoutFrozen)) {
      i = 1;
    } else {
      i = 0;
    }
    localObject = FocusFinder.getInstance();
    int k;
    if ((i != 0) && ((paramInt == 2) || (paramInt == 1)))
    {
      int j;
      if (mLayout.canScrollVertically())
      {
        if (paramInt == 2) {
          j = 130;
        } else {
          j = 33;
        }
        if (((FocusFinder)localObject).findNextFocus(this, paramView, j) == null) {
          k = 1;
        } else {
          k = 0;
        }
        i = k;
        if (FORCE_ABS_FOCUS_SEARCH_DIRECTION)
        {
          paramInt = j;
          i = k;
        }
      }
      else
      {
        i = 0;
      }
      int m = i;
      k = paramInt;
      if (i == 0)
      {
        m = i;
        k = paramInt;
        if (mLayout.canScrollHorizontally())
        {
          if (mLayout.getLayoutDirection() == 1) {
            i = 1;
          } else {
            i = 0;
          }
          if (paramInt == 2) {
            j = 1;
          } else {
            j = 0;
          }
          if ((i ^ j) != 0) {
            i = 66;
          } else {
            i = 17;
          }
          if (((FocusFinder)localObject).findNextFocus(this, paramView, i) == null) {
            j = 1;
          } else {
            j = 0;
          }
          m = j;
          k = paramInt;
          if (FORCE_ABS_FOCUS_SEARCH_DIRECTION)
          {
            k = i;
            m = j;
          }
        }
      }
      if (m != 0)
      {
        consumePendingUpdateOperations();
        if (findContainingItemView(paramView) == null) {
          return null;
        }
        startInterceptRequestLayout();
        mLayout.onFocusSearchFailed(paramView, k, mRecycler, mState);
        stopInterceptRequestLayout(false);
      }
      localObject = ((FocusFinder)localObject).findNextFocus(this, paramView, k);
    }
    else
    {
      View localView = ((FocusFinder)localObject).findNextFocus(this, paramView, paramInt);
      localObject = localView;
      k = paramInt;
      if (localView == null)
      {
        localObject = localView;
        k = paramInt;
        if (i != 0)
        {
          consumePendingUpdateOperations();
          if (findContainingItemView(paramView) == null) {
            return null;
          }
          startInterceptRequestLayout();
          localObject = mLayout.onFocusSearchFailed(paramView, paramInt, mRecycler, mState);
          stopInterceptRequestLayout(false);
          k = paramInt;
        }
      }
    }
    if ((localObject != null) && (!((View)localObject).hasFocusable()))
    {
      if (getFocusedChild() == null) {
        return super.focusSearch(paramView, k);
      }
      requestChildOnScreen((View)localObject, null);
      return paramView;
    }
    if (isPreferredNextFocus(paramView, (View)localObject, k)) {
      return localObject;
    }
    return super.focusSearch(paramView, k);
  }
  
  protected ViewGroup.LayoutParams generateDefaultLayoutParams()
  {
    Object localObject = mLayout;
    if (localObject != null) {
      return ((LayoutManager)localObject).generateDefaultLayoutParams();
    }
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("RecyclerView has no LayoutManager");
    ((StringBuilder)localObject).append(exceptionLabel());
    throw new IllegalStateException(((StringBuilder)localObject).toString());
  }
  
  public ViewGroup.LayoutParams generateLayoutParams(AttributeSet paramAttributeSet)
  {
    LayoutManager localLayoutManager = mLayout;
    if (localLayoutManager != null) {
      return localLayoutManager.generateLayoutParams(getContext(), paramAttributeSet);
    }
    paramAttributeSet = new StringBuilder();
    paramAttributeSet.append("RecyclerView has no LayoutManager");
    paramAttributeSet.append(exceptionLabel());
    throw new IllegalStateException(paramAttributeSet.toString());
  }
  
  protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    LayoutManager localLayoutManager = mLayout;
    if (localLayoutManager != null) {
      return localLayoutManager.generateLayoutParams(paramLayoutParams);
    }
    paramLayoutParams = new StringBuilder();
    paramLayoutParams.append("RecyclerView has no LayoutManager");
    paramLayoutParams.append(exceptionLabel());
    throw new IllegalStateException(paramLayoutParams.toString());
  }
  
  public Adapter getAdapter()
  {
    return mAdapter;
  }
  
  int getAdapterPositionFor(ViewHolder paramViewHolder)
  {
    if ((!paramViewHolder.hasAnyOfTheFlags(524)) && (paramViewHolder.isBound())) {
      return mAdapterHelper.applyPendingUpdatesToPosition(mPosition);
    }
    return -1;
  }
  
  public int getBaseline()
  {
    LayoutManager localLayoutManager = mLayout;
    if (localLayoutManager != null) {
      return localLayoutManager.getBaseline();
    }
    return super.getBaseline();
  }
  
  long getChangedHolderKey(ViewHolder paramViewHolder)
  {
    if (mAdapter.hasStableIds()) {
      return paramViewHolder.getItemId();
    }
    return mPosition;
  }
  
  public int getChildAdapterPosition(View paramView)
  {
    paramView = getChildViewHolderInt(paramView);
    if (paramView != null) {
      return paramView.getAdapterPosition();
    }
    return -1;
  }
  
  protected int getChildDrawingOrder(int paramInt1, int paramInt2)
  {
    ChildDrawingOrderCallback localChildDrawingOrderCallback = mChildDrawingOrderCallback;
    if (localChildDrawingOrderCallback == null) {
      return super.getChildDrawingOrder(paramInt1, paramInt2);
    }
    return localChildDrawingOrderCallback.onGetChildDrawingOrder(paramInt1, paramInt2);
  }
  
  public long getChildItemId(View paramView)
  {
    Adapter localAdapter = mAdapter;
    if (localAdapter != null)
    {
      if (!localAdapter.hasStableIds()) {
        return -1L;
      }
      paramView = getChildViewHolderInt(paramView);
      if (paramView != null) {
        return paramView.getItemId();
      }
    }
    return -1L;
  }
  
  public int getChildLayoutPosition(View paramView)
  {
    paramView = getChildViewHolderInt(paramView);
    if (paramView != null) {
      return paramView.getLayoutPosition();
    }
    return -1;
  }
  
  public int getChildPosition(View paramView)
  {
    return getChildAdapterPosition(paramView);
  }
  
  public ViewHolder getChildViewHolder(View paramView)
  {
    Object localObject = paramView.getParent();
    if ((localObject != null) && (localObject != this))
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("View ");
      ((StringBuilder)localObject).append(paramView);
      ((StringBuilder)localObject).append(" is not a direct child of ");
      ((StringBuilder)localObject).append(this);
      throw new IllegalArgumentException(((StringBuilder)localObject).toString());
    }
    return getChildViewHolderInt(paramView);
  }
  
  public boolean getClipToPadding()
  {
    return mClipToPadding;
  }
  
  public RecyclerViewAccessibilityDelegate getCompatAccessibilityDelegate()
  {
    return mAccessibilityDelegate;
  }
  
  public void getDecoratedBoundsWithMargins(View paramView, Rect paramRect)
  {
    getDecoratedBoundsWithMarginsInt(paramView, paramRect);
  }
  
  public EdgeEffectFactory getEdgeEffectFactory()
  {
    return mEdgeEffectFactory;
  }
  
  public ItemAnimator getItemAnimator()
  {
    return mItemAnimator;
  }
  
  Rect getItemDecorInsetsForChild(View paramView)
  {
    LayoutParams localLayoutParams = (LayoutParams)paramView.getLayoutParams();
    if (!mInsetsDirty) {
      return mDecorInsets;
    }
    if ((mState.isPreLayout()) && ((localLayoutParams.isItemChanged()) || (localLayoutParams.isViewInvalid()))) {
      return mDecorInsets;
    }
    Rect localRect = mDecorInsets;
    localRect.set(0, 0, 0, 0);
    int j = mItemDecorations.size();
    int i = 0;
    while (i < j)
    {
      mTempRect.set(0, 0, 0, 0);
      ((ItemDecoration)mItemDecorations.get(i)).getItemOffsets(mTempRect, paramView, this, mState);
      left += mTempRect.left;
      top += mTempRect.top;
      right += mTempRect.right;
      bottom += mTempRect.bottom;
      i += 1;
    }
    mInsetsDirty = false;
    return localRect;
  }
  
  public ItemDecoration getItemDecorationAt(int paramInt)
  {
    int i = getItemDecorationCount();
    if ((paramInt >= 0) && (paramInt < i)) {
      return (ItemDecoration)mItemDecorations.get(paramInt);
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramInt);
    localStringBuilder.append(" is an invalid index for size ");
    localStringBuilder.append(i);
    throw new IndexOutOfBoundsException(localStringBuilder.toString());
  }
  
  public int getItemDecorationCount()
  {
    return mItemDecorations.size();
  }
  
  public LayoutManager getLayoutManager()
  {
    return mLayout;
  }
  
  public int getMaxFlingVelocity()
  {
    return mMaxFlingVelocity;
  }
  
  public int getMinFlingVelocity()
  {
    return mMinFlingVelocity;
  }
  
  long getNanoTime()
  {
    if (ALLOW_THREAD_GAP_WORK) {
      return System.nanoTime();
    }
    return 0L;
  }
  
  public OnFlingListener getOnFlingListener()
  {
    return mOnFlingListener;
  }
  
  public boolean getPreserveFocusAfterLayout()
  {
    return mPreserveFocusAfterLayout;
  }
  
  public RecycledViewPool getRecycledViewPool()
  {
    return mRecycler.getRecycledViewPool();
  }
  
  public int getScrollState()
  {
    return mScrollState;
  }
  
  public boolean hasFixedSize()
  {
    return mHasFixedSize;
  }
  
  public boolean hasNestedScrollingParent()
  {
    return getScrollingChildHelper().hasNestedScrollingParent();
  }
  
  public boolean hasNestedScrollingParent(int paramInt)
  {
    return getScrollingChildHelper().hasNestedScrollingParent(paramInt);
  }
  
  public boolean hasPendingAdapterUpdates()
  {
    return (!mFirstLayoutComplete) || (mDataSetHasChangedAfterLayout) || (mAdapterHelper.hasPendingUpdates());
  }
  
  void initAdapterManager()
  {
    mAdapterHelper = new AdapterHelper(new AdapterHelper.Callback()
    {
      void dispatchUpdate(AdapterHelper.UpdateOp paramAnonymousUpdateOp)
      {
        int i = cmd;
        if (i != 1)
        {
          if (i != 2)
          {
            if (i != 4)
            {
              if (i != 8) {
                return;
              }
              mLayout.onItemsMoved(RecyclerView.this, positionStart, itemCount, 1);
              return;
            }
            mLayout.onItemsUpdated(RecyclerView.this, positionStart, itemCount, payload);
            return;
          }
          mLayout.onItemsRemoved(RecyclerView.this, positionStart, itemCount);
          return;
        }
        mLayout.onItemsAdded(RecyclerView.this, positionStart, itemCount);
      }
      
      public RecyclerView.ViewHolder findViewHolder(int paramAnonymousInt)
      {
        RecyclerView.ViewHolder localViewHolder = findViewHolderForPosition(paramAnonymousInt, true);
        if (localViewHolder == null) {
          return null;
        }
        if (mChildHelper.isHidden(itemView)) {
          return null;
        }
        return localViewHolder;
      }
      
      public void markViewHoldersUpdated(int paramAnonymousInt1, int paramAnonymousInt2, Object paramAnonymousObject)
      {
        viewRangeUpdate(paramAnonymousInt1, paramAnonymousInt2, paramAnonymousObject);
        mItemsChanged = true;
      }
      
      public void offsetPositionsForAdd(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        offsetPositionRecordsForInsert(paramAnonymousInt1, paramAnonymousInt2);
        mItemsAddedOrRemoved = true;
      }
      
      public void offsetPositionsForMove(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        offsetPositionRecordsForMove(paramAnonymousInt1, paramAnonymousInt2);
        mItemsAddedOrRemoved = true;
      }
      
      public void offsetPositionsForRemovingInvisible(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        offsetPositionRecordsForRemove(paramAnonymousInt1, paramAnonymousInt2, true);
        Object localObject = RecyclerView.this;
        mItemsAddedOrRemoved = true;
        localObject = mState;
        mDeletedInvisibleItemCountSincePreviousLayout += paramAnonymousInt2;
      }
      
      public void offsetPositionsForRemovingLaidOutOrNewView(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        offsetPositionRecordsForRemove(paramAnonymousInt1, paramAnonymousInt2, false);
        mItemsAddedOrRemoved = true;
      }
      
      public void onDispatchFirstPass(AdapterHelper.UpdateOp paramAnonymousUpdateOp)
      {
        dispatchUpdate(paramAnonymousUpdateOp);
      }
      
      public void onDispatchSecondPass(AdapterHelper.UpdateOp paramAnonymousUpdateOp)
      {
        dispatchUpdate(paramAnonymousUpdateOp);
      }
    });
  }
  
  void initFastScroller(StateListDrawable paramStateListDrawable1, Drawable paramDrawable1, StateListDrawable paramStateListDrawable2, Drawable paramDrawable2)
  {
    if ((paramStateListDrawable1 != null) && (paramDrawable1 != null) && (paramStateListDrawable2 != null) && (paramDrawable2 != null))
    {
      Resources localResources = getContext().getResources();
      new FastScroller(this, paramStateListDrawable1, paramDrawable1, paramStateListDrawable2, paramDrawable2, localResources.getDimensionPixelSize(R.dimen.fastscroll_default_thickness), localResources.getDimensionPixelSize(R.dimen.fastscroll_minimum_range), localResources.getDimensionPixelOffset(R.dimen.fastscroll_margin));
      return;
    }
    paramStateListDrawable1 = new StringBuilder();
    paramStateListDrawable1.append("Trying to set fast scroller without both required drawables.");
    paramStateListDrawable1.append(exceptionLabel());
    throw new IllegalArgumentException(paramStateListDrawable1.toString());
  }
  
  void invalidateGlows()
  {
    mBottomGlow = null;
    mTopGlow = null;
    mRightGlow = null;
    mLeftGlow = null;
  }
  
  public void invalidateItemDecorations()
  {
    if (mItemDecorations.size() == 0) {
      return;
    }
    LayoutManager localLayoutManager = mLayout;
    if (localLayoutManager != null) {
      localLayoutManager.assertNotInLayoutOrScroll("Cannot invalidate item decorations during a scroll or layout");
    }
    markItemDecorInsetsDirty();
    requestLayout();
  }
  
  boolean isAccessibilityEnabled()
  {
    AccessibilityManager localAccessibilityManager = mAccessibilityManager;
    return (localAccessibilityManager != null) && (localAccessibilityManager.isEnabled());
  }
  
  public boolean isAnimating()
  {
    ItemAnimator localItemAnimator = mItemAnimator;
    return (localItemAnimator != null) && (localItemAnimator.isRunning());
  }
  
  public boolean isAttachedToWindow()
  {
    return mIsAttached;
  }
  
  public boolean isComputingLayout()
  {
    return mLayoutOrScrollCounter > 0;
  }
  
  public boolean isLayoutFrozen()
  {
    return mLayoutFrozen;
  }
  
  public boolean isNestedScrollingEnabled()
  {
    return getScrollingChildHelper().isNestedScrollingEnabled();
  }
  
  void jumpToPositionForSmoothScroller(int paramInt)
  {
    LayoutManager localLayoutManager = mLayout;
    if (localLayoutManager == null) {
      return;
    }
    localLayoutManager.scrollToPosition(paramInt);
    awakenScrollBars();
  }
  
  void markItemDecorInsetsDirty()
  {
    int j = mChildHelper.getUnfilteredChildCount();
    int i = 0;
    while (i < j)
    {
      mChildHelper.getUnfilteredChildAt(i).getLayoutParams()).mInsetsDirty = true;
      i += 1;
    }
    mRecycler.markItemDecorInsetsDirty();
  }
  
  void markKnownViewsInvalid()
  {
    int j = mChildHelper.getUnfilteredChildCount();
    int i = 0;
    while (i < j)
    {
      ViewHolder localViewHolder = getChildViewHolderInt(mChildHelper.getUnfilteredChildAt(i));
      if ((localViewHolder != null) && (!localViewHolder.shouldIgnore())) {
        localViewHolder.addFlags(6);
      }
      i += 1;
    }
    markItemDecorInsetsDirty();
    mRecycler.markKnownViewsInvalid();
  }
  
  public void offsetChildrenHorizontal(int paramInt)
  {
    int j = mChildHelper.getChildCount();
    int i = 0;
    while (i < j)
    {
      mChildHelper.getChildAt(i).offsetLeftAndRight(paramInt);
      i += 1;
    }
  }
  
  public void offsetChildrenVertical(int paramInt)
  {
    int j = mChildHelper.getChildCount();
    int i = 0;
    while (i < j)
    {
      mChildHelper.getChildAt(i).offsetTopAndBottom(paramInt);
      i += 1;
    }
  }
  
  void offsetPositionRecordsForInsert(int paramInt1, int paramInt2)
  {
    int j = mChildHelper.getUnfilteredChildCount();
    int i = 0;
    while (i < j)
    {
      ViewHolder localViewHolder = getChildViewHolderInt(mChildHelper.getUnfilteredChildAt(i));
      if ((localViewHolder != null) && (!localViewHolder.shouldIgnore()) && (mPosition >= paramInt1))
      {
        localViewHolder.offsetPosition(paramInt2, false);
        mState.mStructureChanged = true;
      }
      i += 1;
    }
    mRecycler.offsetPositionRecordsForInsert(paramInt1, paramInt2);
    requestLayout();
  }
  
  void offsetPositionRecordsForMove(int paramInt1, int paramInt2)
  {
    int n = mChildHelper.getUnfilteredChildCount();
    int i;
    int j;
    int k;
    if (paramInt1 < paramInt2)
    {
      i = paramInt1;
      j = paramInt2;
      k = -1;
    }
    else
    {
      j = paramInt1;
      i = paramInt2;
      k = 1;
    }
    int m = 0;
    while (m < n)
    {
      ViewHolder localViewHolder = getChildViewHolderInt(mChildHelper.getUnfilteredChildAt(m));
      if ((localViewHolder != null) && (mPosition >= i) && (mPosition <= j))
      {
        if (mPosition == paramInt1) {
          localViewHolder.offsetPosition(paramInt2 - paramInt1, false);
        } else {
          localViewHolder.offsetPosition(k, false);
        }
        mState.mStructureChanged = true;
      }
      m += 1;
    }
    mRecycler.offsetPositionRecordsForMove(paramInt1, paramInt2);
    requestLayout();
  }
  
  void offsetPositionRecordsForRemove(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    int j = mChildHelper.getUnfilteredChildCount();
    int i = 0;
    while (i < j)
    {
      ViewHolder localViewHolder = getChildViewHolderInt(mChildHelper.getUnfilteredChildAt(i));
      if ((localViewHolder != null) && (!localViewHolder.shouldIgnore())) {
        if (mPosition >= paramInt1 + paramInt2)
        {
          localViewHolder.offsetPosition(-paramInt2, paramBoolean);
          mState.mStructureChanged = true;
        }
        else if (mPosition >= paramInt1)
        {
          localViewHolder.flagRemovedAndOffsetPosition(paramInt1 - 1, -paramInt2, paramBoolean);
          mState.mStructureChanged = true;
        }
      }
      i += 1;
    }
    mRecycler.offsetPositionRecordsForRemove(paramInt1, paramInt2, paramBoolean);
    requestLayout();
  }
  
  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    mLayoutOrScrollCounter = 0;
    boolean bool = true;
    mIsAttached = true;
    if ((!mFirstLayoutComplete) || (isLayoutRequested())) {
      bool = false;
    }
    mFirstLayoutComplete = bool;
    Object localObject = mLayout;
    if (localObject != null) {
      ((LayoutManager)localObject).dispatchAttachedToWindow(this);
    }
    mPostedAnimatorRunner = false;
    if (ALLOW_THREAD_GAP_WORK)
    {
      mGapWorker = ((GapWorker)GapWorker.sGapWorker.get());
      if (mGapWorker == null)
      {
        mGapWorker = new GapWorker();
        localObject = ViewCompat.getDisplay(this);
        float f1;
        if ((!isInEditMode()) && (localObject != null))
        {
          float f2 = ((Display)localObject).getRefreshRate();
          f1 = f2;
          if (f2 >= 30.0F) {}
        }
        else
        {
          f1 = 60.0F;
        }
        mGapWorker.mFrameIntervalNs = ((1.0E9F / f1));
        GapWorker.sGapWorker.set(mGapWorker);
      }
      mGapWorker.addCallback(this);
    }
  }
  
  public void onChildAttachedToWindow(View paramView) {}
  
  public void onChildDetachedFromWindow(View paramView) {}
  
  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    Object localObject = mItemAnimator;
    if (localObject != null) {
      ((ItemAnimator)localObject).endAnimations();
    }
    stopScroll();
    mIsAttached = false;
    localObject = mLayout;
    if (localObject != null) {
      ((LayoutManager)localObject).dispatchDetachedFromWindow(this, mRecycler);
    }
    mPendingAccessibilityImportanceChange.clear();
    removeCallbacks(mItemAnimatorRunner);
    mViewInfoStore.onDetach();
    if (ALLOW_THREAD_GAP_WORK)
    {
      localObject = mGapWorker;
      if (localObject != null)
      {
        ((GapWorker)localObject).remove(this);
        mGapWorker = null;
      }
    }
  }
  
  public void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    int j = mItemDecorations.size();
    int i = 0;
    while (i < j)
    {
      ((ItemDecoration)mItemDecorations.get(i)).onDraw(paramCanvas, this, mState);
      i += 1;
    }
  }
  
  void onEnterLayoutOrScroll()
  {
    mLayoutOrScrollCounter += 1;
  }
  
  void onExitLayoutOrScroll()
  {
    onExitLayoutOrScroll(true);
  }
  
  void onExitLayoutOrScroll(boolean paramBoolean)
  {
    mLayoutOrScrollCounter -= 1;
    if (mLayoutOrScrollCounter < 1)
    {
      mLayoutOrScrollCounter = 0;
      if (paramBoolean)
      {
        dispatchContentChangedIfNecessary();
        dispatchPendingImportantForAccessibilityChanges();
      }
    }
  }
  
  public boolean onGenericMotionEvent(MotionEvent paramMotionEvent)
  {
    if (mLayout == null) {
      return false;
    }
    if (mLayoutFrozen) {
      return false;
    }
    if (paramMotionEvent.getAction() == 8)
    {
      float f1;
      if ((paramMotionEvent.getSource() & 0x2) != 0)
      {
        if (mLayout.canScrollVertically()) {
          f2 = -paramMotionEvent.getAxisValue(9);
        } else {
          f2 = 0.0F;
        }
        f1 = f2;
        if (mLayout.canScrollHorizontally())
        {
          float f3 = paramMotionEvent.getAxisValue(10);
          f1 = f2;
          f2 = f3;
          break label140;
        }
      }
      else
      {
        if ((paramMotionEvent.getSource() & 0x400000) != 0)
        {
          f2 = paramMotionEvent.getAxisValue(26);
          if (mLayout.canScrollVertically())
          {
            f1 = -f2;
            break label138;
          }
          if (mLayout.canScrollHorizontally())
          {
            f1 = 0.0F;
            break label140;
          }
        }
        f1 = 0.0F;
      }
      label138:
      float f2 = 0.0F;
      label140:
      if ((f1 != 0.0F) || (f2 != 0.0F)) {
        scrollByInternal((int)(f2 * mScaledHorizontalScrollFactor), (int)(f1 * mScaledVerticalScrollFactor), paramMotionEvent);
      }
    }
    return false;
  }
  
  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
  {
    if (mLayoutFrozen) {
      return false;
    }
    if (dispatchOnItemTouchIntercept(paramMotionEvent))
    {
      cancelTouch();
      return true;
    }
    LayoutManager localLayoutManager = mLayout;
    if (localLayoutManager == null) {
      return false;
    }
    boolean bool1 = localLayoutManager.canScrollHorizontally();
    boolean bool2 = mLayout.canScrollVertically();
    if (mVelocityTracker == null) {
      mVelocityTracker = VelocityTracker.obtain();
    }
    mVelocityTracker.addMovement(paramMotionEvent);
    int j = paramMotionEvent.getActionMasked();
    int i = paramMotionEvent.getActionIndex();
    if (j != 0)
    {
      if (j != 1)
      {
        if (j != 2)
        {
          if (j != 3)
          {
            if (j != 5)
            {
              if (j == 6) {
                onPointerUp(paramMotionEvent);
              }
            }
            else
            {
              mScrollPointerId = paramMotionEvent.getPointerId(i);
              j = (int)(paramMotionEvent.getX(i) + 0.5F);
              mLastTouchX = j;
              mInitialTouchX = j;
              i = (int)(paramMotionEvent.getY(i) + 0.5F);
              mLastTouchY = i;
              mInitialTouchY = i;
            }
          }
          else {
            cancelTouch();
          }
        }
        else
        {
          j = paramMotionEvent.findPointerIndex(mScrollPointerId);
          if (j < 0)
          {
            paramMotionEvent = new StringBuilder();
            paramMotionEvent.append("Error processing scroll; pointer index for id ");
            paramMotionEvent.append(mScrollPointerId);
            paramMotionEvent.append(" not found. Did any MotionEvents get skipped?");
            Log.e("RecyclerView", paramMotionEvent.toString());
            return false;
          }
          i = (int)(paramMotionEvent.getX(j) + 0.5F);
          int k = (int)(paramMotionEvent.getY(j) + 0.5F);
          if (mScrollState != 1)
          {
            j = mInitialTouchX;
            int m = mInitialTouchY;
            if ((bool1) && (Math.abs(i - j) > mTouchSlop))
            {
              mLastTouchX = i;
              i = 1;
            }
            else
            {
              i = 0;
            }
            j = i;
            if (bool2)
            {
              j = i;
              if (Math.abs(k - m) > mTouchSlop)
              {
                mLastTouchY = k;
                j = 1;
              }
            }
            if (j != 0) {
              setScrollState(1);
            }
          }
        }
      }
      else
      {
        mVelocityTracker.clear();
        stopNestedScroll(0);
      }
    }
    else
    {
      if (mIgnoreMotionEventTillDown) {
        mIgnoreMotionEventTillDown = false;
      }
      mScrollPointerId = paramMotionEvent.getPointerId(0);
      i = (int)(paramMotionEvent.getX() + 0.5F);
      mLastTouchX = i;
      mInitialTouchX = i;
      i = (int)(paramMotionEvent.getY() + 0.5F);
      mLastTouchY = i;
      mInitialTouchY = i;
      if (mScrollState == 2)
      {
        getParent().requestDisallowInterceptTouchEvent(true);
        setScrollState(1);
      }
      paramMotionEvent = mNestedOffsets;
      paramMotionEvent[1] = 0;
      paramMotionEvent[0] = 0;
      if (bool1) {
        i = 1;
      } else {
        i = 0;
      }
      j = i;
      if (bool2) {
        j = i | 0x2;
      }
      startNestedScroll(j, 0);
    }
    return mScrollState == 1;
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    TraceCompat.beginSection("RV OnLayout");
    dispatchLayout();
    TraceCompat.endSection();
    mFirstLayoutComplete = true;
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    Object localObject = mLayout;
    if (localObject == null)
    {
      defaultOnMeasure(paramInt1, paramInt2);
      return;
    }
    boolean bool = ((LayoutManager)localObject).isAutoMeasureEnabled();
    int j = 0;
    if (bool)
    {
      int k = View.MeasureSpec.getMode(paramInt1);
      int m = View.MeasureSpec.getMode(paramInt2);
      mLayout.onMeasure(mRecycler, mState, paramInt1, paramInt2);
      int i = j;
      if (k == 1073741824)
      {
        i = j;
        if (m == 1073741824) {
          i = 1;
        }
      }
      if (i == 0)
      {
        if (mAdapter == null) {
          return;
        }
        if (mState.mLayoutStep == 1) {
          dispatchLayoutStep1();
        }
        mLayout.setMeasureSpecs(paramInt1, paramInt2);
        mState.mIsMeasuring = true;
        dispatchLayoutStep2();
        mLayout.setMeasuredDimensionFromChildren(paramInt1, paramInt2);
        if (!mLayout.shouldMeasureTwice()) {
          return;
        }
        mLayout.setMeasureSpecs(View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824));
        mState.mIsMeasuring = true;
        dispatchLayoutStep2();
        mLayout.setMeasuredDimensionFromChildren(paramInt1, paramInt2);
      }
    }
    else
    {
      if (mHasFixedSize)
      {
        mLayout.onMeasure(mRecycler, mState, paramInt1, paramInt2);
        return;
      }
      if (mAdapterUpdateDuringMeasure)
      {
        startInterceptRequestLayout();
        onEnterLayoutOrScroll();
        processAdapterUpdatesAndSetAnimationFlags();
        onExitLayoutOrScroll();
        if (mState.mRunPredictiveAnimations)
        {
          mState.mInPreLayout = true;
        }
        else
        {
          mAdapterHelper.consumeUpdatesInOnePass();
          mState.mInPreLayout = false;
        }
        mAdapterUpdateDuringMeasure = false;
        stopInterceptRequestLayout(false);
      }
      else if (mState.mRunPredictiveAnimations)
      {
        setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());
        return;
      }
      localObject = mAdapter;
      if (localObject != null) {
        mState.mItemCount = ((Adapter)localObject).getItemCount();
      } else {
        mState.mItemCount = 0;
      }
      startInterceptRequestLayout();
      mLayout.onMeasure(mRecycler, mState, paramInt1, paramInt2);
      stopInterceptRequestLayout(false);
      mState.mInPreLayout = false;
    }
  }
  
  protected boolean onRequestFocusInDescendants(int paramInt, Rect paramRect)
  {
    if (isComputingLayout()) {
      return false;
    }
    return super.onRequestFocusInDescendants(paramInt, paramRect);
  }
  
  protected void onRestoreInstanceState(Parcelable paramParcelable)
  {
    if (!(paramParcelable instanceof SavedState))
    {
      super.onRestoreInstanceState(paramParcelable);
      return;
    }
    mPendingSavedState = ((SavedState)paramParcelable);
    super.onRestoreInstanceState(mPendingSavedState.getSuperState());
    if ((mLayout != null) && (mPendingSavedState.mLayoutState != null)) {
      mLayout.onRestoreInstanceState(mPendingSavedState.mLayoutState);
    }
  }
  
  protected Parcelable onSaveInstanceState()
  {
    SavedState localSavedState = new SavedState(super.onSaveInstanceState());
    Object localObject = mPendingSavedState;
    if (localObject != null)
    {
      localSavedState.copyFrom((SavedState)localObject);
      return localSavedState;
    }
    localObject = mLayout;
    if (localObject != null)
    {
      mLayoutState = ((LayoutManager)localObject).onSaveInstanceState();
      return localSavedState;
    }
    mLayoutState = null;
    return localSavedState;
  }
  
  public void onScrollStateChanged(int paramInt) {}
  
  public void onScrolled(int paramInt1, int paramInt2) {}
  
  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    if ((paramInt1 != paramInt3) || (paramInt2 != paramInt4)) {
      invalidateGlows();
    }
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    boolean bool1 = mLayoutFrozen;
    int i2 = 0;
    if (!bool1)
    {
      if (mIgnoreMotionEventTillDown) {
        return false;
      }
      if (dispatchOnItemTouch(paramMotionEvent))
      {
        cancelTouch();
        return true;
      }
      Object localObject = mLayout;
      if (localObject == null) {
        return false;
      }
      bool1 = ((LayoutManager)localObject).canScrollHorizontally();
      boolean bool2 = mLayout.canScrollVertically();
      if (mVelocityTracker == null) {
        mVelocityTracker = VelocityTracker.obtain();
      }
      localObject = MotionEvent.obtain(paramMotionEvent);
      int j = paramMotionEvent.getActionMasked();
      int i = paramMotionEvent.getActionIndex();
      if (j == 0)
      {
        arrayOfInt = mNestedOffsets;
        arrayOfInt[1] = 0;
        arrayOfInt[0] = 0;
      }
      int[] arrayOfInt = mNestedOffsets;
      ((MotionEvent)localObject).offsetLocation(arrayOfInt[0], arrayOfInt[1]);
      if (j != 0)
      {
        if (j != 1)
        {
          if (j != 2)
          {
            if (j != 3)
            {
              if (j != 5)
              {
                if (j != 6)
                {
                  i = i2;
                }
                else
                {
                  onPointerUp(paramMotionEvent);
                  i = i2;
                }
              }
              else
              {
                mScrollPointerId = paramMotionEvent.getPointerId(i);
                j = (int)(paramMotionEvent.getX(i) + 0.5F);
                mLastTouchX = j;
                mInitialTouchX = j;
                i = (int)(paramMotionEvent.getY(i) + 0.5F);
                mLastTouchY = i;
                mInitialTouchY = i;
                i = i2;
              }
            }
            else
            {
              cancelTouch();
              i = i2;
            }
          }
          else
          {
            i = paramMotionEvent.findPointerIndex(mScrollPointerId);
            if (i < 0)
            {
              paramMotionEvent = new StringBuilder();
              paramMotionEvent.append("Error processing scroll; pointer index for id ");
              paramMotionEvent.append(mScrollPointerId);
              paramMotionEvent.append(" not found. Did any MotionEvents get skipped?");
              Log.e("RecyclerView", paramMotionEvent.toString());
              return false;
            }
            int i3 = (int)(paramMotionEvent.getX(i) + 0.5F);
            int i4 = (int)(paramMotionEvent.getY(i) + 0.5F);
            int m = mLastTouchX - i3;
            int k = mLastTouchY - i4;
            i = k;
            j = m;
            if (dispatchNestedPreScroll(m, k, mScrollConsumed, mScrollOffset, 0))
            {
              paramMotionEvent = mScrollConsumed;
              j = m - paramMotionEvent[0];
              i = k - paramMotionEvent[1];
              paramMotionEvent = mScrollOffset;
              ((MotionEvent)localObject).offsetLocation(paramMotionEvent[0], paramMotionEvent[1]);
              paramMotionEvent = mNestedOffsets;
              k = paramMotionEvent[0];
              arrayOfInt = mScrollOffset;
              paramMotionEvent[0] = (k + arrayOfInt[0]);
              paramMotionEvent[1] += arrayOfInt[1];
            }
            k = i;
            m = j;
            if (mScrollState != 1)
            {
              if (bool1)
              {
                k = Math.abs(j);
                m = mTouchSlop;
                if (k > m)
                {
                  if (j > 0) {
                    j -= m;
                  } else {
                    j += m;
                  }
                  k = 1;
                  break label553;
                }
              }
              k = 0;
              label553:
              int n = i;
              int i1 = k;
              if (bool2)
              {
                m = Math.abs(i);
                int i5 = mTouchSlop;
                n = i;
                i1 = k;
                if (m > i5)
                {
                  if (i > 0) {
                    n = i - i5;
                  } else {
                    n = i + i5;
                  }
                  i1 = 1;
                }
              }
              k = n;
              m = j;
              if (i1 != 0)
              {
                setScrollState(1);
                m = j;
                k = n;
              }
            }
            i = i2;
            if (mScrollState == 1)
            {
              paramMotionEvent = mScrollOffset;
              mLastTouchX = (i3 - paramMotionEvent[0]);
              mLastTouchY = (i4 - paramMotionEvent[1]);
              if (bool1) {
                i = m;
              } else {
                i = 0;
              }
              if (bool2) {
                j = k;
              } else {
                j = 0;
              }
              if (scrollByInternal(i, j, (MotionEvent)localObject)) {
                getParent().requestDisallowInterceptTouchEvent(true);
              }
              i = i2;
              if (mGapWorker != null) {
                if (m == 0)
                {
                  i = i2;
                  if (k == 0) {}
                }
                else
                {
                  mGapWorker.postFromTraversal(this, m, k);
                  i = i2;
                }
              }
            }
          }
        }
        else
        {
          mVelocityTracker.addMovement((MotionEvent)localObject);
          mVelocityTracker.computeCurrentVelocity(1000, mMaxFlingVelocity);
          float f1;
          if (bool1) {
            f1 = -mVelocityTracker.getXVelocity(mScrollPointerId);
          } else {
            f1 = 0.0F;
          }
          float f2;
          if (bool2) {
            f2 = -mVelocityTracker.getYVelocity(mScrollPointerId);
          } else {
            f2 = 0.0F;
          }
          if (((f1 == 0.0F) && (f2 == 0.0F)) || (!fling((int)f1, (int)f2))) {
            setScrollState(0);
          }
          resetTouch();
          i = 1;
        }
      }
      else
      {
        mScrollPointerId = paramMotionEvent.getPointerId(0);
        i = (int)(paramMotionEvent.getX() + 0.5F);
        mLastTouchX = i;
        mInitialTouchX = i;
        i = (int)(paramMotionEvent.getY() + 0.5F);
        mLastTouchY = i;
        mInitialTouchY = i;
        if (bool1) {
          i = 1;
        } else {
          i = 0;
        }
        j = i;
        if (bool2) {
          j = i | 0x2;
        }
        startNestedScroll(j, 0);
        i = i2;
      }
      if (i == 0) {
        mVelocityTracker.addMovement((MotionEvent)localObject);
      }
      ((MotionEvent)localObject).recycle();
      return true;
    }
    return false;
  }
  
  void postAnimationRunner()
  {
    if ((!mPostedAnimatorRunner) && (mIsAttached))
    {
      ViewCompat.postOnAnimation(this, mItemAnimatorRunner);
      mPostedAnimatorRunner = true;
    }
  }
  
  void processDataSetCompletelyChanged(boolean paramBoolean)
  {
    mDispatchItemsChangedEvent = (paramBoolean | mDispatchItemsChangedEvent);
    mDataSetHasChangedAfterLayout = true;
    markKnownViewsInvalid();
  }
  
  void recordAnimationInfoIfBouncedHiddenView(ViewHolder paramViewHolder, RecyclerView.ItemAnimator.ItemHolderInfo paramItemHolderInfo)
  {
    paramViewHolder.setFlags(0, 8192);
    if ((mState.mTrackOldChangeHolders) && (paramViewHolder.isUpdated()) && (!paramViewHolder.isRemoved()) && (!paramViewHolder.shouldIgnore()))
    {
      long l = getChangedHolderKey(paramViewHolder);
      mViewInfoStore.addToOldChangeHolders(l, paramViewHolder);
    }
    mViewInfoStore.addToPreLayout(paramViewHolder, paramItemHolderInfo);
  }
  
  void removeAndRecycleViews()
  {
    Object localObject = mItemAnimator;
    if (localObject != null) {
      ((ItemAnimator)localObject).endAnimations();
    }
    localObject = mLayout;
    if (localObject != null)
    {
      ((LayoutManager)localObject).removeAndRecycleAllViews(mRecycler);
      mLayout.removeAndRecycleScrapInt(mRecycler);
    }
    mRecycler.clear();
  }
  
  boolean removeAnimatingView(View paramView)
  {
    startInterceptRequestLayout();
    boolean bool = mChildHelper.removeViewIfHidden(paramView);
    if (bool)
    {
      paramView = getChildViewHolderInt(paramView);
      mRecycler.unscrapView(paramView);
      mRecycler.recycleViewHolderInternal(paramView);
    }
    stopInterceptRequestLayout(bool ^ true);
    return bool;
  }
  
  protected void removeDetachedView(View paramView, boolean paramBoolean)
  {
    ViewHolder localViewHolder = getChildViewHolderInt(paramView);
    if (localViewHolder != null) {
      if (localViewHolder.isTmpDetached())
      {
        localViewHolder.clearTmpDetachFlag();
      }
      else if (!localViewHolder.shouldIgnore())
      {
        paramView = new StringBuilder();
        paramView.append("Called removeDetachedView with a view which is not flagged as tmp detached.");
        paramView.append(localViewHolder);
        paramView.append(exceptionLabel());
        throw new IllegalArgumentException(paramView.toString());
      }
    }
    paramView.clearAnimation();
    dispatchChildDetached(paramView);
    super.removeDetachedView(paramView, paramBoolean);
  }
  
  public void removeItemDecoration(ItemDecoration paramItemDecoration)
  {
    LayoutManager localLayoutManager = mLayout;
    if (localLayoutManager != null) {
      localLayoutManager.assertNotInLayoutOrScroll("Cannot remove item decoration during a scroll  or layout");
    }
    mItemDecorations.remove(paramItemDecoration);
    if (mItemDecorations.isEmpty())
    {
      boolean bool;
      if (getOverScrollMode() == 2) {
        bool = true;
      } else {
        bool = false;
      }
      setWillNotDraw(bool);
    }
    markItemDecorInsetsDirty();
    requestLayout();
  }
  
  public void removeItemDecorationAt(int paramInt)
  {
    int i = getItemDecorationCount();
    if ((paramInt >= 0) && (paramInt < i))
    {
      removeItemDecoration(getItemDecorationAt(paramInt));
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramInt);
    localStringBuilder.append(" is an invalid index for size ");
    localStringBuilder.append(i);
    throw new IndexOutOfBoundsException(localStringBuilder.toString());
  }
  
  public void removeOnChildAttachStateChangeListener(OnChildAttachStateChangeListener paramOnChildAttachStateChangeListener)
  {
    List localList = mOnChildAttachStateListeners;
    if (localList == null) {
      return;
    }
    localList.remove(paramOnChildAttachStateChangeListener);
  }
  
  public void removeOnItemTouchListener(OnItemTouchListener paramOnItemTouchListener)
  {
    mOnItemTouchListeners.remove(paramOnItemTouchListener);
    if (mActiveOnItemTouchListener == paramOnItemTouchListener) {
      mActiveOnItemTouchListener = null;
    }
  }
  
  public void removeOnScrollListener(OnScrollListener paramOnScrollListener)
  {
    List localList = mScrollListeners;
    if (localList != null) {
      localList.remove(paramOnScrollListener);
    }
  }
  
  void repositionShadowingViews()
  {
    int j = mChildHelper.getChildCount();
    int i = 0;
    while (i < j)
    {
      View localView = mChildHelper.getChildAt(i);
      Object localObject = getChildViewHolder(localView);
      if ((localObject != null) && (mShadowingHolder != null))
      {
        localObject = mShadowingHolder.itemView;
        int k = localView.getLeft();
        int m = localView.getTop();
        if ((k != ((View)localObject).getLeft()) || (m != ((View)localObject).getTop())) {
          ((View)localObject).layout(k, m, ((View)localObject).getWidth() + k, ((View)localObject).getHeight() + m);
        }
      }
      i += 1;
    }
  }
  
  public void requestChildFocus(View paramView1, View paramView2)
  {
    if ((!mLayout.onRequestChildFocus(this, mState, paramView1, paramView2)) && (paramView2 != null)) {
      requestChildOnScreen(paramView1, paramView2);
    }
    super.requestChildFocus(paramView1, paramView2);
  }
  
  public boolean requestChildRectangleOnScreen(View paramView, Rect paramRect, boolean paramBoolean)
  {
    return mLayout.requestChildRectangleOnScreen(this, paramView, paramRect, paramBoolean);
  }
  
  public void requestDisallowInterceptTouchEvent(boolean paramBoolean)
  {
    int j = mOnItemTouchListeners.size();
    int i = 0;
    while (i < j)
    {
      ((OnItemTouchListener)mOnItemTouchListeners.get(i)).onRequestDisallowInterceptTouchEvent(paramBoolean);
      i += 1;
    }
    super.requestDisallowInterceptTouchEvent(paramBoolean);
  }
  
  public void requestLayout()
  {
    if ((mInterceptRequestLayoutDepth == 0) && (!mLayoutFrozen))
    {
      super.requestLayout();
      return;
    }
    mLayoutWasDefered = true;
  }
  
  void saveOldPositions()
  {
    int j = mChildHelper.getUnfilteredChildCount();
    int i = 0;
    while (i < j)
    {
      ViewHolder localViewHolder = getChildViewHolderInt(mChildHelper.getUnfilteredChildAt(i));
      if (!localViewHolder.shouldIgnore()) {
        localViewHolder.saveOldPosition();
      }
      i += 1;
    }
  }
  
  public void scrollBy(int paramInt1, int paramInt2)
  {
    LayoutManager localLayoutManager = mLayout;
    if (localLayoutManager == null)
    {
      Log.e("RecyclerView", "Cannot scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
      return;
    }
    if (mLayoutFrozen) {
      return;
    }
    boolean bool1 = localLayoutManager.canScrollHorizontally();
    boolean bool2 = mLayout.canScrollVertically();
    if ((bool1) || (bool2))
    {
      if (!bool1) {
        paramInt1 = 0;
      }
      if (!bool2) {
        paramInt2 = 0;
      }
      scrollByInternal(paramInt1, paramInt2, null);
    }
  }
  
  boolean scrollByInternal(int paramInt1, int paramInt2, MotionEvent paramMotionEvent)
  {
    consumePendingUpdateOperations();
    int[] arrayOfInt;
    int i;
    int j;
    int k;
    int m;
    if (mAdapter != null)
    {
      scrollStep(paramInt1, paramInt2, mScrollStepConsumed);
      arrayOfInt = mScrollStepConsumed;
      i = arrayOfInt[0];
      j = arrayOfInt[1];
      k = paramInt1 - i;
      m = paramInt2 - j;
    }
    else
    {
      j = 0;
      k = 0;
      m = 0;
      i = 0;
    }
    if (!mItemDecorations.isEmpty()) {
      invalidate();
    }
    if (dispatchNestedScroll(i, j, k, m, mScrollOffset, 0))
    {
      paramInt1 = mLastTouchX;
      arrayOfInt = mScrollOffset;
      mLastTouchX = (paramInt1 - arrayOfInt[0]);
      mLastTouchY -= arrayOfInt[1];
      if (paramMotionEvent != null) {
        paramMotionEvent.offsetLocation(arrayOfInt[0], arrayOfInt[1]);
      }
      paramMotionEvent = mNestedOffsets;
      paramInt1 = paramMotionEvent[0];
      arrayOfInt = mScrollOffset;
      paramMotionEvent[0] = (paramInt1 + arrayOfInt[0]);
      paramMotionEvent[1] += arrayOfInt[1];
    }
    else if (getOverScrollMode() != 2)
    {
      if ((paramMotionEvent != null) && (!MotionEventCompat.isFromSource(paramMotionEvent, 8194))) {
        pullGlows(paramMotionEvent.getX(), k, paramMotionEvent.getY(), m);
      }
      considerReleasingGlowsOnScroll(paramInt1, paramInt2);
    }
    if ((i != 0) || (j != 0)) {
      dispatchOnScrolled(i, j);
    }
    if (!awakenScrollBars()) {
      invalidate();
    }
    return (i != 0) || (j != 0);
  }
  
  void scrollStep(int paramInt1, int paramInt2, int[] paramArrayOfInt)
  {
    startInterceptRequestLayout();
    onEnterLayoutOrScroll();
    TraceCompat.beginSection("RV Scroll");
    fillRemainingScrollValues(mState);
    if (paramInt1 != 0) {
      paramInt1 = mLayout.scrollHorizontallyBy(paramInt1, mRecycler, mState);
    } else {
      paramInt1 = 0;
    }
    if (paramInt2 != 0) {
      paramInt2 = mLayout.scrollVerticallyBy(paramInt2, mRecycler, mState);
    } else {
      paramInt2 = 0;
    }
    TraceCompat.endSection();
    repositionShadowingViews();
    onExitLayoutOrScroll();
    stopInterceptRequestLayout(false);
    if (paramArrayOfInt != null)
    {
      paramArrayOfInt[0] = paramInt1;
      paramArrayOfInt[1] = paramInt2;
    }
  }
  
  public void scrollTo(int paramInt1, int paramInt2)
  {
    Log.w("RecyclerView", "RecyclerView does not support scrolling to an absolute position. Use scrollToPosition instead");
  }
  
  public void scrollToPosition(int paramInt)
  {
    if (mLayoutFrozen) {
      return;
    }
    stopScroll();
    LayoutManager localLayoutManager = mLayout;
    if (localLayoutManager == null)
    {
      Log.e("RecyclerView", "Cannot scroll to position a LayoutManager set. Call setLayoutManager with a non-null argument.");
      return;
    }
    localLayoutManager.scrollToPosition(paramInt);
    awakenScrollBars();
  }
  
  public void sendAccessibilityEventUnchecked(AccessibilityEvent paramAccessibilityEvent)
  {
    if (shouldDeferAccessibilityEvent(paramAccessibilityEvent)) {
      return;
    }
    super.sendAccessibilityEventUnchecked(paramAccessibilityEvent);
  }
  
  public void setAccessibilityDelegateCompat(RecyclerViewAccessibilityDelegate paramRecyclerViewAccessibilityDelegate)
  {
    mAccessibilityDelegate = paramRecyclerViewAccessibilityDelegate;
    ViewCompat.setAccessibilityDelegate(this, mAccessibilityDelegate);
  }
  
  public void setAdapter(Adapter paramAdapter)
  {
    setLayoutFrozen(false);
    setAdapterInternal(paramAdapter, false, true);
    processDataSetCompletelyChanged(false);
    requestLayout();
  }
  
  public void setChildDrawingOrderCallback(ChildDrawingOrderCallback paramChildDrawingOrderCallback)
  {
    if (paramChildDrawingOrderCallback == mChildDrawingOrderCallback) {
      return;
    }
    mChildDrawingOrderCallback = paramChildDrawingOrderCallback;
    boolean bool;
    if (mChildDrawingOrderCallback != null) {
      bool = true;
    } else {
      bool = false;
    }
    setChildrenDrawingOrderEnabled(bool);
  }
  
  boolean setChildImportantForAccessibilityInternal(ViewHolder paramViewHolder, int paramInt)
  {
    if (isComputingLayout())
    {
      mPendingAccessibilityState = paramInt;
      mPendingAccessibilityImportanceChange.add(paramViewHolder);
      return false;
    }
    ViewCompat.setImportantForAccessibility(itemView, paramInt);
    return true;
  }
  
  public void setClipToPadding(boolean paramBoolean)
  {
    if (paramBoolean != mClipToPadding) {
      invalidateGlows();
    }
    mClipToPadding = paramBoolean;
    super.setClipToPadding(paramBoolean);
    if (mFirstLayoutComplete) {
      requestLayout();
    }
  }
  
  public void setEdgeEffectFactory(EdgeEffectFactory paramEdgeEffectFactory)
  {
    Preconditions.checkNotNull(paramEdgeEffectFactory);
    mEdgeEffectFactory = paramEdgeEffectFactory;
    invalidateGlows();
  }
  
  public void setHasFixedSize(boolean paramBoolean)
  {
    mHasFixedSize = paramBoolean;
  }
  
  public void setItemAnimator(ItemAnimator paramItemAnimator)
  {
    ItemAnimator localItemAnimator = mItemAnimator;
    if (localItemAnimator != null)
    {
      localItemAnimator.endAnimations();
      mItemAnimator.setListener(null);
    }
    mItemAnimator = paramItemAnimator;
    paramItemAnimator = mItemAnimator;
    if (paramItemAnimator != null) {
      paramItemAnimator.setListener(mItemAnimatorListener);
    }
  }
  
  public void setItemViewCacheSize(int paramInt)
  {
    mRecycler.setViewCacheSize(paramInt);
  }
  
  public void setLayoutFrozen(boolean paramBoolean)
  {
    if (paramBoolean != mLayoutFrozen)
    {
      assertNotInLayoutOrScroll("Do not setLayoutFrozen in layout or scroll");
      if (!paramBoolean)
      {
        mLayoutFrozen = false;
        if ((mLayoutWasDefered) && (mLayout != null) && (mAdapter != null)) {
          requestLayout();
        }
        mLayoutWasDefered = false;
        return;
      }
      long l = SystemClock.uptimeMillis();
      onTouchEvent(MotionEvent.obtain(l, l, 3, 0.0F, 0.0F, 0));
      mLayoutFrozen = true;
      mIgnoreMotionEventTillDown = true;
      stopScroll();
    }
  }
  
  public void setLayoutManager(LayoutManager paramLayoutManager)
  {
    if (paramLayoutManager == mLayout) {
      return;
    }
    stopScroll();
    Object localObject;
    if (mLayout != null)
    {
      localObject = mItemAnimator;
      if (localObject != null) {
        ((ItemAnimator)localObject).endAnimations();
      }
      mLayout.removeAndRecycleAllViews(mRecycler);
      mLayout.removeAndRecycleScrapInt(mRecycler);
      mRecycler.clear();
      if (mIsAttached) {
        mLayout.dispatchDetachedFromWindow(this, mRecycler);
      }
      mLayout.setRecyclerView(null);
      mLayout = null;
    }
    else
    {
      mRecycler.clear();
    }
    mChildHelper.removeAllViewsUnfiltered();
    mLayout = paramLayoutManager;
    if (paramLayoutManager != null) {
      if (mRecyclerView == null)
      {
        mLayout.setRecyclerView(this);
        if (mIsAttached) {
          mLayout.dispatchAttachedToWindow(this);
        }
      }
      else
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("LayoutManager ");
        ((StringBuilder)localObject).append(paramLayoutManager);
        ((StringBuilder)localObject).append(" is already attached to a RecyclerView:");
        ((StringBuilder)localObject).append(mRecyclerView.exceptionLabel());
        throw new IllegalArgumentException(((StringBuilder)localObject).toString());
      }
    }
    mRecycler.updateViewCacheSize();
    requestLayout();
  }
  
  public void setNestedScrollingEnabled(boolean paramBoolean)
  {
    getScrollingChildHelper().setNestedScrollingEnabled(paramBoolean);
  }
  
  public void setOnFlingListener(OnFlingListener paramOnFlingListener)
  {
    mOnFlingListener = paramOnFlingListener;
  }
  
  public void setOnScrollListener(OnScrollListener paramOnScrollListener)
  {
    mScrollListener = paramOnScrollListener;
  }
  
  public void setPreserveFocusAfterLayout(boolean paramBoolean)
  {
    mPreserveFocusAfterLayout = paramBoolean;
  }
  
  public void setRecycledViewPool(RecycledViewPool paramRecycledViewPool)
  {
    mRecycler.setRecycledViewPool(paramRecycledViewPool);
  }
  
  public void setRecyclerListener(RecyclerListener paramRecyclerListener)
  {
    mRecyclerListener = paramRecyclerListener;
  }
  
  void setScrollState(int paramInt)
  {
    if (paramInt == mScrollState) {
      return;
    }
    mScrollState = paramInt;
    if (paramInt != 2) {
      stopScrollersInternal();
    }
    dispatchOnScrollStateChanged(paramInt);
  }
  
  public void setScrollingTouchSlop(int paramInt)
  {
    ViewConfiguration localViewConfiguration = ViewConfiguration.get(getContext());
    if (paramInt != 0) {
      if (paramInt != 1)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("setScrollingTouchSlop(): bad argument constant ");
        localStringBuilder.append(paramInt);
        localStringBuilder.append("; using default value");
        Log.w("RecyclerView", localStringBuilder.toString());
      }
      else
      {
        mTouchSlop = localViewConfiguration.getScaledPagingTouchSlop();
        return;
      }
    }
    mTouchSlop = localViewConfiguration.getScaledTouchSlop();
  }
  
  public void setViewCacheExtension(ViewCacheExtension paramViewCacheExtension)
  {
    mRecycler.setViewCacheExtension(paramViewCacheExtension);
  }
  
  boolean shouldDeferAccessibilityEvent(AccessibilityEvent paramAccessibilityEvent)
  {
    if (isComputingLayout())
    {
      int i;
      if (paramAccessibilityEvent != null) {
        i = AccessibilityEventCompat.getContentChangeTypes(paramAccessibilityEvent);
      } else {
        i = 0;
      }
      int j = i;
      if (i == 0) {
        j = 0;
      }
      mEatenAccessibilityChangeFlags = (j | mEatenAccessibilityChangeFlags);
      return true;
    }
    return false;
  }
  
  public void smoothScrollBy(int paramInt1, int paramInt2)
  {
    smoothScrollBy(paramInt1, paramInt2, null);
  }
  
  public void smoothScrollBy(int paramInt1, int paramInt2, Interpolator paramInterpolator)
  {
    LayoutManager localLayoutManager = mLayout;
    if (localLayoutManager == null)
    {
      Log.e("RecyclerView", "Cannot smooth scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
      return;
    }
    if (mLayoutFrozen) {
      return;
    }
    if (!localLayoutManager.canScrollHorizontally()) {
      paramInt1 = 0;
    }
    if (!mLayout.canScrollVertically()) {
      paramInt2 = 0;
    }
    if ((paramInt1 != 0) || (paramInt2 != 0)) {
      mViewFlinger.smoothScrollBy(paramInt1, paramInt2, paramInterpolator);
    }
  }
  
  public void smoothScrollToPosition(int paramInt)
  {
    if (mLayoutFrozen) {
      return;
    }
    LayoutManager localLayoutManager = mLayout;
    if (localLayoutManager == null)
    {
      Log.e("RecyclerView", "Cannot smooth scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
      return;
    }
    localLayoutManager.smoothScrollToPosition(this, mState, paramInt);
  }
  
  void startInterceptRequestLayout()
  {
    mInterceptRequestLayoutDepth += 1;
    if ((mInterceptRequestLayoutDepth == 1) && (!mLayoutFrozen)) {
      mLayoutWasDefered = false;
    }
  }
  
  public boolean startNestedScroll(int paramInt)
  {
    return getScrollingChildHelper().startNestedScroll(paramInt);
  }
  
  public boolean startNestedScroll(int paramInt1, int paramInt2)
  {
    return getScrollingChildHelper().startNestedScroll(paramInt1, paramInt2);
  }
  
  void stopInterceptRequestLayout(boolean paramBoolean)
  {
    if (mInterceptRequestLayoutDepth < 1) {
      mInterceptRequestLayoutDepth = 1;
    }
    if ((!paramBoolean) && (!mLayoutFrozen)) {
      mLayoutWasDefered = false;
    }
    if (mInterceptRequestLayoutDepth == 1)
    {
      if ((paramBoolean) && (mLayoutWasDefered) && (!mLayoutFrozen) && (mLayout != null) && (mAdapter != null)) {
        dispatchLayout();
      }
      if (!mLayoutFrozen) {
        mLayoutWasDefered = false;
      }
    }
    mInterceptRequestLayoutDepth -= 1;
  }
  
  public void stopNestedScroll()
  {
    getScrollingChildHelper().stopNestedScroll();
  }
  
  public void stopNestedScroll(int paramInt)
  {
    getScrollingChildHelper().stopNestedScroll(paramInt);
  }
  
  public void stopScroll()
  {
    setScrollState(0);
    stopScrollersInternal();
  }
  
  public void swapAdapter(Adapter paramAdapter, boolean paramBoolean)
  {
    setLayoutFrozen(false);
    setAdapterInternal(paramAdapter, true, paramBoolean);
    processDataSetCompletelyChanged(true);
    requestLayout();
  }
  
  void viewRangeUpdate(int paramInt1, int paramInt2, Object paramObject)
  {
    int j = mChildHelper.getUnfilteredChildCount();
    int i = 0;
    while (i < j)
    {
      View localView = mChildHelper.getUnfilteredChildAt(i);
      ViewHolder localViewHolder = getChildViewHolderInt(localView);
      if ((localViewHolder != null) && (!localViewHolder.shouldIgnore()) && (mPosition >= paramInt1) && (mPosition < paramInt1 + paramInt2))
      {
        localViewHolder.addFlags(2);
        localViewHolder.addChangePayload(paramObject);
        getLayoutParamsmInsetsDirty = true;
      }
      i += 1;
    }
    mRecycler.viewRangeUpdate(paramInt1, paramInt2);
  }
  
  public static abstract class Adapter<VH extends RecyclerView.ViewHolder>
  {
    private boolean mHasStableIds = false;
    private final RecyclerView.AdapterDataObservable mObservable = new RecyclerView.AdapterDataObservable();
    
    public Adapter() {}
    
    public final void bindViewHolder(RecyclerView.ViewHolder paramViewHolder, int paramInt)
    {
      mPosition = paramInt;
      if (hasStableIds()) {
        mItemId = getItemId(paramInt);
      }
      paramViewHolder.setFlags(1, 519);
      TraceCompat.beginSection("RV OnBindView");
      onBindViewHolder(paramViewHolder, paramInt, paramViewHolder.getUnmodifiedPayloads());
      paramViewHolder.clearPayload();
      paramViewHolder = itemView.getLayoutParams();
      if ((paramViewHolder instanceof RecyclerView.LayoutParams)) {
        mInsetsDirty = true;
      }
      TraceCompat.endSection();
    }
    
    public final RecyclerView.ViewHolder createViewHolder(ViewGroup paramViewGroup, int paramInt)
    {
      try
      {
        TraceCompat.beginSection("RV CreateView");
        paramViewGroup = onCreateViewHolder(paramViewGroup, paramInt);
        ViewParent localViewParent = itemView.getParent();
        if (localViewParent == null)
        {
          mItemViewType = paramInt;
          TraceCompat.endSection();
          return paramViewGroup;
        }
        throw new IllegalStateException("ViewHolder views must not be attached when created. Ensure that you are not passing 'true' to the attachToRoot parameter of LayoutInflater.inflate(..., boolean attachToRoot)");
      }
      catch (Throwable paramViewGroup)
      {
        TraceCompat.endSection();
        throw paramViewGroup;
      }
    }
    
    public abstract int getItemCount();
    
    public long getItemId(int paramInt)
    {
      return -1L;
    }
    
    public int getItemViewType(int paramInt)
    {
      return 0;
    }
    
    public final boolean hasObservers()
    {
      return mObservable.hasObservers();
    }
    
    public final boolean hasStableIds()
    {
      return mHasStableIds;
    }
    
    public final void notifyDataSetChanged()
    {
      mObservable.notifyChanged();
    }
    
    public final void notifyItemChanged(int paramInt)
    {
      mObservable.notifyItemRangeChanged(paramInt, 1);
    }
    
    public final void notifyItemChanged(int paramInt, Object paramObject)
    {
      mObservable.notifyItemRangeChanged(paramInt, 1, paramObject);
    }
    
    public final void notifyItemInserted(int paramInt)
    {
      mObservable.notifyItemRangeInserted(paramInt, 1);
    }
    
    public final void notifyItemMoved(int paramInt1, int paramInt2)
    {
      mObservable.notifyItemMoved(paramInt1, paramInt2);
    }
    
    public final void notifyItemRangeChanged(int paramInt1, int paramInt2)
    {
      mObservable.notifyItemRangeChanged(paramInt1, paramInt2);
    }
    
    public final void notifyItemRangeChanged(int paramInt1, int paramInt2, Object paramObject)
    {
      mObservable.notifyItemRangeChanged(paramInt1, paramInt2, paramObject);
    }
    
    public final void notifyItemRangeInserted(int paramInt1, int paramInt2)
    {
      mObservable.notifyItemRangeInserted(paramInt1, paramInt2);
    }
    
    public final void notifyItemRangeRemoved(int paramInt1, int paramInt2)
    {
      mObservable.notifyItemRangeRemoved(paramInt1, paramInt2);
    }
    
    public final void notifyItemRemoved(int paramInt)
    {
      mObservable.notifyItemRangeRemoved(paramInt, 1);
    }
    
    public void onAttachedToRecyclerView(RecyclerView paramRecyclerView) {}
    
    public abstract void onBindViewHolder(RecyclerView.ViewHolder paramViewHolder, int paramInt);
    
    public void onBindViewHolder(RecyclerView.ViewHolder paramViewHolder, int paramInt, List paramList)
    {
      onBindViewHolder(paramViewHolder, paramInt);
    }
    
    public abstract RecyclerView.ViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt);
    
    public void onDetachedFromRecyclerView(RecyclerView paramRecyclerView) {}
    
    public boolean onFailedToRecycleView(RecyclerView.ViewHolder paramViewHolder)
    {
      return false;
    }
    
    public void onViewAttachedToWindow(RecyclerView.ViewHolder paramViewHolder) {}
    
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder paramViewHolder) {}
    
    public void onViewRecycled(RecyclerView.ViewHolder paramViewHolder) {}
    
    public void registerAdapterDataObserver(RecyclerView.AdapterDataObserver paramAdapterDataObserver)
    {
      mObservable.registerObserver(paramAdapterDataObserver);
    }
    
    public void setHasStableIds(boolean paramBoolean)
    {
      if (!hasObservers())
      {
        mHasStableIds = paramBoolean;
        return;
      }
      throw new IllegalStateException("Cannot change whether this adapter has stable IDs while the adapter has registered observers.");
    }
    
    public void unregisterAdapterDataObserver(RecyclerView.AdapterDataObserver paramAdapterDataObserver)
    {
      mObservable.unregisterObserver(paramAdapterDataObserver);
    }
  }
  
  static class AdapterDataObservable
    extends Observable<RecyclerView.AdapterDataObserver>
  {
    AdapterDataObservable() {}
    
    public boolean hasObservers()
    {
      return mObservers.isEmpty() ^ true;
    }
    
    public void notifyChanged()
    {
      int i = mObservers.size() - 1;
      while (i >= 0)
      {
        ((RecyclerView.AdapterDataObserver)mObservers.get(i)).onChanged();
        i -= 1;
      }
    }
    
    public void notifyItemMoved(int paramInt1, int paramInt2)
    {
      int i = mObservers.size() - 1;
      while (i >= 0)
      {
        ((RecyclerView.AdapterDataObserver)mObservers.get(i)).onItemRangeMoved(paramInt1, paramInt2, 1);
        i -= 1;
      }
    }
    
    public void notifyItemRangeChanged(int paramInt1, int paramInt2)
    {
      notifyItemRangeChanged(paramInt1, paramInt2, null);
    }
    
    public void notifyItemRangeChanged(int paramInt1, int paramInt2, Object paramObject)
    {
      int i = mObservers.size() - 1;
      while (i >= 0)
      {
        ((RecyclerView.AdapterDataObserver)mObservers.get(i)).onItemRangeChanged(paramInt1, paramInt2, paramObject);
        i -= 1;
      }
    }
    
    public void notifyItemRangeInserted(int paramInt1, int paramInt2)
    {
      int i = mObservers.size() - 1;
      while (i >= 0)
      {
        ((RecyclerView.AdapterDataObserver)mObservers.get(i)).onItemRangeInserted(paramInt1, paramInt2);
        i -= 1;
      }
    }
    
    public void notifyItemRangeRemoved(int paramInt1, int paramInt2)
    {
      int i = mObservers.size() - 1;
      while (i >= 0)
      {
        ((RecyclerView.AdapterDataObserver)mObservers.get(i)).onItemRangeRemoved(paramInt1, paramInt2);
        i -= 1;
      }
    }
  }
  
  public static abstract class AdapterDataObserver
  {
    public AdapterDataObserver() {}
    
    public void onChanged() {}
    
    public void onItemRangeChanged(int paramInt1, int paramInt2) {}
    
    public void onItemRangeChanged(int paramInt1, int paramInt2, Object paramObject)
    {
      onItemRangeChanged(paramInt1, paramInt2);
    }
    
    public void onItemRangeInserted(int paramInt1, int paramInt2) {}
    
    public void onItemRangeMoved(int paramInt1, int paramInt2, int paramInt3) {}
    
    public void onItemRangeRemoved(int paramInt1, int paramInt2) {}
  }
  
  public static abstract interface ChildDrawingOrderCallback
  {
    public abstract int onGetChildDrawingOrder(int paramInt1, int paramInt2);
  }
  
  public static class EdgeEffectFactory
  {
    public static final int DIRECTION_BOTTOM = 3;
    public static final int DIRECTION_LEFT = 0;
    public static final int DIRECTION_RIGHT = 2;
    public static final int DIRECTION_TOP = 1;
    
    public EdgeEffectFactory() {}
    
    protected EdgeEffect createEdgeEffect(RecyclerView paramRecyclerView, int paramInt)
    {
      return new EdgeEffect(paramRecyclerView.getContext());
    }
    
    @Retention(RetentionPolicy.SOURCE)
    public static @interface EdgeDirection {}
  }
  
  public static abstract class ItemAnimator
  {
    public static final int FLAG_APPEARED_IN_PRE_LAYOUT = 4096;
    public static final int FLAG_CHANGED = 2;
    public static final int FLAG_INVALIDATED = 4;
    public static final int FLAG_MOVED = 2048;
    public static final int FLAG_REMOVED = 8;
    private long mAddDuration = 120L;
    private long mChangeDuration = 250L;
    private ArrayList<ItemAnimatorFinishedListener> mFinishedListeners = new ArrayList();
    private ItemAnimatorListener mListener = null;
    private long mMoveDuration = 250L;
    private long mRemoveDuration = 120L;
    
    public ItemAnimator() {}
    
    static int buildAdapterChangeFlagsForAnimations(RecyclerView.ViewHolder paramViewHolder)
    {
      int j = mFlags & 0xE;
      if (paramViewHolder.isInvalid()) {
        return 4;
      }
      int i = j;
      if ((j & 0x4) == 0)
      {
        int k = paramViewHolder.getOldPosition();
        int m = paramViewHolder.getAdapterPosition();
        i = j;
        if (k != -1)
        {
          i = j;
          if (m != -1)
          {
            i = j;
            if (k != m) {
              i = j | 0x800;
            }
          }
        }
      }
      return i;
    }
    
    public abstract boolean animateAppearance(RecyclerView.ViewHolder paramViewHolder, ItemHolderInfo paramItemHolderInfo1, ItemHolderInfo paramItemHolderInfo2);
    
    public abstract boolean animateChange(RecyclerView.ViewHolder paramViewHolder1, RecyclerView.ViewHolder paramViewHolder2, ItemHolderInfo paramItemHolderInfo1, ItemHolderInfo paramItemHolderInfo2);
    
    public abstract boolean animateDisappearance(RecyclerView.ViewHolder paramViewHolder, ItemHolderInfo paramItemHolderInfo1, ItemHolderInfo paramItemHolderInfo2);
    
    public abstract boolean animatePersistence(RecyclerView.ViewHolder paramViewHolder, ItemHolderInfo paramItemHolderInfo1, ItemHolderInfo paramItemHolderInfo2);
    
    public boolean canReuseUpdatedViewHolder(RecyclerView.ViewHolder paramViewHolder)
    {
      return true;
    }
    
    public boolean canReuseUpdatedViewHolder(RecyclerView.ViewHolder paramViewHolder, List paramList)
    {
      return canReuseUpdatedViewHolder(paramViewHolder);
    }
    
    public final void dispatchAnimationFinished(RecyclerView.ViewHolder paramViewHolder)
    {
      onAnimationFinished(paramViewHolder);
      ItemAnimatorListener localItemAnimatorListener = mListener;
      if (localItemAnimatorListener != null) {
        localItemAnimatorListener.onAnimationFinished(paramViewHolder);
      }
    }
    
    public final void dispatchAnimationStarted(RecyclerView.ViewHolder paramViewHolder)
    {
      onAnimationStarted(paramViewHolder);
    }
    
    public final void dispatchAnimationsFinished()
    {
      int j = mFinishedListeners.size();
      int i = 0;
      while (i < j)
      {
        ((ItemAnimatorFinishedListener)mFinishedListeners.get(i)).onAnimationsFinished();
        i += 1;
      }
      mFinishedListeners.clear();
    }
    
    public abstract void endAnimation(RecyclerView.ViewHolder paramViewHolder);
    
    public abstract void endAnimations();
    
    public long getAddDuration()
    {
      return mAddDuration;
    }
    
    public long getChangeDuration()
    {
      return mChangeDuration;
    }
    
    public long getMoveDuration()
    {
      return mMoveDuration;
    }
    
    public long getRemoveDuration()
    {
      return mRemoveDuration;
    }
    
    public abstract boolean isRunning();
    
    public final boolean isRunning(ItemAnimatorFinishedListener paramItemAnimatorFinishedListener)
    {
      boolean bool = isRunning();
      if (paramItemAnimatorFinishedListener != null)
      {
        if (!bool)
        {
          paramItemAnimatorFinishedListener.onAnimationsFinished();
          return bool;
        }
        mFinishedListeners.add(paramItemAnimatorFinishedListener);
      }
      return bool;
    }
    
    public ItemHolderInfo obtainHolderInfo()
    {
      return new ItemHolderInfo();
    }
    
    public void onAnimationFinished(RecyclerView.ViewHolder paramViewHolder) {}
    
    public void onAnimationStarted(RecyclerView.ViewHolder paramViewHolder) {}
    
    public ItemHolderInfo recordPostLayoutInformation(RecyclerView.State paramState, RecyclerView.ViewHolder paramViewHolder)
    {
      return obtainHolderInfo().setFrom(paramViewHolder);
    }
    
    public ItemHolderInfo recordPreLayoutInformation(RecyclerView.State paramState, RecyclerView.ViewHolder paramViewHolder, int paramInt, List paramList)
    {
      return obtainHolderInfo().setFrom(paramViewHolder);
    }
    
    public abstract void runPendingAnimations();
    
    public void setAddDuration(long paramLong)
    {
      mAddDuration = paramLong;
    }
    
    public void setChangeDuration(long paramLong)
    {
      mChangeDuration = paramLong;
    }
    
    void setListener(ItemAnimatorListener paramItemAnimatorListener)
    {
      mListener = paramItemAnimatorListener;
    }
    
    public void setMoveDuration(long paramLong)
    {
      mMoveDuration = paramLong;
    }
    
    public void setRemoveDuration(long paramLong)
    {
      mRemoveDuration = paramLong;
    }
    
    @Retention(RetentionPolicy.SOURCE)
    public static @interface AdapterChanges {}
    
    public static abstract interface ItemAnimatorFinishedListener
    {
      public abstract void onAnimationsFinished();
    }
    
    static abstract interface ItemAnimatorListener
    {
      public abstract void onAnimationFinished(RecyclerView.ViewHolder paramViewHolder);
    }
    
    public static class ItemHolderInfo
    {
      public int bottom;
      public int changeFlags;
      public int left;
      public int right;
      public int top;
      
      public ItemHolderInfo() {}
      
      public ItemHolderInfo setFrom(RecyclerView.ViewHolder paramViewHolder)
      {
        return setFrom(paramViewHolder, 0);
      }
      
      public ItemHolderInfo setFrom(RecyclerView.ViewHolder paramViewHolder, int paramInt)
      {
        paramViewHolder = itemView;
        left = paramViewHolder.getLeft();
        top = paramViewHolder.getTop();
        right = paramViewHolder.getRight();
        bottom = paramViewHolder.getBottom();
        return this;
      }
    }
  }
  
  private class ItemAnimatorRestoreListener
    implements RecyclerView.ItemAnimator.ItemAnimatorListener
  {
    ItemAnimatorRestoreListener() {}
    
    public void onAnimationFinished(RecyclerView.ViewHolder paramViewHolder)
    {
      paramViewHolder.setIsRecyclable(true);
      if ((mShadowedHolder != null) && (mShadowingHolder == null)) {
        mShadowedHolder = null;
      }
      mShadowingHolder = null;
      if ((!paramViewHolder.shouldBeKeptAsChild()) && (!removeAnimatingView(itemView)) && (paramViewHolder.isTmpDetached())) {
        removeDetachedView(itemView, false);
      }
    }
  }
  
  public static abstract class ItemDecoration
  {
    public ItemDecoration() {}
    
    public void getItemOffsets(Rect paramRect, int paramInt, RecyclerView paramRecyclerView)
    {
      paramRect.set(0, 0, 0, 0);
    }
    
    public void getItemOffsets(Rect paramRect, View paramView, RecyclerView paramRecyclerView, RecyclerView.State paramState)
    {
      getItemOffsets(paramRect, ((RecyclerView.LayoutParams)paramView.getLayoutParams()).getViewLayoutPosition(), paramRecyclerView);
    }
    
    public void onDraw(Canvas paramCanvas, RecyclerView paramRecyclerView) {}
    
    public void onDraw(Canvas paramCanvas, RecyclerView paramRecyclerView, RecyclerView.State paramState)
    {
      onDraw(paramCanvas, paramRecyclerView);
    }
    
    public void onDrawOver(Canvas paramCanvas, RecyclerView paramRecyclerView) {}
    
    public void onDrawOver(Canvas paramCanvas, RecyclerView paramRecyclerView, RecyclerView.State paramState)
    {
      onDrawOver(paramCanvas, paramRecyclerView);
    }
  }
  
  public static abstract class LayoutManager
  {
    boolean mAutoMeasure = false;
    ChildHelper mChildHelper;
    private int mHeight;
    private int mHeightMode;
    ViewBoundsCheck mHorizontalBoundCheck = new ViewBoundsCheck(mHorizontalBoundCheckCallback);
    private final ViewBoundsCheck.Callback mHorizontalBoundCheckCallback = new ViewBoundsCheck.Callback()
    {
      public View getChildAt(int paramAnonymousInt)
      {
        return RecyclerView.LayoutManager.this.getChildAt(paramAnonymousInt);
      }
      
      public int getChildCount()
      {
        return RecyclerView.LayoutManager.this.getChildCount();
      }
      
      public int getChildEnd(View paramAnonymousView)
      {
        RecyclerView.LayoutParams localLayoutParams = (RecyclerView.LayoutParams)paramAnonymousView.getLayoutParams();
        return getDecoratedRight(paramAnonymousView) + rightMargin;
      }
      
      public int getChildStart(View paramAnonymousView)
      {
        RecyclerView.LayoutParams localLayoutParams = (RecyclerView.LayoutParams)paramAnonymousView.getLayoutParams();
        return getDecoratedLeft(paramAnonymousView) - leftMargin;
      }
      
      public View getParent()
      {
        return mRecyclerView;
      }
      
      public int getParentEnd()
      {
        return getWidth() - getPaddingRight();
      }
      
      public int getParentStart()
      {
        return getPaddingLeft();
      }
    };
    boolean mIsAttachedToWindow = false;
    private boolean mItemPrefetchEnabled = true;
    private boolean mMeasurementCacheEnabled = true;
    int mPrefetchMaxCountObserved;
    boolean mPrefetchMaxObservedInInitialPrefetch;
    RecyclerView mRecyclerView;
    boolean mRequestedSimpleAnimations = false;
    RecyclerView.SmoothScroller mSmoothScroller;
    ViewBoundsCheck mVerticalBoundCheck = new ViewBoundsCheck(mVerticalBoundCheckCallback);
    private final ViewBoundsCheck.Callback mVerticalBoundCheckCallback = new ViewBoundsCheck.Callback()
    {
      public View getChildAt(int paramAnonymousInt)
      {
        return RecyclerView.LayoutManager.this.getChildAt(paramAnonymousInt);
      }
      
      public int getChildCount()
      {
        return RecyclerView.LayoutManager.this.getChildCount();
      }
      
      public int getChildEnd(View paramAnonymousView)
      {
        RecyclerView.LayoutParams localLayoutParams = (RecyclerView.LayoutParams)paramAnonymousView.getLayoutParams();
        return getDecoratedBottom(paramAnonymousView) + bottomMargin;
      }
      
      public int getChildStart(View paramAnonymousView)
      {
        RecyclerView.LayoutParams localLayoutParams = (RecyclerView.LayoutParams)paramAnonymousView.getLayoutParams();
        return getDecoratedTop(paramAnonymousView) - topMargin;
      }
      
      public View getParent()
      {
        return mRecyclerView;
      }
      
      public int getParentEnd()
      {
        return getHeight() - getPaddingBottom();
      }
      
      public int getParentStart()
      {
        return getPaddingTop();
      }
    };
    private int mWidth;
    private int mWidthMode;
    
    public LayoutManager() {}
    
    private void addViewInt(View paramView, int paramInt, boolean paramBoolean)
    {
      Object localObject = RecyclerView.getChildViewHolderInt(paramView);
      if ((!paramBoolean) && (!((RecyclerView.ViewHolder)localObject).isRemoved())) {
        mRecyclerView.mViewInfoStore.removeFromDisappearedInLayout((RecyclerView.ViewHolder)localObject);
      } else {
        mRecyclerView.mViewInfoStore.addToDisappearedInLayout((RecyclerView.ViewHolder)localObject);
      }
      RecyclerView.LayoutParams localLayoutParams = (RecyclerView.LayoutParams)paramView.getLayoutParams();
      if ((!((RecyclerView.ViewHolder)localObject).wasReturnedFromScrap()) && (!((RecyclerView.ViewHolder)localObject).isScrap()))
      {
        if (paramView.getParent() == mRecyclerView)
        {
          int j = mChildHelper.indexOfChild(paramView);
          int i = paramInt;
          if (paramInt == -1) {
            i = mChildHelper.getChildCount();
          }
          if (j != -1)
          {
            if (j != i) {
              mRecyclerView.mLayout.moveView(j, i);
            }
          }
          else
          {
            localObject = new StringBuilder();
            ((StringBuilder)localObject).append("Added View has RecyclerView as parent but view is not a real child. Unfiltered index:");
            ((StringBuilder)localObject).append(mRecyclerView.indexOfChild(paramView));
            ((StringBuilder)localObject).append(mRecyclerView.exceptionLabel());
            throw new IllegalStateException(((StringBuilder)localObject).toString());
          }
        }
        else
        {
          mChildHelper.addView(paramView, paramInt, false);
          mInsetsDirty = true;
          RecyclerView.SmoothScroller localSmoothScroller = mSmoothScroller;
          if ((localSmoothScroller != null) && (localSmoothScroller.isRunning())) {
            mSmoothScroller.onChildAttachedToWindow(paramView);
          }
        }
      }
      else
      {
        if (((RecyclerView.ViewHolder)localObject).isScrap()) {
          ((RecyclerView.ViewHolder)localObject).unScrap();
        } else {
          ((RecyclerView.ViewHolder)localObject).clearReturnedFromScrapFlag();
        }
        mChildHelper.attachViewToParent(paramView, paramInt, paramView.getLayoutParams(), false);
      }
      if (mPendingInvalidate)
      {
        itemView.invalidate();
        mPendingInvalidate = false;
      }
    }
    
    public static int chooseSize(int paramInt1, int paramInt2, int paramInt3)
    {
      int i = View.MeasureSpec.getMode(paramInt1);
      paramInt1 = View.MeasureSpec.getSize(paramInt1);
      if (i != Integer.MIN_VALUE)
      {
        if (i != 1073741824) {
          return Math.max(paramInt2, paramInt3);
        }
      }
      else {
        paramInt1 = Math.min(paramInt1, Math.max(paramInt2, paramInt3));
      }
      return paramInt1;
    }
    
    private void detachViewInternal(int paramInt, View paramView)
    {
      mChildHelper.detachViewFromParent(paramInt);
    }
    
    public static int getChildMeasureSpec(int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean)
    {
      int k = 0;
      int j = Math.max(0, paramInt1 - paramInt3);
      int i = j;
      if (paramBoolean)
      {
        if (paramInt4 < 0)
        {
          if (paramInt4 != -1) {
            break label127;
          }
          paramInt1 = paramInt2;
          paramInt3 = j;
          if (paramInt2 != Integer.MIN_VALUE) {
            if (paramInt2 != 0)
            {
              paramInt1 = paramInt2;
              paramInt3 = j;
              if (paramInt2 == 1073741824) {}
            }
            else
            {
              paramInt1 = 0;
              paramInt3 = 0;
            }
          }
          break label132;
        }
      }
      else {
        if (paramInt4 < 0) {
          break label81;
        }
      }
      paramInt3 = paramInt4;
      paramInt1 = 1073741824;
      break label132;
      label81:
      if (paramInt4 == -1)
      {
        paramInt1 = paramInt2;
        paramInt3 = i;
      }
      else if (paramInt4 == -2)
      {
        if (paramInt2 != Integer.MIN_VALUE)
        {
          paramInt3 = i;
          paramInt1 = k;
          if (paramInt2 != 1073741824) {}
        }
        else
        {
          paramInt1 = Integer.MIN_VALUE;
          paramInt3 = i;
        }
      }
      else
      {
        label127:
        paramInt3 = 0;
        paramInt1 = k;
      }
      label132:
      return View.MeasureSpec.makeMeasureSpec(paramInt3, paramInt1);
    }
    
    public static int getChildMeasureSpec(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
    {
      int i = 0;
      paramInt2 = Math.max(0, paramInt1 - paramInt2);
      if (paramBoolean) {
        if (paramInt3 >= 0) {
          break label34;
        }
      }
      label34:
      do
      {
        paramInt2 = 0;
        paramInt1 = i;
        break;
        if (paramInt3 >= 0) {
          paramInt2 = paramInt3;
        }
        while (paramInt3 == -1)
        {
          paramInt1 = 1073741824;
          break;
        }
      } while (paramInt3 != -2);
      paramInt1 = Integer.MIN_VALUE;
      return View.MeasureSpec.makeMeasureSpec(paramInt2, paramInt1);
    }
    
    private int[] getChildRectangleOnScreenScrollAmount(RecyclerView paramRecyclerView, View paramView, Rect paramRect, boolean paramBoolean)
    {
      int i = getPaddingLeft();
      int j = getPaddingTop();
      int k = getWidth();
      int i1 = getPaddingRight();
      int i3 = getHeight();
      int i4 = getPaddingBottom();
      int i8 = paramView.getLeft() + left - paramView.getScrollX();
      int i5 = paramView.getTop() + top - paramView.getScrollY();
      int i9 = paramRect.width();
      int i6 = paramRect.height();
      int i7 = i8 - i;
      int n = Math.min(0, i7);
      i = n;
      int i2 = i5 - j;
      int m = Math.min(0, i2);
      j = m;
      i8 = i9 + i8 - (k - i1);
      i1 = Math.max(0, i8);
      k = i1;
      i3 = Math.max(0, i6 + i5 - (i3 - i4));
      if (getLayoutDirection() == 1)
      {
        if (i1 != 0) {
          i = k;
        } else {
          i = Math.max(n, i8);
        }
      }
      else if (n == 0) {
        i = Math.min(i7, i1);
      }
      if (m == 0) {
        j = Math.min(i2, i3);
      }
      return new int[] { i, j };
    }
    
    public static Properties getProperties(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
    {
      Properties localProperties = new Properties();
      paramContext = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.RecyclerView, paramInt1, paramInt2);
      orientation = paramContext.getInt(R.styleable.RecyclerView_android_orientation, 1);
      spanCount = paramContext.getInt(R.styleable.RecyclerView_spanCount, 1);
      reverseLayout = paramContext.getBoolean(R.styleable.RecyclerView_reverseLayout, false);
      stackFromEnd = paramContext.getBoolean(R.styleable.RecyclerView_stackFromEnd, false);
      paramContext.recycle();
      return localProperties;
    }
    
    private boolean isFocusedChildVisibleAfterScrolling(RecyclerView paramRecyclerView, int paramInt1, int paramInt2)
    {
      paramRecyclerView = paramRecyclerView.getFocusedChild();
      if (paramRecyclerView == null) {
        return false;
      }
      int i = getPaddingLeft();
      int j = getPaddingTop();
      int k = getWidth();
      int m = getPaddingRight();
      int n = getHeight();
      int i1 = getPaddingBottom();
      Rect localRect = mRecyclerView.mTempRect;
      getDecoratedBoundsWithMargins(paramRecyclerView, localRect);
      if ((left - paramInt1 < k - m) && (right - paramInt1 > i) && (top - paramInt2 < n - i1)) {
        return bottom - paramInt2 > j;
      }
      return false;
    }
    
    private static boolean isMeasurementUpToDate(int paramInt1, int paramInt2, int paramInt3)
    {
      int i = View.MeasureSpec.getMode(paramInt2);
      paramInt2 = View.MeasureSpec.getSize(paramInt2);
      if ((paramInt3 > 0) && (paramInt1 != paramInt3)) {
        return false;
      }
      if (i != Integer.MIN_VALUE)
      {
        if (i != 0)
        {
          if (i != 1073741824) {
            return false;
          }
          if (paramInt2 == paramInt1) {
            return true;
          }
        }
        else
        {
          return true;
        }
      }
      else if (paramInt2 >= paramInt1) {
        return true;
      }
      return false;
    }
    
    private void scrapOrRecycleView(RecyclerView.Recycler paramRecycler, int paramInt, View paramView)
    {
      RecyclerView.ViewHolder localViewHolder = RecyclerView.getChildViewHolderInt(paramView);
      if (localViewHolder.shouldIgnore()) {
        return;
      }
      if ((localViewHolder.isInvalid()) && (!localViewHolder.isRemoved()) && (!mRecyclerView.mAdapter.hasStableIds()))
      {
        removeViewAt(paramInt);
        paramRecycler.recycleViewHolderInternal(localViewHolder);
        return;
      }
      detachViewAt(paramInt);
      paramRecycler.scrapView(paramView);
      mRecyclerView.mViewInfoStore.onViewDetached(localViewHolder);
    }
    
    public void addDisappearingView(View paramView)
    {
      addDisappearingView(paramView, -1);
    }
    
    public void addDisappearingView(View paramView, int paramInt)
    {
      addViewInt(paramView, paramInt, true);
    }
    
    public void addView(View paramView)
    {
      addView(paramView, -1);
    }
    
    public void addView(View paramView, int paramInt)
    {
      addViewInt(paramView, paramInt, false);
    }
    
    public void assertInLayoutOrScroll(String paramString)
    {
      RecyclerView localRecyclerView = mRecyclerView;
      if (localRecyclerView != null) {
        localRecyclerView.assertInLayoutOrScroll(paramString);
      }
    }
    
    public void assertNotInLayoutOrScroll(String paramString)
    {
      RecyclerView localRecyclerView = mRecyclerView;
      if (localRecyclerView != null) {
        localRecyclerView.assertNotInLayoutOrScroll(paramString);
      }
    }
    
    public void attachView(View paramView)
    {
      attachView(paramView, -1);
    }
    
    public void attachView(View paramView, int paramInt)
    {
      attachView(paramView, paramInt, (RecyclerView.LayoutParams)paramView.getLayoutParams());
    }
    
    public void attachView(View paramView, int paramInt, RecyclerView.LayoutParams paramLayoutParams)
    {
      RecyclerView.ViewHolder localViewHolder = RecyclerView.getChildViewHolderInt(paramView);
      if (localViewHolder.isRemoved()) {
        mRecyclerView.mViewInfoStore.addToDisappearedInLayout(localViewHolder);
      } else {
        mRecyclerView.mViewInfoStore.removeFromDisappearedInLayout(localViewHolder);
      }
      mChildHelper.attachViewToParent(paramView, paramInt, paramLayoutParams, localViewHolder.isRemoved());
    }
    
    public void calculateItemDecorationsForChild(View paramView, Rect paramRect)
    {
      RecyclerView localRecyclerView = mRecyclerView;
      if (localRecyclerView == null)
      {
        paramRect.set(0, 0, 0, 0);
        return;
      }
      paramRect.set(localRecyclerView.getItemDecorInsetsForChild(paramView));
    }
    
    public boolean canScrollHorizontally()
    {
      return false;
    }
    
    public boolean canScrollVertically()
    {
      return false;
    }
    
    public boolean checkLayoutParams(RecyclerView.LayoutParams paramLayoutParams)
    {
      return paramLayoutParams != null;
    }
    
    public void collectAdjacentPrefetchPositions(int paramInt1, int paramInt2, RecyclerView.State paramState, LayoutPrefetchRegistry paramLayoutPrefetchRegistry) {}
    
    public void collectInitialPrefetchPositions(int paramInt, LayoutPrefetchRegistry paramLayoutPrefetchRegistry) {}
    
    public int computeHorizontalScrollExtent(RecyclerView.State paramState)
    {
      return 0;
    }
    
    public int computeHorizontalScrollOffset(RecyclerView.State paramState)
    {
      return 0;
    }
    
    public int computeHorizontalScrollRange(RecyclerView.State paramState)
    {
      return 0;
    }
    
    public int computeVerticalScrollExtent(RecyclerView.State paramState)
    {
      return 0;
    }
    
    public int computeVerticalScrollOffset(RecyclerView.State paramState)
    {
      return 0;
    }
    
    public int computeVerticalScrollRange(RecyclerView.State paramState)
    {
      return 0;
    }
    
    public void detachAndScrapAttachedViews(RecyclerView.Recycler paramRecycler)
    {
      int i = getChildCount() - 1;
      while (i >= 0)
      {
        scrapOrRecycleView(paramRecycler, i, getChildAt(i));
        i -= 1;
      }
    }
    
    public void detachAndScrapView(View paramView, RecyclerView.Recycler paramRecycler)
    {
      scrapOrRecycleView(paramRecycler, mChildHelper.indexOfChild(paramView), paramView);
    }
    
    public void detachAndScrapViewAt(int paramInt, RecyclerView.Recycler paramRecycler)
    {
      scrapOrRecycleView(paramRecycler, paramInt, getChildAt(paramInt));
    }
    
    public void detachView(View paramView)
    {
      int i = mChildHelper.indexOfChild(paramView);
      if (i >= 0) {
        detachViewInternal(i, paramView);
      }
    }
    
    public void detachViewAt(int paramInt)
    {
      detachViewInternal(paramInt, getChildAt(paramInt));
    }
    
    void dispatchAttachedToWindow(RecyclerView paramRecyclerView)
    {
      mIsAttachedToWindow = true;
      onAttachedToWindow(paramRecyclerView);
    }
    
    void dispatchDetachedFromWindow(RecyclerView paramRecyclerView, RecyclerView.Recycler paramRecycler)
    {
      mIsAttachedToWindow = false;
      onDetachedFromWindow(paramRecyclerView, paramRecycler);
    }
    
    public void endAnimation(View paramView)
    {
      if (mRecyclerView.mItemAnimator != null) {
        mRecyclerView.mItemAnimator.endAnimation(RecyclerView.getChildViewHolderInt(paramView));
      }
    }
    
    public View findContainingItemView(View paramView)
    {
      RecyclerView localRecyclerView = mRecyclerView;
      if (localRecyclerView == null) {
        return null;
      }
      paramView = localRecyclerView.findContainingItemView(paramView);
      if (paramView == null) {
        return null;
      }
      if (mChildHelper.isHidden(paramView)) {
        return null;
      }
      return paramView;
    }
    
    public View findViewByPosition(int paramInt)
    {
      int j = getChildCount();
      int i = 0;
      View localView;
      while (i < j)
      {
        localView = getChildAt(i);
        RecyclerView.ViewHolder localViewHolder = RecyclerView.getChildViewHolderInt(localView);
        if ((localViewHolder != null) && (localViewHolder.getLayoutPosition() == paramInt) && (!localViewHolder.shouldIgnore()))
        {
          if (mRecyclerView.mState.isPreLayout()) {
            break label84;
          }
          if (!localViewHolder.isRemoved()) {
            return localView;
          }
        }
        i += 1;
      }
      return null;
      label84:
      return localView;
    }
    
    public abstract RecyclerView.LayoutParams generateDefaultLayoutParams();
    
    public RecyclerView.LayoutParams generateLayoutParams(Context paramContext, AttributeSet paramAttributeSet)
    {
      return new RecyclerView.LayoutParams(paramContext, paramAttributeSet);
    }
    
    public RecyclerView.LayoutParams generateLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
    {
      if ((paramLayoutParams instanceof RecyclerView.LayoutParams)) {
        return new RecyclerView.LayoutParams((RecyclerView.LayoutParams)paramLayoutParams);
      }
      if ((paramLayoutParams instanceof ViewGroup.MarginLayoutParams)) {
        return new RecyclerView.LayoutParams((ViewGroup.MarginLayoutParams)paramLayoutParams);
      }
      return new RecyclerView.LayoutParams(paramLayoutParams);
    }
    
    public int getBaseline()
    {
      return -1;
    }
    
    public int getBottomDecorationHeight(View paramView)
    {
      return getLayoutParamsmDecorInsets.bottom;
    }
    
    public View getChildAt(int paramInt)
    {
      ChildHelper localChildHelper = mChildHelper;
      if (localChildHelper != null) {
        return localChildHelper.getChildAt(paramInt);
      }
      return null;
    }
    
    public int getChildCount()
    {
      ChildHelper localChildHelper = mChildHelper;
      if (localChildHelper != null) {
        return localChildHelper.getChildCount();
      }
      return 0;
    }
    
    public boolean getClipToPadding()
    {
      RecyclerView localRecyclerView = mRecyclerView;
      return (localRecyclerView != null) && (mClipToPadding);
    }
    
    public int getColumnCountForAccessibility(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
    {
      paramRecycler = mRecyclerView;
      if (paramRecycler != null)
      {
        if (mAdapter == null) {
          return 1;
        }
        if (canScrollHorizontally()) {
          return mRecyclerView.mAdapter.getItemCount();
        }
      }
      return 1;
    }
    
    public int getDecoratedBottom(View paramView)
    {
      return paramView.getBottom() + getBottomDecorationHeight(paramView);
    }
    
    public void getDecoratedBoundsWithMargins(View paramView, Rect paramRect)
    {
      RecyclerView.getDecoratedBoundsWithMarginsInt(paramView, paramRect);
    }
    
    public int getDecoratedLeft(View paramView)
    {
      return paramView.getLeft() - getLeftDecorationWidth(paramView);
    }
    
    public int getDecoratedMeasuredHeight(View paramView)
    {
      Rect localRect = getLayoutParamsmDecorInsets;
      return paramView.getMeasuredHeight() + top + bottom;
    }
    
    public int getDecoratedMeasuredWidth(View paramView)
    {
      Rect localRect = getLayoutParamsmDecorInsets;
      return paramView.getMeasuredWidth() + left + right;
    }
    
    public int getDecoratedRight(View paramView)
    {
      return paramView.getRight() + getRightDecorationWidth(paramView);
    }
    
    public int getDecoratedTop(View paramView)
    {
      return paramView.getTop() - getTopDecorationHeight(paramView);
    }
    
    public View getFocusedChild()
    {
      Object localObject = mRecyclerView;
      if (localObject == null) {
        return null;
      }
      localObject = ((ViewGroup)localObject).getFocusedChild();
      if (localObject != null)
      {
        if (mChildHelper.isHidden((View)localObject)) {
          return null;
        }
        return localObject;
      }
      return null;
    }
    
    public int getHeight()
    {
      return mHeight;
    }
    
    public int getHeightMode()
    {
      return mHeightMode;
    }
    
    public int getItemCount()
    {
      Object localObject = mRecyclerView;
      if (localObject != null) {
        localObject = ((RecyclerView)localObject).getAdapter();
      } else {
        localObject = null;
      }
      if (localObject != null) {
        return ((RecyclerView.Adapter)localObject).getItemCount();
      }
      return 0;
    }
    
    public int getItemViewType(View paramView)
    {
      return RecyclerView.getChildViewHolderInt(paramView).getItemViewType();
    }
    
    public int getLayoutDirection()
    {
      return ViewCompat.getLayoutDirection(mRecyclerView);
    }
    
    public int getLeftDecorationWidth(View paramView)
    {
      return getLayoutParamsmDecorInsets.left;
    }
    
    public int getMinimumHeight()
    {
      return ViewCompat.getMinimumHeight(mRecyclerView);
    }
    
    public int getMinimumWidth()
    {
      return ViewCompat.getMinimumWidth(mRecyclerView);
    }
    
    public int getPaddingBottom()
    {
      RecyclerView localRecyclerView = mRecyclerView;
      if (localRecyclerView != null) {
        return localRecyclerView.getPaddingBottom();
      }
      return 0;
    }
    
    public int getPaddingEnd()
    {
      RecyclerView localRecyclerView = mRecyclerView;
      if (localRecyclerView != null) {
        return ViewCompat.getPaddingEnd(localRecyclerView);
      }
      return 0;
    }
    
    public int getPaddingLeft()
    {
      RecyclerView localRecyclerView = mRecyclerView;
      if (localRecyclerView != null) {
        return localRecyclerView.getPaddingLeft();
      }
      return 0;
    }
    
    public int getPaddingRight()
    {
      RecyclerView localRecyclerView = mRecyclerView;
      if (localRecyclerView != null) {
        return localRecyclerView.getPaddingRight();
      }
      return 0;
    }
    
    public int getPaddingStart()
    {
      RecyclerView localRecyclerView = mRecyclerView;
      if (localRecyclerView != null) {
        return ViewCompat.getPaddingStart(localRecyclerView);
      }
      return 0;
    }
    
    public int getPaddingTop()
    {
      RecyclerView localRecyclerView = mRecyclerView;
      if (localRecyclerView != null) {
        return localRecyclerView.getPaddingTop();
      }
      return 0;
    }
    
    public int getPosition(View paramView)
    {
      return ((RecyclerView.LayoutParams)paramView.getLayoutParams()).getViewLayoutPosition();
    }
    
    public int getRightDecorationWidth(View paramView)
    {
      return getLayoutParamsmDecorInsets.right;
    }
    
    public int getRowCountForAccessibility(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
    {
      paramRecycler = mRecyclerView;
      if (paramRecycler != null)
      {
        if (mAdapter == null) {
          return 1;
        }
        if (canScrollVertically()) {
          return mRecyclerView.mAdapter.getItemCount();
        }
      }
      return 1;
    }
    
    public int getSelectionModeForAccessibility(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
    {
      return 0;
    }
    
    public int getTopDecorationHeight(View paramView)
    {
      return getLayoutParamsmDecorInsets.top;
    }
    
    public void getTransformedBoundingBox(View paramView, boolean paramBoolean, Rect paramRect)
    {
      Object localObject;
      if (paramBoolean)
      {
        localObject = getLayoutParamsmDecorInsets;
        paramRect.set(-left, -top, paramView.getWidth() + right, paramView.getHeight() + bottom);
      }
      else
      {
        paramRect.set(0, 0, paramView.getWidth(), paramView.getHeight());
      }
      if (mRecyclerView != null)
      {
        localObject = paramView.getMatrix();
        if ((localObject != null) && (!((Matrix)localObject).isIdentity()))
        {
          RectF localRectF = mRecyclerView.mTempRectF;
          localRectF.set(paramRect);
          ((Matrix)localObject).mapRect(localRectF);
          paramRect.set((int)Math.floor(left), (int)Math.floor(top), (int)Math.ceil(right), (int)Math.ceil(bottom));
        }
      }
      paramRect.offset(paramView.getLeft(), paramView.getTop());
    }
    
    public int getWidth()
    {
      return mWidth;
    }
    
    public int getWidthMode()
    {
      return mWidthMode;
    }
    
    boolean hasFlexibleChildInBothOrientations()
    {
      int j = getChildCount();
      int i = 0;
      while (i < j)
      {
        ViewGroup.LayoutParams localLayoutParams = getChildAt(i).getLayoutParams();
        if ((width < 0) && (height < 0)) {
          return true;
        }
        i += 1;
      }
      return false;
    }
    
    public boolean hasFocus()
    {
      RecyclerView localRecyclerView = mRecyclerView;
      return (localRecyclerView != null) && (localRecyclerView.hasFocus());
    }
    
    public void ignoreView(View paramView)
    {
      ViewParent localViewParent = paramView.getParent();
      RecyclerView localRecyclerView = mRecyclerView;
      if ((localViewParent == localRecyclerView) && (localRecyclerView.indexOfChild(paramView) != -1))
      {
        paramView = RecyclerView.getChildViewHolderInt(paramView);
        paramView.addFlags(128);
        mRecyclerView.mViewInfoStore.removeViewHolder(paramView);
        return;
      }
      paramView = new StringBuilder();
      paramView.append("View should be fully attached to be ignored");
      paramView.append(mRecyclerView.exceptionLabel());
      throw new IllegalArgumentException(paramView.toString());
    }
    
    public boolean isAttachedToWindow()
    {
      return mIsAttachedToWindow;
    }
    
    public boolean isAutoMeasureEnabled()
    {
      return mAutoMeasure;
    }
    
    public boolean isFocused()
    {
      RecyclerView localRecyclerView = mRecyclerView;
      return (localRecyclerView != null) && (localRecyclerView.isFocused());
    }
    
    public final boolean isItemPrefetchEnabled()
    {
      return mItemPrefetchEnabled;
    }
    
    public boolean isLayoutHierarchical(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
    {
      return false;
    }
    
    public boolean isMeasurementCacheEnabled()
    {
      return mMeasurementCacheEnabled;
    }
    
    public boolean isSmoothScrolling()
    {
      RecyclerView.SmoothScroller localSmoothScroller = mSmoothScroller;
      return (localSmoothScroller != null) && (localSmoothScroller.isRunning());
    }
    
    public boolean isViewPartiallyVisible(View paramView, boolean paramBoolean1, boolean paramBoolean2)
    {
      if ((mHorizontalBoundCheck.isViewWithinBoundFlags(paramView, 24579)) && (mVerticalBoundCheck.isViewWithinBoundFlags(paramView, 24579))) {
        paramBoolean2 = true;
      } else {
        paramBoolean2 = false;
      }
      if (paramBoolean1) {
        return paramBoolean2;
      }
      return paramBoolean2 ^ true;
    }
    
    public void layoutDecorated(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      Rect localRect = getLayoutParamsmDecorInsets;
      paramView.layout(paramInt1 + left, paramInt2 + top, paramInt3 - right, paramInt4 - bottom);
    }
    
    public void layoutDecoratedWithMargins(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      RecyclerView.LayoutParams localLayoutParams = (RecyclerView.LayoutParams)paramView.getLayoutParams();
      Rect localRect = mDecorInsets;
      paramView.layout(paramInt1 + left + leftMargin, paramInt2 + top + topMargin, paramInt3 - right - rightMargin, paramInt4 - bottom - bottomMargin);
    }
    
    public void measureChild(View paramView, int paramInt1, int paramInt2)
    {
      RecyclerView.LayoutParams localLayoutParams = (RecyclerView.LayoutParams)paramView.getLayoutParams();
      Rect localRect = mRecyclerView.getItemDecorInsetsForChild(paramView);
      int k = left;
      int m = right;
      int i = top;
      int j = bottom;
      paramInt1 = getChildMeasureSpec(getWidth(), getWidthMode(), getPaddingLeft() + getPaddingRight() + (paramInt1 + (k + m)), width, canScrollHorizontally());
      paramInt2 = getChildMeasureSpec(getHeight(), getHeightMode(), getPaddingTop() + getPaddingBottom() + (paramInt2 + (i + j)), height, canScrollVertically());
      if (shouldMeasureChild(paramView, paramInt1, paramInt2, localLayoutParams)) {
        paramView.measure(paramInt1, paramInt2);
      }
    }
    
    public void measureChildWithMargins(View paramView, int paramInt1, int paramInt2)
    {
      RecyclerView.LayoutParams localLayoutParams = (RecyclerView.LayoutParams)paramView.getLayoutParams();
      Rect localRect = mRecyclerView.getItemDecorInsetsForChild(paramView);
      int k = left;
      int m = right;
      int i = top;
      int j = bottom;
      paramInt1 = getChildMeasureSpec(getWidth(), getWidthMode(), getPaddingLeft() + getPaddingRight() + leftMargin + rightMargin + (paramInt1 + (k + m)), width, canScrollHorizontally());
      paramInt2 = getChildMeasureSpec(getHeight(), getHeightMode(), getPaddingTop() + getPaddingBottom() + topMargin + bottomMargin + (paramInt2 + (i + j)), height, canScrollVertically());
      if (shouldMeasureChild(paramView, paramInt1, paramInt2, localLayoutParams)) {
        paramView.measure(paramInt1, paramInt2);
      }
    }
    
    public void moveView(int paramInt1, int paramInt2)
    {
      Object localObject = getChildAt(paramInt1);
      if (localObject != null)
      {
        detachViewAt(paramInt1);
        attachView((View)localObject, paramInt2);
        return;
      }
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("Cannot move a child from non-existing index:");
      ((StringBuilder)localObject).append(paramInt1);
      ((StringBuilder)localObject).append(mRecyclerView.toString());
      throw new IllegalArgumentException(((StringBuilder)localObject).toString());
    }
    
    public void offsetChildrenHorizontal(int paramInt)
    {
      RecyclerView localRecyclerView = mRecyclerView;
      if (localRecyclerView != null) {
        localRecyclerView.offsetChildrenHorizontal(paramInt);
      }
    }
    
    public void offsetChildrenVertical(int paramInt)
    {
      RecyclerView localRecyclerView = mRecyclerView;
      if (localRecyclerView != null) {
        localRecyclerView.offsetChildrenVertical(paramInt);
      }
    }
    
    public void onAdapterChanged(RecyclerView.Adapter paramAdapter1, RecyclerView.Adapter paramAdapter2) {}
    
    public boolean onAddFocusables(RecyclerView paramRecyclerView, ArrayList paramArrayList, int paramInt1, int paramInt2)
    {
      return false;
    }
    
    public void onAttachedToWindow(RecyclerView paramRecyclerView) {}
    
    public void onDetachedFromWindow(RecyclerView paramRecyclerView) {}
    
    public void onDetachedFromWindow(RecyclerView paramRecyclerView, RecyclerView.Recycler paramRecycler)
    {
      onDetachedFromWindow(paramRecyclerView);
    }
    
    public View onFocusSearchFailed(View paramView, int paramInt, RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
    {
      return null;
    }
    
    public void onInitializeAccessibilityEvent(AccessibilityEvent paramAccessibilityEvent)
    {
      onInitializeAccessibilityEvent(mRecyclerView.mRecycler, mRecyclerView.mState, paramAccessibilityEvent);
    }
    
    public void onInitializeAccessibilityEvent(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, AccessibilityEvent paramAccessibilityEvent)
    {
      paramRecycler = mRecyclerView;
      if (paramRecycler != null)
      {
        if (paramAccessibilityEvent == null) {
          return;
        }
        boolean bool2 = true;
        boolean bool1 = bool2;
        if (!paramRecycler.canScrollVertically(1))
        {
          bool1 = bool2;
          if (!mRecyclerView.canScrollVertically(-1))
          {
            bool1 = bool2;
            if (!mRecyclerView.canScrollHorizontally(-1)) {
              if (mRecyclerView.canScrollHorizontally(1)) {
                bool1 = bool2;
              } else {
                bool1 = false;
              }
            }
          }
        }
        paramAccessibilityEvent.setScrollable(bool1);
        if (mRecyclerView.mAdapter != null) {
          paramAccessibilityEvent.setItemCount(mRecyclerView.mAdapter.getItemCount());
        }
      }
    }
    
    void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfoCompat paramAccessibilityNodeInfoCompat)
    {
      onInitializeAccessibilityNodeInfo(mRecyclerView.mRecycler, mRecyclerView.mState, paramAccessibilityNodeInfoCompat);
    }
    
    public void onInitializeAccessibilityNodeInfo(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, AccessibilityNodeInfoCompat paramAccessibilityNodeInfoCompat)
    {
      if ((mRecyclerView.canScrollVertically(-1)) || (mRecyclerView.canScrollHorizontally(-1)))
      {
        paramAccessibilityNodeInfoCompat.addAction(8192);
        paramAccessibilityNodeInfoCompat.setScrollable(true);
      }
      if ((mRecyclerView.canScrollVertically(1)) || (mRecyclerView.canScrollHorizontally(1)))
      {
        paramAccessibilityNodeInfoCompat.addAction(4096);
        paramAccessibilityNodeInfoCompat.setScrollable(true);
      }
      paramAccessibilityNodeInfoCompat.setCollectionInfo(AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(getRowCountForAccessibility(paramRecycler, paramState), getColumnCountForAccessibility(paramRecycler, paramState), isLayoutHierarchical(paramRecycler, paramState), getSelectionModeForAccessibility(paramRecycler, paramState)));
    }
    
    void onInitializeAccessibilityNodeInfoForItem(View paramView, AccessibilityNodeInfoCompat paramAccessibilityNodeInfoCompat)
    {
      RecyclerView.ViewHolder localViewHolder = RecyclerView.getChildViewHolderInt(paramView);
      if ((localViewHolder != null) && (!localViewHolder.isRemoved()) && (!mChildHelper.isHidden(itemView))) {
        onInitializeAccessibilityNodeInfoForItem(mRecyclerView.mRecycler, mRecyclerView.mState, paramView, paramAccessibilityNodeInfoCompat);
      }
    }
    
    public void onInitializeAccessibilityNodeInfoForItem(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, View paramView, AccessibilityNodeInfoCompat paramAccessibilityNodeInfoCompat)
    {
      int i;
      if (canScrollVertically()) {
        i = getPosition(paramView);
      } else {
        i = 0;
      }
      int j;
      if (canScrollHorizontally()) {
        j = getPosition(paramView);
      } else {
        j = 0;
      }
      paramAccessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(i, 1, j, 1, false, false));
    }
    
    public View onInterceptFocusSearch(View paramView, int paramInt)
    {
      return null;
    }
    
    public void onItemsAdded(RecyclerView paramRecyclerView, int paramInt1, int paramInt2) {}
    
    public void onItemsChanged(RecyclerView paramRecyclerView) {}
    
    public void onItemsMoved(RecyclerView paramRecyclerView, int paramInt1, int paramInt2, int paramInt3) {}
    
    public void onItemsRemoved(RecyclerView paramRecyclerView, int paramInt1, int paramInt2) {}
    
    public void onItemsUpdated(RecyclerView paramRecyclerView, int paramInt1, int paramInt2) {}
    
    public void onItemsUpdated(RecyclerView paramRecyclerView, int paramInt1, int paramInt2, Object paramObject)
    {
      onItemsUpdated(paramRecyclerView, paramInt1, paramInt2);
    }
    
    public void onLayoutChildren(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
    {
      Log.e("RecyclerView", "You must override onLayoutChildren(Recycler recycler, State state) ");
    }
    
    public void onLayoutCompleted(RecyclerView.State paramState) {}
    
    public void onMeasure(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, int paramInt1, int paramInt2)
    {
      mRecyclerView.defaultOnMeasure(paramInt1, paramInt2);
    }
    
    public boolean onRequestChildFocus(RecyclerView paramRecyclerView, View paramView1, View paramView2)
    {
      return (isSmoothScrolling()) || (paramRecyclerView.isComputingLayout());
    }
    
    public boolean onRequestChildFocus(RecyclerView paramRecyclerView, RecyclerView.State paramState, View paramView1, View paramView2)
    {
      return onRequestChildFocus(paramRecyclerView, paramView1, paramView2);
    }
    
    public void onRestoreInstanceState(Parcelable paramParcelable) {}
    
    public Parcelable onSaveInstanceState()
    {
      return null;
    }
    
    public void onScrollStateChanged(int paramInt) {}
    
    void onSmoothScrollerStopped(RecyclerView.SmoothScroller paramSmoothScroller)
    {
      if (mSmoothScroller == paramSmoothScroller) {
        mSmoothScroller = null;
      }
    }
    
    boolean performAccessibilityAction(int paramInt, Bundle paramBundle)
    {
      return performAccessibilityAction(mRecyclerView.mRecycler, mRecyclerView.mState, paramInt, paramBundle);
    }
    
    public boolean performAccessibilityAction(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, int paramInt, Bundle paramBundle)
    {
      paramRecycler = mRecyclerView;
      if (paramRecycler == null) {
        return false;
      }
      if (paramInt != 4096) {
        if (paramInt != 8192) {
          paramInt = 0;
        }
      }
      int i;
      do
      {
        do
        {
          j = 0;
          break;
          if (paramRecycler.canScrollVertically(-1)) {
            i = -(getHeight() - getPaddingTop() - getPaddingBottom());
          } else {
            i = 0;
          }
          paramInt = i;
        } while (!mRecyclerView.canScrollHorizontally(-1));
        j = -(getWidth() - getPaddingLeft() - getPaddingRight());
        paramInt = i;
        break;
        if (paramRecycler.canScrollVertically(1)) {
          i = getHeight() - getPaddingTop() - getPaddingBottom();
        } else {
          i = 0;
        }
        paramInt = i;
      } while (!mRecyclerView.canScrollHorizontally(1));
      int j = getWidth() - getPaddingLeft() - getPaddingRight();
      paramInt = i;
      if ((paramInt == 0) && (j == 0)) {
        return false;
      }
      mRecyclerView.smoothScrollBy(j, paramInt);
      return true;
    }
    
    boolean performAccessibilityActionForItem(View paramView, int paramInt, Bundle paramBundle)
    {
      return performAccessibilityActionForItem(mRecyclerView.mRecycler, mRecyclerView.mState, paramView, paramInt, paramBundle);
    }
    
    public boolean performAccessibilityActionForItem(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, View paramView, int paramInt, Bundle paramBundle)
    {
      return false;
    }
    
    public void postOnAnimation(Runnable paramRunnable)
    {
      RecyclerView localRecyclerView = mRecyclerView;
      if (localRecyclerView != null) {
        ViewCompat.postOnAnimation(localRecyclerView, paramRunnable);
      }
    }
    
    public void removeAllViews()
    {
      int i = getChildCount() - 1;
      while (i >= 0)
      {
        mChildHelper.removeViewAt(i);
        i -= 1;
      }
    }
    
    public void removeAndRecycleAllViews(RecyclerView.Recycler paramRecycler)
    {
      int i = getChildCount() - 1;
      while (i >= 0)
      {
        if (!RecyclerView.getChildViewHolderInt(getChildAt(i)).shouldIgnore()) {
          removeAndRecycleViewAt(i, paramRecycler);
        }
        i -= 1;
      }
    }
    
    void removeAndRecycleScrapInt(RecyclerView.Recycler paramRecycler)
    {
      int j = paramRecycler.getScrapCount();
      int i = j - 1;
      while (i >= 0)
      {
        View localView = paramRecycler.getScrapViewAt(i);
        RecyclerView.ViewHolder localViewHolder = RecyclerView.getChildViewHolderInt(localView);
        if (!localViewHolder.shouldIgnore())
        {
          localViewHolder.setIsRecyclable(false);
          if (localViewHolder.isTmpDetached()) {
            mRecyclerView.removeDetachedView(localView, false);
          }
          if (mRecyclerView.mItemAnimator != null) {
            mRecyclerView.mItemAnimator.endAnimation(localViewHolder);
          }
          localViewHolder.setIsRecyclable(true);
          paramRecycler.quickRecycleScrapView(localView);
        }
        i -= 1;
      }
      paramRecycler.clearScrap();
      if (j > 0) {
        mRecyclerView.invalidate();
      }
    }
    
    public void removeAndRecycleView(View paramView, RecyclerView.Recycler paramRecycler)
    {
      removeView(paramView);
      paramRecycler.recycleView(paramView);
    }
    
    public void removeAndRecycleViewAt(int paramInt, RecyclerView.Recycler paramRecycler)
    {
      View localView = getChildAt(paramInt);
      removeViewAt(paramInt);
      paramRecycler.recycleView(localView);
    }
    
    public boolean removeCallbacks(Runnable paramRunnable)
    {
      RecyclerView localRecyclerView = mRecyclerView;
      if (localRecyclerView != null) {
        return localRecyclerView.removeCallbacks(paramRunnable);
      }
      return false;
    }
    
    public void removeDetachedView(View paramView)
    {
      mRecyclerView.removeDetachedView(paramView, false);
    }
    
    public void removeView(View paramView)
    {
      mChildHelper.removeView(paramView);
    }
    
    public void removeViewAt(int paramInt)
    {
      if (getChildAt(paramInt) != null) {
        mChildHelper.removeViewAt(paramInt);
      }
    }
    
    public boolean requestChildRectangleOnScreen(RecyclerView paramRecyclerView, View paramView, Rect paramRect, boolean paramBoolean)
    {
      return requestChildRectangleOnScreen(paramRecyclerView, paramView, paramRect, paramBoolean, false);
    }
    
    public boolean requestChildRectangleOnScreen(RecyclerView paramRecyclerView, View paramView, Rect paramRect, boolean paramBoolean1, boolean paramBoolean2)
    {
      paramView = getChildRectangleOnScreenScrollAmount(paramRecyclerView, paramView, paramRect, paramBoolean1);
      int i = paramView[0];
      int j = paramView[1];
      if ((!paramBoolean2) || (isFocusedChildVisibleAfterScrolling(paramRecyclerView, i, j)))
      {
        if (i == 0) {
          if (j == 0) {
            break label77;
          }
        }
      }
      else {
        return false;
      }
      if (paramBoolean1)
      {
        paramRecyclerView.scrollBy(i, j);
        return true;
      }
      paramRecyclerView.smoothScrollBy(i, j);
      return true;
      label77:
      return false;
    }
    
    public void requestLayout()
    {
      RecyclerView localRecyclerView = mRecyclerView;
      if (localRecyclerView != null) {
        localRecyclerView.requestLayout();
      }
    }
    
    public void requestSimpleAnimationsInNextLayout()
    {
      mRequestedSimpleAnimations = true;
    }
    
    public int scrollHorizontallyBy(int paramInt, RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
    {
      return 0;
    }
    
    public void scrollToPosition(int paramInt) {}
    
    public int scrollVerticallyBy(int paramInt, RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
    {
      return 0;
    }
    
    public void setAutoMeasureEnabled(boolean paramBoolean)
    {
      mAutoMeasure = paramBoolean;
    }
    
    void setExactMeasureSpecsFrom(RecyclerView paramRecyclerView)
    {
      setMeasureSpecs(View.MeasureSpec.makeMeasureSpec(paramRecyclerView.getWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(paramRecyclerView.getHeight(), 1073741824));
    }
    
    public final void setItemPrefetchEnabled(boolean paramBoolean)
    {
      if (paramBoolean != mItemPrefetchEnabled)
      {
        mItemPrefetchEnabled = paramBoolean;
        mPrefetchMaxCountObserved = 0;
        RecyclerView localRecyclerView = mRecyclerView;
        if (localRecyclerView != null) {
          mRecycler.updateViewCacheSize();
        }
      }
    }
    
    void setMeasureSpecs(int paramInt1, int paramInt2)
    {
      mWidth = View.MeasureSpec.getSize(paramInt1);
      mWidthMode = View.MeasureSpec.getMode(paramInt1);
      if ((mWidthMode == 0) && (!RecyclerView.ALLOW_SIZE_IN_UNSPECIFIED_SPEC)) {
        mWidth = 0;
      }
      mHeight = View.MeasureSpec.getSize(paramInt2);
      mHeightMode = View.MeasureSpec.getMode(paramInt2);
      if ((mHeightMode == 0) && (!RecyclerView.ALLOW_SIZE_IN_UNSPECIFIED_SPEC)) {
        mHeight = 0;
      }
    }
    
    public void setMeasuredDimension(int paramInt1, int paramInt2)
    {
      mRecyclerView.setMeasuredDimension(paramInt1, paramInt2);
    }
    
    public void setMeasuredDimension(Rect paramRect, int paramInt1, int paramInt2)
    {
      int i = paramRect.width();
      int j = getPaddingLeft();
      int k = getPaddingRight();
      int m = paramRect.height();
      int n = getPaddingTop();
      int i1 = getPaddingBottom();
      setMeasuredDimension(chooseSize(paramInt1, i + j + k, getMinimumWidth()), chooseSize(paramInt2, m + n + i1, getMinimumHeight()));
    }
    
    void setMeasuredDimensionFromChildren(int paramInt1, int paramInt2)
    {
      int i4 = getChildCount();
      if (i4 == 0)
      {
        mRecyclerView.defaultOnMeasure(paramInt1, paramInt2);
        return;
      }
      int j = 0;
      int i2 = Integer.MAX_VALUE;
      int k = Integer.MAX_VALUE;
      int n = Integer.MIN_VALUE;
      int i3;
      for (int i = Integer.MIN_VALUE; j < i4; i = i3)
      {
        View localView = getChildAt(j);
        Rect localRect = mRecyclerView.mTempRect;
        getDecoratedBoundsWithMargins(localView, localRect);
        int m = i2;
        if (left < i2) {
          m = left;
        }
        int i1 = n;
        if (right > n) {
          i1 = right;
        }
        n = k;
        if (top < k) {
          n = top;
        }
        i3 = i;
        if (bottom > i) {
          i3 = bottom;
        }
        j += 1;
        i2 = m;
        k = n;
        n = i1;
      }
      mRecyclerView.mTempRect.set(i2, k, n, i);
      setMeasuredDimension(mRecyclerView.mTempRect, paramInt1, paramInt2);
    }
    
    public void setMeasurementCacheEnabled(boolean paramBoolean)
    {
      mMeasurementCacheEnabled = paramBoolean;
    }
    
    void setRecyclerView(RecyclerView paramRecyclerView)
    {
      if (paramRecyclerView == null)
      {
        mRecyclerView = null;
        mChildHelper = null;
        mWidth = 0;
        mHeight = 0;
      }
      else
      {
        mRecyclerView = paramRecyclerView;
        mChildHelper = mChildHelper;
        mWidth = paramRecyclerView.getWidth();
        mHeight = paramRecyclerView.getHeight();
      }
      mWidthMode = 1073741824;
      mHeightMode = 1073741824;
    }
    
    boolean shouldMeasureChild(View paramView, int paramInt1, int paramInt2, RecyclerView.LayoutParams paramLayoutParams)
    {
      return (paramView.isLayoutRequested()) || (!mMeasurementCacheEnabled) || (!isMeasurementUpToDate(paramView.getWidth(), paramInt1, width)) || (!isMeasurementUpToDate(paramView.getHeight(), paramInt2, height));
    }
    
    boolean shouldMeasureTwice()
    {
      return false;
    }
    
    boolean shouldReMeasureChild(View paramView, int paramInt1, int paramInt2, RecyclerView.LayoutParams paramLayoutParams)
    {
      return (!mMeasurementCacheEnabled) || (!isMeasurementUpToDate(paramView.getMeasuredWidth(), paramInt1, width)) || (!isMeasurementUpToDate(paramView.getMeasuredHeight(), paramInt2, height));
    }
    
    public void smoothScrollToPosition(RecyclerView paramRecyclerView, RecyclerView.State paramState, int paramInt)
    {
      Log.e("RecyclerView", "You must override smoothScrollToPosition to support smooth scrolling");
    }
    
    public void startSmoothScroll(RecyclerView.SmoothScroller paramSmoothScroller)
    {
      RecyclerView.SmoothScroller localSmoothScroller = mSmoothScroller;
      if ((localSmoothScroller != null) && (paramSmoothScroller != localSmoothScroller) && (localSmoothScroller.isRunning())) {
        mSmoothScroller.stop();
      }
      mSmoothScroller = paramSmoothScroller;
      mSmoothScroller.start(mRecyclerView, this);
    }
    
    public void stopIgnoringView(View paramView)
    {
      paramView = RecyclerView.getChildViewHolderInt(paramView);
      paramView.stopIgnoring();
      paramView.resetInternal();
      paramView.addFlags(4);
    }
    
    void stopSmoothScroller()
    {
      RecyclerView.SmoothScroller localSmoothScroller = mSmoothScroller;
      if (localSmoothScroller != null) {
        localSmoothScroller.stop();
      }
    }
    
    public boolean supportsPredictiveItemAnimations()
    {
      return false;
    }
    
    public static abstract interface LayoutPrefetchRegistry
    {
      public abstract void addPosition(int paramInt1, int paramInt2);
    }
    
    public static class Properties
    {
      public int orientation;
      public boolean reverseLayout;
      public int spanCount;
      public boolean stackFromEnd;
      
      public Properties() {}
    }
  }
  
  public static class LayoutParams
    extends ViewGroup.MarginLayoutParams
  {
    final Rect mDecorInsets = new Rect();
    boolean mInsetsDirty = true;
    boolean mPendingInvalidate = false;
    RecyclerView.ViewHolder mViewHolder;
    
    public LayoutParams(int paramInt1, int paramInt2)
    {
      super(paramInt2);
    }
    
    public LayoutParams(Context paramContext, AttributeSet paramAttributeSet)
    {
      super(paramAttributeSet);
    }
    
    public LayoutParams(ViewGroup.LayoutParams paramLayoutParams)
    {
      super();
    }
    
    public LayoutParams(ViewGroup.MarginLayoutParams paramMarginLayoutParams)
    {
      super();
    }
    
    public LayoutParams(LayoutParams paramLayoutParams)
    {
      super();
    }
    
    public int getViewAdapterPosition()
    {
      return mViewHolder.getAdapterPosition();
    }
    
    public int getViewLayoutPosition()
    {
      return mViewHolder.getLayoutPosition();
    }
    
    public int getViewPosition()
    {
      return mViewHolder.getPosition();
    }
    
    public boolean isItemChanged()
    {
      return mViewHolder.isUpdated();
    }
    
    public boolean isItemRemoved()
    {
      return mViewHolder.isRemoved();
    }
    
    public boolean isViewInvalid()
    {
      return mViewHolder.isInvalid();
    }
    
    public boolean viewNeedsUpdate()
    {
      return mViewHolder.needsUpdate();
    }
  }
  
  public static abstract interface OnChildAttachStateChangeListener
  {
    public abstract void onChildViewAttachedToWindow(View paramView);
    
    public abstract void onChildViewDetachedFromWindow(View paramView);
  }
  
  public static abstract class OnFlingListener
  {
    public OnFlingListener() {}
    
    public abstract boolean onFling(int paramInt1, int paramInt2);
  }
  
  public static abstract interface OnItemTouchListener
  {
    public abstract boolean onInterceptTouchEvent(RecyclerView paramRecyclerView, MotionEvent paramMotionEvent);
    
    public abstract void onRequestDisallowInterceptTouchEvent(boolean paramBoolean);
    
    public abstract void onTouchEvent(RecyclerView paramRecyclerView, MotionEvent paramMotionEvent);
  }
  
  public static abstract class OnScrollListener
  {
    public OnScrollListener() {}
    
    public void onScrollStateChanged(RecyclerView paramRecyclerView, int paramInt) {}
    
    public void onScrolled(RecyclerView paramRecyclerView, int paramInt1, int paramInt2) {}
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface Orientation {}
  
  public static class RecycledViewPool
  {
    private static final int DEFAULT_MAX_SCRAP = 5;
    private int mAttachCount = 0;
    SparseArray<ScrapData> mScrap = new SparseArray();
    
    public RecycledViewPool() {}
    
    private ScrapData getScrapDataForType(int paramInt)
    {
      ScrapData localScrapData2 = (ScrapData)mScrap.get(paramInt);
      ScrapData localScrapData1 = localScrapData2;
      if (localScrapData2 == null)
      {
        localScrapData1 = new ScrapData();
        mScrap.put(paramInt, localScrapData1);
      }
      return localScrapData1;
    }
    
    void attach()
    {
      mAttachCount += 1;
    }
    
    public void clear()
    {
      int i = 0;
      while (i < mScrap.size())
      {
        mScrap.valueAt(i)).mScrapHeap.clear();
        i += 1;
      }
    }
    
    void detach()
    {
      mAttachCount -= 1;
    }
    
    void factorInBindTime(int paramInt, long paramLong)
    {
      ScrapData localScrapData = getScrapDataForType(paramInt);
      mBindRunningAverageNs = runningAverage(mBindRunningAverageNs, paramLong);
    }
    
    void factorInCreateTime(int paramInt, long paramLong)
    {
      ScrapData localScrapData = getScrapDataForType(paramInt);
      mCreateRunningAverageNs = runningAverage(mCreateRunningAverageNs, paramLong);
    }
    
    public RecyclerView.ViewHolder getRecycledView(int paramInt)
    {
      Object localObject = (ScrapData)mScrap.get(paramInt);
      if ((localObject != null) && (!mScrapHeap.isEmpty()))
      {
        localObject = mScrapHeap;
        return (RecyclerView.ViewHolder)((ArrayList)localObject).remove(((ArrayList)localObject).size() - 1);
      }
      return null;
    }
    
    public int getRecycledViewCount(int paramInt)
    {
      return getScrapDataForTypemScrapHeap.size();
    }
    
    void onAdapterChanged(RecyclerView.Adapter paramAdapter1, RecyclerView.Adapter paramAdapter2, boolean paramBoolean)
    {
      if (paramAdapter1 != null) {
        detach();
      }
      if ((!paramBoolean) && (mAttachCount == 0)) {
        clear();
      }
      if (paramAdapter2 != null) {
        attach();
      }
    }
    
    public void putRecycledView(RecyclerView.ViewHolder paramViewHolder)
    {
      int i = paramViewHolder.getItemViewType();
      ArrayList localArrayList = getScrapDataForTypemScrapHeap;
      if (mScrap.get(i)).mMaxScrap <= localArrayList.size()) {
        return;
      }
      paramViewHolder.resetInternal();
      localArrayList.add(paramViewHolder);
    }
    
    long runningAverage(long paramLong1, long paramLong2)
    {
      if (paramLong1 == 0L) {
        return paramLong2;
      }
      return paramLong1 / 4L * 3L + paramLong2 / 4L;
    }
    
    public void setMaxRecycledViews(int paramInt1, int paramInt2)
    {
      Object localObject = getScrapDataForType(paramInt1);
      mMaxScrap = paramInt2;
      localObject = mScrapHeap;
      while (((ArrayList)localObject).size() > paramInt2) {
        ((ArrayList)localObject).remove(((ArrayList)localObject).size() - 1);
      }
    }
    
    int size()
    {
      int i = 0;
      int k;
      for (int j = 0; i < mScrap.size(); j = k)
      {
        ArrayList localArrayList = mScrap.valueAt(i)).mScrapHeap;
        k = j;
        if (localArrayList != null) {
          k = j + localArrayList.size();
        }
        i += 1;
      }
      return j;
    }
    
    boolean willBindInTime(int paramInt, long paramLong1, long paramLong2)
    {
      long l = getScrapDataForTypemBindRunningAverageNs;
      return (l == 0L) || (paramLong1 + l < paramLong2);
    }
    
    boolean willCreateInTime(int paramInt, long paramLong1, long paramLong2)
    {
      long l = getScrapDataForTypemCreateRunningAverageNs;
      return (l == 0L) || (paramLong1 + l < paramLong2);
    }
    
    static class ScrapData
    {
      long mBindRunningAverageNs = 0L;
      long mCreateRunningAverageNs = 0L;
      int mMaxScrap = 5;
      final ArrayList<RecyclerView.ViewHolder> mScrapHeap = new ArrayList();
      
      ScrapData() {}
    }
  }
  
  public final class Recycler
  {
    static final int DEFAULT_CACHE_SIZE = 2;
    final ArrayList<RecyclerView.ViewHolder> mAttachedScrap = new ArrayList();
    final ArrayList<RecyclerView.ViewHolder> mCachedViews = new ArrayList();
    ArrayList<RecyclerView.ViewHolder> mChangedScrap = null;
    RecyclerView.RecycledViewPool mRecyclerPool;
    private int mRequestedCacheMax = 2;
    private final List<RecyclerView.ViewHolder> mUnmodifiableAttachedScrap = Collections.unmodifiableList(mAttachedScrap);
    private RecyclerView.ViewCacheExtension mViewCacheExtension;
    int mViewCacheMax = 2;
    
    public Recycler() {}
    
    private void attachAccessibilityDelegateOnBind(RecyclerView.ViewHolder paramViewHolder)
    {
      if (isAccessibilityEnabled())
      {
        View localView = itemView;
        if (ViewCompat.getImportantForAccessibility(localView) == 0) {
          ViewCompat.setImportantForAccessibility(localView, 1);
        }
        if (!ViewCompat.hasAccessibilityDelegate(localView))
        {
          paramViewHolder.addFlags(16384);
          ViewCompat.setAccessibilityDelegate(localView, mAccessibilityDelegate.getItemDelegate());
        }
      }
    }
    
    private void invalidateDisplayListInt(ViewGroup paramViewGroup, boolean paramBoolean)
    {
      int i = paramViewGroup.getChildCount() - 1;
      while (i >= 0)
      {
        View localView = paramViewGroup.getChildAt(i);
        if ((localView instanceof ViewGroup)) {
          invalidateDisplayListInt((ViewGroup)localView, true);
        }
        i -= 1;
      }
      if (!paramBoolean) {
        return;
      }
      if (paramViewGroup.getVisibility() == 4)
      {
        paramViewGroup.setVisibility(0);
        paramViewGroup.setVisibility(4);
        return;
      }
      i = paramViewGroup.getVisibility();
      paramViewGroup.setVisibility(4);
      paramViewGroup.setVisibility(i);
    }
    
    private void invalidateDisplayListInt(RecyclerView.ViewHolder paramViewHolder)
    {
      if ((itemView instanceof ViewGroup)) {
        invalidateDisplayListInt((ViewGroup)itemView, false);
      }
    }
    
    private boolean tryBindViewHolderByDeadline(RecyclerView.ViewHolder paramViewHolder, int paramInt1, int paramInt2, long paramLong)
    {
      mOwnerRecyclerView = RecyclerView.this;
      int i = paramViewHolder.getItemViewType();
      long l = getNanoTime();
      if ((paramLong != Long.MAX_VALUE) && (!mRecyclerPool.willBindInTime(i, l, paramLong))) {
        return false;
      }
      mAdapter.bindViewHolder(paramViewHolder, paramInt1);
      paramLong = getNanoTime();
      mRecyclerPool.factorInBindTime(paramViewHolder.getItemViewType(), paramLong - l);
      attachAccessibilityDelegateOnBind(paramViewHolder);
      if (mState.isPreLayout()) {
        mPreLayoutPosition = paramInt2;
      }
      return true;
    }
    
    void addViewHolderToRecycledViewPool(RecyclerView.ViewHolder paramViewHolder, boolean paramBoolean)
    {
      RecyclerView.clearNestedRecyclerViewIfNotNested(paramViewHolder);
      if (paramViewHolder.hasAnyOfTheFlags(16384))
      {
        paramViewHolder.setFlags(0, 16384);
        ViewCompat.setAccessibilityDelegate(itemView, null);
      }
      if (paramBoolean) {
        dispatchViewRecycled(paramViewHolder);
      }
      mOwnerRecyclerView = null;
      getRecycledViewPool().putRecycledView(paramViewHolder);
    }
    
    public void bindViewToPosition(View paramView, int paramInt)
    {
      RecyclerView.ViewHolder localViewHolder = RecyclerView.getChildViewHolderInt(paramView);
      if (localViewHolder != null)
      {
        int i = mAdapterHelper.findPositionOffset(paramInt);
        if ((i >= 0) && (i < mAdapter.getItemCount()))
        {
          tryBindViewHolderByDeadline(localViewHolder, i, paramInt, Long.MAX_VALUE);
          paramView = itemView.getLayoutParams();
          if (paramView == null)
          {
            paramView = (RecyclerView.LayoutParams)generateDefaultLayoutParams();
            itemView.setLayoutParams(paramView);
          }
          else if (!checkLayoutParams(paramView))
          {
            paramView = (RecyclerView.LayoutParams)generateLayoutParams(paramView);
            itemView.setLayoutParams(paramView);
          }
          else
          {
            paramView = (RecyclerView.LayoutParams)paramView;
          }
          boolean bool = true;
          mInsetsDirty = true;
          mViewHolder = localViewHolder;
          if (itemView.getParent() != null) {
            bool = false;
          }
          mPendingInvalidate = bool;
          return;
        }
        paramView = new StringBuilder();
        paramView.append("Inconsistency detected. Invalid item position ");
        paramView.append(paramInt);
        paramView.append("(offset:");
        paramView.append(i);
        paramView.append(").");
        paramView.append("state:");
        paramView.append(mState.getItemCount());
        paramView.append(exceptionLabel());
        throw new IndexOutOfBoundsException(paramView.toString());
      }
      paramView = new StringBuilder();
      paramView.append("The view does not have a ViewHolder. You cannot pass arbitrary views to this method, they should be created by the Adapter");
      paramView.append(exceptionLabel());
      throw new IllegalArgumentException(paramView.toString());
    }
    
    public void clear()
    {
      mAttachedScrap.clear();
      recycleAndClearCachedViews();
    }
    
    void clearOldPositions()
    {
      int k = mCachedViews.size();
      int j = 0;
      int i = 0;
      while (i < k)
      {
        ((RecyclerView.ViewHolder)mCachedViews.get(i)).clearOldPosition();
        i += 1;
      }
      k = mAttachedScrap.size();
      i = 0;
      while (i < k)
      {
        ((RecyclerView.ViewHolder)mAttachedScrap.get(i)).clearOldPosition();
        i += 1;
      }
      ArrayList localArrayList = mChangedScrap;
      if (localArrayList != null)
      {
        k = localArrayList.size();
        i = j;
        while (i < k)
        {
          ((RecyclerView.ViewHolder)mChangedScrap.get(i)).clearOldPosition();
          i += 1;
        }
      }
    }
    
    void clearScrap()
    {
      mAttachedScrap.clear();
      ArrayList localArrayList = mChangedScrap;
      if (localArrayList != null) {
        localArrayList.clear();
      }
    }
    
    public int convertPreLayoutPositionToPostLayout(int paramInt)
    {
      if ((paramInt >= 0) && (paramInt < mState.getItemCount()))
      {
        if (!mState.isPreLayout()) {
          return paramInt;
        }
        return mAdapterHelper.findPositionOffset(paramInt);
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("invalid position ");
      localStringBuilder.append(paramInt);
      localStringBuilder.append(". State ");
      localStringBuilder.append("item count is ");
      localStringBuilder.append(mState.getItemCount());
      localStringBuilder.append(exceptionLabel());
      throw new IndexOutOfBoundsException(localStringBuilder.toString());
    }
    
    void dispatchViewRecycled(RecyclerView.ViewHolder paramViewHolder)
    {
      if (mRecyclerListener != null) {
        mRecyclerListener.onViewRecycled(paramViewHolder);
      }
      if (mAdapter != null) {
        mAdapter.onViewRecycled(paramViewHolder);
      }
      if (mState != null) {
        mViewInfoStore.removeViewHolder(paramViewHolder);
      }
    }
    
    RecyclerView.ViewHolder getChangedScrapViewForPosition(int paramInt)
    {
      Object localObject = mChangedScrap;
      Recycler localRecycler = this;
      if (localObject != null)
      {
        int k = ((ArrayList)localObject).size();
        if (k == 0) {
          return null;
        }
        int j = 0;
        int i = 0;
        while (i < k)
        {
          localObject = mChangedScrap;
          localObject = (RecyclerView.ViewHolder)((ArrayList)localObject).get(i);
          if ((!((RecyclerView.ViewHolder)localObject).wasReturnedFromScrap()) && (((RecyclerView.ViewHolder)localObject).getLayoutPosition() == paramInt))
          {
            ((RecyclerView.ViewHolder)localObject).addFlags(32);
            return localObject;
          }
          i += 1;
        }
        if (this$0.mAdapter.hasStableIds())
        {
          paramInt = this$0.mAdapterHelper.findPositionOffset(paramInt);
          if ((paramInt > 0) && (paramInt < this$0.mAdapter.getItemCount()))
          {
            long l = this$0.mAdapter.getItemId(paramInt);
            paramInt = j;
            while (paramInt < k)
            {
              localObject = mChangedScrap;
              localObject = (RecyclerView.ViewHolder)((ArrayList)localObject).get(paramInt);
              if ((!((RecyclerView.ViewHolder)localObject).wasReturnedFromScrap()) && (((RecyclerView.ViewHolder)localObject).getItemId() == l))
              {
                ((RecyclerView.ViewHolder)localObject).addFlags(32);
                return localObject;
              }
              paramInt += 1;
            }
          }
        }
      }
      return null;
    }
    
    RecyclerView.RecycledViewPool getRecycledViewPool()
    {
      if (mRecyclerPool == null) {
        mRecyclerPool = new RecyclerView.RecycledViewPool();
      }
      return mRecyclerPool;
    }
    
    int getScrapCount()
    {
      return mAttachedScrap.size();
    }
    
    public List getScrapList()
    {
      return mUnmodifiableAttachedScrap;
    }
    
    RecyclerView.ViewHolder getScrapOrCachedViewForId(long paramLong, int paramInt, boolean paramBoolean)
    {
      Object localObject2 = mAttachedScrap;
      Object localObject1 = this;
      int i = ((ArrayList)localObject2).size() - 1;
      Object localObject3;
      while (i >= 0)
      {
        localObject3 = mAttachedScrap;
        localObject2 = localObject1;
        localObject3 = (RecyclerView.ViewHolder)((ArrayList)localObject3).get(i);
        if ((((RecyclerView.ViewHolder)localObject3).getItemId() == paramLong) && (!((RecyclerView.ViewHolder)localObject3).wasReturnedFromScrap()))
        {
          if (paramInt == ((RecyclerView.ViewHolder)localObject3).getItemViewType())
          {
            ((RecyclerView.ViewHolder)localObject3).addFlags(32);
            localObject1 = localObject3;
            if (!((RecyclerView.ViewHolder)localObject3).isRemoved()) {
              break label289;
            }
            localObject1 = localObject3;
            if (this$0.mState.isPreLayout()) {
              break label289;
            }
            ((RecyclerView.ViewHolder)localObject3).setFlags(2, 14);
            return localObject3;
          }
          if (!paramBoolean)
          {
            ArrayList localArrayList = mAttachedScrap;
            localArrayList.remove(i);
            this$0.removeDetachedView(itemView, false);
            ((Recycler)localObject2).quickRecycleScrapView(itemView);
          }
        }
        i -= 1;
      }
      localObject2 = mCachedViews;
      i = ((ArrayList)localObject2).size() - 1;
      while (i >= 0)
      {
        localObject3 = mCachedViews;
        localObject2 = localObject1;
        localObject3 = (RecyclerView.ViewHolder)((ArrayList)localObject3).get(i);
        if (((RecyclerView.ViewHolder)localObject3).getItemId() == paramLong)
        {
          if (paramInt == ((RecyclerView.ViewHolder)localObject3).getItemViewType())
          {
            localObject1 = localObject3;
            if (paramBoolean) {
              break label289;
            }
            mCachedViews.remove(i);
            return localObject3;
          }
          if (!paramBoolean)
          {
            ((Recycler)localObject2).recycleCachedViewAt(i);
            return null;
          }
        }
        i -= 1;
        localObject1 = localObject2;
      }
      return null;
      label289:
      return localObject1;
    }
    
    RecyclerView.ViewHolder getScrapOrHiddenOrCachedHolderForPosition(int paramInt, boolean paramBoolean)
    {
      int k = mAttachedScrap.size();
      int j = 0;
      int i = 0;
      RecyclerView.ViewHolder localViewHolder;
      while (i < k)
      {
        localViewHolder = (RecyclerView.ViewHolder)mAttachedScrap.get(i);
        if ((!localViewHolder.wasReturnedFromScrap()) && (localViewHolder.getLayoutPosition() == paramInt) && (!localViewHolder.isInvalid()) && ((mState.mInPreLayout) || (!localViewHolder.isRemoved())))
        {
          localViewHolder.addFlags(32);
          return localViewHolder;
        }
        i += 1;
      }
      if (!paramBoolean)
      {
        Object localObject = mChildHelper.findHiddenNonRemovedView(paramInt);
        if (localObject != null)
        {
          localViewHolder = RecyclerView.getChildViewHolderInt((View)localObject);
          mChildHelper.unhide((View)localObject);
          paramInt = mChildHelper.indexOfChild((View)localObject);
          if (paramInt != -1)
          {
            mChildHelper.detachViewFromParent(paramInt);
            scrapView((View)localObject);
            localViewHolder.addFlags(8224);
            return localViewHolder;
          }
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("layout index should not be -1 after unhiding a view:");
          ((StringBuilder)localObject).append(localViewHolder);
          ((StringBuilder)localObject).append(exceptionLabel());
          throw new IllegalStateException(((StringBuilder)localObject).toString());
        }
      }
      k = mCachedViews.size();
      i = j;
      while (i < k)
      {
        localViewHolder = (RecyclerView.ViewHolder)mCachedViews.get(i);
        if ((!localViewHolder.isInvalid()) && (localViewHolder.getLayoutPosition() == paramInt))
        {
          if (paramBoolean) {
            break label308;
          }
          mCachedViews.remove(i);
          return localViewHolder;
        }
        i += 1;
      }
      return null;
      label308:
      return localViewHolder;
    }
    
    View getScrapViewAt(int paramInt)
    {
      return mAttachedScrap.get(paramInt)).itemView;
    }
    
    public View getViewForPosition(int paramInt)
    {
      return getViewForPosition(paramInt, false);
    }
    
    View getViewForPosition(int paramInt, boolean paramBoolean)
    {
      return tryGetViewHolderForPositionByDeadlineMAX_VALUEitemView;
    }
    
    void markItemDecorInsetsDirty()
    {
      int j = mCachedViews.size();
      int i = 0;
      while (i < j)
      {
        RecyclerView.LayoutParams localLayoutParams = (RecyclerView.LayoutParams)mCachedViews.get(i)).itemView.getLayoutParams();
        if (localLayoutParams != null) {
          mInsetsDirty = true;
        }
        i += 1;
      }
    }
    
    void markKnownViewsInvalid()
    {
      int j = mCachedViews.size();
      int i = 0;
      while (i < j)
      {
        RecyclerView.ViewHolder localViewHolder = (RecyclerView.ViewHolder)mCachedViews.get(i);
        if (localViewHolder != null)
        {
          localViewHolder.addFlags(6);
          localViewHolder.addChangePayload(null);
        }
        i += 1;
      }
      if ((mAdapter == null) || (!mAdapter.hasStableIds())) {
        recycleAndClearCachedViews();
      }
    }
    
    void offsetPositionRecordsForInsert(int paramInt1, int paramInt2)
    {
      int j = mCachedViews.size();
      int i = 0;
      while (i < j)
      {
        RecyclerView.ViewHolder localViewHolder = (RecyclerView.ViewHolder)mCachedViews.get(i);
        if ((localViewHolder != null) && (mPosition >= paramInt1)) {
          localViewHolder.offsetPosition(paramInt2, true);
        }
        i += 1;
      }
    }
    
    void offsetPositionRecordsForMove(int paramInt1, int paramInt2)
    {
      int i;
      int j;
      int k;
      if (paramInt1 < paramInt2)
      {
        i = paramInt1;
        j = paramInt2;
        k = -1;
      }
      else
      {
        j = paramInt1;
        i = paramInt2;
        k = 1;
      }
      int n = mCachedViews.size();
      int m = 0;
      while (m < n)
      {
        RecyclerView.ViewHolder localViewHolder = (RecyclerView.ViewHolder)mCachedViews.get(m);
        if ((localViewHolder != null) && (mPosition >= i) && (mPosition <= j)) {
          if (mPosition == paramInt1) {
            localViewHolder.offsetPosition(paramInt2 - paramInt1, false);
          } else {
            localViewHolder.offsetPosition(k, false);
          }
        }
        m += 1;
      }
    }
    
    void offsetPositionRecordsForRemove(int paramInt1, int paramInt2, boolean paramBoolean)
    {
      int i = mCachedViews.size() - 1;
      while (i >= 0)
      {
        RecyclerView.ViewHolder localViewHolder = (RecyclerView.ViewHolder)mCachedViews.get(i);
        if (localViewHolder != null) {
          if (mPosition >= paramInt1 + paramInt2)
          {
            localViewHolder.offsetPosition(-paramInt2, paramBoolean);
          }
          else if (mPosition >= paramInt1)
          {
            localViewHolder.addFlags(8);
            recycleCachedViewAt(i);
          }
        }
        i -= 1;
      }
    }
    
    void onAdapterChanged(RecyclerView.Adapter paramAdapter1, RecyclerView.Adapter paramAdapter2, boolean paramBoolean)
    {
      clear();
      getRecycledViewPool().onAdapterChanged(paramAdapter1, paramAdapter2, paramBoolean);
    }
    
    void quickRecycleScrapView(View paramView)
    {
      paramView = RecyclerView.getChildViewHolderInt(paramView);
      mScrapContainer = null;
      mInChangeScrap = false;
      paramView.clearReturnedFromScrapFlag();
      recycleViewHolderInternal(paramView);
    }
    
    void recycleAndClearCachedViews()
    {
      int i = mCachedViews.size() - 1;
      while (i >= 0)
      {
        recycleCachedViewAt(i);
        i -= 1;
      }
      mCachedViews.clear();
      if (RecyclerView.ALLOW_THREAD_GAP_WORK) {
        mPrefetchRegistry.clearPrefetchPositions();
      }
    }
    
    void recycleCachedViewAt(int paramInt)
    {
      addViewHolderToRecycledViewPool((RecyclerView.ViewHolder)mCachedViews.get(paramInt), true);
      mCachedViews.remove(paramInt);
    }
    
    public void recycleView(View paramView)
    {
      RecyclerView.ViewHolder localViewHolder = RecyclerView.getChildViewHolderInt(paramView);
      if (localViewHolder.isTmpDetached()) {
        removeDetachedView(paramView, false);
      }
      if (localViewHolder.isScrap()) {
        localViewHolder.unScrap();
      } else if (localViewHolder.wasReturnedFromScrap()) {
        localViewHolder.clearReturnedFromScrapFlag();
      }
      recycleViewHolderInternal(localViewHolder);
    }
    
    void recycleViewHolderInternal(RecyclerView.ViewHolder paramViewHolder)
    {
      boolean bool2 = paramViewHolder.isScrap();
      boolean bool1 = false;
      int k = 0;
      StringBuilder localStringBuilder;
      if ((!bool2) && (itemView.getParent() == null))
      {
        if (!paramViewHolder.isTmpDetached())
        {
          if (!paramViewHolder.shouldIgnore())
          {
            bool1 = paramViewHolder.doesTransientStatePreventRecycling();
            int i;
            if ((mAdapter != null) && (bool1) && (mAdapter.onFailedToRecycleView(paramViewHolder))) {
              i = 1;
            } else {
              i = 0;
            }
            int j;
            if ((i == 0) && (!paramViewHolder.isRecyclable()))
            {
              i = 0;
              j = k;
              k = i;
            }
            else
            {
              if ((mViewCacheMax > 0) && (!paramViewHolder.hasAnyOfTheFlags(526)))
              {
                int m = mCachedViews.size();
                j = m;
                i = j;
                if (m >= mViewCacheMax)
                {
                  i = j;
                  if (m > 0)
                  {
                    recycleCachedViewAt(0);
                    i = m - 1;
                  }
                }
                j = i;
                if (RecyclerView.ALLOW_THREAD_GAP_WORK)
                {
                  j = i;
                  if (i > 0)
                  {
                    j = i;
                    if (!mPrefetchRegistry.lastPrefetchIncludedPosition(mPosition))
                    {
                      i -= 1;
                      while (i >= 0)
                      {
                        j = mCachedViews.get(i)).mPosition;
                        if (!mPrefetchRegistry.lastPrefetchIncludedPosition(j)) {
                          break;
                        }
                        i -= 1;
                      }
                      j = i + 1;
                    }
                  }
                }
                mCachedViews.add(j, paramViewHolder);
                i = 1;
              }
              else
              {
                i = 0;
              }
              j = k;
              k = i;
              if (i == 0)
              {
                addViewHolderToRecycledViewPool(paramViewHolder, true);
                j = 1;
                k = i;
              }
            }
            mViewInfoStore.removeViewHolder(paramViewHolder);
            if ((k == 0) && (j == 0) && (bool1)) {
              mOwnerRecyclerView = null;
            }
          }
          else
          {
            paramViewHolder = new StringBuilder();
            paramViewHolder.append("Trying to recycle an ignored view holder. You should first call stopIgnoringView(view) before calling recycle.");
            paramViewHolder.append(exceptionLabel());
            throw new IllegalArgumentException(paramViewHolder.toString());
          }
        }
        else
        {
          localStringBuilder = new StringBuilder();
          localStringBuilder.append("Tmp detached view should be removed from RecyclerView before it can be recycled: ");
          localStringBuilder.append(paramViewHolder);
          localStringBuilder.append(exceptionLabel());
          throw new IllegalArgumentException(localStringBuilder.toString());
        }
      }
      else
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("Scrapped or attached views may not be recycled. isScrap:");
        localStringBuilder.append(paramViewHolder.isScrap());
        localStringBuilder.append(" isAttached:");
        if (itemView.getParent() != null) {
          bool1 = true;
        }
        localStringBuilder.append(bool1);
        localStringBuilder.append(exceptionLabel());
        paramViewHolder = new IllegalArgumentException(localStringBuilder.toString());
        throw paramViewHolder;
      }
    }
    
    void recycleViewInternal(View paramView)
    {
      recycleViewHolderInternal(RecyclerView.getChildViewHolderInt(paramView));
    }
    
    void scrapView(View paramView)
    {
      paramView = RecyclerView.getChildViewHolderInt(paramView);
      if ((!paramView.hasAnyOfTheFlags(12)) && (paramView.isUpdated()) && (!canReuseUpdatedViewHolder(paramView)))
      {
        if (mChangedScrap == null) {
          mChangedScrap = new ArrayList();
        }
        paramView.setScrapContainer(this, true);
        mChangedScrap.add(paramView);
        return;
      }
      if ((paramView.isInvalid()) && (!paramView.isRemoved()) && (!mAdapter.hasStableIds()))
      {
        paramView = new StringBuilder();
        paramView.append("Called scrap view with an invalid view. Invalid views cannot be reused from scrap, they should rebound from recycler pool.");
        paramView.append(exceptionLabel());
        throw new IllegalArgumentException(paramView.toString());
      }
      paramView.setScrapContainer(this, false);
      mAttachedScrap.add(paramView);
    }
    
    void setRecycledViewPool(RecyclerView.RecycledViewPool paramRecycledViewPool)
    {
      RecyclerView.RecycledViewPool localRecycledViewPool = mRecyclerPool;
      if (localRecycledViewPool != null) {
        localRecycledViewPool.detach();
      }
      mRecyclerPool = paramRecycledViewPool;
      if ((mRecyclerPool != null) && (getAdapter() != null)) {
        mRecyclerPool.attach();
      }
    }
    
    void setViewCacheExtension(RecyclerView.ViewCacheExtension paramViewCacheExtension)
    {
      mViewCacheExtension = paramViewCacheExtension;
    }
    
    public void setViewCacheSize(int paramInt)
    {
      mRequestedCacheMax = paramInt;
      updateViewCacheSize();
    }
    
    RecyclerView.ViewHolder tryGetViewHolderForPositionByDeadline(int paramInt, boolean paramBoolean, long paramLong)
    {
      if ((paramInt >= 0) && (paramInt < mState.getItemCount()))
      {
        boolean bool2 = mState.isPreLayout();
        boolean bool1 = true;
        Object localObject3;
        if (bool2)
        {
          localObject3 = getChangedScrapViewForPosition(paramInt);
          localObject1 = localObject3;
          localObject2 = localObject1;
          if (localObject3 != null)
          {
            j = 1;
            localObject2 = localObject1;
            break label74;
          }
        }
        else
        {
          localObject2 = null;
        }
        int j = 0;
        label74:
        int i = j;
        localObject1 = localObject2;
        if (localObject2 == null)
        {
          localObject3 = getScrapOrHiddenOrCachedHolderForPosition(paramInt, paramBoolean);
          localObject2 = localObject3;
          i = j;
          localObject1 = localObject2;
          if (localObject3 != null) {
            if (!validateViewHolderForOffsetPosition((RecyclerView.ViewHolder)localObject3))
            {
              if (!paramBoolean)
              {
                ((RecyclerView.ViewHolder)localObject3).addFlags(4);
                if (((RecyclerView.ViewHolder)localObject3).isScrap())
                {
                  removeDetachedView(itemView, false);
                  ((RecyclerView.ViewHolder)localObject3).unScrap();
                }
                else if (((RecyclerView.ViewHolder)localObject3).wasReturnedFromScrap())
                {
                  ((RecyclerView.ViewHolder)localObject3).clearReturnedFromScrapFlag();
                }
                recycleViewHolderInternal((RecyclerView.ViewHolder)localObject3);
              }
              localObject1 = null;
              i = j;
            }
            else
            {
              i = 1;
              localObject1 = localObject2;
            }
          }
        }
        int k = i;
        Object localObject2 = localObject1;
        if (localObject1 == null)
        {
          k = mAdapterHelper.findPositionOffset(paramInt);
          if ((k >= 0) && (k < mAdapter.getItemCount()))
          {
            int m = mAdapter.getItemViewType(k);
            j = i;
            if (mAdapter.hasStableIds())
            {
              localObject3 = getScrapOrCachedViewForId(mAdapter.getItemId(k), m, paramBoolean);
              localObject2 = localObject3;
              j = i;
              localObject1 = localObject2;
              if (localObject3 != null)
              {
                mPosition = k;
                j = 1;
                localObject1 = localObject2;
              }
            }
            localObject2 = localObject1;
            if (localObject1 == null)
            {
              localObject3 = mViewCacheExtension;
              localObject2 = localObject1;
              if (localObject3 != null)
              {
                localObject3 = ((RecyclerView.ViewCacheExtension)localObject3).getViewForPositionAndType(this, paramInt, m);
                localObject2 = localObject1;
                if (localObject3 != null)
                {
                  localObject1 = getChildViewHolder((View)localObject3);
                  localObject2 = localObject1;
                  if (localObject1 != null)
                  {
                    if (((RecyclerView.ViewHolder)localObject1).shouldIgnore())
                    {
                      localObject1 = new StringBuilder();
                      ((StringBuilder)localObject1).append("getViewForPositionAndType returned a view that is ignored. You must call stopIgnoring before returning this view.");
                      ((StringBuilder)localObject1).append(exceptionLabel());
                      throw new IllegalArgumentException(((StringBuilder)localObject1).toString());
                    }
                  }
                  else
                  {
                    localObject1 = new StringBuilder();
                    ((StringBuilder)localObject1).append("getViewForPositionAndType returned a view which does not have a ViewHolder");
                    ((StringBuilder)localObject1).append(exceptionLabel());
                    throw new IllegalArgumentException(((StringBuilder)localObject1).toString());
                  }
                }
              }
            }
            localObject1 = localObject2;
            if (localObject2 == null)
            {
              localObject3 = getRecycledViewPool().getRecycledView(m);
              localObject2 = localObject3;
              localObject1 = localObject2;
              if (localObject3 != null)
              {
                ((RecyclerView.ViewHolder)localObject3).resetInternal();
                localObject1 = localObject2;
                if (RecyclerView.FORCE_INVALIDATE_DISPLAY_LIST)
                {
                  invalidateDisplayListInt((RecyclerView.ViewHolder)localObject3);
                  localObject1 = localObject2;
                }
              }
            }
            k = j;
            localObject2 = localObject1;
            if (localObject1 == null)
            {
              long l1 = getNanoTime();
              if ((paramLong != Long.MAX_VALUE) && (!mRecyclerPool.willCreateInTime(m, l1, paramLong))) {
                return null;
              }
              localObject2 = mAdapter.createViewHolder(RecyclerView.this, m);
              if (RecyclerView.ALLOW_THREAD_GAP_WORK)
              {
                localObject1 = RecyclerView.findNestedRecyclerView(itemView);
                if (localObject1 != null) {
                  mNestedRecyclerView = new WeakReference(localObject1);
                }
              }
              long l2 = getNanoTime();
              mRecyclerPool.factorInCreateTime(m, l2 - l1);
              k = j;
            }
          }
          else
          {
            localObject1 = new StringBuilder();
            ((StringBuilder)localObject1).append("Inconsistency detected. Invalid item position ");
            ((StringBuilder)localObject1).append(paramInt);
            ((StringBuilder)localObject1).append("(offset:");
            ((StringBuilder)localObject1).append(k);
            ((StringBuilder)localObject1).append(").");
            ((StringBuilder)localObject1).append("state:");
            ((StringBuilder)localObject1).append(mState.getItemCount());
            ((StringBuilder)localObject1).append(exceptionLabel());
            throw new IndexOutOfBoundsException(((StringBuilder)localObject1).toString());
          }
        }
        if ((k != 0) && (!mState.isPreLayout()) && (((RecyclerView.ViewHolder)localObject2).hasAnyOfTheFlags(8192)))
        {
          ((RecyclerView.ViewHolder)localObject2).setFlags(0, 8192);
          if (mState.mRunSimpleAnimations)
          {
            i = RecyclerView.ItemAnimator.buildAdapterChangeFlagsForAnimations((RecyclerView.ViewHolder)localObject2);
            localObject1 = mItemAnimator.recordPreLayoutInformation(mState, (RecyclerView.ViewHolder)localObject2, i | 0x1000, ((RecyclerView.ViewHolder)localObject2).getUnmodifiedPayloads());
            recordAnimationInfoIfBouncedHiddenView((RecyclerView.ViewHolder)localObject2, (RecyclerView.ItemAnimator.ItemHolderInfo)localObject1);
          }
        }
        if ((mState.isPreLayout()) && (((RecyclerView.ViewHolder)localObject2).isBound())) {
          mPreLayoutPosition = paramInt;
        } else {
          if ((!((RecyclerView.ViewHolder)localObject2).isBound()) || (((RecyclerView.ViewHolder)localObject2).needsUpdate()) || (((RecyclerView.ViewHolder)localObject2).isInvalid())) {
            break label940;
          }
        }
        paramBoolean = false;
        break label960;
        label940:
        paramBoolean = tryBindViewHolderByDeadline((RecyclerView.ViewHolder)localObject2, mAdapterHelper.findPositionOffset(paramInt), paramInt, paramLong);
        label960:
        localObject1 = itemView.getLayoutParams();
        if (localObject1 == null)
        {
          localObject1 = (RecyclerView.LayoutParams)generateDefaultLayoutParams();
          itemView.setLayoutParams((ViewGroup.LayoutParams)localObject1);
        }
        else if (!checkLayoutParams((ViewGroup.LayoutParams)localObject1))
        {
          localObject1 = (RecyclerView.LayoutParams)generateLayoutParams((ViewGroup.LayoutParams)localObject1);
          itemView.setLayoutParams((ViewGroup.LayoutParams)localObject1);
        }
        else
        {
          localObject1 = (RecyclerView.LayoutParams)localObject1;
        }
        mViewHolder = ((RecyclerView.ViewHolder)localObject2);
        if ((k != 0) && (paramBoolean)) {
          paramBoolean = bool1;
        } else {
          paramBoolean = false;
        }
        mPendingInvalidate = paramBoolean;
        return localObject2;
      }
      Object localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("Invalid item position ");
      ((StringBuilder)localObject1).append(paramInt);
      ((StringBuilder)localObject1).append("(");
      ((StringBuilder)localObject1).append(paramInt);
      ((StringBuilder)localObject1).append("). Item count:");
      ((StringBuilder)localObject1).append(mState.getItemCount());
      ((StringBuilder)localObject1).append(exceptionLabel());
      throw new IndexOutOfBoundsException(((StringBuilder)localObject1).toString());
    }
    
    void unscrapView(RecyclerView.ViewHolder paramViewHolder)
    {
      if (mInChangeScrap) {
        mChangedScrap.remove(paramViewHolder);
      } else {
        mAttachedScrap.remove(paramViewHolder);
      }
      mScrapContainer = null;
      mInChangeScrap = false;
      paramViewHolder.clearReturnedFromScrapFlag();
    }
    
    void updateViewCacheSize()
    {
      if (mLayout != null) {
        i = mLayout.mPrefetchMaxCountObserved;
      } else {
        i = 0;
      }
      mViewCacheMax = (mRequestedCacheMax + i);
      int i = mCachedViews.size() - 1;
      while ((i >= 0) && (mCachedViews.size() > mViewCacheMax))
      {
        recycleCachedViewAt(i);
        i -= 1;
      }
    }
    
    boolean validateViewHolderForOffsetPosition(RecyclerView.ViewHolder paramViewHolder)
    {
      if (paramViewHolder.isRemoved()) {
        return mState.isPreLayout();
      }
      if ((mPosition >= 0) && (mPosition < mAdapter.getItemCount()))
      {
        if ((!mState.isPreLayout()) && (mAdapter.getItemViewType(mPosition) != paramViewHolder.getItemViewType())) {
          return false;
        }
        if (mAdapter.hasStableIds())
        {
          if (paramViewHolder.getItemId() == mAdapter.getItemId(mPosition)) {
            return true;
          }
        }
        else {
          return true;
        }
      }
      else
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("Inconsistency detected. Invalid view holder adapter position");
        localStringBuilder.append(paramViewHolder);
        localStringBuilder.append(exceptionLabel());
        throw new IndexOutOfBoundsException(localStringBuilder.toString());
      }
      return false;
    }
    
    void viewRangeUpdate(int paramInt1, int paramInt2)
    {
      int i = mCachedViews.size() - 1;
      while (i >= 0)
      {
        RecyclerView.ViewHolder localViewHolder = (RecyclerView.ViewHolder)mCachedViews.get(i);
        if (localViewHolder != null)
        {
          int j = mPosition;
          if ((j >= paramInt1) && (j < paramInt2 + paramInt1))
          {
            localViewHolder.addFlags(2);
            recycleCachedViewAt(i);
          }
        }
        i -= 1;
      }
    }
  }
  
  public static abstract interface RecyclerListener
  {
    public abstract void onViewRecycled(RecyclerView.ViewHolder paramViewHolder);
  }
  
  private class RecyclerViewDataObserver
    extends RecyclerView.AdapterDataObserver
  {
    RecyclerViewDataObserver() {}
    
    public void onChanged()
    {
      assertNotInLayoutOrScroll(null);
      mState.mStructureChanged = true;
      processDataSetCompletelyChanged(true);
      if (!mAdapterHelper.hasPendingUpdates()) {
        requestLayout();
      }
    }
    
    public void onItemRangeChanged(int paramInt1, int paramInt2, Object paramObject)
    {
      assertNotInLayoutOrScroll(null);
      if (mAdapterHelper.onItemRangeChanged(paramInt1, paramInt2, paramObject)) {
        triggerUpdateProcessor();
      }
    }
    
    public void onItemRangeInserted(int paramInt1, int paramInt2)
    {
      assertNotInLayoutOrScroll(null);
      if (mAdapterHelper.onItemRangeInserted(paramInt1, paramInt2)) {
        triggerUpdateProcessor();
      }
    }
    
    public void onItemRangeMoved(int paramInt1, int paramInt2, int paramInt3)
    {
      assertNotInLayoutOrScroll(null);
      if (mAdapterHelper.onItemRangeMoved(paramInt1, paramInt2, paramInt3)) {
        triggerUpdateProcessor();
      }
    }
    
    public void onItemRangeRemoved(int paramInt1, int paramInt2)
    {
      assertNotInLayoutOrScroll(null);
      if (mAdapterHelper.onItemRangeRemoved(paramInt1, paramInt2)) {
        triggerUpdateProcessor();
      }
    }
    
    void triggerUpdateProcessor()
    {
      if ((RecyclerView.POST_UPDATES_ON_ANIMATION) && (mHasFixedSize) && (mIsAttached))
      {
        localRecyclerView = RecyclerView.this;
        ViewCompat.postOnAnimation(localRecyclerView, mUpdateChildViewsRunnable);
        return;
      }
      RecyclerView localRecyclerView = RecyclerView.this;
      mAdapterUpdateDuringMeasure = true;
      localRecyclerView.requestLayout();
    }
  }
  
  public static class SavedState
    extends AbsSavedState
  {
    public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator()
    {
      public RecyclerView.SavedState createFromParcel(Parcel paramAnonymousParcel)
      {
        return new RecyclerView.SavedState(paramAnonymousParcel, null);
      }
      
      public RecyclerView.SavedState createFromParcel(Parcel paramAnonymousParcel, ClassLoader paramAnonymousClassLoader)
      {
        return new RecyclerView.SavedState(paramAnonymousParcel, paramAnonymousClassLoader);
      }
      
      public RecyclerView.SavedState[] newArray(int paramAnonymousInt)
      {
        return new RecyclerView.SavedState[paramAnonymousInt];
      }
    };
    Parcelable mLayoutState;
    
    SavedState(Parcel paramParcel, ClassLoader paramClassLoader)
    {
      super(paramClassLoader);
      if (paramClassLoader == null) {
        paramClassLoader = RecyclerView.LayoutManager.class.getClassLoader();
      }
      mLayoutState = paramParcel.readParcelable(paramClassLoader);
    }
    
    SavedState(Parcelable paramParcelable)
    {
      super();
    }
    
    void copyFrom(SavedState paramSavedState)
    {
      mLayoutState = mLayoutState;
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      super.writeToParcel(paramParcel, paramInt);
      paramParcel.writeParcelable(mLayoutState, 0);
    }
  }
  
  public static class SimpleOnItemTouchListener
    implements RecyclerView.OnItemTouchListener
  {
    public SimpleOnItemTouchListener() {}
    
    public boolean onInterceptTouchEvent(RecyclerView paramRecyclerView, MotionEvent paramMotionEvent)
    {
      return false;
    }
    
    public void onRequestDisallowInterceptTouchEvent(boolean paramBoolean) {}
    
    public void onTouchEvent(RecyclerView paramRecyclerView, MotionEvent paramMotionEvent) {}
  }
  
  public static abstract class SmoothScroller
  {
    private RecyclerView.LayoutManager mLayoutManager;
    private boolean mPendingInitialRun;
    private RecyclerView mRecyclerView;
    private final Action mRecyclingAction = new Action(0, 0);
    private boolean mRunning;
    private boolean mStarted;
    private int mTargetPosition = -1;
    private View mTargetView;
    
    public SmoothScroller() {}
    
    public PointF computeScrollVectorForPosition(int paramInt)
    {
      Object localObject = getLayoutManager();
      if ((localObject instanceof ScrollVectorProvider)) {
        return ((ScrollVectorProvider)localObject).computeScrollVectorForPosition(paramInt);
      }
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("You should override computeScrollVectorForPosition when the LayoutManager does not implement ");
      ((StringBuilder)localObject).append(ScrollVectorProvider.class.getCanonicalName());
      Log.w("RecyclerView", ((StringBuilder)localObject).toString());
      return null;
    }
    
    public View findViewByPosition(int paramInt)
    {
      return mRecyclerView.mLayout.findViewByPosition(paramInt);
    }
    
    public int getChildCount()
    {
      return mRecyclerView.mLayout.getChildCount();
    }
    
    public int getChildPosition(View paramView)
    {
      return mRecyclerView.getChildLayoutPosition(paramView);
    }
    
    public RecyclerView.LayoutManager getLayoutManager()
    {
      return mLayoutManager;
    }
    
    public int getTargetPosition()
    {
      return mTargetPosition;
    }
    
    public void instantScrollToPosition(int paramInt)
    {
      mRecyclerView.scrollToPosition(paramInt);
    }
    
    public boolean isPendingInitialRun()
    {
      return mPendingInitialRun;
    }
    
    public boolean isRunning()
    {
      return mRunning;
    }
    
    protected void normalize(PointF paramPointF)
    {
      float f = (float)Math.sqrt(x * x + y * y);
      x /= f;
      y /= f;
    }
    
    void onAnimation(int paramInt1, int paramInt2)
    {
      RecyclerView localRecyclerView = mRecyclerView;
      if ((!mRunning) || (mTargetPosition == -1) || (localRecyclerView == null)) {
        stop();
      }
      if ((mPendingInitialRun) && (mTargetView == null) && (mLayoutManager != null))
      {
        localObject = computeScrollVectorForPosition(mTargetPosition);
        if ((localObject != null) && ((x != 0.0F) || (y != 0.0F))) {
          localRecyclerView.scrollStep((int)Math.signum(x), (int)Math.signum(y), null);
        }
      }
      mPendingInitialRun = false;
      Object localObject = mTargetView;
      if (localObject != null) {
        if (getChildPosition((View)localObject) == mTargetPosition)
        {
          onTargetFound(mTargetView, mState, mRecyclingAction);
          mRecyclingAction.runIfNecessary(localRecyclerView);
          stop();
        }
        else
        {
          Log.e("RecyclerView", "Passed over target position while smooth scrolling.");
          mTargetView = null;
        }
      }
      if (mRunning)
      {
        onSeekTargetStep(paramInt1, paramInt2, mState, mRecyclingAction);
        boolean bool = mRecyclingAction.hasJumpTarget();
        mRecyclingAction.runIfNecessary(localRecyclerView);
        if (bool)
        {
          if (mRunning)
          {
            mPendingInitialRun = true;
            mViewFlinger.postOnAnimation();
            return;
          }
          stop();
        }
      }
    }
    
    protected void onChildAttachedToWindow(View paramView)
    {
      if (getChildPosition(paramView) == getTargetPosition()) {
        mTargetView = paramView;
      }
    }
    
    protected abstract void onSeekTargetStep(int paramInt1, int paramInt2, RecyclerView.State paramState, Action paramAction);
    
    protected abstract void onStart();
    
    protected abstract void onStop();
    
    protected abstract void onTargetFound(View paramView, RecyclerView.State paramState, Action paramAction);
    
    public void setTargetPosition(int paramInt)
    {
      mTargetPosition = paramInt;
    }
    
    void start(RecyclerView paramRecyclerView, RecyclerView.LayoutManager paramLayoutManager)
    {
      if (mStarted)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("An instance of ");
        localStringBuilder.append(getClass().getSimpleName());
        localStringBuilder.append(" was started ");
        localStringBuilder.append("more than once. Each instance of");
        localStringBuilder.append(getClass().getSimpleName());
        localStringBuilder.append(" ");
        localStringBuilder.append("is intended to only be used once. You should create a new instance for ");
        localStringBuilder.append("each use.");
        Log.w("RecyclerView", localStringBuilder.toString());
      }
      mRecyclerView = paramRecyclerView;
      mLayoutManager = paramLayoutManager;
      if (mTargetPosition != -1)
      {
        mRecyclerView.mState.mTargetPosition = mTargetPosition;
        mRunning = true;
        mPendingInitialRun = true;
        mTargetView = findViewByPosition(getTargetPosition());
        onStart();
        mRecyclerView.mViewFlinger.postOnAnimation();
        mStarted = true;
        return;
      }
      throw new IllegalArgumentException("Invalid target position");
    }
    
    protected final void stop()
    {
      if (!mRunning) {
        return;
      }
      mRunning = false;
      onStop();
      mRecyclerView.mState.mTargetPosition = -1;
      mTargetView = null;
      mTargetPosition = -1;
      mPendingInitialRun = false;
      mLayoutManager.onSmoothScrollerStopped(this);
      mLayoutManager = null;
      mRecyclerView = null;
    }
    
    public static class Action
    {
      public static final int UNDEFINED_DURATION = Integer.MIN_VALUE;
      private boolean mChanged = false;
      private int mConsecutiveUpdates = 0;
      private int mDuration;
      private int mDx;
      private int mDy;
      private Interpolator mInterpolator;
      private int mJumpToPosition = -1;
      
      public Action(int paramInt1, int paramInt2)
      {
        this(paramInt1, paramInt2, Integer.MIN_VALUE, null);
      }
      
      public Action(int paramInt1, int paramInt2, int paramInt3)
      {
        this(paramInt1, paramInt2, paramInt3, null);
      }
      
      public Action(int paramInt1, int paramInt2, int paramInt3, Interpolator paramInterpolator)
      {
        mDx = paramInt1;
        mDy = paramInt2;
        mDuration = paramInt3;
        mInterpolator = paramInterpolator;
      }
      
      private void validate()
      {
        if ((mInterpolator != null) && (mDuration < 1)) {
          throw new IllegalStateException("If you provide an interpolator, you must set a positive duration");
        }
        if (mDuration >= 1) {
          return;
        }
        throw new IllegalStateException("Scroll duration must be a positive number");
      }
      
      public int getDuration()
      {
        return mDuration;
      }
      
      public int getDx()
      {
        return mDx;
      }
      
      public int getDy()
      {
        return mDy;
      }
      
      public Interpolator getInterpolator()
      {
        return mInterpolator;
      }
      
      boolean hasJumpTarget()
      {
        return mJumpToPosition >= 0;
      }
      
      public void jumpTo(int paramInt)
      {
        mJumpToPosition = paramInt;
      }
      
      void runIfNecessary(RecyclerView paramRecyclerView)
      {
        int i = mJumpToPosition;
        if (i >= 0)
        {
          mJumpToPosition = -1;
          paramRecyclerView.jumpToPositionForSmoothScroller(i);
          mChanged = false;
          return;
        }
        if (mChanged)
        {
          validate();
          if (mInterpolator == null)
          {
            if (mDuration == Integer.MIN_VALUE) {
              mViewFlinger.smoothScrollBy(mDx, mDy);
            } else {
              mViewFlinger.smoothScrollBy(mDx, mDy, mDuration);
            }
          }
          else {
            mViewFlinger.smoothScrollBy(mDx, mDy, mDuration, mInterpolator);
          }
          mConsecutiveUpdates += 1;
          if (mConsecutiveUpdates > 10) {
            Log.e("RecyclerView", "Smooth Scroll action is being updated too frequently. Make sure you are not changing it unless necessary");
          }
          mChanged = false;
          return;
        }
        mConsecutiveUpdates = 0;
      }
      
      public void setDuration(int paramInt)
      {
        mChanged = true;
        mDuration = paramInt;
      }
      
      public void setDx(int paramInt)
      {
        mChanged = true;
        mDx = paramInt;
      }
      
      public void setDy(int paramInt)
      {
        mChanged = true;
        mDy = paramInt;
      }
      
      public void setInterpolator(Interpolator paramInterpolator)
      {
        mChanged = true;
        mInterpolator = paramInterpolator;
      }
      
      public void update(int paramInt1, int paramInt2, int paramInt3, Interpolator paramInterpolator)
      {
        mDx = paramInt1;
        mDy = paramInt2;
        mDuration = paramInt3;
        mInterpolator = paramInterpolator;
        mChanged = true;
      }
    }
    
    public static abstract interface ScrollVectorProvider
    {
      public abstract PointF computeScrollVectorForPosition(int paramInt);
    }
  }
  
  public static class State
  {
    static final int STEP_ANIMATIONS = 4;
    static final int STEP_LAYOUT = 2;
    static final int STEP_START = 1;
    private SparseArray<Object> mData;
    int mDeletedInvisibleItemCountSincePreviousLayout = 0;
    long mFocusedItemId;
    int mFocusedItemPosition;
    int mFocusedSubChildId;
    boolean mInPreLayout = false;
    boolean mIsMeasuring = false;
    int mItemCount = 0;
    int mLayoutStep = 1;
    int mPreviousLayoutItemCount = 0;
    int mRemainingScrollHorizontal;
    int mRemainingScrollVertical;
    boolean mRunPredictiveAnimations = false;
    boolean mRunSimpleAnimations = false;
    boolean mStructureChanged = false;
    int mTargetPosition = -1;
    boolean mTrackOldChangeHolders = false;
    
    public State() {}
    
    void assertLayoutStep(int paramInt)
    {
      if ((mLayoutStep & paramInt) != 0) {
        return;
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Layout state should be one of ");
      localStringBuilder.append(Integer.toBinaryString(paramInt));
      localStringBuilder.append(" but it is ");
      localStringBuilder.append(Integer.toBinaryString(mLayoutStep));
      throw new IllegalStateException(localStringBuilder.toString());
    }
    
    public boolean didStructureChange()
    {
      return mStructureChanged;
    }
    
    public Object get(int paramInt)
    {
      SparseArray localSparseArray = mData;
      if (localSparseArray == null) {
        return null;
      }
      return localSparseArray.get(paramInt);
    }
    
    public int getItemCount()
    {
      if (mInPreLayout) {
        return mPreviousLayoutItemCount - mDeletedInvisibleItemCountSincePreviousLayout;
      }
      return mItemCount;
    }
    
    public int getRemainingScrollHorizontal()
    {
      return mRemainingScrollHorizontal;
    }
    
    public int getRemainingScrollVertical()
    {
      return mRemainingScrollVertical;
    }
    
    public int getTargetScrollPosition()
    {
      return mTargetPosition;
    }
    
    public boolean hasTargetScrollPosition()
    {
      return mTargetPosition != -1;
    }
    
    public boolean isMeasuring()
    {
      return mIsMeasuring;
    }
    
    public boolean isPreLayout()
    {
      return mInPreLayout;
    }
    
    void prepareForNestedPrefetch(RecyclerView.Adapter paramAdapter)
    {
      mLayoutStep = 1;
      mItemCount = paramAdapter.getItemCount();
      mInPreLayout = false;
      mTrackOldChangeHolders = false;
      mIsMeasuring = false;
    }
    
    public void put(int paramInt, Object paramObject)
    {
      if (mData == null) {
        mData = new SparseArray();
      }
      mData.put(paramInt, paramObject);
    }
    
    public void remove(int paramInt)
    {
      SparseArray localSparseArray = mData;
      if (localSparseArray == null) {
        return;
      }
      localSparseArray.remove(paramInt);
    }
    
    State reset()
    {
      mTargetPosition = -1;
      SparseArray localSparseArray = mData;
      if (localSparseArray != null) {
        localSparseArray.clear();
      }
      mItemCount = 0;
      mStructureChanged = false;
      mIsMeasuring = false;
      return this;
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("State{mTargetPosition=");
      localStringBuilder.append(mTargetPosition);
      localStringBuilder.append(", mData=");
      localStringBuilder.append(mData);
      localStringBuilder.append(", mItemCount=");
      localStringBuilder.append(mItemCount);
      localStringBuilder.append(", mIsMeasuring=");
      localStringBuilder.append(mIsMeasuring);
      localStringBuilder.append(", mPreviousLayoutItemCount=");
      localStringBuilder.append(mPreviousLayoutItemCount);
      localStringBuilder.append(", mDeletedInvisibleItemCountSincePreviousLayout=");
      localStringBuilder.append(mDeletedInvisibleItemCountSincePreviousLayout);
      localStringBuilder.append(", mStructureChanged=");
      localStringBuilder.append(mStructureChanged);
      localStringBuilder.append(", mInPreLayout=");
      localStringBuilder.append(mInPreLayout);
      localStringBuilder.append(", mRunSimpleAnimations=");
      localStringBuilder.append(mRunSimpleAnimations);
      localStringBuilder.append(", mRunPredictiveAnimations=");
      localStringBuilder.append(mRunPredictiveAnimations);
      localStringBuilder.append('}');
      return localStringBuilder.toString();
    }
    
    public boolean willRunPredictiveAnimations()
    {
      return mRunPredictiveAnimations;
    }
    
    public boolean willRunSimpleAnimations()
    {
      return mRunSimpleAnimations;
    }
  }
  
  public static abstract class ViewCacheExtension
  {
    public ViewCacheExtension() {}
    
    public abstract View getViewForPositionAndType(RecyclerView.Recycler paramRecycler, int paramInt1, int paramInt2);
  }
  
  class ViewFlinger
    implements Runnable
  {
    private boolean mEatRunOnAnimationRequest = false;
    Interpolator mInterpolator = RecyclerView.sQuinticInterpolator;
    private int mLastFlingX;
    private int mLastFlingY;
    private boolean mReSchedulePostAnimationCallback = false;
    OverScroller mScroller = new OverScroller(getContext(), RecyclerView.sQuinticInterpolator);
    
    ViewFlinger() {}
    
    private int computeScrollDuration(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      int j = Math.abs(paramInt1);
      int i = j;
      int k = Math.abs(paramInt2);
      if (j > k) {
        j = 1;
      } else {
        j = 0;
      }
      paramInt3 = (int)Math.sqrt(paramInt3 * paramInt3 + paramInt4 * paramInt4);
      paramInt2 = (int)Math.sqrt(paramInt1 * paramInt1 + paramInt2 * paramInt2);
      RecyclerView localRecyclerView = RecyclerView.this;
      if (j != 0) {
        paramInt1 = localRecyclerView.getWidth();
      } else {
        paramInt1 = localRecyclerView.getHeight();
      }
      paramInt4 = paramInt1 / 2;
      float f2 = paramInt2;
      float f1 = paramInt1;
      float f3 = Math.min(1.0F, f2 * 1.0F / f1);
      f2 = paramInt4;
      f3 = distanceInfluenceForSnapDuration(f3);
      if (paramInt3 > 0)
      {
        paramInt1 = Math.round(Math.abs((f2 + f3 * f2) / paramInt3) * 1000.0F) * 4;
      }
      else
      {
        if (j != 0) {
          paramInt1 = i;
        } else {
          paramInt1 = k;
        }
        paramInt1 = (int)((paramInt1 / f1 + 1.0F) * 300.0F);
      }
      return Math.min(paramInt1, 2000);
    }
    
    private void disableRunOnAnimationRequests()
    {
      mReSchedulePostAnimationCallback = false;
      mEatRunOnAnimationRequest = true;
    }
    
    private float distanceInfluenceForSnapDuration(float paramFloat)
    {
      return (float)Math.sin((paramFloat - 0.5F) * 0.47123894F);
    }
    
    private void enableRunOnAnimationRequests()
    {
      mEatRunOnAnimationRequest = false;
      if (mReSchedulePostAnimationCallback) {
        postOnAnimation();
      }
    }
    
    public void fling(int paramInt1, int paramInt2)
    {
      setScrollState(2);
      mLastFlingY = 0;
      mLastFlingX = 0;
      mScroller.fling(0, 0, paramInt1, paramInt2, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE);
      postOnAnimation();
    }
    
    void postOnAnimation()
    {
      if (mEatRunOnAnimationRequest)
      {
        mReSchedulePostAnimationCallback = true;
        return;
      }
      removeCallbacks(this);
      ViewCompat.postOnAnimation(RecyclerView.this, this);
    }
    
    public void run()
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 25	androidx/recyclerview/widget/RecyclerView$ViewFlinger:this$0	Landroidx/recyclerview/widget/RecyclerView;
      //   4: getfield 128	androidx/recyclerview/widget/RecyclerView:mLayout	Landroidx/recyclerview/widget/RecyclerView$LayoutManager;
      //   7: ifnonnull +8 -> 15
      //   10: aload_0
      //   11: invokevirtual 131	androidx/recyclerview/widget/RecyclerView$ViewFlinger:stop	()V
      //   14: return
      //   15: aload_0
      //   16: invokespecial 133	androidx/recyclerview/widget/RecyclerView$ViewFlinger:disableRunOnAnimationRequests	()V
      //   19: aload_0
      //   20: getfield 25	androidx/recyclerview/widget/RecyclerView$ViewFlinger:this$0	Landroidx/recyclerview/widget/RecyclerView;
      //   23: invokevirtual 136	androidx/recyclerview/widget/RecyclerView:consumePendingUpdateOperations	()V
      //   26: aload_0
      //   27: getfield 50	androidx/recyclerview/widget/RecyclerView$ViewFlinger:mScroller	Landroid/widget/OverScroller;
      //   30: astore 13
      //   32: aload_0
      //   33: getfield 25	androidx/recyclerview/widget/RecyclerView$ViewFlinger:this$0	Landroidx/recyclerview/widget/RecyclerView;
      //   36: getfield 128	androidx/recyclerview/widget/RecyclerView:mLayout	Landroidx/recyclerview/widget/RecyclerView$LayoutManager;
      //   39: getfield 142	androidx/recyclerview/widget/RecyclerView$LayoutManager:mSmoothScroller	Landroidx/recyclerview/widget/RecyclerView$SmoothScroller;
      //   42: astore 14
      //   44: aload 13
      //   46: invokevirtual 146	android/widget/OverScroller:computeScrollOffset	()Z
      //   49: ifeq +763 -> 812
      //   52: aload_0
      //   53: getfield 25	androidx/recyclerview/widget/RecyclerView$ViewFlinger:this$0	Landroidx/recyclerview/widget/RecyclerView;
      //   56: getfield 150	androidx/recyclerview/widget/RecyclerView:mScrollConsumed	[I
      //   59: astore 15
      //   61: aload 13
      //   63: invokevirtual 153	android/widget/OverScroller:getCurrX	()I
      //   66: istore 11
      //   68: aload 13
      //   70: invokevirtual 156	android/widget/OverScroller:getCurrY	()I
      //   73: istore 12
      //   75: iload 11
      //   77: aload_0
      //   78: getfield 109	androidx/recyclerview/widget/RecyclerView$ViewFlinger:mLastFlingX	I
      //   81: isub
      //   82: istore_2
      //   83: iload 12
      //   85: aload_0
      //   86: getfield 107	androidx/recyclerview/widget/RecyclerView$ViewFlinger:mLastFlingY	I
      //   89: isub
      //   90: istore_1
      //   91: aload_0
      //   92: iload 11
      //   94: putfield 109	androidx/recyclerview/widget/RecyclerView$ViewFlinger:mLastFlingX	I
      //   97: aload_0
      //   98: iload 12
      //   100: putfield 107	androidx/recyclerview/widget/RecyclerView$ViewFlinger:mLastFlingY	I
      //   103: iload_2
      //   104: istore 4
      //   106: iload_1
      //   107: istore_3
      //   108: aload_0
      //   109: getfield 25	androidx/recyclerview/widget/RecyclerView$ViewFlinger:this$0	Landroidx/recyclerview/widget/RecyclerView;
      //   112: iload_2
      //   113: iload_1
      //   114: aload 15
      //   116: aconst_null
      //   117: iconst_1
      //   118: invokevirtual 160	androidx/recyclerview/widget/RecyclerView:dispatchNestedPreScroll	(II[I[II)Z
      //   121: ifeq +18 -> 139
      //   124: iload_2
      //   125: aload 15
      //   127: iconst_0
      //   128: iaload
      //   129: isub
      //   130: istore 4
      //   132: iload_1
      //   133: aload 15
      //   135: iconst_1
      //   136: iaload
      //   137: isub
      //   138: istore_3
      //   139: aload_0
      //   140: getfield 25	androidx/recyclerview/widget/RecyclerView$ViewFlinger:this$0	Landroidx/recyclerview/widget/RecyclerView;
      //   143: getfield 164	androidx/recyclerview/widget/RecyclerView:mAdapter	Landroidx/recyclerview/widget/RecyclerView$Adapter;
      //   146: ifnull +236 -> 382
      //   149: aload_0
      //   150: getfield 25	androidx/recyclerview/widget/RecyclerView$ViewFlinger:this$0	Landroidx/recyclerview/widget/RecyclerView;
      //   153: astore 15
      //   155: aload 15
      //   157: iload 4
      //   159: iload_3
      //   160: aload 15
      //   162: getfield 167	androidx/recyclerview/widget/RecyclerView:mScrollStepConsumed	[I
      //   165: invokevirtual 171	androidx/recyclerview/widget/RecyclerView:scrollStep	(II[I)V
      //   168: aload_0
      //   169: getfield 25	androidx/recyclerview/widget/RecyclerView$ViewFlinger:this$0	Landroidx/recyclerview/widget/RecyclerView;
      //   172: getfield 167	androidx/recyclerview/widget/RecyclerView:mScrollStepConsumed	[I
      //   175: iconst_0
      //   176: iaload
      //   177: istore_1
      //   178: aload_0
      //   179: getfield 25	androidx/recyclerview/widget/RecyclerView$ViewFlinger:this$0	Landroidx/recyclerview/widget/RecyclerView;
      //   182: getfield 167	androidx/recyclerview/widget/RecyclerView:mScrollStepConsumed	[I
      //   185: iconst_1
      //   186: iaload
      //   187: istore_2
      //   188: iload 4
      //   190: iload_1
      //   191: isub
      //   192: istore 9
      //   194: iload_3
      //   195: iload_2
      //   196: isub
      //   197: istore 10
      //   199: iload_1
      //   200: istore 5
      //   202: iload_2
      //   203: istore 8
      //   205: iload 9
      //   207: istore 7
      //   209: iload 10
      //   211: istore 6
      //   213: aload 14
      //   215: ifnull +179 -> 394
      //   218: iload_1
      //   219: istore 5
      //   221: iload_2
      //   222: istore 8
      //   224: iload 9
      //   226: istore 7
      //   228: iload 10
      //   230: istore 6
      //   232: aload 14
      //   234: invokevirtual 176	androidx/recyclerview/widget/RecyclerView$SmoothScroller:isPendingInitialRun	()Z
      //   237: ifne +157 -> 394
      //   240: iload_1
      //   241: istore 5
      //   243: iload_2
      //   244: istore 8
      //   246: iload 9
      //   248: istore 7
      //   250: iload 10
      //   252: istore 6
      //   254: aload 14
      //   256: invokevirtual 179	androidx/recyclerview/widget/RecyclerView$SmoothScroller:isRunning	()Z
      //   259: ifeq +135 -> 394
      //   262: aload_0
      //   263: getfield 25	androidx/recyclerview/widget/RecyclerView$ViewFlinger:this$0	Landroidx/recyclerview/widget/RecyclerView;
      //   266: getfield 183	androidx/recyclerview/widget/RecyclerView:mState	Landroidx/recyclerview/widget/RecyclerView$State;
      //   269: invokevirtual 188	androidx/recyclerview/widget/RecyclerView$State:getItemCount	()I
      //   272: istore 5
      //   274: iload 5
      //   276: ifne +25 -> 301
      //   279: aload 14
      //   281: invokevirtual 189	androidx/recyclerview/widget/RecyclerView$SmoothScroller:stop	()V
      //   284: iload_1
      //   285: istore 5
      //   287: iload_2
      //   288: istore 8
      //   290: iload 9
      //   292: istore 7
      //   294: iload 10
      //   296: istore 6
      //   298: goto +96 -> 394
      //   301: aload 14
      //   303: invokevirtual 192	androidx/recyclerview/widget/RecyclerView$SmoothScroller:getTargetPosition	()I
      //   306: iload 5
      //   308: if_icmplt +43 -> 351
      //   311: aload 14
      //   313: iload 5
      //   315: iconst_1
      //   316: isub
      //   317: invokevirtual 195	androidx/recyclerview/widget/RecyclerView$SmoothScroller:setTargetPosition	(I)V
      //   320: aload 14
      //   322: iload 4
      //   324: iload 9
      //   326: isub
      //   327: iload_3
      //   328: iload 10
      //   330: isub
      //   331: invokevirtual 198	androidx/recyclerview/widget/RecyclerView$SmoothScroller:onAnimation	(II)V
      //   334: iload_1
      //   335: istore 5
      //   337: iload_2
      //   338: istore 8
      //   340: iload 9
      //   342: istore 7
      //   344: iload 10
      //   346: istore 6
      //   348: goto +46 -> 394
      //   351: aload 14
      //   353: iload 4
      //   355: iload 9
      //   357: isub
      //   358: iload_3
      //   359: iload 10
      //   361: isub
      //   362: invokevirtual 198	androidx/recyclerview/widget/RecyclerView$SmoothScroller:onAnimation	(II)V
      //   365: iload_1
      //   366: istore 5
      //   368: iload_2
      //   369: istore 8
      //   371: iload 9
      //   373: istore 7
      //   375: iload 10
      //   377: istore 6
      //   379: goto +15 -> 394
      //   382: iconst_0
      //   383: istore 5
      //   385: iconst_0
      //   386: istore 8
      //   388: iconst_0
      //   389: istore 7
      //   391: iconst_0
      //   392: istore 6
      //   394: aload_0
      //   395: getfield 25	androidx/recyclerview/widget/RecyclerView$ViewFlinger:this$0	Landroidx/recyclerview/widget/RecyclerView;
      //   398: getfield 202	androidx/recyclerview/widget/RecyclerView:mItemDecorations	Ljava/util/ArrayList;
      //   401: invokevirtual 207	java/util/ArrayList:isEmpty	()Z
      //   404: ifne +10 -> 414
      //   407: aload_0
      //   408: getfield 25	androidx/recyclerview/widget/RecyclerView$ViewFlinger:this$0	Landroidx/recyclerview/widget/RecyclerView;
      //   411: invokevirtual 210	android/view/View:invalidate	()V
      //   414: aload_0
      //   415: getfield 25	androidx/recyclerview/widget/RecyclerView$ViewFlinger:this$0	Landroidx/recyclerview/widget/RecyclerView;
      //   418: invokevirtual 213	android/view/View:getOverScrollMode	()I
      //   421: iconst_2
      //   422: if_icmpeq +13 -> 435
      //   425: aload_0
      //   426: getfield 25	androidx/recyclerview/widget/RecyclerView$ViewFlinger:this$0	Landroidx/recyclerview/widget/RecyclerView;
      //   429: iload 4
      //   431: iload_3
      //   432: invokevirtual 216	androidx/recyclerview/widget/RecyclerView:considerReleasingGlowsOnScroll	(II)V
      //   435: aload_0
      //   436: getfield 25	androidx/recyclerview/widget/RecyclerView$ViewFlinger:this$0	Landroidx/recyclerview/widget/RecyclerView;
      //   439: iload 5
      //   441: iload 8
      //   443: iload 7
      //   445: iload 6
      //   447: aconst_null
      //   448: iconst_1
      //   449: invokevirtual 220	androidx/recyclerview/widget/RecyclerView:dispatchNestedScroll	(IIII[II)Z
      //   452: ifne +141 -> 593
      //   455: iload 7
      //   457: ifne +8 -> 465
      //   460: iload 6
      //   462: ifeq +131 -> 593
      //   465: aload 13
      //   467: invokevirtual 224	android/widget/OverScroller:getCurrVelocity	()F
      //   470: f2i
      //   471: istore_2
      //   472: iload 7
      //   474: iload 11
      //   476: if_icmpeq +24 -> 500
      //   479: iload 7
      //   481: ifge +9 -> 490
      //   484: iload_2
      //   485: ineg
      //   486: istore_1
      //   487: goto +15 -> 502
      //   490: iload 7
      //   492: ifle +8 -> 500
      //   495: iload_2
      //   496: istore_1
      //   497: goto +5 -> 502
      //   500: iconst_0
      //   501: istore_1
      //   502: iload 6
      //   504: iload 12
      //   506: if_icmpeq +22 -> 528
      //   509: iload 6
      //   511: ifge +9 -> 520
      //   514: iload_2
      //   515: ineg
      //   516: istore_2
      //   517: goto +13 -> 530
      //   520: iload 6
      //   522: ifle +6 -> 528
      //   525: goto +5 -> 530
      //   528: iconst_0
      //   529: istore_2
      //   530: aload_0
      //   531: getfield 25	androidx/recyclerview/widget/RecyclerView$ViewFlinger:this$0	Landroidx/recyclerview/widget/RecyclerView;
      //   534: invokevirtual 213	android/view/View:getOverScrollMode	()I
      //   537: iconst_2
      //   538: if_icmpeq +12 -> 550
      //   541: aload_0
      //   542: getfield 25	androidx/recyclerview/widget/RecyclerView$ViewFlinger:this$0	Landroidx/recyclerview/widget/RecyclerView;
      //   545: iload_1
      //   546: iload_2
      //   547: invokevirtual 227	androidx/recyclerview/widget/RecyclerView:absorbGlows	(II)V
      //   550: iload_1
      //   551: ifne +18 -> 569
      //   554: iload 7
      //   556: iload 11
      //   558: if_icmpeq +11 -> 569
      //   561: aload 13
      //   563: invokevirtual 230	android/widget/OverScroller:getFinalX	()I
      //   566: ifne +27 -> 593
      //   569: iload_2
      //   570: ifne +18 -> 588
      //   573: iload 6
      //   575: iload 12
      //   577: if_icmpeq +11 -> 588
      //   580: aload 13
      //   582: invokevirtual 233	android/widget/OverScroller:getFinalY	()I
      //   585: ifne +8 -> 593
      //   588: aload 13
      //   590: invokevirtual 236	android/widget/OverScroller:abortAnimation	()V
      //   593: iload 5
      //   595: ifne +8 -> 603
      //   598: iload 8
      //   600: ifeq +14 -> 614
      //   603: aload_0
      //   604: getfield 25	androidx/recyclerview/widget/RecyclerView$ViewFlinger:this$0	Landroidx/recyclerview/widget/RecyclerView;
      //   607: iload 5
      //   609: iload 8
      //   611: invokevirtual 239	androidx/recyclerview/widget/RecyclerView:dispatchOnScrolled	(II)V
      //   614: aload_0
      //   615: getfield 25	androidx/recyclerview/widget/RecyclerView$ViewFlinger:this$0	Landroidx/recyclerview/widget/RecyclerView;
      //   618: invokestatic 243	androidx/recyclerview/widget/RecyclerView:access$200	(Landroidx/recyclerview/widget/RecyclerView;)Z
      //   621: ifne +10 -> 631
      //   624: aload_0
      //   625: getfield 25	androidx/recyclerview/widget/RecyclerView$ViewFlinger:this$0	Landroidx/recyclerview/widget/RecyclerView;
      //   628: invokevirtual 210	android/view/View:invalidate	()V
      //   631: iload_3
      //   632: ifeq +27 -> 659
      //   635: aload_0
      //   636: getfield 25	androidx/recyclerview/widget/RecyclerView$ViewFlinger:this$0	Landroidx/recyclerview/widget/RecyclerView;
      //   639: getfield 128	androidx/recyclerview/widget/RecyclerView:mLayout	Landroidx/recyclerview/widget/RecyclerView$LayoutManager;
      //   642: invokevirtual 246	androidx/recyclerview/widget/RecyclerView$LayoutManager:canScrollVertically	()Z
      //   645: ifeq +14 -> 659
      //   648: iload 8
      //   650: iload_3
      //   651: if_icmpne +8 -> 659
      //   654: iconst_1
      //   655: istore_1
      //   656: goto +5 -> 661
      //   659: iconst_0
      //   660: istore_1
      //   661: iload 4
      //   663: ifeq +28 -> 691
      //   666: aload_0
      //   667: getfield 25	androidx/recyclerview/widget/RecyclerView$ViewFlinger:this$0	Landroidx/recyclerview/widget/RecyclerView;
      //   670: getfield 128	androidx/recyclerview/widget/RecyclerView:mLayout	Landroidx/recyclerview/widget/RecyclerView$LayoutManager;
      //   673: invokevirtual 249	androidx/recyclerview/widget/RecyclerView$LayoutManager:canScrollHorizontally	()Z
      //   676: ifeq +15 -> 691
      //   679: iload 5
      //   681: iload 4
      //   683: if_icmpne +8 -> 691
      //   686: iconst_1
      //   687: istore_2
      //   688: goto +5 -> 693
      //   691: iconst_0
      //   692: istore_2
      //   693: iload 4
      //   695: ifne +7 -> 702
      //   698: iload_3
      //   699: ifeq +19 -> 718
      //   702: iload_2
      //   703: ifne +15 -> 718
      //   706: iload_1
      //   707: ifeq +6 -> 713
      //   710: goto +8 -> 718
      //   713: iconst_0
      //   714: istore_1
      //   715: goto +5 -> 720
      //   718: iconst_1
      //   719: istore_1
      //   720: aload 13
      //   722: invokevirtual 252	android/widget/OverScroller:isFinished	()Z
      //   725: ifne +55 -> 780
      //   728: iload_1
      //   729: ifne +17 -> 746
      //   732: aload_0
      //   733: getfield 25	androidx/recyclerview/widget/RecyclerView$ViewFlinger:this$0	Landroidx/recyclerview/widget/RecyclerView;
      //   736: iconst_1
      //   737: invokevirtual 256	androidx/recyclerview/widget/RecyclerView:hasNestedScrollingParent	(I)Z
      //   740: ifne +6 -> 746
      //   743: goto +37 -> 780
      //   746: aload_0
      //   747: invokevirtual 99	androidx/recyclerview/widget/RecyclerView$ViewFlinger:postOnAnimation	()V
      //   750: aload_0
      //   751: getfield 25	androidx/recyclerview/widget/RecyclerView$ViewFlinger:this$0	Landroidx/recyclerview/widget/RecyclerView;
      //   754: getfield 260	androidx/recyclerview/widget/RecyclerView:mGapWorker	Landroidx/recyclerview/widget/GapWorker;
      //   757: ifnull +55 -> 812
      //   760: aload_0
      //   761: getfield 25	androidx/recyclerview/widget/RecyclerView$ViewFlinger:this$0	Landroidx/recyclerview/widget/RecyclerView;
      //   764: getfield 260	androidx/recyclerview/widget/RecyclerView:mGapWorker	Landroidx/recyclerview/widget/GapWorker;
      //   767: aload_0
      //   768: getfield 25	androidx/recyclerview/widget/RecyclerView$ViewFlinger:this$0	Landroidx/recyclerview/widget/RecyclerView;
      //   771: iload 4
      //   773: iload_3
      //   774: invokevirtual 266	androidx/recyclerview/widget/GapWorker:postFromTraversal	(Landroidx/recyclerview/widget/RecyclerView;II)V
      //   777: goto +35 -> 812
      //   780: aload_0
      //   781: getfield 25	androidx/recyclerview/widget/RecyclerView$ViewFlinger:this$0	Landroidx/recyclerview/widget/RecyclerView;
      //   784: iconst_0
      //   785: invokevirtual 105	androidx/recyclerview/widget/RecyclerView:setScrollState	(I)V
      //   788: getstatic 269	androidx/recyclerview/widget/RecyclerView:ALLOW_THREAD_GAP_WORK	Z
      //   791: ifeq +13 -> 804
      //   794: aload_0
      //   795: getfield 25	androidx/recyclerview/widget/RecyclerView$ViewFlinger:this$0	Landroidx/recyclerview/widget/RecyclerView;
      //   798: getfield 273	androidx/recyclerview/widget/RecyclerView:mPrefetchRegistry	Landroidx/recyclerview/widget/GapWorker$LayoutPrefetchRegistryImpl;
      //   801: invokevirtual 278	androidx/recyclerview/widget/GapWorker$LayoutPrefetchRegistryImpl:clearPrefetchPositions	()V
      //   804: aload_0
      //   805: getfield 25	androidx/recyclerview/widget/RecyclerView$ViewFlinger:this$0	Landroidx/recyclerview/widget/RecyclerView;
      //   808: iconst_1
      //   809: invokevirtual 281	androidx/recyclerview/widget/RecyclerView:stopNestedScroll	(I)V
      //   812: aload 14
      //   814: ifnull +30 -> 844
      //   817: aload 14
      //   819: invokevirtual 176	androidx/recyclerview/widget/RecyclerView$SmoothScroller:isPendingInitialRun	()Z
      //   822: ifeq +10 -> 832
      //   825: aload 14
      //   827: iconst_0
      //   828: iconst_0
      //   829: invokevirtual 198	androidx/recyclerview/widget/RecyclerView$SmoothScroller:onAnimation	(II)V
      //   832: aload_0
      //   833: getfield 37	androidx/recyclerview/widget/RecyclerView$ViewFlinger:mReSchedulePostAnimationCallback	Z
      //   836: ifne +8 -> 844
      //   839: aload 14
      //   841: invokevirtual 189	androidx/recyclerview/widget/RecyclerView$SmoothScroller:stop	()V
      //   844: aload_0
      //   845: invokespecial 283	androidx/recyclerview/widget/RecyclerView$ViewFlinger:enableRunOnAnimationRequests	()V
      //   848: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	849	0	this	ViewFlinger
      //   90	639	1	i	int
      //   82	621	2	j	int
      //   107	667	3	k	int
      //   104	668	4	m	int
      //   200	484	5	n	int
      //   211	367	6	i1	int
      //   207	352	7	i2	int
      //   203	449	8	i3	int
      //   192	180	9	i4	int
      //   197	179	10	i5	int
      //   66	493	11	i6	int
      //   73	505	12	i7	int
      //   30	691	13	localOverScroller	OverScroller
      //   42	798	14	localSmoothScroller	RecyclerView.SmoothScroller
      //   59	102	15	localObject	Object
    }
    
    public void smoothScrollBy(int paramInt1, int paramInt2)
    {
      smoothScrollBy(paramInt1, paramInt2, 0, 0);
    }
    
    public void smoothScrollBy(int paramInt1, int paramInt2, int paramInt3)
    {
      smoothScrollBy(paramInt1, paramInt2, paramInt3, RecyclerView.sQuinticInterpolator);
    }
    
    public void smoothScrollBy(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      smoothScrollBy(paramInt1, paramInt2, computeScrollDuration(paramInt1, paramInt2, paramInt3, paramInt4));
    }
    
    public void smoothScrollBy(int paramInt1, int paramInt2, int paramInt3, Interpolator paramInterpolator)
    {
      if (mInterpolator != paramInterpolator)
      {
        mInterpolator = paramInterpolator;
        mScroller = new OverScroller(getContext(), paramInterpolator);
      }
      setScrollState(2);
      mLastFlingY = 0;
      mLastFlingX = 0;
      mScroller.startScroll(0, 0, paramInt1, paramInt2, paramInt3);
      if (Build.VERSION.SDK_INT < 23) {
        mScroller.computeScrollOffset();
      }
      postOnAnimation();
    }
    
    public void smoothScrollBy(int paramInt1, int paramInt2, Interpolator paramInterpolator)
    {
      int i = computeScrollDuration(paramInt1, paramInt2, 0, 0);
      Interpolator localInterpolator = paramInterpolator;
      if (paramInterpolator == null) {
        localInterpolator = RecyclerView.sQuinticInterpolator;
      }
      smoothScrollBy(paramInt1, paramInt2, i, localInterpolator);
    }
    
    public void stop()
    {
      removeCallbacks(this);
      mScroller.abortAnimation();
    }
  }
  
  public static abstract class ViewHolder
  {
    static final int FLAG_ADAPTER_FULLUPDATE = 1024;
    static final int FLAG_ADAPTER_POSITION_UNKNOWN = 512;
    static final int FLAG_APPEARED_IN_PRE_LAYOUT = 4096;
    static final int FLAG_BOUNCED_FROM_HIDDEN_LIST = 8192;
    static final int FLAG_BOUND = 1;
    static final int FLAG_IGNORE = 128;
    static final int FLAG_INVALID = 4;
    static final int FLAG_MOVED = 2048;
    static final int FLAG_NOT_RECYCLABLE = 16;
    static final int FLAG_REMOVED = 8;
    static final int FLAG_RETURNED_FROM_SCRAP = 32;
    static final int FLAG_SET_A11Y_ITEM_DELEGATE = 16384;
    static final int FLAG_TMP_DETACHED = 256;
    static final int FLAG_UPDATE = 2;
    private static final List<Object> FULLUPDATE_PAYLOADS = ;
    static final int PENDING_ACCESSIBILITY_STATE_NOT_SET = -1;
    public final View itemView;
    int mFlags;
    boolean mInChangeScrap = false;
    private int mIsRecyclableCount = 0;
    long mItemId = -1L;
    int mItemViewType = -1;
    WeakReference<RecyclerView> mNestedRecyclerView;
    int mOldPosition = -1;
    RecyclerView mOwnerRecyclerView;
    List<Object> mPayloads = null;
    int mPendingAccessibilityState = -1;
    int mPosition = -1;
    int mPreLayoutPosition = -1;
    RecyclerView.Recycler mScrapContainer = null;
    ViewHolder mShadowedHolder = null;
    ViewHolder mShadowingHolder = null;
    List<Object> mUnmodifiedPayloads = null;
    private int mWasImportantForAccessibilityBeforeHidden = 0;
    
    public ViewHolder(View paramView)
    {
      if (paramView != null)
      {
        itemView = paramView;
        return;
      }
      throw new IllegalArgumentException("itemView may not be null");
    }
    
    private void createPayloadsIfNeeded()
    {
      if (mPayloads == null)
      {
        mPayloads = new ArrayList();
        mUnmodifiedPayloads = Collections.unmodifiableList(mPayloads);
      }
    }
    
    void addChangePayload(Object paramObject)
    {
      if (paramObject == null)
      {
        addFlags(1024);
        return;
      }
      if ((0x400 & mFlags) == 0)
      {
        createPayloadsIfNeeded();
        mPayloads.add(paramObject);
      }
    }
    
    void addFlags(int paramInt)
    {
      mFlags = (paramInt | mFlags);
    }
    
    void clearOldPosition()
    {
      mOldPosition = -1;
      mPreLayoutPosition = -1;
    }
    
    void clearPayload()
    {
      List localList = mPayloads;
      if (localList != null) {
        localList.clear();
      }
      mFlags &= 0xFBFF;
    }
    
    void clearReturnedFromScrapFlag()
    {
      mFlags &= 0xFFFFFFDF;
    }
    
    void clearTmpDetachFlag()
    {
      mFlags &= 0xFEFF;
    }
    
    boolean doesTransientStatePreventRecycling()
    {
      return ((mFlags & 0x10) == 0) && (ViewCompat.hasTransientState(itemView));
    }
    
    void flagRemovedAndOffsetPosition(int paramInt1, int paramInt2, boolean paramBoolean)
    {
      addFlags(8);
      offsetPosition(paramInt2, paramBoolean);
      mPosition = paramInt1;
    }
    
    public final int getAdapterPosition()
    {
      RecyclerView localRecyclerView = mOwnerRecyclerView;
      if (localRecyclerView == null) {
        return -1;
      }
      return localRecyclerView.getAdapterPositionFor(this);
    }
    
    public final long getItemId()
    {
      return mItemId;
    }
    
    public final int getItemViewType()
    {
      return mItemViewType;
    }
    
    public final int getLayoutPosition()
    {
      int j = mPreLayoutPosition;
      int i = j;
      if (j == -1) {
        i = mPosition;
      }
      return i;
    }
    
    public final int getOldPosition()
    {
      return mOldPosition;
    }
    
    public final int getPosition()
    {
      int j = mPreLayoutPosition;
      int i = j;
      if (j == -1) {
        i = mPosition;
      }
      return i;
    }
    
    List getUnmodifiedPayloads()
    {
      if ((mFlags & 0x400) == 0)
      {
        List localList = mPayloads;
        if ((localList != null) && (localList.size() != 0)) {
          return mUnmodifiedPayloads;
        }
        return FULLUPDATE_PAYLOADS;
      }
      return FULLUPDATE_PAYLOADS;
    }
    
    boolean hasAnyOfTheFlags(int paramInt)
    {
      return (paramInt & mFlags) != 0;
    }
    
    boolean isAdapterPositionUnknown()
    {
      return ((mFlags & 0x200) != 0) || (isInvalid());
    }
    
    boolean isBound()
    {
      return (mFlags & 0x1) != 0;
    }
    
    boolean isInvalid()
    {
      return (mFlags & 0x4) != 0;
    }
    
    public final boolean isRecyclable()
    {
      return ((mFlags & 0x10) == 0) && (!ViewCompat.hasTransientState(itemView));
    }
    
    boolean isRemoved()
    {
      return (mFlags & 0x8) != 0;
    }
    
    boolean isScrap()
    {
      return mScrapContainer != null;
    }
    
    boolean isTmpDetached()
    {
      return (mFlags & 0x100) != 0;
    }
    
    boolean isUpdated()
    {
      return (mFlags & 0x2) != 0;
    }
    
    boolean needsUpdate()
    {
      return (mFlags & 0x2) != 0;
    }
    
    void offsetPosition(int paramInt, boolean paramBoolean)
    {
      if (mOldPosition == -1) {
        mOldPosition = mPosition;
      }
      if (mPreLayoutPosition == -1) {
        mPreLayoutPosition = mPosition;
      }
      if (paramBoolean) {
        mPreLayoutPosition += paramInt;
      }
      mPosition += paramInt;
      if (itemView.getLayoutParams() != null) {
        itemView.getLayoutParams()).mInsetsDirty = true;
      }
    }
    
    void onEnteredHiddenState(RecyclerView paramRecyclerView)
    {
      int i = mPendingAccessibilityState;
      if (i != -1) {
        mWasImportantForAccessibilityBeforeHidden = i;
      } else {
        mWasImportantForAccessibilityBeforeHidden = ViewCompat.getImportantForAccessibility(itemView);
      }
      paramRecyclerView.setChildImportantForAccessibilityInternal(this, 4);
    }
    
    void onLeftHiddenState(RecyclerView paramRecyclerView)
    {
      paramRecyclerView.setChildImportantForAccessibilityInternal(this, mWasImportantForAccessibilityBeforeHidden);
      mWasImportantForAccessibilityBeforeHidden = 0;
    }
    
    void resetInternal()
    {
      mFlags = 0;
      mPosition = -1;
      mOldPosition = -1;
      mItemId = -1L;
      mPreLayoutPosition = -1;
      mIsRecyclableCount = 0;
      mShadowedHolder = null;
      mShadowingHolder = null;
      clearPayload();
      mWasImportantForAccessibilityBeforeHidden = 0;
      mPendingAccessibilityState = -1;
      RecyclerView.clearNestedRecyclerViewIfNotNested(this);
    }
    
    void saveOldPosition()
    {
      if (mOldPosition == -1) {
        mOldPosition = mPosition;
      }
    }
    
    void setFlags(int paramInt1, int paramInt2)
    {
      mFlags = (paramInt1 & paramInt2 | mFlags & paramInt2);
    }
    
    public final void setIsRecyclable(boolean paramBoolean)
    {
      int i = mIsRecyclableCount;
      if (paramBoolean) {
        i -= 1;
      } else {
        i += 1;
      }
      mIsRecyclableCount = i;
      i = mIsRecyclableCount;
      if (i < 0)
      {
        mIsRecyclableCount = 0;
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("isRecyclable decremented below 0: unmatched pair of setIsRecyable() calls for ");
        localStringBuilder.append(this);
        Log.e("View", localStringBuilder.toString());
        return;
      }
      if ((!paramBoolean) && (i == 1))
      {
        mFlags |= 0x10;
        return;
      }
      if ((paramBoolean) && (mIsRecyclableCount == 0)) {
        mFlags &= 0xFFFFFFEF;
      }
    }
    
    void setScrapContainer(RecyclerView.Recycler paramRecycler, boolean paramBoolean)
    {
      mScrapContainer = paramRecycler;
      mInChangeScrap = paramBoolean;
    }
    
    boolean shouldBeKeptAsChild()
    {
      return (mFlags & 0x10) != 0;
    }
    
    boolean shouldIgnore()
    {
      return (mFlags & 0x80) != 0;
    }
    
    void stopIgnoring()
    {
      mFlags &= 0xFF7F;
    }
    
    public String toString()
    {
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("ViewHolder{");
      ((StringBuilder)localObject).append(Integer.toHexString(hashCode()));
      ((StringBuilder)localObject).append(" position=");
      ((StringBuilder)localObject).append(mPosition);
      ((StringBuilder)localObject).append(" id=");
      ((StringBuilder)localObject).append(mItemId);
      ((StringBuilder)localObject).append(", oldPos=");
      ((StringBuilder)localObject).append(mOldPosition);
      ((StringBuilder)localObject).append(", pLpos:");
      ((StringBuilder)localObject).append(mPreLayoutPosition);
      StringBuilder localStringBuilder = new StringBuilder(((StringBuilder)localObject).toString());
      if (isScrap())
      {
        localStringBuilder.append(" scrap ");
        if (mInChangeScrap) {
          localObject = "[changeScrap]";
        } else {
          localObject = "[attachedScrap]";
        }
        localStringBuilder.append((String)localObject);
      }
      if (isInvalid()) {
        localStringBuilder.append(" invalid");
      }
      if (!isBound()) {
        localStringBuilder.append(" unbound");
      }
      if (needsUpdate()) {
        localStringBuilder.append(" update");
      }
      if (isRemoved()) {
        localStringBuilder.append(" removed");
      }
      if (shouldIgnore()) {
        localStringBuilder.append(" ignored");
      }
      if (isTmpDetached()) {
        localStringBuilder.append(" tmpDetached");
      }
      if (!isRecyclable())
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append(" not recyclable(");
        ((StringBuilder)localObject).append(mIsRecyclableCount);
        ((StringBuilder)localObject).append(")");
        localStringBuilder.append(((StringBuilder)localObject).toString());
      }
      if (isAdapterPositionUnknown()) {
        localStringBuilder.append(" undefined adapter position");
      }
      if (itemView.getParent() == null) {
        localStringBuilder.append(" no parent");
      }
      localStringBuilder.append("}");
      return localStringBuilder.toString();
    }
    
    void unScrap()
    {
      mScrapContainer.unscrapView(this);
    }
    
    boolean wasReturnedFromScrap()
    {
      return (mFlags & 0x20) != 0;
    }
  }
}
