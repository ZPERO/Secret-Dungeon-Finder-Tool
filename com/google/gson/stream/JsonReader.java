package com.google.gson.stream;

import com.google.gson.internal.JsonReaderInternalAccess;
import com.google.gson.internal.bind.JsonTreeReader;
import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;

public class JsonReader
  implements Closeable
{
  private static final long MIN_INCOMPLETE_INTEGER = -922337203685477580L;
  private static final char[] NON_EXECUTE_PREFIX = ")]}'\n".toCharArray();
  private static final int NUMBER_CHAR_DECIMAL = 3;
  private static final int NUMBER_CHAR_DIGIT = 2;
  private static final int NUMBER_CHAR_EXP_DIGIT = 7;
  private static final int NUMBER_CHAR_EXP_E = 5;
  private static final int NUMBER_CHAR_EXP_SIGN = 6;
  private static final int NUMBER_CHAR_FRACTION_DIGIT = 4;
  private static final int NUMBER_CHAR_NONE = 0;
  private static final int NUMBER_CHAR_SIGN = 1;
  private static final int PEEKED_BEGIN_ARRAY = 3;
  private static final int PEEKED_BEGIN_OBJECT = 1;
  private static final int PEEKED_BUFFERED = 11;
  private static final int PEEKED_DOUBLE_QUOTED = 9;
  private static final int PEEKED_DOUBLE_QUOTED_NAME = 13;
  private static final int PEEKED_END_ARRAY = 4;
  private static final int PEEKED_END_OBJECT = 2;
  private static final int PEEKED_EOF = 17;
  private static final int PEEKED_FALSE = 6;
  private static final int PEEKED_LONG = 15;
  private static final int PEEKED_NONE = 0;
  private static final int PEEKED_NULL = 7;
  private static final int PEEKED_NUMBER = 16;
  private static final int PEEKED_SINGLE_QUOTED = 8;
  private static final int PEEKED_SINGLE_QUOTED_NAME = 12;
  private static final int PEEKED_TRUE = 5;
  private static final int PEEKED_UNQUOTED = 10;
  private static final int PEEKED_UNQUOTED_NAME = 14;
  private final char[] buffer = new char['?'];
  private final Reader in;
  private boolean lenient = false;
  private int limit = 0;
  private int lineNumber = 0;
  private int lineStart = 0;
  private int[] pathIndices;
  private String[] pathNames;
  int peeked = 0;
  private long peekedLong;
  private int peekedNumberLength;
  private String peekedString;
  private int pos = 0;
  private int[] stack = new int[32];
  private int stackSize = 0;
  
  static
  {
    JsonReaderInternalAccess.INSTANCE = new JsonReaderInternalAccess()
    {
      public void promoteNameToValue(JsonReader paramAnonymousJsonReader)
        throws IOException
      {
        if ((paramAnonymousJsonReader instanceof JsonTreeReader))
        {
          ((JsonTreeReader)paramAnonymousJsonReader).promoteNameToValue();
          return;
        }
        int j = peeked;
        int i = j;
        if (j == 0) {
          i = paramAnonymousJsonReader.doPeek();
        }
        if (i == 13)
        {
          peeked = 9;
          return;
        }
        if (i == 12)
        {
          peeked = 8;
          return;
        }
        if (i == 14)
        {
          peeked = 10;
          return;
        }
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("Expected a name but was ");
        localStringBuilder.append(paramAnonymousJsonReader.peek());
        localStringBuilder.append(paramAnonymousJsonReader.locationString());
        throw new IllegalStateException(localStringBuilder.toString());
      }
    };
  }
  
  public JsonReader(Reader paramReader)
  {
    int[] arrayOfInt = stack;
    int i = stackSize;
    stackSize = (i + 1);
    arrayOfInt[i] = 6;
    pathNames = new String[32];
    pathIndices = new int[32];
    if (paramReader != null)
    {
      in = paramReader;
      return;
    }
    throw new NullPointerException("in == null");
  }
  
  private void checkLenient()
    throws IOException
  {
    if (lenient) {
      return;
    }
    throw syntaxError("Use JsonReader.setLenient(true) to accept malformed JSON");
  }
  
  private void consumeNonExecutePrefix()
    throws IOException
  {
    nextNonWhitespace(true);
    pos -= 1;
    int i = pos;
    char[] arrayOfChar = NON_EXECUTE_PREFIX;
    if ((i + arrayOfChar.length > limit) && (!fillBuffer(arrayOfChar.length))) {
      return;
    }
    i = 0;
    for (;;)
    {
      arrayOfChar = NON_EXECUTE_PREFIX;
      if (i >= arrayOfChar.length) {
        break;
      }
      if (buffer[(pos + i)] != arrayOfChar[i]) {
        return;
      }
      i += 1;
    }
    pos += arrayOfChar.length;
  }
  
  private boolean fillBuffer(int paramInt)
    throws IOException
  {
    char[] arrayOfChar = buffer;
    int j = lineStart;
    int i = pos;
    lineStart = (j - i);
    j = limit;
    if (j != i)
    {
      limit = (j - i);
      System.arraycopy(arrayOfChar, i, arrayOfChar, 0, limit);
    }
    else
    {
      limit = 0;
    }
    pos = 0;
    do
    {
      Reader localReader = in;
      i = limit;
      i = localReader.read(arrayOfChar, i, arrayOfChar.length - i);
      if (i == -1) {
        break;
      }
      limit += i;
      i = paramInt;
      if (lineNumber == 0)
      {
        j = lineStart;
        i = paramInt;
        if (j == 0)
        {
          i = paramInt;
          if (limit > 0)
          {
            i = paramInt;
            if (arrayOfChar[0] == 65279)
            {
              pos += 1;
              lineStart = (j + 1);
              i = paramInt + 1;
            }
          }
        }
      }
      paramInt = i;
    } while (limit < i);
    return true;
    return false;
  }
  
  private boolean isLiteral(char paramChar)
    throws IOException
  {
    if ((paramChar != '\t') && (paramChar != '\n') && (paramChar != '\f') && (paramChar != '\r') && (paramChar != ' '))
    {
      if (paramChar != '#')
      {
        if (paramChar == ',') {
          break label121;
        }
        if ((paramChar != '/') && (paramChar != '='))
        {
          if ((paramChar == '{') || (paramChar == '}') || (paramChar == ':')) {
            break label121;
          }
          if (paramChar == ';') {}
        }
      }
      switch (paramChar)
      {
      default: 
        return true;
      case '\\': 
        checkLenient();
      }
    }
    label121:
    return false;
  }
  
  private int nextNonWhitespace(boolean paramBoolean)
    throws IOException
  {
    Object localObject = buffer;
    int i = pos;
    int j = limit;
    for (;;)
    {
      int m = i;
      int k = j;
      if (i == j)
      {
        pos = i;
        if (!fillBuffer(1))
        {
          if (!paramBoolean) {
            return -1;
          }
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("End of input");
          ((StringBuilder)localObject).append(locationString());
          throw new EOFException(((StringBuilder)localObject).toString());
        }
        m = pos;
        k = limit;
      }
      i = m + 1;
      j = localObject[m];
      if (j == 10)
      {
        lineNumber += 1;
        lineStart = i;
      }
      else if ((j != 32) && (j != 13) && (j != 9))
      {
        if (j == 47)
        {
          pos = i;
          if (i == k)
          {
            pos -= 1;
            boolean bool = fillBuffer(2);
            pos += 1;
            if (!bool) {
              return j;
            }
          }
          checkLenient();
          i = pos;
          k = localObject[i];
          if (k != 42)
          {
            if (k != 47) {
              return j;
            }
            pos = (i + 1);
            skipToEndOfLine();
            i = pos;
            j = limit;
            continue;
          }
          pos = (i + 1);
          if (skipTo("*/"))
          {
            i = pos + 2;
            j = limit;
            continue;
          }
          throw syntaxError("Unterminated comment");
        }
        if (j == 35)
        {
          pos = i;
          checkLenient();
          skipToEndOfLine();
          i = pos;
          j = limit;
          continue;
        }
        pos = i;
        return j;
      }
      j = k;
    }
  }
  
  private String nextQuotedValue(char paramChar)
    throws IOException
  {
    char[] arrayOfChar = buffer;
    Object localObject2;
    for (Object localObject1 = null;; localObject1 = localObject2)
    {
      char c1 = pos;
      int i = limit;
      char c2 = c1;
      char c3;
      for (;;)
      {
        c3 = c2;
        if (c3 >= i) {
          break label203;
        }
        c2 = c3 + '\001';
        c3 = arrayOfChar[c3];
        if (c3 == paramChar)
        {
          pos = c2;
          paramChar = c2 - c1 - 1;
          if (localObject1 == null) {
            return new String(arrayOfChar, c1, paramChar);
          }
          ((StringBuilder)localObject1).append(arrayOfChar, c1, paramChar);
          return ((StringBuilder)localObject1).toString();
        }
        if (c3 == '\\')
        {
          pos = c2;
          c2 = c2 - c1 - 1;
          localObject2 = localObject1;
          if (localObject1 == null) {
            localObject2 = new StringBuilder(Math.max((c2 + '\001') * 2, 16));
          }
          ((StringBuilder)localObject2).append(arrayOfChar, c1, c2);
          ((StringBuilder)localObject2).append(readEscapeCharacter());
          c1 = pos;
          i = limit;
          localObject1 = localObject2;
          break;
        }
        if (c3 == '\n')
        {
          lineNumber += 1;
          lineStart = c2;
        }
      }
      label203:
      localObject2 = localObject1;
      if (localObject1 == null) {
        localObject2 = new StringBuilder(Math.max((c3 - c1) * 2, 16));
      }
      ((StringBuilder)localObject2).append(arrayOfChar, c1, c3 - c1);
      pos = c3;
      if (!fillBuffer(1)) {
        break;
      }
    }
    localObject1 = syntaxError("Unterminated string");
    throw ((Throwable)localObject1);
  }
  
  private String nextUnquotedValue()
    throws IOException
  {
    int j = 0;
    Object localObject1 = null;
    label161:
    label183:
    label186:
    Object localObject2;
    do
    {
      i = 0;
      do
      {
        for (;;)
        {
          int k = pos;
          if (k + i >= limit) {
            break label161;
          }
          k = buffer[(k + i)];
          if ((k == 9) || (k == 10) || (k == 12) || (k == 13) || (k == 32)) {
            break label183;
          }
          if (k == 35) {
            break;
          }
          if (k == 44) {
            break label183;
          }
          if ((k == 47) || (k == 61)) {
            break;
          }
          if ((k == 123) || (k == 125) || (k == 58)) {
            break label183;
          }
          if (k == 59) {
            break;
          }
          switch (k)
          {
          default: 
            i += 1;
          }
        }
        checkLenient();
        break;
        if (i >= buffer.length) {
          break label186;
        }
      } while (fillBuffer(i + 1));
      break;
      localObject2 = localObject1;
      if (localObject1 == null) {
        localObject2 = new StringBuilder(Math.max(i, 16));
      }
      ((StringBuilder)localObject2).append(buffer, pos, i);
      pos += i;
      localObject1 = localObject2;
    } while (fillBuffer(1));
    localObject1 = localObject2;
    int i = j;
    if (localObject1 == null)
    {
      localObject1 = new String(buffer, pos, i);
    }
    else
    {
      ((StringBuilder)localObject1).append(buffer, pos, i);
      localObject1 = ((StringBuilder)localObject1).toString();
    }
    pos += i;
    return localObject1;
  }
  
  private int peekKeyword()
    throws IOException
  {
    int i = buffer[pos];
    String str1;
    String str2;
    if ((i != 116) && (i != 84))
    {
      if ((i != 102) && (i != 70))
      {
        if ((i != 110) && (i != 78)) {
          return 0;
        }
        i = 7;
        str1 = "null";
        str2 = "NULL";
      }
      else
      {
        i = 6;
        str1 = "false";
        str2 = "FALSE";
      }
    }
    else
    {
      i = 5;
      str1 = "true";
      str2 = "TRUE";
    }
    int k = str1.length();
    int j = 1;
    while (j < k)
    {
      if ((pos + j >= limit) && (!fillBuffer(j + 1))) {
        return 0;
      }
      int m = buffer[(pos + j)];
      if ((m != str1.charAt(j)) && (m != str2.charAt(j))) {
        return 0;
      }
      j += 1;
    }
    if (((pos + k < limit) || (fillBuffer(k + 1))) && (isLiteral(buffer[(pos + k)]))) {
      return 0;
    }
    pos += k;
    peeked = i;
    return i;
  }
  
  private int peekNumber()
    throws IOException
  {
    char[] arrayOfChar = buffer;
    int i3 = pos;
    int k = limit;
    int m = 0;
    int i = 0;
    int j = 1;
    long l1 = 0L;
    int n = 0;
    for (;;)
    {
      int i2 = i3;
      int i1 = k;
      if (i3 + m == k)
      {
        if (m == arrayOfChar.length) {
          return 0;
        }
        if (fillBuffer(m + 1))
        {
          i2 = pos;
          i1 = limit;
        }
      }
      else
      {
        char c = arrayOfChar[(i2 + m)];
        if (c == '+') {
          break label460;
        }
        if ((c == 'E') || (c == 'e')) {
          break label440;
        }
        if (c == '-') {
          break label418;
        }
        if (c == '.') {
          break label406;
        }
        if ((c >= '0') && (c <= '9'))
        {
          if ((i != 1) && (i != 0))
          {
            long l2;
            if (i == 2)
            {
              if (l1 == 0L) {
                return 0;
              }
              l2 = 10L * l1 - (c - '0');
              if ((l1 <= -922337203685477580L) && ((l1 != -922337203685477580L) || (l2 >= l1))) {
                k = 0;
              } else {
                k = 1;
              }
              k &= j;
            }
            else
            {
              if (i == 3)
              {
                i = 4;
                break label283;
              }
              if (i == 5) {
                break label267;
              }
              k = j;
              l2 = l1;
              if (i == 6) {
                break label267;
              }
            }
            j = k;
            l1 = l2;
            break label283;
            label267:
            i = 7;
          }
          else
          {
            l1 = -(c - '0');
            i = 2;
          }
          label283:
          break label468;
        }
        if (isLiteral(c)) {
          break label404;
        }
      }
      if ((i == 2) && (j != 0) && ((l1 != Long.MIN_VALUE) || (n != 0)) && ((l1 != 0L) || (n == 0)))
      {
        if (n == 0) {
          l1 = -l1;
        }
        peekedLong = l1;
        pos += m;
        peeked = 15;
        return 15;
      }
      if ((i != 2) && (i != 4) && (i != 7)) {
        return 0;
      }
      peekedNumberLength = m;
      peeked = 16;
      return 16;
      label404:
      return 0;
      label406:
      if (i == 2)
      {
        i = 3;
      }
      else
      {
        return 0;
        label418:
        if (i == 0)
        {
          i = 1;
          n = 1;
        }
        else if (i != 5)
        {
          return 0;
          label440:
          if ((i != 2) && (i != 4)) {
            return 0;
          }
          i = 5;
          break label468;
          label460:
          if (i != 5) {
            break;
          }
        }
        else
        {
          i = 6;
        }
      }
      label468:
      m += 1;
      i3 = i2;
      k = i1;
    }
    return 0;
  }
  
  private void push(int paramInt)
  {
    int i = stackSize;
    int[] arrayOfInt = stack;
    if (i == arrayOfInt.length)
    {
      i *= 2;
      stack = Arrays.copyOf(arrayOfInt, i);
      pathIndices = Arrays.copyOf(pathIndices, i);
      pathNames = ((String[])Arrays.copyOf(pathNames, i));
    }
    arrayOfInt = stack;
    i = stackSize;
    stackSize = (i + 1);
    arrayOfInt[i] = paramInt;
  }
  
  private char readEscapeCharacter()
    throws IOException
  {
    if ((pos == limit) && (!fillBuffer(1))) {
      throw syntaxError("Unterminated escape sequence");
    }
    Object localObject = buffer;
    int i = pos;
    pos = (i + 1);
    char c = localObject[i];
    if (c != '\n')
    {
      if ((c != '"') && (c != '\'') && (c != '/') && (c != '\\'))
      {
        if (c != 'b')
        {
          if (c != 'f')
          {
            if (c != 'n')
            {
              if (c != 'r')
              {
                if (c != 't')
                {
                  if (c == 'u')
                  {
                    if ((pos + 4 > limit) && (!fillBuffer(4))) {
                      throw syntaxError("Unterminated escape sequence");
                    }
                    c = '\000';
                    int j = pos;
                    int k;
                    for (i = j;; i = k + 1)
                    {
                      k = i;
                      if (k >= j + 4) {
                        break label317;
                      }
                      i = buffer[k];
                      int m = (char)(c << '\004');
                      if ((i >= 48) && (i <= 57))
                      {
                        i -= 48;
                        c = (char)(m + i);
                      }
                      else
                      {
                        if ((i >= 97) && (i <= 102)) {
                          i -= 97;
                        }
                        for (;;)
                        {
                          i += 10;
                          break;
                          if ((i < 65) || (i > 70)) {
                            break label264;
                          }
                          i -= 65;
                        }
                      }
                    }
                    label264:
                    localObject = new StringBuilder();
                    ((StringBuilder)localObject).append("\\u");
                    ((StringBuilder)localObject).append(new String(buffer, pos, 4));
                    throw new NumberFormatException(((StringBuilder)localObject).toString());
                    label317:
                    pos += 4;
                    return c;
                  }
                  throw syntaxError("Invalid escape sequence");
                }
                return '\t';
              }
              return '\r';
            }
            return '\n';
          }
          return '\f';
        }
        return '\b';
      }
    }
    else
    {
      lineNumber += 1;
      lineStart = pos;
    }
    return c;
  }
  
  private void skipQuotedValue(char paramChar)
    throws IOException
  {
    Object localObject = buffer;
    do
    {
      char c1 = pos;
      char c2 = limit;
      while (c1 < c2)
      {
        char c3 = c1 + '\001';
        c1 = localObject[c1];
        if (c1 == paramChar)
        {
          pos = c3;
          return;
        }
        if (c1 == '\\')
        {
          pos = c3;
          readEscapeCharacter();
          c1 = pos;
          c2 = limit;
        }
        else
        {
          if (c1 == '\n')
          {
            lineNumber += 1;
            lineStart = c3;
          }
          c1 = c3;
        }
      }
      pos = c1;
    } while (fillBuffer(1));
    localObject = syntaxError("Unterminated string");
    throw ((Throwable)localObject);
  }
  
  private boolean skipTo(String paramString)
    throws IOException
  {
    int j = paramString.length();
    int k = pos;
    int m = limit;
    int i = 0;
    if ((k + j > m) && (!fillBuffer(j))) {
      return false;
    }
    char[] arrayOfChar = buffer;
    k = pos;
    if (arrayOfChar[k] == '\n')
    {
      lineNumber += 1;
      lineStart = (k + 1);
    }
    for (;;)
    {
      if (i >= j) {
        break label128;
      }
      if (buffer[(pos + i)] != paramString.charAt(i))
      {
        pos += 1;
        break;
      }
      i += 1;
    }
    label128:
    return true;
  }
  
  private void skipToEndOfLine()
    throws IOException
  {
    int i;
    do
    {
      if ((pos >= limit) && (!fillBuffer(1))) {
        break;
      }
      char[] arrayOfChar = buffer;
      i = pos;
      pos = (i + 1);
      i = arrayOfChar[i];
      if (i == 10)
      {
        lineNumber += 1;
        lineStart = pos;
        return;
      }
    } while (i != 13);
  }
  
  private void skipUnquotedValue()
    throws IOException
  {
    label154:
    label165:
    do
    {
      int i = 0;
      int j;
      for (;;)
      {
        j = pos;
        if (j + i >= limit) {
          break label165;
        }
        j = buffer[(j + i)];
        if ((j == 9) || (j == 10) || (j == 12) || (j == 13) || (j == 32)) {
          break label154;
        }
        if (j == 35) {
          break;
        }
        if (j == 44) {
          break label154;
        }
        if ((j == 47) || (j == 61)) {
          break;
        }
        if ((j == 123) || (j == 125) || (j == 58)) {
          break label154;
        }
        if (j == 59) {
          break;
        }
        switch (j)
        {
        default: 
          i += 1;
        }
      }
      checkLenient();
      pos += i;
      return;
      pos = (j + i);
    } while (fillBuffer(1));
  }
  
  private IOException syntaxError(String paramString)
    throws IOException
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramString);
    localStringBuilder.append(locationString());
    throw new MalformedJsonException(localStringBuilder.toString());
  }
  
  public void beginArray()
    throws IOException
  {
    int j = peeked;
    int i = j;
    if (j == 0) {
      i = doPeek();
    }
    if (i == 3)
    {
      push(1);
      pathIndices[(stackSize - 1)] = 0;
      peeked = 0;
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Expected BEGIN_ARRAY but was ");
    localStringBuilder.append(peek());
    localStringBuilder.append(locationString());
    throw new IllegalStateException(localStringBuilder.toString());
  }
  
  public void beginObject()
    throws IOException
  {
    int j = peeked;
    int i = j;
    if (j == 0) {
      i = doPeek();
    }
    if (i == 1)
    {
      push(3);
      peeked = 0;
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Expected BEGIN_OBJECT but was ");
    localStringBuilder.append(peek());
    localStringBuilder.append(locationString());
    throw new IllegalStateException(localStringBuilder.toString());
  }
  
  public void close()
    throws IOException
  {
    peeked = 0;
    stack[0] = 8;
    stackSize = 1;
    in.close();
  }
  
  int doPeek()
    throws IOException
  {
    Object localObject = stack;
    int j = stackSize;
    int i = localObject[(j - 1)];
    if (i == 1)
    {
      localObject[(j - 1)] = 2;
    }
    else if (i == 2)
    {
      j = nextNonWhitespace(true);
      if (j != 44)
      {
        if (j != 59)
        {
          if (j == 93)
          {
            peeked = 4;
            return 4;
          }
          throw syntaxError("Unterminated array");
        }
        checkLenient();
      }
    }
    else
    {
      if ((i == 3) || (i == 5)) {
        break label482;
      }
      if (i == 4)
      {
        localObject[(j - 1)] = 5;
        j = nextNonWhitespace(true);
        if (j != 58) {
          if (j == 61)
          {
            checkLenient();
            if ((pos < limit) || (fillBuffer(1)))
            {
              localObject = buffer;
              j = pos;
              if (localObject[j] == '>') {
                pos = (j + 1);
              }
            }
          }
          else
          {
            throw syntaxError("Expected ':'");
          }
        }
      }
      else if (i == 6)
      {
        if (lenient) {
          consumeNonExecutePrefix();
        }
        stack[(stackSize - 1)] = 7;
      }
      else if (i == 7)
      {
        if (nextNonWhitespace(false) == -1)
        {
          peeked = 17;
          return 17;
        }
        checkLenient();
        pos -= 1;
      }
      else
      {
        if (i == 8) {
          break label471;
        }
      }
    }
    j = nextNonWhitespace(true);
    if (j != 34)
    {
      if (j != 39)
      {
        if ((j != 44) && (j != 59)) {
          if (j != 91)
          {
            if (j != 93)
            {
              if (j != 123)
              {
                pos -= 1;
                i = peekKeyword();
                if (i != 0) {
                  return i;
                }
                i = peekNumber();
                if (i != 0) {
                  return i;
                }
                if (isLiteral(buffer[pos]))
                {
                  checkLenient();
                  peeked = 10;
                  return 10;
                }
                throw syntaxError("Expected value");
              }
              peeked = 1;
              return 1;
            }
            if (i == 1)
            {
              peeked = 4;
              return 4;
            }
          }
          else
          {
            peeked = 3;
            return 3;
          }
        }
        if ((i != 1) && (i != 2)) {
          throw syntaxError("Unexpected value");
        }
        checkLenient();
        pos -= 1;
        peeked = 7;
        return 7;
      }
      checkLenient();
      peeked = 8;
      return 8;
    }
    peeked = 9;
    return 9;
    label471:
    throw new IllegalStateException("JsonReader is closed");
    label482:
    stack[(stackSize - 1)] = 4;
    if (i == 5)
    {
      j = nextNonWhitespace(true);
      if (j != 44)
      {
        if (j != 59)
        {
          if (j == 125)
          {
            peeked = 2;
            return 2;
          }
          throw syntaxError("Unterminated object");
        }
        checkLenient();
      }
    }
    j = nextNonWhitespace(true);
    if (j != 34)
    {
      if (j != 39)
      {
        if (j != 125)
        {
          checkLenient();
          pos -= 1;
          if (isLiteral((char)j))
          {
            peeked = 14;
            return 14;
          }
          throw syntaxError("Expected name");
        }
        if (i != 5)
        {
          peeked = 2;
          return 2;
        }
        throw syntaxError("Expected name");
      }
      checkLenient();
      peeked = 12;
      return 12;
    }
    peeked = 13;
    return 13;
  }
  
  public void endArray()
    throws IOException
  {
    int j = peeked;
    int i = j;
    if (j == 0) {
      i = doPeek();
    }
    if (i == 4)
    {
      stackSize -= 1;
      localObject = pathIndices;
      i = stackSize - 1;
      localObject[i] += 1;
      peeked = 0;
      return;
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Expected END_ARRAY but was ");
    ((StringBuilder)localObject).append(peek());
    ((StringBuilder)localObject).append(locationString());
    throw new IllegalStateException(((StringBuilder)localObject).toString());
  }
  
  public void endObject()
    throws IOException
  {
    int j = peeked;
    int i = j;
    if (j == 0) {
      i = doPeek();
    }
    if (i == 2)
    {
      stackSize -= 1;
      localObject = pathNames;
      i = stackSize;
      localObject[i] = null;
      localObject = pathIndices;
      i -= 1;
      localObject[i] += 1;
      peeked = 0;
      return;
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Expected END_OBJECT but was ");
    ((StringBuilder)localObject).append(peek());
    ((StringBuilder)localObject).append(locationString());
    throw new IllegalStateException(((StringBuilder)localObject).toString());
  }
  
  public String getPath()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append('$');
    int j = stackSize;
    int i = 0;
    while (i < j)
    {
      int k = stack[i];
      if ((k != 1) && (k != 2))
      {
        if ((k == 3) || (k == 4) || (k == 5))
        {
          localStringBuilder.append('.');
          String[] arrayOfString = pathNames;
          if (arrayOfString[i] != null) {
            localStringBuilder.append(arrayOfString[i]);
          }
        }
      }
      else
      {
        localStringBuilder.append('[');
        localStringBuilder.append(pathIndices[i]);
        localStringBuilder.append(']');
      }
      i += 1;
    }
    return localStringBuilder.toString();
  }
  
  public boolean hasNext()
    throws IOException
  {
    int j = peeked;
    int i = j;
    if (j == 0) {
      i = doPeek();
    }
    return (i != 2) && (i != 4);
  }
  
  public final boolean isLenient()
  {
    return lenient;
  }
  
  String locationString()
  {
    int i = lineNumber;
    int j = pos;
    int k = lineStart;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(" at line ");
    localStringBuilder.append(i + 1);
    localStringBuilder.append(" column ");
    localStringBuilder.append(j - k + 1);
    localStringBuilder.append(" path ");
    localStringBuilder.append(getPath());
    return localStringBuilder.toString();
  }
  
  public boolean nextBoolean()
    throws IOException
  {
    int j = peeked;
    int i = j;
    if (j == 0) {
      i = doPeek();
    }
    if (i == 5)
    {
      peeked = 0;
      localObject = pathIndices;
      i = stackSize - 1;
      localObject[i] += 1;
      return true;
    }
    if (i == 6)
    {
      peeked = 0;
      localObject = pathIndices;
      i = stackSize - 1;
      localObject[i] += 1;
      return false;
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Expected a boolean but was ");
    ((StringBuilder)localObject).append(peek());
    ((StringBuilder)localObject).append(locationString());
    throw new IllegalStateException(((StringBuilder)localObject).toString());
  }
  
  public double nextDouble()
    throws IOException
  {
    int j = peeked;
    int i = j;
    if (j == 0) {
      i = doPeek();
    }
    if (i == 15)
    {
      peeked = 0;
      localObject = pathIndices;
      i = stackSize - 1;
      localObject[i] += 1;
      return peekedLong;
    }
    if (i == 16)
    {
      peekedString = new String(buffer, pos, peekedNumberLength);
      pos += peekedNumberLength;
    }
    else if ((i != 8) && (i != 9))
    {
      if (i == 10)
      {
        peekedString = nextUnquotedValue();
      }
      else if (i != 11)
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("Expected a double but was ");
        ((StringBuilder)localObject).append(peek());
        ((StringBuilder)localObject).append(locationString());
        throw new IllegalStateException(((StringBuilder)localObject).toString());
      }
    }
    else
    {
      char c;
      if (i == 8) {
        c = '\'';
      } else {
        c = '"';
      }
      peekedString = nextQuotedValue(c);
    }
    peeked = 11;
    double d = Double.parseDouble(peekedString);
    if ((!lenient) && ((Double.isNaN(d)) || (Double.isInfinite(d))))
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("JSON forbids NaN and infinities: ");
      ((StringBuilder)localObject).append(d);
      ((StringBuilder)localObject).append(locationString());
      throw new MalformedJsonException(((StringBuilder)localObject).toString());
    }
    peekedString = null;
    peeked = 0;
    Object localObject = pathIndices;
    i = stackSize - 1;
    localObject[i] += 1;
    return d;
  }
  
  public int nextInt()
    throws IOException
  {
    int j = peeked;
    int i = j;
    if (j == 0) {
      i = doPeek();
    }
    if (i == 15)
    {
      long l = peekedLong;
      i = (int)l;
      if (l == i)
      {
        peeked = 0;
        localObject = pathIndices;
        j = stackSize - 1;
        localObject[j] += 1;
        return i;
      }
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("Expected an int but was ");
      ((StringBuilder)localObject).append(peekedLong);
      ((StringBuilder)localObject).append(locationString());
      throw new NumberFormatException(((StringBuilder)localObject).toString());
    }
    if (i == 16)
    {
      peekedString = new String(buffer, pos, peekedNumberLength);
      pos += peekedNumberLength;
    }
    else
    {
      if ((i != 8) && (i != 9) && (i != 10))
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("Expected an int but was ");
        ((StringBuilder)localObject).append(peek());
        ((StringBuilder)localObject).append(locationString());
        throw new IllegalStateException(((StringBuilder)localObject).toString());
      }
      if (i == 10)
      {
        peekedString = nextUnquotedValue();
      }
      else
      {
        char c;
        if (i == 8) {
          c = '\'';
        } else {
          c = '"';
        }
        peekedString = nextQuotedValue(c);
      }
      localObject = peekedString;
    }
    try
    {
      i = Integer.parseInt((String)localObject);
      peeked = 0;
      localObject = pathIndices;
      j = stackSize - 1;
      localObject[j] += 1;
      return i;
    }
    catch (NumberFormatException localNumberFormatException)
    {
      double d;
      for (;;) {}
    }
    peeked = 11;
    d = Double.parseDouble(peekedString);
    i = (int)d;
    if (i == d)
    {
      peekedString = null;
      peeked = 0;
      localObject = pathIndices;
      j = stackSize - 1;
      localObject[j] += 1;
      return i;
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Expected an int but was ");
    ((StringBuilder)localObject).append(peekedString);
    ((StringBuilder)localObject).append(locationString());
    throw new NumberFormatException(((StringBuilder)localObject).toString());
  }
  
  public long nextLong()
    throws IOException
  {
    int j = peeked;
    int i = j;
    if (j == 0) {
      i = doPeek();
    }
    if (i == 15)
    {
      peeked = 0;
      localObject = pathIndices;
      i = stackSize - 1;
      localObject[i] += 1;
      return peekedLong;
    }
    if (i == 16)
    {
      peekedString = new String(buffer, pos, peekedNumberLength);
      pos += peekedNumberLength;
    }
    else
    {
      if ((i != 8) && (i != 9) && (i != 10))
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("Expected a long but was ");
        ((StringBuilder)localObject).append(peek());
        ((StringBuilder)localObject).append(locationString());
        throw new IllegalStateException(((StringBuilder)localObject).toString());
      }
      if (i == 10)
      {
        peekedString = nextUnquotedValue();
      }
      else
      {
        char c;
        if (i == 8) {
          c = '\'';
        } else {
          c = '"';
        }
        peekedString = nextQuotedValue(c);
      }
      localObject = peekedString;
    }
    try
    {
      l = Long.parseLong((String)localObject);
      peeked = 0;
      localObject = pathIndices;
      i = stackSize - 1;
      localObject[i] += 1;
      return l;
    }
    catch (NumberFormatException localNumberFormatException)
    {
      long l;
      double d;
      for (;;) {}
    }
    peeked = 11;
    d = Double.parseDouble(peekedString);
    l = d;
    if (l == d)
    {
      peekedString = null;
      peeked = 0;
      localObject = pathIndices;
      i = stackSize - 1;
      localObject[i] += 1;
      return l;
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Expected a long but was ");
    ((StringBuilder)localObject).append(peekedString);
    ((StringBuilder)localObject).append(locationString());
    throw new NumberFormatException(((StringBuilder)localObject).toString());
  }
  
  public String nextName()
    throws IOException
  {
    int j = peeked;
    int i = j;
    if (j == 0) {
      i = doPeek();
    }
    if (i == 14)
    {
      localObject = nextUnquotedValue();
    }
    else if (i == 12)
    {
      localObject = nextQuotedValue('\'');
    }
    else
    {
      if (i != 13) {
        break label78;
      }
      localObject = nextQuotedValue('"');
    }
    peeked = 0;
    pathNames[(stackSize - 1)] = localObject;
    return localObject;
    label78:
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Expected a name but was ");
    ((StringBuilder)localObject).append(peek());
    ((StringBuilder)localObject).append(locationString());
    throw new IllegalStateException(((StringBuilder)localObject).toString());
  }
  
  public void nextNull()
    throws IOException
  {
    int j = peeked;
    int i = j;
    if (j == 0) {
      i = doPeek();
    }
    if (i == 7)
    {
      peeked = 0;
      localObject = pathIndices;
      i = stackSize - 1;
      localObject[i] += 1;
      return;
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Expected null but was ");
    ((StringBuilder)localObject).append(peek());
    ((StringBuilder)localObject).append(locationString());
    throw new IllegalStateException(((StringBuilder)localObject).toString());
  }
  
  public String nextString()
    throws IOException
  {
    int j = peeked;
    int i = j;
    if (j == 0) {
      i = doPeek();
    }
    if (i == 10)
    {
      localObject = nextUnquotedValue();
    }
    else if (i == 8)
    {
      localObject = nextQuotedValue('\'');
    }
    else if (i == 9)
    {
      localObject = nextQuotedValue('"');
    }
    else if (i == 11)
    {
      localObject = peekedString;
      peekedString = null;
    }
    else if (i == 15)
    {
      localObject = Long.toString(peekedLong);
    }
    else
    {
      if (i != 16) {
        break label167;
      }
      localObject = new String(buffer, pos, peekedNumberLength);
      pos += peekedNumberLength;
    }
    peeked = 0;
    int[] arrayOfInt = pathIndices;
    i = stackSize - 1;
    arrayOfInt[i] += 1;
    return localObject;
    label167:
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Expected a string but was ");
    ((StringBuilder)localObject).append(peek());
    ((StringBuilder)localObject).append(locationString());
    throw new IllegalStateException(((StringBuilder)localObject).toString());
  }
  
  public JsonToken peek()
    throws IOException
  {
    int j = peeked;
    int i = j;
    if (j == 0) {
      i = doPeek();
    }
    switch (i)
    {
    default: 
      throw new AssertionError();
    case 17: 
      return JsonToken.END_DOCUMENT;
    case 15: 
    case 16: 
      return JsonToken.NUMBER;
    case 12: 
    case 13: 
    case 14: 
      return JsonToken.NAME;
    case 8: 
    case 9: 
    case 10: 
    case 11: 
      return JsonToken.STRING;
    case 7: 
      return JsonToken.NULL;
    case 5: 
    case 6: 
      return JsonToken.BOOLEAN;
    case 4: 
      return JsonToken.END_ARRAY;
    case 3: 
      return JsonToken.BEGIN_ARRAY;
    case 2: 
      return JsonToken.END_OBJECT;
    }
    return JsonToken.BEGIN_OBJECT;
  }
  
  public final void setLenient(boolean paramBoolean)
  {
    lenient = paramBoolean;
  }
  
  public void skipValue()
    throws IOException
  {
    int j = 0;
    label187:
    do
    {
      i = peeked;
      int k = i;
      if (i == 0) {
        k = doPeek();
      }
      if (k == 3) {
        push(1);
      }
      for (;;)
      {
        i = j + 1;
        break label187;
        if (k != 1) {
          break;
        }
        push(3);
      }
      if (k == 4) {}
      for (stackSize -= 1;; stackSize -= 1)
      {
        i = j - 1;
        break label187;
        if (k != 2) {
          break;
        }
      }
      if ((k != 14) && (k != 10))
      {
        if ((k != 8) && (k != 12))
        {
          if ((k != 9) && (k != 13))
          {
            i = j;
            if (k == 16)
            {
              pos += peekedNumberLength;
              i = j;
            }
          }
          else
          {
            skipQuotedValue('"');
            i = j;
          }
        }
        else
        {
          skipQuotedValue('\'');
          i = j;
        }
      }
      else
      {
        skipUnquotedValue();
        i = j;
      }
      peeked = 0;
      j = i;
    } while (i != 0);
    int[] arrayOfInt = pathIndices;
    int i = stackSize;
    j = i - 1;
    arrayOfInt[j] += 1;
    pathNames[(i - 1)] = "null";
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(getClass().getSimpleName());
    localStringBuilder.append(locationString());
    return localStringBuilder.toString();
  }
}
