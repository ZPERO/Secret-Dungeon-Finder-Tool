package androidx.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import androidx.appcompat.content.wiki.AppCompatResources;
import androidx.core.content.delay.TypedArrayUtils;

public abstract class DialogPreference
  extends Preference
{
  private Drawable mDialogIcon;
  private int mDialogLayoutResId;
  private CharSequence mDialogMessage;
  private CharSequence mDialogTitle;
  private CharSequence mNegativeButtonText;
  private CharSequence mPositiveButtonText;
  
  public DialogPreference(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public DialogPreference(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, TypedArrayUtils.getAttr(paramContext, R.attr.dialogPreferenceStyle, 16842897));
  }
  
  public DialogPreference(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    this(paramContext, paramAttributeSet, paramInt, 0);
  }
  
  public DialogPreference(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    super(paramContext, paramAttributeSet, paramInt1, paramInt2);
    paramContext = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.DialogPreference, paramInt1, paramInt2);
    mDialogTitle = TypedArrayUtils.getString(paramContext, R.styleable.DialogPreference_dialogTitle, R.styleable.DialogPreference_android_dialogTitle);
    if (mDialogTitle == null) {
      mDialogTitle = getTitle();
    }
    mDialogMessage = TypedArrayUtils.getString(paramContext, R.styleable.DialogPreference_dialogMessage, R.styleable.DialogPreference_android_dialogMessage);
    mDialogIcon = TypedArrayUtils.getDrawable(paramContext, R.styleable.DialogPreference_dialogIcon, R.styleable.DialogPreference_android_dialogIcon);
    mPositiveButtonText = TypedArrayUtils.getString(paramContext, R.styleable.DialogPreference_positiveButtonText, R.styleable.DialogPreference_android_positiveButtonText);
    mNegativeButtonText = TypedArrayUtils.getString(paramContext, R.styleable.DialogPreference_negativeButtonText, R.styleable.DialogPreference_android_negativeButtonText);
    mDialogLayoutResId = TypedArrayUtils.getResourceId(paramContext, R.styleable.DialogPreference_dialogLayout, R.styleable.DialogPreference_android_dialogLayout, 0);
    paramContext.recycle();
  }
  
  public Drawable getDialogIcon()
  {
    return mDialogIcon;
  }
  
  public int getDialogLayoutResource()
  {
    return mDialogLayoutResId;
  }
  
  public CharSequence getDialogMessage()
  {
    return mDialogMessage;
  }
  
  public CharSequence getDialogTitle()
  {
    return mDialogTitle;
  }
  
  public CharSequence getNegativeButtonText()
  {
    return mNegativeButtonText;
  }
  
  public CharSequence getPositiveButtonText()
  {
    return mPositiveButtonText;
  }
  
  protected void onClick()
  {
    getPreferenceManager().showDialog(this);
  }
  
  public void setDialogIcon(int paramInt)
  {
    mDialogIcon = AppCompatResources.getDrawable(getContext(), paramInt);
  }
  
  public void setDialogIcon(Drawable paramDrawable)
  {
    mDialogIcon = paramDrawable;
  }
  
  public void setDialogLayoutResource(int paramInt)
  {
    mDialogLayoutResId = paramInt;
  }
  
  public void setDialogMessage(int paramInt)
  {
    setDialogMessage(getContext().getString(paramInt));
  }
  
  public void setDialogMessage(CharSequence paramCharSequence)
  {
    mDialogMessage = paramCharSequence;
  }
  
  public void setDialogTitle(int paramInt)
  {
    setDialogTitle(getContext().getString(paramInt));
  }
  
  public void setDialogTitle(CharSequence paramCharSequence)
  {
    mDialogTitle = paramCharSequence;
  }
  
  public void setNegativeButtonText(int paramInt)
  {
    setNegativeButtonText(getContext().getString(paramInt));
  }
  
  public void setNegativeButtonText(CharSequence paramCharSequence)
  {
    mNegativeButtonText = paramCharSequence;
  }
  
  public void setPositiveButtonText(int paramInt)
  {
    setPositiveButtonText(getContext().getString(paramInt));
  }
  
  public void setPositiveButtonText(CharSequence paramCharSequence)
  {
    mPositiveButtonText = paramCharSequence;
  }
  
  public static abstract interface TargetFragment
  {
    public abstract Preference findPreference(CharSequence paramCharSequence);
  }
}
