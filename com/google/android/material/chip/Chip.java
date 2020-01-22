package com.google.android.material.chip;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.PointerIcon;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewOutlineProvider;
import android.view.ViewParent;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.core.content.delay.ResourcesCompat.FontCallback;
import androidx.core.text.BidiFormatter;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityActionCompat;
import androidx.customview.widget.ExploreByTouchHelper;
import com.google.android.material.R.attr;
import com.google.android.material.R.string;
import com.google.android.material.R.style;
import com.google.android.material.animation.MotionSpec;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.TextAppearance;
import com.google.android.material.ripple.RippleUtils;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class Chip
  extends AppCompatCheckBox
  implements ChipDrawable.Delegate
{
  private static final int CLOSE_ICON_VIRTUAL_ID = 0;
  private static final Rect EMPTY_BOUNDS = new Rect();
  private static final String NAMESPACE_ANDROID = "http://schemas.android.com/apk/res/android";
  private static final String PAGE_KEY = "Chip";
  private static final int[] SELECTED_STATE = { 16842913 };
  private ChipDrawable chipDrawable;
  private boolean closeIconFocused;
  private boolean closeIconHovered;
  private boolean closeIconPressed;
  private boolean deferredCheckedValue;
  private int focusedVirtualView = Integer.MIN_VALUE;
  private final ResourcesCompat.FontCallback fontCallback = new ResourcesCompat.FontCallback()
  {
    public void onFontRetrievalFailed(int paramAnonymousInt) {}
    
    public void onFontRetrieved(Typeface paramAnonymousTypeface)
    {
      paramAnonymousTypeface = Chip.this;
      paramAnonymousTypeface.setText(paramAnonymousTypeface.getText());
      requestLayout();
      invalidate();
    }
  };
  private CompoundButton.OnCheckedChangeListener onCheckedChangeListenerInternal;
  private View.OnClickListener onCloseIconClickListener;
  private final Rect rect = new Rect();
  private final RectF rectF = new RectF();
  private RippleDrawable ripple;
  private final ChipTouchHelper touchHelper;
  
  public Chip(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public Chip(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, R.attr.chipStyle);
  }
  
  public Chip(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    validateAttributes(paramAttributeSet);
    paramContext = ChipDrawable.createFromAttributes(paramContext, paramAttributeSet, paramInt, R.style.Widget_MaterialComponents_Chip_Action);
    setChipDrawable(paramContext);
    touchHelper = new ChipTouchHelper(this);
    ViewCompat.setAccessibilityDelegate(this, touchHelper);
    initOutlineProvider();
    setChecked(deferredCheckedValue);
    paramContext.setShouldDrawText(false);
    setText(paramContext.getText());
    setEllipsize(paramContext.getEllipsize());
    setIncludeFontPadding(false);
    if (getTextAppearance() != null) {
      updateTextPaintDrawState(getTextAppearance());
    }
    setSingleLine();
    setGravity(8388627);
    updatePaddingInternal();
  }
  
  private void applyChipDrawable(ChipDrawable paramChipDrawable)
  {
    paramChipDrawable.setDelegate(this);
  }
  
  private float calculateTextOffsetFromStart(ChipDrawable paramChipDrawable)
  {
    float f = getChipStartPadding() + paramChipDrawable.calculateChipIconWidth() + getTextStartPadding();
    if (ViewCompat.getLayoutDirection(this) == 0) {
      return f;
    }
    return -f;
  }
  
  private int[] createCloseIconDrawableState()
  {
    boolean bool = isEnabled();
    int k = 0;
    if (bool) {
      j = 1;
    } else {
      j = 0;
    }
    int i = j;
    if (closeIconFocused) {
      i = j + 1;
    }
    int j = i;
    if (closeIconHovered) {
      j = i + 1;
    }
    i = j;
    if (closeIconPressed) {
      i = j + 1;
    }
    j = i;
    if (isChecked()) {
      j = i + 1;
    }
    int[] arrayOfInt = new int[j];
    j = k;
    if (isEnabled())
    {
      arrayOfInt[0] = 16842910;
      j = 1;
    }
    i = j;
    if (closeIconFocused)
    {
      arrayOfInt[j] = 16842908;
      i = j + 1;
    }
    j = i;
    if (closeIconHovered)
    {
      arrayOfInt[i] = 16843623;
      j = i + 1;
    }
    i = j;
    if (closeIconPressed)
    {
      arrayOfInt[j] = 16842919;
      i = j + 1;
    }
    if (isChecked()) {
      arrayOfInt[i] = 16842913;
    }
    return arrayOfInt;
  }
  
  private void ensureFocus()
  {
    if (focusedVirtualView == Integer.MIN_VALUE) {
      setFocusedVirtualView(-1);
    }
  }
  
  private RectF getCloseIconTouchBounds()
  {
    rectF.setEmpty();
    if (hasCloseIcon()) {
      chipDrawable.getCloseIconTouchBounds(rectF);
    }
    return rectF;
  }
  
  private Rect getCloseIconTouchBoundsInt()
  {
    RectF localRectF = getCloseIconTouchBounds();
    rect.set((int)left, (int)top, (int)right, (int)bottom);
    return rect;
  }
  
  private TextAppearance getTextAppearance()
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      return localChipDrawable.getTextAppearance();
    }
    return null;
  }
  
  private boolean handleAccessibilityExit(MotionEvent paramMotionEvent)
  {
    if (paramMotionEvent.getAction() == 10) {
      try
      {
        paramMotionEvent = ExploreByTouchHelper.class.getDeclaredField("mHoveredVirtualViewId");
        paramMotionEvent.setAccessible(true);
        ChipTouchHelper localChipTouchHelper = touchHelper;
        paramMotionEvent = paramMotionEvent.get(localChipTouchHelper);
        paramMotionEvent = (Integer)paramMotionEvent;
        int i = paramMotionEvent.intValue();
        if (i != Integer.MIN_VALUE)
        {
          paramMotionEvent = Integer.TYPE;
          paramMotionEvent = ExploreByTouchHelper.class.getDeclaredMethod("updateHoveredVirtualView", new Class[] { paramMotionEvent });
          paramMotionEvent.setAccessible(true);
          localChipTouchHelper = touchHelper;
          paramMotionEvent.invoke(localChipTouchHelper, new Object[] { Integer.valueOf(Integer.MIN_VALUE) });
          return true;
        }
      }
      catch (NoSuchFieldException paramMotionEvent)
      {
        Log.e("Chip", "Unable to send Accessibility Exit event", paramMotionEvent);
        return false;
      }
      catch (InvocationTargetException paramMotionEvent)
      {
        Log.e("Chip", "Unable to send Accessibility Exit event", paramMotionEvent);
        return false;
      }
      catch (IllegalAccessException paramMotionEvent)
      {
        Log.e("Chip", "Unable to send Accessibility Exit event", paramMotionEvent);
        return false;
      }
      catch (NoSuchMethodException paramMotionEvent)
      {
        Log.e("Chip", "Unable to send Accessibility Exit event", paramMotionEvent);
      }
    }
    return false;
  }
  
  private boolean hasCloseIcon()
  {
    ChipDrawable localChipDrawable = chipDrawable;
    return (localChipDrawable != null) && (localChipDrawable.getCloseIcon() != null);
  }
  
  private void initOutlineProvider()
  {
    if (Build.VERSION.SDK_INT >= 21) {
      setOutlineProvider(new ViewOutlineProvider()
      {
        public void getOutline(View paramAnonymousView, Outline paramAnonymousOutline)
        {
          if (chipDrawable != null)
          {
            chipDrawable.getOutline(paramAnonymousOutline);
            return;
          }
          paramAnonymousOutline.setAlpha(0.0F);
        }
      });
    }
  }
  
  private boolean moveFocus(boolean paramBoolean)
  {
    ensureFocus();
    if (paramBoolean)
    {
      if (focusedVirtualView == -1)
      {
        setFocusedVirtualView(0);
        return true;
      }
    }
    else if (focusedVirtualView == 0)
    {
      setFocusedVirtualView(-1);
      return true;
    }
    return false;
  }
  
  private void setCloseIconFocused(boolean paramBoolean)
  {
    if (closeIconFocused != paramBoolean)
    {
      closeIconFocused = paramBoolean;
      refreshDrawableState();
    }
  }
  
  private void setCloseIconHovered(boolean paramBoolean)
  {
    if (closeIconHovered != paramBoolean)
    {
      closeIconHovered = paramBoolean;
      refreshDrawableState();
    }
  }
  
  private void setCloseIconPressed(boolean paramBoolean)
  {
    if (closeIconPressed != paramBoolean)
    {
      closeIconPressed = paramBoolean;
      refreshDrawableState();
    }
  }
  
  private void setFocusedVirtualView(int paramInt)
  {
    int i = focusedVirtualView;
    if (i != paramInt)
    {
      if (i == 0) {
        setCloseIconFocused(false);
      }
      focusedVirtualView = paramInt;
      if (paramInt == 0) {
        setCloseIconFocused(true);
      }
    }
  }
  
  private void unapplyChipDrawable(ChipDrawable paramChipDrawable)
  {
    if (paramChipDrawable != null) {
      paramChipDrawable.setDelegate(null);
    }
  }
  
  private void updatePaddingInternal()
  {
    if (!TextUtils.isEmpty(getText()))
    {
      ChipDrawable localChipDrawable = chipDrawable;
      if (localChipDrawable == null) {
        return;
      }
      float f2 = localChipDrawable.getChipStartPadding() + chipDrawable.getChipEndPadding() + chipDrawable.getTextStartPadding() + chipDrawable.getTextEndPadding();
      float f1;
      if ((!chipDrawable.isChipIconVisible()) || (chipDrawable.getChipIcon() == null))
      {
        f1 = f2;
        if (chipDrawable.getCheckedIcon() != null)
        {
          f1 = f2;
          if (chipDrawable.isCheckedIconVisible())
          {
            f1 = f2;
            if (!isChecked()) {}
          }
        }
      }
      else
      {
        f1 = f2 + (chipDrawable.getIconStartPadding() + chipDrawable.getIconEndPadding() + chipDrawable.getChipIconSize());
      }
      f2 = f1;
      if (chipDrawable.isCloseIconVisible())
      {
        f2 = f1;
        if (chipDrawable.getCloseIcon() != null) {
          f2 = f1 + (chipDrawable.getCloseIconStartPadding() + chipDrawable.getCloseIconEndPadding() + chipDrawable.getCloseIconSize());
        }
      }
      if (ViewCompat.getPaddingEnd(this) != f2) {
        ViewCompat.setPaddingRelative(this, ViewCompat.getPaddingStart(this), getPaddingTop(), (int)f2, getPaddingBottom());
      }
    }
  }
  
  private void updateTextPaintDrawState(TextAppearance paramTextAppearance)
  {
    TextPaint localTextPaint = getPaint();
    drawableState = chipDrawable.getState();
    paramTextAppearance.updateDrawState(getContext(), localTextPaint, fontCallback);
  }
  
  private void validateAttributes(AttributeSet paramAttributeSet)
  {
    if (paramAttributeSet == null) {
      return;
    }
    if (paramAttributeSet.getAttributeValue("http://schemas.android.com/apk/res/android", "background") == null)
    {
      if (paramAttributeSet.getAttributeValue("http://schemas.android.com/apk/res/android", "drawableLeft") == null)
      {
        if (paramAttributeSet.getAttributeValue("http://schemas.android.com/apk/res/android", "drawableStart") == null)
        {
          if (paramAttributeSet.getAttributeValue("http://schemas.android.com/apk/res/android", "drawableEnd") == null)
          {
            if (paramAttributeSet.getAttributeValue("http://schemas.android.com/apk/res/android", "drawableRight") == null)
            {
              if ((paramAttributeSet.getAttributeBooleanValue("http://schemas.android.com/apk/res/android", "singleLine", true)) && (paramAttributeSet.getAttributeIntValue("http://schemas.android.com/apk/res/android", "lines", 1) == 1) && (paramAttributeSet.getAttributeIntValue("http://schemas.android.com/apk/res/android", "minLines", 1) == 1) && (paramAttributeSet.getAttributeIntValue("http://schemas.android.com/apk/res/android", "maxLines", 1) == 1))
              {
                if (paramAttributeSet.getAttributeIntValue("http://schemas.android.com/apk/res/android", "gravity", 8388627) != 8388627) {
                  Log.w("Chip", "Chip text must be vertically center and start aligned");
                }
              }
              else {
                throw new UnsupportedOperationException("Chip does not support multi-line text");
              }
            }
            else {
              throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
            }
          }
          else {
            throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
          }
        }
        else {
          throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
        }
      }
      else {
        throw new UnsupportedOperationException("Please set left drawable using R.attr#chipIcon.");
      }
    }
    else {
      throw new UnsupportedOperationException("Do not set the background; Chip manages its own background drawable.");
    }
  }
  
  protected boolean dispatchHoverEvent(MotionEvent paramMotionEvent)
  {
    return (handleAccessibilityExit(paramMotionEvent)) || (touchHelper.dispatchHoverEvent(paramMotionEvent)) || (super.dispatchHoverEvent(paramMotionEvent));
  }
  
  public boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
  {
    return (touchHelper.dispatchKeyEvent(paramKeyEvent)) || (super.dispatchKeyEvent(paramKeyEvent));
  }
  
  protected void drawableStateChanged()
  {
    super.drawableStateChanged();
    ChipDrawable localChipDrawable = chipDrawable;
    boolean bool;
    if ((localChipDrawable != null) && (localChipDrawable.isCloseIconStateful())) {
      bool = chipDrawable.setCloseIconState(createCloseIconDrawableState());
    } else {
      bool = false;
    }
    if (bool) {
      invalidate();
    }
  }
  
  public Drawable getCheckedIcon()
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      return localChipDrawable.getCheckedIcon();
    }
    return null;
  }
  
  public ColorStateList getChipBackgroundColor()
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      return localChipDrawable.getChipBackgroundColor();
    }
    return null;
  }
  
  public float getChipCornerRadius()
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      return localChipDrawable.getChipCornerRadius();
    }
    return 0.0F;
  }
  
  public Drawable getChipDrawable()
  {
    return chipDrawable;
  }
  
  public float getChipEndPadding()
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      return localChipDrawable.getChipEndPadding();
    }
    return 0.0F;
  }
  
  public Drawable getChipIcon()
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      return localChipDrawable.getChipIcon();
    }
    return null;
  }
  
  public float getChipIconSize()
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      return localChipDrawable.getChipIconSize();
    }
    return 0.0F;
  }
  
  public ColorStateList getChipIconTint()
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      return localChipDrawable.getChipIconTint();
    }
    return null;
  }
  
  public float getChipMinHeight()
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      return localChipDrawable.getChipMinHeight();
    }
    return 0.0F;
  }
  
  public float getChipStartPadding()
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      return localChipDrawable.getChipStartPadding();
    }
    return 0.0F;
  }
  
  public ColorStateList getChipStrokeColor()
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      return localChipDrawable.getChipStrokeColor();
    }
    return null;
  }
  
  public float getChipStrokeWidth()
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      return localChipDrawable.getChipStrokeWidth();
    }
    return 0.0F;
  }
  
  public CharSequence getChipText()
  {
    return getText();
  }
  
  public Drawable getCloseIcon()
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      return localChipDrawable.getCloseIcon();
    }
    return null;
  }
  
  public CharSequence getCloseIconContentDescription()
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      return localChipDrawable.getCloseIconContentDescription();
    }
    return null;
  }
  
  public float getCloseIconEndPadding()
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      return localChipDrawable.getCloseIconEndPadding();
    }
    return 0.0F;
  }
  
  public float getCloseIconSize()
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      return localChipDrawable.getCloseIconSize();
    }
    return 0.0F;
  }
  
  public float getCloseIconStartPadding()
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      return localChipDrawable.getCloseIconStartPadding();
    }
    return 0.0F;
  }
  
  public ColorStateList getCloseIconTint()
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      return localChipDrawable.getCloseIconTint();
    }
    return null;
  }
  
  public TextUtils.TruncateAt getEllipsize()
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      return localChipDrawable.getEllipsize();
    }
    return null;
  }
  
  public void getFocusedRect(Rect paramRect)
  {
    if (focusedVirtualView == 0)
    {
      paramRect.set(getCloseIconTouchBoundsInt());
      return;
    }
    super.getFocusedRect(paramRect);
  }
  
  public MotionSpec getHideMotionSpec()
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      return localChipDrawable.getHideMotionSpec();
    }
    return null;
  }
  
  public float getIconEndPadding()
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      return localChipDrawable.getIconEndPadding();
    }
    return 0.0F;
  }
  
  public float getIconStartPadding()
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      return localChipDrawable.getIconStartPadding();
    }
    return 0.0F;
  }
  
  public ColorStateList getRippleColor()
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      return localChipDrawable.getRippleColor();
    }
    return null;
  }
  
  public MotionSpec getShowMotionSpec()
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      return localChipDrawable.getShowMotionSpec();
    }
    return null;
  }
  
  public CharSequence getText()
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      return localChipDrawable.getText();
    }
    return "";
  }
  
  public float getTextEndPadding()
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      return localChipDrawable.getTextEndPadding();
    }
    return 0.0F;
  }
  
  public float getTextStartPadding()
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      return localChipDrawable.getTextStartPadding();
    }
    return 0.0F;
  }
  
  public void invalidateOutline()
  {
    throw new Error("Unresolved compilation error: Method <com.google.android.material.chip.Chip: void invalidateOutline()> does not exist!");
  }
  
  public boolean isCheckable()
  {
    ChipDrawable localChipDrawable = chipDrawable;
    return (localChipDrawable != null) && (localChipDrawable.isCheckable());
  }
  
  public boolean isCheckedIconEnabled()
  {
    return isCheckedIconVisible();
  }
  
  public boolean isCheckedIconVisible()
  {
    ChipDrawable localChipDrawable = chipDrawable;
    return (localChipDrawable != null) && (localChipDrawable.isCheckedIconVisible());
  }
  
  public boolean isChipIconEnabled()
  {
    return isChipIconVisible();
  }
  
  public boolean isChipIconVisible()
  {
    ChipDrawable localChipDrawable = chipDrawable;
    return (localChipDrawable != null) && (localChipDrawable.isChipIconVisible());
  }
  
  public boolean isCloseIconEnabled()
  {
    return isCloseIconVisible();
  }
  
  public boolean isCloseIconVisible()
  {
    ChipDrawable localChipDrawable = chipDrawable;
    return (localChipDrawable != null) && (localChipDrawable.isCloseIconVisible());
  }
  
  public void onChipDrawableSizeChange()
  {
    updatePaddingInternal();
    requestLayout();
    if (Build.VERSION.SDK_INT >= 21) {
      invalidateOutline();
    }
  }
  
  protected int[] onCreateDrawableState(int paramInt)
  {
    int[] arrayOfInt = super.onCreateDrawableState(paramInt + 1);
    if (isChecked()) {
      View.mergeDrawableStates(arrayOfInt, SELECTED_STATE);
    }
    return arrayOfInt;
  }
  
  protected void onDraw(Canvas paramCanvas)
  {
    if (!TextUtils.isEmpty(getText()))
    {
      ChipDrawable localChipDrawable = chipDrawable;
      if ((localChipDrawable != null) && (!localChipDrawable.shouldDrawText()))
      {
        int i = paramCanvas.save();
        paramCanvas.translate(calculateTextOffsetFromStart(chipDrawable), 0.0F);
        super.onDraw(paramCanvas);
        paramCanvas.restoreToCount(i);
        return;
      }
    }
    super.onDraw(paramCanvas);
  }
  
  protected void onFocusChanged(boolean paramBoolean, int paramInt, Rect paramRect)
  {
    if (paramBoolean) {
      setFocusedVirtualView(-1);
    } else {
      setFocusedVirtualView(Integer.MIN_VALUE);
    }
    invalidate();
    super.onFocusChanged(paramBoolean, paramInt, paramRect);
    touchHelper.onFocusChanged(paramBoolean, paramInt, paramRect);
  }
  
  public boolean onHoverEvent(MotionEvent paramMotionEvent)
  {
    int i = paramMotionEvent.getActionMasked();
    if (i != 7)
    {
      if (i == 10) {
        setCloseIconHovered(false);
      }
    }
    else {
      setCloseIconHovered(getCloseIconTouchBounds().contains(paramMotionEvent.getX(), paramMotionEvent.getY()));
    }
    return super.onHoverEvent(paramMotionEvent);
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    int i = paramKeyEvent.getKeyCode();
    boolean bool2 = false;
    boolean bool1;
    if (i != 61)
    {
      if (i != 66)
      {
        switch (i)
        {
        default: 
          bool1 = bool2;
          break;
        case 22: 
          bool1 = bool2;
          if (!paramKeyEvent.hasNoModifiers()) {
            break;
          }
          bool1 = moveFocus(ViewUtils.isLayoutRtl(this) ^ true);
          break;
        case 21: 
          bool1 = bool2;
          if (!paramKeyEvent.hasNoModifiers()) {
            break;
          }
          bool1 = moveFocus(ViewUtils.isLayoutRtl(this));
          break;
        }
      }
      else
      {
        i = focusedVirtualView;
        if (i != -1)
        {
          if (i != 0)
          {
            bool1 = bool2;
          }
          else
          {
            performCloseIconClick();
            return true;
          }
        }
        else
        {
          performClick();
          return true;
        }
      }
    }
    else
    {
      if (paramKeyEvent.hasNoModifiers()) {
        i = 2;
      } else if (paramKeyEvent.hasModifiers(1)) {
        i = 1;
      } else {
        i = 0;
      }
      bool1 = bool2;
      if (i != 0)
      {
        ViewParent localViewParent = getParent();
        Object localObject = this;
        View localView;
        do
        {
          localView = ((View)localObject).focusSearch(i);
          localObject = localView;
        } while ((localView != null) && (localView != this) && (localView.getParent() == localViewParent));
        bool1 = bool2;
        if (localView != null)
        {
          localView.requestFocus();
          return true;
        }
      }
    }
    if (bool1)
    {
      invalidate();
      return true;
    }
    return super.onKeyDown(paramInt, paramKeyEvent);
  }
  
  public PointerIcon onResolvePointerIcon(MotionEvent paramMotionEvent, int paramInt)
  {
    if ((getCloseIconTouchBounds().contains(paramMotionEvent.getX(), paramMotionEvent.getY())) && (isEnabled())) {
      return PointerIcon.getSystemIcon(getContext(), 1002);
    }
    return null;
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    int i = paramMotionEvent.getActionMasked();
    boolean bool = getCloseIconTouchBounds().contains(paramMotionEvent.getX(), paramMotionEvent.getY());
    if (i != 0)
    {
      if (i != 1)
      {
        if (i != 2)
        {
          if (i != 3) {
            break label103;
          }
        }
        else
        {
          if (!closeIconPressed) {
            break label103;
          }
          if (bool) {
            break label98;
          }
          setCloseIconPressed(false);
          break label98;
        }
      }
      else if (closeIconPressed)
      {
        performCloseIconClick();
        i = 1;
        break label81;
      }
      i = 0;
      label81:
      setCloseIconPressed(false);
      break label105;
    }
    else
    {
      if (!bool) {
        break label103;
      }
      setCloseIconPressed(true);
    }
    label98:
    i = 1;
    break label105;
    label103:
    i = 0;
    label105:
    return (i != 0) || (super.onTouchEvent(paramMotionEvent));
  }
  
  public boolean performCloseIconClick()
  {
    playSoundEffect(0);
    View.OnClickListener localOnClickListener = onCloseIconClickListener;
    boolean bool;
    if (localOnClickListener != null)
    {
      localOnClickListener.onClick(this);
      bool = true;
    }
    else
    {
      bool = false;
    }
    touchHelper.sendEventForVirtualView(0, 1);
    return bool;
  }
  
  public void setBackground(Drawable paramDrawable)
  {
    if ((paramDrawable != chipDrawable) && (paramDrawable != ripple)) {
      throw new UnsupportedOperationException("Do not set the background; Chip manages its own background drawable.");
    }
    super.setBackground(paramDrawable);
  }
  
  public void setBackgroundColor(int paramInt)
  {
    throw new UnsupportedOperationException("Do not set the background color; Chip manages its own background drawable.");
  }
  
  public void setBackgroundDrawable(Drawable paramDrawable)
  {
    if ((paramDrawable != chipDrawable) && (paramDrawable != ripple)) {
      throw new UnsupportedOperationException("Do not set the background drawable; Chip manages its own background drawable.");
    }
    super.setBackgroundDrawable(paramDrawable);
  }
  
  public void setBackgroundResource(int paramInt)
  {
    throw new UnsupportedOperationException("Do not set the background resource; Chip manages its own background drawable.");
  }
  
  public void setBackgroundTintList(ColorStateList paramColorStateList)
  {
    throw new UnsupportedOperationException("Do not set the background tint list; Chip manages its own background drawable.");
  }
  
  public void setBackgroundTintMode(PorterDuff.Mode paramMode)
  {
    throw new UnsupportedOperationException("Do not set the background tint mode; Chip manages its own background drawable.");
  }
  
  public void setCheckable(boolean paramBoolean)
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      localChipDrawable.setCheckable(paramBoolean);
    }
  }
  
  public void setCheckableResource(int paramInt)
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      localChipDrawable.setCheckableResource(paramInt);
    }
  }
  
  public void setChecked(boolean paramBoolean)
  {
    Object localObject = chipDrawable;
    if (localObject == null)
    {
      deferredCheckedValue = paramBoolean;
      return;
    }
    if (((ChipDrawable)localObject).isCheckable())
    {
      boolean bool = isChecked();
      super.setChecked(paramBoolean);
      if (bool != paramBoolean)
      {
        localObject = onCheckedChangeListenerInternal;
        if (localObject != null) {
          ((CompoundButton.OnCheckedChangeListener)localObject).onCheckedChanged(this, paramBoolean);
        }
      }
    }
  }
  
  public void setCheckedIcon(Drawable paramDrawable)
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      localChipDrawable.setCheckedIcon(paramDrawable);
    }
  }
  
  public void setCheckedIconEnabled(boolean paramBoolean)
  {
    setCheckedIconVisible(paramBoolean);
  }
  
  public void setCheckedIconEnabledResource(int paramInt)
  {
    setCheckedIconVisible(paramInt);
  }
  
  public void setCheckedIconResource(int paramInt)
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      localChipDrawable.setCheckedIconResource(paramInt);
    }
  }
  
  public void setCheckedIconVisible(int paramInt)
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      localChipDrawable.setCheckedIconVisible(paramInt);
    }
  }
  
  public void setCheckedIconVisible(boolean paramBoolean)
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      localChipDrawable.setCheckedIconVisible(paramBoolean);
    }
  }
  
  public void setChipBackgroundColor(ColorStateList paramColorStateList)
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      localChipDrawable.setChipBackgroundColor(paramColorStateList);
    }
  }
  
  public void setChipBackgroundColorResource(int paramInt)
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      localChipDrawable.setChipBackgroundColorResource(paramInt);
    }
  }
  
  public void setChipCornerRadius(float paramFloat)
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      localChipDrawable.setChipCornerRadius(paramFloat);
    }
  }
  
  public void setChipCornerRadiusResource(int paramInt)
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      localChipDrawable.setChipCornerRadiusResource(paramInt);
    }
  }
  
  public void setChipDrawable(ChipDrawable paramChipDrawable)
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != paramChipDrawable)
    {
      unapplyChipDrawable(localChipDrawable);
      chipDrawable = paramChipDrawable;
      applyChipDrawable(chipDrawable);
      if (RippleUtils.USE_FRAMEWORK_RIPPLE)
      {
        ripple = new RippleDrawable(RippleUtils.convertToRippleDrawableColor(chipDrawable.getRippleColor()), chipDrawable, null);
        chipDrawable.setUseCompatRipple(false);
        ViewCompat.setBackground(this, (Drawable)ripple);
        return;
      }
      chipDrawable.setUseCompatRipple(true);
      ViewCompat.setBackground(this, chipDrawable);
    }
  }
  
  public void setChipEndPadding(float paramFloat)
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      localChipDrawable.setChipEndPadding(paramFloat);
    }
  }
  
  public void setChipEndPaddingResource(int paramInt)
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      localChipDrawable.setChipEndPaddingResource(paramInt);
    }
  }
  
  public void setChipIcon(Drawable paramDrawable)
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      localChipDrawable.setChipIcon(paramDrawable);
    }
  }
  
  public void setChipIconEnabled(boolean paramBoolean)
  {
    setChipIconVisible(paramBoolean);
  }
  
  public void setChipIconEnabledResource(int paramInt)
  {
    setChipIconVisible(paramInt);
  }
  
  public void setChipIconResource(int paramInt)
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      localChipDrawable.setChipIconResource(paramInt);
    }
  }
  
  public void setChipIconSize(float paramFloat)
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      localChipDrawable.setChipIconSize(paramFloat);
    }
  }
  
  public void setChipIconSizeResource(int paramInt)
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      localChipDrawable.setChipIconSizeResource(paramInt);
    }
  }
  
  public void setChipIconTint(ColorStateList paramColorStateList)
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      localChipDrawable.setChipIconTint(paramColorStateList);
    }
  }
  
  public void setChipIconTintResource(int paramInt)
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      localChipDrawable.setChipIconTintResource(paramInt);
    }
  }
  
  public void setChipIconVisible(int paramInt)
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      localChipDrawable.setChipIconVisible(paramInt);
    }
  }
  
  public void setChipIconVisible(boolean paramBoolean)
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      localChipDrawable.setChipIconVisible(paramBoolean);
    }
  }
  
  public void setChipMinHeight(float paramFloat)
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      localChipDrawable.setChipMinHeight(paramFloat);
    }
  }
  
  public void setChipMinHeightResource(int paramInt)
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      localChipDrawable.setChipMinHeightResource(paramInt);
    }
  }
  
  public void setChipStartPadding(float paramFloat)
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      localChipDrawable.setChipStartPadding(paramFloat);
    }
  }
  
  public void setChipStartPaddingResource(int paramInt)
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      localChipDrawable.setChipStartPaddingResource(paramInt);
    }
  }
  
  public void setChipStrokeColor(ColorStateList paramColorStateList)
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      localChipDrawable.setChipStrokeColor(paramColorStateList);
    }
  }
  
  public void setChipStrokeColorResource(int paramInt)
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      localChipDrawable.setChipStrokeColorResource(paramInt);
    }
  }
  
  public void setChipStrokeWidth(float paramFloat)
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      localChipDrawable.setChipStrokeWidth(paramFloat);
    }
  }
  
  public void setChipStrokeWidthResource(int paramInt)
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      localChipDrawable.setChipStrokeWidthResource(paramInt);
    }
  }
  
  public void setChipText(CharSequence paramCharSequence)
  {
    setText(paramCharSequence);
  }
  
  public void setChipTextResource(int paramInt)
  {
    setText(getResources().getString(paramInt));
  }
  
  public void setCloseIcon(Drawable paramDrawable)
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      localChipDrawable.setCloseIcon(paramDrawable);
    }
  }
  
  public void setCloseIconContentDescription(CharSequence paramCharSequence)
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      localChipDrawable.setCloseIconContentDescription(paramCharSequence);
    }
  }
  
  public void setCloseIconEnabled(boolean paramBoolean)
  {
    setCloseIconVisible(paramBoolean);
  }
  
  public void setCloseIconEnabledResource(int paramInt)
  {
    setCloseIconVisible(paramInt);
  }
  
  public void setCloseIconEndPadding(float paramFloat)
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      localChipDrawable.setCloseIconEndPadding(paramFloat);
    }
  }
  
  public void setCloseIconEndPaddingResource(int paramInt)
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      localChipDrawable.setCloseIconEndPaddingResource(paramInt);
    }
  }
  
  public void setCloseIconResource(int paramInt)
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      localChipDrawable.setCloseIconResource(paramInt);
    }
  }
  
  public void setCloseIconSize(float paramFloat)
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      localChipDrawable.setCloseIconSize(paramFloat);
    }
  }
  
  public void setCloseIconSizeResource(int paramInt)
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      localChipDrawable.setCloseIconSizeResource(paramInt);
    }
  }
  
  public void setCloseIconStartPadding(float paramFloat)
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      localChipDrawable.setCloseIconStartPadding(paramFloat);
    }
  }
  
  public void setCloseIconStartPaddingResource(int paramInt)
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      localChipDrawable.setCloseIconStartPaddingResource(paramInt);
    }
  }
  
  public void setCloseIconTint(ColorStateList paramColorStateList)
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      localChipDrawable.setCloseIconTint(paramColorStateList);
    }
  }
  
  public void setCloseIconTintResource(int paramInt)
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      localChipDrawable.setCloseIconTintResource(paramInt);
    }
  }
  
  public void setCloseIconVisible(int paramInt)
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      localChipDrawable.setCloseIconVisible(paramInt);
    }
  }
  
  public void setCloseIconVisible(boolean paramBoolean)
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      localChipDrawable.setCloseIconVisible(paramBoolean);
    }
  }
  
  public void setCompoundDrawables(Drawable paramDrawable1, Drawable paramDrawable2, Drawable paramDrawable3, Drawable paramDrawable4)
  {
    if (paramDrawable1 == null)
    {
      if (paramDrawable3 == null)
      {
        super.setCompoundDrawables(paramDrawable1, paramDrawable2, paramDrawable3, paramDrawable4);
        return;
      }
      throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
    }
    throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
  }
  
  public void setCompoundDrawablesRelative(Drawable paramDrawable1, Drawable paramDrawable2, Drawable paramDrawable3, Drawable paramDrawable4)
  {
    if (paramDrawable1 == null)
    {
      if (paramDrawable3 == null)
      {
        super.setCompoundDrawablesRelative(paramDrawable1, paramDrawable2, paramDrawable3, paramDrawable4);
        return;
      }
      throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
    }
    throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
  }
  
  public void setCompoundDrawablesRelativeWithIntrinsicBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (paramInt1 == 0)
    {
      if (paramInt3 == 0)
      {
        super.setCompoundDrawablesRelativeWithIntrinsicBounds(paramInt1, paramInt2, paramInt3, paramInt4);
        return;
      }
      throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
    }
    throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
  }
  
  public void setCompoundDrawablesRelativeWithIntrinsicBounds(Drawable paramDrawable1, Drawable paramDrawable2, Drawable paramDrawable3, Drawable paramDrawable4)
  {
    if (paramDrawable1 == null)
    {
      if (paramDrawable3 == null)
      {
        super.setCompoundDrawablesRelativeWithIntrinsicBounds(paramDrawable1, paramDrawable2, paramDrawable3, paramDrawable4);
        return;
      }
      throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
    }
    throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
  }
  
  public void setCompoundDrawablesWithIntrinsicBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (paramInt1 == 0)
    {
      if (paramInt3 == 0)
      {
        super.setCompoundDrawablesWithIntrinsicBounds(paramInt1, paramInt2, paramInt3, paramInt4);
        return;
      }
      throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
    }
    throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
  }
  
  public void setCompoundDrawablesWithIntrinsicBounds(Drawable paramDrawable1, Drawable paramDrawable2, Drawable paramDrawable3, Drawable paramDrawable4)
  {
    if (paramDrawable1 == null)
    {
      if (paramDrawable3 == null)
      {
        super.setCompoundDrawablesWithIntrinsicBounds(paramDrawable1, paramDrawable2, paramDrawable3, paramDrawable4);
        return;
      }
      throw new UnsupportedOperationException("Please set right drawable using R.attr#closeIcon.");
    }
    throw new UnsupportedOperationException("Please set left drawable using R.attr#chipIcon.");
  }
  
  public void setEllipsize(TextUtils.TruncateAt paramTruncateAt)
  {
    if (chipDrawable == null) {
      return;
    }
    if (paramTruncateAt != TextUtils.TruncateAt.MARQUEE)
    {
      super.setEllipsize(paramTruncateAt);
      ChipDrawable localChipDrawable = chipDrawable;
      if (localChipDrawable != null) {
        localChipDrawable.setEllipsize(paramTruncateAt);
      }
    }
    else
    {
      throw new UnsupportedOperationException("Text within a chip are not allowed to scroll.");
    }
  }
  
  public void setGravity(int paramInt)
  {
    if (paramInt != 8388627)
    {
      Log.w("Chip", "Chip text must be vertically center and start aligned");
      return;
    }
    super.setGravity(paramInt);
  }
  
  public void setHideMotionSpec(MotionSpec paramMotionSpec)
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      localChipDrawable.setHideMotionSpec(paramMotionSpec);
    }
  }
  
  public void setHideMotionSpecResource(int paramInt)
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      localChipDrawable.setHideMotionSpecResource(paramInt);
    }
  }
  
  public void setIconEndPadding(float paramFloat)
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      localChipDrawable.setIconEndPadding(paramFloat);
    }
  }
  
  public void setIconEndPaddingResource(int paramInt)
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      localChipDrawable.setIconEndPaddingResource(paramInt);
    }
  }
  
  public void setIconStartPadding(float paramFloat)
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      localChipDrawable.setIconStartPadding(paramFloat);
    }
  }
  
  public void setIconStartPaddingResource(int paramInt)
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      localChipDrawable.setIconStartPaddingResource(paramInt);
    }
  }
  
  public void setLines(int paramInt)
  {
    if (paramInt <= 1)
    {
      super.setLines(paramInt);
      return;
    }
    throw new UnsupportedOperationException("Chip does not support multi-line text");
  }
  
  public void setMaxLines(int paramInt)
  {
    if (paramInt <= 1)
    {
      super.setMaxLines(paramInt);
      return;
    }
    throw new UnsupportedOperationException("Chip does not support multi-line text");
  }
  
  public void setMaxWidth(int paramInt)
  {
    super.setMaxWidth(paramInt);
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      localChipDrawable.setMaxWidth(paramInt);
    }
  }
  
  public void setMinLines(int paramInt)
  {
    if (paramInt <= 1)
    {
      super.setMinLines(paramInt);
      return;
    }
    throw new UnsupportedOperationException("Chip does not support multi-line text");
  }
  
  void setOnCheckedChangeListenerInternal(CompoundButton.OnCheckedChangeListener paramOnCheckedChangeListener)
  {
    onCheckedChangeListenerInternal = paramOnCheckedChangeListener;
  }
  
  public void setOnCloseIconClickListener(View.OnClickListener paramOnClickListener)
  {
    onCloseIconClickListener = paramOnClickListener;
  }
  
  public void setOutlineProvider(ViewOutlineProvider paramViewOutlineProvider)
  {
    throw new Error("Unresolved compilation error: Method <com.google.android.material.chip.Chip: void setOutlineProvider(android.view.ViewOutlineProvider)> does not exist!");
  }
  
  public void setRippleColor(ColorStateList paramColorStateList)
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      localChipDrawable.setRippleColor(paramColorStateList);
    }
  }
  
  public void setRippleColorResource(int paramInt)
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      localChipDrawable.setRippleColorResource(paramInt);
    }
  }
  
  public void setShowMotionSpec(MotionSpec paramMotionSpec)
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      localChipDrawable.setShowMotionSpec(paramMotionSpec);
    }
  }
  
  public void setShowMotionSpecResource(int paramInt)
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      localChipDrawable.setShowMotionSpecResource(paramInt);
    }
  }
  
  public void setSingleLine(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      super.setSingleLine(paramBoolean);
      return;
    }
    throw new UnsupportedOperationException("Chip does not support multi-line text");
  }
  
  public void setText(CharSequence paramCharSequence, TextView.BufferType paramBufferType)
  {
    if (chipDrawable == null) {
      return;
    }
    Object localObject = paramCharSequence;
    if (paramCharSequence == null) {
      localObject = "";
    }
    paramCharSequence = BidiFormatter.getInstance().unicodeWrap((CharSequence)localObject);
    if (chipDrawable.shouldDrawText()) {
      paramCharSequence = null;
    }
    super.setText(paramCharSequence, paramBufferType);
    paramCharSequence = chipDrawable;
    if (paramCharSequence != null) {
      paramCharSequence.setText((CharSequence)localObject);
    }
  }
  
  public void setTextAppearance(int paramInt)
  {
    super.setTextAppearance(paramInt);
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      localChipDrawable.setTextAppearanceResource(paramInt);
    }
    if (getTextAppearance() != null)
    {
      getTextAppearance().updateMeasureState(getContext(), getPaint(), fontCallback);
      updateTextPaintDrawState(getTextAppearance());
    }
  }
  
  public void setTextAppearance(Context paramContext, int paramInt)
  {
    super.setTextAppearance(paramContext, paramInt);
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      localChipDrawable.setTextAppearanceResource(paramInt);
    }
    if (getTextAppearance() != null)
    {
      getTextAppearance().updateMeasureState(paramContext, getPaint(), fontCallback);
      updateTextPaintDrawState(getTextAppearance());
    }
  }
  
  public void setTextAppearance(TextAppearance paramTextAppearance)
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      localChipDrawable.setTextAppearance(paramTextAppearance);
    }
    if (getTextAppearance() != null)
    {
      getTextAppearance().updateMeasureState(getContext(), getPaint(), fontCallback);
      updateTextPaintDrawState(paramTextAppearance);
    }
  }
  
  public void setTextAppearanceResource(int paramInt)
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      localChipDrawable.setTextAppearanceResource(paramInt);
    }
    setTextAppearance(getContext(), paramInt);
  }
  
  public void setTextEndPadding(float paramFloat)
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      localChipDrawable.setTextEndPadding(paramFloat);
    }
  }
  
  public void setTextEndPaddingResource(int paramInt)
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      localChipDrawable.setTextEndPaddingResource(paramInt);
    }
  }
  
  public void setTextStartPadding(float paramFloat)
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      localChipDrawable.setTextStartPadding(paramFloat);
    }
  }
  
  public void setTextStartPaddingResource(int paramInt)
  {
    ChipDrawable localChipDrawable = chipDrawable;
    if (localChipDrawable != null) {
      localChipDrawable.setTextStartPaddingResource(paramInt);
    }
  }
  
  private class ChipTouchHelper
    extends ExploreByTouchHelper
  {
    ChipTouchHelper(Chip paramChip)
    {
      super();
    }
    
    protected int getVirtualViewAt(float paramFloat1, float paramFloat2)
    {
      if ((Chip.this.hasCloseIcon()) && (Chip.this.getCloseIconTouchBounds().contains(paramFloat1, paramFloat2))) {
        return 0;
      }
      return -1;
    }
    
    protected void getVisibleVirtualViews(List paramList)
    {
      if (Chip.this.hasCloseIcon()) {
        paramList.add(Integer.valueOf(0));
      }
    }
    
    protected boolean onPerformActionForVirtualView(int paramInt1, int paramInt2, Bundle paramBundle)
    {
      if ((paramInt2 == 16) && (paramInt1 == 0)) {
        return performCloseIconClick();
      }
      return false;
    }
    
    protected void onPopulateNodeForHost(AccessibilityNodeInfoCompat paramAccessibilityNodeInfoCompat)
    {
      boolean bool;
      if ((chipDrawable != null) && (chipDrawable.isCheckable())) {
        bool = true;
      } else {
        bool = false;
      }
      paramAccessibilityNodeInfoCompat.setCheckable(bool);
      paramAccessibilityNodeInfoCompat.setClassName(Chip.class.getName());
      CharSequence localCharSequence = getText();
      if (Build.VERSION.SDK_INT >= 23)
      {
        paramAccessibilityNodeInfoCompat.setText(localCharSequence);
        return;
      }
      paramAccessibilityNodeInfoCompat.setContentDescription(localCharSequence);
    }
    
    protected void onPopulateNodeForVirtualView(int paramInt, AccessibilityNodeInfoCompat paramAccessibilityNodeInfoCompat)
    {
      if (Chip.this.hasCloseIcon())
      {
        Object localObject = getCloseIconContentDescription();
        if (localObject != null)
        {
          paramAccessibilityNodeInfoCompat.setContentDescription((CharSequence)localObject);
        }
        else
        {
          CharSequence localCharSequence = getText();
          localObject = localCharSequence;
          Context localContext = getContext();
          paramInt = R.string.mtrl_chip_close_icon_content_description;
          if (TextUtils.isEmpty(localCharSequence)) {
            localObject = "";
          }
          paramAccessibilityNodeInfoCompat.setContentDescription(localContext.getString(paramInt, new Object[] { localObject }).trim());
        }
        paramAccessibilityNodeInfoCompat.setBoundsInParent(Chip.this.getCloseIconTouchBoundsInt());
        paramAccessibilityNodeInfoCompat.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK);
        paramAccessibilityNodeInfoCompat.setEnabled(isEnabled());
        return;
      }
      paramAccessibilityNodeInfoCompat.setContentDescription("");
      paramAccessibilityNodeInfoCompat.setBoundsInParent(Chip.EMPTY_BOUNDS);
    }
  }
}
