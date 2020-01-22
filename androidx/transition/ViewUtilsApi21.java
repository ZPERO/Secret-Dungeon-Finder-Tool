package androidx.transition;

import android.graphics.Matrix;
import android.util.Log;
import android.view.View;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class ViewUtilsApi21
  extends ViewUtilsApi19
{
  private static final String PAGE_KEY = "ViewUtilsApi21";
  private static Method sSetAnimationMatrixMethod;
  private static boolean sSetAnimationMatrixMethodFetched;
  private static Method sTransformMatrixToGlobalMethod;
  private static boolean sTransformMatrixToGlobalMethodFetched;
  private static Method sTransformMatrixToLocalMethod;
  private static boolean sTransformMatrixToLocalMethodFetched;
  
  ViewUtilsApi21() {}
  
  private void fetchSetAnimationMatrix()
  {
    if (!sSetAnimationMatrixMethodFetched)
    {
      try
      {
        Method localMethod = View.class.getDeclaredMethod("setAnimationMatrix", new Class[] { Matrix.class });
        sSetAnimationMatrixMethod = localMethod;
        localMethod = sSetAnimationMatrixMethod;
        localMethod.setAccessible(true);
      }
      catch (NoSuchMethodException localNoSuchMethodException)
      {
        Log.i("ViewUtilsApi21", "Failed to retrieve setAnimationMatrix method", localNoSuchMethodException);
      }
      sSetAnimationMatrixMethodFetched = true;
    }
  }
  
  private void fetchTransformMatrixToGlobalMethod()
  {
    if (!sTransformMatrixToGlobalMethodFetched)
    {
      try
      {
        Method localMethod = View.class.getDeclaredMethod("transformMatrixToGlobal", new Class[] { Matrix.class });
        sTransformMatrixToGlobalMethod = localMethod;
        localMethod = sTransformMatrixToGlobalMethod;
        localMethod.setAccessible(true);
      }
      catch (NoSuchMethodException localNoSuchMethodException)
      {
        Log.i("ViewUtilsApi21", "Failed to retrieve transformMatrixToGlobal method", localNoSuchMethodException);
      }
      sTransformMatrixToGlobalMethodFetched = true;
    }
  }
  
  private void fetchTransformMatrixToLocalMethod()
  {
    if (!sTransformMatrixToLocalMethodFetched)
    {
      try
      {
        Method localMethod = View.class.getDeclaredMethod("transformMatrixToLocal", new Class[] { Matrix.class });
        sTransformMatrixToLocalMethod = localMethod;
        localMethod = sTransformMatrixToLocalMethod;
        localMethod.setAccessible(true);
      }
      catch (NoSuchMethodException localNoSuchMethodException)
      {
        Log.i("ViewUtilsApi21", "Failed to retrieve transformMatrixToLocal method", localNoSuchMethodException);
      }
      sTransformMatrixToLocalMethodFetched = true;
    }
  }
  
  public void setAnimationMatrix(View paramView, Matrix paramMatrix)
  {
    fetchSetAnimationMatrix();
    Method localMethod = sSetAnimationMatrixMethod;
    if (localMethod != null) {
      try
      {
        localMethod.invoke(paramView, new Object[] { paramMatrix });
        return;
      }
      catch (IllegalAccessException paramView)
      {
        throw new RuntimeException(paramView.getCause());
      }
      catch (InvocationTargetException paramView) {}
    }
  }
  
  public void transformMatrixToGlobal(View paramView, Matrix paramMatrix)
  {
    fetchTransformMatrixToGlobalMethod();
    Method localMethod = sTransformMatrixToGlobalMethod;
    if (localMethod != null) {
      try
      {
        localMethod.invoke(paramView, new Object[] { paramMatrix });
        return;
      }
      catch (InvocationTargetException paramView)
      {
        throw new RuntimeException(paramView.getCause());
      }
      catch (IllegalAccessException paramView) {}
    }
  }
  
  public void transformMatrixToLocal(View paramView, Matrix paramMatrix)
  {
    fetchTransformMatrixToLocalMethod();
    Method localMethod = sTransformMatrixToLocalMethod;
    if (localMethod != null) {
      try
      {
        localMethod.invoke(paramView, new Object[] { paramMatrix });
        return;
      }
      catch (InvocationTargetException paramView)
      {
        throw new RuntimeException(paramView.getCause());
      }
      catch (IllegalAccessException paramView) {}
    }
  }
}
