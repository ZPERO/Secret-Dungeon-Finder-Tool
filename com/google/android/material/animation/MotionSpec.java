package com.google.android.material.animation;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.Log;
import androidx.collection.SimpleArrayMap;
import java.util.ArrayList;
import java.util.List;

public class MotionSpec
{
  private static final String PAGE_KEY = "MotionSpec";
  private final SimpleArrayMap<String, MotionTiming> timings = new SimpleArrayMap();
  
  public MotionSpec() {}
  
  private static void addTimingFromAnimator(MotionSpec paramMotionSpec, Animator paramAnimator)
  {
    if ((paramAnimator instanceof ObjectAnimator))
    {
      paramAnimator = (ObjectAnimator)paramAnimator;
      paramMotionSpec.setTiming(paramAnimator.getPropertyName(), MotionTiming.createFromAnimator(paramAnimator));
      return;
    }
    paramMotionSpec = new StringBuilder();
    paramMotionSpec.append("Animator must be an ObjectAnimator: ");
    paramMotionSpec.append(paramAnimator);
    throw new IllegalArgumentException(paramMotionSpec.toString());
  }
  
  public static MotionSpec createFromAttribute(Context paramContext, TypedArray paramTypedArray, int paramInt)
  {
    if (paramTypedArray.hasValue(paramInt))
    {
      paramInt = paramTypedArray.getResourceId(paramInt, 0);
      if (paramInt != 0) {
        return createFromResource(paramContext, paramInt);
      }
    }
    return null;
  }
  
  public static MotionSpec createFromResource(Context paramContext, int paramInt)
  {
    try
    {
      paramContext = AnimatorInflater.loadAnimator(paramContext, paramInt);
      if ((paramContext instanceof AnimatorSet))
      {
        paramContext = (AnimatorSet)paramContext;
        paramContext = createSpecFromAnimators(paramContext.getChildAnimations());
        return paramContext;
      }
      if (paramContext != null)
      {
        localObject = new ArrayList();
        ((List)localObject).add(paramContext);
        paramContext = createSpecFromAnimators((List)localObject);
        return paramContext;
      }
      return null;
    }
    catch (Exception paramContext)
    {
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("Can't load animation resource ID #0x");
      ((StringBuilder)localObject).append(Integer.toHexString(paramInt));
      Log.w("MotionSpec", ((StringBuilder)localObject).toString(), paramContext);
    }
    return null;
  }
  
  private static MotionSpec createSpecFromAnimators(List paramList)
  {
    MotionSpec localMotionSpec = new MotionSpec();
    int j = paramList.size();
    int i = 0;
    while (i < j)
    {
      addTimingFromAnimator(localMotionSpec, (Animator)paramList.get(i));
      i += 1;
    }
    return localMotionSpec;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if ((paramObject != null) && (getClass() == paramObject.getClass()))
    {
      paramObject = (MotionSpec)paramObject;
      return timings.equals(timings);
    }
    return false;
  }
  
  public MotionTiming getTiming(String paramString)
  {
    if (hasTiming(paramString)) {
      return (MotionTiming)timings.get(paramString);
    }
    throw new IllegalArgumentException();
  }
  
  public long getTotalDuration()
  {
    int j = timings.size();
    long l = 0L;
    int i = 0;
    while (i < j)
    {
      MotionTiming localMotionTiming = (MotionTiming)timings.valueAt(i);
      l = Math.max(l, localMotionTiming.getDelay() + localMotionTiming.getDuration());
      i += 1;
    }
    return l;
  }
  
  public boolean hasTiming(String paramString)
  {
    return timings.get(paramString) != null;
  }
  
  public int hashCode()
  {
    return timings.hashCode();
  }
  
  public void setTiming(String paramString, MotionTiming paramMotionTiming)
  {
    timings.put(paramString, paramMotionTiming);
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append('\n');
    localStringBuilder.append(getClass().getName());
    localStringBuilder.append('{');
    localStringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
    localStringBuilder.append(" timings: ");
    localStringBuilder.append(timings);
    localStringBuilder.append("}\n");
    return localStringBuilder.toString();
  }
}
