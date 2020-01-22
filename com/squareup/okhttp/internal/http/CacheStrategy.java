package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.CacheControl;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Request.Builder;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.Response.Builder;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public final class CacheStrategy
{
  public final Response cacheResponse;
  public final Request networkRequest;
  
  private CacheStrategy(Request paramRequest, Response paramResponse)
  {
    networkRequest = paramRequest;
    cacheResponse = paramResponse;
  }
  
  public static boolean isCacheable(Response paramResponse, Request paramRequest)
  {
    int i = paramResponse.code();
    if ((i != 200) && (i != 410) && (i != 414) && (i != 501) && (i != 203) && (i != 204)) {
      if (i != 307)
      {
        if ((i == 308) || (i == 404) || (i == 405)) {}
      }
      else {
        switch (i)
        {
        default: 
          return false;
        case 302: 
          if ((paramResponse.header("Expires") == null) && (paramResponse.cacheControl().maxAgeSeconds() == -1) && (!paramResponse.cacheControl().isPublic()) && (!paramResponse.cacheControl().isPrivate())) {
            return false;
          }
          break;
        }
      }
    }
    return (!paramResponse.cacheControl().noStore()) && (!paramRequest.cacheControl().noStore());
  }
  
  public static class Factory
  {
    private int ageSeconds = -1;
    final Response cacheResponse;
    private String etag;
    private Date expires;
    private Date lastModified;
    private String lastModifiedString;
    final long nowMillis;
    private long receivedResponseMillis;
    final Request request;
    private long sentRequestMillis;
    private Date servedDate;
    private String servedDateString;
    
    public Factory(long paramLong, Request paramRequest, Response paramResponse)
    {
      nowMillis = paramLong;
      request = paramRequest;
      cacheResponse = paramResponse;
      if (paramResponse != null)
      {
        paramRequest = paramResponse.headers();
        int i = 0;
        int j = paramRequest.size();
        while (i < j)
        {
          paramResponse = paramRequest.name(i);
          String str = paramRequest.value(i);
          if ("Date".equalsIgnoreCase(paramResponse))
          {
            servedDate = HttpDate.parse(str);
            servedDateString = str;
          }
          else if ("Expires".equalsIgnoreCase(paramResponse))
          {
            expires = HttpDate.parse(str);
          }
          else if ("Last-Modified".equalsIgnoreCase(paramResponse))
          {
            lastModified = HttpDate.parse(str);
            lastModifiedString = str;
          }
          else if ("ETag".equalsIgnoreCase(paramResponse))
          {
            etag = str;
          }
          else if ("Age".equalsIgnoreCase(paramResponse))
          {
            ageSeconds = HeaderParser.parseSeconds(str, -1);
          }
          else if (OkHeaders.SENT_MILLIS.equalsIgnoreCase(paramResponse))
          {
            sentRequestMillis = Long.parseLong(str);
          }
          else if (OkHeaders.RECEIVED_MILLIS.equalsIgnoreCase(paramResponse))
          {
            receivedResponseMillis = Long.parseLong(str);
          }
          i += 1;
        }
      }
    }
    
    private long cacheResponseAge()
    {
      Date localDate = servedDate;
      long l1 = 0L;
      if (localDate != null) {
        l1 = Math.max(0L, receivedResponseMillis - localDate.getTime());
      }
      long l2 = l1;
      if (ageSeconds != -1) {
        l2 = Math.max(l1, TimeUnit.SECONDS.toMillis(ageSeconds));
      }
      l1 = receivedResponseMillis;
      return l2 + (l1 - sentRequestMillis) + (nowMillis - l1);
    }
    
    private long computeFreshnessLifetime()
    {
      Object localObject = cacheResponse.cacheControl();
      if (((CacheControl)localObject).maxAgeSeconds() != -1) {
        return TimeUnit.SECONDS.toMillis(((CacheControl)localObject).maxAgeSeconds());
      }
      long l;
      if (expires != null)
      {
        localObject = servedDate;
        if (localObject != null) {
          l = ((Date)localObject).getTime();
        } else {
          l = receivedResponseMillis;
        }
        l = expires.getTime() - l;
        if (l > 0L) {
          return l;
        }
      }
      else if ((lastModified != null) && (cacheResponse.request().httpUrl().query() == null))
      {
        localObject = servedDate;
        if (localObject != null) {
          l = ((Date)localObject).getTime();
        } else {
          l = sentRequestMillis;
        }
        l -= lastModified.getTime();
        if (l > 0L) {
          return l / 10L;
        }
      }
      return 0L;
    }
    
    private CacheStrategy getCandidate()
    {
      if (cacheResponse == null) {
        return new CacheStrategy(request, null, null);
      }
      if ((request.isHttps()) && (cacheResponse.handshake() == null)) {
        return new CacheStrategy(request, null, null);
      }
      if (!CacheStrategy.isCacheable(cacheResponse, request)) {
        return new CacheStrategy(request, null, null);
      }
      Object localObject1 = request.cacheControl();
      if ((!((CacheControl)localObject1).noCache()) && (!hasConditions(request)))
      {
        long l5 = cacheResponseAge();
        long l2 = computeFreshnessLifetime();
        long l1 = l2;
        if (((CacheControl)localObject1).maxAgeSeconds() != -1) {
          l1 = Math.min(l2, TimeUnit.SECONDS.toMillis(((CacheControl)localObject1).maxAgeSeconds()));
        }
        int i = ((CacheControl)localObject1).minFreshSeconds();
        long l4 = 0L;
        if (i != -1) {
          l2 = TimeUnit.SECONDS.toMillis(((CacheControl)localObject1).minFreshSeconds());
        } else {
          l2 = 0L;
        }
        Object localObject2 = cacheResponse.cacheControl();
        long l3 = l4;
        if (!((CacheControl)localObject2).mustRevalidate())
        {
          l3 = l4;
          if (((CacheControl)localObject1).maxStaleSeconds() != -1) {
            l3 = TimeUnit.SECONDS.toMillis(((CacheControl)localObject1).maxStaleSeconds());
          }
        }
        if (!((CacheControl)localObject2).noCache())
        {
          l2 += l5;
          if (l2 < l3 + l1)
          {
            localObject1 = cacheResponse.newBuilder();
            if (l2 >= l1) {
              ((Response.Builder)localObject1).addHeader("Warning", "110 HttpURLConnection \"Response is stale\"");
            }
            if ((l5 > 86400000L) && (isFreshnessLifetimeHeuristic())) {
              ((Response.Builder)localObject1).addHeader("Warning", "113 HttpURLConnection \"Heuristic expiration\"");
            }
            return new CacheStrategy(null, ((Response.Builder)localObject1).build(), null);
          }
        }
        localObject1 = request.newBuilder();
        localObject2 = etag;
        if (localObject2 != null) {
          ((Request.Builder)localObject1).header("If-None-Match", (String)localObject2);
        } else if (lastModified != null) {
          ((Request.Builder)localObject1).header("If-Modified-Since", lastModifiedString);
        } else if (servedDate != null) {
          ((Request.Builder)localObject1).header("If-Modified-Since", servedDateString);
        }
        localObject1 = ((Request.Builder)localObject1).build();
        if (hasConditions((Request)localObject1)) {
          return new CacheStrategy((Request)localObject1, cacheResponse, null);
        }
        return new CacheStrategy((Request)localObject1, null, null);
      }
      return new CacheStrategy(request, null, null);
    }
    
    private static boolean hasConditions(Request paramRequest)
    {
      return (paramRequest.header("If-Modified-Since") != null) || (paramRequest.header("If-None-Match") != null);
    }
    
    private boolean isFreshnessLifetimeHeuristic()
    {
      return (cacheResponse.cacheControl().maxAgeSeconds() == -1) && (expires == null);
    }
    
    public CacheStrategy get()
    {
      CacheStrategy localCacheStrategy2 = getCandidate();
      CacheStrategy localCacheStrategy1 = localCacheStrategy2;
      if (networkRequest != null)
      {
        localCacheStrategy1 = localCacheStrategy2;
        if (request.cacheControl().onlyIfCached()) {
          localCacheStrategy1 = new CacheStrategy(null, null, null);
        }
      }
      return localCacheStrategy1;
    }
  }
}
