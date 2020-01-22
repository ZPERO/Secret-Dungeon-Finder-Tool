package com.google.firebase.auth.internal;

import com.google.android.android.tasks.Task;
import com.google.firebase.internal.InternalTokenProvider;

public abstract interface InternalAuthProvider
  extends InternalTokenProvider
{
  public abstract void addIdTokenListener(IdTokenListener paramIdTokenListener);
  
  public abstract Task getAccessToken(boolean paramBoolean);
  
  public abstract String getUid();
  
  public abstract void removeIdTokenListener(IdTokenListener paramIdTokenListener);
}
