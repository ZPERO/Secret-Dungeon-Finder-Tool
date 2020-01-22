package kotlin.comparisons;

import java.util.Comparator;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000<\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\020\017\n\000\n\002\020\021\n\002\b\005\n\002\020\b\n\002\b\013\n\002\020\000\n\002\b\b\n\002\030\002\n\002\030\002\n\002\b\003\032;\020\000\032\022\022\004\022\002H\0020\001j\b\022\004\022\002H\002`\003\"\004\b\000\020\0022\032\b\004\020\004\032\024\022\004\022\002H\002\022\n\022\b\022\002\b\003\030\0010\0060\005H?\b\032Y\020\000\032\022\022\004\022\002H\0020\001j\b\022\004\022\002H\002`\003\"\004\b\000\020\00226\020\007\032\034\022\030\b\001\022\024\022\004\022\002H\002\022\n\022\b\022\002\b\003\030\0010\0060\0050\b\"\024\022\004\022\002H\002\022\n\022\b\022\002\b\003\030\0010\0060\005?\006\002\020\t\032W\020\000\032\022\022\004\022\002H\0020\001j\b\022\004\022\002H\002`\003\"\004\b\000\020\002\"\004\b\001\020\n2\032\020\013\032\026\022\006\b\000\022\002H\n0\001j\n\022\006\b\000\022\002H\n`\0032\024\b\004\020\004\032\016\022\004\022\002H\002\022\004\022\002H\n0\005H?\b\032;\020\f\032\022\022\004\022\002H\0020\001j\b\022\004\022\002H\002`\003\"\004\b\000\020\0022\032\b\004\020\004\032\024\022\004\022\002H\002\022\n\022\b\022\002\b\003\030\0010\0060\005H?\b\032W\020\f\032\022\022\004\022\002H\0020\001j\b\022\004\022\002H\002`\003\"\004\b\000\020\002\"\004\b\001\020\n2\032\020\013\032\026\022\006\b\000\022\002H\n0\001j\n\022\006\b\000\022\002H\n`\0032\024\b\004\020\004\032\016\022\004\022\002H\002\022\004\022\002H\n0\005H?\b\032-\020\r\032\0020\016\"\f\b\000\020\002*\006\022\002\b\0030\0062\b\020\017\032\004\030\001H\0022\b\020\020\032\004\030\001H\002?\006\002\020\021\032>\020\022\032\0020\016\"\004\b\000\020\0022\006\020\017\032\002H\0022\006\020\020\032\002H\0022\030\020\004\032\024\022\004\022\002H\002\022\n\022\b\022\002\b\003\030\0010\0060\005H?\b?\006\002\020\023\032Y\020\022\032\0020\016\"\004\b\000\020\0022\006\020\017\032\002H\0022\006\020\020\032\002H\00226\020\007\032\034\022\030\b\001\022\024\022\004\022\002H\002\022\n\022\b\022\002\b\003\030\0010\0060\0050\b\"\024\022\004\022\002H\002\022\n\022\b\022\002\b\003\030\0010\0060\005?\006\002\020\024\032Z\020\022\032\0020\016\"\004\b\000\020\002\"\004\b\001\020\n2\006\020\017\032\002H\0022\006\020\020\032\002H\0022\032\020\013\032\026\022\006\b\000\022\002H\n0\001j\n\022\006\b\000\022\002H\n`\0032\022\020\004\032\016\022\004\022\002H\002\022\004\022\002H\n0\005H?\b?\006\002\020\025\032G\020\026\032\0020\016\"\004\b\000\020\0022\006\020\017\032\002H\0022\006\020\020\032\002H\0022 \020\007\032\034\022\030\b\001\022\024\022\004\022\002H\002\022\n\022\b\022\002\b\003\030\0010\0060\0050\bH\002?\006\004\b\027\020\024\032&\020\030\032\022\022\004\022\002H\0020\001j\b\022\004\022\002H\002`\003\"\016\b\000\020\002*\b\022\004\022\002H\0020\006\032-\020\031\032\026\022\006\022\004\030\001H\0020\001j\n\022\006\022\004\030\001H\002`\003\"\016\b\000\020\002*\b\022\004\022\002H\0020\006H?\b\032@\020\031\032\026\022\006\022\004\030\001H\0020\001j\n\022\006\022\004\030\001H\002`\003\"\b\b\000\020\002*\0020\0322\032\020\013\032\026\022\006\b\000\022\002H\0020\001j\n\022\006\b\000\022\002H\002`\003\032-\020\033\032\026\022\006\022\004\030\001H\0020\001j\n\022\006\022\004\030\001H\002`\003\"\016\b\000\020\002*\b\022\004\022\002H\0020\006H?\b\032@\020\033\032\026\022\006\022\004\030\001H\0020\001j\n\022\006\022\004\030\001H\002`\003\"\b\b\000\020\002*\0020\0322\032\020\013\032\026\022\006\b\000\022\002H\0020\001j\n\022\006\b\000\022\002H\002`\003\032&\020\034\032\022\022\004\022\002H\0020\001j\b\022\004\022\002H\002`\003\"\016\b\000\020\002*\b\022\004\022\002H\0020\006\0320\020\035\032\022\022\004\022\002H\0020\001j\b\022\004\022\002H\002`\003\"\004\b\000\020\002*\022\022\004\022\002H\0020\001j\b\022\004\022\002H\002`\003\032O\020\036\032\022\022\004\022\002H\0020\001j\b\022\004\022\002H\002`\003\"\004\b\000\020\002*\022\022\004\022\002H\0020\001j\b\022\004\022\002H\002`\0032\032\020\013\032\026\022\006\b\000\022\002H\0020\001j\n\022\006\b\000\022\002H\002`\003H?\004\032O\020\037\032\022\022\004\022\002H\0020\001j\b\022\004\022\002H\002`\003\"\004\b\000\020\002*\022\022\004\022\002H\0020\001j\b\022\004\022\002H\002`\0032\032\b\004\020\004\032\024\022\004\022\002H\002\022\n\022\b\022\002\b\003\030\0010\0060\005H?\b\032k\020\037\032\022\022\004\022\002H\0020\001j\b\022\004\022\002H\002`\003\"\004\b\000\020\002\"\004\b\001\020\n*\022\022\004\022\002H\0020\001j\b\022\004\022\002H\002`\0032\032\020\013\032\026\022\006\b\000\022\002H\n0\001j\n\022\006\b\000\022\002H\n`\0032\024\b\004\020\004\032\016\022\004\022\002H\002\022\004\022\002H\n0\005H?\b\032O\020 \032\022\022\004\022\002H\0020\001j\b\022\004\022\002H\002`\003\"\004\b\000\020\002*\022\022\004\022\002H\0020\001j\b\022\004\022\002H\002`\0032\032\b\004\020\004\032\024\022\004\022\002H\002\022\n\022\b\022\002\b\003\030\0010\0060\005H?\b\032k\020 \032\022\022\004\022\002H\0020\001j\b\022\004\022\002H\002`\003\"\004\b\000\020\002\"\004\b\001\020\n*\022\022\004\022\002H\0020\001j\b\022\004\022\002H\002`\0032\032\020\013\032\026\022\006\b\000\022\002H\n0\001j\n\022\006\b\000\022\002H\n`\0032\024\b\004\020\004\032\016\022\004\022\002H\002\022\004\022\002H\n0\005H?\b\032m\020!\032\022\022\004\022\002H\0020\001j\b\022\004\022\002H\002`\003\"\004\b\000\020\002*\022\022\004\022\002H\0020\001j\b\022\004\022\002H\002`\00328\b\004\020\"\0322\022\023\022\021H\002?\006\f\b$\022\b\b%\022\004\b\b(\017\022\023\022\021H\002?\006\f\b$\022\b\b%\022\004\b\b(\020\022\004\022\0020\0160#H?\b\032O\020&\032\022\022\004\022\002H\0020\001j\b\022\004\022\002H\002`\003\"\004\b\000\020\002*\022\022\004\022\002H\0020\001j\b\022\004\022\002H\002`\0032\032\020\013\032\026\022\006\b\000\022\002H\0020\001j\n\022\006\b\000\022\002H\002`\003H?\004?\006'"}, d2={"compareBy", "Ljava/util/Comparator;", "T", "Lkotlin/Comparator;", "selector", "Lkotlin/Function1;", "", "selectors", "", "([Lkotlin/jvm/functions/Function1;)Ljava/util/Comparator;", "K", "comparator", "compareByDescending", "compareValues", "", "a", "b", "(Ljava/lang/Comparable;Ljava/lang/Comparable;)I", "compareValuesBy", "(Ljava/lang/Object;Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)I", "(Ljava/lang/Object;Ljava/lang/Object;[Lkotlin/jvm/functions/Function1;)I", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/util/Comparator;Lkotlin/jvm/functions/Function1;)I", "compareValuesByImpl", "compareValuesByImpl$ComparisonsKt__ComparisonsKt", "naturalOrder", "nullsFirst", "", "nullsLast", "reverseOrder", "reversed", "then", "thenBy", "thenByDescending", "thenComparator", "comparison", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "thenDescending", "kotlin-stdlib"}, k=5, mv={1, 1, 15}, xi=1, xs="kotlin/comparisons/ComparisonsKt")
class ComparisonsKt__ComparisonsKt
{
  public ComparisonsKt__ComparisonsKt() {}
  
