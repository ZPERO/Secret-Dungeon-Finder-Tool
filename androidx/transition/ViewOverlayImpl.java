package androidx.transition;

import android.graphics.drawable.Drawable;

abstract interface ViewOverlayImpl
{
  public abstract void clear();
  
  public abstract void log1p(Drawable paramDrawable);
  
  public abstract void remove(Drawable paramDrawable);
}
