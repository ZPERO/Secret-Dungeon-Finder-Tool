package com.flaviotps.swsd.repository;

import androidx.lifecycle.MutableLiveData;
import com.flaviotps.swsd.model.AppInfoModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref.ObjectRef;

@Metadata(bv={1, 0, 3}, d1={"\000\030\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\030\002\n\002\b\002\030\000 \0062\0020\001:\001\006B\005?\006\002\020\002J\f\020\003\032\b\022\004\022\0020\0050\004?\006\007"}, d2={"Lcom/flaviotps/swsd/repository/AppRepository;", "Lcom/flaviotps/swsd/repository/BaseRepository;", "()V", "getAppInfo", "Landroidx/lifecycle/MutableLiveData;", "Lcom/flaviotps/swsd/model/AppInfoModel;", "Companion", "app_release"}, k=1, mv={1, 1, 16})
public final class AppRepository
  extends BaseRepository
{
  public static final Companion Companion = new Companion(null);
  private static final String MAINTENANCE = "AppInfo";
  private static AppRepository instance;
  
  public AppRepository() {}
  
  public final MutableLiveData getAppInfo()
  {
    Ref.ObjectRef localObjectRef = new Ref.ObjectRef();
    element = new MutableLiveData();
    getBase().child("AppInfo").addValueEventListener((ValueEventListener)new ValueEventListener()
    {
      public void onCancelled(DatabaseError paramAnonymousDatabaseError)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousDatabaseError, "error");
      }
      
      public void onDataChange(DataSnapshot paramAnonymousDataSnapshot)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousDataSnapshot, "data");
        if (paramAnonymousDataSnapshot.exists()) {
          ((MutableLiveData)element).postValue(paramAnonymousDataSnapshot.getValue(AppInfoModel.class));
        }
      }
    });
    return (MutableLiveData)element;
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\030\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\016\n\000\n\002\030\002\n\000\b?\003\030\0002\0020\001B\007\b\002?\006\002\020\002J\006\020\005\032\0020\006R\016\020\003\032\0020\004X?T?\006\002\n\000R\020\020\005\032\004\030\0010\006X?\016?\006\002\n\000?\006\007"}, d2={"Lcom/flaviotps/swsd/repository/AppRepository$Companion;", "", "()V", "MAINTENANCE", "", "instance", "Lcom/flaviotps/swsd/repository/AppRepository;", "app_release"}, k=1, mv={1, 1, 16})
  public static final class Companion
  {
    private Companion() {}
    
    public final AppRepository instance()
    {
      if (AppRepository.access$getInstance$cp() == null) {
        return new AppRepository();
      }
      AppRepository localAppRepository = AppRepository.access$getInstance$cp();
      if (localAppRepository != null) {
        return localAppRepository;
      }
      throw new TypeCastException("null cannot be cast to non-null type com.flaviotps.swsd.repository.AppRepository");
    }
  }
}
