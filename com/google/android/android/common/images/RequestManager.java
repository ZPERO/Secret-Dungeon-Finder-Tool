package com.google.android.android.common.images;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import com.google.android.android.common.internal.Asserts;
import com.google.android.android.internal.base.DisplayImageOptions;

public abstract class RequestManager
{
  final BitmapLoader zamu;
  private int zamv = 0;
  protected int zamw = 0;
  private boolean zamx = false;
  private boolean zamy = true;
  private boolean zamz = false;
  private boolean zana = true;
  
  public RequestManager(Uri paramUri, int paramInt)
  {
    zamu = new BitmapLoader(paramUri);
    zamw = paramInt;
  }
  
  final void load(Context paramContext, Bitmap paramBitmap, boolean paramBoolean)
  {
    Asserts.checkNotNull(paramBitmap);
    loadImage(new BitmapDrawable(paramContext.getResources(), paramBitmap), paramBoolean, false, true);
  }
  
  final void loadImage(Context paramContext, DisplayImageOptions paramDisplayImageOptions)
  {
    if (zana) {
      loadImage(null, false, true, false);
    }
  }
  
  final void loadImage(Context paramContext, DisplayImageOptions paramDisplayImageOptions, boolean paramBoolean)
  {
    int i = zamw;
    if (i != 0) {
      paramContext = paramContext.getResources().getDrawable(i);
    } else {
      paramContext = null;
    }
    loadImage(paramContext, paramBoolean, false, false);
  }
  
  protected abstract void loadImage(Drawable paramDrawable, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3);
  
  protected final boolean loadImage(boolean paramBoolean1, boolean paramBoolean2)
  {
    return (zamy) && (!paramBoolean2) && (!paramBoolean1);
  }
}
