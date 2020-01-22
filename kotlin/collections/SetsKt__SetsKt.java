package kotlin.collections;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\0000\n\000\n\002\020\"\n\002\b\002\n\002\030\002\n\002\030\002\n\000\n\002\020\021\n\002\b\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020#\n\002\b\005\032\022\020\000\032\b\022\004\022\002H\0020\001\"\004\b\000\020\002\032\037\020\003\032\022\022\004\022\002H\0020\004j\b\022\004\022\002H\002`\005\"\004\b\000\020\002H?\b\0325\020\003\032\022\022\004\022\002H\0020\004j\b\022\004\022\002H\002`\005\"\004\b\000\020\0022\022\020\006\032\n\022\006\b\001\022\002H\0020\007\"\002H\002?\006\002\020\b\032\037\020\t\032\022\022\004\022\002H\0020\nj\b\022\004\022\002H\002`\013\"\004\b\000\020\002H?\b\0325\020\t\032\022\022\004\022\002H\0020\nj\b\022\004\022\002H\002`\013\"\004\b\000\020\0022\022\020\006\032\n\022\006\b\001\022\002H\0020\007\"\002H\002?\006\002\020\f\032\025\020\r\032\b\022\004\022\002H\0020\016\"\004\b\000\020\002H?\b\032+\020\r\032\b\022\004\022\002H\0020\016\"\004\b\000\020\0022\022\020\006\032\n\022\006\b\001\022\002H\0020\007\"\002H\002?\006\002\020\017\032\025\020\020\032\b\022\004\022\002H\0020\001\"\004\b\000\020\002H?\b\032+\020\020\032\b\022\004\022\002H\0020\001\"\004\b\000\020\0022\022\020\006\032\n\022\006\b\001\022\002H\0020\007\"\002H\002?\006\002\020\017\032\036\020\021\032\b\022\004\022\002H\0020\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\001H\000\032!\020\022\032\b\022\004\022\002H\0020\001\"\004\b\000\020\002*\n\022\004\022\002H\002\030\0010\001H?\b?\006\023"}, d2={"emptySet", "", "T", "hashSetOf", "Ljava/util/HashSet;", "Lkotlin/collections/HashSet;", "elements", "", "([Ljava/lang/Object;)Ljava/util/HashSet;", "linkedSetOf", "Ljava/util/LinkedHashSet;", "Lkotlin/collections/LinkedHashSet;", "([Ljava/lang/Object;)Ljava/util/LinkedHashSet;", "mutableSetOf", "", "([Ljava/lang/Object;)Ljava/util/Set;", "setOf", "optimizeReadOnlySet", "orEmpty", "kotlin-stdlib"}, k=5, mv={1, 1, 15}, xi=1, xs="kotlin/collections/SetsKt")
class SetsKt__SetsKt
  extends SetsKt__SetsJVMKt
{
  public SetsKt__SetsKt() {}
  
  public static final Set emptySet()
  {
    return (Set)EmptySet.INSTANCE;
  }
  
  private static final HashSet hashSetOf()
  {
    return new HashSet();
  }
  
  public static final HashSet hashSetOf(Object... paramVarArgs)
  {
    Intrinsics.checkParameterIsNotNull(paramVarArgs, "elements");
    return (HashSet)ArraysKt___ArraysKt.toCollection(paramVarArgs, (Collection)new HashSet(MapsKt__MapsKt.mapCapacity(paramVarArgs.length)));
  }
  
  private static final LinkedHashSet linkedSetOf()
  {
    return new LinkedHashSet();
  }
  
  public static final LinkedHashSet linkedSetOf(Object... paramVarArgs)
  {
    Intrinsics.checkParameterIsNotNull(paramVarArgs, "elements");
    return (LinkedHashSet)ArraysKt___ArraysKt.toCollection(paramVarArgs, (Collection)new LinkedHashSet(MapsKt__MapsKt.mapCapacity(paramVarArgs.length)));
  }
  
  private static final Set mutableSetOf()
  {
    return (Set)new LinkedHashSet();
  }
  
  public static final Set mutableSetOf(Object... paramVarArgs)
  {
    Intrinsics.checkParameterIsNotNull(paramVarArgs, "elements");
    return (Set)ArraysKt___ArraysKt.toCollection(paramVarArgs, (Collection)new LinkedHashSet(MapsKt__MapsKt.mapCapacity(paramVarArgs.length)));
  }
  
  public static final Set optimizeReadOnlySet(Set paramSet)
  {
    Intrinsics.checkParameterIsNotNull(paramSet, "$this$optimizeReadOnlySet");
    int i = paramSet.size();
    if (i != 0)
    {
      if (i != 1) {
        return paramSet;
      }
      return SetsKt__SetsJVMKt.setOf(paramSet.iterator().next());
    }
    return emptySet();
  }
  
  private static final Set orEmpty(Set paramSet)
  {
    if (paramSet != null) {
      return paramSet;
    }
    return emptySet();
  }
  
  private static final Set setOf()
  {
    return emptySet();
  }
  
  public static final Set setOf(Object... paramVarArgs)
  {
    Intrinsics.checkParameterIsNotNull(paramVarArgs, "elements");
    if (paramVarArgs.length > 0) {
      return ArraysKt___ArraysKt.toSet(paramVarArgs);
    }
    return emptySet();
  }
}
