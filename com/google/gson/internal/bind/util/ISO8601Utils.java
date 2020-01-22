package com.google.gson.internal.bind.util;

import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class ISO8601Utils
{
  private static final TimeZone TIMEZONE_UTC = TimeZone.getTimeZone("UTC");
  private static final String UTC_ID = "UTC";
  
  public ISO8601Utils() {}
  
  private static boolean checkOffset(String paramString, int paramInt, char paramChar)
  {
    return (paramInt < paramString.length()) && (paramString.charAt(paramInt) == paramChar);
  }
  
  public static String format(Date paramDate)
  {
    return format(paramDate, false, TIMEZONE_UTC);
  }
  
  public static String format(Date paramDate, boolean paramBoolean)
  {
    return format(paramDate, paramBoolean, TIMEZONE_UTC);
  }
  
  public static String format(Date paramDate, boolean paramBoolean, TimeZone paramTimeZone)
  {
    GregorianCalendar localGregorianCalendar = new GregorianCalendar(paramTimeZone, Locale.US);
    localGregorianCalendar.setTime(paramDate);
    if (paramBoolean) {
      i = 4;
    } else {
      i = 0;
    }
    int j;
    if (paramTimeZone.getRawOffset() == 0) {
      j = 1;
    } else {
      j = 6;
    }
    paramDate = new StringBuilder(19 + i + j);
    padInt(paramDate, localGregorianCalendar.get(1), 4);
    char c = '-';
    paramDate.append('-');
    padInt(paramDate, localGregorianCalendar.get(2) + 1, 2);
    paramDate.append('-');
    padInt(paramDate, localGregorianCalendar.get(5), 2);
    paramDate.append('T');
    padInt(paramDate, localGregorianCalendar.get(11), 2);
    paramDate.append(':');
    padInt(paramDate, localGregorianCalendar.get(12), 2);
    paramDate.append(':');
    padInt(paramDate, localGregorianCalendar.get(13), 2);
    if (paramBoolean)
    {
      paramDate.append('.');
      padInt(paramDate, localGregorianCalendar.get(14), 3);
    }
    int i = paramTimeZone.getOffset(localGregorianCalendar.getTimeInMillis());
    if (i != 0)
    {
      int k = i / 60000;
      j = Math.abs(k / 60);
      k = Math.abs(k % 60);
      if (i >= 0) {
        c = '+';
      }
      paramDate.append(c);
      padInt(paramDate, j, 2);
      paramDate.append(':');
      padInt(paramDate, k, 2);
    }
    else
    {
      paramDate.append('Z');
    }
    return paramDate.toString();
  }
  
  private static int indexOfNonDigit(String paramString, int paramInt)
  {
    while (paramInt < paramString.length())
    {
      int i = paramString.charAt(paramInt);
      if (i >= 48)
      {
        if (i > 57) {
          return paramInt;
        }
        paramInt += 1;
      }
      else
      {
        return paramInt;
      }
    }
    return paramString.length();
  }
  
  private static void padInt(StringBuilder paramStringBuilder, int paramInt1, int paramInt2)
  {
    String str = Integer.toString(paramInt1);
    paramInt1 = paramInt2 - str.length();
    while (paramInt1 > 0)
    {
      paramStringBuilder.append('0');
      paramInt1 -= 1;
    }
    paramStringBuilder.append(str);
  }
  
  public static Date parse(String paramString, ParsePosition paramParsePosition)
    throws ParseException
  {
    try
    {
      int i = paramParsePosition.getIndex();
      int j = i + 4;
      int i3 = parseInt(paramString, i, j);
      boolean bool = checkOffset(paramString, j, '-');
      i = j;
      if (bool) {
        i = j + 1;
      }
      j = i + 2;
      int i4 = parseInt(paramString, i, j);
      bool = checkOffset(paramString, j, '-');
      i = j;
      if (bool) {
        i = j + 1;
      }
      int n = i + 2;
      int i5 = parseInt(paramString, i, n);
      bool = checkOffset(paramString, n, 'T');
      if (!bool)
      {
        i = paramString.length();
        if (i <= n)
        {
          localObject1 = new GregorianCalendar(i3, i4 - 1, i5);
          paramParsePosition.setIndex(n);
          localObject1 = ((Calendar)localObject1).getTime();
          return localObject1;
        }
      }
      if (bool)
      {
        i = n + 1;
        j = i + 2;
        k = parseInt(paramString, i, j);
        bool = checkOffset(paramString, j, ':');
        i = j;
        if (bool) {
          i = j + 1;
        }
        j = i + 2;
        m = parseInt(paramString, i, j);
        bool = checkOffset(paramString, j, ':');
        i1 = j;
        if (bool) {
          i1 = j + 1;
        }
        int i2 = paramString.length();
        i = k;
        j = m;
        n = i1;
        if (i2 > i1)
        {
          i2 = paramString.charAt(i1);
          i = k;
          j = m;
          n = i1;
          if (i2 != 90)
          {
            i = k;
            j = m;
            n = i1;
            if (i2 != 43)
            {
              i = k;
              j = m;
              n = i1;
              if (i2 != 45)
              {
                n = i1 + 2;
                i1 = parseInt(paramString, i1, n);
                j = i1;
                i = j;
                if (i1 > 59)
                {
                  i = j;
                  if (i1 < 63) {
                    i = 59;
                  }
                }
                bool = checkOffset(paramString, n, '.');
                if (bool)
                {
                  i2 = n + 1;
                  n = indexOfNonDigit(paramString, i2 + 1);
                  int i6 = Math.min(n, i2 + 3);
                  i1 = parseInt(paramString, i2, i6);
                  j = i1;
                  i2 = i6 - i2;
                  if (i2 != 1)
                  {
                    if (i2 != 2) {}
                  }
                  else {
                    for (;;)
                    {
                      j = i1 * 10;
                      continue;
                      j = i1 * 100;
                    }
                  }
                  i1 = j;
                  i2 = i;
                  i = k;
                  j = m;
                  k = i1;
                  m = i2;
                  break label526;
                }
                i1 = i;
                i = k;
                j = m;
                k = i1;
                break label515;
              }
            }
          }
        }
      }
      else
      {
        i = 0;
        j = 0;
      }
      int k = 0;
      label515:
      int i1 = 0;
      int m = k;
      k = i1;
      label526:
      i1 = paramString.length();
      if (i1 > n)
      {
        char c = paramString.charAt(n);
        if (c == 'Z')
        {
          localObject1 = TIMEZONE_UTC;
          n += 1;
        }
        else
        {
          if ((c != '+') && (c != '-'))
          {
            localObject1 = new StringBuilder();
            ((StringBuilder)localObject1).append("Invalid time zone indicator '");
            ((StringBuilder)localObject1).append(c);
            ((StringBuilder)localObject1).append("'");
            throw new IndexOutOfBoundsException(((StringBuilder)localObject1).toString());
          }
          localObject2 = paramString.substring(n);
          localObject1 = localObject2;
          i1 = ((String)localObject2).length();
          if (i1 < 5)
          {
            localObject1 = new StringBuilder();
            ((StringBuilder)localObject1).append((String)localObject2);
            ((StringBuilder)localObject1).append("00");
            localObject1 = ((StringBuilder)localObject1).toString();
          }
          i1 = ((String)localObject1).length();
          n += i1;
          bool = "+0000".equals(localObject1);
          if (!bool)
          {
            bool = "+00:00".equals(localObject1);
            if (!bool)
            {
              localObject2 = new StringBuilder();
              ((StringBuilder)localObject2).append("GMT");
              ((StringBuilder)localObject2).append((String)localObject1);
              localObject2 = ((StringBuilder)localObject2).toString();
              localObject1 = TimeZone.getTimeZone((String)localObject2);
              localObject3 = ((TimeZone)localObject1).getID();
              bool = ((String)localObject3).equals(localObject2);
              if (!bool)
              {
                bool = ((String)localObject3).replace(":", "").equals(localObject2);
                if (!bool)
                {
                  localObject3 = new StringBuilder();
                  ((StringBuilder)localObject3).append("Mismatching time zone indicator: ");
                  ((StringBuilder)localObject3).append((String)localObject2);
                  ((StringBuilder)localObject3).append(" given, resolves to ");
                  ((StringBuilder)localObject3).append(((TimeZone)localObject1).getID());
                  localObject1 = new IndexOutOfBoundsException(((StringBuilder)localObject3).toString());
                  throw ((Throwable)localObject1);
                }
              }
              break label884;
            }
          }
          localObject1 = TIMEZONE_UTC;
        }
        label884:
        localObject1 = new GregorianCalendar((TimeZone)localObject1);
        ((Calendar)localObject1).setLenient(false);
        ((Calendar)localObject1).set(1, i3);
        ((Calendar)localObject1).set(2, i4 - 1);
        ((Calendar)localObject1).set(5, i5);
        ((Calendar)localObject1).set(11, i);
        ((Calendar)localObject1).set(12, j);
        ((Calendar)localObject1).set(13, m);
        ((Calendar)localObject1).set(14, k);
        paramParsePosition.setIndex(n);
        localObject1 = ((Calendar)localObject1).getTime();
        return localObject1;
      }
      Object localObject1 = new IllegalArgumentException("No time zone indicator");
      throw ((Throwable)localObject1);
    }
    catch (IllegalArgumentException localIllegalArgumentException) {}catch (NumberFormatException localNumberFormatException) {}catch (IndexOutOfBoundsException localIndexOutOfBoundsException) {}
    if (paramString == null)
    {
      paramString = null;
    }
    else
    {
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append('"');
      ((StringBuilder)localObject2).append(paramString);
      ((StringBuilder)localObject2).append('"');
      paramString = ((StringBuilder)localObject2).toString();
    }
    Object localObject3 = ((Exception)localIndexOutOfBoundsException).getMessage();
    Object localObject2 = localObject3;
    if ((localObject3 == null) || (((String)localObject3).isEmpty()))
    {
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("(");
      ((StringBuilder)localObject2).append(localIndexOutOfBoundsException.getClass().getName());
      ((StringBuilder)localObject2).append(")");
      localObject2 = ((StringBuilder)localObject2).toString();
    }
    localObject3 = new StringBuilder();
    ((StringBuilder)localObject3).append("Failed to parse date [");
    ((StringBuilder)localObject3).append(paramString);
    ((StringBuilder)localObject3).append("]: ");
    ((StringBuilder)localObject3).append((String)localObject2);
    paramString = new ParseException(((StringBuilder)localObject3).toString(), paramParsePosition.getIndex());
    paramString.initCause((Throwable)localIndexOutOfBoundsException);
    throw paramString;
  }
  
  private static int parseInt(String paramString, int paramInt1, int paramInt2)
    throws NumberFormatException
  {
    if ((paramInt1 >= 0) && (paramInt2 <= paramString.length()) && (paramInt1 <= paramInt2))
    {
      int i;
      int j;
      StringBuilder localStringBuilder;
      if (paramInt1 < paramInt2)
      {
        i = paramInt1 + 1;
        j = Character.digit(paramString.charAt(paramInt1), 10);
        if (j >= 0)
        {
          j = -j;
        }
        else
        {
          localStringBuilder = new StringBuilder();
          localStringBuilder.append("Invalid number: ");
          localStringBuilder.append(paramString.substring(paramInt1, paramInt2));
          throw new NumberFormatException(localStringBuilder.toString());
        }
      }
      else
      {
        i = paramInt1;
        j = 0;
      }
      while (i < paramInt2)
      {
        int k = Character.digit(paramString.charAt(i), 10);
        if (k >= 0)
        {
          j = j * 10 - k;
          i += 1;
        }
        else
        {
          localStringBuilder = new StringBuilder();
          localStringBuilder.append("Invalid number: ");
          localStringBuilder.append(paramString.substring(paramInt1, paramInt2));
          throw new NumberFormatException(localStringBuilder.toString());
        }
      }
      return -j;
    }
    paramString = new NumberFormatException(paramString);
    throw paramString;
  }
}
