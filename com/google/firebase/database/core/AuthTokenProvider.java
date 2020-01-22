package com.google.firebase.database.core;

public abstract interface AuthTokenProvider
{
  public abstract void addTokenChangeListener(TokenChangeListener paramTokenChangeListener);
  
  public abstract void getToken(boolean paramBoolean, GetTokenCompletionListener paramGetTokenCompletionListener);
  
  public abstract void removeTokenChangeListener(TokenChangeListener paramTokenChangeListener);
  
  public static abstract interface GetTokenCompletionListener
  {
    public abstract void onError(String paramString);
    
    public abstract void onSuccess(String paramString);
  }
  
  public static abstract interface TokenChangeListener
  {
    public abstract void onTokenChange();
    
    public abstract void onTokenChange(String paramString);
  }
}
