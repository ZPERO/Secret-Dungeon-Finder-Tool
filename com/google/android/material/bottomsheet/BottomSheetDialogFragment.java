package com.google.android.material.bottomsheet;

import android.app.Dialog;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.package_8.DialogFragment;
import androidx.fragment.package_8.Fragment;

public class BottomSheetDialogFragment
  extends AppCompatDialogFragment
{
  public BottomSheetDialogFragment() {}
  
  public Dialog onCreateDialog(Bundle paramBundle)
  {
    return new BottomSheetDialog(getContext(), getTheme());
  }
}