  private static final Comparator compareBy(Comparator paramComparator, final Function1 paramFunction1)
  {
    (Comparator)new Comparator()
    {
      public final int compare(Object paramAnonymousObject1, Object paramAnonymousObject2)
      {
        return ComparisonsKt__ComparisonsKt.this.compare(paramFunction1.invoke(paramAnonymousObject1), paramFunction1.invoke(paramAnonymousObject2));
      }
    };
  }
  
  private static final Comparator compareBy(Function1 paramFunction1)
  {
    (Comparator)new Comparator()
    {
      public final int compare(Object paramAnonymousObject1, Object paramAnonymousObject2)
      {
        return ComparisonsKt__ComparisonsKt.compareValues((Comparable)invoke(paramAnonymousObject1), (Comparable)invoke(paramAnonymousObject2));
      }
    };
  }
  
  public static final Comparator compareBy(Function1... paramVarArgs)
  {
    Intrinsics.checkParameterIsNotNull(paramVarArgs, "selectors");
    int i;
    if (paramVarArgs.length > 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0) {
      (Comparator)new Comparator()
      {
        public final int compare(Object paramAnonymousObject1, Object paramAnonymousObject2)
        {
          return ComparisonsKt__ComparisonsKt.access$compareValuesByImpl(paramAnonymousObject1, paramAnonymousObject2, ComparisonsKt__ComparisonsKt.this);
        }
      };
    }
    throw ((Throwable)new IllegalArgumentException("Failed requirement.".toString()));
  }
  
