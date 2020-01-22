package com.google.android.material.bottomnavigation;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.view.ViewGroup;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.appcompat.view.menu.MenuPresenter.Callback;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.view.menu.SubMenuBuilder;

public class BottomNavigationPresenter
  implements MenuPresenter
{
  private int Id;
  private MenuBuilder menu;
  private BottomNavigationMenuView menuView;
  private boolean updateSuspended = false;
  
  public BottomNavigationPresenter() {}
  
  public boolean collapseItemActionView(MenuBuilder paramMenuBuilder, MenuItemImpl paramMenuItemImpl)
  {
    return false;
  }
  
  public boolean expandItemActionView(MenuBuilder paramMenuBuilder, MenuItemImpl paramMenuItemImpl)
  {
    return false;
  }
  
  public boolean flagActionItems()
  {
    return false;
  }
  
  public int getId()
  {
    return Id;
  }
  
  public MenuView getMenuView(ViewGroup paramViewGroup)
  {
    return menuView;
  }
  
  public void initForMenu(Context paramContext, MenuBuilder paramMenuBuilder)
  {
    menu = paramMenuBuilder;
    menuView.initialize(menu);
  }
  
  public void onCloseMenu(MenuBuilder paramMenuBuilder, boolean paramBoolean) {}
  
  public void onRestoreInstanceState(Parcelable paramParcelable)
  {
    if ((paramParcelable instanceof SavedState)) {
      menuView.tryRestoreSelectedItemId(selectedItemId);
    }
  }
  
  public Parcelable onSaveInstanceState()
  {
    SavedState localSavedState = new SavedState();
    selectedItemId = menuView.getSelectedItemId();
    return localSavedState;
  }
  
  public boolean onSubMenuSelected(SubMenuBuilder paramSubMenuBuilder)
  {
    return false;
  }
  
  public void setBottomNavigationMenuView(BottomNavigationMenuView paramBottomNavigationMenuView)
  {
    menuView = paramBottomNavigationMenuView;
  }
  
  public void setCallback(MenuPresenter.Callback paramCallback) {}
  
  public void setId(int paramInt)
  {
    Id = paramInt;
  }
  
  public void setUpdateSuspended(boolean paramBoolean)
  {
    updateSuspended = paramBoolean;
  }
  
  public void updateMenuView(boolean paramBoolean)
  {
    if (updateSuspended) {
      return;
    }
    if (paramBoolean)
    {
      menuView.buildMenuView();
      return;
    }
    menuView.updateMenuView();
  }
  
  static class SavedState
    implements Parcelable
  {
    public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator()
    {
      public BottomNavigationPresenter.SavedState createFromParcel(Parcel paramAnonymousParcel)
      {
        return new BottomNavigationPresenter.SavedState(paramAnonymousParcel);
      }
      
      public BottomNavigationPresenter.SavedState[] newArray(int paramAnonymousInt)
      {
        return new BottomNavigationPresenter.SavedState[paramAnonymousInt];
      }
    };
    int selectedItemId;
    
    SavedState() {}
    
    SavedState(Parcel paramParcel)
    {
      selectedItemId = paramParcel.readInt();
    }
    
    public int describeContents()
    {
      return 0;
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      paramParcel.writeInt(selectedItemId);
    }
  }
}
