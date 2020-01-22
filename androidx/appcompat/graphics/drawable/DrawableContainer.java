package androidx.appcompat.graphics.drawable;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;
import android.graphics.drawable.Drawable.ConstantState;
import android.os.Build.VERSION;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import androidx.core.graphics.drawable.DrawableCompat;

class DrawableContainer
  extends Drawable
  implements Drawable.Callback
{
  private static final boolean DEBUG = false;
  private static final boolean DEFAULT_DITHER = true;
  private static final String TAG = "DrawableContainer";
  private int mAlpha = 255;
  private Runnable mAnimationRunnable;
  private BlockInvalidateCallback mBlockInvalidateCallback;
  private int mCurIndex = -1;
  private Drawable mCurrDrawable;
  private DrawableContainerState mDrawableContainerState;
  private long mEnterAnimationEnd;
  private long mExitAnimationEnd;
  private boolean mHasAlpha;
  private Rect mHotspotBounds;
  private Drawable mLastDrawable;
  private int mLastIndex = -1;
  private boolean mMutated;
  
  DrawableContainer() {}
  
  private void initializeDrawableForDisplay(Drawable paramDrawable)
  {
    if (mBlockInvalidateCallback == null) {
      mBlockInvalidateCallback = new BlockInvalidateCallback();
    }
    paramDrawable.setCallback(mBlockInvalidateCallback.wrap(paramDrawable.getCallback()));
    try
    {
      int i = mDrawableContainerState.mEnterFadeDuration;
      if (i <= 0)
      {
        bool = mHasAlpha;
        if (bool) {
          paramDrawable.setAlpha(mAlpha);
        }
      }
      boolean bool = mDrawableContainerState.mHasColorFilter;
      if (bool)
      {
        paramDrawable.setColorFilter(mDrawableContainerState.mColorFilter);
      }
      else
      {
        bool = mDrawableContainerState.mHasTintList;
        if (bool) {
          DrawableCompat.setTintList(paramDrawable, mDrawableContainerState.mTintList);
        }
        bool = mDrawableContainerState.mHasTintMode;
        if (bool) {
          DrawableCompat.setTintMode(paramDrawable, mDrawableContainerState.mTintMode);
        }
      }
      paramDrawable.setVisible(isVisible(), true);
      paramDrawable.setDither(mDrawableContainerState.mDither);
      paramDrawable.setState(getState());
      paramDrawable.setLevel(getLevel());
      paramDrawable.setBounds(getBounds());
      i = Build.VERSION.SDK_INT;
      if (i >= 23) {
        paramDrawable.setLayoutDirection(getLayoutDirection());
      }
      i = Build.VERSION.SDK_INT;
      if (i >= 19) {
        paramDrawable.setAutoMirrored(mDrawableContainerState.mAutoMirrored);
      }
      Rect localRect = mHotspotBounds;
      i = Build.VERSION.SDK_INT;
      if ((i >= 21) && (localRect != null)) {
        paramDrawable.setHotspotBounds(left, top, right, bottom);
      }
      paramDrawable.setCallback(mBlockInvalidateCallback.unwrap());
      return;
    }
    catch (Throwable localThrowable)
    {
      paramDrawable.setCallback(mBlockInvalidateCallback.unwrap());
      throw localThrowable;
    }
  }
  
  private boolean needsMirroring()
  {
    return (isAutoMirrored()) && (DrawableCompat.getLayoutDirection(this) == 1);
  }
  
  static int resolveDensity(Resources paramResources, int paramInt)
  {
    if (paramResources != null) {
      paramInt = getDisplayMetricsdensityDpi;
    }
    if (paramInt == 0) {
      return 160;
    }
    return paramInt;
  }
  
  void animate(boolean paramBoolean)
  {
    int j = 1;
    mHasAlpha = true;
    long l1 = SystemClock.uptimeMillis();
    Drawable localDrawable = mCurrDrawable;
    long l2;
    if (localDrawable != null)
    {
      l2 = mEnterAnimationEnd;
      if (l2 != 0L) {
        if (l2 <= l1)
        {
          localDrawable.setAlpha(mAlpha);
          mEnterAnimationEnd = 0L;
        }
        else
        {
          i = (int)((l2 - l1) * 255L) / mDrawableContainerState.mEnterFadeDuration;
          mCurrDrawable.setAlpha((255 - i) * mAlpha / 255);
          i = 1;
          break label113;
        }
      }
    }
    else
    {
      mEnterAnimationEnd = 0L;
    }
    int i = 0;
    label113:
    localDrawable = mLastDrawable;
    if (localDrawable != null)
    {
      l2 = mExitAnimationEnd;
      if (l2 != 0L) {
        if (l2 <= l1)
        {
          localDrawable.setVisible(false, false);
          mLastDrawable = null;
          mLastIndex = -1;
          mExitAnimationEnd = 0L;
        }
        else
        {
          i = (int)((l2 - l1) * 255L) / mDrawableContainerState.mExitFadeDuration;
          mLastDrawable.setAlpha(i * mAlpha / 255);
          i = j;
        }
      }
    }
    else
    {
      mExitAnimationEnd = 0L;
    }
    if ((paramBoolean) && (i != 0)) {
      scheduleSelf(mAnimationRunnable, l1 + 16L);
    }
  }
  
  public void applyTheme(Resources.Theme paramTheme)
  {
    mDrawableContainerState.applyTheme(paramTheme);
  }
  
  public boolean canApplyTheme()
  {
    return mDrawableContainerState.canApplyTheme();
  }
  
  void clearMutated()
  {
    mDrawableContainerState.clearMutated();
    mMutated = false;
  }
  
  DrawableContainerState cloneConstantState()
  {
    return mDrawableContainerState;
  }
  
  public void draw(Canvas paramCanvas)
  {
    Drawable localDrawable = mCurrDrawable;
    if (localDrawable != null) {
      localDrawable.draw(paramCanvas);
    }
    localDrawable = mLastDrawable;
    if (localDrawable != null) {
      localDrawable.draw(paramCanvas);
    }
  }
  
  public int getAlpha()
  {
    return mAlpha;
  }
  
  public int getChangingConfigurations()
  {
    return super.getChangingConfigurations() | mDrawableContainerState.getChangingConfigurations();
  }
  
  public final Drawable.ConstantState getConstantState()
  {
    if (mDrawableContainerState.canConstantState())
    {
      mDrawableContainerState.mChangingConfigurations = getChangingConfigurations();
      return mDrawableContainerState;
    }
    return null;
  }
  
  public Drawable getCurrent()
  {
    return mCurrDrawable;
  }
  
  int getCurrentIndex()
  {
    return mCurIndex;
  }
  
  public void getHotspotBounds(Rect paramRect)
  {
    Rect localRect = mHotspotBounds;
    if (localRect != null)
    {
      paramRect.set(localRect);
      return;
    }
    super.getHotspotBounds(paramRect);
  }
  
  public int getIntrinsicHeight()
  {
    if (mDrawableContainerState.isConstantSize()) {
      return mDrawableContainerState.getConstantHeight();
    }
    Drawable localDrawable = mCurrDrawable;
    if (localDrawable != null) {
      return localDrawable.getIntrinsicHeight();
    }
    return -1;
  }
  
  public int getIntrinsicWidth()
  {
    if (mDrawableContainerState.isConstantSize()) {
      return mDrawableContainerState.getConstantWidth();
    }
    Drawable localDrawable = mCurrDrawable;
    if (localDrawable != null) {
      return localDrawable.getIntrinsicWidth();
    }
    return -1;
  }
  
  public int getLayoutDirection()
  {
    throw new Error("Unresolved compilation error: Method <androidx.appcompat.graphics.drawable.DrawableContainer: int getLayoutDirection()> does not exist!");
  }
  
  public int getMinimumHeight()
  {
    if (mDrawableContainerState.isConstantSize()) {
      return mDrawableContainerState.getConstantMinimumHeight();
    }
    Drawable localDrawable = mCurrDrawable;
    if (localDrawable != null) {
      return localDrawable.getMinimumHeight();
    }
    return 0;
  }
  
  public int getMinimumWidth()
  {
    if (mDrawableContainerState.isConstantSize()) {
      return mDrawableContainerState.getConstantMinimumWidth();
    }
    Drawable localDrawable = mCurrDrawable;
    if (localDrawable != null) {
      return localDrawable.getMinimumWidth();
    }
    return 0;
  }
  
  public int getOpacity()
  {
    Drawable localDrawable = mCurrDrawable;
    if ((localDrawable != null) && (localDrawable.isVisible())) {
      return mDrawableContainerState.getOpacity();
    }
    return -2;
  }
  
  public void getOutline(Outline paramOutline)
  {
    Drawable localDrawable = mCurrDrawable;
    if (localDrawable != null) {
      localDrawable.getOutline(paramOutline);
    }
  }
  
  public boolean getPadding(Rect paramRect)
  {
    Object localObject = mDrawableContainerState.getConstantPadding();
    int i;
    boolean bool;
    if (localObject != null)
    {
      paramRect.set((Rect)localObject);
      i = left;
      int j = top;
      int k = bottom;
      if ((right | i | j | k) != 0) {
        bool = true;
      } else {
        bool = false;
      }
    }
    else
    {
      localObject = mCurrDrawable;
      if (localObject != null) {
        bool = ((Drawable)localObject).getPadding(paramRect);
      } else {
        bool = super.getPadding(paramRect);
      }
    }
    if (needsMirroring())
    {
      i = left;
      left = right;
      right = i;
    }
    return bool;
  }
  
  public void invalidateDrawable(Drawable paramDrawable)
  {
    DrawableContainerState localDrawableContainerState = mDrawableContainerState;
    if (localDrawableContainerState != null) {
      localDrawableContainerState.invalidateCache();
    }
    if ((paramDrawable == mCurrDrawable) && (getCallback() != null)) {
      getCallback().invalidateDrawable(this);
    }
  }
  
  public boolean isAutoMirrored()
  {
    return mDrawableContainerState.mAutoMirrored;
  }
  
  public boolean isStateful()
  {
    return mDrawableContainerState.isStateful();
  }
  
  public void jumpToCurrentState()
  {
    Drawable localDrawable = mLastDrawable;
    int i;
    if (localDrawable != null)
    {
      localDrawable.jumpToCurrentState();
      mLastDrawable = null;
      mLastIndex = -1;
      i = 1;
    }
    else
    {
      i = 0;
    }
    localDrawable = mCurrDrawable;
    if (localDrawable != null)
    {
      localDrawable.jumpToCurrentState();
      if (mHasAlpha) {
        mCurrDrawable.setAlpha(mAlpha);
      }
    }
    if (mExitAnimationEnd != 0L)
    {
      mExitAnimationEnd = 0L;
      i = 1;
    }
    if (mEnterAnimationEnd != 0L)
    {
      mEnterAnimationEnd = 0L;
      i = 1;
    }
    if (i != 0) {
      invalidateSelf();
    }
  }
  
  public Drawable mutate()
  {
    if ((!mMutated) && (super.mutate() == this))
    {
      DrawableContainerState localDrawableContainerState = cloneConstantState();
      localDrawableContainerState.mutate();
      setConstantState(localDrawableContainerState);
      mMutated = true;
    }
    return this;
  }
  
  protected void onBoundsChange(Rect paramRect)
  {
    Drawable localDrawable = mLastDrawable;
    if (localDrawable != null) {
      localDrawable.setBounds(paramRect);
    }
    localDrawable = mCurrDrawable;
    if (localDrawable != null) {
      localDrawable.setBounds(paramRect);
    }
  }
  
  public boolean onLayoutDirectionChanged(int paramInt)
  {
    return mDrawableContainerState.setLayoutDirection(paramInt, getCurrentIndex());
  }
  
  protected boolean onLevelChange(int paramInt)
  {
    Drawable localDrawable = mLastDrawable;
    if (localDrawable != null) {
      return localDrawable.setLevel(paramInt);
    }
    localDrawable = mCurrDrawable;
    if (localDrawable != null) {
      return localDrawable.setLevel(paramInt);
    }
    return false;
  }
  
  protected boolean onStateChange(int[] paramArrayOfInt)
  {
    Drawable localDrawable = mLastDrawable;
    if (localDrawable != null) {
      return localDrawable.setState(paramArrayOfInt);
    }
    localDrawable = mCurrDrawable;
    if (localDrawable != null) {
      return localDrawable.setState(paramArrayOfInt);
    }
    return false;
  }
  
  public void scheduleDrawable(Drawable paramDrawable, Runnable paramRunnable, long paramLong)
  {
    if ((paramDrawable == mCurrDrawable) && (getCallback() != null)) {
      getCallback().scheduleDrawable(this, paramRunnable, paramLong);
    }
  }
  
  boolean selectDrawable(int paramInt)
  {
    if (paramInt == mCurIndex) {
      return false;
    }
    long l = SystemClock.uptimeMillis();
    Object localObject;
    if (mDrawableContainerState.mExitFadeDuration > 0)
    {
      localObject = mLastDrawable;
      if (localObject != null) {
        ((Drawable)localObject).setVisible(false, false);
      }
      localObject = mCurrDrawable;
      if (localObject != null)
      {
        mLastDrawable = ((Drawable)localObject);
        mLastIndex = mCurIndex;
        mExitAnimationEnd = (mDrawableContainerState.mExitFadeDuration + l);
      }
      else
      {
        mLastDrawable = null;
        mLastIndex = -1;
        mExitAnimationEnd = 0L;
      }
    }
    else
    {
      localObject = mCurrDrawable;
      if (localObject != null) {
        ((Drawable)localObject).setVisible(false, false);
      }
    }
    if ((paramInt >= 0) && (paramInt < mDrawableContainerState.mNumChildren))
    {
      localObject = mDrawableContainerState.getChild(paramInt);
      mCurrDrawable = ((Drawable)localObject);
      mCurIndex = paramInt;
      if (localObject != null)
      {
        if (mDrawableContainerState.mEnterFadeDuration > 0) {
          mEnterAnimationEnd = (l + mDrawableContainerState.mEnterFadeDuration);
        }
        initializeDrawableForDisplay((Drawable)localObject);
      }
    }
    else
    {
      mCurrDrawable = null;
      mCurIndex = -1;
    }
    if ((mEnterAnimationEnd != 0L) || (mExitAnimationEnd != 0L))
    {
      localObject = mAnimationRunnable;
      if (localObject == null) {
        mAnimationRunnable = new Runnable()
        {
          public void run()
          {
            animate(true);
            invalidateSelf();
          }
        };
      } else {
        unscheduleSelf((Runnable)localObject);
      }
      animate(true);
    }
    invalidateSelf();
    return true;
  }
  
  public void setAlpha(int paramInt)
  {
    if ((!mHasAlpha) || (mAlpha != paramInt))
    {
      mHasAlpha = true;
      mAlpha = paramInt;
      Drawable localDrawable = mCurrDrawable;
      if (localDrawable != null)
      {
        if (mEnterAnimationEnd == 0L)
        {
          localDrawable.setAlpha(paramInt);
          return;
        }
        animate(false);
      }
    }
  }
  
  public void setAutoMirrored(boolean paramBoolean)
  {
    if (mDrawableContainerState.mAutoMirrored != paramBoolean)
    {
      DrawableContainerState localDrawableContainerState = mDrawableContainerState;
      mAutoMirrored = paramBoolean;
      Drawable localDrawable = mCurrDrawable;
      if (localDrawable != null) {
        DrawableCompat.setAutoMirrored(localDrawable, mAutoMirrored);
      }
    }
  }
  
  public void setColorFilter(ColorFilter paramColorFilter)
  {
    Object localObject = mDrawableContainerState;
    mHasColorFilter = true;
    if (mColorFilter != paramColorFilter)
    {
      mDrawableContainerState.mColorFilter = paramColorFilter;
      localObject = mCurrDrawable;
      if (localObject != null) {
        ((Drawable)localObject).setColorFilter(paramColorFilter);
      }
    }
  }
  
  void setConstantState(DrawableContainerState paramDrawableContainerState)
  {
    mDrawableContainerState = paramDrawableContainerState;
    int i = mCurIndex;
    if (i >= 0)
    {
      mCurrDrawable = paramDrawableContainerState.getChild(i);
      paramDrawableContainerState = mCurrDrawable;
      if (paramDrawableContainerState != null) {
        initializeDrawableForDisplay(paramDrawableContainerState);
      }
    }
    mLastIndex = -1;
    mLastDrawable = null;
  }
  
  void setCurrentIndex(int paramInt)
  {
    selectDrawable(paramInt);
  }
  
  public void setDither(boolean paramBoolean)
  {
    if (mDrawableContainerState.mDither != paramBoolean)
    {
      DrawableContainerState localDrawableContainerState = mDrawableContainerState;
      mDither = paramBoolean;
      Drawable localDrawable = mCurrDrawable;
      if (localDrawable != null) {
        localDrawable.setDither(mDither);
      }
    }
  }
  
  public void setEnterFadeDuration(int paramInt)
  {
    mDrawableContainerState.mEnterFadeDuration = paramInt;
  }
  
  public void setExitFadeDuration(int paramInt)
  {
    mDrawableContainerState.mExitFadeDuration = paramInt;
  }
  
  public void setHotspot(float paramFloat1, float paramFloat2)
  {
    Drawable localDrawable = mCurrDrawable;
    if (localDrawable != null) {
      DrawableCompat.setHotspot(localDrawable, paramFloat1, paramFloat2);
    }
  }
  
  public void setHotspotBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    Object localObject = mHotspotBounds;
    if (localObject == null) {
      mHotspotBounds = new Rect(paramInt1, paramInt2, paramInt3, paramInt4);
    } else {
      ((Rect)localObject).set(paramInt1, paramInt2, paramInt3, paramInt4);
    }
    localObject = mCurrDrawable;
    if (localObject != null) {
      DrawableCompat.setHotspotBounds((Drawable)localObject, paramInt1, paramInt2, paramInt3, paramInt4);
    }
  }
  
  public void setTintList(ColorStateList paramColorStateList)
  {
    DrawableContainerState localDrawableContainerState = mDrawableContainerState;
    mHasTintList = true;
    if (mTintList != paramColorStateList)
    {
      mDrawableContainerState.mTintList = paramColorStateList;
      DrawableCompat.setTintList(mCurrDrawable, paramColorStateList);
    }
  }
  
  public void setTintMode(PorterDuff.Mode paramMode)
  {
    DrawableContainerState localDrawableContainerState = mDrawableContainerState;
    mHasTintMode = true;
    if (mTintMode != paramMode)
    {
      mDrawableContainerState.mTintMode = paramMode;
      DrawableCompat.setTintMode(mCurrDrawable, paramMode);
    }
  }
  
  public boolean setVisible(boolean paramBoolean1, boolean paramBoolean2)
  {
    boolean bool = super.setVisible(paramBoolean1, paramBoolean2);
    Drawable localDrawable = mLastDrawable;
    if (localDrawable != null) {
      localDrawable.setVisible(paramBoolean1, paramBoolean2);
    }
    localDrawable = mCurrDrawable;
    if (localDrawable != null) {
      localDrawable.setVisible(paramBoolean1, paramBoolean2);
    }
    return bool;
  }
  
  public void unscheduleDrawable(Drawable paramDrawable, Runnable paramRunnable)
  {
    if ((paramDrawable == mCurrDrawable) && (getCallback() != null)) {
      getCallback().unscheduleDrawable(this, paramRunnable);
    }
  }
  
  final void updateDensity(Resources paramResources)
  {
    mDrawableContainerState.updateDensity(paramResources);
  }
  
  static class BlockInvalidateCallback
    implements Drawable.Callback
  {
    private Drawable.Callback mCallback;
    
    BlockInvalidateCallback() {}
    
    public void invalidateDrawable(Drawable paramDrawable) {}
    
    public void scheduleDrawable(Drawable paramDrawable, Runnable paramRunnable, long paramLong)
    {
      Drawable.Callback localCallback = mCallback;
      if (localCallback != null) {
        localCallback.scheduleDrawable(paramDrawable, paramRunnable, paramLong);
      }
    }
    
    public void unscheduleDrawable(Drawable paramDrawable, Runnable paramRunnable)
    {
      Drawable.Callback localCallback = mCallback;
      if (localCallback != null) {
        localCallback.unscheduleDrawable(paramDrawable, paramRunnable);
      }
    }
    
    public Drawable.Callback unwrap()
    {
      Drawable.Callback localCallback = mCallback;
      mCallback = null;
      return localCallback;
    }
    
    public BlockInvalidateCallback wrap(Drawable.Callback paramCallback)
    {
      mCallback = paramCallback;
      return this;
    }
  }
  
  static abstract class DrawableContainerState
    extends Drawable.ConstantState
  {
    boolean mAutoMirrored;
    boolean mCanConstantState;
    int mChangingConfigurations;
    boolean mCheckedConstantSize;
    boolean mCheckedConstantState;
    boolean mCheckedOpacity;
    boolean mCheckedPadding;
    boolean mCheckedStateful;
    int mChildrenChangingConfigurations;
    ColorFilter mColorFilter;
    int mConstantHeight;
    int mConstantMinimumHeight;
    int mConstantMinimumWidth;
    Rect mConstantPadding;
    boolean mConstantSize;
    int mConstantWidth;
    int mDensity = 160;
    boolean mDither;
    SparseArray<Drawable.ConstantState> mDrawableFutures;
    Drawable[] mDrawables;
    int mEnterFadeDuration;
    int mExitFadeDuration;
    boolean mHasColorFilter;
    boolean mHasTintList;
    boolean mHasTintMode;
    int mLayoutDirection;
    boolean mMutated;
    int mNumChildren;
    int mOpacity;
    final DrawableContainer mOwner;
    Resources mSourceRes;
    boolean mStateful;
    ColorStateList mTintList;
    PorterDuff.Mode mTintMode;
    boolean mVariablePadding;
    
    DrawableContainerState(DrawableContainerState paramDrawableContainerState, DrawableContainer paramDrawableContainer, Resources paramResources)
    {
      int j = 0;
      mVariablePadding = false;
      mConstantSize = false;
      mDither = true;
      mEnterFadeDuration = 0;
      mExitFadeDuration = 0;
      mOwner = paramDrawableContainer;
      if (paramResources != null) {
        paramDrawableContainer = paramResources;
      } else if (paramDrawableContainerState != null) {
        paramDrawableContainer = mSourceRes;
      } else {
        paramDrawableContainer = null;
      }
      mSourceRes = paramDrawableContainer;
      int i;
      if (paramDrawableContainerState != null) {
        i = mDensity;
      } else {
        i = 0;
      }
      mDensity = DrawableContainer.resolveDensity(paramResources, i);
      if (paramDrawableContainerState != null)
      {
        mChangingConfigurations = mChangingConfigurations;
        mChildrenChangingConfigurations = mChildrenChangingConfigurations;
        mCheckedConstantState = true;
        mCanConstantState = true;
        mVariablePadding = mVariablePadding;
        mConstantSize = mConstantSize;
        mDither = mDither;
        mMutated = mMutated;
        mLayoutDirection = mLayoutDirection;
        mEnterFadeDuration = mEnterFadeDuration;
        mExitFadeDuration = mExitFadeDuration;
        mAutoMirrored = mAutoMirrored;
        mColorFilter = mColorFilter;
        mHasColorFilter = mHasColorFilter;
        mTintList = mTintList;
        mTintMode = mTintMode;
        mHasTintList = mHasTintList;
        mHasTintMode = mHasTintMode;
        if (mDensity == mDensity)
        {
          if (mCheckedPadding)
          {
            mConstantPadding = new Rect(mConstantPadding);
            mCheckedPadding = true;
          }
          if (mCheckedConstantSize)
          {
            mConstantWidth = mConstantWidth;
            mConstantHeight = mConstantHeight;
            mConstantMinimumWidth = mConstantMinimumWidth;
            mConstantMinimumHeight = mConstantMinimumHeight;
            mCheckedConstantSize = true;
          }
        }
        if (mCheckedOpacity)
        {
          mOpacity = mOpacity;
          mCheckedOpacity = true;
        }
        if (mCheckedStateful)
        {
          mStateful = mStateful;
          mCheckedStateful = true;
        }
        paramDrawableContainer = mDrawables;
        mDrawables = new Drawable[paramDrawableContainer.length];
        mNumChildren = mNumChildren;
        paramDrawableContainerState = mDrawableFutures;
        if (paramDrawableContainerState != null) {
          mDrawableFutures = paramDrawableContainerState.clone();
        } else {
          mDrawableFutures = new SparseArray(mNumChildren);
        }
        int k = mNumChildren;
        i = j;
        while (i < k)
        {
          if (paramDrawableContainer[i] != null)
          {
            paramDrawableContainerState = paramDrawableContainer[i].getConstantState();
            if (paramDrawableContainerState != null) {
              mDrawableFutures.put(i, paramDrawableContainerState);
            } else {
              mDrawables[i] = paramDrawableContainer[i];
            }
          }
          i += 1;
        }
      }
      mDrawables = new Drawable[10];
      mNumChildren = 0;
    }
    
    private void createAllFutures()
    {
      Object localObject = mDrawableFutures;
      if (localObject != null)
      {
        int j = ((SparseArray)localObject).size();
        int i = 0;
        while (i < j)
        {
          int k = mDrawableFutures.keyAt(i);
          localObject = (Drawable.ConstantState)mDrawableFutures.valueAt(i);
          mDrawables[k] = prepareDrawable(((Drawable.ConstantState)localObject).newDrawable(mSourceRes));
          i += 1;
        }
        mDrawableFutures = null;
      }
    }
    
    private Drawable prepareDrawable(Drawable paramDrawable)
    {
      if (Build.VERSION.SDK_INT >= 23) {
        paramDrawable.setLayoutDirection(mLayoutDirection);
      }
      paramDrawable = paramDrawable.mutate();
      paramDrawable.setCallback(mOwner);
      return paramDrawable;
    }
    
    public final int addChild(Drawable paramDrawable)
    {
      int i = mNumChildren;
      if (i >= mDrawables.length) {
        growArray(i, i + 10);
      }
      paramDrawable.mutate();
      paramDrawable.setVisible(false, true);
      paramDrawable.setCallback(mOwner);
      mDrawables[i] = paramDrawable;
      mNumChildren += 1;
      int j = mChildrenChangingConfigurations;
      mChildrenChangingConfigurations = (paramDrawable.getChangingConfigurations() | j);
      invalidateCache();
      mConstantPadding = null;
      mCheckedPadding = false;
      mCheckedConstantSize = false;
      mCheckedConstantState = false;
      return i;
    }
    
    final void applyTheme(Resources.Theme paramTheme)
    {
      if (paramTheme != null)
      {
        createAllFutures();
        int j = mNumChildren;
        Drawable[] arrayOfDrawable = mDrawables;
        int i = 0;
        while (i < j)
        {
          if ((arrayOfDrawable[i] != null) && (arrayOfDrawable[i].canApplyTheme()))
          {
            arrayOfDrawable[i].applyTheme(paramTheme);
            mChildrenChangingConfigurations |= arrayOfDrawable[i].getChangingConfigurations();
          }
          i += 1;
        }
        updateDensity(paramTheme.getResources());
      }
    }
    
    public boolean canApplyTheme()
    {
      int j = mNumChildren;
      Drawable[] arrayOfDrawable = mDrawables;
      int i = 0;
      while (i < j)
      {
        Object localObject = arrayOfDrawable[i];
        if (localObject != null)
        {
          if (((Drawable)localObject).canApplyTheme()) {
            return true;
          }
        }
        else
        {
          localObject = (Drawable.ConstantState)mDrawableFutures.get(i);
          if ((localObject != null) && (((Drawable.ConstantState)localObject).canApplyTheme())) {
            return true;
          }
        }
        i += 1;
      }
      return false;
    }
    
    public boolean canConstantState()
    {
      try
      {
        if (mCheckedConstantState)
        {
          boolean bool = mCanConstantState;
          return bool;
        }
        createAllFutures();
        mCheckedConstantState = true;
        int j = mNumChildren;
        Drawable[] arrayOfDrawable = mDrawables;
        int i = 0;
        while (i < j)
        {
          if (arrayOfDrawable[i].getConstantState() == null)
          {
            mCanConstantState = false;
            return false;
          }
          i += 1;
        }
        mCanConstantState = true;
        return true;
      }
      catch (Throwable localThrowable)
      {
        throw localThrowable;
      }
    }
    
    final void clearMutated()
    {
      mMutated = false;
    }
    
    protected void computeConstantSize()
    {
      mCheckedConstantSize = true;
      createAllFutures();
      int j = mNumChildren;
      Drawable[] arrayOfDrawable = mDrawables;
      mConstantHeight = -1;
      mConstantWidth = -1;
      int i = 0;
      mConstantMinimumHeight = 0;
      mConstantMinimumWidth = 0;
      while (i < j)
      {
        Drawable localDrawable = arrayOfDrawable[i];
        int k = localDrawable.getIntrinsicWidth();
        if (k > mConstantWidth) {
          mConstantWidth = k;
        }
        k = localDrawable.getIntrinsicHeight();
        if (k > mConstantHeight) {
          mConstantHeight = k;
        }
        k = localDrawable.getMinimumWidth();
        if (k > mConstantMinimumWidth) {
          mConstantMinimumWidth = k;
        }
        k = localDrawable.getMinimumHeight();
        if (k > mConstantMinimumHeight) {
          mConstantMinimumHeight = k;
        }
        i += 1;
      }
    }
    
    final int getCapacity()
    {
      return mDrawables.length;
    }
    
    public int getChangingConfigurations()
    {
      return mChangingConfigurations | mChildrenChangingConfigurations;
    }
    
    public final Drawable getChild(int paramInt)
    {
      Object localObject = mDrawables[paramInt];
      if (localObject != null) {
        return localObject;
      }
      localObject = mDrawableFutures;
      if (localObject != null)
      {
        int i = ((SparseArray)localObject).indexOfKey(paramInt);
        if (i >= 0)
        {
          localObject = prepareDrawable(((Drawable.ConstantState)mDrawableFutures.valueAt(i)).newDrawable(mSourceRes));
          mDrawables[paramInt] = localObject;
          mDrawableFutures.removeAt(i);
          if (mDrawableFutures.size() != 0) {
            return localObject;
          }
          mDrawableFutures = null;
          return localObject;
        }
      }
      return null;
      return localObject;
    }
    
    public final int getChildCount()
    {
      return mNumChildren;
    }
    
    public final int getConstantHeight()
    {
      if (!mCheckedConstantSize) {
        computeConstantSize();
      }
      return mConstantHeight;
    }
    
    public final int getConstantMinimumHeight()
    {
      if (!mCheckedConstantSize) {
        computeConstantSize();
      }
      return mConstantMinimumHeight;
    }
    
    public final int getConstantMinimumWidth()
    {
      if (!mCheckedConstantSize) {
        computeConstantSize();
      }
      return mConstantMinimumWidth;
    }
    
    public final Rect getConstantPadding()
    {
      if (mVariablePadding) {
        return null;
      }
      if ((mConstantPadding == null) && (!mCheckedPadding))
      {
        createAllFutures();
        Rect localRect = new Rect();
        int j = mNumChildren;
        Drawable[] arrayOfDrawable = mDrawables;
        Object localObject1 = null;
        int i = 0;
        while (i < j)
        {
          Object localObject3 = localObject1;
          if (arrayOfDrawable[i].getPadding(localRect))
          {
            Object localObject2 = localObject1;
            if (localObject1 == null) {
              localObject2 = new Rect(0, 0, 0, 0);
            }
            if (left > left) {
              left = left;
            }
            if (top > top) {
              top = top;
            }
            if (right > right) {
              right = right;
            }
            localObject3 = localObject2;
            if (bottom > bottom)
            {
              bottom = bottom;
              localObject3 = localObject2;
            }
          }
          i += 1;
          localObject1 = localObject3;
        }
        mCheckedPadding = true;
        mConstantPadding = localObject1;
        return localObject1;
      }
      return mConstantPadding;
    }
    
    public final int getConstantWidth()
    {
      if (!mCheckedConstantSize) {
        computeConstantSize();
      }
      return mConstantWidth;
    }
    
    public final int getEnterFadeDuration()
    {
      return mEnterFadeDuration;
    }
    
    public final int getExitFadeDuration()
    {
      return mExitFadeDuration;
    }
    
    public final int getOpacity()
    {
      if (mCheckedOpacity) {
        return mOpacity;
      }
      createAllFutures();
      int m = mNumChildren;
      Drawable[] arrayOfDrawable = mDrawables;
      if (m > 0) {
        i = arrayOfDrawable[0].getOpacity();
      } else {
        i = -2;
      }
      int k = 1;
      int j = i;
      int i = k;
      while (i < m)
      {
        j = Drawable.resolveOpacity(j, arrayOfDrawable[i].getOpacity());
        i += 1;
      }
      mOpacity = j;
      mCheckedOpacity = true;
      return j;
    }
    
    public void growArray(int paramInt1, int paramInt2)
    {
      Drawable[] arrayOfDrawable = new Drawable[paramInt2];
      System.arraycopy(mDrawables, 0, arrayOfDrawable, 0, paramInt1);
      mDrawables = arrayOfDrawable;
    }
    
    void invalidateCache()
    {
      mCheckedOpacity = false;
      mCheckedStateful = false;
    }
    
    public final boolean isConstantSize()
    {
      return mConstantSize;
    }
    
    public final boolean isStateful()
    {
      if (mCheckedStateful) {
        return mStateful;
      }
      createAllFutures();
      int j = mNumChildren;
      Drawable[] arrayOfDrawable = mDrawables;
      boolean bool2 = false;
      int i = 0;
      boolean bool1;
      for (;;)
      {
        bool1 = bool2;
        if (i >= j) {
          break;
        }
        if (arrayOfDrawable[i].isStateful())
        {
          bool1 = true;
          break;
        }
        i += 1;
      }
      mStateful = bool1;
      mCheckedStateful = true;
      return bool1;
    }
    
    void mutate()
    {
      int j = mNumChildren;
      Drawable[] arrayOfDrawable = mDrawables;
      int i = 0;
      while (i < j)
      {
        if (arrayOfDrawable[i] != null) {
          arrayOfDrawable[i].mutate();
        }
        i += 1;
      }
      mMutated = true;
    }
    
    public final void setConstantSize(boolean paramBoolean)
    {
      mConstantSize = paramBoolean;
    }
    
    public final void setEnterFadeDuration(int paramInt)
    {
      mEnterFadeDuration = paramInt;
    }
    
    public final void setExitFadeDuration(int paramInt)
    {
      mExitFadeDuration = paramInt;
    }
    
    final boolean setLayoutDirection(int paramInt1, int paramInt2)
    {
      int j = mNumChildren;
      Drawable[] arrayOfDrawable = mDrawables;
      int i = 0;
      boolean bool3;
      for (boolean bool2 = false; i < j; bool2 = bool3)
      {
        bool3 = bool2;
        if (arrayOfDrawable[i] != null)
        {
          boolean bool1;
          if (Build.VERSION.SDK_INT >= 23) {
            bool1 = arrayOfDrawable[i].setLayoutDirection(paramInt1);
          } else {
            bool1 = false;
          }
          bool3 = bool2;
          if (i == paramInt2) {
            bool3 = bool1;
          }
        }
        i += 1;
      }
      mLayoutDirection = paramInt1;
      return bool2;
    }
    
    public final void setVariablePadding(boolean paramBoolean)
    {
      mVariablePadding = paramBoolean;
    }
    
    final void updateDensity(Resources paramResources)
    {
      if (paramResources != null)
      {
        mSourceRes = paramResources;
        int i = DrawableContainer.resolveDensity(paramResources, mDensity);
        int j = mDensity;
        mDensity = i;
        if (j != i)
        {
          mCheckedConstantSize = false;
          mCheckedPadding = false;
        }
      }
    }
  }
}
