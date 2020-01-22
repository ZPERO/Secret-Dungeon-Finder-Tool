package androidx.core.content.flattr;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutInfo.Builder;
import android.graphics.drawable.Drawable;
import android.os.PersistableBundle;
import android.text.TextUtils;
import androidx.core.graphics.drawable.IconCompat;
import androidx.core.package_7.Person;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ShortcutInfoCompat
{
  private static final String EXTRA_LONG_LIVED = "extraLongLived";
  private static final String EXTRA_PERSON_ = "extraPerson_";
  private static final String EXTRA_PERSON_COUNT = "extraPersonCount";
  ComponentName mActivity;
  Set<String> mCategories;
  Context mContext;
  CharSequence mDisabledMessage;
  IconCompat mIcon;
  String mId;
  Intent[] mIntents;
  boolean mIsAlwaysBadged;
  boolean mIsLongLived;
  CharSequence mLabel;
  CharSequence mLongLabel;
  Person[] mPersons;
  
  ShortcutInfoCompat() {}
  
  private PersistableBundle buildExtrasBundle()
  {
    PersistableBundle localPersistableBundle = new PersistableBundle();
    Object localObject = mPersons;
    if ((localObject != null) && (localObject.length > 0))
    {
      localPersistableBundle.putInt("extraPersonCount", localObject.length);
      int j;
      for (int i = 0; i < mPersons.length; i = j)
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("extraPerson_");
        j = i + 1;
        ((StringBuilder)localObject).append(j);
        localPersistableBundle.putPersistableBundle(((StringBuilder)localObject).toString(), mPersons[i].toPersistableBundle());
      }
    }
    localPersistableBundle.putBoolean("extraLongLived", mIsLongLived);
    return localPersistableBundle;
  }
  
  static boolean getLongLivedFromExtra(PersistableBundle paramPersistableBundle)
  {
    if ((paramPersistableBundle != null) && (paramPersistableBundle.containsKey("extraLongLived"))) {
      return paramPersistableBundle.getBoolean("extraLongLived");
    }
    return false;
  }
  
  static Person[] getPersonsFromExtra(PersistableBundle paramPersistableBundle)
  {
    if ((paramPersistableBundle != null) && (paramPersistableBundle.containsKey("extraPersonCount")))
    {
      int k = paramPersistableBundle.getInt("extraPersonCount");
      Person[] arrayOfPerson = new Person[k];
      int j;
      for (int i = 0; i < k; i = j)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("extraPerson_");
        j = i + 1;
        localStringBuilder.append(j);
        arrayOfPerson[i] = Person.fromPersistableBundle(paramPersistableBundle.getPersistableBundle(localStringBuilder.toString()));
      }
      return arrayOfPerson;
    }
    return null;
  }
  
  Intent addToIntent(Intent paramIntent)
  {
    Object localObject1 = mIntents;
    paramIntent.putExtra("android.intent.extra.shortcut.INTENT", localObject1[(localObject1.length - 1)]).putExtra("android.intent.extra.shortcut.NAME", mLabel.toString());
    Object localObject4;
    if (mIcon != null)
    {
      Object localObject3 = null;
      localObject4 = null;
      PackageManager localPackageManager;
      if (mIsAlwaysBadged)
      {
        localPackageManager = mContext.getPackageManager();
        localObject3 = mActivity;
        localObject1 = localObject4;
        if (localObject3 == null) {}
      }
      try
      {
        localObject1 = localPackageManager.getActivityIcon((ComponentName)localObject3);
      }
      catch (PackageManager.NameNotFoundException localNameNotFoundException)
      {
        for (;;)
        {
          Object localObject2 = localObject4;
        }
      }
      localObject3 = localObject1;
      if (localObject1 == null) {
        localObject3 = mContext.getApplicationInfo().loadIcon(localPackageManager);
      }
      mIcon.addToShortcutIntent(paramIntent, (Drawable)localObject3, mContext);
      return paramIntent;
    }
    return paramIntent;
  }
  
  public ComponentName getActivity()
  {
    return mActivity;
  }
  
  public Set getCategories()
  {
    return mCategories;
  }
  
  public CharSequence getDisabledMessage()
  {
    return mDisabledMessage;
  }
  
  public IconCompat getIcon()
  {
    return mIcon;
  }
  
  public String getId()
  {
    return mId;
  }
  
  public Intent getIntent()
  {
    Intent[] arrayOfIntent = mIntents;
    return arrayOfIntent[(arrayOfIntent.length - 1)];
  }
  
  public Intent[] getIntents()
  {
    Intent[] arrayOfIntent = mIntents;
    return (Intent[])Arrays.copyOf(arrayOfIntent, arrayOfIntent.length);
  }
  
  public CharSequence getLongLabel()
  {
    return mLongLabel;
  }
  
  public CharSequence getShortLabel()
  {
    return mLabel;
  }
  
  public ShortcutInfo toShortcutInfo()
  {
    ShortcutInfo.Builder localBuilder = new ShortcutInfo.Builder(mContext, mId).setShortLabel(mLabel).setIntents(mIntents);
    Object localObject = mIcon;
    if (localObject != null) {
      localBuilder.setIcon(((IconCompat)localObject).toIcon());
    }
    if (!TextUtils.isEmpty(mLongLabel)) {
      localBuilder.setLongLabel(mLongLabel);
    }
    if (!TextUtils.isEmpty(mDisabledMessage)) {
      localBuilder.setDisabledMessage(mDisabledMessage);
    }
    localObject = mActivity;
    if (localObject != null) {
      localBuilder.setActivity((ComponentName)localObject);
    }
    localObject = mCategories;
    if (localObject != null) {
      localBuilder.setCategories((Set)localObject);
    }
    localBuilder.setExtras(buildExtrasBundle());
    return localBuilder.build();
  }
  
  public class Builder
  {
    private final ShortcutInfoCompat mInfo = new ShortcutInfoCompat();
    
    public Builder(ShortcutInfo paramShortcutInfo)
    {
      ShortcutInfoCompat localShortcutInfoCompat = mInfo;
      mContext = this$1;
      mId = paramShortcutInfo.getId();
      this$1 = paramShortcutInfo.getIntents();
      mInfo.mIntents = ((Intent[])Arrays.copyOf(this$1, this$1.length));
      mInfo.mActivity = paramShortcutInfo.getActivity();
      mInfo.mLabel = paramShortcutInfo.getShortLabel();
      mInfo.mLongLabel = paramShortcutInfo.getLongLabel();
      mInfo.mDisabledMessage = paramShortcutInfo.getDisabledMessage();
      mInfo.mCategories = paramShortcutInfo.getCategories();
      mInfo.mPersons = ShortcutInfoCompat.getPersonsFromExtra(paramShortcutInfo.getExtras());
    }
    
    public Builder(String paramString)
    {
      ShortcutInfoCompat localShortcutInfoCompat = mInfo;
      mContext = this$1;
      mId = paramString;
    }
    
    public Builder()
    {
      mInfo.mContext = mContext;
      mInfo.mId = mId;
      mInfo.mIntents = ((Intent[])Arrays.copyOf(mIntents, mIntents.length));
      mInfo.mActivity = mActivity;
      mInfo.mLabel = mLabel;
      mInfo.mLongLabel = mLongLabel;
      mInfo.mDisabledMessage = mDisabledMessage;
      mInfo.mIcon = mIcon;
      mInfo.mIsAlwaysBadged = mIsAlwaysBadged;
      mInfo.mIsLongLived = mIsLongLived;
      if (mPersons != null) {
        mInfo.mPersons = ((Person[])Arrays.copyOf(mPersons, mPersons.length));
      }
      if (mCategories != null) {
        mInfo.mCategories = new HashSet(mCategories);
      }
    }
    
    public ShortcutInfoCompat build()
    {
      if (!TextUtils.isEmpty(mInfo.mLabel))
      {
        if ((mInfo.mIntents != null) && (mInfo.mIntents.length != 0)) {
          return mInfo;
        }
        throw new IllegalArgumentException("Shortcut must have an intent");
      }
      throw new IllegalArgumentException("Shortcut must have a non-empty label");
    }
    
    public Builder setActivity(ComponentName paramComponentName)
    {
      mInfo.mActivity = paramComponentName;
      return this;
    }
    
    public Builder setAlwaysBadged()
    {
      mInfo.mIsAlwaysBadged = true;
      return this;
    }
    
    public Builder setCategories(Set paramSet)
    {
      mInfo.mCategories = paramSet;
      return this;
    }
    
    public Builder setDisabledMessage(CharSequence paramCharSequence)
    {
      mInfo.mDisabledMessage = paramCharSequence;
      return this;
    }
    
    public Builder setIcon(IconCompat paramIconCompat)
    {
      mInfo.mIcon = paramIconCompat;
      return this;
    }
    
    public Builder setIntent(Intent paramIntent)
    {
      return setIntents(new Intent[] { paramIntent });
    }
    
    public Builder setIntents(Intent[] paramArrayOfIntent)
    {
      mInfo.mIntents = paramArrayOfIntent;
      return this;
    }
    
    public Builder setLongLabel(CharSequence paramCharSequence)
    {
      mInfo.mLongLabel = paramCharSequence;
      return this;
    }
    
    public Builder setLongLived()
    {
      mInfo.mIsLongLived = true;
      return this;
    }
    
    public Builder setPerson(Person paramPerson)
    {
      return setPersons(new Person[] { paramPerson });
    }
    
    public Builder setPersons(Person[] paramArrayOfPerson)
    {
      mInfo.mPersons = paramArrayOfPerson;
      return this;
    }
    
    public Builder setShortLabel(CharSequence paramCharSequence)
    {
      mInfo.mLabel = paramCharSequence;
      return this;
    }
  }
}
