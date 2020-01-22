package com.google.android.material.button;

import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build.VERSION;
import android.view.View;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;
import com.google.android.material.R.styleable;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.ripple.RippleUtils;

class MaterialButtonHelper
{
  private static final float CORNER_RADIUS_ADJUSTMENT = 1.0E-5F;
  private static final int DEFAULT_BACKGROUND_COLOR = -1;
  private static final boolean IS_LOLLIPOP;
  private GradientDrawable backgroundDrawableLollipop;
  private boolean backgroundOverwritten = false;
  private ColorStateList backgroundTint;
  private PorterDuff.Mode backgroundTintMode;
  private final Rect bounds = new Rect();
  private final Paint buttonStrokePaint = new Paint(1);
  private GradientDrawable colorableBackgroundDrawableCompat;
  private int cornerRadius;
  private int insetBottom;
  private int insetLeft;
  private int insetRight;
  private int insetTop;
  private GradientDrawable maskDrawableLollipop;
  private final MaterialButton materialButton;
  private final RectF rectF = new RectF();
  private ColorStateList rippleColor;
  private GradientDrawable rippleDrawableCompat;
  private ColorStateList strokeColor;
  private GradientDrawable strokeDrawableLollipop;
  private int strokeWidth;
  private Drawable tintableBackgroundDrawableCompat;
  private Drawable tintableRippleDrawableCompat;
  
  static
  {
    boolean bool;
    if (Build.VERSION.SDK_INT >= 21) {
      bool = true;
    } else {
      bool = false;
    }
    IS_LOLLIPOP = bool;
  }
  
  public MaterialButtonHelper(MaterialButton paramMaterialButton)
  {
    materialButton = paramMaterialButton;
  }
  
  private Drawable createBackgroundCompat()
  {
    colorableBackgroundDrawableCompat = new GradientDrawable();
    colorableBackgroundDrawableCompat.setCornerRadius(cornerRadius + 1.0E-5F);
    colorableBackgroundDrawableCompat.setColor(-1);
    tintableBackgroundDrawableCompat = DrawableCompat.wrap(colorableBackgroundDrawableCompat);
    DrawableCompat.setTintList(tintableBackgroundDrawableCompat, backgroundTint);
    PorterDuff.Mode localMode = backgroundTintMode;
    if (localMode != null) {
      DrawableCompat.setTintMode(tintableBackgroundDrawableCompat, localMode);
    }
    rippleDrawableCompat = new GradientDrawable();
    rippleDrawableCompat.setCornerRadius(cornerRadius + 1.0E-5F);
    rippleDrawableCompat.setColor(-1);
    tintableRippleDrawableCompat = DrawableCompat.wrap(rippleDrawableCompat);
    DrawableCompat.setTintList(tintableRippleDrawableCompat, rippleColor);
    return wrapDrawableWithInset(new LayerDrawable(new Drawable[] { tintableBackgroundDrawableCompat, tintableRippleDrawableCompat }));
  }
  
  private Drawable createBackgroundLollipop()
  {
    backgroundDrawableLollipop = new GradientDrawable();
    backgroundDrawableLollipop.setCornerRadius(cornerRadius + 1.0E-5F);
    backgroundDrawableLollipop.setColor(-1);
    updateTintAndTintModeLollipop();
    strokeDrawableLollipop = new GradientDrawable();
    strokeDrawableLollipop.setCornerRadius(cornerRadius + 1.0E-5F);
    strokeDrawableLollipop.setColor(0);
    strokeDrawableLollipop.setStroke(strokeWidth, strokeColor);
    InsetDrawable localInsetDrawable = wrapDrawableWithInset(new LayerDrawable(new Drawable[] { backgroundDrawableLollipop, strokeDrawableLollipop }));
    maskDrawableLollipop = new GradientDrawable();
    maskDrawableLollipop.setCornerRadius(cornerRadius + 1.0E-5F);
    maskDrawableLollipop.setColor(-1);
    return (Drawable)new MaterialButtonBackgroundDrawable(RippleUtils.convertToRippleDrawableColor(rippleColor), localInsetDrawable, maskDrawableLollipop);
  }
  
  private GradientDrawable unwrapBackgroundDrawable()
  {
    if ((IS_LOLLIPOP) && (materialButton.getBackground() != null)) {
      return (GradientDrawable)((LayerDrawable)((InsetDrawable)((RippleDrawable)materialButton.getBackground()).getDrawable(0)).getDrawable()).getDrawable(0);
    }
    return null;
  }
  
  private GradientDrawable unwrapStrokeDrawable()
  {
    if ((IS_LOLLIPOP) && (materialButton.getBackground() != null)) {
      return (GradientDrawable)((LayerDrawable)((InsetDrawable)((RippleDrawable)materialButton.getBackground()).getDrawable(0)).getDrawable()).getDrawable(1);
    }
    return null;
  }
  
