package androidx.fragment.package_8;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import androidx.core.package_7.ActivityCompat;
import androidx.core.util.Preconditions;
import androidx.fragment.app.FragmentContainer;
import java.io.FileDescriptor;
import java.io.PrintWriter;

public abstract class FragmentHostCallback<E>
  extends FragmentContainer
{
  private final Activity mActivity;
  private final Context mContext;
  final FragmentManagerImpl mFragmentManager = new FragmentManagerImpl();
  private final Handler mHandler;
  private final int mWindowAnimations;
  
  FragmentHostCallback(Activity paramActivity, Context paramContext, Handler paramHandler, int paramInt)
  {
    mActivity = paramActivity;
    mContext = ((Context)Preconditions.checkNotNull(paramContext, "context == null"));
    mHandler = ((Handler)Preconditions.checkNotNull(paramHandler, "handler == null"));
    mWindowAnimations = paramInt;
  }
  
  public FragmentHostCallback(Context paramContext, Handler paramHandler, int paramInt)
  {
    this(localActivity, paramContext, paramHandler, paramInt);
  }
  
  FragmentHostCallback(FragmentActivity paramFragmentActivity)
  {
    this(paramFragmentActivity, paramFragmentActivity, new Handler(), 0);
  }
  
  Activity getActivity()
  {
    return mActivity;
  }
  
  Context getContext()
  {
    return mContext;
  }
  
  Handler getHandler()
  {
    return mHandler;
  }
  
  void onAttachFragment(Fragment paramFragment) {}
  
  public void onDump(String paramString, FileDescriptor paramFileDescriptor, PrintWriter paramPrintWriter, String[] paramArrayOfString) {}
  
  public View onFindViewById(int paramInt)
  {
    return null;
  }
  
  public abstract Object onGetHost();
  
  public LayoutInflater onGetLayoutInflater()
  {
    return LayoutInflater.from(mContext);
  }
  
  public int onGetWindowAnimations()
  {
    return mWindowAnimations;
  }
  
  public boolean onHasView()
  {
    return true;
  }
  
  public boolean onHasWindowAnimations()
  {
    return true;
  }
  
  public void onRequestPermissionsFromFragment(Fragment paramFragment, String[] paramArrayOfString, int paramInt) {}
  
  public boolean onShouldSaveFragmentState(Fragment paramFragment)
  {
    return true;
  }
  
  public boolean onShouldShowRequestPermissionRationale(String paramString)
  {
    return false;
  }
  
  public void onStartActivityFromFragment(Fragment paramFragment, Intent paramIntent, int paramInt)
  {
    onStartActivityFromFragment(paramFragment, paramIntent, paramInt, null);
  }
  
  public void onStartActivityFromFragment(Fragment paramFragment, Intent paramIntent, int paramInt, Bundle paramBundle)
  {
    if (paramInt == -1)
    {
      mContext.startActivity(paramIntent);
      return;
    }
    throw new IllegalStateException("Starting activity with a requestCode requires a FragmentActivity host");
  }
  
  public void onStartIntentSenderFromFragment(Fragment paramFragment, IntentSender paramIntentSender, int paramInt1, Intent paramIntent, int paramInt2, int paramInt3, int paramInt4, Bundle paramBundle)
    throws IntentSender.SendIntentException
  {
    if (paramInt1 == -1)
    {
      ActivityCompat.startIntentSenderForResult(mActivity, paramIntentSender, paramInt1, paramIntent, paramInt2, paramInt3, paramInt4, paramBundle);
      return;
    }
    throw new IllegalStateException("Starting intent sender with a requestCode requires a FragmentActivity host");
  }
  
  public void onSupportInvalidateOptionsMenu() {}
}
