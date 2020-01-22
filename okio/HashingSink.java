package okio;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.annotation.Nullable;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public final class HashingSink
  extends ForwardingSink
{
  @Nullable
  private final Mac data;
  @Nullable
  private final MessageDigest messageDigest;
  
  private HashingSink(Sink paramSink, String paramString)
  {
    super(paramSink);
    try
    {
      paramSink = MessageDigest.getInstance(paramString);
      messageDigest = paramSink;
      data = null;
      return;
    }
    catch (NoSuchAlgorithmException paramSink)
    {
      for (;;) {}
    }
    throw new AssertionError();
  }
  
  private HashingSink(Sink paramSink, ByteString paramByteString, String paramString)
  {
    super(paramSink);
    try
    {
      paramSink = Mac.getInstance(paramString);
      data = paramSink;
      paramSink = data;
      paramSink.init(new SecretKeySpec(paramByteString.toByteArray(), paramString));
      messageDigest = null;
      return;
    }
    catch (InvalidKeyException paramSink)
    {
      throw new IllegalArgumentException(paramSink);
      throw new AssertionError();
    }
    catch (NoSuchAlgorithmException paramSink)
    {
      for (;;) {}
    }
  }
  
  public static HashingSink handshake(Sink paramSink)
  {
    return new HashingSink(paramSink, "MD5");
  }
  
  public static HashingSink hmacSha1(Sink paramSink, ByteString paramByteString)
  {
    return new HashingSink(paramSink, paramByteString, "HmacSHA1");
  }
  
  public static HashingSink hmacSha256(Sink paramSink, ByteString paramByteString)
  {
    return new HashingSink(paramSink, paramByteString, "HmacSHA256");
  }
  
  public static HashingSink hmacSha512(Sink paramSink, ByteString paramByteString)
  {
    return new HashingSink(paramSink, paramByteString, "HmacSHA512");
  }
  
  public static HashingSink sha1(Sink paramSink)
  {
    return new HashingSink(paramSink, "SHA-1");
  }
  
  public static HashingSink sha256(Sink paramSink)
  {
    return new HashingSink(paramSink, "SHA-256");
  }
  
  public static HashingSink sha512(Sink paramSink)
  {
    return new HashingSink(paramSink, "SHA-512");
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
  
  public void write(Buffer paramBuffer, long paramLong)
    throws IOException
  {
    Util.checkOffsetAndCount(size, 0L, paramLong);
    Segment localSegment = head;
    long l = 0L;
    while (l < paramLong)
    {
      int i = (int)Math.min(paramLong - l, limit - pos);
      MessageDigest localMessageDigest = messageDigest;
      if (localMessageDigest != null) {
        localMessageDigest.update(data, pos, i);
      } else {
        data.update(data, pos, i);
      }
      l += i;
      localSegment = next;
    }
    super.write(paramBuffer, paramLong);
  }
}
