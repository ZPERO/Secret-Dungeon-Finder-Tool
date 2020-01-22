package okio;

import javax.annotation.Nullable;

final class SegmentPool
{
  static final long MAX_SIZE = 65536L;
  static long byteCount;
  @Nullable
  static Segment next;
  
  private SegmentPool() {}
  
  static void recycle(Segment paramSegment)
  {
    if ((next == null) && (prev == null))
    {
      if (shared) {
        return;
      }
      try
      {
        if (byteCount + 8192L > 65536L) {
          return;
        }
        byteCount += 8192L;
        next = next;
        limit = 0;
        pos = 0;
        next = paramSegment;
        return;
      }
      catch (Throwable paramSegment)
      {
        throw paramSegment;
      }
    }
    throw new IllegalArgumentException();
  }
  
  static Segment take()
  {
    try
    {
      if (next != null)
      {
        Segment localSegment = next;
        next = next;
        next = null;
        byteCount -= 8192L;
        return localSegment;
      }
      return new Segment();
    }
    catch (Throwable localThrowable)
    {
      throw localThrowable;
    }
  }
}
