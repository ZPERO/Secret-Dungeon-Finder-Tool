package com.google.firebase.database.core.utilities;

import android.util.Base64;
import com.google.android.android.tasks.TaskCompletionSource;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseReference.CompletionListener;
import com.google.firebase.database.core.Path;
import com.google.firebase.database.core.RepoInfo;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Map;

public class Utilities
{
  private static final char[] HEX_CHARACTERS = "0123456789abcdef".toCharArray();
  
  public Utilities() {}
  
  public static Object castOrNull(Object paramObject, Class paramClass)
  {
    if (paramClass.isAssignableFrom(paramObject.getClass())) {
      return paramObject;
    }
    return null;
  }
  
  public static int compareInts(int paramInt1, int paramInt2)
  {
    if (paramInt1 < paramInt2) {
      return -1;
    }
    if (paramInt1 == paramInt2) {
      return 0;
    }
    return 1;
  }
  
  public static int compareLongs(long paramLong1, long paramLong2)
  {
    if (paramLong1 < paramLong2) {
      return -1;
    }
    if (paramLong1 == paramLong2) {
      return 0;
    }
    return 1;
  }
  
  public static String doubleToHashString(double paramDouble)
  {
    StringBuilder localStringBuilder = new StringBuilder(16);
    long l = Double.doubleToLongBits(paramDouble);
    int i = 7;
    while (i >= 0)
    {
      int j = (int)(l >>> i * 8 & 0xFF);
      localStringBuilder.append(HEX_CHARACTERS[(j >> 4 & 0xF)]);
      localStringBuilder.append(HEX_CHARACTERS[(j & 0xF)]);
      i -= 1;
    }
    return localStringBuilder.toString();
  }
  
  public static Object getOrNull(Object paramObject, String paramString, Class paramClass)
  {
    if (paramObject == null) {
      return null;
    }
    paramObject = ((Map)castOrNull(paramObject, Map.class)).get(paramString);
    if (paramObject != null) {
      return castOrNull(paramObject, paramClass);
    }
    return null;
  }
  
  public static void hardAssert(boolean paramBoolean)
  {
    hardAssert(paramBoolean, "");
  }
  
