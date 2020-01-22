package kotlin.sequences;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;

@Metadata(bv={1, 0, 3}, d1={"\000$\n\002\030\002\n\000\n\002\020\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020(\n\000\b\002\030\000*\b\b\000\020\001*\0020\0022\b\022\004\022\002H\0010\003B+\022\016\020\004\032\n\022\006\022\004\030\0018\0000\005\022\024\020\006\032\020\022\004\022\0028\000\022\006\022\004\030\0018\0000\007?\006\002\020\bJ\017\020\t\032\b\022\004\022\0028\0000\nH?\002R\026\020\004\032\n\022\006\022\004\030\0018\0000\005X?\004?\006\002\n\000R\034\020\006\032\020\022\004\022\0028\000\022\006\022\004\030\0018\0000\007X?\004?\006\002\n\000?\006\013"}, d2={"Lkotlin/sequences/GeneratorSequence;", "T", "", "Lkotlin/sequences/Sequence;", "getInitialValue", "Lkotlin/Function0;", "getNextValue", "Lkotlin/Function1;", "(Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function1;)V", "iterator", "", "kotlin-stdlib"}, k=1, mv={1, 1, 15})
final class GeneratorSequence<T>
  implements Sequence<T>
{
  private final Function0<T> getInitialValue;
  private final Function1<T, T> getNextValue;
  
  public GeneratorSequence(Function0 paramFunction0, Function1 paramFunction1)
  {
    getInitialValue = paramFunction0;
    getNextValue = paramFunction1;
  }
  
  public Iterator iterator()
  {
    (Iterator)new Iterator()
    {
      private T nextItem;
      private int nextState = -2;
      
      private final void calcNext()
      {
        Object localObject1;
        if (nextState == -2)
        {
          localObject1 = GeneratorSequence.access$getGetInitialValue$p(GeneratorSequence.this).invoke();
        }
        else
        {
          localObject1 = GeneratorSequence.access$getGetNextValue$p(GeneratorSequence.this);
          Object localObject2 = nextItem;
          if (localObject2 == null) {
            Intrinsics.throwNpe();
          }
          localObject1 = ((Function1)localObject1).invoke(localObject2);
        }
        nextItem = localObject1;
        int i;
        if (nextItem == null) {
          i = 0;
        } else {
          i = 1;
        }
        nextState = i;
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
        if (nextState < 0) {
          calcNext();
        }
        return nextState == 1;
      }
      
      public Object next()
      {
        if (nextState < 0) {
          calcNext();
        }
        if (nextState != 0)
        {
          Object localObject = nextItem;
          if (localObject != null)
          {
            nextState = -1;
            return localObject;
          }
          throw new TypeCastException("null cannot be cast to non-null type T");
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