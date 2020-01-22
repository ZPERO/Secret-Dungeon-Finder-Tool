package kotlin.jvm.internal;

import kotlin.reflect.KCallable;
import kotlin.reflect.KDeclarationContainer;

public class MutablePropertyReference1Impl
  extends MutablePropertyReference1
{
  private final String name;
  private final KDeclarationContainer owner;
  private final String signature;
  
  public MutablePropertyReference1Impl(KDeclarationContainer paramKDeclarationContainer, String paramString1, String paramString2)
  {
    owner = paramKDeclarationContainer;
    name = paramString1;
    signature = paramString2;
  }
  
  public Object get(Object paramObject)
  {
    return getGetter().call(new Object[] { paramObject });
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
  
  public void set(Object paramObject1, Object paramObject2)
  {
    getSetter().call(new Object[] { paramObject1, paramObject2 });
  }
}
