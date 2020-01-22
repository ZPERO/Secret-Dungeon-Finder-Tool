package kotlin.collections;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.TypeIntrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000&\n\000\n\002\020$\n\000\n\002\020\b\n\000\n\002\030\002\n\000\n\002\020%\n\002\b\003\n\002\030\002\n\002\020&\n\000\0320\020\000\032\016\022\004\022\002H\002\022\004\022\0020\0030\001\"\004\b\000\020\004\"\004\b\001\020\002*\016\022\004\022\002H\004\022\004\022\002H\0020\005H\007\032W\020\006\032\016\022\004\022\002H\002\022\004\022\002H\b0\007\"\004\b\000\020\002\"\004\b\001\020\t\"\004\b\002\020\b*\016\022\004\022\002H\002\022\004\022\002H\t0\0072\036\020\n\032\032\022\020\022\016\022\004\022\002H\002\022\004\022\002H\t0\f\022\004\022\002H\b0\013H?\b?\006\r"}, d2={"eachCount", "", "K", "", "T", "Lkotlin/collections/Grouping;", "mapValuesInPlace", "", "R", "V", "f", "Lkotlin/Function1;", "", "kotlin-stdlib"}, k=5, mv={1, 1, 15}, xi=1, xs="kotlin/collections/GroupingKt")
class GroupingKt__GroupingJVMKt
{
  public GroupingKt__GroupingJVMKt() {}
  
  public static final Map eachCount(Grouping paramGrouping)
  {
    throw new Runtime("d2j fail translate: java.lang.RuntimeException: fail exe a10 = a9\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.exec(BaseAnalyze.java:92)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.exec(BaseAnalyze.java:1)\n\tat com.googlecode.dex2jar.ir.ts.Cfg.dfs(Cfg.java:255)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.analyze0(BaseAnalyze.java:75)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.analyze(BaseAnalyze.java:69)\n\tat com.googlecode.dex2jar.ir.ts.UnSSATransformer.transform(UnSSATransformer.java:274)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:163)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\nCaused by: java.lang.NullPointerException\n");
  }
  
  private static final Map mapValuesInPlace(Map paramMap, Function1 paramFunction1)
  {
    Iterator localIterator = ((Iterable)paramMap.entrySet()).iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      if (localEntry != null) {
        TypeIntrinsics.asMutableMapEntry(localEntry).setValue(paramFunction1.invoke(localEntry));
      } else {
        throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.MutableMap.MutableEntry<K, R>");
      }
    }
    if (paramMap != null) {
      return TypeIntrinsics.asMutableMap(paramMap);
    }
    paramMap = new TypeCastException("null cannot be cast to non-null type kotlin.collections.MutableMap<K, R>");
    throw paramMap;
  }
}
