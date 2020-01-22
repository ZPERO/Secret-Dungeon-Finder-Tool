package kotlin;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.UShortIterator;
import kotlin.jvm.internal.CollectionToArray;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;

@Metadata(bv={1, 0, 3}, d1={"\000F\n\002\030\002\n\002\020\036\n\002\030\002\n\000\n\002\020\b\n\002\b\003\n\002\020\027\n\002\b\006\n\002\020\013\n\002\b\t\n\002\020\000\n\002\b\t\n\002\030\002\n\002\b\003\n\002\020\002\n\002\b\004\n\002\020\016\n\002\b\002\b?@\030\0002\b\022\004\022\0020\0020\001:\001-B\024\b\026\022\006\020\003\032\0020\004?\001\000?\006\004\b\005\020\006B\024\b\001\022\006\020\007\032\0020\b?\001\000?\006\004\b\005\020\tJ\033\020\016\032\0020\0172\006\020\020\032\0020\002H?\002?\001\000?\006\004\b\021\020\022J \020\023\032\0020\0172\f\020\024\032\b\022\004\022\0020\0020\001H\026?\001\000?\006\004\b\025\020\026J\023\020\027\032\0020\0172\b\020\030\032\004\030\0010\031H?\003J\033\020\032\032\0020\0022\006\020\033\032\0020\004H?\002?\001\000?\006\004\b\034\020\035J\t\020\036\032\0020\004H?\001J\017\020\037\032\0020\017H\026?\006\004\b \020!J\020\020\"\032\0020#H?\002?\006\004\b$\020%J#\020&\032\0020'2\006\020\033\032\0020\0042\006\020(\032\0020\002H?\002?\001\000?\006\004\b)\020*J\t\020+\032\0020,H?\001R\024\020\003\032\0020\0048VX?\004?\006\006\032\004\b\n\020\013R\026\020\007\032\0020\b8\000X?\004?\006\b\n\000\022\004\b\f\020\r?\001\000?\002\004\n\002\b\031?\006."}, d2={"Lkotlin/UShortArray;", "", "Lkotlin/UShort;", "size", "", "constructor-impl", "(I)[S", "storage", "", "([S)[S", "getSize-impl", "([S)I", "storage$annotations", "()V", "contains", "", "element", "contains-xj2QHRw", "([SS)Z", "containsAll", "elements", "containsAll-impl", "([SLjava/util/Collection;)Z", "equals", "other", "", "get", "index", "get-impl", "([SI)S", "hashCode", "isEmpty", "isEmpty-impl", "([S)Z", "iterator", "Lkotlin/collections/UShortIterator;", "iterator-impl", "([S)Lkotlin/collections/UShortIterator;", "set", "", "value", "set-01HTLdE", "([SIS)V", "toString", "", "Iterator", "kotlin-stdlib"}, k=1, mv={1, 1, 15})
public final class UShortArray
  implements Collection<UShort>, KMappedMarker
{
  private final short[] storage;
  
  public static short[] constructor-impl(int paramInt)
  {
    return constructor-impl(new short[paramInt]);
  }
  
  public static short[] constructor-impl(short[] paramArrayOfShort)
  {
    Intrinsics.checkParameterIsNotNull(paramArrayOfShort, "storage");
    return paramArrayOfShort;
  }
  
  public static boolean contains-xj2QHRw(short[] paramArrayOfShort, short paramShort)
  {
    return ArraysKt___ArraysKt.contains(paramArrayOfShort, paramShort);
  }
  
  public static boolean containsAll-impl(short[] paramArrayOfShort, Collection paramCollection)
  {
    Intrinsics.checkParameterIsNotNull(paramCollection, "elements");
    paramCollection = (Iterable)paramCollection;
    if (((Collection)paramCollection).isEmpty()) {
      return true;
    }
    paramCollection = paramCollection.iterator();
    while (paramCollection.hasNext())
    {
      Object localObject = paramCollection.next();
      int i;
      if (((localObject instanceof UShort)) && (ArraysKt___ArraysKt.contains(paramArrayOfShort, ((UShort)localObject).unbox-impl()))) {
        i = 1;
      } else {
        i = 0;
      }
      if (i == 0) {
        return false;
      }
    }
    return true;
  }
  
  public static boolean equals-impl(short[] paramArrayOfShort, Object paramObject)
  {
    return ((paramObject instanceof UShortArray)) && (Intrinsics.areEqual(paramArrayOfShort, ((UShortArray)paramObject).unbox-impl()));
  }
  
  public static final boolean equals-impl0(short[] paramArrayOfShort1, short[] paramArrayOfShort2)
  {
    Intrinsics.checkParameterIsNotNull(paramArrayOfShort1, "p1");
    Intrinsics.checkParameterIsNotNull(paramArrayOfShort2, "p2");
    throw new NullPointerException("Null throw statement replaced by Soot");
  }
  
  public static final short get-impl(short[] paramArrayOfShort, int paramInt)
  {
    return UShort.constructor-impl(paramArrayOfShort[paramInt]);
  }
  
  public static int getSize-impl(short[] paramArrayOfShort)
  {
    return paramArrayOfShort.length;
  }
  
  public static int hashCode-impl(short[] paramArrayOfShort)
  {
    if (paramArrayOfShort != null) {
      return Arrays.hashCode(paramArrayOfShort);
    }
    return 0;
  }
  
  public static boolean isEmpty-impl(short[] paramArrayOfShort)
  {
    return paramArrayOfShort.length == 0;
  }
  
  public static UShortIterator iterator-impl(short[] paramArrayOfShort)
  {
    return (UShortIterator)new Iterator(paramArrayOfShort);
  }
  
  public static final void set-01HTLdE(short[] paramArrayOfShort, int paramInt, short paramShort)
  {
    paramArrayOfShort[paramInt] = paramShort;
  }
  
  public static String toString-impl(short[] paramArrayOfShort)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("UShortArray(storage=");
    localStringBuilder.append(Arrays.toString(paramArrayOfShort));
    localStringBuilder.append(")");
    return localStringBuilder.toString();
  }
  
  public boolean add-xj2QHRw(short paramShort)
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
  
  public boolean contains-xj2QHRw(short paramShort)
  {
    return contains-xj2QHRw(storage, paramShort);
  }
  
  public boolean containsAll(Collection paramCollection)
  {
    return containsAll-impl(storage, paramCollection);
  }
  
  public boolean equals(Object paramObject)
  {
    return equals-impl(storage, paramObject);
  }
  
  public int getSize()
  {
    return getSize-impl(storage);
  }
  
  public int hashCode()
  {
    return hashCode-impl(storage);
  }
  
  public boolean isEmpty()
  {
    return isEmpty-impl(storage);
  }
  
  public UShortIterator iterator()
  {
    return iterator-impl(storage);
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
  
  public Object[] toArray()
  {
    return CollectionToArray.toArray(this);
  }
  
  public Object[] toArray(Object[] paramArrayOfObject)
  {
    return CollectionToArray.toArray(this, paramArrayOfObject);
  }
  
  public String toString()
  {
    return toString-impl(storage);
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000&\n\002\030\002\n\002\030\002\n\000\n\002\020\027\n\002\b\002\n\002\020\b\n\000\n\002\020\013\n\000\n\002\030\002\n\002\b\002\b\002\030\0002\0020\001B\r\022\006\020\002\032\0020\003?\006\002\020\004J\t\020\007\032\0020\bH?\002J\020\020\t\032\0020\nH\026?\001\000?\006\002\020\013R\016\020\002\032\0020\003X?\004?\006\002\n\000R\016\020\005\032\0020\006X?\016?\006\002\n\000?\002\004\n\002\b\031?\006\f"}, d2={"Lkotlin/UShortArray$Iterator;", "Lkotlin/collections/UShortIterator;", "array", "", "([S)V", "index", "", "hasNext", "", "nextUShort", "Lkotlin/UShort;", "()S", "kotlin-stdlib"}, k=1, mv={1, 1, 15})
  private static final class Iterator
    extends UShortIterator
  {
    private final short[] array;
    private int index;
    
    public Iterator(short[] paramArrayOfShort)
    {
      array = paramArrayOfShort;
    }
    
    public boolean hasNext()
    {
      return index < array.length;
    }
    
    public short nextUShort()
    {
      int i = index;
      short[] arrayOfShort = array;
      if (i < arrayOfShort.length)
      {
        index = (i + 1);
        return UShort.constructor-impl(arrayOfShort[i]);
      }
      throw ((Throwable)new NoSuchElementException(String.valueOf(i)));
    }
  }
}
