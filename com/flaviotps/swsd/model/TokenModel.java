package com.flaviotps.swsd.model;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000\"\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\016\n\000\n\002\020\013\n\002\b\023\n\002\020\b\n\002\b\002\b?\b\030\0002\0020\001B\007\b\026?\006\002\020\002B!\022\006\020\003\032\0020\004\022\b\b\002\020\005\032\0020\006\022\b\b\002\020\007\032\0020\006?\006\002\020\bJ\t\020\023\032\0020\004H?\003J\t\020\024\032\0020\006H?\003J\t\020\025\032\0020\006H?\003J'\020\026\032\0020\0002\b\b\002\020\003\032\0020\0042\b\b\002\020\005\032\0020\0062\b\b\002\020\007\032\0020\006H?\001J\023\020\027\032\0020\0062\b\020\030\032\004\030\0010\001H?\003J\t\020\031\032\0020\032H?\001J\t\020\033\032\0020\004H?\001R\032\020\005\032\0020\006X?\016?\006\016\n\000\032\004\b\t\020\n\"\004\b\013\020\fR\032\020\007\032\0020\006X?\016?\006\016\n\000\032\004\b\r\020\n\"\004\b\016\020\fR\032\020\003\032\0020\004X?\016?\006\016\n\000\032\004\b\017\020\020\"\004\b\021\020\022?\006\034"}, d2={"Lcom/flaviotps/swsd/model/TokenModel;", "", "()V", "token", "", "enabled", "", "requestEnabled", "(Ljava/lang/String;ZZ)V", "getEnabled", "()Z", "setEnabled", "(Z)V", "getRequestEnabled", "setRequestEnabled", "getToken", "()Ljava/lang/String;", "setToken", "(Ljava/lang/String;)V", "component1", "component2", "component3", "copy", "equals", "other", "hashCode", "", "toString", "app_release"}, k=1, mv={1, 1, 16})
public final class TokenModel
{
  private boolean enabled;
  private boolean requestEnabled;
  private String token;
  
  public TokenModel()
  {
    this("", true, true);
  }
  
  public TokenModel(String paramString, boolean paramBoolean1, boolean paramBoolean2)
  {
    token = paramString;
    enabled = paramBoolean1;
    requestEnabled = paramBoolean2;
  }
  
  public final String component1()
  {
    return token;
  }
  
  public final boolean component2()
  {
    return enabled;
  }
  
  public final boolean component3()
  {
    return requestEnabled;
  }
  
  public final TokenModel copy(String paramString, boolean paramBoolean1, boolean paramBoolean2)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "token");
    return new TokenModel(paramString, paramBoolean1, paramBoolean2);
  }
  
  public boolean equals(Object paramObject)
  {
    if (this != paramObject) {
      if ((paramObject instanceof TokenModel))
      {
        paramObject = (TokenModel)paramObject;
        if ((Intrinsics.areEqual(token, token)) && (enabled == enabled) && (requestEnabled == requestEnabled)) {}
      }
      else
      {
        return false;
      }
    }
    return true;
  }
  
  public final boolean getEnabled()
  {
    return enabled;
  }
  
  public final boolean getRequestEnabled()
  {
    return requestEnabled;
  }
  
  public final String getToken()
  {
    return token;
  }
  
  public int hashCode()
  {
    throw new Runtime("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\n");
  }
  
  public final void setEnabled(boolean paramBoolean)
  {
    enabled = paramBoolean;
  }
  
  public final void setRequestEnabled(boolean paramBoolean)
  {
    requestEnabled = paramBoolean;
  }
  
  public final void setToken(String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "<set-?>");
    token = paramString;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("TokenModel(token=");
    localStringBuilder.append(token);
    localStringBuilder.append(", enabled=");
    localStringBuilder.append(enabled);
    localStringBuilder.append(", requestEnabled=");
    localStringBuilder.append(requestEnabled);
    localStringBuilder.append(")");
    return localStringBuilder.toString();
  }
}
