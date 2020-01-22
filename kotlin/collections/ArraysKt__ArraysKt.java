package kotlin.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.UByteArray;
import kotlin.UIntArray;
import kotlin.ULongArray;
import kotlin.UShortArray;
import kotlin.collections.unsigned.UArraysKt___UArraysKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt___RangesKt;

@Metadata(bv={1, 0, 3}, d1={"\000H\n\000\n\002\020\013\n\000\n\002\020\021\n\002\b\004\n\002\020\016\n\002\b\003\n\002\020\002\n\000\n\002\030\002\n\002\030\002\n\000\n\002\020!\n\002\b\003\n\002\020 \n\002\b\005\n\002\030\002\n\002\b\004\n\002\030\002\n\002\b\002\0321\020\000\032\0020\001\"\004\b\000\020\002*\n\022\006\b\001\022\002H\0020\0032\016\020\004\032\n\022\006\b\001\022\002H\0020\003H\001?\006\004\b\005\020\006\032!\020\007\032\0020\b\"\004\b\000\020\002*\n\022\006\b\001\022\002H\0020\003H\001?\006\004\b\t\020\n\032?\020\013\032\0020\f\"\004\b\000\020\002*\n\022\006\b\001\022\002H\0020\0032\n\020\r\032\0060\016j\002`\0172\020\020\020\032\f\022\b\022\006\022\002\b\0030\0030\021H\002?\006\004\b\022\020\023\032+\020\024\032\b\022\004\022\002H\0020\025\"\004\b\000\020\002*\022\022\016\b\001\022\n\022\006\b\001\022\002H\0020\0030\003?\006\002\020\026\0328\020\027\032\002H\030\"\020\b\000\020\031*\006\022\002\b\0030\003*\002H\030\"\004\b\001\020\030*\002H\0312\f\020\032\032\b\022\004\022\002H\0300\033H?\b?\006\002\020\034\032)\020\035\032\0020\001*\b\022\002\b\003\030\0010\003H?\b?\002\016\n\f\b\000\022\002\030\001\032\004\b\003\020\000?\006\002\020\036\032G\020\037\032\032\022\n\022\b\022\004\022\002H\0020\025\022\n\022\b\022\004\022\002H\0300\0250 \"\004\b\000\020\002\"\004\b\001\020\030*\026\022\022\b\001\022\016\022\004\022\002H\002\022\004\022\002H\0300 0\003?\006\002\020!?\006\""}, d2={"contentDeepEqualsImpl", "", "T", "", "other", "contentDeepEquals", "([Ljava/lang/Object;[Ljava/lang/Object;)Z", "contentDeepToStringImpl", "", "contentDeepToString", "([Ljava/lang/Object;)Ljava/lang/String;", "contentDeepToStringInternal", "", "result", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "processed", "", "contentDeepToStringInternal$ArraysKt__ArraysKt", "([Ljava/lang/Object;Ljava/lang/StringBuilder;Ljava/util/List;)V", "flatten", "", "([[Ljava/lang/Object;)Ljava/util/List;", "ifEmpty", "R", "C", "defaultValue", "Lkotlin/Function0;", "([Ljava/lang/Object;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "isNullOrEmpty", "([Ljava/lang/Object;)Z", "unzip", "Lkotlin/Pair;", "([Lkotlin/Pair;)Lkotlin/Pair;", "kotlin-stdlib"}, k=5, mv={1, 1, 15}, xi=1, xs="kotlin/collections/ArraysKt")
class ArraysKt__ArraysKt
  extends ArraysKt__ArraysJVMKt
{
  public ArraysKt__ArraysKt() {}
  
  public static final boolean contentDeepEquals(Object[] paramArrayOfObject1, Object[] paramArrayOfObject2)
  {
    Intrinsics.checkParameterIsNotNull(paramArrayOfObject1, "$this$contentDeepEqualsImpl");
    Intrinsics.checkParameterIsNotNull(paramArrayOfObject2, "other");
    if (paramArrayOfObject1 == paramArrayOfObject2) {
      return true;
    }
    if (paramArrayOfObject1.length != paramArrayOfObject2.length) {
      return false;
    }
    int j = paramArrayOfObject1.length;
    int i = 0;
    while (i < j)
    {
      Object localObject1 = paramArrayOfObject1[i];
      Object localObject2 = paramArrayOfObject2[i];
      if (localObject1 != localObject2)
      {
        if (localObject1 == null) {
          break label557;
        }
        if (localObject2 == null) {
          return false;
        }
        if (((localObject1 instanceof Object[])) && ((localObject2 instanceof Object[])))
        {
          if (!contentDeepEquals((Object[])localObject1, (Object[])localObject2)) {
            return false;
          }
        }
        else if (((localObject1 instanceof byte[])) && ((localObject2 instanceof byte[])))
        {
          if (!Arrays.equals((byte[])localObject1, (byte[])localObject2)) {
            return false;
          }
        }
        else if (((localObject1 instanceof short[])) && ((localObject2 instanceof short[])))
        {
          if (!Arrays.equals((short[])localObject1, (short[])localObject2)) {
            return false;
          }
        }
        else if (((localObject1 instanceof int[])) && ((localObject2 instanceof int[])))
        {
          if (!Arrays.equals((int[])localObject1, (int[])localObject2)) {
            return false;
          }
        }
        else if (((localObject1 instanceof long[])) && ((localObject2 instanceof long[])))
        {
          if (!Arrays.equals((long[])localObject1, (long[])localObject2)) {
            return false;
          }
        }
        else if (((localObject1 instanceof float[])) && ((localObject2 instanceof float[])))
        {
          if (!Arrays.equals((float[])localObject1, (float[])localObject2)) {
            return false;
          }
        }
        else if (((localObject1 instanceof double[])) && ((localObject2 instanceof double[])))
        {
          if (!Arrays.equals((double[])localObject1, (double[])localObject2)) {
            return false;
          }
        }
        else if (((localObject1 instanceof char[])) && ((localObject2 instanceof char[])))
        {
          if (!Arrays.equals((char[])localObject1, (char[])localObject2)) {
            return false;
          }
        }
        else if (((localObject1 instanceof boolean[])) && ((localObject2 instanceof boolean[])))
        {
          if (!Arrays.equals((boolean[])localObject1, (boolean[])localObject2)) {
            return false;
          }
        }
        else if (((localObject1 instanceof UByteArray)) && ((localObject2 instanceof UByteArray)))
        {
          if (!UArraysKt___UArraysKt.contentEquals-kdPth3s(((UByteArray)localObject1).unbox-impl(), ((UByteArray)localObject2).unbox-impl())) {
            return false;
          }
        }
        else if (((localObject1 instanceof UShortArray)) && ((localObject2 instanceof UShortArray)))
        {
          if (!UArraysKt___UArraysKt.contentEquals-mazbYpA(((UShortArray)localObject1).unbox-impl(), ((UShortArray)localObject2).unbox-impl())) {
            return false;
          }
        }
        else if (((localObject1 instanceof UIntArray)) && ((localObject2 instanceof UIntArray)))
        {
          if (!UArraysKt___UArraysKt.contentEquals-ctEhBpI(((UIntArray)localObject1).unbox-impl(), ((UIntArray)localObject2).unbox-impl())) {
            return false;
          }
        }
        else if (((localObject1 instanceof ULongArray)) && ((localObject2 instanceof ULongArray)))
        {
          if (!UArraysKt___UArraysKt.contentEquals-us8wMrg(((ULongArray)localObject1).unbox-impl(), ((ULongArray)localObject2).unbox-impl())) {
            return false;
          }
        }
        else if ((Intrinsics.areEqual(localObject1, localObject2) ^ true)) {
          return false;
        }
      }
      i += 1;
      continue;
      label557:
      return false;
    }
    return true;
  }
  
  public static final String contentDeepToString(Object[] paramArrayOfObject)
  {
    Intrinsics.checkParameterIsNotNull(paramArrayOfObject, "$this$contentDeepToStringImpl");
    StringBuilder localStringBuilder = new StringBuilder(RangesKt___RangesKt.coerceAtMost(paramArrayOfObject.length, 429496729) * 5 + 2);
    contentDeepToStringInternal$ArraysKt__ArraysKt(paramArrayOfObject, localStringBuilder, (List)new ArrayList());
    paramArrayOfObject = localStringBuilder.toString();
    Intrinsics.checkExpressionValueIsNotNull(paramArrayOfObject, "StringBuilder(capacity).?builderAction).toString()");
    return paramArrayOfObject;
  }
  
  private static final void contentDeepToStringInternal$ArraysKt__ArraysKt(Object[] paramArrayOfObject, StringBuilder paramStringBuilder, List paramList)
  {
    if (paramList.contains(paramArrayOfObject))
    {
      paramStringBuilder.append("[...]");
      return;
    }
    paramList.add(paramArrayOfObject);
    paramStringBuilder.append('[');
    int i = 0;
    int j = paramArrayOfObject.length;
    while (i < j)
    {
      if (i != 0) {
        paramStringBuilder.append(", ");
      }
      Object localObject = paramArrayOfObject[i];
      if (localObject == null)
      {
        paramStringBuilder.append("null");
      }
      else if ((localObject instanceof Object[]))
      {
        contentDeepToStringInternal$ArraysKt__ArraysKt((Object[])localObject, paramStringBuilder, paramList);
      }
      else if ((localObject instanceof byte[]))
      {
        localObject = Arrays.toString((byte[])localObject);
        Intrinsics.checkExpressionValueIsNotNull(localObject, "java.util.Arrays.toString(this)");
        paramStringBuilder.append((String)localObject);
      }
      else if ((localObject instanceof short[]))
      {
        localObject = Arrays.toString((short[])localObject);
        Intrinsics.checkExpressionValueIsNotNull(localObject, "java.util.Arrays.toString(this)");
        paramStringBuilder.append((String)localObject);
      }
      else if ((localObject instanceof int[]))
      {
        localObject = Arrays.toString((int[])localObject);
        Intrinsics.checkExpressionValueIsNotNull(localObject, "java.util.Arrays.toString(this)");
        paramStringBuilder.append((String)localObject);
      }
      else if ((localObject instanceof long[]))
      {
        localObject = Arrays.toString((long[])localObject);
        Intrinsics.checkExpressionValueIsNotNull(localObject, "java.util.Arrays.toString(this)");
        paramStringBuilder.append((String)localObject);
      }
      else if ((localObject instanceof float[]))
      {
        localObject = Arrays.toString((float[])localObject);
        Intrinsics.checkExpressionValueIsNotNull(localObject, "java.util.Arrays.toString(this)");
        paramStringBuilder.append((String)localObject);
      }
      else if ((localObject instanceof double[]))
      {
        localObject = Arrays.toString((double[])localObject);
        Intrinsics.checkExpressionValueIsNotNull(localObject, "java.util.Arrays.toString(this)");
        paramStringBuilder.append((String)localObject);
      }
      else if ((localObject instanceof char[]))
      {
        localObject = Arrays.toString((char[])localObject);
        Intrinsics.checkExpressionValueIsNotNull(localObject, "java.util.Arrays.toString(this)");
        paramStringBuilder.append((String)localObject);
      }
      else if ((localObject instanceof boolean[]))
      {
        localObject = Arrays.toString((boolean[])localObject);
        Intrinsics.checkExpressionValueIsNotNull(localObject, "java.util.Arrays.toString(this)");
        paramStringBuilder.append((String)localObject);
      }
      else if ((localObject instanceof UByteArray))
      {
        paramStringBuilder.append(UArraysKt___UArraysKt.contentToString-GBYM_sE(((UByteArray)localObject).unbox-impl()));
      }
      else if ((localObject instanceof UShortArray))
      {
        paramStringBuilder.append(UArraysKt___UArraysKt.contentToString-rL5Bavg(((UShortArray)localObject).unbox-impl()));
      }
      else if ((localObject instanceof UIntArray))
      {
        paramStringBuilder.append(UArraysKt___UArraysKt.contentToString--ajY-9A(((UIntArray)localObject).unbox-impl()));
      }
      else if ((localObject instanceof ULongArray))
      {
        paramStringBuilder.append(UArraysKt___UArraysKt.contentToString-QwZRm1k(((ULongArray)localObject).unbox-impl()));
      }
      else
      {
        paramStringBuilder.append(localObject.toString());
      }
      i += 1;
    }
    paramStringBuilder.append(']');
    paramList.remove(CollectionsKt__CollectionsKt.getLastIndex(paramList));
  }
  
  public static final List flatten(Object[][] paramArrayOfObject)
  {
    Intrinsics.checkParameterIsNotNull(paramArrayOfObject, "$this$flatten");
    Object localObject = (Object[])paramArrayOfObject;
    int m = localObject.length;
    int k = 0;
    int i = 0;
    int j = 0;
    while (i < m)
    {
      j += ((Object[])localObject[i]).length;
      i += 1;
    }
    localObject = new ArrayList(j);
    j = paramArrayOfObject.length;
    i = k;
    while (i < j)
    {
      Object[] arrayOfObject = paramArrayOfObject[i];
      CollectionsKt__MutableCollectionsKt.addAll((Collection)localObject, arrayOfObject);
      i += 1;
    }
    return (List)localObject;
  }
  
  private static final Object ifEmpty(Object[] paramArrayOfObject, Function0 paramFunction0)
  {
    int i;
    if (paramArrayOfObject.length == 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0) {
      return paramFunction0.invoke();
    }
    return paramArrayOfObject;
  }
  
  private static final boolean isNullOrEmpty(Object[] paramArrayOfObject)
  {
    int i;
    if (paramArrayOfObject != null) {
      if (paramArrayOfObject.length == 0) {
        i = 1;
      } else {
        i = 0;
      }
    }
    return i != 0;
  }
  
  public static final Pair unzip(Pair[] paramArrayOfPair)
  {
    Intrinsics.checkParameterIsNotNull(paramArrayOfPair, "$this$unzip");
    ArrayList localArrayList1 = new ArrayList(paramArrayOfPair.length);
    ArrayList localArrayList2 = new ArrayList(paramArrayOfPair.length);
    int j = paramArrayOfPair.length;
    int i = 0;
    while (i < j)
    {
      Pair localPair = paramArrayOfPair[i];
      localArrayList1.add(localPair.getFirst());
      localArrayList2.add(localPair.getSecond());
      i += 1;
    }
    return TuplesKt.to(localArrayList1, localArrayList2);
  }
}
