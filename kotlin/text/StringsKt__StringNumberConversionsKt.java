package kotlin.text;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000.\n\000\n\002\020\001\n\000\n\002\020\016\n\000\n\002\020\005\n\002\b\002\n\002\020\b\n\002\b\005\n\002\020\t\n\002\b\003\n\002\020\n\n\002\b\003\032\020\020\000\032\0020\0012\006\020\002\032\0020\003H\000\032\023\020\004\032\004\030\0010\005*\0020\003H\007?\006\002\020\006\032\033\020\004\032\004\030\0010\005*\0020\0032\006\020\007\032\0020\bH\007?\006\002\020\t\032\023\020\n\032\004\030\0010\b*\0020\003H\007?\006\002\020\013\032\033\020\n\032\004\030\0010\b*\0020\0032\006\020\007\032\0020\bH\007?\006\002\020\f\032\023\020\r\032\004\030\0010\016*\0020\003H\007?\006\002\020\017\032\033\020\r\032\004\030\0010\016*\0020\0032\006\020\007\032\0020\bH\007?\006\002\020\020\032\023\020\021\032\004\030\0010\022*\0020\003H\007?\006\002\020\023\032\033\020\021\032\004\030\0010\022*\0020\0032\006\020\007\032\0020\bH\007?\006\002\020\024?\006\025"}, d2={"numberFormatError", "", "input", "", "toByteOrNull", "", "(Ljava/lang/String;)Ljava/lang/Byte;", "radix", "", "(Ljava/lang/String;I)Ljava/lang/Byte;", "toIntOrNull", "(Ljava/lang/String;)Ljava/lang/Integer;", "(Ljava/lang/String;I)Ljava/lang/Integer;", "toLongOrNull", "", "(Ljava/lang/String;)Ljava/lang/Long;", "(Ljava/lang/String;I)Ljava/lang/Long;", "toShortOrNull", "", "(Ljava/lang/String;)Ljava/lang/Short;", "(Ljava/lang/String;I)Ljava/lang/Short;", "kotlin-stdlib"}, k=5, mv={1, 1, 15}, xi=1, xs="kotlin/text/StringsKt")
class StringsKt__StringNumberConversionsKt
  extends StringsKt__StringNumberConversionsJVMKt
{
  public StringsKt__StringNumberConversionsKt() {}
  
  public static final Void numberFormatError(String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "input");
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Invalid number format: '");
    localStringBuilder.append(paramString);
    localStringBuilder.append('\'');
    throw ((Throwable)new NumberFormatException(localStringBuilder.toString()));
  }
  
  public static final Byte toByteOrNull(String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "$this$toByteOrNull");
    return toByteOrNull(paramString, 10);
  }
  
  public static final Byte toByteOrNull(String paramString, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "$this$toByteOrNull");
    paramString = toIntOrNull(paramString, paramInt);
    if (paramString != null)
    {
      paramInt = paramString.intValue();
      if (paramInt >= -128)
      {
        if (paramInt > 127) {
          return null;
        }
        return Byte.valueOf((byte)paramInt);
      }
    }
    return null;
  }
  
  public static final Integer toIntOrNull(String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "$this$toIntOrNull");
    return toIntOrNull(paramString, 10);
  }
  
  public static final Integer toIntOrNull(String paramString, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "$this$toIntOrNull");
    CharsKt__CharJVMKt.checkRadix(paramInt);
    int i2 = paramString.length();
    if (i2 == 0) {
      return null;
    }
    int n = 0;
    int i = paramString.charAt(0);
    int k = -2147483647;
    int j = 1;
    if (i < 48)
    {
      if (i2 == 1) {
        return null;
      }
      if (i == 45)
      {
        k = Integer.MIN_VALUE;
        i = 1;
      }
      else if (i == 43)
      {
        i = 0;
      }
      else
      {
        return null;
      }
    }
    else
    {
      i = 0;
      j = 0;
    }
    int m;
    for (int i1 = -59652323; j < i2; i1 = m)
    {
      int i3 = CharsKt__CharJVMKt.digitOf(paramString.charAt(j), paramInt);
      if (i3 < 0) {
        return null;
      }
      m = i1;
      if (n < i1)
      {
        if (i1 != -59652323) {
          break label203;
        }
        i1 = k / paramInt;
        m = i1;
        if (n < i1) {
          return null;
        }
      }
      n *= paramInt;
      if (n < k + i3) {
        return null;
      }
      n -= i3;
      j += 1;
    }
    if (i != 0) {
      return Integer.valueOf(n);
    }
    return Integer.valueOf(-n);
    label203:
    return null;
  }
  
  public static final Long toLongOrNull(String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "$this$toLongOrNull");
    return toLongOrNull(paramString, 10);
  }
  
  public static final Long toLongOrNull(String paramString, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "$this$toLongOrNull");
    CharsKt__CharJVMKt.checkRadix(paramInt);
    int k = paramString.length();
    if (k == 0) {
      return null;
    }
    int j = 0;
    int m = paramString.charAt(0);
    long l1 = -9223372036854775807L;
    int i = 1;
    if (m < 48)
    {
      if (k == 1) {
        return null;
      }
      if (m == 45)
      {
        l1 = Long.MIN_VALUE;
        j = 1;
      }
      else if (m != 43)
      {
        return null;
      }
    }
    else
    {
      i = 0;
    }
    long l3 = 0L;
    long l2;
    for (long l4 = -256204778801521550L; i < k; l4 = l2)
    {
      m = CharsKt__CharJVMKt.digitOf(paramString.charAt(i), paramInt);
      if (m < 0) {
        return null;
      }
      l2 = l4;
      if (l3 < l4)
      {
        if (l4 != -256204778801521550L) {
          break label220;
        }
        l4 = l1 / paramInt;
        l2 = l4;
        if (l3 < l4) {
          return null;
        }
      }
      l3 *= paramInt;
      l4 = m;
      if (l3 < l1 + l4) {
        return null;
      }
      l3 -= l4;
      i += 1;
    }
    if (j != 0) {
      return Long.valueOf(l3);
    }
    return Long.valueOf(-l3);
    label220:
    return null;
  }
  
  public static final Short toShortOrNull(String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "$this$toShortOrNull");
    return toShortOrNull(paramString, 10);
  }
  
  public static final Short toShortOrNull(String paramString, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "$this$toShortOrNull");
    paramString = toIntOrNull(paramString, paramInt);
    if (paramString != null)
    {
      paramInt = paramString.intValue();
      if (paramInt >= 32768)
      {
        if (paramInt > 32767) {
          return null;
        }
        return Short.valueOf((short)paramInt);
      }
    }
    return null;
  }
}