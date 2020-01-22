package kotlin.comparisons;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000(\n\002\b\002\n\002\020\017\n\002\b\005\n\002\020\005\n\002\020\006\n\002\020\007\n\002\020\b\n\002\020\t\n\002\020\n\n\002\b\002\032-\020\000\032\002H\001\"\016\b\000\020\001*\b\022\004\022\002H\0010\0022\006\020\003\032\002H\0012\006\020\004\032\002H\001H\007?\006\002\020\005\0325\020\000\032\002H\001\"\016\b\000\020\001*\b\022\004\022\002H\0010\0022\006\020\003\032\002H\0012\006\020\004\032\002H\0012\006\020\006\032\002H\001H\007?\006\002\020\007\032\031\020\000\032\0020\b2\006\020\003\032\0020\b2\006\020\004\032\0020\bH?\b\032!\020\000\032\0020\b2\006\020\003\032\0020\b2\006\020\004\032\0020\b2\006\020\006\032\0020\bH?\b\032\031\020\000\032\0020\t2\006\020\003\032\0020\t2\006\020\004\032\0020\tH?\b\032!\020\000\032\0020\t2\006\020\003\032\0020\t2\006\020\004\032\0020\t2\006\020\006\032\0020\tH?\b\032\031\020\000\032\0020\n2\006\020\003\032\0020\n2\006\020\004\032\0020\nH?\b\032!\020\000\032\0020\n2\006\020\003\032\0020\n2\006\020\004\032\0020\n2\006\020\006\032\0020\nH?\b\032\031\020\000\032\0020\0132\006\020\003\032\0020\0132\006\020\004\032\0020\013H?\b\032!\020\000\032\0020\0132\006\020\003\032\0020\0132\006\020\004\032\0020\0132\006\020\006\032\0020\013H?\b\032\031\020\000\032\0020\f2\006\020\003\032\0020\f2\006\020\004\032\0020\fH?\b\032!\020\000\032\0020\f2\006\020\003\032\0020\f2\006\020\004\032\0020\f2\006\020\006\032\0020\fH?\b\032\031\020\000\032\0020\r2\006\020\003\032\0020\r2\006\020\004\032\0020\rH?\b\032!\020\000\032\0020\r2\006\020\003\032\0020\r2\006\020\004\032\0020\r2\006\020\006\032\0020\rH?\b\032-\020\016\032\002H\001\"\016\b\000\020\001*\b\022\004\022\002H\0010\0022\006\020\003\032\002H\0012\006\020\004\032\002H\001H\007?\006\002\020\005\0325\020\016\032\002H\001\"\016\b\000\020\001*\b\022\004\022\002H\0010\0022\006\020\003\032\002H\0012\006\020\004\032\002H\0012\006\020\006\032\002H\001H\007?\006\002\020\007\032\031\020\016\032\0020\b2\006\020\003\032\0020\b2\006\020\004\032\0020\bH?\b\032!\020\016\032\0020\b2\006\020\003\032\0020\b2\006\020\004\032\0020\b2\006\020\006\032\0020\bH?\b\032\031\020\016\032\0020\t2\006\020\003\032\0020\t2\006\020\004\032\0020\tH?\b\032!\020\016\032\0020\t2\006\020\003\032\0020\t2\006\020\004\032\0020\t2\006\020\006\032\0020\tH?\b\032\031\020\016\032\0020\n2\006\020\003\032\0020\n2\006\020\004\032\0020\nH?\b\032!\020\016\032\0020\n2\006\020\003\032\0020\n2\006\020\004\032\0020\n2\006\020\006\032\0020\nH?\b\032\031\020\016\032\0020\0132\006\020\003\032\0020\0132\006\020\004\032\0020\013H?\b\032!\020\016\032\0020\0132\006\020\003\032\0020\0132\006\020\004\032\0020\0132\006\020\006\032\0020\013H?\b\032\031\020\016\032\0020\f2\006\020\003\032\0020\f2\006\020\004\032\0020\fH?\b\032!\020\016\032\0020\f2\006\020\003\032\0020\f2\006\020\004\032\0020\f2\006\020\006\032\0020\fH?\b\032\031\020\016\032\0020\r2\006\020\003\032\0020\r2\006\020\004\032\0020\rH?\b\032!\020\016\032\0020\r2\006\020\003\032\0020\r2\006\020\004\032\0020\r2\006\020\006\032\0020\rH?\b?\006\017"}, d2={"maxOf", "T", "", "a", "b", "(Ljava/lang/Comparable;Ljava/lang/Comparable;)Ljava/lang/Comparable;", "c", "(Ljava/lang/Comparable;Ljava/lang/Comparable;Ljava/lang/Comparable;)Ljava/lang/Comparable;", "", "", "", "", "", "", "minOf", "kotlin-stdlib"}, k=5, mv={1, 1, 15}, xi=1, xs="kotlin/comparisons/ComparisonsKt")
class ComparisonsKt___ComparisonsJvmKt
  extends ComparisonsKt__ComparisonsKt
{
  public ComparisonsKt___ComparisonsJvmKt() {}
  
  private static final byte maxOf(byte paramByte1, byte paramByte2)
  {
    return (byte)Math.max(paramByte1, paramByte2);
  }
  
  private static final byte maxOf(byte paramByte1, byte paramByte2, byte paramByte3)
  {
    return (byte)Math.max(paramByte1, Math.max(paramByte2, paramByte3));
  }
  
  private static final double maxOf(double paramDouble1, double paramDouble2)
  {
    return Math.max(paramDouble1, paramDouble2);
  }
  
  private static final double maxOf(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    return Math.max(paramDouble1, Math.max(paramDouble2, paramDouble3));
  }
  
  private static final float maxOf(float paramFloat1, float paramFloat2)
  {
    return Math.max(paramFloat1, paramFloat2);
  }
  
  private static final float maxOf(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    return Math.max(paramFloat1, Math.max(paramFloat2, paramFloat3));
  }
  
  private static final int maxOf(int paramInt1, int paramInt2)
  {
    return Math.max(paramInt1, paramInt2);
  }
  
  private static final int maxOf(int paramInt1, int paramInt2, int paramInt3)
  {
    return Math.max(paramInt1, Math.max(paramInt2, paramInt3));
  }
  
  private static final long maxOf(long paramLong1, long paramLong2)
  {
    return Math.max(paramLong1, paramLong2);
  }
  
  private static final long maxOf(long paramLong1, long paramLong2, long paramLong3)
  {
    return Math.max(paramLong1, Math.max(paramLong2, paramLong3));
  }
  
  public static final Comparable maxOf(Comparable paramComparable1, Comparable paramComparable2)
  {
    Intrinsics.checkParameterIsNotNull(paramComparable1, "a");
    Intrinsics.checkParameterIsNotNull(paramComparable2, "b");
    if (paramComparable1.compareTo(paramComparable2) >= 0) {
      return paramComparable1;
    }
    return paramComparable2;
  }
  
  public static final Comparable maxOf(Comparable paramComparable1, Comparable paramComparable2, Comparable paramComparable3)
  {
    Intrinsics.checkParameterIsNotNull(paramComparable1, "a");
    Intrinsics.checkParameterIsNotNull(paramComparable2, "b");
    Intrinsics.checkParameterIsNotNull(paramComparable3, "c");
    return maxOf(paramComparable1, maxOf(paramComparable2, paramComparable3));
  }
  
  private static final short maxOf(short paramShort1, short paramShort2)
  {
    return (short)Math.max(paramShort1, paramShort2);
  }
  
  private static final short maxOf(short paramShort1, short paramShort2, short paramShort3)
  {
    return (short)Math.max(paramShort1, Math.max(paramShort2, paramShort3));
  }
  
  private static final byte minOf(byte paramByte1, byte paramByte2)
  {
    return (byte)Math.min(paramByte1, paramByte2);
  }
  
  private static final byte minOf(byte paramByte1, byte paramByte2, byte paramByte3)
  {
    return (byte)Math.min(paramByte1, Math.min(paramByte2, paramByte3));
  }
  
  private static final double minOf(double paramDouble1, double paramDouble2)
  {
    return Math.min(paramDouble1, paramDouble2);
  }
  
  private static final double minOf(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    return Math.min(paramDouble1, Math.min(paramDouble2, paramDouble3));
  }
  
  private static final float minOf(float paramFloat1, float paramFloat2)
  {
    return Math.min(paramFloat1, paramFloat2);
  }
  
  private static final float minOf(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    return Math.min(paramFloat1, Math.min(paramFloat2, paramFloat3));
  }
  
  private static final int minOf(int paramInt1, int paramInt2)
  {
    return Math.min(paramInt1, paramInt2);
  }
  
  private static final int minOf(int paramInt1, int paramInt2, int paramInt3)
  {
    return Math.min(paramInt1, Math.min(paramInt2, paramInt3));
  }
  
  private static final long minOf(long paramLong1, long paramLong2)
  {
    return Math.min(paramLong1, paramLong2);
  }
  
  private static final long minOf(long paramLong1, long paramLong2, long paramLong3)
  {
    return Math.min(paramLong1, Math.min(paramLong2, paramLong3));
  }
  
  public static final Comparable minOf(Comparable paramComparable1, Comparable paramComparable2)
  {
    Intrinsics.checkParameterIsNotNull(paramComparable1, "a");
    Intrinsics.checkParameterIsNotNull(paramComparable2, "b");
    if (paramComparable1.compareTo(paramComparable2) <= 0) {
      return paramComparable1;
    }
    return paramComparable2;
  }
  
  public static final Comparable minOf(Comparable paramComparable1, Comparable paramComparable2, Comparable paramComparable3)
  {
    Intrinsics.checkParameterIsNotNull(paramComparable1, "a");
    Intrinsics.checkParameterIsNotNull(paramComparable2, "b");
    Intrinsics.checkParameterIsNotNull(paramComparable3, "c");
    return minOf(paramComparable1, minOf(paramComparable2, paramComparable3));
  }
  
  private static final short minOf(short paramShort1, short paramShort2)
  {
    return (short)Math.min(paramShort1, paramShort2);
  }
  
  private static final short minOf(short paramShort1, short paramShort2, short paramShort3)
  {
    return (short)Math.min(paramShort1, Math.min(paramShort2, paramShort3));
  }
}