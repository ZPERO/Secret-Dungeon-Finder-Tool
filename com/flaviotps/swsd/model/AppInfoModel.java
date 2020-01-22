package com.flaviotps.swsd.model;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000\032\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\013\n\000\n\002\020\016\n\002\b\n\030\0002\0020\001B\007\b\026?\006\002\020\002B\025\022\006\020\003\032\0020\004\022\006\020\005\032\0020\006?\006\002\020\007R\032\020\003\032\0020\004X?\016?\006\016\n\000\032\004\b\b\020\t\"\004\b\n\020\013R\032\020\005\032\0020\006X?\016?\006\016\n\000\032\004\b\f\020\r\"\004\b\016\020\017?\006\020"}, d2={"Lcom/flaviotps/swsd/model/AppInfoModel;", "", "()V", "maintenance", "", "minVersion", "", "(ZLjava/lang/String;)V", "getMaintenance", "()Z", "setMaintenance", "(Z)V", "getMinVersion", "()Ljava/lang/String;", "setMinVersion", "(Ljava/lang/String;)V", "app_release"}, k=1, mv={1, 1, 16})
public final class AppInfoModel
{
  private boolean maintenance;
  private String minVersion;
  
  public AppInfoModel()
  {
    this(false, "1.1.5");
  }
  
  public AppInfoModel(boolean paramBoolean, String paramString)
  {
    maintenance = paramBoolean;
    minVersion = paramString;
  }
  
  public final boolean getMaintenance()
  {
    return maintenance;
  }
  
  public final String getMinVersion()
  {
    return minVersion;
  }
  
  public final void setMaintenance(boolean paramBoolean)
  {
    maintenance = paramBoolean;
  }
  
  public final void setMinVersion(String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "<set-?>");
    minVersion = paramString;
  }
}
