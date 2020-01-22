package kotlin.collections;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000$\n\000\n\002\020\"\n\002\b\004\n\002\030\002\n\000\n\002\020\021\n\002\b\002\n\002\030\002\n\002\030\002\n\002\b\002\032\037\020\000\032\b\022\004\022\002H\0020\001\"\004\b\000\020\0022\006\020\003\032\002H\002?\006\002\020\004\032+\020\005\032\b\022\004\022\002H\0020\006\"\004\b\000\020\0022\022\020\007\032\n\022\006\b\001\022\002H\0020\b\"\002H\002?\006\002\020\t\032G\020\005\032\b\022\004\022\002H\0020\006\"\004\b\000\020\0022\032\020\n\032\026\022\006\b\000\022\002H\0020\013j\n\022\006\b\000\022\002H\002`\f2\022\020\007\032\n\022\006\b\001\022\002H\0020\b\"\002H\002?\006\002\020\r?\006\016"}, d2={"setOf", "", "T", "element", "(Ljava/lang/Object;)Ljava/util/Set;", "sortedSetOf", "Ljava/util/TreeSet;", "elements", "", "([Ljava/lang/Object;)Ljava/util/TreeSet;", "comparator", "Ljava/util/Comparator;", "Lkotlin/Comparator;", "(Ljava/util/Comparator;[Ljava/lang/Object;)Ljava/util/TreeSet;", "kotlin-stdlib"}, k=5, mv={1, 1, 15}, xi=1, xs="kotlin/collections/SetsKt")
class SetsKt__SetsJVMKt
{
  public SetsKt__SetsJVMKt() {}
  
  public static final Set setOf(Object paramObject)
  {
    paramObject = Collections.singleton(paramObject);
    Intrinsics.checkExpressionValueIsNotNull(paramObject, "java.util.Collections.singleton(element)");
    return paramObject;
  }
  
  public static final TreeSet sortedSetOf(Comparator paramComparator, Object... paramVarArgs)
  {
    Intrinsics.checkParameterIsNotNull(paramComparator, "comparator");
    Intrinsics.checkParameterIsNotNull(paramVarArgs, "elements");
    return (TreeSet)ArraysKt___ArraysKt.toCollection(paramVarArgs, (Collection)new TreeSet(paramComparator));
  }
  
  public static final TreeSet sortedSetOf(Object... paramVarArgs)
  {
    Intrinsics.checkParameterIsNotNull(paramVarArgs, "elements");
    return (TreeSet)ArraysKt___ArraysKt.toCollection(paramVarArgs, (Collection)new TreeSet());
  }
}
