package androidx.lifecycle;

import androidx.arch.core.util.Function;

public class Transformations
{
  private Transformations() {}
  
  public static LiveData addSource(LiveData paramLiveData, final Function paramFunction)
  {
    MediatorLiveData localMediatorLiveData = new MediatorLiveData();
    localMediatorLiveData.addSource(paramLiveData, new Observer()
    {
      public void onChanged(Object paramAnonymousObject)
      {
        val$result.setValue(paramFunction.apply(paramAnonymousObject));
      }
    });
    return localMediatorLiveData;
  }
  
  public static LiveData distinctUntilChanged(LiveData paramLiveData)
  {
    MediatorLiveData localMediatorLiveData = new MediatorLiveData();
    localMediatorLiveData.addSource(paramLiveData, new Observer()
    {
      boolean mFirstTime = true;
      
      public void onChanged(Object paramAnonymousObject)
      {
        Object localObject = val$outputLiveData.getValue();
        if ((mFirstTime) || ((localObject == null) && (paramAnonymousObject != null)) || ((localObject != null) && (!localObject.equals(paramAnonymousObject))))
        {
          mFirstTime = false;
          val$outputLiveData.setValue(paramAnonymousObject);
        }
      }
    });
    return localMediatorLiveData;
  }
  
  public static LiveData switchMap(LiveData paramLiveData, Function paramFunction)
  {
    final MediatorLiveData localMediatorLiveData = new MediatorLiveData();
    localMediatorLiveData.addSource(paramLiveData, new Observer()
    {
      LiveData<Y> mSource;
      
      public void onChanged(Object paramAnonymousObject)
      {
        paramAnonymousObject = (LiveData)val$switchMapFunction.apply(paramAnonymousObject);
        LiveData localLiveData = mSource;
        if (localLiveData == paramAnonymousObject) {
          return;
        }
        if (localLiveData != null) {
          localMediatorLiveData.removeSource(localLiveData);
        }
        mSource = paramAnonymousObject;
        paramAnonymousObject = mSource;
        if (paramAnonymousObject != null) {
          localMediatorLiveData.addSource(paramAnonymousObject, new Observer()
          {
            public void onChanged(Object paramAnonymous2Object)
            {
              val$result.setValue(paramAnonymous2Object);
            }
          });
        }
      }
    });
    return localMediatorLiveData;
  }
}
