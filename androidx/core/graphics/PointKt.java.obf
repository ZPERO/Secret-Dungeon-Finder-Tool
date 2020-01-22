package androidx.core.graphics;

import android.graphics.Point;
import android.graphics.PointF;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000\026\n\000\n\002\020\b\n\002\030\002\n\002\020\007\n\002\030\002\n\002\b\t\032\r\020\000\032\0020\001*\0020\002H?\n\032\r\020\000\032\0020\003*\0020\004H?\n\032\r\020\005\032\0020\001*\0020\002H?\n\032\r\020\005\032\0020\003*\0020\004H?\n\032\025\020\006\032\0020\002*\0020\0022\006\020\007\032\0020\002H?\n\032\025\020\006\032\0020\002*\0020\0022\006\020\b\032\0020\001H?\n\032\025\020\006\032\0020\004*\0020\0042\006\020\007\032\0020\004H?\n\032\025\020\006\032\0020\004*\0020\0042\006\020\b\032\0020\003H?\n\032\025\020\t\032\0020\002*\0020\0022\006\020\007\032\0020\002H?\n\032\025\020\t\032\0020\002*\0020\0022\006\020\b\032\0020\001H?\n\032\025\020\t\032\0020\004*\0020\0042\006\020\007\032\0020\004H?\n\032\025\020\t\032\0020\004*\0020\0042\006\020\b\032\0020\003H?\n\032\r\020\n\032\0020\002*\0020\004H?\b\032\r\020\013\032\0020\004*\0020\002H?\b\032\r\020\f\032\0020\002*\0020\002H?\n\032\r\020\f\032\0020\004*\0020\004H?\n?\006\r"}, d2={"component1", "", "Landroid/graphics/Point;", "", "Landroid/graphics/PointF;", "component2", "minus", "p", "xy", "plus", "toPoint", "toPointF", "unaryMinus", "core-ktx_release"}, k=2, mv={1, 1, 15})
public final class PointKt
{
  public static final float component1(PointF paramPointF)
  {
    Intrinsics.checkParameterIsNotNull(paramPointF, "$this$component1");
    return x;
  }
  
  public static final int component1(Point paramPoint)
  {
    Intrinsics.checkParameterIsNotNull(paramPoint, "$this$component1");
    return x;
  }
  
  public static final float component2(PointF paramPointF)
  {
    Intrinsics.checkParameterIsNotNull(paramPointF, "$this$component2");
    return y;
  }
  
  public static final int component2(Point paramPoint)
  {
    Intrinsics.checkParameterIsNotNull(paramPoint, "$this$component2");
    return y;
  }
  
  public static final Point minus(Point paramPoint, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramPoint, "$this$minus");
    paramPoint = new Point(x, y);
    paramInt = -paramInt;
    paramPoint.offset(paramInt, paramInt);
    return paramPoint;
  }
  
  public static final Point minus(Point paramPoint1, Point paramPoint2)
  {
    Intrinsics.checkParameterIsNotNull(paramPoint1, "$this$minus");
    Intrinsics.checkParameterIsNotNull(paramPoint2, "p");
    paramPoint1 = new Point(x, y);
    paramPoint1.offset(-x, -y);
    return paramPoint1;
  }
  
  public static final PointF minus(PointF paramPointF, float paramFloat)
  {
    Intrinsics.checkParameterIsNotNull(paramPointF, "$this$minus");
    paramPointF = new PointF(x, y);
    paramFloat = -paramFloat;
    paramPointF.offset(paramFloat, paramFloat);
    return paramPointF;
  }
  
  public static final PointF minus(PointF paramPointF1, PointF paramPointF2)
  {
    Intrinsics.checkParameterIsNotNull(paramPointF1, "$this$minus");
    Intrinsics.checkParameterIsNotNull(paramPointF2, "p");
    paramPointF1 = new PointF(x, y);
    paramPointF1.offset(-x, -y);
    return paramPointF1;
  }
  
  public static final Point plus(Point paramPoint, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramPoint, "$this$plus");
    paramPoint = new Point(x, y);
    paramPoint.offset(paramInt, paramInt);
    return paramPoint;
  }
  
  public static final Point plus(Point paramPoint1, Point paramPoint2)
  {
    Intrinsics.checkParameterIsNotNull(paramPoint1, "$this$plus");
    Intrinsics.checkParameterIsNotNull(paramPoint2, "p");
    paramPoint1 = new Point(x, y);
    paramPoint1.offset(x, y);
    return paramPoint1;
  }
  
  public static final PointF plus(PointF paramPointF, float paramFloat)
  {
    Intrinsics.checkParameterIsNotNull(paramPointF, "$this$plus");
    paramPointF = new PointF(x, y);
    paramPointF.offset(paramFloat, paramFloat);
    return paramPointF;
  }
  
  public static final PointF plus(PointF paramPointF1, PointF paramPointF2)
  {
    Intrinsics.checkParameterIsNotNull(paramPointF1, "$this$plus");
    Intrinsics.checkParameterIsNotNull(paramPointF2, "p");
    paramPointF1 = new PointF(x, y);
    paramPointF1.offset(x, y);
    return paramPointF1;
  }
  
  public static final Point toPoint(PointF paramPointF)
  {
    Intrinsics.checkParameterIsNotNull(paramPointF, "$this$toPoint");
    return new Point((int)x, (int)y);
  }
  
  public static final PointF toPointF(Point paramPoint)
  {
    Intrinsics.checkParameterIsNotNull(paramPoint, "$this$toPointF");
    return new PointF(paramPoint);
  }
  
  public static final Point unaryMinus(Point paramPoint)
  {
    Intrinsics.checkParameterIsNotNull(paramPoint, "$this$unaryMinus");
    return new Point(-x, -y);
  }
  
  public static final PointF unaryMinus(PointF paramPointF)
  {
    Intrinsics.checkParameterIsNotNull(paramPointF, "$this$unaryMinus");
    return new PointF(-x, -y);
  }
}
