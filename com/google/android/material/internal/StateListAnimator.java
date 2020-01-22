package com.google.android.material.internal;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.util.StateSet;
import java.util.ArrayList;

public final class StateListAnimator
{
  private final Animator.AnimatorListener animationListener = new AnimatorListenerAdapter()
  {
    public void onAnimationEnd(Animator paramAnonymousAnimator)
    {
      if (runningAnimator == paramAnonymousAnimator) {
        runningAnimator = null;
      }
    }
  };
  private Tuple lastMatch = null;
  ValueAnimator runningAnimator = null;
  private final ArrayList<Tuple> tuples = new ArrayList();
  
  public StateListAnimator() {}
  
  private void cancel()
  {
    ValueAnimator localValueAnimator = runningAnimator;
    if (localValueAnimator != null)
    {
      localValueAnimator.cancel();
      runningAnimator = null;
    }
  }
  
  private void start(Tuple paramTuple)
  {
    runningAnimator = animator;
    runningAnimator.start();
  }
  
  public void addState(int[] paramArrayOfInt, ValueAnimator paramValueAnimator)
  {
    paramArrayOfInt = new Tuple(paramArrayOfInt, paramValueAnimator);
    paramValueAnimator.addListener(animationListener);
    tuples.add(paramArrayOfInt);
  }
  
  public void jumpToCurrentState()
  {
    ValueAnimator localValueAnimator = runningAnimator;
    if (localValueAnimator != null)
    {
      localValueAnimator.end();
      runningAnimator = null;
    }
  }
  
  public void setState(int[] paramArrayOfInt)
  {
    int j = tuples.size();
    int i = 0;
    while (i < j)
    {
      localTuple = (Tuple)tuples.get(i);
      if (StateSet.stateSetMatches(specs, paramArrayOfInt))
      {
        paramArrayOfInt = localTuple;
        break label55;
      }
      i += 1;
    }
    paramArrayOfInt = null;
    label55:
    Tuple localTuple = lastMatch;
    if (paramArrayOfInt == localTuple) {
      return;
    }
    if (localTuple != null) {
      cancel();
    }
    lastMatch = paramArrayOfInt;
    if (paramArrayOfInt != null) {
      start(paramArrayOfInt);
    }
  }
  
  static class Tuple
  {
    final ValueAnimator animator;
    final int[] specs;
    
    Tuple(int[] paramArrayOfInt, ValueAnimator paramValueAnimator)
    {
      specs = paramArrayOfInt;
      animator = paramValueAnimator;
    }
  }
}
