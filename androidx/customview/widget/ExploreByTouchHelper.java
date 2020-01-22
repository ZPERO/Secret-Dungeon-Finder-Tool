package androidx.customview.widget;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityRecord;
import androidx.collection.SparseArrayCompat;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewParentCompat;
import androidx.core.view.accessibility.AccessibilityEventCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityNodeProviderCompat;
import androidx.core.view.accessibility.AccessibilityRecordCompat;
import java.util.ArrayList;
import java.util.List;

public abstract class ExploreByTouchHelper
  extends AccessibilityDelegateCompat
{
  private static final String DEFAULT_CLASS_NAME = "android.view.View";
  public static final int HOST_ID = -1;
  public static final int INVALID_ID = Integer.MIN_VALUE;
  private static final Rect INVALID_PARENT_BOUNDS = new Rect(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
  private static final FocusStrategy.BoundsAdapter<AccessibilityNodeInfoCompat> NODE_ADAPTER = new FocusStrategy.BoundsAdapter()
  {
    public void obtainBounds(AccessibilityNodeInfoCompat paramAnonymousAccessibilityNodeInfoCompat, Rect paramAnonymousRect)
    {
      paramAnonymousAccessibilityNodeInfoCompat.getBoundsInParent(paramAnonymousRect);
    }
  };
  private static final FocusStrategy.CollectionAdapter<SparseArrayCompat<AccessibilityNodeInfoCompat>, AccessibilityNodeInfoCompat> SPARSE_VALUES_ADAPTER = new FocusStrategy.CollectionAdapter()
  {
    public AccessibilityNodeInfoCompat get(SparseArrayCompat paramAnonymousSparseArrayCompat, int paramAnonymousInt)
    {
      return (AccessibilityNodeInfoCompat)paramAnonymousSparseArrayCompat.valueAt(paramAnonymousInt);
    }
    
    public int size(SparseArrayCompat paramAnonymousSparseArrayCompat)
    {
      return paramAnonymousSparseArrayCompat.size();
    }
  };
  int mAccessibilityFocusedVirtualViewId = Integer.MIN_VALUE;
  private final View mHost;
  private int mHoveredVirtualViewId = Integer.MIN_VALUE;
  int mKeyboardFocusedVirtualViewId = Integer.MIN_VALUE;
  private final AccessibilityManager mManager;
  private MyNodeProvider mNodeProvider;
  private final int[] mTempGlobalRect = new int[2];
  private final Rect mTempParentRect = new Rect();
  private final Rect mTempScreenRect = new Rect();
  private final Rect mTempVisibleRect = new Rect();
  
  public ExploreByTouchHelper(View paramView)
  {
    if (paramView != null)
    {
      mHost = paramView;
      mManager = ((AccessibilityManager)paramView.getContext().getSystemService("accessibility"));
      paramView.setFocusable(true);
      if (ViewCompat.getImportantForAccessibility(paramView) == 0) {
        ViewCompat.setImportantForAccessibility(paramView, 1);
      }
    }
    else
    {
      throw new IllegalArgumentException("View may not be null");
    }
  }
  
  private boolean clearAccessibilityFocus(int paramInt)
  {
    if (mAccessibilityFocusedVirtualViewId == paramInt)
    {
      mAccessibilityFocusedVirtualViewId = Integer.MIN_VALUE;
      mHost.invalidate();
      sendEventForVirtualView(paramInt, 65536);
      return true;
    }
    return false;
  }
  
  private boolean clickKeyboardFocusedVirtualView()
  {
    int i = mKeyboardFocusedVirtualViewId;
    return (i != Integer.MIN_VALUE) && (onPerformActionForVirtualView(i, 16, null));
  }
  
  private AccessibilityEvent createEvent(int paramInt1, int paramInt2)
  {
    if (paramInt1 != -1) {
      return createEventForChild(paramInt1, paramInt2);
    }
    return createEventForHost(paramInt2);
  }
  
  private AccessibilityEvent createEventForChild(int paramInt1, int paramInt2)
  {
    AccessibilityEvent localAccessibilityEvent = AccessibilityEvent.obtain(paramInt2);
    AccessibilityNodeInfoCompat localAccessibilityNodeInfoCompat = obtainAccessibilityNodeInfo(paramInt1);
    localAccessibilityEvent.getText().add(localAccessibilityNodeInfoCompat.getText());
    localAccessibilityEvent.setContentDescription(localAccessibilityNodeInfoCompat.getContentDescription());
    localAccessibilityEvent.setScrollable(localAccessibilityNodeInfoCompat.isScrollable());
    localAccessibilityEvent.setPassword(localAccessibilityNodeInfoCompat.isPassword());
    localAccessibilityEvent.setEnabled(localAccessibilityNodeInfoCompat.isEnabled());
    localAccessibilityEvent.setChecked(localAccessibilityNodeInfoCompat.isChecked());
    onPopulateEventForVirtualView(paramInt1, localAccessibilityEvent);
    if ((localAccessibilityEvent.getText().isEmpty()) && (localAccessibilityEvent.getContentDescription() == null)) {
      throw new RuntimeException("Callbacks must add text or a content description in populateEventForVirtualViewId()");
    }
    localAccessibilityEvent.setClassName(localAccessibilityNodeInfoCompat.getClassName());
    AccessibilityRecordCompat.setSource(localAccessibilityEvent, mHost, paramInt1);
    localAccessibilityEvent.setPackageName(mHost.getContext().getPackageName());
    return localAccessibilityEvent;
  }
  
  private AccessibilityEvent createEventForHost(int paramInt)
  {
    AccessibilityEvent localAccessibilityEvent = AccessibilityEvent.obtain(paramInt);
    mHost.onInitializeAccessibilityEvent(localAccessibilityEvent);
    return localAccessibilityEvent;
  }
  
  private AccessibilityNodeInfoCompat createNodeForChild(int paramInt)
  {
    Object localObject = AccessibilityNodeInfoCompat.obtain();
    ((AccessibilityNodeInfoCompat)localObject).setEnabled(true);
    ((AccessibilityNodeInfoCompat)localObject).setFocusable(true);
    ((AccessibilityNodeInfoCompat)localObject).setClassName("android.view.View");
    ((AccessibilityNodeInfoCompat)localObject).setBoundsInParent(INVALID_PARENT_BOUNDS);
    ((AccessibilityNodeInfoCompat)localObject).setBoundsInScreen(INVALID_PARENT_BOUNDS);
    ((AccessibilityNodeInfoCompat)localObject).setParent(mHost);
    onPopulateNodeForVirtualView(paramInt, (AccessibilityNodeInfoCompat)localObject);
    if ((((AccessibilityNodeInfoCompat)localObject).getText() == null) && (((AccessibilityNodeInfoCompat)localObject).getContentDescription() == null)) {
      throw new RuntimeException("Callbacks must add text or a content description in populateNodeForVirtualViewId()");
    }
    ((AccessibilityNodeInfoCompat)localObject).getBoundsInParent(mTempParentRect);
    if (!mTempParentRect.equals(INVALID_PARENT_BOUNDS))
    {
      int i = ((AccessibilityNodeInfoCompat)localObject).getActions();
      if ((i & 0x40) == 0)
      {
        if ((i & 0x80) == 0)
        {
          ((AccessibilityNodeInfoCompat)localObject).setPackageName(mHost.getContext().getPackageName());
          ((AccessibilityNodeInfoCompat)localObject).setSource(mHost, paramInt);
          if (mAccessibilityFocusedVirtualViewId == paramInt)
          {
            ((AccessibilityNodeInfoCompat)localObject).setAccessibilityFocused(true);
            ((AccessibilityNodeInfoCompat)localObject).addAction(128);
          }
          else
          {
            ((AccessibilityNodeInfoCompat)localObject).setAccessibilityFocused(false);
            ((AccessibilityNodeInfoCompat)localObject).addAction(64);
          }
          boolean bool;
          if (mKeyboardFocusedVirtualViewId == paramInt) {
            bool = true;
          } else {
            bool = false;
          }
          if (bool) {
            ((AccessibilityNodeInfoCompat)localObject).addAction(2);
          } else if (((AccessibilityNodeInfoCompat)localObject).isFocusable()) {
            ((AccessibilityNodeInfoCompat)localObject).addAction(1);
          }
          ((AccessibilityNodeInfoCompat)localObject).setFocused(bool);
          mHost.getLocationOnScreen(mTempGlobalRect);
          ((AccessibilityNodeInfoCompat)localObject).getBoundsInScreen(mTempScreenRect);
          if (mTempScreenRect.equals(INVALID_PARENT_BOUNDS))
          {
            ((AccessibilityNodeInfoCompat)localObject).getBoundsInParent(mTempScreenRect);
            if (mParentVirtualDescendantId != -1)
            {
              AccessibilityNodeInfoCompat localAccessibilityNodeInfoCompat = AccessibilityNodeInfoCompat.obtain();
              for (paramInt = mParentVirtualDescendantId; paramInt != -1; paramInt = mParentVirtualDescendantId)
              {
                localAccessibilityNodeInfoCompat.setParent(mHost, -1);
                localAccessibilityNodeInfoCompat.setBoundsInParent(INVALID_PARENT_BOUNDS);
                onPopulateNodeForVirtualView(paramInt, localAccessibilityNodeInfoCompat);
                localAccessibilityNodeInfoCompat.getBoundsInParent(mTempParentRect);
                mTempScreenRect.offset(mTempParentRect.left, mTempParentRect.top);
              }
              localAccessibilityNodeInfoCompat.recycle();
            }
            mTempScreenRect.offset(mTempGlobalRect[0] - mHost.getScrollX(), mTempGlobalRect[1] - mHost.getScrollY());
          }
          if (mHost.getLocalVisibleRect(mTempVisibleRect))
          {
            mTempVisibleRect.offset(mTempGlobalRect[0] - mHost.getScrollX(), mTempGlobalRect[1] - mHost.getScrollY());
            if (mTempScreenRect.intersect(mTempVisibleRect))
            {
              ((AccessibilityNodeInfoCompat)localObject).setBoundsInScreen(mTempScreenRect);
              if (isVisibleToUser(mTempScreenRect))
              {
                ((AccessibilityNodeInfoCompat)localObject).setVisibleToUser(true);
                return localObject;
              }
            }
          }
        }
        else
        {
          throw new RuntimeException("Callbacks must not add ACTION_CLEAR_ACCESSIBILITY_FOCUS in populateNodeForVirtualViewId()");
        }
      }
      else {
        throw new RuntimeException("Callbacks must not add ACTION_ACCESSIBILITY_FOCUS in populateNodeForVirtualViewId()");
      }
    }
    else
    {
      localObject = new RuntimeException("Callbacks must set parent bounds in populateNodeForVirtualViewId()");
      throw ((Throwable)localObject);
    }
    return localObject;
  }
  
  private AccessibilityNodeInfoCompat createNodeForHost()
  {
    AccessibilityNodeInfoCompat localAccessibilityNodeInfoCompat = AccessibilityNodeInfoCompat.obtain(mHost);
    ViewCompat.onInitializeAccessibilityNodeInfo(mHost, localAccessibilityNodeInfoCompat);
    ArrayList localArrayList = new ArrayList();
    getVisibleVirtualViews(localArrayList);
    if ((localAccessibilityNodeInfoCompat.getChildCount() > 0) && (localArrayList.size() > 0)) {
      throw new RuntimeException("Views cannot have both real and virtual children");
    }
    int i = 0;
    int j = localArrayList.size();
    while (i < j)
    {
      localAccessibilityNodeInfoCompat.addChild(mHost, ((Integer)localArrayList.get(i)).intValue());
      i += 1;
    }
    return localAccessibilityNodeInfoCompat;
  }
  
  private SparseArrayCompat getAllNodes()
  {
    ArrayList localArrayList = new ArrayList();
    getVisibleVirtualViews(localArrayList);
    SparseArrayCompat localSparseArrayCompat = new SparseArrayCompat();
    int i = 0;
    while (i < localArrayList.size())
    {
      localSparseArrayCompat.put(i, createNodeForChild(i));
      i += 1;
    }
    return localSparseArrayCompat;
  }
  
  private void getBoundsInParent(int paramInt, Rect paramRect)
  {
    obtainAccessibilityNodeInfo(paramInt).getBoundsInParent(paramRect);
  }
  
  private static Rect guessPreviouslyFocusedRect(View paramView, int paramInt, Rect paramRect)
  {
    int i = paramView.getWidth();
    int j = paramView.getHeight();
    if (paramInt != 17)
    {
      if (paramInt != 33)
      {
        if (paramInt != 66)
        {
          if (paramInt == 130)
          {
            paramRect.set(0, -1, i, -1);
            return paramRect;
          }
          throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
        }
        paramRect.set(-1, 0, -1, j);
        return paramRect;
      }
      paramRect.set(0, j, i, j);
      return paramRect;
    }
    paramRect.set(i, 0, i, j);
    return paramRect;
  }
  
  private boolean isVisibleToUser(Rect paramRect)
  {
    if (paramRect != null)
    {
      if (paramRect.isEmpty()) {
        return false;
      }
      if (mHost.getWindowVisibility() != 0) {
        return false;
      }
      paramRect = mHost.getParent();
      while ((paramRect instanceof View))
      {
        paramRect = (View)paramRect;
        if (paramRect.getAlpha() > 0.0F)
        {
          if (paramRect.getVisibility() != 0) {
            return false;
          }
          paramRect = paramRect.getParent();
        }
        else
        {
          return false;
        }
      }
      if (paramRect != null) {
        return true;
      }
    }
    return false;
  }
  
  private static int keyToDirection(int paramInt)
  {
    if (paramInt != 19)
    {
      if (paramInt != 21)
      {
        if (paramInt != 22) {
          return 130;
        }
        return 66;
      }
      return 17;
    }
    return 33;
  }
  
  private boolean moveFocus(int paramInt, Rect paramRect)
  {
    SparseArrayCompat localSparseArrayCompat = getAllNodes();
    int j = mKeyboardFocusedVirtualViewId;
    int i = Integer.MIN_VALUE;
    AccessibilityNodeInfoCompat localAccessibilityNodeInfoCompat;
    if (j == Integer.MIN_VALUE) {
      localAccessibilityNodeInfoCompat = null;
    } else {
      localAccessibilityNodeInfoCompat = (AccessibilityNodeInfoCompat)localSparseArrayCompat.get(j);
    }
    if ((paramInt != 1) && (paramInt != 2))
    {
      if ((paramInt != 17) && (paramInt != 33) && (paramInt != 66) && (paramInt != 130)) {
        throw new IllegalArgumentException("direction must be one of {FOCUS_FORWARD, FOCUS_BACKWARD, FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
      }
      Rect localRect = new Rect();
      j = mKeyboardFocusedVirtualViewId;
      if (j != Integer.MIN_VALUE) {
        getBoundsInParent(j, localRect);
      } else if (paramRect != null) {
        localRect.set(paramRect);
      } else {
        guessPreviouslyFocusedRect(mHost, paramInt, localRect);
      }
      paramRect = (AccessibilityNodeInfoCompat)FocusStrategy.findNextFocusInAbsoluteDirection(localSparseArrayCompat, SPARSE_VALUES_ADAPTER, NODE_ADAPTER, localAccessibilityNodeInfoCompat, localRect, paramInt);
    }
    else
    {
      boolean bool;
      if (ViewCompat.getLayoutDirection(mHost) == 1) {
        bool = true;
      } else {
        bool = false;
      }
      paramRect = (AccessibilityNodeInfoCompat)FocusStrategy.findNextFocusInRelativeDirection(localSparseArrayCompat, SPARSE_VALUES_ADAPTER, NODE_ADAPTER, localAccessibilityNodeInfoCompat, paramInt, bool, false);
    }
    if (paramRect == null) {
      paramInt = i;
    } else {
      paramInt = localSparseArrayCompat.keyAt(localSparseArrayCompat.indexOfValue(paramRect));
    }
    return requestKeyboardFocusForVirtualView(paramInt);
  }
  
  private boolean performActionForChild(int paramInt1, int paramInt2, Bundle paramBundle)
  {
    if (paramInt2 != 1)
    {
      if (paramInt2 != 2)
      {
        if (paramInt2 != 64)
        {
          if (paramInt2 != 128) {
            return onPerformActionForVirtualView(paramInt1, paramInt2, paramBundle);
          }
          return clearAccessibilityFocus(paramInt1);
        }
        return requestAccessibilityFocus(paramInt1);
      }
      return clearKeyboardFocusForVirtualView(paramInt1);
    }
    return requestKeyboardFocusForVirtualView(paramInt1);
  }
  
  private boolean performActionForHost(int paramInt, Bundle paramBundle)
  {
    return ViewCompat.performAccessibilityAction(mHost, paramInt, paramBundle);
  }
  
  private boolean requestAccessibilityFocus(int paramInt)
  {
    if (mManager.isEnabled())
    {
      if (!mManager.isTouchExplorationEnabled()) {
        return false;
      }
      int i = mAccessibilityFocusedVirtualViewId;
      if (i != paramInt)
      {
        if (i != Integer.MIN_VALUE) {
          clearAccessibilityFocus(i);
        }
        mAccessibilityFocusedVirtualViewId = paramInt;
        mHost.invalidate();
        sendEventForVirtualView(paramInt, 32768);
        return true;
      }
    }
    return false;
  }
  
  private void updateHoveredVirtualView(int paramInt)
  {
    int i = mHoveredVirtualViewId;
    if (i == paramInt) {
      return;
    }
    mHoveredVirtualViewId = paramInt;
    sendEventForVirtualView(paramInt, 128);
    sendEventForVirtualView(i, 256);
  }
  
  public final boolean clearKeyboardFocusForVirtualView(int paramInt)
  {
    if (mKeyboardFocusedVirtualViewId != paramInt) {
      return false;
    }
    mKeyboardFocusedVirtualViewId = Integer.MIN_VALUE;
    onVirtualViewKeyboardFocusChanged(paramInt, false);
    sendEventForVirtualView(paramInt, 8);
    return true;
  }
  
  public final boolean dispatchHoverEvent(MotionEvent paramMotionEvent)
  {
    if (mManager.isEnabled())
    {
      if (!mManager.isTouchExplorationEnabled()) {
        return false;
      }
      int i = paramMotionEvent.getAction();
      if ((i != 7) && (i != 9))
      {
        if (i != 10) {
          return false;
        }
        if (mHoveredVirtualViewId != Integer.MIN_VALUE)
        {
          updateHoveredVirtualView(Integer.MIN_VALUE);
          return true;
        }
        return false;
      }
      i = getVirtualViewAt(paramMotionEvent.getX(), paramMotionEvent.getY());
      updateHoveredVirtualView(i);
      if (i != Integer.MIN_VALUE) {
        return true;
      }
    }
    return false;
  }
  
  public final boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
  {
    int j = paramKeyEvent.getAction();
    int i = 0;
    if (j != 1)
    {
      j = paramKeyEvent.getKeyCode();
      if (j != 61)
      {
        if (j != 66)
        {
          switch (j)
          {
          default: 
            return false;
          case 19: 
          case 20: 
          case 21: 
          case 22: 
            if (!paramKeyEvent.hasNoModifiers()) {
              break;
            }
            j = keyToDirection(j);
            int k = paramKeyEvent.getRepeatCount();
            for (boolean bool = false; (i < k + 1) && (moveFocus(j, null)); bool = true) {
              i += 1;
            }
            return bool;
          }
        }
        else if ((paramKeyEvent.hasNoModifiers()) && (paramKeyEvent.getRepeatCount() == 0))
        {
          clickKeyboardFocusedVirtualView();
          return true;
        }
      }
      else
      {
        if (paramKeyEvent.hasNoModifiers()) {
          return moveFocus(2, null);
        }
        if (paramKeyEvent.hasModifiers(1)) {
          return moveFocus(1, null);
        }
      }
    }
    return false;
  }
  
  public final int getAccessibilityFocusedVirtualViewId()
  {
    return mAccessibilityFocusedVirtualViewId;
  }
  
  public AccessibilityNodeProviderCompat getAccessibilityNodeProvider(View paramView)
  {
    if (mNodeProvider == null) {
      mNodeProvider = new MyNodeProvider();
    }
    return mNodeProvider;
  }
  
  public int getFocusedVirtualView()
  {
    return getAccessibilityFocusedVirtualViewId();
  }
  
  public final int getKeyboardFocusedVirtualViewId()
  {
    return mKeyboardFocusedVirtualViewId;
  }
  
  protected abstract int getVirtualViewAt(float paramFloat1, float paramFloat2);
  
  protected abstract void getVisibleVirtualViews(List paramList);
  
  public final void invalidateRoot()
  {
    invalidateVirtualView(-1, 1);
  }
  
  public final void invalidateVirtualView(int paramInt)
  {
    invalidateVirtualView(paramInt, 0);
  }
  
  public final void invalidateVirtualView(int paramInt1, int paramInt2)
  {
    if ((paramInt1 != Integer.MIN_VALUE) && (mManager.isEnabled()))
    {
      ViewParent localViewParent = mHost.getParent();
      if (localViewParent != null)
      {
        AccessibilityEvent localAccessibilityEvent = createEvent(paramInt1, 2048);
        AccessibilityEventCompat.setContentChangeTypes(localAccessibilityEvent, paramInt2);
        ViewParentCompat.requestSendAccessibilityEvent(localViewParent, mHost, localAccessibilityEvent);
      }
    }
  }
  
  AccessibilityNodeInfoCompat obtainAccessibilityNodeInfo(int paramInt)
  {
    if (paramInt == -1) {
      return createNodeForHost();
    }
    return createNodeForChild(paramInt);
  }
  
  public final void onFocusChanged(boolean paramBoolean, int paramInt, Rect paramRect)
  {
    int i = mKeyboardFocusedVirtualViewId;
    if (i != Integer.MIN_VALUE) {
      clearKeyboardFocusForVirtualView(i);
    }
    if (paramBoolean) {
      moveFocus(paramInt, paramRect);
    }
  }
  
  public void onInitializeAccessibilityEvent(View paramView, AccessibilityEvent paramAccessibilityEvent)
  {
    super.onInitializeAccessibilityEvent(paramView, paramAccessibilityEvent);
    onPopulateEventForHost(paramAccessibilityEvent);
  }
  
  public void onInitializeAccessibilityNodeInfo(View paramView, AccessibilityNodeInfoCompat paramAccessibilityNodeInfoCompat)
  {
    super.onInitializeAccessibilityNodeInfo(paramView, paramAccessibilityNodeInfoCompat);
    onPopulateNodeForHost(paramAccessibilityNodeInfoCompat);
  }
  
  protected abstract boolean onPerformActionForVirtualView(int paramInt1, int paramInt2, Bundle paramBundle);
  
  protected void onPopulateEventForHost(AccessibilityEvent paramAccessibilityEvent) {}
  
  protected void onPopulateEventForVirtualView(int paramInt, AccessibilityEvent paramAccessibilityEvent) {}
  
  protected void onPopulateNodeForHost(AccessibilityNodeInfoCompat paramAccessibilityNodeInfoCompat) {}
  
  protected abstract void onPopulateNodeForVirtualView(int paramInt, AccessibilityNodeInfoCompat paramAccessibilityNodeInfoCompat);
  
  protected void onVirtualViewKeyboardFocusChanged(int paramInt, boolean paramBoolean) {}
  
  boolean performAction(int paramInt1, int paramInt2, Bundle paramBundle)
  {
    if (paramInt1 != -1) {
      return performActionForChild(paramInt1, paramInt2, paramBundle);
    }
    return performActionForHost(paramInt2, paramBundle);
  }
  
  public final boolean requestKeyboardFocusForVirtualView(int paramInt)
  {
    if ((!mHost.isFocused()) && (!mHost.requestFocus())) {
      return false;
    }
    int i = mKeyboardFocusedVirtualViewId;
    if (i == paramInt) {
      return false;
    }
    if (i != Integer.MIN_VALUE) {
      clearKeyboardFocusForVirtualView(i);
    }
    mKeyboardFocusedVirtualViewId = paramInt;
    onVirtualViewKeyboardFocusChanged(paramInt, true);
    sendEventForVirtualView(paramInt, 8);
    return true;
  }
  
  public final boolean sendEventForVirtualView(int paramInt1, int paramInt2)
  {
    if (paramInt1 != Integer.MIN_VALUE)
    {
      if (!mManager.isEnabled()) {
        return false;
      }
      ViewParent localViewParent = mHost.getParent();
      if (localViewParent == null) {
        return false;
      }
      AccessibilityEvent localAccessibilityEvent = createEvent(paramInt1, paramInt2);
      return ViewParentCompat.requestSendAccessibilityEvent(localViewParent, mHost, localAccessibilityEvent);
    }
    return false;
  }
  
  private class MyNodeProvider
    extends AccessibilityNodeProviderCompat
  {
    MyNodeProvider() {}
    
    public AccessibilityNodeInfoCompat createAccessibilityNodeInfo(int paramInt)
    {
      return AccessibilityNodeInfoCompat.obtain(obtainAccessibilityNodeInfo(paramInt));
    }
    
    public AccessibilityNodeInfoCompat findFocus(int paramInt)
    {
      if (paramInt == 2) {
        paramInt = mAccessibilityFocusedVirtualViewId;
      } else {
        paramInt = mKeyboardFocusedVirtualViewId;
      }
      if (paramInt == Integer.MIN_VALUE) {
        return null;
      }
      return createAccessibilityNodeInfo(paramInt);
    }
    
    public boolean performAction(int paramInt1, int paramInt2, Bundle paramBundle)
    {
      return ExploreByTouchHelper.this.performAction(paramInt1, paramInt2, paramBundle);
    }
  }
}
