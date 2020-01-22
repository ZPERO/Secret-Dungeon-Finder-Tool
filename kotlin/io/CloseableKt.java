package kotlin.io;

import java.io.Closeable;
import kotlin.ExceptionsKt__ExceptionsKt;
import kotlin.Metadata;
import kotlin.internal.PlatformImplementationsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.InlineMarker;

@Metadata(bv={1, 0, 3}, d1={"\000\034\n\000\n\002\020\002\n\002\030\002\n\000\n\002\020\003\n\002\b\004\n\002\030\002\n\002\b\003\032\030\020\000\032\0020\001*\004\030\0010\0022\b\020\003\032\004\030\0010\004H\001\032;\020\005\032\002H\006\"\n\b\000\020\007*\004\030\0010\002\"\004\b\001\020\006*\002H\0072\022\020\b\032\016\022\004\022\002H\007\022\004\022\002H\0060\tH?\b?\001\000?\006\002\020\013?\002\b\n\006\b\021(\n0\001?\006\f"}, d2={"closeFinally", "", "Ljava/io/Closeable;", "cause", "", "use", "R", "T", "block", "Lkotlin/Function1;", "Requires newer compiler version to be inlined correctly.", "(Ljava/io/Closeable;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "kotlin-stdlib"}, k=2, mv={1, 1, 15})
public final class CloseableKt
{
  public static final void closeFinally(Closeable paramCloseable, Throwable paramThrowable)
  {
    if (paramCloseable == null) {
      return;
    }
    if (paramThrowable == null)
    {
      paramCloseable.close();
      return;
    }
    try
    {
      paramCloseable.close();
      return;
    }
    catch (Throwable paramCloseable)
    {
      ExceptionsKt__ExceptionsKt.addSuppressed(paramThrowable, paramCloseable);
    }
  }
  
  private static final Object use(Closeable paramCloseable, Function1 paramFunction1)
  {
    try
    {
      paramFunction1 = paramFunction1.invoke(paramCloseable);
      InlineMarker.finallyStart(1);
      if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 1, 0)) {
        closeFinally(paramCloseable, null);
      } else if (paramCloseable != null) {
        paramCloseable.close();
      }
      InlineMarker.finallyEnd(1);
      return paramFunction1;
    }
    catch (Throwable localThrowable)
    {
      try
      {
        throw localThrowable;
      }
      catch (Throwable paramFunction1)
      {
        InlineMarker.finallyStart(1);
        if ((PlatformImplementationsKt.apiVersionIsAtLeast(1, 1, 0)) || (paramCloseable != null)) {}
        try
        {
          paramCloseable.close();
        }
        catch (Throwable paramCloseable)
        {
          for (;;) {}
        }
        closeFinally(paramCloseable, localThrowable);
      }
    }
    InlineMarker.finallyEnd(1);
    throw paramFunction1;
  }
}