package kotlin.time;

import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\000\024\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\f\b\003\030\0002\0020\001B\030\022\006\020\002\032\0020\001\022\006\020\003\032\0020\004?\001\000?\006\002\020\005J\020\020\013\032\0020\004H\026?\001\000?\006\002\020\007J\033\020\f\032\0020\0012\006\020\r\032\0020\004H?\002?\001\000?\006\004\b\016\020\017R\026\020\003\032\0020\004?\001\000?\006\n\n\002\020\b\032\004\b\006\020\007R\021\020\002\032\0020\001?\006\b\n\000\032\004\b\t\020\n?\002\004\n\002\b\031?\006\020"}, d2={"Lkotlin/time/AdjustedClockMark;", "Lkotlin/time/ClockMark;", "mark", "adjustment", "Lkotlin/time/Duration;", "(Lkotlin/time/ClockMark;DLkotlin/jvm/internal/DefaultConstructorMarker;)V", "getAdjustment", "()D", "D", "getMark", "()Lkotlin/time/ClockMark;", "elapsedNow", "plus", "duration", "plus-LRDsOJo", "(D)Lkotlin/time/ClockMark;", "kotlin-stdlib"}, k=1, mv={1, 1, 15})
final class AdjustedClockMark
  extends ClockMark
{
  private final double adjustment;
  private final ClockMark mark;
  
  private AdjustedClockMark(ClockMark paramClockMark, double paramDouble)
  {
    mark = paramClockMark;
    adjustment = paramDouble;
  }
  
  public double elapsedNow()
  {
    return Duration.minus-LRDsOJo(mark.elapsedNow(), adjustment);
  }
  
  public final double getAdjustment()
  {
    return adjustment;
  }
  
  public final ClockMark getMark()
  {
    return mark;
  }
  
  public ClockMark plus-LRDsOJo(double paramDouble)
  {
    return (ClockMark)new AdjustedClockMark(mark, Duration.plus-LRDsOJo(adjustment, paramDouble), null);
  }
}
