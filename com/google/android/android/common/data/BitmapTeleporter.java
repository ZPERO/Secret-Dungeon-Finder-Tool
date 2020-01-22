package com.google.android.android.common.data;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.ParcelFileDescriptor.AutoCloseInputStream;
import android.os.Parcelable.Creator;
import android.util.Log;
import com.google.android.android.common.internal.ReflectedParcelable;
import com.google.android.android.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.android.common.internal.safeparcel.SafeParcelWriter;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;

public class BitmapTeleporter
  extends AbstractSafeParcelable
  implements ReflectedParcelable
{
  public static final Parcelable.Creator<com.google.android.gms.common.data.BitmapTeleporter> CREATOR = new DiscreteSeekBar.CustomState.1();
  private final int mType;
  private final int zale;
  private ParcelFileDescriptor zalf;
  private Bitmap zalg;
  private boolean zalh;
  private File zali;
  
  BitmapTeleporter(int paramInt1, ParcelFileDescriptor paramParcelFileDescriptor, int paramInt2)
  {
    zale = paramInt1;
    zalf = paramParcelFileDescriptor;
    mType = paramInt2;
    zalg = null;
    zalh = false;
  }
  
  public BitmapTeleporter(Bitmap paramBitmap)
  {
    zale = 1;
    zalf = null;
    mType = 0;
    zalg = paramBitmap;
    zalh = true;
  }
  
  private static void closeQuietly(Closeable paramCloseable)
  {
    try
    {
      paramCloseable.close();
      return;
    }
    catch (IOException paramCloseable)
    {
      Log.w("BitmapTeleporter", "Could not close stream", paramCloseable);
    }
  }
  
  /* Error */
  private final java.io.FileOutputStream zabz()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 68	com/google/android/android/common/data/BitmapTeleporter:zali	Ljava/io/File;
    //   4: astore_1
    //   5: aload_1
    //   6: ifnull +62 -> 68
    //   9: ldc 70
    //   11: ldc 72
    //   13: aload_1
    //   14: invokestatic 78	java/io/File:createTempFile	(Ljava/lang/String;Ljava/lang/String;Ljava/io/File;)Ljava/io/File;
    //   17: astore_1
    //   18: new 80	java/io/FileOutputStream
    //   21: dup
    //   22: aload_1
    //   23: invokespecial 83	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   26: astore_2
    //   27: aload_1
    //   28: ldc 84
    //   30: invokestatic 90	android/os/ParcelFileDescriptor:open	(Ljava/io/File;I)Landroid/os/ParcelFileDescriptor;
    //   33: astore_3
    //   34: aload_0
    //   35: aload_3
    //   36: putfield 36	com/google/android/android/common/data/BitmapTeleporter:zalf	Landroid/os/ParcelFileDescriptor;
    //   39: aload_1
    //   40: invokevirtual 94	java/io/File:delete	()Z
    //   43: pop
    //   44: aload_2
    //   45: areturn
    //   46: new 96	java/lang/IllegalStateException
    //   49: dup
    //   50: ldc 98
    //   52: invokespecial 101	java/lang/IllegalStateException:<init>	(Ljava/lang/String;)V
    //   55: athrow
    //   56: astore_1
    //   57: new 96	java/lang/IllegalStateException
    //   60: dup
    //   61: ldc 103
    //   63: aload_1
    //   64: invokespecial 106	java/lang/IllegalStateException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   67: athrow
    //   68: new 96	java/lang/IllegalStateException
    //   71: dup
    //   72: ldc 108
    //   74: invokespecial 101	java/lang/IllegalStateException:<init>	(Ljava/lang/String;)V
    //   77: athrow
    //   78: astore_1
    //   79: goto -33 -> 46
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	82	0	this	BitmapTeleporter
    //   4	36	1	localFile	File
    //   56	8	1	localIOException	IOException
    //   78	1	1	localFileNotFoundException	java.io.FileNotFoundException
    //   26	19	2	localFileOutputStream	java.io.FileOutputStream
    //   33	3	3	localParcelFileDescriptor	ParcelFileDescriptor
    // Exception table:
    //   from	to	target	type
    //   9	18	56	java/io/IOException
    //   18	34	78	java/io/FileNotFoundException
  }
  
  public Bitmap get()
  {
    if (!zalh)
    {
      Object localObject1 = new DataInputStream(new ParcelFileDescriptor.AutoCloseInputStream(zalf));
      try
      {
        int i = ((DataInputStream)localObject1).readInt();
        byte[] arrayOfByte = new byte[i];
        i = ((DataInputStream)localObject1).readInt();
        int j = ((DataInputStream)localObject1).readInt();
        Object localObject2 = Bitmap.Config.valueOf(((DataInputStream)localObject1).readUTF());
        ((DataInputStream)localObject1).read(arrayOfByte);
        closeQuietly((Closeable)localObject1);
        localObject1 = ByteBuffer.wrap(arrayOfByte);
        localObject2 = Bitmap.createBitmap(i, j, (Bitmap.Config)localObject2);
        ((Bitmap)localObject2).copyPixelsFromBuffer((Buffer)localObject1);
        zalg = ((Bitmap)localObject2);
        zalh = true;
      }
      catch (Throwable localThrowable) {}catch (IOException localIOException)
      {
        throw new IllegalStateException("Could not read from parcel file descriptor", localIOException);
      }
      closeQuietly((Closeable)localObject1);
      throw localIOException;
    }
    return zalg;
  }
  
  public void release()
  {
    if (!zalh)
    {
      ParcelFileDescriptor localParcelFileDescriptor = zalf;
      try
      {
        localParcelFileDescriptor.close();
        return;
      }
      catch (IOException localIOException)
      {
        Log.w("BitmapTeleporter", "Could not close PFD", localIOException);
      }
    }
  }
  
  public void setTempDir(File paramFile)
  {
    if (paramFile != null)
    {
      zali = paramFile;
      return;
    }
    throw new NullPointerException("Cannot set null temp directory");
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    if (zalf == null)
    {
      Bitmap localBitmap = zalg;
      Object localObject = ByteBuffer.allocate(localBitmap.getRowBytes() * localBitmap.getHeight());
      localBitmap.copyPixelsToBuffer((Buffer)localObject);
      byte[] arrayOfByte = ((ByteBuffer)localObject).array();
      localObject = new DataOutputStream(new BufferedOutputStream(zabz()));
      i = arrayOfByte.length;
      try
      {
        ((DataOutputStream)localObject).writeInt(i);
        ((DataOutputStream)localObject).writeInt(localBitmap.getWidth());
        ((DataOutputStream)localObject).writeInt(localBitmap.getHeight());
        ((DataOutputStream)localObject).writeUTF(localBitmap.getConfig().toString());
        ((DataOutputStream)localObject).write(arrayOfByte);
        closeQuietly((Closeable)localObject);
      }
      catch (Throwable paramParcel) {}catch (IOException paramParcel)
      {
        throw new IllegalStateException("Could not write into unlinked file", paramParcel);
      }
      closeQuietly((Closeable)localObject);
      throw paramParcel;
    }
    int i = SafeParcelWriter.beginObjectHeader(paramParcel);
    SafeParcelWriter.writeInt(paramParcel, 1, zale);
    SafeParcelWriter.writeParcelable(paramParcel, 2, zalf, paramInt | 0x1, false);
    SafeParcelWriter.writeInt(paramParcel, 3, mType);
    SafeParcelWriter.finishObjectHeader(paramParcel, i);
    zalf = null;
  }
}
