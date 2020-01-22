package com.flaviotps.swsd.enum;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsJVMKt;

@Metadata(bv={1, 0, 3}, d1={"\000\022\n\002\030\002\n\002\020\020\n\000\n\002\020\016\n\002\b\n\b?\001\030\000 \f2\b\022\004\022\0020\0000\001:\001\fB\017\b\002\022\006\020\002\032\0020\003?\006\002\020\004R\032\020\002\032\0020\003X?\016?\006\016\n\000\032\004\b\005\020\006\"\004\b\007\020\bj\002\b\tj\002\b\nj\002\b\013?\006\r"}, d2={"Lcom/flaviotps/swsd/enum/NotificationType;", "", "value", "", "(Ljava/lang/String;ILjava/lang/String;)V", "getValue", "()Ljava/lang/String;", "setValue", "(Ljava/lang/String;)V", "FRIEND_REQUEST", "SECRET_DUNGEON", "NONE", "Companion", "app_release"}, k=1, mv={1, 1, 16})
public enum NotificationType
{
  public static final Companion Companion = new Companion(null);
  private String value;
  
  static
  {
    NotificationType localNotificationType1 = new NotificationType("FRIEND_REQUEST", 0, "FR");
    FRIEND_REQUEST = localNotificationType1;
    NotificationType localNotificationType2 = new NotificationType("SECRET_DUNGEON", 1, "SD");
    SECRET_DUNGEON = localNotificationType2;
    NotificationType localNotificationType3 = new NotificationType("NONE", 2, "");
    NONE = localNotificationType3;
    $VALUES = new NotificationType[] { localNotificationType1, localNotificationType2, localNotificationType3 };
  }
  
  private NotificationType(String paramString)
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
  
  @Metadata(bv={1, 0, 3}, d1={"\000\030\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\000\n\002\020\016\n\000\b?\003\030\0002\0020\001B\007\b\002?\006\002\020\002J\016\020\003\032\0020\0042\006\020\005\032\0020\006?\006\007"}, d2={"Lcom/flaviotps/swsd/enum/NotificationType$Companion;", "", "()V", "getNotificationType", "Lcom/flaviotps/swsd/enum/NotificationType;", "server", "", "app_release"}, k=1, mv={1, 1, 16})
  public static final class Companion
  {
    private Companion() {}
    
    public final NotificationType getNotificationType(String paramString)
    {
      Intrinsics.checkParameterIsNotNull(paramString, "server");
      NotificationType[] arrayOfNotificationType = NotificationType.values();
      int j = arrayOfNotificationType.length;
      int i = 0;
      while (i < j)
      {
        NotificationType localNotificationType = arrayOfNotificationType[i];
        if (StringsKt__StringsJVMKt.equals(localNotificationType.getValue(), paramString, true)) {
          return localNotificationType;
        }
        i += 1;
      }
      return NotificationType.NONE;
    }
  }
}
