package com.google.android.material.textfield;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.GradientDrawable;
import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.ClassLoaderCreator;
import android.os.Parcelable.Creator;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewStructure;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityRecord;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import androidx.appcompat.content.wiki.AppCompatResources;
import androidx.appcompat.widget.AppCompatDrawableManager;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.TintTypedArray;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.widget.TextViewCompat;
import androidx.customview.view.AbsSavedState;
import com.google.android.material.R.attr;
import com.google.android.material.R.color;
import com.google.android.material.R.dimen;
import com.google.android.material.R.id;
import com.google.android.material.R.layout;
import com.google.android.material.R.string;
import com.google.android.material.R.style;
import com.google.android.material.R.styleable;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.internal.CheckableImageButton;
import com.google.android.material.internal.CollapsingTextHelper;
import com.google.android.material.internal.DescendantOffsetUtils;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

public class TextInputLayout
  extends LinearLayout
{
  public static final int BOX_BACKGROUND_FILLED = 1;
  public static final int BOX_BACKGROUND_NONE = 0;
  public static final int BOX_BACKGROUND_OUTLINE = 2;
  private static final int INVALID_MAX_LENGTH = -1;
  private static final int LABEL_SCALE_ANIMATION_DURATION = 167;
  private static final String LOG_TAG = "TextInputLayout";
  private ValueAnimator animator;
  private GradientDrawable boxBackground;
  private int boxBackgroundColor;
  private int boxBackgroundMode;
  private final int boxBottomOffsetPx;
  private final int boxCollapsedPaddingTopPx;
  private float boxCornerRadiusBottomEnd;
  private float boxCornerRadiusBottomStart;
  private float boxCornerRadiusTopEnd;
  private float boxCornerRadiusTopStart;
  private final int boxLabelCutoutPaddingPx;
  private int boxStrokeColor;
  private final int boxStrokeWidthDefaultPx;
  private final int boxStrokeWidthFocusedPx;
  private int boxStrokeWidthPx;
  final CollapsingTextHelper collapsingTextHelper = new CollapsingTextHelper(this);
  boolean counterEnabled;
  private int counterMaxLength;
  private final int counterOverflowTextAppearance;
  private boolean counterOverflowed;
  private final int counterTextAppearance;
  private TextView counterView;
  private ColorStateList defaultHintTextColor;
  private final int defaultStrokeColor;
  private final int disabledColor;
  EditText editText;
  private Drawable editTextOriginalDrawable;
  private int focusedStrokeColor;
  private ColorStateList focusedTextColor;
  private boolean hasPasswordToggleTintList;
  private boolean hasPasswordToggleTintMode;
  private boolean hasReconstructedEditTextBackground;
  private CharSequence hint;
  private boolean hintAnimationEnabled;
  private boolean hintEnabled;
  private boolean hintExpanded;
  private final int hoveredStrokeColor;
  private boolean inDrawableStateChanged;
  private final IndicatorViewController indicatorViewController = new IndicatorViewController(this);
  private final FrameLayout inputFrame;
  private boolean isProvidingHint;
  private Drawable originalEditTextEndDrawable;
  private CharSequence originalHint;
  private CharSequence passwordToggleContentDesc;
  private Drawable passwordToggleDrawable;
  private Drawable passwordToggleDummyDrawable;
  private boolean passwordToggleEnabled;
  private ColorStateList passwordToggleTintList;
  private PorterDuff.Mode passwordToggleTintMode;
  private CheckableImageButton passwordToggleView;
  private boolean passwordToggledVisible;
  private boolean restoringSavedState;
  private final Rect tmpRect = new Rect();
  private final RectF tmpRectF = new RectF();
  private Typeface typeface;
  
  public TextInputLayout(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public TextInputLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, R.attr.textInputStyle);
  }
  
  public TextInputLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    setOrientation(1);
    setWillNotDraw(false);
    setAddStatesFromChildren(true);
    inputFrame = new FrameLayout(paramContext);
    inputFrame.setAddStatesFromChildren(true);
    addView(inputFrame);
    collapsingTextHelper.setTextSizeInterpolator(AnimationUtils.LINEAR_INTERPOLATOR);
    collapsingTextHelper.setPositionInterpolator(AnimationUtils.LINEAR_INTERPOLATOR);
    collapsingTextHelper.setCollapsedTextGravity(8388659);
    paramAttributeSet = ThemeEnforcement.obtainTintedStyledAttributes(paramContext, paramAttributeSet, R.styleable.TextInputLayout, paramInt, R.style.Widget_Design_TextInputLayout, new int[0]);
    hintEnabled = paramAttributeSet.getBoolean(R.styleable.TextInputLayout_hintEnabled, true);
    setHint(paramAttributeSet.getText(R.styleable.TextInputLayout_android_hint));
    hintAnimationEnabled = paramAttributeSet.getBoolean(R.styleable.TextInputLayout_hintAnimationEnabled, true);
    boxBottomOffsetPx = paramContext.getResources().getDimensionPixelOffset(R.dimen.mtrl_textinput_box_bottom_offset);
    boxLabelCutoutPaddingPx = paramContext.getResources().getDimensionPixelOffset(R.dimen.mtrl_textinput_box_label_cutout_padding);
    boxCollapsedPaddingTopPx = paramAttributeSet.getDimensionPixelOffset(R.styleable.TextInputLayout_boxCollapsedPaddingTop, 0);
    boxCornerRadiusTopStart = paramAttributeSet.getDimension(R.styleable.TextInputLayout_boxCornerRadiusTopStart, 0.0F);
    boxCornerRadiusTopEnd = paramAttributeSet.getDimension(R.styleable.TextInputLayout_boxCornerRadiusTopEnd, 0.0F);
    boxCornerRadiusBottomEnd = paramAttributeSet.getDimension(R.styleable.TextInputLayout_boxCornerRadiusBottomEnd, 0.0F);
    boxCornerRadiusBottomStart = paramAttributeSet.getDimension(R.styleable.TextInputLayout_boxCornerRadiusBottomStart, 0.0F);
    boxBackgroundColor = paramAttributeSet.getColor(R.styleable.TextInputLayout_boxBackgroundColor, 0);
    focusedStrokeColor = paramAttributeSet.getColor(R.styleable.TextInputLayout_boxStrokeColor, 0);
    boxStrokeWidthDefaultPx = paramContext.getResources().getDimensionPixelSize(R.dimen.mtrl_textinput_box_stroke_width_default);
    boxStrokeWidthFocusedPx = paramContext.getResources().getDimensionPixelSize(R.dimen.mtrl_textinput_box_stroke_width_focused);
    boxStrokeWidthPx = boxStrokeWidthDefaultPx;
    setBoxBackgroundMode(paramAttributeSet.getInt(R.styleable.TextInputLayout_boxBackgroundMode, 0));
    if (paramAttributeSet.hasValue(R.styleable.TextInputLayout_android_textColorHint))
    {
      ColorStateList localColorStateList = paramAttributeSet.getColorStateList(R.styleable.TextInputLayout_android_textColorHint);
      focusedTextColor = localColorStateList;
      defaultHintTextColor = localColorStateList;
    }
    defaultStrokeColor = ContextCompat.getColor(paramContext, R.color.mtrl_textinput_default_box_stroke_color);
    disabledColor = ContextCompat.getColor(paramContext, R.color.mtrl_textinput_disabled_color);
    hoveredStrokeColor = ContextCompat.getColor(paramContext, R.color.mtrl_textinput_hovered_box_stroke_color);
    if (paramAttributeSet.getResourceId(R.styleable.TextInputLayout_hintTextAppearance, -1) != -1) {
      setHintTextAppearance(paramAttributeSet.getResourceId(R.styleable.TextInputLayout_hintTextAppearance, 0));
    }
    paramInt = paramAttributeSet.getResourceId(R.styleable.TextInputLayout_errorTextAppearance, 0);
    boolean bool1 = paramAttributeSet.getBoolean(R.styleable.TextInputLayout_errorEnabled, false);
    int i = paramAttributeSet.getResourceId(R.styleable.TextInputLayout_helperTextTextAppearance, 0);
    boolean bool2 = paramAttributeSet.getBoolean(R.styleable.TextInputLayout_helperTextEnabled, false);
    paramContext = paramAttributeSet.getText(R.styleable.TextInputLayout_helperText);
    boolean bool3 = paramAttributeSet.getBoolean(R.styleable.TextInputLayout_counterEnabled, false);
    setCounterMaxLength(paramAttributeSet.getInt(R.styleable.TextInputLayout_counterMaxLength, -1));
    counterTextAppearance = paramAttributeSet.getResourceId(R.styleable.TextInputLayout_counterTextAppearance, 0);
    counterOverflowTextAppearance = paramAttributeSet.getResourceId(R.styleable.TextInputLayout_counterOverflowTextAppearance, 0);
    passwordToggleEnabled = paramAttributeSet.getBoolean(R.styleable.TextInputLayout_passwordToggleEnabled, false);
    passwordToggleDrawable = paramAttributeSet.getDrawable(R.styleable.TextInputLayout_passwordToggleDrawable);
    passwordToggleContentDesc = paramAttributeSet.getText(R.styleable.TextInputLayout_passwordToggleContentDescription);
    if (paramAttributeSet.hasValue(R.styleable.TextInputLayout_passwordToggleTint))
    {
      hasPasswordToggleTintList = true;
      passwordToggleTintList = paramAttributeSet.getColorStateList(R.styleable.TextInputLayout_passwordToggleTint);
    }
    if (paramAttributeSet.hasValue(R.styleable.TextInputLayout_passwordToggleTintMode))
    {
      hasPasswordToggleTintMode = true;
      passwordToggleTintMode = ViewUtils.parseTintMode(paramAttributeSet.getInt(R.styleable.TextInputLayout_passwordToggleTintMode, -1), null);
    }
    paramAttributeSet.recycle();
    setHelperTextEnabled(bool2);
    setHelperText(paramContext);
    setHelperTextTextAppearance(i);
    setErrorEnabled(bool1);
    setErrorTextAppearance(paramInt);
    setCounterEnabled(bool3);
    applyPasswordToggleTint();
    ViewCompat.setImportantForAccessibility(this, 2);
  }
  
  private void applyBoxAttributes()
  {
    if (boxBackground == null) {
      return;
    }
    setBoxAttributes();
    EditText localEditText = editText;
    if ((localEditText != null) && (boxBackgroundMode == 2))
    {
      if (localEditText.getBackground() != null) {
        editTextOriginalDrawable = editText.getBackground();
      }
      ViewCompat.setBackground(editText, null);
    }
    localEditText = editText;
    if ((localEditText != null) && (boxBackgroundMode == 1))
    {
      Drawable localDrawable = editTextOriginalDrawable;
      if (localDrawable != null) {
        ViewCompat.setBackground(localEditText, localDrawable);
      }
    }
    int i = boxStrokeWidthPx;
    if (i > -1)
    {
      int j = boxStrokeColor;
      if (j != 0) {
        boxBackground.setStroke(i, j);
      }
    }
    boxBackground.setCornerRadii(getCornerRadiiAsArray());
    boxBackground.setColor(boxBackgroundColor);
    invalidate();
  }
  
  private void applyCutoutPadding(RectF paramRectF)
  {
    left -= boxLabelCutoutPaddingPx;
    top -= boxLabelCutoutPaddingPx;
    right += boxLabelCutoutPaddingPx;
    bottom += boxLabelCutoutPaddingPx;
  }
  
  private void applyPasswordToggleTint()
  {
    if ((passwordToggleDrawable != null) && ((hasPasswordToggleTintList) || (hasPasswordToggleTintMode)))
    {
      passwordToggleDrawable = DrawableCompat.wrap(passwordToggleDrawable).mutate();
      if (hasPasswordToggleTintList) {
        DrawableCompat.setTintList(passwordToggleDrawable, passwordToggleTintList);
      }
      if (hasPasswordToggleTintMode) {
        DrawableCompat.setTintMode(passwordToggleDrawable, passwordToggleTintMode);
      }
      Object localObject = passwordToggleView;
      if (localObject != null)
      {
        localObject = ((ImageView)localObject).getDrawable();
        Drawable localDrawable = passwordToggleDrawable;
        if (localObject != localDrawable) {
          passwordToggleView.setImageDrawable(localDrawable);
        }
      }
    }
  }
  
  private void assignBoxBackgroundByMode()
  {
    int i = boxBackgroundMode;
    if (i == 0)
    {
      boxBackground = null;
      return;
    }
    if ((i == 2) && (hintEnabled) && (!(boxBackground instanceof CutoutDrawable)))
    {
      boxBackground = new CutoutDrawable();
      return;
    }
    if (!(boxBackground instanceof GradientDrawable)) {
      boxBackground = new GradientDrawable();
    }
  }
  
  private int calculateBoxBackgroundTop()
  {
    EditText localEditText = editText;
    if (localEditText == null) {
      return 0;
    }
    int i = boxBackgroundMode;
    if (i != 1)
    {
      if (i != 2) {
        return 0;
      }
      return localEditText.getTop() + calculateLabelMarginTop();
    }
    return localEditText.getTop();
  }
  
  private int calculateCollapsedTextTopBounds()
  {
    int i = boxBackgroundMode;
    if (i != 1)
    {
      if (i != 2) {
        return getPaddingTop();
      }
      return getBoxBackgroundgetBoundstop - calculateLabelMarginTop();
    }
    return getBoxBackgroundgetBoundstop + boxCollapsedPaddingTopPx;
  }
  
  private int calculateLabelMarginTop()
  {
    if (!hintEnabled) {
      return 0;
    }
    int i = boxBackgroundMode;
    if ((i != 0) && (i != 1)) {
      if (i != 2) {
        return 0;
      }
    }
    for (float f = collapsingTextHelper.getCollapsedTextHeight() / 2.0F;; f = collapsingTextHelper.getCollapsedTextHeight()) {
      return (int)f;
    }
  }
  
  private void closeCutout()
  {
    if (cutoutEnabled()) {
      ((CutoutDrawable)boxBackground).removeCutout();
    }
  }
  
  private void collapseHint(boolean paramBoolean)
  {
    ValueAnimator localValueAnimator = animator;
    if ((localValueAnimator != null) && (localValueAnimator.isRunning())) {
      animator.cancel();
    }
    if ((paramBoolean) && (hintAnimationEnabled)) {
      animateToExpansionFraction(1.0F);
    } else {
      collapsingTextHelper.setExpansionFraction(1.0F);
    }
    hintExpanded = false;
    if (cutoutEnabled()) {
      openCutout();
    }
  }
  
  private boolean cutoutEnabled()
  {
    return (hintEnabled) && (!TextUtils.isEmpty(hint)) && ((boxBackground instanceof CutoutDrawable));
  }
  
  private void ensureBackgroundDrawableStateWorkaround()
  {
    int i = Build.VERSION.SDK_INT;
    if ((i != 21) && (i != 22)) {
      return;
    }
    Drawable localDrawable1 = editText.getBackground();
    if (localDrawable1 == null) {
      return;
    }
    if (!hasReconstructedEditTextBackground)
    {
      Drawable localDrawable2 = localDrawable1.getConstantState().newDrawable();
      if ((localDrawable1 instanceof DrawableContainer)) {
        hasReconstructedEditTextBackground = com.google.android.material.internal.DrawableUtils.setContainerConstantState((DrawableContainer)localDrawable1, localDrawable2.getConstantState());
      }
      if (!hasReconstructedEditTextBackground)
      {
        ViewCompat.setBackground(editText, localDrawable2);
        hasReconstructedEditTextBackground = true;
        onApplyBoxBackgroundMode();
      }
    }
  }
  
  private void expandHint(boolean paramBoolean)
  {
    ValueAnimator localValueAnimator = animator;
    if ((localValueAnimator != null) && (localValueAnimator.isRunning())) {
      animator.cancel();
    }
    if ((paramBoolean) && (hintAnimationEnabled)) {
      animateToExpansionFraction(0.0F);
    } else {
      collapsingTextHelper.setExpansionFraction(0.0F);
    }
    if ((cutoutEnabled()) && (((CutoutDrawable)boxBackground).hasCutout())) {
      closeCutout();
    }
    hintExpanded = true;
  }
  
  private Drawable getBoxBackground()
  {
    int i = boxBackgroundMode;
    if ((i != 1) && (i != 2)) {
      throw new IllegalStateException();
    }
    return boxBackground;
  }
  
  private float[] getCornerRadiiAsArray()
  {
    if (!ViewUtils.isLayoutRtl(this))
    {
      f1 = boxCornerRadiusTopStart;
      f2 = boxCornerRadiusTopEnd;
      f3 = boxCornerRadiusBottomEnd;
      f4 = boxCornerRadiusBottomStart;
      return new float[] { f1, f1, f2, f2, f3, f3, f4, f4 };
    }
    float f1 = boxCornerRadiusTopEnd;
    float f2 = boxCornerRadiusTopStart;
    float f3 = boxCornerRadiusBottomStart;
    float f4 = boxCornerRadiusBottomEnd;
    return new float[] { f1, f1, f2, f2, f3, f3, f4, f4 };
  }
  
  private boolean hasPasswordTransformation()
  {
    EditText localEditText = editText;
    return (localEditText != null) && ((localEditText.getTransformationMethod() instanceof PasswordTransformationMethod));
  }
  
  private void onApplyBoxBackgroundMode()
  {
    assignBoxBackgroundByMode();
    if (boxBackgroundMode != 0) {
      updateInputLayoutMargins();
    }
    updateTextInputBoxBounds();
  }
  
  private void openCutout()
  {
    if (!cutoutEnabled()) {
      return;
    }
    RectF localRectF = tmpRectF;
    collapsingTextHelper.getCollapsedTextActualBounds(localRectF);
    applyCutoutPadding(localRectF);
    ((CutoutDrawable)boxBackground).setCutout(localRectF);
  }
  
  private static void recursiveSetEnabled(ViewGroup paramViewGroup, boolean paramBoolean)
  {
    int j = paramViewGroup.getChildCount();
    int i = 0;
    while (i < j)
    {
      View localView = paramViewGroup.getChildAt(i);
      localView.setEnabled(paramBoolean);
      if ((localView instanceof ViewGroup)) {
        recursiveSetEnabled((ViewGroup)localView, paramBoolean);
      }
      i += 1;
    }
  }
  
  private void setBoxAttributes()
  {
    int i = boxBackgroundMode;
    if (i != 1)
    {
      if (i != 2) {
        return;
      }
      if (focusedStrokeColor == 0) {
        focusedStrokeColor = focusedTextColor.getColorForState(getDrawableState(), focusedTextColor.getDefaultColor());
      }
    }
    else
    {
      boxStrokeWidthPx = 0;
    }
  }
  
  private void setEditText(EditText paramEditText)
  {
    if (editText == null)
    {
      if (!(paramEditText instanceof TextInputEditText)) {
        Log.i("TextInputLayout", "EditText added is not a TextInputEditText. Please switch to using that class instead.");
      }
      editText = paramEditText;
      onApplyBoxBackgroundMode();
      setTextInputAccessibilityDelegate(new AccessibilityDelegate(this));
      if (!hasPasswordTransformation()) {
        collapsingTextHelper.setTypefaces(editText.getTypeface());
      }
      collapsingTextHelper.setExpandedTextSize(editText.getTextSize());
      int i = editText.getGravity();
      collapsingTextHelper.setCollapsedTextGravity(i & 0xFFFFFF8F | 0x30);
      collapsingTextHelper.setExpandedTextGravity(i);
      editText.addTextChangedListener(new TextWatcher()
      {
        public void afterTextChanged(Editable paramAnonymousEditable)
        {
          TextInputLayout localTextInputLayout = TextInputLayout.this;
          localTextInputLayout.updateLabelState(restoringSavedState ^ true);
          if (counterEnabled) {
            updateCounter(paramAnonymousEditable.length());
          }
        }
        
        public void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {}
        
        public void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {}
      });
      if (defaultHintTextColor == null) {
        defaultHintTextColor = editText.getHintTextColors();
      }
      if (hintEnabled)
      {
        if (TextUtils.isEmpty(hint))
        {
          originalHint = editText.getHint();
          setHint(originalHint);
          editText.setHint(null);
        }
        isProvidingHint = true;
      }
      if (counterView != null) {
        updateCounter(editText.getText().length());
      }
      indicatorViewController.adjustIndicatorPadding();
      updatePasswordToggleView();
      updateLabelState(false, true);
      return;
    }
    throw new IllegalArgumentException("We already have an EditText, can only have one");
  }
  
  private void setHintInternal(CharSequence paramCharSequence)
  {
    if (!TextUtils.equals(paramCharSequence, hint))
    {
      hint = paramCharSequence;
      collapsingTextHelper.setText(paramCharSequence);
      if (!hintExpanded) {
        openCutout();
      }
    }
  }
  
  private boolean shouldShowPasswordIcon()
  {
    return (passwordToggleEnabled) && ((hasPasswordTransformation()) || (passwordToggledVisible));
  }
  
  private void updateEditTextBackgroundBounds()
  {
    Object localObject1 = editText;
    if (localObject1 == null) {
      return;
    }
    Object localObject2 = ((View)localObject1).getBackground();
    localObject1 = localObject2;
    if (localObject2 == null) {
      return;
    }
    if (androidx.appcompat.widget.DrawableUtils.canSafelyMutateDrawable((Drawable)localObject2)) {
      localObject1 = ((Drawable)localObject2).mutate();
    }
    localObject2 = new Rect();
    DescendantOffsetUtils.getDescendantRect(this, editText, (Rect)localObject2);
    localObject2 = ((Drawable)localObject1).getBounds();
    if (left != right)
    {
      Rect localRect = new Rect();
      ((Drawable)localObject1).getPadding(localRect);
      int i = left;
      int j = left;
      int k = right;
      int m = right;
      ((Drawable)localObject1).setBounds(i - j, top, k + m * 2, editText.getBottom());
    }
  }
  
  private void updateInputLayoutMargins()
  {
    LinearLayout.LayoutParams localLayoutParams = (LinearLayout.LayoutParams)inputFrame.getLayoutParams();
    int i = calculateLabelMarginTop();
    if (i != topMargin)
    {
      topMargin = i;
      inputFrame.requestLayout();
    }
  }
  
  private void updateLabelState(boolean paramBoolean1, boolean paramBoolean2)
  {
    boolean bool1 = isEnabled();
    Object localObject = editText;
    int j = 1;
    int i;
    if ((localObject != null) && (!TextUtils.isEmpty(((EditText)localObject).getText()))) {
      i = 1;
    } else {
      i = 0;
    }
    localObject = editText;
    if ((localObject == null) || (!((View)localObject).hasFocus())) {
      j = 0;
    }
    boolean bool2 = indicatorViewController.errorShouldBeShown();
    localObject = defaultHintTextColor;
    if (localObject != null)
    {
      collapsingTextHelper.setCollapsedTextColor((ColorStateList)localObject);
      collapsingTextHelper.setExpandedTextColor(defaultHintTextColor);
    }
    if (!bool1)
    {
      collapsingTextHelper.setCollapsedTextColor(ColorStateList.valueOf(disabledColor));
      collapsingTextHelper.setExpandedTextColor(ColorStateList.valueOf(disabledColor));
    }
    else if (bool2)
    {
      collapsingTextHelper.setCollapsedTextColor(indicatorViewController.getErrorViewTextColors());
    }
    else
    {
      if (counterOverflowed)
      {
        localObject = counterView;
        if (localObject != null)
        {
          collapsingTextHelper.setCollapsedTextColor(((TextView)localObject).getTextColors());
          break label219;
        }
      }
      if (j != 0)
      {
        localObject = focusedTextColor;
        if (localObject != null) {
          collapsingTextHelper.setCollapsedTextColor((ColorStateList)localObject);
        }
      }
    }
    label219:
    if ((i == 0) && ((!isEnabled()) || ((j == 0) && (!bool2))))
    {
      if ((paramBoolean2) || (!hintExpanded)) {
        expandHint(paramBoolean1);
      }
    }
    else if ((paramBoolean2) || (hintExpanded)) {
      collapseHint(paramBoolean1);
    }
  }
  
  private void updatePasswordToggleView()
  {
    if (editText == null) {
      return;
    }
    if (shouldShowPasswordIcon())
    {
      if (passwordToggleView == null)
      {
        passwordToggleView = ((CheckableImageButton)LayoutInflater.from(getContext()).inflate(R.layout.design_text_input_password_icon, inputFrame, false));
        passwordToggleView.setImageDrawable(passwordToggleDrawable);
        passwordToggleView.setContentDescription(passwordToggleContentDesc);
        inputFrame.addView(passwordToggleView);
        passwordToggleView.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramAnonymousView)
          {
            passwordVisibilityToggleRequested(false);
          }
        });
      }
      localObject = editText;
      if ((localObject != null) && (ViewCompat.getMinimumHeight((View)localObject) <= 0)) {
        editText.setMinimumHeight(ViewCompat.getMinimumHeight(passwordToggleView));
      }
      passwordToggleView.setVisibility(0);
      passwordToggleView.setChecked(passwordToggledVisible);
      if (passwordToggleDummyDrawable == null) {
        passwordToggleDummyDrawable = new ColorDrawable();
      }
      passwordToggleDummyDrawable.setBounds(0, 0, passwordToggleView.getMeasuredWidth(), 1);
      localObject = TextViewCompat.getCompoundDrawablesRelative(editText);
      if (localObject[2] != passwordToggleDummyDrawable) {
        originalEditTextEndDrawable = localObject[2];
      }
      TextViewCompat.setCompoundDrawablesRelative(editText, localObject[0], localObject[1], passwordToggleDummyDrawable, localObject[3]);
      passwordToggleView.setPadding(editText.getPaddingLeft(), editText.getPaddingTop(), editText.getPaddingRight(), editText.getPaddingBottom());
      return;
    }
    Object localObject = passwordToggleView;
    if ((localObject != null) && (((View)localObject).getVisibility() == 0)) {
      passwordToggleView.setVisibility(8);
    }
    if (passwordToggleDummyDrawable != null)
    {
      localObject = TextViewCompat.getCompoundDrawablesRelative(editText);
      if (localObject[2] == passwordToggleDummyDrawable)
      {
        TextViewCompat.setCompoundDrawablesRelative(editText, localObject[0], localObject[1], originalEditTextEndDrawable, localObject[3]);
        passwordToggleDummyDrawable = null;
      }
    }
  }
  
  private void updateTextInputBoxBounds()
  {
    if ((boxBackgroundMode != 0) && (boxBackground != null) && (editText != null))
    {
      if (getRight() == 0) {
        return;
      }
      int i3 = editText.getLeft();
      int j = i3;
      int i2 = calculateBoxBackgroundTop();
      int k = i2;
      int i1 = editText.getRight();
      int m = i1;
      int n = editText.getBottom() + boxBottomOffsetPx;
      int i = n;
      if (boxBackgroundMode == 2)
      {
        i = boxStrokeWidthFocusedPx;
        j = i3 + i / 2;
        k = i2 - i / 2;
        m = i1 - i / 2;
        i = n + i / 2;
      }
      boxBackground.setBounds(j, k, m, i);
      applyBoxAttributes();
      updateEditTextBackgroundBounds();
    }
  }
  
  public void addView(View paramView, int paramInt, ViewGroup.LayoutParams paramLayoutParams)
  {
    if ((paramView instanceof EditText))
    {
      FrameLayout.LayoutParams localLayoutParams = new FrameLayout.LayoutParams(paramLayoutParams);
      gravity = (gravity & 0xFFFFFF8F | 0x10);
      inputFrame.addView(paramView, localLayoutParams);
      inputFrame.setLayoutParams(paramLayoutParams);
      updateInputLayoutMargins();
      setEditText((EditText)paramView);
      return;
    }
    super.addView(paramView, paramInt, paramLayoutParams);
  }
  
  void animateToExpansionFraction(float paramFloat)
  {
    if (collapsingTextHelper.getExpansionFraction() == paramFloat) {
      return;
    }
    if (animator == null)
    {
      animator = new ValueAnimator();
      animator.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
      animator.setDuration(167L);
      animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
      {
        public void onAnimationUpdate(ValueAnimator paramAnonymousValueAnimator)
        {
          collapsingTextHelper.setExpansionFraction(((Float)paramAnonymousValueAnimator.getAnimatedValue()).floatValue());
        }
      });
    }
    animator.setFloatValues(new float[] { collapsingTextHelper.getExpansionFraction(), paramFloat });
    animator.start();
  }
  
  boolean cutoutIsOpen()
  {
    return (cutoutEnabled()) && (((CutoutDrawable)boxBackground).hasCutout());
  }
  
  public void dispatchProvideAutofillStructure(ViewStructure paramViewStructure, int paramInt)
  {
    if (originalHint != null)
    {
      Object localObject = editText;
      if (localObject != null)
      {
        boolean bool = isProvidingHint;
        isProvidingHint = false;
        localObject = ((TextView)localObject).getHint();
        editText.setHint(originalHint);
        try
        {
          super.dispatchProvideAutofillStructure(paramViewStructure, paramInt);
          editText.setHint((CharSequence)localObject);
          isProvidingHint = bool;
          return;
        }
        catch (Throwable paramViewStructure)
        {
          editText.setHint((CharSequence)localObject);
          isProvidingHint = bool;
          throw paramViewStructure;
        }
      }
    }
    super.dispatchProvideAutofillStructure(paramViewStructure, paramInt);
  }
  
  protected void dispatchRestoreInstanceState(SparseArray paramSparseArray)
  {
    restoringSavedState = true;
    super.dispatchRestoreInstanceState(paramSparseArray);
    restoringSavedState = false;
  }
  
  public void draw(Canvas paramCanvas)
  {
    GradientDrawable localGradientDrawable = boxBackground;
    if (localGradientDrawable != null) {
      localGradientDrawable.draw(paramCanvas);
    }
    super.draw(paramCanvas);
    if (hintEnabled) {
      collapsingTextHelper.draw(paramCanvas);
    }
  }
  
  protected void drawableStateChanged()
  {
    if (inDrawableStateChanged) {
      return;
    }
    boolean bool2 = true;
    inDrawableStateChanged = true;
    super.drawableStateChanged();
    int[] arrayOfInt = getDrawableState();
    if ((!ViewCompat.isLaidOut(this)) || (!isEnabled())) {
      bool2 = false;
    }
    updateLabelState(bool2);
    updateEditTextBackground();
    updateTextInputBoxBounds();
    updateTextInputBoxState();
    CollapsingTextHelper localCollapsingTextHelper = collapsingTextHelper;
    boolean bool1;
    if (localCollapsingTextHelper != null) {
      bool1 = localCollapsingTextHelper.setState(arrayOfInt) | false;
    } else {
      bool1 = false;
    }
    if (bool1) {
      invalidate();
    }
    inDrawableStateChanged = false;
  }
  
  public int getBoxBackgroundColor()
  {
    return boxBackgroundColor;
  }
  
  public float getBoxCornerRadiusBottomEnd()
  {
    return boxCornerRadiusBottomEnd;
  }
  
  public float getBoxCornerRadiusBottomStart()
  {
    return boxCornerRadiusBottomStart;
  }
  
  public float getBoxCornerRadiusTopEnd()
  {
    return boxCornerRadiusTopEnd;
  }
  
  public float getBoxCornerRadiusTopStart()
  {
    return boxCornerRadiusTopStart;
  }
  
  public int getBoxStrokeColor()
  {
    return focusedStrokeColor;
  }
  
  public int getCounterMaxLength()
  {
    return counterMaxLength;
  }
  
  CharSequence getCounterOverflowDescription()
  {
    if ((counterEnabled) && (counterOverflowed))
    {
      TextView localTextView = counterView;
      if (localTextView != null) {
        return localTextView.getContentDescription();
      }
    }
    return null;
  }
  
  public ColorStateList getDefaultHintTextColor()
  {
    return defaultHintTextColor;
  }
  
  public EditText getEditText()
  {
    return editText;
  }
  
  public CharSequence getError()
  {
    if (indicatorViewController.isErrorEnabled()) {
      return indicatorViewController.getErrorText();
    }
    return null;
  }
  
  public int getErrorCurrentTextColors()
  {
    return indicatorViewController.getErrorViewCurrentTextColor();
  }
  
  final int getErrorTextCurrentColor()
  {
    return indicatorViewController.getErrorViewCurrentTextColor();
  }
  
  public CharSequence getHelperText()
  {
    if (indicatorViewController.isHelperTextEnabled()) {
      return indicatorViewController.getHelperText();
    }
    return null;
  }
  
  public int getHelperTextCurrentTextColor()
  {
    return indicatorViewController.getHelperTextViewCurrentTextColor();
  }
  
  public CharSequence getHint()
  {
    if (hintEnabled) {
      return hint;
    }
    return null;
  }
  
  final float getHintCollapsedTextHeight()
  {
    return collapsingTextHelper.getCollapsedTextHeight();
  }
  
  final int getHintCurrentCollapsedTextColor()
  {
    return collapsingTextHelper.getCurrentCollapsedTextColor();
  }
  
  public CharSequence getPasswordVisibilityToggleContentDescription()
  {
    return passwordToggleContentDesc;
  }
  
  public Drawable getPasswordVisibilityToggleDrawable()
  {
    return passwordToggleDrawable;
  }
  
  public Typeface getTypeface()
  {
    return typeface;
  }
  
  public boolean isCounterEnabled()
  {
    return counterEnabled;
  }
  
  public boolean isErrorEnabled()
  {
    return indicatorViewController.isErrorEnabled();
  }
  
  final boolean isHelperTextDisplayed()
  {
    return indicatorViewController.helperTextIsDisplayed();
  }
  
  public boolean isHelperTextEnabled()
  {
    return indicatorViewController.isHelperTextEnabled();
  }
  
  public boolean isHintAnimationEnabled()
  {
    return hintAnimationEnabled;
  }
  
  public boolean isHintEnabled()
  {
    return hintEnabled;
  }
  
  final boolean isHintExpanded()
  {
    return hintExpanded;
  }
  
  public boolean isPasswordVisibilityToggleEnabled()
  {
    return passwordToggleEnabled;
  }
  
  boolean isProvidingHint()
  {
    return isProvidingHint;
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    if (boxBackground != null) {
      updateTextInputBoxBounds();
    }
    if (hintEnabled)
    {
      EditText localEditText = editText;
      if (localEditText != null)
      {
        Rect localRect = tmpRect;
        DescendantOffsetUtils.getDescendantRect(this, localEditText, localRect);
        paramInt1 = left + editText.getCompoundPaddingLeft();
        paramInt3 = right - editText.getCompoundPaddingRight();
        int i = calculateCollapsedTextTopBounds();
        collapsingTextHelper.setExpandedBounds(paramInt1, top + editText.getCompoundPaddingTop(), paramInt3, bottom - editText.getCompoundPaddingBottom());
        collapsingTextHelper.setCollapsedBounds(paramInt1, i, paramInt3, paramInt4 - paramInt2 - getPaddingBottom());
        collapsingTextHelper.recalculate();
        if ((cutoutEnabled()) && (!hintExpanded)) {
          openCutout();
        }
      }
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    updatePasswordToggleView();
    super.onMeasure(paramInt1, paramInt2);
  }
  
  protected void onRestoreInstanceState(Parcelable paramParcelable)
  {
    if (!(paramParcelable instanceof SavedState))
    {
      super.onRestoreInstanceState(paramParcelable);
      return;
    }
    paramParcelable = (SavedState)paramParcelable;
    super.onRestoreInstanceState(paramParcelable.getSuperState());
    setError(error);
    if (isPasswordToggledVisible) {
      passwordVisibilityToggleRequested(true);
    }
    requestLayout();
  }
  
  public Parcelable onSaveInstanceState()
  {
    SavedState localSavedState = new SavedState(super.onSaveInstanceState());
    if (indicatorViewController.errorShouldBeShown()) {
      error = getError();
    }
    isPasswordToggledVisible = passwordToggledVisible;
    return localSavedState;
  }
  
  public void passwordVisibilityToggleRequested(boolean paramBoolean)
  {
    if (passwordToggleEnabled)
    {
      int i = editText.getSelectionEnd();
      if (hasPasswordTransformation())
      {
        editText.setTransformationMethod(null);
        passwordToggledVisible = true;
      }
      else
      {
        editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        passwordToggledVisible = false;
      }
      passwordToggleView.setChecked(passwordToggledVisible);
      if (paramBoolean) {
        passwordToggleView.jumpDrawablesToCurrentState();
      }
      editText.setSelection(i);
    }
  }
  
  public void setBoxBackgroundColor(int paramInt)
  {
    if (boxBackgroundColor != paramInt)
    {
      boxBackgroundColor = paramInt;
      applyBoxAttributes();
    }
  }
  
  public void setBoxBackgroundColorResource(int paramInt)
  {
    setBoxBackgroundColor(ContextCompat.getColor(getContext(), paramInt));
  }
  
  public void setBoxBackgroundMode(int paramInt)
  {
    if (paramInt == boxBackgroundMode) {
      return;
    }
    boxBackgroundMode = paramInt;
    onApplyBoxBackgroundMode();
  }
  
  public void setBoxCornerRadii(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    if ((boxCornerRadiusTopStart != paramFloat1) || (boxCornerRadiusTopEnd != paramFloat2) || (boxCornerRadiusBottomEnd != paramFloat4) || (boxCornerRadiusBottomStart != paramFloat3))
    {
      boxCornerRadiusTopStart = paramFloat1;
      boxCornerRadiusTopEnd = paramFloat2;
      boxCornerRadiusBottomEnd = paramFloat4;
      boxCornerRadiusBottomStart = paramFloat3;
      applyBoxAttributes();
    }
  }
  
  public void setBoxCornerRadiiResources(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    setBoxCornerRadii(getContext().getResources().getDimension(paramInt1), getContext().getResources().getDimension(paramInt2), getContext().getResources().getDimension(paramInt3), getContext().getResources().getDimension(paramInt4));
  }
  
  public void setBoxStrokeColor(int paramInt)
  {
    if (focusedStrokeColor != paramInt)
    {
      focusedStrokeColor = paramInt;
      updateTextInputBoxState();
    }
  }
  
  public void setCounterEnabled(boolean paramBoolean)
  {
    if (counterEnabled != paramBoolean)
    {
      if (paramBoolean)
      {
        counterView = new AppCompatTextView(getContext());
        counterView.setId(R.id.textinput_counter);
        Object localObject = typeface;
        if (localObject != null) {
          counterView.setTypeface((Typeface)localObject);
        }
        counterView.setMaxLines(1);
        setTextAppearanceCompatWithErrorFallback(counterView, counterTextAppearance);
        indicatorViewController.addIndicator(counterView, 2);
        localObject = editText;
        if (localObject == null) {
          updateCounter(0);
        } else {
          updateCounter(((EditText)localObject).getText().length());
        }
      }
      else
      {
        indicatorViewController.removeIndicator(counterView, 2);
        counterView = null;
      }
      counterEnabled = paramBoolean;
    }
  }
  
  public void setCounterMaxLength(int paramInt)
  {
    if (counterMaxLength != paramInt)
    {
      if (paramInt > 0) {
        counterMaxLength = paramInt;
      } else {
        counterMaxLength = -1;
      }
      if (counterEnabled)
      {
        EditText localEditText = editText;
        if (localEditText == null) {
          paramInt = 0;
        } else {
          paramInt = localEditText.getText().length();
        }
        updateCounter(paramInt);
      }
    }
  }
  
  public void setDefaultHintTextColor(ColorStateList paramColorStateList)
  {
    defaultHintTextColor = paramColorStateList;
    focusedTextColor = paramColorStateList;
    if (editText != null) {
      updateLabelState(false);
    }
  }
  
  public void setEnabled(boolean paramBoolean)
  {
    recursiveSetEnabled(this, paramBoolean);
    super.setEnabled(paramBoolean);
  }
  
  public void setError(CharSequence paramCharSequence)
  {
    if (!indicatorViewController.isErrorEnabled())
    {
      if (TextUtils.isEmpty(paramCharSequence)) {
        return;
      }
      setErrorEnabled(true);
    }
    if (!TextUtils.isEmpty(paramCharSequence))
    {
      indicatorViewController.showError(paramCharSequence);
      return;
    }
    indicatorViewController.hideError();
  }
  
  public void setErrorEnabled(boolean paramBoolean)
  {
    indicatorViewController.setErrorEnabled(paramBoolean);
  }
  
  public void setErrorTextAppearance(int paramInt)
  {
    indicatorViewController.setErrorTextAppearance(paramInt);
  }
  
  public void setErrorTextColor(ColorStateList paramColorStateList)
  {
    indicatorViewController.setErrorViewTextColor(paramColorStateList);
  }
  
  public void setHelperText(CharSequence paramCharSequence)
  {
    if (TextUtils.isEmpty(paramCharSequence))
    {
      if (isHelperTextEnabled()) {
        setHelperTextEnabled(false);
      }
    }
    else
    {
      if (!isHelperTextEnabled()) {
        setHelperTextEnabled(true);
      }
      indicatorViewController.showHelper(paramCharSequence);
    }
  }
  
  public void setHelperTextColor(ColorStateList paramColorStateList)
  {
    indicatorViewController.setHelperTextViewTextColor(paramColorStateList);
  }
  
  public void setHelperTextEnabled(boolean paramBoolean)
  {
    indicatorViewController.setHelperTextEnabled(paramBoolean);
  }
  
  public void setHelperTextTextAppearance(int paramInt)
  {
    indicatorViewController.setHelperTextAppearance(paramInt);
  }
  
  public void setHint(CharSequence paramCharSequence)
  {
    if (hintEnabled)
    {
      setHintInternal(paramCharSequence);
      sendAccessibilityEvent(2048);
    }
  }
  
  public void setHintAnimationEnabled(boolean paramBoolean)
  {
    hintAnimationEnabled = paramBoolean;
  }
  
  public void setHintEnabled(boolean paramBoolean)
  {
    if (paramBoolean != hintEnabled)
    {
      hintEnabled = paramBoolean;
      if (!hintEnabled)
      {
        isProvidingHint = false;
        if ((!TextUtils.isEmpty(hint)) && (TextUtils.isEmpty(editText.getHint()))) {
          editText.setHint(hint);
        }
        setHintInternal(null);
      }
      else
      {
        CharSequence localCharSequence = editText.getHint();
        if (!TextUtils.isEmpty(localCharSequence))
        {
          if (TextUtils.isEmpty(hint)) {
            setHint(localCharSequence);
          }
          editText.setHint(null);
        }
        isProvidingHint = true;
      }
      if (editText != null) {
        updateInputLayoutMargins();
      }
    }
  }
  
  public void setHintTextAppearance(int paramInt)
  {
    collapsingTextHelper.setCollapsedTextAppearance(paramInt);
    focusedTextColor = collapsingTextHelper.getCollapsedTextColor();
    if (editText != null)
    {
      updateLabelState(false);
      updateInputLayoutMargins();
    }
  }
  
  public void setPasswordVisibilityToggleContentDescription(int paramInt)
  {
    CharSequence localCharSequence;
    if (paramInt != 0) {
      localCharSequence = getResources().getText(paramInt);
    } else {
      localCharSequence = null;
    }
    setPasswordVisibilityToggleContentDescription(localCharSequence);
  }
  
  public void setPasswordVisibilityToggleContentDescription(CharSequence paramCharSequence)
  {
    passwordToggleContentDesc = paramCharSequence;
    CheckableImageButton localCheckableImageButton = passwordToggleView;
    if (localCheckableImageButton != null) {
      localCheckableImageButton.setContentDescription(paramCharSequence);
    }
  }
  
  public void setPasswordVisibilityToggleDrawable(int paramInt)
  {
    Drawable localDrawable;
    if (paramInt != 0) {
      localDrawable = AppCompatResources.getDrawable(getContext(), paramInt);
    } else {
      localDrawable = null;
    }
    setPasswordVisibilityToggleDrawable(localDrawable);
  }
  
  public void setPasswordVisibilityToggleDrawable(Drawable paramDrawable)
  {
    passwordToggleDrawable = paramDrawable;
    CheckableImageButton localCheckableImageButton = passwordToggleView;
    if (localCheckableImageButton != null) {
      localCheckableImageButton.setImageDrawable(paramDrawable);
    }
  }
  
  public void setPasswordVisibilityToggleEnabled(boolean paramBoolean)
  {
    if (passwordToggleEnabled != paramBoolean)
    {
      passwordToggleEnabled = paramBoolean;
      if ((!paramBoolean) && (passwordToggledVisible))
      {
        EditText localEditText = editText;
        if (localEditText != null) {
          localEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
      }
      passwordToggledVisible = false;
      updatePasswordToggleView();
    }
  }
  
  public void setPasswordVisibilityToggleTintList(ColorStateList paramColorStateList)
  {
    passwordToggleTintList = paramColorStateList;
    hasPasswordToggleTintList = true;
    applyPasswordToggleTint();
  }
  
  public void setPasswordVisibilityToggleTintMode(PorterDuff.Mode paramMode)
  {
    passwordToggleTintMode = paramMode;
    hasPasswordToggleTintMode = true;
    applyPasswordToggleTint();
  }
  
  void setTextAppearanceCompatWithErrorFallback(TextView paramTextView, int paramInt)
  {
    int i = 1;
    try
    {
      TextViewCompat.setTextAppearance(paramTextView, paramInt);
      if (Build.VERSION.SDK_INT >= 23)
      {
        paramInt = paramTextView.getTextColors().getDefaultColor();
        if (paramInt == -65281)
        {
          paramInt = i;
          break label40;
        }
      }
      paramInt = 0;
    }
    catch (Exception localException)
    {
      for (;;)
      {
        label40:
        paramInt = i;
      }
    }
    if (paramInt != 0)
    {
      TextViewCompat.setTextAppearance(paramTextView, R.style.TextAppearance_AppCompat_Caption);
      paramTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.design_error));
      return;
    }
  }
  
  public void setTextInputAccessibilityDelegate(AccessibilityDelegate paramAccessibilityDelegate)
  {
    EditText localEditText = editText;
    if (localEditText != null) {
      ViewCompat.setAccessibilityDelegate(localEditText, paramAccessibilityDelegate);
    }
  }
  
  public void setTypeface(Typeface paramTypeface)
  {
    if (paramTypeface != typeface)
    {
      typeface = paramTypeface;
      collapsingTextHelper.setTypefaces(paramTypeface);
      indicatorViewController.setTypefaces(paramTypeface);
      TextView localTextView = counterView;
      if (localTextView != null) {
        localTextView.setTypeface(paramTypeface);
      }
    }
  }
  
  void updateCounter(int paramInt)
  {
    boolean bool2 = counterOverflowed;
    if (counterMaxLength == -1)
    {
      counterView.setText(String.valueOf(paramInt));
      counterView.setContentDescription(null);
      counterOverflowed = false;
    }
    else
    {
      if (ViewCompat.getAccessibilityLiveRegion(counterView) == 1) {
        ViewCompat.setAccessibilityLiveRegion(counterView, 0);
      }
      if (paramInt > counterMaxLength) {
        bool1 = true;
      } else {
        bool1 = false;
      }
      counterOverflowed = bool1;
      boolean bool1 = counterOverflowed;
      if (bool2 != bool1)
      {
        TextView localTextView = counterView;
        int i;
        if (bool1) {
          i = counterOverflowTextAppearance;
        } else {
          i = counterTextAppearance;
        }
        setTextAppearanceCompatWithErrorFallback(localTextView, i);
        if (counterOverflowed) {
          ViewCompat.setAccessibilityLiveRegion(counterView, 1);
        }
      }
      counterView.setText(getContext().getString(R.string.character_counter_pattern, new Object[] { Integer.valueOf(paramInt), Integer.valueOf(counterMaxLength) }));
      counterView.setContentDescription(getContext().getString(R.string.character_counter_content_description, new Object[] { Integer.valueOf(paramInt), Integer.valueOf(counterMaxLength) }));
    }
    if ((editText != null) && (bool2 != counterOverflowed))
    {
      updateLabelState(false);
      updateTextInputBoxState();
      updateEditTextBackground();
    }
  }
  
  void updateEditTextBackground()
  {
    Object localObject1 = editText;
    if (localObject1 == null) {
      return;
    }
    Object localObject2 = ((View)localObject1).getBackground();
    localObject1 = localObject2;
    if (localObject2 == null) {
      return;
    }
    ensureBackgroundDrawableStateWorkaround();
    if (androidx.appcompat.widget.DrawableUtils.canSafelyMutateDrawable((Drawable)localObject2)) {
      localObject1 = ((Drawable)localObject2).mutate();
    }
    if (indicatorViewController.errorShouldBeShown())
    {
      ((Drawable)localObject1).setColorFilter(AppCompatDrawableManager.getPorterDuffColorFilter(indicatorViewController.getErrorViewCurrentTextColor(), PorterDuff.Mode.SRC_IN));
      return;
    }
    if (counterOverflowed)
    {
      localObject2 = counterView;
      if (localObject2 != null)
      {
        ((Drawable)localObject1).setColorFilter(AppCompatDrawableManager.getPorterDuffColorFilter(((TextView)localObject2).getCurrentTextColor(), PorterDuff.Mode.SRC_IN));
        return;
      }
    }
    DrawableCompat.clearColorFilter((Drawable)localObject1);
    editText.refreshDrawableState();
  }
  
  void updateLabelState(boolean paramBoolean)
  {
    updateLabelState(paramBoolean, false);
  }
  
  void updateTextInputBoxState()
  {
    if (boxBackground != null)
    {
      if (boxBackgroundMode == 0) {
        return;
      }
      Object localObject = editText;
      int j = 1;
      int i;
      if ((localObject != null) && (((View)localObject).hasFocus())) {
        i = 1;
      } else {
        i = 0;
      }
      localObject = editText;
      if ((localObject == null) || (!((View)localObject).isHovered())) {
        j = 0;
      }
      if (boxBackgroundMode == 2)
      {
        if (!isEnabled())
        {
          boxStrokeColor = disabledColor;
        }
        else if (indicatorViewController.errorShouldBeShown())
        {
          boxStrokeColor = indicatorViewController.getErrorViewCurrentTextColor();
        }
        else
        {
          if (counterOverflowed)
          {
            localObject = counterView;
            if (localObject != null)
            {
              boxStrokeColor = ((TextView)localObject).getCurrentTextColor();
              break label176;
            }
          }
          if (i != 0) {
            boxStrokeColor = focusedStrokeColor;
          } else if (j != 0) {
            boxStrokeColor = hoveredStrokeColor;
          } else {
            boxStrokeColor = defaultStrokeColor;
          }
        }
        label176:
        if (((j != 0) || (i != 0)) && (isEnabled())) {
          boxStrokeWidthPx = boxStrokeWidthFocusedPx;
        } else {
          boxStrokeWidthPx = boxStrokeWidthDefaultPx;
        }
        applyBoxAttributes();
      }
    }
  }
  
  public static class AccessibilityDelegate
    extends AccessibilityDelegateCompat
  {
    private final TextInputLayout layout;
    
    public AccessibilityDelegate(TextInputLayout paramTextInputLayout)
    {
      layout = paramTextInputLayout;
    }
    
    public void onInitializeAccessibilityNodeInfo(View paramView, AccessibilityNodeInfoCompat paramAccessibilityNodeInfoCompat)
    {
      super.onInitializeAccessibilityNodeInfo(paramView, paramAccessibilityNodeInfoCompat);
      paramView = layout.getEditText();
      if (paramView != null) {
        paramView = paramView.getText();
      } else {
        paramView = null;
      }
      CharSequence localCharSequence4 = layout.getHint();
      CharSequence localCharSequence2 = layout.getError();
      CharSequence localCharSequence1 = localCharSequence2;
      CharSequence localCharSequence3 = layout.getCounterOverflowDescription();
      boolean bool1 = TextUtils.isEmpty(paramView) ^ true;
      boolean bool2 = TextUtils.isEmpty(localCharSequence4) ^ true;
      boolean bool3 = TextUtils.isEmpty(localCharSequence2) ^ true;
      boolean bool5 = false;
      int i;
      if ((!bool3) && (TextUtils.isEmpty(localCharSequence3))) {
        i = 0;
      } else {
        i = 1;
      }
      if (bool1) {
        paramAccessibilityNodeInfoCompat.setText(paramView);
      } else if (bool2) {
        paramAccessibilityNodeInfoCompat.setText(localCharSequence4);
      }
      if (bool2)
      {
        paramAccessibilityNodeInfoCompat.setHintText(localCharSequence4);
        boolean bool4 = bool5;
        if (!bool1)
        {
          bool4 = bool5;
          if (bool2) {
            bool4 = true;
          }
        }
        paramAccessibilityNodeInfoCompat.setShowingHintText(bool4);
      }
      if (i != 0)
      {
        if (bool3) {
          paramView = localCharSequence1;
        } else {
          paramView = localCharSequence3;
        }
        paramAccessibilityNodeInfoCompat.setError(paramView);
        paramAccessibilityNodeInfoCompat.setContentInvalid(true);
      }
    }
    
    public void onPopulateAccessibilityEvent(View paramView, AccessibilityEvent paramAccessibilityEvent)
    {
      super.onPopulateAccessibilityEvent(paramView, paramAccessibilityEvent);
      paramView = layout.getEditText();
      if (paramView != null) {
        paramView = paramView.getText();
      } else {
        paramView = null;
      }
      Object localObject = paramView;
      if (TextUtils.isEmpty((CharSequence)paramView)) {
        localObject = layout.getHint();
      }
      if (!TextUtils.isEmpty((CharSequence)localObject)) {
        paramAccessibilityEvent.getText().add(localObject);
      }
    }
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface BoxBackgroundMode {}
  
  static class SavedState
    extends AbsSavedState
  {
    public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator()
    {
      public TextInputLayout.SavedState createFromParcel(Parcel paramAnonymousParcel)
      {
        return new TextInputLayout.SavedState(paramAnonymousParcel, null);
      }
      
      public TextInputLayout.SavedState createFromParcel(Parcel paramAnonymousParcel, ClassLoader paramAnonymousClassLoader)
      {
        return new TextInputLayout.SavedState(paramAnonymousParcel, paramAnonymousClassLoader);
      }
      
      public TextInputLayout.SavedState[] newArray(int paramAnonymousInt)
      {
        return new TextInputLayout.SavedState[paramAnonymousInt];
      }
    };
    CharSequence error;
    boolean isPasswordToggledVisible;
    
    SavedState(Parcel paramParcel, ClassLoader paramClassLoader)
    {
      super(paramClassLoader);
      error = ((CharSequence)TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(paramParcel));
      int i = paramParcel.readInt();
      boolean bool = true;
      if (i != 1) {
        bool = false;
      }
      isPasswordToggledVisible = bool;
    }
    
    SavedState(Parcelable paramParcelable)
    {
      super();
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("TextInputLayout.SavedState{");
      localStringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
      localStringBuilder.append(" error=");
      localStringBuilder.append(error);
      localStringBuilder.append("}");
      return localStringBuilder.toString();
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      throw new Runtime("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.provideAs(TypeTransformer.java:780)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.e1expr(TypeTransformer.java:496)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:713)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:698)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.s1stmt(TypeTransformer.java:810)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.sxStmt(TypeTransformer.java:840)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:206)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\n");
    }
  }
}
