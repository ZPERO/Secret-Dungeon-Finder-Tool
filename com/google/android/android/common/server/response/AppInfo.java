package com.google.android.android.common.server.response;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.android.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.android.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.server.response.zal;
import com.google.android.gms.common.server.response.zam;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public final class AppInfo
  extends AbstractSafeParcelable
{
  public static final Parcelable.Creator<zal> CREATOR = new DiscreteSeekBar.CustomState.1();
  final String className;
  private final int versionCode;
  final ArrayList<zam> zaqx;
  
  AppInfo(int paramInt, String paramString, ArrayList paramArrayList)
  {
    versionCode = paramInt;
    className = paramString;
    zaqx = paramArrayList;
  }
  
  AppInfo(String paramString, Map paramMap)
  {
    versionCode = 1;
    className = paramString;
    if (paramMap == null)
    {
      paramString = null;
    }
    else
    {
      ArrayList localArrayList = new ArrayList();
      Iterator localIterator = paramMap.keySet().iterator();
      for (;;)
      {
        paramString = localArrayList;
        if (!localIterator.hasNext()) {
          break;
        }
        paramString = (String)localIterator.next();
        localArrayList.add(new CustomTile.ExpandedStyle(paramString, (FastJsonResponse.Field)paramMap.get(paramString)));
      }
    }
    zaqx = paramString;
  }
  
  public final void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramInt = SafeParcelWriter.beginObjectHeader(paramParcel);
    SafeParcelWriter.writeInt(paramParcel, 1, versionCode);
    SafeParcelWriter.writeString(paramParcel, 2, className, false);
    SafeParcelWriter.writeTypedList(paramParcel, 3, zaqx, false);
    SafeParcelWriter.finishObjectHeader(paramParcel, paramInt);
  }
}
