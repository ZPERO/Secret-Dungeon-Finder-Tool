package kotlin.text;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000X\n\002\b\003\n\002\020\016\n\000\n\002\030\002\n\002\b\003\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\020\b\n\002\b\002\n\002\020\013\n\000\n\002\020\005\n\000\n\002\020\006\n\002\b\003\n\002\020\007\n\002\b\004\n\002\020\t\n\000\n\002\020\n\n\002\b\002\0324\020\000\032\004\030\001H\001\"\004\b\000\020\0012\006\020\002\032\0020\0032\022\020\004\032\016\022\004\022\0020\003\022\004\022\002H\0010\005H?\b?\006\004\b\006\020\007\032\r\020\b\032\0020\t*\0020\003H?\b\032\025\020\b\032\0020\t*\0020\0032\006\020\n\032\0020\013H?\b\032\016\020\f\032\004\030\0010\t*\0020\003H\007\032\026\020\f\032\004\030\0010\t*\0020\0032\006\020\n\032\0020\013H\007\032\r\020\r\032\0020\016*\0020\003H?\b\032\025\020\r\032\0020\016*\0020\0032\006\020\017\032\0020\020H?\b\032\016\020\021\032\004\030\0010\016*\0020\003H\007\032\026\020\021\032\004\030\0010\016*\0020\0032\006\020\017\032\0020\020H\007\032\r\020\022\032\0020\023*\0020\003H?\b\032\r\020\024\032\0020\025*\0020\003H?\b\032\025\020\024\032\0020\025*\0020\0032\006\020\017\032\0020\020H?\b\032\r\020\026\032\0020\027*\0020\003H?\b\032\023\020\030\032\004\030\0010\027*\0020\003H\007?\006\002\020\031\032\r\020\032\032\0020\033*\0020\003H?\b\032\023\020\034\032\004\030\0010\033*\0020\003H\007?\006\002\020\035\032\r\020\036\032\0020\020*\0020\003H?\b\032\025\020\036\032\0020\020*\0020\0032\006\020\017\032\0020\020H?\b\032\r\020\037\032\0020 *\0020\003H?\b\032\025\020\037\032\0020 *\0020\0032\006\020\017\032\0020\020H?\b\032\r\020!\032\0020\"*\0020\003H?\b\032\025\020!\032\0020\"*\0020\0032\006\020\017\032\0020\020H?\b\032\025\020#\032\0020\003*\0020\0252\006\020\017\032\0020\020H?\b\032\025\020#\032\0020\003*\0020\0202\006\020\017\032\0020\020H?\b\032\025\020#\032\0020\003*\0020 2\006\020\017\032\0020\020H?\b\032\025\020#\032\0020\003*\0020\"2\006\020\017\032\0020\020H?\b?\006$"}, d2={"screenFloatValue", "T", "str", "", "parse", "Lkotlin/Function1;", "screenFloatValue$StringsKt__StringNumberConversionsJVMKt", "(Ljava/lang/String;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "toBigDecimal", "Ljava/math/BigDecimal;", "mathContext", "Ljava/math/MathContext;", "toBigDecimalOrNull", "toBigInteger", "Ljava/math/BigInteger;", "radix", "", "toBigIntegerOrNull", "toBoolean", "", "toByte", "", "toDouble", "", "toDoubleOrNull", "(Ljava/lang/String;)Ljava/lang/Double;", "toFloat", "", "toFloatOrNull", "(Ljava/lang/String;)Ljava/lang/Float;", "toInt", "toLong", "", "toShort", "", "toString", "kotlin-stdlib"}, k=5, mv={1, 1, 15}, xi=1, xs="kotlin/text/StringsKt")
class StringsKt__StringNumberConversionsJVMKt
  extends StringsKt__StringBuilderKt
{
  public StringsKt__StringNumberConversionsJVMKt() {}
  
  private static final Object screenFloatValue$StringsKt__StringNumberConversionsJVMKt(String paramString, Function1 paramFunction1)
  {
    Regex localRegex = ScreenFloatValueRegEx.value;
    CharSequence localCharSequence = (CharSequence)paramString;
    try
    {
      boolean bool = localRegex.matches(localCharSequence);
      if (bool)
      {
        paramString = paramFunction1.invoke(paramString);
        return paramString;
      }
    }
    catch (NumberFormatException paramString) {}
    return null;
  }
  
  private static final BigDecimal toBigDecimal(String paramString)
  {
    return new BigDecimal(paramString);
  }
  
  private static final BigDecimal toBigDecimal(String paramString, MathContext paramMathContext)
  {
    return new BigDecimal(paramString, paramMathContext);
  }
  
  public static final BigDecimal toBigDecimalOrNull(String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "$this$toBigDecimalOrNull");
    Regex localRegex = ScreenFloatValueRegEx.value;
    CharSequence localCharSequence = (CharSequence)paramString;
    try
    {
      boolean bool = localRegex.matches(localCharSequence);
      if (bool)
      {
        paramString = new BigDecimal(paramString);
        return paramString;
      }
    }
    catch (NumberFormatException paramString) {}
    return null;
  }
  
  public static final BigDecimal toBigDecimalOrNull(String paramString, MathContext paramMathContext)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "$this$toBigDecimalOrNull");
    Intrinsics.checkParameterIsNotNull(paramMathContext, "mathContext");
    Regex localRegex = ScreenFloatValueRegEx.value;
    CharSequence localCharSequence = (CharSequence)paramString;
    try
    {
      boolean bool = localRegex.matches(localCharSequence);
      if (bool)
      {
        paramString = new BigDecimal(paramString, paramMathContext);
        return paramString;
      }
    }
    catch (NumberFormatException paramString) {}
    return null;
  }
  
  private static final BigInteger toBigInteger(String paramString)
  {
    return new BigInteger(paramString);
  }
  
  private static final BigInteger toBigInteger(String paramString, int paramInt)
  {
    return new BigInteger(paramString, CharsKt__CharJVMKt.checkRadix(paramInt));
  }
  
  public static final BigInteger toBigIntegerOrNull(String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "$this$toBigIntegerOrNull");
    return toBigIntegerOrNull(paramString, 10);
  }
  
  public static final BigInteger toBigIntegerOrNull(String paramString, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "$this$toBigIntegerOrNull");
    CharsKt__CharJVMKt.checkRadix(paramInt);
    int j = paramString.length();
    if (j != 0)
    {
      int i = 0;
      if (j != 1)
      {
        if (paramString.charAt(0) == '-') {
          i = 1;
        }
        while (i < j)
        {
          if (CharsKt__CharJVMKt.digitOf(paramString.charAt(i), paramInt) < 0) {
            return null;
          }
          i += 1;
        }
      }
      if (CharsKt__CharJVMKt.digitOf(paramString.charAt(0), paramInt) < 0) {
        return null;
      }
      return new BigInteger(paramString, CharsKt__CharJVMKt.checkRadix(paramInt));
    }
    return null;
  }
  
  private static final boolean toBoolean(String paramString)
  {
    return Boolean.parseBoolean(paramString);
  }
  
  private static final byte toByte(String paramString)
  {
    return Byte.parseByte(paramString);
  }
  
  private static final byte toByte(String paramString, int paramInt)
  {
    return Byte.parseByte(paramString, CharsKt__CharJVMKt.checkRadix(paramInt));
  }
  
  private static final double toDouble(String paramString)
  {
    return Double.parseDouble(paramString);
  }
  
  public static final Double toDoubleOrNull(String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "$this$toDoubleOrNull");
    Regex localRegex = ScreenFloatValueRegEx.value;
    CharSequence localCharSequence = (CharSequence)paramString;
    try
    {
      boolean bool = localRegex.matches(localCharSequence);
      if (bool)
      {
        double d = Double.parseDouble(paramString);
        return Double.valueOf(d);
      }
    }
    catch (NumberFormatException paramString) {}
    return null;
  }
  
  private static final float toFloat(String paramString)
  {
    return Float.parseFloat(paramString);
  }
  
  public static final Float toFloatOrNull(String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "$this$toFloatOrNull");
    Regex localRegex = ScreenFloatValueRegEx.value;
    CharSequence localCharSequence = (CharSequence)paramString;
    try
    {
      boolean bool = localRegex.matches(localCharSequence);
      if (bool)
      {
        float f = Float.parseFloat(paramString);
        return Float.valueOf(f);
      }
    }
    catch (NumberFormatException paramString) {}
    return null;
  }
  
  private static final int toInt(String paramString)
  {
    return Integer.parseInt(paramString);
  }
  
  private static final int toInt(String paramString, int paramInt)
  {
    return Integer.parseInt(paramString, CharsKt__CharJVMKt.checkRadix(paramInt));
  }
  
  private static final long toLong(String paramString)
  {
    return Long.parseLong(paramString);
  }
  
  private static final long toLong(String paramString, int paramInt)
  {
    return Long.parseLong(paramString, CharsKt__CharJVMKt.checkRadix(paramInt));
  }
  
  private static final short toShort(String paramString)
  {
    return Short.parseShort(paramString);
  }
  
  private static final short toShort(String paramString, int paramInt)
  {
    return Short.parseShort(paramString, CharsKt__CharJVMKt.checkRadix(paramInt));
  }
  
  private static final String toString(byte paramByte, int paramInt)
  {
    String str = Integer.toString(paramByte, CharsKt__CharJVMKt.checkRadix(CharsKt__CharJVMKt.checkRadix(paramInt)));
    Intrinsics.checkExpressionValueIsNotNull(str, "java.lang.Integer.toStri?(this, checkRadix(radix))");
    return str;
  }
  
  private static final String toString(int paramInt1, int paramInt2)
  {
    String str = Integer.toString(paramInt1, CharsKt__CharJVMKt.checkRadix(paramInt2));
    Intrinsics.checkExpressionValueIsNotNull(str, "java.lang.Integer.toStri?(this, checkRadix(radix))");
    return str;
  }
  
  private static final String toString(long paramLong, int paramInt)
  {
    String str = Long.toString(paramLong, CharsKt__CharJVMKt.checkRadix(paramInt));
    Intrinsics.checkExpressionValueIsNotNull(str, "java.lang.Long.toString(this, checkRadix(radix))");
    return str;
  }
  
  private static final String toString(short paramShort, int paramInt)
  {
    String str = Integer.toString(paramShort, CharsKt__CharJVMKt.checkRadix(CharsKt__CharJVMKt.checkRadix(paramInt)));
    Intrinsics.checkExpressionValueIsNotNull(str, "java.lang.Integer.toStri?(this, checkRadix(radix))");
    return str;
  }
}
