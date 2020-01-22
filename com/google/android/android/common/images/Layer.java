package com.google.android.android.common.images;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;
import com.google.android.android.common.internal.Asserts;
import com.google.android.android.common.internal.Objects;
import com.google.android.android.internal.base.DrawableContainer;
import com.google.android.android.internal.base.ImageViewCustom;
import java.lang.ref.WeakReference;

public final class Layer
  extends RequestManager
{
  private WeakReference<ImageView> zanb;
  
  public Layer(ImageView paramImageView, int paramInt)
  {
    super(null, paramInt);
    Asserts.checkNotNull(paramImageView);
    zanb = new WeakReference(paramImageView);
  }
  
  public Layer(ImageView paramImageView, Uri paramUri)
  {
    super(paramUri, 0);
    Asserts.checkNotNull(paramImageView);
    zanb = new WeakReference(paramImageView);
  }
  
  public final boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof Layer)) {
      return false;
    }
    if (this == paramObject) {
      return true;
    }
    Object localObject = (Layer)paramObject;
    paramObject = (ImageView)zanb.get();
    localObject = (ImageView)zanb.get();
    return (localObject != null) && (paramObject != null) && (Objects.equal(localObject, paramObject));
  }
  
  public final int hashCode()
  {
    return 0;
  }
  
  protected final void loadImage(Drawable paramDrawable, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    ImageView localImageView = (ImageView)zanb.get();
    if (localImageView != null)
    {
      int j = 0;
      int i;
      if ((!paramBoolean2) && (!paramBoolean3)) {
        i = 1;
      } else {
        i = 0;
      }
      if ((i != 0) && ((localImageView instanceof ImageViewCustom)))
      {
        int k = ImageViewCustom.zach();
        if ((zamw != 0) && (k == zamw)) {}
      }
      else
      {
        paramBoolean1 = loadImage(paramBoolean1, paramBoolean2);
        Object localObject2 = null;
        Object localObject1 = paramDrawable;
        if (paramBoolean1)
        {
          Drawable localDrawable = localImageView.getDrawable();
          localObject1 = localDrawable;
          if (localDrawable != null)
          {
            if ((localDrawable instanceof DrawableContainer)) {
              localObject1 = ((DrawableContainer)localDrawable).zacf();
            }
          }
          else {
            localObject1 = null;
          }
          localObject1 = new DrawableContainer((Drawable)localObject1, paramDrawable);
        }
        localImageView.setImageDrawable((Drawable)localObject1);
        if ((localImageView instanceof ImageViewCustom))
        {
          paramDrawable = localObject2;
          if (paramBoolean3) {
            paramDrawable = zamu.uri;
          }
          ImageViewCustom.extract(paramDrawable);
          if (i != 0) {
            j = zamw;
          }
          ImageViewCustom.setImageBitmap(j);
        }
        if (paramBoolean1) {
          ((DrawableContainer)localObject1).startTransition(250);
        }
      }
    }
  }
}
