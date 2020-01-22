package androidx.transition;

import android.view.View;

abstract interface ViewGroupOverlayImpl
  extends ViewOverlayImpl
{
  public abstract void addHeaderView(View paramView);
  
  public abstract void remove(View paramView);
}
