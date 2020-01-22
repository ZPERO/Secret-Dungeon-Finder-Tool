package com.squareup.okhttp;

import com.squareup.okhttp.internal.Util;

public final class Challenge
{
  private final String realm;
  private final String scheme;
  
  public Challenge(String paramString1, String paramString2)
  {
    scheme = paramString1;
    realm = paramString2;
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject instanceof Challenge))
    {
      String str = scheme;
      paramObject = (Challenge)paramObject;
      if ((Util.equal(str, scheme)) && (Util.equal(realm, realm))) {
        return true;
      }
    }
    return false;
  }
  
  public String getRealm()
  {
    return realm;
  }
  
  public String getScheme()
  {
    return scheme;
  }
  
  public int hashCode()
  {
    String str = realm;
    int j = 0;
    int i;
    if (str != null) {
      i = str.hashCode();
    } else {
      i = 0;
    }
    str = scheme;
    if (str != null) {
      j = str.hashCode();
    }
    return (899 + i) * 31 + j;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(scheme);
    localStringBuilder.append(" realm=\"");
    localStringBuilder.append(realm);
    localStringBuilder.append("\"");
    return localStringBuilder.toString();
  }
}
