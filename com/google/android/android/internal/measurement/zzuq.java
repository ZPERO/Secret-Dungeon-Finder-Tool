package com.google.android.android.internal.measurement;

import java.io.IOException;
import java.util.Arrays;

final class zzuq
  extends zzuo
{
  private final byte[] buffer;
  private int limit;
  private int pointer;
  private final boolean zzbum;
  private int zzbun;
  private int zzbuo;
  private int zzbup;
  private int zzbuq = Integer.MAX_VALUE;
  
  private zzuq(byte[] paramArrayOfByte, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    super(null);
    buffer = paramArrayOfByte;
    limit = (paramInt2 + paramInt1);
    pointer = paramInt1;
    zzbuo = pointer;
    zzbum = paramBoolean;
  }
  
  private final int zzuy()
    throws IOException
  {
    int k = pointer;
    int i = limit;
    if (i != k)
    {
      byte[] arrayOfByte = buffer;
      int j = k + 1;
      k = arrayOfByte[k];
      if (k >= 0)
      {
        pointer = j;
        return k;
      }
      if (i - j >= 9)
      {
        i = j + 1;
        k ^= arrayOfByte[j] << 7;
        if (k < 0)
        {
          j = k ^ 0xFFFFFF80;
        }
        else
        {
          j = i + 1;
          k ^= arrayOfByte[i] << 14;
          if (k >= 0)
          {
            k ^= 0x3F80;
            i = j;
            j = k;
          }
          for (;;)
          {
            break;
            i = j + 1;
            j = k ^ arrayOfByte[j] << 21;
            if (j < 0)
            {
              j ^= 0xFFE03F80;
            }
            else
            {
              int m = i + 1;
              int n = arrayOfByte[i];
              k = j ^ n << 28 ^ 0xFE03F80;
              j = k;
              i = m;
              if (n < 0)
              {
                n = m + 1;
                i = n;
                j = k;
                if (arrayOfByte[m] < 0)
                {
                  m = n + 1;
                  j = k;
                  i = m;
                  if (arrayOfByte[n] < 0)
                  {
                    n = m + 1;
                    i = n;
                    j = k;
                    if (arrayOfByte[m] < 0)
                    {
                      m = n + 1;
                      j = k;
                      i = m;
                      if (arrayOfByte[n] < 0)
                      {
                        i = m + 1;
                        if (arrayOfByte[m] < 0) {
                          break label262;
                        }
                        j = k;
                      }
                    }
                  }
                }
              }
            }
          }
        }
        pointer = i;
        return j;
      }
    }
    label262:
    return (int)zzuv();
  }
  
  private final long zzuz()
    throws IOException
  {
    int k = pointer;
    int i = limit;
    if (i != k)
    {
      byte[] arrayOfByte = buffer;
      int j = k + 1;
      k = arrayOfByte[k];
      if (k >= 0)
      {
        pointer = j;
        return k;
      }
      if (i - j >= 9)
      {
        i = j + 1;
        k ^= arrayOfByte[j] << 7;
        if (k < 0) {
          j = k ^ 0xFFFFFF80;
        }
        for (;;)
        {
          l1 = j;
          break label339;
          j = i + 1;
          k ^= arrayOfByte[i] << 14;
          if (k >= 0)
          {
            l1 = k ^ 0x3F80;
            i = j;
            break label339;
          }
          i = j + 1;
          j = k ^ arrayOfByte[j] << 21;
          if (j >= 0) {
            break;
          }
          j ^= 0xFFE03F80;
        }
        long l1 = j;
        j = i + 1;
        l1 ^= arrayOfByte[i] << 28;
        long l2;
        if (l1 >= 0L)
        {
          l2 = 266354560L;
          i = j;
          l1 = l2 ^ l1;
        }
        for (;;)
        {
          break label339;
          i = j + 1;
          l1 ^= arrayOfByte[j] << 35;
          if (l1 < 0L) {}
          for (l2 = -34093383808L;; l2 = -558586000294016L)
          {
            l1 ^= l2;
            break label339;
            j = i + 1;
            l1 ^= arrayOfByte[i] << 42;
            if (l1 >= 0L)
            {
              l2 = 4363953127296L;
              i = j;
              break;
            }
            i = j + 1;
            l1 ^= arrayOfByte[j] << 49;
            if (l1 >= 0L) {
              break label287;
            }
          }
          label287:
          j = i + 1;
          l2 = l1 ^ arrayOfByte[i] << 56 ^ 0xFE03F80FE03F80;
          i = j;
          l1 = l2;
          if (l2 < 0L)
          {
            i = j + 1;
            if (arrayOfByte[j] < 0L) {
              break label347;
            }
            l1 = l2;
          }
        }
        label339:
        pointer = i;
        return l1;
      }
    }
    label347:
    return zzuv();
  }
  
  private final int zzva()
    throws IOException
  {
    int i = pointer;
    if (limit - i >= 4)
    {
      byte[] arrayOfByte = buffer;
      pointer = (i + 4);
      int j = arrayOfByte[i];
      int k = arrayOfByte[(i + 1)];
      int m = arrayOfByte[(i + 2)];
      return (arrayOfByte[(i + 3)] & 0xFF) << 24 | j & 0xFF | (k & 0xFF) << 8 | (m & 0xFF) << 16;
    }
    throw zzvt.zzwk();
  }
  
  private final long zzvb()
    throws IOException
  {
    int i = pointer;
    if (limit - i >= 8)
    {
      byte[] arrayOfByte = buffer;
      pointer = (i + 8);
      long l1 = arrayOfByte[i];
      long l2 = arrayOfByte[(i + 1)];
      long l3 = arrayOfByte[(i + 2)];
      long l4 = arrayOfByte[(i + 3)];
      long l5 = arrayOfByte[(i + 4)];
      long l6 = arrayOfByte[(i + 5)];
      long l7 = arrayOfByte[(i + 6)];
      return (arrayOfByte[(i + 7)] & 0xFF) << 56 | l1 & 0xFF | (l2 & 0xFF) << 8 | (l3 & 0xFF) << 16 | (l4 & 0xFF) << 24 | (l5 & 0xFF) << 32 | (l6 & 0xFF) << 40 | (l7 & 0xFF) << 48;
    }
    throw zzvt.zzwk();
  }
  
  private final void zzvc()
  {
    limit += zzbun;
    int i = limit;
    int j = i - zzbuo;
    int k = zzbuq;
    if (j > k)
    {
      zzbun = (j - k);
      limit = (i - zzbun);
      return;
    }
    zzbun = 0;
  }
  
  private final byte zzvd()
    throws IOException
  {
    int i = pointer;
    if (i != limit)
    {
      byte[] arrayOfByte = buffer;
      pointer = (i + 1);
      return arrayOfByte[i];
    }
    throw zzvt.zzwk();
  }
  
  public final zzwt blur(zzxd paramZzxd, zzuz paramZzuz)
    throws IOException
  {
    int i = zzuy();
    if (zzbuh < zzbui)
    {
      i = zzaq(i);
      zzbuh += 1;
      paramZzxd = (zzwt)paramZzxd.subtract(this, paramZzuz);
      zzan(0);
      zzbuh -= 1;
      zzar(i);
      return paramZzxd;
    }
    throw zzvt.zzwp();
  }
  
  public final double readDouble()
    throws IOException
  {
    return Double.longBitsToDouble(zzvb());
  }
  
  public final float readFloat()
    throws IOException
  {
    return Float.intBitsToFloat(zzva());
  }
  
  public final String readString()
    throws IOException
  {
    int i = zzuy();
    if (i > 0)
    {
      int j = limit;
      int k = pointer;
      if (i <= j - k)
      {
        String str = new String(buffer, k, i, zzvo.UTF_8);
        pointer += i;
        return str;
      }
    }
    if (i == 0) {
      return "";
    }
    if (i < 0) {
      throw zzvt.zzwl();
    }
    throw zzvt.zzwk();
  }
  
  public final void zzan(int paramInt)
    throws zzvt
  {
    if (zzbup == paramInt) {
      return;
    }
    throw zzvt.zzwn();
  }
  
  public final boolean zzao(int paramInt)
    throws IOException
  {
    int k = paramInt & 0x7;
    int j = 0;
    int i = 0;
    if (k != 0)
    {
      if (k != 1)
      {
        if (k != 2)
        {
          if (k != 3)
          {
            if (k != 4)
            {
              if (k == 5)
              {
                zzas(4);
                return true;
              }
              throw zzvt.zzwo();
            }
          }
          else
          {
            do
            {
              i = zzug();
            } while ((i != 0) && (zzao(i)));
            zzan(paramInt >>> 3 << 3 | 0x4);
            return true;
          }
        }
        else
        {
          zzas(zzuy());
          return true;
        }
      }
      else
      {
        zzas(8);
        return true;
      }
    }
    else
    {
      paramInt = j;
      if (limit - pointer >= 10)
      {
        paramInt = i;
        while (paramInt < 10)
        {
          localObject = buffer;
          i = pointer;
          pointer = (i + 1);
          if (localObject[i] >= 0) {
            break label184;
          }
          paramInt += 1;
        }
        throw zzvt.zzwm();
      }
      while (paramInt < 10)
      {
        if (zzvd() >= 0) {
          break label199;
        }
        paramInt += 1;
      }
      label184:
      return true;
      Object localObject = zzvt.zzwm();
      throw ((Throwable)localObject);
    }
    return false;
    label199:
    return true;
  }
  
  public final int zzaq(int paramInt)
    throws zzvt
  {
    if (paramInt >= 0)
    {
      paramInt += zzux();
      int i = zzbuq;
      if (paramInt <= i)
      {
        zzbuq = paramInt;
        zzvc();
        return i;
      }
      throw zzvt.zzwk();
    }
    throw zzvt.zzwl();
  }
  
  public final void zzar(int paramInt)
  {
    zzbuq = paramInt;
    zzvc();
  }
  
  public final void zzas(int paramInt)
    throws IOException
  {
    if (paramInt >= 0)
    {
      int i = limit;
      int j = pointer;
      if (paramInt <= i - j)
      {
        pointer = (j + paramInt);
        return;
      }
    }
    if (paramInt < 0) {
      throw zzvt.zzwl();
    }
    throw zzvt.zzwk();
  }
  
  public final int zzug()
    throws IOException
  {
    if (zzuw())
    {
      zzbup = 0;
      return 0;
    }
    zzbup = zzuy();
    int i = zzbup;
    if (i >>> 3 != 0) {
      return i;
    }
    throw new zzvt("Protocol message contained an invalid tag (zero).");
  }
  
  public final long zzuh()
    throws IOException
  {
    return zzuz();
  }
  
  public final long zzui()
    throws IOException
  {
    return zzuz();
  }
  
  public final int zzuj()
    throws IOException
  {
    return zzuy();
  }
  
  public final long zzuk()
    throws IOException
  {
    return zzvb();
  }
  
  public final int zzul()
    throws IOException
  {
    return zzva();
  }
  
  public final boolean zzum()
    throws IOException
  {
    return zzuz() != 0L;
  }
  
  public final String zzun()
    throws IOException
  {
    int i = zzuy();
    if (i > 0)
    {
      int j = limit;
      int k = pointer;
      if (i <= j - k)
      {
        String str = zzyj.readInternal(buffer, k, i);
        pointer += i;
        return str;
      }
    }
    if (i == 0) {
      return "";
    }
    if (i <= 0) {
      throw zzvt.zzwl();
    }
    throw zzvt.zzwk();
  }
  
  public final zzud zzuo()
    throws IOException
  {
    int i = zzuy();
    int j;
    int k;
    Object localObject;
    if (i > 0)
    {
      j = limit;
      k = pointer;
      if (i <= j - k)
      {
        localObject = zzud.getChars(buffer, k, i);
        pointer += i;
        return localObject;
      }
    }
    if (i == 0) {
      return zzud.zzbtz;
    }
    if (i > 0)
    {
      j = limit;
      k = pointer;
      if (i <= j - k)
      {
        pointer = (i + k);
        localObject = Arrays.copyOfRange(buffer, k, pointer);
        break label116;
      }
    }
    if (i <= 0)
    {
      if (i == 0)
      {
        localObject = zzvo.zzbzj;
        label116:
        return zzud.bytesToHex((byte[])localObject);
      }
      throw zzvt.zzwl();
    }
    throw zzvt.zzwk();
  }
  
  public final int zzup()
    throws IOException
  {
    return zzuy();
  }
  
  public final int zzuq()
    throws IOException
  {
    return zzuy();
  }
  
  public final int zzur()
    throws IOException
  {
    return zzva();
  }
  
  public final long zzus()
    throws IOException
  {
    return zzvb();
  }
  
  public final int zzut()
    throws IOException
  {
    int i = zzuy();
    return -(i & 0x1) ^ i >>> 1;
  }
  
  public final long zzuu()
    throws IOException
  {
    long l = zzuz();
    return -(l & 1L) ^ l >>> 1;
  }
  
  final long zzuv()
    throws IOException
  {
    long l = 0L;
    int i = 0;
    while (i < 64)
    {
      int j = zzvd();
      l |= (j & 0x7F) << i;
      if ((j & 0x80) == 0) {
        return l;
      }
      i += 7;
    }
    zzvt localZzvt = zzvt.zzwm();
    throw localZzvt;
  }
  
  public final boolean zzuw()
    throws IOException
  {
    return pointer == limit;
  }
  
  public final int zzux()
  {
    return pointer - zzbuo;
  }
}
