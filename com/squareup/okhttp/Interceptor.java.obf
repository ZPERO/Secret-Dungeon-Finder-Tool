package com.squareup.okhttp;

import java.io.IOException;

public abstract interface Interceptor
{
  public abstract Response intercept(Chain paramChain)
    throws IOException;
  
  public static abstract interface Chain
  {
    public abstract Connection connection();
    
    public abstract Response proceed(Request paramRequest)
      throws IOException;
    
    public abstract Request request();
  }
}
