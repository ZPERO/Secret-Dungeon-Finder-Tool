package com.flaviotps.swsd.view;

import ZoomOutPageTransformer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.package_8.FragmentActivity;
import androidx.fragment.package_8.FragmentManager;
import androidx.preference.PreferenceManager;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;
import androidx.viewpager.widget.ViewPager.PageTransformer;
import com.flaviotps.swsd.adapters.TutorialPageAdapter;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000H\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\020\013\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\002\n\002\b\003\n\002\020\b\n\002\b\003\n\002\030\002\n\002\b\005\n\002\020\007\n\002\b\006\030\000  2\0020\0012\0020\002:\001 B\005?\006\002\020\003J\b\020\f\032\0020\rH\026J\b\020\016\032\0020\rH\026J\b\020\017\032\0020\rH\026J\b\020\020\032\0020\021H\002J\b\020\022\032\0020\rH\026J\022\020\023\032\0020\r2\b\020\024\032\004\030\0010\025H\024J\020\020\026\032\0020\r2\006\020\027\032\0020\021H\026J \020\030\032\0020\r2\006\020\031\032\0020\0212\006\020\032\032\0020\0332\006\020\034\032\0020\021H\026J\020\020\035\032\0020\r2\006\020\031\032\0020\021H\026J\b\020\036\032\0020\rH\002J\b\020\037\032\0020\007H\026R\016\020\004\032\0020\005X?.?\006\002\n\000R\016\020\006\032\0020\007X?\016?\006\002\n\000R\016\020\b\032\0020\tX?.?\006\002\n\000R\016\020\n\032\0020\013X?.?\006\002\n\000?\006!"}, d2={"Lcom/flaviotps/swsd/view/TutorialActivity;", "Lcom/flaviotps/swsd/view/BaseActivity;", "Landroidx/viewpager/widget/ViewPager$OnPageChangeListener;", "()V", "btnNext", "Landroid/widget/Button;", "finishAfterDone", "", "tutorialPagerAdapter", "Lcom/flaviotps/swsd/adapters/TutorialPageAdapter;", "viewPager", "Landroidx/viewpager/widget/ViewPager;", "afterStart", "", "bindListeners", "bindViews", "getNext", "", "onBackPressed", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onPageScrollStateChanged", "state", "onPageScrolled", "position", "positionOffset", "", "positionOffsetPixels", "onPageSelected", "setupTutorial", "showBackButton", "Companion", "app_release"}, k=1, mv={1, 1, 16})
public final class TutorialActivity
  extends BaseActivity
  implements ViewPager.OnPageChangeListener
{
  private static final String APP_LAUNCH_TIMES = "APP_LAUNCH_TIMES";
  public static final Companion Companion = new Companion(null);
  public static final String FINISH_AFTER_DONE = "finishAfterDone";
  private static final int LAST_PAGE = -1;
  public static final int SHOW_TUTORIAL_MAX_TIMES = 3;
  private HashMap _$_findViewCache;
  private Button btnNext;
  private boolean finishAfterDone;
  private TutorialPageAdapter tutorialPagerAdapter;
  private ViewPager viewPager;
  
  public TutorialActivity() {}
  
  private final int getNext()
  {
    Object localObject = viewPager;
    if (localObject == null) {
      Intrinsics.throwUninitializedPropertyAccessException("viewPager");
    }
    int i = ((ViewPager)localObject).getCurrentItem() + 1;
    localObject = viewPager;
    if (localObject == null) {
      Intrinsics.throwUninitializedPropertyAccessException("viewPager");
    }
    localObject = ((ViewPager)localObject).getAdapter();
    if (localObject != null) {
      localObject = Integer.valueOf(((PagerAdapter)localObject).getCount());
    } else {
      localObject = null;
    }
    if ((localObject != null) && (((Integer)localObject).intValue() > i)) {
      return i;
    }
    return -1;
  }
  
  private final void setupTutorial()
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
    Object localObject = getSupportFragmentManager();
    Intrinsics.checkExpressionValueIsNotNull(localObject, "supportFragmentManager");
    tutorialPagerAdapter = new TutorialPageAdapter((FragmentManager)localObject, 1);
    localObject = viewPager;
    if (localObject == null) {
      Intrinsics.throwUninitializedPropertyAccessException("viewPager");
    }
    TutorialPageAdapter localTutorialPageAdapter = tutorialPagerAdapter;
    if (localTutorialPageAdapter == null) {
      Intrinsics.throwUninitializedPropertyAccessException("tutorialPagerAdapter");
    }
    ((ViewPager)localObject).setAdapter((PagerAdapter)localTutorialPageAdapter);
    localObject = viewPager;
    if (localObject == null) {
      Intrinsics.throwUninitializedPropertyAccessException("viewPager");
    }
    ((ViewPager)localObject).setPageTransformer(true, (ViewPager.PageTransformer)new ZoomOutPageTransformer());
  }
  
  public void bindListeners()
  {
    Object localObject = viewPager;
    if (localObject == null) {
      Intrinsics.throwUninitializedPropertyAccessException("viewPager");
    }
    ((ViewPager)localObject).addOnPageChangeListener((ViewPager.OnPageChangeListener)this);
    localObject = btnNext;
    if (localObject == null) {
      Intrinsics.throwUninitializedPropertyAccessException("btnNext");
    }
    ((View)localObject).setOnClickListener((View.OnClickListener)new View.OnClickListener()
    {
      public final void onClick(View paramAnonymousView)
      {
        if (TutorialActivity.access$getNext(TutorialActivity.this) == -1)
        {
          if (TutorialActivity.access$getFinishAfterDone$p(TutorialActivity.this))
          {
            finish();
            return;
          }
          BaseActivity.openActivity$default(TutorialActivity.this, MainActivity.class, null, 2, null);
          return;
        }
        TutorialActivity.access$getViewPager$p(TutorialActivity.this).setCurrentItem(TutorialActivity.access$getNext(TutorialActivity.this), true);
      }
    });
  }
  
  public void bindViews()
  {
    View localView = findViewById(2131362097);
    Intrinsics.checkExpressionValueIsNotNull(localView, "findViewById(R.id.vp_tutorial)");
    viewPager = ((ViewPager)localView);
    localView = findViewById(2131361868);
    Intrinsics.checkExpressionValueIsNotNull(localView, "findViewById(R.id.btnNext)");
    btnNext = ((Button)localView);
  }
  
  public void onBackPressed() {}
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setupTutorial();
    setContentView(2131558433);
    ActionBar localActionBar = getSupportActionBar();
    if (localActionBar != null) {
      localActionBar.hide();
    }
    if (paramBundle != null) {
      finishAfterDone = paramBundle.getBoolean("finishAfterDone", false);
    }
  }
  
  public void onPageScrollStateChanged(int paramInt) {}
  
  public void onPageScrolled(int paramInt1, float paramFloat, int paramInt2) {}
  
  public void onPageSelected(int paramInt)
  {
    if (getNext() == -1)
    {
      localButton = btnNext;
      if (localButton == null) {
        Intrinsics.throwUninitializedPropertyAccessException("btnNext");
      }
      localButton.setText((CharSequence)getString(2131820677));
      return;
    }
    Button localButton = btnNext;
    if (localButton == null) {
      Intrinsics.throwUninitializedPropertyAccessException("btnNext");
    }
    localButton.setText((CharSequence)getString(2131820650));
  }
  
  public boolean showBackButton()
  {
    return false;
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000*\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\016\n\002\b\002\n\002\020\b\n\002\b\003\n\002\030\002\n\000\n\002\020\002\n\002\b\002\b?\003\030\0002\0020\001B\007\b\002?\006\002\020\002J\016\020\t\032\0020\0072\006\020\n\032\0020\013J\030\020\f\032\0020\r2\b\b\002\020\016\032\0020\0072\006\020\n\032\0020\013R\016\020\003\032\0020\004X?T?\006\002\n\000R\016\020\005\032\0020\004X?T?\006\002\n\000R\016\020\006\032\0020\007X?T?\006\002\n\000R\016\020\b\032\0020\007X?T?\006\002\n\000?\006\017"}, d2={"Lcom/flaviotps/swsd/view/TutorialActivity$Companion;", "", "()V", "APP_LAUNCH_TIMES", "", "FINISH_AFTER_DONE", "LAST_PAGE", "", "SHOW_TUTORIAL_MAX_TIMES", "getLaunchTimes", "context", "Landroid/content/Context;", "setLaunchTimes", "", "times", "app_release"}, k=1, mv={1, 1, 16})
  public static final class Companion
  {
    private Companion() {}
    
    public final int getLaunchTimes(Context paramContext)
    {
      Intrinsics.checkParameterIsNotNull(paramContext, "context");
      return PreferenceManager.getDefaultSharedPreferences(paramContext).getInt("APP_LAUNCH_TIMES", 0);
    }
    
    public final void setLaunchTimes(int paramInt, Context paramContext)
    {
      Intrinsics.checkParameterIsNotNull(paramContext, "context");
      PreferenceManager.getDefaultSharedPreferences(paramContext).edit().putInt("APP_LAUNCH_TIMES", paramInt).apply();
    }
  }
}
