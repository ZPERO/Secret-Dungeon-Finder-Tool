package kotlin.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt___RangesKt;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequenceScope;
import kotlin.sequences.SequencesKt__SequenceBuilderKt;

@Metadata(bv={1, 0, 3}, d1={"\000*\n\000\n\002\020\002\n\000\n\002\020\b\n\002\b\002\n\002\020(\n\002\020 \n\002\b\003\n\002\020\013\n\002\b\002\n\002\030\002\n\000\032\030\020\000\032\0020\0012\006\020\002\032\0020\0032\006\020\004\032\0020\003H\000\032H\020\005\032\016\022\n\022\b\022\004\022\002H\b0\0070\006\"\004\b\000\020\b2\f\020\t\032\b\022\004\022\002H\b0\0062\006\020\002\032\0020\0032\006\020\004\032\0020\0032\006\020\n\032\0020\0132\006\020\f\032\0020\013H\000\032D\020\r\032\016\022\n\022\b\022\004\022\002H\b0\0070\016\"\004\b\000\020\b*\b\022\004\022\002H\b0\0162\006\020\002\032\0020\0032\006\020\004\032\0020\0032\006\020\n\032\0020\0132\006\020\f\032\0020\013H\000?\006\017"}, d2={"checkWindowSizeStep", "", "size", "", "step", "windowedIterator", "", "", "T", "iterator", "partialWindows", "", "reuseBuffer", "windowedSequence", "Lkotlin/sequences/Sequence;", "kotlin-stdlib"}, k=2, mv={1, 1, 15})
public final class SlidingWindowKt
{
  public static final void checkWindowSizeStep(int paramInt1, int paramInt2)
  {
    int i;
    if ((paramInt1 > 0) && (paramInt2 > 0)) {
      i = 1;
    } else {
      i = 0;
    }
    if (i == 0)
    {
      Object localObject;
      if (paramInt1 != paramInt2)
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("Both size ");
        ((StringBuilder)localObject).append(paramInt1);
        ((StringBuilder)localObject).append(" and step ");
        ((StringBuilder)localObject).append(paramInt2);
        ((StringBuilder)localObject).append(" must be greater than zero.");
        localObject = ((StringBuilder)localObject).toString();
      }
      else
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("size ");
        ((StringBuilder)localObject).append(paramInt1);
        ((StringBuilder)localObject).append(" must be greater than zero.");
        localObject = ((StringBuilder)localObject).toString();
      }
      throw ((Throwable)new IllegalArgumentException(localObject.toString()));
    }
  }
  
  public static final Iterator windowedIterator(final Iterator paramIterator, int paramInt1, final int paramInt2, final boolean paramBoolean1, final boolean paramBoolean2)
  {
    Intrinsics.checkParameterIsNotNull(paramIterator, "iterator");
    if (!paramIterator.hasNext()) {
      return (Iterator)EmptyIterator.INSTANCE;
    }
    SequencesKt__SequenceBuilderKt.iterator((Function2)new kotlin.coroutines.stats.internal.RestrictedSuspendLambda(paramInt1, paramInt2)
    {
      Object codes;
      int height;
      int index;
      int keyboard;
      int label;
      Object lastIndex;
      private SequenceScope mMiniKeyboard;
      Object popupResId;
      Object text;
      
      public final Continuation create(Object paramAnonymousObject, Continuation paramAnonymousContinuation)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousContinuation, "completion");
        paramAnonymousContinuation = new 1($size, paramInt2, paramIterator, paramBoolean2, paramBoolean1, paramAnonymousContinuation);
        mMiniKeyboard = ((SequenceScope)paramAnonymousObject);
        return paramAnonymousContinuation;
      }
      
      public final Object invoke(Object paramAnonymousObject1, Object paramAnonymousObject2)
      {
        return ((1)create(paramAnonymousObject1, (Continuation)paramAnonymousObject2)).invokeSuspend(Unit.INSTANCE);
      }
      
      public final Object invokeSuspend(Object paramAnonymousObject)
      {
        Object localObject2 = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = label;
        Object localObject1;
        int j;
        Object localObject4;
        Object localObject3;
        Object localObject5;
        int k;
        Object localObject6;
        int m;
        if (i != 0)
        {
          if (i != 1)
          {
            if (i != 2)
            {
              if (i != 3)
              {
                if (i != 4)
                {
                  if (i == 5) {
                    localObject1 = (RingBuffer)codes;
                  } else {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                  }
                }
                else
                {
                  localObject1 = (RingBuffer)codes;
                  i = height;
                  j = keyboard;
                  localObject4 = (SequenceScope)popupResId;
                  ResultKt.throwOnFailure(paramAnonymousObject);
                  paramAnonymousObject = localObject1;
                  break label821;
                }
              }
              else
              {
                localObject3 = (Iterator)text;
                localObject1 = (RingBuffer)codes;
                i = height;
                j = keyboard;
                localObject5 = (SequenceScope)popupResId;
                ResultKt.throwOnFailure(paramAnonymousObject);
                paramAnonymousObject = localObject1;
                break label710;
              }
            }
            else {
              localObject1 = (ArrayList)codes;
            }
            localObject1 = (SequenceScope)popupResId;
            ResultKt.throwOnFailure(paramAnonymousObject);
            break label891;
          }
          localObject4 = (Iterator)text;
          localObject1 = (ArrayList)codes;
          i = height;
          k = keyboard;
          localObject3 = (SequenceScope)popupResId;
          ResultKt.throwOnFailure(paramAnonymousObject);
          localObject6 = localObject2;
          localObject2 = localObject3;
        }
        else
        {
          ResultKt.throwOnFailure(paramAnonymousObject);
          localObject1 = mMiniKeyboard;
          k = RangesKt___RangesKt.coerceAtMost($size, 1024);
          i = paramInt2 - $size;
          if (i < 0) {
            break label540;
          }
          paramAnonymousObject = new ArrayList(k);
          m = 0;
          localObject4 = paramIterator;
          j = i;
          localObject3 = localObject2;
          i = m;
          localObject5 = localObject1;
        }
        while (((Iterator)localObject4).hasNext())
        {
          localObject1 = ((Iterator)localObject4).next();
          if (i > 0)
          {
            i -= 1;
          }
          else
          {
            paramAnonymousObject.add(localObject1);
            if (paramAnonymousObject.size() == $size)
            {
              popupResId = localObject5;
              keyboard = k;
              height = j;
              codes = paramAnonymousObject;
              index = i;
              lastIndex = localObject1;
              text = localObject4;
              label = 1;
              i = j;
              localObject2 = localObject5;
              localObject1 = paramAnonymousObject;
              localObject6 = localObject3;
              if (((SequenceScope)localObject5).yield(paramAnonymousObject, this) == localObject3) {
                return localObject3;
              }
              if (paramBoolean2)
              {
                ((ArrayList)localObject1).clear();
                paramAnonymousObject = localObject1;
              }
              else
              {
                paramAnonymousObject = new ArrayList($size);
              }
              m = i;
              j = i;
              localObject5 = localObject2;
              i = m;
              localObject3 = localObject6;
            }
          }
        }
        if (((((Collection)paramAnonymousObject).isEmpty() ^ true)) && ((paramBoolean1) || (paramAnonymousObject.size() == $size)))
        {
          popupResId = localObject5;
          keyboard = k;
          height = j;
          codes = paramAnonymousObject;
          index = i;
          label = 2;
          if (((SequenceScope)localObject5).yield(paramAnonymousObject, this) == localObject3)
          {
            return localObject3;
            label540:
            paramAnonymousObject = new RingBuffer(k);
            localObject3 = paramIterator;
            j = k;
            while (((Iterator)localObject3).hasNext())
            {
              localObject5 = ((Iterator)localObject3).next();
              paramAnonymousObject.add(localObject5);
              if (paramAnonymousObject.isFull())
              {
                k = paramAnonymousObject.size();
                m = $size;
                if (k < m)
                {
                  paramAnonymousObject = paramAnonymousObject.expanded(m);
                }
                else
                {
                  if (paramBoolean2) {
                    localObject4 = (List)paramAnonymousObject;
                  } else {
                    localObject4 = (List)new ArrayList((Collection)paramAnonymousObject);
                  }
                  popupResId = localObject1;
                  keyboard = j;
                  height = i;
                  codes = paramAnonymousObject;
                  lastIndex = localObject5;
                  text = localObject3;
                  label = 3;
                  localObject5 = localObject1;
                  if (((SequenceScope)localObject1).yield(localObject4, this) == localObject2) {
                    return localObject2;
                  }
                  label710:
                  paramAnonymousObject.removeFirst(paramInt2);
                  localObject1 = localObject5;
                }
              }
            }
            if (paramBoolean1)
            {
              while (paramAnonymousObject.size() > paramInt2)
              {
                if (paramBoolean2) {
                  localObject3 = (List)paramAnonymousObject;
                } else {
                  localObject3 = (List)new ArrayList((Collection)paramAnonymousObject);
                }
                popupResId = localObject1;
                keyboard = j;
                height = i;
                codes = paramAnonymousObject;
                label = 4;
                localObject4 = localObject1;
                if (((SequenceScope)localObject1).yield(localObject3, this) == localObject2) {
                  return localObject2;
                }
                label821:
                paramAnonymousObject.removeFirst(paramInt2);
                localObject1 = localObject4;
              }
              if ((((Collection)paramAnonymousObject).isEmpty() ^ true))
              {
                popupResId = localObject1;
                keyboard = j;
                height = i;
                codes = paramAnonymousObject;
                label = 5;
                if (((SequenceScope)localObject1).yield(paramAnonymousObject, this) == localObject2) {
                  return localObject2;
                }
              }
            }
          }
        }
        label891:
        return Unit.INSTANCE;
      }
    });
  }
  
  public static final Sequence windowedSequence(Sequence paramSequence, final int paramInt1, final int paramInt2, final boolean paramBoolean1, final boolean paramBoolean2)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$windowedSequence");
    checkWindowSizeStep(paramInt1, paramInt2);
    (Sequence)new Sequence()
    {
      public Iterator iterator()
      {
        return SlidingWindowKt.windowedIterator(SlidingWindowKt.this.iterator(), paramInt1, paramInt2, paramBoolean1, paramBoolean2);
      }
    };
  }
}
