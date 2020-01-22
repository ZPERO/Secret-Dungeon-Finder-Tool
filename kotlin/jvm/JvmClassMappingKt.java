package kotlin.jvm;

import java.lang.annotation.Annotation;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.ClassBasedDeclarationContainer;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;

@Metadata(bv={1, 0, 3}, d1={"\000,\n\000\n\002\030\002\n\000\n\002\020\033\n\002\b\003\n\002\030\002\n\002\b\005\n\002\020\000\n\002\b\013\n\002\020\013\n\002\020\021\n\002\b\002\032\037\020\030\032\0020\031\"\n\b\000\020\002\030\001*\0020\r*\006\022\002\b\0030\032?\006\002\020\033\"'\020\000\032\n\022\006\b\001\022\002H\0020\001\"\b\b\000\020\002*\0020\003*\002H\0028F?\006\006\032\004\b\004\020\005\"-\020\006\032\b\022\004\022\002H\0020\007\"\004\b\000\020\002*\b\022\004\022\002H\0020\0018G?\006\f\022\004\b\b\020\t\032\004\b\n\020\013\"&\020\f\032\b\022\004\022\002H\0020\007\"\b\b\000\020\002*\0020\r*\002H\0028?\002?\006\006\032\004\b\n\020\016\";\020\f\032\016\022\n\022\b\022\004\022\002H\0020\0010\007\"\b\b\000\020\002*\0020\r*\b\022\004\022\002H\0020\0018?\002X?\004?\006\f\022\004\b\017\020\t\032\004\b\020\020\013\"+\020\021\032\b\022\004\022\002H\0020\007\"\b\b\000\020\002*\0020\r*\b\022\004\022\002H\0020\0018F?\006\006\032\004\b\022\020\013\"-\020\023\032\n\022\004\022\002H\002\030\0010\007\"\b\b\000\020\002*\0020\r*\b\022\004\022\002H\0020\0018F?\006\006\032\004\b\024\020\013\"+\020\025\032\b\022\004\022\002H\0020\001\"\b\b\000\020\002*\0020\r*\b\022\004\022\002H\0020\0078G?\006\006\032\004\b\026\020\027?\006\034"}, d2={"annotationClass", "Lkotlin/reflect/KClass;", "T", "", "getAnnotationClass", "(Ljava/lang/annotation/Annotation;)Lkotlin/reflect/KClass;", "java", "Ljava/lang/Class;", "java$annotations", "(Lkotlin/reflect/KClass;)V", "getJavaClass", "(Lkotlin/reflect/KClass;)Ljava/lang/Class;", "javaClass", "", "(Ljava/lang/Object;)Ljava/lang/Class;", "javaClass$annotations", "getRuntimeClassOfKClassInstance", "javaObjectType", "getJavaObjectType", "javaPrimitiveType", "getJavaPrimitiveType", "kotlin", "getKotlinClass", "(Ljava/lang/Class;)Lkotlin/reflect/KClass;", "isArrayOf", "", "", "([Ljava/lang/Object;)Z", "kotlin-stdlib"}, k=2, mv={1, 1, 15})
public final class JvmClassMappingKt
{
  public static final KClass getAnnotationClass(Annotation paramAnnotation)
  {
    Intrinsics.checkParameterIsNotNull(paramAnnotation, "$this$annotationClass");
    paramAnnotation = paramAnnotation.annotationType();
    Intrinsics.checkExpressionValueIsNotNull(paramAnnotation, "(this as java.lang.annot?otation).annotationType()");
    paramAnnotation = getKotlinClass(paramAnnotation);
    if (paramAnnotation != null) {
      return paramAnnotation;
    }
    throw new TypeCastException("null cannot be cast to non-null type kotlin.reflect.KClass<out T>");
  }
  
  public static final Class getJavaClass(Object paramObject)
  {
    Intrinsics.checkParameterIsNotNull(paramObject, "$this$javaClass");
    paramObject = paramObject.getClass();
    if (paramObject != null) {
      return paramObject;
    }
    throw new TypeCastException("null cannot be cast to non-null type java.lang.Class<T>");
  }
  
  public static final Class getJavaClass(KClass paramKClass)
  {
    Intrinsics.checkParameterIsNotNull(paramKClass, "$this$java");
    paramKClass = ((ClassBasedDeclarationContainer)paramKClass).getJClass();
    if (paramKClass != null) {
      return paramKClass;
    }
    throw new TypeCastException("null cannot be cast to non-null type java.lang.Class<T>");
  }
  
