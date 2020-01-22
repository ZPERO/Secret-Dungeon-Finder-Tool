package com.google.android.material.internal;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.SubMenu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.appcompat.view.menu.MenuPresenter.Callback;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.view.menu.SubMenuBuilder;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.google.android.material.R.dimen;
import com.google.android.material.R.layout;
import java.util.ArrayList;

public class NavigationMenuPresenter
  implements MenuPresenter
{
  private static final String STATE_ADAPTER = "android:menu:adapter";
  private static final String STATE_HEADER = "android:menu:header";
  private static final String STATE_HIERARCHY = "android:menu:list";
  NavigationMenuAdapter adapter;
  private MenuPresenter.Callback callback;
  LinearLayout headerLayout;
  ColorStateList iconTintList;
  Drawable itemBackground;
  int itemHorizontalPadding;
  int itemIconPadding;
  LayoutInflater layoutInflater;
  private int mId;
  MenuBuilder menu;
  private NavigationMenuView menuView;
  final View.OnClickListener onClickListener = new View.OnClickListener()
  {
    public void onClick(View paramAnonymousView)
    {
      paramAnonymousView = (NavigationMenuItemView)paramAnonymousView;
      setUpdateSuspended(true);
      paramAnonymousView = paramAnonymousView.getItemData();
      boolean bool = menu.performItemAction(paramAnonymousView, NavigationMenuPresenter.this, 0);
      if ((paramAnonymousView != null) && (paramAnonymousView.isCheckable()) && (bool)) {
        adapter.setCheckedItem(paramAnonymousView);
      }
      setUpdateSuspended(false);
      updateMenuView(false);
    }
  };
  int paddingSeparator;
  private int paddingTopDefault;
  int textAppearance;
  boolean textAppearanceSet;
  ColorStateList textColor;
  
  public NavigationMenuPresenter() {}
  
  public void addHeaderView(View paramView)
  {
    headerLayout.addView(paramView);
    paramView = menuView;
    paramView.setPadding(0, 0, 0, paramView.getPaddingBottom());
  }
  
  public boolean collapseItemActionView(MenuBuilder paramMenuBuilder, MenuItemImpl paramMenuItemImpl)
  {
    return false;
  }
  
  public void dispatchApplyWindowInsets(WindowInsetsCompat paramWindowInsetsCompat)
  {
    int i = paramWindowInsetsCompat.getSystemWindowInsetTop();
    if (paddingTopDefault != i)
    {
      paddingTopDefault = i;
      if (headerLayout.getChildCount() == 0)
      {
        NavigationMenuView localNavigationMenuView = menuView;
        localNavigationMenuView.setPadding(0, paddingTopDefault, 0, localNavigationMenuView.getPaddingBottom());
      }
    }
    ViewCompat.dispatchApplyWindowInsets(headerLayout, paramWindowInsetsCompat);
  }
  
  public boolean expandItemActionView(MenuBuilder paramMenuBuilder, MenuItemImpl paramMenuItemImpl)
  {
    return false;
  }
  
  public boolean flagActionItems()
  {
    return false;
  }
  
  public MenuItemImpl getCheckedItem()
  {
    return adapter.getCheckedItem();
  }
  
  public int getHeaderCount()
  {
    return headerLayout.getChildCount();
  }
  
  public View getHeaderView(int paramInt)
  {
    return headerLayout.getChildAt(paramInt);
  }
  
  public int getId()
  {
    return mId;
  }
  
  public Drawable getItemBackground()
  {
    return itemBackground;
  }
  
  public int getItemHorizontalPadding()
  {
    return itemHorizontalPadding;
  }
  
  public int getItemIconPadding()
  {
    return itemIconPadding;
  }
  
  public ColorStateList getItemTextColor()
  {
    return textColor;
  }
  
  public ColorStateList getItemTintList()
  {
    return iconTintList;
  }
  
  public MenuView getMenuView(ViewGroup paramViewGroup)
  {
    if (menuView == null)
    {
      menuView = ((NavigationMenuView)layoutInflater.inflate(R.layout.design_navigation_menu, paramViewGroup, false));
      if (adapter == null) {
        adapter = new NavigationMenuAdapter();
      }
      headerLayout = ((LinearLayout)layoutInflater.inflate(R.layout.design_navigation_item_header, menuView, false));
      menuView.setAdapter(adapter);
    }
    return menuView;
  }
  
  public View inflateHeaderView(int paramInt)
  {
    View localView = layoutInflater.inflate(paramInt, headerLayout, false);
    addHeaderView(localView);
    return localView;
  }
  
  public void initForMenu(Context paramContext, MenuBuilder paramMenuBuilder)
  {
    layoutInflater = LayoutInflater.from(paramContext);
    menu = paramMenuBuilder;
    paddingSeparator = paramContext.getResources().getDimensionPixelOffset(R.dimen.design_navigation_separator_vertical_padding);
  }
  
  public void onCloseMenu(MenuBuilder paramMenuBuilder, boolean paramBoolean)
  {
    MenuPresenter.Callback localCallback = callback;
    if (localCallback != null) {
      localCallback.onCloseMenu(paramMenuBuilder, paramBoolean);
    }
  }
  
  public void onRestoreInstanceState(Parcelable paramParcelable)
  {
    if ((paramParcelable instanceof Bundle))
    {
      paramParcelable = (Bundle)paramParcelable;
      Object localObject = paramParcelable.getSparseParcelableArray("android:menu:list");
      if (localObject != null) {
        menuView.restoreHierarchyState((SparseArray)localObject);
      }
      localObject = paramParcelable.getBundle("android:menu:adapter");
      if (localObject != null) {
        adapter.restoreInstanceState((Bundle)localObject);
      }
      paramParcelable = paramParcelable.getSparseParcelableArray("android:menu:header");
      if (paramParcelable != null) {
        headerLayout.restoreHierarchyState(paramParcelable);
      }
    }
  }
  
  public Parcelable onSaveInstanceState()
  {
    Bundle localBundle = new Bundle();
    if (menuView != null)
    {
      localObject = new SparseArray();
      menuView.saveHierarchyState((SparseArray)localObject);
      localBundle.putSparseParcelableArray("android:menu:list", (SparseArray)localObject);
    }
    Object localObject = adapter;
    if (localObject != null) {
      localBundle.putBundle("android:menu:adapter", ((NavigationMenuAdapter)localObject).createInstanceState());
    }
    if (headerLayout != null)
    {
      localObject = new SparseArray();
      headerLayout.saveHierarchyState((SparseArray)localObject);
      localBundle.putSparseParcelableArray("android:menu:header", (SparseArray)localObject);
    }
    return localBundle;
  }
  
  public boolean onSubMenuSelected(SubMenuBuilder paramSubMenuBuilder)
  {
    return false;
  }
  
  public void removeHeaderView(View paramView)
  {
    headerLayout.removeView(paramView);
    if (headerLayout.getChildCount() == 0)
    {
      paramView = menuView;
      paramView.setPadding(0, paddingTopDefault, 0, paramView.getPaddingBottom());
    }
  }
  
  public void setCallback(MenuPresenter.Callback paramCallback)
  {
    callback = paramCallback;
  }
  
  public void setCheckedItem(MenuItemImpl paramMenuItemImpl)
  {
    adapter.setCheckedItem(paramMenuItemImpl);
  }
  
  public void setId(int paramInt)
  {
    mId = paramInt;
  }
  
  public void setItemBackground(Drawable paramDrawable)
  {
    itemBackground = paramDrawable;
    updateMenuView(false);
  }
  
  public void setItemHorizontalPadding(int paramInt)
  {
    itemHorizontalPadding = paramInt;
    updateMenuView(false);
  }
  
  public void setItemIconPadding(int paramInt)
  {
    itemIconPadding = paramInt;
    updateMenuView(false);
  }
  
  public void setItemIconTintList(ColorStateList paramColorStateList)
  {
    iconTintList = paramColorStateList;
    updateMenuView(false);
  }
  
  public void setItemTextAppearance(int paramInt)
  {
    textAppearance = paramInt;
    textAppearanceSet = true;
    updateMenuView(false);
  }
  
  public void setItemTextColor(ColorStateList paramColorStateList)
  {
    textColor = paramColorStateList;
    updateMenuView(false);
  }
  
  public void setUpdateSuspended(boolean paramBoolean)
  {
    NavigationMenuAdapter localNavigationMenuAdapter = adapter;
    if (localNavigationMenuAdapter != null) {
      localNavigationMenuAdapter.setUpdateSuspended(paramBoolean);
    }
  }
  
  public void updateMenuView(boolean paramBoolean)
  {
    NavigationMenuAdapter localNavigationMenuAdapter = adapter;
    if (localNavigationMenuAdapter != null) {
      localNavigationMenuAdapter.update();
    }
  }
  
  private static class HeaderViewHolder
    extends NavigationMenuPresenter.ViewHolder
  {
    public HeaderViewHolder(View paramView)
    {
      super();
    }
  }
  
  private class NavigationMenuAdapter
    extends RecyclerView.Adapter<NavigationMenuPresenter.ViewHolder>
  {
    private static final String STATE_ACTION_VIEWS = "android:menu:action_views";
    private static final String STATE_CHECKED_ITEM = "android:menu:checked";
    private static final int VIEW_TYPE_HEADER = 3;
    private static final int VIEW_TYPE_NORMAL = 0;
    private static final int VIEW_TYPE_SEPARATOR = 2;
    private static final int VIEW_TYPE_SUBHEADER = 1;
    private MenuItemImpl checkedItem;
    private final ArrayList<NavigationMenuPresenter.NavigationMenuItem> items = new ArrayList();
    private boolean updateSuspended;
    
    NavigationMenuAdapter()
    {
      prepareMenuItems();
    }
    
    private void appendTransparentIconIfMissing(int paramInt1, int paramInt2)
    {
      while (paramInt1 < paramInt2)
      {
        items.get(paramInt1)).needsEmptyIcon = true;
        paramInt1 += 1;
      }
    }
    
    private void prepareMenuItems()
    {
      if (updateSuspended) {
        return;
      }
      updateSuspended = true;
      items.clear();
      items.add(new NavigationMenuPresenter.NavigationMenuHeaderItem());
      int i2 = menu.getVisibleItems().size();
      int m = 0;
      int n = -1;
      boolean bool2 = false;
      int k;
      for (int i = 0; m < i2; i = k)
      {
        Object localObject = (MenuItemImpl)menu.getVisibleItems().get(m);
        if (((MenuItemImpl)localObject).isChecked()) {
          setCheckedItem((MenuItemImpl)localObject);
        }
        if (((MenuItemImpl)localObject).isCheckable()) {
          ((MenuItemImpl)localObject).setExclusiveCheckable(false);
        }
        boolean bool1;
        int i1;
        int j;
        if (((MenuItemImpl)localObject).hasSubMenu())
        {
          SubMenu localSubMenu = ((MenuItemImpl)localObject).getSubMenu();
          bool1 = bool2;
          i1 = n;
          k = i;
          if (localSubMenu.hasVisibleItems())
          {
            if (m != 0) {
              items.add(new NavigationMenuPresenter.NavigationMenuSeparatorItem(paddingSeparator, 0));
            }
            items.add(new NavigationMenuPresenter.NavigationMenuTextItem((MenuItemImpl)localObject));
            int i3 = items.size();
            int i4 = localSubMenu.size();
            i1 = 0;
            for (j = 0; i1 < i4; j = k)
            {
              MenuItemImpl localMenuItemImpl = (MenuItemImpl)localSubMenu.getItem(i1);
              k = j;
              if (localMenuItemImpl.isVisible())
              {
                k = j;
                if (j == 0)
                {
                  k = j;
                  if (localMenuItemImpl.getIcon() != null) {
                    k = 1;
                  }
                }
                if (localMenuItemImpl.isCheckable()) {
                  localMenuItemImpl.setExclusiveCheckable(false);
                }
                if (((MenuItemImpl)localObject).isChecked()) {
                  setCheckedItem((MenuItemImpl)localObject);
                }
                items.add(new NavigationMenuPresenter.NavigationMenuTextItem(localMenuItemImpl));
              }
              i1 += 1;
            }
            bool1 = bool2;
            i1 = n;
            k = i;
            if (j != 0)
            {
              appendTransparentIconIfMissing(i3, items.size());
              bool1 = bool2;
              i1 = n;
              k = i;
            }
          }
        }
        else
        {
          i1 = ((MenuItemImpl)localObject).getGroupId();
          if (i1 != n)
          {
            i = items.size();
            j = i;
            if (((MenuItemImpl)localObject).getIcon() != null) {
              bool1 = true;
            } else {
              bool1 = false;
            }
            if (m != 0)
            {
              j = i + 1;
              items.add(new NavigationMenuPresenter.NavigationMenuSeparatorItem(paddingSeparator, paddingSeparator));
            }
          }
          else
          {
            bool1 = bool2;
            j = i;
            if (!bool2)
            {
              bool1 = bool2;
              j = i;
              if (((MenuItemImpl)localObject).getIcon() != null)
              {
                appendTransparentIconIfMissing(i, items.size());
                bool1 = true;
                j = i;
              }
            }
          }
          localObject = new NavigationMenuPresenter.NavigationMenuTextItem((MenuItemImpl)localObject);
          needsEmptyIcon = bool1;
          items.add(localObject);
          k = j;
        }
        m += 1;
        bool2 = bool1;
        n = i1;
      }
      updateSuspended = false;
    }
    
    public Bundle createInstanceState()
    {
      Bundle localBundle = new Bundle();
      Object localObject = checkedItem;
      if (localObject != null) {
        localBundle.putInt("android:menu:checked", ((MenuItemImpl)localObject).getItemId());
      }
      SparseArray localSparseArray = new SparseArray();
      int i = 0;
      int j = items.size();
      while (i < j)
      {
        localObject = (NavigationMenuPresenter.NavigationMenuItem)items.get(i);
        if ((localObject instanceof NavigationMenuPresenter.NavigationMenuTextItem))
        {
          MenuItemImpl localMenuItemImpl = ((NavigationMenuPresenter.NavigationMenuTextItem)localObject).getMenuItem();
          if (localMenuItemImpl != null) {
            localObject = localMenuItemImpl.getActionView();
          } else {
            localObject = null;
          }
          if (localObject != null)
          {
            ParcelableSparseArray localParcelableSparseArray = new ParcelableSparseArray();
            ((View)localObject).saveHierarchyState(localParcelableSparseArray);
            localSparseArray.put(localMenuItemImpl.getItemId(), localParcelableSparseArray);
          }
        }
        i += 1;
      }
      localBundle.putSparseParcelableArray("android:menu:action_views", localSparseArray);
      return localBundle;
    }
    
    public MenuItemImpl getCheckedItem()
    {
      return checkedItem;
    }
    
    public int getItemCount()
    {
      return items.size();
    }
    
    public long getItemId(int paramInt)
    {
      return paramInt;
    }
    
    public int getItemViewType(int paramInt)
    {
      NavigationMenuPresenter.NavigationMenuItem localNavigationMenuItem = (NavigationMenuPresenter.NavigationMenuItem)items.get(paramInt);
      if ((localNavigationMenuItem instanceof NavigationMenuPresenter.NavigationMenuSeparatorItem)) {
        return 2;
      }
      if ((localNavigationMenuItem instanceof NavigationMenuPresenter.NavigationMenuHeaderItem)) {
        return 3;
      }
      if ((localNavigationMenuItem instanceof NavigationMenuPresenter.NavigationMenuTextItem))
      {
        if (((NavigationMenuPresenter.NavigationMenuTextItem)localNavigationMenuItem).getMenuItem().hasSubMenu()) {
          return 1;
        }
        return 0;
      }
      throw new RuntimeException("Unknown item type.");
    }
    
    public void onBindViewHolder(NavigationMenuPresenter.ViewHolder paramViewHolder, int paramInt)
    {
      int i = getItemViewType(paramInt);
      if (i != 0)
      {
        if (i != 1)
        {
          if (i != 2) {
            return;
          }
          localObject = (NavigationMenuPresenter.NavigationMenuSeparatorItem)items.get(paramInt);
          itemView.setPadding(0, ((NavigationMenuPresenter.NavigationMenuSeparatorItem)localObject).getPaddingTop(), 0, ((NavigationMenuPresenter.NavigationMenuSeparatorItem)localObject).getPaddingBottom());
          return;
        }
        ((TextView)itemView).setText(((NavigationMenuPresenter.NavigationMenuTextItem)items.get(paramInt)).getMenuItem().getTitle());
        return;
      }
      Object localObject = (NavigationMenuItemView)itemView;
      ((NavigationMenuItemView)localObject).setIconTintList(iconTintList);
      if (textAppearanceSet) {
        ((NavigationMenuItemView)localObject).setTextAppearance(textAppearance);
      }
      if (textColor != null) {
        ((NavigationMenuItemView)localObject).setTextColor(textColor);
      }
      if (itemBackground != null) {
        paramViewHolder = itemBackground.getConstantState().newDrawable();
      } else {
        paramViewHolder = null;
      }
      ViewCompat.setBackground((View)localObject, paramViewHolder);
      paramViewHolder = (NavigationMenuPresenter.NavigationMenuTextItem)items.get(paramInt);
      ((NavigationMenuItemView)localObject).setNeedsEmptyIcon(needsEmptyIcon);
      ((NavigationMenuItemView)localObject).setHorizontalPadding(itemHorizontalPadding);
      ((NavigationMenuItemView)localObject).setIconPadding(itemIconPadding);
      ((NavigationMenuItemView)localObject).initialize(paramViewHolder.getMenuItem(), 0);
    }
    
    public NavigationMenuPresenter.ViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt)
    {
      if (paramInt != 0)
      {
        if (paramInt != 1)
        {
          if (paramInt != 2)
          {
            if (paramInt != 3) {
              return null;
            }
            return new NavigationMenuPresenter.HeaderViewHolder(headerLayout);
          }
          return new NavigationMenuPresenter.SeparatorViewHolder(layoutInflater, paramViewGroup);
        }
        return new NavigationMenuPresenter.SubheaderViewHolder(layoutInflater, paramViewGroup);
      }
      return new NavigationMenuPresenter.NormalViewHolder(layoutInflater, paramViewGroup, onClickListener);
    }
    
    public void onViewRecycled(NavigationMenuPresenter.ViewHolder paramViewHolder)
    {
      if ((paramViewHolder instanceof NavigationMenuPresenter.NormalViewHolder)) {
        ((NavigationMenuItemView)itemView).recycle();
      }
    }
    
    public void restoreInstanceState(Bundle paramBundle)
    {
      int j = 0;
      int k = paramBundle.getInt("android:menu:checked", 0);
      int i;
      Object localObject1;
      if (k != 0)
      {
        updateSuspended = true;
        int m = items.size();
        i = 0;
        while (i < m)
        {
          localObject1 = (NavigationMenuPresenter.NavigationMenuItem)items.get(i);
          if ((localObject1 instanceof NavigationMenuPresenter.NavigationMenuTextItem))
          {
            localObject1 = ((NavigationMenuPresenter.NavigationMenuTextItem)localObject1).getMenuItem();
            if ((localObject1 != null) && (((MenuItemImpl)localObject1).getItemId() == k))
            {
              setCheckedItem((MenuItemImpl)localObject1);
              break;
            }
          }
          i += 1;
        }
        updateSuspended = false;
        prepareMenuItems();
      }
      paramBundle = paramBundle.getSparseParcelableArray("android:menu:action_views");
      if (paramBundle != null)
      {
        k = items.size();
        i = j;
        while (i < k)
        {
          localObject1 = (NavigationMenuPresenter.NavigationMenuItem)items.get(i);
          if ((localObject1 instanceof NavigationMenuPresenter.NavigationMenuTextItem))
          {
            Object localObject2 = ((NavigationMenuPresenter.NavigationMenuTextItem)localObject1).getMenuItem();
            if (localObject2 != null)
            {
              localObject1 = ((MenuItemImpl)localObject2).getActionView();
              if (localObject1 != null)
              {
                localObject2 = (ParcelableSparseArray)paramBundle.get(((MenuItemImpl)localObject2).getItemId());
                if (localObject2 != null) {
                  ((View)localObject1).restoreHierarchyState((SparseArray)localObject2);
                }
              }
            }
          }
          i += 1;
        }
      }
    }
    
    public void setCheckedItem(MenuItemImpl paramMenuItemImpl)
    {
      if (checkedItem != paramMenuItemImpl)
      {
        if (!paramMenuItemImpl.isCheckable()) {
          return;
        }
        MenuItemImpl localMenuItemImpl = checkedItem;
        if (localMenuItemImpl != null) {
          localMenuItemImpl.setChecked(false);
        }
        checkedItem = paramMenuItemImpl;
        paramMenuItemImpl.setChecked(true);
      }
    }
    
    public void setUpdateSuspended(boolean paramBoolean)
    {
      updateSuspended = paramBoolean;
    }
    
    public void update()
    {
      prepareMenuItems();
      notifyDataSetChanged();
    }
  }
  
  private static class NavigationMenuHeaderItem
    implements NavigationMenuPresenter.NavigationMenuItem
  {
    NavigationMenuHeaderItem() {}
  }
  
  private static abstract interface NavigationMenuItem {}
  
  private static class NavigationMenuSeparatorItem
    implements NavigationMenuPresenter.NavigationMenuItem
  {
    private final int paddingBottom;
    private final int paddingTop;
    
    public NavigationMenuSeparatorItem(int paramInt1, int paramInt2)
    {
      paddingTop = paramInt1;
      paddingBottom = paramInt2;
    }
    
    public int getPaddingBottom()
    {
      return paddingBottom;
    }
    
    public int getPaddingTop()
    {
      return paddingTop;
    }
  }
  
  private static class NavigationMenuTextItem
    implements NavigationMenuPresenter.NavigationMenuItem
  {
    private final MenuItemImpl menuItem;
    boolean needsEmptyIcon;
    
    NavigationMenuTextItem(MenuItemImpl paramMenuItemImpl)
    {
      menuItem = paramMenuItemImpl;
    }
    
    public MenuItemImpl getMenuItem()
    {
      return menuItem;
    }
  }
  
  private static class NormalViewHolder
    extends NavigationMenuPresenter.ViewHolder
  {
    public NormalViewHolder(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, View.OnClickListener paramOnClickListener)
    {
      super();
      itemView.setOnClickListener(paramOnClickListener);
    }
  }
  
  private static class SeparatorViewHolder
    extends NavigationMenuPresenter.ViewHolder
  {
    public SeparatorViewHolder(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup)
    {
      super();
    }
  }
  
  private static class SubheaderViewHolder
    extends NavigationMenuPresenter.ViewHolder
  {
    public SubheaderViewHolder(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup)
    {
      super();
    }
  }
  
  private static abstract class ViewHolder
    extends RecyclerView.ViewHolder
  {
    public ViewHolder(View paramView)
    {
      super();
    }
  }
}
