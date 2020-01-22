package kotlin.sequences;

import java.util.Enumeration;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt__IteratorsJVMKt;

@Metadata(bv={1, 0, 3}, d1={"\000\016\n\000\n\002\030\002\n\000\n\002\030\002\n\000\032\037\020\000\032\b\022\004\022\002H\0020\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\003H?\b?\006\004"}, d2={"asSequence", "Lkotlin/sequences/Sequence;", "T", "Ljava/util/Enumeration;", "kotlin-stdlib"}, k=5, mv={1, 1, 15}, xi=1, xs="kotlin/sequences/SequencesKt")
class SequencesKt__SequencesJVMKt
  extends SequencesKt__SequenceBuilderKt
{
  public SequencesKt__SequencesJVMKt() {}
  
  private static final Sequence asSequence(Enumeration paramEnumeration)
  {
    return SequencesKt__SequencesKt.asSequence(CollectionsKt__IteratorsJVMKt.iterator(paramEnumeration));
  }
}