  public static final Class getJavaObjectType(KClass paramKClass)
  {
    Intrinsics.checkParameterIsNotNull(paramKClass, "$this$javaObjectType");
    Object localObject = ((ClassBasedDeclarationContainer)paramKClass).getJClass();
    paramKClass = (KClass)localObject;
    if (!((Class)localObject).isPrimitive())
    {
      if (localObject != null) {
        return localObject;
      }
      throw new TypeCastException("null cannot be cast to non-null type java.lang.Class<T>");
    }
    localObject = ((Class)localObject).getName();
    if (localObject != null) {
      switch (((String)localObject).hashCode())
      {
      default: 
        break;
      case 109413500: 
        if (((String)localObject).equals("short")) {
          paramKClass = Short.class;
        }
        break;
      case 97526364: 
        if (((String)localObject).equals("float")) {
          paramKClass = Float.class;
        }
        break;
      case 64711720: 
        if (((String)localObject).equals("boolean")) {
          paramKClass = Boolean.class;
        }
        break;
      case 3625364: 
        if (((String)localObject).equals("void")) {
          paramKClass = Void.class;
        }
        break;
      case 3327612: 
        if (((String)localObject).equals("long")) {
          paramKClass = Long.class;
        }
        break;
      case 3052374: 
        if (((String)localObject).equals("char")) {
          paramKClass = Character.class;
        }
        break;
      case 3039496: 
        if (((String)localObject).equals("byte")) {
          paramKClass = Byte.class;
        }
        break;
      case 104431: 
        if (((String)localObject).equals("int")) {
          paramKClass = Integer.class;
        }
        break;
      case -1325958191: 
        if (((String)localObject).equals("double")) {
          paramKClass = Double.class;
        }
        break;
      }
    }
    if (paramKClass != null) {
      return paramKClass;
    }
    throw new TypeCastException("null cannot be cast to non-null type java.lang.Class<T>");
  }
  
  public static final Class getJavaPrimitiveType(KClass paramKClass)
  {
    Intrinsics.checkParameterIsNotNull(paramKClass, "$this$javaPrimitiveType");
    paramKClass = ((ClassBasedDeclarationContainer)paramKClass).getJClass();
    if (paramKClass.isPrimitive())
    {
      if (paramKClass != null) {
        return paramKClass;
      }
      throw new TypeCastException("null cannot be cast to non-null type java.lang.Class<T>");
    }
    paramKClass = paramKClass.getName();
    if (paramKClass != null) {
      switch (paramKClass.hashCode())
      {
      default: 
        break;
      case 761287205: 
        if (paramKClass.equals("java.lang.Double")) {
          return Double.TYPE;
        }
        break;
      case 399092968: 
        if (paramKClass.equals("java.lang.Void")) {
          return Void.TYPE;
        }
        break;
      case 398795216: 
        if (paramKClass.equals("java.lang.Long")) {
          return Long.TYPE;
        }
        break;
      case 398507100: 
        if (paramKClass.equals("java.lang.Byte")) {
          return Byte.TYPE;
        }
        break;
      case 344809556: 
        if (paramKClass.equals("java.lang.Boolean")) {
          return Boolean.TYPE;
        }
        break;
      case 155276373: 
        if (paramKClass.equals("java.lang.Character")) {
          return Character.TYPE;
        }
        break;
      case -515992664: 
        if (paramKClass.equals("java.lang.Short")) {
          return Short.TYPE;
        }
        break;
      case -527879800: 
        if (paramKClass.equals("java.lang.Float")) {
          return Float.TYPE;
        }
        break;
      case -2056817302: 
        if (paramKClass.equals("java.lang.Integer")) {
          return Integer.TYPE;
        }
        break;
      }
    }
    return null;
  }
  
  public static final KClass getKotlinClass(Class paramClass)
  {
    Intrinsics.checkParameterIsNotNull(paramClass, "$this$kotlin");
    return Reflection.getOrCreateKotlinClass(paramClass);
  }
  
  public static final Class getRuntimeClassOfKClassInstance(KClass paramKClass)
  {
    Intrinsics.checkParameterIsNotNull(paramKClass, "$this$javaClass");
    paramKClass = ((Object)paramKClass).getClass();
    if (paramKClass != null) {
      return paramKClass;
    }
    throw new TypeCastException("null cannot be cast to non-null type java.lang.Class<kotlin.reflect.KClass<T>>");
  }
}
