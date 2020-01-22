package kotlin.collections;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000<\n\000\n\002\020\002\n\000\n\002\020!\n\002\b\004\n\002\030\002\n\000\n\002\020 \n\002\020\034\n\000\n\002\020\017\n\000\n\002\030\002\n\002\020\b\n\000\n\002\030\002\n\002\030\002\n\002\b\002\032&\020\000\032\0020\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\006\020\004\032\002H\002H?\b?\006\002\020\005\032\031\020\006\032\0020\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\003H?\b\032!\020\006\032\0020\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\006\020\007\032\0020\bH?\b\032\036\020\t\032\b\022\004\022\002H\0020\n\"\004\b\000\020\002*\b\022\004\022\002H\0020\013H\007\032&\020\t\032\b\022\004\022\002H\0020\n\"\004\b\000\020\002*\b\022\004\022\002H\0020\0132\006\020\007\032\0020\bH\007\032 \020\f\032\0020\001\"\016\b\000\020\002*\b\022\004\022\002H\0020\r*\b\022\004\022\002H\0020\003\0323\020\f\032\0020\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\030\020\016\032\024\022\004\022\002H\002\022\004\022\002H\002\022\004\022\0020\0200\017H?\b\0325\020\f\032\0020\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\032\020\021\032\026\022\006\b\000\022\002H\0020\022j\n\022\006\b\000\022\002H\002`\023H?\b\0322\020\024\032\0020\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\032\020\021\032\026\022\006\b\000\022\002H\0020\022j\n\022\006\b\000\022\002H\002`\023?\006\025"}, d2={"fill", "", "T", "", "value", "(Ljava/util/List;Ljava/lang/Object;)V", "shuffle", "random", "Ljava/util/Random;", "shuffled", "", "", "sort", "", "comparison", "Lkotlin/Function2;", "", "comparator", "Ljava/util/Comparator;", "Lkotlin/Comparator;", "sortWith", "kotlin-stdlib"}, k=5, mv={1, 1, 15}, xi=1, xs="kotlin/collections/CollectionsKt")
class CollectionsKt__MutableCollectionsJVMKt
  extends CollectionsKt__IteratorsKt
{
  public CollectionsKt__MutableCollectionsJVMKt() {}
  
  private static final void fill(List paramList, Object paramObject)
  {
    Collections.fill(paramList, paramObject);
  }
  
  private static final void shuffle(List paramList)
  {
    Collections.shuffle(paramList);
  }
  
  private static final void shuffle(List paramList, Random paramRandom)
  {
    Collections.shuffle(paramList, paramRandom);
  }
  
  public static final List shuffled(Iterable paramIterable)
  {
    Intrinsics.checkParameterIsNotNull(paramIterable, "$this$shuffled");
    paramIterable = CollectionsKt___CollectionsKt.toMutableList(paramIterable);
    Collections.shuffle(paramIterable);
    return paramIterable;
  }
  
  public static final List shuffled(Iterable paramIterable, Random paramRandom)
  {
    Intrinsics.checkParameterIsNotNull(paramIterable, "$this$shuffled");
    Intrinsics.checkParameterIsNotNull(paramRandom, "random");
    paramIterable = CollectionsKt___CollectionsKt.toMutableList(paramIterable);
    Collections.shuffle(paramIterable, paramRandom);
    return paramIterable;
  }
  
  public static final void sort(List paramList)
  {
    Intrinsics.checkParameterIsNotNull(paramList, "$this$sort");
    if (paramList.size() > 1) {
      Collections.sort(paramList);
    }
  }
  
  private static final void sort(List paramList, Comparator paramComparator)
  {
    throw ((Throwable)new NotImplementedError(null, 1, null));
  }
  
  private static final void sort(List paramList, Function2 paramFunction2)
  {
    throw ((Throwable)new NotImplementedError(null, 1, null));
  }
  
  public static final void sortWith(List paramList, Comparator paramComparator)
  {
    Intrinsics.checkParameterIsNotNull(paramList, "$this$sortWith");
    Intrinsics.checkParameterIsNotNull(paramComparator, "comparator");
    if (paramList.size() > 1) {
      Collections.sort(paramList, paramComparator);
    }
  }
}
