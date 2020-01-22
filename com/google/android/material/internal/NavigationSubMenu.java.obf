package com.google.android.material.internal;

import android.content.Context;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.SubMenuBuilder;

public class NavigationSubMenu
  extends SubMenuBuilder
{
  public NavigationSubMenu(Context paramContext, NavigationMenu paramNavigationMenu, MenuItemImpl paramMenuItemImpl)
  {
    super(paramContext, paramNavigationMenu, paramMenuItemImpl);
  }
  
  public void onItemsChanged(boolean paramBoolean)
  {
    super.onItemsChanged(paramBoolean);
    ((MenuBuilder)getParentMenu()).onItemsChanged(paramBoolean);
  }
}
