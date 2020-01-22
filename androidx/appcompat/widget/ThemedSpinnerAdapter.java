package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.Resources.Theme;
import android.view.LayoutInflater;
import android.widget.SpinnerAdapter;
import androidx.appcompat.view.ContextThemeWrapper;

public abstract interface ThemedSpinnerAdapter
  extends SpinnerAdapter
{
  public abstract Resources.Theme getDropDownViewTheme();
  
  public abstract void setDropDownViewTheme(Resources.Theme paramTheme);
  
  public static final class Helper
  {
    private final Context mContext;
    private LayoutInflater mDropDownInflater;
    private final LayoutInflater mInflater;
    
    public Helper(Context paramContext)
    {
      mContext = paramContext;
      mInflater = LayoutInflater.from(paramContext);
    }
    
    public LayoutInflater getDropDownViewInflater()
    {
      LayoutInflater localLayoutInflater = mDropDownInflater;
      if (localLayoutInflater != null) {
        return localLayoutInflater;
      }
      return mInflater;
    }
    
    public Resources.Theme getDropDownViewTheme()
    {
      LayoutInflater localLayoutInflater = mDropDownInflater;
      if (localLayoutInflater == null) {
        return null;
      }
      return localLayoutInflater.getContext().getTheme();
    }
    
    public void setDropDownViewTheme(Resources.Theme paramTheme)
    {
      if (paramTheme == null)
      {
        mDropDownInflater = null;
        return;
      }
      if (paramTheme == mContext.getTheme())
      {
        mDropDownInflater = mInflater;
        return;
      }
      mDropDownInflater = LayoutInflater.from(new ContextThemeWrapper(mContext, paramTheme));
    }
  }
}
