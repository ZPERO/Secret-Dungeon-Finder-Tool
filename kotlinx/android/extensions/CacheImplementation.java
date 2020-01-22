package kotlinx.android.extensions;

import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\000\f\n\002\030\002\n\002\020\020\n\002\b\006\b?\001\030\000 \0062\b\022\004\022\0020\0000\001:\001\006B\007\b\002?\006\002\020\002j\002\b\003j\002\b\004j\002\b\005?\006\007"}, d2={"Lkotlinx/android/extensions/CacheImplementation;", "", "(Ljava/lang/String;I)V", "SPARSE_ARRAY", "HASH_MAP", "NO_CACHE", "Companion", "kotlin-android-extensions-runtime"}, k=1, mv={1, 1, 15})
public enum CacheImplementation
{
  public static final Companion Companion = new Companion(null);
  private static final CacheImplementation DEFAULT = HASH_MAP;
  
  static
  {
    CacheImplementation localCacheImplementation1 = new CacheImplementation("SPARSE_ARRAY", 0);
    SPARSE_ARRAY = localCacheImplementation1;
    CacheImplementation localCacheImplementation2 = new CacheImplementation("HASH_MAP", 1);
    HASH_MAP = localCacheImplementation2;
    CacheImplementation localCacheImplementation3 = new CacheImplementation("NO_CACHE", 2);
    NO_CACHE = localCacheImplementation3;
    $VALUES = new CacheImplementation[] { localCacheImplementation1, localCacheImplementation2, localCacheImplementation3 };
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\024\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\002\b\003\b?\003\030\0002\0020\001B\007\b\002?\006\002\020\002R\021\020\003\032\0020\004?\006\b\n\000\032\004\b\005\020\006?\006\007"}, d2={"Lkotlinx/android/extensions/CacheImplementation$Companion;", "", "()V", "DEFAULT", "Lkotlinx/android/extensions/CacheImplementation;", "getDEFAULT", "()Lkotlinx/android/extensions/CacheImplementation;", "kotlin-android-extensions-runtime"}, k=1, mv={1, 1, 15})
  public static final class Companion
  {
    private Companion() {}
    
    public final CacheImplementation getDEFAULT()
    {
      return CacheImplementation.access$getDEFAULT$cp();
    }
  }
}
