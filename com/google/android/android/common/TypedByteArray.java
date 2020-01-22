package com.google.android.android.common;

import java.util.Arrays;

final class TypedByteArray
  extends Name
{
  private final byte[] bytes;
  
  TypedByteArray(byte[] paramArrayOfByte)
  {
    super(Arrays.copyOfRange(paramArrayOfByte, 0, 25));
    bytes = paramArrayOfByte;
  }
  
  final byte[] getBytes()
  {
    return bytes;
  }
}
