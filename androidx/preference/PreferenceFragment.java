package androidx.preference;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.content.delay.TypedArrayUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver;
import androidx.recyclerview.widget.RecyclerView.ItemDecoration;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;
import androidx.recyclerview.widget.RecyclerView.State;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

@Deprecated
public abstract class PreferenceFragment
  extends Fragment
  implements PreferenceManager.OnPreferenceTreeClickListener, PreferenceManager.OnDisplayPreferenceDialogListener, PreferenceManager.OnNavigateToScreenListener, DialogPreference.TargetFragment
{
  @Deprecated
  public static final String ARG_PREFERENCE_ROOT = "androidx.preference.PreferenceFragmentCompat.PREFERENCE_ROOT";
  private static final String DIALOG_FRAGMENT_TAG = "androidx.preference.PreferenceFragment.DIALOG";
  private static final int MSG_BIND_PREFERENCES = 1;
  private static final String PREFERENCES_TAG = "android:preferences";
  private final DividerDecoration mDividerDecoration = new DividerDecoration();
  private final Handler mHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      if (what != 1) {
        return;
      }
      bindPreferences();
    }
  };
  private boolean mHavePrefs;
  private boolean mInitDone;
  private int mLayoutResId = R.layout.preference_list_fragment;
  RecyclerView mList;
  private PreferenceManager mPreferenceManager;
  private final Runnable mRequestFocus = new Runnable()
  {
    public void run()
    {
      mList.focusableViewAvailable(mList);
    }
  };
  private Runnable mSelectPreferenceRunnable;
  private Context mStyledContext;
  
  public PreferenceFragment() {}
  
  private void postBindPreferences()
  {
    if (mHandler.hasMessages(1)) {
      return;
    }
    mHandler.obtainMessage(1).sendToTarget();
  }
  
  private void requirePreferenceManager()
  {
    if (mPreferenceManager != null) {
      return;
    }
    throw new RuntimeException("This should be called after super.onCreate.");
  }
  
  private void scrollToPreferenceInternal(final Preference paramPreference, final String paramString)
  {
    paramPreference = new Runnable()
    {
      public void run()
      {
        RecyclerView.Adapter localAdapter = mList.getAdapter();
        if (!(localAdapter instanceof PreferenceGroup.PreferencePositionCallback))
        {
          if (localAdapter == null) {
            return;
          }
          throw new IllegalStateException("Adapter must implement PreferencePositionCallback");
        }
        Preference localPreference = paramPreference;
        int i;
        if (localPreference != null) {
          i = ((PreferenceGroup.PreferencePositionCallback)localAdapter).getPreferenceAdapterPosition(localPreference);
        } else {
          i = ((PreferenceGroup.PreferencePositionCallback)localAdapter).getPreferenceAdapterPosition(paramString);
        }
        if (i != -1)
        {
          mList.scrollToPosition(i);
          return;
        }
        localAdapter.registerAdapterDataObserver(new PreferenceFragment.ScrollToPreferenceObserver(localAdapter, mList, paramPreference, paramString));
      }
    };
    if (mList == null)
    {
      mSelectPreferenceRunnable = paramPreference;
      return;
    }
    paramPreference.run();
  }
  
  private void unbindPreferences()
  {
    PreferenceScreen localPreferenceScreen = getPreferenceScreen();
    if (localPreferenceScreen != null) {
      localPreferenceScreen.onDetached();
    }
    onUnbindPreferences();
  }
  
  public void addPreferencesFromResource(int paramInt)
  {
    requirePreferenceManager();
    setPreferenceScreen(mPreferenceManager.inflateFromResource(mStyledContext, paramInt, getPreferenceScreen()));
  }
  
  void bindPreferences()
  {
    PreferenceScreen localPreferenceScreen = getPreferenceScreen();
    if (localPreferenceScreen != null)
    {
      getListView().setAdapter(onCreateAdapter(localPreferenceScreen));
      localPreferenceScreen.onAttached();
    }
    onBindPreferences();
  }
  
  public Preference findPreference(CharSequence paramCharSequence)
  {
    PreferenceManager localPreferenceManager = mPreferenceManager;
    if (localPreferenceManager == null) {
      return null;
    }
    return localPreferenceManager.findPreference(paramCharSequence);
  }
  
  public Fragment getCallbackFragment()
  {
    return null;
  }
  
  public final RecyclerView getListView()
  {
    return mList;
  }
  
  public PreferenceManager getPreferenceManager()
  {
    return mPreferenceManager;
  }
  
  public PreferenceScreen getPreferenceScreen()
  {
    return mPreferenceManager.getPreferenceScreen();
  }
  
  protected void onBindPreferences() {}
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Object localObject = new TypedValue();
    getActivity().getTheme().resolveAttribute(R.attr.preferenceTheme, (TypedValue)localObject, true);
    int j = resourceId;
    int i = j;
    if (j == 0) {
      i = R.style.PreferenceThemeOverlay;
    }
    mStyledContext = new ContextThemeWrapper(getActivity(), i);
    mPreferenceManager = new PreferenceManager(mStyledContext);
    mPreferenceManager.setOnNavigateToScreenListener(this);
    if (getArguments() != null) {
      localObject = getArguments().getString("androidx.preference.PreferenceFragmentCompat.PREFERENCE_ROOT");
    } else {
      localObject = null;
    }
    onCreatePreferences(paramBundle, (String)localObject);
  }
  
  protected RecyclerView.Adapter onCreateAdapter(PreferenceScreen paramPreferenceScreen)
  {
    return new PreferenceGroupAdapter(paramPreferenceScreen);
  }
  
  public RecyclerView.LayoutManager onCreateLayoutManager()
  {
    return new LinearLayoutManager(getActivity());
  }
  
  public abstract void onCreatePreferences(Bundle paramBundle, String paramString);
  
  public RecyclerView onCreateRecyclerView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    if (mStyledContext.getPackageManager().hasSystemFeature("android.hardware.type.automotive"))
    {
      paramBundle = (RecyclerView)paramViewGroup.findViewById(R.id.recycler_view);
      if (paramBundle != null) {
        return paramBundle;
      }
    }
    paramLayoutInflater = (RecyclerView)paramLayoutInflater.inflate(R.layout.preference_recyclerview, paramViewGroup, false);
    paramLayoutInflater.setLayoutManager(onCreateLayoutManager());
    paramLayoutInflater.setAccessibilityDelegateCompat(new PreferenceRecyclerViewAccessibilityDelegate(paramLayoutInflater));
    return paramLayoutInflater;
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    Object localObject = mStyledContext.obtainStyledAttributes(null, R.styleable.PreferenceFragment, TypedArrayUtils.getAttr(mStyledContext, R.attr.preferenceFragmentStyle, 16844038), 0);
    mLayoutResId = ((TypedArray)localObject).getResourceId(R.styleable.PreferenceFragment_android_layout, mLayoutResId);
    Drawable localDrawable = ((TypedArray)localObject).getDrawable(R.styleable.PreferenceFragment_android_divider);
    int i = ((TypedArray)localObject).getDimensionPixelSize(R.styleable.PreferenceFragment_android_dividerHeight, -1);
    boolean bool = ((TypedArray)localObject).getBoolean(R.styleable.PreferenceFragment_allowDividerAfterLastItem, true);
    ((TypedArray)localObject).recycle();
    paramLayoutInflater = paramLayoutInflater.cloneInContext(mStyledContext);
    paramViewGroup = paramLayoutInflater.inflate(mLayoutResId, paramViewGroup, false);
    localObject = paramViewGroup.findViewById(16908351);
    if ((localObject instanceof ViewGroup))
    {
      localObject = (ViewGroup)localObject;
      paramLayoutInflater = onCreateRecyclerView(paramLayoutInflater, (ViewGroup)localObject, paramBundle);
      if (paramLayoutInflater != null)
      {
        mList = paramLayoutInflater;
        paramLayoutInflater.addItemDecoration(mDividerDecoration);
        setDivider(localDrawable);
        if (i != -1) {
          setDividerHeight(i);
        }
        mDividerDecoration.setAllowDividerAfterLastItem(bool);
        if (mList.getParent() == null) {
          ((ViewGroup)localObject).addView(mList);
        }
        mHandler.post(mRequestFocus);
        return paramViewGroup;
      }
      throw new RuntimeException("Could not create RecyclerView");
    }
    throw new RuntimeException("Content has view with id attribute 'android.R.id.list_container' that is not a ViewGroup class");
  }
  
  public void onDestroyView()
  {
    mHandler.removeCallbacks(mRequestFocus);
    mHandler.removeMessages(1);
    if (mHavePrefs) {
      unbindPreferences();
    }
    mList = null;
    super.onDestroyView();
  }
  
  public void onDisplayPreferenceDialog(Preference paramPreference)
  {
    boolean bool1;
    if ((getCallbackFragment() instanceof OnPreferenceDisplayDialogCallback)) {
      bool1 = ((OnPreferenceDisplayDialogCallback)getCallbackFragment()).onPreferenceDisplayDialog(this, paramPreference);
    } else {
      bool1 = false;
    }
    boolean bool2 = bool1;
    if (!bool1)
    {
      bool2 = bool1;
      if ((getActivity() instanceof OnPreferenceDisplayDialogCallback)) {
        bool2 = ((OnPreferenceDisplayDialogCallback)getActivity()).onPreferenceDisplayDialog(this, paramPreference);
      }
    }
    if (bool2) {
      return;
    }
    if (getFragmentManager().findFragmentByTag("androidx.preference.PreferenceFragment.DIALOG") != null) {
      return;
    }
    if ((paramPreference instanceof EditTextPreference))
    {
      paramPreference = EditTextPreferenceDialogFragment.newInstance(paramPreference.getKey());
    }
    else if ((paramPreference instanceof ListPreference))
    {
      paramPreference = ListPreferenceDialogFragment.newInstance(paramPreference.getKey());
    }
    else
    {
      if (!(paramPreference instanceof MultiSelectListPreference)) {
        break label149;
      }
      paramPreference = MultiSelectListPreferenceDialogFragment.newInstance(paramPreference.getKey());
    }
    paramPreference.setTargetFragment(this, 0);
    paramPreference.show(getFragmentManager(), "androidx.preference.PreferenceFragment.DIALOG");
    return;
    label149:
    throw new IllegalArgumentException("Tried to display dialog for unknown preference type. Did you forget to override onDisplayPreferenceDialog()?");
  }
  
  public void onNavigateToScreen(PreferenceScreen paramPreferenceScreen)
  {
    boolean bool;
    if ((getCallbackFragment() instanceof OnPreferenceStartScreenCallback)) {
      bool = ((OnPreferenceStartScreenCallback)getCallbackFragment()).onPreferenceStartScreen(this, paramPreferenceScreen);
    } else {
      bool = false;
    }
    if ((!bool) && ((getActivity() instanceof OnPreferenceStartScreenCallback))) {
      ((OnPreferenceStartScreenCallback)getActivity()).onPreferenceStartScreen(this, paramPreferenceScreen);
    }
  }
  
  public boolean onPreferenceTreeClick(Preference paramPreference)
  {
    String str = paramPreference.getFragment();
    boolean bool = false;
    if (str != null)
    {
      if ((getCallbackFragment() instanceof OnPreferenceStartFragmentCallback)) {
        bool = ((OnPreferenceStartFragmentCallback)getCallbackFragment()).onPreferenceStartFragment(this, paramPreference);
      }
      if ((!bool) && ((getActivity() instanceof OnPreferenceStartFragmentCallback))) {
        return ((OnPreferenceStartFragmentCallback)getActivity()).onPreferenceStartFragment(this, paramPreference);
      }
    }
    else
    {
      return false;
    }
    return bool;
  }
  
  public void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    PreferenceScreen localPreferenceScreen = getPreferenceScreen();
    if (localPreferenceScreen != null)
    {
      Bundle localBundle = new Bundle();
      localPreferenceScreen.saveHierarchyState(localBundle);
      paramBundle.putBundle("android:preferences", localBundle);
    }
  }
  
  public void onStart()
  {
    super.onStart();
    mPreferenceManager.setOnPreferenceTreeClickListener(this);
    mPreferenceManager.setOnDisplayPreferenceDialogListener(this);
  }
  
  public void onStop()
  {
    super.onStop();
    mPreferenceManager.setOnPreferenceTreeClickListener(null);
    mPreferenceManager.setOnDisplayPreferenceDialogListener(null);
  }
  
  protected void onUnbindPreferences() {}
  
  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    if (paramBundle != null)
    {
      paramView = paramBundle.getBundle("android:preferences");
      if (paramView != null)
      {
        paramBundle = getPreferenceScreen();
        if (paramBundle != null) {
          paramBundle.restoreHierarchyState(paramView);
        }
      }
    }
    if (mHavePrefs)
    {
      bindPreferences();
      paramView = mSelectPreferenceRunnable;
      if (paramView != null)
      {
        paramView.run();
        mSelectPreferenceRunnable = null;
      }
    }
    mInitDone = true;
  }
  
  public void scrollToPreference(Preference paramPreference)
  {
    scrollToPreferenceInternal(paramPreference, null);
  }
  
  public void scrollToPreference(String paramString)
  {
    scrollToPreferenceInternal(null, paramString);
  }
  
  public void setDivider(Drawable paramDrawable)
  {
    mDividerDecoration.setDivider(paramDrawable);
  }
  
  public void setDividerHeight(int paramInt)
  {
    mDividerDecoration.setDividerHeight(paramInt);
  }
  
  public void setPreferenceScreen(PreferenceScreen paramPreferenceScreen)
  {
    if ((mPreferenceManager.setPreferences(paramPreferenceScreen)) && (paramPreferenceScreen != null))
    {
      onUnbindPreferences();
      mHavePrefs = true;
      if (mInitDone) {
        postBindPreferences();
      }
    }
  }
  
  public void setPreferencesFromResource(int paramInt, String paramString)
  {
    requirePreferenceManager();
    Object localObject2 = mPreferenceManager.inflateFromResource(mStyledContext, paramInt, null);
    Object localObject1 = localObject2;
    if (paramString != null)
    {
      localObject2 = ((PreferenceGroup)localObject2).findPreference(paramString);
      localObject1 = localObject2;
      if (!(localObject2 instanceof PreferenceScreen))
      {
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("Preference object with key ");
        ((StringBuilder)localObject1).append(paramString);
        ((StringBuilder)localObject1).append(" is not a PreferenceScreen");
        throw new IllegalArgumentException(((StringBuilder)localObject1).toString());
      }
    }
    setPreferenceScreen((PreferenceScreen)localObject1);
  }
  
  private class DividerDecoration
    extends RecyclerView.ItemDecoration
  {
    private boolean mAllowDividerAfterLastItem = true;
    private Drawable mDivider;
    private int mDividerHeight;
    
    DividerDecoration() {}
    
    private boolean shouldDrawDividerBelow(View paramView, RecyclerView paramRecyclerView)
    {
      RecyclerView.ViewHolder localViewHolder = paramRecyclerView.getChildViewHolder(paramView);
      if (((localViewHolder instanceof PreferenceViewHolder)) && (((PreferenceViewHolder)localViewHolder).isDividerAllowedBelow())) {
        i = 1;
      } else {
        i = 0;
      }
      if (i == 0) {
        return false;
      }
      boolean bool = mAllowDividerAfterLastItem;
      int i = paramRecyclerView.indexOfChild(paramView);
      if (i < paramRecyclerView.getChildCount() - 1)
      {
        paramView = paramRecyclerView.getChildViewHolder(paramRecyclerView.getChildAt(i + 1));
        return ((paramView instanceof PreferenceViewHolder)) && (((PreferenceViewHolder)paramView).isDividerAllowedAbove());
      }
      return bool;
    }
    
    public void getItemOffsets(Rect paramRect, View paramView, RecyclerView paramRecyclerView, RecyclerView.State paramState)
    {
      if (shouldDrawDividerBelow(paramView, paramRecyclerView)) {
        bottom = mDividerHeight;
      }
    }
    
    public void onDrawOver(Canvas paramCanvas, RecyclerView paramRecyclerView, RecyclerView.State paramState)
    {
      if (mDivider == null) {
        return;
      }
      int j = paramRecyclerView.getChildCount();
      int k = paramRecyclerView.getWidth();
      int i = 0;
      while (i < j)
      {
        paramState = paramRecyclerView.getChildAt(i);
        if (shouldDrawDividerBelow(paramState, paramRecyclerView))
        {
          int m = (int)paramState.getY() + paramState.getHeight();
          mDivider.setBounds(0, m, k, mDividerHeight + m);
          mDivider.draw(paramCanvas);
        }
        i += 1;
      }
    }
    
    public void setAllowDividerAfterLastItem(boolean paramBoolean)
    {
      mAllowDividerAfterLastItem = paramBoolean;
    }
    
    public void setDivider(Drawable paramDrawable)
    {
      if (paramDrawable != null) {
        mDividerHeight = paramDrawable.getIntrinsicHeight();
      } else {
        mDividerHeight = 0;
      }
      mDivider = paramDrawable;
      mList.invalidateItemDecorations();
    }
    
    public void setDividerHeight(int paramInt)
    {
      mDividerHeight = paramInt;
      mList.invalidateItemDecorations();
    }
  }
  
  public static abstract interface OnPreferenceDisplayDialogCallback
  {
    public abstract boolean onPreferenceDisplayDialog(PreferenceFragment paramPreferenceFragment, Preference paramPreference);
  }
  
  public static abstract interface OnPreferenceStartFragmentCallback
  {
    public abstract boolean onPreferenceStartFragment(PreferenceFragment paramPreferenceFragment, Preference paramPreference);
  }
  
  public static abstract interface OnPreferenceStartScreenCallback
  {
    public abstract boolean onPreferenceStartScreen(PreferenceFragment paramPreferenceFragment, PreferenceScreen paramPreferenceScreen);
  }
  
  private static class ScrollToPreferenceObserver
    extends RecyclerView.AdapterDataObserver
  {
    private final RecyclerView.Adapter mAdapter;
    private final String mKey;
    private final RecyclerView mList;
    private final Preference mPreference;
    
    ScrollToPreferenceObserver(RecyclerView.Adapter paramAdapter, RecyclerView paramRecyclerView, Preference paramPreference, String paramString)
    {
      mAdapter = paramAdapter;
      mList = paramRecyclerView;
      mPreference = paramPreference;
      mKey = paramString;
    }
    
    private void scrollToPreference()
    {
      mAdapter.unregisterAdapterDataObserver(this);
      Preference localPreference = mPreference;
      int i;
      if (localPreference != null) {
        i = ((PreferenceGroup.PreferencePositionCallback)mAdapter).getPreferenceAdapterPosition(localPreference);
      } else {
        i = ((PreferenceGroup.PreferencePositionCallback)mAdapter).getPreferenceAdapterPosition(mKey);
      }
      if (i != -1) {
        mList.scrollToPosition(i);
      }
    }
    
    public void onChanged()
    {
      scrollToPreference();
    }
    
    public void onItemRangeChanged(int paramInt1, int paramInt2)
    {
      scrollToPreference();
    }
    
    public void onItemRangeChanged(int paramInt1, int paramInt2, Object paramObject)
    {
      scrollToPreference();
    }
    
    public void onItemRangeInserted(int paramInt1, int paramInt2)
    {
      scrollToPreference();
    }
    
    public void onItemRangeMoved(int paramInt1, int paramInt2, int paramInt3)
    {
      scrollToPreference();
    }
    
    public void onItemRangeRemoved(int paramInt1, int paramInt2)
    {
      scrollToPreference();
    }
  }
}
