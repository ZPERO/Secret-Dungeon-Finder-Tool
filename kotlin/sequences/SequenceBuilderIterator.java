package kotlin.sequences;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.Result.Companion;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.stats.internal.DebugProbesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;

@Metadata(bv={1, 0, 3}, d1={"\000B\n\002\030\002\n\000\n\002\030\002\n\002\020(\n\002\030\002\n\002\020\002\n\002\b\002\n\002\030\002\n\002\b\013\n\002\020\b\n\002\030\002\n\000\n\002\020\003\n\000\n\002\020\013\n\002\b\005\n\002\030\002\n\002\b\b\b\002\030\000*\004\b\000\020\0012\b\022\004\022\002H\0010\0022\b\022\004\022\002H\0010\0032\b\022\004\022\0020\0050\004B\005?\006\002\020\006J\b\020\026\032\0020\027H\002J\t\020\030\032\0020\031H?\002J\016\020\032\032\0028\000H?\002?\006\002\020\033J\r\020\034\032\0028\000H\002?\006\002\020\033J\036\020\035\032\0020\0052\f\020\036\032\b\022\004\022\0020\0050\037H\026?\001\000?\006\002\020 J\031\020!\032\0020\0052\006\020\"\032\0028\000H?@?\001\000?\006\002\020#J\037\020$\032\0020\0052\f\020%\032\b\022\004\022\0028\0000\003H?@?\001\000?\006\002\020&R\024\020\007\032\0020\b8VX?\004?\006\006\032\004\b\t\020\nR\026\020\013\032\n\022\004\022\0028\000\030\0010\003X?\016?\006\002\n\000R\"\020\f\032\n\022\004\022\0020\005\030\0010\004X?\016?\006\016\n\000\032\004\b\r\020\016\"\004\b\017\020\020R\022\020\021\032\004\030\0018\000X?\016?\006\004\n\002\020\022R\022\020\023\032\0060\024j\002`\025X?\016?\006\002\n\000?\002\004\n\002\b\031?\006'"}, d2={"Lkotlin/sequences/SequenceBuilderIterator;", "T", "Lkotlin/sequences/SequenceScope;", "", "Lkotlin/coroutines/Continuation;", "", "()V", "context", "Lkotlin/coroutines/CoroutineContext;", "getContext", "()Lkotlin/coroutines/CoroutineContext;", "nextIterator", "nextStep", "getNextStep", "()Lkotlin/coroutines/Continuation;", "setNextStep", "(Lkotlin/coroutines/Continuation;)V", "nextValue", "Ljava/lang/Object;", "state", "", "Lkotlin/sequences/State;", "exceptionalState", "", "hasNext", "", "next", "()Ljava/lang/Object;", "nextNotReady", "resumeWith", "result", "Lkotlin/Result;", "(Ljava/lang/Object;)V", "yield", "value", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "yieldAll", "iterator", "(Ljava/util/Iterator;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlin-stdlib"}, k=1, mv={1, 1, 15})
final class SequenceBuilderIterator<T>
  extends SequenceScope<T>
  implements Iterator<T>, Continuation<Unit>, KMappedMarker
{
  private Iterator<? extends T> nextIterator;
  private Continuation<? super Unit> nextStep;
  private T nextValue;
  private int state;
  
  public SequenceBuilderIterator() {}
  
  private final Throwable exceptionalState()
  {
    int i = state;
    if (i != 4)
    {
      if (i != 5)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("Unexpected state of the iterator: ");
        localStringBuilder.append(state);
        return (Throwable)new IllegalStateException(localStringBuilder.toString());
      }
      return (Throwable)new IllegalStateException("Iterator has failed.");
    }
    return (Throwable)new NoSuchElementException();
  }
  
  private final Object nextNotReady()
  {
    if (hasNext()) {
      return next();
    }
    throw ((Throwable)new NoSuchElementException());
  }
  
  public CoroutineContext getContext()
  {
    return (CoroutineContext)EmptyCoroutineContext.INSTANCE;
  }
  
  public final Continuation getNextStep()
  {
    return nextStep;
  }
  
  public boolean hasNext()
  {
    for (;;)
    {
      int i = state;
      if (i != 0)
      {
        if (i != 1)
        {
          if ((i == 2) || (i == 3)) {
            break;
          }
          if (i == 4) {
            return false;
          }
          throw exceptionalState();
        }
        localObject = nextIterator;
        if (localObject == null) {
          Intrinsics.throwNpe();
        }
        if (((Iterator)localObject).hasNext())
        {
          state = 2;
          return true;
        }
        nextIterator = null;
      }
      state = 5;
      Object localObject = nextStep;
      if (localObject == null) {
        Intrinsics.throwNpe();
      }
      nextStep = null;
      Unit localUnit = Unit.INSTANCE;
      Result.Companion localCompanion = Result.Companion;
      ((Continuation)localObject).resumeWith(Result.constructor-impl(localUnit));
    }
    return true;
  }
  
  public Object next()
  {
    int i = state;
    if ((i != 0) && (i != 1))
    {
      if (i != 2)
      {
        if (i == 3)
        {
          state = 0;
          localObject = nextValue;
          nextValue = null;
          return localObject;
        }
        throw exceptionalState();
      }
      state = 1;
      Object localObject = nextIterator;
      if (localObject == null) {
        Intrinsics.throwNpe();
      }
      return ((Iterator)localObject).next();
    }
    return nextNotReady();
  }
  
  public void remove()
  {
    throw new UnsupportedOperationException("Operation is not supported for read-only collection");
  }
  
  public void resumeWith(Object paramObject)
  {
    ResultKt.throwOnFailure(paramObject);
    state = 4;
  }
  
  public final void setNextStep(Continuation paramContinuation)
  {
    nextStep = paramContinuation;
  }
  
  public Object yield(Object paramObject, Continuation paramContinuation)
  {
    nextValue = paramObject;
    state = 3;
    nextStep = paramContinuation;
    paramObject = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
    if (paramObject == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      DebugProbesKt.probeCoroutineSuspended(paramContinuation);
    }
    return paramObject;
  }
  
  public Object yieldAll(Iterator paramIterator, Continuation paramContinuation)
  {
    if (!paramIterator.hasNext()) {
      return Unit.INSTANCE;
    }
    nextIterator = paramIterator;
    state = 2;
    nextStep = paramContinuation;
    paramIterator = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
    if (paramIterator == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      DebugProbesKt.probeCoroutineSuspended(paramContinuation);
    }
    return paramIterator;
  }
}
