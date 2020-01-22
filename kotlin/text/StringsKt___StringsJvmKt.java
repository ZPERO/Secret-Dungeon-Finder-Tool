package kotlin.text;

import java.util.Collection;
import java.util.SortedSet;
import java.util.TreeSet;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000\030\n\000\n\002\020\f\n\002\020\r\n\000\n\002\020\b\n\000\n\002\030\002\n\000\032\025\020\000\032\0020\001*\0020\0022\006\020\003\032\0020\004H?\b\032\020\020\005\032\b\022\004\022\0020\0010\006*\0020\002?\006\007"}, d2={"elementAt", "", "", "index", "", "toSortedSet", "Ljava/util/SortedSet;", "kotlin-stdlib"}, k=5, mv={1, 1, 15}, xi=1, xs="kotlin/text/StringsKt")
class StringsKt___StringsJvmKt
  extends StringsKt__StringsKt
{
  public StringsKt___StringsJvmKt() {}
  
  private static final char elementAt(CharSequence paramCharSequence, int paramInt)
  {
    return paramCharSequence.charAt(paramInt);
  }
  
  public static final SortedSet toSortedSet(CharSequence paramCharSequence)
  {
    Intrinsics.checkParameterIsNotNull(paramCharSequence, "$this$toSortedSet");
    return (SortedSet)StringsKt___StringsKt.toCollection(paramCharSequence, (Collection)new TreeSet());
  }
}
