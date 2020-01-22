package androidx.transition;

import android.animation.TypeEvaluator;
import android.graphics.Rect;

class RectEvaluator
  implements TypeEvaluator<Rect>
{
  private Rect mRect;
  
  RectEvaluator() {}
  
  RectEvaluator(Rect paramRect)
  {
    mRect = paramRect;
  }
  
  public Rect evaluate(float paramFloat, Rect paramRect1, Rect paramRect2)
  {
    int i = left + (int)((left - left) * paramFloat);
    int j = top + (int)((top - top) * paramFloat);
    int k = right + (int)((right - right) * paramFloat);
    int m = bottom + (int)((bottom - bottom) * paramFloat);
    paramRect1 = mRect;
    if (paramRect1 == null) {
      return new Rect(i, j, k, m);
    }
    paramRect1.set(i, j, k, m);
    return mRect;
  }
}
