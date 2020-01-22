package androidx.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.AbsSavedState;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.AbsSeekBar;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

public class SeekBarPreference
  extends Preference
{
  private static final String LOG_TAG = "SeekBarPreference";
  boolean mAdjustable;
  private int mMax;
  int mMin;
  SeekBar mSeekBar;
  private SeekBar.OnSeekBarChangeListener mSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener()
  {
    public void onProgressChanged(SeekBar paramAnonymousSeekBar, int paramAnonymousInt, boolean paramAnonymousBoolean)
    {
      if ((paramAnonymousBoolean) && ((mUpdatesContinuously) || (!mTrackingTouch)))
      {
        syncValueInternal(paramAnonymousSeekBar);
        return;
      }
      paramAnonymousSeekBar = SeekBarPreference.this;
      paramAnonymousSeekBar.updateLabelValue(paramAnonymousInt + mMin);
    }
    
    public void onStartTrackingTouch(SeekBar paramAnonymousSeekBar)
    {
      mTrackingTouch = true;
    }
    
    public void onStopTrackingTouch(SeekBar paramAnonymousSeekBar)
    {
      mTrackingTouch = false;
      if (paramAnonymousSeekBar.getProgress() + mMin != mSeekBarValue) {
        syncValueInternal(paramAnonymousSeekBar);
      }
    }
  };
  private int mSeekBarIncrement;
  private View.OnKeyListener mSeekBarKeyListener = new View.OnKeyListener()
  {
    public boolean onKey(View paramAnonymousView, int paramAnonymousInt, KeyEvent paramAnonymousKeyEvent)
    {
      if (paramAnonymousKeyEvent.getAction() != 0) {
        return false;
      }
      if (!mAdjustable)
      {
        if (paramAnonymousInt == 21) {
          break label80;
        }
        if (paramAnonymousInt == 22) {
          return false;
        }
      }
      if (paramAnonymousInt != 23)
      {
        if (paramAnonymousInt == 66) {
          return false;
        }
        if (mSeekBar == null)
        {
          Log.e("SeekBarPreference", "SeekBar view is null and hence cannot be adjusted.");
          return false;
        }
        return mSeekBar.onKeyDown(paramAnonymousInt, paramAnonymousKeyEvent);
      }
      label80:
      return false;
    }
  };
  int mSeekBarValue;
  private TextView mSeekBarValueTextView;
  private boolean mShowSeekBarValue;
  boolean mTrackingTouch;
  boolean mUpdatesContinuously;
  
  public SeekBarPreference(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public SeekBarPreference(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, R.attr.seekBarPreferenceStyle);
  }
  
  public SeekBarPreference(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    this(paramContext, paramAttributeSet, paramInt, 0);
  }
  
  public SeekBarPreference(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    super(paramContext, paramAttributeSet, paramInt1, paramInt2);
    paramContext = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.SeekBarPreference, paramInt1, paramInt2);
    mMin = paramContext.getInt(R.styleable.SeekBarPreference_min, 0);
    setMax(paramContext.getInt(R.styleable.SeekBarPreference_android_max, 100));
    setSeekBarIncrement(paramContext.getInt(R.styleable.SeekBarPreference_seekBarIncrement, 0));
    mAdjustable = paramContext.getBoolean(R.styleable.SeekBarPreference_adjustable, true);
    mShowSeekBarValue = paramContext.getBoolean(R.styleable.SeekBarPreference_showSeekBarValue, false);
    mUpdatesContinuously = paramContext.getBoolean(R.styleable.SeekBarPreference_updatesContinuously, false);
    paramContext.recycle();
  }
  
  private void setValueInternal(int paramInt, boolean paramBoolean)
  {
    int j = mMin;
    int i = paramInt;
    if (paramInt < j) {
      i = j;
    }
    j = mMax;
    paramInt = i;
    if (i > j) {
      paramInt = j;
    }
    if (paramInt != mSeekBarValue)
    {
      mSeekBarValue = paramInt;
      updateLabelValue(mSeekBarValue);
      persistInt(paramInt);
      if (paramBoolean) {
        notifyChanged();
      }
    }
  }
  
  public int getMax()
  {
    return mMax;
  }
  
  public int getMin()
  {
    return mMin;
  }
  
  public final int getSeekBarIncrement()
  {
    return mSeekBarIncrement;
  }
  
  public boolean getShowSeekBarValue()
  {
    return mShowSeekBarValue;
  }
  
  public boolean getUpdatesContinuously()
  {
    return mUpdatesContinuously;
  }
  
  public int getValue()
  {
    return mSeekBarValue;
  }
  
  public boolean isAdjustable()
  {
    return mAdjustable;
  }
  
  public void onBindViewHolder(PreferenceViewHolder paramPreferenceViewHolder)
  {
    super.onBindViewHolder(paramPreferenceViewHolder);
    itemView.setOnKeyListener(mSeekBarKeyListener);
    mSeekBar = ((SeekBar)paramPreferenceViewHolder.findViewById(R.id.seekbar));
    mSeekBarValueTextView = ((TextView)paramPreferenceViewHolder.findViewById(R.id.seekbar_value));
    if (mShowSeekBarValue)
    {
      mSeekBarValueTextView.setVisibility(0);
    }
    else
    {
      mSeekBarValueTextView.setVisibility(8);
      mSeekBarValueTextView = null;
    }
    paramPreferenceViewHolder = mSeekBar;
    if (paramPreferenceViewHolder == null)
    {
      Log.e("SeekBarPreference", "SeekBar view is null in onBindViewHolder.");
      return;
    }
    paramPreferenceViewHolder.setOnSeekBarChangeListener(mSeekBarChangeListener);
    mSeekBar.setMax(mMax - mMin);
    int i = mSeekBarIncrement;
    if (i != 0) {
      mSeekBar.setKeyProgressIncrement(i);
    } else {
      mSeekBarIncrement = mSeekBar.getKeyProgressIncrement();
    }
    mSeekBar.setProgress(mSeekBarValue - mMin);
    updateLabelValue(mSeekBarValue);
    mSeekBar.setEnabled(isEnabled());
  }
  
  protected Object onGetDefaultValue(TypedArray paramTypedArray, int paramInt)
  {
    return Integer.valueOf(paramTypedArray.getInt(paramInt, 0));
  }
  
  protected void onRestoreInstanceState(Parcelable paramParcelable)
  {
    if (!paramParcelable.getClass().equals(SavedState.class))
    {
      super.onRestoreInstanceState(paramParcelable);
      return;
    }
    paramParcelable = (SavedState)paramParcelable;
    super.onRestoreInstanceState(paramParcelable.getSuperState());
    mSeekBarValue = mSeekBarValue;
    mMin = mMin;
    mMax = mMax;
    notifyChanged();
  }
  
  protected Parcelable onSaveInstanceState()
  {
    Object localObject = super.onSaveInstanceState();
    if (isPersistent()) {
      return localObject;
    }
    localObject = new SavedState((Parcelable)localObject);
    mSeekBarValue = mSeekBarValue;
    mMin = mMin;
    mMax = mMax;
    return localObject;
  }
  
  protected void onSetInitialValue(Object paramObject)
  {
    Object localObject = paramObject;
    if (paramObject == null) {
      localObject = Integer.valueOf(0);
    }
    setValue(getPersistedInt(((Integer)localObject).intValue()));
  }
  
  public void setAdjustable(boolean paramBoolean)
  {
    mAdjustable = paramBoolean;
  }
  
  public final void setMax(int paramInt)
  {
    int j = mMin;
    int i = paramInt;
    if (paramInt < j) {
      i = j;
    }
    if (i != mMax)
    {
      mMax = i;
      notifyChanged();
    }
  }
  
  public void setMin(int paramInt)
  {
    int j = mMax;
    int i = paramInt;
    if (paramInt > j) {
      i = j;
    }
    if (i != mMin)
    {
      mMin = i;
      notifyChanged();
    }
  }
  
  public final void setSeekBarIncrement(int paramInt)
  {
    if (paramInt != mSeekBarIncrement)
    {
      mSeekBarIncrement = Math.min(mMax - mMin, Math.abs(paramInt));
      notifyChanged();
    }
  }
  
  public void setShowSeekBarValue(boolean paramBoolean)
  {
    mShowSeekBarValue = paramBoolean;
    notifyChanged();
  }
  
  public void setUpdatesContinuously(boolean paramBoolean)
  {
    mUpdatesContinuously = paramBoolean;
  }
  
  public void setValue(int paramInt)
  {
    setValueInternal(paramInt, true);
  }
  
  void syncValueInternal(SeekBar paramSeekBar)
  {
    int i = mMin + paramSeekBar.getProgress();
    if (i != mSeekBarValue)
    {
      if (callChangeListener(Integer.valueOf(i)))
      {
        setValueInternal(i, false);
        return;
      }
      paramSeekBar.setProgress(mSeekBarValue - mMin);
      updateLabelValue(mSeekBarValue);
    }
  }
  
  void updateLabelValue(int paramInt)
  {
    TextView localTextView = mSeekBarValueTextView;
    if (localTextView != null) {
      localTextView.setText(String.valueOf(paramInt));
    }
  }
  
  private static class SavedState
    extends Preference.BaseSavedState
  {
    public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator()
    {
      public SeekBarPreference.SavedState createFromParcel(Parcel paramAnonymousParcel)
      {
        return new SeekBarPreference.SavedState(paramAnonymousParcel);
      }
      
      public SeekBarPreference.SavedState[] newArray(int paramAnonymousInt)
      {
        return new SeekBarPreference.SavedState[paramAnonymousInt];
      }
    };
    int mMax;
    int mMin;
    int mSeekBarValue;
    
    SavedState(Parcel paramParcel)
    {
      super();
      mSeekBarValue = paramParcel.readInt();
      mMin = paramParcel.readInt();
      mMax = paramParcel.readInt();
    }
    
    SavedState(Parcelable paramParcelable)
    {
      super();
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      super.writeToParcel(paramParcel, paramInt);
      paramParcel.writeInt(mSeekBarValue);
      paramParcel.writeInt(mMin);
      paramParcel.writeInt(mMax);
    }
  }
}
