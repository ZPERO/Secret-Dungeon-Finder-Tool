package androidx.transition;

import android.util.Log;
import android.view.View;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class ViewUtilsApi22
  extends ViewUtilsApi21
{
  private static final String PAGE_KEY = "ViewUtilsApi22";
  private static Method sSetLeftTopRightBottomMethod;
  private static boolean sSetLeftTopRightBottomMethodFetched;
  
  ViewUtilsApi22() {}
  
  private void fetchSetLeftTopRightBottomMethod()
  {
    if (!sSetLeftTopRightBottomMethodFetched)
    {
      Object localObject = Integer.TYPE;
      Class localClass1 = Integer.TYPE;
      Class localClass2 = Integer.TYPE;
      Class localClass3 = Integer.TYPE;
      try
      {
        localObject = View.class.getDeclaredMethod("setLeftTopRightBottom", new Class[] { localObject, localClass1, localClass2, localClass3 });
        sSetLeftTopRightBottomMethod = (Method)localObject;
        localObject = sSetLeftTopRightBottomMethod;
        ((Method)localObject).setAccessible(true);
      }
      catch (NoSuchMethodException localNoSuchMethodException)
      {
        Log.i("ViewUtilsApi22", "Failed to retrieve setLeftTopRightBottom method", localNoSuchMethodException);
      }
      sSetLeftTopRightBottomMethodFetched = true;
    }
  }
  
  public void setLeftTopRightBottom(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    fetchSetLeftTopRightBottomMethod();
    Method localMethod = sSetLeftTopRightBottomMethod;
    if (localMethod != null) {
      try
      {
        localMethod.invoke(paramView, new Object[] { Integer.valueOf(paramInt1), Integer.valueOf(paramInt2), Integer.valueOf(paramInt3), Integer.valueOf(paramInt4) });
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
