package androidx.preference;

import android.content.Context;
import android.content.res.Resources.Theme;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.core.content.delay.TypedArrayUtils;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat.CollectionItemInfoCompat;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

public class PreferenceCategory
  extends PreferenceGroup
{
  public PreferenceCategory(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PreferenceCategory(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, TypedArrayUtils.getAttr(paramContext, R.attr.preferenceCategoryStyle, 16842892));
  }
  
  public PreferenceCategory(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    this(paramContext, paramAttributeSet, paramInt, 0);
  }
  
  public PreferenceCategory(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    super(paramContext, paramAttributeSet, paramInt1, paramInt2);
  }
  
  public boolean isEnabled()
  {
    return false;
  }
  
  public void onBindViewHolder(PreferenceViewHolder paramPreferenceViewHolder)
  {
    super.onBindViewHolder(paramPreferenceViewHolder);
    if (Build.VERSION.SDK_INT >= 28)
    {
      itemView.setAccessibilityHeading(true);
      return;
    }
    if (Build.VERSION.SDK_INT < 21)
    {
      TypedValue localTypedValue = new TypedValue();
      if (!getContext().getTheme().resolveAttribute(R.attr.colorAccent, localTypedValue, true)) {
        return;
      }
      paramPreferenceViewHolder = (TextView)paramPreferenceViewHolder.findViewById(16908310);
      if (paramPreferenceViewHolder == null) {
        return;
      }
      int i = ContextCompat.getColor(getContext(), R.color.preference_fallback_accent_color);
      if (paramPreferenceViewHolder.getCurrentTextColor() != i) {
        return;
      }
      paramPreferenceViewHolder.setTextColor(data);
    }
  }
  
  public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfoCompat paramAccessibilityNodeInfoCompat)
  {
    super.onInitializeAccessibilityNodeInfo(paramAccessibilityNodeInfoCompat);
    if (Build.VERSION.SDK_INT < 28)
    {
      AccessibilityNodeInfoCompat.CollectionItemInfoCompat localCollectionItemInfoCompat = paramAccessibilityNodeInfoCompat.getCollectionItemInfo();
      if (localCollectionItemInfoCompat == null) {
        return;
      }
      paramAccessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(localCollectionItemInfoCompat.getRowIndex(), localCollectionItemInfoCompat.getRowSpan(), localCollectionItemInfoCompat.getColumnIndex(), localCollectionItemInfoCompat.getColumnSpan(), true, localCollectionItemInfoCompat.isSelected()));
    }
  }
  
  public boolean shouldDisableDependents()
  {
    return super.isEnabled() ^ true;
  }
}
