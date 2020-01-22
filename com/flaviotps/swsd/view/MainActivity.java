package com.flaviotps.swsd.view;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.package_8.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;
import com.airbnb.lottie.LottieAnimationView;
import com.flaviotps.swsd.adapters.SecretDungeonRecyclerAdapter;
import com.flaviotps.swsd.adapters.SecretDungeonRecyclerAdapter.SdViewHolder;
import com.flaviotps.swsd.interfaces.SecretDungeonItem;
import com.flaviotps.swsd.model.SecretDungeonModel;
import com.flaviotps.swsd.utils.DialogUtils;
import com.flaviotps.swsd.utils.DialogUtils.Companion;
import com.flaviotps.swsd.viewmodel.MainViewModel;
import com.google.android.material.snackbar.Snackbar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.text.StringsKt__StringsJVMKt;

@Metadata(bv={1, 0, 3}, d1={"\000d\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\002\n\002\b\004\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\004\n\002\030\002\n\000\n\002\020\013\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\007\030\0002\0020\001B\005?\006\002\020\002J\b\020\017\032\0020\020H\026J\b\020\021\032\0020\020H\026J\b\020\022\032\0020\020H\026J\020\020\023\032\0020\0202\006\020\024\032\0020\025H\002J\020\020\026\032\0020\0202\006\020\027\032\0020\030H\002J\b\020\031\032\0020\020H\002J\b\020\032\032\0020\020H\026J\022\020\033\032\0020\0202\b\020\034\032\004\030\0010\035H\024J\022\020\036\032\0020\0372\b\020 \032\004\030\0010!H\026J\020\020\"\032\0020\0372\006\020#\032\0020$H\026J\b\020%\032\0020\020H\002J\b\020&\032\0020\037H\026J\b\020'\032\0020\020H\002J\b\020(\032\0020\020H\002J\b\020)\032\0020\020H\002J\b\020*\032\0020\020H\002R\016\020\003\032\0020\004X?.?\006\002\n\000R\016\020\005\032\0020\006X?.?\006\002\n\000R\016\020\007\032\0020\bX?.?\006\002\n\000R\016\020\t\032\0020\nX?.?\006\002\n\000R\020\020\013\032\004\030\0010\fX?\016?\006\002\n\000R\016\020\r\032\0020\016X?.?\006\002\n\000?\006+"}, d2={"Lcom/flaviotps/swsd/view/MainActivity;", "Lcom/flaviotps/swsd/view/BaseActivity;", "()V", "adapter", "Lcom/flaviotps/swsd/adapters/SecretDungeonRecyclerAdapter;", "coordinator", "Landroidx/coordinatorlayout/widget/CoordinatorLayout;", "lottieAnimationView", "Lcom/airbnb/lottie/LottieAnimationView;", "recyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "recyclerViewState", "Landroid/os/Parcelable;", "viewModel", "Lcom/flaviotps/swsd/viewmodel/MainViewModel;", "afterStart", "", "bindListeners", "bindViews", "handleCopyClick", "sdViewHolder", "Lcom/flaviotps/swsd/adapters/SecretDungeonRecyclerAdapter$SdViewHolder;", "handleShareClick", "secretDungeonModel", "Lcom/flaviotps/swsd/model/SecretDungeonModel;", "hideLoading", "onBackPressed", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onCreateOptionsMenu", "", "menu", "Landroid/view/Menu;", "onOptionsItemSelected", "item", "Landroid/view/MenuItem;", "shareApp", "showBackButton", "showDeleteConfirmation", "showFilterWarning", "showLoading", "showNoDungeons", "app_release"}, k=1, mv={1, 1, 16})
public final class MainActivity
  extends BaseActivity
{
  private HashMap _$_findViewCache;
  private SecretDungeonRecyclerAdapter adapter;
  private CoordinatorLayout coordinator;
  private LottieAnimationView lottieAnimationView;
  private RecyclerView recyclerView;
  private Parcelable recyclerViewState;
  private MainViewModel viewModel;
  
  public MainActivity() {}
  
  private final void handleCopyClick(SecretDungeonRecyclerAdapter.SdViewHolder paramSdViewHolder)
  {
    int i = 0;
    Object localObject1 = (String)BaseActivity.getSettings$default(this, "app_nickname", false, 2, null);
    if ((String)BaseActivity.getSettings$default(this, "PREF_TOKEN", false, 2, null) != null)
    {
      Object localObject2 = (CharSequence)localObject1;
      if ((localObject2 == null) || (StringsKt__StringsJVMKt.isBlank((CharSequence)localObject2))) {
        i = 1;
      }
      if (i != 0)
      {
        paramSdViewHolder = DialogUtils.Companion;
        localObject1 = (BaseActivity)this;
        localObject2 = getString(2131820618);
        Intrinsics.checkExpressionValueIsNotNull(localObject2, "getString(R.string.copy_nickname_not_set)");
        paramSdViewHolder.showSettingsDialog((BaseActivity)localObject1, (String)localObject2);
        return;
      }
      localObject2 = getSystemService("clipboard");
      if (localObject2 != null)
      {
        localObject2 = (ClipboardManager)localObject2;
        Object localObject3 = ClipData.newPlainText((CharSequence)getString(2131820617), (CharSequence)paramSdViewHolder.getSecretDungeonModel$app_release().getInGameName());
        Intrinsics.checkExpressionValueIsNotNull(localObject3, "ClipData.newPlainText(\n ?ame\n                    )");
        ((ClipboardManager)localObject2).setPrimaryClip((ClipData)localObject3);
        localObject2 = coordinator;
        if (localObject2 == null) {
          Intrinsics.throwUninitializedPropertyAccessException("coordinator");
        }
        localObject2 = (View)localObject2;
        localObject3 = getString(2131820615);
        Intrinsics.checkExpressionValueIsNotNull(localObject3, "getString(R.string.copied_clipboard)");
        Snackbar.make((View)localObject2, (CharSequence)StringsKt__StringsJVMKt.replace$default((String)localObject3, "%s", paramSdViewHolder.getSecretDungeonModel$app_release().getInGameName(), false, 4, null), -1).show();
        localObject2 = viewModel;
        if (localObject2 == null) {
          Intrinsics.throwUninitializedPropertyAccessException("viewModel");
        }
        ((MainViewModel)localObject2).requestFriend(paramSdViewHolder.getSecretDungeonModel$app_release(), (String)localObject1);
        paramSdViewHolder.disableCopyButton();
        return;
      }
      throw new TypeCastException("null cannot be cast to non-null type android.content.ClipboardManager");
    }
  }
  
  private final void handleShareClick(SecretDungeonModel paramSecretDungeonModel)
  {
    paramSecretDungeonModel = new Intent("android.intent.action.SEND");
    paramSecretDungeonModel.setType("text/plain");
    paramSecretDungeonModel.addFlags(67108864);
    paramSecretDungeonModel.putExtra("android.intent.extra.SUBJECT", getString(2131820667));
    paramSecretDungeonModel.putExtra("android.intent.extra.TEXT", getString(2131820588));
    startActivity(Intent.createChooser(paramSecretDungeonModel, (CharSequence)getString(2131820586)));
  }
  
  private final void hideLoading()
  {
    Object localObject = recyclerView;
    if (localObject == null) {
      Intrinsics.throwUninitializedPropertyAccessException("recyclerView");
    }
    ((View)localObject).setVisibility(0);
    localObject = lottieAnimationView;
    if (localObject == null) {
      Intrinsics.throwUninitializedPropertyAccessException("lottieAnimationView");
    }
    ((ImageView)localObject).setVisibility(4);
  }
  
  private final void shareApp()
  {
    Intent localIntent = new Intent("android.intent.action.SEND");
    localIntent.setType("text/plain");
    localIntent.addFlags(67108864);
    localIntent.putExtra("android.intent.extra.SUBJECT", getString(2131820587));
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(getString(2131820587));
    localStringBuilder.append('\n');
    localStringBuilder.append(getString(2131820588));
    localIntent.putExtra("android.intent.extra.TEXT", localStringBuilder.toString());
    startActivity(Intent.createChooser(localIntent, (CharSequence)getString(2131820586)));
  }
  
  private final void showDeleteConfirmation()
  {
    CoordinatorLayout localCoordinatorLayout = coordinator;
    if (localCoordinatorLayout == null) {
      Intrinsics.throwUninitializedPropertyAccessException("coordinator");
    }
    Snackbar.make((View)localCoordinatorLayout, (CharSequence)getString(2131820623), -1).show();
  }
  
  private final void showFilterWarning()
  {
    CoordinatorLayout localCoordinatorLayout = coordinator;
    if (localCoordinatorLayout == null) {
      Intrinsics.throwUninitializedPropertyAccessException("coordinator");
    }
    Snackbar.make((View)localCoordinatorLayout, (CharSequence)getString(2131820636), -2).setAction(2131820665, (View.OnClickListener)new View.OnClickListener()
    {
      public final void onClick(View paramAnonymousView)
      {
        BaseActivity.openActivity$default(MainActivity.this, SettingsActivity.class, null, 2, null);
      }
    }).show();
  }
  
  private final void showLoading()
  {
    Object localObject = recyclerView;
    if (localObject == null) {
      Intrinsics.throwUninitializedPropertyAccessException("recyclerView");
    }
    ((View)localObject).setVisibility(4);
    localObject = lottieAnimationView;
    if (localObject == null) {
      Intrinsics.throwUninitializedPropertyAccessException("lottieAnimationView");
    }
    ((ImageView)localObject).setVisibility(0);
  }
  
  private final void showNoDungeons()
  {
    CoordinatorLayout localCoordinatorLayout = coordinator;
    if (localCoordinatorLayout == null) {
      Intrinsics.throwUninitializedPropertyAccessException("coordinator");
    }
    Snackbar.make((View)localCoordinatorLayout, (CharSequence)getString(2131820651), -2).setAction(2131820544, (View.OnClickListener)showNoDungeons.1.INSTANCE).show();
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
    showLoading();
  }
  
  public void bindListeners()
  {
    MainViewModel localMainViewModel = viewModel;
    if (localMainViewModel == null) {
      Intrinsics.throwUninitializedPropertyAccessException("viewModel");
    }
    localMainViewModel.getSecretDungeons().observe((LifecycleOwner)this, (Observer)new Observer()
    {
      public final void onChanged(List paramAnonymousList)
      {
        Object localObject2 = MainActivity.this;
        Object localObject1 = MainActivity.access$getRecyclerView$p((MainActivity)localObject2).getLayoutManager();
        if (localObject1 != null) {
          localObject1 = ((RecyclerView.LayoutManager)localObject1).onSaveInstanceState();
        } else {
          localObject1 = null;
        }
        MainActivity.access$setRecyclerViewState$p((MainActivity)localObject2, (Parcelable)localObject1);
        MainActivity.access$hideLoading(MainActivity.this);
        localObject1 = (Set)getSettings("filter_server", true);
        localObject2 = (Set)getSettings("filter_elements", true);
        Set localSet = (Set)getSettings("filter_monsters", true);
        MainViewModel localMainViewModel = MainActivity.access$getViewModel$p(MainActivity.this);
        Intrinsics.checkExpressionValueIsNotNull(paramAnonymousList, "list");
        localObject1 = localMainViewModel.filter(paramAnonymousList, (Set)localObject1, (Set)localObject2, localSet);
        if (paramAnonymousList.isEmpty()) {
          MainActivity.access$showNoDungeons(MainActivity.this);
        }
        if ((((List)localObject1).isEmpty()) && ((((Collection)paramAnonymousList).isEmpty() ^ true))) {
          MainActivity.access$showFilterWarning(MainActivity.this);
        }
        paramAnonymousList = (String)BaseActivity.getSettings$default(MainActivity.this, "PREF_TOKEN", false, 2, null);
        localObject2 = MainActivity.this;
        MainActivity.access$setAdapter$p((MainActivity)localObject2, new SecretDungeonRecyclerAdapter(paramAnonymousList, (List)localObject1, (Context)localObject2, (SecretDungeonItem)new SecretDungeonItem()
        {
          public void onCopyClicked(SecretDungeonRecyclerAdapter.SdViewHolder paramAnonymous2SdViewHolder)
          {
            Intrinsics.checkParameterIsNotNull(paramAnonymous2SdViewHolder, "sdViewHolder");
            MainActivity.access$handleCopyClick(this$0.this$0, paramAnonymous2SdViewHolder);
          }
          
          public void onDeleteClicked(SecretDungeonModel paramAnonymous2SecretDungeonModel)
          {
            Intrinsics.checkParameterIsNotNull(paramAnonymous2SecretDungeonModel, "secretDungeonModel");
            MainActivity.access$getViewModel$p(this$0.this$0).delete(paramAnonymous2SecretDungeonModel).observe((LifecycleOwner)this$0.this$0, (Observer)new Observer()
            {
              public final void onChanged(SecretDungeonModel paramAnonymous3SecretDungeonModel)
              {
                MainActivity.access$showDeleteConfirmation(this$0.this$0);
              }
            });
            MainActivity.access$getViewModel$p(this$0.this$0).removeFromList(paramAnonymous2SecretDungeonModel);
          }
          
          public void onExpired()
          {
            MainActivity.access$getViewModel$p(this$0.this$0).clearExpired();
          }
          
          public void onReportClicked(SecretDungeonModel paramAnonymous2SecretDungeonModel)
          {
            Intrinsics.checkParameterIsNotNull(paramAnonymous2SecretDungeonModel, "secretDungeonModel");
            MainActivity.access$getViewModel$p(this$0.this$0).report(paramAnonymous2SecretDungeonModel);
          }
          
          public void onShareClicked(SecretDungeonModel paramAnonymous2SecretDungeonModel)
          {
            Intrinsics.checkParameterIsNotNull(paramAnonymous2SecretDungeonModel, "secretDungeonModel");
            MainActivity.access$handleShareClick(this$0.this$0, paramAnonymous2SecretDungeonModel);
          }
        }));
        MainActivity.access$getAdapter$p(MainActivity.this).notifyDataSetChanged();
        MainActivity.access$getRecyclerView$p(MainActivity.this).setHasFixedSize(true);
        MainActivity.access$getRecyclerView$p(MainActivity.this).setAdapter((RecyclerView.Adapter)MainActivity.access$getAdapter$p(MainActivity.this));
        MainActivity.access$getRecyclerView$p(MainActivity.this).setLayoutManager((RecyclerView.LayoutManager)new LinearLayoutManager((Context)MainActivity.this));
        paramAnonymousList = MainActivity.access$getRecyclerView$p(MainActivity.this).getLayoutManager();
        if (paramAnonymousList != null) {
          paramAnonymousList.onRestoreInstanceState(MainActivity.access$getRecyclerViewState$p(MainActivity.this));
        }
      }
    });
  }
  
  public void bindViews()
  {
    View localView = findViewById(2131361990);
    Intrinsics.checkExpressionValueIsNotNull(localView, "findViewById(R.id.recycler)");
    recyclerView = ((RecyclerView)localView);
    localView = findViewById(2131361954);
    Intrinsics.checkExpressionValueIsNotNull(localView, "findViewById(R.id.list_loading)");
    lottieAnimationView = ((LottieAnimationView)localView);
    localView = findViewById(2131361889);
    Intrinsics.checkExpressionValueIsNotNull(localView, "findViewById(R.id.coordinator)");
    coordinator = ((CoordinatorLayout)localView);
  }
  
  public void onBackPressed() {}
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2131558429);
    paramBundle = ViewModelProviders.with((FragmentActivity)this).getName(MainViewModel.class);
    Intrinsics.checkExpressionValueIsNotNull(paramBundle, "ViewModelProviders.of(th?ainViewModel::class.java]");
    viewModel = ((MainViewModel)paramBundle);
  }
  
  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    getMenuInflater().inflate(2131623936, paramMenu);
    return true;
  }
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    Intrinsics.checkParameterIsNotNull(paramMenuItem, "item");
    int i = paramMenuItem.getItemId();
    int j = 0;
    switch (i)
    {
    default: 
      return false;
    case 2131362086: 
      openActivity(TutorialActivity.class, (Function1)onOptionsItemSelected.1.INSTANCE);
      return true;
    case 2131362023: 
      shareApp();
      return true;
    case 2131362021: 
      BaseActivity.openActivity$default(this, SettingsActivity.class, null, 2, null);
      return true;
    }
    paramMenuItem = (CharSequence)BaseActivity.getSettings$default(this, "app_nickname", false, 2, null);
    if ((paramMenuItem != null) && (!StringsKt__StringsJVMKt.isBlank(paramMenuItem))) {
      i = 0;
    } else {
      i = 1;
    }
    BaseActivity localBaseActivity;
    String str;
    if (i != 0)
    {
      paramMenuItem = DialogUtils.Companion;
      localBaseActivity = (BaseActivity)this;
      str = getString(2131820619);
      Intrinsics.checkExpressionValueIsNotNull(str, "getString(R.string.creat?dungeon_nickname_not_set)");
      paramMenuItem.showSettingsDialog(localBaseActivity, str);
      return true;
    }
    paramMenuItem = (CharSequence)BaseActivity.getSettings$default(this, "server_location", false, 2, null);
    if (paramMenuItem != null)
    {
      i = j;
      if (paramMenuItem.length() != 0) {}
    }
    else
    {
      i = 1;
    }
    if (i != 0)
    {
      paramMenuItem = DialogUtils.Companion;
      localBaseActivity = (BaseActivity)this;
      str = getString(2131820620);
      Intrinsics.checkExpressionValueIsNotNull(str, "getString(R.string.create_dungeon_server_not_set)");
      paramMenuItem.showSettingsDialog(localBaseActivity, str);
      return true;
    }
    BaseActivity.openActivity$default(this, AddActivity.class, null, 2, null);
    return true;
  }
  
  public boolean showBackButton()
  {
    return false;
  }
}
