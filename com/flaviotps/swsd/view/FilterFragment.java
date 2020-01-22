package com.flaviotps.swsd.view;

import android.os.Bundle;
import android.view.View;
import androidx.fragment.package_8.Fragment;
import androidx.preference.PreferenceFragmentCompat;
import java.util.HashMap;
import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\000\036\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\002\n\000\n\002\030\002\n\000\n\002\020\016\n\000\030\0002\0020\001B\005?\006\002\020\002J\034\020\003\032\0020\0042\b\020\005\032\004\030\0010\0062\b\020\007\032\004\030\0010\bH\026?\006\t"}, d2={"Lcom/flaviotps/swsd/view/FilterFragment;", "Landroidx/preference/PreferenceFragmentCompat;", "()V", "onCreatePreferences", "", "savedInstanceState", "Landroid/os/Bundle;", "rootKey", "", "app_release"}, k=1, mv={1, 1, 16})
public final class FilterFragment
  extends PreferenceFragmentCompat
{
  private HashMap _$_findViewCache;
  
  public FilterFragment() {}
  
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
      localView1 = getView();
      if (localView1 == null) {
        return null;
      }
      localView1 = localView1.findViewById(paramInt);
      _$_findViewCache.put(Integer.valueOf(paramInt), localView1);
    }
    return localView1;
  }
  
  public void onCreatePreferences(Bundle paramBundle, String paramString)
  {
    setPreferencesFromResource(2132017152, paramString);
  }
}
