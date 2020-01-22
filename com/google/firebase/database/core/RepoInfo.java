package com.google.firebase.database.core;

import java.net.URI;

public class RepoInfo
{
  private static final String LAST_SESSION_ID_PARAM = "ls";
  private static final String VERSION_PARAM = "v";
  public String host;
  public String internalHost;
  public String namespace;
  public boolean secure;
  
  public RepoInfo() {}
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if (paramObject != null)
    {
      if (getClass() != paramObject.getClass()) {
        return false;
      }
      paramObject = (RepoInfo)paramObject;
      if (secure != secure) {
        return false;
      }
      if (!host.equals(host)) {
        return false;
      }
      return namespace.equals(namespace);
    }
    return false;
  }
  
  public URI getConnectionURL(String paramString)
  {
    if (secure) {
      localObject1 = "wss";
    } else {
      localObject1 = "ws";
    }
    Object localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append((String)localObject1);
    ((StringBuilder)localObject2).append("://");
    ((StringBuilder)localObject2).append(internalHost);
    ((StringBuilder)localObject2).append("/.ws?ns=");
    ((StringBuilder)localObject2).append(namespace);
    ((StringBuilder)localObject2).append("&");
    ((StringBuilder)localObject2).append("v");
    ((StringBuilder)localObject2).append("=");
    ((StringBuilder)localObject2).append("5");
    localObject2 = ((StringBuilder)localObject2).toString();
    Object localObject1 = localObject2;
    if (paramString != null)
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append((String)localObject2);
      ((StringBuilder)localObject1).append("&ls=");
      ((StringBuilder)localObject1).append(paramString);
      localObject1 = ((StringBuilder)localObject1).toString();
    }
    return URI.create((String)localObject1);
  }
  
  public int hashCode()
  {
    throw new Runtime("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.provideAs(TypeTransformer.java:780)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.e1expr(TypeTransformer.java:496)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:713)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.e2expr(TypeTransformer.java:632)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:716)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.e2expr(TypeTransformer.java:629)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:716)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.e2expr(TypeTransformer.java:629)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:716)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.s1stmt(TypeTransformer.java:810)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.sxStmt(TypeTransformer.java:840)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:206)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\n");
  }
  
  public boolean isCacheableHost()
  {
    return internalHost.startsWith("s-");
  }
  
  public boolean isCustomHost()
  {
    return (!host.contains(".firebaseio.com")) && (!host.contains(".firebaseio-demo.com"));
  }
  
  public boolean isDemoHost()
  {
    return host.contains(".firebaseio-demo.com");
  }
  
  public boolean isSecure()
  {
    return secure;
  }
  
  public String toDebugString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("(host=");
    localStringBuilder.append(host);
    localStringBuilder.append(", secure=");
    localStringBuilder.append(secure);
    localStringBuilder.append(", ns=");
    localStringBuilder.append(namespace);
    localStringBuilder.append(" internal=");
    localStringBuilder.append(internalHost);
    localStringBuilder.append(")");
    return localStringBuilder.toString();
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("http");
    String str;
    if (secure) {
      str = "s";
    } else {
      str = "";
    }
    localStringBuilder.append(str);
    localStringBuilder.append("://");
    localStringBuilder.append(host);
    return localStringBuilder.toString();
  }
}
