package kotlin.jvm.internal;

import kotlin.Metadata;
import kotlin.collections.ShortIterator;

@Metadata(bv={1, 0, 3}, d1={"\000$\n\002\030\002\n\002\030\002\n\000\n\002\020\027\n\002\b\002\n\002\020\b\n\000\n\002\020\013\n\000\n\002\020\n\n\000\b\002\030\0002\0020\001B\r\022\006\020\002\032\0020\003?\006\002\020\004J\t\020\007\032\0020\bH?\002J\b\020\t\032\0020\nH\026R\016\020\002\032\0020\003X?\004?\006\002\n\000R\016\020\005\032\0020\006X?\016?\006\002\n\000?\006\013"}, d2={"Lkotlin/jvm/internal/ArrayShortIterator;", "Lkotlin/collections/ShortIterator;", "array", "", "([S)V", "index", "", "hasNext", "", "nextShort", "", "kotlin-stdlib"}, k=1, mv={1, 1, 15})
final class ArrayShortIterator
  extends ShortIterator
{
  private final short[] array;
  private int index;
  
  public ArrayShortIterator(short[] paramArrayOfShort)
  {
    array = paramArrayOfShort;
  }
  
  public boolean hasNext()
  {
    return index < array.length;
  }
  
  public short nextShort()
  {
    short[] arrayOfShort = array;
    int i = index;
    index = (i + 1);
    return arrayOfShort[i];
  }
}
