package androidx.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import androidx.core.content.delay.TypedArrayUtils;

public class SwitchPreference
  extends TwoStatePreference
{
  private final Listener mListener = new Listener();
  private CharSequence mSwitchOff;
  private CharSequence mSwitchOn;
  
  public SwitchPreference(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public SwitchPreference(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, TypedArrayUtils.getAttr(paramContext, R.attr.switchPreferenceStyle, 16843629));
  }
  
  public SwitchPreference(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    this(paramContext, paramAttributeSet, paramInt, 0);
  }
  
  public SwitchPreference(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    super(paramContext, paramAttributeSet, paramInt1, paramInt2);
    paramContext = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.SwitchPreference, paramInt1, paramInt2);
    setSummaryOn(TypedArrayUtils.getString(paramContext, R.styleable.SwitchPreference_summaryOn, R.styleable.SwitchPreference_android_summaryOn));
    setSummaryOff(TypedArrayUtils.getString(paramContext, R.styleable.SwitchPreference_summaryOff, R.styleable.SwitchPreference_android_summaryOff));
    setSwitchTextOn(TypedArrayUtils.getString(paramContext, R.styleable.SwitchPreference_switchTextOn, R.styleable.SwitchPreference_android_switchTextOn));
    setSwitchTextOff(TypedArrayUtils.getString(paramContext, R.styleable.SwitchPreference_switchTextOff, R.styleable.SwitchPreference_android_switchTextOff));
    setDisableDependentsState(TypedArrayUtils.getBoolean(paramContext, R.styleable.SwitchPreference_disableDependentsState, R.styleable.SwitchPreference_android_disableDependentsState, false));
    paramContext.recycle();
  }
  
  private void syncSwitchView(View paramView)
  {
    boolean bool = paramView instanceof Switch;
    if (bool) {
      ((Switch)paramView).setOnCheckedChangeListener(null);
    }
    if ((paramView instanceof Checkable)) {
      ((Checkable)paramView).setChecked(mChecked);
    }
    if (bool)
    {
      paramView = (Switch)paramView;
      paramView.setTextOn(mSwitchOn);
      paramView.setTextOff(mSwitchOff);
      paramView.setOnCheckedChangeListener(mListener);
    }
  }
  
  private void syncViewIfAccessibilityEnabled(View paramView)
  {
    if (!((AccessibilityManager)getContext().getSystemService("accessibility")).isEnabled()) {
      return;
    }
    syncSwitchView(paramView.findViewById(16908352));
    syncSummaryView(paramView.findViewById(16908304));
  }
  
  public CharSequence getSwitchTextOff()
  {
    return mSwitchOff;
  }
  
  public CharSequence getSwitchTextOn()
  {
    return mSwitchOn;
  }
  
  public void onBindViewHolder(PreferenceViewHolder paramPreferenceViewHolder)
  {
    super.onBindViewHolder(paramPreferenceViewHolder);
    syncSwitchView(paramPreferenceViewHolder.findViewById(16908352));
    syncSummaryView(paramPreferenceViewHolder);
  }
  
  protected void performClick(View paramView)
  {
    super.performClick(paramView);
    syncViewIfAccessibilityEnabled(paramView);
  }
  
  public void setSwitchTextOff(int paramInt)
  {
    setSwitchTextOff(getContext().getString(paramInt));
  }
  
  public void setSwitchTextOff(CharSequence paramCharSequence)
  {
    mSwitchOff = paramCharSequence;
    notifyChanged();
  }
  
  public void setSwitchTextOn(int paramInt)
  {
    setSwitchTextOn(getContext().getString(paramInt));
  }
  
  public void setSwitchTextOn(CharSequence paramCharSequence)
  {
    mSwitchOn = paramCharSequence;
    notifyChanged();
  }
  
  private class Listener
    implements CompoundButton.OnCheckedChangeListener
  {
    Listener() {}
    
    public void onCheckedChanged(CompoundButton paramCompoundButton, boolean paramBoolean)
    {
      if (!callChangeListener(Boolean.valueOf(paramBoolean)))
      {
        paramCompoundButton.setChecked(paramBoolean ^ true);
        return;
      }
      setChecked(paramBoolean);
    }
  }
}
