package androidx.preference;

import android.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

@Deprecated
public class EditTextPreferenceDialogFragment
  extends PreferenceDialogFragment
{
  private static final String SAVE_STATE_TEXT = "EditTextPreferenceDialogFragment.text";
  private EditText mEditText;
  private CharSequence mText;
  
  public EditTextPreferenceDialogFragment() {}
  
  private EditTextPreference getEditTextPreference()
  {
    return (EditTextPreference)getPreference();
  }
  
  public static EditTextPreferenceDialogFragment newInstance(String paramString)
  {
    EditTextPreferenceDialogFragment localEditTextPreferenceDialogFragment = new EditTextPreferenceDialogFragment();
    Bundle localBundle = new Bundle(1);
    localBundle.putString("key", paramString);
    localEditTextPreferenceDialogFragment.setArguments(localBundle);
    return localEditTextPreferenceDialogFragment;
  }
  
  protected boolean needInputMethod()
  {
    return true;
  }
  
  protected void onBindDialogView(View paramView)
  {
    super.onBindDialogView(paramView);
    mEditText = ((EditText)paramView.findViewById(16908291));
    mEditText.requestFocus();
    paramView = mEditText;
    if (paramView != null)
    {
      paramView.setText(mText);
      paramView = mEditText;
      paramView.setSelection(paramView.getText().length());
      return;
    }
    throw new IllegalStateException("Dialog view must contain an EditText with id @android:id/edit");
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
      if (getEditTextPreference().callChangeListener(str)) {
        getEditTextPreference().setText(str);
      }
    }
  }
  
  public void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putCharSequence("EditTextPreferenceDialogFragment.text", mText);
  }
}