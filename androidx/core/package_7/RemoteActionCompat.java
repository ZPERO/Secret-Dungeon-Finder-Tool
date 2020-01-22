package androidx.core.package_7;

import android.app.PendingIntent;
import android.app.RemoteAction;
import android.os.Build.VERSION;
import androidx.core.graphics.drawable.IconCompat;
import androidx.core.util.Preconditions;
import androidx.versionedparcelable.VersionedParcelable;

public final class RemoteActionCompat
  implements VersionedParcelable
{
  public PendingIntent mActionIntent;
  public CharSequence mContentDescription;
  public boolean mEnabled;
  public IconCompat mIcon;
  public boolean mShouldShowIcon;
  public CharSequence mTitle;
  
  public RemoteActionCompat() {}
  
  public RemoteActionCompat(IconCompat paramIconCompat, CharSequence paramCharSequence1, CharSequence paramCharSequence2, PendingIntent paramPendingIntent)
  {
    mIcon = ((IconCompat)Preconditions.checkNotNull(paramIconCompat));
    mTitle = ((CharSequence)Preconditions.checkNotNull(paramCharSequence1));
    mContentDescription = ((CharSequence)Preconditions.checkNotNull(paramCharSequence2));
    mActionIntent = ((PendingIntent)Preconditions.checkNotNull(paramPendingIntent));
    mEnabled = true;
    mShouldShowIcon = true;
  }
  
  public RemoteActionCompat(RemoteActionCompat paramRemoteActionCompat)
  {
    Preconditions.checkNotNull(paramRemoteActionCompat);
    mIcon = mIcon;
    mTitle = mTitle;
    mContentDescription = mContentDescription;
    mActionIntent = mActionIntent;
    mEnabled = mEnabled;
    mShouldShowIcon = mShouldShowIcon;
  }
  
  public static RemoteActionCompat createFromRemoteAction(RemoteAction paramRemoteAction)
  {
    Preconditions.checkNotNull(paramRemoteAction);
    RemoteActionCompat localRemoteActionCompat = new RemoteActionCompat(IconCompat.createFromIcon(paramRemoteAction.getIcon()), paramRemoteAction.getTitle(), paramRemoteAction.getContentDescription(), paramRemoteAction.getActionIntent());
    localRemoteActionCompat.setEnabled(paramRemoteAction.isEnabled());
    if (Build.VERSION.SDK_INT >= 28) {
      localRemoteActionCompat.setShouldShowIcon(paramRemoteAction.shouldShowIcon());
    }
    return localRemoteActionCompat;
  }
  
  public PendingIntent getActionIntent()
  {
    return mActionIntent;
  }
  
  public CharSequence getContentDescription()
  {
    return mContentDescription;
  }
  
  public IconCompat getIcon()
  {
    return mIcon;
  }
  
  public CharSequence getTitle()
  {
    return mTitle;
  }
  
  public boolean isEnabled()
  {
    return mEnabled;
  }
  
  public void setEnabled(boolean paramBoolean)
  {
    mEnabled = paramBoolean;
  }
  
  public void setShouldShowIcon(boolean paramBoolean)
  {
    mShouldShowIcon = paramBoolean;
  }
  
  public boolean shouldShowIcon()
  {
    return mShouldShowIcon;
  }
  
  public RemoteAction toRemoteAction()
  {
    RemoteAction localRemoteAction = new RemoteAction(mIcon.toIcon(), mTitle, mContentDescription, mActionIntent);
    localRemoteAction.setEnabled(isEnabled());
    if (Build.VERSION.SDK_INT >= 28) {
      localRemoteAction.setShouldShowIcon(shouldShowIcon());
    }
    return localRemoteAction;
  }
}
