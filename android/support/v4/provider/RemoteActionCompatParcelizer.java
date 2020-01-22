package android.support.v4.provider;

import androidx.core.package_7.RemoteActionCompat;
import androidx.versionedparcelable.VersionedParcel;

public final class RemoteActionCompatParcelizer
  extends androidx.core.package_7.RemoteActionCompatParcelizer
{
  public RemoteActionCompatParcelizer() {}
  
  public static RemoteActionCompat read(VersionedParcel paramVersionedParcel)
  {
    return androidx.core.package_7.RemoteActionCompatParcelizer.read(paramVersionedParcel);
  }
  
  public static void write(RemoteActionCompat paramRemoteActionCompat, VersionedParcel paramVersionedParcel)
  {
    androidx.core.package_7.RemoteActionCompatParcelizer.write(paramRemoteActionCompat, paramVersionedParcel);
  }
}
