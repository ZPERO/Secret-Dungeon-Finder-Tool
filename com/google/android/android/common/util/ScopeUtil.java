package com.google.android.android.common.util;

import com.google.android.android.common.aimsicd.Scope;
import com.google.android.android.common.internal.Preconditions;
import java.util.Set;

public final class ScopeUtil
{
  private ScopeUtil() {}
  
  public static String[] toScopeString(Set paramSet)
  {
    Preconditions.checkNotNull(paramSet, "scopes can't be null.");
    paramSet = (Scope[])paramSet.toArray(new Scope[paramSet.size()]);
    Preconditions.checkNotNull(paramSet, "scopes can't be null.");
    String[] arrayOfString = new String[paramSet.length];
    int i = 0;
    while (i < paramSet.length)
    {
      arrayOfString[i] = paramSet[i].getScopeUri();
      i += 1;
    }
    return arrayOfString;
  }
}
