package androidx.core.util;

public class Pair<F, S>
{
  public final F first;
  public final S second;
  
  public Pair(Object paramObject1, Object paramObject2)
  {
    first = paramObject1;
    second = paramObject2;
  }
  
  public static Pair create(Object paramObject1, Object paramObject2)
  {
    return new Pair(paramObject1, paramObject2);
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof Pair)) {
      return false;
    }
    paramObject = (Pair)paramObject;
    return (ObjectsCompat.equals(first, first)) && (ObjectsCompat.equals(second, second));
  }
  
  public int hashCode()
  {
    Object localObject = first;
    int j = 0;
    int i;
    if (localObject == null) {
      i = 0;
    } else {
      i = localObject.hashCode();
    }
    localObject = second;
    if (localObject != null) {
      j = localObject.hashCode();
    }
    return i ^ j;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Pair{");
    localStringBuilder.append(String.valueOf(first));
    localStringBuilder.append(" ");
    localStringBuilder.append(String.valueOf(second));
    localStringBuilder.append("}");
    return localStringBuilder.toString();
  }
}
