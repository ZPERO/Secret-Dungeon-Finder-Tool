package com.google.android.android.measurement.internal;

import android.text.TextUtils;
import androidx.collection.ArrayMap;
import com.google.android.android.common.internal.Preconditions;
import com.google.android.android.internal.measurement.zzfv;
import com.google.android.android.internal.measurement.zzfw;
import com.google.android.android.internal.measurement.zzfx;
import com.google.android.android.internal.measurement.zzfy;
import com.google.android.android.internal.measurement.zzfz;
import com.google.android.android.internal.measurement.zzgd;
import com.google.android.android.internal.measurement.zzge;
import com.google.android.android.internal.measurement.zzgf;
import com.google.android.android.internal.measurement.zzgg;
import com.google.android.android.internal.measurement.zzgl;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

final class Array
  extends zzez
{
  Array(zzfa paramZzfa)
  {
    super(paramZzfa);
  }
  
  private static void add(Map paramMap, int paramInt, long paramLong)
  {
    throw new Runtime("d2j fail translate: java.lang.RuntimeException: fail exe a5 = a4\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.exec(BaseAnalyze.java:92)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.exec(BaseAnalyze.java:1)\n\tat com.googlecode.dex2jar.ir.ts.Cfg.dfs(Cfg.java:255)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.analyze0(BaseAnalyze.java:75)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.analyze(BaseAnalyze.java:69)\n\tat com.googlecode.dex2jar.ir.ts.UnSSATransformer.transform(UnSSATransformer.java:274)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:163)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\nCaused by: java.lang.NullPointerException\n");
  }
  
  private static Boolean apply(BigDecimal paramBigDecimal, zzfx paramZzfx, double paramDouble)
  {
    Preconditions.checkNotNull(paramZzfx);
    int i;
    Object localObject;
    if (zzavo != null)
    {
      if (zzavo.intValue() == 0) {
        return null;
      }
      if (zzavo.intValue() == 4)
      {
        if (zzavr == null) {
          break label428;
        }
        if (zzavs == null) {
          return null;
        }
      }
      else if (zzavq == null)
      {
        return null;
      }
      i = zzavo.intValue();
      if (zzavo.intValue() == 4) {
        if (zzfg.zzcp(zzavr))
        {
          if (!zzfg.zzcp(zzavs)) {
            return null;
          }
          localObject = zzavr;
        }
      }
    }
    for (;;)
    {
      try
      {
        localObject = new BigDecimal((String)localObject);
        paramZzfx = zzavs;
        localBigDecimal = new BigDecimal(paramZzfx);
        paramZzfx = (zzfx)localObject;
        localObject = null;
        continue;
        return null;
        if (!zzfg.zzcp(zzavq)) {
          return null;
        }
        paramZzfx = zzavq;
      }
      catch (NumberFormatException paramBigDecimal)
      {
        BigDecimal localBigDecimal;
        boolean bool3;
        boolean bool1;
        boolean bool4;
        boolean bool5;
        boolean bool2;
        return null;
      }
      try
      {
        localObject = new BigDecimal(paramZzfx);
        paramZzfx = null;
        localBigDecimal = null;
        if (i == 4)
        {
          if (paramZzfx == null) {
            return null;
          }
        }
        else {
          if (localObject == null) {
            break;
          }
        }
        bool3 = false;
        bool1 = false;
        bool4 = false;
        bool5 = false;
        bool2 = false;
        if (i != 1)
        {
          if (i != 2)
          {
            if (i != 3)
            {
              if (i != 4) {
                return null;
              }
              bool1 = bool2;
              if (paramBigDecimal.compareTo(paramZzfx) != -1)
              {
                bool1 = bool2;
                if (paramBigDecimal.compareTo(localBigDecimal) != 1) {
                  bool1 = true;
                }
              }
              return Boolean.valueOf(bool1);
            }
            if (paramDouble != 0.0D)
            {
              bool1 = bool3;
              if (paramBigDecimal.compareTo(((BigDecimal)localObject).subtract(new BigDecimal(paramDouble).multiply(new BigDecimal(2)))) == 1)
              {
                bool1 = bool3;
                if (paramBigDecimal.compareTo(((BigDecimal)localObject).add(new BigDecimal(paramDouble).multiply(new BigDecimal(2)))) == -1) {
                  bool1 = true;
                }
              }
              return Boolean.valueOf(bool1);
            }
            if (paramBigDecimal.compareTo((BigDecimal)localObject) == 0) {
              bool1 = true;
            }
            return Boolean.valueOf(bool1);
          }
          bool1 = bool4;
          if (paramBigDecimal.compareTo((BigDecimal)localObject) == 1) {
            bool1 = true;
          }
          return Boolean.valueOf(bool1);
        }
        bool1 = bool5;
        if (paramBigDecimal.compareTo((BigDecimal)localObject) == -1) {
          bool1 = true;
        }
        return Boolean.valueOf(bool1);
      }
      catch (NumberFormatException paramBigDecimal) {}
    }
    return null;
    label428:
    return null;
  }
  
  private final Boolean create(String paramString1, int paramInt, boolean paramBoolean, String paramString2, List paramList, String paramString3)
  {
    if (paramString1 == null) {
      return null;
    }
    if (paramInt == 6)
    {
      if (paramList == null) {
        break label238;
      }
      if (paramList.size() == 0) {
        return null;
      }
    }
    else if (paramString2 == null)
    {
      return null;
    }
    String str = paramString1;
    if (!paramBoolean) {
      if (paramInt == 1) {
        str = paramString1;
      } else {
        str = paramString1.toUpperCase(Locale.ENGLISH);
      }
    }
    switch (paramInt)
    {
    default: 
      return null;
    case 6: 
      return Boolean.valueOf(paramList.contains(str));
    case 5: 
      return Boolean.valueOf(str.equals(paramString2));
    case 4: 
      return Boolean.valueOf(str.contains(paramString2));
    case 3: 
      return Boolean.valueOf(str.endsWith(paramString2));
    case 2: 
      return Boolean.valueOf(str.startsWith(paramString2));
    }
    if (paramBoolean) {
      paramInt = 0;
    } else {
      paramInt = 66;
    }
    try
    {
      paramBoolean = Pattern.compile(paramString3, paramInt).matcher(str).matches();
      return Boolean.valueOf(paramBoolean);
    }
    catch (PatternSyntaxException paramString1)
    {
      for (;;) {}
    }
    zzgo().zzjg().append("Invalid regular expression in REGEXP audience filter. expression", paramString3);
    return null;
    label238:
    return null;
  }
  
  private final Boolean get(double paramDouble, zzfx paramZzfx)
  {
    try
    {
      paramZzfx = apply(new BigDecimal(paramDouble), paramZzfx, Math.ulp(paramDouble));
      return paramZzfx;
    }
    catch (NumberFormatException paramZzfx)
    {
      for (;;) {}
    }
    return null;
  }
  
  private final Boolean get(long paramLong, zzfx paramZzfx)
  {
    try
    {
      paramZzfx = apply(new BigDecimal(paramLong), paramZzfx, 0.0D);
      return paramZzfx;
    }
    catch (NumberFormatException paramZzfx)
    {
      for (;;) {}
    }
    return null;
  }
  
  private final Boolean get(zzfy paramZzfy, zzgl paramZzgl)
  {
    paramZzfy = zzavv;
    if (paramZzfy == null)
    {
      zzgo().zzjg().append("Missing property filter. property", zzgl().zzbu(name));
      return null;
    }
    boolean bool = Boolean.TRUE.equals(zzavm);
    if (zzawx != null)
    {
      if (zzavl == null)
      {
        zzgo().zzjg().append("No number filter for long property. property", zzgl().zzbu(name));
        return null;
      }
      return set(get(zzawx.longValue(), zzavl), bool);
    }
    if (zzauh != null)
    {
      if (zzavl == null)
      {
        zzgo().zzjg().append("No number filter for double property. property", zzgl().zzbu(name));
        return null;
      }
      return set(get(zzauh.doubleValue(), zzavl), bool);
    }
    if (zzamp != null)
    {
      if (zzavk == null)
      {
        if (zzavl == null)
        {
          zzgo().zzjg().append("No string or number filter defined. property", zzgl().zzbu(name));
          return null;
        }
        if (zzfg.zzcp(zzamp)) {
          return set(get(zzamp, zzavl), bool);
        }
        zzgo().zzjg().append("Invalid user property value for Numeric number filter. property, value", zzgl().zzbu(name), zzamp);
        return null;
      }
      return set(parse(zzamp, zzavk), bool);
    }
    zzgo().zzjg().append("User property has no value, property", zzgl().zzbu(name));
    return null;
  }
  
  private final Boolean get(String paramString, zzfx paramZzfx)
  {
    if (!zzfg.zzcp(paramString)) {
      return null;
    }
    try
    {
      paramString = apply(new BigDecimal(paramString), paramZzfx, 0.0D);
      return paramString;
    }
    catch (NumberFormatException paramString) {}
    return null;
  }
  
  private final Boolean getValue(zzfv paramZzfv, String paramString, zzgg[] paramArrayOfZzgg, long paramLong)
  {
    Object localObject1 = zzavi;
    int j = 0;
    Boolean localBoolean = Boolean.valueOf(false);
    if (localObject1 != null)
    {
      localObject1 = get(paramLong, zzavi);
      if (localObject1 == null) {
        return null;
      }
      if (!((Boolean)localObject1).booleanValue()) {
        return localBoolean;
      }
    }
    Object localObject2 = new HashSet();
    localObject1 = zzavg;
    int k = localObject1.length;
    int i = 0;
    Object localObject3;
    while (i < k)
    {
      localObject3 = localObject1[i];
      if (TextUtils.isEmpty(zzavn))
      {
        zzgo().zzjg().append("null or empty param name in filter. event", zzgl().zzbs(paramString));
        return null;
      }
      ((Set)localObject2).add(zzavn);
      i += 1;
    }
    localObject1 = new ArrayMap();
    k = paramArrayOfZzgg.length;
    i = 0;
    while (i < k)
    {
      localObject3 = paramArrayOfZzgg[i];
      if (((Set)localObject2).contains(name)) {
        if (zzawx != null)
        {
          ((Map)localObject1).put(name, zzawx);
        }
        else if (zzauh != null)
        {
          ((Map)localObject1).put(name, zzauh);
        }
        else if (zzamp != null)
        {
          ((Map)localObject1).put(name, zzamp);
        }
        else
        {
          zzgo().zzjg().append("Unknown value for param. event, param", zzgl().zzbs(paramString), zzgl().zzbt(name));
          return null;
        }
      }
      i += 1;
    }
    paramArrayOfZzgg = zzavg;
    k = paramArrayOfZzgg.length;
    i = j;
    while (i < k)
    {
      paramZzfv = paramArrayOfZzgg[i];
      boolean bool = Boolean.TRUE.equals(zzavm);
      localObject2 = zzavn;
      if (TextUtils.isEmpty((CharSequence)localObject2))
      {
        zzgo().zzjg().append("Event has empty param name. event", zzgl().zzbs(paramString));
        return null;
      }
      localObject3 = ((Map)localObject1).get(localObject2);
      if ((localObject3 instanceof Long))
      {
        if (zzavl == null)
        {
          zzgo().zzjg().append("No number filter for long param. event, param", zzgl().zzbs(paramString), zzgl().zzbt((String)localObject2));
          return null;
        }
        paramZzfv = get(((Long)localObject3).longValue(), zzavl);
        if (paramZzfv == null) {
          return null;
        }
        if ((true ^ paramZzfv.booleanValue() ^ bool)) {
          return localBoolean;
        }
      }
      else if ((localObject3 instanceof Double))
      {
        if (zzavl == null)
        {
          zzgo().zzjg().append("No number filter for double param. event, param", zzgl().zzbs(paramString), zzgl().zzbt((String)localObject2));
          return null;
        }
        paramZzfv = get(((Double)localObject3).doubleValue(), zzavl);
        if (paramZzfv == null) {
          return null;
        }
        if ((true ^ paramZzfv.booleanValue() ^ bool)) {
          return localBoolean;
        }
      }
      else
      {
        if (!(localObject3 instanceof String)) {
          break label732;
        }
        if (zzavk != null)
        {
          paramZzfv = parse((String)localObject3, zzavk);
        }
        else
        {
          if (zzavl == null) {
            break label700;
          }
          localObject3 = (String)localObject3;
          if (!zzfg.zzcp((String)localObject3)) {
            break label668;
          }
          paramZzfv = get((String)localObject3, zzavl);
        }
        if (paramZzfv == null) {
          return null;
        }
        if ((true ^ paramZzfv.booleanValue() ^ bool)) {
          return localBoolean;
        }
      }
      i += 1;
      continue;
      label668:
      zzgo().zzjg().append("Invalid param value for number filter. event, param", zzgl().zzbs(paramString), zzgl().zzbt((String)localObject2));
      return null;
      label700:
      zzgo().zzjg().append("No filter for String param. event, param", zzgl().zzbs(paramString), zzgl().zzbt((String)localObject2));
      return null;
      label732:
      if (localObject3 == null)
      {
        zzgo().zzjl().append("Missing param for filter. event, param", zzgl().zzbs(paramString), zzgl().zzbt((String)localObject2));
        return localBoolean;
      }
      zzgo().zzjg().append("Unknown param type. event, param", zzgl().zzbs(paramString), zzgl().zzbt((String)localObject2));
      return null;
    }
    return Boolean.valueOf(true);
  }
  
  private final Boolean parse(String paramString, zzfz paramZzfz)
  {
    Preconditions.checkNotNull(paramZzfz);
    if (paramString == null) {
      return null;
    }
    if (zzavw != null)
    {
      if (zzavw.intValue() == 0) {
        return null;
      }
      if (zzavw.intValue() == 6)
      {
        if (zzavz == null) {
          break label260;
        }
        if (zzavz.length == 0) {
          return null;
        }
      }
      else if (zzavx == null)
      {
        return null;
      }
      int j = zzavw.intValue();
      Object localObject1 = zzavy;
      int i = 0;
      boolean bool;
      if ((localObject1 != null) && (zzavy.booleanValue())) {
        bool = true;
      } else {
        bool = false;
      }
      if ((!bool) && (j != 1) && (j != 6)) {
        localObject1 = zzavx.toUpperCase(Locale.ENGLISH);
      } else {
        localObject1 = zzavx;
      }
      Object localObject2;
      if (zzavz == null)
      {
        paramZzfz = null;
      }
      else
      {
        localObject2 = zzavz;
        if (bool)
        {
          paramZzfz = Arrays.asList((Object[])localObject2);
        }
        else
        {
          paramZzfz = new ArrayList();
          int k = localObject2.length;
          while (i < k)
          {
            paramZzfz.add(localObject2[i].toUpperCase(Locale.ENGLISH));
            i += 1;
          }
        }
      }
      if (j == 1) {
        localObject2 = localObject1;
      } else {
        localObject2 = null;
      }
      return create(paramString, j, bool, (String)localObject1, (List)paramZzfz, (String)localObject2);
    }
    label260:
    return null;
  }
  
  private static Boolean set(Boolean paramBoolean, boolean paramBoolean1)
  {
    if (paramBoolean == null) {
      return null;
    }
    return Boolean.valueOf(paramBoolean.booleanValue() ^ paramBoolean1);
  }
  
  private static void set(Map paramMap, int paramInt, long paramLong)
  {
    Long localLong = (Long)paramMap.get(Integer.valueOf(paramInt));
    paramLong /= 1000L;
    if ((localLong == null) || (paramLong > localLong.longValue())) {
      paramMap.put(Integer.valueOf(paramInt), Long.valueOf(paramLong));
    }
  }
  
  private static zzge[] toArray(Map paramMap)
  {
    if (paramMap == null) {
      return null;
    }
    int i = 0;
    zzge[] arrayOfZzge = new zzge[paramMap.size()];
    Iterator localIterator = paramMap.keySet().iterator();
    while (localIterator.hasNext())
    {
      Integer localInteger = (Integer)localIterator.next();
      zzge localZzge = new zzge();
      zzawq = localInteger;
      zzawr = ((Long)paramMap.get(localInteger));
      arrayOfZzge[i] = localZzge;
      i += 1;
    }
    return arrayOfZzge;
  }
  
  final zzgd[] replace(String paramString, zzgf[] paramArrayOfZzgf, zzgl[] paramArrayOfZzgl)
  {
    throw new Runtime("d2j fail translate: java.lang.RuntimeException: fail exe a5 = a4\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.exec(BaseAnalyze.java:92)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.exec(BaseAnalyze.java:1)\n\tat com.googlecode.dex2jar.ir.ts.Cfg.dfs(Cfg.java:255)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.analyze0(BaseAnalyze.java:75)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.analyze(BaseAnalyze.java:69)\n\tat com.googlecode.dex2jar.ir.ts.UnSSATransformer.transform(UnSSATransformer.java:274)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:163)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\nCaused by: java.lang.NullPointerException\n");
  }
  
  protected final boolean zzgt()
  {
    return false;
  }
}
