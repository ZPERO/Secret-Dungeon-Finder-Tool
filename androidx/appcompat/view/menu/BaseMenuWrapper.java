package androidx.appcompat.view.menu;

import android.content.Context;
import android.view.MenuItem;
import android.view.SubMenu;
import androidx.collection.ArrayMap;
import androidx.core.internal.view.SupportMenuItem;
import androidx.core.internal.view.SupportSubMenu;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

abstract class BaseMenuWrapper
{
  final Context mContext;
  private Map<SupportMenuItem, MenuItem> mMenuItems;
  private Map<SupportSubMenu, SubMenu> mSubMenus;
  
  BaseMenuWrapper(Context paramContext)
  {
    mContext = paramContext;
  }
  
  final MenuItem getMenuItemWrapper(MenuItem paramMenuItem)
  {
    MenuItem localMenuItem = paramMenuItem;
    if ((paramMenuItem instanceof SupportMenuItem))
    {
      SupportMenuItem localSupportMenuItem = (SupportMenuItem)paramMenuItem;
      if (mMenuItems == null) {
        mMenuItems = new ArrayMap();
      }
      paramMenuItem = (MenuItem)mMenuItems.get(paramMenuItem);
      localMenuItem = paramMenuItem;
      if (paramMenuItem == null)
      {
        paramMenuItem = new MenuItemWrapperICS(mContext, localSupportMenuItem);
        mMenuItems.put(localSupportMenuItem, paramMenuItem);
        return paramMenuItem;
      }
    }
    return localMenuItem;
  }
  
  final SubMenu getSubMenuWrapper(SubMenu paramSubMenu)
  {
    SubMenu localSubMenu = paramSubMenu;
    if ((paramSubMenu instanceof SupportSubMenu))
    {
      SupportSubMenu localSupportSubMenu = (SupportSubMenu)paramSubMenu;
      if (mSubMenus == null) {
        mSubMenus = new ArrayMap();
      }
      paramSubMenu = (SubMenu)mSubMenus.get(localSupportSubMenu);
      localSubMenu = paramSubMenu;
      if (paramSubMenu == null)
      {
        paramSubMenu = new SubMenuWrapperICS(mContext, localSupportSubMenu);
        mSubMenus.put(localSupportSubMenu, paramSubMenu);
        return paramSubMenu;
      }
    }
    return localSubMenu;
  }
  
  final void internalClear()
  {
    Map localMap = mMenuItems;
    if (localMap != null) {
      localMap.clear();
    }
    localMap = mSubMenus;
    if (localMap != null) {
      localMap.clear();
    }
  }
  
  final void internalRemoveGroup(int paramInt)
  {
    Object localObject = mMenuItems;
    if (localObject == null) {
      return;
    }
    localObject = ((Map)localObject).keySet().iterator();
    while (((Iterator)localObject).hasNext()) {
      if (paramInt == ((MenuItem)((Iterator)localObject).next()).getGroupId()) {
        ((Iterator)localObject).remove();
      }
    }
  }
  
  final void internalRemoveItem(int paramInt)
  {
    Object localObject = mMenuItems;
    if (localObject == null) {
      return;
    }
    localObject = ((Map)localObject).keySet().iterator();
    while (((Iterator)localObject).hasNext()) {
      if (paramInt == ((MenuItem)((Iterator)localObject).next()).getItemId()) {
        ((Iterator)localObject).remove();
      }
    }
  }
}
