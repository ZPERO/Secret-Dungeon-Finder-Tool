package com.google.firebase.components;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.Log;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public final class ComponentDiscovery<T>
{
  private static final String COMPONENT_KEY_PREFIX = "com.google.firebase.components:";
  private static final String COMPONENT_SENTINEL_VALUE = "com.google.firebase.components.ComponentRegistrar";
  private static final String PAGE_KEY = "ComponentDiscovery";
  private final T context;
  private final RegistrarNameRetriever<T> retriever;
  
  ComponentDiscovery(Object paramObject, RegistrarNameRetriever paramRegistrarNameRetriever)
  {
    context = paramObject;
    retriever = paramRegistrarNameRetriever;
  }
  
  public static ComponentDiscovery forContext(Context paramContext)
  {
    return new ComponentDiscovery(paramContext, new MetadataRegistrarNameRetriever(null));
  }
  
  private static List instantiate(List paramList)
  {
    ArrayList localArrayList = new ArrayList();
    paramList = paramList.iterator();
    while (paramList.hasNext())
    {
      String str = (String)paramList.next();
      try
      {
        Object localObject = Class.forName(str);
        boolean bool = ComponentRegistrar.class.isAssignableFrom((Class)localObject);
        if (!bool)
        {
          Log.w("ComponentDiscovery", String.format("Class %s is not an instance of %s", new Object[] { str, "com.google.firebase.components.ComponentRegistrar" }));
        }
        else
        {
          localObject = ((Class)localObject).getDeclaredConstructor(new Class[0]);
          localObject = ((Constructor)localObject).newInstance(new Object[0]);
          localObject = (ComponentRegistrar)localObject;
          localArrayList.add(localObject);
        }
      }
      catch (InvocationTargetException localInvocationTargetException)
      {
        Log.w("ComponentDiscovery", String.format("Could not instantiate %s", new Object[] { str }), localInvocationTargetException);
      }
      catch (NoSuchMethodException localNoSuchMethodException)
      {
        Log.w("ComponentDiscovery", String.format("Could not instantiate %s", new Object[] { str }), localNoSuchMethodException);
      }
      catch (InstantiationException localInstantiationException)
      {
        Log.w("ComponentDiscovery", String.format("Could not instantiate %s.", new Object[] { str }), localInstantiationException);
      }
      catch (IllegalAccessException localIllegalAccessException)
      {
        Log.w("ComponentDiscovery", String.format("Could not instantiate %s.", new Object[] { str }), localIllegalAccessException);
      }
      catch (ClassNotFoundException localClassNotFoundException)
      {
        Log.w("ComponentDiscovery", String.format("Class %s is not an found.", new Object[] { str }), localClassNotFoundException);
      }
    }
    return localArrayList;
  }
  
  public List discover()
  {
    return instantiate(retriever.retrieve(context));
  }
  
  private static class MetadataRegistrarNameRetriever
    implements ComponentDiscovery.RegistrarNameRetriever<Context>
  {
    private MetadataRegistrarNameRetriever() {}
    
    private static Bundle getMetadata(Context paramContext)
    {
      try
      {
        PackageManager localPackageManager = paramContext.getPackageManager();
        if (localPackageManager == null)
        {
          Log.w("ComponentDiscovery", "Context has no PackageManager.");
          return null;
        }
        paramContext = localPackageManager.getServiceInfo(new ComponentName(paramContext, ComponentDiscoveryService.class), 128);
        if (paramContext == null)
        {
          Log.w("ComponentDiscovery", "ComponentDiscoveryService has no service info.");
          return null;
        }
        return metaData;
      }
      catch (PackageManager.NameNotFoundException paramContext)
      {
        for (;;) {}
      }
      Log.w("ComponentDiscovery", "Application info not found.");
      return null;
    }
    
    public List retrieve(Context paramContext)
    {
      paramContext = getMetadata(paramContext);
      if (paramContext == null)
      {
        Log.w("ComponentDiscovery", "Could not retrieve metadata, returning empty list of registrars.");
        return Collections.emptyList();
      }
      ArrayList localArrayList = new ArrayList();
      Iterator localIterator = paramContext.keySet().iterator();
      while (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        if (("com.google.firebase.components.ComponentRegistrar".equals(paramContext.get(str))) && (str.startsWith("com.google.firebase.components:"))) {
          localArrayList.add(str.substring(31));
        }
      }
      return localArrayList;
    }
  }
  
  static abstract interface RegistrarNameRetriever<T>
  {
    public abstract List retrieve(Object paramObject);
  }
}
