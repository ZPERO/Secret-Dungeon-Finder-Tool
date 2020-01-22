package kotlin;

import kotlin.jvm.functions.Function0;

@Metadata(bv={1, 0, 3}, d1={"\000\"\n\000\n\002\020\002\n\000\n\002\020\013\n\000\n\002\030\002\n\002\020\000\n\002\b\005\n\002\020\001\n\002\b\004\032\034\020\000\032\0020\0012\006\020\002\032\0020\003H?\b?\002\b\n\006\b\000\032\002\020\001\032*\020\000\032\0020\0012\006\020\002\032\0020\0032\f\020\004\032\b\022\004\022\0020\0060\005H?\b?\002\b\n\006\b\000\032\002\020\001\032/\020\007\032\002H\b\"\b\b\000\020\b*\0020\0062\b\020\002\032\004\030\001H\bH?\b?\002\n\n\b\b\000\032\004\b\003\020\001?\006\002\020\t\032=\020\007\032\002H\b\"\b\b\000\020\b*\0020\0062\b\020\002\032\004\030\001H\b2\f\020\004\032\b\022\004\022\0020\0060\005H?\b?\002\n\n\b\b\000\032\004\b\003\020\001?\006\002\020\n\032\021\020\013\032\0020\f2\006\020\r\032\0020\006H?\b\032\034\020\016\032\0020\0012\006\020\002\032\0020\003H?\b?\002\b\n\006\b\000\032\002\020\001\032*\020\016\032\0020\0012\006\020\002\032\0020\0032\f\020\004\032\b\022\004\022\0020\0060\005H?\b?\002\b\n\006\b\000\032\002\020\001\032/\020\017\032\002H\b\"\b\b\000\020\b*\0020\0062\b\020\002\032\004\030\001H\bH?\b?\002\n\n\b\b\000\032\004\b\003\020\001?\006\002\020\t\032=\020\017\032\002H\b\"\b\b\000\020\b*\0020\0062\b\020\002\032\004\030\001H\b2\f\020\004\032\b\022\004\022\0020\0060\005H?\b?\002\n\n\b\b\000\032\004\b\003\020\001?\006\002\020\n?\006\020"}, d2={"check", "", "value", "", "lazyMessage", "Lkotlin/Function0;", "", "checkNotNull", "T", "(Ljava/lang/Object;)Ljava/lang/Object;", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "error", "", "message", "require", "requireNotNull", "kotlin-stdlib"}, k=5, mv={1, 1, 15}, xi=1, xs="kotlin/PreconditionsKt")
class PreconditionsKt__PreconditionsKt
  extends PreconditionsKt__AssertionsJVMKt
{
  public PreconditionsKt__PreconditionsKt() {}
  
  private static final void check(boolean paramBoolean)
  {
    if (paramBoolean) {
      return;
    }
    throw ((Throwable)new IllegalStateException("Check failed.".toString()));
  }
  
  private static final void check(boolean paramBoolean, Function0 paramFunction0)
  {
    if (paramBoolean) {
      return;
    }
    throw ((Throwable)new IllegalStateException(paramFunction0.invoke().toString()));
  }
  
  private static final Object checkNotNull(Object paramObject)
  {
    if (paramObject != null) {
      return paramObject;
    }
    throw ((Throwable)new IllegalStateException("Required value was null.".toString()));
  }
  
  private static final Object checkNotNull(Object paramObject, Function0 paramFunction0)
  {
    if (paramObject != null) {
      return paramObject;
    }
    throw ((Throwable)new IllegalStateException(paramFunction0.invoke().toString()));
  }
  
  private static final Void error(Object paramObject)
  {
    throw ((Throwable)new IllegalStateException(paramObject.toString()));
  }
  
  private static final void require(boolean paramBoolean)
  {
    if (paramBoolean) {
      return;
    }
    throw ((Throwable)new IllegalArgumentException("Failed requirement.".toString()));
  }
  
  private static final void require(boolean paramBoolean, Function0 paramFunction0)
  {
    if (paramBoolean) {
      return;
    }
    throw ((Throwable)new IllegalArgumentException(paramFunction0.invoke().toString()));
  }
  
  private static final Object requireNotNull(Object paramObject)
  {
    if (paramObject != null) {
      return paramObject;
    }
    throw ((Throwable)new IllegalArgumentException("Required value was null.".toString()));
  }
  
  private static final Object requireNotNull(Object paramObject, Function0 paramFunction0)
  {
    if (paramObject != null) {
      return paramObject;
    }
    throw ((Throwable)new IllegalArgumentException(paramFunction0.invoke().toString()));
  }
}
