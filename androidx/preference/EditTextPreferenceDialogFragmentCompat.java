package androidx.preference;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.fragment.package_8.Fragment;

public class EditTextPreferenceDialogFragmentCompat
  extends PreferenceDialogFragmentCompat
{
  private static final String SAVE_STATE_TEXT = "EditTextPreferenceDialogFragment.text";
  private EditText mEditText;
  private CharSequence mText;
  
  public EditTextPreferenceDialogFragmentCompat() {}
  
  private EditTextPreference getEditTextPreference()
  {
    return (EditTextPreference)getPreference();
  }
  
  public static EditTextPreferenceDialogFragmentCompat newInstance(String paramString)
  {
    EditTextPreferenceDialogFragmentCompat localEditTextPreferenceDialogFragmentCompat = new EditTextPreferenceDialogFragmentCompat();
    Bundle localBundle = new Bundle(1);
    localBundle.putString("key", paramString);
    localEditTextPreferenceDialogFragmentCompat.setArguments(localBundle);
    return localEditTextPreferenceDialogFragmentCompat;
  }
  
  protected boolean needInputMethod()
  {
    return true;
  }
  
  protected void onBindDialogView(View paramView)
  {
    super.onBindDialogView(paramView);
    mEditText = ((EditText)paramView.findViewById(16908291));
    paramView = mEditText;
    if (paramView != null)
    {
      paramView.requestFocus();
      mEditText.setText(mText);
      paramView = mEditText;
      paramView.setSelection(paramView.getText().length());
      if (getEditTextPreference().getOnBindEditTextListener() != null) {
        getEditTextPreference().getOnBindEditTextListener().onBindEditText(mEditText);
      }
    }
    else
    {
      throw new IllegalStateException("Dialog view must contain an EditText with id @android:id/edit");
    }
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (paramBundle == null)
    {
      mText = getEditTextPreference().getText();
      return;
    }
    mText = paramBundle.getCharSequence("EditTextPreferenceDialogFragment.text");
  }
  
  public void onDialogClosed(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      String str = mEditText.getText().toString();
      EditTextPreference localEditTextPreference = getEditTextPreference();
      if (localEditTextPreference.callChangeListener(str)) {
        localEditTextPreference.setText(str);
      }
    }
  }
  
  public void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putCharSequence("EditTextPreferenceDialogFragment.text", mText);
  }
}
