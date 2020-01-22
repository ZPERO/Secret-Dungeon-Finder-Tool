package androidx.core.util;

import android.util.LongSparseArray;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.LongIterator;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;

@Metadata(bv={1, 0, 3}, d1={"\000F\n\000\n\002\020\b\n\000\n\002\030\002\n\002\b\003\n\002\020\013\n\000\n\002\020\t\n\002\b\005\n\002\020\002\n\000\n\002\030\002\n\002\030\002\n\002\b\005\n\002\030\002\n\002\b\004\n\002\030\002\n\002\b\b\n\002\020(\n\000\032!\020\006\032\0020\007\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\006\020\b\032\0020\tH?\n\032!\020\n\032\0020\007\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\006\020\b\032\0020\tH?\b\032&\020\013\032\0020\007\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\006\020\f\032\002H\002H?\b?\006\002\020\r\032Q\020\016\032\0020\017\"\004\b\000\020\002*\b\022\004\022\002H\0020\00326\020\020\0322\022\023\022\0210\t?\006\f\b\022\022\b\b\023\022\004\b\b(\b\022\023\022\021H\002?\006\f\b\022\022\b\b\023\022\004\b\b(\f\022\004\022\0020\0170\021H?\b\032.\020\024\032\002H\002\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\006\020\b\032\0020\t2\006\020\025\032\002H\002H?\b?\006\002\020\026\0324\020\027\032\002H\002\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\006\020\b\032\0020\t2\f\020\025\032\b\022\004\022\002H\0020\030H?\b?\006\002\020\031\032\031\020\032\032\0020\007\"\004\b\000\020\002*\b\022\004\022\002H\0020\003H?\b\032\031\020\033\032\0020\007\"\004\b\000\020\002*\b\022\004\022\002H\0020\003H?\b\032\030\020\034\032\0020\035\"\004\b\000\020\002*\b\022\004\022\002H\0020\003H\007\032-\020\036\032\b\022\004\022\002H\0020\003\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\f\020\037\032\b\022\004\022\002H\0020\003H?\002\032&\020 \032\0020\017\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\f\020\037\032\b\022\004\022\002H\0020\003H\007\032-\020!\032\0020\007\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\006\020\b\032\0020\t2\006\020\f\032\002H\002H\007?\006\002\020\"\032.\020#\032\0020\017\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\006\020\b\032\0020\t2\006\020\f\032\002H\002H?\n?\006\002\020$\032\036\020%\032\b\022\004\022\002H\0020&\"\004\b\000\020\002*\b\022\004\022\002H\0020\003H\007\"\"\020\000\032\0020\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\0038?\002?\006\006\032\004\b\004\020\005?\006'"}, d2={"size", "", "T", "Landroid/util/LongSparseArray;", "getSize", "(Landroid/util/LongSparseArray;)I", "contains", "", "key", "", "containsKey", "containsValue", "value", "(Landroid/util/LongSparseArray;Ljava/lang/Object;)Z", "forEach", "", "action", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "getOrDefault", "defaultValue", "(Landroid/util/LongSparseArray;JLjava/lang/Object;)Ljava/lang/Object;", "getOrElse", "Lkotlin/Function0;", "(Landroid/util/LongSparseArray;JLkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "isEmpty", "isNotEmpty", "keyIterator", "Lkotlin/collections/LongIterator;", "plus", "other", "putAll", "remove", "(Landroid/util/LongSparseArray;JLjava/lang/Object;)Z", "set", "(Landroid/util/LongSparseArray;JLjava/lang/Object;)V", "valueIterator", "", "core-ktx_release"}, k=2, mv={1, 1, 15})
public final class LongSparseArrayKt
{
  public static final boolean contains(LongSparseArray paramLongSparseArray, long paramLong)
  {
    Intrinsics.checkParameterIsNotNull(paramLongSparseArray, "$this$contains");
    return paramLongSparseArray.indexOfKey(paramLong) >= 0;
  }
  
  public static final boolean containsKey(LongSparseArray paramLongSparseArray, long paramLong)
  {
    Intrinsics.checkParameterIsNotNull(paramLongSparseArray, "$this$containsKey");
    return paramLongSparseArray.indexOfKey(paramLong) >= 0;
  }
  
  public static final boolean containsValue(LongSparseArray paramLongSparseArray, Object paramObject)
  {
    Intrinsics.checkParameterIsNotNull(paramLongSparseArray, "$this$containsValue");
    return paramLongSparseArray.indexOfValue(paramObject) != -1;
  }
  
  public static final void forEach(LongSparseArray paramLongSparseArray, Function2 paramFunction2)
  {
    Intrinsics.checkParameterIsNotNull(paramLongSparseArray, "$this$forEach");
    Intrinsics.checkParameterIsNotNull(paramFunction2, "action");
    int j = paramLongSparseArray.size();
    int i = 0;
    while (i < j)
    {
      paramFunction2.invoke(Long.valueOf(paramLongSparseArray.keyAt(i)), paramLongSparseArray.valueAt(i));
      i += 1;
    }
  }
  
  public static final Object getOrDefault(LongSparseArray paramLongSparseArray, long paramLong, Object paramObject)
  {
    Intrinsics.checkParameterIsNotNull(paramLongSparseArray, "$this$getOrDefault");
    paramLongSparseArray = paramLongSparseArray.get(paramLong);
    if (paramLongSparseArray != null) {
      return paramLongSparseArray;
    }
    return paramObject;
  }
  
  public static final Object getOrElse(LongSparseArray paramLongSparseArray, long paramLong, Function0 paramFunction0)
  {
    Intrinsics.checkParameterIsNotNull(paramLongSparseArray, "$this$getOrElse");
    Intrinsics.checkParameterIsNotNull(paramFunction0, "defaultValue");
    paramLongSparseArray = paramLongSparseArray.get(paramLong);
    if (paramLongSparseArray != null) {
      return paramLongSparseArray;
    }
    return paramFunction0.invoke();
  }
  
  public static final int getSize(LongSparseArray paramLongSparseArray)
  {
    Intrinsics.checkParameterIsNotNull(paramLongSparseArray, "$this$size");
    return paramLongSparseArray.size();
  }
  
  public static final boolean isEmpty(LongSparseArray paramLongSparseArray)
  {
    Intrinsics.checkParameterIsNotNull(paramLongSparseArray, "$this$isEmpty");
    return paramLongSparseArray.size() == 0;
  }
  
  public static final boolean isNotEmpty(LongSparseArray paramLongSparseArray)
  {
    Intrinsics.checkParameterIsNotNull(paramLongSparseArray, "$this$isNotEmpty");
    return paramLongSparseArray.size() != 0;
  }
  
  public static final LongIterator keyIterator(LongSparseArray paramLongSparseArray)
  {
    Intrinsics.checkParameterIsNotNull(paramLongSparseArray, "$this$keyIterator");
    (LongIterator)new LongIterator()
    {
      private int index;
      
      public final int getIndex()
      {
        return index;
      }
      
      public boolean hasNext()
      {
        return index < size();
      }
      
      public long nextLong()
      {
        LongSparseArray localLongSparseArray = LongSparseArrayKt.this;
        int i = index;
        index = (i + 1);
        return localLongSparseArray.keyAt(i);
      }
      
      public final void setIndex(int paramAnonymousInt)
      {
        index = paramAnonymousInt;
      }
    };
  }
  
  public static final LongSparseArray plus(LongSparseArray paramLongSparseArray1, LongSparseArray paramLongSparseArray2)
  {
    Intrinsics.checkParameterIsNotNull(paramLongSparseArray1, "$this$plus");
    Intrinsics.checkParameterIsNotNull(paramLongSparseArray2, "other");
    LongSparseArray localLongSparseArray = new LongSparseArray(paramLongSparseArray1.size() + paramLongSparseArray2.size());
    putAll(localLongSparseArray, paramLongSparseArray1);
    putAll(localLongSparseArray, paramLongSparseArray2);
    return localLongSparseArray;
  }
  
  public static final void putAll(LongSparseArray paramLongSparseArray1, LongSparseArray paramLongSparseArray2)
  {
    Intrinsics.checkParameterIsNotNull(paramLongSparseArray1, "$this$putAll");
    Intrinsics.checkParameterIsNotNull(paramLongSparseArray2, "other");
    int j = paramLongSparseArray2.size();
    int i = 0;
    while (i < j)
    {
      paramLongSparseArray1.put(paramLongSparseArray2.keyAt(i), paramLongSparseArray2.valueAt(i));
      i += 1;
    }
  }
  
  public static final boolean remove(LongSparseArray paramLongSparseArray, long paramLong, Object paramObject)
  {
    Intrinsics.checkParameterIsNotNull(paramLongSparseArray, "$this$remove");
    int i = paramLongSparseArray.indexOfKey(paramLong);
    if ((i != -1) && (Intrinsics.areEqual(paramObject, paramLongSparseArray.valueAt(i))))
    {
      paramLongSparseArray.removeAt(i);
      return true;
    }
    return false;
  }
  
  public static final void replace(LongSparseArray paramLongSparseArray, long paramLong, Object paramObject)
  {
    Intrinsics.checkParameterIsNotNull(paramLongSparseArray, "$this$set");
    paramLongSparseArray.put(paramLong, paramObject);
  }
  
  public static final Iterator valueIterator(LongSparseArray paramLongSparseArray)
  {
    Intrinsics.checkParameterIsNotNull(paramLongSparseArray, "$this$valueIterator");
    (Iterator)new Iterator()
    {
      private int index;
      
      public final int getIndex()
      {
        return index;
      }
      
      public boolean hasNext()
      {
        return index < size();
      }
      
      public Object next()
      {
        LongSparseArray localLongSparseArray = LongSparseArrayKt.this;
        int i = index;
        index = (i + 1);
        return localLongSparseArray.valueAt(i);
      }
      
      public void remove()
      {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
      }
      
      public final void setIndex(int paramAnonymousInt)
      {
        index = paramAnonymousInt;
      }
    };
  }
}
