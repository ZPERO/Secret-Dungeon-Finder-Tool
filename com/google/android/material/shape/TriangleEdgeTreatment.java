package com.google.android.material.shape;

public class TriangleEdgeTreatment
  extends EdgeTreatment
{
  private final boolean inside;
  private final float size;
  
  public TriangleEdgeTreatment(float paramFloat, boolean paramBoolean)
  {
    size = paramFloat;
    inside = paramBoolean;
  }
  
  public void getEdgePath(float paramFloat1, float paramFloat2, ShapePath paramShapePath)
  {
    float f2 = paramFloat1 / 2.0F;
    paramShapePath.lineTo(f2 - size * paramFloat2, 0.0F);
    float f1;
    if (inside) {
      f1 = size;
    } else {
      f1 = -size;
    }
    paramShapePath.lineTo(f2, f1 * paramFloat2);
    paramShapePath.lineTo(f2 + size * paramFloat2, 0.0F);
    paramShapePath.lineTo(paramFloat1, 0.0F);
  }
}
