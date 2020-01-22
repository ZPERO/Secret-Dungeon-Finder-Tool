package kotlin.jvm.internal;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import kotlin.jvm.KotlinReflectionNotSupportedError;
import kotlin.reflect.KAnnotatedElement;
import kotlin.reflect.KCallable;
import kotlin.reflect.KDeclarationContainer;
import kotlin.reflect.KType;
import kotlin.reflect.KVisibility;

public abstract class CallableReference
  implements KCallable, Serializable
{
  public static final Object NO_RECEIVER = NoReceiver.INSTANCE;
  protected final Object receiver;
  private transient KCallable reflected;
  
  public CallableReference()
  {
    this(NO_RECEIVER);
  }
  
  protected CallableReference(Object paramObject)
  {
    receiver = paramObject;
  }
  
  public Object call(Object... paramVarArgs)
  {
    return getReflected().call(paramVarArgs);
  }
  
  public Object callBy(Map paramMap)
  {
    return getReflected().callBy(paramMap);
  }
  
  public KCallable compute()
  {
    KCallable localKCallable2 = reflected;
    KCallable localKCallable1 = localKCallable2;
    if (localKCallable2 == null)
    {
      localKCallable1 = computeReflected();
      reflected = localKCallable1;
    }
    return localKCallable1;
  }
  
  protected abstract KCallable computeReflected();
  
  public List getAnnotations()
  {
    return getReflected().getAnnotations();
  }
  
  public Object getBoundReceiver()
  {
    return receiver;
  }
  
  public String getName()
  {
    throw new AbstractMethodError();
  }
  
  public KDeclarationContainer getOwner()
  {
    throw new AbstractMethodError();
  }
  
  public List getParameters()
  {
    return getReflected().getParameters();
  }
  
  protected KCallable getReflected()
  {
    KCallable localKCallable = compute();
    if (localKCallable != this) {
      return localKCallable;
    }
    throw new KotlinReflectionNotSupportedError();
  }
  
  public KType getReturnType()
  {
    return getReflected().getReturnType();
  }
  
  public String getSignature()
  {
    throw new AbstractMethodError();
  }
  
  public List getTypeParameters()
  {
    return getReflected().getTypeParameters();
  }
  
  public KVisibility getVisibility()
  {
    return getReflected().getVisibility();
  }
  
  public boolean isAbstract()
  {
    return getReflected().isAbstract();
  }
  
  public boolean isFinal()
  {
    return getReflected().isFinal();
  }
  
  public boolean isOpen()
  {
    return getReflected().isOpen();
  }
  
  public boolean isSuspend()
  {
    return getReflected().isSuspend();
  }
  
  private static class NoReceiver
    implements Serializable
  {
    private static final NoReceiver INSTANCE = new NoReceiver();
    
    private NoReceiver() {}
    
    private Object readResolve()
      throws ObjectStreamException
    {
      return INSTANCE;
    }
  }
}
