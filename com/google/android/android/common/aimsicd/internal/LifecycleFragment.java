package com.google.android.android.common.aimsicd.internal;

import android.app.Activity;
import android.content.Intent;

public abstract interface LifecycleFragment
{
  public abstract void addCallback(String paramString, LifecycleCallback paramLifecycleCallback);
  
  public abstract LifecycleCallback getCallbackOrNull(String paramString, Class paramClass);
  
  public abstract Activity getLifecycleActivity();
  
  public abstract boolean isCreated();
  
  public abstract boolean isStarted();
  
  public abstract void startActivityForResult(Intent paramIntent, int paramInt);
}
