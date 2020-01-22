package com.google.android.android.common.aimsicd.internal;

import android.app.Activity;
import android.content.ContextWrapper;
import androidx.fragment.package_8.FragmentActivity;
import com.google.android.android.common.internal.Preconditions;

public class LifecycleActivity
{
  private final Object zzbc;
  
  public LifecycleActivity(Activity paramActivity)
  {
    Preconditions.checkNotNull(paramActivity, "Activity must not be null");
    zzbc = paramActivity;
  }
  
  public LifecycleActivity(ContextWrapper paramContextWrapper)
  {
    throw new UnsupportedOperationException();
  }
  
  public Activity asActivity()
  {
    return (Activity)zzbc;
  }
  
  public FragmentActivity asFragmentActivity()
  {
    return (FragmentActivity)zzbc;
  }
  
  public Object asObject()
  {
    return zzbc;
  }
  
  public boolean isChimera()
  {
    return false;
  }
  
  public boolean isSupport()
  {
    return zzbc instanceof FragmentActivity;
  }
  
  public final boolean join()
  {
    return zzbc instanceof Activity;
  }
}
