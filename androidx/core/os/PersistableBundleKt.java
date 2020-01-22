package androidx.core.os;

import android.os.Build.VERSION;
import android.os.PersistableBundle;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000\034\n\000\n\002\030\002\n\000\n\002\020\021\n\002\030\002\n\002\020\016\n\002\020\000\n\002\b\002\032=\020\000\032\0020\0012.\020\002\032\030\022\024\b\001\022\020\022\004\022\0020\005\022\006\022\004\030\0010\0060\0040\003\"\020\022\004\022\0020\005\022\006\022\004\030\0010\0060\004H\007?\006\002\020\007?\006\b"}, d2={"persistableBundleOf", "Landroid/os/PersistableBundle;", "pairs", "", "Lkotlin/Pair;", "", "", "([Lkotlin/Pair;)Landroid/os/PersistableBundle;", "core-ktx_release"}, k=2, mv={1, 1, 15})
public final class PersistableBundleKt
{
  public static final PersistableBundle persistableBundleOf(Pair... paramVarArgs)
  {
    Intrinsics.checkParameterIsNotNull(paramVarArgs, "pairs");
    Object localObject1 = new PersistableBundle(paramVarArgs.length);
    int j = paramVarArgs.length;
    int i = 0;
    while (i < j)
    {
      Object localObject2 = paramVarArgs[i];
      String str = (String)((Pair)localObject2).component1();
      localObject2 = ((Pair)localObject2).component2();
      Class localClass;
      if (localObject2 == null)
      {
        ((PersistableBundle)localObject1).putString(str, null);
      }
      else if ((localObject2 instanceof Boolean))
      {
        if (Build.VERSION.SDK_INT >= 22)
        {
          ((PersistableBundle)localObject1).putBoolean(str, ((Boolean)localObject2).booleanValue());
        }
        else
        {
          paramVarArgs = new StringBuilder();
          paramVarArgs.append("Illegal value type boolean for key \"");
          paramVarArgs.append(str);
          paramVarArgs.append('"');
          throw ((Throwable)new IllegalArgumentException(paramVarArgs.toString()));
        }
      }
      else if ((localObject2 instanceof Double))
      {
        ((PersistableBundle)localObject1).putDouble(str, ((Number)localObject2).doubleValue());
      }
      else if ((localObject2 instanceof Integer))
      {
        ((PersistableBundle)localObject1).putInt(str, ((Number)localObject2).intValue());
      }
      else if ((localObject2 instanceof Long))
      {
        ((PersistableBundle)localObject1).putLong(str, ((Number)localObject2).longValue());
      }
      else if ((localObject2 instanceof String))
      {
        ((PersistableBundle)localObject1).putString(str, (String)localObject2);
      }
      else if ((localObject2 instanceof boolean[]))
      {
        if (Build.VERSION.SDK_INT >= 22)
        {
          ((PersistableBundle)localObject1).putBooleanArray(str, (boolean[])localObject2);
        }
        else
        {
          paramVarArgs = new StringBuilder();
          paramVarArgs.append("Illegal value type boolean[] for key \"");
          paramVarArgs.append(str);
          paramVarArgs.append('"');
          throw ((Throwable)new IllegalArgumentException(paramVarArgs.toString()));
        }
      }
      else if ((localObject2 instanceof double[]))
      {
        ((PersistableBundle)localObject1).putDoubleArray(str, (double[])localObject2);
      }
      else if ((localObject2 instanceof int[]))
      {
        ((PersistableBundle)localObject1).putIntArray(str, (int[])localObject2);
      }
      else if ((localObject2 instanceof long[]))
      {
        ((PersistableBundle)localObject1).putLongArray(str, (long[])localObject2);
      }
      else
      {
        if (!(localObject2 instanceof Object[])) {
          break label513;
        }
        localClass = localObject2.getClass().getComponentType();
        if (localClass == null) {
          Intrinsics.throwNpe();
        }
        if (!String.class.isAssignableFrom(localClass)) {
          break label444;
        }
        if (localObject2 == null) {
          break label434;
        }
        ((PersistableBundle)localObject1).putStringArray(str, (String[])localObject2);
      }
      i += 1;
      continue;
      label434:
      throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<kotlin.String>");
      label444:
      paramVarArgs = localClass.getCanonicalName();
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("Illegal value array type ");
      ((StringBuilder)localObject1).append(paramVarArgs);
      ((StringBuilder)localObject1).append(" for key \"");
      ((StringBuilder)localObject1).append(str);
      ((StringBuilder)localObject1).append('"');
      throw ((Throwable)new IllegalArgumentException(((StringBuilder)localObject1).toString()));
      label513:
      paramVarArgs = localObject2.getClass().getCanonicalName();
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("Illegal value type ");
      ((StringBuilder)localObject1).append(paramVarArgs);
      ((StringBuilder)localObject1).append(" for key \"");
      ((StringBuilder)localObject1).append(str);
      ((StringBuilder)localObject1).append('"');
      throw ((Throwable)new IllegalArgumentException(((StringBuilder)localObject1).toString()));
    }
    return localObject1;
  }
}
