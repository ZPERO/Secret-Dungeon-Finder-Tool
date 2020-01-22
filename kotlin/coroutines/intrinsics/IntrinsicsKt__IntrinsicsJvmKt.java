package kotlin.coroutines.intrinsics;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.TypeCastException;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.stats.internal.BaseContinuationImpl;
import kotlin.coroutines.stats.internal.ContinuationImpl;
import kotlin.coroutines.stats.internal.DebugProbesKt;
import kotlin.coroutines.stats.internal.RestrictedContinuationImpl;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000&\n\000\n\002\030\002\n\002\020\002\n\002\b\003\n\002\030\002\n\002\020\000\n\002\b\004\n\002\030\002\n\002\030\002\n\002\b\007\032F\020\000\032\b\022\004\022\0020\0020\001\"\004\b\000\020\0032\f\020\004\032\b\022\004\022\002H\0030\0012\034\b\004\020\005\032\026\022\n\022\b\022\004\022\002H\0030\001\022\006\022\004\030\0010\0070\006H?\b?\006\002\b\b\032D\020\t\032\b\022\004\022\0020\0020\001\"\004\b\000\020\003*\030\b\001\022\n\022\b\022\004\022\002H\0030\001\022\006\022\004\030\0010\0070\0062\f\020\004\032\b\022\004\022\002H\0030\001H\007?\001\000?\006\002\020\n\032]\020\t\032\b\022\004\022\0020\0020\001\"\004\b\000\020\013\"\004\b\001\020\003*#\b\001\022\004\022\002H\013\022\n\022\b\022\004\022\002H\0030\001\022\006\022\004\030\0010\0070\f?\006\002\b\r2\006\020\016\032\002H\0132\f\020\004\032\b\022\004\022\002H\0030\001H\007?\001\000?\006\002\020\017\032\036\020\020\032\b\022\004\022\002H\0030\001\"\004\b\000\020\003*\b\022\004\022\002H\0030\001H\007\032A\020\021\032\004\030\0010\007\"\004\b\000\020\003*\030\b\001\022\n\022\b\022\004\022\002H\0030\001\022\006\022\004\030\0010\0070\0062\f\020\004\032\b\022\004\022\002H\0030\001H?\b?\001\000?\006\002\020\022\032Z\020\021\032\004\030\0010\007\"\004\b\000\020\013\"\004\b\001\020\003*#\b\001\022\004\022\002H\013\022\n\022\b\022\004\022\002H\0030\001\022\006\022\004\030\0010\0070\f?\006\002\b\r2\006\020\016\032\002H\0132\f\020\004\032\b\022\004\022\002H\0030\001H?\b?\001\000?\006\002\020\023?\002\004\n\002\b\031?\006\024"}, d2={"createCoroutineFromSuspendFunction", "Lkotlin/coroutines/Continuation;", "", "T", "completion", "block", "Lkotlin/Function1;", "", "createCoroutineFromSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt", "createCoroutineUnintercepted", "(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Lkotlin/coroutines/Continuation;", "R", "Lkotlin/Function2;", "Lkotlin/ExtensionFunctionType;", "receiver", "(Lkotlin/jvm/functions/Function2;Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Lkotlin/coroutines/Continuation;", "intercepted", "startCoroutineUninterceptedOrReturn", "(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "(Lkotlin/jvm/functions/Function2;Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlin-stdlib"}, k=5, mv={1, 1, 15}, xi=1, xs="kotlin/coroutines/intrinsics/IntrinsicsKt")
class IntrinsicsKt__IntrinsicsJvmKt
{
  public IntrinsicsKt__IntrinsicsJvmKt() {}
  
  private static final Continuation createCoroutineFromSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt(final Continuation paramContinuation, Function1 paramFunction1)
  {
    final CoroutineContext localCoroutineContext = paramContinuation.getContext();
    if (localCoroutineContext == EmptyCoroutineContext.INSTANCE)
    {
      if (paramContinuation != null) {
        (Continuation)new RestrictedContinuationImpl(paramFunction1)
        {
          private int label;
          
          protected Object invokeSuspend(Object paramAnonymousObject)
          {
            int i = label;
            if (i != 0)
            {
              if (i == 1)
              {
                label = 2;
                ResultKt.throwOnFailure(paramAnonymousObject);
                return paramAnonymousObject;
              }
              throw ((Throwable)new IllegalStateException("This coroutine had already completed".toString()));
            }
            label = 1;
            ResultKt.throwOnFailure(paramAnonymousObject);
            return invoke(this);
          }
        };
      }
      throw new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.Continuation<kotlin.Any?>");
    }
    if (paramContinuation != null) {
      (Continuation)new ContinuationImpl(paramFunction1, paramContinuation)
      {
        private int label;
        
        protected Object invokeSuspend(Object paramAnonymousObject)
        {
          int i = label;
          if (i != 0)
          {
            if (i == 1)
            {
              label = 2;
              ResultKt.throwOnFailure(paramAnonymousObject);
              return paramAnonymousObject;
            }
            throw ((Throwable)new IllegalStateException("This coroutine had already completed".toString()));
          }
          label = 1;
          ResultKt.throwOnFailure(paramAnonymousObject);
          return invoke(this);
        }
      };
    }
    throw new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.Continuation<kotlin.Any?>");
  }
  
  public static final Continuation createCoroutineUnintercepted(final Function1 paramFunction1, Continuation paramContinuation)
  {
    Intrinsics.checkParameterIsNotNull(paramFunction1, "$this$createCoroutineUnintercepted");
    Intrinsics.checkParameterIsNotNull(paramContinuation, "completion");
    paramContinuation = DebugProbesKt.probeCoroutineCreated(paramContinuation);
    if ((paramFunction1 instanceof BaseContinuationImpl)) {
      return ((BaseContinuationImpl)paramFunction1).create(paramContinuation);
    }
    final CoroutineContext localCoroutineContext = paramContinuation.getContext();
    if (localCoroutineContext == EmptyCoroutineContext.INSTANCE)
    {
      if (paramContinuation != null) {
        (Continuation)new RestrictedContinuationImpl(paramContinuation)
        {
          private int label;
          
          protected Object invokeSuspend(Object paramAnonymousObject)
          {
            int i = label;
            if (i != 0)
            {
              if (i == 1)
              {
                label = 2;
                ResultKt.throwOnFailure(paramAnonymousObject);
                return paramAnonymousObject;
              }
              throw ((Throwable)new IllegalStateException("This coroutine had already completed".toString()));
            }
            label = 1;
            ResultKt.throwOnFailure(paramAnonymousObject);
            paramAnonymousObject = (Continuation)this;
            Function1 localFunction1 = paramFunction1;
            if (localFunction1 != null) {
              return ((Function1)TypeIntrinsics.beforeCheckcastToFunctionOfArity(localFunction1, 1)).invoke(paramAnonymousObject);
            }
            throw new TypeCastException("null cannot be cast to non-null type (kotlin.coroutines.Continuation<T>) -> kotlin.Any?");
          }
        };
      }
      throw new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.Continuation<kotlin.Any?>");
    }
    if (paramContinuation != null) {
      (Continuation)new ContinuationImpl(paramContinuation, localCoroutineContext)
      {
        private int label;
        
        protected Object invokeSuspend(Object paramAnonymousObject)
        {
          int i = label;
          if (i != 0)
          {
            if (i == 1)
            {
              label = 2;
              ResultKt.throwOnFailure(paramAnonymousObject);
              return paramAnonymousObject;
            }
            throw ((Throwable)new IllegalStateException("This coroutine had already completed".toString()));
          }
          label = 1;
          ResultKt.throwOnFailure(paramAnonymousObject);
          paramAnonymousObject = (Continuation)this;
          Function1 localFunction1 = paramFunction1;
          if (localFunction1 != null) {
            return ((Function1)TypeIntrinsics.beforeCheckcastToFunctionOfArity(localFunction1, 1)).invoke(paramAnonymousObject);
          }
          throw new TypeCastException("null cannot be cast to non-null type (kotlin.coroutines.Continuation<T>) -> kotlin.Any?");
        }
      };
    }
    throw new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.Continuation<kotlin.Any?>");
  }
  
  public static final Continuation createCoroutineUnintercepted(final Function2 paramFunction2, final Object paramObject, Continuation paramContinuation)
  {
    Intrinsics.checkParameterIsNotNull(paramFunction2, "$this$createCoroutineUnintercepted");
    Intrinsics.checkParameterIsNotNull(paramContinuation, "completion");
    paramContinuation = DebugProbesKt.probeCoroutineCreated(paramContinuation);
    if ((paramFunction2 instanceof BaseContinuationImpl)) {
      return ((BaseContinuationImpl)paramFunction2).create(paramObject, paramContinuation);
    }
    final CoroutineContext localCoroutineContext = paramContinuation.getContext();
    if (localCoroutineContext == EmptyCoroutineContext.INSTANCE)
    {
      if (paramContinuation != null) {
        (Continuation)new RestrictedContinuationImpl(paramContinuation)
        {
          private int label;
          
          protected Object invokeSuspend(Object paramAnonymousObject)
          {
            int i = label;
            if (i != 0)
            {
              if (i == 1)
              {
                label = 2;
                ResultKt.throwOnFailure(paramAnonymousObject);
                return paramAnonymousObject;
              }
              throw ((Throwable)new IllegalStateException("This coroutine had already completed".toString()));
            }
            label = 1;
            ResultKt.throwOnFailure(paramAnonymousObject);
            paramAnonymousObject = (Continuation)this;
            Function2 localFunction2 = paramFunction2;
            if (localFunction2 != null) {
              return ((Function2)TypeIntrinsics.beforeCheckcastToFunctionOfArity(localFunction2, 2)).invoke(paramObject, paramAnonymousObject);
            }
            throw new TypeCastException("null cannot be cast to non-null type (R, kotlin.coroutines.Continuation<T>) -> kotlin.Any?");
          }
        };
      }
      throw new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.Continuation<kotlin.Any?>");
    }
    if (paramContinuation != null) {
      (Continuation)new ContinuationImpl(paramContinuation, localCoroutineContext)
      {
        private int label;
        
        protected Object invokeSuspend(Object paramAnonymousObject)
        {
          int i = label;
          if (i != 0)
          {
            if (i == 1)
            {
              label = 2;
              ResultKt.throwOnFailure(paramAnonymousObject);
              return paramAnonymousObject;
            }
            throw ((Throwable)new IllegalStateException("This coroutine had already completed".toString()));
          }
          label = 1;
          ResultKt.throwOnFailure(paramAnonymousObject);
          paramAnonymousObject = (Continuation)this;
          Function2 localFunction2 = paramFunction2;
          if (localFunction2 != null) {
            return ((Function2)TypeIntrinsics.beforeCheckcastToFunctionOfArity(localFunction2, 2)).invoke(paramObject, paramAnonymousObject);
          }
          throw new TypeCastException("null cannot be cast to non-null type (R, kotlin.coroutines.Continuation<T>) -> kotlin.Any?");
        }
      };
    }
    throw new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.Continuation<kotlin.Any?>");
  }
  
  public static final Continuation intercepted(Continuation paramContinuation)
  {
    Intrinsics.checkParameterIsNotNull(paramContinuation, "$this$intercepted");
    if (!(paramContinuation instanceof ContinuationImpl)) {
      localObject = null;
    } else {
      localObject = paramContinuation;
    }
    Object localObject = (ContinuationImpl)localObject;
    if (localObject != null)
    {
      localObject = ((ContinuationImpl)localObject).intercepted();
      if (localObject != null) {
        return localObject;
      }
    }
    return paramContinuation;
  }
  
  private static final Object startCoroutineUninterceptedOrReturn(Function1 paramFunction1, Continuation paramContinuation)
  {
    if (paramFunction1 != null) {
      return ((Function1)TypeIntrinsics.beforeCheckcastToFunctionOfArity(paramFunction1, 1)).invoke(paramContinuation);
    }
    throw new TypeCastException("null cannot be cast to non-null type (kotlin.coroutines.Continuation<T>) -> kotlin.Any?");
  }
  
  private static final Object startCoroutineUninterceptedOrReturn(Function2 paramFunction2, Object paramObject, Continuation paramContinuation)
  {
    if (paramFunction2 != null) {
      return ((Function2)TypeIntrinsics.beforeCheckcastToFunctionOfArity(paramFunction2, 2)).invoke(paramObject, paramContinuation);
    }
    throw new TypeCastException("null cannot be cast to non-null type (R, kotlin.coroutines.Continuation<T>) -> kotlin.Any?");
  }
}
