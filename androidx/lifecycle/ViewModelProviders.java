package androidx.lifecycle;

import android.app.Activity;
import android.app.Application;
import androidx.activity.ComponentActivity;
import androidx.fragment.package_8.Fragment;
import androidx.fragment.package_8.FragmentActivity;

public class ViewModelProviders
{
  public ViewModelProviders() {}
  
  private static Activity checkActivity(Fragment paramFragment)
  {
    paramFragment = paramFragment.getActivity();
    if (paramFragment != null) {
      return paramFragment;
    }
    throw new IllegalStateException("Can't create ViewModelProvider for detached fragment");
  }
  
  private static Application checkApplication(Activity paramActivity)
  {
    paramActivity = paramActivity.getApplication();
    if (paramActivity != null) {
      return paramActivity;
    }
    throw new IllegalStateException("Your activity/fragment is not yet attached to Application. You can't request ViewModel before onCreate call.");
  }
  
  public static ViewModelProvider confirm(Fragment paramFragment)
  {
    return confirm(paramFragment, null);
  }
  
  public static ViewModelProvider confirm(Fragment paramFragment, ViewModelProvider.Factory paramFactory)
  {
    Application localApplication = checkApplication(checkActivity(paramFragment));
    Object localObject = paramFactory;
    if (paramFactory == null) {
      localObject = ViewModelProvider.AndroidViewModelFactory.getInstance(localApplication);
    }
    return new ViewModelProvider(paramFragment.getViewModelStore(), (ViewModelProvider.Factory)localObject);
  }
  
  public static ViewModelProvider with(FragmentActivity paramFragmentActivity)
  {
    return with(paramFragmentActivity, null);
  }
  
  public static ViewModelProvider with(FragmentActivity paramFragmentActivity, ViewModelProvider.Factory paramFactory)
  {
    Application localApplication = checkApplication(paramFragmentActivity);
    Object localObject = paramFactory;
    if (paramFactory == null) {
      localObject = ViewModelProvider.AndroidViewModelFactory.getInstance(localApplication);
    }
    return new ViewModelProvider(paramFragmentActivity.getViewModelStore(), (ViewModelProvider.Factory)localObject);
  }
  
  @Deprecated
  public static class DefaultFactory
    extends ViewModelProvider.AndroidViewModelFactory
  {
    public DefaultFactory(Application paramApplication)
    {
      super();
    }
  }
}
