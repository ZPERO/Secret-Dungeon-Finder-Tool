package com.google.android.android.auth.params.signin;

import android.os.Bundle;
import java.util.List;

public abstract interface GoogleSignInOptionsExtension
{
  public static final int FITNESS = 3;
  public static final int GAMES = 1;
  
  public abstract int getExtensionType();
  
  public abstract List getImpliedScopes();
  
  public abstract Bundle toBundle();
}
