package kotlin.collections;

import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.ArrayIteratorKt;
import kotlin.jvm.internal.CollectionToArray;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;

@Metadata(bv={1, 0, 3}, d1={"\000.\n\002\030\002\n\000\n\002\020\036\n\000\n\002\020\021\n\000\n\002\020\013\n\002\b\003\n\002\020\b\n\002\b\f\n\002\020(\n\000\n\002\020\000\n\000\b\002\030\000*\004\b\000\020\0012\b\022\004\022\002H\0010\002B\035\022\016\020\003\032\n\022\006\b\001\022\0028\0000\004\022\006\020\005\032\0020\006?\006\002\020\007J\026\020\020\032\0020\0062\006\020\021\032\0028\000H?\002?\006\002\020\022J\026\020\023\032\0020\0062\f\020\024\032\b\022\004\022\0028\0000\002H\026J\b\020\025\032\0020\006H\026J\017\020\026\032\b\022\004\022\0028\0000\027H?\002J\025\020\030\032\f\022\b\b\001\022\004\030\0010\0310\004?\006\002\020\016R\021\020\005\032\0020\006?\006\b\n\000\032\004\b\005\020\bR\024\020\t\032\0020\n8VX?\004?\006\006\032\004\b\013\020\fR\033\020\003\032\n\022\006\b\001\022\0028\0000\004?\006\n\n\002\020\017\032\004\b\r\020\016?\006\032"}, d2={"Lkotlin/collections/ArrayAsCollection;", "T", "", "values", "", "isVarargs", "", "([Ljava/lang/Object;Z)V", "()Z", "size", "", "getSize", "()I", "getValues", "()[Ljava/lang/Object;", "[Ljava/lang/Object;", "contains", "element", "(Ljava/lang/Object;)Z", "containsAll", "elements", "isEmpty", "iterator", "", "toArray", "", "kotlin-stdlib"}, k=1, mv={1, 1, 15})
final class ArrayAsCollection<T>
  implements Collection<T>, KMappedMarker
{
  private final boolean isVarargs;
  private final T[] values;
  
  public ArrayAsCollection(Object[] paramArrayOfObject, boolean paramBoolean)
  {
    values = paramArrayOfObject;
    isVarargs = paramBoolean;
  }
  
  public boolean add(Object paramObject)
  {
    throw new UnsupportedOperationException("Operation is not supported for read-only collection");
  }
  
  public boolean addAll(Collection paramCollection)
  {
    throw new UnsupportedOperationException("Operation is not supported for read-only collection");
  }
  
  public void clear()
  {
    throw new UnsupportedOperationException("Operation is not supported for read-only collection");
  }
  
  public boolean contains(Object paramObject)
  {
    return ArraysKt___ArraysKt.contains(values, paramObject);
  }
  
  public boolean containsAll(Collection paramCollection)
  {
    Intrinsics.checkParameterIsNotNull(paramCollection, "elements");
    paramCollection = (Iterable)paramCollection;
    if (((Collection)paramCollection).isEmpty()) {
      return true;
    }
    paramCollection = paramCollection.iterator();
    while (paramCollection.hasNext()) {
      if (!contains(paramCollection.next())) {
        return false;
      }
    }
    return true;
  }
  
  public int getSize()
  {
    return values.length;
  }
  
  public final Object[] getValues()
  {
    return values;
  }
  
  public boolean isEmpty()
  {
    return values.length == 0;
  }
  
  public final boolean isVarargs()
  {
    return isVarargs;
  }
  
  public Iterator iterator()
  {
    return ArrayIteratorKt.iterator(values);
  }
  
  public boolean remove(Object paramObject)
  {
    throw new UnsupportedOperationException("Operation is not supported for read-only collection");
  }
  
  public boolean removeAll(Collection paramCollection)
  {
    throw new UnsupportedOperationException("Operation is not supported for read-only collection");
  }
  
  public boolean retainAll(Collection paramCollection)
  {
    throw new UnsupportedOperationException("Operation is not supported for read-only collection");
  }
  
  public final Object[] toArray()
  {
    return CollectionsKt__CollectionsJVMKt.copyToArrayOfAny(values, isVarargs);
  }
  
  public Object[] toArray(Object[] paramArrayOfObject)
  {
    return CollectionToArray.toArray(this, paramArrayOfObject);
  }
}
