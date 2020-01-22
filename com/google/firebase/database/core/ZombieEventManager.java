package com.google.firebase.database.core;

import com.google.firebase.database.core.view.QuerySpec;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class ZombieEventManager
  implements EventRegistrationZombieListener
{
  private static ZombieEventManager defaultInstance = new ZombieEventManager();
  final HashMap<EventRegistration, List<EventRegistration>> globalEventRegistrations = new HashMap();
  
  private ZombieEventManager() {}
  
  public static ZombieEventManager getInstance()
  {
    return defaultInstance;
  }
  
  private void unRecordEventRegistration(EventRegistration paramEventRegistration)
  {
    HashMap localHashMap = globalEventRegistrations;
    for (;;)
    {
      int i;
      try
      {
        Object localObject = (List)globalEventRegistrations.get(paramEventRegistration);
        int j = 0;
        if (localObject != null)
        {
          i = 0;
          if (i < ((List)localObject).size())
          {
            if (((List)localObject).get(i) != paramEventRegistration) {
              break label200;
            }
            ((List)localObject).remove(i);
          }
          if (((List)localObject).isEmpty()) {
            globalEventRegistrations.remove(paramEventRegistration);
          }
        }
        if (!paramEventRegistration.getQuerySpec().isDefault())
        {
          localObject = paramEventRegistration.clone(QuerySpec.defaultQueryAtPath(paramEventRegistration.getQuerySpec().getPath()));
          List localList = (List)globalEventRegistrations.get(localObject);
          if (localList != null)
          {
            i = j;
            if (i < localList.size())
            {
              if (localList.get(i) != paramEventRegistration) {
                break label207;
              }
              localList.remove(i);
            }
            if (localList.isEmpty()) {
              globalEventRegistrations.remove(localObject);
            }
          }
        }
        return;
      }
      catch (Throwable paramEventRegistration)
      {
        throw paramEventRegistration;
      }
      label200:
      i += 1;
      continue;
      label207:
      i += 1;
    }
  }
  
  public void onZombied(EventRegistration paramEventRegistration)
  {
    unRecordEventRegistration(paramEventRegistration);
  }
  
  public void recordEventRegistration(EventRegistration paramEventRegistration)
  {
    throw new Runtime("d2j fail translate: java.lang.RuntimeException: fail exe a5 = a4\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.exec(BaseAnalyze.java:92)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.exec(BaseAnalyze.java:1)\n\tat com.googlecode.dex2jar.ir.ts.Cfg.dfs(Cfg.java:255)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.analyze0(BaseAnalyze.java:75)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.analyze(BaseAnalyze.java:69)\n\tat com.googlecode.dex2jar.ir.ts.UnSSATransformer.transform(UnSSATransformer.java:274)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:163)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\nCaused by: java.lang.NullPointerException\n");
  }
  
  public void zombifyForRemove(EventRegistration paramEventRegistration)
  {
    HashMap localHashMap = globalEventRegistrations;
    for (;;)
    {
      int i;
      try
      {
        List localList = (List)globalEventRegistrations.get(paramEventRegistration);
        if ((localList != null) && (!localList.isEmpty())) {
          if (paramEventRegistration.getQuerySpec().isDefault())
          {
            paramEventRegistration = new HashSet();
            i = localList.size() - 1;
            if (i >= 0)
            {
              EventRegistration localEventRegistration = (EventRegistration)localList.get(i);
              if (paramEventRegistration.contains(localEventRegistration.getQuerySpec())) {
                break label135;
              }
              paramEventRegistration.add(localEventRegistration.getQuerySpec());
              localEventRegistration.zombify();
              break label135;
            }
          }
          else
          {
            ((EventRegistration)localList.get(0)).zombify();
          }
        }
        return;
      }
      catch (Throwable paramEventRegistration)
      {
        throw paramEventRegistration;
      }
      label135:
      i -= 1;
    }
  }
}
