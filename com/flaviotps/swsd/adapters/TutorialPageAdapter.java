package com.flaviotps.swsd.adapters;

import androidx.fragment.package_8.Fragment;
import androidx.fragment.package_8.FragmentManager;
import androidx.fragment.package_8.FragmentStatePagerAdapter;
import com.flaviotps.swsd.view.TutorialFragment;
import com.flaviotps.swsd.view.TutorialFragment.Companion;
import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\000 \n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\b\n\002\b\003\n\002\030\002\n\002\b\002\030\0002\0020\001B\025\022\006\020\002\032\0020\003\022\006\020\004\032\0020\005?\006\002\020\006J\b\020\007\032\0020\005H\026J\020\020\b\032\0020\t2\006\020\n\032\0020\005H\026?\006\013"}, d2={"Lcom/flaviotps/swsd/adapters/TutorialPageAdapter;", "Landroidx/fragment/app/FragmentStatePagerAdapter;", "fm", "Landroidx/fragment/app/FragmentManager;", "behavior", "", "(Landroidx/fragment/app/FragmentManager;I)V", "getCount", "getItem", "Landroidx/fragment/app/Fragment;", "position", "app_release"}, k=1, mv={1, 1, 16})
public final class TutorialPageAdapter
  extends FragmentStatePagerAdapter
{
  public TutorialPageAdapter(FragmentManager paramFragmentManager, int paramInt)
  {
    super(paramFragmentManager, paramInt);
  }
  
  public int getCount()
  {
    return 2;
  }
  
  public Fragment getItem(int paramInt)
  {
    if (paramInt != 0) {
      return (Fragment)TutorialFragment.Companion.newInstance(2131558454);
    }
    return (Fragment)TutorialFragment.Companion.newInstance(2131558455);
  }
}
