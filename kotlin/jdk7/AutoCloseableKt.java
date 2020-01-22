package kotlin.jdk7;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.InlineMarker;

@Metadata(bv={1, 0, 3}, d1={"\000\034\n\000\n\002\020\002\n\002\030\002\n\000\n\002\020\003\n\002\b\004\n\002\030\002\n\002\b\002\032\030\020\000\032\0020\001*\004\030\0010\0022\b\020\003\032\004\030\0010\004H\001\0328\020\005\032\002H\006\"\n\b\000\020\007*\004\030\0010\002\"\004\b\001\020\006*\002H\0072\022\020\b\032\016\022\004\022\002H\007\022\004\022\002H\0060\tH?\b?\006\002\020\n?\006\013"}, d2={"closeFinally", "", "Ljava/lang/AutoCloseable;", "cause", "", "use", "R", "T", "block", "Lkotlin/Function1;", "(Ljava/lang/AutoCloseable;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "kotlin-stdlib-jdk7"}, k=2, mv={1, 1, 15}, pn="kotlin")
public final class AutoCloseableKt
{
  public static final void closeFinally(AutoCloseable paramAutoCloseable, Throwable paramThrowable)
  {
    if (paramAutoCloseable == null) {
      return;
    }
    if (paramThrowable == null)
    {
      paramAutoCloseable.close();
      return;
    }
    try
    {
      paramAutoCloseable.close();
      return;
    }
    catch (Throwable paramAutoCloseable)
    {
      paramThrowable.addSuppressed(paramAutoCloseable);
    }
  }
  
  private static final Object use(AutoCloseable paramAutoCloseable, Function1 paramFunction1)
  {
    try
    {
      paramFunction1 = paramFunction1.invoke(paramAutoCloseable);
      InlineMarker.finallyStart(1);
      closeFinally(paramAutoCloseable, null);
      InlineMarker.finallyEnd(1);
      return paramFunction1;
    }
    catch (Throwable paramFunction1)
    {
      try
      {
        throw paramFunction1;
      }
      catch (Throwable localThrowable)
      {
        InlineMarker.finallyStart(1);
        closeFinally(paramAutoCloseable, paramFunction1);
        InlineMarker.finallyEnd(1);
        throw localThrowable;
      }
    }
  }
}
