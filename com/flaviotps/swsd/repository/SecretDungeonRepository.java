package com.flaviotps.swsd.repository;

import android.text.TextUtils;
import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import com.flaviotps.swsd.enum.MonsterElement;
import com.flaviotps.swsd.model.MonsterModel;
import com.flaviotps.swsd.model.SecretDungeonModel;
import com.google.android.android.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.FirebaseFunctionsException;
import com.google.firebase.functions.HttpsCallableReference;
import com.google.firebase.functions.HttpsCallableResult;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref.ObjectRef;

@Metadata(bv={1, 0, 3}, d1={"\000,\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020 \n\000\n\002\020\002\n\000\n\002\020\016\n\002\b\003\030\000 \0162\0020\001:\001\016B\007\b\002?\006\002\020\002J\024\020\003\032\b\022\004\022\0020\0050\0042\006\020\006\032\0020\005J\022\020\007\032\016\022\n\022\b\022\004\022\0020\0050\b0\004J\026\020\t\032\0020\n2\006\020\006\032\0020\0052\006\020\013\032\0020\fJ\024\020\r\032\b\022\004\022\0020\0050\0042\006\020\006\032\0020\005?\006\017"}, d2={"Lcom/flaviotps/swsd/repository/SecretDungeonRepository;", "Lcom/flaviotps/swsd/repository/BaseRepository;", "()V", "delete", "Landroidx/lifecycle/MutableLiveData;", "Lcom/flaviotps/swsd/model/SecretDungeonModel;", "secretDungeonModel", "getSecretDungeons", "", "requestFriend", "", "nickname", "", "save", "Companion", "app_release"}, k=1, mv={1, 1, 16})
public final class SecretDungeonRepository
  extends BaseRepository
{
  public static final Companion Companion = new Companion(null);
  private static SecretDungeonRepository instance;
  
  private SecretDungeonRepository() {}
  
  public final MutableLiveData delete(final SecretDungeonModel paramSecretDungeonModel)
  {
    Intrinsics.checkParameterIsNotNull(paramSecretDungeonModel, "secretDungeonModel");
    final Ref.ObjectRef localObjectRef = new Ref.ObjectRef();
    element = new MutableLiveData();
    String str1 = paramSecretDungeonModel.getKey();
    if (str1 != null)
    {
      DatabaseReference localDatabaseReference = getBase().child("SD");
      String str2 = paramSecretDungeonModel.getMonster().getElement().toString();
      if (str2 != null)
      {
        str2 = str2.toUpperCase();
        Intrinsics.checkExpressionValueIsNotNull(str2, "(this as java.lang.String).toUpperCase()");
        localDatabaseReference = localDatabaseReference.child(str2);
        str2 = paramSecretDungeonModel.getMonster().getGenericName();
        if (str2 != null)
        {
          str2 = str2.toUpperCase();
          Intrinsics.checkExpressionValueIsNotNull(str2, "(this as java.lang.String).toUpperCase()");
          localDatabaseReference.child(str2).child(str1).removeValue().addOnCompleteListener((com.google.android.android.tasks.OnCompleteListener)new com.google.android.android.tasks.OnCompleteListener()
          {
            public final void onComplete(Task paramAnonymousTask)
            {
              Intrinsics.checkParameterIsNotNull(paramAnonymousTask, "task");
              if (!paramAnonymousTask.isSuccessful())
              {
                paramAnonymousTask = paramAnonymousTask.getException();
                if ((paramAnonymousTask instanceof FirebaseFunctionsException))
                {
                  FirebaseFunctionsException localFirebaseFunctionsException = (FirebaseFunctionsException)paramAnonymousTask;
                  Intrinsics.checkExpressionValueIsNotNull(localFirebaseFunctionsException.getCode(), "e.code");
                  localFirebaseFunctionsException.getDetails();
                }
                Log.w("ContentValues", "delete:onFailure", (Throwable)paramAnonymousTask);
                return;
              }
              ((MutableLiveData)localObjectRefelement).postValue(paramSecretDungeonModel);
            }
          });
        }
        else
        {
          throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
      }
      else
      {
        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
      }
    }
    return (MutableLiveData)element;
  }
  
  public final MutableLiveData getSecretDungeons()
  {
    Ref.ObjectRef localObjectRef = new Ref.ObjectRef();
    element = new MutableLiveData();
    getBase().child("SD").addValueEventListener((ValueEventListener)new ValueEventListener()
    {
      public void onCancelled(DatabaseError paramAnonymousDatabaseError)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousDatabaseError, "error");
      }
      
      public void onDataChange(DataSnapshot paramAnonymousDataSnapshot)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousDataSnapshot, "data");
        try
        {
          Object localObject1 = new ArrayList();
          localObject1 = (List)localObject1;
          boolean bool = paramAnonymousDataSnapshot.exists();
          if (bool)
          {
            paramAnonymousDataSnapshot = paramAnonymousDataSnapshot.getChildren().iterator();
            Object localObject2;
            do
            {
              bool = paramAnonymousDataSnapshot.hasNext();
              if (!bool) {
                break;
              }
              localObject2 = paramAnonymousDataSnapshot.next();
              localObject2 = (DataSnapshot)localObject2;
              Intrinsics.checkExpressionValueIsNotNull(localObject2, "element");
              localObject2 = ((DataSnapshot)localObject2).getChildren().iterator();
              bool = ((Iterator)localObject2).hasNext();
            } while (!bool);
            Object localObject3 = ((Iterator)localObject2).next();
            localObject3 = (DataSnapshot)localObject3;
            Intrinsics.checkExpressionValueIsNotNull(localObject3, "monster");
            localObject3 = ((DataSnapshot)localObject3).getChildren().iterator();
            for (;;)
            {
              bool = ((Iterator)localObject3).hasNext();
              if (!bool) {
                break;
              }
              Object localObject4 = ((Iterator)localObject3).next();
              localObject4 = (DataSnapshot)localObject4;
              Object localObject5 = ((DataSnapshot)localObject4).getValue(SecretDungeonModel.class);
              localObject5 = (SecretDungeonModel)localObject5;
              if (localObject5 != null)
              {
                bool = ((SecretDungeonModel)localObject5).expired();
                if (!bool)
                {
                  int i = 0;
                  Iterator localIterator = ((List)localObject1).iterator();
                  for (;;)
                  {
                    bool = localIterator.hasNext();
                    if (!bool) {
                      break;
                    }
                    Object localObject6 = localIterator.next();
                    localObject6 = (SecretDungeonModel)localObject6;
                    bool = ((SecretDungeonModel)localObject6).equals((SecretDungeonModel)localObject5);
                    if (bool) {
                      i = 1;
                    }
                  }
                  if (i == 0)
                  {
                    Intrinsics.checkExpressionValueIsNotNull(localObject4, "c");
                    ((SecretDungeonModel)localObject5).setKey(((DataSnapshot)localObject4).getKey());
                    ((List)localObject1).add(localObject5);
                  }
                }
              }
            }
            paramAnonymousDataSnapshot = (MutableLiveData)element;
            localObject1 = (Iterable)localObject1;
            paramAnonymousDataSnapshot.postValue(CollectionsKt___CollectionsKt.toList((Iterable)localObject1));
            return;
          }
        }
        catch (Exception paramAnonymousDataSnapshot)
        {
          paramAnonymousDataSnapshot.printStackTrace();
        }
      }
    });
    return (MutableLiveData)element;
  }
  
  public final void requestFriend(SecretDungeonModel paramSecretDungeonModel, String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramSecretDungeonModel, "secretDungeonModel");
    Intrinsics.checkParameterIsNotNull(paramString, "nickname");
    if (TextUtils.isEmpty((CharSequence)paramSecretDungeonModel.getFcmToken())) {
      return;
    }
    paramSecretDungeonModel = MapsKt__MapsKt.hashMapOf(new Pair[] { TuplesKt.to("token", paramSecretDungeonModel.getFcmToken()), TuplesKt.to("monster", paramSecretDungeonModel.getMonster().getGenericName()), TuplesKt.to("nickname", paramString), TuplesKt.to("element", paramSecretDungeonModel.getMonster().getElement().getValue()), TuplesKt.to("image", paramSecretDungeonModel.getMonster().getImage()) });
    FirebaseFunctions.getInstance().getHttpsCallable("requestFriend").call(paramSecretDungeonModel).addOnCompleteListener((com.google.android.android.tasks.OnCompleteListener)requestFriend.1.INSTANCE);
  }
  
  public final MutableLiveData save(final SecretDungeonModel paramSecretDungeonModel)
  {
    Intrinsics.checkParameterIsNotNull(paramSecretDungeonModel, "secretDungeonModel");
    Ref.ObjectRef localObjectRef = new Ref.ObjectRef();
    element = new MutableLiveData();
    DatabaseReference localDatabaseReference = getBase().child("SD");
    String str = paramSecretDungeonModel.getMonster().getElement().toString();
    if (str != null)
    {
      str = str.toUpperCase();
      Intrinsics.checkExpressionValueIsNotNull(str, "(this as java.lang.String).toUpperCase()");
      localDatabaseReference = localDatabaseReference.child(str);
      str = paramSecretDungeonModel.getMonster().getGenericName();
      if (str != null)
      {
        str = str.toUpperCase();
        Intrinsics.checkExpressionValueIsNotNull(str, "(this as java.lang.String).toUpperCase()");
        localDatabaseReference.child(str).push().setValue(paramSecretDungeonModel).addOnCompleteListener((com.google.android.android.tasks.OnCompleteListener)new com.google.android.android.tasks.OnCompleteListener()
        {
          public final void onComplete(Task paramAnonymousTask)
          {
            Intrinsics.checkParameterIsNotNull(paramAnonymousTask, "it");
            if (paramAnonymousTask.isSuccessful()) {
              ((MutableLiveData)element).postValue(paramSecretDungeonModel);
            }
          }
        });
        return (MutableLiveData)element;
      }
      throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
    }
    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\022\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\000\b?\003\030\0002\0020\001B\007\b\002?\006\002\020\002J\006\020\003\032\0020\004R\020\020\003\032\004\030\0010\004X?\016?\006\002\n\000?\006\005"}, d2={"Lcom/flaviotps/swsd/repository/SecretDungeonRepository$Companion;", "", "()V", "instance", "Lcom/flaviotps/swsd/repository/SecretDungeonRepository;", "app_release"}, k=1, mv={1, 1, 16})
  public static final class Companion
  {
    private Companion() {}
    
    public final SecretDungeonRepository instance()
    {
      if (SecretDungeonRepository.access$getInstance$cp() == null) {
        return new SecretDungeonRepository(null);
      }
      SecretDungeonRepository localSecretDungeonRepository = SecretDungeonRepository.access$getInstance$cp();
      if (localSecretDungeonRepository != null) {
        return localSecretDungeonRepository;
      }
      throw new TypeCastException("null cannot be cast to non-null type com.flaviotps.swsd.repository.SecretDungeonRepository");
    }
  }
}
