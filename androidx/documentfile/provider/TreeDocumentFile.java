package androidx.documentfile.provider;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.util.Log;
import java.util.ArrayList;

class TreeDocumentFile
  extends DocumentFile
{
  private Context mContext;
  private Uri mUri;
  
  TreeDocumentFile(DocumentFile paramDocumentFile, Context paramContext, Uri paramUri)
  {
    super(paramDocumentFile);
    mContext = paramContext;
    mUri = paramUri;
  }
  
  private static void closeQuietly(AutoCloseable paramAutoCloseable)
  {
    if (paramAutoCloseable != null) {
      try
      {
        paramAutoCloseable.close();
        return;
      }
      catch (RuntimeException paramAutoCloseable)
      {
        throw paramAutoCloseable;
      }
      catch (Exception paramAutoCloseable) {}
    }
  }
  
  private static Uri createFile(Context paramContext, Uri paramUri, String paramString1, String paramString2)
  {
    try
    {
      paramContext = DocumentsContract.createDocument(paramContext.getContentResolver(), paramUri, paramString1, paramString2);
      return paramContext;
    }
    catch (Exception paramContext)
    {
      for (;;) {}
    }
    return null;
  }
  
  public boolean canRead()
  {
    return DocumentsContractApi19.canRead(mContext, mUri);
  }
  
  public boolean canWrite()
  {
    return DocumentsContractApi19.canWrite(mContext, mUri);
  }
  
  public DocumentFile createDirectory(String paramString)
  {
    paramString = createFile(mContext, mUri, "vnd.android.document/directory", paramString);
    if (paramString != null) {
      return new TreeDocumentFile(this, mContext, paramString);
    }
    return null;
  }
  
  public DocumentFile createFile(String paramString1, String paramString2)
  {
    paramString1 = createFile(mContext, mUri, paramString1, paramString2);
    if (paramString1 != null) {
      return new TreeDocumentFile(this, mContext, paramString1);
    }
    return null;
  }
  
  public boolean delete()
  {
    Object localObject = mContext;
    try
    {
      localObject = ((Context)localObject).getContentResolver();
      Uri localUri = mUri;
      boolean bool = DocumentsContract.deleteDocument((ContentResolver)localObject, localUri);
      return bool;
    }
    catch (Exception localException)
    {
      for (;;) {}
    }
    return false;
  }
  
  public boolean exists()
  {
    return DocumentsContractApi19.exists(mContext, mUri);
  }
  
  public String getName()
  {
    return DocumentsContractApi19.getName(mContext, mUri);
  }
  
  public String getType()
  {
    return DocumentsContractApi19.getType(mContext, mUri);
  }
  
  public Uri getUri()
  {
    return mUri;
  }
  
  public boolean isDirectory()
  {
    return DocumentsContractApi19.isDirectory(mContext, mUri);
  }
  
  public boolean isFile()
  {
    return DocumentsContractApi19.isFile(mContext, mUri);
  }
  
  public boolean isVirtual()
  {
    return DocumentsContractApi19.isVirtual(mContext, mUri);
  }
  
  public long lastModified()
  {
    return DocumentsContractApi19.lastModified(mContext, mUri);
  }
  
  public long length()
  {
    return DocumentsContractApi19.length(mContext, mUri);
  }
  
  public DocumentFile[] listFiles()
  {
    Object localObject2 = mContext.getContentResolver();
    Object localObject1 = mUri;
    Object localObject4 = DocumentsContract.buildChildDocumentsUriUsingTree((Uri)localObject1, DocumentsContract.getDocumentId((Uri)localObject1));
    ArrayList localArrayList = new ArrayList();
    int i = 0;
    localObject1 = null;
    Object localObject3 = null;
    try
    {
      localObject4 = ((ContentResolver)localObject2).query((Uri)localObject4, new String[] { "document_id" }, null, null, null);
      localObject2 = localObject4;
      for (;;)
      {
        localObject3 = localObject2;
        localObject1 = localObject2;
        boolean bool = ((Cursor)localObject4).moveToNext();
        localObject1 = localObject2;
        if (!bool) {
          break;
        }
        localObject3 = localObject2;
        localObject1 = localObject2;
        String str = ((Cursor)localObject4).getString(0);
        Uri localUri = mUri;
        localObject3 = localObject2;
        localObject1 = localObject2;
        localArrayList.add(DocumentsContract.buildDocumentUriUsingTree(localUri, str));
      }
    }
    catch (Throwable localThrowable) {}catch (Exception localException)
    {
      for (;;)
      {
        localObject3 = localThrowable;
        localObject4 = new StringBuilder();
        localObject3 = localThrowable;
        ((StringBuilder)localObject4).append("Failed query: ");
        localObject3 = localThrowable;
        ((StringBuilder)localObject4).append(localException);
        localObject3 = localThrowable;
        Log.w("DocumentFile", ((StringBuilder)localObject4).toString());
      }
    }
    closeQuietly((AutoCloseable)localObject1);
    Uri[] arrayOfUri = (Uri[])localArrayList.toArray(new Uri[localArrayList.size()]);
    DocumentFile[] arrayOfDocumentFile = new DocumentFile[arrayOfUri.length];
    while (i < arrayOfUri.length)
    {
      arrayOfDocumentFile[i] = new TreeDocumentFile(this, mContext, arrayOfUri[i]);
      i += 1;
    }
    return arrayOfDocumentFile;
    closeQuietly((AutoCloseable)localObject3);
    throw arrayOfUri;
  }
  
  public boolean renameTo(String paramString)
  {
    Object localObject = mContext;
    try
    {
      localObject = ((Context)localObject).getContentResolver();
      Uri localUri = mUri;
      paramString = DocumentsContract.renameDocument((ContentResolver)localObject, localUri, paramString);
      if (paramString != null)
      {
        mUri = paramString;
        return true;
      }
      return false;
    }
    catch (Exception paramString) {}
    return false;
  }
}
