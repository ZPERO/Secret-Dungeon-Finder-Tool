package okio;

import java.io.IOException;

public abstract class ForwardingSink
  implements Sink
{
  private final Sink delegate;
  
  public ForwardingSink(Sink paramSink)
  {
    if (paramSink != null)
    {
      delegate = paramSink;
      return;
    }
    throw new IllegalArgumentException("delegate == null");
  }
  
  public void close()
    throws IOException
  {
    delegate.close();
  }
  
  public final Sink delegate()
  {
    return delegate;
  }
  
  public void flush()
    throws IOException
  {
    delegate.flush();
  }
  
  public Timeout timeout()
  {
    return delegate.timeout();
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(getClass().getSimpleName());
    localStringBuilder.append("(");
    localStringBuilder.append(delegate.toString());
    localStringBuilder.append(")");
    return localStringBuilder.toString();
  }
  
  public void write(Buffer paramBuffer, long paramLong)
    throws IOException
  {
    delegate.write(paramBuffer, paramLong);
  }
}
