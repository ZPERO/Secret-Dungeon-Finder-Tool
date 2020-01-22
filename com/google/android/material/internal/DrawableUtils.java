package com.google.android.material.internal;

import android.graphics.drawable.Drawable.ConstantState;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.DrawableContainer.DrawableContainerState;
import android.util.Log;
import java.lang.reflect.Method;

public class DrawableUtils
{
  private static final String LOG_TAG = "DrawableUtils";
  private static Method setConstantStateMethod;
  private static boolean setConstantStateMethodFetched;
  
  private DrawableUtils() {}
  
  public static boolean setContainerConstantState(DrawableContainer paramDrawableContainer, Drawable.ConstantState paramConstantState)
  {
    return setContainerConstantStateV9(paramDrawableContainer, paramConstantState);
  }
  
  private static boolean setContainerConstantStateV9(DrawableContainer paramDrawableContainer, Drawable.ConstantState paramConstantState)
  {
    if (!setConstantStateMethodFetched) {}
    try
    {
      localMethod = DrawableContainer.class.getDeclaredMethod("setConstantState", new Class[] { DrawableContainer.DrawableContainerState.class });
      setConstantStateMethod = localMethod;
      localMethod = setConstantStateMethod;
      localMethod.setAccessible(true);
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      Method localMethod;
      for (;;) {}
    }
    Log.e("DrawableUtils", "Could not fetch setConstantState(). Oh well.");
    setConstantStateMethodFetched = true;
    localMethod = setConstantStateMethod;
    if (localMethod != null)
    {
      try
      {
        localMethod.invoke(paramDrawableContainer, new Object[] { paramConstantState });
        return true;
      }
      catch (Exception paramDrawableContainer)
      {
        for (;;) {}
      }
      Log.e("DrawableUtils", "Could not invoke setConstantState(). Oh well.");
      return false;
    }
    return false;
  }
}
