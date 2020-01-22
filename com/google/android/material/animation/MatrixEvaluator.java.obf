package com.google.android.material.animation;

import android.animation.TypeEvaluator;
import android.graphics.Matrix;

public class MatrixEvaluator
  implements TypeEvaluator<Matrix>
{
  private final float[] tempEndValues = new float[9];
  private final Matrix tempMatrix = new Matrix();
  private final float[] tempStartValues = new float[9];
  
  public MatrixEvaluator() {}
  
  public Matrix evaluate(float paramFloat, Matrix paramMatrix1, Matrix paramMatrix2)
  {
    paramMatrix1.getValues(tempStartValues);
    paramMatrix2.getValues(tempEndValues);
    int i = 0;
    while (i < 9)
    {
      paramMatrix1 = tempEndValues;
      float f1 = paramMatrix1[i];
      paramMatrix2 = tempStartValues;
      float f2 = paramMatrix2[i];
      paramMatrix2[i] += (f1 - f2) * paramFloat;
      i += 1;
    }
    tempMatrix.setValues(tempEndValues);
    return tempMatrix;
  }
}
