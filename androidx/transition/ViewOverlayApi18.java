package androidx.transition;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewOverlay;

class ViewOverlayApi18
  implements ViewOverlayImpl
{
  private final ViewOverlay mViewOverlay;
  
  ViewOverlayApi18(View paramView)
  {
    mViewOverlay = paramView.getOverlay();
  }
  
  public void clear()
  {
    mViewOverlay.clear();
  }
  
  public void log1p(Drawable paramDrawable)
  {
    mViewOverlay.add(paramDrawable);
  }
  
  public void remove(Drawable paramDrawable)
  {
    mViewOverlay.remove(paramDrawable);
  }
}
