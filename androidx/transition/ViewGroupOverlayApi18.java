package androidx.transition;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;

class ViewGroupOverlayApi18
  implements ViewGroupOverlayImpl
{
  private final ViewGroupOverlay mViewGroupOverlay;
  
  ViewGroupOverlayApi18(ViewGroup paramViewGroup)
  {
    mViewGroupOverlay = paramViewGroup.getOverlay();
  }
  
  public void addHeaderView(View paramView)
  {
    mViewGroupOverlay.add(paramView);
  }
  
  public void clear()
  {
    mViewGroupOverlay.clear();
  }
  
  public void log1p(Drawable paramDrawable)
  {
    mViewGroupOverlay.add(paramDrawable);
  }
  
  public void remove(Drawable paramDrawable)
  {
    mViewGroupOverlay.remove(paramDrawable);
  }
  
  public void remove(View paramView)
  {
    mViewGroupOverlay.remove(paramView);
  }
}
