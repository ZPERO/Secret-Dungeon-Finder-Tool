package com.google.firebase.functions;

import com.google.android.android.tasks.Task;

abstract interface ContextProvider
{
  public abstract Task getContext();
}
