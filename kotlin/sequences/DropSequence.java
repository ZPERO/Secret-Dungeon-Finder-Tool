package kotlin.sequences;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.markers.KMappedMarker;

@Metadata(bv={1, 0, 3}, d1={"\000\"\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\b\n\002\b\004\n\002\020(\n\002\b\002\b\000\030\000*\004\b\000\020\0012\b\022\004\022\002H\0010\0022\b\022\004\022\002H\0010\003B\033\022\f\020\004\032\b\022\004\022\0028\0000\002\022\006\020\005\032\0020\006?\006\002\020\007J\026\020\b\032\b\022\004\022\0028\0000\0022\006\020\t\032\0020\006H\026J\017\020\n\032\b\022\004\022\0028\0000\013H?\002J\026\020\f\032\b\022\004\022\0028\0000\0022\006\020\t\032\0020\006H\026R\016\020\005\032\0020\006X?\004?\006\002\n\000R\024\020\004\032\b\022\004\022\0028\0000\002X?\004?\006\002\n\000?\006\r"}, d2={"Lkotlin/sequences/DropSequence;", "T", "Lkotlin/sequences/Sequence;", "Lkotlin/sequences/DropTakeSequence;", "sequence", "count", "", "(Lkotlin/sequences/Sequence;I)V", "drop", "n", "iterator", "", "take", "kotlin-stdlib"}, k=1, mv={1, 1, 15})
public final class DropSequence<T>
  implements Sequence<T>, DropTakeSequence<T>
{
  private final int count;
  private final Sequence<T> sequence;
  
  public DropSequence(Sequence paramSequence, int paramInt)
  {
    sequence = paramSequence;
    count = paramInt;
    if (count >= 0) {
      paramInt = 1;
    } else {
      paramInt = 0;
    }
    if (paramInt != 0) {
      return;
    }
    paramSequence = new StringBuilder();
    paramSequence.append("count must be non-negative, but was ");
    paramSequence.append(count);
    paramSequence.append('.');
    throw ((Throwable)new IllegalArgumentException(paramSequence.toString().toString()));
  }
  
  public Sequence drop(int paramInt)
  {
    int i = count + paramInt;
    if (i < 0) {
      return (Sequence)new DropSequence((Sequence)this, paramInt);
    }
    return (Sequence)new DropSequence(sequence, i);
  }
  
  public Iterator iterator()
  {
    (Iterator)new Iterator()
    {
      private final Iterator<T> iterator = DropSequence.access$getSequence$p(DropSequence.this).iterator();
      private int left = DropSequence.access$getCount$p(DropSequence.this);
      
      private final void drop()
      {
        while ((left > 0) && (iterator.hasNext()))
        {
          iterator.next();
          left -= 1;
        }
      }
      
      public final Iterator getIterator()
      {
        return iterator;
      }
      
      public final int getLeft()
      {
        return left;
      }
      
      public boolean hasNext()
      {
        drop();
        return iterator.hasNext();
      }
      
      public Object next()
      {
        drop();
        return iterator.next();
      }
      
      public void remove()
      {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
      }
      
      public final void setLeft(int paramAnonymousInt)
      {
        left = paramAnonymousInt;
      }
    };
  }
  
  public Sequence take(int paramInt)
  {
    int i = count;
    int j = i + paramInt;
    if (j < 0) {
      return (Sequence)new TakeSequence((Sequence)this, paramInt);
    }
    return (Sequence)new SubSequence(sequence, i, j);
  }
}
