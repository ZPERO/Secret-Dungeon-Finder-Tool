package com.google.android.material.bottomappbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.ClassLoaderCreator;
import android.os.Parcelable.Creator;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import androidx.appcompat.app.ActionBar.LayoutParams;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.Toolbar.LayoutParams;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout.AttachedBehavior;
import androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior;
import androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;
import androidx.customview.view.AbsSavedState;
import com.google.android.material.R.attr;
import com.google.android.material.R.dimen;
import com.google.android.material.R.style;
import com.google.android.material.R.styleable;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.behavior.HideBottomViewOnScrollBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapePathModel;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class BottomAppBar
  extends Toolbar
  implements CoordinatorLayout.AttachedBehavior
{
  private static final long ANIMATION_DURATION = 300L;
  public static final int FAB_ALIGNMENT_MODE_CENTER = 0;
  public static final int FAB_ALIGNMENT_MODE_END = 1;
  private Animator attachAnimator;
  private int fabAlignmentMode;
  AnimatorListenerAdapter fabAnimationListener = new AnimatorListenerAdapter()
  {
    public void onAnimationStart(Animator paramAnonymousAnimator)
    {
      paramAnonymousAnimator = BottomAppBar.this;
      paramAnonymousAnimator.maybeAnimateAttachChange(fabAttached);
      paramAnonymousAnimator = BottomAppBar.this;
      paramAnonymousAnimator.maybeAnimateMenuView(fabAlignmentMode, fabAttached);
    }
  };
  private boolean fabAttached = true;
  private final int fabOffsetEndMode;
  private boolean hideOnScroll;
  private final MaterialShapeDrawable materialShapeDrawable;
  private Animator menuAnimator;
  private Animator modeAnimator;
  private final BottomAppBarTopEdgeTreatment topEdgeTreatment;
  
  public BottomAppBar(Context paramContext)
  {
    this(paramContext, null, 0);
  }
  
  public BottomAppBar(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, R.attr.bottomAppBarStyle);
  }
  
  public BottomAppBar(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    paramAttributeSet = ThemeEnforcement.obtainStyledAttributes(paramContext, paramAttributeSet, R.styleable.BottomAppBar, paramInt, R.style.Widget_MaterialComponents_BottomAppBar, new int[0]);
    paramContext = MaterialResources.getColorStateList(paramContext, paramAttributeSet, R.styleable.BottomAppBar_backgroundTint);
    float f1 = paramAttributeSet.getDimensionPixelOffset(R.styleable.BottomAppBar_fabCradleMargin, 0);
    float f2 = paramAttributeSet.getDimensionPixelOffset(R.styleable.BottomAppBar_fabCradleRoundedCornerRadius, 0);
    float f3 = paramAttributeSet.getDimensionPixelOffset(R.styleable.BottomAppBar_fabCradleVerticalOffset, 0);
    fabAlignmentMode = paramAttributeSet.getInt(R.styleable.BottomAppBar_fabAlignmentMode, 0);
    hideOnScroll = paramAttributeSet.getBoolean(R.styleable.BottomAppBar_hideOnScroll, false);
    paramAttributeSet.recycle();
    fabOffsetEndMode = getResources().getDimensionPixelOffset(R.dimen.mtrl_bottomappbar_fabOffsetEndMode);
    topEdgeTreatment = new BottomAppBarTopEdgeTreatment(f1, f2, f3);
    paramAttributeSet = new ShapePathModel();
    paramAttributeSet.setTopEdge(topEdgeTreatment);
    materialShapeDrawable = new MaterialShapeDrawable(paramAttributeSet);
    materialShapeDrawable.setShadowEnabled(true);
    materialShapeDrawable.setPaintStyle(Paint.Style.FILL);
    DrawableCompat.setTintList(materialShapeDrawable, paramContext);
    ViewCompat.setBackground(this, materialShapeDrawable);
  }
  
  private void addFabAnimationListeners(FloatingActionButton paramFloatingActionButton)
  {
    removeFabAnimationListeners(paramFloatingActionButton);
    paramFloatingActionButton.addOnHideAnimationListener(fabAnimationListener);
    paramFloatingActionButton.addOnShowAnimationListener(fabAnimationListener);
  }
  
  private void cancelAnimations()
  {
    Animator localAnimator = attachAnimator;
    if (localAnimator != null) {
      localAnimator.cancel();
    }
    localAnimator = menuAnimator;
    if (localAnimator != null) {
      localAnimator.cancel();
    }
    localAnimator = modeAnimator;
    if (localAnimator != null) {
      localAnimator.cancel();
    }
  }
  
  private void createCradleShapeAnimation(boolean paramBoolean, List paramList)
  {
    if (paramBoolean) {
      topEdgeTreatment.setHorizontalOffset(getFabTranslationX());
    }
    float f2 = materialShapeDrawable.getInterpolation();
    float f1;
    if (paramBoolean) {
      f1 = 1.0F;
    } else {
      f1 = 0.0F;
    }
    ValueAnimator localValueAnimator = ValueAnimator.ofFloat(new float[] { f2, f1 });
    localValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
    {
      public void onAnimationUpdate(ValueAnimator paramAnonymousValueAnimator)
      {
        materialShapeDrawable.setInterpolation(((Float)paramAnonymousValueAnimator.getAnimatedValue()).floatValue());
      }
    });
    localValueAnimator.setDuration(300L);
    paramList.add(localValueAnimator);
  }
  
  private void createCradleTranslationAnimation(int paramInt, List paramList)
  {
    if (!fabAttached) {
      return;
    }
    ValueAnimator localValueAnimator = ValueAnimator.ofFloat(new float[] { topEdgeTreatment.getHorizontalOffset(), getFabTranslationX(paramInt) });
    localValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
    {
      public void onAnimationUpdate(ValueAnimator paramAnonymousValueAnimator)
      {
        topEdgeTreatment.setHorizontalOffset(((Float)paramAnonymousValueAnimator.getAnimatedValue()).floatValue());
        materialShapeDrawable.invalidateSelf();
      }
    });
    localValueAnimator.setDuration(300L);
    paramList.add(localValueAnimator);
  }
  
  private void createFabTranslationXAnimation(int paramInt, List paramList)
  {
    ObjectAnimator localObjectAnimator = ObjectAnimator.ofFloat(findDependentFab(), "translationX", new float[] { getFabTranslationX(paramInt) });
    localObjectAnimator.setDuration(300L);
    paramList.add(localObjectAnimator);
  }
  
  private void createFabTranslationYAnimation(boolean paramBoolean, List paramList)
  {
    Object localObject = findDependentFab();
    if (localObject == null) {
      return;
    }
    localObject = ObjectAnimator.ofFloat(localObject, "translationY", new float[] { getFabTranslationY(paramBoolean) });
    ((ObjectAnimator)localObject).setDuration(300L);
    paramList.add(localObject);
  }
  
  private void createMenuViewTranslationAnimation(final int paramInt, final boolean paramBoolean, List paramList)
  {
    Object localObject = getActionMenuView();
    if (localObject == null) {
      return;
    }
    ObjectAnimator localObjectAnimator1 = ObjectAnimator.ofFloat(localObject, "alpha", new float[] { 1.06535322E9F });
    if (((!fabAttached) && ((!paramBoolean) || (!isVisibleFab()))) || ((fabAlignmentMode != 1) && (paramInt != 1)))
    {
      if (((View)localObject).getAlpha() < 1.0F) {
        paramList.add(localObjectAnimator1);
      }
    }
    else
    {
      ObjectAnimator localObjectAnimator2 = ObjectAnimator.ofFloat(localObject, "alpha", new float[] { 0.0F });
      localObjectAnimator2.addListener(new AnimatorListenerAdapter()
      {
        public boolean cancelled;
        
        public void onAnimationCancel(Animator paramAnonymousAnimator)
        {
          cancelled = true;
        }
        
        public void onAnimationEnd(Animator paramAnonymousAnimator)
        {
          if (!cancelled) {
            BottomAppBar.this.translateActionMenuView(val$actionMenuView, paramInt, paramBoolean);
          }
        }
      });
      localObject = new AnimatorSet();
      ((AnimatorSet)localObject).setDuration(150L);
      ((AnimatorSet)localObject).playSequentially(new Animator[] { localObjectAnimator2, localObjectAnimator1 });
      paramList.add(localObject);
    }
  }
  
  private FloatingActionButton findDependentFab()
  {
    if (!(getParent() instanceof CoordinatorLayout)) {
      return null;
    }
    Iterator localIterator = ((CoordinatorLayout)getParent()).getDependents(this).iterator();
    while (localIterator.hasNext())
    {
      View localView = (View)localIterator.next();
      if ((localView instanceof FloatingActionButton)) {
        return (FloatingActionButton)localView;
      }
    }
    return null;
  }
  
  private ActionMenuView getActionMenuView()
  {
    int i = 0;
    while (i < getChildCount())
    {
      View localView = getChildAt(i);
      if ((localView instanceof ActionMenuView)) {
        return (ActionMenuView)localView;
      }
      i += 1;
    }
    return null;
  }
  
  private float getFabTranslationX()
  {
    return getFabTranslationX(fabAlignmentMode);
  }
  
  private int getFabTranslationX(int paramInt)
  {
    int i = ViewCompat.getLayoutDirection(this);
    int j = 1;
    if (i == 1) {
      i = 1;
    } else {
      i = 0;
    }
    if (paramInt == 1)
    {
      int k = getMeasuredWidth() / 2;
      int m = fabOffsetEndMode;
      paramInt = j;
      if (i != 0) {
        paramInt = -1;
      }
      return (k - m) * paramInt;
    }
    return 0;
  }
  
  private float getFabTranslationY()
  {
    return getFabTranslationY(fabAttached);
  }
  
  private float getFabTranslationY(boolean paramBoolean)
  {
    FloatingActionButton localFloatingActionButton = findDependentFab();
    if (localFloatingActionButton == null) {
      return 0.0F;
    }
    Rect localRect = new Rect();
    localFloatingActionButton.getContentRect(localRect);
    float f2 = localRect.height();
    float f1 = f2;
    if (f2 == 0.0F) {
      f1 = localFloatingActionButton.getMeasuredHeight();
    }
    f2 = localFloatingActionButton.getHeight() - bottom;
    float f5 = localFloatingActionButton.getHeight() - localRect.height();
    float f3 = -getCradleVerticalOffset();
    float f4 = f1 / 2.0F;
    f1 = f5 - localFloatingActionButton.getPaddingBottom();
    f5 = -getMeasuredHeight();
    if (paramBoolean) {
      f1 = f3 + f4 + f2;
    }
    return f5 + f1;
  }
  
  private boolean isAnimationRunning()
  {
    Animator localAnimator = attachAnimator;
    if ((localAnimator == null) || (!localAnimator.isRunning()))
    {
      localAnimator = menuAnimator;
      if ((localAnimator == null) || (!localAnimator.isRunning())) {
        localAnimator = modeAnimator;
      }
    }
    return (localAnimator != null) && (localAnimator.isRunning());
  }
  
  private boolean isVisibleFab()
  {
    FloatingActionButton localFloatingActionButton = findDependentFab();
    return (localFloatingActionButton != null) && (localFloatingActionButton.isOrWillBeShown());
  }
  
  private void maybeAnimateAttachChange(boolean paramBoolean)
  {
    if (!ViewCompat.isLaidOut(this)) {
      return;
    }
    Object localObject = attachAnimator;
    if (localObject != null) {
      ((Animator)localObject).cancel();
    }
    localObject = new ArrayList();
    boolean bool;
    if ((paramBoolean) && (isVisibleFab())) {
      bool = true;
    } else {
      bool = false;
    }
    createCradleShapeAnimation(bool, (List)localObject);
    createFabTranslationYAnimation(paramBoolean, (List)localObject);
    AnimatorSet localAnimatorSet = new AnimatorSet();
    localAnimatorSet.playTogether((Collection)localObject);
    attachAnimator = localAnimatorSet;
    attachAnimator.addListener(new AnimatorListenerAdapter()
    {
      public void onAnimationEnd(Animator paramAnonymousAnimator)
      {
        BottomAppBar.access$502(BottomAppBar.this, null);
      }
    });
    attachAnimator.start();
  }
  
  private void maybeAnimateMenuView(int paramInt, boolean paramBoolean)
  {
    if (!ViewCompat.isLaidOut(this)) {
      return;
    }
    Object localObject = menuAnimator;
    if (localObject != null) {
      ((Animator)localObject).cancel();
    }
    localObject = new ArrayList();
    if (!isVisibleFab())
    {
      paramInt = 0;
      paramBoolean = false;
    }
    createMenuViewTranslationAnimation(paramInt, paramBoolean, (List)localObject);
    AnimatorSet localAnimatorSet = new AnimatorSet();
    localAnimatorSet.playTogether((Collection)localObject);
    menuAnimator = localAnimatorSet;
    menuAnimator.addListener(new AnimatorListenerAdapter()
    {
      public void onAnimationEnd(Animator paramAnonymousAnimator)
      {
        BottomAppBar.access$302(BottomAppBar.this, null);
      }
    });
    menuAnimator.start();
  }
  
  private void maybeAnimateModeChange(int paramInt)
  {
    if (fabAlignmentMode != paramInt)
    {
      if (!ViewCompat.isLaidOut(this)) {
        return;
      }
      Object localObject = modeAnimator;
      if (localObject != null) {
        ((Animator)localObject).cancel();
      }
      localObject = new ArrayList();
      createCradleTranslationAnimation(paramInt, (List)localObject);
      createFabTranslationXAnimation(paramInt, (List)localObject);
      AnimatorSet localAnimatorSet = new AnimatorSet();
      localAnimatorSet.playTogether((Collection)localObject);
      modeAnimator = localAnimatorSet;
      modeAnimator.addListener(new AnimatorListenerAdapter()
      {
        public void onAnimationEnd(Animator paramAnonymousAnimator)
        {
          BottomAppBar.access$002(BottomAppBar.this, null);
        }
      });
      modeAnimator.start();
    }
  }
  
  private void removeFabAnimationListeners(FloatingActionButton paramFloatingActionButton)
  {
    paramFloatingActionButton.removeOnHideAnimationListener(fabAnimationListener);
    paramFloatingActionButton.removeOnShowAnimationListener(fabAnimationListener);
  }
  
  private void setCutoutState()
  {
    topEdgeTreatment.setHorizontalOffset(getFabTranslationX());
    Object localObject = findDependentFab();
    MaterialShapeDrawable localMaterialShapeDrawable = materialShapeDrawable;
    float f;
    if ((fabAttached) && (isVisibleFab())) {
      f = 1.0F;
    } else {
      f = 0.0F;
    }
    localMaterialShapeDrawable.setInterpolation(f);
    if (localObject != null)
    {
      ((View)localObject).setTranslationY(getFabTranslationY());
      ((View)localObject).setTranslationX(getFabTranslationX());
    }
    localObject = getActionMenuView();
    if (localObject != null)
    {
      ((View)localObject).setAlpha(1.0F);
      if (!isVisibleFab())
      {
        translateActionMenuView((ActionMenuView)localObject, 0, false);
        return;
      }
      translateActionMenuView((ActionMenuView)localObject, fabAlignmentMode, fabAttached);
    }
  }
  
  private void translateActionMenuView(ActionMenuView paramActionMenuView, int paramInt, boolean paramBoolean)
  {
    int i;
    if (ViewCompat.getLayoutDirection(this) == 1) {
      i = 1;
    } else {
      i = 0;
    }
    int k = 0;
    int m;
    for (int j = 0; k < getChildCount(); j = m)
    {
      View localView = getChildAt(k);
      int n;
      if (((localView.getLayoutParams() instanceof Toolbar.LayoutParams)) && ((getLayoutParamsgravity & 0x800007) == 8388611)) {
        n = 1;
      } else {
        n = 0;
      }
      m = j;
      if (n != 0)
      {
        if (i != 0) {
          m = localView.getLeft();
        } else {
          m = localView.getRight();
        }
        m = Math.max(j, m);
      }
      k += 1;
    }
    if (i != 0) {
      i = paramActionMenuView.getRight();
    } else {
      i = paramActionMenuView.getLeft();
    }
    float f;
    if ((paramInt == 1) && (paramBoolean)) {
      f = j - i;
    } else {
      f = 0.0F;
    }
    paramActionMenuView.setTranslationX(f);
  }
  
  public ColorStateList getBackgroundTint()
  {
    return materialShapeDrawable.getTintList();
  }
  
  public CoordinatorLayout.Behavior getBehavior()
  {
    return new Behavior();
  }
  
  public float getCradleVerticalOffset()
  {
    return topEdgeTreatment.getCradleVerticalOffset();
  }
  
  public int getFabAlignmentMode()
  {
    return fabAlignmentMode;
  }
  
  public float getFabCradleMargin()
  {
    return topEdgeTreatment.getFabCradleMargin();
  }
  
  public float getFabCradleRoundedCornerRadius()
  {
    return topEdgeTreatment.getFabCradleRoundedCornerRadius();
  }
  
  public boolean getHideOnScroll()
  {
    return hideOnScroll;
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    cancelAnimations();
    setCutoutState();
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
    fabAlignmentMode = fabAlignmentMode;
    fabAttached = fabAttached;
  }
  
  protected Parcelable onSaveInstanceState()
  {
    SavedState localSavedState = new SavedState(super.onSaveInstanceState());
    fabAlignmentMode = fabAlignmentMode;
    fabAttached = fabAttached;
    return localSavedState;
  }
  
  public void replaceMenu(int paramInt)
  {
    getMenu().clear();
    inflateMenu(paramInt);
  }
  
  public void setBackgroundTint(ColorStateList paramColorStateList)
  {
    DrawableCompat.setTintList(materialShapeDrawable, paramColorStateList);
  }
  
  public void setCradleVerticalOffset(float paramFloat)
  {
    if (paramFloat != getCradleVerticalOffset())
    {
      topEdgeTreatment.setCradleVerticalOffset(paramFloat);
      materialShapeDrawable.invalidateSelf();
    }
  }
  
  public void setFabAlignmentMode(int paramInt)
  {
    maybeAnimateModeChange(paramInt);
    maybeAnimateMenuView(paramInt, fabAttached);
    fabAlignmentMode = paramInt;
  }
  
  public void setFabCradleMargin(float paramFloat)
  {
    if (paramFloat != getFabCradleMargin())
    {
      topEdgeTreatment.setFabCradleMargin(paramFloat);
      materialShapeDrawable.invalidateSelf();
    }
  }
  
  public void setFabCradleRoundedCornerRadius(float paramFloat)
  {
    if (paramFloat != getFabCradleRoundedCornerRadius())
    {
      topEdgeTreatment.setFabCradleRoundedCornerRadius(paramFloat);
      materialShapeDrawable.invalidateSelf();
    }
  }
  
  void setFabDiameter(int paramInt)
  {
    float f = paramInt;
    if (f != topEdgeTreatment.getFabDiameter())
    {
      topEdgeTreatment.setFabDiameter(f);
      materialShapeDrawable.invalidateSelf();
    }
  }
  
  public void setHideOnScroll(boolean paramBoolean)
  {
    hideOnScroll = paramBoolean;
  }
  
  public void setSubtitle(CharSequence paramCharSequence) {}
  
  public void setTitle(CharSequence paramCharSequence) {}
  
  public static class Behavior
    extends HideBottomViewOnScrollBehavior<BottomAppBar>
  {
    private final Rect fabContentRect = new Rect();
    
    public Behavior() {}
    
    public Behavior(Context paramContext, AttributeSet paramAttributeSet)
    {
      super(paramAttributeSet);
    }
    
    private boolean updateFabPositionAndVisibility(FloatingActionButton paramFloatingActionButton, BottomAppBar paramBottomAppBar)
    {
      getLayoutParamsanchorGravity = 17;
      paramBottomAppBar.addFabAnimationListeners(paramFloatingActionButton);
      return true;
    }
    
    public boolean onLayoutChild(CoordinatorLayout paramCoordinatorLayout, BottomAppBar paramBottomAppBar, int paramInt)
    {
      FloatingActionButton localFloatingActionButton = paramBottomAppBar.findDependentFab();
      if (localFloatingActionButton != null)
      {
        updateFabPositionAndVisibility(localFloatingActionButton, paramBottomAppBar);
        localFloatingActionButton.getMeasuredContentRect(fabContentRect);
        paramBottomAppBar.setFabDiameter(fabContentRect.height());
      }
      if (!paramBottomAppBar.isAnimationRunning()) {
        paramBottomAppBar.setCutoutState();
      }
      paramCoordinatorLayout.onLayoutChild(paramBottomAppBar, paramInt);
      return super.onLayoutChild(paramCoordinatorLayout, paramBottomAppBar, paramInt);
    }
    
    public boolean onStartNestedScroll(CoordinatorLayout paramCoordinatorLayout, BottomAppBar paramBottomAppBar, View paramView1, View paramView2, int paramInt1, int paramInt2)
    {
      return (paramBottomAppBar.getHideOnScroll()) && (super.onStartNestedScroll(paramCoordinatorLayout, paramBottomAppBar, paramView1, paramView2, paramInt1, paramInt2));
    }
    
    protected void slideDown(BottomAppBar paramBottomAppBar)
    {
      super.slideDown(paramBottomAppBar);
      paramBottomAppBar = paramBottomAppBar.findDependentFab();
      if (paramBottomAppBar != null)
      {
        paramBottomAppBar.getContentRect(fabContentRect);
        float f = paramBottomAppBar.getMeasuredHeight() - fabContentRect.height();
        paramBottomAppBar.clearAnimation();
        paramBottomAppBar.animate().translationY(-paramBottomAppBar.getPaddingBottom() + f).setInterpolator(AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR).setDuration(175L);
      }
    }
    
    protected void slideUp(BottomAppBar paramBottomAppBar)
    {
      super.slideUp(paramBottomAppBar);
      FloatingActionButton localFloatingActionButton = paramBottomAppBar.findDependentFab();
      if (localFloatingActionButton != null)
      {
        localFloatingActionButton.clearAnimation();
        localFloatingActionButton.animate().translationY(paramBottomAppBar.getFabTranslationY()).setInterpolator(AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR).setDuration(225L);
      }
    }
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface FabAlignmentMode {}
  
  static class SavedState
    extends AbsSavedState
  {
    public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator()
    {
      public BottomAppBar.SavedState createFromParcel(Parcel paramAnonymousParcel)
      {
        return new BottomAppBar.SavedState(paramAnonymousParcel, null);
      }
      
      public BottomAppBar.SavedState createFromParcel(Parcel paramAnonymousParcel, ClassLoader paramAnonymousClassLoader)
      {
        return new BottomAppBar.SavedState(paramAnonymousParcel, paramAnonymousClassLoader);
      }
      
      public BottomAppBar.SavedState[] newArray(int paramAnonymousInt)
      {
        return new BottomAppBar.SavedState[paramAnonymousInt];
      }
    };
    int fabAlignmentMode;
    boolean fabAttached;
    
    public SavedState(Parcel paramParcel, ClassLoader paramClassLoader)
    {
      super(paramClassLoader);
      fabAlignmentMode = paramParcel.readInt();
      boolean bool;
      if (paramParcel.readInt() != 0) {
        bool = true;
      } else {
        bool = false;
      }
      fabAttached = bool;
    }
    
    public SavedState(Parcelable paramParcelable)
    {
      super();
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      throw new Runtime("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.provideAs(TypeTransformer.java:780)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.e1expr(TypeTransformer.java:496)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:713)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:698)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.s1stmt(TypeTransformer.java:810)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.sxStmt(TypeTransformer.java:840)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:206)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\n");
    }
  }
}
