package com.flaviotps.swsd.repository;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000\032\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\000\n\002\020\016\n\002\b\002\b&\030\000 \0072\0020\001:\001\007B\005?\006\002\020\002J\006\020\003\032\0020\004J\b\020\005\032\0020\006H\002?\006\b"}, d2={"Lcom/flaviotps/swsd/repository/BaseRepository;", "", "()V", "getBase", "Lcom/google/firebase/database/DatabaseReference;", "getBuildBase", "", "Companion", "app_release"}, k=1, mv={1, 1, 16})
public abstract class BaseRepository
{
  public static final Companion Companion = new Companion(null);
  public static final String REF_BASE_PROD = "APP";
  public static final String REF_BASE_TEST = "APP_TEST";
  public static final String REF_SECRET_DUNGEON = "SD";
  public static final String REF_TOKEN = "TOKEN";
  
  public BaseRepository() {}
  
  private final String getBuildBase()
  {
    return "APP";
  }
  
  public final DatabaseReference getBase()
  {
    DatabaseReference localDatabaseReference = FirebaseDatabase.getInstance().getReference(getBuildBase());
    Intrinsics.checkExpressionValueIsNotNull(localDatabaseReference, "FirebaseDatabase.getInst?Reference(getBuildBase())");
    return localDatabaseReference;
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\024\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\016\n\002\b\004\b?\003\030\0002\0020\001B\007\b\002?\006\002\020\002R\016\020\003\032\0020\004X?T?\006\002\n\000R\016\020\005\032\0020\004X?T?\006\002\n\000R\016\020\006\032\0020\004X?T?\006\002\n\000R\016\020\007\032\0020\004X?T?\006\002\n\000?\006\b"}, d2={"Lcom/flaviotps/swsd/repository/BaseRepository$Companion;", "", "()V", "REF_BASE_PROD", "", "REF_BASE_TEST", "REF_SECRET_DUNGEON", "REF_TOKEN", "app_release"}, k=1, mv={1, 1, 16})
  public static final class Companion
  {
    private Companion() {}
  }
}
