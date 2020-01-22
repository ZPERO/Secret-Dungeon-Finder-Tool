package kotlin.collections;

import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KCallable;
import kotlin.reflect.KProperty;

@Metadata(bv={1, 0, 3}, d1={"\000.\n\002\b\003\n\002\020$\n\002\020\016\n\002\030\002\n\000\n\002\020\000\n\000\n\002\030\002\n\000\n\002\020%\n\002\b\003\n\002\020\002\n\002\b\003\032K\020\000\032\002H\001\"\004\b\000\020\002\"\b\b\001\020\001*\002H\002*\025\022\006\b\000\022\0020\004\022\t\022\007H\002?\006\002\b\0050\0032\b\020\006\032\004\030\0010\0072\n\020\b\032\006\022\002\b\0030\tH?\n?\006\002\020\n\032@\020\000\032\002H\002\"\004\b\000\020\002*\022\022\006\b\000\022\0020\004\022\006\b\000\022\002H\0020\0132\b\020\006\032\004\030\0010\0072\n\020\b\032\006\022\002\b\0030\tH?\b?\006\004\b\f\020\n\032O\020\000\032\002H\001\"\004\b\000\020\002\"\b\b\001\020\001*\002H\002*\027\022\006\b\000\022\0020\004\022\013\b\001\022\007H\002?\006\002\b\0050\0132\b\020\006\032\004\030\0010\0072\n\020\b\032\006\022\002\b\0030\tH?\n?\006\004\b\r\020\n\032F\020\016\032\0020\017\"\004\b\000\020\002*\022\022\006\b\000\022\0020\004\022\006\b\000\022\002H\0020\0132\b\020\006\032\004\030\0010\0072\n\020\b\032\006\022\002\b\0030\t2\006\020\020\032\002H\002H?\n?\006\002\020\021?\006\022"}, d2={"getValue", "V1", "V", "", "", "Lkotlin/internal/Exact;", "thisRef", "", "property", "Lkotlin/reflect/KProperty;", "(Ljava/util/Map;Ljava/lang/Object;Lkotlin/reflect/KProperty;)Ljava/lang/Object;", "", "getVarContravariant", "getVar", "setValue", "", "value", "(Ljava/util/Map;Ljava/lang/Object;Lkotlin/reflect/KProperty;Ljava/lang/Object;)V", "kotlin-stdlib"}, k=2, mv={1, 1, 15})
public final class MapAccessorsKt
{
  private static final Object getValue(Map paramMap, Object paramObject, KProperty paramKProperty)
  {
    Intrinsics.checkParameterIsNotNull(paramMap, "$this$getValue");
    return MapsKt__MapWithDefaultKt.getOrImplicitDefaultNullable(paramMap, paramKProperty.getName());
  }
  
  private static final Object getVar(Map paramMap, Object paramObject, KProperty paramKProperty)
  {
    Intrinsics.checkParameterIsNotNull(paramMap, "$this$getValue");
    return MapsKt__MapWithDefaultKt.getOrImplicitDefaultNullable(paramMap, paramKProperty.getName());
  }
  
  private static final Object getVarContravariant(Map paramMap, Object paramObject, KProperty paramKProperty)
  {
    return MapsKt__MapWithDefaultKt.getOrImplicitDefaultNullable(paramMap, paramKProperty.getName());
  }
  
  private static final void setValue(Map paramMap, Object paramObject1, KProperty paramKProperty, Object paramObject2)
  {
    Intrinsics.checkParameterIsNotNull(paramMap, "$this$setValue");
    paramMap.put(paramKProperty.getName(), paramObject2);
  }
}
