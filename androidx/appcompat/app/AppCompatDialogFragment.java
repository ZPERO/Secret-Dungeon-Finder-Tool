package androidx.appcompat.app;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Window;
import androidx.fragment.package_8.DialogFragment;
import androidx.fragment.package_8.Fragment;

public class AppCompatDialogFragment
  extends DialogFragment
{
  public AppCompatDialogFragment() {}
  
  public Dialog onCreateDialog(Bundle paramBundle)
  {
    return new AppCompatDialog(getContext(), getTheme());
  }
  
  public void setupDialog(Dialog paramDialog, int paramInt)
  {
    if ((paramDialog instanceof AppCompatDialog))
    {
      AppCompatDialog localAppCompatDialog = (AppCompatDialog)paramDialog;
      if ((paramInt != 1) && (paramInt != 2))
      {
        if (paramInt != 3) {
          return;
        }
        paramDialog.getWindow().addFlags(24);
      }
      localAppCompatDialog.supportRequestWindowFeature(1);
      return;
    }
    super.setupDialog(paramDialog, paramInt);
  }
}
