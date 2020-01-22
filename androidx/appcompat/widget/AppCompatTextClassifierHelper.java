package androidx.appcompat.widget;

import android.content.Context;
import android.view.View;
import android.view.textclassifier.TextClassificationManager;
import android.view.textclassifier.TextClassifier;
import android.widget.TextView;
import androidx.core.util.Preconditions;

final class AppCompatTextClassifierHelper
{
  private TextClassifier mTextClassifier;
  private TextView mTextView;
  
  AppCompatTextClassifierHelper(TextView paramTextView)
  {
    mTextView = ((TextView)Preconditions.checkNotNull(paramTextView));
  }
  
  public TextClassifier getTextClassifier()
  {
    TextClassifier localTextClassifier = mTextClassifier;
    Object localObject = localTextClassifier;
    if (localTextClassifier == null)
    {
      localObject = (TextClassificationManager)mTextView.getContext().getSystemService(TextClassificationManager.class);
      if (localObject != null) {
        return ((TextClassificationManager)localObject).getTextClassifier();
      }
      localObject = TextClassifier.NO_OP;
    }
    return localObject;
  }
  
  public void setTextClassifier(TextClassifier paramTextClassifier)
  {
    mTextClassifier = paramTextClassifier;
  }
}
