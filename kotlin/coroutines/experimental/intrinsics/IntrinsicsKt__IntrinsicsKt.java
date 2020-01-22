package kotlin.coroutines.experimental.intrinsics;

import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.coroutines.experimental.Continuation;
import kotlin.coroutines.experimental.theories.internal.CoroutineIntrinsics;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.InlineMarker;

@Metadata(bv={1, 0, 3}, d1={"\000\024\n\002\b\003\n\002\030\002\n\002\030\002\n\002\020\000\n\002\b\004\0325\020\000\032\002H\001\"\004\b\000\020\0012\034\b\004\020\002\032\026\022\n\022\b\022\004\022\002H\0010\004\022\006\022\004\030\0010\0050\003H?H?\001\000?\006\002\020\006\0325\020\007\032\002H\001\"\004\b\000\020\0012\034\b\004\020\002\032\026\022\n\022\b\022\004\022\002H\0010\004\022\006\022\004\030\0010\0050\003H?H?\001\000?\006\002\020\006\032\037\020\b\032\b\022\004\022\002H\0010\004\"\004\b\000\020\001*\b\022\004\022\002H\0010\004H?\b?\002\004\n\002\b\t?\006\t"}, d2={"suspendCoroutineOrReturn", "T", "block", "Lkotlin/Function1;", "Lkotlin/coroutines/experimental/Continuation;", "", "(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/experimental/Continuation;)Ljava/lang/Object;", "suspendCoroutineUninterceptedOrReturn", "intercepted", "kotlin-stdlib-coroutines"}, k=5, mv={1, 1, 15}, xi=1, xs="kotlin/coroutines/experimental/intrinsics/IntrinsicsKt")
class IntrinsicsKt__IntrinsicsKt
  extends IntrinsicsKt__IntrinsicsJvmKt
{
  public IntrinsicsKt__IntrinsicsKt() {}
  
  private static final Continuation intercepted(Continuation paramContinuation)
  {
    throw ((Throwable)new NotImplementedError("Implementation of intercepted is intrinsic"));
  }
  
  private static final Object suspendCoroutineOrReturn(Function1 paramFunction1, Continuation paramContinuation)
  {
    InlineMarker.mark(0);
    paramFunction1 = paramFunction1.invoke(CoroutineIntrinsics.normalizeContinuation(paramContinuation));
    InlineMarker.mark(1);
    return paramFunction1;
  }
  
  private static final Object suspendCoroutineUninterceptedOrReturn(Function1 paramFunction1, Continuation paramContinuation)
  {
    throw ((Throwable)new NotImplementedError("Implementation of suspendCoroutineUninterceptedOrReturn is intrinsic"));
  }
}
