package okio;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class ByteString
  implements Serializable, Comparable<ByteString>
{
  public static final ByteString EMPTY = of(new byte[0]);
  static final char[] HEX_DIGITS = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102 };
  private static final long serialVersionUID = 1L;
  final byte[] data;
  transient int hashCode;
  transient String utf8;
  
  ByteString(byte[] paramArrayOfByte)
  {
    data = paramArrayOfByte;
  }
  
  static int codePointIndexToCharIndex(String paramString, int paramInt)
  {
    int k = paramString.length();
    int i = 0;
    int j = 0;
    while (i < k)
    {
      if (j == paramInt) {
        return i;
      }
      int m = paramString.codePointAt(i);
      if (((Character.isISOControl(m)) && (m != 10) && (m != 13)) || (m == 65533)) {
        return -1;
      }
      j += 1;
      i += Character.charCount(m);
    }
    return paramString.length();
  }
  
  public static ByteString decodeBase64(String paramString)
  {
    if (paramString != null)
    {
      paramString = Base64.decode(paramString);
      if (paramString != null) {
        return new ByteString(paramString);
      }
      return null;
    }
    throw new IllegalArgumentException("base64 == null");
  }
  
  public static ByteString decodeHex(String paramString)
  {
    if (paramString != null)
    {
      if (paramString.length() % 2 == 0)
      {
        localObject = new byte[paramString.length() / 2];
        int i = 0;
        while (i < localObject.length)
        {
          int j = i * 2;
          localObject[i] = ((byte)((decodeHexDigit(paramString.charAt(j)) << 4) + decodeHexDigit(paramString.charAt(j + 1))));
          i += 1;
        }
        return of((byte[])localObject);
      }
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("Unexpected hex string: ");
      ((StringBuilder)localObject).append(paramString);
      throw new IllegalArgumentException(((StringBuilder)localObject).toString());
    }
    paramString = new IllegalArgumentException("hex == null");
    throw paramString;
  }
  
  private static int decodeHexDigit(char paramChar)
  {
    if ((paramChar >= '0') && (paramChar <= '9')) {
      return paramChar - '0';
    }
    char c = 'a';
    if ((paramChar >= 'a') && (paramChar <= 'f')) {}
    do
    {
      return paramChar - c + 10;
      c = 'A';
    } while ((paramChar >= 'A') && (paramChar <= 'F'));
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Unexpected hex digit: ");
    ((StringBuilder)localObject).append(paramChar);
    localObject = new IllegalArgumentException(((StringBuilder)localObject).toString());
    throw ((Throwable)localObject);
  }
  
  private ByteString digest(String paramString)
  {
    try
    {
      paramString = MessageDigest.getInstance(paramString);
      byte[] arrayOfByte = data;
      paramString = of(paramString.digest(arrayOfByte));
      return paramString;
    }
    catch (NoSuchAlgorithmException paramString)
    {
      throw new AssertionError(paramString);
    }
  }
  
  public static ByteString encodeString(String paramString, Charset paramCharset)
  {
    if (paramString != null)
    {
      if (paramCharset != null) {
        return new ByteString(paramString.getBytes(paramCharset));
      }
      throw new IllegalArgumentException("charset == null");
    }
    throw new IllegalArgumentException("s == null");
  }
  
  public static ByteString encodeUtf8(String paramString)
  {
    if (paramString != null)
    {
      ByteString localByteString = new ByteString(paramString.getBytes(Util.UTF_8));
      utf8 = paramString;
      return localByteString;
    }
    throw new IllegalArgumentException("s == null");
  }
  
  private ByteString hmac(String paramString, ByteString paramByteString)
  {
    try
    {
      Mac localMac = Mac.getInstance(paramString);
      localMac.init(new SecretKeySpec(paramByteString.toByteArray(), paramString));
      paramString = data;
      paramString = of(localMac.doFinal(paramString));
      return paramString;
    }
    catch (InvalidKeyException paramString)
    {
      throw new IllegalArgumentException(paramString);
    }
    catch (NoSuchAlgorithmException paramString)
    {
      throw new AssertionError(paramString);
    }
  }
  
  public static ByteString of(byte... paramVarArgs)
  {
    if (paramVarArgs != null) {
      return new ByteString((byte[])paramVarArgs.clone());
    }
    throw new IllegalArgumentException("data == null");
  }
  
  public static ByteString of(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    if (paramArrayOfByte != null)
    {
      Util.checkOffsetAndCount(paramArrayOfByte.length, paramInt1, paramInt2);
      byte[] arrayOfByte = new byte[paramInt2];
      System.arraycopy(paramArrayOfByte, paramInt1, arrayOfByte, 0, paramInt2);
      return new ByteString(arrayOfByte);
    }
    throw new IllegalArgumentException("data == null");
  }
  
  public static ByteString read(InputStream paramInputStream, int paramInt)
    throws IOException
  {
    if (paramInputStream != null)
    {
      if (paramInt >= 0)
      {
        byte[] arrayOfByte = new byte[paramInt];
        int i = 0;
        while (i < paramInt)
        {
          int j = paramInputStream.read(arrayOfByte, i, paramInt - i);
          if (j != -1) {
            i += j;
          } else {
            throw new EOFException();
          }
        }
        return new ByteString(arrayOfByte);
      }
      paramInputStream = new StringBuilder();
      paramInputStream.append("byteCount < 0: ");
      paramInputStream.append(paramInt);
      throw new IllegalArgumentException(paramInputStream.toString());
    }
    paramInputStream = new IllegalArgumentException("in == null");
    throw paramInputStream;
  }
  
  public static ByteString read(ByteBuffer paramByteBuffer)
  {
    if (paramByteBuffer != null)
    {
      byte[] arrayOfByte = new byte[paramByteBuffer.remaining()];
      paramByteBuffer.get(arrayOfByte);
      return new ByteString(arrayOfByte);
    }
    throw new IllegalArgumentException("data == null");
  }
  
  private void readObject(ObjectInputStream paramObjectInputStream)
    throws IOException
  {
    Object localObject = read(paramObjectInputStream, paramObjectInputStream.readInt());
    try
    {
      paramObjectInputStream = ByteString.class.getDeclaredField("data");
      paramObjectInputStream.setAccessible(true);
      localObject = data;
      paramObjectInputStream.set(this, localObject);
      return;
    }
    catch (NoSuchFieldException paramObjectInputStream)
    {
      for (;;) {}
    }
    catch (IllegalAccessException paramObjectInputStream)
    {
      for (;;) {}
    }
    throw new AssertionError();
    throw new AssertionError();
  }
  
  private void writeObject(ObjectOutputStream paramObjectOutputStream)
    throws IOException
  {
    paramObjectOutputStream.writeInt(data.length);
    paramObjectOutputStream.write(data);
  }
  
  public ByteBuffer asByteBuffer()
  {
    return ByteBuffer.wrap(data).asReadOnlyBuffer();
  }
  
  public String base64()
  {
    return Base64.encode(data);
  }
  
  public String base64Url()
  {
    return Base64.encodeUrl(data);
  }
  
  public int compareTo(ByteString paramByteString)
  {
    int j = size();
    int k = paramByteString.size();
    int m = Math.min(j, k);
    int i = 0;
    while (i < m)
    {
      int n = getByte(i) & 0xFF;
      int i1 = paramByteString.getByte(i) & 0xFF;
      if (n == i1)
      {
        i += 1;
      }
      else
      {
        if (n < i1) {
          return -1;
        }
        return 1;
      }
    }
    if (j == k) {
      return 0;
    }
    if (j < k) {
      return -1;
    }
    return 1;
  }
  
  public final boolean endsWith(ByteString paramByteString)
  {
    return rangeEquals(size() - paramByteString.size(), paramByteString, 0, paramByteString.size());
  }
  
  public final boolean endsWith(byte[] paramArrayOfByte)
  {
    return rangeEquals(size() - paramArrayOfByte.length, paramArrayOfByte, 0, paramArrayOfByte.length);
  }
  
  public boolean equals(Object paramObject)
  {
    if (paramObject == this) {
      return true;
    }
    if ((paramObject instanceof ByteString))
    {
      paramObject = (ByteString)paramObject;
      int i = paramObject.size();
      byte[] arrayOfByte = data;
      if ((i == arrayOfByte.length) && (paramObject.rangeEquals(0, arrayOfByte, 0, arrayOfByte.length))) {
        return true;
      }
    }
    return false;
  }
  
  public byte getByte(int paramInt)
  {
    return data[paramInt];
  }
  
  public int hashCode()
  {
    int i = hashCode;
    if (i != 0) {
      return i;
    }
    i = Arrays.hashCode(data);
    hashCode = i;
    return i;
  }
  
  public String hex()
  {
    byte[] arrayOfByte = data;
    char[] arrayOfChar1 = new char[arrayOfByte.length * 2];
    int k = arrayOfByte.length;
    int i = 0;
    int j = 0;
    while (i < k)
    {
      int m = arrayOfByte[i];
      int n = j + 1;
      char[] arrayOfChar2 = HEX_DIGITS;
      arrayOfChar1[j] = arrayOfChar2[(m >> 4 & 0xF)];
      j = n + 1;
      arrayOfChar1[n] = arrayOfChar2[(m & 0xF)];
      i += 1;
    }
    return new String(arrayOfChar1);
  }
  
  public ByteString hmacSha1(ByteString paramByteString)
  {
    return hmac("HmacSHA1", paramByteString);
  }
  
  public ByteString hmacSha256(ByteString paramByteString)
  {
    return hmac("HmacSHA256", paramByteString);
  }
  
  public ByteString hmacSha512(ByteString paramByteString)
  {
    return hmac("HmacSHA512", paramByteString);
  }
  
  public final int indexOf(ByteString paramByteString)
  {
    return indexOf(paramByteString.internalArray(), 0);
  }
  
  public final int indexOf(ByteString paramByteString, int paramInt)
  {
    return indexOf(paramByteString.internalArray(), paramInt);
  }
  
  public final int indexOf(byte[] paramArrayOfByte)
  {
    return indexOf(paramArrayOfByte, 0);
  }
  
  public int indexOf(byte[] paramArrayOfByte, int paramInt)
  {
    paramInt = Math.max(paramInt, 0);
    int i = data.length;
    int j = paramArrayOfByte.length;
    while (paramInt <= i - j)
    {
      if (Util.arrayRangeEquals(data, paramInt, paramArrayOfByte, 0, paramArrayOfByte.length)) {
        return paramInt;
      }
      paramInt += 1;
    }
    return -1;
  }
  
  byte[] internalArray()
  {
    return data;
  }
  
  public final int lastIndexOf(ByteString paramByteString)
  {
    return lastIndexOf(paramByteString.internalArray(), size());
  }
  
  public final int lastIndexOf(ByteString paramByteString, int paramInt)
  {
    return lastIndexOf(paramByteString.internalArray(), paramInt);
  }
  
  public final int lastIndexOf(byte[] paramArrayOfByte)
  {
    return lastIndexOf(paramArrayOfByte, size());
  }
  
  public int lastIndexOf(byte[] paramArrayOfByte, int paramInt)
  {
    paramInt = Math.min(paramInt, data.length - paramArrayOfByte.length);
    while (paramInt >= 0)
    {
      if (Util.arrayRangeEquals(data, paramInt, paramArrayOfByte, 0, paramArrayOfByte.length)) {
        return paramInt;
      }
      paramInt -= 1;
    }
    return -1;
  }
  
  public ByteString md5()
  {
    return digest("MD5");
  }
  
  public boolean rangeEquals(int paramInt1, ByteString paramByteString, int paramInt2, int paramInt3)
  {
    return paramByteString.rangeEquals(paramInt2, data, paramInt1, paramInt3);
  }
  
  public boolean rangeEquals(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
  {
    if (paramInt1 >= 0)
    {
      byte[] arrayOfByte = data;
      if ((paramInt1 <= arrayOfByte.length - paramInt3) && (paramInt2 >= 0) && (paramInt2 <= paramArrayOfByte.length - paramInt3) && (Util.arrayRangeEquals(arrayOfByte, paramInt1, paramArrayOfByte, paramInt2, paramInt3))) {
        return true;
      }
    }
    return false;
  }
  
  public ByteString sha1()
  {
    return digest("SHA-1");
  }
  
  public ByteString sha256()
  {
    return digest("SHA-256");
  }
  
  public ByteString sha512()
  {
    return digest("SHA-512");
  }
  
  public int size()
  {
    return data.length;
  }
  
  public final boolean startsWith(ByteString paramByteString)
  {
    return rangeEquals(0, paramByteString, 0, paramByteString.size());
  }
  
  public final boolean startsWith(byte[] paramArrayOfByte)
  {
    return rangeEquals(0, paramArrayOfByte, 0, paramArrayOfByte.length);
  }
  
  public String string(Charset paramCharset)
  {
    if (paramCharset != null) {
      return new String(data, paramCharset);
    }
    throw new IllegalArgumentException("charset == null");
  }
  
  public ByteString substring(int paramInt)
  {
    return substring(paramInt, data.length);
  }
  
  public ByteString substring(int paramInt1, int paramInt2)
  {
    if (paramInt1 >= 0)
    {
      Object localObject = data;
      if (paramInt2 <= localObject.length)
      {
        int i = paramInt2 - paramInt1;
        if (i >= 0)
        {
          if ((paramInt1 == 0) && (paramInt2 == localObject.length)) {
            return this;
          }
          localObject = new byte[i];
          System.arraycopy(data, paramInt1, localObject, 0, i);
          return new ByteString((byte[])localObject);
        }
        throw new IllegalArgumentException("endIndex < beginIndex");
      }
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("endIndex > length(");
      ((StringBuilder)localObject).append(data.length);
      ((StringBuilder)localObject).append(")");
      throw new IllegalArgumentException(((StringBuilder)localObject).toString());
    }
    throw new IllegalArgumentException("beginIndex < 0");
  }
  
  public ByteString toAsciiLowercase()
  {
    int i = 0;
    for (;;)
    {
      byte[] arrayOfByte = data;
      if (i >= arrayOfByte.length) {
        break;
      }
      int k = arrayOfByte[i];
      if ((k >= 65) && (k <= 90))
      {
        arrayOfByte = (byte[])arrayOfByte.clone();
        int j = i + 1;
        arrayOfByte[i] = ((byte)(k + 32));
        i = j;
        while (i < arrayOfByte.length)
        {
          j = arrayOfByte[i];
          if ((j >= 65) && (j <= 90)) {
            arrayOfByte[i] = ((byte)(j + 32));
          }
          i += 1;
        }
        return new ByteString(arrayOfByte);
      }
      i += 1;
    }
    return this;
  }
  
  public ByteString toAsciiUppercase()
  {
    int i = 0;
    for (;;)
    {
      byte[] arrayOfByte = data;
      if (i >= arrayOfByte.length) {
        break;
      }
      int k = arrayOfByte[i];
      if ((k >= 97) && (k <= 122))
      {
        arrayOfByte = (byte[])arrayOfByte.clone();
        int j = i + 1;
        arrayOfByte[i] = ((byte)(k - 32));
        i = j;
        while (i < arrayOfByte.length)
        {
          j = arrayOfByte[i];
          if ((j >= 97) && (j <= 122)) {
            arrayOfByte[i] = ((byte)(j - 32));
          }
          i += 1;
        }
        return new ByteString(arrayOfByte);
      }
      i += 1;
    }
    return this;
  }
  
  public byte[] toByteArray()
  {
    return (byte[])data.clone();
  }
  
  public String toString()
  {
    if (data.length == 0) {
      return "[size=0]";
    }
    Object localObject2 = utf8();
    int i = codePointIndexToCharIndex((String)localObject2, 64);
    if (i == -1)
    {
      if (data.length <= 64)
      {
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("[hex=");
        ((StringBuilder)localObject1).append(hex());
        ((StringBuilder)localObject1).append("]");
        return ((StringBuilder)localObject1).toString();
      }
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("[size=");
      ((StringBuilder)localObject1).append(data.length);
      ((StringBuilder)localObject1).append(" hex=");
      ((StringBuilder)localObject1).append(substring(0, 64).hex());
      ((StringBuilder)localObject1).append("?]");
      return ((StringBuilder)localObject1).toString();
    }
    Object localObject1 = ((String)localObject2).substring(0, i).replace("\\", "\\\\").replace("\n", "\\n").replace("\r", "\\r");
    if (i < ((String)localObject2).length())
    {
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("[size=");
      ((StringBuilder)localObject2).append(data.length);
      ((StringBuilder)localObject2).append(" text=");
      ((StringBuilder)localObject2).append((String)localObject1);
      ((StringBuilder)localObject2).append("?]");
      return ((StringBuilder)localObject2).toString();
    }
    localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append("[text=");
    ((StringBuilder)localObject2).append((String)localObject1);
    ((StringBuilder)localObject2).append("]");
    return ((StringBuilder)localObject2).toString();
  }
  
  public String utf8()
  {
    String str = utf8;
    if (str != null) {
      return str;
    }
    str = new String(data, Util.UTF_8);
    utf8 = str;
    return str;
  }
  
  public void write(OutputStream paramOutputStream)
    throws IOException
  {
    if (paramOutputStream != null)
    {
      paramOutputStream.write(data);
      return;
    }
    throw new IllegalArgumentException("out == null");
  }
  
  void write(Buffer paramBuffer)
  {
    byte[] arrayOfByte = data;
    paramBuffer.write(arrayOfByte, 0, arrayOfByte.length);
  }
}
