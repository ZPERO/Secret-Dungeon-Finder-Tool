package kotlin.text;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000B\n\000\n\002\020\016\n\000\n\002\020\b\n\000\n\002\030\002\n\002\030\002\n\002\030\002\n\002\020\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\030\002\n\000\n\002\020\021\n\002\020\r\n\000\n\002\020\000\n\002\b\007\032.\020\000\032\0020\0012\006\020\002\032\0020\0032\033\020\004\032\027\022\b\022\0060\006j\002`\007\022\004\022\0020\b0\005?\006\002\b\tH?\b\032&\020\000\032\0020\0012\033\020\004\032\027\022\b\022\0060\006j\002`\007\022\004\022\0020\b0\005?\006\002\b\tH?\b\0325\020\n\032\002H\013\"\f\b\000\020\013*\0060\fj\002`\r*\002H\0132\026\020\016\032\f\022\b\b\001\022\004\030\0010\0200\017\"\004\030\0010\020?\006\002\020\021\032/\020\n\032\0060\006j\002`\007*\0060\006j\002`\0072\026\020\016\032\f\022\b\b\001\022\004\030\0010\0220\017\"\004\030\0010\022?\006\002\020\023\032/\020\n\032\0060\006j\002`\007*\0060\006j\002`\0072\026\020\016\032\f\022\b\b\001\022\004\030\0010\0010\017\"\004\030\0010\001?\006\002\020\024\0329\020\025\032\0020\b\"\004\b\000\020\013*\0060\fj\002`\r2\006\020\026\032\002H\0132\024\020\027\032\020\022\004\022\002H\013\022\004\022\0020\020\030\0010\005H\000?\006\002\020\030?\006\031"}, d2={"buildString", "", "capacity", "", "builderAction", "Lkotlin/Function1;", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "", "Lkotlin/ExtensionFunctionType;", "append", "T", "Ljava/lang/Appendable;", "Lkotlin/text/Appendable;", "value", "", "", "(Ljava/lang/Appendable;[Ljava/lang/CharSequence;)Ljava/lang/Appendable;", "", "(Ljava/lang/StringBuilder;[Ljava/lang/Object;)Ljava/lang/StringBuilder;", "(Ljava/lang/StringBuilder;[Ljava/lang/String;)Ljava/lang/StringBuilder;", "appendElement", "element", "transform", "(Ljava/lang/Appendable;Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)V", "kotlin-stdlib"}, k=5, mv={1, 1, 15}, xi=1, xs="kotlin/text/StringsKt")
class StringsKt__StringBuilderKt
  extends StringsKt__StringBuilderJVMKt
{
  public StringsKt__StringBuilderKt() {}
  
  public static final Appendable append(Appendable paramAppendable, CharSequence... paramVarArgs)
  {
    Intrinsics.checkParameterIsNotNull(paramAppendable, "$this$append");
    Intrinsics.checkParameterIsNotNull(paramVarArgs, "value");
    int j = paramVarArgs.length;
    int i = 0;
    while (i < j)
    {
      paramAppendable.append(paramVarArgs[i]);
      i += 1;
    }
    return paramAppendable;
  }
  
  public static final StringBuilder append(StringBuilder paramStringBuilder, Object... paramVarArgs)
  {
    Intrinsics.checkParameterIsNotNull(paramStringBuilder, "$this$append");
    Intrinsics.checkParameterIsNotNull(paramVarArgs, "value");
    int j = paramVarArgs.length;
    int i = 0;
    while (i < j)
    {
      paramStringBuilder.append(paramVarArgs[i]);
      i += 1;
    }
    return paramStringBuilder;
  }
  
  public static final StringBuilder append(StringBuilder paramStringBuilder, String... paramVarArgs)
  {
    Intrinsics.checkParameterIsNotNull(paramStringBuilder, "$this$append");
    Intrinsics.checkParameterIsNotNull(paramVarArgs, "value");
    int j = paramVarArgs.length;
    int i = 0;
    while (i < j)
    {
      paramStringBuilder.append(paramVarArgs[i]);
      i += 1;
    }
    return paramStringBuilder;
  }
  
  public static final void appendElement(Appendable paramAppendable, Object paramObject, Function1 paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramAppendable, "$this$appendElement");
    if (paramFunction1 != null)
    {
      paramAppendable.append((CharSequence)paramFunction1.invoke(paramObject));
      return;
    }
    boolean bool;
    if (paramObject != null) {
      bool = paramObject instanceof CharSequence;
    } else {
      bool = true;
    }
    if (bool)
    {
      paramAppendable.append((CharSequence)paramObject);
      return;
    }
    if ((paramObject instanceof Character))
    {
      paramAppendable.append(((Character)paramObject).charValue());
      return;
    }
    paramAppendable.append((CharSequence)String.valueOf(paramObject));
  }
  
  private static final String buildString(int paramInt, Function1 paramFunction1)
  {
    StringBuilder localStringBuilder = new StringBuilder(paramInt);
    paramFunction1.invoke(localStringBuilder);
    paramFunction1 = localStringBuilder.toString();
    Intrinsics.checkExpressionValueIsNotNull(paramFunction1, "StringBuilder(capacity).?builderAction).toString()");
    return paramFunction1;
  }
  
  private static final String buildString(Function1 paramFunction1)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    paramFunction1.invoke(localStringBuilder);
    paramFunction1 = localStringBuilder.toString();
    Intrinsics.checkExpressionValueIsNotNull(paramFunction1, "StringBuilder().apply(builderAction).toString()");
    return paramFunction1;
  }
}
