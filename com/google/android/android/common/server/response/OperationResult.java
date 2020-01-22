package com.google.android.android.common.server.response;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.android.common.internal.Preconditions;
import com.google.android.android.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.android.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.server.response.zak;
import com.google.android.gms.common.server.response.zal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public final class OperationResult
  extends AbstractSafeParcelable
{
  public static final Parcelable.Creator<zak> CREATOR = new DownloadProgressInfo.1();
  private final int zale;
  private final HashMap<String, Map<String, com.google.android.gms.common.server.response.FastJsonResponse.Field<?, ?>>> zaqu;
  private final ArrayList<zal> zaqv;
  private final String zaqw;
  
  OperationResult(int paramInt, ArrayList paramArrayList, String paramString)
  {
    zale = paramInt;
    zaqv = null;
    HashMap localHashMap1 = new HashMap();
    int j = paramArrayList.size();
    paramInt = 0;
    while (paramInt < j)
    {
      AppInfo localAppInfo = (AppInfo)paramArrayList.get(paramInt);
      String str = className;
      HashMap localHashMap2 = new HashMap();
      int k = zaqx.size();
      int i = 0;
      while (i < k)
      {
        CustomTile.ExpandedStyle localExpandedStyle = (CustomTile.ExpandedStyle)zaqx.get(i);
        localHashMap2.put(zaqy, zaqz);
        i += 1;
      }
      localHashMap1.put(str, localHashMap2);
      paramInt += 1;
    }
    zaqu = localHashMap1;
    zaqw = ((String)Preconditions.checkNotNull(paramString));
    zacr();
  }
  
  public OperationResult(Class paramClass)
  {
    zale = 1;
    zaqv = null;
    zaqu = new HashMap();
    zaqw = paramClass.getCanonicalName();
  }
  
  public final void add(Class paramClass, Map paramMap)
  {
    zaqu.put(paramClass.getCanonicalName(), paramMap);
  }
  
  public final boolean add(Class paramClass)
  {
    return zaqu.containsKey(paramClass.getCanonicalName());
  }
  
  public final Map getMessages(String paramString)
  {
    return (Map)zaqu.get(paramString);
  }
  
  public final String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    Iterator localIterator1 = zaqu.keySet().iterator();
    while (localIterator1.hasNext())
    {
      Object localObject = (String)localIterator1.next();
      localStringBuilder.append((String)localObject);
      localStringBuilder.append(":\n");
      localObject = (Map)zaqu.get(localObject);
      Iterator localIterator2 = ((Map)localObject).keySet().iterator();
      while (localIterator2.hasNext())
      {
        String str = (String)localIterator2.next();
        localStringBuilder.append("  ");
        localStringBuilder.append(str);
        localStringBuilder.append(": ");
        localStringBuilder.append(((Map)localObject).get(str));
      }
    }
    return localStringBuilder.toString();
  }
  
  public final void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramInt = SafeParcelWriter.beginObjectHeader(paramParcel);
    SafeParcelWriter.writeInt(paramParcel, 1, zale);
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = zaqu.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      localArrayList.add(new AppInfo(str, (Map)zaqu.get(str)));
    }
    SafeParcelWriter.writeTypedList(paramParcel, 2, localArrayList, false);
    SafeParcelWriter.writeString(paramParcel, 3, zaqw, false);
    SafeParcelWriter.finishObjectHeader(paramParcel, paramInt);
  }
  
  public final void zacr()
  {
    Iterator localIterator1 = zaqu.keySet().iterator();
    while (localIterator1.hasNext())
    {
      Object localObject = (String)localIterator1.next();
      localObject = (Map)zaqu.get(localObject);
      Iterator localIterator2 = ((Map)localObject).keySet().iterator();
      while (localIterator2.hasNext()) {
        ((FastJsonResponse.Field)((Map)localObject).get((String)localIterator2.next())).addTo(this);
      }
    }
  }
  
  public final void zacs()
  {
    Iterator localIterator1 = zaqu.keySet().iterator();
    while (localIterator1.hasNext())
    {
      String str1 = (String)localIterator1.next();
      Map localMap = (Map)zaqu.get(str1);
      HashMap localHashMap = new HashMap();
      Iterator localIterator2 = localMap.keySet().iterator();
      while (localIterator2.hasNext())
      {
        String str2 = (String)localIterator2.next();
        localHashMap.put(str2, ((FastJsonResponse.Field)localMap.get(str2)).zacl());
      }
      zaqu.put(str1, localHashMap);
    }
  }
  
  public final String zact()
  {
    return zaqw;
  }
}
