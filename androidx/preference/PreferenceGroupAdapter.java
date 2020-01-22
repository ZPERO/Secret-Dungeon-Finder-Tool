package androidx.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.content.wiki.AppCompatResources;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.DiffUtil.Callback;
import androidx.recyclerview.widget.DiffUtil.DiffResult;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PreferenceGroupAdapter
  extends RecyclerView.Adapter<PreferenceViewHolder>
  implements Preference.OnPreferenceChangeInternalListener, PreferenceGroup.PreferencePositionCallback
{
  private Handler mHandler;
  private PreferenceGroup mPreferenceGroup;
  private List<PreferenceResourceDescriptor> mPreferenceResourceDescriptors;
  private List<Preference> mPreferences;
  private Runnable mSyncRunnable = new Runnable()
  {
    public void run()
    {
      updatePreferences();
    }
  };
  private List<Preference> mVisiblePreferences;
  
  public PreferenceGroupAdapter(PreferenceGroup paramPreferenceGroup)
  {
    mPreferenceGroup = paramPreferenceGroup;
    mHandler = new Handler();
    mPreferenceGroup.setOnPreferenceChangeInternalListener(this);
    mPreferences = new ArrayList();
    mVisiblePreferences = new ArrayList();
    mPreferenceResourceDescriptors = new ArrayList();
    paramPreferenceGroup = mPreferenceGroup;
    if ((paramPreferenceGroup instanceof PreferenceScreen)) {
      setHasStableIds(((PreferenceScreen)paramPreferenceGroup).shouldUseGeneratedIds());
    } else {
      setHasStableIds(true);
    }
    updatePreferences();
  }
  
  private ExpandButton createExpandButton(final PreferenceGroup paramPreferenceGroup, List paramList)
  {
    paramList = new ExpandButton(paramPreferenceGroup.getContext(), paramList, paramPreferenceGroup.getId());
    paramList.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
    {
      public boolean onPreferenceClick(Preference paramAnonymousPreference)
      {
        paramPreferenceGroup.setInitialExpandedChildrenCount(Integer.MAX_VALUE);
        onPreferenceHierarchyChange(paramAnonymousPreference);
        paramAnonymousPreference = paramPreferenceGroup.getOnExpandButtonClickListener();
        if (paramAnonymousPreference != null) {
          paramAnonymousPreference.onExpandButtonClick();
        }
        return true;
      }
    });
    return paramList;
  }
  
  private List createVisiblePreferencesList(PreferenceGroup paramPreferenceGroup)
  {
    ArrayList localArrayList1 = new ArrayList();
    ArrayList localArrayList2 = new ArrayList();
    int m = paramPreferenceGroup.getPreferenceCount();
    int k = 0;
    int i = 0;
    while (k < m)
    {
      Object localObject = paramPreferenceGroup.getPreference(k);
      if (((Preference)localObject).isVisible())
      {
        if ((isGroupExpandable(paramPreferenceGroup)) && (i >= paramPreferenceGroup.getInitialExpandedChildrenCount())) {
          localArrayList2.add(localObject);
        } else {
          localArrayList1.add(localObject);
        }
        if (!(localObject instanceof PreferenceGroup))
        {
          i += 1;
        }
        else
        {
          localObject = (PreferenceGroup)localObject;
          if (((PreferenceGroup)localObject).isOnSameScreenAsChildren())
          {
            if ((isGroupExpandable(paramPreferenceGroup)) && (isGroupExpandable((PreferenceGroup)localObject))) {
              throw new IllegalStateException("Nesting an expandable group inside of another expandable group is not supported!");
            }
            localObject = createVisiblePreferencesList((PreferenceGroup)localObject).iterator();
            int j = i;
            for (;;)
            {
              i = j;
              if (!((Iterator)localObject).hasNext()) {
                break;
              }
              Preference localPreference = (Preference)((Iterator)localObject).next();
              if ((isGroupExpandable(paramPreferenceGroup)) && (j >= paramPreferenceGroup.getInitialExpandedChildrenCount())) {
                localArrayList2.add(localPreference);
              } else {
                localArrayList1.add(localPreference);
              }
              j += 1;
            }
          }
        }
      }
      k += 1;
    }
    if ((isGroupExpandable(paramPreferenceGroup)) && (i > paramPreferenceGroup.getInitialExpandedChildrenCount())) {
      localArrayList1.add(createExpandButton(paramPreferenceGroup, localArrayList2));
    }
    return localArrayList1;
  }
  
  private void flattenPreferenceGroup(List paramList, PreferenceGroup paramPreferenceGroup)
  {
    paramPreferenceGroup.sortPreferences();
    int j = paramPreferenceGroup.getPreferenceCount();
    int i = 0;
    while (i < j)
    {
      Preference localPreference = paramPreferenceGroup.getPreference(i);
      paramList.add(localPreference);
      Object localObject = new PreferenceResourceDescriptor(localPreference);
      if (!mPreferenceResourceDescriptors.contains(localObject)) {
        mPreferenceResourceDescriptors.add(localObject);
      }
      if ((localPreference instanceof PreferenceGroup))
      {
        localObject = (PreferenceGroup)localPreference;
        if (((PreferenceGroup)localObject).isOnSameScreenAsChildren()) {
          flattenPreferenceGroup(paramList, (PreferenceGroup)localObject);
        }
      }
      localPreference.setOnPreferenceChangeInternalListener(this);
      i += 1;
    }
  }
  
  private boolean isGroupExpandable(PreferenceGroup paramPreferenceGroup)
  {
    return paramPreferenceGroup.getInitialExpandedChildrenCount() != Integer.MAX_VALUE;
  }
  
  public Preference getItem(int paramInt)
  {
    if ((paramInt >= 0) && (paramInt < getItemCount())) {
      return (Preference)mVisiblePreferences.get(paramInt);
    }
    return null;
  }
  
  public int getItemCount()
  {
    return mVisiblePreferences.size();
  }
  
  public long getItemId(int paramInt)
  {
    if (!hasStableIds()) {
      return -1L;
    }
    return getItem(paramInt).getId();
  }
  
  public int getItemViewType(int paramInt)
  {
    PreferenceResourceDescriptor localPreferenceResourceDescriptor = new PreferenceResourceDescriptor(getItem(paramInt));
    paramInt = mPreferenceResourceDescriptors.indexOf(localPreferenceResourceDescriptor);
    if (paramInt != -1) {
      return paramInt;
    }
    paramInt = mPreferenceResourceDescriptors.size();
    mPreferenceResourceDescriptors.add(localPreferenceResourceDescriptor);
    return paramInt;
  }
  
  public int getPreferenceAdapterPosition(Preference paramPreference)
  {
    int j = mVisiblePreferences.size();
    int i = 0;
    while (i < j)
    {
      Preference localPreference = (Preference)mVisiblePreferences.get(i);
      if ((localPreference != null) && (localPreference.equals(paramPreference))) {
        return i;
      }
      i += 1;
    }
    return -1;
  }
  
  public int getPreferenceAdapterPosition(String paramString)
  {
    int j = mVisiblePreferences.size();
    int i = 0;
    while (i < j)
    {
      if (TextUtils.equals(paramString, ((Preference)mVisiblePreferences.get(i)).getKey())) {
        return i;
      }
      i += 1;
    }
    return -1;
  }
  
  public void onBindViewHolder(PreferenceViewHolder paramPreferenceViewHolder, int paramInt)
  {
    getItem(paramInt).onBindViewHolder(paramPreferenceViewHolder);
  }
  
  public PreferenceViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt)
  {
    PreferenceResourceDescriptor localPreferenceResourceDescriptor = (PreferenceResourceDescriptor)mPreferenceResourceDescriptors.get(paramInt);
    LayoutInflater localLayoutInflater = LayoutInflater.from(paramViewGroup.getContext());
    TypedArray localTypedArray = paramViewGroup.getContext().obtainStyledAttributes(null, R.styleable.BackgroundStyle);
    Drawable localDrawable = localTypedArray.getDrawable(R.styleable.BackgroundStyle_android_selectableItemBackground);
    Object localObject = localDrawable;
    if (localDrawable == null) {
      localObject = AppCompatResources.getDrawable(paramViewGroup.getContext(), 17301602);
    }
    localTypedArray.recycle();
    paramViewGroup = localLayoutInflater.inflate(mLayoutResId, paramViewGroup, false);
    if (paramViewGroup.getBackground() == null) {
      ViewCompat.setBackground(paramViewGroup, (Drawable)localObject);
    }
    localObject = (ViewGroup)paramViewGroup.findViewById(16908312);
    if (localObject != null) {
      if (mWidgetLayoutResId != 0) {
        localLayoutInflater.inflate(mWidgetLayoutResId, (ViewGroup)localObject);
      } else {
        ((View)localObject).setVisibility(8);
      }
    }
    return new PreferenceViewHolder(paramViewGroup);
  }
  
  public void onPreferenceChange(Preference paramPreference)
  {
    int i = mVisiblePreferences.indexOf(paramPreference);
    if (i != -1) {
      notifyItemChanged(i, paramPreference);
    }
  }
  
  public void onPreferenceHierarchyChange(Preference paramPreference)
  {
    mHandler.removeCallbacks(mSyncRunnable);
    mHandler.post(mSyncRunnable);
  }
  
  public void onPreferenceVisibilityChange(Preference paramPreference)
  {
    onPreferenceHierarchyChange(paramPreference);
  }
  
  void updatePreferences()
  {
    Object localObject = mPreferences.iterator();
    while (((Iterator)localObject).hasNext()) {
      ((Preference)((Iterator)localObject).next()).setOnPreferenceChangeInternalListener(null);
    }
    mPreferences = new ArrayList(mPreferences.size());
    flattenPreferenceGroup(mPreferences, mPreferenceGroup);
    localObject = mVisiblePreferences;
    final List localList = createVisiblePreferencesList(mPreferenceGroup);
    mVisiblePreferences = localList;
    PreferenceManager localPreferenceManager = mPreferenceGroup.getPreferenceManager();
    if ((localPreferenceManager != null) && (localPreferenceManager.getPreferenceComparisonCallback() != null)) {
      DiffUtil.calculateDiff(new DiffUtil.Callback()
      {
        public boolean areContentsTheSame(int paramAnonymousInt1, int paramAnonymousInt2)
        {
          return val$comparisonCallback.arePreferenceContentsTheSame((Preference)val$oldVisibleList.get(paramAnonymousInt1), (Preference)localList.get(paramAnonymousInt2));
        }
        
        public boolean areItemsTheSame(int paramAnonymousInt1, int paramAnonymousInt2)
        {
          return val$comparisonCallback.arePreferenceItemsTheSame((Preference)val$oldVisibleList.get(paramAnonymousInt1), (Preference)localList.get(paramAnonymousInt2));
        }
        
        public int getNewListSize()
        {
          return localList.size();
        }
        
        public int getOldListSize()
        {
          return val$oldVisibleList.size();
        }
      }).dispatchUpdatesTo(this);
    } else {
      notifyDataSetChanged();
    }
    localObject = mPreferences.iterator();
    while (((Iterator)localObject).hasNext()) {
      ((Preference)((Iterator)localObject).next()).clearWasDetached();
    }
  }
  
  private static class PreferenceResourceDescriptor
  {
    String mClassName;
    int mLayoutResId;
    int mWidgetLayoutResId;
    
    PreferenceResourceDescriptor(Preference paramPreference)
    {
      mClassName = paramPreference.getClass().getName();
      mLayoutResId = paramPreference.getLayoutResource();
      mWidgetLayoutResId = paramPreference.getWidgetLayoutResource();
    }
    
    public boolean equals(Object paramObject)
    {
      if (!(paramObject instanceof PreferenceResourceDescriptor)) {
        return false;
      }
      paramObject = (PreferenceResourceDescriptor)paramObject;
      return (mLayoutResId == mLayoutResId) && (mWidgetLayoutResId == mWidgetLayoutResId) && (TextUtils.equals(mClassName, mClassName));
    }
    
    public int hashCode()
    {
      return ((527 + mLayoutResId) * 31 + mWidgetLayoutResId) * 31 + mClassName.hashCode();
    }
  }
}