  private static final Comparator compareByDescending(Comparator paramComparator, final Function1 paramFunction1)
  {
    (Comparator)new Comparator()
    {
      public final int compare(Object paramAnonymousObject1, Object paramAnonymousObject2)
      {
        return ComparisonsKt__ComparisonsKt.this.compare(paramFunction1.invoke(paramAnonymousObject2), paramFunction1.invoke(paramAnonymousObject1));
      }
    };
  }
  
  private static final Comparator compareByDescending(Function1 paramFunction1)
  {
    (Comparator)new Comparator()
    {
      public final int compare(Object paramAnonymousObject1, Object paramAnonymousObject2)
      {
        return ComparisonsKt__ComparisonsKt.compareValues((Comparable)invoke(paramAnonymousObject2), (Comparable)invoke(paramAnonymousObject1));
      }
    };
  }
  
  public static final int compareValues(Comparable paramComparable1, Comparable paramComparable2)
  {
    if (paramComparable1 == paramComparable2) {
      return 0;
    }
    if (paramComparable1 == null) {
      return -1;
    }
    if (paramComparable2 == null) {
      return 1;
    }
    return paramComparable1.compareTo(paramComparable2);
  }
  
  private static final int compareValuesBy(Object paramObject1, Object paramObject2, Comparator paramComparator, Function1 paramFunction1)
  {
    return paramComparator.compare(paramFunction1.invoke(paramObject1), paramFunction1.invoke(paramObject2));
  }
  
  private static final int compareValuesBy(Object paramObject1, Object paramObject2, Function1 paramFunction1)
  {
    return compareValues((Comparable)paramFunction1.invoke(paramObject1), (Comparable)paramFunction1.invoke(paramObject2));
  }
  
  public static final int compareValuesBy(Object paramObject1, Object paramObject2, Function1... paramVarArgs)
  {
    Intrinsics.checkParameterIsNotNull(paramVarArgs, "selectors");
    int i;
    if (paramVarArgs.length > 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0) {
      return compareValuesByImpl$ComparisonsKt__ComparisonsKt(paramObject1, paramObject2, paramVarArgs);
    }
    throw ((Throwable)new IllegalArgumentException("Failed requirement.".toString()));
  }
  
