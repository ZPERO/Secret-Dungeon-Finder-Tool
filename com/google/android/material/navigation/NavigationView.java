package com.google.android.material.navigation;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.ClassLoaderCreator;
import android.os.Parcelable.Creator;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.appcompat.content.wiki.AppCompatResources;
import androidx.appcompat.view.SupportMenuInflater;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuBuilder.Callback;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.widget.TintTypedArray;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.customview.view.AbsSavedState;
import com.google.android.material.R.style;
import com.google.android.material.R.styleable;
import com.google.android.material.internal.NavigationMenu;
import com.google.android.material.internal.NavigationMenuPresenter;
import com.google.android.material.internal.ScrimInsetsFrameLayout;
import com.google.android.material.internal.ThemeEnforcement;

public class NavigationView
  extends ScrimInsetsFrameLayout
{
  private static final int[] CHECKED_STATE_SET = { 16842912 };
  private static final int[] DISABLED_STATE_SET = { -16842910 };
  private static final int PRESENTER_NAVIGATION_VIEW_ID = 1;
  OnNavigationItemSelectedListener listener;
  private final int maxWidth;
  private final NavigationMenu menu;
  private MenuInflater menuInflater;
  private final NavigationMenuPresenter presenter = new NavigationMenuPresenter();
  
  public NavigationView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public NavigationView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, com.google.android.material.R.attr.navigationViewStyle);
  }
  
  public NavigationView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    menu = new NavigationMenu(paramContext);
    TintTypedArray localTintTypedArray = ThemeEnforcement.obtainTintedStyledAttributes(paramContext, paramAttributeSet, R.styleable.NavigationView, paramInt, R.style.Widget_Design_NavigationView, new int[0]);
    ViewCompat.setBackground(this, localTintTypedArray.getDrawable(R.styleable.NavigationView_android_background));
    if (localTintTypedArray.hasValue(R.styleable.NavigationView_elevation)) {
      ViewCompat.setElevation(this, localTintTypedArray.getDimensionPixelSize(R.styleable.NavigationView_elevation, 0));
    }
    ViewCompat.setFitsSystemWindows(this, localTintTypedArray.getBoolean(R.styleable.NavigationView_android_fitsSystemWindows, false));
    maxWidth = localTintTypedArray.getDimensionPixelSize(R.styleable.NavigationView_android_maxWidth, 0);
    ColorStateList localColorStateList;
    if (localTintTypedArray.hasValue(R.styleable.NavigationView_itemIconTint)) {
      localColorStateList = localTintTypedArray.getColorStateList(R.styleable.NavigationView_itemIconTint);
    } else {
      localColorStateList = createDefaultColorStateList(16842808);
    }
    int i;
    if (localTintTypedArray.hasValue(R.styleable.NavigationView_itemTextAppearance))
    {
      i = localTintTypedArray.getResourceId(R.styleable.NavigationView_itemTextAppearance, 0);
      paramInt = 1;
    }
    else
    {
      paramInt = 0;
      i = 0;
    }
    paramAttributeSet = null;
    if (localTintTypedArray.hasValue(R.styleable.NavigationView_itemTextColor)) {
      paramAttributeSet = localTintTypedArray.getColorStateList(R.styleable.NavigationView_itemTextColor);
    }
    Object localObject = paramAttributeSet;
    if (paramInt == 0)
    {
      localObject = paramAttributeSet;
      if (paramAttributeSet == null) {
        localObject = createDefaultColorStateList(16842806);
      }
    }
    paramAttributeSet = localTintTypedArray.getDrawable(R.styleable.NavigationView_itemBackground);
    if (localTintTypedArray.hasValue(R.styleable.NavigationView_itemHorizontalPadding))
    {
      j = localTintTypedArray.getDimensionPixelSize(R.styleable.NavigationView_itemHorizontalPadding, 0);
      presenter.setItemHorizontalPadding(j);
    }
    int j = localTintTypedArray.getDimensionPixelSize(R.styleable.NavigationView_itemIconPadding, 0);
    menu.setCallback(new MenuBuilder.Callback()
    {
      public boolean onMenuItemSelected(MenuBuilder paramAnonymousMenuBuilder, MenuItem paramAnonymousMenuItem)
      {
        return (listener != null) && (listener.onNavigationItemSelected(paramAnonymousMenuItem));
      }
      
      public void onMenuModeChange(MenuBuilder paramAnonymousMenuBuilder) {}
    });
    presenter.setId(1);
    presenter.initForMenu(paramContext, menu);
    presenter.setItemIconTintList(localColorStateList);
    if (paramInt != 0) {
      presenter.setItemTextAppearance(i);
    }
    presenter.setItemTextColor((ColorStateList)localObject);
    presenter.setItemBackground(paramAttributeSet);
    presenter.setItemIconPadding(j);
    menu.addMenuPresenter(presenter);
    addView((View)presenter.getMenuView(this));
    if (localTintTypedArray.hasValue(R.styleable.NavigationView_menu)) {
      inflateMenu(localTintTypedArray.getResourceId(R.styleable.NavigationView_menu, 0));
    }
    if (localTintTypedArray.hasValue(R.styleable.NavigationView_headerLayout)) {
      inflateHeaderView(localTintTypedArray.getResourceId(R.styleable.NavigationView_headerLayout, 0));
    }
    localTintTypedArray.recycle();
  }
  
  private ColorStateList createDefaultColorStateList(int paramInt)
  {
    Object localObject = new TypedValue();
    if (!getContext().getTheme().resolveAttribute(paramInt, (TypedValue)localObject, true)) {
      return null;
    }
    ColorStateList localColorStateList = AppCompatResources.getColorStateList(getContext(), resourceId);
    if (!getContext().getTheme().resolveAttribute(androidx.appcompat.R.attr.colorPrimary, (TypedValue)localObject, true)) {
      return null;
    }
    paramInt = data;
    int i = localColorStateList.getDefaultColor();
    localObject = DISABLED_STATE_SET;
    int[] arrayOfInt1 = CHECKED_STATE_SET;
    int[] arrayOfInt2 = View.EMPTY_STATE_SET;
    int j = localColorStateList.getColorForState(DISABLED_STATE_SET, i);
    return new ColorStateList(new int[][] { localObject, arrayOfInt1, arrayOfInt2 }, new int[] { j, paramInt, i });
  }
  
  private MenuInflater getMenuInflater()
  {
    if (menuInflater == null) {
      menuInflater = new SupportMenuInflater(getContext());
    }
    return menuInflater;
  }
  
  public void addHeaderView(View paramView)
  {
    presenter.addHeaderView(paramView);
  }
  
  public MenuItem getCheckedItem()
  {
    return presenter.getCheckedItem();
  }
  
  public int getHeaderCount()
  {
    return presenter.getHeaderCount();
  }
  
  public View getHeaderView(int paramInt)
  {
    return presenter.getHeaderView(paramInt);
  }
  
  public Drawable getItemBackground()
  {
    return presenter.getItemBackground();
  }
  
  public int getItemHorizontalPadding()
  {
    return presenter.getItemHorizontalPadding();
  }
  
  public int getItemIconPadding()
  {
    return presenter.getItemIconPadding();
  }
  
  public ColorStateList getItemIconTintList()
  {
    return presenter.getItemTintList();
  }
  
  public ColorStateList getItemTextColor()
  {
    return presenter.getItemTextColor();
  }
  
  public Menu getMenu()
  {
    return menu;
  }
  
  public View inflateHeaderView(int paramInt)
  {
    return presenter.inflateHeaderView(paramInt);
  }
  
  public void inflateMenu(int paramInt)
  {
    presenter.setUpdateSuspended(true);
    getMenuInflater().inflate(paramInt, menu);
    presenter.setUpdateSuspended(false);
    presenter.updateMenuView(false);
  }
  
  protected void onInsetsChanged(WindowInsetsCompat paramWindowInsetsCompat)
  {
    presenter.dispatchApplyWindowInsets(paramWindowInsetsCompat);
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getMode(paramInt1);
    if (i != Integer.MIN_VALUE)
    {
      if (i == 0) {
        paramInt1 = View.MeasureSpec.makeMeasureSpec(maxWidth, 1073741824);
      }
    }
    else {
      paramInt1 = View.MeasureSpec.makeMeasureSpec(Math.min(View.MeasureSpec.getSize(paramInt1), maxWidth), 1073741824);
    }
    super.onMeasure(paramInt1, paramInt2);
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
    menu.restorePresenterStates(menuState);
  }
  
  protected Parcelable onSaveInstanceState()
  {
    SavedState localSavedState = new SavedState(super.onSaveInstanceState());
    menuState = new Bundle();
    menu.savePresenterStates(menuState);
    return localSavedState;
  }
  
  public void removeHeaderView(View paramView)
  {
    presenter.removeHeaderView(paramView);
  }
  
  public void setCheckedItem(int paramInt)
  {
    MenuItem localMenuItem = menu.findItem(paramInt);
    if (localMenuItem != null) {
      presenter.setCheckedItem((MenuItemImpl)localMenuItem);
    }
  }
  
  public void setCheckedItem(MenuItem paramMenuItem)
  {
    paramMenuItem = menu.findItem(paramMenuItem.getItemId());
    if (paramMenuItem != null)
    {
      presenter.setCheckedItem((MenuItemImpl)paramMenuItem);
      return;
    }
    throw new IllegalArgumentException("Called setCheckedItem(MenuItem) with an item that is not in the current menu.");
  }
  
  public void setItemBackground(Drawable paramDrawable)
  {
    presenter.setItemBackground(paramDrawable);
  }
  
  public void setItemBackgroundResource(int paramInt)
  {
    setItemBackground(ContextCompat.getDrawable(getContext(), paramInt));
  }
  
  public void setItemHorizontalPadding(int paramInt)
  {
    presenter.setItemHorizontalPadding(paramInt);
  }
  
  public void setItemHorizontalPaddingResource(int paramInt)
  {
    presenter.setItemHorizontalPadding(getResources().getDimensionPixelSize(paramInt));
  }
  
  public void setItemIconPadding(int paramInt)
  {
    presenter.setItemIconPadding(paramInt);
  }
  
  public void setItemIconPaddingResource(int paramInt)
  {
    presenter.setItemIconPadding(getResources().getDimensionPixelSize(paramInt));
  }
  
  public void setItemIconTintList(ColorStateList paramColorStateList)
  {
    presenter.setItemIconTintList(paramColorStateList);
  }
  
  public void setItemTextAppearance(int paramInt)
  {
    presenter.setItemTextAppearance(paramInt);
  }
  
  public void setItemTextColor(ColorStateList paramColorStateList)
  {
    presenter.setItemTextColor(paramColorStateList);
  }
  
  public void setNavigationItemSelectedListener(OnNavigationItemSelectedListener paramOnNavigationItemSelectedListener)
  {
    listener = paramOnNavigationItemSelectedListener;
  }
  
  public static abstract interface OnNavigationItemSelectedListener
  {
    public abstract boolean onNavigationItemSelected(MenuItem paramMenuItem);
  }
  
  public static class SavedState
    extends AbsSavedState
  {
    public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator()
    {
      public NavigationView.SavedState createFromParcel(Parcel paramAnonymousParcel)
      {
        return new NavigationView.SavedState(paramAnonymousParcel, null);
      }
      
      public NavigationView.SavedState createFromParcel(Parcel paramAnonymousParcel, ClassLoader paramAnonymousClassLoader)
      {
        return new NavigationView.SavedState(paramAnonymousParcel, paramAnonymousClassLoader);
      }
      
      public NavigationView.SavedState[] newArray(int paramAnonymousInt)
      {
        return new NavigationView.SavedState[paramAnonymousInt];
      }
    };
    public Bundle menuState;
    
    public SavedState(Parcel paramParcel, ClassLoader paramClassLoader)
    {
      super(paramClassLoader);
      menuState = paramParcel.readBundle(paramClassLoader);
    }
    
    public SavedState(Parcelable paramParcelable)
    {
      super();
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      super.writeToParcel(paramParcel, paramInt);
      paramParcel.writeBundle(menuState);
    }
  }
}
