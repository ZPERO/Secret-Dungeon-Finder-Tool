package com.google.android.material.floatingactionbutton;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Matrix;
import android.graphics.Matrix.ScaleToFit;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build.VERSION;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.ImageView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;
import com.google.android.material.R.animator;
import com.google.android.material.R.color;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.animation.AnimatorSetCompat;
import com.google.android.material.animation.ImageMatrixProperty;
import com.google.android.material.animation.MatrixEvaluator;
import com.google.android.material.animation.MotionSpec;
import com.google.android.material.animation.MotionTiming;
import com.google.android.material.internal.CircularBorderDrawable;
import com.google.android.material.internal.StateListAnimator;
import com.google.android.material.internal.VisibilityAwareImageButton;
import com.google.android.material.ripple.RippleUtils;
import com.google.android.material.shadow.ShadowDrawableWrapper;
import com.google.android.material.shadow.ShadowViewDelegate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class FloatingActionButtonImpl
{
  static final int ANIM_STATE_HIDING = 1;
  static final int ANIM_STATE_NONE = 0;
  static final int ANIM_STATE_SHOWING = 2;
  static final long ELEVATION_ANIM_DELAY = 100L;
  static final long ELEVATION_ANIM_DURATION = 100L;
  static final TimeInterpolator ELEVATION_ANIM_INTERPOLATOR = AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR;
  static final int[] EMPTY_STATE_SET = new int[0];
  static final int[] ENABLED_STATE_SET;
  static final int[] FOCUSED_ENABLED_STATE_SET;
  private static final float HIDE_ICON_SCALE = 0.0F;
  private static final float HIDE_OPACITY = 0.0F;
  private static final float HIDE_SCALE = 0.0F;
  static final int[] HOVERED_ENABLED_STATE_SET;
  static final int[] HOVERED_FOCUSED_ENABLED_STATE_SET;
  static final int[] PRESSED_ENABLED_STATE_SET = { 16842919, 16842910 };
  private static final float SHOW_ICON_SCALE = 1.0F;
  private static final float SHOW_OPACITY = 1.0F;
  private static final float SHOW_SCALE = 1.0F;
  int animState = 0;
  CircularBorderDrawable borderDrawable;
  Drawable contentBackground;
  Animator currentAnimator;
  private MotionSpec defaultHideMotionSpec;
  private MotionSpec defaultShowMotionSpec;
  float elevation;
  private ArrayList<Animator.AnimatorListener> hideListeners;
  MotionSpec hideMotionSpec;
  float hoveredFocusedTranslationZ;
  float imageMatrixScale = 1.0F;
  int maxImageSize;
  private ViewTreeObserver.OnPreDrawListener preDrawListener;
  float pressedTranslationZ;
  Drawable rippleDrawable;
  private float rotation;
  ShadowDrawableWrapper shadowDrawable;
  final ShadowViewDelegate shadowViewDelegate;
  Drawable shapeDrawable;
  private ArrayList<Animator.AnimatorListener> showListeners;
  MotionSpec showMotionSpec;
  private final StateListAnimator stateListAnimator;
  private final Matrix tmpMatrix = new Matrix();
  private final Rect tmpRect = new Rect();
  private final RectF tmpRectF1 = new RectF();
  private final RectF tmpRectF2 = new RectF();
  final VisibilityAwareImageButton view;
  
  static
  {
    HOVERED_FOCUSED_ENABLED_STATE_SET = new int[] { 16843623, 16842908, 16842910 };
    FOCUSED_ENABLED_STATE_SET = new int[] { 16842908, 16842910 };
    HOVERED_ENABLED_STATE_SET = new int[] { 16843623, 16842910 };
    ENABLED_STATE_SET = new int[] { 16842910 };
  }
  
  FloatingActionButtonImpl(VisibilityAwareImageButton paramVisibilityAwareImageButton, ShadowViewDelegate paramShadowViewDelegate)
  {
    view = paramVisibilityAwareImageButton;
    shadowViewDelegate = paramShadowViewDelegate;
    stateListAnimator = new StateListAnimator();
    stateListAnimator.addState(PRESSED_ENABLED_STATE_SET, createElevationAnimator(new ElevateToPressedTranslationZAnimation()));
    stateListAnimator.addState(HOVERED_FOCUSED_ENABLED_STATE_SET, createElevationAnimator(new ElevateToHoveredFocusedTranslationZAnimation()));
    stateListAnimator.addState(FOCUSED_ENABLED_STATE_SET, createElevationAnimator(new ElevateToHoveredFocusedTranslationZAnimation()));
    stateListAnimator.addState(HOVERED_ENABLED_STATE_SET, createElevationAnimator(new ElevateToHoveredFocusedTranslationZAnimation()));
    stateListAnimator.addState(ENABLED_STATE_SET, createElevationAnimator(new ResetElevationAnimation()));
    stateListAnimator.addState(EMPTY_STATE_SET, createElevationAnimator(new DisabledElevationAnimation()));
    rotation = view.getRotation();
  }
  
  private void calculateImageMatrixFromScale(float paramFloat, Matrix paramMatrix)
  {
    paramMatrix.reset();
    Drawable localDrawable = view.getDrawable();
    if ((localDrawable != null) && (maxImageSize != 0))
    {
      RectF localRectF1 = tmpRectF1;
      RectF localRectF2 = tmpRectF2;
      localRectF1.set(0.0F, 0.0F, localDrawable.getIntrinsicWidth(), localDrawable.getIntrinsicHeight());
      int i = maxImageSize;
      localRectF2.set(0.0F, 0.0F, i, i);
      paramMatrix.setRectToRect(localRectF1, localRectF2, Matrix.ScaleToFit.CENTER);
      i = maxImageSize;
      paramMatrix.postScale(paramFloat, paramFloat, i / 2.0F, i / 2.0F);
    }
  }
  
  private AnimatorSet createAnimator(MotionSpec paramMotionSpec, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    ArrayList localArrayList = new ArrayList();
    ObjectAnimator localObjectAnimator = ObjectAnimator.ofFloat(view, View.ALPHA, new float[] { paramFloat1 });
    paramMotionSpec.getTiming("opacity").apply(localObjectAnimator);
    localArrayList.add(localObjectAnimator);
    localObjectAnimator = ObjectAnimator.ofFloat(view, View.SCALE_X, new float[] { paramFloat2 });
    paramMotionSpec.getTiming("scale").apply(localObjectAnimator);
    localArrayList.add(localObjectAnimator);
    localObjectAnimator = ObjectAnimator.ofFloat(view, View.SCALE_Y, new float[] { paramFloat2 });
    paramMotionSpec.getTiming("scale").apply(localObjectAnimator);
    localArrayList.add(localObjectAnimator);
    calculateImageMatrixFromScale(paramFloat3, tmpMatrix);
    localObjectAnimator = ObjectAnimator.ofObject(view, new ImageMatrixProperty(), new MatrixEvaluator(), new Matrix[] { new Matrix(tmpMatrix) });
    paramMotionSpec.getTiming("iconScale").apply(localObjectAnimator);
    localArrayList.add(localObjectAnimator);
    paramMotionSpec = new AnimatorSet();
    AnimatorSetCompat.playTogether(paramMotionSpec, localArrayList);
    return paramMotionSpec;
  }
  
  private ValueAnimator createElevationAnimator(ShadowAnimatorImpl paramShadowAnimatorImpl)
  {
    ValueAnimator localValueAnimator = new ValueAnimator();
    localValueAnimator.setInterpolator(ELEVATION_ANIM_INTERPOLATOR);
    localValueAnimator.setDuration(100L);
    localValueAnimator.addListener(paramShadowAnimatorImpl);
    localValueAnimator.addUpdateListener(paramShadowAnimatorImpl);
    localValueAnimator.setFloatValues(new float[] { 0.0F, 1.0F });
    return localValueAnimator;
  }
  
  private void ensurePreDrawListener()
  {
    if (preDrawListener == null) {
      preDrawListener = new ViewTreeObserver.OnPreDrawListener()
      {
        public boolean onPreDraw()
        {
          onPreDraw();
          return true;
        }
      };
    }
  }
  
  private MotionSpec getDefaultHideMotionSpec()
  {
    if (defaultHideMotionSpec == null) {
      defaultHideMotionSpec = MotionSpec.createFromResource(view.getContext(), R.animator.design_fab_hide_motion_spec);
    }
    return defaultHideMotionSpec;
  }
  
  private MotionSpec getDefaultShowMotionSpec()
  {
    if (defaultShowMotionSpec == null) {
      defaultShowMotionSpec = MotionSpec.createFromResource(view.getContext(), R.animator.design_fab_show_motion_spec);
    }
    return defaultShowMotionSpec;
  }
  
  private boolean shouldAnimateVisibilityChange()
  {
    return (ViewCompat.isLaidOut(view)) && (!view.isInEditMode());
  }
  
  private void updateFromViewRotation()
  {
    if (Build.VERSION.SDK_INT == 19) {
      if (rotation % 90.0F != 0.0F)
      {
        if (view.getLayerType() != 1) {
          view.setLayerType(1, null);
        }
      }
      else if (view.getLayerType() != 0) {
        view.setLayerType(0, null);
      }
    }
    Object localObject = shadowDrawable;
    if (localObject != null) {
      ((ShadowDrawableWrapper)localObject).setRotation(-rotation);
    }
    localObject = borderDrawable;
    if (localObject != null) {
      ((CircularBorderDrawable)localObject).setRotation(-rotation);
    }
  }
  
  public void addOnHideAnimationListener(Animator.AnimatorListener paramAnimatorListener)
  {
    if (hideListeners == null) {
      hideListeners = new ArrayList();
    }
    hideListeners.add(paramAnimatorListener);
  }
  
  void addOnShowAnimationListener(Animator.AnimatorListener paramAnimatorListener)
  {
    if (showListeners == null) {
      showListeners = new ArrayList();
    }
    showListeners.add(paramAnimatorListener);
  }
  
  CircularBorderDrawable createBorderDrawable(int paramInt, ColorStateList paramColorStateList)
  {
    Context localContext = view.getContext();
    CircularBorderDrawable localCircularBorderDrawable = newCircularDrawable();
    localCircularBorderDrawable.setGradientColors(ContextCompat.getColor(localContext, R.color.design_fab_stroke_top_outer_color), ContextCompat.getColor(localContext, R.color.design_fab_stroke_top_inner_color), ContextCompat.getColor(localContext, R.color.design_fab_stroke_end_inner_color), ContextCompat.getColor(localContext, R.color.design_fab_stroke_end_outer_color));
    localCircularBorderDrawable.setBorderWidth(paramInt);
    localCircularBorderDrawable.setBorderTint(paramColorStateList);
    return localCircularBorderDrawable;
  }
  
  GradientDrawable createShapeDrawable()
  {
    GradientDrawable localGradientDrawable = newGradientDrawableForShape();
    localGradientDrawable.setShape(1);
    localGradientDrawable.setColor(-1);
    return localGradientDrawable;
  }
  
  final Drawable getContentBackground()
  {
    return contentBackground;
  }
  
  float getElevation()
  {
    return elevation;
  }
  
  final MotionSpec getHideMotionSpec()
  {
    return hideMotionSpec;
  }
  
  float getHoveredFocusedTranslationZ()
  {
    return hoveredFocusedTranslationZ;
  }
  
  void getPadding(Rect paramRect)
  {
    shadowDrawable.getPadding(paramRect);
  }
  
  float getPressedTranslationZ()
  {
    return pressedTranslationZ;
  }
  
  final MotionSpec getShowMotionSpec()
  {
    return showMotionSpec;
  }
  
  void hide(final InternalVisibilityChangedListener paramInternalVisibilityChangedListener, final boolean paramBoolean)
  {
    if (isOrWillBeHidden()) {
      return;
    }
    Object localObject = currentAnimator;
    if (localObject != null) {
      ((Animator)localObject).cancel();
    }
    if (shouldAnimateVisibilityChange())
    {
      localObject = hideMotionSpec;
      if (localObject == null) {
        localObject = getDefaultHideMotionSpec();
      }
      localObject = createAnimator((MotionSpec)localObject, 0.0F, 0.0F, 0.0F);
      ((Animator)localObject).addListener(new AnimatorListenerAdapter()
      {
        private boolean cancelled;
        
        public void onAnimationCancel(Animator paramAnonymousAnimator)
        {
          cancelled = true;
        }
        
        public void onAnimationEnd(Animator paramAnonymousAnimator)
        {
          paramAnonymousAnimator = FloatingActionButtonImpl.this;
          animState = 0;
          currentAnimator = null;
          if (!cancelled)
          {
            paramAnonymousAnimator = view;
            int i;
            if (paramBoolean) {
              i = 8;
            } else {
              i = 4;
            }
            paramAnonymousAnimator.internalSetVisibility(i, paramBoolean);
            paramAnonymousAnimator = paramInternalVisibilityChangedListener;
            if (paramAnonymousAnimator != null) {
              paramAnonymousAnimator.onHidden();
            }
          }
        }
        
        public void onAnimationStart(Animator paramAnonymousAnimator)
        {
          view.internalSetVisibility(0, paramBoolean);
          FloatingActionButtonImpl localFloatingActionButtonImpl = FloatingActionButtonImpl.this;
          animState = 1;
          currentAnimator = paramAnonymousAnimator;
          cancelled = false;
        }
      });
      paramInternalVisibilityChangedListener = hideListeners;
      if (paramInternalVisibilityChangedListener != null)
      {
        paramInternalVisibilityChangedListener = paramInternalVisibilityChangedListener.iterator();
        while (paramInternalVisibilityChangedListener.hasNext()) {
          ((Animator)localObject).addListener((Animator.AnimatorListener)paramInternalVisibilityChangedListener.next());
        }
      }
      ((AnimatorSet)localObject).start();
      return;
    }
    localObject = view;
    int i;
    if (paramBoolean) {
      i = 8;
    } else {
      i = 4;
    }
    ((VisibilityAwareImageButton)localObject).internalSetVisibility(i, paramBoolean);
    if (paramInternalVisibilityChangedListener != null) {
      paramInternalVisibilityChangedListener.onHidden();
    }
  }
  
  boolean isOrWillBeHidden()
  {
    if (view.getVisibility() == 0)
    {
      if (animState == 1) {
        return true;
      }
    }
    else if (animState != 2) {
      return true;
    }
    return false;
  }
  
  boolean isOrWillBeShown()
  {
    if (view.getVisibility() != 0)
    {
      if (animState == 2) {
        return true;
      }
    }
    else if (animState != 1) {
      return true;
    }
    return false;
  }
  
  void jumpDrawableToCurrentState()
  {
    stateListAnimator.jumpToCurrentState();
  }
  
  CircularBorderDrawable newCircularDrawable()
  {
    return new CircularBorderDrawable();
  }
  
  GradientDrawable newGradientDrawableForShape()
  {
    return new GradientDrawable();
  }
  
  void onAttachedToWindow()
  {
    if (requirePreDrawListener())
    {
      ensurePreDrawListener();
      view.getViewTreeObserver().addOnPreDrawListener(preDrawListener);
    }
  }
  
  void onCompatShadowChanged() {}
  
  void onDetachedFromWindow()
  {
    if (preDrawListener != null)
    {
      view.getViewTreeObserver().removeOnPreDrawListener(preDrawListener);
      preDrawListener = null;
    }
  }
  
  void onDrawableStateChanged(int[] paramArrayOfInt)
  {
    stateListAnimator.setState(paramArrayOfInt);
  }
  
  void onElevationsChanged(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    ShadowDrawableWrapper localShadowDrawableWrapper = shadowDrawable;
    if (localShadowDrawableWrapper != null)
    {
      localShadowDrawableWrapper.setShadowSize(paramFloat1, pressedTranslationZ + paramFloat1);
      updatePadding();
    }
  }
  
  void onPaddingUpdated(Rect paramRect) {}
  
  void onPreDraw()
  {
    float f = view.getRotation();
    if (rotation != f)
    {
      rotation = f;
      updateFromViewRotation();
    }
  }
  
  public void removeOnHideAnimationListener(Animator.AnimatorListener paramAnimatorListener)
  {
    ArrayList localArrayList = hideListeners;
    if (localArrayList == null) {
      return;
    }
    localArrayList.remove(paramAnimatorListener);
  }
  
  void removeOnShowAnimationListener(Animator.AnimatorListener paramAnimatorListener)
  {
    ArrayList localArrayList = showListeners;
    if (localArrayList == null) {
      return;
    }
    localArrayList.remove(paramAnimatorListener);
  }
  
  boolean requirePreDrawListener()
  {
    return true;
  }
  
  void setBackgroundDrawable(ColorStateList paramColorStateList1, PorterDuff.Mode paramMode, ColorStateList paramColorStateList2, int paramInt)
  {
    shapeDrawable = DrawableCompat.wrap(createShapeDrawable());
    DrawableCompat.setTintList(shapeDrawable, paramColorStateList1);
    if (paramMode != null) {
      DrawableCompat.setTintMode(shapeDrawable, paramMode);
    }
    rippleDrawable = DrawableCompat.wrap(createShapeDrawable());
    DrawableCompat.setTintList(rippleDrawable, RippleUtils.convertToRippleDrawableColor(paramColorStateList2));
    if (paramInt > 0)
    {
      borderDrawable = createBorderDrawable(paramInt, paramColorStateList1);
      paramColorStateList1 = new Drawable[3];
      paramColorStateList1[0] = borderDrawable;
      paramColorStateList1[1] = shapeDrawable;
      paramColorStateList1[2] = rippleDrawable;
    }
    else
    {
      borderDrawable = null;
      paramColorStateList1 = new Drawable[2];
      paramColorStateList1[0] = shapeDrawable;
      paramColorStateList1[1] = rippleDrawable;
    }
    contentBackground = new LayerDrawable(paramColorStateList1);
    paramColorStateList1 = view.getContext();
    paramMode = contentBackground;
    float f1 = shadowViewDelegate.getRadius();
    float f2 = elevation;
    shadowDrawable = new ShadowDrawableWrapper(paramColorStateList1, paramMode, f1, f2, f2 + pressedTranslationZ);
    shadowDrawable.setAddPaddingForCorners(false);
    shadowViewDelegate.setBackgroundDrawable(shadowDrawable);
  }
  
  void setBackgroundTintList(ColorStateList paramColorStateList)
  {
    Object localObject = shapeDrawable;
    if (localObject != null) {
      DrawableCompat.setTintList((Drawable)localObject, paramColorStateList);
    }
    localObject = borderDrawable;
    if (localObject != null) {
      ((CircularBorderDrawable)localObject).setBorderTint(paramColorStateList);
    }
  }
  
  void setBackgroundTintMode(PorterDuff.Mode paramMode)
  {
    Drawable localDrawable = shapeDrawable;
    if (localDrawable != null) {
      DrawableCompat.setTintMode(localDrawable, paramMode);
    }
  }
  
  final void setElevation(float paramFloat)
  {
    if (elevation != paramFloat)
    {
      elevation = paramFloat;
      onElevationsChanged(elevation, hoveredFocusedTranslationZ, pressedTranslationZ);
    }
  }
  
  final void setHideMotionSpec(MotionSpec paramMotionSpec)
  {
    hideMotionSpec = paramMotionSpec;
  }
  
  final void setHoveredFocusedTranslationZ(float paramFloat)
  {
    if (hoveredFocusedTranslationZ != paramFloat)
    {
      hoveredFocusedTranslationZ = paramFloat;
      onElevationsChanged(elevation, hoveredFocusedTranslationZ, pressedTranslationZ);
    }
  }
  
  final void setImageMatrixScale(float paramFloat)
  {
    imageMatrixScale = paramFloat;
    Matrix localMatrix = tmpMatrix;
    calculateImageMatrixFromScale(paramFloat, localMatrix);
    view.setImageMatrix(localMatrix);
  }
  
  final void setMaxImageSize(int paramInt)
  {
    if (maxImageSize != paramInt)
    {
      maxImageSize = paramInt;
      updateImageMatrixScale();
    }
  }
  
  final void setPressedTranslationZ(float paramFloat)
  {
    if (pressedTranslationZ != paramFloat)
    {
      pressedTranslationZ = paramFloat;
      onElevationsChanged(elevation, hoveredFocusedTranslationZ, pressedTranslationZ);
    }
  }
  
  void setRippleColor(ColorStateList paramColorStateList)
  {
    Drawable localDrawable = rippleDrawable;
    if (localDrawable != null) {
      DrawableCompat.setTintList(localDrawable, RippleUtils.convertToRippleDrawableColor(paramColorStateList));
    }
  }
  
  final void setShowMotionSpec(MotionSpec paramMotionSpec)
  {
    showMotionSpec = paramMotionSpec;
  }
  
  void show(final InternalVisibilityChangedListener paramInternalVisibilityChangedListener, final boolean paramBoolean)
  {
    if (isOrWillBeShown()) {
      return;
    }
    Object localObject = currentAnimator;
    if (localObject != null) {
      ((Animator)localObject).cancel();
    }
    if (shouldAnimateVisibilityChange())
    {
      if (view.getVisibility() != 0)
      {
        view.setAlpha(0.0F);
        view.setScaleY(0.0F);
        view.setScaleX(0.0F);
        setImageMatrixScale(0.0F);
      }
      localObject = showMotionSpec;
      if (localObject == null) {
        localObject = getDefaultShowMotionSpec();
      }
      localObject = createAnimator((MotionSpec)localObject, 1.0F, 1.0F, 1.0F);
      ((Animator)localObject).addListener(new AnimatorListenerAdapter()
      {
        public void onAnimationEnd(Animator paramAnonymousAnimator)
        {
          paramAnonymousAnimator = FloatingActionButtonImpl.this;
          animState = 0;
          currentAnimator = null;
          paramAnonymousAnimator = paramInternalVisibilityChangedListener;
          if (paramAnonymousAnimator != null) {
            paramAnonymousAnimator.onShown();
          }
        }
        
        public void onAnimationStart(Animator paramAnonymousAnimator)
        {
          view.internalSetVisibility(0, paramBoolean);
          FloatingActionButtonImpl localFloatingActionButtonImpl = FloatingActionButtonImpl.this;
          animState = 2;
          currentAnimator = paramAnonymousAnimator;
        }
      });
      paramInternalVisibilityChangedListener = showListeners;
      if (paramInternalVisibilityChangedListener != null)
      {
        paramInternalVisibilityChangedListener = paramInternalVisibilityChangedListener.iterator();
        while (paramInternalVisibilityChangedListener.hasNext()) {
          ((Animator)localObject).addListener((Animator.AnimatorListener)paramInternalVisibilityChangedListener.next());
        }
      }
      ((AnimatorSet)localObject).start();
      return;
    }
    view.internalSetVisibility(0, paramBoolean);
    view.setAlpha(1.0F);
    view.setScaleY(1.0F);
    view.setScaleX(1.0F);
    setImageMatrixScale(1.0F);
    if (paramInternalVisibilityChangedListener != null) {
      paramInternalVisibilityChangedListener.onShown();
    }
  }
  
  final void updateImageMatrixScale()
  {
    setImageMatrixScale(imageMatrixScale);
  }
  
  final void updatePadding()
  {
    Rect localRect = tmpRect;
    getPadding(localRect);
    onPaddingUpdated(localRect);
    shadowViewDelegate.setShadowPadding(left, top, right, bottom);
  }
  
  private class DisabledElevationAnimation
    extends FloatingActionButtonImpl.ShadowAnimatorImpl
  {
    DisabledElevationAnimation()
    {
      super(null);
    }
    
    protected float getTargetShadowSize()
    {
      return 0.0F;
    }
  }
  
  private class ElevateToHoveredFocusedTranslationZAnimation
    extends FloatingActionButtonImpl.ShadowAnimatorImpl
  {
    ElevateToHoveredFocusedTranslationZAnimation()
    {
      super(null);
    }
    
    protected float getTargetShadowSize()
    {
      return elevation + hoveredFocusedTranslationZ;
    }
  }
  
  private class ElevateToPressedTranslationZAnimation
    extends FloatingActionButtonImpl.ShadowAnimatorImpl
  {
    ElevateToPressedTranslationZAnimation()
    {
      super(null);
    }
    
    protected float getTargetShadowSize()
    {
      return elevation + pressedTranslationZ;
    }
  }
  
  static abstract interface InternalVisibilityChangedListener
  {
    public abstract void onHidden();
    
    public abstract void onShown();
  }
  
  private class ResetElevationAnimation
    extends FloatingActionButtonImpl.ShadowAnimatorImpl
  {
    ResetElevationAnimation()
    {
      super(null);
    }
    
    protected float getTargetShadowSize()
    {
      return elevation;
    }
  }
  
  private abstract class ShadowAnimatorImpl
    extends AnimatorListenerAdapter
    implements ValueAnimator.AnimatorUpdateListener
  {
    private float shadowSizeEnd;
    private float shadowSizeStart;
    private boolean validValues;
    
    private ShadowAnimatorImpl() {}
    
    protected abstract float getTargetShadowSize();
    
    public void onAnimationEnd(Animator paramAnimator)
    {
      shadowDrawable.setShadowSize(shadowSizeEnd);
      validValues = false;
    }
    
    public void onAnimationUpdate(ValueAnimator paramValueAnimator)
    {
      if (!validValues)
      {
        shadowSizeStart = shadowDrawable.getShadowSize();
        shadowSizeEnd = getTargetShadowSize();
        validValues = true;
      }
      ShadowDrawableWrapper localShadowDrawableWrapper = shadowDrawable;
      float f = shadowSizeStart;
      localShadowDrawableWrapper.setShadowSize(f + (shadowSizeEnd - f) * paramValueAnimator.getAnimatedFraction());
    }
  }
}
