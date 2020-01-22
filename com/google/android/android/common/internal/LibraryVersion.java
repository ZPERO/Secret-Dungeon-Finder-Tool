package com.google.android.android.common.internal;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class LibraryVersion
{
  private static final GmsLogger zzel = new GmsLogger("LibraryVersion", "");
  private static LibraryVersion zzem = new LibraryVersion();
  private ConcurrentHashMap<String, String> zzen = new ConcurrentHashMap();
  
  protected LibraryVersion() {}
  
  public static LibraryVersion getInstance()
  {
    return zzem;
  }
  
  public String getVersion(String paramString)
  {
    Preconditions.checkNotEmpty(paramString, "Please provide a valid libraryName");
    if (zzen.containsKey(paramString)) {
      return (String)zzen.get(paramString);
    }
    Object localObject2 = new Properties();
    GmsLogger localGmsLogger = null;
    String str = null;
    Object localObject1 = str;
    try
    {
      Object localObject3 = String.format("/%s.properties", new Object[] { paramString });
      localObject1 = str;
      localObject3 = com.google.android.gms.common.internal.LibraryVersion.class.getResourceAsStream((String)localObject3);
      int i;
      if (localObject3 != null)
      {
        localObject1 = str;
        ((Properties)localObject2).load((InputStream)localObject3);
        localObject1 = str;
        str = ((Properties)localObject2).getProperty("version", null);
        localObject2 = str;
        localGmsLogger = zzel;
        localObject1 = localObject2;
        i = String.valueOf(paramString).length();
        localObject1 = localObject2;
        int j = String.valueOf(str).length();
        localObject1 = localObject2;
        localObject3 = new StringBuilder(i + 12 + j);
        localObject1 = localObject2;
        ((StringBuilder)localObject3).append(paramString);
        localObject1 = localObject2;
        ((StringBuilder)localObject3).append(" version is ");
        localObject1 = localObject2;
        ((StringBuilder)localObject3).append(str);
        localObject1 = localObject2;
        localGmsLogger.v("LibraryVersion", ((StringBuilder)localObject3).toString());
        localObject1 = localObject2;
      }
      else
      {
        localObject3 = zzel;
        localObject1 = str;
        localObject2 = String.valueOf(paramString);
        localObject1 = str;
        i = ((String)localObject2).length();
        if (i != 0)
        {
          localObject1 = str;
          localObject2 = "Failed to get app version for libraryName: ".concat((String)localObject2);
        }
        else
        {
          localObject1 = str;
          localObject2 = new String("Failed to get app version for libraryName: ");
        }
        localObject1 = str;
        ((GmsLogger)localObject3).append("LibraryVersion", (String)localObject2);
        localObject1 = localGmsLogger;
      }
    }
    catch (IOException localIOException)
    {
      localGmsLogger = zzel;
      localObject2 = String.valueOf(paramString);
      if (((String)localObject2).length() != 0) {
        localObject2 = "Failed to get app version for libraryName: ".concat((String)localObject2);
      } else {
        localObject2 = new String("Failed to get app version for libraryName: ");
      }
      localGmsLogger.create("LibraryVersion", (String)localObject2, localIOException);
    }
    localObject2 = localObject1;
    if (localObject1 == null)
    {
      zzel.d("LibraryVersion", ".properties file is dropped during release process. Failure to read app version isexpected druing Google internal testing where locally-built libraries are used");
      localObject2 = "UNKNOWN";
    }
    zzen.put(paramString, localObject2);
    return localObject2;
  }
}
