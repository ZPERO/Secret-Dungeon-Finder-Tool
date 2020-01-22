package kotlin.internal;

import kotlin.KotlinVersion;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

@Metadata(bv={1, 0, 3}, d1={"\000\036\n\000\n\002\030\002\n\000\n\002\020\013\n\000\n\002\020\b\n\002\b\004\n\002\020\000\n\002\b\004\032 \020\002\032\0020\0032\006\020\004\032\0020\0052\006\020\006\032\0020\0052\006\020\007\032\0020\005H\001\032\"\020\b\032\002H\t\"\n\b\000\020\t\030\001*\0020\n2\006\020\013\032\0020\nH?\b?\006\002\020\f\032\b\020\r\032\0020\005H\002\"\020\020\000\032\0020\0018\000X?\004?\006\002\n\000?\006\016"}, d2={"IMPLEMENTATIONS", "Lkotlin/internal/PlatformImplementations;", "apiVersionIsAtLeast", "", "major", "", "minor", "patch", "castToBaseType", "T", "", "instance", "(Ljava/lang/Object;)Ljava/lang/Object;", "getJavaVersion", "kotlin-stdlib"}, k=2, mv={1, 1, 15})
public final class PlatformImplementationsKt
{
  public static final PlatformImplementations IMPLEMENTATIONS;
  
  static
  {
    int i = getJavaVersion();
    if (i >= 65544) {}
    for (;;)
    {
      try
      {
        localObject5 = Class.forName("kotlin.internal.jdk8.JDK8PlatformImplementations").newInstance();
        Intrinsics.checkExpressionValueIsNotNull(localObject5, "Class.forName(\"kotlin.in?entations\").newInstance()");
        if (localObject5 != null) {
          try
          {
            PlatformImplementations localPlatformImplementations = (PlatformImplementations)localObject5;
          }
          catch (ClassCastException localClassCastException1) {}
        }
      }
      catch (ClassNotFoundException localClassNotFoundException1)
      {
        Object localObject5;
        Object localObject1;
        ClassLoader localClassLoader;
        StringBuilder localStringBuilder;
        Object localObject2;
        label266:
        continue;
      }
      try
      {
        localObject1 = new TypeCastException("null cannot be cast to non-null type kotlin.internal.PlatformImplementations");
        throw ((Throwable)localObject1);
      }
      catch (ClassNotFoundException localClassNotFoundException2)
      {
        continue;
      }
      try
      {
        localObject5 = localObject5.getClass().getClassLoader();
        localClassLoader = PlatformImplementations.class.getClassLoader();
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("Instance classloader: ");
        localStringBuilder.append(localObject5);
        localStringBuilder.append(", base type classloader: ");
        localStringBuilder.append(localClassLoader);
        localObject5 = new ClassCastException(localStringBuilder.toString());
        localObject1 = (Throwable)localObject1;
        localObject1 = ((Exception)localObject5).initCause((Throwable)localObject1);
        Intrinsics.checkExpressionValueIsNotNull(localObject1, "ClassCastException(\"Inst?baseTypeCL\").initCause(e)");
        throw ((Throwable)localObject1);
      }
      catch (ClassNotFoundException localClassNotFoundException3)
      {
        continue;
      }
      try
      {
        localObject5 = Class.forName("kotlin.internal.JRE8PlatformImplementations").newInstance();
        Intrinsics.checkExpressionValueIsNotNull(localObject5, "Class.forName(\"kotlin.in?entations\").newInstance()");
        if (localObject5 != null) {
          try
          {
            localObject1 = (PlatformImplementations)localObject5;
          }
          catch (ClassCastException localClassCastException2) {}
        }
      }
      catch (ClassNotFoundException localClassNotFoundException4) {}
    }
    try
    {
      localObject2 = new TypeCastException("null cannot be cast to non-null type kotlin.internal.PlatformImplementations");
      throw ((Throwable)localObject2);
    }
    catch (ClassNotFoundException localClassNotFoundException5)
    {
      break label266;
    }
    try
    {
      localObject5 = localObject5.getClass().getClassLoader();
      localClassLoader = PlatformImplementations.class.getClassLoader();
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("Instance classloader: ");
      localStringBuilder.append(localObject5);
      localStringBuilder.append(", base type classloader: ");
      localStringBuilder.append(localClassLoader);
      localObject5 = new ClassCastException(localStringBuilder.toString());
      localObject2 = (Throwable)localObject2;
      localObject2 = ((Exception)localObject5).initCause((Throwable)localObject2);
      Intrinsics.checkExpressionValueIsNotNull(localObject2, "ClassCastException(\"Inst?baseTypeCL\").initCause(e)");
      throw ((Throwable)localObject2);
    }
    catch (ClassNotFoundException localClassNotFoundException6)
    {
      break label266;
    }
    if (i >= 65543) {}
    for (;;)
    {
      try
      {
        localObject5 = Class.forName("kotlin.internal.jdk7.JDK7PlatformImplementations").newInstance();
        Intrinsics.checkExpressionValueIsNotNull(localObject5, "Class.forName(\"kotlin.in?entations\").newInstance()");
        if (localObject5 != null) {
          try
          {
            localObject2 = (PlatformImplementations)localObject5;
          }
          catch (ClassCastException localClassCastException3) {}
        }
      }
      catch (ClassNotFoundException localClassNotFoundException7)
      {
        Object localObject3;
        Object localObject4;
        label528:
        continue;
      }
      try
      {
        localObject3 = new TypeCastException("null cannot be cast to non-null type kotlin.internal.PlatformImplementations");
        throw ((Throwable)localObject3);
      }
      catch (ClassNotFoundException localClassNotFoundException8)
      {
        continue;
      }
      try
      {
        localObject5 = localObject5.getClass().getClassLoader();
        localClassLoader = PlatformImplementations.class.getClassLoader();
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("Instance classloader: ");
        localStringBuilder.append(localObject5);
        localStringBuilder.append(", base type classloader: ");
        localStringBuilder.append(localClassLoader);
        localObject5 = new ClassCastException(localStringBuilder.toString());
        localObject3 = (Throwable)localObject3;
        localObject3 = ((Exception)localObject5).initCause((Throwable)localObject3);
        Intrinsics.checkExpressionValueIsNotNull(localObject3, "ClassCastException(\"Inst?baseTypeCL\").initCause(e)");
        throw ((Throwable)localObject3);
      }
      catch (ClassNotFoundException localClassNotFoundException9)
      {
        continue;
      }
      try
      {
        localObject5 = Class.forName("kotlin.internal.JRE7PlatformImplementations").newInstance();
        Intrinsics.checkExpressionValueIsNotNull(localObject5, "Class.forName(\"kotlin.in?entations\").newInstance()");
        if (localObject5 != null) {
          try
          {
            localObject3 = (PlatformImplementations)localObject5;
          }
          catch (ClassCastException localClassCastException4) {}
        }
      }
      catch (ClassNotFoundException localClassNotFoundException10) {}
    }
    try
    {
      localObject4 = new TypeCastException("null cannot be cast to non-null type kotlin.internal.PlatformImplementations");
      throw ((Throwable)localObject4);
    }
    catch (ClassNotFoundException localClassNotFoundException11)
    {
      break label528;
    }
    try
    {
      localObject5 = localObject5.getClass().getClassLoader();
      localClassLoader = PlatformImplementations.class.getClassLoader();
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("Instance classloader: ");
      localStringBuilder.append(localObject5);
      localStringBuilder.append(", base type classloader: ");
      localStringBuilder.append(localClassLoader);
      localObject5 = new ClassCastException(localStringBuilder.toString());
      localObject4 = (Throwable)localObject4;
      localObject4 = ((Exception)localObject5).initCause((Throwable)localObject4);
      Intrinsics.checkExpressionValueIsNotNull(localObject4, "ClassCastException(\"Inst?baseTypeCL\").initCause(e)");
      throw ((Throwable)localObject4);
    }
    catch (ClassNotFoundException localClassNotFoundException12)
    {
      break label528;
    }
    localObject4 = new PlatformImplementations();
    IMPLEMENTATIONS = (PlatformImplementations)localObject4;
  }
  
