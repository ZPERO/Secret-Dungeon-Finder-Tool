package com.google.firebase.components;

import com.google.android.android.common.internal.Preconditions;
import com.google.firebase.events.Event;
import com.google.firebase.events.EventHandler;
import com.google.firebase.events.Publisher;
import com.google.firebase.events.Subscriber;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

class EventBus
  implements Subscriber, Publisher
{
  private final Executor defaultExecutor;
  private final Map<Class<?>, ConcurrentHashMap<EventHandler<Object>, Executor>> handlerMap = new HashMap();
  private Queue<Event<?>> pendingEvents = new ArrayDeque();
  
  EventBus(Executor paramExecutor)
  {
    defaultExecutor = paramExecutor;
  }
  
  private Set getHandlers(Event paramEvent)
  {
    try
    {
      paramEvent = (Map)handlerMap.get(paramEvent.getType());
      if (paramEvent == null) {
        paramEvent = Collections.emptySet();
      } else {
        paramEvent = paramEvent.entrySet();
      }
      return paramEvent;
    }
    catch (Throwable paramEvent)
    {
      throw paramEvent;
    }
  }
  
  void enablePublishingAndFlushPending()
  {
    for (;;)
    {
      try
      {
        if (pendingEvents == null) {
          break label70;
        }
        Object localObject1 = pendingEvents;
        pendingEvents = null;
        if (localObject1 != null)
        {
          localObject1 = ((Queue)localObject1).iterator();
          if (((Iterator)localObject1).hasNext())
          {
            publish((Event)((Iterator)localObject1).next());
            continue;
          }
        }
        else
        {
          return;
        }
      }
      catch (Throwable localThrowable)
      {
        throw localThrowable;
      }
      return;
      label70:
      Object localObject2 = null;
    }
  }
  
  public void publish(Event paramEvent)
  {
    Preconditions.checkNotNull(paramEvent);
    try
    {
      if (pendingEvents != null)
      {
        pendingEvents.add(paramEvent);
        return;
      }
      Iterator localIterator = getHandlers(paramEvent).iterator();
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        ((Executor)localEntry.getValue()).execute(EventBus..Lambda.1.lambdaFactory$(localEntry, paramEvent));
      }
      return;
    }
    catch (Throwable paramEvent)
    {
      throw paramEvent;
    }
  }
  
  public void subscribe(Class paramClass, EventHandler paramEventHandler)
  {
    subscribe(paramClass, defaultExecutor, paramEventHandler);
  }
  
  public void subscribe(Class paramClass, Executor paramExecutor, EventHandler paramEventHandler)
  {
    try
    {
      Preconditions.checkNotNull(paramClass);
      Preconditions.checkNotNull(paramEventHandler);
      Preconditions.checkNotNull(paramExecutor);
      if (!handlerMap.containsKey(paramClass)) {
        handlerMap.put(paramClass, new ConcurrentHashMap());
      }
      ((ConcurrentHashMap)handlerMap.get(paramClass)).put(paramEventHandler, paramExecutor);
      return;
    }
    catch (Throwable paramClass)
    {
      throw paramClass;
    }
  }
  
  public void unsubscribe(Class paramClass, EventHandler paramEventHandler)
  {
    try
    {
      Preconditions.checkNotNull(paramClass);
      Preconditions.checkNotNull(paramEventHandler);
      boolean bool = handlerMap.containsKey(paramClass);
      if (!bool) {
        return;
      }
      ConcurrentHashMap localConcurrentHashMap = (ConcurrentHashMap)handlerMap.get(paramClass);
      localConcurrentHashMap.remove(paramEventHandler);
      if (localConcurrentHashMap.isEmpty()) {
        handlerMap.remove(paramClass);
      }
      return;
    }
    catch (Throwable paramClass)
    {
      throw paramClass;
    }
  }
}
