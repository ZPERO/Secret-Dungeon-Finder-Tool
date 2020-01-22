package com.google.android.material.internal;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.transition.Transition;
import androidx.transition.TransitionValues;
import java.util.Map;

public class TextScale
  extends Transition
{
  private static final String PROPNAME_SCALE = "android:textscale:scale";
  
  public TextScale() {}
  
  private void captureValues(TransitionValues paramTransitionValues)
  {
    if ((view instanceof TextView))
    {
      TextView localTextView = (TextView)view;
      values.put("android:textscale:scale", Float.valueOf(localTextView.getScaleX()));
    }
  }
  
  public void captureEndValues(TransitionValues paramTransitionValues)
  {
    captureValues(paramTransitionValues);
  }
  
  public void captureStartValues(TransitionValues paramTransitionValues)
  {
    captureValues(paramTransitionValues);
  }
  
  public Animator createAnimator(final ViewGroup paramViewGroup, TransitionValues paramTransitionValues1, TransitionValues paramTransitionValues2)
  {
    if ((paramTransitionValues1 != null) && (paramTransitionValues2 != null) && ((view instanceof TextView)))
    {
      if (!(view instanceof TextView)) {
        return null;
      }
      paramViewGroup = (TextView)view;
      paramTransitionValues1 = values;
      paramTransitionValues2 = values;
      Object localObject = paramTransitionValues1.get("android:textscale:scale");
      float f2 = 1.0F;
      float f1;
      if (localObject != null) {
        f1 = ((Float)paramTransitionValues1.get("android:textscale:scale")).floatValue();
      } else {
        f1 = 1.0F;
      }
      if (paramTransitionValues2.get("android:textscale:scale") != null) {
        f2 = ((Float)paramTransitionValues2.get("android:textscale:scale")).floatValue();
      }
      if (f1 == f2) {
        return null;
      }
      paramTransitionValues1 = ValueAnimator.ofFloat(new float[] { f1, f2 });
      paramTransitionValues1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
      {
        public void onAnimationUpdate(ValueAnimator paramAnonymousValueAnimator)
        {
          float f = ((Float)paramAnonymousValueAnimator.getAnimatedValue()).floatValue();
          paramViewGroup.setScaleX(f);
          paramViewGroup.setScaleY(f);
        }
      });
      return paramTransitionValues1;
    }
    return null;
  }
}
