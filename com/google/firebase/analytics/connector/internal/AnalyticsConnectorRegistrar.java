package com.google.firebase.analytics.connector.internal;

import android.content.Context;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.connector.AnalyticsConnector;
import com.google.firebase.components.Component;
import com.google.firebase.components.Component.Builder;
import com.google.firebase.components.ComponentRegistrar;
import com.google.firebase.components.Dependency;
import com.google.firebase.events.Subscriber;
import java.util.Collections;
import java.util.List;

public class AnalyticsConnectorRegistrar
  implements ComponentRegistrar
{
  public AnalyticsConnectorRegistrar() {}
  
  public List getComponents()
  {
    return Collections.singletonList(Component.builder(AnalyticsConnector.class).putAll(Dependency.required(FirebaseApp.class)).putAll(Dependency.required(Context.class)).putAll(Dependency.required(Subscriber.class)).factory(Filter.zzbsl).eagerInDefaultApp().build());
  }
}
