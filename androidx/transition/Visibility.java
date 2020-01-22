package androidx.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.content.delay.TypedArrayUtils;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Map;

public abstract class Visibility
  extends Transition
{
  public static final int MODE_IN = 1;
  public static final int MODE_OUT = 2;
  private static final String PROPNAME_PARENT = "android:visibility:parent";
  private static final String PROPNAME_SCREEN_LOCATION = "android:visibility:screenLocation";
  static final String PROPNAME_VISIBILITY = "android:visibility:visibility";
  private static final String[] sTransitionProperties = { "android:visibility:visibility", "android:visibility:parent" };
  private int mMode = 3;
  
  public Visibility() {}
  
  public Visibility(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    paramContext = paramContext.obtainStyledAttributes(paramAttributeSet, Styleable.VISIBILITY_TRANSITION);
    int i = TypedArrayUtils.getNamedInt(paramContext, (XmlResourceParser)paramAttributeSet, "transitionVisibilityMode", 0, 0);
    paramContext.recycle();
    if (i != 0) {
      setMode(i);
    }
  }
  
  private void captureValues(TransitionValues paramTransitionValues)
  {
    int i = view.getVisibility();
    values.put("android:visibility:visibility", Integer.valueOf(i));
    values.put("android:visibility:parent", view.getParent());
    int[] arrayOfInt = new int[2];
    view.getLocationOnScreen(arrayOfInt);
    values.put("android:visibility:screenLocation", arrayOfInt);
  }
  
  private VisibilityInfo getVisibilityChangeInfo(TransitionValues paramTransitionValues1, TransitionValues paramTransitionValues2)
  {
    VisibilityInfo localVisibilityInfo = new VisibilityInfo();
    mVisibilityChange = false;
    mFadeIn = false;
    if ((paramTransitionValues1 != null) && (values.containsKey("android:visibility:visibility")))
    {
      mStartVisibility = ((Integer)values.get("android:visibility:visibility")).intValue();
      mStartParent = ((ViewGroup)values.get("android:visibility:parent"));
    }
    else
    {
      mStartVisibility = -1;
      mStartParent = null;
    }
    if ((paramTransitionValues2 != null) && (values.containsKey("android:visibility:visibility")))
    {
      mEndVisibility = ((Integer)values.get("android:visibility:visibility")).intValue();
      mEndParent = ((ViewGroup)values.get("android:visibility:parent"));
    }
    else
    {
      mEndVisibility = -1;
      mEndParent = null;
    }
    if ((paramTransitionValues1 != null) && (paramTransitionValues2 != null))
    {
      if ((mStartVisibility == mEndVisibility) && (mStartParent == mEndParent)) {
        return localVisibilityInfo;
      }
      if (mStartVisibility != mEndVisibility)
      {
        if (mStartVisibility == 0)
        {
          mFadeIn = false;
          mVisibilityChange = true;
          return localVisibilityInfo;
        }
        if (mEndVisibility == 0)
        {
          mFadeIn = true;
          mVisibilityChange = true;
          return localVisibilityInfo;
        }
      }
      else
      {
        if (mEndParent == null)
        {
          mFadeIn = false;
          mVisibilityChange = true;
          return localVisibilityInfo;
        }
        if (mStartParent == null)
        {
          mFadeIn = true;
          mVisibilityChange = true;
          return localVisibilityInfo;
        }
      }
    }
    else
    {
      if ((paramTransitionValues1 == null) && (mEndVisibility == 0))
      {
        mFadeIn = true;
        mVisibilityChange = true;
        return localVisibilityInfo;
      }
      if ((paramTransitionValues2 == null) && (mStartVisibility == 0))
      {
        mFadeIn = false;
        mVisibilityChange = true;
      }
    }
    return localVisibilityInfo;
  }
  
  public void captureEndValues(TransitionValues paramTransitionValues)
  {
    captureValues(paramTransitionValues);
  }
  
  public void captureStartValues(TransitionValues paramTransitionValues)
  {
    captureValues(paramTransitionValues);
  }
  
  public Animator createAnimator(ViewGroup paramViewGroup, TransitionValues paramTransitionValues1, TransitionValues paramTransitionValues2)
  {
    VisibilityInfo localVisibilityInfo = getVisibilityChangeInfo(paramTransitionValues1, paramTransitionValues2);
    if ((mVisibilityChange) && ((mStartParent != null) || (mEndParent != null)))
    {
      if (mFadeIn) {
        return onAppear(paramViewGroup, paramTransitionValues1, mStartVisibility, paramTransitionValues2, mEndVisibility);
      }
      return onDisappear(paramViewGroup, paramTransitionValues1, mStartVisibility, paramTransitionValues2, mEndVisibility);
    }
    return null;
  }
  
  public int getMode()
  {
    return mMode;
  }
  
  public String[] getTransitionProperties()
  {
    return sTransitionProperties;
  }
  
  public boolean isTransitionRequired(TransitionValues paramTransitionValues1, TransitionValues paramTransitionValues2)
  {
    if ((paramTransitionValues1 == null) && (paramTransitionValues2 == null)) {
      return false;
    }
    if ((paramTransitionValues1 != null) && (paramTransitionValues2 != null) && (values.containsKey("android:visibility:visibility") != values.containsKey("android:visibility:visibility"))) {
      return false;
    }
    paramTransitionValues1 = getVisibilityChangeInfo(paramTransitionValues1, paramTransitionValues2);
    return (mVisibilityChange) && ((mStartVisibility == 0) || (mEndVisibility == 0));
  }
  
  public boolean isVisible(TransitionValues paramTransitionValues)
  {
    if (paramTransitionValues == null) {
      return false;
    }
    int i = ((Integer)values.get("android:visibility:visibility")).intValue();
    paramTransitionValues = (View)values.get("android:visibility:parent");
    return (i == 0) && (paramTransitionValues != null);
  }
  
  public Animator onAppear(ViewGroup paramViewGroup, View paramView, TransitionValues paramTransitionValues1, TransitionValues paramTransitionValues2)
  {
    return null;
  }
  
  public Animator onAppear(ViewGroup paramViewGroup, TransitionValues paramTransitionValues1, int paramInt1, TransitionValues paramTransitionValues2, int paramInt2)
  {
    if ((mMode & 0x1) == 1)
    {
      if (paramTransitionValues2 == null) {
        return null;
      }
      if (paramTransitionValues1 == null)
      {
        View localView = (View)view.getParent();
        if (getVisibilityChangeInfogetMatchedTransitionValuesgetTransitionValuesmVisibilityChange) {
          return null;
        }
      }
      return onAppear(paramViewGroup, view, paramTransitionValues1, paramTransitionValues2);
    }
    return null;
  }
  
  public Animator onDisappear(ViewGroup paramViewGroup, View paramView, TransitionValues paramTransitionValues1, TransitionValues paramTransitionValues2)
  {
    return null;
  }
  
  public Animator onDisappear(ViewGroup paramViewGroup, TransitionValues paramTransitionValues1, int paramInt1, TransitionValues paramTransitionValues2, int paramInt2)
  {
    if ((mMode & 0x2) != 2) {
      return null;
    }
    if (paramTransitionValues1 != null) {
      localObject2 = view;
    } else {
      localObject2 = null;
    }
    if (paramTransitionValues2 != null) {
      localObject1 = view;
    } else {
      localObject1 = null;
    }
    if ((localObject1 != null) && (((View)localObject1).getParent() != null))
    {
      if ((paramInt2 == 4) || (localObject2 == localObject1))
      {
        localObject2 = null;
        break label267;
      }
      if (mCanRemoveViews) {
        localObject1 = localObject2;
      } else {
        localObject1 = TransitionUtils.copyViewImage(paramViewGroup, (View)localObject2, (View)((View)localObject2).getParent());
      }
    }
    else
    {
      if (localObject1 == null) {
        break label136;
      }
    }
    for (;;)
    {
      Object localObject3 = null;
      localObject2 = localObject1;
      localObject1 = localObject3;
      break label267;
      label136:
      if (localObject2 == null) {
        break;
      }
      if (((View)localObject2).getParent() == null)
      {
        localObject1 = localObject2;
      }
      else
      {
        if (!(((View)localObject2).getParent() instanceof View)) {
          break;
        }
        localObject1 = (View)((View)localObject2).getParent();
        if (!getVisibilityChangeInfogetTransitionValuesgetMatchedTransitionValuesmVisibilityChange)
        {
          localObject1 = TransitionUtils.copyViewImage(paramViewGroup, (View)localObject2, (View)localObject1);
        }
        else
        {
          if (((View)localObject1).getParent() == null)
          {
            paramInt1 = ((View)localObject1).getId();
            if ((paramInt1 != -1) && (paramViewGroup.findViewById(paramInt1) != null) && (mCanRemoveViews))
            {
              localObject1 = localObject2;
              continue;
            }
          }
          localObject1 = null;
        }
      }
    }
    Object localObject2 = null;
    Object localObject1 = null;
    label267:
    if ((localObject2 != null) && (paramTransitionValues1 != null))
    {
      localObject1 = (int[])values.get("android:visibility:screenLocation");
      paramInt1 = localObject1[0];
      paramInt2 = localObject1[1];
      localObject1 = new int[2];
      paramViewGroup.getLocationOnScreen((int[])localObject1);
      ((View)localObject2).offsetLeftAndRight(paramInt1 - localObject1[0] - ((View)localObject2).getLeft());
      ((View)localObject2).offsetTopAndBottom(paramInt2 - localObject1[1] - ((View)localObject2).getTop());
      localObject1 = ViewGroupUtils.getOverlay(paramViewGroup);
      ((ViewGroupOverlayImpl)localObject1).addHeaderView((View)localObject2);
      paramViewGroup = onDisappear(paramViewGroup, (View)localObject2, paramTransitionValues1, paramTransitionValues2);
      if (paramViewGroup == null)
      {
        ((ViewGroupOverlayImpl)localObject1).remove((View)localObject2);
        return paramViewGroup;
      }
      paramViewGroup.addListener(new AnimatorListenerAdapter()
      {
        public void onAnimationEnd(Animator paramAnonymousAnimator)
        {
          val$overlay.remove(val$finalOverlayView);
        }
      });
      return paramViewGroup;
    }
    if (localObject1 != null)
    {
      paramInt1 = ((View)localObject1).getVisibility();
      ViewUtils.setTransitionVisibility((View)localObject1, 0);
      paramViewGroup = onDisappear(paramViewGroup, (View)localObject1, paramTransitionValues1, paramTransitionValues2);
      if (paramViewGroup != null)
      {
        paramTransitionValues1 = new DisappearListener((View)localObject1, paramInt2, true);
        paramViewGroup.addListener(paramTransitionValues1);
        AnimatorUtils.addPauseListener(paramViewGroup, paramTransitionValues1);
        addListener(paramTransitionValues1);
        return paramViewGroup;
      }
      ViewUtils.setTransitionVisibility((View)localObject1, paramInt1);
      return paramViewGroup;
    }
    return null;
  }
  
  public void setMode(int paramInt)
  {
    if ((paramInt & 0xFFFFFFFC) == 0)
    {
      mMode = paramInt;
      return;
    }
    throw new IllegalArgumentException("Only MODE_IN and MODE_OUT flags are allowed");
  }
  
  private static class DisappearListener
    extends AnimatorListenerAdapter
    implements Transition.TransitionListener, AnimatorUtils.AnimatorPauseListenerCompat
  {
    boolean mCanceled = false;
    private final int mFinalVisibility;
    private boolean mLayoutSuppressed;
    private final ViewGroup mParent;
    private final boolean mSuppressLayout;
    private final View mView;
    
    DisappearListener(View paramView, int paramInt, boolean paramBoolean)
    {
      mView = paramView;
      mFinalVisibility = paramInt;
      mParent = ((ViewGroup)paramView.getParent());
      mSuppressLayout = paramBoolean;
      suppressLayout(true);
    }
    
    private void hideViewWhenNotCanceled()
    {
      if (!mCanceled)
      {
        ViewUtils.setTransitionVisibility(mView, mFinalVisibility);
        ViewGroup localViewGroup = mParent;
        if (localViewGroup != null) {
          localViewGroup.invalidate();
        }
      }
      suppressLayout(false);
    }
    
    private void suppressLayout(boolean paramBoolean)
    {
      if ((mSuppressLayout) && (mLayoutSuppressed != paramBoolean))
      {
        ViewGroup localViewGroup = mParent;
        if (localViewGroup != null)
        {
          mLayoutSuppressed = paramBoolean;
          ViewGroupUtils.suppressLayout(localViewGroup, paramBoolean);
        }
      }
    }
    
    public void onAnimationCancel(Animator paramAnimator)
    {
      mCanceled = true;
    }
    
    public void onAnimationEnd(Animator paramAnimator)
    {
      hideViewWhenNotCanceled();
    }
    
    public void onAnimationPause(Animator paramAnimator)
    {
      if (!mCanceled) {
        ViewUtils.setTransitionVisibility(mView, mFinalVisibility);
      }
    }
    
    public void onAnimationRepeat(Animator paramAnimator) {}
    
    public void onAnimationResume(Animator paramAnimator)
    {
      if (!mCanceled) {
        ViewUtils.setTransitionVisibility(mView, 0);
      }
    }
    
    public void onAnimationStart(Animator paramAnimator) {}
    
    public void onTransitionCancel(Transition paramTransition) {}
    
    public void onTransitionEnd(Transition paramTransition)
    {
      hideViewWhenNotCanceled();
      paramTransition.removeListener(this);
    }
    
    public void onTransitionPause(Transition paramTransition)
    {
      suppressLayout(false);
    }
    
    public void onTransitionResume(Transition paramTransition)
    {
      suppressLayout(true);
    }
    
    public void onTransitionStart(Transition paramTransition) {}
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface Mode {}
  
  private static class VisibilityInfo
  {
    ViewGroup mEndParent;
    int mEndVisibility;
    boolean mFadeIn;
    ViewGroup mStartParent;
    int mStartVisibility;
    boolean mVisibilityChange;
    
    VisibilityInfo() {}
  }
}
