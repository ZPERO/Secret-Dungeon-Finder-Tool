package androidx.core.os;

import android.os.Binder;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.util.Size;
import android.util.SizeF;
import java.io.Serializable;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000\034\n\000\n\002\030\002\n\000\n\002\020\021\n\002\030\002\n\002\020\016\n\002\020\000\n\002\b\002\032;\020\000\032\0020\0012.\020\002\032\030\022\024\b\001\022\020\022\004\022\0020\005\022\006\022\004\030\0010\0060\0040\003\"\020\022\004\022\0020\005\022\006\022\004\030\0010\0060\004?\006\002\020\007?\006\b"}, d2={"bundleOf", "Landroid/os/Bundle;", "pairs", "", "Lkotlin/Pair;", "", "", "([Lkotlin/Pair;)Landroid/os/Bundle;", "core-ktx_release"}, k=2, mv={1, 1, 15})
public final class BundleKt
{
  public static final Bundle bundleOf(Pair... paramVarArgs)
  {
    Intrinsics.checkParameterIsNotNull(paramVarArgs, "pairs");
    Object localObject1 = new Bundle(paramVarArgs.length);
    int j = paramVarArgs.length;
    int i = 0;
    while (i < j)
    {
      Object localObject2 = paramVarArgs[i];
      String str = (String)((Pair)localObject2).component1();
      localObject2 = ((Pair)localObject2).component2();
      if (localObject2 == null)
      {
        ((Bundle)localObject1).putString(str, null);
      }
      else if ((localObject2 instanceof Boolean))
      {
        ((Bundle)localObject1).putBoolean(str, ((Boolean)localObject2).booleanValue());
      }
      else if ((localObject2 instanceof Byte))
      {
        ((Bundle)localObject1).putByte(str, ((Number)localObject2).byteValue());
      }
      else if ((localObject2 instanceof Character))
      {
        ((Bundle)localObject1).putChar(str, ((Character)localObject2).charValue());
      }
      else if ((localObject2 instanceof Double))
      {
        ((Bundle)localObject1).putDouble(str, ((Number)localObject2).doubleValue());
      }
      else if ((localObject2 instanceof Float))
      {
        ((Bundle)localObject1).putFloat(str, ((Number)localObject2).floatValue());
      }
      else if ((localObject2 instanceof Integer))
      {
        ((Bundle)localObject1).putInt(str, ((Number)localObject2).intValue());
      }
      else if ((localObject2 instanceof Long))
      {
        ((Bundle)localObject1).putLong(str, ((Number)localObject2).longValue());
      }
      else if ((localObject2 instanceof Short))
      {
        ((Bundle)localObject1).putShort(str, ((Number)localObject2).shortValue());
      }
      else if ((localObject2 instanceof Bundle))
      {
        ((Bundle)localObject1).putBundle(str, (Bundle)localObject2);
      }
      else if ((localObject2 instanceof CharSequence))
      {
        ((Bundle)localObject1).putCharSequence(str, (CharSequence)localObject2);
      }
      else if ((localObject2 instanceof Parcelable))
      {
        ((Bundle)localObject1).putParcelable(str, (Parcelable)localObject2);
      }
      else if ((localObject2 instanceof boolean[]))
      {
        ((Bundle)localObject1).putBooleanArray(str, (boolean[])localObject2);
      }
      else if ((localObject2 instanceof byte[]))
      {
        ((Bundle)localObject1).putByteArray(str, (byte[])localObject2);
      }
      else if ((localObject2 instanceof char[]))
      {
        ((Bundle)localObject1).putCharArray(str, (char[])localObject2);
      }
      else if ((localObject2 instanceof double[]))
      {
        ((Bundle)localObject1).putDoubleArray(str, (double[])localObject2);
      }
      else if ((localObject2 instanceof float[]))
      {
        ((Bundle)localObject1).putFloatArray(str, (float[])localObject2);
      }
      else if ((localObject2 instanceof int[]))
      {
        ((Bundle)localObject1).putIntArray(str, (int[])localObject2);
      }
      else if ((localObject2 instanceof long[]))
      {
        ((Bundle)localObject1).putLongArray(str, (long[])localObject2);
      }
      else if ((localObject2 instanceof short[]))
      {
        ((Bundle)localObject1).putShortArray(str, (short[])localObject2);
      }
      else if ((localObject2 instanceof Object[]))
      {
        Class localClass = localObject2.getClass().getComponentType();
        if (localClass == null) {
          Intrinsics.throwNpe();
        }
        if (Parcelable.class.isAssignableFrom(localClass))
        {
          if (localObject2 != null) {
            ((Bundle)localObject1).putParcelableArray(str, (Parcelable[])localObject2);
          } else {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<android.os.Parcelable>");
          }
        }
        else if (String.class.isAssignableFrom(localClass))
        {
          if (localObject2 != null) {
            ((Bundle)localObject1).putStringArray(str, (String[])localObject2);
          } else {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<kotlin.String>");
          }
        }
        else if (CharSequence.class.isAssignableFrom(localClass))
        {
          if (localObject2 != null) {
            ((Bundle)localObject1).putCharSequenceArray(str, (CharSequence[])localObject2);
          } else {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<kotlin.CharSequence>");
          }
        }
        else if (Serializable.class.isAssignableFrom(localClass))
        {
          ((Bundle)localObject1).putSerializable(str, (Serializable)localObject2);
        }
        else
        {
          paramVarArgs = localClass.getCanonicalName();
          localObject1 = new StringBuilder();
          ((StringBuilder)localObject1).append("Illegal value array type ");
          ((StringBuilder)localObject1).append(paramVarArgs);
          ((StringBuilder)localObject1).append(" for key \"");
          ((StringBuilder)localObject1).append(str);
          ((StringBuilder)localObject1).append('"');
          throw ((Throwable)new IllegalArgumentException(((StringBuilder)localObject1).toString()));
        }
      }
      else if ((localObject2 instanceof Serializable))
      {
        ((Bundle)localObject1).putSerializable(str, (Serializable)localObject2);
      }
      else if ((Build.VERSION.SDK_INT >= 18) && ((localObject2 instanceof Binder)))
      {
        ((Bundle)localObject1).putBinder(str, (IBinder)localObject2);
      }
      else if ((Build.VERSION.SDK_INT >= 21) && ((localObject2 instanceof Size)))
      {
        ((Bundle)localObject1).putSize(str, (Size)localObject2);
      }
      else
      {
        if ((Build.VERSION.SDK_INT < 21) || (!(localObject2 instanceof SizeF))) {
          break label859;
        }
        ((Bundle)localObject1).putSizeF(str, (SizeF)localObject2);
      }
      i += 1;
      continue;
      label859:
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