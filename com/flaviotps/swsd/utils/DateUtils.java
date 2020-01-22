package com.flaviotps.swsd.utils;

import java.util.Calendar;
import java.util.TimeZone;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000\f\n\002\030\002\n\002\020\000\n\002\b\003\030\000 \0032\0020\001:\001\003B\005?\006\002\020\002?\006\004"}, d2={"Lcom/flaviotps/swsd/utils/DateUtils;", "", "()V", "Companion", "app_release"}, k=1, mv={1, 1, 16})
public final class DateUtils
{
  public static final Companion Companion = new Companion(null);
  private static final int MILLISECONDS_IN_MINUTE = 60000;
  
  public DateUtils() {}
  
  @Metadata(bv={1, 0, 3}, d1={"\000\032\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\b\n\000\n\002\020\t\n\002\b\004\b?\003\030\0002\0020\001B\007\b\002?\006\002\020\002J\006\020\005\032\0020\006J\026\020\007\032\0020\0062\006\020\b\032\0020\0062\006\020\t\032\0020\004R\016\020\003\032\0020\004X?T?\006\002\n\000?\006\n"}, d2={"Lcom/flaviotps/swsd/utils/DateUtils$Companion;", "", "()V", "MILLISECONDS_IN_MINUTE", "", "getCreationTime", "", "getEndTime", "creationTime", "timeLeft", "app_release"}, k=1, mv={1, 1, 16})
  public static final class Companion
  {
    private Companion() {}
    
    public final long getCreationTime()
    {
      Calendar localCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
      Intrinsics.checkExpressionValueIsNotNull(localCalendar, "Calendar.getInstance(TimeZone.getTimeZone(\"UTC\"))");
      return localCalendar.getTimeInMillis();
    }
    
    public final long getEndTime(long paramLong, int paramInt)
    {
      return paramLong + paramInt * 60000;
    }
  }
}
