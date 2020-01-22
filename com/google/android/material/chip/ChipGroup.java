package com.google.android.material.chip;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewGroup.OnHierarchyChangeListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import com.google.android.material.R.attr;
import com.google.android.material.R.style;
import com.google.android.material.R.styleable;
import com.google.android.material.internal.FlowLayout;
import com.google.android.material.internal.ThemeEnforcement;

public class ChipGroup
  extends FlowLayout
{
  private int checkedId = -1;
  private final CheckedStateTracker checkedStateTracker = new CheckedStateTracker(null);
  private int chipSpacingHorizontal;
  private int chipSpacingVertical;
  private OnCheckedChangeListener onCheckedChangeListener;
  private PassThroughHierarchyChangeListener passThroughListener = new PassThroughHierarchyChangeListener(null);
  private boolean protectFromCheckedChange = false;
  private boolean singleSelection;
  
  public ChipGroup(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public ChipGroup(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, R.attr.chipGroupStyle);
  }
  
  public ChipGroup(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    paramContext = ThemeEnforcement.obtainStyledAttributes(paramContext, paramAttributeSet, R.styleable.ChipGroup, paramInt, R.style.Widget_MaterialComponents_ChipGroup, new int[0]);
    paramInt = paramContext.getDimensionPixelOffset(R.styleable.ChipGroup_chipSpacing, 0);
    setChipSpacingHorizontal(paramContext.getDimensionPixelOffset(R.styleable.ChipGroup_chipSpacingHorizontal, paramInt));
    setChipSpacingVertical(paramContext.getDimensionPixelOffset(R.styleable.ChipGroup_chipSpacingVertical, paramInt));
    setSingleLine(paramContext.getBoolean(R.styleable.ChipGroup_singleLine, false));
    setSingleSelection(paramContext.getBoolean(R.styleable.ChipGroup_singleSelection, false));
    paramInt = paramContext.getResourceId(R.styleable.ChipGroup_checkedChip, -1);
    if (paramInt != -1) {
      checkedId = paramInt;
    }
    paramContext.recycle();
    super.setOnHierarchyChangeListener(passThroughListener);
  }
  
  private void setCheckedId(int paramInt)
  {
    checkedId = paramInt;
    OnCheckedChangeListener localOnCheckedChangeListener = onCheckedChangeListener;
    if ((localOnCheckedChangeListener != null) && (singleSelection)) {
      localOnCheckedChangeListener.onCheckedChanged(this, paramInt);
    }
  }
  
  private void setCheckedStateForView(int paramInt, boolean paramBoolean)
  {
    View localView = findViewById(paramInt);
    if ((localView instanceof Chip))
    {
      protectFromCheckedChange = true;
      ((Chip)localView).setChecked(paramBoolean);
      protectFromCheckedChange = false;
    }
  }
  
  public void addView(View paramView, int paramInt, ViewGroup.LayoutParams paramLayoutParams)
  {
    if ((paramView instanceof Chip))
    {
      Chip localChip = (Chip)paramView;
      if (localChip.isChecked())
      {
        int i = checkedId;
        if ((i != -1) && (singleSelection)) {
          setCheckedStateForView(i, false);
        }
        setCheckedId(localChip.getId());
      }
    }
    super.addView(paramView, paramInt, paramLayoutParams);
  }
  
  public void check(int paramInt)
  {
    int i = checkedId;
    if (paramInt == i) {
      return;
    }
    if ((i != -1) && (singleSelection)) {
      setCheckedStateForView(i, false);
    }
    if (paramInt != -1) {
      setCheckedStateForView(paramInt, true);
    }
    setCheckedId(paramInt);
  }
  
  protected boolean checkLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    return (super.checkLayoutParams(paramLayoutParams)) && ((paramLayoutParams instanceof LayoutParams));
  }
  
  public void clearCheck()
  {
    protectFromCheckedChange = true;
    int i = 0;
    while (i < getChildCount())
    {
      View localView = getChildAt(i);
      if ((localView instanceof Chip)) {
        ((Chip)localView).setChecked(false);
      }
      i += 1;
    }
    protectFromCheckedChange = false;
    setCheckedId(-1);
  }
  
  protected ViewGroup.LayoutParams generateDefaultLayoutParams()
  {
    return new LayoutParams(-2, -2);
  }
  
  public ViewGroup.LayoutParams generateLayoutParams(AttributeSet paramAttributeSet)
  {
    return new LayoutParams(getContext(), paramAttributeSet);
  }
  
  protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    return new LayoutParams(paramLayoutParams);
  }
  
  public int getCheckedChipId()
  {
    if (singleSelection) {
      return checkedId;
    }
    return -1;
  }
  
  public int getChipSpacingHorizontal()
  {
    return chipSpacingHorizontal;
  }
  
  public int getChipSpacingVertical()
  {
    return chipSpacingVertical;
  }
  
  public boolean isSingleSelection()
  {
    return singleSelection;
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    int i = checkedId;
    if (i != -1)
    {
      setCheckedStateForView(i, true);
      setCheckedId(checkedId);
    }
  }
  
  public void setChipSpacing(int paramInt)
  {
    setChipSpacingHorizontal(paramInt);
    setChipSpacingVertical(paramInt);
  }
  
  public void setChipSpacingHorizontal(int paramInt)
  {
    if (chipSpacingHorizontal != paramInt)
    {
      chipSpacingHorizontal = paramInt;
      setItemSpacing(paramInt);
      requestLayout();
    }
  }
  
  public void setChipSpacingHorizontalResource(int paramInt)
  {
    setChipSpacingHorizontal(getResources().getDimensionPixelOffset(paramInt));
  }
  
  public void setChipSpacingResource(int paramInt)
  {
    setChipSpacing(getResources().getDimensionPixelOffset(paramInt));
  }
  
  public void setChipSpacingVertical(int paramInt)
  {
    if (chipSpacingVertical != paramInt)
    {
      chipSpacingVertical = paramInt;
      setLineSpacing(paramInt);
      requestLayout();
    }
  }
  
  public void setChipSpacingVerticalResource(int paramInt)
  {
    setChipSpacingVertical(getResources().getDimensionPixelOffset(paramInt));
  }
  
  public void setDividerDrawableHorizontal(Drawable paramDrawable)
  {
    throw new UnsupportedOperationException("Changing divider drawables have no effect. ChipGroup do not use divider drawables as spacing.");
  }
  
  public void setDividerDrawableVertical(Drawable paramDrawable)
  {
    throw new UnsupportedOperationException("Changing divider drawables have no effect. ChipGroup do not use divider drawables as spacing.");
  }
  
  public void setFlexWrap(int paramInt)
  {
    throw new UnsupportedOperationException("Changing flex wrap not allowed. ChipGroup exposes a singleLine attribute instead.");
  }
  
  public void setOnCheckedChangeListener(OnCheckedChangeListener paramOnCheckedChangeListener)
  {
    onCheckedChangeListener = paramOnCheckedChangeListener;
  }
  
  public void setOnHierarchyChangeListener(ViewGroup.OnHierarchyChangeListener paramOnHierarchyChangeListener)
  {
    PassThroughHierarchyChangeListener.access$202(passThroughListener, paramOnHierarchyChangeListener);
  }
  
  public void setShowDividerHorizontal(int paramInt)
  {
    throw new UnsupportedOperationException("Changing divider modes has no effect. ChipGroup do not use divider drawables as spacing.");
  }
  
  public void setShowDividerVertical(int paramInt)
  {
    throw new UnsupportedOperationException("Changing divider modes has no effect. ChipGroup do not use divider drawables as spacing.");
  }
  
  public void setSingleLine(int paramInt)
  {
    setSingleLine(getResources().getBoolean(paramInt));
  }
  
  public void setSingleSelection(int paramInt)
  {
    setSingleSelection(getResources().getBoolean(paramInt));
  }
  
  public void setSingleSelection(boolean paramBoolean)
  {
    if (singleSelection != paramBoolean)
    {
      singleSelection = paramBoolean;
      clearCheck();
    }
  }
  
  private class CheckedStateTracker
    implements CompoundButton.OnCheckedChangeListener
  {
    private CheckedStateTracker() {}
    
    public void onCheckedChanged(CompoundButton paramCompoundButton, boolean paramBoolean)
    {
      if (protectFromCheckedChange) {
        return;
      }
      int i = paramCompoundButton.getId();
      if (paramBoolean)
      {
        if ((checkedId != -1) && (checkedId != i) && (singleSelection))
        {
          paramCompoundButton = ChipGroup.this;
          paramCompoundButton.setCheckedStateForView(checkedId, false);
        }
        ChipGroup.this.setCheckedId(i);
        return;
      }
      if (checkedId == i) {
        ChipGroup.this.setCheckedId(-1);
      }
    }
  }
  
  public static class LayoutParams
    extends ViewGroup.MarginLayoutParams
  {
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
  }
  
  public static abstract interface OnCheckedChangeListener
  {
    public abstract void onCheckedChanged(ChipGroup paramChipGroup, int paramInt);
  }
  
  private class PassThroughHierarchyChangeListener
    implements ViewGroup.OnHierarchyChangeListener
  {
    private ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener;
    
    private PassThroughHierarchyChangeListener() {}
    
    public void onChildViewAdded(View paramView1, View paramView2)
    {
      if ((paramView1 == ChipGroup.this) && ((paramView2 instanceof Chip)))
      {
        if (paramView2.getId() == -1)
        {
          int i;
          if (Build.VERSION.SDK_INT >= 17) {
            i = View.generateViewId();
          } else {
            i = paramView2.hashCode();
          }
          paramView2.setId(i);
        }
        ((Chip)paramView2).setOnCheckedChangeListenerInternal(checkedStateTracker);
      }
      ViewGroup.OnHierarchyChangeListener localOnHierarchyChangeListener = onHierarchyChangeListener;
      if (localOnHierarchyChangeListener != null) {
        localOnHierarchyChangeListener.onChildViewAdded(paramView1, paramView2);
      }
    }
    
    public void onChildViewRemoved(View paramView1, View paramView2)
    {
      if ((paramView1 == ChipGroup.this) && ((paramView2 instanceof Chip))) {
        ((Chip)paramView2).setOnCheckedChangeListenerInternal(null);
      }
      ViewGroup.OnHierarchyChangeListener localOnHierarchyChangeListener = onHierarchyChangeListener;
      if (localOnHierarchyChangeListener != null) {
        localOnHierarchyChangeListener.onChildViewRemoved(paramView1, paramView2);
      }
    }
  }
}
