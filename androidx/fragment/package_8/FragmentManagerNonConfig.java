package androidx.fragment.package_8;

import androidx.lifecycle.ViewModelStore;
import java.util.Collection;
import java.util.Map;

@Deprecated
public class FragmentManagerNonConfig
{
  private final Map<String, androidx.fragment.app.FragmentManagerNonConfig> mChildNonConfigs;
  private final Collection<androidx.fragment.app.Fragment> mFragments;
  private final Map<String, ViewModelStore> mViewModelStores;
  
  FragmentManagerNonConfig(Collection paramCollection, Map paramMap1, Map paramMap2)
  {
    mFragments = paramCollection;
    mChildNonConfigs = paramMap1;
    mViewModelStores = paramMap2;
  }
  
  Map getChildNonConfigs()
  {
    return mChildNonConfigs;
  }
  
  Collection getFragments()
  {
    return mFragments;
  }
  
  Map getViewModelStores()
  {
    return mViewModelStores;
  }
  
  boolean isRetaining(Fragment paramFragment)
  {
    Collection localCollection = mFragments;
    if (localCollection == null) {
      return false;
    }
    return localCollection.contains(paramFragment);
  }
}
