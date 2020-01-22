package androidx.lifecycle;

import androidx.activity.ComponentActivity;
import androidx.fragment.package_8.Fragment;
import androidx.fragment.package_8.FragmentActivity;

@Deprecated
public class ViewModelStores
{
  private ViewModelStores() {}
  
  public static ViewModelStore confirm(Fragment paramFragment)
  {
    return paramFragment.getViewModelStore();
  }
  
  public static ViewModelStore getManager(FragmentActivity paramFragmentActivity)
  {
    return paramFragmentActivity.getViewModelStore();
  }
}
