package androidx.fragment.package_8;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater.Factory2;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.activity.OnBackPressedDispatcherOwner;
import androidx.collection.ArraySet;
import androidx.core.util.DebugUtils;
import androidx.core.util.LogWriter;
import androidx.core.view.OneShotPreDrawListener;
import androidx.lifecycle.Lifecycle.State;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

final class FragmentManagerImpl
  extends FragmentManager
  implements LayoutInflater.Factory2
{
  static final int ANIM_DUR = 220;
  public static final int ANIM_STYLE_CLOSE_ENTER = 3;
  public static final int ANIM_STYLE_CLOSE_EXIT = 4;
  public static final int ANIM_STYLE_FADE_ENTER = 5;
  public static final int ANIM_STYLE_FADE_EXIT = 6;
  public static final int ANIM_STYLE_OPEN_ENTER = 1;
  public static final int ANIM_STYLE_OPEN_EXIT = 2;
  static boolean DEBUG = false;
  static final Interpolator DECELERATE_CUBIC = new DecelerateInterpolator(1.5F);
  static final Interpolator DECELERATE_QUINT = new DecelerateInterpolator(2.5F);
  static final String TAG = "FragmentManager";
  static final String TARGET_REQUEST_CODE_STATE_TAG = "android:target_req_state";
  static final String TARGET_STATE_TAG = "android:target_state";
  static final String USER_VISIBLE_HINT_TAG = "android:user_visible_hint";
  static final String VIEW_STATE_TAG = "android:view_state";
  final HashMap<String, androidx.fragment.app.Fragment> mActive = new HashMap();
  final ArrayList<androidx.fragment.app.Fragment> mAdded = new ArrayList();
  ArrayList<Integer> mAvailBackStackIndices;
  ArrayList<androidx.fragment.app.BackStackRecord> mBackStack;
  ArrayList<androidx.fragment.app.FragmentManager.OnBackStackChangedListener> mBackStackChangeListeners;
  ArrayList<androidx.fragment.app.BackStackRecord> mBackStackIndices;
  FragmentContainer mContainer;
  ArrayList<androidx.fragment.app.Fragment> mCreatedMenus;
  int mCurState = 0;
  boolean mDestroyed;
  Runnable mExecCommit = new Runnable()
  {
    public void run()
    {
      execPendingActions();
    }
  };
  boolean mExecutingActions;
  boolean mHavePendingDeferredStart;
  FragmentHostCallback mHost;
  private final CopyOnWriteArrayList<androidx.fragment.app.FragmentManagerImpl.FragmentLifecycleCallbacksHolder> mLifecycleCallbacks = new CopyOnWriteArrayList();
  boolean mNeedMenuInvalidate;
  int mNextFragmentIndex = 0;
  private FragmentManagerViewModel mNonConfig;
  private final OnBackPressedCallback mOnBackPressedCallback = new OnBackPressedCallback(false)
  {
    public void handleOnBackPressed()
    {
      FragmentManagerImpl.this.handleOnBackPressed();
    }
  };
  private OnBackPressedDispatcher mOnBackPressedDispatcher;
  Fragment mParent;
  ArrayList<androidx.fragment.app.FragmentManagerImpl.OpGenerator> mPendingActions;
  ArrayList<androidx.fragment.app.FragmentManagerImpl.StartEnterTransitionListener> mPostponedTransactions;
  Fragment mPrimaryNav;
  SparseArray<Parcelable> mStateArray = null;
  Bundle mStateBundle = null;
  boolean mStateSaved;
  boolean mStopped;
  ArrayList<androidx.fragment.app.Fragment> mTmpAddedFragments;
  ArrayList<Boolean> mTmpIsPop;
  ArrayList<androidx.fragment.app.BackStackRecord> mTmpRecords;
  
  FragmentManagerImpl() {}
  
  private void addAddedFragments(ArraySet paramArraySet)
  {
    int i = mCurState;
    if (i < 1) {
      return;
    }
    int j = Math.min(i, 3);
    int k = mAdded.size();
    i = 0;
    while (i < k)
    {
      Fragment localFragment = (Fragment)mAdded.get(i);
      if (mState < j)
      {
        moveToState(localFragment, j, localFragment.getNextAnim(), localFragment.getNextTransition(), false);
        if ((mView != null) && (!mHidden) && (mIsNewlyAdded)) {
          paramArraySet.add(localFragment);
        }
      }
      i += 1;
    }
  }
  
  private void animateRemoveFragment(Fragment paramFragment, AnimationOrAnimator paramAnimationOrAnimator, int paramInt)
  {
    View localView = mView;
    ViewGroup localViewGroup = mContainer;
    localViewGroup.startViewTransition(localView);
    paramFragment.setStateAfterAnimating(paramInt);
    if (animation != null)
    {
      paramAnimationOrAnimator = new EndViewTransitionAnimation(animation, localViewGroup, localView);
      paramFragment.setAnimatingAway(mView);
      paramAnimationOrAnimator.setAnimationListener(new FragmentManagerImpl.3(this, localViewGroup, paramFragment));
      mView.startAnimation(paramAnimationOrAnimator);
      return;
    }
    Animator localAnimator = animator;
    paramFragment.setAnimator(animator);
    localAnimator.addListener(new FragmentManagerImpl.4(this, localViewGroup, localView, paramFragment));
    localAnimator.setTarget(mView);
    localAnimator.start();
  }
  
  private void burpActive()
  {
    mActive.values().removeAll(Collections.singleton(null));
  }
  
  private void checkStateLoss()
  {
    if (!isStateSaved()) {
      return;
    }
    throw new IllegalStateException("Can not perform this action after onSaveInstanceState");
  }
  
  private void cleanupExec()
  {
    mExecutingActions = false;
    mTmpIsPop.clear();
    mTmpRecords.clear();
  }
  
  private void dispatchParentPrimaryNavigationFragmentChanged(Fragment paramFragment)
  {
    if ((paramFragment != null) && (mActive.get(mWho) == paramFragment)) {
      paramFragment.performPrimaryNavigationFragmentChanged();
    }
  }
  
  private void dispatchStateChange(int paramInt)
  {
    try
    {
      mExecutingActions = true;
      moveToState(paramInt, false);
      mExecutingActions = false;
      execPendingActions();
      return;
    }
    catch (Throwable localThrowable)
    {
      mExecutingActions = false;
      throw localThrowable;
    }
  }
  
  private void endAnimatingAwayFragments()
  {
    Iterator localIterator = mActive.values().iterator();
    while (localIterator.hasNext())
    {
      Fragment localFragment = (Fragment)localIterator.next();
      if (localFragment != null) {
        if (localFragment.getAnimatingAway() != null)
        {
          int i = localFragment.getStateAfterAnimating();
          View localView = localFragment.getAnimatingAway();
          Animation localAnimation = localView.getAnimation();
          if (localAnimation != null)
          {
            localAnimation.cancel();
            localView.clearAnimation();
          }
          localFragment.setAnimatingAway(null);
          moveToState(localFragment, i, 0, 0, false);
        }
        else if (localFragment.getAnimator() != null)
        {
          localFragment.getAnimator().end();
        }
      }
    }
  }
  
  private void ensureExecReady(boolean paramBoolean)
  {
    if (!mExecutingActions)
    {
      if (mHost != null)
      {
        if (Looper.myLooper() == mHost.getHandler().getLooper())
        {
          if (!paramBoolean) {
            checkStateLoss();
          }
          if (mTmpRecords == null)
          {
            mTmpRecords = new ArrayList();
            mTmpIsPop = new ArrayList();
          }
          mExecutingActions = true;
          try
          {
            executePostponedTransaction(null, null);
            mExecutingActions = false;
            return;
          }
          catch (Throwable localThrowable)
          {
            mExecutingActions = false;
            throw localThrowable;
          }
        }
        throw new IllegalStateException("Must be called from main thread of fragment host");
      }
      throw new IllegalStateException("Fragment host has been destroyed");
    }
    throw new IllegalStateException("FragmentManager is already executing transactions");
  }
  
  private static void executeOps(ArrayList paramArrayList1, ArrayList paramArrayList2, int paramInt1, int paramInt2)
  {
    while (paramInt1 < paramInt2)
    {
      BackStackRecord localBackStackRecord = (BackStackRecord)paramArrayList1.get(paramInt1);
      boolean bool2 = ((Boolean)paramArrayList2.get(paramInt1)).booleanValue();
      boolean bool1 = true;
      if (bool2)
      {
        localBackStackRecord.bumpBackStackNesting(-1);
        if (paramInt1 != paramInt2 - 1) {
          bool1 = false;
        }
        localBackStackRecord.executePopOps(bool1);
      }
      else
      {
        localBackStackRecord.bumpBackStackNesting(1);
        localBackStackRecord.executeOps();
      }
      paramInt1 += 1;
    }
  }
  
  private void executeOpsTogether(ArrayList paramArrayList1, ArrayList paramArrayList2, int paramInt1, int paramInt2)
  {
    int j = paramInt1;
    boolean bool = getmReorderingAllowed;
    Object localObject = mTmpAddedFragments;
    if (localObject == null) {
      mTmpAddedFragments = new ArrayList();
    } else {
      ((ArrayList)localObject).clear();
    }
    mTmpAddedFragments.addAll(mAdded);
    localObject = getPrimaryNavigationFragment();
    int k = paramInt1;
    int i = 0;
    while (k < paramInt2)
    {
      BackStackRecord localBackStackRecord = (BackStackRecord)paramArrayList1.get(k);
      if (!((Boolean)paramArrayList2.get(k)).booleanValue()) {
        localObject = localBackStackRecord.expandOps(mTmpAddedFragments, (Fragment)localObject);
      } else {
        localObject = localBackStackRecord.trackAddedFragmentsInPop(mTmpAddedFragments, (Fragment)localObject);
      }
      if ((i == 0) && (!mAddToBackStack)) {
        i = 0;
      } else {
        i = 1;
      }
      k += 1;
    }
    mTmpAddedFragments.clear();
    if (!bool) {
      FragmentTransition.startTransitions(this, paramArrayList1, paramArrayList2, paramInt1, paramInt2, false);
    }
    executeOps(paramArrayList1, paramArrayList2, paramInt1, paramInt2);
    if (bool)
    {
      localObject = new ArraySet();
      addAddedFragments((ArraySet)localObject);
      k = postponePostponableTransactions(paramArrayList1, paramArrayList2, paramInt1, paramInt2, (ArraySet)localObject);
      makeRemovedFragmentsInvisible((ArraySet)localObject);
    }
    else
    {
      k = paramInt2;
    }
    int m = j;
    if (k != paramInt1)
    {
      m = j;
      if (bool)
      {
        FragmentTransition.startTransitions(this, paramArrayList1, paramArrayList2, paramInt1, k, true);
        moveToState(mCurState, true);
        m = j;
      }
    }
    while (m < paramInt2)
    {
      localObject = (BackStackRecord)paramArrayList1.get(m);
      if ((((Boolean)paramArrayList2.get(m)).booleanValue()) && (mIndex >= 0))
      {
        freeBackStackIndex(mIndex);
        mIndex = -1;
      }
      ((BackStackRecord)localObject).runOnCommitRunnables();
      m += 1;
    }
    if (i != 0) {
      reportBackStackChanged();
    }
  }
  
  private void executePostponedTransaction(ArrayList paramArrayList1, ArrayList paramArrayList2)
  {
    Object localObject = mPostponedTransactions;
    int i;
    if (localObject == null) {
      i = 0;
    } else {
      i = ((ArrayList)localObject).size();
    }
    int j = 0;
    for (int m = i; j < m; m = i)
    {
      localObject = (StartEnterTransitionListener)mPostponedTransactions.get(j);
      int k;
      if ((paramArrayList1 != null) && (!mIsBack))
      {
        i = paramArrayList1.indexOf(mRecord);
        if ((i != -1) && (((Boolean)paramArrayList2.get(i)).booleanValue()))
        {
          mPostponedTransactions.remove(j);
          k = j - 1;
          i = m - 1;
          ((StartEnterTransitionListener)localObject).cancelTransaction();
          break label240;
        }
      }
      if (!((StartEnterTransitionListener)localObject).isReady())
      {
        i = m;
        k = j;
        if (paramArrayList1 != null)
        {
          i = m;
          k = j;
          if (!mRecord.interactsWith(paramArrayList1, 0, paramArrayList1.size())) {}
        }
      }
      else
      {
        mPostponedTransactions.remove(j);
        k = j - 1;
        i = m - 1;
        if ((paramArrayList1 != null) && (!mIsBack))
        {
          j = paramArrayList1.indexOf(mRecord);
          if ((j != -1) && (((Boolean)paramArrayList2.get(j)).booleanValue()))
          {
            ((StartEnterTransitionListener)localObject).cancelTransaction();
            break label240;
          }
        }
        ((StartEnterTransitionListener)localObject).completeTransaction();
      }
      label240:
      j = k + 1;
    }
  }
  
  private Fragment findFragmentUnder(Fragment paramFragment)
  {
    ViewGroup localViewGroup = mContainer;
    View localView = mView;
    if (localViewGroup != null)
    {
      if (localView == null) {
        return null;
      }
      int i = mAdded.indexOf(paramFragment) - 1;
      while (i >= 0)
      {
        paramFragment = (Fragment)mAdded.get(i);
        if ((mContainer == localViewGroup) && (mView != null)) {
          return paramFragment;
        }
        i -= 1;
      }
    }
    return null;
  }
  
  private void forcePostponedTransactions()
  {
    if (mPostponedTransactions != null) {
      while (!mPostponedTransactions.isEmpty()) {
        ((StartEnterTransitionListener)mPostponedTransactions.remove(0)).completeTransaction();
      }
    }
  }
  
  private boolean generateOpsForPendingActions(ArrayList paramArrayList1, ArrayList paramArrayList2)
  {
    try
    {
      ArrayList localArrayList = mPendingActions;
      int i = 0;
      if ((localArrayList != null) && (mPendingActions.size() != 0))
      {
        int j = mPendingActions.size();
        boolean bool = false;
        while (i < j)
        {
          bool |= ((OpGenerator)mPendingActions.get(i)).generateOps(paramArrayList1, paramArrayList2);
          i += 1;
        }
        mPendingActions.clear();
        mHost.getHandler().removeCallbacks(mExecCommit);
        return bool;
      }
      return false;
    }
    catch (Throwable paramArrayList1)
    {
      throw paramArrayList1;
    }
  }
  
  private boolean isMenuAvailable(Fragment paramFragment)
  {
    return ((mHasMenu) && (mMenuVisible)) || (mChildFragmentManager.checkForMenus());
  }
  
  static AnimationOrAnimator makeFadeAnimation(float paramFloat1, float paramFloat2)
  {
    AlphaAnimation localAlphaAnimation = new AlphaAnimation(paramFloat1, paramFloat2);
    localAlphaAnimation.setInterpolator(DECELERATE_CUBIC);
    localAlphaAnimation.setDuration(220L);
    return new AnimationOrAnimator(localAlphaAnimation);
  }
  
  static AnimationOrAnimator makeOpenCloseAnimation(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    AnimationSet localAnimationSet = new AnimationSet(false);
    Object localObject = new ScaleAnimation(paramFloat1, paramFloat2, paramFloat1, paramFloat2, 1, 0.5F, 1, 0.5F);
    ((Animation)localObject).setInterpolator(DECELERATE_QUINT);
    ((Animation)localObject).setDuration(220L);
    localAnimationSet.addAnimation((Animation)localObject);
    localObject = new AlphaAnimation(paramFloat3, paramFloat4);
    ((Animation)localObject).setInterpolator(DECELERATE_CUBIC);
    ((Animation)localObject).setDuration(220L);
    localAnimationSet.addAnimation((Animation)localObject);
    return new AnimationOrAnimator(localAnimationSet);
  }
  
  private void makeRemovedFragmentsInvisible(ArraySet paramArraySet)
  {
    int j = paramArraySet.size();
    int i = 0;
    while (i < j)
    {
      Fragment localFragment = (Fragment)paramArraySet.valueAt(i);
      if (!mAdded)
      {
        View localView = localFragment.requireView();
        mPostponedAlpha = localView.getAlpha();
        localView.setAlpha(0.0F);
      }
      i += 1;
    }
  }
  
  private boolean popBackStackImmediate(String paramString, int paramInt1, int paramInt2)
  {
    execPendingActions();
    ensureExecReady(true);
    Fragment localFragment = mPrimaryNav;
    if ((localFragment != null) && (paramInt1 < 0) && (paramString == null) && (localFragment.getChildFragmentManager().popBackStackImmediate())) {
      return true;
    }
    boolean bool = popBackStackState(mTmpRecords, mTmpIsPop, paramString, paramInt1, paramInt2);
    if (bool)
    {
      mExecutingActions = true;
      try
      {
        removeRedundantOperationsAndExecute(mTmpRecords, mTmpIsPop);
        cleanupExec();
      }
      catch (Throwable paramString)
      {
        cleanupExec();
        throw paramString;
      }
    }
    updateOnBackPressedCallbackEnabled();
    doPendingDeferredStart();
    burpActive();
    return bool;
  }
  
  private int postponePostponableTransactions(ArrayList paramArrayList1, ArrayList paramArrayList2, int paramInt1, int paramInt2, ArraySet paramArraySet)
  {
    int i = paramInt2 - 1;
    int k;
    for (int j = paramInt2; i >= paramInt1; j = k)
    {
      BackStackRecord localBackStackRecord = (BackStackRecord)paramArrayList1.get(i);
      boolean bool = ((Boolean)paramArrayList2.get(i)).booleanValue();
      int m;
      if ((localBackStackRecord.isPostponed()) && (!localBackStackRecord.interactsWith(paramArrayList1, i + 1, paramInt2))) {
        m = 1;
      } else {
        m = 0;
      }
      k = j;
      if (m != 0)
      {
        if (mPostponedTransactions == null) {
          mPostponedTransactions = new ArrayList();
        }
        StartEnterTransitionListener localStartEnterTransitionListener = new StartEnterTransitionListener(localBackStackRecord, bool);
        mPostponedTransactions.add(localStartEnterTransitionListener);
        localBackStackRecord.setOnStartPostponedListener(localStartEnterTransitionListener);
        if (bool) {
          localBackStackRecord.executeOps();
        } else {
          localBackStackRecord.executePopOps(false);
        }
        k = j - 1;
        if (i != k)
        {
          paramArrayList1.remove(i);
          paramArrayList1.add(k, localBackStackRecord);
        }
        addAddedFragments(paramArraySet);
      }
      i -= 1;
    }
    return j;
  }
  
  private void removeRedundantOperationsAndExecute(ArrayList paramArrayList1, ArrayList paramArrayList2)
  {
    if (paramArrayList1 != null)
    {
      if (paramArrayList1.isEmpty()) {
        return;
      }
      if ((paramArrayList2 != null) && (paramArrayList1.size() == paramArrayList2.size()))
      {
        executePostponedTransaction(paramArrayList1, paramArrayList2);
        int n = paramArrayList1.size();
        int i = 0;
        int j;
        for (int k = 0; i < n; k = j)
        {
          int m = i;
          j = k;
          if (!getmReorderingAllowed)
          {
            if (k != i) {
              executeOpsTogether(paramArrayList1, paramArrayList2, k, i);
            }
            k = i + 1;
            j = k;
            if (((Boolean)paramArrayList2.get(i)).booleanValue()) {
              for (;;)
              {
                j = k;
                if (k >= n) {
                  break;
                }
                j = k;
                if (!((Boolean)paramArrayList2.get(k)).booleanValue()) {
                  break;
                }
                j = k;
                if (getmReorderingAllowed) {
                  break;
                }
                k += 1;
              }
            }
            executeOpsTogether(paramArrayList1, paramArrayList2, i, j);
            m = j - 1;
          }
          i = m + 1;
        }
        if (k != n) {
          executeOpsTogether(paramArrayList1, paramArrayList2, k, n);
        }
      }
      else
      {
        throw new IllegalStateException("Internal error with the back stack records");
      }
    }
  }
  
  public static int reverseTransit(int paramInt)
  {
    if (paramInt != 4097)
    {
      if (paramInt != 4099)
      {
        if (paramInt != 8194) {
          return 0;
        }
        return 4097;
      }
      return 4099;
    }
    return 8194;
  }
  
  private void throwException(RuntimeException paramRuntimeException)
  {
    Log.e("FragmentManager", paramRuntimeException.getMessage());
    Log.e("FragmentManager", "Activity state:");
    PrintWriter localPrintWriter = new PrintWriter(new LogWriter("FragmentManager"));
    FragmentHostCallback localFragmentHostCallback = mHost;
    if (localFragmentHostCallback != null) {
      try
      {
        localFragmentHostCallback.onDump("  ", null, localPrintWriter, new String[0]);
      }
      catch (Exception localException1)
      {
        Log.e("FragmentManager", "Failed dumping state", localException1);
      }
    } else {
      try
      {
        dump("  ", null, localException1, new String[0]);
      }
      catch (Exception localException2)
      {
        Log.e("FragmentManager", "Failed dumping state", localException2);
      }
    }
    throw paramRuntimeException;
  }
  
  public static int transitToStyleIndex(int paramInt, boolean paramBoolean)
  {
    if (paramInt != 4097)
    {
      if (paramInt != 4099)
      {
        if (paramInt != 8194) {
          return -1;
        }
        if (paramBoolean) {
          return 3;
        }
        return 4;
      }
      if (paramBoolean) {
        return 5;
      }
      return 6;
    }
    if (paramBoolean) {
      return 1;
    }
    return 2;
  }
  
  private void updateOnBackPressedCallbackEnabled()
  {
    Object localObject = mPendingActions;
    boolean bool = true;
    if ((localObject != null) && (!((ArrayList)localObject).isEmpty()))
    {
      mOnBackPressedCallback.setEnabled(true);
      return;
    }
    localObject = mOnBackPressedCallback;
    if ((getBackStackEntryCount() <= 0) || (!isPrimaryNavigation(mParent))) {
      bool = false;
    }
    ((OnBackPressedCallback)localObject).setEnabled(bool);
  }
  
  void addBackStackState(BackStackRecord paramBackStackRecord)
  {
    if (mBackStack == null) {
      mBackStack = new ArrayList();
    }
    mBackStack.add(paramBackStackRecord);
  }
  
  public void addFragment(Fragment paramFragment, boolean paramBoolean)
  {
    Object localObject;
    if (DEBUG)
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("add: ");
      ((StringBuilder)localObject).append(paramFragment);
      Log.v("FragmentManager", ((StringBuilder)localObject).toString());
    }
    makeActive(paramFragment);
    if (!mDetached)
    {
      if (!mAdded.contains(paramFragment))
      {
        localObject = mAdded;
        try
        {
          mAdded.add(paramFragment);
          mAdded = true;
          mRemoving = false;
          if (mView == null) {
            mHiddenChanged = false;
          }
          if (isMenuAvailable(paramFragment)) {
            mNeedMenuInvalidate = true;
          }
          if (!paramBoolean) {
            return;
          }
          moveToState(paramFragment);
          return;
        }
        catch (Throwable paramFragment)
        {
          throw paramFragment;
        }
      }
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("Fragment already added: ");
      ((StringBuilder)localObject).append(paramFragment);
      throw new IllegalStateException(((StringBuilder)localObject).toString());
    }
  }
  
  public void addOnBackStackChangedListener(FragmentManager.OnBackStackChangedListener paramOnBackStackChangedListener)
  {
    if (mBackStackChangeListeners == null) {
      mBackStackChangeListeners = new ArrayList();
    }
    mBackStackChangeListeners.add(paramOnBackStackChangedListener);
  }
  
  void addRetainedFragment(Fragment paramFragment)
  {
    if (isStateSaved())
    {
      if (DEBUG) {
        Log.v("FragmentManager", "Ignoring addRetainedFragment as the state is already saved");
      }
    }
    else if ((mNonConfig.addRetainedFragment(paramFragment)) && (DEBUG))
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Updating retained Fragments: Added ");
      localStringBuilder.append(paramFragment);
      Log.v("FragmentManager", localStringBuilder.toString());
    }
  }
  
  public int allocBackStackIndex(BackStackRecord paramBackStackRecord)
  {
    try
    {
      StringBuilder localStringBuilder;
      if ((mAvailBackStackIndices != null) && (mAvailBackStackIndices.size() > 0))
      {
        i = ((Integer)mAvailBackStackIndices.remove(mAvailBackStackIndices.size() - 1)).intValue();
        if (DEBUG)
        {
          localStringBuilder = new StringBuilder();
          localStringBuilder.append("Adding back stack index ");
          localStringBuilder.append(i);
          localStringBuilder.append(" with ");
          localStringBuilder.append(paramBackStackRecord);
          Log.v("FragmentManager", localStringBuilder.toString());
        }
        mBackStackIndices.set(i, paramBackStackRecord);
        return i;
      }
      if (mBackStackIndices == null) {
        mBackStackIndices = new ArrayList();
      }
      int i = mBackStackIndices.size();
      if (DEBUG)
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("Setting back stack index ");
        localStringBuilder.append(i);
        localStringBuilder.append(" to ");
        localStringBuilder.append(paramBackStackRecord);
        Log.v("FragmentManager", localStringBuilder.toString());
      }
      mBackStackIndices.add(paramBackStackRecord);
      return i;
    }
    catch (Throwable paramBackStackRecord)
    {
      throw paramBackStackRecord;
    }
  }
  
  public void attachController(FragmentHostCallback paramFragmentHostCallback, FragmentContainer paramFragmentContainer, Fragment paramFragment)
  {
    if (mHost == null)
    {
      mHost = paramFragmentHostCallback;
      mContainer = paramFragmentContainer;
      mParent = paramFragment;
      if (mParent != null) {
        updateOnBackPressedCallbackEnabled();
      }
      if ((paramFragmentHostCallback instanceof OnBackPressedDispatcherOwner))
      {
        paramFragmentContainer = (OnBackPressedDispatcherOwner)paramFragmentHostCallback;
        mOnBackPressedDispatcher = ((OnBackPressedDispatcherOwner)paramFragmentContainer).getOnBackPressedDispatcher();
        if (paramFragment != null) {
          paramFragmentContainer = paramFragment;
        }
        mOnBackPressedDispatcher.addCallback(paramFragmentContainer, mOnBackPressedCallback);
      }
      if (paramFragment != null)
      {
        mNonConfig = mFragmentManager.getChildNonConfig(paramFragment);
        return;
      }
      if ((paramFragmentHostCallback instanceof ViewModelStoreOwner))
      {
        mNonConfig = FragmentManagerViewModel.getInstance(((ViewModelStoreOwner)paramFragmentHostCallback).getViewModelStore());
        return;
      }
      mNonConfig = new FragmentManagerViewModel(false);
      return;
    }
    throw new IllegalStateException("Already attached");
  }
  
  public void attachFragment(Fragment paramFragment)
  {
    Object localObject;
    if (DEBUG)
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("attach: ");
      ((StringBuilder)localObject).append(paramFragment);
      Log.v("FragmentManager", ((StringBuilder)localObject).toString());
    }
    if (mDetached)
    {
      mDetached = false;
      if (!mAdded) {
        if (!mAdded.contains(paramFragment))
        {
          if (DEBUG)
          {
            localObject = new StringBuilder();
            ((StringBuilder)localObject).append("add from attach: ");
            ((StringBuilder)localObject).append(paramFragment);
            Log.v("FragmentManager", ((StringBuilder)localObject).toString());
          }
          localObject = mAdded;
          try
          {
            mAdded.add(paramFragment);
            mAdded = true;
            if (!isMenuAvailable(paramFragment)) {
              return;
            }
            mNeedMenuInvalidate = true;
            return;
          }
          catch (Throwable paramFragment)
          {
            throw paramFragment;
          }
        }
        else
        {
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("Fragment already added: ");
          ((StringBuilder)localObject).append(paramFragment);
          throw new IllegalStateException(((StringBuilder)localObject).toString());
        }
      }
    }
  }
  
  public FragmentTransaction beginTransaction()
  {
    return new BackStackRecord(this);
  }
  
  boolean checkForMenus()
  {
    Iterator localIterator = mActive.values().iterator();
    boolean bool1 = false;
    while (localIterator.hasNext())
    {
      Fragment localFragment = (Fragment)localIterator.next();
      boolean bool2 = bool1;
      if (localFragment != null) {
        bool2 = isMenuAvailable(localFragment);
      }
      bool1 = bool2;
      if (bool2) {
        return true;
      }
    }
    return false;
  }
  
  void completeExecute(BackStackRecord paramBackStackRecord, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    if (paramBoolean1) {
      paramBackStackRecord.executePopOps(paramBoolean3);
    } else {
      paramBackStackRecord.executeOps();
    }
    Object localObject1 = new ArrayList(1);
    Object localObject2 = new ArrayList(1);
    ((ArrayList)localObject1).add(paramBackStackRecord);
    ((ArrayList)localObject2).add(Boolean.valueOf(paramBoolean1));
    if (paramBoolean2) {
      FragmentTransition.startTransitions(this, (ArrayList)localObject1, (ArrayList)localObject2, 0, 1, true);
    }
    if (paramBoolean3) {
      moveToState(mCurState, true);
    }
    localObject1 = mActive.values().iterator();
    while (((Iterator)localObject1).hasNext())
    {
      localObject2 = (Fragment)((Iterator)localObject1).next();
      if ((localObject2 != null) && (mView != null) && (mIsNewlyAdded) && (paramBackStackRecord.interactsWith(mContainerId)))
      {
        if (mPostponedAlpha > 0.0F) {
          mView.setAlpha(mPostponedAlpha);
        }
        if (paramBoolean3)
        {
          mPostponedAlpha = 0.0F;
        }
        else
        {
          mPostponedAlpha = -1.0F;
          mIsNewlyAdded = false;
        }
      }
    }
  }
  
  void completeShowHideFragment(Fragment paramFragment)
  {
    if (mView != null)
    {
      AnimationOrAnimator localAnimationOrAnimator = loadAnimation(paramFragment, paramFragment.getNextTransition(), mHidden ^ true, paramFragment.getNextTransitionStyle());
      if ((localAnimationOrAnimator != null) && (animator != null))
      {
        animator.setTarget(mView);
        if (mHidden)
        {
          if (paramFragment.isHideReplaced())
          {
            paramFragment.setHideReplaced(false);
          }
          else
          {
            ViewGroup localViewGroup = mContainer;
            View localView = mView;
            localViewGroup.startViewTransition(localView);
            animator.addListener(new FragmentManagerImpl.5(this, localViewGroup, localView, paramFragment));
          }
        }
        else {
          mView.setVisibility(0);
        }
        animator.start();
      }
      else
      {
        if (localAnimationOrAnimator != null)
        {
          mView.startAnimation(animation);
          animation.start();
        }
        int i;
        if ((mHidden) && (!paramFragment.isHideReplaced())) {
          i = 8;
        } else {
          i = 0;
        }
        mView.setVisibility(i);
        if (paramFragment.isHideReplaced()) {
          paramFragment.setHideReplaced(false);
        }
      }
    }
    if ((mAdded) && (isMenuAvailable(paramFragment))) {
      mNeedMenuInvalidate = true;
    }
    mHiddenChanged = false;
    paramFragment.onHiddenChanged(mHidden);
  }
  
  public void detachFragment(Fragment paramFragment)
  {
    Object localObject;
    if (DEBUG)
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("detach: ");
      ((StringBuilder)localObject).append(paramFragment);
      Log.v("FragmentManager", ((StringBuilder)localObject).toString());
    }
    if (!mDetached)
    {
      mDetached = true;
      if (mAdded)
      {
        if (DEBUG)
        {
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("remove from detach: ");
          ((StringBuilder)localObject).append(paramFragment);
          Log.v("FragmentManager", ((StringBuilder)localObject).toString());
        }
        localObject = mAdded;
        try
        {
          mAdded.remove(paramFragment);
          if (isMenuAvailable(paramFragment)) {
            mNeedMenuInvalidate = true;
          }
          mAdded = false;
          return;
        }
        catch (Throwable paramFragment)
        {
          throw paramFragment;
        }
      }
    }
  }
  
  public void dispatchActivityCreated()
  {
    mStateSaved = false;
    mStopped = false;
    dispatchStateChange(2);
  }
  
  public void dispatchConfigurationChanged(Configuration paramConfiguration)
  {
    int i = 0;
    while (i < mAdded.size())
    {
      Fragment localFragment = (Fragment)mAdded.get(i);
      if (localFragment != null) {
        localFragment.performConfigurationChanged(paramConfiguration);
      }
      i += 1;
    }
  }
  
  public boolean dispatchContextItemSelected(MenuItem paramMenuItem)
  {
    if (mCurState < 1) {
      return false;
    }
    int i = 0;
    while (i < mAdded.size())
    {
      Fragment localFragment = (Fragment)mAdded.get(i);
      if ((localFragment != null) && (localFragment.performContextItemSelected(paramMenuItem))) {
        return true;
      }
      i += 1;
    }
    return false;
  }
  
  public void dispatchCreate()
  {
    mStateSaved = false;
    mStopped = false;
    dispatchStateChange(1);
  }
  
  public boolean dispatchCreateOptionsMenu(Menu paramMenu, MenuInflater paramMenuInflater)
  {
    int i = mCurState;
    int j = 0;
    if (i < 1) {
      return false;
    }
    Object localObject1 = null;
    i = 0;
    boolean bool2;
    for (boolean bool1 = false; i < mAdded.size(); bool1 = bool2)
    {
      Fragment localFragment = (Fragment)mAdded.get(i);
      Object localObject2 = localObject1;
      bool2 = bool1;
      if (localFragment != null)
      {
        localObject2 = localObject1;
        bool2 = bool1;
        if (localFragment.performCreateOptionsMenu(paramMenu, paramMenuInflater))
        {
          localObject2 = localObject1;
          if (localObject1 == null) {
            localObject2 = new ArrayList();
          }
          ((ArrayList)localObject2).add(localFragment);
          bool2 = true;
        }
      }
      i += 1;
      localObject1 = localObject2;
    }
    if (mCreatedMenus != null)
    {
      i = j;
      while (i < mCreatedMenus.size())
      {
        paramMenu = (Fragment)mCreatedMenus.get(i);
        if ((localObject1 == null) || (!localObject1.contains(paramMenu))) {
          paramMenu.onDestroyOptionsMenu();
        }
        i += 1;
      }
    }
    mCreatedMenus = localObject1;
    return bool1;
  }
  
  public void dispatchDestroy()
  {
    mDestroyed = true;
    execPendingActions();
    dispatchStateChange(0);
    mHost = null;
    mContainer = null;
    mParent = null;
    if (mOnBackPressedDispatcher != null)
    {
      mOnBackPressedCallback.remove();
      mOnBackPressedDispatcher = null;
    }
  }
  
  public void dispatchDestroyView()
  {
    dispatchStateChange(1);
  }
  
  public void dispatchLowMemory()
  {
    int i = 0;
    while (i < mAdded.size())
    {
      Fragment localFragment = (Fragment)mAdded.get(i);
      if (localFragment != null) {
        localFragment.performLowMemory();
      }
      i += 1;
    }
  }
  
  public void dispatchMultiWindowModeChanged(boolean paramBoolean)
  {
    int i = mAdded.size() - 1;
    while (i >= 0)
    {
      Fragment localFragment = (Fragment)mAdded.get(i);
      if (localFragment != null) {
        localFragment.performMultiWindowModeChanged(paramBoolean);
      }
      i -= 1;
    }
  }
  
  void dispatchOnFragmentActivityCreated(Fragment paramFragment, Bundle paramBundle, boolean paramBoolean)
  {
    Object localObject = mParent;
    if (localObject != null)
    {
      localObject = ((Fragment)localObject).getFragmentManager();
      if ((localObject instanceof FragmentManagerImpl)) {
        ((FragmentManagerImpl)localObject).dispatchOnFragmentActivityCreated(paramFragment, paramBundle, true);
      }
    }
    localObject = mLifecycleCallbacks.iterator();
    while (((Iterator)localObject).hasNext())
    {
      FragmentLifecycleCallbacksHolder localFragmentLifecycleCallbacksHolder = (FragmentLifecycleCallbacksHolder)((Iterator)localObject).next();
      if ((!paramBoolean) || (mRecursive)) {
        mCallback.onFragmentActivityCreated(this, paramFragment, paramBundle);
      }
    }
  }
  
  void dispatchOnFragmentAttached(Fragment paramFragment, Context paramContext, boolean paramBoolean)
  {
    Object localObject = mParent;
    if (localObject != null)
    {
      localObject = ((Fragment)localObject).getFragmentManager();
      if ((localObject instanceof FragmentManagerImpl)) {
        ((FragmentManagerImpl)localObject).dispatchOnFragmentAttached(paramFragment, paramContext, true);
      }
    }
    localObject = mLifecycleCallbacks.iterator();
    while (((Iterator)localObject).hasNext())
    {
      FragmentLifecycleCallbacksHolder localFragmentLifecycleCallbacksHolder = (FragmentLifecycleCallbacksHolder)((Iterator)localObject).next();
      if ((!paramBoolean) || (mRecursive)) {
        mCallback.onFragmentAttached(this, paramFragment, paramContext);
      }
    }
  }
  
  void dispatchOnFragmentCreated(Fragment paramFragment, Bundle paramBundle, boolean paramBoolean)
  {
    Object localObject = mParent;
    if (localObject != null)
    {
      localObject = ((Fragment)localObject).getFragmentManager();
      if ((localObject instanceof FragmentManagerImpl)) {
        ((FragmentManagerImpl)localObject).dispatchOnFragmentCreated(paramFragment, paramBundle, true);
      }
    }
    localObject = mLifecycleCallbacks.iterator();
    while (((Iterator)localObject).hasNext())
    {
      FragmentLifecycleCallbacksHolder localFragmentLifecycleCallbacksHolder = (FragmentLifecycleCallbacksHolder)((Iterator)localObject).next();
      if ((!paramBoolean) || (mRecursive)) {
        mCallback.onFragmentCreated(this, paramFragment, paramBundle);
      }
    }
  }
  
  void dispatchOnFragmentDestroyed(Fragment paramFragment, boolean paramBoolean)
  {
    Object localObject = mParent;
    if (localObject != null)
    {
      localObject = ((Fragment)localObject).getFragmentManager();
      if ((localObject instanceof FragmentManagerImpl)) {
        ((FragmentManagerImpl)localObject).dispatchOnFragmentDestroyed(paramFragment, true);
      }
    }
    localObject = mLifecycleCallbacks.iterator();
    while (((Iterator)localObject).hasNext())
    {
      FragmentLifecycleCallbacksHolder localFragmentLifecycleCallbacksHolder = (FragmentLifecycleCallbacksHolder)((Iterator)localObject).next();
      if ((!paramBoolean) || (mRecursive)) {
        mCallback.onFragmentDestroyed(this, paramFragment);
      }
    }
  }
  
  void dispatchOnFragmentDetached(Fragment paramFragment, boolean paramBoolean)
  {
    Object localObject = mParent;
    if (localObject != null)
    {
      localObject = ((Fragment)localObject).getFragmentManager();
      if ((localObject instanceof FragmentManagerImpl)) {
        ((FragmentManagerImpl)localObject).dispatchOnFragmentDetached(paramFragment, true);
      }
    }
    localObject = mLifecycleCallbacks.iterator();
    while (((Iterator)localObject).hasNext())
    {
      FragmentLifecycleCallbacksHolder localFragmentLifecycleCallbacksHolder = (FragmentLifecycleCallbacksHolder)((Iterator)localObject).next();
      if ((!paramBoolean) || (mRecursive)) {
        mCallback.onFragmentDetached(this, paramFragment);
      }
    }
  }
  
  void dispatchOnFragmentPaused(Fragment paramFragment, boolean paramBoolean)
  {
    Object localObject = mParent;
    if (localObject != null)
    {
      localObject = ((Fragment)localObject).getFragmentManager();
      if ((localObject instanceof FragmentManagerImpl)) {
        ((FragmentManagerImpl)localObject).dispatchOnFragmentPaused(paramFragment, true);
      }
    }
    localObject = mLifecycleCallbacks.iterator();
    while (((Iterator)localObject).hasNext())
    {
      FragmentLifecycleCallbacksHolder localFragmentLifecycleCallbacksHolder = (FragmentLifecycleCallbacksHolder)((Iterator)localObject).next();
      if ((!paramBoolean) || (mRecursive)) {
        mCallback.onFragmentPaused(this, paramFragment);
      }
    }
  }
  
  void dispatchOnFragmentPreAttached(Fragment paramFragment, Context paramContext, boolean paramBoolean)
  {
    Object localObject = mParent;
    if (localObject != null)
    {
      localObject = ((Fragment)localObject).getFragmentManager();
      if ((localObject instanceof FragmentManagerImpl)) {
        ((FragmentManagerImpl)localObject).dispatchOnFragmentPreAttached(paramFragment, paramContext, true);
      }
    }
    localObject = mLifecycleCallbacks.iterator();
    while (((Iterator)localObject).hasNext())
    {
      FragmentLifecycleCallbacksHolder localFragmentLifecycleCallbacksHolder = (FragmentLifecycleCallbacksHolder)((Iterator)localObject).next();
      if ((!paramBoolean) || (mRecursive)) {
        mCallback.onFragmentPreAttached(this, paramFragment, paramContext);
      }
    }
  }
  
  void dispatchOnFragmentPreCreated(Fragment paramFragment, Bundle paramBundle, boolean paramBoolean)
  {
    Object localObject = mParent;
    if (localObject != null)
    {
      localObject = ((Fragment)localObject).getFragmentManager();
      if ((localObject instanceof FragmentManagerImpl)) {
        ((FragmentManagerImpl)localObject).dispatchOnFragmentPreCreated(paramFragment, paramBundle, true);
      }
    }
    localObject = mLifecycleCallbacks.iterator();
    while (((Iterator)localObject).hasNext())
    {
      FragmentLifecycleCallbacksHolder localFragmentLifecycleCallbacksHolder = (FragmentLifecycleCallbacksHolder)((Iterator)localObject).next();
      if ((!paramBoolean) || (mRecursive)) {
        mCallback.onFragmentPreCreated(this, paramFragment, paramBundle);
      }
    }
  }
  
  void dispatchOnFragmentResumed(Fragment paramFragment, boolean paramBoolean)
  {
    Object localObject = mParent;
    if (localObject != null)
    {
      localObject = ((Fragment)localObject).getFragmentManager();
      if ((localObject instanceof FragmentManagerImpl)) {
        ((FragmentManagerImpl)localObject).dispatchOnFragmentResumed(paramFragment, true);
      }
    }
    localObject = mLifecycleCallbacks.iterator();
    while (((Iterator)localObject).hasNext())
    {
      FragmentLifecycleCallbacksHolder localFragmentLifecycleCallbacksHolder = (FragmentLifecycleCallbacksHolder)((Iterator)localObject).next();
      if ((!paramBoolean) || (mRecursive)) {
        mCallback.onFragmentResumed(this, paramFragment);
      }
    }
  }
  
  void dispatchOnFragmentSaveInstanceState(Fragment paramFragment, Bundle paramBundle, boolean paramBoolean)
  {
    Object localObject = mParent;
    if (localObject != null)
    {
      localObject = ((Fragment)localObject).getFragmentManager();
      if ((localObject instanceof FragmentManagerImpl)) {
        ((FragmentManagerImpl)localObject).dispatchOnFragmentSaveInstanceState(paramFragment, paramBundle, true);
      }
    }
    localObject = mLifecycleCallbacks.iterator();
    while (((Iterator)localObject).hasNext())
    {
      FragmentLifecycleCallbacksHolder localFragmentLifecycleCallbacksHolder = (FragmentLifecycleCallbacksHolder)((Iterator)localObject).next();
      if ((!paramBoolean) || (mRecursive)) {
        mCallback.onFragmentSaveInstanceState(this, paramFragment, paramBundle);
      }
    }
  }
  
  void dispatchOnFragmentStarted(Fragment paramFragment, boolean paramBoolean)
  {
    Object localObject = mParent;
    if (localObject != null)
    {
      localObject = ((Fragment)localObject).getFragmentManager();
      if ((localObject instanceof FragmentManagerImpl)) {
        ((FragmentManagerImpl)localObject).dispatchOnFragmentStarted(paramFragment, true);
      }
    }
    localObject = mLifecycleCallbacks.iterator();
    while (((Iterator)localObject).hasNext())
    {
      FragmentLifecycleCallbacksHolder localFragmentLifecycleCallbacksHolder = (FragmentLifecycleCallbacksHolder)((Iterator)localObject).next();
      if ((!paramBoolean) || (mRecursive)) {
        mCallback.onFragmentStarted(this, paramFragment);
      }
    }
  }
  
  void dispatchOnFragmentStopped(Fragment paramFragment, boolean paramBoolean)
  {
    Object localObject = mParent;
    if (localObject != null)
    {
      localObject = ((Fragment)localObject).getFragmentManager();
      if ((localObject instanceof FragmentManagerImpl)) {
        ((FragmentManagerImpl)localObject).dispatchOnFragmentStopped(paramFragment, true);
      }
    }
    localObject = mLifecycleCallbacks.iterator();
    while (((Iterator)localObject).hasNext())
    {
      FragmentLifecycleCallbacksHolder localFragmentLifecycleCallbacksHolder = (FragmentLifecycleCallbacksHolder)((Iterator)localObject).next();
      if ((!paramBoolean) || (mRecursive)) {
        mCallback.onFragmentStopped(this, paramFragment);
      }
    }
  }
  
  void dispatchOnFragmentViewCreated(Fragment paramFragment, View paramView, Bundle paramBundle, boolean paramBoolean)
  {
    Object localObject = mParent;
    if (localObject != null)
    {
      localObject = ((Fragment)localObject).getFragmentManager();
      if ((localObject instanceof FragmentManagerImpl)) {
        ((FragmentManagerImpl)localObject).dispatchOnFragmentViewCreated(paramFragment, paramView, paramBundle, true);
      }
    }
    localObject = mLifecycleCallbacks.iterator();
    while (((Iterator)localObject).hasNext())
    {
      FragmentLifecycleCallbacksHolder localFragmentLifecycleCallbacksHolder = (FragmentLifecycleCallbacksHolder)((Iterator)localObject).next();
      if ((!paramBoolean) || (mRecursive)) {
        mCallback.onFragmentViewCreated(this, paramFragment, paramView, paramBundle);
      }
    }
  }
  
  void dispatchOnFragmentViewDestroyed(Fragment paramFragment, boolean paramBoolean)
  {
    Object localObject = mParent;
    if (localObject != null)
    {
      localObject = ((Fragment)localObject).getFragmentManager();
      if ((localObject instanceof FragmentManagerImpl)) {
        ((FragmentManagerImpl)localObject).dispatchOnFragmentViewDestroyed(paramFragment, true);
      }
    }
    localObject = mLifecycleCallbacks.iterator();
    while (((Iterator)localObject).hasNext())
    {
      FragmentLifecycleCallbacksHolder localFragmentLifecycleCallbacksHolder = (FragmentLifecycleCallbacksHolder)((Iterator)localObject).next();
      if ((!paramBoolean) || (mRecursive)) {
        mCallback.onFragmentViewDestroyed(this, paramFragment);
      }
    }
  }
  
  public boolean dispatchOptionsItemSelected(MenuItem paramMenuItem)
  {
    if (mCurState < 1) {
      return false;
    }
    int i = 0;
    while (i < mAdded.size())
    {
      Fragment localFragment = (Fragment)mAdded.get(i);
      if ((localFragment != null) && (localFragment.performOptionsItemSelected(paramMenuItem))) {
        return true;
      }
      i += 1;
    }
    return false;
  }
  
  public void dispatchOptionsMenuClosed(Menu paramMenu)
  {
    if (mCurState < 1) {
      return;
    }
    int i = 0;
    while (i < mAdded.size())
    {
      Fragment localFragment = (Fragment)mAdded.get(i);
      if (localFragment != null) {
        localFragment.performOptionsMenuClosed(paramMenu);
      }
      i += 1;
    }
  }
  
  public void dispatchPause()
  {
    dispatchStateChange(3);
  }
  
  public void dispatchPictureInPictureModeChanged(boolean paramBoolean)
  {
    int i = mAdded.size() - 1;
    while (i >= 0)
    {
      Fragment localFragment = (Fragment)mAdded.get(i);
      if (localFragment != null) {
        localFragment.performPictureInPictureModeChanged(paramBoolean);
      }
      i -= 1;
    }
  }
  
  public boolean dispatchPrepareOptionsMenu(Menu paramMenu)
  {
    int j = mCurState;
    int i = 0;
    if (j < 1) {
      return false;
    }
    boolean bool2;
    for (boolean bool1 = false; i < mAdded.size(); bool1 = bool2)
    {
      Fragment localFragment = (Fragment)mAdded.get(i);
      bool2 = bool1;
      if (localFragment != null)
      {
        bool2 = bool1;
        if (localFragment.performPrepareOptionsMenu(paramMenu)) {
          bool2 = true;
        }
      }
      i += 1;
    }
    return bool1;
  }
  
  void dispatchPrimaryNavigationFragmentChanged()
  {
    updateOnBackPressedCallbackEnabled();
    dispatchParentPrimaryNavigationFragmentChanged(mPrimaryNav);
  }
  
  public void dispatchResume()
  {
    mStateSaved = false;
    mStopped = false;
    dispatchStateChange(4);
  }
  
  public void dispatchStart()
  {
    mStateSaved = false;
    mStopped = false;
    dispatchStateChange(3);
  }
  
  public void dispatchStop()
  {
    mStopped = true;
    dispatchStateChange(2);
  }
  
  void doPendingDeferredStart()
  {
    if (mHavePendingDeferredStart)
    {
      mHavePendingDeferredStart = false;
      startPendingDeferredFragments();
    }
  }
  
  public void dump(String paramString, FileDescriptor paramFileDescriptor, PrintWriter paramPrintWriter, String[] paramArrayOfString)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append(paramString);
    ((StringBuilder)localObject).append("    ");
    localObject = ((StringBuilder)localObject).toString();
    if (!mActive.isEmpty())
    {
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("Active Fragments in ");
      paramPrintWriter.print(Integer.toHexString(System.identityHashCode(this)));
      paramPrintWriter.println(":");
      Iterator localIterator = mActive.values().iterator();
      while (localIterator.hasNext())
      {
        Fragment localFragment = (Fragment)localIterator.next();
        paramPrintWriter.print(paramString);
        paramPrintWriter.println(localFragment);
        if (localFragment != null) {
          localFragment.dump((String)localObject, paramFileDescriptor, paramPrintWriter, paramArrayOfString);
        }
      }
    }
    int k = mAdded.size();
    int j = 0;
    int i;
    if (k > 0)
    {
      paramPrintWriter.print(paramString);
      paramPrintWriter.println("Added Fragments:");
      i = 0;
      while (i < k)
      {
        paramFileDescriptor = (Fragment)mAdded.get(i);
        paramPrintWriter.print(paramString);
        paramPrintWriter.print("  #");
        paramPrintWriter.print(i);
        paramPrintWriter.print(": ");
        paramPrintWriter.println(paramFileDescriptor.toString());
        i += 1;
      }
    }
    paramFileDescriptor = mCreatedMenus;
    if (paramFileDescriptor != null)
    {
      k = paramFileDescriptor.size();
      if (k > 0)
      {
        paramPrintWriter.print(paramString);
        paramPrintWriter.println("Fragments Created Menus:");
        i = 0;
        while (i < k)
        {
          paramFileDescriptor = (Fragment)mCreatedMenus.get(i);
          paramPrintWriter.print(paramString);
          paramPrintWriter.print("  #");
          paramPrintWriter.print(i);
          paramPrintWriter.print(": ");
          paramPrintWriter.println(paramFileDescriptor.toString());
          i += 1;
        }
      }
    }
    paramFileDescriptor = mBackStack;
    if (paramFileDescriptor != null)
    {
      k = paramFileDescriptor.size();
      if (k > 0)
      {
        paramPrintWriter.print(paramString);
        paramPrintWriter.println("Back Stack:");
        i = 0;
        while (i < k)
        {
          paramFileDescriptor = (BackStackRecord)mBackStack.get(i);
          paramPrintWriter.print(paramString);
          paramPrintWriter.print("  #");
          paramPrintWriter.print(i);
          paramPrintWriter.print(": ");
          paramPrintWriter.println(paramFileDescriptor.toString());
          paramFileDescriptor.dump((String)localObject, paramPrintWriter);
          i += 1;
        }
      }
    }
    try
    {
      if (mBackStackIndices != null)
      {
        k = mBackStackIndices.size();
        if (k > 0)
        {
          paramPrintWriter.print(paramString);
          paramPrintWriter.println("Back Stack Indices:");
          i = 0;
          while (i < k)
          {
            paramFileDescriptor = (BackStackRecord)mBackStackIndices.get(i);
            paramPrintWriter.print(paramString);
            paramPrintWriter.print("  #");
            paramPrintWriter.print(i);
            paramPrintWriter.print(": ");
            paramPrintWriter.println(paramFileDescriptor);
            i += 1;
          }
        }
      }
      if ((mAvailBackStackIndices != null) && (mAvailBackStackIndices.size() > 0))
      {
        paramPrintWriter.print(paramString);
        paramPrintWriter.print("mAvailBackStackIndices: ");
        paramPrintWriter.println(Arrays.toString(mAvailBackStackIndices.toArray()));
      }
      paramFileDescriptor = mPendingActions;
      if (paramFileDescriptor != null)
      {
        k = paramFileDescriptor.size();
        if (k > 0)
        {
          paramPrintWriter.print(paramString);
          paramPrintWriter.println("Pending Actions:");
          i = j;
          while (i < k)
          {
            paramFileDescriptor = (OpGenerator)mPendingActions.get(i);
            paramPrintWriter.print(paramString);
            paramPrintWriter.print("  #");
            paramPrintWriter.print(i);
            paramPrintWriter.print(": ");
            paramPrintWriter.println(paramFileDescriptor);
            i += 1;
          }
        }
      }
      paramPrintWriter.print(paramString);
      paramPrintWriter.println("FragmentManager misc state:");
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("  mHost=");
      paramPrintWriter.println(mHost);
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("  mContainer=");
      paramPrintWriter.println(mContainer);
      if (mParent != null)
      {
        paramPrintWriter.print(paramString);
        paramPrintWriter.print("  mParent=");
        paramPrintWriter.println(mParent);
      }
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("  mCurState=");
      paramPrintWriter.print(mCurState);
      paramPrintWriter.print(" mStateSaved=");
      paramPrintWriter.print(mStateSaved);
      paramPrintWriter.print(" mStopped=");
      paramPrintWriter.print(mStopped);
      paramPrintWriter.print(" mDestroyed=");
      paramPrintWriter.println(mDestroyed);
      if (mNeedMenuInvalidate)
      {
        paramPrintWriter.print(paramString);
        paramPrintWriter.print("  mNeedMenuInvalidate=");
        paramPrintWriter.println(mNeedMenuInvalidate);
        return;
      }
    }
    catch (Throwable paramString)
    {
      throw paramString;
    }
  }
  
  public void enqueueAction(OpGenerator paramOpGenerator, boolean paramBoolean)
  {
    if (!paramBoolean) {
      checkStateLoss();
    }
    try
    {
      if ((!mDestroyed) && (mHost != null))
      {
        if (mPendingActions == null) {
          mPendingActions = new ArrayList();
        }
        mPendingActions.add(paramOpGenerator);
        scheduleCommit();
        return;
      }
      if (paramBoolean) {
        return;
      }
      throw new IllegalStateException("Activity has been destroyed");
    }
    catch (Throwable paramOpGenerator)
    {
      throw paramOpGenerator;
    }
  }
  
  void ensureInflatedFragmentView(Fragment paramFragment)
  {
    if ((mFromLayout) && (!mPerformedCreateView))
    {
      paramFragment.performCreateView(paramFragment.performGetLayoutInflater(mSavedFragmentState), null, mSavedFragmentState);
      if (mView != null)
      {
        mInnerView = mView;
        mView.setSaveFromParentEnabled(false);
        if (mHidden) {
          mView.setVisibility(8);
        }
        paramFragment.onViewCreated(mView, mSavedFragmentState);
        dispatchOnFragmentViewCreated(paramFragment, mView, mSavedFragmentState, false);
        return;
      }
      mInnerView = null;
    }
  }
  
  public boolean execPendingActions()
  {
    ensureExecReady(true);
    boolean bool = false;
    while (generateOpsForPendingActions(mTmpRecords, mTmpIsPop))
    {
      mExecutingActions = true;
      try
      {
        removeRedundantOperationsAndExecute(mTmpRecords, mTmpIsPop);
        cleanupExec();
        bool = true;
      }
      catch (Throwable localThrowable)
      {
        cleanupExec();
        throw localThrowable;
      }
    }
    updateOnBackPressedCallbackEnabled();
    doPendingDeferredStart();
    burpActive();
    return bool;
  }
  
  public void execSingleAction(OpGenerator paramOpGenerator, boolean paramBoolean)
  {
    if (paramBoolean)
    {
      if (mHost == null) {
        return;
      }
      if (mDestroyed) {
        return;
      }
    }
    ensureExecReady(paramBoolean);
    if (paramOpGenerator.generateOps(mTmpRecords, mTmpIsPop))
    {
      mExecutingActions = true;
      try
      {
        removeRedundantOperationsAndExecute(mTmpRecords, mTmpIsPop);
        cleanupExec();
      }
      catch (Throwable paramOpGenerator)
      {
        cleanupExec();
        throw paramOpGenerator;
      }
    }
    updateOnBackPressedCallbackEnabled();
    doPendingDeferredStart();
    burpActive();
  }
  
  public boolean executePendingTransactions()
  {
    boolean bool = execPendingActions();
    forcePostponedTransactions();
    return bool;
  }
  
  public Fragment findFragmentById(int paramInt)
  {
    int i = mAdded.size() - 1;
    while (i >= 0)
    {
      localObject = (Fragment)mAdded.get(i);
      if ((localObject != null) && (mFragmentId == paramInt)) {
        return localObject;
      }
      i -= 1;
    }
    Object localObject = mActive.values().iterator();
    while (((Iterator)localObject).hasNext())
    {
      Fragment localFragment = (Fragment)((Iterator)localObject).next();
      if ((localFragment != null) && (mFragmentId == paramInt)) {
        return localFragment;
      }
    }
    return null;
  }
  
  public Fragment findFragmentByTag(String paramString)
  {
    Object localObject;
    if (paramString != null)
    {
      int i = mAdded.size() - 1;
      while (i >= 0)
      {
        localObject = (Fragment)mAdded.get(i);
        if ((localObject != null) && (paramString.equals(mTag))) {
          return localObject;
        }
        i -= 1;
      }
    }
    if (paramString != null)
    {
      localObject = mActive.values().iterator();
      while (((Iterator)localObject).hasNext())
      {
        Fragment localFragment = (Fragment)((Iterator)localObject).next();
        if ((localFragment != null) && (paramString.equals(mTag))) {
          return localFragment;
        }
      }
    }
    return null;
  }
  
  public Fragment findFragmentByWho(String paramString)
  {
    Iterator localIterator = mActive.values().iterator();
    while (localIterator.hasNext())
    {
      Fragment localFragment = (Fragment)localIterator.next();
      if (localFragment != null)
      {
        localFragment = localFragment.findFragmentByWho(paramString);
        if (localFragment != null) {
          return localFragment;
        }
      }
    }
    return null;
  }
  
  public void freeBackStackIndex(int paramInt)
  {
    try
    {
      mBackStackIndices.set(paramInt, null);
      if (mAvailBackStackIndices == null) {
        mAvailBackStackIndices = new ArrayList();
      }
      if (DEBUG)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("Freeing back stack index ");
        localStringBuilder.append(paramInt);
        Log.v("FragmentManager", localStringBuilder.toString());
      }
      mAvailBackStackIndices.add(Integer.valueOf(paramInt));
      return;
    }
    catch (Throwable localThrowable)
    {
      throw localThrowable;
    }
  }
  
  int getActiveFragmentCount()
  {
    return mActive.size();
  }
  
  List getActiveFragments()
  {
    return new ArrayList(mActive.values());
  }
  
  public FragmentManager.BackStackEntry getBackStackEntryAt(int paramInt)
  {
    return (FragmentManager.BackStackEntry)mBackStack.get(paramInt);
  }
  
  public int getBackStackEntryCount()
  {
    ArrayList localArrayList = mBackStack;
    if (localArrayList != null) {
      return localArrayList.size();
    }
    return 0;
  }
  
  FragmentManagerViewModel getChildNonConfig(Fragment paramFragment)
  {
    return mNonConfig.getChildNonConfig(paramFragment);
  }
  
  public Fragment getFragment(Bundle paramBundle, String paramString)
  {
    paramBundle = paramBundle.getString(paramString);
    if (paramBundle == null) {
      return null;
    }
    Fragment localFragment = (Fragment)mActive.get(paramBundle);
    if (localFragment == null)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Fragment no longer exists for key ");
      localStringBuilder.append(paramString);
      localStringBuilder.append(": unique id ");
      localStringBuilder.append(paramBundle);
      throwException(new IllegalStateException(localStringBuilder.toString()));
    }
    return localFragment;
  }
  
  public FragmentFactory getFragmentFactory()
  {
    if (super.getFragmentFactory() == FragmentManager.DEFAULT_FACTORY)
    {
      Fragment localFragment = mParent;
      if (localFragment != null) {
        return mFragmentManager.getFragmentFactory();
      }
      setFragmentFactory(new FragmentManagerImpl.6(this));
    }
    return super.getFragmentFactory();
  }
  
  public List getFragments()
  {
    if (mAdded.isEmpty()) {
      return Collections.emptyList();
    }
    ArrayList localArrayList = mAdded;
    try
    {
      List localList = (List)mAdded.clone();
      return localList;
    }
    catch (Throwable localThrowable)
    {
      throw localThrowable;
    }
  }
  
  LayoutInflater.Factory2 getLayoutInflaterFactory()
  {
    return this;
  }
  
  public Fragment getPrimaryNavigationFragment()
  {
    return mPrimaryNav;
  }
  
  ViewModelStore getViewModelStore(Fragment paramFragment)
  {
    return mNonConfig.getViewModelStore(paramFragment);
  }
  
  void handleOnBackPressed()
  {
    execPendingActions();
    if (mOnBackPressedCallback.isEnabled())
    {
      popBackStackImmediate();
      return;
    }
    mOnBackPressedDispatcher.onBackPressed();
  }
  
  public void hideFragment(Fragment paramFragment)
  {
    if (DEBUG)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("hide: ");
      localStringBuilder.append(paramFragment);
      Log.v("FragmentManager", localStringBuilder.toString());
    }
    if (!mHidden)
    {
      mHidden = true;
      mHiddenChanged = (true ^ mHiddenChanged);
    }
  }
  
  public boolean isDestroyed()
  {
    return mDestroyed;
  }
  
  boolean isPrimaryNavigation(Fragment paramFragment)
  {
    if (paramFragment == null) {
      return true;
    }
    FragmentManagerImpl localFragmentManagerImpl = mFragmentManager;
    return (paramFragment == localFragmentManagerImpl.getPrimaryNavigationFragment()) && (isPrimaryNavigation(mParent));
  }
  
  boolean isStateAtLeast(int paramInt)
  {
    return mCurState >= paramInt;
  }
  
  public boolean isStateSaved()
  {
    return (mStateSaved) || (mStopped);
  }
  
  /* Error */
  AnimationOrAnimator loadAnimation(Fragment paramFragment, int paramInt1, boolean paramBoolean, int paramInt2)
  {
    // Byte code:
    //   0: aload_1
    //   1: invokevirtual 188	androidx/fragment/package_8/Fragment:getNextAnim	()I
    //   4: istore 7
    //   6: iconst_0
    //   7: istore 6
    //   9: aload_1
    //   10: iconst_0
    //   11: invokevirtual 1275	androidx/fragment/package_8/Fragment:setNextAnim	(I)V
    //   14: aload_1
    //   15: getfield 216	androidx/fragment/package_8/Fragment:mContainer	Landroid/view/ViewGroup;
    //   18: ifnull +15 -> 33
    //   21: aload_1
    //   22: getfield 216	androidx/fragment/package_8/Fragment:mContainer	Landroid/view/ViewGroup;
    //   25: invokevirtual 1279	android/view/ViewGroup:getLayoutTransition	()Landroid/animation/LayoutTransition;
    //   28: ifnull +5 -> 33
    //   31: aconst_null
    //   32: areturn
    //   33: aload_1
    //   34: iload_2
    //   35: iload_3
    //   36: iload 7
    //   38: invokevirtual 1283	androidx/fragment/package_8/Fragment:onCreateAnimation	(IZI)Landroid/view/animation/Animation;
    //   41: astore 9
    //   43: aload 9
    //   45: ifnull +13 -> 58
    //   48: new 12	androidx/fragment/package_8/FragmentManagerImpl$AnimationOrAnimator
    //   51: dup
    //   52: aload 9
    //   54: invokespecial 561	androidx/fragment/package_8/FragmentManagerImpl$AnimationOrAnimator:<init>	(Landroid/view/animation/Animation;)V
    //   57: areturn
    //   58: aload_1
    //   59: iload_2
    //   60: iload_3
    //   61: iload 7
    //   63: invokevirtual 1287	androidx/fragment/package_8/Fragment:onCreateAnimator	(IZI)Landroid/animation/Animator;
    //   66: astore_1
    //   67: aload_1
    //   68: ifnull +12 -> 80
    //   71: new 12	androidx/fragment/package_8/FragmentManagerImpl$AnimationOrAnimator
    //   74: dup
    //   75: aload_1
    //   76: invokespecial 1289	androidx/fragment/package_8/FragmentManagerImpl$AnimationOrAnimator:<init>	(Landroid/animation/Animator;)V
    //   79: areturn
    //   80: iload 7
    //   82: ifeq +148 -> 230
    //   85: ldc_w 1291
    //   88: aload_0
    //   89: getfield 380	androidx/fragment/package_8/FragmentManagerImpl:mHost	Landroidx/fragment/package_8/FragmentHostCallback;
    //   92: invokevirtual 1295	androidx/fragment/package_8/FragmentHostCallback:getContext	()Landroid/content/Context;
    //   95: invokevirtual 1301	android/content/Context:getResources	()Landroid/content/res/Resources;
    //   98: iload 7
    //   100: invokevirtual 1306	android/content/res/Resources:getResourceTypeName	(I)Ljava/lang/String;
    //   103: invokevirtual 1191	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   106: istore 8
    //   108: iload 6
    //   110: istore 5
    //   112: iload 8
    //   114: ifeq +49 -> 163
    //   117: aload_0
    //   118: getfield 380	androidx/fragment/package_8/FragmentManagerImpl:mHost	Landroidx/fragment/package_8/FragmentHostCallback;
    //   121: astore_1
    //   122: aload_1
    //   123: invokevirtual 1295	androidx/fragment/package_8/FragmentHostCallback:getContext	()Landroid/content/Context;
    //   126: iload 7
    //   128: invokestatic 1311	android/view/animation/AnimationUtils:loadAnimation	(Landroid/content/Context;I)Landroid/view/animation/Animation;
    //   131: astore_1
    //   132: aload_1
    //   133: ifnull +14 -> 147
    //   136: new 12	androidx/fragment/package_8/FragmentManagerImpl$AnimationOrAnimator
    //   139: dup
    //   140: aload_1
    //   141: invokespecial 561	androidx/fragment/package_8/FragmentManagerImpl$AnimationOrAnimator:<init>	(Landroid/view/animation/Animation;)V
    //   144: astore_1
    //   145: aload_1
    //   146: areturn
    //   147: iconst_1
    //   148: istore 5
    //   150: goto +13 -> 163
    //   153: iload 6
    //   155: istore 5
    //   157: goto +6 -> 163
    //   160: astore_1
    //   161: aload_1
    //   162: athrow
    //   163: iload 5
    //   165: ifne +65 -> 230
    //   168: aload_0
    //   169: getfield 380	androidx/fragment/package_8/FragmentManagerImpl:mHost	Landroidx/fragment/package_8/FragmentHostCallback;
    //   172: invokevirtual 1295	androidx/fragment/package_8/FragmentHostCallback:getContext	()Landroid/content/Context;
    //   175: iload 7
    //   177: invokestatic 1317	android/animation/AnimatorInflater:loadAnimator	(Landroid/content/Context;I)Landroid/animation/Animator;
    //   180: astore_1
    //   181: aload_1
    //   182: ifnull +48 -> 230
    //   185: new 12	androidx/fragment/package_8/FragmentManagerImpl$AnimationOrAnimator
    //   188: dup
    //   189: aload_1
    //   190: invokespecial 1289	androidx/fragment/package_8/FragmentManagerImpl$AnimationOrAnimator:<init>	(Landroid/animation/Animator;)V
    //   193: astore_1
    //   194: aload_1
    //   195: areturn
    //   196: astore_1
    //   197: iload 8
    //   199: ifne +29 -> 228
    //   202: aload_0
    //   203: getfield 380	androidx/fragment/package_8/FragmentManagerImpl:mHost	Landroidx/fragment/package_8/FragmentHostCallback;
    //   206: invokevirtual 1295	androidx/fragment/package_8/FragmentHostCallback:getContext	()Landroid/content/Context;
    //   209: iload 7
    //   211: invokestatic 1311	android/view/animation/AnimationUtils:loadAnimation	(Landroid/content/Context;I)Landroid/view/animation/Animation;
    //   214: astore_1
    //   215: aload_1
    //   216: ifnull +14 -> 230
    //   219: new 12	androidx/fragment/package_8/FragmentManagerImpl$AnimationOrAnimator
    //   222: dup
    //   223: aload_1
    //   224: invokespecial 561	androidx/fragment/package_8/FragmentManagerImpl$AnimationOrAnimator:<init>	(Landroid/view/animation/Animation;)V
    //   227: areturn
    //   228: aload_1
    //   229: athrow
    //   230: iload_2
    //   231: ifne +5 -> 236
    //   234: aconst_null
    //   235: areturn
    //   236: iload_2
    //   237: iload_3
    //   238: invokestatic 1319	androidx/fragment/package_8/FragmentManagerImpl:transitToStyleIndex	(IZ)I
    //   241: istore_2
    //   242: iload_2
    //   243: ifge +5 -> 248
    //   246: aconst_null
    //   247: areturn
    //   248: iload_2
    //   249: lookupswitch	default:+59->308, 1:+136->385, 2:+126->375, 3:+116->365, 4:+106->355, 5:+100->349, 6:+94->343
    //   308: goto +3 -> 311
    //   311: iload 4
    //   313: istore_2
    //   314: iload 4
    //   316: ifne +79 -> 395
    //   319: iload 4
    //   321: istore_2
    //   322: aload_0
    //   323: getfield 380	androidx/fragment/package_8/FragmentManagerImpl:mHost	Landroidx/fragment/package_8/FragmentHostCallback;
    //   326: invokevirtual 1322	androidx/fragment/package_8/FragmentHostCallback:onHasWindowAnimations	()Z
    //   329: ifeq +66 -> 395
    //   332: aload_0
    //   333: getfield 380	androidx/fragment/package_8/FragmentManagerImpl:mHost	Landroidx/fragment/package_8/FragmentHostCallback;
    //   336: invokevirtual 1325	androidx/fragment/package_8/FragmentHostCallback:onGetWindowAnimations	()I
    //   339: istore_2
    //   340: goto +55 -> 395
    //   343: fconst_1
    //   344: fconst_0
    //   345: invokestatic 1327	androidx/fragment/package_8/FragmentManagerImpl:makeFadeAnimation	(FF)Landroidx/fragment/package_8/FragmentManagerImpl$AnimationOrAnimator;
    //   348: areturn
    //   349: fconst_0
    //   350: fconst_1
    //   351: invokestatic 1327	androidx/fragment/package_8/FragmentManagerImpl:makeFadeAnimation	(FF)Landroidx/fragment/package_8/FragmentManagerImpl$AnimationOrAnimator;
    //   354: areturn
    //   355: fconst_1
    //   356: ldc_w 1328
    //   359: fconst_1
    //   360: fconst_0
    //   361: invokestatic 1330	androidx/fragment/package_8/FragmentManagerImpl:makeOpenCloseAnimation	(FFFF)Landroidx/fragment/package_8/FragmentManagerImpl$AnimationOrAnimator;
    //   364: areturn
    //   365: ldc_w 1331
    //   368: fconst_1
    //   369: fconst_0
    //   370: fconst_1
    //   371: invokestatic 1330	androidx/fragment/package_8/FragmentManagerImpl:makeOpenCloseAnimation	(FFFF)Landroidx/fragment/package_8/FragmentManagerImpl$AnimationOrAnimator;
    //   374: areturn
    //   375: fconst_1
    //   376: ldc_w 1331
    //   379: fconst_1
    //   380: fconst_0
    //   381: invokestatic 1330	androidx/fragment/package_8/FragmentManagerImpl:makeOpenCloseAnimation	(FFFF)Landroidx/fragment/package_8/FragmentManagerImpl$AnimationOrAnimator;
    //   384: areturn
    //   385: ldc_w 1332
    //   388: fconst_1
    //   389: fconst_0
    //   390: fconst_1
    //   391: invokestatic 1330	androidx/fragment/package_8/FragmentManagerImpl:makeOpenCloseAnimation	(FFFF)Landroidx/fragment/package_8/FragmentManagerImpl$AnimationOrAnimator;
    //   394: areturn
    //   395: iload_2
    //   396: ifne +9 -> 405
    //   399: aconst_null
    //   400: areturn
    //   401: astore_1
    //   402: goto -249 -> 153
    //   405: aconst_null
    //   406: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	407	0	this	FragmentManagerImpl
    //   0	407	1	paramFragment	Fragment
    //   0	407	2	paramInt1	int
    //   0	407	3	paramBoolean	boolean
    //   0	407	4	paramInt2	int
    //   110	54	5	i	int
    //   7	147	6	j	int
    //   4	206	7	k	int
    //   106	92	8	bool	boolean
    //   41	12	9	localAnimation	Animation
    // Exception table:
    //   from	to	target	type
    //   122	132	160	android/content/res/Resources$NotFoundException
    //   136	145	160	android/content/res/Resources$NotFoundException
    //   168	181	196	java/lang/RuntimeException
    //   185	194	196	java/lang/RuntimeException
    //   122	132	401	java/lang/RuntimeException
    //   136	145	401	java/lang/RuntimeException
  }
  
  void makeActive(Fragment paramFragment)
  {
    if (mActive.get(mWho) != null) {
      return;
    }
    mActive.put(mWho, paramFragment);
    if (mRetainInstanceChangedWhileDetached)
    {
      if (mRetainInstance) {
        addRetainedFragment(paramFragment);
      } else {
        removeRetainedFragment(paramFragment);
      }
      mRetainInstanceChangedWhileDetached = false;
    }
    if (DEBUG)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Added fragment to active set ");
      localStringBuilder.append(paramFragment);
      Log.v("FragmentManager", localStringBuilder.toString());
    }
  }
  
  void makeInactive(Fragment paramFragment)
  {
    if (mActive.get(mWho) == null) {
      return;
    }
    if (DEBUG)
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("Removed fragment from active set ");
      ((StringBuilder)localObject).append(paramFragment);
      Log.v("FragmentManager", ((StringBuilder)localObject).toString());
    }
    Object localObject = mActive.values().iterator();
    while (((Iterator)localObject).hasNext())
    {
      Fragment localFragment = (Fragment)((Iterator)localObject).next();
      if ((localFragment != null) && (mWho.equals(mTargetWho)))
      {
        mTarget = paramFragment;
        mTargetWho = null;
      }
    }
    mActive.put(mWho, null);
    removeRetainedFragment(paramFragment);
    if (mTargetWho != null) {
      mTarget = ((Fragment)mActive.get(mTargetWho));
    }
    paramFragment.initState();
  }
  
  void moveFragmentToExpectedState(Fragment paramFragment)
  {
    if (paramFragment == null) {
      return;
    }
    Object localObject;
    if (!mActive.containsKey(mWho))
    {
      if (DEBUG)
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("Ignoring moving ");
        ((StringBuilder)localObject).append(paramFragment);
        ((StringBuilder)localObject).append(" to state ");
        ((StringBuilder)localObject).append(mCurState);
        ((StringBuilder)localObject).append("since it is not added to ");
        ((StringBuilder)localObject).append(this);
        Log.v("FragmentManager", ((StringBuilder)localObject).toString());
      }
    }
    else
    {
      int j = mCurState;
      int i = j;
      if (mRemoving) {
        if (paramFragment.isInBackStack()) {
          i = Math.min(j, 1);
        } else {
          i = Math.min(j, 0);
        }
      }
      moveToState(paramFragment, i, paramFragment.getNextTransition(), paramFragment.getNextTransitionStyle(), false);
      if (mView != null)
      {
        localObject = findFragmentUnder(paramFragment);
        if (localObject != null)
        {
          localObject = mView;
          ViewGroup localViewGroup = mContainer;
          i = localViewGroup.indexOfChild((View)localObject);
          j = localViewGroup.indexOfChild(mView);
          if (j < i)
          {
            localViewGroup.removeViewAt(j);
            localViewGroup.addView(mView, i);
          }
        }
        if ((mIsNewlyAdded) && (mContainer != null))
        {
          if (mPostponedAlpha > 0.0F) {
            mView.setAlpha(mPostponedAlpha);
          }
          mPostponedAlpha = 0.0F;
          mIsNewlyAdded = false;
          localObject = loadAnimation(paramFragment, paramFragment.getNextTransition(), true, paramFragment.getNextTransitionStyle());
          if (localObject != null) {
            if (animation != null)
            {
              mView.startAnimation(animation);
            }
            else
            {
              animator.setTarget(mView);
              animator.start();
            }
          }
        }
      }
      if (mHiddenChanged) {
        completeShowHideFragment(paramFragment);
      }
    }
  }
  
  void moveToState(int paramInt, boolean paramBoolean)
  {
    if ((mHost == null) && (paramInt != 0)) {
      throw new IllegalStateException("No activity");
    }
    if ((!paramBoolean) && (paramInt == mCurState)) {
      return;
    }
    mCurState = paramInt;
    int i = mAdded.size();
    paramInt = 0;
    while (paramInt < i)
    {
      moveFragmentToExpectedState((Fragment)mAdded.get(paramInt));
      paramInt += 1;
    }
    Object localObject = mActive.values().iterator();
    while (((Iterator)localObject).hasNext())
    {
      Fragment localFragment = (Fragment)((Iterator)localObject).next();
      if ((localFragment != null) && ((mRemoving) || (mDetached)) && (!mIsNewlyAdded)) {
        moveFragmentToExpectedState(localFragment);
      }
    }
    startPendingDeferredFragments();
    if (mNeedMenuInvalidate)
    {
      localObject = mHost;
      if ((localObject != null) && (mCurState == 4))
      {
        ((FragmentHostCallback)localObject).onSupportInvalidateOptionsMenu();
        mNeedMenuInvalidate = false;
      }
    }
  }
  
  void moveToState(Fragment paramFragment)
  {
    moveToState(paramFragment, mCurState, 0, 0, false);
  }
  
  void moveToState(Fragment paramFragment, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
  {
    boolean bool3 = mAdded;
    boolean bool2 = true;
    int k = 1;
    boolean bool1 = true;
    if ((bool3) && (!mDetached))
    {
      i = paramInt1;
    }
    else
    {
      i = paramInt1;
      if (paramInt1 > 1) {
        i = 1;
      }
    }
    paramInt1 = i;
    if (mRemoving)
    {
      paramInt1 = i;
      if (i > mState) {
        if ((mState == 0) && (paramFragment.isInBackStack())) {
          paramInt1 = 1;
        } else {
          paramInt1 = mState;
        }
      }
    }
    int i = paramInt1;
    if (mDeferStart)
    {
      i = paramInt1;
      if (mState < 3)
      {
        i = paramInt1;
        if (paramInt1 > 2) {
          i = 2;
        }
      }
    }
    int j;
    if (mMaxState == Lifecycle.State.CREATED) {
      j = Math.min(i, 1);
    } else {
      j = Math.min(i, mMaxState.ordinal());
    }
    paramInt1 = j;
    Object localObject1;
    if (mState <= j)
    {
      if ((mFromLayout) && (!mInLayout)) {
        return;
      }
      if ((paramFragment.getAnimatingAway() != null) || (paramFragment.getAnimator() != null))
      {
        paramFragment.setAnimatingAway(null);
        paramFragment.setAnimator(null);
        moveToState(paramFragment, paramFragment.getStateAfterAnimating(), 0, 0, true);
      }
      i = mState;
      if (i != 0)
      {
        paramInt2 = paramInt1;
        if (i == 1) {
          break label892;
        }
        paramInt3 = paramInt1;
        if (i != 2)
        {
          paramInt2 = paramInt1;
          if (i != 3)
          {
            i = paramInt1;
            break label2304;
          }
        }
      }
      for (;;)
      {
        break;
        for (;;)
        {
          break;
          paramInt2 = paramInt1;
          Object localObject2;
          if (paramInt1 > 0)
          {
            if (DEBUG)
            {
              localObject1 = new StringBuilder();
              ((StringBuilder)localObject1).append("moveto CREATED: ");
              ((StringBuilder)localObject1).append(paramFragment);
              Log.v("FragmentManager", ((StringBuilder)localObject1).toString());
            }
            paramInt2 = paramInt1;
            if (mSavedFragmentState != null)
            {
              mSavedFragmentState.setClassLoader(mHost.getContext().getClassLoader());
              mSavedViewState = mSavedFragmentState.getSparseParcelableArray("android:view_state");
              localObject1 = getFragment(mSavedFragmentState, "android:target_state");
              if (localObject1 != null) {
                localObject1 = mWho;
              } else {
                localObject1 = null;
              }
              mTargetWho = ((String)localObject1);
              if (mTargetWho != null) {
                mTargetRequestCode = mSavedFragmentState.getInt("android:target_req_state", 0);
              }
              if (mSavedUserVisibleHint != null)
              {
                mUserVisibleHint = mSavedUserVisibleHint.booleanValue();
                mSavedUserVisibleHint = null;
              }
              else
              {
                mUserVisibleHint = mSavedFragmentState.getBoolean("android:user_visible_hint", true);
              }
              paramInt2 = paramInt1;
              if (!mUserVisibleHint)
              {
                mDeferStart = true;
                paramInt2 = paramInt1;
                if (paramInt1 > 2) {
                  paramInt2 = 2;
                }
              }
            }
            localObject1 = mHost;
            mHost = ((FragmentHostCallback)localObject1);
            localObject2 = mParent;
            mParentFragment = ((Fragment)localObject2);
            if (localObject2 != null) {
              localObject1 = mChildFragmentManager;
            } else {
              localObject1 = mFragmentManager;
            }
            mFragmentManager = ((FragmentManagerImpl)localObject1);
            if (mTarget != null) {
              if (mActive.get(mTarget.mWho) == mTarget)
              {
                if (mTarget.mState < 1) {
                  moveToState(mTarget, 1, 0, 0, true);
                }
                mTargetWho = mTarget.mWho;
                mTarget = null;
              }
              else
              {
                localObject1 = new StringBuilder();
                ((StringBuilder)localObject1).append("Fragment ");
                ((StringBuilder)localObject1).append(paramFragment);
                ((StringBuilder)localObject1).append(" declared target fragment ");
                ((StringBuilder)localObject1).append(mTarget);
                ((StringBuilder)localObject1).append(" that does not belong to this FragmentManager!");
                throw new IllegalStateException(((StringBuilder)localObject1).toString());
              }
            }
            if (mTargetWho != null)
            {
              localObject1 = (Fragment)mActive.get(mTargetWho);
              if (localObject1 != null)
              {
                if (mState < 1) {
                  moveToState((Fragment)localObject1, 1, 0, 0, true);
                }
              }
              else
              {
                localObject1 = new StringBuilder();
                ((StringBuilder)localObject1).append("Fragment ");
                ((StringBuilder)localObject1).append(paramFragment);
                ((StringBuilder)localObject1).append(" declared target fragment ");
                ((StringBuilder)localObject1).append(mTargetWho);
                ((StringBuilder)localObject1).append(" that does not belong to this FragmentManager!");
                throw new IllegalStateException(((StringBuilder)localObject1).toString());
              }
            }
            dispatchOnFragmentPreAttached(paramFragment, mHost.getContext(), false);
            paramFragment.performAttach();
            if (mParentFragment == null) {
              mHost.onAttachFragment(paramFragment);
            } else {
              mParentFragment.onAttachFragment(paramFragment);
            }
            dispatchOnFragmentAttached(paramFragment, mHost.getContext(), false);
            if (!mIsCreated)
            {
              dispatchOnFragmentPreCreated(paramFragment, mSavedFragmentState, false);
              paramFragment.performCreate(mSavedFragmentState);
              dispatchOnFragmentCreated(paramFragment, mSavedFragmentState, false);
            }
            else
            {
              paramFragment.restoreChildFragmentState(mSavedFragmentState);
              mState = 1;
            }
          }
          label892:
          if (paramInt2 > 0) {
            ensureInflatedFragmentView(paramFragment);
          }
          paramInt3 = paramInt2;
          if (paramInt2 > 1)
          {
            if (DEBUG)
            {
              localObject1 = new StringBuilder();
              ((StringBuilder)localObject1).append("moveto ACTIVITY_CREATED: ");
              ((StringBuilder)localObject1).append(paramFragment);
              Log.v("FragmentManager", ((StringBuilder)localObject1).toString());
            }
            if (!mFromLayout) {
              if (mContainerId != 0)
              {
                if (mContainerId == -1)
                {
                  localObject1 = new StringBuilder();
                  ((StringBuilder)localObject1).append("Cannot create fragment ");
                  ((StringBuilder)localObject1).append(paramFragment);
                  ((StringBuilder)localObject1).append(" for a container view with no id");
                  throwException(new IllegalArgumentException(((StringBuilder)localObject1).toString()));
                }
                localObject2 = (ViewGroup)mContainer.onFindViewById(mContainerId);
                localObject1 = localObject2;
                if (localObject2 != null) {
                  break label1176;
                }
                localObject1 = localObject2;
                if (mRestored) {
                  break label1176;
                }
              }
            }
          }
          try
          {
            localObject1 = paramFragment.getResources();
            paramInt1 = mContainerId;
            localObject1 = ((Resources)localObject1).getResourceName(paramInt1);
          }
          catch (Resources.NotFoundException localNotFoundException)
          {
            StringBuilder localStringBuilder;
            for (;;) {}
          }
          localObject1 = "unknown";
          localStringBuilder = new StringBuilder();
          localStringBuilder.append("No view found for id 0x");
          localStringBuilder.append(Integer.toHexString(mContainerId));
          localStringBuilder.append(" (");
          localStringBuilder.append((String)localObject1);
          localStringBuilder.append(") for fragment ");
          localStringBuilder.append(paramFragment);
          throwException(new IllegalArgumentException(localStringBuilder.toString()));
          localObject1 = localObject2;
          break label1176;
          localObject1 = null;
          label1176:
          mContainer = ((ViewGroup)localObject1);
          paramFragment.performCreateView(paramFragment.performGetLayoutInflater(mSavedFragmentState), (ViewGroup)localObject1, mSavedFragmentState);
          if (mView != null)
          {
            mInnerView = mView;
            mView.setSaveFromParentEnabled(false);
            if (localObject1 != null) {
              ((ViewGroup)localObject1).addView(mView);
            }
            if (mHidden) {
              mView.setVisibility(8);
            }
            paramFragment.onViewCreated(mView, mSavedFragmentState);
            dispatchOnFragmentViewCreated(paramFragment, mView, mSavedFragmentState, false);
            if ((mView.getVisibility() == 0) && (mContainer != null)) {
              paramBoolean = bool1;
            } else {
              paramBoolean = false;
            }
            mIsNewlyAdded = paramBoolean;
          }
          else
          {
            mInnerView = null;
          }
          paramFragment.performActivityCreated(mSavedFragmentState);
          dispatchOnFragmentActivityCreated(paramFragment, mSavedFragmentState, false);
          if (mView != null) {
            paramFragment.restoreViewState(mSavedFragmentState);
          }
          mSavedFragmentState = null;
          paramInt3 = paramInt2;
        }
        paramInt2 = paramInt3;
        if (paramInt3 > 2)
        {
          if (DEBUG)
          {
            localObject1 = new StringBuilder();
            ((StringBuilder)localObject1).append("moveto STARTED: ");
            ((StringBuilder)localObject1).append(paramFragment);
            Log.v("FragmentManager", ((StringBuilder)localObject1).toString());
          }
          paramFragment.performStart();
          dispatchOnFragmentStarted(paramFragment, false);
          paramInt2 = paramInt3;
        }
      }
      i = paramInt2;
      if (paramInt2 > 3)
      {
        if (DEBUG)
        {
          localObject1 = new StringBuilder();
          ((StringBuilder)localObject1).append("moveto RESUMED: ");
          ((StringBuilder)localObject1).append(paramFragment);
          Log.v("FragmentManager", ((StringBuilder)localObject1).toString());
        }
        paramFragment.performResume();
        dispatchOnFragmentResumed(paramFragment, false);
        mSavedFragmentState = null;
        mSavedViewState = null;
        i = paramInt2;
      }
    }
    else
    {
      i = paramInt1;
      if (mState > j)
      {
        i = mState;
        if (i != 1)
        {
          if (i != 2)
          {
            if (i != 3)
            {
              if (i != 4)
              {
                i = paramInt1;
                break label2304;
              }
              if (j < 4)
              {
                if (DEBUG)
                {
                  localObject1 = new StringBuilder();
                  ((StringBuilder)localObject1).append("movefrom RESUMED: ");
                  ((StringBuilder)localObject1).append(paramFragment);
                  Log.v("FragmentManager", ((StringBuilder)localObject1).toString());
                }
                paramFragment.performPause();
                dispatchOnFragmentPaused(paramFragment, false);
              }
            }
            if (paramInt1 < 3)
            {
              if (DEBUG)
              {
                localObject1 = new StringBuilder();
                ((StringBuilder)localObject1).append("movefrom STARTED: ");
                ((StringBuilder)localObject1).append(paramFragment);
                Log.v("FragmentManager", ((StringBuilder)localObject1).toString());
              }
              paramFragment.performStop();
              dispatchOnFragmentStopped(paramFragment, false);
            }
          }
          if (paramInt1 < 2)
          {
            if (DEBUG)
            {
              localObject1 = new StringBuilder();
              ((StringBuilder)localObject1).append("movefrom ACTIVITY_CREATED: ");
              ((StringBuilder)localObject1).append(paramFragment);
              Log.v("FragmentManager", ((StringBuilder)localObject1).toString());
            }
            if ((mView != null) && (mHost.onShouldSaveFragmentState(paramFragment)) && (mSavedViewState == null)) {
              saveFragmentViewState(paramFragment);
            }
            paramFragment.performDestroyView();
            dispatchOnFragmentViewDestroyed(paramFragment, false);
            if ((mView != null) && (mContainer != null))
            {
              mContainer.endViewTransition(mView);
              mView.clearAnimation();
              if ((paramFragment.getParentFragment() == null) || (!getParentFragmentmRemoving))
              {
                if ((mCurState > 0) && (!mDestroyed) && (mView.getVisibility() == 0) && (mPostponedAlpha >= 0.0F)) {
                  localObject1 = loadAnimation(paramFragment, paramInt2, false, paramInt3);
                } else {
                  localObject1 = null;
                }
                mPostponedAlpha = 0.0F;
                if (localObject1 != null) {
                  animateRemoveFragment(paramFragment, (AnimationOrAnimator)localObject1, paramInt1);
                }
                mContainer.removeView(mView);
              }
            }
            mContainer = null;
            mView = null;
            mViewLifecycleOwner = null;
            mViewLifecycleOwnerLiveData.setValue(null);
            mInnerView = null;
            mInLayout = false;
          }
        }
        i = paramInt1;
        if (paramInt1 < 1)
        {
          if (mDestroyed) {
            if (paramFragment.getAnimatingAway() != null)
            {
              localObject1 = paramFragment.getAnimatingAway();
              paramFragment.setAnimatingAway(null);
              ((View)localObject1).clearAnimation();
            }
            else if (paramFragment.getAnimator() != null)
            {
              localObject1 = paramFragment.getAnimator();
              paramFragment.setAnimator(null);
              ((Animator)localObject1).cancel();
            }
          }
          if ((paramFragment.getAnimatingAway() == null) && (paramFragment.getAnimator() == null))
          {
            if (DEBUG)
            {
              localObject1 = new StringBuilder();
              ((StringBuilder)localObject1).append("movefrom CREATED: ");
              ((StringBuilder)localObject1).append(paramFragment);
              Log.v("FragmentManager", ((StringBuilder)localObject1).toString());
            }
            if ((mRemoving) && (!paramFragment.isInBackStack())) {
              paramInt2 = 1;
            } else {
              paramInt2 = 0;
            }
            if ((paramInt2 == 0) && (!mNonConfig.shouldDestroy(paramFragment)))
            {
              mState = 0;
            }
            else
            {
              localObject1 = mHost;
              if ((localObject1 instanceof ViewModelStoreOwner))
              {
                bool1 = mNonConfig.isCleared();
              }
              else
              {
                bool1 = bool2;
                if ((((FragmentHostCallback)localObject1).getContext() instanceof Activity)) {
                  bool1 = true ^ ((Activity)mHost.getContext()).isChangingConfigurations();
                }
              }
              if ((paramInt2 != 0) || (bool1)) {
                mNonConfig.clearNonConfigState(paramFragment);
              }
              paramFragment.performDestroy();
              dispatchOnFragmentDestroyed(paramFragment, false);
            }
            paramFragment.performDetach();
            dispatchOnFragmentDetached(paramFragment, false);
            i = paramInt1;
            if (!paramBoolean) {
              if ((paramInt2 == 0) && (!mNonConfig.shouldDestroy(paramFragment)))
              {
                mHost = null;
                mParentFragment = null;
                mFragmentManager = null;
                i = paramInt1;
                if (mTargetWho != null)
                {
                  localObject1 = (Fragment)mActive.get(mTargetWho);
                  i = paramInt1;
                  if (localObject1 != null)
                  {
                    i = paramInt1;
                    if (((Fragment)localObject1).getRetainInstance())
                    {
                      mTarget = ((Fragment)localObject1);
                      i = paramInt1;
                    }
                  }
                }
              }
              else
              {
                makeInactive(paramFragment);
                i = paramInt1;
              }
            }
          }
          else
          {
            paramFragment.setStateAfterAnimating(paramInt1);
            i = k;
          }
        }
      }
    }
    label2304:
    if (mState != i)
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("moveToState: Fragment state for ");
      ((StringBuilder)localObject1).append(paramFragment);
      ((StringBuilder)localObject1).append(" not updated inline; expected state ");
      ((StringBuilder)localObject1).append(i);
      ((StringBuilder)localObject1).append(" found ");
      ((StringBuilder)localObject1).append(mState);
      Log.w("FragmentManager", ((StringBuilder)localObject1).toString());
      mState = i;
      return;
    }
  }
  
  public void noteStateNotSaved()
  {
    int i = 0;
    mStateSaved = false;
    mStopped = false;
    int j = mAdded.size();
    while (i < j)
    {
      Fragment localFragment = (Fragment)mAdded.get(i);
      if (localFragment != null) {
        localFragment.noteStateNotSaved();
      }
      i += 1;
    }
  }
  
  public View onCreateView(View paramView, String paramString, Context paramContext, AttributeSet paramAttributeSet)
  {
    boolean bool = "fragment".equals(paramString);
    paramString = null;
    if (!bool) {
      return null;
    }
    String str2 = paramAttributeSet.getAttributeValue(null, "class");
    String str1 = str2;
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, FragmentTag.Fragment);
    int i = 0;
    if (str2 == null) {
      str1 = localTypedArray.getString(0);
    }
    int k = localTypedArray.getResourceId(1, -1);
    str2 = localTypedArray.getString(2);
    localTypedArray.recycle();
    if (str1 != null)
    {
      if (!FragmentFactory.isFragmentClass(paramContext.getClassLoader(), str1)) {
        return null;
      }
      if (paramView != null) {
        i = paramView.getId();
      }
      if ((i == -1) && (k == -1) && (str2 == null))
      {
        paramView = new StringBuilder();
        paramView.append(paramAttributeSet.getPositionDescription());
        paramView.append(": Must specify unique android:id, android:tag, or have a parent with an id for ");
        paramView.append(str1);
        throw new IllegalArgumentException(paramView.toString());
      }
      paramView = paramString;
      if (k != -1) {
        paramView = findFragmentById(k);
      }
      paramString = paramView;
      if (paramView == null)
      {
        paramString = paramView;
        if (str2 != null) {
          paramString = findFragmentByTag(str2);
        }
      }
      paramView = paramString;
      if (paramString == null)
      {
        paramView = paramString;
        if (i != -1) {
          paramView = findFragmentById(i);
        }
      }
      if (DEBUG)
      {
        paramString = new StringBuilder();
        paramString.append("onCreateView: id=0x");
        paramString.append(Integer.toHexString(k));
        paramString.append(" fname=");
        paramString.append(str1);
        paramString.append(" existing=");
        paramString.append(paramView);
        Log.v("FragmentManager", paramString.toString());
      }
      if (paramView == null)
      {
        paramView = getFragmentFactory().instantiate(paramContext.getClassLoader(), str1);
        mFromLayout = true;
        int j;
        if (k != 0) {
          j = k;
        } else {
          j = i;
        }
        mFragmentId = j;
        mContainerId = i;
        mTag = str2;
        mInLayout = true;
        mFragmentManager = this;
        paramString = mHost;
        mHost = paramString;
        paramView.onInflate(paramString.getContext(), paramAttributeSet, mSavedFragmentState);
        addFragment(paramView, true);
      }
      else
      {
        if (mInLayout) {
          break label563;
        }
        mInLayout = true;
        paramString = mHost;
        mHost = paramString;
        paramView.onInflate(paramString.getContext(), paramAttributeSet, mSavedFragmentState);
      }
      if ((mCurState < 1) && (mFromLayout)) {
        moveToState(paramView, 1, 0, 0, false);
      } else {
        moveToState(paramView);
      }
      if (mView != null)
      {
        if (k != 0) {
          mView.setId(k);
        }
        if (mView.getTag() == null) {
          mView.setTag(str2);
        }
        return mView;
      }
      paramView = new StringBuilder();
      paramView.append("Fragment ");
      paramView.append(str1);
      paramView.append(" did not create a view.");
      throw new IllegalStateException(paramView.toString());
      label563:
      paramView = new StringBuilder();
      paramView.append(paramAttributeSet.getPositionDescription());
      paramView.append(": Duplicate id 0x");
      paramView.append(Integer.toHexString(k));
      paramView.append(", tag ");
      paramView.append(str2);
      paramView.append(", or parent id 0x");
      paramView.append(Integer.toHexString(i));
      paramView.append(" with another fragment for ");
      paramView.append(str1);
      throw new IllegalArgumentException(paramView.toString());
    }
    return null;
  }
  
  public View onCreateView(String paramString, Context paramContext, AttributeSet paramAttributeSet)
  {
    return onCreateView(null, paramString, paramContext, paramAttributeSet);
  }
  
  public void performPendingDeferredStart(Fragment paramFragment)
  {
    if (mDeferStart)
    {
      if (mExecutingActions)
      {
        mHavePendingDeferredStart = true;
        return;
      }
      mDeferStart = false;
      moveToState(paramFragment, mCurState, 0, 0, false);
    }
  }
  
  public void popBackStack()
  {
    enqueueAction(new PopBackStackState(null, -1, 0), false);
  }
  
  public void popBackStack(int paramInt1, int paramInt2)
  {
    if (paramInt1 >= 0)
    {
      enqueueAction(new PopBackStackState(null, paramInt1, paramInt2), false);
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Bad id: ");
    localStringBuilder.append(paramInt1);
    throw new IllegalArgumentException(localStringBuilder.toString());
  }
  
  public void popBackStack(String paramString, int paramInt)
  {
    enqueueAction(new PopBackStackState(paramString, -1, paramInt), false);
  }
  
  public boolean popBackStackImmediate()
  {
    checkStateLoss();
    return popBackStackImmediate(null, -1, 0);
  }
  
  public boolean popBackStackImmediate(int paramInt1, int paramInt2)
  {
    checkStateLoss();
    execPendingActions();
    if (paramInt1 >= 0) {
      return popBackStackImmediate(null, paramInt1, paramInt2);
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Bad id: ");
    localStringBuilder.append(paramInt1);
    throw new IllegalArgumentException(localStringBuilder.toString());
  }
  
  public boolean popBackStackImmediate(String paramString, int paramInt)
  {
    checkStateLoss();
    return popBackStackImmediate(paramString, -1, paramInt);
  }
  
  boolean popBackStackState(ArrayList paramArrayList1, ArrayList paramArrayList2, String paramString, int paramInt1, int paramInt2)
  {
    Object localObject = mBackStack;
    if (localObject == null) {
      return false;
    }
    if ((paramString == null) && (paramInt1 < 0) && ((paramInt2 & 0x1) == 0))
    {
      paramInt1 = ((ArrayList)localObject).size() - 1;
      if (paramInt1 < 0) {
        return false;
      }
      paramArrayList1.add(mBackStack.remove(paramInt1));
      paramArrayList2.add(Boolean.valueOf(true));
      return true;
    }
    int j;
    if ((paramString == null) && (paramInt1 < 0))
    {
      j = -1;
    }
    else
    {
      int i = mBackStack.size() - 1;
      while (i >= 0)
      {
        localObject = (BackStackRecord)mBackStack.get(i);
        if (((paramString != null) && (paramString.equals(((BackStackRecord)localObject).getName()))) || ((paramInt1 >= 0) && (paramInt1 == mIndex))) {
          break;
        }
        i -= 1;
      }
      if (i < 0) {
        return false;
      }
      j = i;
      if ((paramInt2 & 0x1) != 0) {
        for (;;)
        {
          paramInt2 = i - 1;
          j = paramInt2;
          if (paramInt2 < 0) {
            break;
          }
          localObject = (BackStackRecord)mBackStack.get(paramInt2);
          if (paramString != null)
          {
            i = paramInt2;
            if (paramString.equals(((BackStackRecord)localObject).getName())) {}
          }
          else
          {
            j = paramInt2;
            if (paramInt1 < 0) {
              break;
            }
            j = paramInt2;
            if (paramInt1 != mIndex) {
              break;
            }
            i = paramInt2;
          }
        }
      }
    }
    if (j == mBackStack.size() - 1) {
      return false;
    }
    paramInt1 = mBackStack.size() - 1;
    while (paramInt1 > j)
    {
      paramArrayList1.add(mBackStack.remove(paramInt1));
      paramArrayList2.add(Boolean.valueOf(true));
      paramInt1 -= 1;
    }
    return true;
  }
  
  public void putFragment(Bundle paramBundle, String paramString, Fragment paramFragment)
  {
    if (mFragmentManager != this)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Fragment ");
      localStringBuilder.append(paramFragment);
      localStringBuilder.append(" is not currently in the FragmentManager");
      throwException(new IllegalStateException(localStringBuilder.toString()));
    }
    paramBundle.putString(paramString, mWho);
  }
  
  public void registerFragmentLifecycleCallbacks(FragmentManager.FragmentLifecycleCallbacks paramFragmentLifecycleCallbacks, boolean paramBoolean)
  {
    mLifecycleCallbacks.add(new FragmentLifecycleCallbacksHolder(paramFragmentLifecycleCallbacks, paramBoolean));
  }
  
  public void removeFragment(Fragment paramFragment)
  {
    Object localObject;
    if (DEBUG)
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("remove: ");
      ((StringBuilder)localObject).append(paramFragment);
      ((StringBuilder)localObject).append(" nesting=");
      ((StringBuilder)localObject).append(mBackStackNesting);
      Log.v("FragmentManager", ((StringBuilder)localObject).toString());
    }
    boolean bool = paramFragment.isInBackStack();
    if ((!mDetached) || ((bool ^ true)))
    {
      localObject = mAdded;
      try
      {
        mAdded.remove(paramFragment);
        if (isMenuAvailable(paramFragment)) {
          mNeedMenuInvalidate = true;
        }
        mAdded = false;
        mRemoving = true;
        return;
      }
      catch (Throwable paramFragment)
      {
        throw paramFragment;
      }
    }
  }
  
  public void removeOnBackStackChangedListener(FragmentManager.OnBackStackChangedListener paramOnBackStackChangedListener)
  {
    ArrayList localArrayList = mBackStackChangeListeners;
    if (localArrayList != null) {
      localArrayList.remove(paramOnBackStackChangedListener);
    }
  }
  
  void removeRetainedFragment(Fragment paramFragment)
  {
    if (isStateSaved())
    {
      if (DEBUG) {
        Log.v("FragmentManager", "Ignoring removeRetainedFragment as the state is already saved");
      }
    }
    else if ((mNonConfig.removeRetainedFragment(paramFragment)) && (DEBUG))
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Updating retained Fragments: Removed ");
      localStringBuilder.append(paramFragment);
      Log.v("FragmentManager", localStringBuilder.toString());
    }
  }
  
  void reportBackStackChanged()
  {
    if (mBackStackChangeListeners != null)
    {
      int i = 0;
      while (i < mBackStackChangeListeners.size())
      {
        ((FragmentManager.OnBackStackChangedListener)mBackStackChangeListeners.get(i)).onBackStackChanged();
        i += 1;
      }
    }
  }
  
  void restoreAllState(Parcelable paramParcelable, FragmentManagerNonConfig paramFragmentManagerNonConfig)
  {
    if ((mHost instanceof ViewModelStoreOwner)) {
      throwException(new IllegalStateException("You must use restoreSaveState when your FragmentHostCallback implements ViewModelStoreOwner"));
    }
    mNonConfig.restoreFromSnapshot(paramFragmentManagerNonConfig);
    restoreSaveState(paramParcelable);
  }
  
  void restoreSaveState(Parcelable paramParcelable)
  {
    if (paramParcelable == null) {
      return;
    }
    FragmentManagerState localFragmentManagerState = (FragmentManagerState)paramParcelable;
    if (mActive == null) {
      return;
    }
    Object localObject2 = mNonConfig.getRetainedFragments().iterator();
    Object localObject3;
    Object localObject1;
    while (((Iterator)localObject2).hasNext())
    {
      localObject3 = (Fragment)((Iterator)localObject2).next();
      if (DEBUG)
      {
        paramParcelable = new StringBuilder();
        paramParcelable.append("restoreSaveState: re-attaching retained ");
        paramParcelable.append(localObject3);
        Log.v("FragmentManager", paramParcelable.toString());
      }
      localObject1 = mActive.iterator();
      while (((Iterator)localObject1).hasNext())
      {
        paramParcelable = (FragmentState)((Iterator)localObject1).next();
        if (mWho.equals(mWho)) {
          break label143;
        }
      }
      paramParcelable = null;
      label143:
      if (paramParcelable == null)
      {
        if (DEBUG)
        {
          paramParcelable = new StringBuilder();
          paramParcelable.append("Discarding retained Fragment ");
          paramParcelable.append(localObject3);
          paramParcelable.append(" that was not found in the set of active Fragments ");
          paramParcelable.append(mActive);
          Log.v("FragmentManager", paramParcelable.toString());
        }
        moveToState((Fragment)localObject3, 1, 0, 0, false);
        mRemoving = true;
        moveToState((Fragment)localObject3, 0, 0, 0, false);
      }
      else
      {
        mInstance = ((Fragment)localObject3);
        mSavedViewState = null;
        mBackStackNesting = 0;
        mInLayout = false;
        mAdded = false;
        if (mTarget != null) {
          localObject1 = mTarget.mWho;
        } else {
          localObject1 = null;
        }
        mTargetWho = ((String)localObject1);
        mTarget = null;
        if (mSavedFragmentState != null)
        {
          mSavedFragmentState.setClassLoader(mHost.getContext().getClassLoader());
          mSavedViewState = mSavedFragmentState.getSparseParcelableArray("android:view_state");
          mSavedFragmentState = mSavedFragmentState;
        }
      }
    }
    mActive.clear();
    paramParcelable = mActive.iterator();
    while (paramParcelable.hasNext())
    {
      localObject1 = (FragmentState)paramParcelable.next();
      if (localObject1 != null)
      {
        localObject2 = ((FragmentState)localObject1).instantiate(mHost.getContext().getClassLoader(), getFragmentFactory());
        mFragmentManager = this;
        if (DEBUG)
        {
          localObject3 = new StringBuilder();
          ((StringBuilder)localObject3).append("restoreSaveState: active (");
          ((StringBuilder)localObject3).append(mWho);
          ((StringBuilder)localObject3).append("): ");
          ((StringBuilder)localObject3).append(localObject2);
          Log.v("FragmentManager", ((StringBuilder)localObject3).toString());
        }
        mActive.put(mWho, localObject2);
        mInstance = null;
      }
    }
    mAdded.clear();
    if (mAdded != null)
    {
      localObject1 = mAdded.iterator();
      for (;;)
      {
        if (!((Iterator)localObject1).hasNext()) {
          break label749;
        }
        localObject2 = (String)((Iterator)localObject1).next();
        paramParcelable = (Fragment)mActive.get(localObject2);
        if (paramParcelable == null)
        {
          localObject3 = new StringBuilder();
          ((StringBuilder)localObject3).append("No instantiated fragment for (");
          ((StringBuilder)localObject3).append((String)localObject2);
          ((StringBuilder)localObject3).append(")");
          throwException(new IllegalStateException(((StringBuilder)localObject3).toString()));
        }
        mAdded = true;
        if (DEBUG)
        {
          localObject3 = new StringBuilder();
          ((StringBuilder)localObject3).append("restoreSaveState: added (");
          ((StringBuilder)localObject3).append((String)localObject2);
          ((StringBuilder)localObject3).append("): ");
          ((StringBuilder)localObject3).append(paramParcelable);
          Log.v("FragmentManager", ((StringBuilder)localObject3).toString());
        }
        if (!mAdded.contains(paramParcelable))
        {
          localObject2 = mAdded;
          try
          {
            mAdded.add(paramParcelable);
          }
          catch (Throwable paramParcelable)
          {
            throw paramParcelable;
          }
        }
      }
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("Already added ");
      ((StringBuilder)localObject1).append(paramParcelable);
      throw new IllegalStateException(((StringBuilder)localObject1).toString());
    }
    label749:
    if (mBackStack != null)
    {
      mBackStack = new ArrayList(mBackStack.length);
      int i = 0;
      while (i < mBackStack.length)
      {
        paramParcelable = mBackStack[i].instantiate(this);
        if (DEBUG)
        {
          localObject1 = new StringBuilder();
          ((StringBuilder)localObject1).append("restoreAllState: back stack #");
          ((StringBuilder)localObject1).append(i);
          ((StringBuilder)localObject1).append(" (index ");
          ((StringBuilder)localObject1).append(mIndex);
          ((StringBuilder)localObject1).append("): ");
          ((StringBuilder)localObject1).append(paramParcelable);
          Log.v("FragmentManager", ((StringBuilder)localObject1).toString());
          localObject1 = new PrintWriter(new LogWriter("FragmentManager"));
          paramParcelable.dump("  ", (PrintWriter)localObject1, false);
          ((PrintWriter)localObject1).close();
        }
        mBackStack.add(paramParcelable);
        if (mIndex >= 0) {
          setBackStackIndex(mIndex, paramParcelable);
        }
        i += 1;
      }
    }
    mBackStack = null;
    if (mPrimaryNavActiveWho != null)
    {
      mPrimaryNav = ((Fragment)mActive.get(mPrimaryNavActiveWho));
      dispatchParentPrimaryNavigationFragmentChanged(mPrimaryNav);
    }
    mNextFragmentIndex = mNextFragmentIndex;
  }
  
  FragmentManagerNonConfig retainNonConfig()
  {
    if ((mHost instanceof ViewModelStoreOwner)) {
      throwException(new IllegalStateException("You cannot use retainNonConfig when your FragmentHostCallback implements ViewModelStoreOwner."));
    }
    return mNonConfig.getSnapshot();
  }
  
  Parcelable saveAllState()
  {
    forcePostponedTransactions();
    endAnimatingAwayFragments();
    execPendingActions();
    mStateSaved = true;
    boolean bool = mActive.isEmpty();
    Object localObject3 = null;
    if (bool) {
      return null;
    }
    ArrayList localArrayList = new ArrayList(mActive.size());
    Object localObject1 = mActive.values().iterator();
    int j = 0;
    int i = 0;
    Object localObject2;
    Object localObject4;
    Object localObject5;
    while (((Iterator)localObject1).hasNext())
    {
      localObject2 = (Fragment)((Iterator)localObject1).next();
      if (localObject2 != null)
      {
        if (mFragmentManager != this)
        {
          localObject4 = new StringBuilder();
          ((StringBuilder)localObject4).append("Failure saving state: active ");
          ((StringBuilder)localObject4).append(localObject2);
          ((StringBuilder)localObject4).append(" was removed from the FragmentManager");
          throwException(new IllegalStateException(((StringBuilder)localObject4).toString()));
        }
        localObject4 = new FragmentState((Fragment)localObject2);
        localArrayList.add(localObject4);
        if ((mState > 0) && (mSavedFragmentState == null))
        {
          mSavedFragmentState = saveFragmentBasicState((Fragment)localObject2);
          if (mTargetWho != null)
          {
            localObject5 = (Fragment)mActive.get(mTargetWho);
            if (localObject5 == null)
            {
              StringBuilder localStringBuilder = new StringBuilder();
              localStringBuilder.append("Failure saving state: ");
              localStringBuilder.append(localObject2);
              localStringBuilder.append(" has target not in fragment manager: ");
              localStringBuilder.append(mTargetWho);
              throwException(new IllegalStateException(localStringBuilder.toString()));
            }
            if (mSavedFragmentState == null) {
              mSavedFragmentState = new Bundle();
            }
            putFragment(mSavedFragmentState, "android:target_state", (Fragment)localObject5);
            if (mTargetRequestCode != 0) {
              mSavedFragmentState.putInt("android:target_req_state", mTargetRequestCode);
            }
          }
        }
        else
        {
          mSavedFragmentState = mSavedFragmentState;
        }
        if (DEBUG)
        {
          localObject5 = new StringBuilder();
          ((StringBuilder)localObject5).append("Saved state of ");
          ((StringBuilder)localObject5).append(localObject2);
          ((StringBuilder)localObject5).append(": ");
          ((StringBuilder)localObject5).append(mSavedFragmentState);
          Log.v("FragmentManager", ((StringBuilder)localObject5).toString());
        }
        i = 1;
      }
    }
    if (i == 0)
    {
      if (DEBUG)
      {
        Log.v("FragmentManager", "saveAllState: no fragments!");
        return null;
      }
    }
    else
    {
      i = mAdded.size();
      if (i > 0)
      {
        localObject2 = new ArrayList(i);
        localObject4 = mAdded.iterator();
        for (;;)
        {
          localObject1 = localObject2;
          if (!((Iterator)localObject4).hasNext()) {
            break;
          }
          localObject1 = (Fragment)((Iterator)localObject4).next();
          ((ArrayList)localObject2).add(mWho);
          if (mFragmentManager != this)
          {
            localObject5 = new StringBuilder();
            ((StringBuilder)localObject5).append("Failure saving state: active ");
            ((StringBuilder)localObject5).append(localObject1);
            ((StringBuilder)localObject5).append(" was removed from the FragmentManager");
            throwException(new IllegalStateException(((StringBuilder)localObject5).toString()));
          }
          if (DEBUG)
          {
            localObject5 = new StringBuilder();
            ((StringBuilder)localObject5).append("saveAllState: adding fragment (");
            ((StringBuilder)localObject5).append(mWho);
            ((StringBuilder)localObject5).append("): ");
            ((StringBuilder)localObject5).append(localObject1);
            Log.v("FragmentManager", ((StringBuilder)localObject5).toString());
          }
        }
      }
      localObject1 = null;
      localObject4 = mBackStack;
      localObject2 = localObject3;
      if (localObject4 != null)
      {
        int k = ((ArrayList)localObject4).size();
        localObject2 = localObject3;
        if (k > 0)
        {
          localObject3 = new BackStackState[k];
          i = j;
          for (;;)
          {
            localObject2 = localObject3;
            if (i >= k) {
              break;
            }
            localObject3[i] = new BackStackState((BackStackRecord)mBackStack.get(i));
            if (DEBUG)
            {
              localObject2 = new StringBuilder();
              ((StringBuilder)localObject2).append("saveAllState: adding back stack #");
              ((StringBuilder)localObject2).append(i);
              ((StringBuilder)localObject2).append(": ");
              ((StringBuilder)localObject2).append(mBackStack.get(i));
              Log.v("FragmentManager", ((StringBuilder)localObject2).toString());
            }
            i += 1;
          }
        }
      }
      localObject3 = new FragmentManagerState();
      mActive = localArrayList;
      mAdded = ((ArrayList)localObject1);
      mBackStack = ((BackStackState[])localObject2);
      localObject1 = mPrimaryNav;
      if (localObject1 != null) {
        mPrimaryNavActiveWho = mWho;
      }
      mNextFragmentIndex = mNextFragmentIndex;
      return localObject3;
    }
    return null;
  }
  
  Bundle saveFragmentBasicState(Fragment paramFragment)
  {
    if (mStateBundle == null) {
      mStateBundle = new Bundle();
    }
    paramFragment.performSaveInstanceState(mStateBundle);
    dispatchOnFragmentSaveInstanceState(paramFragment, mStateBundle, false);
    if (!mStateBundle.isEmpty())
    {
      localObject2 = mStateBundle;
      mStateBundle = null;
    }
    else
    {
      localObject2 = null;
    }
    if (mView != null) {
      saveFragmentViewState(paramFragment);
    }
    Object localObject1 = localObject2;
    if (mSavedViewState != null)
    {
      localObject1 = localObject2;
      if (localObject2 == null) {
        localObject1 = new Bundle();
      }
      ((Bundle)localObject1).putSparseParcelableArray("android:view_state", mSavedViewState);
    }
    Object localObject2 = localObject1;
    if (!mUserVisibleHint)
    {
      localObject2 = localObject1;
      if (localObject1 == null) {
        localObject2 = new Bundle();
      }
      ((Bundle)localObject2).putBoolean("android:user_visible_hint", mUserVisibleHint);
    }
    return localObject2;
  }
  
  public Fragment.SavedState saveFragmentInstanceState(Fragment paramFragment)
  {
    if (mFragmentManager != this)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Fragment ");
      localStringBuilder.append(paramFragment);
      localStringBuilder.append(" is not currently in the FragmentManager");
      throwException(new IllegalStateException(localStringBuilder.toString()));
    }
    if (mState > 0)
    {
      paramFragment = saveFragmentBasicState(paramFragment);
      if (paramFragment != null) {
        return new Fragment.SavedState(paramFragment);
      }
    }
    return null;
  }
  
  void saveFragmentViewState(Fragment paramFragment)
  {
    if (mInnerView == null) {
      return;
    }
    SparseArray localSparseArray = mStateArray;
    if (localSparseArray == null) {
      mStateArray = new SparseArray();
    } else {
      localSparseArray.clear();
    }
    mInnerView.saveHierarchyState(mStateArray);
    if (mStateArray.size() > 0)
    {
      mSavedViewState = mStateArray;
      mStateArray = null;
    }
  }
  
  void scheduleCommit()
  {
    for (;;)
    {
      int j;
      try
      {
        ArrayList localArrayList = mPostponedTransactions;
        int k = 0;
        if ((localArrayList == null) || (mPostponedTransactions.isEmpty())) {
          break label100;
        }
        i = 1;
        j = k;
        if (mPendingActions == null) {
          break label105;
        }
        j = k;
        if (mPendingActions.size() != 1) {
          break label105;
        }
        j = 1;
      }
      catch (Throwable localThrowable)
      {
        throw localThrowable;
      }
      mHost.getHandler().removeCallbacks(mExecCommit);
      mHost.getHandler().post(mExecCommit);
      updateOnBackPressedCallbackEnabled();
      return;
      label100:
      int i = 0;
      continue;
      label105:
      if (i == 0) {
        if (j == 0) {}
      }
    }
  }
  
  public void setBackStackIndex(int paramInt, BackStackRecord paramBackStackRecord)
  {
    try
    {
      if (mBackStackIndices == null) {
        mBackStackIndices = new ArrayList();
      }
      int j = mBackStackIndices.size();
      int i = j;
      StringBuilder localStringBuilder;
      if (paramInt < j)
      {
        if (DEBUG)
        {
          localStringBuilder = new StringBuilder();
          localStringBuilder.append("Setting back stack index ");
          localStringBuilder.append(paramInt);
          localStringBuilder.append(" to ");
          localStringBuilder.append(paramBackStackRecord);
          Log.v("FragmentManager", localStringBuilder.toString());
        }
        mBackStackIndices.set(paramInt, paramBackStackRecord);
      }
      else
      {
        while (i < paramInt)
        {
          mBackStackIndices.add(null);
          if (mAvailBackStackIndices == null) {
            mAvailBackStackIndices = new ArrayList();
          }
          if (DEBUG)
          {
            localStringBuilder = new StringBuilder();
            localStringBuilder.append("Adding available back stack index ");
            localStringBuilder.append(i);
            Log.v("FragmentManager", localStringBuilder.toString());
          }
          mAvailBackStackIndices.add(Integer.valueOf(i));
          i += 1;
        }
        if (DEBUG)
        {
          localStringBuilder = new StringBuilder();
          localStringBuilder.append("Adding back stack index ");
          localStringBuilder.append(paramInt);
          localStringBuilder.append(" with ");
          localStringBuilder.append(paramBackStackRecord);
          Log.v("FragmentManager", localStringBuilder.toString());
        }
        mBackStackIndices.add(paramBackStackRecord);
      }
      return;
    }
    catch (Throwable paramBackStackRecord)
    {
      throw paramBackStackRecord;
    }
  }
  
  public void setMaxLifecycle(Fragment paramFragment, Lifecycle.State paramState)
  {
    if ((mActive.get(mWho) == paramFragment) && ((mHost == null) || (paramFragment.getFragmentManager() == this)))
    {
      mMaxState = paramState;
      return;
    }
    paramState = new StringBuilder();
    paramState.append("Fragment ");
    paramState.append(paramFragment);
    paramState.append(" is not an active fragment of FragmentManager ");
    paramState.append(this);
    throw new IllegalArgumentException(paramState.toString());
  }
  
  public void setPrimaryNavigationFragment(Fragment paramFragment)
  {
    if ((paramFragment != null) && ((mActive.get(mWho) != paramFragment) || ((mHost != null) && (paramFragment.getFragmentManager() != this))))
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("Fragment ");
      ((StringBuilder)localObject).append(paramFragment);
      ((StringBuilder)localObject).append(" is not an active fragment of FragmentManager ");
      ((StringBuilder)localObject).append(this);
      throw new IllegalArgumentException(((StringBuilder)localObject).toString());
    }
    Object localObject = mPrimaryNav;
    mPrimaryNav = paramFragment;
    dispatchParentPrimaryNavigationFragmentChanged((Fragment)localObject);
    dispatchParentPrimaryNavigationFragmentChanged(mPrimaryNav);
  }
  
  public void showFragment(Fragment paramFragment)
  {
    if (DEBUG)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("show: ");
      localStringBuilder.append(paramFragment);
      Log.v("FragmentManager", localStringBuilder.toString());
    }
    if (mHidden)
    {
      mHidden = false;
      mHiddenChanged ^= true;
    }
  }
  
  void startPendingDeferredFragments()
  {
    Iterator localIterator = mActive.values().iterator();
    while (localIterator.hasNext())
    {
      Fragment localFragment = (Fragment)localIterator.next();
      if (localFragment != null) {
        performPendingDeferredStart(localFragment);
      }
    }
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder(128);
    localStringBuilder.append("FragmentManager{");
    localStringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
    localStringBuilder.append(" in ");
    Fragment localFragment = mParent;
    if (localFragment != null) {
      DebugUtils.buildShortClassTag(localFragment, localStringBuilder);
    } else {
      DebugUtils.buildShortClassTag(mHost, localStringBuilder);
    }
    localStringBuilder.append("}}");
    return localStringBuilder.toString();
  }
  
  public void unregisterFragmentLifecycleCallbacks(FragmentManager.FragmentLifecycleCallbacks paramFragmentLifecycleCallbacks)
  {
    CopyOnWriteArrayList localCopyOnWriteArrayList = mLifecycleCallbacks;
    int i = 0;
    for (;;)
    {
      try
      {
        int j = mLifecycleCallbacks.size();
        if (i < j)
        {
          if (mLifecycleCallbacks.get(i)).mCallback != paramFragmentLifecycleCallbacks) {
            break label67;
          }
          mLifecycleCallbacks.remove(i);
        }
        return;
      }
      catch (Throwable paramFragmentLifecycleCallbacks)
      {
        throw paramFragmentLifecycleCallbacks;
      }
      label67:
      i += 1;
    }
  }
  
  class AnimationOrAnimator
  {
    public final Animation animation;
    public final Animator animator;
    
    AnimationOrAnimator()
    {
      animation = null;
      animator = this$1;
      if (this$1 != null) {
        return;
      }
      throw new IllegalStateException("Animator cannot be null");
    }
    
    AnimationOrAnimator()
    {
      animation = this$1;
      animator = null;
      if (this$1 != null) {
        return;
      }
      throw new IllegalStateException("Animation cannot be null");
    }
  }
  
  class EndViewTransitionAnimation
    extends AnimationSet
    implements Runnable
  {
    private boolean mAnimating = true;
    private final View mChild;
    private boolean mEnded;
    private final ViewGroup mParent;
    private boolean mTransitionEnded;
    
    EndViewTransitionAnimation(ViewGroup paramViewGroup, View paramView)
    {
      super();
      mParent = paramViewGroup;
      mChild = paramView;
      addAnimation(this$1);
      mParent.post(this);
    }
    
    public boolean getTransformation(long paramLong, Transformation paramTransformation)
    {
      mAnimating = true;
      if (mEnded) {
        return mTransitionEnded ^ true;
      }
      if (!super.getTransformation(paramLong, paramTransformation))
      {
        mEnded = true;
        OneShotPreDrawListener.a(mParent, this);
      }
      return true;
    }
    
    public boolean getTransformation(long paramLong, Transformation paramTransformation, float paramFloat)
    {
      mAnimating = true;
      if (mEnded) {
        return mTransitionEnded ^ true;
      }
      if (!super.getTransformation(paramLong, paramTransformation, paramFloat))
      {
        mEnded = true;
        OneShotPreDrawListener.a(mParent, this);
      }
      return true;
    }
    
    public void run()
    {
      if ((!mEnded) && (mAnimating))
      {
        mAnimating = false;
        mParent.post(this);
        return;
      }
      mParent.endViewTransition(mChild);
      mTransitionEnded = true;
    }
  }
  
  final class FragmentLifecycleCallbacksHolder
  {
    final boolean mRecursive;
    
    FragmentLifecycleCallbacksHolder(boolean paramBoolean)
    {
      mRecursive = paramBoolean;
    }
  }
  
  class FragmentTag
  {
    public static final int[] Fragment = { 16842755, 16842960, 16842961 };
    public static final int Fragment_id = 1;
    public static final int Fragment_name = 0;
    public static final int Fragment_tag = 2;
    
    private FragmentTag() {}
  }
  
  abstract interface OpGenerator
  {
    public abstract boolean generateOps(ArrayList paramArrayList1, ArrayList paramArrayList2);
  }
  
  class PopBackStackState
    implements FragmentManagerImpl.OpGenerator
  {
    final int mFlags;
    final int mFolder;
    final String mName;
    
    PopBackStackState(String paramString, int paramInt1, int paramInt2)
    {
      mName = paramString;
      mFolder = paramInt1;
      mFlags = paramInt2;
    }
    
    public boolean generateOps(ArrayList paramArrayList1, ArrayList paramArrayList2)
    {
      if ((mPrimaryNav != null) && (mFolder < 0) && (mName == null) && (mPrimaryNav.getChildFragmentManager().popBackStackImmediate())) {
        return false;
      }
      return popBackStackState(paramArrayList1, paramArrayList2, mName, mFolder, mFlags);
    }
  }
  
  class StartEnterTransitionListener
    implements Fragment.OnStartEnterTransitionListener
  {
    final boolean mIsBack;
    private int mNumPostponed;
    
    StartEnterTransitionListener(boolean paramBoolean)
    {
      mIsBack = paramBoolean;
    }
    
    public void cancelTransaction()
    {
      mManager.completeExecute(FragmentManagerImpl.this, mIsBack, false, false);
    }
    
    public void completeTransaction()
    {
      int i = mNumPostponed;
      int j = 0;
      if (i > 0) {
        i = 1;
      } else {
        i = 0;
      }
      FragmentManagerImpl localFragmentManagerImpl = mManager;
      int k = mAdded.size();
      while (j < k)
      {
        Fragment localFragment = (Fragment)mAdded.get(j);
        localFragment.setOnStartEnterTransitionListener(null);
        if ((i != 0) && (localFragment.isPostponed())) {
          localFragment.startPostponedEnterTransition();
        }
        j += 1;
      }
      mManager.completeExecute(FragmentManagerImpl.this, mIsBack, i ^ 0x1, true);
    }
    
    public boolean isReady()
    {
      return mNumPostponed == 0;
    }
    
    public void onStartEnterTransition()
    {
      mNumPostponed -= 1;
      if (mNumPostponed != 0) {
        return;
      }
      mManager.scheduleCommit();
    }
    
    public void startListening()
    {
      mNumPostponed += 1;
    }
  }
}
