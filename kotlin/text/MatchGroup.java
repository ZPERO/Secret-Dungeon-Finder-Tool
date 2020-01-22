package kotlin.text;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;

@Metadata(bv={1, 0, 3}, d1={"\000(\n\002\030\002\n\002\020\000\n\000\n\002\020\016\n\000\n\002\030\002\n\002\b\t\n\002\020\013\n\002\b\002\n\002\020\b\n\002\b\002\b?\b\030\0002\0020\001B\025\022\006\020\002\032\0020\003\022\006\020\004\032\0020\005?\006\002\020\006J\t\020\013\032\0020\003H?\003J\t\020\f\032\0020\005H?\003J\035\020\r\032\0020\0002\b\b\002\020\002\032\0020\0032\b\b\002\020\004\032\0020\005H?\001J\023\020\016\032\0020\0172\b\020\020\032\004\030\0010\001H?\003J\t\020\021\032\0020\022H?\001J\t\020\023\032\0020\003H?\001R\021\020\004\032\0020\005?\006\b\n\000\032\004\b\007\020\bR\021\020\002\032\0020\003?\006\b\n\000\032\004\b\t\020\n?\006\024"}, d2={"Lkotlin/text/MatchGroup;", "", "value", "", "range", "Lkotlin/ranges/IntRange;", "(Ljava/lang/String;Lkotlin/ranges/IntRange;)V", "getRange", "()Lkotlin/ranges/IntRange;", "getValue", "()Ljava/lang/String;", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "kotlin-stdlib"}, k=1, mv={1, 1, 15})
public final class MatchGroup
{
  private final IntRange range;
  private final String value;
  
  public MatchGroup(String paramString, IntRange paramIntRange)
  {
    value = paramString;
    range = paramIntRange;
  }
  
  public final String component1()
  {
    return value;
  }
  
  public final IntRange component2()
  {
    return range;
  }
  
  public final MatchGroup copy(String paramString, IntRange paramIntRange)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "value");
    Intrinsics.checkParameterIsNotNull(paramIntRange, "range");
    return new MatchGroup(paramString, paramIntRange);
  }
  
  public boolean equals(Object paramObject)
  {
    if (this != paramObject) {
      if ((paramObject instanceof MatchGroup))
      {
        paramObject = (MatchGroup)paramObject;
        if ((Intrinsics.areEqual(value, value)) && (Intrinsics.areEqual(range, range))) {}
      }
      else
      {
        return false;
      }
    }
    return true;
  }
  
  public final IntRange getRange()
  {
    return range;
  }
  
  public final String getValue()
  {
    return value;
  }
  
  public int hashCode()
  {
    Object localObject = value;
    int j = 0;
    int i;
    if (localObject != null) {
      i = localObject.hashCode();
    } else {
      i = 0;
    }
    localObject = range;
    if (localObject != null) {
      j = localObject.hashCode();
    }
    return i * 31 + j;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("MatchGroup(value=");
    localStringBuilder.append(value);
    localStringBuilder.append(", range=");
    localStringBuilder.append(range);
    localStringBuilder.append(")");
    return localStringBuilder.toString();
  }
}
