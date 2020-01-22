package kotlin;

import java.io.Serializable;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000,\n\002\030\002\n\002\b\003\n\002\030\002\n\002\030\002\n\002\b\017\n\002\020\013\n\000\n\002\020\000\n\000\n\002\020\b\n\000\n\002\020\016\n\000\b?\b\030\000*\006\b\000\020\001 \001*\006\b\001\020\002 \001*\006\b\002\020\003 \0012\0060\004j\002`\005B\035\022\006\020\006\032\0028\000\022\006\020\007\032\0028\001\022\006\020\b\032\0028\002?\006\002\020\tJ\016\020\017\032\0028\000H?\003?\006\002\020\013J\016\020\020\032\0028\001H?\003?\006\002\020\013J\016\020\021\032\0028\002H?\003?\006\002\020\013J>\020\022\032\024\022\004\022\0028\000\022\004\022\0028\001\022\004\022\0028\0020\0002\b\b\002\020\006\032\0028\0002\b\b\002\020\007\032\0028\0012\b\b\002\020\b\032\0028\002H?\001?\006\002\020\023J\023\020\024\032\0020\0252\b\020\026\032\004\030\0010\027H?\003J\t\020\030\032\0020\031H?\001J\b\020\032\032\0020\033H\026R\023\020\006\032\0028\000?\006\n\n\002\020\f\032\004\b\n\020\013R\023\020\007\032\0028\001?\006\n\n\002\020\f\032\004\b\r\020\013R\023\020\b\032\0028\002?\006\n\n\002\020\f\032\004\b\016\020\013?\006\034"}, d2={"Lkotlin/Triple;", "A", "B", "C", "Ljava/io/Serializable;", "Lkotlin/io/Serializable;", "first", "second", "third", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V", "getFirst", "()Ljava/lang/Object;", "Ljava/lang/Object;", "getSecond", "getThird", "component1", "component2", "component3", "copy", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Triple;", "equals", "", "other", "", "hashCode", "", "toString", "", "kotlin-stdlib"}, k=1, mv={1, 1, 15})
public final class Triple<A, B, C>
  implements Serializable
{
  private final A first;
  private final B second;
  private final C third;
  
  public Triple(Object paramObject1, Object paramObject2, Object paramObject3)
  {
    first = paramObject1;
    second = paramObject2;
    third = paramObject3;
  }
  
  public final Object component1()
  {
    return first;
  }
  
  public final Object component2()
  {
    return second;
  }
  
  public final Object component3()
  {
    return third;
  }
  
  public final Triple copy(Object paramObject1, Object paramObject2, Object paramObject3)
  {
    return new Triple(paramObject1, paramObject2, paramObject3);
  }
  
  public boolean equals(Object paramObject)
  {
    if (this != paramObject) {
      if ((paramObject instanceof Triple))
      {
        paramObject = (Triple)paramObject;
        if ((Intrinsics.areEqual(first, first)) && (Intrinsics.areEqual(second, second)) && (Intrinsics.areEqual(third, third))) {}
      }
      else
      {
        return false;
      }
    }
    return true;
  }
  
  public final Object getFirst()
  {
    return first;
  }
  
  public final Object getSecond()
  {
    return second;
  }
  
  public final Object getThird()
  {
    return third;
  }
  
  public int hashCode()
  {
    Object localObject = first;
    int k = 0;
    int i;
    if (localObject != null) {
      i = localObject.hashCode();
    } else {
      i = 0;
    }
    localObject = second;
    int j;
    if (localObject != null) {
      j = localObject.hashCode();
    } else {
      j = 0;
    }
    localObject = third;
    if (localObject != null) {
      k = localObject.hashCode();
    }
    return (i * 31 + j) * 31 + k;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append('(');
    localStringBuilder.append(first);
    localStringBuilder.append(", ");
    localStringBuilder.append(second);
    localStringBuilder.append(", ");
    localStringBuilder.append(third);
    localStringBuilder.append(')');
    return localStringBuilder.toString();
  }
}