  private static final int compareValuesByImpl$ComparisonsKt__ComparisonsKt(Object paramObject1, Object paramObject2, Function1[] paramArrayOfFunction1)
  {
    int j = paramArrayOfFunction1.length;
    int i = 0;
    while (i < j)
    {
      Function1 localFunction1 = paramArrayOfFunction1[i];
      int k = compareValues((Comparable)localFunction1.invoke(paramObject1), (Comparable)localFunction1.invoke(paramObject2));
      if (k != 0) {
        return k;
      }
      i += 1;
    }
    return 0;
  }
  
  public static final Comparator naturalOrder()
  {
    NaturalOrderComparator localNaturalOrderComparator = NaturalOrderComparator.INSTANCE;
    if (localNaturalOrderComparator != null) {
      return (Comparator)localNaturalOrderComparator;
    }
    throw new TypeCastException("null cannot be cast to non-null type kotlin.Comparator<T> /* = java.util.Comparator<T> */");
  }
  
  private static final Comparator nullsFirst()
  {
    return nullsFirst(naturalOrder());
  }
  
  public static final Comparator nullsFirst(Comparator paramComparator)
  {
    Intrinsics.checkParameterIsNotNull(paramComparator, "comparator");
    (Comparator)new Comparator()
    {
      public final int compare(Object paramAnonymousObject1, Object paramAnonymousObject2)
      {
        if (paramAnonymousObject1 == paramAnonymousObject2) {
          return 0;
        }
        if (paramAnonymousObject1 == null) {
          return -1;
        }
        if (paramAnonymousObject2 == null) {
          return 1;
        }
        return ComparisonsKt__ComparisonsKt.this.compare(paramAnonymousObject1, paramAnonymousObject2);
      }
    };
  }
  
  private static final Comparator nullsLast()
  {
    return nullsLast(naturalOrder());
  }
  
  public static final Comparator nullsLast(Comparator paramComparator)
  {
    Intrinsics.checkParameterIsNotNull(paramComparator, "comparator");
    (Comparator)new Comparator()
    {
      public final int compare(Object paramAnonymousObject1, Object paramAnonymousObject2)
      {
        if (paramAnonymousObject1 == paramAnonymousObject2) {
          return 0;
        }
        if (paramAnonymousObject1 == null) {
          return 1;
        }
        if (paramAnonymousObject2 == null) {
          return -1;
        }
        return ComparisonsKt__ComparisonsKt.this.compare(paramAnonymousObject1, paramAnonymousObject2);
      }
    };
  }
  
  public static final Comparator reverseOrder()
  {
    ReverseOrderComparator localReverseOrderComparator = ReverseOrderComparator.INSTANCE;
    if (localReverseOrderComparator != null) {
      return (Comparator)localReverseOrderComparator;
    }
    throw new TypeCastException("null cannot be cast to non-null type kotlin.Comparator<T> /* = java.util.Comparator<T> */");
  }
  
  public static final Comparator reversed(Comparator paramComparator)
  {
    Intrinsics.checkParameterIsNotNull(paramComparator, "$this$reversed");
    if ((paramComparator instanceof ReversedComparator)) {
      return ((ReversedComparator)paramComparator).getComparator();
    }
    if (Intrinsics.areEqual(paramComparator, NaturalOrderComparator.INSTANCE))
    {
      paramComparator = ReverseOrderComparator.INSTANCE;
      if (paramComparator != null) {
        return (Comparator)paramComparator;
      }
      throw new TypeCastException("null cannot be cast to non-null type kotlin.Comparator<T> /* = java.util.Comparator<T> */");
    }
    if (Intrinsics.areEqual(paramComparator, ReverseOrderComparator.INSTANCE))
    {
      paramComparator = NaturalOrderComparator.INSTANCE;
      if (paramComparator != null) {
        return (Comparator)paramComparator;
      }
      throw new TypeCastException("null cannot be cast to non-null type kotlin.Comparator<T> /* = java.util.Comparator<T> */");
    }
    return (Comparator)new ReversedComparator(paramComparator);
  }
  
