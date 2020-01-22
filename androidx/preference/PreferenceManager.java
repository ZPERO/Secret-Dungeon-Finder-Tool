package androidx.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.text.TextUtils;
import androidx.core.content.ContextCompat;

public class PreferenceManager
{
  public static final String KEY_HAS_SET_DEFAULT_VALUES = "_has_set_default_values";
  private static final int STORAGE_DEFAULT = 0;
  private static final int STORAGE_DEVICE_PROTECTED = 1;
  private Context mContext;
  private SharedPreferences.Editor mEditor;
  private long mNextId = 0L;
  private boolean mNoCommit;
  private OnDisplayPreferenceDialogListener mOnDisplayPreferenceDialogListener;
  private OnNavigateToScreenListener mOnNavigateToScreenListener;
  private OnPreferenceTreeClickListener mOnPreferenceTreeClickListener;
  private PreferenceComparisonCallback mPreferenceComparisonCallback;
  private PreferenceDataStore mPreferenceDataStore;
  private PreferenceScreen mPreferenceScreen;
  private SharedPreferences mSharedPreferences;
  private int mSharedPreferencesMode;
  private String mSharedPreferencesName;
  private int mStorage = 0;
  
  public PreferenceManager(Context paramContext)
  {
    mContext = paramContext;
    setSharedPreferencesName(getDefaultSharedPreferencesName(paramContext));
  }
  
  public static SharedPreferences getDefaultSharedPreferences(Context paramContext)
  {
    return paramContext.getSharedPreferences(getDefaultSharedPreferencesName(paramContext), getDefaultSharedPreferencesMode());
  }
  
  private static int getDefaultSharedPreferencesMode()
  {
    return 0;
  }
  
