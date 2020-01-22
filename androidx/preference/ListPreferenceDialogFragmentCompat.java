package androidx.preference;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog.Builder;
import androidx.fragment.package_8.Fragment;

public class ListPreferenceDialogFragmentCompat
  extends PreferenceDialogFragmentCompat
{
  private static final String SAVE_STATE_ENTRIES = "ListPreferenceDialogFragment.entries";
  private static final String SAVE_STATE_ENTRY_VALUES = "ListPreferenceDialogFragment.entryValues";
  private static final String SAVE_STATE_INDEX = "ListPreferenceDialogFragment.index";
  int mClickedDialogEntryIndex;
  private CharSequence[] mEntries;
  private CharSequence[] mEntryValues;
  
  public ListPreferenceDialogFragmentCompat() {}
  
  private ListPreference getListPreference()
  {
    return (ListPreference)getPreference();
  }
  
  public static ListPreferenceDialogFragmentCompat newInstance(String paramString)
  {
    ListPreferenceDialogFragmentCompat localListPreferenceDialogFragmentCompat = new ListPreferenceDialogFragmentCompat();
    Bundle localBundle = new Bundle(1);
    localBundle.putString("key", paramString);
    localListPreferenceDialogFragmentCompat.setArguments(localBundle);
    return localListPreferenceDialogFragmentCompat;
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (paramBundle == null)
    {
      paramBundle = getListPreference();
      if ((paramBundle.getEntries() != null) && (paramBundle.getEntryValues() != null))
      {
        mClickedDialogEntryIndex = paramBundle.findIndexOfValue(paramBundle.getValue());
        mEntries = paramBundle.getEntries();
        mEntryValues = paramBundle.getEntryValues();
        return;
      }
      throw new IllegalStateException("ListPreference requires an entries array and an entryValues array.");
    }
    mClickedDialogEntryIndex = paramBundle.getInt("ListPreferenceDialogFragment.index", 0);
    mEntries = paramBundle.getCharSequenceArray("ListPreferenceDialogFragment.entries");
    mEntryValues = paramBundle.getCharSequenceArray("ListPreferenceDialogFragment.entryValues");
  }
  
  public void onDialogClosed(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      int i = mClickedDialogEntryIndex;
      if (i >= 0)
      {
        String str = mEntryValues[i].toString();
        ListPreference localListPreference = getListPreference();
        if (localListPreference.callChangeListener(str)) {
          localListPreference.setValue(str);
        }
      }
    }
  }
  
  protected void onPrepareDialogBuilder(AlertDialog.Builder paramBuilder)
  {
    super.onPrepareDialogBuilder(paramBuilder);
    paramBuilder.setSingleChoiceItems(mEntries, mClickedDialogEntryIndex, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        ListPreferenceDialogFragmentCompat localListPreferenceDialogFragmentCompat = ListPreferenceDialogFragmentCompat.this;
        mClickedDialogEntryIndex = paramAnonymousInt;
        localListPreferenceDialogFragmentCompat.onClick(paramAnonymousDialogInterface, -1);
        paramAnonymousDialogInterface.dismiss();
      }
    });
    paramBuilder.setPositiveButton(null, null);
  }
  
  public void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putInt("ListPreferenceDialogFragment.index", mClickedDialogEntryIndex);
    paramBundle.putCharSequenceArray("ListPreferenceDialogFragment.entries", mEntries);
    paramBundle.putCharSequenceArray("ListPreferenceDialogFragment.entryValues", mEntryValues);
  }
}