package com.google.android.android.internal.measurement;

import java.nio.ByteBuffer;

final class zzyo
  extends zzyl
{
  zzyo() {}
  
  private static int process(byte[] paramArrayOfByte, int paramInt1, long paramLong, int paramInt2)
  {
    if (paramInt2 != 0)
    {
      if (paramInt2 != 1)
      {
        if (paramInt2 == 2) {
          return zzyj.processBlock(paramInt1, zzyh.toString(paramArrayOfByte, paramLong), zzyh.toString(paramArrayOfByte, paramLong + 1L));
        }
        throw new AssertionError();
      }
      return zzyj.processBlock(paramInt1, zzyh.toString(paramArrayOfByte, paramLong));
    }
    return zzyj.zzbx(paramInt1);
  }
  
  final int decode(CharSequence paramCharSequence, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    long l1 = paramInt1;
    long l4 = paramInt2 + l1;
    int j = paramCharSequence.length();
    if ((j <= paramInt2) && (paramArrayOfByte.length - paramInt2 >= paramInt1))
    {
      paramInt2 = 0;
      long l2;
      for (;;)
      {
        l2 = 1L;
        if (paramInt2 >= j) {
          break;
        }
        paramInt1 = paramCharSequence.charAt(paramInt2);
        if (paramInt1 >= 128) {
          break;
        }
        zzyh.setBytes(paramArrayOfByte, l1, (byte)paramInt1);
        paramInt2 += 1;
        l1 = 1L + l1;
      }
      long l3 = l1;
      paramInt1 = paramInt2;
      if (paramInt2 == j) {
        return (int)l1;
      }
      while (paramInt1 < j)
      {
        int i = paramCharSequence.charAt(paramInt1);
        if ((i < 128) && (l3 < l4))
        {
          zzyh.setBytes(paramArrayOfByte, l3, (byte)i);
          l1 = l3 + l2;
        }
        for (;;)
        {
          break;
          if ((i < 2048) && (l3 <= l4 - 2L))
          {
            l1 = l3 + l2;
            zzyh.setBytes(paramArrayOfByte, l3, (byte)(i >>> 6 | 0x3C0));
            zzyh.setBytes(paramArrayOfByte, l1, (byte)(i & 0x3F | 0x80));
            l1 += l2;
          }
          else if (((i < 55296) || (57343 < i)) && (l3 <= l4 - 3L))
          {
            l1 = l3 + l2;
            zzyh.setBytes(paramArrayOfByte, l3, (byte)(i >>> 12 | 0x1E0));
            l2 = l1 + l2;
            zzyh.setBytes(paramArrayOfByte, l1, (byte)(i >>> 6 & 0x3F | 0x80));
            zzyh.setBytes(paramArrayOfByte, l2, (byte)(i & 0x3F | 0x80));
            l1 = l2 + 1L;
            l2 = 1L;
          }
          else
          {
            if (l3 > l4 - 4L) {
              break label503;
            }
            paramInt2 = paramInt1 + 1;
            if (paramInt2 == j) {
              break label490;
            }
            char c2 = paramCharSequence.charAt(paramInt2);
            if (!Character.isSurrogatePair(i, c2)) {
              break label487;
            }
            paramInt1 = Character.toCodePoint(i, c2);
            l1 = l3 + 1L;
            zzyh.setBytes(paramArrayOfByte, l3, (byte)(paramInt1 >>> 18 | 0xF0));
            l2 = l1 + 1L;
            zzyh.setBytes(paramArrayOfByte, l1, (byte)(paramInt1 >>> 12 & 0x3F | 0x80));
            l3 = l2 + 1L;
            zzyh.setBytes(paramArrayOfByte, l2, (byte)(paramInt1 >>> 6 & 0x3F | 0x80));
            l2 = 1L;
            l1 = l3 + 1L;
            zzyh.setBytes(paramArrayOfByte, l3, (byte)(paramInt1 & 0x3F | 0x80));
            paramInt1 = paramInt2;
          }
        }
        paramInt1 += 1;
        l3 = l1;
        continue;
        label487:
        paramInt1 = paramInt2;
        label490:
        throw new zzyn(paramInt1 - 1, j);
        label503:
        if ((55296 <= i) && (i <= 57343))
        {
          paramInt2 = paramInt1 + 1;
          if ((paramInt2 == j) || (!Character.isSurrogatePair(i, paramCharSequence.charAt(paramInt2)))) {
            throw new zzyn(paramInt1, j);
          }
        }
        paramCharSequence = new StringBuilder(46);
        paramCharSequence.append("Failed writing ");
        paramCharSequence.append(i);
        paramCharSequence.append(" at index ");
        paramCharSequence.append(l3);
        throw new ArrayIndexOutOfBoundsException(paramCharSequence.toString());
      }
      return (int)l3;
    }
    char c1 = paramCharSequence.charAt(j - 1);
    paramCharSequence = new StringBuilder(37);
    paramCharSequence.append("Failed writing ");
    paramCharSequence.append(c1);
    paramCharSequence.append(" at index ");
    paramCharSequence.append(paramInt1 + paramInt2);
    paramCharSequence = new ArrayIndexOutOfBoundsException(paramCharSequence.toString());
    throw paramCharSequence;
  }
  
  final void encode(CharSequence paramCharSequence, ByteBuffer paramByteBuffer)
  {
    long l3 = zzyh.next(paramByteBuffer);
    long l1 = paramByteBuffer.position() + l3;
    long l4 = paramByteBuffer.limit() + l3;
    int m = paramCharSequence.length();
    if (m <= l4 - l1)
    {
      int k = 0;
      while (k < m)
      {
        j = paramCharSequence.charAt(k);
        if (j >= 128) {
          break;
        }
        zzyh.toHex(l1, (byte)j);
        k += 1;
        l1 = 1L + l1;
      }
      long l2 = l1;
      j = k;
      if (k == m)
      {
        paramByteBuffer.position((int)(l1 - l3));
        return;
      }
      while (j < m)
      {
        int i = paramCharSequence.charAt(j);
        if ((i < 128) && (l2 < l4))
        {
          zzyh.toHex(l2, (byte)i);
          l1 = l2 + 1L;
        }
        else
        {
          if ((i < 2048) && (l2 <= l4 - 2L))
          {
            l1 = l2 + 1L;
            zzyh.toHex(l2, (byte)(i >>> 6 | 0x3C0));
            zzyh.toHex(l1, (byte)(i & 0x3F | 0x80));
            l1 += 1L;
          }
          for (;;)
          {
            break;
            if (((i < 55296) || (57343 < i)) && (l2 <= l4 - 3L))
            {
              l1 = l2 + 1L;
              zzyh.toHex(l2, (byte)(i >>> 12 | 0x1E0));
              l2 = l1 + 1L;
              zzyh.toHex(l1, (byte)(i >>> 6 & 0x3F | 0x80));
              zzyh.toHex(l2, (byte)(i & 0x3F | 0x80));
              l1 = l2 + 1L;
            }
            else
            {
              if (l2 > l4 - 4L) {
                break label500;
              }
              k = j + 1;
              if (k == m) {
                break label486;
              }
              char c2 = paramCharSequence.charAt(k);
              j = k;
              if (!Character.isSurrogatePair(i, c2)) {
                break label486;
              }
              j = Character.toCodePoint(i, c2);
              l1 = l2 + 1L;
              zzyh.toHex(l2, (byte)(j >>> 18 | 0xF0));
              l2 = l1 + 1L;
              zzyh.toHex(l1, (byte)(j >>> 12 & 0x3F | 0x80));
              l1 = l2 + 1L;
              zzyh.toHex(l2, (byte)(j >>> 6 & 0x3F | 0x80));
              zzyh.toHex(l1, (byte)(j & 0x3F | 0x80));
              l1 += 1L;
              j = k;
            }
          }
        }
        j += 1;
        l2 = l1;
        continue;
        label486:
        throw new zzyn(j - 1, m);
        label500:
        if ((55296 <= i) && (i <= 57343))
        {
          k = j + 1;
          if ((k == m) || (!Character.isSurrogatePair(i, paramCharSequence.charAt(k)))) {
            throw new zzyn(j, m);
          }
        }
        paramCharSequence = new StringBuilder(46);
        paramCharSequence.append("Failed writing ");
        paramCharSequence.append(i);
        paramCharSequence.append(" at index ");
        paramCharSequence.append(l2);
        throw new ArrayIndexOutOfBoundsException(paramCharSequence.toString());
      }
      paramByteBuffer.position((int)(l2 - l3));
      return;
    }
    char c1 = paramCharSequence.charAt(m - 1);
    int j = paramByteBuffer.limit();
    paramCharSequence = new StringBuilder(37);
    paramCharSequence.append("Failed writing ");
    paramCharSequence.append(c1);
    paramCharSequence.append(" at index ");
    paramCharSequence.append(j);
    paramCharSequence = new ArrayIndexOutOfBoundsException(paramCharSequence.toString());
    throw paramCharSequence;
  }
  
  final int parse(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
  {
    if ((paramInt2 | paramInt3 | paramArrayOfByte.length - paramInt3) >= 0)
    {
      long l2 = paramInt2;
      paramInt2 = (int)(paramInt3 - l2);
      if (paramInt2 < 16)
      {
        paramInt1 = 0;
      }
      else
      {
        l1 = l2;
        paramInt1 = 0;
        while (paramInt1 < paramInt2)
        {
          if (zzyh.toString(paramArrayOfByte, l1) < 0) {
            break label74;
          }
          paramInt1 += 1;
          l1 += 1L;
        }
        paramInt1 = paramInt2;
      }
      label74:
      paramInt2 -= paramInt1;
      long l1 = l2 + paramInt1;
      paramInt1 = paramInt2;
      paramInt2 = 0;
      paramInt3 = paramInt1;
      paramInt1 = paramInt2;
      while (paramInt3 > 0)
      {
        long l3 = l1 + 1L;
        int i = zzyh.toString(paramArrayOfByte, l1);
        paramInt1 = i;
        l2 = l3;
        paramInt2 = paramInt1;
        if (i < 0) {
          break label146;
        }
        paramInt3 -= 1;
        l1 = l3;
      }
      paramInt2 = paramInt1;
      l2 = l1;
      label146:
      if (paramInt3 == 0) {
        return 0;
      }
      paramInt1 = paramInt3 - 1;
      if (paramInt2 < -32)
      {
        if (paramInt1 == 0) {
          return paramInt2;
        }
        paramInt1 -= 1;
        if (paramInt2 >= -62)
        {
          l1 = l2 + 1L;
          if (zzyh.toString(paramArrayOfByte, l2) > -65) {
            return -1;
          }
        }
      }
      do
      {
        break;
        return -1;
        if (paramInt2 < -16)
        {
          if (paramInt1 < 2) {
            return process(paramArrayOfByte, paramInt2, l2, paramInt1);
          }
          paramInt1 -= 2;
          l1 = l2 + 1L;
          paramInt3 = zzyh.toString(paramArrayOfByte, l2);
          if (paramInt3 <= -65)
          {
            if (((paramInt2 == -32) && (paramInt3 < -96)) || ((paramInt2 == -19) && (paramInt3 >= -96))) {
              break label435;
            }
            if (zzyh.toString(paramArrayOfByte, l1) > -65) {
              return -1;
            }
            l1 += 1L;
            break;
          }
          return -1;
        }
        if (paramInt1 < 3) {
          return process(paramArrayOfByte, paramInt2, l2, paramInt1);
        }
        paramInt1 -= 3;
        l1 = l2 + 1L;
        paramInt3 = zzyh.toString(paramArrayOfByte, l2);
        if ((paramInt3 > -65) || ((paramInt2 << 28) + (paramInt3 + 112) >> 30 != 0)) {
          break label435;
        }
        l2 = l1 + 1L;
        if (zzyh.toString(paramArrayOfByte, l1) > -65) {
          break label435;
        }
        l1 = l2 + 1L;
      } while (zzyh.toString(paramArrayOfByte, l2) <= -65);
      return -1;
    }
    paramArrayOfByte = new ArrayIndexOutOfBoundsException(String.format("Array length=%d, index=%d, limit=%d", new Object[] { Integer.valueOf(paramArrayOfByte.length), Integer.valueOf(paramInt2), Integer.valueOf(paramInt3) }));
    throw paramArrayOfByte;
    label435:
    return -1;
  }
  
  final String read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws zzvt
  {
    if ((paramInt1 | paramInt2 | paramArrayOfByte.length - paramInt1 - paramInt2) >= 0)
    {
      int k = paramInt1 + paramInt2;
      char[] arrayOfChar = new char[paramInt2];
      int i = 0;
      int j = paramInt1;
      byte b1;
      for (;;)
      {
        paramInt2 = j;
        paramInt1 = i;
        if (j >= k) {
          break;
        }
        b1 = zzyh.toString(paramArrayOfByte, j);
        paramInt2 = j;
        paramInt1 = i;
        if (!zzyk.get(b1)) {
          break;
        }
        j += 1;
        zzyk.delete(b1, arrayOfChar, i);
        i += 1;
      }
      while (paramInt2 < k)
      {
        i = paramInt2 + 1;
        b1 = zzyh.toString(paramArrayOfByte, paramInt2);
        if (zzyk.get(b1))
        {
          paramInt2 = paramInt1 + 1;
          zzyk.delete(b1, arrayOfChar, paramInt1);
          paramInt1 = paramInt2;
          paramInt2 = i;
          while (paramInt2 < k)
          {
            b1 = zzyh.toString(paramArrayOfByte, paramInt2);
            if (!zzyk.get(b1)) {
              break;
            }
            paramInt2 += 1;
            zzyk.delete(b1, arrayOfChar, paramInt1);
            paramInt1 += 1;
          }
        }
        else if (zzyk.indexOf(b1))
        {
          if (i < k)
          {
            zzyk.append(b1, zzyh.toString(paramArrayOfByte, i), arrayOfChar, paramInt1);
            paramInt2 = i + 1;
            paramInt1 += 1;
          }
          else
          {
            throw zzvt.zzwr();
          }
        }
        else if (zzyk.add(b1))
        {
          if (i < k - 1)
          {
            paramInt2 = i + 1;
            zzyk.encode(b1, zzyh.toString(paramArrayOfByte, i), zzyh.toString(paramArrayOfByte, paramInt2), arrayOfChar, paramInt1);
            paramInt2 += 1;
            paramInt1 += 1;
          }
          else
          {
            throw zzvt.zzwr();
          }
        }
        else if (i < k - 2)
        {
          paramInt2 = i + 1;
          byte b2 = zzyh.toString(paramArrayOfByte, i);
          i = paramInt2 + 1;
          byte b3 = zzyh.toString(paramArrayOfByte, paramInt2);
          paramInt2 = i + 1;
          zzyk.update(b1, b2, b3, zzyh.toString(paramArrayOfByte, i), arrayOfChar, paramInt1);
          paramInt1 = paramInt1 + 1 + 1;
        }
        else
        {
          throw zzvt.zzwr();
        }
      }
      return new String(arrayOfChar, 0, paramInt1);
    }
    paramArrayOfByte = new ArrayIndexOutOfBoundsException(String.format("buffer length=%d, index=%d, size=%d", new Object[] { Integer.valueOf(paramArrayOfByte.length), Integer.valueOf(paramInt1), Integer.valueOf(paramInt2) }));
    throw paramArrayOfByte;
  }
}
