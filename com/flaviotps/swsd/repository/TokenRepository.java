package com.flaviotps.swsd.repository;

import androidx.lifecycle.MutableLiveData;
import com.flaviotps.swsd.model.TokenModel;
import com.google.android.android.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.package_9.FirebaseInstanceId;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref.ObjectRef;

@Metadata(bv={1, 0, 3}, d1={"\000&\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\020\016\n\002\b\002\n\002\030\002\n\000\n\002\020\002\n\002\b\004\030\000 \r2\0020\001:\001\rB\007\b\002?\006\002\020\002J\f\020\003\032\b\022\004\022\0020\0050\004J\020\020\006\032\004\030\0010\0052\006\020\007\032\0020\bJ\026\020\t\032\0020\n2\006\020\013\032\0020\0052\006\020\f\032\0020\b?\006\016"}, d2={"Lcom/flaviotps/swsd/repository/TokenRepository;", "Lcom/flaviotps/swsd/repository/BaseRepository;", "()V", "load", "Landroidx/lifecycle/MutableLiveData;", "", "save", "token", "Lcom/flaviotps/swsd/model/TokenModel;", "setTokenStatus", "", "uid", "tokenModel", "Companion", "app_release"}, k=1, mv={1, 1, 16})
public final class TokenRepository
  extends BaseRepository
{
  public static final Companion Companion = new Companion(null);
  private static TokenRepository instance;
  
  private TokenRepository() {}
  
  public final MutableLiveData load()
  {
    Ref.ObjectRef localObjectRef = new Ref.ObjectRef();
    element = new MutableLiveData();
    FirebaseInstanceId localFirebaseInstanceId = FirebaseInstanceId.getInstance();
    Intrinsics.checkExpressionValueIsNotNull(localFirebaseInstanceId, "FirebaseInstanceId.getInstance()");
    localFirebaseInstanceId.getInstanceId().addOnCompleteListener((com.google.android.android.tasks.OnCompleteListener)new com.google.android.android.tasks.OnCompleteListener()
    {
      public final void onComplete(Task paramAnonymousTask)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousTask, "task");
        if (!paramAnonymousTask.isSuccessful()) {
          ((MutableLiveData)element).postValue(null);
        }
        MutableLiveData localMutableLiveData = (MutableLiveData)element;
        try
        {
          paramAnonymousTask = paramAnonymousTask.getResult();
          paramAnonymousTask = (com.google.firebase.package_9.InstanceIdResult)paramAnonymousTask;
          if (paramAnonymousTask != null) {
            paramAnonymousTask = paramAnonymousTask.getToken();
          } else {
            paramAnonymousTask = null;
          }
          localMutableLiveData.postValue(paramAnonymousTask);
          return;
        }
        catch (Exception paramAnonymousTask)
        {
          for (;;) {}
        }
        ((MutableLiveData)element).postValue(null);
      }
    });
    return (MutableLiveData)element;
  }
  
  public final String save(TokenModel paramTokenModel)
  {
    Intrinsics.checkParameterIsNotNull(paramTokenModel, "token");
    DatabaseReference localDatabaseReference = getBase().child("TOKEN").push();
    Intrinsics.checkExpressionValueIsNotNull(localDatabaseReference, "getBase()\n            .c?OKEN)\n            .push()");
    String str = localDatabaseReference.getKey();
    localDatabaseReference.setValue(paramTokenModel);
    return str;
  }
  
  public final void setTokenStatus(String paramString, TokenModel paramTokenModel)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "uid");
    Intrinsics.checkParameterIsNotNull(paramTokenModel, "tokenModel");
    getBase().child("TOKEN").child(paramString).setValue(paramTokenModel);
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\022\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\000\b?\003\030\0002\0020\001B\007\b\002?\006\002\020\002J\006\020\003\032\0020\004R\020\020\003\032\004\030\0010\004X?\016?\006\002\n\000?\006\005"}, d2={"Lcom/flaviotps/swsd/repository/TokenRepository$Companion;", "", "()V", "instance", "Lcom/flaviotps/swsd/repository/TokenRepository;", "app_release"}, k=1, mv={1, 1, 16})
  public static final class Companion
  {
    private Companion() {}
    
    public final TokenRepository instance()
    {
      if (TokenRepository.access$getInstance$cp() == null) {
        return new TokenRepository(null);
      }
      TokenRepository localTokenRepository = TokenRepository.access$getInstance$cp();
      if (localTokenRepository != null) {
        return localTokenRepository;
      }
      throw new TypeCastException("null cannot be cast to non-null type com.flaviotps.swsd.repository.TokenRepository");
    }
  }
}