  private void updateStroke()
  {
    if ((IS_LOLLIPOP) && (strokeDrawableLollipop != null))
    {
      materialButton.setInternalBackground(createBackgroundLollipop());
      return;
    }
    if (!IS_LOLLIPOP) {
      materialButton.invalidate();
    }
  }
  
  private void updateTintAndTintModeLollipop()
  {
    Object localObject = backgroundDrawableLollipop;
    if (localObject != null)
    {
      DrawableCompat.setTintList((Drawable)localObject, backgroundTint);
      localObject = backgroundTintMode;
      if (localObject != null) {
        DrawableCompat.setTintMode(backgroundDrawableLollipop, (PorterDuff.Mode)localObject);
      }
    }
  }
  
  private InsetDrawable wrapDrawableWithInset(Drawable paramDrawable)
  {
    return new InsetDrawable(paramDrawable, insetLeft, insetTop, insetRight, insetBottom);
  }
  
  void drawStroke(Canvas paramCanvas)
  {
    if ((paramCanvas != null) && (strokeColor != null) && (strokeWidth > 0))
    {
      bounds.set(materialButton.getBackground().getBounds());
      rectF.set(bounds.left + strokeWidth / 2.0F + insetLeft, bounds.top + strokeWidth / 2.0F + insetTop, bounds.right - strokeWidth / 2.0F - insetRight, bounds.bottom - strokeWidth / 2.0F - insetBottom);
      float f = cornerRadius - strokeWidth / 2.0F;
      paramCanvas.drawRoundRect(rectF, f, f, buttonStrokePaint);
    }
  }
  
  int getCornerRadius()
  {
    return cornerRadius;
  }
  
  ColorStateList getRippleColor()
  {
    return rippleColor;
  }
  
  ColorStateList getStrokeColor()
  {
    return strokeColor;
  }
  
  int getStrokeWidth()
  {
    return strokeWidth;
  }
  
  ColorStateList getSupportBackgroundTintList()
  {
    return backgroundTint;
  }
  
  PorterDuff.Mode getSupportBackgroundTintMode()
  {
    return backgroundTintMode;
  }
  
  boolean isBackgroundOverwritten()
  {
    return backgroundOverwritten;
  }
  
  public void loadFromAttributes(TypedArray paramTypedArray)
  {
    int j = R.styleable.MaterialButton_android_insetLeft;
    int i = 0;
    insetLeft = paramTypedArray.getDimensionPixelOffset(j, 0);
    insetRight = paramTypedArray.getDimensionPixelOffset(R.styleable.MaterialButton_android_insetRight, 0);
    insetTop = paramTypedArray.getDimensionPixelOffset(R.styleable.MaterialButton_android_insetTop, 0);
    insetBottom = paramTypedArray.getDimensionPixelOffset(R.styleable.MaterialButton_android_insetBottom, 0);
    cornerRadius = paramTypedArray.getDimensionPixelSize(R.styleable.MaterialButton_cornerRadius, 0);
    strokeWidth = paramTypedArray.getDimensionPixelSize(R.styleable.MaterialButton_strokeWidth, 0);
    backgroundTintMode = ViewUtils.parseTintMode(paramTypedArray.getInt(R.styleable.MaterialButton_backgroundTintMode, -1), PorterDuff.Mode.SRC_IN);
    backgroundTint = MaterialResources.getColorStateList(materialButton.getContext(), paramTypedArray, R.styleable.MaterialButton_backgroundTint);
    strokeColor = MaterialResources.getColorStateList(materialButton.getContext(), paramTypedArray, R.styleable.MaterialButton_strokeColor);
    rippleColor = MaterialResources.getColorStateList(materialButton.getContext(), paramTypedArray, R.styleable.MaterialButton_rippleColor);
    buttonStrokePaint.setStyle(Paint.Style.STROKE);
    buttonStrokePaint.setStrokeWidth(strokeWidth);
    paramTypedArray = buttonStrokePaint;
    Object localObject = strokeColor;
    if (localObject != null) {
      i = ((ColorStateList)localObject).getColorForState(materialButton.getDrawableState(), 0);
    }
    paramTypedArray.setColor(i);
    i = ViewCompat.getPaddingStart(materialButton);
    j = materialButton.getPaddingTop();
    int k = ViewCompat.getPaddingEnd(materialButton);
    int m = materialButton.getPaddingBottom();
    localObject = materialButton;
    if (IS_LOLLIPOP) {
      paramTypedArray = createBackgroundLollipop();
    } else {
      paramTypedArray = createBackgroundCompat();
    }
    ((MaterialButton)localObject).setInternalBackground(paramTypedArray);
    ViewCompat.setPaddingRelative(materialButton, i + insetLeft, j + insetTop, k + insetRight, m + insetBottom);
  }
  
