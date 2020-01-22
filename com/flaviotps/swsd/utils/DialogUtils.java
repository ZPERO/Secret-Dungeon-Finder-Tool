package com.flaviotps.swsd.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Build.VERSION;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.flaviotps.swsd.view.BaseActivity;
import com.flaviotps.swsd.view.SettingsActivity;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000\f\n\002\030\002\n\002\020\000\n\002\b\003\030\000 \0032\0020\001:\001\003B\005?\006\002\020\002?\006\004"}, d2={"Lcom/flaviotps/swsd/utils/DialogUtils;", "", "()V", "Companion", "app_release"}, k=1, mv={1, 1, 16})
public final class DialogUtils
{
  public static final Companion Companion = new Companion(null);
  
  public DialogUtils() {}
  
  @Metadata(bv={1, 0, 3}, d1={"\000&\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\002\n\000\n\002\030\002\n\000\n\002\020\016\n\002\b\004\n\002\030\002\n\000\b?\003\030\0002\0020\001B\007\b\002?\006\002\020\002J\026\020\003\032\0020\0042\006\020\005\032\0020\0062\006\020\007\032\0020\bJ\026\020\t\032\0020\0042\006\020\005\032\0020\0062\006\020\007\032\0020\bJ,\020\n\032\0020\0042\006\020\005\032\0020\0062\006\020\007\032\0020\b2\b\b\002\020\013\032\0020\b2\n\b\002\020\f\032\004\030\0010\r?\006\016"}, d2={"Lcom/flaviotps/swsd/utils/DialogUtils$Companion;", "", "()V", "showErrorDialog", "", "baseActivity", "Lcom/flaviotps/swsd/view/BaseActivity;", "msg", "", "showSettingsDialog", "showWarningDialog", "btnTitle", "listener", "Landroid/view/View$OnClickListener;", "app_release"}, k=1, mv={1, 1, 16})
  public static final class Companion
  {
    private Companion() {}
    
    public final void showErrorDialog(BaseActivity paramBaseActivity, String paramString)
    {
      Intrinsics.checkParameterIsNotNull(paramBaseActivity, "baseActivity");
      Intrinsics.checkParameterIsNotNull(paramString, "msg");
      Dialog localDialog = new Dialog((Context)paramBaseActivity);
      localDialog.setCancelable(false);
      localDialog.setContentView(2131558450);
      Button localButton = (Button)localDialog.findViewById(2131361869);
      TextView localTextView = (TextView)localDialog.findViewById(2131362068);
      Intrinsics.checkExpressionValueIsNotNull(localTextView, "text");
      if (Build.VERSION.SDK_INT >= 24) {
        paramBaseActivity = (CharSequence)Html.fromHtml(paramString, 0);
      } else {
        paramBaseActivity = (CharSequence)Html.fromHtml(paramString);
      }
      localTextView.setText(paramBaseActivity);
      localButton.setOnClickListener((View.OnClickListener)new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          dismiss();
        }
      });
      localDialog.show();
    }
    
    public final void showSettingsDialog(BaseActivity paramBaseActivity, String paramString)
    {
      Intrinsics.checkParameterIsNotNull(paramBaseActivity, "baseActivity");
      Intrinsics.checkParameterIsNotNull(paramString, "msg");
      final Dialog localDialog = new Dialog((Context)paramBaseActivity);
      localDialog.setCancelable(false);
      localDialog.setContentView(2131558451);
      Button localButton1 = (Button)localDialog.findViewById(2131361869);
      Button localButton2 = (Button)localDialog.findViewById(2131361870);
      TextView localTextView = (TextView)localDialog.findViewById(2131362068);
      Intrinsics.checkExpressionValueIsNotNull(localTextView, "text");
      if (Build.VERSION.SDK_INT >= 24) {
        paramString = (CharSequence)Html.fromHtml(paramString, 0);
      } else {
        paramString = (CharSequence)Html.fromHtml(paramString);
      }
      localTextView.setText(paramString);
      localButton1.setOnClickListener((View.OnClickListener)new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          dismiss();
        }
      });
      localButton2.setOnClickListener((View.OnClickListener)new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          BaseActivity.openActivity$default(DialogUtils.Companion.this, SettingsActivity.class, null, 2, null);
          localDialog.dismiss();
        }
      });
      localDialog.show();
    }
    
    public final void showWarningDialog(BaseActivity paramBaseActivity, String paramString1, String paramString2, View.OnClickListener paramOnClickListener)
    {
      Intrinsics.checkParameterIsNotNull(paramBaseActivity, "baseActivity");
      Intrinsics.checkParameterIsNotNull(paramString1, "msg");
      Intrinsics.checkParameterIsNotNull(paramString2, "btnTitle");
      Dialog localDialog = new Dialog((Context)paramBaseActivity);
      localDialog.setCancelable(false);
      localDialog.setContentView(2131558452);
      Button localButton = (Button)localDialog.findViewById(2131361869);
      TextView localTextView = (TextView)localDialog.findViewById(2131362068);
      Intrinsics.checkExpressionValueIsNotNull(localButton, "btnOk");
      localButton.setText((CharSequence)paramString2);
      Intrinsics.checkExpressionValueIsNotNull(localTextView, "text");
      if (Build.VERSION.SDK_INT >= 24) {
        paramBaseActivity = (CharSequence)Html.fromHtml(paramString1, 0);
      } else {
        paramBaseActivity = (CharSequence)Html.fromHtml(paramString1);
      }
      localTextView.setText(paramBaseActivity);
      if (paramOnClickListener != null) {
        localButton.setOnClickListener(paramOnClickListener);
      } else {
        localButton.setOnClickListener((View.OnClickListener)new View.OnClickListener()
        {
          public final void onClick(View paramAnonymousView)
          {
            dismiss();
          }
        });
      }
      localDialog.show();
    }
  }
}
