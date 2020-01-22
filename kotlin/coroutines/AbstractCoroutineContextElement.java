package kotlin.coroutines;

import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000\022\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\004\b'\030\0002\0020\001B\021\022\n\020\002\032\006\022\002\b\0030\003?\006\002\020\004R\030\020\002\032\006\022\002\b\0030\003X?\004?\006\b\n\000\032\004\b\005\020\006?\006\007"}, d2={"Lkotlin/coroutines/AbstractCoroutineContextElement;", "Lkotlin/coroutines/CoroutineContext$Element;", "key", "Lkotlin/coroutines/CoroutineContext$Key;", "(Lkotlin/coroutines/CoroutineContext$Key;)V", "getKey", "()Lkotlin/coroutines/CoroutineContext$Key;", "kotlin-stdlib"}, k=1, mv={1, 1, 15})
public abstract class AbstractCoroutineContextElement
  implements CoroutineContext.Element
{
  private final CoroutineContext.Key<?> pair;
  
  public AbstractCoroutineContextElement(CoroutineContext.Key paramKey)
  {
    pair = paramKey;
  }
  
  public Object fold(Object paramObject, Function2 paramFunction2)
  {
    Intrinsics.checkParameterIsNotNull(paramFunction2, "operation");
    return CoroutineContext.Element.DefaultImpls.fold(this, paramObject, paramFunction2);
  }
  
  public CoroutineContext.Key getKey()
  {
    return pair;
  }
  
  public CoroutineContext minusKey(CoroutineContext.Key paramKey)
  {
    Intrinsics.checkParameterIsNotNull(paramKey, "key");
    return CoroutineContext.Element.DefaultImpls.minusKey(this, paramKey);
  }
  
  public CoroutineContext plus(CoroutineContext paramCoroutineContext)
  {
    Intrinsics.checkParameterIsNotNull(paramCoroutineContext, "context");
    return CoroutineContext.Element.DefaultImpls.plus(this, paramCoroutineContext);
  }
  
  public CoroutineContext.Element remove(CoroutineContext.Key paramKey)
  {
    Intrinsics.checkParameterIsNotNull(paramKey, "key");
    return CoroutineContext.Element.DefaultImpls.getValue(this, paramKey);
  }
}
