package androidx.appcompat.app;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnKeyListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import androidx.appcompat.R.attr;

public class AlertDialog
  extends AppCompatDialog
  implements DialogInterface
{
  static final int LAYOUT_HINT_NONE = 0;
  static final int LAYOUT_HINT_SIDE = 1;
  final AlertController mAlert = new AlertController(getContext(), this, getWindow());
  
  protected AlertDialog(Context paramContext)
  {
    this(paramContext, 0);
  }
  
  protected AlertDialog(Context paramContext, int paramInt)
  {
    super(paramContext, resolveDialogTheme(paramContext, paramInt));
  }
  
  protected AlertDialog(Context paramContext, boolean paramBoolean, DialogInterface.OnCancelListener paramOnCancelListener)
  {
    this(paramContext, 0);
    setCancelable(paramBoolean);
    setOnCancelListener(paramOnCancelListener);
  }
  
  static int resolveDialogTheme(Context paramContext, int paramInt)
  {
    if ((paramInt >>> 24 & 0xFF) >= 1) {
      return paramInt;
    }
    TypedValue localTypedValue = new TypedValue();
    paramContext.getTheme().resolveAttribute(R.attr.alertDialogTheme, localTypedValue, true);
    return resourceId;
  }
  
  public Button getButton(int paramInt)
  {
    return mAlert.getButton(paramInt);
  }
  
  public ListView getListView()
  {
    return mAlert.getListView();
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    mAlert.installContent();
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if (mAlert.onKeyDown(paramInt, paramKeyEvent)) {
      return true;
    }
    return super.onKeyDown(paramInt, paramKeyEvent);
  }
  
  public boolean onKeyUp(int paramInt, KeyEvent paramKeyEvent)
  {
    if (mAlert.onKeyUp(paramInt, paramKeyEvent)) {
      return true;
    }
    return super.onKeyUp(paramInt, paramKeyEvent);
  }
  
  public void setButton(int paramInt, CharSequence paramCharSequence, DialogInterface.OnClickListener paramOnClickListener)
  {
    mAlert.setButton(paramInt, paramCharSequence, paramOnClickListener, null, null);
  }
  
  public void setButton(int paramInt, CharSequence paramCharSequence, Drawable paramDrawable, DialogInterface.OnClickListener paramOnClickListener)
  {
    mAlert.setButton(paramInt, paramCharSequence, paramOnClickListener, null, paramDrawable);
  }
  
  public void setButton(int paramInt, CharSequence paramCharSequence, Message paramMessage)
  {
    mAlert.setButton(paramInt, paramCharSequence, null, paramMessage, null);
  }
  
  void setButtonPanelLayoutHint(int paramInt)
  {
    mAlert.setButtonPanelLayoutHint(paramInt);
  }
  
  public void setCustomTitle(View paramView)
  {
    mAlert.setCustomTitle(paramView);
  }
  
  public void setIcon(int paramInt)
  {
    mAlert.setIcon(paramInt);
  }
  
  public void setIcon(Drawable paramDrawable)
  {
    mAlert.setIcon(paramDrawable);
  }
  
  public void setIconAttribute(int paramInt)
  {
    TypedValue localTypedValue = new TypedValue();
    getContext().getTheme().resolveAttribute(paramInt, localTypedValue, true);
    mAlert.setIcon(resourceId);
  }
  
  public void setMessage(CharSequence paramCharSequence)
  {
    mAlert.setMessage(paramCharSequence);
  }
  
  public void setTitle(CharSequence paramCharSequence)
  {
    super.setTitle(paramCharSequence);
    mAlert.setTitle(paramCharSequence);
  }
  
  public void setView(View paramView)
  {
    mAlert.setView(paramView);
  }
  
  public void setView(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    mAlert.setView(paramView, paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public static class Builder
  {
    private final AlertController.AlertParams P;
    private final int mTheme;
    
    public Builder(Context paramContext)
    {
      this(paramContext, AlertDialog.resolveDialogTheme(paramContext, 0));
    }
    
    public Builder(Context paramContext, int paramInt)
    {
      P = new AlertController.AlertParams(new ContextThemeWrapper(paramContext, AlertDialog.resolveDialogTheme(paramContext, paramInt)));
      mTheme = paramInt;
    }
    
    public AlertDialog create()
    {
      AlertDialog localAlertDialog = new AlertDialog(P.mContext, mTheme);
      P.apply(mAlert);
      localAlertDialog.setCancelable(P.mCancelable);
      if (P.mCancelable) {
        localAlertDialog.setCanceledOnTouchOutside(true);
      }
      localAlertDialog.setOnCancelListener(P.mOnCancelListener);
      localAlertDialog.setOnDismissListener(P.mOnDismissListener);
      if (P.mOnKeyListener != null) {
        localAlertDialog.setOnKeyListener(P.mOnKeyListener);
      }
      return localAlertDialog;
    }
    
    public Context getContext()
    {
      return P.mContext;
    }
    
    public Builder setAdapter(ListAdapter paramListAdapter, DialogInterface.OnClickListener paramOnClickListener)
    {
      AlertController.AlertParams localAlertParams = P;
      mAdapter = paramListAdapter;
      mOnClickListener = paramOnClickListener;
      return this;
    }
    
    public Builder setCancelable(boolean paramBoolean)
    {
      P.mCancelable = paramBoolean;
      return this;
    }
    
    public Builder setCursor(Cursor paramCursor, DialogInterface.OnClickListener paramOnClickListener, String paramString)
    {
      AlertController.AlertParams localAlertParams = P;
      mCursor = paramCursor;
      mLabelColumn = paramString;
      mOnClickListener = paramOnClickListener;
      return this;
    }
    
    public Builder setCustomTitle(View paramView)
    {
      P.mCustomTitleView = paramView;
      return this;
    }
    
    public Builder setIcon(int paramInt)
    {
      P.mIconId = paramInt;
      return this;
    }
    
    public Builder setIcon(Drawable paramDrawable)
    {
      P.mIcon = paramDrawable;
      return this;
    }
    
    public Builder setIconAttribute(int paramInt)
    {
      TypedValue localTypedValue = new TypedValue();
      P.mContext.getTheme().resolveAttribute(paramInt, localTypedValue, true);
      P.mIconId = resourceId;
      return this;
    }
    
    public Builder setInverseBackgroundForced(boolean paramBoolean)
    {
      P.mForceInverseBackground = paramBoolean;
      return this;
    }
    
    public Builder setItems(int paramInt, DialogInterface.OnClickListener paramOnClickListener)
    {
      AlertController.AlertParams localAlertParams = P;
      mItems = mContext.getResources().getTextArray(paramInt);
      P.mOnClickListener = paramOnClickListener;
      return this;
    }
    
    public Builder setItems(CharSequence[] paramArrayOfCharSequence, DialogInterface.OnClickListener paramOnClickListener)
    {
      AlertController.AlertParams localAlertParams = P;
      mItems = paramArrayOfCharSequence;
      mOnClickListener = paramOnClickListener;
      return this;
    }
    
    public Builder setMessage(int paramInt)
    {
      AlertController.AlertParams localAlertParams = P;
      mMessage = mContext.getText(paramInt);
      return this;
    }
    
    public Builder setMessage(CharSequence paramCharSequence)
    {
      P.mMessage = paramCharSequence;
      return this;
    }
    
    public Builder setMultiChoiceItems(int paramInt, boolean[] paramArrayOfBoolean, DialogInterface.OnMultiChoiceClickListener paramOnMultiChoiceClickListener)
    {
      AlertController.AlertParams localAlertParams = P;
      mItems = mContext.getResources().getTextArray(paramInt);
      localAlertParams = P;
      mOnCheckboxClickListener = paramOnMultiChoiceClickListener;
      mCheckedItems = paramArrayOfBoolean;
      mIsMultiChoice = true;
      return this;
    }
    
    public Builder setMultiChoiceItems(Cursor paramCursor, String paramString1, String paramString2, DialogInterface.OnMultiChoiceClickListener paramOnMultiChoiceClickListener)
    {
      AlertController.AlertParams localAlertParams = P;
      mCursor = paramCursor;
      mOnCheckboxClickListener = paramOnMultiChoiceClickListener;
      mIsCheckedColumn = paramString1;
      mLabelColumn = paramString2;
      mIsMultiChoice = true;
      return this;
    }
    
    public Builder setMultiChoiceItems(CharSequence[] paramArrayOfCharSequence, boolean[] paramArrayOfBoolean, DialogInterface.OnMultiChoiceClickListener paramOnMultiChoiceClickListener)
    {
      AlertController.AlertParams localAlertParams = P;
      mItems = paramArrayOfCharSequence;
      mOnCheckboxClickListener = paramOnMultiChoiceClickListener;
      mCheckedItems = paramArrayOfBoolean;
      mIsMultiChoice = true;
      return this;
    }
    
    public Builder setNegativeButton(int paramInt, DialogInterface.OnClickListener paramOnClickListener)
    {
      AlertController.AlertParams localAlertParams = P;
      mNegativeButtonText = mContext.getText(paramInt);
      P.mNegativeButtonListener = paramOnClickListener;
      return this;
    }
    
    public Builder setNegativeButton(CharSequence paramCharSequence, DialogInterface.OnClickListener paramOnClickListener)
    {
      AlertController.AlertParams localAlertParams = P;
      mNegativeButtonText = paramCharSequence;
      mNegativeButtonListener = paramOnClickListener;
      return this;
    }
    
    public Builder setNegativeButtonIcon(Drawable paramDrawable)
    {
      P.mNegativeButtonIcon = paramDrawable;
      return this;
    }
    
    public Builder setNeutralButton(int paramInt, DialogInterface.OnClickListener paramOnClickListener)
    {
      AlertController.AlertParams localAlertParams = P;
      mNeutralButtonText = mContext.getText(paramInt);
      P.mNeutralButtonListener = paramOnClickListener;
      return this;
    }
    
    public Builder setNeutralButton(CharSequence paramCharSequence, DialogInterface.OnClickListener paramOnClickListener)
    {
      AlertController.AlertParams localAlertParams = P;
      mNeutralButtonText = paramCharSequence;
      mNeutralButtonListener = paramOnClickListener;
      return this;
    }
    
    public Builder setNeutralButtonIcon(Drawable paramDrawable)
    {
      P.mNeutralButtonIcon = paramDrawable;
      return this;
    }
    
    public Builder setOnCancelListener(DialogInterface.OnCancelListener paramOnCancelListener)
    {
      P.mOnCancelListener = paramOnCancelListener;
      return this;
    }
    
    public Builder setOnDismissListener(DialogInterface.OnDismissListener paramOnDismissListener)
    {
      P.mOnDismissListener = paramOnDismissListener;
      return this;
    }
    
    public Builder setOnItemSelectedListener(AdapterView.OnItemSelectedListener paramOnItemSelectedListener)
    {
      P.mOnItemSelectedListener = paramOnItemSelectedListener;
      return this;
    }
    
    public Builder setOnKeyListener(DialogInterface.OnKeyListener paramOnKeyListener)
    {
      P.mOnKeyListener = paramOnKeyListener;
      return this;
    }
    
    public Builder setPositiveButton(int paramInt, DialogInterface.OnClickListener paramOnClickListener)
    {
      AlertController.AlertParams localAlertParams = P;
      mPositiveButtonText = mContext.getText(paramInt);
      P.mPositiveButtonListener = paramOnClickListener;
      return this;
    }
    
    public Builder setPositiveButton(CharSequence paramCharSequence, DialogInterface.OnClickListener paramOnClickListener)
    {
      AlertController.AlertParams localAlertParams = P;
      mPositiveButtonText = paramCharSequence;
      mPositiveButtonListener = paramOnClickListener;
      return this;
    }
    
    public Builder setPositiveButtonIcon(Drawable paramDrawable)
    {
      P.mPositiveButtonIcon = paramDrawable;
      return this;
    }
    
    public Builder setRecycleOnMeasureEnabled(boolean paramBoolean)
    {
      P.mRecycleOnMeasure = paramBoolean;
      return this;
    }
    
    public Builder setSingleChoiceItems(int paramInt1, int paramInt2, DialogInterface.OnClickListener paramOnClickListener)
    {
      AlertController.AlertParams localAlertParams = P;
      mItems = mContext.getResources().getTextArray(paramInt1);
      localAlertParams = P;
      mOnClickListener = paramOnClickListener;
      mCheckedItem = paramInt2;
      mIsSingleChoice = true;
      return this;
    }
    
    public Builder setSingleChoiceItems(Cursor paramCursor, int paramInt, String paramString, DialogInterface.OnClickListener paramOnClickListener)
    {
      AlertController.AlertParams localAlertParams = P;
      mCursor = paramCursor;
      mOnClickListener = paramOnClickListener;
      mCheckedItem = paramInt;
      mLabelColumn = paramString;
      mIsSingleChoice = true;
      return this;
    }
    
    public Builder setSingleChoiceItems(ListAdapter paramListAdapter, int paramInt, DialogInterface.OnClickListener paramOnClickListener)
    {
      AlertController.AlertParams localAlertParams = P;
      mAdapter = paramListAdapter;
      mOnClickListener = paramOnClickListener;
      mCheckedItem = paramInt;
      mIsSingleChoice = true;
      return this;
    }
    
    public Builder setSingleChoiceItems(CharSequence[] paramArrayOfCharSequence, int paramInt, DialogInterface.OnClickListener paramOnClickListener)
    {
      AlertController.AlertParams localAlertParams = P;
      mItems = paramArrayOfCharSequence;
      mOnClickListener = paramOnClickListener;
      mCheckedItem = paramInt;
      mIsSingleChoice = true;
      return this;
    }
    
    public Builder setTitle(int paramInt)
    {
      AlertController.AlertParams localAlertParams = P;
      mTitle = mContext.getText(paramInt);
      return this;
    }
    
    public Builder setTitle(CharSequence paramCharSequence)
    {
      P.mTitle = paramCharSequence;
      return this;
    }
    
    public Builder setView(int paramInt)
    {
      AlertController.AlertParams localAlertParams = P;
      mView = null;
      mViewLayoutResId = paramInt;
      mViewSpacingSpecified = false;
      return this;
    }
    
    public Builder setView(View paramView)
    {
      AlertController.AlertParams localAlertParams = P;
      mView = paramView;
      mViewLayoutResId = 0;
      mViewSpacingSpecified = false;
      return this;
    }
    
    public Builder setView(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      AlertController.AlertParams localAlertParams = P;
      mView = paramView;
      mViewLayoutResId = 0;
      mViewSpacingSpecified = true;
      mViewSpacingLeft = paramInt1;
      mViewSpacingTop = paramInt2;
      mViewSpacingRight = paramInt3;
      mViewSpacingBottom = paramInt4;
      return this;
    }
    
    public AlertDialog show()
    {
      AlertDialog localAlertDialog = create();
      localAlertDialog.show();
      return localAlertDialog;
    }
  }
}
