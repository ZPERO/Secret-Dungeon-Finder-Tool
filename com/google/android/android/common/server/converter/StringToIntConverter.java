package com.google.android.android.common.server.converter;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.util.SparseArray;
import com.google.android.android.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.server.response.FastJsonResponse.FieldConverter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public final class StringToIntConverter
  extends com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable
  implements FastJsonResponse.FieldConverter<String, Integer>
{
  public static final Parcelable.Creator<com.google.android.gms.common.server.converter.StringToIntConverter> CREATOR = new DiscreteSeekBar.CustomState.1();
  private final int zale;
  private final HashMap<String, Integer> zapl;
  private final SparseArray<String> zapm;
  private final ArrayList<com.google.android.gms.common.server.converter.StringToIntConverter.zaa> zapn;
  
  public StringToIntConverter()
  {
    zale = 1;
    zapl = new HashMap();
    zapm = new SparseArray();
    zapn = null;
  }
  
  StringToIntConverter(int paramInt, ArrayList paramArrayList)
  {
    zale = paramInt;
    zapl = new HashMap();
    zapm = new SparseArray();
    zapn = null;
    paramArrayList = (ArrayList)paramArrayList;
    int i = paramArrayList.size();
    paramInt = 0;
    while (paramInt < i)
    {
      Object localObject = paramArrayList.get(paramInt);
      paramInt += 1;
      localObject = (zaa)localObject;
      add(zapo, zapp);
    }
  }
  
  public final StringToIntConverter add(String paramString, int paramInt)
  {
    zapl.put(paramString, Integer.valueOf(paramInt));
    zapm.put(paramInt, paramString);
    return this;
  }
  
  public final void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramInt = SafeParcelWriter.beginObjectHeader(paramParcel);
    SafeParcelWriter.writeInt(paramParcel, 1, zale);
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = zapl.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      localArrayList.add(new zaa(str, ((Integer)zapl.get(str)).intValue()));
    }
    SafeParcelWriter.writeTypedList(paramParcel, 2, localArrayList, false);
    SafeParcelWriter.finishObjectHeader(paramParcel, paramInt);
  }
  
  public final int zacj()
  {
    return 7;
  }
  
  public final int zack()
  {
    return 0;
  }
  
  public final class zaa
    extends com.google.android.android.common.internal.safeparcel.AbstractSafeParcelable
  {
    public static final Parcelable.Creator<com.google.android.gms.common.server.converter.StringToIntConverter.zaa> CREATOR = new DownloadProgressInfo.1();
    private final int versionCode;
    final int zapp;
    
    zaa(String paramString, int paramInt)
    {
      versionCode = this$1;
      zapp = paramInt;
    }
    
    zaa(int paramInt)
    {
      versionCode = 1;
      zapp = paramInt;
    }
    
    public final void writeToParcel(Parcel paramParcel, int paramInt)
    {
      paramInt = SafeParcelWriter.beginObjectHeader(paramParcel);
      SafeParcelWriter.writeInt(paramParcel, 1, versionCode);
      SafeParcelWriter.writeString(paramParcel, 2, StringToIntConverter.this, false);
      SafeParcelWriter.writeInt(paramParcel, 3, zapp);
      SafeParcelWriter.finishObjectHeader(paramParcel, paramInt);
    }
  }
}
