package com.squareup.okhttp;

import java.nio.charset.Charset;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class MediaType
{
  private static final Pattern PARAMETER = Pattern.compile(";\\s*(?:([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)=(?:([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)|\"([^\"]*)\"))?");
  private static final String QUOTED = "\"([^\"]*)\"";
  private static final String TOKEN = "([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)";
  private static final Pattern TYPE_SUBTYPE = Pattern.compile("([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)/([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)");
  private final String charset;
  private final String mediaType;
  private final String subtype;
  private final String type;
  
  private MediaType(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    mediaType = paramString1;
    type = paramString2;
    subtype = paramString3;
    charset = paramString4;
  }
  
  public static MediaType parse(String paramString)
  {
    Object localObject1 = TYPE_SUBTYPE.matcher(paramString);
    if (!((Matcher)localObject1).lookingAt()) {
      return null;
    }
    String str1 = ((Matcher)localObject1).group(1).toLowerCase(Locale.US);
    String str2 = ((Matcher)localObject1).group(2).toLowerCase(Locale.US);
    Matcher localMatcher = PARAMETER.matcher(paramString);
    int i = ((Matcher)localObject1).end();
    for (Object localObject2 = null; i < paramString.length(); localObject2 = localObject1)
    {
      localMatcher.region(i, paramString.length());
      if (!localMatcher.lookingAt()) {
        return null;
      }
      String str3 = localMatcher.group(1);
      localObject1 = localObject2;
      if (str3 != null) {
        if (!str3.equalsIgnoreCase("charset"))
        {
          localObject1 = localObject2;
        }
        else
        {
          if (localMatcher.group(2) != null) {
            localObject1 = localMatcher.group(2);
          } else {
            localObject1 = localMatcher.group(3);
          }
          if ((localObject2 != null) && (!((String)localObject1).equalsIgnoreCase((String)localObject2)))
          {
            localObject1 = new StringBuilder();
            ((StringBuilder)localObject1).append("Multiple different charsets: ");
            ((StringBuilder)localObject1).append(paramString);
            throw new IllegalArgumentException(((StringBuilder)localObject1).toString());
          }
        }
      }
      i = localMatcher.end();
    }
    return new MediaType(paramString, str1, str2, (String)localObject2);
  }
  
  public Charset charset()
  {
    String str = charset;
    if (str != null) {
      return Charset.forName(str);
    }
    return null;
  }
  
  public Charset charset(Charset paramCharset)
  {
    String str = charset;
    if (str != null) {
      paramCharset = Charset.forName(str);
    }
    return paramCharset;
  }
  
  public boolean equals(Object paramObject)
  {
    return ((paramObject instanceof MediaType)) && (mediaType.equals(mediaType));
  }
  
  public int hashCode()
  {
    return mediaType.hashCode();
  }
  
  public String subtype()
  {
    return subtype;
  }
  
  public String toString()
  {
    return mediaType;
  }
  
  public String type()
  {
    return type;
  }
}
