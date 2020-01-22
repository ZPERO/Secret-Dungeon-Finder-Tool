package com.flaviotps.swsd.enum;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsJVMKt;

@Metadata(bv={1, 0, 3}, d1={"\000\022\n\002\030\002\n\002\020\020\n\000\n\002\020\016\n\002\b\013\b?\001\030\000 \r2\b\022\004\022\0020\0000\001:\001\rB\017\b\002\022\006\020\002\032\0020\003?\006\002\020\004R\032\020\002\032\0020\003X?\016?\006\016\n\000\032\004\b\005\020\006\"\004\b\007\020\bj\002\b\tj\002\b\nj\002\b\013j\002\b\f?\006\016"}, d2={"Lcom/flaviotps/swsd/enum/ServerLocation;", "", "value", "", "(Ljava/lang/String;ILjava/lang/String;)V", "getValue", "()Ljava/lang/String;", "setValue", "(Ljava/lang/String;)V", "GLOBAL", "ASIA", "EUROPE", "NONE", "Companion", "app_release"}, k=1, mv={1, 1, 16})
public enum ServerLocation
{
  public static final Companion Companion = new Companion(null);
  private String value;
  
  static
  {
    ServerLocation localServerLocation1 = new ServerLocation("GLOBAL", 0, "Global");
    GLOBAL = localServerLocation1;
    ServerLocation localServerLocation2 = new ServerLocation("ASIA", 1, "Asia");
    ASIA = localServerLocation2;
    ServerLocation localServerLocation3 = new ServerLocation("EUROPE", 2, "Europe");
    EUROPE = localServerLocation3;
    ServerLocation localServerLocation4 = new ServerLocation("NONE", 3, "NONE");
    NONE = localServerLocation4;
    $VALUES = new ServerLocation[] { localServerLocation1, localServerLocation2, localServerLocation3, localServerLocation4 };
  }
  
  private ServerLocation(String paramString)
  {
    value = paramString;
  }
  
  public final String getValue()
  {
    return value;
  }
  
  public final void setValue(String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "<set-?>");
    value = paramString;
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\030\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\000\n\002\020\016\n\000\b?\003\030\0002\0020\001B\007\b\002?\006\002\020\002J\016\020\003\032\0020\0042\006\020\005\032\0020\006?\006\007"}, d2={"Lcom/flaviotps/swsd/enum/ServerLocation$Companion;", "", "()V", "getServerType", "Lcom/flaviotps/swsd/enum/ServerLocation;", "server", "", "app_release"}, k=1, mv={1, 1, 16})
  public static final class Companion
  {
    private Companion() {}
    
    public final ServerLocation getServerType(String paramString)
    {
      Intrinsics.checkParameterIsNotNull(paramString, "server");
      ServerLocation[] arrayOfServerLocation = ServerLocation.values();
      int j = arrayOfServerLocation.length;
      int i = 0;
      while (i < j)
      {
        ServerLocation localServerLocation = arrayOfServerLocation[i];
        if (StringsKt__StringsJVMKt.equals(localServerLocation.getValue(), paramString, true)) {
          return localServerLocation;
        }
        i += 1;
      }
      return ServerLocation.NONE;
    }
  }
}