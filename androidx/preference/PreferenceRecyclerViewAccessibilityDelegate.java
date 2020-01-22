package androidx.preference;

import android.os.Bundle;
import android.view.View;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerViewAccessibilityDelegate;

@Deprecated
public class PreferenceRecyclerViewAccessibilityDelegate
  extends RecyclerViewAccessibilityDelegate
{
  final AccessibilityDelegateCompat mDefaultItemDelegate = super.getItemDelegate();
  final AccessibilityDelegateCompat mItemDelegate = new AccessibilityDelegateCompat()
  {
    public void onInitializeAccessibilityNodeInfo(View paramAnonymousView, AccessibilityNodeInfoCompat paramAnonymousAccessibilityNodeInfoCompat)
    {
      mDefaultItemDelegate.onInitializeAccessibilityNodeInfo(paramAnonymousView, paramAnonymousAccessibilityNodeInfoCompat);
      int i = mRecyclerView.getChildAdapterPosition(paramAnonymousView);
      paramAnonymousView = mRecyclerView.getAdapter();
      if (!(paramAnonymousView instanceof PreferenceGroupAdapter)) {
        return;
      }
      paramAnonymousView = ((PreferenceGroupAdapter)paramAnonymousView).getItem(i);
      if (paramAnonymousView == null) {
        return;
      }
      paramAnonymousView.onInitializeAccessibilityNodeInfo(paramAnonymousAccessibilityNodeInfoCompat);
    }
    
    public boolean performAccessibilityAction(View paramAnonymousView, int paramAnonymousInt, Bundle paramAnonymousBundle)
    {
      return mDefaultItemDelegate.performAccessibilityAction(paramAnonymousView, paramAnonymousInt, paramAnonymousBundle);
    }
  };
  final RecyclerView mRecyclerView;
  
  public PreferenceRecyclerViewAccessibilityDelegate(RecyclerView paramRecyclerView)
  {
    super(paramRecyclerView);
    mRecyclerView = paramRecyclerView;
  }
  
  public AccessibilityDelegateCompat getItemDelegate()
  {
    return mItemDelegate;
  }
}
