package com.google.android.material.shape;

public class RoundedCornerTreatment
  extends CornerTreatment
{
  private final float radius;
  
  public RoundedCornerTreatment(float paramFloat)
  {
    radius = paramFloat;
  }
  
  public void getCornerPath(float paramFloat1, float paramFloat2, ShapePath paramShapePath)
  {
    paramShapePath.reset(0.0F, radius * paramFloat2);
    float f = radius;
    paramShapePath.addArc(0.0F, 0.0F, f * 2.0F * paramFloat2, f * 2.0F * paramFloat2, paramFloat1 + 180.0F, 90.0F);
  }
}
