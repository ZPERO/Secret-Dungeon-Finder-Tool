package androidx.lifecycle;

import android.app.Application;

public class AndroidViewModel
  extends ViewModel
{
  private Application mApplication;
  
  public AndroidViewModel(Application paramApplication)
  {
    mApplication = paramApplication;
  }
  
  public Application getApplication()
  {
    return mApplication;
  }
}
