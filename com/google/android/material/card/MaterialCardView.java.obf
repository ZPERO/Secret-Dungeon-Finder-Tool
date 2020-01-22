package com.google.android.material.card;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import androidx.cardview.widget.CardView;
import com.google.android.material.R.attr;
import com.google.android.material.R.style;
import com.google.android.material.R.styleable;
import com.google.android.material.internal.ThemeEnforcement;

public class MaterialCardView
  extends CardView
{
  private final MaterialCardViewHelper cardViewHelper;
  
  public MaterialCardView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public MaterialCardView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, R.attr.materialCardViewStyle);
  }
  
  public MaterialCardView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    paramContext = ThemeEnforcement.obtainStyledAttributes(paramContext, paramAttributeSet, R.styleable.MaterialCardView, paramInt, R.style.Widget_MaterialComponents_CardView, new int[0]);
    cardViewHelper = new MaterialCardViewHelper(this);
    cardViewHelper.loadFromAttributes(paramContext);
    paramContext.recycle();
  }
  
  public int getStrokeColor()
  {
    return cardViewHelper.getStrokeColor();
  }
  
  public int getStrokeWidth()
  {
    return cardViewHelper.getStrokeWidth();
  }
  
  public void setRadius(float paramFloat)
  {
    super.setRadius(paramFloat);
    cardViewHelper.updateForeground();
  }
  
  public void setStrokeColor(int paramInt)
  {
    cardViewHelper.setStrokeColor(paramInt);
  }
  
  public void setStrokeWidth(int paramInt)
  {
    cardViewHelper.setStrokeWidth(paramInt);
  }
}
