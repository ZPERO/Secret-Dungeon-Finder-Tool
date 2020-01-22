package com.google.android.material.internal;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.R.style;
import com.google.android.material.R.styleable;

public class ScrimInsetsFrameLayout
  extends FrameLayout
{
  Drawable insetForeground;
  Rect insets;
  private Rect tempRect = new Rect();
  
  public ScrimInsetsFrameLayout(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public ScrimInsetsFrameLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public ScrimInsetsFrameLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    paramContext = ThemeEnforcement.obtainStyledAttributes(paramContext, paramAttributeSet, R.styleable.ScrimInsetsFrameLayout, paramInt, R.style.Widget_Design_ScrimInsetsFrameLayout, new int[0]);
    insetForeground = paramContext.getDrawable(R.styleable.ScrimInsetsFrameLayout_insetForeground);
    paramContext.recycle();
    setWillNotDraw(true);
    ViewCompat.setOnApplyWindowInsetsListener(this, new OnApplyWindowInsetsListener()
    {
      public WindowInsetsCompat onApplyWindowInsets(View paramAnonymousView, WindowInsetsCompat paramAnonymousWindowInsetsCompat)
      {
        if (insets == null) {
          insets = new Rect();
        }
        insets.set(paramAnonymousWindowInsetsCompat.getSystemWindowInsetLeft(), paramAnonymousWindowInsetsCompat.getSystemWindowInsetTop(), paramAnonymousWindowInsetsCompat.getSystemWindowInsetRight(), paramAnonymousWindowInsetsCompat.getSystemWindowInsetBottom());
        onInsetsChanged(paramAnonymousWindowInsetsCompat);
        paramAnonymousView = ScrimInsetsFrameLayout.this;
        boolean bool;
        if ((paramAnonymousWindowInsetsCompat.hasSystemWindowInsets()) && (insetForeground != null)) {
          bool = false;
        } else {
          bool = true;
        }
        paramAnonymousView.setWillNotDraw(bool);
        ViewCompat.postInvalidateOnAnimation(ScrimInsetsFrameLayout.this);
        return paramAnonymousWindowInsetsCompat.consumeSystemWindowInsets();
      }
    });
  }
  
  public void draw(Canvas paramCanvas)
  {
    super.draw(paramCanvas);
    int i = getWidth();
    int j = getHeight();
    if ((insets != null) && (insetForeground != null))
    {
      int k = paramCanvas.save();
      paramCanvas.translate(getScrollX(), getScrollY());
      tempRect.set(0, 0, i, insets.top);
      insetForeground.setBounds(tempRect);
      insetForeground.draw(paramCanvas);
      tempRect.set(0, j - insets.bottom, i, j);
      insetForeground.setBounds(tempRect);
      insetForeground.draw(paramCanvas);
      tempRect.set(0, insets.top, insets.left, j - insets.bottom);
      insetForeground.setBounds(tempRect);
      insetForeground.draw(paramCanvas);
      tempRect.set(i - insets.right, insets.top, i, j - insets.bottom);
      insetForeground.setBounds(tempRect);
      insetForeground.draw(paramCanvas);
      paramCanvas.restoreToCount(k);
    }
  }
  
  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    Drawable localDrawable = insetForeground;
    if (localDrawable != null) {
      localDrawable.setCallback(this);
    }
  }
  
  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    Drawable localDrawable = insetForeground;
    if (localDrawable != null) {
      localDrawable.setCallback(null);
    }
  }
  
  protected void onInsetsChanged(WindowInsetsCompat paramWindowInsetsCompat) {}
}
