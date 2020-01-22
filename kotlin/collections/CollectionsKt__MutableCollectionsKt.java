package kotlin.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.RandomAccess;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.random.Random;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt___SequencesKt;

@Metadata(bv={1, 0, 3}, d1={"\000^\n\000\n\002\020\013\n\000\n\002\020\037\n\000\n\002\020\021\n\000\n\002\020\034\n\002\030\002\n\000\n\002\020\035\n\000\n\002\030\002\n\002\b\002\n\002\020!\n\000\n\002\020\002\n\002\b\005\n\002\030\002\n\002\b\002\n\002\020\b\n\002\b\002\n\002\020\036\n\002\b\005\n\002\030\002\n\000\n\002\020 \n\000\032-\020\000\032\0020\001\"\004\b\000\020\002*\n\022\006\b\000\022\002H\0020\0032\016\020\004\032\n\022\006\b\001\022\002H\0020\005?\006\002\020\006\032&\020\000\032\0020\001\"\004\b\000\020\002*\n\022\006\b\000\022\002H\0020\0032\f\020\004\032\b\022\004\022\002H\0020\007\032&\020\000\032\0020\001\"\004\b\000\020\002*\n\022\006\b\000\022\002H\0020\0032\f\020\004\032\b\022\004\022\002H\0020\b\0329\020\t\032\0020\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\n2\022\020\013\032\016\022\004\022\002H\002\022\004\022\0020\0010\f2\006\020\r\032\0020\001H\002?\006\002\b\016\0329\020\t\032\0020\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\0172\022\020\013\032\016\022\004\022\002H\002\022\004\022\0020\0010\f2\006\020\r\032\0020\001H\002?\006\002\b\016\032(\020\020\032\0020\021\"\004\b\000\020\002*\n\022\006\b\000\022\002H\0020\0032\006\020\022\032\002H\002H?\n?\006\002\020\023\032.\020\020\032\0020\021\"\004\b\000\020\002*\n\022\006\b\000\022\002H\0020\0032\f\020\004\032\b\022\004\022\002H\0020\005H?\n?\006\002\020\024\032)\020\020\032\0020\021\"\004\b\000\020\002*\n\022\006\b\000\022\002H\0020\0032\f\020\004\032\b\022\004\022\002H\0020\007H?\n\032)\020\020\032\0020\021\"\004\b\000\020\002*\n\022\006\b\000\022\002H\0020\0032\f\020\004\032\b\022\004\022\002H\0020\bH?\n\032(\020\025\032\0020\021\"\004\b\000\020\002*\n\022\006\b\000\022\002H\0020\0032\006\020\022\032\002H\002H?\n?\006\002\020\023\032.\020\025\032\0020\021\"\004\b\000\020\002*\n\022\006\b\000\022\002H\0020\0032\f\020\004\032\b\022\004\022\002H\0020\005H?\n?\006\002\020\024\032)\020\025\032\0020\021\"\004\b\000\020\002*\n\022\006\b\000\022\002H\0020\0032\f\020\004\032\b\022\004\022\002H\0020\007H?\n\032)\020\025\032\0020\021\"\004\b\000\020\002*\n\022\006\b\000\022\002H\0020\0032\f\020\004\032\b\022\004\022\002H\0020\bH?\n\032-\020\026\032\0020\001\"\t\b\000\020\002?\006\002\b\027*\n\022\006\b\001\022\002H\0020\0032\006\020\022\032\002H\002H?\b?\006\002\020\030\032&\020\026\032\002H\002\"\004\b\000\020\002*\b\022\004\022\002H\0020\0172\006\020\031\032\0020\032H?\b?\006\002\020\033\032-\020\034\032\0020\001\"\004\b\000\020\002*\n\022\006\b\000\022\002H\0020\0032\016\020\004\032\n\022\006\b\001\022\002H\0020\005?\006\002\020\006\032&\020\034\032\0020\001\"\004\b\000\020\002*\n\022\006\b\000\022\002H\0020\0032\f\020\004\032\b\022\004\022\002H\0020\007\032&\020\034\032\0020\001\"\004\b\000\020\002*\n\022\006\b\000\022\002H\0020\0032\f\020\004\032\b\022\004\022\002H\0020\b\032.\020\034\032\0020\001\"\t\b\000\020\002?\006\002\b\027*\n\022\006\b\001\022\002H\0020\0032\f\020\004\032\b\022\004\022\002H\0020\035H?\b\032*\020\034\032\0020\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\n2\022\020\013\032\016\022\004\022\002H\002\022\004\022\0020\0010\f\032*\020\034\032\0020\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\0172\022\020\013\032\016\022\004\022\002H\002\022\004\022\0020\0010\f\032-\020\036\032\0020\001\"\004\b\000\020\002*\n\022\006\b\000\022\002H\0020\0032\016\020\004\032\n\022\006\b\001\022\002H\0020\005?\006\002\020\006\032&\020\036\032\0020\001\"\004\b\000\020\002*\n\022\006\b\000\022\002H\0020\0032\f\020\004\032\b\022\004\022\002H\0020\007\032&\020\036\032\0020\001\"\004\b\000\020\002*\n\022\006\b\000\022\002H\0020\0032\f\020\004\032\b\022\004\022\002H\0020\b\032.\020\036\032\0020\001\"\t\b\000\020\002?\006\002\b\027*\n\022\006\b\001\022\002H\0020\0032\f\020\004\032\b\022\004\022\002H\0020\035H?\b\032*\020\036\032\0020\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\n2\022\020\013\032\016\022\004\022\002H\002\022\004\022\0020\0010\f\032*\020\036\032\0020\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\0172\022\020\013\032\016\022\004\022\002H\002\022\004\022\0020\0010\f\032\025\020\037\032\0020\001*\006\022\002\b\0030\003H\002?\006\002\b \032 \020!\032\0020\021\"\004\b\000\020\002*\b\022\004\022\002H\0020\0172\006\020\"\032\0020#H\007\032&\020$\032\b\022\004\022\002H\0020%\"\004\b\000\020\002*\b\022\004\022\002H\0020\0072\006\020\"\032\0020#H\007?\006&"}, d2={"addAll", "", "T", "", "elements", "", "(Ljava/util/Collection;[Ljava/lang/Object;)Z", "", "Lkotlin/sequences/Sequence;", "filterInPlace", "", "predicate", "Lkotlin/Function1;", "predicateResultToRemove", "filterInPlace$CollectionsKt__MutableCollectionsKt", "", "minusAssign", "", "element", "(Ljava/util/Collection;Ljava/lang/Object;)V", "(Ljava/util/Collection;[Ljava/lang/Object;)V", "plusAssign", "remove", "Lkotlin/internal/OnlyInputTypes;", "(Ljava/util/Collection;Ljava/lang/Object;)Z", "index", "", "(Ljava/util/List;I)Ljava/lang/Object;", "removeAll", "", "retainAll", "retainNothing", "retainNothing$CollectionsKt__MutableCollectionsKt", "shuffle", "random", "Lkotlin/random/Random;", "shuffled", "", "kotlin-stdlib"}, k=5, mv={1, 1, 15}, xi=1, xs="kotlin/collections/CollectionsKt")
class CollectionsKt__MutableCollectionsKt
  extends CollectionsKt__MutableCollectionsJVMKt
{
  public CollectionsKt__MutableCollectionsKt() {}
  
  public static final boolean addAll(Collection paramCollection, Iterable paramIterable)
  {
    Intrinsics.checkParameterIsNotNull(paramCollection, "$this$addAll");
    Intrinsics.checkParameterIsNotNull(paramIterable, "elements");
    if ((paramIterable instanceof Collection)) {
      return paramCollection.addAll((Collection)paramIterable);
    }
    boolean bool = false;
    paramIterable = paramIterable.iterator();
    while (paramIterable.hasNext()) {
      if (paramCollection.add(paramIterable.next())) {
        bool = true;
      }
    }
    return bool;
  }
  
  public static final boolean addAll(Collection paramCollection, Sequence paramSequence)
  {
    Intrinsics.checkParameterIsNotNull(paramCollection, "$this$addAll");
    Intrinsics.checkParameterIsNotNull(paramSequence, "elements");
    paramSequence = paramSequence.iterator();
    boolean bool = false;
    while (paramSequence.hasNext()) {
      if (paramCollection.add(paramSequence.next())) {
        bool = true;
      }
    }
    return bool;
  }
  
  public static final boolean addAll(Collection paramCollection, Object[] paramArrayOfObject)
  {
    Intrinsics.checkParameterIsNotNull(paramCollection, "$this$addAll");
    Intrinsics.checkParameterIsNotNull(paramArrayOfObject, "elements");
    return paramCollection.addAll((Collection)ArraysKt___ArraysJvmKt.asList(paramArrayOfObject));
  }
  
  private static final boolean filterInPlace$CollectionsKt__MutableCollectionsKt(Iterable paramIterable, Function1 paramFunction1, boolean paramBoolean)
  {
    paramIterable = paramIterable.iterator();
    boolean bool = false;
    while (paramIterable.hasNext()) {
      if (((Boolean)paramFunction1.invoke(paramIterable.next())).booleanValue() == paramBoolean)
      {
        paramIterable.remove();
        bool = true;
      }
    }
    return bool;
  }
  
  private static final boolean filterInPlace$CollectionsKt__MutableCollectionsKt(List paramList, Function1 paramFunction1, boolean paramBoolean)
  {
    if (!(paramList instanceof RandomAccess))
    {
      if (paramList != null) {
        return filterInPlace$CollectionsKt__MutableCollectionsKt(TypeIntrinsics.asMutableIterable(paramList), paramFunction1, paramBoolean);
      }
      throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.MutableIterable<T>");
    }
    int m = CollectionsKt__CollectionsKt.getLastIndex(paramList);
    int k;
    int i;
    if (m >= 0)
    {
      k = 0;
      i = 0;
      for (;;)
      {
        Object localObject = paramList.get(k);
        if (((Boolean)paramFunction1.invoke(localObject)).booleanValue() != paramBoolean)
        {
          if (i != k) {
            paramList.set(i, localObject);
          }
          i += 1;
        }
        j = i;
        if (k == m) {
          break;
        }
        k += 1;
      }
    }
    int j = 0;
    if (j < paramList.size())
    {
      k = CollectionsKt__CollectionsKt.getLastIndex(paramList);
      i = k;
      if (k >= j) {
        for (;;)
        {
          paramList.remove(i);
          if (i == j) {
            break;
          }
          i -= 1;
        }
      }
      return true;
    }
    return false;
  }
  
  private static final void minusAssign(Collection paramCollection, Iterable paramIterable)
  {
    Intrinsics.checkParameterIsNotNull(paramCollection, "$this$minusAssign");
    removeAll(paramCollection, paramIterable);
  }
  
  private static final void minusAssign(Collection paramCollection, Object paramObject)
  {
    Intrinsics.checkParameterIsNotNull(paramCollection, "$this$minusAssign");
    paramCollection.remove(paramObject);
  }
  
  private static final void minusAssign(Collection paramCollection, Sequence paramSequence)
  {
    Intrinsics.checkParameterIsNotNull(paramCollection, "$this$minusAssign");
    removeAll(paramCollection, paramSequence);
  }
  
  private static final void minusAssign(Collection paramCollection, Object[] paramArrayOfObject)
  {
    Intrinsics.checkParameterIsNotNull(paramCollection, "$this$minusAssign");
    removeAll(paramCollection, paramArrayOfObject);
  }
  
  private static final void plusAssign(Collection paramCollection, Iterable paramIterable)
  {
    Intrinsics.checkParameterIsNotNull(paramCollection, "$this$plusAssign");
    addAll(paramCollection, paramIterable);
  }
  
  private static final void plusAssign(Collection paramCollection, Object paramObject)
  {
    Intrinsics.checkParameterIsNotNull(paramCollection, "$this$plusAssign");
    paramCollection.add(paramObject);
  }
  
  private static final void plusAssign(Collection paramCollection, Sequence paramSequence)
  {
    Intrinsics.checkParameterIsNotNull(paramCollection, "$this$plusAssign");
    addAll(paramCollection, paramSequence);
  }
  
  private static final void plusAssign(Collection paramCollection, Object[] paramArrayOfObject)
  {
    Intrinsics.checkParameterIsNotNull(paramCollection, "$this$plusAssign");
    addAll(paramCollection, paramArrayOfObject);
  }
  
  private static final Object remove(List paramList, int paramInt)
  {
    return paramList.remove(paramInt);
  }
  
  private static final boolean remove(Collection paramCollection, Object paramObject)
  {
    if (paramCollection != null) {
      return TypeIntrinsics.asMutableCollection(paramCollection).remove(paramObject);
    }
    throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.MutableCollection<T>");
  }
  
  public static final boolean removeAll(Iterable paramIterable, Function1 paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramIterable, "$this$removeAll");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "predicate");
    return filterInPlace$CollectionsKt__MutableCollectionsKt(paramIterable, paramFunction1, true);
  }
  
  public static final boolean removeAll(Collection paramCollection, Iterable paramIterable)
  {
    Intrinsics.checkParameterIsNotNull(paramCollection, "$this$removeAll");
    Intrinsics.checkParameterIsNotNull(paramIterable, "elements");
    paramIterable = CollectionsKt__IterablesKt.convertToSetForSetOperationWith(paramIterable, (Iterable)paramCollection);
    return TypeIntrinsics.asMutableCollection(paramCollection).removeAll(paramIterable);
  }
  
  private static final boolean removeAll(Collection paramCollection1, Collection paramCollection2)
  {
    if (paramCollection1 != null) {
      return TypeIntrinsics.asMutableCollection(paramCollection1).removeAll(paramCollection2);
    }
    throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.MutableCollection<T>");
  }
  
  public static final boolean removeAll(Collection paramCollection, Sequence paramSequence)
  {
    Intrinsics.checkParameterIsNotNull(paramCollection, "$this$removeAll");
    Intrinsics.checkParameterIsNotNull(paramSequence, "elements");
    paramSequence = (Collection)SequencesKt___SequencesKt.toHashSet(paramSequence);
    return ((paramSequence.isEmpty() ^ true)) && (paramCollection.removeAll(paramSequence));
  }
  
  public static final boolean removeAll(Collection paramCollection, Object[] paramArrayOfObject)
  {
    Intrinsics.checkParameterIsNotNull(paramCollection, "$this$removeAll");
    Intrinsics.checkParameterIsNotNull(paramArrayOfObject, "elements");
    int i;
    if (paramArrayOfObject.length == 0) {
      i = 1;
    } else {
      i = 0;
    }
    return ((i ^ 0x1) != 0) && (paramCollection.removeAll((Collection)ArraysKt___ArraysKt.toHashSet(paramArrayOfObject)));
  }
  
  public static final boolean removeAll(List paramList, Function1 paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramList, "$this$removeAll");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "predicate");
    return filterInPlace$CollectionsKt__MutableCollectionsKt(paramList, paramFunction1, true);
  }
  
  public static final boolean retainAll(Iterable paramIterable, Function1 paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramIterable, "$this$retainAll");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "predicate");
    return filterInPlace$CollectionsKt__MutableCollectionsKt(paramIterable, paramFunction1, false);
  }
  
  public static final boolean retainAll(Collection paramCollection, Iterable paramIterable)
  {
    Intrinsics.checkParameterIsNotNull(paramCollection, "$this$retainAll");
    Intrinsics.checkParameterIsNotNull(paramIterable, "elements");
    paramIterable = CollectionsKt__IterablesKt.convertToSetForSetOperationWith(paramIterable, (Iterable)paramCollection);
    return TypeIntrinsics.asMutableCollection(paramCollection).retainAll(paramIterable);
  }
  
  private static final boolean retainAll(Collection paramCollection1, Collection paramCollection2)
  {
    if (paramCollection1 != null) {
      return TypeIntrinsics.asMutableCollection(paramCollection1).retainAll(paramCollection2);
    }
    throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.MutableCollection<T>");
  }
  
  public static final boolean retainAll(Collection paramCollection, Sequence paramSequence)
  {
    Intrinsics.checkParameterIsNotNull(paramCollection, "$this$retainAll");
    Intrinsics.checkParameterIsNotNull(paramSequence, "elements");
    paramSequence = (Collection)SequencesKt___SequencesKt.toHashSet(paramSequence);
    if ((paramSequence.isEmpty() ^ true)) {
      return paramCollection.retainAll(paramSequence);
    }
    return retainNothing$CollectionsKt__MutableCollectionsKt(paramCollection);
  }
  
  public static final boolean retainAll(Collection paramCollection, Object[] paramArrayOfObject)
  {
    Intrinsics.checkParameterIsNotNull(paramCollection, "$this$retainAll");
    Intrinsics.checkParameterIsNotNull(paramArrayOfObject, "elements");
    int i;
    if (paramArrayOfObject.length == 0) {
      i = 1;
    } else {
      i = 0;
    }
    if ((i ^ 0x1) != 0) {
      return paramCollection.retainAll((Collection)ArraysKt___ArraysKt.toHashSet(paramArrayOfObject));
    }
    return retainNothing$CollectionsKt__MutableCollectionsKt(paramCollection);
  }
  
  public static final boolean retainAll(List paramList, Function1 paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramList, "$this$retainAll");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "predicate");
    return filterInPlace$CollectionsKt__MutableCollectionsKt(paramList, paramFunction1, false);
  }
  
  private static final boolean retainNothing$CollectionsKt__MutableCollectionsKt(Collection paramCollection)
  {
    boolean bool = paramCollection.isEmpty();
    paramCollection.clear();
    return bool ^ true;
  }
  
  public static final void shuffle(List paramList, Random paramRandom)
  {
    Intrinsics.checkParameterIsNotNull(paramList, "$this$shuffle");
    Intrinsics.checkParameterIsNotNull(paramRandom, "random");
    int i = CollectionsKt__CollectionsKt.getLastIndex(paramList);
    while (i >= 1)
    {
      int j = paramRandom.nextInt(i + 1);
      Object localObject = paramList.get(i);
      paramList.set(i, paramList.get(j));
      paramList.set(j, localObject);
      i -= 1;
    }
  }
  
  public static final List shuffled(Iterable paramIterable, Random paramRandom)
  {
    Intrinsics.checkParameterIsNotNull(paramIterable, "$this$shuffled");
    Intrinsics.checkParameterIsNotNull(paramRandom, "random");
    paramIterable = CollectionsKt___CollectionsKt.toMutableList(paramIterable);
    shuffle(paramIterable, paramRandom);
    return paramIterable;
  }
}
