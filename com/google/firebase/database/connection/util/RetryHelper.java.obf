package com.google.firebase.database.connection.util;

import com.google.firebase.database.logging.LogWrapper;
import com.google.firebase.database.logging.Logger;
import java.util.Random;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class RetryHelper
{
  private long currentRetryDelay;
  private final ScheduledExecutorService executorService;
  private final double jitterFactor;
  private boolean lastWasSuccess = true;
  private final LogWrapper logger;
  private final long maxRetryDelay;
  private final long minRetryDelayAfterFailure;
  private final Random random = new Random();
  private final double retryExponent;
  private ScheduledFuture<?> scheduledRetry;
  
  private RetryHelper(ScheduledExecutorService paramScheduledExecutorService, LogWrapper paramLogWrapper, long paramLong1, long paramLong2, double paramDouble1, double paramDouble2)
  {
    executorService = paramScheduledExecutorService;
    logger = paramLogWrapper;
    minRetryDelayAfterFailure = paramLong1;
    maxRetryDelay = paramLong2;
    retryExponent = paramDouble1;
    jitterFactor = paramDouble2;
  }
  
  public void cancel()
  {
    if (scheduledRetry != null)
    {
      logger.debug("Cancelling existing retry attempt", new Object[0]);
      scheduledRetry.cancel(false);
      scheduledRetry = null;
    }
    else
    {
      logger.debug("No existing retry attempt to cancel", new Object[0]);
    }
    currentRetryDelay = 0L;
  }
  
  public void retry(final Runnable paramRunnable)
  {
    paramRunnable = new Runnable()
    {
      public void run()
      {
        RetryHelper.access$002(RetryHelper.this, null);
        paramRunnable.run();
      }
    };
    if (scheduledRetry != null)
    {
      logger.debug("Cancelling previous scheduled retry", new Object[0]);
      scheduledRetry.cancel(false);
      scheduledRetry = null;
    }
    boolean bool = lastWasSuccess;
    long l = 0L;
    if (!bool)
    {
      l = currentRetryDelay;
      if (l == 0L)
      {
        currentRetryDelay = minRetryDelayAfterFailure;
      }
      else
      {
        d1 = l;
        d2 = retryExponent;
        Double.isNaN(d1);
        currentRetryDelay = Math.min((d1 * d2), maxRetryDelay);
      }
      double d1 = jitterFactor;
      l = currentRetryDelay;
      double d2 = l;
      Double.isNaN(d2);
      double d3 = l;
      Double.isNaN(d3);
      l = ((1.0D - d1) * d2 + d1 * d3 * random.nextDouble());
    }
    lastWasSuccess = false;
    logger.debug("Scheduling retry in %dms", new Object[] { Long.valueOf(l) });
    scheduledRetry = executorService.schedule(paramRunnable, l, TimeUnit.MILLISECONDS);
  }
  
  public void setMaxDelay()
  {
    currentRetryDelay = maxRetryDelay;
  }
  
  public void signalSuccess()
  {
    lastWasSuccess = true;
    currentRetryDelay = 0L;
  }
  
  public static class Builder
  {
    private double jitterFactor = 0.5D;
    private final LogWrapper logger;
    private long minRetryDelayAfterFailure = 1000L;
    private double retryExponent = 1.3D;
    private long retryMaxDelay = 30000L;
    private final ScheduledExecutorService service;
    
    public Builder(ScheduledExecutorService paramScheduledExecutorService, Logger paramLogger, String paramString)
    {
      service = paramScheduledExecutorService;
      logger = new LogWrapper(paramLogger, paramString);
    }
    
    public RetryHelper build()
    {
      return new RetryHelper(service, logger, minRetryDelayAfterFailure, retryMaxDelay, retryExponent, jitterFactor, null);
    }
    
    public Builder withJitterFactor(double paramDouble)
    {
      if ((paramDouble >= 0.0D) && (paramDouble <= 1.0D))
      {
        jitterFactor = paramDouble;
        return this;
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Argument out of range: ");
      localStringBuilder.append(paramDouble);
      throw new IllegalArgumentException(localStringBuilder.toString());
    }
    
    public Builder withMaxDelay(long paramLong)
    {
      retryMaxDelay = paramLong;
      return this;
    }
    
    public Builder withMinDelayAfterFailure(long paramLong)
    {
      minRetryDelayAfterFailure = paramLong;
      return this;
    }
    
    public Builder withRetryExponent(double paramDouble)
    {
      retryExponent = paramDouble;
      return this;
    }
  }
}
