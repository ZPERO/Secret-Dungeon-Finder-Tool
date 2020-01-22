package com.google.android.material.tabs;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import androidx.appcompat.widget.TintTypedArray;
import com.google.android.material.R.styleable;

public class TabItem
  extends View
{
  public final int customLayout;
  public final Drawable icon;
  public final CharSequence text;
  
  public TabItem(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public TabItem(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    paramContext = TintTypedArray.obtainStyledAttributes(paramContext, paramAttributeSet, R.styleable.TabItem);
    text = paramContext.getText(R.styleable.TabItem_android_text);
    icon = paramContext.getDrawable(R.styleable.TabItem_android_icon);
    customLayout = paramContext.getResourceId(R.styleable.TabItem_android_layout, 0);
    paramContext.recycle();
  }
}
