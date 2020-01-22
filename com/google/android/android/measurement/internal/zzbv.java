package com.google.android.android.measurement.internal;

import android.os.Binder;
import android.text.TextUtils;
import com.google.android.android.common.GooglePlayServicesUtilLight;
import com.google.android.android.common.GoogleSignatureVerifier;
import com.google.android.android.common.internal.Preconditions;
import com.google.android.android.common.util.Clock;
import com.google.android.android.common.util.UidVerifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public final class zzbv
  extends zzah
{
  private final zzfa zzamz;
  private Boolean zzaql;
  private String zzaqm;
  
  public zzbv(zzfa paramZzfa)
  {
    this(paramZzfa, null);
  }
  
  private zzbv(zzfa paramZzfa, String paramString)
  {
    Preconditions.checkNotNull(paramZzfa);
    zzamz = paramZzfa;
    zzaqm = null;
  }
  
  private final void append(VideoItem paramVideoItem, boolean paramBoolean)
  {
    Preconditions.checkNotNull(paramVideoItem);
    e(packageName, false);
    zzamz.zzgm().forEach(zzafx, zzagk);
  }
  
  private final void e(String paramString, boolean paramBoolean)
  {
    if (!TextUtils.isEmpty(paramString))
    {
      Object localObject;
      if (paramBoolean) {
        if (zzaql == null) {
          localObject = zzaqm;
        }
      }
      try
      {
        paramBoolean = "com.google.android.gms".equals(localObject);
        if (!paramBoolean)
        {
          localObject = zzamz;
          paramBoolean = UidVerifier.isGooglePlayServicesUid(((zzfa)localObject).getContext(), Binder.getCallingUid());
          if (!paramBoolean)
          {
            localObject = zzamz;
            paramBoolean = GoogleSignatureVerifier.getInstance(((zzfa)localObject).getContext()).isUidGoogleSigned(Binder.getCallingUid());
            if (!paramBoolean)
            {
              paramBoolean = false;
              break label87;
            }
          }
        }
        paramBoolean = true;
        label87:
        zzaql = Boolean.valueOf(paramBoolean);
        localObject = zzaql;
        paramBoolean = ((Boolean)localObject).booleanValue();
        if (paramBoolean) {
          return;
        }
        if (zzaqm == null)
        {
          localObject = zzamz;
          paramBoolean = GooglePlayServicesUtilLight.uidHasPackageName(((zzfa)localObject).getContext(), Binder.getCallingUid(), paramString);
          if (paramBoolean) {
            zzaqm = paramString;
          }
        }
        localObject = zzaqm;
        paramBoolean = paramString.equals(localObject);
        if (paramBoolean) {
          return;
        }
        throw new SecurityException(String.format("Unknown calling package name '%s'.", new Object[] { paramString }));
      }
      catch (SecurityException localSecurityException)
      {
        zzamz.zzgo().zzjd().append("Measurement Service called with invalid calling package. appId", zzap.zzbv(paramString));
        throw localSecurityException;
      }
    }
    zzamz.zzgo().zzjd().zzbx("Measurement Service called without app package");
    throw new SecurityException("Measurement Service called without app package");
  }
  
  private final void toString(Runnable paramRunnable)
  {
    Preconditions.checkNotNull(paramRunnable);
    if ((((Boolean)zzaf.zzakv.getDefaultValue()).booleanValue()) && (zzamz.zzgn().zzkb()))
    {
      paramRunnable.run();
      return;
    }
    zzamz.zzgn().next(paramRunnable);
  }
  
  public final void chmod(VideoItem paramVideoItem)
  {
    append(paramVideoItem, false);
    toString(new zzcm(this, paramVideoItem));
  }
  
  public final byte[] get(zzad paramZzad, String paramString)
  {
    Preconditions.checkNotEmpty(paramString);
    Preconditions.checkNotNull(paramZzad);
    e(paramString, true);
    zzamz.zzgo().zzjk().append("Log and bundle. event", zzamz.zzgl().zzbs(name));
    long l1 = zzamz.zzbx().nanoTime() / 1000000L;
    Object localObject1 = zzamz.zzgn().get(new zzci(this, paramZzad, paramString));
    try
    {
      localObject1 = ((Future)localObject1).get();
      Object localObject2 = (byte[])localObject1;
      localObject1 = localObject2;
      if (localObject2 == null)
      {
        localObject1 = zzamz;
        ((zzfa)localObject1).zzgo().zzjd().append("Log and bundle returned null. appId", zzap.zzbv(paramString));
        localObject1 = new byte[0];
      }
      localObject2 = zzamz;
      long l2 = ((zzfa)localObject2).zzbx().nanoTime();
      l2 /= 1000000L;
      localObject2 = zzamz;
      localObject2 = ((zzfa)localObject2).zzgo().zzjk();
      Object localObject3 = zzamz;
      localObject3 = ((zzfa)localObject3).zzgl();
      String str = name;
      localObject3 = ((zzan)localObject3).zzbs(str);
      int i = localObject1.length;
      ((zzar)localObject2).append("Log and bundle processed. event, size, time_ms", localObject3, Integer.valueOf(i), Long.valueOf(l2 - l1));
      return localObject1;
    }
    catch (ExecutionException localExecutionException) {}catch (InterruptedException localInterruptedException) {}
    zzamz.zzgo().zzjd().append("Failed to log and bundle. appId, event, error", zzap.zzbv(paramString), zzamz.zzgl().zzbs(name), localInterruptedException);
    return null;
  }
  
  public final String getAbsoluteUrl(VideoItem paramVideoItem)
  {
    append(paramVideoItem, false);
    return zzamz.getAbsoluteUrl(paramVideoItem);
  }
  
  public final List getFromLocationName(VideoItem paramVideoItem, boolean paramBoolean)
  {
    append(paramVideoItem, false);
    Object localObject1 = zzamz.zzgn().d(new zzcl(this, paramVideoItem));
    try
    {
      localObject1 = ((Future)localObject1).get();
      Object localObject2 = (List)localObject1;
      localObject1 = new ArrayList(((List)localObject2).size());
      localObject2 = ((List)localObject2).iterator();
      for (;;)
      {
        boolean bool = ((Iterator)localObject2).hasNext();
        if (!bool) {
          break;
        }
        Object localObject3 = ((Iterator)localObject2).next();
        localObject3 = (zzfj)localObject3;
        if (!paramBoolean)
        {
          String str = name;
          bool = zzfk.zzcv(str);
          if (bool) {}
        }
        else
        {
          ((List)localObject1).add(new zzfh((zzfj)localObject3));
        }
      }
      return localObject1;
    }
    catch (ExecutionException localExecutionException) {}catch (InterruptedException localInterruptedException) {}
    zzamz.zzgo().zzjd().append("Failed to get user attributes. appId", zzap.zzbv(packageName), localInterruptedException);
    return null;
  }
  
  public final List getFromLocationName(String paramString1, String paramString2, VideoItem paramVideoItem)
  {
    append(paramVideoItem, false);
    paramString1 = zzamz.zzgn().d(new zzcd(this, paramVideoItem, paramString1, paramString2));
    try
    {
      paramString1 = paramString1.get();
      return (List)paramString1;
    }
    catch (ExecutionException paramString1) {}catch (InterruptedException paramString1) {}
    zzamz.zzgo().zzjd().append("Failed to get conditional user properties", paramString1);
    return Collections.emptyList();
  }
  
  public final List getFromLocationName(String paramString1, String paramString2, String paramString3)
  {
    e(paramString1, true);
    paramString1 = zzamz.zzgn().d(new zzce(this, paramString1, paramString2, paramString3));
    try
    {
      paramString1 = paramString1.get();
      return (List)paramString1;
    }
    catch (ExecutionException paramString1) {}catch (InterruptedException paramString1) {}
    zzamz.zzgo().zzjd().append("Failed to get conditional user properties", paramString1);
    return Collections.emptyList();
  }
  
  public final List getFromLocationName(String paramString1, String paramString2, String paramString3, boolean paramBoolean)
  {
    e(paramString1, true);
    paramString2 = zzamz.zzgn().d(new zzcc(this, paramString1, paramString2, paramString3));
    try
    {
      paramString2 = paramString2.get();
      paramString3 = (List)paramString2;
      paramString2 = new ArrayList(paramString3.size());
      paramString3 = paramString3.iterator();
      for (;;)
      {
        boolean bool = paramString3.hasNext();
        if (!bool) {
          break;
        }
        Object localObject = paramString3.next();
        localObject = (zzfj)localObject;
        if (!paramBoolean)
        {
          String str = name;
          bool = zzfk.zzcv(str);
          if (bool) {}
        }
        else
        {
          paramString2.add(new zzfh((zzfj)localObject));
        }
      }
      return paramString2;
    }
    catch (ExecutionException paramString2) {}catch (InterruptedException paramString2) {}
    zzamz.zzgo().zzjd().append("Failed to get user attributes. appId", zzap.zzbv(paramString1), paramString2);
    return Collections.emptyList();
  }
  
  public final List getFromLocationName(String paramString1, String paramString2, boolean paramBoolean, VideoItem paramVideoItem)
  {
    append(paramVideoItem, false);
    paramString1 = zzamz.zzgn().d(new zzcb(this, paramVideoItem, paramString1, paramString2));
    try
    {
      paramString1 = paramString1.get();
      paramString2 = (List)paramString1;
      paramString1 = new ArrayList(paramString2.size());
      paramString2 = paramString2.iterator();
      for (;;)
      {
        boolean bool = paramString2.hasNext();
        if (!bool) {
          break;
        }
        Object localObject = paramString2.next();
        localObject = (zzfj)localObject;
        if (!paramBoolean)
        {
          String str = name;
          bool = zzfk.zzcv(str);
          if (bool) {}
        }
        else
        {
          paramString1.add(new zzfh((zzfj)localObject));
        }
      }
      return paramString1;
    }
    catch (ExecutionException paramString1) {}catch (InterruptedException paramString1) {}
    zzamz.zzgo().zzjd().append("Failed to get user attributes. appId", zzap.zzbv(packageName), paramString1);
    return Collections.emptyList();
  }
  
  public final void getPackageInfo(ComponentInfo paramComponentInfo, VideoItem paramVideoItem)
  {
    Preconditions.checkNotNull(paramComponentInfo);
    Preconditions.checkNotNull(zzahb);
    append(paramVideoItem, false);
    ComponentInfo localComponentInfo = new ComponentInfo(paramComponentInfo);
    packageName = packageName;
    if (zzahb.getValue() == null)
    {
      toString(new zzbx(this, localComponentInfo, paramVideoItem));
      return;
    }
    toString(new zzby(this, localComponentInfo, paramVideoItem));
  }
  
  public final void handleResult(VideoItem paramVideoItem)
  {
    e(packageName, false);
    toString(new zzcf(this, paramVideoItem));
  }
  
  public final void handleResult(zzad paramZzad, VideoItem paramVideoItem)
  {
    Preconditions.checkNotNull(paramZzad);
    append(paramVideoItem, false);
    toString(new zzcg(this, paramZzad, paramVideoItem));
  }
  
  public final void handleResult(zzad paramZzad, String paramString1, String paramString2)
  {
    Preconditions.checkNotNull(paramZzad);
    Preconditions.checkNotEmpty(paramString1);
    e(paramString1, true);
    toString(new zzch(this, paramZzad, paramString1));
  }
  
  public final void onNetworkStateChanged(long paramLong, String paramString1, String paramString2, String paramString3)
  {
    toString(new zzcn(this, paramString2, paramString3, paramString1, paramLong));
  }
  
  public final void openInPhone(VideoItem paramVideoItem)
  {
    append(paramVideoItem, false);
    toString(new zzbw(this, paramVideoItem));
  }
  
  final zzad toJSONObject(zzad paramZzad, VideoItem paramVideoItem)
  {
    boolean bool = "_cmp".equals(name);
    int j = 0;
    int i = j;
    if (bool)
    {
      i = j;
      if (zzaid != null) {
        if (zzaid.size() == 0)
        {
          i = j;
        }
        else
        {
          String str = zzaid.getString("_cis");
          i = j;
          if (!TextUtils.isEmpty(str)) {
            if (!"referrer broadcast".equals(str))
            {
              i = j;
              if (!"referrer API".equals(str)) {}
            }
            else
            {
              i = j;
              if (zzamz.zzgq().zzbg(packageName)) {
                i = 1;
              }
            }
          }
        }
      }
    }
    if (i != 0)
    {
      zzamz.zzgo().zzjj().append("Event has been filtered ", paramZzad.toString());
      return new zzad("_cmpx", zzaid, origin, zzaip);
    }
    return paramZzad;
  }
  
  public final void toggleState(ComponentInfo paramComponentInfo)
  {
    Preconditions.checkNotNull(paramComponentInfo);
    Preconditions.checkNotNull(zzahb);
    e(packageName, true);
    ComponentInfo localComponentInfo = new ComponentInfo(paramComponentInfo);
    if (zzahb.getValue() == null)
    {
      toString(new zzbz(this, localComponentInfo));
      return;
    }
    toString(new zzca(this, localComponentInfo));
  }
  
  public final void write(zzfh paramZzfh, VideoItem paramVideoItem)
  {
    Preconditions.checkNotNull(paramZzfh);
    append(paramVideoItem, false);
    if (paramZzfh.getValue() == null)
    {
      toString(new zzcj(this, paramZzfh, paramVideoItem));
      return;
    }
    toString(new zzck(this, paramZzfh, paramVideoItem));
  }
}
