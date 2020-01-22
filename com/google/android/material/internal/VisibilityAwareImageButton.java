package com.google.android.material.internal;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class VisibilityAwareImageButton
  extends ImageButton
{
  private int userSetVisibility = getVisibility();
  
  public VisibilityAwareImageButton(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public VisibilityAwareImageButton(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public VisibilityAwareImageButton(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  public final int getUserSetVisibility()
  {
    return userSetVisibility;
  }
  
  public final void internalSetVisibility(int paramInt, boolean paramBoolean)
  {
    super.setVisibility(paramInt);
    if (paramBoolean) {
      userSetVisibility = paramInt;
    }
  }
  
  public void setVisibility(int paramInt)
  {
    internalSetVisibility(paramInt, true);
  }
}
