package androidx.transition;

import android.os.Build.VERSION;
import android.view.ViewGroup;

class ViewGroupUtils
{
  private ViewGroupUtils() {}
  
  static ViewGroupOverlayImpl getOverlay(ViewGroup paramViewGroup)
  {
    if (Build.VERSION.SDK_INT >= 18) {
      return new ViewGroupOverlayApi18(paramViewGroup);
    }
    return ViewGroupOverlayApi14.createFrom(paramViewGroup);
  }
  
  static void suppressLayout(ViewGroup paramViewGroup, boolean paramBoolean)
  {
    if (Build.VERSION.SDK_INT >= 18)
    {
      ViewGroupUtilsApi18.suppressLayout(paramViewGroup, paramBoolean);
      return;
    }
    ViewGroupUtilsApi14.suppressLayout(paramViewGroup, paramBoolean);
  }
}
