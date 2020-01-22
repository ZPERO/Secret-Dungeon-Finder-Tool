package kotlin.coroutines.experimental;

import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000\022\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\004\bg\030\000 \0062\0020\001:\001\006J\"\020\002\032\b\022\004\022\002H\0040\003\"\004\b\000\020\0042\f\020\005\032\b\022\004\022\002H\0040\003H&?\006\007"}, d2={"Lkotlin/coroutines/experimental/ContinuationInterceptor;", "Lkotlin/coroutines/experimental/CoroutineContext$Element;", "interceptContinuation", "Lkotlin/coroutines/experimental/Continuation;", "T", "continuation", "Key", "kotlin-stdlib-coroutines"}, k=1, mv={1, 1, 15})
public abstract interface ContinuationInterceptor
  extends CoroutineContext.Element
{
  public static final Key this$0 = Key.$$INSTANCE;
  
  public abstract Continuation interceptContinuation(Continuation paramContinuation);
  
  @Metadata(bv={1, 0, 3}, k=3, mv={1, 1, 15})
  public static final class DefaultImpls
  {
    public static Object fold(ContinuationInterceptor paramContinuationInterceptor, Object paramObject, Function2 paramFunction2)
    {
      Intrinsics.checkParameterIsNotNull(paramFunction2, "operation");
      return CoroutineContext.Element.DefaultImpls.fold((CoroutineContext.Element)paramContinuationInterceptor, paramObject, paramFunction2);
    }
    
    public static CoroutineContext.Element getKey(ContinuationInterceptor paramContinuationInterceptor, CoroutineContext.Key paramKey)
    {
      Intrinsics.checkParameterIsNotNull(paramKey, "key");
      return CoroutineContext.Element.DefaultImpls.getValue((CoroutineContext.Element)paramContinuationInterceptor, paramKey);
    }
    
    public static CoroutineContext minusKey(ContinuationInterceptor paramContinuationInterceptor, CoroutineContext.Key paramKey)
    {
      Intrinsics.checkParameterIsNotNull(paramKey, "key");
      return CoroutineContext.Element.DefaultImpls.minusKey((CoroutineContext.Element)paramContinuationInterceptor, paramKey);
    }
    
    public static CoroutineContext plus(ContinuationInterceptor paramContinuationInterceptor, CoroutineContext paramCoroutineContext)
    {
      Intrinsics.checkParameterIsNotNull(paramCoroutineContext, "context");
      return CoroutineContext.Element.DefaultImpls.plus((CoroutineContext.Element)paramContinuationInterceptor, paramCoroutineContext);
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\020\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\b?\003\030\0002\b\022\004\022\0020\0020\001B\007\b\002?\006\002\020\003?\006\004"}, d2={"Lkotlin/coroutines/experimental/ContinuationInterceptor$Key;", "Lkotlin/coroutines/experimental/CoroutineContext$Key;", "Lkotlin/coroutines/experimental/ContinuationInterceptor;", "()V", "kotlin-stdlib-coroutines"}, k=1, mv={1, 1, 15})
  public static final class Key
    implements CoroutineContext.Key<ContinuationInterceptor>
  {
    private Key() {}
  }
}
