package com.google.android.android.common.images;

import android.net.Uri;
import com.google.android.android.common.internal.Objects;

final class BitmapLoader
{
  public final Uri uri;
  
  public BitmapLoader(Uri paramUri)
  {
    uri = paramUri;
  }
  
  public final boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof BitmapLoader)) {
      return false;
    }
    if (this == paramObject) {
      return true;
    }
    return Objects.equal(uri, uri);
  }
  
  public final int hashCode()
  {
    return Objects.hashCode(new Object[] { uri });
  }
}
