package com.squareup.okhttp.internal.framed;

import okio.ByteString;

public final class Header
{
  public static final ByteString RESPONSE_STATUS = ByteString.encodeUtf8(":status");
  public static final ByteString TARGET_AUTHORITY = ByteString.encodeUtf8(":authority");
  public static final ByteString TARGET_HOST = ByteString.encodeUtf8(":host");
  public static final ByteString TARGET_METHOD = ByteString.encodeUtf8(":method");
  public static final ByteString TARGET_PATH = ByteString.encodeUtf8(":path");
  public static final ByteString TARGET_SCHEME = ByteString.encodeUtf8(":scheme");
  public static final ByteString VERSION = ByteString.encodeUtf8(":version");
  final int hpackSize;
  public final ByteString name;
  public final ByteString value;
  
  public Header(String paramString1, String paramString2)
  {
    this(ByteString.encodeUtf8(paramString1), ByteString.encodeUtf8(paramString2));
  }
  
  public Header(ByteString paramByteString, String paramString)
  {
    this(paramByteString, ByteString.encodeUtf8(paramString));
  }
  
  public Header(ByteString paramByteString1, ByteString paramByteString2)
  {
    name = paramByteString1;
    value = paramByteString2;
    hpackSize = (paramByteString1.size() + 32 + paramByteString2.size());
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject instanceof Header))
    {
      paramObject = (Header)paramObject;
      if ((name.equals(name)) && (value.equals(value))) {
        return true;
      }
    }
    return false;
  }
  
  public int hashCode()
  {
    return (527 + name.hashCode()) * 31 + value.hashCode();
  }
  
  public String toString()
  {
    return String.format("%s: %s", new Object[] { name.utf8(), value.utf8() });
  }
}
