package com.flaviotps.swsd.model;

import com.flaviotps.swsd.enum.MonsterElement;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000*\n\002\030\002\n\002\020\000\n\000\n\002\020\016\n\002\b\002\n\002\030\002\n\002\b\024\n\002\020\013\n\002\b\002\n\002\020\b\n\002\b\002\b?\b\030\0002\0020\001B/\022\b\b\002\020\002\032\0020\003\022\b\b\002\020\004\032\0020\003\022\b\b\002\020\005\032\0020\006\022\n\b\002\020\007\032\004\030\0010\003?\006\002\020\bJ\t\020\025\032\0020\003H?\003J\t\020\026\032\0020\003H?\003J\t\020\027\032\0020\006H?\003J\013\020\030\032\004\030\0010\003H?\003J3\020\031\032\0020\0002\b\b\002\020\002\032\0020\0032\b\b\002\020\004\032\0020\0032\b\b\002\020\005\032\0020\0062\n\b\002\020\007\032\004\030\0010\003H?\001J\023\020\032\032\0020\0332\b\020\034\032\004\030\0010\001H?\003J\t\020\035\032\0020\036H?\001J\t\020\037\032\0020\003H?\001R\032\020\005\032\0020\006X?\016?\006\016\n\000\032\004\b\t\020\n\"\004\b\013\020\fR\034\020\007\032\004\030\0010\003X?\016?\006\016\n\000\032\004\b\r\020\016\"\004\b\017\020\020R\032\020\002\032\0020\003X?\016?\006\016\n\000\032\004\b\021\020\016\"\004\b\022\020\020R\032\020\004\032\0020\003X?\016?\006\016\n\000\032\004\b\023\020\016\"\004\b\024\020\020?\006 "}, d2={"Lcom/flaviotps/swsd/model/FriendRequestModel;", "", "monster", "", "nickname", "element", "Lcom/flaviotps/swsd/enum/MonsterElement;", "image", "(Ljava/lang/String;Ljava/lang/String;Lcom/flaviotps/swsd/enum/MonsterElement;Ljava/lang/String;)V", "getElement", "()Lcom/flaviotps/swsd/enum/MonsterElement;", "setElement", "(Lcom/flaviotps/swsd/enum/MonsterElement;)V", "getImage", "()Ljava/lang/String;", "setImage", "(Ljava/lang/String;)V", "getMonster", "setMonster", "getNickname", "setNickname", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "", "toString", "app_release"}, k=1, mv={1, 1, 16})
public final class FriendRequestModel
{
  private MonsterElement element;
  private String image;
  private String monster;
  private String nickname;
  
  public FriendRequestModel()
  {
    this(null, null, null, null, 15, null);
  }
  
  public FriendRequestModel(String paramString1, String paramString2, MonsterElement paramMonsterElement, String paramString3)
  {
    monster = paramString1;
    nickname = paramString2;
    element = paramMonsterElement;
    image = paramString3;
  }
  
  public final String component1()
  {
    return monster;
  }
  
  public final String component2()
  {
    return nickname;
  }
  
  public final MonsterElement component3()
  {
    return element;
  }
  
  public final String component4()
  {
    return image;
  }
  
  public final FriendRequestModel copy(String paramString1, String paramString2, MonsterElement paramMonsterElement, String paramString3)
  {
    Intrinsics.checkParameterIsNotNull(paramString1, "monster");
    Intrinsics.checkParameterIsNotNull(paramString2, "nickname");
    Intrinsics.checkParameterIsNotNull(paramMonsterElement, "element");
    return new FriendRequestModel(paramString1, paramString2, paramMonsterElement, paramString3);
  }
  
  public boolean equals(Object paramObject)
  {
    if (this != paramObject) {
      if ((paramObject instanceof FriendRequestModel))
      {
        paramObject = (FriendRequestModel)paramObject;
        if ((Intrinsics.areEqual(monster, monster)) && (Intrinsics.areEqual(nickname, nickname)) && (Intrinsics.areEqual(element, element)) && (Intrinsics.areEqual(image, image))) {}
      }
      else
      {
        return false;
      }
    }
    return true;
  }
  
  public final MonsterElement getElement()
  {
    return element;
  }
  
  public final String getImage()
  {
    return image;
  }
  
  public final String getMonster()
  {
    return monster;
  }
  
  public final String getNickname()
  {
    return nickname;
  }
  
  public int hashCode()
  {
    Object localObject = monster;
    int m = 0;
    int i;
    if (localObject != null) {
      i = localObject.hashCode();
    } else {
      i = 0;
    }
    localObject = nickname;
    int j;
    if (localObject != null) {
      j = localObject.hashCode();
    } else {
      j = 0;
    }
    localObject = element;
    int k;
    if (localObject != null) {
      k = localObject.hashCode();
    } else {
      k = 0;
    }
    localObject = image;
    if (localObject != null) {
      m = localObject.hashCode();
    }
    return ((i * 31 + j) * 31 + k) * 31 + m;
  }
  
  public final void setElement(MonsterElement paramMonsterElement)
  {
    Intrinsics.checkParameterIsNotNull(paramMonsterElement, "<set-?>");
    element = paramMonsterElement;
  }
  
  public final void setImage(String paramString)
  {
    image = paramString;
  }
  
  public final void setMonster(String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "<set-?>");
    monster = paramString;
  }
  
  public final void setNickname(String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "<set-?>");
    nickname = paramString;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("FriendRequestModel(monster=");
    localStringBuilder.append(monster);
    localStringBuilder.append(", nickname=");
    localStringBuilder.append(nickname);
    localStringBuilder.append(", element=");
    localStringBuilder.append(element);
    localStringBuilder.append(", image=");
    localStringBuilder.append(image);
    localStringBuilder.append(")");
    return localStringBuilder.toString();
  }
}
