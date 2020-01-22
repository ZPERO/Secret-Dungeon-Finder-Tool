package com.squareup.okhttp.internal.framed;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public final class Ping
{
  private final CountDownLatch latch = new CountDownLatch(1);
  private long received = -1L;
  private long sent = -1L;
  
  Ping() {}
  
  void cancel()
  {
    if (received == -1L)
    {
      long l = sent;
      if (l != -1L)
      {
        received = (l - 1L);
        latch.countDown();
        return;
      }
    }
    throw new IllegalStateException();
  }
  
  void receive()
  {
    if ((received == -1L) && (sent != -1L))
    {
      received = System.nanoTime();
      latch.countDown();
      return;
    }
    throw new IllegalStateException();
  }
  
  public long roundTripTime()
    throws InterruptedException
  {
    latch.await();
    return received - sent;
  }
  
  public long roundTripTime(long paramLong, TimeUnit paramTimeUnit)
    throws InterruptedException
  {
    if (latch.await(paramLong, paramTimeUnit)) {
      return received - sent;
    }
    return -2L;
  }
  
  void send()
  {
    if (sent == -1L)
    {
      sent = System.nanoTime();
      return;
    }
    throw new IllegalStateException();
  }
}
