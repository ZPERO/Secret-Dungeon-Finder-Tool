package com.google.android.material.shape;

import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.RectF;
import java.util.ArrayList;
import java.util.List;

public class ShapePath
{
  public float endX;
  public float endY;
  private final List<PathOperation> operations = new ArrayList();
  public float startX;
  public float startY;
  
  public ShapePath()
  {
    reset(0.0F, 0.0F);
  }
  
  public ShapePath(float paramFloat1, float paramFloat2)
  {
    reset(paramFloat1, paramFloat2);
  }
  
  public void addArc(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    PathArcOperation localPathArcOperation = new PathArcOperation(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
    startAngle = paramFloat5;
    sweepAngle = paramFloat6;
    operations.add(localPathArcOperation);
    float f = (paramFloat3 - paramFloat1) / 2.0F;
    double d = paramFloat5 + paramFloat6;
    endX = ((paramFloat1 + paramFloat3) * 0.5F + f * (float)Math.cos(Math.toRadians(d)));
    endY = ((paramFloat2 + paramFloat4) * 0.5F + (paramFloat4 - paramFloat2) / 2.0F * (float)Math.sin(Math.toRadians(d)));
  }
  
  public void applyToPath(Matrix paramMatrix, Path paramPath)
  {
    int j = operations.size();
    int i = 0;
    while (i < j)
    {
      ((PathOperation)operations.get(i)).applyToPath(paramMatrix, paramPath);
      i += 1;
    }
  }
  
  public void lineTo(float paramFloat1, float paramFloat2)
  {
    PathLineOperation localPathLineOperation = new PathLineOperation();
    PathLineOperation.access$002(localPathLineOperation, paramFloat1);
    PathLineOperation.access$102(localPathLineOperation, paramFloat2);
    operations.add(localPathLineOperation);
    endX = paramFloat1;
    endY = paramFloat2;
  }
  
  public void quadToPoint(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    PathQuadOperation localPathQuadOperation = new PathQuadOperation();
    controlX = paramFloat1;
    controlY = paramFloat2;
    endX = paramFloat3;
    endY = paramFloat4;
    operations.add(localPathQuadOperation);
    endX = paramFloat3;
    endY = paramFloat4;
  }
  
  public void reset(float paramFloat1, float paramFloat2)
  {
    startX = paramFloat1;
    startY = paramFloat2;
    endX = paramFloat1;
    endY = paramFloat2;
    operations.clear();
  }
  
  public static class PathArcOperation
    extends ShapePath.PathOperation
  {
    private static final RectF rectF = new RectF();
    public float bottom;
    public float down;
    public float left;
    public float right;
    public float startAngle;
    public float sweepAngle;
    
    public PathArcOperation(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
    {
      left = paramFloat1;
      down = paramFloat2;
      right = paramFloat3;
      bottom = paramFloat4;
    }
    
    public void applyToPath(Matrix paramMatrix, Path paramPath)
    {
      Matrix localMatrix = matrix;
      paramMatrix.invert(localMatrix);
      paramPath.transform(localMatrix);
      rectF.set(left, down, right, bottom);
      paramPath.arcTo(rectF, startAngle, sweepAngle, false);
      paramPath.transform(paramMatrix);
    }
  }
  
  public static class PathLineOperation
    extends ShapePath.PathOperation
  {
    private float posX;
    private float posY;
    
    public PathLineOperation() {}
    
    public void applyToPath(Matrix paramMatrix, Path paramPath)
    {
      Matrix localMatrix = matrix;
      paramMatrix.invert(localMatrix);
      paramPath.transform(localMatrix);
      paramPath.lineTo(posX, posY);
      paramPath.transform(paramMatrix);
    }
  }
  
  public static abstract class PathOperation
  {
    protected final Matrix matrix = new Matrix();
    
    public PathOperation() {}
    
    public abstract void applyToPath(Matrix paramMatrix, Path paramPath);
  }
  
  public static class PathQuadOperation
    extends ShapePath.PathOperation
  {
    public float controlX;
    public float controlY;
    public float endX;
    public float endY;
    
    public PathQuadOperation() {}
    
    public void applyToPath(Matrix paramMatrix, Path paramPath)
    {
      Matrix localMatrix = matrix;
      paramMatrix.invert(localMatrix);
      paramPath.transform(localMatrix);
      paramPath.quadTo(controlX, controlY, endX, endY);
      paramPath.transform(paramMatrix);
    }
  }
}
