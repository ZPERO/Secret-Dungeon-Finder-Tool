package kotlin.text;

import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\000\022\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\000\b?\002\030\0002\0020\001B\007\b\002?\006\002\020\002R\020\020\003\032\0020\0048\006X?\004?\006\002\n\000?\006\005"}, d2={"Lkotlin/text/ScreenFloatValueRegEx;", "", "()V", "value", "Lkotlin/text/Regex;", "kotlin-stdlib"}, k=1, mv={1, 1, 15})
final class ScreenFloatValueRegEx
{
  public static final ScreenFloatValueRegEx INSTANCE = new ScreenFloatValueRegEx();
  public static final Regex value;
  
  static
  {
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("[eE][+-]?");
    ((StringBuilder)localObject1).append("(\\p{Digit}+)");
    localObject1 = ((StringBuilder)localObject1).toString();
    Object localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append("(0[xX]");
    ((StringBuilder)localObject2).append("(\\p{XDigit}+)");
    ((StringBuilder)localObject2).append("(\\.)?)|");
    ((StringBuilder)localObject2).append("(0[xX]");
    ((StringBuilder)localObject2).append("(\\p{XDigit}+)");
    ((StringBuilder)localObject2).append("?(\\.)");
    ((StringBuilder)localObject2).append("(\\p{XDigit}+)");
    ((StringBuilder)localObject2).append(')');
    localObject2 = ((StringBuilder)localObject2).toString();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append('(');
    localStringBuilder.append("(\\p{Digit}+)");
    localStringBuilder.append("(\\.)?(");
    localStringBuilder.append("(\\p{Digit}+)");
    localStringBuilder.append("?)(");
    localStringBuilder.append((String)localObject1);
    localStringBuilder.append(")?)|");
    localStringBuilder.append("(\\.(");
    localStringBuilder.append("(\\p{Digit}+)");
    localStringBuilder.append(")(");
    localStringBuilder.append((String)localObject1);
    localStringBuilder.append(")?)|");
    localStringBuilder.append("((");
    localStringBuilder.append((String)localObject2);
    localStringBuilder.append(")[pP][+-]?");
    localStringBuilder.append("(\\p{Digit}+)");
    localStringBuilder.append(')');
    localObject1 = localStringBuilder.toString();
    localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append("[\\x00-\\x20]*[+-]?(NaN|Infinity|((");
    ((StringBuilder)localObject2).append((String)localObject1);
    ((StringBuilder)localObject2).append(")[fFdD]?))[\\x00-\\x20]*");
    value = new Regex(((StringBuilder)localObject2).toString());
  }
  
  private ScreenFloatValueRegEx() {}
}