  public static final boolean apiVersionIsAtLeast(int paramInt1, int paramInt2, int paramInt3)
  {
    return KotlinVersion.CURRENT.isAtLeast(paramInt1, paramInt2, paramInt3);
  }
  
  private static final int getJavaVersion()
  {
    String str = System.getProperty("java.specification.version");
    if (str != null)
    {
      Object localObject = (CharSequence)str;
      int k = StringsKt__StringsKt.indexOf$default((CharSequence)localObject, '.', 0, false, 6, null);
      if (k < 0) {}
      try
      {
        i = Integer.parseInt(str);
        return i * 65536;
      }
      catch (NumberFormatException localNumberFormatException1)
      {
        int i;
        int m;
        int j;
        return 65542;
      }
      m = k + 1;
      j = StringsKt__StringsKt.indexOf$default((CharSequence)localObject, '.', m, false, 4, null);
      i = j;
      if (j < 0) {
        i = str.length();
      }
      if (str != null)
      {
        localObject = str.substring(0, k);
        Intrinsics.checkExpressionValueIsNotNull(localObject, "(this as java.lang.Strin?ing(startIndex, endIndex)");
        if (str != null)
        {
          str = str.substring(m, i);
          Intrinsics.checkExpressionValueIsNotNull(str, "(this as java.lang.Strin?ing(startIndex, endIndex)");
        }
      }
      try
      {
        i = Integer.parseInt((String)localObject);
        j = Integer.parseInt(str);
        return i * 65536 + j;
      }
      catch (NumberFormatException localNumberFormatException2) {}
      throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
      throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
    }
    return 65542;
  }
}
