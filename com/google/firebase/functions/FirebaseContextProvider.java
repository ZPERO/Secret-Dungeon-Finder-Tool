package com.google.firebase.functions;

import com.google.android.android.tasks.Task;
import com.google.android.android.tasks.TaskCompletionSource;
import com.google.firebase.auth.internal.InternalAuthProvider;
import com.google.firebase.inject.Provider;

class FirebaseContextProvider
  implements ContextProvider
{
  private final Provider<com.google.firebase.iid.internal.FirebaseInstanceIdInternal> instanceId;
  private final Provider<InternalAuthProvider> tokenProvider;
  
  FirebaseContextProvider(Provider paramProvider1, Provider paramProvider2)
  {
    tokenProvider = paramProvider1;
    instanceId = paramProvider2;
  }
  
  public Task getContext()
  {
    Object localObject = tokenProvider;
    if (localObject == null)
    {
      localObject = new TaskCompletionSource();
      ((TaskCompletionSource)localObject).setResult(new HttpsCallableContext(null, ((com.google.firebase.package_9.internal.FirebaseInstanceIdInternal)instanceId.get()).getToken()));
      return ((TaskCompletionSource)localObject).getTask();
    }
    return ((InternalAuthProvider)((Provider)localObject).get()).getAccessToken(false).continueWith(FirebaseContextProvider..Lambda.1.lambdaFactory$(this));
  }
}
