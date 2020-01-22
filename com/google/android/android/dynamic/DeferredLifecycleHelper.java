package com.google.android.android.dynamic;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.android.common.GoogleApiAvailability;
import com.google.android.android.common.internal.ConnectionErrorMessages;
import java.util.LinkedList;

public abstract class DeferredLifecycleHelper<T extends com.google.android.gms.dynamic.LifecycleDelegate>
{
  private T zare;
  private Bundle zarf;
  private LinkedList<com.google.android.gms.dynamic.DeferredLifecycleHelper.zaa> zarg;
  private final com.google.android.gms.dynamic.OnDelegateCreatedListener<T> zarh = new ContentSource(this);
  
  public DeferredLifecycleHelper() {}
  
  private final void next(int paramInt)
  {
    while ((!zarg.isEmpty()) && (((zaa)zarg.getLast()).getState() >= paramInt)) {
      zarg.removeLast();
    }
  }
  
  private final void set(Bundle paramBundle, zaa paramZaa)
  {
    LifecycleDelegate localLifecycleDelegate = zare;
    if (localLifecycleDelegate != null)
    {
      paramZaa.makeView(localLifecycleDelegate);
      return;
    }
    if (zarg == null) {
      zarg = new LinkedList();
    }
    zarg.add(paramZaa);
    if (paramBundle != null)
    {
      paramZaa = zarf;
      if (paramZaa == null) {
        zarf = ((Bundle)paramBundle.clone());
      } else {
        paramZaa.putAll(paramBundle);
      }
    }
    createDelegate(zarh);
  }
  
  public static void showGooglePlayUnavailableMessage(FrameLayout paramFrameLayout)
  {
    Object localObject = GoogleApiAvailability.getInstance();
    Context localContext = paramFrameLayout.getContext();
    int i = ((GoogleApiAvailability)localObject).isGooglePlayServicesAvailable(localContext);
    String str2 = ConnectionErrorMessages.getErrorMessage(localContext, i);
    String str1 = ConnectionErrorMessages.getErrorDialogButtonMessage(localContext, i);
    LinearLayout localLinearLayout = new LinearLayout(paramFrameLayout.getContext());
    localLinearLayout.setOrientation(1);
    localLinearLayout.setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
    paramFrameLayout.addView(localLinearLayout);
    paramFrameLayout = new TextView(paramFrameLayout.getContext());
    paramFrameLayout.setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
    paramFrameLayout.setText(str2);
    localLinearLayout.addView(paramFrameLayout);
    paramFrameLayout = ((GoogleApiAvailability)localObject).getErrorResolutionIntent(localContext, i, null);
    if (paramFrameLayout != null)
    {
      localObject = new Button(localContext);
      ((View)localObject).setId(16908313);
      ((View)localObject).setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
      ((TextView)localObject).setText(str1);
      localLinearLayout.addView((View)localObject);
      ((View)localObject).setOnClickListener(new Utilities.1(localContext, paramFrameLayout));
    }
  }
  
  protected abstract void createDelegate(OnDelegateCreatedListener paramOnDelegateCreatedListener);
  
  public LifecycleDelegate getDelegate()
  {
    return zare;
  }
  
  protected void handleGooglePlayUnavailable(FrameLayout paramFrameLayout)
  {
    showGooglePlayUnavailableMessage(paramFrameLayout);
  }
  
  public void onCreate(Bundle paramBundle)
  {
    set(paramBundle, new Call(this, paramBundle));
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    FrameLayout localFrameLayout = new FrameLayout(paramLayoutInflater.getContext());
    set(paramBundle, new PlaylistFragment.2(this, localFrameLayout, paramLayoutInflater, paramViewGroup, paramBundle));
    if (zare == null) {
      handleGooglePlayUnavailable(localFrameLayout);
    }
    return localFrameLayout;
  }
  
  public void onDestroy()
  {
    LifecycleDelegate localLifecycleDelegate = zare;
    if (localLifecycleDelegate != null)
    {
      localLifecycleDelegate.onDestroy();
      return;
    }
    next(1);
  }
  
  public void onDestroyView()
  {
    LifecycleDelegate localLifecycleDelegate = zare;
    if (localLifecycleDelegate != null)
    {
      localLifecycleDelegate.onDestroyView();
      return;
    }
    next(2);
  }
  
  public void onInflate(Activity paramActivity, Bundle paramBundle1, Bundle paramBundle2)
  {
    set(paramBundle2, new BackgroundService.1(this, paramActivity, paramBundle1, paramBundle2));
  }
  
  public void onLowMemory()
  {
    LifecycleDelegate localLifecycleDelegate = zare;
    if (localLifecycleDelegate != null) {
      localLifecycleDelegate.onLowMemory();
    }
  }
  
  public void onPause()
  {
    LifecycleDelegate localLifecycleDelegate = zare;
    if (localLifecycleDelegate != null)
    {
      localLifecycleDelegate.onPause();
      return;
    }
    next(5);
  }
  
  public void onResume()
  {
    set(null, new AudioRecorder(this));
  }
  
  public void onSaveInstanceState(Bundle paramBundle)
  {
    Object localObject = zare;
    if (localObject != null)
    {
      ((LifecycleDelegate)localObject).onSaveInstanceState(paramBundle);
      return;
    }
    localObject = zarf;
    if (localObject != null) {
      paramBundle.putAll((Bundle)localObject);
    }
  }
  
  public void onStart()
  {
    set(null, new MainActivity.4(this));
  }
  
  public void onStop()
  {
    LifecycleDelegate localLifecycleDelegate = zare;
    if (localLifecycleDelegate != null)
    {
      localLifecycleDelegate.onStop();
      return;
    }
    next(4);
  }
  
  abstract interface zaa
  {
    public abstract int getState();
    
    public abstract void makeView(LifecycleDelegate paramLifecycleDelegate);
  }
}
