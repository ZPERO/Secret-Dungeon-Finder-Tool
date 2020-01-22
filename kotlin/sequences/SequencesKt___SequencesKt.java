package kotlin.sequences;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysJvmKt;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt__MutableCollectionsJVMKt;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.Grouping;
import kotlin.collections.IndexedValue;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.collections.SlidingWindowKt;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt.compareBy.2;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt.compareByDescending.1;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.internal.PlatformImplementationsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DoubleCompanionObject;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Ref.BooleanRef;
import kotlin.jvm.internal.markers.KMappedMarker;
import kotlin.text.StringsKt__StringBuilderKt;

@Metadata(bv={1, 0, 3}, d1={"\000?\002\n\000\n\002\020\013\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\034\n\002\b\002\n\002\020$\n\002\b\003\n\002\030\002\n\002\b\005\n\002\020%\n\002\b\b\n\002\020\006\n\002\020\005\n\002\b\002\n\002\020\007\n\000\n\002\020\b\n\000\n\002\020\t\n\000\n\002\020\n\n\002\b\002\n\002\020 \n\002\b\003\n\002\030\002\n\002\b\022\n\002\030\002\n\002\030\002\n\002\b\003\n\002\020\037\n\002\b\002\n\002\030\002\n\002\b\004\n\002\020\000\n\002\b\022\n\002\030\002\n\002\b\002\n\002\020\002\n\002\b\004\n\002\020!\n\000\n\002\030\002\n\002\b\006\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\r\n\002\b\006\n\002\020\016\n\002\b\f\n\002\020\017\n\002\b\006\n\002\030\002\n\002\030\002\n\002\b\007\n\002\020\021\n\002\b!\n\002\030\002\n\002\030\002\n\002\b\003\n\002\020#\n\000\n\002\020\"\n\002\b\004\n\002\030\002\n\002\b\006\032-\020\000\032\0020\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\022\020\004\032\016\022\004\022\002H\002\022\004\022\0020\0010\005H?\b\032\026\020\006\032\0020\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\003\032-\020\006\032\0020\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\022\020\004\032\016\022\004\022\002H\002\022\004\022\0020\0010\005H?\b\032\034\020\007\032\b\022\004\022\002H\0020\b\"\004\b\000\020\002*\b\022\004\022\002H\0020\003\032\037\020\t\032\b\022\004\022\002H\0020\003\"\004\b\000\020\002*\b\022\004\022\002H\0020\003H?\b\032Q\020\n\032\016\022\004\022\002H\f\022\004\022\002H\r0\013\"\004\b\000\020\002\"\004\b\001\020\f\"\004\b\002\020\r*\b\022\004\022\002H\0020\0032\036\020\016\032\032\022\004\022\002H\002\022\020\022\016\022\004\022\002H\f\022\004\022\002H\r0\0170\005H?\b\032?\020\020\032\016\022\004\022\002H\f\022\004\022\002H\0020\013\"\004\b\000\020\002\"\004\b\001\020\f*\b\022\004\022\002H\0020\0032\022\020\021\032\016\022\004\022\002H\002\022\004\022\002H\f0\005H?\b\032Y\020\020\032\016\022\004\022\002H\f\022\004\022\002H\r0\013\"\004\b\000\020\002\"\004\b\001\020\f\"\004\b\002\020\r*\b\022\004\022\002H\0020\0032\022\020\021\032\016\022\004\022\002H\002\022\004\022\002H\f0\0052\022\020\022\032\016\022\004\022\002H\002\022\004\022\002H\r0\005H?\b\032Z\020\023\032\002H\024\"\004\b\000\020\002\"\004\b\001\020\f\"\030\b\002\020\024*\022\022\006\b\000\022\002H\f\022\006\b\000\022\002H\0020\025*\b\022\004\022\002H\0020\0032\006\020\026\032\002H\0242\022\020\021\032\016\022\004\022\002H\002\022\004\022\002H\f0\005H?\b?\006\002\020\027\032t\020\023\032\002H\024\"\004\b\000\020\002\"\004\b\001\020\f\"\004\b\002\020\r\"\030\b\003\020\024*\022\022\006\b\000\022\002H\f\022\006\b\000\022\002H\r0\025*\b\022\004\022\002H\0020\0032\006\020\026\032\002H\0242\022\020\021\032\016\022\004\022\002H\002\022\004\022\002H\f0\0052\022\020\022\032\016\022\004\022\002H\002\022\004\022\002H\r0\005H?\b?\006\002\020\030\032l\020\031\032\002H\024\"\004\b\000\020\002\"\004\b\001\020\f\"\004\b\002\020\r\"\030\b\003\020\024*\022\022\006\b\000\022\002H\f\022\006\b\000\022\002H\r0\025*\b\022\004\022\002H\0020\0032\006\020\026\032\002H\0242\036\020\016\032\032\022\004\022\002H\002\022\020\022\016\022\004\022\002H\f\022\004\022\002H\r0\0170\005H?\b?\006\002\020\027\032?\020\032\032\016\022\004\022\002H\f\022\004\022\002H\r0\013\"\004\b\000\020\f\"\004\b\001\020\r*\b\022\004\022\002H\f0\0032\022\020\033\032\016\022\004\022\002H\f\022\004\022\002H\r0\005H?\b\032Z\020\034\032\002H\024\"\004\b\000\020\f\"\004\b\001\020\r\"\030\b\002\020\024*\022\022\006\b\000\022\002H\f\022\006\b\000\022\002H\r0\025*\b\022\004\022\002H\f0\0032\006\020\026\032\002H\0242\022\020\033\032\016\022\004\022\002H\f\022\004\022\002H\r0\005H?\b?\006\002\020\027\032\027\020\035\032\0020\036*\b\022\004\022\0020\0370\003H\007?\006\002\b \032\027\020\035\032\0020\036*\b\022\004\022\0020\0360\003H\007?\006\002\b!\032\027\020\035\032\0020\036*\b\022\004\022\0020\"0\003H\007?\006\002\b#\032\027\020\035\032\0020\036*\b\022\004\022\0020$0\003H\007?\006\002\b%\032\027\020\035\032\0020\036*\b\022\004\022\0020&0\003H\007?\006\002\b'\032\027\020\035\032\0020\036*\b\022\004\022\0020(0\003H\007?\006\002\b)\032,\020*\032\016\022\n\022\b\022\004\022\002H\0020+0\003\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\006\020,\032\0020$H\007\032F\020*\032\b\022\004\022\002H-0\003\"\004\b\000\020\002\"\004\b\001\020-*\b\022\004\022\002H\0020\0032\006\020,\032\0020$2\030\020\016\032\024\022\n\022\b\022\004\022\002H\0020+\022\004\022\002H-0\005H\007\032+\020.\032\0020\001\"\t\b\000\020\002?\006\002\b/*\b\022\004\022\002H\0020\0032\006\0200\032\002H\002H?\002?\006\002\0201\032\026\0202\032\0020$\"\004\b\000\020\002*\b\022\004\022\002H\0020\003\032-\0202\032\0020$\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\022\020\004\032\016\022\004\022\002H\002\022\004\022\0020\0010\005H?\b\032\034\0203\032\b\022\004\022\002H\0020\003\"\004\b\000\020\002*\b\022\004\022\002H\0020\003\0326\0204\032\b\022\004\022\002H\0020\003\"\004\b\000\020\002\"\004\b\001\020\f*\b\022\004\022\002H\0020\0032\022\0205\032\016\022\004\022\002H\002\022\004\022\002H\f0\005\032$\0206\032\b\022\004\022\002H\0020\003\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\006\0207\032\0020$\0320\0208\032\b\022\004\022\002H\0020\003\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\022\020\004\032\016\022\004\022\002H\002\022\004\022\0020\0010\005\032#\0209\032\002H\002\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\006\020:\032\0020$?\006\002\020;\0327\020<\032\002H\002\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\006\020:\032\0020$2\022\020=\032\016\022\004\022\0020$\022\004\022\002H\0020\005?\006\002\020>\032%\020?\032\004\030\001H\002\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\006\020:\032\0020$?\006\002\020;\0320\020@\032\b\022\004\022\002H\0020\003\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\022\020\004\032\016\022\004\022\002H\002\022\004\022\0020\0010\005\032E\020A\032\b\022\004\022\002H\0020\003\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032'\020\004\032#\022\023\022\0210$?\006\f\bC\022\b\bD\022\004\b\b(:\022\004\022\002H\002\022\004\022\0020\0010B\032a\020E\032\002HF\"\004\b\000\020\002\"\020\b\001\020F*\n\022\006\b\000\022\002H\0020G*\b\022\004\022\002H\0020\0032\006\020\026\032\002HF2'\020\004\032#\022\023\022\0210$?\006\f\bC\022\b\bD\022\004\b\b(:\022\004\022\002H\002\022\004\022\0020\0010BH?\b?\006\002\020H\032$\020I\032\r\022\t\022\007H-?\006\002\bJ0\003\"\006\b\000\020-\030\001*\006\022\002\b\0030\003H?\b\0328\020K\032\002HF\"\006\b\000\020-\030\001\"\020\b\001\020F*\n\022\006\b\000\022\002H-0G*\006\022\002\b\0030\0032\006\020\026\032\002HFH?\b?\006\002\020L\0320\020M\032\b\022\004\022\002H\0020\003\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\022\020\004\032\016\022\004\022\002H\002\022\004\022\0020\0010\005\032\"\020N\032\b\022\004\022\002H\0020\003\"\b\b\000\020\002*\0020O*\n\022\006\022\004\030\001H\0020\003\032;\020P\032\002HF\"\020\b\000\020F*\n\022\006\b\000\022\002H\0020G\"\b\b\001\020\002*\0020O*\n\022\006\022\004\030\001H\0020\0032\006\020\026\032\002HF?\006\002\020L\032L\020Q\032\002HF\"\004\b\000\020\002\"\020\b\001\020F*\n\022\006\b\000\022\002H\0020G*\b\022\004\022\002H\0020\0032\006\020\026\032\002HF2\022\020\004\032\016\022\004\022\002H\002\022\004\022\0020\0010\005H?\b?\006\002\020R\032L\020S\032\002HF\"\004\b\000\020\002\"\020\b\001\020F*\n\022\006\b\000\022\002H\0020G*\b\022\004\022\002H\0020\0032\006\020\026\032\002HF2\022\020\004\032\016\022\004\022\002H\002\022\004\022\0020\0010\005H?\b?\006\002\020R\0324\020T\032\004\030\001H\002\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\022\020\004\032\016\022\004\022\002H\002\022\004\022\0020\0010\005H?\b?\006\002\020U\0324\020V\032\004\030\001H\002\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\022\020\004\032\016\022\004\022\002H\002\022\004\022\0020\0010\005H?\b?\006\002\020U\032\033\020W\032\002H\002\"\004\b\000\020\002*\b\022\004\022\002H\0020\003?\006\002\020X\0322\020W\032\002H\002\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\022\020\004\032\016\022\004\022\002H\002\022\004\022\0020\0010\005H?\b?\006\002\020U\032\035\020Y\032\004\030\001H\002\"\004\b\000\020\002*\b\022\004\022\002H\0020\003?\006\002\020X\0324\020Y\032\004\030\001H\002\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\022\020\004\032\016\022\004\022\002H\002\022\004\022\0020\0010\005H?\b?\006\002\020U\032<\020Z\032\b\022\004\022\002H-0\003\"\004\b\000\020\002\"\004\b\001\020-*\b\022\004\022\002H\0020\0032\030\020\016\032\024\022\004\022\002H\002\022\n\022\b\022\004\022\002H-0\0030\005\032X\020[\032\002HF\"\004\b\000\020\002\"\004\b\001\020-\"\020\b\002\020F*\n\022\006\b\000\022\002H-0G*\b\022\004\022\002H\0020\0032\006\020\026\032\002HF2\030\020\016\032\024\022\004\022\002H\002\022\n\022\b\022\004\022\002H-0\0030\005H?\b?\006\002\020R\032U\020\\\032\002H-\"\004\b\000\020\002\"\004\b\001\020-*\b\022\004\022\002H\0020\0032\006\020]\032\002H-2'\020^\032#\022\023\022\021H-?\006\f\bC\022\b\bD\022\004\b\b(_\022\004\022\002H\002\022\004\022\002H-0BH?\b?\006\002\020`\032j\020a\032\002H-\"\004\b\000\020\002\"\004\b\001\020-*\b\022\004\022\002H\0020\0032\006\020]\032\002H-2<\020^\0328\022\023\022\0210$?\006\f\bC\022\b\bD\022\004\b\b(:\022\023\022\021H-?\006\f\bC\022\b\bD\022\004\b\b(_\022\004\022\002H\002\022\004\022\002H-0bH?\b?\006\002\020c\032-\020d\032\0020e\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\022\020f\032\016\022\004\022\002H\002\022\004\022\0020e0\005H?\b\032B\020g\032\0020e\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032'\020f\032#\022\023\022\0210$?\006\f\bC\022\b\bD\022\004\b\b(:\022\004\022\002H\002\022\004\022\0020e0BH?\b\032E\020h\032\024\022\004\022\002H\f\022\n\022\b\022\004\022\002H\0020+0\013\"\004\b\000\020\002\"\004\b\001\020\f*\b\022\004\022\002H\0020\0032\022\020\021\032\016\022\004\022\002H\002\022\004\022\002H\f0\005H?\b\032_\020h\032\024\022\004\022\002H\f\022\n\022\b\022\004\022\002H\r0+0\013\"\004\b\000\020\002\"\004\b\001\020\f\"\004\b\002\020\r*\b\022\004\022\002H\0020\0032\022\020\021\032\016\022\004\022\002H\002\022\004\022\002H\f0\0052\022\020\022\032\016\022\004\022\002H\002\022\004\022\002H\r0\005H?\b\032^\020i\032\002H\024\"\004\b\000\020\002\"\004\b\001\020\f\"\034\b\002\020\024*\026\022\006\b\000\022\002H\f\022\n\022\b\022\004\022\002H\0020j0\025*\b\022\004\022\002H\0020\0032\006\020\026\032\002H\0242\022\020\021\032\016\022\004\022\002H\002\022\004\022\002H\f0\005H?\b?\006\002\020\027\032x\020i\032\002H\024\"\004\b\000\020\002\"\004\b\001\020\f\"\004\b\002\020\r\"\034\b\003\020\024*\026\022\006\b\000\022\002H\f\022\n\022\b\022\004\022\002H\r0j0\025*\b\022\004\022\002H\0020\0032\006\020\026\032\002H\0242\022\020\021\032\016\022\004\022\002H\002\022\004\022\002H\f0\0052\022\020\022\032\016\022\004\022\002H\002\022\004\022\002H\r0\005H?\b?\006\002\020\030\032A\020k\032\016\022\004\022\002H\002\022\004\022\002H\f0l\"\004\b\000\020\002\"\004\b\001\020\f*\b\022\004\022\002H\0020\0032\024\b\004\020\021\032\016\022\004\022\002H\002\022\004\022\002H\f0\005H?\b\032(\020m\032\0020$\"\t\b\000\020\002?\006\002\b/*\b\022\004\022\002H\0020\0032\006\0200\032\002H\002?\006\002\020n\032-\020o\032\0020$\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\022\020\004\032\016\022\004\022\002H\002\022\004\022\0020\0010\005H?\b\032-\020p\032\0020$\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\022\020\004\032\016\022\004\022\002H\002\022\004\022\0020\0010\005H?\b\032{\020q\032\002Hr\"\004\b\000\020\002\"\f\b\001\020r*\0060sj\002`t*\b\022\004\022\002H\0020\0032\006\020u\032\002Hr2\b\b\002\020v\032\0020w2\b\b\002\020x\032\0020w2\b\b\002\020y\032\0020w2\b\b\002\020z\032\0020$2\b\b\002\020{\032\0020w2\026\b\002\020\016\032\020\022\004\022\002H\002\022\004\022\0020w\030\0010\005?\006\002\020|\032`\020}\032\0020~\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\b\b\002\020v\032\0020w2\b\b\002\020x\032\0020w2\b\b\002\020y\032\0020w2\b\b\002\020z\032\0020$2\b\b\002\020{\032\0020w2\026\b\002\020\016\032\020\022\004\022\002H\002\022\004\022\0020w\030\0010\005\032\033\020\032\002H\002\"\004\b\000\020\002*\b\022\004\022\002H\0020\003?\006\002\020X\0322\020\032\002H\002\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\022\020\004\032\016\022\004\022\002H\002\022\004\022\0020\0010\005H?\b?\006\002\020U\032)\020?\001\032\0020$\"\t\b\000\020\002?\006\002\b/*\b\022\004\022\002H\0020\0032\006\0200\032\002H\002?\006\002\020n\032\036\020?\001\032\004\030\001H\002\"\004\b\000\020\002*\b\022\004\022\002H\0020\003?\006\002\020X\0325\020?\001\032\004\030\001H\002\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\022\020\004\032\016\022\004\022\002H\002\022\004\022\0020\0010\005H?\b?\006\002\020U\0327\020?\001\032\b\022\004\022\002H-0\003\"\004\b\000\020\002\"\004\b\001\020-*\b\022\004\022\002H\0020\0032\022\020\016\032\016\022\004\022\002H\002\022\004\022\002H-0\005\032L\020?\001\032\b\022\004\022\002H-0\003\"\004\b\000\020\002\"\004\b\001\020-*\b\022\004\022\002H\0020\0032'\020\016\032#\022\023\022\0210$?\006\f\bC\022\b\bD\022\004\b\b(:\022\004\022\002H\002\022\004\022\002H-0B\032R\020?\001\032\b\022\004\022\002H-0\003\"\004\b\000\020\002\"\b\b\001\020-*\0020O*\b\022\004\022\002H\0020\0032)\020\016\032%\022\023\022\0210$?\006\f\bC\022\b\bD\022\004\b\b(:\022\004\022\002H\002\022\006\022\004\030\001H-0B\032n\020?\001\032\002HF\"\004\b\000\020\002\"\b\b\001\020-*\0020O\"\020\b\002\020F*\n\022\006\b\000\022\002H-0G*\b\022\004\022\002H\0020\0032\006\020\026\032\002HF2)\020\016\032%\022\023\022\0210$?\006\f\bC\022\b\bD\022\004\b\b(:\022\004\022\002H\002\022\006\022\004\030\001H-0BH?\b?\006\002\020H\032h\020?\001\032\002HF\"\004\b\000\020\002\"\004\b\001\020-\"\020\b\002\020F*\n\022\006\b\000\022\002H-0G*\b\022\004\022\002H\0020\0032\006\020\026\032\002HF2'\020\016\032#\022\023\022\0210$?\006\f\bC\022\b\bD\022\004\b\b(:\022\004\022\002H\002\022\004\022\002H-0BH?\b?\006\002\020H\032=\020?\001\032\b\022\004\022\002H-0\003\"\004\b\000\020\002\"\b\b\001\020-*\0020O*\b\022\004\022\002H\0020\0032\024\020\016\032\020\022\004\022\002H\002\022\006\022\004\030\001H-0\005\032Y\020?\001\032\002HF\"\004\b\000\020\002\"\b\b\001\020-*\0020O\"\020\b\002\020F*\n\022\006\b\000\022\002H-0G*\b\022\004\022\002H\0020\0032\006\020\026\032\002HF2\024\020\016\032\020\022\004\022\002H\002\022\006\022\004\030\001H-0\005H?\b?\006\002\020R\032S\020?\001\032\002HF\"\004\b\000\020\002\"\004\b\001\020-\"\020\b\002\020F*\n\022\006\b\000\022\002H-0G*\b\022\004\022\002H\0020\0032\006\020\026\032\002HF2\022\020\016\032\016\022\004\022\002H\002\022\004\022\002H-0\005H?\b?\006\002\020R\032*\020?\001\032\004\030\001H\002\"\017\b\000\020\002*\t\022\004\022\002H\0020?\001*\b\022\004\022\002H\0020\003?\006\003\020?\001\032\033\020?\001\032\004\030\0010\036*\b\022\004\022\0020\0360\003H\007?\006\003\020?\001\032\033\020?\001\032\004\030\0010\"*\b\022\004\022\0020\"0\003H\007?\006\003\020?\001\032F\020?\001\032\004\030\001H\002\"\004\b\000\020\002\"\017\b\001\020-*\t\022\004\022\002H-0?\001*\b\022\004\022\002H\0020\0032\022\0205\032\016\022\004\022\002H\002\022\004\022\002H-0\005H?\b?\006\002\020U\032>\020?\001\032\004\030\001H\002\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\035\020?\001\032\030\022\006\b\000\022\002H\0020?\001j\013\022\006\b\000\022\002H\002`?\001?\006\003\020?\001\032*\020?\001\032\004\030\001H\002\"\017\b\000\020\002*\t\022\004\022\002H\0020?\001*\b\022\004\022\002H\0020\003?\006\003\020?\001\032\033\020?\001\032\004\030\0010\036*\b\022\004\022\0020\0360\003H\007?\006\003\020?\001\032\033\020?\001\032\004\030\0010\"*\b\022\004\022\0020\"0\003H\007?\006\003\020?\001\032F\020?\001\032\004\030\001H\002\"\004\b\000\020\002\"\017\b\001\020-*\t\022\004\022\002H-0?\001*\b\022\004\022\002H\0020\0032\022\0205\032\016\022\004\022\002H\002\022\004\022\002H-0\005H?\b?\006\002\020U\032>\020?\001\032\004\030\001H\002\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\035\020?\001\032\030\022\006\b\000\022\002H\0020?\001j\013\022\006\b\000\022\002H\002`?\001?\006\003\020?\001\032.\020?\001\032\b\022\004\022\002H\0020\003\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\006\0200\032\002H\002H?\002?\006\003\020?\001\0328\020?\001\032\b\022\004\022\002H\0020\003\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\020\020?\001\032\013\022\006\b\001\022\002H\0020?\001H?\002?\006\003\020?\001\032/\020?\001\032\b\022\004\022\002H\0020\003\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\r\020?\001\032\b\022\004\022\002H\0020\bH?\002\032/\020?\001\032\b\022\004\022\002H\0020\003\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\r\020?\001\032\b\022\004\022\002H\0020\003H?\002\032.\020?\001\032\b\022\004\022\002H\0020\003\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\006\0200\032\002H\002H?\b?\006\003\020?\001\032\027\020?\001\032\0020\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\003\032.\020?\001\032\0020\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\022\020\004\032\016\022\004\022\002H\002\022\004\022\0020\0010\005H?\b\0323\020?\001\032\b\022\004\022\002H\0020\003\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\022\020f\032\016\022\004\022\002H\002\022\004\022\0020e0\005H\007\032F\020?\001\032\032\022\n\022\b\022\004\022\002H\0020+\022\n\022\b\022\004\022\002H\0020+0\017\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\022\020\004\032\016\022\004\022\002H\002\022\004\022\0020\0010\005H?\b\032.\020?\001\032\b\022\004\022\002H\0020\003\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\006\0200\032\002H\002H?\002?\006\003\020?\001\0328\020?\001\032\b\022\004\022\002H\0020\003\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\020\020?\001\032\013\022\006\b\001\022\002H\0020?\001H?\002?\006\003\020?\001\032/\020?\001\032\b\022\004\022\002H\0020\003\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\r\020?\001\032\b\022\004\022\002H\0020\bH?\002\032/\020?\001\032\b\022\004\022\002H\0020\003\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\r\020?\001\032\b\022\004\022\002H\0020\003H?\002\032.\020?\001\032\b\022\004\022\002H\0020\003\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\006\0200\032\002H\002H?\b?\006\003\020?\001\032X\020?\001\032\003H?\001\"\005\b\000\020?\001\"\t\b\001\020\002*\003H?\001*\b\022\004\022\002H\0020\0032)\020^\032%\022\024\022\022H?\001?\006\f\bC\022\b\bD\022\004\b\b(_\022\004\022\002H\002\022\005\022\003H?\0010BH?\b?\006\003\020?\001\032m\020?\001\032\003H?\001\"\005\b\000\020?\001\"\t\b\001\020\002*\003H?\001*\b\022\004\022\002H\0020\0032>\020^\032:\022\023\022\0210$?\006\f\bC\022\b\bD\022\004\b\b(:\022\024\022\022H?\001?\006\f\bC\022\b\bD\022\004\b\b(_\022\004\022\002H\002\022\005\022\003H?\0010bH?\b?\006\003\020?\001\032#\020?\001\032\b\022\004\022\002H\0020\003\"\b\b\000\020\002*\0020O*\n\022\006\022\004\030\001H\0020\003\032\034\020?\001\032\002H\002\"\004\b\000\020\002*\b\022\004\022\002H\0020\003?\006\002\020X\0323\020?\001\032\002H\002\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\022\020\004\032\016\022\004\022\002H\002\022\004\022\0020\0010\005H?\b?\006\002\020U\032\036\020?\001\032\004\030\001H\002\"\004\b\000\020\002*\b\022\004\022\002H\0020\003?\006\002\020X\0325\020?\001\032\004\030\001H\002\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\022\020\004\032\016\022\004\022\002H\002\022\004\022\0020\0010\005H?\b?\006\002\020U\032(\020?\001\032\b\022\004\022\002H\0020\003\"\017\b\000\020\002*\t\022\004\022\002H\0020?\001*\b\022\004\022\002H\0020\003\032I\020?\001\032\b\022\004\022\002H\0020\003\"\004\b\000\020\002\"\017\b\001\020-*\t\022\004\022\002H-0?\001*\b\022\004\022\002H\0020\0032\026\b\004\0205\032\020\022\004\022\002H\002\022\006\022\004\030\001H-0\005H?\b\032I\020?\001\032\b\022\004\022\002H\0020\003\"\004\b\000\020\002\"\017\b\001\020-*\t\022\004\022\002H-0?\001*\b\022\004\022\002H\0020\0032\026\b\004\0205\032\020\022\004\022\002H\002\022\006\022\004\030\001H-0\005H?\b\032(\020?\001\032\b\022\004\022\002H\0020\003\"\017\b\000\020\002*\t\022\004\022\002H\0020?\001*\b\022\004\022\002H\0020\003\032<\020?\001\032\b\022\004\022\002H\0020\003\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\035\020?\001\032\030\022\006\b\000\022\002H\0020?\001j\013\022\006\b\000\022\002H\002`?\001\032\031\020?\001\032\0020$*\b\022\004\022\0020\0370\003H\007?\006\003\b?\001\032\031\020?\001\032\0020\036*\b\022\004\022\0020\0360\003H\007?\006\003\b?\001\032\031\020?\001\032\0020\"*\b\022\004\022\0020\"0\003H\007?\006\003\b?\001\032\031\020?\001\032\0020$*\b\022\004\022\0020$0\003H\007?\006\003\b?\001\032\031\020?\001\032\0020&*\b\022\004\022\0020&0\003H\007?\006\003\b?\001\032\031\020?\001\032\0020$*\b\022\004\022\0020(0\003H\007?\006\003\b?\001\032.\020?\001\032\0020$\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\022\0205\032\016\022\004\022\002H\002\022\004\022\0020$0\005H?\b\032.\020?\001\032\0020\036\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\022\0205\032\016\022\004\022\002H\002\022\004\022\0020\0360\005H?\b\032%\020?\001\032\b\022\004\022\002H\0020\003\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\006\0207\032\0020$\0321\020?\001\032\b\022\004\022\002H\0020\003\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\022\020\004\032\016\022\004\022\002H\002\022\004\022\0020\0010\005\0326\020?\001\032\002HF\"\004\b\000\020\002\"\020\b\001\020F*\n\022\006\b\000\022\002H\0020G*\b\022\004\022\002H\0020\0032\006\020\026\032\002HF?\006\002\020L\032)\020?\001\032\024\022\004\022\002H\0020?\001j\t\022\004\022\002H\002`?\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\003\032\035\020?\001\032\b\022\004\022\002H\0020+\"\004\b\000\020\002*\b\022\004\022\002H\0020\003\032\035\020?\001\032\b\022\004\022\002H\0020j\"\004\b\000\020\002*\b\022\004\022\002H\0020\003\032\036\020?\001\032\t\022\004\022\002H\0020?\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\003\032\036\020?\001\032\t\022\004\022\002H\0020?\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\003\032C\020?\001\032\016\022\n\022\b\022\004\022\002H\0020+0\003\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\006\020,\032\0020$2\t\b\002\020?\001\032\0020$2\t\b\002\020?\001\032\0020\001H\007\032]\020?\001\032\b\022\004\022\002H-0\003\"\004\b\000\020\002\"\004\b\001\020-*\b\022\004\022\002H\0020\0032\006\020,\032\0020$2\t\b\002\020?\001\032\0020$2\t\b\002\020?\001\032\0020\0012\030\020\016\032\024\022\n\022\b\022\004\022\002H\0020+\022\004\022\002H-0\005H\007\032$\020?\001\032\017\022\013\022\t\022\004\022\002H\0020?\0010\003\"\004\b\000\020\002*\b\022\004\022\002H\0020\003\032A\020?\001\032\024\022\020\022\016\022\004\022\002H\002\022\004\022\002H-0\0170\003\"\004\b\000\020\002\"\004\b\001\020-*\b\022\004\022\002H\0020\0032\r\020?\001\032\b\022\004\022\002H-0\003H?\004\032r\020?\001\032\b\022\004\022\002H\r0\003\"\004\b\000\020\002\"\004\b\001\020-\"\004\b\002\020\r*\b\022\004\022\002H\0020\0032\r\020?\001\032\b\022\004\022\002H-0\00328\020\016\0324\022\024\022\022H\002?\006\r\bC\022\t\bD\022\005\b\b(?\001\022\024\022\022H-?\006\r\bC\022\t\bD\022\005\b\b(?\001\022\004\022\002H\r0B\032+\020?\001\032\024\022\020\022\016\022\004\022\002H\002\022\004\022\002H\0020\0170\003\"\004\b\000\020\002*\b\022\004\022\002H\0020\003H\007\032_\020?\001\032\b\022\004\022\002H-0\003\"\004\b\000\020\002\"\004\b\001\020-*\b\022\004\022\002H\0020\00328\020\016\0324\022\024\022\022H\002?\006\r\bC\022\t\bD\022\005\b\b(?\001\022\024\022\022H\002?\006\r\bC\022\t\bD\022\005\b\b(?\001\022\004\022\002H-0BH\007?\006?\001"}, d2={"all", "", "T", "Lkotlin/sequences/Sequence;", "predicate", "Lkotlin/Function1;", "any", "asIterable", "", "asSequence", "associate", "", "K", "V", "transform", "Lkotlin/Pair;", "associateBy", "keySelector", "valueTransform", "associateByTo", "M", "", "destination", "(Lkotlin/sequences/Sequence;Ljava/util/Map;Lkotlin/jvm/functions/Function1;)Ljava/util/Map;", "(Lkotlin/sequences/Sequence;Ljava/util/Map;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)Ljava/util/Map;", "associateTo", "associateWith", "valueSelector", "associateWithTo", "average", "", "", "averageOfByte", "averageOfDouble", "", "averageOfFloat", "", "averageOfInt", "", "averageOfLong", "", "averageOfShort", "chunked", "", "size", "R", "contains", "Lkotlin/internal/OnlyInputTypes;", "element", "(Lkotlin/sequences/Sequence;Ljava/lang/Object;)Z", "count", "distinct", "distinctBy", "selector", "drop", "n", "dropWhile", "elementAt", "index", "(Lkotlin/sequences/Sequence;I)Ljava/lang/Object;", "elementAtOrElse", "defaultValue", "(Lkotlin/sequences/Sequence;ILkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "elementAtOrNull", "filter", "filterIndexed", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "filterIndexedTo", "C", "", "(Lkotlin/sequences/Sequence;Ljava/util/Collection;Lkotlin/jvm/functions/Function2;)Ljava/util/Collection;", "filterIsInstance", "Lkotlin/internal/NoInfer;", "filterIsInstanceTo", "(Lkotlin/sequences/Sequence;Ljava/util/Collection;)Ljava/util/Collection;", "filterNot", "filterNotNull", "", "filterNotNullTo", "filterNotTo", "(Lkotlin/sequences/Sequence;Ljava/util/Collection;Lkotlin/jvm/functions/Function1;)Ljava/util/Collection;", "filterTo", "find", "(Lkotlin/sequences/Sequence;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "findLast", "first", "(Lkotlin/sequences/Sequence;)Ljava/lang/Object;", "firstOrNull", "flatMap", "flatMapTo", "fold", "initial", "operation", "acc", "(Lkotlin/sequences/Sequence;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "foldIndexed", "Lkotlin/Function3;", "(Lkotlin/sequences/Sequence;Ljava/lang/Object;Lkotlin/jvm/functions/Function3;)Ljava/lang/Object;", "forEach", "", "action", "forEachIndexed", "groupBy", "groupByTo", "", "groupingBy", "Lkotlin/collections/Grouping;", "indexOf", "(Lkotlin/sequences/Sequence;Ljava/lang/Object;)I", "indexOfFirst", "indexOfLast", "joinTo", "A", "Ljava/lang/Appendable;", "Lkotlin/text/Appendable;", "buffer", "separator", "", "prefix", "postfix", "limit", "truncated", "(Lkotlin/sequences/Sequence;Ljava/lang/Appendable;Ljava/lang/CharSequence;Ljava/lang/CharSequence;Ljava/lang/CharSequence;ILjava/lang/CharSequence;Lkotlin/jvm/functions/Function1;)Ljava/lang/Appendable;", "joinToString", "", "last", "lastIndexOf", "lastOrNull", "map", "mapIndexed", "mapIndexedNotNull", "mapIndexedNotNullTo", "mapIndexedTo", "mapNotNull", "mapNotNullTo", "mapTo", "max", "", "(Lkotlin/sequences/Sequence;)Ljava/lang/Comparable;", "(Lkotlin/sequences/Sequence;)Ljava/lang/Double;", "(Lkotlin/sequences/Sequence;)Ljava/lang/Float;", "maxBy", "maxWith", "comparator", "Ljava/util/Comparator;", "Lkotlin/Comparator;", "(Lkotlin/sequences/Sequence;Ljava/util/Comparator;)Ljava/lang/Object;", "min", "minBy", "minWith", "minus", "(Lkotlin/sequences/Sequence;Ljava/lang/Object;)Lkotlin/sequences/Sequence;", "elements", "", "(Lkotlin/sequences/Sequence;[Ljava/lang/Object;)Lkotlin/sequences/Sequence;", "minusElement", "none", "onEach", "partition", "plus", "plusElement", "reduce", "S", "(Lkotlin/sequences/Sequence;Lkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "reduceIndexed", "(Lkotlin/sequences/Sequence;Lkotlin/jvm/functions/Function3;)Ljava/lang/Object;", "requireNoNulls", "single", "singleOrNull", "sorted", "sortedBy", "sortedByDescending", "sortedDescending", "sortedWith", "sum", "sumOfByte", "sumOfDouble", "sumOfFloat", "sumOfInt", "sumOfLong", "sumOfShort", "sumBy", "sumByDouble", "take", "takeWhile", "toCollection", "toHashSet", "Ljava/util/HashSet;", "Lkotlin/collections/HashSet;", "toList", "toMutableList", "toMutableSet", "", "toSet", "", "windowed", "step", "partialWindows", "withIndex", "Lkotlin/collections/IndexedValue;", "zip", "other", "a", "b", "zipWithNext", "kotlin-stdlib"}, k=5, mv={1, 1, 15}, xi=1, xs="kotlin/sequences/SequencesKt")
class SequencesKt___SequencesKt
  extends SequencesKt___SequencesJvmKt
{
  public SequencesKt___SequencesKt() {}
  
  public static final boolean all(Sequence paramSequence, Function1 paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$all");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "predicate");
    paramSequence = paramSequence.iterator();
    while (paramSequence.hasNext()) {
      if (!((Boolean)paramFunction1.invoke(paramSequence.next())).booleanValue()) {
        return false;
      }
    }
    return true;
  }
  
  public static final boolean any(Sequence paramSequence, Function1 paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$any");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "predicate");
    paramSequence = paramSequence.iterator();
    while (paramSequence.hasNext()) {
      if (((Boolean)paramFunction1.invoke(paramSequence.next())).booleanValue()) {
        return true;
      }
    }
    return false;
  }
  
  public static final Iterable asIterable(Sequence paramSequence)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$asIterable");
    (Iterable)new Iterable()
    {
      public Iterator iterator()
      {
        return SequencesKt___SequencesKt.this.iterator();
      }
    };
  }
  
  private static final Sequence asSequence(Sequence paramSequence)
  {
    return paramSequence;
  }
  
  public static final Map associate(Sequence paramSequence, Function1 paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$associate");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "transform");
    Map localMap = (Map)new LinkedHashMap();
    paramSequence = paramSequence.iterator();
    while (paramSequence.hasNext())
    {
      Pair localPair = (Pair)paramFunction1.invoke(paramSequence.next());
      localMap.put(localPair.getFirst(), localPair.getSecond());
    }
    return localMap;
  }
  
  public static final Map associateBy(Sequence paramSequence, Function1 paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$associateBy");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "keySelector");
    Map localMap = (Map)new LinkedHashMap();
    paramSequence = paramSequence.iterator();
    while (paramSequence.hasNext())
    {
      Object localObject = paramSequence.next();
      localMap.put(paramFunction1.invoke(localObject), localObject);
    }
    return localMap;
  }
  
  public static final Map associateBy(Sequence paramSequence, Function1 paramFunction11, Function1 paramFunction12)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$associateBy");
    Intrinsics.checkParameterIsNotNull(paramFunction11, "keySelector");
    Intrinsics.checkParameterIsNotNull(paramFunction12, "valueTransform");
    Map localMap = (Map)new LinkedHashMap();
    paramSequence = paramSequence.iterator();
    while (paramSequence.hasNext())
    {
      Object localObject = paramSequence.next();
      localMap.put(paramFunction11.invoke(localObject), paramFunction12.invoke(localObject));
    }
    return localMap;
  }
  
  public static final Map associateByTo(Sequence paramSequence, Map paramMap, Function1 paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$associateByTo");
    Intrinsics.checkParameterIsNotNull(paramMap, "destination");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "keySelector");
    paramSequence = paramSequence.iterator();
    while (paramSequence.hasNext())
    {
      Object localObject = paramSequence.next();
      paramMap.put(paramFunction1.invoke(localObject), localObject);
    }
    return paramMap;
  }
  
  public static final Map associateByTo(Sequence paramSequence, Map paramMap, Function1 paramFunction11, Function1 paramFunction12)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$associateByTo");
    Intrinsics.checkParameterIsNotNull(paramMap, "destination");
    Intrinsics.checkParameterIsNotNull(paramFunction11, "keySelector");
    Intrinsics.checkParameterIsNotNull(paramFunction12, "valueTransform");
    paramSequence = paramSequence.iterator();
    while (paramSequence.hasNext())
    {
      Object localObject = paramSequence.next();
      paramMap.put(paramFunction11.invoke(localObject), paramFunction12.invoke(localObject));
    }
    return paramMap;
  }
  
  public static final Map associateTo(Sequence paramSequence, Map paramMap, Function1 paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$associateTo");
    Intrinsics.checkParameterIsNotNull(paramMap, "destination");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "transform");
    paramSequence = paramSequence.iterator();
    while (paramSequence.hasNext())
    {
      Pair localPair = (Pair)paramFunction1.invoke(paramSequence.next());
      paramMap.put(localPair.getFirst(), localPair.getSecond());
    }
    return paramMap;
  }
  
  public static final Map associateWith(Sequence paramSequence, Function1 paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$associateWith");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "valueSelector");
    LinkedHashMap localLinkedHashMap = new LinkedHashMap();
    paramSequence = paramSequence.iterator();
    while (paramSequence.hasNext())
    {
      Object localObject = paramSequence.next();
      ((Map)localLinkedHashMap).put(localObject, paramFunction1.invoke(localObject));
    }
    return (Map)localLinkedHashMap;
  }
  
  public static final Map associateWithTo(Sequence paramSequence, Map paramMap, Function1 paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$associateWithTo");
    Intrinsics.checkParameterIsNotNull(paramMap, "destination");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "valueSelector");
    paramSequence = paramSequence.iterator();
    while (paramSequence.hasNext())
    {
      Object localObject = paramSequence.next();
      paramMap.put(localObject, paramFunction1.invoke(localObject));
    }
    return paramMap;
  }
  
  public static final double averageOfByte(Sequence paramSequence)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$average");
    paramSequence = paramSequence.iterator();
    double d1 = 0.0D;
    int i = 0;
    while (paramSequence.hasNext())
    {
      d2 = ((Number)paramSequence.next()).byteValue();
      Double.isNaN(d2);
      d2 = d1 + d2;
      int j = i + 1;
      d1 = d2;
      i = j;
      if (j < 0)
      {
        CollectionsKt__CollectionsKt.throwCountOverflow();
        d1 = d2;
        i = j;
      }
    }
    if (i == 0) {
      return DoubleCompanionObject.INSTANCE.getNaN();
    }
    double d2 = i;
    Double.isNaN(d2);
    return d1 / d2;
  }
  
  public static final double averageOfDouble(Sequence paramSequence)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$average");
    paramSequence = paramSequence.iterator();
    double d1 = 0.0D;
    int i = 0;
    while (paramSequence.hasNext())
    {
      d2 = d1 + ((Number)paramSequence.next()).doubleValue();
      int j = i + 1;
      d1 = d2;
      i = j;
      if (j < 0)
      {
        CollectionsKt__CollectionsKt.throwCountOverflow();
        d1 = d2;
        i = j;
      }
    }
    if (i == 0) {
      return DoubleCompanionObject.INSTANCE.getNaN();
    }
    double d2 = i;
    Double.isNaN(d2);
    return d1 / d2;
  }
  
  public static final double averageOfFloat(Sequence paramSequence)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$average");
    paramSequence = paramSequence.iterator();
    double d1 = 0.0D;
    int i = 0;
    while (paramSequence.hasNext())
    {
      d2 = ((Number)paramSequence.next()).floatValue();
      Double.isNaN(d2);
      d2 = d1 + d2;
      int j = i + 1;
      d1 = d2;
      i = j;
      if (j < 0)
      {
        CollectionsKt__CollectionsKt.throwCountOverflow();
        d1 = d2;
        i = j;
      }
    }
    if (i == 0) {
      return DoubleCompanionObject.INSTANCE.getNaN();
    }
    double d2 = i;
    Double.isNaN(d2);
    return d1 / d2;
  }
  
  public static final double averageOfInt(Sequence paramSequence)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$average");
    paramSequence = paramSequence.iterator();
    double d1 = 0.0D;
    int i = 0;
    while (paramSequence.hasNext())
    {
      d2 = ((Number)paramSequence.next()).intValue();
      Double.isNaN(d2);
      d2 = d1 + d2;
      int j = i + 1;
      d1 = d2;
      i = j;
      if (j < 0)
      {
        CollectionsKt__CollectionsKt.throwCountOverflow();
        d1 = d2;
        i = j;
      }
    }
    if (i == 0) {
      return DoubleCompanionObject.INSTANCE.getNaN();
    }
    double d2 = i;
    Double.isNaN(d2);
    return d1 / d2;
  }
  
  public static final double averageOfLong(Sequence paramSequence)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$average");
    paramSequence = paramSequence.iterator();
    double d1 = 0.0D;
    int i = 0;
    while (paramSequence.hasNext())
    {
      d2 = ((Number)paramSequence.next()).longValue();
      Double.isNaN(d2);
      d2 = d1 + d2;
      int j = i + 1;
      d1 = d2;
      i = j;
      if (j < 0)
      {
        CollectionsKt__CollectionsKt.throwCountOverflow();
        d1 = d2;
        i = j;
      }
    }
    if (i == 0) {
      return DoubleCompanionObject.INSTANCE.getNaN();
    }
    double d2 = i;
    Double.isNaN(d2);
    return d1 / d2;
  }
  
  public static final double averageOfShort(Sequence paramSequence)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$average");
    paramSequence = paramSequence.iterator();
    double d1 = 0.0D;
    int i = 0;
    while (paramSequence.hasNext())
    {
      d2 = ((Number)paramSequence.next()).shortValue();
      Double.isNaN(d2);
      d2 = d1 + d2;
      int j = i + 1;
      d1 = d2;
      i = j;
      if (j < 0)
      {
        CollectionsKt__CollectionsKt.throwCountOverflow();
        d1 = d2;
        i = j;
      }
    }
    if (i == 0) {
      return DoubleCompanionObject.INSTANCE.getNaN();
    }
    double d2 = i;
    Double.isNaN(d2);
    return d1 / d2;
  }
  
  public static final Sequence chunked(Sequence paramSequence, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$chunked");
    return windowed(paramSequence, paramInt, paramInt, true);
  }
  
  public static final Sequence chunked(Sequence paramSequence, int paramInt, Function1 paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$chunked");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "transform");
    return windowed(paramSequence, paramInt, paramInt, true, paramFunction1);
  }
  
  public static final boolean contains(Sequence paramSequence, Object paramObject)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$contains");
    return indexOf(paramSequence, paramObject) >= 0;
  }
  
  public static final int count(Sequence paramSequence)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$count");
    paramSequence = paramSequence.iterator();
    int i = 0;
    while (paramSequence.hasNext())
    {
      paramSequence.next();
      int j = i + 1;
      i = j;
      if (j < 0)
      {
        CollectionsKt__CollectionsKt.throwCountOverflow();
        i = j;
      }
    }
    return i;
  }
  
  public static final int count(Sequence paramSequence, Function1 paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$count");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "predicate");
    paramSequence = paramSequence.iterator();
    int i = 0;
    while (paramSequence.hasNext()) {
      if (((Boolean)paramFunction1.invoke(paramSequence.next())).booleanValue())
      {
        int j = i + 1;
        i = j;
        if (j < 0) {
          if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0))
          {
            CollectionsKt__CollectionsKt.throwCountOverflow();
            i = j;
          }
          else
          {
            throw ((Throwable)new ArithmeticException("Count overflow has happened."));
          }
        }
      }
    }
    return i;
  }
  
  public static final Sequence distinct(Sequence paramSequence)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$distinct");
    return distinctBy(paramSequence, (Function1)distinct.1.INSTANCE);
  }
  
  public static final Sequence distinctBy(Sequence paramSequence, Function1 paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$distinctBy");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "selector");
    return (Sequence)new DistinctSequence(paramSequence, paramFunction1);
  }
  
  public static final Sequence drop(Sequence paramSequence, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$drop");
    int i;
    if (paramInt >= 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      if (paramInt == 0) {
        return paramSequence;
      }
      if ((paramSequence instanceof DropTakeSequence)) {
        return ((DropTakeSequence)paramSequence).drop(paramInt);
      }
      return (Sequence)new DropSequence(paramSequence, paramInt);
    }
    paramSequence = new StringBuilder();
    paramSequence.append("Requested element count ");
    paramSequence.append(paramInt);
    paramSequence.append(" is less than zero.");
    throw ((Throwable)new IllegalArgumentException(paramSequence.toString().toString()));
  }
  
  public static final Sequence dropWhile(Sequence paramSequence, Function1 paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$dropWhile");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "predicate");
    return (Sequence)new DropWhileSequence(paramSequence, paramFunction1);
  }
  
  public static final Object elementAt(Sequence paramSequence, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$elementAt");
    elementAtOrElse(paramSequence, paramInt, (Function1)new Lambda(paramInt)
    {
      public final Void invoke(int paramAnonymousInt)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("Sequence doesn't contain element at index ");
        localStringBuilder.append($index);
        localStringBuilder.append('.');
        throw ((Throwable)new IndexOutOfBoundsException(localStringBuilder.toString()));
      }
    });
  }
  
  public static final Object elementAtOrElse(Sequence paramSequence, int paramInt, Function1 paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$elementAtOrElse");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "defaultValue");
    if (paramInt < 0) {
      return paramFunction1.invoke(Integer.valueOf(paramInt));
    }
    paramSequence = paramSequence.iterator();
    int i = 0;
    while (paramSequence.hasNext())
    {
      Object localObject = paramSequence.next();
      if (paramInt == i) {
        return localObject;
      }
      i += 1;
    }
    return paramFunction1.invoke(Integer.valueOf(paramInt));
  }
  
  public static final Object elementAtOrNull(Sequence paramSequence, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$elementAtOrNull");
    if (paramInt < 0) {
      return null;
    }
    paramSequence = paramSequence.iterator();
    int i = 0;
    while (paramSequence.hasNext())
    {
      Object localObject = paramSequence.next();
      if (paramInt == i) {
        return localObject;
      }
      i += 1;
    }
    return null;
  }
  
  public static final Sequence filter(Sequence paramSequence, Function1 paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$filter");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "predicate");
    return (Sequence)new FilteringSequence(paramSequence, true, paramFunction1);
  }
  
  public static final Sequence filterIndexed(Sequence paramSequence, Function2 paramFunction2)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$filterIndexed");
    Intrinsics.checkParameterIsNotNull(paramFunction2, "predicate");
    (Sequence)new TransformingSequence((Sequence)new FilteringSequence((Sequence)new IndexingSequence(paramSequence), true, (Function1)new Lambda(paramFunction2)
    {
      public final boolean invoke(IndexedValue paramAnonymousIndexedValue)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousIndexedValue, "it");
        return ((Boolean)invoke(Integer.valueOf(paramAnonymousIndexedValue.getIndex()), paramAnonymousIndexedValue.getValue())).booleanValue();
      }
    }), (Function1)filterIndexed.2.INSTANCE);
  }
  
  public static final Collection filterIndexedTo(Sequence paramSequence, Collection paramCollection, Function2 paramFunction2)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$filterIndexedTo");
    Intrinsics.checkParameterIsNotNull(paramCollection, "destination");
    Intrinsics.checkParameterIsNotNull(paramFunction2, "predicate");
    paramSequence = paramSequence.iterator();
    int i = 0;
    while (paramSequence.hasNext())
    {
      Object localObject = paramSequence.next();
      if (i < 0) {
        if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
          CollectionsKt__CollectionsKt.throwIndexOverflow();
        } else {
          throw ((Throwable)new ArithmeticException("Index overflow has happened."));
        }
      }
      if (((Boolean)paramFunction2.invoke(Integer.valueOf(i), localObject)).booleanValue()) {
        paramCollection.add(localObject);
      }
      i += 1;
    }
    return paramCollection;
  }
  
  public static final Sequence filterNot(Sequence paramSequence, Function1 paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$filterNot");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "predicate");
    return (Sequence)new FilteringSequence(paramSequence, false, paramFunction1);
  }
  
  public static final Sequence filterNotNull(Sequence paramSequence)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$filterNotNull");
    paramSequence = filterNot(paramSequence, (Function1)filterNotNull.1.INSTANCE);
    if (paramSequence != null) {
      return paramSequence;
    }
    throw new TypeCastException("null cannot be cast to non-null type kotlin.sequences.Sequence<T>");
  }
  
  public static final Collection filterNotNullTo(Sequence paramSequence, Collection paramCollection)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$filterNotNullTo");
    Intrinsics.checkParameterIsNotNull(paramCollection, "destination");
    paramSequence = paramSequence.iterator();
    while (paramSequence.hasNext())
    {
      Object localObject = paramSequence.next();
      if (localObject != null) {
        paramCollection.add(localObject);
      }
    }
    return paramCollection;
  }
  
  public static final Collection filterNotTo(Sequence paramSequence, Collection paramCollection, Function1 paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$filterNotTo");
    Intrinsics.checkParameterIsNotNull(paramCollection, "destination");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "predicate");
    paramSequence = paramSequence.iterator();
    while (paramSequence.hasNext())
    {
      Object localObject = paramSequence.next();
      if (!((Boolean)paramFunction1.invoke(localObject)).booleanValue()) {
        paramCollection.add(localObject);
      }
    }
    return paramCollection;
  }
  
  public static final Collection filterTo(Sequence paramSequence, Collection paramCollection, Function1 paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$filterTo");
    Intrinsics.checkParameterIsNotNull(paramCollection, "destination");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "predicate");
    paramSequence = paramSequence.iterator();
    while (paramSequence.hasNext())
    {
      Object localObject = paramSequence.next();
      if (((Boolean)paramFunction1.invoke(localObject)).booleanValue()) {
        paramCollection.add(localObject);
      }
    }
    return paramCollection;
  }
  
  private static final Object find(Sequence paramSequence, Function1 paramFunction1)
  {
    paramSequence = paramSequence.iterator();
    while (paramSequence.hasNext())
    {
      Object localObject = paramSequence.next();
      if (((Boolean)paramFunction1.invoke(localObject)).booleanValue()) {
        return localObject;
      }
    }
    return null;
  }
  
  private static final Object findLast(Sequence paramSequence, Function1 paramFunction1)
  {
    Iterator localIterator = paramSequence.iterator();
    paramSequence = null;
    while (localIterator.hasNext())
    {
      Object localObject = localIterator.next();
      if (((Boolean)paramFunction1.invoke(localObject)).booleanValue()) {
        paramSequence = localObject;
      }
    }
    return paramSequence;
  }
  
  public static final Object first(Sequence paramSequence)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$first");
    paramSequence = paramSequence.iterator();
    if (paramSequence.hasNext()) {
      return paramSequence.next();
    }
    throw ((Throwable)new NoSuchElementException("Sequence is empty."));
  }
  
  public static final Object first(Sequence paramSequence, Function1 paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$first");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "predicate");
    paramSequence = paramSequence.iterator();
    while (paramSequence.hasNext())
    {
      Object localObject = paramSequence.next();
      if (((Boolean)paramFunction1.invoke(localObject)).booleanValue()) {
        return localObject;
      }
    }
    paramSequence = (Throwable)new NoSuchElementException("Sequence contains no element matching the predicate.");
    throw paramSequence;
  }
  
  public static final Object firstOrNull(Sequence paramSequence)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$firstOrNull");
    paramSequence = paramSequence.iterator();
    if (!paramSequence.hasNext()) {
      return null;
    }
    return paramSequence.next();
  }
  
  public static final Object firstOrNull(Sequence paramSequence, Function1 paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$firstOrNull");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "predicate");
    paramSequence = paramSequence.iterator();
    while (paramSequence.hasNext())
    {
      Object localObject = paramSequence.next();
      if (((Boolean)paramFunction1.invoke(localObject)).booleanValue()) {
        return localObject;
      }
    }
    return null;
  }
  
  public static final Sequence flatMap(Sequence paramSequence, Function1 paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$flatMap");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "transform");
    return (Sequence)new FlatteningSequence(paramSequence, paramFunction1, (Function1)flatMap.1.INSTANCE);
  }
  
  public static final Collection flatMapTo(Sequence paramSequence, Collection paramCollection, Function1 paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$flatMapTo");
    Intrinsics.checkParameterIsNotNull(paramCollection, "destination");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "transform");
    paramSequence = paramSequence.iterator();
    while (paramSequence.hasNext()) {
      CollectionsKt__MutableCollectionsKt.addAll(paramCollection, (Sequence)paramFunction1.invoke(paramSequence.next()));
    }
    return paramCollection;
  }
  
  public static final Object fold(Sequence paramSequence, Object paramObject, Function2 paramFunction2)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$fold");
    Intrinsics.checkParameterIsNotNull(paramFunction2, "operation");
    paramSequence = paramSequence.iterator();
    while (paramSequence.hasNext()) {
      paramObject = paramFunction2.invoke(paramObject, paramSequence.next());
    }
    return paramObject;
  }
  
  public static final Object foldIndexed(Sequence paramSequence, Object paramObject, Function3 paramFunction3)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$foldIndexed");
    Intrinsics.checkParameterIsNotNull(paramFunction3, "operation");
    paramSequence = paramSequence.iterator();
    int i = 0;
    while (paramSequence.hasNext())
    {
      Object localObject = paramSequence.next();
      if (i < 0) {
        if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
          CollectionsKt__CollectionsKt.throwIndexOverflow();
        } else {
          throw ((Throwable)new ArithmeticException("Index overflow has happened."));
        }
      }
      paramObject = paramFunction3.invoke(Integer.valueOf(i), paramObject, localObject);
      i += 1;
    }
    return paramObject;
  }
  
  public static final void forEach(Sequence paramSequence, Function1 paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$forEach");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "action");
    paramSequence = paramSequence.iterator();
    while (paramSequence.hasNext()) {
      paramFunction1.invoke(paramSequence.next());
    }
  }
  
  public static final void forEachIndexed(Sequence paramSequence, Function2 paramFunction2)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$forEachIndexed");
    Intrinsics.checkParameterIsNotNull(paramFunction2, "action");
    paramSequence = paramSequence.iterator();
    int i = 0;
    while (paramSequence.hasNext())
    {
      Object localObject = paramSequence.next();
      if (i < 0) {
        if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
          CollectionsKt__CollectionsKt.throwIndexOverflow();
        } else {
          throw ((Throwable)new ArithmeticException("Index overflow has happened."));
        }
      }
      paramFunction2.invoke(Integer.valueOf(i), localObject);
      i += 1;
    }
  }
  
  public static final Map groupBy(Sequence paramSequence, Function1 paramFunction1)
  {
    throw new Runtime("d2j fail translate: java.lang.RuntimeException: fail exe a9 = a8\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.exec(BaseAnalyze.java:92)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.exec(BaseAnalyze.java:1)\n\tat com.googlecode.dex2jar.ir.ts.Cfg.dfs(Cfg.java:255)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.analyze0(BaseAnalyze.java:75)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.analyze(BaseAnalyze.java:69)\n\tat com.googlecode.dex2jar.ir.ts.UnSSATransformer.transform(UnSSATransformer.java:274)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:163)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\nCaused by: java.lang.NullPointerException\n");
  }
  
  public static final Map groupBy(Sequence paramSequence, Function1 paramFunction11, Function1 paramFunction12)
  {
    throw new Runtime("d2j fail translate: java.lang.RuntimeException: fail exe a10 = a9\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.exec(BaseAnalyze.java:92)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.exec(BaseAnalyze.java:1)\n\tat com.googlecode.dex2jar.ir.ts.Cfg.dfs(Cfg.java:255)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.analyze0(BaseAnalyze.java:75)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.analyze(BaseAnalyze.java:69)\n\tat com.googlecode.dex2jar.ir.ts.UnSSATransformer.transform(UnSSATransformer.java:274)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:163)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\nCaused by: java.lang.NullPointerException\n");
  }
  
  public static final Map groupByTo(Sequence paramSequence, Map paramMap, Function1 paramFunction1)
  {
    throw new Runtime("d2j fail translate: java.lang.RuntimeException: fail exe a9 = a8\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.exec(BaseAnalyze.java:92)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.exec(BaseAnalyze.java:1)\n\tat com.googlecode.dex2jar.ir.ts.Cfg.dfs(Cfg.java:255)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.analyze0(BaseAnalyze.java:75)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.analyze(BaseAnalyze.java:69)\n\tat com.googlecode.dex2jar.ir.ts.UnSSATransformer.transform(UnSSATransformer.java:274)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:163)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\nCaused by: java.lang.NullPointerException\n");
  }
  
  public static final Map groupByTo(Sequence paramSequence, Map paramMap, Function1 paramFunction11, Function1 paramFunction12)
  {
    throw new Runtime("d2j fail translate: java.lang.RuntimeException: fail exe a10 = a9\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.exec(BaseAnalyze.java:92)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.exec(BaseAnalyze.java:1)\n\tat com.googlecode.dex2jar.ir.ts.Cfg.dfs(Cfg.java:255)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.analyze0(BaseAnalyze.java:75)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.analyze(BaseAnalyze.java:69)\n\tat com.googlecode.dex2jar.ir.ts.UnSSATransformer.transform(UnSSATransformer.java:274)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:163)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\nCaused by: java.lang.NullPointerException\n");
  }
  
  public static final Grouping groupingBy(Sequence paramSequence, final Function1 paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$groupingBy");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "keySelector");
    (Grouping)new Grouping()
    {
      public Object keyOf(Object paramAnonymousObject)
      {
        return paramFunction1.invoke(paramAnonymousObject);
      }
      
      public Iterator sourceIterator()
      {
        return iterator();
      }
    };
  }
  
  public static final int indexOf(Sequence paramSequence, Object paramObject)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$indexOf");
    paramSequence = paramSequence.iterator();
    int i = 0;
    while (paramSequence.hasNext())
    {
      Object localObject = paramSequence.next();
      if (i < 0) {
        CollectionsKt__CollectionsKt.throwIndexOverflow();
      }
      if (Intrinsics.areEqual(paramObject, localObject)) {
        return i;
      }
      i += 1;
    }
    return -1;
  }
  
  public static final int indexOfFirst(Sequence paramSequence, Function1 paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$indexOfFirst");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "predicate");
    paramSequence = paramSequence.iterator();
    int i = 0;
    while (paramSequence.hasNext())
    {
      Object localObject = paramSequence.next();
      if (i < 0) {
        if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
          CollectionsKt__CollectionsKt.throwIndexOverflow();
        } else {
          throw ((Throwable)new ArithmeticException("Index overflow has happened."));
        }
      }
      if (((Boolean)paramFunction1.invoke(localObject)).booleanValue()) {
        return i;
      }
      i += 1;
    }
    return -1;
  }
  
  public static final int indexOfLast(Sequence paramSequence, Function1 paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$indexOfLast");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "predicate");
    paramSequence = paramSequence.iterator();
    int j = -1;
    int i = 0;
    while (paramSequence.hasNext())
    {
      Object localObject = paramSequence.next();
      if (i < 0) {
        if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
          CollectionsKt__CollectionsKt.throwIndexOverflow();
        } else {
          throw ((Throwable)new ArithmeticException("Index overflow has happened."));
        }
      }
      if (((Boolean)paramFunction1.invoke(localObject)).booleanValue()) {
        j = i;
      }
      i += 1;
    }
    return j;
  }
  
  public static final Appendable joinTo(Sequence paramSequence, Appendable paramAppendable, CharSequence paramCharSequence1, CharSequence paramCharSequence2, CharSequence paramCharSequence3, int paramInt, CharSequence paramCharSequence4, Function1 paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$joinTo");
    Intrinsics.checkParameterIsNotNull(paramAppendable, "buffer");
    Intrinsics.checkParameterIsNotNull(paramCharSequence1, "separator");
    Intrinsics.checkParameterIsNotNull(paramCharSequence2, "prefix");
    Intrinsics.checkParameterIsNotNull(paramCharSequence3, "postfix");
    Intrinsics.checkParameterIsNotNull(paramCharSequence4, "truncated");
    paramAppendable.append(paramCharSequence2);
    paramSequence = paramSequence.iterator();
    int i = 0;
    int j;
    for (;;)
    {
      j = i;
      if (!paramSequence.hasNext()) {
        break;
      }
      paramCharSequence2 = paramSequence.next();
      i += 1;
      if (i > 1) {
        paramAppendable.append(paramCharSequence1);
      }
      if (paramInt >= 0)
      {
        j = i;
        if (i > paramInt) {
          break;
        }
      }
      StringsKt__StringBuilderKt.appendElement(paramAppendable, paramCharSequence2, paramFunction1);
    }
    if ((paramInt >= 0) && (j > paramInt)) {
      paramAppendable.append(paramCharSequence4);
    }
    paramAppendable.append(paramCharSequence3);
    return paramAppendable;
  }
  
  public static final String joinToString(Sequence paramSequence, CharSequence paramCharSequence1, CharSequence paramCharSequence2, CharSequence paramCharSequence3, int paramInt, CharSequence paramCharSequence4, Function1 paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$joinToString");
    Intrinsics.checkParameterIsNotNull(paramCharSequence1, "separator");
    Intrinsics.checkParameterIsNotNull(paramCharSequence2, "prefix");
    Intrinsics.checkParameterIsNotNull(paramCharSequence3, "postfix");
    Intrinsics.checkParameterIsNotNull(paramCharSequence4, "truncated");
    paramSequence = ((StringBuilder)joinTo(paramSequence, (Appendable)new StringBuilder(), paramCharSequence1, paramCharSequence2, paramCharSequence3, paramInt, paramCharSequence4, paramFunction1)).toString();
    Intrinsics.checkExpressionValueIsNotNull(paramSequence, "joinTo(StringBuilder(), ?ed, transform).toString()");
    return paramSequence;
  }
  
  public static final Object last(Sequence paramSequence)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$last");
    Iterator localIterator = paramSequence.iterator();
    if (localIterator.hasNext())
    {
      for (paramSequence = localIterator.next(); localIterator.hasNext(); paramSequence = localIterator.next()) {}
      return paramSequence;
    }
    paramSequence = (Throwable)new NoSuchElementException("Sequence is empty.");
    throw paramSequence;
  }
  
  public static final Object last(Sequence paramSequence, Function1 paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$last");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "predicate");
    Iterator localIterator = paramSequence.iterator();
    paramSequence = null;
    int i = 0;
    while (localIterator.hasNext())
    {
      Object localObject = localIterator.next();
      if (((Boolean)paramFunction1.invoke(localObject)).booleanValue())
      {
        paramSequence = localObject;
        i = 1;
      }
    }
    if (i != 0) {
      return paramSequence;
    }
    paramSequence = (Throwable)new NoSuchElementException("Sequence contains no element matching the predicate.");
    throw paramSequence;
  }
  
  public static final int lastIndexOf(Sequence paramSequence, Object paramObject)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$lastIndexOf");
    paramSequence = paramSequence.iterator();
    int j = -1;
    int i = 0;
    while (paramSequence.hasNext())
    {
      Object localObject = paramSequence.next();
      if (i < 0) {
        CollectionsKt__CollectionsKt.throwIndexOverflow();
      }
      if (Intrinsics.areEqual(paramObject, localObject)) {
        j = i;
      }
      i += 1;
    }
    return j;
  }
  
  public static final Object lastOrNull(Sequence paramSequence)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$lastOrNull");
    Iterator localIterator = paramSequence.iterator();
    if (!localIterator.hasNext()) {
      return null;
    }
    for (paramSequence = localIterator.next(); localIterator.hasNext(); paramSequence = localIterator.next()) {}
    return paramSequence;
  }
  
  public static final Object lastOrNull(Sequence paramSequence, Function1 paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$lastOrNull");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "predicate");
    Iterator localIterator = paramSequence.iterator();
    paramSequence = null;
    while (localIterator.hasNext())
    {
      Object localObject = localIterator.next();
      if (((Boolean)paramFunction1.invoke(localObject)).booleanValue()) {
        paramSequence = localObject;
      }
    }
    return paramSequence;
  }
  
  public static final Sequence map(Sequence paramSequence, Function1 paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$map");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "transform");
    return (Sequence)new TransformingSequence(paramSequence, paramFunction1);
  }
  
  public static final Sequence mapIndexed(Sequence paramSequence, Function2 paramFunction2)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$mapIndexed");
    Intrinsics.checkParameterIsNotNull(paramFunction2, "transform");
    return (Sequence)new TransformingIndexedSequence(paramSequence, paramFunction2);
  }
  
  public static final Sequence mapIndexedNotNull(Sequence paramSequence, Function2 paramFunction2)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$mapIndexedNotNull");
    Intrinsics.checkParameterIsNotNull(paramFunction2, "transform");
    return filterNotNull((Sequence)new TransformingIndexedSequence(paramSequence, paramFunction2));
  }
  
  public static final Collection mapIndexedNotNullTo(Sequence paramSequence, Collection paramCollection, Function2 paramFunction2)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$mapIndexedNotNullTo");
    Intrinsics.checkParameterIsNotNull(paramCollection, "destination");
    Intrinsics.checkParameterIsNotNull(paramFunction2, "transform");
    paramSequence = paramSequence.iterator();
    int i = 0;
    while (paramSequence.hasNext())
    {
      Object localObject = paramSequence.next();
      if (i < 0) {
        if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
          CollectionsKt__CollectionsKt.throwIndexOverflow();
        } else {
          throw ((Throwable)new ArithmeticException("Index overflow has happened."));
        }
      }
      localObject = paramFunction2.invoke(Integer.valueOf(i), localObject);
      if (localObject != null) {
        paramCollection.add(localObject);
      }
      i += 1;
    }
    return paramCollection;
  }
  
  public static final Collection mapIndexedTo(Sequence paramSequence, Collection paramCollection, Function2 paramFunction2)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$mapIndexedTo");
    Intrinsics.checkParameterIsNotNull(paramCollection, "destination");
    Intrinsics.checkParameterIsNotNull(paramFunction2, "transform");
    paramSequence = paramSequence.iterator();
    int i = 0;
    while (paramSequence.hasNext())
    {
      Object localObject = paramSequence.next();
      if (i < 0) {
        if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
          CollectionsKt__CollectionsKt.throwIndexOverflow();
        } else {
          throw ((Throwable)new ArithmeticException("Index overflow has happened."));
        }
      }
      paramCollection.add(paramFunction2.invoke(Integer.valueOf(i), localObject));
      i += 1;
    }
    return paramCollection;
  }
  
  public static final Sequence mapNotNull(Sequence paramSequence, Function1 paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$mapNotNull");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "transform");
    return filterNotNull((Sequence)new TransformingSequence(paramSequence, paramFunction1));
  }
  
  public static final Collection mapNotNullTo(Sequence paramSequence, Collection paramCollection, Function1 paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$mapNotNullTo");
    Intrinsics.checkParameterIsNotNull(paramCollection, "destination");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "transform");
    paramSequence = paramSequence.iterator();
    while (paramSequence.hasNext())
    {
      Object localObject = paramFunction1.invoke(paramSequence.next());
      if (localObject != null) {
        paramCollection.add(localObject);
      }
    }
    return paramCollection;
  }
  
  public static final Collection mapTo(Sequence paramSequence, Collection paramCollection, Function1 paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$mapTo");
    Intrinsics.checkParameterIsNotNull(paramCollection, "destination");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "transform");
    paramSequence = paramSequence.iterator();
    while (paramSequence.hasNext()) {
      paramCollection.add(paramFunction1.invoke(paramSequence.next()));
    }
    return paramCollection;
  }
  
  public static final Comparable max(Sequence paramSequence)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$min");
    Iterator localIterator = paramSequence.iterator();
    if (!localIterator.hasNext()) {
      return null;
    }
    paramSequence = (Comparable)localIterator.next();
    while (localIterator.hasNext())
    {
      Comparable localComparable = (Comparable)localIterator.next();
      if (paramSequence.compareTo(localComparable) > 0) {
        paramSequence = localComparable;
      }
    }
    return paramSequence;
  }
  
  public static final Double max(Sequence paramSequence)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$min");
    paramSequence = paramSequence.iterator();
    if (!paramSequence.hasNext()) {
      return null;
    }
    double d2 = ((Number)paramSequence.next()).doubleValue();
    double d1 = d2;
    if (Double.isNaN(d2)) {
      return Double.valueOf(d2);
    }
    while (paramSequence.hasNext())
    {
      d2 = ((Number)paramSequence.next()).doubleValue();
      if (Double.isNaN(d2)) {
        return Double.valueOf(d2);
      }
      if (d1 > d2) {
        d1 = d2;
      }
    }
    return Double.valueOf(d1);
  }
  
  public static final Float max(Sequence paramSequence)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$min");
    paramSequence = paramSequence.iterator();
    if (!paramSequence.hasNext()) {
      return null;
    }
    float f2 = ((Number)paramSequence.next()).floatValue();
    float f1 = f2;
    if (Float.isNaN(f2)) {
      return Float.valueOf(f2);
    }
    while (paramSequence.hasNext())
    {
      f2 = ((Number)paramSequence.next()).floatValue();
      if (Float.isNaN(f2)) {
        return Float.valueOf(f2);
      }
      if (f1 > f2) {
        f1 = f2;
      }
    }
    return Float.valueOf(f1);
  }
  
  public static final Object maxBy(Sequence paramSequence, Function1 paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$maxBy");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "selector");
    Iterator localIterator = paramSequence.iterator();
    if (!localIterator.hasNext()) {
      return null;
    }
    paramSequence = localIterator.next();
    Object localObject2 = paramSequence;
    if (!localIterator.hasNext()) {
      return paramSequence;
    }
    paramSequence = (Comparable)paramFunction1.invoke(paramSequence);
    Object localObject3;
    do
    {
      Object localObject4 = localIterator.next();
      Comparable localComparable = (Comparable)paramFunction1.invoke(localObject4);
      localObject3 = localObject2;
      Object localObject1 = paramSequence;
      if (paramSequence.compareTo(localComparable) < 0)
      {
        localObject3 = localObject4;
        localObject1 = localComparable;
      }
      localObject2 = localObject3;
      paramSequence = (Sequence)localObject1;
    } while (localIterator.hasNext());
    return localObject3;
  }
  
  public static final Object maxWith(Sequence paramSequence, Comparator paramComparator)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$maxWith");
    Intrinsics.checkParameterIsNotNull(paramComparator, "comparator");
    Iterator localIterator = paramSequence.iterator();
    if (!localIterator.hasNext()) {
      return null;
    }
    paramSequence = localIterator.next();
    while (localIterator.hasNext())
    {
      Object localObject = localIterator.next();
      if (paramComparator.compare(paramSequence, localObject) < 0) {
        paramSequence = localObject;
      }
    }
    return paramSequence;
  }
  
  public static final Comparable min(Sequence paramSequence)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$max");
    Iterator localIterator = paramSequence.iterator();
    if (!localIterator.hasNext()) {
      return null;
    }
    paramSequence = (Comparable)localIterator.next();
    while (localIterator.hasNext())
    {
      Comparable localComparable = (Comparable)localIterator.next();
      if (paramSequence.compareTo(localComparable) < 0) {
        paramSequence = localComparable;
      }
    }
    return paramSequence;
  }
  
  public static final Double min(Sequence paramSequence)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$max");
    paramSequence = paramSequence.iterator();
    if (!paramSequence.hasNext()) {
      return null;
    }
    double d2 = ((Number)paramSequence.next()).doubleValue();
    double d1 = d2;
    if (Double.isNaN(d2)) {
      return Double.valueOf(d2);
    }
    while (paramSequence.hasNext())
    {
      d2 = ((Number)paramSequence.next()).doubleValue();
      if (Double.isNaN(d2)) {
        return Double.valueOf(d2);
      }
      if (d1 < d2) {
        d1 = d2;
      }
    }
    return Double.valueOf(d1);
  }
  
  public static final Float min(Sequence paramSequence)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$max");
    paramSequence = paramSequence.iterator();
    if (!paramSequence.hasNext()) {
      return null;
    }
    float f2 = ((Number)paramSequence.next()).floatValue();
    float f1 = f2;
    if (Float.isNaN(f2)) {
      return Float.valueOf(f2);
    }
    while (paramSequence.hasNext())
    {
      f2 = ((Number)paramSequence.next()).floatValue();
      if (Float.isNaN(f2)) {
        return Float.valueOf(f2);
      }
      if (f1 < f2) {
        f1 = f2;
      }
    }
    return Float.valueOf(f1);
  }
  
  public static final Object minBy(Sequence paramSequence, Function1 paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$minBy");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "selector");
    Iterator localIterator = paramSequence.iterator();
    if (!localIterator.hasNext()) {
      return null;
    }
    paramSequence = localIterator.next();
    Object localObject2 = paramSequence;
    if (!localIterator.hasNext()) {
      return paramSequence;
    }
    paramSequence = (Comparable)paramFunction1.invoke(paramSequence);
    Object localObject3;
    do
    {
      Object localObject4 = localIterator.next();
      Comparable localComparable = (Comparable)paramFunction1.invoke(localObject4);
      localObject3 = localObject2;
      Object localObject1 = paramSequence;
      if (paramSequence.compareTo(localComparable) > 0)
      {
        localObject3 = localObject4;
        localObject1 = localComparable;
      }
      localObject2 = localObject3;
      paramSequence = (Sequence)localObject1;
    } while (localIterator.hasNext());
    return localObject3;
  }
  
  public static final Object minWith(Sequence paramSequence, Comparator paramComparator)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$minWith");
    Intrinsics.checkParameterIsNotNull(paramComparator, "comparator");
    Iterator localIterator = paramSequence.iterator();
    if (!localIterator.hasNext()) {
      return null;
    }
    paramSequence = localIterator.next();
    while (localIterator.hasNext())
    {
      Object localObject = localIterator.next();
      if (paramComparator.compare(paramSequence, localObject) > 0) {
        paramSequence = localObject;
      }
    }
    return paramSequence;
  }
  
  public static final Sequence minus(Sequence paramSequence, final Iterable paramIterable)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$minus");
    Intrinsics.checkParameterIsNotNull(paramIterable, "elements");
    (Sequence)new Sequence()
    {
      public Iterator iterator()
      {
        Collection localCollection = CollectionsKt__IterablesKt.convertToSetForSetOperation(paramIterable);
        if (localCollection.isEmpty()) {
          return SequencesKt___SequencesKt.this.iterator();
        }
        SequencesKt___SequencesKt.filterNot(SequencesKt___SequencesKt.this, (Function1)new Lambda(localCollection)
        {
          public final boolean invoke(Object paramAnonymous2Object)
          {
            return contains(paramAnonymous2Object);
          }
        }).iterator();
      }
    };
  }
  
  public static final Sequence minus(Sequence paramSequence, final Object paramObject)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$minus");
    (Sequence)new Sequence()
    {
      public Iterator iterator()
      {
        final Ref.BooleanRef localBooleanRef = new Ref.BooleanRef();
        element = false;
        SequencesKt___SequencesKt.filter(SequencesKt___SequencesKt.this, (Function1)new Lambda(localBooleanRef)
        {
          public final boolean invoke(Object paramAnonymous2Object)
          {
            if ((!localBooleanRefelement) && (Intrinsics.areEqual(paramAnonymous2Object, $element)))
            {
              localBooleanRefelement = true;
              return false;
            }
            return true;
          }
        }).iterator();
      }
    };
  }
  
  public static final Sequence minus(Sequence paramSequence1, final Sequence paramSequence2)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence1, "$this$minus");
    Intrinsics.checkParameterIsNotNull(paramSequence2, "elements");
    (Sequence)new Sequence()
    {
      public Iterator iterator()
      {
        HashSet localHashSet = SequencesKt___SequencesKt.toHashSet(paramSequence2);
        if (localHashSet.isEmpty()) {
          return SequencesKt___SequencesKt.this.iterator();
        }
        SequencesKt___SequencesKt.filterNot(SequencesKt___SequencesKt.this, (Function1)new Lambda(localHashSet)
        {
          public final boolean invoke(Object paramAnonymous2Object)
          {
            return contains(paramAnonymous2Object);
          }
        }).iterator();
      }
    };
  }
  
  public static final Sequence minus(Sequence paramSequence, final Object[] paramArrayOfObject)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$minus");
    Intrinsics.checkParameterIsNotNull(paramArrayOfObject, "elements");
    int i;
    if (paramArrayOfObject.length == 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0) {
      return paramSequence;
    }
    (Sequence)new Sequence()
    {
      public Iterator iterator()
      {
        HashSet localHashSet = ArraysKt___ArraysKt.toHashSet(paramArrayOfObject);
        SequencesKt___SequencesKt.filterNot(SequencesKt___SequencesKt.this, (Function1)new Lambda(localHashSet)
        {
          public final boolean invoke(Object paramAnonymous2Object)
          {
            return contains(paramAnonymous2Object);
          }
        }).iterator();
      }
    };
  }
  
  private static final Sequence minusElement(Sequence paramSequence, Object paramObject)
  {
    return minus(paramSequence, paramObject);
  }
  
  public static final boolean none(Sequence paramSequence)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$none");
    return paramSequence.iterator().hasNext() ^ true;
  }
  
  public static final boolean none(Sequence paramSequence, Function1 paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$none");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "predicate");
    paramSequence = paramSequence.iterator();
    while (paramSequence.hasNext()) {
      if (((Boolean)paramFunction1.invoke(paramSequence.next())).booleanValue()) {
        return false;
      }
    }
    return true;
  }
  
  public static final Sequence onEach(Sequence paramSequence, Function1 paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$onEach");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "action");
    map(paramSequence, (Function1)new Lambda(paramFunction1)
    {
      public final Object invoke(Object paramAnonymousObject)
      {
        SequencesKt___SequencesKt.this.invoke(paramAnonymousObject);
        return paramAnonymousObject;
      }
    });
  }
  
  public static final Pair partition(Sequence paramSequence, Function1 paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$partition");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "predicate");
    ArrayList localArrayList1 = new ArrayList();
    ArrayList localArrayList2 = new ArrayList();
    paramSequence = paramSequence.iterator();
    while (paramSequence.hasNext())
    {
      Object localObject = paramSequence.next();
      if (((Boolean)paramFunction1.invoke(localObject)).booleanValue()) {
        localArrayList1.add(localObject);
      } else {
        localArrayList2.add(localObject);
      }
    }
    return new Pair(localArrayList1, localArrayList2);
  }
  
  public static final Sequence plus(Sequence paramSequence, Iterable paramIterable)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$plus");
    Intrinsics.checkParameterIsNotNull(paramIterable, "elements");
    return SequencesKt__SequencesKt.flatten(SequencesKt__SequencesKt.sequenceOf(new Sequence[] { paramSequence, CollectionsKt___CollectionsKt.asSequence(paramIterable) }));
  }
  
  public static final Sequence plus(Sequence paramSequence, Object paramObject)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$plus");
    return SequencesKt__SequencesKt.flatten(SequencesKt__SequencesKt.sequenceOf(new Sequence[] { paramSequence, SequencesKt__SequencesKt.sequenceOf(new Object[] { paramObject }) }));
  }
  
  public static final Sequence plus(Sequence paramSequence1, Sequence paramSequence2)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence1, "$this$plus");
    Intrinsics.checkParameterIsNotNull(paramSequence2, "elements");
    return SequencesKt__SequencesKt.flatten(SequencesKt__SequencesKt.sequenceOf(new Sequence[] { paramSequence1, paramSequence2 }));
  }
  
  public static final Sequence plus(Sequence paramSequence, Object[] paramArrayOfObject)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$plus");
    Intrinsics.checkParameterIsNotNull(paramArrayOfObject, "elements");
    return plus(paramSequence, (Iterable)ArraysKt___ArraysJvmKt.asList(paramArrayOfObject));
  }
  
  private static final Sequence plusElement(Sequence paramSequence, Object paramObject)
  {
    return plus(paramSequence, paramObject);
  }
  
  public static final Object reduce(Sequence paramSequence, Function2 paramFunction2)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$reduce");
    Intrinsics.checkParameterIsNotNull(paramFunction2, "operation");
    Iterator localIterator = paramSequence.iterator();
    if (localIterator.hasNext())
    {
      for (paramSequence = localIterator.next(); localIterator.hasNext(); paramSequence = paramFunction2.invoke(paramSequence, localIterator.next())) {}
      return paramSequence;
    }
    paramSequence = (Throwable)new UnsupportedOperationException("Empty sequence can't be reduced.");
    throw paramSequence;
  }
  
  public static final boolean reduce(Sequence paramSequence)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$any");
    return paramSequence.iterator().hasNext();
  }
  
  public static final Object reduceIndexed(Sequence paramSequence, Function3 paramFunction3)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$reduceIndexed");
    Intrinsics.checkParameterIsNotNull(paramFunction3, "operation");
    Iterator localIterator = paramSequence.iterator();
    if (localIterator.hasNext())
    {
      paramSequence = localIterator.next();
      int i = 1;
      while (localIterator.hasNext())
      {
        if (i < 0) {
          if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
            CollectionsKt__CollectionsKt.throwIndexOverflow();
          } else {
            throw ((Throwable)new ArithmeticException("Index overflow has happened."));
          }
        }
        paramSequence = paramFunction3.invoke(Integer.valueOf(i), paramSequence, localIterator.next());
        i += 1;
      }
      return paramSequence;
    }
    paramSequence = (Throwable)new UnsupportedOperationException("Empty sequence can't be reduced.");
    throw paramSequence;
  }
  
  public static final Sequence requireNoNulls(Sequence paramSequence)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$requireNoNulls");
    map(paramSequence, (Function1)new Lambda(paramSequence)
    {
      public final Object invoke(Object paramAnonymousObject)
      {
        if (paramAnonymousObject != null) {
          return paramAnonymousObject;
        }
        paramAnonymousObject = new StringBuilder();
        paramAnonymousObject.append("null element found in ");
        paramAnonymousObject.append(SequencesKt___SequencesKt.this);
        paramAnonymousObject.append('.');
        throw ((Throwable)new IllegalArgumentException(paramAnonymousObject.toString()));
      }
    });
  }
  
  public static final Object single(Sequence paramSequence)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$single");
    paramSequence = paramSequence.iterator();
    if (paramSequence.hasNext())
    {
      Object localObject = paramSequence.next();
      if (!paramSequence.hasNext()) {
        return localObject;
      }
      throw ((Throwable)new IllegalArgumentException("Sequence has more than one element."));
    }
    throw ((Throwable)new NoSuchElementException("Sequence is empty."));
  }
  
  public static final Object single(Sequence paramSequence, Function1 paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$single");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "predicate");
    Iterator localIterator = paramSequence.iterator();
    paramSequence = null;
    int i = 0;
    while (localIterator.hasNext())
    {
      Object localObject = localIterator.next();
      if (((Boolean)paramFunction1.invoke(localObject)).booleanValue()) {
        if (i == 0)
        {
          paramSequence = localObject;
          i = 1;
        }
        else
        {
          throw ((Throwable)new IllegalArgumentException("Sequence contains more than one matching element."));
        }
      }
    }
    if (i != 0) {
      return paramSequence;
    }
    paramSequence = (Throwable)new NoSuchElementException("Sequence contains no element matching the predicate.");
    throw paramSequence;
  }
  
  public static final Object singleOrNull(Sequence paramSequence)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$singleOrNull");
    paramSequence = paramSequence.iterator();
    if (!paramSequence.hasNext()) {
      return null;
    }
    Object localObject = paramSequence.next();
    if (paramSequence.hasNext()) {
      return null;
    }
    return localObject;
  }
  
  public static final Object singleOrNull(Sequence paramSequence, Function1 paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$singleOrNull");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "predicate");
    Iterator localIterator = paramSequence.iterator();
    int i = 0;
    paramSequence = null;
    while (localIterator.hasNext())
    {
      Object localObject = localIterator.next();
      if (((Boolean)paramFunction1.invoke(localObject)).booleanValue())
      {
        if (i != 0) {
          return null;
        }
        i = 1;
        paramSequence = localObject;
      }
    }
    if (i == 0) {
      return null;
    }
    return paramSequence;
  }
  
  public static final Sequence sorted(Sequence paramSequence)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$sorted");
    (Sequence)new Sequence()
    {
      public Iterator iterator()
      {
        List localList = SequencesKt___SequencesKt.toMutableList(SequencesKt___SequencesKt.this);
        CollectionsKt__MutableCollectionsJVMKt.sort(localList);
        return localList.iterator();
      }
    };
  }
  
  public static final Sequence sortedBy(Sequence paramSequence, Function1 paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$sortedBy");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "selector");
    return sortedWith(paramSequence, (Comparator)new ComparisonsKt__ComparisonsKt.compareBy.2(paramFunction1));
  }
  
  public static final Sequence sortedByDescending(Sequence paramSequence, Function1 paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$sortedByDescending");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "selector");
    return sortedWith(paramSequence, (Comparator)new ComparisonsKt__ComparisonsKt.compareByDescending.1(paramFunction1));
  }
  
  public static final Sequence sortedDescending(Sequence paramSequence)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$sortedDescending");
    return sortedWith(paramSequence, ComparisonsKt__ComparisonsKt.reverseOrder());
  }
  
  public static final Sequence sortedWith(Sequence paramSequence, final Comparator paramComparator)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$sortedWith");
    Intrinsics.checkParameterIsNotNull(paramComparator, "comparator");
    (Sequence)new Sequence()
    {
      public Iterator iterator()
      {
        List localList = SequencesKt___SequencesKt.toMutableList(SequencesKt___SequencesKt.this);
        CollectionsKt__MutableCollectionsJVMKt.sortWith(localList, paramComparator);
        return localList.iterator();
      }
    };
  }
  
  public static final int sumBy(Sequence paramSequence, Function1 paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$sumBy");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "selector");
    paramSequence = paramSequence.iterator();
    int i = 0;
    while (paramSequence.hasNext()) {
      i += ((Number)paramFunction1.invoke(paramSequence.next())).intValue();
    }
    return i;
  }
  
  public static final double sumByDouble(Sequence paramSequence, Function1 paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$sumByDouble");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "selector");
    paramSequence = paramSequence.iterator();
    for (double d = 0.0D; paramSequence.hasNext(); d += ((Number)paramFunction1.invoke(paramSequence.next())).doubleValue()) {}
    return d;
  }
  
  public static final int sumOfByte(Sequence paramSequence)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$sum");
    paramSequence = paramSequence.iterator();
    int i = 0;
    while (paramSequence.hasNext()) {
      i += ((Number)paramSequence.next()).byteValue();
    }
    return i;
  }
  
  public static final double sumOfDouble(Sequence paramSequence)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$sum");
    paramSequence = paramSequence.iterator();
    for (double d = 0.0D; paramSequence.hasNext(); d += ((Number)paramSequence.next()).doubleValue()) {}
    return d;
  }
  
  public static final float sumOfFloat(Sequence paramSequence)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$sum");
    paramSequence = paramSequence.iterator();
    for (float f = 0.0F; paramSequence.hasNext(); f += ((Number)paramSequence.next()).floatValue()) {}
    return f;
  }
  
  public static final int sumOfInt(Sequence paramSequence)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$sum");
    paramSequence = paramSequence.iterator();
    int i = 0;
    while (paramSequence.hasNext()) {
      i += ((Number)paramSequence.next()).intValue();
    }
    return i;
  }
  
  public static final long sumOfLong(Sequence paramSequence)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$sum");
    paramSequence = paramSequence.iterator();
    for (long l = 0L; paramSequence.hasNext(); l += ((Number)paramSequence.next()).longValue()) {}
    return l;
  }
  
  public static final int sumOfShort(Sequence paramSequence)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$sum");
    paramSequence = paramSequence.iterator();
    int i = 0;
    while (paramSequence.hasNext()) {
      i += ((Number)paramSequence.next()).shortValue();
    }
    return i;
  }
  
  public static final Sequence take(Sequence paramSequence, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$take");
    int i;
    if (paramInt >= 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      if (paramInt == 0) {
        return SequencesKt__SequencesKt.emptySequence();
      }
      if ((paramSequence instanceof DropTakeSequence)) {
        return ((DropTakeSequence)paramSequence).take(paramInt);
      }
      return (Sequence)new TakeSequence(paramSequence, paramInt);
    }
    paramSequence = new StringBuilder();
    paramSequence.append("Requested element count ");
    paramSequence.append(paramInt);
    paramSequence.append(" is less than zero.");
    throw ((Throwable)new IllegalArgumentException(paramSequence.toString().toString()));
  }
  
  public static final Sequence takeWhile(Sequence paramSequence, Function1 paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$takeWhile");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "predicate");
    return (Sequence)new TakeWhileSequence(paramSequence, paramFunction1);
  }
  
  public static final Collection toCollection(Sequence paramSequence, Collection paramCollection)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$toCollection");
    Intrinsics.checkParameterIsNotNull(paramCollection, "destination");
    paramSequence = paramSequence.iterator();
    while (paramSequence.hasNext()) {
      paramCollection.add(paramSequence.next());
    }
    return paramCollection;
  }
  
  public static final HashSet toHashSet(Sequence paramSequence)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$toHashSet");
    return (HashSet)toCollection(paramSequence, (Collection)new HashSet());
  }
  
  public static final List toList(Sequence paramSequence)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$toList");
    return CollectionsKt__CollectionsKt.optimizeReadOnlyList(toMutableList(paramSequence));
  }
  
  public static final List toMutableList(Sequence paramSequence)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$toMutableList");
    return (List)toCollection(paramSequence, (Collection)new ArrayList());
  }
  
  public static final Set toMutableSet(Sequence paramSequence)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$toMutableSet");
    LinkedHashSet localLinkedHashSet = new LinkedHashSet();
    paramSequence = paramSequence.iterator();
    while (paramSequence.hasNext()) {
      localLinkedHashSet.add(paramSequence.next());
    }
    return (Set)localLinkedHashSet;
  }
  
  public static final Set toSet(Sequence paramSequence)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$toSet");
    return SetsKt__SetsKt.optimizeReadOnlySet((Set)toCollection(paramSequence, (Collection)new LinkedHashSet()));
  }
  
  public static final Sequence windowed(Sequence paramSequence, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$windowed");
    return SlidingWindowKt.windowedSequence(paramSequence, paramInt1, paramInt2, paramBoolean, false);
  }
  
  public static final Sequence windowed(Sequence paramSequence, int paramInt1, int paramInt2, boolean paramBoolean, Function1 paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$windowed");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "transform");
    return map(SlidingWindowKt.windowedSequence(paramSequence, paramInt1, paramInt2, paramBoolean, true), paramFunction1);
  }
  
  public static final Sequence withIndex(Sequence paramSequence)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$withIndex");
    return (Sequence)new IndexingSequence(paramSequence);
  }
  
  public static final Sequence zip(Sequence paramSequence1, Sequence paramSequence2)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence1, "$this$zip");
    Intrinsics.checkParameterIsNotNull(paramSequence2, "other");
    return (Sequence)new MergingSequence(paramSequence1, paramSequence2, (Function2)zip.1.INSTANCE);
  }
  
  public static final Sequence zip(Sequence paramSequence1, Sequence paramSequence2, Function2 paramFunction2)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence1, "$this$zip");
    Intrinsics.checkParameterIsNotNull(paramSequence2, "other");
    Intrinsics.checkParameterIsNotNull(paramFunction2, "transform");
    return (Sequence)new MergingSequence(paramSequence1, paramSequence2, paramFunction2);
  }
  
  public static final Sequence zipWithNext(Sequence paramSequence)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$zipWithNext");
    return zipWithNext(paramSequence, (Function2)zipWithNext.1.INSTANCE);
  }
  
  public static final Sequence zipWithNext(Sequence paramSequence, final Function2 paramFunction2)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$zipWithNext");
    Intrinsics.checkParameterIsNotNull(paramFunction2, "transform");
    SequencesKt__SequenceBuilderKt.sequence((Function2)new kotlin.coroutines.stats.internal.RestrictedSuspendLambda(paramSequence, paramFunction2)
    {
      private SequenceScope ARMv6;
      Object arch;
      Object head;
      int label;
      Object last;
      Object value;
      
      public final Continuation create(Object paramAnonymousObject, Continuation paramAnonymousContinuation)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousContinuation, "completion");
        paramAnonymousContinuation = new 2(SequencesKt___SequencesKt.this, paramFunction2, paramAnonymousContinuation);
        ARMv6 = ((SequenceScope)paramAnonymousObject);
        return paramAnonymousContinuation;
      }
      
      public final Object invoke(Object paramAnonymousObject1, Object paramAnonymousObject2)
      {
        return ((2)create(paramAnonymousObject1, (Continuation)paramAnonymousObject2)).invokeSuspend(Unit.INSTANCE);
      }
      
      public final Object invokeSuspend(Object paramAnonymousObject)
      {
        Object localObject1 = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = label;
        Object localObject2;
        Iterator localIterator;
        SequenceScope localSequenceScope;
        if (i != 0)
        {
          if (i == 1)
          {
            localObject2 = value;
            localIterator = (Iterator)head;
            localSequenceScope = (SequenceScope)arch;
            ResultKt.throwOnFailure(paramAnonymousObject);
            paramAnonymousObject = localObject2;
          }
          else
          {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
          }
        }
        else
        {
          ResultKt.throwOnFailure(paramAnonymousObject);
          localSequenceScope = ARMv6;
          localIterator = iterator();
          if (!localIterator.hasNext()) {
            return Unit.INSTANCE;
          }
        }
        Object localObject3;
        for (paramAnonymousObject = localIterator.next();; paramAnonymousObject = localObject3)
        {
          localObject2 = localObject1;
          if (!localIterator.hasNext()) {
            break;
          }
          localObject3 = localIterator.next();
          Object localObject4 = paramFunction2.invoke(paramAnonymousObject, localObject3);
          arch = localSequenceScope;
          head = localIterator;
          last = paramAnonymousObject;
          value = localObject3;
          label = 1;
          if (localSequenceScope.yield(localObject4, this) == localObject2) {
            return localObject2;
          }
        }
        return Unit.INSTANCE;
      }
    });
  }
}
