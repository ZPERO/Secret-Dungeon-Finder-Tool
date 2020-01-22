package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources.NotFoundException;
import android.content.res.TypedArray;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;
import androidx.appcompat.R.styleable;
import androidx.appcompat.content.wiki.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.widget.CompoundButtonCompat;

class AppCompatCompoundButtonHelper
{
  private ColorStateList mButtonTintList = null;
  private PorterDuff.Mode mButtonTintMode = null;
  private boolean mHasButtonTint = false;
  private boolean mHasButtonTintMode = false;
  private boolean mSkipNextApply;
  private final CompoundButton mView;
  
  AppCompatCompoundButtonHelper(CompoundButton paramCompoundButton)
  {
    mView = paramCompoundButton;
  }
  
  void applyButtonTint()
  {
    Drawable localDrawable = CompoundButtonCompat.getButtonDrawable(mView);
    if ((localDrawable != null) && ((mHasButtonTint) || (mHasButtonTintMode)))
    {
      localDrawable = DrawableCompat.wrap(localDrawable).mutate();
      if (mHasButtonTint) {
        DrawableCompat.setTintList(localDrawable, mButtonTintList);
      }
      if (mHasButtonTintMode) {
        DrawableCompat.setTintMode(localDrawable, mButtonTintMode);
      }
      if (localDrawable.isStateful()) {
        localDrawable.setState(mView.getDrawableState());
      }
      mView.setButtonDrawable(localDrawable);
    }
  }
  
  int getCompoundPaddingLeft(int paramInt)
  {
    int i = paramInt;
    if (Build.VERSION.SDK_INT < 17)
    {
      Drawable localDrawable = CompoundButtonCompat.getButtonDrawable(mView);
      i = paramInt;
      if (localDrawable != null) {
        i = paramInt + localDrawable.getIntrinsicWidth();
      }
    }
    return i;
  }
  
  ColorStateList getSupportButtonTintList()
  {
    return mButtonTintList;
  }
  
  PorterDuff.Mode getSupportButtonTintMode()
  {
    return mButtonTintMode;
  }
  
  void loadFromAttributes(AttributeSet paramAttributeSet, int paramInt)
  {
    paramAttributeSet = mView.getContext().obtainStyledAttributes(paramAttributeSet, R.styleable.CompoundButton, paramInt, 0);
    for (;;)
    {
      try
      {
        bool = paramAttributeSet.hasValue(R.styleable.CompoundButton_buttonCompat);
        if (bool)
        {
          paramInt = paramAttributeSet.getResourceId(R.styleable.CompoundButton_buttonCompat, 0);
          if (paramInt != 0)
          {
            localCompoundButton1 = mView;
            localCompoundButton2 = mView;
          }
        }
      }
      catch (Throwable localThrowable)
      {
        boolean bool;
        CompoundButton localCompoundButton1;
        CompoundButton localCompoundButton2;
        paramAttributeSet.recycle();
        throw localThrowable;
      }
      try
      {
        localCompoundButton1.setButtonDrawable(AppCompatResources.getDrawable(localCompoundButton2.getContext(), paramInt));
        paramInt = 1;
      }
      catch (Resources.NotFoundException localNotFoundException) {}
    }
    paramInt = 0;
    if (paramInt == 0)
    {
      bool = paramAttributeSet.hasValue(R.styleable.CompoundButton_android_button);
      if (bool)
      {
        paramInt = paramAttributeSet.getResourceId(R.styleable.CompoundButton_android_button, 0);
        if (paramInt != 0) {
          mView.setButtonDrawable(AppCompatResources.getDrawable(mView.getContext(), paramInt));
        }
      }
    }
    bool = paramAttributeSet.hasValue(R.styleable.CompoundButton_buttonTint);
    if (bool) {
      CompoundButtonCompat.setButtonTintList(mView, paramAttributeSet.getColorStateList(R.styleable.CompoundButton_buttonTint));
    }
    bool = paramAttributeSet.hasValue(R.styleable.CompoundButton_buttonTintMode);
    if (bool) {
      CompoundButtonCompat.setButtonTintMode(mView, DrawableUtils.parseTintMode(paramAttributeSet.getInt(R.styleable.CompoundButton_buttonTintMode, -1), null));
    }
    paramAttributeSet.recycle();
  }
  
  void onSetButtonDrawable()
  {
    if (mSkipNextApply)
    {
      mSkipNextApply = false;
      return;
    }
    mSkipNextApply = true;
    applyButtonTint();
  }
  
  void setSupportButtonTintList(ColorStateList paramColorStateList)
  {
    mButtonTintList = paramColorStateList;
    mHasButtonTint = true;
    applyButtonTint();
  }
  
  void setSupportButtonTintMode(PorterDuff.Mode paramMode)
  {
    mButtonTintMode = paramMode;
    mHasButtonTintMode = true;
    applyButtonTint();
  }
  
  static abstract interface DirectSetButtonDrawableInterface
  {
    public abstract void setButtonDrawable(Drawable paramDrawable);
  }
}
