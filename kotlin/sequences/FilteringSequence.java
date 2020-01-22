package kotlin.sequences;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.markers.KMappedMarker;

@Metadata(bv={1, 0, 3}, d1={"\000\"\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\013\n\000\n\002\030\002\n\002\b\002\n\002\020(\n\000\b\000\030\000*\004\b\000\020\0012\b\022\004\022\002H\0010\002B1\022\f\020\003\032\b\022\004\022\0028\0000\002\022\b\b\002\020\004\032\0020\005\022\022\020\006\032\016\022\004\022\0028\000\022\004\022\0020\0050\007?\006\002\020\bJ\017\020\t\032\b\022\004\022\0028\0000\nH?\002R\032\020\006\032\016\022\004\022\0028\000\022\004\022\0020\0050\007X?\004?\006\002\n\000R\016\020\004\032\0020\005X?\004?\006\002\n\000R\024\020\003\032\b\022\004\022\0028\0000\002X?\004?\006\002\n\000?\006\013"}, d2={"Lkotlin/sequences/FilteringSequence;", "T", "Lkotlin/sequences/Sequence;", "sequence", "sendWhen", "", "predicate", "Lkotlin/Function1;", "(Lkotlin/sequences/Sequence;ZLkotlin/jvm/functions/Function1;)V", "iterator", "", "kotlin-stdlib"}, k=1, mv={1, 1, 15})
public final class FilteringSequence<T>
  implements Sequence<T>
{
  private final Function1<T, Boolean> predicate;
  private final boolean sendWhen;
  private final Sequence<T> sequence;
  
  public FilteringSequence(Sequence paramSequence, boolean paramBoolean, Function1 paramFunction1)
  {
    sequence = paramSequence;
    sendWhen = paramBoolean;
    predicate = paramFunction1;
  }
  
  public Iterator iterator()
  {
    (Iterator)new Iterator()
    {
      private final Iterator<T> iterator = FilteringSequence.access$getSequence$p(FilteringSequence.this).iterator();
      private T nextItem;
      private int nextState = -1;
      
      private final void calcNext()
      {
        while (iterator.hasNext())
        {
          Object localObject = iterator.next();
          if (((Boolean)FilteringSequence.access$getPredicate$p(FilteringSequence.this).invoke(localObject)).booleanValue() == FilteringSequence.access$getSendWhen$p(FilteringSequence.this))
          {
            nextItem = localObject;
            nextState = 1;
            return;
          }
        }
        nextState = 0;
      }
      
      public final Iterator getIterator()
      {
        return iterator;
      }
      
      public final Object getNextItem()
      {
        return nextItem;
      }
      
      public final int getNextState()
      {
        return nextState;
      }
      
      public boolean hasNext()
      {
        if (nextState == -1) {
          calcNext();
        }
        return nextState == 1;
      }
      
      public Object next()
      {
        if (nextState == -1) {
          calcNext();
        }
        if (nextState != 0)
        {
          Object localObject = nextItem;
          nextItem = null;
          nextState = -1;
          return localObject;
        }
        throw ((Throwable)new NoSuchElementException());
      }
      
      public void remove()
      {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
      }
      
      public final void setNextItem(Object paramAnonymousObject)
      {
        nextItem = paramAnonymousObject;
      }
      
      public final void setNextState(int paramAnonymousInt)
      {
        nextState = paramAnonymousInt;
      }
    };
  }
}
