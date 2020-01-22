package androidx.transition;

import android.util.Log;
import android.view.View;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class ViewUtilsApi19
  extends ViewUtilsBase
{
  private static final String PAGE_KEY = "ViewUtilsApi19";
  private static Method sGetTransitionAlphaMethod;
  private static boolean sGetTransitionAlphaMethodFetched;
  private static Method sSetTransitionAlphaMethod;
  private static boolean sSetTransitionAlphaMethodFetched;
  
  ViewUtilsApi19() {}
  
  private void fetchGetTransitionAlphaMethod()
  {
    if (!sGetTransitionAlphaMethodFetched)
    {
      try
      {
        Method localMethod = View.class.getDeclaredMethod("getTransitionAlpha", new Class[0]);
        sGetTransitionAlphaMethod = localMethod;
        localMethod = sGetTransitionAlphaMethod;
        localMethod.setAccessible(true);
      }
      catch (NoSuchMethodException localNoSuchMethodException)
      {
        Log.i("ViewUtilsApi19", "Failed to retrieve getTransitionAlpha method", localNoSuchMethodException);
      }
      sGetTransitionAlphaMethodFetched = true;
    }
  }
  
  private void fetchSetTransitionAlphaMethod()
  {
    if (!sSetTransitionAlphaMethodFetched)
    {
      Object localObject = Float.TYPE;
      try
      {
        localObject = View.class.getDeclaredMethod("setTransitionAlpha", new Class[] { localObject });
        sSetTransitionAlphaMethod = (Method)localObject;
        localObject = sSetTransitionAlphaMethod;
        ((Method)localObject).setAccessible(true);
      }
      catch (NoSuchMethodException localNoSuchMethodException)
      {
        Log.i("ViewUtilsApi19", "Failed to retrieve setTransitionAlpha method", localNoSuchMethodException);
      }
      sSetTransitionAlphaMethodFetched = true;
    }
  }
  
  public void clearNonTransitionAlpha(View paramView) {}
  
  public float getTransitionAlpha(View paramView)
  {
    fetchGetTransitionAlphaMethod();
    Object localObject = sGetTransitionAlphaMethod;
    if (localObject != null) {}
    try
    {
      localObject = ((Method)localObject).invoke(paramView, new Object[0]);
      localObject = (Float)localObject;
      float f = ((Float)localObject).floatValue();
      return f;
    }
    catch (InvocationTargetException paramView)
    {
      throw new RuntimeException(paramView.getCause());
      return super.getTransitionAlpha(paramView);
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      for (;;) {}
    }
  }
  
  public void saveNonTransitionAlpha(View paramView) {}
  
  public void setTransitionAlpha(View paramView, float paramFloat)
  {
    fetchSetTransitionAlphaMethod();
    Method localMethod = sSetTransitionAlphaMethod;
    if (localMethod != null) {}
    try
    {
      localMethod.invoke(paramView, new Object[] { Float.valueOf(paramFloat) });
      return;
    }
    catch (InvocationTargetException paramView)
    {
      throw new RuntimeException(paramView.getCause());
      paramView.setAlpha(paramFloat);
      return;
    }
    catch (IllegalAccessException paramView) {}
  }
}
