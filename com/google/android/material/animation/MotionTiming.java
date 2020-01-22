package com.google.android.material.animation;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

public class MotionTiming
{
  private long delay = 0L;
  private long duration = 300L;
  private TimeInterpolator interpolator = null;
  private int repeatCount = 0;
  private int repeatMode = 1;
  
  public MotionTiming(long paramLong1, long paramLong2)
  {
    delay = paramLong1;
    duration = paramLong2;
  }
  
  public MotionTiming(long paramLong1, long paramLong2, TimeInterpolator paramTimeInterpolator)
  {
    delay = paramLong1;
    duration = paramLong2;
    interpolator = paramTimeInterpolator;
  }
  
  static MotionTiming createFromAnimator(ValueAnimator paramValueAnimator)
  {
    MotionTiming localMotionTiming = new MotionTiming(paramValueAnimator.getStartDelay(), paramValueAnimator.getDuration(), getInterpolatorCompat(paramValueAnimator));
    repeatCount = paramValueAnimator.getRepeatCount();
    repeatMode = paramValueAnimator.getRepeatMode();
    return localMotionTiming;
  }
  
  private static TimeInterpolator getInterpolatorCompat(ValueAnimator paramValueAnimator)
  {
    TimeInterpolator localTimeInterpolator = paramValueAnimator.getInterpolator();
    if ((!(localTimeInterpolator instanceof AccelerateDecelerateInterpolator)) && (localTimeInterpolator != null))
    {
      if ((localTimeInterpolator instanceof AccelerateInterpolator)) {
        return AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR;
      }
      paramValueAnimator = localTimeInterpolator;
      if ((localTimeInterpolator instanceof DecelerateInterpolator)) {
        return AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR;
      }
    }
    else
    {
      paramValueAnimator = AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR;
    }
    return paramValueAnimator;
  }
  
  public void apply(Animator paramAnimator)
  {
    paramAnimator.setStartDelay(getDelay());
    paramAnimator.setDuration(getDuration());
    paramAnimator.setInterpolator(getInterpolator());
    if ((paramAnimator instanceof ValueAnimator))
    {
      paramAnimator = (ValueAnimator)paramAnimator;
      paramAnimator.setRepeatCount(getRepeatCount());
      paramAnimator.setRepeatMode(getRepeatMode());
    }
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if (paramObject != null)
    {
      if (getClass() != paramObject.getClass()) {
        return false;
      }
      paramObject = (MotionTiming)paramObject;
      if (getDelay() != paramObject.getDelay()) {
        return false;
      }
      if (getDuration() != paramObject.getDuration()) {
        return false;
      }
      if (getRepeatCount() != paramObject.getRepeatCount()) {
        return false;
      }
      if (getRepeatMode() != paramObject.getRepeatMode()) {
        return false;
      }
      return getInterpolator().getClass().equals(paramObject.getInterpolator().getClass());
    }
    return false;
  }
  
  public long getDelay()
  {
    return delay;
  }
  
  public long getDuration()
  {
    return duration;
  }
  
  public TimeInterpolator getInterpolator()
  {
    TimeInterpolator localTimeInterpolator = interpolator;
    if (localTimeInterpolator != null) {
      return localTimeInterpolator;
    }
    return AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR;
  }
  
  public int getRepeatCount()
  {
    return repeatCount;
  }
  
  public int getRepeatMode()
  {
    return repeatMode;
  }
  
  public int hashCode()
  {
    return ((((int)(getDelay() ^ getDelay() >>> 32) * 31 + (int)(getDuration() ^ getDuration() >>> 32)) * 31 + getInterpolator().getClass().hashCode()) * 31 + getRepeatCount()) * 31 + getRepeatMode();
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append('\n');
    localStringBuilder.append(getClass().getName());
    localStringBuilder.append('{');
    localStringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
    localStringBuilder.append(" delay: ");
    localStringBuilder.append(getDelay());
    localStringBuilder.append(" duration: ");
    localStringBuilder.append(getDuration());
    localStringBuilder.append(" interpolator: ");
    localStringBuilder.append(getInterpolator().getClass());
    localStringBuilder.append(" repeatCount: ");
    localStringBuilder.append(getRepeatCount());
    localStringBuilder.append(" repeatMode: ");
    localStringBuilder.append(getRepeatMode());
    localStringBuilder.append("}\n");
    return localStringBuilder.toString();
  }
}
