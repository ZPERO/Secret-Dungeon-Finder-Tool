package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.transition.Transition;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.HeaderViewListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import androidx.appcompat.view.menu.ListMenuItemView;
import androidx.appcompat.view.menu.MenuAdapter;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;
import java.lang.reflect.Method;

public class MenuPopupWindow
  extends ListPopupWindow
  implements MenuItemHoverListener
{
  private static final String PAGE_KEY = "MenuPopupWindow";
  private static Method sSetTouchModalMethod;
  private MenuItemHoverListener mHoverListener;
  
  static
  {
    if (Build.VERSION.SDK_INT <= 28)
    {
      Object localObject = Boolean.TYPE;
      try
      {
        localObject = PopupWindow.class.getDeclaredMethod("setTouchModal", new Class[] { localObject });
        sSetTouchModalMethod = (Method)localObject;
        return;
      }
      catch (NoSuchMethodException localNoSuchMethodException)
      {
        for (;;) {}
      }
      Log.i("MenuPopupWindow", "Could not find method setTouchModal() on PopupWindow. Oh well.");
      return;
    }
  }
  
  public MenuPopupWindow(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    super(paramContext, paramAttributeSet, paramInt1, paramInt2);
  }
  
  DropDownListView createDropDownListView(Context paramContext, boolean paramBoolean)
  {
    paramContext = new MenuDropDownListView(paramContext, paramBoolean);
    paramContext.setHoverListener(this);
    return paramContext;
  }
  
  public void onItemHoverEnter(MenuBuilder paramMenuBuilder, MenuItem paramMenuItem)
  {
    MenuItemHoverListener localMenuItemHoverListener = mHoverListener;
    if (localMenuItemHoverListener != null) {
      localMenuItemHoverListener.onItemHoverEnter(paramMenuBuilder, paramMenuItem);
    }
  }
  
  public void onItemHoverExit(MenuBuilder paramMenuBuilder, MenuItem paramMenuItem)
  {
    MenuItemHoverListener localMenuItemHoverListener = mHoverListener;
    if (localMenuItemHoverListener != null) {
      localMenuItemHoverListener.onItemHoverExit(paramMenuBuilder, paramMenuItem);
    }
  }
  
  public void setEnterTransition(Object paramObject)
  {
    if (Build.VERSION.SDK_INT >= 23) {
      mPopup.setEnterTransition((Transition)paramObject);
    }
  }
  
  public void setExitTransition(Object paramObject)
  {
    if (Build.VERSION.SDK_INT >= 23) {
      mPopup.setExitTransition((Transition)paramObject);
    }
  }
  
  public void setHoverListener(MenuItemHoverListener paramMenuItemHoverListener)
  {
    mHoverListener = paramMenuItemHoverListener;
  }
  
  public void setTouchModal(boolean paramBoolean)
  {
    Method localMethod;
    PopupWindow localPopupWindow;
    if (Build.VERSION.SDK_INT <= 28)
    {
      localMethod = sSetTouchModalMethod;
      if (localMethod != null) {
        localPopupWindow = mPopup;
      }
    }
    else
    {
      try
      {
        localMethod.invoke(localPopupWindow, new Object[] { Boolean.valueOf(paramBoolean) });
        return;
      }
      catch (Exception localException)
      {
        for (;;) {}
      }
      Log.i("MenuPopupWindow", "Could not invoke setTouchModal() on PopupWindow. Oh well.");
      return;
      mPopup.setTouchModal(paramBoolean);
      return;
    }
  }
  
  public static class MenuDropDownListView
    extends DropDownListView
  {
    final int mAdvanceKey;
    private MenuItemHoverListener mHoverListener;
    private MenuItem mHoveredMenuItem;
    final int mRetreatKey;
    
    public MenuDropDownListView(Context paramContext, boolean paramBoolean)
    {
      super(paramBoolean);
      paramContext = paramContext.getResources().getConfiguration();
      if ((Build.VERSION.SDK_INT >= 17) && (1 == paramContext.getLayoutDirection()))
      {
        mAdvanceKey = 21;
        mRetreatKey = 22;
        return;
      }
      mAdvanceKey = 22;
      mRetreatKey = 21;
    }
    
    public void clearSelection()
    {
      setSelection(-1);
    }
    
    public boolean onHoverEvent(MotionEvent paramMotionEvent)
    {
      if (mHoverListener != null)
      {
        Object localObject1 = getAdapter();
        int i;
        if ((localObject1 instanceof HeaderViewListAdapter))
        {
          localObject1 = (HeaderViewListAdapter)localObject1;
          i = ((HeaderViewListAdapter)localObject1).getHeadersCount();
          localObject1 = (MenuAdapter)((HeaderViewListAdapter)localObject1).getWrappedAdapter();
        }
        else
        {
          i = 0;
          localObject1 = (MenuAdapter)localObject1;
        }
        MenuItem localMenuItem = null;
        Object localObject2 = localMenuItem;
        if (paramMotionEvent.getAction() != 10)
        {
          int j = pointToPosition((int)paramMotionEvent.getX(), (int)paramMotionEvent.getY());
          localObject2 = localMenuItem;
          if (j != -1)
          {
            i = j - i;
            localObject2 = localMenuItem;
            if (i >= 0)
            {
              localObject2 = localMenuItem;
              if (i < ((MenuAdapter)localObject1).getCount()) {
                localObject2 = ((MenuAdapter)localObject1).getItem(i);
              }
            }
          }
        }
        localMenuItem = mHoveredMenuItem;
        if (localMenuItem != localObject2)
        {
          localObject1 = ((MenuAdapter)localObject1).getAdapterMenu();
          if (localMenuItem != null) {
            mHoverListener.onItemHoverExit((MenuBuilder)localObject1, localMenuItem);
          }
          mHoveredMenuItem = ((MenuItem)localObject2);
          if (localObject2 != null) {
            mHoverListener.onItemHoverEnter((MenuBuilder)localObject1, (MenuItem)localObject2);
          }
        }
      }
      return super.onHoverEvent(paramMotionEvent);
    }
    
    public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
    {
      ListMenuItemView localListMenuItemView = (ListMenuItemView)getSelectedView();
      if ((localListMenuItemView != null) && (paramInt == mAdvanceKey))
      {
        if ((localListMenuItemView.isEnabled()) && (localListMenuItemView.getItemData().hasSubMenu()))
        {
          performItemClick(localListMenuItemView, getSelectedItemPosition(), getSelectedItemId());
          return true;
        }
      }
      else
      {
        if ((localListMenuItemView != null) && (paramInt == mRetreatKey))
        {
          setSelection(-1);
          ((MenuAdapter)getAdapter()).getAdapterMenu().close(false);
          return true;
        }
        return super.onKeyDown(paramInt, paramKeyEvent);
      }
      return true;
    }
    
    public void setHoverListener(MenuItemHoverListener paramMenuItemHoverListener)
    {
      mHoverListener = paramMenuItemHoverListener;
    }
  }
}
