package com.google.android.android.common.util;

import android.content.Context;
import android.os.DropBoxManager;
import android.util.Log;
import com.google.android.android.common.internal.Preconditions;

public final class CrashUtils
{
  private static final String[] zzge = { "android.", "com.android.", "dalvik.", "java.", "javax." };
  private static DropBoxManager zzgf;
  private static boolean zzgg;
  private static int zzgh;
  private static int zzgi;
  private static int zzgj;
  
  public CrashUtils() {}
  
  public static boolean addDynamiteErrorToDropBox(Context paramContext, Throwable paramThrowable)
  {
    return tryGCOrWait(paramContext, paramThrowable, 536870912);
  }
  
  private static boolean tryGCOrWait(Context paramContext, Throwable paramThrowable, int paramInt)
  {
    try
    {
      Preconditions.checkNotNull(paramContext);
      Preconditions.checkNotNull(paramThrowable);
      return false;
    }
    catch (Exception paramContext)
    {
      Log.e("CrashUtils", "Error adding exception to DropBox!", paramContext);
    }
    return false;
  }
}
