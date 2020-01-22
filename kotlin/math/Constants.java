package kotlin.math;

import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\000\024\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\006\n\002\b\006\b?\002\030\0002\0020\001B\007\b\002?\006\002\020\002R\020\020\003\032\0020\0048\000X?\004?\006\002\n\000R\020\020\005\032\0020\0048\000X?\004?\006\002\n\000R\020\020\006\032\0020\0048\000X?\004?\006\002\n\000R\020\020\007\032\0020\0048\000X?\004?\006\002\n\000R\020\020\b\032\0020\0048\000X?\004?\006\002\n\000R\020\020\t\032\0020\0048\000X?\004?\006\002\n\000?\006\n"}, d2={"Lkotlin/math/Constants;", "", "()V", "LN2", "", "epsilon", "taylor_2_bound", "taylor_n_bound", "upper_taylor_2_bound", "upper_taylor_n_bound", "kotlin-stdlib"}, k=1, mv={1, 1, 15})
final class Constants
{
  public static final Constants INSTANCE = new Constants();
  public static final double LN_2 = Math.log(2.0D);
  public static final double epsilon = Math.ulp(1.0D);
  public static final double taylor_2_bound = Math.sqrt(epsilon);
  public static final double taylor_n_bound = Math.sqrt(taylor_2_bound);
  public static final double upper_taylor_2_bound;
  public static final double upper_taylor_n_bound;
  
  static
  {
    double d = taylor_2_bound;
    Double.isNaN(1.0D);
    upper_taylor_2_bound = 1.0D / d;
    d = taylor_n_bound;
    Double.isNaN(1.0D);
    upper_taylor_n_bound = 1.0D / d;
  }
  
  private Constants() {}
}
