package com.flaviotps.swsd.utils;

import com.flaviotps.swsd.enum.MonsterElement;
import com.flaviotps.swsd.enum.MonsterElement.Companion;
import com.flaviotps.swsd.model.FriendRequestModel;
import com.flaviotps.swsd.model.MonsterModel;
import com.flaviotps.swsd.model.SecretDungeonModel;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsJVMKt;

@Metadata(bv={1, 0, 3}, d1={"\000\f\n\002\030\002\n\002\020\000\n\002\b\003\030\000 \0032\0020\001:\001\003B\005?\006\002\020\002?\006\004"}, d2={"Lcom/flaviotps/swsd/utils/ParseUtils;", "", "()V", "Companion", "app_release"}, k=1, mv={1, 1, 16})
public final class ParseUtils
{
  public static final Companion Companion = new Companion(null);
  
  public ParseUtils() {}
  
  @Metadata(bv={1, 0, 3}, d1={"\000,\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\000\n\002\020$\n\002\020\016\n\000\n\002\030\002\n\002\b\003\n\002\020\b\n\002\b\003\b?\003\030\0002\0020\001B\007\b\002?\006\002\020\002J\032\020\003\032\0020\0042\022\020\005\032\016\022\004\022\0020\007\022\004\022\0020\0070\006J\032\020\b\032\0020\t2\022\020\005\032\016\022\004\022\0020\007\022\004\022\0020\0070\006J\030\020\n\032\0020\0072\006\020\013\032\0020\0072\b\b\002\020\f\032\0020\rJ\016\020\016\032\0020\r2\006\020\017\032\0020\007?\006\020"}, d2={"Lcom/flaviotps/swsd/utils/ParseUtils$Companion;", "", "()V", "parseFriendRequest", "Lcom/flaviotps/swsd/model/FriendRequestModel;", "map", "", "", "parseSecretDungeon", "Lcom/flaviotps/swsd/model/SecretDungeonModel;", "trim", "text", "maxSize", "", "versionToInt", "version", "app_release"}, k=1, mv={1, 1, 16})
  public static final class Companion
  {
    private Companion() {}
    
    public final FriendRequestModel parseFriendRequest(Map paramMap)
    {
      Intrinsics.checkParameterIsNotNull(paramMap, "map");
      String str = String.valueOf(paramMap.get("nickname"));
      return new FriendRequestModel(String.valueOf(paramMap.get("monster")), str, MonsterElement.Companion.getMonsterByElement(String.valueOf(paramMap.get("element"))), String.valueOf(paramMap.get("image")));
    }
    
    public final SecretDungeonModel parseSecretDungeon(Map paramMap)
    {
      Intrinsics.checkParameterIsNotNull(paramMap, "map");
      Object localObject2 = String.valueOf(paramMap.get("genericName"));
      String str = String.valueOf(paramMap.get("awakenName"));
      MonsterElement localMonsterElement = MonsterElement.Companion.getMonsterByElement(String.valueOf(paramMap.get("element")));
      Object localObject1 = (String)paramMap.get("stars");
      if (localObject1 == null) {
        localObject1 = Integer.valueOf(0);
      }
      localObject2 = new MonsterModel((String)localObject2, str, localMonsterElement, Integer.parseInt(localObject1.toString()), String.valueOf(paramMap.get("image")));
      str = String.valueOf(paramMap.get("inGameName"));
      localObject1 = (String)paramMap.get("creationTime");
      if (localObject1 == null) {
        localObject1 = "0";
      }
      long l1 = Long.parseLong((String)localObject1);
      localObject1 = (String)paramMap.get("endTime");
      if (localObject1 == null) {
        localObject1 = "0";
      }
      long l2 = Long.parseLong((String)localObject1);
      localObject1 = (String)paramMap.get("timeLeft");
      if (localObject1 == null) {
        localObject1 = "0";
      }
      int i = Integer.parseInt((String)localObject1);
      localObject1 = String.valueOf(paramMap.get("fcmToken"));
      paramMap = (String)paramMap.get("key");
      if (paramMap == null) {
        paramMap = "";
      }
      return new SecretDungeonModel(paramMap.toString(), (MonsterModel)localObject2, str, l1, l2, i, null, (String)localObject1, 64, null);
    }
    
    public final String trim(String paramString, int paramInt)
    {
      Intrinsics.checkParameterIsNotNull(paramString, "text");
      Object localObject = paramString;
      if (paramString.length() > paramInt)
      {
        paramString = paramString.substring(0, paramInt);
        Intrinsics.checkExpressionValueIsNotNull(paramString, "(this as java.lang.Strin?ing(startIndex, endIndex)");
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append(paramString);
        ((StringBuilder)localObject).append("...");
        localObject = ((StringBuilder)localObject).toString();
      }
      return localObject;
    }
    
    public final int versionToInt(String paramString)
    {
      Intrinsics.checkParameterIsNotNull(paramString, "version");
      return Integer.parseInt(StringsKt__StringsJVMKt.replace$default(paramString, ".", "", false, 4, null));
    }
  }
}