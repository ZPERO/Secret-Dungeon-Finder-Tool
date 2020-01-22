package com.google.firebase.database.android;

import com.google.android.android.tasks.OnFailureListener;
import com.google.android.android.tasks.Task;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseApp.IdTokenListener;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.core.AuthTokenProvider;
import com.google.firebase.database.core.AuthTokenProvider.GetTokenCompletionListener;
import com.google.firebase.database.core.AuthTokenProvider.TokenChangeListener;
import com.google.firebase.internal.InternalTokenResult;
import com.google.firebase.internal.text.FirebaseNoSignedInUserException;
import java.util.concurrent.ScheduledExecutorService;

public class AndroidAuthTokenProvider
  implements AuthTokenProvider
{
  private final ScheduledExecutorService executorService;
  private final FirebaseApp firebaseApp;
  
  public AndroidAuthTokenProvider(FirebaseApp paramFirebaseApp, ScheduledExecutorService paramScheduledExecutorService)
  {
    firebaseApp = paramFirebaseApp;
    executorService = paramScheduledExecutorService;
  }
  
  private FirebaseApp.IdTokenListener produceIdTokenListener(final AuthTokenProvider.TokenChangeListener paramTokenChangeListener)
  {
    new FirebaseApp.IdTokenListener()
    {
      public void onIdTokenChanged(final InternalTokenResult paramAnonymousInternalTokenResult)
      {
        executorService.execute(new Runnable()
        {
          public void run()
          {
            val$tokenListener.onTokenChange(paramAnonymousInternalTokenResult.getToken());
          }
        });
      }
    };
  }
  
  public void addTokenChangeListener(AuthTokenProvider.TokenChangeListener paramTokenChangeListener)
  {
    paramTokenChangeListener = produceIdTokenListener(paramTokenChangeListener);
    firebaseApp.addIdTokenListener(paramTokenChangeListener);
  }
  
  public void getToken(boolean paramBoolean, final AuthTokenProvider.GetTokenCompletionListener paramGetTokenCompletionListener)
  {
    firebaseApp.getToken(paramBoolean).addOnSuccessListener(executorService, new com.google.android.android.tasks.OnSuccessListener()
    {
      public void onSuccess(GetTokenResult paramAnonymousGetTokenResult)
      {
        paramGetTokenCompletionListener.onSuccess(paramAnonymousGetTokenResult.getToken());
      }
    }).addOnFailureListener(executorService, new OnFailureListener()
    {
      private boolean isUnauthenticatedUsage(Exception paramAnonymousException)
      {
        return ((paramAnonymousException instanceof FirebaseApiNotAvailableException)) || ((paramAnonymousException instanceof FirebaseNoSignedInUserException));
      }
      
      public void onFailure(Exception paramAnonymousException)
      {
        if (isUnauthenticatedUsage(paramAnonymousException))
        {
          paramGetTokenCompletionListener.onSuccess(null);
          return;
        }
        paramGetTokenCompletionListener.onError(paramAnonymousException.getMessage());
      }
    });
  }
  
  public void removeTokenChangeListener(AuthTokenProvider.TokenChangeListener paramTokenChangeListener) {}
}
