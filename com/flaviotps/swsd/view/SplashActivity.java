package com.flaviotps.swsd.view;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.package_8.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import com.flaviotps.swsd.model.AppInfoModel;
import com.flaviotps.swsd.utils.DialogUtils;
import com.flaviotps.swsd.utils.DialogUtils.Companion;
import com.flaviotps.swsd.utils.ParseUtils;
import com.flaviotps.swsd.utils.ParseUtils.Companion;
import com.flaviotps.swsd.viewmodel.SplashViewModel;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000.\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\002\n\002\b\005\n\002\030\002\n\002\b\004\n\002\020\013\n\000\030\0002\0020\001B\005?\006\002\020\002J\b\020\007\032\0020\bH\026J\b\020\t\032\0020\bH\026J\b\020\n\032\0020\bH\026J\b\020\013\032\0020\bH\002J\022\020\f\032\0020\b2\b\020\r\032\004\030\0010\016H\024J\b\020\017\032\0020\bH\024J\b\020\020\032\0020\bH\002J\b\020\021\032\0020\bH\002J\b\020\022\032\0020\023H\026R\016\020\003\032\0020\004X?.?\006\002\n\000R\016\020\005\032\0020\006X?.?\006\002\n\000?\006\024"}, d2={"Lcom/flaviotps/swsd/view/SplashActivity;", "Lcom/flaviotps/swsd/view/BaseActivity;", "()V", "energyImage", "Landroid/widget/ImageView;", "viewModel", "Lcom/flaviotps/swsd/viewmodel/SplashViewModel;", "afterStart", "", "bindListeners", "bindViews", "handleAppLaunch", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onStop", "openPlayStore", "setUpSplash", "showBackButton", "", "app_release"}, k=1, mv={1, 1, 16})
public final class SplashActivity
  extends BaseActivity
{
  private HashMap _$_findViewCache;
  private ImageView energyImage;
  private SplashViewModel viewModel;
  
  public SplashActivity() {}
  
  private final void handleAppLaunch()
  {
    TutorialActivity.Companion localCompanion = TutorialActivity.Companion;
    Context localContext = (Context)this;
    int i = localCompanion.getLaunchTimes(localContext);
    if (i >= 3)
    {
      BaseActivity.openActivity$default(this, MainActivity.class, null, 2, null);
      return;
    }
    TutorialActivity.Companion.setLaunchTimes(i + 1, localContext);
    BaseActivity.openActivity$default(this, TutorialActivity.class, null, 2, null);
  }
  
  private final void openPlayStore()
  {
    String str = getPackageName();
    try
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("market://details?id=");
      localStringBuilder.append(str);
      startActivity(new Intent("android.intent.action.VIEW", Uri.parse(localStringBuilder.toString())));
      return;
    }
    catch (ActivityNotFoundException localActivityNotFoundException)
    {
      StringBuilder localStringBuilder;
      for (;;) {}
    }
    localStringBuilder = new StringBuilder();
    localStringBuilder.append("https://play.google.com/store/apps/details?id=");
    localStringBuilder.append(str);
    startActivity(new Intent("android.intent.action.VIEW", Uri.parse(localStringBuilder.toString())));
  }
  
  private final void setUpSplash()
  {
    Intent localIntent = getIntent();
    Intrinsics.checkExpressionValueIsNotNull(localIntent, "intent");
    if ((localIntent.getFlags() & 0x400000) != 0)
    {
      finish();
      return;
    }
    getWindow().requestFeature(1);
    getWindow().setFlags(1024, 1024);
  }
  
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
    new Handler().postDelayed((Runnable)new Runnable()
    {
      public final void run()
      {
        Animation localAnimation = AnimationUtils.loadAnimation(getApplicationContext(), 2130771980);
        SplashActivity.access$getEnergyImage$p(SplashActivity.this).startAnimation(localAnimation);
        SplashActivity.access$getEnergyImage$p(SplashActivity.this).setVisibility(0);
      }
    }, 300L);
  }
  
  public void bindListeners()
  {
    SplashViewModel localSplashViewModel = viewModel;
    if (localSplashViewModel == null) {
      Intrinsics.throwUninitializedPropertyAccessException("viewModel");
    }
    localSplashViewModel.getAppInfo().observe((LifecycleOwner)this, (Observer)new Observer()
    {
      public final void onChanged(AppInfoModel paramAnonymousAppInfoModel)
      {
        Object localObject;
        BaseActivity localBaseActivity;
        if (paramAnonymousAppInfoModel.getMaintenance())
        {
          paramAnonymousAppInfoModel = DialogUtils.Companion;
          localObject = SplashActivity.this;
          localBaseActivity = (BaseActivity)localObject;
          localObject = ((Context)localObject).getString(2131820646);
          Intrinsics.checkExpressionValueIsNotNull(localObject, "getString(R.string.maintenance_warning)");
          DialogUtils.Companion.showWarningDialog$default(paramAnonymousAppInfoModel, localBaseActivity, (String)localObject, null, null, 12, null);
          return;
        }
        if (ParseUtils.Companion.versionToInt(paramAnonymousAppInfoModel.getMinVersion()) > ParseUtils.Companion.versionToInt("1.1.5"))
        {
          paramAnonymousAppInfoModel = DialogUtils.Companion;
          localObject = SplashActivity.this;
          localBaseActivity = (BaseActivity)localObject;
          localObject = ((Context)localObject).getString(2131820684);
          Intrinsics.checkExpressionValueIsNotNull(localObject, "getString(R.string.version_outdated)");
          String str = getString(2131820592);
          Intrinsics.checkExpressionValueIsNotNull(str, "getString( R.string.btn_update)");
          paramAnonymousAppInfoModel.showWarningDialog(localBaseActivity, (String)localObject, str, (View.OnClickListener)new View.OnClickListener()
          {
            public final void onClick(View paramAnonymous2View)
            {
              SplashActivity.access$openPlayStore(this$0.this$0);
            }
          });
          return;
        }
        SplashActivity.access$getViewModel$p(SplashActivity.this).load().observe((LifecycleOwner)SplashActivity.this, (Observer)new Observer()
        {
          public final void onChanged(String paramAnonymous2String)
          {
            if (paramAnonymous2String == null)
            {
              paramAnonymous2String = DialogUtils.Companion;
              BaseActivity localBaseActivity = (BaseActivity)this$0.this$0;
              String str = this$0.this$0.getString(2131820671);
              Intrinsics.checkExpressionValueIsNotNull(str, "getString(R.string.token_error)");
              paramAnonymous2String.showErrorDialog(localBaseActivity, str);
              return;
            }
            this$0.this$0.setSettings("PREF_TOKEN", paramAnonymous2String);
            SplashActivity.access$handleAppLaunch(this$0.this$0);
          }
        });
      }
    });
  }
  
  public void bindViews()
  {
    View localView = findViewById(2131361944);
    Intrinsics.checkExpressionValueIsNotNull(localView, "findViewById(R.id.ivEnergy)");
    energyImage = ((ImageView)localView);
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setUpSplash();
    setContentView(2131558432);
    paramBundle = getSupportActionBar();
    if (paramBundle != null) {
      paramBundle.hide();
    }
    paramBundle = ViewModelProviders.with((FragmentActivity)this).getName(SplashViewModel.class);
    Intrinsics.checkExpressionValueIsNotNull(paramBundle, "ViewModelProviders.of(th?ashViewModel::class.java]");
    viewModel = ((SplashViewModel)paramBundle);
  }
  
  protected void onStop()
  {
    super.onStop();
    finish();
  }
  
  public boolean showBackButton()
  {
    return false;
  }
}