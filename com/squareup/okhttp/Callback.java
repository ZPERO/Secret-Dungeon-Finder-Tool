package com.squareup.okhttp;

import java.io.IOException;

public abstract interface Callback
{
  public abstract void onFailure(Request paramRequest, IOException paramIOException);
  
  public abstract void onResponse(Response paramResponse)
    throws IOException;
}
