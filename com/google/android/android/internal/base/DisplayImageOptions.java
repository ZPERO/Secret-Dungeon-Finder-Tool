package com.google.android.android.internal.base;

import android.graphics.drawable.Drawable;
import androidx.collection.LruCache;

public final class DisplayImageOptions
  extends LruCache<Object, Drawable>
{
  public DisplayImageOptions()
  {
    super(10);
  }
}
