package kotlin.random;

import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\000\022\n\002\030\002\n\002\030\002\n\000\n\002\020\b\n\002\b\r\b\000\030\0002\0020\001B\027\b\020\022\006\020\002\032\0020\003\022\006\020\004\032\0020\003?\006\002\020\005B7\b\000\022\006\020\006\032\0020\003\022\006\020\007\032\0020\003\022\006\020\b\032\0020\003\022\006\020\t\032\0020\003\022\006\020\n\032\0020\003\022\006\020\013\032\0020\003?\006\002\020\fJ\020\020\r\032\0020\0032\006\020\016\032\0020\003H\026J\b\020\017\032\0020\003H\026R\016\020\013\032\0020\003X?\016?\006\002\n\000R\016\020\n\032\0020\003X?\016?\006\002\n\000R\016\020\t\032\0020\003X?\016?\006\002\n\000R\016\020\006\032\0020\003X?\016?\006\002\n\000R\016\020\007\032\0020\003X?\016?\006\002\n\000R\016\020\b\032\0020\003X?\016?\006\002\n\000?\006\020"}, d2={"Lkotlin/random/XorWowRandom;", "Lkotlin/random/Random;", "seed1", "", "seed2", "(II)V", "x", "y", "z", "w", "v", "addend", "(IIIIII)V", "nextBits", "bitCount", "nextInt", "kotlin-stdlib"}, k=1, mv={1, 1, 15})
public final class XorWowRandom
  extends Random
{
  private int addend;
  private int head;
  private int next;
  private int numbers;
  private int pos;
  private int tail;
  
  public XorWowRandom(int paramInt1, int paramInt2)
  {
    this(paramInt1, paramInt2, 0, 0, paramInt1, paramInt1 << 10 ^ paramInt2 >>> 4);
  }
  
  public XorWowRandom(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    pos = paramInt1;
    next = paramInt2;
    head = paramInt3;
    tail = paramInt4;
    numbers = paramInt5;
    addend = paramInt6;
    paramInt1 = pos;
    paramInt3 = next;
    paramInt4 = head;
    paramInt5 = tail;
    paramInt6 = numbers;
    paramInt2 = 0;
    if ((paramInt1 | paramInt3 | paramInt4 | paramInt5 | paramInt6) != 0) {
      paramInt1 = 1;
    } else {
      paramInt1 = 0;
    }
    if (paramInt1 != 0)
    {
      paramInt1 = paramInt2;
      while (paramInt1 < 64)
      {
        nextInt();
        paramInt1 += 1;
      }
      return;
    }
    Throwable localThrowable = (Throwable)new IllegalArgumentException("Initial state must have at least one non-zero element.".toString());
    throw localThrowable;
  }
  
  public int nextBits(int paramInt)
  {
    return RandomKt.takeUpperBits(nextInt(), paramInt);
  }
  
  public int nextInt()
  {
    int i = pos;
    i ^= i >>> 2;
    pos = next;
    next = head;
    head = tail;
    int j = numbers;
    tail = j;
    i = i ^ i << 1 ^ j ^ j << 4;
    numbers = i;
    addend += 362437;
    return i + addend;
  }
}
