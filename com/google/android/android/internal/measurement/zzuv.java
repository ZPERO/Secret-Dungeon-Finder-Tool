package com.google.android.android.internal.measurement;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

final class zzuv
  implements zzyw
{
  private final zzut zzbuf;
  
  private zzuv(zzut paramZzut)
  {
    zzbuf = ((zzut)zzvo.attribute(paramZzut, "output"));
    zzbuf.zzbuw = this;
  }
  
  public static zzuv visitMethod(zzut paramZzut)
  {
    if (zzbuw != null) {
      return zzbuw;
    }
    return new zzuv(paramZzut);
  }
  
  public final void a(int paramInt, List paramList, zzxj paramZzxj)
    throws IOException
  {
    int i = 0;
    while (i < paramList.size())
    {
      invoke(paramInt, paramList.get(i), paramZzxj);
      i += 1;
    }
  }
  
  public final void a(int paramInt, List paramList, boolean paramBoolean)
    throws IOException
  {
    int i = 0;
    int j = 0;
    if (paramBoolean)
    {
      zzbuf.next(paramInt, 2);
      paramInt = 0;
      i = 0;
      while (paramInt < paramList.size())
      {
        i += zzut.zzbb(((Long)paramList.get(paramInt)).longValue());
        paramInt += 1;
      }
      zzbuf.zzay(i);
      paramInt = j;
      while (paramInt < paramList.size())
      {
        zzbuf.zzax(((Long)paramList.get(paramInt)).longValue());
        paramInt += 1;
      }
      return;
    }
    while (i < paramList.size())
    {
      zzbuf.append(paramInt, ((Long)paramList.get(i)).longValue());
      i += 1;
    }
  }
  
  public final void add(int paramInt, double paramDouble)
    throws IOException
  {
    zzbuf.setEntry(paramInt, paramDouble);
  }
  
  public final void add(int paramInt, float paramFloat)
    throws IOException
  {
    zzbuf.inc(paramInt, paramFloat);
  }
  
  public final void add(int paramInt, Object paramObject, zzxj paramZzxj)
    throws IOException
  {
    zzut localZzut = zzbuf;
    paramObject = (zzwt)paramObject;
    localZzut.next(paramInt, 3);
    paramZzxj.a(paramObject, zzbuw);
    localZzut.next(paramInt, 4);
  }
  
  public final void add(int paramInt, List paramList, zzxj paramZzxj)
    throws IOException
  {
    int i = 0;
    while (i < paramList.size())
    {
      add(paramInt, paramList.get(i), paramZzxj);
      i += 1;
    }
  }
  
  public final void add(int paramInt, List paramList, boolean paramBoolean)
    throws IOException
  {
    int i = 0;
    int j = 0;
    if (paramBoolean)
    {
      zzbuf.next(paramInt, 2);
      paramInt = 0;
      i = 0;
      while (paramInt < paramList.size())
      {
        i += zzut.add(((Double)paramList.get(paramInt)).doubleValue());
        paramInt += 1;
      }
      zzbuf.zzay(i);
      paramInt = j;
      while (paramInt < paramList.size())
      {
        zzbuf.set(((Double)paramList.get(paramInt)).doubleValue());
        paramInt += 1;
      }
      return;
    }
    while (i < paramList.size())
    {
      zzbuf.setEntry(paramInt, ((Double)paramList.get(i)).doubleValue());
      i += 1;
    }
  }
  
  public final void addAll(int paramInt, List paramList, boolean paramBoolean)
    throws IOException
  {
    int i = 0;
    int j = 0;
    if (paramBoolean)
    {
      zzbuf.next(paramInt, 2);
      paramInt = 0;
      i = 0;
      while (paramInt < paramList.size())
      {
        i += zzut.zzbg(((Integer)paramList.get(paramInt)).intValue());
        paramInt += 1;
      }
      zzbuf.zzay(i);
      paramInt = j;
      while (paramInt < paramList.size())
      {
        zzbuf.zzba(((Integer)paramList.get(paramInt)).intValue());
        paramInt += 1;
      }
      return;
    }
    while (i < paramList.size())
    {
      zzbuf.put(paramInt, ((Integer)paramList.get(i)).intValue());
      i += 1;
    }
  }
  
  public final void addHeaders(int paramInt, zzwm paramZzwm, Map paramMap)
    throws IOException
  {
    paramMap = paramMap.entrySet().iterator();
    while (paramMap.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)paramMap.next();
      zzbuf.next(paramInt, 2);
      zzbuf.zzay(zzwl.compare(paramZzwm, localEntry.getKey(), localEntry.getValue()));
      zzwl.addElement(zzbuf, paramZzwm, localEntry.getKey(), localEntry.getValue());
    }
  }
  
  public final void addResult(int paramInt, long paramLong)
    throws IOException
  {
    zzbuf.next(paramInt, paramLong);
  }
  
  public final void append(int paramInt, boolean paramBoolean)
    throws IOException
  {
    zzbuf.process(paramInt, paramBoolean);
  }
  
  public final void calculate(int paramInt, List paramList, boolean paramBoolean)
    throws IOException
  {
    int i = 0;
    int j = 0;
    if (paramBoolean)
    {
      zzbuf.next(paramInt, 2);
      paramInt = 0;
      i = 0;
      while (paramInt < paramList.size())
      {
        i += zzut.zzbf(((Integer)paramList.get(paramInt)).intValue());
        paramInt += 1;
      }
      zzbuf.zzay(i);
      paramInt = j;
      while (paramInt < paramList.size())
      {
        zzbuf.zzba(((Integer)paramList.get(paramInt)).intValue());
        paramInt += 1;
      }
      return;
    }
    while (i < paramList.size())
    {
      zzbuf.put(paramInt, ((Integer)paramList.get(i)).intValue());
      i += 1;
    }
  }
  
  public final void d(int paramInt, List paramList)
    throws IOException
  {
    int i = 0;
    while (i < paramList.size())
    {
      zzbuf.c(paramInt, (zzud)paramList.get(i));
      i += 1;
    }
  }
  
  public final void d(int paramInt, List paramList, boolean paramBoolean)
    throws IOException
  {
    int i = 0;
    int j = 0;
    if (paramBoolean)
    {
      zzbuf.next(paramInt, 2);
      paramInt = 0;
      i = 0;
      while (paramInt < paramList.size())
      {
        i += zzut.zzbd(((Integer)paramList.get(paramInt)).intValue());
        paramInt += 1;
      }
      zzbuf.zzay(i);
      paramInt = j;
      while (paramInt < paramList.size())
      {
        zzbuf.zzay(((Integer)paramList.get(paramInt)).intValue());
        paramInt += 1;
      }
      return;
    }
    while (i < paramList.size())
    {
      zzbuf.append(paramInt, ((Integer)paramList.get(i)).intValue());
      i += 1;
    }
  }
  
  public final void e(int paramInt, zzud paramZzud)
    throws IOException
  {
    zzbuf.c(paramInt, paramZzud);
  }
  
  public final void f(int paramInt, Object paramObject)
    throws IOException
  {
    if ((paramObject instanceof zzud))
    {
      zzbuf.b(paramInt, (zzud)paramObject);
      return;
    }
    zzbuf.b(paramInt, (zzwt)paramObject);
  }
  
  public final void get(int paramInt1, int paramInt2)
    throws IOException
  {
    zzbuf.cast(paramInt1, paramInt2);
  }
  
  public final void getInputStream(int paramInt, List paramList, boolean paramBoolean)
    throws IOException
  {
    int i = 0;
    int j = 0;
    if (paramBoolean)
    {
      zzbuf.next(paramInt, 2);
      paramInt = 0;
      i = 0;
      while (paramInt < paramList.size())
      {
        i += zzut.zzaz(((Long)paramList.get(paramInt)).longValue());
        paramInt += 1;
      }
      zzbuf.zzay(i);
      paramInt = j;
      while (paramInt < paramList.size())
      {
        zzbuf.zzav(((Long)paramList.get(paramInt)).longValue());
        paramInt += 1;
      }
      return;
    }
    while (i < paramList.size())
    {
      zzbuf.next(paramInt, ((Long)paramList.get(i)).longValue());
      i += 1;
    }
  }
  
  public final void getSerializedSize(int paramInt, List paramList, boolean paramBoolean)
    throws IOException
  {
    int i = 0;
    int j = 0;
    if (paramBoolean)
    {
      zzbuf.next(paramInt, 2);
      paramInt = 0;
      i = 0;
      while (paramInt < paramList.size())
      {
        i += zzut.zzay(((Long)paramList.get(paramInt)).longValue());
        paramInt += 1;
      }
      zzbuf.zzay(i);
      paramInt = j;
      while (paramInt < paramList.size())
      {
        zzbuf.zzav(((Long)paramList.get(paramInt)).longValue());
        paramInt += 1;
      }
      return;
    }
    while (i < paramList.size())
    {
      zzbuf.next(paramInt, ((Long)paramList.get(i)).longValue());
      i += 1;
    }
  }
  
  public final void invoke(int paramInt, Object paramObject, zzxj paramZzxj)
    throws IOException
  {
    zzbuf.f(paramInt, (zzwt)paramObject, paramZzxj);
  }
  
  public final void processWays(int paramInt, List paramList, boolean paramBoolean)
    throws IOException
  {
    int i = 0;
    int j = 0;
    if (paramBoolean)
    {
      zzbuf.next(paramInt, 2);
      paramInt = 0;
      i = 0;
      while (paramInt < paramList.size())
      {
        i += zzut.zzba(((Long)paramList.get(paramInt)).longValue());
        paramInt += 1;
      }
      zzbuf.zzay(i);
      paramInt = j;
      while (paramInt < paramList.size())
      {
        zzbuf.zzaw(((Long)paramList.get(paramInt)).longValue());
        paramInt += 1;
      }
      return;
    }
    while (i < paramList.size())
    {
      zzbuf.add(paramInt, ((Long)paramList.get(i)).longValue());
      i += 1;
    }
  }
  
  public final void put(int paramInt1, int paramInt2)
    throws IOException
  {
    zzbuf.put(paramInt1, paramInt2);
  }
  
  public final void remap(int paramInt, List paramList, boolean paramBoolean)
    throws IOException
  {
    int i = 0;
    int j = 0;
    if (paramBoolean)
    {
      zzbuf.next(paramInt, 2);
      paramInt = 0;
      i = 0;
      while (paramInt < paramList.size())
      {
        i += zzut.zzbc(((Integer)paramList.get(paramInt)).intValue());
        paramInt += 1;
      }
      zzbuf.zzay(i);
      paramInt = j;
      while (paramInt < paramList.size())
      {
        zzbuf.zzax(((Integer)paramList.get(paramInt)).intValue());
        paramInt += 1;
      }
      return;
    }
    while (i < paramList.size())
    {
      zzbuf.remap(paramInt, ((Integer)paramList.get(i)).intValue());
      i += 1;
    }
  }
  
  public final void scan(int paramInt, List paramList, boolean paramBoolean)
    throws IOException
  {
    int i = 0;
    int j = 0;
    if (paramBoolean)
    {
      zzbuf.next(paramInt, 2);
      paramInt = 0;
      i = 0;
      while (paramInt < paramList.size())
      {
        i += zzut.insert(((Boolean)paramList.get(paramInt)).booleanValue());
        paramInt += 1;
      }
      zzbuf.zzay(i);
      paramInt = j;
      while (paramInt < paramList.size())
      {
        zzbuf.set(((Boolean)paramList.get(paramInt)).booleanValue());
        paramInt += 1;
      }
      return;
    }
    while (i < paramList.size())
    {
      zzbuf.process(paramInt, ((Boolean)paramList.get(i)).booleanValue());
      i += 1;
    }
  }
  
  public final void send(int paramInt, List paramList, boolean paramBoolean)
    throws IOException
  {
    int i = 0;
    int j = 0;
    if (paramBoolean)
    {
      zzbuf.next(paramInt, 2);
      paramInt = 0;
      i = 0;
      while (paramInt < paramList.size())
      {
        i += zzut.zzbh(((Integer)paramList.get(paramInt)).intValue());
        paramInt += 1;
      }
      zzbuf.zzay(i);
      paramInt = j;
      while (paramInt < paramList.size())
      {
        zzbuf.zzax(((Integer)paramList.get(paramInt)).intValue());
        paramInt += 1;
      }
      return;
    }
    while (i < paramList.size())
    {
      zzbuf.remap(paramInt, ((Integer)paramList.get(i)).intValue());
      i += 1;
    }
  }
  
  public final void setDetails(int paramInt, List paramList, boolean paramBoolean)
    throws IOException
  {
    int i = 0;
    int j = 0;
    if (paramBoolean)
    {
      zzbuf.next(paramInt, 2);
      paramInt = 0;
      i = 0;
      while (paramInt < paramList.size())
      {
        i += zzut.zzbc(((Long)paramList.get(paramInt)).longValue());
        paramInt += 1;
      }
      zzbuf.zzay(i);
      paramInt = j;
      while (paramInt < paramList.size())
      {
        zzbuf.zzax(((Long)paramList.get(paramInt)).longValue());
        paramInt += 1;
      }
      return;
    }
    while (i < paramList.size())
    {
      zzbuf.append(paramInt, ((Long)paramList.get(i)).longValue());
      i += 1;
    }
  }
  
  public final void toArray(int paramInt, List paramList, boolean paramBoolean)
    throws IOException
  {
    int i = 0;
    int j = 0;
    if (paramBoolean)
    {
      zzbuf.next(paramInt, 2);
      paramInt = 0;
      i = 0;
      while (paramInt < paramList.size())
      {
        i += zzut.write(((Float)paramList.get(paramInt)).floatValue());
        paramInt += 1;
      }
      zzbuf.zzay(i);
      paramInt = j;
      while (paramInt < paramList.size())
      {
        zzbuf.set(((Float)paramList.get(paramInt)).floatValue());
        paramInt += 1;
      }
      return;
    }
    while (i < paramList.size())
    {
      zzbuf.inc(paramInt, ((Float)paramList.get(i)).floatValue());
      i += 1;
    }
  }
  
  public final void toString(int paramInt, long paramLong)
    throws IOException
  {
    zzbuf.next(paramInt, paramLong);
  }
  
  public final void trim(int paramInt, List paramList, boolean paramBoolean)
    throws IOException
  {
    int i = 0;
    int j = 0;
    if (paramBoolean)
    {
      zzbuf.next(paramInt, 2);
      paramInt = 0;
      i = 0;
      while (paramInt < paramList.size())
      {
        i += zzut.zzbe(((Integer)paramList.get(paramInt)).intValue());
        paramInt += 1;
      }
      zzbuf.zzay(i);
      paramInt = j;
      while (paramInt < paramList.size())
      {
        zzbuf.zzaz(((Integer)paramList.get(paramInt)).intValue());
        paramInt += 1;
      }
      return;
    }
    while (i < paramList.size())
    {
      zzbuf.cast(paramInt, ((Integer)paramList.get(i)).intValue());
      i += 1;
    }
  }
  
  public final void valueOf(int paramInt1, int paramInt2)
    throws IOException
  {
    zzbuf.put(paramInt1, paramInt2);
  }
  
  public final void valueOf(int paramInt, long paramLong)
    throws IOException
  {
    zzbuf.append(paramInt, paramLong);
  }
  
  public final void visitFieldInsn(int paramInt, long paramLong)
    throws IOException
  {
    zzbuf.add(paramInt, paramLong);
  }
  
  public final void visitIincInsn(int paramInt1, int paramInt2)
    throws IOException
  {
    zzbuf.remap(paramInt1, paramInt2);
  }
  
  public final void visitLocalVariable(int paramInt, long paramLong)
    throws IOException
  {
    zzbuf.append(paramInt, paramLong);
  }
  
  public final void visitVarInsn(int paramInt1, int paramInt2)
    throws IOException
  {
    zzbuf.remap(paramInt1, paramInt2);
  }
  
  public final void write(int paramInt1, int paramInt2)
    throws IOException
  {
    zzbuf.append(paramInt1, paramInt2);
  }
  
  public final void writeTo(int paramInt, List paramList)
    throws IOException
  {
    boolean bool = paramList instanceof zzwc;
    int i = 0;
    int j = 0;
    if (bool)
    {
      zzwc localZzwc = (zzwc)paramList;
      i = j;
      while (i < paramList.size())
      {
        Object localObject = localZzwc.getRaw(i);
        if ((localObject instanceof String)) {
          zzbuf.writeValue(paramInt, (String)localObject);
        } else {
          zzbuf.c(paramInt, (zzud)localObject);
        }
        i += 1;
      }
      return;
    }
    while (i < paramList.size())
    {
      zzbuf.writeValue(paramInt, (String)paramList.get(i));
      i += 1;
    }
  }
  
  public final void writeValue(int paramInt, String paramString)
    throws IOException
  {
    zzbuf.writeValue(paramInt, paramString);
  }
  
  public final void zzbk(int paramInt)
    throws IOException
  {
    zzbuf.next(paramInt, 3);
  }
  
  public final void zzbl(int paramInt)
    throws IOException
  {
    zzbuf.next(paramInt, 4);
  }
  
  public final int zzvj()
  {
    return zzvm.zze.zzbze;
  }
}
