package androidx.preference;

import android.util.SparseArray;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

public class PreferenceViewHolder
  extends RecyclerView.ViewHolder
{
  private final SparseArray<View> mCachedViews = new SparseArray(4);
  private boolean mDividerAllowedAbove;
  private boolean mDividerAllowedBelow;
  
  PreferenceViewHolder(View paramView)
  {
    super(paramView);
    mCachedViews.put(16908310, paramView.findViewById(16908310));
    mCachedViews.put(16908304, paramView.findViewById(16908304));
    mCachedViews.put(16908294, paramView.findViewById(16908294));
    mCachedViews.put(R.id.icon_frame, paramView.findViewById(R.id.icon_frame));
    mCachedViews.put(16908350, paramView.findViewById(16908350));
  }
  
  public static PreferenceViewHolder createInstanceForTests(View paramView)
  {
    return new PreferenceViewHolder(paramView);
  }
  
  public View findViewById(int paramInt)
  {
    View localView = (View)mCachedViews.get(paramInt);
    if (localView != null) {
      return localView;
    }
    localView = itemView.findViewById(paramInt);
    if (localView != null) {
      mCachedViews.put(paramInt, localView);
    }
    return localView;
  }
  
  public boolean isDividerAllowedAbove()
  {
    return mDividerAllowedAbove;
  }
  
  public boolean isDividerAllowedBelow()
  {
    return mDividerAllowedBelow;
  }
  
  public void setDividerAllowedAbove(boolean paramBoolean)
  {
    mDividerAllowedAbove = paramBoolean;
  }
  
  public void setDividerAllowedBelow(boolean paramBoolean)
  {
    mDividerAllowedBelow = paramBoolean;
  }
}
