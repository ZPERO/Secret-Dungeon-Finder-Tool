package kotlin.jvm.internal;

import kotlin.reflect.KDeclarationContainer;
import kotlin.reflect.KMutableProperty2.Setter;
import kotlin.reflect.KProperty2.Getter;

public class MutablePropertyReference2Impl
  extends MutablePropertyReference2
{
  private final String name;
  private final KDeclarationContainer owner;
  private final String signature;
  
  public MutablePropertyReference2Impl(KDeclarationContainer paramKDeclarationContainer, String paramString1, String paramString2)
  {
    owner = paramKDeclarationContainer;
    name = paramString1;
    signature = paramString2;
  }
  
  public Object get(Object paramObject1, Object paramObject2)
  {
    return getGetter().call(new Object[] { paramObject1, paramObject2 });
  }
  
  public String getName()
  {
    return name;
  }
  
  public KDeclarationContainer getOwner()
  {
    return owner;
  }
  
  public String getSignature()
  {
    return signature;
  }
  
  public void set(Object paramObject1, Object paramObject2, Object paramObject3)
  {
    getSetter().call(new Object[] { paramObject1, paramObject2, paramObject3 });
  }
}
