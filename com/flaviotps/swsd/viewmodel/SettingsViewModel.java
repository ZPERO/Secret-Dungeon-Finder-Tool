package com.flaviotps.swsd.viewmodel;

import androidx.lifecycle.ViewModel;
import com.flaviotps.swsd.model.TokenModel;
import com.flaviotps.swsd.repository.TokenRepository;
import com.flaviotps.swsd.repository.TokenRepository.Companion;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.KProperty;

@Metadata(bv={1, 0, 3}, d1={"\000*\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\005\n\002\020\002\n\000\n\002\020\016\n\002\b\002\n\002\020\013\n\002\b\003\030\0002\0020\001B\005?\006\002\020\002J3\020\t\032\0020\n2\b\020\013\032\004\030\0010\f2\b\020\r\032\004\030\0010\f2\b\020\016\032\004\030\0010\0172\b\020\020\032\004\030\0010\017?\006\002\020\021R\033\020\003\032\0020\0048BX??\002?\006\f\n\004\b\007\020\b\032\004\b\005\020\006?\006\022"}, d2={"Lcom/flaviotps/swsd/viewmodel/SettingsViewModel;", "Landroidx/lifecycle/ViewModel;", "()V", "tokenRepository", "Lcom/flaviotps/swsd/repository/TokenRepository;", "getTokenRepository", "()Lcom/flaviotps/swsd/repository/TokenRepository;", "tokenRepository$delegate", "Lkotlin/Lazy;", "setTokenStatus", "", "uid", "", "token", "enabled", "", "requestEnabled", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Boolean;Ljava/lang/Boolean;)V", "app_release"}, k=1, mv={1, 1, 16})
public final class SettingsViewModel
  extends ViewModel
{
  private final Lazy tokenRepository$delegate = LazyKt__LazyJVMKt.lazy((Function0)tokenRepository.2.INSTANCE);
  
  public SettingsViewModel() {}
  
  private final TokenRepository getTokenRepository()
  {
    Lazy localLazy = tokenRepository$delegate;
    KProperty localKProperty = $$delegatedProperties[0];
    return (TokenRepository)localLazy.getValue();
  }
  
  public final void setTokenStatus(String paramString1, String paramString2, Boolean paramBoolean1, Boolean paramBoolean2)
  {
    if ((paramString2 != null) && (paramBoolean1 != null) && (paramString1 != null) && (paramBoolean2 != null)) {
      getTokenRepository().setTokenStatus(paramString1, new TokenModel(paramString2, paramBoolean1.booleanValue(), paramBoolean2.booleanValue()));
    }
  }
}
