package com.flaviotps.swsd.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import androidx.activity.ComponentActivity;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

@Metadata(bv={1, 0, 3}, d1={"\000D\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\002\n\002\b\005\n\002\020\016\n\000\n\002\020\013\n\002\b\003\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\003\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\b\006\b&\030\000 \0372\0020\001:\001\037B\005?\006\002\020\002J\b\020\003\032\0020\004H&J\b\020\005\032\0020\004H&J\b\020\006\032\0020\004H&J%\020\007\032\004\030\001H\b\"\004\b\000\020\b2\006\020\t\032\0020\n2\b\b\002\020\013\032\0020\f?\006\002\020\rJ\022\020\016\032\0020\0042\b\020\017\032\004\030\0010\020H\024J\020\020\021\032\0020\f2\006\020\022\032\0020\023H\026J\b\020\024\032\0020\004H\024J5\020\025\032\0020\004\"\004\b\000\020\b2\f\020\026\032\b\022\004\022\002H\b0\0272\031\b\002\020\030\032\023\022\004\022\0020\020\022\004\022\0020\0040\031?\006\002\b\032J!\020\033\032\0020\004\"\004\b\000\020\b2\006\020\t\032\0020\n2\006\020\034\032\002H\b?\006\002\020\035J\b\020\036\032\0020\fH\026?\006 "}, d2={"Lcom/flaviotps/swsd/view/BaseActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "afterStart", "", "bindListeners", "bindViews", "getSettings", "T", "key", "", "set", "", "(Ljava/lang/String;Z)Ljava/lang/Object;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onOptionsItemSelected", "item", "Landroid/view/MenuItem;", "onStart", "openActivity", "it", "Ljava/lang/Class;", "extras", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "setSettings", "value", "(Ljava/lang/String;Ljava/lang/Object;)V", "showBackButton", "Companion", "app_release"}, k=1, mv={1, 1, 16})
public abstract class BaseActivity
  extends AppCompatActivity
{
  public static final String CHANNEL_ID = "SWSD";
  public static final Companion Companion = new Companion(null);
  public static final String DUNGEON_NOTIFICATION = "dungeon";
  public static final String PREF_LAST_DUNGEON = "PREF_LAST_DUNGEON";
  public static final String PREF_TOKEN = "PREF_TOKEN";
  public static final String PREF_UID_TOKEN = "PREF_UID_TOKEN";
  public static final String REQUEST_NOTIFICATION = "request";
  public static final String SETTINGS_FILTER_ELEMENTS = "filter_elements";
  public static final String SETTINGS_FILTER_MONSTERS = "filter_monsters";
  public static final String SETTINGS_FILTER_SERVER = "filter_server";
  public static final String SETTINGS_IN_GAME_NAME = "app_nickname";
  public static final String SETTINGS_SERVER_LOCATION = "server_location";
  public static final String VERSION = "version";
  private HashMap _$_findViewCache;
  
  public BaseActivity() {}
  
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
  
  public abstract void afterStart();
  
  public abstract void bindListeners();
  
  public abstract void bindViews();
  
  public final Object getSettings(String paramString, boolean paramBoolean)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "key");
    Object localObject = PreferenceManager.getDefaultSharedPreferences((Context)this);
    if (paramBoolean)
    {
      localObject = ((SharedPreferences)localObject).getStringSet(paramString, null);
      paramString = (String)localObject;
      if (!(localObject instanceof Object)) {
        paramString = null;
      }
      return (Object)paramString;
    }
    localObject = ((SharedPreferences)localObject).getString(paramString, null);
    paramString = (String)localObject;
    if (!(localObject instanceof Object)) {
      paramString = null;
    }
    return (Object)paramString;
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (showBackButton())
    {
      paramBundle = getSupportActionBar();
      if (paramBundle != null) {
        paramBundle.setDisplayHomeAsUpEnabled(true);
      }
    }
  }
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    Intrinsics.checkParameterIsNotNull(paramMenuItem, "item");
    if (paramMenuItem.getItemId() == 16908332)
    {
      onBackPressed();
      return true;
    }
    return super.onOptionsItemSelected(paramMenuItem);
  }
  
  protected void onStart()
  {
    super.onStart();
    bindViews();
    bindListeners();
    afterStart();
  }
  
  public final void openActivity(Class paramClass, Function1 paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramClass, "it");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "extras");
    paramClass = new Intent((Context)this, paramClass);
    Bundle localBundle = new Bundle();
    paramFunction1.invoke(localBundle);
    paramClass.putExtras(localBundle);
    startActivity(paramClass);
  }
  
  public final void setSettings(String paramString, Object paramObject)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "key");
    PreferenceManager.getDefaultSharedPreferences((Context)this).edit().putString(paramString, String.valueOf(paramObject)).apply();
  }
  
  public boolean showBackButton()
  {
    return true;
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\024\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\016\n\002\b\f\b?\003\030\0002\0020\001B\007\b\002?\006\002\020\002R\016\020\003\032\0020\004X?T?\006\002\n\000R\016\020\005\032\0020\004X?T?\006\002\n\000R\016\020\006\032\0020\004X?T?\006\002\n\000R\016\020\007\032\0020\004X?T?\006\002\n\000R\016\020\b\032\0020\004X?T?\006\002\n\000R\016\020\t\032\0020\004X?T?\006\002\n\000R\016\020\n\032\0020\004X?T?\006\002\n\000R\016\020\013\032\0020\004X?T?\006\002\n\000R\016\020\f\032\0020\004X?T?\006\002\n\000R\016\020\r\032\0020\004X?T?\006\002\n\000R\016\020\016\032\0020\004X?T?\006\002\n\000R\016\020\017\032\0020\004X?T?\006\002\n\000?\006\020"}, d2={"Lcom/flaviotps/swsd/view/BaseActivity$Companion;", "", "()V", "CHANNEL_ID", "", "DUNGEON_NOTIFICATION", "PREF_LAST_DUNGEON", "PREF_TOKEN", "PREF_UID_TOKEN", "REQUEST_NOTIFICATION", "SETTINGS_FILTER_ELEMENTS", "SETTINGS_FILTER_MONSTERS", "SETTINGS_FILTER_SERVER", "SETTINGS_IN_GAME_NAME", "SETTINGS_SERVER_LOCATION", "VERSION", "app_release"}, k=1, mv={1, 1, 16})
  public static final class Companion
  {
    private Companion() {}
  }
}
