package com.google.firebase.functions;

import android.content.Context;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.internal.InternalAuthProvider;
import com.google.firebase.components.Component;
import com.google.firebase.components.Component.Builder;
import com.google.firebase.components.ComponentRegistrar;
import com.google.firebase.components.Dependency;
import com.google.firebase.iid.internal.FirebaseInstanceIdInternal;
import java.util.Arrays;
import java.util.List;

public class FunctionsRegistrar
  implements ComponentRegistrar
{
  public FunctionsRegistrar() {}
  
  public List getComponents()
  {
    return Arrays.asList(new Component[] { Component.builder(ContextProvider.class).putAll(Dependency.optionalProvider(InternalAuthProvider.class)).putAll(Dependency.requiredProvider(FirebaseInstanceIdInternal.class)).factory(FunctionsRegistrar..Lambda.1.lambdaFactory$()).build(), Component.builder(FunctionsMultiResourceComponent.class).putAll(Dependency.required(Context.class)).putAll(Dependency.required(ContextProvider.class)).putAll(Dependency.required(FirebaseOptions.class)).factory(FunctionsRegistrar..Lambda.2.lambdaFactory$()).build() });
  }
}
