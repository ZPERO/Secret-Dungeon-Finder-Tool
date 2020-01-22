package com.google.firebase.package_9;

import com.google.firebase.FirebaseApp;
import com.google.firebase.components.Component;
import com.google.firebase.components.Component.Builder;
import com.google.firebase.components.ComponentRegistrar;
import com.google.firebase.components.Dependency;
import com.google.firebase.events.Subscriber;
import java.util.Arrays;
import java.util.List;

public final class Registrar
  implements ComponentRegistrar
{
  public Registrar() {}
  
  public final List getComponents()
  {
    return Arrays.asList(new Component[] { Component.builder(com.google.firebase.iid.FirebaseInstanceId.class).putAll(Dependency.required(FirebaseApp.class)).putAll(Dependency.required(Subscriber.class)).factory(zzao.zzcm).alwaysEager().build(), Component.builder(com.google.firebase.iid.internal.FirebaseInstanceIdInternal.class).putAll(Dependency.required(com.google.firebase.iid.FirebaseInstanceId.class)).factory(zzap.zzcm).build() });
  }
  
  final class zza
    implements com.google.firebase.package_9.internal.FirebaseInstanceIdInternal
  {
    public zza() {}
    
    public final String getId()
    {
      return Registrar.this.getId();
    }
    
    public final String getToken()
    {
      return Registrar.this.getToken();
    }
  }
}
