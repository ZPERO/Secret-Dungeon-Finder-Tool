package androidx.core.view;

import android.os.Build.VERSION;
import android.view.Menu;
import android.view.MenuItem;
import androidx.core.internal.view.SupportMenu;

public final class MenuCompat
{
  private MenuCompat() {}
  
  public static void setGroupDividerEnabled(Menu paramMenu, boolean paramBoolean)
  {
    if ((paramMenu instanceof SupportMenu))
    {
      ((SupportMenu)paramMenu).setGroupDividerEnabled(paramBoolean);
      return;
    }
    if (Build.VERSION.SDK_INT >= 28) {
      paramMenu.setGroupDividerEnabled(paramBoolean);
    }
  }
  
  public static void setShowAsAction(MenuItem paramMenuItem, int paramInt)
  {
    paramMenuItem.setShowAsAction(paramInt);
  }
}
