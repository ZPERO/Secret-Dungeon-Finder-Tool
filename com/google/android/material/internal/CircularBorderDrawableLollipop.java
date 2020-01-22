package com.google.android.material.internal;

import android.graphics.Outline;
import android.graphics.drawable.Drawable;

public class CircularBorderDrawableLollipop
  extends CircularBorderDrawable
{
  public CircularBorderDrawableLollipop() {}
  
  public void getOutline(Outline paramOutline)
  {
    copyBounds(rect);
    paramOutline.setOval(rect);
  }
}
