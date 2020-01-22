package kotlin.coroutines.experimental.theories.internal;

import kotlin.Metadata;
import kotlin.coroutines.experimental.Continuation;
import kotlin.coroutines.experimental.ContinuationInterceptor;
import kotlin.coroutines.experimental.CoroutineContext;
import kotlin.coroutines.experimental.CoroutineContext.Key;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000\022\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\003\032*\020\000\032\b\022\004\022\002H\0020\001\"\004\b\000\020\0022\006\020\003\032\0020\0042\f\020\005\032\b\022\004\022\002H\0020\001H\000\032 \020\006\032\b\022\004\022\002H\0020\001\"\004\b\000\020\0022\f\020\005\032\b\022\004\022\002H\0020\001?\006\007"}, d2={"interceptContinuationIfNeeded", "Lkotlin/coroutines/experimental/Continuation;", "T", "context", "Lkotlin/coroutines/experimental/CoroutineContext;", "continuation", "normalizeContinuation", "kotlin-stdlib-coroutines"}, k=2, mv={1, 1, 15})
public final class CoroutineIntrinsics
{
  public static final Continuation interceptContinuationIfNeeded(CoroutineContext paramCoroutineContext, Continuation paramContinuation)
  {
    Intrinsics.checkParameterIsNotNull(paramCoroutineContext, "context");
    Intrinsics.checkParameterIsNotNull(paramContinuation, "continuation");
    paramCoroutineContext = (ContinuationInterceptor)paramCoroutineContext.find((CoroutineContext.Key)ContinuationInterceptor.this$0);
    if (paramCoroutineContext != null)
    {
      paramCoroutineContext = paramCoroutineContext.interceptContinuation(paramContinuation);
      if (paramCoroutineContext != null) {
        return paramCoroutineContext;
      }
    }
    return paramContinuation;
  }
  
  public static final Continuation normalizeContinuation(Continuation paramContinuation)
  {
    Intrinsics.checkParameterIsNotNull(paramContinuation, "continuation");
    if (!(paramContinuation instanceof CoroutineImpl)) {
      localObject = null;
    } else {
      localObject = paramContinuation;
    }
    Object localObject = (CoroutineImpl)localObject;
    if (localObject != null)
    {
      localObject = ((CoroutineImpl)localObject).getFacade();
      if (localObject != null) {
        return localObject;
      }
    }
    return paramContinuation;
  }
}
