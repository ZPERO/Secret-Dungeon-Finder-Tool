package com.google.firebase.internal;

import com.google.android.android.tasks.Task;

@Deprecated
public abstract interface InternalTokenProvider
{
  public abstract Task getAccessToken(boolean paramBoolean);
  
  public abstract String getUid();
}
