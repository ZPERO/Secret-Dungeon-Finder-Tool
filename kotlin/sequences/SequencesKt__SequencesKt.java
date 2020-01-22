package kotlin.sequences;

import java.util.ArrayList;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

@Metadata(bv={1, 0, 3}, d1={"\000@\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\020(\n\002\b\002\n\002\020\000\n\002\b\002\n\002\030\002\n\002\b\004\n\002\020\021\n\002\b\006\n\002\020\034\n\002\b\005\n\002\030\002\n\002\020 \n\000\032+\020\000\032\b\022\004\022\002H\0020\001\"\004\b\000\020\0022\024\b\004\020\003\032\016\022\n\022\b\022\004\022\002H\0020\0050\004H?\b\032\022\020\006\032\b\022\004\022\002H\0020\001\"\004\b\000\020\002\032&\020\007\032\b\022\004\022\002H\0020\001\"\b\b\000\020\002*\0020\b2\016\020\t\032\n\022\006\022\004\030\001H\0020\004\032<\020\007\032\b\022\004\022\002H\0020\001\"\b\b\000\020\002*\0020\b2\016\020\n\032\n\022\006\022\004\030\001H\0020\0042\024\020\t\032\020\022\004\022\002H\002\022\006\022\004\030\001H\0020\013\032=\020\007\032\b\022\004\022\002H\0020\001\"\b\b\000\020\002*\0020\b2\b\020\f\032\004\030\001H\0022\024\020\t\032\020\022\004\022\002H\002\022\006\022\004\030\001H\0020\013H\007?\006\002\020\r\032+\020\016\032\b\022\004\022\002H\0020\001\"\004\b\000\020\0022\022\020\017\032\n\022\006\b\001\022\002H\0020\020\"\002H\002?\006\002\020\021\032\034\020\022\032\b\022\004\022\002H\0020\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\005\032\034\020\023\032\b\022\004\022\002H\0020\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\001\032C\020\024\032\b\022\004\022\002H\0250\001\"\004\b\000\020\002\"\004\b\001\020\025*\b\022\004\022\002H\0020\0012\030\020\003\032\024\022\004\022\002H\002\022\n\022\b\022\004\022\002H\0250\0050\013H\002?\006\002\b\026\032)\020\024\032\b\022\004\022\002H\0020\001\"\004\b\000\020\002*\016\022\n\022\b\022\004\022\002H\0020\0270\001H\007?\006\002\b\030\032\"\020\024\032\b\022\004\022\002H\0020\001\"\004\b\000\020\002*\016\022\n\022\b\022\004\022\002H\0020\0010\001\0322\020\031\032\b\022\004\022\002H\0020\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\0012\022\020\032\032\016\022\n\022\b\022\004\022\002H\0020\0010\004H\007\032!\020\033\032\b\022\004\022\002H\0020\001\"\004\b\000\020\002*\n\022\004\022\002H\002\030\0010\001H?\b\032@\020\034\032\032\022\n\022\b\022\004\022\002H\0020\036\022\n\022\b\022\004\022\002H\0250\0360\035\"\004\b\000\020\002\"\004\b\001\020\025*\024\022\020\022\016\022\004\022\002H\002\022\004\022\002H\0250\0350\001?\006\037"}, d2={"Sequence", "Lkotlin/sequences/Sequence;", "T", "iterator", "Lkotlin/Function0;", "", "emptySequence", "generateSequence", "", "nextFunction", "seedFunction", "Lkotlin/Function1;", "seed", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)Lkotlin/sequences/Sequence;", "sequenceOf", "elements", "", "([Ljava/lang/Object;)Lkotlin/sequences/Sequence;", "asSequence", "constrainOnce", "flatten", "R", "flatten$SequencesKt__SequencesKt", "", "flattenSequenceOfIterable", "ifEmpty", "defaultValue", "orEmpty", "unzip", "Lkotlin/Pair;", "", "kotlin-stdlib"}, k=5, mv={1, 1, 15}, xi=1, xs="kotlin/sequences/SequencesKt")
class SequencesKt__SequencesKt
  extends SequencesKt__SequencesJVMKt
{
  public SequencesKt__SequencesKt() {}
  
  private static final Sequence Sequence(Function0 paramFunction0)
  {
    (Sequence)new Sequence()
    {
      public Iterator iterator()
      {
        return (Iterator)invoke();
      }
    };
  }
  
  public static final Sequence asSequence(Iterator paramIterator)
  {
    Intrinsics.checkParameterIsNotNull(paramIterator, "$this$asSequence");
    constrainOnce((Sequence)new Sequence()
    {
      public Iterator iterator()
      {
        return SequencesKt__SequencesKt.this;
      }
    });
  }
  
  public static final Sequence constrainOnce(Sequence paramSequence)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$constrainOnce");
    if ((paramSequence instanceof ConstrainedOnceSequence)) {
      return paramSequence;
    }
    return (Sequence)new ConstrainedOnceSequence(paramSequence);
  }
  
  public static final Sequence emptySequence()
  {
    return (Sequence)EmptySequence.INSTANCE;
  }
  
  public static final Sequence flatten(Sequence paramSequence)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$flatten");
    return flatten$SequencesKt__SequencesKt(paramSequence, (Function1)flatten.1.INSTANCE);
  }
  
  private static final Sequence flatten$SequencesKt__SequencesKt(Sequence paramSequence, Function1 paramFunction1)
  {
    if ((paramSequence instanceof TransformingSequence)) {
      return ((TransformingSequence)paramSequence).flatten$kotlin_stdlib(paramFunction1);
    }
    return (Sequence)new FlatteningSequence(paramSequence, (Function1)flatten.3.INSTANCE, paramFunction1);
  }
  
  public static final Sequence flattenSequenceOfIterable(Sequence paramSequence)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$flatten");
    return flatten$SequencesKt__SequencesKt(paramSequence, (Function1)flatten.2.INSTANCE);
  }
  
  public static final Sequence generateSequence(Object paramObject, Function1 paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramFunction1, "nextFunction");
    if (paramObject == null) {
      return (Sequence)EmptySequence.INSTANCE;
    }
    (Sequence)new GeneratorSequence((Function0)new Lambda(paramObject)
    {
      public final Object invoke()
      {
        return SequencesKt__SequencesKt.this;
      }
    }, paramFunction1);
  }
  
  public static final Sequence generateSequence(Function0 paramFunction0)
  {
    Intrinsics.checkParameterIsNotNull(paramFunction0, "nextFunction");
    constrainOnce((Sequence)new GeneratorSequence(paramFunction0, (Function1)new Lambda(paramFunction0)
    {
      public final Object invoke(Object paramAnonymousObject)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousObject, "it");
        return invoke();
      }
    }));
  }
  
  public static final Sequence generateSequence(Function0 paramFunction0, Function1 paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramFunction0, "seedFunction");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "nextFunction");
    return (Sequence)new GeneratorSequence(paramFunction0, paramFunction1);
  }
  
  public static final Sequence ifEmpty(Sequence paramSequence, final Function0 paramFunction0)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$ifEmpty");
    Intrinsics.checkParameterIsNotNull(paramFunction0, "defaultValue");
    SequencesKt__SequenceBuilderKt.sequence((Function2)new kotlin.coroutines.stats.internal.RestrictedSuspendLambda(paramSequence, paramFunction0)
    {
      Object hint;
      int label;
      Object owner;
      private SequenceScope prototype;
      
      public final Continuation create(Object paramAnonymousObject, Continuation paramAnonymousContinuation)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousContinuation, "completion");
        paramAnonymousContinuation = new 1(SequencesKt__SequencesKt.this, paramFunction0, paramAnonymousContinuation);
        prototype = ((SequenceScope)paramAnonymousObject);
        return paramAnonymousContinuation;
      }
      
      public final Object invoke(Object paramAnonymousObject1, Object paramAnonymousObject2)
      {
        return ((1)create(paramAnonymousObject1, (Continuation)paramAnonymousObject2)).invokeSuspend(Unit.INSTANCE);
      }
      
      public final Object invokeSuspend(Object paramAnonymousObject)
      {
        Object localObject = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = label;
        if (i != 0)
        {
          if ((i != 1) && (i != 2)) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
          }
          localObject = (Iterator)owner;
          localObject = (SequenceScope)hint;
          ResultKt.throwOnFailure(paramAnonymousObject);
        }
        else
        {
          ResultKt.throwOnFailure(paramAnonymousObject);
          paramAnonymousObject = prototype;
          Iterator localIterator = iterator();
          if (localIterator.hasNext())
          {
            hint = paramAnonymousObject;
            owner = localIterator;
            label = 1;
            if (paramAnonymousObject.yieldAll(localIterator, this) == localObject) {
              return localObject;
            }
          }
          else
          {
            Sequence localSequence = (Sequence)paramFunction0.invoke();
            hint = paramAnonymousObject;
            owner = localIterator;
            label = 2;
            if (paramAnonymousObject.yieldAll(localSequence, this) == localObject) {
              return localObject;
            }
          }
        }
        return Unit.INSTANCE;
      }
    });
  }
  
  private static final Sequence orEmpty(Sequence paramSequence)
  {
    if (paramSequence != null) {
      return paramSequence;
    }
    return emptySequence();
  }
  
  public static final Sequence sequenceOf(Object... paramVarArgs)
  {
    Intrinsics.checkParameterIsNotNull(paramVarArgs, "elements");
    int i;
    if (paramVarArgs.length == 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0) {
      return emptySequence();
    }
    return ArraysKt___ArraysKt.asSequence(paramVarArgs);
  }
  
  public static final Pair unzip(Sequence paramSequence)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$unzip");
    ArrayList localArrayList1 = new ArrayList();
    ArrayList localArrayList2 = new ArrayList();
    paramSequence = paramSequence.iterator();
    while (paramSequence.hasNext())
    {
      Pair localPair = (Pair)paramSequence.next();
      localArrayList1.add(localPair.getFirst());
      localArrayList2.add(localPair.getSecond());
    }
    return TuplesKt.to(localArrayList1, localArrayList2);
  }
}
