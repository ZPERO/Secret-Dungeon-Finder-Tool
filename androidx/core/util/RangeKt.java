package androidx.core.util;

import android.util.Range;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.ClosedRange;
import kotlin.ranges.ClosedRange.DefaultImpls;

@Metadata(bv={1, 0, 3}, d1={"\000\030\n\000\n\002\030\002\n\000\n\002\020\017\n\002\b\b\n\002\030\002\n\002\b\002\0327\020\000\032\b\022\004\022\002H\0020\001\"\016\b\000\020\002*\b\022\004\022\002H\0020\003*\b\022\004\022\002H\0020\0012\f\020\004\032\b\022\004\022\002H\0020\001H?\f\0326\020\005\032\b\022\004\022\002H\0020\001\"\016\b\000\020\002*\b\022\004\022\002H\0020\003*\b\022\004\022\002H\0020\0012\006\020\006\032\002H\002H?\n?\006\002\020\007\0327\020\005\032\b\022\004\022\002H\0020\001\"\016\b\000\020\002*\b\022\004\022\002H\0020\003*\b\022\004\022\002H\0020\0012\f\020\004\032\b\022\004\022\002H\0020\001H?\n\0320\020\b\032\b\022\004\022\002H\0020\001\"\016\b\000\020\002*\b\022\004\022\002H\0020\003*\002H\0022\006\020\t\032\002H\002H?\f?\006\002\020\n\032(\020\013\032\b\022\004\022\002H\0020\f\"\016\b\000\020\002*\b\022\004\022\002H\0020\003*\b\022\004\022\002H\0020\001H\007\032(\020\r\032\b\022\004\022\002H\0020\001\"\016\b\000\020\002*\b\022\004\022\002H\0020\003*\b\022\004\022\002H\0020\fH\007?\006\016"}, d2={"and", "Landroid/util/Range;", "T", "", "other", "plus", "value", "(Landroid/util/Range;Ljava/lang/Comparable;)Landroid/util/Range;", "rangeTo", "that", "(Ljava/lang/Comparable;Ljava/lang/Comparable;)Landroid/util/Range;", "toClosedRange", "Lkotlin/ranges/ClosedRange;", "toRange", "core-ktx_release"}, k=2, mv={1, 1, 15})
public final class RangeKt
{
  public static final Range intersect(Range paramRange1, Range paramRange2)
  {
    Intrinsics.checkParameterIsNotNull(paramRange1, "$this$and");
    Intrinsics.checkParameterIsNotNull(paramRange2, "other");
    paramRange1 = paramRange1.intersect(paramRange2);
    Intrinsics.checkExpressionValueIsNotNull(paramRange1, "intersect(other)");
    return paramRange1;
  }
  
  public static final Range plus(Range paramRange1, Range paramRange2)
  {
    Intrinsics.checkParameterIsNotNull(paramRange1, "$this$plus");
    Intrinsics.checkParameterIsNotNull(paramRange2, "other");
    paramRange1 = paramRange1.extend(paramRange2);
    Intrinsics.checkExpressionValueIsNotNull(paramRange1, "extend(other)");
    return paramRange1;
  }
  
  public static final Range plus(Range paramRange, Comparable paramComparable)
  {
    Intrinsics.checkParameterIsNotNull(paramRange, "$this$plus");
    Intrinsics.checkParameterIsNotNull(paramComparable, "value");
    paramRange = paramRange.extend(paramComparable);
    Intrinsics.checkExpressionValueIsNotNull(paramRange, "extend(value)");
    return paramRange;
  }
  
  public static final Range rangeTo(Comparable paramComparable1, Comparable paramComparable2)
  {
    Intrinsics.checkParameterIsNotNull(paramComparable1, "$this$rangeTo");
    Intrinsics.checkParameterIsNotNull(paramComparable2, "that");
    return new Range(paramComparable1, paramComparable2);
  }
  
  public static final ClosedRange toClosedRange(Range paramRange)
  {
    Intrinsics.checkParameterIsNotNull(paramRange, "$this$toClosedRange");
    (ClosedRange)new ClosedRange()
    {
      public boolean contains(Comparable paramAnonymousComparable)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousComparable, "value");
        return ClosedRange.DefaultImpls.contains(this, paramAnonymousComparable);
      }
      
      public Comparable getEndInclusive()
      {
        return getUpper();
      }
      
      public Comparable getStart()
      {
        return getLower();
      }
      
      public boolean isEmpty()
      {
        return ClosedRange.DefaultImpls.isEmpty(this);
      }
    };
  }
  
  public static final Range toRange(ClosedRange paramClosedRange)
  {
    Intrinsics.checkParameterIsNotNull(paramClosedRange, "$this$toRange");
    return new Range(paramClosedRange.getStart(), paramClosedRange.getEndInclusive());
  }
}
