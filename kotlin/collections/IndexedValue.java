package kotlin.collections;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000\"\n\002\030\002\n\000\n\002\020\000\n\000\n\002\020\b\n\002\b\f\n\002\020\013\n\002\b\003\n\002\020\016\n\000\b?\b\030\000*\006\b\000\020\001 \0012\0020\002B\025\022\006\020\003\032\0020\004\022\006\020\005\032\0028\000?\006\002\020\006J\t\020\f\032\0020\004H?\003J\016\020\r\032\0028\000H?\003?\006\002\020\nJ(\020\016\032\b\022\004\022\0028\0000\0002\b\b\002\020\003\032\0020\0042\b\b\002\020\005\032\0028\000H?\001?\006\002\020\017J\023\020\020\032\0020\0212\b\020\022\032\004\030\0010\002H?\003J\t\020\023\032\0020\004H?\001J\t\020\024\032\0020\025H?\001R\021\020\003\032\0020\004?\006\b\n\000\032\004\b\007\020\bR\023\020\005\032\0028\000?\006\n\n\002\020\013\032\004\b\t\020\n?\006\026"}, d2={"Lkotlin/collections/IndexedValue;", "T", "", "index", "", "value", "(ILjava/lang/Object;)V", "getIndex", "()I", "getValue", "()Ljava/lang/Object;", "Ljava/lang/Object;", "component1", "component2", "copy", "(ILjava/lang/Object;)Lkotlin/collections/IndexedValue;", "equals", "", "other", "hashCode", "toString", "", "kotlin-stdlib"}, k=1, mv={1, 1, 15})
public final class IndexedValue<T>
{
  private final int index;
  private final T value;
  
  public IndexedValue(int paramInt, Object paramObject)
  {
    index = paramInt;
    value = paramObject;
  }
  
  public final int component1()
  {
    return index;
  }
  
  public final Object component2()
  {
    return value;
  }
  
  public final IndexedValue copy(int paramInt, Object paramObject)
  {
    return new IndexedValue(paramInt, paramObject);
  }
  
  public boolean equals(Object paramObject)
  {
    if (this != paramObject)
    {
      if ((paramObject instanceof IndexedValue))
      {
        paramObject = (IndexedValue)paramObject;
        int i;
        if (index == index) {
          i = 1;
        } else {
          i = 0;
        }
        if ((i != 0) && (Intrinsics.areEqual(value, value))) {
          return true;
        }
      }
      else
      {
        return false;
      }
    }
    else {
      return true;
    }
    return false;
  }
  
  public final int getIndex()
  {
    return index;
  }
  
  public final Object getValue()
  {
    return value;
  }
  
  public int hashCode()
  {
    int j = index;
    Object localObject = value;
    int i;
    if (localObject != null) {
      i = localObject.hashCode();
    } else {
      i = 0;
    }
    return j * 31 + i;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("IndexedValue(index=");
    localStringBuilder.append(index);
    localStringBuilder.append(", value=");
    localStringBuilder.append(value);
    localStringBuilder.append(")");
    return localStringBuilder.toString();
  }
}
