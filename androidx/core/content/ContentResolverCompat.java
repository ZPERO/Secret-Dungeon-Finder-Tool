package androidx.core.content;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build.VERSION;

public final class ContentResolverCompat
{
  private ContentResolverCompat() {}
  
  public static Cursor query(ContentResolver paramContentResolver, Uri paramUri, String[] paramArrayOfString1, String paramString1, String[] paramArrayOfString2, String paramString2, androidx.core.os.CancellationSignal paramCancellationSignal)
  {
    if (Build.VERSION.SDK_INT >= 16)
    {
      if (paramCancellationSignal != null) {
        try
        {
          paramCancellationSignal = paramCancellationSignal.getCancellationSignalObject();
        }
        catch (Exception paramContentResolver)
        {
          break label56;
        }
      } else {
        paramCancellationSignal = null;
      }
      paramCancellationSignal = (android.os.CancellationSignal)paramCancellationSignal;
      paramContentResolver = paramContentResolver.query(paramUri, paramArrayOfString1, paramString1, paramArrayOfString2, paramString2, paramCancellationSignal);
      return paramContentResolver;
      label56:
      if ((paramContentResolver instanceof android.os.OperationCanceledException)) {
        throw new androidx.core.os.OperationCanceledException();
      }
      throw paramContentResolver;
    }
    if (paramCancellationSignal != null) {
      paramCancellationSignal.throwIfCanceled();
    }
    return paramContentResolver.query(paramUri, paramArrayOfString1, paramString1, paramArrayOfString2, paramString2);
  }
}
