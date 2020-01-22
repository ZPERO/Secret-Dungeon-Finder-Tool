package androidx.core.view.accessibility;

import android.os.Bundle;
import android.text.style.ClickableSpan;
import android.view.View;

public final class AccessibilityClickableSpanCompat
  extends ClickableSpan
{
  public static final String SPAN_ID = "ACCESSIBILITY_CLICKABLE_SPAN_ID";
  private final int mClickableSpanActionId;
  private final AccessibilityNodeInfoCompat mNodeInfoCompat;
  private final int mOriginalClickableSpanId;
  
  public AccessibilityClickableSpanCompat(int paramInt1, AccessibilityNodeInfoCompat paramAccessibilityNodeInfoCompat, int paramInt2)
  {
    mOriginalClickableSpanId = paramInt1;
    mNodeInfoCompat = paramAccessibilityNodeInfoCompat;
    mClickableSpanActionId = paramInt2;
  }
  
  public void onClick(View paramView)
  {
    paramView = new Bundle();
    paramView.putInt("ACCESSIBILITY_CLICKABLE_SPAN_ID", mOriginalClickableSpanId);
    mNodeInfoCompat.performAction(mClickableSpanActionId, paramView);
  }
}
