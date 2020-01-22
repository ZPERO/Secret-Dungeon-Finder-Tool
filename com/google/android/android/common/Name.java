package com.google.android.android.common;

import android.os.RemoteException;
import android.util.Log;
import com.google.android.android.common.internal.IMediaSession;
import com.google.android.android.common.internal.Preconditions;
import com.google.android.android.common.internal.Property;
import com.google.android.android.dynamic.IObjectWrapper;
import com.google.android.android.dynamic.ObjectWrapper;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

abstract class Name
  extends Property
{
  private int hashcode;
  
  protected Name(byte[] paramArrayOfByte)
  {
    boolean bool;
    if (paramArrayOfByte.length == 25) {
      bool = true;
    } else {
      bool = false;
    }
    Preconditions.checkArgument(bool);
    hashcode = Arrays.hashCode(paramArrayOfByte);
  }
  
  protected static byte[] getBytes(String paramString)
  {
    try
    {
      paramString = paramString.getBytes("ISO-8859-1");
      return paramString;
    }
    catch (UnsupportedEncodingException paramString)
    {
      throw new AssertionError(paramString);
    }
  }
  
  public boolean equals(Object paramObject)
  {
    if (paramObject != null)
    {
      if (!(paramObject instanceof IMediaSession)) {
        return false;
      }
      paramObject = (IMediaSession)paramObject;
      try
      {
        int i = paramObject.stop();
        int j = hashCode();
        if (i != j) {
          return false;
        }
        paramObject = paramObject.next();
        if (paramObject == null) {
          return false;
        }
        paramObject = ObjectWrapper.unwrap(paramObject);
        paramObject = (byte[])paramObject;
        boolean bool = Arrays.equals(getBytes(), paramObject);
        return bool;
      }
      catch (RemoteException paramObject)
      {
        Log.e("GoogleCertificates", "Failed to get Google certificates from remote", paramObject);
      }
    }
    return false;
  }
  
  abstract byte[] getBytes();
  
  public int hashCode()
  {
    return hashcode;
  }
  
  public final IObjectWrapper next()
  {
    return ObjectWrapper.wrap(getBytes());
  }
  
  public final int stop()
  {
    return hashCode();
  }
}
