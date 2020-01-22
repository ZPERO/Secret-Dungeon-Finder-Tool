package kotlin.time;

import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\000\020\n\002\030\002\n\002\020\000\n\000\n\002\030\002\n\000\bg\030\0002\0020\001J\b\020\002\032\0020\003H&?\006\004"}, d2={"Lkotlin/time/Clock;", "", "markNow", "Lkotlin/time/ClockMark;", "kotlin-stdlib"}, k=1, mv={1, 1, 15})
public abstract interface Clock
{
  public abstract ClockMark markNow();
}
