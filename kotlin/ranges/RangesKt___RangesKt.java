package kotlin.ranges;

import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import kotlin.random.RandomKt;

@Metadata(bv={1, 0, 3}, d1={"\000n\n\002\b\002\n\002\020\017\n\002\b\002\n\002\020\005\n\002\020\006\n\002\020\007\n\002\020\b\n\002\020\t\n\002\020\n\n\002\b\005\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\013\n\002\030\002\n\000\n\002\020\f\n\002\b\b\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\026\032'\020\000\032\002H\001\"\016\b\000\020\001*\b\022\004\022\002H\0010\002*\002H\0012\006\020\003\032\002H\001?\006\002\020\004\032\022\020\000\032\0020\005*\0020\0052\006\020\003\032\0020\005\032\022\020\000\032\0020\006*\0020\0062\006\020\003\032\0020\006\032\022\020\000\032\0020\007*\0020\0072\006\020\003\032\0020\007\032\022\020\000\032\0020\b*\0020\b2\006\020\003\032\0020\b\032\022\020\000\032\0020\t*\0020\t2\006\020\003\032\0020\t\032\022\020\000\032\0020\n*\0020\n2\006\020\003\032\0020\n\032'\020\013\032\002H\001\"\016\b\000\020\001*\b\022\004\022\002H\0010\002*\002H\0012\006\020\f\032\002H\001?\006\002\020\004\032\022\020\013\032\0020\005*\0020\0052\006\020\f\032\0020\005\032\022\020\013\032\0020\006*\0020\0062\006\020\f\032\0020\006\032\022\020\013\032\0020\007*\0020\0072\006\020\f\032\0020\007\032\022\020\013\032\0020\b*\0020\b2\006\020\f\032\0020\b\032\022\020\013\032\0020\t*\0020\t2\006\020\f\032\0020\t\032\022\020\013\032\0020\n*\0020\n2\006\020\f\032\0020\n\0323\020\r\032\002H\001\"\016\b\000\020\001*\b\022\004\022\002H\0010\002*\002H\0012\b\020\003\032\004\030\001H\0012\b\020\f\032\004\030\001H\001?\006\002\020\016\032/\020\r\032\002H\001\"\016\b\000\020\001*\b\022\004\022\002H\0010\002*\002H\0012\f\020\017\032\b\022\004\022\002H\0010\020H\007?\006\002\020\021\032-\020\r\032\002H\001\"\016\b\000\020\001*\b\022\004\022\002H\0010\002*\002H\0012\f\020\017\032\b\022\004\022\002H\0010\022?\006\002\020\023\032\032\020\r\032\0020\005*\0020\0052\006\020\003\032\0020\0052\006\020\f\032\0020\005\032\032\020\r\032\0020\006*\0020\0062\006\020\003\032\0020\0062\006\020\f\032\0020\006\032\032\020\r\032\0020\007*\0020\0072\006\020\003\032\0020\0072\006\020\f\032\0020\007\032\032\020\r\032\0020\b*\0020\b2\006\020\003\032\0020\b2\006\020\f\032\0020\b\032\030\020\r\032\0020\b*\0020\b2\f\020\017\032\b\022\004\022\0020\b0\022\032\032\020\r\032\0020\t*\0020\t2\006\020\003\032\0020\t2\006\020\f\032\0020\t\032\030\020\r\032\0020\t*\0020\t2\f\020\017\032\b\022\004\022\0020\t0\022\032\032\020\r\032\0020\n*\0020\n2\006\020\003\032\0020\n2\006\020\f\032\0020\n\032\034\020\024\032\0020\025*\0020\0262\b\020\027\032\004\030\0010\030H?\n?\006\002\020\031\032 \020\024\032\0020\025*\b\022\004\022\0020\0050\0222\006\020\032\032\0020\006H?\002?\006\002\b\033\032 \020\024\032\0020\025*\b\022\004\022\0020\0050\0222\006\020\032\032\0020\007H?\002?\006\002\b\033\032 \020\024\032\0020\025*\b\022\004\022\0020\0050\0222\006\020\032\032\0020\bH?\002?\006\002\b\033\032 \020\024\032\0020\025*\b\022\004\022\0020\0050\0222\006\020\032\032\0020\tH?\002?\006\002\b\033\032 \020\024\032\0020\025*\b\022\004\022\0020\0050\0222\006\020\032\032\0020\nH?\002?\006\002\b\033\032 \020\024\032\0020\025*\b\022\004\022\0020\0060\0222\006\020\032\032\0020\005H?\002?\006\002\b\034\032 \020\024\032\0020\025*\b\022\004\022\0020\0060\0222\006\020\032\032\0020\007H?\002?\006\002\b\034\032 \020\024\032\0020\025*\b\022\004\022\0020\0060\0222\006\020\032\032\0020\bH?\002?\006\002\b\034\032 \020\024\032\0020\025*\b\022\004\022\0020\0060\0222\006\020\032\032\0020\tH?\002?\006\002\b\034\032 \020\024\032\0020\025*\b\022\004\022\0020\0060\0222\006\020\032\032\0020\nH?\002?\006\002\b\034\032 \020\024\032\0020\025*\b\022\004\022\0020\0070\0222\006\020\032\032\0020\005H?\002?\006\002\b\035\032 \020\024\032\0020\025*\b\022\004\022\0020\0070\0222\006\020\032\032\0020\006H?\002?\006\002\b\035\032 \020\024\032\0020\025*\b\022\004\022\0020\0070\0222\006\020\032\032\0020\bH?\002?\006\002\b\035\032 \020\024\032\0020\025*\b\022\004\022\0020\0070\0222\006\020\032\032\0020\tH?\002?\006\002\b\035\032 \020\024\032\0020\025*\b\022\004\022\0020\0070\0222\006\020\032\032\0020\nH?\002?\006\002\b\035\032 \020\024\032\0020\025*\b\022\004\022\0020\b0\0222\006\020\032\032\0020\005H?\002?\006\002\b\036\032 \020\024\032\0020\025*\b\022\004\022\0020\b0\0222\006\020\032\032\0020\006H?\002?\006\002\b\036\032 \020\024\032\0020\025*\b\022\004\022\0020\b0\0222\006\020\032\032\0020\007H?\002?\006\002\b\036\032 \020\024\032\0020\025*\b\022\004\022\0020\b0\0222\006\020\032\032\0020\tH?\002?\006\002\b\036\032 \020\024\032\0020\025*\b\022\004\022\0020\b0\0222\006\020\032\032\0020\nH?\002?\006\002\b\036\032 \020\024\032\0020\025*\b\022\004\022\0020\t0\0222\006\020\032\032\0020\005H?\002?\006\002\b\037\032 \020\024\032\0020\025*\b\022\004\022\0020\t0\0222\006\020\032\032\0020\006H?\002?\006\002\b\037\032 \020\024\032\0020\025*\b\022\004\022\0020\t0\0222\006\020\032\032\0020\007H?\002?\006\002\b\037\032 \020\024\032\0020\025*\b\022\004\022\0020\t0\0222\006\020\032\032\0020\bH?\002?\006\002\b\037\032 \020\024\032\0020\025*\b\022\004\022\0020\t0\0222\006\020\032\032\0020\nH?\002?\006\002\b\037\032 \020\024\032\0020\025*\b\022\004\022\0020\n0\0222\006\020\032\032\0020\005H?\002?\006\002\b \032 \020\024\032\0020\025*\b\022\004\022\0020\n0\0222\006\020\032\032\0020\006H?\002?\006\002\b \032 \020\024\032\0020\025*\b\022\004\022\0020\n0\0222\006\020\032\032\0020\007H?\002?\006\002\b \032 \020\024\032\0020\025*\b\022\004\022\0020\n0\0222\006\020\032\032\0020\bH?\002?\006\002\b \032 \020\024\032\0020\025*\b\022\004\022\0020\n0\0222\006\020\032\032\0020\tH?\002?\006\002\b \032\034\020\024\032\0020\025*\0020!2\b\020\027\032\004\030\0010\bH?\n?\006\002\020\"\032\034\020\024\032\0020\025*\0020#2\b\020\027\032\004\030\0010\tH?\n?\006\002\020$\032\025\020%\032\0020&*\0020\0052\006\020'\032\0020\005H?\004\032\025\020%\032\0020&*\0020\0052\006\020'\032\0020\bH?\004\032\025\020%\032\0020(*\0020\0052\006\020'\032\0020\tH?\004\032\025\020%\032\0020&*\0020\0052\006\020'\032\0020\nH?\004\032\025\020%\032\0020)*\0020\0302\006\020'\032\0020\030H?\004\032\025\020%\032\0020&*\0020\b2\006\020'\032\0020\005H?\004\032\025\020%\032\0020&*\0020\b2\006\020'\032\0020\bH?\004\032\025\020%\032\0020(*\0020\b2\006\020'\032\0020\tH?\004\032\025\020%\032\0020&*\0020\b2\006\020'\032\0020\nH?\004\032\025\020%\032\0020(*\0020\t2\006\020'\032\0020\005H?\004\032\025\020%\032\0020(*\0020\t2\006\020'\032\0020\bH?\004\032\025\020%\032\0020(*\0020\t2\006\020'\032\0020\tH?\004\032\025\020%\032\0020(*\0020\t2\006\020'\032\0020\nH?\004\032\025\020%\032\0020&*\0020\n2\006\020'\032\0020\005H?\004\032\025\020%\032\0020&*\0020\n2\006\020'\032\0020\bH?\004\032\025\020%\032\0020(*\0020\n2\006\020'\032\0020\tH?\004\032\025\020%\032\0020&*\0020\n2\006\020'\032\0020\nH?\004\032\r\020*\032\0020\030*\0020\026H?\b\032\024\020*\032\0020\030*\0020\0262\006\020*\032\0020+H\007\032\r\020*\032\0020\b*\0020!H?\b\032\024\020*\032\0020\b*\0020!2\006\020*\032\0020+H\007\032\r\020*\032\0020\t*\0020#H?\b\032\024\020*\032\0020\t*\0020#2\006\020*\032\0020+H\007\032\n\020,\032\0020)*\0020)\032\n\020,\032\0020&*\0020&\032\n\020,\032\0020(*\0020(\032\025\020-\032\0020)*\0020)2\006\020-\032\0020\bH?\004\032\025\020-\032\0020&*\0020&2\006\020-\032\0020\bH?\004\032\025\020-\032\0020(*\0020(2\006\020-\032\0020\tH?\004\032\023\020.\032\004\030\0010\005*\0020\006H\000?\006\002\020/\032\023\020.\032\004\030\0010\005*\0020\007H\000?\006\002\0200\032\023\020.\032\004\030\0010\005*\0020\bH\000?\006\002\0201\032\023\020.\032\004\030\0010\005*\0020\tH\000?\006\002\0202\032\023\020.\032\004\030\0010\005*\0020\nH\000?\006\002\0203\032\023\0204\032\004\030\0010\b*\0020\006H\000?\006\002\0205\032\023\0204\032\004\030\0010\b*\0020\007H\000?\006\002\0206\032\023\0204\032\004\030\0010\b*\0020\tH\000?\006\002\0207\032\023\0208\032\004\030\0010\t*\0020\006H\000?\006\002\0209\032\023\0208\032\004\030\0010\t*\0020\007H\000?\006\002\020:\032\023\020;\032\004\030\0010\n*\0020\006H\000?\006\002\020<\032\023\020;\032\004\030\0010\n*\0020\007H\000?\006\002\020=\032\023\020;\032\004\030\0010\n*\0020\bH\000?\006\002\020>\032\023\020;\032\004\030\0010\n*\0020\tH\000?\006\002\020?\032\025\020@\032\0020!*\0020\0052\006\020'\032\0020\005H?\004\032\025\020@\032\0020!*\0020\0052\006\020'\032\0020\bH?\004\032\025\020@\032\0020#*\0020\0052\006\020'\032\0020\tH?\004\032\025\020@\032\0020!*\0020\0052\006\020'\032\0020\nH?\004\032\025\020@\032\0020\026*\0020\0302\006\020'\032\0020\030H?\004\032\025\020@\032\0020!*\0020\b2\006\020'\032\0020\005H?\004\032\025\020@\032\0020!*\0020\b2\006\020'\032\0020\bH?\004\032\025\020@\032\0020#*\0020\b2\006\020'\032\0020\tH?\004\032\025\020@\032\0020!*\0020\b2\006\020'\032\0020\nH?\004\032\025\020@\032\0020#*\0020\t2\006\020'\032\0020\005H?\004\032\025\020@\032\0020#*\0020\t2\006\020'\032\0020\bH?\004\032\025\020@\032\0020#*\0020\t2\006\020'\032\0020\tH?\004\032\025\020@\032\0020#*\0020\t2\006\020'\032\0020\nH?\004\032\025\020@\032\0020!*\0020\n2\006\020'\032\0020\005H?\004\032\025\020@\032\0020!*\0020\n2\006\020'\032\0020\bH?\004\032\025\020@\032\0020#*\0020\n2\006\020'\032\0020\tH?\004\032\025\020@\032\0020!*\0020\n2\006\020'\032\0020\nH?\004?\006A"}, d2={"coerceAtLeast", "T", "", "minimumValue", "(Ljava/lang/Comparable;Ljava/lang/Comparable;)Ljava/lang/Comparable;", "", "", "", "", "", "", "coerceAtMost", "maximumValue", "coerceIn", "(Ljava/lang/Comparable;Ljava/lang/Comparable;Ljava/lang/Comparable;)Ljava/lang/Comparable;", "range", "Lkotlin/ranges/ClosedFloatingPointRange;", "(Ljava/lang/Comparable;Lkotlin/ranges/ClosedFloatingPointRange;)Ljava/lang/Comparable;", "Lkotlin/ranges/ClosedRange;", "(Ljava/lang/Comparable;Lkotlin/ranges/ClosedRange;)Ljava/lang/Comparable;", "contains", "", "Lkotlin/ranges/CharRange;", "element", "", "(Lkotlin/ranges/CharRange;Ljava/lang/Character;)Z", "value", "byteRangeContains", "doubleRangeContains", "floatRangeContains", "intRangeContains", "longRangeContains", "shortRangeContains", "Lkotlin/ranges/IntRange;", "(Lkotlin/ranges/IntRange;Ljava/lang/Integer;)Z", "Lkotlin/ranges/LongRange;", "(Lkotlin/ranges/LongRange;Ljava/lang/Long;)Z", "downTo", "Lkotlin/ranges/IntProgression;", "to", "Lkotlin/ranges/LongProgression;", "Lkotlin/ranges/CharProgression;", "random", "Lkotlin/random/Random;", "reversed", "step", "toByteExactOrNull", "(D)Ljava/lang/Byte;", "(F)Ljava/lang/Byte;", "(I)Ljava/lang/Byte;", "(J)Ljava/lang/Byte;", "(S)Ljava/lang/Byte;", "toIntExactOrNull", "(D)Ljava/lang/Integer;", "(F)Ljava/lang/Integer;", "(J)Ljava/lang/Integer;", "toLongExactOrNull", "(D)Ljava/lang/Long;", "(F)Ljava/lang/Long;", "toShortExactOrNull", "(D)Ljava/lang/Short;", "(F)Ljava/lang/Short;", "(I)Ljava/lang/Short;", "(J)Ljava/lang/Short;", "until", "kotlin-stdlib"}, k=5, mv={1, 1, 15}, xi=1, xs="kotlin/ranges/RangesKt")
class RangesKt___RangesKt
  extends RangesKt__RangesKt
{
  public RangesKt___RangesKt() {}
  
  public static final boolean byteRangeContains(ClosedRange paramClosedRange, double paramDouble)
  {
    Intrinsics.checkParameterIsNotNull(paramClosedRange, "$this$contains");
    Byte localByte = toByteExactOrNull(paramDouble);
    if (localByte != null) {
      return paramClosedRange.contains((Comparable)localByte);
    }
    return false;
  }
  
  public static final boolean byteRangeContains(ClosedRange paramClosedRange, float paramFloat)
  {
    Intrinsics.checkParameterIsNotNull(paramClosedRange, "$this$contains");
    Byte localByte = toByteExactOrNull(paramFloat);
    if (localByte != null) {
      return paramClosedRange.contains((Comparable)localByte);
    }
    return false;
  }
  
  public static final boolean byteRangeContains(ClosedRange paramClosedRange, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramClosedRange, "$this$contains");
    Byte localByte = toByteExactOrNull(paramInt);
    if (localByte != null) {
      return paramClosedRange.contains((Comparable)localByte);
    }
    return false;
  }
  
  public static final boolean byteRangeContains(ClosedRange paramClosedRange, long paramLong)
  {
    Intrinsics.checkParameterIsNotNull(paramClosedRange, "$this$contains");
    Byte localByte = toByteExactOrNull(paramLong);
    if (localByte != null) {
      return paramClosedRange.contains((Comparable)localByte);
    }
    return false;
  }
  
  public static final boolean byteRangeContains(ClosedRange paramClosedRange, short paramShort)
  {
    Intrinsics.checkParameterIsNotNull(paramClosedRange, "$this$contains");
    Byte localByte = toByteExactOrNull(paramShort);
    if (localByte != null) {
      return paramClosedRange.contains((Comparable)localByte);
    }
    return false;
  }
  
  public static final byte coerceAtLeast(byte paramByte1, byte paramByte2)
  {
    if (paramByte1 < paramByte2) {
      return paramByte2;
    }
    return paramByte1;
  }
  
  public static final double coerceAtLeast(double paramDouble1, double paramDouble2)
  {
    if (paramDouble1 < paramDouble2) {
      return paramDouble2;
    }
    return paramDouble1;
  }
  
  public static final float coerceAtLeast(float paramFloat1, float paramFloat2)
  {
    if (paramFloat1 < paramFloat2) {
      return paramFloat2;
    }
    return paramFloat1;
  }
  
  public static final int coerceAtLeast(int paramInt1, int paramInt2)
  {
    if (paramInt1 < paramInt2) {
      return paramInt2;
    }
    return paramInt1;
  }
  
  public static final long coerceAtLeast(long paramLong1, long paramLong2)
  {
    if (paramLong1 < paramLong2) {
      return paramLong2;
    }
    return paramLong1;
  }
  
  public static final Comparable coerceAtLeast(Comparable paramComparable1, Comparable paramComparable2)
  {
    Intrinsics.checkParameterIsNotNull(paramComparable1, "$this$coerceAtLeast");
    Intrinsics.checkParameterIsNotNull(paramComparable2, "minimumValue");
    if (paramComparable1.compareTo(paramComparable2) < 0) {
      return paramComparable2;
    }
    return paramComparable1;
  }
  
  public static final short coerceAtLeast(short paramShort1, short paramShort2)
  {
    if (paramShort1 < paramShort2) {
      return paramShort2;
    }
    return paramShort1;
  }
  
  public static final byte coerceAtMost(byte paramByte1, byte paramByte2)
  {
    if (paramByte1 > paramByte2) {
      return paramByte2;
    }
    return paramByte1;
  }
  
  public static final double coerceAtMost(double paramDouble1, double paramDouble2)
  {
    if (paramDouble1 > paramDouble2) {
      return paramDouble2;
    }
    return paramDouble1;
  }
  
  public static final float coerceAtMost(float paramFloat1, float paramFloat2)
  {
    if (paramFloat1 > paramFloat2) {
      return paramFloat2;
    }
    return paramFloat1;
  }
  
  public static final int coerceAtMost(int paramInt1, int paramInt2)
  {
    if (paramInt1 > paramInt2) {
      return paramInt2;
    }
    return paramInt1;
  }
  
  public static final long coerceAtMost(long paramLong1, long paramLong2)
  {
    if (paramLong1 > paramLong2) {
      return paramLong2;
    }
    return paramLong1;
  }
  
  public static final Comparable coerceAtMost(Comparable paramComparable1, Comparable paramComparable2)
  {
    Intrinsics.checkParameterIsNotNull(paramComparable1, "$this$coerceAtMost");
    Intrinsics.checkParameterIsNotNull(paramComparable2, "maximumValue");
    if (paramComparable1.compareTo(paramComparable2) > 0) {
      return paramComparable2;
    }
    return paramComparable1;
  }
  
  public static final short coerceAtMost(short paramShort1, short paramShort2)
  {
    if (paramShort1 > paramShort2) {
      return paramShort2;
    }
    return paramShort1;
  }
  
  public static final byte coerceIn(byte paramByte1, byte paramByte2, byte paramByte3)
  {
    if (paramByte2 <= paramByte3)
    {
      if (paramByte1 < paramByte2) {
        return paramByte2;
      }
      if (paramByte1 > paramByte3) {
        return paramByte3;
      }
      return paramByte1;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Cannot coerce value to an empty range: maximum ");
    localStringBuilder.append(paramByte3);
    localStringBuilder.append(" is less than minimum ");
    localStringBuilder.append(paramByte2);
    localStringBuilder.append('.');
    throw ((Throwable)new IllegalArgumentException(localStringBuilder.toString()));
  }
  
  public static final double coerceIn(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    if (paramDouble2 <= paramDouble3)
    {
      if (paramDouble1 < paramDouble2) {
        return paramDouble2;
      }
      if (paramDouble1 > paramDouble3) {
        return paramDouble3;
      }
      return paramDouble1;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Cannot coerce value to an empty range: maximum ");
    localStringBuilder.append(paramDouble3);
    localStringBuilder.append(" is less than minimum ");
    localStringBuilder.append(paramDouble2);
    localStringBuilder.append('.');
    throw ((Throwable)new IllegalArgumentException(localStringBuilder.toString()));
  }
  
  public static final float coerceIn(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    if (paramFloat2 <= paramFloat3)
    {
      if (paramFloat1 < paramFloat2) {
        return paramFloat2;
      }
      if (paramFloat1 > paramFloat3) {
        return paramFloat3;
      }
      return paramFloat1;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Cannot coerce value to an empty range: maximum ");
    localStringBuilder.append(paramFloat3);
    localStringBuilder.append(" is less than minimum ");
    localStringBuilder.append(paramFloat2);
    localStringBuilder.append('.');
    throw ((Throwable)new IllegalArgumentException(localStringBuilder.toString()));
  }
  
  public static final int coerceIn(int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramInt2 <= paramInt3)
    {
      if (paramInt1 < paramInt2) {
        return paramInt2;
      }
      if (paramInt1 > paramInt3) {
        return paramInt3;
      }
      return paramInt1;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Cannot coerce value to an empty range: maximum ");
    localStringBuilder.append(paramInt3);
    localStringBuilder.append(" is less than minimum ");
    localStringBuilder.append(paramInt2);
    localStringBuilder.append('.');
    throw ((Throwable)new IllegalArgumentException(localStringBuilder.toString()));
  }
  
  public static final int coerceIn(int paramInt, ClosedRange paramClosedRange)
  {
    Intrinsics.checkParameterIsNotNull(paramClosedRange, "range");
    if ((paramClosedRange instanceof ClosedFloatingPointRange)) {
      return ((Number)coerceIn((Comparable)Integer.valueOf(paramInt), (ClosedFloatingPointRange)paramClosedRange)).intValue();
    }
    if (!paramClosedRange.isEmpty())
    {
      if (paramInt < ((Number)paramClosedRange.getStart()).intValue()) {
        return ((Number)paramClosedRange.getStart()).intValue();
      }
      if (paramInt > ((Number)paramClosedRange.getEndInclusive()).intValue()) {
        return ((Number)paramClosedRange.getEndInclusive()).intValue();
      }
    }
    else
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Cannot coerce value to an empty range: ");
      localStringBuilder.append(paramClosedRange);
      localStringBuilder.append('.');
      throw ((Throwable)new IllegalArgumentException(localStringBuilder.toString()));
    }
    return paramInt;
  }
  
  public static final long coerceIn(long paramLong1, long paramLong2, long paramLong3)
  {
    if (paramLong2 <= paramLong3)
    {
      if (paramLong1 < paramLong2) {
        return paramLong2;
      }
      if (paramLong1 > paramLong3) {
        return paramLong3;
      }
      return paramLong1;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Cannot coerce value to an empty range: maximum ");
    localStringBuilder.append(paramLong3);
    localStringBuilder.append(" is less than minimum ");
    localStringBuilder.append(paramLong2);
    localStringBuilder.append('.');
    throw ((Throwable)new IllegalArgumentException(localStringBuilder.toString()));
  }
  
  public static final long coerceIn(long paramLong, ClosedRange paramClosedRange)
  {
    Intrinsics.checkParameterIsNotNull(paramClosedRange, "range");
    if ((paramClosedRange instanceof ClosedFloatingPointRange)) {
      return ((Number)coerceIn((Comparable)Long.valueOf(paramLong), (ClosedFloatingPointRange)paramClosedRange)).longValue();
    }
    if (!paramClosedRange.isEmpty())
    {
      if (paramLong < ((Number)paramClosedRange.getStart()).longValue()) {
        return ((Number)paramClosedRange.getStart()).longValue();
      }
      if (paramLong > ((Number)paramClosedRange.getEndInclusive()).longValue()) {
        return ((Number)paramClosedRange.getEndInclusive()).longValue();
      }
    }
    else
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Cannot coerce value to an empty range: ");
      localStringBuilder.append(paramClosedRange);
      localStringBuilder.append('.');
      throw ((Throwable)new IllegalArgumentException(localStringBuilder.toString()));
    }
    return paramLong;
  }
  
  public static final Comparable coerceIn(Comparable paramComparable1, Comparable paramComparable2, Comparable paramComparable3)
  {
    Intrinsics.checkParameterIsNotNull(paramComparable1, "$this$coerceIn");
    if ((paramComparable2 != null) && (paramComparable3 != null))
    {
      if (paramComparable2.compareTo(paramComparable3) <= 0)
      {
        if (paramComparable1.compareTo(paramComparable2) < 0) {
          return paramComparable2;
        }
        if (paramComparable1.compareTo(paramComparable3) > 0) {
          return paramComparable3;
        }
      }
      else
      {
        paramComparable1 = new StringBuilder();
        paramComparable1.append("Cannot coerce value to an empty range: maximum ");
        paramComparable1.append(paramComparable3);
        paramComparable1.append(" is less than minimum ");
        paramComparable1.append(paramComparable2);
        paramComparable1.append('.');
        throw ((Throwable)new IllegalArgumentException(paramComparable1.toString()));
      }
    }
    else
    {
      if ((paramComparable2 != null) && (paramComparable1.compareTo(paramComparable2) < 0)) {
        return paramComparable2;
      }
      if ((paramComparable3 != null) && (paramComparable1.compareTo(paramComparable3) > 0)) {
        return paramComparable3;
      }
    }
    return paramComparable1;
  }
  
  public static final Comparable coerceIn(Comparable paramComparable, ClosedFloatingPointRange paramClosedFloatingPointRange)
  {
    Intrinsics.checkParameterIsNotNull(paramComparable, "$this$coerceIn");
    Intrinsics.checkParameterIsNotNull(paramClosedFloatingPointRange, "range");
    if (!paramClosedFloatingPointRange.isEmpty())
    {
      if ((paramClosedFloatingPointRange.lessThanOrEquals(paramComparable, paramClosedFloatingPointRange.getStart())) && (!paramClosedFloatingPointRange.lessThanOrEquals(paramClosedFloatingPointRange.getStart(), paramComparable))) {
        return paramClosedFloatingPointRange.getStart();
      }
      if ((paramClosedFloatingPointRange.lessThanOrEquals(paramClosedFloatingPointRange.getEndInclusive(), paramComparable)) && (!paramClosedFloatingPointRange.lessThanOrEquals(paramComparable, paramClosedFloatingPointRange.getEndInclusive()))) {
        return paramClosedFloatingPointRange.getEndInclusive();
      }
    }
    else
    {
      paramComparable = new StringBuilder();
      paramComparable.append("Cannot coerce value to an empty range: ");
      paramComparable.append(paramClosedFloatingPointRange);
      paramComparable.append('.');
      throw ((Throwable)new IllegalArgumentException(paramComparable.toString()));
    }
    return paramComparable;
  }
  
  public static final Comparable coerceIn(Comparable paramComparable, ClosedRange paramClosedRange)
  {
    Intrinsics.checkParameterIsNotNull(paramComparable, "$this$coerceIn");
    Intrinsics.checkParameterIsNotNull(paramClosedRange, "range");
    if ((paramClosedRange instanceof ClosedFloatingPointRange)) {
      return coerceIn(paramComparable, (ClosedFloatingPointRange)paramClosedRange);
    }
    if (!paramClosedRange.isEmpty())
    {
      if (paramComparable.compareTo(paramClosedRange.getStart()) < 0) {
        return paramClosedRange.getStart();
      }
      if (paramComparable.compareTo(paramClosedRange.getEndInclusive()) > 0) {
        return paramClosedRange.getEndInclusive();
      }
    }
    else
    {
      paramComparable = new StringBuilder();
      paramComparable.append("Cannot coerce value to an empty range: ");
      paramComparable.append(paramClosedRange);
      paramComparable.append('.');
      throw ((Throwable)new IllegalArgumentException(paramComparable.toString()));
    }
    return paramComparable;
  }
  
  public static final short coerceIn(short paramShort1, short paramShort2, short paramShort3)
  {
    if (paramShort2 <= paramShort3)
    {
      if (paramShort1 < paramShort2) {
        return paramShort2;
      }
      if (paramShort1 > paramShort3) {
        return paramShort3;
      }
      return paramShort1;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Cannot coerce value to an empty range: maximum ");
    localStringBuilder.append(paramShort3);
    localStringBuilder.append(" is less than minimum ");
    localStringBuilder.append(paramShort2);
    localStringBuilder.append('.');
    throw ((Throwable)new IllegalArgumentException(localStringBuilder.toString()));
  }
  
  private static final boolean contains(CharRange paramCharRange, Character paramCharacter)
  {
    Intrinsics.checkParameterIsNotNull(paramCharRange, "$this$contains");
    return (paramCharacter != null) && (paramCharRange.contains(paramCharacter.charValue()));
  }
  
  private static final boolean contains(IntRange paramIntRange, Integer paramInteger)
  {
    Intrinsics.checkParameterIsNotNull(paramIntRange, "$this$contains");
    return (paramInteger != null) && (paramIntRange.contains(paramInteger.intValue()));
  }
  
  private static final boolean contains(LongRange paramLongRange, Long paramLong)
  {
    Intrinsics.checkParameterIsNotNull(paramLongRange, "$this$contains");
    return (paramLong != null) && (paramLongRange.contains(paramLong.longValue()));
  }
  
  public static final boolean doubleRangeContains(ClosedRange paramClosedRange, byte paramByte)
  {
    Intrinsics.checkParameterIsNotNull(paramClosedRange, "$this$contains");
    return paramClosedRange.contains((Comparable)Double.valueOf(paramByte));
  }
  
  public static final boolean doubleRangeContains(ClosedRange paramClosedRange, float paramFloat)
  {
    Intrinsics.checkParameterIsNotNull(paramClosedRange, "$this$contains");
    return paramClosedRange.contains((Comparable)Double.valueOf(paramFloat));
  }
  
  public static final boolean doubleRangeContains(ClosedRange paramClosedRange, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramClosedRange, "$this$contains");
    return paramClosedRange.contains((Comparable)Double.valueOf(paramInt));
  }
  
  public static final boolean doubleRangeContains(ClosedRange paramClosedRange, long paramLong)
  {
    Intrinsics.checkParameterIsNotNull(paramClosedRange, "$this$contains");
    return paramClosedRange.contains((Comparable)Double.valueOf(paramLong));
  }
  
  public static final boolean doubleRangeContains(ClosedRange paramClosedRange, short paramShort)
  {
    Intrinsics.checkParameterIsNotNull(paramClosedRange, "$this$contains");
    return paramClosedRange.contains((Comparable)Double.valueOf(paramShort));
  }
  
  public static final CharProgression downTo(char paramChar1, char paramChar2)
  {
    return CharProgression.Companion.fromClosedRange(paramChar1, paramChar2, -1);
  }
  
  public static final IntProgression downTo(byte paramByte1, byte paramByte2)
  {
    return IntProgression.Companion.fromClosedRange(paramByte1, paramByte2, -1);
  }
  
  public static final IntProgression downTo(byte paramByte, int paramInt)
  {
    return IntProgression.Companion.fromClosedRange(paramByte, paramInt, -1);
  }
  
  public static final IntProgression downTo(byte paramByte, short paramShort)
  {
    return IntProgression.Companion.fromClosedRange(paramByte, paramShort, -1);
  }
  
  public static final IntProgression downTo(int paramInt, byte paramByte)
  {
    return IntProgression.Companion.fromClosedRange(paramInt, paramByte, -1);
  }
  
  public static final IntProgression downTo(int paramInt1, int paramInt2)
  {
    return IntProgression.Companion.fromClosedRange(paramInt1, paramInt2, -1);
  }
  
  public static final IntProgression downTo(int paramInt, short paramShort)
  {
    return IntProgression.Companion.fromClosedRange(paramInt, paramShort, -1);
  }
  
  public static final IntProgression downTo(short paramShort, byte paramByte)
  {
    return IntProgression.Companion.fromClosedRange(paramShort, paramByte, -1);
  }
  
  public static final IntProgression downTo(short paramShort, int paramInt)
  {
    return IntProgression.Companion.fromClosedRange(paramShort, paramInt, -1);
  }
  
  public static final IntProgression downTo(short paramShort1, short paramShort2)
  {
    return IntProgression.Companion.fromClosedRange(paramShort1, paramShort2, -1);
  }
  
  public static final LongProgression downTo(byte paramByte, long paramLong)
  {
    return LongProgression.Companion.fromClosedRange(paramByte, paramLong, -1L);
  }
  
  public static final LongProgression downTo(int paramInt, long paramLong)
  {
    return LongProgression.Companion.fromClosedRange(paramInt, paramLong, -1L);
  }
  
  public static final LongProgression downTo(long paramLong, byte paramByte)
  {
    return LongProgression.Companion.fromClosedRange(paramLong, paramByte, -1L);
  }
  
  public static final LongProgression downTo(long paramLong, int paramInt)
  {
    return LongProgression.Companion.fromClosedRange(paramLong, paramInt, -1L);
  }
  
  public static final LongProgression downTo(long paramLong1, long paramLong2)
  {
    return LongProgression.Companion.fromClosedRange(paramLong1, paramLong2, -1L);
  }
  
  public static final LongProgression downTo(long paramLong, short paramShort)
  {
    return LongProgression.Companion.fromClosedRange(paramLong, paramShort, -1L);
  }
  
  public static final LongProgression downTo(short paramShort, long paramLong)
  {
    return LongProgression.Companion.fromClosedRange(paramShort, paramLong, -1L);
  }
  
  public static final boolean floatRangeContains(ClosedRange paramClosedRange, byte paramByte)
  {
    Intrinsics.checkParameterIsNotNull(paramClosedRange, "$this$contains");
    return paramClosedRange.contains((Comparable)Float.valueOf(paramByte));
  }
  
  public static final boolean floatRangeContains(ClosedRange paramClosedRange, double paramDouble)
  {
    Intrinsics.checkParameterIsNotNull(paramClosedRange, "$this$contains");
    return paramClosedRange.contains((Comparable)Float.valueOf((float)paramDouble));
  }
  
  public static final boolean floatRangeContains(ClosedRange paramClosedRange, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramClosedRange, "$this$contains");
    return paramClosedRange.contains((Comparable)Float.valueOf(paramInt));
  }
  
  public static final boolean floatRangeContains(ClosedRange paramClosedRange, long paramLong)
  {
    Intrinsics.checkParameterIsNotNull(paramClosedRange, "$this$contains");
    return paramClosedRange.contains((Comparable)Float.valueOf((float)paramLong));
  }
  
  public static final boolean floatRangeContains(ClosedRange paramClosedRange, short paramShort)
  {
    Intrinsics.checkParameterIsNotNull(paramClosedRange, "$this$contains");
    return paramClosedRange.contains((Comparable)Float.valueOf(paramShort));
  }
  
  public static final boolean intRangeContains(ClosedRange paramClosedRange, byte paramByte)
  {
    Intrinsics.checkParameterIsNotNull(paramClosedRange, "$this$contains");
    return paramClosedRange.contains((Comparable)Integer.valueOf(paramByte));
  }
  
  public static final boolean intRangeContains(ClosedRange paramClosedRange, double paramDouble)
  {
    Intrinsics.checkParameterIsNotNull(paramClosedRange, "$this$contains");
    Integer localInteger = toIntExactOrNull(paramDouble);
    if (localInteger != null) {
      return paramClosedRange.contains((Comparable)localInteger);
    }
    return false;
  }
  
  public static final boolean intRangeContains(ClosedRange paramClosedRange, float paramFloat)
  {
    Intrinsics.checkParameterIsNotNull(paramClosedRange, "$this$contains");
    Integer localInteger = toIntExactOrNull(paramFloat);
    if (localInteger != null) {
      return paramClosedRange.contains((Comparable)localInteger);
    }
    return false;
  }
  
  public static final boolean intRangeContains(ClosedRange paramClosedRange, long paramLong)
  {
    Intrinsics.checkParameterIsNotNull(paramClosedRange, "$this$contains");
    Integer localInteger = toIntExactOrNull(paramLong);
    if (localInteger != null) {
      return paramClosedRange.contains((Comparable)localInteger);
    }
    return false;
  }
  
  public static final boolean intRangeContains(ClosedRange paramClosedRange, short paramShort)
  {
    Intrinsics.checkParameterIsNotNull(paramClosedRange, "$this$contains");
    return paramClosedRange.contains((Comparable)Integer.valueOf(paramShort));
  }
  
  public static final boolean longRangeContains(ClosedRange paramClosedRange, byte paramByte)
  {
    Intrinsics.checkParameterIsNotNull(paramClosedRange, "$this$contains");
    return paramClosedRange.contains((Comparable)Long.valueOf(paramByte));
  }
  
  public static final boolean longRangeContains(ClosedRange paramClosedRange, double paramDouble)
  {
    Intrinsics.checkParameterIsNotNull(paramClosedRange, "$this$contains");
    Long localLong = toLongExactOrNull(paramDouble);
    if (localLong != null) {
      return paramClosedRange.contains((Comparable)localLong);
    }
    return false;
  }
  
  public static final boolean longRangeContains(ClosedRange paramClosedRange, float paramFloat)
  {
    Intrinsics.checkParameterIsNotNull(paramClosedRange, "$this$contains");
    Long localLong = toLongExactOrNull(paramFloat);
    if (localLong != null) {
      return paramClosedRange.contains((Comparable)localLong);
    }
    return false;
  }
  
  public static final boolean longRangeContains(ClosedRange paramClosedRange, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramClosedRange, "$this$contains");
    return paramClosedRange.contains((Comparable)Long.valueOf(paramInt));
  }
  
  public static final boolean longRangeContains(ClosedRange paramClosedRange, short paramShort)
  {
    Intrinsics.checkParameterIsNotNull(paramClosedRange, "$this$contains");
    return paramClosedRange.contains((Comparable)Long.valueOf(paramShort));
  }
  
  private static final char random(CharRange paramCharRange)
  {
    return random(paramCharRange, (Random)Random.Default);
  }
  
  public static final char random(CharRange paramCharRange, Random paramRandom)
  {
    Intrinsics.checkParameterIsNotNull(paramCharRange, "$this$random");
    Intrinsics.checkParameterIsNotNull(paramRandom, "random");
    try
    {
      int i = paramCharRange.getFirst();
      int j = paramCharRange.getLast();
      i = paramRandom.nextInt(i, j + 1);
      return (char)i;
    }
    catch (IllegalArgumentException paramCharRange)
    {
      throw ((Throwable)new NoSuchElementException(paramCharRange.getMessage()));
    }
  }
  
  private static final int random(IntRange paramIntRange)
  {
    return random(paramIntRange, (Random)Random.Default);
  }
  
  public static final int random(IntRange paramIntRange, Random paramRandom)
  {
    Intrinsics.checkParameterIsNotNull(paramIntRange, "$this$random");
    Intrinsics.checkParameterIsNotNull(paramRandom, "random");
    try
    {
      int i = RandomKt.nextInt(paramRandom, paramIntRange);
      return i;
    }
    catch (IllegalArgumentException paramIntRange)
    {
      throw ((Throwable)new NoSuchElementException(paramIntRange.getMessage()));
    }
  }
  
  private static final long random(LongRange paramLongRange)
  {
    return random(paramLongRange, (Random)Random.Default);
  }
  
  public static final long random(LongRange paramLongRange, Random paramRandom)
  {
    Intrinsics.checkParameterIsNotNull(paramLongRange, "$this$random");
    Intrinsics.checkParameterIsNotNull(paramRandom, "random");
    try
    {
      long l = RandomKt.nextLong(paramRandom, paramLongRange);
      return l;
    }
    catch (IllegalArgumentException paramLongRange)
    {
      throw ((Throwable)new NoSuchElementException(paramLongRange.getMessage()));
    }
  }
  
  public static final CharProgression reversed(CharProgression paramCharProgression)
  {
    Intrinsics.checkParameterIsNotNull(paramCharProgression, "$this$reversed");
    return CharProgression.Companion.fromClosedRange(paramCharProgression.getLast(), paramCharProgression.getFirst(), -paramCharProgression.getStep());
  }
  
  public static final IntProgression reversed(IntProgression paramIntProgression)
  {
    Intrinsics.checkParameterIsNotNull(paramIntProgression, "$this$reversed");
    return IntProgression.Companion.fromClosedRange(paramIntProgression.getLast(), paramIntProgression.getFirst(), -paramIntProgression.getStep());
  }
  
  public static final LongProgression reversed(LongProgression paramLongProgression)
  {
    Intrinsics.checkParameterIsNotNull(paramLongProgression, "$this$reversed");
    return LongProgression.Companion.fromClosedRange(paramLongProgression.getLast(), paramLongProgression.getFirst(), -paramLongProgression.getStep());
  }
  
  public static final boolean shortRangeContains(ClosedRange paramClosedRange, byte paramByte)
  {
    Intrinsics.checkParameterIsNotNull(paramClosedRange, "$this$contains");
    return paramClosedRange.contains((Comparable)Short.valueOf((short)paramByte));
  }
  
  public static final boolean shortRangeContains(ClosedRange paramClosedRange, double paramDouble)
  {
    Intrinsics.checkParameterIsNotNull(paramClosedRange, "$this$contains");
    Short localShort = toShortExactOrNull(paramDouble);
    if (localShort != null) {
      return paramClosedRange.contains((Comparable)localShort);
    }
    return false;
  }
  
  public static final boolean shortRangeContains(ClosedRange paramClosedRange, float paramFloat)
  {
    Intrinsics.checkParameterIsNotNull(paramClosedRange, "$this$contains");
    Short localShort = toShortExactOrNull(paramFloat);
    if (localShort != null) {
      return paramClosedRange.contains((Comparable)localShort);
    }
    return false;
  }
  
  public static final boolean shortRangeContains(ClosedRange paramClosedRange, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramClosedRange, "$this$contains");
    Short localShort = toShortExactOrNull(paramInt);
    if (localShort != null) {
      return paramClosedRange.contains((Comparable)localShort);
    }
    return false;
  }
  
  public static final boolean shortRangeContains(ClosedRange paramClosedRange, long paramLong)
  {
    Intrinsics.checkParameterIsNotNull(paramClosedRange, "$this$contains");
    Short localShort = toShortExactOrNull(paramLong);
    if (localShort != null) {
      return paramClosedRange.contains((Comparable)localShort);
    }
    return false;
  }
  
  public static final CharProgression step(CharProgression paramCharProgression, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramCharProgression, "$this$step");
    boolean bool;
    if (paramInt > 0) {
      bool = true;
    } else {
      bool = false;
    }
    RangesKt__RangesKt.checkStepIsPositive(bool, (Number)Integer.valueOf(paramInt));
    CharProgression.Companion localCompanion = CharProgression.Companion;
    char c1 = paramCharProgression.getFirst();
    char c2 = paramCharProgression.getLast();
    if (paramCharProgression.getStep() <= 0) {
      paramInt = -paramInt;
    }
    return localCompanion.fromClosedRange(c1, c2, paramInt);
  }
  
  public static final IntProgression step(IntProgression paramIntProgression, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramIntProgression, "$this$step");
    boolean bool;
    if (paramInt > 0) {
      bool = true;
    } else {
      bool = false;
    }
    RangesKt__RangesKt.checkStepIsPositive(bool, (Number)Integer.valueOf(paramInt));
    IntProgression.Companion localCompanion = IntProgression.Companion;
    int i = paramIntProgression.getFirst();
    int j = paramIntProgression.getLast();
    if (paramIntProgression.getStep() <= 0) {
      paramInt = -paramInt;
    }
    return localCompanion.fromClosedRange(i, j, paramInt);
  }
  
  public static final LongProgression step(LongProgression paramLongProgression, long paramLong)
  {
    Intrinsics.checkParameterIsNotNull(paramLongProgression, "$this$step");
    boolean bool;
    if (paramLong > 0L) {
      bool = true;
    } else {
      bool = false;
    }
    RangesKt__RangesKt.checkStepIsPositive(bool, (Number)Long.valueOf(paramLong));
    LongProgression.Companion localCompanion = LongProgression.Companion;
    long l1 = paramLongProgression.getFirst();
    long l2 = paramLongProgression.getLast();
    if (paramLongProgression.getStep() <= 0L) {
      paramLong = -paramLong;
    }
    return localCompanion.fromClosedRange(l1, l2, paramLong);
  }
  
  public static final Byte toByteExactOrNull(double paramDouble)
  {
    if ((paramDouble >= -128.0D) && (paramDouble <= 127.0D)) {
      return Byte.valueOf((byte)(int)paramDouble);
    }
    return null;
  }
  
  public static final Byte toByteExactOrNull(float paramFloat)
  {
    if ((paramFloat >= -128.0F) && (paramFloat <= 127.0F)) {
      return Byte.valueOf((byte)(int)paramFloat);
    }
    return null;
  }
  
  public static final Byte toByteExactOrNull(int paramInt)
  {
    if ((-128 <= paramInt) && (127 >= paramInt)) {
      return Byte.valueOf((byte)paramInt);
    }
    return null;
  }
  
  public static final Byte toByteExactOrNull(long paramLong)
  {
    long l1 = Byte.MIN_VALUE;
    long l2 = 127;
    if ((l1 <= paramLong) && (l2 >= paramLong)) {
      return Byte.valueOf((byte)(int)paramLong);
    }
    return null;
  }
  
  public static final Byte toByteExactOrNull(short paramShort)
  {
    short s1 = (short)Byte.MIN_VALUE;
    short s2 = (short)127;
    if ((s1 <= paramShort) && (s2 >= paramShort)) {
      return Byte.valueOf((byte)paramShort);
    }
    return null;
  }
  
  public static final Integer toIntExactOrNull(double paramDouble)
  {
    if ((paramDouble >= -2.147483648E9D) && (paramDouble <= 2.147483647E9D)) {
      return Integer.valueOf((int)paramDouble);
    }
    return null;
  }
  
  public static final Integer toIntExactOrNull(float paramFloat)
  {
    if ((paramFloat >= -2.14748365E9F) && (paramFloat <= 2.14748365E9F)) {
      return Integer.valueOf((int)paramFloat);
    }
    return null;
  }
  
  public static final Integer toIntExactOrNull(long paramLong)
  {
    long l1 = Integer.MIN_VALUE;
    long l2 = Integer.MAX_VALUE;
    if ((l1 <= paramLong) && (l2 >= paramLong)) {
      return Integer.valueOf((int)paramLong);
    }
    return null;
  }
  
  public static final Long toLongExactOrNull(double paramDouble)
  {
    double d1 = Long.MIN_VALUE;
    double d2 = Long.MAX_VALUE;
    if ((paramDouble >= d1) && (paramDouble <= d2)) {
      return Long.valueOf(paramDouble);
    }
    return null;
  }
  
  public static final Long toLongExactOrNull(float paramFloat)
  {
    float f1 = (float)Long.MIN_VALUE;
    float f2 = (float)Long.MAX_VALUE;
    if ((paramFloat >= f1) && (paramFloat <= f2)) {
      return Long.valueOf(paramFloat);
    }
    return null;
  }
  
  public static final Short toShortExactOrNull(double paramDouble)
  {
    if ((paramDouble >= -32768.0D) && (paramDouble <= 32767.0D)) {
      return Short.valueOf((short)(int)paramDouble);
    }
    return null;
  }
  
  public static final Short toShortExactOrNull(float paramFloat)
  {
    if ((paramFloat >= -32768.0F) && (paramFloat <= 32767.0F)) {
      return Short.valueOf((short)(int)paramFloat);
    }
    return null;
  }
  
  public static final Short toShortExactOrNull(int paramInt)
  {
    if ((32768 <= paramInt) && (32767 >= paramInt)) {
      return Short.valueOf((short)paramInt);
    }
    return null;
  }
  
  public static final Short toShortExactOrNull(long paramLong)
  {
    long l1 = '?';
    long l2 = '?';
    if ((l1 <= paramLong) && (l2 >= paramLong)) {
      return Short.valueOf((short)(int)paramLong);
    }
    return null;
  }
  
  public static final CharRange until(char paramChar1, char paramChar2)
  {
    if (paramChar2 <= 0) {
      return CharRange.Companion.getEMPTY();
    }
    return new CharRange(paramChar1, (char)(paramChar2 - '\001'));
  }
  
  public static final IntRange until(byte paramByte1, byte paramByte2)
  {
    return new IntRange(paramByte1, paramByte2 - 1);
  }
  
  public static final IntRange until(byte paramByte, int paramInt)
  {
    if (paramInt <= Integer.MIN_VALUE) {
      return IntRange.Companion.getEMPTY();
    }
    return new IntRange(paramByte, paramInt - 1);
  }
  
  public static final IntRange until(byte paramByte, short paramShort)
  {
    return new IntRange(paramByte, paramShort - 1);
  }
  
  public static final IntRange until(int paramInt, byte paramByte)
  {
    return new IntRange(paramInt, paramByte - 1);
  }
  
  public static final IntRange until(int paramInt1, int paramInt2)
  {
    if (paramInt2 <= Integer.MIN_VALUE) {
      return IntRange.Companion.getEMPTY();
    }
    return new IntRange(paramInt1, paramInt2 - 1);
  }
  
  public static final IntRange until(int paramInt, short paramShort)
  {
    return new IntRange(paramInt, paramShort - 1);
  }
  
  public static final IntRange until(short paramShort, byte paramByte)
  {
    return new IntRange(paramShort, paramByte - 1);
  }
  
  public static final IntRange until(short paramShort, int paramInt)
  {
    if (paramInt <= Integer.MIN_VALUE) {
      return IntRange.Companion.getEMPTY();
    }
    return new IntRange(paramShort, paramInt - 1);
  }
  
  public static final IntRange until(short paramShort1, short paramShort2)
  {
    return new IntRange(paramShort1, paramShort2 - 1);
  }
  
  public static final LongRange until(byte paramByte, long paramLong)
  {
    if (paramLong <= Long.MIN_VALUE) {
      return LongRange.Companion.getEMPTY();
    }
    return new LongRange(paramByte, paramLong - 1L);
  }
  
  public static final LongRange until(int paramInt, long paramLong)
  {
    if (paramLong <= Long.MIN_VALUE) {
      return LongRange.Companion.getEMPTY();
    }
    return new LongRange(paramInt, paramLong - 1L);
  }
  
  public static final LongRange until(long paramLong, byte paramByte)
  {
    return new LongRange(paramLong, paramByte - 1L);
  }
  
  public static final LongRange until(long paramLong, int paramInt)
  {
    return new LongRange(paramLong, paramInt - 1L);
  }
  
  public static final LongRange until(long paramLong1, long paramLong2)
  {
    if (paramLong2 <= Long.MIN_VALUE) {
      return LongRange.Companion.getEMPTY();
    }
    return new LongRange(paramLong1, paramLong2 - 1L);
  }
  
  public static final LongRange until(long paramLong, short paramShort)
  {
    return new LongRange(paramLong, paramShort - 1L);
  }
  
  public static final LongRange until(short paramShort, long paramLong)
  {
    if (paramLong <= Long.MIN_VALUE) {
      return LongRange.Companion.getEMPTY();
    }
    return new LongRange(paramShort, paramLong - 1L);
  }
}
