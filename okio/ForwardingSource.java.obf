package okio;

import java.io.IOException;

public abstract class ForwardingSource
  implements Source
{
  private final Source delegate;
  
  public ForwardingSource(Source paramSource)
  {
    if (paramSource != null)
    {
      delegate = paramSource;
      return;
    }
    throw new IllegalArgumentException("delegate == null");
  }
  
  public void close()
    throws IOException
  {
    delegate.close();
  }
  
  public final Source delegate()
  {
    return delegate;
  }
  
  public long read(Buffer paramBuffer, long paramLong)
    throws IOException
  {
    return delegate.read(paramBuffer, paramLong);
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
}
