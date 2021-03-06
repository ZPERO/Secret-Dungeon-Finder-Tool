package com.flaviotps.swsd.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.package_8.Fragment;
import androidx.fragment.package_8.FragmentActivity;
import androidx.fragment.package_8.FragmentManager;
import androidx.fragment.package_8.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import com.flaviotps.swsd.viewmodel.SettingsViewModel;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000 \n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\020\002\n\002\b\004\n\002\030\002\n\000\030\0002\0020\001B\005?\006\002\020\002J\b\020\005\032\0020\006H\026J\b\020\007\032\0020\006H\026J\b\020\b\032\0020\006H\026J\022\020\t\032\0020\0062\b\020\n\032\004\030\0010\013H\024R\016\020\003\032\0020\004X?.?\006\002\n\000?\006\f"}, d2={"Lcom/flaviotps/swsd/view/SettingsActivity;", "Lcom/flaviotps/swsd/view/BaseActivity;", "()V", "viewModel", "Lcom/flaviotps/swsd/viewmodel/SettingsViewModel;", "afterStart", "", "bindListeners", "bindViews", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "app_release"}, k=1, mv={1, 1, 16})
public final class SettingsActivity
  extends BaseActivity
{
  private HashMap _$_findViewCache;
  private SettingsViewModel viewModel;
  
  public SettingsActivity() {}
  
  public void _$_clearFindViewByIdCache()
  {
    HashMap localHashMap = _$_findViewCache;
    if (localHashMap != null) {
      localHashMap.clear();
    }
  }
  
  public View _$_findCachedViewById(int paramInt)
  {
    if (_$_findViewCache == null) {
      _$_findViewCache = new HashMap();
    }
    View localView2 = (View)_$_findViewCache.get(Integer.valueOf(paramInt));
    View localView1 = localView2;
    if (localView2 == null)
    {
      localView1 = findViewById(paramInt);
      _$_findViewCache.put(Integer.valueOf(paramInt), localView1);
    }
    return localView1;
  }
  
  public void afterStart()
  {
    getSupportFragmentManager().beginTransaction().replace(2131361886, (Fragment)new SettingsFragment()).commit();
  }
  
  public void bindListeners() {}
  
  public void bindViews() {}
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2131558430);
    paramBundle = ViewModelProviders.with((FragmentActivity)this).getName(SettingsViewModel.class);
    Intrinsics.checkExpressionValueIsNotNull(paramBundle, "ViewModelProviders.of(th?ngsViewModel::class.java]");
    viewModel = ((SettingsViewModel)paramBundle);
  }
}
