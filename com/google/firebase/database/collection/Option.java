package com.google.firebase.database.collection;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

final class Option<A, B, C>
{
  private final ImmutableSortedMap.Builder.KeyTranslator<A, B> defaultValue;
  private LLRBValueNode<A, C> path;
  private final List<A> ranges;
  private LLRBValueNode<A, C> title;
  private final Map<B, C> values;
  
  private Option(List paramList, Map paramMap, ImmutableSortedMap.Builder.KeyTranslator paramKeyTranslator)
  {
    ranges = paramList;
    values = paramMap;
    defaultValue = paramKeyTranslator;
  }
  
  private final LLRBNode get(int paramInt1, int paramInt2)
  {
    if (paramInt2 == 0) {
      return LLRBEmptyNode.getInstance();
    }
    if (paramInt2 == 1)
    {
      localObject1 = ranges.get(paramInt1);
      return new LLRBBlackValueNode(localObject1, getValue(localObject1), null, null);
    }
    paramInt2 /= 2;
    int i = paramInt1 + paramInt2;
    Object localObject1 = get(paramInt1, paramInt2);
    LLRBNode localLLRBNode = get(i + 1, paramInt2);
    Object localObject2 = ranges.get(i);
    return new LLRBBlackValueNode(localObject2, getValue(localObject2), (LLRBNode)localObject1, localLLRBNode);
  }
  
  private final Object getValue(Object paramObject)
  {
    return values.get(defaultValue.translate(paramObject));
  }
  
  public static Cache toString(List paramList, Map paramMap, ImmutableSortedMap.Builder.KeyTranslator paramKeyTranslator, Comparator paramComparator)
  {
    paramMap = new Option(paramList, paramMap, paramKeyTranslator);
    Collections.sort(paramList, paramComparator);
    paramKeyTranslator = new MultidimensionalCounter(paramList.size()).iterator();
    int i = paramList.size();
    while (paramKeyTranslator.hasNext())
    {
      Count localCount = (Count)paramKeyTranslator.next();
      i -= zzab;
      if (zzaa)
      {
        paramList = LLRBNode.Color.next;
      }
      else
      {
        paramMap.toString(LLRBNode.Color.next, zzab, i);
        i -= zzab;
        paramList = LLRBNode.Color.data;
      }
      paramMap.toString(paramList, zzab, i);
    }
    paramMap = path;
    paramList = paramMap;
    if (paramMap == null) {
      paramList = LLRBEmptyNode.getInstance();
    }
    return new Cache(paramList, paramComparator, null);
  }
  
  private final void toString(LLRBNode.Color paramColor, int paramInt1, int paramInt2)
  {
    throw new Runtime("d2j fail translate: java.lang.RuntimeException: fail exe a7 = a6\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.exec(BaseAnalyze.java:92)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.exec(BaseAnalyze.java:1)\n\tat com.googlecode.dex2jar.ir.ts.Cfg.dfs(Cfg.java:255)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.analyze0(BaseAnalyze.java:75)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.analyze(BaseAnalyze.java:69)\n\tat com.googlecode.dex2jar.ir.ts.UnSSATransformer.transform(UnSSATransformer.java:274)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:163)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\nCaused by: java.lang.NullPointerException\n");
  }
}
