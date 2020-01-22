package com.google.android.material.animation;

import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.R.id;

public class ChildrenAlphaProperty
  extends Property<ViewGroup, Float>
{
  public static final Property<ViewGroup, Float> CHILDREN_ALPHA = new ChildrenAlphaProperty("childrenAlpha");
  
  private ChildrenAlphaProperty(String paramString)
  {
    super(Float.class, paramString);
  }
  
  public void onDraw(ViewGroup paramViewGroup, Float paramFloat)
  {
    float f = paramFloat.floatValue();
    paramViewGroup.setTag(R.id.mtrl_internal_children_alpha_tag, Float.valueOf(f));
    int j = paramViewGroup.getChildCount();
    int i = 0;
    while (i < j)
    {
      paramViewGroup.getChildAt(i).setAlpha(f);
      i += 1;
    }
  }
  
  public Float set(ViewGroup paramViewGroup)
  {
    paramViewGroup = (Float)paramViewGroup.getTag(R.id.mtrl_internal_children_alpha_tag);
    if (paramViewGroup != null) {
      return paramViewGroup;
    }
    return Float.valueOf(1.0F);
  }
}