package com.flaviotps.swsd.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.package_8.Fragment;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000,\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\b\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\030\000 \r2\0020\001:\001\rB\005?\006\002\020\002J&\020\005\032\004\030\0010\0062\006\020\007\032\0020\b2\b\020\t\032\004\030\0010\n2\b\020\013\032\004\030\0010\fH\026R\016\020\003\032\0020\004X?\016?\006\002\n\000?\006\016"}, d2={"Lcom/flaviotps/swsd/view/TutorialFragment;", "Landroidx/fragment/app/Fragment;", "()V", "res", "", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "Companion", "app_release"}, k=1, mv={1, 1, 16})
public final class TutorialFragment
  extends Fragment
{
  public static final Companion Companion = new Companion(null);
  private HashMap _$_findViewCache;
  private int selectedItemIndex = 2131558455;
  
  public TutorialFragment() {}
  
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
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    Intrinsics.checkParameterIsNotNull(paramLayoutInflater, "inflater");
    return paramLayoutInflater.inflate(selectedItemIndex, paramViewGroup, false);
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\030\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\000\n\002\020\b\n\000\b?\003\030\0002\0020\001B\007\b\002?\006\002\020\002J\016\020\003\032\0020\0042\006\020\005\032\0020\006?\006\007"}, d2={"Lcom/flaviotps/swsd/view/TutorialFragment$Companion;", "", "()V", "newInstance", "Lcom/flaviotps/swsd/view/TutorialFragment;", "res", "", "app_release"}, k=1, mv={1, 1, 16})
  public static final class Companion
  {
    private Companion() {}
    
    public final TutorialFragment newInstance(int paramInt)
    {
      TutorialFragment localTutorialFragment = new TutorialFragment();
      TutorialFragment.access$setRes$p(localTutorialFragment, paramInt);
      return localTutorialFragment;
    }
  }
}
