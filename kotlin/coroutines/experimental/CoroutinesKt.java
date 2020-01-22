package kotlin.coroutines.experimental;

import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.coroutines.experimental.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.coroutines.experimental.theories.internal.CoroutineIntrinsics;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\0006\n\000\n\002\030\002\n\002\b\005\n\002\020\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\002\b\004\n\002\030\002\n\002\030\002\n\002\b\006\032%\020\006\032\0020\0072\n\020\b\032\006\022\002\b\0030\t2\016\020\n\032\n\022\006\022\004\030\0010\f0\013H?\b\0323\020\r\032\002H\016\"\004\b\000\020\0162\032\b\004\020\n\032\024\022\n\022\b\022\004\022\002H\0160\t\022\004\022\0020\0070\017H?H?\001\000?\006\002\020\020\032D\020\021\032\b\022\004\022\0020\0070\t\"\004\b\000\020\016*\030\b\001\022\n\022\b\022\004\022\002H\0160\t\022\006\022\004\030\0010\f0\0172\f\020\b\032\b\022\004\022\002H\0160\tH\007?\001\000?\006\002\020\022\032]\020\021\032\b\022\004\022\0020\0070\t\"\004\b\000\020\023\"\004\b\001\020\016*#\b\001\022\004\022\002H\023\022\n\022\b\022\004\022\002H\0160\t\022\006\022\004\030\0010\f0\024?\006\002\b\0252\006\020\026\032\002H\0232\f\020\b\032\b\022\004\022\002H\0160\tH\007?\001\000?\006\002\020\027\032>\020\030\032\0020\007\"\004\b\000\020\016*\030\b\001\022\n\022\b\022\004\022\002H\0160\t\022\006\022\004\030\0010\f0\0172\f\020\b\032\b\022\004\022\002H\0160\tH\007?\001\000?\006\002\020\031\032W\020\030\032\0020\007\"\004\b\000\020\023\"\004\b\001\020\016*#\b\001\022\004\022\002H\023\022\n\022\b\022\004\022\002H\0160\t\022\006\022\004\030\0010\f0\024?\006\002\b\0252\006\020\026\032\002H\0232\f\020\b\032\b\022\004\022\002H\0160\tH\007?\001\000?\006\002\020\032\"\033\020\000\032\0020\0018?\002X?\004?\006\f\022\004\b\002\020\003\032\004\b\004\020\005?\002\004\n\002\b\t?\006\033"}, d2={"coroutineContext", "Lkotlin/coroutines/experimental/CoroutineContext;", "coroutineContext$annotations", "()V", "getCoroutineContext", "()Lkotlin/coroutines/experimental/CoroutineContext;", "processBareContinuationResume", "", "completion", "Lkotlin/coroutines/experimental/Continuation;", "block", "Lkotlin/Function0;", "", "suspendCoroutine", "T", "Lkotlin/Function1;", "(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/experimental/Continuation;)Ljava/lang/Object;", "createCoroutine", "(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/experimental/Continuation;)Lkotlin/coroutines/experimental/Continuation;", "R", "Lkotlin/Function2;", "Lkotlin/ExtensionFunctionType;", "receiver", "(Lkotlin/jvm/functions/Function2;Ljava/lang/Object;Lkotlin/coroutines/experimental/Continuation;)Lkotlin/coroutines/experimental/Continuation;", "startCoroutine", "(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/experimental/Continuation;)V", "(Lkotlin/jvm/functions/Function2;Ljava/lang/Object;Lkotlin/coroutines/experimental/Continuation;)V", "kotlin-stdlib-coroutines"}, k=2, mv={1, 1, 15})
public final class CoroutinesKt
{
  public static final Continuation createCoroutine(Function1 paramFunction1, Continuation paramContinuation)
  {
    Intrinsics.checkParameterIsNotNull(paramFunction1, "$this$createCoroutine");
    Intrinsics.checkParameterIsNotNull(paramContinuation, "completion");
    return (Continuation)new SafeContinuation(IntrinsicsKt__IntrinsicsJvmKt.createCoroutineUnchecked(paramFunction1, paramContinuation), IntrinsicsKt__IntrinsicsJvmKt.getCOROUTINE_SUSPENDED());
  }
  
  public static final Continuation createCoroutine(Function2 paramFunction2, Object paramObject, Continuation paramContinuation)
  {
    Intrinsics.checkParameterIsNotNull(paramFunction2, "$this$createCoroutine");
    Intrinsics.checkParameterIsNotNull(paramContinuation, "completion");
    return (Continuation)new SafeContinuation(IntrinsicsKt__IntrinsicsJvmKt.createCoroutineUnchecked(paramFunction2, paramObject, paramContinuation), IntrinsicsKt__IntrinsicsJvmKt.getCOROUTINE_SUSPENDED());
  }
  
  private static final CoroutineContext getCoroutineContext()
  {
    throw ((Throwable)new NotImplementedError("Implemented as intrinsic"));
  }
  
  private static final void processBareContinuationResume(Continuation paramContinuation, Function0 paramFunction0)
  {
    try
    {
      paramFunction0 = paramFunction0.invoke();
      Object localObject = IntrinsicsKt__IntrinsicsJvmKt.getCOROUTINE_SUSPENDED();
      if (paramFunction0 != localObject)
      {
        if (paramContinuation != null)
        {
          paramContinuation.resume(paramFunction0);
          return;
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.experimental.Continuation<kotlin.Any?>");
      }
    }
    catch (Throwable paramFunction0)
    {
      paramContinuation.resumeWithException(paramFunction0);
    }
  }
  
  public static final void startCoroutine(Function1 paramFunction1, Continuation paramContinuation)
  {
    Intrinsics.checkParameterIsNotNull(paramFunction1, "$this$startCoroutine");
    Intrinsics.checkParameterIsNotNull(paramContinuation, "completion");
    IntrinsicsKt__IntrinsicsJvmKt.createCoroutineUnchecked(paramFunction1, paramContinuation).resume(Unit.INSTANCE);
  }
  
  public static final void startCoroutine(Function2 paramFunction2, Object paramObject, Continuation paramContinuation)
  {
    Intrinsics.checkParameterIsNotNull(paramFunction2, "$this$startCoroutine");
    Intrinsics.checkParameterIsNotNull(paramContinuation, "completion");
    IntrinsicsKt__IntrinsicsJvmKt.createCoroutineUnchecked(paramFunction2, paramObject, paramContinuation).resume(Unit.INSTANCE);
  }
  
  public static final Object suspendCoroutine(Function1 paramFunction1, Continuation paramContinuation)
  {
    paramContinuation = new SafeContinuation(CoroutineIntrinsics.normalizeContinuation(paramContinuation));
    paramFunction1.invoke(paramContinuation);
    return paramContinuation.getResult();
  }
  
  private static final Object suspendCoroutine$$forInline(Function1 paramFunction1, Continuation paramContinuation)
  {
    InlineMarker.mark(0);
    paramContinuation = new SafeContinuation(CoroutineIntrinsics.normalizeContinuation(paramContinuation));
    paramFunction1.invoke(paramContinuation);
    paramFunction1 = paramContinuation.getResult();
    InlineMarker.mark(1);
    return paramFunction1;
  }
}
