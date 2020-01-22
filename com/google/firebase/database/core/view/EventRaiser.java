package com.google.firebase.database.core.view;

import com.google.firebase.database.core.Context;
import com.google.firebase.database.core.EventTarget;
import com.google.firebase.database.logging.LogWrapper;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EventRaiser
{
  private final EventTarget eventTarget;
  private final LogWrapper logger;
  
  public EventRaiser(Context paramContext)
  {
    eventTarget = paramContext.getEventTarget();
    logger = paramContext.getLogger("EventRaiser");
  }
  
  public void raiseEvents(final List paramList)
  {
    if (logger.logsDebug())
    {
      LogWrapper localLogWrapper = logger;
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Raising ");
      localStringBuilder.append(paramList.size());
      localStringBuilder.append(" event(s)");
      localLogWrapper.debug(localStringBuilder.toString(), new Object[0]);
    }
    paramList = new ArrayList(paramList);
    eventTarget.postEvent(new Runnable()
    {
      public void run()
      {
        Iterator localIterator = paramList.iterator();
        while (localIterator.hasNext())
        {
          Event localEvent = (Event)localIterator.next();
          if (logger.logsDebug())
          {
            LogWrapper localLogWrapper = logger;
            StringBuilder localStringBuilder = new StringBuilder();
            localStringBuilder.append("Raising ");
            localStringBuilder.append(localEvent.toString());
            localLogWrapper.debug(localStringBuilder.toString(), new Object[0]);
          }
          localEvent.fire();
        }
      }
    });
  }
}
