package com.google.android.material.bottomnavigation;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.ClassLoaderCreator;
import android.os.Parcelable.Creator;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import androidx.appcompat.view.SupportMenuInflater;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuBuilder.Callback;
import androidx.appcompat.widget.TintTypedArray;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.customview.view.AbsSavedState;
import com.google.android.material.R.attr;
import com.google.android.material.R.color;
import com.google.android.material.R.dimen;
import com.google.android.material.R.style;
import com.google.android.material.R.styleable;
import com.google.android.material.internal.ThemeEnforcement;

public class BottomNavigationView
  extends FrameLayout
{
  private static final int MENU_PRESENTER_ID = 1;
  private final MenuBuilder menu;
  private MenuInflater menuInflater;
  private final BottomNavigationMenuView menuView;
  private final BottomNavigationPresenter presenter = new BottomNavigationPresenter();
  private OnNavigationItemReselectedListener reselectedListener;
  private OnNavigationItemSelectedListener selectedListener;
  
  public BottomNavigationView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public BottomNavigationView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, R.attr.bottomNavigationStyle);
  }
  
  public BottomNavigationView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    menu = new BottomNavigationMenu(paramContext);
    menuView = new BottomNavigationMenuView(paramContext);
    FrameLayout.LayoutParams localLayoutParams = new FrameLayout.LayoutParams(-2, -2);
    gravity = 17;
    menuView.setLayoutParams(localLayoutParams);
    presenter.setBottomNavigationMenuView(menuView);
    presenter.setId(1);
    menuView.setPresenter(presenter);
    menu.addMenuPresenter(presenter);
    presenter.initForMenu(getContext(), menu);
    paramAttributeSet = ThemeEnforcement.obtainTintedStyledAttributes(paramContext, paramAttributeSet, R.styleable.BottomNavigationView, paramInt, R.style.Widget_Design_BottomNavigationView, new int[] { R.styleable.BottomNavigationView_itemTextAppearanceInactive, R.styleable.BottomNavigationView_itemTextAppearanceActive });
    if (paramAttributeSet.hasValue(R.styleable.BottomNavigationView_itemIconTint))
    {
      menuView.setIconTintList(paramAttributeSet.getColorStateList(R.styleable.BottomNavigationView_itemIconTint));
    }
    else
    {
      BottomNavigationMenuView localBottomNavigationMenuView = menuView;
      localBottomNavigationMenuView.setIconTintList(localBottomNavigationMenuView.createDefaultColorStateList(16842808));
    }
    setItemIconSize(paramAttributeSet.getDimensionPixelSize(R.styleable.BottomNavigationView_itemIconSize, getResources().getDimensionPixelSize(R.dimen.design_bottom_navigation_icon_size)));
    if (paramAttributeSet.hasValue(R.styleable.BottomNavigationView_itemTextAppearanceInactive)) {
      setItemTextAppearanceInactive(paramAttributeSet.getResourceId(R.styleable.BottomNavigationView_itemTextAppearanceInactive, 0));
    }
    if (paramAttributeSet.hasValue(R.styleable.BottomNavigationView_itemTextAppearanceActive)) {
      setItemTextAppearanceActive(paramAttributeSet.getResourceId(R.styleable.BottomNavigationView_itemTextAppearanceActive, 0));
    }
    if (paramAttributeSet.hasValue(R.styleable.BottomNavigationView_itemTextColor)) {
      setItemTextColor(paramAttributeSet.getColorStateList(R.styleable.BottomNavigationView_itemTextColor));
    }
    if (paramAttributeSet.hasValue(R.styleable.BottomNavigationView_elevation)) {
      ViewCompat.setElevation(this, paramAttributeSet.getDimensionPixelSize(R.styleable.BottomNavigationView_elevation, 0));
    }
    setLabelVisibilityMode(paramAttributeSet.getInteger(R.styleable.BottomNavigationView_labelVisibilityMode, -1));
    setItemHorizontalTranslationEnabled(paramAttributeSet.getBoolean(R.styleable.BottomNavigationView_itemHorizontalTranslationEnabled, true));
    paramInt = paramAttributeSet.getResourceId(R.styleable.BottomNavigationView_itemBackground, 0);
    menuView.setItemBackgroundRes(paramInt);
    if (paramAttributeSet.hasValue(R.styleable.BottomNavigationView_menu)) {
      inflateMenu(paramAttributeSet.getResourceId(R.styleable.BottomNavigationView_menu, 0));
    }
    paramAttributeSet.recycle();
    addView(menuView, localLayoutParams);
    if (Build.VERSION.SDK_INT < 21) {
      addCompatibilityTopDivider(paramContext);
    }
    menu.setCallback(new MenuBuilder.Callback()
    {
      public boolean onMenuItemSelected(MenuBuilder paramAnonymousMenuBuilder, MenuItem paramAnonymousMenuItem)
      {
        if ((reselectedListener != null) && (paramAnonymousMenuItem.getItemId() == getSelectedItemId()))
        {
          reselectedListener.onNavigationItemReselected(paramAnonymousMenuItem);
          return true;
        }
        return (selectedListener != null) && (!selectedListener.onNavigationItemSelected(paramAnonymousMenuItem));
      }
      
      public void onMenuModeChange(MenuBuilder paramAnonymousMenuBuilder) {}
    });
  }
  
  private void addCompatibilityTopDivider(Context paramContext)
  {
    View localView = new View(paramContext);
    localView.setBackgroundColor(ContextCompat.getColor(paramContext, R.color.design_bottom_navigation_shadow_color));
    localView.setLayoutParams(new FrameLayout.LayoutParams(-1, getResources().getDimensionPixelSize(R.dimen.design_bottom_navigation_shadow_height)));
    addView(localView);
  }
  
  private MenuInflater getMenuInflater()
  {
    if (menuInflater == null) {
      menuInflater = new SupportMenuInflater(getContext());
    }
    return menuInflater;
  }
  
  public Drawable getItemBackground()
  {
    return menuView.getItemBackground();
  }
  
  public int getItemBackgroundResource()
  {
    return menuView.getItemBackgroundRes();
  }
  
  public int getItemIconSize()
  {
    return menuView.getItemIconSize();
  }
  
  public ColorStateList getItemIconTintList()
  {
    return menuView.getIconTintList();
  }
  
  public int getItemTextAppearanceActive()
  {
    return menuView.getItemTextAppearanceActive();
  }
  
  public int getItemTextAppearanceInactive()
  {
    return menuView.getItemTextAppearanceInactive();
  }
  
  public ColorStateList getItemTextColor()
  {
    return menuView.getItemTextColor();
  }
  
  public int getLabelVisibilityMode()
  {
    return menuView.getLabelVisibilityMode();
  }
  
  public int getMaxItemCount()
  {
    return 5;
  }
  
  public Menu getMenu()
  {
    return menu;
  }
  
  public int getSelectedItemId()
  {
    return menuView.getSelectedItemId();
  }
  
  public void inflateMenu(int paramInt)
  {
    presenter.setUpdateSuspended(true);
    getMenuInflater().inflate(paramInt, menu);
    presenter.setUpdateSuspended(false);
    presenter.updateMenuView(true);
  }
  
  public boolean isItemHorizontalTranslationEnabled()
  {
    return menuView.isItemHorizontalTranslationEnabled();
  }
  
  protected void onRestoreInstanceState(Parcelable paramParcelable)
  {
    if (!(paramParcelable instanceof SavedState))
    {
      super.onRestoreInstanceState(paramParcelable);
      return;
    }
    paramParcelable = (SavedState)paramParcelable;
    super.onRestoreInstanceState(paramParcelable.getSuperState());
    menu.restorePresenterStates(menuPresenterState);
  }
  
  protected Parcelable onSaveInstanceState()
  {
    SavedState localSavedState = new SavedState(super.onSaveInstanceState());
    menuPresenterState = new Bundle();
    menu.savePresenterStates(menuPresenterState);
    return localSavedState;
  }
  
  public void setItemBackground(Drawable paramDrawable)
  {
    menuView.setItemBackground(paramDrawable);
  }
  
  public void setItemBackgroundResource(int paramInt)
  {
    menuView.setItemBackgroundRes(paramInt);
  }
  
  public void setItemHorizontalTranslationEnabled(boolean paramBoolean)
  {
    if (menuView.isItemHorizontalTranslationEnabled() != paramBoolean)
    {
      menuView.setItemHorizontalTranslationEnabled(paramBoolean);
      presenter.updateMenuView(false);
    }
  }
  
  public void setItemIconSize(int paramInt)
  {
    menuView.setItemIconSize(paramInt);
  }
  
  public void setItemIconSizeRes(int paramInt)
  {
    setItemIconSize(getResources().getDimensionPixelSize(paramInt));
  }
  
  public void setItemIconTintList(ColorStateList paramColorStateList)
  {
    menuView.setIconTintList(paramColorStateList);
  }
  
  public void setItemTextAppearanceActive(int paramInt)
  {
    menuView.setItemTextAppearanceActive(paramInt);
  }
  
  public void setItemTextAppearanceInactive(int paramInt)
  {
    menuView.setItemTextAppearanceInactive(paramInt);
  }
  
  public void setItemTextColor(ColorStateList paramColorStateList)
  {
    menuView.setItemTextColor(paramColorStateList);
  }
  
  public void setLabelVisibilityMode(int paramInt)
  {
    if (menuView.getLabelVisibilityMode() != paramInt)
    {
      menuView.setLabelVisibilityMode(paramInt);
      presenter.updateMenuView(false);
    }
  }
  
  public void setOnNavigationItemReselectedListener(OnNavigationItemReselectedListener paramOnNavigationItemReselectedListener)
  {
    reselectedListener = paramOnNavigationItemReselectedListener;
  }
  
  public void setOnNavigationItemSelectedListener(OnNavigationItemSelectedListener paramOnNavigationItemSelectedListener)
  {
    selectedListener = paramOnNavigationItemSelectedListener;
  }
  
  public void setSelectedItemId(int paramInt)
  {
    MenuItem localMenuItem = menu.findItem(paramInt);
    if ((localMenuItem != null) && (!menu.performItemAction(localMenuItem, presenter, 0))) {
      localMenuItem.setChecked(true);
    }
  }
  
  public static abstract interface OnNavigationItemReselectedListener
  {
    public abstract void onNavigationItemReselected(MenuItem paramMenuItem);
  }
  
  public static abstract interface OnNavigationItemSelectedListener
  {
    public abstract boolean onNavigationItemSelected(MenuItem paramMenuItem);
  }
  
  static class SavedState
    extends AbsSavedState
  {
    public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator()
    {
      public BottomNavigationView.SavedState createFromParcel(Parcel paramAnonymousParcel)
      {
        return new BottomNavigationView.SavedState(paramAnonymousParcel, null);
      }
      
      public BottomNavigationView.SavedState createFromParcel(Parcel paramAnonymousParcel, ClassLoader paramAnonymousClassLoader)
      {
        return new BottomNavigationView.SavedState(paramAnonymousParcel, paramAnonymousClassLoader);
      }
      
      public BottomNavigationView.SavedState[] newArray(int paramAnonymousInt)
      {
        return new BottomNavigationView.SavedState[paramAnonymousInt];
      }
    };
    Bundle menuPresenterState;
    
    public SavedState(Parcel paramParcel, ClassLoader paramClassLoader)
    {
      super(paramClassLoader);
      readFromParcel(paramParcel, paramClassLoader);
    }
    
    public SavedState(Parcelable paramParcelable)
    {
      super();
    }
    
    private void readFromParcel(Parcel paramParcel, ClassLoader paramClassLoader)
    {
      menuPresenterState = paramParcel.readBundle(paramClassLoader);
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      super.writeToParcel(paramParcel, paramInt);
      paramParcel.writeBundle(menuPresenterState);
    }
  }
}
