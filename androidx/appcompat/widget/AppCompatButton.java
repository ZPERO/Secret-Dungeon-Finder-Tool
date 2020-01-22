package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ActionMode.Callback;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityRecord;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.R.attr;
import androidx.core.view.TintableBackgroundView;
import androidx.core.widget.AutoSizeableTextView;
import androidx.core.widget.TextViewCompat;

public class AppCompatButton
  extends Button
  implements TintableBackgroundView, AutoSizeableTextView
{
  private final AppCompatBackgroundHelper mBackgroundTintHelper = new AppCompatBackgroundHelper(this);
  private final AppCompatTextHelper mTextHelper;
  
  public AppCompatButton(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public AppCompatButton(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, R.attr.buttonStyle);
  }
  
  public AppCompatButton(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(TintContextWrapper.wrap(paramContext), paramAttributeSet, paramInt);
    mBackgroundTintHelper.loadFromAttributes(paramAttributeSet, paramInt);
    mTextHelper = new AppCompatTextHelper(this);
    mTextHelper.loadFromAttributes(paramAttributeSet, paramInt);
    mTextHelper.applyCompoundDrawablesTints();
  }
  
  protected void drawableStateChanged()
  {
    super.drawableStateChanged();
    Object localObject = mBackgroundTintHelper;
    if (localObject != null) {
      ((AppCompatBackgroundHelper)localObject).applySupportBackgroundTint();
    }
    localObject = mTextHelper;
    if (localObject != null) {
      ((AppCompatTextHelper)localObject).applyCompoundDrawablesTints();
    }
  }
  
  public int getAutoSizeMaxTextSize()
  {
    if (AutoSizeableTextView.PLATFORM_SUPPORTS_AUTOSIZE) {
      return super.getAutoSizeMaxTextSize();
    }
    AppCompatTextHelper localAppCompatTextHelper = mTextHelper;
    if (localAppCompatTextHelper != null) {
      return localAppCompatTextHelper.getAutoSizeMaxTextSize();
    }
    return -1;
  }
  
  public int getAutoSizeMinTextSize()
  {
    if (AutoSizeableTextView.PLATFORM_SUPPORTS_AUTOSIZE) {
      return super.getAutoSizeMinTextSize();
    }
    AppCompatTextHelper localAppCompatTextHelper = mTextHelper;
    if (localAppCompatTextHelper != null) {
      return localAppCompatTextHelper.getAutoSizeMinTextSize();
    }
    return -1;
  }
  
  public int getAutoSizeStepGranularity()
  {
    if (AutoSizeableTextView.PLATFORM_SUPPORTS_AUTOSIZE) {
      return super.getAutoSizeStepGranularity();
    }
    AppCompatTextHelper localAppCompatTextHelper = mTextHelper;
    if (localAppCompatTextHelper != null) {
      return localAppCompatTextHelper.getAutoSizeStepGranularity();
    }
    return -1;
  }
  
  public int[] getAutoSizeTextAvailableSizes()
  {
    if (AutoSizeableTextView.PLATFORM_SUPPORTS_AUTOSIZE) {
      return super.getAutoSizeTextAvailableSizes();
    }
    AppCompatTextHelper localAppCompatTextHelper = mTextHelper;
    if (localAppCompatTextHelper != null) {
      return localAppCompatTextHelper.getAutoSizeTextAvailableSizes();
    }
    return new int[0];
  }
  
  public int getAutoSizeTextType()
  {
    if (AutoSizeableTextView.PLATFORM_SUPPORTS_AUTOSIZE)
    {
      if (super.getAutoSizeTextType() == 1) {
        return 1;
      }
    }
    else
    {
      AppCompatTextHelper localAppCompatTextHelper = mTextHelper;
      if (localAppCompatTextHelper != null) {
        return localAppCompatTextHelper.getAutoSizeTextType();
      }
    }
    return 0;
  }
  
  public ColorStateList getSupportBackgroundTintList()
  {
    AppCompatBackgroundHelper localAppCompatBackgroundHelper = mBackgroundTintHelper;
    if (localAppCompatBackgroundHelper != null) {
      return localAppCompatBackgroundHelper.getSupportBackgroundTintList();
    }
    return null;
  }
  
  public PorterDuff.Mode getSupportBackgroundTintMode()
  {
    AppCompatBackgroundHelper localAppCompatBackgroundHelper = mBackgroundTintHelper;
    if (localAppCompatBackgroundHelper != null) {
      return localAppCompatBackgroundHelper.getSupportBackgroundTintMode();
    }
    return null;
  }
  
  public void onInitializeAccessibilityEvent(AccessibilityEvent paramAccessibilityEvent)
  {
    super.onInitializeAccessibilityEvent(paramAccessibilityEvent);
    paramAccessibilityEvent.setClassName(Button.class.getName());
  }
  
  public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo paramAccessibilityNodeInfo)
  {
    super.onInitializeAccessibilityNodeInfo(paramAccessibilityNodeInfo);
    paramAccessibilityNodeInfo.setClassName(Button.class.getName());
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    AppCompatTextHelper localAppCompatTextHelper = mTextHelper;
    if (localAppCompatTextHelper != null) {
      localAppCompatTextHelper.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    }
  }
  
  protected void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
  {
    super.onTextChanged(paramCharSequence, paramInt1, paramInt2, paramInt3);
    if ((mTextHelper != null) && (!AutoSizeableTextView.PLATFORM_SUPPORTS_AUTOSIZE) && (mTextHelper.isAutoSizeEnabled())) {
      mTextHelper.autoSizeText();
    }
  }
  
  public void setAutoSizeTextTypeUniformWithConfiguration(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    throws IllegalArgumentException
  {
    if (AutoSizeableTextView.PLATFORM_SUPPORTS_AUTOSIZE)
    {
      super.setAutoSizeTextTypeUniformWithConfiguration(paramInt1, paramInt2, paramInt3, paramInt4);
      return;
    }
    AppCompatTextHelper localAppCompatTextHelper = mTextHelper;
    if (localAppCompatTextHelper != null) {
      localAppCompatTextHelper.setAutoSizeTextTypeUniformWithConfiguration(paramInt1, paramInt2, paramInt3, paramInt4);
    }
  }
  
  public void setAutoSizeTextTypeUniformWithPresetSizes(int[] paramArrayOfInt, int paramInt)
    throws IllegalArgumentException
  {
    if (AutoSizeableTextView.PLATFORM_SUPPORTS_AUTOSIZE)
    {
      super.setAutoSizeTextTypeUniformWithPresetSizes(paramArrayOfInt, paramInt);
      return;
    }
    AppCompatTextHelper localAppCompatTextHelper = mTextHelper;
    if (localAppCompatTextHelper != null) {
      localAppCompatTextHelper.setAutoSizeTextTypeUniformWithPresetSizes(paramArrayOfInt, paramInt);
    }
  }
  
  public void setAutoSizeTextTypeWithDefaults(int paramInt)
  {
    if (AutoSizeableTextView.PLATFORM_SUPPORTS_AUTOSIZE)
    {
      super.setAutoSizeTextTypeWithDefaults(paramInt);
      return;
    }
    AppCompatTextHelper localAppCompatTextHelper = mTextHelper;
    if (localAppCompatTextHelper != null) {
      localAppCompatTextHelper.setAutoSizeTextTypeWithDefaults(paramInt);
    }
  }
  
  public void setBackgroundDrawable(Drawable paramDrawable)
  {
    super.setBackgroundDrawable(paramDrawable);
    AppCompatBackgroundHelper localAppCompatBackgroundHelper = mBackgroundTintHelper;
    if (localAppCompatBackgroundHelper != null) {
      localAppCompatBackgroundHelper.onSetBackgroundDrawable(paramDrawable);
    }
  }
  
  public void setBackgroundResource(int paramInt)
  {
    super.setBackgroundResource(paramInt);
    AppCompatBackgroundHelper localAppCompatBackgroundHelper = mBackgroundTintHelper;
    if (localAppCompatBackgroundHelper != null) {
      localAppCompatBackgroundHelper.onSetBackgroundResource(paramInt);
    }
  }
  
  public void setCustomSelectionActionModeCallback(ActionMode.Callback paramCallback)
  {
    super.setCustomSelectionActionModeCallback(TextViewCompat.wrapCustomSelectionActionModeCallback(this, paramCallback));
  }
  
  public void setSupportAllCaps(boolean paramBoolean)
  {
    AppCompatTextHelper localAppCompatTextHelper = mTextHelper;
    if (localAppCompatTextHelper != null) {
      localAppCompatTextHelper.setAllCaps(paramBoolean);
    }
  }
  
  public void setSupportBackgroundTintList(ColorStateList paramColorStateList)
  {
    AppCompatBackgroundHelper localAppCompatBackgroundHelper = mBackgroundTintHelper;
    if (localAppCompatBackgroundHelper != null) {
      localAppCompatBackgroundHelper.setSupportBackgroundTintList(paramColorStateList);
    }
  }
  
  public void setSupportBackgroundTintMode(PorterDuff.Mode paramMode)
  {
    AppCompatBackgroundHelper localAppCompatBackgroundHelper = mBackgroundTintHelper;
    if (localAppCompatBackgroundHelper != null) {
      localAppCompatBackgroundHelper.setSupportBackgroundTintMode(paramMode);
    }
  }
  
  public void setTextAppearance(Context paramContext, int paramInt)
  {
    super.setTextAppearance(paramContext, paramInt);
    AppCompatTextHelper localAppCompatTextHelper = mTextHelper;
    if (localAppCompatTextHelper != null) {
      localAppCompatTextHelper.onSetTextAppearance(paramContext, paramInt);
    }
  }
  
  public void setTextSize(int paramInt, float paramFloat)
  {
    if (AutoSizeableTextView.PLATFORM_SUPPORTS_AUTOSIZE)
    {
      super.setTextSize(paramInt, paramFloat);
      return;
    }
    AppCompatTextHelper localAppCompatTextHelper = mTextHelper;
    if (localAppCompatTextHelper != null) {
      localAppCompatTextHelper.setTextSize(paramInt, paramFloat);
    }
  }
}
