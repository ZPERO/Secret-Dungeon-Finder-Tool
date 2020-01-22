package com.google.android.material.bottomnavigation;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.MenuView.ItemView;
import androidx.appcompat.widget.TooltipCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.PointerIconCompat;
import androidx.core.view.ViewCompat;
import androidx.core.widget.TextViewCompat;
import com.google.android.material.R.dimen;
import com.google.android.material.R.drawable;
import com.google.android.material.R.id;
import com.google.android.material.R.layout;

public class BottomNavigationItemView
  extends FrameLayout
  implements MenuView.ItemView
{
  private static final int[] CHECKED_STATE_SET = { 16842912 };
  public static final int INVALID_ITEM_POSITION = -1;
  private final int defaultMargin;
  private ImageView icon;
  private ColorStateList iconTint;
  private boolean isShifting;
  private MenuItemImpl itemData;
  private int itemPosition = -1;
  private int labelVisibilityMode;
  private final TextView largeLabel;
  private float scaleDownFactor;
  private float scaleUpFactor;
  private float shiftAmount;
  private final TextView smallLabel;
  
  public BottomNavigationItemView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public BottomNavigationItemView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public BottomNavigationItemView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    paramAttributeSet = getResources();
    LayoutInflater.from(paramContext).inflate(R.layout.design_bottom_navigation_item, this, true);
    setBackgroundResource(R.drawable.design_bottom_navigation_item_background);
    defaultMargin = paramAttributeSet.getDimensionPixelSize(R.dimen.design_bottom_navigation_margin);
    icon = ((ImageView)findViewById(R.id.icon));
    smallLabel = ((TextView)findViewById(R.id.smallLabel));
    largeLabel = ((TextView)findViewById(R.id.largeLabel));
    ViewCompat.setImportantForAccessibility(smallLabel, 2);
    ViewCompat.setImportantForAccessibility(largeLabel, 2);
    setFocusable(true);
    calculateTextScaleFactors(smallLabel.getTextSize(), largeLabel.getTextSize());
  }
  
  private void calculateTextScaleFactors(float paramFloat1, float paramFloat2)
  {
    shiftAmount = (paramFloat1 - paramFloat2);
    scaleUpFactor = (paramFloat2 * 1.0F / paramFloat1);
    scaleDownFactor = (paramFloat1 * 1.0F / paramFloat2);
  }
  
  private void setViewLayoutParams(View paramView, int paramInt1, int paramInt2)
  {
    FrameLayout.LayoutParams localLayoutParams = (FrameLayout.LayoutParams)paramView.getLayoutParams();
    topMargin = paramInt1;
    gravity = paramInt2;
    paramView.setLayoutParams(localLayoutParams);
  }
  
  private void setViewValues(View paramView, float paramFloat1, float paramFloat2, int paramInt)
  {
    paramView.setScaleX(paramFloat1);
    paramView.setScaleY(paramFloat2);
    paramView.setVisibility(paramInt);
  }
  
  public MenuItemImpl getItemData()
  {
    return itemData;
  }
  
  public int getItemPosition()
  {
    return itemPosition;
  }
  
  public void initialize(MenuItemImpl paramMenuItemImpl, int paramInt)
  {
    itemData = paramMenuItemImpl;
    setCheckable(paramMenuItemImpl.isCheckable());
    setChecked(paramMenuItemImpl.isChecked());
    setEnabled(paramMenuItemImpl.isEnabled());
    setIcon(paramMenuItemImpl.getIcon());
    setTitle(paramMenuItemImpl.getTitle());
    setId(paramMenuItemImpl.getItemId());
    if (!TextUtils.isEmpty(paramMenuItemImpl.getContentDescription())) {
      setContentDescription(paramMenuItemImpl.getContentDescription());
    }
    TooltipCompat.setTooltipText(this, paramMenuItemImpl.getTooltipText());
    if (paramMenuItemImpl.isVisible()) {
      paramInt = 0;
    } else {
      paramInt = 8;
    }
    setVisibility(paramInt);
  }
  
  public int[] onCreateDrawableState(int paramInt)
  {
    int[] arrayOfInt = super.onCreateDrawableState(paramInt + 1);
    MenuItemImpl localMenuItemImpl = itemData;
    if ((localMenuItemImpl != null) && (localMenuItemImpl.isCheckable()) && (itemData.isChecked())) {
      View.mergeDrawableStates(arrayOfInt, CHECKED_STATE_SET);
    }
    return arrayOfInt;
  }
  
  public boolean prefersCondensedTitle()
  {
    return false;
  }
  
  public void setCheckable(boolean paramBoolean)
  {
    refreshDrawableState();
  }
  
  public void setChecked(boolean paramBoolean)
  {
    TextView localTextView = largeLabel;
    localTextView.setPivotX(localTextView.getWidth() / 2);
    localTextView = largeLabel;
    localTextView.setPivotY(localTextView.getBaseline());
    localTextView = smallLabel;
    localTextView.setPivotX(localTextView.getWidth() / 2);
    localTextView = smallLabel;
    localTextView.setPivotY(localTextView.getBaseline());
    int i = labelVisibilityMode;
    float f;
    if (i != -1)
    {
      if (i != 0)
      {
        if (i != 1)
        {
          if (i == 2)
          {
            setViewLayoutParams(icon, defaultMargin, 17);
            largeLabel.setVisibility(8);
            smallLabel.setVisibility(8);
          }
        }
        else if (paramBoolean)
        {
          setViewLayoutParams(icon, (int)(defaultMargin + shiftAmount), 49);
          setViewValues(largeLabel, 1.0F, 1.0F, 0);
          localTextView = smallLabel;
          f = scaleUpFactor;
          setViewValues(localTextView, f, f, 4);
        }
        else
        {
          setViewLayoutParams(icon, defaultMargin, 49);
          localTextView = largeLabel;
          f = scaleDownFactor;
          setViewValues(localTextView, f, f, 4);
          setViewValues(smallLabel, 1.0F, 1.0F, 0);
        }
      }
      else
      {
        if (paramBoolean)
        {
          setViewLayoutParams(icon, defaultMargin, 49);
          setViewValues(largeLabel, 1.0F, 1.0F, 0);
        }
        else
        {
          setViewLayoutParams(icon, defaultMargin, 17);
          setViewValues(largeLabel, 0.5F, 0.5F, 4);
        }
        smallLabel.setVisibility(4);
      }
    }
    else if (isShifting)
    {
      if (paramBoolean)
      {
        setViewLayoutParams(icon, defaultMargin, 49);
        setViewValues(largeLabel, 1.0F, 1.0F, 0);
      }
      else
      {
        setViewLayoutParams(icon, defaultMargin, 17);
        setViewValues(largeLabel, 0.5F, 0.5F, 4);
      }
      smallLabel.setVisibility(4);
    }
    else if (paramBoolean)
    {
      setViewLayoutParams(icon, (int)(defaultMargin + shiftAmount), 49);
      setViewValues(largeLabel, 1.0F, 1.0F, 0);
      localTextView = smallLabel;
      f = scaleUpFactor;
      setViewValues(localTextView, f, f, 4);
    }
    else
    {
      setViewLayoutParams(icon, defaultMargin, 49);
      localTextView = largeLabel;
      f = scaleDownFactor;
      setViewValues(localTextView, f, f, 4);
      setViewValues(smallLabel, 1.0F, 1.0F, 0);
    }
    refreshDrawableState();
    setSelected(paramBoolean);
  }
  
  public void setEnabled(boolean paramBoolean)
  {
    super.setEnabled(paramBoolean);
    smallLabel.setEnabled(paramBoolean);
    largeLabel.setEnabled(paramBoolean);
    icon.setEnabled(paramBoolean);
    if (paramBoolean)
    {
      ViewCompat.setPointerIcon(this, PointerIconCompat.getSystemIcon(getContext(), 1002));
      return;
    }
    ViewCompat.setPointerIcon(this, null);
  }
  
  public void setIcon(Drawable paramDrawable)
  {
    Object localObject = paramDrawable;
    if (paramDrawable != null)
    {
      localObject = paramDrawable.getConstantState();
      if (localObject != null) {
        paramDrawable = ((Drawable.ConstantState)localObject).newDrawable();
      }
      localObject = DrawableCompat.wrap(paramDrawable).mutate();
      paramDrawable = (Drawable)localObject;
      DrawableCompat.setTintList((Drawable)localObject, iconTint);
      localObject = paramDrawable;
    }
    icon.setImageDrawable((Drawable)localObject);
  }
  
  public void setIconSize(int paramInt)
  {
    FrameLayout.LayoutParams localLayoutParams = (FrameLayout.LayoutParams)icon.getLayoutParams();
    width = paramInt;
    height = paramInt;
    icon.setLayoutParams(localLayoutParams);
  }
  
  public void setIconTintList(ColorStateList paramColorStateList)
  {
    iconTint = paramColorStateList;
    paramColorStateList = itemData;
    if (paramColorStateList != null) {
      setIcon(paramColorStateList.getIcon());
    }
  }
  
  public void setItemBackground(int paramInt)
  {
    Drawable localDrawable;
    if (paramInt == 0) {
      localDrawable = null;
    } else {
      localDrawable = ContextCompat.getDrawable(getContext(), paramInt);
    }
    setItemBackground(localDrawable);
  }
  
  public void setItemBackground(Drawable paramDrawable)
  {
    ViewCompat.setBackground(this, paramDrawable);
  }
  
  public void setItemPosition(int paramInt)
  {
    itemPosition = paramInt;
  }
  
  public void setLabelVisibilityMode(int paramInt)
  {
    if (labelVisibilityMode != paramInt)
    {
      labelVisibilityMode = paramInt;
      if (itemData != null) {
        paramInt = 1;
      } else {
        paramInt = 0;
      }
      if (paramInt != 0) {
        setChecked(itemData.isChecked());
      }
    }
  }
  
  public void setShifting(boolean paramBoolean)
  {
    if (isShifting != paramBoolean)
    {
      isShifting = paramBoolean;
      int i;
      if (itemData != null) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0) {
        setChecked(itemData.isChecked());
      }
    }
  }
  
  public void setShortcut(boolean paramBoolean, char paramChar) {}
  
  public void setTextAppearanceActive(int paramInt)
  {
    TextViewCompat.setTextAppearance(largeLabel, paramInt);
    calculateTextScaleFactors(smallLabel.getTextSize(), largeLabel.getTextSize());
  }
  
  public void setTextAppearanceInactive(int paramInt)
  {
    TextViewCompat.setTextAppearance(smallLabel, paramInt);
    calculateTextScaleFactors(smallLabel.getTextSize(), largeLabel.getTextSize());
  }
  
  public void setTextColor(ColorStateList paramColorStateList)
  {
    if (paramColorStateList != null)
    {
      smallLabel.setTextColor(paramColorStateList);
      largeLabel.setTextColor(paramColorStateList);
    }
  }
  
  public void setTitle(CharSequence paramCharSequence)
  {
    smallLabel.setText(paramCharSequence);
    largeLabel.setText(paramCharSequence);
    MenuItemImpl localMenuItemImpl = itemData;
    if ((localMenuItemImpl == null) || (TextUtils.isEmpty(localMenuItemImpl.getContentDescription()))) {
      setContentDescription(paramCharSequence);
    }
  }
  
  public boolean showsIcon()
  {
    return true;
  }
}
