package kotlin.sequences;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.jvm.internal.markers.KMappedMarker;

@Metadata(bv={1, 0, 3}, d1={"\000\030\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\b\003\n\002\020(\n\000\b\000\030\000*\004\b\000\020\0012\016\022\n\022\b\022\004\022\002H\0010\0030\002B\023\022\f\020\004\032\b\022\004\022\0028\0000\002?\006\002\020\005J\025\020\006\032\016\022\n\022\b\022\004\022\0028\0000\0030\007H?\002R\024\020\004\032\b\022\004\022\0028\0000\002X?\004?\006\002\n\000?\006\b"}, d2={"Lkotlin/sequences/IndexingSequence;", "T", "Lkotlin/sequences/Sequence;", "Lkotlin/collections/IndexedValue;", "sequence", "(Lkotlin/sequences/Sequence;)V", "iterator", "", "kotlin-stdlib"}, k=1, mv={1, 1, 15})
public final class IndexingSequence<T>
  implements Sequence<IndexedValue<? extends T>>
{
  private final Sequence<T> sequence;
  
  public IndexingSequence(Sequence paramSequence)
  {
    sequence = paramSequence;
  }
  
  public Iterator iterator()
  {
    (Iterator)new Iterator()
    {
      private int index;
      private final Iterator<T> iterator = IndexingSequence.access$getSequence$p(IndexingSequence.this).iterator();
      
      public final int getIndex()
      {
        return index;
      }
      
      public final Iterator getIterator()
      {
        return iterator;
      }
      
      public boolean hasNext()
      {
        return iterator.hasNext();
      }
      
      public IndexedValue next()
      {
        int i = index;
        index = (i + 1);
        if (i < 0) {
          CollectionsKt__CollectionsKt.throwIndexOverflow();
        }
        return new IndexedValue(i, iterator.next());
      }
      
      public void remove()
      {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
      }
      
      public final void setIndex(int paramAnonymousInt)
      {
        index = paramAnonymousInt;
      }
    };
  }
}
