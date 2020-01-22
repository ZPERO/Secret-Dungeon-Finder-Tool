package androidx.appcompat.widget;

import android.view.MenuItem;
import androidx.appcompat.view.menu.MenuBuilder;

public abstract interface MenuItemHoverListener
{
  public abstract void onItemHoverEnter(MenuBuilder paramMenuBuilder, MenuItem paramMenuItem);
  
  public abstract void onItemHoverExit(MenuBuilder paramMenuBuilder, MenuItem paramMenuItem);
}
