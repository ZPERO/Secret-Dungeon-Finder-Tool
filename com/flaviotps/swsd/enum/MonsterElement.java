package com.flaviotps.swsd.enum;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsJVMKt;

@Metadata(bv={1, 0, 3}, d1={"\000\030\n\002\030\002\n\002\020\020\n\000\n\002\020\b\n\000\n\002\020\016\n\002\b\023\b?\001\030\000 \0272\b\022\004\022\0020\0000\001:\001\027B\037\b\002\022\006\020\002\032\0020\003\022\006\020\004\032\0020\005\022\006\020\006\032\0020\003?\006\002\020\007R\021\020\002\032\0020\003?\006\b\n\000\032\004\b\b\020\tR\032\020\006\032\0020\003X?\016?\006\016\n\000\032\004\b\n\020\t\"\004\b\013\020\fR\032\020\004\032\0020\005X?\016?\006\016\n\000\032\004\b\r\020\016\"\004\b\017\020\020j\002\b\021j\002\b\022j\002\b\023j\002\b\024j\002\b\025j\002\b\026?\006\030"}, d2={"Lcom/flaviotps/swsd/enum/MonsterElement;", "", "color", "", "value", "", "icon", "(Ljava/lang/String;IILjava/lang/String;I)V", "getColor", "()I", "getIcon", "setIcon", "(I)V", "getValue", "()Ljava/lang/String;", "setValue", "(Ljava/lang/String;)V", "FIRE", "WATER", "WIND", "LIGHT", "DARK", "NONE", "Companion", "app_release"}, k=1, mv={1, 1, 16})
public enum MonsterElement
{
  public static final Companion Companion = new Companion(null);
  private final int color;
  private int icon;
  private String value;
  
  static
  {
    MonsterElement localMonsterElement1 = new MonsterElement("FIRE", 0, 2131099730, "Fire", 2131230864);
    FIRE = localMonsterElement1;
    MonsterElement localMonsterElement2 = new MonsterElement("WATER", 1, 2131099802, "Water", 2131230962);
    WATER = localMonsterElement2;
    MonsterElement localMonsterElement3 = new MonsterElement("WIND", 2, 2131099803, "Wind", 2131230966);
    WIND = localMonsterElement3;
    MonsterElement localMonsterElement4 = new MonsterElement("LIGHT", 3, 2131099736, "Light", 2131230913);
    LIGHT = localMonsterElement4;
    MonsterElement localMonsterElement5 = new MonsterElement("DARK", 4, 2131099710, "Dark", 2131230852);
    DARK = localMonsterElement5;
    MonsterElement localMonsterElement6 = new MonsterElement("NONE", 5, 2131099775, "NONE", 0);
    NONE = localMonsterElement6;
    $VALUES = new MonsterElement[] { localMonsterElement1, localMonsterElement2, localMonsterElement3, localMonsterElement4, localMonsterElement5, localMonsterElement6 };
  }
  
  private MonsterElement(int paramInt1, String paramString, int paramInt2)
  {
    color = paramInt1;
    value = paramString;
    icon = paramInt2;
  }
  
  public final int getColor()
  {
    return color;
  }
  
  public final int getIcon()
  {
    return icon;
  }
  
  public final String getValue()
  {
    return value;
  }
  
  public final void setIcon(int paramInt)
  {
    icon = paramInt;
  }
  
  public final void setValue(String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "<set-?>");
    value = paramString;
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\030\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\000\n\002\020\016\n\000\b?\003\030\0002\0020\001B\007\b\002?\006\002\020\002J\016\020\003\032\0020\0042\006\020\005\032\0020\006?\006\007"}, d2={"Lcom/flaviotps/swsd/enum/MonsterElement$Companion;", "", "()V", "getMonsterByElement", "Lcom/flaviotps/swsd/enum/MonsterElement;", "element", "", "app_release"}, k=1, mv={1, 1, 16})
  public static final class Companion
  {
    private Companion() {}
    
    public final MonsterElement getMonsterByElement(String paramString)
    {
      Intrinsics.checkParameterIsNotNull(paramString, "element");
      MonsterElement[] arrayOfMonsterElement = MonsterElement.values();
      int j = arrayOfMonsterElement.length;
      int i = 0;
      while (i < j)
      {
        MonsterElement localMonsterElement = arrayOfMonsterElement[i];
        if (StringsKt__StringsJVMKt.equals(localMonsterElement.getValue(), paramString, true)) {
          return localMonsterElement;
        }
        i += 1;
      }
      return MonsterElement.NONE;
    }
  }
}