  public static void hardAssert(boolean paramBoolean, String paramString)
  {
    if (paramBoolean) {
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("hardAssert failed: ");
    localStringBuilder.append(paramString);
    throw new AssertionError(localStringBuilder.toString());
  }
  
  public static ParsedUrl parseUrl(String paramString)
    throws DatabaseException
  {
    try
    {
      int i = paramString.indexOf("//");
      if (i != -1)
      {
        i += 2;
        int j = paramString.substring(i).indexOf("/");
        Object localObject1 = paramString;
        boolean bool;
        if (j != -1)
        {
          j += i;
          localObject2 = paramString.substring(j).split("/", -1);
          localObject1 = new StringBuilder();
          i = 0;
          while (i < localObject2.length)
          {
            str1 = localObject2[i];
            bool = str1.equals("");
            if (!bool)
            {
              ((StringBuilder)localObject1).append("/");
              str1 = localObject2[i];
              ((StringBuilder)localObject1).append(URLEncoder.encode(str1, "UTF-8"));
            }
            i += 1;
          }
          localObject2 = new StringBuilder();
          ((StringBuilder)localObject2).append(paramString.substring(0, j));
          ((StringBuilder)localObject2).append(((StringBuilder)localObject1).toString());
          localObject1 = ((StringBuilder)localObject2).toString();
        }
        Object localObject2 = new URI((String)localObject1);
        paramString = ((URI)localObject2).getPath().replace("+", " ");
        Validation.validateRootPathString(paramString);
        paramString = new Path(paramString);
        String str1 = ((URI)localObject2).getScheme();
        localObject1 = new RepoInfo();
        String str2 = ((URI)localObject2).getHost().toLowerCase();
        host = str2;
        i = ((URI)localObject2).getPort();
        if (i != -1)
        {
          bool = str1.equals("https");
          secure = bool;
          localObject2 = new StringBuilder();
          str1 = host;
          ((StringBuilder)localObject2).append(str1);
          ((StringBuilder)localObject2).append(":");
          ((StringBuilder)localObject2).append(i);
          localObject2 = ((StringBuilder)localObject2).toString();
          host = ((String)localObject2);
        }
        else
        {
          secure = true;
        }
        localObject2 = host;
        localObject2 = ((String)localObject2).split("\\.", -1);
        localObject2 = localObject2[0];
        localObject2 = ((String)localObject2).toLowerCase();
        namespace = ((String)localObject2);
        internalHost = host;
        localObject2 = new ParsedUrl();
        path = paramString;
        repoInfo = ((RepoInfo)localObject1);
        return localObject2;
      }
      paramString = new URISyntaxException(paramString, "Invalid scheme specified");
      throw paramString;
    }
    catch (UnsupportedEncodingException paramString)
    {
      throw new DatabaseException("Failed to URLEncode the path", paramString);
    }
    catch (URISyntaxException paramString)
    {
      paramString = new DatabaseException("Invalid Firebase Database url specified", paramString);
      throw paramString;
    }
  }
  
  public static String sha1HexDigest(String paramString)
  {
    try
    {
      MessageDigest localMessageDigest = MessageDigest.getInstance("SHA-1");
      localMessageDigest.update(paramString.getBytes("UTF-8"));
      paramString = Base64.encodeToString(localMessageDigest.digest(), 2);
      return paramString;
    }
    catch (NoSuchAlgorithmException paramString)
    {
      throw new RuntimeException("Missing SHA-1 MessageDigest provider.", paramString);
    }
    catch (UnsupportedEncodingException paramString)
    {
      for (;;) {}
    }
    throw new RuntimeException("UTF-8 encoding is required for Firebase Database to run!");
  }
  
  public static String[] splitIntoFrames(String paramString, int paramInt)
  {
    int j = paramString.length();
    int i = 0;
    if (j <= paramInt) {
      return new String[] { paramString };
    }
    ArrayList localArrayList = new ArrayList();
    while (i < paramString.length())
    {
      j = i + paramInt;
      localArrayList.add(paramString.substring(i, Math.min(j, paramString.length())));
      i = j;
    }
    return (String[])localArrayList.toArray(new String[localArrayList.size()]);
  }
  
  public static String stringHashV2Representation(String paramString)
  {
    String str1;
    if (paramString.indexOf('\\') != -1) {
      str1 = paramString.replace("\\", "\\\\");
    } else {
      str1 = paramString;
    }
    String str2 = str1;
    if (paramString.indexOf('"') != -1) {
      str2 = str1.replace("\"", "\\\"");
    }
    paramString = new StringBuilder();
    paramString.append('"');
    paramString.append(str2);
    paramString.append('"');
    return paramString.toString();
  }
  
  public static Integer tryParseInt(String paramString)
  {
    if (paramString.length() <= 11)
    {
      if (paramString.length() == 0) {
        return null;
      }
      int i = 0;
      int k = paramString.charAt(0);
      int j = 1;
      if (k == 45)
      {
        if (paramString.length() == 1) {
          return null;
        }
        i = 1;
      }
      else
      {
        j = 0;
      }
      long l = 0L;
      while (i < paramString.length())
      {
        k = paramString.charAt(i);
        if (k >= 48)
        {
          if (k > 57) {
            return null;
          }
          l = l * 10L + (k - 48);
          i += 1;
        }
        else
        {
          return null;
        }
      }
      if (j != 0)
      {
        l = -l;
        if (l < -2147483648L) {
          return null;
        }
        return Integer.valueOf((int)l);
      }
      if (l > 2147483647L) {
        return null;
      }
      return Integer.valueOf((int)l);
    }
    return null;
  }
  
  public static Pair wrapOnComplete(DatabaseReference.CompletionListener paramCompletionListener)
  {
    if (paramCompletionListener == null)
    {
      paramCompletionListener = new TaskCompletionSource();
      DatabaseReference.CompletionListener local1 = new DatabaseReference.CompletionListener()
      {
        public void onComplete(DatabaseError paramAnonymousDatabaseError, DatabaseReference paramAnonymousDatabaseReference)
        {
          if (paramAnonymousDatabaseError != null)
          {
            setException(paramAnonymousDatabaseError.toException());
            return;
          }
          setResult(null);
        }
      };
      return new Pair(paramCompletionListener.getTask(), local1);
    }
    return new Pair(null, paramCompletionListener);
  }
}
