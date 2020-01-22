package com.google.firebase.database.connection;

public abstract interface ConnectionAuthTokenProvider
{
  public abstract void getToken(boolean paramBoolean, GetTokenCallback paramGetTokenCallback);
  
  public static abstract interface GetTokenCallback
  {
    public abstract void onError(String paramString);
    
    public abstract void onSuccess(String paramString);
  }
}