  private static String getDefaultSharedPreferencesName(Context paramContext)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramContext.getPackageName());
    localStringBuilder.append("_preferences");
    return localStringBuilder.toString();
  }
  
  public static void setDefaultValues(Context paramContext, int paramInt, boolean paramBoolean)
  {
    setDefaultValues(paramContext, getDefaultSharedPreferencesName(paramContext), getDefaultSharedPreferencesMode(), paramInt, paramBoolean);
  }
  
  public static void setDefaultValues(Context paramContext, String paramString, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    SharedPreferences localSharedPreferences = paramContext.getSharedPreferences("_has_set_default_values", 0);
    if ((paramBoolean) || (!localSharedPreferences.getBoolean("_has_set_default_values", false)))
    {
      PreferenceManager localPreferenceManager = new PreferenceManager(paramContext);
      localPreferenceManager.setSharedPreferencesName(paramString);
      localPreferenceManager.setSharedPreferencesMode(paramInt1);
      localPreferenceManager.inflateFromResource(paramContext, paramInt2, null);
      localSharedPreferences.edit().putBoolean("_has_set_default_values", true).apply();
    }
  }
  
  private void setNoCommit(boolean paramBoolean)
  {
    if (!paramBoolean)
    {
      SharedPreferences.Editor localEditor = mEditor;
      if (localEditor != null) {
        localEditor.apply();
      }
    }
    mNoCommit = paramBoolean;
  }
  
  public PreferenceScreen createPreferenceScreen(Context paramContext)
  {
    paramContext = new PreferenceScreen(paramContext, null);
    paramContext.onAttachedToHierarchy(this);
    return paramContext;
  }
  
  public Preference findPreference(CharSequence paramCharSequence)
  {
    PreferenceScreen localPreferenceScreen = mPreferenceScreen;
    if (localPreferenceScreen == null) {
      return null;
    }
    return localPreferenceScreen.findPreference(paramCharSequence);
  }
  
  public Context getContext()
  {
    return mContext;
  }
  
  SharedPreferences.Editor getEditor()
  {
    if (mPreferenceDataStore != null) {
      return null;
    }
    if (mNoCommit)
    {
      if (mEditor == null) {
        mEditor = getSharedPreferences().edit();
      }
      return mEditor;
    }
    return getSharedPreferences().edit();
  }
  
  long getNextId()
  {
    try
    {
      long l = mNextId;
      mNextId = (1L + l);
      return l;
    }
    catch (Throwable localThrowable)
    {
      throw localThrowable;
    }
  }
  
  public OnDisplayPreferenceDialogListener getOnDisplayPreferenceDialogListener()
  {
    return mOnDisplayPreferenceDialogListener;
  }
  
  public OnNavigateToScreenListener getOnNavigateToScreenListener()
  {
    return mOnNavigateToScreenListener;
  }
  
  public OnPreferenceTreeClickListener getOnPreferenceTreeClickListener()
  {
    return mOnPreferenceTreeClickListener;
  }
  
  public PreferenceComparisonCallback getPreferenceComparisonCallback()
  {
    return mPreferenceComparisonCallback;
  }
  
  public PreferenceDataStore getPreferenceDataStore()
  {
    return mPreferenceDataStore;
  }
  
  public PreferenceScreen getPreferenceScreen()
  {
    return mPreferenceScreen;
  }
  
  public SharedPreferences getSharedPreferences()
  {
    if (getPreferenceDataStore() != null) {
      return null;
    }
    if (mSharedPreferences == null)
    {
      Context localContext;
      if (mStorage != 1) {
        localContext = mContext;
      } else {
        localContext = ContextCompat.createDeviceProtectedStorageContext(mContext);
      }
      mSharedPreferences = localContext.getSharedPreferences(mSharedPreferencesName, mSharedPreferencesMode);
    }
    return mSharedPreferences;
  }
  
  public int getSharedPreferencesMode()
  {
    return mSharedPreferencesMode;
  }
  
  public String getSharedPreferencesName()
  {
    return mSharedPreferencesName;
  }
  
  public PreferenceScreen inflateFromResource(Context paramContext, int paramInt, PreferenceScreen paramPreferenceScreen)
  {
    setNoCommit(true);
    paramContext = (PreferenceScreen)new PreferenceInflater(paramContext, this).inflate(paramInt, paramPreferenceScreen);
    paramContext.onAttachedToHierarchy(this);
    setNoCommit(false);
    return paramContext;
  }
  
  public boolean isStorageDefault()
  {
    if (Build.VERSION.SDK_INT >= 24) {
      return mStorage == 0;
    }
    return true;
  }
  
  public boolean isStorageDeviceProtected()
  {
    return (Build.VERSION.SDK_INT >= 24) && (mStorage == 1);
  }
  
  public void setOnDisplayPreferenceDialogListener(OnDisplayPreferenceDialogListener paramOnDisplayPreferenceDialogListener)
  {
    mOnDisplayPreferenceDialogListener = paramOnDisplayPreferenceDialogListener;
  }
  
  public void setOnNavigateToScreenListener(OnNavigateToScreenListener paramOnNavigateToScreenListener)
  {
    mOnNavigateToScreenListener = paramOnNavigateToScreenListener;
  }
  
  public void setOnPreferenceTreeClickListener(OnPreferenceTreeClickListener paramOnPreferenceTreeClickListener)
  {
    mOnPreferenceTreeClickListener = paramOnPreferenceTreeClickListener;
  }
  
  public void setPreferenceComparisonCallback(PreferenceComparisonCallback paramPreferenceComparisonCallback)
  {
    mPreferenceComparisonCallback = paramPreferenceComparisonCallback;
  }
  
  public void setPreferenceDataStore(PreferenceDataStore paramPreferenceDataStore)
  {
    mPreferenceDataStore = paramPreferenceDataStore;
  }
  
  public boolean setPreferences(PreferenceScreen paramPreferenceScreen)
  {
    PreferenceScreen localPreferenceScreen = mPreferenceScreen;
    if (paramPreferenceScreen != localPreferenceScreen)
    {
      if (localPreferenceScreen != null) {
        localPreferenceScreen.onDetached();
      }
      mPreferenceScreen = paramPreferenceScreen;
      return true;
    }
    return false;
  }
  
  public void setSharedPreferencesMode(int paramInt)
  {
    mSharedPreferencesMode = paramInt;
    mSharedPreferences = null;
  }
  
  public void setSharedPreferencesName(String paramString)
  {
    mSharedPreferencesName = paramString;
    mSharedPreferences = null;
  }
  
  public void setStorageDefault()
  {
    if (Build.VERSION.SDK_INT >= 24)
    {
      mStorage = 0;
      mSharedPreferences = null;
    }
  }
  
  public void setStorageDeviceProtected()
  {
    if (Build.VERSION.SDK_INT >= 24)
    {
      mStorage = 1;
      mSharedPreferences = null;
    }
  }
  
  boolean shouldCommit()
  {
    return mNoCommit ^ true;
  }
  
  public void showDialog(Preference paramPreference)
  {
    OnDisplayPreferenceDialogListener localOnDisplayPreferenceDialogListener = mOnDisplayPreferenceDialogListener;
    if (localOnDisplayPreferenceDialogListener != null) {
      localOnDisplayPreferenceDialogListener.onDisplayPreferenceDialog(paramPreference);
    }
  }
  
  public static abstract interface OnDisplayPreferenceDialogListener
  {
    public abstract void onDisplayPreferenceDialog(Preference paramPreference);
  }
  
  public static abstract interface OnNavigateToScreenListener
  {
    public abstract void onNavigateToScreen(PreferenceScreen paramPreferenceScreen);
  }
  
  public static abstract interface OnPreferenceTreeClickListener
  {
    public abstract boolean onPreferenceTreeClick(Preference paramPreference);
  }
  
  public static abstract class PreferenceComparisonCallback
  {
    public PreferenceComparisonCallback() {}
    
    public abstract boolean arePreferenceContentsTheSame(Preference paramPreference1, Preference paramPreference2);
    
    public abstract boolean arePreferenceItemsTheSame(Preference paramPreference1, Preference paramPreference2);
  }
  
  public static class SimplePreferenceComparisonCallback
    extends PreferenceManager.PreferenceComparisonCallback
  {
    public SimplePreferenceComparisonCallback() {}
    
    public boolean arePreferenceContentsTheSame(Preference paramPreference1, Preference paramPreference2)
    {
      if (paramPreference1.getClass() != paramPreference2.getClass()) {
        return false;
      }
      if ((paramPreference1 == paramPreference2) && (paramPreference1.wasDetached())) {
        return false;
      }
      if (!TextUtils.equals(paramPreference1.getTitle(), paramPreference2.getTitle())) {
        return false;
      }
      if (!TextUtils.equals(paramPreference1.getSummary(), paramPreference2.getSummary())) {
        return false;
      }
      Drawable localDrawable1 = paramPreference1.getIcon();
      Drawable localDrawable2 = paramPreference2.getIcon();
      if (localDrawable1 != localDrawable2)
      {
        if (localDrawable1 == null) {
          break label159;
        }
        if (!localDrawable1.equals(localDrawable2)) {
          return false;
        }
      }
      if (paramPreference1.isEnabled() != paramPreference2.isEnabled()) {
        return false;
      }
      if (paramPreference1.isSelectable() != paramPreference2.isSelectable()) {
        return false;
      }
      if (((paramPreference1 instanceof TwoStatePreference)) && (((TwoStatePreference)paramPreference1).isChecked() != ((TwoStatePreference)paramPreference2).isChecked())) {
        return false;
      }
      return (!(paramPreference1 instanceof DropDownPreference)) || (paramPreference1 == paramPreference2);
      label159:
      return false;
    }
    
    public boolean arePreferenceItemsTheSame(Preference paramPreference1, Preference paramPreference2)
    {
      return paramPreference1.getId() == paramPreference2.getId();
    }
  }
}
