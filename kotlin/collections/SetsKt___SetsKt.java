package kotlin.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;

@Metadata(bv={1, 0, 3}, d1={"\000\034\n\000\n\002\020\"\n\002\b\004\n\002\020\021\n\000\n\002\020\034\n\002\030\002\n\002\b\004\032,\020\000\032\b\022\004\022\002H\0020\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\0012\006\020\003\032\002H\002H?\002?\006\002\020\004\0324\020\000\032\b\022\004\022\002H\0020\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\0012\016\020\005\032\n\022\006\b\001\022\002H\0020\006H?\002?\006\002\020\007\032-\020\000\032\b\022\004\022\002H\0020\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\0012\f\020\005\032\b\022\004\022\002H\0020\bH?\002\032-\020\000\032\b\022\004\022\002H\0020\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\0012\f\020\005\032\b\022\004\022\002H\0020\tH?\002\032,\020\n\032\b\022\004\022\002H\0020\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\0012\006\020\003\032\002H\002H?\b?\006\002\020\004\032,\020\013\032\b\022\004\022\002H\0020\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\0012\006\020\003\032\002H\002H?\002?\006\002\020\004\0324\020\013\032\b\022\004\022\002H\0020\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\0012\016\020\005\032\n\022\006\b\001\022\002H\0020\006H?\002?\006\002\020\007\032-\020\013\032\b\022\004\022\002H\0020\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\0012\f\020\005\032\b\022\004\022\002H\0020\bH?\002\032-\020\013\032\b\022\004\022\002H\0020\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\0012\f\020\005\032\b\022\004\022\002H\0020\tH?\002\032,\020\f\032\b\022\004\022\002H\0020\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\0012\006\020\003\032\002H\002H?\b?\006\002\020\004?\006\r"}, d2={"minus", "", "T", "element", "(Ljava/util/Set;Ljava/lang/Object;)Ljava/util/Set;", "elements", "", "(Ljava/util/Set;[Ljava/lang/Object;)Ljava/util/Set;", "", "Lkotlin/sequences/Sequence;", "minusElement", "plus", "plusElement", "kotlin-stdlib"}, k=5, mv={1, 1, 15}, xi=1, xs="kotlin/collections/SetsKt")
class SetsKt___SetsKt
  extends SetsKt__SetsKt
{
  public SetsKt___SetsKt() {}
  
  public static final Set minus(Set paramSet, Iterable paramIterable)
  {
    Intrinsics.checkParameterIsNotNull(paramSet, "$this$minus");
    Intrinsics.checkParameterIsNotNull(paramIterable, "elements");
    Object localObject1 = (Iterable)paramSet;
    paramIterable = CollectionsKt__IterablesKt.convertToSetForSetOperationWith(paramIterable, (Iterable)localObject1);
    if (paramIterable.isEmpty()) {
      return CollectionsKt___CollectionsKt.toSet((Iterable)localObject1);
    }
    if ((paramIterable instanceof Set))
    {
      paramSet = (Collection)new LinkedHashSet();
      localObject1 = ((Iterable)localObject1).iterator();
      while (((Iterator)localObject1).hasNext())
      {
        Object localObject2 = ((Iterator)localObject1).next();
        if (!paramIterable.contains(localObject2)) {
          paramSet.add(localObject2);
        }
      }
      return (Set)paramSet;
    }
    paramSet = new LinkedHashSet((Collection)paramSet);
    paramSet.removeAll(paramIterable);
    return (Set)paramSet;
  }
  
  public static final Set minus(Set paramSet, Object paramObject)
  {
    Intrinsics.checkParameterIsNotNull(paramSet, "$this$minus");
    LinkedHashSet localLinkedHashSet = new LinkedHashSet(MapsKt__MapsKt.mapCapacity(paramSet.size()));
    paramSet = ((Iterable)paramSet).iterator();
    int j = 0;
    while (paramSet.hasNext())
    {
      Object localObject = paramSet.next();
      int m = 1;
      int k = j;
      int i = m;
      if (j == 0)
      {
        k = j;
        i = m;
        if (Intrinsics.areEqual(localObject, paramObject))
        {
          k = 1;
          i = 0;
        }
      }
      j = k;
      if (i != 0)
      {
        ((Collection)localLinkedHashSet).add(localObject);
        j = k;
      }
    }
    return (Set)localLinkedHashSet;
  }
  
  public static final Set minus(Set paramSet, Sequence paramSequence)
  {
    Intrinsics.checkParameterIsNotNull(paramSet, "$this$minus");
    Intrinsics.checkParameterIsNotNull(paramSequence, "elements");
    paramSet = new LinkedHashSet((Collection)paramSet);
    CollectionsKt__MutableCollectionsKt.removeAll((Collection)paramSet, paramSequence);
    return (Set)paramSet;
  }
  
  public static final Set minus(Set paramSet, Object[] paramArrayOfObject)
  {
    Intrinsics.checkParameterIsNotNull(paramSet, "$this$minus");
    Intrinsics.checkParameterIsNotNull(paramArrayOfObject, "elements");
    paramSet = new LinkedHashSet((Collection)paramSet);
    CollectionsKt__MutableCollectionsKt.removeAll((Collection)paramSet, paramArrayOfObject);
    return (Set)paramSet;
  }
  
  private static final Set minusElement(Set paramSet, Object paramObject)
  {
    return minus(paramSet, paramObject);
  }
  
  public static final Set plus(Set paramSet, Iterable paramIterable)
  {
    Intrinsics.checkParameterIsNotNull(paramSet, "$this$plus");
    Intrinsics.checkParameterIsNotNull(paramIterable, "elements");
    Object localObject = CollectionsKt__IterablesKt.collectionSizeOrNull(paramIterable);
    int i;
    if (localObject != null)
    {
      i = ((Number)localObject).intValue();
      i = paramSet.size() + i;
    }
    else
    {
      i = paramSet.size() * 2;
    }
    localObject = new LinkedHashSet(MapsKt__MapsKt.mapCapacity(i));
    ((LinkedHashSet)localObject).addAll((Collection)paramSet);
    CollectionsKt__MutableCollectionsKt.addAll((Collection)localObject, paramIterable);
    return (Set)localObject;
  }
  
  public static final Set plus(Set paramSet, Object paramObject)
  {
    Intrinsics.checkParameterIsNotNull(paramSet, "$this$plus");
    LinkedHashSet localLinkedHashSet = new LinkedHashSet(MapsKt__MapsKt.mapCapacity(paramSet.size() + 1));
    localLinkedHashSet.addAll((Collection)paramSet);
    localLinkedHashSet.add(paramObject);
    return (Set)localLinkedHashSet;
  }
  
  public static final Set plus(Set paramSet, Sequence paramSequence)
  {
    Intrinsics.checkParameterIsNotNull(paramSet, "$this$plus");
    Intrinsics.checkParameterIsNotNull(paramSequence, "elements");
    LinkedHashSet localLinkedHashSet = new LinkedHashSet(MapsKt__MapsKt.mapCapacity(paramSet.size() * 2));
    localLinkedHashSet.addAll((Collection)paramSet);
    CollectionsKt__MutableCollectionsKt.addAll((Collection)localLinkedHashSet, paramSequence);
    return (Set)localLinkedHashSet;
  }
  
  public static final Set plus(Set paramSet, Object[] paramArrayOfObject)
  {
    Intrinsics.checkParameterIsNotNull(paramSet, "$this$plus");
    Intrinsics.checkParameterIsNotNull(paramArrayOfObject, "elements");
    LinkedHashSet localLinkedHashSet = new LinkedHashSet(MapsKt__MapsKt.mapCapacity(paramSet.size() + paramArrayOfObject.length));
    localLinkedHashSet.addAll((Collection)paramSet);
    CollectionsKt__MutableCollectionsKt.addAll((Collection)localLinkedHashSet, paramArrayOfObject);
    return (Set)localLinkedHashSet;
  }
  
  private static final Set plusElement(Set paramSet, Object paramObject)
  {
    return plus(paramSet, paramObject);
  }
}
