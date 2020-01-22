package kotlin.ranges;

import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.collections.CharIterator;

@Metadata(bv={1, 0, 3}, d1={"\000\"\n\002\030\002\n\002\030\002\n\000\n\002\020\f\n\002\b\002\n\002\020\b\n\002\b\003\n\002\020\013\n\002\b\005\b\000\030\0002\0020\001B\035\022\006\020\002\032\0020\003\022\006\020\004\032\0020\003\022\006\020\005\032\0020\006?\006\002\020\007J\t\020\t\032\0020\nH?\002J\b\020\016\032\0020\003H\026R\016\020\b\032\0020\006X?\004?\006\002\n\000R\016\020\t\032\0020\nX?\016?\006\002\n\000R\016\020\013\032\0020\006X?\016?\006\002\n\000R\021\020\005\032\0020\006?\006\b\n\000\032\004\b\f\020\r?\006\017"}, d2={"Lkotlin/ranges/CharProgressionIterator;", "Lkotlin/collections/CharIterator;", "first", "", "last", "step", "", "(CCI)V", "finalElement", "hasNext", "", "next", "getStep", "()I", "nextChar", "kotlin-stdlib"}, k=1, mv={1, 1, 15})
public final class CharProgressionIterator
  extends CharIterator
{
  private final int finalElement;
  private boolean hasNext;
  private int next;
  private final int step;
  
  public CharProgressionIterator(char paramChar1, char paramChar2, int paramInt)
  {
    step = paramInt;
    finalElement = paramChar2;
    paramInt = step;
    boolean bool = true;
    if (paramInt > 0 ? paramChar1 > paramChar2 : paramChar1 < paramChar2) {
      bool = false;
    }
    hasNext = bool;
    if (!hasNext) {
      paramChar1 = finalElement;
    }
    next = paramChar1;
  }
  
  public final int getStep()
  {
    return step;
  }
  
  public boolean hasNext()
  {
    return hasNext;
  }
  
  public char nextChar()
  {
    int i = next;
    if (i == finalElement)
    {
      if (hasNext) {
        hasNext = false;
      } else {
        throw ((Throwable)new NoSuchElementException());
      }
    }
    else {
      next = (step + i);
    }
    return (char)i;
  }
}
