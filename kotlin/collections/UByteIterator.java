package kotlin.collections;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.UByte;
import kotlin.jvm.internal.markers.KMappedMarker;

@Metadata(bv={1, 0, 3}, d1={"\000\020\n\002\030\002\n\002\020(\n\002\030\002\n\002\b\005\b'\030\0002\b\022\004\022\0020\0020\001B\005?\006\002\020\003J\f\020\004\032\0020\002H?\002?\001\000J\020\020\005\032\0020\002H&?\001\000?\006\002\020\006?\001\000?\002\004\n\002\b\031?\006\007"}, d2={"Lkotlin/collections/UByteIterator;", "", "Lkotlin/UByte;", "()V", "next", "nextUByte", "()B", "kotlin-stdlib"}, k=1, mv={1, 1, 15})
public abstract class UByteIterator
  implements Iterator<UByte>, KMappedMarker
{
  public UByteIterator() {}
  
  public final UByte next()
  {
    return UByte.box-impl(nextUByte());
  }
  
  public abstract byte nextUByte();
  
  public void remove()
  {
    throw new UnsupportedOperationException("Operation is not supported for read-only collection");
  }
}
