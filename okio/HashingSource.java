package okio;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public final class HashingSource
  extends ForwardingSource
{
  private final Mac data;
  private final MessageDigest messageDigest;
  
  private HashingSource(Source paramSource, String paramString)
  {
    super(paramSource);
    try
    {
      paramSource = MessageDigest.getInstance(paramString);
      messageDigest = paramSource;
      data = null;
      return;
    }
    catch (NoSuchAlgorithmException paramSource)
    {
      for (;;) {}
    }
    throw new AssertionError();
  }
  
  private HashingSource(Source paramSource, ByteString paramByteString, String paramString)
  {
    super(paramSource);
    try
    {
      paramSource = Mac.getInstance(paramString);
      data = paramSource;
      paramSource = data;
      paramSource.init(new SecretKeySpec(paramByteString.toByteArray(), paramString));
      messageDigest = null;
      return;
    }
    catch (InvalidKeyException paramSource)
    {
      throw new IllegalArgumentException(paramSource);
      throw new AssertionError();
    }
    catch (NoSuchAlgorithmException paramSource)
    {
      for (;;) {}
    }
  }
  
  public static HashingSource addSource(Source paramSource)
  {
    return new HashingSource(paramSource, "MD5");
  }
  
  public static HashingSource hmacSha1(Source paramSource, ByteString paramByteString)
  {
    return new HashingSource(paramSource, paramByteString, "HmacSHA1");
  }
  
  public static HashingSource hmacSha256(Source paramSource, ByteString paramByteString)
  {
    return new HashingSource(paramSource, paramByteString, "HmacSHA256");
  }
  
  public static HashingSource sha1(Source paramSource)
  {
    return new HashingSource(paramSource, "SHA-1");
  }
  
  public static HashingSource sha256(Source paramSource)
  {
    return new HashingSource(paramSource, "SHA-256");
  }
  
  public final ByteString hash()
  {
    Object localObject = messageDigest;
    if (localObject != null) {
      localObject = ((MessageDigest)localObject).digest();
    } else {
      localObject = data.doFinal();
    }
    return ByteString.of((byte[])localObject);
  }
  
  public long read(Buffer paramBuffer, long paramLong)
    throws IOException
  {
    long l4 = super.read(paramBuffer, paramLong);
    if (l4 != -1L)
    {
      long l3 = size - l4;
      paramLong = size;
      Object localObject1 = head;
      long l1;
      long l2;
      Object localObject2;
      for (;;)
      {
        l1 = paramLong;
        l2 = l3;
        localObject2 = localObject1;
        if (paramLong <= l3) {
          break;
        }
        localObject1 = prev;
        paramLong -= limit - pos;
      }
      while (l1 < size)
      {
        int i = (int)(pos + l2 - l1);
        localObject1 = messageDigest;
        if (localObject1 != null) {
          ((MessageDigest)localObject1).update(data, i, limit - i);
        } else {
          data.update(data, i, limit - i);
        }
        l2 = limit - pos + l1;
        localObject2 = next;
        l1 = l2;
      }
    }
    return l4;
  }
}
