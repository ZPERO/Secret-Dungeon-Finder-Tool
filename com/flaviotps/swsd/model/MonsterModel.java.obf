package com.flaviotps.swsd.model;

import com.flaviotps.swsd.enum.MonsterElement;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000*\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\016\n\002\b\002\n\002\030\002\n\000\n\002\020\b\n\002\b\031\n\002\020\013\n\002\b\004\b?\b\030\0002\0020\001B\007\b\026?\006\002\020\002B5\022\006\020\003\032\0020\004\022\006\020\005\032\0020\004\022\b\b\002\020\006\032\0020\007\022\b\b\002\020\b\032\0020\t\022\n\b\002\020\n\032\004\030\0010\004?\006\002\020\013J\t\020\034\032\0020\004H?\003J\t\020\035\032\0020\004H?\003J\t\020\036\032\0020\007H?\003J\t\020\037\032\0020\tH?\003J\013\020 \032\004\030\0010\004H?\003J=\020!\032\0020\0002\b\b\002\020\003\032\0020\0042\b\b\002\020\005\032\0020\0042\b\b\002\020\006\032\0020\0072\b\b\002\020\b\032\0020\t2\n\b\002\020\n\032\004\030\0010\004H?\001J\023\020\"\032\0020#2\b\020$\032\004\030\0010\001H?\003J\t\020%\032\0020\tH?\001J\b\020&\032\0020\004H\026R\032\020\005\032\0020\004X?\016?\006\016\n\000\032\004\b\f\020\r\"\004\b\016\020\017R\032\020\006\032\0020\007X?\016?\006\016\n\000\032\004\b\020\020\021\"\004\b\022\020\023R\032\020\003\032\0020\004X?\016?\006\016\n\000\032\004\b\024\020\r\"\004\b\025\020\017R\034\020\n\032\004\030\0010\004X?\016?\006\016\n\000\032\004\b\026\020\r\"\004\b\027\020\017R\032\020\b\032\0020\tX?\016?\006\016\n\000\032\004\b\030\020\031\"\004\b\032\020\033?\006'"}, d2={"Lcom/flaviotps/swsd/model/MonsterModel;", "", "()V", "genericName", "", "awakenName", "element", "Lcom/flaviotps/swsd/enum/MonsterElement;", "stars", "", "image", "(Ljava/lang/String;Ljava/lang/String;Lcom/flaviotps/swsd/enum/MonsterElement;ILjava/lang/String;)V", "getAwakenName", "()Ljava/lang/String;", "setAwakenName", "(Ljava/lang/String;)V", "getElement", "()Lcom/flaviotps/swsd/enum/MonsterElement;", "setElement", "(Lcom/flaviotps/swsd/enum/MonsterElement;)V", "getGenericName", "setGenericName", "getImage", "setImage", "getStars", "()I", "setStars", "(I)V", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "", "other", "hashCode", "toString", "app_release"}, k=1, mv={1, 1, 16})
public final class MonsterModel
{
  private String awakenName;
  private MonsterElement element;
  private String genericName;
  private String image;
  private int stars;
  
  public MonsterModel()
  {
    this("", "", null, 0, null, 28, null);
  }
  
  public MonsterModel(String paramString1, String paramString2, MonsterElement paramMonsterElement, int paramInt, String paramString3)
  {
    genericName = paramString1;
    awakenName = paramString2;
    element = paramMonsterElement;
    stars = paramInt;
    image = paramString3;
  }
  
  public final String component1()
  {
    return genericName;
  }
  
  public final String component2()
  {
    return awakenName;
  }
  
  public final MonsterElement component3()
  {
    return element;
  }
  
  public final int component4()
  {
    return stars;
  }
  
  public final String component5()
  {
    return image;
  }
  
  public final MonsterModel copy(String paramString1, String paramString2, MonsterElement paramMonsterElement, int paramInt, String paramString3)
  {
    Intrinsics.checkParameterIsNotNull(paramString1, "genericName");
    Intrinsics.checkParameterIsNotNull(paramString2, "awakenName");
    Intrinsics.checkParameterIsNotNull(paramMonsterElement, "element");
    return new MonsterModel(paramString1, paramString2, paramMonsterElement, paramInt, paramString3);
  }
  
  public boolean equals(Object paramObject)
  {
    if (this != paramObject) {
      if ((paramObject instanceof MonsterModel))
      {
        paramObject = (MonsterModel)paramObject;
        if ((Intrinsics.areEqual(genericName, genericName)) && (Intrinsics.areEqual(awakenName, awakenName)) && (Intrinsics.areEqual(element, element)) && (stars == stars) && (Intrinsics.areEqual(image, image))) {}
      }
      else
      {
        return false;
      }
    }
    return true;
  }
  
  public final String getAwakenName()
  {
    return awakenName;
  }
  
  public final MonsterElement getElement()
  {
    return element;
  }
  
  public final String getGenericName()
  {
    return genericName;
  }
  
  public final String getImage()
  {
    return image;
  }
  
  public final int getStars()
  {
    return stars;
  }
  
  public int hashCode()
  {
    Object localObject = genericName;
    int m = 0;
    int i;
    if (localObject != null) {
      i = localObject.hashCode();
    } else {
      i = 0;
    }
    localObject = awakenName;
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
    int n = stars;
    localObject = image;
    if (localObject != null) {
      m = localObject.hashCode();
    }
    return (((i * 31 + j) * 31 + k) * 31 + n) * 31 + m;
  }
  
  public final void setAwakenName(String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "<set-?>");
    awakenName = paramString;
  }
  
  public final void setElement(MonsterElement paramMonsterElement)
  {
    Intrinsics.checkParameterIsNotNull(paramMonsterElement, "<set-?>");
    element = paramMonsterElement;
  }
  
  public final void setGenericName(String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "<set-?>");
    genericName = paramString;
  }
  
  public final void setImage(String paramString)
  {
    image = paramString;
  }
  
  public final void setStars(int paramInt)
  {
    stars = paramInt;
  }
  
  public String toString()
  {
    String str = genericName;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(str);
    localStringBuilder.append(" (");
    localStringBuilder.append(element);
    localStringBuilder.append(')');
    return localStringBuilder.toString();
  }
}
