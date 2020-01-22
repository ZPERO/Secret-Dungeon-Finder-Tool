package androidx.transition;

import android.util.Log;
import android.view.ViewGroup;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class ViewGroupUtilsApi18
{
  private static final String PAGE_KEY = "ViewUtilsApi18";
  private static Method sSuppressLayoutMethod;
  private static boolean sSuppressLayoutMethodFetched;
  
  private ViewGroupUtilsApi18() {}
  
  private static void fetchSuppressLayoutMethod()
  {
    if (!sSuppressLayoutMethodFetched)
    {
      Object localObject = Boolean.TYPE;
      try
      {
        localObject = ViewGroup.class.getDeclaredMethod("suppressLayout", new Class[] { localObject });
        sSuppressLayoutMethod = (Method)localObject;
        localObject = sSuppressLayoutMethod;
        ((Method)localObject).setAccessible(true);
      }
      catch (NoSuchMethodException localNoSuchMethodException)
      {
        Log.i("ViewUtilsApi18", "Failed to retrieve suppressLayout method", localNoSuchMethodException);
      }
      sSuppressLayoutMethodFetched = true;
    }
  }
  
  static void suppressLayout(ViewGroup paramViewGroup, boolean paramBoolean)
  {
    fetchSuppressLayoutMethod();
    Method localMethod = sSuppressLayoutMethod;
    if (localMethod != null) {
      try
      {
        localMethod.invoke(paramViewGroup, new Object[] { Boolean.valueOf(paramBoolean) });
        return;
      }
      catch (InvocationTargetException paramViewGroup)
      {
        Log.i("ViewUtilsApi18", "Error invoking suppressLayout method", paramViewGroup);
        return;
      }
      catch (IllegalAccessException paramViewGroup)
      {
        Log.i("ViewUtilsApi18", "Failed to invoke suppressLayout method", paramViewGroup);
      }
    }
  }
}
