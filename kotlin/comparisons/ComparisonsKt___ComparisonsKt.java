package kotlin.comparisons;

import java.util.Comparator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000\020\n\002\b\006\n\002\030\002\n\002\030\002\n\002\b\004\032G\020\000\032\002H\001\"\004\b\000\020\0012\006\020\002\032\002H\0012\006\020\003\032\002H\0012\006\020\004\032\002H\0012\032\020\005\032\026\022\006\b\000\022\002H\0010\006j\n\022\006\b\000\022\002H\001`\007H\007?\006\002\020\b\032?\020\000\032\002H\001\"\004\b\000\020\0012\006\020\002\032\002H\0012\006\020\003\032\002H\0012\032\020\005\032\026\022\006\b\000\022\002H\0010\006j\n\022\006\b\000\022\002H\001`\007H\007?\006\002\020\t\032G\020\n\032\002H\001\"\004\b\000\020\0012\006\020\002\032\002H\0012\006\020\003\032\002H\0012\006\020\004\032\002H\0012\032\020\005\032\026\022\006\b\000\022\002H\0010\006j\n\022\006\b\000\022\002H\001`\007H\007?\006\002\020\b\032?\020\n\032\002H\001\"\004\b\000\020\0012\006\020\002\032\002H\0012\006\020\003\032\002H\0012\032\020\005\032\026\022\006\b\000\022\002H\0010\006j\n\022\006\b\000\022\002H\001`\007H\007?\006\002\020\t?\006\013"}, d2={"maxOf", "T", "a", "b", "c", "comparator", "Ljava/util/Comparator;", "Lkotlin/Comparator;", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/util/Comparator;)Ljava/lang/Object;", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/util/Comparator;)Ljava/lang/Object;", "minOf", "kotlin-stdlib"}, k=5, mv={1, 1, 15}, xi=1, xs="kotlin/comparisons/ComparisonsKt")
class ComparisonsKt___ComparisonsKt
  extends ComparisonsKt___ComparisonsJvmKt
{
  public ComparisonsKt___ComparisonsKt() {}
  
  public static final Object maxOf(Object paramObject1, Object paramObject2, Object paramObject3, Comparator paramComparator)
  {
    Intrinsics.checkParameterIsNotNull(paramComparator, "comparator");
    return maxOf(paramObject1, maxOf(paramObject2, paramObject3, paramComparator), paramComparator);
  }
  
  public static final Object maxOf(Object paramObject1, Object paramObject2, Comparator paramComparator)
  {
    Intrinsics.checkParameterIsNotNull(paramComparator, "comparator");
    if (paramComparator.compare(paramObject1, paramObject2) >= 0) {
      return paramObject1;
    }
    return paramObject2;
  }
  
  public static final Object minOf(Object paramObject1, Object paramObject2, Object paramObject3, Comparator paramComparator)
  {
    Intrinsics.checkParameterIsNotNull(paramComparator, "comparator");
    return minOf(paramObject1, minOf(paramObject2, paramObject3, paramComparator), paramComparator);
  }
  
  public static final Object minOf(Object paramObject1, Object paramObject2, Comparator paramComparator)
  {
    Intrinsics.checkParameterIsNotNull(paramComparator, "comparator");
    if (paramComparator.compare(paramObject1, paramObject2) <= 0) {
      return paramObject1;
    }
    return paramObject2;
  }
}