  void setBackgroundColor(int paramInt)
  {
    GradientDrawable localGradientDrawable;
    if (IS_LOLLIPOP)
    {
      localGradientDrawable = backgroundDrawableLollipop;
      if (localGradientDrawable != null)
      {
        localGradientDrawable.setColor(paramInt);
        return;
      }
    }
    if (!IS_LOLLIPOP)
    {
      localGradientDrawable = colorableBackgroundDrawableCompat;
      if (localGradientDrawable != null) {
        localGradientDrawable.setColor(paramInt);
      }
    }
  }
  
  void setBackgroundOverwritten()
  {
    backgroundOverwritten = true;
    materialButton.setSupportBackgroundTintList(backgroundTint);
    materialButton.setSupportBackgroundTintMode(backgroundTintMode);
  }
  
  void setCornerRadius(int paramInt)
  {
    if (cornerRadius != paramInt)
    {
      cornerRadius = paramInt;
      GradientDrawable localGradientDrawable;
      float f;
      if ((IS_LOLLIPOP) && (backgroundDrawableLollipop != null) && (strokeDrawableLollipop != null) && (maskDrawableLollipop != null))
      {
        if (Build.VERSION.SDK_INT == 21)
        {
          localGradientDrawable = unwrapBackgroundDrawable();
          f = paramInt + 1.0E-5F;
          localGradientDrawable.setCornerRadius(f);
          unwrapStrokeDrawable().setCornerRadius(f);
        }
        localGradientDrawable = backgroundDrawableLollipop;
        f = paramInt + 1.0E-5F;
        localGradientDrawable.setCornerRadius(f);
        strokeDrawableLollipop.setCornerRadius(f);
        maskDrawableLollipop.setCornerRadius(f);
        return;
      }
      if (!IS_LOLLIPOP)
      {
        localGradientDrawable = colorableBackgroundDrawableCompat;
        if ((localGradientDrawable != null) && (rippleDrawableCompat != null))
        {
          f = paramInt + 1.0E-5F;
          localGradientDrawable.setCornerRadius(f);
          rippleDrawableCompat.setCornerRadius(f);
          materialButton.invalidate();
        }
      }
    }
  }
  
  void setRippleColor(ColorStateList paramColorStateList)
  {
    if (rippleColor != paramColorStateList)
    {
      rippleColor = paramColorStateList;
      if ((IS_LOLLIPOP) && ((materialButton.getBackground() instanceof RippleDrawable)))
      {
        ((RippleDrawable)materialButton.getBackground()).setColor(paramColorStateList);
        return;
      }
      if (!IS_LOLLIPOP)
      {
        Drawable localDrawable = tintableRippleDrawableCompat;
        if (localDrawable != null) {
          DrawableCompat.setTintList(localDrawable, paramColorStateList);
        }
      }
    }
  }
  
  void setStrokeColor(ColorStateList paramColorStateList)
  {
    if (strokeColor != paramColorStateList)
    {
      strokeColor = paramColorStateList;
      Paint localPaint = buttonStrokePaint;
      int i = 0;
      if (paramColorStateList != null) {
        i = paramColorStateList.getColorForState(materialButton.getDrawableState(), 0);
      }
      localPaint.setColor(i);
      updateStroke();
    }
  }
  
  void setStrokeWidth(int paramInt)
  {
    if (strokeWidth != paramInt)
    {
      strokeWidth = paramInt;
      buttonStrokePaint.setStrokeWidth(paramInt);
      updateStroke();
    }
  }
  
  void setSupportBackgroundTintList(ColorStateList paramColorStateList)
  {
    if (backgroundTint != paramColorStateList)
    {
      backgroundTint = paramColorStateList;
      if (IS_LOLLIPOP)
      {
        updateTintAndTintModeLollipop();
        return;
      }
      paramColorStateList = tintableBackgroundDrawableCompat;
      if (paramColorStateList != null) {
        DrawableCompat.setTintList(paramColorStateList, backgroundTint);
      }
    }
  }
  
  void setSupportBackgroundTintMode(PorterDuff.Mode paramMode)
  {
    if (backgroundTintMode != paramMode)
    {
      backgroundTintMode = paramMode;
      if (IS_LOLLIPOP)
      {
        updateTintAndTintModeLollipop();
        return;
      }
      paramMode = tintableBackgroundDrawableCompat;
      if (paramMode != null)
      {
        PorterDuff.Mode localMode = backgroundTintMode;
        if (localMode != null) {
          DrawableCompat.setTintMode(paramMode, localMode);
        }
      }
    }
  }
  
  void updateMaskBounds(int paramInt1, int paramInt2)
  {
    GradientDrawable localGradientDrawable = maskDrawableLollipop;
    if (localGradientDrawable != null) {
      localGradientDrawable.setBounds(insetLeft, insetTop, paramInt2 - insetRight, paramInt1 - insetBottom);
    }
  }
}
