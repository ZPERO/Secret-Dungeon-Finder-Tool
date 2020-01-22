package com.google.android.android.common.images;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import com.google.android.android.common.internal.Asserts;
import com.google.android.android.common.internal.Objects;
import java.lang.ref.WeakReference;

public final class Game
  extends RequestManager
{
  private WeakReference<com.google.android.gms.common.images.ImageManager.OnImageLoadedListener> zanc;
  
  public Game(ImageManager.OnImageLoadedListener paramOnImageLoadedListener, Uri paramUri)
  {
    super(paramUri, 0);
    Asserts.checkNotNull(paramOnImageLoadedListener);
    zanc = new WeakReference(paramOnImageLoadedListener);
  }
  
  public final boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof Game)) {
      return false;
    }
    if (this == paramObject) {
      return true;
    }
    paramObject = (Game)paramObject;
    ImageManager.OnImageLoadedListener localOnImageLoadedListener1 = (ImageManager.OnImageLoadedListener)zanc.get();
    ImageManager.OnImageLoadedListener localOnImageLoadedListener2 = (ImageManager.OnImageLoadedListener)zanc.get();
    return (localOnImageLoadedListener2 != null) && (localOnImageLoadedListener1 != null) && (Objects.equal(localOnImageLoadedListener2, localOnImageLoadedListener1)) && (Objects.equal(zamu, zamu));
  }
  
  public final int hashCode()
  {
    return Objects.hashCode(new Object[] { zamu });
  }
  
  protected final void loadImage(Drawable paramDrawable, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    if (!paramBoolean2)
    {
      ImageManager.OnImageLoadedListener localOnImageLoadedListener = (ImageManager.OnImageLoadedListener)zanc.get();
      if (localOnImageLoadedListener != null) {
        localOnImageLoadedListener.onImageLoaded(zamu.uri, paramDrawable, paramBoolean3);
      }
    }
  }
}
