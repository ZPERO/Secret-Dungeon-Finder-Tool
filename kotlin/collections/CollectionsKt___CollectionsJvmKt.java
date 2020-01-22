package kotlin.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000>\n\000\n\002\020 \n\000\n\002\020\034\n\000\n\002\030\002\n\002\b\002\n\002\020\037\n\002\b\003\n\002\020\002\n\000\n\002\020!\n\000\n\002\030\002\n\002\020\017\n\000\n\002\030\002\n\002\030\002\n\000\032(\020\000\032\b\022\004\022\002H\0020\001\"\004\b\000\020\002*\006\022\002\b\0030\0032\f\020\004\032\b\022\004\022\002H\0020\005\032A\020\006\032\002H\007\"\020\b\000\020\007*\n\022\006\b\000\022\002H\0020\b\"\004\b\001\020\002*\006\022\002\b\0030\0032\006\020\t\032\002H\0072\f\020\004\032\b\022\004\022\002H\0020\005?\006\002\020\n\032\026\020\013\032\0020\f\"\004\b\000\020\r*\b\022\004\022\002H\r0\016\032&\020\017\032\b\022\004\022\002H\r0\020\"\016\b\000\020\r*\b\022\004\022\002H\r0\021*\b\022\004\022\002H\r0\003\0328\020\017\032\b\022\004\022\002H\r0\020\"\004\b\000\020\r*\b\022\004\022\002H\r0\0032\032\020\022\032\026\022\006\b\000\022\002H\r0\023j\n\022\006\b\000\022\002H\r`\024?\006\025"}, d2={"filterIsInstance", "", "R", "", "klass", "Ljava/lang/Class;", "filterIsInstanceTo", "C", "", "destination", "(Ljava/lang/Iterable;Ljava/util/Collection;Ljava/lang/Class;)Ljava/util/Collection;", "reverse", "", "T", "", "toSortedSet", "Ljava/util/SortedSet;", "", "comparator", "Ljava/util/Comparator;", "Lkotlin/Comparator;", "kotlin-stdlib"}, k=5, mv={1, 1, 15}, xi=1, xs="kotlin/collections/CollectionsKt")
class CollectionsKt___CollectionsJvmKt
  extends CollectionsKt__ReversedViewsKt
{
  public CollectionsKt___CollectionsJvmKt() {}
  
  public static final List filterIsInstance(Iterable paramIterable, Class paramClass)
  {
    Intrinsics.checkParameterIsNotNull(paramIterable, "$this$filterIsInstance");
    Intrinsics.checkParameterIsNotNull(paramClass, "klass");
    return (List)filterIsInstanceTo(paramIterable, (Collection)new ArrayList(), paramClass);
  }
  
  public static final Collection filterIsInstanceTo(Iterable paramIterable, Collection paramCollection, Class paramClass)
  {
    Intrinsics.checkParameterIsNotNull(paramIterable, "$this$filterIsInstanceTo");
    Intrinsics.checkParameterIsNotNull(paramCollection, "destination");
    Intrinsics.checkParameterIsNotNull(paramClass, "klass");
    paramIterable = paramIterable.iterator();
    while (paramIterable.hasNext())
    {
      Object localObject = paramIterable.next();
      if (paramClass.isInstance(localObject)) {
        paramCollection.add(localObject);
      }
    }
    return paramCollection;
  }
  
  public static final void reverse(List paramList)
  {
    Intrinsics.checkParameterIsNotNull(paramList, "$this$reverse");
    Collections.reverse(paramList);
  }
  
  public static final SortedSet toSortedSet(Iterable paramIterable)
  {
    Intrinsics.checkParameterIsNotNull(paramIterable, "$this$toSortedSet");
    return (SortedSet)CollectionsKt___CollectionsKt.toCollection(paramIterable, (Collection)new TreeSet());
  }
  
  public static final SortedSet toSortedSet(Iterable paramIterable, Comparator paramComparator)
  {
    Intrinsics.checkParameterIsNotNull(paramIterable, "$this$toSortedSet");
    Intrinsics.checkParameterIsNotNull(paramComparator, "comparator");
    return (SortedSet)CollectionsKt___CollectionsKt.toCollection(paramIterable, (Collection)new TreeSet(paramComparator));
  }
}
