package androidx.core.view.inputmethod;

import android.content.ClipDescription;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import android.view.inputmethod.InputContentInfo;

public final class InputConnectionCompat
{
  private static final String COMMIT_CONTENT_ACTION = "androidx.core.view.inputmethod.InputConnectionCompat.COMMIT_CONTENT";
  private static final String COMMIT_CONTENT_CONTENT_URI_INTEROP_KEY = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_URI";
  private static final String COMMIT_CONTENT_CONTENT_URI_KEY = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_URI";
  private static final String COMMIT_CONTENT_DESCRIPTION_INTEROP_KEY = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_DESCRIPTION";
  private static final String COMMIT_CONTENT_DESCRIPTION_KEY = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_DESCRIPTION";
  private static final String COMMIT_CONTENT_FLAGS_INTEROP_KEY = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_FLAGS";
  private static final String COMMIT_CONTENT_FLAGS_KEY = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_FLAGS";
  private static final String COMMIT_CONTENT_INTEROP_ACTION = "androidx.core.view.inputmethod.InputConnectionCompat.COMMIT_CONTENT";
  private static final String COMMIT_CONTENT_LINK_URI_INTEROP_KEY = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_LINK_URI";
  private static final String COMMIT_CONTENT_LINK_URI_KEY = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_LINK_URI";
  private static final String COMMIT_CONTENT_OPTS_INTEROP_KEY = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_OPTS";
  private static final String COMMIT_CONTENT_OPTS_KEY = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_OPTS";
  private static final String COMMIT_CONTENT_RESULT_INTEROP_RECEIVER_KEY = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_RESULT_RECEIVER";
  private static final String COMMIT_CONTENT_RESULT_RECEIVER_KEY = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_RESULT_RECEIVER";
  public static final int INPUT_CONTENT_GRANT_READ_URI_PERMISSION = 1;
  
  public InputConnectionCompat() {}
  
  public static boolean commitContent(InputConnection paramInputConnection, EditorInfo paramEditorInfo, InputContentInfoCompat paramInputContentInfoCompat, int paramInt, Bundle paramBundle)
  {
    ClipDescription localClipDescription = paramInputContentInfoCompat.getDescription();
    String[] arrayOfString = EditorInfoCompat.getContentMimeTypes(paramEditorInfo);
    int j = arrayOfString.length;
    int i = 0;
    while (i < j)
    {
      if (localClipDescription.hasMimeType(arrayOfString[i]))
      {
        i = 1;
        break label58;
      }
      i += 1;
    }
    i = 0;
    label58:
    if (i == 0) {
      return false;
    }
    if (Build.VERSION.SDK_INT >= 25) {
      return paramInputConnection.commitContent((InputContentInfo)paramInputContentInfoCompat.unwrap(), paramInt, paramBundle);
    }
    i = EditorInfoCompat.getProtocol(paramEditorInfo);
    if ((i != 2) && (i != 3) && (i != 4)) {
      return false;
    }
    paramEditorInfo = new Bundle();
    paramEditorInfo.putParcelable("androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_URI", paramInputContentInfoCompat.getContentUri());
    paramEditorInfo.putParcelable("androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_DESCRIPTION", paramInputContentInfoCompat.getDescription());
    paramEditorInfo.putParcelable("androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_LINK_URI", paramInputContentInfoCompat.getLinkUri());
    paramEditorInfo.putInt("androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_FLAGS", paramInt);
    paramEditorInfo.putParcelable("androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_OPTS", paramBundle);
    return paramInputConnection.performPrivateCommand("androidx.core.view.inputmethod.InputConnectionCompat.COMMIT_CONTENT", paramEditorInfo);
  }
  
  public static InputConnection createWrapper(InputConnection paramInputConnection, EditorInfo paramEditorInfo, final OnCommitContentListener paramOnCommitContentListener)
  {
    if (paramInputConnection != null)
    {
      if (paramEditorInfo != null)
      {
        if (paramOnCommitContentListener != null)
        {
          if (Build.VERSION.SDK_INT >= 25) {
            new InputConnectionWrapper(paramInputConnection, false)
            {
              public boolean commitContent(InputContentInfo paramAnonymousInputContentInfo, int paramAnonymousInt, Bundle paramAnonymousBundle)
              {
                if (paramOnCommitContentListener.onCommitContent(InputContentInfoCompat.wrap(paramAnonymousInputContentInfo), paramAnonymousInt, paramAnonymousBundle)) {
                  return true;
                }
                return super.commitContent(paramAnonymousInputContentInfo, paramAnonymousInt, paramAnonymousBundle);
              }
            };
          }
          if (EditorInfoCompat.getContentMimeTypes(paramEditorInfo).length == 0) {
            return paramInputConnection;
          }
          new InputConnectionWrapper(paramInputConnection, false)
          {
            public boolean performPrivateCommand(String paramAnonymousString, Bundle paramAnonymousBundle)
            {
              if (InputConnectionCompat.handlePerformPrivateCommand(paramAnonymousString, paramAnonymousBundle, paramOnCommitContentListener)) {
                return true;
              }
              return super.performPrivateCommand(paramAnonymousString, paramAnonymousBundle);
            }
          };
        }
        throw new IllegalArgumentException("onCommitContentListener must be non-null");
      }
      throw new IllegalArgumentException("editorInfo must be non-null");
    }
    throw new IllegalArgumentException("inputConnection must be non-null");
  }
  
  static boolean handlePerformPrivateCommand(String paramString, Bundle paramBundle, OnCommitContentListener paramOnCommitContentListener)
  {
    throw new Runtime("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.useAs(TypeTransformer.java:868)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.s1stmt(TypeTransformer.java:806)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.sxStmt(TypeTransformer.java:840)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:206)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\n");
  }
  
  public static abstract interface OnCommitContentListener
  {
    public abstract boolean onCommitContent(InputContentInfoCompat paramInputContentInfoCompat, int paramInt, Bundle paramBundle);
  }
}
