package com.google.firebase.database.core.utilities;

import java.util.Random;

public class PushIdGenerator
{
  private static final String PUSH_CHARS = "-0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz";
  private static long lastPushTime = 0L;
  private static final int[] lastRandChars = new int[12];
  private static final Random randGen = new Random();
  
  public PushIdGenerator() {}
  
  public static String generatePushChildName(long paramLong)
  {
    for (;;)
    {
      try
      {
        long l = lastPushTime;
        int k = 0;
        if (paramLong == l)
        {
          i = 1;
          lastPushTime = paramLong;
          Object localObject = new char[8];
          StringBuilder localStringBuilder = new StringBuilder(20);
          int j = 7;
          if (j >= 0)
          {
            localObject[j] = "-0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz".charAt((int)(paramLong % 64L));
            paramLong /= 64L;
            j -= 1;
            continue;
          }
          localStringBuilder.append((char[])localObject);
          if (i == 0)
          {
            i = 0;
            j = k;
            if (i < 12)
            {
              lastRandChars[i] = randGen.nextInt(64);
              i += 1;
              continue;
            }
          }
          else
          {
            incrementArray();
            j = k;
          }
          if (j < 12)
          {
            localStringBuilder.append("-0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz".charAt(lastRandChars[j]));
            j += 1;
            continue;
          }
          localObject = localStringBuilder.toString();
          return localObject;
        }
      }
      catch (Throwable localThrowable)
      {
        throw localThrowable;
      }
      int i = 0;
    }
  }
  
  private static void incrementArray()
  {
    int i = 11;
    while (i >= 0)
    {
      int[] arrayOfInt = lastRandChars;
      if (arrayOfInt[i] != 63)
      {
        arrayOfInt[i] += 1;
        return;
      }
      arrayOfInt[i] = 0;
      i -= 1;
    }
  }
}
