package androidx.core.package_7;

import android.app.PendingIntent;
import androidx.core.graphics.drawable.IconCompat;
import androidx.versionedparcelable.VersionedParcel;

public class RemoteActionCompatParcelizer
{
  public RemoteActionCompatParcelizer() {}
  
  public static RemoteActionCompat read(VersionedParcel paramVersionedParcel)
  {
    RemoteActionCompat localRemoteActionCompat = new RemoteActionCompat();
    mIcon = ((IconCompat)paramVersionedParcel.readVersionedParcelable(mIcon, 1));
    mTitle = paramVersionedParcel.readCharSequence(mTitle, 2);
    mContentDescription = paramVersionedParcel.readCharSequence(mContentDescription, 3);
    mActionIntent = ((PendingIntent)paramVersionedParcel.readParcelable(mActionIntent, 4));
    mEnabled = paramVersionedParcel.readBoolean(mEnabled, 5);
    mShouldShowIcon = paramVersionedParcel.readBoolean(mShouldShowIcon, 6);
    return localRemoteActionCompat;
  }
  
  public static void write(RemoteActionCompat paramRemoteActionCompat, VersionedParcel paramVersionedParcel)
  {
    paramVersionedParcel.setSerializationFlags(false, false);
    paramVersionedParcel.writeVersionedParcelable(mIcon, 1);
    paramVersionedParcel.writeCharSequence(mTitle, 2);
    paramVersionedParcel.writeCharSequence(mContentDescription, 3);
    paramVersionedParcel.writeParcelable(mActionIntent, 4);
    paramVersionedParcel.writeBoolean(mEnabled, 5);
    paramVersionedParcel.writeBoolean(mShouldShowIcon, 6);
  }
}
