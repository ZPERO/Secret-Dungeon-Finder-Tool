package com.google.android.material.circularreveal;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Build.VERSION;
import android.view.View;
import android.view.ViewAnimationUtils;

public final class CircularRevealCompat
{
  private CircularRevealCompat() {}
  
  public static Animator createCircularReveal(CircularRevealWidget paramCircularRevealWidget, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    ObjectAnimator localObjectAnimator = ObjectAnimator.ofObject(paramCircularRevealWidget, CircularRevealWidget.CircularRevealProperty.CIRCULAR_REVEAL, CircularRevealWidget.CircularRevealEvaluator.CIRCULAR_REVEAL, new CircularRevealWidget.RevealInfo[] { new CircularRevealWidget.RevealInfo(paramFloat1, paramFloat2, paramFloat3) });
    if (Build.VERSION.SDK_INT >= 21)
    {
      Object localObject = paramCircularRevealWidget.getRevealInfo();
      if (localObject != null)
      {
        float f = radius;
        paramCircularRevealWidget = ViewAnimationUtils.createCircularReveal((View)paramCircularRevealWidget, (int)paramFloat1, (int)paramFloat2, f, paramFloat3);
        localObject = new AnimatorSet();
        ((AnimatorSet)localObject).playTogether(new Animator[] { localObjectAnimator, paramCircularRevealWidget });
        return localObject;
      }
      throw new IllegalStateException("Caller must set a non-null RevealInfo before calling this.");
    }
    return localObjectAnimator;
  }
  
  public static Animator createCircularReveal(CircularRevealWidget paramCircularRevealWidget, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    ObjectAnimator localObjectAnimator = ObjectAnimator.ofObject(paramCircularRevealWidget, CircularRevealWidget.CircularRevealProperty.CIRCULAR_REVEAL, CircularRevealWidget.CircularRevealEvaluator.CIRCULAR_REVEAL, new CircularRevealWidget.RevealInfo[] { new CircularRevealWidget.RevealInfo(paramFloat1, paramFloat2, paramFloat3), new CircularRevealWidget.RevealInfo(paramFloat1, paramFloat2, paramFloat4) });
    if (Build.VERSION.SDK_INT >= 21)
    {
      paramCircularRevealWidget = ViewAnimationUtils.createCircularReveal((View)paramCircularRevealWidget, (int)paramFloat1, (int)paramFloat2, paramFloat3, paramFloat4);
      AnimatorSet localAnimatorSet = new AnimatorSet();
      localAnimatorSet.playTogether(new Animator[] { localObjectAnimator, paramCircularRevealWidget });
      return localAnimatorSet;
    }
    return localObjectAnimator;
  }
  
  public static Animator.AnimatorListener createCircularRevealListener(CircularRevealWidget paramCircularRevealWidget)
  {
    new AnimatorListenerAdapter()
    {
      public void onAnimationEnd(Animator paramAnonymousAnimator)
      {
        val$view.destroyCircularRevealCache();
      }
      
      public void onAnimationStart(Animator paramAnonymousAnimator)
      {
        val$view.buildCircularRevealCache();
      }
    };
  }
}
