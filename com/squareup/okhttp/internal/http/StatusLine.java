package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.Response;
import java.io.IOException;
import java.net.ProtocolException;

public final class StatusLine
{
  public static final int HTTP_CONTINUE = 100;
  public static final int HTTP_PERM_REDIRECT = 308;
  public static final int HTTP_TEMP_REDIRECT = 307;
  public final int code;
  public final String message;
  public final Protocol protocol;
  
  public StatusLine(Protocol paramProtocol, int paramInt, String paramString)
  {
    protocol = paramProtocol;
    code = paramInt;
    message = paramString;
  }
  
  public static StatusLine get(Response paramResponse)
  {
    return new StatusLine(paramResponse.protocol(), paramResponse.code(), paramResponse.message());
  }
  
  public static StatusLine parse(String paramString)
    throws IOException
  {
    boolean bool = paramString.startsWith("HTTP/1.");
    int i = 9;
    if (bool)
    {
      if ((paramString.length() >= 9) && (paramString.charAt(8) == ' '))
      {
        j = paramString.charAt(7) - '0';
        if (j == 0)
        {
          localObject = Protocol.HTTP_1_0;
        }
        else if (j == 1)
        {
          localObject = Protocol.HTTP_1_1;
        }
        else
        {
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("Unexpected status line: ");
          ((StringBuilder)localObject).append(paramString);
          throw new ProtocolException(((StringBuilder)localObject).toString());
        }
      }
      else
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("Unexpected status line: ");
        ((StringBuilder)localObject).append(paramString);
        throw new ProtocolException(((StringBuilder)localObject).toString());
      }
    }
    else
    {
      if (!paramString.startsWith("ICY ")) {
        break label340;
      }
      localObject = Protocol.HTTP_1_0;
      i = 4;
    }
    int k = paramString.length();
    int j = i + 3;
    if (k >= j) {}
    try
    {
      k = Integer.parseInt(paramString.substring(i, j));
      if (paramString.length() > j)
      {
        if (paramString.charAt(j) == ' ')
        {
          paramString = paramString.substring(i + 4);
        }
        else
        {
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("Unexpected status line: ");
          ((StringBuilder)localObject).append(paramString);
          throw new ProtocolException(((StringBuilder)localObject).toString());
        }
      }
      else {
        paramString = "";
      }
      return new StatusLine((Protocol)localObject, k, paramString);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      for (;;) {}
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Unexpected status line: ");
    ((StringBuilder)localObject).append(paramString);
    throw new ProtocolException(((StringBuilder)localObject).toString());
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Unexpected status line: ");
    ((StringBuilder)localObject).append(paramString);
    throw new ProtocolException(((StringBuilder)localObject).toString());
    label340:
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Unexpected status line: ");
    ((StringBuilder)localObject).append(paramString);
    throw new ProtocolException(((StringBuilder)localObject).toString());
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    String str;
    if (protocol == Protocol.HTTP_1_0) {
      str = "HTTP/1.0";
    } else {
      str = "HTTP/1.1";
    }
    localStringBuilder.append(str);
    localStringBuilder.append(' ');
    localStringBuilder.append(code);
    if (message != null)
    {
      localStringBuilder.append(' ');
      localStringBuilder.append(message);
    }
    return localStringBuilder.toString();
  }
}
