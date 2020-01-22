package com.flaviotps.swsd.view;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.package_8.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import com.flaviotps.swsd.adapters.MonsterArrayAdapter;
import com.flaviotps.swsd.enum.MonsterElement;
import com.flaviotps.swsd.enum.ServerLocation;
import com.flaviotps.swsd.enum.ServerLocation.Companion;
import com.flaviotps.swsd.model.MonsterModel;
import com.flaviotps.swsd.model.SecretDungeonModel;
import com.flaviotps.swsd.utils.DateUtils;
import com.flaviotps.swsd.utils.DateUtils.Companion;
import com.flaviotps.swsd.utils.DialogUtils;
import com.flaviotps.swsd.utils.DialogUtils.Companion;
import com.flaviotps.swsd.viewmodel.AddViewModel;
import java.util.HashMap;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsJVMKt;

@Metadata(bv={1, 0, 3}, d1={"\000F\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\020\002\n\002\b\004\n\002\030\002\n\000\030\0002\0020\001B\005?\006\002\020\002J\b\020\022\032\0020\023H\026J\b\020\024\032\0020\023H\026J\b\020\025\032\0020\023H\026J\022\020\026\032\0020\0232\b\020\027\032\004\030\0010\030H\024R\016\020\003\032\0020\004X?.?\006\002\n\000R\016\020\005\032\0020\006X?.?\006\002\n\000R\016\020\007\032\0020\bX?.?\006\002\n\000R\016\020\t\032\0020\nX?.?\006\002\n\000R\016\020\013\032\0020\fX?.?\006\002\n\000R\016\020\r\032\0020\016X?.?\006\002\n\000R\016\020\017\032\0020\nX?.?\006\002\n\000R\016\020\020\032\0020\021X?.?\006\002\n\000?\006\031"}, d2={"Lcom/flaviotps/swsd/view/AddActivity;", "Lcom/flaviotps/swsd/view/BaseActivity;", "()V", "adapter", "Lcom/flaviotps/swsd/adapters/MonsterArrayAdapter;", "autoCompleteMonsters", "Landroid/widget/AutoCompleteTextView;", "btnAdd", "Landroid/widget/Button;", "helpCreate", "Landroid/widget/TextView;", "imagePreview", "Landroid/widget/ImageView;", "sbTimeLeft", "Landroid/widget/SeekBar;", "tvTime", "viewModel", "Lcom/flaviotps/swsd/viewmodel/AddViewModel;", "afterStart", "", "bindListeners", "bindViews", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "app_release"}, k=1, mv={1, 1, 16})
public final class AddActivity
  extends BaseActivity
{
  private HashMap _$_findViewCache;
  private MonsterArrayAdapter adapter;
  private AutoCompleteTextView autoCompleteMonsters;
  private Button btnAdd;
  private TextView helpCreate;
  private ImageView imagePreview;
  private SeekBar sbTimeLeft;
  private TextView tvTime;
  private AddViewModel viewModel;
  
  public AddActivity() {}
  
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
    Object localObject1 = btnAdd;
    if (localObject1 == null) {
      Intrinsics.throwUninitializedPropertyAccessException("btnAdd");
    }
    ((TextView)localObject1).setEnabled(true);
    localObject1 = sbTimeLeft;
    if (localObject1 == null) {
      Intrinsics.throwUninitializedPropertyAccessException("sbTimeLeft");
    }
    ((ProgressBar)localObject1).setProgress(55);
    localObject1 = tvTime;
    if (localObject1 == null) {
      Intrinsics.throwUninitializedPropertyAccessException("tvTime");
    }
    Object localObject2 = getString(2131820670);
    Intrinsics.checkExpressionValueIsNotNull(localObject2, "getString(R.string.time_left)");
    SeekBar localSeekBar = sbTimeLeft;
    if (localSeekBar == null) {
      Intrinsics.throwUninitializedPropertyAccessException("sbTimeLeft");
    }
    ((TextView)localObject1).setText((CharSequence)StringsKt__StringsJVMKt.replace$default((String)localObject2, "#", String.valueOf(localSeekBar.getProgress()), false, 4, null));
    localObject2 = helpCreate;
    if (localObject2 == null) {
      Intrinsics.throwUninitializedPropertyAccessException("helpCreate");
    }
    if (Build.VERSION.SDK_INT >= 24) {
      localObject1 = (CharSequence)Html.fromHtml(getString(2131820644), 0);
    } else {
      localObject1 = (CharSequence)Html.fromHtml(getString(2131820644));
    }
    ((TextView)localObject2).setText((CharSequence)localObject1);
  }
  
  public void bindListeners()
  {
    Object localObject = viewModel;
    if (localObject == null) {
      Intrinsics.throwUninitializedPropertyAccessException("viewModel");
    }
    ((AddViewModel)localObject).clearMonster();
    localObject = autoCompleteMonsters;
    if (localObject == null) {
      Intrinsics.throwUninitializedPropertyAccessException("autoCompleteMonsters");
    }
    ((AutoCompleteTextView)localObject).setOnItemClickListener((AdapterView.OnItemClickListener)new AdapterView.OnItemClickListener()
    {
      public final void onItemClick(AdapterView paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
      {
        paramAnonymousAdapterView = paramAnonymousAdapterView.getItemAtPosition(paramAnonymousInt);
        if (paramAnonymousAdapterView != null)
        {
          paramAnonymousView = (MonsterModel)paramAnonymousAdapterView;
          if (paramAnonymousView != null) {
            paramAnonymousAdapterView = paramAnonymousView.getImage();
          } else {
            paramAnonymousAdapterView = null;
          }
          ImageView localImageView = AddActivity.access$getImagePreview$p(AddActivity.this);
          AddActivity localAddActivity = AddActivity.this;
          localImageView.setImageDrawable(ContextCompat.getDrawable((Context)localAddActivity, localAddActivity.getResources().getIdentifier(paramAnonymousAdapterView, "drawable", getPackageName())));
          if (paramAnonymousView != null) {
            AddActivity.access$getViewModel$p(AddActivity.this).getSecretDungeonModel().setMonster(paramAnonymousView);
          }
        }
        else
        {
          throw new TypeCastException("null cannot be cast to non-null type com.flaviotps.swsd.model.MonsterModel");
        }
      }
    });
    localObject = sbTimeLeft;
    if (localObject == null) {
      Intrinsics.throwUninitializedPropertyAccessException("sbTimeLeft");
    }
    ((SeekBar)localObject).setOnSeekBarChangeListener((SeekBar.OnSeekBarChangeListener)new SeekBar.OnSeekBarChangeListener()
    {
      public void onProgressChanged(SeekBar paramAnonymousSeekBar, int paramAnonymousInt, boolean paramAnonymousBoolean)
      {
        paramAnonymousSeekBar = AddActivity.access$getTvTime$p(AddActivity.this);
        String str = getString(2131820670);
        Intrinsics.checkExpressionValueIsNotNull(str, "getString(R.string.time_left)");
        paramAnonymousSeekBar.setText((CharSequence)StringsKt__StringsJVMKt.replace$default(str, "#", String.valueOf(paramAnonymousInt), false, 4, null));
      }
      
      public void onStartTrackingTouch(SeekBar paramAnonymousSeekBar) {}
      
      public void onStopTrackingTouch(SeekBar paramAnonymousSeekBar) {}
    });
    localObject = viewModel;
    if (localObject == null) {
      Intrinsics.throwUninitializedPropertyAccessException("viewModel");
    }
    ((AddViewModel)localObject).getMonsters().observe((LifecycleOwner)this, (Observer)new Observer()
    {
      public final void onChanged(List paramAnonymousList)
      {
        AddActivity localAddActivity = AddActivity.this;
        Context localContext = (Context)localAddActivity;
        Intrinsics.checkExpressionValueIsNotNull(paramAnonymousList, "list");
        AddActivity.access$setAdapter$p(localAddActivity, new MonsterArrayAdapter(localContext, paramAnonymousList));
        AddActivity.access$getAutoCompleteMonsters$p(AddActivity.this).setAdapter((ListAdapter)AddActivity.access$getAdapter$p(AddActivity.this));
        AddActivity.access$getAdapter$p(AddActivity.this).notifyDataSetChanged();
      }
    });
    localObject = btnAdd;
    if (localObject == null) {
      Intrinsics.throwUninitializedPropertyAccessException("btnAdd");
    }
    ((View)localObject).setOnClickListener((View.OnClickListener)new View.OnClickListener()
    {
      public final void onClick(View paramAnonymousView)
      {
        paramAnonymousView = AddActivity.this;
        try
        {
          paramAnonymousView = (String)BaseActivity.getSettings$default(paramAnonymousView, "PREF_LAST_DUNGEON", false, 2, null);
          if (paramAnonymousView != null) {
            paramAnonymousView = Long.valueOf(Long.parseLong(paramAnonymousView));
          }
        }
        catch (ClassCastException paramAnonymousView)
        {
          Object localObject;
          BaseActivity localBaseActivity;
          int i;
          for (;;) {}
        }
        paramAnonymousView = null;
        if ((paramAnonymousView != null) && (!AddActivity.access$getViewModel$p(AddActivity.this).canCreate(paramAnonymousView.longValue())))
        {
          paramAnonymousView = DialogUtils.Companion;
          localObject = AddActivity.this;
          localBaseActivity = (BaseActivity)localObject;
          localObject = ((Context)localObject).getString(2131820685);
          Intrinsics.checkExpressionValueIsNotNull(localObject, "getString(R.string.warning_already_created)");
          DialogUtils.Companion.showWarningDialog$default(paramAnonymousView, localBaseActivity, (String)localObject, null, (View.OnClickListener)new View.OnClickListener()
          {
            public final void onClick(View paramAnonymous2View)
            {
              this$0.this$0.finish();
            }
          }, 4, null);
          return;
        }
        if (((CharSequence)AddActivity.access$getViewModel$p(AddActivity.this).getSecretDungeonModel().getMonster().getGenericName()).length() > 0) {
          i = 1;
        } else {
          i = 0;
        }
        if ((i != 0) && (AddActivity.access$getViewModel$p(AddActivity.this).getSecretDungeonModel().getMonster().getElement() != MonsterElement.NONE))
        {
          AddActivity.access$getViewModel$p(AddActivity.this).getSecretDungeonModel().setTimeLeft(AddActivity.access$getSbTimeLeft$p(AddActivity.this).getProgress());
          paramAnonymousView = (String)BaseActivity.getSettings$default(AddActivity.this, "app_nickname", false, 2, null);
          if (paramAnonymousView != null) {
            AddActivity.access$getViewModel$p(AddActivity.this).getSecretDungeonModel().setInGameName(paramAnonymousView);
          }
          paramAnonymousView = (String)BaseActivity.getSettings$default(AddActivity.this, "server_location", false, 2, null);
          if (paramAnonymousView != null) {
            AddActivity.access$getViewModel$p(AddActivity.this).getSecretDungeonModel().setServerLocation(ServerLocation.Companion.getServerType(paramAnonymousView));
          }
          paramAnonymousView = (String)BaseActivity.getSettings$default(AddActivity.this, "PREF_TOKEN", false, 2, null);
          if (paramAnonymousView != null) {
            AddActivity.access$getViewModel$p(AddActivity.this).getSecretDungeonModel().setFcmToken(paramAnonymousView);
          }
          AddActivity.access$getViewModel$p(AddActivity.this).save().observe((LifecycleOwner)AddActivity.this, (Observer)new Observer()
          {
            public final void onChanged(SecretDungeonModel paramAnonymous2SecretDungeonModel)
            {
              this$0.this$0.setSettings("PREF_LAST_DUNGEON", Long.valueOf(DateUtils.Companion.getEndTime(paramAnonymous2SecretDungeonModel.getCreationTime(), 60)));
            }
          });
        }
        finish();
      }
    });
  }
  
  public void bindViews()
  {
    View localView = findViewById(2131361851);
    Intrinsics.checkExpressionValueIsNotNull(localView, "findViewById(R.id.actvMonsters)");
    autoCompleteMonsters = ((AutoCompleteTextView)localView);
    localView = findViewById(2131361935);
    Intrinsics.checkExpressionValueIsNotNull(localView, "findViewById(R.id.imagePreview)");
    imagePreview = ((ImageView)localView);
    localView = findViewById(2131362028);
    Intrinsics.checkExpressionValueIsNotNull(localView, "findViewById(R.id.skTimeleft)");
    sbTimeLeft = ((SeekBar)localView);
    localView = findViewById(2131362088);
    Intrinsics.checkExpressionValueIsNotNull(localView, "findViewById(R.id.tvTime)");
    tvTime = ((TextView)localView);
    localView = findViewById(2131361867);
    Intrinsics.checkExpressionValueIsNotNull(localView, "findViewById(R.id.btnAdd)");
    btnAdd = ((Button)localView);
    localView = findViewById(2131361926);
    Intrinsics.checkExpressionValueIsNotNull(localView, "findViewById(R.id.helpCreate)");
    helpCreate = ((TextView)localView);
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2131558428);
    paramBundle = ViewModelProviders.with((FragmentActivity)this).getName(AddViewModel.class);
    Intrinsics.checkExpressionValueIsNotNull(paramBundle, "ViewModelProviders.of(th?AddViewModel::class.java]");
    viewModel = ((AddViewModel)paramBundle);
  }
}
