package com.google.firebase;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import androidx.collection.ArrayMap;
import androidx.core.content.ContextCompat;
import com.google.android.android.common.aimsicd.internal.BackgroundDetector;
import com.google.android.android.common.aimsicd.internal.BackgroundDetector.BackgroundStateChangeListener;
import com.google.android.android.common.internal.Objects;
import com.google.android.android.common.internal.Objects.ToStringHelper;
import com.google.android.android.common.internal.Preconditions;
import com.google.android.android.common.util.Base64Utils;
import com.google.android.android.common.util.PlatformVersion;
import com.google.android.android.common.util.ProcessUtils;
import com.google.android.android.tasks.Task;
import com.google.android.android.tasks.Tasks;
import com.google.firebase.components.Component;
import com.google.firebase.components.ComponentDiscovery;
import com.google.firebase.components.ComponentRuntime;
import com.google.firebase.events.Event;
import com.google.firebase.events.Publisher;
import com.google.firebase.internal.DefaultIdTokenListenersCountChangedListener;
import com.google.firebase.internal.InternalTokenProvider;
import com.google.firebase.internal.InternalTokenResult;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class FirebaseApp
{
  private static final List<String> API_INITIALIZERS = Arrays.asList(new String[] { "com.google.firebase.auth.FirebaseAuth", "com.google.firebase.iid.FirebaseInstanceId" });
  private static final String AUTH_CLASSNAME = "com.google.firebase.auth.FirebaseAuth";
  private static final Set<String> CORE_CLASSES;
  private static final String CRASH_CLASSNAME = "com.google.firebase.crash.FirebaseCrash";
  static final String DATA_COLLECTION_DEFAULT_ENABLED = "firebase_data_collection_default_enabled";
  private static final List<String> DEFAULT_APP_API_INITITALIZERS = Collections.singletonList("com.google.firebase.crash.FirebaseCrash");
  public static final String DEFAULT_APP_NAME = "[DEFAULT]";
  private static final List<String> DEFAULT_CONTEXT_API_INITITALIZERS = Arrays.asList(new String[] { "com.google.android.gms.measurement.AppMeasurement" });
  private static final List<String> DIRECT_BOOT_COMPATIBLE_API_INITIALIZERS = Arrays.asList(new String[0]);
  static final String FIREBASE_APP_PREFS = "com.google.firebase.common.prefs";
  private static final String IID_CLASSNAME = "com.google.firebase.iid.FirebaseInstanceId";
  static final Map<String, FirebaseApp> INSTANCES = new ArrayMap();
  private static final Object LOCK;
  private static final String LOG_TAG = "FirebaseApp";
  private static final String MEASUREMENT_CLASSNAME = "com.google.android.gms.measurement.AppMeasurement";
  private static final Executor UI_EXECUTOR;
  private final Context applicationContext;
  private final AtomicBoolean automaticResourceManagementEnabled = new AtomicBoolean(false);
  private final List<BackgroundStateChangeListener> backgroundStateChangeListeners = new CopyOnWriteArrayList();
  private final ComponentRuntime componentRuntime;
  private final AtomicBoolean dataCollectionDefaultEnabled;
  private final AtomicBoolean deleted = new AtomicBoolean();
  private final List<IdTokenListener> idTokenListeners = new CopyOnWriteArrayList();
  private IdTokenListenersCountChangedListener idTokenListenersCountChangedListener;
  private final List<FirebaseAppLifecycleListener> lifecycleListeners = new CopyOnWriteArrayList();
  private final String name;
  private final FirebaseOptions options;
  private final Publisher publisher;
  private final SharedPreferences sharedPreferences;
  private InternalTokenProvider tokenProvider;
  
  static
  {
    CORE_CLASSES = Collections.emptySet();
    LOCK = new Object();
    UI_EXECUTOR = new UiExecutor(null);
  }
  
  protected FirebaseApp(Context paramContext, String paramString, FirebaseOptions paramFirebaseOptions)
  {
    applicationContext = ((Context)Preconditions.checkNotNull(paramContext));
    name = Preconditions.checkNotEmpty(paramString);
    options = ((FirebaseOptions)Preconditions.checkNotNull(paramFirebaseOptions));
    idTokenListenersCountChangedListener = new DefaultIdTokenListenersCountChangedListener();
    sharedPreferences = paramContext.getSharedPreferences("com.google.firebase.common.prefs", 0);
    dataCollectionDefaultEnabled = new AtomicBoolean(readAutoDataCollectionEnabled());
    paramString = ComponentDiscovery.forContext(paramContext).discover();
    componentRuntime = new ComponentRuntime(UI_EXECUTOR, paramString, new Component[] { Component.getView(paramContext, Context.class, new Class[0]), Component.getView(this, FirebaseApp.class, new Class[0]), Component.getView(paramFirebaseOptions, FirebaseOptions.class, new Class[0]) });
    publisher = ((Publisher)componentRuntime.getValue(Publisher.class));
  }
  
  private void checkNotDeleted()
  {
    Preconditions.checkState(deleted.get() ^ true, "FirebaseApp was deleted");
  }
  
  public static void clearInstancesForTest()
  {
    Object localObject = LOCK;
    try
    {
      INSTANCES.clear();
      return;
    }
    catch (Throwable localThrowable)
    {
      throw localThrowable;
    }
  }
  
  private static List getAllAppNames()
  {
    ArrayList localArrayList = new ArrayList();
    Object localObject = LOCK;
    try
    {
      Iterator localIterator = INSTANCES.values().iterator();
      while (localIterator.hasNext()) {
        localArrayList.add(((FirebaseApp)localIterator.next()).getName());
      }
      Collections.sort(localArrayList);
      return localArrayList;
    }
    catch (Throwable localThrowable)
    {
      throw localThrowable;
    }
  }
  
  public static List getApps(Context paramContext)
  {
    paramContext = LOCK;
    try
    {
      ArrayList localArrayList = new ArrayList(INSTANCES.values());
      return localArrayList;
    }
    catch (Throwable localThrowable)
    {
      throw localThrowable;
    }
  }
  
  public static FirebaseApp getInstance()
  {
    Object localObject1 = LOCK;
    try
    {
      Object localObject2 = (FirebaseApp)INSTANCES.get("[DEFAULT]");
      if (localObject2 != null) {
        return localObject2;
      }
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("Default FirebaseApp is not initialized in this process ");
      ((StringBuilder)localObject2).append(ProcessUtils.getMyProcessName());
      ((StringBuilder)localObject2).append(". Make sure to call FirebaseApp.initializeApp(Context) first.");
      throw new IllegalStateException(((StringBuilder)localObject2).toString());
    }
    catch (Throwable localThrowable)
    {
      throw localThrowable;
    }
  }
  
  public static FirebaseApp getInstance(String paramString)
  {
    Object localObject2 = LOCK;
    try
    {
      Object localObject1 = (FirebaseApp)INSTANCES.get(normalize(paramString));
      if (localObject1 != null) {
        return localObject1;
      }
      localObject1 = getAllAppNames();
      if (((List)localObject1).isEmpty())
      {
        localObject1 = "";
      }
      else
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("Available app names: ");
        localStringBuilder.append(TextUtils.join(", ", (Iterable)localObject1));
        localObject1 = localStringBuilder.toString();
      }
      throw new IllegalStateException(String.format("FirebaseApp with name %s doesn't exist. %s", new Object[] { paramString, localObject1 }));
    }
    catch (Throwable paramString)
    {
      throw paramString;
    }
  }
  
  public static String getPersistenceKey(String paramString, FirebaseOptions paramFirebaseOptions)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(Base64Utils.encodeUrlSafeNoPadding(paramString.getBytes(Charset.defaultCharset())));
    localStringBuilder.append("+");
    localStringBuilder.append(Base64Utils.encodeUrlSafeNoPadding(paramFirebaseOptions.getApplicationId().getBytes(Charset.defaultCharset())));
    return localStringBuilder.toString();
  }
  
  private void initializeAllApis()
  {
    boolean bool = ContextCompat.isDeviceProtectedStorage(applicationContext);
    if (bool) {
      UserUnlockReceiver.ensureReceiverRegistered(applicationContext);
    } else {
      componentRuntime.initializeEagerComponents(isDefaultApp());
    }
    initializeApis(FirebaseApp.class, this, API_INITIALIZERS, bool);
    if (isDefaultApp())
    {
      initializeApis(FirebaseApp.class, this, DEFAULT_APP_API_INITITALIZERS, bool);
      initializeApis(Context.class, applicationContext, DEFAULT_CONTEXT_API_INITITALIZERS, bool);
    }
  }
  
  private void initializeApis(Class paramClass, Object paramObject, Iterable paramIterable, boolean paramBoolean)
  {
    Iterator localIterator = paramIterable.iterator();
    for (;;)
    {
      if (localIterator.hasNext())
      {
        paramIterable = (String)localIterator.next();
        Object localObject;
        if (paramBoolean) {
          localObject = DIRECT_BOOT_COMPATIBLE_API_INITIALIZERS;
        }
        try
        {
          boolean bool = ((List)localObject).contains(paramIterable);
          if (bool)
          {
            localObject = Class.forName(paramIterable);
            localObject = ((Class)localObject).getMethod("getInstance", new Class[] { paramClass });
            int i = ((Method)localObject).getModifiers();
            bool = Modifier.isPublic(i);
            if (bool)
            {
              bool = Modifier.isStatic(i);
              if (bool) {
                ((Method)localObject).invoke(null, new Object[] { paramObject });
              }
            }
          }
        }
        catch (IllegalAccessException localIllegalAccessException)
        {
          StringBuilder localStringBuilder2 = new StringBuilder();
          localStringBuilder2.append("Failed to initialize ");
          localStringBuilder2.append(paramIterable);
          Log.wtf("FirebaseApp", localStringBuilder2.toString(), localIllegalAccessException);
        }
        catch (InvocationTargetException paramIterable)
        {
          Log.wtf("FirebaseApp", "Firebase API initialization failure.", paramIterable);
          continue;
          paramClass = new StringBuilder();
          paramClass.append(paramIterable);
          paramClass.append("#getInstance has been removed by Proguard. Add keep rule to prevent it.");
          throw new IllegalStateException(paramClass.toString());
          if (!CORE_CLASSES.contains(paramIterable))
          {
            StringBuilder localStringBuilder1 = new StringBuilder();
            localStringBuilder1.append(paramIterable);
            localStringBuilder1.append(" is not linked. Skipping initialization.");
            Log.d("FirebaseApp", localStringBuilder1.toString());
          }
          else
          {
            paramClass = new StringBuilder();
            paramClass.append(paramIterable);
            paramClass.append(" is missing, but is required. Check if it has been removed by Proguard.");
            throw new IllegalStateException(paramClass.toString());
          }
        }
        catch (ClassNotFoundException localClassNotFoundException)
        {
          for (;;) {}
        }
        catch (NoSuchMethodException paramClass)
        {
          for (;;) {}
        }
      }
    }
  }
  
  public static FirebaseApp initializeApp(Context paramContext)
  {
    Object localObject = LOCK;
    try
    {
      if (INSTANCES.containsKey("[DEFAULT]"))
      {
        paramContext = getInstance();
        return paramContext;
      }
      FirebaseOptions localFirebaseOptions = FirebaseOptions.fromResource(paramContext);
      if (localFirebaseOptions == null)
      {
        Log.d("FirebaseApp", "Default FirebaseApp failed to initialize because no default options were found. This usually means that com.google.gms:google-services was not applied to your gradle project.");
        return null;
      }
      paramContext = initializeApp(paramContext, localFirebaseOptions);
      return paramContext;
    }
    catch (Throwable paramContext)
    {
      throw paramContext;
    }
  }
  
  public static FirebaseApp initializeApp(Context paramContext, FirebaseOptions paramFirebaseOptions)
  {
    return initializeApp(paramContext, paramFirebaseOptions, "[DEFAULT]");
  }
  
  public static FirebaseApp initializeApp(Context paramContext, FirebaseOptions paramFirebaseOptions, String paramString)
  {
    GlobalBackgroundStateListener.ensureBackgroundStateListenerRegistered(paramContext);
    paramString = normalize(paramString);
    if (paramContext.getApplicationContext() != null) {
      paramContext = paramContext.getApplicationContext();
    }
    Object localObject = LOCK;
    for (;;)
    {
      try
      {
        if (!INSTANCES.containsKey(paramString))
        {
          bool = true;
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("FirebaseApp name ");
          localStringBuilder.append(paramString);
          localStringBuilder.append(" already exists!");
          Preconditions.checkState(bool, localStringBuilder.toString());
          Preconditions.checkNotNull(paramContext, "Application context cannot be null.");
          paramContext = new FirebaseApp(paramContext, paramString, paramFirebaseOptions);
          INSTANCES.put(paramString, paramContext);
          paramContext.initializeAllApis();
          return paramContext;
        }
      }
      catch (Throwable paramContext)
      {
        throw paramContext;
      }
      boolean bool = false;
    }
  }
  
  private static String normalize(String paramString)
  {
    return paramString.trim();
  }
  
  private void notifyBackgroundStateChangeListeners(boolean paramBoolean)
  {
    Log.d("FirebaseApp", "Notifying background state change listeners.");
    Iterator localIterator = backgroundStateChangeListeners.iterator();
    while (localIterator.hasNext()) {
      ((BackgroundStateChangeListener)localIterator.next()).onBackgroundStateChanged(paramBoolean);
    }
  }
  
  private void notifyOnAppDeleted()
  {
    Iterator localIterator = lifecycleListeners.iterator();
    while (localIterator.hasNext()) {
      ((FirebaseAppLifecycleListener)localIterator.next()).onDeleted(name, options);
    }
  }
  
  private boolean readAutoDataCollectionEnabled()
  {
    if (sharedPreferences.contains("firebase_data_collection_default_enabled")) {
      return sharedPreferences.getBoolean("firebase_data_collection_default_enabled", true);
    }
    Object localObject1 = applicationContext;
    try
    {
      localObject1 = ((Context)localObject1).getPackageManager();
      if (localObject1 != null)
      {
        Object localObject2 = applicationContext;
        localObject1 = ((PackageManager)localObject1).getApplicationInfo(((Context)localObject2).getPackageName(), 128);
        if ((localObject1 != null) && (metaData != null))
        {
          localObject2 = metaData;
          boolean bool = ((Bundle)localObject2).containsKey("firebase_data_collection_default_enabled");
          if (bool)
          {
            localObject1 = metaData;
            bool = ((Bundle)localObject1).getBoolean("firebase_data_collection_default_enabled");
            return bool;
          }
        }
      }
      else
      {
        return true;
      }
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException) {}
    return true;
  }
  
  public void addBackgroundStateChangeListener(BackgroundStateChangeListener paramBackgroundStateChangeListener)
  {
    checkNotDeleted();
    if ((automaticResourceManagementEnabled.get()) && (BackgroundDetector.getInstance().isInBackground())) {
      paramBackgroundStateChangeListener.onBackgroundStateChanged(true);
    }
    backgroundStateChangeListeners.add(paramBackgroundStateChangeListener);
  }
  
  public void addIdTokenListener(IdTokenListener paramIdTokenListener)
  {
    checkNotDeleted();
    Preconditions.checkNotNull(paramIdTokenListener);
    idTokenListeners.add(paramIdTokenListener);
    idTokenListenersCountChangedListener.onListenerCountChanged(idTokenListeners.size());
  }
  
  public void addLifecycleEventListener(FirebaseAppLifecycleListener paramFirebaseAppLifecycleListener)
  {
    checkNotDeleted();
    Preconditions.checkNotNull(paramFirebaseAppLifecycleListener);
    lifecycleListeners.add(paramFirebaseAppLifecycleListener);
  }
  
  public void delete()
  {
    if (!deleted.compareAndSet(false, true)) {
      return;
    }
    Object localObject = LOCK;
    try
    {
      INSTANCES.remove(name);
      notifyOnAppDeleted();
      return;
    }
    catch (Throwable localThrowable)
    {
      throw localThrowable;
    }
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof FirebaseApp)) {
      return false;
    }
    return name.equals(((FirebaseApp)paramObject).getName());
  }
  
  public Object get(Class paramClass)
  {
    checkNotDeleted();
    return componentRuntime.getValue(paramClass);
  }
  
  public Context getApplicationContext()
  {
    checkNotDeleted();
    return applicationContext;
  }
  
  public List getListeners()
  {
    checkNotDeleted();
    return idTokenListeners;
  }
  
  public String getName()
  {
    checkNotDeleted();
    return name;
  }
  
  public FirebaseOptions getOptions()
  {
    checkNotDeleted();
    return options;
  }
  
  public String getPersistenceKey()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(Base64Utils.encodeUrlSafeNoPadding(getName().getBytes(Charset.defaultCharset())));
    localStringBuilder.append("+");
    localStringBuilder.append(Base64Utils.encodeUrlSafeNoPadding(getOptions().getApplicationId().getBytes(Charset.defaultCharset())));
    return localStringBuilder.toString();
  }
  
  public Task getToken(boolean paramBoolean)
  {
    checkNotDeleted();
    InternalTokenProvider localInternalTokenProvider = tokenProvider;
    if (localInternalTokenProvider == null) {
      return Tasks.forException(new FirebaseApiNotAvailableException("firebase-auth is not linked, please fall back to unauthenticated mode."));
    }
    return localInternalTokenProvider.getAccessToken(paramBoolean);
  }
  
  public String getUid()
    throws FirebaseApiNotAvailableException
  {
    checkNotDeleted();
    InternalTokenProvider localInternalTokenProvider = tokenProvider;
    if (localInternalTokenProvider != null) {
      return localInternalTokenProvider.getUid();
    }
    throw new FirebaseApiNotAvailableException("firebase-auth is not linked, please fall back to unauthenticated mode.");
  }
  
  public int hashCode()
  {
    return name.hashCode();
  }
  
  public boolean isDataCollectionDefaultEnabled()
  {
    checkNotDeleted();
    return dataCollectionDefaultEnabled.get();
  }
  
  public boolean isDefaultApp()
  {
    return "[DEFAULT]".equals(getName());
  }
  
  public void notifyIdTokenListeners(InternalTokenResult paramInternalTokenResult)
  {
    Log.d("FirebaseApp", "Notifying auth state listeners.");
    Iterator localIterator = idTokenListeners.iterator();
    int i = 0;
    while (localIterator.hasNext())
    {
      ((IdTokenListener)localIterator.next()).onIdTokenChanged(paramInternalTokenResult);
      i += 1;
    }
    Log.d("FirebaseApp", String.format("Notified %d auth state listeners.", new Object[] { Integer.valueOf(i) }));
  }
  
  public void removeBackgroundStateChangeListener(BackgroundStateChangeListener paramBackgroundStateChangeListener)
  {
    checkNotDeleted();
    backgroundStateChangeListeners.remove(paramBackgroundStateChangeListener);
  }
  
  public void removeIdTokenListener(IdTokenListener paramIdTokenListener)
  {
    checkNotDeleted();
    Preconditions.checkNotNull(paramIdTokenListener);
    idTokenListeners.remove(paramIdTokenListener);
    idTokenListenersCountChangedListener.onListenerCountChanged(idTokenListeners.size());
  }
  
  public void removeLifecycleEventListener(FirebaseAppLifecycleListener paramFirebaseAppLifecycleListener)
  {
    checkNotDeleted();
    Preconditions.checkNotNull(paramFirebaseAppLifecycleListener);
    lifecycleListeners.remove(paramFirebaseAppLifecycleListener);
  }
  
  public void setAutomaticResourceManagementEnabled(boolean paramBoolean)
  {
    checkNotDeleted();
    if (automaticResourceManagementEnabled.compareAndSet(paramBoolean ^ true, paramBoolean))
    {
      boolean bool = BackgroundDetector.getInstance().isInBackground();
      if ((paramBoolean) && (bool))
      {
        notifyBackgroundStateChangeListeners(true);
        return;
      }
      if ((!paramBoolean) && (bool)) {
        notifyBackgroundStateChangeListeners(false);
      }
    }
  }
  
  public void setDataCollectionDefaultEnabled(boolean paramBoolean)
  {
    checkNotDeleted();
    if (dataCollectionDefaultEnabled.compareAndSet(paramBoolean ^ true, paramBoolean))
    {
      sharedPreferences.edit().putBoolean("firebase_data_collection_default_enabled", paramBoolean).commit();
      publisher.publish(new Event(DataCollectionDefaultChange.class, new DataCollectionDefaultChange(paramBoolean)));
    }
  }
  
  public void setIdTokenListenersCountChangedListener(IdTokenListenersCountChangedListener paramIdTokenListenersCountChangedListener)
  {
    idTokenListenersCountChangedListener = ((IdTokenListenersCountChangedListener)Preconditions.checkNotNull(paramIdTokenListenersCountChangedListener));
    idTokenListenersCountChangedListener.onListenerCountChanged(idTokenListeners.size());
  }
  
  public void setTokenProvider(InternalTokenProvider paramInternalTokenProvider)
  {
    tokenProvider = ((InternalTokenProvider)Preconditions.checkNotNull(paramInternalTokenProvider));
  }
  
  public String toString()
  {
    return Objects.toStringHelper(this).add("name", name).add("options", options).toString();
  }
  
  public static abstract interface BackgroundStateChangeListener
  {
    public abstract void onBackgroundStateChanged(boolean paramBoolean);
  }
  
  private static class GlobalBackgroundStateListener
    implements BackgroundDetector.BackgroundStateChangeListener
  {
    private static AtomicReference<GlobalBackgroundStateListener> INSTANCE = new AtomicReference();
    
    private GlobalBackgroundStateListener() {}
    
    private static void ensureBackgroundStateListenerRegistered(Context paramContext)
    {
      if (PlatformVersion.isAtLeastIceCreamSandwich())
      {
        if (!(paramContext.getApplicationContext() instanceof Application)) {
          return;
        }
        paramContext = (Application)paramContext.getApplicationContext();
        if (INSTANCE.get() == null)
        {
          GlobalBackgroundStateListener localGlobalBackgroundStateListener = new GlobalBackgroundStateListener();
          if (INSTANCE.compareAndSet(null, localGlobalBackgroundStateListener))
          {
            BackgroundDetector.initialize(paramContext);
            BackgroundDetector.getInstance().addListener(localGlobalBackgroundStateListener);
          }
        }
      }
    }
    
    public void onBackgroundStateChanged(boolean paramBoolean)
    {
      Object localObject = FirebaseApp.LOCK;
      try
      {
        Iterator localIterator = new ArrayList(FirebaseApp.INSTANCES.values()).iterator();
        while (localIterator.hasNext())
        {
          FirebaseApp localFirebaseApp = (FirebaseApp)localIterator.next();
          if (automaticResourceManagementEnabled.get()) {
            localFirebaseApp.notifyBackgroundStateChangeListeners(paramBoolean);
          }
        }
        return;
      }
      catch (Throwable localThrowable)
      {
        throw localThrowable;
      }
    }
  }
  
  @Deprecated
  public static abstract interface IdTokenListener
  {
    public abstract void onIdTokenChanged(InternalTokenResult paramInternalTokenResult);
  }
  
  @Deprecated
  public static abstract interface IdTokenListenersCountChangedListener
  {
    public abstract void onListenerCountChanged(int paramInt);
  }
  
  private static class UiExecutor
    implements Executor
  {
    private static final Handler HANDLER = new Handler(Looper.getMainLooper());
    
    private UiExecutor() {}
    
    public void execute(Runnable paramRunnable)
    {
      HANDLER.post(paramRunnable);
    }
  }
  
  private static class UserUnlockReceiver
    extends BroadcastReceiver
  {
    private static AtomicReference<UserUnlockReceiver> INSTANCE = new AtomicReference();
    private final Context applicationContext;
    
    public UserUnlockReceiver(Context paramContext)
    {
      applicationContext = paramContext;
    }
    
    private static void ensureReceiverRegistered(Context paramContext)
    {
      if (INSTANCE.get() == null)
      {
        UserUnlockReceiver localUserUnlockReceiver = new UserUnlockReceiver(paramContext);
        if (INSTANCE.compareAndSet(null, localUserUnlockReceiver)) {
          paramContext.registerReceiver(localUserUnlockReceiver, new IntentFilter("android.intent.action.USER_UNLOCKED"));
        }
      }
    }
    
    public void onReceive(Context paramContext, Intent paramIntent)
    {
      paramContext = FirebaseApp.LOCK;
      try
      {
        paramIntent = FirebaseApp.INSTANCES.values().iterator();
        while (paramIntent.hasNext()) {
          ((FirebaseApp)paramIntent.next()).initializeAllApis();
        }
        unregister();
        return;
      }
      catch (Throwable paramIntent)
      {
        throw paramIntent;
      }
    }
    
    public void unregister()
    {
      applicationContext.unregisterReceiver(this);
    }
  }
}
