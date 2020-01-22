package kotlin.text;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TypeCastException;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.markers.KMappedMarker;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt___RangesKt;
import kotlin.sequences.Sequence;

@Metadata(bv={1, 0, 3}, d1={"\000:\n\002\030\002\n\002\030\002\n\002\030\002\n\000\n\002\020\r\n\000\n\002\020\b\n\002\b\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020(\n\000\b\002\030\0002\b\022\004\022\0020\0020\001BY\022\006\020\003\032\0020\004\022\006\020\005\032\0020\006\022\006\020\007\032\0020\006\022:\020\b\0326\022\004\022\0020\004\022\023\022\0210\006?\006\f\b\n\022\b\b\013\022\004\b\b(\f\022\022\022\020\022\004\022\0020\006\022\004\022\0020\006\030\0010\r0\t?\006\002\b\016?\006\002\020\017J\017\020\020\032\b\022\004\022\0020\0020\021H?\002RB\020\b\0326\022\004\022\0020\004\022\023\022\0210\006?\006\f\b\n\022\b\b\013\022\004\b\b(\f\022\022\022\020\022\004\022\0020\006\022\004\022\0020\006\030\0010\r0\t?\006\002\b\016X?\004?\006\002\n\000R\016\020\003\032\0020\004X?\004?\006\002\n\000R\016\020\007\032\0020\006X?\004?\006\002\n\000R\016\020\005\032\0020\006X?\004?\006\002\n\000?\006\022"}, d2={"Lkotlin/text/DelimitedRangesSequence;", "Lkotlin/sequences/Sequence;", "Lkotlin/ranges/IntRange;", "input", "", "startIndex", "", "limit", "getNextMatch", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "currentIndex", "Lkotlin/Pair;", "Lkotlin/ExtensionFunctionType;", "(Ljava/lang/CharSequence;IILkotlin/jvm/functions/Function2;)V", "iterator", "", "kotlin-stdlib"}, k=1, mv={1, 1, 15})
final class DelimitedRangesSequence
  implements Sequence<IntRange>
{
  private final Function2<CharSequence, Integer, Pair<Integer, Integer>> getNextMatch;
  private final CharSequence input;
  private final int limit;
  private final int startIndex;
  
  public DelimitedRangesSequence(CharSequence paramCharSequence, int paramInt1, int paramInt2, Function2 paramFunction2)
  {
    input = paramCharSequence;
    startIndex = paramInt1;
    limit = paramInt2;
    getNextMatch = paramFunction2;
  }
  
  public Iterator iterator()
  {
    (Iterator)new Iterator()
    {
      private int counter;
      private int currentStartIndex = RangesKt___RangesKt.coerceIn(DelimitedRangesSequence.access$getStartIndex$p(DelimitedRangesSequence.this), 0, DelimitedRangesSequence.access$getInput$p(DelimitedRangesSequence.this).length());
      private IntRange nextItem;
      private int nextSearchIndex = currentStartIndex;
      private int nextState = -1;
      
      private final void calcNext()
      {
        int j = nextSearchIndex;
        Object localObject2 = this;
        int i = 0;
        if (j < 0)
        {
          nextState = 0;
          nextItem = null;
          return;
        }
        Object localObject1;
        if (DelimitedRangesSequence.access$getLimit$p(this$0) > 0)
        {
          counter += 1;
          j = counter;
          localObject1 = localObject2;
          if (j >= DelimitedRangesSequence.access$getLimit$p(this$0)) {}
        }
        else
        {
          localObject2 = this;
          j = nextSearchIndex;
          localObject1 = localObject2;
          if (j <= DelimitedRangesSequence.access$getInput$p(this$0).length()) {
            break label141;
          }
        }
        i = currentStartIndex;
        nextItem = new IntRange(i, StringsKt__StringsKt.getLastIndex(DelimitedRangesSequence.access$getInput$p(this$0)));
        nextSearchIndex = -1;
        break label295;
        label141:
        localObject2 = DelimitedRangesSequence.access$getGetNextMatch$p(this$0);
        CharSequence localCharSequence = DelimitedRangesSequence.access$getInput$p(this$0);
        j = nextSearchIndex;
        localObject2 = (Pair)((Function2)localObject2).invoke(localCharSequence, Integer.valueOf(j));
        if (localObject2 == null)
        {
          i = currentStartIndex;
          nextItem = new IntRange(i, StringsKt__StringsKt.getLastIndex(DelimitedRangesSequence.access$getInput$p(this$0)));
          nextSearchIndex = -1;
        }
        else
        {
          int k = ((Number)((Pair)localObject2).component1()).intValue();
          j = ((Number)((Pair)localObject2).component2()).intValue();
          nextItem = RangesKt___RangesKt.until(currentStartIndex, k);
          currentStartIndex = (k + j);
          k = currentStartIndex;
          if (j == 0) {
            i = 1;
          }
          nextSearchIndex = (k + i);
        }
        label295:
        nextState = 1;
      }
      
      public final int getCounter()
      {
        return counter;
      }
      
      public final int getCurrentStartIndex()
      {
        return currentStartIndex;
      }
      
      public final IntRange getNextItem()
      {
        return nextItem;
      }
      
      public final int getNextSearchIndex()
      {
        return nextSearchIndex;
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
      
      public IntRange next()
      {
        if (nextState == -1) {
          calcNext();
        }
        if (nextState != 0)
        {
          IntRange localIntRange = nextItem;
          if (localIntRange != null)
          {
            nextItem = null;
            nextState = -1;
            return localIntRange;
          }
          throw new TypeCastException("null cannot be cast to non-null type kotlin.ranges.IntRange");
        }
        throw ((Throwable)new NoSuchElementException());
      }
      
      public void remove()
      {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
      }
      
      public final void setCounter(int paramAnonymousInt)
      {
        counter = paramAnonymousInt;
      }
      
      public final void setCurrentStartIndex(int paramAnonymousInt)
      {
        currentStartIndex = paramAnonymousInt;
      }
      
      public final void setNextItem(IntRange paramAnonymousIntRange)
      {
        nextItem = paramAnonymousIntRange;
      }
      
      public final void setNextSearchIndex(int paramAnonymousInt)
      {
        nextSearchIndex = paramAnonymousInt;
      }
      
      public final void setNextState(int paramAnonymousInt)
      {
        nextState = paramAnonymousInt;
      }
    };
  }
}
