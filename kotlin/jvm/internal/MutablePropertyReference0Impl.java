package kotlin.jvm.internal;

import kotlin.reflect.KCallable;
import kotlin.reflect.KDeclarationContainer;

public class MutablePropertyReference0Impl
  extends MutablePropertyReference0
{
  private final String name;
  private final KDeclarationContainer owner;
  private final String signature;
  
  public MutablePropertyReference0Impl(KDeclarationContainer paramKDeclarationContainer, String paramString1, String paramString2)
  {
    owner = paramKDeclarationContainer;
    name = paramString1;
    signature = paramString2;
  }
  
  public Object get()
  {
    return getGetter().call(new Object[0]);
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
  
  public void set(Object paramObject)
  {
    getSetter().call(new Object[] { paramObject });
  }
}