  public static final Comparator then(Comparator paramComparator1, final Comparator paramComparator2)
  {
    Intrinsics.checkParameterIsNotNull(paramComparator1, "$this$then");
    Intrinsics.checkParameterIsNotNull(paramComparator2, "comparator");
    (Comparator)new Comparator()
    {
      public final int compare(Object paramAnonymousObject1, Object paramAnonymousObject2)
      {
        int i = ComparisonsKt__ComparisonsKt.this.compare(paramAnonymousObject1, paramAnonymousObject2);
        if (i != 0) {
          return i;
        }
        return paramComparator2.compare(paramAnonymousObject1, paramAnonymousObject2);
      }
    };
  }
  
  private static final Comparator thenBy(Comparator paramComparator1, final Comparator paramComparator2, final Function1 paramFunction1)
  {
    (Comparator)new Comparator()
    {
      public final int compare(Object paramAnonymousObject1, Object paramAnonymousObject2)
      {
        int i = ComparisonsKt__ComparisonsKt.this.compare(paramAnonymousObject1, paramAnonymousObject2);
        if (i != 0) {
          return i;
        }
        return paramComparator2.compare(paramFunction1.invoke(paramAnonymousObject1), paramFunction1.invoke(paramAnonymousObject2));
      }
    };
  }
  
  private static final Comparator thenBy(Comparator paramComparator, final Function1 paramFunction1)
  {
    (Comparator)new Comparator()
    {
      public final int compare(Object paramAnonymousObject1, Object paramAnonymousObject2)
      {
        int i = ComparisonsKt__ComparisonsKt.this.compare(paramAnonymousObject1, paramAnonymousObject2);
        if (i != 0) {
          return i;
        }
        return ComparisonsKt__ComparisonsKt.compareValues((Comparable)paramFunction1.invoke(paramAnonymousObject1), (Comparable)paramFunction1.invoke(paramAnonymousObject2));
      }
    };
  }
  
  private static final Comparator thenByDescending(Comparator paramComparator1, final Comparator paramComparator2, final Function1 paramFunction1)
  {
    (Comparator)new Comparator()
    {
      public final int compare(Object paramAnonymousObject1, Object paramAnonymousObject2)
      {
        int i = ComparisonsKt__ComparisonsKt.this.compare(paramAnonymousObject1, paramAnonymousObject2);
        if (i != 0) {
          return i;
        }
        return paramComparator2.compare(paramFunction1.invoke(paramAnonymousObject2), paramFunction1.invoke(paramAnonymousObject1));
      }
    };
  }
  
  private static final Comparator thenByDescending(Comparator paramComparator, final Function1 paramFunction1)
  {
    (Comparator)new Comparator()
    {
      public final int compare(Object paramAnonymousObject1, Object paramAnonymousObject2)
      {
        int i = ComparisonsKt__ComparisonsKt.this.compare(paramAnonymousObject1, paramAnonymousObject2);
        if (i != 0) {
          return i;
        }
        return ComparisonsKt__ComparisonsKt.compareValues((Comparable)paramFunction1.invoke(paramAnonymousObject2), (Comparable)paramFunction1.invoke(paramAnonymousObject1));
      }
    };
  }
  
  private static final Comparator thenComparator(Comparator paramComparator, final Function2 paramFunction2)
  {
    (Comparator)new Comparator()
    {
      public final int compare(Object paramAnonymousObject1, Object paramAnonymousObject2)
      {
        int i = ComparisonsKt__ComparisonsKt.this.compare(paramAnonymousObject1, paramAnonymousObject2);
        if (i != 0) {
          return i;
        }
        return ((Number)paramFunction2.invoke(paramAnonymousObject1, paramAnonymousObject2)).intValue();
      }
    };
  }
  
  public static final Comparator thenDescending(Comparator paramComparator1, final Comparator paramComparator2)
  {
    Intrinsics.checkParameterIsNotNull(paramComparator1, "$this$thenDescending");
    Intrinsics.checkParameterIsNotNull(paramComparator2, "comparator");
    (Comparator)new Comparator()
    {
      public final int compare(Object paramAnonymousObject1, Object paramAnonymousObject2)
      {
        int i = ComparisonsKt__ComparisonsKt.this.compare(paramAnonymousObject1, paramAnonymousObject2);
        if (i != 0) {
          return i;
        }
        return paramComparator2.compare(paramAnonymousObject2, paramAnonymousObject1);
      }
    };
  }
}
