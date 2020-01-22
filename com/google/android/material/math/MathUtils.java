package com.google.android.material.math;

public final class MathUtils
{
  public static final float DEFAULT_EPSILON = 1.0E-4F;
  
  private MathUtils() {}
  
  private static float clamp(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    if ((paramFloat1 > paramFloat2) && (paramFloat1 > paramFloat3) && (paramFloat1 > paramFloat4)) {
      return paramFloat1;
    }
    if ((paramFloat2 > paramFloat3) && (paramFloat2 > paramFloat4)) {
      return paramFloat2;
    }
    if (paramFloat3 > paramFloat4) {
      return paramFloat3;
    }
    return paramFloat4;
  }
  
  public static float dist(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    return (float)Math.hypot(paramFloat3 - paramFloat1, paramFloat4 - paramFloat2);
  }
  
  public static float distanceToFurthestCorner(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    return clamp(dist(paramFloat1, paramFloat2, paramFloat3, paramFloat4), dist(paramFloat1, paramFloat2, paramFloat5, paramFloat4), dist(paramFloat1, paramFloat2, paramFloat5, paramFloat6), dist(paramFloat1, paramFloat2, paramFloat3, paramFloat6));
  }
  
  public static boolean isBetween(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    return paramFloat1 + paramFloat3 >= paramFloat2;
  }
  
  public static float lerp(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    return (1.0F - paramFloat3) * paramFloat1 + paramFloat3 * paramFloat2;
  }
}
